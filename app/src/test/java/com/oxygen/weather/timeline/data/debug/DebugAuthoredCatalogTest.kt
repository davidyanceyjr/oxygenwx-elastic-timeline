package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.*
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import com.fasterxml.jackson.databind.ObjectMapper
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

    @Test fun simulatedEventRangeSymbolSelectorPersistsWithoutChangingCanonicalFacts() = runBlocking {
        val start = LocalDateTime.parse("2026-11-03T08:00")
        val end = start.plusHours(8)
        val events = DebugSkySymbolRangeFixture.events(start)
        val definition = DebugAuthoredCatalog(
            "Sky symbol range", location, DatasetId("sky-symbol-range-v1"),
            Instant.parse("2026-11-03T18:00:00Z"), start, end, events.first().values, events,
        )
        val dao = DatasetMemory()
        val storage = CatalogDatasetStorage(dao)
        val repository = DebugAuthoredCatalogRepository(
            storage,
            DebugAuthoredCatalogMetadataStore(MetadataMemory()),
        )

        val recording = repository.save(definition)
        val reopened = requireNotNull(repository.reopen(location, definition.datasetId))

        assertEquals(WeatherSymbolId.entries.take(8), reopened.first.events.map { it.symbolId })
        assertEquals(8, reopened.first.events.size)
        reopened.first.events.forEachIndexed { index, event ->
            assertEquals(start.plusHours(index.toLong()), event.startLocal)
            assertEquals(event.startLocal.plusHours(1), event.endLocalExclusive)
            val rangeFact = recording.catalog.facts.single {
                val local = it.validTime.atZone(location.timeZone).toLocalDateTime()
                local >= event.startLocal && local < event.endLocalExclusive
            }
            assertEquals(event.values.fact(rangeFact.validTime), rangeFact)
            assertTrue(rangeFact.conditions.isEmpty())
            assertNull(rangeFact.cloudCoverPercent)
        }
    }

    @Test fun rainIceRangesPersistWithoutCanonicalWeatherFacts() = runBlocking {
        val start = LocalDateTime.parse("2026-11-03T08:00")
        val events = DebugRainIceSymbolRangeFixture.events(start)
        val definition = DebugAuthoredCatalog(
            "Rain and ice symbol range", location, DatasetId("rain-ice-symbol-range-v1"),
            Instant.parse("2026-11-03T18:00:00Z"), start, start.plusHours(8), events.first().values, events,
        )
        val repository = DebugAuthoredCatalogRepository(
            CatalogDatasetStorage(DatasetMemory()),
            DebugAuthoredCatalogMetadataStore(MetadataMemory()),
        )

        val recording = repository.save(definition)
        val reopened = requireNotNull(repository.reopen(location, definition.datasetId))
        assertEquals(
            listOf(
                WeatherSymbolId.DRIZZLE, WeatherSymbolId.RAIN, WeatherSymbolId.HEAVY_RAIN,
                WeatherSymbolId.RAIN_SHOWERS, WeatherSymbolId.FREEZING_DRIZZLE,
                WeatherSymbolId.FREEZING_RAIN, WeatherSymbolId.ICE_PELLETS, WeatherSymbolId.HAIL,
            ),
            reopened.first.events.map { it.symbolId },
        )
        assertEquals(8, reopened.first.events.size)
        reopened.first.events.forEachIndexed { index, event ->
            assertEquals(start.plusHours(index.toLong()), event.startLocal)
            assertEquals(event.startLocal.plusHours(1), event.endLocalExclusive)
            val rangeFact = recording.catalog.facts.single {
                val local = it.validTime.atZone(location.timeZone).toLocalDateTime()
                local >= event.startLocal && local < event.endLocalExclusive
            }
            assertEquals(event.values.fact(rangeFact.validTime), rangeFact)
            assertTrue(rangeFact.conditions.isEmpty())
            assertNull(rangeFact.cloudCoverPercent)
        }
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

    @Test fun eventRangesReplaceOnlyCoveredFactsAndRejectGapOnlyRanges() = runBlocking {
        val asOf = Instant.parse("2026-03-08T12:00:00Z")
        val first = DebugAuthoredEvent(LocalDateTime.parse("2026-03-08T01:00"),
            LocalDateTime.parse("2026-03-08T02:00"), DebugAuthoredWeatherValues(
                conditions = setOf(WeatherCondition.SNOW), temperatureC = -3.0))
        val second = DebugAuthoredEvent(LocalDateTime.parse("2026-03-08T03:00"),
            LocalDateTime.parse("2026-03-08T04:00"), DebugAuthoredWeatherValues(
                conditions = setOf(WeatherCondition.RAIN), temperatureC = 6.0))
        val definition = DebugAuthoredCatalog("Two events", location, DatasetId("two-events-v1"), asOf,
            LocalDateTime.parse("2026-03-08T01:00"), LocalDateTime.parse("2026-03-08T04:00"), first.values,
            listOf(first, second))
        val recording = DebugAuthoredCatalogMaterializer.materialize(definition)
        val dayFacts = recording.catalog.facts.filter { it.validTime.atZone(location.timeZone).toLocalDate().toString() == "2026-03-08" }
        val firstFact = dayFacts.single { it.validTime.atZone(location.timeZone).hour == 1 }
        val secondFact = dayFacts.single { it.validTime.atZone(location.timeZone).hour == 3 }
        assertEquals(setOf(WeatherCondition.SNOW), firstFact.conditions)
        assertEquals(setOf(WeatherCondition.RAIN), secondFact.conditions)
        assertTrue(dayFacts.none { it.validTime.atZone(location.timeZone).hour == 2 })
        assertEquals(23, dayFacts.size)
        val gapOnly = DebugAuthoredCatalog("Gap", location, DatasetId("gap-event-v1"), asOf,
            LocalDateTime.parse("2026-03-08T01:00"), LocalDateTime.parse("2026-03-08T04:00"), first.values,
            listOf(DebugAuthoredEvent(LocalDateTime.parse("2026-03-08T02:00"),
                LocalDateTime.parse("2026-03-08T03:00"), first.values)))
        try {
            DebugAuthoredCatalogMaterializer.materialize(gapOnly)
            fail("A range wholly within the DST gap must be rejected")
        } catch (_: IllegalArgumentException) { }
    }

    @Test fun adjacentRangesAreHalfOpenAndInvalidRangesAreRejected() = runBlocking {
        val asOf = Instant.parse("2026-03-09T12:00:00Z")
        val day = LocalDateTime.parse("2026-03-09T00:00")
        val start = day.plusHours(1)
        val end = day.plusHours(3)
        val snow = DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.SNOW), temperatureC = -4.0)
        val rain = DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.RAIN), temperatureC = 6.0)
        val adjacent = DebugAuthoredCatalog(
            "Adjacent events", location, DatasetId("adjacent-events-v1"), asOf, start, end, snow,
            listOf(
                DebugAuthoredEvent(start, start.plusHours(1), snow),
                DebugAuthoredEvent(start.plusHours(1), end, rain),
            ),
        )
        val recording = DebugAuthoredCatalogMaterializer.materialize(adjacent)
        val localFacts = recording.catalog.facts.filter {
            it.validTime.atZone(location.timeZone).toLocalDate().toString() == "2026-03-09"
        }.associateBy { it.validTime.atZone(location.timeZone).hour }
        assertEquals(setOf(WeatherCondition.SNOW), localFacts.getValue(1).conditions)
        assertEquals(setOf(WeatherCondition.RAIN), localFacts.getValue(2).conditions)

        val baselineResult = SimulatedCatalogSource().load(CatalogLoadRequest(location, adjacent.datasetId, asOf))
        val baseline = when (baselineResult) {
            is CatalogLoadResult.Success -> baselineResult.catalog
            is CatalogLoadResult.Partial -> baselineResult.catalog
            else -> error("Expected a deterministic baseline catalog")
        }
        val baselineHour3 = baseline.facts.single { it.validTime.atZone(location.timeZone).hour == 3 &&
            it.validTime.atZone(location.timeZone).toLocalDate().toString() == "2026-03-09" }
        assertEquals(baselineHour3, localFacts.getValue(3))

        try {
            DebugAuthoredEvent(start.plusMinutes(30), start.plusHours(1), snow)
            fail("Event boundaries must be whole local hours")
        } catch (_: IllegalArgumentException) { }
        try {
            DebugAuthoredCatalog("Overlap", location, DatasetId("overlap-events-v1"), asOf, start, end.plusHours(1), snow,
                listOf(DebugAuthoredEvent(start, start.plusHours(2), snow),
                    DebugAuthoredEvent(start.plusHours(1), end.plusHours(1), rain)))
            fail("Authored event ranges must not overlap")
        } catch (_: IllegalArgumentException) { }
        try {
            DebugAuthoredCatalog("Outside envelope", location, DatasetId("outside-event-v1"), asOf, start, end, snow,
                listOf(DebugAuthoredEvent(start.minusHours(1), start.plusHours(1), snow)))
            fail("Authored events must remain inside their envelope")
        } catch (_: IllegalArgumentException) { }
    }

    @Test fun savedSimulationReopensWithAuthoredDefinitionAndReplayIsImmutable() = runBlocking {
        val asOf = Instant.parse("2026-03-08T12:00:00Z")
        val definition = DebugAuthoredCatalog(
            "Storm study", location, DatasetId("storm-study-v1"), asOf,
            LocalDateTime.parse("2026-03-08T09:00"), LocalDateTime.parse("2026-03-08T12:00"),
            DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.STORM, WeatherCondition.RAIN),
                temperatureC = 8.0, precipitationProbabilityPercent = 70.0),
            listOf(
                DebugAuthoredEvent(LocalDateTime.parse("2026-03-08T09:00"), LocalDateTime.parse("2026-03-08T10:00"),
                    DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.STORM), temperatureC = 8.0)),
                DebugAuthoredEvent(LocalDateTime.parse("2026-03-08T10:00"), LocalDateTime.parse("2026-03-08T12:00"),
                    DebugAuthoredWeatherValues(conditions = setOf(WeatherCondition.RAIN), temperatureC = 8.0,
                        precipitationProbabilityPercent = 70.0)),
            ),
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
        assertEquals(definition.events, reopened.first.events)
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
        val validSidecar = metadata.values.getValue(sidecarKey)
        val altered = ObjectMapper().readTree(validSidecar).deepCopy<com.fasterxml.jackson.databind.node.ObjectNode>()
        val firstValues = altered.withArray("events").get(0) as com.fasterxml.jackson.databind.node.ObjectNode
        val changedValues = firstValues.get("values").deepCopy<com.fasterxml.jackson.databind.node.ObjectNode>()
        changedValues.put("temperatureC", 99.0)
        firstValues.set<com.fasterxml.jackson.databind.JsonNode>("values", changedValues)
        metadata.values[sidecarKey] = ObjectMapper().writeValueAsString(altered)
        assertNull(repository.reopen(location, definition.datasetId))
        metadata.values[sidecarKey] = validSidecar
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

    @Test fun unversionedPeriodSidecarReopensAsOneAuthoredEvent() = runBlocking {
        val definition = DebugAuthoredCatalog("Legacy period", location, DatasetId("legacy-period-v1"),
            Instant.parse("2026-03-08T12:00:00Z"), LocalDateTime.parse("2026-03-08T09:00"),
            LocalDateTime.parse("2026-03-08T12:00"), DebugAuthoredWeatherValues(
                conditions = setOf(WeatherCondition.RAIN), temperatureC = 7.0,
            ))
        val dao = DatasetMemory()
        val storage = CatalogDatasetStorage(dao)
        val memory = MetadataMemory()
        val repository = DebugAuthoredCatalogRepository(storage, DebugAuthoredCatalogMetadataStore(memory))
        repository.save(definition)
        val sidecarKey = "authored:${location.id.value}:${definition.datasetId.value}"
        val old = ObjectMapper().readTree(memory.values.getValue(sidecarKey)).deepCopy<com.fasterxml.jackson.databind.node.ObjectNode>()
        old.remove("schemaVersion")
        old.remove("events")
        memory.values[sidecarKey] = ObjectMapper().writeValueAsString(old)
        val reopened = repository.reopen(location, definition.datasetId)!!.first
        assertEquals(1, reopened.events.size)
        assertEquals(definition.startLocal, reopened.events.single().startLocal)
        assertEquals(definition.endLocalExclusive, reopened.events.single().endLocalExclusive)
        assertEquals(definition.values, reopened.events.single().values)

        val replayId = DatasetId("legacy-period-replay-v1")
        val replay = repository.captureReplay(location, definition.datasetId, replayId)
        val replayKey = "authored:${location.id.value}:${replayId.value}"
        val oldReplay = ObjectMapper().readTree(memory.values.getValue(replayKey))
            .deepCopy<com.fasterxml.jackson.databind.node.ObjectNode>()
        oldReplay.remove("schemaVersion")
        oldReplay.remove("events")
        memory.values[replayKey] = ObjectMapper().writeValueAsString(oldReplay)
        val reopenedReplay = repository.reopenReplay(location, replayId)!!
        assertEquals(CatalogOrigin.SIMULATED, reopenedReplay.second.catalog.provenance.origin)
        assertEquals(1, reopenedReplay.first.events.size)
        assertEquals(definition.startLocal, reopenedReplay.first.events.single().startLocal)
        assertEquals(definition.endLocalExclusive, reopenedReplay.first.events.single().endLocalExclusive)
        assertEquals(definition.values, reopenedReplay.first.events.single().values)
        assertEquals(replay.catalog.facts, reopenedReplay.second.catalog.facts)
    }
}
