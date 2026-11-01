package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/** Finite composition contract for the first city. It does not define a reusable theme registry. */
object FirstCityTheme {
    const val id = "first_city"
    const val version = 1
    const val worldWidth = 1024
    const val worldHeight = 1536
    const val scaling = "Fit world width; preserve aspect and anchor at bottom; allow open sky above"

    val layers = listOf(
        CityLayer("world", "Compose city world or existing weather backdrop", backToFrontOrder = 0, required = true,
            fallback = "Neutral blue-gray gradient when weather state is unavailable"),
        CityLayer("architecture", "sg2_city_architecture", backToFrontOrder = 1, required = true,
            fallback = "Unresolved required layer; do not substitute weather art"),
        CityLayer("canopy", "sg2_city_canopy", backToFrontOrder = 2, required = true,
            fallback = "Unresolved required layer; do not substitute weather art"),
        CityLayer("window_lights", "Compose clipped window details", backToFrontOrder = 3, required = false,
            fallback = "Omit light details"),
        CityLayer("time_cue", "selected-local-time sun/moon cue", backToFrontOrder = 4, required = true,
            fallback = "Keep geometry and palette; omit the cue"),
        CityLayer("precipitation", "existing rain/snow drawing slots", backToFrontOrder = 5, required = false,
            fallback = "Omit precipitation marks when weather state is unavailable"),
    )

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

    private fun cueProgress(instant: Instant, zone: ZoneId): Float {
        val local: ZonedDateTime = instant.atZone(zone)
        val hour = local.hour + local.minute / 60.0
        return ((hour - 6.0) / 16.0).coerceIn(0.0, 1.0).toFloat()
    }
}

data class CityLayer(
    val name: String,
    val asset: String,
    val backToFrontOrder: Int,
    val required: Boolean,
    val fallback: String,
)

enum class CityTreatment { MORNING, DAY, DUSK, NIGHT, UNAVAILABLE }
