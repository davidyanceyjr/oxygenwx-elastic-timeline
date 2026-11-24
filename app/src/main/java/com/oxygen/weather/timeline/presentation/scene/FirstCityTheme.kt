package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.PI
import kotlin.math.sin

/** Finite composition contract for the first city. It does not define a reusable theme registry. */
object FirstCityTheme {
    const val id = "first_city"
    const val version = 1
    const val worldWidth = 1024
    const val worldHeight = 1536
    const val scaling = "Fit world width; preserve aspect and anchor at bottom; allow open sky above"

    /** First City physical layers; group and local order are the renderer's depth contract. */
    val layers = listOf(
        CityLayer(
            FirstCityLayer.SKY, "world", "Compose city world or existing weather backdrop", true,
            "Neutral blue-gray gradient when weather state is unavailable",
        ),
        CityLayer(FirstCityLayer.STARS, "stars", "Minimal Background star field", false,
            "Omit decorative stars"),
        CityLayer(FirstCityLayer.TIME_CUE, "time_cue", "Selected-local-time Sun cue", false,
            "Keep geometry and palette; omit the cue"),
        CityLayer(FirstCityLayer.LUNAR_CUE, "lunar_cue", "Provider-bounded Moon cue", false,
            "Keep geometry and palette; omit the cue"),
        CityLayer(FirstCityLayer.BACKGROUND_CLOUDS, "background_clouds", "Background cloud clusters", false,
            "Omit clouds when no visible cloud decision exists"),
        CityLayer(FirstCityLayer.BACKGROUND_ARCHITECTURE, "background_architecture", "City world and distant skyline", true,
            "Unresolved required layer; do not substitute weather art"),
        CityLayer(FirstCityLayer.ATMOSPHERE, "atmosphere", "Existing diffuse atmospheric light", false,
            "Omit the diffuse light when no sky decision exists"),
        CityLayer(FirstCityLayer.MIDDLE_GROUND_CLOUDS, "middle_ground_clouds", "Middle-Ground cloud clusters", false,
            "Omit clouds when no visible cloud decision exists"),
        CityLayer(FirstCityLayer.FOREGROUND_ARCHITECTURE, "foreground_architecture", "Pixel-aligned near city", true,
            "Unresolved required layer; do not substitute weather art"),
        CityLayer(FirstCityLayer.CANOPY, "canopy", "sg2_city_canopy", true,
            "Unresolved required layer; do not substitute weather art"),
        CityLayer(FirstCityLayer.WINDOW_LIGHTS, "window_lights", "Compose clipped window details", false,
            "Omit light details"),
        CityLayer(FirstCityLayer.PRECIPITATION, "precipitation", "Existing single rain/snow field", false,
            "Omit precipitation marks when weather state is unavailable"),
    )

    /** Compatibility entry point; all weather meaning is resolved by the shared decision seam. */
    fun compose(
        reading: SceneReading,
        zone: ZoneId,
        canopyAsset: FirstCityCanopyAsset = FirstCityCanopyAsset.OLIVE,
    ): FirstCityComposition = compose(SceneWeatherDecision.resolve(reading, zone), zone, canopyAsset)

