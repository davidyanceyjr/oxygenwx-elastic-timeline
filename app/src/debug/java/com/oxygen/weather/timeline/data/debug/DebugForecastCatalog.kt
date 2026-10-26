package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.PrecipitationAmount
import java.time.LocalDateTime

enum class DebugScenarioId {
    CLEAR_DAY,
    CLEAR_NIGHT,
    PARTLY_DAY,
    CLOUDY_NIGHT,
    RAIN_AMOUNT,
    RAIN_UNKNOWN_AMOUNT,
    SNOW_DAY,
    SNOW_NIGHT,
    STORM_LOW_THUNDER,
    STORM_HIGH_THUNDER,
    STORM_WARNING_PREVIEW,
    CLEAR_ENDPOINT,
}

enum class DebugCondition { CLEAR, PARTLY_CLOUDY, CLOUDY, RAIN, SNOW, STORM }
enum class DebugPrecipitationType { NONE, RAIN, SNOW }
enum class DebugWarningState { NONE, PRESENT }
enum class DebugProvenance { SYNTHETIC_DEBUG }

data class DebugForecastScenario(
    val id: DebugScenarioId,
    val validTime: LocalDateTime,
    val condition: DebugCondition,
    val precipitationProbabilityPercent: Double?,
    val precipitationType: DebugPrecipitationType,
    val precipitationAmount: PrecipitationAmount?,
    val thunderstormProbabilityPercent: Double?,
    val warningState: DebugWarningState,
    val provenance: DebugProvenance = DebugProvenance.SYNTHETIC_DEBUG,
) {
    init {
        require(precipitationProbabilityPercent == null || precipitationProbabilityPercent in 0.0..100.0)
        require(thunderstormProbabilityPercent == null || thunderstormProbabilityPercent in 0.0..100.0)
    }
}

data class DebugSequenceStep(
    val scenarioId: DebugScenarioId,
    val sequenceTime: LocalDateTime,
)

data class DebugForecastSequence(
    val name: String,
    val steps: List<DebugSequenceStep>,
)

/** Deterministic debug-only scenarios; never used by the normal SampleForecast path. */
object DebugForecastCatalog {
    private fun time(value: String): LocalDateTime = LocalDateTime.parse(value)

