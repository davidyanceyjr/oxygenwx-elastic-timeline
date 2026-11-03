package com.oxygen.weather.timeline.data.debug

import android.content.SharedPreferences
import com.fasterxml.jackson.databind.ObjectMapper
import com.oxygen.weather.timeline.data.catalog.*
import com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolId
import java.security.MessageDigest
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/** Debug-only editable definition. Its local-time range is half-open; both real instants in a DST overlap are included. */
data class DebugAuthoredCatalog(
    val name: String,
    val location: CatalogLocation,
    val datasetId: DatasetId,
    val asOf: Instant,
    val startLocal: LocalDateTime,
    val endLocalExclusive: LocalDateTime,
    val values: DebugAuthoredWeatherValues,
    val events: List<DebugAuthoredEvent> = listOf(DebugAuthoredEvent(startLocal, endLocalExclusive, values)),
) {
    init {
        require(name.isNotBlank()) { "A saved catalog needs a name" }
        require(startLocal < endLocalExclusive) { "Authored period must be non-empty" }
        require(startLocal.minute == 0 && startLocal.second == 0 && startLocal.nano == 0 &&
            endLocalExclusive.minute == 0 && endLocalExclusive.second == 0 && endLocalExclusive.nano == 0) {
            "Authored envelope boundaries must be whole local hours"
        }
        require(events.isNotEmpty()) { "An authored envelope needs at least one event" }
        require(events == events.sortedBy { it.startLocal }) { "Authored events must be ordered by start time" }
        require(events.all { it.startLocal >= startLocal && it.endLocalExclusive <= endLocalExclusive }) {
            "Authored events must be inside the envelope"
        }
        require(events.zipWithNext().all { (first, second) -> first.endLocalExclusive <= second.startLocal }) {
            "Authored events may not overlap"
        }
    }
}

data class DebugAuthoredEvent(
    val startLocal: LocalDateTime,
    val endLocalExclusive: LocalDateTime,
    val values: DebugAuthoredWeatherValues,
    /** Debug-only illustration selector; never materialized into CanonicalWeatherFacts. */
    val symbolId: WeatherSymbolId? = null,
) {
    init {
        require(startLocal < endLocalExclusive) { "Event range must be non-empty" }
        require(startLocal.minute == 0 && startLocal.second == 0 && startLocal.nano == 0 &&
            endLocalExclusive.minute == 0 && endLocalExclusive.second == 0 && endLocalExclusive.nano == 0) {
            "Event range boundaries must be whole local hours"
        }
    }
}

/** A complete supported value set applied at each real local-hour instant in the authored period. */
data class DebugAuthoredWeatherValues(
    val conditions: Set<WeatherCondition> = emptySet(),
    val phenomena: Set<WeatherPhenomenon> = emptySet(),
    val temperatureC: Double? = null,
    val precipitationProbabilityPercent: Double? = null,
    val thunderstormProbabilityPercent: Double? = null,
    val relativeHumidityPercent: Double? = null,
    val cloudCoverPercent: Double? = null,
    val windSpeedKph: Double? = null,
    val windGustKph: Double? = null,
    val windDirectionDegrees: Double? = null,
    val rainAmountMillimeters: Double? = null,
    val totalPrecipitationMillimeters: Double? = null,
    val precipitationRateMillimetersPerHour: Double? = null,
    val snowfallCentimeters: Double? = null,
    val snowDepthCentimeters: Double? = null,
    val surfacePressureHpa: Double? = null,
    val meanSeaLevelPressureHpa: Double? = null,
    val visibilityKm: Double? = null,
) {
    init {
        fact(Instant.EPOCH)
    }

    internal fun fact(time: Instant): CanonicalWeatherFacts {
        val interval = AccumulationInterval(time.minus(1, ChronoUnit.HOURS), time)
        return CanonicalWeatherFacts(
            validTime = time,
            conditions = conditions,
            phenomena = phenomena,
            temperatureC = temperatureC,
            precipitationProbabilityPercent = precipitationProbabilityPercent,
            thunderstormProbabilityPercent = thunderstormProbabilityPercent,
            relativeHumidityPercent = relativeHumidityPercent,
            cloudCoverPercent = cloudCoverPercent,
            windSpeedKph = windSpeedKph,
            windGustKph = windGustKph,
            windDirectionDegrees = windDirectionDegrees,
            rainAmountMillimeters = rainAmountMillimeters?.let { AccumulatedMillimeters(it, interval) },
            totalPrecipitationMillimeters = totalPrecipitationMillimeters?.let { AccumulatedMillimeters(it, interval) },
            precipitationRate = precipitationRateMillimetersPerHour?.let { PrecipitationRate(it) },
            snowfallCentimeters = snowfallCentimeters?.let { AccumulatedSnowfallCentimeters(it, interval) },
            snowDepthCentimeters = snowDepthCentimeters,
            surfacePressureHpa = surfacePressureHpa,
            meanSeaLevelPressureHpa = meanSeaLevelPressureHpa,
            visibilityKm = visibilityKm,
        )
    }
}