    /** Compose the city-local treatment, cues, and layers from shared weather meaning. */
    fun compose(
        weatherDecision: SceneWeatherDecision,
        zone: ZoneId,
        canopyAsset: FirstCityCanopyAsset = FirstCityCanopyAsset.OLIVE,
    ): FirstCityComposition {
        val reading = weatherDecision.reading
        val treatment = treatment(reading, zone)
        val sky = weatherDecision.sky
        val clouds = weatherDecision.clouds
        val precipitation = weatherDecision.precipitation
        val position = cuePosition(reading, zone, treatment)
        val cue = CityCueDecision(
            position = position,
            scale = cueScale(reading, zone, treatment),
            kind = if (treatment == CityTreatment.NIGHT) CityCueKind.MOON else CityCueKind.SUN,
            visible = position != null && treatment != CityTreatment.NIGHT && precipitation != CityPrecipitation.RAIN,
        )
        val moonPosition = lunarPosition(reading)
        val moonCue = CityCueDecision(
            position = moonPosition,
            scale = lunarScale(reading),
            kind = CityCueKind.MOON,
            visible = moonPosition != null,
        )
        val hasWeatherState = sky != null || clouds.visible || precipitation != CityPrecipitation.NONE
        val visibleLayers = buildSet {
            add(FirstCityLayer.SKY)
            add(FirstCityLayer.STARS)
            add(FirstCityLayer.BACKGROUND_ARCHITECTURE)
            add(FirstCityLayer.ATMOSPHERE)
            add(FirstCityLayer.FOREGROUND_ARCHITECTURE)
            add(FirstCityLayer.CANOPY)
            if (cue.visible) add(FirstCityLayer.TIME_CUE)
            if (moonCue.visible) add(FirstCityLayer.LUNAR_CUE)
            if (clouds.visible) {
                add(FirstCityLayer.BACKGROUND_CLOUDS)
                add(FirstCityLayer.MIDDLE_GROUND_CLOUDS)
            }
            if (treatment == CityTreatment.DUSK || treatment == CityTreatment.NIGHT) {
                add(FirstCityLayer.WINDOW_LIGHTS)
            }
            if (hasWeatherState) add(FirstCityLayer.PRECIPITATION)
        }
        val layerOrder = orderedLayerSequence(visibleLayers)
        return FirstCityComposition(
            weatherDecision = weatherDecision,
            treatment = treatment,
            cue = cue,
            moonCue = moonCue,
            hasWeatherState = hasWeatherState,
            layerOrder = layerOrder,
            canopyAsset = canopyAsset,
        )
    }

    private fun orderedLayerSequence(visibleLayers: Set<FirstCityLayer>): List<FirstCityLayer> {
        val physicalLayers = layers
            .filter { it.layer in visibleLayers }
            .sortedWith(
                compareBy<CityLayer> { it.depthGroup.backToFrontOrder }
                    .thenBy { it.localOrder },
            )
        return buildList {
            physicalLayers.forEach { layer ->
                add(layer.layer)
                if (layer.layer == FirstCityLayer.CANOPY) {
                    add(FirstCityLayer.TIME_WASH)
                    add(FirstCityLayer.READOUT_QUIET_ZONE)
                }
            }
        }
    }

    fun treatment(reading: SceneReading, zone: ZoneId): CityTreatment {
        return when (reading.lightPhase) {
            LightPhase.DAY -> CityTreatment.DAY
            LightPhase.DAWN -> CityTreatment.MORNING
            LightPhase.DUSK -> CityTreatment.DUSK
            LightPhase.NIGHT -> CityTreatment.NIGHT
            LightPhase.UNAVAILABLE -> CityTreatment.UNAVAILABLE
        }
    }

    fun cuePosition(reading: SceneReading, zone: ZoneId, treatment: CityTreatment): Pair<Float, Float>? {
        if (treatment == CityTreatment.NIGHT) {
            val progress = cueProgress(reading.selectedInstant, zone)
            return .12f + .76f * progress to .20f - .06f * kotlin.math.sin(progress * Math.PI).toFloat()
        }
        val events = reading.solarEvents?.takeIf { it.hasCompleteDaylight } ?: return null
        val sunrise = requireNotNull(events.sunriseInstant)
        val sunset = requireNotNull(events.sunsetInstant)
        if (reading.selectedInstant < sunrise || reading.selectedInstant > sunset) return null
        val progress = ((reading.selectedInstant.epochSecond - sunrise.epochSecond).toDouble() /
            (sunset.epochSecond - sunrise.epochSecond).toDouble()).coerceIn(0.0, 1.0).toFloat()
        return .12f + .76f * progress to .42f - .24f * kotlin.math.sin(progress * Math.PI).toFloat()
    }

