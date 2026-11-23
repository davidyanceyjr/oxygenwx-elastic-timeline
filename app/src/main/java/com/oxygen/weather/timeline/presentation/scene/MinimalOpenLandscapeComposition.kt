package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

/** Review-only open terrain composition; all weather meaning comes from its shared input. */
class MinimalOpenLandscapeComposition internal constructor(
    val weatherDecision: SceneWeatherDecision,
) {
    val selectedInstant: Instant get() = weatherDecision.selectedInstant
    val sky: SceneSkyDecision? get() = weatherDecision.sky
    val clouds: SceneCloudDecision get() = weatherDecision.clouds
    val precipitation: ScenePrecipitation get() = weatherDecision.precipitation
}

internal enum class OpenLandscapeElementId { LEFT_TREE_GROUP, CLOUD_COVER, PRECIPITATION_FIELD }

internal enum class OpenLandscapePass { FIRST_TREE_GROUP, CLOUDS, PRECIPITATION }

internal data class OpenLandscapeTreeGeometry(val centerX: Float, val scale: Float)

internal data class OpenLandscapeCloudBankPlacement(
    val xFraction: Float,
    val yFraction: Float,
    val widthFraction: Float,
    val heightFraction: Float,
    val opacityMultiplier: Float,
    val mirrored: Boolean,
)

/** Minimal Open Landscape's authored cloud placement; the renderer keeps the shared cloud meaning. */
internal data class OpenLandscapeCloudRenderStyle(
    val driftAmplitude: Double = .018,
    val bankPlacements: List<OpenLandscapeCloudBankPlacement> = listOf(
        OpenLandscapeCloudBankPlacement(-.015f, .025f, .29f, .16f, .90f, mirrored = false),
        OpenLandscapeCloudBankPlacement(.235f, .075f, .27f, .145f, 1f, mirrored = true),
        OpenLandscapeCloudBankPlacement(.485f, .025f, .29f, .16f, 1f, mirrored = false),
        OpenLandscapeCloudBankPlacement(.735f, .075f, .27f, .145f, .90f, mirrored = true),
    ),
) : SceneCloudRenderStyle {
    companion object {
        val DEFAULT = OpenLandscapeCloudRenderStyle()
    }
}

/** Open Landscape keeps rain in its existing foreground precipitation pass. */
internal object OpenLandscapeRainRenderStyle : SceneRainRenderStyle

/** Open Landscape keeps snow in its existing foreground precipitation pass. */
internal object OpenLandscapeSnowRenderStyle : SceneSnowRenderStyle

internal object OpenLandscapeSemanticInventory {
    val leftTreeGroup = SceneElementDeclaration(
        OpenLandscapeElementId.LEFT_TREE_GROUP,
        SceneElementRole.VEGETATION,
        OpenLandscapePass.FIRST_TREE_GROUP,
        OpenLandscapeTreeGeometry(centerX = .20f, scale = 1f),
    )

    val cloudCover: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneCloudRenderStyle> = SceneElementDeclaration(
        OpenLandscapeElementId.CLOUD_COVER,
        SceneElementRole.CLOUD_FIELD,
        OpenLandscapePass.CLOUDS,
        OpenLandscapeCloudRenderStyle.DEFAULT,
    )

    val rainField: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneRainRenderStyle> = SceneElementDeclaration(
        OpenLandscapeElementId.PRECIPITATION_FIELD,
        SceneElementRole.RAIN_FIELD,
        OpenLandscapePass.PRECIPITATION,
        OpenLandscapeRainRenderStyle,
    )

    val snowField: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneSnowRenderStyle> = SceneElementDeclaration(
        OpenLandscapeElementId.PRECIPITATION_FIELD,
        SceneElementRole.SNOW_FIELD,
        OpenLandscapePass.PRECIPITATION,
        OpenLandscapeSnowRenderStyle,
    )
}

internal data class OpenLandscapeTreeRenderInput(
    val selectedInstant: Instant,
    val id: OpenLandscapeElementId,
    val pass: OpenLandscapePass,
    val geometry: OpenLandscapeTreeGeometry,
)

internal fun MinimalOpenLandscapeComposition.treeGroupRenderInput(
    declaration: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        OpenLandscapeTreeGeometry> = OpenLandscapeSemanticInventory.leftTreeGroup,
): OpenLandscapeTreeRenderInput {
    require(declaration.role == SceneElementRole.VEGETATION)
    return OpenLandscapeTreeRenderInput(weatherDecision.selectedInstant, declaration.id,
        declaration.pass, declaration.geometry)
}

internal fun MinimalOpenLandscapeComposition.cloudRenderInput(
    declaration: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneCloudRenderStyle> = OpenLandscapeSemanticInventory.cloudCover,
): SceneCloudRenderInput<OpenLandscapeElementId, OpenLandscapePass> {
    require(declaration.id == OpenLandscapeElementId.CLOUD_COVER)
    require(declaration.pass == OpenLandscapePass.CLOUDS)
    return resolveSceneCloudRenderInput(weatherDecision, declaration)
}

internal fun MinimalOpenLandscapeComposition.rainRenderInput(
    declaration: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneRainRenderStyle> = OpenLandscapeSemanticInventory.rainField,
): SceneRainRenderInput<OpenLandscapeElementId, OpenLandscapePass> {
    require(declaration.id == OpenLandscapeElementId.PRECIPITATION_FIELD)
    require(declaration.pass == OpenLandscapePass.PRECIPITATION)
    return resolveSceneRainRenderInput(weatherDecision, declaration)
}

internal fun MinimalOpenLandscapeComposition.snowRenderInput(
    declaration: SceneElementDeclaration<OpenLandscapeElementId, OpenLandscapePass,
        SceneSnowRenderStyle> = OpenLandscapeSemanticInventory.snowField,
): SceneSnowRenderInput<OpenLandscapeElementId, OpenLandscapePass> {
    require(declaration.id == OpenLandscapeElementId.PRECIPITATION_FIELD)
    require(declaration.pass == OpenLandscapePass.PRECIPITATION)
    return resolveSceneSnowRenderInput(weatherDecision, declaration)
}

object MinimalOpenLandscapeTheme {
    fun compose(weatherDecision: SceneWeatherDecision) =
        MinimalOpenLandscapeComposition(weatherDecision)
}
