package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class RecordedCatalogTest {
    private val request = CatalogLoadRequest(
        CatalogLocation(LocationId("wx11-chicago-synthetic"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago")),
        DatasetId("wx11-full-sequence-v1"), Instant.parse("2026-03-08T12:00:00.123456789Z"),
    )

    private fun captured(): CatalogRecording = runBlocking {
        val result = SimulatedCatalogSource().load(request) as CatalogLoadResult.Success
        CatalogRecording(result.catalog, result.expectedSupport)
    }

    private fun fixture(): ByteArray = javaClass.getResourceAsStream("/catalog/wx11-full-sequence-v1.json")!!.readBytes()

    @Test fun fixtureRoundTripsAndReplays() = runBlocking {
        val original = captured()
        val encoded = RecordedCatalogCodec.encode(original)
        assertArrayEquals(fixture(), encoded)
        val decoded = RecordedCatalogCodec.decode(fixture()) as RecordingDecodeResult.Success
        assertEquals(original.catalog, decoded.recording.catalog)
        assertEquals(original.expectedSupport.keys, decoded.recording.expectedSupport.keys)
        original.expectedSupport.forEach { (date, fields) ->
            assertEquals(fields.keys, decoded.recording.expectedSupport[date]?.keys)
            fields.forEach { (field, support) ->
                val actual = decoded.recording.expectedSupport[date]?.get(field)
                when (support) {
                    is ExpectedFieldSupport.Points -> assertEquals(support.instants, (actual as ExpectedFieldSupport.Points).instants)
                    is ExpectedFieldSupport.Intervals -> assertEquals(support.intervals, (actual as ExpectedFieldSupport.Intervals).intervals)
                }
            }
        }
        val replay = CatalogSourceRegistry(listOf(RecordedCatalogSource(fixture()))).load(CatalogOrigin.REPLAY, request)
        assertTrue(replay is CatalogLoadResult.Success)
        replay as CatalogLoadResult.Success
        assertEquals(CatalogOrigin.REPLAY, replay.catalog.provenance.origin)
        assertEquals(CatalogOrigin.SIMULATED, replay.metadata.replayOriginalOrigin)
        assertEquals(original.catalog.facts, replay.catalog.facts)
        assertTrue(original.catalog.facts.any { it.thunderstormProbabilityPercent != null })
        assertTrue(original.catalog.facts.any { it.rainAmountMillimeters?.valueMillimeters == 0.0 })
        assertTrue(original.catalog.facts.any { it.rainAmountMillimeters == null })
    }

    @Test fun badVersionShapeAndIdentityFail() = runBlocking {
        val source = String(fixture(), Charsets.UTF_8)
        fun failure(json: String) = RecordedCatalogCodec.decode(json.toByteArray()) as RecordingDecodeResult.Failure
        assertEquals(RecordingError.UNSUPPORTED_VERSION, failure(source.replace("\"schemaVersion\":1", "\"schemaVersion\":0")).kind)
        assertEquals(RecordingError.MALFORMED, failure(source.replace("\"schemaVersion\":1", "\"schemaVersion\":1,\"schemaVersion\":1")).kind)
        assertEquals(RecordingError.INVALID_VALUE, failure(source.replace("\"timeZone\":\"America/Chicago\"", "\"timeZone\":\"UTC\"")).kind)
        val mismatched = RecordedCatalogSource(fixture()).load(request.copy(asOf = request.asOf.plusSeconds(1)))
        assertEquals("replay:identity_mismatch", (mismatched as CatalogLoadResult.TransportFailure).metadata.detail)
        assertTrue(RecordedCatalogSource(null).load(request) is CatalogLoadResult.NoData)
    }

    @Test fun partialSupportDoesNotBecomeComplete() = runBlocking {
        val original = captured()
        val date = original.expectedSupport.keys.first()
        val fields = original.expectedSupport[date]!!.toMutableMap()
        val fact = original.catalog.facts.first()
        fields[CatalogField.VISIBILITY_KM] = ExpectedFieldSupport.Points(setOf(fact.validTime))
        val support = original.expectedSupport.toMutableMap().apply { put(date, fields) }
        val bytes = RecordedCatalogCodec.encode(CatalogRecording(original.catalog, support))
        val outcome = CatalogSourceRegistry(listOf(RecordedCatalogSource(bytes))).load(CatalogOrigin.REPLAY, request)
        assertTrue(outcome is CatalogLoadResult.Partial)
    }

    @Test fun archivedModelMeaningAndPrecisionSurviveReplay() = runBlocking {
        val original = captured()
        val archived = CanonicalCatalog(original.catalog.location, original.catalog.datasetId,
            CatalogProvenance(CatalogOrigin.LIVE, CatalogProductKind.ARCHIVED_MODEL_FORECAST,
                "provider-model", "archived-run", Instant.parse("2026-03-08T09:00:00.987654321Z")),
            original.catalog.asOf, original.catalog.facts)
        val bytes = RecordedCatalogCodec.encode(CatalogRecording(archived, original.expectedSupport))
        val decoded = (RecordedCatalogCodec.decode(bytes) as RecordingDecodeResult.Success).recording.catalog
        assertEquals(archived, decoded)
        val replay = RecordedCatalogSource(bytes).load(request) as CatalogLoadResult.Success
        assertEquals(CatalogProductKind.ARCHIVED_MODEL_FORECAST, replay.catalog.provenance.productKind)
        assertEquals(CatalogOrigin.LIVE, replay.metadata.replayOriginalOrigin)
        assertEquals(archived.provenance.issuedAt, replay.catalog.provenance.issuedAt)
        assertEquals(123456789, replay.catalog.asOf.nano)
    }

    @Test fun unknownsAndHugeVersionFail() {
        val source = String(fixture(), Charsets.UTF_8)
        fun kind(json: String) = (RecordedCatalogCodec.decode(json.toByteArray()) as RecordingDecodeResult.Failure).kind
        assertEquals(RecordingError.UNSUPPORTED_VERSION,
            kind(source.replace("\"schemaVersion\":1", "\"schemaVersion\":4294967297")))
        assertEquals(RecordingError.INVALID_VALUE,
            kind(source.replace("\"datasetId\":\"wx11-full-sequence-v1\"", "\"datasetId\":\"wx11-full-sequence-v1\",\"futureField\":1")))
        assertEquals(RecordingError.INVALID_VALUE,
            kind(source.replace("\"origin\":\"SIMULATED\"", "\"origin\":\"FUTURE\"")))
        assertEquals(RecordingError.MALFORMED, kind(source + "{}"))
    }
}
