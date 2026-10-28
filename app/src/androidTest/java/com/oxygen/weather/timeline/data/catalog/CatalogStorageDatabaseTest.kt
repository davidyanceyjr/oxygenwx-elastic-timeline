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
        val db = Room.databaseBuilder(context, CatalogStorageDatabase::class.java, name).build()
        try {
            val storage = CatalogDatasetStorage(db.datasets())
            val simulated = recording()
            val live = CatalogRecording(CanonicalCatalog(simulated.catalog.location, simulated.catalog.datasetId,
                CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST),
                simulated.catalog.asOf, simulated.catalog.facts), simulated.expectedSupport)
            storage.save(CatalogStorageNamespace.SIMULATED, simulated)
            storage.save(CatalogStorageNamespace.LIVE, live)
            storage.save(CatalogStorageNamespace.REPLAY, live)
            val replacement = CatalogRecording(CanonicalCatalog(live.catalog.location, live.catalog.datasetId,
                live.catalog.provenance, live.catalog.asOf.plusSeconds(1), live.catalog.facts), live.expectedSupport)
            storage.save(CatalogStorageNamespace.LIVE, replacement)
            assertEquals(3, db.datasets().history(location.id.value, CatalogStorageNamespace.LIVE.name).size +
                db.datasets().history(location.id.value, CatalogStorageNamespace.SIMULATED.name).size +
                db.datasets().history(location.id.value, CatalogStorageNamespace.REPLAY.name).size)
            assertEquals(replacement.catalog, (storage.load(location.id, CatalogStorageNamespace.LIVE, request.datasetId) as StoredCatalogResult.Found).recording.catalog)
            assertEquals(1, storage.history(location.id, CatalogStorageNamespace.LIVE).size)
            val old = db.datasets().find(location.id.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value)!!
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
            val afterRollback = db.datasets().find(location.id.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value)!!
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
            assertTrue(storage.load(location.id, CatalogStorageNamespace.LIVE, request.datasetId) is StoredCatalogResult.Found)
            assertTrue(storage.load(location.id, CatalogStorageNamespace.REPLAY, request.datasetId) is StoredCatalogResult.Found)
            val other = LocationId("other")
            db.datasets().replace(old.copy(locationId = other.value))
            assertEquals(2, storage.deleteLocation(location.id))
            assertNotNull(db.datasets().find(other.value, CatalogStorageNamespace.LIVE.name, request.datasetId.value))
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
}
