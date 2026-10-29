package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.SampleForecast
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ElasticTimeAxisTest {
    private val width = 54.0
    private val hour = 15.0

    @Test fun roundTripIsStableInCompactAndExpandedModes() {
        for (focus in listOf(null, 0, 1, 4, 7)) {
            for (moment in listOf(0.0, 1.0, 12.0, 23.9, 24.0, 39.25, 48.0, 72.3, 190.0)) {
                val x = ElasticTimeAxis.axis(moment, focus, width, hour)
                assertEquals(moment, ElasticTimeAxis.inverse(x, focus, width, hour), 1e-8)
            }
        }
    }

    @Test fun selectedMomentRemainsUnderPlayheadOnExpansion() {
        val selected = 37.5 // Saturday 1:30 PM in sample fixture
        val center = 180.0
        for (focus in listOf(null, 1)) {
            assertEquals(center, ElasticTimeAxis.xForHour(selected, selected, center, focus, width, hour), 1e-9)
        }
    }

    @Test fun neighboringDaysCompressRelativeToExpandedSaturday() {
        val friSpan = ElasticTimeAxis.axis(24.0, 1, width, hour) - ElasticTimeAxis.axis(0.0, 1, width, hour)
        val satSpan = ElasticTimeAxis.axis(48.0, 1, width, hour) - ElasticTimeAxis.axis(24.0, 1, width, hour)
        val sunSpan = ElasticTimeAxis.axis(72.0, 1, width, hour) - ElasticTimeAxis.axis(48.0, 1, width, hour)
        assertEquals(width, friSpan, 1e-8)
        assertEquals(24.0 * hour, satSpan, 1e-8)
        assertEquals(width, sunSpan, 1e-8)
    }

    @Test fun leftDragAdvancesAndBoundsCannotBeExceeded() {
        val next = ElasticTimeAxis.scrub(36.0, -45.0, 1, width, hour, 191.0)
        assertEquals(39.0, next, 1e-8)
        assertEquals(191.0, ElasticTimeAxis.scrub(190.0, -99999.0, 1, width, hour, 191.0), 1e-8)
        assertEquals(0.0, ElasticTimeAxis.scrub(1.0, 99999.0, 1, width, hour, 191.0), 1e-8)
    }

    @Test fun tapInverseMatchesPaintedPosition() {
        val center = 180.0
        val selected = 36.0
        val expected = 45.0
        val x = ElasticTimeAxis.xForHour(expected, selected, center, 1, width, hour)
        val recovered = ElasticTimeAxis.hourAtX(x, selected, center, 1, width, hour)
        assertEquals(expected, recovered, 1e-8)
    }

    @Test fun fixtureIsCompleteChronologicalAndPresentationIsSeparated() {
        assertEquals(192, SampleForecast.hours.size)
        assertEquals(8, SampleForecast.days.size)
        assertTrue(SampleForecast.hours.zipWithNext().all { (a,b) -> a.time.isBefore(b.time) })
        val day = ForecastProjection.fromHourOffset(36.0, false)
        val hour = ForecastProjection.fromHourOffset(36.0, true)
        assertEquals("DAY OVERVIEW · SELECTED 12 PM", day.timeLabel)
        assertTrue(hour.timeLabel.contains("PM"))
    }
}
