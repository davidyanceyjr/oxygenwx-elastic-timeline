package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Test

open class CatalogCoordinatorTestSupport {
    protected val time = Instant.parse("2026-03-08T15:00:00Z")
    protected val a = CatalogLocation(LocationId("a"), GeoCoordinates(41.0, -87.0), ZoneId.of("America/Chicago"))
    protected val b = CatalogLocation(LocationId("b"), GeoCoordinates(40.0, -74.0), ZoneId.of("America/New_York"))
    protected val dataset = DatasetId("forecast-44d")

    protected class FixedClock(private val value: Instant) : Clock() {
        override fun getZone() = ZoneOffset.UTC
        override fun withZone(zone: ZoneId): Clock = this
        override fun instant() = value
    }

    protected class MemoryDao : CatalogDatasetDao {
        protected val rows = mutableListOf<CatalogDatasetRow>()
        var reads = 0
        var writes = 0
        var beforeFind: suspend () -> Unit = {}
        override suspend fun replace(row: CatalogDatasetRow) {
            writes++
            rows.removeAll { it.locationId == row.locationId && it.storageNamespace == row.storageNamespace && it.datasetId == row.datasetId && it.asOfEpochSecond == row.asOfEpochSecond && it.asOfNano == row.asOfNano }
            rows += row
        }
        override suspend fun findCandidates(locationId: String, namespace: String, datasetId: String): List<CatalogDatasetRow> {
            reads++
            beforeFind()
            return rows.filter { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }
                .sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        }
        override suspend fun history(locationId: String, namespace: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace }
        override suspend fun liveRetentionOrder(locationId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == "LIVE" }
            .sortedByDescending { it.storedAtEpochSecond ?: Long.MIN_VALUE }
        override suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int =
            rows.removeAll { it.locationId == locationId && it.storageNamespace == "LIVE" && it.datasetId == datasetId && it.asOfEpochSecond == asOfSecond && it.asOfNano == asOfNano }.let { if (it) 1 else 0 }
        override suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int =
            rows.removeAll { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.let { if (it) 1 else 0 }
        override suspend fun deleteLocation(locationId: String): Int = rows.removeAll { it.locationId == locationId }.let { if (it) 1 else 0 }
        fun corrupt(locationId: String, namespace: String, datasetId: String) {
            val index = rows.indexOfFirst {
                it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId
            }
            check(index >= 0)
            rows[index] = rows[index].copy(payload = byteArrayOf(0, 1, 2))
        }
        override suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int): Boolean {
            replace(row)
            liveRetentionOrder(row.locationId).drop(retentionLimit).forEach { deleteWindow(it.locationId, it.datasetId, it.asOfEpochSecond, it.asOfNano) }
            return true
        }
    }

