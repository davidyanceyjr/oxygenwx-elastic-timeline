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
    DRIZZLE("Drizzle"),
    RAIN("Rain"),
    HEAVY_RAIN("Heavy rain"),
    RAIN_SHOWERS("Rain showers"),
    FREEZING_DRIZZLE("Freezing drizzle"),
    FREEZING_RAIN("Freezing rain"),
    ICE_PELLETS("Ice pellets"),
    HAIL("Hail"),
    FLURRIES_SNOW_GRAINS("Flurries or snow grains"),
    SNOW("Snow"),
    HEAVY_SNOW("Heavy snow"),
    SNOW_SHOWERS("Snow showers"),
    RAIN_AND_SNOW("Rain and snow"),
    WINTRY_MIX("Wintry mix"),
    GRAUPEL("Graupel"),
    ICE_CRYSTALS("Ice crystals"),
    MIST("Mist"),
    FOG("Fog"),
    FREEZING_FOG("Freezing fog"),
    HAZE_SMOKE("Haze or smoke"),
    DUST_SAND_ASH("Dust, sand, or ash"),
    WINDY_GUSTY("Windy or gusty"),
    BLOWING_SNOW("Blowing snow"),
    BLIZZARD("Blizzard"),
}

enum class WeatherSymbolArtwork {
    SUN, MOON, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT, PARTLY_SUNNY_CLOUDY,
    PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, CLOUDY_OVERCAST,
    DRIZZLE, RAIN, HEAVY_RAIN, RAIN_SHOWERS, FREEZING_DRIZZLE,
    FREEZING_RAIN, ICE_PELLETS, HAIL,
    FLURRIES_SNOW_GRAINS, SNOW, HEAVY_SNOW, SNOW_SHOWERS,
    RAIN_AND_SNOW, WINTRY_MIX, GRAUPEL, ICE_CRYSTALS,
    MIST, FOG, FREEZING_FOG, HAZE_SMOKE, DUST_SAND_ASH, WINDY_GUSTY, BLOWING_SNOW, BLIZZARD,
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
        WeatherSymbolDefinition(WeatherSymbolId.DRIZZLE, WeatherSymbolArtwork.DRIZZLE),
        WeatherSymbolDefinition(WeatherSymbolId.RAIN, WeatherSymbolArtwork.RAIN),
        WeatherSymbolDefinition(WeatherSymbolId.HEAVY_RAIN, WeatherSymbolArtwork.HEAVY_RAIN),
        WeatherSymbolDefinition(WeatherSymbolId.RAIN_SHOWERS, WeatherSymbolArtwork.RAIN_SHOWERS),
        WeatherSymbolDefinition(WeatherSymbolId.FREEZING_DRIZZLE, WeatherSymbolArtwork.FREEZING_DRIZZLE),
        WeatherSymbolDefinition(WeatherSymbolId.FREEZING_RAIN, WeatherSymbolArtwork.FREEZING_RAIN),
        WeatherSymbolDefinition(WeatherSymbolId.ICE_PELLETS, WeatherSymbolArtwork.ICE_PELLETS),
        WeatherSymbolDefinition(WeatherSymbolId.HAIL, WeatherSymbolArtwork.HAIL),
        WeatherSymbolDefinition(WeatherSymbolId.FLURRIES_SNOW_GRAINS, WeatherSymbolArtwork.FLURRIES_SNOW_GRAINS),
        WeatherSymbolDefinition(WeatherSymbolId.SNOW, WeatherSymbolArtwork.SNOW),
        WeatherSymbolDefinition(WeatherSymbolId.HEAVY_SNOW, WeatherSymbolArtwork.HEAVY_SNOW),
        WeatherSymbolDefinition(WeatherSymbolId.SNOW_SHOWERS, WeatherSymbolArtwork.SNOW_SHOWERS),
        WeatherSymbolDefinition(WeatherSymbolId.RAIN_AND_SNOW, WeatherSymbolArtwork.RAIN_AND_SNOW),
        WeatherSymbolDefinition(WeatherSymbolId.WINTRY_MIX, WeatherSymbolArtwork.WINTRY_MIX),
        WeatherSymbolDefinition(WeatherSymbolId.GRAUPEL, WeatherSymbolArtwork.GRAUPEL),
        WeatherSymbolDefinition(WeatherSymbolId.ICE_CRYSTALS, WeatherSymbolArtwork.ICE_CRYSTALS),
        WeatherSymbolDefinition(WeatherSymbolId.MIST, WeatherSymbolArtwork.MIST),
        WeatherSymbolDefinition(WeatherSymbolId.FOG, WeatherSymbolArtwork.FOG),
        WeatherSymbolDefinition(WeatherSymbolId.FREEZING_FOG, WeatherSymbolArtwork.FREEZING_FOG),
        WeatherSymbolDefinition(WeatherSymbolId.HAZE_SMOKE, WeatherSymbolArtwork.HAZE_SMOKE),
        WeatherSymbolDefinition(WeatherSymbolId.DUST_SAND_ASH, WeatherSymbolArtwork.DUST_SAND_ASH),
        WeatherSymbolDefinition(WeatherSymbolId.WINDY_GUSTY, WeatherSymbolArtwork.WINDY_GUSTY),
        WeatherSymbolDefinition(WeatherSymbolId.BLOWING_SNOW, WeatherSymbolArtwork.BLOWING_SNOW),
        WeatherSymbolDefinition(WeatherSymbolId.BLIZZARD, WeatherSymbolArtwork.BLIZZARD),
    )
    private val byId = definitions.associateBy(WeatherSymbolDefinition::id)
    fun resolve(id: WeatherSymbolId): WeatherSymbolDefinition = requireNotNull(byId[id])
}

