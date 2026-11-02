package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.catalog.ActiveCatalogSelection
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CatalogWindowResult
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.presentation.atmosphere.DepictionKind
import com.oxygen.weather.timeline.presentation.atmosphere.EffectsLevel
import com.oxygen.weather.timeline.presentation.atmosphere.EnvironmentId
import com.oxygen.weather.timeline.presentation.atmosphere.PrecipitationKind
import com.oxygen.weather.timeline.presentation.atmosphere.PreviewEffectStrengths
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualState
import com.oxygen.weather.timeline.presentation.scene.CityPrecipitation
import com.oxygen.weather.timeline.presentation.scene.FirstCityComposition
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.SceneEvidence
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import com.oxygen.weather.timeline.presentation.scene.SemanticSceneGenerator
import com.oxygen.weather.timeline.presentation.scene.SemanticScenePlan
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

data class CatalogHourView(val instant: Instant, val label: String, val details: String)
data class CatalogDayView(
    val date: LocalDate,
    val overviewInstant: Instant,
    val label: String,
    val details: String,
    val hours: List<CatalogHourView>,
)

/** Values and labels that may cross into Compose. No recording or source fact is exposed. */
data class CatalogTimelineView(
    val generation: Long,
    val selectedInstant: Instant,
    val interval: SupportedTimeInterval,
    val zone: ZoneId,
    val days: List<CatalogDayView>,
    val heading: String,
    val selectedTimeLabel: String,
    val factTimeLabel: String?,
    val temperature: String?,
    val condition: String?,
    val high: String?,
    val low: String?,
    val precipitationChance: String?,
    val precipitationFact: String?,
    val wind: String?,
    val windDirectionDegrees: Double?,
    val meaning: String,
    val source: String,
    val selectedLocationName: String?,
    val status: String,
    val scene: SceneVisualState?,
    val localHour: Double,
    val loading: Boolean,
    val error: String?,
)

/** Facts and the single catalog composition share the authoritative selected instant. */
data class CatalogTimelineScenePresentation(
    val timeline: CatalogTimelineView,
    val reading: SceneReading,
    val composition: FirstCityComposition,
)

