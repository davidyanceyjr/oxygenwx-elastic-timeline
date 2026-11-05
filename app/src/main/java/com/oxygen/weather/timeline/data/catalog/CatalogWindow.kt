package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Collections

/** One independently measurable field in the provider-neutral hourly fact contract. */
enum class CatalogField {
    CONDITIONS,
    PHENOMENA,
    TEMPERATURE_C,
    PRECIPITATION_PROBABILITY_PERCENT,
    THUNDERSTORM_PROBABILITY_PERCENT,
    RELATIVE_HUMIDITY_PERCENT,
    CLOUD_COVER_PERCENT,
    WIND_SPEED_KPH,
    WIND_GUST_KPH,
    WIND_DIRECTION_DEGREES,
    RAIN_AMOUNT_MILLIMETERS,
    TOTAL_PRECIPITATION_MILLIMETERS,
    PRECIPITATION_RATE,
    SNOWFALL_CENTIMETERS,
    SNOW_DEPTH_CENTIMETERS,
    SURFACE_PRESSURE_HPA,
    MEAN_SEA_LEVEL_PRESSURE_HPA,
    VISIBILITY_KM,
}

/** Explicit product support for one field on one local date. */
sealed interface ExpectedFieldSupport {
    class Points(instants: Set<Instant>) : ExpectedFieldSupport {
        val instants: Set<Instant> = Collections.unmodifiableSet(instants.toSet())

        init { require(this.instants.isNotEmpty()) { "Expected point support must not be empty" } }
    }

    class Intervals(intervals: Set<AccumulationInterval>) : ExpectedFieldSupport {
        val intervals: Set<AccumulationInterval> = Collections.unmodifiableSet(intervals.toSet())

        init { require(this.intervals.isNotEmpty()) { "Expected interval support must not be empty" } }
    }
}

enum class CoverageStatus { COMPLETE, PARTIAL, MISSING }
enum class DailyValueOrigin { PROVIDER_SUPPLIED, DERIVED }
enum class DailyAmountField { RAIN, TOTAL_PRECIPITATION, SNOWFALL }
enum class DailyAmountUnit { MILLIMETERS, CENTIMETERS }

/** A daily accumulated amount with explicit field, provenance, and local-day support. */
data class DailyAccumulatedAmount(
    val field: DailyAmountField,
    val value: Double,
    val unit: DailyAmountUnit,
    val origin: DailyValueOrigin,
    val support: AccumulationInterval,
) {
    init {
        require(value.isFinite() && value >= 0.0) { "Daily amount must be finite and non-negative" }
        require(
            (field == DailyAmountField.SNOWFALL) == (unit == DailyAmountUnit.CENTIMETERS),
        ) { "Snowfall uses centimeters; rain and total precipitation use millimeters" }
    }
}

sealed interface CatalogWindowError {
    data class OUT_OF_WINDOW_FACT(val validTime: Instant) : CatalogWindowError
    data class CONFLICTING_DUPLICATE_TIME(val validTime: Instant) : CatalogWindowError
    data class INVALID_EXPECTED_SUPPORT(val reason: String) : CatalogWindowError
    data class INVALID_DAILY_VALUE(val date: LocalDate, val reason: String) : CatalogWindowError
}

sealed interface CatalogWindowResult {
    data class Success(val window: CatalogWindow) : CatalogWindowResult
    data class Failure(val error: CatalogWindowError) : CatalogWindowResult
}

