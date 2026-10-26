package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.ForecastProvenance
import com.oxygen.weather.timeline.presentation.ForecastSource
import com.oxygen.weather.timeline.presentation.ForecastSourceHour
import com.oxygen.weather.timeline.presentation.PresentedCondition
import com.oxygen.weather.timeline.presentation.PresentedWarning
import com.oxygen.weather.timeline.presentation.SkyFrame
import java.time.LocalDateTime
import kotlin.math.sin

enum class DebugProfileId(val loadedDays: Int, val eventDays: Int, val label: String) {
    RAIN_3_DAY(7, 3, "Short rain event"),
    STORM_7_DAY(11, 7, "Clear to storm progression"),
    COLD_SNOW_10_DAY(14, 10, "Extended cold, cloud, wind and snow"),
}

private fun DebugCondition.toPresentedCondition() = when (this) {
    DebugCondition.CLEAR -> PresentedCondition.CLEAR
    DebugCondition.PARTLY_CLOUDY -> PresentedCondition.PARTLY_CLOUDY
    DebugCondition.CLOUDY -> PresentedCondition.CLOUDY
    DebugCondition.RAIN -> PresentedCondition.RAIN
    DebugCondition.SNOW -> PresentedCondition.SNOW
    DebugCondition.STORM -> PresentedCondition.STORM
}

/** Complete immutable sample. Missing probability/amount values stay missing at each valid hour. */
data class DebugProfileSample(
    val validTime: LocalDateTime,
    val temperatureC: Double,
    val cloudPercent: Double,
    val precipitationProbabilityPercent: Double?,
    val precipitationType: DebugPrecipitationType,
    val precipitationAmount: PrecipitationAmount?,
    val windKph: Double,
    val condition: DebugCondition,
    val thunderstormProbabilityPercent: Double?,
    val warningState: DebugWarningState,
    val provenance: DebugProvenance = DebugProvenance.SYNTHETIC_DEBUG,
)

data class DebugForecastProfile(
    val id: DebugProfileId,
    val samples: List<DebugProfileSample>,
    val referenceTime: LocalDateTime,
) {
    init {
        require(samples.size == id.loadedDays * 24)
        require(samples.first().validTime.hour == 0)
        require(samples.zipWithNext().all { (a, b) -> b.validTime == a.validTime.plusHours(1) })
        require(samples.count { it.validTime == referenceTime } == 1)
        require(samples.take(48).all(::isCalmClearBuffer))
        require(samples.takeLast(48).all(::isCalmClearBuffer))
    }

    val eventStartIndex: Int get() = 48
    val eventEndExclusive: Int get() = (2 + id.eventDays) * 24

    fun toForecastSource(): ForecastSource = ForecastSource(
        id = "debug-${id.name.lowercase()}",
        hours = samples.map { sample ->
            val scenicCondition = when (sample.condition) {
                DebugCondition.CLEAR -> SkyCondition.CLEAR
                DebugCondition.PARTLY_CLOUDY -> SkyCondition.PARTLY_CLOUDY
                DebugCondition.CLOUDY, DebugCondition.SNOW -> SkyCondition.CLOUDY
                DebugCondition.RAIN -> SkyCondition.RAIN
                DebugCondition.STORM -> SkyCondition.STORM
            }
            val precipitationType = when (sample.precipitationType) {
                DebugPrecipitationType.NONE -> PrecipitationType.NONE
                DebugPrecipitationType.RAIN -> PrecipitationType.RAIN
                DebugPrecipitationType.SNOW -> PrecipitationType.SNOW
            }
            val baseCondition = when {
                sample.cloudPercent >= 76 -> SkyCondition.CLOUDY
                sample.cloudPercent >= 29 -> SkyCondition.PARTLY_CLOUDY
                else -> SkyCondition.CLEAR
            }
            ForecastSourceHour(
                time = sample.validTime,
                temperatureC = sample.temperatureC,
                cloudPercent = sample.cloudPercent,
                precipitationProbabilityPercent = sample.precipitationProbabilityPercent,
                windKph = sample.windKph,
                condition = sample.condition.toPresentedCondition(),
                precipitationType = precipitationType,
                precipitationAmount = sample.precipitationAmount,
                thunderstormProbabilityPercent = sample.thunderstormProbabilityPercent,
                warning = when (sample.warningState) {
                    DebugWarningState.NONE -> PresentedWarning.NONE
                    DebugWarningState.PRESENT -> PresentedWarning.PRESENT
                },
                provenance = ForecastProvenance("DEMO DATA", "SYNTHETIC DEBUG PROFILE"),
                scene = SkyFrame(
                    condition = scenicCondition,
                    baseSkyCondition = baseCondition,
                    precipitationType = precipitationType,
                    precipitationAmount = sample.precipitationAmount,
                    temperatureC = sample.temperatureC,
                    cloudPercent = sample.cloudPercent,
                    // The renderer consumes this only as a decorative cue; unknown remains null above.
                    precipitationChancePercent = sample.precipitationProbabilityPercent ?: 0.0,
                    windKph = sample.windKph,
                ),
            )
        },
        referenceTime = referenceTime,
        resetToReference = true,
    )

    private fun isCalmClearBuffer(sample: DebugProfileSample) =
        sample.condition == DebugCondition.CLEAR &&
            sample.precipitationType == DebugPrecipitationType.NONE &&
            sample.precipitationProbabilityPercent == 0.0 && sample.windKph <= 8.0
}

/** Explicit formulas produce every hourly value directly; no value is carried from another hour. */
object DebugForecastProfiles {
    private val profileStart = LocalDateTime.parse("2026-10-01T00:00")

