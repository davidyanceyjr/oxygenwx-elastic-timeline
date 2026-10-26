package com.oxygen.weather.timeline.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.lerp
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.presentation.atmosphere.DepictionKind
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualState
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualStateMapper
import com.oxygen.weather.timeline.presentation.SkyFrame
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SkyCondition
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/** Decorative-only rendering. Legible forecast facts are supplied by UI text above this layer. */
@Composable
fun AtmosphericScene(input: AtmosphereInput, modifier: Modifier = Modifier) {
    val state = remember(input) { SceneVisualStateMapper.map(input) }
    AtmosphericScene(state = state, input = input, modifier = modifier)
}

/** Typed adapter seam; the current renderer keeps receiving the original forecast values. */
@Composable
fun AtmosphericScene(state: SceneVisualState, input: AtmosphereInput, modifier: Modifier = Modifier) {
    AtmosphericScene(
        state = state,
        localHour = input.localHour,
        selectedHour = input.selectedHour,
        modifier = modifier,
    )
}

/** Compatibility entry point for callers that still provide the original forecast frame. */
@Composable
fun AtmosphericScene(frame: SkyFrame, localHour: Double, modifier: Modifier = Modifier) {
    val input = remember(frame, localHour) {
        AtmosphereInput(sky = frame, precisionMode = false, localHour = localHour)
    }
    val state = remember(input) { SceneVisualStateMapper.map(input) }
    AtmosphericScene(state = state, localHour = localHour, selectedHour = 0.0, modifier = modifier)
}

@Composable
private fun AtmosphericScene(
    state: SceneVisualState,
    localHour: Double,
    selectedHour: Double,
    modifier: Modifier,
) {
    Canvas(modifier.fillMaxSize()) {
        drawSkyPalette(state)
        drawDiffuseLight(state)
        // Keep the existing daytime sun cue, but omit a night marker: this scene has no
        // celestial position, phase, or visibility model.
        drawIllustrativeSun(state, localHour)
        drawCloudFields(
            state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f,
            state.daylight,
            selectedHour,
            isStorm = state.hasStormVisual(),
        )
        drawLandscape(state.previewStrengths.haze.toFloat(), sceneHorizonColor(state), state)
        when (state.preview) {
            AtmospherePreview.ILLUSTRATIVE_TURBULENCE,
            AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE ->
                drawPreviewTurbulence(state.previewStrengths.turbulence.toFloat())
            else -> Unit
        }
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color.Transparent,
                .5f to Color(0x09091828),
                1f to Color(0xDD081626),
            )
        )
        // Keep precipitation above the atmospheric wash, while leaving text and the tape clear.
        when (state.depiction) {
            DepictionKind.ILLUSTRATIVE_RAIN -> drawRain(
                state.precipitationChancePercent,
                state.precipitationAmount as? PrecipitationAmount.RainLiquidMm,
            )
            DepictionKind.ILLUSTRATIVE_SNOW, DepictionKind.SYNTHETIC_SNOW_PREVIEW ->
                drawSnow(state.precipitationChancePercent)
            else -> Unit
        }
    }
}

/** Abstract, symmetric air contours in the side sky; no arrow, bearing, or measured direction. */
private fun DrawScope.drawPreviewTurbulence(strength: Float) {
    if (strength <= 0f) return
    val w = size.width
    val h = size.height
    listOf(.335f, .355f, .375f).forEachIndexed { index, yFraction ->
        val amplitude = (.008f + index * .0015f) * strength
        val path = Path().apply {
            moveTo(w * .055f, h * yFraction)
            cubicTo(
                w * .12f, h * (yFraction - amplitude),
                w * .205f, h * (yFraction + amplitude),
                w * .29f, h * yFraction,
            )
            moveTo(w * .71f, h * yFraction)
            cubicTo(
                w * .795f, h * (yFraction + amplitude),
                w * .88f, h * (yFraction - amplitude),
                w * .945f, h * yFraction,
            )
        }
        val alpha = (.17f - index * .035f) * strength
        drawPath(
            path,
            Color(0xFFDDECF2).copy(alpha = alpha),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = (1.1f - index * .12f) * density),
        )
    }
}

