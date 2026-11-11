package com.oxygen.weather.timeline.ui

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import com.oxygen.weather.timeline.presentation.ForecastProjection
import com.oxygen.weather.timeline.presentation.ForecastSource
import com.oxygen.weather.timeline.presentation.PresentedCondition
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import com.oxygen.weather.timeline.presentation.symbol.drawWeatherSymbol
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

internal class TimelineDoubleTapDetector(
    private val timeoutMillis: Long = 300L,
) {
    private var previousTapTime = Long.MIN_VALUE
    private var previousTapX = Float.NaN

    fun registerTap(x: Float, uptimeMillis: Long, touchSlop: Float): Boolean {
        val elapsed = uptimeMillis - previousTapTime
        val isDoubleTap = elapsed in 1..timeoutMillis &&
            abs(x - previousTapX) <= touchSlop * 2f
        if (isDoubleTap) {
            clear()
        } else {
            previousTapTime = uptimeMillis
            previousTapX = x
        }
        return isDoubleTap
    }

    fun clear() {
        previousTapTime = Long.MIN_VALUE
        previousTapX = Float.NaN
    }
}

/**
 * One gesture owner, not two competing recognizers.
 * Daily double-tap selects the completing tap's date at 7 AM before entering hourly
 * scale. Hourly double-tap returns to daily while preserving selection. Drag moves
 * the tape beneath the fixed playhead. Single taps and holds do nothing.
 * State always stores absolute hour offsets, never screen coordinates.
 */
