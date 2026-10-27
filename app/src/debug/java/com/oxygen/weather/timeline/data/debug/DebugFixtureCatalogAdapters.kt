package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.PrecipitationAmount
import com.oxygen.weather.timeline.data.catalog.AdaptedFixtureFact
import com.oxygen.weather.timeline.data.catalog.CanonicalWeatherFacts
import com.oxygen.weather.timeline.data.catalog.CatalogLocation
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.data.catalog.FixtureAdapterError
import com.oxygen.weather.timeline.data.catalog.FixtureAdapterResult
import com.oxygen.weather.timeline.data.catalog.FixtureAmountMapping
import com.oxygen.weather.timeline.data.catalog.LegacyFixtureMetadata
import com.oxygen.weather.timeline.data.catalog.WeatherCondition
import com.oxygen.weather.timeline.data.catalog.amountMetadata
import com.oxygen.weather.timeline.data.catalog.catalog
import com.oxygen.weather.timeline.data.catalog.resolveFixtureTimes
import java.time.Instant
import java.util.Locale

data class AdaptedDebugScenario(
    val id: DebugScenarioId,
    val validTime: Instant,
    val fact: AdaptedFixtureFact,
)

data class AdaptedDebugSequenceStep(
    val scenarioId: DebugScenarioId,
    val order: Int,
    val sequenceTime: Instant,
    val scenarioValidTime: Instant,
)

data class AdaptedDebugSequence(
    val id: String,
    val name: String,
    val steps: List<AdaptedDebugSequenceStep>,
)

data class AdaptedDebugForecastCatalog(
    val catalog: com.oxygen.weather.timeline.data.catalog.CanonicalCatalog,
    val scenarios: List<AdaptedDebugScenario>,
    val sequences: List<AdaptedDebugSequence>,
)

data class AdaptedDebugProfile(
    val id: DebugProfileId,
    val label: String,
    val loadedDays: Int,
    val eventStartIndex: Int,
    val eventEndExclusive: Int,
    val referenceTime: Instant,
    val catalog: com.oxygen.weather.timeline.data.catalog.CanonicalCatalog,
    val samples: List<AdaptedFixtureFact>,
)

/** Debug-only pure adapters; fixture generators and presentation routes remain unchanged. */
object DebugForecastCatalogAdapter {
    fun adapt(
        location: CatalogLocation,
        datasetId: DatasetId,
        asOf: Instant,
    ): FixtureAdapterResult<AdaptedDebugForecastCatalog> {
        val stableIds = when (val identityResult = deriveStableSequenceIds(DebugForecastCatalog.sequences.map { it.name })) {
            is FixtureAdapterResult.Failure -> return identityResult
            is FixtureAdapterResult.Success -> identityResult.value
        }

        val scenarioTimes = DebugForecastCatalog.scenarios.map { it.validTime }
        val stepTimes = DebugForecastCatalog.sequences.flatMap { sequence -> sequence.steps.map { it.sequenceTime } }
        val allTimes = scenarioTimes + stepTimes
        val resolved = when (val resolution = resolveFixtureTimes(allTimes, location)) {
            is FixtureAdapterResult.Failure -> return resolution
            is FixtureAdapterResult.Success -> resolution.value
        }
        val scenarioTimesById = DebugForecastCatalog.scenarios.mapIndexed { index, scenario -> scenario.id to resolved[index] }.toMap()
        val scenarios = DebugForecastCatalog.scenarios.mapIndexed { index, scenario ->
            val validTime = resolved[index]
            val fact = adaptScenario(scenario, validTime)
                ?: return FixtureAdapterResult.Failure(
                    FixtureAdapterError.InvalidFixtureFact(scenario.validTime, "Fixture value is outside canonical fact constraints"),
                )
            AdaptedDebugScenario(scenario.id, validTime, fact)
        }
        var timeIndex = scenarioTimes.size
        val sequences = DebugForecastCatalog.sequences.mapIndexed { sequenceIndex, sequence ->
            val steps = sequence.steps.mapIndexed { stepIndex, step ->
                val sequenceTime = resolved[timeIndex++]
                AdaptedDebugSequenceStep(
                    scenarioId = step.scenarioId,
                    order = stepIndex,
                    sequenceTime = sequenceTime,
                    scenarioValidTime = scenarioTimesById.getValue(step.scenarioId),
                )
            }
            AdaptedDebugSequence(stableIds[sequenceIndex], sequence.name, steps)
        }
        val allFacts = scenarios.map { it.fact.facts }
        val resultCatalog = catalog(location, datasetId, asOf, allFacts)
        return FixtureAdapterResult.Success(AdaptedDebugForecastCatalog(resultCatalog, scenarios, sequences))
    }
}

