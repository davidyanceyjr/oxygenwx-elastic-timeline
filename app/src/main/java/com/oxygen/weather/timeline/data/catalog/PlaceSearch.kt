package com.oxygen.weather.timeline.data.catalog

import java.net.URI
import java.time.ZoneId
import java.time.ZoneOffset

/** Provider-neutral result for manually searching for a catalog location. */
data class PlaceSearchCandidate(
    val name: String,
    val countryName: String?,
    val countryCode: String?,
    val administrativeAreas: List<String>,
    val coordinates: GeoCoordinates,
    val timeZone: ZoneId,
    val sourceReference: String?,
    val attribution: PlaceSearchAttribution,
) {
    init {
        require(name.isNotBlank())
        require(countryName?.isNotBlank() != false)
        require(countryCode?.isNotBlank() != false)
        require(countryName != null || countryCode != null)
        require(administrativeAreas.all(String::isNotBlank))
        require(timeZone !is ZoneOffset && timeZone.id in ZoneId.getAvailableZoneIds())
        require(sourceReference?.isNotBlank() != false)
    }

    /** Descriptive UI text only; it must never be used as the local location identity. */
    val displayLabel: String
        get() = buildList {
            add(name)
            addAll(administrativeAreas)
            add(countryName?.let { name -> if (countryCode == null) name else "$name ($countryCode)" } ?: countryCode!!)
        }.joinToString(", ")

    /** Converts a chosen result using an identity assigned by the accepting application layer. */
    fun toCatalogLocation(id: LocationId): CatalogLocation = CatalogLocation(id, coordinates, timeZone)
}

data class PlaceSearchAttribution(
    val sourceName: String,
    val sourceUrl: URI,
    val licenseName: String,
    val licenseUrl: URI,
    val changesMadeNotice: String?,
) {
    init {
        require(sourceName.isNotBlank() && licenseName.isNotBlank())
        require(sourceUrl.scheme == "https" && licenseUrl.scheme == "https")
        require(changesMadeNotice?.isNotBlank() != false)
    }
}

sealed interface PlaceSearchResult {
    data class Matches(val candidates: List<PlaceSearchCandidate>, val rejectedCandidateCount: Int) : PlaceSearchResult {
        init { require(candidates.isNotEmpty() && rejectedCandidateCount >= 0) }
    }

    data object NoMatches : PlaceSearchResult

    data class Failure(val reason: Reason, val detail: String? = null) : PlaceSearchResult {
        enum class Reason { INVALID_QUERY, TIMEOUT, CONNECTIVITY, HTTP, INVALID_RESPONSE, OVERSIZED_RESPONSE }
        init { require(detail?.isNotBlank() != false) }
    }
}

/** Manual search contract. Implementations must not load or replace weather catalogs. */
fun interface PlaceSearcher {
    suspend fun search(query: String): PlaceSearchResult
}