/** A location-local D-30 through D+13 window with normalized facts and all 44 dates. */
class CatalogWindow private constructor(
    val anchorDate: LocalDate,
    val zoneId: ZoneId,
    val startInclusive: Instant,
    val endExclusive: Instant,
    days: List<CatalogDay>,
) {
    val days: List<CatalogDay> = Collections.unmodifiableList(days.toList())

    /** Exact supplied hour cell for typed events; a gap never borrows another hour. */
    fun typedEventFactAt(selected: Instant): CanonicalWeatherFacts? {
        val day = days.firstOrNull { selected >= it.localStart && selected < it.localEnd } ?: return null
        return day.facts.firstOrNull { fact ->
            fact.events.isNotEmpty() && selected >= fact.validTime && selected < fact.validTime.plusSeconds(3600)
        }
    }

    companion object {
        /**
         * Builds the fixed 44-local-date window. Expected support is supplied by the caller per
         * date and field; this type does not assume a field is supported by every product.
         */
        fun create(
            catalog: CanonicalCatalog,
            expectedSupport: Map<LocalDate, Map<CatalogField, ExpectedFieldSupport>>,
            providerDailyAmounts: Map<LocalDate, List<DailyAccumulatedAmount>> = emptyMap(),
            dailyExpectedSupport: DailyExpectedSupport = emptyMap(),
        ): CatalogWindowResult {
            val zone = catalog.location.timeZone
            val anchor = catalog.asOf.atZone(zone).toLocalDate()
            val dates = (-30L..13L).map(anchor::plusDays)
            val start = dates.first().atStartOfDay(zone).toInstant()
            val end = anchor.plusDays(14).atStartOfDay(zone).toInstant()
            val dateSet = dates.toSet()

            if ((expectedSupport.keys + providerDailyAmounts.keys + dailyExpectedSupport.keys + catalog.dailySummaries.map { it.date }).any { it !in dateSet }) {
                return CatalogWindowResult.Failure(
                    CatalogWindowError.INVALID_EXPECTED_SUPPORT("Support or daily values name a date outside the 44-day window"),
                )
            }

            if (catalog.dailySummaries.map { it.date }.distinct().size != catalog.dailySummaries.size ||
                catalog.dailySummaries.any { summary ->
                    summary.support.start != summary.date.atStartOfDay(zone).toInstant() ||
                        summary.support.end != summary.date.plusDays(1).atStartOfDay(zone).toInstant() ||
                        summary.date !in dailyExpectedSupport
                }) return invalidSupport("Daily summaries must be unique and use true local-day support")

            var previousEnd: LocalDate? = null
            var previousSegment: CatalogProductSegment? = null
            for (segment in catalog.productSegments) {
                if (segment.startDateInclusive !in dateSet || segment.endDateExclusive > anchor.plusDays(14) ||
                    segment.startDateInclusive >= segment.endDateExclusive ||
                    (previousEnd != null && segment.startDateInclusive < previousEnd!!)) {
                    return invalidSupport("Product segments must be ordered, disjoint, and within the 44-date window")
                }
                if (previousSegment?.let { it.endDateExclusive == segment.startDateInclusive &&
                        it.productKind == segment.productKind && it.sourceId == segment.sourceId &&
                        it.productId == segment.productId && it.issuedAt == segment.issuedAt } == true) {
                    return invalidSupport("Adjacent product segments with identical identity must be coalesced")
                }
                previousEnd = segment.endDateExclusive
                previousSegment = segment
            }
            if (catalog.productSegments.isNotEmpty()) {
                val segmentKinds = catalog.productSegments.map { it.productKind }.distinct()
                val expectedEnvelopeKind = if (segmentKinds.size > 1) CatalogProductKind.MIXED else segmentKinds.single()
                if (catalog.provenance.productKind != expectedEnvelopeKind) return invalidSupport("Catalog product kind must match its product segments")
                val assignedDates = catalog.productSegments.flatMap { segment -> generateSequence(segment.startDateInclusive) { it.plusDays(1) }.takeWhile { it < segment.endDateExclusive }.toList() }.toSet()
                val returnedDates = (catalog.facts.mapNotNull { fact -> fact.validTime.atZone(zone).toLocalDate().takeIf { it in dateSet } } + catalog.dailySummaries.map { it.date }).toSet()
                if (assignedDates != returnedDates) return invalidSupport("Product segments must cover exactly dates with returned rows")
            }

            for ((date, fields) in expectedSupport) {
                val localStart = date.atStartOfDay(zone).toInstant()
                val localEnd = date.plusDays(1).atStartOfDay(zone).toInstant()
                for ((field, support) in fields) {
                    val isAccumulated = field in ACCUMULATED_FIELDS
                    when {
                        isAccumulated && support !is ExpectedFieldSupport.Intervals ->
                            return invalidSupport("$field requires interval support")
                        !isAccumulated && support !is ExpectedFieldSupport.Points ->
                            return invalidSupport("$field requires point support")
                        support is ExpectedFieldSupport.Points && support.instants.any {
                            it < localStart || it >= localEnd
                        } -> return invalidSupport("Point support for $field falls outside $date")
                        support is ExpectedFieldSupport.Intervals && support.intervals.any {
                            it.start < localStart || it.end > localEnd
                        } -> return invalidSupport("Interval support for $field falls outside $date")
                        support is ExpectedFieldSupport.Intervals && support.intervals.any {
                            it.end.epochSecond - it.start.epochSecond != 3600L ||
                                it.end.nano != it.start.nano
                        } -> return invalidSupport("Accumulated support for $field must use one-hour intervals")
                        support is ExpectedFieldSupport.Intervals && hasOverlap(support.intervals) ->
                            return invalidSupport("Expected intervals for $field overlap on $date")
                    }
                }
            }

            val normalized = normalizeFacts(
                catalog.facts,
                start,
                end,
                dates.last().atStartOfDay(zone).toInstant(),
            )
            if (normalized is Normalization.Failure) return CatalogWindowResult.Failure(normalized.error)
            val facts = (normalized as Normalization.Success).facts
            val days = dates.map { date ->
                val localStart = date.atStartOfDay(zone).toInstant()
                val localEnd = date.plusDays(1).atStartOfDay(zone).toInstant()
                // Accumulation facts are dated by their support as well as validTime: an interval
                // ending at local midnight supports the preceding date, though validTime labels
                // the following date.
                val dayFacts = facts.filter { fact ->
                    fact.validTime.atZone(zone).toLocalDate() == date ||
                        ACCUMULATED_FIELDS.any { field ->
                            fact.intervalFor(field)?.let { it.start >= localStart && it.end <= localEnd } == true
                        }
                }
                val fieldSupport = expectedSupport[date].orEmpty()
                val coverage = fieldSupport.mapValues { (field, support) ->
                    coverageFor(field, support, dayFacts, localStart, localEnd)
                }
                val dailyValues = providerDailyAmounts[date].orEmpty()
                if (dailyValues.map { it.field }.distinct().size != dailyValues.size || dailyValues.any {
                        it.origin != DailyValueOrigin.PROVIDER_SUPPLIED ||
                            it.support.start != localStart || it.support.end != localEnd
                    }
                ) {
                    return CatalogWindowResult.Failure(
                        CatalogWindowError.INVALID_DAILY_VALUE(date, "Provider daily values must be unique by field and cover the complete local date"),
                    )
                }
                val suppliedSummary = catalog.dailySummaries.singleOrNull { it.date == date }
                val dailyCoverage = dailyExpectedSupport[date].orEmpty().associateWith { field ->
                    if (suppliedSummary?.has(field) == true) CoverageStatus.COMPLETE else CoverageStatus.MISSING
                }
                CatalogDay(
                    date = date,
                    localStart = localStart,
                    localEnd = localEnd,
                    facts = dayFacts,
                    fieldCoverage = coverage,
                    dayCoverage = aggregateDayCoverage(coverage.values + dailyCoverage.values),
                    expectedSupport = fieldSupport,
                    providerDailyAmounts = dailyValues,
                    dailySummary = suppliedSummary,
                    dailyCoverage = dailyCoverage,
                )
            }
            return CatalogWindowResult.Success(CatalogWindow(anchor, zone, start, end, days))
        }

        private fun invalidSupport(reason: String) =
            CatalogWindowResult.Failure(CatalogWindowError.INVALID_EXPECTED_SUPPORT(reason))

        private fun normalizeFacts(
            input: List<CanonicalWeatherFacts>,
            start: Instant,
            end: Instant,
            finalDayStart: Instant,
        ): Normalization {
            input.firstOrNull {
                it.validTime < start || it.validTime > end ||
                    (it.validTime == end && !isBoundaryAccumulation(it, finalDayStart, end))
            }?.let {
                return Normalization.Failure(CatalogWindowError.OUT_OF_WINDOW_FACT(it.validTime))
            }
            val normalized = ArrayList<CanonicalWeatherFacts>()
            for ((validTime, sameTime) in input.groupBy { it.validTime }.toSortedMap()) {
                val first = sameTime.first()
                if (sameTime.any { it != first }) {
                    return Normalization.Failure(CatalogWindowError.CONFLICTING_DUPLICATE_TIME(validTime))
                }
                normalized += first
            }
            return Normalization.Success(normalized)
        }

        private fun isBoundaryAccumulation(
            fact: CanonicalWeatherFacts,
            finalDayStart: Instant,
            endExclusive: Instant,
        ): Boolean {
            val intervals = listOfNotNull(
                fact.rainAmountMillimeters?.interval,
                fact.totalPrecipitationMillimeters?.interval,
                fact.snowfallCentimeters?.interval,
            )
            return intervals.isNotEmpty() &&
                fact.conditions.isEmpty() && fact.phenomena.isEmpty() &&
                fact.temperatureC == null &&
                fact.precipitationProbabilityPercent == null &&
                fact.thunderstormProbabilityPercent == null &&
                fact.relativeHumidityPercent == null &&
                fact.cloudCoverPercent == null &&
                fact.windSpeedKph == null && fact.windGustKph == null &&
                fact.windDirectionDegrees == null && fact.precipitationRate == null &&
                fact.snowDepthCentimeters == null && fact.surfacePressureHpa == null &&
                fact.meanSeaLevelPressureHpa == null && fact.visibilityKm == null &&
                intervals.all { it.start >= finalDayStart && it.end == endExclusive }
        }

        private fun coverageFor(
            field: CatalogField,
            support: ExpectedFieldSupport,
            facts: List<CanonicalWeatherFacts>,
            localStart: Instant,
            localEnd: Instant,
        ): CoverageStatus {
            val matched = when (support) {
                is ExpectedFieldSupport.Points -> support.instants.count { instant ->
                    facts.firstOrNull { it.validTime == instant }?.hasValue(field) == true
                }
                is ExpectedFieldSupport.Intervals -> support.intervals.count { interval ->
                    facts.any { it.validTime == interval.end && it.intervalFor(field) == interval }
                }
            }
            val localIntervals = facts.mapNotNull { it.intervalFor(field) }
                .filter { it.start < localEnd && it.end > localStart }
                .toSet()
            val hasUnexpectedIntervals = support is ExpectedFieldSupport.Intervals &&
                localIntervals != support.intervals
            return when {
                matched == 0 -> CoverageStatus.MISSING
                matched == support.size() && !hasUnexpectedIntervals -> CoverageStatus.COMPLETE
                else -> CoverageStatus.PARTIAL
            }
        }

        private fun aggregateDayCoverage(statuses: Collection<CoverageStatus>): CoverageStatus {
            if (statuses.isEmpty()) return CoverageStatus.MISSING
            return when {
                statuses.all { it == CoverageStatus.COMPLETE } -> CoverageStatus.COMPLETE
                statuses.all { it == CoverageStatus.MISSING } -> CoverageStatus.MISSING
                else -> CoverageStatus.PARTIAL
            }
        }

        private fun hasOverlap(intervals: Set<AccumulationInterval>): Boolean {
            val ordered = intervals.sortedBy { it.start }
            return ordered.zipWithNext().any { (left, right) -> right.start < left.end }
        }

        private fun ExpectedFieldSupport.size(): Int = when (this) {
            is ExpectedFieldSupport.Points -> instants.size
            is ExpectedFieldSupport.Intervals -> intervals.size
        }
    }
}

