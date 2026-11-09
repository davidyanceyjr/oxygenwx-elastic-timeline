package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.CatalogDay
import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.CatalogProvenance
import com.oxygen.weather.timeline.data.catalog.CatalogProductKind
import com.oxygen.weather.timeline.data.catalog.CatalogProductSegment
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CoverageStatus
import com.oxygen.weather.timeline.data.catalog.DailyAmountField
import com.oxygen.weather.timeline.data.catalog.DailyValueOrigin
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherEventId
import com.oxygen.weather.timeline.data.catalog.WeatherEventPriority
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.Duration
import java.time.Instant
import java.time.LocalDate

enum class ForecastDisplayUnits { METRIC, IMPERIAL }
enum class SnowVisualIdentity { SNOW, NOT_SNOW, UNKNOWN }
enum class ForecastMeaning {
    FORECAST, ARCHIVED_MODEL_FORECAST, REANALYSIS, OBSERVATION, SYNTHETIC_PRODUCT, MIXED, UNSPECIFIED,
}

data class PresentedMeasurement(
    val canonicalValue: Double,
    val displayValue: Double,
    val displayUnit: String,
)

data class PresentedDailyTemperature(
    val canonicalCelsius: Double?,
    val displayTemperature: PresentedMeasurement?,
    val origin: DailyValueOrigin?,
)

/** Exact source state supplied to the decorative renderer; these values are not forecast facts. */
data class DecorativeSceneState(
    val conditions: Set<WeatherCondition>,
    val temperatureC: Double?,
    val cloudCoverPercent: Double?,
    val precipitationProbabilityPercent: Double?,
    val windSpeedKph: Double?,
    val snow: SnowVisualIdentity,
)

/** A same-day scene blend description. It never contains an interpolated weather measurement. */
data class DecorativeSceneTransition(
    val from: DecorativeSceneState,
    val to: DecorativeSceneState,
    val fraction: Double,
)

data class CanonicalHourlyPresentation(
    val validTime: Instant,
    val facts: CanonicalWeatherFacts,
    val temperature: PresentedMeasurement?,
    val apparentTemperature: PresentedMeasurement?,
    val windSpeed: PresentedMeasurement?,
    val snow: SnowVisualIdentity,
)

/** Support and carry decisions are made by the catalog timeline before event projection. */
data class SelectedEventEvidence(
    val sourceFact: CanonicalWeatherFacts?,
    val sourceTime: Instant?,
    val carried: Boolean,
) {
    init { require((sourceFact == null) == (sourceTime == null)); require(sourceFact == null || sourceFact.validTime == sourceTime) }
}

data class PresentedWeatherEvent(
    val symbolId: WeatherSymbolId,
    val accessibleName: String,
    val sourceTime: Instant,
    val carried: Boolean,
    val sourceCode: Int?,
)

data class CanonicalForecastPresentation(
    val selectedInstant: Instant,
    val selectedDate: LocalDate,
    val factSample: CanonicalHourlyPresentation?,
    val dailyHigh: PresentedDailyTemperature,
    val dailyLow: PresentedDailyTemperature,
    val dailyRainAmountMillimeters: Double?,
    val dailyTotalPrecipitationMillimeters: Double?,
    val dailySnowfallCentimeters: Double?,
    val dailyAmountOrigins: Map<DailyAmountField, DailyValueOrigin>,
    val provenance: CatalogProvenance,
    val productSegment: CatalogProductSegment?,
    val meaning: ForecastMeaning,
    val displayUnits: ForecastDisplayUnits,
    val scene: DecorativeSceneTransition?,
    val selectedEvent: PresentedWeatherEvent? = null,
)

