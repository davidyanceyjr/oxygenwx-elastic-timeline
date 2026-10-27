package com.oxygen.weather.timeline.data.catalog

import com.oxygen.weather.timeline.data.ForecastHour
import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.data.SkyCondition
import java.time.Instant
import java.time.LocalDateTime
import java.util.Collections

enum class FixtureTimeFailureKind { DST_GAP, DST_OVERLAP }

sealed interface FixtureAdapterError {
    data class InvalidLocalTime(
        val localTime: LocalDateTime,
        val zoneId: String,
        val kind: FixtureTimeFailureKind,
    ) : FixtureAdapterError

    data class InvalidFixtureFact(val localTime: LocalDateTime, val reason: String) : FixtureAdapterError
    data class SequenceIdentityCollision(val id: String, val names: List<String>) : FixtureAdapterError
}

sealed interface FixtureAdapterResult<out T> {
    data class Success<T>(val value: T) : FixtureAdapterResult<T>
    data class Failure(val error: FixtureAdapterError) : FixtureAdapterResult<Nothing>
}

enum class FixtureAmountMapping { ACCUMULATION_INTERVAL_UNAVAILABLE, CANONICAL_SNOW_DEPTH }

sealed interface LegacyPrecipitationAmount {
    val value: Double

    data class RainLiquidMm(override val value: Double) : LegacyPrecipitationAmount
    data class SnowDepthCm(override val value: Double) : LegacyPrecipitationAmount
}

/** Source-only details that the provider-neutral canonical facts cannot express. */
data class LegacyFixtureMetadata(
    val condition: String? = null,
    val baseSkyCondition: String? = null,
    val precipitationType: String? = null,
    val precipitationAmount: LegacyPrecipitationAmount? = null,
    val precipitationAmountMapping: FixtureAmountMapping? = null,
    val warningState: String? = null,
    val fixtureProvenance: String? = null,
)

data class AdaptedFixtureFact(
    val fixtureId: String,
    val facts: CanonicalWeatherFacts,
    val metadata: LegacyFixtureMetadata,
)

data class SampleDaySummary(
    val date: java.time.LocalDate,
    val lowC: Double,
    val highC: Double,
    val maxPrecipitationProbabilityPercent: Double,
    val representativeFixtureId: String,
    val representativeValidTime: Instant,
)

class AdaptedSampleForecast internal constructor(
    val catalog: CanonicalCatalog,
    hourly: List<AdaptedFixtureFact>,
    dailySummaries: List<SampleDaySummary>,
) {
    val hourly: List<AdaptedFixtureFact> = Collections.unmodifiableList(hourly.toList())
    val dailySummaries: List<SampleDaySummary> = Collections.unmodifiableList(dailySummaries.toList())
}

/** Pure adapter for the fixed main-source demo fixture. All identity/time inputs are explicit. */
object SampleForecastCatalogAdapter {
    fun adapt(
        location: CatalogLocation,
        datasetId: DatasetId,
        asOf: Instant,
    ): FixtureAdapterResult<AdaptedSampleForecast> {
        val allTimes = SampleForecast.hours.map { it.time } + SampleForecast.days.map { it.representative.time }
        val resolved = when (val resolution = resolveFixtureTimes(allTimes, location)) {
            is FixtureAdapterResult.Failure -> return resolution
            is FixtureAdapterResult.Success -> resolution.value
        }
        var index = 0
        val hourFacts = SampleForecast.hours.map { hour ->
            val fact = adaptHour(hour, resolved[index++])
                ?: return FixtureAdapterResult.Failure(
                    FixtureAdapterError.InvalidFixtureFact(hour.time, "Fixture value is outside canonical fact constraints"),
                )
            fact
        }
        val summaries = SampleForecast.days.map { day ->
            val representativeTime = resolved[index++]
            SampleDaySummary(
                date = day.date,
                lowC = day.lowC,
                highC = day.highC,
                maxPrecipitationProbabilityPercent = day.maxPrecipitationProbabilityPercent,
                representativeFixtureId = day.representative.time.toString(),
                representativeValidTime = representativeTime,
            )
        }
        val catalog = catalog(location, datasetId, asOf, hourFacts.map { it.facts })
        return FixtureAdapterResult.Success(AdaptedSampleForecast(catalog, hourFacts, summaries))
    }
}

