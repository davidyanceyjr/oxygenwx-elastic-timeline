package com.oxygen.weather.timeline.ui

import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.CatalogProductKind
import com.oxygen.weather.timeline.data.catalog.CatalogProvenance
import com.oxygen.weather.timeline.data.catalog.CatalogRecording
import com.oxygen.weather.timeline.data.catalog.CatalogExpectedSupport
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.ExpectedFieldSupport
import com.oxygen.weather.timeline.data.catalog.GeoCoordinates
import com.oxygen.weather.timeline.data.catalog.LocationId
import com.oxygen.weather.timeline.presentation.SavedLocationRailTemperatureProjection
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LocationRailProjectionPositionTest {
    private val zone = ZoneId.of("America/Chicago")
    private val selectedInstant = LocalDate.parse("2026-11-08")
        .atTime(12, 0).atZone(zone).toInstant()
    private val location = CatalogLocation(
        LocationId("geonames:4887398"), GeoCoordinates(41.8781, -87.6298), zone,
    )

    @Test
    fun savedTemperatureUsesMatchingLiveSupportAtTheSelectedInstant() {
        val measurement = SavedLocationRailTemperatureProjection.project(
            location,
            recording(),
            selectedInstant,
        )

        assertEquals(10.0, measurement?.canonicalValue ?: Double.NaN, 0.0)
        assertEquals(50.0, measurement?.displayValue ?: Double.NaN, 0.0)
        assertEquals("°F", measurement?.displayUnit)
    }

    @Test
    fun missingSupportOrMismatchedSavedCatalogStaysUnavailable() {
        assertNull(SavedLocationRailTemperatureProjection.project(
            location, recording(temperatureC = null), selectedInstant,
        ))
        assertNull(SavedLocationRailTemperatureProjection.project(
            location, recording(), selectedInstant.plusSeconds(24 * 60 * 60),
        ))
        assertNull(SavedLocationRailTemperatureProjection.project(
            location.copy(id = LocationId("geonames:999")), recording(), selectedInstant,
        ))
        assertNull(SavedLocationRailTemperatureProjection.project(
            location, recording(origin = CatalogOrigin.SIMULATED), selectedInstant,
        ))
        assertNull(SavedLocationRailTemperatureProjection.project(
            location, recording(datasetId = DatasetId("other-dataset")), selectedInstant,
        ))
    }

    @Test
    fun savedRailInitialPositionFollowsSelectedIdentityAndDefaultsToFirst() {
        val keys = listOf("saved:geonames:4887398", "saved:geonames:4930956")

        assertEquals(1, initialLocationRailPage(keys, "saved:geonames:4930956"))
        assertEquals(0, initialLocationRailPage(keys, "saved:geonames:missing"))
        assertEquals(0, initialLocationRailPage(emptyList(), null))
    }

    private fun recording(
        temperatureC: Double? = 10.0,
        origin: CatalogOrigin = CatalogOrigin.LIVE,
        datasetId: DatasetId = DatasetId("forecast-44d"),
    ): CatalogRecording {
        val fact = CanonicalWeatherFacts(selectedInstant, temperatureC = temperatureC)
        val catalog = CanonicalCatalog(
            location = location,
            datasetId = datasetId,
            provenance = CatalogProvenance(
                origin,
                if (origin == CatalogOrigin.SIMULATED) CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT
                else CatalogProductKind.FORECAST,
            ),
            asOf = selectedInstant,
            facts = listOf(fact),
        )
        val expectedSupport: CatalogExpectedSupport = mapOf(
            selectedInstant.atZone(zone).toLocalDate() to mapOf(
                CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(setOf(selectedInstant)),
            ),
        )
        return CatalogRecording(catalog, expectedSupport)
    }
}