/** One of the 44 local dates, including dates with no facts. */
class CatalogDay internal constructor(
    val date: LocalDate,
    val localStart: Instant,
    val localEnd: Instant,
    facts: List<CanonicalWeatherFacts>,
    fieldCoverage: Map<CatalogField, CoverageStatus>,
    val dayCoverage: CoverageStatus,
    expectedSupport: Map<CatalogField, ExpectedFieldSupport>,
    providerDailyAmounts: List<DailyAccumulatedAmount>,
    val dailySummary: ProviderDailySummary?,
    dailyCoverage: Map<DailyCatalogField, CoverageStatus>,
) {
    val facts: List<CanonicalWeatherFacts> = Collections.unmodifiableList(facts.toList())
    val fieldCoverage: Map<CatalogField, CoverageStatus> = Collections.unmodifiableMap(fieldCoverage.toMap())
    val providerDailyAmounts: List<DailyAccumulatedAmount> = Collections.unmodifiableList(providerDailyAmounts.toList())
    val dailyCoverage: Map<DailyCatalogField, CoverageStatus> = Collections.unmodifiableMap(dailyCoverage.toMap())
    private val expectedSupport: Map<CatalogField, ExpectedFieldSupport> = Collections.unmodifiableMap(
        expectedSupport.mapValues { (_, support) ->
            when (support) {
                is ExpectedFieldSupport.Points -> ExpectedFieldSupport.Points(support.instants)
                is ExpectedFieldSupport.Intervals -> ExpectedFieldSupport.Intervals(support.intervals)
            }
        },
    )

    /** A defensive, read-only view of the source's expected support for this date. */
    fun expectedFieldSupport(): Map<CatalogField, ExpectedFieldSupport> = expectedSupport

    /** Derive only a full-local-day accumulated amount with exact, complete interval support. */
    fun deriveDailyAmount(field: DailyAmountField): DailyAccumulatedAmount? {
        val catalogField = field.catalogField()
        val support = expectedSupport[catalogField] as? ExpectedFieldSupport.Intervals ?: return null
        if (fieldCoverage[catalogField] != CoverageStatus.COMPLETE || !partitionsLocalDay(support.intervals)) return null
        val suppliedIntervals = facts.mapNotNull { it.intervalFor(catalogField) }
            .filter { it.start < localEnd && it.end > localStart }
            .toSet()
        if (suppliedIntervals != support.intervals) return null

        val amounts = support.intervals.map { expected ->
            val fact = facts.firstOrNull { it.validTime == expected.end && it.intervalFor(catalogField) == expected }
                ?: return null
            fact.amountFor(field) ?: return null
        }
        val sum = amounts.sum()
        if (!sum.isFinite()) return null
        return DailyAccumulatedAmount(
            field = field,
            value = sum,
            unit = if (field == DailyAmountField.SNOWFALL) DailyAmountUnit.CENTIMETERS else DailyAmountUnit.MILLIMETERS,
            origin = DailyValueOrigin.DERIVED,
            support = AccumulationInterval(localStart, localEnd),
        )
    }

    private fun partitionsLocalDay(intervals: Set<AccumulationInterval>): Boolean {
        val ordered = intervals.sortedBy { it.start }
        if (ordered.isEmpty() || ordered.first().start != localStart || ordered.last().end != localEnd) return false
        return ordered.zipWithNext().all { (left, right) -> left.end == right.start }
    }
}

