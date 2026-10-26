package com.oxygen.weather.timeline.presentation.atmosphere

import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.presentation.SkyFrame
import kotlin.math.PI
import kotlin.math.floor
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

enum class AtmospherePreview {
    NONE,
    SYNTHETIC_SNOW,
    ILLUSTRATIVE_HAZE,
    ILLUSTRATIVE_TURBULENCE,
    ILLUSTRATIVE_HAZE_AND_TURBULENCE,
}

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
    /** Explicit developer preview selection; never derived from forecast values. */
    val preview: AtmospherePreview,
    /** Renderer-only preview strengths; forecast values and selected time remain authoritative. */
    val previewStrengths: PreviewEffectStrengths,
)

data class PreviewEffectStrengths(val haze: Double, val turbulence: Double)

/** Deterministic synthetic profiles used only by explicit debug preview modes. */
object AtmospherePreviewStrengthProfile {
    private data class Sample(val hour: Double, val haze: Double, val turbulence: Double)

    private val samples = listOf(
        Sample(0.0, .10, .20),
        Sample(24.0, .25, .70),
        Sample(48.0, .55, .35),
        Sample(72.0, .85, .80),
        Sample(96.0, .45, .45),
        Sample(120.0, .15, .20),
    )

    fun at(selectedHour: Double): PreviewEffectStrengths {
        val hour = selectedHour.takeIf(Double::isFinite) ?: samples.first().hour
        if (hour <= samples.first().hour) return samples.first().strengths()
        if (hour >= samples.last().hour) return samples.last().strengths()
        val leftIndex = floor(hour / 24.0).toInt().coerceIn(0, samples.lastIndex - 1)
        val left = samples[leftIndex]
        val right = samples[leftIndex + 1]
        val fraction = (hour - left.hour) / (right.hour - left.hour)
        return PreviewEffectStrengths(
            haze = left.haze + (right.haze - left.haze) * fraction,
            turbulence = left.turbulence + (right.turbulence - left.turbulence) * fraction,
        )
    }

    private fun Sample.strengths() = PreviewEffectStrengths(haze, turbulence)
}

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
            AtmospherePreview.NONE,
            AtmospherePreview.ILLUSTRATIVE_HAZE,
            AtmospherePreview.ILLUSTRATIVE_TURBULENCE,
            AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE -> forecastPrecipitation to forecastDepiction
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
        val profile = AtmospherePreviewStrengthProfile.at(input.selectedHour)
        val previewStrengths = when (input.preview) {
            AtmospherePreview.ILLUSTRATIVE_HAZE -> PreviewEffectStrengths(profile.haze, 0.0)
            AtmospherePreview.ILLUSTRATIVE_TURBULENCE -> PreviewEffectStrengths(0.0, profile.turbulence)
            AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE -> profile
            AtmospherePreview.NONE, AtmospherePreview.SYNTHETIC_SNOW -> PreviewEffectStrengths(0.0, 0.0)
        }

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
            preview = input.preview,
            previewStrengths = previewStrengths,
        )
    }
}
