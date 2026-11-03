package com.oxygen.weather.timeline.ui

import android.graphics.Paint
import android.os.SystemClock
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.presentation.CatalogTimelineView
import com.oxygen.weather.timeline.presentation.CatalogTimelineScenePresentation
import com.oxygen.weather.timeline.presentation.CompassDirection
import com.oxygen.weather.timeline.presentation.InstantTimeAxis
import com.oxygen.weather.timeline.presentation.scene.FirstCityComposition
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CatalogTimeLabScreen(
    view: CatalogTimelineView,
    onSelectInstant: (Instant, Long) -> Unit,
    onReturnToNow: () -> Unit,
    onBrowseCatalogs: (() -> Unit)? = null,
    onChooseLocation: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
) = CatalogTimeLabScreenContent(
    view, null, null, onSelectInstant, onReturnToNow, onBrowseCatalogs, onChooseLocation, onRefresh,
)

@Composable
fun CatalogTimeLabScreen(
    presentation: CatalogTimelineScenePresentation,
    onSelectInstant: (Instant, Long) -> Unit,
    onReturnToNow: () -> Unit,
    onBrowseCatalogs: (() -> Unit)? = null,
    onChooseLocation: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    omitOptionalCityLayers: Boolean = false,
    diagnosticOverlay: (@Composable () -> Unit)? = null,
) = CatalogTimeLabScreenContent(
    presentation.timeline,
    presentation.reading,
    presentation.composition.takeIf { !it.hasWeatherState || presentation.timeline.scene != null },
    onSelectInstant, onReturnToNow, onBrowseCatalogs, onChooseLocation, onRefresh,
    omitOptionalCityLayers,
    diagnosticOverlay,
)

