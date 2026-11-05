package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Instant
import com.oxygen.weather.timeline.presentation.InstantTimeAxis
import com.oxygen.weather.timeline.presentation.SupportedTimeInterval
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** One published, internally consistent view of a selected whole catalog. */
data class ActiveCatalogSelection(
    val location: CatalogLocation,
    val recording: CatalogRecording,
    val origin: CatalogOrigin,
    val datasetId: DatasetId,
    val replayRecordingId: DatasetId?,
    val delivery: CatalogActivationDelivery,
    val freshness: CatalogFreshness?,
    val storedAt: Instant?,
    val selectedInstant: Instant,
    val viewGeneration: Long,
)

enum class CatalogActivationDelivery { SOURCE, CACHE }

data class CatalogActivationState(
    val selectedLocation: CatalogLocation? = null,
    val selectedLocationName: String? = null,
    val active: ActiveCatalogSelection? = null,
    val loading: Boolean = false,
    val lastError: String? = null,
    val refreshStatus: CatalogRefreshStatus? = null,
)

data class CatalogActivationRequest(
    val location: CatalogLocation,
    val origin: CatalogOrigin,
    val datasetId: DatasetId,
    /** Required for REPLAY and must identify the same stored dataset explicitly. */
    val replayRecordingId: DatasetId? = null,
    val forceLiveRefresh: Boolean = false,
) {
    init {
        require((origin == CatalogOrigin.REPLAY) == (replayRecordingId != null)) {
            "REPLAY requests require a recording ID; other origins must not carry one"
        }
        require(replayRecordingId == null || replayRecordingId == datasetId) {
            "The replay recording ID must match the requested dataset ID"
        }
    }
}

sealed interface DebugActivationResult {
    data class Success(val selection: ActiveCatalogSelection) : DebugActivationResult
    data class Failure(val message: String) : DebugActivationResult
    data object Superseded : DebugActivationResult
}

/**
 * Application-owned request sequencer. It stages and validates a whole recording, then replaces
 * the active selection with one StateFlow write only if the request is still current.
 */
