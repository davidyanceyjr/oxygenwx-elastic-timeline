package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.util.Collections

/** The time support of an accumulated value, represented as a half-open interval [start, end). */
data class AccumulationInterval(val start: Instant, val end: Instant) {
    init {
        require(start.isBefore(end)) { "Accumulation interval must have positive duration" }
    }
}

/** Liquid depth in millimeters over [interval]. */
data class AccumulatedMillimeters(val valueMillimeters: Double, val interval: AccumulationInterval) {
    init {
        require(valueMillimeters.isFinite() && valueMillimeters in 0.0..2000.0) {
            "Accumulated millimeters must be finite and in 0..2000"
        }
    }
}

/** Newly accumulated snow depth in centimeters over [interval]. */
data class AccumulatedSnowfallCentimeters(
    val valueCentimeters: Double,
    val interval: AccumulationInterval,
) {
    init {
        require(valueCentimeters.isFinite() && valueCentimeters in 0.0..500.0) {
            "Snowfall must be finite and in 0..500 centimeters"
        }
    }
}

/**
 * Precipitation rate in millimeters/hour. A non-null interval means this is a mean over that
 * interval; a null interval means the value is a point rate at the fact's valid time.
 */
data class PrecipitationRate(
    val valueMillimetersPerHour: Double,
    val meanInterval: AccumulationInterval? = null,
) {
    init {
        require(valueMillimetersPerHour.isFinite() && valueMillimetersPerHour in 0.0..500.0) {
            "Precipitation rate must be finite and in 0..500 millimeters/hour"
        }
    }
}

/**
 * Independent, source-supplied weather facts for one absolute valid time.
 * Nullable measurements preserve unsupported/missing values; explicit zero remains a value.
 * Conditions and phenomena are composable and never imply an official alert.
 */
