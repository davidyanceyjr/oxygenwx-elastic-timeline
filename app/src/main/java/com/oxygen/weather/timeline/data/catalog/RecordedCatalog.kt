package com.oxygen.weather.timeline.data.catalog

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.StreamReadFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Collections
import kotlinx.coroutines.ensureActive

/** A captured original product and its declared support. The codec has no I/O or clock. */
data class CatalogRecording(
    val catalog: CanonicalCatalog,
    val expectedSupport: CatalogExpectedSupport,
    val dailyExpectedSupport: DailyExpectedSupport = emptyMap(),
)

sealed interface RecordingDecodeResult {
    data class Success(val recording: CatalogRecording) : RecordingDecodeResult
    data class Failure(val kind: RecordingError, val detail: String) : RecordingDecodeResult
}

enum class RecordingError { MALFORMED, UNSUPPORTED_VERSION, INVALID_VALUE, IDENTITY_MISMATCH }

object RecordedCatalogCodec {
    private val mapper = ObjectMapper(JsonFactory.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build())
    private val factFields = setOf(
        "validTime", "conditions", "phenomena", "temperatureC", "precipitationProbabilityPercent",
        "thunderstormProbabilityPercent", "relativeHumidityPercent", "cloudCoverPercent", "windSpeedKph",
        "windGustKph", "windDirectionDegrees", "rainAmountMillimeters", "totalPrecipitationMillimeters",
        "precipitationRate", "snowfallCentimeters", "snowDepthCentimeters", "surfacePressureHpa",
        "meanSeaLevelPressureHpa", "visibilityKm",
    )

    fun encode(recording: CatalogRecording): ByteArray {
        require(recording.catalog.provenance.origin != CatalogOrigin.REPLAY) { "Recording must capture an original catalog" }
        require(recording.catalog.facts.map { it.validTime }.distinct().size == recording.catalog.facts.size)
        require(recording.catalog.facts.isNotEmpty() && recording.expectedSupport.values.any { it.isNotEmpty() })
        require(CatalogWindow.create(recording.catalog, recording.expectedSupport, dailyExpectedSupport = recording.dailyExpectedSupport) is CatalogWindowResult.Success)
        val catalog = recording.catalog
        val root = mapper.createObjectNode()
        val version = if (catalog.productSegments.isEmpty() && catalog.dailySummaries.isEmpty() && recording.dailyExpectedSupport.isEmpty()) 1 else 2
        root.put("schemaVersion", version)
        root.set<JsonNode>("location", mapper.createObjectNode().apply {
            put("id", catalog.location.id.value)
            set<JsonNode>("coordinates", mapper.createObjectNode().apply {
                put("latitudeDegrees", catalog.location.coordinates.latitudeDegrees)
                put("longitudeDegrees", catalog.location.coordinates.longitudeDegrees)
            })
            put("timeZone", catalog.location.timeZone.id)
        })
        root.put("datasetId", catalog.datasetId.value)
        root.set<JsonNode>("provenance", mapper.createObjectNode().apply {
            put("origin", catalog.provenance.origin.name)
            put("productKind", catalog.provenance.productKind.name)
            putNullable("sourceId", catalog.provenance.sourceId)
            putNullable("productId", catalog.provenance.productId)
            putNullable("issuedAt", catalog.provenance.issuedAt?.toString())
        })
        root.put("asOf", catalog.asOf.toString())
        root.set<JsonNode>("facts", mapper.createArrayNode().apply {
            catalog.facts.sortedBy { it.validTime }.forEach { add(writeFact(it)) }
        })
        root.set<JsonNode>("expectedSupport", mapper.createArrayNode().apply {
            recording.expectedSupport.toSortedMap().forEach { (date, fields) ->
                fields.toSortedMap(compareBy { it.name }).forEach { (field, support) ->
                    add(mapper.createObjectNode().apply {
                        put("date", date.toString())
                        put("field", field.name)
                        when (support) {
                            is ExpectedFieldSupport.Points -> {
                                put("kind", "POINTS")
                                set<JsonNode>("instants", mapper.createArrayNode().apply {
                                    support.instants.sorted().forEach { add(it.toString()) }
                                })
                            }
                            is ExpectedFieldSupport.Intervals -> {
                                put("kind", "INTERVALS")
                                set<JsonNode>("intervals", mapper.createArrayNode().apply {
                                    support.intervals.sortedWith(compareBy({ it.start }, { it.end })).forEach { add(writeInterval(it)) }
                                })
                            }
                        }
                    })
                }
            }
        })
        if (version == 2) {
            root.set<JsonNode>("productSegments", mapper.createArrayNode().apply {
                catalog.productSegments.forEach { segment -> add(mapper.createObjectNode().apply {
                    put("startDateInclusive", segment.startDateInclusive.toString())
                    put("endDateExclusive", segment.endDateExclusive.toString())
                    put("productKind", segment.productKind.name)
                    putNullable("sourceId", segment.sourceId)
                    putNullable("productId", segment.productId)
                    putNullable("issuedAt", segment.issuedAt?.toString())
                }) }
            })
            root.set<JsonNode>("dailySummaries", mapper.createArrayNode().apply {
                catalog.dailySummaries.sortedBy { it.date }.forEach { summary -> add(mapper.createObjectNode().apply {
                    put("date", summary.date.toString())
                    set<JsonNode>("support", writeInterval(summary.support))
                    putNumber("temperatureMaxC", summary.temperatureMaxC)
                    putNumber("temperatureMinC", summary.temperatureMinC)
                    putNumber("precipitationProbabilityMaxPercent", summary.precipitationProbabilityMaxPercent)
                    set<JsonNode>("conditions", mapper.createArrayNode().apply { summary.conditions.map { it.name }.sorted().forEach(::add) })
                    set<JsonNode>("phenomena", mapper.createArrayNode().apply { summary.phenomena.map { it.name }.sorted().forEach(::add) })
                    putNumber("rainAmountMillimeters", summary.rainAmountMillimeters)
                    putNumber("totalPrecipitationMillimeters", summary.totalPrecipitationMillimeters)
                    putNumber("snowfallCentimeters", summary.snowfallCentimeters)
                    put("origin", summary.origin.name)
                }) }
            })
            root.set<JsonNode>("dailyExpectedSupport", mapper.createArrayNode().apply {
                recording.dailyExpectedSupport.toSortedMap().forEach { (date, fields) -> fields.sortedBy { it.name }.forEach { field ->
                    add(mapper.createObjectNode().apply { put("date", date.toString()); put("field", field.name) })
                } }
            })
        }
        return mapper.writeValueAsBytes(root)
    }

