package com.oxygen.weather.timeline.presentation.scene

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.oxygen.weather.scenecore.ScenePaintedDataSurfaceStyle
import com.oxygen.weather.scenecore.ScenePaintedPathLayer
import com.oxygen.weather.scenecore.ScenePaintedPathSurface
import com.oxygen.weather.scenecore.ScenePaintedSurfaceGeometry
import com.oxygen.weather.scenecore.ScenePlane
import com.oxygen.weather.scenecore.ScenePlanePoint
import com.oxygen.weather.scenecore.ScenePlaneQuadrilateral
import com.oxygen.weather.scenecore.createScenePaintedGlyphPath
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolArtwork
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolCatalog
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.Instant
import java.util.Locale

/** Selected-time weather values consumed by the Urban Park wall renderer. */
internal data class UrbanParkWallReader(
    val selectedInstant: Instant,
    val weatherSymbolId: WeatherSymbolId?,
    val description: String?,
    val currentTemperature: String?,
) {
    val visibleDescription: String get() = description ?: "Condition unavailable"

    val accessibleDescription: String
        get() = "$visibleDescription. Current temperature ${currentTemperature ?: "unavailable"}."
}

/** The Foreground masonry face and inset quad reserved for the painted reader group. */
internal object UrbanParkWallReaderSurface {
    val geometry = ScenePaintedSurfaceGeometry(
        plane = ScenePlane.FOREGROUND,
        face = ScenePlaneQuadrilateral(
            topLeft = ScenePlanePoint(.00f, .210f),
            topRight = ScenePlanePoint(.445f, .305f),
            bottomRight = ScenePlanePoint(.435f, .755f),
            bottomLeft = ScenePlanePoint(.00f, .700f),
        ),
        textRegion = ScenePlaneQuadrilateral(
            topLeft = ScenePlanePoint(.180f, .345f),
            topRight = ScenePlanePoint(.425f, .390f),
            bottomRight = ScenePlanePoint(.410f, .515f),
            bottomLeft = ScenePlanePoint(.170f, .475f),
        ),
    )
}

/**
 * Builds one Park-owned vector/glyph group. The scene-core surface applies one projection and the
 * same clip, texture, and pigment pipeline to every path in this local coordinate space.
 */
internal fun createUrbanParkWallReaderSurface(
    reader: UrbanParkWallReader,
    foreground: androidx.compose.ui.graphics.ImageBitmap,
): ScenePaintedPathSurface {
    val heroStyle = ScenePaintedDataSurfaceStyle(
        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD),
        maxTextSize = 330f,
        minTextSize = 190f,
        maxLines = 1,
        lineSpacingMultiplier = 1f,
        pigmentStrokeWidth = 4.5f,
        pigmentTexture = foreground,
        pigmentTint = PARK_PARCHMENT,
        pigmentAlpha = .76f,
        shadowColor = Color.Transparent,
        shadowOffset = Offset.Zero,
        highlightColor = Color.Transparent,
        highlightOffset = Offset.Zero,
        highlightWidth = 0f,
    )
    val descriptionStyle = ScenePaintedDataSurfaceStyle(
        typeface = Typeface.create("cursive", Typeface.BOLD_ITALIC),
        maxTextSize = 148f,
        minTextSize = 64f,
        maxLines = 2,
        lineSpacingMultiplier = 1f,
        pigmentStrokeWidth = heroStyle.pigmentStrokeWidth,
        pigmentTexture = foreground,
        pigmentTint = PARK_PARCHMENT,
        pigmentAlpha = heroStyle.pigmentAlpha,
        shadowColor = Color.Transparent,
        shadowOffset = Offset.Zero,
        highlightColor = Color.Transparent,
        highlightOffset = Offset.Zero,
        highlightWidth = 0f,
    )

    val localWidth = 760f
    val localHeight = 600f
    val parchmentPaths = Path()
    createScenePaintedGlyphPath(
        value = reader.currentTemperature ?: "—",
        width = 550f,
        height = 345f,
        style = heroStyle,
    )?.addAt(parchmentPaths, 0f, 12f)
    createScenePaintedGlyphPath(
        value = reader.visibleDescription.uppercase(Locale.US),
        width = 720f,
        height = 180f,
        style = descriptionStyle,
    )?.addAt(parchmentPaths, 0f, 394f)

    val symbol = reader.weatherSymbolId ?: WeatherSymbolId.CONDITION_UNAVAILABLE
    val symbolLayers = ParkWeatherArtworkPaths.create(symbol)
        .map { layer -> layer.scaledAndTranslated(scale = .88f, x = 460f, y = 145f) }
    val pathLayers = buildList {
        if (!parchmentPaths.isEmpty) add(ScenePaintedPathLayer(parchmentPaths, PARK_PARCHMENT))
        symbolLayers.forEach { layer ->
            add(ScenePaintedPathLayer(layer.path, layer.tint, strokeOnly = layer.strokeOnly))
        }
    }

    return ScenePaintedPathSurface(
        sourcePlaneSize = IntSize(foreground.width, foreground.height),
        localWidth = localWidth,
        localHeight = localHeight,
        geometry = UrbanParkWallReaderSurface.geometry,
        style = heroStyle,
        layers = pathLayers,
    )
}

