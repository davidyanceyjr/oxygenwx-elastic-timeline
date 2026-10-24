package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.ForecastDay
import com.oxygen.weather.timeline.data.ForecastHour
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.floor
import kotlin.math.roundToInt

/** Presentation-only view. All underlying fixture weather units remain metric. */
data class SkyFrame(
    val condition: SkyCondition,
    val temperatureC: Double,
    val cloudPercent: Double,
    val rainChancePercent: Double,
    val windKph: Double,
)

data class ForecastView(
    val heading: String,
    val timeLabel: String,
    val conditionsLabel: String,
    val temperatureF: Int,
    val precipitationPercent: Int,
    val windMph: Int,
    val lowF: Int,
    val highF: Int,
    val sky: SkyFrame,
    val isPrecision: Boolean,
)

object ForecastProjection {
    fun fromHourOffset(offset: Double, precision: Boolean): ForecastView {
        val clamped = offset.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        val dayIndex = ElasticTimeAxis.dayIndex(clamped).coerceIn(0, SampleForecast.days.lastIndex)
        val day = SampleForecast.days[dayIndex]
        val sky = if (precision) interpolate(clamped) else day.representative.toFrame()
        val selectedTime = SampleForecast.hours[floor(clamped).toInt()].time
        val dayLabel = day.date.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US))
        val hourLabel = selectedTime.format(DateTimeFormatter.ofPattern("h a", Locale.US))
        return ForecastView(
            heading = dayLabel.uppercase(Locale.US),
            timeLabel = if (precision) hourLabel else "DAY OVERVIEW",
            conditionsLabel = sky.condition.displayName(),
            temperatureF = toF(sky.temperatureC),
            precipitationPercent = sky.rainChancePercent.roundToInt(),
            windMph = (sky.windKph / 1.609344).roundToInt(),
            lowF = toF(day.lowC),
            highF = toF(day.highC),
            sky = sky,
            isPrecision = precision,
        )
    }

    /** Numeric interpolation is for visual continuity, not a minute-accurate source forecast. */
    fun interpolate(hourOffset: Double): SkyFrame {
        val t = hourOffset.coerceIn(0.0, SampleForecast.hours.lastIndex.toDouble())
        val left = floor(t).toInt()
        val right = (left + 1).coerceAtMost(SampleForecast.hours.lastIndex)
        val weight = t - left
        val a = SampleForecast.hours[left]
        val b = SampleForecast.hours[right]
        fun mix(x: Double, y: Double) = x + (y - x) * weight
        return SkyFrame(
            condition = if (weight < 0.5) a.condition else b.condition,
            temperatureC = mix(a.temperatureC, b.temperatureC),
            cloudPercent = mix(a.cloudPercent, b.cloudPercent),
            rainChancePercent = mix(a.precipitationProbabilityPercent, b.precipitationProbabilityPercent),
            windKph = mix(a.windKph, b.windKph),
        )
    }

    private fun ForecastHour.toFrame() = SkyFrame(
        condition, temperatureC, cloudPercent, precipitationProbabilityPercent, windKph,
    )

    private fun toF(celsius: Double) = (celsius * 9.0 / 5.0 + 32.0).roundToInt()

    private fun SkyCondition.displayName() = when (this) {
        SkyCondition.CLEAR -> "Clear skies"
        SkyCondition.PARTLY_CLOUDY -> "Partly cloudy"
        SkyCondition.CLOUDY -> "Cloudy"
        SkyCondition.RAIN -> "Showers"
        SkyCondition.STORM -> "Stormy"
    }
}
