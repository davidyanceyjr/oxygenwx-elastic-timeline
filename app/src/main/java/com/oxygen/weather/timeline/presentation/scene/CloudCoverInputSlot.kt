package com.oxygen.weather.timeline.presentation.scene

import java.time.Instant

/** Weather input for First City's existing cloud-cover element, without a second interpretation rule. */
internal data class CloudCoverInputSlot(
    val selectedInstant: Instant,
    val coverFraction: Double?,
    val source: ScenePoint<Double>?,
) {
    val elementId: FirstCityElementId get() = FirstCityElementId.CLOUD_COVER

    companion object {
        fun from(decision: SceneWeatherDecision): CloudCoverInputSlot = CloudCoverInputSlot(
            selectedInstant = decision.selectedInstant,
            coverFraction = decision.clouds.coverFraction,
            source = decision.reading.cloudCoverPercent,
        )
    }
}
