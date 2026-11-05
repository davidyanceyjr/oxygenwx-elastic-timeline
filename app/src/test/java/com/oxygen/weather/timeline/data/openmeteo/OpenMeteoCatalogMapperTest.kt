package com.oxygen.weather.timeline.data.openmeteo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.oxygen.weather.timeline.data.catalog.*
import java.io.File
import java.security.MessageDigest
import java.time.*
import org.junit.Assert.*
import org.junit.Test

class OpenMeteoCatalogMapperTest {
    private val chicago = CatalogLocation(LocationId("reference-chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))
    private val mapper = ObjectMapper()

    @Test fun `captured public Chicago response maps to a 44-date partial canonical catalog`() {
        val request = request(Instant.parse("2026-10-28T14:57:55.714828Z"))
        val bytes = javaClass.getResourceAsStream("/openmeteo/provider-response-public-chicago.json")!!.use { it.readBytes() }
        val mapped = OpenMeteoCatalogMapper.decode(bytes, request).success()
        assertEquals(1_056, mapped.catalog.facts.size)
        assertEquals(44, mapped.expectedSupport.size)
        assertEquals(CatalogProductKind.MIXED, mapped.catalog.provenance.productKind)
        assertEquals(listOf(CatalogProductKind.ARCHIVED_MODEL_FORECAST, CatalogProductKind.FORECAST), mapped.catalog.productSegments.map { it.productKind })
        assertEquals(44, mapped.catalog.dailySummaries.size)
        assertEquals(DailyValueOrigin.PROVIDER_SUPPLIED, mapped.catalog.dailySummaries.first().origin)
        val window = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport, dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window
        val fall = window.days.single { it.date == LocalDate.parse("2026-11-01") }
        val finalDate = window.days.last()
        assertEquals(25, ((mapped.expectedSupport.getValue(fall.date)).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size)
        assertEquals(25, ((mapped.expectedSupport.getValue(fall.date)).getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals).intervals.size)
        assertEquals(CoverageStatus.COMPLETE, fall.fieldCoverage.getValue(CatalogField.TEMPERATURE_C))
        assertEquals(CoverageStatus.COMPLETE, fall.fieldCoverage.getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS))
        assertEquals(25, fall.facts.count { it.validTime.atZone(chicago.timeZone).toLocalDate() == fall.date })
        assertEquals(24, (mapped.expectedSupport.getValue(finalDate.date).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size)
        assertEquals(CoverageStatus.PARTIAL, finalDate.fieldCoverage.getValue(CatalogField.TEMPERATURE_C))
        assertEquals(CoverageStatus.PARTIAL, finalDate.fieldCoverage.getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS))
        val first = mapped.catalog.facts.first()
        val raw = mapper.readTree(bytes).path("hourly")
        assertEquals(raw.path("visibility")[0].doubleValue() / 1000.0, first.visibilityKm!!, 0.000001)
        assertEquals(raw.path("snow_depth")[0].doubleValue() * 100.0, first.snowDepthCentimeters!!, 0.000001)
        val rawDirections = mapper.readTree(bytes).path("hourly").path("wind_direction_10m")
        assertEquals(4, (0 until rawDirections.size()).count { rawDirections[it].doubleValue() == 360.0 })
        assertTrue(mapped.catalog.facts.none { it.windDirectionDegrees == 360.0 })
        val sourceReview = javaClass.getResourceAsStream("/openmeteo/provider-response-public-chicago-manifest.json")!!.use { it.readBytes().decodeToString() }
        assertTrue(sourceReview.contains("\"sha256\""))
    }

    @Test fun `buffer date facts and daily summaries are clipped at the 44-date boundary`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val targetEnd = date.plusDays(14).atStartOfDay(chicago.timeZone).toInstant()
        val extraForecastHour = targetEnd.plusSeconds(3600)
        val response = fixture(
            request,
            -5 * 3600,
            listOf(date.atTime(12, 0).atZone(chicago.timeZone).toInstant(), targetEnd, extraForecastHour),
            listOf(date, date.plusDays(14)),
        )

        val mapped = mapBytes(response, request).success()
        assertEquals(44, mapped.expectedSupport.size)
        assertEquals(44, mapped.dailyExpectedSupport.size)
        assertEquals(mapped.expectedSupport.keys, mapped.dailyExpectedSupport.keys)
        assertEquals(2, mapped.catalog.facts.size)
        assertEquals(targetEnd, mapped.catalog.facts.last().validTime)
        assertNull(mapped.catalog.facts.last().temperatureC)
        assertEquals(targetEnd.minusSeconds(3600), mapped.catalog.facts.last().rainAmountMillimeters!!.interval.start)
        assertEquals(targetEnd, mapped.catalog.facts.last().rainAmountMillimeters!!.interval.end)
        assertFalse(mapped.catalog.facts.any { it.validTime == extraForecastHour })
        assertEquals(listOf(date), mapped.catalog.dailySummaries.map { it.date })
        assertEquals(listOf(date), mapped.catalog.productSegments.map { it.startDateInclusive })
        assertEquals(date.plusDays(1), mapped.catalog.productSegments.single().endDateExclusive)

        val lastTargetDate = date.plusDays(13)
        assertEquals(24, (mapped.expectedSupport.getValue(lastTargetDate).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size)
        assertEquals(24, (mapped.expectedSupport.getValue(lastTargetDate).getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals).intervals.size)
        assertFalse(mapped.expectedSupport.containsKey(date.plusDays(14)))
    }

    @Test fun `captured provider catalog preserves missing lunar values in version 5`() {
        val request = request(Instant.parse("2026-10-28T14:57:55.714828Z"))
        val input = javaClass.getResourceAsStream("/openmeteo/provider-response-public-chicago.json")!!.use { it.readBytes() }
        val mapped = OpenMeteoCatalogMapper.decode(input, request).success()
        val original = CatalogRecording(mapped.catalog, mapped.expectedSupport, mapped.dailyExpectedSupport)
        val encoded = RecordedCatalogCodec.encode(original)
        assertEquals(5, mapper.readTree(encoded).path("schemaVersion").intValue())
        assertNull(mapped.catalog.dailySummaries.first().sunriseInstant)
        assertNull(mapped.catalog.dailySummaries.first().sunsetInstant)
        assertNull(mapped.catalog.dailySummaries.first().moonPhase)
        assertNull(mapped.catalog.dailySummaries.first().moonriseInstant)
        val decoded = (RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success).recording
        assertEquals(original.catalog, decoded.catalog)
        assertEquals(original.expectedSupport.keys, decoded.expectedSupport.keys)
        assertEquals(original.dailyExpectedSupport, decoded.dailyExpectedSupport)
        assertArrayEquals(encoded, RecordedCatalogCodec.encode(decoded))
        val replay = kotlinx.coroutines.runBlocking {
            CatalogSourceRegistry(listOf(RecordedCatalogSource(encoded))).load(CatalogOrigin.REPLAY, request)
        } as CatalogLoadResult.Partial
        assertEquals(CatalogOrigin.REPLAY, replay.catalog.provenance.origin)
        assertEquals(CatalogProductKind.MIXED, replay.catalog.provenance.productKind)
        assertEquals(mapped.catalog.productSegments, replay.catalog.productSegments)
        assertEquals(mapped.catalog.dailySummaries, replay.catalog.dailySummaries)
        (System.getProperty("WX13_RECORDING_OUTPUT") ?: System.getenv("WX13_RECORDING_OUTPUT"))?.let {
            File(it).apply { parentFile?.mkdirs(); writeBytes(encoded) }
        }
        val hash = MessageDigest.getInstance("SHA-256").digest(encoded).joinToString("") { "%02x".format(it) }
        assertTrue(hash.matches(Regex("[a-f0-9]{64}")))
    }

    @Test fun `solar events map as absolute instants, remain nullable, and round trip through version 5`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val response = fixture(request, -5 * 3600, listOf(date.atTime(12, 0).atZone(chicago.timeZone).toInstant()), listOf(date))
        val mapped = mapBytes(response, request).success()
        val summary = mapped.catalog.dailySummaries.single()
        val expectedSunrise = date.atTime(6, 30).atZone(chicago.timeZone).toInstant()
        val expectedSunset = date.atTime(18, 30).atZone(chicago.timeZone).toInstant()
        assertEquals(expectedSunrise, summary.sunriseInstant)
        assertEquals(expectedSunset, summary.sunsetInstant)
        assertTrue(summary.has(DailyCatalogField.SUNRISE))
        assertTrue(summary.has(DailyCatalogField.SUNSET))

        val encoded = RecordedCatalogCodec.encode(CatalogRecording(
            mapped.catalog, mapped.expectedSupport, mapped.dailyExpectedSupport,
        ))
        assertEquals(5, mapper.readTree(encoded).path("schemaVersion").intValue())
        val decoded = (RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success).recording
        assertEquals(mapped.catalog.dailySummaries, decoded.catalog.dailySummaries)
        assertArrayEquals(encoded, RecordedCatalogCodec.encode(decoded))

        val daily = response.path("daily")
        (daily.path("sunrise") as ArrayNode).set(0, mapper.nodeFactory.nullNode())
        (daily.path("sunset") as ArrayNode).set(0, mapper.nodeFactory.nullNode())
        val missing = mapBytes(response, request).success().catalog.dailySummaries.single()
        assertNull(missing.sunriseInstant)
        assertNull(missing.sunsetInstant)
        assertFalse(missing.has(DailyCatalogField.SUNRISE))
        assertFalse(missing.has(DailyCatalogField.SUNSET))
    }

    @Test fun `lunar events and daily phase map independently and legacy versions 2 to 4 do not backfill typed events`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val response = fixture(request, -5 * 3600, listOf(date.atTime(12, 0).atZone(chicago.timeZone).toInstant()), listOf(date))
        val summary = mapBytes(response, request).success().catalog.dailySummaries.single()
        assertEquals(date.atTime(20, 10).atZone(chicago.timeZone).toInstant(), summary.moonriseInstant)
        assertEquals(date.atTime(5, 20).atZone(chicago.timeZone).toInstant(), summary.moonsetInstant)
        assertEquals(.125, summary.moonPhase!!, 0.0)
        assertTrue(summary.has(DailyCatalogField.MOONRISE))
        assertTrue(summary.has(DailyCatalogField.MOONSET))
        assertTrue(summary.has(DailyCatalogField.MOON_PHASE))

        val mapped = mapBytes(response, request).success()
        val encoded = RecordedCatalogCodec.encode(CatalogRecording(mapped.catalog, mapped.expectedSupport, mapped.dailyExpectedSupport))
        assertEquals(5, mapper.readTree(encoded).path("schemaVersion").intValue())
        val decoded = (RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success).recording
        assertEquals(mapped.catalog, decoded.catalog)
        assertEquals(mapped.dailyExpectedSupport, decoded.dailyExpectedSupport)
        assertArrayEquals(encoded, RecordedCatalogCodec.encode(decoded))

        val version5FactsWithoutEvents = mapped.catalog.facts.map(::withoutTypedEventMetadata)
        for (version in 2..4) {
            val legacyBytes = legacyPayload(encoded, version)
            assertEquals(version, mapper.readTree(legacyBytes).path("schemaVersion").intValue())
            val decoded = (RecordedCatalogCodec.decode(legacyBytes) as RecordingDecodeResult.Success).recording
            assertEquals(version5FactsWithoutEvents, decoded.catalog.facts)
            assertTrue(decoded.catalog.facts.all { it.events.isEmpty() && it.primaryEventIndex == null && it.sourceWeatherCode == null })
            val expectedSunrise = if (version >= 3) summary.sunriseInstant else null
            val expectedMoonPhase = if (version >= 4) summary.moonPhase else null
            assertEquals(expectedSunrise, decoded.catalog.dailySummaries.single().sunriseInstant)
            assertEquals(expectedMoonPhase, decoded.catalog.dailySummaries.single().moonPhase)
            val replay = kotlinx.coroutines.runBlocking {
                CatalogSourceRegistry(listOf(RecordedCatalogSource(legacyBytes))).load(CatalogOrigin.REPLAY, request)
            }
            assertTrue("Version $version did not replay: $replay", replay is CatalogLoadResult.Success || replay is CatalogLoadResult.Partial)
            val replayFacts = when (replay) {
                is CatalogLoadResult.Success -> replay.catalog.facts
                is CatalogLoadResult.Partial -> replay.catalog.facts
                else -> emptyList()
            }
            assertEquals(version5FactsWithoutEvents, replayFacts)
        }

        val daily = response.path("daily")
        (daily.path("moonrise") as ArrayNode).set(0, mapper.nodeFactory.nullNode())
        (daily.path("moon_phase") as ArrayNode).set(0, mapper.nodeFactory.nullNode())
        val partial = mapBytes(response, request).success().catalog.dailySummaries.single()
        assertNull(partial.moonriseInstant)
        assertNotNull(partial.moonsetInstant)
        assertNull(partial.moonPhase)
    }

