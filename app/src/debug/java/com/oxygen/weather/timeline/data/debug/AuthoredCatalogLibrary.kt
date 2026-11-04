package com.oxygen.weather.timeline.data.debug

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorage
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogStorageNamespace
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.SavedLocation
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherPhenomenon
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolIcon
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.launch

private val PageBackground = Color(0xFFEDF2F4)
private val Ink = Color(0xFF14283C)
private val Navy = Color(0xFF132B40)
private val Teal = Color(0xFF287C83)
private val PaleTeal = Color(0xFFE2F4EF)
private val MutedText = Color(0xFF5B7182)
private val Border = Color(0xFFD2DEE4)
private val Amber = Color(0xFFFBF2DF)

private data class EventDraft(
    val start: String,
    val end: String,
    val conditions: Set<WeatherCondition> = setOf(WeatherCondition.CLEAR),
    val phenomena: Set<WeatherPhenomenon> = emptySet(),
    val measurements: Map<String, String> = emptyMap(),
    val symbolId: WeatherSymbolId? = null,
)

private data class MeasurementField(val key: String, val label: String)

private val measurementFields = listOf(
    MeasurementField("temperatureC", "Temperature · °C"),
    MeasurementField("precipitationProbabilityPercent", "Precipitation chance · %"),
    MeasurementField("thunderstormProbabilityPercent", "Thunderstorm chance · %"),
    MeasurementField("relativeHumidityPercent", "Humidity · %"),
    MeasurementField("cloudCoverPercent", "Cloud cover · %"),
    MeasurementField("windSpeedKph", "Wind speed · km/h"),
    MeasurementField("windGustKph", "Wind gust · km/h"),
    MeasurementField("windDirectionDegrees", "Wind direction · °"),
    MeasurementField("rainAmountMillimeters", "Rain amount · mm"),
    MeasurementField("totalPrecipitationMillimeters", "Total precipitation · mm"),
    MeasurementField("precipitationRateMillimetersPerHour", "Precipitation rate · mm/h"),
    MeasurementField("snowfallCentimeters", "Snowfall · cm"),
    MeasurementField("snowDepthCentimeters", "Snow depth · cm"),
    MeasurementField("surfacePressureHpa", "Surface pressure · hPa"),
    MeasurementField("meanSeaLevelPressureHpa", "Sea-level pressure · hPa"),
    MeasurementField("visibilityKm", "Visibility · km"),
)

private data class SimulatedRow(
    val itemId: String,
    val name: String,
    val location: CatalogLocation?,
    val locationLabel: String,
    val datasetId: String,
    val start: LocalDateTime?,
    val end: LocalDateTime?,
    val valid: Boolean,
)

private data class ReplayRow(
    val name: String,
    val location: CatalogLocation,
    val locationLabel: String,
    val datasetId: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
)

