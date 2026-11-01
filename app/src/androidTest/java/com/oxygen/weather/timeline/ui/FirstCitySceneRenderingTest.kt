package com.oxygen.weather.timeline.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.presentation.atmosphere.DepictionKind
import com.oxygen.weather.timeline.presentation.atmosphere.EffectsLevel
import com.oxygen.weather.timeline.presentation.atmosphere.EnvironmentId
import com.oxygen.weather.timeline.presentation.atmosphere.PrecipitationKind
import com.oxygen.weather.timeline.presentation.atmosphere.PreviewEffectStrengths
import com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualState
import com.oxygen.weather.timeline.presentation.scene.LightInterval
import com.oxygen.weather.timeline.presentation.scene.LightPhase
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import com.oxygen.weather.timeline.presentation.scene.SolarDayEvents
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FirstCitySceneRenderingTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()

    private val zone = ZoneId.of("America/Chicago")
    private val selected = ZonedDateTime.of(2026, 10, 28, 23, 0, 0, 0, zone).toInstant()
    private val reading = SceneReading(
        selectedInstant = selected,
        weather = emptyMap(),
        intervals = emptyMap(),
        lightInterval = LightInterval(
            selected.minus(Duration.ofHours(1)), selected.plus(Duration.ofHours(1)), LightPhase.NIGHT,
        ),
    )
    private val weather = SceneVisualState(
        condition = SkyCondition.PARTLY_CLOUDY,
        precipitation = PrecipitationKind.NONE,
        depiction = DepictionKind.NONE,
        precipitationChancePercent = 0.0,
        precipitationAmount = null,
        cloudFraction = 0.75,
        daylight = 0.0,
        precisionMode = false,
        environment = EnvironmentId.NEUTRAL,
        effects = EffectsLevel.NONE,
        preview = AtmospherePreview.NONE,
        previewStrengths = PreviewEffectStrengths(0.0, 0.0),
    )

    @Test fun nullWeatherKeepsCityButChangesWeatherRendering() {
        val state = mutableStateOf<SceneVisualState?>(null)
        compose.setContent {
            CatalogAtmosphericScene(
                reading, zone, state.value,
                modifier = Modifier.size(320.dp, 480.dp).testTag("scene"),
            )
        }
        val nullWeatherImage = compose.onNodeWithTag("scene").captureToImage()
        val withoutWeather = nullWeatherImage.toPixelMap()
        state.value = weather
        compose.waitForIdle()
        val weatherImage = compose.onNodeWithTag("scene").captureToImage()
        val withWeather = weatherImage.toPixelMap()

        var changedPixels = 0
        for (y in 0 until withoutWeather.height) for (x in 0 until withoutWeather.width) {
            if (withoutWeather[x, y] != withWeather[x, y]) changedPixels++
        }
        assertTrue("weather-present and null-weather renders should differ", changedPixels > 0)
        assertTrue(
            "the null-weather render should retain city pixels",
            withoutWeather[withoutWeather.width / 2, withoutWeather.height - 20] !=
                withoutWeather[withoutWeather.width / 2, 20],
        )
    }

    @Test fun missingOptionalNightLightsAreOmittedWithoutRemovingCity() {
        val omitLights = mutableStateOf(false)
        compose.setContent {
            CatalogAtmosphericScene(
                reading, zone, state = null,
                omitOptionalCityLayers = omitLights.value,
                modifier = Modifier.size(320.dp, 480.dp).testTag("scene"),
            )
        }
        val withLightsImage = compose.onNodeWithTag("scene").captureToImage()
        val withLights = withLightsImage.toPixelMap()
        omitLights.value = true
        compose.waitForIdle()
        val withoutLightsImage = compose.onNodeWithTag("scene").captureToImage()
        val withoutLights = withoutLightsImage.toPixelMap()

        var changedPixels = 0
        for (y in 0 until withLights.height) for (x in 0 until withLights.width) {
            if (withLights[x, y] != withoutLights[x, y]) changedPixels++
        }
        assertTrue("optional night lights should be omitted", changedPixels > 0)
    }

    @Test fun staticCompositionKeepsTheSelectedTimeCueStable() {
        compose.setContent {
            CatalogAtmosphericScene(
                reading, zone, state = null,
                modifier = Modifier.size(320.dp, 480.dp).testTag("scene"),
            )
        }
        val firstImage = compose.onNodeWithTag("scene").captureToImage()
        val firstFrame = firstImage.toPixelMap()
        compose.waitForIdle()
        val secondFrame = compose.onNodeWithTag("scene").captureToImage().toPixelMap()

        for (y in 0 until firstFrame.height) for (x in 0 until firstFrame.width) {
            assertTrue("static scene pixel changed at ($x, $y)", firstFrame[x, y] == secondFrame[x, y])
        }
    }

    @Test fun selectedLocalTimeProducesStableRainAndStormCloudPoses() {
        val selectedReading = mutableStateOf(reading)
        val selectedWeather = mutableStateOf(weather.copy(
            condition = com.oxygen.weather.timeline.data.SkyCondition.RAIN,
            precipitation = PrecipitationKind.RAIN,
            depiction = DepictionKind.ILLUSTRATIVE_RAIN,
            daylight = .7,
        ))
        compose.setContent {
            CatalogAtmosphericScene(
                selectedReading.value, zone, selectedWeather.value,
                modifier = Modifier.size(320.dp, 480.dp).testTag("scene"),
            )
        }

        fun frameAt(hour: Int) = run {
            selectedReading.value = reading.copy(
                selectedInstant = ZonedDateTime.of(2026, 10, 28, hour, 0, 0, 0, zone).toInstant(),
            )
            compose.waitForIdle()
            compose.onNodeWithTag("scene").captureToImage().toPixelMap()
        }
        val rainMorning = frameAt(6)
        val rainNoon = frameAt(12)
        val rainEvening = frameAt(18)
        assertTrue("rain banks should follow selected local time", differingPixels(rainMorning, rainNoon) > 0)
        assertTrue("rain banks should continue along the bounded path", differingPixels(rainNoon, rainEvening) > 0)
        assertEquals(0, differingPixels(rainNoon, frameAt(12)))

        selectedWeather.value = selectedWeather.value.copy(condition = SkyCondition.STORM)
        val stormMorning = frameAt(6)
        val stormNoon = frameAt(12)
        val stormEvening = frameAt(18)
        assertTrue("opposing storm banks should follow selected local time", differingPixels(stormMorning, stormNoon) > 0)
        assertTrue("storm banks should continue along the bounded path", differingPixels(stormNoon, stormEvening) > 0)
        assertEquals(0, differingPixels(stormNoon, frameAt(12)))
    }

    @Test fun missingSolarEventsDoNotRenderAnInferredDaytimeSun() {
        val noon = ZonedDateTime.of(2026, 10, 28, 12, 0, 0, 0, zone).toInstant()
        val dayReading = reading.copy(
            selectedInstant = noon,
            lightInterval = LightInterval(noon.minusSeconds(3600), noon.plusSeconds(3600), LightPhase.DAY),
            solarEvents = null,
        )
        val selectedReading = mutableStateOf(dayReading)
        val clearDay = weather.copy(
            condition = SkyCondition.CLEAR,
            cloudFraction = .05,
            daylight = 1.0,
        )
        compose.setContent {
            CatalogAtmosphericScene(
                selectedReading.value, zone, clearDay,
                modifier = Modifier.size(320.dp, 480.dp).testTag("scene"),
            )
        }
        val withoutEvents = compose.onNodeWithTag("scene").captureToImage().toPixelMap()
        selectedReading.value = dayReading.copy(
            solarEvents = SolarDayEvents(
                date = noon.atZone(zone).toLocalDate(),
                sunriseInstant = noon.minusSeconds(6 * 3600L),
                sunsetInstant = noon.plusSeconds(6 * 3600L),
            ),
        )
        compose.waitForIdle()
        val withEvents = compose.onNodeWithTag("scene").captureToImage().toPixelMap()
        assertTrue("a provider-bounded sun cue should differ from the missing-event scene",
            differingPixels(withoutEvents, withEvents) > 0)
        assertTrue("the selected daytime cue should be visible at its rendered position",
            withoutEvents[150, 86] != withEvents[150, 86])
    }

    private fun differingPixels(
        first: androidx.compose.ui.graphics.PixelMap,
        second: androidx.compose.ui.graphics.PixelMap,
    ): Int {
        var changed = 0
        for (y in 0 until first.height) for (x in 0 until first.width) {
            if (first[x, y] != second[x, y]) changed++
        }
        return changed
    }

}
