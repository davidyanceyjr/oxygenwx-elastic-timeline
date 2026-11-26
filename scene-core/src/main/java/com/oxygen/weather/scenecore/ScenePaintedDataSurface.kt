package com.oxygen.weather.scenecore

import android.graphics.BitmapShader
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.ColorMatrixColorFilter
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.IntSize
import kotlin.math.hypot
import kotlin.math.roundToInt

/** A point normalized to the source image of a theme-owned scene plane. */
public data class ScenePlanePoint(public val x: Float, public val y: Float) {
    init {
        require(x.isFinite() && y.isFinite())
    }
}

/** A clockwise four-corner surface or text region in normalized plane coordinates. */
public data class ScenePlaneQuadrilateral(
    public val topLeft: ScenePlanePoint,
    public val topRight: ScenePlanePoint,
    public val bottomRight: ScenePlanePoint,
    public val bottomLeft: ScenePlanePoint,
)

/** Theme-owned face geometry and the inset quad used to fit painted data. */
public data class ScenePaintedSurfaceGeometry(
    public val plane: ScenePlane,
    public val face: ScenePlaneQuadrilateral,
    public val textRegion: ScenePlaneQuadrilateral,
)

/** Theme-provided typography, pigment texture, tint, and surface lighting. */
public class ScenePaintedDataSurfaceStyle(
    public val typeface: Typeface,
    public val maxTextSize: Float,
    public val minTextSize: Float,
    public val maxLines: Int,
    public val lineSpacingMultiplier: Float,
    /** Pigment stroke width in source-plane pixels, expanded around the measured glyph paths. */
    public val pigmentStrokeWidth: Float,
    /** A full-plane texture sampled through the same center-crop transform as its scene plane. */
    public val pigmentTexture: ImageBitmap,
    public val pigmentTint: Color,
    public val pigmentAlpha: Float,
    public val shadowColor: Color,
    /** Surface-local offset in source-plane pixels. */
    public val shadowOffset: Offset,
    public val highlightColor: Color,
    /** Surface-local offset in source-plane pixels. */
    public val highlightOffset: Offset,
    /** Surface-local stroke width in source-plane pixels. */
    public val highlightWidth: Float,
) {
    init {
        require(maxTextSize.isFinite() && maxTextSize > 0f)
        require(minTextSize.isFinite() && minTextSize > 0f && minTextSize <= maxTextSize)
        require(maxLines > 0)
        require(lineSpacingMultiplier.isFinite() && lineSpacingMultiplier >= 1f)
        require(pigmentStrokeWidth.isFinite() && pigmentStrokeWidth >= 0f)
        require(pigmentAlpha.isFinite() && pigmentAlpha in 0f..1f)
        require(shadowOffset.x.isFinite() && shadowOffset.y.isFinite())
        require(highlightOffset.x.isFinite() && highlightOffset.y.isFinite())
        require(highlightWidth.isFinite() && highlightWidth >= 0f)
    }
}

/** One theme-authored vector path that receives the shared painted-surface treatment. */
public class ScenePaintedPathLayer(
    path: Path,
    public val pigmentTint: Color,
    public val alpha: Float = 1f,
    public val strokeOnly: Boolean = false,
) {
    internal val path: Path = Path(path)

    init {
        require(alpha.isFinite() && alpha in 0f..1f)
    }
}

/**
 * A group of vector paths laid out in one local coordinate space and projected together onto a
 * theme-owned painted surface. Each path may have its own pigment tint; projection, clipping,
 * texture sampling, worn expansion, and surface lighting are shared by the complete group.
 */
