package com.oxygen.weather.timeline.presentation

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InstantTimeAxisTest {
    private val chicago = ZoneId.of("America/Chicago")

    @Test
    fun elapsedMappingIsMonotonicInvertibleAndClamped() {
        val start = Instant.parse("2026-03-07T00:00:00Z")
        val end = start.plusSeconds(3 * 24 * 3600L)
        val range = SupportedTimeInterval(start, end)
        val selected = start.plusSeconds(40_123).plusNanos(456_000_000)
        val scale = 16.0 / 3600.0
        val moments = listOf(start, selected, selected.plusSeconds(1), end)
        val positions = moments.map { InstantTimeAxis.xForInstant(it, selected, 180.0, scale, range) }

        assertTrue(positions.zipWithNext().all { (left, right) -> right > left })
        assertEquals(180.0, InstantTimeAxis.xForInstant(selected, selected, 180.0, scale, range), 0.0)
        for (moment in moments) {
            val x = InstantTimeAxis.xForInstant(moment, selected, 180.0, scale, range)
            assertEquals(moment, InstantTimeAxis.instantAtX(x, selected, 180.0, scale, range))
        }
        val unsampledMoment = selected.plusSeconds(17).plusNanos(250_000_000)
        assertEquals(
            unsampledMoment,
            InstantTimeAxis.instantAtX(
                InstantTimeAxis.xForInstant(unsampledMoment, selected, 180.0, scale, range),
                selected, 180.0, scale, range,
            ),
        )
        assertEquals(start, InstantTimeAxis.instantAtX(-1e9, selected, 180.0, scale, range))
        assertEquals(end, InstantTimeAxis.instantAtX(1e9, selected, 180.0, scale, range))
        assertEquals(start, range.clamp(start.minusSeconds(1)))
        assertEquals(end, range.clamp(end.plusSeconds(1)))
    }

    @Test
    fun selectedInstantStaysAtPlayheadAcrossScaleChanges() {
        val start = Instant.parse("2026-01-01T00:00:00Z")
        val range = SupportedTimeInterval(start, start.plusSeconds(7 * 86400L))
        val selected = start.plusSeconds(123_456)

        for (scale in listOf(0.00001, 16.0 / 3600.0, 0.25)) {
            assertEquals(212.5, InstantTimeAxis.xForInstant(selected, selected, 212.5, scale, range), 0.0)
        }
    }

    @Test
    fun calendarDayBoundsUseTrueDstDayLengths() {
        val spring = InstantTimeAxis.localDayBounds(LocalDate.parse("2026-03-08"), chicago)
        val fall = InstantTimeAxis.localDayBounds(LocalDate.parse("2026-11-01"), chicago)

        assertEquals(23 * 3600.0, InstantTimeAxis.elapsedSecondsBetween(spring.startInclusive, spring.endExclusive), 0.0)
        assertEquals(25 * 3600.0, InstantTimeAxis.elapsedSecondsBetween(fall.startInclusive, fall.endExclusive), 0.0)
    }

    @Test
    fun dayShiftPreservesWallTimeAndResolvesGapForward() {
        val beforeSpringGap = LocalDate.parse("2026-03-07").atTime(2, 30).atZone(chicago).toInstant()
        val broadRange = SupportedTimeInterval(beforeSpringGap.minusSeconds(86400), beforeSpringGap.plusSeconds(4 * 86400L))
        val shifted = InstantTimeAxis.shiftLocalDay(beforeSpringGap, 1, chicago, broadRange).atZone(chicago)

        assertEquals(LocalDate.parse("2026-03-08"), shifted.toLocalDate())
        assertEquals(3, shifted.hour)
        assertEquals(0, shifted.minute)
        assertEquals("-05:00", shifted.offset.id)

        val ordinary = LocalDate.parse("2026-03-07").atTime(4, 15).atZone(chicago).toInstant()
        val ordinaryShift = InstantTimeAxis.shiftLocalDay(ordinary, 1, chicago, broadRange).atZone(chicago)
        assertEquals(4, ordinaryShift.hour)
        assertEquals(15, ordinaryShift.minute)
        assertEquals(
            beforeSpringGap,
            InstantTimeAxis.shiftLocalDay(
                beforeSpringGap, -1, chicago, SupportedTimeInterval(beforeSpringGap, beforeSpringGap),
            ),
        )
    }

    @Test
    fun repeatedHoursRemainDistinctAndReceiveOffsetLabels() {
        val first = Instant.parse("2026-11-01T06:30:00Z")
        val second = Instant.parse("2026-11-01T07:30:00Z")
        assertNotEquals(first, second)
        assertEquals(first.atZone(chicago).toLocalDateTime(), second.atZone(chicago).toLocalDateTime())
        assertEquals("-05:00", InstantTimeAxis.ambiguousTimeOffsetLabel(first, chicago))
        assertEquals("-06:00", InstantTimeAxis.ambiguousTimeOffsetLabel(second, chicago))
        assertTrue(first.isBefore(second))
        val broadRange = SupportedTimeInterval(first.minusSeconds(86400), second.plusSeconds(2 * 86400L))
        assertEquals("-05:00", InstantTimeAxis.shiftLocalDay(
            LocalDate.parse("2026-10-31").atTime(1, 30).atZone(chicago).toInstant(), 1, chicago, broadRange,
        ).atZone(chicago).offset.id)
        assertEquals("-06:00", InstantTimeAxis.shiftLocalDay(
            LocalDate.parse("2026-11-02").atTime(1, 30).atZone(chicago).toInstant(), -1, chicago, broadRange,
        ).atZone(chicago).offset.id)
    }

    @Test
    fun overviewAnchorIsLocalNoonAndUsesTheSameCalendarResolution() {
        val date = LocalDate.parse("2026-03-08")
        val anchor = InstantTimeAxis.dailyOverviewAnchor(date, chicago)
        assertEquals(date, anchor.atZone(chicago).toLocalDate())
        assertEquals(12, anchor.atZone(chicago).hour)
        assertEquals(0, anchor.atZone(chicago).minute)
    }

    @Test
    fun scrubbingSourceSwapAndResetClampContinuousRangeWithoutSampleSnapping() {
        val start = Instant.parse("2026-10-31T00:00:00Z")
        val end = Instant.parse("2026-11-03T00:00:00Z")
        val range = SupportedTimeInterval(start, end)
        val selected = Instant.parse("2026-11-01T06:17:23Z")
        val scale = 16.0 / 3600.0

        assertEquals(selected.plusSeconds(3600), InstantTimeAxis.scrub(selected, -16.0, scale, range))
        assertEquals(selected.minusSeconds(3600), InstantTimeAxis.scrub(selected, 16.0, scale, range))
        assertEquals(end, InstantTimeAxis.scrub(end, -10_000.0, scale, range))

        assertEquals(selected, InstantTimeAxis.preserveOrClampSelection(selected, range))
        assertEquals(start, InstantTimeAxis.preserveOrClampSelection(start.minusSeconds(5), range))
        assertEquals(end, InstantTimeAxis.preserveOrClampSelection(end.plusSeconds(5), range))
        assertEquals(start, InstantTimeAxis.resolveResetTarget(start.minusSeconds(1), range))
        assertEquals(end, InstantTimeAxis.resolveResetTarget(end.plusSeconds(1), range))
        assertEquals(selected, InstantTimeAxis.resolveResetTarget(selected, range))
    }
}