    @Test fun `lunar event retains provider row ownership when its absolute instant crosses midnight`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val response = fixture(request, -5 * 3600,
            listOf(date.atTime(12, 0).atZone(chicago.timeZone).toInstant()), listOf(date))
        (response.path("daily").path("moonrise") as ArrayNode).set(
            0, mapper.nodeFactory.numberNode(date.plusDays(1).atTime(0, 35).atZone(chicago.timeZone).toEpochSecond()),
        )
        val mapped = mapBytes(response, request).success()
        val summary = mapped.catalog.dailySummaries.single()
        val event = date.plusDays(1).atTime(0, 35).atZone(chicago.timeZone).toInstant()
        assertEquals(date, summary.date)
        assertEquals(event, summary.moonriseInstant)
        assertFalse(event < summary.support.end)
        val window = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport,
            dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window
        assertEquals(CoverageStatus.COMPLETE,
            window.days.single { it.date == date }.dailyCoverage.getValue(DailyCatalogField.MOONRISE))
    }

    @Test fun `buffered live capture can emit a clipped v2 candidate and coverage report`() {
        val inputPath = System.getProperty("WX13R_RESPONSE_INPUT") ?: System.getenv("WX13R_RESPONSE_INPUT")
        val outputPath = System.getProperty("WX13R_RECORDING_OUTPUT") ?: System.getenv("WX13R_RECORDING_OUTPUT")
        val asOf = System.getProperty("WX13R_AS_OF_UTC") ?: System.getenv("WX13R_AS_OF_UTC")
        val reportPath = System.getProperty("WX13R_COVERAGE_OUTPUT") ?: System.getenv("WX13R_COVERAGE_OUTPUT")
        org.junit.Assume.assumeTrue("requires an explicitly supplied live capture", inputPath != null && outputPath != null && asOf != null && reportPath != null)

        val request = request(Instant.parse(asOf!!))
        val mapped = OpenMeteoCatalogMapper.decode(File(inputPath!!).readBytes(), request).success()
        val targetDates = (-30L..13L).map(request.asOf.atZone(chicago.timeZone).toLocalDate()::plusDays).toSet()
        val end = request.asOf.atZone(chicago.timeZone).toLocalDate().plusDays(14).atStartOfDay(chicago.timeZone).toInstant()
        assertEquals(targetDates, mapped.expectedSupport.keys)
        assertTrue(mapped.catalog.facts.all { it.validTime >= targetDates.min().atStartOfDay(chicago.timeZone).toInstant() && it.validTime <= end })
        assertTrue(mapped.catalog.dailySummaries.all { it.date in targetDates })
        assertTrue(mapped.catalog.productSegments.all { it.startDateInclusive in targetDates && it.endDateExclusive <= targetDates.max().plusDays(1) })

        val original = CatalogRecording(mapped.catalog, mapped.expectedSupport, mapped.dailyExpectedSupport)
        val encoded = RecordedCatalogCodec.encode(original)
        val decoded = (RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success).recording
        assertEquals(original.catalog, decoded.catalog)
        assertEquals(original.expectedSupport.keys, decoded.expectedSupport.keys)
        assertEquals(original.dailyExpectedSupport, decoded.dailyExpectedSupport)
        assertArrayEquals(encoded, RecordedCatalogCodec.encode(decoded))
        File(outputPath!!).apply { parentFile?.mkdirs(); writeBytes(encoded) }

        val window = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport, dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window
        val coverage = buildString {
            appendLine("# Buffered Chicago capture coverage")
            appendLine("Target dates: ${targetDates.min()} through ${targetDates.max()} (${targetDates.size} local dates)")
            appendLine("Canonical facts: ${mapped.catalog.facts.size}; daily summaries: ${mapped.catalog.dailySummaries.size}")
            appendLine("Expected support entries: hourly=${mapped.expectedSupport.values.sumOf { it.size }}, daily=${mapped.dailyExpectedSupport.values.sumOf { it.size }}")
            appendLine("Expected temporal support per hourly field:")
            mapped.expectedSupport.values.flatMap { it.entries }
                .groupBy({ it.key }, { it.value }).toSortedMap(compareBy { it.name })
                .forEach { (field, supports) ->
                    val count = supports.sumOf { support -> when (support) {
                        is ExpectedFieldSupport.Points -> support.instants.size
                        is ExpectedFieldSupport.Intervals -> support.intervals.size
                    } }
                    appendLine("- $field: $count")
                }
            val fallDate = LocalDate.parse("2026-11-01")
            val finalDate = targetDates.max()
            appendLine("DST fall-back date $fallDate: points=${(mapped.expectedSupport.getValue(fallDate).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size}; intervals=${(mapped.expectedSupport.getValue(fallDate).getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals).intervals.size}")
            appendLine("Final target date $finalDate: points=${(mapped.expectedSupport.getValue(finalDate).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size}; intervals=${(mapped.expectedSupport.getValue(finalDate).getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals).intervals.size}")
            appendLine("Hourly point/interval coverage by field:")
            window.days.flatMap { day -> day.fieldCoverage.map { (field, status) -> field to status } }
                .groupBy({ it.first }, { it.second }).toSortedMap(compareBy { it.name })
                .forEach { (field, statuses) -> appendLine("- $field: complete=${statuses.count { it == CoverageStatus.COMPLETE }}, partial=${statuses.count { it == CoverageStatus.PARTIAL }}, missing=${statuses.count { it == CoverageStatus.MISSING }}") }
            appendLine("Daily coverage by field:")
            window.days.flatMap { day -> day.dailyCoverage.map { (field, status) -> field to status } }
                .groupBy({ it.first }, { it.second }).toSortedMap(compareBy { it.name })
                .forEach { (field, statuses) -> appendLine("- $field: complete=${statuses.count { it == CoverageStatus.COMPLETE }}, partial=${statuses.count { it == CoverageStatus.PARTIAL }}, missing=${statuses.count { it == CoverageStatus.MISSING }}") }
            appendLine("Incomplete date/field entries:")
            window.days.forEach { day ->
                day.fieldCoverage.filterValues { it != CoverageStatus.COMPLETE }.forEach { (field, status) -> appendLine("- ${day.date} $field $status") }
                day.dailyCoverage.filterValues { it != CoverageStatus.COMPLETE }.forEach { (field, status) -> appendLine("- ${day.date} daily/$field $status") }
            }
        }
        File(reportPath!!).apply { parentFile?.mkdirs(); writeText(coverage) }
    }

    @Test fun `spring forward and fall back use 23 and 25 hour support with fixed-offset daily identity`() {
        val zone = chicago.timeZone
        listOf(
            LocalDate.parse("2026-03-08") to -6 * 3600,
            LocalDate.parse("2026-11-01") to -5 * 3600,
        ).forEach { (date, offset) ->
            val request = request(date.atTime(12, 0).atZone(zone).toInstant())
            val start = date.atStartOfDay(zone).toInstant()
            val end = date.plusDays(1).atStartOfDay(zone).toInstant()
            val response = fixture(request, offset, (0..Duration.between(start, end).toHours().toInt()).map { start.plusSeconds(it * 3600L) }, listOf(date))
            val mapped = (mapBytes(response, request) as OpenMeteoMappingResult.Success).value
            val expectedHours = Duration.between(start, end).toHours().toInt()
            val pointSupport = mapped.expectedSupport.getValue(date).getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points
            val intervalSupport = mapped.expectedSupport.getValue(date).getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals
            assertEquals(expectedHours, pointSupport.instants.size)
            assertEquals(expectedHours, intervalSupport.intervals.size)
            assertTrue(intervalSupport.intervals.all { it.start >= start && it.end <= end })
            val day = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport, dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window.days.single { it.date == date }
            assertEquals(CoverageStatus.COMPLETE, day.fieldCoverage.getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS))
            assertEquals(start, day.dailySummary!!.support.start)
            assertEquals(end, day.dailySummary.support.end)
            assertEquals(expectedHours, day.facts.count { it.validTime.atZone(zone).toLocalDate() == date })
        }
        val fallDate = LocalDate.parse("2026-11-02")
        val fallRequest = request(LocalDate.parse("2026-11-01").atTime(12, 0).atZone(zone).toInstant())
        val fallJson = fixture(fallRequest, -5 * 3600, listOf(LocalDate.parse("2026-11-01").atStartOfDay(zone).toInstant()), listOf(LocalDate.parse("2026-11-01"), fallDate))
        val dailyTimes = fallJson.path("daily").path("time")
        val nominal = Instant.ofEpochSecond(dailyTimes[1].longValue()).plusSeconds(-5 * 3600L).atZone(ZoneOffset.UTC).toLocalDate()
        val actualIana = Instant.ofEpochSecond(dailyTimes[1].longValue()).atZone(zone).toLocalDate()
        assertEquals(fallDate, nominal)
        assertEquals(fallDate.minusDays(1), actualIana)
    }

    @Test fun `sparse omitted field remains expected and lowers coverage while zero remains present`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val start = date.atStartOfDay(chicago.timeZone).toInstant()
        val json = fixture(request, -5 * 3600, listOf(start, start.plusSeconds(7200)), listOf(date), omitHourly = setOf("temperature_2m", "snow_depth"), omitDaily = setOf("rain_sum"))
        val mapped = (mapBytes(json, request) as OpenMeteoMappingResult.Success).value
        val support = mapped.expectedSupport.getValue(date)
        assertEquals(24, (support.getValue(CatalogField.TEMPERATURE_C) as ExpectedFieldSupport.Points).instants.size)
        assertEquals(24, (support.getValue(CatalogField.SNOW_DEPTH_CENTIMETERS) as ExpectedFieldSupport.Points).instants.size)
        val window = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport, dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window
        val day = window.days.single { it.date == date }
        assertEquals(CoverageStatus.MISSING, day.fieldCoverage.getValue(CatalogField.TEMPERATURE_C))
        assertEquals(CoverageStatus.MISSING, day.fieldCoverage.getValue(CatalogField.SNOW_DEPTH_CENTIMETERS))
        assertEquals(0.0, day.facts.first().rainAmountMillimeters!!.valueMillimeters, 0.0)
        assertEquals(CoverageStatus.MISSING, day.dailyCoverage.getValue(DailyCatalogField.RAIN_AMOUNT))
        assertEquals(24, (support.getValue(CatalogField.RAIN_AMOUNT_MILLIMETERS) as ExpectedFieldSupport.Intervals).intervals.size)
    }

    @Test fun `missing daily date remains in expected support and lowers daily coverage`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val time = date.atTime(12, 0).atZone(chicago.timeZone).toInstant()
        val response = fixture(request, -5 * 3600, listOf(time), emptyList())
        val mapped = (mapBytes(response, request) as OpenMeteoMappingResult.Success).value
        assertEquals(44, mapped.dailyExpectedSupport.size)
        assertFalse(mapped.catalog.dailySummaries.any { it.date == date })
        val day = (CatalogWindow.create(mapped.catalog, mapped.expectedSupport, dailyExpectedSupport = mapped.dailyExpectedSupport) as CatalogWindowResult.Success).window.days.single { it.date == date }
        assertEquals(CoverageStatus.MISSING, day.dailyCoverage.getValue(DailyCatalogField.TEMPERATURE_MAX_C))
        assertEquals(CoverageStatus.MISSING, day.dailyCoverage.getValue(DailyCatalogField.CONDITIONS))
        assertEquals(CoverageStatus.MISSING, day.dailyCoverage.getValue(DailyCatalogField.SUNRISE))
        assertEquals(CoverageStatus.MISSING, day.dailyCoverage.getValue(DailyCatalogField.SUNSET))
    }

    @Test fun `returned null only row receives a product segment and absent date has none`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val first = date.atTime(12, 0).atZone(chicago.timeZone).toInstant()
        val nullOnly = first.plusSeconds(86400)
        val response = fixture(request, -5 * 3600, listOf(first, nullOnly), listOf(date))
        val arrays = response.path("hourly")
        OpenMeteoTransport.HOURLY.filter { it != "time" }.forEach { name ->
            (arrays.path(name) as ArrayNode).set(1, mapper.nodeFactory.nullNode())
        }
        val mapped = (mapBytes(response, request) as OpenMeteoMappingResult.Success).value
        assertEquals(2, mapped.catalog.facts.size)
        assertTrue(mapped.catalog.facts.last().conditions.isEmpty())
        assertEquals(date, mapped.catalog.productSegments.single().startDateInclusive)
        assertEquals(date.plusDays(2), mapped.catalog.productSegments.single().endDateExclusive)
        assertFalse(mapped.catalog.productSegments.any { date.plusDays(2) >= it.startDateInclusive && date.plusDays(2) < it.endDateExclusive })
    }

    @Test fun `out of order rows sort and identical duplicates deduplicate while conflicting duplicates fail`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val start = date.atStartOfDay(chicago.timeZone).toInstant()
        val reordered = fixture(request, -5 * 3600, listOf(start.plusSeconds(7200), start, start.plusSeconds(3600)), listOf(date))
        assertTrue(mapBytes(reordered, request) is OpenMeteoMappingResult.Success)
        val duplicate = fixture(request, -5 * 3600, listOf(start, start), listOf(date))
        assertTrue(mapBytes(duplicate, request) is OpenMeteoMappingResult.Success)
        (duplicate.path("hourly").path("temperature_2m") as ArrayNode).set(1, mapper.nodeFactory.numberNode(13.0))
        assertTrue(mapBytes(duplicate, request) is OpenMeteoMappingResult.Invalid)
    }

    @Test fun `unknown WMO code preserves independent measures and invalid code and length reject`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val time = date.atTime(12, 0).atZone(chicago.timeZone).toInstant()
        val unknown = fixture(request, -5 * 3600, listOf(time), listOf(date))
        (unknown.path("hourly").path("weather_code") as ArrayNode).set(0, mapper.nodeFactory.numberNode(98.0))
        val mapped = (mapBytes(unknown, request) as OpenMeteoMappingResult.Success).value
        assertTrue(mapped.catalog.facts.single().conditions.isEmpty())
        assertNotNull(mapped.catalog.facts.single().temperatureC)
        (unknown.path("hourly").path("weather_code") as ArrayNode).set(0, mapper.nodeFactory.numberNode(101.0))
        assertTrue(mapBytes(unknown, request) is OpenMeteoMappingResult.Invalid)
        val malformed = fixture(request, -5 * 3600, listOf(time), listOf(date))
        (malformed.path("hourly").path("temperature_2m") as ArrayNode).add(3)
        assertTrue(mapBytes(malformed, request) is OpenMeteoMappingResult.Invalid)
        val wrongUnit = fixture(request, -5 * 3600, listOf(time), listOf(date))
        (wrongUnit.path("hourly_units") as ObjectNode).put("temperature_2m", "°F")
        assertTrue(mapBytes(wrongUnit, request) is OpenMeteoMappingResult.Invalid)
    }

    @Test fun `rain and ice WMO codes map exactly and survive version 5 replay`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val codes = listOf(
            51 to (WeatherEventId.DRIZZLE to "Light drizzle"),
            53 to (WeatherEventId.DRIZZLE to "Moderate drizzle"),
            55 to (WeatherEventId.DRIZZLE to "Dense drizzle"),
            56 to (WeatherEventId.FREEZING_DRIZZLE to "Light freezing drizzle"),
            57 to (WeatherEventId.FREEZING_DRIZZLE to "Dense freezing drizzle"),
            61 to (WeatherEventId.RAIN to "Slight rain"),
            63 to (WeatherEventId.RAIN to "Moderate rain"),
            65 to (WeatherEventId.HEAVY_RAIN to "Heavy rain"),
            66 to (WeatherEventId.FREEZING_RAIN to "Light freezing rain"),
            67 to (WeatherEventId.FREEZING_RAIN to "Heavy freezing rain"),
            80 to (WeatherEventId.RAIN_SHOWERS to "Slight rain showers"),
            81 to (WeatherEventId.RAIN_SHOWERS to "Moderate rain showers"),
            82 to (WeatherEventId.RAIN_SHOWERS to "Violent rain showers"),
        )
        val times = codes.indices.map { date.atTime(0, 0).atZone(chicago.timeZone).toInstant().plusSeconds(it * 3600L) }
        val response = fixture(request, -5 * 3600, times, listOf(date))
        val weatherCodes = response.path("hourly").path("weather_code") as ArrayNode
        codes.forEachIndexed { index, (code, _) -> weatherCodes.set(index, mapper.nodeFactory.numberNode(code.toDouble())) }

        val mapped = mapBytes(response, request).success()
        val events = mapped.catalog.facts.flatMap { fact -> fact.events.map { fact.validTime to it } }
        assertEquals(codes.size, events.size)
        codes.forEachIndexed { index, (code, expected) ->
            val event = events[index].second
            assertEquals(expected.first, event.id)
            assertEquals(code, event.sourceCode)
            assertEquals(expected.second, event.documentedCodeDescription)
            assertEquals("open-meteo:forecast-api-weather-code-table", event.descriptionSource)
            assertEquals("open-meteo-forecast-api", event.provider)
            assertEquals(code, mapped.catalog.facts[index].sourceWeatherCode)
        }

        val recording = CatalogRecording(mapped.catalog, mapped.expectedSupport, mapped.dailyExpectedSupport)
        val encoded = RecordedCatalogCodec.encode(recording)
        assertEquals(5, mapper.readTree(encoded).path("schemaVersion").intValue())
        val decoded = (RecordedCatalogCodec.decode(encoded) as RecordingDecodeResult.Success).recording
        assertEquals(recording.catalog, decoded.catalog)
        assertArrayEquals(encoded, RecordedCatalogCodec.encode(decoded))
        val replay = kotlinx.coroutines.runBlocking {
            CatalogSourceRegistry(listOf(RecordedCatalogSource(encoded))).load(CatalogOrigin.REPLAY, request)
        }
        assertTrue(replay is CatalogLoadResult.Success || replay is CatalogLoadResult.Partial)
        val replayFacts = when (replay) {
            is CatalogLoadResult.Success -> replay.catalog.facts
            is CatalogLoadResult.Partial -> replay.catalog.facts
            else -> emptyList()
        }
        assertEquals(mapped.catalog.facts.map { it.events }, replayFacts.map { it.events })
    }

    @Test fun `unsupported intermediate rain and ice codes do not create typed events`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val unsupportedCodes = listOf(52, 54, 58, 59, 60, 62, 64, 68, 69, 78, 79, 83, 84)
        val times = unsupportedCodes.indices.map { date.atTime(0, 0).atZone(chicago.timeZone).toInstant().plusSeconds(it * 3600L) }
        val response = fixture(request, -5 * 3600, times, listOf(date))
        val weatherCodes = response.path("hourly").path("weather_code") as ArrayNode
        unsupportedCodes.forEachIndexed { index, code -> weatherCodes.set(index, mapper.nodeFactory.numberNode(code.toDouble())) }

        val mapped = mapBytes(response, request).success()
        assertEquals(unsupportedCodes, mapped.catalog.facts.map { it.sourceWeatherCode })
        assertTrue(mapped.catalog.facts.all { it.events.isEmpty() })
    }

    @Test fun `provider north bearing 360 normalizes to canonical zero`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val response = fixture(request, -5 * 3600, listOf(date.atTime(12, 0).atZone(chicago.timeZone).toInstant()), listOf(date))
        (response.path("hourly").path("wind_direction_10m") as ArrayNode).set(0, mapper.nodeFactory.numberNode(360.0))
        val mapped = (mapBytes(response, request) as OpenMeteoMappingResult.Success).value
        assertEquals(0.0, mapped.catalog.facts.single().windDirectionDegrees!!, 0.0)
    }

    @Test fun `asOf anchor mismatch and all null hourly values return invalid and no data`() {
        val date = LocalDate.parse("2026-07-10")
        val request = request(date.atTime(12, 0).atZone(chicago.timeZone).toInstant())
        val time = date.atTime(12, 0).atZone(chicago.timeZone).toInstant()
        val shifted = fixture(request, -5 * 3600, listOf(time.plusSeconds(86400)), listOf(date.plusDays(1)))
        assertTrue(mapBytes(shifted, request) is OpenMeteoMappingResult.Invalid)
        val empty = fixture(request, -5 * 3600, listOf(time), listOf(date))
        OpenMeteoTransport.HOURLY.filter { it != "time" }.forEach { name -> (empty.path("hourly").path(name) as ArrayNode).set(0, mapper.nodeFactory.nullNode()) }
        assertEquals(OpenMeteoMappingResult.NoData, mapBytes(empty, request))
    }

    private fun legacyPayload(version5Bytes: ByteArray, version: Int): ByteArray {
        require(version in 2..4)
        val root = mapper.readTree(version5Bytes) as ObjectNode
        root.put("schemaVersion", version)
        root.remove("rawCapture")
        (root.path("facts") as ArrayNode).forEach { fact ->
            (fact as ObjectNode).remove(listOf("sourceWeatherCode", "events", "primaryEventIndex"))
        }
        val removedDailyFields = when (version) {
            2 -> listOf("sunriseInstant", "sunsetInstant", "moonriseInstant", "moonsetInstant", "moonPhase")
            3 -> listOf("moonriseInstant", "moonsetInstant", "moonPhase")
            else -> emptyList()
        }
        (root.path("dailySummaries") as ArrayNode).forEach { summary ->
            (summary as ObjectNode).remove(removedDailyFields)
        }
        val unsupportedDailyFields = when (version) {
            2 -> setOf(DailyCatalogField.SUNRISE, DailyCatalogField.SUNSET, DailyCatalogField.MOONRISE,
                DailyCatalogField.MOONSET, DailyCatalogField.MOON_PHASE)
            3 -> setOf(DailyCatalogField.MOONRISE, DailyCatalogField.MOONSET, DailyCatalogField.MOON_PHASE)
            else -> emptySet()
        }
        val support = root.path("dailyExpectedSupport") as ArrayNode
        val retainedSupport = mapper.createArrayNode()
        support.filterNot { it.path("field").asText() in unsupportedDailyFields.map(DailyCatalogField::name) }
            .forEach(retainedSupport::add)
        root.set<ArrayNode>("dailyExpectedSupport", retainedSupport)
        return mapper.writeValueAsBytes(root)
    }

    private fun withoutTypedEventMetadata(fact: CanonicalWeatherFacts) = CanonicalWeatherFacts(
        validTime = fact.validTime,
        conditions = fact.conditions,
        phenomena = fact.phenomena,
        temperatureC = fact.temperatureC,
        precipitationProbabilityPercent = fact.precipitationProbabilityPercent,
        thunderstormProbabilityPercent = fact.thunderstormProbabilityPercent,
        relativeHumidityPercent = fact.relativeHumidityPercent,
        cloudCoverPercent = fact.cloudCoverPercent,
        windSpeedKph = fact.windSpeedKph,
        windGustKph = fact.windGustKph,
        windDirectionDegrees = fact.windDirectionDegrees,
        rainAmountMillimeters = fact.rainAmountMillimeters,
        totalPrecipitationMillimeters = fact.totalPrecipitationMillimeters,
        precipitationRate = fact.precipitationRate,
        snowfallCentimeters = fact.snowfallCentimeters,
        snowDepthCentimeters = fact.snowDepthCentimeters,
        surfacePressureHpa = fact.surfacePressureHpa,
        meanSeaLevelPressureHpa = fact.meanSeaLevelPressureHpa,
        visibilityKm = fact.visibilityKm,
    )

    private fun fixture(
        request: CatalogLoadRequest,
        offsetSeconds: Int,
        hourlyTimes: List<Instant>,
        dailyDates: List<LocalDate>,
        omitHourly: Set<String> = emptySet(),
        omitDaily: Set<String> = emptySet(),
    ) = mapper.createObjectNode().apply {
        put("timezone", request.location.timeZone.id)
        put("utc_offset_seconds", offsetSeconds)
        set<JsonNode>("hourly_units", mapper.createObjectNode().apply {
            OpenMeteoTransport.HOURLY.forEach { put(it, hourUnit(it)) }
        })
        set<JsonNode>("daily_units", mapper.createObjectNode().apply {
            OpenMeteoTransport.DAILY.forEach { put(it, dayUnit(it)) }
        })
        set<com.fasterxml.jackson.databind.JsonNode>("hourly", mapper.createObjectNode().apply {
            set<com.fasterxml.jackson.databind.JsonNode>("time", mapper.createArrayNode().also { a -> hourlyTimes.forEach { a.add(it.epochSecond) } })
            OpenMeteoTransport.HOURLY.filter { it != "time" && it !in omitHourly }.forEach { name ->
                set<com.fasterxml.jackson.databind.JsonNode>(name, mapper.createArrayNode().also { a -> hourlyTimes.forEach { a.add(hourValue(name)) } })
            }
        })
        set<com.fasterxml.jackson.databind.JsonNode>("daily", mapper.createObjectNode().apply {
            set<com.fasterxml.jackson.databind.JsonNode>("time", mapper.createArrayNode().also { a -> dailyDates.forEach { a.add(it.atStartOfDay().toEpochSecond(ZoneOffset.ofTotalSeconds(offsetSeconds))) } })
                OpenMeteoTransport.DAILY.filter { it != "time" && it !in omitDaily }.forEach { name ->
                set<com.fasterxml.jackson.databind.JsonNode>(name, mapper.createArrayNode().also { a -> dailyDates.forEach { a.add(dayValue(name, it)) } })
            }
        })
    }

    private fun hourValue(name: String): Double = when (name) {
        "weather_code" -> 0.0
        "wind_direction_10m" -> 90.0
        "pressure_msl", "surface_pressure" -> 1013.0
        "visibility" -> 10_000.0
        "temperature_2m" -> 12.0
        "relative_humidity_2m" -> 60.0
        "precipitation_probability", "cloud_cover" -> 0.0
        else -> 0.0
    }
    private fun dayValue(name: String, date: LocalDate): Double = when (name) {
        "weather_code" -> 0.0
        "temperature_2m_max" -> 20.0
        "temperature_2m_min" -> 10.0
        "precipitation_probability_max" -> 0.0
        "sunrise" -> date.atTime(6, 30).atZone(chicago.timeZone).toEpochSecond().toDouble()
        "sunset" -> date.atTime(18, 30).atZone(chicago.timeZone).toEpochSecond().toDouble()
        "moonrise" -> date.atTime(20, 10).atZone(chicago.timeZone).toEpochSecond().toDouble()
        "moonset" -> date.atTime(5, 20).atZone(chicago.timeZone).toEpochSecond().toDouble()
        "moon_phase" -> .125
        else -> 0.0
    }
    private fun hourUnit(name: String) = when (name) {
        "time" -> "unixtime"
        "temperature_2m" -> "°C"
        "relative_humidity_2m", "precipitation_probability", "cloud_cover" -> "%"
        "precipitation", "rain" -> "mm"
        "snowfall" -> "cm"
        "snow_depth" -> "m"
        "weather_code" -> "wmo code"
        "pressure_msl", "surface_pressure" -> "hPa"
        "visibility" -> "m"
        "wind_speed_10m", "wind_gusts_10m" -> "km/h"
        else -> "°"
    }
    private fun dayUnit(name: String) = when (name) {
        "time" -> "unixtime"
        "sunrise", "sunset", "moonrise", "moonset" -> "unixtime"
        "moon_phase" -> "fraction"
        "weather_code" -> "wmo code"
        "temperature_2m_max", "temperature_2m_min" -> "°C"
        "precipitation_probability_max" -> "%"
        "precipitation_sum", "rain_sum" -> "mm"
        else -> "cm"
    }
    private fun request(asOf: Instant) = CatalogLoadRequest(chicago, DatasetId("open-meteo-test"), asOf)
    private fun mapBytes(root: JsonNode, request: CatalogLoadRequest) =
        OpenMeteoCatalogMapper.decode(mapper.writeValueAsBytes(root), request)

    private fun OpenMeteoMappingResult.success(): OpenMeteoMappedCatalog = when (this) {
        is OpenMeteoMappingResult.Success -> value
        is OpenMeteoMappingResult.Invalid -> throw AssertionError("mapping invalid: $reason")
        OpenMeteoMappingResult.NoData -> throw AssertionError("unexpected no data")
    }
}
