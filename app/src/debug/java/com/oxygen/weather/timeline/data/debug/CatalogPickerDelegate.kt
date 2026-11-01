package com.oxygen.weather.timeline.data.debug

import android.content.Intent
import com.oxygen.weather.timeline.ActivityVariantDelegate
import com.oxygen.weather.timeline.data.catalog.CatalogActivationRequest
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocationResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import java.time.Duration
import java.time.Instant

/** Debug-build request decoder. MainActivity remains the sole activation owner. */
class CatalogPickerDelegate : ActivityVariantDelegate {
    private var requestError: String? = null

    override fun onMainActivityCreated(host: ActivityVariantDelegate.Host, intent: Intent): Boolean {
        if (!hasCatalogRequest(intent)) return false
        handleRequest(host, intent, coldLaunch = true)
        return true
    }

    override fun onMainActivityNewIntent(host: ActivityVariantDelegate.Host, intent: Intent): Boolean {
        if (!hasCatalogRequest(intent)) return false
        handleRequest(host, intent, coldLaunch = false)
        return true
    }

    override fun onCatalogActivationState(host: ActivityVariantDelegate.Host, state: CatalogActivationState) {
        DebugCatalogRelay.publish(state, requestError)
    }

    override fun createFixturePlaceSearcher(host: ActivityVariantDelegate.Host): PlaceSearcher =
        DebugLocationSearchFactory.create()

    override fun openCatalogPicker(host: ActivityVariantDelegate.Host): Boolean {
        showPicker(host)
        return true
    }

    private fun hasCatalogRequest(intent: Intent): Boolean =
        intent.action == ACTION_ACTIVATE_CATALOG || intent.action == ACTION_SELECT_CATALOG_INSTANT || intent.hasExtra(EXTRA_VERSION) ||
            intent.hasExtra(EXTRA_LOCATION_ID) || intent.hasExtra(EXTRA_ORIGIN) ||
            intent.hasExtra(EXTRA_DATASET_ID) || intent.hasExtra(EXTRA_REPLAY_ID)

    private fun handleRequest(host: ActivityVariantDelegate.Host, request: Intent, coldLaunch: Boolean) {
        requestError = null
        host.activityScope.launch {
            try {
                if (request.action == ACTION_SELECT_CATALOG_INSTANT) {
                    require(!coldLaunch) { "Exact-time selection requires an active debug session" }
                    require(request.getIntExtra(EXTRA_VERSION, -1) == CONTRACT_VERSION) {
                        "Unsupported debug selection request version"
                    }
                    val dataset = request.getStringExtra(EXTRA_DATASET_ID)
                    require(dataset == DebugCatalogActivity.LIVE_DATASET_ID) {
                        "Solar-time selection is only available for the LIVE forecast catalog"
                    }
                    val instant = Instant.parse(request.getStringExtra(EXTRA_SELECTED_INSTANT)
                        ?: error("Missing selected instant"))
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
                    return@launch
                }
                require(request.action == ACTION_ACTIVATE_CATALOG) { "Unknown debug activation action" }
                require(request.getIntExtra(EXTRA_VERSION, -1) == CONTRACT_VERSION) {
                    "Unsupported debug activation request version"
                }
                val locationIdValue = request.getStringExtra(EXTRA_LOCATION_ID)?.takeIf(String::isNotBlank)
                    ?: error("Missing saved location ID")
                val originValue = request.getStringExtra(EXTRA_ORIGIN)?.takeIf(String::isNotBlank)
                    ?: error("Missing catalog origin")
                val datasetIdValue = request.getStringExtra(EXTRA_DATASET_ID)?.takeIf(String::isNotBlank)
                    ?: error("Missing dataset ID")
                val locationId = LocationId(locationIdValue)
                val origin = CatalogOrigin.valueOf(originValue)
                val datasetId = DatasetId(datasetIdValue)
                val replayValue = request.getStringExtra(EXTRA_REPLAY_ID)
                val replayId = replayValue?.takeIf(String::isNotBlank)?.let(::DatasetId)
                require((origin == CatalogOrigin.REPLAY) == (replayId != null)) {
                    "REPLAY requests require a recording ID; other origins must not carry one"
                }
                require(replayId == null || replayId == datasetId) {
                    "Replay recording ID must match the dataset ID"
                }
                val forceRefresh = request.getBooleanExtra(EXTRA_FORCE_REFRESH, false)
                require(forceRefresh == (origin == CatalogOrigin.LIVE)) {
                    "Only LIVE requests may force a refresh"
                }
                val saved = host.savedLocations.find(locationId)
                require(saved is SavedLocationResult.Selected && saved.location != null) {
                    "Saved location is missing or malformed"
                }
                val selectedInstantValue = request.getStringExtra(EXTRA_SELECTED_INSTANT)
                val selectedInstant = selectedInstantValue?.let(Instant::parse)
                require(selectedInstant == null ||
                    (origin == CatalogOrigin.REPLAY && datasetId.value == DebugCatalogActivity.SG4_GAP_REPLAY_ID)) {
                    "Exact-time selection is only available for the SG.4 debug replay"
                }
                host.activationCoordinator.requestDebugCatalog(CatalogActivationRequest(
                    saved.location.location, origin, datasetId, replayId, forceRefresh,
                ))
                if (selectedInstant != null) {
                    val active = host.activationCoordinator.state.first { state ->
                        !state.loading && state.active?.datasetId == datasetId &&
                            state.active.location == saved.location.location
                    }.active ?: error("SG.4 replay did not become active")
                    host.activationCoordinator.selectInstant(selectedInstant, active.viewGeneration)
                }
                showPicker(host)
            } catch (error: Exception) {
                requestError = error.message ?: "Debug activation request was rejected"
                DebugCatalogRelay.publish(host.activationCoordinator.state.value, requestError)
                if (coldLaunch) host.restoreStartupCatalog()
                showPicker(host)
            }
        }
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
