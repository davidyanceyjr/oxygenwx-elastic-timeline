package com.oxygen.weather.timeline.data.scenepack

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.security.MessageDigest
import java.util.zip.CRC32
import java.util.zip.DeflaterOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class ScenePackResourceValidatorTest {
    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Test
    fun acceptsBaselineAndOliveBuilderArtifactsAndMatchesBuilderIdentity() {
        val baselineFile = File(requireEnvironment(ARTIFACT_BASELINE_ENV))
        val oliveFile = File(requireEnvironment(ARTIFACT_OLIVE_ENV))
        val baseline = accepted(ScenePackResourceValidator.validate(baselineFile))
        val olive = accepted(ScenePackResourceValidator.validate(oliveFile))

        assertEquals("first-city-canopy-baseline", baseline.manifest.packId)
        assertEquals("first-city-canopy-olive", olive.manifest.packId)
        assertArrayEquals(
            Files.readAllBytes(File("..", BASELINE_SOURCE_ASSET).toPath()),
            baseline.assetsByPath[baseline.manifest.assetReference.path],
        )
        assertArrayEquals(
            Files.readAllBytes(File("..", OLIVE_SOURCE_ASSET).toPath()),
            olive.assetsByPath[olive.manifest.assetReference.path],
        )
        assertEquals(readBuilderIdentity("builder-baseline.txt"), baseline.contentIdentity)
        assertEquals(readBuilderIdentity("builder-olive.txt"), olive.contentIdentity)
    }

    @Test
    fun rejectsAnEscapingManifestPathThroughTheDecoderHandoff() {
        val bytes = smallPng()
        val path = "assets/../outside.png"
        val result = validate(entriesFor(path, bytes))

        rejected(result, ScenePackResourceRejection.MANIFEST_REJECTED)
    }

    @Test
    fun rejectsUnsupportedDeclaredMediaThroughTheDecoderHandoff() {
        val bytes = smallPng()
        val path = "assets/image.png"
        val manifest = manifestBytes(path, bytes, mediaType = "image/jpeg")
        rejected(validate(listOf(FixtureEntry(ROOT_MANIFEST, manifest), FixtureEntry(path, bytes))),
            ScenePackResourceRejection.MANIFEST_REJECTED)
    }

    @Test
    fun rejectsSymlinkDuplicateAndUndeclaredMembersAsWholeArchives() {
        val path = "assets/image.png"
        val png = smallPng()
        val manifest = manifestBytes(path, png)

        rejected(
            validate(
                listOf(
                    FixtureEntry(ROOT_MANIFEST, manifest),
                    FixtureEntry(path, png, unixMode = S_IFLNK or 0x1ff),
                ),
            ),
            ScenePackResourceRejection.ARCHIVE_REJECTED,
        )
        rejected(
            validate(
                listOf(
                    FixtureEntry(ROOT_MANIFEST, manifest),
                    FixtureEntry(path, png),
                    FixtureEntry(path, png.copyOf()),
                ),
            ),
            ScenePackResourceRejection.ARCHIVE_REJECTED,
        )
        rejected(
            validate(
                listOf(
                    FixtureEntry(ROOT_MANIFEST, manifest),
                    FixtureEntry(path, png),
                    FixtureEntry("notes.txt", byteArrayOf(1, 2, 3)),
                ),
            ),
            ScenePackResourceRejection.ARCHIVE_REJECTED,
        )
    }

    @Test
    fun rejectsADeclaredResourceThatIsMissing() {
        val path = "assets/missing.png"
        val manifest = manifestBytes(path, smallPng())

        rejected(validate(listOf(FixtureEntry(ROOT_MANIFEST, manifest))), ScenePackResourceRejection.ARCHIVE_REJECTED)
    }

    @Test
    fun rejectsDeclaredDigestMismatchWithoutPublishingAnAcceptedValue() {
        val path = "assets/image.png"
        val png = smallPng()
        val manifest = manifestBytes(path, png, digestOverride = "0".repeat(64))

        rejected(
            validate(listOf(FixtureEntry(ROOT_MANIFEST, manifest), FixtureEntry(path, png))),
            ScenePackResourceRejection.DIGEST_MISMATCH,
        )
    }

    @Test
    fun rejectsPngSignatureFramingHeaderOrderCrcAndTerminationFailures() {
        val valid = smallPng()
        val badSignature = valid.copyOf().also { it[1] = 0 }
        val truncatedChunk = valid.copyOfRange(0, valid.size - 1)
        val invalidOrder = pngWithChunks(
            "IDAT" to byteArrayOf(1),
            "IHDR" to ihdrData(),
            "IEND" to byteArrayOf(),
        )
        val invalidHeader = ihdrData().also { data -> data.fill(0, 0, 4) }
        val badHeaderFields = pngWithChunks("IHDR" to invalidHeader, "IDAT" to byteArrayOf(1), "IEND" to byteArrayOf())
        val badCrc = valid.copyOf().also { it[29] = (it[29].toInt() xor 1).toByte() }
        val missingEnd = valid.copyOfRange(0, valid.size - 12)
        val trailingBytes = valid + byteArrayOf(0x55)
        val missingImageData = pngWithChunks("IHDR" to ihdrData(), "IEND" to byteArrayOf())

        listOf(
            badSignature,
            truncatedChunk,
            invalidOrder,
            badHeaderFields,
            badCrc,
            missingEnd,
            trailingBytes,
            missingImageData,
        ).forEach { png ->
            val path = "assets/image.png"
            rejected(
                validate(entriesFor(path, png)),
                ScenePackResourceRejection.UNSUPPORTED_RESOURCE,
            )
        }
    }

    @Test
    fun acceptsAncillaryPngChunksWhenTheirFramingAndCrcAreValid() {
        val png = pngWithChunks(
            "IHDR" to ihdrData(),
            "tEXt" to "Comment\u0000fixture".toByteArray(StandardCharsets.US_ASCII),
            "IDAT" to compressedOnePixelRgba(),
            "IEND" to byteArrayOf(),
        )
        val path = "assets/image.png"

        val result = accepted(validate(entriesFor(path, png)))

        assertArrayEquals(png, result.assetsByPath[path])
    }

    @Test
    fun acceptsTheInclusiveResourceLimitAndRejectsOneByteOver() {
        val path = "assets/large.png"
        val exact = pngOfSize(ScenePackResourceValidator.MAX_RESOURCE_BYTES)
        val exactResult = accepted(validate(entriesFor(path, exact)))
        assertEquals(ScenePackResourceValidator.MAX_RESOURCE_BYTES, exactResult.assetsByPath[path]?.size)

        val over = pngOfSize(ScenePackResourceValidator.MAX_RESOURCE_BYTES + 1)
        rejected(validate(entriesFor(path, over)), ScenePackResourceRejection.LIMIT_EXCEEDED)
    }

    @Test
    fun acceptsTheInclusive64EntryInventoryLimitAndRejects65BeforeAnyRecordScan() {
        val fixture = deepPathFixture()
        assertEquals(64, fixture.entries.size)
        accepted(validate(fixture.entries))

        val probe = ScenePackResourceValidationProbe()
        val overLimit = fixture.entries + FixtureEntry("unused/", byteArrayOf(), isDirectory = true)
        rejected(validate(overLimit, probe), ScenePackResourceRejection.LIMIT_EXCEEDED)
        assertEquals(0, probe.centralRecordsScanned)
        assertEquals(0, probe.centralEntriesCreated)
        assertEquals(0, probe.zipFilesOpened)
    }

    @Test
    fun acceptsTheInclusiveCentralDirectoryByteLimitAndRejectsOneByteOverBeforeScan() {
        val path = "assets/image.png"
        val base = entriesFor(path, smallPng())
        val baseDirectorySize = base.sumOf { CENTRAL_RECORD_FIXED_BYTES + it.name.toByteArray(UTF8).size }
        val exactRemainder = ScenePackResourceValidator.MAX_CENTRAL_DIRECTORY_BYTES - baseDirectorySize
        val exactExtraDataLength = exactRemainder - 5 // extra-field header plus one comment byte
        val exactField = extraField(ByteArray(exactExtraDataLength))
        val exactEntries = base.toMutableList().apply {
            this[0] = this[0].copy(centralExtra = exactField, centralComment = byteArrayOf(0x7f))
        }
        assertEquals(
            ScenePackResourceValidator.MAX_CENTRAL_DIRECTORY_BYTES,
            centralDirectorySize(exactEntries),
        )
        accepted(validate(exactEntries))

        val overExtraDataLength = exactExtraDataLength + 1
        val overEntries = base.toMutableList().apply {
            this[0] = this[0].copy(
                centralExtra = extraField(ByteArray(overExtraDataLength)),
                centralComment = byteArrayOf(0x7f),
            )
        }
        assertEquals(ScenePackResourceValidator.MAX_CENTRAL_DIRECTORY_BYTES + 1, centralDirectorySize(overEntries))
        val probe = ScenePackResourceValidationProbe()
        rejected(validate(overEntries, probe), ScenePackResourceRejection.LIMIT_EXCEEDED)
        assertEquals(0, probe.centralRecordsScanned)
        assertEquals(0, probe.centralEntriesCreated)
        assertEquals(0, probe.zipFilesOpened)
    }

    @Test
    fun reportsAReadFailureWithoutReturningPartialAssets() {
        val path = "assets/image.png"
        val png = smallPng()
        val archive = writeArchive(entriesFor(path, png))
        val file = writeArchiveFile(archive)

        val result = ScenePackResourceValidator.validate(file, memberStreamReader = { zipFile, entry ->
            if (entry.name == path) {
                object : InputStream() {
                    override fun read(): Int = throw IOException("simulated member read failure")
                }
            } else {
                zipFile.getInputStream(entry)
            }
        })

        rejected(result, ScenePackResourceRejection.READ_FAILURE)
    }

    @Test
    fun rejectsMalformedAndUnsupportedArchivesWithABoundedReason() {
        rejected(validateBytes(byteArrayOf(1, 2, 3, 4)), ScenePackResourceRejection.ARCHIVE_REJECTED)

        val valid = writeArchive(entriesFor("assets/image.png", smallPng()))
        val corruptedEndRecord = valid.copyOf().also { it[it.lastIndex - 21] = 0 }
        rejected(validateBytes(corruptedEndRecord), ScenePackResourceRejection.ARCHIVE_REJECTED)
    }

    private fun validate(entries: List<FixtureEntry>, probe: ScenePackResourceValidationProbe? = null): ScenePackResourceValidationResult =
        validateBytes(writeArchive(entries), probe)

    private fun validateBytes(
        bytes: ByteArray,
        probe: ScenePackResourceValidationProbe? = null,
    ): ScenePackResourceValidationResult = ScenePackResourceValidator.validate(writeArchiveFile(bytes), probe)

    private fun writeArchiveFile(bytes: ByteArray): File = temporaryFolder.newFile().also { file ->
        Files.write(file.toPath(), bytes)
    }

    private fun writeArchive(entries: List<FixtureEntry>): ByteArray {
        val local = ByteArrayOutputStream()
        val central = ByteArrayOutputStream()
        val layouts = entries.map { entry ->
            val nameBytes = entry.name.toByteArray(UTF8)
            val flags = if (nameBytes.any { (it.toInt() and 0xff) > 0x7f }) FLAG_UTF8 else 0
            val crc = CRC32().apply { update(entry.bytes) }.value
            val localOffset = local.size().toLong()

            local.writeLe32(LOCAL_SIGNATURE)
            local.writeLe16(VERSION_20)
            local.writeLe16(flags)
            local.writeLe16(STORED)
            local.writeLe16(0)
            local.writeLe16(0)
            local.writeLe32(crc)
            local.writeLe32(entry.bytes.size.toLong())
            local.writeLe32(entry.bytes.size.toLong())
            local.writeLe16(nameBytes.size)
            local.writeLe16(entry.localExtra.size)
            local.write(nameBytes)
            local.write(entry.localExtra)
            local.write(entry.bytes)

            ZipLayout(entry, nameBytes, flags, crc, localOffset)
        }

        val centralOffset = local.size().toLong()
        layouts.forEach { layout ->
            val entry = layout.entry
            val nameBytes = layout.nameBytes
            central.writeLe32(CENTRAL_SIGNATURE)
            central.writeLe16((UNIX_CREATOR shl 8) or VERSION_20)
            central.writeLe16(VERSION_20)
            central.writeLe16(layout.flags)
            central.writeLe16(STORED)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe32(layout.crc)
            central.writeLe32(entry.bytes.size.toLong())
            central.writeLe32(entry.bytes.size.toLong())
            central.writeLe16(nameBytes.size)
            central.writeLe16(entry.centralExtra.size)
            central.writeLe16(entry.centralComment.size)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe32((entry.unixMode.toLong() and 0xffff) shl 16)
            central.writeLe32(layout.localOffset)
            central.write(nameBytes)
            central.write(entry.centralExtra)
            central.write(entry.centralComment)
        }

        val centralBytes = central.toByteArray()
        val output = ByteArrayOutputStream()
        output.write(local.toByteArray())
        output.write(centralBytes)
        output.writeLe32(EOCD_SIGNATURE)
        output.writeLe16(0)
        output.writeLe16(0)
        output.writeLe16(entries.size)
        output.writeLe16(entries.size)
        output.writeLe32(centralBytes.size.toLong())
        output.writeLe32(centralOffset)
        output.writeLe16(0)
        return output.toByteArray()
    }

    private fun entriesFor(path: String, resource: ByteArray): List<FixtureEntry> = listOf(
        FixtureEntry(ROOT_MANIFEST, manifestBytes(path, resource)),
        FixtureEntry(path, resource),
    )

    private fun deepPathFixture(): DeepPathFixture {
        val directories = (0 until 62).map { "d${it.toString().padStart(2, '0')}" }
        val segments = directories + "asset.png"
        val path = segments.joinToString("/")
        val png = smallPng()
        val manifest = FixtureEntry(ROOT_MANIFEST, manifestBytes(path, png))
        val directoryEntries = (1 until segments.size).map { count ->
            FixtureEntry(segments.take(count).joinToString("/", postfix = "/"), byteArrayOf(), isDirectory = true)
        }
        return DeepPathFixture(listOf(manifest) + directoryEntries + FixtureEntry(path, png), path)
    }

    private fun manifestBytes(
        path: String,
        resource: ByteArray,
        digestOverride: String? = null,
        mediaType: String = "image/png",
    ): ByteArray {
        val digest = digestOverride ?: sha256(resource)
        return """
            {"packId":"synthetic-pack","manifestVersion":2,"worldDefinitionRef":"first-city","engineCompatibility":1,"rendererPolicyVersion":1,"requiredCapabilities":["first-city.canopy.image-png"],"assetReferences":[{"refId":"asset","path":"$path","mediaType":"$mediaType","sha256":"$digest"}]}
        """.trimIndent().toByteArray(UTF8)
    }

    private fun accepted(result: ScenePackResourceValidationResult): ScenePackResourceValidationResult.Accepted =
        result as? ScenePackResourceValidationResult.Accepted
            ?: throw AssertionError("Expected accepted result, got $result")

    private fun rejected(
        result: ScenePackResourceValidationResult,
        expected: ScenePackResourceRejection,
    ) {
        assertTrue("Expected rejection $expected, got $result", result is ScenePackResourceValidationResult.Rejected)
        assertEquals(expected, (result as ScenePackResourceValidationResult.Rejected).reason)
    }

    private fun readBuilderIdentity(fileName: String): String {
        val output = File(File("..", EVIDENCE_DIRECTORY), fileName).readText()
        val match = BUILDER_IDENTITY.find(output)
        assertNotNull("Builder output must report a content identity", match)
        return match!!.groupValues[1]
    }

    private fun requireEnvironment(name: String): String =
        System.getenv(name)?.takeIf { it.isNotBlank() }
            ?: throw AssertionError("Required environment variable $name is not set")

    private fun centralDirectorySize(entries: List<FixtureEntry>): Int = entries.sumOf { entry ->
        CENTRAL_RECORD_FIXED_BYTES + entry.name.toByteArray(UTF8).size + entry.centralExtra.size + entry.centralComment.size
    }

    private fun extraField(data: ByteArray): ByteArray = ByteArrayOutputStream().apply {
        writeLe16(0xcafe)
        writeLe16(data.size)
        write(data)
    }.toByteArray()

    private fun smallPng(): ByteArray = pngWithChunks(
        "IHDR" to ihdrData(),
        "IDAT" to compressedOnePixelRgba(),
        "IEND" to byteArrayOf(),
    )

    private fun pngOfSize(totalSize: Int): ByteArray {
        val idatLength = totalSize - PNG_SIGNATURE.size - (12 + PNG_IHDR_SIZE) - 12 - 12
        require(idatLength >= 0)
        return pngWithChunks(
            "IHDR" to ihdrData(),
            "IDAT" to ByteArray(idatLength),
            "IEND" to byteArrayOf(),
        )
    }

    private fun ihdrData(): ByteArray = ByteArray(PNG_IHDR_SIZE).also { data ->
        data.writeBe32(0, 1)
        data.writeBe32(4, 1)
        data[8] = 8
        data[9] = 6
        data[10] = 0
        data[11] = 0
        data[12] = 0
    }

    private fun compressedOnePixelRgba(): ByteArray = ByteArrayOutputStream().use { output ->
        DeflaterOutputStream(output).use { it.write(byteArrayOf(0, 0, 0, 0, 0)) }
        output.toByteArray()
    }

    private fun pngWithChunks(vararg chunks: Pair<String, ByteArray>): ByteArray = ByteArrayOutputStream().apply {
        write(PNG_SIGNATURE)
        chunks.forEach { (type, data) ->
            val typeBytes = type.toByteArray(StandardCharsets.US_ASCII)
            writeBe32(data.size.toLong())
            write(typeBytes)
            write(data)
            val crc = CRC32().apply {
                update(typeBytes)
                update(data)
            }.value
            writeBe32(crc)
        }
    }.toByteArray()

    private fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256")
        .digest(bytes)
        .joinToString("") { byte -> "%02x".format(byte.toInt() and 0xff) }

    private fun ByteArray.writeBe32(offset: Int, value: Int) {
        for (index in 0..3) this[offset + index] = (value ushr (24 - index * 8)).toByte()
    }

    private fun ByteArrayOutputStream.writeLe16(value: Int) {
        write(value and 0xff)
        write((value ushr 8) and 0xff)
    }

    private fun ByteArrayOutputStream.writeLe32(value: Long) {
        for (index in 0..3) write(((value ushr (index * 8)) and 0xff).toInt())
    }

    private fun ByteArrayOutputStream.writeBe32(value: Long) {
        for (index in 0..3) write(((value ushr (24 - index * 8)) and 0xff).toInt())
    }

    private data class FixtureEntry(
        val name: String,
        val bytes: ByteArray,
        val isDirectory: Boolean = name.endsWith('/'),
        val unixMode: Int = if (isDirectory) S_IFDIR or 0x1ed else S_IFREG or 0x1a4,
        val localExtra: ByteArray = byteArrayOf(),
        val centralExtra: ByteArray = byteArrayOf(),
        val centralComment: ByteArray = byteArrayOf(),
    )

    private data class ZipLayout(
        val entry: FixtureEntry,
        val nameBytes: ByteArray,
        val flags: Int,
        val crc: Long,
        val localOffset: Long,
    )

    private data class DeepPathFixture(val entries: List<FixtureEntry>, val assetPath: String)

    companion object {
        private const val ROOT_MANIFEST = "manifest.json"
        private const val BASELINE_SOURCE_ASSET =
            "docs/scene-pack-examples/first-city-canopy-baseline/assets/first-city/canopy/baseline.png"
        private const val OLIVE_SOURCE_ASSET =
            "docs/scene-pack-examples/first-city-canopy-olive/assets/first-city/canopy/olive.png"
        private const val ARTIFACT_BASELINE_ENV = "OX91_PACK_BASELINE_ARTIFACT"
        private const val ARTIFACT_OLIVE_ENV = "OX91_PACK_OLIVE_ARTIFACT"
        private const val EVIDENCE_DIRECTORY = ".codex/test-artifacts/163-ox91-pack-resource-validation"
        private const val FLAG_UTF8 = 1 shl 11
        private const val VERSION_20 = 20
        private const val UNIX_CREATOR = 3
        private const val STORED = 0
        private const val LOCAL_SIGNATURE = 0x04034b50L
        private const val CENTRAL_SIGNATURE = 0x02014b50L
        private const val EOCD_SIGNATURE = 0x06054b50L
        private const val CENTRAL_RECORD_FIXED_BYTES = 46
        private const val PNG_IHDR_SIZE = 13
        private const val S_IFREG = 0x8000
        private const val S_IFDIR = 0x4000
        private const val S_IFLNK = 0xa000
        private val PNG_SIGNATURE = byteArrayOf(
            0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a,
        )
        private val UTF8 = StandardCharsets.UTF_8
        private val BUILDER_IDENTITY = Regex("contentIdentity=(sha256:[0-9a-f]{64})")
    }
}
