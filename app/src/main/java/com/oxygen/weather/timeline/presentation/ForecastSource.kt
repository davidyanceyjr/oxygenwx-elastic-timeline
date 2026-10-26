package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.ForecastHour
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.LocalDate
import java.time.LocalDateTime

/** Neutral presentation vocabulary; it does not extend the app's canonical weather models. */
enum class PresentedCondition(val label: String) {
    CLEAR("Clear skies"), PARTLY_CLOUDY("Partly cloudy"), CLOUDY("Cloudy"),
    RAIN("Showers"), SNOW("Snow"), STORM("Stormy"),
}

enum class PresentedWarning { NOT_SUPPLIED, NONE, PRESENT }
data class ForecastProvenance(val label: String, val detail: String = "") {
    companion object { val DEMO_DATA = ForecastProvenance("DEMO DATA") }
}

/** A complete source sample. [scene] is a renderer adapter; facts remain in neutral typed fields. */
data class ForecastSourceHour(
    val time: LocalDateTime,
    val temperatureC: Double,
    val cloudPercent: Double,
    val precipitationProbabilityPercent: Double?,
    val windKph: Double,
    val condition: PresentedCondition,
    val precipitationType: PrecipitationType,
    val precipitationAmount: PrecipitationAmount?,
    val thunderstormProbabilityPercent: Double? = null,
    val warning: PresentedWarning = PresentedWarning.NOT_SUPPLIED,
    val provenance: ForecastProvenance,
    val scene: SkyFrame,
    val windDirectionDegrees: Double? = null,
) {
    init {
        require(temperatureC.isFinite() && cloudPercent.isFinite() && windKph.isFinite())
        require(precipitationProbabilityPercent == null || precipitationProbabilityPercent in 0.0..100.0)
        require(thunderstormProbabilityPercent == null || thunderstormProbabilityPercent in 0.0..100.0)
        require(windDirectionDegrees == null ||
            (windDirectionDegrees.isFinite() && windDirectionDegrees >= 0.0 && windDirectionDegrees < 360.0)
        )
    }
}

data class ForecastSourceDay(
    val date: LocalDate,
    val noonSampleIndex: Int,
    val lowC: Double,
    val highC: Double,
)

/** Shared presentation input for the normal fixture and isolated debug forecast profiles. */
data class ForecastSource(
    val id: String,
    val hours: List<ForecastSourceHour>,
    val referenceTime: LocalDateTime,
    val resetToReference: Boolean = false,
) {
    init {
        require(id.isNotBlank() && hours.isNotEmpty())
        require(hours.first().time.hour == 0 && hours.first().time.minute == 0)
        require(hours.zipWithNext().all { (a, b) -> b.time == a.time.plusHours(1) })
        require(hours.any { it.time == referenceTime })
    }

    val days: List<ForecastSourceDay> = hours.indices
        .groupBy { hours[it].time.toLocalDate() }
        .map { (date, indexes) ->
            require(indexes.size == 24) { "Forecast source must contain complete local days" }
            val noon = indexes.single { hours[it].time.hour == 12 }
            val temperatures = indexes.map { hours[it].temperatureC }
            ForecastSourceDay(date, noon, temperatures.min(), temperatures.max())
        }

    val lastIndex: Int get() = hours.lastIndex
    val referenceIndex: Int get() = hours.indexOfFirst { it.time == referenceTime }

    companion object {
        val sample: ForecastSource by lazy {
            val samples = SampleForecast.hours.map { it.toSourceHour() }
            ForecastSource("sample", samples, samples[36].time)
        }
    }
}

private fun ForecastHour.toSourceHour() = ForecastSourceHour(
    time = time,
    temperatureC = temperatureC,
    cloudPercent = cloudPercent,
    precipitationProbabilityPercent = precipitationProbabilityPercent,
    windKph = windKph,
    condition = when (condition) {
        SkyCondition.CLEAR -> PresentedCondition.CLEAR
        SkyCondition.PARTLY_CLOUDY -> PresentedCondition.PARTLY_CLOUDY
        SkyCondition.CLOUDY -> PresentedCondition.CLOUDY
        SkyCondition.RAIN -> PresentedCondition.RAIN
        SkyCondition.STORM -> PresentedCondition.STORM
    },
    precipitationType = precipitationType,
    precipitationAmount = precipitationAmount,
    windDirectionDegrees = windDirectionDegrees,
    provenance = ForecastProvenance.DEMO_DATA,
    scene = SkyFrame(
        condition = condition,
        baseSkyCondition = baseSkyCondition,
        precipitationType = precipitationType,
        precipitationAmount = precipitationAmount,
        temperatureC = temperatureC,
        cloudPercent = cloudPercent,
        precipitationChancePercent = precipitationProbabilityPercent,
        windKph = windKph,
    ),
)