public class ScenePaintedPathSurface(
    sourcePlaneSize: IntSize,
    public val localWidth: Float,
    public val localHeight: Float,
    public val geometry: ScenePaintedSurfaceGeometry,
    public val style: ScenePaintedDataSurfaceStyle,
    layers: List<ScenePaintedPathLayer>,
) {
    internal val layers: List<PreparedPaintedPathLayer>
    internal val pigmentShader: BitmapShader?
    internal val shadowPaint: Paint
    internal val highlightPaint: Paint

    init {
        require(sourcePlaneSize.width > 0 && sourcePlaneSize.height > 0)
        require(localWidth.isFinite() && localWidth > 0f)
        require(localHeight.isFinite() && localHeight > 0f)
        val surfaceStyle = style
        val activeLayers = layers.filterNot { it.path.isEmpty }
        this.layers = activeLayers.map { layer ->
            PreparedPaintedPathLayer(
                path = layer.path,
                pigmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    colorFilter = pigmentColorFilter(layer.pigmentTint)
                    alpha = (surfaceStyle.pigmentAlpha * layer.alpha * .45f * 255f).roundToInt()
                    this.style = if (layer.strokeOnly) Paint.Style.STROKE else Paint.Style.FILL_AND_STROKE
                    strokeJoin = Paint.Join.ROUND
                    strokeCap = Paint.Cap.ROUND
                },
                pigmentTintPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = layer.pigmentTint.toArgb()
                    alpha = (surfaceStyle.pigmentAlpha * layer.alpha * .86f * 255f).roundToInt()
                    this.style = if (layer.strokeOnly) Paint.Style.STROKE else Paint.Style.FILL_AND_STROKE
                    strokeJoin = Paint.Join.ROUND
                    strokeCap = Paint.Cap.ROUND
                },
            )
        }
        pigmentShader = this.layers.takeIf { it.isNotEmpty() }?.let {
            BitmapShader(
                surfaceStyle.pigmentTexture.asAndroidBitmap(),
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP,
            )
        }
        this.layers.forEach { it.pigmentPaint.shader = pigmentShader }
        shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = surfaceStyle.shadowColor.toArgb()
            this.style = Paint.Style.FILL
        }
        highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = surfaceStyle.highlightColor.toArgb()
            this.style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }
}

internal class PreparedPaintedPathLayer(
    val path: Path,
    val pigmentPaint: Paint,
    val pigmentTintPaint: Paint,
)

/**
 * A dynamic string laid out once against theme-owned surface geometry.
 *
 * The value stays data, not artwork. The core measures and wraps it to word boundaries, then maps
 * its glyph path to the supplied text-region quadrilateral when drawn. Blank values have no glyph
 * path and draw nothing.
 */
public class ScenePaintedDataSurface(
    public val value: String?,
    sourcePlaneSize: IntSize,
    public val geometry: ScenePaintedSurfaceGeometry,
    public val style: ScenePaintedDataSurfaceStyle,
) {
    internal val layoutWidth: Float
    internal val layoutHeight: Float
    internal val glyphPath: Path?
    internal val pathSurface: ScenePaintedPathSurface?

    init {
        require(sourcePlaneSize.width > 0 && sourcePlaneSize.height > 0)
        val textRegion = geometry.textRegion.toSourcePoints(sourcePlaneSize)
        layoutWidth = (distance(textRegion[0], textRegion[1]) + distance(textRegion[3], textRegion[2])) / 2f
        layoutHeight = (distance(textRegion[0], textRegion[3]) + distance(textRegion[1], textRegion[2])) / 2f
        require(layoutWidth > 0f && layoutHeight > 0f)
        val surfaceStyle = style

        glyphPath = value?.takeIf(String::isNotBlank)?.let { text ->
            createGlyphPath(text, layoutWidth, layoutHeight, surfaceStyle)
        }
        pathSurface = glyphPath?.let { path ->
            ScenePaintedPathSurface(
                sourcePlaneSize = sourcePlaneSize,
                localWidth = layoutWidth,
                localHeight = layoutHeight,
                geometry = geometry,
                style = surfaceStyle,
                layers = listOf(ScenePaintedPathLayer(path, surfaceStyle.pigmentTint)),
            )
        }
    }
}

/** Creates a reusable text glyph path, centered and fit to the requested local bounds. */
public fun createScenePaintedGlyphPath(
    value: String?,
    width: Float,
    height: Float,
    style: ScenePaintedDataSurfaceStyle,
): Path? {
    require(width.isFinite() && width > 0f)
    require(height.isFinite() && height > 0f)
    return value?.takeIf(String::isNotBlank)?.let { createGlyphPath(it, width, height, style) }
}

/** Draws a dynamic painted value after its supporting plane, preserving that plane's crop. */
public fun DrawScope.drawScenePaintedDataSurface(
    stack: ScenePlaneStack,
    transform: ScenePlaneTransform,
    surface: ScenePaintedDataSurface,
) {
    val pathSurface = surface.pathSurface ?: return
    drawScenePaintedPathSurface(stack, transform, pathSurface)
}

