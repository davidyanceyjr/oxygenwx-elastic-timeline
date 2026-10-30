package com.oxygen.weather.timeline.presentation

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
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

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

/** Construct once per published dataset; selected projections remain cheap during scrubbing. */
class CatalogTimelinePresenter(private val active: ActiveCatalogSelection) {
    private val catalog = active.recording.catalog
    private val window: CatalogWindow = (CatalogWindow.create(
        catalog, active.recording.expectedSupport,
        dailyExpectedSupport = active.recording.dailyExpectedSupport,
    ) as CatalogWindowResult.Success).window
    private val zone = window.zoneId
    private val interval = SupportedTimeInterval(window.startInclusive, window.endExclusive.minusNanos(1))
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
        val selected = interval.clamp(checkNotNull(state.active).selectedInstant)
        val projected = CanonicalForecastProjection.project(catalog, window, selected, ForecastDisplayUnits.IMPERIAL)
        val fact = projected.factSample
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
            factTimeLabel = fact?.let { formatTime(it.validTime) },
            temperature = fact?.temperature?.let { "${it.displayValue.rounded()}°" },
            condition = conditionLabel(fact?.facts?.conditions),
            high = projected.dailyHigh.displayTemperature?.let { "${it.displayValue.rounded()}°" },
            low = projected.dailyLow.displayTemperature?.let { "${it.displayValue.rounded()}°" },
            precipitationChance = fact?.facts?.precipitationProbabilityPercent?.let { "${it.rounded()}%" },
            wind = fact?.windSpeed?.let { "${it.displayValue.rounded()} ${it.displayUnit}" },
            windDirectionDegrees = fact?.facts?.windDirectionDegrees,
            meaning = projected.meaning.label(),
            source = sourceId,
            selectedLocationName = state.selectedLocationName,
            status = status,
            scene = projected.scene?.toScene(local.hour + local.minute / 60.0),
            localHour = local.hour + local.minute / 60.0,
            loading = state.loading,
            error = state.lastError,
        )
    }

    private fun formatTime(instant: Instant): String = instant.atZone(zone).format(timeFormat)
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

/** The scene consumes known condition cues only; missing measurements remain missing in text. */
private fun DecorativeSceneTransition.toScene(localHour: Double): SceneVisualState? {
    val state = if (fraction < 0.5) from else to
    val conditions = state.conditions
    if (conditions.isEmpty()) return null
    val condition = when {
        WeatherCondition.STORM in conditions -> SkyCondition.STORM
        WeatherCondition.RAIN in conditions -> SkyCondition.RAIN
        WeatherCondition.CLOUDY in conditions -> SkyCondition.CLOUDY
        WeatherCondition.PARTLY_CLOUDY in conditions -> SkyCondition.PARTLY_CLOUDY
        WeatherCondition.CLEAR in conditions -> SkyCondition.CLEAR
        WeatherCondition.SNOW in conditions -> SkyCondition.CLOUDY
        else -> return null
    }
    val depiction = when {
        WeatherCondition.STORM in conditions -> DepictionKind.STORM
        WeatherCondition.SNOW in conditions -> DepictionKind.ILLUSTRATIVE_SNOW
        WeatherCondition.RAIN in conditions -> DepictionKind.ILLUSTRATIVE_RAIN
        else -> DepictionKind.NONE
    }
    return SceneVisualState(
        condition = condition,
        precipitation = when (depiction) {
            DepictionKind.ILLUSTRATIVE_RAIN -> PrecipitationKind.RAIN
            DepictionKind.ILLUSTRATIVE_SNOW -> PrecipitationKind.SYNTHETIC_SNOW
            DepictionKind.STORM -> PrecipitationKind.UNKNOWN
            else -> PrecipitationKind.NONE
        },
        depiction = depiction,
        // Renderer intensity only. This is never exposed as a forecast percentage.
        precipitationChancePercent = state.precipitationProbabilityPercent ?: 0.0,
        precipitationAmount = null,
        cloudFraction = state.cloudCoverPercent?.div(100.0),
        daylight = sin(((localHour - 6.0) / 12.0) * PI).coerceIn(0.0, 1.0),
        precisionMode = true,
        environment = EnvironmentId.NEUTRAL,
        effects = EffectsLevel.NONE,
        preview = AtmospherePreview.NONE,
        previewStrengths = PreviewEffectStrengths(0.0, 0.0),
    )
}