@Composable
private fun CatalogTimeLabScreenContent(
    view: CatalogTimelineView,
    sceneReading: SceneReading?,
    composition: FirstCityComposition?,
    onSelectInstant: (Instant, Long) -> Unit,
    onReturnToNow: () -> Unit,
    onBrowseCatalogs: (() -> Unit)?,
    onChooseLocation: (() -> Unit)?,
    onRefresh: (() -> Unit)?,
    omitOptionalCityLayers: Boolean = false,
    diagnosticOverlay: (@Composable () -> Unit)? = null,
) {
    var precision by remember(view.generation) { mutableStateOf(false) }
    var browserOpen by remember { mutableStateOf(false) }
    val compact = with(LocalDensity.current) { fontScale >= 1.3f }
    val latestSelected by rememberUpdatedState(view.selectedInstant)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val locale = Locale.getDefault()
    val selectedDateLabels = remember(view.selectedInstant, view.zone, locale) {
        val date = view.selectedInstant.atZone(view.zone)
        DateTimeFormatter.ofPattern("EEE", locale).format(date).uppercase(locale) to
            DateTimeFormatter.ofPattern("MMM d", locale).format(date)
    }
    val lunarFactsLines = remember(sceneReading?.lunarDay, view.zone, locale) {
        sceneReading?.lunarDay?.let { lunar ->
            val stage = FirstCityTheme.phaseLabel(lunar.phase)
            val timeFormat = DateTimeFormatter.ofPattern("h:mm a", locale)
            fun eventTime(instant: Instant): String {
                val localEvent = instant.atZone(view.zone)
                val time = timeFormat.format(localEvent)
                return if (localEvent.toLocalDate() == lunar.date) time
                else "${DateTimeFormatter.ofPattern("MMM d", locale).format(localEvent)} $time"
            }
            val events = listOfNotNull(
                lunar.rise?.let { "Rise ${eventTime(it)}" },
                lunar.set?.let { "Set ${eventTime(it)}" },
            )
            buildList {
                add("Moon: ${stage ?: "phase unavailable"}")
                addAll(events)
            }
        }
    }
    val readoutShadow = with(LocalDensity.current) {
        Shadow(
            color = Color.Black.copy(alpha = 0.32f),
            offset = Offset(0f, 1.dp.toPx()),
            blurRadius = 2.dp.toPx(),
        )
    }
    BoxWithConstraints(Modifier.fillMaxSize().background(Color(0xFF11263D))) {
        val compactLayout = compact || maxWidth < 400.dp || maxHeight < 400.dp
        // Keep foreground typography and pictograms proportional to the available
        // width. The atmospheric canvas already scales independently to fill it.
        val responsiveScale = (maxWidth.value / 411f).coerceIn(.75f, 1f)
        if (composition != null) {
            CatalogAtmosphericScene(
                composition = composition,
                omitOptionalCityLayers = omitOptionalCityLayers,
            )
        } else if (sceneReading != null) {
            CatalogAtmosphericScene(
                reading = sceneReading,
                zone = view.zone,
                state = view.scene,
                omitOptionalCityLayers = omitOptionalCityLayers,
            )
        } else if (view.scene != null) {
            CatalogAtmosphericScene(
                state = view.scene,
                localHour = view.localHour,
                phaseHours = InstantTimeAxis.elapsedSecondsBetween(
                    view.interval.startInclusive, view.selectedInstant,
                ) / 3600.0,
            )
        }
        diagnosticOverlay?.let { content ->
            Box(Modifier.fillMaxWidth().align(Alignment.TopCenter).statusBarsPadding().padding(top = 56.dp)) {
                content()
            }
        }
        Column(
            Modifier.fillMaxWidth().align(Alignment.Center).offset(y = (-20).dp)
                .padding(horizontal = 25.dp * responsiveScale)
                .pointerInput(view.generation, view.zone, view.interval) {
                    var totalDrag = 0f
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, distance ->
                            totalDrag += distance
                            change.consume()
                        },
                        onDragEnd = {
                            if (abs(totalDrag) > 48.dp.toPx()) {
                                val dayDelta = if ((totalDrag < 0f) != isRtl) 1L else -1L
                                onSelectInstant(
                                    InstantTimeAxis.shiftLocalDay(
                                        latestSelected, dayDelta, view.zone, view.interval,
                                    ), view.generation,
                                )
                            }
                            totalDrag = 0f
                        },
                        onDragCancel = { totalDrag = 0f },
                    )
                }
                .pointerInput(view.generation) {
                    detectTapGestures(
                        onTap = { precision = false },
                        onDoubleTap = { onReturnToNow() },
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                view.selectedLocationName ?: "Selected location",
                color = Color.White,
                fontSize = (if (compactLayout) 21.sp else 24.sp) * responsiveScale,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = TextStyle(shadow = readoutShadow),
                modifier = Modifier.testTag("catalogSelectedLocation"),
            )
            Text(view.temperature ?: "—", color = Color.White,
                fontSize = (if (compactLayout) 78.sp else 102.sp) * responsiveScale,
                fontWeight = FontWeight.Light,
                style = TextStyle(shadow = readoutShadow),
                modifier = Modifier.testTag("forecastTemperature"))
            Text(view.condition ?: "Condition unavailable", color = Color.White,
                fontSize = 22.sp * responsiveScale, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center,
                style = TextStyle(shadow = readoutShadow))
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 14.sp * responsiveScale, fontWeight = FontWeight.SemiBold)) {
                        append("${selectedDateLabels.first} ")
                    }
                    withStyle(SpanStyle(fontSize = 12.sp * responsiveScale, fontWeight = FontWeight.Normal)) {
                        append("${selectedDateLabels.second} · ${view.selectedTimeLabel}")
                    }
                },
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(shadow = readoutShadow),
                modifier = Modifier.testTag("catalogSelectedTime"),
            )
            lunarFactsLines?.let { lines ->
                val phase = sceneReading?.lunarDay?.phase
                if (responsiveScale < .95f && lines.size > 1) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.testTag("catalogLunarFacts")) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            LunarPhaseMarker(phase, responsiveScale)
                            Text(lines.first(), color = Color(0xFFECF3F8),
                                fontSize = 12.sp * responsiveScale, textAlign = TextAlign.Center,
                                style = TextStyle(shadow = readoutShadow))
                        }
                        Text(lines.drop(1).joinToString(" · "), color = Color(0xFFECF3F8),
                            fontSize = 12.sp * responsiveScale, textAlign = TextAlign.Center,
                            style = TextStyle(shadow = readoutShadow))
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.testTag("catalogLunarFacts")) {
                        LunarPhaseMarker(phase, responsiveScale)
                        Text(lines.joinToString(" · "), color = Color(0xFFECF3F8),
                            fontSize = 12.sp * responsiveScale, textAlign = TextAlign.Center,
                            style = TextStyle(shadow = readoutShadow))
                    }
                }
            }
            Text("HIGH ${view.high ?: "—"}    /    LOW ${view.low ?: "—"}",
                color = Color(0xFFECF3F8), fontSize = 12.sp * responsiveScale,
                style = TextStyle(shadow = readoutShadow))
        }
        Column(Modifier.fillMaxWidth().align(Alignment.BottomCenter).navigationBarsPadding()) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 27.dp * responsiveScale),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).semantics(mergeDescendants = true) {
                            contentDescription = "Precipitation chance ${view.precipitationChance ?: "unavailable"}"
                        },
                    ) {
                        CatalogPrecipitationIcon(responsiveScale)
                        Text(view.precipitationChance ?: "Unknown", color = Color.White,
                            fontSize = 19.sp * responsiveScale, fontWeight = FontWeight.Medium,
                            modifier = Modifier.testTag("catalogPrecipitationChance"))
                    }
                    Row(
                        modifier = Modifier.weight(1.2f).heightIn(min = 56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.weight(1f).heightIn(min = 56.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Choose location"
                                }
                                .clickable(
                                    enabled = onChooseLocation != null,
                                    role = Role.Button,
                                ) { onChooseLocation?.invoke() },
                            contentAlignment = Alignment.Center,
                        ) {
                            CatalogMagnifierIcon(responsiveScale)
                        }
                        Box(
                            modifier = Modifier.weight(1f).heightIn(min = 56.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Settings"
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            CatalogSettingsIcon(responsiveScale)
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).semantics(mergeDescendants = true) {
                            val direction = CompassDirection.fromBearing(view.windDirectionDegrees)
                            contentDescription = buildString {
                                append("Wind speed ${view.wind ?: "unavailable"}")
                                direction?.let { append(", from ${it.spokenName}") }
                            }
                        },
                    ) {
                        CatalogWindIndicator(view.windDirectionDegrees, responsiveScale)
                        Text(view.wind ?: "Unknown", color = Color.White,
                            fontSize = 19.sp * responsiveScale, fontWeight = FontWeight.Medium,
                            modifier = Modifier.testTag("catalogWind"))
                    }
                }
            }
            Spacer(Modifier.height(19.dp * responsiveScale))
            CatalogForecastTimeline(
                view = view,
                precision = precision,
                onSelect = { onSelectInstant(it, view.generation) },
                onSetPrecision = { precision = it },
                onBrowse = { browserOpen = true },
            )
        }
        if (browserOpen) {
            CatalogForecastBrowser(
                view = view,
                precision = precision,
                onSelect = { onSelectInstant(it, view.generation) },
                onSetPrecision = { precision = it },
                onDismiss = { browserOpen = false },
            )
        }
    }
}

