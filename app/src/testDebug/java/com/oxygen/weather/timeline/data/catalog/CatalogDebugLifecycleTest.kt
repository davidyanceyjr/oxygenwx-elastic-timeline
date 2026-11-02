package com.oxygen.weather.timeline.data.catalog

import android.app.Activity
import androidx.lifecycle.LifecycleCoroutineScope
import com.oxygen.weather.timeline.ActivityVariantDelegate
import com.oxygen.weather.timeline.data.debug.runCatalogRequestLifecycle
import com.oxygen.weather.timeline.data.debug.activateCatalogForRequest
import com.oxygen.weather.timeline.data.debug.CatalogIntentValues
import com.oxygen.weather.timeline.data.debug.CatalogPickerDelegate
import com.oxygen.weather.timeline.data.debug.processCatalogActivationRequest
import com.oxygen.weather.timeline.data.debug.startCatalogColdLaunch
import com.oxygen.weather.timeline.data.debug.startPendingDebugEntry
import com.oxygen.weather.timeline.ui.LocationSelectorController
import com.oxygen.weather.timeline.ui.LocationSelectorStore
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

class CatalogDebugLifecycleTest : CatalogCoordinatorTestSupport() {
    @Test fun delegateHandshakeCancellationAndFallbackAreDistinct() = runBlocking {
        val h = harness()
        val read = CompletableDeferred<SavedLocationResult>()
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { read.await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        var picker = 0
        var failures = 0
        val cancelledParticipant = coordinator.beginAlternateStartup()
        val work = CompletableDeferred<Boolean>()
        val job = launch {
            runCatalogRequestLifecycle({ work.await() }, coordinator, cancelledParticipant, { picker++ }, { failures++ })
        }
        kotlinx.coroutines.yield()
        job.cancel()
        job.join()
        assertEquals(0, picker)
        assertEquals(0, failures)
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        val fallbackParticipant = coordinator.beginAlternateStartup()
        runCatalogRequestLifecycle({ throw IllegalStateException("failure") }, coordinator, fallbackParticipant, { picker++ }, { failures++ })
        kotlinx.coroutines.yield()
        assertEquals(1, picker)
        assertEquals(1, failures)
        assertFalse(capture.isCompleted)
        read.complete(SavedLocationResult.Selected(null))
        withTimeout(3000) { capture.await() }
        Unit
    }

    @Test fun delegateActivationSelectsOnlyItsSuccessfulGeneration() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        val participant = coordinator.beginAlternateStartup()
        val requested = time.plusSeconds(1800)
        val ok = activateCatalogForRequest(coordinator, participant,
            CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("fixture")), requested)
        assertTrue(ok)
        assertEquals(requested, coordinator.state.value.active!!.selectedInstant)
        assertTrue(coordinator.finishAlternateStartup(participant))
        val capture = coordinator.captureSettledCheckpoint()
        val before = coordinator.state.value
        coordinator.restoreCheckpoint(capture)
        assertEquals(before.active!!.selectedInstant, coordinator.state.value.active!!.selectedInstant)
        assertFalse(activateCatalogForRequest(coordinator, participant,
            CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("stale")), time))
        assertEquals(before.active.selectedInstant, coordinator.state.value.active!!.selectedInstant)
    }

    @Test fun supersededDelegateRequestSkipsFixtureAndDoesNotFallback() = runBlocking {
        val entered = CompletableDeferred<Unit>()
        val release = CompletableDeferred<CatalogLoadResult>()
        val source = FakeSource(CatalogOrigin.SIMULATED) { request ->
            if (request.datasetId.value == "first") { entered.complete(Unit); withContext(NonCancellable) { release.await() } }
            else result(request, CatalogOrigin.SIMULATED)
        }
        val coordinator = harness(simulated = source).coordinator(this)
        val participant = coordinator.beginAlternateStartup()
        var picker = 0
        var errors = 0
        val first = launch {
            runCatalogRequestLifecycle(
                { activateCatalogForRequest(coordinator, participant,
                    CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("first")), time.plusSeconds(1800)) },
                coordinator, participant, { picker++ }, { errors++ },
            )
        }
        entered.await()
        val replacement = coordinator.requestDebugCatalog(CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("second")))
        first.join()
        assertEquals(1, picker)
        assertEquals(0, errors)
        assertTrue(replacement.await() is DebugActivationResult.Success)
        assertEquals(time, coordinator.state.value.active!!.selectedInstant)
        release.complete(result(source.requests.first(), CatalogOrigin.SIMULATED))
        Unit
    }

    @Test fun endedSessionDuringSavedLocationLookupCannotSubmitActivation() = runBlocking {
        val h = harness()
        val coordinator = h.coordinator(this)
        var sessionActive = true
        var picker = 0
        var failures = 0
        val lookupEntered = CompletableDeferred<Unit>()
        val releaseLookup = CompletableDeferred<SavedLocationResult>()
        val selected = SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        ))
        val job = launch {
            runCatalogRequestLifecycle(
                work = {
                    processCatalogActivationRequest(
                        coordinator, null,
                        { CatalogIntentValues(CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG, 1, "a", "SIMULATED", "late", null, false, null) },
                        { lookupEntered.complete(Unit); releaseLookup.await() },
                        stillCurrent = { sessionActive },
                    )
                },
                coordinator = coordinator,
                participant = null,
                showPicker = { picker++ },
                reportFailure = { failures++ },
                stillCurrent = { sessionActive },
            )
        }
        lookupEntered.await()
        sessionActive = false
        releaseLookup.complete(SavedLocationResult.Selected(selected))
        job.join()

        assertTrue(h.simulated.requests.isEmpty())
        assertNull(coordinator.state.value.selectedLocation)
        assertEquals(0, picker)
        assertEquals(0, failures)
    }

    @Test fun endingSessionDuringPickerActivationRestoresCheckpointAndSuppressesLatePicker() = runBlocking {
        val started = CompletableDeferred<Unit>()
        val release = CompletableDeferred<CatalogLoadResult>()
        val simulated = FakeSource(CatalogOrigin.SIMULATED) {
            started.complete(Unit)
            withContext(NonCancellable) { release.await() }
        }
        val h = harness(simulated = simulated)
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        val checkpoint = coordinator.captureSettledCheckpoint()
        var sessionActive = true
        var picker = 0
        var failures = 0
        var selectedInstantApplied = false
        val job = launch {
            runCatalogRequestLifecycle(
                work = {
                    activateCatalogForRequest(
                        coordinator, null,
                        CatalogActivationRequest(a, CatalogOrigin.SIMULATED, DatasetId("session-preview")),
                        time.plusSeconds(1800),
                        applySelection = { _, _ -> selectedInstantApplied = true },
                        stillCurrent = { sessionActive },
                    )
                },
                coordinator = coordinator,
                participant = null,
                showPicker = { picker++ },
                reportFailure = { failures++ },
                stillCurrent = { sessionActive },
            )
        }
        started.await()
        sessionActive = false
        coordinator.restoreCheckpoint(checkpoint)
        job.join()
        release.complete(result(simulated.requests.single(), CatalogOrigin.SIMULATED))

        assertNull(coordinator.state.value.selectedLocation)
        assertNull(coordinator.state.value.active)
        assertFalse(selectedInstantApplied)
        assertEquals(0, picker)
        assertEquals(0, failures)
    }

    @Test fun pendingEntryCancelledBySelectionLeavesCoordinatorStartupOwned() = runBlocking {
        val h = harness()
        val initialRead = CompletableDeferred<SavedLocationResult>()
        val coordinator = CatalogActivationCoordinator(
            this, StartupLocationRestorer { initialRead.await() }, h.storage, h.liveRepository, h.sources, h.clock,
        )
        coordinator.restoreStartup()
        var entered = false
        val entry = startPendingDebugEntry(this, coordinator, { true }) { entered = true }
        kotlinx.coroutines.yield()
        entry.cancel()
        entry.join()

        coordinator.committedLocationSelection(b, "B")
        awaitState { coordinator.state.value.active?.location == b }
        initialRead.complete(SavedLocationResult.Selected(null))
        awaitState { !coordinator.state.value.loading }

        assertFalse(entered)
        assertEquals(b, coordinator.state.value.selectedLocation)
    }

    @Test fun pendingEntryCancelledByRefreshDoesNotBlockStartupOrTheNewRefresh() = runBlocking {
        val firstStarted = CompletableDeferred<Unit>()
        val releaseFirst = CompletableDeferred<CatalogLoadResult>()
        lateinit var live: FakeSource
        live = FakeSource(CatalogOrigin.LIVE) { request ->
            if (live.requests.size == 1) {
                firstStarted.complete(Unit)
                withContext(NonCancellable) { releaseFirst.await() }
            } else result(request, CatalogOrigin.LIVE)
        }
        val h = harness(live = live)
        h.restorer.selection = SavedLocationResult.Selected(SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        )))
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        firstStarted.await()
        var entered = false
        val entry = startPendingDebugEntry(this, coordinator, { true }) { entered = true }
        kotlinx.coroutines.yield()
        entry.cancel()
        entry.join()

        coordinator.refreshCurrent()
        awaitState { coordinator.state.value.active?.location == a && !coordinator.state.value.loading }
        releaseFirst.complete(result(live.requests.first(), CatalogOrigin.LIVE))
        awaitState { live.requests.size == 2 }

        assertFalse(entered)
        assertEquals(a, coordinator.state.value.active?.location)
        coordinator.captureSettledCheckpoint()
        Unit
    }

    @Test fun delayedSelectionSaveFailureRestoresCheckpointFirstAndLeavesDebugOff() = runBlocking {
        val h = harness()
        h.restorer.selection = SavedLocationResult.Selected(saved(a, 1001))
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        awaitState { coordinator.state.value.active?.location == a && !coordinator.state.value.loading }
        val originalInstant = coordinator.state.value.active!!.selectedInstant
        val checkpoint = coordinator.captureSettledCheckpoint()
        val host = BoundaryHost(coordinator)
        val delegate = CatalogPickerDelegate()
        delegate.enterDebugSession(host, checkpoint)

        assertTrue(coordinator.requestDebugCatalog(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, DatasetId("debug-session")),
        ).await() is DebugActivationResult.Success)
        assertEquals(b, coordinator.state.value.active?.location)
        assertTrue(delegate.isDebugSessionActive(host))

        val candidate = candidate(CatalogLocation(LocationId("geonames:1002"), b.coordinates, b.timeZone), 1002)
        val saveStarted = CompletableDeferred<Unit>()
        val releaseSave = CompletableDeferred<SavedLocationResult>()
        var selectCalls = 0
        var committedSelections = 0
        val store = object : LocationSelectorStore {
            override suspend fun list() = SavedLocationListResult.Locations(emptyList())
            override suspend fun restore() = SavedLocationResult.Selected(null)
            override suspend fun add(candidate: PlaceSearchCandidate): SavedLocationResult {
                saveStarted.complete(Unit)
                return releaseSave.await()
            }
            override suspend fun select(id: LocationId): SavedLocationResult {
                selectCalls++
                error("a failed save must not persist selection")
            }
            override suspend fun remove(id: LocationId) = SavedLocationResult.NoMutation
        }
        val controller = LocationSelectorController(
            scope = this,
            searcher = PlaceSearcher { PlaceSearchResult.NoMatches },
            store = store,
            beforeSelection = { delegate.beforeOrdinaryAction(host) },
            onCommittedSelection = { committedSelections++ },
        )

        controller.selectCandidate(candidate)
        saveStarted.await()
        assertFalse(delegate.isDebugSessionActive(host))
        assertFalse(host.menuVisible)
        assertEquals(a, coordinator.state.value.active?.location)
        assertEquals(originalInstant, coordinator.state.value.active?.selectedInstant)

        releaseSave.complete(SavedLocationResult.PointConflict(candidate.canonicalPlaceId!!))
        awaitState { controller.state.value.messageIsError }

        assertEquals(0, selectCalls)
        assertEquals(0, committedSelections)
        assertEquals(a, coordinator.state.value.selectedLocation)
        assertEquals(a, coordinator.state.value.active?.location)
        assertFalse(delegate.isDebugSessionActive(host))
    }

    @Test fun refreshFailureUsesRestoredRequestAndKeepsDebugOff() = runBlocking {
        lateinit var live: FakeSource
        live = FakeSource(CatalogOrigin.LIVE) { request ->
            if (live.requests.size == 1) result(request, CatalogOrigin.LIVE)
            else CatalogLoadResult.TransportFailure(TransportFailureReason.UNAVAILABLE)
        }
        val h = harness(live = live)
        h.restorer.selection = SavedLocationResult.Selected(saved(a, 1003))
        val coordinator = h.coordinator(this)
        coordinator.restoreStartup()
        awaitState { coordinator.state.value.active?.location == a && !coordinator.state.value.loading }
        val originalInstant = coordinator.state.value.active!!.selectedInstant
        val checkpoint = coordinator.captureSettledCheckpoint()
        val host = BoundaryHost(coordinator)
        val delegate = CatalogPickerDelegate()
        delegate.enterDebugSession(host, checkpoint)

        assertTrue(coordinator.requestDebugCatalog(
            CatalogActivationRequest(b, CatalogOrigin.SIMULATED, DatasetId("debug-session")),
        ).await() is DebugActivationResult.Success)
        assertEquals(b, coordinator.state.value.active?.location)

        delegate.beforeOrdinaryAction(host)
        assertFalse(delegate.isDebugSessionActive(host))
        assertFalse(host.menuVisible)
        assertEquals(a, coordinator.state.value.active?.location)
        assertEquals(originalInstant, coordinator.state.value.active?.selectedInstant)

        coordinator.refreshCurrent()
        awaitState { !coordinator.state.value.loading && coordinator.state.value.lastError != null }

        assertEquals(2, live.requests.size)
        assertEquals(a, live.requests.last().location)
        assertEquals(DatasetId("forecast-44d"), live.requests.last().datasetId)
        assertEquals(a, coordinator.state.value.selectedLocation)
        assertEquals(a, coordinator.state.value.active?.location)
        assertEquals(originalInstant, coordinator.state.value.active?.selectedInstant)
        assertEquals(CatalogRefreshStatus.TRANSPORT_FAILURE, coordinator.state.value.refreshStatus)
        assertFalse(delegate.isDebugSessionActive(host))
    }

    @Test fun coldLaunchHandshakeWaitsAcrossDecodingLookupActivationAndFixtureSelection() = runBlocking {
        val reading = CompletableDeferred<Unit>()
        val decode = CompletableDeferred<CatalogIntentValues>()
        val lookupEntered = CompletableDeferred<Unit>()
        val lookup = CompletableDeferred<SavedLocationResult>()
        val sourceEntered = CompletableDeferred<Unit>()
        val sourceRelease = CompletableDeferred<CatalogLoadResult>()
        val selectionEntered = CompletableDeferred<Unit>()
        val selectionRelease = CompletableDeferred<Unit>()
        val replay = FakeSource(CatalogOrigin.REPLAY) { sourceEntered.complete(Unit); sourceRelease.await() }
        val coordinator = harness(replay = replay).coordinator(this)
        val selected = SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        ))
        var picker = 0
        var failures = 0
        lateinit var job: kotlinx.coroutines.Job
        startCatalogColdLaunch(coordinator) { participant ->
            job = launch {
                runCatalogRequestLifecycle({
                    processCatalogActivationRequest(coordinator, participant,
                        { reading.complete(Unit); decode.await() },
                        { lookupEntered.complete(Unit); lookup.await() },
                        { instant, generation -> selectionEntered.complete(Unit); selectionRelease.await(); coordinator.selectInstant(instant, generation) })
                }, coordinator, participant, { picker++ }, { failures++ })
            }
        }
        val capture = async { coordinator.captureSettledCheckpoint() }
        reading.await()
        assertFalse(capture.isCompleted)
        decode.complete(CatalogIntentValues(CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG, 1, "a", "REPLAY",
            "sg4-canonical-gaps-v8", "sg4-canonical-gaps-v8", false, time.plusSeconds(1800).toString()))
        lookupEntered.await()
        assertFalse(capture.isCompleted)
        lookup.complete(SavedLocationResult.Selected(selected))
        sourceEntered.await()
        assertFalse(capture.isCompleted)
        sourceRelease.complete(result(replay.requests.single(), CatalogOrigin.REPLAY, CatalogOrigin.LIVE))
        selectionEntered.await()
        assertFalse(capture.isCompleted)
        selectionRelease.complete(Unit)
        job.join()
        withTimeout(3000) { capture.await() }
        assertEquals(time.plusSeconds(1800), coordinator.state.value.active!!.selectedInstant)
        assertEquals(1, picker)
        assertEquals(0, failures)
        Unit
    }

    @Test fun coldLaunchValidationFailureAndLookupCancellationUseActualHandshake() = runBlocking {
        val read = CompletableDeferred<SavedLocationResult>()
        val h = harness()
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { read.await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        var picker = 0
        var failures = 0
        lateinit var validation: kotlinx.coroutines.Job
        startCatalogColdLaunch(coordinator) { participant ->
            validation = launch {
                runCatalogRequestLifecycle({
                    processCatalogActivationRequest(coordinator, participant,
                        { CatalogIntentValues(CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG, -1, "a", "LIVE", "forecast-44d", null, true, null) },
                        { fail("invalid request must not look up a location"); SavedLocationResult.MissingLocation })
                }, coordinator, participant, { picker++ }, { failures++ })
            }
        }
        val capture = async { coordinator.captureSettledCheckpoint() }
        validation.join()
        assertEquals(1, picker)
        assertEquals(1, failures)
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        read.complete(SavedLocationResult.Selected(null))
        withTimeout(3000) { capture.await() }

        val lookupEntered = CompletableDeferred<Unit>()
        val lookup = CompletableDeferred<SavedLocationResult>()
        lateinit var cancelled: kotlinx.coroutines.Job
        startCatalogColdLaunch(coordinator) { participant ->
            cancelled = launch {
                runCatalogRequestLifecycle({
                    processCatalogActivationRequest(coordinator, participant,
                        { CatalogIntentValues(CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG, 1, "a", "LIVE", "forecast-44d", null, true, null) },
                        { lookupEntered.complete(Unit); lookup.await() })
                }, coordinator, participant, { picker++ }, { failures++ })
            }
        }
        lookupEntered.await()
        val blockedCapture = async { coordinator.captureSettledCheckpoint() }
        cancelled.cancel()
        cancelled.join()
        kotlinx.coroutines.yield()
        assertFalse(blockedCapture.isCompleted)
        assertEquals(1, picker)
        assertEquals(1, failures)
        blockedCapture.cancel()
        Unit
    }

    @Test fun failedFixtureActivationCompletesAndFallsBackWithoutSelecting() = runBlocking {
        val fallbackRead = CompletableDeferred<SavedLocationResult>()
        val source = FakeSource(CatalogOrigin.REPLAY) { CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE) }
        val h = harness(replay = source)
        val coordinator = CatalogActivationCoordinator(this, StartupLocationRestorer { fallbackRead.await() },
            h.storage, h.liveRepository, h.sources, h.clock)
        val selected = SavedLocation(a, PlaceSearchCandidate(
            name = "A", countryName = "United States", countryCode = "US", administrativeAreas = emptyList(),
            coordinates = a.coordinates, timeZone = a.timeZone, sourceReference = "test",
            attribution = PlaceSearchAttribution("Test", java.net.URI("https://example.org"), "Test", java.net.URI("https://example.org"), null),
        ))
        var picker = 0
        var failures = 0
        var selections = 0
        lateinit var job: kotlinx.coroutines.Job
        startCatalogColdLaunch(coordinator) { participant ->
            job = launch {
                runCatalogRequestLifecycle({
                    processCatalogActivationRequest(coordinator, participant,
                        { CatalogIntentValues(CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG, 1, "a", "REPLAY",
                            "sg4-canonical-gaps-v8", "sg4-canonical-gaps-v8", false, time.toString()) },
                        { SavedLocationResult.Selected(selected) }, { _, _ -> selections++ })
                }, coordinator, participant, { picker++ }, { failures++ })
            }
        }
        withTimeout(3000) { job.join() }
        assertEquals(1, picker)
        assertEquals(1, failures)
        assertEquals(0, selections)
        val capture = async { coordinator.captureSettledCheckpoint() }
        kotlinx.coroutines.yield()
        assertFalse(capture.isCompleted)
        fallbackRead.complete(SavedLocationResult.Selected(null))
        withTimeout(3000) { capture.await() }
        Unit
    }

    private fun saved(location: CatalogLocation, featureId: Long) = SavedLocation(location, candidate(location, featureId))

    private fun candidate(location: CatalogLocation, featureId: Long) = PlaceSearchCandidate(
        name = location.id.value,
        countryName = "United States",
        countryCode = "US",
        administrativeAreas = emptyList(),
        coordinates = location.coordinates,
        timeZone = location.timeZone,
        sourceReference = "test:${location.id.value}",
        attribution = PlaceSearchAttribution(
            "Test source", java.net.URI("https://example.org/source"),
            "Test license", java.net.URI("https://example.org/license"), null,
        ),
        canonicalPlaceId = CanonicalPlaceId.geonames(featureId),
    )

    private class BoundaryHost(
        override val activationCoordinator: CatalogActivationCoordinator,
    ) : ActivityVariantDelegate.Host {
        override val activity: Activity get() = error("unused in lifecycle test")
        override val activityScope: LifecycleCoroutineScope get() = error("unused in lifecycle test")
        override val savedLocations: SavedLocationRepository get() = error("unused in lifecycle test")
        var menuVisible = false
        var locationSelectorClosed = false

        override fun restoreStartupCatalog() = Unit
        override fun setDebugSessionMenuVisible(visible: Boolean) { menuVisible = visible }
        override fun closeLocationSelector() { locationSelectorClosed = true }
    }

}
