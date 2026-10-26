package com.oxygen.weather.timeline.presentation.atmosphere

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.PrecipitationType
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.SkyFrame
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SceneVisualStateMapperTest {
    @Test
    fun previewProfileMatchesSamplesInterpolatesAndClampsEndpoints() {
        val expected = listOf(
            PreviewEffectStrengths(.10, .20),
            PreviewEffectStrengths(.25, .70),
            PreviewEffectStrengths(.55, .35),
            PreviewEffectStrengths(.85, .80),
            PreviewEffectStrengths(.45, .45),
            PreviewEffectStrengths(.15, .20),
        )
        expected.forEachIndexed { index, strength ->
            assertEquals(strength, AtmospherePreviewStrengthProfile.at(index * 24.0))
        }
        val firstMidpoint = AtmospherePreviewStrengthProfile.at(12.0)
        assertEquals(.175, firstMidpoint.haze, 1e-12)
        assertEquals(.45, firstMidpoint.turbulence, 1e-12)
        val secondMidpoint = AtmospherePreviewStrengthProfile.at(36.0)
        assertEquals(.40, secondMidpoint.haze, 1e-12)
        assertEquals(.525, secondMidpoint.turbulence, 1e-12)
        assertEquals(expected.first(), AtmospherePreviewStrengthProfile.at(-24.0))
        assertEquals(expected.last(), AtmospherePreviewStrengthProfile.at(144.0))
    }

    @Test
    fun previewModesGateIndependentEffectChannelsAndDefaultToOff() {
        val frame = frame()
        fun strengths(preview: AtmospherePreview) = SceneVisualStateMapper.map(
            AtmosphereInput(
                sky = frame,
                precisionMode = false,
                localHour = 12.0,
                preview = preview,
                selectedHour = 24.0,
            )
        ).previewStrengths

        assertEquals(PreviewEffectStrengths(0.0, 0.0), strengths(AtmospherePreview.NONE))
        assertEquals(PreviewEffectStrengths(.25, 0.0), strengths(AtmospherePreview.ILLUSTRATIVE_HAZE))
        assertEquals(PreviewEffectStrengths(0.0, .70), strengths(AtmospherePreview.ILLUSTRATIVE_TURBULENCE))
        assertEquals(PreviewEffectStrengths(.25, .70), strengths(AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE))
    }

    @Test
    fun hazeAndTurbulenceRequireExplicitPreviewSelectionAndPreserveForecastValues() {
        val frame = frame(condition = SkyCondition.RAIN).copy(
            precipitationType = PrecipitationType.RAIN,
            precipitationAmount = PrecipitationAmount.RainLiquidMm(2.5),
        )
        assertEquals(5.0, frame.windKph, 0.0)
        val noPreview = SceneVisualStateMapper.map(
            AtmosphereInput(sky = frame, precisionMode = false, localHour = 12.0)
        )
        assertEquals(AtmospherePreview.NONE, noPreview.preview)
        assertEquals(PreviewEffectStrengths(0.0, 0.0), noPreview.previewStrengths)
        assertEquals(DepictionKind.ILLUSTRATIVE_RAIN, noPreview.depiction)
        assertEquals(PrecipitationAmount.RainLiquidMm(2.5), noPreview.precipitationAmount)

        val previews = listOf(
            AtmospherePreview.ILLUSTRATIVE_HAZE,
            AtmospherePreview.ILLUSTRATIVE_TURBULENCE,
            AtmospherePreview.ILLUSTRATIVE_HAZE_AND_TURBULENCE,
        )
        previews.forEach { preview ->
            val state = SceneVisualStateMapper.map(
                AtmosphereInput(
                    sky = frame,
                    precisionMode = false,
                    localHour = 12.0,
                    preview = preview,
                    selectedHour = 36.0,
                )
            )
            assertEquals(preview, state.preview)
            assertEquals(PrecipitationKind.RAIN, state.precipitation)
            assertEquals(DepictionKind.ILLUSTRATIVE_RAIN, state.depiction)
            assertEquals(PrecipitationAmount.RainLiquidMm(2.5), state.precipitationAmount)
        }
    }

    @Test
    fun mapsEveryCurrentConditionWithoutCollapsingStormIntoRain() {
        val expected = mapOf(
            SkyCondition.CLEAR to (PrecipitationKind.NONE to DepictionKind.NONE),
            SkyCondition.PARTLY_CLOUDY to (PrecipitationKind.NONE to DepictionKind.NONE),
            SkyCondition.CLOUDY to (PrecipitationKind.NONE to DepictionKind.NONE),
            SkyCondition.RAIN to (PrecipitationKind.RAIN to DepictionKind.ILLUSTRATIVE_RAIN),
            SkyCondition.STORM to (PrecipitationKind.UNKNOWN to DepictionKind.STORM),
        )

        expected.forEach { (condition, mapping) ->
            val state = SceneVisualStateMapper.map(input(condition = condition))
            assertEquals(condition, state.condition)
            assertEquals(mapping.first, state.precipitation)
            assertEquals(mapping.second, state.depiction)
        }
    }

    @Test
    fun rainProbabilityDoesNotAffectTypedPrecipitationOrDepiction() {
        val states = listOf(0.0, 100.0, Double.NaN, Double.POSITIVE_INFINITY).map { chance ->
            SceneVisualStateMapper.map(input(condition = SkyCondition.RAIN, rainChance = chance))
        }
        states.forEach {
            assertEquals(PrecipitationKind.RAIN, it.precipitation)
            assertEquals(DepictionKind.ILLUSTRATIVE_RAIN, it.depiction)
        }
        assertEquals(1, states.map { it.precipitation to it.depiction }.distinct().size)

        val storm = SceneVisualStateMapper.map(input(condition = SkyCondition.STORM, rainChance = 100.0))
        assertEquals(PrecipitationKind.UNKNOWN, storm.precipitation)
        assertEquals(DepictionKind.STORM, storm.depiction)
    }

    @Test
    fun illustrativeStormPreviewChangesOnlySceneDepiction() {
        val frame = frame(condition = SkyCondition.RAIN, cloudPercent = 82.0, rainChance = 67.0).copy(
            precipitationType = PrecipitationType.RAIN,
            precipitationAmount = PrecipitationAmount.RainLiquidMm(1.4),
        )
        val input = AtmosphereInput(
            sky = frame,
            precisionMode = true,
            localHour = 0.0,
            preview = AtmospherePreview.ILLUSTRATIVE_STORM,
            selectedHour = 24.5,
        )

        val state = SceneVisualStateMapper.map(input)

        assertEquals(DepictionKind.STORM, state.depiction)
        assertEquals(SkyCondition.RAIN, state.condition)
        assertEquals(PrecipitationKind.RAIN, state.precipitation)
        assertEquals(PrecipitationAmount.RainLiquidMm(1.4), state.precipitationAmount)
        assertEquals(67.0, state.precipitationChancePercent, 0.0)
        assertEquals(.82, state.cloudFraction!!, 0.0)
        assertEquals(0.0, state.daylight!!, 0.0)
        assertEquals(frame, input.sky)
        assertEquals(24.5, input.selectedHour, 0.0)
    }

    @Test
    fun illustrativeClearNightPreviewChangesOnlySceneCloudAndEffectDepiction() {
        val frame = frame(condition = SkyCondition.CLOUDY, cloudPercent = 91.0, rainChance = 24.0).copy(
            precipitationType = PrecipitationType.RAIN,
            precipitationAmount = PrecipitationAmount.RainLiquidMm(0.6),
        )
        val input = AtmosphereInput(
            sky = frame,
            precisionMode = true,
            localHour = 0.0,
            preview = AtmospherePreview.ILLUSTRATIVE_CLEAR_NIGHT,
            selectedHour = 48.0,
        )

        val state = SceneVisualStateMapper.map(input)

        assertEquals(DepictionKind.NONE, state.depiction)
        assertEquals(0.0, state.cloudFraction!!, 0.0)
        assertEquals(0.0, state.daylight!!, 0.0)
        assertEquals(SkyCondition.CLOUDY, state.condition)
        assertEquals(PrecipitationKind.RAIN, state.precipitation)
        assertEquals(24.0, state.precipitationChancePercent, 0.0)
        assertEquals(PrecipitationAmount.RainLiquidMm(0.6), state.precipitationAmount)
        assertEquals(frame, input.sky)
        assertEquals(48.0, input.selectedHour, 0.0)
    }

    @Test
    fun typedStormConditionCanCoexistWithTypedRainPrecipitation() {
        val frame = frame(condition = SkyCondition.STORM, cloudPercent = 91.0, rainChance = 76.0).copy(
            precipitationType = PrecipitationType.RAIN,
            precipitationAmount = PrecipitationAmount.RainLiquidMm(2.8),
        )

        val state = SceneVisualStateMapper.map(
            AtmosphereInput(sky = frame, precisionMode = true, localHour = 0.0)
        )

        assertEquals(SkyCondition.STORM, state.condition)
        assertEquals(PrecipitationKind.RAIN, state.precipitation)
        assertEquals(DepictionKind.ILLUSTRATIVE_RAIN, state.depiction)
        assertEquals(76.0, state.precipitationChancePercent, 0.0)
        assertEquals(PrecipitationAmount.RainLiquidMm(2.8), state.precipitationAmount)
    }

    @Test
    fun normalizesFiniteCloudAndDaylightValuesAndPreservesMissingness() {
        assertEquals(0.0, SceneVisualStateMapper.map(input(cloudPercent = -30.0)).cloudFraction!!, 0.0)
        assertEquals(1.0, SceneVisualStateMapper.map(input(cloudPercent = 130.0)).cloudFraction!!, 0.0)
        assertEquals(.75, SceneVisualStateMapper.map(input(cloudPercent = 75.0)).cloudFraction!!, 0.0)
        listOf(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY).forEach {
            assertNull(SceneVisualStateMapper.map(input(cloudPercent = it)).cloudFraction)
            assertNull(SceneVisualStateMapper.map(input(localHour = it)).daylight)
        }

        assertEquals(0.0, SceneVisualStateMapper.map(input(localHour = 6.0)).daylight!!, 1e-12)
        assertEquals(1.0, SceneVisualStateMapper.map(input(localHour = 12.0)).daylight!!, 1e-12)
    }

    @Test
    fun stormAndNonStormNightKeepTypedIdentityAndExistingDaylightCue() {
        val stormFrame = frame(SkyCondition.STORM, cloudPercent = 85.0, rainChance = 100.0)
        val stormInput = AtmosphereInput(
            sky = stormFrame,
            precisionMode = true,
            localHour = 0.0,
            selectedHour = 48.5,
        )
        val storm = SceneVisualStateMapper.map(stormInput)
        assertEquals(SkyCondition.STORM, storm.condition)
        assertEquals(DepictionKind.STORM, storm.depiction)
        assertEquals(PrecipitationKind.UNKNOWN, storm.precipitation)
        assertEquals(85.0, storm.cloudFraction!! * 100.0, 0.0)
        assertEquals(100.0, storm.precipitationChancePercent, 0.0)
        assertEquals(0.0, storm.daylight!!, 0.0)
        assertEquals(48.5, stormInput.selectedHour, 0.0)
        assertEquals(stormFrame, stormInput.sky)

        val cloudyNight = SceneVisualStateMapper.map(input(SkyCondition.CLOUDY, localHour = 0.0))
        assertEquals(SkyCondition.CLOUDY, cloudyNight.condition)
        assertEquals(DepictionKind.NONE, cloudyNight.depiction)
        assertEquals(0.0, cloudyNight.daylight!!, 0.0)
    }

    @Test
    fun daytimeStormRetainsStormIdentityWithoutInferringPrecipitation() {
        val state = SceneVisualStateMapper.map(input(SkyCondition.STORM, localHour = 12.0))
        assertEquals(DepictionKind.STORM, state.depiction)
        assertEquals(PrecipitationKind.UNKNOWN, state.precipitation)
        assertEquals(1.0, state.daylight!!, 0.0)
    }

    @Test
    fun reducedMotionKeepsStormMeaningAndMissingScenicInputsMissing() {
        val reduced = SceneVisualStateMapper.map(
            AtmosphereInput(
                sky = frame(SkyCondition.STORM, cloudPercent = Double.NaN),
                precisionMode = false,
                localHour = Double.NaN,
                effects = EffectsLevel.FULL,
                reducedMotion = true,
            )
        )
        assertEquals(EffectsLevel.NONE, reduced.effects)
        assertEquals(DepictionKind.STORM, reduced.depiction)
        assertNull(reduced.cloudFraction)
        assertNull(reduced.daylight)
    }

    @Test
    fun defaultsToNeutralStaticSceneAndReducedMotionForcesNone() {
        val defaults = SceneVisualStateMapper.map(input())
        assertEquals(EnvironmentId.NEUTRAL, defaults.environment)
        assertEquals(EffectsLevel.NONE, defaults.effects)

        val reduced = SceneVisualStateMapper.map(
            AtmosphereInput(
                sky = frame(),
                precisionMode = true,
                localHour = 12.0,
                effects = EffectsLevel.FULL,
                reducedMotion = true,
            )
        )
        assertEquals(EffectsLevel.NONE, reduced.effects)
        assertEquals(true, reduced.precisionMode)
    }

    @Test
    fun mappingLeavesAuthoritativeInputTimeUntouched() {
        val selectedHour = 37.375
        val input = AtmosphereInput(
            frame(), precisionMode = true, localHour = selectedHour, selectedHour = selectedHour,
        )
        SceneVisualStateMapper.map(input)
        assertEquals(selectedHour, input.localHour, 0.0)
        assertEquals(SkyCondition.CLEAR, input.sky.condition)
    }

    private fun input(
        condition: SkyCondition = SkyCondition.CLEAR,
        cloudPercent: Double = 50.0,
        rainChance: Double = 0.0,
        localHour: Double = 12.0,
    ) = AtmosphereInput(
        sky = frame(condition, cloudPercent, rainChance),
        precisionMode = false,
        localHour = localHour,
    )

    private fun frame(
        condition: SkyCondition = SkyCondition.CLEAR,
        cloudPercent: Double = 50.0,
        rainChance: Double = 0.0,
    ) = SkyFrame(
        condition = condition,
        baseSkyCondition = condition,
        precipitationType = if (condition == SkyCondition.RAIN) PrecipitationType.RAIN else PrecipitationType.NONE,
        precipitationAmount = null,
        temperatureC = 20.0,
        cloudPercent = cloudPercent,
        precipitationChancePercent = rainChance,
        windKph = 5.0,
    )
}
