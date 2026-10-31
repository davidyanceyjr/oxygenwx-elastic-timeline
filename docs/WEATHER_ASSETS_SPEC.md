# Worldwide Weather Assets — Compose Library Specification

Version: 0.1 — planning baseline, not an implemented library.

## Purpose and instructions for Codex

Build a Kotlin Android library of weather icons and illustrations using Jetpack Compose vector and drawing APIs. This document preserves the complete forecast-condition list, hazard families, regional terminology, data-model recommendations, and implementation guidance established during planning.

Treat IDs as proposed stable identifiers. Do not interpret this catalog as an exhaustive list of official worldwide warning products. Provider support and national alert criteria vary. No graphics have yet been approved or implemented. All proposed assets initially have status `planned`.

Before implementation, inspect the host project's build configuration and conventions. Keep provider normalization separate from rendering. Do not fabricate official warnings, thresholds, or unsupported weather detail. Implement a static core and catalog before animation. Preserve meteorological distinctions in text even when symbols share artwork.

## 1. Forecast conditions

All rows are eligible for daily summaries and hourly intervals when supported by the provider. Daily summaries must retain meaningful timing rather than imply an event lasts all day. Intensity, probability, coverage and duration are separate properties, not mandatory enum variants.

| Group | Semantic ID | Display name | Meaning / implementation note | Suggested visual treatment |
|---|---|---|---|---|
| Sky | clear | Clear / Sunny | Sunny in daylight; Clear at night | Sun or moon |
| Sky | mostly_clear | Mostly clear / Mostly sunny | Small amount of cloud | Sun/moon with small cloud |
| Sky | partly_cloudy | Partly cloudy | Mix of cloud and clear sky | Sun/moon partly obscured |
| Sky | mostly_cloudy | Mostly cloudy | Extensive cloud with breaks | Dominant cloud, small celestial element |
| Sky | overcast | Overcast | Essentially complete cloud cover | Cloud layers |
| Rain | drizzle | Drizzle | Fine falling water droplets | Cloud with fine short drops |
| Rain | rain | Rain | Liquid precipitation; intensity separate | Cloud with elongated drops |
| Rain | rain_showers | Rain showers | Intermittent precipitation, locally variable | Rain plus optional sky break |
| Freezing precipitation | freezing_drizzle | Freezing drizzle | Fine liquid droplets freezing on contact | Drizzle with freezing marker |
| Freezing precipitation | freezing_rain | Freezing rain | Rain freezing on surfaces | Rain with freezing marker |
| Mixed precipitation | rain_snow_mix | Rain and snow | Both reaching ground | Drops and simplified flakes |
| Mixed precipitation | wintry_mix | Wintry mix | Broad provider label; retain known components | Mixed precipitation; precise text |
| Frozen precipitation | snow | Snow | Falling crystals or flakes | Cloud and flakes |
| Frozen precipitation | snow_showers | Snow showers | Intermittent snow | Snow plus optional sky break |
| Frozen precipitation | ice_pellets | Ice pellets | Refrozen precipitation; US sleet | Compact pellet marks |
| Frozen precipitation | graupel | Graupel / Snow pellets | Soft opaque pellets, distinct from ice pellets | Pellet treatment plus explicit label |
| Frozen precipitation | hail | Hail | Ice stones associated with thunderstorms | Larger solid ice marks |
| Frozen precipitation | snow_grains | Snow grains | Very small frozen grains | Fine grain marks; specialist |
| Frozen precipitation | ice_crystals | Ice crystals / Diamond dust | Falling ice crystals in very cold conditions | Sparse crystal marks |
| Convection | thunderstorm | Thunderstorm | May coexist with rain, hail and strong winds | Cloud and lightning |
| Visibility | mist | Mist | Suspended water droplets reducing visibility | Light horizontal bands |
| Visibility | fog | Fog | More substantial visibility reduction | Dense horizontal bands |
| Visibility | freezing_fog | Freezing fog | Fog droplets may freeze onto surfaces | Fog with freezing marker |
| Visibility | ice_fog | Ice fog | Suspended ice particles in very cold air | Fog with crystal detail; precise label |
| Visibility | haze | Haze | Fine suspended particles reduce visibility | Hazy bands; avoid color-only distinction |
| Visibility | smoke | Smoke | Local or transported combustion particles | Irregular plume distinct from fog |
| Visibility | dust | Dust | Suspended or wind-raised dust | Wind lines and fine particles |
| Visibility | sand | Blowing sand | Wind-raised sand reduces visibility | Wind and coarser particles |
| Visibility | volcanic_ash | Volcanic ash | Airborne ash, environmental rather than meteorological origin | Ash plume; optional module |
| Wind / snow | blowing_snow | Blowing snow | Lifted snow reduces visibility, even without snowfall | Wind with snow particles |
| Wind / snow | drifting_snow | Drifting snow | Wind moves snow near ground | Low snow stream |
| Wind | squall | Squall | Sudden wind increase; may accompany showers/storms | Gust symbol; contextual precipitation |
| Fallback | unknown | Weather unavailable / Unknown condition | Never represent missing data as clear weather | Neutral unavailable symbol |

