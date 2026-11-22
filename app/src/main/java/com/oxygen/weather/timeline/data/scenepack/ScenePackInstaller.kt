package com.oxygen.weather.timeline.data.scenepack

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import java.util.UUID

enum class ScenePackInstallOutcome {
    INSTALLED,
    REJECTED,
    LIMIT_EXCEEDED,
    INTERRUPTED,
    WRITE_FAILURE,
}

data class ScenePackInstallResult(
    val outcome: ScenePackInstallOutcome,
    val packId: String? = null,
    val contentIdentity: String? = null,
    val candidateVersion: String? = null,
    val currentRegistration: AppOwnedThemeRegistration? = null,
    val reason: String? = null,
    val supersededGenerationCleanupFailed: Boolean = false,
)

/** One app-owned generation resolved from its current pointer and record in the live process. */
data class ScenePackCurrentGeneration(
    val registration: AppOwnedThemeRegistration,
    val manifest: ScenePackManifest,
    val contentIdentity: String,
    val assetBytes: ByteArray,
)

/** OX-57's finite filesystem and working-data limits, independent of OX-91 and OX-55. */
internal object ScenePackInstallLimits {
    const val MAX_GENERATION_NODES = 64L
    const val MAX_RECORD_BYTES = 32_768L
    const val MAX_RESOURCE_BYTES = ScenePackResourceValidator.MAX_RESOURCE_BYTES_AGGREGATE.toLong()
    const val MAX_GENERATION_BYTES = 1_605_632L
    const val MAX_POINTER_BYTES = 16_384L
    const val MAX_CANDIDATE_AND_COMMIT_BYTES = 1_638_400L
    const val MAX_REPLACEMENT_PEAK_BYTES = 3_244_032L
    const val WRITE_BUFFER_BYTES = 16_384L
    const val MAX_PACKAGE_WORKING_BYTES = 1_622_016L

    data class Estimate(
        val materializedNodes: Long,
        val resourceBytes: Long,
        val recordBytes: Long,
        val generationBytes: Long,
        val candidateAndCommitBytes: Long,
        val replacementPeakBytes: Long,
        val workingBytes: Long,
    )

    fun estimate(
        materializedNodes: Long,
        resourceBytes: Long,
        recordBytes: Long,
        oldGenerationBytes: Long,
    ): Estimate {
        val generationBytes = saturatedAdd(resourceBytes, recordBytes)
        val commitReserve = 2L * MAX_POINTER_BYTES
        return Estimate(
            materializedNodes = materializedNodes,
            resourceBytes = resourceBytes,
            recordBytes = recordBytes,
            generationBytes = generationBytes,
            candidateAndCommitBytes = saturatedAdd(generationBytes, commitReserve),
            replacementPeakBytes = saturatedAdd(saturatedAdd(oldGenerationBytes, generationBytes), commitReserve),
            workingBytes = saturatedAdd(saturatedAdd(resourceBytes, recordBytes), WRITE_BUFFER_BYTES),
        )
    }

    fun violations(estimate: Estimate): List<String> = buildList {
        if (estimate.materializedNodes > MAX_GENERATION_NODES) add("materialized filesystem nodes")
        if (estimate.resourceBytes > MAX_RESOURCE_BYTES) add("accepted resource bytes")
        if (estimate.recordBytes > MAX_RECORD_BYTES) add("install record bytes")
        if (estimate.generationBytes > MAX_GENERATION_BYTES) add("expanded generation bytes")
        if (estimate.candidateAndCommitBytes > MAX_CANDIDATE_AND_COMMIT_BYTES) add("candidate plus commit bytes")
        if (estimate.replacementPeakBytes > MAX_REPLACEMENT_PEAK_BYTES) add("same-pack replacement peak bytes")
        if (estimate.workingBytes > MAX_PACKAGE_WORKING_BYTES) add("package working bytes")
    }

    fun materializedNodeCount(assetPath: String): Long? {
        val segments = canonicalPathSegments(assetPath) ?: return null
        // Count the generation root, each required asset ancestor, the asset, and its install record.
        return segments.size.toLong() + 2L
    }

    private fun canonicalPathSegments(path: String): List<String>? {
        if (path.isEmpty() || path.startsWith('/') || path.contains('\\') || path.contains('\u0000')) return null
        if (DRIVE_PREFIX.containsMatchIn(path) || URI_SCHEME_PREFIX.containsMatchIn(path)) return null
        val segments = path.split('/')
        if (segments.any { it.isEmpty() || it == "." || it == ".." }) return null
        return segments
    }

