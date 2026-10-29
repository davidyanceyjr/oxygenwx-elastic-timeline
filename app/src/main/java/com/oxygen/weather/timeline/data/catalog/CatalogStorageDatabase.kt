package com.oxygen.weather.timeline.data.catalog

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(
    tableName = "catalog_datasets",
    primaryKeys = ["locationId", "storageNamespace", "datasetId", "asOfEpochSecond", "asOfNano"],
    indices = [
        Index(value = ["locationId", "storageNamespace", "asOfEpochSecond", "asOfNano"]),
        Index(value = ["locationId", "storageNamespace", "datasetId", "asOfEpochSecond", "asOfNano"]),
        Index(value = ["locationId", "storageNamespace", "storedAtEpochSecond", "storedAtNano"]),
    ]
)
data class CatalogDatasetRow(
    val locationId: String,
    val storageNamespace: String,
    val datasetId: String,
    val asOf: String,
    val asOfEpochSecond: Long,
    val asOfNano: Int,
    val storedAtEpochSecond: Long?,
    val storedAtNano: Int?,
    val capturedOrigin: String,
    val payloadFormatVersion: Int,
    val payload: ByteArray,
)

@Dao
interface CatalogDatasetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(row: CatalogDatasetRow)

    @Query("SELECT * FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace AND datasetId = :datasetId ORDER BY asOfEpochSecond DESC, asOfNano DESC")
    suspend fun findCandidates(locationId: String, namespace: String, datasetId: String): List<CatalogDatasetRow>

    @Query("SELECT * FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace ORDER BY asOfEpochSecond DESC, asOfNano DESC")
    suspend fun history(locationId: String, namespace: String): List<CatalogDatasetRow>

    @Query("SELECT * FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = 'LIVE' ORDER BY CASE WHEN storedAtEpochSecond IS NULL THEN 1 ELSE 0 END, storedAtEpochSecond DESC, storedAtNano DESC, asOfEpochSecond DESC, asOfNano DESC")
    suspend fun liveRetentionOrder(locationId: String): List<CatalogDatasetRow>

    @Query("DELETE FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = 'LIVE' AND datasetId = :datasetId AND asOfEpochSecond = :asOfSecond AND asOfNano = :asOfNano")
    suspend fun deleteWindow(locationId: String, datasetId: String, asOfSecond: Long, asOfNano: Int): Int

    @Query("DELETE FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace AND datasetId = :datasetId")
    suspend fun deleteDataset(locationId: String, namespace: String, datasetId: String): Int

    @Query("DELETE FROM catalog_datasets WHERE locationId = :locationId")
    suspend fun deleteLocation(locationId: String): Int

    @androidx.room.Transaction
    suspend fun replaceDataset(row: CatalogDatasetRow) {
        deleteDataset(row.locationId, row.storageNamespace, row.datasetId)
        replace(row)
    }

    /** Guard, insert/replace, and location-wide LIVE pruning share one transaction. */
    @androidx.room.Transaction
    suspend fun persistLive(row: CatalogDatasetRow, retentionLimit: Int): Boolean {
        val newest = findCandidates(row.locationId, "LIVE", row.datasetId).firstOrNull()
        if (newest != null && compareInstant(newest.asOfEpochSecond, newest.asOfNano, row.asOfEpochSecond, row.asOfNano) > 0) return false
        replace(row)
        liveRetentionOrder(row.locationId).drop(retentionLimit).forEach { old ->
            deleteWindow(old.locationId, old.datasetId, old.asOfEpochSecond, old.asOfNano)
        }
        return true
    }

    private fun compareInstant(aSecond: Long, aNano: Int, bSecond: Long, bNano: Int): Int =
        if (aSecond != bSecond) aSecond.compareTo(bSecond) else aNano.compareTo(bNano)
}

@Database(entities = [CatalogDatasetRow::class, SavedLocationRow::class, SelectedLocationRow::class], version = 3, exportSchema = true)
abstract class CatalogStorageDatabase : RoomDatabase() {
    abstract fun datasets(): CatalogDatasetDao
    abstract fun savedLocations(): SavedLocationDao

    companion object {
        val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE catalog_datasets_v2 (locationId TEXT NOT NULL, storageNamespace TEXT NOT NULL, datasetId TEXT NOT NULL, asOf TEXT NOT NULL, asOfEpochSecond INTEGER NOT NULL, asOfNano INTEGER NOT NULL, storedAtEpochSecond INTEGER, storedAtNano INTEGER, capturedOrigin TEXT NOT NULL, payloadFormatVersion INTEGER NOT NULL, payload BLOB NOT NULL, PRIMARY KEY(locationId, storageNamespace, datasetId, asOfEpochSecond, asOfNano))")
                db.execSQL("INSERT INTO catalog_datasets_v2 (locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,storedAtEpochSecond,storedAtNano,capturedOrigin,payloadFormatVersion,payload) SELECT locationId,storageNamespace,datasetId,asOf,asOfEpochSecond,asOfNano,NULL,NULL,capturedOrigin,payloadFormatVersion,payload FROM catalog_datasets")
                db.execSQL("DROP TABLE catalog_datasets")
                db.execSQL("ALTER TABLE catalog_datasets_v2 RENAME TO catalog_datasets")
                db.execSQL("CREATE INDEX index_catalog_datasets_locationId_storageNamespace_asOfEpochSecond_asOfNano ON catalog_datasets(locationId,storageNamespace,asOfEpochSecond,asOfNano)")
                db.execSQL("CREATE INDEX index_catalog_datasets_locationId_storageNamespace_datasetId_asOfEpochSecond_asOfNano ON catalog_datasets(locationId,storageNamespace,datasetId,asOfEpochSecond,asOfNano)")
                db.execSQL("CREATE INDEX index_catalog_datasets_locationId_storageNamespace_storedAtEpochSecond_storedAtNano ON catalog_datasets(locationId,storageNamespace,storedAtEpochSecond,storedAtNano)")
            }
        }

        val MIGRATION_2_3 = object : androidx.room.migration.Migration(2, 3) {
            override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS saved_locations (locationId TEXT NOT NULL, name TEXT NOT NULL, countryName TEXT, countryCode TEXT, administrativeAreasJson TEXT NOT NULL, latitudeDegrees REAL NOT NULL, longitudeDegrees REAL NOT NULL, timeZoneId TEXT NOT NULL, sourceReference TEXT, attributionSourceName TEXT NOT NULL, attributionSourceUrl TEXT NOT NULL, attributionLicenseName TEXT NOT NULL, attributionLicenseUrl TEXT NOT NULL, attributionChangesMadeNotice TEXT, PRIMARY KEY(locationId))")
                db.execSQL("CREATE TABLE IF NOT EXISTS selected_location (singletonId INTEGER NOT NULL, selectedLocationId TEXT, PRIMARY KEY(singletonId))")
            }
        }
    }
}