@Composable
fun ElasticForecastTimeline(
    source: ForecastSource = ForecastSource.sample,
    selectedHour: Double,
    focusDay: Int,
    expanded: Boolean,
    onSelectedHour: (Double) -> Unit,
    onTogglePrecision: () -> Unit,
    onDailyDoubleTap: (Double) -> Unit,
    onBrowseForecasts: () -> Unit,
    onGestureEnded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animation by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 260),
        label = "Hourly precision scale",
    )
    val density = LocalDensity.current
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val compactDayPx = with(density) { 55.dp.toPx().toDouble() }
    val maxHour = source.lastIndex.toDouble()
    val expandedPx = with(density) { 48.dp.toPx().toDouble() }
    val latestSelected by rememberUpdatedState(selectedHour)
    val latestExpanded by rememberUpdatedState(expanded)
    val latestAnimation by rememberUpdatedState(animation)
    val latestSetHour by rememberUpdatedState(onSelectedHour)
    val latestTogglePrecision by rememberUpdatedState(onTogglePrecision)
    val latestDailyDoubleTap by rememberUpdatedState(onDailyDoubleTap)
    val latestBrowseForecasts by rememberUpdatedState(onBrowseForecasts)
    val latestEnded by rememberUpdatedState(onGestureEnded)
    val accessibleView = ForecastProjection.fromHourOffset(selectedHour, expanded, source)

    fun widthForHour(zoom: Float): Double =
        compactDayPx / 24.0 + (expandedPx - compactDayPx / 24.0) * zoom
    Canvas(
        modifier
            .fillMaxWidth()
            .height(84.dp) // 84dp touch region, even though the visible rail is much thinner
            .testTag("elasticTimeline")
            .semantics {
                contentDescription = "Forecast timeline. ${accessibleView.heading}, " +
                    "${accessibleView.timeLabel}. ${accessibleView.temperatureF} degrees, " +
                    "${accessibleView.conditionsLabel}. Activate to browse forecast values."
                role = Role.Button
                stateDescription = if (expanded) "Hourly scale" else "Daily scale"
                onClick(label = "Browse forecast values") {
                    latestBrowseForecasts()
                    true
                }
                customActions = listOf(
                    CustomAccessibilityAction(
                        label = if (expanded) "Show daily timeline" else "Show hourly timeline",
                        action = {
                            latestTogglePrecision()
                            true
                        },
                    ),
                    CustomAccessibilityAction(
                        label = "Browse forecast values",
                        action = {
                            latestBrowseForecasts()
                            true
                        },
                    ),
                )
            }
            .pointerInput(isRtl) {
                val doubleTapDetector = TimelineDoubleTapDetector()
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val playhead = size.width / 2.0
                    val touchSlop = viewConfiguration.touchSlop
                    var previousX = down.position.x
                    var gestureSelection = latestSelected
                    var peakDistance = 0f
                    var dragging = false
                    var inPrecision = latestExpanded
                    var finished = false
                    while (!finished) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull { it.id == down.id }
                        if (change == null) {
                            doubleTapDetector.clear()
                            finished = true
                        } else if (!change.pressed) {
                            if (!dragging) {
                                if (doubleTapDetector.registerTap(
                                        down.position.x, change.uptimeMillis,
                                        touchSlop,
                                    )
                                ) {
                                    if (latestExpanded) {
                                        latestTogglePrecision()
                                    } else {
                                        val logicalX = if (isRtl) size.width - down.position.x
                                            else down.position.x
                                        val touchedHour = ElasticTimeAxis.hourAtX(
                                            logicalX.toDouble(), latestSelected, size.width / 2.0,
                                            null, compactDayPx, widthForHour(latestAnimation),
                                        )
                                        val touchedDay = floor(
                                            touchedHour / ElasticTimeAxis.HOURS_PER_DAY,
                                        ).toInt()
                                        val targetDay = touchedDay.takeIf { it in source.days.indices } ?: 0
                                        latestDailyDoubleTap(
                                            targetDay * ElasticTimeAxis.HOURS_PER_DAY + 7.0,
                                        )
                                    }
                                }
                            } else {
                                doubleTapDetector.clear()
                            }
                            finished = true
                        } else {
                            val x = change.position.x
                            val dx = (x - previousX).toDouble()
                            previousX = x
                            peakDistance = maxOf(peakDistance, abs(x - down.position.x))
                            if (peakDistance > touchSlop) {
                                dragging = true
                                doubleTapDetector.clear()
                            }
                            if (dragging && dx != 0.0) {
                                // Once hourly precision is active, keep its drag mapping fixed
                                // while the visual scale animation settles. Otherwise identical
                                // pointer deltas produce different time changes from frame to frame.
                                val scrubZoom = if (inPrecision) 1f else latestAnimation
                                val next = ElasticTimeAxis.scrub(
                                    gestureSelection, if (isRtl) -dx else dx, null,
                                    compactDayPx, widthForHour(scrubZoom), maxHour,
                                )
                                // Keep sub-hour movement so small pointer events accumulate;
                                // labels and ticks continue to use the nearest-hour convention.
                                gestureSelection = next
                                latestSetHour(next)
                            }
                            change.consume()
                        }
                    }
                    if (dragging) latestEnded(inPrecision)
                }
            },
    ) {
        val w = size.width
        val h = size.height
        val playhead = w / 2.0
        val zoom = animation
        // Precision mode changes the whole tape to an hourly scale. No single day
        // expands into a local focus window; the selected label carries the focus.
        val dayFocus: Int? = null
        val pixelPerHour = compactDayPx / 24.0 + (expandedPx - compactDayPx / 24.0) * zoom
        val stroke = 1.dp.toPx()
        val baseY = h * .60f
        val weekdayLabelProgress = (zoom / .5f).coerceIn(0f, 1f)
        val weekdayLabelY = baseY - 17.dp.toPx() - 15.dp.toPx() * weekdayLabelProgress
        val selectedDayIndex = (if (zoom > .8f) focusDay else ElasticTimeAxis.dayIndex(selectedHour))
            .coerceIn(0, source.days.lastIndex)
        val hourlyWeekdayLabelVisible = zoom > .5f
        val selectedWeekday = source.days[selectedDayIndex].date.dayOfWeek.name.take(3)
        val focusedWeekdayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 15.dp.toPx()
            isFakeBoldText = true
        }
        val focusedWeekdayWidth = focusedWeekdayPaint.measureText(selectedWeekday)
        val focusedWeekdayLeft = playhead.toFloat() - focusedWeekdayWidth / 2f
        val focusedWeekdayRight = playhead.toFloat() + focusedWeekdayWidth / 2f

        drawRect(
            Brush.verticalGradient(
                0f to Color.Transparent,
                .28f to Color(0x22071628),
                1f to Color(0xB7081628),
            )
        )
        drawLine(Color(0x33D7EBF7), Offset(0f, baseY), Offset(w, baseY), stroke)

        fun mapHour(hour: Double): Float {
            val logicalX = ElasticTimeAxis.xForHour(
                hour, selectedHour, playhead, dayFocus, compactDayPx, pixelPerHour,
            )
            return (if (isRtl) w - logicalX else logicalX).toFloat()
        }
        val firstSampleTime = source.hours.first().time
        fun mapTime(time: LocalDateTime): Float {
            val hourOffset = Duration.between(firstSampleTime, time).toMillis() / 3_600_000.0
            return mapHour(hourOffset)
        }

        for (day in source.days.indices) {
            val startX = mapHour(day * 24.0)
            if (startX >= -8f && startX <= w + 8f) {
                drawLine(Color(0xA6E0EDF6), Offset(startX, baseY - 9.dp.toPx()),
                    Offset(startX, baseY + 10.dp.toPx()), stroke)
            }
            if (startX >= -60f && startX <= w + 60f) {
                val text = source.days[day].date.dayOfWeek.name.take(3)
                val selectedDay = day == selectedDayIndex
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.argb(
                        if (selectedDay) 245 else 175,
                        244, 250, 255,
                    )
                    textAlign = Paint.Align.CENTER
                    textSize = (if (selectedDay) 15.dp else 11.dp).toPx()
                    isFakeBoldText = selectedDay
                }
                val labelWidth = paint.measureText(text)
                val labelLeft = startX - labelWidth / 2f
                val labelRight = startX + labelWidth / 2f
                val overlapsFocusedWeekday = hourlyWeekdayLabelVisible &&
                    labelLeft < focusedWeekdayRight && labelRight > focusedWeekdayLeft
                if (!(hourlyWeekdayLabelVisible && selectedDay) && !overlapsFocusedWeekday) {
                    drawIntoCanvas { canvas ->
                        canvas.nativeCanvas.drawText(text, startX, weekdayLabelY, paint)
                    }
                }
            }
        }
        if (hourlyWeekdayLabelVisible) {
            drawIntoCanvas { canvas ->
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.argb(245, 244, 250, 255)
                    textAlign = Paint.Align.CENTER
                    textSize = 15.dp.toPx()
                    isFakeBoldText = true
                }
                canvas.nativeCanvas.drawText(selectedWeekday, playhead.toFloat(), weekdayLabelY, paint)
            }
        }
        if (zoom > .01f) {
            // Keep the full fixture on one uniform hourly axis; only nearby marks render.
            for (hour in 0..source.lastIndex) {
                val sourceHour = source.hours[hour]
                val midpointTime = sourceHour.time.plusMinutes(30)
                val midpointX = mapTime(midpointTime)
                if (midpointX >= -30f && midpointX <= w + 30f) {
                    drawLine(Color.White.copy(alpha = zoom * .36f),
                        Offset(midpointX, baseY),
                        Offset(midpointX, baseY + 5.dp.toPx()), stroke)
                }
                val x = mapHour(hour.toDouble())
                if (x < -30f || x > w + 30f) continue
                val isMajor = hour % 3 == 0
                val selectedHourTick = hour == selectedHour.roundToInt()
                val alpha = (zoom * (if (isMajor) .82f else .48f)).coerceIn(0f, 1f)
                drawLine(Color.White.copy(alpha = alpha), Offset(x, baseY - if (isMajor) 8.dp.toPx() else 3.dp.toPx()),
                    Offset(x, baseY + if (isMajor) 9.dp.toPx() else 5.dp.toPx()), stroke)
                if (isMajor && !selectedHourTick && abs(hour - selectedHour) >= 2.0) {
                    val displayHour = hour % 24
                    val hourText = when (displayHour) { 0 -> "12a"; 12 -> "12p"; else ->
                        "${displayHour % 12}${if (displayHour < 12) "a" else "p"}" }
                    drawIntoCanvas { canvas ->
                        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            color = android.graphics.Color.argb((225 * zoom).roundToInt(), 238, 247, 255)
                            textSize = 9.dp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                        canvas.nativeCanvas.drawText(hourText, x, baseY + 24.dp.toPx(), paint)
                    }
                }
                if (zoom > .5f && midpointX in -6f..(w + 6f)) {
                    sourceHour.condition.timelineSymbol()?.let { symbol ->
                        drawWeatherSymbol(symbol, Offset(midpointX, baseY - 8.dp.toPx()), 24.dp.toPx())
                    }
                }
            }
            // Keep the chosen hour label on the fixed playhead and magnify it
            // against the regular neighboring three-hour labels.
            val selectedMoment = source.hours[selectedHour.roundToInt().coerceIn(0, source.lastIndex)].time
            val selectedTime = selectedMoment
                .format(java.time.format.DateTimeFormatter.ofPattern("h:mm a", java.util.Locale.US))
                .replace(":00 ", " ")
            drawIntoCanvas { canvas ->
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.argb((245 * zoom).roundToInt(), 255, 226, 167)
                    textAlign = Paint.Align.CENTER
                    textSize = 13.dp.toPx()
                    isFakeBoldText = true
                }
                canvas.nativeCanvas.drawText(selectedTime, playhead.toFloat(), baseY + 25.dp.toPx(), paint)
            }
        }
        // A restrained axis dot marks the fixed selected position without a full-height line.
        drawCircle(
            Color(0xD9FFE2A7),
            radius = 3.dp.toPx(),
            center = Offset(playhead.toFloat(), baseY),
        )
    }
}

private val timelineSymbolsByCondition = mapOf(
    PresentedCondition.CLEAR to WeatherSymbolId.SUNNY,
    PresentedCondition.PARTLY_CLOUDY to WeatherSymbolId.PARTLY_SUNNY_CLOUDY,
    PresentedCondition.CLOUDY to WeatherSymbolId.CLOUDY_OVERCAST,
    PresentedCondition.RAIN to WeatherSymbolId.RAIN,
    PresentedCondition.SNOW to WeatherSymbolId.SNOW,
    PresentedCondition.STORM to WeatherSymbolId.THUNDERSTORM,
)

private fun PresentedCondition.timelineSymbol(): WeatherSymbolId? = timelineSymbolsByCondition[this]
