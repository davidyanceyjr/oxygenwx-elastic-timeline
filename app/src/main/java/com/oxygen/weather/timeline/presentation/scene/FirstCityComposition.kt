package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

enum class CityCueKind { SUN, MOON }

/** Position and visibility are resolved at the selected instant before drawing. */
data class CityCueDecision(
    val position: Pair<Float, Float>?,
    val scale: Float,
    val kind: CityCueKind,
    val visible: Boolean,
)

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

internal enum class FirstCityCanopyAsset { BASELINE, OLIVE }

/** One existing physical pass, with its established absolute depth and local order. */
internal data class FirstCityPassReference(val layer: FirstCityLayer) {
    val depthGroup: FirstCityDepthGroup = requireNotNull(layer.depthGroup)
    val localOrder: Int = requireNotNull(layer.localOrder)
}

/** Bounds of an authored rectangle in First City world units, independent of viewport crop. */
internal data class FirstCityWorldBounds(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
)

/** The position names an anchor within an unscaled world-space rectangle. */
internal data class FirstCityElementGeometry(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val anchorX: Float,
    val anchorY: Float,
    val scale: Float,
) {
    init {
        require(x.isFinite() && y.isFinite())
        require(width.isFinite() && width > 0f && height.isFinite() && height > 0f)
        require(anchorX.isFinite() && anchorX in 0f..1f)
        require(anchorY.isFinite() && anchorY in 0f..1f)
        require(scale.isFinite() && scale > 0f)
    }

    fun transformedBounds(): FirstCityWorldBounds {
        val scaledWidth = width * scale
        val scaledHeight = height * scale
        val left = x - anchorX * scaledWidth
        val top = y - anchorY * scaledHeight
        return FirstCityWorldBounds(left, top, left + scaledWidth, top + scaledHeight)
    }
}

/** Describes one meaningful First City object and its ordered physical passes. */
internal data class FirstCitySemanticElement(
    val id: FirstCityElementId,
    val role: FirstCityElementRole,
    val renderPasses: Set<FirstCityLayer>,
    val geometry: FirstCityElementGeometry? = null,
    val canopyAsset: FirstCityCanopyAsset? = null,
) {
    val orderedPasses: List<FirstCityPassReference> = renderPasses
        .map(::FirstCityPassReference)
        .sortedWith(compareBy<FirstCityPassReference> { it.depthGroup.backToFrontOrder }
            .thenBy { it.localOrder })
}

/**
 * Complete First City semantic inventory, independent of which objects are visible at one instant.
 * Compositing-only passes intentionally have no semantic element.
 */
internal object FirstCitySemanticInventory {
    fun canopy(asset: FirstCityCanopyAsset) = FirstCitySemanticElement(
        FirstCityElementId.CANOPY,
        FirstCityElementRole.VEGETATION,
        setOf(FirstCityLayer.CANOPY),
        geometry = FirstCityElementGeometry(
            x = 0f, y = 0f,
            width = FirstCityTheme.worldWidth.toFloat(),
            height = FirstCityTheme.worldHeight.toFloat(),
            anchorX = 0f, anchorY = 0f, scale = 1f,
        ),
        canopyAsset = asset,
    )

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
        canopy(FirstCityCanopyAsset.OLIVE),
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

/** The one declaration-backed First City draw input. Other layers keep their direct paths. */
internal data class FirstCityCanopyRenderInput(
    val selectedInstant: Instant,
    val id: FirstCityElementId,
    val pass: FirstCityPassReference,
    val bounds: FirstCityWorldBounds,
    val asset: FirstCityCanopyAsset,
)

internal fun FirstCitySemanticElement.canopyDeclaration():
    SceneElementDeclaration<FirstCityElementId, FirstCityPassReference, FirstCityElementGeometry> {
    require(id == FirstCityElementId.CANOPY)
    require(role == FirstCityElementRole.VEGETATION)
    return SceneElementDeclaration(id, SceneElementRole.VEGETATION,
        orderedPasses.single(), requireNotNull(geometry))
}

internal fun resolveFirstCityCanopy(
    weatherDecision: SceneWeatherDecision,
    declaration: SceneElementDeclaration<FirstCityElementId, FirstCityPassReference, FirstCityElementGeometry>,
    asset: FirstCityCanopyAsset,
): FirstCityCanopyRenderInput {
    require(declaration.id == FirstCityElementId.CANOPY)
    require(declaration.role == SceneElementRole.VEGETATION)
    require(declaration.pass.layer == FirstCityLayer.CANOPY)
    val bounds = declaration.geometry.transformedBounds()
    require(bounds == FirstCityWorldBounds(0f, 0f,
        FirstCityTheme.worldWidth.toFloat(), FirstCityTheme.worldHeight.toFloat()))
    return FirstCityCanopyRenderInput(weatherDecision.selectedInstant, declaration.id,
        declaration.pass, bounds, asset)
}

internal fun FirstCityComposition.canopyRenderInput(
    declaration: FirstCitySemanticElement = requireNotNull(
        FirstCitySemanticInventory.forRenderPass(FirstCityLayer.CANOPY)
    ),
): FirstCityCanopyRenderInput {
    return resolveFirstCityCanopy(weatherDecision, declaration.canopyDeclaration(),
        requireNotNull(declaration.canopyAsset))
}

/** Small renderer contract for the catalog path; all weather choices are already resolved. */
data class FirstCityComposition(
    val weatherDecision: SceneWeatherDecision,
    val treatment: CityTreatment,
    val cue: CityCueDecision,
    val moonCue: CityCueDecision,
    val hasWeatherState: Boolean,
    val layerOrder: List<FirstCityLayer>,
) {
    val selectedInstant: Instant get() = weatherDecision.selectedInstant
    val sky: SceneSkyDecision? get() = weatherDecision.sky
    val clouds: SceneCloudDecision get() = weatherDecision.clouds
    val precipitation: ScenePrecipitation get() = weatherDecision.precipitation
}
