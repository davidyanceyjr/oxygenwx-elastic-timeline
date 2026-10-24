package com.oxygen.weather.timeline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import com.oxygen.weather.timeline.presentation.ForecastProjection
import kotlin.math.roundToInt

private data class ForecastBrowserRow(
    val key: String,
    val hourOffset: Double,
    val title: String,
    val details: String,
    val selected: Boolean,
    val precision: Boolean,
)

@Composable
fun AccessibleForecastBrowser(
    selectedHour: Double,
    precision: Boolean,
    onSelect: (hourOffset: Double, precision: Boolean) -> Unit,
    onTogglePrecision: () -> Unit,
    onDismiss: () -> Unit,
) {
    val rows = if (precision) {
        SampleForecast.hours.mapIndexed { index, _ ->
            val view = ForecastProjection.fromHourOffset(index.toDouble(), precision = true)
            val selected = index == selectedHour.roundToInt().coerceIn(0, SampleForecast.hours.lastIndex)
            ForecastBrowserRow(
                key = "hour-$index",
                hourOffset = index.toDouble(),
                title = "${view.heading}, ${view.timeLabel}",
                details = forecastDetails(view.temperatureF, view.conditionsLabel,
                    view.precipitationPercent, view.windMph),
                selected = selected,
                precision = true,
            )
        }
    } else {
        SampleForecast.days.mapIndexed { index, _ ->
            val hourOffset = index * 24.0 + 12.0
            val view = ForecastProjection.fromHourOffset(hourOffset, precision = false)
            val selected = ElasticTimeAxis.dayIndex(selectedHour) == index
            ForecastBrowserRow(
                key = "day-$index",
                hourOffset = hourOffset,
                title = "${view.heading}, day overview",
                details = forecastDetails(view.temperatureF, view.conditionsLabel,
                    view.precipitationPercent, view.windMph) +
                    ", high ${view.highF} degrees, low ${view.lowF} degrees",
                selected = selected,
                precision = false,
            )
        }
    }
    val selectedRowIndex = if (precision) {
        selectedHour.roundToInt().coerceIn(0, SampleForecast.hours.lastIndex)
    } else {
        ElasticTimeAxis.dayIndex(selectedHour).coerceIn(0, SampleForecast.days.lastIndex)
    }
    val listState = rememberLazyListState()
    LaunchedEffect(precision, selectedRowIndex) {
        listState.scrollToItem(selectedRowIndex)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (precision) "Hourly forecast" else "Daily forecast") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("DEMO DATA", fontWeight = FontWeight.SemiBold)
                Text("Select a forecast to update the timeline and summary.")
                TextButton(onClick = onTogglePrecision) {
                    Text(if (precision) "Browse daily forecasts" else "Browse hourly forecasts")
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 440.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(rows, key = ForecastBrowserRow::key) { row ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = row.selected,
                                    role = Role.RadioButton,
                                    onClick = { onSelect(row.hourOffset, row.precision) },
                                )
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                        ) {
                            Text(row.title, fontWeight = FontWeight.Medium)
                            Text(row.details)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Done") }
        },
    )
}

private fun forecastDetails(
    temperatureF: Int,
    conditions: String,
    precipitationPercent: Int,
    windMph: Int,
): String = "${temperatureF} degrees, $conditions, precipitation chance " +
    "$precipitationPercent percent, wind $windMph miles per hour"
