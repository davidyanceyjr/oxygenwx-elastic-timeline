package com.oxygen.weather.timeline.data.scenepack

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Enumeration
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile

enum class ScenePackResourceRejection {
    ARCHIVE_REJECTED,
    MANIFEST_REJECTED,
    UNSUPPORTED_RESOURCE,
    LIMIT_EXCEEDED,
    DIGEST_MISMATCH,
    READ_FAILURE,
}

sealed interface ScenePackResourceValidationResult {
    data class Accepted(
        val manifest: ScenePackManifest,
        val assetsByPath: Map<String, ByteArray>,
        val contentIdentity: String,
    ) : ScenePackResourceValidationResult

    data class Rejected(val reason: ScenePackResourceRejection) : ScenePackResourceValidationResult
}

/** Internal counters let focused tests prove inventory caps run before record or JDK-entry scans. */
internal class ScenePackResourceValidationProbe {
    var centralRecordsScanned: Int = 0
    var centralEntriesCreated: Int = 0
    var zipFilesOpened: Int = 0
    var memberReads: Int = 0
}

/** Bounded, read-only validator for the current OX-30 manifest-v2 ZIP profile. */
object ScenePackResourceValidator {
    const val MAX_RESOURCE_BYTES = 1_572_864
    const val MAX_RESOURCE_BYTES_AGGREGATE = 1_572_864
    const val MAX_ARCHIVE_ENTRIES = 64
    const val MAX_CENTRAL_DIRECTORY_BYTES = 65_536

    private const val MAX_MANIFEST_BYTES = 16 * 1024
    private const val MAX_ZIP_COMMENT_BYTES = 65_535
    private const val EOCD_FIXED_BYTES = 22
    private const val CENTRAL_FIXED_BYTES = 46
    private const val LOCAL_FIXED_BYTES = 30
    private const val ZIP_EOCD_SIGNATURE = 0x06054b50L
    private const val ZIP_CENTRAL_SIGNATURE = 0x02014b50L
    private const val ZIP_LOCAL_SIGNATURE = 0x04034b50L
    private const val ZIP64_LOCATOR_SIGNATURE = 0x07064b50L
    private const val ZIP64_EXTRA_ID = 0x0001
    private const val FLAG_UTF8_NAMES = 1 shl 11
    private const val S_IFMT = 0xf000
    private const val S_IFREG = 0x8000
    private const val S_IFDIR = 0x4000
    private val CONTENT_IDENTITY_DOMAIN = "oxygen-scene-pack-content-v1\u0000".toByteArray(StandardCharsets.UTF_8)
    private val PNG_SIGNATURE = byteArrayOf(
        0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a,
    )

    fun validate(packFile: File): ScenePackResourceValidationResult =
        validate(packFile, probe = null, memberStreamReader = null)

    /** Package-private seams are used only to prove bounded preflight and member-read failure behavior. */
    internal fun validate(
        packFile: File,
        probe: ScenePackResourceValidationProbe? = null,
        memberStreamReader: ((ZipFile, ZipEntry) -> InputStream)? = null,
    ): ScenePackResourceValidationResult {
        if (!packFile.isFile) return rejected(ScenePackResourceRejection.ARCHIVE_REJECTED)

        return try {
            val archiveIndex = RandomAccessFile(packFile, "r").use { file ->
                inspectArchive(file, probe)
            }

            probe?.zipFilesOpened = (probe?.zipFilesOpened ?: 0) + 1
            ZipFile(packFile).use { zipFile ->
                validateEntries(zipFile, archiveIndex, probe, memberStreamReader)
            }
        } catch (rejection: ValidationRejected) {
            rejected(rejection.reason)
        } catch (_: ZipException) {
            rejected(ScenePackResourceRejection.ARCHIVE_REJECTED)
        } catch (_: IOException) {
            rejected(ScenePackResourceRejection.READ_FAILURE)
        }
    }

