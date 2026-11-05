package com.oxygen.weather.timeline.data.openmeteo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.StreamReadFeature
import com.oxygen.weather.timeline.data.catalog.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.Duration

data class OpenMeteoMappedCatalog(
    val catalog: CanonicalCatalog,
    val expectedSupport: CatalogExpectedSupport,
    val dailyExpectedSupport: DailyExpectedSupport,
)

sealed interface OpenMeteoMappingResult {
    data class Success(val value: OpenMeteoMappedCatalog) : OpenMeteoMappingResult
    data object NoData : OpenMeteoMappingResult
    data class Invalid(val reason: String) : OpenMeteoMappingResult
}

/** Strict, side-effect-free decoder and mapper for the requested Forecast API response. */
object OpenMeteoCatalogMapper {
    private val json = ObjectMapper(JsonFactory.builder().enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build())
    private val pointFields = linkedMapOf(
        "temperature_2m" to CatalogField.TEMPERATURE_C,
        "relative_humidity_2m" to CatalogField.RELATIVE_HUMIDITY_PERCENT,
        "precipitation_probability" to CatalogField.PRECIPITATION_PROBABILITY_PERCENT,
        "weather_code" to CatalogField.CONDITIONS,
        "pressure_msl" to CatalogField.MEAN_SEA_LEVEL_PRESSURE_HPA,
        "surface_pressure" to CatalogField.SURFACE_PRESSURE_HPA,
        "cloud_cover" to CatalogField.CLOUD_COVER_PERCENT,
        "visibility" to CatalogField.VISIBILITY_KM,
        "snow_depth" to CatalogField.SNOW_DEPTH_CENTIMETERS,
        "wind_speed_10m" to CatalogField.WIND_SPEED_KPH,
        "wind_direction_10m" to CatalogField.WIND_DIRECTION_DEGREES,
        "wind_gusts_10m" to CatalogField.WIND_GUST_KPH,
    )
    private val accumulationFields = mapOf(
        "rain" to CatalogField.RAIN_AMOUNT_MILLIMETERS,
        "precipitation" to CatalogField.TOTAL_PRECIPITATION_MILLIMETERS,
        "snowfall" to CatalogField.SNOWFALL_CENTIMETERS,
    )

    fun decode(bytes: ByteArray, request: CatalogLoadRequest): OpenMeteoMappingResult = try {
        map(json.readTree(bytes), request)
    } catch (e: Exception) {
        OpenMeteoMappingResult.Invalid(e.message ?: "Malformed provider response")
    }

