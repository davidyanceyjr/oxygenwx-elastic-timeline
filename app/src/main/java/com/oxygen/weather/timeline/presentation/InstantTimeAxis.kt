package com.oxygen.weather.timeline.presentation

import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.zone.ZoneOffsetTransition
import kotlin.math.roundToLong

/** A single continuous supported interval. Both endpoints are selectable. */
data class SupportedTimeInterval(val startInclusive: Instant, val endInclusive: Instant) {
    init {
        require(!endInclusive.isBefore(startInclusive)) { "Supported interval end precedes start" }
    }

    fun clamp(instant: Instant): Instant = when {
        instant.isBefore(startInclusive) -> startInclusive
        instant.isAfter(endInclusive) -> endInclusive
        else -> instant
    }
}

/** True local-day bounds. The end is exclusive and may be 23 or 25 elapsed hours after the start. */
data class LocalDayBounds(val startInclusive: Instant, val endExclusive: Instant) {
    init { require(endExclusive.isAfter(startInclusive)) }
}

/**
 * Pure absolute-time axis helpers. Coordinate mapping uses elapsed seconds only; timezone rules
 * are consulted only by the explicit calendar operations below.
 */
object InstantTimeAxis {
    /**
     * Places [instant] relative to [selectedInstant], which remains at [playheadX]. Values outside
     * the supported interval clamp to its endpoints. The hot path does no timezone conversion or
     * Duration allocation.
     */
    fun xForInstant(
        instant: Instant,
        selectedInstant: Instant,
        playheadX: Double,
        pixelsPerSecond: Double,
        supported: SupportedTimeInterval,
    ): Double {
        require(playheadX.isFinite())
        require(pixelsPerSecond.isFinite() && pixelsPerSecond > 0.0)
        val selected = supported.clamp(selectedInstant)
        val target = supported.clamp(instant)
        return playheadX + secondsBetween(selected, target) * pixelsPerSecond
    }

    /** Inverts elapsed-time pixel projection and clamps to the supported interval. */
    fun instantAtX(
        x: Double,
        anchorInstant: Instant,
        anchorX: Double,
        pixelsPerSecond: Double,
        supported: SupportedTimeInterval,
    ): Instant {
        return supported.clamp(
            instantAtXUnbounded(x, supported.clamp(anchorInstant), anchorX, pixelsPerSecond),
        )
    }

    /** Inverts the absolute-time projection without applying catalog bounds. */
    fun instantAtXUnbounded(
        x: Double,
        anchorInstant: Instant,
        anchorX: Double,
        pixelsPerSecond: Double,
    ): Instant {
        require(x.isFinite() && anchorX.isFinite())
        require(pixelsPerSecond.isFinite() && pixelsPerSecond > 0.0)
        val deltaNanos = ((x - anchorX) / pixelsPerSecond * NANOS_PER_SECOND).roundToLong()
        val target = try {
            anchorInstant.plusNanos(deltaNanos)
        } catch (_: ArithmeticException) {
            if (deltaNanos < 0) Instant.MIN else Instant.MAX
        } catch (_: DateTimeException) {
            if (deltaNanos < 0) Instant.MIN else Instant.MAX
        }
        return target
    }

    /** Dragging the tape left (negative delta) advances the selected instant. */
    fun scrub(
        selectedInstant: Instant,
        dragDeltaPixels: Double,
        pixelsPerSecond: Double,
        supported: SupportedTimeInterval,
    ): Instant {
        require(dragDeltaPixels.isFinite())
        return instantAtX(
            x = -dragDeltaPixels,
            anchorInstant = selectedInstant,
            anchorX = 0.0,
            pixelsPerSecond = pixelsPerSecond,
            supported = supported,
        )
    }

    /** Preserve the exact selected instant when supported by a replacement dataset; otherwise clamp. */
    fun preserveOrClampSelection(selectedInstant: Instant, targetInterval: SupportedTimeInterval): Instant =
        targetInterval.clamp(selectedInstant)

    /** Resolve an explicit reset target (LIVE now or DEBUG reference) without reading a clock/source. */
    fun resolveResetTarget(explicitTarget: Instant, activeInterval: SupportedTimeInterval): Instant =
        activeInterval.clamp(explicitTarget)

    /** Shift by local calendar dates while retaining the wall-clock time where it exists. */
    fun shiftLocalDay(
        selectedInstant: Instant,
        dayDelta: Long,
        zone: ZoneId,
        supported: SupportedTimeInterval,
    ): Instant {
        val selected = selectedInstant.atZone(zone)
        val targetLocal = LocalDateTime.of(selected.toLocalDate().plusDays(dayDelta), selected.toLocalTime())
        return supported.clamp(resolveLocalDateTime(targetLocal, zone, selected.offset))
    }

    /** Local noon as an overview anchor, independent of forecast sample availability. */
    fun dailyOverviewAnchor(
        date: LocalDate,
        zone: ZoneId,
        preferredOffset: ZoneOffset? = null,
    ): Instant = resolveLocalDateTime(date.atTime(12, 0), zone, preferredOffset)

    fun localDayBounds(date: LocalDate, zone: ZoneId): LocalDayBounds = LocalDayBounds(
        date.atStartOfDay(zone).toInstant(), date.plusDays(1).atStartOfDay(zone).toInstant(),
    )

    /** Offset text is needed only when the local wall time occurs more than once. */
    fun ambiguousTimeOffsetLabel(instant: Instant, zone: ZoneId): String? {
        val local = instant.atZone(zone).toLocalDateTime()
        return if (zone.rules.getValidOffsets(local).size > 1) instant.atZone(zone).offset.id else null
    }

    /** Elapsed seconds from [from] to [to], with subsecond precision and no Duration object. */
    fun elapsedSecondsBetween(from: Instant, to: Instant): Double = secondsBetween(from, to)

    private fun resolveLocalDateTime(
        localDateTime: LocalDateTime,
        zone: ZoneId,
        preferredOffset: ZoneOffset?,
    ): Instant {
        val offsets = zone.rules.getValidOffsets(localDateTime)
        return when (offsets.size) {
            1 -> localDateTime.toInstant(offsets.single())
            2 -> localDateTime.toInstant(preferredOffset?.takeIf(offsets::contains) ?: offsets.first())
            else -> {
                val transition: ZoneOffsetTransition = requireNotNull(zone.rules.getTransition(localDateTime))
                transition.dateTimeAfter.toInstant(transition.offsetAfter)
            }
        }
    }

    private fun secondsBetween(from: Instant, to: Instant): Double =
        (to.epochSecond - from.epochSecond).toDouble() + (to.nano - from.nano) / NANOS_PER_SECOND

    private const val NANOS_PER_SECOND = 1_000_000_000.0
}