    private fun validateEntries(
        zipFile: ZipFile,
        archiveIndex: ArchiveIndex,
        probe: ScenePackResourceValidationProbe?,
        memberStreamReader: ((ZipFile, ZipEntry) -> InputStream)?,
    ): ScenePackResourceValidationResult {
        val zipEntries = zipFile.entries().toList()
        if (zipEntries.size != archiveIndex.entries.size) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

        val zipEntriesByName = LinkedHashMap<String, ZipEntry>(zipEntries.size)
        zipEntries.forEachIndexed { index, zipEntry ->
            val indexed = archiveIndex.entries[index]
            if (zipEntry.name != indexed.name ||
                zipEntry.isDirectory != indexed.isDirectory ||
                zipEntry.method != indexed.method ||
                zipEntry.crc != indexed.crc32 ||
                zipEntry.compressedSize != indexed.compressedSize ||
                zipEntry.size != indexed.uncompressedSize
            ) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
            if (zipEntriesByName.put(zipEntry.name, zipEntry) != null) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
        }

        val rootManifestEntry = zipEntriesByName[ROOT_MANIFEST_NAME]
            ?: reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        if (rootManifestEntry.isDirectory) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

        val manifestBytes = readMember(
            zipFile = zipFile,
            entry = rootManifestEntry,
            maxBytes = MAX_MANIFEST_BYTES,
            probe = probe,
            memberStreamReader = memberStreamReader,
        )
        verifyZipCrc(rootManifestEntry, manifestBytes)

        val manifest = when (val decoded = ScenePackManifestDecoder.decode(ByteArrayInputStream(manifestBytes))) {
            is ScenePackManifestDecodeResult.Accepted -> decoded.manifest
            is ScenePackManifestDecodeResult.Rejected -> when (decoded.reason) {
                ScenePackManifestRejection.LIMIT_EXCEEDED -> reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
                ScenePackManifestRejection.INPUT_READ_FAILURE -> reject(ScenePackResourceRejection.READ_FAILURE)
                else -> reject(ScenePackResourceRejection.MANIFEST_REJECTED)
            }
        }

        val resourcePath = manifest.assetReference.path
        if (resourcePath == ROOT_MANIFEST_NAME) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        val expectedFiles = setOf(ROOT_MANIFEST_NAME, resourcePath)
        val resourceAncestors = canonicalDirectoryAncestors(resourcePath)
        if ("$ROOT_MANIFEST_NAME/" in resourceAncestors) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }

