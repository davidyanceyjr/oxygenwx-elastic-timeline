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

/** Eight canonical-only Additional events for installed SG.11f review. */
internal object DebugSg11AdditionalEventsCatalogFixture {
    val ids = listOf(
        WeatherEventId.DRY_THUNDERSTORM,
        WeatherEventId.TROPICAL_STORM,
        WeatherEventId.WATERSPOUT,
        WeatherEventId.FROST,
        WeatherEventId.EXTREME_HEAT,
        WeatherEventId.EXTREME_COLD,
        WeatherEventId.DUST_SANDSTORM,
        WeatherEventId.VOLCANIC_ASH,
    )
    val expectedEvents = ids.map { WeatherEventFact(it, provider = "oxygen-debug-sg11") }
    val asOf: Instant = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF)

    suspend fun recording(location: CatalogLocation): CatalogRecording {
        val dataset = DatasetId(DebugCatalogActivity.SG11_ADDITIONAL_EVENTS_REPLAY_ID)
        val result = SimulatedCatalogSource().load(CatalogLoadRequest(location, dataset, asOf))
        val baseline = when (result) {
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            else -> error("SG.11 Additional events baseline unavailable")
        }
        val date = asOf.atZone(location.timeZone).toLocalDate()
        val instants = ids.indices.map { index -> date.atTime(8 + index, 0).atZone(location.timeZone).toInstant() }
        require(instants.all { time -> baseline.catalog.facts.any { it.validTime == time } })
        val eventByTime = instants.indices.associate { instants[it] to expectedEvents[it] }
        val facts = baseline.catalog.facts.map { fact ->
            val event = eventByTime[fact.validTime] ?: return@map fact
            fact.withEvents(listOf(event), 0, emptySet())
        }
        val source = baseline.catalog
        val catalog = CanonicalCatalog(source.location, source.datasetId, source.provenance, source.asOf,
            facts, source.productSegments, source.dailySummaries)
        return baseline.copy(catalog = catalog)
    }
}