`unknown` is an added operational fallback, not a weather phenomenon. Distinguish unavailable observations from unmapped provider codes in application state.

## 2. Hazard and alert event catalog

Event IDs below expand the previously grouped hazard families into independently addressable meanings. These are internal suggestions, not official alert codes. A hazard is not automatically an official warning. Some optional hazards require specialist data sources.

| Family | Suggested semantic IDs | Events | Geographic relevance | Rendering / data rule |
|---|---|---|---|---|
| Heavy precipitation | heavy_rain; intense_rainfall; prolonged_rain | Heavy, intense, prolonged rain | Worldwide | Intensity and duration separate; official alert independent |
| Thunderstorms | severe_thunderstorm; dangerous_lightning; large_hail | Severe convection, lightning, large hail | Worldwide | Preserve individual threats within storm alert |
| Convective wind | damaging_gusts; downburst; microburst; straight_line_wind | Damaging storm winds | Thunderstorm regions | Short-duration hazard; may share wind symbol |
| Rotating storms | tornado; waterspout | Tornado and waterspout | Where conditions permit worldwide | Distinct from generic thunderstorm |
| Strong wind | high_wind; damaging_wind; gale; storm_force_wind | Strong land or marine winds | Worldwide, coasts, mountains | Criteria differ by authority and domain |
| Tropical systems | tropical_depression; tropical_storm; tropical_cyclone | Tropical system classifications | Tropical/subtropical basins and affected inland areas | Retain official basin classification; spiral family |
| Winter storms | heavy_snow; snowstorm; blizzard; snow_squall | Snow and combined winter hazards | Cold climates and mountains | Blizzard includes wind/visibility; local definitions |
| Ice | ice_storm; freezing_rain; freezing_drizzle; ice_accretion | Surface icing and accumulation | Cold/transitional climates | Reuse forecast IDs where meaning matches |
| Road freezing | frost; freeze; black_ice; freezing_surfaces | Frost and frozen surfaces | Cold climates, high elevations | Separate risk from observed road condition |
| Heat | extreme_heat; heatwave | Acute or prolonged heat | Worldwide | Duration and overnight temperatures matter |
| Cold | extreme_cold; cold_wave; dangerous_wind_chill | Cold exposure hazards | Worldwide | Use local thresholds; wind chill not air temperature |
| Low visibility | dense_fog; freezing_fog; smoke; blowing_snow | Hazardous visibility reduction | Worldwide | Store visibility plus cause; reuse matching IDs |
| Dust / sand | dust_storm; sandstorm; blowing_dust | Airborne mineral particles | Arid/semiarid regions and downwind | Visibility and exposure consequences |
| Rapid flooding | flash_flood; urban_flood; surface_water_flood | Rapid or surface flooding | Worldwide | Flood symbol distinct from rain |
| River flooding | river_flood; snowmelt_flood; ice_jam_flood | River and melt-related floods | River basins | Can persist without local rain |
| Coastal flooding | storm_surge; storm_tide; coastal_inundation | Coastal water-level hazards | Coastlines | Distinct from inland flooding |
| Marine conditions | high_waves; heavy_swell; hazardous_surf; rip_current | Wave and current hazards | Coasts/open waters | Avoid one symbol implying identical hazards |
| Marine icing | freezing_spray; vessel_icing | Spray freezing onto vessels | Cold seas and large lakes | Specialist marine coverage |
| Fire weather | critical_fire_weather; dangerous_fire_conditions | Weather favoring dangerous fire spread | Fire-prone regions | Does not establish an active fire |
| Slope hazards | landslide; debris_flow; avalanche | Slope failure / snow movement | Mountains and steep/saturated terrain | Specialist authority/source |
| Long-duration dryness | drought; prolonged_dry_conditions | Persistent dryness | Worldwide | Separate long-duration context |
| Environmental exposure | poor_air_quality; smoke; extreme_uv | Exposure conditions | Worldwide | Separate scales/data; smoke ID reused |
| Related natural hazards | tsunami; volcanic_ashfall | Nonmeteorological natural hazards | Relevant coasts/volcanic areas | Optional module; do not classify as weather formation |