/** Pure canonical-to-presentation projection. The caller pairs a window with its source catalog. */
object CanonicalForecastProjection {
    fun project(
        catalog: CanonicalCatalog,
        window: CatalogWindow,
        selectedInstant: Instant,
        displayUnits: ForecastDisplayUnits,
        eventEvidence: SelectedEventEvidence? = null,
    ): CanonicalForecastPresentation {
        require(window.zoneId == catalog.location.timeZone) {
            "Catalog and window timezones must match"
        }
        val selectedDate = selectedInstant.atZone(catalog.location.timeZone).toLocalDate()
        val day = window.days.firstOrNull { it.date == selectedDate }
        val eligibleFacts = day?.let { selectedDay ->
            selectedDay.facts.filter { it.validTime >= selectedDay.localStart && it.validTime < selectedDay.localEnd }
        }.orEmpty()
        val nearest = eligibleFacts.minWithOrNull(
            compareBy<CanonicalWeatherFacts> { Duration.between(it.validTime, selectedInstant).abs() }
                .thenBy { it.validTime },
        )
        val summary = day?.dailySummary
        val high = dailyTemperature(day, summary?.temperatureMaxC, true, displayUnits)
        val low = dailyTemperature(day, summary?.temperatureMinC, false, displayUnits)
        val amounts = DailyAmountField.entries.mapNotNull { field ->
            val value = day?.let { dayAmount(it, field) } ?: return@mapNotNull null
            field to value.origin
        }.toMap()
        val segment = catalog.productSegments.firstOrNull {
            selectedDate >= it.startDateInclusive && selectedDate < it.endDateExclusive
        }
        val meaning = (segment?.productKind ?: catalog.provenance.productKind).toMeaning()
        return CanonicalForecastPresentation(
            selectedInstant = selectedInstant,
            selectedDate = selectedDate,
            factSample = nearest?.toPresentation(displayUnits),
            dailyHigh = high,
            dailyLow = low,
            dailyRainAmountMillimeters = day?.let { dayAmount(it, DailyAmountField.RAIN)?.value },
            dailyTotalPrecipitationMillimeters = day?.let { dayAmount(it, DailyAmountField.TOTAL_PRECIPITATION)?.value },
            dailySnowfallCentimeters = day?.let { dayAmount(it, DailyAmountField.SNOWFALL)?.value },
            dailyAmountOrigins = amounts,
            provenance = catalog.provenance,
            productSegment = segment,
            meaning = meaning,
            displayUnits = displayUnits,
            scene = day?.let { sceneTransition(eligibleFacts, selectedInstant) },
            selectedEvent = eventEvidence?.let { evidence ->
                val source = evidence.sourceFact ?: return@let null
                val selected = WeatherEventPriority.select(source.events, source.primaryEventIndex)
                val id = selected?.id ?: legacyEventId(source, day, selectedInstant)
                id?.let { stableId ->
                    val symbol = WeatherSymbolId.valueOf(stableId.name)
                    PresentedWeatherEvent(symbol, symbol.label, requireNotNull(evidence.sourceTime),
                        evidence.carried, selected?.sourceCode ?: source.sourceWeatherCode)
                }
            },
        )
    }

    private fun legacyEventId(source: CanonicalWeatherFacts, day: CatalogDay?, selectedInstant: Instant): WeatherEventId? {
        // A supplied but unsupported numeric code must never be inferred from broad scene input.
        if (source.sourceWeatherCode != null) return null
        return when {
            WeatherCondition.CLOUDY in source.conditions -> WeatherEventId.CLOUDY_OVERCAST
            WeatherCondition.CLEAR in source.conditions || WeatherCondition.PARTLY_CLOUDY in source.conditions -> {
                val sunrise = day?.dailySummary?.sunriseInstant ?: return null
                val sunset = day.dailySummary.sunsetInstant ?: return null
                val daylight = selectedInstant >= sunrise.minusSeconds(3600) && selectedInstant < sunset.minusSeconds(3600)
                if (WeatherCondition.CLEAR in source.conditions) {
                    if (daylight) WeatherEventId.SUNNY else WeatherEventId.CLEAR_NIGHT
                } else if (daylight) WeatherEventId.PARTLY_SUNNY_CLOUDY else WeatherEventId.PARTLY_CLOUDY_NIGHT
            }
            else -> null
        }
    }

    private fun dailyTemperature(
        day: CatalogDay?,
        suppliedC: Double?,
        high: Boolean,
        units: ForecastDisplayUnits,
    ): PresentedDailyTemperature {
        val derived = if (suppliedC == null && day?.fieldCoverage?.get(CatalogField.TEMPERATURE_C) == CoverageStatus.COMPLETE) {
            day.facts.asSequence()
                .filter { it.validTime >= day.localStart && it.validTime < day.localEnd }
                .mapNotNull { it.temperatureC }
                .let { values -> if (high) values.maxOrNull() else values.minOrNull() }
        } else null
        val celsius = suppliedC ?: derived
        return PresentedDailyTemperature(
            canonicalCelsius = celsius,
            displayTemperature = celsius?.let { temperature(it, units) },
            origin = when {
                suppliedC != null -> DailyValueOrigin.PROVIDER_SUPPLIED
                derived != null -> DailyValueOrigin.DERIVED
                else -> null
            },
        )
    }

