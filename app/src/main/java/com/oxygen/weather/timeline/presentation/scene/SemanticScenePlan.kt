package com.oxygen.weather.timeline.presentation.scene

import com.oxygen.weather.timeline.data.catalog.AccumulationInterval
import com.oxygen.weather.timeline.data.catalog.CanonicalCatalog
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.CatalogField
import com.oxygen.weather.timeline.data.catalog.CatalogWindow
import com.oxygen.weather.timeline.data.catalog.ExpectedFieldSupport
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Collections

/** Display bounds are illustrative. Their endpoints are never reported weather-event times. */
data class RepresentativeCell(val startInclusive: Instant, val endExclusive: Instant) {
    init { require(startInclusive < endExclusive) }
}

enum class SceneSupportKind { POINT, INTERVAL, LIGHT_RULE }
enum class SceneEvidence { SUPPLIED, EXPECTED_MISSING, PROBABILITY_ONLY, LIGHT_RULE }
enum class LightPhase { DAY, DUSK, NIGHT }

/** One expected point, including missing slots. The source time exists only for supplied data. */
data class ScenePoint(
    val expectedSlot: Instant,
    val sourceValidTime: Instant?,
    val value: String?,
    val evidence: SceneEvidence,
    val representativeCell: RepresentativeCell?,
    /** A rate can be expected at a point while its supplied value is a mean over these source bounds. */
    val sourceMeanInterval: AccumulationInterval? = null,
)

/** The source's exact half-open bounds, never a representative point cell. */
data class SceneInterval(
    val sourceBounds: AccumulationInterval,
    val sourceValidTime: Instant?,
    val value: String?,
    val evidence: SceneEvidence,
)

/** A generated local-clock variant, clipped to the requested window. */
data class LightInterval(val startInclusive: Instant, val endExclusive: Instant, val phase: LightPhase) {
    init { require(startInclusive < endExclusive) }
}

data class SceneTrack(
    val layerName: String,
    val assetFamily: String,
    val supportKind: SceneSupportKind,
    val points: List<ScenePoint> = emptyList(),
    val intervals: List<SceneInterval> = emptyList(),
    val lightIntervals: List<LightInterval> = emptyList(),
) {
    init {
        require(when (supportKind) {
            SceneSupportKind.POINT -> points.isNotEmpty() && intervals.isEmpty() && lightIntervals.isEmpty()
            SceneSupportKind.INTERVAL -> points.isEmpty() && intervals.isNotEmpty() && lightIntervals.isEmpty()
            SceneSupportKind.LIGHT_RULE -> points.isEmpty() && intervals.isEmpty() && lightIntervals.isNotEmpty()
        })
    }
}

data class SceneReading(
    val selectedInstant: Instant,
    val weather: Map<String, ScenePoint?>,
    val intervals: Map<String, SceneInterval?>,
    val lightInterval: LightInterval,
) {
    val lightPhase: LightPhase get() = lightInterval.phase
}

/** Selection evaluates a fixed plan; it cannot change the plan or move the selected instant. */
class SemanticScenePlan internal constructor(
    val identity: String,
    val zoneId: ZoneId,
    val windowStart: Instant,
    val windowEnd: Instant,
    tracks: List<SceneTrack>,
) {
    val tracks: List<SceneTrack> = Collections.unmodifiableList(tracks.toList())

    fun evaluate(selectedInstant: Instant): SceneReading {
        require(selectedInstant >= windowStart && selectedInstant < windowEnd)
        val weather = linkedMapOf<String, ScenePoint?>()
        val intervals = linkedMapOf<String, SceneInterval?>()
        var lightInterval: LightInterval? = null
        for (track in tracks) when (track.supportKind) {
            SceneSupportKind.POINT -> weather[track.layerName] =
                track.points.firstOrNull { it.expectedSlot == selectedInstant } ?:
                track.points.firstOrNull { it.representativeCell?.endExclusive == selectedInstant &&
                    track.points.any { next -> next.representativeCell?.startInclusive == selectedInstant } } ?:
                track.points.firstOrNull { point -> point.representativeCell?.let { cell ->
                    selectedInstant >= cell.startInclusive && selectedInstant < cell.endExclusive
                } == true }
            SceneSupportKind.INTERVAL -> intervals[track.layerName] = track.intervals.firstOrNull {
                selectedInstant >= it.sourceBounds.start && selectedInstant < it.sourceBounds.end
            }
            SceneSupportKind.LIGHT_RULE -> lightInterval = track.lightIntervals.firstOrNull {
                selectedInstant >= it.startInclusive && selectedInstant < it.endExclusive
            }
        }
        return SceneReading(selectedInstant, Collections.unmodifiableMap(weather),
            Collections.unmodifiableMap(intervals), requireNotNull(lightInterval))
    }
}

