package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.LocalDate

enum class DailyCatalogField {
    TEMPERATURE_MAX_C, TEMPERATURE_MIN_C, PRECIPITATION_PROBABILITY_MAX_PERCENT,
    CONDITIONS, PHENOMENA, RAIN_AMOUNT, TOTAL_PRECIPITATION, SNOWFALL, SUNRISE, SUNSET,
    MOONRISE, MOONSET, MOON_PHASE,
}

/** Provider-supplied day-level values. Daily support spans the real IANA local day; event Instants remain absolute and may cross midnight. */
data class ProviderDailySummary(
    val date: LocalDate,
    val support: AccumulationInterval,
    val temperatureMaxC: Double? = null,
    val temperatureMinC: Double? = null,
    val precipitationProbabilityMaxPercent: Double? = null,
    val conditions: Set<WeatherCondition> = emptySet(),
    val phenomena: Set<WeatherPhenomenon> = emptySet(),
    val rainAmountMillimeters: Double? = null,
    val totalPrecipitationMillimeters: Double? = null,
    val snowfallCentimeters: Double? = null,
    val sunriseInstant: Instant? = null,
    val sunsetInstant: Instant? = null,
    val moonriseInstant: Instant? = null,
    val moonsetInstant: Instant? = null,
    val moonPhase: Double? = null,
    val origin: DailyValueOrigin = DailyValueOrigin.PROVIDER_SUPPLIED,
) {
    init {
        require(origin == DailyValueOrigin.PROVIDER_SUPPLIED) { "Catalog daily summaries represent provider-supplied values" }
        require(temperatureMaxC == null || temperatureMaxC.isFinite() && temperatureMaxC in -100.0..70.0)
        require(temperatureMinC == null || temperatureMinC.isFinite() && temperatureMinC in -100.0..70.0)
        require(temperatureMaxC == null || temperatureMinC == null || temperatureMaxC >= temperatureMinC)
        require(precipitationProbabilityMaxPercent == null || precipitationProbabilityMaxPercent.isFinite() && precipitationProbabilityMaxPercent in 0.0..100.0)
        require(listOf(rainAmountMillimeters, totalPrecipitationMillimeters, snowfallCentimeters).all { it == null || it.isFinite() && it >= 0.0 })
        require(sunriseInstant == null || sunriseInstant >= support.start && sunriseInstant < support.end)
        require(sunsetInstant == null || sunsetInstant >= support.start && sunsetInstant < support.end)
        require(sunriseInstant == null || sunsetInstant == null || sunriseInstant < sunsetInstant)
        require(moonPhase == null || moonPhase.isFinite() && moonPhase in 0.0..1.0)
    }

    fun has(field: DailyCatalogField): Boolean = when (field) {
        DailyCatalogField.TEMPERATURE_MAX_C -> temperatureMaxC != null
        DailyCatalogField.TEMPERATURE_MIN_C -> temperatureMinC != null
        DailyCatalogField.PRECIPITATION_PROBABILITY_MAX_PERCENT -> precipitationProbabilityMaxPercent != null
        DailyCatalogField.CONDITIONS -> conditions.isNotEmpty()
        DailyCatalogField.PHENOMENA -> conditions.isNotEmpty() || phenomena.isNotEmpty()
        DailyCatalogField.RAIN_AMOUNT -> rainAmountMillimeters != null
        DailyCatalogField.TOTAL_PRECIPITATION -> totalPrecipitationMillimeters != null
        DailyCatalogField.SNOWFALL -> snowfallCentimeters != null
        DailyCatalogField.SUNRISE -> sunriseInstant != null
        DailyCatalogField.SUNSET -> sunsetInstant != null
        DailyCatalogField.MOONRISE -> moonriseInstant != null
        DailyCatalogField.MOONSET -> moonsetInstant != null
        DailyCatalogField.MOON_PHASE -> moonPhase != null
    }
}

typealias DailyExpectedSupport = Map<LocalDate, Set<DailyCatalogField>>
