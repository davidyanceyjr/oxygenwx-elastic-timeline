package com.oxygen.weather.timeline.data.catalog

import java.time.ZoneId
import java.time.ZoneOffset
import java.time.zone.ZoneRulesProvider

/** Delivery channel metadata. It does not select a different catalog schema. */
enum class CatalogOrigin { LIVE, SIMULATED, REPLAY }

/** Meaning of the weather product carried by a catalog. */
enum class CatalogProductKind {
    FORECAST,
    ARCHIVED_MODEL_FORECAST,
    REANALYSIS,
    ACTUAL_OBSERVATION,
    SYNTHETIC_METEOROLOGICAL_PRODUCT,
}

@JvmInline
value class LocationId(val value: String) {
    init {
        require(value.isNotBlank()) { "Location ID must not be blank" }
    }
}

@JvmInline
value class DatasetId(val value: String) {
    init {
        require(value.isNotBlank()) { "Dataset ID must not be blank" }
    }
}

/** Geographic coordinates in decimal degrees. */
data class GeoCoordinates(val latitudeDegrees: Double, val longitudeDegrees: Double) {
    init {
        require(latitudeDegrees.isFinite() && latitudeDegrees in -90.0..90.0) {
            "Latitude must be finite and in -90..90 degrees"
        }
        require(longitudeDegrees.isFinite() && longitudeDegrees in -180.0..180.0) {
            "Longitude must be finite and in -180..180 degrees"
        }
    }
}

/** Stable local identity and timezone used to derive catalog calendar labels. */
data class CatalogLocation(
    val id: LocationId,
    val coordinates: GeoCoordinates,
    val timeZone: ZoneId,
) {
    init {
        require(timeZone !is ZoneOffset && timeZone.id in ZoneRulesProvider.getAvailableZoneIds()) {
            "Timezone must be an IANA zone ID"
        }
    }
}

/**
 * Source and product labels are opaque descriptive IDs, never schema selectors or provider DTOs.
 * `issuedAt` describes product issue/initialization time; catalog `asOf` is separate.
 */
data class CatalogProvenance(
    val origin: CatalogOrigin,
    val productKind: CatalogProductKind,
    val sourceId: String? = null,
    val productId: String? = null,
    val issuedAt: java.time.Instant? = null,
) {
    init {
        require(sourceId == null || sourceId.isNotBlank()) { "Source ID must be absent or nonblank" }
        require(productId == null || productId.isNotBlank()) { "Product ID must be absent or nonblank" }
        require(
            origin != CatalogOrigin.SIMULATED ||
                productKind == CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
        ) { "Simulated catalogs must identify a synthetic meteorological product" }
        require(
            origin != CatalogOrigin.LIVE ||
                productKind != CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
        ) { "A live catalog cannot identify a synthetic meteorological product" }
    }
}