private fun Path.addAt(destination: Path, x: Float, y: Float) {
    val matrix = Matrix().apply { setTranslate(x, y) }
    val translated = Path()
    transform(matrix, translated)
    destination.addPath(translated)
}

private fun ParkWeatherPath.scaledAndTranslated(scale: Float, x: Float, y: Float): ParkWeatherPath {
    val matrix = Matrix().apply {
        setValues(
            floatArrayOf(
                scale, 0f, x,
                0f, scale, y,
                0f, 0f, 1f,
            ),
        )
    }
    val transformed = Path()
    path.transform(matrix, transformed)
    return copy(path = transformed)
}

private data class ParkWeatherPath(
    val path: Path,
    val tint: Color,
    val strokeOnly: Boolean = false,
)

/** Park's icon art follows the existing semantic-to-art catalog, then paints as vector paths. */
private object ParkWeatherArtworkPaths {
    fun create(symbol: WeatherSymbolId): List<ParkWeatherPath> {
        val artwork = WeatherSymbolCatalog.resolve(symbol).artwork
        val cream = Path()
        val cloud = Path()
        val aquaOutline = Path()
        val aquaFill = Path()
        val gold = Path()
        val ice = Path()

        when (artwork) {
            WeatherSymbolArtwork.SUN -> addSun(gold, 110f, 95f, 42f)
            WeatherSymbolArtwork.MOON -> {
                addMoon(cream, 108f, 96f, 48f)
                addStars(ice, listOf(Offset(35f, 48f), Offset(70f, 25f), Offset(180f, 47f)))
            }
            WeatherSymbolArtwork.MOSTLY_SUNNY -> {
                addSun(gold, 78f, 75f, 37f)
                addCloud(cloud, aquaOutline, 25f, 82f, 172f, 100f)
            }
            WeatherSymbolArtwork.MOSTLY_CLEAR_NIGHT -> {
                addMoon(cream, 80f, 73f, 39f)
                addStars(ice, listOf(Offset(25f, 34f), Offset(180f, 30f), Offset(185f, 75f)))
                addCloud(cloud, aquaOutline, 42f, 108f, 155f, 83f)
            }
            WeatherSymbolArtwork.PARTLY_SUNNY_CLOUDY -> {
                addSun(gold, 77f, 70f, 39f)
                addCloud(cloud, aquaOutline, 20f, 82f, 180f, 105f)
            }
            WeatherSymbolArtwork.PARTLY_CLOUDY_NIGHT -> {
                addMoon(cream, 77f, 68f, 40f)
                addStars(ice, listOf(Offset(28f, 30f), Offset(174f, 32f), Offset(185f, 70f)))
                addCloud(cloud, aquaOutline, 20f, 84f, 180f, 103f)
            }
            WeatherSymbolArtwork.MOSTLY_CLOUDY,
            WeatherSymbolArtwork.CLOUDY_OVERCAST -> {
                addCloud(cloud, aquaOutline, 15f, 68f, 185f, 112f)
                if (artwork == WeatherSymbolArtwork.CLOUDY_OVERCAST) {
                    addCloud(cloud, aquaOutline, 22f, 42f, 174f, 100f)
                }
            }
            WeatherSymbolArtwork.DRIZZLE -> {
                addCloud(cloud, aquaOutline, 18f, 29f, 184f, 105f)
                addRain(aquaFill, listOf(55f, 110f, 165f), 143f, 22f)
            }
            WeatherSymbolArtwork.RAIN -> {
                addCloud(cloud, aquaOutline, 18f, 22f, 184f, 105f)
                addRain(aquaFill, listOf(45f, 90f, 135f, 180f), 140f, 31f)
            }
            WeatherSymbolArtwork.HEAVY_RAIN -> {
                addCloud(cloud, aquaOutline, 18f, 18f, 184f, 105f)
                addRain(aquaFill, listOf(28f, 68f, 108f, 148f, 188f), 137f, 38f)
            }
            WeatherSymbolArtwork.RAIN_SHOWERS -> {
                addSun(gold, 56f, 44f, 28f)
                addCloud(cloud, aquaOutline, 36f, 60f, 164f, 96f)
                addRain(aquaFill, listOf(58f, 110f, 162f), 144f, 29f)
            }
            WeatherSymbolArtwork.FREEZING_DRIZZLE,
            WeatherSymbolArtwork.FREEZING_RAIN -> {
                addCloud(cloud, aquaOutline, 18f, 22f, 184f, 105f)
                addRain(aquaFill, listOf(54f, 111f, 168f), 140f, 30f)
                addSnowflake(ice, 193f, 175f, 12f)
            }
            WeatherSymbolArtwork.ICE_PELLETS,
            WeatherSymbolArtwork.HAIL -> {
                addCloud(cloud, aquaOutline, 18f, 20f, 184f, 105f)
                listOf(Offset(54f, 156f), Offset(109f, 173f), Offset(164f, 156f)).forEach {
                    addCircle(aquaFill, it.x, it.y, if (artwork == WeatherSymbolArtwork.HAIL) 11f else 8f)
                }
            }
            WeatherSymbolArtwork.FLURRIES_SNOW_GRAINS,
            WeatherSymbolArtwork.SNOW,
            WeatherSymbolArtwork.HEAVY_SNOW,
            WeatherSymbolArtwork.SNOW_SHOWERS,
            WeatherSymbolArtwork.GRAUPEL,
            WeatherSymbolArtwork.ICE_CRYSTALS -> {
                if (artwork != WeatherSymbolArtwork.ICE_CRYSTALS) {
                    if (artwork == WeatherSymbolArtwork.SNOW_SHOWERS) addSun(gold, 55f, 42f, 28f)
                    addCloud(cloud, aquaOutline, 18f, 25f, 184f, 105f)
                }
                val centers = when (artwork) {
                    WeatherSymbolArtwork.FLURRIES_SNOW_GRAINS -> listOf(Offset(70f, 163f), Offset(120f, 177f), Offset(170f, 159f))
                    WeatherSymbolArtwork.SNOW -> listOf(Offset(72f, 164f), Offset(152f, 164f))
                    WeatherSymbolArtwork.HEAVY_SNOW -> listOf(Offset(48f, 160f), Offset(106f, 178f), Offset(166f, 160f))
                    WeatherSymbolArtwork.SNOW_SHOWERS -> listOf(Offset(76f, 164f), Offset(152f, 164f))
                    WeatherSymbolArtwork.GRAUPEL -> listOf(Offset(50f, 160f), Offset(95f, 176f), Offset(145f, 160f), Offset(185f, 178f))
                    else -> listOf(Offset(48f, 45f), Offset(168f, 43f), Offset(108f, 112f))
                }
                centers.forEach { addSnowflake(ice, it.x, it.y, if (artwork == WeatherSymbolArtwork.HEAVY_SNOW) 16f else 12f) }
            }
            WeatherSymbolArtwork.RAIN_AND_SNOW,
            WeatherSymbolArtwork.WINTRY_MIX -> {
                addCloud(cloud, aquaOutline, 18f, 22f, 184f, 105f)
                addRain(aquaFill, listOf(62f, 156f), 140f, 28f)
                addSnowflake(ice, 109f, 164f, 13f)
            }
            WeatherSymbolArtwork.MIST,
            WeatherSymbolArtwork.FOG,
            WeatherSymbolArtwork.FREEZING_FOG,
            WeatherSymbolArtwork.HAZE_SMOKE,
            WeatherSymbolArtwork.DUST_SAND_ASH,
            WeatherSymbolArtwork.DUST_SANDSTORM -> {
                if (artwork == WeatherSymbolArtwork.FOG || artwork == WeatherSymbolArtwork.FREEZING_FOG) {
                    addCloud(cloud, aquaOutline, 25f, 23f, 170f, 90f)
                }
                addVisibility(aquaOutline, 43f, 126f, 165f)
                if (artwork == WeatherSymbolArtwork.FREEZING_FOG) addSnowflake(ice, 181f, 164f, 11f)
                if (artwork == WeatherSymbolArtwork.DUST_SAND_ASH ||
                    artwork == WeatherSymbolArtwork.DUST_SANDSTORM
                ) addDust(cream)
            }
            WeatherSymbolArtwork.WINDY_GUSTY,
            WeatherSymbolArtwork.BLOWING_SNOW,
            WeatherSymbolArtwork.BLIZZARD,
            WeatherSymbolArtwork.SQUALL -> {
                if (artwork != WeatherSymbolArtwork.WINDY_GUSTY) {
                    addCloud(cloud, aquaOutline, 24f, 24f, 172f, 94f)
                }
                addWind(aquaOutline, 48f, 93f)
                if (artwork == WeatherSymbolArtwork.BLOWING_SNOW || artwork == WeatherSymbolArtwork.BLIZZARD) {
                    addSnowflake(ice, 166f, 161f, 12f)
                }
                if (artwork == WeatherSymbolArtwork.SQUALL) addRain(aquaFill, listOf(104f, 160f), 153f, 24f)
            }
            WeatherSymbolArtwork.THUNDERSTORM,
            WeatherSymbolArtwork.THUNDERSTORM_WITH_RAIN,
            WeatherSymbolArtwork.THUNDERSTORM_WITH_HAIL,
            WeatherSymbolArtwork.THUNDERSTORM_WITH_SNOW,
            WeatherSymbolArtwork.DRY_THUNDERSTORM -> {
                addCloud(cloud, aquaOutline, 18f, 18f, 184f, 105f)
                addLightning(gold, 118f, 116f)
                when (artwork) {
                    WeatherSymbolArtwork.THUNDERSTORM_WITH_RAIN -> addRain(aquaFill, listOf(52f, 174f), 145f, 28f)
                    WeatherSymbolArtwork.THUNDERSTORM_WITH_HAIL -> listOf(Offset(52f, 159f), Offset(176f, 159f)).forEach { addCircle(ice, it.x, it.y, 10f) }
                    WeatherSymbolArtwork.THUNDERSTORM_WITH_SNOW -> addSnowflake(ice, 165f, 161f, 12f)
                    else -> Unit
                }
            }
            WeatherSymbolArtwork.TROPICAL_CYCLONE,
            WeatherSymbolArtwork.TROPICAL_STORM -> {
                addCyclone(aquaOutline, 110f, 98f)
                if (artwork == WeatherSymbolArtwork.TROPICAL_STORM) {
                    addCloud(cloud, aquaOutline, 24f, 22f, 172f, 98f)
                    addRain(aquaFill, listOf(63f, 112f, 161f), 145f, 25f)
                }
            }
            WeatherSymbolArtwork.TORNADO_FUNNEL -> addFunnel(aquaOutline)
            WeatherSymbolArtwork.CONDITION_UNAVAILABLE -> {
                addCloud(cloud, aquaOutline, 25f, 32f, 170f, 100f)
                addUnavailableMark(gold)
            }
            WeatherSymbolArtwork.WATERSPOUT -> {
                addFunnel(aquaOutline)
                addVisibility(aquaOutline, 158f, 33f, 116f)
            }
            WeatherSymbolArtwork.FROST -> {
                addSnowflake(ice, 55f, 60f, 20f)
                addSnowflake(ice, 160f, 58f, 20f)
                addSnowflake(ice, 108f, 158f, 22f)
            }
            WeatherSymbolArtwork.EXTREME_HEAT -> {
                addSun(gold, 110f, 76f, 43f)
                addVisibility(aquaOutline, 122f, 46f, 120f)
            }
            WeatherSymbolArtwork.EXTREME_COLD -> {
                addCloud(cloud, aquaOutline, 20f, 25f, 180f, 102f)
                addSnowflake(ice, 110f, 163f, 18f)
            }
            WeatherSymbolArtwork.VOLCANIC_ASH -> {
                addCloud(cloud, aquaOutline, 20f, 24f, 180f, 102f)
                addDust(cream)
            }
        }

        return buildList {
            if (!gold.isEmpty) add(ParkWeatherPath(gold, PARK_GOLD))
            if (!cream.isEmpty) add(ParkWeatherPath(cream, PARK_PARCHMENT))
            if (!cloud.isEmpty) add(ParkWeatherPath(cloud, PARK_PARCHMENT))
            if (!aquaOutline.isEmpty) add(ParkWeatherPath(aquaOutline, PARK_AQUA, strokeOnly = true))
            if (!aquaFill.isEmpty) add(ParkWeatherPath(aquaFill, PARK_AQUA))
            if (!ice.isEmpty) add(ParkWeatherPath(ice, PARK_ICE))
        }
    }

