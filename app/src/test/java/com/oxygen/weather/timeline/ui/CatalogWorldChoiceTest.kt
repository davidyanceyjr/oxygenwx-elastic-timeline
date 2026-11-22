package com.oxygen.weather.timeline.ui

import com.oxygen.weather.timeline.data.catalog.CatalogCoordinatorTestSupport
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeCatalogEntry
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeId
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeOrigin
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeValidity
import com.oxygen.weather.timeline.data.scenepack.InstalledThemeVersion
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogWorldChoiceTest : CatalogCoordinatorTestSupport() {
    private fun installedBaseline() = InstalledThemeCatalogEntry(
        id = InstalledThemeId("first-city-canopy-baseline"),
        version = InstalledThemeVersion("v1"),
        displayName = "First City · Baseline pack",
        origin = InstalledThemeOrigin.APP_OWNED,
        validity = InstalledThemeValidity.COMPATIBLE,
    )

    @Test fun `park default and world selection leave active catalog and source request count unchanged`() = runBlocking {
        val sources = harness()
        val coordinator = sources.coordinator(this)
        coordinator.requestTransientLive(a, "Chicago").await()
        val active = requireNotNull(coordinator.state.value.active)
        val requestsBefore = sources.live.requests.size + sources.simulated.requests.size +
            sources.replay.requests.size
        val choice = CatalogWorldChoice<Unit>()

        assertEquals(CatalogWorld.LIVING_URBAN_PARK, choice.selected)
        assertEquals("Living Urban Park", choice.committed.label)
        assertEquals("Bundled free · selected default",
            choice.committed.detailFor(CatalogWorld.LIVING_URBAN_PARK))
        choice.choose(CatalogWorld.FIRST_CITY)
        assertEquals(CatalogWorld.FIRST_CITY, choice.selected)
        choice.choose(CatalogWorld.OPEN_LANDSCAPE)
        assertEquals(CatalogWorld.OPEN_LANDSCAPE, choice.selected)
        choice.choose(CatalogWorld.LIVING_URBAN_PARK)
        assertEquals(CatalogWorld.LIVING_URBAN_PARK, choice.selected)
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

    @Test fun successfulOlderInstalledCandidateCannotReplaceNewerBundledChoice() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val installed = installedBaseline()
        val loaderStarted = CompletableDeferred<Unit>()
        val releaseCandidate = CompletableDeferred<String>()
        val installedRequest = async {
            choice.chooseInstalled(installed, {
                loaderStarted.complete(Unit)
                releaseCandidate.await()
            }, admissible = { true })
        }

        withTimeout(2_000) { loaderStarted.await() }
        assertEquals(CatalogWorld.LIVING_URBAN_PARK, choice.committed.world)
        assertNull(choice.committed.candidate)

        choice.choose(CatalogWorld.FIRST_CITY)
        val bundledCommit = choice.committed
        assertEquals(CatalogWorld.FIRST_CITY, bundledCommit.world)
        assertEquals("First City", bundledCommit.label)
        assertEquals("Bundled free", bundledCommit.detailFor(CatalogWorld.FIRST_CITY))
        assertNull(bundledCommit.candidate)

        releaseCandidate.complete("complete admissible installed candidate")
        assertFalse(withTimeout(2_000) { installedRequest.await() })
        assertEquals(bundledCommit, choice.committed)
        assertEquals(CatalogWorld.FIRST_CITY, choice.committed.world)
        assertEquals("First City", choice.committed.label)
        assertNull(choice.committed.candidate)
    }

    @Test fun missingCurrentInstalledResolutionFallsBackToBundledFirstCity() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val installed = installedBaseline()
        assertTrue(choice.chooseInstalled(installed, { "complete installed candidate" }, { true }))
        assertEquals(CatalogWorld.INSTALLED_PACK, choice.committed.world)
        assertEquals(installed, choice.committed.installedTheme)

        // A null candidate is the existing resolver/loader result for a missing installed asset.
        assertTrue(choice.chooseInstalled(installed, { null }, { true }))
        assertEquals(CatalogWorld.FIRST_CITY, choice.committed.world)
        assertEquals("First City", choice.committed.label)
        assertNull(choice.committed.installedTheme)
        assertNull(choice.committed.candidate)
    }

    @Test fun invalidCurrentInstalledCandidateFallsBackToBundledFirstCity() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val installed = installedBaseline()
        assertTrue(choice.chooseInstalled(installed, { "complete installed candidate" }, { true }))

        assertTrue(choice.chooseInstalled(installed, { "inadmissible installed candidate" }, { false }))
        assertEquals(CatalogWorld.FIRST_CITY, choice.committed.world)
        assertEquals("First City", choice.committed.label)
        assertNull(choice.committed.candidate)
    }

    @Test fun successfulCurrentInstalledResolutionKeepsTheInstalledWorld() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val installed = installedBaseline()
        assertTrue(choice.chooseInstalled(installed, { "initial candidate" }, { true }))

        assertTrue(choice.chooseInstalled(installed, { "resolved candidate" }, { true }))
        assertEquals(CatalogWorld.INSTALLED_PACK, choice.committed.world)
        assertEquals(installed, choice.committed.installedTheme)
        assertEquals("resolved candidate", choice.committed.candidate)
    }

    @Test fun failedNewInstalledChoiceDoesNotReplaceTheCommittedWorld() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val before = choice.committed

        assertFalse(choice.chooseInstalled(installedBaseline(), { null }, { true }))
        assertEquals(before, choice.committed)
    }

    @Test fun failedOlderInstalledResolutionCannotReplaceNewerWorldChoice() = runBlocking {
        val choice = CatalogWorldChoice<String>()
        val installed = installedBaseline()
        assertTrue(choice.chooseInstalled(installed, { "complete installed candidate" }, { true }))

        val loaderStarted = CompletableDeferred<Unit>()
        val releaseFailure = CompletableDeferred<String?>()
        val installedRequest = async {
            choice.chooseInstalled(installed, {
                loaderStarted.complete(Unit)
                releaseFailure.await()
            }, admissible = { true })
        }
        withTimeout(2_000) { loaderStarted.await() }

        choice.choose(CatalogWorld.OPEN_LANDSCAPE)
        releaseFailure.complete(null)

        assertFalse(withTimeout(2_000) { installedRequest.await() })
        assertEquals(CatalogWorld.OPEN_LANDSCAPE, choice.committed.world)
        assertEquals("Open Landscape", choice.committed.label)
    }
}
