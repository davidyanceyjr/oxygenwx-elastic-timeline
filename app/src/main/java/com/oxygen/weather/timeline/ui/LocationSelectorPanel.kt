package com.oxygen.weather.timeline.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.presentation.PresentedMeasurement
import java.time.Instant
import java.util.Locale
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun LocationSelectorPanel(
    controller: LocationSelectorController,
    selectedInstant: Instant? = null,
    currentLocationName: String? = null,
    forecastError: String? = null,
    onRetryForecast: (() -> Unit)? = null,
    loadSavedTemperature: suspend (SavedLocation, Instant) -> PresentedMeasurement? = { _, _ -> null },
    onDismiss: () -> Unit,
) {
    val state by controller.state.collectAsState()
    val savedLocations = state.saved
    val pendingRemoval = state.pendingRemoval
    val dismiss = {
        controller.close()
        onDismiss()
    }
    val handleBack = {
        if (pendingRemoval != null) controller.cancelRemoval() else dismiss()
    }
    BackHandler(onBack = handleBack)
    LaunchedEffect(controller) { controller.open() }

    var temperatureByLocation by remember { mutableStateOf<Map<LocationId, PresentedMeasurement?>>(emptyMap()) }
    var temperaturesLoading by remember { mutableStateOf(false) }
    LaunchedEffect(savedLocations, selectedInstant, loadSavedTemperature) {
        temperatureByLocation = emptyMap()
        if (savedLocations == null) {
            temperaturesLoading = false
        } else if (selectedInstant == null) {
            temperatureByLocation = savedLocations.associate { it.location.id to null }
            temperaturesLoading = false
        } else {
            temperaturesLoading = true
            temperatureByLocation = coroutineScope {
                savedLocations.map { saved ->
                    async(Dispatchers.IO) {
                        val measurement = try {
                            loadSavedTemperature(saved, selectedInstant)
                        } catch (cancelled: kotlinx.coroutines.CancellationException) {
                            throw cancelled
                        } catch (_: Exception) {
                            null
                        }
                        saved.location.id to measurement
                    }
                }.awaitAll().toMap()
            }
            temperaturesLoading = false
        }
    }

    val currentSaved = savedLocations?.firstOrNull { it.location.id == state.selectedId }
    val currentName = currentSaved?.candidate?.name ?: currentLocationName
    val resultMode = state.search != LocationSearchState.Idle
    val railPlaces = when (val search = state.search) {
        is LocationSearchState.Matches -> search.candidates.mapIndexed { index, candidate ->
            LocationRailPlace(
                key = "result:${candidate.canonicalPlaceId?.value ?: "unidentified-$index"}",
                candidate = candidate,
                result = true,
                selected = false,
                temperature = null,
                temperatureLoading = false,
            )
        }
        else -> if (resultMode) emptyList() else savedLocations.orEmpty().map { saved ->
            LocationRailPlace(
                key = "saved:${saved.location.id.value}",
                candidate = saved.candidate,
                result = false,
                selected = saved.location.id == state.selectedId,
                temperature = temperatureByLocation[saved.location.id],
                temperatureLoading = temperaturesLoading || saved.location.id !in temperatureByLocation,
            )
        }
    }
    val savedFocusKey = state.selectedId?.let { "saved:${it.value}" }
    val modeKey = when (state.search) {
        LocationSearchState.Idle -> "saved"
        is LocationSearchState.Loading -> "results:loading"
        is LocationSearchState.Matches -> "results:matches"
        is LocationSearchState.NoMatches -> "results:no-matches"
        is LocationSearchState.Failed -> "results:failed"
    }
    var focusedKey by remember { mutableStateOf<String?>(null) }
    val railKeys = railPlaces.map { it.key }
    LaunchedEffect(modeKey, railKeys, savedFocusKey) {
        focusedKey = if (!resultMode && savedFocusKey != null && savedFocusKey in railKeys) savedFocusKey
            else railPlaces.firstOrNull()?.key
    }
    val focused = railPlaces.firstOrNull { it.key == focusedKey }
        ?: railPlaces.firstOrNull()
    var attributionExpanded by remember(focused?.key) { mutableStateOf(false) }
    val focusedSaved = focused?.takeIf { !it.result }
        ?.let { place -> savedLocations.orEmpty().firstOrNull { it.location.id.value == place.candidate.canonicalPlaceId?.value } }

    Dialog(
        onDismissRequest = handleBack,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
            dismissOnClickOutside = false,
        ),
    ) {
        MaterialTheme(colorScheme = selectorColorScheme()) {
            val keyboard = LocalSoftwareKeyboardController.current
            val searchActionFocusRequester = remember { FocusRequester() }
            val submitSearch = {
                controller.submitSearch()
                searchActionFocusRequester.requestFocus()
                keyboard?.hide()
            }
            Box(Modifier.fillMaxSize().background(Color(0xFF102743))) {
                Image(
                    painter = painterResource(com.oxygen.weather.timeline.R.drawable.sg2_city_architecture),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(
                    0f to Color(0xFF112948),
                    0.42f to Color(0x88122946),
                    0.70f to Color(0x99203856),
                    1f to Color(0xEE0A1D35),
                )))
                Canvas(Modifier.fillMaxWidth().height(260.dp).align(Alignment.TopCenter)) {
                    val cloud = Path().apply {
                        moveTo(size.width * 0.18f, size.height * 0.75f)
                        cubicTo(size.width * 0.23f, size.height * 0.52f, size.width * 0.33f, size.height * 0.51f, size.width * 0.39f, size.height * 0.66f)
                        cubicTo(size.width * 0.46f, size.height * 0.30f, size.width * 0.59f, size.height * 0.25f, size.width * 0.65f, size.height * 0.58f)
                        cubicTo(size.width * 0.74f, size.height * 0.38f, size.width * 0.84f, size.height * 0.47f, size.width * 0.89f, size.height * 0.70f)
                        cubicTo(size.width * 0.92f, size.height * 0.72f, size.width * 0.93f, size.height * 0.77f, size.width * 0.93f, size.height * 0.79f)
                        quadraticTo(size.width * 0.56f, size.height * 0.98f, size.width * 0.18f, size.height * 0.79f)
                        close()
                    }
                    drawPath(cloud, Color(0x263D5A7A))
                }

                Column(
                    Modifier.fillMaxSize().statusBarsPadding().imePadding().navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.weight(0.75f))
                    Text("Locations", color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("⌖", color = Color(0xFFE1ECF7), fontSize = 17.sp)
                        Spacer(Modifier.width(5.dp))
                        Text(currentName ?: "Choose a place", color = Color.White,
                            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium,
                            maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        focused?.candidate?.name ?: currentName ?: "Search for a place",
                        color = if (focused?.result == true) GOLD else Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    focused?.let { place ->
                        Text(
                            fullPlaceContext(place.candidate),
                            color = Color(0xFFE1ECF7),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            "${place.candidate.timeZone.id} · ${place.candidate.coordinates.latitudeDegrees}, ${place.candidate.coordinates.longitudeDegrees}",
                            color = Color(0xFFD1DFEA),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    if (pendingRemoval != null) {
                        Spacer(Modifier.height(10.dp))
                        Text("Remove ${pendingRemoval.candidate.name}?", color = Color.White,
                            style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                        Text("Stored catalog data will also be removed.", color = Color(0xFFE1ECF7),
                            style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                            TextButton(onClick = controller::cancelRemoval) { Text("Cancel", color = Color.White) }
                            TextButton(onClick = controller::confirmRemoval) { Text("Remove", color = Color(0xFFFFB4A6)) }
                        }
                    } else {
                        Row(
                            Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            when {
                                focused?.result == true -> {
                                    Button(onClick = {
                                        keyboard?.hide()
                                        controller.selectCandidate(focused.candidate)
                                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF82D8C9), contentColor = Color(0xFF062F32)), modifier = Modifier.semantics {
                                        contentDescription = "Save ${focused.candidate.displayLabel} and make it the selected location"
                                    }) { Text("Save", color = Color(0xFF062F32)) }
                                    OutlinedButton(onClick = {
                                        keyboard?.hide()
                                        controller.viewCandidate(focused.candidate)
                                    }, modifier = Modifier.semantics {
                                        contentDescription = "View ${focused.candidate.displayLabel} temporarily"
                                    }) { Text("View", color = Color.White) }
                                }
                                focusedSaved != null -> {
                                    OutlinedButton(onClick = {
                                        keyboard?.hide()
                                        controller.requestRemoval(focusedSaved)
                                    }, modifier = Modifier.semantics {
                                        contentDescription = "Remove ${focused.candidate.displayLabel}"
                                    }) { Text("Remove", color = Color.White) }
                                    Button(onClick = {
                                        keyboard?.hide()
                                        controller.select(focusedSaved)
                                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF82D8C9), contentColor = Color(0xFF062F32)), modifier = Modifier.semantics {
                                        contentDescription = "View ${focused.candidate.displayLabel} and make it the selected location"
                                    }) { Text("View", color = Color(0xFF062F32)) }
                                }
                            }
                        }
                        if (focused?.result == true) {
                            TextButton(onClick = { attributionExpanded = !attributionExpanded }) {
                                Text(if (attributionExpanded) "Hide attribution" else "Attribution and license",
                                    color = Color(0xFFE1ECF7), style = MaterialTheme.typography.labelMedium)
                            }
                            if (attributionExpanded) Attribution(focused.candidate.attribution)
                        }
                    }
                    if (forecastError != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                forecastError,
                                color = Color(0xFFFFB4A6),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f).semantics {
                                    liveRegion = LiveRegionMode.Polite
                                },
                            )
                            if (onRetryForecast != null) {
                                TextButton(onClick = onRetryForecast) {
                                    Text("Retry forecast", color = Color.White)
                                }
                            }
                        }
                    } else state.message?.let { message ->
                        Text(message,
                            color = if (state.messageIsError) Color(0xFFFFB4A6) else Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                        )
                    }

                    Spacer(Modifier.weight(0.8f))
                    Row(
                        modifier = Modifier.fillMaxWidth().background(
                            Color(0xAA344960), RoundedCornerShape(40.dp),
                        ).padding(start = 12.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = state.query,
                            onValueChange = controller::editQuery,
                            modifier = Modifier.weight(1f).testTag("locationSelectorQuery"),
                            placeholder = { Text("Search places") },
                            singleLine = true,
                            enabled = pendingRemoval == null,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { submitSearch() }),
                        )
                        if (state.query.isNotEmpty()) {
                            TextButton(onClick = { controller.editQuery("") }, modifier = Modifier.semantics {
                                contentDescription = "Clear search"
                            }) { Text("×", color = Color.White, fontSize = 21.sp) }
                        }
                        TextButton(enabled = pendingRemoval == null, onClick = {
                            submitSearch()
                        }, modifier = Modifier
                            .focusRequester(searchActionFocusRequester)
                            .focusable(enabled = pendingRemoval == null)
                            .semantics { contentDescription = "Search places" }) {
                            Text("Search", color = Color.White)
                        }
                    }
                    Spacer(Modifier.height(5.dp))
                    SearchStatus(state.search, state.query, controller::retrySearch)
                    savedLocations?.let { locations ->
                        when {
                            state.search == LocationSearchState.Idle && state.savedLoading && locations.isEmpty() -> Text("Loading saved places…", color = Color.White,
                                style = MaterialTheme.typography.labelSmall)
                            state.search == LocationSearchState.Idle && state.savedLoading -> Text("Refreshing saved places…", color = Color.White,
                                style = MaterialTheme.typography.labelSmall)
                            state.search == LocationSearchState.Idle && locations.isEmpty() -> Text("No saved places yet", color = Color.White,
                                style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    if (savedLocations == null && state.search == LocationSearchState.Idle) {
                        if (state.savedLoading) Text("Loading saved places…", color = Color.White,
                            style = MaterialTheme.typography.labelSmall)
                        else Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Saved places unavailable", color = Color.White,
                                style = MaterialTheme.typography.labelSmall)
                            TextButton(onClick = controller::refreshSnapshot) { Text("Retry") }
                        }
                    }
                    LocationRail(
                        places = railPlaces,
                        modeKey = modeKey,
                        selectedSavedKey = savedFocusKey,
                        focusedKey = focusedKey,
                        temperaturesLoading = temperaturesLoading,
                        onFocus = { place -> focusedKey = place.key },
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchStatus(search: LocationSearchState, editedQuery: String, onRetry: () -> Unit) {
    when (search) {
        LocationSearchState.Idle -> Unit
        is LocationSearchState.Loading -> Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(Modifier.size(14.dp), color = Color.White, strokeWidth = 2.dp)
            Spacer(Modifier.width(8.dp))
            Text("Searching for ${search.query}…", color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
        }
        is LocationSearchState.Matches -> Text(
            if (editedQuery.trim() == search.query) "${search.candidates.size} results for ${search.query}"
            else "Showing results for ${search.query}; submit to update",
            color = Color.White, style = MaterialTheme.typography.labelSmall,
        )
        is LocationSearchState.NoMatches -> Text("No places found for “${search.query}”.", color = Color.White,
            style = MaterialTheme.typography.labelSmall)
        is LocationSearchState.Failed -> Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Search failed for “${search.query}”. ${search.reason}", color = Color.White,
                style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(1f))
            TextButton(onClick = onRetry) { Text("Retry", color = Color.White) }
        }
    }
}

private data class LocationRailPlace(
    val key: String,
    val candidate: PlaceSearchCandidate,
    val result: Boolean,
    val selected: Boolean,
    val temperature: PresentedMeasurement?,
    val temperatureLoading: Boolean,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LocationRail(
    places: List<LocationRailPlace>,
    modeKey: String,
    selectedSavedKey: String?,
    focusedKey: String?,
    temperaturesLoading: Boolean,
    onFocus: (LocationRailPlace) -> Unit,
) {
    val keys = places.map { it.key }
        val initialPage = initialLocationRailPage(keys, selectedSavedKey)
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { places.size })
    val scope = rememberCoroutineScope()
    LaunchedEffect(modeKey, keys, selectedSavedKey) {
        if (places.isEmpty()) return@LaunchedEffect
        val targetPage = if (modeKey == "saved") initialLocationRailPage(keys, selectedSavedKey) else 0
        pagerState.scrollToPage(targetPage)
        onFocus(places[targetPage])
    }
    LaunchedEffect(pagerState, keys) {
        snapshotFlow { pagerState.settledPage }.drop(1).collect { page ->
            places.getOrNull(page)?.let(onFocus)
        }
    }

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val itemWidth = 76.dp
        val sidePadding = ((maxWidth - itemWidth) / 2).coerceAtLeast(0.dp)
        Box(Modifier.fillMaxWidth().height(148.dp)) {
            if (places.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    pageSize = PageSize.Fixed(itemWidth),
                    contentPadding = PaddingValues(horizontal = sidePadding),
                    pageSpacing = 0.dp,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().testTag("locationSelectorRail"),
                ) { page ->
                    val place = places[page]
                    val isFocused = place.key == focusedKey
                    val color = when {
                        place.selected -> CURRENT_GREEN
                        place.result && isFocused -> GOLD
                        else -> Color.White
                    }
                    val measurement = place.temperature
                    val temperatureText = when {
                        place.result -> null
                        place.temperatureLoading || temperaturesLoading -> "…"
                        measurement != null -> "${measurement.displayValue.roundToInt()}°"
                        else -> "—"
                    }
                    val spokenTemperature = when {
                        place.result -> "no temperature in search results"
                        place.temperatureLoading || temperaturesLoading -> "temperature loading"
                        measurement != null -> "temperature ${measurement.displayValue.roundToInt()} degrees ${measurement.displayUnit}"
                        else -> "temperature unavailable at the selected forecast time"
                    }
                    Column(
                        Modifier.fillMaxSize().clickable(role = Role.Button) {
                            onFocus(place)
                            scope.launch { pagerState.animateScrollToPage(page) }
                        }.semantics(mergeDescendants = true) {
                            contentDescription = buildString {
                                append(place.candidate.displayLabel)
                                append(". Time zone ${place.candidate.timeZone.id}.")
                                val canonicalId = place.candidate.canonicalPlaceId
                                if (canonicalId != null) append(" Canonical place ID ${canonicalId.value}.")
                                else append(" Canonical place ID unavailable.")
                                append(" $spokenTemperature.")
                            }
                            stateDescription = when {
                                place.selected -> "Current saved location"
                                place.result && isFocused -> "Focused search result"
                                isFocused -> "Focused saved location"
                                else -> "Location"
                            }
                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(Modifier.height(13.dp))
                        Text(
                            designation(place.candidate.name),
                            color = color,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                        )
                        Text(
                            shortRegion(place.candidate),
                            color = color.copy(alpha = 0.86f),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(17.dp))
                        temperatureText?.let {
                            Text(it, color = color, fontSize = 21.sp, fontWeight = FontWeight.SemiBold,
                                maxLines = 1, modifier = Modifier.semantics {
                                    contentDescription = spokenTemperature
                                })
                        }
                    }
                }
            }
            Canvas(Modifier.fillMaxSize()) {
                val railY = 73.dp.toPx()
                drawRect(Brush.horizontalGradient(
                    0f to Color(0xF2102743),
                    0.14f to Color.Transparent,
                    0.86f to Color.Transparent,
                    1f to Color(0xF2102743),
                ))
                drawLine(Color(0x66D7EBF7), Offset(0f, railY), Offset(size.width, railY), 1.dp.toPx())
                drawLine(GOLD.copy(alpha = 0.86f), Offset(size.width / 2f, railY - 12.dp.toPx()),
                    Offset(size.width / 2f, railY + 13.dp.toPx()), 2.dp.toPx())
                drawCircle(GOLD.copy(alpha = 0.92f), 4.dp.toPx(), Offset(size.width / 2f, railY))
            }
            val focused = places.firstOrNull { it.key == focusedKey }
                ?: places.getOrNull(pagerState.currentPage)
            Box(
                Modifier.align(Alignment.BottomCenter).padding(bottom = 2.dp)
                    .background(Color(0xE51A2A39), CircleShape)
                    .padding(horizontal = 18.dp, vertical = 3.dp),
            ) {
                Text(
                    focused?.let { "${designation(it.candidate.name)} · ${shortRegion(it.candidate)}" } ?: "Places",
                    color = GOLD, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

internal fun initialLocationRailPage(keys: List<String>, selectedSavedKey: String?): Int =
    keys.indexOf(selectedSavedKey).coerceAtLeast(0)

private fun designation(name: String): String = name.take(3).uppercase(Locale.US)

private fun shortRegion(candidate: PlaceSearchCandidate): String = buildList {
    candidate.administrativeAreas.firstOrNull()?.let(::add)
    (candidate.countryCode ?: candidate.countryName)?.let(::add)
}.joinToString(" · ")

private fun fullPlaceContext(candidate: PlaceSearchCandidate): String = buildString {
    append(candidate.displayLabel)
    append(" · ${candidate.timeZone.id}")
}

@Composable
private fun Attribution(value: PlaceSearchAttribution) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Source: ${value.sourceName} · ${value.sourceUrl}", color = Color.White,
            style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center,
            maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text("License: ${value.licenseName} · ${value.licenseUrl}", color = Color.White,
            style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center,
            maxLines = 2, overflow = TextOverflow.Ellipsis)
        value.changesMadeNotice?.let {
            Text(it, color = Color.White, style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

private fun selectorColorScheme() = darkColorScheme(
    primary = Color(0xFF82D8C9),
    onPrimary = Color(0xFF062F32),
    primaryContainer = Color(0xFF194B4B),
    onPrimaryContainer = Color(0xFFE2FFF8),
    secondary = Color(0xFF9DBDCE),
    surface = Color(0xFF132B3D),
    onSurface = Color(0xFFF1F6F8),
    surfaceVariant = Color(0xFF1D3A4C),
    onSurfaceVariant = Color(0xFFC0D1D9),
    outline = Color(0xFF648594),
    outlineVariant = Color(0xFF355467),
    error = Color(0xFFFFB4A6),
)

private val CURRENT_GREEN = Color(0xFF72F274)
private val GOLD = Color(0xFFFFD27E)
