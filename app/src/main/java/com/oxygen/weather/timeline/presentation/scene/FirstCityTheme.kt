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
        val local = reading.selectedInstant.atZone(zone)
        return when (reading.lightPhase) {
            LightPhase.DAY -> if (local.hour < 12) CityTreatment.MORNING else CityTreatment.DAY
            LightPhase.DUSK -> CityTreatment.DUSK
            LightPhase.NIGHT -> CityTreatment.NIGHT
        }
    }

    fun cuePosition(instant: Instant, zone: ZoneId): Pair<Float, Float> {
        val progress = cueProgress(instant, zone)
        return .12f + .76f * progress to .20f - .06f * kotlin.math.sin(progress * Math.PI).toFloat()
    }

    /** Theme-only apparent size: larger toward the ends of the illustrative arc. */
    fun cueScale(instant: Instant, zone: ZoneId): Float {
        val progress = cueProgress(instant, zone)
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

enum class CityTreatment { MORNING, DAY, DUSK, NIGHT }
