package com.oxygen.weather.timeline.data.catalog

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.withTransaction
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.net.URI
import java.time.ZoneId
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/** Stable, provider-neutral identity for a GeoNames place feature. */
@JvmInline
value class CanonicalPlaceId(val value: String) {
    init {
        require(value.matches(Regex("geonames:[1-9][0-9]*"))) { "Canonical place ID must be geonames:<positive feature id>" }
        require(value.substringAfter(':').toLongOrNull() != null) { "GeoNames feature ID is out of range" }
    }

    companion object {
        fun geonames(featureId: Long): CanonicalPlaceId {
            require(featureId > 0)
            return CanonicalPlaceId("geonames:$featureId")
        }
    }
}

@Entity(tableName = "saved_locations")
data class SavedLocationRow(
    @PrimaryKey val locationId: String,
    val name: String,
    val countryName: String?,
    val countryCode: String?,
    val administrativeAreasJson: String,
    val latitudeDegrees: Double,
    val longitudeDegrees: Double,
    val timeZoneId: String,
    val sourceReference: String?,
    val attributionSourceName: String,
    val attributionSourceUrl: String,
    val attributionLicenseName: String,
    val attributionLicenseUrl: String,
    val attributionChangesMadeNotice: String?,
)

@Entity(tableName = "selected_location", primaryKeys = ["singletonId"])
data class SelectedLocationRow(val singletonId: Int = 1, val selectedLocationId: String?)

@Dao
interface SavedLocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(row: SavedLocationRow): Long

    @Query("SELECT * FROM saved_locations WHERE locationId = :id")
    suspend fun find(id: String): SavedLocationRow?

    @Query("SELECT * FROM saved_locations ORDER BY name COLLATE NOCASE, locationId")
    suspend fun list(): List<SavedLocationRow>

    @Query("DELETE FROM saved_locations WHERE locationId = :id")
    suspend fun delete(id: String): Int

    @Query("SELECT selectedLocationId FROM selected_location WHERE singletonId = 1")
    suspend fun selectedId(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSelection(row: SelectedLocationRow)

    @Query("DELETE FROM selected_location WHERE singletonId = 1")
    suspend fun clearSelection()
}

data class SavedLocation(val location: CatalogLocation, val candidate: PlaceSearchCandidate)

sealed interface SavedLocationResult {
    data class Added(val location: SavedLocation) : SavedLocationResult
    data class AlreadySaved(val location: SavedLocation) : SavedLocationResult
    data class PointConflict(val id: CanonicalPlaceId) : SavedLocationResult
    data object MissingCanonicalId : SavedLocationResult
    data object MissingLocation : SavedLocationResult
    data object Removed : SavedLocationResult
    data object MalformedRecord : SavedLocationResult
    data class Selected(val location: SavedLocation?) : SavedLocationResult
    data object NoMutation : SavedLocationResult
}

sealed interface SavedLocationListResult {
    data class Locations(val values: List<SavedLocation>) : SavedLocationListResult
    data object MalformedRecord : SavedLocationListResult
}

