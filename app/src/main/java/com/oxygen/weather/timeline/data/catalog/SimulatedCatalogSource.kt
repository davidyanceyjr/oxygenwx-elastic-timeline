package com.oxygen.weather.timeline.data.catalog

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.CLEAR
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.CLOUDY
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.PARTLY_CLOUDY
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.RAIN
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.SNOW
import com.oxygen.weather.timeline.data.catalog.WeatherCondition.STORM

/** One fixed, request-anchored synthetic diagnostic sequence. No clock or scenario input. */
class SimulatedCatalogSource : WeatherCatalogSource {
    override val origin = CatalogOrigin.SIMULATED

    override suspend fun load(request: CatalogLoadRequest): CatalogLoadResult {
        val anchor = request.asOf.atZone(request.location.timeZone).toLocalDate()
        val facts = ArrayList<CanonicalWeatherFacts>(44 * 25)
        val dates = (-30L..13L).map(anchor::plusDays)
        val windowStart = dates.first().atStartOfDay(request.location.timeZone).toInstant()
        SEQUENCE.forEachIndexed { index, row ->
            val date = dates[index]
            val zone = request.location.timeZone
            val localStart = date.atStartOfDay(zone).toInstant()
            val localEnd = date.plusDays(1).atStartOfDay(zone).toInstant()
            var validTime = localStart
            while (validTime < localEnd) {
                val localHour = validTime.atZone(zone).hour
                val night = localHour < 9 || localHour >= 21
                val intervalIsInWindow = !validTime.minus(1, ChronoUnit.HOURS).isBefore(windowStart)
                facts += row.fact(validTime, night, includeAccumulatedAmounts = intervalIsInWindow)
                validTime = validTime.plus(1, ChronoUnit.HOURS)
            }
        }
        val support = supportFor(facts, request.location.timeZone)
        val catalog = CanonicalCatalog(
            request.location,
            request.datasetId,
            CatalogProvenance(
                CatalogOrigin.SIMULATED,
                CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT,
                sourceId = "oxygen-wx10-simulated",
                productId = "fixed-44-day-hourly-sequence-v3",
            ),
            request.asOf,
            facts,
        )
        check(CatalogWindow.create(catalog, support) is CatalogWindowResult.Success)
        return CatalogLoadResult.Success(catalog, support)
    }
}

private data class Row(
    val conditions: Set<WeatherCondition>,
    val phenomena: Set<WeatherPhenomenon> = emptySet(),
    val temperatureC: Double? = null,
    val cloudCoverPercent: Double? = null,
    val precipitationProbabilityPercent: Double? = null,
    val thunderstormProbabilityPercent: Double? = null,
    val precipitationRate: Double? = null,
    val rainAmount: Double? = null,
    val snowfall: Double? = null,
    val snowDepth: Double? = null,
    val windSpeed: Double? = null,
    val gust: Double? = null,
    val visibility: Double? = null,
    val nightTemperatureC: Double? = null,
    val nightConditions: Set<WeatherCondition>? = null,
    val nightPhenomena: Set<WeatherPhenomenon>? = null,
    val nightCloudCoverPercent: Double? = null,
    val nightVisibility: Double? = null,
) {
    fun fact(time: Instant, night: Boolean, includeAccumulatedAmounts: Boolean): CanonicalWeatherFacts {
        val interval = AccumulationInterval(time.minusSeconds(3600), time)
        return CanonicalWeatherFacts(
            validTime = time,
            conditions = if (night) nightConditions ?: conditions else conditions,
            phenomena = if (night) nightPhenomena ?: phenomena else phenomena,
            temperatureC = if (night) nightTemperatureC ?: temperatureC else temperatureC,
            cloudCoverPercent = if (night) nightCloudCoverPercent ?: cloudCoverPercent else cloudCoverPercent,
            precipitationProbabilityPercent = precipitationProbabilityPercent ?: 0.0,
            thunderstormProbabilityPercent = thunderstormProbabilityPercent,
            precipitationRate = precipitationRate?.let(::PrecipitationRate),
            rainAmountMillimeters = rainAmount?.takeIf { includeAccumulatedAmounts }
                ?.let { AccumulatedMillimeters(it, interval) },
            snowfallCentimeters = snowfall?.takeIf { includeAccumulatedAmounts }
                ?.let { AccumulatedSnowfallCentimeters(it, interval) },
            snowDepthCentimeters = snowDepth,
            windSpeedKph = windSpeed ?: 0.0,
            windGustKph = gust,
            visibilityKm = if (night) nightVisibility ?: visibility else visibility,
        )
    }
}

