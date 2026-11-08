package com.oxygen.weather.timeline.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oxygen.weather.timeline.data.catalog.CanonicalPlaceId
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.data.catalog.SavedLocationListResult
import com.oxygen.weather.timeline.data.catalog.SavedLocationResult
import com.oxygen.weather.timeline.presentation.PresentedMeasurement
import java.net.URI
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationSelectorPanelTest {
    @get:Rule val compose = createAndroidComposeRule<ComponentActivity>()

    private val selectedInstant = Instant.parse("2026-11-08T18:00:00Z")
    private val zone = ZoneId.of("America/Chicago")
    private var controllerScope: CoroutineScope? = null

    @After
    fun cancelControllerScope() {
        controllerScope?.cancel()
    }

    @Test
    fun focusKeepsSelectionAndResultViewDoesNotWriteBeforeSave() {
        val chicago = saved(candidate("Chicago", 4887398, 41.8781, -87.6298))
        val madison = saved(candidate("Madison", 5261457, 43.0748, -89.3844))
        val portland = candidate("Portland", 5746545, 45.5152, -122.6784, "Oregon")
        val store = RecordingStore(mutableListOf(chicago, madison), chicago)
        val viewed = CopyOnWriteArrayList<Pair<CatalogLocation, String>>()
        val committed = CopyOnWriteArrayList<SavedLocation>()
        val loadedInstants = CopyOnWriteArrayList<Instant>()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        controllerScope = scope
        val controller = LocationSelectorController(
            scope = scope,
            searcher = PlaceSearcher { PlaceSearchResult.Matches(listOf(portland), 0) },
            store = store,
            onCommittedSelection = committed::add,
            onTransientView = { location, name -> viewed += location to name },
            preserveSubmittedSetWhileEditing = true,
            resetSearchAfterResultSelection = true,
        )

        compose.setContent {
            LocationSelectorPanel(
                controller = controller,
                selectedInstant = selectedInstant,
                currentLocationName = "Chicago",
                loadSavedTemperature = { saved, instant ->
                    loadedInstants += instant
                    val fahrenheit = if (saved.candidate.name == "Chicago") 68.0 else 72.0
                    PresentedMeasurement((fahrenheit - 32.0) * 5.0 / 9.0, fahrenheit, "°F")
                },
                onDismiss = {},
            )
        }

        compose.waitUntil(timeoutMillis = 10_000) {
            store.listCalls.get() > 0 && loadedInstants.isNotEmpty()
        }
        compose.onNodeWithText("68°").assertIsDisplayed()

        compose.onNodeWithTag("locationSelectorRail").performTouchInput { swipeLeft() }
        compose.onNodeWithText("Madison").assertIsDisplayed()
        assertEquals(0, store.selectCalls.get())
        assertEquals(0, store.addCalls.get())
        assertEquals(0, committed.size)
        assertEquals(setOf(selectedInstant), loadedInstants.toSet())

        compose.onNodeWithText("Search places").performClick().performTextInput("Portland")
        compose.onNodeWithContentDescription("Search places").performClick()
        compose.onNodeWithText("1 results for Portland").assertIsDisplayed()
        compose.onNodeWithContentDescription("no temperature in search results", substring = true).assertExists()
        val queryField = compose.onNodeWithTag("locationSelectorQuery")
        queryField.performTextReplacement("Salem")
        assertEquals("Salem", controller.state.value.query)
        assertEquals(LocationSearchState.Matches("Portland", listOf(portland)), controller.state.value.search)
        compose.onNodeWithText("Showing results for Portland; submit to update").assertIsDisplayed()
        compose.onNodeWithText("Attribution and license").performClick()
        compose.onNodeWithText("Source: Test gazetteer", substring = true).assertIsDisplayed()

        compose.onNodeWithText("View").performClick()
        assertEquals(listOf(portland.toCatalogLocation(LocationId(portland.canonicalPlaceId!!.value)) to "Portland"), viewed)
        assertEquals(0, store.addCalls.get())
        assertEquals(0, store.selectCalls.get())
        assertEquals(0, store.removeCalls.get())
        assertEquals(0, committed.size)

        compose.onNodeWithText("Save").performClick()
        compose.waitUntil(timeoutMillis = 10_000) {
            store.addCalls.get() == 1 && store.selectCalls.get() == 1 && committed.size == 1 &&
                controller.state.value.search == LocationSearchState.Idle && controller.state.value.query.isEmpty()
        }
        assertEquals(portland.canonicalPlaceId!!.value, committed.single().location.id.value)
    }

    private fun candidate(
        name: String,
        featureId: Long,
        latitude: Double,
        longitude: Double,
        administrativeArea: String = "Illinois",
    ) = PlaceSearchCandidate(
        name = name,
        countryName = "United States",
        countryCode = "US",
        administrativeAreas = listOf(administrativeArea),
        coordinates = GeoCoordinates(latitude, longitude),
        timeZone = zone,
        sourceReference = "test:$featureId",
        attribution = PlaceSearchAttribution(
            sourceName = "Test gazetteer",
            sourceUrl = URI("https://example.test/source"),
            licenseName = "Test license",
            licenseUrl = URI("https://example.test/license"),
            changesMadeNotice = "Test attribution fixture.",
        ),
        canonicalPlaceId = CanonicalPlaceId.geonames(featureId),
    )

    private fun saved(candidate: PlaceSearchCandidate) = SavedLocation(
        candidate.toCatalogLocation(LocationId(candidate.canonicalPlaceId!!.value)), candidate,
    )

    private class RecordingStore(
        private val locations: MutableList<SavedLocation>,
        private var selected: SavedLocation?,
    ) : LocationSelectorStore {
        val listCalls = AtomicInteger()
        val addCalls = AtomicInteger()
        val selectCalls = AtomicInteger()
        val removeCalls = AtomicInteger()

        override suspend fun list(): SavedLocationListResult.Locations {
            listCalls.incrementAndGet()
            return SavedLocationListResult.Locations(locations.toList())
        }

        override suspend fun restore() = SavedLocationResult.Selected(selected)

        override suspend fun add(candidate: PlaceSearchCandidate): SavedLocationResult {
            addCalls.incrementAndGet()
            val location = SavedLocation(
                candidate.toCatalogLocation(LocationId(candidate.canonicalPlaceId!!.value)), candidate,
            )
            val existing = locations.firstOrNull { it.location.id == location.location.id }
            if (existing != null) return SavedLocationResult.AlreadySaved(existing)
            locations += location
            return SavedLocationResult.Added(location)
        }

        override suspend fun select(id: LocationId): SavedLocationResult {
            selectCalls.incrementAndGet()
            selected = locations.firstOrNull { it.location.id == id }
            return selected?.let { SavedLocationResult.Selected(it) } ?: SavedLocationResult.MissingLocation
        }

        override suspend fun remove(id: LocationId): SavedLocationResult {
            removeCalls.incrementAndGet()
            locations.removeAll { it.location.id == id }
            return SavedLocationResult.Removed
        }
    }
}
