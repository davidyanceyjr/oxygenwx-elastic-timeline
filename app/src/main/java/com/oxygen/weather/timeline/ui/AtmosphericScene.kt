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
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualState
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualStateMapper
import com.oxygen.weather.timeline.presentation.SkyFrame
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
        frame = input.sky,
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
    AtmosphericScene(state = state, frame = frame, localHour = localHour, selectedHour = 0.0, modifier = modifier)
}

@Composable
private fun AtmosphericScene(
    state: SceneVisualState,
    frame: SkyFrame,
    localHour: Double,
    selectedHour: Double,
    modifier: Modifier,
) {
    Canvas(modifier.fillMaxSize()) {
        drawSkyPalette(state)
        drawDiffuseLight(state)
        drawIllustrativeCelestial(state, localHour)
        drawCloudFields(state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f, state.daylight, selectedHour)
        drawLandscape()
        drawRainHints((frame.rainChancePercent / 100.0).toFloat())
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color.Transparent,
                .5f to Color(0x09091828),
                1f to Color(0xDD081626),
            )
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
    val top = lerp(skyColor(nightTop, duskTop, dayTop), wash, cloudiness * .30f)
    val mid = lerp(skyColor(nightMid, duskMid, dayMid), wash, cloudiness * .34f)
    val horizon = lerp(skyColor(nightHorizon, duskHorizon, dayHorizon), wash, cloudiness * .38f)
    drawRect(brush = Brush.verticalGradient(0f to top, .58f to mid, 1f to horizon))
}

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

private fun DrawScope.drawIllustrativeCelestial(state: SceneVisualState, localHour: Double) {
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
    if (sunAlpha > .025f) {
        drawCircle(
            color = Color(0xFFFFE6B9).copy(alpha = .76f * sunAlpha),
            radius = w * .022f,
            center = Offset(x, y),
        )
    } else {
        // A simple crescent is an illustration; no phase or visibility model is implied.
        val moonCenter = Offset(w * .73f, h * .19f)
        val alpha = (1f - daylight).coerceIn(0f, 1f) * (1f - cloudiness * .35f)
        drawCircle(Color(0xFFE4ECF5).copy(alpha = .82f * alpha), w * .024f, moonCenter)
        drawCircle(Color(0xFF263B58).copy(alpha = .96f), w * .020f, Offset(moonCenter.x + w * .011f, moonCenter.y - h * .004f))
    }
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
private fun DrawScope.drawCloudFields(cloudiness: Float, daylight: Double?, selectedHour: Double) {
    if (cloudiness <= 0f) return
    val daylight01 = daylight?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val farColor = lerp(Color(0xFF91A7C1), Color(0xFFE2EDF4), daylight01)
    val midColor = lerp(Color(0xFF7189A5), Color(0xFFD3E1EB), daylight01)
    drawCloudGroup(
        shapes = farClouds,
        cloudiness = cloudiness,
        selectedHour = selectedHour,
        rateDpPerHour = .42f,
        color = farColor,
        alpha = .34f,
        detail = .58f,
    )
    drawCloudGroup(
        shapes = midClouds,
        cloudiness = cloudiness,
        selectedHour = selectedHour,
        rateDpPerHour = .88f,
        color = midColor,
        alpha = .50f,
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
    // 1.5 viewport widths form a repeating field; drawing adjacent copies makes
    // modulo wrapping visually continuous at both horizontal edges.
    val period = size.width * 1.5f
    if (period <= 0f) return
    val hours = selectedHour.takeIf(Double::isFinite) ?: 0.0
    val driftPx = (hours * rateDpPerHour * density).rem(period.toDouble()).toFloat()
    val offset = if (driftPx < 0f) driftPx + period else driftPx
    shapes.forEach { shape ->
        val cloudWidth = size.width * shape.width
        val cloudHeight = size.height * shape.height
        val baseX = shape.x * size.width - offset
        for (copy in -1..1) {
            val x = baseX + copy * period
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
}

private fun DrawScope.drawLandscape() {
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
    drawPath(distant, Color(0x884B7486))
    val near = Path().apply {
        moveTo(0f, h * .88f)
        cubicTo(w * .25f, h * .78f, w * .44f, h * .89f, w * .65f, h * .84f)
        cubicTo(w * .79f, h * .80f, w * .89f, h * .83f, w, h * .86f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }
    drawPath(near, Color(0xFF244A58))
}

private fun DrawScope.drawRainHints(rainChance: Float) {
    // Indicative texture only; probability is not a physical rain-rate measurement.
    val alpha = ((rainChance - .35f) * 1.25f).coerceIn(0f, .52f)
    if (alpha <= 0f) return
    repeat(46) { i ->
        val x = ((i * 77 + 29) % 347) / 347f * size.width
        val y = ((i * 107 + 41) % 491) / 491f * size.height * .84f
        drawLine(Color(0xFFDFEEFF).copy(alpha=alpha), Offset(x,y),
            Offset(x - 5f, y + 18f), strokeWidth=1.65f)
    }
}