    private fun dayAmount(day: CatalogDay, field: DailyAmountField) = when (field) {
        DailyAmountField.RAIN -> day.providerDailyAmounts.firstOrNull { it.field == field }
            ?: day.deriveDailyAmount(field)
        DailyAmountField.TOTAL_PRECIPITATION -> day.providerDailyAmounts.firstOrNull { it.field == field }
            ?: day.deriveDailyAmount(field)
        DailyAmountField.SNOWFALL -> day.providerDailyAmounts.firstOrNull { it.field == field }
            ?: day.deriveDailyAmount(field)
    }

    private fun sceneTransition(
        facts: List<CanonicalWeatherFacts>,
        selected: Instant,
    ): DecorativeSceneTransition? {
        if (facts.isEmpty()) return null
        val left = facts.lastOrNull { !it.validTime.isAfter(selected) }
        val right = facts.firstOrNull { !it.validTime.isBefore(selected) }
        val from = left ?: right ?: return null
        val to = right ?: left ?: return null
        val duration = Duration.between(from.validTime, to.validTime)
        val fraction = if (duration.isZero) 0.0 else
            (Duration.between(from.validTime, selected).toNanos().toDouble() / duration.toNanos()).coerceIn(0.0, 1.0)
        return DecorativeSceneTransition(from.sceneState(), to.sceneState(), fraction)
    }

    private fun CanonicalWeatherFacts.sceneState() = DecorativeSceneState(
        conditions = conditions,
        temperatureC = temperatureC,
        cloudCoverPercent = cloudCoverPercent,
        precipitationProbabilityPercent = precipitationProbabilityPercent,
        windSpeedKph = windSpeedKph,
        snow = when {
            WeatherCondition.SNOW in conditions || snowfallCentimeters != null || snowDepthCentimeters != null -> SnowVisualIdentity.SNOW
            conditions.isNotEmpty() -> SnowVisualIdentity.NOT_SNOW
            else -> SnowVisualIdentity.UNKNOWN
        },
    )

    private fun CanonicalWeatherFacts.toPresentation(units: ForecastDisplayUnits) = CanonicalHourlyPresentation(
        validTime = validTime,
        facts = this,
        temperature = temperatureC?.let { temperature(it, units) },
        apparentTemperature = apparentTemperatureC?.let { temperature(it, units) },
        windSpeed = windSpeedKph?.let { wind(it, units) },
        snow = when {
            WeatherCondition.SNOW in conditions || snowfallCentimeters != null || snowDepthCentimeters != null -> SnowVisualIdentity.SNOW
            conditions.isNotEmpty() -> SnowVisualIdentity.NOT_SNOW
            else -> SnowVisualIdentity.UNKNOWN
        },
    )

    private fun temperature(celsius: Double, units: ForecastDisplayUnits) = when (units) {
        ForecastDisplayUnits.METRIC -> PresentedMeasurement(celsius, celsius, "°C")
        ForecastDisplayUnits.IMPERIAL -> PresentedMeasurement(celsius, celsius * 9.0 / 5.0 + 32.0, "°F")
    }

    private fun wind(kph: Double, units: ForecastDisplayUnits) = when (units) {
        ForecastDisplayUnits.METRIC -> PresentedMeasurement(kph, kph, "km/h")
        ForecastDisplayUnits.IMPERIAL -> PresentedMeasurement(kph, kph / 1.609344, "mph")
    }

    private fun CatalogProductKind.toMeaning() = when (this) {
        CatalogProductKind.FORECAST -> ForecastMeaning.FORECAST
        CatalogProductKind.ARCHIVED_MODEL_FORECAST -> ForecastMeaning.ARCHIVED_MODEL_FORECAST
        CatalogProductKind.REANALYSIS -> ForecastMeaning.REANALYSIS
        CatalogProductKind.ACTUAL_OBSERVATION -> ForecastMeaning.OBSERVATION
        CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT -> ForecastMeaning.SYNTHETIC_PRODUCT
        CatalogProductKind.MIXED -> ForecastMeaning.MIXED
    }
}
