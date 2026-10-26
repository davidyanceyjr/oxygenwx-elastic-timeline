package com.oxygen.weather.timeline.data.debug

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oxygen.weather.timeline.ui.TimeLabScreen

/** Explicit debug tooling entry point; it has no launcher filter and no immersive-screen control. */
class DebugForecastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profileId = intent.getStringExtra(EXTRA_PROFILE)?.let { value ->
            runCatching { DebugProfileId.valueOf(value) }.getOrNull()
        }
        val profile = profileId?.let(DebugForecastProfiles::get)
        setContent {
            MaterialTheme {
                if (profile == null) {
                    ProfilePicker { selected ->
                        startActivity(newIntent(this, selected))
                        finish()
                    }
                } else {
                    TimeLabScreen(
                        forecastSource = profile.toForecastSource(),
                        initialSelectedHour = profile.samples.indexOfFirst { it.validTime == profile.referenceTime }.toDouble(),
                        initialPrecisionMode = false,
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_PROFILE = "debug_profile"

        fun newIntent(activity: Activity, profileId: DebugProfileId) =
            android.content.Intent(activity, DebugForecastActivity::class.java)
                .putExtra(EXTRA_PROFILE, profileId.name)
    }
}

@Composable
private fun ProfilePicker(onChoose: (DebugProfileId) -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Debug forecast profiles", style = MaterialTheme.typography.headlineSmall)
        Text("Synthetic scenario data · not provider forecasts")
        DebugProfileId.entries.forEach { id ->
            Button(onClick = { onChoose(id) }) {
                Text("${id.label} · ${id.eventDays} event days · ${id.loadedDays} loaded days")
            }
        }
    }
}
