package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Test

class CatalogActivationCoordinatorTest {
    private val time = Instant.parse("2026-03-08T15:00:00Z")
    private val a = CatalogLocation(LocationId("a"), GeoCoordinates(41.0, -87.0), ZoneId.of("America/Chicago"))
    private val b = CatalogLocation(LocationId("b"), GeoCoordinates(40.0, -74.0), ZoneId.of("America/New_York"))
    private val dataset = DatasetId("forecast-44d")

    private class FixedClock(private val value: Instant) : Clock() {
        override fun getZone() = ZoneOffset.UTC
        override fun withZone(zone: ZoneId): Clock = this
        override fun instant() = value
    }

    private class MemoryDao : CatalogDatasetDao {
        private val rows = mutableListOf<CatalogDatasetRow>()
        override suspend fun replace(row: CatalogDatasetRow) {
            rows.removeAll { it.locationId == row.locationId && it.storageNamespace == row.storageNamespace && it.datasetId == row.datasetId && it.asOfEpochSecond == row.asOfEpochSecond && it.asOfNano == row.asOfNano }
            rows += row
        }
        override suspend fun findCandidates(locationId: String, namespace: String, datasetId: String) =
            rows.filter { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }
                .sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun history(locationId: String, namespace: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace }
        override suspend fun liveRetentionOrder(locationId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == "LIVE" }
            .sortedByDescending { it.storedAtEpochSecond ?: Long.MIN_VALUE }
        override suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int =
            rows.removeAll { it.locationId == locationId && it.storageNamespace == "LIVE" && it.datasetId == datasetId && it.asOfEpochSecond == asOfSecond && it.asOfNano == asOfNano }.let { if (it) 1 else 0 }
        override suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int =
            rows.removeAll { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.let { if (it) 1 else 0 }
        override suspend fun deleteLocation(locationId: String): Int = rows.removeAll { it.locationId == locationId }.let { if (it) 1 else 0 }
        override suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int): Boolean {
            replace(row)
            liveRetentionOrder(row.locationId).drop(retentionLimit).forEach { deleteWindow(it.locationId, it.datasetId, it.asOfEpochSecond, it.asOfNano) }
            return true
        }
    }

