package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SimulatedCatalogSourceTest {
    private val chicago = CatalogLocation(LocationId("chicago"), GeoCoordinates(41.88, -87.63), ZoneId.of("America/Chicago"))
    private val source = SimulatedCatalogSource()

    private fun request(asOf: String = "2026-03-10T18:00:00Z") =
        CatalogLoadRequest(chicago, DatasetId("demo"), Instant.parse(asOf))

    private fun load(request: CatalogLoadRequest = request()): Pair<CanonicalCatalog, CatalogWindow> = runBlocking {
        val registry = CatalogSourceRegistry(listOf(source))
        val result = registry.load(CatalogOrigin.SIMULATED, request) as CatalogLoadResult.Success
        val window = (CatalogWindow.create(result.catalog, result.expectedSupport) as CatalogWindowResult.Success).window
        result.catalog to window
    }

    @Test fun deterministicIdentityAndCompleteFortyFourLocalDates() {
        val req = request()
        val first = runBlocking { source.load(req) as CatalogLoadResult.Success }
        val second = runBlocking { source.load(req) as CatalogLoadResult.Success }
        assertEquals(first.catalog, second.catalog)
        assertEquals(first.expectedSupport.keys, second.expectedSupport.keys)
        first.expectedSupport.forEach { (date, fields) ->
            val other = second.expectedSupport.getValue(date)
            assertEquals(fields.keys, other.keys)
            fields.forEach { (field, value) ->
                when (value) {
                    is ExpectedFieldSupport.Points -> assertEquals(value.instants, (other.getValue(field) as ExpectedFieldSupport.Points).instants)
                    is ExpectedFieldSupport.Intervals -> assertEquals(value.intervals, (other.getValue(field) as ExpectedFieldSupport.Intervals).intervals)
                }
            }
        }
        val (catalog, window) = load(req)
        assertEquals(88, catalog.facts.size)
        assertEquals(44, window.days.size)
        assertEquals(LocalDate.of(2026, 3, 10), window.anchorDate)
        assertEquals(window.anchorDate.minusDays(30), window.days.first().date)
        assertEquals(window.anchorDate.plusDays(13), window.days.last().date)
        assertEquals(30, window.days.count { it.date < window.anchorDate })
        assertEquals(14, window.days.count { it.date >= window.anchorDate })
        assertTrue(window.days.all { it.facts.size == 2 && it.fieldCoverage.values.all { status -> status == CoverageStatus.COMPLETE } })
        assertEquals(CatalogOrigin.SIMULATED, catalog.provenance.origin)
        assertEquals(CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT, catalog.provenance.productKind)
        assertNull(catalog.provenance.issuedAt)
    }

    @Test fun eventSequenceAndNullVersusZeroAreExplicit() {
        val (_, window) = load()
        fun day(offset: Long) = window.days.single { it.date == window.anchorDate.plusDays(offset) }
        listOf(-30L, -29L, 12L, 13L).forEach { offset ->
            assertTrue(day(offset).facts.all { it.conditions == setOf(WeatherCondition.CLEAR) && it.rainAmountMillimeters?.valueMillimeters == 0.0 })
        }
        assertTrue(day(-23).facts.all { WeatherCondition.RAIN in it.conditions && it.rainAmountMillimeters == null && it.precipitationRate == null })
        assertTrue(day(-21).facts.all { it.precipitationRate?.valueMillimetersPerHour == 18.0 && it.rainAmountMillimeters?.valueMillimeters == 18.0 })
        assertTrue(day(-16).facts.all { WeatherPhenomenon.HAZE in it.phenomena && it.visibilityKm == 6.0 })
        assertTrue(day(-15).facts.all { WeatherPhenomenon.HAZE in it.phenomena && it.visibilityKm == 4.0 })
        assertTrue(day(-14).facts.all { WeatherPhenomenon.HAZE !in it.phenomena })
        assertTrue(day(-8).facts.all { WeatherPhenomenon.ICE_PELLETS in it.phenomena })
        assertTrue(day(-4).facts.all { it.snowfallCentimeters?.valueCentimeters == 0.0 && it.snowDepthCentimeters == 7.0 })
        assertTrue(day(2).facts.all { it.thunderstormProbabilityPercent == 27.0 && it.precipitationProbabilityPercent == 35.0 })
        assertTrue(day(3).facts.all { it.thunderstormProbabilityPercent == 90.0 && it.rainAmountMillimeters == null })
        assertEquals(setOf(WeatherPhenomenon.FOG), day(-11).facts.first().phenomena)
        assertTrue(day(-11).facts.last().phenomena.isEmpty())
        assertEquals(setOf(WeatherPhenomenon.FOG), day(9).facts.first().phenomena)
        assertTrue(day(9).facts.last().phenomena.isEmpty())
        assertTrue(window.days.flatMap { it.facts }.all { it.totalPrecipitationMillimeters == null })
    }

    @Test fun springAndFallDstUseLocalDatesAndOneHourIntervals() {
        listOf("2026-03-10T18:00:00Z", "2026-11-03T18:00:00Z").forEach { asOf ->
            val (catalog, window) = load(request(asOf))
            val zone = chicago.timeZone
            assertEquals(44, window.days.size)
            assertEquals(88, catalog.facts.size)
            assertTrue(window.days.all { day ->
                day.facts.map { it.validTime.atZone(zone).toLocalDate() }.toSet() == setOf(day.date) &&
                    day.facts.map { it.validTime.atZone(zone).hour } == listOf(9, 21)
            })
            assertTrue(catalog.facts.flatMap { listOfNotNull(it.rainAmountMillimeters?.interval, it.snowfallCentimeters?.interval) }
                .all { it.end.epochSecond - it.start.epochSecond == 3600L })
            assertTrue(window.days.any { it.localEnd.epochSecond - it.localStart.epochSecond != 86400L })
        }
    }

    @Test fun requestLocationAndDatasetRemainAuthoritative() {
        val tokyo = CatalogLocation(LocationId("tokyo"), GeoCoordinates(35.68, 139.69), ZoneId.of("Asia/Tokyo"))
        val req = CatalogLoadRequest(tokyo, DatasetId("alternate-id"), Instant.parse("2026-03-10T18:00:00Z"))
        val (catalog, window) = load(req)
        assertEquals(tokyo, catalog.location)
        assertEquals(req.datasetId, catalog.datasetId)
        assertEquals(req.asOf, catalog.asOf)
        assertEquals(LocalDate.of(2026, 3, 11), window.anchorDate)
    }
}
