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

/** Seven typed Storm facts and one empty-event state for installed SG.11e review. */
internal object DebugSg11StormFallbackCatalogFixture {
    val ids = listOf(
        WeatherEventId.THUNDERSTORM,
        WeatherEventId.THUNDERSTORM_WITH_RAIN,
        WeatherEventId.THUNDERSTORM_WITH_HAIL,
        WeatherEventId.THUNDERSTORM_WITH_SNOW,
        WeatherEventId.SQUALL,
        WeatherEventId.TROPICAL_CYCLONE,
        WeatherEventId.TORNADO_FUNNEL,
    )
    val expectedEvents = listOf(
        WeatherEventFact(WeatherEventId.THUNDERSTORM, 95, provider = "oxygen-debug-sg11",
            documentedCodeDescription = "Thunderstorm",
            descriptionSource = "open-meteo:forecast-api-weather-code-table"),
        WeatherEventFact(WeatherEventId.THUNDERSTORM_WITH_RAIN, provider = "oxygen-debug-sg11"),
        WeatherEventFact(WeatherEventId.THUNDERSTORM_WITH_HAIL, 96, provider = "oxygen-debug-sg11",
            documentedCodeDescription = "Thunderstorm with slight hail",
            descriptionSource = "open-meteo:forecast-api-weather-code-table"),
        WeatherEventFact(WeatherEventId.THUNDERSTORM_WITH_SNOW, provider = "oxygen-debug-sg11"),
        WeatherEventFact(WeatherEventId.SQUALL, provider = "oxygen-debug-sg11"),
        WeatherEventFact(WeatherEventId.TROPICAL_CYCLONE, provider = "oxygen-debug-sg11"),
        WeatherEventFact(WeatherEventId.TORNADO_FUNNEL, provider = "oxygen-debug-sg11"),
    )
    val stateCount: Int get() = ids.size + 1
    val unavailableIndex: Int get() = ids.size
    val asOf: Instant = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF)

    suspend fun recording(location: CatalogLocation): CatalogRecording {
        val dataset = DatasetId(DebugCatalogActivity.SG11_STORM_FALLBACK_REPLAY_ID)
        val result = SimulatedCatalogSource().load(CatalogLoadRequest(location, dataset, asOf))
        val baseline = when (result) {
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            else -> error("SG.11 Storm and fallback baseline unavailable")
        }
        val date = asOf.atZone(location.timeZone).toLocalDate()
        val instants = (0 until stateCount).map { index -> date.atTime(8 + index, 0).atZone(location.timeZone).toInstant() }
        require(instants.all { time -> baseline.catalog.facts.any { it.validTime == time } })
        val eventByTime = ids.indices.associate { instants[it] to expectedEvents[it] }
        val unavailableTime = instants[unavailableIndex]
        val facts = baseline.catalog.facts.map { fact ->
            when {
                fact.validTime in eventByTime -> fact.withEvents(
                    listOf(requireNotNull(eventByTime[fact.validTime])), 0, setOf(WeatherCondition.STORM),
                )
                fact.validTime == unavailableTime -> fact.withEvents(emptyList(), null, emptySet())
                else -> fact
            }
        }
        val source = baseline.catalog
        val catalog = CanonicalCatalog(source.location, source.datasetId, source.provenance, source.asOf,
            facts, source.productSegments, source.dailySummaries)
        return baseline.copy(catalog = catalog)
    }
}
