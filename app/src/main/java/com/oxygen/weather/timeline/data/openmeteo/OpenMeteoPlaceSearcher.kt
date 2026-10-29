package com.oxygen.weather.timeline.data.openmeteo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InterruptedIOException
import java.net.URI
import java.net.SocketTimeoutException
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.coroutines.resume

/** Open-Meteo geocoding adapter. It is deliberately not wired to catalog loading or UI state. */
class OpenMeteoPlaceSearcher(
    client: OkHttpClient = OkHttpClient(),
    private val endpoint: String = ENDPOINT,
    private val limits: Limits = Limits(),
    private val mapper: ObjectMapper = ObjectMapper(),
) : PlaceSearcher {
    data class Limits(
        val connectTimeoutMillis: Long = 5_000,
        val readTimeoutMillis: Long = 15_000,
        val callTimeoutMillis: Long = 30_000,
        val maxResponseBytes: Int = 512 * 1024,
        val maxQueryCodePoints: Int = 160,
        val resultCount: Int = 10,
    ) {
        init {
            require(connectTimeoutMillis > 0 && readTimeoutMillis > 0 && callTimeoutMillis > 0)
            require(maxResponseBytes in 1 until Int.MAX_VALUE && maxQueryCodePoints >= 2 && resultCount in 1..100)
        }
    }

    private sealed interface FetchResult {
        data class Success(val payload: ByteArray) : FetchResult
        data class HttpFailure(val status: Int, val reason: String?) : FetchResult
        data object Timeout : FetchResult
        data object Connectivity : FetchResult
        data object InvalidResponse : FetchResult
        data object Oversized : FetchResult
    }

    private val http = client.newBuilder()
        .connectTimeout(limits.connectTimeoutMillis, TimeUnit.MILLISECONDS)
        .readTimeout(limits.readTimeoutMillis, TimeUnit.MILLISECONDS)
        .callTimeout(limits.callTimeoutMillis, TimeUnit.MILLISECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    fun request(query: String): Request {
        val url = endpoint.toHttpUrl().newBuilder()
            .addQueryParameter("name", query)
            .addQueryParameter("count", limits.resultCount.toString())
            .addQueryParameter("language", "en")
            .build()
        return Request.Builder().url(url).get().build()
    }

    override suspend fun search(query: String): PlaceSearchResult {
        val normalized = query.trim()
        val codePointCount = normalized.codePointCount(0, normalized.length)
        if (codePointCount < 2) return PlaceSearchResult.NoMatches
        if (codePointCount > limits.maxQueryCodePoints) {
            return PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_QUERY)
        }
        return when (val response = fetch(normalized)) {
            is FetchResult.Success -> map(response.payload)
            is FetchResult.HttpFailure -> PlaceSearchResult.Failure(
                PlaceSearchResult.Failure.Reason.HTTP,
                listOf(response.status.toString(), response.reason).filterNotNull().joinToString(": "),
            )
            FetchResult.Timeout -> PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.TIMEOUT)
            FetchResult.Connectivity -> PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.CONNECTIVITY)
            FetchResult.InvalidResponse -> PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE)
            FetchResult.Oversized -> PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.OVERSIZED_RESPONSE)
        }
    }

    private suspend fun fetch(query: String): FetchResult {
        val call = http.newCall(request(query))
        val response = try {
            suspendCancellableCoroutine<Response> { continuation ->
                continuation.invokeOnCancellation { call.cancel() }
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        if (continuation.isActive) continuation.resumeWith(Result.failure(e))
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (continuation.isActive) continuation.resume(response) { _, value, _ -> value.close() }
                        else response.close()
                    }
                })
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            return if (e is SocketTimeoutException || e is InterruptedIOException) FetchResult.Timeout else FetchResult.Connectivity
        }
        response.use {
            val bytes = try {
                val body = it.body ?: return FetchResult.InvalidResponse
                body.byteStream().use { input ->
                    val output = ByteArrayOutputStream()
                    val buffer = ByteArray(8192)
                    while (true) {
                        val count = input.read(buffer, 0, minOf(buffer.size, limits.maxResponseBytes + 1 - output.size()))
                        if (count < 0) break
                        output.write(buffer, 0, count)
                        if (output.size() > limits.maxResponseBytes) return FetchResult.Oversized
                    }
                    output.toByteArray()
                }
            } catch (e: IOException) {
                return if (e is SocketTimeoutException || e is InterruptedIOException) FetchResult.Timeout else FetchResult.Connectivity
            }
            val root = try { mapper.readTree(bytes) } catch (_: Exception) { null }
            if (!it.isSuccessful) {
                val reason = root?.path("reason")?.takeIf(JsonNode::isTextual)?.asText()
                    ?.filter { char -> char.code in 32..126 }?.take(160)
                return FetchResult.HttpFailure(it.code, reason)
            }
            return if (root == null) FetchResult.InvalidResponse else FetchResult.Success(bytes)
        }
    }

    private fun map(bytes: ByteArray): PlaceSearchResult {
        val root = try { mapper.readTree(bytes) } catch (_: Exception) { null }
            ?: return PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE)
        if (!root.isObject || root.path("error").asBoolean(false)) {
            return PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE)
        }
        val results = root.get("results")
            ?: return PlaceSearchResult.NoMatches
        if (!results.isArray) return PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE)
        if (results.isEmpty) return PlaceSearchResult.NoMatches

        val candidates = mutableListOf<PlaceSearchCandidate>()
        var rejected = 0
        results.forEach { row ->
            val candidate = parseCandidate(row)
            if (candidate == null) rejected++ else candidates += candidate
        }
        if (candidates.isEmpty()) return PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.INVALID_RESPONSE)
        return PlaceSearchResult.Matches(candidates, rejected)
    }

    private fun parseCandidate(row: JsonNode): PlaceSearchCandidate? = try {
        if (!row.isObject) return null
        val name = row.textOrNull("name")?.takeIf(String::isNotBlank) ?: return null
        val country = row.textOrNull("country")?.takeIf(String::isNotBlank)
        val countryCode = row.textOrNull("country_code")?.takeIf(String::isNotBlank)
        if (country == null && countryCode == null) return null
        val latitude = row.numberOrNull("latitude") ?: return null
        val longitude = row.numberOrNull("longitude") ?: return null
        val timezoneText = row.textOrNull("timezone")?.takeIf(String::isNotBlank) ?: return null
        val timezone = ZoneId.of(timezoneText)
        if (timezone is ZoneOffset || timezone.id !in ZoneId.getAvailableZoneIds()) return null
        val adminAreas = listOf("admin1", "admin2", "admin3", "admin4")
            .mapNotNull { key -> row.textOrNull(key)?.takeIf(String::isNotBlank) }
        val reference = row.get("id")?.takeIf { it.isIntegralNumber }?.asText()
        PlaceSearchCandidate(
            name = name,
            countryName = country,
            countryCode = countryCode,
            administrativeAreas = adminAreas,
            coordinates = GeoCoordinates(latitude, longitude),
            timeZone = timezone,
            sourceReference = reference,
            attribution = ATTRIBUTION,
        )
    } catch (_: Exception) {
        null
    }

    private fun JsonNode.textOrNull(field: String): String? = get(field)?.takeIf(JsonNode::isTextual)?.asText()
    private fun JsonNode.numberOrNull(field: String): Double? = get(field)?.takeIf(JsonNode::isNumber)?.asDouble()

    companion object {
        const val ENDPOINT = "https://geocoding-api.open-meteo.com/v1/search"
        val ATTRIBUTION = PlaceSearchAttribution(
            sourceName = "Location data by GeoNames via Open-Meteo",
            sourceUrl = URI("https://open-meteo.com/"),
            licenseName = "Creative Commons Attribution 4.0 International (CC BY 4.0)",
            licenseUrl = URI("https://creativecommons.org/licenses/by/4.0/"),
            changesMadeNotice = "Display label combines the returned place, administrative, and country names.",
        )
    }
}
