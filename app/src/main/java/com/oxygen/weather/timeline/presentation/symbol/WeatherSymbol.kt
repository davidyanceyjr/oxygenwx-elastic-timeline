package com.oxygen.weather.timeline.presentation.symbol

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/** Stable catalog identity. Artwork is shared only where the semantic IDs still remain distinct. */
enum class WeatherSymbolId(val label: String) {
    SUNNY("Sunny"),
    CLEAR_NIGHT("Clear night"),
    MOSTLY_SUNNY("Mostly sunny"),
    MOSTLY_CLEAR_NIGHT("Mostly clear night"),
    PARTLY_SUNNY_CLOUDY("Partly sunny and cloudy"),
    PARTLY_CLOUDY_NIGHT("Partly cloudy night"),
    MOSTLY_CLOUDY("Mostly cloudy"),
    CLOUDY_OVERCAST("Cloudy or overcast"),
}

enum class WeatherSymbolArtwork {
    SUN, MOON, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT, PARTLY_SUNNY_CLOUDY,
    PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, CLOUDY_OVERCAST,
}

data class WeatherSymbolDefinition(val id: WeatherSymbolId, val artwork: WeatherSymbolArtwork)

/** Explicit semantic-to-art mapping, kept independent from provider codes and forecast values. */
object WeatherSymbolCatalog {
    val definitions: List<WeatherSymbolDefinition> = listOf(
        WeatherSymbolDefinition(WeatherSymbolId.SUNNY, WeatherSymbolArtwork.SUN),
        WeatherSymbolDefinition(WeatherSymbolId.CLEAR_NIGHT, WeatherSymbolArtwork.MOON),
        WeatherSymbolDefinition(WeatherSymbolId.MOSTLY_SUNNY, WeatherSymbolArtwork.MOSTLY_SUNNY),
        WeatherSymbolDefinition(WeatherSymbolId.MOSTLY_CLEAR_NIGHT, WeatherSymbolArtwork.MOSTLY_CLEAR_NIGHT),
        WeatherSymbolDefinition(WeatherSymbolId.PARTLY_SUNNY_CLOUDY, WeatherSymbolArtwork.PARTLY_SUNNY_CLOUDY),
        WeatherSymbolDefinition(WeatherSymbolId.PARTLY_CLOUDY_NIGHT, WeatherSymbolArtwork.PARTLY_CLOUDY_NIGHT),
        WeatherSymbolDefinition(WeatherSymbolId.MOSTLY_CLOUDY, WeatherSymbolArtwork.MOSTLY_CLOUDY),
        WeatherSymbolDefinition(WeatherSymbolId.CLOUDY_OVERCAST, WeatherSymbolArtwork.CLOUDY_OVERCAST),
    )
    private val byId = definitions.associateBy(WeatherSymbolDefinition::id)
    fun resolve(id: WeatherSymbolId): WeatherSymbolDefinition = requireNotNull(byId[id])
}

