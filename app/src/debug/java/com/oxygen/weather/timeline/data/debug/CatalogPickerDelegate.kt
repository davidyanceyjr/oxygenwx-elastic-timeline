package com.oxygen.weather.timeline.data.debug

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oxygen.weather.timeline.ActivityVariantDelegate
import com.oxygen.weather.timeline.presentation.CatalogDiagnosticPresentation
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

internal enum class DiagnosticGroup {
    CATALOG, SELECTION, MISSING;

    fun label() = when (this) {
        CATALOG -> "Catalog and delivery state"
        SELECTION -> "Selected time and sample support"
        MISSING -> "Unavailable fields"
    }
}

internal object DiagnosticPresetCodec {
    fun encode(groups: Set<DiagnosticGroup>) = groups.sortedBy { it.ordinal }.joinToString(",") { it.name }
    fun decode(value: String) = value.split(',').mapNotNull { name ->
        DiagnosticGroup.entries.firstOrNull { it.name == name }
    }.toSet()
}

/** Debug-build request decoder. MainActivity remains the sole activation owner. */
class CatalogPickerDelegate : ActivityVariantDelegate {
    private var requestError: String? = null
    private var previewFailureShownInLibrary = false
    private var entryJob: Job? = null
    private var entryGeneration = 0L
    private var sessionGeneration = 0L
    private var sessionCheckpoint: CatalogActivationCoordinator.Checkpoint? = null
    private val sessionRequestJobs = linkedSetOf<Job>()
    private var overlayVisible by mutableStateOf(false)
    private var selectedGroups by mutableStateOf(DiagnosticGroup.entries.toSet())
    private var configurePresets by mutableStateOf(false)
    private var presetName by mutableStateOf("")
    private var selectedPreset by mutableStateOf<String?>(null)

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
        publishRelay(state)
    }

    private fun publishRelay(state: CatalogActivationState) {
        val activation = if (previewFailureShownInLibrary) state.copy(lastError = null) else state
        DebugCatalogRelay.publish(activation, if (previewFailureShownInLibrary) null else requestError)
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

    @Composable
    override fun MainScreenOverlay(host: ActivityVariantDelegate.Host, diagnostics: CatalogDiagnosticPresentation) {
        val activeOrigin = host.activationCoordinator.state.value.active?.origin
        val sourceDetail = when (activeOrigin) {
            CatalogOrigin.SIMULATED -> "SIMULATED source"
            CatalogOrigin.REPLAY -> "REPLAY · captured from SIMULATED"
            else -> null
        }
        if (sourceDetail == null && !overlayVisible) return
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (sourceDetail != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xF2F2F6F5),
                    shape = RoundedCornerShape(14.dp),
                    tonalElevation = 3.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Surface(
                            color = Color(0xFFFFF4D8),
                            shape = RoundedCornerShape(50),
                        ) {
                            Text(
                                "DEMO DATA",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                color = Color(0xFF166F7B),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1,
                            )
                        }
                        Text(
                            sourceDetail,
                            modifier = Modifier.weight(1f).padding(start = 10.dp),
                            color = Color(0xFF4C687A),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.End,
                            maxLines = 2,
                        )
                    }
                }
            }
            if (overlayVisible) {
                val anyGroup = selectedGroups.isNotEmpty()
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xE6102030), shape = RoundedCornerShape(14.dp), tonalElevation = 3.dp,
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Text("DIAGNOSTICS · LIVE VIEW", color = Color(0xFF9FE5D7), fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall)
                        if (DiagnosticGroup.CATALOG in selectedGroups) Text(
                            "Catalog diagnostics · ${diagnostics.catalog.joinToString(" · ")}",
                            color = Color.White, style = MaterialTheme.typography.labelSmall,
                        )
                        if (DiagnosticGroup.SELECTION in selectedGroups) {
                            Text("Time diagnostics · ${diagnostics.selection.firstOrNull() ?: "unavailable"}", color = Color.White,
                                style = MaterialTheme.typography.labelSmall)
                            diagnostics.selection.drop(1).forEach {
                                Text(it, color = Color.White, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                        if (DiagnosticGroup.MISSING in selectedGroups) Text(
                            "Missing-field diagnostics · ${diagnostics.missingFields.joinToString()}",
                            color = Color.White, style = MaterialTheme.typography.labelSmall,
                        )
                        if (!anyGroup) Text("No diagnostic groups selected", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }

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
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text("Diagnostic overlay", color = Color.White)
                                androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))
                                androidx.compose.material3.Switch(checked = overlayVisible, onCheckedChange = { overlayVisible = it })
                            }
                            Text("Saved display presets", style = MaterialTheme.typography.labelMedium, color = Color(0xFF9FE5D7))
                            savedPresets(host).forEach { (name, groups) ->
                                TextButton(onClick = { selectedGroups = groups; selectedPreset = name }) {
                                    Text(if (selectedPreset == name) "$name · selected" else name, color = Color.White)
                                }
                            }
                            TextButton(onClick = { configurePresets = true }) { Text("Configure and save preset", color = Color.White) }
                            Spacer(Modifier.height(10.dp))
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
        if (configurePresets) {
            AlertDialog(
                onDismissRequest = { configurePresets = false },
                title = { Text("Diagnostic display groups") },
                text = {
                    androidx.compose.foundation.layout.Column {
                        DiagnosticGroup.entries.forEach { group ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = group in selectedGroups, onCheckedChange = { checked ->
                                    selectedGroups = if (checked) selectedGroups + group else selectedGroups - group
                                })
                                Text(group.label())
                            }
                        }
                        OutlinedTextField(value = presetName, onValueChange = { presetName = it.take(32) }, label = { Text("Preset name") })
                        savedPresets(host).forEach { (name, groups) ->
                            TextButton(onClick = { presetName = name; selectedGroups = groups; selectedPreset = name }) { Text("Update $name") }
                        }
                    }
                },
                confirmButton = { TextButton(
                    enabled = presetName.isNotBlank(),
                    onClick = { savePreset(host, presetName, selectedGroups); selectedPreset = presetName.trim(); configurePresets = false },
                ) { Text("Save") } },
                dismissButton = { TextButton(onClick = { configurePresets = false }) { Text("Cancel") } },
            )
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
        previewFailureShownInLibrary = false
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
                        publishRelay(host.activationCoordinator.state.value)
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
                                request.getBooleanExtra(EXTRA_SAVED_PREVIEW, false),
                            )
                        },
                        findLocation = { host.savedLocations.find(it) },
                        stillCurrent = isCurrentSessionRequest,
                        previewSaved = { activationRequest ->
                            val db = androidx.room.Room.databaseBuilder(host.activity.applicationContext,
                                com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase::class.java,
                                "oxygen-time-lab-catalog.db")
                                .addMigrations(
                                    com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase.MIGRATION_1_2,
                                    com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase.MIGRATION_2_3,
                                ).build()
                            try {
                                val repository = DebugAuthoredCatalogRepository(
                                    com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorage(db.datasets()),
                                    DebugAuthoredCatalogMetadataStore(host.activity.getSharedPreferences(
                                        "debug_authored_catalogs", android.content.Context.MODE_PRIVATE)),
                                )
                                val completion = host.activationCoordinator.requestSavedCatalogPreview(activationRequest,
                                    repository.previewMetadataValidator(activationRequest.location, activationRequest.datasetId))
                                when (val result = completion.await()) {
                                    is DebugActivationResult.Success -> true
                                    is DebugActivationResult.Failure -> error(result.message)
                                    DebugActivationResult.Superseded -> false
                                }
                            } finally { db.close() }
                        },
                    )
                },
                coordinator = host.activationCoordinator,
                participant = participant,
                showPicker = {
                    if (request.getBooleanExtra(EXTRA_SAVED_PREVIEW, false)) {
                        showPicker(host, openAuthoredLibrary = true, previewError = requestError)
                    } else showPicker(host)
                },
                showPickerAfterSuccess = { !request.getBooleanExtra(EXTRA_SAVED_PREVIEW, false) },
                stillCurrent = isCurrentSessionRequest,
                reportFailure = { error ->
                    requestError = error.message ?: "Debug activation request was rejected"
                    previewFailureShownInLibrary = request.getBooleanExtra(EXTRA_SAVED_PREVIEW, false)
                    publishRelay(host.activationCoordinator.state.value)
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
        overlayVisible = false
        selectedGroups = DiagnosticGroup.entries.toSet()
        selectedPreset = null
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
        overlayVisible = false
        configurePresets = false
        requestError = null
        previewFailureShownInLibrary = false
        host.setDebugSessionMenuVisible(false)
        val checkpoint = sessionCheckpoint
        sessionCheckpoint = null
        if (checkpoint != null) host.activationCoordinator.restoreCheckpoint(checkpoint)
    }

    private fun preferences(host: ActivityVariantDelegate.Host) =
        host.activity.getSharedPreferences("debug_diagnostic_presets", android.content.Context.MODE_PRIVATE)

    private fun savedPresets(host: ActivityVariantDelegate.Host): List<Pair<String, Set<DiagnosticGroup>>> =
        preferences(host).all.entries.mapNotNull { (key, value) ->
            if (!key.startsWith("preset:") || value !is String) return@mapNotNull null
            val groups = DiagnosticPresetCodec.decode(value)
            key.removePrefix("preset:") to groups
        }.sortedBy { it.first }

    private fun savePreset(host: ActivityVariantDelegate.Host, rawName: String, groups: Set<DiagnosticGroup>) {
        val name = rawName.trim().take(32)
        if (name.isEmpty()) return
        preferences(host).edit().putString("preset:$name", DiagnosticPresetCodec.encode(groups)).apply()
    }

    private fun showPicker(
        host: ActivityVariantDelegate.Host,
        openAuthoredLibrary: Boolean = false,
        previewError: String? = null,
    ) {
        host.activity.startActivity(Intent(host.activity, DebugCatalogActivity::class.java)
            .putExtra(DebugCatalogActivity.EXTRA_DEBUG_SESSION, true)
            .putExtra(DebugCatalogActivity.EXTRA_OPEN_AUTHORED_LIBRARY, openAuthoredLibrary)
            .putExtra(DebugCatalogActivity.EXTRA_AUTHORED_PREVIEW_ERROR, previewError))
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
        const val EXTRA_SAVED_PREVIEW = "debug_catalog_saved_preview"
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
    showPickerAfterSuccess: () -> Boolean = { true },
) {
    try {
        if (work() && stillCurrent() && (participant == null || coordinator.finishAlternateStartup(participant)) &&
            showPickerAfterSuccess()) showPicker()
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
    val savedPreview: Boolean = false,
)

/** Shared startup request path; Android Intent access stays at the calling boundary. */
internal suspend fun processCatalogActivationRequest(
    coordinator: CatalogActivationCoordinator,
    participant: Long?,
    readRequest: suspend () -> CatalogIntentValues,
    findLocation: suspend (LocationId) -> SavedLocationResult,
    applySelection: suspend (Instant, Long) -> Unit = { instant, generation -> coordinator.selectInstant(instant, generation) },
    stillCurrent: () -> Boolean = { true },
    previewSaved: suspend (CatalogActivationRequest) -> Boolean = { request ->
        val result = coordinator.requestDebugCatalog(request).await()
        when (result) {
            is DebugActivationResult.Success -> true
            is DebugActivationResult.Failure -> error(result.message)
            DebugActivationResult.Superseded -> false
        }
    },
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
    val activationRequest = CatalogActivationRequest(saved.location.location, origin, datasetId, replayId, values.forceRefresh)
    if (values.savedPreview) {
        require(origin == CatalogOrigin.SIMULATED || origin == CatalogOrigin.REPLAY) { "Only synthetic records can be previewed" }
        require(selectedInstant == null) { "Saved preview cannot include a selected-time override" }
        return previewSaved(activationRequest)
    }
    return activateCatalogForRequest(coordinator, participant, activationRequest, selectedInstant, applySelection, stillCurrent)
}
