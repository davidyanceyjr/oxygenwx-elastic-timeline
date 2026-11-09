package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class CatalogDatasetStorageTest {
    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))
    private val request = CatalogLoadRequest(location, DatasetId("sequence"), Instant.parse("2026-03-08T12:00:00.123456789Z"))

    private suspend fun recording(): CatalogRecording {
        val result = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
        return CatalogRecording(result.catalog, result.expectedSupport)
    }

    @Test fun acceptsVersionSixPayload() = runBlocking {
        val dao = MemoryDao()
        val storage = CatalogDatasetStorage(dao)
        storage.save(CatalogStorageNamespace.SIMULATED, recording())
        assertEquals(6, dao.rows.values.single().payloadFormatVersion)
        assertTrue(storage.load(location, CatalogStorageNamespace.SIMULATED, request.datasetId) is StoredCatalogResult.Found)
    }

    private class MemoryDao : CatalogDatasetDao {
        val rows = linkedMapOf<String, CatalogDatasetRow>()
        private fun key(row: CatalogDatasetRow) = "${row.locationId}|${row.storageNamespace}|${row.datasetId}|${row.asOfEpochSecond}|${row.asOfNano}"
        var failNextReplace = false
        override suspend fun replace(row: CatalogDatasetRow) {
            if (failNextReplace) { failNextReplace = false; error("injected write failure") }
            rows[key(row)] = row
        }
        override suspend fun findCandidates(locationId: String, namespace: String, datasetId: String) = rows.values.filter { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun history(locationId: String, namespace: String) = rows.values.filter { it.locationId == locationId && it.storageNamespace == namespace }.sortedWith(compareByDescending<CatalogDatasetRow> { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun liveRetentionOrder(locationId: String) = rows.values.filter { it.locationId == locationId && it.storageNamespace == "LIVE" }.sortedWith(compareByDescending<CatalogDatasetRow> { it.storedAtEpochSecond ?: Long.MIN_VALUE }.thenByDescending { it.storedAtNano ?: 0 }.thenByDescending { it.asOfEpochSecond }.thenByDescending { it.asOfNano })
        override suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int { val row = rows.values.firstOrNull { it.locationId == locationId && it.storageNamespace == "LIVE" && it.datasetId == datasetId && it.asOfEpochSecond == asOfSecond && it.asOfNano == asOfNano } ?: return 0; rows.remove(key(row)); return 1 }
        override suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int { val keys = rows.filterValues { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.keys.toList(); keys.forEach(rows::remove); return keys.size }
        override suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int): Boolean {
            val newest = findCandidates(row.locationId, "LIVE", row.datasetId).firstOrNull()
            if (newest != null && (newest.asOfEpochSecond > row.asOfEpochSecond || newest.asOfEpochSecond == row.asOfEpochSecond && newest.asOfNano > row.asOfNano)) return false
            replace(row)
            liveRetentionOrder(row.locationId).drop(retentionLimit).forEach { deleteWindow(it.locationId, it.datasetId, it.asOfEpochSecond, it.asOfNano) }
            return true
        }
        override suspend fun deleteLocation(locationId: String): Int {
            val keys = rows.filterValues { it.locationId == locationId }.keys.toList()
            keys.forEach(rows::remove)
            return keys.size
        }
    }

    @Test fun roundTripNamespacesAndFailurePreservePriorRow() = runBlocking {
        val dao = MemoryDao()
        val storage = CatalogDatasetStorage(dao)
        val simulated = recording()
        assertEquals(44, simulated.expectedSupport.size)
        val live = CatalogRecording(CanonicalCatalog(simulated.catalog.location, simulated.catalog.datasetId,
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST, "live-provider", "run", request.asOf),
            simulated.catalog.asOf, simulated.catalog.facts), simulated.expectedSupport)
        storage.save(CatalogStorageNamespace.SIMULATED, simulated)
        storage.save(CatalogStorageNamespace.LIVE, live, request.asOf)
        storage.save(CatalogStorageNamespace.REPLAY, live)
        assertEquals(simulated.catalog, (storage.load(location, CatalogStorageNamespace.SIMULATED, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
        assertEquals(live.catalog, (storage.load(location, CatalogStorageNamespace.LIVE, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
        assertEquals(live.catalog, (storage.load(location, CatalogStorageNamespace.REPLAY, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
        assertEquals(3, dao.rows.size)
        dao.failNextReplace = true
        try { storage.save(CatalogStorageNamespace.LIVE, live, request.asOf); fail("write should fail") } catch (_: IllegalStateException) { }
        assertEquals(live.catalog, (storage.load(location, CatalogStorageNamespace.LIVE, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
        assertEquals(1, storage.delete(location.id, CatalogStorageNamespace.SIMULATED, request.datasetId))
        assertEquals(2, dao.rows.size)
        assertEquals(2, storage.deleteLocation(location.id))
    }

    @Test fun rejectsMetadataAndPayloadCorruption() = runBlocking {
        val dao = MemoryDao()
        val storage = CatalogDatasetStorage(dao)
        storage.save(CatalogStorageNamespace.SIMULATED, recording())
        val key = dao.rows.keys.single()
        val original = dao.rows.getValue(key)
        listOf(
            original.copy(capturedOrigin = "LIVE"),
            original.copy(asOf = request.asOf.plusSeconds(1).toString()),
            original.copy(asOfEpochSecond = original.asOfEpochSecond + 1),
            original.copy(asOfNano = 0),
            original.copy(payloadFormatVersion = 99),
            original.copy(payload = "broken".toByteArray()),
            original.copy(payloadFormatVersion = 2),
        ).forEach { altered ->
            dao.rows[key] = altered
            assertTrue(storage.load(location, CatalogStorageNamespace.SIMULATED, request.datasetId) is StoredCatalogResult.Invalid)
        }
        dao.rows[key] = original
        assertTrue(storage.load(location, CatalogStorageNamespace.SIMULATED, request.datasetId) is StoredCatalogResult.Found)
    }

    @Test fun versionTwoLiveAndReplayKeepSegmentsDailySupportAndDst() = runBlocking {
        val base = recording()
        val anchor = request.asOf.atZone(location.timeZone).toLocalDate()
        val historical = anchor.minusDays(30)
        val segments = listOf(
            CatalogProductSegment(historical, anchor, CatalogProductKind.ARCHIVED_MODEL_FORECAST, "provider", "history", request.asOf.minusSeconds(1)),
            CatalogProductSegment(anchor, anchor.plusDays(14), CatalogProductKind.FORECAST, "provider", "forecast", request.asOf),
        )
        val summary = ProviderDailySummary(anchor,
            AccumulationInterval(anchor.atStartOfDay(location.timeZone).toInstant(), anchor.plusDays(1).atStartOfDay(location.timeZone).toInstant()),
            temperatureMaxC = 12.0, rainAmountMillimeters = 0.0)
        val catalog = CanonicalCatalog(location, request.datasetId,
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.MIXED, "provider", "mixed", request.asOf),
            request.asOf, base.catalog.facts, segments, listOf(summary))
        val original = CatalogRecording(catalog, base.expectedSupport,
            mapOf(anchor to setOf(DailyCatalogField.TEMPERATURE_MAX_C, DailyCatalogField.RAIN_AMOUNT)))
        val storage = CatalogDatasetStorage(MemoryDao())
        storage.save(CatalogStorageNamespace.LIVE, original, request.asOf)
        storage.save(CatalogStorageNamespace.REPLAY, original)
        for (namespace in listOf(CatalogStorageNamespace.LIVE, CatalogStorageNamespace.REPLAY)) {
            val loaded = (storage.load(location, namespace, request.datasetId) as StoredCatalogResult.Found).recording
            assertEquals(catalog, loaded.catalog)
            assertEquals(original.dailyExpectedSupport, loaded.dailyExpectedSupport)
            assertEquals(44, loaded.expectedSupport.size)
            assertEquals(123456789, loaded.catalog.asOf.nano)
            assertEquals(23, java.time.Duration.between(summary.support.start, summary.support.end).toHours())
            assertEquals(CatalogOrigin.LIVE, loaded.catalog.provenance.origin)
            assertTrue(loaded.catalog.facts.any { it.rainAmountMillimeters == null })
            assertTrue(loaded.catalog.facts.any { it.rainAmountMillimeters?.valueMillimeters == 0.0 })
        }
    }
}
