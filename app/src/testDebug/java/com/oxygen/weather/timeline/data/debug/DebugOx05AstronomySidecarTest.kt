package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.ActiveCatalogSelection
import com.oxygen.weather.timeline.data.catalog.CatalogActivationDelivery
import com.oxygen.weather.timeline.data.catalog.CatalogActivationState
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.RecordedCatalogCodec
import com.oxygen.weather.timeline.data.catalog.RecordingDecodeResult
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.presentation.CatalogTimelinePresenter
import com.oxygen.weather.timeline.presentation.scene.CityPrecipitation
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.LightPhase
import com.oxygen.weather.timeline.presentation.scene.LunarDayFacts
import com.oxygen.weather.timeline.presentation.scene.LunarEvent
import com.oxygen.weather.timeline.presentation.scene.LunarEventKind
import com.oxygen.weather.timeline.presentation.scene.SolarDayEvents
import java.time.Instant
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

class DebugOx05AstronomySidecarTest {
    @Test fun `recording has fixed simulated identity and exact review weather points`() = runBlocking {
        val recording = DebugOx05DepthOrderingFixture.recording()
        val catalog = recording.catalog
        val clear = catalog.facts.single { it.validTime == DebugOx05DepthOrderingFixture.celestialOverlapInstant }
        val rain = catalog.facts.single { it.validTime == DebugOx05DepthOrderingFixture.moderateRainInstant }

        assertEquals(DebugOx05DepthOrderingFixture.location, catalog.location)
        assertEquals(DebugOx05DepthOrderingFixture.datasetId, catalog.datasetId)
        assertEquals(DebugOx05DepthOrderingFixture.asOf, catalog.asOf)
        assertEquals(CatalogOrigin.SIMULATED, catalog.provenance.origin)
        assertEquals(emptyList<Any>(), catalog.dailySummaries)
        assertEquals(setOf(WeatherCondition.CLEAR), clear.conditions)
        assertEquals(0.0, clear.cloudCoverPercent!!, 0.0)
        assertEquals(setOf(WeatherCondition.RAIN), rain.conditions)
        assertEquals(88.0, rain.cloudCoverPercent!!, 0.0)
    }

    @Test fun `fixed review recording survives canonical storage encoding`() = runBlocking {
        val recording = DebugOx05DepthOrderingFixture.recording()

        val decoded = RecordedCatalogCodec.decode(RecordedCatalogCodec.encode(recording))
        assertTrue("The fixed OX-05 recording must decode after storage encoding: $decoded", decoded is RecordingDecodeResult.Success)
        val decodedRecording = (decoded as RecordingDecodeResult.Success).recording
        assertEquals(recording.catalog, decodedRecording.catalog)
        assertTrue(DebugOx05DepthOrderingFixture.matchesReviewContract(decodedRecording))
    }

