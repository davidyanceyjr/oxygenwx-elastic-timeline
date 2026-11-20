package com.oxygen.weather.timeline.data.scenepack

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.StreamReadConstraints
import com.fasterxml.jackson.core.StreamReadFeature
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.exc.StreamConstraintsException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger

data class ScenePackAssetReference(
    val refId: String,
    val path: String,
    val mediaType: String,
    val sha256: String,
)

data class ScenePackManifest(
    val packId: String,
    val manifestVersion: Int,
    val worldDefinitionRef: String,
    val engineCompatibility: Int,
    val rendererPolicyVersion: Int,
    val requiredCapabilities: List<String>,
    val assetReference: ScenePackAssetReference,
)

enum class ScenePackManifestRejection {
    MALFORMED_JSON,
    INVALID_MANIFEST,
    UNSUPPORTED_VERSION,
    UNSUPPORTED_CAPABILITY,
    LIMIT_EXCEEDED,
    INPUT_READ_FAILURE,
}

sealed interface ScenePackManifestDecodeResult {
    data class Accepted(val manifest: ScenePackManifest) : ScenePackManifestDecodeResult
    data class Rejected(val reason: ScenePackManifestRejection) : ScenePackManifestDecodeResult
}

/** Bounded, side-effect-free decoder for the closed First City scene-pack manifest v2 envelope. */
object ScenePackManifestDecoder {
    const val MAX_MANIFEST_BYTES = 16 * 1024
    private const val MAX_NESTING_DEPTH = 4
    private const val MAX_JSON_TOKENS = 128

    private val rootFields = setOf(
        "packId",
        "manifestVersion",
        "worldDefinitionRef",
        "engineCompatibility",
        "rendererPolicyVersion",
        "requiredCapabilities",
        "assetReferences",
    )
    private val assetReferenceFields = setOf("refId", "path", "mediaType", "sha256")
    private val packIdPattern = Regex("[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?")
    private val sha256Pattern = Regex("[0-9a-f]{64}")
    private val drivePathPrefix = Regex("^[A-Za-z]:")
    private val uriSchemePrefix = Regex("^[A-Za-z][A-Za-z0-9+.-]*:")

    private val jsonFactory = JsonFactory.builder()
        .streamReadConstraints(
            StreamReadConstraints.builder()
                .maxNestingDepth(MAX_NESTING_DEPTH)
                .maxTokenCount(MAX_JSON_TOKENS.toLong())
                .build(),
        )
        .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
        .build()
    private val objectMapper = ObjectMapper(jsonFactory)

    /** Reads the complete supplied manifest within the byte cap; ownership of [input] stays with the caller. */
    fun decode(input: InputStream): ScenePackManifestDecodeResult {
        val bytes = try {
            readBounded(input)
        } catch (_: IOException) {
            return rejected(ScenePackManifestRejection.INPUT_READ_FAILURE)
        } ?: return rejected(ScenePackManifestRejection.LIMIT_EXCEEDED)

        val root = try {
            jsonFactory.createParser(bytes).use { parser ->
                val parsed = objectMapper.readTree<JsonNode>(parser)
                    ?: return rejected(ScenePackManifestRejection.MALFORMED_JSON)
                if (parser.nextToken() != null) {
                    return rejected(ScenePackManifestRejection.MALFORMED_JSON)
                }
                parsed
            }
        } catch (_: StreamConstraintsException) {
            return rejected(ScenePackManifestRejection.LIMIT_EXCEEDED)
        } catch (_: JsonProcessingException) {
            return rejected(ScenePackManifestRejection.MALFORMED_JSON)
        } catch (_: IOException) {
            return rejected(ScenePackManifestRejection.MALFORMED_JSON)
        }

        return validate(root)
    }

    private fun validate(root: JsonNode): ScenePackManifestDecodeResult {
        if (!root.isObject || !hasExactFields(root, rootFields)) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }

        val packId = root["packId"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        if (!packIdPattern.matches(packId)) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }

        val worldDefinitionRef = root["worldDefinitionRef"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        if (worldDefinitionRef != SUPPORTED_WORLD_DEFINITION_REF) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }

        val versionFields = listOf(
            root["manifestVersion"] to MANIFEST_VERSION,
            root["engineCompatibility"] to ENGINE_COMPATIBILITY,
            root["rendererPolicyVersion"] to RENDERER_POLICY_VERSION,
        )
        for ((node, expectedVersion) in versionFields) {
            val versionRejection = node.requireVersion(expectedVersion)
            if (versionRejection != null) return rejected(versionRejection)
        }

