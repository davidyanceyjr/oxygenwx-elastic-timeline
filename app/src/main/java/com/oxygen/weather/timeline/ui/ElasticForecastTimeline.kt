package com.oxygen.weather.timeline.ui

import android.graphics.Paint
import android.os.SystemClock
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

private const val HOLD_DURATION_MS = 480L

/**
 * One gesture owner, not two competing recognizers.
 * Tap = jump to day; drag = time tape moves beneath fixed playhead;
 * hold without dragging = switch the whole rail to equally spaced hours, then scrub.
 * State always stores absolute hour offsets, never screen coordinates.
 */
@Composable
fun ElasticForecastTimeline(
    selectedHour: Double,
    focusDay: Int,
    expanded: Boolean,
    onSelectedHour: (Double) -> Unit,
    onPrecisionStarted: (Int, Double) -> Unit,
    onGestureEnded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animation by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 260),
        label = "Hourly precision scale",
    )
    val density = LocalDensity.current
    val compactDayPx = with(density) { 55.dp.toPx().toDouble() }
    val maxHour = SampleForecast.hours.lastIndex.toDouble()
    val expandedPx = with(density) { 16.dp.toPx().toDouble() }
    val latestSelected by rememberUpdatedState(selectedHour)
    val latestAnimation by rememberUpdatedState(animation)
    val latestSetHour by rememberUpdatedState(onSelectedHour)
    val latestPrecision by rememberUpdatedState(onPrecisionStarted)
    val latestEnded by rememberUpdatedState(onGestureEnded)

    fun widthForHour(zoom: Float): Double =
        compactDayPx / 24.0 + (expandedPx - compactDayPx / 24.0) * zoom
    Canvas(
        modifier
            .fillMaxWidth()
            .height(84.dp) // 84dp touch region, even though the visible rail is much thinner
            .testTag("elasticTimeline")
            .semantics {
                contentDescription = "Forecast time tape. Tap to select a day, drag to browse days, " +
                    "or press and hold then drag to inspect hours."
            }
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val downX = down.position.x.toDouble()
                    val playhead = size.width / 2.0
                    val touchSlop = viewConfiguration.touchSlop
                    var previousX = down.position.x
                    var gestureSelection = latestSelected
                    var peakDistance = 0f
                    var dragging = false
                    var inPrecision = false
                    var longPressArmed = true
                    var finished = false
                    while (!finished) {
                        val remainingMs = (HOLD_DURATION_MS -
                            (SystemClock.uptimeMillis() - down.uptimeMillis)).coerceAtLeast(1L)
                        val event = if (longPressArmed) {
                            withTimeoutOrNull(remainingMs) { awaitPointerEvent() }
                        } else awaitPointerEvent()
                        if (event == null) {
                            // Long-press fired while finger is stationary: select the time at
                            // the touch, THEN expand without changing that selected timestamp.
                            val tappedHour = ElasticTimeAxis.hourAtX(
                                downX, latestSelected, playhead, null,
                                compactDayPx, widthForHour(latestAnimation),
                            ).coerceIn(0.0, maxHour)
                            val day = ElasticTimeAxis.dayIndex(tappedHour)
                            latestPrecision(day, tappedHour)
                            gestureSelection = tappedHour
                            inPrecision = true
                            longPressArmed = false
                            continue
                        }
                        val change = event.changes.firstOrNull { it.id == down.id }
                        if (change == null || !change.pressed) {
                            if (!dragging && !inPrecision) {
                                val target = ElasticTimeAxis.hourAtX(
                                    downX, latestSelected, playhead, null,
                                    compactDayPx, widthForHour(latestAnimation),
                                ).coerceIn(0.0, maxHour)
                                val day = ElasticTimeAxis.dayIndex(target)
                                latestSetHour((day * 24.0 + 12.0).coerceAtMost(maxHour))
                            }
                            finished = true
                        } else {
                            val x = change.position.x
                            val dx = (x - previousX).toDouble()
                            previousX = x
                            peakDistance = maxOf(peakDistance, abs(x - down.position.x))
                            if (peakDistance > touchSlop) {
                                dragging = true
                                longPressArmed = false
                            }
                            if (dragging && dx != 0.0) {
                                val next = ElasticTimeAxis.scrub(
                                    gestureSelection, dx, null,
                                    compactDayPx, widthForHour(latestAnimation), maxHour,
                                )
                                gestureSelection = next
                                latestSetHour(next)
                            }
                            change.consume()
                        }
                    }
                    latestEnded(inPrecision)
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

        drawRect(
            Brush.verticalGradient(
                0f to Color.Transparent,
                .28f to Color(0x22071628),
                1f to Color(0xB7081628),
            )
        )
        drawLine(Color(0x33D7EBF7), Offset(0f, baseY), Offset(w, baseY), stroke)

        fun mapHour(hour: Double): Float = ElasticTimeAxis.xForHour(
            hour, selectedHour, playhead, dayFocus, compactDayPx, pixelPerHour,
        ).toFloat()

        for (day in 0 until SampleForecast.DAY_COUNT) {
            val startX = mapHour(day * 24.0)
            val midX = mapHour(day * 24.0 + 12.0)
            if (startX >= -8f && startX <= w + 8f) {
                drawLine(Color(0xA6E0EDF6), Offset(startX, baseY - 9.dp.toPx()),
                    Offset(startX, baseY + 10.dp.toPx()), stroke)
            }
            if (midX >= -60f && midX <= w + 60f) {
                val text = SampleForecast.days[day].date.dayOfWeek.name.take(3)
                val selectedDayIndex = if (zoom > .8f) focusDay
                    else ElasticTimeAxis.dayIndex(selectedHour)
                val selectedDay = day == selectedDayIndex
                drawIntoCanvas { canvas ->
                    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        color = android.graphics.Color.argb(
                            if (selectedDay) 245 else 175,
                            244, 250, 255,
                        )
                        textAlign = Paint.Align.CENTER
                        textSize = (if (selectedDay) 15.dp else 11.dp).toPx()
                        isFakeBoldText = selectedDay
                    }
                    canvas.nativeCanvas.drawText(text, midX, baseY - 17.dp.toPx(), paint)
                }
            }
        }
        if (zoom > .01f) {
            // Keep the full fixture on one uniform hourly axis; only nearby marks render.
            for (hour in 0..SampleForecast.hours.lastIndex) {
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
            }
            // Keep the chosen hour label on the fixed playhead and magnify it
            // against the regular neighboring three-hour labels.
            val selectedTick = selectedHour.roundToInt().coerceIn(0, SampleForecast.hours.lastIndex)
            val selectedTime = SampleForecast.hours[selectedTick].time
                .format(java.time.format.DateTimeFormatter.ofPattern("h a", java.util.Locale.US))
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
