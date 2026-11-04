package com.oxygen.weather.timeline.presentation.symbol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherSymbolCatalogTest {
    @Test fun `sky and rain ice rows have sixteen unique semantic ids with explicit artwork`() {
        assertEquals(16, WeatherSymbolId.entries.size)
        assertEquals(WeatherSymbolId.entries.toSet(), WeatherSymbolCatalog.definitions.map { it.id }.toSet())
        assertEquals(16, WeatherSymbolCatalog.definitions.map { it.id }.toSet().size)
        assertTrue(WeatherSymbolId.entries.map { it.label }.all(String::isNotBlank))
        WeatherSymbolId.entries.forEach { id -> assertNotNull(WeatherSymbolCatalog.resolve(id).artwork) }
        assertEquals(
            listOf("Drizzle", "Rain", "Heavy rain", "Rain showers", "Freezing drizzle", "Freezing rain", "Ice pellets", "Hail"),
            WeatherSymbolId.entries.drop(8).map { it.label },
        )
    }
}
