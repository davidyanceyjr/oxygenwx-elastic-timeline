package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CanonicalCatalogContractTest {
    private val validTime = Instant.parse("2026-10-27T18:00:00Z")
    private val interval = AccumulationInterval(validTime.minusSeconds(3600), validTime)

    @Test
    fun apparentTemperaturePreservesIndependentNullZeroAndFactIdentity() {
        val missing = CanonicalWeatherFacts(validTime, temperatureC = 10.0)
        val zero = CanonicalWeatherFacts(validTime, temperatureC = 10.0, apparentTemperatureC = 0.0)
        assertNull(missing.apparentTemperatureC)
        assertEquals(0.0, zero.apparentTemperatureC!!, 0.0)
        assertNotEquals(missing, zero)
        assertEquals(zero.hashCode(), zero.withEvents(emptyList()).hashCode())
        assertEquals(0.0, zero.withEvents(emptyList()).apparentTemperatureC!!, 0.0)
        assertThrows(IllegalArgumentException::class.java) {
            CanonicalWeatherFacts(validTime, apparentTemperatureC = Double.NaN)
        }
        val catalog = catalog(CatalogOrigin.LIVE, CatalogProductKind.FORECAST)
        val withValue = CanonicalCatalog(catalog.location, catalog.datasetId, catalog.provenance,
            catalog.asOf, listOf(zero))
        val date = validTime.atZone(catalog.location.timeZone).toLocalDate()
        val support = mapOf(date to mapOf(CatalogField.APPARENT_TEMPERATURE_C to ExpectedFieldSupport.Points(setOf(validTime))))
        val window = (CatalogWindow.create(withValue, support) as CatalogWindowResult.Success).window
        assertEquals(CoverageStatus.COMPLETE, window.days.single { it.date == date }.fieldCoverage[CatalogField.APPARENT_TEMPERATURE_C])
        val withoutValue = CanonicalCatalog(catalog.location, catalog.datasetId, catalog.provenance,
            catalog.asOf, listOf(missing))
        val missingWindow = (CatalogWindow.create(withoutValue, support) as CatalogWindowResult.Success).window
        assertEquals(CoverageStatus.MISSING, missingWindow.days.single { it.date == date }.fieldCoverage[CatalogField.APPARENT_TEMPERATURE_C])
    }

    @Test
    fun identityUsesOpaqueNonblankIdsBoundedCoordinatesAndIanaTimeZone() {
        assertEquals("home", LocationId("home").value)
        assertEquals("snapshot-1", DatasetId("snapshot-1").value)
        assertThrows(IllegalArgumentException::class.java) { LocationId(" ") }
        assertThrows(IllegalArgumentException::class.java) { DatasetId("") }

        val location = location()
        assertEquals(90.0, GeoCoordinates(90.0, -180.0).latitudeDegrees, 0.0)
        assertEquals(-180.0, GeoCoordinates(90.0, -180.0).longitudeDegrees, 0.0)
        assertEquals(ZoneId.of("America/Chicago"), location.timeZone)
        listOf(
            { GeoCoordinates(90.01, 0.0) },
            { GeoCoordinates(0.0, -180.01) },
            { GeoCoordinates(Double.NaN, 0.0) },
            { GeoCoordinates(0.0, Double.POSITIVE_INFINITY) },
        ).forEach(::assertInvalid)
        assertThrows(IllegalArgumentException::class.java) {
            CatalogLocation(LocationId("fixed-offset"), GeoCoordinates(0.0, 0.0), ZoneOffset.UTC)
        }
    }

    @Test
    fun provenanceKeepsOriginAndProductKindIndependentAndExplicit() {
        assertThrows(IllegalArgumentException::class.java) { CatalogProvenance(CatalogOrigin.SIMULATED, CatalogProductKind.FORECAST) }
        assertThrows(IllegalArgumentException::class.java) {
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT)
        }
        assertThrows(IllegalArgumentException::class.java) {
            CatalogProvenance(CatalogOrigin.REPLAY, CatalogProductKind.FORECAST, sourceId = " ")
        }
        assertThrows(IllegalArgumentException::class.java) {
            CatalogProvenance(CatalogOrigin.REPLAY, CatalogProductKind.FORECAST, productId = " ")
        }

        val archivedForecast = CatalogProvenance(
            origin = CatalogOrigin.REPLAY,
            productKind = CatalogProductKind.ARCHIVED_MODEL_FORECAST,
            sourceId = "archive",
            issuedAt = validTime.minusSeconds(86_400),
        )
        assertEquals(CatalogProductKind.ARCHIVED_MODEL_FORECAST, archivedForecast.productKind)
        assertEquals(CatalogProductKind.REANALYSIS, CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.REANALYSIS).productKind)
        assertEquals(
            CatalogProductKind.ACTUAL_OBSERVATION,
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.ACTUAL_OBSERVATION).productKind,
        )
        assertEquals(
            CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
            CatalogProvenance(CatalogOrigin.SIMULATED, CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT).productKind,
        )
        assertEquals(
            CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
            CatalogProvenance(CatalogOrigin.REPLAY, CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT).productKind,
        )
    }

    @Test
    fun oneEnvelopeShapeCarriesLiveSimulatedAndReplayData() {
        val catalogs = listOf(
            catalog(CatalogOrigin.LIVE, CatalogProductKind.FORECAST),
            catalog(CatalogOrigin.SIMULATED, CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT),
            catalog(CatalogOrigin.REPLAY, CatalogProductKind.ARCHIVED_MODEL_FORECAST),
        )
        catalogs.forEach { catalog ->
            assertEquals(CanonicalCatalog::class.java, catalog.javaClass)
            assertEquals(CanonicalWeatherFacts::class.java, catalog.facts.single().javaClass)
            assertEquals(validTime, catalog.facts.single().validTime)
            assertEquals(Instant.parse("2026-10-27T19:00:00Z"), catalog.asOf)
        }
        assertNotSame(catalogs[0].provenance, catalogs[1].provenance)
    }

    @Test
    fun weatherFactsPreserveNullZeroAndComposableConditionsWithoutAlertFields() {
        val sourceConditions = mutableSetOf(WeatherCondition.STORM, WeatherCondition.RAIN)
        val sourcePhenomena = mutableSetOf(WeatherPhenomenon.FOG)
        val facts = CanonicalWeatherFacts(
            validTime = validTime,
            conditions = sourceConditions,
            phenomena = sourcePhenomena,
            precipitationProbabilityPercent = 0.0,
            thunderstormProbabilityPercent = null,
        )
        sourceConditions.clear()
        sourcePhenomena.clear()

        assertEquals(setOf(WeatherCondition.STORM, WeatherCondition.RAIN), facts.conditions)
        assertEquals(setOf(WeatherPhenomenon.FOG), facts.phenomena)
        assertEquals(0.0, facts.precipitationProbabilityPercent!!, 0.0)
        assertNull(facts.thunderstormProbabilityPercent)
        assertEquals(facts, CanonicalWeatherFacts(
            validTime = validTime,
            conditions = setOf(WeatherCondition.STORM, WeatherCondition.RAIN),
            phenomena = setOf(WeatherPhenomenon.FOG),
            precipitationProbabilityPercent = 0.0,
        ))
        assertNotEquals(facts, CanonicalWeatherFacts(validTime, precipitationProbabilityPercent = 0.0))
        assertThrows(UnsupportedOperationException::class.java) {
            @Suppress("UNCHECKED_CAST")
            (facts.conditions as MutableSet<WeatherCondition>).add(WeatherCondition.CLEAR)
        }
        assertFalse(CanonicalWeatherFacts::class.java.declaredFields.any {
            listOf("alert", "warning", "demo", "scene", "profile").any { marker ->
                it.name.contains(marker, ignoreCase = true)
            }
        })
    }

    @Test
    fun nullableMeasurementsAcceptExplicitZeroAndRejectNonFiniteOrOutOfRangeValues() {
        val zeros = CanonicalWeatherFacts(
            validTime = validTime,
            temperatureC = 0.0,
            precipitationProbabilityPercent = 0.0,
            thunderstormProbabilityPercent = 0.0,
            relativeHumidityPercent = 0.0,
            cloudCoverPercent = 0.0,
            windSpeedKph = 0.0,
            windGustKph = 0.0,
            windDirectionDegrees = 0.0,
            snowDepthCentimeters = 0.0,
            surfacePressureHpa = 100.0,
            meanSeaLevelPressureHpa = 800.0,
            visibilityKm = 0.0,
        )
        assertEquals(0.0, zeros.precipitationProbabilityPercent!!, 0.0)
        assertEquals(0.0, zeros.snowDepthCentimeters!!, 0.0)

        val invalidFacts = listOf(
            { CanonicalWeatherFacts(validTime, temperatureC = Double.NaN) },
            { CanonicalWeatherFacts(validTime, temperatureC = 70.01) },
            { CanonicalWeatherFacts(validTime, precipitationProbabilityPercent = -0.01) },
            { CanonicalWeatherFacts(validTime, thunderstormProbabilityPercent = Double.POSITIVE_INFINITY) },
            { CanonicalWeatherFacts(validTime, relativeHumidityPercent = 100.01) },
            { CanonicalWeatherFacts(validTime, cloudCoverPercent = Double.NEGATIVE_INFINITY) },
            { CanonicalWeatherFacts(validTime, windSpeedKph = 500.01) },
            { CanonicalWeatherFacts(validTime, windGustKph = Double.NaN) },
            { CanonicalWeatherFacts(validTime, windDirectionDegrees = 360.0) },
            { CanonicalWeatherFacts(validTime, snowDepthCentimeters = -0.01) },
            { CanonicalWeatherFacts(validTime, surfacePressureHpa = 99.99) },
            { CanonicalWeatherFacts(validTime, meanSeaLevelPressureHpa = 1100.01) },
            { CanonicalWeatherFacts(validTime, visibilityKm = Double.POSITIVE_INFINITY) },
        )
        invalidFacts.forEach(::assertInvalid)
    }

    @Test
    fun canonicalNumericFieldsAcceptTheirDocumentedUpperBounds() {
        val facts = CanonicalWeatherFacts(
            validTime = validTime,
            temperatureC = 70.0,
            precipitationProbabilityPercent = 100.0,
            thunderstormProbabilityPercent = 100.0,
            relativeHumidityPercent = 100.0,
            cloudCoverPercent = 100.0,
            windSpeedKph = 500.0,
            windGustKph = 500.0,
            windDirectionDegrees = 359.999,
            rainAmountMillimeters = AccumulatedMillimeters(2_000.0, interval),
            totalPrecipitationMillimeters = AccumulatedMillimeters(2_000.0, interval),
            precipitationRate = PrecipitationRate(500.0),
            snowfallCentimeters = AccumulatedSnowfallCentimeters(500.0, interval),
            snowDepthCentimeters = 2_000.0,
            surfacePressureHpa = 1_200.0,
            meanSeaLevelPressureHpa = 1_100.0,
            visibilityKm = 500.0,
        )
        assertEquals(70.0, facts.temperatureC!!, 0.0)
        assertEquals(2_000.0, facts.snowDepthCentimeters!!, 0.0)
        assertEquals(1_100.0, facts.meanSeaLevelPressureHpa!!, 0.0)
    }

    @Test
    fun precipitationAmountsAndSnowMeasurementsKeepDistinctTypesAndIntervals() {
        val rain = AccumulatedMillimeters(0.0, interval)
        val total = AccumulatedMillimeters(2_000.0, interval)
        val snowfall = AccumulatedSnowfallCentimeters(0.0, interval)
        val facts = CanonicalWeatherFacts(
            validTime = validTime,
            rainAmountMillimeters = rain,
            totalPrecipitationMillimeters = total,
            precipitationRate = PrecipitationRate(0.0),
            snowfallCentimeters = snowfall,
            snowDepthCentimeters = 0.0,
        )
        assertSame(rain, facts.rainAmountMillimeters)
        assertSame(total, facts.totalPrecipitationMillimeters)
        assertSame(snowfall, facts.snowfallCentimeters)
        assertTrue(facts.snowDepthCentimeters == 0.0)
        assertEquals(AccumulatedSnowfallCentimeters::class.java, facts.snowfallCentimeters!!.javaClass)

        assertThrows(IllegalArgumentException::class.java) {
            AccumulationInterval(validTime, validTime)
        }
        assertThrows(IllegalArgumentException::class.java) { AccumulatedMillimeters(2_000.01, interval) }
        assertThrows(IllegalArgumentException::class.java) { AccumulatedSnowfallCentimeters(500.01, interval) }
        assertThrows(IllegalArgumentException::class.java) { PrecipitationRate(Double.NaN) }
        assertThrows(IllegalArgumentException::class.java) { PrecipitationRate(500.01) }
        assertThrows(IllegalArgumentException::class.java) {
            CanonicalWeatherFacts(
                validTime = validTime,
                rainAmountMillimeters = AccumulatedMillimeters(
                    1.0,
                    AccumulationInterval(validTime.minusSeconds(7200), validTime.minusSeconds(3600)),
                ),
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            CanonicalWeatherFacts(
                validTime = validTime,
                precipitationRate = PrecipitationRate(
                    1.0,
                    AccumulationInterval(validTime.minusSeconds(7200), validTime.minusSeconds(3600)),
                ),
            )
        }
    }

    @Test
    fun catalogCopiesItsFactListAndKeepsStructuralValueSemantics() {
        val sourceFacts = mutableListOf(CanonicalWeatherFacts(validTime, conditions = setOf(WeatherCondition.CLEAR)))
        val catalog = catalog(CatalogOrigin.LIVE, CatalogProductKind.FORECAST).let {
            CanonicalCatalog(it.location, it.datasetId, it.provenance, it.asOf, sourceFacts)
        }
        sourceFacts.clear()

        assertEquals(1, catalog.facts.size)
        assertEquals(catalog, catalog(CatalogOrigin.LIVE, CatalogProductKind.FORECAST))
        assertThrows(UnsupportedOperationException::class.java) {
            @Suppress("UNCHECKED_CAST")
            (catalog.facts as MutableList<CanonicalWeatherFacts>).clear()
        }
    }

    private fun location() = CatalogLocation(
        id = LocationId("local-location-1"),
        coordinates = GeoCoordinates(41.88, -87.63),
        timeZone = ZoneId.of("America/Chicago"),
    )

    private fun catalog(origin: CatalogOrigin, productKind: CatalogProductKind) = CanonicalCatalog(
        location = location(),
        datasetId = DatasetId("dataset-1"),
        provenance = CatalogProvenance(origin, productKind),
        asOf = Instant.parse("2026-10-27T19:00:00Z"),
        facts = listOf(CanonicalWeatherFacts(validTime, conditions = setOf(WeatherCondition.CLEAR))),
    )

    private fun assertInvalid(block: () -> Any?) {
        assertThrows(IllegalArgumentException::class.java) { block() }
    }
}
