package com.oxygen.weather.timeline.data.catalog

import java.time.Duration
import java.time.Instant
import java.time.ZoneId

data class CatalogRefreshPolicy(
    val freshFor: Duration = Duration.ofMinutes(60),
    val eagerRefreshAfter: Duration = Duration.ofMinutes(15),
    val liveSnapshotLimit: Int = 2,
) {
    init {
        require(!freshFor.isNegative && !freshFor.isZero)
        require(!eagerRefreshAfter.isNegative && !eagerRefreshAfter.isZero)
        require(liveSnapshotLimit >= 1)
    }

    fun freshness(storedAt: Instant?, now: Instant): CatalogFreshness =
        if (storedAt != null && Duration.between(storedAt, now).let { !it.isNegative && it <= freshFor }) CatalogFreshness.FRESH
        else CatalogFreshness.STALE

    fun shouldRefresh(storedAt: Instant?, cachedAsOf: Instant?, locationZone: ZoneId, now: Instant, force: Boolean): Boolean {
        if (force || storedAt == null || cachedAsOf == null) return true
        if (cachedAsOf.atZone(locationZone).toLocalDate() != now.atZone(locationZone).toLocalDate()) return true
        return Duration.between(storedAt, now) > eagerRefreshAfter || Duration.between(storedAt, now).isNegative
    }
}

enum class CatalogFreshness { FRESH, STALE }
enum class CatalogDelivery { LIVE, CACHE, NONE }
enum class CatalogRefreshStatus { SKIPPED, SUCCESS, PARTIAL, NO_DATA, INVALID_REQUEST, TRANSPORT_FAILURE }
enum class CatalogPersistenceStatus { NOT_ATTEMPTED, PERSISTED, SUPERSEDED, WRITE_FAILURE }

data class CatalogRepositoryResult(
    val recording: CatalogRecording?,
    val delivery: CatalogDelivery,
    val freshness: CatalogFreshness?,
    val refresh: CatalogRefreshStatus,
    val persistence: CatalogPersistenceStatus,
    val sourceOutcome: CatalogLoadResult? = null,
    val cacheInvalidity: List<String> = emptyList(),
    val storedAt: Instant? = null,
) {
    init { require((recording == null) == (delivery == CatalogDelivery.NONE)); require(recording != null || freshness == null) }
}

/** Caller-driven whole-catalog refresh. It owns neither active selection nor background scheduling. */
class LiveCatalogRepository(
    private val sources: CatalogSourceRegistry,
    private val storage: CatalogDatasetStorage,
    private val clock: java.time.Clock,
    private val policy: CatalogRefreshPolicy = CatalogRefreshPolicy(),
) {
    suspend fun load(location: CatalogLocation, datasetId: DatasetId, forceRefresh: Boolean = false): CatalogRepositoryResult {
        val operationInstant = clock.instant()
        val cachedResult = storage.load(location, CatalogStorageNamespace.LIVE, datasetId)
        val cached = (cachedResult as? StoredCatalogResult.Found)
        val invalidity = when (cachedResult) {
            is StoredCatalogResult.Invalid -> cachedResult.reasons
            is StoredCatalogResult.Found -> cachedResult.invalidCandidates
            StoredCatalogResult.Missing -> emptyList()
        }
        val cachedRecording = cached?.recording
        val shouldRefresh = policy.shouldRefresh(cached?.storedAt, cachedRecording?.catalog?.asOf, location.timeZone, operationInstant, forceRefresh)
        if (!shouldRefresh) {
            val cachedSnapshot = requireNotNull(cached)
            return CatalogRepositoryResult(cachedSnapshot.recording, CatalogDelivery.CACHE,
                policy.freshness(cachedSnapshot.storedAt, operationInstant), CatalogRefreshStatus.SKIPPED,
                CatalogPersistenceStatus.NOT_ATTEMPTED, cacheInvalidity = invalidity, storedAt = cachedSnapshot.storedAt)
        }

        val request = CatalogLoadRequest(location, datasetId, operationInstant)
        val sourceOutcome = sources.load(CatalogOrigin.LIVE, request)
        val usable = when (sourceOutcome) {
            is CatalogLoadResult.Success -> sourceOutcome.catalog to sourceOutcome.expectedSupport
            is CatalogLoadResult.Partial -> sourceOutcome.catalog to sourceOutcome.expectedSupport
            else -> null
        }
        if (usable == null) {
            return if (cachedRecording != null) CatalogRepositoryResult(cachedRecording, CatalogDelivery.CACHE,
                CatalogFreshness.STALE, statusOf(sourceOutcome), CatalogPersistenceStatus.NOT_ATTEMPTED,
                sourceOutcome, invalidity, requireNotNull(cached).storedAt)
            else CatalogRepositoryResult(null, CatalogDelivery.NONE, null, statusOf(sourceOutcome),
                CatalogPersistenceStatus.NOT_ATTEMPTED, sourceOutcome, invalidity)
        }

        val recording = CatalogRecording(usable.first, usable.second,
            (sourceOutcome as? CatalogLoadResult.Success)?.dailyExpectedSupport
                ?: (sourceOutcome as CatalogLoadResult.Partial).dailyExpectedSupport)
        // Retrieval time is captured when a usable whole-catalog result arrives, independently of asOf.
        val retrievedAt = clock.instant()
        val persistence = try {
            when (storage.save(CatalogStorageNamespace.LIVE, recording, retrievedAt, policy.liveSnapshotLimit)) {
                CatalogPersistResult.PERSISTED -> CatalogPersistenceStatus.PERSISTED
                CatalogPersistResult.SUPERSEDED -> CatalogPersistenceStatus.SUPERSEDED
            }
        } catch (cancelled: kotlinx.coroutines.CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            CatalogPersistenceStatus.WRITE_FAILURE
        }
        return CatalogRepositoryResult(recording, CatalogDelivery.LIVE, CatalogFreshness.FRESH,
            statusOf(sourceOutcome), persistence, sourceOutcome, invalidity, retrievedAt)
    }

    private fun statusOf(result: CatalogLoadResult): CatalogRefreshStatus = when (result) {
        is CatalogLoadResult.Success -> CatalogRefreshStatus.SUCCESS
        is CatalogLoadResult.Partial -> CatalogRefreshStatus.PARTIAL
        is CatalogLoadResult.NoData -> CatalogRefreshStatus.NO_DATA
        is CatalogLoadResult.InvalidRequest -> CatalogRefreshStatus.INVALID_REQUEST
        is CatalogLoadResult.TransportFailure -> CatalogRefreshStatus.TRANSPORT_FAILURE
    }
}
