package com.oxygen.weather.timeline.presentation.scene

import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
}
