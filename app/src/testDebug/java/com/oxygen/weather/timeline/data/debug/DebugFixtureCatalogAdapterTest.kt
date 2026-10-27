package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogProductKind
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.FixtureAdapterResult
import com.oxygen.weather.timeline.data.catalog.FixtureAdapterError
import com.oxygen.weather.timeline.data.catalog.FixtureAmountMapping
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.LegacyPrecipitationAmount
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugFixtureCatalogAdapterTest {
    private val location = CatalogLocation(LocationId("fixed-test"), GeoCoordinates(41.88, -87.63), ZoneId.of("UTC"))
    private val asOf = Instant.parse("2026-10-27T18:00:00Z")

    @Test
    fun scenarioCatalogPreservesEveryScenarioAndSequenceIdentityAndTime() {
        val result = DebugForecastCatalogAdapter.adapt(location, DatasetId("debug-catalog"), asOf)
        assertTrue(result.toString(), result is FixtureAdapterResult.Success)
        val adapted = (result as FixtureAdapterResult.Success).value
        assertEquals(12, adapted.scenarios.size)
        assertEquals(12, adapted.catalog.facts.size)
        assertEquals(CatalogOrigin.SIMULATED, adapted.catalog.provenance.origin)
        assertEquals(CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT, adapted.catalog.provenance.productKind)

        DebugForecastCatalog.scenarios.zip(adapted.scenarios).forEach { (source, row) ->
            assertEquals(source.id, row.id)
            assertEquals(source.validTime.toInstant(ZoneOffset.UTC), row.validTime)
            assertEquals(source.validTime.toInstant(ZoneOffset.UTC), row.fact.facts.validTime)
            assertEquals(setOf(source.condition.toExpected()), row.fact.facts.conditions)
            assertEquals(source.precipitationProbabilityPercent, row.fact.facts.precipitationProbabilityPercent)
            assertEquals(source.thunderstormProbabilityPercent, row.fact.facts.thunderstormProbabilityPercent)
            assertEquals(source.precipitationType.name, row.fact.metadata.precipitationType)
            assertEquals(source.warningState.name, row.fact.metadata.warningState)
            assertEquals(source.provenance.name, row.fact.metadata.fixtureProvenance)
            when (val amount = source.precipitationAmount) {
                is PrecipitationAmount.RainLiquidMm -> {
                    assertEquals(LegacyPrecipitationAmount.RainLiquidMm(amount.value), row.fact.metadata.precipitationAmount)
                    assertEquals(FixtureAmountMapping.ACCUMULATION_INTERVAL_UNAVAILABLE, row.fact.metadata.precipitationAmountMapping)
                    assertNull(row.fact.facts.rainAmountMillimeters)
                }
                is PrecipitationAmount.SnowDepthCm -> {
                    assertEquals(amount.value, row.fact.facts.snowDepthCentimeters!!, 0.0)
                    assertEquals(LegacyPrecipitationAmount.SnowDepthCm(amount.value), row.fact.metadata.precipitationAmount)
                    assertEquals(FixtureAmountMapping.CANONICAL_SNOW_DEPTH, row.fact.metadata.precipitationAmountMapping)
                }
                null -> assertNull(row.fact.metadata.precipitationAmount)
            }
        }

        val expectedIds = listOf("cloud-build-rain-clearing", "snow-entry-and-exit", "graded-storm-and-warning-preview")
        assertEquals(expectedIds, adapted.sequences.map { it.id })
        DebugForecastCatalog.sequences.zip(adapted.sequences).forEach { (source, sequence) ->
            assertEquals(source.name, sequence.name)
            assertEquals(source.steps.size, sequence.steps.size)
            source.steps.zip(sequence.steps).forEachIndexed { index, (sourceStep, step) ->
                assertEquals(sourceStep.scenarioId, step.scenarioId)
                assertEquals(index, step.order)
                assertEquals(sourceStep.sequenceTime.toInstant(ZoneOffset.UTC), step.sequenceTime)
                val scenarioTime = DebugForecastCatalog.scenarios.single { it.id == sourceStep.scenarioId }.validTime
                assertEquals(scenarioTime.toInstant(ZoneOffset.UTC), step.scenarioValidTime)
            }
        }
        assertTrue(adapted.sequences.first().steps.first().sequenceTime != adapted.sequences.first().steps.first().scenarioValidTime)
        val low = adapted.scenarios.single { it.id == DebugScenarioId.STORM_LOW_THUNDER }
        val high = adapted.scenarios.single { it.id == DebugScenarioId.STORM_HIGH_THUNDER }
        assertEquals(27.0, low.fact.facts.thunderstormProbabilityPercent!!, 0.0)
        assertEquals(90.0, high.fact.facts.thunderstormProbabilityPercent!!, 0.0)
        val warning = adapted.scenarios.single { it.id == DebugScenarioId.STORM_WARNING_PREVIEW }
        assertEquals("PRESENT", warning.fact.metadata.warningState)
        assertTrue(warning.fact.facts.conditions.contains(WeatherCondition.STORM))
    }

    @Test
    fun generatedProfilesAdaptIndependentlyWithExactSamplesAndReferenceTimes() {
        DebugProfileId.entries.forEach { id ->
            val result = DebugForecastProfileAdapter.adapt(id, location, DatasetId("profile-${id.name.lowercase()}"), asOf)
            assertTrue(result.toString(), result is FixtureAdapterResult.Success)
            val adapted = (result as FixtureAdapterResult.Success).value
            val source = DebugForecastProfiles.get(id)
            assertEquals(id, adapted.id)
            assertEquals(id.loadedDays, adapted.loadedDays)
            assertEquals(id.label, adapted.label)
            assertEquals(id.loadedDays * 24, adapted.samples.size)
            assertEquals(source.eventStartIndex, adapted.eventStartIndex)
            assertEquals(source.eventEndExclusive, adapted.eventEndExclusive)
            assertEquals(source.referenceTime.toInstant(ZoneOffset.UTC), adapted.referenceTime)
            assertEquals(adapted.samples.map { it.facts }, adapted.catalog.facts)

            source.samples.zip(adapted.samples).forEach { (sample, row) ->
                val facts = row.facts
                assertEquals(sample.validTime.toInstant(ZoneOffset.UTC), facts.validTime)
                assertEquals(sample.temperatureC, facts.temperatureC!!, 0.0)
                assertEquals(sample.cloudPercent, facts.cloudCoverPercent!!, 0.0)
                assertEquals(sample.precipitationProbabilityPercent, facts.precipitationProbabilityPercent)
                assertEquals(sample.thunderstormProbabilityPercent, facts.thunderstormProbabilityPercent)
                assertEquals(sample.windKph, facts.windSpeedKph!!, 0.0)
                assertEquals(setOf(sample.condition.toExpected()), facts.conditions)
                assertEquals(sample.precipitationType.name, row.metadata.precipitationType)
                assertEquals(sample.warningState.name, row.metadata.warningState)
                when (val amount = sample.precipitationAmount) {
                    is PrecipitationAmount.RainLiquidMm -> assertEquals(
                        LegacyPrecipitationAmount.RainLiquidMm(amount.value), row.metadata.precipitationAmount,
                    )
                    is PrecipitationAmount.SnowDepthCm -> {
                        assertEquals(amount.value, facts.snowDepthCentimeters!!, 0.0)
                        assertEquals(LegacyPrecipitationAmount.SnowDepthCm(amount.value), row.metadata.precipitationAmount)
                    }
                    null -> assertNull(row.metadata.precipitationAmount)
                }
            }
        }
    }

    @Test
    fun normalizedSequenceIdentityCollisionsFailWithBothSourceNames() {
        val result = deriveStableSequenceIds(listOf("Cloud build", "Cloud-build"))
        assertTrue(result is FixtureAdapterResult.Failure)
        val error = (result as FixtureAdapterResult.Failure).error as FixtureAdapterError.SequenceIdentityCollision
        assertEquals("cloud-build", error.id)
        assertEquals(listOf("Cloud build", "Cloud-build"), error.names)
    }

    private fun DebugCondition.toExpected() = when (this) {
        DebugCondition.CLEAR -> WeatherCondition.CLEAR
        DebugCondition.PARTLY_CLOUDY -> WeatherCondition.PARTLY_CLOUDY
        DebugCondition.CLOUDY -> WeatherCondition.CLOUDY
        DebugCondition.RAIN -> WeatherCondition.RAIN
        DebugCondition.SNOW -> WeatherCondition.SNOW
        DebugCondition.STORM -> WeatherCondition.STORM
    }
}
