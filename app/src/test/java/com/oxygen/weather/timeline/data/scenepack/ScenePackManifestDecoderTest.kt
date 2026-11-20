package com.oxygen.weather.timeline.data.scenepack

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile
import org.junit.Assume.assumeTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ScenePackManifestDecoderTest {
    @Test
    fun decodesTheSupportedManifestDeclaration() {
        val manifest = accepted(validManifest().toByteArray())

        assertEquals("first-city-canopy-baseline", manifest.packId)
        assertEquals(2, manifest.manifestVersion)
        assertEquals("first-city", manifest.worldDefinitionRef)
        assertEquals(1, manifest.engineCompatibility)
        assertEquals(1, manifest.rendererPolicyVersion)
        assertEquals(listOf("first-city.canopy.image-png"), manifest.requiredCapabilities)
        assertEquals("first-city-canopy-baseline", manifest.assetReference.refId)
        assertEquals("assets/first-city/canopy/baseline.png", manifest.assetReference.path)
        assertEquals("image/png", manifest.assetReference.mediaType)
        assertEquals("480dd857a7aa4ed7d3da62c991cf04056a364fbf04554027d9f6af6628791c89", manifest.assetReference.sha256)
    }

    @Test
    fun decodesManifestFromTheGeneratedPackWhenArtifactIsProvided() {
        val artifactPath = System.getenv(ARTIFACT_ENV)
        assumeTrue("Set $ARTIFACT_ENV to run the artifact-backed acceptance case", !artifactPath.isNullOrBlank())

        ZipFile(artifactPath!!).use { zip ->
            val entry = zip.getEntry("manifest.json")
            assertTrue("The generated pack must contain a root manifest.json", entry != null)
            zip.getInputStream(entry).use { input ->
                val manifest = accepted(input)
                assertEquals("first-city-canopy-baseline", manifest.packId)
                assertEquals("assets/first-city/canopy/baseline.png", manifest.assetReference.path)
            }
        }
    }

    @Test
    fun rejectsMalformedDuplicateAndTrailingJson() {
        reject("{".toByteArray(), ScenePackManifestRejection.MALFORMED_JSON)
        reject(
            validManifest().replaceFirst("\"packId\":", "\"packId\":\"duplicate\",\"packId\":").toByteArray(),
            ScenePackManifestRejection.MALFORMED_JSON,
        )
        reject(
            validManifest().replaceFirst("\"refId\":", "\"refId\":\"duplicate\",\"refId\":").toByteArray(),
            ScenePackManifestRejection.MALFORMED_JSON,
        )
        reject((validManifest() + " {} ").toByteArray(), ScenePackManifestRejection.MALFORMED_JSON)
    }

    @Test
    fun rejectsWrongRootShapeAndMissingExtraOrWrongTypeFields() {
        reject("[]".toByteArray(), ScenePackManifestRejection.INVALID_MANIFEST)

        val missing = validRoot().apply { remove("packId") }
        reject(json(missing), ScenePackManifestRejection.INVALID_MANIFEST)

        val extra = validRoot().apply { put("unexpected", "value") }
        reject(json(extra), ScenePackManifestRejection.INVALID_MANIFEST)

        val wrongPackIdType = validRoot().apply { put("packId", 9) }
        reject(json(wrongPackIdType), ScenePackManifestRejection.INVALID_MANIFEST)

        val wrongVersionType = validRoot().apply { put("manifestVersion", "2") }
        reject(json(wrongVersionType), ScenePackManifestRejection.INVALID_MANIFEST)

        val missingAssetField = validRoot().apply { assetObject().remove("sha256") }
        reject(json(missingAssetField), ScenePackManifestRejection.INVALID_MANIFEST)

        val extraAssetField = validRoot().apply { assetObject().put("unexpected", "value") }
        reject(json(extraAssetField), ScenePackManifestRejection.INVALID_MANIFEST)

        val wrongAssetFieldType = validRoot().apply { assetObject().put("path", 3) }
        reject(json(wrongAssetFieldType), ScenePackManifestRejection.INVALID_MANIFEST)
    }

    @Test
    fun rejectsInvalidPackIdentityAndWorldReference() {
        val invalidPackId = validRoot().apply { put("packId", "First_City") }
        reject(json(invalidPackId), ScenePackManifestRejection.INVALID_MANIFEST)

        val unsupportedWorld = validRoot().apply { put("worldDefinitionRef", "minimal-open-landscape") }
        reject(json(unsupportedWorld), ScenePackManifestRejection.INVALID_MANIFEST)
    }

    @Test
    fun distinguishesUnsupportedVersionsFromWrongJsonTypes() {
        listOf(
            "manifestVersion" to 3,
            "engineCompatibility" to 2,
            "rendererPolicyVersion" to 2,
        ).forEach { (field, value) ->
            val changed = validRoot().apply { put(field, value) }
            reject(json(changed), ScenePackManifestRejection.UNSUPPORTED_VERSION, field)
        }
    }

    @Test
    fun distinguishesUnsupportedCapabilitiesFromInvalidCapabilityShape() {
        val unsupported = validRoot().apply {
            set<com.fasterxml.jackson.databind.JsonNode>(
                "requiredCapabilities",
                mapper.createArrayNode().add("unknown.capability"),
            )
        }
        reject(json(unsupported), ScenePackManifestRejection.UNSUPPORTED_CAPABILITY)

        val multiple = validRoot().apply {
            set<com.fasterxml.jackson.databind.JsonNode>(
                "requiredCapabilities",
                mapper.createArrayNode().add("first-city.canopy.image-png").add("unknown.capability"),
            )
        }
        reject(json(multiple), ScenePackManifestRejection.INVALID_MANIFEST)

        val wrongItemType = validRoot().apply {
            set<com.fasterxml.jackson.databind.JsonNode>(
                "requiredCapabilities",
                mapper.createArrayNode().add(5),
            )
        }
        reject(json(wrongItemType), ScenePackManifestRejection.INVALID_MANIFEST)
    }

    @Test
    fun rejectsInvalidAssetReferenceValues() {
        val emptyRefId = validRoot().apply { assetObject().put("refId", "") }
        reject(json(emptyRefId), ScenePackManifestRejection.INVALID_MANIFEST)

        val unsupportedMediaType = validRoot().apply { assetObject().put("mediaType", "image/jpeg") }
        reject(json(unsupportedMediaType), ScenePackManifestRejection.INVALID_MANIFEST)

        val uppercaseSha = validRoot().apply {
            assetObject().put("sha256", "A".repeat(64))
        }
        reject(json(uppercaseSha), ScenePackManifestRejection.INVALID_MANIFEST)
    }

    @Test
    fun rejectsEveryNoncanonicalAssetPathFormAsInvalidManifest() {
        val invalidPaths = listOf(
            "",
            "/assets/first-city/canopy/baseline.png",
            "C:/assets/first-city/canopy/baseline.png",
            "https://example.test/baseline.png",
            "assets\\first-city\\canopy\\baseline.png",
            "assets//baseline.png",
            "assets/first-city/canopy/baseline.png/",
            "assets/./baseline.png",
            "assets/../baseline.png",
        )

        invalidPaths.forEach { path ->
            val changed = validRoot().apply { assetObject().put("path", path) }
            reject(json(changed), ScenePackManifestRejection.INVALID_MANIFEST, "path=$path")
        }
    }

    @Test
    fun rejectsManifestByteNestingAndTokenLimitOverruns() {
        val oversized = validManifest().toByteArray() + ByteArray(
            ScenePackManifestDecoder.MAX_MANIFEST_BYTES + 1,
        ) { ' '.code.toByte() }
        reject(oversized, ScenePackManifestRejection.LIMIT_EXCEEDED)

        reject(
            "{\"x\":[[[[[]]]]]}".toByteArray(),
            ScenePackManifestRejection.LIMIT_EXCEEDED,
        )

        val tooManyTokens = mapper.writeValueAsBytes(List(130) { 0 })
        reject(tooManyTokens, ScenePackManifestRejection.LIMIT_EXCEEDED)
    }

    @Test
    fun reportsStreamReadFailureWithoutParsingPartialBytes() {
        val input = FailingAfterPrefixInputStream(validManifest().toByteArray())
        val result = ScenePackManifestDecoder.decode(input)

        assertEquals(
            ScenePackManifestRejection.INPUT_READ_FAILURE,
            (result as ScenePackManifestDecodeResult.Rejected).reason,
        )
    }

    @Test
    fun leavesTheCallerOwnedInputStreamOpen() {
        val input = CloseTrackingInputStream(validManifest().toByteArray())

        accepted(input)

        assertFalse(input.wasClosed)
    }

    private fun accepted(bytes: ByteArray): ScenePackManifest =
        accepted(ByteArrayInputStream(bytes))

    private fun accepted(input: InputStream): ScenePackManifest =
        when (val result = ScenePackManifestDecoder.decode(input)) {
            is ScenePackManifestDecodeResult.Accepted -> result.manifest
            is ScenePackManifestDecodeResult.Rejected -> error("Expected accepted manifest, got ${result.reason}")
        }

    private fun reject(bytes: ByteArray, reason: ScenePackManifestRejection, message: String = "") {
        val result = ScenePackManifestDecoder.decode(ByteArrayInputStream(bytes))
        assertTrue("Expected rejection $reason $message, got $result", result is ScenePackManifestDecodeResult.Rejected)
        assertEquals(message, reason, (result as ScenePackManifestDecodeResult.Rejected).reason)
    }

    private fun validRoot(): ObjectNode = mapper.readTree(validManifest()) as ObjectNode

    private fun ObjectNode.assetObject(): ObjectNode = this["assetReferences"][0] as ObjectNode

    private fun json(root: ObjectNode): ByteArray = mapper.writeValueAsBytes(root)

    private fun validManifest(): String =
        """
        {
          "packId": "first-city-canopy-baseline",
          "manifestVersion": 2,
          "worldDefinitionRef": "first-city",
          "engineCompatibility": 1,
          "rendererPolicyVersion": 1,
          "requiredCapabilities": ["first-city.canopy.image-png"],
          "assetReferences": [{
            "refId": "first-city-canopy-baseline",
            "path": "assets/first-city/canopy/baseline.png",
            "mediaType": "image/png",
            "sha256": "480dd857a7aa4ed7d3da62c991cf04056a364fbf04554027d9f6af6628791c89"
          }]
        }
        """.trimIndent()

    private class FailingAfterPrefixInputStream(private val prefix: ByteArray) : InputStream() {
        private var prefixReturned = false

        override fun read(): Int = throw IOException("simulated stream read failure")

        override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
            if (prefixReturned) throw IOException("simulated stream read failure")
            prefixReturned = true
            val count = minOf(prefix.size, length)
            System.arraycopy(prefix, 0, buffer, offset, count)
            return count
        }
    }

    private class CloseTrackingInputStream(bytes: ByteArray) : ByteArrayInputStream(bytes) {
        var wasClosed = false
            private set

        override fun close() {
            wasClosed = true
            super.close()
        }
    }

    companion object {
        private const val ARTIFACT_ENV = "OX26_PACK_ARTIFACT"
        private val mapper = ObjectMapper()
    }
}
