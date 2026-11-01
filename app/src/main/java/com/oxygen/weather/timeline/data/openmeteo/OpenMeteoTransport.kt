package com.oxygen.weather.timeline.data.openmeteo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.coroutines.resume

/** Transport only. WX.13 owns weather interpretation and catalog publication. */
class OpenMeteoTransport(
    client: OkHttpClient,
    private val limits: Limits = Limits(),
    private val mapper: ObjectMapper = ObjectMapper(),
) {
    data class Limits(
        val connectTimeoutMillis: Long = 5_000,
        val readTimeoutMillis: Long = 15_000,
        val callTimeoutMillis: Long = 30_000,
        val maxResponseBytes: Int = 2 * 1024 * 1024,
    ) {
        init {
            require(connectTimeoutMillis > 0 && readTimeoutMillis > 0 && callTimeoutMillis > 0)
            require(maxResponseBytes in 1 until Int.MAX_VALUE)
        }
    }

    sealed interface Result {
        data class Success(val payload: JsonNode) : Result
        data class HttpError(val status: Int, val reason: String?) : Result
        data object Timeout : Result
        data object Connectivity : Result
        data object InvalidResponse : Result
        data object OversizedResponse : Result
    }

    private val http = client.newBuilder()
        .connectTimeout(limits.connectTimeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
        .readTimeout(limits.readTimeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
        .callTimeout(limits.callTimeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    fun request(location: CatalogLocation): Request {
        val url = ENDPOINT.toHttpUrl().newBuilder()
            .addQueryParameter("latitude", location.coordinates.latitudeDegrees.toString())
            .addQueryParameter("longitude", location.coordinates.longitudeDegrees.toString())
            .addQueryParameter("timezone", location.timeZone.id)
            .addQueryParameter("past_days", "30")
            .addQueryParameter("forecast_days", "15")
            .addQueryParameter("timeformat", "unixtime")
            .addQueryParameter("temperature_unit", "celsius")
            .addQueryParameter("wind_speed_unit", "kmh")
            .addQueryParameter("precipitation_unit", "mm")
            .addQueryParameter("hourly", HOURLY.joinToString(","))
            .addQueryParameter("daily", DAILY.joinToString(","))
            .build()
        return Request.Builder().url(url).get().build()
    }

    suspend fun fetch(location: CatalogLocation): Result {
        val call = http.newCall(request(location))
        val response = try {
            suspendCancellableCoroutine<Response> { continuation ->
                continuation.invokeOnCancellation { call.cancel() }
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        if (continuation.isActive) continuation.resumeWith(kotlin.Result.failure(e))
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (continuation.isActive) continuation.resume(response) { _, value, _ -> value.close() } else response.close()
                    }
                })
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            return if (e is SocketTimeoutException || e is InterruptedIOException) Result.Timeout else Result.Connectivity
        }
        return try {
            withContext(Dispatchers.IO) { readResponse(response) }
        } catch (e: CancellationException) {
            withContext(NonCancellable + Dispatchers.IO) { response.close() }
            throw e
        } catch (e: IOException) {
            if (e is SocketTimeoutException || e is InterruptedIOException) Result.Timeout else Result.Connectivity
        }
    }

    /** Response body I/O and JSON parsing must not resume on the caller's (often Main) thread. */
    private fun readResponse(response: Response): Result {
        return response.use {
            val body = it.body ?: return Result.InvalidResponse
            val bytes = body.byteStream().use { input ->
                val output = ByteArrayOutputStream()
                val buffer = ByteArray(8192)
                while (true) {
                    val count = input.read(buffer, 0, minOf(buffer.size, limits.maxResponseBytes + 1 - output.size()))
                    if (count < 0) break
                    output.write(buffer, 0, count)
                    if (output.size() > limits.maxResponseBytes) return Result.OversizedResponse
                }
                output.toByteArray()
            }
            val root = try { mapper.readTree(bytes) } catch (_: Exception) { null }
            if (!it.isSuccessful) {
                val reason = root?.path("reason")?.takeIf { n -> n.isTextual }?.asText()
                    ?.filter { c -> c.code in 32..126 }?.take(200)
                return Result.HttpError(it.code, reason)
            }
            if (root == null || !root.isObject || root.path("error").asBoolean(false) ||
                !root.path("hourly").isObject || !root.path("hourly").path("time").isArray ||
                !root.path("daily").isObject || !root.path("daily").path("time").isArray ||
                !root.path("timezone").isTextual) return Result.InvalidResponse
            return Result.Success(root)
        }
    }

    companion object {
        private const val ENDPOINT = "https://api.open-meteo.com/v1/forecast"
        val HOURLY = listOf("temperature_2m", "relative_humidity_2m", "precipitation_probability", "precipitation", "rain", "snowfall", "snow_depth", "weather_code", "pressure_msl", "surface_pressure", "cloud_cover", "visibility", "wind_speed_10m", "wind_direction_10m", "wind_gusts_10m")
        val DAILY = listOf("weather_code", "temperature_2m_max", "temperature_2m_min", "precipitation_sum", "rain_sum", "snowfall_sum", "precipitation_probability_max", "sunrise", "sunset")
    }
}