/** Materializes a full canonical 44-local-date catalog, overriding baseline facts only in the authored range. */
object DebugAuthoredCatalogMaterializer {
    suspend fun materialize(definition: DebugAuthoredCatalog): CatalogRecording {
        require(definition.datasetId.value != "wx22-debug-simulated-v3") {
            "Authored recordings require an identity distinct from the fixed SIMULATED fixture"
        }
        val baselineResult = SimulatedCatalogSource().load(
            CatalogLoadRequest(definition.location, definition.datasetId, definition.asOf),
        )
        val baseline = when (baselineResult) {
            is CatalogLoadResult.Success -> CatalogRecording(
                baselineResult.catalog, baselineResult.expectedSupport, baselineResult.dailyExpectedSupport,
            )
            is CatalogLoadResult.Partial -> CatalogRecording(
                baselineResult.catalog, baselineResult.expectedSupport, baselineResult.dailyExpectedSupport,
            )
            else -> error("The deterministic SIMULATED baseline could not be materialized")
        }
        val zone = definition.location.timeZone
        val anchor = definition.asOf.atZone(zone).toLocalDate()
        require(definition.startLocal >= anchor.minusDays(30).atStartOfDay() &&
            definition.endLocalExclusive <= anchor.plusDays(14).atStartOfDay()) {
            "Authored period must fit inside the canonical 44-date window"
        }
        val coveredByEvents = definition.events.map { event ->
            require(event.startLocal >= definition.startLocal && event.endLocalExclusive <= definition.endLocalExclusive) {
                "Event range must be contained by the authored envelope"
            }
            require(event.startLocal >= anchor.minusDays(30).atStartOfDay() &&
                event.endLocalExclusive <= anchor.plusDays(14).atStartOfDay()) {
                "Event range must fit inside the canonical 44-date window"
            }
            val covered = baseline.catalog.facts.filter { fact ->
                val local = fact.validTime.atZone(zone).toLocalDateTime()
                local >= event.startLocal && local < event.endLocalExclusive
            }
            require(covered.isNotEmpty()) { "Event range contains no real local-hour instant" }
            event to covered.mapTo(hashSetOf()) { it.validTime }
        }
        val facts = baseline.catalog.facts.map { base ->
            val event = coveredByEvents.firstOrNull { base.validTime in it.second }?.first
            event?.values?.fact(base.validTime) ?: base
        }
        val support = supportFor(facts, zone)
        val catalog = CanonicalCatalog(
            definition.location,
            definition.datasetId,
            CatalogProvenance(
                CatalogOrigin.SIMULATED,
                CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
                sourceId = "oxygen-time-lab-debug-authored",
                productId = definition.datasetId.value,
            ),
            definition.asOf,
            facts,
        )
        require(CatalogWindow.create(catalog, support) is CatalogWindowResult.Success) {
            "Authored catalog does not satisfy the canonical 44-date window"
        }
        return CatalogRecording(catalog, support)
    }

