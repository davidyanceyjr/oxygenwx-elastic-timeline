package com.oxygen.weather.timeline.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
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
fun LocationSelectorPanel(
    controller: LocationSelectorController,
    onDismiss: () -> Unit,
) {
    val state by controller.state.collectAsState()
    val savedLocations = state.saved
    val keyboard = LocalSoftwareKeyboardController.current
    val dismiss = {
        controller.close()
        onDismiss()
    }
    BackHandler(onBack = dismiss)
    LaunchedEffect(controller) { controller.open() }

    Dialog(
        onDismissRequest = dismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = true),
    ) {
        MaterialTheme(colorScheme = selectorColorScheme()) {
          Box(Modifier.fillMaxSize()) {
            Box(Modifier.matchParentSize().clickable(onClick = dismiss))
            Surface(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp, vertical = 10.dp).heightIn(max = 760.dp),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
            ) {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 16.dp)) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Spacer(Modifier.size(width = 48.dp, height = 5.dp).background(
                        MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(3.dp),
                    ))
                }
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Locations", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Text("Search and choose a place manually", style = MaterialTheme.typography.bodySmall)
                    }
                    TextButton(onClick = dismiss, modifier = Modifier.semantics {
                        contentDescription = "Close locations"
                    }) { Text("Close") }
                }
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = state.query,
                        onValueChange = controller::editQuery,
                        modifier = Modifier.weight(1f),
                        label = { Text("Search places") },
                        singleLine = true,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = {
                            keyboard?.hide()
                            controller.submitSearch()
                        }),
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        keyboard?.hide()
                        controller.submitSearch()
                    }, modifier = Modifier.semantics { contentDescription = "Search places" }) { Text("Search") }
                }
                Spacer(Modifier.height(14.dp))
                SectionTitle("Search results")
                when (val search = state.search) {
                    LocationSearchState.Idle -> Text("Enter a place and tap Search.", style = MaterialTheme.typography.bodyMedium)
                    is LocationSearchState.Loading -> Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(12.dp))
                        Text("Searching for ${search.query}…", Modifier.semantics { liveRegion = LiveRegionMode.Polite })
                    }
                    is LocationSearchState.Matches -> search.candidates.forEachIndexed { index, candidate ->
                        key(candidate.canonicalPlaceId?.value ?: "${candidate.coordinates.latitudeDegrees},${candidate.coordinates.longitudeDegrees}") {
                            val isSaved = savedLocations.orEmpty().any {
                                it.location.id.value == candidate.canonicalPlaceId?.value
                            }
                            CandidateCard(candidate, index, alreadySaved = isSaved, onSelect = { controller.selectCandidate(candidate) })
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    is LocationSearchState.NoMatches -> Text("No places found for “${search.query}”. Edit the query and search again.")
                    is LocationSearchState.Failed -> Column {
                        Text("Search failed for “${search.query}”. ${search.reason}")
                        TextButton(onClick = controller::retrySearch) { Text("Retry search") }
                    }
                }

                state.message?.let {
                    Spacer(Modifier.height(10.dp))
                    Text(it, color = if (state.messageIsError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.semantics {
                        liveRegion = LiveRegionMode.Polite
                    })
                    if (state.messageIsError || savedLocations == null) {
                        TextButton(onClick = controller::refreshSnapshot) { Text("Retry saved locations") }
                    }
                }

                Spacer(Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SectionTitle("Saved locations", Modifier.weight(1f))
                    if (state.savedLoading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                }
                when {
                    savedLocations == null && state.savedLoading -> Text("Loading saved locations…")
                    savedLocations == null -> Text("Saved locations could not be loaded. Retry to check again.")
                    savedLocations.isEmpty() -> Text("No saved locations yet.")
                    else -> savedLocations.forEach { location ->
                        key(location.location.id.value) {
                            SavedLocationCard(
                                name = location.candidate.name,
                                context = location.candidate.displayLabel,
                                timezone = location.candidate.timeZone.id,
                                selected = location.location.id == state.selectedId,
                                onSelect = { controller.select(location) },
                                onRemove = { controller.requestRemoval(location) },
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("Search does not request device location. Selecting a place saves its identity and loads its forecast catalog.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            }
          }
        }
    }

    state.pendingRemoval?.let { location ->
      MaterialTheme(colorScheme = selectorColorScheme()) {
        AlertDialog(
            onDismissRequest = controller::cancelRemoval,
            title = { Text("Remove ${location.candidate.name}?") },
            text = { Text("This removes the saved location and its stored catalog data. The action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = controller::confirmRemoval,
                    modifier = Modifier.semantics { contentDescription = "Confirm removal of ${location.candidate.displayLabel}" }) {
                    Text("Remove")
                }
            },
            dismissButton = { TextButton(onClick = controller::cancelRemoval) { Text("Cancel") } },
        )
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
    Text("Source: ${value.sourceName} · ${value.sourceUrl}", style = MaterialTheme.typography.labelSmall)
    Text("License: ${value.licenseName} · ${value.licenseUrl}", style = MaterialTheme.typography.labelSmall)
    value.changesMadeNotice?.let { Text(it, style = MaterialTheme.typography.labelSmall) }
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