    fun decode(bytes: ByteArray): RecordingDecodeResult {
        val root = try {
            mapper.factory.createParser(bytes).use { parser ->
                val parsed = mapper.readTree<JsonNode>(parser)
                require(parser.nextToken() == null) { "Trailing JSON content" }
                parsed
            }
        } catch (e: Exception) {
            return RecordingDecodeResult.Failure(RecordingError.MALFORMED, e.message ?: "Invalid JSON")
        }
        if (root == null || !root.isObject || !root.has("schemaVersion") || !root["schemaVersion"].isIntegralNumber) {
            return RecordingDecodeResult.Failure(RecordingError.MALFORMED, "schemaVersion must be an integer")
        }
        val version = root["schemaVersion"].takeIf { it.canConvertToInt() }?.intValue() ?: 0
        if (version !in 1..2) {
            return RecordingDecodeResult.Failure(RecordingError.UNSUPPORTED_VERSION, "Unsupported schemaVersion")
        }
        return try {
            root.fieldsExact(*(if (version == 1) arrayOf("schemaVersion", "location", "datasetId", "provenance", "asOf", "facts", "expectedSupport") else arrayOf("schemaVersion", "location", "datasetId", "provenance", "asOf", "facts", "expectedSupport", "productSegments", "dailySummaries", "dailyExpectedSupport")))
            val locationNode = root.required("location").fieldsExact("id", "coordinates", "timeZone")
            val coordinates = locationNode.required("coordinates").fieldsExact("latitudeDegrees", "longitudeDegrees")
            val location = CatalogLocation(
                LocationId(locationNode.required("id").string()),
                GeoCoordinates(coordinates.required("latitudeDegrees").number(), coordinates.required("longitudeDegrees").number()),
                ZoneId.of(locationNode.required("timeZone").string()),
            )
            val provenanceNode = root.required("provenance").fieldsExact("origin", "productKind", "sourceId", "productId", "issuedAt")
            val provenance = CatalogProvenance(
                enum<CatalogOrigin>(provenanceNode.required("origin")),
                enum<CatalogProductKind>(provenanceNode.required("productKind")),
                provenanceNode.required("sourceId").nullableString(),
                provenanceNode.required("productId").nullableString(),
                provenanceNode.required("issuedAt").nullableString()?.let(Instant::parse),
            )
            require(provenance.origin != CatalogOrigin.REPLAY) { "Captured origin cannot be REPLAY" }
            val facts = root.required("facts").array().map(::readFact)
            require(facts.isNotEmpty() && facts.map { it.validTime }.distinct().size == facts.size) { "Facts must have unique times" }
            val segments = if (version == 2) root.required("productSegments").array().map { node ->
                node.fieldsExact("startDateInclusive", "endDateExclusive", "productKind", "sourceId", "productId", "issuedAt")
                CatalogProductSegment(LocalDate.parse(node.required("startDateInclusive").string()), LocalDate.parse(node.required("endDateExclusive").string()),
                    enum(node.required("productKind")), node.required("sourceId").nullableString(), node.required("productId").nullableString(), node.required("issuedAt").nullableString()?.let(Instant::parse))
            } else emptyList()
            val summaries = if (version == 2) root.required("dailySummaries").array().map { node ->
                node.fieldsExact("date", "support", "temperatureMaxC", "temperatureMinC", "precipitationProbabilityMaxPercent", "conditions", "phenomena", "rainAmountMillimeters", "totalPrecipitationMillimeters", "snowfallCentimeters", "origin")
                val conditions = node.required("conditions").array().map { enum<WeatherCondition>(it) }
                val phenomena = node.required("phenomena").array().map { enum<WeatherPhenomenon>(it) }
                require(conditions.size == conditions.distinct().size && phenomena.size == phenomena.distinct().size) { "Duplicate daily enum value" }
                ProviderDailySummary(LocalDate.parse(node.required("date").string()), readInterval(node.required("support")),
                    node.required("temperatureMaxC").nullableNumber(), node.required("temperatureMinC").nullableNumber(), node.required("precipitationProbabilityMaxPercent").nullableNumber(),
                    conditions.toSet(), phenomena.toSet(),
                    node.required("rainAmountMillimeters").nullableNumber(), node.required("totalPrecipitationMillimeters").nullableNumber(), node.required("snowfallCentimeters").nullableNumber(), enum(node.required("origin")))
            } else emptyList()
            val catalog = CanonicalCatalog(location, DatasetId(root.required("datasetId").string()), provenance,
                Instant.parse(root.required("asOf").string()), facts, segments, summaries)
            val support = linkedMapOf<LocalDate, MutableMap<CatalogField, ExpectedFieldSupport>>()
            root.required("expectedSupport").array().forEach { node ->
                require(node.isObject) { "Support entry must be an object" }
                val kind = node.required("kind").string()
                node.fieldsExact(*(if (kind == "POINTS") arrayOf("date", "field", "kind", "instants")
                    else arrayOf("date", "field", "kind", "intervals")))
                val date = LocalDate.parse(node.required("date").string())
                val field = enum<CatalogField>(node.required("field"))
                val value = when (kind) {
                    "POINTS" -> {
                        val items = node.required("instants").array().map { Instant.parse(it.string()) }
                        require(items.size == items.distinct().size) { "Duplicate support instant" }
                        ExpectedFieldSupport.Points(items.toSet())
                    }
                    "INTERVALS" -> {
                        val items = node.required("intervals").array().map(::readInterval)
                        require(items.size == items.distinct().size) { "Duplicate support interval" }
                        ExpectedFieldSupport.Intervals(items.toSet())
                    }
                    else -> error("Unknown support kind")
                }
                require(support.getOrPut(date) { linkedMapOf() }.putIfAbsent(field, value) == null) { "Duplicate support field" }
            }
            require(support.values.any { it.isNotEmpty() }) { "No declared support" }
            val dailySupport = linkedMapOf<LocalDate, MutableSet<DailyCatalogField>>()
            if (version == 2) root.required("dailyExpectedSupport").array().forEach { node ->
                node.fieldsExact("date", "field")
                val date = LocalDate.parse(node.required("date").string())
                require(dailySupport.getOrPut(date) { linkedSetOf() }.add(enum(node.required("field")))) { "Duplicate daily support field" }
            }
            require(summaries.all { it.date in dailySupport }) { "Daily summaries require declared daily support" }
            require(CatalogWindow.create(catalog, support, dailyExpectedSupport = dailySupport) is CatalogWindowResult.Success) { "Invalid window or support" }
            RecordingDecodeResult.Success(CatalogRecording(catalog, Collections.unmodifiableMap(support), dailySupport.mapValues { it.value.toSet() }))
        } catch (e: Exception) {
            RecordingDecodeResult.Failure(RecordingError.INVALID_VALUE, e.message ?: "Invalid canonical value")
        }
    }

