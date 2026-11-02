package com.oxygen.weather.timeline.data.debug

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oxygen.weather.timeline.ActivityVariantDelegate
import com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinator
import com.oxygen.weather.timeline.data.catalog.CatalogActivationRequest
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.DebugActivationResult
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocationResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.time.Duration
import java.time.Instant

/** Debug-build request decoder. MainActivity remains the sole activation owner. */
class CatalogPickerDelegate : ActivityVariantDelegate {
    private var requestError: String? = null
    private var entryJob: Job? = null
    private var entryGeneration = 0L
    private var sessionGeneration = 0L
    private var sessionCheckpoint: CatalogActivationCoordinator.Checkpoint? = null
    private val sessionRequestJobs = linkedSetOf<Job>()

    override fun onMainActivityCreated(host: ActivityVariantDelegate.Host, intent: Intent): Boolean {
        if (!hasCatalogRequest(intent)) return false
        startCatalogColdLaunch(host.activationCoordinator) { participant ->
            handleRequest(host, intent, participant)
        }
        return true
    }

    override fun onMainActivityNewIntent(host: ActivityVariantDelegate.Host, intent: Intent): Boolean {
        if (!hasCatalogRequest(intent)) return false
        handleRequest(host, intent, null)
        return true
    }

    override fun onCatalogActivationState(host: ActivityVariantDelegate.Host, state: CatalogActivationState) {
        DebugCatalogRelay.publish(state, requestError)
    }

    override fun createFixturePlaceSearcher(host: ActivityVariantDelegate.Host): PlaceSearcher =
        DebugLocationSearchFactory.create()

    override fun openCatalogPicker(host: ActivityVariantDelegate.Host): Boolean {
        if (sessionCheckpoint == null) return false
        showPicker(host)
        return true
    }

    override fun onSearchSubmission(host: ActivityVariantDelegate.Host, query: String): Boolean = when (query) {
        "#DEBUG" -> {
            if (sessionCheckpoint != null) {
                host.closeLocationSelector()
                host.setDebugSessionMenuVisible(true)
            } else beginSessionEntry(host)
            true
        }
        "#PICKER" -> if (sessionCheckpoint != null) {
            host.closeLocationSelector()
            showPicker(host)
            true
        } else false
        else -> false
    }

    override fun isDebugSessionActive(host: ActivityVariantDelegate.Host) = sessionCheckpoint != null

    override suspend fun beforeOrdinaryAction(host: ActivityVariantDelegate.Host) {
        cancelPendingEntry()
        restoreAndEndSession(host)
    }