    private fun supportFor(facts: List<CanonicalWeatherFacts>, zone: ZoneId): CatalogExpectedSupport {
        val pointFields = linkedMapOf<LocalDate, MutableMap<CatalogField, MutableSet<Instant>>>()
        val intervalFields = linkedMapOf<LocalDate, MutableMap<CatalogField, MutableSet<AccumulationInterval>>>()
        facts.forEach { fact ->
            val date = fact.validTime.atZone(zone).toLocalDate()
            fun point(field: CatalogField, present: Boolean) {
                if (present) pointFields.getOrPut(date) { linkedMapOf() }
                    .getOrPut(field) { linkedSetOf() }.add(fact.validTime)
            }
            fun interval(field: CatalogField, value: AccumulationInterval?) {
                value?.let { intervalFields.getOrPut(it.end.minusNanos(1).atZone(zone).toLocalDate()) { linkedMapOf() }
                    .getOrPut(field) { linkedSetOf() }.add(it) }
            }
            point(CatalogField.CONDITIONS, fact.conditions.isNotEmpty())
            point(CatalogField.PHENOMENA, fact.phenomena.isNotEmpty())
            point(CatalogField.TEMPERATURE_C, fact.temperatureC != null)
            point(CatalogField.PRECIPITATION_PROBABILITY_PERCENT, fact.precipitationProbabilityPercent != null)
            point(CatalogField.THUNDERSTORM_PROBABILITY_PERCENT, fact.thunderstormProbabilityPercent != null)
            point(CatalogField.RELATIVE_HUMIDITY_PERCENT, fact.relativeHumidityPercent != null)
            point(CatalogField.CLOUD_COVER_PERCENT, fact.cloudCoverPercent != null)
            point(CatalogField.WIND_SPEED_KPH, fact.windSpeedKph != null)
            point(CatalogField.WIND_GUST_KPH, fact.windGustKph != null)
            point(CatalogField.WIND_DIRECTION_DEGREES, fact.windDirectionDegrees != null)
            point(CatalogField.PRECIPITATION_RATE, fact.precipitationRate != null)
            point(CatalogField.SNOW_DEPTH_CENTIMETERS, fact.snowDepthCentimeters != null)
            point(CatalogField.SURFACE_PRESSURE_HPA, fact.surfacePressureHpa != null)
            point(CatalogField.MEAN_SEA_LEVEL_PRESSURE_HPA, fact.meanSeaLevelPressureHpa != null)
            point(CatalogField.VISIBILITY_KM, fact.visibilityKm != null)
            interval(CatalogField.RAIN_AMOUNT_MILLIMETERS, fact.rainAmountMillimeters?.interval)
            interval(CatalogField.TOTAL_PRECIPITATION_MILLIMETERS, fact.totalPrecipitationMillimeters?.interval)
            interval(CatalogField.SNOWFALL_CENTIMETERS, fact.snowfallCentimeters?.interval)
        }
        return (pointFields.keys + intervalFields.keys).associateWith { date ->
            pointFields[date].orEmpty().mapValues { ExpectedFieldSupport.Points(it.value) } +
                intervalFields[date].orEmpty().mapValues { ExpectedFieldSupport.Intervals(it.value) }
        }
    }
}

data class DebugAuthoredCatalogMetadata(
    val name: String,
    val locationId: String,
    val datasetId: String,
    val asOf: Instant,
    val startLocal: LocalDateTime,
    val endLocalExclusive: LocalDateTime,
    val recordingSha256: String,
    val events: List<DebugAuthoredEvent> = emptyList(),
)

/** Sidecar metadata stays in debug app preferences and is bound to the canonical recording bytes. */
interface DebugAuthoredMetadataBackend {
    fun get(key: String): String?
    fun put(key: String, value: String): Boolean
    fun remove(key: String): Boolean
}

class DebugAuthoredCatalogMetadataStore(private val backend: DebugAuthoredMetadataBackend) {
    constructor(preferences: SharedPreferences) : this(object : DebugAuthoredMetadataBackend {
        override fun get(key: String) = preferences.getString(key, null)
        override fun put(key: String, value: String) = preferences.edit().putString(key, value).commit()
        override fun remove(key: String) = preferences.edit().remove(key).commit()
    })

    private val mapper = ObjectMapper()

