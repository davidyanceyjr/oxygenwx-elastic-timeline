package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadResult
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherEventFact
import com.oxygen.weather.timeline.data.catalog.WeatherEventId
import java.time.Instant

/** Eight typed Snow and mixed precipitation facts for installed SG.11c review. */
internal object DebugSg11SnowMixedCatalogFixture {
    val ids = listOf(
        WeatherEventId.FLURRIES_SNOW_GRAINS, WeatherEventId.SNOW,
        WeatherEventId.HEAVY_SNOW, WeatherEventId.SNOW_SHOWERS,
        WeatherEventId.RAIN_AND_SNOW, WeatherEventId.WINTRY_MIX,
        WeatherEventId.GRAUPEL, WeatherEventId.ICE_CRYSTALS,
    )
    val expectedEvents = ids.indices.map { index ->
        val sourceCode = listOf(77, 71, 75, 85, null, null, null, null)[index]
        val description = listOf(
            "Snow grains", "Slight snowfall", "Heavy snowfall", "Slight snow showers",
            null, null, null, null,
        )[index]
        WeatherEventFact(ids[index], sourceCode = sourceCode, provider = "oxygen-debug-sg11",
            documentedCodeDescription = description,
            descriptionSource = description?.let { "open-meteo:forecast-api-weather-code-table" })
    }
    val asOf: Instant = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF)

    suspend fun recording(location: CatalogLocation): CatalogRecording {
        val dataset = DatasetId(DebugCatalogActivity.SG11_SNOW_MIXED_REPLAY_ID)
        val result = SimulatedCatalogSource().load(CatalogLoadRequest(location, dataset, asOf))
        val baseline = when (result) {
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            else -> error("SG.11 Snow and mixed precipitation baseline unavailable")
        }
        val date = asOf.atZone(location.timeZone).toLocalDate()
        val instants = ids.indices.map { index -> date.atTime(8 + index, 0).atZone(location.timeZone).toInstant() }
        require(instants.all { time -> baseline.catalog.facts.any { it.validTime == time } })
        val eventByTime = instants.indices.associate { instants[it] to expectedEvents[it] }
        val facts = baseline.catalog.facts.map { fact ->
            val event = eventByTime[fact.validTime] ?: return@map fact
            fact.withEvents(listOf(event), 0, setOf(WeatherCondition.SNOW))
        }
        val source = baseline.catalog
        val catalog = CanonicalCatalog(source.location, source.datasetId, source.provenance, source.asOf,
            facts, source.productSegments, source.dailySummaries)
        return baseline.copy(catalog = catalog)
    }
}
