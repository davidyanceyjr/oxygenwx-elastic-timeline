package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight adjacent synthetic one-hour ranges for the WA.2 Rain + Ice artwork review. */
internal object DebugRainIceSymbolRangeFixture {
    private val symbols = listOf(
        WeatherSymbolId.DRIZZLE, WeatherSymbolId.RAIN, WeatherSymbolId.HEAVY_RAIN,
        WeatherSymbolId.RAIN_SHOWERS, WeatherSymbolId.FREEZING_DRIZZLE,
        WeatherSymbolId.FREEZING_RAIN, WeatherSymbolId.ICE_PELLETS, WeatherSymbolId.HAIL,
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
