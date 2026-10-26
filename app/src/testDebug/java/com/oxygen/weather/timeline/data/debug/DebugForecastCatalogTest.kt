package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.SkyCondition
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugForecastCatalogTest {
    @Test
    fun `approved catalog rows retain exact values and explicit missingness`() {
        val expected = listOf(
            DebugForecastScenario(DebugScenarioId.CLEAR_DAY, LocalDateTime.parse("2026-10-23T12:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.CLEAR_NIGHT, LocalDateTime.parse("2026-10-23T23:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.PARTLY_DAY, LocalDateTime.parse("2026-10-24T12:00"), DebugCondition.PARTLY_CLOUDY, null, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.CLOUDY_NIGHT, LocalDateTime.parse("2026-10-24T23:00"), DebugCondition.CLOUDY, 20.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.RAIN_AMOUNT, LocalDateTime.parse("2026-10-25T12:00"), DebugCondition.RAIN, 60.0, DebugPrecipitationType.RAIN, PrecipitationAmount.RainLiquidMm(0.8), null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.RAIN_UNKNOWN_AMOUNT, LocalDateTime.parse("2026-10-25T18:00"), DebugCondition.RAIN, 70.0, DebugPrecipitationType.RAIN, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.SNOW_DAY, LocalDateTime.parse("2026-10-26T12:00"), DebugCondition.SNOW, 80.0, DebugPrecipitationType.SNOW, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.SNOW_NIGHT, LocalDateTime.parse("2026-10-26T23:00"), DebugCondition.SNOW, 80.0, DebugPrecipitationType.SNOW, null, null, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.STORM_LOW_THUNDER, LocalDateTime.parse("2026-10-27T12:00"), DebugCondition.STORM, 20.0, DebugPrecipitationType.NONE, null, 27.0, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.STORM_HIGH_THUNDER, LocalDateTime.parse("2026-10-27T13:00"), DebugCondition.STORM, 70.0, DebugPrecipitationType.RAIN, null, 90.0, DebugWarningState.NONE),
            DebugForecastScenario(DebugScenarioId.STORM_WARNING_PREVIEW, LocalDateTime.parse("2026-10-27T14:00"), DebugCondition.STORM, null, DebugPrecipitationType.NONE, null, null, DebugWarningState.PRESENT),
            DebugForecastScenario(DebugScenarioId.CLEAR_ENDPOINT, LocalDateTime.parse("2026-10-27T15:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
        )
        assertEquals(expected, DebugForecastCatalog.scenarios)
        DebugForecastCatalog.scenarios.forEach {
            assertEquals(DebugProvenance.SYNTHETIC_DEBUG, it.provenance)
            assertTrue(it.precipitationProbabilityPercent == null || it.precipitationProbabilityPercent in 0.0..100.0)
            assertTrue(it.thunderstormProbabilityPercent == null || it.thunderstormProbabilityPercent in 0.0..100.0)
        }
    }

    @Test
    fun `sequences preserve scenario rows and advance from approved local start`() {
        val start = LocalDateTime.parse("2026-10-20T00:00")
        assertEquals(3, DebugForecastCatalog.sequences.size)
        assertEquals(
            listOf(
                listOf(DebugScenarioId.CLEAR_DAY, DebugScenarioId.PARTLY_DAY, DebugScenarioId.CLOUDY_NIGHT, DebugScenarioId.RAIN_AMOUNT, DebugScenarioId.RAIN_UNKNOWN_AMOUNT, DebugScenarioId.CLEAR_ENDPOINT),
                listOf(DebugScenarioId.CLOUDY_NIGHT, DebugScenarioId.SNOW_DAY, DebugScenarioId.SNOW_NIGHT, DebugScenarioId.CLEAR_ENDPOINT),
                listOf(DebugScenarioId.STORM_LOW_THUNDER, DebugScenarioId.STORM_HIGH_THUNDER, DebugScenarioId.STORM_WARNING_PREVIEW, DebugScenarioId.CLEAR_ENDPOINT),
            ),
            DebugForecastCatalog.sequences.map { sequence -> sequence.steps.map { it.scenarioId } },
        )
        DebugForecastCatalog.sequences.forEach { sequence ->
            assertEquals(start, sequence.steps.first().sequenceTime)
            sequence.steps.zipWithNext().forEach { (left, right) ->
                assertEquals(left.sequenceTime.plusHours(1), right.sequenceTime)
            }
            sequence.steps.forEach { step ->
                assertTrue(DebugForecastCatalog.scenarios.any { it.id == step.scenarioId })
            }
        }
        val cloudFirst = DebugForecastCatalog.sequences.first().steps.first()
        assertNotEquals(
            DebugForecastCatalog.scenarios.single { it.id == cloudFirst.scenarioId }.validTime,
            cloudFirst.sequenceTime,
        )
        assertEquals(LocalDateTime.parse("2026-10-23T12:00"), DebugForecastCatalog.scenarios.first().validTime)
    }

    @Test
    fun `inventory is deterministic and complete and normal fixture snapshot is unchanged`() {
        val report = DebugForecastCatalog.inventoryReport()
        assertEquals(report, DebugForecastCatalog.inventoryReport())
        assertTrue(report.contains("SYNTHETIC_DEBUG"))
        assertTrue(report.contains("ABSENT"))
        assertTrue(report.contains("2026-10-20T00:00"))
        DebugForecastCatalog.scenarios.forEach { assertTrue(report.contains(it.id.name.replace('_', '-'))) }
        assertTrue(report.contains("RainLiquidMm(0.8 mm)"))

        val first = SampleForecast.hours.first()
        assertEquals(192, SampleForecast.hours.size)
        assertEquals(LocalDateTime.parse("2026-10-23T00:00"), first.time)
        assertEquals(79.0, first.cloudPercent, 0.0)
        assertEquals(50.0, first.precipitationProbabilityPercent, 0.0)
        assertEquals(SkyCondition.RAIN, first.condition)
        assertEquals(SkyCondition.CLOUDY, first.baseSkyCondition)
        assertEquals(12.0, first.windKph, 0.0)
        assertEquals(LocalDateTime.parse("2026-10-23T14:00"), SampleForecast.days.first().representative.time)

        val artifactDir = Path.of("../.codex/test-artifacts/040-dfm2-debug-forecast-catalog")
        Files.createDirectories(artifactDir)
        Files.writeString(artifactDir.resolve("forecast-catalog-inventory.md"), report)
    }

    @Test
    fun `percentage fields reject values outside range`() {
        val base = DebugForecastCatalog.scenarios.first()
        assertTrue(runCatching { base.copy(precipitationProbabilityPercent = -0.1) }.isFailure)
        assertTrue(runCatching { base.copy(precipitationProbabilityPercent = 100.1) }.isFailure)
        assertTrue(runCatching { base.copy(thunderstormProbabilityPercent = -1.0) }.isFailure)
        assertTrue(runCatching { base.copy(thunderstormProbabilityPercent = 101.0) }.isFailure)
    }
}
