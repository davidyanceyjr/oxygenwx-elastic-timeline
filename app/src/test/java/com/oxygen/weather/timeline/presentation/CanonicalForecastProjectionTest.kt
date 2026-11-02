package com.oxygen.weather.timeline.presentation

import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.catalog.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.roundToInt

class CanonicalForecastProjectionTest {
    private val zone = ZoneId.of("America/Chicago")

    @Test
    fun keepsSelectedInstantSeparateFromNearestExactFactAndSceneBlend() {
        val date = LocalDate.parse("2026-10-28")
        val nine = date.atTime(9, 0).atZone(zone).toInstant()
        val ten = date.atTime(10, 0).atZone(zone).toInstant()
        val facts = listOf(
            CanonicalWeatherFacts(nine, temperatureC = 10.0, cloudCoverPercent = 20.0),
            CanonicalWeatherFacts(ten, temperatureC = 20.0, cloudCoverPercent = 80.0, windSpeedKph = 16.09344),
        )
        val (catalog, window) = snapshot(date, facts)
        val selected = date.atTime(9, 47).atZone(zone).toInstant()

        val output = CanonicalForecastProjection.project(catalog, window, selected, ForecastDisplayUnits.IMPERIAL)

        assertEquals(selected, output.selectedInstant)
        assertEquals(ten, output.factSample!!.validTime)
        assertEquals(facts[1], output.factSample.facts)
        assertEquals(68.0, output.factSample.temperature!!.displayValue, 0.0)
        assertEquals(10.0, output.factSample.windSpeed!!.displayValue, 0.00001)
        assertNull(output.factSample.facts.precipitationProbabilityPercent)
        assertNull(output.factSample.facts.rainAmountMillimeters)
        assertEquals(0.7833333333, output.scene!!.fraction, 0.000001)
        assertEquals(10.0, output.scene.from.temperatureC!!, 0.0)
        assertEquals(20.0, output.scene.to.temperatureC!!, 0.0)
        val metric = CanonicalForecastProjection.project(catalog, window, selected, ForecastDisplayUnits.METRIC)
        assertEquals(20.0, metric.factSample!!.temperature!!.displayValue, 0.0)
        assertEquals(16.09344, metric.factSample.windSpeed!!.displayValue, 0.0)
    }

    @Test
    fun diagnosticsUseDeclaredSampleSupportAndKeepZeroDistinctFromMissing() {
        val date = LocalDate.parse("2026-10-28")
        val valid = date.atTime(9, 0).atZone(zone).toInstant()
        val rainBounds = AccumulationInterval(valid.minusSeconds(3600), valid)
        val facts = CanonicalWeatherFacts(
            validTime = valid,
            temperatureC = 0.0,
            rainAmountMillimeters = AccumulatedMillimeters(0.0, rainBounds),
        )
        val expected = mapOf(date to mapOf(
            CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(setOf(valid)),
            CatalogField.WIND_SPEED_KPH to ExpectedFieldSupport.Points(setOf(valid)),
            CatalogField.RAIN_AMOUNT_MILLIMETERS to ExpectedFieldSupport.Intervals(setOf(rainBounds)),
            // This declaration does not match the sample and must not imply missingness.
            CatalogField.VISIBILITY_KM to ExpectedFieldSupport.Points(setOf(valid.plusSeconds(3600))),
        ))
        val (catalog, _) = snapshot(date, listOf(facts), support = expected)
        val active = ActiveCatalogSelection(
            location = catalog.location,
            recording = CatalogRecording(catalog, expected),
            origin = CatalogOrigin.LIVE,
            datasetId = catalog.datasetId,
            replayRecordingId = null,
            delivery = CatalogActivationDelivery.SOURCE,
            freshness = CatalogFreshness.FRESH,
            storedAt = null,
            selectedInstant = valid.plusSeconds(900),
            viewGeneration = 1L,
        )
        val diagnostics = CatalogTimelinePresenter(active).scenePresentation(
            CatalogActivationState(active = active),
        ).diagnostics

        assertTrue(diagnostics.selection.first().contains("Selected"))
        assertTrue(diagnostics.selection.first().contains("sample"))
        assertTrue(diagnostics.selection.any { it.contains("Point fields @") && it.contains("temp") })
        assertTrue(diagnostics.selection.any { it.contains("Intervals: rain") })
        assertEquals(listOf("wind"), diagnostics.missingFields)
    }

