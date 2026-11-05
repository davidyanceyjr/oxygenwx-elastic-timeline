package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.RecordedCatalogCodec
import com.oxygen.weather.timeline.data.catalog.RecordingDecodeResult
import com.oxygen.weather.timeline.data.catalog.WeatherEventPriority
import com.oxygen.weather.timeline.data.catalog.WeatherEventId
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugSg11VisibilityWindCatalogFixtureTest {
    @Test fun `visibility wind review facts retain identities and version 5 evidence`() = runBlocking {
        val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298),
            ZoneId.of("America/Chicago"))
        val recording = DebugSg11VisibilityWindCatalogFixture.recording(location)
        val reviewFacts = recording.catalog.facts.filter { it.events.isNotEmpty() }
        assertEquals(DebugSg11VisibilityWindCatalogFixture.ids, reviewFacts.map { it.events.single().id })
        assertEquals(DebugSg11VisibilityWindCatalogFixture.ids.map { it.name },
            reviewFacts.map { WeatherEventPriority.select(it.events, it.primaryEventIndex)!!.id.name })
        assertEquals(DebugSg11VisibilityWindCatalogFixture.ids.map { it.name },
            reviewFacts.map { WeatherSymbolId.valueOf(it.events.single().id.name).name })
        assertEquals(DebugSg11VisibilityWindCatalogFixture.ids.map { WeatherSymbolId.valueOf(it.name).label },
            reviewFacts.map { WeatherSymbolId.valueOf(it.events.single().id.name).label })

        listOf(1, 2).forEachIndexed { index, factIndex ->
            val event = reviewFacts[factIndex].events.single()
            assertEquals(listOf(45, 48)[index], event.sourceCode)
            assertEquals(listOf("Fog", "Depositing rime fog")[index], event.documentedCodeDescription)
            assertEquals("open-meteo:forecast-api-weather-code-table", event.descriptionSource)
            assertEquals("oxygen-debug-sg11", event.provider)
        }
        listOf(0, 3, 4, 5, 6, 7).forEach { index ->
            assertNull(reviewFacts[index].events.single().sourceCode)
            assertNull(reviewFacts[index].events.single().documentedCodeDescription)
            assertNull(reviewFacts[index].events.single().descriptionSource)
        }

        val encoded = RecordedCatalogCodec.encode(recording)
        val decoded = RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success
        assertEquals(recording.catalog, decoded.recording.catalog)
        assertEquals(reviewFacts.map { it.events },
            decoded.recording.catalog.facts.filter { it.events.isNotEmpty() }.map { it.events })
        assertTrue(reviewFacts.all { it.primaryEventIndex == 0 })
    }
}