    fun map(root: JsonNode, request: CatalogLoadRequest): OpenMeteoMappingResult {
        try {
            require(root.isObject) { "Response must be an object" }
            val zone = request.location.timeZone
            require(root.requiredText("timezone") == zone.id) { "Response timezone differs from requested IANA zone" }
            val offset = root.requiredLong("utc_offset_seconds")
            require(offset in -64_800..64_800) { "Invalid response utc_offset_seconds" }
            val anchor = request.asOf.atZone(zone).toLocalDate()
            val dates = (-30L..13L).map(anchor::plusDays)
            val dateSet = dates.toSet()
            val start = dates.first().atStartOfDay(zone).toInstant()
            val end = anchor.plusDays(14).atStartOfDay(zone).toInstant()
            val providerEnd = anchor.plusDays(15).atStartOfDay(zone).toInstant()

            val hourly = root.required("hourly").also { require(it.isObject) }
            val hourlyTimes = hourly.required("time").longArray().map { Instant.ofEpochSecond(it) }
            require(hourlyTimes.all { it.atZone(zone).let { local -> local.minute == 0 && local.second == 0 && local.nano == 0 } }) {
                "Hourly valid times must lie on local hour boundaries"
            }
            val hourlyValues = readSeries(hourly, root.required("hourly_units"), OpenMeteoTransport.HOURLY, hourlyTimes.size, ::hourlyUnit)
            require(hourlyValues.values.all { it.size == hourlyTimes.size }) { "Hourly variable array length does not match hourly.time" }
            val hRows = hourlyTimes.indices.map { index ->
                hourlyTimes[index] to hourlyValues.filterKeys { it != "time" }.mapValues { (_, values) -> values[index] }
            }
            val hDistinct = dedupeRows(hRows)
            require(hDistinct.keys.all { it >= start && it <= providerEnd }) { "Hourly timestamp outside request window" }
            // The provider request includes D+14 as a buffer, but canonical facts and
            // product segments stop at the D+14 midnight boundary. Keep that boundary
            // row because its preceding-hour accumulation belongs to D+13.
            val targetHourly = hDistinct.filterKeys { it <= end }
            val hDates = targetHourly.keys.map { it.atZone(zone).toLocalDate() }
            require(hDates.all { it in dateSet || it == anchor.plusDays(14) && targetHourly.keys.any { time -> time == end } }) { "Hourly timestamp outside target 44-date window" }

            val daily = root.required("daily").also { require(it.isObject) }
            val dailyTimes = daily.required("time").longArray().map { Instant.ofEpochSecond(it) }
            require(dailyTimes.all { it.plusSeconds(offset).atZone(ZoneOffset.UTC).toLocalTime() == java.time.LocalTime.MIDNIGHT }) {
                "Daily timestamps must represent nominal midnight under response utc_offset_seconds"
            }
            val dailyOffsetDates = dailyTimes.map { it.plusSeconds(offset).atZone(ZoneOffset.UTC).toLocalDate() }
            require(dailyOffsetDates.zipWithNext().all { (a, b) -> b.isAfter(a) }) { "Daily nominal dates must be strictly increasing" }
            require(dailyOffsetDates.all { it in dates.first()..anchor.plusDays(14) }) { "Daily nominal date outside request window" }
            val dailyValues = readSeries(daily, root.required("daily_units"), OpenMeteoTransport.DAILY, dailyTimes.size, ::dailyUnit)
            require(dailyValues.values.all { it.size == dailyTimes.size }) { "Daily variable array length does not match daily.time" }
            val targetDailyRows = dailyOffsetDates.indices.filter { dailyOffsetDates[it] in dateSet }
            val solarByDate = targetDailyRows.associate { index ->
                val date = dailyOffsetDates[index]
                date to (dailyValues["sunrise"]?.get(index)?.let { it.toEventInstant("sunrise", date) } to
                    dailyValues["sunset"]?.get(index)?.let { it.toEventInstant("sunset", date) })
            }

            val hasAnchor = hDates.any { it == anchor } || targetDailyRows.any { dailyOffsetDates[it] == anchor }
            require(hasAnchor) { "Response does not contain the request asOf local date" }

            val expected = linkedMapOf<LocalDate, MutableMap<CatalogField, ExpectedFieldSupport>>()
            for (date in dates) {
                val dayStart = date.atStartOfDay(zone).toInstant()
                val dayEnd = date.plusDays(1).atStartOfDay(zone).toInstant()
                (pointFields.values + CatalogField.PHENOMENA).forEach { field ->
                    expected.getOrPut(date) { linkedMapOf() }[field] = ExpectedFieldSupport.Points(
                        generateSequence(dayStart) { it.plusSeconds(3600) }.takeWhile { it < dayEnd }.toSet(),
                    )
                }
                accumulationFields.values.forEach { field ->
                    val intervals = generateSequence(dayStart.plusSeconds(3600)) { it.plusSeconds(3600) }
                        .takeWhile { it <= dayEnd }
                        .map { AccumulationInterval(it.minusSeconds(3600), it) }.toSet()
                    expected.getOrPut(date) { linkedMapOf() }[field] = ExpectedFieldSupport.Intervals(intervals)
                }
            }

            val factsByTime = linkedMapOf<Instant, CanonicalWeatherFacts>()
            val usableDates = mutableSetOf<LocalDate>()
            for ((time, values) in targetHourly.toSortedMap()) {
                val isWindowBoundary = time == end
                if (isWindowBoundary && listOf("rain", "precipitation", "snowfall").all { values[it] == null }) continue
                val localDate = if (isWindowBoundary) anchor.plusDays(13) else time.atZone(zone).toLocalDate()
                val code = values["weather_code"]
                val sourceCode = code?.toWmoInt()
                val mapped = sourceCode?.let(::mapWmo)
                val event = if (isWindowBoundary) null else sourceCode?.let { code ->
                    skyEvent(code, time, solarByDate[localDate]) ?: rainIceEvent(code)
                }
                val interval = AccumulationInterval(time.minusSeconds(3600), time)
                val fact = try {
                    CanonicalWeatherFacts(
                        validTime = time,
                        conditions = if (isWindowBoundary) emptySet() else mapped?.conditions.orEmpty(),
                        phenomena = if (isWindowBoundary) emptySet() else mapped?.phenomena.orEmpty(),
                        temperatureC = if (isWindowBoundary) null else values["temperature_2m"],
                        relativeHumidityPercent = if (isWindowBoundary) null else values["relative_humidity_2m"],
                        precipitationProbabilityPercent = if (isWindowBoundary) null else values["precipitation_probability"],
                        cloudCoverPercent = if (isWindowBoundary) null else values["cloud_cover"],
                        windSpeedKph = if (isWindowBoundary) null else values["wind_speed_10m"],
                        // Circular bearings 360° and 0° identify the same north direction; the
                        // canonical half-open [0, 360) representation uses 0° for either input.
                        windDirectionDegrees = if (isWindowBoundary) null else values["wind_direction_10m"]?.let { if (it == 360.0) 0.0 else it },
                        windGustKph = if (isWindowBoundary) null else values["wind_gusts_10m"],
                        meanSeaLevelPressureHpa = if (isWindowBoundary) null else values["pressure_msl"],
                        surfacePressureHpa = if (isWindowBoundary) null else values["surface_pressure"],
                        visibilityKm = if (isWindowBoundary) null else values["visibility"]?.div(1000.0),
                        rainAmountMillimeters = values["rain"]?.let { AccumulatedMillimeters(it, interval) },
                        totalPrecipitationMillimeters = values["precipitation"]?.let { AccumulatedMillimeters(it, interval) },
                        snowfallCentimeters = values["snowfall"]?.let { AccumulatedSnowfallCentimeters(it, interval) },
                        snowDepthCentimeters = if (isWindowBoundary) null else values["snow_depth"]?.times(100.0),
                        events = listOfNotNull(event),
                        primaryEventIndex = if (event == null) null else 0,
                        sourceWeatherCode = if (isWindowBoundary) null else sourceCode,
                    )
                } catch (e: Exception) { throw IllegalArgumentException("Invalid hourly value at $time: ${e.message}") }
                factsByTime[time] = fact
                if (!isWindowBoundary && (fact.conditions.isNotEmpty() || listOfNotNull(
                        fact.temperatureC, fact.relativeHumidityPercent, fact.precipitationProbabilityPercent,
                        fact.cloudCoverPercent, fact.windSpeedKph, fact.windDirectionDegrees, fact.windGustKph,
                        fact.meanSeaLevelPressureHpa, fact.surfacePressureHpa, fact.visibilityKm,
                        fact.rainAmountMillimeters?.valueMillimeters, fact.totalPrecipitationMillimeters?.valueMillimeters,
                        fact.snowfallCentimeters?.valueCentimeters, fact.snowDepthCentimeters,
                    ).isNotEmpty())) usableDates += localDate
            }
            if (targetHourly.isEmpty() || usableDates.isEmpty()) return OpenMeteoMappingResult.NoData

            val summaries = mutableListOf<ProviderDailySummary>()
            val dailyExpected = linkedMapOf<LocalDate, Set<DailyCatalogField>>()
            dates.forEach { date -> dailyExpected[date] = DAILY_EXPECTED }
            targetDailyRows.forEach { index ->
                val date = dailyOffsetDates[index]
                val values = dailyValues.mapValues { (_, series) -> series[index] }
                val code = values["weather_code"]?.let { mapWmo(it.toWmoInt()) }
                val support = AccumulationInterval(date.atStartOfDay(zone).toInstant(), date.plusDays(1).atStartOfDay(zone).toInstant())
                try {
                    summaries += ProviderDailySummary(
                        date, support,
                        temperatureMaxC = values["temperature_2m_max"],
                        temperatureMinC = values["temperature_2m_min"],
                        precipitationProbabilityMaxPercent = values["precipitation_probability_max"],
                        conditions = code?.conditions.orEmpty(),
                        phenomena = code?.phenomena.orEmpty(),
                        rainAmountMillimeters = values["rain_sum"],
                        totalPrecipitationMillimeters = values["precipitation_sum"],
                        snowfallCentimeters = values["snowfall_sum"],
                        sunriseInstant = values["sunrise"]?.let { it.toEventInstant("sunrise", date) },
                        sunsetInstant = values["sunset"]?.let { it.toEventInstant("sunset", date) },
                        moonriseInstant = values["moonrise"]?.let { it.toEventInstant("moonrise", date) },
                        moonsetInstant = values["moonset"]?.let { it.toEventInstant("moonset", date) },
                        moonPhase = values["moon_phase"]?.also { require(it in 0.0..1.0) { "moon_phase on $date must be in [0, 1]" } },
                    )
                } catch (e: Exception) { throw IllegalArgumentException("Invalid daily value on $date: ${e.message}") }
            }

            val presentDates = (targetHourly.keys.map { it.atZone(zone).toLocalDate() } + targetDailyRows.map { dailyOffsetDates[it] }).toSet()
            val segments = buildSegments(dates, presentDates, anchor)
            val kinds = segments.map { it.productKind }.distinct()
            val productKind = if (kinds.size > 1) CatalogProductKind.MIXED else kinds.singleOrNull() ?: CatalogProductKind.FORECAST
            val catalog = CanonicalCatalog(
                request.location, request.datasetId,
                CatalogProvenance(CatalogOrigin.LIVE, productKind, sourceId = SOURCE_ID),
                request.asOf, factsByTime.values.toList(), segments, summaries,
            )
            return OpenMeteoMappingResult.Success(OpenMeteoMappedCatalog(catalog, expected, dailyExpected))
        } catch (e: Exception) {
            return OpenMeteoMappingResult.Invalid(e.message ?: "Invalid provider response")
        }
    }

