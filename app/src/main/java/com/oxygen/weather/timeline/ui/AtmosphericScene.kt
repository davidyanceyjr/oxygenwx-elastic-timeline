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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.imageResource
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.presentation.atmosphere.DepictionKind
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualState
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualStateMapper
import com.oxygen.weather.timeline.presentation.SkyFrame
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.scene.CityTreatment
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import java.time.ZoneId
import com.oxygen.weather.timeline.R
import android.animation.ValueAnimator
import kotlin.math.PI
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

/** Catalog route: presentation has already supplied a typed decorative state. */
@Composable
fun CatalogAtmosphericScene(
    state: SceneVisualState,
    localHour: Double,
    phaseHours: Double,
    modifier: Modifier = Modifier,
) {
    AtmosphericScene(state, localHour, phaseHours, modifier)
}

/** First-city catalog route. A missing weather state omits all condition marks, never implying clear. */
@Composable
fun CatalogAtmosphericScene(
    reading: SceneReading,
    zone: ZoneId,
    state: SceneVisualState?,
    omitOptionalCityLayers: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val architecture = ImageBitmap.imageResource(R.drawable.sg2_city_architecture)
    val canopy = ImageBitmap.imageResource(R.drawable.sg2_city_canopy)
    val treatment = FirstCityTheme.treatment(reading, zone)
    val reducedMotion = !ValueAnimator.areAnimatorsEnabled()
    Canvas(modifier.fillMaxSize()) {
        if (state != null) {
            drawSkyPalette(state)
            if (state.depiction == DepictionKind.ILLUSTRATIVE_RAIN) {
                drawRect(Brush.verticalGradient(
                    0f to Color(0xFF4B6072), .55f to Color(0xFF5F7385),
                    1f to Color.Transparent,
                ))
            }
            drawCityDiffuseLight(reading, zone, treatment, state)
            when (state.preview) {
                AtmospherePreview.ILLUSTRATIVE_TURBULENCE,
                AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE ->
                    drawPreviewTurbulence(state.previewStrengths.turbulence.toFloat())
                else -> Unit
            }
            drawLandscape(state.previewStrengths.haze.toFloat(), sceneHorizonColor(state), state)
        } else {
            drawCityWorld(treatment)
        }
        drawAlignedLayer(architecture)
        if (state != null) {
            drawCityCloudFields(
                cloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f,
                daylight = state.daylight,
                localHour = reading.selectedInstant.atZone(zone).let {
                    it.hour + it.minute / 60.0 + it.second / 3600.0
                },
                isRain = state.depiction == DepictionKind.ILLUSTRATIVE_RAIN,
                isSnow = state.depiction == DepictionKind.ILLUSTRATIVE_SNOW,
                isStorm = state.hasStormVisual(),
                reducedMotion = reducedMotion,
            )
        }
        // Repaint the pixel-aligned lower city after the clouds. The upper skyline stays
        // behind the banks while the near buildings stay in front without altering the PNG.
        drawCityForegroundLayer(architecture)
        drawAlignedLayer(canopy)
        drawCityTimeWash(treatment)
        drawCityReadoutQuietZone()
        if (!omitOptionalCityLayers &&
            (treatment == CityTreatment.DUSK || treatment == CityTreatment.NIGHT)) drawCityWindowLights()
        if (state?.depiction != DepictionKind.ILLUSTRATIVE_RAIN) drawCityTimeCue(reading, zone, treatment)
        if (state != null) {
            drawRect(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    .5f to Color(0x09091828),
                    1f to Color(0xDD081626),
                ),
            )
            when (state.depiction) {
                DepictionKind.ILLUSTRATIVE_RAIN -> drawCityRainField()
                DepictionKind.ILLUSTRATIVE_SNOW, DepictionKind.SYNTHETIC_SNOW_PREVIEW ->
                    drawCitySnowField()
                else -> Unit
            }
        }
    }
}

