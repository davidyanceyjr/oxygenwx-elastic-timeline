package com.oxygen.weather.timeline.presentation

import kotlin.math.floor

/**
 * Pure, monotonic time -> pixel coordinate transform.
 * With no focused day, precision scale applies uniformly across the full axis.
 * A non-null focused day retains the localized expansion used by the original lens.
 * The selected time remains an absolute hour offset in either mode.
 */
object ElasticTimeAxis {
    const val HOURS_PER_DAY = 24.0

    fun axis(
        hour: Double,
        focusedDay: Int?,
        compactDayPixels: Double,
        expandedHourPixels: Double,
    ): Double {
        require(compactDayPixels > 0.0 && expandedHourPixels > 0.0)
        val compactHour = compactDayPixels / HOURS_PER_DAY
        if (focusedDay == null) return hour * expandedHourPixels
        val start = focusedDay * HOURS_PER_DAY
        val end = start + HOURS_PER_DAY
        return when {
            hour <= start -> hour * compactHour
            hour < end -> start * compactHour + (hour - start) * expandedHourPixels
            else -> start * compactHour + HOURS_PER_DAY * expandedHourPixels +
                (hour - end) * compactHour
        }
    }

    fun inverse(
        position: Double,
        focusedDay: Int?,
        compactDayPixels: Double,
        expandedHourPixels: Double,
    ): Double {
        require(compactDayPixels > 0.0 && expandedHourPixels > 0.0)
        val compactHour = compactDayPixels / HOURS_PER_DAY
        if (focusedDay == null) return position / expandedHourPixels
        val startHour = focusedDay * HOURS_PER_DAY
        val startX = startHour * compactHour
        val endX = startX + HOURS_PER_DAY * expandedHourPixels
        return when {
            position <= startX -> position / compactHour
            position < endX -> startHour + (position - startX) / expandedHourPixels
            else -> startHour + HOURS_PER_DAY + (position - endX) / compactHour
        }
    }

    fun xForHour(
        hour: Double, selectedHour: Double, playheadX: Double, focusedDay: Int?,
        compactDayPixels: Double, expandedHourPixels: Double,
    ): Double = playheadX + axis(hour, focusedDay, compactDayPixels, expandedHourPixels) -
        axis(selectedHour, focusedDay, compactDayPixels, expandedHourPixels)

    fun hourAtX(
        x: Double, selectedHour: Double, playheadX: Double, focusedDay: Int?,
        compactDayPixels: Double, expandedHourPixels: Double,
    ): Double = inverse(
        axis(selectedHour, focusedDay, compactDayPixels, expandedHourPixels) + x - playheadX,
        focusedDay, compactDayPixels, expandedHourPixels,
    )

    /** Dragging left means future time advances toward the playhead. */
    fun scrub(
        selectedHour: Double, dragDeltaPx: Double, focusedDay: Int?,
        compactDayPixels: Double, expandedHourPixels: Double,
        maxHour: Double,
    ): Double = inverse(
        axis(selectedHour, focusedDay, compactDayPixels, expandedHourPixels) - dragDeltaPx,
        focusedDay, compactDayPixels, expandedHourPixels,
    ).coerceIn(0.0, maxHour)

    fun dayIndex(hour: Double): Int = floor(hour / HOURS_PER_DAY).toInt()
}
