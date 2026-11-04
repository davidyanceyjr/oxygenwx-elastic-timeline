package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight adjacent synthetic one-hour ranges for the WA.5 Storms + Fallback artwork review. */
internal object DebugStormFallbackSymbolRangeFixture {
    private val symbols = listOf(
        WeatherSymbolId.THUNDERSTORM,
        WeatherSymbolId.THUNDERSTORM_WITH_RAIN,
        WeatherSymbolId.THUNDERSTORM_WITH_HAIL,
        WeatherSymbolId.THUNDERSTORM_WITH_SNOW,
        WeatherSymbolId.SQUALL,
        WeatherSymbolId.TROPICAL_CYCLONE,
        WeatherSymbolId.TORNADO_FUNNEL,
        WeatherSymbolId.CONDITION_UNAVAILABLE,
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
