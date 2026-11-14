package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.RecordedCatalogCodec
import com.oxygen.weather.timeline.data.catalog.RecordingDecodeResult
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherEventPriority
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DebugSg11StormFallbackCatalogFixtureTest {
    @Test fun `storm identities and separate unavailable state survive version 5`() = runBlocking {
        val location = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.8781, -87.6298),
            ZoneId.of("America/Chicago"))
        val recording = DebugSg11StormFallbackCatalogFixture.recording(location)
        val date = DebugSg11StormFallbackCatalogFixture.asOf.atZone(location.timeZone).toLocalDate()
        val reviewFacts = DebugSg11StormFallbackCatalogFixture.ids.indices.map { index ->
            val time = date.atTime(8 + index, 0).atZone(location.timeZone).toInstant()
            recording.catalog.facts.single { it.validTime == time }
        }

        assertEquals(DebugSg11StormFallbackCatalogFixture.ids, reviewFacts.map { it.events.single().id })
        assertEquals(DebugSg11StormFallbackCatalogFixture.ids.map { it.name },
            reviewFacts.map { WeatherEventPriority.select(it.events, it.primaryEventIndex)!!.id.name })
        assertEquals(DebugSg11StormFallbackCatalogFixture.ids.map { it.name },
            reviewFacts.map { WeatherSymbolId.valueOf(it.events.single().id.name).name })
        assertEquals(DebugSg11StormFallbackCatalogFixture.ids.map { WeatherSymbolId.valueOf(it.name).label },
            reviewFacts.map { WeatherSymbolId.valueOf(it.events.single().id.name).label })
        assertTrue(reviewFacts.all { WeatherCondition.STORM in it.conditions && WeatherCondition.RAIN !in it.conditions })

        listOf(0, 2).forEachIndexed { index, factIndex ->
            val event = reviewFacts[factIndex].events.single()
            assertEquals(listOf(95, 96)[index], event.sourceCode)
            assertEquals(listOf("Thunderstorm", "Thunderstorm with slight hail")[index],
                event.documentedCodeDescription)
            assertEquals("open-meteo:forecast-api-weather-code-table", event.descriptionSource)
            assertEquals("oxygen-debug-sg11", event.provider)
        }
        listOf(1, 3, 4, 5, 6).forEach { index ->
            assertNull(reviewFacts[index].events.single().sourceCode)
            assertNull(reviewFacts[index].events.single().documentedCodeDescription)
            assertNull(reviewFacts[index].events.single().descriptionSource)
        }

        val handoffTime = date.atTime(8 + DebugSg11StormFallbackCatalogFixture.handoffIndex, 0)
            .atZone(location.timeZone).toInstant()
        val handoff = recording.catalog.facts.single { it.validTime == handoffTime }
        assertEquals(listOf(DebugSg11StormFallbackCatalogFixture.handoffEvent), handoff.events)
        assertEquals(setOf(WeatherCondition.PARTLY_CLOUDY), handoff.conditions)

        val diagnostic = DebugSg11StormFallbackCatalogFixture.unavailableRecording(location)
        val unavailable = diagnostic.catalog.facts.single { it.validTime == handoffTime }
        assertTrue(unavailable.events.isEmpty())
        assertNull(unavailable.primaryEventIndex)
        assertTrue(unavailable.conditions.isEmpty())

        val encoded = RecordedCatalogCodec.encode(diagnostic)
        val decoded = RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success
        assertEquals(diagnostic.catalog, decoded.recording.catalog)
        assertTrue(decoded.recording.catalog.facts.single { it.validTime == handoffTime }.events.isEmpty())
    }
}
