package com.oxygen.weather.timeline.ui

import android.graphics.Paint
import android.graphics.Rect
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.presentation.CatalogTimelineView
import com.oxygen.weather.timeline.presentation.CatalogTimelineScenePresentation
import com.oxygen.weather.timeline.presentation.CompassDirection
import com.oxygen.weather.timeline.presentation.InstantTimeAxis
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolIcon
import com.oxygen.weather.timeline.presentation.symbol.drawWeatherSymbol
import com.oxygen.weather.timeline.presentation.scene.FirstCityComposition
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

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
    val readoutGestures = Modifier
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
            Modifier.fillMaxWidth().align(Alignment.Center)
                .offset(y = (if (compactLayout) -36 else -48).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height((if (compactLayout) 56.dp else 72.dp) * responsiveScale)
                    .then(readoutGestures),
                contentAlignment = Alignment.Center,
            ) {
                WeatherSymbolIcon(
                    symbol = view.weatherSymbolId ?: WeatherSymbolId.CONDITION_UNAVAILABLE,
                    modifier = Modifier.size((if (compactLayout) 56.dp else 72.dp) * responsiveScale),
                    contentDescription = view.weatherSymbolId?.let { view.weatherSymbolName ?: it.label }
                        ?: WeatherSymbolId.CONDITION_UNAVAILABLE.label,
                )
            }
            Spacer(Modifier.height(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp * responsiveScale),
            ) {
                Text(
                    view.selectedLocationName ?: "Selected location",
                    color = Color.White,
                    fontSize = (if (compactLayout) 21.sp else 24.sp) * responsiveScale,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(shadow = readoutShadow),
                    modifier = Modifier.testTag("catalogSelectedLocation"),
                )
                if (onChooseLocation != null) {
                    Box(
                        modifier = Modifier.size(48.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription = "Choose location"
                            }
                            .clickable(role = Role.Button) { onChooseLocation() },
                        contentAlignment = Alignment.Center,
                    ) {
                        CatalogLocationPickerIcon(responsiveScale)
                    }
                }
            }
            Spacer(Modifier.height(7.dp))
            Column(
                Modifier.fillMaxWidth()
                    .then(readoutGestures),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(view.condition ?: "Condition unavailable", color = Color(0xFFECF3F8),
                    fontSize = 18.sp * responsiveScale, fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(shadow = readoutShadow),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp * responsiveScale)
                        .testTag("catalogSelectedCondition"))
                view.conditionSourceDisclosure?.let { disclosure ->
                    Text(disclosure, color = Color(0xFFECF3F8),
                        fontSize = 12.sp * responsiveScale, textAlign = TextAlign.Center,
                        style = TextStyle(shadow = readoutShadow),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp * responsiveScale)
                            .testTag("catalogConditionSourceDisclosure"))
                }
                Spacer(Modifier.height(7.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(view.temperature ?: "—", color = Color.White,
                        fontSize = (if (compactLayout) 78.sp else 102.sp) * responsiveScale,
                        fontWeight = FontWeight.Light,
                        style = TextStyle(shadow = readoutShadow),
                        modifier = Modifier.alignByBaseline().testTag("forecastTemperature"))
                    Spacer(Modifier.width(18.dp * responsiveScale))
                    Text("≈", color = Color(0xFF59DCEF),
                        fontSize = (if (compactLayout) 34.sp else 42.sp) * responsiveScale,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.alignByBaseline())
                    Spacer(Modifier.width(6.dp * responsiveScale))
                    Text(view.apparentTemperature ?: "—", color = Color.White,
                        fontSize = (if (compactLayout) 29.sp else 35.sp) * responsiveScale,
                        modifier = Modifier.alignByBaseline().testTag("catalogApparentTemperature")
                            .semantics { contentDescription = "Apparent temperature ${view.apparentTemperature ?: "unavailable"}" })
                }
            }
            Spacer(Modifier.height(20.dp))
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp * responsiveScale),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).semantics(mergeDescendants = true) {
                            contentDescription = "Precipitation chance ${view.precipitationChance ?: "unavailable"}"
                        },
                    ) {
                        Box(Modifier.size(32.dp * responsiveScale), contentAlignment = Alignment.Center) {
                            Text("☂", color = Color(0xFF59DCEF), fontSize = 28.sp * responsiveScale)
                        }
                        Spacer(Modifier.height(7.dp))
                        Text("Precip", color = Color(0xFFECF3F8), fontSize = 13.sp * responsiveScale)
                        Text(view.precipitationChance ?: "—", color = Color.White,
                            fontSize = 20.sp * responsiveScale, fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.testTag("catalogPrecipitationChance"))
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
                        Box(Modifier.size(32.dp * responsiveScale), contentAlignment = Alignment.Center) {
                            CatalogWindDirectionIcon(view.windDirectionDegrees, responsiveScale)
                        }
                        Spacer(Modifier.height(7.dp))
                        Text("Wind", color = Color(0xFFECF3F8), fontSize = 13.sp * responsiveScale)
                        Text(view.wind ?: "—", color = Color.White,
                            fontSize = 20.sp * responsiveScale, fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.testTag("catalogWind"))
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f).semantics(mergeDescendants = true) {
                            contentDescription = "Humidity ${view.humidity ?: "unavailable"}"
                        },
                    ) {
                        Box(Modifier.size(32.dp * responsiveScale), contentAlignment = Alignment.Center) {
                            CatalogHumidityIcon(responsiveScale)
                        }
                        Spacer(Modifier.height(7.dp))
                        Text("Humidity", color = Color(0xFFECF3F8), fontSize = 13.sp * responsiveScale)
                        Text(view.humidity ?: "—", color = Color.White,
                            fontSize = 20.sp * responsiveScale, fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.testTag("catalogHumidity"))
                    }
                }
            }
        }
        Column(Modifier.fillMaxWidth().align(Alignment.BottomCenter).navigationBarsPadding()) {
            Spacer(Modifier.height(19.dp * responsiveScale))
            CatalogForecastTimeline(
                view = view,
                precision = precision,
                onSelect = { onSelectInstant(it, view.generation) },
                onSetPrecision = { precision = it },
                onDailyDoubleTap = {
                    onSelectInstant(it, view.generation)
                    precision = true
                },
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
private fun CatalogLocationPickerIcon(scale: Float) {
    Canvas(Modifier.size(22.dp * scale)) {
        val color = Color.White.copy(alpha = 0.9f)
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val pin = Path().apply {
            moveTo(size.width * .5f, size.height * .92f)
            cubicTo(size.width * .4f, size.height * .74f,
                size.width * .12f, size.height * .48f, size.width * .12f, size.height * .33f)
            cubicTo(size.width * .12f, size.height * .08f,
                size.width * .88f, size.height * .08f, size.width * .88f, size.height * .33f)
            cubicTo(size.width * .88f, size.height * .48f,
                size.width * .6f, size.height * .74f, size.width * .5f, size.height * .92f)
        }
        drawPath(pin, color, style = stroke)
        drawCircle(color, radius = size.minDimension * .12f, center = Offset(size.width / 2f, size.height * .33f), style = stroke)
    }
}

@Composable
private fun CatalogWindDirectionIcon(bearingDegrees: Double?, scale: Float) {
    if (bearingDegrees == null) {
        Text("≋", color = Color(0xFF59DCEF), fontSize = 28.sp * scale)
    } else {
        Canvas(Modifier.size(28.dp * scale)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            rotate((bearingDegrees + 180.0).toFloat(), center) {
                val arrow = Path().apply {
                    moveTo(center.x, size.height * .08f)
                    lineTo(size.width * .79f, size.height * .42f)
                    lineTo(size.width * .59f, size.height * .42f)
                    lineTo(size.width * .59f, size.height * .92f)
                    lineTo(size.width * .41f, size.height * .92f)
                    lineTo(size.width * .41f, size.height * .42f)
                    lineTo(size.width * .21f, size.height * .42f)
                    close()
                }
                drawPath(arrow, Color(0xFF59DCEF))
            }
        }
    }
}

@Composable
private fun CatalogHumidityIcon(scale: Float) {
    Canvas(Modifier.size(29.dp * scale)) {
        val centerX = size.width / 2f
        val path = Path().apply {
            moveTo(centerX, size.height * .08f)
            cubicTo(size.width * .38f, size.height * .36f, size.width * .15f, size.height * .55f,
                size.width * .2f, size.height * .7f)
            cubicTo(size.width * .28f, size.height * 1.02f, size.width * .72f, size.height * 1.02f,
                size.width * .8f, size.height * .7f)
            cubicTo(size.width * .85f, size.height * .55f, size.width * .62f, size.height * .36f,
                centerX, size.height * .08f)
            close()
        }
        drawPath(path, Color(0xFF59DCEF), style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
private fun CatalogForecastTimeline(
    view: CatalogTimelineView,
    precision: Boolean,
    onSelect: (Instant) -> Unit,
    onSetPrecision: (Boolean) -> Unit,
    onDailyDoubleTap: (Instant) -> Unit,
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
    val expandedHourPx = with(density) { 48.dp.toPx().toDouble() }
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val selected by rememberUpdatedState(view.selectedInstant)
    val latestPrecision by rememberUpdatedState(precision)
    val latestAnimation by rememberUpdatedState(animation)
    val latestSelect by rememberUpdatedState(onSelect)
    val latestSetPrecision by rememberUpdatedState(onSetPrecision)
    val latestDailyDoubleTap by rememberUpdatedState(onDailyDoubleTap)
    val latestBrowse by rememberUpdatedState(onBrowse)
    fun pixelsPerSecond(zoom: Float) =
        (compactDayPx / 24.0 + (expandedHourPx - compactDayPx / 24.0) * zoom) / 3600.0
    Canvas(
        modifier.fillMaxWidth().height(148.dp).testTag("catalogTimeline")
            .semantics {
                val factTimeDescription = when {
                    view.conditionSourceDisclosure != null && view.sourceTimeDisclosure != null ->
                        "${view.conditionSourceDisclosure}; ${view.sourceTimeDisclosure}"
                    view.conditionSourceDisclosure != null -> view.conditionSourceDisclosure
                    view.sourceTimeDisclosure != null -> view.sourceTimeDisclosure
                    view.factTimeLabel != null -> "Fact sample ${view.factTimeLabel}"
                    else -> "No hourly fact for selected hour"
                }
                contentDescription = "${view.meaning} timeline. Selected ${view.selectedTimeLabel}. " +
                    "${view.temperature ?: "Temperature unavailable"}; ${view.condition ?: "condition unavailable"}. " +
                    "$factTimeDescription. Activate to browse."
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
                val doubleTapDetector = TimelineDoubleTapDetector()
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    var previousX = down.position.x
                    var gestureSelection = selected
                    var dragging = false
                    var hourly = latestPrecision
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull { it.id == down.id }
                        if (change == null) {
                            doubleTapDetector.clear()
                            break
                        } else if (!change.pressed) {
                            if (!dragging) {
                                if (doubleTapDetector.registerTap(
                                        down.position.x, change.uptimeMillis,
                                        viewConfiguration.touchSlop,
                                    )
                                ) {
                                    if (latestPrecision) {
                                        latestSetPrecision(false)
                                    } else {
                                        val logicalX = if (rtl) size.width - down.position.x
                                            else down.position.x
                                        val touched = InstantTimeAxis.instantAtXUnbounded(
                                            logicalX.toDouble(), selected, size.width / 2.0,
                                            pixelsPerSecond(latestAnimation),
                                        )
                                        val touchedDate = touched.atZone(view.zone).toLocalDate()
                                        val requested = touchedDate.atTime(7, 0).atZone(view.zone).toInstant()
                                        val inCatalog = view.days.any { it.date == touchedDate } &&
                                            requested >= view.interval.startInclusive &&
                                            requested <= view.interval.endInclusive
                                        val target = if (inCatalog) requested else view.days.asSequence()
                                            .map { it.date.atTime(7, 0).atZone(view.zone).toInstant() }
                                            .first { it >= view.interval.startInclusive &&
                                                it <= view.interval.endInclusive }
                                        latestDailyDoubleTap(target)
                                    }
                                }
                            } else {
                                doubleTapDetector.clear()
                                if (!hourly) {
                                    val targetDate = gestureSelection.atZone(view.zone).toLocalDate()
                                    latestSelect(InstantTimeAxis.dailyOverviewAnchor(targetDate, view.zone))
                                }
                            }
                            break
                        }
                        val x = change.position.x
                        val dx = x - previousX
                        previousX = x
                        if (abs(x - down.position.x) > viewConfiguration.touchSlop) {
                            dragging = true
                            doubleTapDetector.clear()
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
        val weekdayLabelProgress = (animation / .5f).coerceIn(0f, 1f)
        val weekdayLabelY = dateY - 12.dp.toPx() * weekdayLabelProgress
        val selectedLocalDate = view.selectedInstant.atZone(view.zone).toLocalDate()
        val hourlyWeekdayLabelVisible = animation > .5f
        val selectedWeekday = selectedLocalDate.dayOfWeek.name.take(3)
        val focusedWeekdayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 20.dp.toPx()
            isFakeBoldText = true
        }
        val focusedWeekdayWidth = focusedWeekdayPaint.measureText(selectedWeekday)
        val focusedWeekdayLeft = playhead.toFloat() - focusedWeekdayWidth / 2f
        val focusedWeekdayRight = playhead.toFloat() + focusedWeekdayWidth / 2f
        val dailyIconY = 53.dp.toPx()
        val railY = 73.dp.toPx()
        val scale = pixelsPerSecond(animation)
        val stroke = 1.dp.toPx()
        val hourLabelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.argb((225 * animation).roundToInt(), 238, 247, 255)
            textSize = 18.dp.toPx()
            textAlign = Paint.Align.CENTER
        }
        val hourLabelBounds = Rect()
        val hourLabelTop = railY + 11.dp.toPx()
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
            if (startX in -60f..(w + 60f)) {
                val selectedDay = selectedLocalDate == day.date
                val dayLabel = day.date.dayOfWeek.name.take(3)
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.argb(if (selectedDay) 245 else 175, 244, 250, 255)
                    textAlign = Paint.Align.CENTER
                    textSize = (if (selectedDay) 20.dp else 12.dp).toPx()
                    isFakeBoldText = selectedDay
                }
                val labelWidth = paint.measureText(dayLabel)
                val labelLeft = startX - labelWidth / 2f
                val labelRight = startX + labelWidth / 2f
                val overlapsFocusedWeekday = hourlyWeekdayLabelVisible &&
                    labelLeft < focusedWeekdayRight && labelRight > focusedWeekdayLeft
                if (!(hourlyWeekdayLabelVisible && selectedDay) && !overlapsFocusedWeekday) {
                    drawIntoCanvas { canvas ->
                        canvas.nativeCanvas.drawText(dayLabel, startX, weekdayLabelY, paint)
                    }
                }
            }
            if (noonX in -60f..(w + 60f)) {
                val selectedDay = view.selectedInstant.atZone(view.zone).toLocalDate() == day.date
                if (animation < .5f) {
                    day.weatherSymbolId?.let { symbol ->
                        drawWeatherSymbol(symbol, Offset(noonX, dailyIconY), 31.dp.toPx())
                    }
                    val high = day.high ?: "—"
                    drawIntoCanvas { canvas ->
                        val native = canvas.nativeCanvas
                        val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            color = android.graphics.Color.argb(if (selectedDay) 255 else 220, 244, 250, 255)
                            textAlign = Paint.Align.RIGHT
                            textSize = 9.dp.toPx()
                            isFakeBoldText = true
                        }
                        val highPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            color = if (selectedDay) android.graphics.Color.rgb(255, 210, 126)
                                else android.graphics.Color.rgb(244, 250, 255)
                            textAlign = Paint.Align.LEFT
                            textSize = (if (selectedDay) 29.dp else 25.dp).toPx()
                            isFakeBoldText = true
                        }
                        val marker = "HI"
                        val markerWidth = markerPaint.measureText(marker)
                        val valueWidth = highPaint.measureText(high)
                        val gap = 3.dp.toPx()
                        val left = noonX - (markerWidth + gap + valueWidth) / 2f
                        val baseline = railY + 34.dp.toPx()
                        native.drawText(marker, left + markerWidth, baseline, markerPaint)
                        native.drawText(high, left + markerWidth + gap, baseline, highPaint)
                    }
                }
            }
        }
        if (hourlyWeekdayLabelVisible) {
            drawIntoCanvas { canvas ->
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.argb(245, 244, 250, 255)
                    textAlign = Paint.Align.CENTER
                    textSize = 20.dp.toPx()
                    isFakeBoldText = true
                }
                canvas.nativeCanvas.drawText(selectedWeekday, playhead.toFloat(), weekdayLabelY, paint)
            }
        }
        if (animation > .01f) {
            val selectedHour = view.selectedInstant.atZone(view.zone).withMinute(0).withSecond(0).withNano(0)
            for (offset in -18..18) {
                val tick = selectedHour.plusHours(offset.toLong()).toInstant()
                if (tick < view.interval.startInclusive || tick > view.interval.endInclusive) continue
                val x = xFor(tick)
                val localHour = tick.atZone(view.zone).hour
                val major = localHour % 3 == 0
                if (x in -30f..(w + 30f)) {
                    drawLine(Color.White.copy(alpha = animation * if (major) .82f else .48f),
                        Offset(x, railY - if (major) 9.dp.toPx() else 3.dp.toPx()),
                        Offset(x, railY + if (major) 10.dp.toPx() else 5.dp.toPx()), stroke)
                    if (major) {
                        val hourText = when (localHour) {
                            0 -> "12 AM"
                            12 -> "12 PM"
                            else -> (localHour % 12).toString()
                        }
                        hourLabelPaint.getTextBounds(hourText, 0, hourText.length, hourLabelBounds)
                        val labelWidth = hourLabelPaint.measureText(hourText)
                        val labelBaseline = hourLabelTop - hourLabelBounds.top
                        val labelBottom = labelBaseline + hourLabelBounds.bottom
                        val labelFits = x - stroke / 2f >= 0f && x + stroke / 2f <= w &&
                            x - labelWidth / 2f >= 0f && x + labelWidth / 2f <= w &&
                            hourLabelTop >= 0f && labelBottom <= h
                        if (labelFits) {
                            drawIntoCanvas { canvas ->
                                canvas.nativeCanvas.drawText(hourText, x, labelBaseline, hourLabelPaint)
                            }
                        }
                    }
                }
                val midpoint = tick.plusSeconds(1_800L)
                if (midpoint >= view.interval.startInclusive && midpoint <= view.interval.endInclusive) {
                    val midpointX = xFor(midpoint)
                    if (midpointX in -30f..(w + 30f)) {
                        drawLine(Color.White.copy(alpha = animation * .36f),
                            Offset(midpointX, railY),
                            Offset(midpointX, railY + 6.dp.toPx()), stroke)
                    }
                }
            }
            if (animation > .5f) {
                view.days.asSequence().flatMap { it.hours.asSequence() }.forEach { hour ->
                    val midpoint = hour.instant.plusSeconds(1_800L)
                    if (midpoint < view.interval.startInclusive || midpoint > view.interval.endInclusive) {
                        return@forEach
                    }
                    val midpointX = xFor(midpoint)
                    if (midpointX !in -6f..(w + 6f)) return@forEach
                    hour.weatherSymbolId?.let { symbol ->
                        drawWeatherSymbol(symbol, Offset(midpointX, railY - 20.dp.toPx()), 24.dp.toPx())
                    }
                }
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