private fun DrawScope.drawSkyPalette(state: SceneVisualState) {
    val daylight = state.daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val cloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val twilight = (1f - kotlin.math.abs(daylight - .259f) / .259f).coerceIn(0f, 1f)

    val nightTop = Color(0xFF142440)
    val nightMid = Color(0xFF34435D)
    val nightHorizon = Color(0xFF6F6572)
    val dayTop = Color(0xFF3479A8)
    val dayMid = Color(0xFF79B5D4)
    val dayHorizon = Color(0xFFE7B98D)
    val duskTop = Color(0xFF31466C)
    val duskMid = Color(0xFF786D88)
    val duskHorizon = Color(0xFFC58D91)

    fun skyColor(night: Color, twilightColor: Color, day: Color): Color =
        lerp(lerp(night, twilightColor, twilight), day, daylight)

    val cloudWash = when (state.condition) {
        com.oxygen.weather.timeline.data.SkyCondition.CLOUDY,
        com.oxygen.weather.timeline.data.SkyCondition.RAIN,
        com.oxygen.weather.timeline.data.SkyCondition.STORM -> .10f
        else -> .035f
    } * cloudiness
    val wash = Color(0xFF8293A8).copy(alpha = cloudWash)
    val storm = state.hasStormVisual()
    val stormTop = Color(0xFF3B526B)
    val stormMid = Color(0xFF697B8D)
    val stormHorizon = Color(0xFF8A8990)
    val topBase = skyColor(nightTop, duskTop, dayTop)
    val midBase = skyColor(nightMid, duskMid, dayMid)
    val top = lerp(
        lerp(topBase, stormTop, if (storm) .28f else 0f),
        wash,
        cloudiness * .30f,
    )
    val mid = lerp(
        lerp(midBase, stormMid, if (storm) .30f else 0f),
        wash,
        cloudiness * .34f,
    )
    val horizon = if (storm) lerp(sceneHorizonColor(state), stormHorizon, .24f) else sceneHorizonColor(state)
    drawRect(brush = Brush.verticalGradient(0f to top, .58f to mid, 1f to horizon))
}

private fun sceneHorizonColor(state: SceneVisualState): Color {
    val daylight = state.daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val cloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val twilight = (1f - kotlin.math.abs(daylight - .259f) / .259f).coerceIn(0f, 1f)
    val night = Color(0xFF6F6572)
    val dusk = Color(0xFFC58D91)
    val day = Color(0xFFE7B98D)
    val cloudWash = when (state.condition) {
        com.oxygen.weather.timeline.data.SkyCondition.CLOUDY,
        com.oxygen.weather.timeline.data.SkyCondition.RAIN,
        com.oxygen.weather.timeline.data.SkyCondition.STORM -> .10f
        else -> .035f
    } * cloudiness
    val wash = Color(0xFF8293A8).copy(alpha = cloudWash)
    val base = lerp(lerp(night, dusk, twilight), day, daylight).let {
        lerp(it, wash, cloudiness * .38f)
    }
    return if (state.hasStormVisual()) lerp(base, Color(0xFF8A8990), .24f) else base
}

/** Storm styling follows the typed condition even when precipitation has its own depiction. */
private fun SceneVisualState.hasStormVisual(): Boolean =
    condition == SkyCondition.STORM || depiction == DepictionKind.STORM

private fun DrawScope.drawDiffuseLight(state: SceneVisualState) {
    val daylight = state.daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val cloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    if (daylight <= .01f) return
    val w = size.width
    val h = size.height
    val strength = daylight * (1f - .65f * cloudiness)
    val center = Offset(w * .5f, h * .34f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x88FFD9A0).copy(alpha = .42f * strength), Color.Transparent),
            center = center, radius = w * .48f,
        ),
        radius = w * .48f, center = center,
    )
}

private fun DrawScope.drawIllustrativeSun(state: SceneVisualState, localHour: Double) {
    val daylight = state.daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val cloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val w = size.width
    val h = size.height
    val hour = localHour.takeIf(Double::isFinite)?.rem(24.0)?.let { if (it < 0) it + 24 else it } ?: 12.0
    val arc = ((hour - 6.0) / 12.0).coerceIn(0.0, 1.0)
    val x = (w * (.12 + .76 * arc)).toFloat()
    // Keep the small marker in the open upper sky, above the forecast text block.
    val y = h * (.19f - .045f * sin(arc * PI).toFloat())
    val sunAlpha = daylight * (1f - .55f * cloudiness)
    if (sunAlpha > .025f) drawCircle(
        color = Color(0xFFFFE6B9).copy(alpha = .76f * sunAlpha),
        radius = w * .022f,
        center = Offset(x, y),
    )
}