private fun supportFor(
    facts: List<CanonicalWeatherFacts>,
    zone: ZoneId,
): Map<LocalDate, Map<CatalogField, ExpectedFieldSupport>> {
    val points = linkedMapOf<LocalDate, MutableMap<CatalogField, MutableSet<Instant>>>()
    val intervals = linkedMapOf<LocalDate, MutableMap<CatalogField, MutableSet<AccumulationInterval>>>()
    facts.forEach { fact ->
        fun point(field: CatalogField, present: Boolean) {
            if (present) {
                val date = fact.validTime.atZone(zone).toLocalDate()
                points.getOrPut(date) { linkedMapOf() }.getOrPut(field) { linkedSetOf() }.add(fact.validTime)
            }
        }
        fun interval(field: CatalogField, value: AccumulationInterval?) {
            if (value != null) {
                val date = value.end.minusNanos(1).atZone(zone).toLocalDate()
                intervals.getOrPut(date) { linkedMapOf() }.getOrPut(field) { linkedSetOf() }.add(value)
            }
        }
        point(CatalogField.CONDITIONS, fact.conditions.isNotEmpty())
        point(CatalogField.PHENOMENA, fact.phenomena.isNotEmpty())
        point(CatalogField.TEMPERATURE_C, fact.temperatureC != null)
        point(CatalogField.CLOUD_COVER_PERCENT, fact.cloudCoverPercent != null)
        point(CatalogField.PRECIPITATION_PROBABILITY_PERCENT, fact.precipitationProbabilityPercent != null)
        point(CatalogField.THUNDERSTORM_PROBABILITY_PERCENT, fact.thunderstormProbabilityPercent != null)
        point(CatalogField.PRECIPITATION_RATE, fact.precipitationRate != null)
        point(CatalogField.SNOW_DEPTH_CENTIMETERS, fact.snowDepthCentimeters != null)
        point(CatalogField.WIND_SPEED_KPH, fact.windSpeedKph != null)
        point(CatalogField.WIND_GUST_KPH, fact.windGustKph != null)
        point(CatalogField.VISIBILITY_KM, fact.visibilityKm != null)
        interval(CatalogField.RAIN_AMOUNT_MILLIMETERS, fact.rainAmountMillimeters?.interval)
        interval(CatalogField.SNOWFALL_CENTIMETERS, fact.snowfallCentimeters?.interval)
    }
    val dates = points.keys + intervals.keys
    return dates.associateWith { date ->
        points[date].orEmpty().mapValues { ExpectedFieldSupport.Points(it.value) } +
            intervals[date].orEmpty().mapValues { ExpectedFieldSupport.Intervals(it.value) }
    }
}

