package com.oxygen.weather.timeline.data.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.oxygen.weather.timeline.presentation.scene.FirstCityComposition
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.MinimalOpenLandscapeComposition
import com.oxygen.weather.timeline.presentation.scene.MinimalOpenLandscapeTheme
import com.oxygen.weather.timeline.presentation.scene.SceneWeatherDecision
import com.oxygen.weather.timeline.ui.CatalogAtmosphericScene
import com.oxygen.weather.timeline.ui.CatalogOpenLandscapeScene
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZoneId

/** Debug-only, single-scene host for clean installed OX-18 artwork captures. */
class DebugOx18SceneReviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val world = intent.getStringExtra(EXTRA_WORLD) ?: WORLD_FIRST_CITY
        setContent {
            var compositions by remember { mutableStateOf<ReviewCompositions?>(null) }
            LaunchedEffect(Unit) {
                compositions = withContext(Dispatchers.IO) {
                    val zone = ZoneId.of("America/Chicago")
                    val reading = DebugOx18ReviewScenario.reading()
                    val weatherDecision = SceneWeatherDecision.resolve(reading, zone)
                    ReviewCompositions(
                        firstCity = FirstCityTheme.compose(weatherDecision, zone),
                        openLandscape = MinimalOpenLandscapeTheme.compose(weatherDecision),
                    )
                }
            }
            val current = compositions
            when {
                current == null -> Box(Modifier.fillMaxSize().background(Color(0xFF11263D)))
                world == WORLD_FIRST_CITY -> CatalogAtmosphericScene(composition = current.firstCity)
                world == WORLD_OPEN_LANDSCAPE -> CatalogOpenLandscapeScene(composition = current.openLandscape)
                else -> Box(Modifier.fillMaxSize().background(Color(0xFF11263D)))
            }
        }
    }

    companion object {
        const val EXTRA_WORLD = "ox18_review_world"
        const val WORLD_FIRST_CITY = "first-city"
        const val WORLD_OPEN_LANDSCAPE = "open-landscape"
    }
}

private data class ReviewCompositions(
    val firstCity: FirstCityComposition,
    val openLandscape: MinimalOpenLandscapeComposition,
)
