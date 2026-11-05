package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.RecordedCatalogCodec
import com.oxygen.weather.timeline.data.catalog.RecordingDecodeResult
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherEventId
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugSg11SnowMixedCatalogFixtureTest {
    @Test fun `snow mixed review facts retain all typed identities through version 5 reload`() = runBlocking {
        val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298),
            ZoneId.of("America/Chicago"))
        val recording = DebugSg11SnowMixedCatalogFixture.recording(location)

        val reviewFacts = recording.catalog.facts.filter { it.events.isNotEmpty() }
        assertEquals(DebugSg11SnowMixedCatalogFixture.ids.size, reviewFacts.size)
        assertEquals(DebugSg11SnowMixedCatalogFixture.ids, reviewFacts.map { it.events.single().id })
        assertTrue(reviewFacts.all { it.primaryEventIndex == 0 && WeatherCondition.SNOW in it.conditions })
        val providerAssignments = listOf(
            WeatherEventId.FLURRIES_SNOW_GRAINS to (77 to "Snow grains"),
            WeatherEventId.SNOW to (71 to "Slight snowfall"),
            WeatherEventId.HEAVY_SNOW to (75 to "Heavy snowfall"),
            WeatherEventId.SNOW_SHOWERS to (85 to "Slight snow showers"),
        )
        providerAssignments.forEachIndexed { index, (id, expected) ->
            val event = reviewFacts[index].events.single()
            assertEquals(id, event.id)
            assertEquals(expected.first, event.sourceCode)
            assertEquals(expected.second, event.documentedCodeDescription)
            assertEquals("open-meteo:forecast-api-weather-code-table", event.descriptionSource)
        }
        reviewFacts.drop(4).forEach { fact ->
            assertNull(fact.events.single().sourceCode)
            assertNull(fact.events.single().documentedCodeDescription)
            assertNull(fact.events.single().descriptionSource)
        }

        val encoded = RecordedCatalogCodec.encode(recording)
        val decoded = RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success
        assertEquals(recording.catalog, decoded.recording.catalog)
        assertEquals(reviewFacts.map { it.events }, decoded.recording.catalog.facts.filter { it.events.isNotEmpty() }.map { it.events })
    }
}