/** Draws theme-authored vector paths through the same projection and pigment pipeline as text. */
public fun DrawScope.drawScenePaintedPathSurface(
    stack: ScenePlaneStack,
    transform: ScenePlaneTransform,
    surface: ScenePaintedPathSurface,
) {
    if (surface.layers.isEmpty()) return
    val shader = surface.pigmentShader ?: return
    val sourceImage = stack.image(surface.geometry.plane)
    val sourceWidth = sourceImage.width.toFloat()
    val sourceHeight = sourceImage.height.toFloat()
    val destinationWidth = transform.destinationSize.width.toFloat()
    val destinationHeight = transform.destinationSize.height.toFloat()
    if (sourceWidth <= 0f || sourceHeight <= 0f || destinationWidth <= 0f || destinationHeight <= 0f) return

    val scaleX = destinationWidth / sourceWidth
    val scaleY = destinationHeight / sourceHeight
    val facePath = surface.geometry.face.toViewportPath(transform)
    val textQuad = surface.geometry.textRegion.toViewportPoints(transform)
    val sourceQuad = floatArrayOf(
        0f, 0f,
        surface.localWidth, 0f,
        surface.localWidth, surface.localHeight,
        0f, surface.localHeight,
    )
    val destinationQuad = textQuad.toFloatArray()
    val projection = Matrix()
    if (!projection.setPolyToPoly(sourceQuad, 0, destinationQuad, 0, 4)) return
    val screenPaths = surface.layers.map { layer ->
        Path().also { layer.path.transform(projection, it) }
    }
    val combinedPath = Path().apply { screenPaths.forEach(::addPath) }

    val textureScaleX = surface.style.pigmentTexture.width.toFloat() / destinationWidth
    val textureScaleY = surface.style.pigmentTexture.height.toFloat() / destinationHeight
    val textureMatrix = Matrix().apply {
        setValues(
            floatArrayOf(
                textureScaleX, 0f, -transform.destinationOffset.x * textureScaleX,
                0f, textureScaleY, -transform.destinationOffset.y * textureScaleY,
                0f, 0f, 1f,
            ),
        )
    }
    shader.setLocalMatrix(textureMatrix)

    val canvas = drawContext.canvas.nativeCanvas
    val saveCount = canvas.save()
    canvas.clipPath(facePath)

    val shadowPath = Path(combinedPath)
    val shadowMatrix = Matrix().apply {
        setTranslate(
            surface.style.shadowOffset.x * scaleX,
            surface.style.shadowOffset.y * scaleY,
        )
    }
    shadowPath.transform(shadowMatrix)
    canvas.drawPath(shadowPath, surface.shadowPaint)
    val pigmentStrokeWidth = surface.style.pigmentStrokeWidth * (scaleX + scaleY) / 2f
    surface.layers.zip(screenPaths).forEach { (layer, screenPath) ->
        layer.pigmentPaint.strokeWidth = pigmentStrokeWidth
        layer.pigmentTintPaint.strokeWidth = pigmentStrokeWidth
        canvas.drawPath(screenPath, layer.pigmentPaint)
        canvas.drawPath(screenPath, layer.pigmentTintPaint)
    }

    if (surface.style.highlightWidth > 0f && surface.style.highlightColor.alpha > 0f) {
        val highlightPath = Path(combinedPath)
        val highlightMatrix = Matrix().apply {
            setTranslate(
                surface.style.highlightOffset.x * scaleX,
                surface.style.highlightOffset.y * scaleY,
            )
        }
        highlightPath.transform(highlightMatrix)
        surface.highlightPaint.strokeWidth = surface.style.highlightWidth * (scaleX + scaleY) / 2f
        canvas.drawPath(highlightPath, surface.highlightPaint)
    }

    canvas.restoreToCount(saveCount)
}