class CanonicalWeatherFacts(
    val validTime: Instant,
    conditions: Set<WeatherCondition> = emptySet(),
    phenomena: Set<WeatherPhenomenon> = emptySet(),
    val temperatureC: Double? = null,
    val precipitationProbabilityPercent: Double? = null,
    val thunderstormProbabilityPercent: Double? = null,
    val relativeHumidityPercent: Double? = null,
    val cloudCoverPercent: Double? = null,
    val windSpeedKph: Double? = null,
    val windGustKph: Double? = null,
    val windDirectionDegrees: Double? = null,
    val rainAmountMillimeters: AccumulatedMillimeters? = null,
    val totalPrecipitationMillimeters: AccumulatedMillimeters? = null,
    val precipitationRate: PrecipitationRate? = null,
    val snowfallCentimeters: AccumulatedSnowfallCentimeters? = null,
    val snowDepthCentimeters: Double? = null,
    val surfacePressureHpa: Double? = null,
    val meanSeaLevelPressureHpa: Double? = null,
    val visibilityKm: Double? = null,
) {
    val conditions: Set<WeatherCondition> = Collections.unmodifiableSet(conditions.toSet())
    val phenomena: Set<WeatherPhenomenon> = Collections.unmodifiableSet(phenomena.toSet())

    init {
        temperatureC?.requireRange(-100.0..70.0, "Temperature must be in -100..70 °C")
        precipitationProbabilityPercent?.requireRange(0.0..100.0, "Precipitation probability must be in 0..100%")
        thunderstormProbabilityPercent?.requireRange(0.0..100.0, "Thunderstorm probability must be in 0..100%")
        relativeHumidityPercent?.requireRange(0.0..100.0, "Relative humidity must be in 0..100%")
        cloudCoverPercent?.requireRange(0.0..100.0, "Cloud cover must be in 0..100%")
        windSpeedKph?.requireRange(0.0..500.0, "Wind speed must be in 0..500 km/h")
        windGustKph?.requireRange(0.0..500.0, "Wind gust must be in 0..500 km/h")
        windDirectionDegrees?.requireRange(0.0..<360.0, "Wind direction must be in [0, 360) degrees")
        snowDepthCentimeters?.requireRange(0.0..2000.0, "Snow depth must be in 0..2000 cm")
        surfacePressureHpa?.requireRange(100.0..1200.0, "Surface pressure must be in 100..1200 hPa")
        meanSeaLevelPressureHpa?.requireRange(800.0..1100.0, "Mean sea-level pressure must be in 800..1100 hPa")
        visibilityKm?.requireRange(0.0..500.0, "Visibility must be in 0..500 km")

        require(rainAmountMillimeters == null || rainAmountMillimeters.interval.end == validTime) {
            "Rain accumulation interval must end at validTime"
        }
        require(totalPrecipitationMillimeters == null || totalPrecipitationMillimeters.interval.end == validTime) {
            "Total precipitation interval must end at validTime"
        }
        require(snowfallCentimeters == null || snowfallCentimeters.interval.end == validTime) {
            "Snowfall interval must end at validTime"
        }
        require(precipitationRate?.meanInterval == null || precipitationRate.meanInterval.end == validTime) {
            "Precipitation-rate mean interval must end at validTime"
        }
    }

    override fun equals(other: Any?): Boolean = other is CanonicalWeatherFacts &&
        validTime == other.validTime &&
        conditions == other.conditions &&
        phenomena == other.phenomena &&
        temperatureC == other.temperatureC &&
        precipitationProbabilityPercent == other.precipitationProbabilityPercent &&
        thunderstormProbabilityPercent == other.thunderstormProbabilityPercent &&
        relativeHumidityPercent == other.relativeHumidityPercent &&
        cloudCoverPercent == other.cloudCoverPercent &&
        windSpeedKph == other.windSpeedKph &&
        windGustKph == other.windGustKph &&
        windDirectionDegrees == other.windDirectionDegrees &&
        rainAmountMillimeters == other.rainAmountMillimeters &&
        totalPrecipitationMillimeters == other.totalPrecipitationMillimeters &&
        precipitationRate == other.precipitationRate &&
        snowfallCentimeters == other.snowfallCentimeters &&
        snowDepthCentimeters == other.snowDepthCentimeters &&
        surfacePressureHpa == other.surfacePressureHpa &&
        meanSeaLevelPressureHpa == other.meanSeaLevelPressureHpa &&
        visibilityKm == other.visibilityKm

    override fun hashCode(): Int = listOf(
        validTime,
        conditions,
        phenomena,
        temperatureC,
        precipitationProbabilityPercent,
        thunderstormProbabilityPercent,
        relativeHumidityPercent,
        cloudCoverPercent,
        windSpeedKph,
        windGustKph,
        windDirectionDegrees,
        rainAmountMillimeters,
        totalPrecipitationMillimeters,
        precipitationRate,
        snowfallCentimeters,
        snowDepthCentimeters,
        surfacePressureHpa,
        meanSeaLevelPressureHpa,
        visibilityKm,
    ).hashCode()

    override fun toString(): String = "CanonicalWeatherFacts(validTime=$validTime, conditions=$conditions, " +
        "phenomena=$phenomena, temperatureC=$temperatureC, " +
        "precipitationProbabilityPercent=$precipitationProbabilityPercent, " +
        "thunderstormProbabilityPercent=$thunderstormProbabilityPercent, relativeHumidityPercent=$relativeHumidityPercent, " +
        "cloudCoverPercent=$cloudCoverPercent, windSpeedKph=$windSpeedKph, windGustKph=$windGustKph, " +
        "windDirectionDegrees=$windDirectionDegrees, rainAmountMillimeters=$rainAmountMillimeters, " +
        "totalPrecipitationMillimeters=$totalPrecipitationMillimeters, precipitationRate=$precipitationRate, " +
        "snowfallCentimeters=$snowfallCentimeters, snowDepthCentimeters=$snowDepthCentimeters, " +
        "surfacePressureHpa=$surfacePressureHpa, meanSeaLevelPressureHpa=$meanSeaLevelPressureHpa, " +
        "visibilityKm=$visibilityKm)"
}

/** Composable sky/weather labels; this is deliberately not a Cartesian product with measurements. */
enum class WeatherCondition { CLEAR, PARTLY_CLOUDY, CLOUDY, RAIN, SNOW, STORM }

/** Additional supported phenomena; absence means the source did not supply the phenomenon. */
enum class WeatherPhenomenon { FOG, FREEZING_RAIN, ICE_PELLETS, HAZE }

private fun Double.requireRange(range: ClosedFloatingPointRange<Double>, message: String) {
    require(isFinite() && this in range) { message }
}

private fun Double.requireRange(range: OpenEndRange<Double>, message: String) {
    require(isFinite() && this in range) { message }
}