    private fun buildSegments(dates: List<LocalDate>, present: Set<LocalDate>, anchor: LocalDate): List<CatalogProductSegment> {
        val assigned = dates.filter { it in present }.map { date ->
            date to if (date.isBefore(anchor)) CatalogProductKind.ARCHIVED_MODEL_FORECAST else CatalogProductKind.FORECAST
        }
        return assigned.fold(mutableListOf<CatalogProductSegment>()) { out, (date, kind) ->
            val previous = out.lastOrNull()
            if (previous != null && previous.endDateExclusive == date && previous.productKind == kind && previous.sourceId == SOURCE_ID) {
                out[out.lastIndex] = previous.copy(endDateExclusive = date.plusDays(1))
            } else out += CatalogProductSegment(date, date.plusDays(1), kind, SOURCE_ID, null, null)
            out
        }
    }

    private fun readSeries(obj: JsonNode, units: JsonNode, names: List<String>, rowCount: Int, unit: (String) -> String): Map<String, List<Double?>> {
        require(units.isObject) { "units must be an object" }
        val result = names.associateWith { name ->
            if (name == "time") {
                val declared = units.get(name)?.takeIf { it.isTextual }?.asText()
                require(declared == unit(name)) { "Missing or unexpected time unit: $declared" }
                return@associateWith emptyList()
            }
            val array = obj.get(name)
            if (array == null) return@associateWith List<Double?>(rowCount) { null }
            require(array.isArray) { "$name must be an array" }
            val declared = units.get(name)?.takeIf { it.isTextual }?.asText()
            require(declared == unit(name)) { "Missing or unexpected unit for $name: $declared" }
            array.map { node ->
                if (node.isNull) null else {
                    require(node.isNumber && node.doubleValue().isFinite()) { "$name values must be finite numbers or null" }
                    node.doubleValue()
                }
            }
        }
        return result
    }