    private fun addSun(path: Path, cx: Float, cy: Float, radius: Float) {
        addCircle(path, cx, cy, radius)
        repeat(12) { index ->
            val angle = Math.toRadians(index * 30.0 - 90.0)
            val inner = radius + 8f
            val outer = radius + 22f
            path.moveTo(cx + kotlin.math.cos(angle).toFloat() * inner, cy + kotlin.math.sin(angle).toFloat() * inner)
            path.lineTo(cx + kotlin.math.cos(angle).toFloat() * outer, cy + kotlin.math.sin(angle).toFloat() * outer)
        }
    }

    private fun addMoon(path: Path, cx: Float, cy: Float, radius: Float) {
        path.moveTo(cx + radius * .45f, cy - radius)
        path.cubicTo(cx - radius * .82f, cy - radius * .68f, cx - radius * .82f, cy + radius * .72f, cx + radius * .4f, cy + radius)
        path.cubicTo(cx - radius * .08f, cy + radius * .23f, cx - radius * .1f, cy - radius * .3f, cx + radius * .45f, cy - radius)
        path.close()
    }

    private fun addStars(path: Path, points: List<Offset>) {
        points.forEach { point ->
            path.moveTo(point.x - 6f, point.y)
            path.lineTo(point.x + 6f, point.y)
            path.moveTo(point.x, point.y - 6f)
            path.lineTo(point.x, point.y + 6f)
        }
    }

