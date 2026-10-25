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
@Suppress("UNUSED_PARAMETER")
fun AtmosphericScene(state: SceneVisualState, input: AtmosphereInput, modifier: Modifier = Modifier) {
    AtmosphericScene(frame = input.sky, localHour = input.localHour, modifier = modifier)
}

/** Compatibility renderer retained unchanged for baseline parity. */
@Composable
fun AtmosphericScene(frame: SkyFrame, localHour: Double, modifier: Modifier = Modifier) {
    Canvas(modifier.fillMaxSize()) {
        val cloudy = (frame.cloudPercent / 100.0).toFloat().coerceIn(0f, 1f)
        val daylight = (sin(((localHour - 6.0) / 12.0) * PI)).toFloat().coerceIn(0f, 1f)
        val top = lerp(Color(0xFF122A4D), Color(0xFF448DC0), daylight * (1f - cloudy * .42f))
        val mid = lerp(Color(0xFF314C70), Color(0xFF8FC2DF), daylight * (1f - cloudy * .38f))
        val horizon = lerp(Color(0xFF7E6C75), Color(0xFFF3BD88), daylight * (1f - cloudy * .45f))
        drawRect(brush = Brush.verticalGradient(0f to top, .6f to mid, 1f to horizon))
        drawSunAndGlow(daylight, cloudy, localHour)
        drawCloudBands(cloudy, localHour)
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

private fun DrawScope.drawSunAndGlow(daylight: Float, cloudiness: Float, hour: Double) {
    val w = size.width
    val h = size.height
    val arc = ((hour - 6.0) / 12.0).coerceIn(0.0, 1.0)
    val sx = (w * (.10 + .78 * arc)).toFloat()
    val sy = h * (.48f - .31f * sin(arc * PI).toFloat())
    val strength = daylight * (1f - .65f * cloudiness)
    if (strength > .01f) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xB2FFDC9B).copy(alpha=.40f * strength), Color.Transparent),
                center = Offset(sx, sy), radius = w * .43f,
            ),
            radius = w * .43f, center = Offset(sx, sy),
        )
        drawCircle(color = Color(0xFFFFE4B6).copy(alpha = strength),
            radius = w * .066f, center = Offset(sx, sy))
    } else {
        drawCircle(
            color = Color(0xFFE8F4FF).copy(alpha = .7f * (1f - daylight)),
            radius = w * .033f,
            center = Offset(w * .71f, h * .19f),
        )
    }
}

private fun DrawScope.drawCloudBands(cloudiness: Float, hour: Double) {
    if (cloudiness <= .04f) return
    val w = size.width
    val h = size.height
    val drift = (hour * 2.3).toFloat()
    repeat(7) { i ->
        val x = ((i * .195f + .06f) * w + drift * (i % 3 + 1)) % (w + w * .5f) - w * .25f
        val y = h * (.13f + (i % 4) * .09f)
        val radius = w * (.16f + (i % 3) * .028f)
        val alpha = (cloudiness * (.36f + (i % 3) * .08f)).coerceIn(0f, .75f)
        val color = lerp(Color(0xFFD9E8F2), Color(0xFF718DA7), cloudiness * .6f).copy(alpha=alpha)
        drawOval(color, topLeft = Offset(x - radius, y), size = Size(radius * 2.1f, radius * .58f))
        drawOval(color, topLeft = Offset(x - radius * .55f, y - radius * .13f),
            size = Size(radius * 1.02f, radius * .60f))
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