private fun createGlyphPath(
    value: String,
    width: Float,
    height: Float,
    style: ScenePaintedDataSurfaceStyle,
): Path? {
    val normalized = value.trim().replace(Regex("\\s+"), " ")
    if (normalized.isEmpty()) return null

    var textSize = style.maxTextSize
    while (true) {
        val sizeForPaint = textSize
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = style.typeface
            this.textSize = sizeForPaint
            textAlign = Paint.Align.LEFT
            isSubpixelText = true
        }
        val lines = wrapWords(normalized, width, paint)
        if (lines.size <= style.maxLines && lines.all { paint.measureText(it) <= width }) {
            val lineHeight = textSize * style.lineSpacingMultiplier
            if (lineHeight * lines.size <= height) {
                val path = Path()
                val top = (height - lineHeight * lines.size) / 2f
                lines.forEachIndexed { index, line ->
                    val lineWidth = paint.measureText(line)
                    val linePath = Path()
                    paint.getTextPath(line, 0, line.length, 0f, 0f, linePath)
                    val bounds = android.graphics.RectF()
                    linePath.computeBounds(bounds, true)
                    val baseline = top + index * lineHeight +
                        (lineHeight - bounds.height()) / 2f - bounds.top
                    val translate = Matrix().apply {
                        setTranslate((width - lineWidth) / 2f, baseline)
                    }
                    val positionedLine = Path()
                    linePath.transform(translate, positionedLine)
                    path.addPath(positionedLine)
                }
                if (!path.isEmpty) return path
            }
        }
        if (textSize <= style.minTextSize) return null
        textSize = maxOf(style.minTextSize, textSize - 1f)
    }
}

/** Tints the plane's luminance so the supplied pigment hue remains visible in textured lettering. */
private fun pigmentColorFilter(tint: Color): ColorMatrixColorFilter {
    val red = tint.red
    val green = tint.green
    val blue = tint.blue
    return ColorMatrixColorFilter(
        floatArrayOf(
            .299f * red, .587f * red, .114f * red, 0f, 0f,
            .299f * green, .587f * green, .114f * green, 0f, 0f,
            .299f * blue, .587f * blue, .114f * blue, 0f, 0f,
            0f, 0f, 0f, 1f, 0f,
        ),
    )
}

private fun wrapWords(value: String, width: Float, paint: Paint): List<String> {
    val lines = mutableListOf<String>()
    var current = ""
    value.split(' ').forEach { word ->
        val candidate = if (current.isEmpty()) word else "$current $word"
        if (current.isNotEmpty() && paint.measureText(candidate) > width) {
            lines += current
            current = word
        } else {
            current = candidate
        }
    }
    if (current.isNotEmpty()) lines += current
    return lines
}

private fun ScenePlaneQuadrilateral.toSourcePoints(sourceSize: IntSize): List<Point> = listOf(
    Point(topLeft.x * sourceSize.width, topLeft.y * sourceSize.height),
    Point(topRight.x * sourceSize.width, topRight.y * sourceSize.height),
    Point(bottomRight.x * sourceSize.width, bottomRight.y * sourceSize.height),
    Point(bottomLeft.x * sourceSize.width, bottomLeft.y * sourceSize.height),
)

private fun ScenePlaneQuadrilateral.toViewportPoints(transform: ScenePlaneTransform): List<Point> = listOf(
    viewportPoint(topLeft, transform),
    viewportPoint(topRight, transform),
    viewportPoint(bottomRight, transform),
    viewportPoint(bottomLeft, transform),
)

private fun ScenePlaneQuadrilateral.toViewportPath(transform: ScenePlaneTransform): Path {
    val points = toViewportPoints(transform)
    return Path().apply {
        moveTo(points[0].x, points[0].y)
        points.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
}

private fun viewportPoint(point: ScenePlanePoint, transform: ScenePlaneTransform): Point = Point(
    transform.destinationOffset.x + point.x * transform.destinationSize.width,
    transform.destinationOffset.y + point.y * transform.destinationSize.height,
)

private fun List<Point>.toFloatArray(): FloatArray =
    flatMap { listOf(it.x, it.y) }.toFloatArray()

private fun distance(first: Point, second: Point): Float = hypot(second.x - first.x, second.y - first.y)

private data class Point(val x: Float, val y: Float)

private fun ScenePlaneStack.image(plane: ScenePlane): ImageBitmap = when (plane) {
    ScenePlane.BACKGROUND -> background
    ScenePlane.MIDDLEGROUND -> middleground
    ScenePlane.FOREGROUND -> foreground
}
