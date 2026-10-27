package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.util.Collections
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

/** One captured wall-clock instant is carried through the entire load operation. */
data class CatalogLoadRequest(
    val location: CatalogLocation,
    val datasetId: DatasetId,
    val asOf: Instant,
) {
    companion object {
        fun atNow(location: CatalogLocation, datasetId: DatasetId, clock: Clock): CatalogLoadRequest =
            CatalogLoadRequest(location, datasetId, clock.instant())
    }
}

enum class CatalogCacheState { FRESH, CACHED }

/** Opaque provider details are descriptive metadata, not a provider DTO or schema selector. */
data class CatalogLoadMetadata(
    val cacheState: CatalogCacheState = CatalogCacheState.FRESH,
    val detail: String? = null,
) {
    init { require(detail == null || detail.isNotBlank()) }
}

typealias CatalogExpectedSupport = Map<LocalDate, Map<CatalogField, ExpectedFieldSupport>>

/** Only usable outcomes carry a catalog. Cancellation remains coroutine control flow. */
sealed interface CatalogLoadResult {
    val metadata: CatalogLoadMetadata

    data class Success(
        val catalog: CanonicalCatalog,
        val expectedSupport: CatalogExpectedSupport,
        override val metadata: CatalogLoadMetadata = CatalogLoadMetadata(),
    ) : CatalogLoadResult

    data class Partial(
        val catalog: CanonicalCatalog,
        val expectedSupport: CatalogExpectedSupport,
        override val metadata: CatalogLoadMetadata = CatalogLoadMetadata(),
    ) : CatalogLoadResult

    data class NoData(val reason: NoDataReason, override val metadata: CatalogLoadMetadata = CatalogLoadMetadata()) : CatalogLoadResult
    data class InvalidRequest(val reason: InvalidRequestReason, override val metadata: CatalogLoadMetadata = CatalogLoadMetadata()) : CatalogLoadResult
    data class TransportFailure(val reason: TransportFailureReason, override val metadata: CatalogLoadMetadata = CatalogLoadMetadata()) : CatalogLoadResult
}

enum class NoDataReason { EMPTY_SOURCE, OUTSIDE_AVAILABLE_HORIZON }
enum class InvalidRequestReason { UNSUPPORTED_LOCATION, UNSUPPORTED_DATASET }
enum class TransportFailureReason { UNAVAILABLE, TIMEOUT, INVALID_RESPONSE }

interface WeatherCatalogSource {
    val origin: CatalogOrigin
    /** Implementations must let coroutine cancellation propagate. */
    suspend fun load(request: CatalogLoadRequest): CatalogLoadResult
}

/** Registry only; it neither selects an active source nor owns an active catalog. */
class CatalogSourceRegistry(sources: Collection<WeatherCatalogSource>) {
    private val byOrigin: Map<CatalogOrigin, WeatherCatalogSource> =
        Collections.unmodifiableMap(sources.associateBy { it.origin }.also { registered ->
            require(registered.size == sources.size) { "Each origin must have at most one source" }
        })

    fun source(origin: CatalogOrigin): WeatherCatalogSource? = byOrigin[origin]

    suspend fun load(origin: CatalogOrigin, request: CatalogLoadRequest): CatalogLoadResult {
        val source = requireNotNull(source(origin)) { "No source registered for $origin" }
        currentCoroutineContext().ensureActive()
        val result = source.load(request)
        currentCoroutineContext().ensureActive()
        val catalog = when (result) {
            is CatalogLoadResult.Success -> result.catalog
            is CatalogLoadResult.Partial -> result.catalog
            else -> null
        }
        if (catalog != null) {
            require(catalog.facts.isNotEmpty()) { "Usable source results require at least one fact" }
            require(catalog.location == request.location && catalog.datasetId == request.datasetId &&
                catalog.asOf == request.asOf && catalog.provenance.origin == source.origin) {
                "Source result identity or provenance differs from request"
            }
            val support = when (result) {
                is CatalogLoadResult.Success -> result.expectedSupport
                is CatalogLoadResult.Partial -> result.expectedSupport
                else -> error("Unreachable")
            }
            require(support.values.any { it.isNotEmpty() }) { "Usable source results require declared field support" }
            val window = CatalogWindow.create(catalog, support)
            require(window is CatalogWindowResult.Success) { "Source result has invalid expected support or facts" }
            val complete = window.window.days.all { day ->
                day.fieldCoverage.values.all { it == CoverageStatus.COMPLETE }
            }
            require((result is CatalogLoadResult.Success) == complete) {
                "Success must have complete declared support; incomplete support requires Partial"
            }
        }
        return result
    }
}

/** Pure candidate gate; callers retain ownership of their active catalog. */
fun acceptedCatalog(current: CanonicalCatalog?, result: CatalogLoadResult): CanonicalCatalog? = when (result) {
    is CatalogLoadResult.Success -> result.catalog
    is CatalogLoadResult.Partial -> result.catalog
    else -> current
}
