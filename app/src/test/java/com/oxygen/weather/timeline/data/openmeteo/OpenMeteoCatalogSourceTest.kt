package com.oxygen.weather.timeline.data.openmeteo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.oxygen.weather.timeline.data.catalog.*
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Assert.*
import org.junit.Test

class OpenMeteoCatalogSourceTest {
    private val location = CatalogLocation(
        LocationId("reference-chicago"),
        GeoCoordinates(41.8781, -87.6298),
        ZoneId.of("America/Chicago"),
    )
    private val request = CatalogLoadRequest(
        location,
        DatasetId("open-meteo-test"),
        Instant.parse("2026-10-28T14:57:55.714828Z"),
    )

    @Test fun `captured response is published as partial catalog`() = runBlocking {
        val bytes = javaClass.getResourceAsStream("/openmeteo/provider-response-public-chicago.json")!!.use { it.readBytes() }
        val result = loadWith(MockResponse().setBody(bytes.decodeToString()))
        assertTrue(result is CatalogLoadResult.Partial)
        assertEquals(1_056, (result as CatalogLoadResult.Partial).catalog.facts.size)
    }

    @Test fun `null only hourly values map to no data`() = runBlocking {
        val bytes = javaClass.getResourceAsStream("/openmeteo/provider-response-public-chicago.json")!!.use { it.readBytes() }
        val json = ObjectMapper().readTree(bytes)
        val hourly = json.path("hourly")
        hourly.fieldNames().asSequence().filter { it != "time" }.forEach { name ->
            val values = hourly.path(name) as ArrayNode
            for (index in 0 until values.size()) values.set(index, ObjectMapper().nullNode())
        }
        val result = loadWith(MockResponse().setBody(ObjectMapper().writeValueAsBytes(json).decodeToString()))
        assertEquals(CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE), result)
    }

    @Test fun `http failure maps to unavailable transport failure`() = runBlocking {
        val result = loadWith(MockResponse().setResponseCode(503).setBody("service unavailable"))
        assertEquals(TransportFailureReason.UNAVAILABLE, (result as CatalogLoadResult.TransportFailure).reason)
    }

    @Test fun `cancellation propagates through source load`() = runBlocking {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
            val source = sourceFor(server)
            val job = async(Dispatchers.IO) { source.load(request) }
            assertNotNull(server.takeRequest(10, TimeUnit.SECONDS))
            job.cancel()
            try {
                job.await()
                fail("Expected cancellation")
            } catch (_: CancellationException) {
                // Expected: source must not turn coroutine cancellation into a failure result.
            }
        }
    }

    private suspend fun loadWith(response: MockResponse): CatalogLoadResult = MockWebServer().use { server ->
        server.enqueue(response)
        sourceFor(server).load(request)
    }

    private fun sourceFor(server: MockWebServer): OpenMeteoCatalogSource {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().url(server.url("/v1/forecast")).build())
        }.build()
        return OpenMeteoCatalogSource(OpenMeteoTransport(client))
    }
}
