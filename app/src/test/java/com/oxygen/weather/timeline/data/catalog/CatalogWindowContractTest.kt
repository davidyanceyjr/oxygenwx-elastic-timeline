package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogWindowContractTest {
    private val zone = ZoneId.of("America/Chicago")

    @Test
    fun createsExactlyFortyFourLocalDatesAndUsesLocationDateAtAsOf() {
        val result = create("2026-10-28T01:00:00Z")
        val window = result.window()

        assertEquals(LocalDate.parse("2026-10-27"), window.anchorDate)
        assertEquals(44, window.days.size)
        assertEquals(LocalDate.parse("2026-09-27"), window.days.first().date)
        assertEquals(LocalDate.parse("2026-11-09"), window.days.last().date)
        assertEquals(LocalDate.parse("2026-09-27").atStartOfDay(zone).toInstant(), window.startInclusive)
        assertEquals(LocalDate.parse("2026-11-10").atStartOfDay(zone).toInstant(), window.endExclusive)
        assertEquals((0L until 44L).map { LocalDate.parse("2026-09-27").plusDays(it) }, window.days.map { it.date })
        assertTrue(window.days.all { it.facts.isEmpty() })
        assertTrue(window.days.all { it.dayCoverage == CoverageStatus.MISSING })
    }

    @Test
    fun localDaysUseTwentyThreeAndTwentyFiveHourlyIntervalsAcrossDst() {
        val springDate = LocalDate.parse("2026-03-08")
        val springIntervals = hourlyIntervals(springDate)
        val followingDate = springDate.plusDays(1)
        val followingIntervals = hourlyIntervals(followingDate)
        assertEquals(23, springIntervals.size)
        val spring = create(
            "2026-03-08T18:00:00Z",
            support = mapOf(
                springDate to mapOf(
                    CatalogField.RAIN_AMOUNT_MILLIMETERS to ExpectedFieldSupport.Intervals(springIntervals.toSet()),
                ),
                followingDate to mapOf(
                    CatalogField.RAIN_AMOUNT_MILLIMETERS to ExpectedFieldSupport.Intervals(followingIntervals.toSet()),
                ),
            ),
            facts = (springIntervals + followingIntervals).map { hourlyRainFact(it, 1.0) },
        ).window()
        assertEquals(23 * 3600L, spring.day(springDate).localEnd.epochSecond - spring.day(springDate).localStart.epochSecond)
        assertEquals(CoverageStatus.COMPLETE, spring.day(springDate).fieldCoverage[CatalogField.RAIN_AMOUNT_MILLIMETERS])
        assertEquals(23.0, spring.day(springDate).deriveDailyAmount(DailyAmountField.RAIN)!!.value, 0.0)
        assertEquals(CoverageStatus.COMPLETE, spring.day(followingDate).fieldCoverage[CatalogField.RAIN_AMOUNT_MILLIMETERS])
        assertEquals(24.0, spring.day(followingDate).deriveDailyAmount(DailyAmountField.RAIN)!!.value, 0.0)

        val fallDate = LocalDate.parse("2026-11-01")
        val fallIntervals = hourlyIntervals(fallDate)
        assertEquals(25, fallIntervals.size)
        val fall = create("2026-11-01T18:00:00Z").window().day(fallDate)
        assertEquals(25 * 3600L, fall.localEnd.epochSecond - fall.localStart.epochSecond)
    }

    @Test
    fun callerSupportDistinguishesCompletePartialMissingAndExplicitZero() {
        val date = LocalDate.parse("2026-10-27")
        val start = date.atStartOfDay(zone).toInstant()
        val times = (0..2).map { start.plusSeconds(it * 3600L) }
        val result = create(
            "2026-10-27T18:00:00Z",
            support = mapOf(date to mapOf(
                CatalogField.TEMPERATURE_C to ExpectedFieldSupport.Points(times.toSet()),
                CatalogField.WIND_SPEED_KPH to ExpectedFieldSupport.Points(times.take(2).toSet()),
                CatalogField.CLOUD_COVER_PERCENT to ExpectedFieldSupport.Points(times.toSet()),
            )),
            facts = listOf(
                CanonicalWeatherFacts(times[0], temperatureC = 0.0, windSpeedKph = 0.0),
                CanonicalWeatherFacts(times[1], temperatureC = 12.0, windSpeedKph = 5.0),
            ),
        ).window().day(date)

        assertEquals(CoverageStatus.PARTIAL, result.fieldCoverage[CatalogField.TEMPERATURE_C])
        assertEquals(CoverageStatus.COMPLETE, result.fieldCoverage[CatalogField.WIND_SPEED_KPH])
        assertEquals(CoverageStatus.MISSING, result.fieldCoverage[CatalogField.CLOUD_COVER_PERCENT])
        assertEquals(CoverageStatus.PARTIAL, result.dayCoverage)
        assertFalse(result.fieldCoverage.containsKey(CatalogField.VISIBILITY_KM))
    }

    @Test
    fun normalizationSortsAndCollapsesOnlyIdenticalFacts() {
        val date = LocalDate.parse("2026-10-27")
        val start = date.atStartOfDay(zone).toInstant()
        val early = CanonicalWeatherFacts(start.plusSeconds(3600), temperatureC = 4.0)
        val late = CanonicalWeatherFacts(start.plusSeconds(7200), temperatureC = 7.0)
        val result = create("2026-10-27T18:00:00Z", facts = listOf(late, early, early)).window().day(date)
        assertEquals(listOf(early, late), result.facts)

        val conflict = create(
            "2026-10-27T18:00:00Z",
            facts = listOf(early, CanonicalWeatherFacts(early.validTime, temperatureC = 5.0)),
        )
        assertEquals(
            CatalogWindowError.CONFLICTING_DUPLICATE_TIME(early.validTime),
            (conflict as CatalogWindowResult.Failure).error,
        )
    }

    @Test
    fun allowsAccumulationAtExclusiveEndButRejectsPointFactsThere() {
        val window = create("2026-10-27T18:00:00Z").window()
        val inclusiveStart = create(
            "2026-10-27T18:00:00Z",
            facts = listOf(CanonicalWeatherFacts(window.startInclusive)),
        )
        assertTrue(inclusiveStart is CatalogWindowResult.Success)

        val finalDate = window.days.last().date
        val finalIntervals = hourlyIntervals(finalDate)
        assertEquals(window.endExclusive, finalIntervals.last().end)
        val finalDay = create(
            "2026-10-27T18:00:00Z",
            support = mapOf(finalDate to mapOf(
                CatalogField.RAIN_AMOUNT_MILLIMETERS to ExpectedFieldSupport.Intervals(finalIntervals.toSet()),
            )),
            facts = finalIntervals.map { hourlyRainFact(it, 1.0) },
        ).window().day(finalDate)
        assertEquals(CoverageStatus.COMPLETE, finalDay.fieldCoverage[CatalogField.RAIN_AMOUNT_MILLIMETERS])
        assertEquals(24.0, finalDay.deriveDailyAmount(DailyAmountField.RAIN)!!.value, 0.0)

        val exclusiveEnd = create(
            "2026-10-27T18:00:00Z",
            facts = listOf(CanonicalWeatherFacts(window.endExclusive, temperatureC = 4.0)),
        )
        assertEquals(
            CatalogWindowError.OUT_OF_WINDOW_FACT(window.endExclusive),
            (exclusiveEnd as CatalogWindowResult.Failure).error,
        )
    }

    @Test
    fun derivedDailyAccumulationRequiresExactCompleteDaySupport() {
        val date = LocalDate.parse("2026-03-08")
        val intervals = hourlyIntervals(date)
        val support = mapOf(date to mapOf(
            CatalogField.TOTAL_PRECIPITATION_MILLIMETERS to ExpectedFieldSupport.Intervals(intervals.toSet()),
            CatalogField.SNOWFALL_CENTIMETERS to ExpectedFieldSupport.Intervals(intervals.toSet()),
        ))
        val facts = intervals.map { interval ->
            CanonicalWeatherFacts(
                validTime = interval.end,
                totalPrecipitationMillimeters = AccumulatedMillimeters(2.0, interval),
                snowfallCentimeters = AccumulatedSnowfallCentimeters(0.5, interval),
            )
        }
        val complete = create("2026-03-08T18:00:00Z", support, facts).window().day(date)
        assertEquals(46.0, complete.deriveDailyAmount(DailyAmountField.TOTAL_PRECIPITATION)!!.value, 0.0)
        val derivedSnow = complete.deriveDailyAmount(DailyAmountField.SNOWFALL)!!
        assertEquals(11.5, derivedSnow.value, 0.0)
        assertEquals(DailyAmountUnit.CENTIMETERS, derivedSnow.unit)
        assertEquals(DailyValueOrigin.DERIVED, derivedSnow.origin)
        assertEquals(complete.localStart, derivedSnow.support.start)
        assertEquals(complete.localEnd, derivedSnow.support.end)

        val partial = create("2026-03-08T18:00:00Z", support, facts.drop(1)).window().day(date)
        assertEquals(CoverageStatus.PARTIAL, partial.fieldCoverage[CatalogField.TOTAL_PRECIPITATION_MILLIMETERS])
        assertNull(partial.deriveDailyAmount(DailyAmountField.TOTAL_PRECIPITATION))
    }

    @Test
    fun providerDailyZeroRemainsSuppliedWhenHourlySupportIsMissing() {
        val date = LocalDate.parse("2026-10-27")
        val start = date.atStartOfDay(zone).toInstant()
        val end = date.plusDays(1).atStartOfDay(zone).toInstant()
        val day = create(
            "2026-10-27T18:00:00Z",
            providerDailyAmounts = mapOf(date to listOf(
                DailyAccumulatedAmount(
                    DailyAmountField.RAIN,
                    0.0,
                    DailyAmountUnit.MILLIMETERS,
                    DailyValueOrigin.PROVIDER_SUPPLIED,
                    AccumulationInterval(start, end),
                ),
            )),
        ).window().day(date)

        assertEquals(0.0, day.providerDailyAmounts.single().value, 0.0)
        assertEquals(DailyValueOrigin.PROVIDER_SUPPLIED, day.providerDailyAmounts.single().origin)
        assertNull(day.deriveDailyAmount(DailyAmountField.RAIN))
    }

    @Test
    fun expectedSupportIsSnapshottedFromCallerCollections() {
        val date = LocalDate.parse("2026-10-27")
        val intervals = hourlyIntervals(date).toMutableSet()
        val fields = mutableMapOf<CatalogField, ExpectedFieldSupport>(
            CatalogField.RAIN_AMOUNT_MILLIMETERS to ExpectedFieldSupport.Intervals(intervals),
        )
        val support = mutableMapOf(date to fields)
        val result = create(
            "2026-10-27T18:00:00Z",
            support = support,
            facts = intervals.map { hourlyRainFact(it, 1.0) },
        ).window()

        intervals.clear()
        fields.clear()
        support.clear()

        assertEquals(24.0, result.day(date).deriveDailyAmount(DailyAmountField.RAIN)!!.value, 0.0)
    }

    private fun create(
        asOf: String,
        support: Map<LocalDate, Map<CatalogField, ExpectedFieldSupport>> = emptyMap(),
        facts: List<CanonicalWeatherFacts> = emptyList(),
        providerDailyAmounts: Map<LocalDate, List<DailyAccumulatedAmount>> = emptyMap(),
    ): CatalogWindowResult = CatalogWindow.create(
        CanonicalCatalog(
            location = CatalogLocation(LocationId("test"), GeoCoordinates(41.0, -87.0), zone),
            datasetId = DatasetId("test-dataset"),
            provenance = CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.FORECAST),
            asOf = Instant.parse(asOf),
            facts = facts,
        ),
        support,
        providerDailyAmounts,
    )

    private fun CatalogWindowResult.window(): CatalogWindow = (this as CatalogWindowResult.Success).window

    private fun CatalogWindow.day(date: LocalDate): CatalogDay = days.single { it.date == date }

    private fun hourlyIntervals(date: LocalDate): List<AccumulationInterval> {
        val start = date.atStartOfDay(zone).toInstant()
        val end = date.plusDays(1).atStartOfDay(zone).toInstant()
        val result = mutableListOf<AccumulationInterval>()
        var cursor = start
        while (cursor < end) {
            val next = cursor.plusSeconds(3600)
            result += AccumulationInterval(cursor, next)
            cursor = next
        }
        return result
    }

    private fun hourlyRainFact(interval: AccumulationInterval, value: Double) = CanonicalWeatherFacts(
        validTime = interval.end,
        rainAmountMillimeters = AccumulatedMillimeters(value, interval),
    )
}