    private fun dedupeRows(rows: List<Pair<Instant, Map<String, Double?>>>): Map<Instant, Map<String, Double?>> {
        val out = linkedMapOf<Instant, Map<String, Double?>>()
        rows.forEach { (time, values) ->
            val previous = out[time]
            require(previous == null || previous == values) { "Conflicting duplicate hourly timestamp $time" }
            out[time] = values
        }
        return out
    }

    private data class Wmo(val conditions: Set<WeatherCondition>, val phenomena: Set<WeatherPhenomenon> = emptySet())

    private fun skyEvent(code: Int, time: Instant, solar: Pair<Instant?, Instant?>?): WeatherEventFact? {
        val (id, description) = when (code) {
            0 -> (daySymbol(time, solar)?.let { if (it) WeatherEventId.SUNNY else WeatherEventId.CLEAR_NIGHT } ?: return null) to "Clear sky"
            1 -> (daySymbol(time, solar)?.let { if (it) WeatherEventId.MOSTLY_SUNNY else WeatherEventId.MOSTLY_CLEAR_NIGHT } ?: return null) to "Mainly clear"
            2 -> (daySymbol(time, solar)?.let { if (it) WeatherEventId.PARTLY_SUNNY_CLOUDY else WeatherEventId.PARTLY_CLOUDY_NIGHT } ?: return null) to "Partly cloudy"
            3 -> WeatherEventId.CLOUDY_OVERCAST to "Overcast"
            else -> return null
        }
        return WeatherEventFact(id, code, provider = SOURCE_ID,
            documentedCodeDescription = description, descriptionSource = "open-meteo:forecast-api-weather-code-table")
    }