/** Static, size-independent symbol renderer. Layout and placement belong to the caller. */
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
                    WeatherSymbolArtwork.DRIZZLE -> drawPrecipitation(drops = 3, small = true)
                    WeatherSymbolArtwork.RAIN -> drawPrecipitation(drops = 3)
                    WeatherSymbolArtwork.HEAVY_RAIN -> drawPrecipitation(drops = 5, heavy = true)
                    WeatherSymbolArtwork.RAIN_SHOWERS -> drawPrecipitation(drops = 3, sun = true)
                    WeatherSymbolArtwork.FREEZING_DRIZZLE -> drawPrecipitation(drops = 2, small = true, freezing = true)
                    WeatherSymbolArtwork.FREEZING_RAIN -> drawPrecipitation(drops = 2, freezing = true)
                    WeatherSymbolArtwork.ICE_PELLETS -> drawPrecipitation(pellets = true)
                    WeatherSymbolArtwork.HAIL -> drawPrecipitation(hail = true)
                    WeatherSymbolArtwork.FLURRIES_SNOW_GRAINS -> drawFrozenPrecipitation(flurries = true)
                    WeatherSymbolArtwork.SNOW -> drawFrozenPrecipitation(snowflakes = 2)
                    WeatherSymbolArtwork.HEAVY_SNOW -> drawFrozenPrecipitation(snowflakes = 3, heavy = true)
                    WeatherSymbolArtwork.SNOW_SHOWERS -> drawFrozenPrecipitation(snowflakes = 3, sun = true)
                    WeatherSymbolArtwork.RAIN_AND_SNOW -> drawFrozenPrecipitation(rain = 2, snowflakes = 1)
                    WeatherSymbolArtwork.WINTRY_MIX -> drawFrozenPrecipitation(rain = 1, snowflakes = 1, pellets = true)
                    WeatherSymbolArtwork.GRAUPEL -> drawFrozenPrecipitation(graupel = true)
                    WeatherSymbolArtwork.ICE_CRYSTALS -> drawFrozenPrecipitation(crystalsOnly = true)
                    WeatherSymbolArtwork.MIST -> drawVisibility(0)
                    WeatherSymbolArtwork.FOG -> drawVisibility(1)
                    WeatherSymbolArtwork.FREEZING_FOG -> drawVisibility(2)
                    WeatherSymbolArtwork.HAZE_SMOKE -> drawVisibility(3)
                    WeatherSymbolArtwork.DUST_SAND_ASH -> drawVisibility(4)
                    WeatherSymbolArtwork.WINDY_GUSTY -> drawWind(0)
                    WeatherSymbolArtwork.BLOWING_SNOW -> drawWind(1)
                    WeatherSymbolArtwork.BLIZZARD -> drawWind(2)
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

