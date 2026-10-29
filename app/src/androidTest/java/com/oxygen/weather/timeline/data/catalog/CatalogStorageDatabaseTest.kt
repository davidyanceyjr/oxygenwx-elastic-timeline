package com.oxygen.weather.timeline.data.catalog

import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.room.withTransaction
import androidx.test.platform.app.InstrumentationRegistry
import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
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
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).addMigrations(CatalogStorageDatabase.MIGRATION_1_2).build()
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

    @Test fun migrationV1ToV2PreservesPayloadAndLeavesLegacyRetrievalUnknown() {
        context.deleteDatabase(name)
        val sql = migrations.createDatabase(name, 1)
        val payload = "{\"schemaVersion\":1}".toByteArray()
        sql.execSQL("INSERT INTO catalog_datasets (locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,capturedOrigin,payloadFormatVersion,payload) VALUES ('saved','LIVE','one','2026-03-08T12:00:00Z',1772971200,0,'LIVE',1,?)", arrayOf(payload))
        sql.close()
        migrations.runMigrationsAndValidate(name, 2, true, CatalogStorageDatabase.MIGRATION_1_2).use { db ->
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
            .addMigrations(CatalogStorageDatabase.MIGRATION_1_2).build()
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
}
