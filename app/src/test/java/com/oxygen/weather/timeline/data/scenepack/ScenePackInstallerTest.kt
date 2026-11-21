package com.oxygen.weather.timeline.data.scenepack

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.security.MessageDigest
import java.util.zip.CRC32
import java.util.zip.DeflaterOutputStream
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class ScenePackInstallerTest {
    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Test
    fun commitsAcceptedBytesAndComposesTheAppOwnedCompatibleEntry() {
        val appFiles = temporaryFolder.newFolder("app-files")
        val archive = writePack("pack-atomic", smallPng())
        val installer = ScenePackInstaller(appFiles)

        val result = installer.install(archive, "OX-57 local test data", "ox57-installed-test-v1")

        assertEquals("$result", ScenePackInstallOutcome.INSTALLED, result.outcome)
        assertEquals("pack-atomic", result.packId)
        assertEquals("ox57-installed-test-v1", result.currentRegistration?.version?.value)
        val bundledFixture = BundledFreeThemeMetadata(
            displayName = "Debug bundled-free fixture",
            version = InstalledThemeVersion("ox57-debug-fixture-v1"),
        )
        val catalog = installer.queryCatalog(bundledFixture)
        val bundled = catalog.single { it.id == InstalledThemeCatalog.BUNDLED_FREE_THEME_ID }
        val installed = catalog.single { it.id == InstalledThemeId("pack-atomic") }
        assertEquals("Debug bundled-free fixture", bundled.displayName)
        assertEquals("ox57-debug-fixture-v1", bundled.version.value)
        assertEquals(InstalledThemeOrigin.APP_OWNED, installed.origin)
        assertEquals(InstalledThemeValidity.COMPATIBLE, installed.validity)
        assertEquals("OX-57 local test data", installed.displayName)
        assertEquals("ox57-installed-test-v1", installed.version.value)
        assertNoBundledFixtureValuesInStorage(appFiles)

        val resource = File(appFiles, "installed-scene-packs/pack-atomic/generations")
            .walkTopDown().single { it.isFile && it.name.endsWith(".png") }
        assertArrayEquals(smallPng(), Files.readAllBytes(resource.toPath()))
    }

    @Test
    fun interruptedReplacementKeepsTheCommittedGenerationAndHidesTheCandidate() {
        val appFiles = temporaryFolder.newFolder("app-files")
        val firstBytes = smallPng("first")
        val replacementBytes = smallPng("replacement")
        val firstArchive = writePack("pack-atomic", firstBytes)
        val replacementArchive = writePack("pack-atomic", replacementBytes)
        val installer = ScenePackInstaller(appFiles)

        val first = installer.install(firstArchive, "First local test data", "ox57-first-test-v1")
        assertEquals("$first", ScenePackInstallOutcome.INSTALLED, first.outcome)
        val firstIdentity = first.contentIdentity
        val failedReplacement = installer.install(
            archiveFile = replacementArchive,
            displayName = "Replacement local test data",
            version = "ox57-replacement-test-v1",
            interruptAfterFirstResourceWrite = true,
        )

        assertEquals(ScenePackInstallOutcome.INTERRUPTED, failedReplacement.outcome)
        assertTrue(firstIdentity != failedReplacement.contentIdentity)
        assertEquals("ox57-first-test-v1", failedReplacement.currentRegistration?.version?.value)
        val catalog = installer.queryCatalog(
            BundledFreeThemeMetadata("Debug bundled-free fixture", InstalledThemeVersion("ox57-debug-fixture-v1")),
        )
        val installed = catalog.single { it.id == InstalledThemeId("pack-atomic") }
        assertEquals("ox57-first-test-v1", installed.version.value)
        assertEquals(InstalledThemeValidity.COMPATIBLE, installed.validity)
        assertFalse(catalog.any { it.id == InstalledThemeId("pack-atomic") && it.version.value == "ox57-replacement-test-v1" })

        val packRoot = File(appFiles, "installed-scene-packs/pack-atomic")
        assertFalse(File(packRoot, "staging").exists())
        assertEquals(1, File(packRoot, "generations").listFiles()!!.size)
        val currentResource = File(packRoot, "generations").walkTopDown()
            .single { it.isFile && it.name.endsWith(".png") }
        assertArrayEquals(firstBytes, Files.readAllBytes(currentResource.toPath()))
    }

    @Test
    fun rejectedEscapedAndOversizedArchivesDoNotAllocateAppOwnedStorage() {
        val appFiles = temporaryFolder.newFolder("app-files")
        val installer = ScenePackInstaller(appFiles)
        val escaped = writePack("pack-escaped", smallPng(), resourcePath = "assets/../outside.png")
        val oversized = writePack(
            "pack-oversized",
            pngOfSize(ScenePackResourceValidator.MAX_RESOURCE_BYTES + 1),
        )

        assertEquals(ScenePackInstallOutcome.REJECTED, installer.install(escaped, "test", "escaped-v1").outcome)
        assertEquals(ScenePackInstallOutcome.REJECTED, installer.install(oversized, "test", "oversized-v1").outcome)
        assertFalse(File(appFiles, "installed-scene-packs").exists())
    }

    @Test
    fun confinesCanonicalNestedPathsAndRejectsEscapes() {
        val root = temporaryFolder.newFolder("stage")
        val nested = ScenePackInstallPaths.resolveConfinedOutput(root, "assets/scenes/one.png")
        assertNotNull(nested)
        assertTrue(ScenePackInstallPaths.isConfined(root, nested!!))
        assertNull(ScenePackInstallPaths.resolveConfinedOutput(root, "../outside.png"))
        assertNull(ScenePackInstallPaths.resolveConfinedOutput(root, "assets/../../outside.png"))
    }

    @Test
    fun checksInclusiveAndOneOverFilesystemAndByteBudgets() {
        val exactNodePath = (0 until 61).map { "d$it" }.plus("asset.png").joinToString("/")
        val oneOverNodePath = (0 until 62).map { "d$it" }.plus("asset.png").joinToString("/")
        assertEquals(ScenePackInstallLimits.MAX_GENERATION_NODES, ScenePackInstallLimits.materializedNodeCount(exactNodePath))
        assertEquals(ScenePackInstallLimits.MAX_GENERATION_NODES + 1, ScenePackInstallLimits.materializedNodeCount(oneOverNodePath))

        val inclusive = ScenePackInstallLimits.estimate(
            materializedNodes = ScenePackInstallLimits.MAX_GENERATION_NODES,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES,
            recordBytes = ScenePackInstallLimits.MAX_RECORD_BYTES,
            oldGenerationBytes = ScenePackInstallLimits.MAX_GENERATION_BYTES,
        )
        assertEquals(ScenePackInstallLimits.MAX_GENERATION_BYTES, inclusive.generationBytes)
        assertEquals(ScenePackInstallLimits.MAX_CANDIDATE_AND_COMMIT_BYTES, inclusive.candidateAndCommitBytes)
        assertEquals(ScenePackInstallLimits.MAX_REPLACEMENT_PEAK_BYTES, inclusive.replacementPeakBytes)
        assertEquals(ScenePackInstallLimits.MAX_PACKAGE_WORKING_BYTES, inclusive.workingBytes)
        assertTrue(ScenePackInstallLimits.violations(inclusive).isEmpty())

        val nodeOver = inclusive.copy(materializedNodes = ScenePackInstallLimits.MAX_GENERATION_NODES + 1)
        assertTrue(ScenePackInstallLimits.violations(nodeOver).contains("materialized filesystem nodes"))
        val resourceOver = ScenePackInstallLimits.estimate(
            materializedNodes = 0,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES + 1,
            recordBytes = 0,
            oldGenerationBytes = 0,
        )
        assertTrue(ScenePackInstallLimits.violations(resourceOver).contains("accepted resource bytes"))
        val generationOver = ScenePackInstallLimits.estimate(
            materializedNodes = 0,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES,
            recordBytes = ScenePackInstallLimits.MAX_RECORD_BYTES + 1,
            oldGenerationBytes = 0,
        )
        assertTrue(ScenePackInstallLimits.violations(generationOver).contains("expanded generation bytes"))
        val replacementOver = ScenePackInstallLimits.estimate(
            materializedNodes = 0,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES,
            recordBytes = ScenePackInstallLimits.MAX_RECORD_BYTES,
            oldGenerationBytes = ScenePackInstallLimits.MAX_GENERATION_BYTES + 1,
        )
        assertTrue(ScenePackInstallLimits.violations(replacementOver).contains("same-pack replacement peak bytes"))
        val commitOver = ScenePackInstallLimits.estimate(
            materializedNodes = 0,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES,
            recordBytes = ScenePackInstallLimits.MAX_RECORD_BYTES + 1,
            oldGenerationBytes = 0,
        )
        assertTrue(ScenePackInstallLimits.violations(commitOver).contains("candidate plus commit bytes"))
        val workingOver = ScenePackInstallLimits.estimate(
            materializedNodes = 0,
            resourceBytes = ScenePackInstallLimits.MAX_RESOURCE_BYTES,
            recordBytes = ScenePackInstallLimits.MAX_RECORD_BYTES + 1,
            oldGenerationBytes = 0,
        )
        assertTrue(ScenePackInstallLimits.violations(workingOver).contains("package working bytes"))
    }

    @Test
    fun measuresTheInstallRecordAtItsInclusiveCapAndRejectsOneOver() {
        val manifest = ScenePackManifest(
            packId = "pack-record",
            manifestVersion = 2,
            worldDefinitionRef = "first-city",
            engineCompatibility = 1,
            rendererPolicyVersion = 1,
            requiredCapabilities = listOf("first-city.canopy.image-png"),
            assetReference = ScenePackAssetReference("asset", "assets/scene.png", "image/png", "a".repeat(64)),
        )
        val emptyNameRecord = PersistedScenePackRecord(
            manifest = manifest,
            contentIdentity = "b".repeat(64),
            registration = AppOwnedThemeRegistration(
                packId = manifest.packId,
                version = InstalledThemeVersion("v1"),
                displayName = "",
                compatibility = AppOwnedThemeCompatibility.COMPATIBLE,
            ),
        )
        val emptyNameSize = ScenePackInstallRecordCodec.encodedSize(emptyNameRecord)
        assertNotNull(emptyNameSize)
        val displayNameLength = (ScenePackInstallLimits.MAX_RECORD_BYTES - emptyNameSize!!).toInt()
        val exactRecord = emptyNameRecord.copy(
            registration = emptyNameRecord.registration.copy(displayName = "x".repeat(displayNameLength)),
        )
        val exactSize = ScenePackInstallRecordCodec.encodedSize(exactRecord)
        assertEquals(ScenePackInstallLimits.MAX_RECORD_BYTES, exactSize)
        val encoded = ByteArrayOutputStream().also { bytes ->
            DataOutputStream(bytes).use { ScenePackInstallRecordCodec.write(it, exactRecord) }
        }.toByteArray()
        assertEquals(ScenePackInstallLimits.MAX_RECORD_BYTES.toInt(), encoded.size)

        val oneOverRecord = exactRecord.copy(
            registration = exactRecord.registration.copy(displayName = "x".repeat(displayNameLength + 1)),
        )
        assertNull(ScenePackInstallRecordCodec.encodedSize(oneOverRecord))
    }

    private fun writePack(
        packId: String,
        resource: ByteArray,
        resourcePath: String = "assets/scene.png",
    ): File {
        val archive = temporaryFolder.newFile("${packId}-${temporaryFolder.root.listFiles()!!.size}.ox30pack")
        val entries = listOf(
            ZipFixtureEntry("manifest.json", manifestBytes(packId, resourcePath, resource)),
            ZipFixtureEntry(resourcePath, resource),
        )
        Files.write(archive.toPath(), writeStoredUnixZip(entries))
        return archive
    }

    private fun writeStoredUnixZip(entries: List<ZipFixtureEntry>): ByteArray {
        val local = ByteArrayOutputStream()
        val central = ByteArrayOutputStream()
        val layouts = entries.map { entry ->
            val name = entry.name.toByteArray(StandardCharsets.UTF_8)
            val crc = CRC32().apply { update(entry.bytes) }.value
            val offset = local.size().toLong()
            local.writeLe32(0x04034b50L)
            local.writeLe16(20)
            local.writeLe16(0)
            local.writeLe16(0)
            local.writeLe16(0)
            local.writeLe16(0)
            local.writeLe32(crc)
            local.writeLe32(entry.bytes.size.toLong())
            local.writeLe32(entry.bytes.size.toLong())
            local.writeLe16(name.size)
            local.writeLe16(0)
            local.write(name)
            local.write(entry.bytes)
            Triple(entry, name, offset to crc)
        }
        val centralOffset = local.size().toLong()
        layouts.forEach { (entry, name, offsetAndCrc) ->
            val (offset, crc) = offsetAndCrc
            central.writeLe32(0x02014b50L)
            central.writeLe16((3 shl 8) or 20)
            central.writeLe16(20)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe32(crc)
            central.writeLe32(entry.bytes.size.toLong())
            central.writeLe32(entry.bytes.size.toLong())
            central.writeLe16(name.size)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe16(0)
            central.writeLe32((0x81a4L) shl 16)
            central.writeLe32(offset)
            central.write(name)
        }
        val centralBytes = central.toByteArray()
        return ByteArrayOutputStream().also { output ->
            output.write(local.toByteArray())
            output.write(centralBytes)
            output.writeLe32(0x06054b50L)
            output.writeLe16(0)
            output.writeLe16(0)
            output.writeLe16(entries.size)
            output.writeLe16(entries.size)
            output.writeLe32(centralBytes.size.toLong())
            output.writeLe32(centralOffset)
            output.writeLe16(0)
        }.toByteArray()
    }

    private fun manifestBytes(packId: String, path: String, resource: ByteArray): ByteArray {
        val digest = sha256(resource)
        return """
            {"packId":"$packId","manifestVersion":2,"worldDefinitionRef":"first-city","engineCompatibility":1,"rendererPolicyVersion":1,"requiredCapabilities":["first-city.canopy.image-png"],"assetReferences":[{"refId":"asset","path":"$path","mediaType":"image/png","sha256":"$digest"}]}
        """.trimIndent().toByteArray(StandardCharsets.UTF_8)
    }

    private fun assertNoBundledFixtureValuesInStorage(root: File) {
        root.walkTopDown().filter { it.isFile }.forEach { file ->
            val stored = String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)
            assertFalse(stored.contains("Debug bundled-free fixture"))
            assertFalse(stored.contains("ox57-debug-fixture-v1"))
        }
    }

    private fun smallPng(label: String? = null): ByteArray {
        val chunks = mutableListOf("IHDR" to ihdr())
        if (label != null) chunks += "tEXt" to "Comment\u0000$label".toByteArray(StandardCharsets.US_ASCII)
        chunks += "IDAT" to compressedPixel()
        chunks += "IEND" to byteArrayOf()
        return png(chunks)
    }

    private fun pngOfSize(size: Int): ByteArray {
        val dataSize = size - PNG_SIGNATURE.size - (12 + 13) - 12 - 12
        require(dataSize >= 0)
        return png(listOf("IHDR" to ihdr(), "IDAT" to ByteArray(dataSize), "IEND" to byteArrayOf()))
    }

    private fun ihdr(): ByteArray = ByteArray(13).also { bytes ->
        bytes.writeBe32(0, 1)
        bytes.writeBe32(4, 1)
        bytes[8] = 8
        bytes[9] = 6
    }

    private fun compressedPixel(): ByteArray = ByteArrayOutputStream().use { output ->
        DeflaterOutputStream(output).use { it.write(byteArrayOf(0, 0, 0, 0, 0)) }
        output.toByteArray()
    }

    private fun png(chunks: List<Pair<String, ByteArray>>): ByteArray = ByteArrayOutputStream().also { output ->
        output.write(PNG_SIGNATURE)
        chunks.forEach { (name, data) ->
            val type = name.toByteArray(StandardCharsets.US_ASCII)
            output.writeBe32(data.size)
            output.write(type)
            output.write(data)
            val crc = CRC32().apply { update(type); update(data) }.value
            output.writeBe32(crc.toInt())
        }
    }.toByteArray()

    private fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256")
        .digest(bytes).joinToString("") { "%02x".format(it.toInt() and 0xff) }

    private fun ByteArray.writeBe32(offset: Int, value: Int) {
        for (index in 0..3) this[offset + index] = (value ushr (24 - index * 8)).toByte()
    }

    private fun ByteArrayOutputStream.writeBe32(value: Int) {
        write(value ushr 24 and 0xff)
        write(value ushr 16 and 0xff)
        write(value ushr 8 and 0xff)
        write(value and 0xff)
    }

    private fun ByteArrayOutputStream.writeLe16(value: Int) {
        write(value and 0xff)
        write(value ushr 8 and 0xff)
    }

    private fun ByteArrayOutputStream.writeLe32(value: Long) {
        writeLe16((value and 0xffff).toInt())
        writeLe16((value ushr 16 and 0xffff).toInt())
    }

    private data class ZipFixtureEntry(val name: String, val bytes: ByteArray)

    companion object {
        private val PNG_SIGNATURE = byteArrayOf(
            0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a,
        )
    }
}
