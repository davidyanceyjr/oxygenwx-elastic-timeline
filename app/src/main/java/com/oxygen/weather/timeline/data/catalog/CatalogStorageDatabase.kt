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
    primaryKeys = ["locationId", "storageNamespace", "datasetId"],
    indices = [Index(value = ["locationId", "storageNamespace", "asOfEpochSecond", "asOfNano"])]
)
data class CatalogDatasetRow(
    val locationId: String,
    val storageNamespace: String,
    val datasetId: String,
    val asOf: String,
    val asOfEpochSecond: Long,
    val asOfNano: Int,
    val capturedOrigin: String,
    val payloadFormatVersion: Int,
    val payload: ByteArray,
)

@Dao
interface CatalogDatasetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replace(row: CatalogDatasetRow)

    @Query("SELECT * FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace AND datasetId = :datasetId")
    suspend fun find(locationId: String, namespace: String, datasetId: String): CatalogDatasetRow?

    @Query("SELECT * FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace ORDER BY asOfEpochSecond DESC, asOfNano DESC")
    suspend fun history(locationId: String, namespace: String): List<CatalogDatasetRow>

    @Query("DELETE FROM catalog_datasets WHERE locationId = :locationId AND storageNamespace = :namespace AND datasetId = :datasetId")
    suspend fun delete(locationId: String, namespace: String, datasetId: String): Int

    @Query("DELETE FROM catalog_datasets WHERE locationId = :locationId")
    suspend fun deleteLocation(locationId: String): Int
}

@Database(entities = [CatalogDatasetRow::class], version = 1, exportSchema = true)
abstract class CatalogStorageDatabase : RoomDatabase() {
    abstract fun datasets(): CatalogDatasetDao
}
