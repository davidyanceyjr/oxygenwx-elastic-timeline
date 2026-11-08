package com.oxygen.weather.timeline.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oxygen.weather.timeline.data.catalog.PlaceSearchAttribution
import com.oxygen.weather.timeline.data.catalog.PlaceSearchCandidate

@Composable
fun FixtureLocationSelectorPanel(
    controller: LocationSelectorController,
    onDismiss: () -> Unit,
) {
    val state by controller.state.collectAsState()
    val savedLocations = state.saved
    val keyboard = LocalSoftwareKeyboardController.current
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

    Dialog(
        onDismissRequest = handleBack,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
            dismissOnClickOutside = false,
        ),
    ) {
        MaterialTheme(colorScheme = selectorColorScheme()) {
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
                    Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.weight(0.8f))
                    Text("Locations", color = Color.White,
                        style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(20.dp))
                    val current = savedLocations?.firstOrNull { it.location.id == state.selectedId }
                    Text(
                        current?.candidate?.name ?: "Choose a place",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    current?.let {
                        Text(it.candidate.displayLabel, color = Color(0xFFE1ECF7),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                    Spacer(Modifier.weight(1.2f))
                    Row(
                        modifier = Modifier.fillMaxWidth().background(
                            Color(0xAA344960), RoundedCornerShape(40.dp),
                        ).padding(start = 12.dp, end = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = state.query,
                            onValueChange = controller::editQuery,
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Search places") },
                            singleLine = true,
                            enabled = pendingRemoval == null,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = {
                                keyboard?.hide()
                                controller.submitSearch()
                            }),
                        )
                        TextButton(enabled = pendingRemoval == null, onClick = {
                            keyboard?.hide()
                            controller.submitSearch()
                        }, modifier = Modifier.semantics { contentDescription = "Search places" }) {
                            Text("Search", color = Color.White)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    if (pendingRemoval != null) {
                        Column(
                            Modifier.fillMaxWidth().heightIn(min = 150.dp, max = 230.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                "Remove ${pendingRemoval.candidate.name}?",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                pendingRemoval.candidate.displayLabel,
                                color = Color(0xFFE1ECF7),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                "Stored catalog data will also be removed.",
                                color = Color(0xFFE1ECF7),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                TextButton(onClick = controller::cancelRemoval) {
                                    Text("Cancel", color = Color.White)
                                }
                                TextButton(onClick = controller::confirmRemoval) {
                                    Text("Remove", color = Color(0xFFFFB4A6))
                                }
                            }
                        }
                    } else {
                        Column(
                            Modifier.fillMaxWidth().heightIn(min = 92.dp, max = 230.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            when (val search = state.search) {
                                LocationSearchState.Idle -> Text("Enter a place and tap Search", color = Color.White)
                                is LocationSearchState.Loading -> Text(
                                    "Searching for ${search.query}…",
                                    color = Color.White,
                                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                                )
                                is LocationSearchState.Matches -> {
                                    var detailsIndex by remember(search) { mutableStateOf<Int?>(null) }
                                    LazyRow(
                                        modifier = Modifier.fillMaxWidth().heightIn(min = 78.dp),
                                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    ) {
                                        items(search.candidates.size) { index ->
                                            val candidate = search.candidates[index]
                                            val alreadySaved = savedLocations.orEmpty().any {
                                                it.location.id.value == candidate.canonicalPlaceId?.value
                                            }
                                            Column(
                                                Modifier.width(196.dp).clickable { controller.selectCandidate(candidate) }
                                                    .semantics {
                                                        contentDescription = "Choose search result ${index + 1}, ${candidate.displayLabel}, ${if (alreadySaved) "already saved" else "saves if needed"}"
                                                    },
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                Box(Modifier.width(1.dp).height(9.dp).background(Color(0xFFDAE8F4)))
                                                Text(candidate.name.take(3).uppercase(), color = Color.White, fontWeight = FontWeight.Bold)
                                                Text(candidate.displayLabel, color = Color.White,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    maxLines = 3, overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center)
                                                TextButton(onClick = { detailsIndex = if (detailsIndex == index) null else index }) {
                                                    Text(if (detailsIndex == index) "Hide details" else "Details")
                                                }
                                            }
                                        }
                                    }
                                    detailsIndex?.let { index ->
                                        val candidate = search.candidates.getOrNull(index)
                                        candidate?.let {
                                            Text(it.displayLabel, color = Color.White,
                                                style = MaterialTheme.typography.labelSmall,
                                                maxLines = 2, overflow = TextOverflow.Ellipsis)
                                            Text("${it.timeZone.id} · ${it.coordinates.latitudeDegrees}, ${it.coordinates.longitudeDegrees}",
                                                color = Color.White, style = MaterialTheme.typography.labelSmall)
                                            Attribution(it.attribution)
                                        }
                                    }
                                }
                                is LocationSearchState.NoMatches -> Text("No places found for “${search.query}”.", color = Color.White)
                                is LocationSearchState.Failed -> {
                                    Text("Search failed for “${search.query}”. ${search.reason}", color = Color.White)
                                    TextButton(onClick = controller::retrySearch) { Text("Retry search") }
                                }
                            }
                        }
                    }
                    state.message?.let {
                        Text(it, color = if (state.messageIsError) Color(0xFFFFB4A6) else Color.White,
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
                    }
                    HorizontalDivider(color = Color(0xFFDAE8F4))
                    Spacer(Modifier.height(8.dp))
                    when {
                        savedLocations == null && state.savedLoading -> Text("Loading saved places…", color = Color.White)
                        savedLocations == null -> {
                            Text("Saved places unavailable", color = Color.White)
                            TextButton(onClick = controller::refreshSnapshot) { Text("Retry saved places") }
                        }
                        savedLocations.isEmpty() -> Text("No saved places yet", color = Color.White)
                        else -> LazyRow(
                            modifier = Modifier.fillMaxWidth().heightIn(min = 96.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            userScrollEnabled = pendingRemoval == null,
                        ) {
                            items(savedLocations, key = { it.location.id.value }) { location ->
                                val selected = location.location.id == state.selectedId
                                Column(
                                    Modifier.width(148.dp)
                                        .clickable(enabled = pendingRemoval == null) { controller.select(location) }
                                        .semantics {
                                            contentDescription = "${location.candidate.displayLabel}, ${if (selected) "current location" else "saved location"}"
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Box(Modifier.width(1.dp).height(9.dp).background(Color(0xFFDAE8F4)))
                                    Text(location.candidate.name.take(3).uppercase(),
                                        color = if (selected) Color(0xFF72F274) else Color.White,
                                        fontWeight = FontWeight.Bold)
                                    Text(location.candidate.displayLabel,
                                        color = if (selected) Color(0xFF72F274) else Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 2, overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center)
                                    TextButton(
                                        enabled = pendingRemoval == null,
                                        onClick = {
                                            keyboard?.hide()
                                            controller.requestRemoval(location)
                                        },
                                    ) {
                                        Text("Remove", maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                    if (state.savedLoading && savedLocations != null) {
                        Text("Refreshing saved places…", color = Color.White)
                    }
                    Spacer(Modifier.height(14.dp))
                }
            }
        }
    }

}

@Composable
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

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(text.uppercase(), modifier, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
}

@Composable
private fun CandidateCard(candidate: PlaceSearchCandidate, index: Int, alreadySaved: Boolean, onSelect: () -> Unit) {
    var attributionExpanded by remember(candidate.canonicalPlaceId) { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth().semantics {
            contentDescription = "Search result ${index + 1}: ${candidate.displayLabel}, timezone ${candidate.timeZone.id}, " +
                "coordinates ${candidate.coordinates.latitudeDegrees}, ${candidate.coordinates.longitudeDegrees}"
        },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(candidate.displayLabel, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text("${candidate.timeZone.id} · ${candidate.coordinates.latitudeDegrees}, ${candidate.coordinates.longitudeDegrees}",
                        style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = onSelect,
                    modifier = Modifier.semantics {
                        contentDescription = "Select ${candidate.displayLabel}${if (alreadySaved) ", already saved" else ", saves if needed"}"
                    },
                ) { Text(if (alreadySaved) "Select" else "Choose") }
            }
            TextButton(
                onClick = { attributionExpanded = !attributionExpanded },
                modifier = Modifier.semantics {
                    contentDescription = if (attributionExpanded) "Hide source and license details" else "Show source and license details"
                },
            ) { Text(if (attributionExpanded) "Hide attribution" else "Attribution and license") }
            if (attributionExpanded) Attribution(candidate.attribution)
        }
    }
}

@Composable
private fun Attribution(value: PlaceSearchAttribution) {
    Spacer(Modifier.height(5.dp))
    Text("Source: ${value.sourceName} · ${value.sourceUrl}", color = Color.White, style = MaterialTheme.typography.labelSmall)
    Text("License: ${value.licenseName} · ${value.licenseUrl}", color = Color.White, style = MaterialTheme.typography.labelSmall)
    value.changesMadeNotice?.let { Text(it, color = Color.White, style = MaterialTheme.typography.labelSmall) }
}

@Composable
private fun SavedLocationCard(
    name: String,
    context: String,
    timezone: String,
    selected: Boolean,
    onSelect: () -> Unit,
    onRemove: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth().semantics {
            contentDescription = "$context. Timezone $timezone. ${if (selected) "Currently selected" else "Not selected"}."
        },
        shape = RoundedCornerShape(12.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(name, fontWeight = FontWeight.SemiBold)
            Text(context, style = MaterialTheme.typography.bodySmall)
            Text(if (selected) "Selected · $timezone" else "Saved · $timezone", style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (!selected) OutlinedButton(onClick = onSelect, modifier = Modifier.semantics {
                    contentDescription = "Select $context"
                }) { Text("Select") }
                TextButton(onClick = onRemove, modifier = Modifier.semantics {
                    contentDescription = "Remove $context"
                }) { Text("Remove") }
            }
        }
    }
}
