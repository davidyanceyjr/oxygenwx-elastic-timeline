package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.catalog.AccumulatedMillimeters
import com.oxygen.weather.timeline.data.catalog.AccumulatedSnowfallCentimeters
import com.oxygen.weather.timeline.data.catalog.AccumulationInterval
import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.ExpectedFieldSupport
import com.oxygen.weather.timeline.data.catalog.PrecipitationRate
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.WeatherPhenomenon
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Display bounds are illustrative. Their endpoints are never reported weather-event times. */
data class RepresentativeCell(val startInclusive: Instant, val endExclusive: Instant) {
    init { require(startInclusive < endExclusive) }
}

enum class SceneEvidence { SUPPLIED, SUPPLIED_EMPTY, EXPECTED_MISSING, PROBABILITY_ONLY, LIGHT_RULE }
enum class LightPhase { DAY, DAWN, DUSK, NIGHT, UNAVAILABLE }

data class SolarDayEvents(
    val date: LocalDate,
    val sunriseInstant: Instant?,
    val sunsetInstant: Instant?,
) {
    val hasCompleteDaylight: Boolean
        get() = sunriseInstant != null && sunsetInstant != null && sunriseInstant < sunsetInstant
}

/** One typed point value with its expected slot, source time, support, and display cell. */
data class ScenePoint<T>(
    val expectedSlot: Instant,
    val sourceValidTime: Instant?,
    val value: T?,
    val evidence: SceneEvidence,
    val representativeCell: RepresentativeCell?,
)

/** A typed interval value retaining the source's exact half-open bounds and valid time. */
data class SceneInterval<T>(
    val sourceBounds: AccumulationInterval,
    val sourceValidTime: Instant?,
    val value: T?,
    val evidence: SceneEvidence,
)

/** One selected time's typed canonical values and their independent support records. */
data class SceneReading(
    val selectedInstant: Instant,
    val conditions: ScenePoint<Set<WeatherCondition>>? = null,
    val phenomena: ScenePoint<Set<WeatherPhenomenon>>? = null,
    val cloudCoverPercent: ScenePoint<Double>? = null,
    val precipitationChancePercent: ScenePoint<Double>? = null,
    val precipitationRate: ScenePoint<PrecipitationRate>? = null,
    val rainAmount: SceneInterval<AccumulatedMillimeters>? = null,
    val totalPrecipitation: SceneInterval<AccumulatedMillimeters>? = null,
    val snowfall: SceneInterval<AccumulatedSnowfallCentimeters>? = null,
    val lightInterval: LightInterval,
    val solarEvents: SolarDayEvents? = null,
) {
    /** Compatibility constructor for existing theme-only callers that supply no weather tracks. */
    @Deprecated("Catalog weather values are carried by the typed SceneReading fields")
    constructor(
        selectedInstant: Instant,
        weather: Map<String, ScenePoint<String>?>,
        intervals: Map<String, SceneInterval<String>?>,
        lightInterval: LightInterval,
        solarEvents: SolarDayEvents? = null,
    ) : this(
        selectedInstant = selectedInstant,
        lightInterval = lightInterval,
        solarEvents = solarEvents,
    )

    val lightPhase: LightPhase get() = lightInterval.phase
}

/** Generated local-clock light segment, clipped to the requested window. */
data class LightInterval(val startInclusive: Instant, val endExclusive: Instant, val phase: LightPhase) {
    init { require(startInclusive < endExclusive) }
}

