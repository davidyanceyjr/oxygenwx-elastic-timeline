package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugAuthoredCatalogTest {
    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))

    private class MetadataMemory : DebugAuthoredMetadataBackend {
        val values = mutableMapOf<String, String>()
        var failNextPut = false
        override fun get(key: String) = values[key]
        override fun put(key: String, value: String): Boolean {
            if (failNextPut) { failNextPut = false; return false }
            values[key] = value
            return true
        }
        override fun remove(key: String) = (values.remove(key) != null)
    }

    private class DatasetMemory : CatalogDatasetDao {
        val rows = mutableListOf<CatalogDatasetRow>()
        override suspend fun replace(row: CatalogDatasetRow) { rows.removeAll { it.locationId == row.locationId && it.storageNamespace == row.storageNamespace && it.datasetId == row.datasetId }; rows += row }
        override suspend fun findCandidates(locationId: String, namespace: String, datasetId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }
        override suspend fun history(locationId: String, namespace: String) = rows.filter { it.locationId == locationId && it.storageNamespace == namespace }
        override suspend fun liveRetentionOrder(locationId: String) = rows.filter { it.locationId == locationId && it.storageNamespace == "LIVE" }
        override suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int = rows.removeAll { it.locationId == locationId && it.storageNamespace == "LIVE" && it.datasetId == datasetId && it.asOfEpochSecond == asOfSecond && it.asOfNano == asOfNano }.let { if (it) 1 else 0 }
        override suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int = rows.removeAll { it.locationId == locationId && it.storageNamespace == namespace && it.datasetId == datasetId }.let { if (it) 1 else 0 }
        override suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int) = false
        override suspend fun deleteLocation(locationId: String): Int = rows.removeAll { it.locationId == locationId }.let { if (it) 1 else 0 }
    }

    @Test fun authoredPeriodOverridesEveryRealLocalHourAndKeepsFullDstWindow() = runBlocking {
        val asOf = Instant.parse("2026-03-08T12:00:00Z")
        val definition = DebugAuthoredCatalog(
            "Spring event", location, DatasetId("authored-spring-v1"), asOf,
            LocalDateTime.parse("2026-03-08T01:00"), LocalDateTime.parse("2026-03-08T04:00"),
            DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.SNOW), temperatureC = -4.0,
                precipitationProbabilityPercent = 0.0, windSpeedKph = 0.0),
        )

        val recording = DebugAuthoredCatalogMaterializer.materialize(definition)
        val localDayFacts = recording.catalog.facts.filter { it.validTime.atZone(location.timeZone).toLocalDate().toString() == "2026-03-08" }
        val changed = localDayFacts.filter {
            val local = it.validTime.atZone(location.timeZone).toLocalDateTime()
            local >= definition.startLocal && local < definition.endLocalExclusive
        }

        assertEquals(44, recording.expectedSupport.size)
        assertEquals(23, localDayFacts.size)
        assertEquals(listOf(1, 3), changed.map { it.validTime.atZone(location.timeZone).hour })
        changed.forEach {
            assertEquals(setOf(WeatherCondition.SNOW), it.conditions)
            assertEquals(-4.0, it.temperatureC!!, 0.0)
            assertEquals(0.0, it.precipitationProbabilityPercent!!, 0.0)
            assertEquals(0.0, it.windSpeedKph!!, 0.0)
        }
        assertFalse(recording.expectedSupport[changed.first().validTime.atZone(location.timeZone).toLocalDate()]
            .orEmpty().containsKey(CatalogField.RAIN_AMOUNT_MILLIMETERS))
        assertNotNull(recording.expectedSupport[changed.first().validTime.atZone(location.timeZone).toLocalDate()]
            ?.get(CatalogField.PRECIPITATION_PROBABILITY_PERCENT))
        assertTrue(recording.catalog.facts.first().temperatureC != -4.0)
        assertTrue(CatalogWindow.create(recording.catalog, recording.expectedSupport) is CatalogWindowResult.Success)
    }

    @Test fun repeatedLocalHourIncludesBothRealInstantsAndAbsentValuesStayUnsupported() = runBlocking {
        val asOf = Instant.parse("2026-11-01T12:00:00Z")
        val definition = DebugAuthoredCatalog(
            "Fall overlap", location, DatasetId("authored-fall-v1"), asOf,
            LocalDateTime.parse("2026-11-01T01:00"), LocalDateTime.parse("2026-11-01T02:00"),
            DebugAuthoredWeatherValues(conditions = emptySet(), temperatureC = null, windSpeedKph = 0.0),
        )

        val recording = DebugAuthoredCatalogMaterializer.materialize(definition)
        val overlap = recording.catalog.facts.filter {
            val zoned = it.validTime.atZone(location.timeZone)
            zoned.toLocalDate().toString() == "2026-11-01" && zoned.hour == 1
        }
        assertEquals(2, overlap.size)
        overlap.forEach {
            assertTrue(it.conditions.isEmpty())
            assertNull(it.temperatureC)
            assertEquals(0.0, it.windSpeedKph!!, 0.0)
        }
        val day = overlap.first().validTime.atZone(location.timeZone).toLocalDate()
        val declaredConditions = recording.expectedSupport[day]?.get(CatalogField.CONDITIONS) as ExpectedFieldSupport.Points
        val declaredTemperatures = recording.expectedSupport[day]?.get(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points
        assertTrue(overlap.none { it.validTime in declaredConditions.instants })
        assertTrue(overlap.none { it.validTime in declaredTemperatures.instants })
        assertNotNull(recording.expectedSupport[day]?.get(CatalogField.WIND_SPEED_KPH))
    }

    @Test fun savedSimulationReopensWithAuthoredDefinitionAndReplayIsImmutable() = runBlocking {
        val asOf = Instant.parse("2026-03-08T12:00:00Z")
        val definition = DebugAuthoredCatalog(
            "Storm study", location, DatasetId("storm-study-v1"), asOf,
            LocalDateTime.parse("2026-03-08T09:00"), LocalDateTime.parse("2026-03-08T12:00"),
            DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.STORM, WeatherCondition.RAIN),
                temperatureC = 8.0, precipitationProbabilityPercent = 70.0),
        )
        val dao = DatasetMemory()
        val storage = CatalogDatasetStorage(dao)
        val metadata = MetadataMemory()
        val repository = DebugAuthoredCatalogRepository(storage, DebugAuthoredCatalogMetadataStore(metadata))

        val saved = repository.save(definition)
        val reopened = repository.reopen(location, definition.datasetId)!!
        assertEquals(definition.name, reopened.first.name)
        assertEquals(definition.startLocal, reopened.first.startLocal)
        assertEquals(definition.endLocalExclusive, reopened.first.endLocalExclusive)
        assertEquals(definition.values.conditions, reopened.first.values.conditions)
        assertEquals(definition.values.temperatureC, reopened.first.values.temperatureC)
        assertArrayEquals(RecordedCatalogCodec.encode(saved), RecordedCatalogCodec.encode(reopened.second))

        val replay = repository.captureReplay(location, definition.datasetId, DatasetId("storm-study-replay-v1"))
        assertEquals(CatalogOrigin.SIMULATED, replay.catalog.provenance.origin)
        assertEquals(saved.catalog.facts, replay.catalog.facts)
        assertArrayEquals(RecordedCatalogCodec.encode(replay), RecordedCatalogCodec.encode(
            (storage.load(location, CatalogStorageNamespace.REPLAY, replay.catalog.datasetId) as StoredCatalogResult.Found).recording,
        ))
        assertEquals("Storm study replay", repository.reopenReplay(location, replay.catalog.datasetId)!!.first.name)
        val edit = definition.copy(name = "Storm study edited", datasetId = DatasetId("storm-study-v2"),
            values = definition.values.copy(temperatureC = 15.0))
        repository.save(edit)
        assertEquals(8.0, repository.reopenReplay(location, replay.catalog.datasetId)!!.second.catalog.facts
            .first { it.temperatureC == 8.0 }.temperatureC!!, 0.0)

        val sidecarKey = "authored:${location.id.value}:${definition.datasetId.value}"
        metadata.values[sidecarKey] = metadata.values.getValue(sidecarKey)
            .replace(Regex("\"recordingSha256\":\"[^\"]+\""), "\"recordingSha256\":\"tampered\"")
        assertNull(repository.reopen(location, definition.datasetId))
        try {
            repository.captureReplay(location, definition.datasetId, replay.catalog.datasetId)
            fail("existing REPLAY identity must be immutable")
        } catch (_: IllegalArgumentException) { }
    }

    @Test fun sidecarFailureRollsBackNewCanonicalIdentity() = runBlocking {
        val dao = DatasetMemory()
        val storage = CatalogDatasetStorage(dao)
        val metadata = MetadataMemory().apply { failNextPut = true }
        val repository = DebugAuthoredCatalogRepository(storage, DebugAuthoredCatalogMetadataStore(metadata))
        val definition = DebugAuthoredCatalog("Write failure", location, DatasetId("write-failure-v1"),
            Instant.parse("2026-03-08T12:00:00Z"), LocalDateTime.parse("2026-03-08T09:00"),
            LocalDateTime.parse("2026-03-08T12:00"), DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.RAIN)))

        try {
            repository.save(definition)
            fail("metadata failure must prevent a saved success")
        } catch (_: IllegalStateException) { }
        assertTrue(storage.load(location, CatalogStorageNamespace.SIMULATED, definition.datasetId) is StoredCatalogResult.Missing)
    }
}
