package com.oxygen.weather.timeline

import android.os.Bundle
import android.content.pm.ApplicationInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxygen.weather.timeline.data.SkyCondition
import com.oxygen.weather.timeline.presentation.SkyFrame
import com.oxygen.weather.timeline.presentation.atmosphere.AtmosphereInput
import com.oxygen.weather.timeline.presentation.atmosphere.AtmospherePreview
import com.oxygen.weather.timeline.ui.AtmosphericScene
import com.oxygen.weather.timeline.ui.TimeLabScreen

/** Standalone experiment. Does not replace Oxygen Weather's production Home pager. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDebuggable = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        val showSnowPreview = isDebuggable && intent.getBooleanExtra(EXTRA_PREVIEW_SNOW, false)
        setContent {
            MaterialTheme {
                if (showSnowPreview) SyntheticSnowPreview() else TimeLabScreen()
            }
        }
    }
}

private const val EXTRA_PREVIEW_SNOW = "preview_snow"

@androidx.compose.runtime.Composable
private fun SyntheticSnowPreview() {
    val input = AtmosphereInput(
        sky = SkyFrame(
            condition = SkyCondition.CLOUDY,
            temperatureC = 2.0,
            cloudPercent = 70.0,
            rainChancePercent = 0.0,
            windKph = 0.0,
        ),
        precisionMode = false,
        localHour = 15.0,
        preview = AtmospherePreview.SYNTHETIC_SNOW,
    )
    Box(Modifier.fillMaxSize()) {
        AtmosphericScene(input = input)
        Text(
            text = "SYNTHETIC SNOW PREVIEW",
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .background(Color(0xDD102237))
                .padding(horizontal = 18.dp, vertical = 12.dp),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
        )
    }
}
