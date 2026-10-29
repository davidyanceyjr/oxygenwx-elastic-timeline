package com.oxygen.weather.timeline.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import com.oxygen.weather.timeline.presentation.ForecastProjection
import com.oxygen.weather.timeline.presentation.ForecastSource
import com.oxygen.weather.timeline.presentation.CompassDirection
import com.oxygen.weather.timeline.presentation.PresentedWarning
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

/** Experimental one-screen UI; sample forecast and independent package ID. */
@Composable
fun TimeLabScreen(
    forecastSource: ForecastSource = ForecastSource.sample,
    effectPreview: AtmospherePreview = AtmospherePreview.NONE,
    initialSelectedHour: Double = forecastSource.referenceIndex.toDouble(),
    initialPrecisionMode: Boolean = false,
    placeSearcher: PlaceSearcher? = null,
    locationStore: LocationSelectorStore? = null,
    openLocationSelectorOnLaunch: Boolean = false,
    showDebugLocationLauncher: Boolean = false,
) {
    // Absolute hour offset is the sole selection authority. Zoom never rewrites it.
    var selectedHour by rememberSaveable(forecastSource.id) {
        mutableDoubleStateOf(initialSelectedHour.coerceIn(0.0, forecastSource.lastIndex.toDouble()))
    }
    var precisionMode by rememberSaveable { mutableStateOf(initialPrecisionMode) }
    var focusDay by rememberSaveable { mutableIntStateOf(1) }
    var forecastBrowserOpen by remember { mutableStateOf(false) }
    var locationSelectorOpen by rememberSaveable { mutableStateOf(openLocationSelectorOnLaunch) }
    val locationSelectorScope = androidx.compose.runtime.rememberCoroutineScope()
    val locationSelectorController = if (placeSearcher != null && locationStore != null) {
        remember(placeSearcher, locationStore, locationSelectorScope) {
            LocationSelectorController(locationSelectorScope, placeSearcher, locationStore)
        }
    } else null
    val haptics = LocalHapticFeedback.current
    val swipeThresholdPx = with(LocalDensity.current) { 48.dp.toPx() }
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val view = remember(selectedHour, precisionMode, forecastSource) {
        ForecastProjection.fromHourOffset(selectedHour, precisionMode, forecastSource)
    }
    val hourOfDay = if (precisionMode) selectedHour % 24.0 else 12.0

    fun enterPrecision(day: Int, selected: Double) {
        // Picking a day may change the selected time; expanding does not.
        selectedHour = selected.roundToInt().coerceIn(0, forecastSource.lastIndex).toDouble()
        focusDay = day.coerceIn(0, forecastSource.days.lastIndex)
        precisionMode = true
        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    fun leavePrecision(wasPrecisionGesture: Boolean) {
        selectedHour = if (wasPrecisionGesture) selectedHour
            else (floor(selectedHour / 24.0) * 24.0 + 12.0)
        selectedHour = selectedHour.coerceIn(0.0, forecastSource.lastIndex.toDouble())
        precisionMode = false
    }
    fun returnToNow() {
        val hoursFromStart = if (forecastSource.resetToReference) forecastSource.referenceIndex.toDouble()
        else Duration.between(forecastSource.hours.first().time, LocalDateTime.now()).toHours().toDouble()
        selectedHour = hoursFromStart.coerceIn(0.0, forecastSource.lastIndex.toDouble())
        focusDay = ElasticTimeAxis.dayIndex(selectedHour)
        precisionMode = true
    }
    fun shiftDay(days: Int) {
        selectedHour = (selectedHour + days * 24.0).coerceIn(0.0, forecastSource.lastIndex.toDouble())
        focusDay = ElasticTimeAxis.dayIndex(selectedHour)
    }
    val latestReturnToNow by rememberUpdatedState<() -> Unit> { returnToNow() }
    val latestShiftDay by rememberUpdatedState<(Int) -> Unit> { days -> shiftDay(days) }
    val latestExitPrecision by rememberUpdatedState<() -> Unit> {
        if (precisionMode) leavePrecision(true)
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(Color(0xFF11263D))) {
        val compactLandscape = maxHeight < 400.dp
        val compactPresentation = compactLandscape || LocalDensity.current.fontScale >= 1.3f
        AtmosphericScene(
            input = AtmosphereInput(
                sky = view.sky,
                precisionMode = view.isPrecision,
                localHour = hourOfDay,
                selectedHour = selectedHour,
                preview = effectPreview,
            )
        )

        Column(
            Modifier.fillMaxWidth().align(Alignment.TopCenter).statusBarsPadding().padding(horizontal = 25.dp),
        ) {
            Spacer(Modifier.height(if (compactPresentation) 5.dp else 19.dp))
            if (effectPreview != AtmospherePreview.NONE) {
                Text(
                    text = "ILLUSTRATIVE PREVIEW",
                    fontSize = 9.sp,
                    letterSpacing = .7.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFE2A7),
                )
                Spacer(Modifier.height(6.dp))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("OXYGEN  /  TIME LAB", fontSize = 11.sp, letterSpacing = 2.0.sp,
                        fontWeight = FontWeight.SemiBold, color = Color(0xFFECF5FD))
                    if (!compactPresentation) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            if (view.provenanceDetail.isEmpty()) "An interactive atmosphere" else view.provenanceDetail,
                            fontSize = 12.sp,
                            color = Color(0xCDE4EDF5),
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(view.provenanceLabel, fontSize = 10.sp, letterSpacing = 1.0.sp,
                        fontWeight = FontWeight.SemiBold, color = Color(0xFFFFE2A7))
                    if (compactPresentation && view.provenanceDetail.isNotEmpty()) {
                        Text(view.provenanceDetail, fontSize = 8.sp, color = Color(0xFFFFE2A7))
                    }
                    if (showDebugLocationLauncher && locationSelectorController != null) {
                        TextButton(
                            onClick = { locationSelectorOpen = true },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFECF5FD)),
                        ) { Text("LOCATIONS · DEBUG", fontSize = 10.sp, letterSpacing = .6.sp) }
                    }
                }
            }
        }

        Column(
            Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = 23.dp)
                .padding(bottom = if (compactPresentation) 48.dp else 76.dp)
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
                                latestShiftDay(if ((totalDrag < 0f) != isRtl) 1 else -1)
                            }
                        },
                        onDragCancel = { totalDrag = 0f },
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { latestExitPrecision() },
                        onDoubleTap = { latestReturnToNow() },
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (compactPresentation) {
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
                            color = Color.White.copy(alpha = .79f),
                            style = TextStyle(textDirection = TextDirection.Ltr))
                        Spacer(Modifier.height(4.dp))
                        Text(displayConditionsLabel(view.conditionsLabel, effectPreview), fontSize = 22.sp,
                            fontWeight = FontWeight.Light, color = Color.White)
                        DebugFactLine(view.thunderstormProbabilityPercent, view.warning)
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
                    color = Color.White.copy(alpha = .79f),
                    style = TextStyle(textDirection = TextDirection.Ltr))
                Spacer(Modifier.height(13.dp))
                Text("${view.temperatureF}°", fontSize = 102.sp, lineHeight = 108.sp,
                    fontWeight = FontWeight.Light, color = Color.White,
                    modifier = Modifier.testTag("forecastTemperature"))
                Spacer(Modifier.height(3.dp))
                Text(displayConditionsLabel(view.conditionsLabel, effectPreview), fontSize = 23.sp,
                    fontWeight = FontWeight.Light, color = Color.White,
                    textAlign = TextAlign.Center)
                DebugFactLine(view.thunderstormProbabilityPercent, view.warning)
                Spacer(Modifier.height(10.dp))
                Text("HIGH ${view.highF}°    /    LOW ${view.lowF}°", fontSize = 12.sp,
                    letterSpacing = 1.15.sp, color = Color(0xFFECF3F8))
            }
        }

        Column(
            Modifier.fillMaxWidth().align(Alignment.BottomCenter).navigationBarsPadding(),
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 27.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.semantics(mergeDescendants = true) {
                            contentDescription = "Precipitation chance ${view.precipitationPercent?.let { "$it percent" } ?: "unknown"}"
                        },
                    ) {
                        RainSignalIcon()
                        Text(view.precipitationPercent?.let { "$it%" } ?: "Unknown", fontSize = 19.sp,
                            fontWeight = FontWeight.Medium, color = Color.White)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.semantics(mergeDescendants = true) {
                            val direction = CompassDirection.fromBearing(view.windDirectionDegrees)
                            contentDescription = buildString {
                                append("Wind speed ${view.windMph} miles per hour")
                                direction?.let { append(", from ${it.spokenName}") }
                            }
                        },
                    ) {
                        CompassIndicator(view.windDirectionDegrees)
                        Text("${view.windMph} mph", fontSize = 19.sp,
                            fontWeight = FontWeight.Medium, color = Color.White)
                    }
                }
            }
            Spacer(Modifier.height(35.dp))
            ElasticForecastTimeline(
                source = forecastSource,
                selectedHour = selectedHour,
                focusDay = focusDay,
                expanded = precisionMode,
                onSelectedHour = {
                    selectedHour = it
                    if (precisionMode) focusDay = ElasticTimeAxis.dayIndex(it).coerceIn(0, forecastSource.days.lastIndex)
                },
                onPrecisionStarted = { day, atHour -> enterPrecision(day, atHour) },
                onTimelineTap = { hourOffset ->
                    selectedHour = hourOffset.coerceIn(0.0, forecastSource.lastIndex.toDouble())
                    focusDay = ElasticTimeAxis.dayIndex(selectedHour).coerceIn(0, forecastSource.days.lastIndex)
                    precisionMode = false
                },
                onTogglePrecision = {
                    if (precisionMode) leavePrecision(true)
                    else enterPrecision(ElasticTimeAxis.dayIndex(selectedHour), selectedHour)
                },
                onBrowseForecasts = { forecastBrowserOpen = true },
                onGestureEnded = { wasPrecision ->
                    // Precision remains active after release. Only a later explicit
                    // main-area or timeline tap exits it. Daily drags still settle at noon.
                    if (!wasPrecision && !precisionMode) leavePrecision(false)
                },
            )
        }
        if (forecastBrowserOpen) {
            AccessibleForecastBrowser(
                source = forecastSource,
                selectedHour = selectedHour,
                precision = precisionMode,
                onSelect = { hourOffset, precision ->
                    selectedHour = hourOffset.coerceIn(0.0, forecastSource.lastIndex.toDouble())
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
        if (locationSelectorOpen && locationSelectorController != null) {
            LocationSelectorPanel(
                controller = locationSelectorController,
                onDismiss = { locationSelectorOpen = false },
            )
        }
    }
}

private fun displayConditionsLabel(label: String, preview: AtmospherePreview): String =
    when (preview) {
        AtmospherePreview.ILLUSTRATIVE_CLEAR_NIGHT -> "Illustrative clear night"
        AtmospherePreview.ILLUSTRATIVE_STORM -> "Illustrative storm"
        else -> label
    }

@Composable
private fun RainSignalIcon() {
    Canvas(Modifier.width(36.dp).height(36.dp)) {
        val w = size.width
        val h = size.height
        val color = Color(0xFFBFE9F5)

        fun drop(centerX: Float, top: Float, diameter: Float): Path = Path().apply {
            moveTo(centerX, top)
            cubicTo(centerX - diameter * .15f, top + diameter * .20f,
                centerX - diameter * .52f, top + diameter * .56f,
                centerX - diameter * .46f, top + diameter * .72f)
            cubicTo(centerX - diameter * .39f, top + diameter * 1.05f,
                centerX + diameter * .39f, top + diameter * 1.05f,
                centerX + diameter * .46f, top + diameter * .72f)
            cubicTo(centerX + diameter * .52f, top + diameter * .56f,
                centerX + diameter * .15f, top + diameter * .20f,
                centerX, top)
            close()
        }

        drawPath(drop(w * .50f, h * .04f, w * .30f), color)
        drawPath(drop(w * .26f, h * .48f, w * .27f), color)
        drawPath(drop(w * .74f, h * .48f, w * .27f), color)
    }
}

@Composable
private fun CompassIndicator(bearingDegrees: Double?) {
    Canvas(Modifier.width(32.dp).height(32.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension * .43f
        val faceColor = Color(0xB8D7E4EE)
        drawCircle(faceColor, radius, center, style = Stroke(width = 1.5.dp.toPx()))

        repeat(8) { tick ->
            rotate(tick * 45f, center) {
                drawLine(
                    color = faceColor,
                    start = Offset(center.x, center.y - radius + 1.dp.toPx()),
                    end = Offset(center.x, center.y - radius + if (tick % 2 == 0) 5.dp.toPx() else 3.dp.toPx()),
                    strokeWidth = 1.dp.toPx(),
                )
            }
        }

        bearingDegrees?.let { bearing ->
            rotate(bearing.toFloat(), center) {
                val needle = Path().apply {
                    moveTo(center.x, center.y - radius * .77f)
                    lineTo(center.x + radius * .17f, center.y + radius * .10f)
                    lineTo(center.x, center.y)
                    lineTo(center.x - radius * .17f, center.y + radius * .10f)
                    close()
                }
                val tail = Path().apply {
                    moveTo(center.x, center.y + radius * .67f)
                    lineTo(center.x + radius * .14f, center.y - radius * .02f)
                    lineTo(center.x, center.y)
                    lineTo(center.x - radius * .14f, center.y - radius * .02f)
                    close()
                }
                drawPath(needle, Color(0xFFFFD985))
                drawPath(tail, Color(0xB8D7E4EE))
            }
        }
    }
}

@Composable
private fun DebugFactLine(thunderstormChance: Int?, warning: PresentedWarning) {
    if (thunderstormChance == null && warning != PresentedWarning.PRESENT) return
    val details = buildList {
        thunderstormChance?.let { add("THUNDERSTORM CHANCE $it%") }
        if (warning == PresentedWarning.PRESENT) add("DEBUG WARNING PREVIEW")
    }.joinToString(" · ")
    Text(
        details,
        fontSize = 10.sp,
        letterSpacing = .7.sp,
        color = Color(0xFFFFE2A7),
        textAlign = TextAlign.Center,
    )
}