    fun save(definition: DebugAuthoredCatalog, recording: CatalogRecording): DebugAuthoredCatalogMetadata {
        require(recording.catalog.location == definition.location && recording.catalog.datasetId == definition.datasetId &&
            recording.catalog.asOf == definition.asOf && recording.catalog.provenance.origin == CatalogOrigin.SIMULATED) {
            "Authored metadata identity does not match its SIMULATED recording"
        }
        val bytes = RecordedCatalogCodec.encode(recording)
        val metadata = DebugAuthoredCatalogMetadata(
            definition.name.trim(), definition.location.id.value, definition.datasetId.value, definition.asOf,
            definition.startLocal, definition.endLocalExclusive, sha256(bytes), definition.events,
        )
        check(put(key(definition.location.id.value, definition.datasetId.value), encode(metadata))) {
            "Could not save debug authored-catalog metadata"
        }
        return metadata
    }

    fun load(location: CatalogLocation, datasetId: DatasetId, recording: CatalogRecording): DebugAuthoredCatalog? {
        val raw = get(key(location.id.value, datasetId.value)) ?: return null
        val metadata = decode(raw) ?: return null
        if (metadata.locationId != location.id.value || metadata.datasetId != datasetId.value ||
            metadata.asOf != recording.catalog.asOf || recording.catalog.location != location ||
            recording.catalog.datasetId != datasetId || recording.catalog.provenance.origin != CatalogOrigin.SIMULATED ||
            sha256(RecordedCatalogCodec.encode(recording)) != metadata.recordingSha256) return null
        val events = metadata.events.ifEmpty {
            val sampleLocal = metadata.startLocal
            val eventTime = recording.catalog.facts.firstOrNull {
                it.validTime.atZone(location.timeZone).toLocalDateTime() >= sampleLocal &&
                    it.validTime.atZone(location.timeZone).toLocalDateTime() < metadata.endLocalExclusive
            }?.validTime ?: return null
            listOf(DebugAuthoredEvent(metadata.startLocal, metadata.endLocalExclusive,
                recording.catalog.facts.first { it.validTime == eventTime }.toAuthoredValues()))
        }
        if (events.any { event ->
                val covered = recording.catalog.facts.filter { fact ->
                    val local = fact.validTime.atZone(location.timeZone).toLocalDateTime()
                    local >= event.startLocal && local < event.endLocalExclusive
                }
                covered.isEmpty() || covered.any { event.values.fact(it.validTime) != it }
            }) return null
        return DebugAuthoredCatalog(metadata.name, location, datasetId, metadata.asOf,
            metadata.startLocal, metadata.endLocalExclusive, events.first().values, events)
    }

    fun remove(locationId: String, datasetId: String) {
        check(remove(key(locationId, datasetId))) { "Could not remove debug authored metadata" }
    }

    private fun get(key: String) = backend.get(key)
    private fun put(key: String, value: String) = backend.put(key, value)
    private fun remove(key: String) = backend.remove(key)

    private fun encode(value: DebugAuthoredCatalogMetadata): String = mapper.writeValueAsString(mapper.createObjectNode().apply {
        put("schemaVersion", 3)
        put("name", value.name); put("locationId", value.locationId); put("datasetId", value.datasetId)
        put("asOf", value.asOf.toString()); put("startLocal", value.startLocal.toString())
        put("endLocalExclusive", value.endLocalExclusive.toString()); put("recordingSha256", value.recordingSha256)
        set<com.fasterxml.jackson.databind.JsonNode>("events", mapper.createArrayNode().apply {
            value.events.forEach { event -> add(mapper.createObjectNode().apply {
                put("startLocal", event.startLocal.toString())
                put("endLocalExclusive", event.endLocalExclusive.toString())
                event.symbolId?.let { put("symbolId", it.name) }
                set<com.fasterxml.jackson.databind.JsonNode>("values", authoredValuesNode(event.values))
            }) }
        })
    })

