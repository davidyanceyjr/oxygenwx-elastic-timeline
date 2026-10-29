package com.oxygen.weather.timeline.ui

import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.PlaceSearchResult
import com.oxygen.weather.timeline.data.catalog.PlaceSearcher
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.data.catalog.SavedLocationListResult
import com.oxygen.weather.timeline.data.catalog.SavedLocationRepository
import com.oxygen.weather.timeline.data.catalog.SavedLocationResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface LocationSearchState {
    data object Idle : LocationSearchState
    data class Loading(val query: String) : LocationSearchState
    data class Matches(val query: String, val candidates: List<PlaceSearchCandidate>) : LocationSearchState
    data class NoMatches(val query: String) : LocationSearchState
    data class Failed(val query: String, val reason: String) : LocationSearchState
}

data class LocationSelectorState(
    val query: String = "",
    val search: LocationSearchState = LocationSearchState.Idle,
    val saved: List<SavedLocation>? = null,
    val selectedId: LocationId? = null,
    val savedLoading: Boolean = false,
    val pendingRemoval: SavedLocation? = null,
    val message: String? = null,
    val messageIsError: Boolean = false,
)

/** Thin persistence boundary keeps the interaction state independently testable. */
interface LocationSelectorStore {
    suspend fun list(): SavedLocationListResult
    suspend fun restore(): SavedLocationResult
    suspend fun add(candidate: PlaceSearchCandidate): SavedLocationResult
    suspend fun select(id: LocationId): SavedLocationResult
    suspend fun remove(id: LocationId): SavedLocationResult
}

class RepositoryLocationSelectorStore(private val repository: SavedLocationRepository) : LocationSelectorStore {
    override suspend fun list() = repository.list()
    override suspend fun restore() = repository.restore()
    override suspend fun add(candidate: PlaceSearchCandidate) = repository.add(candidate)
    override suspend fun select(id: LocationId) = repository.select(id)
    override suspend fun remove(id: LocationId) = repository.remove(id)
}