    val all: List<DebugForecastProfile> = DebugProfileId.entries.map(::build)

    fun get(id: DebugProfileId): DebugForecastProfile = all.single { it.id == id }

    private fun build(id: DebugProfileId): DebugForecastProfile {
        val samples = (0 until id.loadedDays * 24).map { index ->
            val day = index / 24
            val hour = index % 24
            val eventDay = day - 2
            val (condition, clouds, chance, type, amount, baseTemp, wind, thunder, warning) =
                profileFacts(id, eventDay, hour)
            val daylight = sin(((hour - 7.0) / 24.0) * Math.PI)
            DebugProfileSample(
                validTime = profileStart.plusHours(index.toLong()),
                temperatureC = baseTemp + daylight * if (id == DebugProfileId.COLD_SNOW_10_DAY) 2.2 else 5.0,
                cloudPercent = clouds,
                precipitationProbabilityPercent = chance,
                precipitationType = type,
                precipitationAmount = amount,
                windKph = wind,
                condition = condition,
                thunderstormProbabilityPercent = thunder,
                warningState = warning,
            )
        }
        val reference = samples[(2 * 24) + 12].validTime
        return DebugForecastProfile(id, samples, reference)
    }

    private data class Facts(
        val condition: DebugCondition,
        val clouds: Double,
        val chance: Double?,
        val type: DebugPrecipitationType,
        val amount: PrecipitationAmount?,
        val baseTemp: Double,
        val wind: Double,
        val thunder: Double?,
        val warning: DebugWarningState,
    )

    private fun profileFacts(id: DebugProfileId, day: Int, hour: Int): Facts {
        if (day < 0 || day >= id.eventDays) return Facts(
            DebugCondition.CLEAR, 8.0, 0.0, DebugPrecipitationType.NONE, null,
            16.0, 5.0 + hour % 3, null, DebugWarningState.NONE,
        )
        return when (id) {
            DebugProfileId.RAIN_3_DAY -> when {
                day == 0 -> Facts(DebugCondition.CLOUDY, 72.0, 20.0, DebugPrecipitationType.NONE, null, 13.0, 12.0, null, DebugWarningState.NONE)
                day == 1 && hour in 7..19 -> Facts(
                    DebugCondition.RAIN, 88.0, 60.0 + (hour % 4), DebugPrecipitationType.RAIN,
                    if (hour in 10..12) PrecipitationAmount.RainLiquidMm(0.4 + (hour - 10) * 0.5) else null,
                    11.0, 18.0, null, DebugWarningState.NONE,
                )
                day == 1 -> Facts(DebugCondition.CLOUDY, 76.0, 30.0, DebugPrecipitationType.NONE, null, 12.0, 15.0, null, DebugWarningState.NONE)
                else -> Facts(DebugCondition.PARTLY_CLOUDY, 42.0, 10.0, DebugPrecipitationType.NONE, null, 14.0, 9.0, null, DebugWarningState.NONE)
            }
            DebugProfileId.STORM_7_DAY -> when (day) {
                0 -> Facts(DebugCondition.CLEAR, 18.0, 0.0, DebugPrecipitationType.NONE, null, 18.0, 7.0, null, DebugWarningState.NONE)
                1, 5 -> Facts(DebugCondition.PARTLY_CLOUDY, 46.0, 10.0, DebugPrecipitationType.NONE, null, 17.0, 11.0, null, DebugWarningState.NONE)
                2 -> Facts(DebugCondition.CLOUDY, 82.0, 25.0, DebugPrecipitationType.NONE, null, 15.0, 16.0, null, DebugWarningState.NONE)
                3 -> Facts(DebugCondition.STORM, 88.0, 20.0, DebugPrecipitationType.NONE, null, 14.0, 28.0, 27.0, DebugWarningState.NONE)
                4 -> Facts(
                    DebugCondition.STORM, 96.0, 70.0, DebugPrecipitationType.RAIN, null,
                    12.0, 36.0, 90.0,
                    if (hour in 12..15) DebugWarningState.PRESENT else DebugWarningState.NONE,
                )
                else -> Facts(DebugCondition.CLOUDY, 70.0, 35.0, DebugPrecipitationType.RAIN, null, 13.0, 20.0, null, DebugWarningState.NONE)
            }
            DebugProfileId.COLD_SNOW_10_DAY -> when {
                day <= 1 -> Facts(DebugCondition.CLOUDY, 78.0, 20.0, DebugPrecipitationType.NONE, null, -4.0, 32.0, null, DebugWarningState.NONE)
                day in 2..5 -> Facts(
                    DebugCondition.SNOW, 91.0, 75.0, DebugPrecipitationType.SNOW,
                    if (hour in 8..10) PrecipitationAmount.SnowDepthCm(0.4 + (hour - 8) * 0.3) else null,
                    -6.0, 29.0, null, DebugWarningState.NONE,
                )
                day in 6..7 -> Facts(DebugCondition.CLOUDY, 85.0, 30.0, DebugPrecipitationType.NONE, null, -8.0, 35.0, null, DebugWarningState.NONE)
                day == 8 -> Facts(DebugCondition.PARTLY_CLOUDY, 55.0, null, DebugPrecipitationType.NONE, null, -5.0, 22.0, null, DebugWarningState.NONE)
                else -> Facts(DebugCondition.CLEAR, 16.0, 0.0, DebugPrecipitationType.NONE, null, -2.0, 10.0, null, DebugWarningState.NONE)
            }
        }
    }

}