    private fun decode(json: String): DebugAuthoredCatalogMetadata? = try {
        val node = mapper.readTree(json)
        val schemaVersion = node.get("schemaVersion")?.asInt() ?: 1
        require(schemaVersion in 1..3) { "Unsupported authored metadata version" }
        val events = node.get("events")?.map { event -> DebugAuthoredEvent(
            LocalDateTime.parse(event.required("startLocal").asText()),
            LocalDateTime.parse(event.required("endLocalExclusive").asText()),
            authoredValues(event.required("values")),
            event.get("symbolId")?.asText()?.let { id -> WeatherSymbolId.entries.firstOrNull { it.name == id } },
        ) } ?: emptyList()
        DebugAuthoredCatalogMetadata(node.required("name").asText(), node.required("locationId").asText(),
            node.required("datasetId").asText(), Instant.parse(node.required("asOf").asText()),
            LocalDateTime.parse(node.required("startLocal").asText()), LocalDateTime.parse(node.required("endLocalExclusive").asText()),
            node.required("recordingSha256").asText(), events)
    } catch (_: Exception) { null }

    private fun key(locationId: String, datasetId: String) = "authored:$locationId:$datasetId"
    private fun sha256(bytes: ByteArray) = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }

    private fun authoredValuesNode(values: DebugAuthoredWeatherValues) = mapper.createObjectNode().apply {
        set<com.fasterxml.jackson.databind.JsonNode>("conditions", mapper.valueToTree(values.conditions.map { it.name }))
        set<com.fasterxml.jackson.databind.JsonNode>("phenomena", mapper.valueToTree(values.phenomena.map { it.name }))
        fun number(name: String, value: Double?) { if (value == null) putNull(name) else put(name, value) }
        number("temperatureC", values.temperatureC); number("precipitationProbabilityPercent", values.precipitationProbabilityPercent)
        number("thunderstormProbabilityPercent", values.thunderstormProbabilityPercent); number("relativeHumidityPercent", values.relativeHumidityPercent)
        number("cloudCoverPercent", values.cloudCoverPercent); number("windSpeedKph", values.windSpeedKph)
        number("windGustKph", values.windGustKph); number("windDirectionDegrees", values.windDirectionDegrees)
        number("rainAmountMillimeters", values.rainAmountMillimeters); number("totalPrecipitationMillimeters", values.totalPrecipitationMillimeters)
        number("precipitationRateMillimetersPerHour", values.precipitationRateMillimetersPerHour)
        number("snowfallCentimeters", values.snowfallCentimeters); number("snowDepthCentimeters", values.snowDepthCentimeters)
        number("surfacePressureHpa", values.surfacePressureHpa); number("meanSeaLevelPressureHpa", values.meanSeaLevelPressureHpa)
        number("visibilityKm", values.visibilityKm)
    }

    private fun authoredValues(node: com.fasterxml.jackson.databind.JsonNode): DebugAuthoredWeatherValues {
        fun number(name: String) = node.get(name)?.takeUnless { it.isNull }?.asDouble()
        fun <T : Enum<T>> enums(name: String, values: Array<T>): Set<T> = node.get(name)?.mapNotNull { item ->
            values.firstOrNull { it.name == item.asText() }
        }?.toSet().orEmpty()
        return DebugAuthoredWeatherValues(
            conditions = enums("conditions", WeatherCondition.entries.toTypedArray()),
            phenomena = enums("phenomena", WeatherPhenomenon.entries.toTypedArray()),
            temperatureC = number("temperatureC"), precipitationProbabilityPercent = number("precipitationProbabilityPercent"),
            thunderstormProbabilityPercent = number("thunderstormProbabilityPercent"), relativeHumidityPercent = number("relativeHumidityPercent"),
            cloudCoverPercent = number("cloudCoverPercent"), windSpeedKph = number("windSpeedKph"), windGustKph = number("windGustKph"),
            windDirectionDegrees = number("windDirectionDegrees"), rainAmountMillimeters = number("rainAmountMillimeters"),
            totalPrecipitationMillimeters = number("totalPrecipitationMillimeters"),
            precipitationRateMillimetersPerHour = number("precipitationRateMillimetersPerHour"),
            snowfallCentimeters = number("snowfallCentimeters"), snowDepthCentimeters = number("snowDepthCentimeters"),
            surfacePressureHpa = number("surfacePressureHpa"), meanSeaLevelPressureHpa = number("meanSeaLevelPressureHpa"),
            visibilityKm = number("visibilityKm"),
        )
    }
}

