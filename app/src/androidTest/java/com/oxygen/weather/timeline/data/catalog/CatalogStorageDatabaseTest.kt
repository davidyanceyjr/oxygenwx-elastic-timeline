package com.oxygen.weather.timeline.data.catalog

import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.room.withTransaction
import androidx.test.platform.app.InstrumentationRegistry
import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CatalogStorageDatabaseTest {
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "catalog-storage-test.db"
    @get:Rule val migrations = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
        "com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase")

    private val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))
    private val request = CatalogLoadRequest(location, DatasetId("sequence"), Instant.parse("2026-03-08T12:00:00.123456789Z"))

    private suspend fun recording(): CatalogRecording {
        val loaded = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
        return CatalogRecording(loaded.catalog, loaded.expectedSupport)
    }

    @Test fun creationReplacementRollbackAndIsolation() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).addMigrations(CatalogStorageDatabase.MIGRATION_1_2, CatalogStorageDatabase.MIGRATION_2_3).build()
        try {
            val storage = CatalogDatasetStorage(db.datasets())
            val simulated = recording()
            val live = CatalogRecording(CanonicalCatalog(simulated.catalog.location, simulated.catalog.datasetId,
                CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST),
                simulated.catalog.asOf, simulated.catalog.facts), simulated.expectedSupport)
            storage.save(CatalogStorageNamespace.SIMULATED, simulated)
            storage.save(CatalogStorageNamespace.LIVE, live, request.asOf)
            storage.save(CatalogStorageNamespace.REPLAY, live)
            val replacement = CatalogRecording(CanonicalCatalog(live.catalog.location, live.catalog.datasetId,
                live.catalog.provenance, live.catalog.asOf, live.catalog.facts), live.expectedSupport)
            storage.save(CatalogStorageNamespace.LIVE, replacement, request.asOf)
            assertEquals(3, db.datasets().history(location.id.value, CatalogStorageNamespace.LIVE.name).size +
                db.datasets().history(location.id.value, CatalogStorageNamespace.SIMULATED.name).size +
                db.datasets().history(location.id.value, CatalogStorageNamespace.REPLAY.name).size)
            assertEquals(replacement.catalog, (storage.load(location, CatalogStorageNamespace.LIVE, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
            assertEquals(1, storage.history(location.id, CatalogStorageNamespace.LIVE).size)
            val old = db.datasets().findCandidates(location.id.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value).single()
            try {
                db.withTransaction {
                    db.datasets().replace(old.copy(
                        asOf = request.asOf.plusSeconds(2).toString(),
                        asOfEpochSecond = request.asOf.plusSeconds(2).epochSecond,
                    ))
                    error("injected failure after replacement")
                }
                fail("transaction should fail")
            } catch (_: IllegalStateException) { }
            val afterRollback = db.datasets().findCandidates(location.id.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value).single()
            assertEquals(old.locationId, afterRollback.locationId)
            assertEquals(old.storageNamespace, afterRollback.storageNamespace)
            assertEquals(old.datasetId, afterRollback.datasetId)
            assertEquals(old.asOf, afterRollback.asOf)
            assertEquals(old.asOfEpochSecond, afterRollback.asOfEpochSecond)
            assertEquals(old.asOfNano, afterRollback.asOfNano)
            assertEquals(old.capturedOrigin, afterRollback.capturedOrigin)
            assertEquals(old.payloadFormatVersion, afterRollback.payloadFormatVersion)
            assertArrayEquals(old.payload, afterRollback.payload)
            assertEquals(1, storage.delete(location.id, CatalogStorageNamespace.SIMULATED, request.datasetId))
            assertTrue(storage.load(location, CatalogStorageNamespace.LIVE, request.datasetId) is StoredCatalogResult.Found)
            assertTrue(storage.load(location, CatalogStorageNamespace.REPLAY, request.datasetId) is StoredCatalogResult.Found)
            val other = LocationId("other")
            db.datasets().replace(old.copy(locationId = other.value))
            assertEquals(2, storage.deleteLocation(location.id))
            assertTrue(db.datasets().findCandidates(other.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value).isNotEmpty())
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun exportedSchemaAndMissingMigrationPreserveContents() {
        val sql = migrations.createDatabase(name, 1)
        sql.execSQL("INSERT INTO catalog_datasets (locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,capturedOrigin,payloadFormatVersion,payload) VALUES ('saved','LIVE','one','2026-03-08T12:00:00Z',1772971200,0,'LIVE',1,X'7B7D')")
        sql.close()
        val file = context.getDatabasePath(name)
        SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READWRITE).use { it.version = 2 }
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        try {
            try { db.openHelper.writableDatabase; fail("missing migration should fail") }
            catch (_: IllegalStateException) { }
        } finally { db.close() }
        SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READONLY).use { raw ->
            raw.rawQuery("SELECT COUNT(*) FROM catalog_datasets WHERE locationId='saved'", null).use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals(1, cursor.getInt(0))
            }
        }
        context.deleteDatabase(name)
    }

    @Test fun migrationV1ToV3PreservesPayloadAndLeavesLegacyRetrievalUnknown() {
        context.deleteDatabase(name)
        val sql = migrations.createDatabase(name, 1)
        val payload = "{\"schemaVersion\":1}".toByteArray()
        sql.execSQL("INSERT INTO catalog_datasets (locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,capturedOrigin,payloadFormatVersion,payload) VALUES ('saved','LIVE','one','2026-03-08T12:00:00Z',1772971200,0,'LIVE',1,?)", arrayOf(payload))
        sql.close()
        migrations.runMigrationsAndValidate(name, 3, true, CatalogStorageDatabase.MIGRATION_1_2, CatalogStorageDatabase.MIGRATION_2_3).use { db ->
            db.query("SELECT locationId,storageNamespace,datasetId,storedAtEpochSecond,storedAtNano,payload FROM catalog_datasets").use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals("saved", cursor.getString(0))
                assertEquals("LIVE", cursor.getString(1))
                assertEquals("one", cursor.getString(2))
                assertTrue(cursor.isNull(3))
                assertTrue(cursor.isNull(4))
                assertArrayEquals(payload, cursor.getBlob(5))
            }
        }
        context.deleteDatabase(name)
    }

    @Test fun historyOrdersWholeAndFractionalSecondsNumerically() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        try {
            val storage = CatalogDatasetStorage(db.datasets())
            val first = recording()
            val whole = Instant.parse("2026-03-08T12:00:00Z")
            val fraction = Instant.parse("2026-03-08T12:00:00.100000000Z")
            listOf("whole" to whole, "fraction" to fraction).forEach { (id, asOf) ->
                val catalog = CanonicalCatalog(first.catalog.location, DatasetId(id), first.catalog.provenance,
                    asOf, first.catalog.facts)
                storage.save(CatalogStorageNamespace.SIMULATED, CatalogRecording(catalog, first.expectedSupport))
            }
            assertEquals(listOf("fraction", "whole"), storage.history(location.id, CatalogStorageNamespace.SIMULATED).map { it.datasetId })
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun liveWindowsSupersedeAndPruneAtomicallyWithoutTouchingSimulation() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name)
            .addMigrations(CatalogStorageDatabase.MIGRATION_1_2, CatalogStorageDatabase.MIGRATION_2_3).build()
        try {
            val storage = CatalogDatasetStorage(db.datasets())
            val seed = recording()
            fun live(asOf: Instant) = CatalogRecording(
                CanonicalCatalog(location, request.datasetId, CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST), asOf, seed.catalog.facts),
                seed.expectedSupport,
            )
            storage.save(CatalogStorageNamespace.SIMULATED, seed)
            val t1 = Instant.parse("2026-03-08T12:00:00Z")
            val t2 = t1.plusSeconds(60)
            val t3 = t2.plusSeconds(60)
            assertEquals(CatalogPersistResult.PERSISTED, storage.save(CatalogStorageNamespace.LIVE, live(t1), t1))
            assertEquals(CatalogPersistResult.PERSISTED, storage.save(CatalogStorageNamespace.LIVE, live(t2), t2))
            assertEquals(CatalogPersistResult.SUPERSEDED, storage.save(CatalogStorageNamespace.LIVE, live(t1.plusSeconds(30)), t3))
            assertEquals(listOf(t2, t1), storage.history(location.id, CatalogStorageNamespace.LIVE).map { it.asOf })
            try {
                val invalid = CatalogRecording(CanonicalCatalog(location, request.datasetId,
                    CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST), t3, emptyList()), seed.expectedSupport)
                storage.save(CatalogStorageNamespace.LIVE, invalid, t3)
                fail("invalid recording must not write or prune")
            } catch (_: IllegalArgumentException) { }
            assertEquals(listOf(t2, t1), storage.history(location.id, CatalogStorageNamespace.LIVE).map { it.asOf })
            assertEquals(CatalogPersistResult.PERSISTED, storage.save(CatalogStorageNamespace.LIVE, live(t3), t3))
            assertEquals(listOf(t3, t2), storage.history(location.id, CatalogStorageNamespace.LIVE).map { it.asOf })
            assertEquals(1, storage.history(location.id, CatalogStorageNamespace.SIMULATED).size)
            assertEquals(listOf(t3, t2), storage.history(location.id, CatalogStorageNamespace.LIVE).mapNotNull { it.storedAt })
        } finally { db.close(); context.deleteDatabase(name) }
    }

    private fun placeCandidate(id: Long = 12345, name: String = "Chicago", latitude: Double = 41.8781) = PlaceSearchCandidate(
        name = name, countryName = "United States", countryCode = "US", administrativeAreas = listOf("Illinois", "Cook County"),
        coordinates = GeoCoordinates(latitude, -87.6298), timeZone = ZoneId.of("America/Chicago"),
        sourceReference = id.toString(),
        attribution = com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution(
            "GeoNames via Open-Meteo", java.net.URI("https://open-meteo.com/"), "CC BY 4.0",
            java.net.URI("https://creativecommons.org/licenses/by/4.0/"), "Display label combines returned place fields."),
        canonicalPlaceId = CanonicalPlaceId.geonames(id),
    )

    @Test fun savedLocationStateRoundTripsAndRemovalDeletesOnlyAssociatedCatalogs() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name)
            .addMigrations(CatalogStorageDatabase.MIGRATION_1_2, CatalogStorageDatabase.MIGRATION_2_3).build()
        try {
            val repository = SavedLocationRepository(db)
            val candidate = placeCandidate()
            assertTrue(repository.add(candidate) is SavedLocationResult.Added)
            assertTrue(repository.add(candidate.copy(name = "Changed display name")) is SavedLocationResult.AlreadySaved)
            assertTrue(repository.add(candidate.copy(coordinates = GeoCoordinates(41.9, -87.6298))) is SavedLocationResult.PointConflict)
            val reread = (repository.find(LocationId("geonames:12345")) as SavedLocationResult.Selected).location!!
            assertEquals("Chicago", reread.candidate.name)
            assertEquals(listOf("Illinois", "Cook County"), reread.candidate.administrativeAreas)
            assertEquals("12345", reread.candidate.sourceReference)
            assertEquals("https://open-meteo.com/", reread.candidate.attribution.sourceUrl.toString())
            assertEquals(41.8781, reread.location.coordinates.latitudeDegrees, 0.0)
            assertEquals(-87.6298, reread.location.coordinates.longitudeDegrees, 0.0)
            assertEquals("America/Chicago", reread.location.timeZone.id)
            assertTrue(repository.select(LocationId("geonames:12345")) is SavedLocationResult.Selected)

            val seed = recording()
            for (namespace in CatalogStorageNamespace.entries) {
                db.datasets().replace(CatalogDatasetRow(
                    locationId = "geonames:12345", storageNamespace = namespace.name, datasetId = "seed",
                    asOf = request.asOf.toString(), asOfEpochSecond = request.asOf.epochSecond, asOfNano = request.asOf.nano,
                    storedAtEpochSecond = null, storedAtNano = null, capturedOrigin = seed.catalog.provenance.origin.name,
                    payloadFormatVersion = 1, payload = byteArrayOf(1, 2, 3),
                ))
            }
            db.datasets().replace(CatalogDatasetRow(
                locationId = "geonames:99999", storageNamespace = "SIMULATED", datasetId = "keep",
                asOf = request.asOf.toString(), asOfEpochSecond = request.asOf.epochSecond, asOfNano = request.asOf.nano,
                storedAtEpochSecond = null, storedAtNano = null, capturedOrigin = "SIMULATED",
                payloadFormatVersion = 1, payload = byteArrayOf(4, 5, 6),
            ))
            assertEquals(SavedLocationResult.Removed, repository.remove(LocationId("geonames:12345")))
            assertNull(db.savedLocations().selectedId())
            assertTrue(db.datasets().findCandidates("geonames:12345", "LIVE", "seed").isEmpty())
            assertArrayEquals(byteArrayOf(4, 5, 6), db.datasets().findCandidates("geonames:99999", "SIMULATED", "keep").single().payload)
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun missingCanonicalIdStaleSelectionMalformedRecordAndUnselectedRemovalAreExplicit() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        try {
            val repository = SavedLocationRepository(db)
            assertEquals(SavedLocationResult.MissingCanonicalId, repository.add(placeCandidate().copy(canonicalPlaceId = null)))
            assertEquals(SavedLocationListResult.Locations(emptyList()), repository.list())
            repository.add(placeCandidate())
            repository.add(placeCandidate(id = 67890, name = "Evanston"))
            repository.select(LocationId("geonames:12345"))
            assertEquals(SavedLocationResult.Removed, repository.remove(LocationId("geonames:67890")))
            assertEquals("geonames:12345", (repository.restore() as SavedLocationResult.Selected).location?.location?.id?.value)

            db.savedLocations().setSelection(SelectedLocationRow(selectedLocationId = "geonames:77777"))
            assertEquals(SavedLocationResult.Selected(null), repository.restore())
            assertNull(db.savedLocations().selectedId())

            db.savedLocations().insert(SavedLocationRow(
                locationId = "invalid-id", name = "Corrupt", countryName = "US", countryCode = "US",
                administrativeAreasJson = "[]", latitudeDegrees = 1.0, longitudeDegrees = 2.0, timeZoneId = "UTC",
                sourceReference = null, attributionSourceName = "Source", attributionSourceUrl = "https://example.com/",
                attributionLicenseName = "License", attributionLicenseUrl = "https://example.com/license", attributionChangesMadeNotice = null,
            ))
            db.savedLocations().setSelection(SelectedLocationRow(selectedLocationId = "invalid-id"))
            assertEquals(SavedLocationResult.MalformedRecord, repository.restore())
            assertEquals(SavedLocationListResult.MalformedRecord, repository.list())
            assertEquals("invalid-id", db.savedLocations().selectedId())
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun selectedLocationRestoresAfterDatabaseReopen() = runBlocking {
        context.deleteDatabase(name)
        var db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        val repository = SavedLocationRepository(db)
        try {
            repository.add(placeCandidate())
            repository.select(LocationId("geonames:12345"))
            db.close()
            db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
            val restored = SavedLocationRepository(db).restore() as SavedLocationResult.Selected
            assertEquals("geonames:12345", restored.location?.location?.id?.value)
            assertEquals(41.8781, restored.location?.location?.coordinates?.latitudeDegrees ?: Double.NaN, 0.0)
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun concurrentCanonicalAddsSerializeAndFailedRemovalRollsBackAllTables() = runBlocking {
        context.deleteDatabase(name)
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        try {
            val repository = SavedLocationRepository(db)
            val outcomes = coroutineScope { listOf(async { repository.add(placeCandidate()) }, async { repository.add(placeCandidate()) }).map { it.await() } }
            assertEquals(1, outcomes.count { it is SavedLocationResult.Added })
            assertEquals(1, outcomes.count { it is SavedLocationResult.AlreadySaved })
            val savedId = "geonames:12345"
            repository.select(LocationId(savedId))
            db.datasets().replace(CatalogDatasetRow(savedId, "LIVE", "seed", request.asOf.toString(), request.asOf.epochSecond,
                request.asOf.nano, null, null, "LIVE", 1, byteArrayOf(1, 2, 3)))
            db.openHelper.writableDatabase.execSQL("CREATE TRIGGER fail_saved_location_delete BEFORE DELETE ON saved_locations BEGIN SELECT RAISE(ABORT, 'injected removal failure'); END")
            try { repository.remove(LocationId(savedId)); fail("removal should fail") } catch (_: Exception) { }
            assertNotNull(db.savedLocations().find(savedId))
            assertEquals(savedId, db.savedLocations().selectedId())
            assertArrayEquals(byteArrayOf(1, 2, 3), db.datasets().findCandidates(savedId, "LIVE", "seed").single().payload)
        } finally { db.close(); context.deleteDatabase(name) }
    }

    @Test fun exportedV2SchemaMigratesAdditivelyToV3() {
        context.deleteDatabase(name)
        val sql = migrations.createDatabase(name, 2)
        val payload = byteArrayOf(9, 8, 7, 6)
        sql.execSQL("INSERT INTO catalog_datasets (locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,storedAtEpochSecond,storedAtNano,capturedOrigin,payloadFormatVersion,payload) VALUES ('keep','LIVE','dataset','2026-03-08T12:00:00Z',1772971200,0,1772971200,0,'LIVE',2,?)", arrayOf(payload))
        sql.close()
        migrations.runMigrationsAndValidate(name, 3, true, CatalogStorageDatabase.MIGRATION_2_3).use { db ->
            db.query("SELECT locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,storedAtEpochSecond,storedAtNano,capturedOrigin,payloadFormatVersion,payload FROM catalog_datasets").use { cursor ->
                assertTrue(cursor.moveToFirst())
                assertEquals("keep", cursor.getString(0))
                assertEquals("LIVE", cursor.getString(1))
                assertEquals("dataset", cursor.getString(2))
                assertEquals("2026-03-08T12:00:00Z", cursor.getString(3))
                assertEquals(1772971200L, cursor.getLong(4))
                assertEquals(0, cursor.getInt(5))
                assertEquals(1772971200L, cursor.getLong(6))
                assertEquals(0, cursor.getInt(7))
                assertEquals("LIVE", cursor.getString(8))
                assertEquals(2, cursor.getInt(9))
                assertArrayEquals(payload, cursor.getBlob(10))
            }
            val indexNames = db.query("PRAGMA index_list(catalog_datasets)").use { cursor ->
                buildSet { while (cursor.moveToNext()) add(cursor.getString(1)) }
            }
            assertTrue(indexNames.containsAll(listOf(
                "index_catalog_datasets_locationId_storageNamespace_asOfEpochSecond_asOfNano",
                "index_catalog_datasets_locationId_storageNamespace_datasetId_asOfEpochSecond_asOfNano",
                "index_catalog_datasets_locationId_storageNamespace_storedAtEpochSecond_storedAtNano",
            )))
        }
        context.deleteDatabase(name)
    }
}
