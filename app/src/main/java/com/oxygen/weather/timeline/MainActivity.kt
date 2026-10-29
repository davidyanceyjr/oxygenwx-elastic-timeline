package com.oxygen.weather.timeline

import android.os.Bundle
import android.content.pm.ApplicationInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.presentation.ForecastProjection
import com.oxygen.weather.timeline.presentation.SkyFrame
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.ui.AtmosphericScene
import com.oxygen.weather.timeline.ui.TimeLabScreen
import com.oxygen.weather.timeline.ui.CatalogTimeLabScreen
import com.oxygen.weather.timeline.ui.LocationSelectorController
import com.oxygen.weather.timeline.ui.LocationSelectorPanel
import com.oxygen.weather.timeline.presentation.CatalogTimelinePresenter
import com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase
import com.oxygen.weather.timeline.data.catalog.SavedLocationRepository
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.ui.RepositoryLocationSelectorStore
import com.oxygen.weather.timeline.data.openmeteo.OpenMeteoPlaceSearcher
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import java.time.Clock
import com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinator
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorage
import com.oxygen.weather.timeline.data.catalog.CatalogSourceRegistry
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.RecordedCatalogSource
import com.oxygen.weather.timeline.data.catalog.LiveCatalogRepository
import com.oxygen.weather.timeline.data.openmeteo.OpenMeteoCatalogSource
import kotlinx.coroutines.launch
import android.content.Intent