class CatalogActivationCoordinator(
    private val scope: CoroutineScope,
    private val savedLocations: StartupLocationRestorer,
    private val storage: CatalogDatasetStorage,
    private val liveRepository: LiveCatalogRepository,
    private val sources: CatalogSourceRegistry,
    private val clock: Clock,
    private val startupDatasetId: DatasetId = DatasetId("forecast-44d"),
) {
    private val mutableState = MutableStateFlow(CatalogActivationState())
    val state: StateFlow<CatalogActivationState> = mutableState.asStateFlow()

    private var selectedLocation: CatalogLocation? = null
    private var sequence = 0L
    private var requestJob: Job? = null
    private var lastRequest: CatalogActivationRequest? = null
    private var activeInterval: SupportedTimeInterval? = null
    private var nextViewGeneration = 0L
    private var pendingSelectionAnchor: Instant? = null
    private var startupParticipant: Long? = 0L
    private var nextStartupParticipant = 0L
    private var activationTerminal = true
    private var changed = CompletableDeferred<Unit>()
    private var currentCompletion: CompletableDeferred<DebugActivationResult>? = null

    sealed interface Checkpoint

    private data class SavedCheckpoint(
        val location: CatalogLocation?,
        val request: CatalogActivationRequest?,
        val interval: SupportedTimeInterval?,
        val anchor: Instant?,
        val state: CatalogActivationState,
    ) : Checkpoint

    private fun signalChange() {
        val previous = changed
        changed = CompletableDeferred()
        previous.complete(Unit)
    }

    fun beginAlternateStartup(): Long = synchronized(this) {
        val participant = ++nextStartupParticipant
        startupParticipant = participant
        signalChange()
        participant
    }

    fun finishAlternateStartup(participant: Long): Boolean = synchronized(this) {
        if (startupParticipant != participant) false else {
            startupParticipant = null
            signalChange()
            true
        }
    }

    /** Transfer the alternate participant to saved-location restoration without a ready gap. */
    fun fallbackStartup(participant: Long): Boolean = synchronized(this) {
        if (startupParticipant != participant) false else {
            startRestoreLocked()
            true
        }
    }

    suspend fun captureSettledCheckpoint(): Checkpoint {
        while (true) {
            val wait = synchronized(this) {
                if (startupParticipant == null && activationTerminal) {
                    return SavedCheckpoint(selectedLocation, lastRequest, activeInterval, pendingSelectionAnchor, mutableState.value)
                }
                changed
            }
            wait.await()
        }
    }

    fun restoreCheckpoint(checkpoint: Checkpoint) = synchronized(this) {
        val saved = checkpoint as SavedCheckpoint
        sequence++
        currentCompletion?.complete(DebugActivationResult.Superseded)
        currentCompletion = null
        requestJob?.cancel()
        requestJob = null
        selectedLocation = saved.location
        lastRequest = saved.request
        activeInterval = saved.interval
        pendingSelectionAnchor = saved.anchor
        val active = saved.state.active
        mutableState.value = saved.state.copy(active = active?.copy(viewGeneration = ++nextViewGeneration))
        activationTerminal = true
        signalChange()
    }

    /** Restore independently of selector visibility. A newer committed choice wins the race. */
    fun restoreStartup() = synchronized(this) { startRestoreLocked() }

    private fun startRestoreLocked() {
        val participant = ++nextStartupParticipant
        startupParticipant = participant
        val restoreSequence = ++sequence
        currentCompletion?.complete(DebugActivationResult.Superseded)
        currentCompletion = null
        requestJob?.cancel()
        requestJob = null
        activationTerminal = true
        signalChange()
        scope.launch {
            val result = try {
                savedLocations.restore()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                synchronized(this@CatalogActivationCoordinator) {
                    if (startupParticipant == participant) {
                        if (sequence == restoreSequence) mutableState.value = mutableState.value.copy(
                            loading = false,
                            lastError = error.message ?: "Saved location restoration failed (${error.javaClass.simpleName})",
                        )
                        startupParticipant = null
                        signalChange()
                    }
                }
                return@launch
            }
            synchronized(this@CatalogActivationCoordinator) {
                if (startupParticipant != participant) return@synchronized
                if (sequence == restoreSequence) {
                    when (result) {
                        is SavedLocationResult.Selected -> {
                            val nextLocation = result.location?.location
                            if (selectedLocation != nextLocation) {
                                pendingSelectionAnchor = mutableState.value.active?.selectedInstant
                                activeInterval = null
                            }
                            selectedLocation = nextLocation
                            mutableState.value = mutableState.value.copy(
                                selectedLocation = selectedLocation,
                                selectedLocationName = result.location?.candidate?.name,
                            )
                            result.location?.location?.let {
                                beginRequest(CatalogActivationRequest(it, CatalogOrigin.LIVE, startupDatasetId))
                            }
                        }
                        else -> mutableState.value = mutableState.value.copy(
                            loading = false,
                            lastError = "Saved location restoration was invalid",
                        )
                    }
                }
                startupParticipant = null
                signalChange()
            }
        }
    }

    /** Call only after the selector has persisted the selection successfully. */
    fun committedLocationSelection(location: CatalogLocation, locationName: String? = null) {
        synchronized(this) {
            if (selectedLocation != location) {
                pendingSelectionAnchor = mutableState.value.active?.selectedInstant ?: pendingSelectionAnchor
                activeInterval = null
            }
            selectedLocation = location
            mutableState.value = mutableState.value.copy(
                selectedLocation = location,
                selectedLocationName = locationName ?: mutableState.value.selectedLocationName,
                active = mutableState.value.active?.takeIf { it.location == location },
                loading = true,
                lastError = null,
                refreshStatus = null,
            )
            beginRequest(CatalogActivationRequest(location, CatalogOrigin.LIVE, startupDatasetId))
        }
    }

    /** Activate a validated debug choice for this process without persisting a location choice. */
    fun requestDebugCatalog(request: CatalogActivationRequest): Deferred<DebugActivationResult> =
        synchronized(this) {
            selectedLocation = request.location
            mutableState.value = mutableState.value.copy(selectedLocation = request.location)
            beginRequest(request)
        }

    /** Preview a recording that must already exist in its SIMULATED or REPLAY namespace. */
    fun requestSavedCatalogPreview(
        request: CatalogActivationRequest,
        validateSavedMetadata: (CatalogRecording) -> Boolean,
    ): Deferred<DebugActivationResult> =
        synchronized(this) {
            require(request.origin == CatalogOrigin.SIMULATED || request.origin == CatalogOrigin.REPLAY) {
                "Saved previews require SIMULATED or REPLAY origin"
            }
            require(!request.forceLiveRefresh) { "Saved previews cannot request a LIVE refresh" }
            beginRequest(request, savedOnlyPreview = true, validateSavedMetadata = validateSavedMetadata)
        }

    fun requestAlternateStartupCatalog(participant: Long, request: CatalogActivationRequest): Deferred<DebugActivationResult>? =
        synchronized(this) {
            if (startupParticipant != participant) null else {
                selectedLocation = request.location
                mutableState.value = mutableState.value.copy(selectedLocation = request.location)
                beginRequest(request)
            }
        }

    /** Internal data-path entry point for refreshes and deterministic origin-switch coverage. */
    fun requestCatalog(
        origin: CatalogOrigin,
        datasetId: DatasetId,
        replayRecordingId: DatasetId? = null,
        forceLiveRefresh: Boolean = false,
    ) {
        synchronized(this) {
            val location = selectedLocation ?: return
            beginRequest(CatalogActivationRequest(location, origin, datasetId, replayRecordingId, forceLiveRefresh))
        }
    }

    fun refreshCurrent() {
        synchronized(this) {
            val previous = lastRequest ?: return
            beginRequest(previous.copy(forceLiveRefresh = previous.origin == CatalogOrigin.LIVE))
        }
    }

    /** A selection from the currently displayed dataset; stale UI callbacks are ignored. */
    fun selectInstant(instant: Instant, viewGeneration: Long) {
        synchronized(this) {
            val active = mutableState.value.active ?: return
            if (active.viewGeneration != viewGeneration) return
            val interval = activeInterval ?: return
            mutableState.value = mutableState.value.copy(
                active = active.copy(selectedInstant = interval.clamp(instant)),
            )
        }
    }

    private fun beginRequest(
        request: CatalogActivationRequest,
        savedOnlyPreview: Boolean = false,
        validateSavedMetadata: ((CatalogRecording) -> Boolean)? = null,
    ): Deferred<DebugActivationResult> {
        sequence++
        activationTerminal = false
        val requestSequence = sequence
        currentCompletion?.complete(DebugActivationResult.Superseded)
        val completion = CompletableDeferred<DebugActivationResult>()
        currentCompletion = completion
        requestJob?.cancel()
        val stateBeforeRequest = mutableState.value
        if (!savedOnlyPreview) lastRequest = request
        val retainsMatching = savedOnlyPreview || stateBeforeRequest.active?.location == request.location
        mutableState.value = mutableState.value.copy(
            loading = true,
            lastError = null,
            refreshStatus = null,
            active = stateBeforeRequest.active?.takeIf { retainsMatching },
        )
        signalChange()
        requestJob = scope.launch {
            try {
                val staged = loadAndValidate(request, requestSequence, completion, savedOnlyPreview, validateSavedMetadata)
                synchronized(this@CatalogActivationCoordinator) {
                    if (isCurrent(request, requestSequence, completion, savedOnlyPreview)) {
                        val previous = mutableState.value.active
                        val failedRefresh = staged.refreshStatus in setOf(
                            CatalogRefreshStatus.NO_DATA,
                            CatalogRefreshStatus.INVALID_REQUEST,
                            CatalogRefreshStatus.TRANSPORT_FAILURE,
                        )
                        if (request.origin == CatalogOrigin.LIVE && request.forceLiveRefresh &&
                            failedRefresh && previous?.location == request.location) {
                            mutableState.value = mutableState.value.copy(
                                loading = false,
                                refreshStatus = staged.refreshStatus,
                                lastError = "Refresh failed; showing the last active catalog.",
                            )
                            activationTerminal = true
                            completion.complete(DebugActivationResult.Failure("Refresh failed"))
                            signalChange()
                            return@synchronized
                        }
                        val interval = selectionInterval(staged.recording)
                        val previousTargetSelection = previous?.takeIf { it.location == request.location }
                        val selectedInstant = chooseSelectedInstant(
                            if (savedOnlyPreview) previousTargetSelection?.selectedInstant
                            else previous?.selectedInstant ?: pendingSelectionAnchor,
                            interval, staged.recording, request.origin,
                        )
                        val nextGeneration = ++nextViewGeneration
                        pendingSelectionAnchor = null
                        activeInterval = interval
                        selectedLocation = request.location
                        lastRequest = request
                        mutableState.value = mutableState.value.copy(
                            selectedLocation = request.location,
                            selectedLocationName = if (savedOnlyPreview &&
                                mutableState.value.selectedLocation != request.location) null
                                else mutableState.value.selectedLocationName,
                            active = ActiveCatalogSelection(
                                location = request.location,
                                recording = staged.recording,
                                origin = request.origin,
                                datasetId = request.datasetId,
                                replayRecordingId = request.replayRecordingId,
                                delivery = staged.delivery,
                                freshness = staged.freshness,
                                storedAt = staged.storedAt,
                                selectedInstant = selectedInstant,
                                viewGeneration = nextGeneration,
                            ),
                            loading = false,
                            lastError = null,
                            refreshStatus = staged.refreshStatus,
                        )
                        activationTerminal = true
                        completion.complete(DebugActivationResult.Success(mutableState.value.active!!))
                        signalChange()
                    }
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                synchronized(this@CatalogActivationCoordinator) {
                    if (isCurrent(request, requestSequence, completion, savedOnlyPreview)) {
                        val status = (error as? LiveCatalogUnavailableException)?.status
                        val message = error.message ?: "Catalog activation failed (${error.javaClass.simpleName})"
                        mutableState.value = if (savedOnlyPreview) {
                            stateBeforeRequest.copy(loading = false, lastError = message, refreshStatus = status)
                        } else {
                            mutableState.value.copy(
                                active = mutableState.value.active?.takeIf { it.location == request.location },
                                loading = false,
                                lastError = message,
                                refreshStatus = status,
                            )
                        }
                        activationTerminal = true
                        completion.complete(DebugActivationResult.Failure(mutableState.value.lastError ?: "Catalog activation failed"))
                        signalChange()
                    }
                }
            }
        }
        return completion
    }

    private suspend fun loadAndValidate(
        request: CatalogActivationRequest,
        requestSequence: Long,
        completion: CompletableDeferred<DebugActivationResult>,
        savedOnlyPreview: Boolean,
        validateSavedMetadata: ((CatalogRecording) -> Boolean)?,
    ): StagedCatalog {
        val staged = if (savedOnlyPreview) {
            loadSavedOnly(request, requireNotNull(validateSavedMetadata))
        } else when (request.origin) {
            CatalogOrigin.LIVE -> {
                val result = liveRepository.load(request.location, request.datasetId, request.forceLiveRefresh)
                val recording = result.recording ?: throw LiveCatalogUnavailableException(
                    result.refresh, "No valid LIVE catalog is available (${result.refresh.name.lowercase()})",
                )
                require(recording.catalog.location == request.location &&
                    recording.catalog.datasetId == request.datasetId &&
                    recording.catalog.provenance.origin == CatalogOrigin.LIVE) {
                    "LIVE recording identity or provenance does not match the request"
                }
                StagedCatalog(recording,
                    if (result.delivery == CatalogDelivery.CACHE) CatalogActivationDelivery.CACHE else CatalogActivationDelivery.SOURCE,
                    result.freshness,
                    result.storedAt, result.refresh)
            }
            CatalogOrigin.SIMULATED, CatalogOrigin.REPLAY -> loadIsolated(request)
        }
        currentCoroutineSequence(request, requestSequence, completion, savedOnlyPreview)
        // The codec is the canonical whole-recording envelope validator. A source result that
        // cannot be recorded must not become active even if a provider boundary returned it.
        if (request.origin != CatalogOrigin.REPLAY) RecordedCatalogCodec.encode(staged.recording)
        return staged
    }

    private suspend fun loadSavedOnly(
        request: CatalogActivationRequest,
        validateSavedMetadata: (CatalogRecording) -> Boolean,
    ): StagedCatalog {
        val namespace = when (request.origin) {
            CatalogOrigin.SIMULATED -> CatalogStorageNamespace.SIMULATED
            CatalogOrigin.REPLAY -> CatalogStorageNamespace.REPLAY
            CatalogOrigin.LIVE -> error("LIVE cannot use saved-only preview")
        }
        return when (val saved = storage.load(request.location, namespace, request.datasetId)) {
            is StoredCatalogResult.Found -> {
                require(validateSavedMetadata(saved.recording)) {
                    "Saved catalog metadata is missing or inconsistent with its canonical recording"
                }
                val activeRecording = if (request.origin == CatalogOrigin.REPLAY) asReplay(saved.recording)
                    else saved.recording
                StagedCatalog(activeRecording, CatalogActivationDelivery.CACHE, null, saved.storedAt)
            }
            is StoredCatalogResult.Invalid -> error(
                "Saved ${request.origin.name} catalog is invalid: ${saved.reasons.joinToString("; ")}",
            )
            StoredCatalogResult.Missing -> error("Saved ${request.origin.name} catalog was not found")
        }
    }

    private suspend fun loadIsolated(request: CatalogActivationRequest): StagedCatalog {
        val namespace = when (request.origin) {
            CatalogOrigin.SIMULATED -> CatalogStorageNamespace.SIMULATED
            CatalogOrigin.REPLAY -> CatalogStorageNamespace.REPLAY
            CatalogOrigin.LIVE -> error("LIVE uses its repository")
        }
        when (val cached = storage.load(request.location, namespace, request.datasetId)) {
            is StoredCatalogResult.Found -> {
                val activeRecording = if (request.origin == CatalogOrigin.REPLAY) asReplay(cached.recording) else cached.recording
                return StagedCatalog(activeRecording, CatalogActivationDelivery.CACHE, null, cached.storedAt)
            }
            is StoredCatalogResult.Invalid, StoredCatalogResult.Missing -> Unit
        }

        val source = sources.source(request.origin) ?: throw IllegalStateException("No source registered for ${request.origin}")
        val sourceRequest = CatalogLoadRequest(request.location, request.datasetId, clock.instant())
        val result = sources.load(request.origin, sourceRequest)
        val capturedRecording = when (result) {
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport, result.metadata.rawCapture)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport, result.metadata.rawCapture)
            else -> throw IllegalStateException("${request.origin} source did not return a usable catalog")
        }
        require(capturedRecording.catalog.location == request.location &&
            capturedRecording.catalog.datasetId == request.datasetId &&
            capturedRecording.catalog.provenance.origin == request.origin) {
            "Source recording identity or provenance does not match the request"
        }
        val storedRecording = if (request.origin == CatalogOrigin.REPLAY) asCapturedReplay(capturedRecording, result.metadata.replayOriginalOrigin)
            else capturedRecording
        RecordedCatalogCodec.encode(storedRecording)
        // Storage failures do not discard a validated source result.
        val persistedAt = clock.instant()
        try {
            storage.save(namespace, storedRecording, persistedAt)
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            // Persistence is independent of activation once the source result is validated.
        }
        val activeRecording = if (request.origin == CatalogOrigin.REPLAY) asReplay(storedRecording) else storedRecording
        return StagedCatalog(activeRecording, CatalogActivationDelivery.SOURCE,
            null, persistedAt)
    }

    private fun asCapturedReplay(recording: CatalogRecording, originalOrigin: CatalogOrigin?): CatalogRecording {
        require(originalOrigin == CatalogOrigin.LIVE || originalOrigin == CatalogOrigin.SIMULATED) {
            "REPLAY source must identify a captured LIVE or SIMULATED origin"
        }
        return recording.copy(catalog = copyWithOrigin(recording.catalog, originalOrigin))
    }

    private fun asReplay(recording: CatalogRecording): CatalogRecording {
        require(recording.catalog.provenance.origin != CatalogOrigin.REPLAY) {
            "Stored REPLAY recordings must retain captured origin provenance"
        }
        return recording.copy(catalog = copyWithOrigin(recording.catalog, CatalogOrigin.REPLAY))
    }

    private fun copyWithOrigin(catalog: CanonicalCatalog, origin: CatalogOrigin) = CanonicalCatalog(
        catalog.location, catalog.datasetId, catalog.provenance.copy(origin = origin), catalog.asOf,
        catalog.facts, catalog.productSegments, catalog.dailySummaries,
    )

    private fun chooseSelectedInstant(previous: Instant?, interval: SupportedTimeInterval, recording: CatalogRecording, origin: CatalogOrigin): Instant {
        return if (previous != null) InstantTimeAxis.preserveOrClampSelection(previous, interval)
        else InstantTimeAxis.resolveResetTarget(
            if (origin == CatalogOrigin.LIVE) clock.instant() else recording.catalog.asOf,
            interval,
        )
    }

    private fun selectionInterval(recording: CatalogRecording): SupportedTimeInterval {
        val result = CatalogWindow.create(
            recording.catalog, recording.expectedSupport,
            dailyExpectedSupport = recording.dailyExpectedSupport,
        )
        val window = (result as? CatalogWindowResult.Success)?.window
            ?: error("Validated recording has no valid catalog window")
        return SupportedTimeInterval(window.startInclusive, window.endExclusive.minusNanos(1))
    }

    private fun currentCoroutineSequence(
        request: CatalogActivationRequest,
        requestSequence: Long,
        completion: CompletableDeferred<DebugActivationResult>,
        savedOnlyPreview: Boolean,
    ) {
        synchronized(this) {
            if (!isCurrent(request, requestSequence, completion, savedOnlyPreview)) {
                throw CancellationException("Catalog request was superseded")
            }
        }
    }

    private fun isCurrent(
        request: CatalogActivationRequest,
        requestSequence: Long,
        completion: CompletableDeferred<DebugActivationResult>,
        savedOnlyPreview: Boolean,
    ): Boolean = sequence == requestSequence && currentCompletion === completion &&
        (savedOnlyPreview || selectedLocation == request.location)

    private data class StagedCatalog(
        val recording: CatalogRecording,
        val delivery: CatalogActivationDelivery,
        val freshness: CatalogFreshness?,
        val storedAt: Instant?,
        val refreshStatus: CatalogRefreshStatus? = null,
    )

    private class LiveCatalogUnavailableException(val status: CatalogRefreshStatus, message: String) : IllegalStateException(message)
}

fun interface StartupLocationRestorer {
    suspend fun restore(): SavedLocationResult
}
