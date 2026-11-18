package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FirstCityThemeTest {
    private val zone = ZoneId.of("America/Chicago")
    private val date = LocalDate.parse("2026-11-01")
    private val sunrise = ZonedDateTime.of(date.year, date.monthValue, date.dayOfMonth, 6, 23, 8, 0, zone).toInstant()
    private val sunset = ZonedDateTime.of(date.year, date.monthValue, date.dayOfMonth, 16, 44, 44, 0, zone).toInstant()

    private fun reading(selected: java.time.Instant, events: SolarDayEvents?) = SceneReading(
        selectedInstant = selected,
        weather = emptyMap(),
        intervals = emptyMap(),
        lightInterval = LightInterval(selected.minusSeconds(1), selected.plusSeconds(1), LightPhase.DAY),
        solarEvents = events,
    )

    @Test fun canopyAdapterUsesDeclarationAtSelectedInstant() {
        val selected = sunrise.plusSeconds(3600)
        val weatherDecision = SceneWeatherDecision.resolve(reading(selected, null), zone)
        val composition = FirstCityTheme.compose(weatherDecision, zone)
        val input = composition.canopyRenderInput()

        assertEquals(selected, input.selectedInstant)
        assertEquals(FirstCityElementId.CANOPY, input.id)
        assertEquals(FirstCityLayer.CANOPY, input.pass.layer)
        assertEquals(FirstCityDepthGroup.FOREGROUND, input.pass.depthGroup)
        assertEquals(1, input.pass.localOrder)
        assertEquals(FirstCityWorldBounds(0f, 0f, 1024f, 1536f), input.bounds)
        assertTrue(FirstCityLayer.CANOPY in composition.layerOrder)
        assertTrue(composition.weatherDecision === weatherDecision)
        assertEquals(FirstCityCanopyAsset.OLIVE, input.asset)
        val baseline = composition.canopyRenderInput(
            FirstCitySemanticInventory.canopy(FirstCityCanopyAsset.BASELINE)
        )
        val olive = composition.canopyRenderInput(
            FirstCitySemanticInventory.canopy(FirstCityCanopyAsset.OLIVE)
        )
        assertEquals(FirstCityCanopyAsset.BASELINE, baseline.asset)
        assertEquals(FirstCityCanopyAsset.OLIVE, olive.asset)
        assertEquals(baseline.copy(asset = FirstCityCanopyAsset.OLIVE), olive)
        assertEquals(input, composition.canopyRenderInput(FirstCitySemanticInventory.canopy(input.asset)))
        assertTrue(composition.weatherDecision === weatherDecision)
    }

    @Test fun `sun cue meets the horizon only at provider events and stays absent outside them`() {
        val events = SolarDayEvents(date, sunrise, sunset)
        val before = reading(sunrise.minusSeconds(1), events)
        val atSunrise = reading(sunrise, events)
        val middayInstant = sunrise.plus(Duration.between(sunrise, sunset).dividedBy(2))
        val midday = reading(middayInstant, events)
        val atSunset = reading(sunset, events)
        val after = reading(sunset.plusSeconds(1), events)

        assertNull(FirstCityTheme.cuePosition(before, zone, CityTreatment.MORNING))
        assertEquals(.12f to .42f, FirstCityTheme.cuePosition(atSunrise, zone, CityTreatment.MORNING))
        val noonCue = FirstCityTheme.cuePosition(midday, zone, CityTreatment.DAY)
        requireNotNull(noonCue)
        assertEquals(.18f, noonCue.second, .01f)
        assertEquals(.5f, noonCue.first, .01f)
        assertEquals(.88f to .42f, FirstCityTheme.cuePosition(atSunset, zone, CityTreatment.DUSK))
        assertNull(FirstCityTheme.cuePosition(after, zone, CityTreatment.DUSK))
        assertNull(FirstCityTheme.cuePosition(reading(middayInstant, null), zone, CityTreatment.DAY))
        assertEquals(middayInstant, midday.selectedInstant)
    }

    @Test fun `moon follows neighboring rise to set events and keeps neutral phase stage`() {
        val rise = ZonedDateTime.of(2026, 10, 31, 22, 0, 0, 0, zone).toInstant()
        val set = ZonedDateTime.of(2026, 11, 1, 6, 0, 0, 0, zone).toInstant()
        val selected = rise.plus(Duration.between(rise, set).dividedBy(2))
        val events = listOf(
            LunarEvent(LocalDate.parse("2026-10-31"), LunarEventKind.RISE, rise),
            LunarEvent(LocalDate.parse("2026-11-01"), LunarEventKind.SET, set),
        )
        val reading = reading(selected, null).copy(
            lunarDay = LunarDayFacts(LocalDate.parse("2026-11-01"), .125, null, set),
            lunarEvents = events,
        )
        val cue = FirstCityTheme.lunarPosition(reading)
        assertNotNull(cue)
        assertEquals(.5f, cue!!.first, .001f)
        assertEquals(.18f, cue.second, .01f)
        assertEquals(.8f, FirstCityTheme.lunarScale(reading), .001f)
        assertEquals("Waxing Crescent", FirstCityTheme.phaseLabel(reading.lunarDay?.phase))
        assertEquals("New Moon", FirstCityTheme.phaseLabel(1.0))
        assertEquals(listOf(
            "New Moon", "Waxing Crescent", "First Quarter", "Waxing Gibbous",
            "Full Moon", "Waning Gibbous", "Last Quarter", "Waning Crescent",
        ), (0..7).map { FirstCityTheme.phaseLabel(it / 8.0) })
        assertNull(FirstCityTheme.phaseLabel(null))

        val exactRise = reading.copy(selectedInstant = rise)
        assertEquals(.12f to .42f, FirstCityTheme.lunarPosition(exactRise))
        assertEquals(1.28f, FirstCityTheme.lunarScale(exactRise), .001f)
        val exactSet = reading.copy(selectedInstant = set)
        assertEquals(.88f to .42f, FirstCityTheme.lunarPosition(exactSet))
        assertEquals(1.28f, FirstCityTheme.lunarScale(exactSet), .001f)
        val unresolved = reading.copy(lunarEvents = listOf(events.last()))
        assertNull(FirstCityTheme.lunarPosition(unresolved))
        val setThenRise = listOf(
            LunarEvent(LocalDate.parse("2026-10-31"), LunarEventKind.SET, selected.minusSeconds(3600)),
            LunarEvent(LocalDate.parse("2026-11-01"), LunarEventKind.RISE, selected.plusSeconds(3600)),
        )
        assertNull(FirstCityTheme.lunarPosition(reading.copy(lunarEvents = setThenRise)))
    }

    @Test fun `cloud layers occlude a supported lunar cue`() {
        val rise = ZonedDateTime.of(2026, 10, 31, 22, 0, 0, 0, zone).toInstant()
        val set = ZonedDateTime.of(2026, 11, 1, 6, 0, 0, 0, zone).toInstant()
        val selected = rise.plus(Duration.between(rise, set).dividedBy(2))
        val cloudy = reading(selected, null).copy(
            cloudCoverPercent = ScenePoint(selected, selected, 80.0, SceneEvidence.SUPPLIED, null),
            lunarEvents = listOf(
                LunarEvent(LocalDate.parse("2026-10-31"), LunarEventKind.RISE, rise),
                LunarEvent(LocalDate.parse("2026-11-01"), LunarEventKind.SET, set),
            ),
        )

        val composition = FirstCityTheme.compose(cloudy, zone)

        assertTrue(composition.moonCue.visible)
        assertTrue(composition.clouds.visible)
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.LUNAR_CUE) <
            composition.layerOrder.indexOf(FirstCityLayer.BACKGROUND_CLOUDS))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.MIDDLE_GROUND_CLOUDS) <
            composition.layerOrder.indexOf(FirstCityLayer.FOREGROUND_ARCHITECTURE))
    }

    @Test fun physicalLayerGroupsAndLocalCueOrdersAreMonotonic() {
        val selected = ZonedDateTime.of(2026, 11, 1, 12, 0, 0, 0, zone).toInstant()
        val solar = SolarDayEvents(date, selected.minusSeconds(6 * 3600L), selected.plusSeconds(6 * 3600L))
        val lunar = listOf(
            LunarEvent(date, LunarEventKind.RISE, selected.minusSeconds(3 * 3600L)),
            LunarEvent(date, LunarEventKind.SET, selected.plusSeconds(3 * 3600L)),
        )
        val reading = reading(selected, solar).copy(
            cloudCoverPercent = ScenePoint(selected, selected, 80.0, SceneEvidence.SUPPLIED, null),
            lunarEvents = lunar,
        )

        val composition = FirstCityTheme.compose(reading, zone)
        val physicalLayers = composition.layerOrder.filter { it.depthGroup != null }
        val orderKeys = physicalLayers.map {
            requireNotNull(it.depthGroup).backToFrontOrder to requireNotNull(it.localOrder)
        }

        assertEquals(orderKeys.sortedWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second }), orderKeys)
        assertTrue(composition.cue.visible)
        assertTrue(composition.moonCue.visible)
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.STARS) <
            composition.layerOrder.indexOf(FirstCityLayer.TIME_CUE))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.TIME_CUE) <
            composition.layerOrder.indexOf(FirstCityLayer.LUNAR_CUE))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.LUNAR_CUE) <
            composition.layerOrder.indexOf(FirstCityLayer.BACKGROUND_CLOUDS))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.BACKGROUND_CLOUDS) <
            composition.layerOrder.indexOf(FirstCityLayer.BACKGROUND_ARCHITECTURE))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.ATMOSPHERE) <
            composition.layerOrder.indexOf(FirstCityLayer.MIDDLE_GROUND_CLOUDS))
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.MIDDLE_GROUND_CLOUDS) <
            composition.layerOrder.indexOf(FirstCityLayer.FOREGROUND_ARCHITECTURE))
    }

    @Test fun rainFieldFollowsNearArchitectureInForeground() {
        val selected = ZonedDateTime.of(2026, 11, 1, 12, 0, 0, 0, zone).toInstant()
        val suppliedRain = reading(selected, null).copy(
            conditions = ScenePoint(
                selected, selected, setOf(WeatherCondition.RAIN), SceneEvidence.SUPPLIED, null,
            ),
            cloudCoverPercent = ScenePoint(selected, selected, 88.0, SceneEvidence.SUPPLIED, null),
        )

        val composition = FirstCityTheme.compose(suppliedRain, zone)

        assertEquals(CityPrecipitation.RAIN, composition.precipitation)
        assertTrue(composition.layerOrder.indexOf(FirstCityLayer.FOREGROUND_ARCHITECTURE) <
            composition.layerOrder.indexOf(FirstCityLayer.PRECIPITATION))
    }

    @Test fun `semantic inventory is the complete stable First City identity and role map`() {
        val inventory = FirstCitySemanticInventory.elements

        assertEquals(11, inventory.size)
        assertEquals(
            listOf(
                FirstCityElementId.SKY_BACKDROP to FirstCityElementRole.SKY,
                FirstCityElementId.STAR_FIELD to FirstCityElementRole.CELESTIAL_FIELD,
                FirstCityElementId.SUN_CUE to FirstCityElementRole.TIME_CUE,
                FirstCityElementId.MOON_CUE to FirstCityElementRole.LUNAR_CUE,
                FirstCityElementId.CLOUD_COVER to FirstCityElementRole.CLOUD_FIELD,
                FirstCityElementId.DISTANT_SKYLINE to FirstCityElementRole.CITY_STRUCTURE,
                FirstCityElementId.DIFFUSE_ATMOSPHERIC_LIGHT to FirstCityElementRole.ATMOSPHERE,
                FirstCityElementId.NEAR_CITY to FirstCityElementRole.CITY_STRUCTURE,
                FirstCityElementId.CANOPY to FirstCityElementRole.VEGETATION,
                FirstCityElementId.WINDOW_DETAILS to FirstCityElementRole.LIGHT_DETAIL,
                FirstCityElementId.PRECIPITATION_FIELD to FirstCityElementRole.WEATHER_FIELD,
            ),
            inventory.map { it.id to it.role },
        )
    }

    @Test fun `semantic inventory maps every physical pass and excludes compositing passes`() {
        val inventory = FirstCitySemanticInventory.elements
        val mappedPasses = inventory.flatMap { it.renderPasses }
        val physicalPasses = FirstCityLayer.values().filter { it.depthGroup != null }.toSet()

        assertEquals(12, physicalPasses.size)
        assertEquals(12, mappedPasses.size)
        assertEquals(mappedPasses.size, mappedPasses.toSet().size)
        assertEquals(physicalPasses, mappedPasses.toSet())
        assertEquals(
            mapOf(
                FirstCityLayer.SKY to FirstCityElementId.SKY_BACKDROP,
                FirstCityLayer.STARS to FirstCityElementId.STAR_FIELD,
                FirstCityLayer.TIME_CUE to FirstCityElementId.SUN_CUE,
                FirstCityLayer.LUNAR_CUE to FirstCityElementId.MOON_CUE,
                FirstCityLayer.BACKGROUND_CLOUDS to FirstCityElementId.CLOUD_COVER,
                FirstCityLayer.MIDDLE_GROUND_CLOUDS to FirstCityElementId.CLOUD_COVER,
                FirstCityLayer.BACKGROUND_ARCHITECTURE to FirstCityElementId.DISTANT_SKYLINE,
                FirstCityLayer.ATMOSPHERE to FirstCityElementId.DIFFUSE_ATMOSPHERIC_LIGHT,
                FirstCityLayer.FOREGROUND_ARCHITECTURE to FirstCityElementId.NEAR_CITY,
                FirstCityLayer.CANOPY to FirstCityElementId.CANOPY,
                FirstCityLayer.WINDOW_LIGHTS to FirstCityElementId.WINDOW_DETAILS,
                FirstCityLayer.PRECIPITATION to FirstCityElementId.PRECIPITATION_FIELD,
            ),
            mappedPasses.associateWith { pass ->
                requireNotNull(FirstCitySemanticInventory.forRenderPass(pass)).id
            },
        )
        assertNull(FirstCitySemanticInventory.forRenderPass(FirstCityLayer.TIME_WASH))
        assertNull(FirstCitySemanticInventory.forRenderPass(FirstCityLayer.READOUT_QUIET_ZONE))
    }
}