/** Saved-location and selected-location state sharing the catalog database transaction boundary. */
class SavedLocationRepository(
    private val database: CatalogStorageDatabase,
    private val mapper: ObjectMapper = ObjectMapper(),
) : StartupLocationRestorer {
    private val dao get() = database.savedLocations()
    private val locationLocks = ConcurrentHashMap<String, Mutex>()
    private val locationVersions = ConcurrentHashMap<String, AtomicLong>()

    private fun lockFor(id: LocationId) = locationLocks.getOrPut(id.value) { Mutex() }
    private fun versionFor(id: LocationId) = locationVersions.getOrPut(id.value) { AtomicLong() }

    /** Captured when a background update is scheduled; a later remove invalidates it. */
    fun backgroundUpdateVersion(id: LocationId): Long = versionFor(id).get()

    /** Holds the location's storage boundary while updating, so removal runs after and deletes its result. */
    suspend fun updateSavedLocationIfCurrent(
        id: LocationId,
        expectedVersion: Long,
        update: suspend (CatalogLocation) -> Unit,
    ): Boolean = lockFor(id).withLock {
        if (versionFor(id).get() != expectedVersion) return@withLock false
        val row = dao.find(id.value) ?: return@withLock false
        val location = decode(row)?.location ?: return@withLock false
        update(location)
        versionFor(id).get() == expectedVersion
    }

    suspend fun add(candidate: PlaceSearchCandidate): SavedLocationResult {
        val canonicalId = candidate.canonicalPlaceId ?: return SavedLocationResult.MissingCanonicalId
        val location = candidate.toCatalogLocation(LocationId(canonicalId.value))
        val id = location.id
        return lockFor(id).withLock {
            val result = database.withTransaction {
                val current = dao.find(canonicalId.value)
                if (current != null) {
                    val existing = decode(current) ?: return@withTransaction SavedLocationResult.MalformedRecord
                    if (existing.location.coordinates != location.coordinates || existing.location.timeZone != location.timeZone) {
                        SavedLocationResult.PointConflict(canonicalId)
                    } else SavedLocationResult.AlreadySaved(existing)
                } else {
                    val inserted = dao.insert(encode(candidate, location))
                    if (inserted == -1L) {
                        val raced = dao.find(canonicalId.value)?.let(::decode)
                            ?: return@withTransaction SavedLocationResult.MalformedRecord
                        if (raced.location.coordinates != location.coordinates || raced.location.timeZone != location.timeZone)
                            SavedLocationResult.PointConflict(canonicalId)
                        else SavedLocationResult.AlreadySaved(raced)
                    } else SavedLocationResult.Added(SavedLocation(location, candidate))
                }
            }
            if (result is SavedLocationResult.Added) versionFor(id).incrementAndGet()
            result
        }
    }

    suspend fun select(id: LocationId): SavedLocationResult = database.withTransaction {
        val row = dao.find(id.value) ?: return@withTransaction SavedLocationResult.MissingLocation
        val location = decode(row) ?: return@withTransaction SavedLocationResult.MalformedRecord
        dao.setSelection(SelectedLocationRow(selectedLocationId = id.value))
        SavedLocationResult.Selected(location)
    }

    suspend fun remove(id: LocationId): SavedLocationResult {
        versionFor(id).incrementAndGet()
        return lockFor(id).withLock {
            database.withTransaction {
                val row = dao.find(id.value) ?: return@withTransaction SavedLocationResult.NoMutation
                if (decode(row) == null) return@withTransaction SavedLocationResult.MalformedRecord
                database.datasets().deleteLocation(id.value)
                if (dao.selectedId() == id.value) dao.clearSelection()
                dao.delete(id.value)
                SavedLocationResult.Removed
            }
        }
    }

    override suspend fun restore(): SavedLocationResult = database.withTransaction {
        val selectedId = dao.selectedId()
        if (selectedId == null) return@withTransaction SavedLocationResult.Selected(null)
        val row = dao.find(selectedId)
        if (row == null) {
            dao.clearSelection()
            return@withTransaction SavedLocationResult.Selected(null)
        }
        val location = decode(row) ?: return@withTransaction SavedLocationResult.MalformedRecord
        SavedLocationResult.Selected(location)
    }

    suspend fun list(): SavedLocationListResult {
        val locations = mutableListOf<SavedLocation>()
        for (row in dao.list()) locations += decode(row) ?: return SavedLocationListResult.MalformedRecord
        return SavedLocationListResult.Locations(locations)
    }

    suspend fun find(id: LocationId): SavedLocationResult {
        val row = dao.find(id.value) ?: return SavedLocationResult.MissingLocation
        return decode(row)?.let(SavedLocationResult::Selected) ?: SavedLocationResult.MalformedRecord
    }

    private fun encode(candidate: PlaceSearchCandidate, location: CatalogLocation): SavedLocationRow = SavedLocationRow(
        locationId = location.id.value,
        name = candidate.name,
        countryName = candidate.countryName,
        countryCode = candidate.countryCode,
        administrativeAreasJson = mapper.writeValueAsString(candidate.administrativeAreas),
        latitudeDegrees = location.coordinates.latitudeDegrees,
        longitudeDegrees = location.coordinates.longitudeDegrees,
        timeZoneId = location.timeZone.id,
        sourceReference = candidate.sourceReference,
        attributionSourceName = candidate.attribution.sourceName,
        attributionSourceUrl = candidate.attribution.sourceUrl.toString(),
        attributionLicenseName = candidate.attribution.licenseName,
        attributionLicenseUrl = candidate.attribution.licenseUrl.toString(),
        attributionChangesMadeNotice = candidate.attribution.changesMadeNotice,
    )

    private fun decode(row: SavedLocationRow): SavedLocation? = try {
        val id = CanonicalPlaceId(row.locationId)
        val areasNode = mapper.readTree(row.administrativeAreasJson)
        require(areasNode.isArray && areasNode.all { it.isTextual && it.asText().isNotBlank() })
        val areas = areasNode.map { it.asText() }
        val attribution = PlaceSearchAttribution(
            row.attributionSourceName, URI(row.attributionSourceUrl), row.attributionLicenseName,
            URI(row.attributionLicenseUrl), row.attributionChangesMadeNotice,
        )
        val candidate = PlaceSearchCandidate(
            row.name, row.countryName, row.countryCode, areas,
            GeoCoordinates(row.latitudeDegrees, row.longitudeDegrees), ZoneId.of(row.timeZoneId),
            row.sourceReference, attribution, id,
        )
        SavedLocation(candidate.toCatalogLocation(LocationId(id.value)), candidate)
    } catch (_: Exception) { null }
}
