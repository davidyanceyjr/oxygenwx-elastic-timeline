package com.oxygen.weather.timeline.data

import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/** Demo fixture ONLY. No claim to represent live or historical conditions. */
enum class SkyCondition { CLEAR, PARTLY_CLOUDY, CLOUDY, RAIN, STORM }

data class ForecastHour(
    val time: LocalDateTime,
    val temperatureC: Double,
    val cloudPercent: Double,
    val precipitationProbabilityPercent: Double,
    val windKph: Double,
    val condition: SkyCondition,
)

data class ForecastDay(
    val date: LocalDate,
    val lowC: Double,
    val highC: Double,
    val maxPrecipitationProbabilityPercent: Double,
    val representative: ForecastHour,
)

/**
 * Immutable deterministic fixture: 8 full local days / 192 hourly entries.
 * Separates source-like hourly data from presentation and rendering.
 */
object SampleForecast {
    val startDate: LocalDate = LocalDate.of(2026, 10, 23)
    const val DAY_COUNT: Int = 8

    val hours: List<ForecastHour> = buildList {
        repeat(DAY_COUNT) { d ->
            repeat(24) { h ->
                val daylight = ((sin(((h - 6) / 12.0) * Math.PI) + 1.0) / 2.0).coerceIn(0.0, 1.0)
                val cloud = when (d) {
                    0 -> 79.0 - 12.0 * daylight
                    1 -> 77.0 - 67.0 * (h / 23.0)
                    2 -> 18.0 + 24.0 * (1.0 - daylight)
                    3 -> 49.0 + 32.0 * (h / 23.0)
                    4 -> 90.0 - 13.0 * daylight
                    5 -> 53.0 - 32.0 * daylight
                    6 -> 27.0 + 27.0 * (1.0 - daylight)
                    else -> 37.0 + 22.0 * daylight
                }.coerceIn(0.0, 100.0)
                val chance = when (d) {
                    0 -> 50.0 + 30.0 * daylight
                    1 -> max(0.0, 55.0 - h * 5.2)
                    2 -> 3.0
                    3 -> 5.0 + 27.0 * (h / 23.0)
                    4 -> 64.0 + 18.0 * daylight
                    5 -> 12.0
                    6 -> 4.0
                    else -> 15.0
                }.coerceIn(0.0, 100.0)
                val condition = when {
                    chance >= 78.0 && cloud > 85.0 -> SkyCondition.STORM
                    chance >= 45.0 -> SkyCondition.RAIN
                    cloud >= 76.0 -> SkyCondition.CLOUDY
                    cloud >= 29.0 -> SkyCondition.PARTLY_CLOUDY
                    else -> SkyCondition.CLEAR
                }
                val temp = 15.0 + (d % 3) * 1.8 +
                    5.3 * sin(((h - 8.0) / 24.0) * 2.0 * Math.PI) -
                    if (d == 4) 3.0 else 0.0
                add(
                    ForecastHour(
                        time = startDate.plusDays(d.toLong()).atTime(h, 0),
                        temperatureC = temp,
                        cloudPercent = cloud,
                        precipitationProbabilityPercent = chance,
                        windKph = 9.0 + d * 1.7 + 3.0 * cos(h / 7.0),
                        condition = condition,
                    )
                )
            }
        }
    }

    val days: List<ForecastDay> = (0 until DAY_COUNT).map { d ->
        val range = hours.subList(d * 24, (d + 1) * 24)
        ForecastDay(
            date = startDate.plusDays(d.toLong()),
            lowC = range.minOf { it.temperatureC },
            highC = range.maxOf { it.temperatureC },
            maxPrecipitationProbabilityPercent = range.maxOf { it.precipitationProbabilityPercent },
            representative = range[14],
        )
    }
}
