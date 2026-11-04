package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight adjacent synthetic one-hour ranges for the WA.3 Snow + Mixed Precipitation artwork review. */
internal object DebugSnowMixedSymbolRangeFixture {
    private val symbols = listOf(
        WeatherSymbolId.FLURRIES_SNOW_GRAINS, WeatherSymbolId.SNOW,
        WeatherSymbolId.HEAVY_SNOW, WeatherSymbolId.SNOW_SHOWERS,
        WeatherSymbolId.RAIN_AND_SNOW, WeatherSymbolId.WINTRY_MIX,
        WeatherSymbolId.GRAUPEL, WeatherSymbolId.ICE_CRYSTALS,
    )

    fun events(start: LocalDateTime): List<DebugAuthoredEvent> =
        symbols.mapIndexed { index, symbol ->
            val rangeStart = start.plusHours(index.toLong())
            DebugAuthoredEvent(
                startLocal = rangeStart,
                endLocalExclusive = rangeStart.plusHours(1),
                values = DebugAuthoredWeatherValues(),
                symbolId = symbol,
            )
        }
}