Use one semantic definition for repeated IDs across forecast and hazard contexts. Add context and severity in records rather than creating conflicting definitions.

## 3. Regional terminology and official products

| Term / product | Region or usage | Required handling |
|---|---|---|
| Hurricane / typhoon / tropical cyclone | Different ocean basins | Shared tropical-cyclone family; preserve official term and classification |
| Sleet | US means ice pellets; elsewhere may mean rain/snow mixture | Normalize unambiguously; localize display label |
| Monsoon | Seasonal circulation in several regions | Context; represent rain, wind and flooding individually |
| Strong Monsoon Signal | Hong Kong | Preserve official local product |
| Amber / Red / Black Rainstorm Signals | Hong Kong | Preserve signal; no cross-country color equivalence |
| Yellow / Amber / Red warnings | United Kingdom | Preserve official impact-based warning and wording |
| Numbered tropical cyclone signals | Hong Kong | Preserve number, designation and instructions |
| Sheep Grazers' warnings | Australia | Specialized product, not generic severity |
| Bushwalkers alerts | Australia | Specialized local product |
| Blizzard | Cold-weather regions | Use issuing authority definition, not universal threshold |

## 4. Data separation

| Record | Fields / responsibilities |
|---|---|
| Forecast interval | Start/end, location, sky, precipitation types, intensity, probability and definition, amount, temperature, wind, visibility |
| Daily summary | Local date/time zone, high/low, precipitation totals/probabilities and definitions, peak gust, condition timeline |
| Hazard event | Family, affected area, onset/end, forecast/observed status, source, confidence |
| Official alert | Original ID, issuer, event name, headline, description, instructions, language, geometry, issued/onset/expiry times |
| Alert classification | Original level/color; normalized severity, urgency, certainty only where supplied or reliably mapped |
| Alert lifecycle | Updates, cancellations, references to previous messages |
| Provider mapping | Original provider/code, normalized ID, mapping version, unknown fallback |

Use Common Alerting Protocol (CAP) where available. CAP standardizes message exchange, not worldwide hazard thresholds. Preserve original alerts. Distinguish no active alerts from unavailable alert coverage. Never generate an official warning from app-calculated thresholds.

## 5. Asset specification schema

| Field | Requirement |
|---|---|
| semantic_id | Stable weather meaning; snake_case |
| asset_id | Stable drawing identifier; many meanings may share one asset |
| family | Sky, precipitation, visibility, wind, hazard, environmental, fallback |
| distinguishing_feature | Shape-based cue separating confusable assets |
| day_night_support | required, optional, irrelevant |
| intensity_support | Supported visual levels; do not imply unsupported precision |
| minimum_size_dp | Smallest verified size |
| small_size_fallback | Simplified asset or shared family symbol |
| label_key | Localized textual meaning; retain semantic specificity |
| implementation_status | planned, drawn, reviewed, verified |

Do not assume each semantic ID needs a unique drawing. Document shared assets and their reliance on labels. Do not claim subtle pellet types are visually self-explanatory at 24 dp.

## 6. Compose implementation recommendations

Pipeline: provider data -> normalized model -> visual resolver -> renderer.

Keep the graphics module free of network clients, provider DTOs, alert parsing and arbitrary weather thresholds. Start with one graphics module and a catalog/sample app; split optional illustrations/animation when justified.

| Facility | Recommended role |
|---|---|
| ImageVector | Static path-based symbols; reuse built definitions |
| Painter / DrawScope | Procedural graphics, parameterized geometry, larger scenes |
| Canvas | Composable surface for custom drawing |
| drawWithCache | Cache meaningful size-dependent paths/brushes; avoid per-frame rebuilding |
| graphicsLayer | Whole-graphic transformations and opacity |

Define a common 24-unit compact-icon viewport, stroke conventions, corner treatment, optical padding, cloud silhouette and precipitation placement. Verify 16/24/32/48 dp. Use separate, simplified small-size geometry when needed. Keep illustration geometry separate from compact icons.

Proposed public API shape, not implemented code:

```kotlin
WeatherIcon(
    symbol = WeatherSymbol.FreezingRain,
    contentDescription = description,
    modifier = Modifier.size(24.dp),
    palette = palette,
)

WeatherIllustration(
    scene = scene,
    contentDescription = description,
    modifier = Modifier.size(160.dp),
    motion = WeatherMotion.Static,
)
```

Require an explicit nullable contentDescription. Use localized descriptions for meaningful graphics; null when adjacent text already provides the meaning. Add semantics explicitly for custom-drawn composables. Do not announce duplicates.

