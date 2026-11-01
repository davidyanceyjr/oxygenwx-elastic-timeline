package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.AccumulatedSnowfallCentimeters
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadResult
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.ExpectedFieldSupport
import com.oxygen.weather.timeline.data.catalog.AccumulationInterval
import com.oxygen.weather.timeline.data.catalog.DailyCatalogField
import com.oxygen.weather.timeline.data.catalog.ProviderDailySummary
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.temporal.ChronoUnit

/** Debug-only canonical replay with declared expected slots but absent hourly records. */
internal suspend fun sg4GapRecording(request: CatalogLoadRequest): CatalogRecording {
    val source = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
    val catalog = source.catalog
    val zone = catalog.location.timeZone
    val anchor = request.asOf.atZone(zone).toLocalDate()
    val rainDay = anchor.minusDays(24).atStartOfDay(zone).toInstant()
    val nextDay = anchor.minusDays(23).atStartOfDay(zone).toInstant()
    val absent = setOf(rainDay.plus(11, ChronoUnit.HOURS), nextDay)
    val intervalOnly = rainDay.plus(14, ChronoUnit.HOURS)
    val typedConflict = rainDay.plus(15, ChronoUnit.HOURS)
    val sequence = mapOf(
        8L to WeatherCondition.CLEAR,
        9L to WeatherCondition.CLOUDY,
        10L to WeatherCondition.RAIN,
        12L to WeatherCondition.CLEAR,
        13L to WeatherCondition.PARTLY_CLOUDY,
        19L to WeatherCondition.CLEAR,
        20L to WeatherCondition.RAIN,
    ).mapKeys { rainDay.plus(it.key, ChronoUnit.HOURS) }
    val dailySummaries = listOf(anchor.minusDays(24), anchor.minusDays(23)).map { date ->
        val start = date.atStartOfDay(zone).toInstant()
        val end = date.plusDays(1).atStartOfDay(zone).toInstant()
        val temperatures = catalog.facts.filter { it.validTime >= start && it.validTime < end }
            .mapNotNull { it.temperatureC }
        ProviderDailySummary(date, AccumulationInterval(start, end),
            temperatureMaxC = temperatures.maxOrNull(), temperatureMinC = temperatures.minOrNull())
    }
    val typedConflictBounds = AccumulationInterval(typedConflict.minus(1, ChronoUnit.HOURS), typedConflict)
    val expectedSupport = source.expectedSupport.toMutableMap()
    val conflictDate = typedConflictBounds.end.minusNanos(1).atZone(zone).toLocalDate()
    val conflictFields = expectedSupport[conflictDate].orEmpty().toMutableMap()
    val existingSnowIntervals = (conflictFields[CatalogField.SNOWFALL_CENTIMETERS] as?
        ExpectedFieldSupport.Intervals)?.intervals.orEmpty()
    conflictFields[CatalogField.SNOWFALL_CENTIMETERS] =
        ExpectedFieldSupport.Intervals(existingSnowIntervals + typedConflictBounds)
    expectedSupport[conflictDate] = conflictFields
    val fixture = CanonicalCatalog(
        catalog.location, DatasetId(DebugCatalogActivity.SG4_GAP_REPLAY_ID),
        catalog.provenance, catalog.asOf,
        catalog.facts.filterNot { it.validTime in absent }.map { fact ->
            when {
                fact.validTime == intervalOnly -> fact.withConditions(emptySet())
                fact.validTime == typedConflict -> fact.withTypedPrecipitationConflict()
                fact.validTime in sequence -> fact.withConditions(setOf(sequence.getValue(fact.validTime)))
                else -> fact
            }
        },
        catalog.productSegments, catalog.dailySummaries + dailySummaries,
    )
    val dailySupport = source.dailyExpectedSupport + dailySummaries.associate { summary ->
        summary.date to setOf(DailyCatalogField.TEMPERATURE_MAX_C, DailyCatalogField.TEMPERATURE_MIN_C)
    }
    return CatalogRecording(fixture, expectedSupport, dailySupport)
}

private fun CanonicalWeatherFacts.withTypedPrecipitationConflict(): CanonicalWeatherFacts {
    val interval = AccumulationInterval(validTime.minus(1, ChronoUnit.HOURS), validTime)
    return CanonicalWeatherFacts(
        validTime = validTime, conditions = emptySet(), phenomena = phenomena,
        temperatureC = temperatureC, precipitationProbabilityPercent = precipitationProbabilityPercent,
        thunderstormProbabilityPercent = thunderstormProbabilityPercent,
        relativeHumidityPercent = relativeHumidityPercent, cloudCoverPercent = cloudCoverPercent,
        windSpeedKph = windSpeedKph, windGustKph = windGustKph,
        windDirectionDegrees = windDirectionDegrees, rainAmountMillimeters = rainAmountMillimeters,
        totalPrecipitationMillimeters = totalPrecipitationMillimeters, precipitationRate = precipitationRate,
        snowfallCentimeters = AccumulatedSnowfallCentimeters(2.0, interval),
        snowDepthCentimeters = snowDepthCentimeters,
        surfacePressureHpa = surfacePressureHpa, meanSeaLevelPressureHpa = meanSeaLevelPressureHpa,
        visibilityKm = visibilityKm,
    )
}

private fun CanonicalWeatherFacts.withConditions(values: Set<WeatherCondition>) = CanonicalWeatherFacts(
    validTime = validTime, conditions = values, phenomena = phenomena,
    temperatureC = temperatureC, precipitationProbabilityPercent = precipitationProbabilityPercent,
    thunderstormProbabilityPercent = thunderstormProbabilityPercent,
    relativeHumidityPercent = relativeHumidityPercent, cloudCoverPercent = cloudCoverPercent,
    windSpeedKph = windSpeedKph, windGustKph = windGustKph,
    windDirectionDegrees = windDirectionDegrees, rainAmountMillimeters = rainAmountMillimeters,
    totalPrecipitationMillimeters = totalPrecipitationMillimeters, precipitationRate = precipitationRate,
    snowfallCentimeters = snowfallCentimeters, snowDepthCentimeters = snowDepthCentimeters,
    surfacePressureHpa = surfacePressureHpa, meanSeaLevelPressureHpa = meanSeaLevelPressureHpa,
    visibilityKm = visibilityKm,
)