/** Construct once per published dataset; selected projections remain cheap during scrubbing. */
class CatalogTimelinePresenter(private val active: ActiveCatalogSelection) {
    private val catalog = active.recording.catalog
    private val window: CatalogWindow = (CatalogWindow.create(
        catalog, active.recording.expectedSupport,
        dailyExpectedSupport = active.recording.dailyExpectedSupport,
    ) as CatalogWindowResult.Success).window
    private val zone = window.zoneId
    private val interval = SupportedTimeInterval(window.startInclusive, window.endExclusive.minusNanos(1))
    private val scenePlan: SemanticScenePlan = SemanticSceneGenerator.generate(
        catalog, window, window.startInclusive, window.endExclusive,
    )
    private val dateFormat = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US)
    private val timeFormat = DateTimeFormatter.ofPattern("h:mm a z", Locale.US)
    private val selectedTimeFormat = DateTimeFormatter.ofPattern("h:mm:ss a z", Locale.US)
    private val updateFormat = DateTimeFormatter.ofPattern("MMM d, h:mm a z", Locale.US)
    private val days = window.days.map { day ->
        val anchor = interval.clamp(InstantTimeAxis.dailyOverviewAnchor(day.date, zone))
        val overview = CanonicalForecastProjection.project(catalog, window, anchor, ForecastDisplayUnits.IMPERIAL)
        CatalogDayView(
            date = day.date,
            overviewInstant = anchor,
            label = "${day.date.format(dateFormat)}, ${overview.meaning.label()}",
            details = buildString {
                append("Coverage ${day.dayCoverage.name.lowercase(Locale.US)}")
                overview.factSample?.let { append("; sample ${formatTime(it.validTime)}") }
                append("; temperature ${overview.factSample?.temperature?.displayValue?.rounded()?.let { "$it degrees Fahrenheit" } ?: "unavailable"}")
                append("; high ${overview.dailyHigh.displayTemperature?.displayValue?.rounded()?.let { "$it degrees Fahrenheit" } ?: "unavailable"}")
                append("; low ${overview.dailyLow.displayTemperature?.displayValue?.rounded()?.let { "$it degrees Fahrenheit" } ?: "unavailable"}")
            },
            hours = day.facts.asSequence().filter { it.validTime >= day.localStart && it.validTime < day.localEnd }
                .distinctBy { it.validTime }.sortedBy { it.validTime }.map { fact ->
                    val projected = CanonicalForecastProjection.project(
                        catalog, window, fact.validTime, ForecastDisplayUnits.IMPERIAL,
                    )
                    CatalogHourView(
                        instant = fact.validTime,
                        label = formatTime(fact.validTime),
                        details = buildString {
                            append(projected.meaning.label())
                            append("; temperature ${projected.factSample?.temperature?.displayValue?.rounded()?.let { "$it degrees Fahrenheit" } ?: "unavailable"}")
                            append("; condition ${conditionLabel(projected.factSample?.facts?.conditions) ?: "unavailable"}")
                            append("; precipitation chance ${projected.factSample?.facts?.precipitationProbabilityPercent?.rounded()?.let { "$it percent" } ?: "unavailable"}")
                            append("; wind ${projected.factSample?.windSpeed?.displayValue?.rounded()?.let { "$it miles per hour" } ?: "unavailable"}")
                        },
                    )
                }.toList(),
        )
    }

    fun view(state: CatalogActivationState): CatalogTimelineView {
        val selected = selectedInstant(state)
        val reading = scenePlan.evaluate(selected)
        val composition = FirstCityTheme.compose(reading, zone)
        return view(state, selected, reading, composition)
    }

    fun scenePresentation(state: CatalogActivationState): CatalogTimelineScenePresentation {
        val selected = selectedInstant(state)
        val reading = scenePlan.evaluate(selected)
        val composition = FirstCityTheme.compose(reading, zone)
        return CatalogTimelineScenePresentation(
            timeline = view(state, selected, reading, composition),
            reading = reading,
            composition = composition,
        )
    }

    private fun selectedInstant(state: CatalogActivationState): Instant =
        interval.clamp(checkNotNull(state.active).selectedInstant)

    private fun view(
        state: CatalogActivationState,
        selected: Instant,
        reading: SceneReading,
        composition: FirstCityComposition,
    ): CatalogTimelineView {
        val projected = CanonicalForecastProjection.project(catalog, window, selected, ForecastDisplayUnits.IMPERIAL)
        val fact = projected.factSample
        val conditionPoint = reading.conditions
        // Preserve the catalog text label selection exactly; scene category priorities are separate.
        val supportedCondition = conditionPoint?.value
            ?.takeIf { it.size == 1 }
            ?.single()
            ?.takeIf { it in DISPLAY_CONDITIONS }
        val carried = conditionPoint?.takeIf { it.evidence == SceneEvidence.EXPECTED_MISSING &&
            it.value != null && it.sourceValidTime != null }
        val displayFact = fact.takeIf { candidate ->
            when {
                conditionPoint == null -> candidate?.validTime == selected
                conditionPoint.evidence == SceneEvidence.EXPECTED_MISSING ->
                    candidate?.validTime == conditionPoint.expectedSlot
                else -> true
            }
        }
        val local = selected.atZone(zone)
        val sourceId = projected.productSegment?.sourceId ?: projected.provenance.sourceId ?: "Source unavailable"
        val status = buildString {
            append(active.origin.name.lowercase(Locale.US))
            append(" · ${active.delivery.name.lowercase(Locale.US)}")
            active.freshness?.let { append(" · ${it.name.lowercase(Locale.US)}") }
            active.storedAt?.let { append(" · saved ${formatTime(it)}") }
            (projected.productSegment?.issuedAt ?: projected.provenance.issuedAt)?.let {
                append(" · issued ${it.atZone(zone).format(updateFormat)}")
            }
            if (state.loading) append(" · updating")
            state.refreshStatus?.takeIf { it in setOf(
                com.oxygen.weather.timeline.data.catalog.CatalogRefreshStatus.NO_DATA,
                com.oxygen.weather.timeline.data.catalog.CatalogRefreshStatus.INVALID_REQUEST,
                com.oxygen.weather.timeline.data.catalog.CatalogRefreshStatus.TRANSPORT_FAILURE,
            ) }?.let { append(" · refresh ${it.name.lowercase(Locale.US).replace('_', ' ')}") }
        }
        return CatalogTimelineView(
            generation = active.viewGeneration,
            selectedInstant = selected,
            interval = interval,
            zone = zone,
            days = days,
            heading = projected.selectedDate.format(dateFormat).uppercase(Locale.US),
            selectedTimeLabel = selected.atZone(zone).format(selectedTimeFormat),
            factTimeLabel = carried?.let { "Condition from ${formatTime(requireNotNull(it.sourceValidTime))}; selected hour unavailable" }
                ?: displayFact?.let { formatTime(it.validTime) },
            temperature = displayFact?.temperature?.let { "${it.displayValue.rounded()}°" },
            condition = supportedCondition?.name?.lowercase(Locale.US)?.replace('_', ' ')
                ?: conditionLabel(displayFact?.facts?.conditions).takeIf { conditionPoint?.evidence == SceneEvidence.SUPPLIED },
            high = projected.dailyHigh.displayTemperature?.let { "${it.displayValue.rounded()}°" },
            low = projected.dailyLow.displayTemperature?.let { "${it.displayValue.rounded()}°" },
            precipitationChance = reading.precipitationChancePercent
                ?.takeIf { it.evidence == SceneEvidence.PROBABILITY_ONLY }
                ?.value?.let { "${it.rounded()}%" },
            precipitationFact = buildList {
                reading.rainAmount?.takeIf { it.evidence == SceneEvidence.SUPPLIED }?.let {
                    add("Rain ${it.value?.valueMillimeters} mm · ${formatTime(it.sourceBounds.start)}–${formatTime(it.sourceBounds.end)}")
                }
                reading.snowfall?.takeIf { it.evidence == SceneEvidence.SUPPLIED }?.let {
                    add("Snowfall ${it.value?.valueCentimeters} cm · ${formatTime(it.sourceBounds.start)}–${formatTime(it.sourceBounds.end)}")
                }
                reading.totalPrecipitation?.takeIf { it.evidence == SceneEvidence.SUPPLIED }?.let {
                    add("Total precipitation ${it.value?.valueMillimeters} mm · ${formatTime(it.sourceBounds.start)}–${formatTime(it.sourceBounds.end)}")
                }
                reading.precipitationRate?.takeIf { it.evidence == SceneEvidence.SUPPLIED }?.let {
                    val rate = requireNotNull(it.value)
                    add("Rate ${rate.valueMillimetersPerHour} mm/h" + (rate.meanInterval?.let { bounds ->
                        " · ${formatTime(bounds.start)}–${formatTime(bounds.end)}" } ?: " · ${formatTime(it.expectedSlot)}"))
                }
            }.takeIf { it.isNotEmpty() }?.joinToString("\n"),
            wind = displayFact?.windSpeed?.let { "${it.displayValue.rounded()} ${it.displayUnit}" },
            windDirectionDegrees = displayFact?.facts?.windDirectionDegrees,
            meaning = projected.meaning.label(),
            source = sourceId,
            selectedLocationName = state.selectedLocationName,
            status = status,
            scene = composition.compatibilitySceneState(reading),
            localHour = local.hour + local.minute / 60.0,
            loading = state.loading,
            error = state.lastError,
        )
    }

    private fun formatTime(instant: Instant): String = instant.atZone(zone).format(timeFormat)

    private companion object {
        val DISPLAY_CONDITIONS = setOf(
            WeatherCondition.CLEAR,
            WeatherCondition.PARTLY_CLOUDY,
            WeatherCondition.CLOUDY,
            WeatherCondition.RAIN,
            WeatherCondition.SNOW,
        )
    }
}

