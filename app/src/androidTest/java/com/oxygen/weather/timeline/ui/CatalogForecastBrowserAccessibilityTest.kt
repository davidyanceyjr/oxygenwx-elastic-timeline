package com.oxygen.weather.timeline.ui

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oxygen.weather.timeline.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatalogForecastBrowserAccessibilityTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun activeCatalogBrowseActionOpensAndCapturesForecastDetails() {
        composeRule.waitUntil(timeoutMillis = 45_000) {
            composeRule.onAllNodesWithTag("catalogTimeline").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("catalogTimeline")
            .performSemanticsAction(SemanticsActions.OnClick)

        composeRule.onNodeWithText("Daily forecasts").assertIsDisplayed()
        composeRule.onNodeWithText("Chicago", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText(
            "Forecast · Product unavailable · open-meteo-forecast-api",
            substring = true,
        ).assertExists()
        composeRule.onNodeWithText("live · source · fresh", substring = true).assertExists()
        composeRule.onNodeWithText("fact sample", substring = true).assertExists()

    }
}