    private class FakeSource(
        override val origin: CatalogOrigin,
        var respond: suspend (CatalogLoadRequest) -> CatalogLoadResult,
    ) : WeatherCatalogSource {
        val requests = mutableListOf<CatalogLoadRequest>()
        override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
            requests += request
            return respond(request)
        }
    }

    private class FakeRestorer(var selection: SavedLocationResult = SavedLocationResult.Selected(null)) : StartupLocationRestorer {
        override suspend fun restore() = selection
    }

    private suspend fun result(request: CatalogLoadRequest, origin: CatalogOrigin, originalOrigin: CatalogOrigin? = null): CatalogLoadResult {
        val base = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
        val provenanceOrigin = if (origin == CatalogOrigin.REPLAY) CatalogOrigin.REPLAY else origin
        val catalog = CanonicalCatalog(request.location, request.datasetId,
            CatalogProvenance(provenanceOrigin,
                if (provenanceOrigin == CatalogOrigin.SIMULATED) CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT else CatalogProductKind.FORECAST,
                sourceId = if (origin == CatalogOrigin.REPLAY) "live-test" else "${origin.name.lowercase()}-test",
                productId = "whole", issuedAt = request.asOf),
            request.asOf, base.catalog.facts.map { fact ->
                fact.withTemperature(when (origin) {
                    CatalogOrigin.LIVE -> 20.0
                    CatalogOrigin.SIMULATED -> 10.0
                    CatalogOrigin.REPLAY -> 5.0
                })
            })
        return CatalogLoadResult.Success(catalog, base.expectedSupport,
            metadata = CatalogLoadMetadata(replayOriginalOrigin = originalOrigin))
    }

    private fun harness(
        dao: MemoryDao = MemoryDao(),
        live: FakeSource = FakeSource(CatalogOrigin.LIVE) { result(it, CatalogOrigin.LIVE) },
        simulated: FakeSource = FakeSource(CatalogOrigin.SIMULATED) { result(it, CatalogOrigin.SIMULATED) },
        replay: FakeSource = FakeSource(CatalogOrigin.REPLAY) { result(it, CatalogOrigin.REPLAY, CatalogOrigin.LIVE) },
        restorer: FakeRestorer = FakeRestorer(),
        clockInstant: Instant = time,
    ): Harness {
        val storage = CatalogDatasetStorage(dao)
        val sources = CatalogSourceRegistry(listOf(live, simulated, replay))
        val clock = FixedClock(clockInstant)
        val liveRepository = LiveCatalogRepository(sources, storage, clock)
        return Harness(dao, storage, live, simulated, replay, restorer, clock, liveRepository, sources)
    }

    private data class Harness(
        val dao: MemoryDao,
        val storage: CatalogDatasetStorage,
        val live: FakeSource,
        val simulated: FakeSource,
        val replay: FakeSource,
        val restorer: FakeRestorer,
        val clock: Clock,
        val liveRepository: LiveCatalogRepository,
        val sources: CatalogSourceRegistry,
    ) {
        fun coordinator(scope: kotlinx.coroutines.CoroutineScope) = CatalogActivationCoordinator(
            scope, restorer, storage, liveRepository, sources, clock,
        )
    }

    private suspend fun awaitState(predicate: () -> Boolean) = withTimeout(3_000) {
        while (!predicate()) delay(1)
    }

    private fun CanonicalWeatherFacts.withTemperature(value: Double) = CanonicalWeatherFacts(
        validTime = validTime,
        conditions = conditions,
        phenomena = phenomena,
        temperatureC = value,
        precipitationProbabilityPercent = precipitationProbabilityPercent,
        thunderstormProbabilityPercent = thunderstormProbabilityPercent,
        relativeHumidityPercent = relativeHumidityPercent,
        cloudCoverPercent = cloudCoverPercent,
        windSpeedKph = windSpeedKph,
        windGustKph = windGustKph,
        windDirectionDegrees = windDirectionDegrees,
        rainAmountMillimeters = rainAmountMillimeters,
        totalPrecipitationMillimeters = totalPrecipitationMillimeters,
        precipitationRate = precipitationRate,
        snowfallCentimeters = snowfallCentimeters,
        snowDepthCentimeters = snowDepthCentimeters,
        surfacePressureHpa = surfacePressureHpa,
        meanSeaLevelPressureHpa = meanSeaLevelPressureHpa,
        visibilityKm = visibilityKm,
    )

    @Test fun aToBToARaceOnlyPublishesNewestMatchingLocationRequest() = runBlocking {
        val firstA = CompletableDeferred<CatalogLoadResult>()
        var aCalls = 0
        val live = FakeSource(CatalogOrigin.LIVE) { request ->
            if (request.location == a && ++aCalls == 1) withContext(NonCancellable) { firstA.await() }
            else result(request, CatalogOrigin.LIVE)
        }
        val h = harness(live = live)
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { live.requests.size == 1 }
        coordinator.committedLocationSelection(b)
        awaitState { coordinator.state.value.active?.location == b }
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active?.location == a && coordinator.state.value.active?.viewGeneration == 2L }
        firstA.complete(result(live.requests.first(), CatalogOrigin.LIVE))
        delay(5)
        assertEquals(a, coordinator.state.value.active?.location)
        assertEquals(2L, coordinator.state.value.active?.viewGeneration)
    }

    @Test fun repeatedSameLocationRequestRejectsCanceledLateCompletion() = runBlocking {
        val firstResult = CompletableDeferred<CatalogLoadResult>()
        var calls = 0
        val live = FakeSource(CatalogOrigin.LIVE) { request ->
            if (++calls == 1) withContext(NonCancellable) { firstResult.await() }
            else result(request, CatalogOrigin.LIVE)
        }
        val coordinator = harness(live = live).coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { live.requests.size == 1 }
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null && live.requests.size == 2 }
        val published = coordinator.state.value.active
        firstResult.complete(result(live.requests.first(), CatalogOrigin.LIVE))
        delay(5)
        assertEquals(published, coordinator.state.value.active)
        assertEquals(1L, coordinator.state.value.active?.viewGeneration)
    }

    @Test fun originSwitchesUseIsolatedWholeRecordingsAndRetainLiveCache() = runBlocking {
        val h = harness()
        val otherLocationDataset = DatasetId("other-location")
        val otherLocationLoad = CatalogLoadRequest(b, otherLocationDataset, time)
        val otherLocationResult = result(otherLocationLoad, CatalogOrigin.SIMULATED) as CatalogLoadResult.Success
        val otherLocationRecording = CatalogRecording(
            otherLocationResult.catalog,
            otherLocationResult.expectedSupport,
            otherLocationResult.dailyExpectedSupport,
        )
        h.storage.save(CatalogStorageNamespace.SIMULATED, otherLocationRecording)
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }
        val selected = coordinator.state.value.active!!.selectedInstant
        assertEquals("live-test", coordinator.state.value.active?.recording?.catalog?.provenance?.sourceId)
        assertEquals(20.0, coordinator.state.value.active!!.recording.catalog.facts.first().temperatureC!!, 0.0)
        coordinator.requestCatalog(CatalogOrigin.SIMULATED, DatasetId("sim-v1"))
        awaitState { coordinator.state.value.active?.origin == CatalogOrigin.SIMULATED }
        assertEquals("simulated-test", coordinator.state.value.active?.recording?.catalog?.provenance?.sourceId)
        assertEquals(10.0, coordinator.state.value.active!!.recording.catalog.facts.first().temperatureC!!, 0.0)
        assertEquals(selected, coordinator.state.value.active?.selectedInstant)
        coordinator.requestCatalog(CatalogOrigin.REPLAY, DatasetId("recording-v1"), DatasetId("recording-v1"))
        awaitState { coordinator.state.value.active?.origin == CatalogOrigin.REPLAY }

        val active = coordinator.state.value.active!!
        assertEquals(CatalogOrigin.REPLAY, active.recording.catalog.provenance.origin)
        assertEquals("live-test", active.recording.catalog.provenance.sourceId)
        assertEquals(5.0, active.recording.catalog.facts.first().temperatureC!!, 0.0)
        assertEquals(a, active.location)
        assertEquals(CatalogOrigin.LIVE,
            (h.storage.load(a, CatalogStorageNamespace.REPLAY, DatasetId("recording-v1")) as StoredCatalogResult.Found)
                .recording.catalog.provenance.origin)
        assertTrue(h.storage.load(a, CatalogStorageNamespace.LIVE, dataset) is StoredCatalogResult.Found)
        assertTrue(h.storage.load(a, CatalogStorageNamespace.SIMULATED, DatasetId("sim-v1")) is StoredCatalogResult.Found)
        val unchangedOtherLocation = (h.storage.load(b, CatalogStorageNamespace.SIMULATED, otherLocationDataset) as StoredCatalogResult.Found).recording
        assertEquals(otherLocationRecording.catalog, unchangedOtherLocation.catalog)
        assertEquals(otherLocationRecording.expectedSupport.keys, unchangedOtherLocation.expectedSupport.keys)
        coordinator.requestCatalog(CatalogOrigin.LIVE, dataset)
        awaitState { coordinator.state.value.active?.origin == CatalogOrigin.LIVE && coordinator.state.value.active?.viewGeneration == 4L }
        assertEquals(CatalogActivationDelivery.CACHE, coordinator.state.value.active?.delivery)
        assertEquals(20.0, coordinator.state.value.active!!.recording.catalog.facts.first().temperatureC!!, 0.0)
        assertEquals(1, h.live.requests.size)
    }

    @Test fun failedReplacementPreservesActiveAndRefreshAdvancesGeneration() = runBlocking {
        val live = FakeSource(CatalogOrigin.LIVE) { result(it, CatalogOrigin.LIVE) }
        val simulated = FakeSource(CatalogOrigin.SIMULATED) { CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE) }
        val h = harness(live = live, simulated = simulated)
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }
        coordinator.refreshCurrent()
        awaitState { !coordinator.state.value.loading && coordinator.state.value.active?.viewGeneration == 2L }
        val previous = coordinator.state.value.active
        coordinator.requestCatalog(CatalogOrigin.SIMULATED, DatasetId("missing"))
        awaitState { !coordinator.state.value.loading && coordinator.state.value.lastError != null }
        assertEquals(previous, coordinator.state.value.active)
        assertNotNull(coordinator.state.value.lastError)

        simulated.respond = { request -> result(request.copy(location = b), CatalogOrigin.SIMULATED) }
        coordinator.requestCatalog(CatalogOrigin.SIMULATED, DatasetId("invalid-identity"))
        awaitState { !coordinator.state.value.loading && coordinator.state.value.lastError != null }
        assertEquals(previous, coordinator.state.value.active)
    }

    @Test fun startupRestorationRunsWithoutSelectorAndUsesCacheAfterRecreation() = runBlocking {
        val dao = MemoryDao()
        val saved = FakeRestorer(SavedLocationResult.Selected(null))
        val first = harness(dao = dao, restorer = saved)
        val request = CatalogLoadRequest(a, dataset, time)
        val recording = (result(request, CatalogOrigin.LIVE) as CatalogLoadResult.Success).let {
            CatalogRecording(it.catalog, it.expectedSupport, it.dailyExpectedSupport)
        }
        first.storage.save(CatalogStorageNamespace.LIVE, recording, time)
        saved.selection = SavedLocationResult.Selected(SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        )))
        val coordinator = first.coordinator(this)
        coordinator.restoreStartup()
        awaitState { coordinator.state.value.active != null }
        assertEquals(CatalogActivationDelivery.CACHE, coordinator.state.value.active?.delivery)
        assertTrue(first.live.requests.isEmpty())
        assertEquals(a, coordinator.state.value.active?.location)
    }

    @Test fun exactSelectionAtSampleTieAndReplayIdentityIsExplicit() = runBlocking {
        val tieInstant = Instant.parse("2026-03-08T20:00:00Z")
        val h = harness(clockInstant = tieInstant)
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }
        val facts = coordinator.state.value.active!!.recording.catalog.facts.map { it.validTime }.distinct().sorted()
        val earlier = facts.last { it.isBefore(tieInstant) }
        val later = facts.first { it.isAfter(tieInstant) }
        assertEquals(6L, java.time.Duration.between(earlier, tieInstant).toHours())
        assertEquals(6L, java.time.Duration.between(tieInstant, later).toHours())
        assertEquals(tieInstant, coordinator.state.value.active?.selectedInstant)
        try {
            CatalogActivationRequest(a, CatalogOrigin.REPLAY, DatasetId("r"))
            fail("Replay must name its recording")
        } catch (_: IllegalArgumentException) { }
    }

}