private fun Double.rounded() = roundToInt()

private fun ForecastMeaning.label(): String = when (this) {
    ForecastMeaning.FORECAST -> "Forecast"
    ForecastMeaning.ARCHIVED_MODEL_FORECAST -> "Archived model forecast"
    ForecastMeaning.REANALYSIS -> "Reanalysis"
    ForecastMeaning.OBSERVATION -> "Observation"
    ForecastMeaning.SYNTHETIC_PRODUCT -> "DEMO DATA · Synthetic forecast"
    ForecastMeaning.MIXED -> "Mixed product"
    ForecastMeaning.UNSPECIFIED -> "Product unspecified"
}

private fun conditionLabel(conditions: Set<WeatherCondition>?): String? =
    conditions?.takeIf { it.isNotEmpty() }?.joinToString(", ") {
        it.name.lowercase(Locale.US).replace('_', ' ')
    }

/** Compatibility value for callers that still accept the existing SceneVisualState input. */
private fun FirstCityComposition.compatibilitySceneState(reading: SceneReading): SceneVisualState? {
    if (!hasWeatherState) return null
    val depiction = when (precipitation) {
        CityPrecipitation.NONE -> DepictionKind.NONE
        CityPrecipitation.RAIN -> DepictionKind.ILLUSTRATIVE_RAIN
        CityPrecipitation.SNOW -> DepictionKind.ILLUSTRATIVE_SNOW
        CityPrecipitation.STORM -> DepictionKind.STORM
    }
    val precipitationKind = when (precipitation) {
        CityPrecipitation.NONE -> PrecipitationKind.NONE
        CityPrecipitation.RAIN -> PrecipitationKind.RAIN
        CityPrecipitation.SNOW -> PrecipitationKind.SYNTHETIC_SNOW
        CityPrecipitation.STORM -> PrecipitationKind.UNKNOWN
    }
    val amount = when (precipitation) {
        CityPrecipitation.RAIN -> reading.rainAmount?.value?.let { PrecipitationAmount.RainLiquidMm(it.valueMillimeters) }
        CityPrecipitation.SNOW -> reading.snowfall?.value?.let { PrecipitationAmount.SnowDepthCm(it.valueCentimeters) }
        CityPrecipitation.NONE, CityPrecipitation.STORM -> null
    }
    return SceneVisualState(
        condition = sky?.condition ?: SkyCondition.CLOUDY,
        precipitation = precipitationKind,
        depiction = depiction,
        precipitationChancePercent = reading.precipitationChancePercent?.value ?: 0.0,
        precipitationAmount = amount,
        cloudFraction = clouds.coverFraction,
        daylight = sky?.daylight ?: clouds.daylight,
        precisionMode = true,
        environment = EnvironmentId.NEUTRAL,
        effects = EffectsLevel.NONE,
        preview = AtmospherePreview.NONE,
        previewStrengths = PreviewEffectStrengths(0.0, 0.0),
    )
}
