package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.LocalDateTime

/** Eight consecutive, synthetic one-hour ranges used only to review the WA.1 symbol row. */
internal object DebugSkySymbolRangeFixture {
    fun events(start: LocalDateTime): List<DebugAuthoredEvent> =
        WeatherSymbolId.entries.mapIndexed { index, symbol ->
            val rangeStart = start.plusHours(index.toLong())
            DebugAuthoredEvent(
                startLocal = rangeStart,
                endLocalExclusive = rangeStart.plusHours(1),
                values = DebugAuthoredWeatherValues(),
                symbolId = symbol,
            )
        }
}
