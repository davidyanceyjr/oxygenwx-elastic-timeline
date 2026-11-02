package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.SkyCondition
import java.time.Instant

/** Resolved first-city sky input. A missing category stays absent rather than becoming clear. */
data class CitySkyDecision(
    val condition: SkyCondition,
    val daylight: Double,
)

enum class CityCueKind { SUN, MOON }

/** Position and visibility are resolved at the selected instant before drawing. */
data class CityCueDecision(
    val position: Pair<Float, Float>?,
    val scale: Float,
    val kind: CityCueKind,
    val visible: Boolean,
)

enum class CityCloudAppearance { GENERAL, RAIN, SNOW, STORM }

/** Cloud support is retained as supplied; appearance describes only the selected artwork. */
data class CityCloudDecision(
    val coverFraction: Double?,
    val daylight: Double,
    val localHour: Double,
    val appearance: CityCloudAppearance,
    val visible: Boolean,
)

enum class CityPrecipitation { NONE, RAIN, SNOW, STORM }

enum class FirstCityLayer {
    SKY,
    BACKGROUND_ARCHITECTURE,
    CLOUDS,
    FOREGROUND_ARCHITECTURE,
    CANOPY,
    TIME_WASH,
    READOUT_QUIET_ZONE,
    WINDOW_LIGHTS,
    TIME_CUE,
    PRECIPITATION,
}

/** Small renderer contract for the catalog path; all weather choices are already resolved. */
data class FirstCityComposition(
    val selectedInstant: Instant,
    val treatment: CityTreatment,
    val sky: CitySkyDecision?,
    val cue: CityCueDecision,
    val clouds: CityCloudDecision,
    val precipitation: CityPrecipitation,
    val hasWeatherState: Boolean,
    val layerOrder: List<FirstCityLayer>,
)