    internal fun saturatedAdd(left: Long, right: Long): Long =
        if (Long.MAX_VALUE - left < right) Long.MAX_VALUE else left + right

    private val DRIVE_PREFIX = Regex("^[A-Za-z]:")
    private val URI_SCHEME_PREFIX = Regex("^[A-Za-z][A-Za-z0-9+.-]*:")
}

/**
 * Installs only complete OX-91 accepted values. The app-private per-pack current pointer is the
 * sole selector for a committed generation; this class never discovers registrations by scanning
 * staging folders. Cold-relaunch enumeration remains outside OX-57.
 */
class ScenePackInstaller(private val appFilesDirectory: File) {
    private val installLock = Any()
    private val knownPackIds = linkedSetOf<String>()
    private val storageRoot = File(appFilesDirectory, STORAGE_DIRECTORY)

    fun install(
        archiveFile: File,
        displayName: String,
        version: String,
        interruptAfterFirstResourceWrite: Boolean = false,
    ): ScenePackInstallResult = synchronized(installLock) {
        when (val validated = ScenePackResourceValidator.validate(archiveFile)) {
            is ScenePackResourceValidationResult.Rejected -> ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.REJECTED,
                reason = "OX91_${validated.reason.name}",
            )
            is ScenePackResourceValidationResult.Accepted -> installAccepted(
                accepted = validated,
                displayName = displayName,
                version = version,
                interruptAfterFirstResourceWrite = interruptAfterFirstResourceWrite,
            )
        }
    }

    /** The query remains explicit: only IDs installed by this live process are considered. */
    fun queryCatalog(bundledFreeMetadata: BundledFreeThemeMetadata): List<InstalledThemeCatalogEntry> =
        synchronized(installLock) {
            val registrations = knownPackIds.mapNotNull(::readCurrentRegistration)
            InstalledThemeCatalog.query(bundledFreeMetadata, registrations)
        }

    /**
     * Resolve only a registration known to this installer instance. The current-generation
     * pointer selects the record, and that record's manifest path selects the exact asset bytes.
     */
    fun resolveCurrentGeneration(entry: InstalledThemeCatalogEntry): ScenePackCurrentGeneration? =
        synchronized(installLock) {
            val packId = entry.id.value
            if (entry.origin != InstalledThemeOrigin.APP_OWNED ||
                entry.validity != InstalledThemeValidity.COMPATIBLE ||
                !PACK_ID.matches(packId) || packId !in knownPackIds
            ) return@synchronized null

            try {
                val packRoot = File(storageRoot, packId)
                val pointerFile = File(packRoot, CURRENT_POINTER_FILE)
                if (!ScenePackInstallPaths.isConfined(storageRoot, packRoot) ||
                    !ScenePackInstallPaths.isConfined(packRoot, pointerFile)
                ) return@synchronized null
                val pointer = readPointer(pointerFile) ?: return@synchronized null
                val generationsRoot = File(packRoot, GENERATIONS_DIRECTORY)
                val generationDirectory = File(generationsRoot, pointer.generationId)
                if (!generationDirectory.isDirectory ||
                    !ScenePackInstallPaths.isConfined(generationsRoot, generationDirectory)
                ) return@synchronized null
                val record = readRecord(generationDirectory, pointer.recordFileName)
                    ?: return@synchronized null
                val registration = record.registration
                if (record.manifest.packId != packId || registration.packId != packId ||
                    registration.displayName != entry.displayName || registration.version != entry.version ||
                    registration.compatibility != AppOwnedThemeCompatibility.COMPATIBLE
                ) return@synchronized null

                val resource = ScenePackInstallPaths.resolveConfinedOutput(
                    generationDirectory,
                    record.manifest.assetReference.path,
                ) ?: return@synchronized null
                if (!resource.isFile || resource.length() > ScenePackInstallLimits.MAX_RESOURCE_BYTES) {
                    return@synchronized null
                }
                val bytes = readBounded(resource, ScenePackInstallLimits.MAX_RESOURCE_BYTES.toInt())
                val digest = MessageDigest.getInstance("SHA-256").digest(bytes).toHex()
                if (digest != record.manifest.assetReference.sha256) return@synchronized null

                ScenePackCurrentGeneration(
                    registration = registration,
                    manifest = record.manifest,
                    contentIdentity = record.contentIdentity,
                    assetBytes = bytes,
                )
            } catch (_: IOException) {
                null
            }
        }

    private fun installAccepted(
        accepted: ScenePackResourceValidationResult.Accepted,
        displayName: String,
        version: String,
        interruptAfterFirstResourceWrite: Boolean,
    ): ScenePackInstallResult {
        val manifest = accepted.manifest
        val packId = manifest.packId
        if (!PACK_ID.matches(packId)) return rejected(packId, version, "invalid OX-20 packId")
        if (displayName.isEmpty()) return rejected(packId, version, "app-managed display name is empty")
        val installedVersion = try {
            InstalledThemeVersion(version)
        } catch (_: IllegalArgumentException) {
            return rejected(packId, version, "app-managed version is empty")
        }

        val assetPath = manifest.assetReference.path
        val assetBytes = accepted.assetsByPath[assetPath]
            ?: return rejected(packId, version, "accepted resource map is incomplete")
        if (accepted.assetsByPath.size != 1) {
            return rejected(packId, version, "accepted resource map does not match the current OX-30 profile")
        }

        val recordFileName = newRecordFileName(assetPath)
            ?: return rejected(packId, version, "could not choose an install-record path")
        val registration = AppOwnedThemeRegistration(
            packId = packId,
            version = installedVersion,
            displayName = displayName,
            compatibility = AppOwnedThemeCompatibility.COMPATIBLE,
        )
        val persistedRecord = PersistedScenePackRecord(
            manifest = manifest,
            contentIdentity = accepted.contentIdentity,
            registration = registration,
        )
        val recordBytes = ScenePackInstallRecordCodec.encodedSize(persistedRecord)
            ?: return limitExceeded(packId, accepted, version, null, "install record bytes")
        val nodeCount = ScenePackInstallLimits.materializedNodeCount(assetPath)
            ?: return rejected(packId, version, "resource path is not canonical and relative")
        val resourceBytes = assetBytes.size.toLong()
        if (resourceBytes > ScenePackInstallLimits.MAX_RESOURCE_BYTES) {
            return limitExceeded(packId, accepted, version, null, "accepted resource bytes")
        }

        val packRoot = File(storageRoot, packId)
        val pointerFile = File(packRoot, CURRENT_POINTER_FILE)
        val oldPointer: CurrentGenerationPointer?
        val oldRecord: PersistedScenePackRecord?
        val oldGenerationDirectory: File?
        val oldGenerationBytes: Long
        try {
            oldPointer = readPointer(pointerFile)
            if (oldPointer == null) {
                oldRecord = null
                oldGenerationDirectory = null
                oldGenerationBytes = 0L
            } else {
                oldGenerationDirectory = File(File(packRoot, GENERATIONS_DIRECTORY), oldPointer.generationId)
                oldRecord = readRecord(oldGenerationDirectory, oldPointer.recordFileName)
                    ?: throw IOException("Current generation record is missing or unreadable")
                if (oldRecord.manifest.packId != packId) throw IOException("Current generation packId mismatch")
                oldGenerationBytes = generationBytes(oldGenerationDirectory, oldPointer, oldRecord)
            }
        } catch (error: Exception) {
            return ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.WRITE_FAILURE,
                packId = packId,
                contentIdentity = accepted.contentIdentity,
                candidateVersion = version,
                reason = error.message ?: "could not read current generation",
            )
        }

        val estimate = ScenePackInstallLimits.estimate(
            materializedNodes = nodeCount,
            resourceBytes = resourceBytes,
            recordBytes = recordBytes,
            oldGenerationBytes = oldGenerationBytes,
        )
        val violations = ScenePackInstallLimits.violations(estimate)
        if (violations.isNotEmpty()) {
            return limitExceeded(packId, accepted, version, oldRecord?.registration, violations.joinToString(","))
        }

        return installLockInternal(
            accepted = accepted,
            registration = registration,
            persistedRecord = persistedRecord,
            recordFileName = recordFileName,
            assetBytes = assetBytes,
            nodeCount = nodeCount,
            oldPointer = oldPointer,
            oldRecord = oldRecord,
            packRoot = packRoot,
            pointerFile = pointerFile,
            interruptAfterFirstResourceWrite = interruptAfterFirstResourceWrite,
        )
    }

    private fun installLockInternal(
        accepted: ScenePackResourceValidationResult.Accepted,
        registration: AppOwnedThemeRegistration,
        persistedRecord: PersistedScenePackRecord,
        recordFileName: String,
        assetBytes: ByteArray,
        nodeCount: Long,
        oldPointer: CurrentGenerationPointer?,
        oldRecord: PersistedScenePackRecord?,
        packRoot: File,
        pointerFile: File,
        interruptAfterFirstResourceWrite: Boolean,
    ): ScenePackInstallResult {
        val packId = registration.packId
        val stagingRoot = File(packRoot, STAGING_DIRECTORY)
        val generationsRoot = File(packRoot, GENERATIONS_DIRECTORY)
        val pendingPointer = File(packRoot, PENDING_POINTER_FILE)
        var finalGeneration: File? = null
        var committed = false

        try {
            ensureStoreDirectory(storageRoot)
            ensureStoreDirectory(packRoot)
            ensureStoreDirectory(generationsRoot)
            if (!cleanupAbandoned(packRoot, stagingRoot, generationsRoot, pendingPointer, oldPointer)) {
                throw IOException("Could not remove abandoned staging or commit files")
            }

            val expectedNodes = nodeCount
            val stagedAssetFile = ScenePackInstallPaths.resolveConfinedOutput(
                stagingRoot,
                accepted.manifest.assetReference.path,
            ) ?: throw CandidateRejected("resource output escapes the private generation")
            val stagedRecordFile = File(stagingRoot, recordFileName)
            if (!ScenePackInstallPaths.isConfined(stagingRoot, stagedRecordFile)) {
                throw CandidateRejected("install record escapes the private generation")
            }

            if (!stagingRoot.mkdirs() && !stagingRoot.isDirectory) throw IOException("Could not create staging generation")
            ensureConfinedDirectory(storageRoot, stagingRoot)
            createParentDirectories(stagingRoot, stagedAssetFile.parentFile ?: stagingRoot)
            writeAsset(stagedAssetFile, assetBytes)
            if (interruptAfterFirstResourceWrite) throw InjectedInstallInterruption()
            writeInstallRecord(stagedRecordFile, persistedRecord)

            if (stagedAssetFile.length() != assetBytes.size.toLong()) {
                throw IOException("Staged resource length does not match the accepted bytes")
            }
            if (!verifyResourceDigest(stagedAssetFile, accepted.manifest.assetReference.sha256)) {
                throw IOException("Staged resource bytes do not match the accepted digest")
            }
            if (stagedRecordFile.length() != ScenePackInstallRecordCodec.encodedSize(persistedRecord)) {
                throw IOException("Staged install record length changed")
            }
            val stagedRoundTrip = readRecord(stagingRoot, recordFileName)
            if (stagedRoundTrip != persistedRecord) throw IOException("Staged install record did not round-trip")
            if (countGenerationNodes(stagingRoot) != expectedNodes) {
                throw IOException("Materialized filesystem nodes differ from the preflight count")
            }

            val generationId = newGenerationId()
            finalGeneration = File(generationsRoot, generationId)
            moveAtomically(stagingRoot, finalGeneration)

            val pointer = CurrentGenerationPointer(generationId, recordFileName)
            writePendingPointer(pendingPointer, pointer)
            moveAtomically(pendingPointer, pointerFile, replace = true)
            committed = true
            knownPackIds += packId

            val supersededCleanupFailed = oldPointer?.let { previous ->
                val oldDirectory = File(generationsRoot, previous.generationId)
                oldDirectory != finalGeneration && !deleteTree(oldDirectory)
            } ?: false

            return ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.INSTALLED,
                packId = packId,
                contentIdentity = accepted.contentIdentity,
                candidateVersion = registration.version.value,
                currentRegistration = registration,
                supersededGenerationCleanupFailed = supersededCleanupFailed,
            )
        } catch (interrupted: InjectedInstallInterruption) {
            val cleaned = cleanupCandidate(stagingRoot, pendingPointer, finalGeneration, committed)
            return ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.INTERRUPTED,
                packId = packId,
                contentIdentity = accepted.contentIdentity,
                candidateVersion = registration.version.value,
                currentRegistration = oldRecord?.registration,
                reason = if (cleaned) "injected after first staged resource" else "injected interruption; candidate cleanup failed",
            )
        } catch (rejected: CandidateRejected) {
            val cleaned = cleanupCandidate(stagingRoot, pendingPointer, finalGeneration, committed)
            return ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.REJECTED,
                packId = packId,
                contentIdentity = accepted.contentIdentity,
                candidateVersion = registration.version.value,
                currentRegistration = oldRecord?.registration,
                reason = if (cleaned) rejected.message else "${rejected.message}; candidate cleanup failed",
            )
        } catch (error: Exception) {
            val cleaned = cleanupCandidate(stagingRoot, pendingPointer, finalGeneration, committed)
            return ScenePackInstallResult(
                outcome = ScenePackInstallOutcome.WRITE_FAILURE,
                packId = packId,
                contentIdentity = accepted.contentIdentity,
                candidateVersion = registration.version.value,
                currentRegistration = oldRecord?.registration,
                reason = if (cleaned) error.message ?: "write failed" else "${error.message ?: "write failed"}; candidate cleanup failed",
            )
        }
    }

    private fun readCurrentRegistration(packId: String): AppOwnedThemeRegistration? {
        val packRoot = File(storageRoot, packId)
        val pointer = readPointer(File(packRoot, CURRENT_POINTER_FILE)) ?: return null
        val generationDirectory = File(File(packRoot, GENERATIONS_DIRECTORY), pointer.generationId)
        val record = readRecord(generationDirectory, pointer.recordFileName) ?: return null
        if (record.manifest.packId != packId || record.registration.packId != packId) return null
        return record.registration
    }

    private fun readPointer(file: File): CurrentGenerationPointer? {
        if (!file.exists()) return null
        if (!file.isFile || file.length() > ScenePackInstallLimits.MAX_POINTER_BYTES) {
            throw IOException("Current-generation pointer is not a bounded file")
        }
        val bytes = readBounded(file, ScenePackInstallLimits.MAX_POINTER_BYTES.toInt())
        return CurrentGenerationPointerCodec.decode(bytes)
    }

    private fun readRecord(generationDirectory: File, recordFileName: String): PersistedScenePackRecord? {
        if (!RECORD_FILE.matches(recordFileName)) return null
        val file = File(generationDirectory, recordFileName)
        if (!ScenePackInstallPaths.isConfined(generationDirectory, file)) return null
        if (!file.isFile || file.length() > ScenePackInstallLimits.MAX_RECORD_BYTES) return null
        val bytes = readBounded(file, ScenePackInstallLimits.MAX_RECORD_BYTES.toInt())
        return ScenePackInstallRecordCodec.decode(bytes)
    }

    private fun generationBytes(
        directory: File,
        pointer: CurrentGenerationPointer,
        record: PersistedScenePackRecord,
    ): Long {
        val resource = ScenePackInstallPaths.resolveConfinedOutput(directory, record.manifest.assetReference.path)
            ?: throw IOException("Current resource escapes its generation")
        val installRecord = File(directory, pointer.recordFileName)
        if (!resource.isFile || !installRecord.isFile) throw IOException("Current generation is incomplete")
        return ScenePackInstallLimits.saturatedAdd(resource.length(), installRecord.length())
    }

    private fun cleanupAbandoned(
        packRoot: File,
        stagingRoot: File,
        generationsRoot: File,
        pendingPointer: File,
        currentPointer: CurrentGenerationPointer?,
    ): Boolean {
        if (stagingRoot.exists() && !deleteTree(stagingRoot)) return false
        if (pendingPointer.exists() && !pendingPointer.delete()) return false
        if (!generationsRoot.exists()) return true
        val children = generationsRoot.listFiles() ?: return false
        children.forEach { child ->
            if (child.name != currentPointer?.generationId && !deleteTree(child)) return false
        }
        return true
    }

    private fun cleanupCandidate(
        stagingRoot: File,
        pendingPointer: File,
        finalGeneration: File?,
        committed: Boolean,
    ): Boolean {
        if (committed) return true
        var cleaned = true
        if (stagingRoot.exists() && !deleteTree(stagingRoot)) cleaned = false
        if (pendingPointer.exists() && !pendingPointer.delete()) cleaned = false
        if (finalGeneration?.exists() == true && !deleteTree(finalGeneration)) cleaned = false
        return cleaned
    }

    private fun writeAsset(file: File, bytes: ByteArray) {
        FileOutputStream(file, false).use { raw ->
            BufferedOutputStream(raw, ScenePackInstallLimits.WRITE_BUFFER_BYTES.toInt()).use { buffered ->
                buffered.write(bytes)
                buffered.flush()
                raw.fd.sync()
            }
        }
    }

    private fun writeInstallRecord(file: File, record: PersistedScenePackRecord) {
        FileOutputStream(file, false).use { raw ->
            BufferedOutputStream(raw, ScenePackInstallLimits.WRITE_BUFFER_BYTES.toInt()).use { buffered ->
                val output = DataOutputStream(buffered)
                ScenePackInstallRecordCodec.write(output, record)
                output.flush()
                raw.fd.sync()
            }
        }
    }

    private fun writePendingPointer(file: File, pointer: CurrentGenerationPointer) {
        FileOutputStream(file, false).use { raw ->
            BufferedOutputStream(raw, ScenePackInstallLimits.WRITE_BUFFER_BYTES.toInt()).use { buffered ->
                val output = DataOutputStream(buffered)
                CurrentGenerationPointerCodec.write(output, pointer)
                output.flush()
                raw.fd.sync()
            }
        }
        if (file.length() > ScenePackInstallLimits.MAX_POINTER_BYTES) throw IOException("Current pointer exceeds its cap")
    }

    private fun writeAtomicMove(source: File, destination: File, replace: Boolean = false) {
        val options = if (replace) {
            arrayOf(StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
        } else {
            arrayOf(StandardCopyOption.ATOMIC_MOVE)
        }
        try {
            Files.move(source.toPath(), destination.toPath(), *options)
        } catch (error: AtomicMoveNotSupportedException) {
            throw IOException("Filesystem does not support the required atomic rename", error)
        }
    }

    private fun moveAtomically(source: File, destination: File, replace: Boolean = false) =
        writeAtomicMove(source, destination, replace)

    private fun createParentDirectories(root: File, parent: File) {
        val rootPath = root.canonicalFile.toPath()
        val parentPath = parent.canonicalFile.toPath()
        if (!parentPath.startsWith(rootPath)) throw CandidateRejected("resource parent escapes its generation")
        var current = root
        val relative = rootPath.relativize(parentPath)
        for (part in relative) {
            current = File(current, part.toString())
            if (!current.exists() && !current.mkdir()) throw IOException("Could not create resource directory")
            ensureConfinedDirectory(root, current)
        }
    }

    private fun ensureStoreDirectory(directory: File) {
        if (!directory.exists() && !directory.mkdirs() && !directory.isDirectory) {
            throw IOException("Could not create app-owned pack directory")
        }
        if (!directory.isDirectory) throw IOException("App-owned pack path is not a directory")
    }

    private fun ensureConfinedDirectory(root: File, directory: File) {
        val rootPath = root.canonicalFile.toPath()
        val directoryPath = directory.canonicalFile.toPath()
        if (!directoryPath.startsWith(rootPath)) throw CandidateRejected("directory escaped its private root")
    }

    private fun countGenerationNodes(root: File): Long {
        var count = 1L // generation root
        val children = root.listFiles() ?: return count
        children.forEach { child ->
            if (Files.isSymbolicLink(child.toPath())) throw CandidateRejected("staged symlink is not allowed")
            if (child.isDirectory) count += countDirectory(child)
            else if (child.isFile) count += 1L
            else throw IOException("Staged generation contains an unsupported filesystem node")
        }
        return count
    }

    private fun countDirectory(directory: File): Long {
        var count = 1L
        val children = directory.listFiles() ?: throw IOException("Could not inspect staged directory")
        children.forEach { child ->
            if (Files.isSymbolicLink(child.toPath())) throw CandidateRejected("staged symlink is not allowed")
            count += if (child.isDirectory) countDirectory(child) else if (child.isFile) 1L
            else throw IOException("Staged generation contains an unsupported filesystem node")
        }
        return count
    }

    private fun verifyResourceDigest(file: File, expectedHex: String): Boolean {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { input ->
            val buffered = BufferedInputStream(input, ScenePackInstallLimits.WRITE_BUFFER_BYTES.toInt())
            val bytes = ByteArray(ScenePackInstallLimits.WRITE_BUFFER_BYTES.toInt())
            while (true) {
                val count = buffered.read(bytes)
                if (count < 0) break
                digest.update(bytes, 0, count)
            }
        }
        return digest.digest().toHex() == expectedHex
    }

    private fun newRecordFileName(assetPath: String): String? {
        repeat(16) {
            val name = "install-${UUID.randomUUID().toString().replace("-", "")}.bin"
            if (!assetPathConflictsWithRecord(assetPath, name)) return name
        }
        return null
    }

    private fun generationBytes(
        directory: File,
        pointer: CurrentGenerationPointer,
    ): Long {
        val record = readRecord(directory, pointer.recordFileName) ?: throw IOException("Current generation record is unreadable")
        return generationBytes(directory, pointer, record)
    }

    private fun rejected(packId: String, version: String, reason: String) = ScenePackInstallResult(
        outcome = ScenePackInstallOutcome.REJECTED,
        packId = packId,
        candidateVersion = version,
        reason = reason,
    )

    private fun limitExceeded(
        packId: String,
        accepted: ScenePackResourceValidationResult.Accepted,
        version: String,
        current: AppOwnedThemeRegistration?,
        reason: String,
    ) = ScenePackInstallResult(
        outcome = ScenePackInstallOutcome.LIMIT_EXCEEDED,
        packId = packId,
        contentIdentity = accepted.contentIdentity,
        candidateVersion = version,
        currentRegistration = current,
        reason = reason,
    )

    private fun assetPathConflictsWithRecord(assetPath: String, recordFileName: String): Boolean =
        assetPath == recordFileName || assetPath.startsWith("$recordFileName/") || recordFileName.startsWith("$assetPath/")

    private fun newGenerationId(): String = "g-${UUID.randomUUID().toString().replace("-", "")}"

    private fun deleteTree(file: File): Boolean {
        if (!file.exists()) return true
        return try {
            if (Files.isSymbolicLink(file.toPath())) return Files.deleteIfExists(file.toPath())
            if (file.isDirectory) {
                val children = file.listFiles() ?: return false
                if (children.any { !deleteTree(it) }) return false
            }
            file.delete()
        } catch (_: IOException) {
            false
        } catch (_: SecurityException) {
            false
        }
    }

    private fun readBounded(file: File, limit: Int): ByteArray {
        val length = file.length()
        if (length < 0L || length > limit.toLong()) throw IOException("Stored metadata exceeds its byte cap")
        val bytes = ByteArray(length.toInt())
        var offset = 0
        FileInputStream(file).use { input ->
            while (offset < bytes.size) {
                val count = input.read(bytes, offset, bytes.size - offset)
                if (count < 0) throw IOException("Stored metadata changed while being read")
                offset += count
            }
            if (input.read() != -1) throw IOException("Stored metadata exceeds its byte cap")
        }
        return bytes
    }

    private class InjectedInstallInterruption : IOException()

    private class CandidateRejected(message: String) : IOException(message)

    companion object {
        private const val STORAGE_DIRECTORY = "installed-scene-packs"
        private const val GENERATIONS_DIRECTORY = "generations"
        private const val STAGING_DIRECTORY = "staging"
        private const val CURRENT_POINTER_FILE = "current-generation"
        private const val PENDING_POINTER_FILE = "current-generation.pending"
        private val PACK_ID = Regex("[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?")
        private val RECORD_FILE = Regex("install-[a-f0-9]{32}\\.bin")
        private val GENERATION_ID = Regex("g-[a-f0-9]{32}")
    }
}