internal fun adaptHour(hour: ForecastHour, validTime: Instant): AdaptedFixtureFact? {
    val amount = amountMetadata(hour.precipitationAmount)
    val facts = runCatching {
        CanonicalWeatherFacts(
            validTime = validTime,
            conditions = setOf(hour.condition.toCanonical()),
            temperatureC = hour.temperatureC,
            precipitationProbabilityPercent = hour.precipitationProbabilityPercent,
            cloudCoverPercent = hour.cloudPercent,
            windSpeedKph = hour.windKph,
            windDirectionDegrees = hour.windDirectionDegrees,
            snowDepthCentimeters = (hour.precipitationAmount as? PrecipitationAmount.SnowDepthCm)?.value,
        )
    }.getOrNull() ?: return null
    return AdaptedFixtureFact(
        fixtureId = hour.time.toString(),
        facts = facts,
        metadata = LegacyFixtureMetadata(
            condition = hour.condition.name,
            baseSkyCondition = hour.baseSkyCondition.name,
            precipitationType = hour.precipitationType.name,
            precipitationAmount = amount,
            precipitationAmountMapping = when (hour.precipitationAmount) {
                is PrecipitationAmount.RainLiquidMm -> FixtureAmountMapping.ACCUMULATION_INTERVAL_UNAVAILABLE
                is PrecipitationAmount.SnowDepthCm -> FixtureAmountMapping.CANONICAL_SNOW_DEPTH
                null -> null
            },
        ),
    )
}

internal fun amountMetadata(amount: PrecipitationAmount?): LegacyPrecipitationAmount? = when (amount) {
    is PrecipitationAmount.RainLiquidMm -> LegacyPrecipitationAmount.RainLiquidMm(amount.value)
    is PrecipitationAmount.SnowDepthCm -> LegacyPrecipitationAmount.SnowDepthCm(amount.value)
    null -> null
}

internal fun SkyCondition.toCanonical(): WeatherCondition = when (this) {
    SkyCondition.CLEAR -> WeatherCondition.CLEAR
    SkyCondition.PARTLY_CLOUDY -> WeatherCondition.PARTLY_CLOUDY
    SkyCondition.CLOUDY -> WeatherCondition.CLOUDY
    SkyCondition.RAIN -> WeatherCondition.RAIN
    SkyCondition.STORM -> WeatherCondition.STORM
}

internal fun catalog(
    location: CatalogLocation,
    datasetId: DatasetId,
    asOf: Instant,
    facts: List<CanonicalWeatherFacts>,
) = CanonicalCatalog(
    location = location,
    datasetId = datasetId,
    provenance = CatalogProvenance(CatalogOrigin.SIMULATED, CatalogProductKind.SYNTHETIC_METEOROLOGICAL_PRODUCT),
    asOf = asOf,
    facts = facts,
)

internal fun resolveFixtureTimes(
    times: List<LocalDateTime>,
    location: CatalogLocation,
): FixtureAdapterResult<List<Instant>> {
    val offsets = times.map { local -> location.timeZone.rules.getValidOffsets(local) }
    val invalidIndex = offsets.indexOfFirst { it.size != 1 }
    if (invalidIndex >= 0) {
        val local = times[invalidIndex]
        val kind = if (offsets[invalidIndex].isEmpty()) FixtureTimeFailureKind.DST_GAP else FixtureTimeFailureKind.DST_OVERLAP
        return FixtureAdapterResult.Failure(FixtureAdapterError.InvalidLocalTime(local, location.timeZone.id, kind))
    }
    return FixtureAdapterResult.Success(times.zip(offsets).map { (local, validOffsets) ->
        local.atOffset(validOffsets.single()).toInstant()
    })
}
