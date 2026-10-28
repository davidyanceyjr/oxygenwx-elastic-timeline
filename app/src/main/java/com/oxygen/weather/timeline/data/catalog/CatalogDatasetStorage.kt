package com.oxygen.weather.timeline.data.catalog

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant

/** Storage purpose is separate from the source origin captured inside the recording. */
enum class CatalogStorageNamespace { LIVE, SIMULATED, REPLAY }

sealed interface StoredCatalogResult {
    data class Found(val recording: CatalogRecording) : StoredCatalogResult
    data object Missing : StoredCatalogResult
    data class Invalid(val reason: String) : StoredCatalogResult
}

/** Explicit retention: rows remain until a caller deletes them. No write-time eviction. */
class CatalogDatasetStorage(private val dao: CatalogDatasetDao) {
    suspend fun save(namespace: CatalogStorageNamespace, recording: CatalogRecording) {
        val catalog = recording.catalog
        require(namespace == CatalogStorageNamespace.REPLAY || namespace.name == catalog.provenance.origin.name) {
            "Storage namespace does not match captured origin"
        }
        val bytes = RecordedCatalogCodec.encode(recording)
        val version = ObjectMapper().readTree(bytes)["schemaVersion"].intValue()
        dao.replace(CatalogDatasetRow(
            locationId = catalog.location.id.value,
            storageNamespace = namespace.name,
            datasetId = catalog.datasetId.value,
            asOf = catalog.asOf.toString(),
            asOfEpochSecond = catalog.asOf.epochSecond,
            asOfNano = catalog.asOf.nano,
            capturedOrigin = catalog.provenance.origin.name,
            payloadFormatVersion = version,
            payload = bytes,
        ))
    }

    suspend fun load(locationId: LocationId, namespace: CatalogStorageNamespace, datasetId: DatasetId): StoredCatalogResult {
        val row = dao.find(locationId.value, namespace.name, datasetId.value) ?: return StoredCatalogResult.Missing
        if (row.payloadFormatVersion !in 1..2) return StoredCatalogResult.Invalid("Unsupported payload format version")
        val decoded = RecordedCatalogCodec.decode(row.payload)
        if (decoded is RecordingDecodeResult.Failure) return StoredCatalogResult.Invalid("${decoded.kind}: ${decoded.detail}")
        val recording = (decoded as RecordingDecodeResult.Success).recording
        val catalog = recording.catalog
        val payloadVersion = try { ObjectMapper().readTree(row.payload)["schemaVersion"].intValue() } catch (_: Exception) { -1 }
        if (payloadVersion != row.payloadFormatVersion ||
            catalog.location.id.value != row.locationId || catalog.datasetId.value != row.datasetId ||
            catalog.asOf.toString() != row.asOf || catalog.asOf.epochSecond != row.asOfEpochSecond ||
            catalog.asOf.nano != row.asOfNano || catalog.provenance.origin.name != row.capturedOrigin ||
            (namespace != CatalogStorageNamespace.REPLAY && namespace.name != row.capturedOrigin)
        ) return StoredCatalogResult.Invalid("Row metadata does not match recording")
        return StoredCatalogResult.Found(recording)
    }

    suspend fun history(locationId: LocationId, namespace: CatalogStorageNamespace): List<StoredDatasetIdentity> =
        dao.history(locationId.value, namespace.name).map { row ->
            StoredDatasetIdentity(row.locationId, namespace, row.datasetId, Instant.parse(row.asOf), row.capturedOrigin, row.payloadFormatVersion)
        }

    suspend fun delete(locationId: LocationId, namespace: CatalogStorageNamespace, datasetId: DatasetId): Int =
        dao.delete(locationId.value, namespace.name, datasetId.value)

    suspend fun deleteLocation(locationId: LocationId): Int = dao.deleteLocation(locationId.value)
}

data class StoredDatasetIdentity(
    val locationId: String,
    val namespace: CatalogStorageNamespace,
    val datasetId: String,
    val asOf: Instant,
    val capturedOrigin: String,
    val payloadFormatVersion: Int,
)