internal object ScenePackInstallPaths {
    fun resolveConfinedOutput(root: File, relativePath: String): File? {
        if (ScenePackInstallLimits.materializedNodeCount(relativePath) == null) return null
        val canonicalRoot = root.canonicalFile.toPath()
        val output = File(root, relativePath).canonicalFile
        if (output.toPath() == canonicalRoot || !output.toPath().startsWith(canonicalRoot)) return null
        return output
    }

    fun isConfined(root: File, target: File): Boolean {
        val canonicalRoot = root.canonicalFile.toPath()
        val canonicalTarget = target.canonicalFile.toPath()
        return canonicalTarget != canonicalRoot && canonicalTarget.startsWith(canonicalRoot)
    }
}

internal data class PersistedScenePackRecord(
    val manifest: ScenePackManifest,
    val contentIdentity: String,
    val registration: AppOwnedThemeRegistration,
)

private data class CurrentGenerationPointer(
    val generationId: String,
    val recordFileName: String,
)

internal object ScenePackInstallRecordCodec {
    private const val MAGIC = 0x4f583537 // OX57
    private const val SCHEMA_VERSION = 1
    private const val COMPATIBLE = 1

    fun encodedSize(record: PersistedScenePackRecord): Long? {
        var size = 8L // magic and schema version
        size = addStringSize(size, record.manifest.packId) ?: return null
        size += 4L
        size = addStringSize(size, record.manifest.worldDefinitionRef) ?: return null
        size += 12L // engine compatibility, renderer policy version, capability count
        record.manifest.requiredCapabilities.forEach { size = addStringSize(size, it) ?: return null }
        size = addStringSize(size, record.manifest.assetReference.refId) ?: return null
        size = addStringSize(size, record.manifest.assetReference.path) ?: return null
        size = addStringSize(size, record.manifest.assetReference.mediaType) ?: return null
        size = addStringSize(size, record.manifest.assetReference.sha256) ?: return null
        size = addStringSize(size, record.contentIdentity) ?: return null
        size = addStringSize(size, record.registration.displayName) ?: return null
        size = addStringSize(size, record.registration.version.value) ?: return null
        size += 1L // compatibility disposition
        return size.takeIf { it <= ScenePackInstallLimits.MAX_RECORD_BYTES }
    }

