package com.oxygen.weather.timeline.presentation.scene

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Debug
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidationResult
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidator
import java.io.File
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Ox55RendererLoadMemoryMeasurementTest {
    @Test
    fun measuresValidatedOliveDecodeAndTypedCanopyCandidateOnDevice() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val testContext = instrumentation.context
        val appContext = instrumentation.targetContext
        val stagedArchive = File(appContext.cacheDir, "ox55-first-city-canopy-olive-v2.zip")
        testContext.assets.open(OLIVE_ARCHIVE_ASSET).use { input ->
            stagedArchive.outputStream().use(input::copyTo)
        }

        val accepted = try {
            when (val result = ScenePackResourceValidator.validate(stagedArchive)) {
                is ScenePackResourceValidationResult.Accepted -> result
                is ScenePackResourceValidationResult.Rejected -> error("OX-91 rejected test pack: ${result.reason}")
            }
        } finally {
            stagedArchive.delete()
        }
        assertEquals("first-city-canopy-olive", accepted.manifest.packId)
        assertEquals("first-city.canopy.image-png", accepted.manifest.requiredCapabilities.single())
        val pngBytes = requireNotNull(accepted.assetsByPath[accepted.manifest.assetReference.path])

        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(pngBytes, 0, pngBytes.size, bounds)
        assertEquals(1024, bounds.outWidth)
        assertEquals(1536, bounds.outHeight)

        val zone = ZoneId.of("America/Chicago")
        val selected = ZonedDateTime.of(2026, 10, 28, 23, 0, 0, 0, zone).toInstant()
        val reading = SceneReading(
            selectedInstant = selected,
            weather = emptyMap(),
            intervals = emptyMap(),
            lightInterval = LightInterval(
                selected.minus(Duration.ofHours(1)), selected.plus(Duration.ofHours(1)), LightPhase.NIGHT,
            ),
        )
        val composition = FirstCityTheme.compose(reading, zone)

        System.gc()
        val nativeBaseline = Debug.getNativeHeapAllocatedSize()
        val javaBaseline = usedJavaHeapBytes()
        Debug.startAllocCounting()
        Debug.resetGlobalAllocSize()
        Debug.resetGlobalExternalAllocSize()
        val sampler = PeakMemorySampler().apply { start() }
        var bitmap: Bitmap? = null
        var candidate: MeasuredCanopyCandidate? = null
        val javaAllocated: Int
        val externalAllocated: Int
        try {
            val options = BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            bitmap = BitmapFactory.decodeByteArray(pngBytes, 0, pngBytes.size, options)
            assertNotNull("validated PNG must decode", bitmap)
            val image = requireNotNull(bitmap).asImageBitmap()
            candidate = MeasuredCanopyCandidate(composition.canopyRenderInput(), image)
            javaAllocated = Debug.getGlobalAllocSize()
            externalAllocated = Debug.getGlobalExternalAllocSize()
        } finally {
            sampler.finishSampling()
            Debug.stopAllocCounting()
        }

        val decoded = requireNotNull(bitmap)
        val measuredCandidate = requireNotNull(candidate)
        assertEquals(selected, measuredCandidate.canopyInput.selectedInstant)
        assertSame(decoded, measuredCandidate.image.asAndroidBitmap())

        val pixelBytes = decoded.allocationByteCount.toLong()
        val nativePeakDelta = (sampler.nativePeakBytes - nativeBaseline).coerceAtLeast(0)
        val javaPeakDelta = (sampler.javaPeakBytes - javaBaseline).coerceAtLeast(0)
        val encodedBytes = pngBytes.size.toLong()
        val retainedAssetAndPixels = encodedBytes + pixelBytes
        assertTrue("native heap peak should include the decoded bitmap", nativePeakDelta >= pixelBytes)
        val nativeWorkingPeak = nativePeakDelta - pixelBytes
        val candidateHeapPeak = maxOf(javaPeakDelta, javaAllocated.toLong())
        val measuredCandidateOverhead = nativeWorkingPeak + candidateHeapPeak
        val measuredLoadBytes = retainedAssetAndPixels + measuredCandidateOverhead
        val capBytes = 8_388_608L
        val counterTotal = javaAllocated.toLong() + externalAllocated.toLong()
        Log.i(
            TAG,
            "device=${android.os.Build.MODEL}; sdk=${android.os.Build.VERSION.SDK_INT}; " +
                "packId=${accepted.manifest.packId}; contentIdentity=${accepted.contentIdentity}; " +
                "assetDigest=${accepted.manifest.assetReference.sha256}; " +
                "assetBytes=$encodedBytes; bitmapBytes=$pixelBytes; " +
                "nativePeakDelta=$nativePeakDelta; javaPeakDelta=$javaPeakDelta; " +
                "nativeWorkingPeak=$nativeWorkingPeak; candidateHeapPeak=$candidateHeapPeak; " +
                "measuredCandidateOverhead=$measuredCandidateOverhead; " +
                "globalJavaAllocated=$javaAllocated; globalExternalAllocated=$externalAllocated; " +
                "retainedAssetAndPixels=$retainedAssetAndPixels; " +
                "measuredLoadBytes=$measuredLoadBytes; cap=$capBytes; " +
                "capHeadroom=${capBytes - measuredLoadBytes}; " +
                "candidateInstant=$selected",
        )
        assertEquals("ARGB_8888 output allocation must match fixed dimensions", 6_291_456L, pixelBytes)
        assertTrue("Android allocation counters should observe decode/candidate work", counterTotal > 0)
        assertTrue("measured candidate load must fit the 8 MiB cap", measuredLoadBytes <= capBytes)

        decoded.recycle()
    }

    private data class MeasuredCanopyCandidate(
        val canopyInput: FirstCityCanopyRenderInput,
        val image: ImageBitmap,
    )

    private class PeakMemorySampler : Thread("ox55-memory-sampler") {
        @Volatile var sampling = true
        @Volatile var nativePeakBytes = Debug.getNativeHeapAllocatedSize()
        @Volatile var javaPeakBytes = usedJavaHeapBytes()

        override fun run() {
            while (sampling) {
                val native = Debug.getNativeHeapAllocatedSize()
                if (native > nativePeakBytes) nativePeakBytes = native
                val java = usedJavaHeapBytes()
                if (java > javaPeakBytes) javaPeakBytes = java
                Thread.yield()
            }
        }

        fun finishSampling() {
            sampling = false
            join()
        }
    }

    private companion object {
        const val OLIVE_ARCHIVE_ASSET = "first-city-canopy-olive-v2.zip"
        const val TAG = "OX55MemoryMeasurement"

        fun usedJavaHeapBytes(): Long {
            val runtime = Runtime.getRuntime()
            return runtime.totalMemory() - runtime.freeMemory()
        }
    }
}
