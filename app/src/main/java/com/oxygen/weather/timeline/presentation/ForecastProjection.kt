package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.ForecastDay
import com.oxygen.weather.timeline.data.ForecastHour
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.floor
import kotlin.math.roundToInt

/** Presentation-only view. All underlying fixture weather units remain metric. */
data class SkyFrame(
    /** Legacy scene-renderer input retained until the renderer consumes typed effects. */
    val condition: SkyCondition,
    val baseSkyCondition: SkyCondition,
    val precipitationType: PrecipitationType,
    val precipitationAmount: PrecipitationAmount?,
    val temperatureC: Double,
    val cloudPercent: Double,
    val precipitationChancePercent: Double,
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
        val selectedTime = SampleForecast.hours.first().time
            .plus((clamped * 60.0).roundToInt().toLong(), ChronoUnit.MINUTES)
        val dayLabel = day.date.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US))
        val hourLabel = selectedTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US))
            .replace(":00 ", " ")
        return ForecastView(
            heading = dayLabel.uppercase(Locale.US),
            timeLabel = if (precision) hourLabel else "DAY OVERVIEW · SELECTED $hourLabel",
            conditionsLabel = when (sky.precipitationType) {
                PrecipitationType.RAIN -> "Showers"
                PrecipitationType.SNOW -> "Snow"
                PrecipitationType.NONE -> sky.baseSkyCondition.displayName()
            },
            temperatureF = toF(sky.temperatureC),
            precipitationPercent = sky.precipitationChancePercent.roundToInt(),
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
        val categorical = if (weight < 0.5) a else b
        fun mix(x: Double, y: Double) = x + (y - x) * weight
        return SkyFrame(
            condition = categorical.condition,
            baseSkyCondition = categorical.baseSkyCondition,
            precipitationType = categorical.precipitationType,
            precipitationAmount = categorical.precipitationAmount,
            temperatureC = mix(a.temperatureC, b.temperatureC),
            cloudPercent = mix(a.cloudPercent, b.cloudPercent),
            precipitationChancePercent = mix(a.precipitationProbabilityPercent, b.precipitationProbabilityPercent),
            windKph = mix(a.windKph, b.windKph),
        )
    }

    private fun ForecastHour.toFrame() = SkyFrame(
        condition = condition,
        baseSkyCondition = baseSkyCondition,
        precipitationType = precipitationType,
        precipitationAmount = precipitationAmount,
        temperatureC = temperatureC,
        cloudPercent = cloudPercent,
        precipitationChancePercent = precipitationProbabilityPercent,
        windKph = windKph,
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
