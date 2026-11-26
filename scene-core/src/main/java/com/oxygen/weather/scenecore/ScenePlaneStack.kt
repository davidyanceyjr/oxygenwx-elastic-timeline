package com.oxygen.weather.scenecore

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

/** Theme-owned image planes that share one viewport-covering center-crop transform. */
public class ScenePlaneStack(
    public val background: ImageBitmap,
    public val middleground: ImageBitmap,
    public val foreground: ImageBitmap,
) {
    /** Calculates the shared destination from the Background plane and the active viewport. */
    public fun centerCropTransform(viewport: Size): ScenePlaneTransform {
        val scale = maxOf(
            viewport.width / background.width.toFloat(),
            viewport.height / background.height.toFloat(),
        )
        val destinationWidth = (background.width * scale).roundToInt()
        val destinationHeight = (background.height * scale).roundToInt()
        val viewportWidth = viewport.width.roundToInt()
        val viewportHeight = viewport.height.roundToInt()
        return ScenePlaneTransform(
            destinationOffset = IntOffset(
                (viewportWidth - destinationWidth) / 2,
                (viewportHeight - destinationHeight) / 2,
            ),
            destinationSize = IntSize(destinationWidth, destinationHeight),
        )
    }
}

/** The depth positions available in a [ScenePlaneStack]. */
public enum class ScenePlane { BACKGROUND, MIDDLEGROUND, FOREGROUND }

/** Shared viewport destination used to keep every plane and aligned element registered. */
public class ScenePlaneTransform internal constructor(
    public val destinationOffset: IntOffset,
    public val destinationSize: IntSize,
)

/** Draws one theme-owned plane with the shared destination while retaining its source alpha. */
public fun DrawScope.drawScenePlane(
    stack: ScenePlaneStack,
    plane: ScenePlane,
    transform: ScenePlaneTransform,
) {
    val image = when (plane) {
        ScenePlane.BACKGROUND -> stack.background
        ScenePlane.MIDDLEGROUND -> stack.middleground
        ScenePlane.FOREGROUND -> stack.foreground
    }
    drawImage(
        image = image,
        srcOffset = IntOffset.Zero,
        srcSize = IntSize(image.width, image.height),
        dstOffset = transform.destinationOffset,
        dstSize = transform.destinationSize,
        filterQuality = FilterQuality.Medium,
    )
}
