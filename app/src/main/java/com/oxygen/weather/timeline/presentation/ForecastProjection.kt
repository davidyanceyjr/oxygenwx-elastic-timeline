package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.format.DateTimeFormatter
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
    val precipitationPercent: Int?,
    val windMph: Int,
    val lowF: Int,
    val highF: Int,
    val thunderstormProbabilityPercent: Int?,
    val warning: PresentedWarning,
    val provenanceLabel: String,
    val provenanceDetail: String,
    val sky: SkyFrame,
    val isPrecision: Boolean,
)

object ForecastProjection {
    fun fromHourOffset(
        offset: Double,
        precision: Boolean,
        source: ForecastSource = ForecastSource.sample,
    ): ForecastView {
        val clamped = offset.coerceIn(0.0, source.lastIndex.toDouble())
        val dayIndex = ElasticTimeAxis.dayIndex(clamped).coerceIn(0, source.days.lastIndex)
        val day = source.days[dayIndex]
        val selectedIndex = clamped.roundToInt().coerceIn(0, source.lastIndex)
        val selectedTime = source.hours[selectedIndex].time
        val factSample = source.hours[if (precision) selectedIndex else day.noonSampleIndex]
        val sky = if (precision) interpolate(clamped, source) else factSample.scene
        val dayLabel = day.date.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US))
        val hourLabel = selectedTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US))
            .replace(":00 ", " ")
        return ForecastView(
            heading = dayLabel.uppercase(Locale.US),
            timeLabel = if (precision) hourLabel else "DAY OVERVIEW · SELECTED $hourLabel",
            conditionsLabel = factSample.condition.label,
            temperatureF = toF(factSample.temperatureC),
            precipitationPercent = factSample.precipitationProbabilityPercent?.roundToInt(),
            windMph = (factSample.windKph / 1.609344).roundToInt(),
            lowF = toF(day.lowC),
            highF = toF(day.highC),
            thunderstormProbabilityPercent = factSample.thunderstormProbabilityPercent?.roundToInt(),
            warning = factSample.warning,
            provenanceLabel = factSample.provenance.label,
            provenanceDetail = factSample.provenance.detail,
            sky = sky,
            isPrecision = precision,
        )
    }

    /** Scene continuity only. Facts in [ForecastView] always come from an exact sample. */
    fun interpolate(hourOffset: Double, source: ForecastSource = ForecastSource.sample): SkyFrame {
        val t = hourOffset.coerceIn(0.0, source.lastIndex.toDouble())
        val left = floor(t).toInt()
        val right = (left + 1).coerceAtMost(source.lastIndex)
        val weight = t - left
        val a = source.hours[left].scene
        val b = source.hours[right].scene
        val categorical = if (weight < 0.5) a else b
        fun mix(x: Double, y: Double) = x + (y - x) * weight
        return SkyFrame(
            condition = categorical.condition,
            baseSkyCondition = categorical.baseSkyCondition,
            precipitationType = categorical.precipitationType,
            precipitationAmount = categorical.precipitationAmount,
            temperatureC = mix(a.temperatureC, b.temperatureC),
            cloudPercent = mix(a.cloudPercent, b.cloudPercent),
            precipitationChancePercent = mix(a.precipitationChancePercent, b.precipitationChancePercent),
            windKph = mix(a.windKph, b.windKph),
        )
    }

    private fun toF(celsius: Double) = (celsius * 9.0 / 5.0 + 32.0).roundToInt()

}