        val requiredCapabilities = root["requiredCapabilities"]
        if (!requiredCapabilities.isArray || requiredCapabilities.size() != 1 ||
            !requiredCapabilities[0].isTextual
        ) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }
        val capability = requiredCapabilities[0].textValue()
        if (capability != SUPPORTED_CAPABILITY) {
            return rejected(ScenePackManifestRejection.UNSUPPORTED_CAPABILITY)
        }

        val assetReferences = root["assetReferences"]
        if (!assetReferences.isArray || assetReferences.size() != 1) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }
        val assetNode = assetReferences[0]
        if (!assetNode.isObject || !hasExactFields(assetNode, assetReferenceFields)) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }

        val refId = assetNode["refId"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        val path = assetNode["path"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        val mediaType = assetNode["mediaType"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        val sha256 = assetNode["sha256"].textValueOrNull()
            ?: return rejected(ScenePackManifestRejection.INVALID_MANIFEST)

        if (refId.isEmpty() || !isCanonicalRelativePath(path) ||
            mediaType != SUPPORTED_MEDIA_TYPE || !sha256Pattern.matches(sha256)
        ) {
            return rejected(ScenePackManifestRejection.INVALID_MANIFEST)
        }

        return ScenePackManifestDecodeResult.Accepted(
            ScenePackManifest(
                packId = packId,
                manifestVersion = MANIFEST_VERSION,
                worldDefinitionRef = worldDefinitionRef,
                engineCompatibility = ENGINE_COMPATIBILITY,
                rendererPolicyVersion = RENDERER_POLICY_VERSION,
                requiredCapabilities = listOf(capability),
                assetReference = ScenePackAssetReference(
                    refId = refId,
                    path = path,
                    mediaType = mediaType,
                    sha256 = sha256,
                ),
            ),
        )
    }

    /** Returns null for a supported version, INVALID_MANIFEST for a wrong JSON type, or UNSUPPORTED_VERSION. */
    private fun JsonNode.requireVersion(expected: Int): ScenePackManifestRejection? = when {
        !isIntegralNumber -> ScenePackManifestRejection.INVALID_MANIFEST
        bigIntegerValue() != BigInteger.valueOf(expected.toLong()) -> ScenePackManifestRejection.UNSUPPORTED_VERSION
        else -> null
    }

    private fun hasExactFields(node: JsonNode, expected: Set<String>): Boolean {
        val actual = mutableSetOf<String>()
        val names = node.fieldNames()
        while (names.hasNext()) actual += names.next()
        return actual == expected
    }

    private fun JsonNode.textValueOrNull(): String? = if (isTextual) textValue() else null

    private fun isCanonicalRelativePath(path: String): Boolean {
        if (path.isEmpty() || path.startsWith('/') || path.contains('\\')) return false
        if (drivePathPrefix.containsMatchIn(path) || uriSchemePrefix.containsMatchIn(path)) return false

        var segmentStart = 0
        for (index in path.indices) {
            if (path[index] == '/') {
                if (isInvalidPathSegment(path.substring(segmentStart, index))) return false
                segmentStart = index + 1
            }
        }
        return !isInvalidPathSegment(path.substring(segmentStart))
    }

    private fun isInvalidPathSegment(segment: String): Boolean =
        segment.isEmpty() || segment == "." || segment == ".."

    /** Null means the caller stream exceeded [MAX_MANIFEST_BYTES]. */
    private fun readBounded(input: InputStream): ByteArray? {
        val output = ByteArrayOutputStream(MAX_MANIFEST_BYTES)
        val buffer = ByteArray(4 * 1024)
        while (true) {
            val remainingWithSentinel = MAX_MANIFEST_BYTES + 1 - output.size()
            val count = input.read(buffer, 0, minOf(buffer.size, remainingWithSentinel))
            if (count < 0) break
            if (count == 0) {
                val next = input.read()
                if (next < 0) break
                if (output.size() == MAX_MANIFEST_BYTES) return null
                output.write(next)
            } else {
                if (output.size() + count > MAX_MANIFEST_BYTES) return null
                output.write(buffer, 0, count)
            }
        }
        return output.toByteArray()
    }

    private fun rejected(reason: ScenePackManifestRejection) = ScenePackManifestDecodeResult.Rejected(reason)

    private const val MANIFEST_VERSION = 2
    private const val ENGINE_COMPATIBILITY = 1
    private const val RENDERER_POLICY_VERSION = 1
    private const val SUPPORTED_WORLD_DEFINITION_REF = "first-city"
    private const val SUPPORTED_CAPABILITY = "first-city.canopy.image-png"
    private const val SUPPORTED_MEDIA_TYPE = "image/png"
}