private fun DrawScope.drawCityDiffuseLight(
    reading: SceneReading,
    zone: ZoneId,
    treatment: CityTreatment,
    state: SceneVisualState,
) {
    if (treatment == CityTreatment.NIGHT || treatment == CityTreatment.UNAVAILABLE) return
    val (xFraction, yFraction) = FirstCityTheme.cuePosition(reading, zone, treatment) ?: return
    val center = Offset(size.width * xFraction, size.height * yFraction)
    val supportedCloudiness = state.cloudFraction?.toFloat()?.coerceIn(0f, 1f) ?: 0f
    val dusk = treatment == CityTreatment.DUSK
    val spread = size.width * (.70f + .24f * supportedCloudiness)
    val lightX = (center.x / size.width).coerceIn(.001f, .999f)
    val warmLight = if (dusk) Color(0xFFF1A98A) else Color(0xFFFFDDAA)
    val diffuseAlpha = (if (dusk) .40f else .44f) * (1f - .38f * supportedCloudiness)
    val sideAlpha = diffuseAlpha * (if (supportedCloudiness > .05f) .66f else .42f)
    val left = (lightX - .46f).coerceAtLeast(0f)
    val right = (lightX + .46f).coerceAtMost(1f)
    drawRect(
        brush = Brush.horizontalGradient(
            colorStops = arrayOf(
                0f to Color.Transparent,
                left to warmLight.copy(alpha = sideAlpha),
                lightX to warmLight.copy(alpha = diffuseAlpha),
                right to warmLight.copy(alpha = sideAlpha),
                1f to Color.Transparent,
            ),
            startX = 0f,
            endX = size.width,
        ),
        size = Size(size.width, size.height * .48f),
    )
    val centerAlpha = diffuseAlpha * if (supportedCloudiness > .05f) .34f else .42f
    drawCircle(
        brush = Brush.radialGradient(
            colorStops = arrayOf(
                0f to warmLight.copy(alpha = centerAlpha),
                .46f to warmLight.copy(alpha = centerAlpha * .58f),
                1f to Color.Transparent,
            ),
            center = center,
            radius = spread,
        ),
        radius = spread,
        center = center,
    )
}

/** Selected-time storyboard, bounded to 06:00–18:00 with no loops or wall-clock drift. */
private fun DrawScope.drawCityCloudFields(
    cloudiness: Float,
    daylight: Double?,
    localHour: Double,
    isRain: Boolean,
    isSnow: Boolean,
    isStorm: Boolean,
    reducedMotion: Boolean,
) {
    if (cloudiness <= .01f || size.width <= 0f) return
    val day = daylight?.toFloat()?.coerceIn(0f, 1f) ?: .35f
    val baseColor = if (isStorm) Color(0xFF4E6075)
    else lerp(Color(0xFF7189A5), Color(0xFFE0EAF1), day)
    val hour = if (reducedMotion) 12.0 else localHour.takeIf(Double::isFinite) ?: 12.0
    val t = ((hour - 6.0) / 6.0).coerceIn(0.0, 2.0)
    fun interpolate(first: Float, middle: Float, last: Float): Float {
        val segment = t.coerceIn(0.0, 1.0)
        val secondSegment = (t - 1.0).coerceIn(0.0, 1.0)
        fun smooth(value: Double) = value * value * (3.0 - 2.0 * value)
        return if (t <= 1.0) first + (middle - first) * smooth(segment).toFloat()
        else middle + (last - middle) * smooth(secondSegment).toFloat()
    }
    val alpha = (cloudiness * if (isStorm) .70f else .43f).coerceIn(0f, if (isStorm) .60f else .48f)
    val y = size.height * .385f
    val height = size.height * .17f
    if (isStorm) {
        val bankWidth = size.width * .82f
        val leftX = size.width * interpolate(-.58f, -.12f, .12f)
        val rightX = size.width * interpolate(1.12f, .54f, .25f)
        drawCityCloudBank(leftX, y, bankWidth, height, baseColor.copy(alpha = alpha), mirrored = false)
        drawCityCloudBank(rightX, y - height * .18f, bankWidth, height, baseColor.copy(alpha = alpha * .82f), mirrored = true)
    } else {
        if (isRain || isSnow) {
            drawCityCloudBank(-size.width * .25f, y - height * .16f, size.width * .88f,
                height * 1.15f, baseColor.copy(alpha = .76f), mirrored = false)
            drawCityCloudBank(size.width * .40f, y - height * .12f, size.width * .9f,
                height * 1.12f, baseColor.copy(alpha = .74f), mirrored = true)
            return
        }
        val bankWidth = size.width * if (isRain) 1.02f else .78f
        val left = if (isRain) -.36f else .08f
        val middle = if (isRain) -.02f else .14f
        val right = if (isRain) .34f else .22f
        val x = size.width * interpolate(left, middle, right)
        drawCityCloudBank(x, y, bankWidth, height, baseColor.copy(alpha = alpha), mirrored = false)
    }
}

