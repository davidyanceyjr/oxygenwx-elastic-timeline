package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CatalogWindowResult
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.debug.DebugCatalogActivity
import com.oxygen.weather.timeline.data.debug.DebugOx18ReviewScenario
import com.oxygen.weather.timeline.data.debug.DebugSg11StormFallbackCatalogFixture
import com.oxygen.weather.timeline.data.debug.sg4GapRecording
import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class SharedSceneWeatherDecisionTest {
    private val zone = ZoneId.of("America/Chicago")

    @Test fun `both vegetation adapters consume the shared declaration shape and decision`() = runBlocking {
        val selected = Instant.parse("2026-10-04T15:30:00Z")
        val decision = SceneWeatherDecision.resolve(sg4Reading(selected), zone)
        val city = FirstCityTheme.compose(decision, zone)
        val landscape = MinimalOpenLandscapeTheme.compose(decision)
        val cityDeclaration = FirstCitySemanticInventory.canopy(FirstCityCanopyAsset.OLIVE)
            .canopyDeclaration()
        val landscapeDeclaration = OpenLandscapeSemanticInventory.leftTreeGroup
        val cityInput = city.canopyRenderInput()
        val landscapeInput = landscape.treeGroupRenderInput()

        assertEquals(SceneElementRole.VEGETATION, cityDeclaration.role)
        assertEquals(SceneElementRole.VEGETATION, landscapeDeclaration.role)
        assertEquals(FirstCityElementId.CANOPY, cityDeclaration.id)
        assertEquals(FirstCityLayer.CANOPY, cityDeclaration.pass.layer)
        assertEquals(FirstCityWorldBounds(0f, 0f, 1024f, 1536f), cityInput.bounds)
        assertEquals(FirstCityCanopyAsset.OLIVE, cityInput.asset)
        assertEquals(OpenLandscapeElementId.LEFT_TREE_GROUP, landscapeDeclaration.id)
        assertEquals(OpenLandscapePass.FIRST_TREE_GROUP, landscapeDeclaration.pass)
        assertEquals(OpenLandscapeTreeGeometry(.20f, 1f), landscapeInput.geometry)
        assertEquals(selected, cityInput.selectedInstant)
        assertEquals(selected, landscapeInput.selectedInstant)
        assertSame(decision, city.weatherDecision)
        assertSame(decision, landscape.weatherDecision)
    }

    @Test fun `cloud-cover slot keeps supplied value and source evidence`() = runBlocking {
        val selected = Instant.parse("2026-10-28T20:00:00Z")
        val recording = DebugSg11StormFallbackCatalogFixture.unavailableRecording(
            DebugOx18ReviewScenario.location,
        )
        val decision = SceneWeatherDecision.resolve(recording.reading(selected), zone)
        val slot = CloudCoverInputSlot.from(decision)

        assertEquals(FirstCityElementId.CLOUD_COVER, slot.elementId)
        assertEquals(selected, slot.selectedInstant)
        assertEquals(.4, requireNotNull(slot.coverFraction), 0.0)
        assertEquals(decision.clouds.coverFraction, slot.coverFraction)
        val source = requireNotNull(slot.source)
        assertEquals(SceneEvidence.SUPPLIED, source.evidence)
        assertEquals(40.0, requireNotNull(source.value), 0.0)
        assertEquals(source.expectedSlot, source.sourceValidTime)
    }

    @Test fun `cloud-cover slot leaves missing cover unavailable despite carried rain and chance`() = runBlocking {
        val selected = Instant.parse("2026-10-04T16:30:00Z")
        val decision = SceneWeatherDecision.resolve(sg4Reading(selected), zone)
        val slot = CloudCoverInputSlot.from(decision)

        assertEquals(selected, slot.selectedInstant)
        assertNull(slot.coverFraction)
        assertEquals(SceneEvidence.EXPECTED_MISSING, slot.source?.evidence)
        assertNull(slot.source?.value)
        assertNull(slot.source?.sourceValidTime)
        assertEquals(SceneEvidence.EXPECTED_MISSING, decision.reading.conditions?.evidence)
        assertEquals(Instant.parse("2026-10-04T15:00:00Z"), decision.reading.conditions?.sourceValidTime)
        assertEquals(ScenePrecipitation.RAIN, decision.precipitation)
    }

    @Test fun `supplied rain decisions share one reading and retain independent probability`() = runBlocking {
        val selected = Instant.parse("2026-10-04T15:30:00Z")
        val reading = sg4Reading(selected)
        val decision = SceneWeatherDecision.resolve(reading, zone)
        val city = FirstCityTheme.compose(decision, zone)
        val landscape = MinimalOpenLandscapeTheme.compose(decision)

        assertSame(reading, decision.reading)
        assertSharedDecision(decision, city, landscape, selected)
        assertEquals(setOf(WeatherCondition.RAIN), reading.conditions?.value)
        assertEquals(SceneEvidence.SUPPLIED, reading.conditions?.evidence)
        assertEquals(ScenePrecipitation.RAIN, city.precipitation)
        assertEquals(ScenePrecipitation.RAIN, landscape.precipitation)
        assertEquals(75.0, requireNotNull(reading.precipitationChancePercent?.value), 0.0)
        assertEquals(reading.precipitationChancePercent, decision.reading.precipitationChancePercent)
        assertEquals(reading.rainAmount, decision.reading.rainAmount)
        assertEquals(reading.precipitationRate, decision.reading.precipitationRate)
    }

    @Test fun `eligible carried rain keeps its source time and does not infer cloud cover`() = runBlocking {
        val selected = Instant.parse("2026-10-04T16:30:00Z")
        val reading = sg4Reading(selected)
        val decision = SceneWeatherDecision.resolve(reading, zone)
        val city = FirstCityTheme.compose(decision, zone)
        val landscape = MinimalOpenLandscapeTheme.compose(decision)

        assertSharedDecision(decision, city, landscape, selected)
        assertEquals(setOf(WeatherCondition.RAIN), reading.conditions?.value)
        assertEquals(SceneEvidence.EXPECTED_MISSING, reading.conditions?.evidence)
        assertEquals(Instant.parse("2026-10-04T15:00:00Z"), reading.conditions?.sourceValidTime)
        assertEquals(ScenePrecipitation.RAIN, city.precipitation)
        assertEquals(ScenePrecipitation.RAIN, landscape.precipitation)
        assertNull(reading.cloudCoverPercent?.value)
        assertNull(city.clouds.coverFraction)
        assertFalse(city.clouds.visible)
        assertFalse(landscape.clouds.visible)
    }

    @Test fun `empty condition preserves independent clouds and chance without deriving precipitation`() = runBlocking {
        val selected = Instant.parse("2026-10-28T20:00:00Z")
        val recording = DebugSg11StormFallbackCatalogFixture.unavailableRecording(
            DebugOx18ReviewScenario.location,
        )
        val reading = recording.reading(selected)
        val decision = SceneWeatherDecision.resolve(reading, zone)
        val city = FirstCityTheme.compose(decision, zone)
        val landscape = MinimalOpenLandscapeTheme.compose(decision)

        assertSharedDecision(decision, city, landscape, selected)
        assertNull(reading.conditions?.value)
        assertEquals(SceneEvidence.SUPPLIED_EMPTY, reading.conditions?.evidence)
        assertNull(city.sky)
        assertNull(landscape.sky)
        assertEquals(ScenePrecipitation.NONE, city.precipitation)
        assertEquals(ScenePrecipitation.NONE, landscape.precipitation)
        assertEquals(40.0, requireNotNull(reading.cloudCoverPercent?.value), 0.0)
        assertEquals(SceneEvidence.SUPPLIED, requireNotNull(reading.cloudCoverPercent).evidence)
        assertEquals(.4, requireNotNull(city.clouds.coverFraction), 0.0)
        assertTrue(city.clouds.visible)
        assertTrue(landscape.clouds.visible)
        assertEquals(25.0, requireNotNull(reading.precipitationChancePercent?.value), 0.0)
        assertEquals(SceneEvidence.PROBABILITY_ONLY, requireNotNull(reading.precipitationChancePercent).evidence)
        assertNull(reading.rainAmount?.value)
        assertNull(reading.precipitationRate?.value)
    }

    private fun assertSharedDecision(
        decision: SceneWeatherDecision,
        city: FirstCityComposition,
        landscape: MinimalOpenLandscapeComposition,
        selected: Instant,
    ) {
        assertSame(decision, city.weatherDecision)
        assertSame(decision, landscape.weatherDecision)
        assertEquals(selected, decision.selectedInstant)
        assertEquals(selected, city.selectedInstant)
        assertEquals(selected, landscape.selectedInstant)
    }

    private suspend fun sg4Reading(selected: Instant): SceneReading {
        val recording = sg4GapRecording(
            CatalogLoadRequest(
                location = DebugOx18ReviewScenario.location,
                datasetId = DatasetId(DebugCatalogActivity.SG4_GAP_REPLAY_ID),
                asOf = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF),
            ),
        )
        return recording.reading(selected)
    }

    private fun com.oxygen.weather.timeline.data.catalog.CatalogRecording.reading(
        selected: Instant,
    ): SceneReading {
        val window = when (val result = CatalogWindow.create(
            catalog = catalog,
            expectedSupport = expectedSupport,
            dailyExpectedSupport = dailyExpectedSupport,
        )) {
            is CatalogWindowResult.Success -> result.window
            is CatalogWindowResult.Failure -> error("OX-18 test catalog window failed: ${result.error}")
        }
        return SemanticSceneGenerator.generate(
            catalog,
            window,
            window.startInclusive,
            window.endExclusive,
        ).evaluate(selected)
    }
}