Use immutable public models where practical; apply @Immutable only when the contract holds. Avoid reconstructing vector paths during each recomposition. Keep geometry helpers internal. Avoid Material-specific palette dependencies unless deliberately chosen. Multicolor assets must not be accidentally flattened by a single default tint.

Represent hazard identity and alert level separately. Shapes and text must carry meaning without color. Preserve the authority's original level and instructions. Do not automatically mirror meteorological symbols in RTL; mirror only where the graphic's semantics require it.

## 7. Delivery plan and acceptance criteria

1. Inspect project conventions and settle public IDs and mapping policy.
2. Define visual grammar and semantic-to-asset mappings.
3. Build static representative set: clear day/night, partly cloudy day/night, overcast, rain, showers, snow, mixed precipitation, freezing rain, thunderstorm, fog, wind, unknown.
4. Include confusable symbols early: fog/smoke, rain/freezing rain, snow/ice pellets.
5. Build catalog and preview matrix; review the grammar before expanding.
6. Expand to all catalog meanings, with documented shared graphics/fallbacks.
7. Verify mapping, semantics, screenshot rendering and realistic forecast-list performance.
8. Add optional larger illustrations, then restrained animation.

Release criteria:

- Every semantic condition resolves to a verified asset or documented fallback.
- Unknown or unavailable weather never becomes a sunny symbol.
- Symbols fit bounds and remain readable at declared sizes.
- Light, dark and monochrome rendering work.
- Differences never depend exclusively on hue.
- Screenshot regressions cover sizes, themes and confusing pairs in a controlled rendering environment.
- Mapping tests cover supported provider codes, unmapped codes and simultaneous conditions.
- TalkBack descriptions are meaningful and nonredundant.
- A realistic scrolling hourly list is measured on a lower-powered device.
- Official alert names, instructions and lifecycle remain intact.

## 8. Subsequent improvements

| Priority | Improvement | Benefit |
|---|---|---|
| High | Monochrome and multicolor palettes | Dense UI, dark mode and varied backgrounds |
| High | Size-specific geometry | Readable compact symbols |
| High | Published catalog with IDs/examples | Easier discovery and review |
| High | Stable IDs and version policy | Evolve art without breaking mappings |
| Medium | Optional illustration/animation module | Lightweight static consumers |
| Medium | Deterministic animation progress | Reproducible screenshots and previews |
| Medium | Explicit static/reduced-motion policy | Accessible motion control |
| Medium | Android resource export pipeline | Notifications, widgets, other non-Compose surfaces |
| Later | Compose Multiplatform compatibility | Reuse when additional platforms are required |

Default motion to static. Avoid animating every forecast-list item. Start with gentle cloud drift or precipitation; avoid continuous lightning flashes. Stop unnecessary animation when not visible. Keep randomness seeded if used. Exported artwork needs its own size/contrast checks.

## 9. Reference sources

These references informed the planning conversation. Recheck current API documentation and national products during implementation. This file does not prescribe a Compose version or universal warning threshold.

- NOAA weather phenomena: https://www.noaa.gov/jetstream/synoptic/types-of-weather-phenomena
- NWS weather glossary: https://www.weather.gov/key/glossary
- NWS observation descriptors: https://www.weather.gov/media/surface/SFCTraining.pdf
- WMO CAP: https://wmo.int/site/wmo-common-alerting-protocol
- NWS alert services: https://www.weather.gov/documentation/services-web-alerts
- UK warning guide: https://weather.metoffice.gov.uk/guides/warnings
- Australian warning products: https://www.bom.gov.au/catalogue/Bureau_of_Meteorology_warning_products_user_guide.pdf
- Japan warning services: https://www.jma.go.jp/jma/en/Activities/forecast.html
- Hong Kong warning symbols: https://www.hko.gov.hk/en/wxinfo/dailywx/warnlegend.htm
- Compose graphics: https://developer.android.com/develop/ui/compose/graphics/draw/overview
- Compose graphics modifiers: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers
- Compose image formats: https://developer.android.com/develop/ui/compose/graphics/images/compare
- Compose accessibility: https://developer.android.com/develop/ui/compose/accessibility/api-defaults
- Compose stability: https://developer.android.com/develop/ui/compose/performance/stability

## 10. Suggested Codex handoff

Read WEATHER_ASSETS_SPEC.md, inspect this project's Gradle and Compose conventions, and implement the static weather-assets library plus a browsable catalog. Begin with the representative core set, establish consistent geometry and semantic mappings, and document shared symbols and fallbacks. Preserve all catalog meanings in the model. Keep provider adapters and official-alert ingestion outside the drawing library. Report implemented coverage, verification performed, and remaining planned assets accurately.