    private fun addCloud(fill: Path, outline: Path, x: Float, y: Float, width: Float, height: Float) {
        fun trace(path: Path) {
            path.moveTo(x + width * .08f, y + height * .82f)
            path.cubicTo(x - width * .02f, y + height * .78f, x - width * .01f, y + height * .58f, x + width * .14f, y + height * .52f)
            path.cubicTo(x + width * .16f, y + height * .22f, x + width * .42f, y + height * .14f, x + width * .53f, y + height * .42f)
            path.cubicTo(x + width * .68f, y + height * .12f, x + width * .98f, y + height * .29f, x + width * .88f, y + height * .61f)
            path.cubicTo(x + width * 1.08f, y + height * .70f, x + width * .97f, y + height * .91f, x + width * .78f, y + height * .91f)
            path.lineTo(x + width * .2f, y + height * .91f)
            path.cubicTo(x + width * .1f, y + height * .91f, x + width * .08f, y + height * .88f, x + width * .08f, y + height * .82f)
            path.close()
        }
        trace(fill)
        trace(outline)
    }

    private fun addRain(path: Path, xs: List<Float>, y: Float, length: Float) {
        xs.forEachIndexed { index, x ->
            val top = y + if (index % 2 == 0) 0f else 7f
            val halfWidth = 7f
            path.moveTo(x, top)
            path.cubicTo(x - halfWidth, top + length * .35f, x - halfWidth, top + length * .72f, x, top + length)
            path.cubicTo(x + halfWidth, top + length * .72f, x + halfWidth, top + length * .35f, x, top)
            path.close()
        }
    }

