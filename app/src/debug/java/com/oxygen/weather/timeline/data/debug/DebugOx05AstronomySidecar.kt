package com.oxygen.weather.timeline.data.debug

import com.oxygen.weather.timeline.data.catalog.CatalogOrigin
import com.oxygen.weather.timeline.data.catalog.DatasetId
import com.oxygen.weather.timeline.presentation.CatalogTimelineScenePresentation
import com.oxygen.weather.timeline.presentation.scene.FirstCityTheme
import com.oxygen.weather.timeline.presentation.scene.LightPhase
import com.oxygen.weather.timeline.presentation.scene.LunarDayFacts
import com.oxygen.weather.timeline.presentation.scene.LunarEvent
import com.oxygen.weather.timeline.presentation.scene.LunarEventKind
import com.oxygen.weather.timeline.presentation.scene.SolarDayEvents
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

internal enum class AstronomyInputOrigin { AUTHORED_SYNTHETIC_REVIEW_INPUT }

internal data class AuthoredSyntheticAstronomyInput(
    val origin: AstronomyInputOrigin,
    val date: LocalDate,
    val sunrise: Instant,
    val sunset: Instant,
    val moonrise: Instant,
    val moonset: Instant,
    val moonPhase: Double,
)

/** Debug-only, authored event inputs for the two exact OX-05 review dates. */
internal object DebugOx05AstronomySidecar {
    const val LABEL = "DEMO DATA · OX-05 authored synthetic astronomy fixture"
    private val zone = ZoneId.of("America/Chicago")

    private val inputs = listOf(
        input(LocalDate.parse("2026-10-28")),
        input(LocalDate.parse("2026-10-29")),
    ).associateBy { it.date }

    fun inputFor(date: LocalDate): AuthoredSyntheticAstronomyInput? = inputs[date]

    fun apply(
        datasetId: DatasetId,
        origin: CatalogOrigin,
        locationZone: ZoneId,
        presentation: CatalogTimelineScenePresentation,
    ): CatalogTimelineScenePresentation {
        if (origin != CatalogOrigin.SIMULATED || datasetId.value != DebugOx05DepthOrderingFixture.DATASET_ID) {
            return presentation
        }

        val original = presentation.reading
        val input = inputFor(original.selectedInstant.atZone(locationZone).toLocalDate()) ?: return presentation
        val solarEvents = SolarDayEvents(input.date, input.sunrise, input.sunset)
        val lunarDay = LunarDayFacts(input.date, input.moonPhase, input.moonrise, input.moonset)
        val reading = original.copy(
            lightInterval = original.lightInterval.copy(phase = lightPhaseAt(original.selectedInstant, input)),
            solarEvents = solarEvents,
            lunarDay = lunarDay,
            lunarEvents = listOf(
                LunarEvent(input.date, LunarEventKind.RISE, input.moonrise),
                LunarEvent(input.date, LunarEventKind.SET, input.moonset),
            ),
        )
        return presentation.copy(
            reading = reading,
            composition = FirstCityTheme.compose(reading, locationZone),
        )
    }

    private fun input(date: LocalDate) = AuthoredSyntheticAstronomyInput(
        origin = AstronomyInputOrigin.AUTHORED_SYNTHETIC_REVIEW_INPUT,
        date = date,
        sunrise = date.atTime(LocalTime.of(6, 0)).atZone(zone).toInstant(),
        sunset = date.atTime(LocalTime.of(18, 0)).atZone(zone).toInstant(),
        moonrise = date.atTime(LocalTime.of(4, 30)).atZone(zone).toInstant(),
        moonset = date.atTime(LocalTime.of(17, 30)).atZone(zone).toInstant(),
        moonPhase = 0.50,
    )

    private fun lightPhaseAt(instant: Instant, input: AuthoredSyntheticAstronomyInput): LightPhase {
        val dawnStart = input.sunrise.minusSeconds(45 * 60L)
        val dawnEnd = input.sunrise.plusSeconds(45 * 60L)
        val duskStart = input.sunset.minusSeconds(45 * 60L)
        val duskEnd = input.sunset.plusSeconds(45 * 60L)
        return when {
            instant < dawnStart || instant >= duskEnd -> LightPhase.NIGHT
            instant < dawnEnd -> LightPhase.DAWN
            instant < duskStart -> LightPhase.DAY
            else -> LightPhase.DUSK
        }
    }
}
