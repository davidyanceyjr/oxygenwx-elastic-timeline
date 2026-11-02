package com.oxygen.weather.timeline.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oxygen.weather.timeline.presentation.CatalogDayView
import com.oxygen.weather.timeline.presentation.CatalogTimelineView
import com.oxygen.weather.timeline.presentation.SupportedTimeInterval
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogForecastBrowserErrorAccessibilityTest {
    @get:Rule val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun forecastBrowserExposesApplicableRefreshError() {
        val zone = ZoneId.of("America/Chicago")
        val date = LocalDate.parse("2026-11-02")
        val selected = date.atTime(12, 0).atZone(zone).toInstant()
        val view = CatalogTimelineView(
            generation = 1L,
            selectedInstant = selected,
            interval = SupportedTimeInterval(selected.minusSeconds(86_400), selected.plusSeconds(86_400)),
            zone = zone,
            days = listOf(CatalogDayView(date, selected, "Mon, Nov 2, LIVE", "Coverage partial", emptyList())),
            heading = "MON, NOV 2",
            selectedTimeLabel = "12:00 PM CST",
            factTimeLabel = "12:00 PM CST",
            temperature = "54°",
            condition = "clear",
            high = "61°",
            low = "53°",
            precipitationChance = "1%",
            precipitationFact = null,
            wind = "5 mph",
            windDirectionDegrees = 270.0,
            meaning = "LIVE",
            source = "open-meteo-forecast-api",
            product = "Product unavailable",
            selectedLocationName = "Chicago",
            status = "live · source · fresh",
            scene = null,
            localHour = 12.0,
            loading = false,
            error = "Refresh unavailable",
        )
        composeRule.setContent {
            MaterialTheme {
                CatalogTimeLabScreen(view = view, onSelectInstant = { _, _ -> }, onReturnToNow = {})
            }
        }

        composeRule.onNodeWithTag("catalogTimeline")
            .performSemanticsAction(SemanticsActions.OnClick)
        composeRule.onNodeWithText("Daily forecasts").assertIsDisplayed()
        composeRule.onNodeWithText("Load or refresh error: Refresh unavailable").assertIsDisplayed()
    }
}