    private fun rainIceEvent(code: Int): WeatherEventFact? {
        val (id, description) = when (code) {
            51 -> WeatherEventId.DRIZZLE to "Light drizzle"
            53 -> WeatherEventId.DRIZZLE to "Moderate drizzle"
            55 -> WeatherEventId.DRIZZLE to "Dense drizzle"
            56 -> WeatherEventId.FREEZING_DRIZZLE to "Light freezing drizzle"
            57 -> WeatherEventId.FREEZING_DRIZZLE to "Dense freezing drizzle"
            61 -> WeatherEventId.RAIN to "Slight rain"
            63 -> WeatherEventId.RAIN to "Moderate rain"
            65 -> WeatherEventId.HEAVY_RAIN to "Heavy rain"
            66 -> WeatherEventId.FREEZING_RAIN to "Light freezing rain"
            67 -> WeatherEventId.FREEZING_RAIN to "Heavy freezing rain"
            80 -> WeatherEventId.RAIN_SHOWERS to "Slight rain showers"
            81 -> WeatherEventId.RAIN_SHOWERS to "Moderate rain showers"
            82 -> WeatherEventId.RAIN_SHOWERS to "Violent rain showers"
            else -> return null
        }
        return WeatherEventFact(id, sourceCode = code, provider = SOURCE_ID,
            documentedCodeDescription = description,
            descriptionSource = "open-meteo:forecast-api-weather-code-table")
    }

