package com.oxygen.weather.timeline.data.openmeteo

import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OpenMeteoPlaceSearcherTest {
    private lateinit var server: MockWebServer

    @Before fun setUp() { server = MockWebServer().apply { start() } }
    @After fun tearDown() { server.shutdown() }

    private fun searcher(bodyLimit: Int = 512 * 1024) = OpenMeteoPlaceSearcher(
        client = OkHttpClient(),
        endpoint = server.url("/v1/search").toString(),
        limits = OpenMeteoPlaceSearcher.Limits(maxResponseBytes = bodyLimit),
    )

    @Test fun requestUsesBoundedOpenMeteoSearchParametersAndEncoding() {
        val request = searcher().request("New York, NY")
        assertEquals("/v1/search", request.url.encodedPath)
        assertEquals("New York, NY", request.url.queryParameter("name"))
        assertEquals("10", request.url.queryParameter("count"))
        assertEquals("en", request.url.queryParameter("language"))
    }

    @Test fun blankAndOneCharacterQueriesReturnNoMatchesWithoutNetwork() = runBlocking {
        val searcher = searcher()
        assertEquals(PlaceSearchResult.NoMatches, searcher.search("  "))
        assertEquals(PlaceSearchResult.NoMatches, searcher.search("x"))
        assertEquals(
            PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_QUERY),
            searcher.search("a".repeat(161)),
        )
        assertEquals(0, server.requestCount)
    }

    @Test fun mapsAmbiguousCandidatesAndCallerSuppliedIdentityWithoutCoordinateRounding() = runBlocking {
        server.enqueue(MockResponse().setBody("""
            {"results":[
              {"id":2950159,"name":"Springfield","latitude":39.7990173,"longitude":-89.6439575,"timezone":"America/Chicago","country":"United States","country_code":"US","admin1":"Illinois"},
              {"id":4250542,"name":"Springfield","latitude":42.1014831,"longitude":-72.589811,"timezone":"America/New_York","country_code":"US","admin1":"Massachusetts"}
            ]}
        """.trimIndent()))
        val result = searcher().search("Springfield") as PlaceSearchResult.Matches
        assertEquals(2, result.candidates.size)
        assertEquals("Springfield, Illinois, United States (US)", result.candidates[0].displayLabel)
        assertEquals("Springfield, Massachusetts, US", result.candidates[1].displayLabel)
        assertEquals(39.7990173, result.candidates[0].coordinates.latitudeDegrees, 0.0)
        assertEquals(-89.6439575, result.candidates[0].coordinates.longitudeDegrees, 0.0)
        assertTrue(result.candidates[0].attribution.sourceName.contains("GeoNames"))
        assertEquals("https", result.candidates[0].attribution.licenseUrl.scheme)
        assertNotNull(result.candidates[0].attribution.changesMadeNotice)
        val location = result.candidates[0].toCatalogLocation(LocationId("loc-local-1"))
        assertEquals("loc-local-1", location.id.value)
        assertNotEquals(result.candidates[0].sourceReference, location.id.value)
        assertEquals("America/Chicago", location.timeZone.id)
    }

    @Test fun supportsCountryNameOnlyAndCountryCodeOnlyAndRejectsRowsWithoutEither() = runBlocking {
        server.enqueue(MockResponse().setBody("""
            {"results":[
              {"id":1,"name":"A","latitude":1,"longitude":2,"timezone":"Europe/Paris","country":"France"},
              {"id":2,"name":"B","latitude":3,"longitude":4,"timezone":"Europe/Paris","country_code":"FR","admin1":"Île-de-France"},
              {"id":3,"name":"C","latitude":3,"longitude":4,"timezone":"Europe/Paris","admin1":"Île-de-France"}
            ]}
        """.trimIndent()))
        val result = searcher().search("Paris") as PlaceSearchResult.Matches
        assertEquals(2, result.candidates.size)
        assertEquals(1, result.rejectedCandidateCount)
        assertEquals("France", result.candidates[0].countryName)
        assertEquals("FR", result.candidates[1].countryCode)
    }

    @Test fun mixedRowsKeepUsablePeersAndAllInvalidRowsAreInvalidResponse() = runBlocking {
        server.enqueue(MockResponse().setBody("""
            {"results":[
              {"name":"Valid","latitude":1.123456789,"longitude":2.987654321,"timezone":"Europe/Paris","country":"France"},
              {"name":"Bad timezone","latitude":1,"longitude":2,"timezone":"Mars/Olympus","country":"France"}
            ]}
        """.trimIndent()))
        val mixed = searcher().search("Valid") as PlaceSearchResult.Matches
        assertEquals(1, mixed.candidates.size)
        assertEquals(1, mixed.rejectedCandidateCount)
        assertEquals(1.123456789, mixed.candidates.single().coordinates.latitudeDegrees, 0.0)

        server.enqueue(MockResponse().setBody("""{"results":[{"name":"Bad","latitude":1000,"longitude":2,"timezone":"Europe/Paris","country":"France"}]}"""))
        assertEquals(
            PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE),
            searcher().search("Bad"),
        )
    }

    @Test fun missingAndEmptyResultsAreNoMatchesButMalformedEnvelopesAreFailures() = runBlocking {
        server.enqueue(MockResponse().setBody("{}"))
        assertEquals(PlaceSearchResult.NoMatches, searcher().search("Paris"))
        server.enqueue(MockResponse().setBody("""{"results":[]}"""))
        assertEquals(PlaceSearchResult.NoMatches, searcher().search("Paris"))
        server.enqueue(MockResponse().setBody("{".repeat(3)))
        assertEquals(PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE), searcher().search("Paris"))
        server.enqueue(MockResponse().setBody("""{"results":{}}"""))
        assertEquals(PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE), searcher().search("Paris"))
        server.enqueue(MockResponse().setBody("""{"error":true,"reason":"bad"}"""))
        assertEquals(PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE), searcher().search("Paris"))
    }

    @Test fun failuresRetainTheirTransportDisposition() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(503).setBody("""{"reason":"temporarily unavailable"}"""))
        val http = searcher().search("Paris") as PlaceSearchResult.Failure
        assertEquals(PlaceSearchResult.Failure.Reason.HTTP, http.reason)
        assertEquals("503: temporarily unavailable", http.detail)

        server.enqueue(MockResponse().setBody("{".repeat(100)))
        assertEquals(
            PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.OVERSIZED_RESPONSE),
            searcher(bodyLimit = 8).search("Paris"),
        )
    }

    @Test fun timeoutAndConnectivityFailuresRemainDistinct() = runBlocking {
        server.enqueue(MockResponse().setHeadersDelay(300, TimeUnit.MILLISECONDS).setBody("{}"))
        val shortTimeout = OpenMeteoPlaceSearcher(
            client = OkHttpClient(),
            endpoint = server.url("/v1/search").toString(),
            limits = OpenMeteoPlaceSearcher.Limits(readTimeoutMillis = 40, callTimeoutMillis = 500),
        )
        assertEquals(
            PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.TIMEOUT),
            shortTimeout.search("Paris"),
        )

        val closedServerUrl = server.url("/v1/search").toString()
        server.shutdown()
        val unavailable = OpenMeteoPlaceSearcher(client = OkHttpClient(), endpoint = closedServerUrl)
        assertEquals(
            PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.CONNECTIVITY),
            unavailable.search("Paris"),
        )
    }

    @Test fun cancellationPropagatesToTheCaller() = runBlocking {
        server.enqueue(MockResponse().setHeadersDelay(2, TimeUnit.SECONDS).setBody("{}"))
        val job = launch(Dispatchers.IO) { searcher().search("Paris") }
        assertNotNull(server.takeRequest(1, TimeUnit.SECONDS))
        job.cancel()
        job.join()
        assertTrue(job.isCancelled)
    }
}
