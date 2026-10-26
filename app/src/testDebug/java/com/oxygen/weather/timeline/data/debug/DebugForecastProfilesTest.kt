package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.ForecastProjection
import com.oxygen.weather.timeline.presentation.PresentedCondition
import com.oxygen.weather.timeline.presentation.PresentedWarning
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.roundToInt

class DebugForecastProfilesTest {
    @Test
    fun profilesHaveExactLoadedHorizonsReferenceSamplesAndClearBuffers() {
        val expectedLoadedDays = mapOf(
            DebugProfileId.RAIN_3_DAY to 7,
            DebugProfileId.STORM_7_DAY to 11,
            DebugProfileId.COLD_SNOW_10_DAY to 14,
        )

        DebugForecastProfiles.all.forEach { profile ->
            val source = profile.toForecastSource()
            assertEquals(expectedLoadedDays.getValue(profile.id), source.days.size)
            assertEquals(expectedLoadedDays.getValue(profile.id) * 24, source.hours.size)
            assertEquals(profile.referenceTime, source.hours[profile.referenceTimeIndex()].time)
            assertTrue(profile.samples.take(48).all { it.condition == DebugCondition.CLEAR && it.windKph <= 8.0 })
            assertTrue(profile.samples.takeLast(48).all { it.condition == DebugCondition.CLEAR && it.windKph <= 8.0 })
            assertTrue(source.hours.zipWithNext().all { (a, b) -> b.time == a.time.plusHours(1) })
        }
    }

    @Test
    fun hourlyProjectionUsesExactSampleFactsAndPreservesUnknownValues() {
        DebugForecastProfiles.all.forEach { profile ->
            val source = profile.toForecastSource()
            profile.samples.forEachIndexed { index, sample ->
                val view = ForecastProjection.fromHourOffset(index.toDouble(), precision = true, source)
                assertEquals(sample.condition.labelForTest(), view.conditionsLabel)
                assertEquals(sample.precipitationProbabilityPercent?.toInt(), view.precipitationPercent)
                assertEquals(sample.thunderstormProbabilityPercent?.toInt(), view.thunderstormProbabilityPercent)
                assertEquals(sample.validTime.format(java.time.format.DateTimeFormatter.ofPattern("h a", java.util.Locale.US)), view.timeLabel)
                assertEquals("DEMO DATA", view.provenanceLabel)
                assertEquals("SYNTHETIC DEBUG PROFILE", view.provenanceDetail)
            }
        }

        val storm = DebugForecastProfiles.get(DebugProfileId.STORM_7_DAY)
        val warningIndex = storm.samples.indexOfFirst { it.warningState == DebugWarningState.PRESENT }
        val warningView = ForecastProjection.fromHourOffset(warningIndex.toDouble(), true, storm.toForecastSource())
        assertEquals(PresentedWarning.PRESENT, warningView.warning)
    }

    @Test
    fun dailyProjectionUsesNoonFactsAndHourlyTemperatureExtrema() {
        DebugForecastProfiles.all.forEach { profile ->
            val source = profile.toForecastSource()
            source.days.forEachIndexed { dayIndex, day ->
                val noon = profile.samples[day.noonSampleIndex]
                val view = ForecastProjection.fromHourOffset(day.noonSampleIndex.toDouble(), false, source)
                assertEquals(noon.condition.labelForTest(), view.conditionsLabel)
                assertEquals(noon.precipitationProbabilityPercent?.toInt(), view.precipitationPercent)
                assertEquals(noon.thunderstormProbabilityPercent?.toInt(), view.thunderstormProbabilityPercent)
                assertEquals(day.lowC.toFForTest(), view.lowF)
                assertEquals(day.highC.toFForTest(), view.highF)
                assertEquals("DAY OVERVIEW", view.timeLabel.substringBefore(" ·"))
                assertTrue(day.noonSampleIndex in (dayIndex * 24) until ((dayIndex + 1) * 24))
            }
        }
    }

    @Test
    fun snowAndWarningsRemainTypedWhileSceneUsesSupportedCondition() {
        val profile = DebugForecastProfiles.get(DebugProfileId.COLD_SNOW_10_DAY)
        val snowIndex = profile.samples.indexOfFirst { it.condition == DebugCondition.SNOW }
        val sourceHour = profile.toForecastSource().hours[snowIndex]
        val view = ForecastProjection.fromHourOffset(snowIndex.toDouble(), true, profile.toForecastSource())

        assertEquals(PresentedCondition.SNOW, sourceHour.condition)
        assertEquals("Snow", view.conditionsLabel)
        assertEquals(com.oxygen.weather.timeline.data.SkyCondition.CLOUDY, sourceHour.scene.condition)
        assertNull(view.thunderstormProbabilityPercent)
        assertTrue(sourceHour.scene.precipitationChancePercent.isFinite())
    }

    private fun DebugForecastProfile.referenceTimeIndex() =
        samples.indexOfFirst { it.validTime == referenceTime }

    private fun DebugCondition.labelForTest() = when (this) {
        DebugCondition.CLEAR -> "Clear skies"
        DebugCondition.PARTLY_CLOUDY -> "Partly cloudy"
        DebugCondition.CLOUDY -> "Cloudy"
        DebugCondition.RAIN -> "Showers"
        DebugCondition.SNOW -> "Snow"
        DebugCondition.STORM -> "Stormy"
    }

    private fun Double.toFForTest() = (this * 9.0 / 5.0 + 32.0).roundToInt()
}