    private fun writeFact(f: CanonicalWeatherFacts): ObjectNode = mapper.createObjectNode().apply {
        put("validTime", f.validTime.toString())
        set<JsonNode>("conditions", mapper.createArrayNode().apply { f.conditions.map { it.name }.sorted().forEach(::add) })
        set<JsonNode>("phenomena", mapper.createArrayNode().apply { f.phenomena.map { it.name }.sorted().forEach(::add) })
        putNumber("temperatureC", f.temperatureC)
        putNumber("precipitationProbabilityPercent", f.precipitationProbabilityPercent)
        putNumber("thunderstormProbabilityPercent", f.thunderstormProbabilityPercent)
        putNumber("relativeHumidityPercent", f.relativeHumidityPercent)
        putNumber("cloudCoverPercent", f.cloudCoverPercent)
        putNumber("windSpeedKph", f.windSpeedKph)
        putNumber("windGustKph", f.windGustKph)
        putNumber("windDirectionDegrees", f.windDirectionDegrees)
        set<JsonNode>("rainAmountMillimeters", f.rainAmountMillimeters?.let { writeAmount(it.valueMillimeters, it.interval) } ?: mapper.nullNode())
        set<JsonNode>("totalPrecipitationMillimeters", f.totalPrecipitationMillimeters?.let { writeAmount(it.valueMillimeters, it.interval) } ?: mapper.nullNode())
        set<JsonNode>("precipitationRate", f.precipitationRate?.let { mapper.createObjectNode().apply {
            put("valueMillimetersPerHour", it.valueMillimetersPerHour)
            set<JsonNode>("meanInterval", it.meanInterval?.let(::writeInterval) ?: mapper.nullNode())
        } } ?: mapper.nullNode())
        set<JsonNode>("snowfallCentimeters", f.snowfallCentimeters?.let { writeAmount(it.valueCentimeters, it.interval) } ?: mapper.nullNode())
        putNumber("snowDepthCentimeters", f.snowDepthCentimeters)
        putNumber("surfacePressureHpa", f.surfacePressureHpa)
        putNumber("meanSeaLevelPressureHpa", f.meanSeaLevelPressureHpa)
        putNumber("visibilityKm", f.visibilityKm)
    }

