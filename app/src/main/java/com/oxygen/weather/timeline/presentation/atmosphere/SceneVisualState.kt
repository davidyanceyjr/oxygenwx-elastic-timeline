package com.oxygen.weather.timeline.presentation.atmosphere

import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.presentation.SkyFrame
import kotlin.math.PI
import kotlin.math.sin

/** Explicit scenic context. Forecast time remains owned by the screen. */
data class AtmosphereInput(
    val sky: SkyFrame,
    val precisionMode: Boolean,
    val localHour: Double,
    val environment: EnvironmentId = EnvironmentId.NEUTRAL,
    val effects: EffectsLevel = EffectsLevel.NONE,
    val reducedMotion: Boolean = false,
    /** Explicit renderer preview only; never inferred from forecast fields. */
    val preview: AtmospherePreview = AtmospherePreview.NONE,
    /** Exact selected fixture-hour offset; decorative phase context only. */
    val selectedHour: Double = 0.0,
)

enum class EnvironmentId { NEUTRAL }

enum class EffectsLevel { NONE, SUBTLE, FULL }

enum class PrecipitationKind { NONE, RAIN, SYNTHETIC_SNOW, UNKNOWN }

enum class DepictionKind { NONE, ILLUSTRATIVE_RAIN, ILLUSTRATIVE_SNOW, SYNTHETIC_SNOW_PREVIEW, STORM, UNKNOWN }

enum class AtmospherePreview { NONE, SYNTHETIC_SNOW }

/** Immutable, renderer-independent description of the selected atmosphere. */
data class SceneVisualState(
    val condition: SkyCondition,
    val precipitation: PrecipitationKind,
    val depiction: DepictionKind,
    /** Forecast chance remains separate from precipitation identity and amount. */
    val precipitationChancePercent: Double,
    /** Explicit interval amount when supplied; null remains unknown. */
    val precipitationAmount: PrecipitationAmount?,
    /** Cloud cover in the inclusive range [0, 1], or null when the source is invalid. */
    val cloudFraction: Double?,
    /** Existing illustrative daylight cue in the inclusive range [0, 1], or null when invalid. */
    val daylight: Double?,
    val precisionMode: Boolean,
    val environment: EnvironmentId,
    val effects: EffectsLevel,
)

/** Pure projection of typed forecast inputs into scenic state. */
object SceneVisualStateMapper {
    fun map(input: AtmosphereInput): SceneVisualState {
        val (forecastPrecipitation, forecastDepiction) = when (input.sky.precipitationType) {
            PrecipitationType.NONE -> when (input.sky.condition) {
                SkyCondition.STORM -> PrecipitationKind.UNKNOWN to DepictionKind.STORM
                else -> PrecipitationKind.NONE to DepictionKind.NONE
            }
            PrecipitationType.RAIN -> PrecipitationKind.RAIN to DepictionKind.ILLUSTRATIVE_RAIN
            PrecipitationType.SNOW -> PrecipitationKind.SYNTHETIC_SNOW to DepictionKind.ILLUSTRATIVE_SNOW
        }
        val (precipitation, depiction) = when (input.preview) {
            AtmospherePreview.NONE -> forecastPrecipitation to forecastDepiction
            AtmospherePreview.SYNTHETIC_SNOW ->
                PrecipitationKind.SYNTHETIC_SNOW to DepictionKind.SYNTHETIC_SNOW_PREVIEW
        }
        val cloudFraction = input.sky.cloudPercent
            .takeIf(Double::isFinite)
            ?.coerceIn(0.0, 100.0)
            ?.div(100.0)
        val daylight = input.localHour
            .takeIf(Double::isFinite)
            ?.let { sin(((it - 6.0) / 12.0) * PI).coerceIn(0.0, 1.0) }

        return SceneVisualState(
            condition = input.sky.condition,
            precipitation = precipitation,
            depiction = depiction,
            precipitationChancePercent = input.sky.precipitationChancePercent
                .takeIf(Double::isFinite)?.coerceIn(0.0, 100.0) ?: 0.0,
            precipitationAmount = input.sky.precipitationAmount,
            cloudFraction = cloudFraction,
            daylight = daylight,
            precisionMode = input.precisionMode,
            environment = input.environment,
            effects = if (input.reducedMotion) EffectsLevel.NONE else input.effects,
        )
    }
}