    @Test
    fun tiesChooseEarlierSampleAndDoNotBorrowAcrossDayBoundary() {
        val date = LocalDate.parse("2026-10-28")
        val nine = date.atTime(9, 0).atZone(zone).toInstant()
        val ten = date.atTime(10, 0).atZone(zone).toInstant()
        val (catalog, window) = snapshot(date, listOf(
            CanonicalWeatherFacts(nine, temperatureC = 9.0),
            CanonicalWeatherFacts(ten, temperatureC = 10.0),
            CanonicalWeatherFacts(date.plusDays(1).atStartOfDay(zone).toInstant(), temperatureC = 69.0),
        ))
        val tie = date.atTime(9, 30).atZone(zone).toInstant()

        val output = CanonicalForecastProjection.project(catalog, window, tie, ForecastDisplayUnits.METRIC)
        assertEquals(nine, output.factSample!!.validTime)
        assertEquals(0.5, output.scene!!.fraction, 0.0)
        val nextDate = date.plusDays(1).atTime(0, 0).atZone(zone).toInstant()
        val boundary = CanonicalForecastProjection.project(catalog, window, nextDate, ForecastDisplayUnits.METRIC)
        assertEquals(nextDate, boundary.selectedInstant)
        assertEquals(69.0, boundary.factSample!!.facts.temperatureC!!, 0.0)
        assertEquals(69.0, boundary.scene!!.from.temperatureC!!, 0.0)
        assertEquals(69.0, boundary.scene.to.temperatureC!!, 0.0)
        assertEquals(0.0, boundary.scene.fraction, 0.0)
    }

    @Test
    fun emptyDateAndUnknownMeasurementsRemainUnavailable() {
        val date = LocalDate.parse("2026-10-28")
        val (catalog, window) = snapshot(date, listOf(CanonicalWeatherFacts(date.atTime(1, 0).atZone(zone).toInstant())))
        val emptyDateSelection = date.plusDays(1).atTime(8, 0).atZone(zone).toInstant()

        val output = CanonicalForecastProjection.project(catalog, window, emptyDateSelection, ForecastDisplayUnits.METRIC)

        assertNull(output.factSample)
        assertNull(output.scene)
        assertNull(output.dailyHigh.canonicalCelsius)
        assertNull(output.dailyLow.canonicalCelsius)
        val outside = CanonicalForecastProjection.project(
            catalog, window, date.minusDays(31).atStartOfDay(zone).toInstant(), ForecastDisplayUnits.METRIC,
        )
        assertEquals(date.minusDays(31), outside.selectedDate)
        assertNull(outside.factSample)
    }

    @Test
    fun incompleteTemperatureCoverageDoesNotDeriveMissingDailyExtrema() {
        val date = LocalDate.parse("2026-10-28")
        val start = date.atStartOfDay(zone).toInstant()
        val times = listOf(start, start.plusSeconds(3600))
        val summary = ProviderDailySummary(
            date, AccumulationInterval(start, date.plusDays(1).atStartOfDay(zone).toInstant()), temperatureMinC = 1.0,
        )
        val (catalog, window) = snapshot(
            date, listOf(CanonicalWeatherFacts(times[0], temperatureC = 4.0)),
            support = mapOf(date to mapOf(CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(times.toSet()))),
            summary = summary,
            dailySupport = mapOf(date to setOf(DailyCatalogField.TEMPERATURE_MAX_C, DailyCatalogField.TEMPERATURE_MIN_C)),
        )

        val output = CanonicalForecastProjection.project(catalog, window, start, ForecastDisplayUnits.METRIC)
        assertNull(output.dailyHigh.canonicalCelsius)
        assertNull(output.dailyHigh.origin)
        assertEquals(1.0, output.dailyLow.canonicalCelsius!!, 0.0)
        assertEquals(DailyValueOrigin.PROVIDER_SUPPLIED, output.dailyLow.origin)
    }

    @Test
    fun dailyTemperaturePrefersProviderValuesAndDerivesMissingExtremeOnlyWithCompleteCoverage() {
        val date = LocalDate.parse("2026-10-28")
        val start = date.atStartOfDay(zone).toInstant()
        val hours = listOf(start, start.plusSeconds(3600), start.plusSeconds(7200))
        val facts = listOf(2.0, 8.0, 5.0).mapIndexed { i, temp -> CanonicalWeatherFacts(hours[i], temperatureC = temp) }
        val summary = ProviderDailySummary(date, AccumulationInterval(start, date.plusDays(1).atStartOfDay(zone).toInstant()), temperatureMaxC = 12.0)
        val (catalog, window) = snapshot(
            date, facts,
            support = mapOf(date to mapOf(CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(hours.toSet()))),
            summary = summary,
            dailySupport = mapOf(date to setOf(DailyCatalogField.TEMPERATURE_MAX_C, DailyCatalogField.TEMPERATURE_MIN_C)),
        )

        val output = CanonicalForecastProjection.project(catalog, window, hours.first(), ForecastDisplayUnits.IMPERIAL)

        assertEquals(12.0, output.dailyHigh.canonicalCelsius!!, 0.0)
        assertEquals(DailyValueOrigin.PROVIDER_SUPPLIED, output.dailyHigh.origin)
        assertEquals(2.0, output.dailyLow.canonicalCelsius!!, 0.0)
        assertEquals(DailyValueOrigin.DERIVED, output.dailyLow.origin)
        assertEquals(35.6, output.dailyLow.displayTemperature!!.displayValue, 0.00001)
    }