    fun write(output: DataOutputStream, record: PersistedScenePackRecord) {
        output.writeInt(MAGIC)
        output.writeInt(SCHEMA_VERSION)
        output.writeUTF(record.manifest.packId)
        output.writeInt(record.manifest.manifestVersion)
        output.writeUTF(record.manifest.worldDefinitionRef)
        output.writeInt(record.manifest.engineCompatibility)
        output.writeInt(record.manifest.rendererPolicyVersion)
        output.writeInt(record.manifest.requiredCapabilities.size)
        record.manifest.requiredCapabilities.forEach(output::writeUTF)
        output.writeUTF(record.manifest.assetReference.refId)
        output.writeUTF(record.manifest.assetReference.path)
        output.writeUTF(record.manifest.assetReference.mediaType)
        output.writeUTF(record.manifest.assetReference.sha256)
        output.writeUTF(record.contentIdentity)
        output.writeUTF(record.registration.displayName)
        output.writeUTF(record.registration.version.value)
        output.writeByte(COMPATIBLE)
    }

    fun decode(bytes: ByteArray): PersistedScenePackRecord? = try {
        DataInputStream(bytes.inputStream()).use { input ->
            if (input.readInt() != MAGIC || input.readInt() != SCHEMA_VERSION) return null
            val packId = input.readUTF()
            val manifestVersion = input.readInt()
            val worldDefinitionRef = input.readUTF()
            val engineCompatibility = input.readInt()
            val rendererPolicyVersion = input.readInt()
            val capabilityCount = input.readInt()
            if (capabilityCount !in 0..16) return null
            val capabilities = List(capabilityCount) { input.readUTF() }
            val reference = ScenePackAssetReference(
                refId = input.readUTF(),
                path = input.readUTF(),
                mediaType = input.readUTF(),
                sha256 = input.readUTF(),
            )
            val contentIdentity = input.readUTF()
            val displayName = input.readUTF()
            val version = input.readUTF()
            val compatibility = input.readUnsignedByte()
            if (compatibility != COMPATIBLE || input.read() != -1) return null
            val manifest = ScenePackManifest(
                packId = packId,
                manifestVersion = manifestVersion,
                worldDefinitionRef = worldDefinitionRef,
                engineCompatibility = engineCompatibility,
                rendererPolicyVersion = rendererPolicyVersion,
                requiredCapabilities = capabilities,
                assetReference = reference,
            )
            PersistedScenePackRecord(
                manifest = manifest,
                contentIdentity = contentIdentity,
                registration = AppOwnedThemeRegistration(
                    packId = packId,
                    version = InstalledThemeVersion(version),
                    displayName = displayName,
                    compatibility = AppOwnedThemeCompatibility.COMPATIBLE,
                ),
            )
        }
    } catch (_: Exception) {
        null
    }