private data class CloudShape(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val fullness: Float,
)

private val farClouds = listOf(
    CloudShape(.02f, .150f, .24f, .038f, .42f),
    CloudShape(.76f, .160f, .23f, .040f, .48f),
    CloudShape(1.12f, .148f, .25f, .039f, .46f),
)

private val midClouds = listOf(
    CloudShape(.10f, .175f, .25f, .048f, .56f),
    CloudShape(.79f, .180f, .26f, .051f, .62f),
    CloudShape(1.22f, .168f, .24f, .047f, .60f),
)

/** Stable decorative groups. Their phase is a pure function of selected fixture time. */
private fun DrawScope.drawCloudFields(
    cloudiness: Float,
    daylight: Double?,
    selectedHour: Double,
    isStorm: Boolean,
) {
    if (cloudiness <= 0f) return
    val daylight01 = daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val farColor = if (isStorm) Color(0xFF8B98A8) else lerp(Color(0xFF91A7C1), Color(0xFFE2EDF4), daylight01)
    val midColor = if (isStorm) Color(0xFF596A7E) else lerp(Color(0xFF7189A5), Color(0xFFD3E1EB), daylight01)
    drawCloudGroup(
        shapes = farClouds,
        cloudiness = cloudiness,
        selectedHour = selectedHour,
        rateDpPerHour = 2.0f,
        color = farColor,
        alpha = if (isStorm) .48f else .34f,
        detail = .58f,
    )
    drawCloudGroup(
        shapes = midClouds,
        cloudiness = cloudiness,
        selectedHour = selectedHour,
        rateDpPerHour = 3.5f,
        color = midColor,
        alpha = if (isStorm) .62f else .50f,
        detail = .90f,
    )
}

private fun DrawScope.drawCloudGroup(
    shapes: List<CloudShape>,
    cloudiness: Float,
    selectedHour: Double,
    rateDpPerHour: Float,
    color: Color,
    alpha: Float,
    detail: Float,
) {
    if (size.width <= 0f) return
    val hours = selectedHour.takeIf(Double::isFinite) ?: 0.0
    shapes.forEach { shape ->
        val cloudWidth = size.width * shape.width
        val cloudHeight = size.height * shape.height
        val edgeInset = size.width * .025f
        val minX = edgeInset
        val maxX = (size.width - cloudWidth - edgeInset).coerceAtLeast(minX)
        val travel = maxX - minX
        val baseFraction = shape.x.rem(1f).coerceIn(0f, 1f)
        val basePhase = kotlin.math.asin(baseFraction * 2f - 1f)
        val phase = if (travel > 0f) {
            basePhase + (hours * rateDpPerHour * density / (travel * .5f))
        } else basePhase.toDouble()
        // A bounded sinusoidal phase keeps clouds wholly inside the viewport
        // with smooth, deterministic movement as selected forecast time changes.
        val positionFraction = (.5 + .5 * sin(phase)).toFloat()
        val x = minX + travel * positionFraction
        val y = shape.y * size.height
        val shapeAlpha = (cloudiness * alpha * shape.fullness).coerceIn(0f, .62f)
        val tint = color.copy(alpha = shapeAlpha)
        drawOval(
            color = tint,
            topLeft = Offset(x, y + cloudHeight * .36f),
            size = Size(cloudWidth, cloudHeight * .62f),
        )
        drawCircle(
            color = tint.copy(alpha = shapeAlpha * detail),
            radius = cloudHeight * .45f,
            center = Offset(x + cloudWidth * .30f, y + cloudHeight * .48f),
        )
        drawCircle(
            color = tint.copy(alpha = shapeAlpha * detail),
            radius = cloudHeight * .58f,
            center = Offset(x + cloudWidth * .53f, y + cloudHeight * .34f),
        )
        drawCircle(
            color = tint.copy(alpha = shapeAlpha * detail),
            radius = cloudHeight * .39f,
            center = Offset(x + cloudWidth * .77f, y + cloudHeight * .49f),
        )
    }
}