/** Rain + Ice row, composed from the shared cloud and type-specific precipitation. */
private fun DrawScope.drawPrecipitation(
    drops: Int = 0, small: Boolean = false, heavy: Boolean = false,
    sun: Boolean = false, freezing: Boolean = false,
    pellets: Boolean = false, hail: Boolean = false,
) {
    if (sun) drawSun(28f, 26f, 11f)
    drawCloud(17f, 22f, 68f, 42f)
    if (drops > 0) {
        val xs = when (drops) {
            5 -> listOf(25f, 37f, 49f, 61f, 73f)
            2 -> listOf(35f, 58f)
            else -> listOf(31f, 50f, 69f)
        }
        xs.forEachIndexed { index, x ->
            val y = if (index % 2 == 0) 74f else 79f
            val length = if (small) 4f else if (heavy) 13f else 9f
            drawLine(Color(0xFF55D8FF), Offset(x + 2f, y - length), Offset(x - 2f, y),
                strokeWidth = if (small) 2.2f else if (heavy) 4.3f else 3.3f,
                cap = StrokeCap.Round)
        }
    }
    if (freezing) {
        val center = Offset(78f, 77f)
        drawLine(Color.White, Offset(center.x - 7f, center.y), Offset(center.x + 7f, center.y), 2f)
        drawLine(Color.White, Offset(center.x, center.y - 7f), Offset(center.x, center.y + 7f), 2f)
        drawLine(Color.White, Offset(center.x - 5f, center.y - 5f), Offset(center.x + 5f, center.y + 5f), 1.7f)
        drawLine(Color.White, Offset(center.x - 5f, center.y + 5f), Offset(center.x + 5f, center.y - 5f), 1.7f)
    }
    if (pellets) {
        listOf(Offset(31f, 69f), Offset(49f, 76f), Offset(68f, 69f)).forEach {
            val diamond = Path().apply {
                moveTo(it.x, it.y - 4f); lineTo(it.x + 4f, it.y)
                lineTo(it.x, it.y + 4f); lineTo(it.x - 4f, it.y); close()
            }
            drawPath(diamond, Color(0xFFE7F5FF))
        }
    }
    if (hail) {
        listOf(Offset(31f, 70f), Offset(50f, 77f), Offset(69f, 70f)).forEach {
            drawCircle(Color(0xFF48C6F4), 6f, it)
            drawCircle(Color(0xFFE5F8FF), 2.5f, Offset(it.x - 1.5f, it.y - 2f))
        }
    }
}

/** Snow + mixed row: flakes, rain strokes, soft graupel, and bare crystals. */
private fun DrawScope.drawFrozenPrecipitation(
    flurries: Boolean = false, snowflakes: Int = 0, heavy: Boolean = false,
    sun: Boolean = false, rain: Int = 0, pellets: Boolean = false,
    graupel: Boolean = false, crystalsOnly: Boolean = false,
) {
    if (!crystalsOnly) {
        if (sun) drawSun(28f, 26f, 11f)
        drawCloud(17f, 22f, 68f, 42f)
    }
    val ice = Color(0xFFE6F8FF)
    if (flurries) {
        drawSnowflake(65f, 76f, 7f)
        listOf(Offset(36f, 72f), Offset(48f, 79f)).forEach { drawCircle(ice, 1.8f, it) }
    }
    if (snowflakes > 0) {
        val centers = when (snowflakes) {
            1 -> listOf(Offset(67f, 76f))
            2 -> listOf(Offset(38f, 74f), Offset(64f, 77f))
            else -> listOf(Offset(30f, 72f), Offset(50f, 79f), Offset(71f, 72f))
        }
        centers.forEach { drawSnowflake(it.x, it.y, if (heavy) 8.5f else 7f) }
    }
    if (rain > 0) {
        val xs = if (rain == 1) listOf(35f) else listOf(31f, 47f)
        xs.forEachIndexed { index, x ->
            val y = if (index == 0) 77f else 81f
            drawLine(Color(0xFF55D8FF), Offset(x + 2f, y - 11f), Offset(x - 2f, y), 3.2f, StrokeCap.Round)
        }
    }
    if (pellets) listOf(Offset(79f, 79f), Offset(57f, 85f)).forEach {
        drawCircle(Color(0xFFA5DAEF), 3f, it)
        drawCircle(ice, 1.4f, Offset(it.x - 1f, it.y - 1f))
    }
    if (graupel) listOf(Offset(31f, 70f), Offset(49f, 78f), Offset(67f, 70f), Offset(59f, 87f)).forEach {
        drawCircle(Color(0xFFDFEDF3), 4.4f, it)
        drawCircle(Color.White.copy(alpha = .8f), 1.6f, Offset(it.x - 1.3f, it.y - 1.4f))
    }
    if (crystalsOnly) listOf(Offset(30f, 34f), Offset(70f, 28f), Offset(52f, 66f)).forEach {
        drawSnowflake(it.x, it.y, 9f)
    }
}

