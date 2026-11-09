package com.oxygen.weather.timeline.data.openmeteo

import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import java.time.ZoneId
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.TimeUnit

class OpenMeteoTransportTest {
    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.9, -87.6), ZoneId.of("America/Chicago"))
    private val valid = """{"timezone":"America/Chicago","hourly":{"time":[]},"daily":{"time":[]}}"""

    @Test fun requestIncludesApparentTemperatureInCelsius() {
        val request = OpenMeteoTransport(OkHttpClient()).request(location)
        assertTrue(request.url.queryParameter("hourly")!!.split(",").contains("apparent_temperature"))
        assertEquals("celsius", request.url.queryParameter("temperature_unit"))
    }

    @Test fun requestIsFixedAndEncoded() {
        val request = OpenMeteoTransport(OkHttpClient()).request(location)
        assertEquals("https", request.url.scheme)
        assertEquals("api.open-meteo.com", request.url.host)
        assertEquals("/v1/forecast", request.url.encodedPath)
        assertEquals("America/Chicago", request.url.queryParameter("timezone"))
        assertEquals("30", request.url.queryParameter("past_days"))
        assertEquals("15", request.url.queryParameter("forecast_days"))
        assertEquals(OpenMeteoTransport.HOURLY.joinToString(","), request.url.queryParameter("hourly"))
        assertEquals(OpenMeteoTransport.DAILY.joinToString(","), request.url.queryParameter("daily"))
        assertNull(request.url.queryParameter("apikey"))
    }

    private fun withServer(response: MockResponse, limits: OpenMeteoTransport.Limits = OpenMeteoTransport.Limits(), check: (OpenMeteoTransport.Result) -> Unit) {
        MockWebServer().use { server ->
            server.enqueue(response)
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().url(server.url("/v1/forecast")).build())
            }.build()
            check(runBlocking { OpenMeteoTransport(client, limits).fetch(location) })
        }
    }

    @Test fun successAndFailures() {
        withServer(MockResponse().setBody(valid)) { assertTrue(it is OpenMeteoTransport.Result.Success) }
        withServer(MockResponse().setResponseCode(400).setBody("""{"error":true,"reason":"bad parameter"}""")) {
            assertEquals(OpenMeteoTransport.Result.HttpError(400, "bad parameter"), it)
        }
        withServer(MockResponse().setResponseCode(502).setBody("<html>bad gateway</html>")) {
            assertEquals(OpenMeteoTransport.Result.HttpError(502, null), it)
        }
        withServer(MockResponse().setResponseCode(302).addHeader("Location", "https://evil.example/")) {
            assertEquals(OpenMeteoTransport.Result.HttpError(302, null), it)
        }
        withServer(MockResponse().setBody("{")) { assertEquals(OpenMeteoTransport.Result.InvalidResponse, it) }
        withServer(MockResponse().setBody("[]")) { assertEquals(OpenMeteoTransport.Result.InvalidResponse, it) }
        withServer(MockResponse().setBody("{}")) { assertEquals(OpenMeteoTransport.Result.InvalidResponse, it) }
        withServer(MockResponse().setBody("x".repeat(256)), OpenMeteoTransport.Limits(maxResponseBytes = 64)) {
            assertEquals(OpenMeteoTransport.Result.OversizedResponse, it)
        }
    }

    @Test fun timeoutAndConnectivity() {
        withServer(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE), OpenMeteoTransport.Limits(readTimeoutMillis = 100, callTimeoutMillis = 200)) {
            assertEquals(OpenMeteoTransport.Result.Timeout, it)
        }
        withServer(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE), OpenMeteoTransport.Limits(readTimeoutMillis = 5_000, callTimeoutMillis = 150)) {
            assertEquals(OpenMeteoTransport.Result.Timeout, it)
        }
        withServer(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)) {
            assertEquals(OpenMeteoTransport.Result.Connectivity, it)
        }
    }

    @Test fun cancellationPropagates() = runBlocking {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().url(server.url("/v1/forecast")).build())
            }.build()
            val job = async { OpenMeteoTransport(client).fetch(location) }
            server.takeRequest(2, TimeUnit.SECONDS)
            job.cancel()
            try { job.await(); fail("Expected cancellation") } catch (_: CancellationException) { }
        }
    }
}