/** Selection evaluates fixed typed tracks and never moves or interpolates the selected instant. */
class SemanticScenePlan internal constructor(
    val identity: String,
    val zoneId: ZoneId,
    val windowStart: Instant,
    val windowEnd: Instant,
    private val conditions: List<ScenePoint<Set<WeatherCondition>>>,
    private val phenomena: List<ScenePoint<Set<WeatherPhenomenon>>>,
    private val cloudCoverPercent: List<ScenePoint<Double>>,
    private val precipitationChancePercent: List<ScenePoint<Double>>,
    private val precipitationRate: List<ScenePoint<PrecipitationRate>>,
    private val rainAmount: List<SceneInterval<AccumulatedMillimeters>>,
    private val totalPrecipitation: List<SceneInterval<AccumulatedMillimeters>>,
    private val snowfall: List<SceneInterval<AccumulatedSnowfallCentimeters>>,
    private val lightIntervals: List<LightInterval>,
    private val solarEventsByDate: Map<LocalDate, SolarDayEvents>,
) {
    fun evaluate(selectedInstant: Instant): SceneReading {
        require(selectedInstant >= windowStart && selectedInstant < windowEnd)
        return SceneReading(
            selectedInstant = selectedInstant,
            conditions = conditions.at(selectedInstant),
            phenomena = phenomena.at(selectedInstant),
            cloudCoverPercent = cloudCoverPercent.at(selectedInstant),
            precipitationChancePercent = precipitationChancePercent.at(selectedInstant),
            precipitationRate = precipitationRate.at(selectedInstant),
            rainAmount = rainAmount.at(selectedInstant),
            totalPrecipitation = totalPrecipitation.at(selectedInstant),
            snowfall = snowfall.at(selectedInstant),
            lightInterval = requireNotNull(lightIntervals.firstOrNull {
                selectedInstant >= it.startInclusive && selectedInstant < it.endExclusive
            }),
            solarEvents = solarEventsByDate[selectedInstant.atZone(zoneId).toLocalDate()],
        )
    }
}

object SemanticSceneGenerator {
    const val VERSION = "sg9-typed-selected-scene-v1"