/** Latest-query-wins controller for manual search and local saved-location actions. */
class LocationSelectorController(
    private val scope: CoroutineScope,
    private val searcher: PlaceSearcher,
    private val store: LocationSelectorStore,
    private val onCommittedSelection: (SavedLocation) -> Unit = {},
) {
    private val mutableState = MutableStateFlow(LocationSelectorState())
    val state: StateFlow<LocationSelectorState> = mutableState.asStateFlow()

    private var searchJob: Job? = null
    private var savedJob: Job? = null
    private var searchGeneration = 0
    private var savedGeneration = 0

    fun open() {
        refreshSnapshot()
    }

    fun close() {
        searchGeneration++
        searchJob?.cancel()
        searchJob = null
        savedGeneration++
        savedJob?.cancel()
        savedJob = null
        mutableState.value = mutableState.value.copy(search = LocationSearchState.Idle, pendingRemoval = null)
    }

    fun editQuery(value: String) {
        searchGeneration++
        searchJob?.cancel()
        searchJob = null
        mutableState.value = mutableState.value.copy(
            query = value,
            search = LocationSearchState.Idle,
            message = null,
        )
    }

    fun submitSearch() {
        val query = mutableState.value.query.trim()
        if (query.isEmpty()) {
            editQuery(mutableState.value.query)
            return
        }
        searchGeneration++
        val generation = searchGeneration
        searchJob?.cancel()
        mutableState.value = mutableState.value.copy(search = LocationSearchState.Loading(query), message = null)
        searchJob = scope.launch {
            try {
                val result = withContext(Dispatchers.IO) { searcher.search(query) }
                if (generation != searchGeneration) return@launch
                mutableState.value = mutableState.value.copy(search = when (result) {
                    is PlaceSearchResult.Matches -> LocationSearchState.Matches(query, result.candidates)
                    PlaceSearchResult.NoMatches -> LocationSearchState.NoMatches(query)
                    is PlaceSearchResult.Failure -> LocationSearchState.Failed(query, failureText(result))
                })
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                if (generation == searchGeneration) {
                    mutableState.value = mutableState.value.copy(
                        search = LocationSearchState.Failed(query, error.message ?: "Search could not be completed."),
                    )
                }
            }
        }
    }

    fun retrySearch() = submitSearch()

    fun add(candidate: PlaceSearchCandidate) {
        scope.launch {
            try {
                when (val result = withContext(Dispatchers.IO) { store.add(candidate) }) {
                    is SavedLocationResult.Added -> {
                        mutableState.value = mutableState.value.copy(message = "${result.location.candidate.name} saved.", messageIsError = false)
                        refreshSnapshot(preserveMessage = true)
                    }
                    is SavedLocationResult.AlreadySaved -> {
                        mutableState.value = mutableState.value.copy(message = "${result.location.candidate.name} is already saved.", messageIsError = false)
                        refreshSnapshot(preserveMessage = true)
                    }
                    SavedLocationResult.MissingCanonicalId -> announce("This result cannot be saved because it has no stable location identity.")
                    is SavedLocationResult.PointConflict -> announce("This location ID already has different coordinates or timezone; the saved point was kept.")
                    SavedLocationResult.MalformedRecord -> announce("Saved location data is invalid. No changes were made.")
                    else -> announce("The location could not be saved.")
                }
            } catch (cancelled: CancellationException) { throw cancelled }
            catch (error: Exception) { announce("Save failed: ${error.message ?: "storage error"}") }
        }
    }

    fun select(location: SavedLocation) {
        scope.launch {
            try {
                when (val result = withContext(Dispatchers.IO) { store.select(location.location.id) }) {
                    is SavedLocationResult.Selected -> {
                        result.location?.let(onCommittedSelection)
                        mutableState.value = mutableState.value.copy(
                            selectedId = result.location?.location?.id,
                            message = result.location?.let { "${it.candidate.name} selected. Demo forecast remains unchanged." }
                                ?: "No location is selected.",
                        )
                    }
                    SavedLocationResult.MissingLocation -> announce("That saved location is no longer available. Refresh and try again.")
                    SavedLocationResult.MalformedRecord -> announce("Saved location data is invalid. Selection was not changed.")
                    else -> announce("The location could not be selected.")
                }
            } catch (cancelled: CancellationException) { throw cancelled }
            catch (error: Exception) { announce("Selection failed: ${error.message ?: "storage error"}") }
        }
    }

    fun requestRemoval(location: SavedLocation) {
        mutableState.value = mutableState.value.copy(pendingRemoval = location, message = null)
    }

    fun cancelRemoval() {
        mutableState.value = mutableState.value.copy(pendingRemoval = null)
    }

    fun confirmRemoval() {
        val location = mutableState.value.pendingRemoval ?: return
        mutableState.value = mutableState.value.copy(pendingRemoval = null)
        scope.launch {
            try {
                when (withContext(Dispatchers.IO) { store.remove(location.location.id) }) {
                    SavedLocationResult.Removed -> {
                        mutableState.value = mutableState.value.copy(message = "${location.candidate.name} removed.", messageIsError = false)
                        refreshSnapshot(preserveMessage = true)
                    }
                    SavedLocationResult.NoMutation -> announce("The saved location was not removed. Refresh and try again.")
                    SavedLocationResult.MalformedRecord -> announce("Saved location data is invalid. No changes were made.")
                    else -> announce("The saved location could not be removed.")
                }
            } catch (cancelled: CancellationException) { throw cancelled }
            catch (error: Exception) { announce("Removal failed: ${error.message ?: "storage error"}") }
        }
    }

    fun refreshSnapshot(preserveMessage: Boolean = false) {
        savedGeneration++
        val generation = savedGeneration
        savedJob?.cancel()
        val previous = mutableState.value
        mutableState.value = previous.copy(
            savedLoading = true,
            message = if (preserveMessage) previous.message else null,
            messageIsError = if (preserveMessage) previous.messageIsError else false,
        )
        savedJob = scope.launch {
            try {
                val list = withContext(Dispatchers.IO) { store.list() }
                val selection = withContext(Dispatchers.IO) { store.restore() }
                if (generation != savedGeneration) return@launch
                if (list is SavedLocationListResult.Locations && selection is SavedLocationResult.Selected) {
                    val selected = selection.location?.location?.id
                    val validSelection = selected?.takeIf { id -> list.values.any { it.location.id == id } }
                    mutableState.value = mutableState.value.copy(
                        saved = list.values,
                        selectedId = validSelection,
                        savedLoading = false,
                    )
                } else {
                    val reason = if (list is SavedLocationListResult.MalformedRecord || selection is SavedLocationResult.MalformedRecord)
                        "Saved location data is invalid. Your last known list is still shown."
                    else "Saved locations could not be refreshed. Your last known list is still shown."
                    mutableState.value = mutableState.value.copy(savedLoading = false, message = reason, messageIsError = true)
                }
            } catch (cancelled: CancellationException) { throw cancelled }
            catch (error: Exception) {
                if (generation == savedGeneration) {
                    mutableState.value = mutableState.value.copy(
                        savedLoading = false,
                        message = "Saved locations could not be refreshed: ${error.message ?: "storage error"}",
                        messageIsError = true,
                    )
                }
            }
        }
    }

    private fun announce(message: String) {
        mutableState.value = mutableState.value.copy(message = message, messageIsError = true)
    }

    private fun failureText(failure: PlaceSearchResult.Failure): String = when (failure.reason) {
        PlaceSearchResult.Failure.Reason.INVALID_QUERY -> "Enter a longer search term and try again."
        PlaceSearchResult.Failure.Reason.TIMEOUT -> "Search timed out. Check your connection and retry."
        PlaceSearchResult.Failure.Reason.CONNECTIVITY -> "Could not connect. Check your connection and retry."
        PlaceSearchResult.Failure.Reason.HTTP,
        PlaceSearchResult.Failure.Reason.INVALID_RESPONSE,
        PlaceSearchResult.Failure.Reason.OVERSIZED_RESPONSE -> "Search service could not complete the request. Retry or edit the query."
    }
}
