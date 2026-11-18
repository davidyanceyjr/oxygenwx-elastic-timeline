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

internal enum class OpenLandscapeElementId { LEFT_TREE_GROUP }

internal enum class OpenLandscapePass { FIRST_TREE_GROUP }

internal data class OpenLandscapeTreeGeometry(val centerX: Float, val scale: Float)

internal object OpenLandscapeSemanticInventory {
    val leftTreeGroup = SceneElementDeclaration(
        OpenLandscapeElementId.LEFT_TREE_GROUP,
        SceneElementRole.VEGETATION,
        OpenLandscapePass.FIRST_TREE_GROUP,
        OpenLandscapeTreeGeometry(centerX = .20f, scale = 1f),
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

object MinimalOpenLandscapeTheme {
    fun compose(weatherDecision: SceneWeatherDecision) =
        MinimalOpenLandscapeComposition(weatherDecision)
}
