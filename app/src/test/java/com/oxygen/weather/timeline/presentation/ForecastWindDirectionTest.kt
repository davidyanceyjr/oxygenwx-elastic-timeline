package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.SampleForecast
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class ForecastWindDirectionTest {
    @Test
    fun normalDemoFixtureHasExplicitNortheastBearing() {
        assertEquals(192, SampleForecast.hours.count { it.windDirectionDegrees == 45.0 })
        assertEquals(45.0, ForecastProjection.fromHourOffset(36.0, false).windDirectionDegrees!!, 0.0)
    }

    @Test
    fun bearingValidationAcceptsZeroAndRejectsOutOfRangeOrNonFiniteValues() {
        val sample = SampleForecast.hours.first()
        assertEquals(0.0, sample.copy(windDirectionDegrees = 0.0).windDirectionDegrees!!, 0.0)
        listOf(-0.1, 360.0, Double.NaN, Double.POSITIVE_INFINITY).forEach { invalid ->
            assertThrows(IllegalArgumentException::class.java) {
                sample.copy(windDirectionDegrees = invalid)
            }
        }
    }

    @Test
    fun projectionUsesExactDailyNoonAndPrecisionHourBearings() {
        val samples = ForecastSource.sample.hours.toMutableList()
        samples[36] = samples[36].copy(windDirectionDegrees = 90.0)
        samples[37] = samples[37].copy(windDirectionDegrees = 270.0)
        val source = ForecastSource.sample.copy(hours = samples)

        assertEquals(90.0, ForecastProjection.fromHourOffset(37.0, false, source).windDirectionDegrees!!, 0.0)
        assertEquals(270.0, ForecastProjection.fromHourOffset(37.0, true, source).windDirectionDegrees!!, 0.0)
    }

    @Test
    fun compassDirectionMapsEightSectorsAndPreservesUnknown() {
        val bearings = listOf(0.0, 45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0)
        val expected = CompassDirection.entries
        bearings.forEachIndexed { index, bearing ->
            assertEquals(expected[index], CompassDirection.fromBearing(bearing))
        }
        assertEquals(CompassDirection.NORTH_EAST, CompassDirection.fromBearing(22.5))
        assertEquals(CompassDirection.NORTH, CompassDirection.fromBearing(337.5))
        assertNull(CompassDirection.fromBearing(null))
    }

    @Test
    fun missingDirectionStaysUnknownThroughDailyAndHourlyProjection() {
        val source = ForecastSource.sample.copy(
            id = "unknown-wind-direction",
            hours = ForecastSource.sample.hours.map { it.copy(windDirectionDegrees = null) },
        )

        assertNull(ForecastProjection.fromHourOffset(36.0, false, source).windDirectionDegrees)
        assertNull(ForecastProjection.fromHourOffset(37.0, true, source).windDirectionDegrees)
    }
}
