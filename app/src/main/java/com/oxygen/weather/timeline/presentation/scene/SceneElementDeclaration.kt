package com.oxygen.weather.timeline.presentation.scene

/** The small authored shape shared by comparable objects in different worlds. */
internal data class SceneElementDeclaration<Id, Pass, Geometry>(
    val id: Id,
    val role: SceneElementRole,
    val pass: Pass,
    val geometry: Geometry,
)

internal enum class SceneElementRole { VEGETATION }
