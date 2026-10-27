package com.oxygen.weather.timeline.data.catalog

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SampleForecast
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SampleForecastCatalogAdapterTest {
    private val location = CatalogLocation(
        LocationId("fixed-test"), GeoCoordinates(41.88, -87.63), ZoneId.of("UTC"),
    )
    private val asOf = Instant.parse("2026-10-27T18:00:00Z")

    @Test
    fun adapterPreservesAllHourlyFactsDailySummariesAndSyntheticIdentity() {
        val result = SampleForecastCatalogAdapter.adapt(location, DatasetId("sample-v1"), asOf)
        assertTrue(result.toString(), result is FixtureAdapterResult.Success)
        val adapted = (result as FixtureAdapterResult.Success).value
        assertEquals(192, adapted.hourly.size)
        assertEquals(192, adapted.catalog.facts.size)
        assertEquals(8, adapted.dailySummaries.size)
        assertEquals(asOf, adapted.catalog.asOf)
        assertEquals(location, adapted.catalog.location)
        assertEquals(DatasetId("sample-v1"), adapted.catalog.datasetId)
        assertEquals(CatalogOrigin.SIMULATED, adapted.catalog.provenance.origin)
        assertEquals(CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT, adapted.catalog.provenance.productKind)

        SampleForecast.hours.zip(adapted.hourly).forEach { (source, row) ->
            assertEquals(source.time.toInstant(java.time.ZoneOffset.UTC), row.facts.validTime)
            assertEquals(source.temperatureC, row.facts.temperatureC!!, 0.0)
            assertEquals(source.cloudPercent, row.facts.cloudCoverPercent!!, 0.0)
            assertEquals(source.precipitationProbabilityPercent, row.facts.precipitationProbabilityPercent!!, 0.0)
            assertEquals(source.windKph, row.facts.windSpeedKph!!, 0.0)
            assertEquals(source.windDirectionDegrees, row.facts.windDirectionDegrees)
            assertEquals(setOf(source.condition.toExpected()), row.facts.conditions)
            assertEquals(source.baseSkyCondition.name, row.metadata.baseSkyCondition)
            assertEquals(source.precipitationType.name, row.metadata.precipitationType)
            when (val amount = source.precipitationAmount) {
                is PrecipitationAmount.RainLiquidMm -> {
                    assertEquals(LegacyPrecipitationAmount.RainLiquidMm(amount.value), row.metadata.precipitationAmount)
                    assertEquals(FixtureAmountMapping.ACCUMULATION_INTERVAL_UNAVAILABLE, row.metadata.precipitationAmountMapping)
                    assertNull(row.facts.rainAmountMillimeters)
                }
                is PrecipitationAmount.SnowDepthCm -> {
                    assertEquals(amount.value, row.facts.snowDepthCentimeters!!, 0.0)
                    assertEquals(LegacyPrecipitationAmount.SnowDepthCm(amount.value), row.metadata.precipitationAmount)
                    assertEquals(FixtureAmountMapping.CANONICAL_SNOW_DEPTH, row.metadata.precipitationAmountMapping)
                }
                null -> {
                    assertNull(row.metadata.precipitationAmount)
                    assertNull(row.facts.snowDepthCentimeters)
                }
            }
        }
        SampleForecast.days.zip(adapted.dailySummaries).forEach { (source, summary) ->
            assertEquals(source.date, summary.date)
            assertEquals(source.lowC, summary.lowC, 0.0)
            assertEquals(source.highC, summary.highC, 0.0)
            assertEquals(source.maxPrecipitationProbabilityPercent, summary.maxPrecipitationProbabilityPercent, 0.0)
            assertEquals(source.representative.time.toString(), summary.representativeFixtureId)
            assertEquals(source.representative.time.toInstant(java.time.ZoneOffset.UTC), summary.representativeValidTime)
        }
        assertFalse(adapted.catalog.facts.any { it.thunderstormProbabilityPercent != null })
    }

    @Test
    fun strictResolverReturnsNoPartialInstantsForGapOrOverlap() {
        val fixedInputs = listOf(
            Triple(ZoneId.of("America/Chicago"), LocalDateTime.parse("2026-03-08T02:30"), FixtureTimeFailureKind.DST_GAP),
            Triple(ZoneId.of("America/Chicago"), LocalDateTime.parse("2026-11-01T01:30"), FixtureTimeFailureKind.DST_OVERLAP),
        )
        fixedInputs.forEach { (zone, invalidTime, expectedKind) ->
            val result = resolveFixtureTimes(
                listOf(LocalDateTime.parse("2026-03-07T12:00"), invalidTime),
                location.copy(timeZone = zone),
            )
            assertTrue(result is FixtureAdapterResult.Failure)
            val error = (result as FixtureAdapterResult.Failure).error as FixtureAdapterError.InvalidLocalTime
            assertEquals(invalidTime, error.localTime)
            assertEquals(zone.id, error.zoneId)
            assertEquals(expectedKind, error.kind)
        }
        val unique = resolveFixtureTimes(
            listOf(LocalDateTime.parse("2026-03-08T02:30")),
            location.copy(timeZone = ZoneId.of("UTC")),
        )
        assertEquals(
            listOf(Instant.parse("2026-03-08T02:30:00Z")),
            (unique as FixtureAdapterResult.Success).value,
        )
    }

    private fun com.oxygen.weather.timeline.data.SkyCondition.toExpected() = when (this) {
        com.oxygen.weather.timeline.data.SkyCondition.CLEAR -> WeatherCondition.CLEAR
        com.oxygen.weather.timeline.data.SkyCondition.PARTLY_CLOUDY -> WeatherCondition.PARTLY_CLOUDY
        com.oxygen.weather.timeline.data.SkyCondition.CLOUDY -> WeatherCondition.CLOUDY
        com.oxygen.weather.timeline.data.SkyCondition.RAIN -> WeatherCondition.RAIN
        com.oxygen.weather.timeline.data.SkyCondition.STORM -> WeatherCondition.STORM
    }
}
