package com.oxygen.weather.timeline.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import com.oxygen.weather.timeline.presentation.ForecastProjection
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

/** Experimental one-screen UI; sample forecast and independent package ID. */
@Composable
fun TimeLabScreen() {
    // Absolute hour offset is the sole selection authority. Zoom never rewrites it.
    var selectedHour by rememberSaveable { mutableDoubleStateOf(36.0) }
    var precisionMode by rememberSaveable { mutableStateOf(false) }
    var focusDay by rememberSaveable { mutableIntStateOf(1) }
    var forecastBrowserOpen by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current
    val swipeThresholdPx = with(LocalDensity.current) { 48.dp.toPx() }

    val view = remember(selectedHour, precisionMode) {
        ForecastProjection.fromHourOffset(selectedHour, precisionMode)
    }
    val hourOfDay = if (precisionMode) selectedHour % 24.0 else 14.0

    fun enterPrecision(day: Int, selected: Double) {
        // Picking a day may change the selected time; expanding does not.
        selectedHour = selected.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        focusDay = day.coerceIn(0, SampleForecast.DAY_COUNT - 1)
        precisionMode = true
        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    fun leavePrecision(wasPrecisionGesture: Boolean) {
        selectedHour = if (wasPrecisionGesture) selectedHour.roundToInt().toDouble()
            else (floor(selectedHour / 24.0) * 24.0 + 12.0)
        selectedHour = selectedHour.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        precisionMode = false
    }
    fun returnToNow() {
        val fixtureStart = SampleForecast.hours.first().time
        val hoursFromStart = Duration.between(fixtureStart, LocalDateTime.now()).toHours().toDouble()
        selectedHour = hoursFromStart.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        focusDay = ElasticTimeAxis.dayIndex(selectedHour)
        precisionMode = true
    }
    fun shiftDay(days: Int) {
        selectedHour = (selectedHour + days * 24.0)
            .coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        focusDay = ElasticTimeAxis.dayIndex(selectedHour)
    }
    val latestReturnToNow by rememberUpdatedState<() -> Unit> { returnToNow() }
    val latestShiftDay by rememberUpdatedState<(Int) -> Unit> { days -> shiftDay(days) }

    BoxWithConstraints(Modifier.fillMaxSize().background(Color(0xFF11263D))) {
        val compactLandscape = maxHeight < 400.dp
        AtmosphericScene(
            input = AtmosphereInput(
                sky = view.sky,
                precisionMode = view.isPrecision,
                localHour = hourOfDay,
                selectedHour = selectedHour,
            )
        )

        Column(
            Modifier.fillMaxWidth().align(Alignment.TopCenter).statusBarsPadding().padding(horizontal = 25.dp),
        ) {
            Spacer(Modifier.height(if (compactLandscape) 5.dp else 19.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("OXYGEN  /  TIME LAB", fontSize = 11.sp, letterSpacing = 2.0.sp,
                        fontWeight = FontWeight.SemiBold, color = Color(0xFFECF5FD))
                    if (!compactLandscape) {
                        Spacer(Modifier.height(4.dp))
                        Text("An interactive atmosphere", fontSize = 12.sp, color = Color(0xCDE4EDF5))
                    }
                }
                Text("DEMO DATA", fontSize = 10.sp, letterSpacing = 1.0.sp,
                    fontWeight = FontWeight.SemiBold, color = Color(0xFFFFE2A7))
            }
        }

        Column(
            Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = 23.dp)
                .padding(bottom = if (compactLandscape) 48.dp else 76.dp)
                .pointerInput(swipeThresholdPx) {
                    var totalDrag = 0f
                    detectHorizontalDragGestures(
                        onDragStart = { totalDrag = 0f },
                        onHorizontalDrag = { change, dragAmount ->
                            totalDrag += dragAmount
                            change.consume()
                        },
                        onDragEnd = {
                            if (abs(totalDrag) >= swipeThresholdPx) {
                                latestShiftDay(if (totalDrag < 0f) 1 else -1)
                            }
                        },
                        onDragCancel = { totalDrag = 0f },
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = { latestReturnToNow() })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (compactLandscape) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${view.temperatureF}°", fontSize = 78.sp, lineHeight = 84.sp,
                        fontWeight = FontWeight.Light, color = Color.White,
                        modifier = Modifier.testTag("forecastTemperature"))
                    Spacer(Modifier.width(18.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(view.heading, fontSize = 13.sp, letterSpacing = 1.5.sp,
                            fontWeight = FontWeight.SemiBold, color = Color.White.copy(alpha = .93f))
                        Spacer(Modifier.height(2.dp))
                        Text(view.timeLabel, fontSize = 12.sp, letterSpacing = 1.5.sp,
                            color = Color.White.copy(alpha = .79f))
                        Spacer(Modifier.height(4.dp))
                        Text(view.conditionsLabel, fontSize = 22.sp,
                            fontWeight = FontWeight.Light, color = Color.White)
                        Spacer(Modifier.height(3.dp))
                        Text("HIGH ${view.highF}°    /    LOW ${view.lowF}°", fontSize = 12.sp,
                            letterSpacing = 1.0.sp, color = Color(0xFFECF3F8))
                    }
                }
            } else {
                Text(view.heading, fontSize = 13.sp, letterSpacing = 2.sp,
                    fontWeight = FontWeight.SemiBold, color = Color.White.copy(alpha = .93f))
                Spacer(Modifier.height(5.dp))
                Text(view.timeLabel, fontSize = 12.sp, letterSpacing = 2.sp,
                    color = Color.White.copy(alpha = .79f))
                Spacer(Modifier.height(13.dp))
                Text("${view.temperatureF}°", fontSize = 102.sp, lineHeight = 108.sp,
                    fontWeight = FontWeight.Light, color = Color.White,
                    modifier = Modifier.testTag("forecastTemperature"))
                Spacer(Modifier.height(3.dp))
                Text(view.conditionsLabel, fontSize = 23.sp,
                    fontWeight = FontWeight.Light, color = Color.White,
                    textAlign = TextAlign.Center)
                Spacer(Modifier.height(10.dp))
                Text("HIGH ${view.highF}°    /    LOW ${view.lowF}°", fontSize = 12.sp,
                    letterSpacing = 1.15.sp, color = Color(0xFFECF3F8))
            }
        }

        Column(
            Modifier.fillMaxWidth().align(Alignment.BottomCenter).navigationBarsPadding(),
        ) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 27.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Column {
                    Text("RAIN CHANCE", fontSize = 9.sp, letterSpacing = 1.4.sp,
                        color = Color(0xCCDDEAF3))
                    Text("${view.precipitationPercent}%", fontSize = 19.sp,
                        fontWeight = FontWeight.Medium, color = Color.White)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("WIND", fontSize = 9.sp, letterSpacing = 1.4.sp,
                        color = Color(0xCCDDEAF3))
                    Text("${view.windMph} mph", fontSize = 19.sp,
                        fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
            Spacer(Modifier.height(13.dp))
            ElasticForecastTimeline(
                selectedHour = selectedHour,
                focusDay = focusDay,
                expanded = precisionMode,
                onSelectedHour = {
                    selectedHour = it
                    if (precisionMode) focusDay = ElasticTimeAxis.dayIndex(it)
                },
                onPrecisionStarted = { day, atHour -> enterPrecision(day, atHour) },
                onTogglePrecision = {
                    if (precisionMode) leavePrecision(true)
                    else enterPrecision(ElasticTimeAxis.dayIndex(selectedHour), selectedHour)
                },
                onBrowseForecasts = { forecastBrowserOpen = true },
                onGestureEnded = { wasPrecision ->
                    // A hold completed an hour scrub. For an ordinary drag, snap to the
                    // middle of the selected day, leaving the represented DAY unchanged.
                    // If expanded by an accessibility action, keep precision active.
                    if (wasPrecision) leavePrecision(true)
                    else if (!precisionMode) leavePrecision(false)
                    else selectedHour = selectedHour.roundToInt().toDouble()
                },
            )
        }
        if (forecastBrowserOpen) {
            AccessibleForecastBrowser(
                selectedHour = selectedHour,
                precision = precisionMode,
                onSelect = { hourOffset, precision ->
                    selectedHour = hourOffset.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
                    precisionMode = precision
                    focusDay = ElasticTimeAxis.dayIndex(selectedHour)
                },
                onTogglePrecision = {
                    precisionMode = !precisionMode
                    focusDay = ElasticTimeAxis.dayIndex(selectedHour)
                },
                onDismiss = { forecastBrowserOpen = false },
            )
        }
    }
}
