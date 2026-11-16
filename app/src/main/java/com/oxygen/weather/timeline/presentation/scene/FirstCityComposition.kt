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

enum class FirstCityDepthGroup(val backToFrontOrder: Int) {
    BACKGROUND(0),
    MIDDLE_GROUND(1),
    FOREGROUND(2),
}

/** Physical layers carry an absolute group and a stable local order. Null group marks a pass. */
enum class FirstCityLayer(
    val depthGroup: FirstCityDepthGroup?,
    val localOrder: Int?,
) {
    SKY(FirstCityDepthGroup.BACKGROUND, 0),
    STARS(FirstCityDepthGroup.BACKGROUND, 1),
    TIME_CUE(FirstCityDepthGroup.BACKGROUND, 2),
    LUNAR_CUE(FirstCityDepthGroup.BACKGROUND, 3),
    BACKGROUND_CLOUDS(FirstCityDepthGroup.BACKGROUND, 4),
    BACKGROUND_ARCHITECTURE(FirstCityDepthGroup.BACKGROUND, 5),
    ATMOSPHERE(FirstCityDepthGroup.MIDDLE_GROUND, 0),
    MIDDLE_GROUND_CLOUDS(FirstCityDepthGroup.MIDDLE_GROUND, 1),
    FOREGROUND_ARCHITECTURE(FirstCityDepthGroup.FOREGROUND, 0),
    CANOPY(FirstCityDepthGroup.FOREGROUND, 1),
    WINDOW_LIGHTS(FirstCityDepthGroup.FOREGROUND, 2),
    PRECIPITATION(FirstCityDepthGroup.FOREGROUND, 3),
    TIME_WASH(null, null),
    READOUT_QUIET_ZONE(null, null),
}

/** Small renderer contract for the catalog path; all weather choices are already resolved. */
data class FirstCityComposition(
    val selectedInstant: Instant,
    val treatment: CityTreatment,
    val sky: CitySkyDecision?,
    val cue: CityCueDecision,
    val moonCue: CityCueDecision,
    val clouds: CityCloudDecision,
    val precipitation: CityPrecipitation,
    val hasWeatherState: Boolean,
    val layerOrder: List<FirstCityLayer>,
)