@Composable
internal fun AuthoredCatalogLibrary(
    activity: DebugCatalogActivity,
    locations: List<SavedLocation>,
    storage: CatalogDatasetStorage,
    onBack: () -> Unit,
    onPreview: (CatalogLocation, CatalogOrigin, String) -> Unit,
    initialPreviewError: String? = null,
) {
    val preferences = remember { activity.getSharedPreferences("debug_authored_catalog_library", 0) }
    val metadata = remember {
        DebugAuthoredCatalogMetadataStore(activity.getSharedPreferences("debug_authored_catalogs", 0))
    }
    val repository = remember { DebugAuthoredCatalogRepository(storage, metadata) }
    var itemIds by remember { mutableStateOf(preferences.getStringSet("items", emptySet()).orEmpty().toList()) }
    var selectedLocation by remember(locations) { mutableStateOf(locations.firstOrNull()?.location) }
    var editingItem by remember { mutableStateOf<String?>(null) }
    var name by remember { mutableStateOf("") }
    var envelopeStart by remember { mutableStateOf("") }
    var envelopeEnd by remember { mutableStateOf("") }
    val events = remember { mutableStateListOf<EventDraft>() }
    var error by remember { mutableStateOf<String?>(null) }
    var working by remember { mutableStateOf(false) }
    var simulatedRows by remember { mutableStateOf<List<SimulatedRow>>(emptyList()) }
    var replayRows by remember { mutableStateOf<List<ReplayRow>>(emptyList()) }
    var replayRefresh by remember { mutableStateOf(0) }

    LaunchedEffect(locations, itemIds, replayRefresh) {
        val savedRows = mutableListOf<SimulatedRow>()
        itemIds.forEach { item ->
            val locationId = preferences.getString("location:$item", null)
            val locationRow = locations.firstOrNull { it.location.id.value == locationId }
            val location = locationRow?.location
            val dataset = preferences.getString("dataset:$item", "").orEmpty()
            val displayName = preferences.getString("name:$item", "Saved catalog") ?: "Saved catalog"
            val definition = if (location != null && dataset.isNotBlank()) {
                runCatching { repository.reopen(location, DatasetId(dataset)) }.getOrNull()?.first
            } else null
            savedRows += SimulatedRow(
                itemId = item,
                name = definition?.name ?: displayName,
                location = location,
                locationLabel = locationRow?.let { "${it.candidate.name} · ${it.location.timeZone.id}" }
                    ?: "Saved location unavailable",
                datasetId = dataset,
                start = definition?.startLocal,
                end = definition?.endLocalExclusive,
                valid = definition != null,
            )
        }
        simulatedRows = savedRows

        val savedReplays = mutableListOf<ReplayRow>()
        locations.forEach { saved ->
            storage.history(saved.location.id, CatalogStorageNamespace.REPLAY).forEach { identity ->
                val opened = runCatching {
                    repository.reopenReplay(saved.location, DatasetId(identity.datasetId))
                }.getOrNull()
                if (opened != null) {
                    val definition = opened.first
                    savedReplays += ReplayRow(
                        name = definition.name,
                        location = saved.location,
                        locationLabel = "${saved.candidate.name} · ${saved.location.timeZone.id}",
                        datasetId = identity.datasetId,
                        start = definition.startLocal,
                        end = definition.endLocalExclusive,
                    )
                }
            }
        }
        replayRows = savedReplays
    }

    fun beginNew() {
        val location = selectedLocation
        if (location == null) {
            error = "Add a saved location before building a simulated catalog."
            return
        }
        editingItem = "new:${UUID.randomUUID()}"
        name = ""
        val date = Instant.now().atZone(location.timeZone).toLocalDate()
        envelopeStart = date.atStartOfDay().toString()
        envelopeEnd = date.plusDays(1).atStartOfDay().toString()
        events.clear()
        events += EventDraft(date.atTime(12, 0).toString(), date.atTime(13, 0).toString())
        error = null
    }

    fun beginEdit(item: String) {
        val locationId = preferences.getString("location:$item", null) ?: run {
            error = "The saved location is unavailable. This catalog cannot be edited."
            return
        }
        val locationRow = locations.firstOrNull { it.location.id.value == locationId } ?: run {
            error = "The saved location is unavailable. This catalog cannot be edited."
            return
        }
        val dataset = preferences.getString("dataset:$item", null) ?: return
        activity.lifecycleScope.launch {
            val opened = repository.reopen(locationRow.location, DatasetId(dataset))
            if (opened == null) {
                error = "Saved catalog details are missing or inconsistent; this item cannot be edited."
                return@launch
            }
            val definition = opened.first
            selectedLocation = locationRow.location
            editingItem = item
            name = definition.name
            envelopeStart = definition.startLocal.toString()
            envelopeEnd = definition.endLocalExclusive.toString()
            events.clear()
            events.addAll(definition.events.map { event ->
                EventDraft(
                    start = event.startLocal.toString(),
                    end = event.endLocalExclusive.toString(),
                    conditions = event.values.conditions,
                    phenomena = event.values.phenomena,
                    symbolId = event.symbolId,
                    measurements = measurementFields.mapNotNull { field ->
                        valueFor(field.key, event.values)?.let { field.key to it.toString() }
                    }.toMap(),
                )
            })
            error = null
        }
    }

    MaterialTheme(colorScheme = MaterialTheme.colorScheme.copy(
        primary = Teal,
        onPrimary = Color.White,
        background = PageBackground,
        surface = Color.White,
        onSurface = Ink,
        error = Color(0xFF9B3D36),
    )) {
        Column(
            Modifier.fillMaxSize()
                .background(PageBackground)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PageHeader(
                title = if (editingItem == null) "Forecast catalog" else "Build simulated catalog",
                closeLabel = "CLOSE",
                onClose = {
                    if (editingItem != null) {
                        editingItem = null
                        error = null
                    } else onBack()
                },
            )

            if (editingItem == null) {
                val currentLocation = selectedLocation?.let { location ->
                    locations.firstOrNull { it.location.id == location.id }
                }
                currentLocation?.let {
                    Text(
                        "${it.candidate.name.uppercase(Locale.US)} · ${it.location.timeZone.id.uppercase(Locale.US)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MutedText,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
                Button(
                    onClick = ::beginNew,
                    enabled = selectedLocation != null && !working,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PaleTeal, contentColor = Ink),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text("+ BUILD NEW SIMULATION", style = MaterialTheme.typography.labelLarge)
                }
                initialPreviewError?.let { PreviewFailureNotice(it) }
                SectionCaption("SIMULATED", "editable · synthetic")
                if (simulatedRows.isEmpty()) {
                    EmptyState("No saved simulations yet. Build one to begin.")
                } else simulatedRows.forEach { row ->
                    SimulatedCatalogCard(
                        row = row,
                        onEdit = { beginEdit(row.itemId) },
                        onPreview = {
                            row.location?.let { onPreview(it, CatalogOrigin.SIMULATED, row.datasetId) }
                        },
                        onCapture = {
                            val location = row.location ?: return@SimulatedCatalogCard
                            working = true
                            activity.lifecycleScope.launch {
                                runCatching {
                                    repository.captureReplay(
                                        location,
                                        DatasetId(row.datasetId),
                                        DatasetId("authored-replay-${UUID.randomUUID()}"),
                                    )
                                    replayRefresh++
                                }.onFailure { error = it.message ?: "The snapshot could not be saved." }
                                working = false
                            }
                        },
                        working = working,
                    )
                }
                SectionCaption("REPLAY · FIXED SNAPSHOTS", "read-only")
                if (replayRows.isEmpty()) {
                    EmptyState("Captured snapshots appear here and cannot be edited.")
                } else replayRows.forEach { row ->
                    ReplayCatalogCard(row) {
                        onPreview(row.location, CatalogOrigin.REPLAY, row.datasetId)
                    }
                }
                Text(
                    "Saved on this debug installation. Both types stay synthetic; LIVE and location data are untouched.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                )
                error?.let { ErrorNotice(it) }
            } else {
                val location = selectedLocation
                SectionTitle("STEP 1", "NAME & LOCAL PERIOD")
                CardSurface {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        LabeledFieldTitle("CATALOG NAME")
                        CompactTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = "Name this simulation",
                        )
                        LabeledFieldTitle("LOCATION · FIXED FOR THIS CATALOG")
                        location?.let { selected ->
                            val display = locations.firstOrNull { it.location.id == selected.id }?.candidate?.name
                                ?: selected.id.value
                            Surface(
                                color = Color(0xFFF5F8F9),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                            ) {
                                Text(
                                    "$display · ${selected.timeZone.id}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            DateTimeField(
                                value = envelopeStart,
                                onValueChange = { envelopeStart = it },
                                label = "START · LOCAL",
                                modifier = Modifier.weight(1f),
                            )
                            DateTimeField(
                                value = envelopeEnd,
                                onValueChange = { envelopeEnd = it },
                                label = "END · LOCAL",
                                modifier = Modifier.weight(1f),
                            )
                        }
                        Text(
                            "Use YYYY-MM-DDTHH:MM · end is exclusive · full 44-date catalog remains",
                            style = MaterialTheme.typography.bodySmall,
                            color = MutedText,
                        )
                    }
                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SectionTitle("STEP 2", "PLACE SUPPORTED EVENTS")
                    Column(horizontalAlignment = Alignment.End) {
                    TextButton(onClick = {
                        runCatching {
                            val envelopeEndTime = parseLocalDateTime(envelopeEnd)
                            val nextStart = events.lastOrNull()?.let { parseLocalDateTime(it.end) }
                                ?: parseLocalDateTime(envelopeStart)
                            check(nextStart.plusHours(1) <= envelopeEndTime) {
                                "No free whole-hour range remains inside the local period."
                            }
                            events += EventDraft(nextStart.toString(), nextStart.plusHours(1).toString())
                            error = null
                        }.onFailure { error = it.message ?: "Check the local period before adding an event." }
                    }) { Text("+ ADD EVENT", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            val end = start.plusHours(8)
                            envelopeStart = start.toString()
                            envelopeEnd = end.toString()
                            events.clear()
                            events.addAll(DebugSkySymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Sky review ranges." }
                    }) { Text("8 SYMBOL RANGES", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            envelopeStart = start.toString()
                            envelopeEnd = start.plusHours(8).toString()
                            events.clear()
                            events.addAll(DebugRainIceSymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Rain + Ice ranges." }
                    }) { Text("RAIN + ICE RANGES", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            envelopeStart = start.toString()
                            envelopeEnd = start.plusHours(8).toString()
                            events.clear()
                            events.addAll(DebugSnowMixedSymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Snow + Mixed ranges." }
                    }) { Text("SNOW + MIXED RANGES", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            envelopeStart = start.toString()
                            envelopeEnd = start.plusHours(8).toString()
                            events.clear()
                            events.addAll(DebugVisibilityWindSymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Visibility + Wind ranges." }
                    }) { Text("VISIBILITY + WIND RANGES", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            envelopeStart = start.toString()
                            envelopeEnd = start.plusHours(8).toString()
                            events.clear()
                            events.addAll(DebugStormFallbackSymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Storms + Fallback ranges." }
                    }) { Text("STORMS + FALLBACK RANGES", style = MaterialTheme.typography.labelMedium) }
                    TextButton(onClick = {
                        runCatching {
                            val day = parseLocalDateTime(envelopeStart).toLocalDate()
                            val start = day.atTime(8, 0)
                            envelopeStart = start.toString()
                            envelopeEnd = start.plusHours(8).toString()
                            events.clear()
                            events.addAll(DebugAdditionalEventsSymbolRangeFixture.events(start).map { event ->
                                EventDraft(
                                    start = event.startLocal.toString(),
                                    end = event.endLocalExclusive.toString(),
                                    conditions = emptySet(),
                                    symbolId = event.symbolId,
                                )
                            })
                            error = null
                        }.onFailure { error = it.message ?: "Set a valid local date before loading the Additional Events ranges." }
                    }) { Text("ADDITIONAL EVENTS RANGES", style = MaterialTheme.typography.labelMedium) }
                    }
                }
                EventTimeline(envelopeStart, envelopeEnd, events)
                events.forEachIndexed { index, draft ->
                    key(index) {
                        EventEditorCard(
                            index = index,
                            draft = draft,
                            onChange = { events[index] = it },
                            onRemove = { events.removeAt(index) },
                            canRemove = events.size > 1,
                        )
                    }
                }
                NoticeCard(
                    title = "Unavailable stays unavailable",
                    body = "No guessed condition or silent zero. Chance and amount are separate values.",
                )
                error?.let { ErrorNotice(it) }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        enabled = !working && name.isNotBlank() && location != null,
                        onClick = {
                            val item = editingItem ?: return@Button
                            val fixedLocation = location ?: return@Button
                            working = true
                            error = null
                            activity.lifecycleScope.launch {
                                try {
                                    val start = parseLocalDateTime(envelopeStart)
                                    val end = parseLocalDateTime(envelopeEnd)
                                    val definitions = events.map { draft ->
                                        fun number(key: String): Double? {
                                            val raw = draft.measurements[key]?.trim().orEmpty()
                                            if (raw.isEmpty()) return null
                                            return raw.toDoubleOrNull()?.takeIf(Double::isFinite)
                                                ?: kotlin.error("${measurementFields.first { it.key == key }.label} needs a valid number.")
                                        }
                                        val values = DebugAuthoredWeatherValues(
                                            conditions = draft.conditions,
                                            phenomena = draft.phenomena,
                                            temperatureC = number("temperatureC"),
                                            precipitationProbabilityPercent = number("precipitationProbabilityPercent"),
                                            thunderstormProbabilityPercent = number("thunderstormProbabilityPercent"),
                                            relativeHumidityPercent = number("relativeHumidityPercent"),
                                            cloudCoverPercent = number("cloudCoverPercent"),
                                            windSpeedKph = number("windSpeedKph"),
                                            windGustKph = number("windGustKph"),
                                            windDirectionDegrees = number("windDirectionDegrees"),
                                            rainAmountMillimeters = number("rainAmountMillimeters"),
                                            totalPrecipitationMillimeters = number("totalPrecipitationMillimeters"),
                                            precipitationRateMillimetersPerHour = number("precipitationRateMillimetersPerHour"),
                                            snowfallCentimeters = number("snowfallCentimeters"),
                                            snowDepthCentimeters = number("snowDepthCentimeters"),
                                            surfacePressureHpa = number("surfacePressureHpa"),
                                            meanSeaLevelPressureHpa = number("meanSeaLevelPressureHpa"),
                                            visibilityKm = number("visibilityKm"),
                                        )
                                        DebugAuthoredEvent(
                                            parseLocalDateTime(draft.start),
                                            parseLocalDateTime(draft.end),
                                            values,
                                            draft.symbolId,
                                        )
                                    }
                                    require(definitions.isNotEmpty()) { "Add at least one supported event." }
                                    val dataset = DatasetId("authored-${UUID.randomUUID()}")
                                    val asOf = start.toLocalDate().atTime(12, 0).atZone(fixedLocation.timeZone).toInstant()
                                    val definition = DebugAuthoredCatalog(
                                        name = name.trim(),
                                        location = fixedLocation,
                                        datasetId = dataset,
                                        asOf = asOf,
                                        startLocal = start,
                                        endLocalExclusive = end,
                                        values = definitions.first().values,
                                        events = definitions,
                                    )
                                    repository.save(definition)
                                    val logicalId = item.removePrefix("new:")
                                    val updated = (itemIds + logicalId).distinct()
                                    val committed = preferences.edit()
                                        .putStringSet("items", updated.toSet())
                                        .putString("name:$logicalId", name.trim())
                                        .putString("location:$logicalId", fixedLocation.id.value)
                                        .putString("dataset:$logicalId", dataset.value)
                                        .commit()
                                    check(committed) {
                                        "Library reference could not be updated; the prior saved revision is retained."
                                    }
                                    itemIds = updated
                                    editingItem = null
                                } catch (failure: Exception) {
                                    error = failure.message ?: "Check the local period and authored event values."
                                } finally {
                                    working = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).heightIn(min = 42.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PaleTeal, contentColor = Ink),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(9.dp),
                    ) {
                        Text(if (working) "SAVING…" else "SAVE SIMULATION", style = MaterialTheme.typography.labelMedium)
                    }
                    OutlinedButton(
                        enabled = !working,
                        onClick = { editingItem = null; error = null },
                        modifier = Modifier.weight(1f).heightIn(min = 42.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(9.dp),
                    ) {
                        Text("CANCEL", style = MaterialTheme.typography.labelMedium)
                    }
                }
                Text(
                    "Save returns to the library. Preview is a separate action on a saved item.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText,
                )
            }
        }
    }
}

@Composable
private fun PageHeader(title: String, closeLabel: String, onClose: () -> Unit) {
    Surface(color = Navy, shape = RoundedCornerShape(14.dp)) {
        Row(
            Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(title, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            TextButton(onClick = onClose, contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
                Text(closeLabel, color = Color(0xFF79D5C5), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun SectionCaption(title: String, detail: String) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(title, color = Ink, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Text(detail, color = MutedText, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SectionTitle(step: String, title: String) {
    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        Text(step, color = Teal, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Text(title, color = Ink, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CardSurface(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        content = { content() },
    )
}

@Composable
private fun EmptyState(message: String) {
    CardSurface {
        Text(message, color = MutedText, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(14.dp))
    }
}

@Composable
private fun SimulatedCatalogCard(
    row: SimulatedRow,
    onEdit: () -> Unit,
    onPreview: () -> Unit,
    onCapture: () -> Unit,
    working: Boolean,
) {
    CardSurface {
        Column(Modifier.padding(11.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(
                row.name,
                color = Ink,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (row.start != null && row.end != null) {
                Text(
                    "${periodSummary(row.start, row.end)} · ${row.locationLabel}",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            } else {
                Text(
                    if (row.location == null) "${row.locationLabel} · saved data unavailable"
                    else "Saved details unavailable · Preview will revalidate this record",
                    color = MutedText,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedButton(
                    onClick = onEdit,
                    enabled = row.valid && !working,
                    modifier = Modifier.weight(0.72f).heightIn(min = 36.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                ) { Text("EDIT", fontSize = 10.sp, maxLines = 1) }
                Button(
                    onClick = onPreview,
                    enabled = row.location != null && row.datasetId.isNotBlank() && !working,
                    modifier = Modifier.weight(0.9f).heightIn(min = 36.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Navy),
                    shape = RoundedCornerShape(8.dp),
                ) { Text("PREVIEW", fontSize = 10.sp, maxLines = 1) }
                OutlinedButton(
                    onClick = onCapture,
                    enabled = row.valid && !working,
                    modifier = Modifier.weight(1.45f).heightIn(min = 36.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                ) { Text("CAPTURE REPLAY", fontSize = 10.sp, maxLines = 1, softWrap = false) }
            }
        }
    }
}

@Composable
private fun ReplayCatalogCard(row: ReplayRow, onPreview: () -> Unit) {
    CardSurface {
        Column(Modifier.padding(11.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(row.name, color = Ink, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(
                "${row.locationLabel} · captured from SIMULATED · immutable",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Button(
                onClick = onPreview,
                modifier = Modifier.heightIn(min = 36.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Navy),
                shape = RoundedCornerShape(8.dp),
            ) { Text("PREVIEW REPLAY", fontSize = 10.sp) }
        }
    }
}

@Composable
private fun LabeledFieldTitle(label: String) {
    Text(label, color = MutedText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
}

@Composable
private fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, fontSize = 12.sp, color = MutedText) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Teal,
            unfocusedBorderColor = Border,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
private fun DateTimeField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        LabeledFieldTitle(label)
        CompactTextField(value, onValueChange, "YYYY-MM-DDTHH:MM", keyboardType = KeyboardType.Ascii)
    }
}

@Composable
private fun EventTimeline(envelopeStart: String, envelopeEnd: String, events: List<EventDraft>) {
    val envelope = runCatching { parseLocalDateTime(envelopeStart) to parseLocalDateTime(envelopeEnd) }.getOrNull()
    CardSurface {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Canvas(Modifier.fillMaxWidth().height(46.dp)) {
                val left = 4.dp.toPx()
                val right = size.width - 4.dp.toPx()
                val axisY = size.height - 5.dp.toPx()
                drawLine(Color(0xFFAABDC7), Offset(left, axisY), Offset(right, axisY), strokeWidth = 2.dp.toPx())
                val bounds = envelope
                if (bounds != null && bounds.first < bounds.second) {
                    val total = ChronoUnit.SECONDS.between(bounds.first, bounds.second).toFloat().coerceAtLeast(1f)
                    val colors = listOf(Color(0xFF579DA3), Color(0xFF647DA1), Color(0xFF8B7B9A), Color(0xFFB17C55))
                    events.forEachIndexed { index, event ->
                        val range = runCatching { parseLocalDateTime(event.start) to parseLocalDateTime(event.end) }.getOrNull()
                            ?: return@forEachIndexed
                        val startFraction = (ChronoUnit.SECONDS.between(bounds.first, range.first) / total).coerceIn(0f, 1f)
                        val endFraction = (ChronoUnit.SECONDS.between(bounds.first, range.second) / total).coerceIn(0f, 1f)
                        val startX = left + (right - left) * startFraction
                        val endX = left + (right - left) * endFraction
                        val y = 2.dp.toPx() + (index % 3) * 12.dp.toPx()
                        if (endX > startX) drawRoundRect(
                            color = colors[index % colors.size],
                            topLeft = Offset(startX, y),
                            size = androidx.compose.ui.geometry.Size(endX - startX, 10.dp.toPx()),
                            cornerRadius = CornerRadius(5.dp.toPx()),
                        )
                    }
                    for (fraction in 0..4) {
                        val x = left + (right - left) * fraction / 4f
                        drawLine(Color(0xFFC5D3DA), Offset(x, axisY - 8.dp.toPx()), Offset(x, axisY + 1.dp.toPx()), 1.dp.toPx())
                    }
                }
            }
            if (envelope != null) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(dateLabel(envelope.first), style = MaterialTheme.typography.labelSmall, color = MutedText)
                    Text("LOCAL PERIOD", style = MaterialTheme.typography.labelSmall, color = MutedText)
                    Text(dateLabel(envelope.second), style = MaterialTheme.typography.labelSmall, color = MutedText)
                }
            }
        }
    }
}

@Composable
private fun EventEditorCard(
    index: Int,
    draft: EventDraft,
    onChange: (EventDraft) -> Unit,
    onRemove: () -> Unit,
    canRemove: Boolean,
) {
    var showMore by remember { mutableStateOf(false) }
    CardSurface {
        Column(Modifier.padding(11.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("EVENT ${index + 1} · LOCAL RANGE", color = Teal, style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (canRemove) {
                    TextButton(onClick = onRemove, contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)) {
                        Text("REMOVE", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DateTimeField(draft.start, { onChange(draft.copy(start = it)) }, "START", Modifier.weight(1f))
                DateTimeField(draft.end, { onChange(draft.copy(end = it)) }, "END · EXCLUSIVE", Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("SIMULATED SYMBOL", color = MutedText, style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium)
                    Text(draft.symbolId?.label ?: "No symbol selected", color = Ink,
                        style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        PreviewSymbolTile(draft.symbolId, 48)
                        PreviewSymbolTile(draft.symbolId, 24)
                        Text("48 dp  ·  24 dp", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    }
                }
                TextButton(
                    onClick = { onChange(draft.copy(symbolId = null)) },
                    enabled = draft.symbolId != null,
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                ) { Text("CLEAR", color = MutedText, style = MaterialTheme.typography.labelSmall) }
            }
            WeatherSymbolId.entries.chunked(4).forEach { rowSymbols ->
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    rowSymbols.forEach { symbol ->
                        FilterChip(
                            selected = draft.symbolId == symbol,
                            onClick = { onChange(draft.copy(symbolId = symbol)) },
                            label = { Text(symbol.label, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
            Text("CONDITION", color = MutedText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
            WeatherCondition.entries.chunked(3).forEach { rowConditions ->
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    rowConditions.forEach { condition ->
                        FilterChip(
                            selected = condition in draft.conditions,
                            onClick = {
                                val next = if (condition in draft.conditions) draft.conditions - condition
                                    else draft.conditions + condition
                                onChange(draft.copy(conditions = next))
                            },
                            label = { Text(condition.displayLabel(), fontSize = 10.sp, maxLines = 1) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    if (rowConditions.size < 3) repeat(3 - rowConditions.size) { Spacer(Modifier.weight(1f)) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("CHANCE · %", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    CompactTextField(
                        value = draft.measurements["precipitationProbabilityPercent"].orEmpty(),
                        onValueChange = { onChange(draft.copy(measurements = draft.measurements + ("precipitationProbabilityPercent" to it))) },
                        placeholder = "Unavailable",
                        keyboardType = KeyboardType.Decimal,
                    )
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("RAIN AMOUNT · mm", color = MutedText, style = MaterialTheme.typography.labelSmall)
                    CompactTextField(
                        value = draft.measurements["rainAmountMillimeters"].orEmpty(),
                        onValueChange = { onChange(draft.copy(measurements = draft.measurements + ("rainAmountMillimeters" to it))) },
                        placeholder = "Unavailable",
                        keyboardType = KeyboardType.Decimal,
                    )
                }
            }
            TextButton(
                onClick = { showMore = !showMore },
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
            ) {
                Text(if (showMore) "− FEWER SUPPORTED VALUES" else "+ MORE SUPPORTED VALUES", style = MaterialTheme.typography.labelMedium)
            }
            if (showMore) {
                Text("PHENOMENA", color = MutedText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                WeatherPhenomenon.entries.chunked(2).forEach { rowPhenomena ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        rowPhenomena.forEach { phenomenon ->
                            FilterChip(
                                selected = phenomenon in draft.phenomena,
                                onClick = {
                                    val next = if (phenomenon in draft.phenomena) draft.phenomena - phenomenon
                                        else draft.phenomena + phenomenon
                                    onChange(draft.copy(phenomena = next))
                                },
                                label = { Text(phenomenon.displayLabel(), fontSize = 10.sp, maxLines = 1) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
                measurementFields.filter {
                    it.key !in setOf("precipitationProbabilityPercent", "rainAmountMillimeters")
                }.chunked(2).forEach { rowFields ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        rowFields.forEach { field ->
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text(field.label, color = MutedText, style = MaterialTheme.typography.labelSmall, maxLines = 1)
                                CompactTextField(
                                    value = draft.measurements[field.key].orEmpty(),
                                    onValueChange = { onChange(draft.copy(measurements = draft.measurements + (field.key to it))) },
                                    placeholder = "Unavailable",
                                    keyboardType = KeyboardType.Decimal,
                                )
                            }
                        }
                        if (rowFields.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                Text("Blank means unavailable; explicit 0 remains a supplied value.", color = MutedText,
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun PreviewSymbolTile(symbol: WeatherSymbolId?, sizeDp: Int) {
    Box(
        modifier = Modifier.size((sizeDp + 8).dp).background(Navy, RoundedCornerShape(9.dp)),
        contentAlignment = Alignment.Center,
    ) {
        if (symbol != null) {
            WeatherSymbolIcon(symbol, Modifier.size(sizeDp.dp), contentDescription = null)
        } else {
            Text("—", color = Color.White.copy(alpha = .7f), fontSize = 15.sp)
        }
    }
}

@Composable
private fun NoticeCard(title: String, body: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Amber,
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDC58A)),
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 9.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = Color(0xFF6E571E), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Text(body, color = MutedText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun ErrorNotice(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFFCEDEC),
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE8B8B2)),
    ) {
        Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp))
    }
}

@Composable
private fun PreviewFailureNotice(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Amber,
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDDC58A)),
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 9.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                "Can't preview this saved catalog",
                color = Color(0xFF6E571E),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "This saved record could not be revalidated: $message. Your current forecast and selected time " +
                    "are unchanged. Choose another item to preview.",
                color = MutedText,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private fun parseLocalDateTime(value: String): LocalDateTime {
    val normalized = value.trim().replace(' ', 'T')
    return LocalDateTime.parse(normalized, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

private fun periodSummary(start: LocalDateTime, end: LocalDateTime): String {
    if (start >= end) return "Local period unavailable"
    val first = start.toLocalDate()
    val last = end.minusNanos(1).toLocalDate()
    val count = ChronoUnit.DAYS.between(first, last) + 1
    val formatter = DateTimeFormatter.ofPattern("MMM d", Locale.US)
    val range = if (first == last) formatter.format(first) else "${formatter.format(first)} – ${formatter.format(last)}"
    return "$range · $count local ${if (count == 1L) "date" else "dates"}"
}

private fun dateLabel(value: LocalDateTime) = DateTimeFormatter.ofPattern("MMM d", Locale.US).format(value)

private fun WeatherCondition.displayLabel() = when (this) {
    WeatherCondition.CLEAR -> "Clear"
    WeatherCondition.PARTLY_CLOUDY -> "Partly cloudy"
    WeatherCondition.CLOUDY -> "Cloudy"
    WeatherCondition.RAIN -> "Rain"
    WeatherCondition.SNOW -> "Snow"
    WeatherCondition.STORM -> "Storm"
}

private fun WeatherPhenomenon.displayLabel() = when (this) {
    WeatherPhenomenon.FOG -> "Fog"
    WeatherPhenomenon.FREEZING_RAIN -> "Freezing rain"
    WeatherPhenomenon.ICE_PELLETS -> "Ice pellets"
    WeatherPhenomenon.HAZE -> "Haze"
}

private fun valueFor(key: String, values: DebugAuthoredWeatherValues): Double? = when (key) {
    "temperatureC" -> values.temperatureC
    "precipitationProbabilityPercent" -> values.precipitationProbabilityPercent
    "thunderstormProbabilityPercent" -> values.thunderstormProbabilityPercent
    "relativeHumidityPercent" -> values.relativeHumidityPercent
    "cloudCoverPercent" -> values.cloudCoverPercent
    "windSpeedKph" -> values.windSpeedKph
    "windGustKph" -> values.windGustKph
    "windDirectionDegrees" -> values.windDirectionDegrees
    "rainAmountMillimeters" -> values.rainAmountMillimeters
    "totalPrecipitationMillimeters" -> values.totalPrecipitationMillimeters
    "precipitationRateMillimetersPerHour" -> values.precipitationRateMillimetersPerHour
    "snowfallCentimeters" -> values.snowfallCentimeters
    "snowDepthCentimeters" -> values.snowDepthCentimeters
    "surfacePressureHpa" -> values.surfacePressureHpa
    "meanSeaLevelPressureHpa" -> values.meanSeaLevelPressureHpa
    "visibilityKm" -> values.visibilityKm
    else -> null
}