    private fun addSnowflake(path: Path, cx: Float, cy: Float, radius: Float) {
        repeat(3) { index ->
            val angle = Math.toRadians(index * 60.0)
            val dx = kotlin.math.cos(angle).toFloat() * radius
            val dy = kotlin.math.sin(angle).toFloat() * radius
            path.moveTo(cx - dx, cy - dy)
            path.lineTo(cx + dx, cy + dy)
        }
        addCircle(path, cx, cy, 2.4f)
    }

    private fun addVisibility(path: Path, top: Float, left: Float, width: Float) {
        repeat(3) { index ->
            val y = top + index * 22f
            path.moveTo(left, y)
            path.cubicTo(left + width * .22f, y - 8f, left + width * .72f, y + 8f, left + width, y)
        }
    }

    private fun addWind(path: Path, x: Float, y: Float) {
        path.moveTo(x, y)
        path.cubicTo(x + 38f, y - 20f, x + 93f, y - 18f, x + 130f, y)
        path.cubicTo(x + 155f, y + 14f, x + 133f, y + 29f, x + 115f, y + 19f)
        path.moveTo(x - 10f, y + 36f)
        path.cubicTo(x + 25f, y + 19f, x + 81f, y + 20f, x + 116f, y + 37f)
        path.cubicTo(x + 141f, y + 50f, x + 125f, y + 63f, x + 109f, y + 54f)
    }

