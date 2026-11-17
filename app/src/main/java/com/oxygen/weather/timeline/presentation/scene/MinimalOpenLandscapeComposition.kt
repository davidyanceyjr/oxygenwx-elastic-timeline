package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

/** Review-only open terrain composition; all weather meaning comes from its shared input. */
class MinimalOpenLandscapeComposition internal constructor(
    val weatherDecision: SceneWeatherDecision,
) {
    val selectedInstant: Instant get() = weatherDecision.selectedInstant
    val sky: SceneSkyDecision? get() = weatherDecision.sky
    val clouds: SceneCloudDecision get() = weatherDecision.clouds
    val precipitation: ScenePrecipitation get() = weatherDecision.precipitation
}

object MinimalOpenLandscapeTheme {
    fun compose(weatherDecision: SceneWeatherDecision) =
        MinimalOpenLandscapeComposition(weatherDecision)
}
