package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogLoadRequest
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.CatalogWindowResult
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.presentation.scene.SceneReading
import com.oxygen.weather.timeline.presentation.scene.SemanticSceneGenerator
import java.time.Instant
import java.time.ZoneId

/** The exact supported-rain input approved for OX-18 scene review and JVM parity assertions. */
internal object DebugOx18ReviewScenario {
    val location = CatalogLocation(
        id = LocationId("geonames:4887398"),
        coordinates = GeoCoordinates(41.85003, -87.65005),
        timeZone = ZoneId.of("America/Chicago"),
    )
    val selectedInstant: Instant = Instant.parse("2026-10-04T15:30:00Z")

    suspend fun reading(): SceneReading {
        val recording = sg4GapRecording(
            CatalogLoadRequest(
                location = location,
                datasetId = DatasetId(DebugCatalogActivity.SG4_GAP_REPLAY_ID),
                asOf = Instant.parse(DebugCatalogActivity.CHICAGO_REPLAY_AS_OF),
            ),
        )
        val window = when (val result = CatalogWindow.create(
            catalog = recording.catalog,
            expectedSupport = recording.expectedSupport,
            dailyExpectedSupport = recording.dailyExpectedSupport,
        )) {
            is CatalogWindowResult.Success -> result.window
            is CatalogWindowResult.Failure -> error("OX-18 SG.4 review catalog window failed: ${result.error}")
        }
        return SemanticSceneGenerator.generate(
            recording.catalog,
            window,
            window.startInclusive,
            window.endExclusive,
        ).evaluate(selectedInstant)
    }
}
