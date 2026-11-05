package com.oxygen.weather.timeline.data.catalog

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant

/** Storage purpose is separate from the source origin captured inside the recording. */
enum class CatalogStorageNamespace { LIVE, SIMULATED, REPLAY }

sealed interface StoredCatalogResult {
    data class Found(val recording: CatalogRecording, val storedAt: Instant?, val invalidCandidates: List<String> = emptyList()) : StoredCatalogResult
    data object Missing : StoredCatalogResult
    data class Invalid(val reasons: List<String>) : StoredCatalogResult
}

enum class CatalogPersistResult { PERSISTED, SUPERSEDED }

/** Validates the entire recording envelope and keeps retrieval metadata outside its payload. */
class CatalogDatasetStorage(private val dao: CatalogDatasetDao) {
    suspend fun save(namespace: CatalogStorageNamespace, recording: CatalogRecording, storedAt: Instant? = null, liveSnapshotLimit: Int = 2): CatalogPersistResult {
        val row = encodeRow(namespace, recording, storedAt)
        if (namespace == CatalogStorageNamespace.LIVE) {
            require(liveSnapshotLimit >= 1)
            return if (dao.persistLive(row, liveSnapshotLimit)) CatalogPersistResult.PERSISTED else CatalogPersistResult.SUPERSEDED
        }
        dao.replaceDataset(row)
        return CatalogPersistResult.PERSISTED
    }

    private fun encodeRow(namespace: CatalogStorageNamespace, recording: CatalogRecording, storedAt: Instant?): CatalogDatasetRow {
        require(namespace != CatalogStorageNamespace.LIVE || storedAt != null) {
            "New LIVE snapshots require a retrieval timestamp"
        }
        val catalog = recording.catalog
        require(namespace == CatalogStorageNamespace.REPLAY || namespace.name == catalog.provenance.origin.name) {
            "Storage namespace does not match captured origin"
        }
        val bytes = RecordedCatalogCodec.encode(recording)
        val version = ObjectMapper().readTree(bytes)["schemaVersion"].intValue()
        return CatalogDatasetRow(
            locationId = catalog.location.id.value,
            storageNamespace = namespace.name,
            datasetId = catalog.datasetId.value,
            asOf = catalog.asOf.toString(),
            asOfEpochSecond = catalog.asOf.epochSecond,
            asOfNano = catalog.asOf.nano,
            storedAtEpochSecond = storedAt?.epochSecond,
            storedAtNano = storedAt?.nano,
            capturedOrigin = catalog.provenance.origin.name,
            payloadFormatVersion = version,
            payload = bytes,
        )
    }

    suspend fun load(
        location: CatalogLocation,
        namespace: CatalogStorageNamespace,
        datasetId: DatasetId,
    ): StoredCatalogResult {
        val rows = dao.findCandidates(location.id.value, namespace.name, datasetId.value)
        if (rows.isEmpty()) return StoredCatalogResult.Missing
        val invalid = mutableListOf<String>()
        for (row in rows) {
            val recording = validateRow(row, location, namespace, datasetId, invalid) ?: continue
            val storedAt = if (row.storedAtEpochSecond == null && row.storedAtNano == null) null
                else if (row.storedAtEpochSecond != null && row.storedAtNano != null && row.storedAtNano in 0..999_999_999)
                    Instant.ofEpochSecond(row.storedAtEpochSecond, row.storedAtNano.toLong())
                else {
                    invalid += "Invalid retrieval timestamp metadata"
                    continue
                }
            return StoredCatalogResult.Found(recording, storedAt, invalid.toList())
        }
        return StoredCatalogResult.Invalid(invalid.ifEmpty { listOf("No compatible catalog candidate") })
    }

    private fun validateRow(
        row: CatalogDatasetRow,
        location: CatalogLocation,
        namespace: CatalogStorageNamespace,
        datasetId: DatasetId,
        invalid: MutableList<String>,
    ): CatalogRecording? {
        fun bad(reason: String): CatalogRecording? { invalid += reason; return null }
        if (row.payloadFormatVersion !in 1..5) return bad("Unsupported payload format version")
        val decoded = RecordedCatalogCodec.decode(row.payload)
        if (decoded is RecordingDecodeResult.Failure) return bad("${decoded.kind}: ${decoded.detail}")
        val recording = (decoded as RecordingDecodeResult.Success).recording
        val catalog = recording.catalog
        val payloadVersion = try { ObjectMapper().readTree(row.payload)["schemaVersion"].intValue() } catch (_: Exception) { -1 }
        if (payloadVersion != row.payloadFormatVersion ||
            catalog.location.id.value != row.locationId || catalog.location != location ||
            catalog.datasetId.value != row.datasetId || catalog.datasetId != datasetId ||
            catalog.asOf.toString() != row.asOf || catalog.asOf.epochSecond != row.asOfEpochSecond ||
            catalog.asOf.nano != row.asOfNano || catalog.provenance.origin.name != row.capturedOrigin ||
            (namespace != CatalogStorageNamespace.REPLAY && namespace.name != row.capturedOrigin)
        ) return bad("Row metadata does not match requested location or recording")
        return recording
    }

    suspend fun history(locationId: LocationId, namespace: CatalogStorageNamespace): List<StoredDatasetIdentity> =
        dao.history(locationId.value, namespace.name).map { row ->
            StoredDatasetIdentity(row.locationId, namespace, row.datasetId, Instant.parse(row.asOf), row.capturedOrigin,
                row.payloadFormatVersion, row.storedAtEpochSecond?.let { sec -> row.storedAtNano?.let { Instant.ofEpochSecond(sec, it.toLong()) } })
        }

    suspend fun delete(locationId: LocationId, namespace: CatalogStorageNamespace, datasetId: DatasetId): Int =
        dao.deleteDataset(locationId.value, namespace.name, datasetId.value)

    suspend fun deleteLocation(locationId: LocationId): Int = dao.deleteLocation(locationId.value)
}

data class StoredDatasetIdentity(
    val locationId: String,
    val namespace: CatalogStorageNamespace,
    val datasetId: String,
    val asOf: Instant,
    val capturedOrigin: String,
    val payloadFormatVersion: Int,
    val storedAt: Instant?,
)