private fun DrawScope.drawSnowflake(cx: Float, cy: Float, radius: Float) {
    for (index in 0 until 3) {
        val angle = index * Math.PI / 3.0
        val dx = cos(angle).toFloat() * radius
        val dy = sin(angle).toFloat() * radius
        drawLine(Color(0xFFE8F9FF), Offset(cx - dx, cy - dy), Offset(cx + dx, cy + dy), 1.8f, StrokeCap.Round)
    }
    drawCircle(Color.White, 1.7f, Offset(cx, cy))
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

/** Visibility row: layered bands, ice accent, and warm particulate cues remain separate. */
private fun DrawScope.drawVisibility(kind: Int) {
    val cold = Color(0xFFB9E8F4)
    val warm = Color(0xFFD8A45F)
    val tint = if (kind >= 3) warm else cold
    if (kind == 0 || kind == 1 || kind == 2) {
        drawCloud(21f, 21f, 58f, 35f)
    } else if (kind == 3) {
        drawCircle(Color(0xFFFFC765), 9f, Offset(72f, 24f))
    }
    val bands = if (kind == 0) listOf(54f, 70f) else listOf(46f, 60f, 74f, 86f)
    bands.forEachIndexed { index, y ->
        val start = if (index % 2 == 0) 12f else 23f
        drawLine(tint.copy(alpha = if (kind == 1) .78f else .9f), Offset(start, y), Offset(89f - start / 4f, y - 3f),
            strokeWidth = if (kind == 1) 6f else 4f, cap = StrokeCap.Round)
    }
    if (kind == 2) drawSnowflake(76f, 70f, 9f)
    if (kind == 4) listOf(Offset(29f, 32f), Offset(48f, 22f), Offset(72f, 37f), Offset(35f, 78f), Offset(67f, 69f)).forEach {
        drawCircle(Color(0xFFFFCB70), 2.5f, it)
    }
}

/** Wind row: long directional strokes with separate drifting and dense snow. */
private fun DrawScope.drawWind(kind: Int) {
    val wind = Color(0xFF8EE6F5)
    val count = if (kind == 2) 5 else 3
    repeat(count) { index ->
        val y = 30f + index * (if (kind == 2) 12f else 18f)
        val path = Path().apply {
            moveTo(10f, y + 7f)
            cubicTo(31f, y - 8f, 49f, y + 10f, 73f, y - 5f)
            quadraticTo(85f, y - 11f, 91f, y - 9f)
        }
        drawPath(path, wind.copy(alpha = if (kind == 2) .9f else .78f), style = Stroke(3f, cap = StrokeCap.Round))
    }
    if (kind > 0) {
        val flakes = if (kind == 1) listOf(Offset(38f, 27f), Offset(73f, 68f))
            else listOf(Offset(25f, 23f), Offset(67f, 21f), Offset(42f, 57f), Offset(78f, 79f))
        flakes.forEach { drawSnowflake(it.x, it.y, if (kind == 2) 6f else 7f) }
    }
}
