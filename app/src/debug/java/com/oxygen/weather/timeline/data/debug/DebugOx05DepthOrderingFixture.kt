package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogActivationRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadResult
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.CatalogStorageNamespace
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CatalogWindowResult
import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.Instant
import java.time.ZoneId

/** Fixed, debug-only SIMULATED recording for the installed OX-05 depth review. */
internal object DebugOx05DepthOrderingFixture {
    const val DATASET_ID = "ox05-depth-review-synthetic-astronomy-v1"
    const val LOCATION_ID = "debug:ox05-depth-review-chicago"
    const val AS_OF = "2026-10-28T12:00:00Z"
    const val CELESTIAL_OVERLAP_INSTANT = "2026-10-28T17:00:00Z"
    const val MODERATE_RAIN_INSTANT = "2026-10-29T17:00:00Z"

    val datasetId = DatasetId(DATASET_ID)
    val asOf: Instant = Instant.parse(AS_OF)
    val celestialOverlapInstant: Instant = Instant.parse(CELESTIAL_OVERLAP_INSTANT)
    val moderateRainInstant: Instant = Instant.parse(MODERATE_RAIN_INSTANT)
    val location = com.oxygen.weather.timeline.data.catalog.CatalogLocation(
        LocationId(LOCATION_ID),
        GeoCoordinates(41.8781, -87.6298),
        ZoneId.of("America/Chicago"),
    )
    val storageNamespace = CatalogStorageNamespace.SIMULATED

    fun activationRequest() = CatalogActivationRequest(
        location = location,
        origin = CatalogOrigin.SIMULATED,
        datasetId = datasetId,
    )

    suspend fun recording(): CatalogRecording {
        val baseline = when (val result = SimulatedCatalogSource().load(
            CatalogLoadRequest(location, datasetId, asOf),
        )) {
            is CatalogLoadResult.Success -> CatalogRecording(
                result.catalog, result.expectedSupport, result.dailyExpectedSupport,
            )
            is CatalogLoadResult.Partial -> CatalogRecording(
                result.catalog, result.expectedSupport, result.dailyExpectedSupport,
            )
            else -> error("The deterministic SIMULATED source returned no usable OX-05 catalog")
        }

        val reviewFacts = mapOf(
            celestialOverlapInstant to (setOf(WeatherCondition.CLEAR) to 0.0),
            moderateRainInstant to (setOf(WeatherCondition.RAIN) to 88.0),
        )
        val found = mutableSetOf<Instant>()
        val facts = baseline.catalog.facts.map { fact ->
            val weather = reviewFacts[fact.validTime] ?: return@map fact
            found += fact.validTime
            fact.withReviewWeather(weather.first, weather.second)
        }
        check(found == reviewFacts.keys) { "The SIMULATED catalog did not contain both fixed OX-05 review instants" }

        val source = baseline.catalog
        val catalog = CanonicalCatalog(
            location = source.location,
            datasetId = source.datasetId,
            provenance = source.provenance,
            asOf = source.asOf,
            facts = facts,
            productSegments = source.productSegments,
            dailySummaries = source.dailySummaries,
        )
        check(catalog.dailySummaries.isEmpty()) {
            "OX-05 authored astronomy belongs in the debug sidecar, not catalog daily summaries"
        }
        check(CatalogWindow.create(
            catalog,
            baseline.expectedSupport,
            dailyExpectedSupport = baseline.dailyExpectedSupport,
        ) is CatalogWindowResult.Success) {
            "The OX-05 review recording must retain a valid 44-date catalog window"
        }
        return baseline.copy(catalog = catalog)
    }

    fun matchesReviewContract(recording: CatalogRecording): Boolean {
        val catalog = recording.catalog
        if (catalog.location != location || catalog.datasetId != datasetId || catalog.asOf != asOf ||
            catalog.provenance.origin != CatalogOrigin.SIMULATED || catalog.dailySummaries.isNotEmpty()
        ) return false
        val clear = catalog.facts.singleOrNull { it.validTime == celestialOverlapInstant } ?: return false
        val rain = catalog.facts.singleOrNull { it.validTime == moderateRainInstant } ?: return false
        return clear.conditions == setOf(WeatherCondition.CLEAR) && clear.cloudCoverPercent == 0.0 &&
            rain.conditions == setOf(WeatherCondition.RAIN) && rain.cloudCoverPercent == 88.0 &&
            CatalogWindow.create(
                catalog,
                recording.expectedSupport,
                dailyExpectedSupport = recording.dailyExpectedSupport,
            ) is CatalogWindowResult.Success
    }

    private fun CanonicalWeatherFacts.withReviewWeather(
        conditions: Set<WeatherCondition>,
        cloudCoverPercent: Double,
    ) = CanonicalWeatherFacts(
        validTime = validTime,
        conditions = conditions,
        phenomena = phenomena,
        temperatureC = temperatureC,
        precipitationProbabilityPercent = precipitationProbabilityPercent,
        thunderstormProbabilityPercent = thunderstormProbabilityPercent,
        relativeHumidityPercent = relativeHumidityPercent,
        cloudCoverPercent = cloudCoverPercent,
        windSpeedKph = windSpeedKph,
        windGustKph = windGustKph,
        windDirectionDegrees = windDirectionDegrees,
        rainAmountMillimeters = rainAmountMillimeters,
        totalPrecipitationMillimeters = totalPrecipitationMillimeters,
        precipitationRate = precipitationRate,
        snowfallCentimeters = snowfallCentimeters,
        snowDepthCentimeters = snowDepthCentimeters,
        surfacePressureHpa = surfacePressureHpa,
        meanSeaLevelPressureHpa = meanSeaLevelPressureHpa,
        visibilityKm = visibilityKm,
        events = events,
        primaryEventIndex = primaryEventIndex,
        sourceWeatherCode = sourceWeatherCode,
        apparentTemperatureC = apparentTemperatureC,
    )
}
