package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLoadResult
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.SimulatedCatalogSource
import com.oxygen.weather.timeline.data.catalog.WeatherEventFact
import com.oxygen.weather.timeline.data.catalog.WeatherEventId
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.Instant

/** Eight typed Sky facts in a full catalog, for installed SG.11a review only. */
internal object DebugSg11SkyCatalogFixture {
    val ids = listOf(
        WeatherEventId.SUNNY, WeatherEventId.CLEAR_NIGHT,
        WeatherEventId.MOSTLY_SUNNY, WeatherEventId.MOSTLY_CLEAR_NIGHT,
        WeatherEventId.PARTLY_SUNNY_CLOUDY, WeatherEventId.PARTLY_CLOUDY_NIGHT,
        WeatherEventId.MOSTLY_CLOUDY, WeatherEventId.CLOUDY_OVERCAST,
    )
    val asOf: Instant = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF)

    suspend fun recording(location: CatalogLocation): CatalogRecording {
        val dataset = DatasetId(DebugCatalogActivity.SG11_SKY_REPLAY_ID)
        val result = SimulatedCatalogSource().load(CatalogLoadRequest(location, dataset, asOf))
        val baseline = when (result) {
            is CatalogLoadResult.Success -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            is CatalogLoadResult.Partial -> CatalogRecording(result.catalog, result.expectedSupport, result.dailyExpectedSupport)
            else -> error("SG.11 Sky baseline unavailable")
        }
        val date = asOf.atZone(location.timeZone).toLocalDate()
        val instants = ids.indices.map { index -> date.atTime(8 + index, 0).atZone(location.timeZone).toInstant() }
        require(instants.all { time -> baseline.catalog.facts.any { it.validTime == time } })
        val idsByTime = instants.zip(ids).toMap()
        val facts = baseline.catalog.facts.map { fact ->
            val id = idsByTime[fact.validTime]
            if (id == null) fact else fact.withEvents(
                listOf(WeatherEventFact(id, provider = "oxygen-debug-sg11")), 0,
                when (id) {
                    WeatherEventId.SUNNY, WeatherEventId.CLEAR_NIGHT -> setOf(WeatherCondition.CLEAR)
                    WeatherEventId.MOSTLY_SUNNY, WeatherEventId.MOSTLY_CLEAR_NIGHT,
                    WeatherEventId.PARTLY_SUNNY_CLOUDY, WeatherEventId.PARTLY_CLOUDY_NIGHT -> setOf(WeatherCondition.PARTLY_CLOUDY)
                    else -> setOf(WeatherCondition.CLOUDY)
                },
            )
        }
        val source = baseline.catalog
        val catalog = CanonicalCatalog(source.location, source.datasetId, source.provenance, source.asOf,
            facts, source.productSegments, source.dailySummaries)
        return baseline.copy(catalog = catalog)
    }
}