private fun DrawScope.drawCityCloudBank(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color,
    mirrored: Boolean,
) {
    fun px(fraction: Float) = x + width * if (mirrored) 1f - fraction else fraction
    val bank = Path().apply {
        moveTo(px(.02f), y + height * .76f)
        cubicTo(px(.08f), y + height * .58f, px(.16f), y + height * .59f, px(.25f), y + height * .68f)
        cubicTo(px(.34f), y + height * .34f, px(.47f), y + height * .36f, px(.55f), y + height * .61f)
        cubicTo(px(.66f), y + height * .42f, px(.78f), y + height * .48f, px(.85f), y + height * .69f)
        cubicTo(px(.92f), y + height * .61f, px(.98f), y + height * .68f, px(.98f), y + height * .78f)
        cubicTo(px(.76f), y + height * .91f, px(.28f), y + height * .90f, px(.02f), y + height * .76f)
        close()
    }
    drawPath(bank, color)
}

private fun DrawScope.drawCityRainField() {
    repeat(1800) { i ->
        val x = ((i * 83L + i * i * 29L + 19) % 991) / 991f * size.width
        val y = size.height * (.52f + ((i * 127L + i * i * 43L + 53) % 997) / 997f * .35f)
        val quiet = inPrecipitationQuietZone(x, y)
        val length = size.height * (.003f + (i % 7) * .00065f)
        drawLine(Color(0xFFE4F3FF).copy(alpha = (.23f + (i % 5) * .075f) *
            if (quiet) .28f else 1f),
            Offset(x, y), Offset(x - length * .13f, y + length),
            strokeWidth = (.35f + (i % 3) * .16f) * density)
    }
}

private fun DrawScope.drawCityTimeWash(treatment: CityTreatment) {
    val color = when (treatment) {
        CityTreatment.MORNING -> Color(0x33E9A767)
        CityTreatment.DAY -> Color(0x075795C6)
        CityTreatment.DUSK -> Color(0x42B45E72)
        CityTreatment.NIGHT -> Color(0x8506152C)
        CityTreatment.UNAVAILABLE -> Color(0x181A2936)
    }
    drawRect(color)
}

private fun DrawScope.drawCityReadoutQuietZone() {
    val center = Offset(size.width * .5f, size.height * .53f)
    val radius = size.width * .82f
    drawCircle(
        brush = Brush.radialGradient(
            colorStops = arrayOf(
                0f to Color(0x24102031),
                .48f to Color(0x18102031),
                1f to Color.Transparent,
            ),
            center = center,
            radius = radius,
        ),
        radius = radius,
        center = center,
    )
}

private fun DrawScope.drawCityWorld(treatment: CityTreatment) {
    val colors = when (treatment) {
        CityTreatment.MORNING -> listOf(Color(0xFF6497C0), Color(0xFFB9CBD4), Color(0xFFE8C49C))
        CityTreatment.DAY -> listOf(Color(0xFF347EB1), Color(0xFF8DC2DC), Color(0xFFD5D9D0))
        CityTreatment.DUSK -> listOf(Color(0xFF25385F), Color(0xFF685B7B), Color(0xFFD08275))
        CityTreatment.NIGHT -> listOf(Color(0xFF08152E), Color(0xFF1D3150), Color(0xFF39465D))
        CityTreatment.UNAVAILABLE -> listOf(Color(0xFF455B6D), Color(0xFF738391), Color(0xFFA4ADB0))
    }
    drawRect(brush = Brush.verticalGradient(
        0f to colors[0], .42f to colors[1], .72f to colors[2], 1f to colors[2],
    ))
    // A restrained phase wash preserves the same city geometry at every selected instant.
    val tint = when (treatment) {
        CityTreatment.MORNING -> Color(0x22FFC77F)
        CityTreatment.DAY -> Color(0x102B7CC0)
        CityTreatment.DUSK -> Color(0x285C3769)
        CityTreatment.NIGHT -> Color(0x5A07152F)
        CityTreatment.UNAVAILABLE -> Color(0x160A1621)
    }
    drawRect(tint)
}

