package com.oxygen.weather.timeline.data.catalog

import java.time.Clock
import java.time.Duration
import java.time.Instant
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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
    val active: ActiveCatalogSelection? = null,
    val loading: Boolean = false,
    val lastError: String? = null,
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

    /** Restore independently of selector visibility. A newer committed choice wins the race. */
    fun restoreStartup() {
        val restoreSequence = synchronized(this) { ++sequence }
        scope.launch {
            val result = try {
                savedLocations.restore()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                synchronized(this@CatalogActivationCoordinator) {
                    if (sequence == restoreSequence) mutableState.value = mutableState.value.copy(
                        loading = false,
                        lastError = error.message ?: "Saved location restoration failed (${error.javaClass.simpleName})",
                    )
                }
                return@launch
            }
            synchronized(this@CatalogActivationCoordinator) {
                if (sequence != restoreSequence) return@synchronized
                when (result) {
                    is SavedLocationResult.Selected -> {
                        selectedLocation = result.location?.location
                        mutableState.value = mutableState.value.copy(selectedLocation = selectedLocation)
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
        }
    }

    /** Call only after the selector has persisted the selection successfully. */
    fun committedLocationSelection(location: CatalogLocation) {
        synchronized(this) {
            selectedLocation = location
            mutableState.value = mutableState.value.copy(selectedLocation = location)
            beginRequest(CatalogActivationRequest(location, CatalogOrigin.LIVE, startupDatasetId))
        }
    }

    /** Activate a validated debug choice for this process without persisting a location choice. */
    fun requestDebugCatalog(request: CatalogActivationRequest) {
        synchronized(this) {
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

    private fun beginRequest(request: CatalogActivationRequest) {
        sequence++
        val requestSequence = sequence
        requestJob?.cancel()
        lastRequest = request
        mutableState.value = mutableState.value.copy(loading = true, lastError = null)
        requestJob = scope.launch {
            try {
                val staged = loadAndValidate(request, requestSequence)
                synchronized(this@CatalogActivationCoordinator) {
                    if (isCurrent(request, requestSequence)) {
                        val previous = mutableState.value.active
                        val selectedInstant = chooseSelectedInstant(previous?.selectedInstant, staged.recording)
                        val nextGeneration = (previous?.viewGeneration ?: 0L) + 1L
                        mutableState.value = mutableState.value.copy(
                            selectedLocation = request.location,
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
                        )
                    }
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                synchronized(this@CatalogActivationCoordinator) {
                    if (isCurrent(request, requestSequence)) mutableState.value = mutableState.value.copy(
                        loading = false,
                        lastError = error.message ?: "Catalog activation failed (${error.javaClass.simpleName})",
                    )
                }
            }
        }
    }

    private suspend fun loadAndValidate(request: CatalogActivationRequest, requestSequence: Long): StagedCatalog {
        val staged = when (request.origin) {
            CatalogOrigin.LIVE -> {
                val result = liveRepository.load(request.location, request.datasetId, request.forceLiveRefresh)
                val recording = result.recording ?: throw IllegalStateException("No valid LIVE catalog is available")
                require(recording.catalog.location == request.location &&
                    recording.catalog.datasetId == request.datasetId &&
                    recording.catalog.provenance.origin == CatalogOrigin.LIVE) {
                    "LIVE recording identity or provenance does not match the request"
                }
                StagedCatalog(recording,
                    if (result.delivery == CatalogDelivery.CACHE) CatalogActivationDelivery.CACHE else CatalogActivationDelivery.SOURCE,
                    result.freshness,
                    result.storedAt)
            }
            CatalogOrigin.SIMULATED, CatalogOrigin.REPLAY -> loadIsolated(request)
        }
        currentCoroutineSequence(request, requestSequence)
        // The codec is the canonical whole-recording envelope validator. A source result that
        // cannot be recorded must not become active even if a provider boundary returned it.
        if (request.origin != CatalogOrigin.REPLAY) RecordedCatalogCodec.encode(staged.recording)
        return staged
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
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
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

    private fun chooseSelectedInstant(previous: Instant?, recording: CatalogRecording): Instant {
        val instants = recording.catalog.facts.map { it.validTime }.distinct().sorted()
        require(instants.isNotEmpty()) { "Cannot activate a catalog without valid samples" }
        val target = previous ?: clock.instant()
        return instants.minWithOrNull(compareBy<Instant>({ Duration.between(target, it).abs() }, { it }))!!
    }

    private fun currentCoroutineSequence(request: CatalogActivationRequest, requestSequence: Long) {
        synchronized(this) {
            if (!isCurrent(request, requestSequence)) throw CancellationException("Catalog request was superseded")
        }
    }

    private fun isCurrent(request: CatalogActivationRequest, requestSequence: Long): Boolean =
        sequence == requestSequence && selectedLocation == request.location

    private data class StagedCatalog(
        val recording: CatalogRecording,
        val delivery: CatalogActivationDelivery,
        val freshness: CatalogFreshness?,
        val storedAt: Instant?,
    )
}

fun interface StartupLocationRestorer {
    suspend fun restore(): SavedLocationResult
}
