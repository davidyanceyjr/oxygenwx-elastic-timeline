package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class LiveCatalogRepositoryTest {
    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))
    private val dataset = DatasetId("canonical")

    private class MutableClock(var instant: Instant) : Clock() {
        override fun getZone() = ZoneOffset.UTC
        override fun withZone(zone: ZoneId): Clock = this
        override fun instant() = instant
    }

    private class MemoryDao : CatalogDatasetDao {
        val rows = mutableListOf<CatalogDatasetRow>()
        var failWrite = false
        override suspend fun replace(row: CatalogDatasetRow) {
            if (failWrite) { failWrite = false; error("injected storage failure") }
            rows.removeAll { it.locationId == row.locationId && it.storageNamespace == row.storageNamespace && it.datasetId == row.datasetId && it.asOfEpochSecond == row.asOfEpochSecond && it.asOfNano == row.asOfNano }
            rows += row
        }
        override suspend fun findCandidates(locationId: String, namespace: String, datasetId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun history(locationId: String, namespace: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace }.sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun liveRetentionOrder(locationId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == "LIVE" }.sortedWith(compareByDescending<CatalogDatasetRow> { it.storedAtEpochSecond ?: Long.MIN_VALUE }.thenByDescending { it.storedAtNano ?: 0 }.thenByDescending { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int = rows.removeAll { it.locationId == locationId && it.storageNamespace == "LIVE" && it.datasetId == datasetId && it.asOfEpochSecond == asOfSecond && it.asOfNano == asOfNano }.let { if (it) 1 else 0 }
        override suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int = rows.removeAll { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.let { if (it) 1 else 0 }
        override suspend fun deleteLocation(locationId: String): Int = rows.removeAll { it.locationId == locationId }.let { if (it) 1 else 0 }
        override suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int): Boolean {
            val newest = findCandidates(row.locationId, "LIVE", row.datasetId).firstOrNull()
            if (newest != null && (newest.asOfEpochSecond > row.asOfEpochSecond || newest.asOfEpochSecond == row.asOfEpochSecond && newest.asOfNano > row.asOfNano)) return false
            replace(row)
            liveRetentionOrder(row.locationId).drop(retentionLimit).toList().forEach { deleteWindow(it.locationId, it.datasetId, it.asOfEpochSecond, it.asOfNano) }
            return true
        }
    }

    private class FakeSource(var respond: (CatalogLoadRequest) -> CatalogLoadResult) : WeatherCatalogSource {
        override val origin = CatalogOrigin.LIVE
        val requests = mutableListOf<CatalogLoadRequest>()
        override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
            requests += request
            return respond(request)
        }
    }

    private suspend fun sourceRecording(request: CatalogLoadRequest): CatalogRecording {
        val simulated = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
        val catalog = CanonicalCatalog(request.location, request.datasetId,
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST, "test-source", "whole", request.asOf),
            request.asOf, simulated.catalog.facts)
        return CatalogRecording(catalog, simulated.expectedSupport)
    }

    private fun repository(clock: Clock, dao: MemoryDao, source: FakeSource) = LiveCatalogRepository(
        CatalogSourceRegistry(listOf(source)), CatalogDatasetStorage(dao), clock,
    )

    @Test fun policyUsesExplicitFreshAndEagerRefreshBoundaries() {
        val policy = CatalogRefreshPolicy()
        val now = Instant.parse("2026-03-08T12:00:00Z")
        assertEquals(CatalogFreshness.FRESH, policy.freshness(now.minusSeconds(3600), now))
        assertEquals(CatalogFreshness.STALE, policy.freshness(now.minusSeconds(3601), now))
        assertFalse(policy.shouldRefresh(now.minusSeconds(900), now, location.timeZone, now, false))
        assertTrue(policy.shouldRefresh(now.minusSeconds(901), now, location.timeZone, now, false))
        assertTrue(policy.shouldRefresh(null, now, location.timeZone, now, false))
        assertTrue(policy.shouldRefresh(now, now.minusSeconds(86400), location.timeZone, now, false))
    }

    @Test fun freshCacheSkipsNetworkAndForceRefreshUsesStaleFallbackOnFailure() = runBlocking {
        val start = Instant.parse("2026-03-08T17:00:00Z")
        val clock = MutableClock(start)
        val dao = MemoryDao()
        val source = FakeSource { error("source must not run") }
        val repo = repository(clock, dao, source)
        val cached = sourceRecording(CatalogLoadRequest(location, dataset, start))
        CatalogDatasetStorage(dao).save(CatalogStorageNamespace.LIVE, cached, start)
        clock.instant = start.plusSeconds(600)
        val fast = repo.load(location, dataset)
        assertEquals(CatalogDelivery.CACHE, fast.delivery)
        assertEquals(CatalogFreshness.FRESH, fast.freshness)
        assertEquals(CatalogRefreshStatus.SKIPPED, fast.refresh)
        source.respond = { CatalogLoadResult.TransportFailure(TransportFailureReason.UNAVAILABLE) }
        val forced = repo.load(location, dataset, forceRefresh = true)
        assertEquals(CatalogDelivery.CACHE, forced.delivery)
        assertEquals(CatalogFreshness.STALE, forced.freshness)
        assertEquals(CatalogRefreshStatus.TRANSPORT_FAILURE, forced.refresh)
        assertTrue(forced.sourceOutcome is CatalogLoadResult.TransportFailure)
        assertEquals(start, forced.recording!!.catalog.asOf)
        source.respond = { request -> runBlocking {
            sourceRecording(request).let { CatalogLoadResult.Success(it.catalog, it.expectedSupport) }
        } }
        clock.instant = start.plusSeconds(901)
        val aged = repo.load(location, dataset)
        assertEquals(CatalogDelivery.LIVE, aged.delivery)
        assertEquals(CatalogRefreshStatus.SUCCESS, aged.refresh)
        assertEquals(start.plusSeconds(901), source.requests.last().asOf)
    }

    @Test fun noCacheFailureReturnsNoneAndWriteFailureDoesNotDiscardLiveResult() = runBlocking {
        val now = Instant.parse("2026-03-08T18:00:00Z")
        val clock = MutableClock(now)
        val dao = MemoryDao()
        val source = FakeSource { CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE) }
        val repo = repository(clock, dao, source)
        val missing = repo.load(location, dataset)
        assertEquals(CatalogDelivery.NONE, missing.delivery)
        assertEquals(CatalogRefreshStatus.NO_DATA, missing.refresh)
        source.respond = { request -> runBlocking { sourceRecording(request).let { CatalogLoadResult.Success(it.catalog, it.expectedSupport) } } }
        dao.failWrite = true
        val live = repo.load(location, dataset)
        assertEquals(CatalogDelivery.LIVE, live.delivery)
        assertEquals(CatalogPersistenceStatus.WRITE_FAILURE, live.persistence)
        assertNotNull(live.recording)
    }

    @Test fun freshFetchAndCorruptOrIdentityMismatchedCacheAreReportedTruthfully() = runBlocking {
        val now = Instant.parse("2026-03-08T18:30:00Z")
        val clock = MutableClock(now)
        val dao = MemoryDao()
        val source = FakeSource { request -> runBlocking {
            sourceRecording(request).let { CatalogLoadResult.Success(it.catalog, it.expectedSupport) }
        } }
        val repo = repository(clock, dao, source)
        val fetched = repo.load(location, dataset)
        assertEquals(CatalogDelivery.LIVE, fetched.delivery)
        assertEquals(CatalogFreshness.FRESH, fetched.freshness)
        assertEquals(CatalogPersistenceStatus.PERSISTED, fetched.persistence)
        assertEquals(now, (CatalogDatasetStorage(dao).load(location, CatalogStorageNamespace.LIVE, dataset) as StoredCatalogResult.Found).storedAt)

        val row = dao.rows.single()
        dao.rows[0] = row.copy(payload = "invalid".toByteArray())
        source.respond = { CatalogLoadResult.TransportFailure(TransportFailureReason.UNAVAILABLE) }
        val corrupt = repo.load(location, dataset)
        assertEquals(CatalogDelivery.NONE, corrupt.delivery)
        assertTrue(corrupt.cacheInvalidity.isNotEmpty())

        dao.rows.clear()
        val otherLocation = location.copy(coordinates = GeoCoordinates(42.0, -87.0))
        val incompatible = sourceRecording(CatalogLoadRequest(otherLocation, dataset, now))
        CatalogDatasetStorage(dao).save(CatalogStorageNamespace.LIVE, incompatible, now)
        val mismatched = repo.load(location, dataset)
        assertEquals(CatalogDelivery.NONE, mismatched.delivery)
        assertTrue(mismatched.cacheInvalidity.any { it.contains("requested location") })
    }

    @Test fun midnightRolloverRequestsNewWindowAndKeepsOldAsOfWhenRefreshFails() = runBlocking {
        val beforeMidnight = Instant.parse("2026-03-09T04:50:00Z")
        val clock = MutableClock(beforeMidnight)
        val dao = MemoryDao()
        val source = FakeSource { CatalogLoadResult.TransportFailure(TransportFailureReason.UNAVAILABLE) }
        val old = sourceRecording(CatalogLoadRequest(location, dataset, beforeMidnight))
        CatalogDatasetStorage(dao).save(CatalogStorageNamespace.LIVE, old, beforeMidnight)
        clock.instant = Instant.parse("2026-03-09T05:05:00Z")
        val result = repository(clock, dao, source).load(location, dataset)
        assertEquals(1, source.requests.size)
        assertEquals(clock.instant, source.requests.single().asOf)
        assertEquals(old.catalog.asOf, result.recording!!.catalog.asOf)
        assertEquals(CatalogFreshness.STALE, result.freshness)
    }

    @Test fun partialSourceOutcomeIsUsableAndStoredWithItsRetrievalInstant() = runBlocking {
        val now = Instant.parse("2026-03-08T19:00:00Z")
        val clock = MutableClock(now)
        val dao = MemoryDao()
        val source = FakeSource { request -> runBlocking {
            val recording = sourceRecording(request)
            val support = recording.expectedSupport.toMutableMap()
            val (date, fields) = support.entries.first { it.value[CatalogField.TEMPERATURE_C] is ExpectedFieldSupport.Points }
            val changed = fields.toMutableMap()
            val points = changed.getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points
            changed[CatalogField.TEMPERATURE_C] = ExpectedFieldSupport.Points(points.instants + points.instants.first().plusSeconds(1800))
            support[date] = changed
            CatalogLoadResult.Partial(recording.catalog, support)
        } }
        val result = repository(clock, dao, source).load(location, dataset)
        assertEquals(CatalogDelivery.LIVE, result.delivery)
        assertEquals(CatalogRefreshStatus.PARTIAL, result.refresh)
        assertEquals(CatalogPersistenceStatus.PERSISTED, result.persistence)
        val stored = CatalogDatasetStorage(dao).load(location, CatalogStorageNamespace.LIVE, dataset) as StoredCatalogResult.Found
        assertEquals(now, stored.storedAt)
        assertTrue(result.sourceOutcome is CatalogLoadResult.Partial)
    }
}
