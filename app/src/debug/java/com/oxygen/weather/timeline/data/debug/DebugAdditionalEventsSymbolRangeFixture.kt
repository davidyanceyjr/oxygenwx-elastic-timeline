package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight adjacent synthetic one-hour ranges for the WA.6 Additional Events artwork review. */
internal object DebugAdditionalEventsSymbolRangeFixture {
    private val symbols = listOf(
        WeatherSymbolId.DRY_THUNDERSTORM,
        WeatherSymbolId.TROPICAL_STORM,
        WeatherSymbolId.WATERSPOUT,
        WeatherSymbolId.FROST,
        WeatherSymbolId.EXTREME_HEAT,
        WeatherSymbolId.EXTREME_COLD,
        WeatherSymbolId.DUST_SANDSTORM,
        WeatherSymbolId.VOLCANIC_ASH,
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