/** Standalone experiment. Does not replace Oxygen Weather's production Home pager. */
class MainActivity : ComponentActivity(), ActivityVariantDelegate.Host {
    private lateinit var catalogDatabase: CatalogStorageDatabase
    override lateinit var savedLocations: SavedLocationRepository
        private set
    override lateinit var activationCoordinator: CatalogActivationCoordinator
        private set
    override val activity get() = this
    override val activityScope get() = lifecycleScope
    private var variantDelegate: ActivityVariantDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDebuggable = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        variantDelegate = ActivityVariantDelegate.load(classLoader)
        catalogDatabase = Room.databaseBuilder(
            applicationContext,
            CatalogStorageDatabase::class.java,
            "oxygen-time-lab-catalog.db",
        ).addMigrations(
            CatalogStorageDatabase.MIGRATION_1_2,
            CatalogStorageDatabase.MIGRATION_2_3,
        ).build()
        savedLocations = SavedLocationRepository(catalogDatabase)
        val selectorStore = RepositoryLocationSelectorStore(savedLocations)
        val catalogStorage = CatalogDatasetStorage(catalogDatabase.datasets())
        val catalogSources = CatalogSourceRegistry(listOf(
            OpenMeteoCatalogSource(),
            SimulatedCatalogSource(),
            RecordedCatalogSource(null),
        ))
        val clock = Clock.systemUTC()
        activationCoordinator = CatalogActivationCoordinator(
            scope = lifecycleScope,
            savedLocations = savedLocations,
            storage = catalogStorage,
            liveRepository = LiveCatalogRepository(catalogSources, catalogStorage, clock),
            sources = catalogSources,
            clock = clock,
        )
        lifecycleScope.launch {
            activationCoordinator.state.collect { variantDelegate?.onCatalogActivationState(this@MainActivity, it) }
        }
        if (variantDelegate?.onMainActivityCreated(this, intent) != true) restoreStartupCatalog()
        val placeSearcher = if (isDebuggable && intent.getBooleanExtra(EXTRA_LOCATION_FIXTURE_SEARCH, false)) {
            variantDelegate?.createFixturePlaceSearcher(this) ?: OpenMeteoPlaceSearcher()
        } else OpenMeteoPlaceSearcher()
        val openLocationSelector = isDebuggable && intent.getBooleanExtra(EXTRA_LOCATION_SELECTOR, false)
        val showSnowPreview = isDebuggable && intent.getBooleanExtra(EXTRA_PREVIEW_SNOW, false)
        val showStormPreview = isDebuggable && intent.getBooleanExtra(EXTRA_ATM6_STORM, false)
        val showClearNightPreview = isDebuggable && intent.getBooleanExtra(EXTRA_ATM6_CLEAR_NIGHT, false)
        val debugSelectedHour = if (isDebuggable) {
            intent.getStringExtra(EXTRA_ATM6_HOUR)?.toDoubleOrNull()
                ?.takeIf { it.isFinite() && it in 0.0..SampleForecast.hours.lastIndex.toDouble() }
        } else null
        val effectPreview = if (showStormPreview) {
            AtmospherePreview.ILLUSTRATIVE_STORM
        } else if (showClearNightPreview) {
            AtmospherePreview.ILLUSTRATIVE_CLEAR_NIGHT
        } else if (isDebuggable) {
            when (intent.getStringExtra(EXTRA_ATM5_PREVIEW)) {
                "haze" -> AtmospherePreview.ILLUSTRATIVE_HAZE
                "turbulence" -> AtmospherePreview.ILLUSTRATIVE_TURBULENCE
                "combined" -> AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE
                else -> AtmospherePreview.NONE
            }
        } else AtmospherePreview.NONE
        val effectPreviewHour = if (isDebuggable) {
            intent.getStringExtra(EXTRA_ATM5_HOUR)?.toDoubleOrNull()
                ?.takeIf { it.isFinite() && it in 0.0..SampleForecast.hours.lastIndex.toDouble() }
        } else null
        val previewHour = if (isDebuggable && intent.hasExtra(EXTRA_PREVIEW_HOUR)) {
            intent.getIntExtra(EXTRA_PREVIEW_HOUR, -1).takeIf { it in SampleForecast.hours.indices }
        } else null
        setContent {
            val activation by activationCoordinator.state.collectAsState()
            val active = activation.active
            val presenter = remember(active?.viewGeneration) { active?.let(::CatalogTimelinePresenter) }
            var catalogLocationSelectorOpen by remember { mutableStateOf(openLocationSelector) }
            val selectorScope = rememberCoroutineScope()
            val catalogSelector = remember(placeSearcher, selectorStore, selectorScope) {
                LocationSelectorController(selectorScope, placeSearcher, selectorStore) {
                    activationCoordinator.committedLocationSelection(it.location)
                }
            }
            val fixturePreview = isDebuggable && (showStormPreview || showClearNightPreview ||
                debugSelectedHour != null || effectPreview != AtmospherePreview.NONE ||
                effectPreviewHour != null)
            CompositionLocalProvider(LocalAppClock provides AppClock.clock) {
                MaterialTheme {
                    when {
                        showSnowPreview -> SyntheticSnowPreview()
                        previewHour != null -> FixturePrecipitationPreview(previewHour)
                        fixturePreview -> TimeLabScreen(
                            effectPreview = effectPreview,
                            initialSelectedHour = debugSelectedHour
                                ?: if (showStormPreview) 0.0
                                else if (showClearNightPreview) 48.0
                                else effectPreviewHour ?: 36.0,
                            initialPrecisionMode = showStormPreview || showClearNightPreview || debugSelectedHour != null,
                            placeSearcher = placeSearcher,
                            locationStore = selectorStore,
                            onCommittedLocationSelection = { activationCoordinator.committedLocationSelection(it.location) },
                            openLocationSelectorOnLaunch = openLocationSelector,
                            showDebugLocationLauncher = isDebuggable,
                        )
                        active != null && presenter != null -> CatalogTimeLabScreen(
                            view = presenter.view(activation),
                            onSelectInstant = activationCoordinator::selectInstant,
                            onReturnToNow = {
                                activationCoordinator.selectInstant(
                                    if (active.origin == CatalogOrigin.LIVE) clock.instant()
                                    else active.recording.catalog.asOf,
                                    active.viewGeneration,
                                )
                            },
                            onBrowseCatalogs = if (isDebuggable) {
                                { variantDelegate?.openCatalogPicker(this@MainActivity) }
                            } else null,
                        )
                        else -> Box(Modifier.fillMaxSize().background(Color(0xFF11263D))) {
                            Column(
                                Modifier.align(Alignment.Center).padding(25.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text("OXYGEN  /  TIME LAB", color = Color.White)
                                Text(
                                    when {
                                        activation.loading -> "Loading forecast…"
                                        activation.lastError != null -> activation.lastError ?: "Forecast unavailable"
                                        else -> "No active forecast catalog"
                                    },
                                    color = Color.White,
                                )
                                TextButton(
                                    onClick = { catalogLocationSelectorOpen = true },
                                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                                ) {
                                    Text("Choose location")
                                }
                            }
                        }
                    }
                    if (!fixturePreview && catalogLocationSelectorOpen) {
                        LocationSelectorPanel(catalogSelector) { catalogLocationSelectorOpen = false }
                    }
                }
            }
        }
    }

