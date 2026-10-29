package com.oxygen.weather.timeline.ui

import com.oxygen.weather.timeline.data.catalog.CanonicalPlaceId
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.data.catalog.SavedLocationListResult
import com.oxygen.weather.timeline.data.catalog.SavedLocationResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.URI
import java.time.ZoneId
import java.util.concurrent.CopyOnWriteArrayList

class LocationSelectorControllerTest {
    @Test
    fun committedSelectionDispatchesOnlyAfterPersistenceSucceeds() = runBlocking {
        val location = saved(candidate("Chicago", 31))
        val store = FakeStore(mutableListOf(location))
        val committed = mutableListOf<SavedLocation>()
        val controller = LocationSelectorController(
            this,
            RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches },
            store,
            onCommittedSelection = committed::add,
        )

        controller.select(location)
        await { committed.size == 1 }
        assertEquals(location, committed.single())

        store.selectOverride = SavedLocationResult.MissingLocation
        controller.select(location)
        await { controller.state.value.message?.contains("no longer available") == true }
        assertEquals(1, committed.size)
    }

    @Test
    fun searchRunsOnlyOnSubmitAndEditsClearPreviousResults() = runBlocking {
        val searcher = RecordingSearcher { _, query ->
            PlaceSearchResult.Matches(listOf(candidate(query, 1)), 0)
        }
        val controller = LocationSelectorController(this, searcher, FakeStore())

        controller.editQuery("Springfield")
        delay(80)
        assertTrue(searcher.queries.isEmpty())
        assertEquals(LocationSearchState.Idle, controller.state.value.search)

        controller.submitSearch()
        await { controller.state.value.search is LocationSearchState.Matches }
        controller.editQuery("Madison")
        assertEquals(LocationSearchState.Idle, controller.state.value.search)
        assertEquals(listOf("Springfield"), searcher.queries)
    }

    @Test
    fun latestSubmittedQueryOwnsVisibleSearchState() = runBlocking {
        val old = CompletableDeferred<PlaceSearchResult>()
        val searcher = RecordingSearcher { _, query ->
            if (query == "old") old.await() else PlaceSearchResult.NoMatches
        }
        val controller = LocationSelectorController(this, searcher, FakeStore())

        controller.editQuery("old")
        controller.submitSearch()
        await { searcher.queries.contains("old") }
        controller.editQuery("new")
        controller.submitSearch()
        await { controller.state.value.search == LocationSearchState.NoMatches("new") }
        old.complete(PlaceSearchResult.Matches(listOf(candidate("Oldtown", 2)), 0))
        delay(100)

        assertEquals(LocationSearchState.NoMatches("new"), controller.state.value.search)
    }

    @Test
    fun addDoesNotSelectAndRemovalRequiresConfirmation() = runBlocking {
        val place = candidate("Chicago", 3)
        val store = FakeStore()
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        controller.open()
        await { controller.state.value.saved != null }

        controller.add(place)
        await { controller.state.value.saved?.isNotEmpty() == true }
        assertNull(controller.state.value.selectedId)
        assertEquals(0, store.selectCalls)

        val saved = controller.state.value.saved!!.single()
        controller.requestRemoval(saved)
        controller.cancelRemoval()
        assertEquals(0, store.removeCalls)
        assertEquals(listOf(saved), controller.state.value.saved)
    }

    @Test
    fun selectedLocationCanBeRemovedAndRestoreClearsSelection() = runBlocking {
        val place = candidate("Madison", 4)
        val store = FakeStore(mutableListOf(saved(place)), selected = place.canonicalPlaceId!!.value)
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        controller.open()
        await { controller.state.value.savedLoading.not() && controller.state.value.selectedId != null }
        val item = controller.state.value.saved!!.single()

        controller.requestRemoval(item)
        controller.confirmRemoval()
        await { controller.state.value.saved?.isEmpty() == true && !controller.state.value.savedLoading }

        assertNull(controller.state.value.selectedId)
        assertEquals(1, store.removeCalls)
    }

    @Test
    fun selectingPersistsIdentityAndRemovingAnotherLocationKeepsItSelected() = runBlocking {
        val chicago = candidate("Chicago", 5)
        val madison = candidate("Madison", 6)
        val store = FakeStore(mutableListOf(saved(chicago), saved(madison)))
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        controller.open()
        await { controller.state.value.saved?.size == 2 && !controller.state.value.savedLoading }

        controller.select(controller.state.value.saved!!.first())
        await { controller.state.value.selectedId == chicago.canonicalPlaceId?.let { LocationId(it.value) } }
        assertEquals(chicago.canonicalPlaceId!!.value, (store.restore() as SavedLocationResult.Selected).location!!.location.id.value)

        val unselected = controller.state.value.saved!!.first { it.location.id.value == madison.canonicalPlaceId!!.value }
        controller.requestRemoval(unselected)
        controller.confirmRemoval()
        await { controller.state.value.saved?.size == 1 && !controller.state.value.savedLoading }
        assertEquals(chicago.canonicalPlaceId.value, controller.state.value.selectedId?.value)
    }

    @Test
    fun noMatchAndFailureStatesRemainRecoverable() = runBlocking {
        var attempts = 0
        val searcher = RecordingSearcher { _, _ ->
            attempts++
            if (attempts == 1) PlaceSearchResult.Failure(PlaceSearchResult.Failure.Reason.CONNECTIVITY)
            else PlaceSearchResult.NoMatches
        }
        val controller = LocationSelectorController(this, searcher, FakeStore())
        controller.editQuery("Unknown")
        controller.submitSearch()
        await { controller.state.value.search is LocationSearchState.Failed }
        controller.retrySearch()
        await { controller.state.value.search == LocationSearchState.NoMatches("Unknown") }
        assertEquals(2, attempts)
    }

    @Test
    fun failedRefreshKeepsLastKnownSavedState() = runBlocking {
        val place = candidate("Chicago", 7)
        val store = FakeStore(mutableListOf(saved(place)))
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        controller.open()
        await { controller.state.value.saved?.size == 1 && !controller.state.value.savedLoading }
        store.failList = true

        controller.refreshSnapshot()
        await { !controller.state.value.savedLoading && controller.state.value.messageIsError }
        assertEquals(listOf(saved(place)), controller.state.value.saved)
    }

    @Test
    fun alreadySavedAndTypedAddFailuresAreReportedTruthfully() = runBlocking {
        val place = candidate("Chicago", 8)
        val existing = saved(place)
        val store = FakeStore(mutableListOf(existing))
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        controller.open()
        await { controller.state.value.saved?.size == 1 && !controller.state.value.savedLoading }
        store.addOverride = SavedLocationResult.AlreadySaved(existing)
        controller.add(place)
        await { controller.state.value.message?.contains("already saved") == true }
        assertFalse(controller.state.value.messageIsError)
        assertNull(controller.state.value.selectedId)

        store.addOverride = SavedLocationResult.PointConflict(place.canonicalPlaceId!!)
        controller.add(place)
        await { controller.state.value.messageIsError }
        assertTrue(controller.state.value.message!!.contains("different coordinates"))
        assertEquals(listOf(existing), controller.state.value.saved)

        store.addOverride = SavedLocationResult.MalformedRecord
        controller.add(place)
        await { controller.state.value.message!!.contains("data is invalid") }
        assertTrue(controller.state.value.messageIsError)
    }

    @Test
    fun missingCanonicalIdAndMissingSelectionTargetRemainErrors() = runBlocking {
        val store = FakeStore()
        val controller = LocationSelectorController(this, RecordingSearcher { _, _ -> PlaceSearchResult.NoMatches }, store)
        val noIdentity = candidate("Nowhere", 9).copy(canonicalPlaceId = null)
        controller.add(noIdentity)
        await { controller.state.value.messageIsError }
        assertTrue(controller.state.value.message!!.contains("stable location identity"))
        assertNull(controller.state.value.saved)

        val stale = saved(candidate("Stale", 10))
        controller.select(stale)
        await { controller.state.value.message!!.contains("no longer available") }
        assertTrue(controller.state.value.messageIsError)
    }

    private suspend fun await(predicate: () -> Boolean) = withTimeout(3_000) {
        while (!predicate()) delay(10)
    }

    private class RecordingSearcher(
        private val response: suspend (Int, String) -> PlaceSearchResult,
    ) : PlaceSearcher {
        val queries = CopyOnWriteArrayList<String>()
        override suspend fun search(query: String): PlaceSearchResult {
            queries += query
            return response(queries.size, query)
        }
    }

    private class FakeStore(
        private val values: MutableList<SavedLocation> = mutableListOf(),
        selected: String? = null,
    ) : LocationSelectorStore {
        private var selectedId: String? = selected
        var selectCalls = 0
        var removeCalls = 0
        var failList = false
        var addOverride: SavedLocationResult? = null
        var selectOverride: SavedLocationResult? = null

        override suspend fun list(): SavedLocationListResult {
            if (failList) error("storage unavailable")
            return SavedLocationListResult.Locations(values.toList())
        }
        override suspend fun restore(): SavedLocationResult = SavedLocationResult.Selected(
            values.firstOrNull { it.location.id.value == selectedId },
        )

        override suspend fun add(candidate: PlaceSearchCandidate): SavedLocationResult {
            addOverride?.let { return it }
            val found = values.firstOrNull { it.location.id.value == candidate.canonicalPlaceId?.value }
            if (found != null) return SavedLocationResult.AlreadySaved(found)
            if (candidate.canonicalPlaceId == null) return SavedLocationResult.MissingCanonicalId
            val value = saved(candidate)
            values += value
            return SavedLocationResult.Added(value)
        }

        override suspend fun select(id: LocationId): SavedLocationResult {
            selectCalls++
            selectOverride?.let { return it }
            val found = values.firstOrNull { it.location.id == id } ?: return SavedLocationResult.MissingLocation
            selectedId = id.value
            return SavedLocationResult.Selected(found)
        }

        override suspend fun remove(id: LocationId): SavedLocationResult {
            removeCalls++
            val removed = values.removeAll { it.location.id == id }
            if (!removed) return SavedLocationResult.NoMutation
            if (selectedId == id.value) selectedId = null
            return SavedLocationResult.Removed
        }
    }

    companion object {
        private fun candidate(name: String, id: Long) = PlaceSearchCandidate(
            name = name,
            countryName = "United States",
            countryCode = "US",
            administrativeAreas = listOf("Illinois"),
            coordinates = GeoCoordinates(41.8, -87.6),
            timeZone = ZoneId.of("America/Chicago"),
            sourceReference = "fixture:$id",
            attribution = PlaceSearchAttribution(
                "Open-Meteo Geocoding API and GeoNames",
                URI("https://open-meteo.com/"),
                "GeoNames data license",
                URI("https://www.geonames.org/"),
                "Modified for display.",
            ),
            canonicalPlaceId = CanonicalPlaceId.geonames(id),
        )

        private fun saved(candidate: PlaceSearchCandidate) = SavedLocation(
            candidate.toCatalogLocation(LocationId(candidate.canonicalPlaceId!!.value)), candidate,
        )
    }
}