/** Rule v1 uses clock hours only; it makes no sunrise, sunset, or moon claim. */
private fun lightAt(instant: Instant, zone: ZoneId): LightPhase = when (instant.atZone(zone).hour) {
    in 7..17 -> LightPhase.DAY
    in 18..19 -> LightPhase.DUSK
    else -> LightPhase.NIGHT
}

object SemanticSceneGenerator {
    const val VERSION = "sg1-hourly-cells-light-v1"

    fun generate(catalog: CanonicalCatalog, window: CatalogWindow, start: Instant, end: Instant): SemanticScenePlan {
        require(start < end && start >= window.startInclusive && end <= window.endExclusive)
        require(catalog.location.timeZone == window.zoneId)
        val tracks = mutableListOf<SceneTrack>()
        val pointFields = listOf(CatalogField.CONDITIONS, CatalogField.PHENOMENA,
            CatalogField.PRECIPITATION_PROBABILITY_PERCENT, CatalogField.PRECIPITATION_RATE)
        val intervalFields = listOf(CatalogField.RAIN_AMOUNT_MILLIMETERS,
            CatalogField.TOTAL_PRECIPITATION_MILLIMETERS, CatalogField.SNOWFALL_CENTIMETERS)
        for (field in pointFields) {
            val points = window.days.flatMap { day ->
                val support = day.expectedFieldSupport()[field] as? ExpectedFieldSupport.Points
                    ?: return@flatMap emptyList<ScenePoint>()
                val expected = support.instants.sorted()
                expected.mapIndexed { index, slot ->
                    val fact = day.facts.firstOrNull { it.validTime == slot }
                    val value = fact?.pointValue(field)
                    val left = expected.getOrNull(index - 1)?.takeIf { slot.epochSecond - it.epochSecond == 3600L }
                    val right = expected.getOrNull(index + 1)?.takeIf { it.epochSecond - slot.epochSecond == 3600L }
                    val cell = if (left == null && right == null) null else {
                        val from = left?.plusSeconds(1800) ?: slot.minusSeconds(1800)
                        val to = right?.minusSeconds(1800) ?: slot.plusSeconds(1800)
                        val clippedStart = maxOf(from, start, day.localStart)
                        val clippedEnd = minOf(to, end, day.localEnd)
                        if (clippedStart < clippedEnd) RepresentativeCell(clippedStart, clippedEnd) else null
                    }
                    ScenePoint(slot, slot.takeIf { value != null }, value,
                        if (value == null) SceneEvidence.EXPECTED_MISSING else if (field == CatalogField.PRECIPITATION_PROBABILITY_PERCENT)
                            SceneEvidence.PROBABILITY_ONLY else SceneEvidence.SUPPLIED, cell,
                        if (value != null && field == CatalogField.PRECIPITATION_RATE) fact?.precipitationRate?.meanInterval else null)
                }
            }.filter { it.expectedSlot >= start && it.expectedSlot < end || it.representativeCell != null }
            if (points.isNotEmpty()) tracks += SceneTrack(field.name.lowercase(), field.assetFamily(), SceneSupportKind.POINT,
                points = Collections.unmodifiableList(points))
        }
        for (field in intervalFields) {
            val values = window.days.flatMap { day ->
                val support = day.expectedFieldSupport()[field] as? ExpectedFieldSupport.Intervals
                    ?: return@flatMap emptyList<SceneInterval>()
                support.intervals.sortedBy { it.start }.map { bounds ->
                    val fact = day.facts.firstOrNull { it.validTime == bounds.end && it.intervalValue(field, bounds) != null }
                    val value = fact?.intervalValue(field, bounds)
                    SceneInterval(bounds, fact?.validTime, value,
                        if (value == null) SceneEvidence.EXPECTED_MISSING else SceneEvidence.SUPPLIED)
                }
            }.filter { it.sourceBounds.start < end && it.sourceBounds.end > start }
            if (values.isNotEmpty()) tracks += SceneTrack(field.name.lowercase(), field.assetFamily(), SceneSupportKind.INTERVAL,
                intervals = Collections.unmodifiableList(values))
        }
        tracks += SceneTrack("light_phase", "local_clock_light", SceneSupportKind.LIGHT_RULE,
            lightIntervals = Collections.unmodifiableList(lightIntervals(start, end, window.zoneId)))
        val canonical = buildString {
            append(VERSION).append('|').append(catalog.datasetId.value).append('|')
            append(catalog.location).append('|').append(catalog.provenance).append('|').append(catalog.asOf)
            append('|').append(catalog.productSegments).append('|').append(start).append('|').append(end)
            append('|').append(window.zoneId.id).append('|').append(tracks)
            // Non-scene catalog changes still identify a new snapshot.
            append('|').append(catalog.facts.map { it.toString() }).append('|').append(catalog.dailySummaries)
        }
        val digest = MessageDigest.getInstance("SHA-256").digest(canonical.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
        return SemanticScenePlan("$VERSION:$digest", window.zoneId, start, end, tracks)
    }
}

private fun lightIntervals(start: Instant, end: Instant, zone: ZoneId): List<LightInterval> {
    val firstDate = start.atZone(zone).toLocalDate().minusDays(1)
    val lastDate = end.atZone(zone).toLocalDate().plusDays(1)
    val boundaries = mutableListOf(start, end)
    var date: LocalDate = firstDate
    while (!date.isAfter(lastDate)) {
        for (hour in listOf(7, 18, 20)) {
            val boundary = date.atTime(hour, 0).atZone(zone).toInstant()
            if (boundary > start && boundary < end) boundaries += boundary
        }
        date = date.plusDays(1)
    }
    return boundaries.distinct().sorted().zipWithNext { from, to ->
        LightInterval(from, to, lightAt(from, zone))
    }
}

private fun CatalogField.assetFamily(): String = when (this) {
    CatalogField.CONDITIONS -> "weather_condition"
    CatalogField.PHENOMENA -> "weather_phenomenon"
    CatalogField.PRECIPITATION_PROBABILITY_PERCENT -> "precipitation_chance"
    CatalogField.PRECIPITATION_RATE -> "precipitation_rate"
    else -> "accumulated_amount"
}

private fun CanonicalWeatherFacts.pointValue(field: CatalogField): String? = when (field) {
    CatalogField.CONDITIONS -> conditions.takeIf { it.isNotEmpty() }?.sortedBy { it.name }?.joinToString("+") { it.name }
    CatalogField.PHENOMENA -> phenomena.takeIf { it.isNotEmpty() }?.sortedBy { it.name }?.joinToString("+") { it.name }
    CatalogField.PRECIPITATION_PROBABILITY_PERCENT -> precipitationProbabilityPercent?.toString()
    CatalogField.PRECIPITATION_RATE -> precipitationRate?.valueMillimetersPerHour?.toString()
    else -> null
}

private fun CanonicalWeatherFacts.intervalValue(field: CatalogField, bounds: AccumulationInterval): String? = when (field) {
    CatalogField.RAIN_AMOUNT_MILLIMETERS -> rainAmountMillimeters?.takeIf { it.interval == bounds }?.valueMillimeters?.toString()
    CatalogField.TOTAL_PRECIPITATION_MILLIMETERS -> totalPrecipitationMillimeters?.takeIf { it.interval == bounds }?.valueMillimeters?.toString()
    CatalogField.SNOWFALL_CENTIMETERS -> snowfallCentimeters?.takeIf { it.interval == bounds }?.valueCentimeters?.toString()
    else -> null
}
