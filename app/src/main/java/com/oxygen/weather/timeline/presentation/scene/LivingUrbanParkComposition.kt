package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

internal enum class LivingUrbanParkElementId {
    DISTANT_BACKDROP,
    CURVED_PATH,
    ENTRANCE,
}

internal enum class LivingUrbanParkElementRole {
    DISTANT_BACKDROP,
    PATH,
    ENTRANCE,
}

internal enum class LivingUrbanParkDepthPass(val backToFrontOrder: Int) {
    BACKGROUND(0),
    PATH(1),
    FOREGROUND(2),
}

internal data class ParkPoint(val x: Float, val y: Float) {
    init {
        require(x.isFinite() && x in 0f..1f)
        require(y.isFinite() && y in 0f..1f)
    }
}

internal data class ParkCubicSegment(
    val control1: ParkPoint,
    val control2: ParkPoint,
    val end: ParkPoint,
)

internal sealed interface LivingUrbanParkGeometry {
    data class Backdrop(
        val horizon: Float,
        val skyline: List<ParkPoint>,
    ) : LivingUrbanParkGeometry {
        init {
            require(horizon.isFinite() && horizon in 0f..1f)
            require(skyline.size >= 4)
        }
    }

    data class CurvedPath(
        val leftStart: ParkPoint,
        val leftEdge: List<ParkCubicSegment>,
        val rightStart: ParkPoint,
        val rightEdge: List<ParkCubicSegment>,
    ) : LivingUrbanParkGeometry {
        init {
            require(leftEdge.isNotEmpty() && rightEdge.isNotEmpty())
        }
    }

    data class Entrance(
        val leftTop: ParkPoint,
        val rightTop: ParkPoint,
        val rightBottom: ParkPoint,
        val leftBottom: ParkPoint,
    ) : LivingUrbanParkGeometry
}

/** Park-owned declarations keep their identity and semantics separate from vegetation adapters. */
internal data class LivingUrbanParkElementDeclaration(
    val id: LivingUrbanParkElementId,
    val role: LivingUrbanParkElementRole,
    val pass: LivingUrbanParkDepthPass,
    val geometry: LivingUrbanParkGeometry,
)

internal object LivingUrbanParkSemanticInventory {
    val elements = listOf(
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.DISTANT_BACKDROP,
            role = LivingUrbanParkElementRole.DISTANT_BACKDROP,
            pass = LivingUrbanParkDepthPass.BACKGROUND,
            geometry = LivingUrbanParkGeometry.Backdrop(
                horizon = .50f,
                skyline = listOf(
                    ParkPoint(0f, .61f), ParkPoint(0f, .55f), ParkPoint(.08f, .55f),
                    ParkPoint(.08f, .52f), ParkPoint(.16f, .52f), ParkPoint(.16f, .56f),
                    ParkPoint(.24f, .56f), ParkPoint(.24f, .49f), ParkPoint(.31f, .49f),
                    ParkPoint(.31f, .55f), ParkPoint(.39f, .55f), ParkPoint(.39f, .51f),
                    ParkPoint(.47f, .51f), ParkPoint(.47f, .55f), ParkPoint(.56f, .55f),
                    ParkPoint(.56f, .46f), ParkPoint(.64f, .46f), ParkPoint(.64f, .53f),
                    ParkPoint(.73f, .53f), ParkPoint(.73f, .49f), ParkPoint(.81f, .49f),
                    ParkPoint(.81f, .56f), ParkPoint(.91f, .56f), ParkPoint(.91f, .51f),
                    ParkPoint(1f, .51f), ParkPoint(1f, .61f),
                ),
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.CURVED_PATH,
            role = LivingUrbanParkElementRole.PATH,
            pass = LivingUrbanParkDepthPass.PATH,
            geometry = LivingUrbanParkGeometry.CurvedPath(
                leftStart = ParkPoint(.28f, 1f),
                leftEdge = listOf(
                    ParkCubicSegment(ParkPoint(.47f, .93f), ParkPoint(.43f, .84f), ParkPoint(.38f, .78f)),
                    ParkCubicSegment(ParkPoint(.31f, .70f), ParkPoint(.42f, .64f), ParkPoint(.47f, .60f)),
                ),
                rightStart = ParkPoint(.54f, .60f),
                rightEdge = listOf(
                    ParkCubicSegment(ParkPoint(.58f, .66f), ParkPoint(.48f, .72f), ParkPoint(.54f, .78f)),
                    ParkCubicSegment(ParkPoint(.62f, .86f), ParkPoint(.55f, .94f), ParkPoint(.72f, 1f)),
                ),
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.ENTRANCE,
            role = LivingUrbanParkElementRole.ENTRANCE,
            pass = LivingUrbanParkDepthPass.FOREGROUND,
            geometry = LivingUrbanParkGeometry.Entrance(
                leftTop = ParkPoint(.36f, .765f),
                rightTop = ParkPoint(.57f, .765f),
                rightBottom = ParkPoint(.59f, .795f),
                leftBottom = ParkPoint(.33f, .795f),
            ),
        ),
    )
}

/** One Park scene and the exact shared weather decision used by every bundled world. */
class LivingUrbanParkComposition internal constructor(
    val weatherDecision: SceneWeatherDecision,
    internal val elements: List<LivingUrbanParkElementDeclaration>,
) {
    val selectedInstant: Instant get() = weatherDecision.selectedInstant
}

object LivingUrbanParkTheme {
    fun compose(weatherDecision: SceneWeatherDecision) = LivingUrbanParkComposition(
        weatherDecision = weatherDecision,
        elements = LivingUrbanParkSemanticInventory.elements.sortedBy { it.pass.backToFrontOrder },
    )
}