private fun DrawScope.drawLandscape(hazeStrength: Float, horizon: Color, state: SceneVisualState) {
    val w = size.width
    val h = size.height
    val distant = Path().apply {
        moveTo(0f, h * .82f)
        lineTo(w * .12f, h * .66f)
        lineTo(w * .23f, h * .77f)
        lineTo(w * .43f, h * .59f)
        lineTo(w * .68f, h * .81f)
        lineTo(w * .86f, h * .69f)
        lineTo(w, h * .77f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }
    val isNight = state.daylight == 0.0
    val isStorm = state.hasStormVisual()
    val ridgeBase = when {
        isStorm && isNight -> Color(0xFF46566A)
        isStorm -> Color(0xFF557080)
        isNight -> Color(0xFF3E667D)
        else -> Color(0xFF4B7486)
    }
    val ridgeHaze = (hazeStrength.coerceIn(0f, 1f) * .30f).coerceIn(0f, .30f)
    drawPath(distant, lerp(ridgeBase, horizon, ridgeHaze).copy(alpha = 0x88 / 255f))
    val near = Path().apply {
        moveTo(0f, h * .88f)
        cubicTo(w * .25f, h * .78f, w * .44f, h * .89f, w * .65f, h * .84f)
        cubicTo(w * .79f, h * .80f, w * .89f, h * .83f, w, h * .86f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }
    val nearColor = when {
        isStorm && isNight -> Color(0xFF1C3448)
        isStorm -> Color(0xFF244151)
        isNight -> Color(0xFF1E4355)
        else -> Color(0xFF244A58)
    }
    drawPath(near, nearColor)
}

private fun DrawScope.drawRain(chancePercent: Double, amount: PrecipitationAmount.RainLiquidMm?) {
    val coverage = precipitationCoverage(chancePercent)
    if (coverage <= 0f) return
    // Chance selects ambient coverage. Only an explicit liquid amount changes illustrative
    // streak length/weight; missing totals use a stable middle depiction without a data claim.
    val amountFactor = amount?.value?.div(3.0)?.toFloat()?.coerceIn(0f, 1f) ?: .2f
    val streakLength = (15f + amountFactor * 38f) * density
    val stroke = (1.5f + amountFactor * 1.4f) * density
    val alpha = .4f + amountFactor * .14f
    val count = (MAX_PRECIPITATION_PARTICLES * coverage).toInt().coerceAtLeast(12)
    repeat(count) { i ->
        val xSeed = (i * 73 + i * i * 19 + 31) % 997
        val ySeed = (i * 193 + i * i * 47 + 17) % 991
        val x = xSeed / 997f * size.width
        val y = ySeed / 991f * size.height * .82f
        val endX = x - streakLength * .30f
        val endY = y + streakLength
        if (inPrecipitationQuietZone(x, y) || inPrecipitationQuietZone(endX, endY)) return@repeat
        drawLine(
            Color(0xFFE4F3FF).copy(alpha = alpha), Offset(x, y), Offset(endX, endY),
            strokeWidth = stroke,
        )
    }
}

private fun DrawScope.drawSnow(chancePercent: Double) {
    val coverage = precipitationCoverage(chancePercent)
    val count = (MAX_SNOW_PARTICLES * coverage).toInt().coerceAtLeast(28)
    repeat(count) { i ->
        val xSeed = (i * 83 + i * i * 29 + 19) % 991
        val ySeed = (i * 127 + i * i * 43 + 53) % 997
        val x = xSeed / 991f * size.width
        val y = ySeed / 997f * size.height * .82f
        if (inPrecipitationQuietZone(x, y)) return@repeat
        val radius = (2.3f + (i % 3) * .45f) * density
        drawCircle(Color(0xFFF0F7FF).copy(alpha = .82f), radius, Offset(x, y))
    }
}

private fun DrawScope.inPrecipitationQuietZone(x: Float, y: Float): Boolean {
    val headerText = y <= size.height * .21f
    val centralForecastText = y in (size.height * .28f)..(size.height * .68f) &&
        x in (size.width * .14f)..(size.width * .86f)
    val lowerFactLabels = y in (size.height * .73f)..(size.height * .87f) &&
        (x < size.width * .34f || x > size.width * .66f)
    val timeline = y >= size.height * .87f
    return headerText || centralForecastText || lowerFactLabels || timeline
}

private fun precipitationCoverage(chancePercent: Double): Float = when {
    !chancePercent.isFinite() || chancePercent <= 0.0 -> 0f
    chancePercent <= 20.0 -> .28f
    chancePercent <= 40.0 -> .44f
    chancePercent <= 60.0 -> .60f
    chancePercent <= 80.0 -> .78f
    else -> .94f
}

private const val MAX_PRECIPITATION_PARTICLES = 150
private const val MAX_SNOW_PARTICLES = 180