private sealed interface Normalization {
    data class Success(val facts: List<CanonicalWeatherFacts>) : Normalization
    data class Failure(val error: CatalogWindowError) : Normalization
}

private val ACCUMULATED_FIELDS = setOf(
    CatalogField.RAIN_AMOUNT_MILLIMETERS,
    CatalogField.TOTAL_PRECIPITATION_MILLIMETERS,
    CatalogField.SNOWFALL_CENTIMETERS,
)

private fun CanonicalWeatherFacts.hasValue(field: CatalogField): Boolean = when (field) {
    CatalogField.CONDITIONS -> conditions.isNotEmpty()
    CatalogField.PHENOMENA -> conditions.isNotEmpty() || phenomena.isNotEmpty()
    CatalogField.TEMPERATURE_C -> temperatureC != null
    CatalogField.PRECIPITATION_PROBABILITY_PERCENT -> precipitationProbabilityPercent != null
    CatalogField.THUNDERSTORM_PROBABILITY_PERCENT -> thunderstormProbabilityPercent != null
    CatalogField.RELATIVE_HUMIDITY_PERCENT -> relativeHumidityPercent != null
    CatalogField.CLOUD_COVER_PERCENT -> cloudCoverPercent != null
    CatalogField.WIND_SPEED_KPH -> windSpeedKph != null
    CatalogField.WIND_GUST_KPH -> windGustKph != null
    CatalogField.WIND_DIRECTION_DEGREES -> windDirectionDegrees != null
    CatalogField.RAIN_AMOUNT_MILLIMETERS -> rainAmountMillimeters != null
    CatalogField.TOTAL_PRECIPITATION_MILLIMETERS -> totalPrecipitationMillimeters != null
    CatalogField.PRECIPITATION_RATE -> precipitationRate != null
    CatalogField.SNOWFALL_CENTIMETERS -> snowfallCentimeters != null
    CatalogField.SNOW_DEPTH_CENTIMETERS -> snowDepthCentimeters != null
    CatalogField.SURFACE_PRESSURE_HPA -> surfacePressureHpa != null
    CatalogField.MEAN_SEA_LEVEL_PRESSURE_HPA -> meanSeaLevelPressureHpa != null
    CatalogField.VISIBILITY_KM -> visibilityKm != null
}