    fun generate(catalog: CanonicalCatalog, window: CatalogWindow, start: Instant, end: Instant): SemanticScenePlan {
        require(start < end && start >= window.startInclusive && end <= window.endExclusive)
        require(catalog.location.timeZone == window.zoneId)
        val conditions = pointTrack(
            window, start, end, CatalogField.CONDITIONS,
            carryForward = { value -> value.size == 1 && value.single() in CARRIED_CONDITIONS },
        ) { facts -> facts.conditions.takeIf { it.isNotEmpty() } }
        val phenomena = pointTrack(window, start, end, CatalogField.PHENOMENA) { facts ->
            facts.phenomena.takeIf { it.isNotEmpty() }
        }
        val cloudCoverPercent = pointTrack(window, start, end, CatalogField.CLOUD_COVER_PERCENT) { it.cloudCoverPercent }
        val precipitationChancePercent = pointTrack(
            window, start, end, CatalogField.PRECIPITATION_PROBABILITY_PERCENT,
        ) { it.precipitationProbabilityPercent }
        val precipitationRate = pointTrack(window, start, end, CatalogField.PRECIPITATION_RATE) { it.precipitationRate }
        val rainAmount = intervalTrack(window, start, end, CatalogField.RAIN_AMOUNT_MILLIMETERS) { facts, bounds ->
            facts.rainAmountMillimeters?.takeIf { it.interval == bounds }
        }
        val totalPrecipitation = intervalTrack(
            window, start, end, CatalogField.TOTAL_PRECIPITATION_MILLIMETERS,
        ) { facts, bounds -> facts.totalPrecipitationMillimeters?.takeIf { it.interval == bounds } }
        val snowfall = intervalTrack(window, start, end, CatalogField.SNOWFALL_CENTIMETERS) { facts, bounds ->
            facts.snowfallCentimeters?.takeIf { it.interval == bounds }
        }
        val solarEventsByDate = catalog.dailySummaries.associate { summary ->
            summary.date to SolarDayEvents(summary.date, summary.sunriseInstant, summary.sunsetInstant)
        }
        val lights = lightIntervals(start, end, window.zoneId, solarEventsByDate)
        val canonical = buildString {
            append(VERSION).append('|').append(catalog.datasetId.value).append('|')
            append(catalog.location).append('|').append(catalog.provenance).append('|').append(catalog.asOf)
            append('|').append(catalog.productSegments).append('|').append(start).append('|').append(end)
            append('|').append(window.zoneId.id).append('|')
            append(conditions).append('|').append(phenomena).append('|').append(cloudCoverPercent).append('|')
            append(precipitationChancePercent).append('|').append(precipitationRate).append('|')
            append(rainAmount).append('|').append(totalPrecipitation).append('|').append(snowfall).append('|')
            append(lights).append('|').append(solarEventsByDate)
            // Non-scene catalog changes still identify a new snapshot.
            append('|').append(catalog.facts.map { it.toString() }).append('|').append(catalog.dailySummaries)
        }
        val digest = MessageDigest.getInstance("SHA-256").digest(canonical.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
        return SemanticScenePlan(
            identity = "$VERSION:$digest",
            zoneId = window.zoneId,
            windowStart = start,
            windowEnd = end,
            conditions = conditions,
            phenomena = phenomena,
            cloudCoverPercent = cloudCoverPercent,
            precipitationChancePercent = precipitationChancePercent,
            precipitationRate = precipitationRate,
            rainAmount = rainAmount,
            totalPrecipitation = totalPrecipitation,
            snowfall = snowfall,
            lightIntervals = lights,
            solarEventsByDate = solarEventsByDate,
        )
    }
}

private val CARRIED_CONDITIONS = setOf(
    WeatherCondition.CLEAR,
    WeatherCondition.PARTLY_CLOUDY,
    WeatherCondition.CLOUDY,
    WeatherCondition.RAIN,
    WeatherCondition.SNOW,
)

private fun <T> pointTrack(
    window: CatalogWindow,
    start: Instant,
    end: Instant,
    field: CatalogField,
    carryForward: ((T) -> Boolean)? = null,
    valueOf: (CanonicalWeatherFacts) -> T?,
): List<ScenePoint<T>> = window.days.flatMap { day ->
    val support = day.expectedFieldSupport()[field] as? ExpectedFieldSupport.Points
        ?: return@flatMap emptyList()
    val expected = support.instants.sorted()
    expected.mapIndexed { index, slot ->
        val fact = day.facts.firstOrNull { it.validTime == slot }
        val carried = if (field == CatalogField.CONDITIONS && fact == null && carryForward != null) {
            val previousIndex = (index - 1 downTo 0).takeWhile { offset ->
                expected[offset + 1].epochSecond - expected[offset].epochSecond == 3600L
            }.firstOrNull { offset -> day.facts.any { it.validTime == expected[offset] } }
            previousIndex?.let { offset ->
                val previousSlot = expected[offset]
                val previousValue = day.facts.firstOrNull { it.validTime == previousSlot }?.let(valueOf)
                previousValue?.takeIf(carryForward)?.let { previousSlot to it }
            }
        } else null
        val value = fact?.let(valueOf) ?: carried?.second
        val left = expected.getOrNull(index - 1)?.takeIf { slot.epochSecond - it.epochSecond == 3600L }
        val right = expected.getOrNull(index + 1)?.takeIf { it.epochSecond - slot.epochSecond == 3600L }
        val cell = if (left == null && right == null) null else {
            val from = left?.plusSeconds(1800) ?: slot.minusSeconds(1800)
            val to = right?.minusSeconds(1800) ?: slot.plusSeconds(1800)
            val clippedStart = maxOf(from, start, day.localStart)
            val clippedEnd = minOf(to, end, day.localEnd)
            if (clippedStart < clippedEnd) RepresentativeCell(clippedStart, clippedEnd) else null
        }
        val directlySupplied = fact?.let(valueOf)
        ScenePoint(
            expectedSlot = slot,
            sourceValidTime = carried?.first ?: slot.takeIf { directlySupplied != null },
            value = value,
            evidence = when {
                fact == null -> SceneEvidence.EXPECTED_MISSING
                value == null -> SceneEvidence.SUPPLIED_EMPTY
                field == CatalogField.PRECIPITATION_PROBABILITY_PERCENT -> SceneEvidence.PROBABILITY_ONLY
                else -> SceneEvidence.SUPPLIED
            },
            representativeCell = cell,
        )
    }.filter { it.expectedSlot >= start && it.expectedSlot < end || it.representativeCell != null }
}

private fun <T> intervalTrack(
    window: CatalogWindow,
    start: Instant,
    end: Instant,
    field: CatalogField,
    valueOf: (CanonicalWeatherFacts, AccumulationInterval) -> T?,
): List<SceneInterval<T>> = window.days.flatMap { day ->
    val support = day.expectedFieldSupport()[field] as? ExpectedFieldSupport.Intervals
        ?: return@flatMap emptyList()
    support.intervals.sortedBy { it.start }.map { bounds ->
        val fact = day.facts.firstOrNull { it.validTime == bounds.end && valueOf(it, bounds) != null }
        val value = fact?.let { valueOf(it, bounds) }
        SceneInterval(
            sourceBounds = bounds,
            sourceValidTime = fact?.validTime,
            value = value,
            evidence = if (value == null) SceneEvidence.EXPECTED_MISSING else SceneEvidence.SUPPLIED,
        )
    }.filter { it.sourceBounds.start < end && it.sourceBounds.end > start }
}

private fun <T> List<ScenePoint<T>>.at(selectedInstant: Instant): ScenePoint<T>? =
    firstOrNull { it.expectedSlot == selectedInstant } ?:
        firstOrNull { it.representativeCell?.endExclusive == selectedInstant &&
            any { next -> next.representativeCell?.startInclusive == selectedInstant } } ?:
        firstOrNull { point -> point.representativeCell?.let { cell ->
            selectedInstant >= cell.startInclusive && selectedInstant < cell.endExclusive
        } == true }

private fun <T> List<SceneInterval<T>>.at(selectedInstant: Instant): SceneInterval<T>? = firstOrNull {
    selectedInstant >= it.sourceBounds.start && selectedInstant < it.sourceBounds.end
}

private fun lightIntervals(
    start: Instant,
    end: Instant,
    zone: ZoneId,
    solarEventsByDate: Map<LocalDate, SolarDayEvents>,
): List<LightInterval> {
    val boundaries = mutableListOf(start, end)
    for (events in solarEventsByDate.values) {
        for (event in listOfNotNull(events.sunriseInstant, events.sunsetInstant)) {
            for (boundary in listOf(event.minusSeconds(45 * 60L), event, event.plusSeconds(45 * 60L))) {
                if (boundary > start && boundary < end) boundaries += boundary
            }
        }
    }
    return boundaries.distinct().sorted().zipWithNext { from, to ->
        LightInterval(from, to, lightAt(from, zone, solarEventsByDate))
    }
}

private fun lightAt(instant: Instant, zone: ZoneId, solarEventsByDate: Map<LocalDate, SolarDayEvents>): LightPhase {
    val events = solarEventsByDate[instant.atZone(zone).toLocalDate()]?.takeIf { it.hasCompleteDaylight }
        ?: return LightPhase.UNAVAILABLE
    val sunrise = requireNotNull(events.sunriseInstant)
    val sunset = requireNotNull(events.sunsetInstant)
    val dawnStart = sunrise.minusSeconds(45 * 60L)
    val dawnEnd = sunrise.plusSeconds(45 * 60L)
    val duskStart = sunset.minusSeconds(45 * 60L)
    val duskEnd = sunset.plusSeconds(45 * 60L)
    return when {
        instant < dawnStart || instant >= duskEnd -> LightPhase.NIGHT
        instant < dawnEnd -> LightPhase.DAWN
        instant < duskStart -> LightPhase.DAY
        else -> LightPhase.DUSK
    }
}
