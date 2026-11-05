package com.oxygen.weather.timeline.data.catalog

/** Stable source-event identity. CONDITION_UNAVAILABLE is deliberately absent. */
enum class WeatherEventId {
    SUNNY, CLEAR_NIGHT, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT,
    PARTLY_SUNNY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, CLOUDY_OVERCAST,
    DRIZZLE, RAIN, HEAVY_RAIN, RAIN_SHOWERS, FREEZING_DRIZZLE, FREEZING_RAIN, ICE_PELLETS, HAIL,
    FLURRIES_SNOW_GRAINS, SNOW, HEAVY_SNOW, SNOW_SHOWERS, RAIN_AND_SNOW, WINTRY_MIX, GRAUPEL, ICE_CRYSTALS,
    MIST, FOG, FREEZING_FOG, HAZE_SMOKE, DUST_SAND_ASH, WINDY_GUSTY, BLOWING_SNOW, BLIZZARD,
    THUNDERSTORM, THUNDERSTORM_WITH_RAIN, THUNDERSTORM_WITH_HAIL, THUNDERSTORM_WITH_SNOW,
    SQUALL, TROPICAL_CYCLONE, TORNADO_FUNNEL,
    DRY_THUNDERSTORM, TROPICAL_STORM, WATERSPOUT, FROST,
    EXTREME_HEAT, EXTREME_COLD, DUST_SANDSTORM, VOLCANIC_ASH,
}

/** Source wording and documentation wording remain distinct from the forecast label. */
data class WeatherEventFact(
    val id: WeatherEventId,
    val sourceCode: Int? = null,
    val suppliedWording: String? = null,
    val documentedCodeDescription: String? = null,
    val provider: String,
    val descriptionSource: String? = null,
) {
    init {
        require(provider.isNotBlank())
        require(sourceCode == null || sourceCode in 0..99)
        require(suppliedWording == null || suppliedWording.isNotBlank())
        require(documentedCodeDescription == null || documentedCodeDescription.isNotBlank())
        require(descriptionSource == null || descriptionSource.isNotBlank())
        require((documentedCodeDescription == null) == (descriptionSource == null))
    }
}

/** The order is an owner-approved deterministic fallback, independent of event-list order. */
object WeatherEventPriority {
    val highestFirst = listOf(
        WeatherEventId.THUNDERSTORM_WITH_HAIL, WeatherEventId.THUNDERSTORM_WITH_SNOW,
        WeatherEventId.THUNDERSTORM_WITH_RAIN, WeatherEventId.WINTRY_MIX, WeatherEventId.RAIN_AND_SNOW,
        WeatherEventId.TORNADO_FUNNEL, WeatherEventId.TROPICAL_CYCLONE, WeatherEventId.TROPICAL_STORM,
        WeatherEventId.WATERSPOUT, WeatherEventId.BLIZZARD, WeatherEventId.HEAVY_SNOW,
        WeatherEventId.HEAVY_RAIN, WeatherEventId.FREEZING_RAIN, WeatherEventId.FREEZING_DRIZZLE,
        WeatherEventId.FREEZING_FOG, WeatherEventId.SQUALL, WeatherEventId.DUST_SANDSTORM,
        WeatherEventId.VOLCANIC_ASH, WeatherEventId.EXTREME_HEAT, WeatherEventId.EXTREME_COLD,
        WeatherEventId.FROST, WeatherEventId.DRY_THUNDERSTORM, WeatherEventId.THUNDERSTORM,
        WeatherEventId.HAIL, WeatherEventId.ICE_PELLETS, WeatherEventId.GRAUPEL,
        WeatherEventId.SNOW_SHOWERS, WeatherEventId.SNOW, WeatherEventId.FLURRIES_SNOW_GRAINS,
        WeatherEventId.ICE_CRYSTALS, WeatherEventId.RAIN_SHOWERS, WeatherEventId.RAIN,
        WeatherEventId.DRIZZLE, WeatherEventId.BLOWING_SNOW, WeatherEventId.WINDY_GUSTY,
        WeatherEventId.FOG, WeatherEventId.MIST, WeatherEventId.HAZE_SMOKE,
        WeatherEventId.DUST_SAND_ASH, WeatherEventId.CLOUDY_OVERCAST,
        WeatherEventId.MOSTLY_CLOUDY, WeatherEventId.PARTLY_SUNNY_CLOUDY,
        WeatherEventId.PARTLY_CLOUDY_NIGHT, WeatherEventId.MOSTLY_SUNNY,
        WeatherEventId.MOSTLY_CLEAR_NIGHT, WeatherEventId.SUNNY, WeatherEventId.CLEAR_NIGHT,
    )
    private val ranks = highestFirst.withIndex().associate { it.value to it.index }
    init { require(highestFirst.toSet() == WeatherEventId.entries.toSet() && highestFirst.size == WeatherEventId.entries.size) }

    fun select(events: List<WeatherEventFact>, primaryEventIndex: Int?): WeatherEventFact? =
        primaryEventIndex?.let(events::get) ?: events.minByOrNull { ranks.getValue(it.id) }
}