    val scenarios: List<DebugForecastScenario> = listOf(
        DebugForecastScenario(DebugScenarioId.CLEAR_DAY, time("2026-10-23T12:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.CLEAR_NIGHT, time("2026-10-23T23:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.PARTLY_DAY, time("2026-10-24T12:00"), DebugCondition.PARTLY_CLOUDY, null, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.CLOUDY_NIGHT, time("2026-10-24T23:00"), DebugCondition.CLOUDY, 20.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.RAIN_AMOUNT, time("2026-10-25T12:00"), DebugCondition.RAIN, 60.0, DebugPrecipitationType.RAIN, PrecipitationAmount.RainLiquidMm(0.8), null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.RAIN_UNKNOWN_AMOUNT, time("2026-10-25T18:00"), DebugCondition.RAIN, 70.0, DebugPrecipitationType.RAIN, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.SNOW_DAY, time("2026-10-26T12:00"), DebugCondition.SNOW, 80.0, DebugPrecipitationType.SNOW, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.SNOW_NIGHT, time("2026-10-26T23:00"), DebugCondition.SNOW, 80.0, DebugPrecipitationType.SNOW, null, null, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.STORM_LOW_THUNDER, time("2026-10-27T12:00"), DebugCondition.STORM, 20.0, DebugPrecipitationType.NONE, null, 27.0, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.STORM_HIGH_THUNDER, time("2026-10-27T13:00"), DebugCondition.STORM, 70.0, DebugPrecipitationType.RAIN, null, 90.0, DebugWarningState.NONE),
        DebugForecastScenario(DebugScenarioId.STORM_WARNING_PREVIEW, time("2026-10-27T14:00"), DebugCondition.STORM, null, DebugPrecipitationType.NONE, null, null, DebugWarningState.PRESENT),
        DebugForecastScenario(DebugScenarioId.CLEAR_ENDPOINT, time("2026-10-27T15:00"), DebugCondition.CLEAR, 0.0, DebugPrecipitationType.NONE, null, null, DebugWarningState.NONE),
    )

    private val sequenceStart = time("2026-10-20T00:00")

    private fun sequence(name: String, ids: List<DebugScenarioId>) = DebugForecastSequence(
        name = name,
        steps = ids.mapIndexed { index, id -> DebugSequenceStep(id, sequenceStart.plusHours(index.toLong())) },
    )

    val sequences: List<DebugForecastSequence> = listOf(
        sequence("Cloud build, rain, clearing", listOf(DebugScenarioId.CLEAR_DAY, DebugScenarioId.PARTLY_DAY, DebugScenarioId.CLOUDY_NIGHT, DebugScenarioId.RAIN_AMOUNT, DebugScenarioId.RAIN_UNKNOWN_AMOUNT, DebugScenarioId.CLEAR_ENDPOINT)),
        sequence("Snow entry and exit", listOf(DebugScenarioId.CLOUDY_NIGHT, DebugScenarioId.SNOW_DAY, DebugScenarioId.SNOW_NIGHT, DebugScenarioId.CLEAR_ENDPOINT)),
        sequence("Graded storm and warning preview", listOf(DebugScenarioId.STORM_LOW_THUNDER, DebugScenarioId.STORM_HIGH_THUNDER, DebugScenarioId.STORM_WARNING_PREVIEW, DebugScenarioId.CLEAR_ENDPOINT)),
    )

    fun inventoryReport(): String = buildString {
        appendLine("# Debug forecast catalog inventory")
        appendLine()
        appendLine("All values are illustrative and labeled `SYNTHETIC_DEBUG`; absent values are explicitly `ABSENT`.")
        appendLine()
        appendLine("## Scenarios")
        appendLine()
        appendLine("| ID | Valid time | Condition | Precipitation probability | Precipitation type | Amount | Thunderstorm probability | Warning | Provenance |")
        appendLine("|---|---|---|---:|---|---|---:|---|---|")
        scenarios.forEach { scenario ->
            appendLine("| ${scenario.id.name.replace('_', '-')} | ${scenario.validTime} | ${scenario.condition} | ${scenario.precipitationProbabilityPercent ?: "ABSENT"}${if (scenario.precipitationProbabilityPercent == null) "" else "%"} | ${scenario.precipitationType} | ${scenario.precipitationAmount?.let(::formatAmount) ?: "ABSENT"} | ${scenario.thunderstormProbabilityPercent?.let { "$it%" } ?: "ABSENT"} | ${scenario.warningState} | ${scenario.provenance} |")
        }
        appendLine()
        appendLine("## Transition sequences")
        appendLine()
        appendLine("Sequence times start at the owner-approved synthetic local time `2026-10-20T00:00` in each sequence and advance by one hour. They are independent of scenario valid times.")
        appendLine()
        sequences.forEach { sequence ->
            appendLine("### ${sequence.name}")
            appendLine()
            appendLine("| Order | Scenario ID | Sequence time | Referenced valid time |")
            appendLine("|---:|---|---|---|")
            sequence.steps.forEachIndexed { index, step ->
                val row = scenarios.single { it.id == step.scenarioId }
                appendLine("| ${index + 1} | ${step.scenarioId.name.replace('_', '-')} | ${step.sequenceTime} | ${row.validTime} |")
            }
            appendLine()
        }
    }

    private fun formatAmount(amount: PrecipitationAmount): String = when (amount) {
        is PrecipitationAmount.RainLiquidMm -> "RainLiquidMm(${amount.value} mm)"
        is PrecipitationAmount.SnowDepthCm -> "SnowDepthCm(${amount.value} cm)"
    }
}