    private fun addLightning(path: Path, x: Float, y: Float) {
        path.moveTo(x + 24f, y)
        path.lineTo(x - 9f, y + 43f)
        path.lineTo(x + 15f, y + 43f)
        path.lineTo(x - 12f, y + 90f)
        path.lineTo(x + 45f, y + 31f)
        path.lineTo(x + 19f, y + 31f)
        path.close()
    }

    private fun addCyclone(path: Path, cx: Float, cy: Float) {
        path.moveTo(cx - 78f, cy - 15f)
        path.cubicTo(cx - 62f, cy - 85f, cx + 53f, cy - 82f, cx + 73f, cy - 26f)
        path.cubicTo(cx + 91f, cy + 25f, cx + 42f, cy + 72f, cx - 12f, cy + 58f)
        path.cubicTo(cx - 60f, cy + 45f, cx - 51f, cy - 6f, cx - 8f, cy - 17f)
        path.cubicTo(cx + 23f, cy - 24f, cx + 41f, cy + 4f, cx + 25f, cy + 24f)
        path.cubicTo(cx + 14f, cy + 37f, cx - 5f, cy + 31f, cx - 5f, cy + 18f)
    }

    private fun addFunnel(path: Path) {
        path.moveTo(42f, 35f)
        path.cubicTo(78f, 28f, 145f, 28f, 180f, 35f)
        path.cubicTo(163f, 76f, 147f, 98f, 120f, 119f)
        path.lineTo(116f, 174f)
        path.lineTo(98f, 174f)
        path.lineTo(100f, 119f)
        path.cubicTo(73f, 98f, 58f, 75f, 42f, 35f)
        path.close()
        path.moveTo(68f, 192f)
        path.cubicTo(86f, 181f, 130f, 181f, 151f, 192f)
    }

    private fun addDust(path: Path) {
        listOf(Offset(40f, 52f), Offset(177f, 58f), Offset(60f, 170f), Offset(184f, 180f)).forEach {
            addCircle(path, it.x, it.y, 4f)
        }
    }

    private fun addUnavailableMark(path: Path) {
        path.moveTo(93f, 72f)
        path.cubicTo(96f, 45f, 136f, 43f, 139f, 69f)
        path.cubicTo(141f, 84f, 120f, 88f, 116f, 105f)
        path.moveTo(116f, 132f)
        path.lineTo(116f, 134f)
    }

    private fun addCircle(path: Path, x: Float, y: Float, radius: Float) {
        path.addCircle(x, y, radius, Path.Direction.CW)
    }
}

private val PARK_PARCHMENT = Color(0xFFF0E7CB)
private val PARK_AQUA = Color(0xFF70D5E4)
private val PARK_GOLD = Color(0xFFF3C773)
private val PARK_ICE = Color(0xFFE2F1F2)