    private fun daySymbol(time: Instant, solar: Pair<Instant?, Instant?>?): Boolean? {
        val sunrise = solar?.first ?: return null
        val sunset = solar.second ?: return null
        return time >= sunrise.minus(Duration.ofHours(1)) && time < sunset.minus(Duration.ofHours(1))
    }
    private fun mapWmo(code: Int): Wmo? = when (code) {
        0 -> Wmo(setOf(WeatherCondition.CLEAR))
        1, 2 -> Wmo(setOf(WeatherCondition.PARTLY_CLOUDY))
        3 -> Wmo(setOf(WeatherCondition.CLOUDY))
        45, 48 -> Wmo(setOf(WeatherCondition.CLOUDY), setOf(WeatherPhenomenon.FOG))
        56, 57, 66, 67 -> Wmo(setOf(WeatherCondition.RAIN), setOf(WeatherPhenomenon.FREEZING_RAIN))
        in 51..67, in 80..82 -> Wmo(setOf(WeatherCondition.RAIN))
        in 71..77, in 85..86 -> Wmo(setOf(WeatherCondition.SNOW))
        95, 96, 99 -> Wmo(setOf(WeatherCondition.STORM, WeatherCondition.RAIN))
        else -> null
    }.also { require(code in 0..99) { "Malformed WMO weather code $code" } }

    private fun Double.toWmoInt(): Int {
        require(isFinite() && this == kotlin.math.floor(this) && this in 0.0..99.0) { "Malformed WMO weather code $this" }
        return toInt()
    }

    private fun Double.toEventInstant(name: String, date: LocalDate): Instant {
        require(isFinite() && this == kotlin.math.floor(this) && this >= 0.0 && this <= Long.MAX_VALUE.toDouble()) {
            "$name on $date must be whole Unix seconds"
        }
        return try {
            Instant.ofEpochSecond(toLong())
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid $name instant on $date", e)
        }
    }

    private fun hourlyUnit(name: String): String = when (name) {
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
        "wind_direction_10m" -> "°"
        else -> error("Unexpected hourly variable $name")
    }
    private fun dailyUnit(name: String): String = when (name) {
        "time" -> "unixtime"
        "sunrise", "sunset" -> "unixtime"
        "moonrise", "moonset" -> "unixtime"
        "moon_phase" -> "fraction"
        "weather_code" -> "wmo code"
        "temperature_2m_max", "temperature_2m_min" -> "°C"
        "precipitation_probability_max" -> "%"
        "precipitation_sum", "rain_sum" -> "mm"
        "snowfall_sum" -> "cm"
        else -> error("Unexpected daily variable $name")
    }

    private fun JsonNode.required(name: String): JsonNode = get(name) ?: error("Missing $name")
    private fun JsonNode.requiredText(name: String): String = required(name).takeIf { it.isTextual }?.asText() ?: error("$name must be text")
    private fun JsonNode.requiredLong(name: String): Long = required(name).takeIf { it.isIntegralNumber && it.canConvertToLong() }?.longValue() ?: error("$name must be an integer")
    private fun JsonNode.longArray(): List<Long> { require(isArray); return map { it.takeIf { n -> n.isIntegralNumber && n.canConvertToLong() }?.longValue() ?: error("time values must be integer Unix seconds") } }
    private val DAILY_EXPECTED = setOf(DailyCatalogField.TEMPERATURE_MAX_C, DailyCatalogField.TEMPERATURE_MIN_C, DailyCatalogField.PRECIPITATION_PROBABILITY_MAX_PERCENT, DailyCatalogField.CONDITIONS, DailyCatalogField.PHENOMENA, DailyCatalogField.RAIN_AMOUNT, DailyCatalogField.TOTAL_PRECIPITATION, DailyCatalogField.SNOWFALL, DailyCatalogField.SUNRISE, DailyCatalogField.SUNSET, DailyCatalogField.MOONRISE, DailyCatalogField.MOONSET, DailyCatalogField.MOON_PHASE)
    const val SOURCE_ID = "open-meteo-forecast-api"
}
