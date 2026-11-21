package com.oxygen.weather.timeline.ui

import androidx.test.platform.app.InstrumentationRegistry
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidationResult
import com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidator
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class OlivePackCandidateTest {
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    private fun accepted(): ScenePackResourceValidationResult.Accepted {
        val file = File.createTempFile("ox55-test-", ".zip", context.cacheDir)
        try {
            context.assets.open("first-city-canopy-olive-v2.zip").use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            return ScenePackResourceValidator.validate(file) as ScenePackResourceValidationResult.Accepted
        } finally {
            file.delete()
        }
    }

    @Test fun acceptedArchivePreparesOneCanopyImageWithinBudget() {
        val validated = accepted()
        val bytes = validated.assetsByPath.getValue(validated.manifest.assetReference.path)
        assertEquals(1_386_266, bytes.size)
        assertTrue(OlivePackCandidateLoader.admit(bytes))
        val candidate = OlivePackCandidateLoader.prepare(validated)
        assertNotNull(candidate)
        assertEquals(1024, candidate!!.image.width)
        assertEquals(1536, candidate.image.height)
        assertEquals(validated.contentIdentity, candidate.contentIdentity)
        assertEquals(8_202_010L, candidate.estimatedLoadBytes)
        assertTrue(OlivePackCandidateLoader.withinBudget(candidate))
    }

    @Test fun unsupportedRequirementAndFailedDecodeDoNotPrepareCandidate() {
        val validated = accepted()
        assertNull(OlivePackCandidateLoader.prepare(validated.copy(
            manifest = validated.manifest.copy(requiredCapabilities = listOf("unknown.required")),
        )))
        assertNull(OlivePackCandidateLoader.prepare(validated.copy(
            manifest = validated.manifest.copy(rendererPolicyVersion = 2),
        )))
        val path = validated.manifest.assetReference.path
        val corrupt = validated.assetsByPath.getValue(path).copyOf().apply {
            fill(0, 24, size)
        }
        assertTrue(OlivePackCandidateLoader.admit(corrupt))
        assertNull(OlivePackCandidateLoader.prepare(validated.copy(assetsByPath = mapOf(path to corrupt))))
    }

    @Test fun budgetExcessRejectsBeforeDecode() {
        val validated = accepted()
        val bytes = validated.assetsByPath.getValue(validated.manifest.assetReference.path)
        assertFalse(OlivePackCandidateLoader.admit(bytes.copyOf(1_573_000)))
    }
}