@Composable
private fun LunarPhaseMarker(phase: Double?, scale: Float) {
    phase?.let {
        Canvas(Modifier.size(14.dp * scale)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val orbit = size.minDimension * .38f
            drawCircle(Color(0xFF526875), radius = size.minDimension * .40f, center = center)
            val angle = it * (2.0 * Math.PI) - Math.PI / 2.0
            val marker = Offset(
                center.x + (kotlin.math.cos(angle) * orbit).toFloat(),
                center.y + (kotlin.math.sin(angle) * orbit).toFloat(),
            )
            drawCircle(Color(0xFFFFD98B), radius = size.minDimension * .11f, center = marker)
        }
        Spacer(Modifier.size(5.dp * scale))
    }
}

@Composable
private fun CatalogPrecipitationIcon(scale: Float) {
    Canvas(Modifier.size(36.dp * scale)) {
        val color = Color.White.copy(alpha = 0.67f)
        val stroke = Stroke(
            width = 1.6.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        )
        fun drop(centerX: Float, top: Float, diameter: Float) = Path().apply {
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
        drawPath(drop(size.width * .50f, size.height * .02f, size.width * .30f), color, style = stroke)
        drawPath(drop(size.width * .26f, size.height * .46f, size.width * .27f), color, style = stroke)
        drawPath(drop(size.width * .74f, size.height * .46f, size.width * .27f), color, style = stroke)
    }
}

@Composable
private fun CatalogMagnifierIcon(scale: Float) {
    Canvas(Modifier.size(32.dp * scale)) {
        val color = Color.White.copy(alpha = 0.67f)
        val stroke = 2.dp.toPx()
        val center = Offset(size.width * .42f, size.height * .42f)
        drawCircle(color, size.minDimension * .27f, center, style = Stroke(stroke))
        drawLine(
            color,
            start = Offset(size.width * .62f, size.height * .62f),
            end = Offset(size.width * .88f, size.height * .88f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun CatalogSettingsIcon(scale: Float) {
    Canvas(Modifier.size(32.dp * scale)) {
        val color = Color.White.copy(alpha = 0.67f)
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension * .46f
        val gear = Path()
        for (tooth in 0 until 8) {
            val base = tooth * 45f
            val points = listOf(
                (base - 22.5f) to .70f,
                (base - 13f) to .70f,
                (base - 13f) to .94f,
                (base + 13f) to .94f,
                (base + 13f) to .70f,
                (base + 22.5f) to .70f,
            )
            points.forEach { (degrees, scale) ->
                val radians = Math.toRadians(degrees.toDouble())
                val point = Offset(
                    center.x + cos(radians).toFloat() * radius * scale,
                    center.y + sin(radians).toFloat() * radius * scale,
                )
                if (gear.isEmpty) gear.moveTo(point.x, point.y) else gear.lineTo(point.x, point.y)
            }
        }
        gear.close()
        drawPath(
            gear,
            color,
            style = Stroke(width = 1.8.dp.toPx(), join = StrokeJoin.Round),
        )
        drawCircle(color, radius * .27f, center, style = Stroke(width = 1.8.dp.toPx()))
    }
}

@Composable
private fun CatalogWindIndicator(bearingDegrees: Double?, scale: Float) {
    Canvas(Modifier.size(32.dp * scale)) {
        val color = Color.White.copy(alpha = 0.67f)
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension * .43f
        val stroke = 1.4.dp.toPx()
        drawCircle(color, radius, center, style = Stroke(width = stroke))
        repeat(8) { tick ->
            rotate(tick * 45f, center) {
                drawLine(
                    color,
                    start = Offset(center.x, center.y - radius + 1.dp.toPx()),
                    end = Offset(
                        center.x,
                        center.y - radius + if (tick % 2 == 0) 5.dp.toPx() else 3.dp.toPx(),
                    ),
                    strokeWidth = stroke,
                )
            }
        }
        bearingDegrees?.let { bearing ->
            rotate(bearing.toFloat(), center) {
                val needle = Path().apply {
                    moveTo(center.x, center.y + radius * .42f)
                    lineTo(center.x, center.y - radius * .72f)
                    moveTo(center.x - radius * .22f, center.y - radius * .38f)
                    lineTo(center.x, center.y - radius * .72f)
                    lineTo(center.x + radius * .22f, center.y - radius * .38f)
                }
                drawPath(
                    needle,
                    color,
                    style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
            }
        }
    }
}

@Composable
private fun CatalogForecastTimeline(
    view: CatalogTimelineView,
    precision: Boolean,
    onSelect: (Instant) -> Unit,
    onSetPrecision: (Boolean) -> Unit,
    onBrowse: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animation by animateFloatAsState(
        targetValue = if (precision) 1f else 0f,
        animationSpec = tween(durationMillis = 260),
        label = "Catalog hourly scale",
    )
    val density = LocalDensity.current
    val compactDayPx = with(density) { 55.dp.toPx().toDouble() }
    val expandedHourPx = with(density) { 16.dp.toPx().toDouble() }
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val selected by rememberUpdatedState(view.selectedInstant)
    val latestPrecision by rememberUpdatedState(precision)
    val latestAnimation by rememberUpdatedState(animation)
    val latestSelect by rememberUpdatedState(onSelect)
    val latestSetPrecision by rememberUpdatedState(onSetPrecision)
    val latestBrowse by rememberUpdatedState(onBrowse)
    fun pixelsPerSecond(zoom: Float) =
        (compactDayPx / 24.0 + (expandedHourPx - compactDayPx / 24.0) * zoom) / 3600.0
    Canvas(
        modifier.fillMaxWidth().height(120.dp).testTag("catalogTimeline")
            .semantics {
                contentDescription = "${view.meaning} timeline. Selected ${view.selectedTimeLabel}. " +
                    "${view.temperature ?: "Temperature unavailable"}; ${view.condition ?: "condition unavailable"}. " +
                    "${view.factTimeLabel?.let { if (it.startsWith("Condition from ")) it else "Fact sample $it" }
                        ?: "No hourly fact for selected hour"}. Activate to browse."
                role = Role.Button
                stateDescription = if (precision) "Hourly scale" else "Daily scale"
                onClick(label = "Browse forecast values") { latestBrowse(); true }
                customActions = listOf(
                    CustomAccessibilityAction(
                        if (precision) "Show daily timeline" else "Show hourly timeline",
                    ) { latestSetPrecision(!latestPrecision); true },
                    CustomAccessibilityAction("Browse forecast values") { latestBrowse(); true },
                )
            }
            .pointerInput(view.generation, rtl) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val playhead = size.width / 2.0
                    val downX = if (rtl) size.width - down.position.x.toDouble() else down.position.x.toDouble()
                    var previousX = down.position.x
                    var gestureSelection = selected
                    var dragging = false
                    var hourly = latestPrecision
                    var holdActivated = false
                    var longPressArmed = !hourly
                    while (true) {
                        val remaining = (480L - (SystemClock.uptimeMillis() - down.uptimeMillis)).coerceAtLeast(1L)
                        val event = if (longPressArmed) withTimeoutOrNull(remaining) { awaitPointerEvent() }
                            else awaitPointerEvent()
                        if (event == null) {
                            val target = InstantTimeAxis.instantAtX(
                                downX, selected, playhead, pixelsPerSecond(latestAnimation), view.interval,
                            )
                            latestSelect(target)
                            gestureSelection = target
                            hourly = true
                            holdActivated = true
                            longPressArmed = false
                            latestSetPrecision(true)
                            continue
                        }
                        val change = event.changes.firstOrNull { it.id == down.id }
                        if (change == null || !change.pressed) {
                            if (!dragging && !holdActivated) {
                                val target = InstantTimeAxis.instantAtX(
                                    downX, selected, playhead, pixelsPerSecond(latestAnimation), view.interval,
                                )
                                if (hourly) {
                                    latestSelect(target)
                                    latestSetPrecision(false)
                                } else {
                                    val date = target.atZone(view.zone).toLocalDate()
                                    latestSelect(view.interval.clamp(
                                        InstantTimeAxis.dailyOverviewAnchor(date, view.zone),
                                    ))
                                }
                            }
                            break
                        }
                        val x = change.position.x
                        val dx = x - previousX
                        previousX = x
                        if (abs(x - down.position.x) > viewConfiguration.touchSlop) {
                            dragging = true
                            longPressArmed = false
                        }
                        if (dragging && dx != 0f) {
                            gestureSelection = InstantTimeAxis.scrub(
                                gestureSelection,
                                if (rtl) -dx.toDouble() else dx.toDouble(),
                                pixelsPerSecond(if (hourly) 1f else latestAnimation),
                                view.interval,
                            )
                            latestSelect(gestureSelection)
                        }
                        change.consume()
                    }
                }
            },
    ) {
        val w = size.width
        val h = size.height
        val playhead = w / 2.0
        val dateY = 30.dp.toPx()
        val railY = 73.dp.toPx()
        val scale = pixelsPerSecond(animation)
        val stroke = 1.dp.toPx()
        drawRect(Brush.verticalGradient(0f to Color.Transparent, 1f to Color(0xB7081628)))
        drawLine(Color(0x33D7EBF7), Offset(0f, railY), Offset(w, railY), stroke)
        fun xFor(instant: Instant): Float {
            val x = InstantTimeAxis.xForInstant(instant, view.selectedInstant, playhead, scale, view.interval)
            return (if (rtl) w - x else x).toFloat()
        }
        view.days.forEach { day ->
            val start = day.date.atStartOfDay(view.zone).toInstant()
            val startX = xFor(start)
            val noonX = xFor(day.overviewInstant)
            if (startX in -8f..(w + 8f)) {
                drawLine(Color(0x4DD7EBF7), Offset(startX, dateY + 6.dp.toPx()),
                    Offset(startX, dateY + 18.dp.toPx()), stroke)
            }
            if (noonX in -60f..(w + 60f)) {
                val selectedDay = view.selectedInstant.atZone(view.zone).toLocalDate() == day.date
                drawIntoCanvas { canvas ->
                    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        color = android.graphics.Color.argb(if (selectedDay) 245 else 175, 244, 250, 255)
                        textAlign = Paint.Align.CENTER
                        textSize = (if (selectedDay) 20.dp else 12.dp).toPx()
                        isFakeBoldText = selectedDay
                    }
                    val dayLabel = day.date.dayOfWeek.name.take(3)
                    canvas.nativeCanvas.drawText(dayLabel, noonX,
                        dateY, paint)
                }
            }
        }
        if (animation > .01f) {
            val selectedHour = view.selectedInstant.atZone(view.zone).withMinute(0).withSecond(0).withNano(0)
            for (offset in -18..18) {
                val tick = selectedHour.plusHours(offset.toLong()).toInstant()
                if (tick < view.interval.startInclusive || tick > view.interval.endInclusive) continue
                val x = xFor(tick)
                if (x !in -30f..(w + 30f)) continue
                val major = tick.atZone(view.zone).hour % 3 == 0
                drawLine(Color.White.copy(alpha = animation * if (major) .82f else .48f),
                    Offset(x, railY - if (major) 9.dp.toPx() else 3.dp.toPx()),
                    Offset(x, railY + if (major) 10.dp.toPx() else 5.dp.toPx()), stroke)
            }
        }
        drawCircle(Color(0xD9FFE2A7), 4.dp.toPx(), Offset(playhead.toFloat(), railY))
        drawLine(Color(0xD9FFE2A7), Offset(playhead.toFloat(), railY - 12.dp.toPx()),
            Offset(playhead.toFloat(), railY + 13.dp.toPx()), 2.dp.toPx())
        drawIntoCanvas { canvas ->
            val native = canvas.nativeCanvas
            val label = view.selectedInstant.atZone(view.zone).format(
                DateTimeFormatter.ofPattern("h:mm a", Locale.US),
            )
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.rgb(255, 240, 201)
                textAlign = Paint.Align.CENTER
                textSize = 12.dp.toPx()
                isFakeBoldText = true
            }
            val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = android.graphics.Color.argb(224, 26, 42, 57)
            }
            val badgeWidth = 66.dp.toPx()
            val badgeHeight = 22.dp.toPx()
            val badgeTop = h - badgeHeight - 2.dp.toPx()
            native.drawRoundRect(
                playhead.toFloat() - badgeWidth / 2f, badgeTop,
                playhead.toFloat() + badgeWidth / 2f, badgeTop + badgeHeight,
                11.dp.toPx(), 11.dp.toPx(), badgePaint,
            )
            native.drawText(label, playhead.toFloat(), badgeTop + 15.dp.toPx(), textPaint)
        }
    }
}

