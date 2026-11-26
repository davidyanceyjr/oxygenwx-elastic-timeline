package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.time.Instant

/** Park-only scene passes; their order is independent of First City's shared layer order. */
internal enum class UrbanParkLayer(val backToFrontPosition: Int) {
    SKY_UNDERLAY(0),
    BACKGROUND(1),
    STARS(2),
    SUN(3),
    MOON(4),
    CLOUD_LAYER_1(5),
    CLOUD_LAYER_2(6),
    CLOUD_LAYER_3(7),
    MIDDLEGROUND(8),
    LOCATION_INSCRIPTION(9),
    FOREGROUND(10),
    MURAL_READER(11),
    PRECIPITATION(12),
}

/** Stable Park cloud identities with their independent selected-time tracks. */
internal enum class UrbanParkCloudLayer(
    val sceneLayer: UrbanParkLayer,
    val travelPerHour: Double,
) {
    LAYER_1(UrbanParkLayer.CLOUD_LAYER_1, .085),
    LAYER_2(UrbanParkLayer.CLOUD_LAYER_2, -.035),
    LAYER_3(UrbanParkLayer.CLOUD_LAYER_3, .12),
}

/** Park rendering reuses resolved First City weather/celestial inputs and adds Park-only cloud depth. */
internal data class UrbanParkComposition(
    val firstCity: FirstCityComposition,
    val selectedWeatherSymbolId: WeatherSymbolId?,
    val wallReader: UrbanParkWallReader,
) {
    val selectedInstant: Instant get() = firstCity.selectedInstant

    init {
        require(wallReader.selectedInstant == selectedInstant)
    }

    val cloudLayers: List<UrbanParkCloudLayer> = when (selectedWeatherSymbolId) {
        WeatherSymbolId.PARTLY_SUNNY_CLOUDY,
        WeatherSymbolId.PARTLY_CLOUDY_NIGHT -> listOf(UrbanParkCloudLayer.LAYER_1)
        WeatherSymbolId.MOSTLY_CLOUDY -> listOf(
            UrbanParkCloudLayer.LAYER_1,
            UrbanParkCloudLayer.LAYER_2,
        )
        WeatherSymbolId.CLOUDY_OVERCAST -> UrbanParkCloudLayer.entries
        else -> emptyList()
    }

    val layerOrder: List<UrbanParkLayer> = buildList {
        add(UrbanParkLayer.SKY_UNDERLAY)
        add(UrbanParkLayer.BACKGROUND)
        add(UrbanParkLayer.STARS)
        if (firstCity.cue.visible) add(UrbanParkLayer.SUN)
        if (firstCity.moonCue.visible) add(UrbanParkLayer.MOON)
        cloudLayers.forEach { add(it.sceneLayer) }
        add(UrbanParkLayer.MIDDLEGROUND)
        add(UrbanParkLayer.LOCATION_INSCRIPTION)
        add(UrbanParkLayer.FOREGROUND)
        add(UrbanParkLayer.MURAL_READER)
        if (FirstCityLayer.PRECIPITATION in firstCity.layerOrder) {
            add(UrbanParkLayer.PRECIPITATION)
        }
    }
}