    @Test
    fun handlesTwentyThreeAndTwentyFiveHourDaysAndSnowIdentity() {
        for ((date, expectedHours) in listOf(LocalDate.parse("2026-03-08") to 23, LocalDate.parse("2026-11-01") to 25)) {
            val start = date.atStartOfDay(zone).toInstant()
            val facts = (0 until expectedHours).map { index ->
                CanonicalWeatherFacts(
                    start.plusSeconds(index * 3600L),
                    conditions = if (index == 0) setOf(WeatherCondition.SNOW) else emptySet(),
                    temperatureC = index.toDouble(),
                )
            }
            val points = facts.map { it.validTime }.toSet()
            val (catalog, window) = snapshot(
                date, facts,
                support = mapOf(date to mapOf(CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(points))),
            )
            val last = CanonicalForecastProjection.project(catalog, window, facts.last().validTime, ForecastDisplayUnits.METRIC)
            assertEquals(expectedHours * 3600L, window.days.first { it.date == date }.localEnd.epochSecond - start.epochSecond)
            assertEquals(SnowVisualIdentity.SNOW, CanonicalForecastProjection.project(catalog, window, start, ForecastDisplayUnits.METRIC).factSample!!.snow)
            assertEquals(expectedHours - 1.0, last.dailyHigh.canonicalCelsius!!, 0.0)
        }
    }

    @Test
    fun archivedProductMeaningAndProvenanceAreRetained() {
        val date = LocalDate.parse("2026-10-28")
        val segment = CatalogProductSegment(date, date.plusDays(1), CatalogProductKind.ARCHIVED_MODEL_FORECAST, "provider", "model", null)
        val (catalog, window) = snapshot(
            date, listOf(CanonicalWeatherFacts(date.atTime(12, 0).atZone(zone).toInstant())),
            kind = CatalogProductKind.ARCHIVED_MODEL_FORECAST, segments = listOf(segment),
        )

        val output = CanonicalForecastProjection.project(catalog, window, date.atTime(12, 0).atZone(zone).toInstant(), ForecastDisplayUnits.METRIC)
        assertEquals(ForecastMeaning.ARCHIVED_MODEL_FORECAST, output.meaning)
        assertSame(catalog.provenance, output.provenance)
        assertEquals(segment, output.productSegment)
    }

    @Test
    fun midnightAccumulationFactDoesNotBecomeHourlySampleOrExtrema() {
        val date = LocalDate.parse("2026-10-28")
        val start = date.atStartOfDay(zone).toInstant()
        val end = date.plusDays(1).atStartOfDay(zone).toInstant()
        val interval = AccumulationInterval(end.minusSeconds(3600), end)
        val facts = listOf(
            CanonicalWeatherFacts(start, temperatureC = 3.0),
            CanonicalWeatherFacts(end, temperatureC = 69.0, rainAmountMillimeters = AccumulatedMillimeters(1.0, interval)),
        )
        val (catalog, window) = snapshot(
            date, facts,
            support = mapOf(date to mapOf(CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(setOf(start)))),
        )

        val output = CanonicalForecastProjection.project(catalog, window, end.minusSeconds(600), ForecastDisplayUnits.METRIC)
        assertEquals(start, output.factSample!!.validTime)
        assertEquals(3.0, output.dailyHigh.canonicalCelsius!!, 0.0)
        assertNull(output.dailyAmountOrigins[DailyAmountField.RAIN])
    }

    @Test
    fun legacyFixtureProjectionStillUsesItsExistingExactSampleValues() {
        val index = 1
        val source = ForecastProjection.fromHourOffset(index.toDouble(), precision = true)
        val fixture = SampleForecast.hours[index]
        assertEquals((fixture.temperatureC * 9.0 / 5.0 + 32.0).roundToInt(), source.temperatureF)
        assertEquals((fixture.windKph / 1.609344).roundToInt(), source.windMph)
        assertEquals(fixture.precipitationProbabilityPercent.roundToInt(), source.precipitationPercent)
        assertTrue(source.isPrecision)
    }

    private fun snapshot(
        date: LocalDate,
        facts: List<CanonicalWeatherFacts>,
        support: Map<LocalDate, Map<CatalogField, ExpectedFieldSupport>> = emptyMap(),
        summary: ProviderDailySummary? = null,
        dailySupport: Map<LocalDate, Set<DailyCatalogField>> = emptyMap(),
        kind: CatalogProductKind = CatalogProductKind.FORECAST,
        segments: List<CatalogProductSegment> = emptyList(),
    ): Pair<CanonicalCatalog, CatalogWindow> {
        val catalog = CanonicalCatalog(
            CatalogLocation(LocationId("test"), GeoCoordinates(41.0, -87.0), zone),
            DatasetId("test-${date}"),
            CatalogProvenance(CatalogOrigin.LIVE, kind, "source", "product"),
            date.atTime(12, 0).atZone(zone).toInstant(), facts, segments,
            listOfNotNull(summary),
        )
        val window = (CatalogWindow.create(catalog, support, dailyExpectedSupport = dailySupport) as CatalogWindowResult.Success).window
        return catalog to window
    }
}