@Composable
private fun CatalogForecastBrowser(
    view: CatalogTimelineView,
    precision: Boolean,
    onSelect: (Instant) -> Unit,
    onSetPrecision: (Boolean) -> Unit,
    onDismiss: () -> Unit,
) {
    val day = view.selectedInstant.atZone(view.zone).toLocalDate()
    val selectedDay = view.days.indexOfFirst { it.date == day }.coerceAtLeast(0)
    val rows = if (precision) view.days[selectedDay].hours else emptyList()
    val selectedHour = rows.indexOfFirst { it.instant == view.selectedInstant }.coerceAtLeast(0)
    val listState = rememberLazyListState()
    LaunchedEffect(precision, selectedDay, selectedHour) {
        listState.scrollToItem(if (precision) selectedHour else selectedDay)
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (precision) "Hourly facts" else "Daily forecasts") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("${view.meaning} · ${view.product} · ${view.source} · ${view.status}")
                Text("Selected ${view.selectedTimeLabel}; fact sample ${view.factTimeLabel ?: "unavailable"}")
                view.error?.let { Text("Load or refresh error: $it") }
                TextButton(onClick = { onSetPrecision(!precision) }) {
                    Text(if (precision) "Browse dates" else "Browse fact times")
                }
                LazyColumn(Modifier.fillMaxWidth().heightIn(max = 440.dp), state = listState) {
                    if (precision) {
                        items(rows, key = { it.instant.toString() }) { row ->
                            Column(Modifier.fillMaxWidth().selectable(
                                selected = row.instant == view.selectedInstant,
                                role = Role.RadioButton,
                                onClick = { onSelect(row.instant) },
                            ).padding(vertical = 8.dp)) {
                                Text(row.label, fontWeight = FontWeight.Medium)
                                Text(row.details)
                            }
                        }
                        if (rows.isEmpty()) item { Text("No fact times for this date") }
                    } else {
                        items(view.days, key = { it.date.toString() }) { row ->
                            Column(Modifier.fillMaxWidth().selectable(
                                selected = row.date == day,
                                role = Role.RadioButton,
                                onClick = { onSelect(row.overviewInstant) },
                            ).padding(vertical = 8.dp)) {
                                Text(row.label, fontWeight = FontWeight.Medium)
                                Text(row.details)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Done") } },
    )
}