    private fun addStringSize(current: Long, value: String): Long? {
        val length = modifiedUtfLength(value) ?: return null
        val updated = current + 2L + length
        return updated.takeIf { it <= ScenePackInstallLimits.MAX_RECORD_BYTES }
    }

    private fun modifiedUtfLength(value: String): Long? {
        var size = 0L
        value.forEach { char ->
            size += when {
                char.code in 0x0001..0x007f -> 1L
                char.code <= 0x07ff -> 2L
                else -> 3L
            }
            if (size > ScenePackInstallLimits.MAX_RECORD_BYTES) return null
        }
        return size
    }
}

private object CurrentGenerationPointerCodec {
    private const val MAGIC = 0x4f583537 // OX57
    private const val SCHEMA_VERSION = 1

    fun write(output: DataOutputStream, pointer: CurrentGenerationPointer) {
        output.writeInt(MAGIC)
        output.writeInt(SCHEMA_VERSION)
        output.writeUTF(pointer.generationId)
        output.writeUTF(pointer.recordFileName)
    }

    fun decode(bytes: ByteArray): CurrentGenerationPointer {
        DataInputStream(bytes.inputStream()).use { input ->
            if (input.readInt() != MAGIC || input.readInt() != SCHEMA_VERSION) throw IOException("Unsupported current-generation pointer")
            val generationId = input.readUTF()
            val recordFileName = input.readUTF()
            if (input.read() != -1 || !Regex("g-[a-f0-9]{32}").matches(generationId) ||
                !Regex("install-[a-f0-9]{32}\\.bin").matches(recordFileName)
            ) {
                throw IOException("Invalid current-generation pointer")
            }
            return CurrentGenerationPointer(generationId, recordFileName)
        }
    }
}

private fun assetPathConflictsWithRecord(assetPath: String, recordFileName: String): Boolean =
    assetPath == recordFileName || assetPath.startsWith("$recordFileName/") || recordFileName.startsWith("$assetPath/")

private fun ByteArray.toHex(): String = joinToString("") { byte -> "%02x".format(byte.toInt() and 0xff) }
