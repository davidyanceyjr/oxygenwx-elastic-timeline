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

/** Stable, First City-local identities for meaningful scene objects. */
internal enum class FirstCityElementId {
    SKY_BACKDROP,
    STAR_FIELD,
    SUN_CUE,
    MOON_CUE,
    CLOUD_COVER,
    DISTANT_SKYLINE,
    DIFFUSE_ATMOSPHERIC_LIGHT,
    NEAR_CITY,
    CANOPY,
    WINDOW_DETAILS,
    PRECIPITATION_FIELD,
}

/** Reusable semantic categories, kept separate from element identity and physical draw order. */
internal enum class FirstCityElementRole {
    SKY,
    CELESTIAL_FIELD,
    TIME_CUE,
    LUNAR_CUE,
    CLOUD_FIELD,
    CITY_STRUCTURE,
    ATMOSPHERE,
    VEGETATION,
    LIGHT_DETAIL,
    WEATHER_FIELD,
}

/** Describes one meaningful First City object and the existing physical passes that render it. */
internal data class FirstCitySemanticElement(
    val id: FirstCityElementId,
    val role: FirstCityElementRole,
    val renderPasses: Set<FirstCityLayer>,
)

/**
 * Complete First City semantic inventory, independent of which objects are visible at one instant.
 * Compositing-only passes intentionally have no semantic element.
 */
internal object FirstCitySemanticInventory {
    val elements: List<FirstCitySemanticElement> = listOf(
        FirstCitySemanticElement(
            FirstCityElementId.SKY_BACKDROP,
            FirstCityElementRole.SKY,
            setOf(FirstCityLayer.SKY),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.STAR_FIELD,
            FirstCityElementRole.CELESTIAL_FIELD,
            setOf(FirstCityLayer.STARS),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.SUN_CUE,
            FirstCityElementRole.TIME_CUE,
            setOf(FirstCityLayer.TIME_CUE),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.MOON_CUE,
            FirstCityElementRole.LUNAR_CUE,
            setOf(FirstCityLayer.LUNAR_CUE),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.CLOUD_COVER,
            FirstCityElementRole.CLOUD_FIELD,
            setOf(FirstCityLayer.BACKGROUND_CLOUDS, FirstCityLayer.MIDDLE_GROUND_CLOUDS),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.DISTANT_SKYLINE,
            FirstCityElementRole.CITY_STRUCTURE,
            setOf(FirstCityLayer.BACKGROUND_ARCHITECTURE),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.DIFFUSE_ATMOSPHERIC_LIGHT,
            FirstCityElementRole.ATMOSPHERE,
            setOf(FirstCityLayer.ATMOSPHERE),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.NEAR_CITY,
            FirstCityElementRole.CITY_STRUCTURE,
            setOf(FirstCityLayer.FOREGROUND_ARCHITECTURE),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.CANOPY,
            FirstCityElementRole.VEGETATION,
            setOf(FirstCityLayer.CANOPY),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.WINDOW_DETAILS,
            FirstCityElementRole.LIGHT_DETAIL,
            setOf(FirstCityLayer.WINDOW_LIGHTS),
        ),
        FirstCitySemanticElement(
            FirstCityElementId.PRECIPITATION_FIELD,
            FirstCityElementRole.WEATHER_FIELD,
            setOf(FirstCityLayer.PRECIPITATION),
        ),
    )

    fun forRenderPass(layer: FirstCityLayer): FirstCitySemanticElement? =
        elements.firstOrNull { layer in it.renderPasses }
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