    /** Theme-only apparent size: larger toward the ends of the illustrative arc. */
    fun cueScale(reading: SceneReading, zone: ZoneId, treatment: CityTreatment): Float {
        val progress = if (treatment == CityTreatment.NIGHT) cueProgress(reading.selectedInstant, zone) else {
            val events = reading.solarEvents?.takeIf { it.hasCompleteDaylight } ?: return 1f
            val sunrise = requireNotNull(events.sunriseInstant)
            val sunset = requireNotNull(events.sunsetInstant)
            ((reading.selectedInstant.epochSecond - sunrise.epochSecond).toDouble() /
                (sunset.epochSecond - sunrise.epochSecond).toDouble()).coerceIn(0.0, 1.0).toFloat()
        }
        return 1.28f - .48f * kotlin.math.sin(progress * Math.PI).toFloat()
    }

    fun phaseLabel(phase: Double?): String? {
        if (phase == null || !phase.isFinite() || phase !in 0.0..1.0) return null
        val stages = listOf("New Moon", "Waxing Crescent", "First Quarter", "Waxing Gibbous", "Full Moon", "Waning Gibbous", "Last Quarter", "Waning Crescent")
        val index = kotlin.math.floor(phase * 8.0 + .5).toInt() % 8
        return stages[index]
    }

    fun lunarPosition(reading: SceneReading): Pair<Float, Float>? {
        val events = reading.lunarEvents
        events.firstOrNull { it.instant == reading.selectedInstant }?.let { event ->
            return (if (event.kind == LunarEventKind.RISE) .12f else .88f) to .42f
        }
        val before = events.lastOrNull { it.instant < reading.selectedInstant } ?: return null
        val after = events.firstOrNull { it.instant > reading.selectedInstant } ?: return null
        if (before.kind != LunarEventKind.RISE || after.kind != LunarEventKind.SET) return null
        val progress = ((reading.selectedInstant.epochSecond - before.instant.epochSecond).toDouble() /
            (after.instant.epochSecond - before.instant.epochSecond).toDouble()).coerceIn(0.0, 1.0).toFloat()
        return (.12f + .76f * progress) to (.42f - .24f * kotlin.math.sin(progress * Math.PI).toFloat())
    }

    fun lunarScale(reading: SceneReading): Float {
        val events = reading.lunarEvents
        val before = events.lastOrNull { it.instant <= reading.selectedInstant } ?: return 1.28f
        val after = events.firstOrNull { it.instant >= reading.selectedInstant } ?: return 1.28f
        if (before.kind != LunarEventKind.RISE || after.kind != LunarEventKind.SET || before.instant == after.instant) return 1.28f
        val progress = ((reading.selectedInstant.epochSecond - before.instant.epochSecond).toDouble() /
            (after.instant.epochSecond - before.instant.epochSecond).toDouble()).coerceIn(0.0, 1.0).toFloat()
        return 1.28f - .48f * kotlin.math.sin(progress * Math.PI).toFloat()
    }

    private fun cueProgress(instant: Instant, zone: ZoneId): Float {
        val local: ZonedDateTime = instant.atZone(zone)
        val hour = local.hour + local.minute / 60.0
        return ((hour - 6.0) / 16.0).coerceIn(0.0, 1.0).toFloat()
    }

}

data class CityLayer(
    val layer: FirstCityLayer,
    val name: String,
    val asset: String,
    val required: Boolean,
    val fallback: String,
    val depthGroup: FirstCityDepthGroup = requireNotNull(layer.depthGroup),
    val localOrder: Int = requireNotNull(layer.localOrder),
) {
    init {
        require(layer.depthGroup == depthGroup && layer.localOrder == localOrder) {
            "City layer metadata must match its First City depth contract"
        }
    }
}

enum class CityTreatment { MORNING, DAY, DUSK, NIGHT, UNAVAILABLE }