    @Test fun `authored sidecar is fixture scoped and recomposes both approved selected states`() = runBlocking {
        val recording = DebugOx05DepthOrderingFixture.recording()
        val clearSelection = active(recording, DebugOx05DepthOrderingFixture.celestialOverlapInstant)
        val clearBase = CatalogTimelinePresenter(clearSelection).scenePresentation(
            CatalogActivationState(selectedLocation = clearSelection.location, active = clearSelection),
        )
        val clear = DebugOx05AstronomySidecar.apply(
            clearSelection.datasetId, clearSelection.origin, clearSelection.location.timeZone, clearBase,
        )
        val clearInput = requireNotNull(DebugOx05AstronomySidecar.inputFor(LocalDate.parse("2026-10-28")))

        assertEquals(AstronomyInputOrigin.AUTHORED_SYNTHETIC_REVIEW_INPUT, clearInput.origin)
        assertEquals(Instant.parse("2026-10-28T11:00:00Z"), clearInput.sunrise)
        assertEquals(Instant.parse("2026-10-28T23:00:00Z"), clearInput.sunset)
        assertEquals(Instant.parse("2026-10-28T09:30:00Z"), clearInput.moonrise)
        assertEquals(Instant.parse("2026-10-28T22:30:00Z"), clearInput.moonset)
        assertEquals(0.50, clearInput.moonPhase, 0.0)
        assertEquals("DEMO DATA · OX-05 authored synthetic astronomy fixture", DebugOx05AstronomySidecar.LABEL)
        assertEquals(clearBase.timeline, clear.timeline)
        assertEquals(clearBase.diagnostics, clear.diagnostics)
        assertEquals(clearBase.reading.selectedInstant, clear.reading.selectedInstant)
        assertEquals(clearBase.reading.conditions, clear.reading.conditions)
        assertEquals(clearBase.reading.phenomena, clear.reading.phenomena)
        assertEquals(clearBase.reading.cloudCoverPercent, clear.reading.cloudCoverPercent)
        assertEquals(clearBase.reading.precipitationChancePercent, clear.reading.precipitationChancePercent)
        assertEquals(clearBase.reading.precipitationRate, clear.reading.precipitationRate)
        assertEquals(clearBase.reading.rainAmount, clear.reading.rainAmount)
        assertEquals(clearBase.reading.totalPrecipitation, clear.reading.totalPrecipitation)
        assertEquals(clearBase.reading.snowfall, clear.reading.snowfall)
        assertEquals(LightPhase.DAY, clear.reading.lightPhase)
        assertEquals(SolarDayEvents(LocalDate.parse("2026-10-28"), clearInput.sunrise, clearInput.sunset), clear.reading.solarEvents)
        assertEquals(LunarDayFacts(LocalDate.parse("2026-10-28"), 0.50, clearInput.moonrise, clearInput.moonset), clear.reading.lunarDay)
        assertEquals(
            listOf(
                LunarEvent(LocalDate.parse("2026-10-28"), LunarEventKind.RISE, clearInput.moonrise),
                LunarEvent(LocalDate.parse("2026-10-28"), LunarEventKind.SET, clearInput.moonset),
            ),
            clear.reading.lunarEvents,
        )
        assertTrue(clear.composition.cue.visible)
        assertTrue(clear.composition.moonCue.visible)
        val sunPosition = requireNotNull(clear.composition.cue.position)
        val moonPosition = requireNotNull(clear.composition.moonCue.position)
        assertEquals(0.500f, sunPosition.first, 0.002f)
        assertEquals(0.180f, sunPosition.second, 0.002f)
        assertEquals(0.558f, moonPosition.first, 0.002f)
        assertEquals(0.187f, moonPosition.second, 0.002f)

        val rainSelection = active(recording, DebugOx05DepthOrderingFixture.moderateRainInstant)
        val rainBase = CatalogTimelinePresenter(rainSelection).scenePresentation(
            CatalogActivationState(selectedLocation = rainSelection.location, active = rainSelection),
        )
        val rain = DebugOx05AstronomySidecar.apply(
            rainSelection.datasetId, rainSelection.origin, rainSelection.location.timeZone, rainBase,
        )
        assertEquals(rainBase.timeline, rain.timeline)
        assertEquals(rainBase.diagnostics, rain.diagnostics)
        assertEquals(rainBase.reading.selectedInstant, rain.reading.selectedInstant)
        assertEquals(rainBase.reading.conditions, rain.reading.conditions)
        assertEquals(rainBase.reading.cloudCoverPercent, rain.reading.cloudCoverPercent)
        assertEquals(LightPhase.DAY, rain.reading.lightPhase)
        assertEquals(CityPrecipitation.RAIN, rain.composition.precipitation)
        assertFalse(rain.composition.cue.visible)
        assertTrue(rain.composition.moonCue.visible)
        assertEquals(0.88, rain.composition.clouds.coverFraction!!, 0.0)
        assertEquals(LocalDate.parse("2026-10-29"), rain.reading.solarEvents?.date)
        assertEquals(LocalDate.parse("2026-10-29"), rain.reading.lunarDay?.date)

        assertSame(clearBase, DebugOx05AstronomySidecar.apply(
            DatasetId("another-dataset"), clearSelection.origin, clearSelection.location.timeZone, clearBase,
        ))
        assertSame(clearBase, DebugOx05AstronomySidecar.apply(
            clearSelection.datasetId, CatalogOrigin.LIVE, clearSelection.location.timeZone, clearBase,
        ))
        val outsideReviewedDates = clearBase.copy(
            reading = clearBase.reading.copy(selectedInstant = Instant.parse("2026-10-30T17:00:00Z")),
        )
        assertSame(outsideReviewedDates, DebugOx05AstronomySidecar.apply(
            clearSelection.datasetId, clearSelection.origin, clearSelection.location.timeZone, outsideReviewedDates,
        ))
        assertNotSame(clearBase, clear)
        assertEquals(FirstCityTheme.compose(clear.reading, clearSelection.location.timeZone), clear.composition)
    }

    private fun active(
        recording: com.oxygen.weather.timeline.data.catalog.CatalogRecording,
        selectedInstant: Instant,
    ) = ActiveCatalogSelection(
        location = recording.catalog.location,
        recording = recording,
        origin = CatalogOrigin.SIMULATED,
        datasetId = recording.catalog.datasetId,
        replayRecordingId = null,
        delivery = CatalogActivationDelivery.SOURCE,
        freshness = null,
        storedAt = null,
        selectedInstant = selectedInstant,
        viewGeneration = 1,
    )
}