    override fun restoreStartupCatalog() = activationCoordinator.restoreStartup()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val handledByVariant = variantDelegate?.onMainActivityNewIntent(this, intent) == true
        if (!handledByVariant && applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 &&
            intent.getBooleanExtra(EXTRA_LOCATION_SELECTOR, false)) recreate()
    }

    override fun onDestroy() {
        if (::catalogDatabase.isInitialized) catalogDatabase.close()
        super.onDestroy()
    }
}

private const val EXTRA_PREVIEW_SNOW = "preview_snow"
private const val EXTRA_PREVIEW_HOUR = "preview_hour"
private const val EXTRA_ATM5_PREVIEW = "atm5_preview"
private const val EXTRA_ATM5_HOUR = "atm5_hour"
private const val EXTRA_ATM6_STORM = "atm6_storm"
private const val EXTRA_ATM6_CLEAR_NIGHT = "atm6_clear_night"
private const val EXTRA_ATM6_HOUR = "atm6_hour"
private const val EXTRA_LOCATION_SELECTOR = "location_selector"
private const val EXTRA_LOCATION_FIXTURE_SEARCH = "location_fixture_search"
@androidx.compose.runtime.Composable
private fun FixturePrecipitationPreview(hourOffset: Int) {
    val view = ForecastProjection.fromHourOffset(hourOffset.toDouble(), precision = true)
    val amountLabel = when (val amount = view.sky.precipitationAmount) {
        is com.oxygen.weather.timeline.data.PrecipitationAmount.RainLiquidMm -> " · ${amount.value} MM"
        is com.oxygen.weather.timeline.data.PrecipitationAmount.SnowDepthCm -> " · ${amount.value} CM SNOW"
        null -> " · AMOUNT UNKNOWN"
    }
    val input = AtmosphereInput(
        sky = view.sky,
        precisionMode = true,
        localHour = hourOffset % 24.0,
        selectedHour = hourOffset.toDouble(),
    )
    Box(Modifier.fillMaxSize()) {
        AtmosphericScene(input = input)
        Text(
            text = "ILLUSTRATIVE ${view.conditionsLabel.uppercase()} · ${view.precipitationPercent}% CHANCE$amountLabel",
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .background(Color(0xDD102237))
                .padding(horizontal = 18.dp, vertical = 12.dp),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = .5.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@androidx.compose.runtime.Composable
private fun SyntheticSnowPreview() {
    val input = AtmosphereInput(
        sky = SkyFrame(
            condition = SkyCondition.CLOUDY,
            baseSkyCondition = SkyCondition.CLOUDY,
            precipitationType = PrecipitationType.NONE,
            precipitationAmount = null,
            temperatureC = 2.0,
            cloudPercent = 70.0,
            precipitationChancePercent = 0.0,
            windKph = 0.0,
        ),
        precisionMode = false,
        localHour = 15.0,
        preview = AtmospherePreview.SYNTHETIC_SNOW,
    )
    Box(Modifier.fillMaxSize()) {
        AtmosphericScene(input = input)
        Text(
            text = "SYNTHETIC SNOW PREVIEW",
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .background(Color(0xDD102237))
                .padding(horizontal = 18.dp, vertical = 12.dp),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
        )
    }
}
