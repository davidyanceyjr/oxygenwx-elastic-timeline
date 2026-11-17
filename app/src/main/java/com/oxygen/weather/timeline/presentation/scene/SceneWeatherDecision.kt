package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.ZoneId
import kotlin.math.PI
import kotlin.math.sin

enum class ScenePrecipitation { NONE, RAIN, SNOW, STORM }

enum class SceneCloudAppearance { GENERAL, RAIN, SNOW, STORM }

data class SceneSkyDecision(
    val condition: SkyCondition,
    val daylight: Double,
)

data class SceneCloudDecision(
    val coverFraction: Double?,
    val daylight: Double,
    val localHour: Double,
    val appearance: SceneCloudAppearance,
    val visible: Boolean,
)

/** Shared weather meaning for one selected reading; the source retains every field and its evidence. */
class SceneWeatherDecision internal constructor(
    val reading: SceneReading,
    val sky: SceneSkyDecision?,
    val clouds: SceneCloudDecision,
    val precipitation: ScenePrecipitation,
) {
    val selectedInstant get() = reading.selectedInstant

    companion object {
        /** Resolve the existing First City weather rules once for every world-specific composer. */
        fun resolve(reading: SceneReading, zone: ZoneId): SceneWeatherDecision {
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
                rainPositive && !snowPositive -> ScenePrecipitation.RAIN
                snowPositive && !rainPositive -> ScenePrecipitation.SNOW
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
                conditionIsSupplied -> conditions?.let(::precipitation)?.takeIf { it != ScenePrecipitation.NONE }
                    ?: ScenePrecipitation.NONE
                conditionIsCarried -> uniqueAmountEvent ?: conditions?.let(::precipitation) ?: ScenePrecipitation.NONE
                else -> uniqueAmountEvent ?: ScenePrecipitation.NONE
            }
            val cloudAppearance = when (precipitation) {
                ScenePrecipitation.RAIN -> SceneCloudAppearance.RAIN
                ScenePrecipitation.SNOW -> SceneCloudAppearance.SNOW
                ScenePrecipitation.STORM -> SceneCloudAppearance.STORM
                ScenePrecipitation.NONE -> SceneCloudAppearance.GENERAL
            }
            val clouds = SceneCloudDecision(
                coverFraction = cloudFraction,
                daylight = daylight,
                localHour = localHour,
                appearance = cloudAppearance,
                visible = cloudFraction?.let { it > .01 } == true,
            )
            return SceneWeatherDecision(
                reading = reading,
                sky = skyCondition?.let { SceneSkyDecision(it, daylight) },
                clouds = clouds,
                precipitation = precipitation,
            )
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

        private fun precipitation(conditions: Set<WeatherCondition>): ScenePrecipitation = when {
            WeatherCondition.STORM in conditions -> ScenePrecipitation.STORM
            WeatherCondition.SNOW in conditions -> ScenePrecipitation.SNOW
            WeatherCondition.RAIN in conditions -> ScenePrecipitation.RAIN
            else -> ScenePrecipitation.NONE
        }
    }
}

// Preserve the established First City source names while both compositions share these values.
typealias CitySkyDecision = SceneSkyDecision
typealias CityCloudAppearance = SceneCloudAppearance
typealias CityCloudDecision = SceneCloudDecision
typealias CityPrecipitation = ScenePrecipitation