/** Static, size-independent Sky-row symbol renderer. Layout and placement belong to the caller. */
@Composable
fun WeatherSymbolIcon(
    symbol: WeatherSymbolId,
    modifier: Modifier = Modifier,
    contentDescription: String? = symbol.label,
) {
    val accessibleModifier = if (contentDescription == null) modifier else modifier.semantics {
        this.contentDescription = contentDescription
    }
    Canvas(accessibleModifier) {
        val unit = min(size.width, size.height) / 100f
        val left = (size.width - 100f * unit) / 2f
        val top = (size.height - 100f * unit) / 2f
        translate(left, top) {
            scale(unit, unit, pivot = Offset.Zero) {
                when (WeatherSymbolCatalog.resolve(symbol).artwork) {
                    WeatherSymbolArtwork.SUN -> drawSun(50f, 48f, 22f)
                    WeatherSymbolArtwork.MOON -> {
                        drawStars(listOf(Offset(20f, 29f), Offset(43f, 15f), Offset(74f, 34f), Offset(27f, 59f)))
                        drawMoon(53f, 49f, 25f)
                    }
                    WeatherSymbolArtwork.MOSTLY_SUNNY -> {
                        drawSun(35f, 37f, 19f)
                        drawCloud(28f, 43f, 62f, 40f)
                    }
                    WeatherSymbolArtwork.MOSTLY_CLEAR_NIGHT -> {
                        drawStars(listOf(Offset(17f, 26f), Offset(76f, 24f), Offset(83f, 51f)))
                        drawMoon(37f, 37f, 21f)
                        drawCloud(34f, 54f, 55f, 31f)
                    }
                    WeatherSymbolArtwork.PARTLY_SUNNY_CLOUDY -> {
                        drawSun(32f, 35f, 22f)
                        drawCloud(25f, 44f, 70f, 43f)
                        drawCloud(57f, 59f, 31f, 23f, foreground = true)
                    }
                    WeatherSymbolArtwork.PARTLY_CLOUDY_NIGHT -> {
                        drawStars(listOf(Offset(15f, 26f), Offset(78f, 24f), Offset(84f, 51f)))
                        drawMoon(34f, 35f, 23f)
                        drawCloud(23f, 46f, 70f, 42f)
                    }
                    WeatherSymbolArtwork.MOSTLY_CLOUDY -> {
                        drawCloud(8f, 42f, 67f, 40f, foreground = false)
                        drawCloud(31f, 53f, 63f, 38f, foreground = true)
                    }
                    WeatherSymbolArtwork.CLOUDY_OVERCAST -> {
                        drawCloud(8f, 42f, 64f, 38f, foreground = false)
                        drawCloud(30f, 45f, 64f, 39f, foreground = false)
                        drawCloud(18f, 57f, 68f, 37f, foreground = true)
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawSun(cx: Float, cy: Float, radius: Float) {
    val rayInner = radius + 6f
    val rayOuter = radius + 14f
    for (index in 0 until 12) {
        val angle = (index * 30.0 - 90.0) * Math.PI / 180.0
        val start = Offset(cx + cos(angle).toFloat() * rayInner, cy + sin(angle).toFloat() * rayInner)
        val end = Offset(cx + cos(angle).toFloat() * rayOuter, cy + sin(angle).toFloat() * rayOuter)
        drawLine(
            color = Color(0xFFFFBD43), start = start, end = end,
            strokeWidth = if (index % 2 == 0) 3.8f else 2.7f, cap = StrokeCap.Round,
        )
    }
    drawCircle(Brush.radialGradient(listOf(Color(0xFFFFE99A), Color(0xFFFFB735), Color(0xFFF58C28)),
        center = Offset(cx - radius * .2f, cy - radius * .25f), radius = radius * 1.7f), radius, Offset(cx, cy))
    drawCircle(Color(0xFFFFF2BD).copy(alpha = .8f), radius * .72f, Offset(cx - radius * .18f, cy - radius * .2f), style = Stroke(1.2f))
}

private fun DrawScope.drawMoon(cx: Float, cy: Float, radius: Float) {
    val crescent = Path().apply {
        moveTo(cx + radius * .82f, cy - radius * .5f)
        cubicTo(cx + radius * .18f, cy - radius * 1.03f, cx - radius * .62f, cy - radius * .38f, cx - radius * .36f, cy + radius * .48f)
        cubicTo(cx - radius * .06f, cy + radius * 1.12f, cx + radius * .72f, cy + radius * .82f, cx + radius * .92f, cy + radius * .18f)
        cubicTo(cx + radius * .46f, cy + radius * .45f, cx + radius * .15f, cy + radius * .05f, cx + radius * .21f, cy - radius * .4f)
        cubicTo(cx + radius * .29f, cy - radius * .72f, cx + radius * .52f, cy - radius * .71f, cx + radius * .82f, cy - radius * .5f)
        close()
    }
    drawPath(crescent, Brush.linearGradient(listOf(Color(0xFFFFF4C6), Color(0xFFFFC957)), Offset(cx, cy - radius), Offset(cx, cy + radius)))
    drawPath(crescent, Color(0xFFFFF8DB).copy(alpha = .8f), style = Stroke(1.1f))
}

private fun DrawScope.drawStars(points: List<Offset>) {
    points.forEachIndexed { index, point ->
        val r = if (index % 2 == 0) 2.2f else 1.5f
        drawLine(Color(0xFFD9F3FF), Offset(point.x - r * 1.7f, point.y), Offset(point.x + r * 1.7f, point.y), 1.2f, StrokeCap.Round)
        drawLine(Color(0xFFD9F3FF), Offset(point.x, point.y - r * 1.7f), Offset(point.x, point.y + r * 1.7f), 1.2f, StrokeCap.Round)
        drawCircle(Color.White, r * .7f, point)
    }
}

private fun DrawScope.drawCloud(x: Float, y: Float, width: Float, height: Float, foreground: Boolean = false) {
    val p = Path().apply {
        moveTo(x + width * .08f, y + height * .82f)
        cubicTo(x - width * .02f, y + height * .78f, x - width * .01f, y + height * .58f, x + width * .14f, y + height * .52f)
        cubicTo(x + width * .16f, y + height * .22f, x + width * .42f, y + height * .14f, x + width * .53f, y + height * .42f)
        cubicTo(x + width * .68f, y + height * .12f, x + width * .98f, y + height * .29f, x + width * .88f, y + height * .61f)
        cubicTo(x + width * 1.08f, y + height * .7f, x + width * .97f, y + height * .91f, x + width * .78f, y + height * .91f)
        lineTo(x + width * .2f, y + height * .91f)
        cubicTo(x + width * .1f, y + height * .91f, x + width * .08f, y + height * .88f, x + width * .08f, y + height * .82f)
        close()
    }
    val top = if (foreground) Color(0xFFF8FCFF) else Color(0xFFE4F1F8)
    val bottom = if (foreground) Color(0xFF94B2C5) else Color(0xFF6F8CA2)
    drawPath(p, Brush.verticalGradient(listOf(top, Color(0xFFD2E1EA), bottom), y, y + height))
    drawPath(p, Color(0xFFF6FCFF).copy(alpha = .58f), style = Stroke(width = 1.4f))
    drawPath(p, Brush.horizontalGradient(listOf(Color.Transparent, Color.White.copy(alpha = .42f), Color.Transparent), x, x + width), style = Stroke(width = 1.8f, cap = StrokeCap.Round))
}