private fun DrawScope.drawAlignedLayer(bitmap: ImageBitmap) {
    if (size.width <= 0f || size.height <= 0f) return
    val sourceAspect = bitmap.width.toFloat() / bitmap.height
    val viewportAspect = size.width / size.height
    val topCrop = if (viewportAspect < .70f) (bitmap.height * .18f).toInt() else 0
    val availableHeight = bitmap.height - topCrop
    val sourceWidth = if (sourceAspect > viewportAspect) {
        (availableHeight * viewportAspect).toInt().coerceIn(1, bitmap.width)
    } else bitmap.width
    val sourceHeight = if (sourceAspect > viewportAspect) {
        availableHeight
    } else {
        (bitmap.width / viewportAspect).toInt().coerceIn(1, availableHeight)
    }
    val sourceX = (bitmap.width - sourceWidth) / 2
    val sourceY = topCrop + (availableHeight - sourceHeight) / 2
    drawImage(
        image = bitmap,
        srcOffset = androidx.compose.ui.unit.IntOffset(sourceX, sourceY),
        srcSize = androidx.compose.ui.unit.IntSize(sourceWidth, sourceHeight),
        dstOffset = androidx.compose.ui.unit.IntOffset.Zero,
        dstSize = androidx.compose.ui.unit.IntSize(size.width.toInt(), size.height.toInt()),
        filterQuality = FilterQuality.Medium,
    )
}

/** Exact repeat draw from the original bitmap, clipped to the near-city band. */
private fun DrawScope.drawCityForegroundLayer(bitmap: ImageBitmap) {
    clipRect(top = size.height * .54f) {
        drawAlignedLayer(bitmap)
    }
}

private fun DrawScope.drawCityWindowLights() {
    // Deliberate normalized positions stay within the foreground building facades in the master.
    val windows = listOf(
        .055f to .783f, .105f to .812f, .164f to .801f, .235f to .773f,
        .296f to .825f, .373f to .792f, .445f to .817f, .518f to .785f,
        .584f to .832f, .662f to .801f, .731f to .827f, .807f to .785f,
        .874f to .816f, .938f to .782f,
    )
    val width = size.width * .009f
    val height = size.height * .008f
    windows.forEach { (x, y) ->
        drawRect(
            color = Color(0xFFFFC76E).copy(alpha = .84f),
            topLeft = Offset(size.width * x, size.height * y),
            size = Size(width, height),
        )
    }
}

private fun DrawScope.drawCityTimeCue(
    reading: SceneReading,
    zone: ZoneId,
    treatment: CityTreatment,
) {
    val (x, y) = FirstCityTheme.cuePosition(reading, zone, treatment) ?: return
    val center = Offset(size.width * x, size.height * y)
    val radius = size.width * .052f * FirstCityTheme.cueScale(reading, zone, treatment)
    if (treatment == CityTreatment.NIGHT) {
        drawCircle(
            brush = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0x44E7E7DE),
                    .52f to Color(0x20E7E7DE),
                    1f to Color.Transparent,
                ),
                center = center,
                radius = radius * 3.0f,
            ),
            radius = radius * 3.0f,
            center = center,
        )
        drawCircle(Color(0xFFF2EAD5), radius * .70f, center)
        drawCircle(Color(0xFF24334D), radius * .86f,
            center + Offset(radius * .42f, -radius * .30f))
    } else {
        val sun = if (treatment == CityTreatment.DUSK) Color(0xFFFFC9A1) else Color(0xFFFFE8BB)
        drawCircle(
            brush = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to sun.copy(alpha = .24f),
                    .52f to sun.copy(alpha = .10f),
                    1f to Color.Transparent,
                ),
                center = center,
                radius = radius * 3.0f,
            ),
            radius = radius * 3.0f,
            center = center,
        )
        drawCircle(sun.copy(alpha = .98f), radius * .57f, center)
    }
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

private fun DrawScope.drawCitySnowField() {
    repeat(2300) { i ->
        val x = ((i * 83L + i * i * 29L + 19) % 991) / 991f * size.width
        val y = size.height * (.52f + ((i * 127L + i * i * 43L + 53) % 997) / 997f * .35f)
        val quiet = inPrecipitationQuietZone(x, y)
        val radius = (.48f + (i % 7) * .24f) * density
        val center = Offset(x, y)
        if (i % 3 == 0) {
            repeat(3) { arm ->
                val angle = arm * Math.PI / 3.0
                val dx = kotlin.math.cos(angle).toFloat() * radius * 2.3f
                val dy = kotlin.math.sin(angle).toFloat() * radius * 2.3f
                drawLine(Color(0xCDEEF7FF).copy(alpha = if (quiet) .22f else .8f),
                    center - Offset(dx, dy), center + Offset(dx, dy),
                    strokeWidth = .48f * density)
            }
        } else drawCircle(Color(0xB8EEF7FF).copy(alpha = if (quiet) .2f else .72f), radius, center)
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
