package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

/** The small authored shape shared by comparable objects in different worlds. */
internal data class SceneElementDeclaration<Id, Pass, Geometry>(
    val id: Id,
    val role: SceneElementRole,
    val pass: Pass,
    val geometry: Geometry,
)

internal enum class SceneElementRole { VEGETATION, CLOUD_FIELD, RAIN_FIELD, SNOW_FIELD }

/** Resolved cloud facts paired with one scene-authored cloud declaration for rendering. */
internal data class SceneCloudRenderInput<Id, Pass>(
    val selectedInstant: Instant,
    val id: Id,
    val pass: Pass,
    val decision: SceneCloudDecision,
    val style: SceneCloudRenderStyle,
)

/** A world supplies its own cloud placement and style through the declaration. */
internal sealed interface SceneCloudRenderStyle

/** A world supplies its local rain-field style through a precipitation declaration. */
internal sealed interface SceneRainRenderStyle

/** A world supplies its local snow-field style through a precipitation declaration. */
internal sealed interface SceneSnowRenderStyle

/** Resolved rain facts paired with one scene-authored rain declaration. */
internal data class SceneRainRenderInput<Id, Pass>(
    val selectedInstant: Instant,
    val id: Id,
    val pass: Pass,
    val decision: SceneWeatherDecision,
    val style: SceneRainRenderStyle,
)

/** Resolved snow facts paired with one scene-authored snow declaration. */
internal data class SceneSnowRenderInput<Id, Pass>(
    val selectedInstant: Instant,
    val id: Id,
    val pass: Pass,
    val decision: SceneWeatherDecision,
    val style: SceneSnowRenderStyle,
)

internal fun <Id, Pass> resolveSceneCloudRenderInput(
    weatherDecision: SceneWeatherDecision,
    declaration: SceneElementDeclaration<Id, Pass, SceneCloudRenderStyle>,
): SceneCloudRenderInput<Id, Pass> {
    require(declaration.role == SceneElementRole.CLOUD_FIELD)
    return SceneCloudRenderInput(
        selectedInstant = weatherDecision.selectedInstant,
        id = declaration.id,
        pass = declaration.pass,
        decision = weatherDecision.clouds,
        style = declaration.geometry,
    )
}

internal fun <Id, Pass> resolveSceneRainRenderInput(
    weatherDecision: SceneWeatherDecision,
    declaration: SceneElementDeclaration<Id, Pass, SceneRainRenderStyle>,
): SceneRainRenderInput<Id, Pass> {
    require(declaration.role == SceneElementRole.RAIN_FIELD)
    return SceneRainRenderInput(
        selectedInstant = weatherDecision.selectedInstant,
        id = declaration.id,
        pass = declaration.pass,
        decision = weatherDecision,
        style = declaration.geometry,
    )
}

internal fun <Id, Pass> resolveSceneSnowRenderInput(
    weatherDecision: SceneWeatherDecision,
    declaration: SceneElementDeclaration<Id, Pass, SceneSnowRenderStyle>,
): SceneSnowRenderInput<Id, Pass> {
    require(declaration.role == SceneElementRole.SNOW_FIELD)
    return SceneSnowRenderInput(
        selectedInstant = weatherDecision.selectedInstant,
        id = declaration.id,
        pass = declaration.pass,
        decision = weatherDecision,
        style = declaration.geometry,
    )
}