/** Couples canonical persistence with sidecar metadata and refuses identity reuse. */
class DebugAuthoredCatalogRepository(
    private val storage: CatalogDatasetStorage,
    private val metadata: DebugAuthoredCatalogMetadataStore,
) {
    /** Pass to the coordinator; it is called with the recording only after coordinator-owned storage load. */
    fun previewMetadataValidator(location: CatalogLocation, datasetId: DatasetId): (CatalogRecording) -> Boolean =
        { recording -> metadata.load(location, datasetId, recording) != null }

    suspend fun save(definition: DebugAuthoredCatalog): CatalogRecording {
        require(storage.load(definition.location, CatalogStorageNamespace.SIMULATED, definition.datasetId) is StoredCatalogResult.Missing) {
            "Saved SIMULATED identities are immutable; use a new dataset identity for an edited revision"
        }
        val recording = DebugAuthoredCatalogMaterializer.materialize(definition)
        storage.save(CatalogStorageNamespace.SIMULATED, recording)
        try {
            metadata.save(definition, recording)
        } catch (failure: Exception) {
            storage.delete(definition.location.id, CatalogStorageNamespace.SIMULATED, definition.datasetId)
            throw failure
        }
        return recording
    }

    suspend fun reopen(location: CatalogLocation, datasetId: DatasetId): Pair<DebugAuthoredCatalog, CatalogRecording>? {
        val stored = storage.load(location, CatalogStorageNamespace.SIMULATED, datasetId) as? StoredCatalogResult.Found
            ?: return null
        val definition = metadata.load(location, datasetId, stored.recording) ?: return null
        return definition to stored.recording
    }

    suspend fun captureReplay(
        location: CatalogLocation,
        simulatedId: DatasetId,
        replayId: DatasetId,
    ): CatalogRecording {
        require(replayId != simulatedId) { "REPLAY snapshots require a distinct dataset identity" }
        require(storage.load(location, CatalogStorageNamespace.REPLAY, replayId) is StoredCatalogResult.Missing) {
            "REPLAY snapshots are immutable"
        }
        val (sourceDefinition, simulated) = reopen(location, simulatedId)
            ?: error("SIMULATED recording or its authored metadata is missing or inconsistent")
        val replayCatalog = CanonicalCatalog(
            simulated.catalog.location,
            replayId,
            simulated.catalog.provenance,
            simulated.catalog.asOf,
            simulated.catalog.facts,
            simulated.catalog.productSegments,
            simulated.catalog.dailySummaries,
        )
        val replay = CatalogRecording(replayCatalog, simulated.expectedSupport, simulated.dailyExpectedSupport)
        storage.save(CatalogStorageNamespace.REPLAY, replay)
        try {
            metadata.save(sourceDefinition.copy(name = "${sourceDefinition.name} replay", datasetId = replayId), replay)
        } catch (failure: Exception) {
            storage.delete(location.id, CatalogStorageNamespace.REPLAY, replayId)
            throw failure
        }
        return replay
    }

    suspend fun reopenReplay(location: CatalogLocation, replayId: DatasetId): Pair<DebugAuthoredCatalog, CatalogRecording>? {
        val stored = storage.load(location, CatalogStorageNamespace.REPLAY, replayId) as? StoredCatalogResult.Found
            ?: return null
        val definition = metadata.load(location, replayId, stored.recording) ?: return null
        return definition to stored.recording
    }
}

private fun CanonicalWeatherFacts.toAuthoredValues() = DebugAuthoredWeatherValues(
    conditions, phenomena, temperatureC, precipitationProbabilityPercent, thunderstormProbabilityPercent,
    relativeHumidityPercent, cloudCoverPercent, windSpeedKph, windGustKph, windDirectionDegrees,
    rainAmountMillimeters?.valueMillimeters, totalPrecipitationMillimeters?.valueMillimeters,
    precipitationRate?.valueMillimetersPerHour, snowfallCentimeters?.valueCentimeters, snowDepthCentimeters,
    surfacePressureHpa, meanSeaLevelPressureHpa, visibilityKm,
)
