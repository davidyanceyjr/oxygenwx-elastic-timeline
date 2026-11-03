package com.oxygen.weather.timeline.presentation.symbol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherSymbolCatalogTest {
    @Test fun `sky row has eight unique semantic ids with explicit artwork`() {
        assertEquals(8, WeatherSymbolId.entries.size)
        assertEquals(WeatherSymbolId.entries.toSet(), WeatherSymbolCatalog.definitions.map { it.id }.toSet())
        assertEquals(8, WeatherSymbolCatalog.definitions.map { it.id }.toSet().size)
        assertTrue(WeatherSymbolId.entries.map { it.label }.all(String::isNotBlank))
        WeatherSymbolId.entries.forEach { id -> assertNotNull(WeatherSymbolCatalog.resolve(id).artwork) }
    }
}