    private fun readFact(node: JsonNode): CanonicalWeatherFacts {
        node.fieldsExact(*factFields.toTypedArray())
        val conditions = node.required("conditions").array().map { enum<WeatherCondition>(it) }
        val phenomena = node.required("phenomena").array().map { enum<WeatherPhenomenon>(it) }
        require(conditions.size == conditions.distinct().size && phenomena.size == phenomena.distinct().size)
        fun amount(name: String): Pair<Double, AccumulationInterval>? = node.required(name).takeUnless { it.isNull }?.let {
            it.fieldsExact("value", "interval")
            it.required("value").number() to readInterval(it.required("interval"))
        }
        val rain = amount("rainAmountMillimeters")
        val total = amount("totalPrecipitationMillimeters")
        val snow = amount("snowfallCentimeters")
        val rate = node.required("precipitationRate").takeUnless { it.isNull }?.let {
            it.fieldsExact("valueMillimetersPerHour", "meanInterval")
            PrecipitationRate(it.required("valueMillimetersPerHour").number(), it.required("meanInterval").takeUnless(JsonNode::isNull)?.let(::readInterval))
        }
        return CanonicalWeatherFacts(
            Instant.parse(node.required("validTime").string()), conditions.toSet(), phenomena.toSet(),
            node.required("temperatureC").nullableNumber(), node.required("precipitationProbabilityPercent").nullableNumber(),
            node.required("thunderstormProbabilityPercent").nullableNumber(), node.required("relativeHumidityPercent").nullableNumber(),
            node.required("cloudCoverPercent").nullableNumber(), node.required("windSpeedKph").nullableNumber(),
            node.required("windGustKph").nullableNumber(), node.required("windDirectionDegrees").nullableNumber(),
            rain?.let { AccumulatedMillimeters(it.first, it.second) }, total?.let { AccumulatedMillimeters(it.first, it.second) },
            rate, snow?.let { AccumulatedSnowfallCentimeters(it.first, it.second) },
            node.required("snowDepthCentimeters").nullableNumber(), node.required("surfacePressureHpa").nullableNumber(),
            node.required("meanSeaLevelPressureHpa").nullableNumber(), node.required("visibilityKm").nullableNumber(),
        )
    }