private val SEQUENCE: List<Row> = listOf(
        Row(setOf(CLEAR), temperatureC = 18.0, cloudCoverPercent = 5.0, rainAmount = 0.0),
        Row(setOf(CLEAR), temperatureC = 15.0, nightTemperatureC = 11.0, cloudCoverPercent = 5.0, rainAmount = 0.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 18.0, cloudCoverPercent = 35.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 17.0, cloudCoverPercent = 60.0),
        Row(setOf(CLOUDY), temperatureC = 16.0, cloudCoverPercent = 90.0),
        Row(setOf(RAIN), temperatureC = 15.0, cloudCoverPercent = 95.0, precipitationProbabilityPercent = 55.0, precipitationRate = 1.0, rainAmount = 1.0),
        Row(setOf(RAIN), temperatureC = 14.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 75.0, precipitationRate = 3.0, rainAmount = 3.0),
        Row(setOf(RAIN), temperatureC = 14.0, cloudCoverPercent = 95.0, precipitationProbabilityPercent = 70.0),
        Row(setOf(RAIN), temperatureC = 13.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 85.0, precipitationRate = 8.0, rainAmount = 8.0, windSpeed = 20.0, gust = 35.0),
        Row(setOf(RAIN), temperatureC = 12.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 95.0, precipitationRate = 18.0, rainAmount = 18.0, windSpeed = 30.0, gust = 55.0),
        Row(setOf(RAIN), temperatureC = 13.0, cloudCoverPercent = 95.0, precipitationProbabilityPercent = 80.0, precipitationRate = 7.0, rainAmount = 7.0, windSpeed = 18.0, gust = 30.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 16.0, cloudCoverPercent = 45.0, rainAmount = 0.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 17.0, cloudCoverPercent = 45.0, windSpeed = 45.0, gust = 75.0),
        Row(setOf(CLEAR), temperatureC = 16.0, cloudCoverPercent = 10.0, windSpeed = 22.0, gust = 40.0),
        Row(setOf(PARTLY_CLOUDY), setOf(WeatherPhenomenon.HAZE), temperatureC = 18.0, cloudCoverPercent = 40.0, visibility = 6.0),
        Row(setOf(PARTLY_CLOUDY), setOf(WeatherPhenomenon.HAZE), temperatureC = 19.0, cloudCoverPercent = 40.0, visibility = 4.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 18.0, cloudCoverPercent = 40.0, visibility = 10.0),
        Row(setOf(CLOUDY), setOf(WeatherPhenomenon.FOG), temperatureC = 11.0, cloudCoverPercent = 90.0, visibility = 2.0),
        Row(setOf(CLOUDY), setOf(WeatherPhenomenon.FOG), temperatureC = 10.0, cloudCoverPercent = 100.0, visibility = 0.5),
        Row(setOf(CLOUDY), setOf(WeatherPhenomenon.FOG), temperatureC = 9.0, nightTemperatureC = 8.0, cloudCoverPercent = 95.0, nightCloudCoverPercent = 5.0, visibility = 1.0, nightVisibility = 12.0, nightConditions = setOf(CLEAR), nightPhenomena = emptySet()),
        Row(setOf(RAIN), temperatureC = 3.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 70.0, precipitationRate = 2.0, rainAmount = 2.0),
        Row(setOf(RAIN), setOf(WeatherPhenomenon.FREEZING_RAIN), temperatureC = 0.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 80.0, precipitationRate = 2.0, rainAmount = 2.0),
        Row(setOf(RAIN), setOf(WeatherPhenomenon.ICE_PELLETS), temperatureC = 0.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 75.0, precipitationRate = 2.0),
        Row(setOf(SNOW), temperatureC = -3.0, cloudCoverPercent = 100.0, snowfall = 2.0, snowDepth = 3.0),
        Row(setOf(SNOW), temperatureC = -5.0, nightTemperatureC = -7.0, cloudCoverPercent = 100.0, snowDepth = 6.0),
        Row(setOf(SNOW), temperatureC = -2.0, cloudCoverPercent = 95.0, snowfall = 0.5, snowDepth = 7.0),
        Row(setOf(CLOUDY), temperatureC = -1.0, cloudCoverPercent = 90.0, snowfall = 0.0, snowDepth = 7.0),
        Row(setOf(CLEAR), temperatureC = -4.0, cloudCoverPercent = 5.0, snowDepth = 6.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 2.0, cloudCoverPercent = 35.0, snowDepth = 4.0),
        Row(setOf(CLOUDY), temperatureC = 5.0, cloudCoverPercent = 90.0, precipitationProbabilityPercent = 20.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 9.0, cloudCoverPercent = 40.0, precipitationProbabilityPercent = 25.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 10.0, nightTemperatureC = 7.0, cloudCoverPercent = 35.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 13.0, cloudCoverPercent = 55.0, precipitationProbabilityPercent = 35.0, thunderstormProbabilityPercent = 27.0),
        Row(setOf(STORM, RAIN), temperatureC = 16.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 95.0, thunderstormProbabilityPercent = 90.0, precipitationRate = 5.0),
        Row(setOf(STORM, RAIN), temperatureC = 15.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 80.0, thunderstormProbabilityPercent = 55.0, precipitationRate = 4.0, rainAmount = 4.0),
        Row(setOf(RAIN), temperatureC = 14.0, cloudCoverPercent = 100.0, precipitationProbabilityPercent = 90.0, precipitationRate = 12.0, rainAmount = 12.0, windSpeed = 25.0, gust = 45.0),
        Row(setOf(RAIN), temperatureC = 14.0, cloudCoverPercent = 95.0, precipitationProbabilityPercent = 75.0, precipitationRate = 4.0, rainAmount = 4.0, windSpeed = 20.0, gust = 40.0),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 16.0, cloudCoverPercent = 40.0, rainAmount = 0.0, windSpeed = 25.0, gust = 45.0),
        Row(setOf(CLOUDY), setOf(WeatherPhenomenon.FOG), temperatureC = 12.0, cloudCoverPercent = 95.0, visibility = 1.5),
        Row(setOf(CLOUDY), setOf(WeatherPhenomenon.FOG), temperatureC = 11.0, nightTemperatureC = 13.0, cloudCoverPercent = 95.0, nightCloudCoverPercent = 40.0, visibility = 2.0, nightVisibility = 12.0, nightConditions = setOf(PARTLY_CLOUDY), nightPhenomena = emptySet()),
        Row(setOf(PARTLY_CLOUDY), temperatureC = 17.0, cloudCoverPercent = 35.0),
        Row(setOf(CLEAR), temperatureC = 18.0, cloudCoverPercent = 5.0, rainAmount = 0.0),
        Row(setOf(CLEAR), temperatureC = 18.0, cloudCoverPercent = 5.0, rainAmount = 0.0),
        Row(setOf(CLEAR), temperatureC = 16.0, nightTemperatureC = 12.0, cloudCoverPercent = 5.0, rainAmount = 0.0),
)
