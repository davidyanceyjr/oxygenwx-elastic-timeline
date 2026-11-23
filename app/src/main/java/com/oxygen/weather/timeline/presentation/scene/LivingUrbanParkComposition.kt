package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

internal enum class LivingUrbanParkElementId {
    DISTANT_BACKDROP,
    MURAL_WALL,
    STREETCAR_SHELTER,
    TREE_LEFT,
    TREE_RIGHT,
    CURVED_PATH,
    ENTRANCE,
    BENCH_LEFT,
    BENCH_RIGHT,
}

internal enum class LivingUrbanParkElementRole {
    DISTANT_BACKDROP,
    MURAL_WALL,
    STREETCAR_SHELTER,
    VEGETATION,
    PATH,
    ENTRANCE,
    BENCH,
}

internal enum class LivingUrbanParkDepthPass(val backToFrontOrder: Int) {
    BACKGROUND(0),
    MURAL_WALL(1),
    PARK_LANDMARKS(2),
    PATH(3),
    FOREGROUND(4),
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

/** Normalized scene-relative rectangle size for a Park landmark. */
internal data class ParkElementSize(val width: Float, val height: Float) {
    init {
        require(width.isFinite() && width > 0f && width <= 1f)
        require(height.isFinite() && height > 0f && height <= 1f)
    }
}

internal data class ParkElementBounds(
    val topLeft: ParkPoint,
    val bottomRight: ParkPoint,
) {
    init {
        require(bottomRight.x > topLeft.x)
        require(bottomRight.y > topLeft.y)
    }
}

/**
 * A Park-local landmark rectangle. Position and size use normalized scene coordinates;
 * anchor is the normalized point within the rectangle attached to position.
 */
internal data class ParkElementGeometry(
    val position: ParkPoint,
    val size: ParkElementSize,
    val anchor: ParkPoint,
) {
    val bounds: ParkElementBounds
        get() {
            val left = position.x - anchor.x * size.width
            val top = position.y - anchor.y * size.height
            return ParkElementBounds(
                topLeft = ParkPoint(left, top),
                bottomRight = ParkPoint(left + size.width, top + size.height),
            )
        }

    init {
        // ParkPoint construction in the derived value also enforces the scene's 0..1 bounds.
        bounds
    }
}

/** A four-corner Park-local plane expressed in normalized scene coordinates. */
internal data class ParkQuadrilateral(
    val topLeft: ParkPoint,
    val topRight: ParkPoint,
    val bottomRight: ParkPoint,
    val bottomLeft: ParkPoint,
)

internal enum class LivingUrbanParkDataSurfaceId {
    MURAL,
}

/** Opaque Park-owned slot identity; OX-12 supplies the selected-reading semantics. */
internal enum class LivingUrbanParkExactBindingReference {
    CANONICAL_SELECTED_VALUE,
}

internal data class LivingUrbanParkDataSurfaceDeclaration(
    val id: LivingUrbanParkDataSurfaceId,
    val plane: ParkQuadrilateral,
    val anchor: ParkPoint,
    val reservedRegion: ParkQuadrilateral,
    val bindingReference: LivingUrbanParkExactBindingReference,
)

internal sealed interface LivingUrbanParkGeometry {
    data class MuralWall(
        val surfaceId: LivingUrbanParkDataSurfaceId,
    ) : LivingUrbanParkGeometry

    data class Tree(val frame: ParkElementGeometry) : LivingUrbanParkGeometry

    data class Bench(val frame: ParkElementGeometry) : LivingUrbanParkGeometry

    data class StreetcarShelter(val frame: ParkElementGeometry) : LivingUrbanParkGeometry

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
    val dataSurface: LivingUrbanParkDataSurfaceDeclaration? = null,
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
            id = LivingUrbanParkElementId.MURAL_WALL,
            role = LivingUrbanParkElementRole.MURAL_WALL,
            pass = LivingUrbanParkDepthPass.MURAL_WALL,
            geometry = LivingUrbanParkGeometry.MuralWall(LivingUrbanParkDataSurfaceId.MURAL),
            dataSurface = LivingUrbanParkDataSurfaceDeclaration(
                id = LivingUrbanParkDataSurfaceId.MURAL,
                plane = ParkQuadrilateral(
                    topLeft = ParkPoint(.02f, .35f),
                    topRight = ParkPoint(.18f, .33f),
                    bottomRight = ParkPoint(.18f, .56f),
                    bottomLeft = ParkPoint(.02f, .58f),
                ),
                anchor = ParkPoint(.02f, .58f),
                reservedRegion = ParkQuadrilateral(
                    topLeft = ParkPoint(.05f, .39f),
                    topRight = ParkPoint(.15f, .38f),
                    bottomRight = ParkPoint(.15f, .50f),
                    bottomLeft = ParkPoint(.05f, .51f),
                ),
                bindingReference = LivingUrbanParkExactBindingReference.CANONICAL_SELECTED_VALUE,
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.STREETCAR_SHELTER,
            role = LivingUrbanParkElementRole.STREETCAR_SHELTER,
            pass = LivingUrbanParkDepthPass.PARK_LANDMARKS,
            geometry = LivingUrbanParkGeometry.StreetcarShelter(
                ParkElementGeometry(
                    // The approved OX-172 art places this frame at x=122,y=244,w=82,h=83
                    // in its 240x570 Park viewBox; retain that coarse anchor proportionally.
                    position = ParkPoint(.679167f, .573684f),
                    size = ParkElementSize(.341667f, .145614f),
                    anchor = ParkPoint(.5f, 1f),
                ),
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.TREE_LEFT,
            role = LivingUrbanParkElementRole.VEGETATION,
            pass = LivingUrbanParkDepthPass.PARK_LANDMARKS,
            geometry = LivingUrbanParkGeometry.Tree(
                ParkElementGeometry(
                    position = ParkPoint(.115f, .825f),
                    size = ParkElementSize(.20f, .16f),
                    anchor = ParkPoint(.5f, 1f),
                ),
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.TREE_RIGHT,
            role = LivingUrbanParkElementRole.VEGETATION,
            pass = LivingUrbanParkDepthPass.PARK_LANDMARKS,
            geometry = LivingUrbanParkGeometry.Tree(
                ParkElementGeometry(
                    position = ParkPoint(.885f, .68f),
                    size = ParkElementSize(.20f, .16f),
                    anchor = ParkPoint(.5f, 1f),
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
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.BENCH_LEFT,
            role = LivingUrbanParkElementRole.BENCH,
            pass = LivingUrbanParkDepthPass.FOREGROUND,
            geometry = LivingUrbanParkGeometry.Bench(
                ParkElementGeometry(
                    position = ParkPoint(.145f, .835f),
                    size = ParkElementSize(.28f, .065f),
                    anchor = ParkPoint(.5f, 1f),
                ),
            ),
        ),
        LivingUrbanParkElementDeclaration(
            id = LivingUrbanParkElementId.BENCH_RIGHT,
            role = LivingUrbanParkElementRole.BENCH,
            pass = LivingUrbanParkDepthPass.FOREGROUND,
            geometry = LivingUrbanParkGeometry.Bench(
                ParkElementGeometry(
                    position = ParkPoint(.855f, .835f),
                    size = ParkElementSize(.28f, .065f),
                    anchor = ParkPoint(.5f, 1f),
                ),
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