    private fun writeAmount(value: Double, interval: AccumulationInterval): ObjectNode = mapper.createObjectNode().apply {
        put("value", value)
        set<JsonNode>("interval", writeInterval(interval))
    }
    private fun writeInterval(interval: AccumulationInterval): ObjectNode = mapper.createObjectNode().apply {
        put("start", interval.start.toString())
        put("end", interval.end.toString())
    }
    private fun readInterval(node: JsonNode): AccumulationInterval {
        node.fieldsExact("start", "end")
        return AccumulationInterval(Instant.parse(node.required("start").string()), Instant.parse(node.required("end").string()))
    }
    private fun ObjectNode.putNullable(name: String, value: String?) { if (value == null) putNull(name) else put(name, value) }
    private fun ObjectNode.putNumber(name: String, value: Double?) { if (value == null) putNull(name) else put(name, value) }
    private fun JsonNode.required(name: String): JsonNode = get(name) ?: error("Missing $name")
    private fun JsonNode.fieldsExact(vararg names: String): JsonNode {
        require(isObject && fieldNames().asSequence().toSet() == names.toSet()) { "Unexpected or missing object field" }
        return this
    }
    private fun JsonNode.array(): List<JsonNode> { require(isArray); return toList() }
    private fun JsonNode.string(): String { require(isTextual); return textValue() }
    private fun JsonNode.nullableString(): String? = if (isNull) null else string()
    private fun JsonNode.number(): Double { require(isNumber); return doubleValue().also { require(it.isFinite()) } }
    private fun JsonNode.nullableNumber(): Double? = if (isNull) null else number()
    private inline fun <reified T : Enum<T>> enum(node: JsonNode): T = enumValueOf<T>(node.string())
}

/** Caller supplies bytes; no file, network, or time service is accessed. */
class RecordedCatalogSource(bytes: ByteArray?) : WeatherCatalogSource {
    private val bytes = bytes?.clone()
    override val origin = CatalogOrigin.REPLAY

    override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
        kotlinx.coroutines.currentCoroutineContext().ensureActive()
        val input = bytes ?: return CatalogLoadResult.NoData(NoDataReason.EMPTY_SOURCE)
        val decoded = RecordedCatalogCodec.decode(input)
        if (decoded is RecordingDecodeResult.Failure) {
            return CatalogLoadResult.TransportFailure(TransportFailureReason.INVALID_RESPONSE,
                CatalogLoadMetadata(detail = "replay:${decoded.kind.name.lowercase()}"))
        }
        val recording = (decoded as RecordingDecodeResult.Success).recording
        val original = recording.catalog
        if (original.location.id != request.location.id) return CatalogLoadResult.InvalidRequest(InvalidRequestReason.UNSUPPORTED_LOCATION)
        if (original.datasetId != request.datasetId) return CatalogLoadResult.InvalidRequest(InvalidRequestReason.UNSUPPORTED_DATASET)
        if (original.location.coordinates != request.location.coordinates || original.location.timeZone != request.location.timeZone || original.asOf != request.asOf) {
            return CatalogLoadResult.TransportFailure(TransportFailureReason.INVALID_RESPONSE, CatalogLoadMetadata(detail = "replay:identity_mismatch"))
        }
        val replay = CanonicalCatalog(original.location, original.datasetId,
            original.provenance.copy(origin = CatalogOrigin.REPLAY), original.asOf, original.facts,
            original.productSegments, original.dailySummaries)
        val window = CatalogWindow.create(replay, recording.expectedSupport, dailyExpectedSupport = recording.dailyExpectedSupport)
        if (window !is CatalogWindowResult.Success) {
            return CatalogLoadResult.TransportFailure(TransportFailureReason.INVALID_RESPONSE, CatalogLoadMetadata(detail = "replay:invalid_value"))
        }
        val metadata = CatalogLoadMetadata(replayOriginalOrigin = original.provenance.origin)
        val complete = window.window.days.all { day -> day.fieldCoverage.values.all { it == CoverageStatus.COMPLETE } && day.dailyCoverage.values.all { it == CoverageStatus.COMPLETE } }
        return if (complete) CatalogLoadResult.Success(replay, recording.expectedSupport, metadata, recording.dailyExpectedSupport)
            else CatalogLoadResult.Partial(replay, recording.expectedSupport, metadata, recording.dailyExpectedSupport)
    }
}
