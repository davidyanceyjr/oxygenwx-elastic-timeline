package com.oxygen.weather.timeline.presentation.atmosphere

import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.SkyFrame
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SceneVisualStateMapperTest {
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
        val input = AtmosphereInput(frame(), precisionMode = true, localHour = selectedHour)
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
    ) = SkyFrame(condition, 20.0, cloudPercent, rainChance, 5.0)
}
