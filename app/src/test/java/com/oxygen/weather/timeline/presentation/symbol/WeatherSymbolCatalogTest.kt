package com.oxygen.weather.timeline.presentation.symbol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherSymbolCatalogTest {
    @Test fun `weather event identities have explicit artwork and row order`() {
        assertEquals(48, WeatherSymbolId.entries.size)
        assertEquals(WeatherSymbolId.entries.toSet(), WeatherSymbolCatalog.definitions.map { it.id }.toSet())
        assertEquals(48, WeatherSymbolCatalog.definitions.map { it.id }.toSet().size)
        assertTrue(WeatherSymbolId.entries.map { it.label }.all(String::isNotBlank))
        WeatherSymbolId.entries.forEach { id -> assertNotNull(WeatherSymbolCatalog.resolve(id).artwork) }
        assertEquals(
            listOf(
                WeatherSymbolId.DRIZZLE, WeatherSymbolId.RAIN, WeatherSymbolId.HEAVY_RAIN,
                WeatherSymbolId.RAIN_SHOWERS, WeatherSymbolId.FREEZING_DRIZZLE,
                WeatherSymbolId.FREEZING_RAIN, WeatherSymbolId.ICE_PELLETS, WeatherSymbolId.HAIL,
            ),
            WeatherSymbolId.entries.drop(8).take(8),
        )
        assertEquals(
            listOf(
                WeatherSymbolId.FLURRIES_SNOW_GRAINS, WeatherSymbolId.SNOW,
                WeatherSymbolId.HEAVY_SNOW, WeatherSymbolId.SNOW_SHOWERS,
                WeatherSymbolId.RAIN_AND_SNOW, WeatherSymbolId.WINTRY_MIX,
                WeatherSymbolId.GRAUPEL, WeatherSymbolId.ICE_CRYSTALS,
            ),
            WeatherSymbolId.entries.drop(16).take(8),
        )
    }
}
