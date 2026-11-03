package com.oxygen.weather.timeline.presentation.scene

import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
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

    @Test fun `cloud layer occludes a supported lunar cue`() {
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
            composition.layerOrder.indexOf(FirstCityLayer.CLOUDS))
    }
}
