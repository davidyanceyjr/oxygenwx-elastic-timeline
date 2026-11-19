package com.oxygen.weather.timeline.ui

import com.oxygen.weather.timeline.data.catalog.CatalogCoordinatorTestSupport
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class CatalogWorldChoiceTest : CatalogCoordinatorTestSupport() {
    @Test fun `world selection leaves active catalog and source request count unchanged`() = runBlocking {
        val sources = harness()
        val coordinator = sources.coordinator(this)
        coordinator.requestTransientLive(a, "Chicago").await()
        val active = requireNotNull(coordinator.state.value.active)
        val requestsBefore = sources.live.requests.size + sources.simulated.requests.size +
            sources.replay.requests.size
        val choice = CatalogWorldChoice()

        assertEquals(CatalogWorld.FIRST_CITY, choice.selected)
        choice.choose(CatalogWorld.OPEN_LANDSCAPE)
        assertEquals(CatalogWorld.OPEN_LANDSCAPE, choice.selected)
        choice.choose(CatalogWorld.FIRST_CITY)
        assertEquals(CatalogWorld.FIRST_CITY, choice.selected)
        assertSame(active, coordinator.state.value.active)
        assertEquals(active.location, coordinator.state.value.active?.location)
        assertEquals(active.datasetId, coordinator.state.value.active?.datasetId)
        assertEquals(active.recording.catalog.asOf, coordinator.state.value.active?.recording?.catalog?.asOf)
        assertEquals(active.viewGeneration, coordinator.state.value.active?.viewGeneration)
        assertEquals(active.selectedInstant, coordinator.state.value.active?.selectedInstant)
        assertEquals(requestsBefore, sources.live.requests.size + sources.simulated.requests.size +
            sources.replay.requests.size)
    }
}