        val indexedByName = archiveIndex.entries.associateBy { it.name }
        val fileEntries = archiveIndex.entries.filterNot { it.isDirectory }
        val directoryEntries = archiveIndex.entries.filter { it.isDirectory }
        if (fileEntries.map { it.name }.toSet() != expectedFiles ||
            directoryEntries.any { it.name !in resourceAncestors } ||
            directoryEntries.any { it.uncompressedSize != 0L || it.compressedSize != 0L }
        ) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }

        val resourceEntry = indexedByName[resourcePath]
            ?.let { record -> zipEntriesByName[record.name] }
            ?: reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        if (resourceEntry.isDirectory) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

        val resourceBytes = readMember(
            zipFile = zipFile,
            entry = resourceEntry,
            maxBytes = MAX_RESOURCE_BYTES,
            probe = probe,
            memberStreamReader = memberStreamReader,
        )
        verifyZipCrc(resourceEntry, resourceBytes)
        if (resourceBytes.size > MAX_RESOURCE_BYTES ||
            resourceBytes.size.toLong() > MAX_RESOURCE_BYTES_AGGREGATE
        ) {
            reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
        }
        if (!isStructurallyValidPng(resourceBytes)) {
            reject(ScenePackResourceRejection.UNSUPPORTED_RESOURCE)
        }

        val actualDigest = sha256Hex(resourceBytes)
        if (actualDigest != manifest.assetReference.sha256) {
            reject(ScenePackResourceRejection.DIGEST_MISMATCH)
        }

        val contentIdentity = calculateContentIdentity(manifestBytes, resourcePath, resourceBytes)
        return ScenePackResourceValidationResult.Accepted(
            manifest = manifest,
            assetsByPath = mapOf(resourcePath to resourceBytes.copyOf()),
            contentIdentity = contentIdentity,
        )
    }

    private fun inspectArchive(
        file: RandomAccessFile,
        probe: ScenePackResourceValidationProbe?,
    ): ArchiveIndex {
        val archiveLength = file.length()
        val eocd = findEndRecord(file, archiveLength)

        val diskNumber = unsignedShort(eocd, 4)
        val centralDirectoryDisk = unsignedShort(eocd, 6)
        val entriesOnDisk = unsignedShort(eocd, 8)
        val entryCount = unsignedShort(eocd, 10)
        val centralDirectorySize = unsignedInt(eocd, 12)
        val centralDirectoryOffset = unsignedInt(eocd, 16)

        if (diskNumber != 0 || centralDirectoryDisk != 0 || entriesOnDisk != entryCount) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }
        if (entriesOnDisk == 0xffff || entryCount == 0xffff ||
            centralDirectorySize == UINT32_MAX || centralDirectoryOffset == UINT32_MAX
        ) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }

        // These limits are checked from the EOCD before reading any central record or opening ZipFile.
        if (entryCount > MAX_ARCHIVE_ENTRIES || centralDirectorySize > MAX_CENTRAL_DIRECTORY_BYTES) {
            reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
        }

        val eocdOffset = findEndRecordOffset(file, archiveLength)
        val centralDirectoryEnd = centralDirectoryOffset + centralDirectorySize
        if (centralDirectoryOffset > archiveLength || centralDirectoryEnd > archiveLength ||
            centralDirectoryEnd != eocdOffset
        ) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }

        val entries = ArrayList<CentralEntry>(entryCount)
        val names = HashSet<String>(entryCount)
        var cursor = centralDirectoryOffset
        repeat(entryCount) {
            probe?.centralRecordsScanned = (probe?.centralRecordsScanned ?: 0) + 1
            ensureRange(cursor, CENTRAL_FIXED_BYTES.toLong(), centralDirectoryEnd)
            val header = readAt(file, cursor, CENTRAL_FIXED_BYTES)
            if (unsignedInt(header, 0) != ZIP_CENTRAL_SIGNATURE) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }

            val versionMadeBy = unsignedShort(header, 4)
            val versionNeeded = unsignedShort(header, 6)
            val flags = unsignedShort(header, 8)
            val method = unsignedShort(header, 10)
            val crc32 = unsignedInt(header, 16)
            val compressedSize = unsignedInt(header, 20)
            val uncompressedSize = unsignedInt(header, 24)
            val nameLength = unsignedShort(header, 28)
            val extraLength = unsignedShort(header, 30)
            val commentLength = unsignedShort(header, 32)
            val diskStart = unsignedShort(header, 34)
            val externalAttributes = unsignedInt(header, 38)
            val localHeaderOffset = unsignedInt(header, 42)

            if ((versionMadeBy ushr 8) != UNIX_CREATOR_OS || versionNeeded !in MIN_ZIP_VERSION..MAX_SUPPORTED_ZIP_VERSION ||
                flags and FLAG_UTF8_NAMES.inv() != 0 || method != ZIP_STORED || diskStart != 0 ||
                compressedSize == UINT32_MAX || uncompressedSize == UINT32_MAX ||
                localHeaderOffset == UINT32_MAX || compressedSize != uncompressedSize
            ) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
            if (nameLength == 0) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

            val variableLength = nameLength.toLong() + extraLength + commentLength
            val recordLength = CENTRAL_FIXED_BYTES.toLong() + variableLength
            ensureRange(cursor, recordLength, centralDirectoryEnd)
            val variable = readAt(file, cursor + CENTRAL_FIXED_BYTES, variableLength.toInt())
            val rawName = variable.copyOfRange(0, nameLength)
            val rawExtra = variable.copyOfRange(nameLength, nameLength + extraLength)
            validateExtraFields(rawExtra)
            val name = decodeEntryName(rawName, flags)
            val unixMode = (externalAttributes ushr 16).toInt() and 0xffff
            val fileType = unixMode and S_IFMT
            val isDirectory = name.endsWith('/')
            if ((fileType != S_IFREG && fileType != S_IFDIR) || (fileType == S_IFDIR) != isDirectory) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
            if (!names.add(name)) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

            val entry = CentralEntry(
                name = name,
                rawName = rawName,
                isDirectory = isDirectory,
                unixMode = unixMode,
                versionNeeded = versionNeeded,
                flags = flags,
                method = method,
                crc32 = crc32,
                compressedSize = compressedSize,
                uncompressedSize = uncompressedSize,
                localHeaderOffset = localHeaderOffset,
            )
            probe?.centralEntriesCreated = (probe?.centralEntriesCreated ?: 0) + 1
            entries += entry
            cursor += recordLength
        }
        if (cursor != centralDirectoryEnd) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

        validateLocalHeaders(file, entries, centralDirectoryOffset)
        return ArchiveIndex(entries)
    }

    private fun findEndRecord(file: RandomAccessFile, archiveLength: Long): ByteArray {
        if (archiveLength < EOCD_FIXED_BYTES) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        val tailSize = minOf(archiveLength, EOCD_FIXED_BYTES.toLong() + MAX_ZIP_COMMENT_BYTES).toInt()
        val tailStart = archiveLength - tailSize
        val tail = readAt(file, tailStart, tailSize)

        for (candidate in tail.size - EOCD_FIXED_BYTES downTo 0) {
            if (unsignedInt(tail, candidate) != ZIP_EOCD_SIGNATURE) continue
            val commentLength = unsignedShort(tail, candidate + 20)
            if (candidate + EOCD_FIXED_BYTES + commentLength == tail.size) {
                return tail.copyOfRange(candidate, candidate + EOCD_FIXED_BYTES)
            }
        }
        reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
    }

    private fun findEndRecordOffset(file: RandomAccessFile, archiveLength: Long): Long {
        val tailSize = minOf(archiveLength, EOCD_FIXED_BYTES.toLong() + MAX_ZIP_COMMENT_BYTES).toInt()
        val tailStart = archiveLength - tailSize
        val tail = readAt(file, tailStart, tailSize)
        for (candidate in tail.size - EOCD_FIXED_BYTES downTo 0) {
            if (unsignedInt(tail, candidate) != ZIP_EOCD_SIGNATURE) continue
            val commentLength = unsignedShort(tail, candidate + 20)
            if (candidate + EOCD_FIXED_BYTES + commentLength == tail.size) {
                return tailStart + candidate
            }
        }
        reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
    }

    private fun validateLocalHeaders(
        file: RandomAccessFile,
        entries: List<CentralEntry>,
        centralDirectoryOffset: Long,
    ) {
        val localRanges = ArrayList<LongRange>(entries.size)
        entries.forEach { entry ->
            val offset = entry.localHeaderOffset
            ensureRange(offset, LOCAL_FIXED_BYTES.toLong(), centralDirectoryOffset)
            val header = readAt(file, offset, LOCAL_FIXED_BYTES)
            if (unsignedInt(header, 0) != ZIP_LOCAL_SIGNATURE) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)

            val versionNeeded = unsignedShort(header, 4)
            val flags = unsignedShort(header, 6)
            val method = unsignedShort(header, 8)
            val crc32 = unsignedInt(header, 14)
            val compressedSize = unsignedInt(header, 18)
            val uncompressedSize = unsignedInt(header, 22)
            val nameLength = unsignedShort(header, 26)
            val extraLength = unsignedShort(header, 28)
            if (versionNeeded != entry.versionNeeded || flags != entry.flags || method != entry.method ||
                crc32 != entry.crc32 || compressedSize != entry.compressedSize ||
                uncompressedSize != entry.uncompressedSize || nameLength != entry.rawName.size
            ) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }

            val variableLength = nameLength.toLong() + extraLength
            ensureRange(offset + LOCAL_FIXED_BYTES, variableLength, centralDirectoryOffset)
            val variable = readAt(file, offset + LOCAL_FIXED_BYTES, variableLength.toInt())
            val localName = variable.copyOfRange(0, nameLength)
            val localExtra = variable.copyOfRange(nameLength, variable.size)
            if (!localName.contentEquals(entry.rawName)) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            validateExtraFields(localExtra)

            val dataStart = offset + LOCAL_FIXED_BYTES + variableLength
            val dataEnd = dataStart + entry.compressedSize
            if (dataEnd > centralDirectoryOffset || (entry.isDirectory && entry.compressedSize != 0L)) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
            localRanges += offset until dataEnd
        }

        val sortedRanges = localRanges.sortedBy { it.first }
        for (index in 1 until sortedRanges.size) {
            if (sortedRanges[index].first <= sortedRanges[index - 1].last) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
        }
    }

    private fun validateExtraFields(extra: ByteArray) {
        var cursor = 0
        while (cursor < extra.size) {
            if (extra.size - cursor < 4) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            val fieldId = unsignedShort(extra, cursor)
            val fieldLength = unsignedShort(extra, cursor + 2)
            if (fieldId == ZIP64_EXTRA_ID || cursor + 4 + fieldLength > extra.size) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
            cursor += 4 + fieldLength
        }
    }

    private fun decodeEntryName(rawName: ByteArray, flags: Int): String {
        if (flags and FLAG_UTF8_NAMES != 0) {
            return try {
                StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(rawName))
                    .toString()
            } catch (_: CharacterCodingException) {
                reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
            }
        }
        if (rawName.any { (it.toInt() and 0xff) > 0x7f }) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }
        return String(rawName, StandardCharsets.US_ASCII)
    }

    private fun readMember(
        zipFile: ZipFile,
        entry: ZipEntry,
        maxBytes: Int,
        probe: ScenePackResourceValidationProbe?,
        memberStreamReader: ((ZipFile, ZipEntry) -> InputStream)?,
    ): ByteArray {
        probe?.let { it.memberReads++ }
        val input = memberStreamReader?.invoke(zipFile, entry) ?: zipFile.getInputStream(entry)
            ?: reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        return input.use { stream ->
            val output = ByteArrayOutputStream(minOf(maxBytes, 8 * 1024))
            val buffer = ByteArray(8 * 1024)
            while (true) {
                val remainingWithSentinel = maxBytes + 1 - output.size()
                if (remainingWithSentinel <= 0) reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
                val count = stream.read(buffer, 0, minOf(buffer.size, remainingWithSentinel))
                if (count < 0) break
                if (count == 0) {
                    val next = stream.read()
                    if (next < 0) break
                    if (output.size() == maxBytes) reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
                    output.write(next)
                } else {
                    if (output.size() + count > maxBytes) reject(ScenePackResourceRejection.LIMIT_EXCEEDED)
                    output.write(buffer, 0, count)
                }
            }
            output.toByteArray()
        }
    }

    private fun verifyZipCrc(entry: ZipEntry, bytes: ByteArray) {
        val crc = CRC32().apply { update(bytes) }.value
        if (crc != entry.crc) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
    }

    private fun isStructurallyValidPng(bytes: ByteArray): Boolean {
        if (bytes.size < PNG_SIGNATURE.size ||
            !bytes.copyOfRange(0, PNG_SIGNATURE.size).contentEquals(PNG_SIGNATURE)
        ) {
            return false
        }

        var cursor = PNG_SIGNATURE.size
        var sawHeader = false
        var sawPalette = false
        var sawImageData = false
        var imageDataEnded = false
        var sawEnd = false
        var colorType = -1
        var bitDepth = -1

        while (cursor < bytes.size) {
            if (bytes.size - cursor < PNG_CHUNK_OVERHEAD) return false
            val length = unsignedPngInt(bytes, cursor)
            if (length > PNG_MAX_CHUNK_BYTES) return false
            val chunkTypeOffset = cursor + 4
            val dataOffset = cursor + 8
            val dataEndLong = dataOffset.toLong() + length
            val chunkEndLong = dataEndLong + 4
            if (chunkEndLong > bytes.size || dataEndLong > Int.MAX_VALUE) return false

            val type = bytes.copyOfRange(chunkTypeOffset, chunkTypeOffset + 4)
            if (type.any { !it.isPngLetter() } || (type[2].toInt() and 0x20) != 0) return false
            val typeName = String(type, StandardCharsets.US_ASCII)
            val dataEnd = dataEndLong.toInt()
            val chunkEnd = chunkEndLong.toInt()
            val expectedCrc = unsignedPngInt(bytes, dataEnd)
            val actualCrc = CRC32().apply {
                update(bytes, chunkTypeOffset, (dataEnd - chunkTypeOffset))
            }.value
            if (expectedCrc != actualCrc) return false

            val isKnownCritical = typeName in KNOWN_CRITICAL_PNG_CHUNKS
            val isCritical = type[0].toInt() and 0x20 == 0
            if (isCritical && !isKnownCritical) return false
            if (!sawHeader && typeName != PNG_IHDR) return false

            when (typeName) {
                PNG_IHDR -> {
                    if (sawHeader || cursor != PNG_SIGNATURE.size || length != PNG_IHDR_BYTES.toLong()) return false
                    val width = unsignedPngInt(bytes, dataOffset)
                    val height = unsignedPngInt(bytes, dataOffset + 4)
                    bitDepth = bytes[dataOffset + 8].toInt() and 0xff
                    colorType = bytes[dataOffset + 9].toInt() and 0xff
                    val compression = bytes[dataOffset + 10].toInt() and 0xff
                    val filter = bytes[dataOffset + 11].toInt() and 0xff
                    val interlace = bytes[dataOffset + 12].toInt() and 0xff
                    if (width == 0L || height == 0L || width > PNG_MAX_DIMENSION || height > PNG_MAX_DIMENSION ||
                        !isValidBitDepth(colorType, bitDepth) || compression != 0 || filter != 0 || interlace !in 0..1
                    ) {
                        return false
                    }
                    sawHeader = true
                }

                PNG_PLTE -> {
                    if (!sawHeader || sawPalette || sawImageData || length == 0L || length > 768 || length % 3 != 0L ||
                        colorType == PNG_COLOR_GRAYSCALE || colorType == PNG_COLOR_GRAYSCALE_ALPHA ||
                        (colorType == PNG_COLOR_INDEXED && length / 3 > (1 shl bitDepth))
                    ) {
                        return false
                    }
                    sawPalette = true
                }

                PNG_IDAT -> {
                    if (!sawHeader || imageDataEnded) return false
                    if (colorType == PNG_COLOR_INDEXED && !sawPalette) return false
                    sawImageData = true
                }

                PNG_IEND -> {
                    if (!sawImageData || length != 0L || sawEnd || chunkEnd != bytes.size) return false
                    sawEnd = true
                }

                else -> {
                    if (sawImageData) imageDataEnded = true
                }
            }

            if (typeName != PNG_IDAT && sawImageData && typeName != PNG_IEND) imageDataEnded = true
            cursor = chunkEnd
            if (sawEnd) break
        }

        return sawHeader && sawImageData && sawEnd && cursor == bytes.size
    }

    private fun Byte.isPngLetter(): Boolean {
        val value = toInt() and 0xff
        return value in 'A'.code..'Z'.code || value in 'a'.code..'z'.code
    }

    private fun unsignedPngInt(bytes: ByteArray, offset: Int): Long =
        ((bytes[offset].toLong() and 0xff) shl 24) or
            ((bytes[offset + 1].toLong() and 0xff) shl 16) or
            ((bytes[offset + 2].toLong() and 0xff) shl 8) or
            (bytes[offset + 3].toLong() and 0xff)

    private fun isValidBitDepth(colorType: Int, bitDepth: Int): Boolean = when (colorType) {
        PNG_COLOR_GRAYSCALE -> bitDepth in setOf(1, 2, 4, 8, 16)
        PNG_COLOR_TRUECOLOR -> bitDepth == 8 || bitDepth == 16
        PNG_COLOR_INDEXED -> bitDepth in setOf(1, 2, 4, 8)
        PNG_COLOR_GRAYSCALE_ALPHA, PNG_COLOR_TRUECOLOR_ALPHA -> bitDepth == 8 || bitDepth == 16
        else -> false
    }

    private fun canonicalDirectoryAncestors(path: String): Set<String> {
        val segments = path.split('/')
        if (segments.size < 2) return emptySet()
        return (1 until segments.size).mapTo(LinkedHashSet()) { index ->
            segments.take(index).joinToString("/", postfix = "/")
        }
    }

    private fun calculateContentIdentity(manifestBytes: ByteArray, resourcePath: String, resourceBytes: ByteArray): String {
        val members = listOf(
            IdentityMember(ROOT_MANIFEST_NAME, manifestBytes),
            IdentityMember(resourcePath, resourceBytes),
        ).sortedWith { left, right -> compareUtf8(left.path, right.path) }

        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(CONTENT_IDENTITY_DOMAIN)
        digest.update(unsignedIntBytes(members.size.toLong()))
        members.forEach { member ->
            val pathBytes = member.path.toByteArray(StandardCharsets.UTF_8)
            digest.update(unsignedIntBytes(pathBytes.size.toLong()))
            digest.update(pathBytes)
            digest.update(unsignedLongBytes(member.bytes.size.toLong()))
            digest.update(member.bytes)
        }
        return "sha256:${digest.digest().toLowerHex()}"
    }

    private fun compareUtf8(left: String, right: String): Int {
        val leftBytes = left.toByteArray(StandardCharsets.UTF_8)
        val rightBytes = right.toByteArray(StandardCharsets.UTF_8)
        for (index in 0 until minOf(leftBytes.size, rightBytes.size)) {
            val comparison = (leftBytes[index].toInt() and 0xff).compareTo(rightBytes[index].toInt() and 0xff)
            if (comparison != 0) return comparison
        }
        return leftBytes.size.compareTo(rightBytes.size)
    }

    private fun unsignedIntBytes(value: Long): ByteArray = ByteArray(4) { index ->
        (value ushr (24 - index * 8)).toByte()
    }

    private fun unsignedLongBytes(value: Long): ByteArray = ByteArray(8) { index ->
        (value ushr (56 - index * 8)).toByte()
    }

    private fun ByteArray.toLowerHex(): String = buildString(size * 2) {
        this@toLowerHex.forEach { byte ->
            val value = byte.toInt() and 0xff
            append(HEX[value ushr 4])
            append(HEX[value and 0x0f])
        }
    }

    private fun sha256Hex(bytes: ByteArray): String =
        MessageDigest.getInstance("SHA-256").digest(bytes).toLowerHex()

    private fun readAt(file: RandomAccessFile, offset: Long, length: Int): ByteArray {
        if (length < 0) reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        ensureRange(offset, length.toLong(), file.length())
        val bytes = ByteArray(length)
        file.seek(offset)
        file.readFully(bytes)
        return bytes
    }

    private fun ensureRange(offset: Long, length: Long, limit: Long) {
        if (offset < 0 || length < 0 || offset > limit || length > limit - offset) {
            reject(ScenePackResourceRejection.ARCHIVE_REJECTED)
        }
    }

    private fun unsignedShort(bytes: ByteArray, offset: Int): Int =
        (bytes[offset].toInt() and 0xff) or ((bytes[offset + 1].toInt() and 0xff) shl 8)

    private fun unsignedInt(bytes: ByteArray, offset: Int): Long =
        (bytes[offset].toLong() and 0xff) or
            ((bytes[offset + 1].toLong() and 0xff) shl 8) or
            ((bytes[offset + 2].toLong() and 0xff) shl 16) or
            ((bytes[offset + 3].toLong() and 0xff) shl 24)

    private fun Enumeration<ZipEntry>.toList(): List<ZipEntry> {
        val result = ArrayList<ZipEntry>()
        while (hasMoreElements()) result += nextElement()
        return result
    }

    private fun rejected(reason: ScenePackResourceRejection) =
        ScenePackResourceValidationResult.Rejected(reason)

    private fun reject(reason: ScenePackResourceRejection): Nothing = throw ValidationRejected(reason)

    private class ValidationRejected(val reason: ScenePackResourceRejection) : Exception()

    private data class ArchiveIndex(val entries: List<CentralEntry>)

    private data class CentralEntry(
        val name: String,
        val rawName: ByteArray,
        val isDirectory: Boolean,
        val unixMode: Int,
        val versionNeeded: Int,
        val flags: Int,
        val method: Int,
        val crc32: Long,
        val compressedSize: Long,
        val uncompressedSize: Long,
        val localHeaderOffset: Long,
    )

    private data class IdentityMember(val path: String, val bytes: ByteArray)

    private const val ROOT_MANIFEST_NAME = "manifest.json"
    private const val UINT32_MAX = 0xffff_ffffL
    private const val UNIX_CREATOR_OS = 3
    private const val MIN_ZIP_VERSION = 10
    private const val MAX_SUPPORTED_ZIP_VERSION = 20
    private const val ZIP_STORED = 0
    private const val PNG_CHUNK_OVERHEAD = 12
    private const val PNG_MAX_CHUNK_BYTES = 0x7fff_ffffL
    private const val PNG_MAX_DIMENSION = 0x7fff_ffffL
    private const val PNG_IHDR_BYTES = 13
    private const val PNG_IHDR = "IHDR"
    private const val PNG_PLTE = "PLTE"
    private const val PNG_IDAT = "IDAT"
    private const val PNG_IEND = "IEND"
    private const val PNG_COLOR_GRAYSCALE = 0
    private const val PNG_COLOR_TRUECOLOR = 2
    private const val PNG_COLOR_INDEXED = 3
    private const val PNG_COLOR_GRAYSCALE_ALPHA = 4
    private const val PNG_COLOR_TRUECOLOR_ALPHA = 6
    private val KNOWN_CRITICAL_PNG_CHUNKS = setOf(PNG_IHDR, PNG_PLTE, PNG_IDAT, PNG_IEND)
    private const val HEX = "0123456789abcdef"
}