private fun CanonicalWeatherFacts.intervalFor(field: CatalogField): AccumulationInterval? = when (field) {
    CatalogField.RAIN_AMOUNT_MILLIMETERS -> rainAmountMillimeters?.interval
    CatalogField.TOTAL_PRECIPITATION_MILLIMETERS -> totalPrecipitationMillimeters?.interval
    CatalogField.SNOWFALL_CENTIMETERS -> snowfallCentimeters?.interval
    else -> null
}

private fun CanonicalWeatherFacts.amountFor(field: DailyAmountField): Double? = when (field) {
    DailyAmountField.RAIN -> rainAmountMillimeters?.valueMillimeters
    DailyAmountField.TOTAL_PRECIPITATION -> totalPrecipitationMillimeters?.valueMillimeters
    DailyAmountField.SNOWFALL -> snowfallCentimeters?.valueCentimeters
}

private fun DailyAmountField.catalogField(): CatalogField = when (this) {
    DailyAmountField.RAIN -> CatalogField.RAIN_AMOUNT_MILLIMETERS
    DailyAmountField.TOTAL_PRECIPITATION -> CatalogField.TOTAL_PRECIPITATION_MILLIMETERS
    DailyAmountField.SNOWFALL -> CatalogField.SNOWFALL_CENTIMETERS
}