    @Composable
    override fun DebugSessionMenu(host: ActivityVariantDelegate.Host) {
        Dialog(
            onDismissRequest = { host.setDebugSessionMenuVisible(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = true),
        ) {
            Box(Modifier.fillMaxSize()) {
                Box(Modifier.matchParentSize().clickable { host.setDebugSessionMenuVisible(false) })
                MaterialTheme(colorScheme = debugMenuColors()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp,
                    ) {
                        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Spacer(Modifier.size(width = 48.dp, height = 5.dp)
                                    .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(3.dp)))
                            }
                            Spacer(Modifier.height(16.dp))
                            Text("Debug menu", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(4.dp))
                            Text("SESSION ACTIVE · DEBUG BUILD", style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.height(18.dp))
                            OutlinedButton(
                                onClick = {
                                    host.setDebugSessionMenuVisible(false)
                                    host.closeLocationSelector()
                                    openCatalogPicker(host)
                                },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text("Open catalog picker     #PICKER")
                            }
                            Spacer(Modifier.height(10.dp))
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text("OVERLAY", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.weight(1f))
                                Text("OFF", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text("Production view", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.height(12.dp))
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                TextButton(onClick = { host.setDebugSessionMenuVisible(false) }) { Text("Continue") }
                                Spacer(Modifier.weight(1f))
                                Button(onClick = { restoreAndEndSession(host) }) { Text("Exit debug") }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun debugMenuColors() = darkColorScheme(
        primary = Color(0xFF82D8C9),
        onPrimary = Color(0xFF062F32),
        surface = Color(0xFF102B3D),
        onSurface = Color(0xFFF1F6F8),
        onSurfaceVariant = Color(0xFF9AB1BE),
        outline = Color(0xFF355467),
        outlineVariant = Color(0xFF355467),
    )

    private fun hasCatalogRequest(intent: Intent): Boolean =
        intent.action == ACTION_ACTIVATE_CATALOG || intent.action == ACTION_SELECT_CATALOG_INSTANT || intent.hasExtra(EXTRA_VERSION) ||
            intent.hasExtra(EXTRA_LOCATION_ID) || intent.hasExtra(EXTRA_ORIGIN) ||
            intent.hasExtra(EXTRA_DATASET_ID) || intent.hasExtra(EXTRA_REPLAY_ID)

    private fun handleRequest(host: ActivityVariantDelegate.Host, request: Intent, participant: Long?) {
        val coldLaunch = participant != null
        val requestSessionGeneration = sessionGeneration
        val requestBelongsToSession = sessionCheckpoint != null
        val isCurrentSessionRequest = {
            !requestBelongsToSession || (sessionCheckpoint != null && sessionGeneration == requestSessionGeneration)
        }
        requestError = null
        val requestJob = host.activityScope.launch(start = CoroutineStart.LAZY) {
            runCatalogRequestLifecycle(
                work = work@{
                    if (request.action == ACTION_SELECT_CATALOG_INSTANT) {
                        require(!coldLaunch) { "Exact-time selection requires an active debug session" }
                        require(sessionCheckpoint != null) { "Exact-time selection requires an active debug session" }
                        require(request.getIntExtra(EXTRA_VERSION, -1) == CONTRACT_VERSION) {
                            "Unsupported debug selection request version"
                        }
                        val dataset = request.getStringExtra(EXTRA_DATASET_ID)
                        require(dataset == DebugCatalogActivity.LIVE_DATASET_ID) {
                            "Solar-time selection is only available for the LIVE forecast catalog"
                        }
                        val instant = Instant.parse(request.getStringExtra(EXTRA_SELECTED_INSTANT)
                            ?: error("Missing selected instant"))
                        currentCoroutineContext().ensureActive()
                        check(isCurrentSessionRequest()) { "Debug session ended before the selection was applied" }
                        val active = host.activationCoordinator.state.value.active
                            ?: error("There is no active catalog to inspect")
                        require(active.origin == CatalogOrigin.LIVE && active.datasetId.value == dataset) {
                            "The LIVE forecast catalog is not active"
                        }
                        val solarEvents = active.recording.catalog.dailySummaries.flatMap { summary ->
                            listOfNotNull(summary.sunriseInstant, summary.sunsetInstant)
                        }
                        require(solarEvents.any { Duration.between(it, instant).abs() <= Duration.ofMinutes(45) }) {
                            "Selected instant is outside the active catalog's solar inspection windows"
                        }
                        host.activationCoordinator.selectInstant(instant, active.viewGeneration)
                        DebugCatalogRelay.publish(host.activationCoordinator.state.value, requestError)
                        return@work false
                    }
                    processCatalogActivationRequest(
                        coordinator = host.activationCoordinator,
                        participant = participant,
                        readRequest = {
                            CatalogIntentValues(
                                request.action,
                                request.getIntExtra(EXTRA_VERSION, -1),
                                request.getStringExtra(EXTRA_LOCATION_ID),
                                request.getStringExtra(EXTRA_ORIGIN),
                                request.getStringExtra(EXTRA_DATASET_ID),
                                request.getStringExtra(EXTRA_REPLAY_ID),
                                request.getBooleanExtra(EXTRA_FORCE_REFRESH, false),
                                request.getStringExtra(EXTRA_SELECTED_INSTANT),
                            )
                        },
                        findLocation = { host.savedLocations.find(it) },
                        stillCurrent = isCurrentSessionRequest,
                    )
                },
                coordinator = host.activationCoordinator,
                participant = participant,
                showPicker = { showPicker(host) },
                stillCurrent = isCurrentSessionRequest,
                reportFailure = { error ->
                    requestError = error.message ?: "Debug activation request was rejected"
                    DebugCatalogRelay.publish(host.activationCoordinator.state.value, requestError)
                },
            )
        }
        if (requestBelongsToSession) {
            sessionRequestJobs += requestJob
            requestJob.invokeOnCompletion { sessionRequestJobs.remove(requestJob) }
        }
        requestJob.start()
    }

    private fun beginSessionEntry(host: ActivityVariantDelegate.Host) {
        cancelPendingEntry()
        val generation = entryGeneration
        entryJob = startPendingDebugEntry(
            scope = host.activityScope,
            coordinator = host.activationCoordinator,
            eligible = { generation == entryGeneration && sessionCheckpoint == null },
        ) { checkpoint ->
            enterDebugSession(host, checkpoint)
            if (generation == entryGeneration) entryJob = null
        }
    }

    internal fun enterDebugSession(host: ActivityVariantDelegate.Host, checkpoint: CatalogActivationCoordinator.Checkpoint) {
        sessionCheckpoint = checkpoint
        sessionGeneration++
        host.closeLocationSelector()
        host.setDebugSessionMenuVisible(true)
    }

    private fun cancelPendingEntry() {
        entryGeneration++
        entryJob?.cancel()
        entryJob = null
    }

    private fun restoreAndEndSession(host: ActivityVariantDelegate.Host) {
        sessionGeneration++
        sessionRequestJobs.toList().forEach(Job::cancel)
        sessionRequestJobs.clear()
        host.setDebugSessionMenuVisible(false)
        val checkpoint = sessionCheckpoint
        sessionCheckpoint = null
        if (checkpoint != null) host.activationCoordinator.restoreCheckpoint(checkpoint)
    }

    private fun showPicker(host: ActivityVariantDelegate.Host) {
        host.activity.startActivity(Intent(host.activity, DebugCatalogActivity::class.java))
    }

    companion object {
        const val ACTION_ACTIVATE_CATALOG = "com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG"
        const val ACTION_SELECT_CATALOG_INSTANT = "com.oxygen.weather.timeline.DEBUG_SELECT_CATALOG_INSTANT"
        const val CONTRACT_VERSION = 1
        const val EXTRA_VERSION = "debug_catalog_version"
        const val EXTRA_LOCATION_ID = "debug_catalog_location_id"
        const val EXTRA_ORIGIN = "debug_catalog_origin"
        const val EXTRA_DATASET_ID = "debug_catalog_dataset_id"
        const val EXTRA_REPLAY_ID = "debug_catalog_replay_id"
        const val EXTRA_FORCE_REFRESH = "debug_catalog_force_refresh"
        const val EXTRA_SELECTED_INSTANT = "debug_catalog_selected_instant"
    }
}

internal fun startPendingDebugEntry(
    scope: kotlinx.coroutines.CoroutineScope,
    coordinator: CatalogActivationCoordinator,
    eligible: () -> Boolean,
    onEntered: (CatalogActivationCoordinator.Checkpoint) -> Unit,
): Job = scope.launch {
    try {
        val checkpoint = coordinator.captureSettledCheckpoint()
        if (eligible()) onEntered(checkpoint)
    } catch (cancelled: CancellationException) {
        throw cancelled
    }
}

/** Runs the actual delegate handshake with cancellation kept distinct from failure. */
internal suspend fun runCatalogRequestLifecycle(
    work: suspend () -> Boolean,
    coordinator: CatalogActivationCoordinator,
    participant: Long?,
    showPicker: () -> Unit,
    reportFailure: (Exception) -> Unit,
    stillCurrent: () -> Boolean = { true },
) {
    try {
        if (work() && stillCurrent() && (participant == null || coordinator.finishAlternateStartup(participant))) showPicker()
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (error: Exception) {
        if (stillCurrent() && (participant == null || coordinator.fallbackStartup(participant))) {
            reportFailure(error)
            showPicker()
        }
    }
}

/** The delegate's request completion and fixture-selection handshake. */
internal suspend fun activateCatalogForRequest(
    coordinator: CatalogActivationCoordinator,
    participant: Long?,
    request: CatalogActivationRequest,
    selectedInstant: Instant?,
    applySelection: suspend (Instant, Long) -> Unit = { instant, generation -> coordinator.selectInstant(instant, generation) },
    stillCurrent: () -> Boolean = { true },
): Boolean {
    currentCoroutineContext().ensureActive()
    if (!stillCurrent()) return false
    val completion = if (participant != null)
        coordinator.requestAlternateStartupCatalog(participant, request) ?: return false
    else coordinator.requestDebugCatalog(request)
    when (val result = completion.await()) {
        is DebugActivationResult.Success -> if (selectedInstant != null) {
            currentCoroutineContext().ensureActive()
            if (stillCurrent()) applySelection(selectedInstant, result.selection.viewGeneration)
        }
        is DebugActivationResult.Failure -> error(result.message)
        DebugActivationResult.Superseded -> Unit
    }
    return true
}

/** Registers the startup participant before the host schedules request handling. */
internal fun startCatalogColdLaunch(coordinator: CatalogActivationCoordinator, launch: (Long) -> Unit) {
    val participant = coordinator.beginAlternateStartup()
    launch(participant)
}

internal data class CatalogIntentValues(
    val action: String?,
    val version: Int,
    val locationId: String?,
    val origin: String?,
    val datasetId: String?,
    val replayId: String?,
    val forceRefresh: Boolean,
    val selectedInstant: String?,
)

/** Shared startup request path; Android Intent access stays at the calling boundary. */
internal suspend fun processCatalogActivationRequest(
    coordinator: CatalogActivationCoordinator,
    participant: Long?,
    readRequest: suspend () -> CatalogIntentValues,
    findLocation: suspend (LocationId) -> SavedLocationResult,
    applySelection: suspend (Instant, Long) -> Unit = { instant, generation -> coordinator.selectInstant(instant, generation) },
    stillCurrent: () -> Boolean = { true },
): Boolean {
    val values = readRequest()
    currentCoroutineContext().ensureActive()
    check(stillCurrent()) { "Debug session ended while decoding the catalog request" }
    require(values.action == CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG) { "Unknown debug activation action" }
    require(values.version == CatalogPickerDelegate.CONTRACT_VERSION) { "Unsupported debug activation request version" }
    val locationId = LocationId(values.locationId?.takeIf(String::isNotBlank) ?: error("Missing saved location ID"))
    val origin = CatalogOrigin.valueOf(values.origin?.takeIf(String::isNotBlank) ?: error("Missing catalog origin"))
    val datasetId = DatasetId(values.datasetId?.takeIf(String::isNotBlank) ?: error("Missing dataset ID"))
    val replayId = values.replayId?.takeIf(String::isNotBlank)?.let(::DatasetId)
    require((origin == CatalogOrigin.REPLAY) == (replayId != null)) {
        "REPLAY requests require a recording ID; other origins must not carry one"
    }
    require(replayId == null || replayId == datasetId) { "Replay recording ID must match the dataset ID" }
    require(values.forceRefresh == (origin == CatalogOrigin.LIVE)) { "Only LIVE requests may force a refresh" }
    val saved = findLocation(locationId)
    currentCoroutineContext().ensureActive()
    check(stillCurrent()) { "Debug session ended while looking up the saved location" }
    require(saved is SavedLocationResult.Selected && saved.location != null) { "Saved location is missing or malformed" }
    val selectedInstant = values.selectedInstant?.let(Instant::parse)
    require(selectedInstant == null ||
        (origin == CatalogOrigin.REPLAY && datasetId.value == DebugCatalogActivity.SG4_GAP_REPLAY_ID)) {
        "Exact-time selection is only available for the SG.4 debug replay"
    }
    return activateCatalogForRequest(coordinator, participant,
        CatalogActivationRequest(saved.location.location, origin, datasetId, replayId, values.forceRefresh), selectedInstant,
        applySelection, stillCurrent)
}
