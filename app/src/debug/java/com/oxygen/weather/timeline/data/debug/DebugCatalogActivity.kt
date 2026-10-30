package com.oxygen.weather.timeline.data.debug

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.oxygen.weather.timeline.MainActivity
import com.oxygen.weather.timeline.data.catalog.CatalogActivationRequest
import com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorage
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadResult
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase
import com.oxygen.weather.timeline.data.catalog.CatalogStorageNamespace
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.data.catalog.SavedLocationListResult
import com.oxygen.weather.timeline.data.catalog.SavedLocationRepository
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.StoredCatalogResult
import java.time.Instant
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

/** Debug-only chooser for canonical location catalogs. It never owns activation state. */
class DebugCatalogActivity : ComponentActivity() {
    private lateinit var database: CatalogStorageDatabase
    private lateinit var locations: SavedLocationRepository
    private lateinit var storage: CatalogDatasetStorage

    private var saved by mutableStateOf<List<SavedLocation>>(emptyList())
    private var loadError by mutableStateOf<String?>(null)
    private var replayIds by mutableStateOf<List<String>>(emptyList())
    private var fixtureStatus by mutableStateOf("Preparing Chicago replay fixture…")
    private var fixtureReady by mutableStateOf(false)
    private var reloadJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(applicationContext, CatalogStorageDatabase::class.java,
            "oxygen-time-lab-catalog.db")
            .addMigrations(CatalogStorageDatabase.MIGRATION_1_2, CatalogStorageDatabase.MIGRATION_2_3)
            .build()
        locations = SavedLocationRepository(database)
        storage = CatalogDatasetStorage(database.datasets())
        reloadLocations()
        setContent { CatalogPickerScreen() }
    }

    override fun onResume() {
        super.onResume()
        if (::locations.isInitialized) reloadLocations()
    }

    override fun onDestroy() {
        if (::database.isInitialized) database.close()
        super.onDestroy()
    }

    private suspend fun prepareChicagoReplay(location: CatalogLocation) {
        val id = DatasetId(CHICAGO_REPLAY_ID)
        val fixedAsOf = Instant.parse(CHICAGO_REPLAY_AS_OF)
        fun valid(recording: com.oxygen.weather.timeline.data.catalog.CatalogRecording) =
            recording.catalog.location == location && recording.catalog.datasetId == id &&
                recording.catalog.asOf == fixedAsOf && recording.catalog.provenance.origin == CatalogOrigin.SIMULATED

        val existing = storage.load(location, CatalogStorageNamespace.REPLAY, id)
        val retained = (existing as? StoredCatalogResult.Found)?.recording?.takeIf(::valid)
        if (retained == null) {
            fixtureStatus = "Generating deterministic Chicago replay…"
            val result = SimulatedCatalogSource().load(CatalogLoadRequest(location, id, fixedAsOf))
            val recording = when (result) {
                is CatalogLoadResult.Success -> com.oxygen.weather.timeline.data.catalog.CatalogRecording(
                    result.catalog, result.expectedSupport, result.dailyExpectedSupport)
                is CatalogLoadResult.Partial -> com.oxygen.weather.timeline.data.catalog.CatalogRecording(
                    result.catalog, result.expectedSupport, result.dailyExpectedSupport)
                else -> error("The deterministic SIMULATED source returned no usable Chicago catalog")
            }
            check(valid(recording)) { "The deterministic Chicago catalog did not match its fixed identity" }
            storage.save(CatalogStorageNamespace.REPLAY, recording)
        }
        val reloaded = storage.load(location, CatalogStorageNamespace.REPLAY, id)
        check(reloaded is StoredCatalogResult.Found && valid(reloaded.recording)) {
            "The stored Chicago replay failed canonical payload validation"
        }
        val history = storage.history(location.id, CatalogStorageNamespace.REPLAY)
        check(history.any { it.datasetId == id.value }) { "The Chicago replay is missing from REPLAY history" }
        replayIds = history.map { it.datasetId }.distinct().sorted()
        fixtureReady = true
        fixtureStatus = "Chicago replay ready · captured SIMULATED · $CHICAGO_REPLAY_AS_OF"
    }

    private fun reloadLocations() {
        reloadJob?.cancel()
        reloadJob = lifecycleScope.launch {
            when (val result = locations.list()) {
                is SavedLocationListResult.Locations -> {
                    saved = result.values
                    loadError = if (result.values.isEmpty())
                        "No saved locations. Add Chicago and Miami with the existing location selector." else null
                    result.values.firstOrNull(::isChicago)?.let { prepareChicagoReplay(it.location) }
                }
                SavedLocationListResult.MalformedRecord -> loadError =
                    "A saved location is invalid. Repair it in the location selector."
            }
        }
    }

    private fun isChicago(value: SavedLocation): Boolean = value.candidate.name.equals("Chicago", true) &&
        value.candidate.countryCode.equals("US", true)

    @androidx.compose.runtime.Composable
    private fun CatalogPickerScreen() {
        val relay by DebugCatalogRelay.state.collectAsState()
        var selectedLocation by remember(saved) { mutableStateOf(saved.firstOrNull()) }
        var selectedOrigin by remember { mutableStateOf(CatalogOrigin.SIMULATED) }
        var selectedReplay by remember(replayIds) { mutableStateOf(replayIds.singleOrNull()) }
        LaunchedEffect(selectedLocation?.location?.id) {
            val location = selectedLocation?.location ?: return@LaunchedEffect
            replayIds = storage.history(location.id, CatalogStorageNamespace.REPLAY)
                .map { it.datasetId }.distinct().sorted()
        }

        MaterialTheme {
            Column(
                Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Debug dataset picker", style = MaterialTheme.typography.headlineSmall)
                Text("Catalog activation and 44-date timeline review")
                val current = relay.activation.active
                if (current != null) Text(
                    "Active catalog: ${current.origin} · ${current.location.id.value} · ${current.datasetId.value} · " +
                        "${current.delivery}${current.freshness?.let { " · $it" } ?: ""}",
                )
                if (relay.activation.loading) Text("Catalog loading… prior valid catalog remains active.")
                relay.activation.lastError?.let { Text("Load failed · prior valid catalog kept · $it") }
                relay.requestError?.let { Text("Request rejected · no catalog load started · $it") }
                loadError?.let { Text(it) }

                Text("Location", style = MaterialTheme.typography.titleMedium)
                saved.forEach { location ->
                    val label = "${location.candidate.displayLabel} · ${location.location.timeZone.id}"
                    if (selectedLocation?.location?.id == location.location.id) Text("Selected · $label")
                    else OutlinedButton(onClick = { selectedLocation = location }) { Text(label) }
                }
                Row(
                    Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CatalogOrigin.entries.forEach { origin ->
                        if (selectedOrigin == origin) Button(onClick = { selectedOrigin = origin }) { Text(origin.name) }
                        else OutlinedButton(onClick = { selectedOrigin = origin }) { Text(origin.name) }
                    }
                }
                when (selectedOrigin) {
                    CatalogOrigin.SIMULATED -> Text("SIMULATED · $SIMULATED_DATASET_ID · deterministic synthetic catalog")
                    CatalogOrigin.LIVE -> Text("LIVE · forecast-44d · force refresh selected location")
                    CatalogOrigin.REPLAY -> {
                        Text(fixtureStatus)
                        replayIds.forEach { id ->
                            val active = selectedReplay == id
                            if (active) Text("Selected recording · $id")
                            else OutlinedButton(onClick = { selectedReplay = id }) { Text(id) }
                        }
                    }
                }
                OutlinedButton(onClick = { openLocationSelector() }) { Text("Add or manage saved locations") }
                Button(
                    enabled = selectedLocation != null && !relay.activation.loading &&
                        (selectedOrigin != CatalogOrigin.REPLAY ||
                            (fixtureReady && selectedReplay != null && selectedReplay in replayIds)),
                    onClick = {
                        val location = selectedLocation ?: return@Button
                        val dataset = when (selectedOrigin) {
                            CatalogOrigin.LIVE -> LIVE_DATASET_ID
                            CatalogOrigin.SIMULATED -> SIMULATED_DATASET_ID
                            CatalogOrigin.REPLAY -> selectedReplay ?: return@Button
                        }
                        routeActivation(location.location, selectedOrigin, dataset)
                    },
                ) { Text(if (relay.activation.loading) "Loading catalog…" else "Load dataset") }
                Text("${fixtureStatus}")
                OutlinedButton(onClick = { openLegacyProfiles() }) { Text("Open legacy forecast profiles") }
                OutlinedButton(onClick = { finish() }) { Text("Open immersive timeline") }
                if (relay.requestError != null || relay.activation.lastError != null) {
                    OutlinedButton(onClick = { routePickerAgain() }) { Text("Retry or choose another dataset") }
                }
            }
        }
    }

    private fun routeActivation(location: CatalogLocation, origin: CatalogOrigin, dataset: String) {
        val request = Intent(this, MainActivity::class.java)
            .setAction(ACTION_ACTIVATE_CATALOG)
            .putExtra(EXTRA_VERSION, CONTRACT_VERSION)
            .putExtra(EXTRA_LOCATION_ID, location.id.value)
            .putExtra(EXTRA_ORIGIN, origin.name)
            .putExtra(EXTRA_DATASET_ID, dataset)
            .putExtra(EXTRA_FORCE_REFRESH, origin == CatalogOrigin.LIVE)
        if (origin == CatalogOrigin.REPLAY) request.putExtra(EXTRA_REPLAY_ID, dataset)
        request.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(request)
        finish()
    }

    private fun routePickerAgain() = startActivity(Intent(this, DebugCatalogActivity::class.java))

    private fun openLocationSelector() = startActivity(Intent(this, MainActivity::class.java)
        .putExtra("location_selector", true).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))

    private fun openLegacyProfiles() = startActivity(Intent(this, DebugForecastActivity::class.java))

    companion object {
        const val ACTION_ACTIVATE_CATALOG = CatalogPickerDelegate.ACTION_ACTIVATE_CATALOG
        const val CONTRACT_VERSION = CatalogPickerDelegate.CONTRACT_VERSION
        const val EXTRA_VERSION = CatalogPickerDelegate.EXTRA_VERSION
        const val EXTRA_LOCATION_ID = CatalogPickerDelegate.EXTRA_LOCATION_ID
        const val EXTRA_ORIGIN = CatalogPickerDelegate.EXTRA_ORIGIN
        const val EXTRA_DATASET_ID = CatalogPickerDelegate.EXTRA_DATASET_ID
        const val EXTRA_REPLAY_ID = CatalogPickerDelegate.EXTRA_REPLAY_ID
        const val EXTRA_FORCE_REFRESH = CatalogPickerDelegate.EXTRA_FORCE_REFRESH
        const val SIMULATED_DATASET_ID = "wx22-debug-simulated-v3"
        const val LIVE_DATASET_ID = "forecast-44d"
        const val CHICAGO_REPLAY_ID = "wx22-chicago-replay-v3"
        const val CHICAGO_REPLAY_AS_OF = "2026-10-28T12:00:00Z"
    }
}
