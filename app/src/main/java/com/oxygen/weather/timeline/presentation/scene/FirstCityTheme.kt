package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
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

    /** Resolve catalog values, support, solar position, and the established layer order once. */
    fun compose(reading: SceneReading, zone: ZoneId): FirstCityComposition {
        val treatment = treatment(reading, zone)
        val conditions = reading.conditions?.value
        val skyCondition = conditions?.let(::skyCondition)
        val local = reading.selectedInstant.atZone(zone)
        val localHour = local.hour + local.minute / 60.0 + local.second / 3600.0
        val visualHour = local.hour + local.minute / 60.0
        val daylight = sin(((visualHour - 6.0) / 12.0) * PI).coerceIn(0.0, 1.0)
        val cloudFraction = reading.cloudCoverPercent
            ?.takeIf { it.evidence == SceneEvidence.SUPPLIED }
            ?.value?.div(100.0)
        val rainPositive = reading.rainAmount?.let {
            it.evidence == SceneEvidence.SUPPLIED && it.value?.valueMillimeters?.let { amount -> amount > 0.0 } == true
        } == true
        val snowPositive = reading.snowfall?.let {
            it.evidence == SceneEvidence.SUPPLIED && it.value?.valueCentimeters?.let { amount -> amount > 0.0 } == true
        } == true
        val uniqueAmountEvent = when {
            rainPositive && !snowPositive -> CityPrecipitation.RAIN
            snowPositive && !rainPositive -> CityPrecipitation.SNOW
            else -> null
        }
        val conditionIsSupplied = reading.conditions?.let {
            it.evidence == SceneEvidence.SUPPLIED && !it.value.isNullOrEmpty()
        } == true
        val conditionIsCarried = reading.conditions?.let {
            it.evidence == SceneEvidence.EXPECTED_MISSING &&
                !it.value.isNullOrEmpty() && it.sourceValidTime != null
        } == true
        val precipitation = when {
            conditionIsSupplied -> conditions?.let(::precipitation)?.takeIf { it != CityPrecipitation.NONE }
                ?: CityPrecipitation.NONE
            conditionIsCarried -> uniqueAmountEvent ?: conditions?.let(::precipitation) ?: CityPrecipitation.NONE
            else -> uniqueAmountEvent ?: CityPrecipitation.NONE
        }
        val sky = skyCondition?.let { CitySkyDecision(it, daylight) }
        val cloudAppearance = when (precipitation) {
            CityPrecipitation.RAIN -> CityCloudAppearance.RAIN
            CityPrecipitation.SNOW -> CityCloudAppearance.SNOW
            CityPrecipitation.STORM -> CityCloudAppearance.STORM
            CityPrecipitation.NONE -> CityCloudAppearance.GENERAL
        }
        val clouds = CityCloudDecision(
            coverFraction = cloudFraction,
            daylight = daylight,
            localHour = localHour,
            appearance = cloudAppearance,
            visible = cloudFraction?.let { it > .01 } == true,
        )
        val position = cuePosition(reading, zone, treatment)
        val cue = CityCueDecision(
            position = position,
            scale = cueScale(reading, zone, treatment),
            kind = if (treatment == CityTreatment.NIGHT) CityCueKind.MOON else CityCueKind.SUN,
            visible = position != null && precipitation != CityPrecipitation.RAIN,
        )
        val hasWeatherState = sky != null || clouds.visible || precipitation != CityPrecipitation.NONE
        val layerOrder = buildList {
            add(FirstCityLayer.SKY)
            add(FirstCityLayer.BACKGROUND_ARCHITECTURE)
            if (clouds.visible) add(FirstCityLayer.CLOUDS)
            add(FirstCityLayer.FOREGROUND_ARCHITECTURE)
            add(FirstCityLayer.CANOPY)
            add(FirstCityLayer.TIME_WASH)
            add(FirstCityLayer.READOUT_QUIET_ZONE)
            if (treatment == CityTreatment.DUSK || treatment == CityTreatment.NIGHT) {
                add(FirstCityLayer.WINDOW_LIGHTS)
            }
            if (cue.visible) add(FirstCityLayer.TIME_CUE)
            if (hasWeatherState) add(FirstCityLayer.PRECIPITATION)
        }
        return FirstCityComposition(
            selectedInstant = reading.selectedInstant,
            treatment = treatment,
            sky = sky,
            cue = cue,
            clouds = clouds,
            precipitation = precipitation,
            hasWeatherState = hasWeatherState,
            layerOrder = layerOrder,
        )
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

    private fun cueProgress(instant: Instant, zone: ZoneId): Float {
        val local: ZonedDateTime = instant.atZone(zone)
        val hour = local.hour + local.minute / 60.0
        return ((hour - 6.0) / 16.0).coerceIn(0.0, 1.0).toFloat()
    }

    private fun skyCondition(conditions: Set<WeatherCondition>): SkyCondition? = when {
        WeatherCondition.STORM in conditions -> SkyCondition.STORM
        WeatherCondition.RAIN in conditions -> SkyCondition.RAIN
        WeatherCondition.CLOUDY in conditions -> SkyCondition.CLOUDY
        WeatherCondition.PARTLY_CLOUDY in conditions -> SkyCondition.PARTLY_CLOUDY
        WeatherCondition.CLEAR in conditions -> SkyCondition.CLEAR
        WeatherCondition.SNOW in conditions -> SkyCondition.CLOUDY
        else -> null
    }

    private fun precipitation(conditions: Set<WeatherCondition>): CityPrecipitation = when {
        WeatherCondition.STORM in conditions -> CityPrecipitation.STORM
        WeatherCondition.SNOW in conditions -> CityPrecipitation.SNOW
        WeatherCondition.RAIN in conditions -> CityPrecipitation.RAIN
        else -> CityPrecipitation.NONE
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
