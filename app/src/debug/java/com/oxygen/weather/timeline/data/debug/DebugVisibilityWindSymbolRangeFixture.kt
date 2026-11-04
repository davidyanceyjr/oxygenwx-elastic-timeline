package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight adjacent synthetic one-hour ranges for the WA.4 Visibility + Wind artwork review. */
internal object DebugVisibilityWindSymbolRangeFixture {
    private val symbols = listOf(
        WeatherSymbolId.MIST, WeatherSymbolId.FOG,
        WeatherSymbolId.FREEZING_FOG, WeatherSymbolId.HAZE_SMOKE,
        WeatherSymbolId.DUST_SAND_ASH, WeatherSymbolId.WINDY_GUSTY,
        WeatherSymbolId.BLOWING_SNOW, WeatherSymbolId.BLIZZARD,
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
