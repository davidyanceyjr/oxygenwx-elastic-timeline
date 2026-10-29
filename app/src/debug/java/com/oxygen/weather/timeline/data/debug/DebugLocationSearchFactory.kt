package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CanonicalPlaceId
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import java.net.URI
import java.time.ZoneId

/** Deterministic, debug-source-set-only places for repeatable selector interaction review. */
object DebugLocationSearchFactory {
    @JvmStatic
    fun create(): PlaceSearcher = PlaceSearcher { query ->
        when {
            query.contains("springfield", ignoreCase = true) -> PlaceSearchResult.Matches(
                listOf(springfieldIllinois, springfieldMassachusetts),
                rejectedCandidateCount = 0,
            )
            query.contains("madison", ignoreCase = true) -> PlaceSearchResult.Matches(
                listOf(madisonWisconsin),
                rejectedCandidateCount = 0,
            )
            else -> PlaceSearchResult.NoMatches
        }
    }

    private val attribution = PlaceSearchAttribution(
        sourceName = "Open-Meteo Geocoding API / GeoNames (review fixture)",
        sourceUrl = URI("https://open-meteo.com/en/docs/geocoding-api"),
        licenseName = "GeoNames CC BY 4.0",
        licenseUrl = URI("https://creativecommons.org/licenses/by/4.0/"),
        changesMadeNotice = "Deterministic review fixture; this is not a live geocoding response.",
    )

    private val springfieldIllinois = place(
        name = "Springfield", area = "Illinois", country = "United States", code = "US",
        latitude = 39.8017, longitude = -89.6436, zone = "America/Chicago", id = 4_250_542,
    )
    private val springfieldMassachusetts = place(
        name = "Springfield", area = "Massachusetts", country = "United States", code = "US",
        latitude = 42.1015, longitude = -72.5898, zone = "America/New_York", id = 4_951_788,
    )
    private val madisonWisconsin = place(
        name = "Madison", area = "Wisconsin", country = "United States", code = "US",
        latitude = 43.0748, longitude = -89.3848, zone = "America/Chicago", id = 5_261_457,
    )

    private fun place(
        name: String,
        area: String,
        country: String,
        code: String,
        latitude: Double,
        longitude: Double,
        zone: String,
        id: Long,
    ) = PlaceSearchCandidate(
        name = name,
        countryName = country,
        countryCode = code,
        administrativeAreas = listOf(area),
        coordinates = GeoCoordinates(latitude, longitude),
        timeZone = ZoneId.of(zone),
        sourceReference = "geonames:$id",
        attribution = attribution,
        canonicalPlaceId = CanonicalPlaceId.geonames(id),
    )
}
