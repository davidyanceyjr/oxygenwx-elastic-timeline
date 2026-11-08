package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CatalogWindowResult
import com.oxygen.weather.timeline.data.catalog.DatasetId
import java.time.Instant

/** Projects only a supported temperature from a saved location's persisted LIVE catalog. */
object SavedLocationRailTemperatureProjection {
    private val liveForecastDataset = DatasetId("forecast-44d")

    fun project(
        location: CatalogLocation,
        recording: CatalogRecording?,
        selectedInstant: Instant,
    ): PresentedMeasurement? {
        recording ?: return null
        val catalog = recording.catalog
        if (catalog.location != location || catalog.datasetId != liveForecastDataset ||
            catalog.provenance.origin != CatalogOrigin.LIVE
        ) return null

        val window = (CatalogWindow.create(
            catalog,
            recording.expectedSupport,
            dailyExpectedSupport = recording.dailyExpectedSupport,
        ) as? CatalogWindowResult.Success)?.window ?: return null
        val temperatureFact = supportedPointFactAt(window, selectedInstant, CatalogField.TEMPERATURE_C)
            ?: return null
        return CanonicalForecastProjection.project(
            catalog,
            window,
            temperatureFact.validTime,
            ForecastDisplayUnits.IMPERIAL,
        ).factSample?.takeIf { it.validTime == temperatureFact.validTime }?.temperature
    }
}
