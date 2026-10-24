package com.oxygen.weather.timeline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.oxygen.weather.timeline.ui.TimeLabScreen

/** Standalone experiment. Does not replace Oxygen Weather's production Home pager. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TimeLabScreen()
            }
        }
    }
}
