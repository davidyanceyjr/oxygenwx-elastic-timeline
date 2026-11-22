package com.oxygen.weather.timeline.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.oxygen.weather.timeline.data.scenepack.AppOwnedThemeCompatibility
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeCatalogEntry
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeOrigin
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeValidity
import com.oxygen.weather.timeline.data.scenepack.ScenePackCurrentGeneration
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidationResult
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidator
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import java.io.File
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** A complete First City CANOPY candidate. The active scene changes only after this is ready. */
internal data class FirstCityCanopyCandidate(
    val image: ImageBitmap,
    val contentIdentity: String,
    val estimatedLoadBytes: Long,
)

internal object FirstCityCanopyCandidateLoader {
    internal const val INSTALLED_BASELINE_PACK_ID = "first-city-canopy-baseline"

    private const val OLIVE_PACK_ID = "first-city-canopy-olive"
    private const val OLIVE_ARCHIVE = "first-city-canopy-olive-v2.zip"
    private const val CAPABILITY = "first-city.canopy.image-png"
    private const val CAP_BYTES = 8_388_608L
    private const val RESERVE_BYTES = 524_288L

    suspend fun loadBundledOlive(context: Context): FirstCityCanopyCandidate? = withContext(Dispatchers.IO) {
        val staged = try { File.createTempFile("ox55-", ".zip", context.cacheDir) }
            catch (_: IOException) { return@withContext null }
        val result = try {
            context.assets.open(OLIVE_ARCHIVE).use { source ->
                staged.outputStream().use { target -> source.copyTo(target) }
            }
            ScenePackResourceValidator.validate(staged)
        } catch (_: IOException) {
            null
        } finally {
            staged.delete()
        }
        val accepted = result as? ScenePackResourceValidationResult.Accepted
            ?: return@withContext null
        prepare(accepted, OLIVE_PACK_ID)
    }

    suspend fun loadInstalledBaseline(
        entry: InstalledThemeCatalogEntry,
        resolve: (InstalledThemeCatalogEntry) -> ScenePackCurrentGeneration?,
    ): FirstCityCanopyCandidate? = withContext(Dispatchers.IO) {
        if (entry.id.value != INSTALLED_BASELINE_PACK_ID ||
            entry.origin != InstalledThemeOrigin.APP_OWNED ||
            entry.validity != InstalledThemeValidity.COMPATIBLE
        ) return@withContext null
        val generation = resolve(entry) ?: return@withContext null
        prepare(generation, entry)
    }

    /** Existing OX-55 bundled proof path. */
    internal fun prepare(
        accepted: ScenePackResourceValidationResult.Accepted,
    ): FirstCityCanopyCandidate? = prepare(accepted, OLIVE_PACK_ID)

    internal fun prepare(
        accepted: ScenePackResourceValidationResult.Accepted,
        expectedPackId: String,
    ): FirstCityCanopyCandidate? {
        val manifest = accepted.manifest
        val bytes = accepted.assetsByPath[manifest.assetReference.path] ?: return null
        return prepare(manifest, accepted.contentIdentity, bytes, expectedPackId)
    }

    internal fun prepare(
        generation: ScenePackCurrentGeneration,
        entry: InstalledThemeCatalogEntry,
    ): FirstCityCanopyCandidate? {
        val registration = generation.registration
        if (registration.packId != entry.id.value ||
            registration.displayName != entry.displayName ||
            registration.version != entry.version ||
            registration.compatibility != AppOwnedThemeCompatibility.COMPATIBLE
        ) return null
        return prepare(
            generation.manifest,
            generation.contentIdentity,
            generation.assetBytes,
            entry.id.value,
        )
    }

    internal fun withinBudget(candidate: FirstCityCanopyCandidate): Boolean =
        candidate.estimatedLoadBytes <= CAP_BYTES

    /** Read the validated PNG header and enforce the retained candidate budget before decode. */
    internal fun admit(bytes: ByteArray): Boolean = estimate(bytes)?.let { it <= CAP_BYTES } ?: false

    private fun prepare(
        manifest: com.oxygen.weather.timeline.data.scenepack.ScenePackManifest,
        contentIdentity: String,
        bytes: ByteArray,
        expectedPackId: String,
    ): FirstCityCanopyCandidate? {
        if (manifest.packId != expectedPackId ||
            manifest.worldDefinitionRef != "first-city" ||
            manifest.manifestVersion != 2 ||
            manifest.engineCompatibility != 1 ||
            manifest.rendererPolicyVersion != 1 ||
            manifest.requiredCapabilities != listOf(CAPABILITY) ||
            manifest.assetReference.mediaType != "image/png"
        ) return null

        val estimatedLoadBytes = estimate(bytes) ?: return null
        if (estimatedLoadBytes > CAP_BYTES) return null
        val bitmap = try { BitmapFactory.decodeByteArray(bytes, 0, bytes.size,
            BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.ARGB_8888
                inScaled = false
            }) } catch (_: RuntimeException) { null } ?: return null
        if (bitmap.width != FirstCityTheme.worldWidth ||
            bitmap.height != FirstCityTheme.worldHeight ||
            bitmap.config != Bitmap.Config.ARGB_8888
        ) {
            bitmap.recycle()
            return null
        }
        return FirstCityCanopyCandidate(bitmap.asImageBitmap(), contentIdentity, estimatedLoadBytes)
    }

    private fun estimate(bytes: ByteArray): Long? {
        if (bytes.size < 24) return null
        val width = pngInt(bytes, 16)
        val height = pngInt(bytes, 20)
        if (width != FirstCityTheme.worldWidth.toLong() ||
            height != FirstCityTheme.worldHeight.toLong()
        ) return null
        return try {
            val pixels = Math.multiplyExact(Math.multiplyExact(width, height), 4L)
            Math.addExact(Math.addExact(bytes.size.toLong(), pixels), RESERVE_BYTES)
        } catch (_: ArithmeticException) {
            null
        }
    }

    private fun pngInt(bytes: ByteArray, offset: Int): Long =
        ((bytes[offset].toLong() and 0xff) shl 24) or
            ((bytes[offset + 1].toLong() and 0xff) shl 16) or
            ((bytes[offset + 2].toLong() and 0xff) shl 8) or
            (bytes[offset + 3].toLong() and 0xff)
}