    protected class FakeSource(
        override val origin: CatalogOrigin,
        var respond: suspend (CatalogLoadRequest) -> CatalogLoadResult,
    ) : WeatherCatalogSource {
        val requests = mutableListOf<CatalogLoadRequest>()
        override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
            requests += request
            return respond(request)
        }
    }

    protected class FakeRestorer(var selection: SavedLocationResult = SavedLocationResult.Selected(null)) : StartupLocationRestorer {
        override suspend fun restore() = selection
    }

    protected suspend fun result(request: CatalogLoadRequest, origin: CatalogOrigin, originalOrigin: CatalogOrigin? = null): CatalogLoadResult {
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

    protected fun harness(
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

    protected data class Harness(
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

    protected suspend fun awaitState(predicate: () -> Boolean) = withTimeout(3_000) {
        while (!predicate()) delay(1)
    }

    protected fun CanonicalWeatherFacts.withTemperature(value: Double) = CanonicalWeatherFacts(
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

}

class CatalogActivationCoordinatorTest : CatalogCoordinatorTestSupport() {
    @Test fun savedPreviewForAnotherLocationRetainsPublishedSelectionUntilAtomicCommit() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a, "Alpha")
        awaitState { coordinator.state.value.active != null }
        val old = coordinator.state.value.active!!
        coordinator.selectInstant(old.selectedInstant.plusSeconds(3600), old.viewGeneration)
        val before = coordinator.state.value

        val savedId = DatasetId("saved-b")
        val savedAsOf = time.minusSeconds(5 * 24 * 60 * 60L)
        val loaded = result(CatalogLoadRequest(b, savedId, savedAsOf), CatalogOrigin.SIMULATED)
            as CatalogLoadResult.Success
        h.storage.save(CatalogStorageNamespace.SIMULATED,
            CatalogRecording(loaded.catalog, loaded.expectedSupport, loaded.dailyExpectedSupport))
        val writesBeforePreview = h.dao.writes
        val sourceLoadsBeforePreview = h.simulated.requests.size

        val readStarted = CompletableDeferred<Unit>()
        val releaseRead = CompletableDeferred<Unit>()
        h.dao.beforeFind = {
            readStarted.complete(Unit)
            withContext(NonCancellable) { releaseRead.await() }
        }
        val preview = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, savedId),
        ) { true }
        readStarted.await()

        val pending = coordinator.state.value
        assertTrue(pending.loading)
        assertEquals(a, pending.selectedLocation)
        assertEquals("Alpha", pending.selectedLocationName)
        assertEquals(before.active, pending.active)
        assertEquals(before.active?.selectedInstant, pending.active?.selectedInstant)

        releaseRead.complete(Unit)
        val activated = withTimeout(3000) { preview.await() } as DebugActivationResult.Success
        assertEquals(b, coordinator.state.value.selectedLocation)
        assertEquals(b, activated.selection.location)
        assertEquals(savedId, activated.selection.datasetId)
        assertEquals(savedAsOf, activated.selection.selectedInstant)
        assertEquals(CatalogActivationDelivery.CACHE, activated.selection.delivery)
        assertNull(coordinator.state.value.selectedLocationName)
        assertEquals(writesBeforePreview, h.dao.writes)
        assertEquals(sourceLoadsBeforePreview, h.simulated.requests.size)
    }

    @Test fun savedReplayPreviewProjectsReplayWithoutChangingCapturedRecording() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }

        val replayId = DatasetId("saved-replay-b")
        val captured = result(CatalogLoadRequest(b, replayId, time), CatalogOrigin.SIMULATED)
            as CatalogLoadResult.Success
        val recording = CatalogRecording(captured.catalog, captured.expectedSupport, captured.dailyExpectedSupport)
        h.storage.save(CatalogStorageNamespace.REPLAY, recording)
        val writesBeforePreview = h.dao.writes
        val sourceLoadsBeforePreview = h.replay.requests.size

        val preview = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.REPLAY, replayId, replayId),
        ) { true }.await() as DebugActivationResult.Success
        val stored = h.storage.load(b, CatalogStorageNamespace.REPLAY, replayId) as StoredCatalogResult.Found

        assertEquals(b, preview.selection.location)
        assertEquals(CatalogOrigin.REPLAY, preview.selection.origin)
        assertEquals(CatalogOrigin.REPLAY, preview.selection.recording.catalog.provenance.origin)
        assertEquals(CatalogOrigin.SIMULATED, stored.recording.catalog.provenance.origin)
        assertArrayEquals(RecordedCatalogCodec.encode(recording), RecordedCatalogCodec.encode(stored.recording))
        assertEquals(writesBeforePreview, h.dao.writes)
        assertEquals(sourceLoadsBeforePreview, h.replay.requests.size)
    }

    @Test fun missingAndInvalidSavedPreviewsKeepPriorSelectionAndDoNoFallbackWrites() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a, "Alpha")
        awaitState { coordinator.state.value.active != null }
        val before = coordinator.state.value
        val liveCalls = h.live.requests.size
        val simulatedCalls = h.simulated.requests.size
        val writes = h.dao.writes

        val missing = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, DatasetId("missing-b")),
        ) { true }.await() as DebugActivationResult.Failure
        assertTrue(missing.message.contains("not found"))
        assertEquals(a, coordinator.state.value.selectedLocation)
        assertEquals("Alpha", coordinator.state.value.selectedLocationName)
        assertEquals(before.active, coordinator.state.value.active)
        assertEquals(before.active?.selectedInstant, coordinator.state.value.active?.selectedInstant)
        assertEquals(before.active?.viewGeneration, coordinator.state.value.active?.viewGeneration)
        assertEquals(liveCalls, h.live.requests.size)
        assertEquals(simulatedCalls, h.simulated.requests.size)
        assertEquals(writes, h.dao.writes)

        val invalidId = DatasetId("invalid-b")
        val loaded = result(CatalogLoadRequest(b, invalidId, time), CatalogOrigin.SIMULATED)
            as CatalogLoadResult.Success
        h.storage.save(CatalogStorageNamespace.SIMULATED,
            CatalogRecording(loaded.catalog, loaded.expectedSupport, loaded.dailyExpectedSupport))
        h.dao.corrupt(b.id.value, CatalogStorageNamespace.SIMULATED.name, invalidId.value)
        val writesBeforeInvalidPreview = h.dao.writes
        val liveCallsBeforeInvalidPreview = h.live.requests.size
        val simulatedCallsBeforeInvalidPreview = h.simulated.requests.size
        val invalidBefore = coordinator.state.value.active

        val invalid = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, invalidId),
        ) { true }.await() as DebugActivationResult.Failure
        assertTrue(invalid.message.contains("invalid"))
        assertEquals(a, coordinator.state.value.selectedLocation)
        assertEquals("Alpha", coordinator.state.value.selectedLocationName)
        assertEquals(invalidBefore, coordinator.state.value.active)
        assertEquals(invalidBefore?.selectedInstant, coordinator.state.value.active?.selectedInstant)
        assertEquals(liveCallsBeforeInvalidPreview, h.live.requests.size)
        assertEquals(simulatedCallsBeforeInvalidPreview, h.simulated.requests.size)
        assertEquals(writesBeforeInvalidPreview, h.dao.writes)
    }

    @Test fun savedPreviewMetadataValidatorRunsAfterCoordinatorLoadAndFailsClosed() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a, "Alpha")
        awaitState { coordinator.state.value.active != null }
        val prior = coordinator.state.value
        val id = DatasetId("saved-metadata-check")
        val loaded = result(CatalogLoadRequest(b, id, time), CatalogOrigin.SIMULATED) as CatalogLoadResult.Success
        val recording = CatalogRecording(loaded.catalog, loaded.expectedSupport, loaded.dailyExpectedSupport)
        h.storage.save(CatalogStorageNamespace.SIMULATED, recording)
        val sourceCalls = h.simulated.requests.size
        val writes = h.dao.writes
        var validatorRecording: CatalogRecording? = null

        val result = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, id),
        ) { candidate -> validatorRecording = candidate; false }.await() as DebugActivationResult.Failure

        assertTrue(result.message.contains("metadata"))
        assertEquals(recording.catalog, validatorRecording?.catalog)
        assertEquals(a, coordinator.state.value.selectedLocation)
        assertEquals(prior.active, coordinator.state.value.active)
        assertEquals(prior.active?.selectedInstant, coordinator.state.value.active?.selectedInstant)
        assertEquals(sourceCalls, h.simulated.requests.size)
        assertEquals(writes, h.dao.writes)
    }

    @Test fun supersededSavedPreviewCannotPublishOrRestoreOverNewerRequest() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }

        val savedId = DatasetId("saved-b-pending")
        val loaded = result(CatalogLoadRequest(b, savedId, time), CatalogOrigin.SIMULATED)
            as CatalogLoadResult.Success
        h.storage.save(CatalogStorageNamespace.SIMULATED,
            CatalogRecording(loaded.catalog, loaded.expectedSupport, loaded.dailyExpectedSupport))
        val readStarted = CompletableDeferred<Unit>()
        val releaseRead = CompletableDeferred<Unit>()
        var blockedFirstRead = false
        h.dao.beforeFind = {
            if (!blockedFirstRead) {
                blockedFirstRead = true
                readStarted.complete(Unit)
                withContext(NonCancellable) { releaseRead.await() }
            }
        }
        val preview = coordinator.requestSavedCatalogPreview(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, savedId),
        ) { true }
        readStarted.await()

        val replacement = coordinator.requestDebugCatalog(
            CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("newer-a")),
        )
        val replacementResult = withTimeout(3000) { replacement.await() } as DebugActivationResult.Success
        val replacementState = coordinator.state.value
        assertEquals(a, replacementResult.selection.location)

        releaseRead.complete(Unit)
        assertEquals(DebugActivationResult.Superseded, withTimeout(3000) { preview.await() })
        assertEquals(replacementState, coordinator.state.value)
    }

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

    @Test fun checkpointWaitsForStartupReadAndRestoresSettledEmptyState() = runBlocking {
        val h = harness()
        val read = CompletableDeferred<SavedLocationResult>()
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { read.await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        coordinator.restoreStartup()
        val checkpoint = async { coordinator.captureSettledCheckpoint() }
        delay(30)
        assertFalse(coordinator.state.value.loading)
        assertFalse(checkpoint.isCompleted)
        read.complete(SavedLocationResult.Selected(null))
        val saved = withTimeout(3000) { checkpoint.await() }
        val savedState = coordinator.state.value
        val result = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("test-preview")))
        assertTrue(withTimeout(3000) { result.await() } is DebugActivationResult.Success)
        coordinator.restoreCheckpoint(saved)
        assertEquals(savedState, coordinator.state.value)
        val next = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("after-empty")))
        val success = withTimeout(3000) { next.await() } as DebugActivationResult.Success
        assertEquals(2L, success.selection.viewGeneration)
    }

    @Test fun overlappingStartupAndFallbackRemainPendingUntilCurrentReadFinishes() = runBlocking {
        val h = harness()
        val reads = listOf(CompletableDeferred<SavedLocationResult>(), CompletableDeferred())
        var calls = 0
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { reads[calls++].await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        coordinator.restoreStartup()
        kotlinx.coroutines.yield()
        val alternate = coordinator.beginAlternateStartup()
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        reads[0].complete(SavedLocationResult.Selected(null))
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        assertTrue(coordinator.fallbackStartup(alternate))
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        reads[1].complete(SavedLocationResult.Selected(null))
        withTimeout(3000) { capture.await() }
        Unit
    }

    @Test fun activeCheckpointRestoreInvalidatesGenerationAndSupersedesPreview() = runBlocking {
        val blocked = CompletableDeferred<CatalogLoadResult>()
        val started = CompletableDeferred<Unit>()
        val finished = CompletableDeferred<Unit>()
        val simulated = FakeSource(CatalogOrigin.SIMULATED) {
            started.complete(Unit)
            try { withContext(NonCancellable) { blocked.await() } } finally { finished.complete(Unit) }
        }
        val h = harness(simulated = simulated)
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        coordinator.captureSettledCheckpoint()
        val initial = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.LIVE, dataset))
        assertTrue(withTimeout(3000) { initial.await() } is DebugActivationResult.Success)
        val before = coordinator.state.value
        val ioBeforeCapture = listOf(h.dao.reads, h.dao.writes, h.live.requests.size, h.simulated.requests.size)
        val checkpoint = coordinator.captureSettledCheckpoint()
        assertEquals(ioBeforeCapture, listOf(h.dao.reads, h.dao.writes, h.live.requests.size, h.simulated.requests.size))
        val preview = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("blocked")))
        started.await()
        val ioBeforeRestore = listOf(h.dao.reads, h.dao.writes, h.live.requests.size, h.simulated.requests.size)
        coordinator.restoreCheckpoint(checkpoint)
        assertEquals(ioBeforeRestore, listOf(h.dao.reads, h.dao.writes, h.live.requests.size, h.simulated.requests.size))
        assertEquals(DebugActivationResult.Superseded, preview.await())
        val restored = coordinator.state.value
        val old = before.active!!
        val fresh = restored.active!!
        assertTrue(fresh.viewGeneration > old.viewGeneration)
        assertEquals(before.copy(active = old.copy(viewGeneration = fresh.viewGeneration)), restored)
        val other = old.selectedInstant.plusSeconds(3600)
        coordinator.selectInstant(other, old.viewGeneration)
        assertEquals(old.selectedInstant, coordinator.state.value.active!!.selectedInstant)
        coordinator.selectInstant(other, fresh.viewGeneration)
        assertEquals(other, coordinator.state.value.active!!.selectedInstant)
        val afterSelection = coordinator.state.value
        blocked.complete(result(simulated.requests.first(), CatalogOrigin.SIMULATED))
        finished.await()
        kotlinx.coroutines.yield()
        assertEquals(afterSelection, coordinator.state.value)
        Unit
    }


    @Test fun captureWaitsForReplacementActivationAndRequestHandlesResolve() = runBlocking {
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<CatalogLoadResult>()
        val source = FakeSource(CatalogOrigin.SIMULATED) { request ->
            if (request.datasetId.value == "hold") {
                entered.complete(Unit)
                withContext(NonCancellable) { release.await() }
            } else CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE)
        }
        val coordinator = harness(simulated = source).coordinator(this)
        val participant = coordinator.beginAlternateStartup()
        val first = coordinator.requestAlternateStartupCatalog(participant,
            CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("hold")))!!
        entered.await()
        coordinator.finishAlternateStartup(participant)
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        val second = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("failure")))
        assertEquals(DebugActivationResult.Superseded, first.await())
        assertTrue(second.await() is DebugActivationResult.Failure)
        withTimeout(3000) { capture.await() }
        val failed = coordinator.state.value
        val checkpoint = coordinator.captureSettledCheckpoint()
        coordinator.restoreCheckpoint(checkpoint)
        assertEquals(failed, coordinator.state.value)
        release.complete(result(source.requests.first(), CatalogOrigin.SIMULATED))
        Unit
    }

    @Test fun startupReadAndActivationBothDelayCapture() = runBlocking {
        val selected = SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        ))
        val read = CompletableDeferred<SavedLocationResult>()
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<CatalogLoadResult>()
        val live = FakeSource(CatalogOrigin.LIVE) { entered.complete(Unit); release.await() }
        val h = harness(live = live)
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { read.await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        coordinator.restoreStartup()
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        assertFalse(coordinator.state.value.loading)
        assertFalse(capture.isCompleted)
        read.complete(SavedLocationResult.Selected(selected))
        entered.await()
        assertFalse(capture.isCompleted)
        release.complete(result(live.requests.single(), CatalogOrigin.LIVE))
        withTimeout(3000) { capture.await() }
        assertEquals(a, coordinator.state.value.active?.location)
        Unit
    }


    @Test fun invalidAndFailedSavedReadsSettleAndRestoreErrorState() = runBlocking {
        val h = harness()
        for (restorer in listOf(
            StartupLocationRestorer { SavedLocationResult.MalformedRecord },
            StartupLocationRestorer { throw IllegalStateException("read failed") },
        )) {
            val coordinator = CatalogActivationCoordinator(this, restorer, h.storage, h.liveRepository, h.sources, h.clock)
            coordinator.restoreStartup()
            val saved = withTimeout(3000) { coordinator.captureSettledCheckpoint() }
            val state = coordinator.state.value
            assertNull(state.active)
            assertNotNull(state.lastError)
            coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("error-roundtrip"))).await()
            coordinator.restoreCheckpoint(saved)
            assertEquals(state, coordinator.state.value)
        }
    }

    @Test fun startupActivationFailureSettlesAndCapturesError() = runBlocking {
        val location = SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        ))
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<Unit>()
        val live = FakeSource(CatalogOrigin.LIVE) { entered.complete(Unit); release.await(); CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE) }
        val h = harness(live = live, restorer = FakeRestorer(SavedLocationResult.Selected(location)))
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        entered.await()
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        release.complete(Unit)
        withTimeout(3000) { capture.await() }
        assertNotNull(coordinator.state.value.lastError)
        assertNull(coordinator.state.value.active)
        Unit
    }

    @Test fun captureRechecksAfterReadyNotificationWhenActivationStarts() = runBlocking {
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<CatalogLoadResult>()
        val simulated = FakeSource(CatalogOrigin.SIMULATED) { entered.complete(Unit); release.await() }
        val coordinator = harness(simulated = simulated).coordinator(this)
        val participant = coordinator.beginAlternateStartup()
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        coordinator.finishAlternateStartup(participant)
        val completion = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("later")))
        entered.await()
        assertFalse(capture.isCompleted)
        release.complete(result(simulated.requests.single(), CatalogOrigin.SIMULATED))
        assertTrue(completion.await() is DebugActivationResult.Success)
        withTimeout(3000) { capture.await() }
        assertNotNull(coordinator.state.value.active)
        Unit
    }




    @Test fun exactSelectionAtSampleTieAndReplayIdentityIsExplicit() = runBlocking {
        val tieInstant = Instant.parse("2026-03-08T20:30:00Z")
        val h = harness(clockInstant = tieInstant)
        val coordinator = h.coordinator(this)
        coordinator.committedLocationSelection(a)
        awaitState { coordinator.state.value.active != null }
        val facts = coordinator.state.value.active!!.recording.catalog.facts.map { it.validTime }.distinct().sorted()
        val earlier = facts.last { it.isBefore(tieInstant) }
        val later = facts.first { it.isAfter(tieInstant) }
        assertEquals(30L, java.time.Duration.between(earlier, tieInstant).toMinutes())
        assertEquals(30L, java.time.Duration.between(tieInstant, later).toMinutes())
        assertEquals(tieInstant, coordinator.state.value.active?.selectedInstant)
        try {
            CatalogActivationRequest(a, CatalogOrigin.REPLAY, DatasetId("r"))
            fail("Replay must name its recording")
        } catch (_: IllegalArgumentException) { }
    }

}