object DebugForecastProfileAdapter {
    fun adapt(
        id: DebugProfileId,
        location: CatalogLocation,
        datasetId: DatasetId,
        asOf: Instant,
    ): FixtureAdapterResult<AdaptedDebugProfile> {
        val profile = DebugForecastProfiles.get(id)
        val localTimes = profile.samples.map { it.validTime } + profile.referenceTime
        val resolved = when (val resolution = resolveFixtureTimes(localTimes, location)) {
            is FixtureAdapterResult.Failure -> return resolution
            is FixtureAdapterResult.Success -> resolution.value
        }
        val samples = profile.samples.mapIndexed { index, sample ->
            adaptProfileSample(sample, resolved[index])
                ?: return FixtureAdapterResult.Failure(
                    FixtureAdapterError.InvalidFixtureFact(sample.validTime, "Fixture value is outside canonical fact constraints"),
                )
        }
        val referenceTime = resolved.last()
        return FixtureAdapterResult.Success(
            AdaptedDebugProfile(
                id = profile.id,
                label = profile.id.label,
                loadedDays = profile.id.loadedDays,
                eventStartIndex = profile.eventStartIndex,
                eventEndExclusive = profile.eventEndExclusive,
                referenceTime = referenceTime,
                catalog = catalog(location, datasetId, asOf, samples.map { it.facts }),
                samples = samples,
            ),
        )
    }
}

/** Locale-independent slug for the adapter-only identity chosen by the owner. */
internal fun stableSequenceId(name: String): String = name.lowercase(Locale.ROOT)
    .replace(Regex("[^a-z0-9]+"), "-")
    .trim('-')

internal fun deriveStableSequenceIds(names: List<String>): FixtureAdapterResult<List<String>> {
    val ids = names.map(::stableSequenceId)
    val collision = ids.withIndex().groupBy { it.value }.entries.firstOrNull { it.key.isEmpty() || it.value.size > 1 }
    if (collision != null) {
        return FixtureAdapterResult.Failure(
            FixtureAdapterError.SequenceIdentityCollision(collision.key, collision.value.map { names[it.index] }),
        )
    }
    return FixtureAdapterResult.Success(ids)
}

private fun adaptScenario(scenario: DebugForecastScenario, validTime: Instant): AdaptedFixtureFact? {
    val amount = amountMetadata(scenario.precipitationAmount)
    val fact = buildCanonicalFacts(
        validTime = validTime,
        condition = scenario.condition.toCanonical(),
        precipitationProbability = scenario.precipitationProbabilityPercent,
        thunderstormProbability = scenario.thunderstormProbabilityPercent,
        amount = scenario.precipitationAmount,
    ) ?: return null
    return AdaptedFixtureFact(
        fixtureId = scenario.id.name,
        facts = fact,
        metadata = LegacyFixtureMetadata(
            condition = scenario.condition.name,
            precipitationType = scenario.precipitationType.name,
            precipitationAmount = amount,
            precipitationAmountMapping = amountMapping(scenario.precipitationAmount),
            warningState = scenario.warningState.name,
            fixtureProvenance = scenario.provenance.name,
        ),
    )
}

private fun adaptProfileSample(sample: DebugProfileSample, validTime: Instant): AdaptedFixtureFact? {
    val amount = amountMetadata(sample.precipitationAmount)
    val fact = buildCanonicalFacts(
        validTime = validTime,
        condition = sample.condition.toCanonical(),
        temperatureC = sample.temperatureC,
        precipitationProbability = sample.precipitationProbabilityPercent,
        thunderstormProbability = sample.thunderstormProbabilityPercent,
        cloudPercent = sample.cloudPercent,
        windSpeed = sample.windKph,
        amount = sample.precipitationAmount,
    ) ?: return null
    return AdaptedFixtureFact(
        fixtureId = sample.validTime.toString(),
        facts = fact,
        metadata = LegacyFixtureMetadata(
            condition = sample.condition.name,
            precipitationType = sample.precipitationType.name,
            precipitationAmount = amount,
            precipitationAmountMapping = amountMapping(sample.precipitationAmount),
            warningState = sample.warningState.name,
            fixtureProvenance = sample.provenance.name,
        ),
    )
}

private fun buildCanonicalFacts(
    validTime: Instant,
    condition: WeatherCondition,
    precipitationProbability: Double?,
    thunderstormProbability: Double?,
    temperatureC: Double? = null,
    cloudPercent: Double? = null,
    windSpeed: Double? = null,
    amount: PrecipitationAmount?,
): CanonicalWeatherFacts? = runCatching {
    CanonicalWeatherFacts(
        validTime = validTime,
        conditions = setOf(condition),
        temperatureC = temperatureC,
        precipitationProbabilityPercent = precipitationProbability,
        thunderstormProbabilityPercent = thunderstormProbability,
        cloudCoverPercent = cloudPercent,
        windSpeedKph = windSpeed,
        snowDepthCentimeters = (amount as? PrecipitationAmount.SnowDepthCm)?.value,
    )
}.getOrNull()

private fun DebugCondition.toCanonical(): WeatherCondition = when (this) {
    DebugCondition.CLEAR -> WeatherCondition.CLEAR
    DebugCondition.PARTLY_CLOUDY -> WeatherCondition.PARTLY_CLOUDY
    DebugCondition.CLOUDY -> WeatherCondition.CLOUDY
    DebugCondition.RAIN -> WeatherCondition.RAIN
    DebugCondition.SNOW -> WeatherCondition.SNOW
    DebugCondition.STORM -> WeatherCondition.STORM
}

private fun amountMapping(amount: PrecipitationAmount?): FixtureAmountMapping? = when (amount) {
    is PrecipitationAmount.RainLiquidMm -> FixtureAmountMapping.ACCUMULATION_INTERVAL_UNAVAILABLE
    is PrecipitationAmount.SnowDepthCm -> FixtureAmountMapping.CANONICAL_SNOW_DEPTH
    null -> null
}
