# SG.11 weather-event mapping — draft behavior contract

**Status:** Product owner approved both SG.11 boards on 2026-11-04. Approval covers the original source/recording flow, named ledger, and proposed code-to-icon matches, including codes 48, 77, and 97. The owner subsequently approved the fixed multiple-event fallback hierarchy in conversation on 2026-11-04; the complete deterministic order below records that amendment. The owner clarified that forecast wording must be deterministic: Open-Meteo code descriptions come from the documented code ledger, not the response body, and display wording comes from the selected WA identity. The owner also clarified that captured raw response data is the input from which the catalog is generated; the scene builder consumes the resulting 44-date catalog. The owner approved this corrected SG.11 direction on 2026-11-04: the saved raw response heads the event-mapping path, while loading older raw captures through DEBUG is outside this slice.

## Purpose and named table

The **Open-Meteo Code → WA Event Ledger** is the reviewed source-semantic contract. Each row records the numeric `weather_code`, Open-Meteo's documented wording, the WA event ID chosen for the supplied hourly fact, the symbol/accessibility wording, and any deliberate loss of specificity. The ledger prevents the mapper from deriving events from precipitation, wind, visibility, or display strings. The approved `Oxygen_forecast_weather_symbol_board.png` and `WA.1-weather-symbol-library-behavior-board.md` define the existing artwork and IDs; this ledger joins source meanings to them.

Open-Meteo's Forecast API supplies numeric weather codes, not description strings. Its published weather-code table supplies the wording below: https://open-meteo.com/en/docs . A live response can show which codes occurred, but cannot enumerate all possible codes or provide their descriptions. The adapter uses a fixed exact-code ledger to derive a documented code description and stores it as a separate catalog event field with provenance identifying the documentation mapping. This description is distinct from provider-supplied text and from the deterministic forecast/accessibility label selected by WA event ID.

## Source and recording flow

```text
LIVE Open-Meteo request → save exact bounded raw response bytes with request identity
    → parse that saved capture and map its codes through the exact-code catalog ledger
    → canonical 44-local-date catalog: D-30..D-1 history; D..D+13 forecast
    → scene builder and selected-time forecast projection consume the catalog
    → scene plus matching WA symbol ID and deterministic accessible event name

Legacy canonical-only recording → existing decode/replay path → canonical catalog → scene/view
```

During development, the recording retains the exact response with provider,
retrieval time, request identity, and our capture-contract version alongside
the generated canonical catalog. The saved raw response is the input to event
mapping while it is retained. Scene builder and view never parse raw JSON.
The completed production catalog still contains the fields needed for normal
rendering if raw-response retention is later removed.
The 44-date target does not claim complete hourly coverage across gaps or DST.
Older recordings without raw response or source code remain readable with their broad facts.
The raw response, retained code, and derived event belong to the same captured fetch.

`CONDITION_UNAVAILABLE` is a presentation fallback, never a supplied event. The other 47 WA IDs are representable when explicitly supplied by a source even where Open-Meteo has no matching code. Preserve wording actually supplied by other sources when available; never label the Open-Meteo documentation description as text supplied in a response. Forecast and accessible wording are fixed by the selected WA identity, not by raw JSON, an optional source phrase, or collection order. Unknown/unsupported codes never become clear weather.

## Open-Meteo Code → WA Event Ledger (draft)

| Code | Documented meaning | Proposed WA ID | Review note |
|---|---|---|---|
| 0 | Clear sky | `SUNNY` / `CLEAR_NIGHT` | Select by supplied Sun times. |
| 1 | Mainly clear | `MOSTLY_SUNNY` / `MOSTLY_CLEAR_NIGHT` | “Mainly” mapped to “mostly”; review wording. |
| 2 | Partly cloudy | `PARTLY_SUNNY_CLOUDY` / `PARTLY_CLOUDY_NIGHT` | Select by supplied Sun times. |
| 3 | Overcast | `CLOUDY_OVERCAST` | Same artwork across day/night. |
| 45 | Fog | `FOG` | Direct. |
| 48 | Depositing rime fog | `FREEZING_FOG` | Owner approved this visual/wording match on 2026-11-04; retain the documented code description. |
| 51, 53, 55 | Light, moderate, dense drizzle | `DRIZZLE` | Preserve exact code and documented intensity. |
| 56, 57 | Light, dense freezing drizzle | `FREEZING_DRIZZLE` | Direct event family. |
| 61, 63 | Slight, moderate rain | `RAIN` | Preserve exact code and documented intensity. |
| 65 | Heavy rain | `HEAVY_RAIN` | Direct. |
| 66, 67 | Light, heavy freezing rain | `FREEZING_RAIN` | Preserve exact code and documented intensity. |
| 71, 73 | Slight, moderate snowfall | `SNOW` | Preserve exact code and documented intensity. |
| 75 | Heavy snowfall | `HEAVY_SNOW` | Direct. |
| 77 | Snow grains | `FLURRIES_SNOW_GRAINS` | Owner approved this visual/wording match on 2026-11-04; use “Snow grains” as the documented code description. |
| 80, 81, 82 | Slight, moderate, violent rain showers | `RAIN_SHOWERS` | Preserve exact code and documented intensity. |
| 85, 86 | Slight, heavy snow showers | `SNOW_SHOWERS` | Preserve exact code and documented intensity. |
| 95 | Thunderstorm | `THUNDERSTORM` | Do not add rain claim. |
| 96, 99 | Thunderstorm with slight/heavy hail | `THUNDERSTORM_WITH_HAIL` | Preserve hail intensity in the documented code description. |
| 97 | Heavy thunderstorm | `THUNDERSTORM` | Owner approved this broader icon match on 2026-11-04; adapter does not yet accept code 97. No heavy-storm icon; retain the documented code description. |
| Other / missing | No supported documented event | `CONDITION_UNAVAILABLE` | Presentation fallback; retain raw code for review. |

The exact-code implementation ledger must expand grouped rows before coding, including each currently accepted code and code 97 if added. The adapter currently accepts 0, 1, 2, 3, 45, 48, 51–67 within listed families, 71–77 within listed families, 80–82, 85–86, 95, 96, and 99; its range branches also accept undocumented intermediate numbers as generic rain/snow. The implementation contract must explicitly mark those intermediate values unsupported rather than preserve that accidental range behavior.

### Exact-code implementation ledger — SG.11a checkpoint

Open-Meteo's [current Forecast API code table](https://open-meteo.com/en/docs) lists exactly the following codes. The current adapter's range fallback also accepts the intermediate numbers listed after the table; they have no documented code description and are unsupported in the typed mapping. SG.11a implements only the Sky assignments (0–3). Other documented rows retain their existing broad scene mapping until their own row slice.

| Exact code | Documented code description | Approved WA identity | Row |
|---|---|---|---|
| 0 | Clear sky | `SUNNY` / `CLEAR_NIGHT` | Sky |
| 1 | Mainly clear | `MOSTLY_SUNNY` / `MOSTLY_CLEAR_NIGHT` | Sky |
| 2 | Partly cloudy | `PARTLY_SUNNY_CLOUDY` / `PARTLY_CLOUDY_NIGHT` | Sky |
| 3 | Overcast | `CLOUDY_OVERCAST` | Sky |
| 45 | Fog | `FOG` | Visibility and wind |
| 48 | Depositing rime fog | `FREEZING_FOG` | Visibility and wind |
| 51 | Light drizzle | `DRIZZLE` | Rain and ice |
| 53 | Moderate drizzle | `DRIZZLE` | Rain and ice |
| 55 | Dense drizzle | `DRIZZLE` | Rain and ice |
| 56 | Light freezing drizzle | `FREEZING_DRIZZLE` | Rain and ice |
| 57 | Dense freezing drizzle | `FREEZING_DRIZZLE` | Rain and ice |
| 61 | Slight rain | `RAIN` | Rain and ice |
| 63 | Moderate rain | `RAIN` | Rain and ice |
| 65 | Heavy rain | `HEAVY_RAIN` | Rain and ice |
| 66 | Light freezing rain | `FREEZING_RAIN` | Rain and ice |
| 67 | Heavy freezing rain | `FREEZING_RAIN` | Rain and ice |
| 71 | Slight snowfall | `SNOW` | Snow and mixed precipitation |
| 73 | Moderate snowfall | `SNOW` | Snow and mixed precipitation |
| 75 | Heavy snowfall | `HEAVY_SNOW` | Snow and mixed precipitation |
| 77 | Snow grains | `FLURRIES_SNOW_GRAINS` | Snow and mixed precipitation |
| 80 | Slight rain showers | `RAIN_SHOWERS` | Rain and ice |
| 81 | Moderate rain showers | `RAIN_SHOWERS` | Rain and ice |
| 82 | Violent rain showers | `RAIN_SHOWERS` | Rain and ice |
| 85 | Slight snow showers | `SNOW_SHOWERS` | Snow and mixed precipitation |
| 86 | Heavy snow showers | `SNOW_SHOWERS` | Snow and mixed precipitation |
| 95 | Thunderstorm | `THUNDERSTORM` | Storm and fallback |
| 96 | Thunderstorm with slight hail | `THUNDERSTORM_WITH_HAIL` | Storm and fallback |
| 97 | Heavy thunderstorm | `THUNDERSTORM` | Storm and fallback |
| 99 | Thunderstorm with heavy hail | `THUNDERSTORM_WITH_HAIL` | Storm and fallback |

Unsupported intermediate values previously swept in by broad branches: `52`, `54`, `58`, `59`, `60`, `62`, `64`, `72`, `74`, and `76`. Values `68`–`70` and all other unlisted codes, including `78`, `79`, `83`, `84`, `87`–`94`, and `98`, are also unsupported. A missing or unsupported code yields no typed supplied event; its raw numeric value remains inspectable where supplied.

## Day/night and multiple events

For a broad clear or partly cloudy source event, the selected local date's supplied sunrise and sunset set the symbol variant: day from sunrise minus one hour inclusive to sunset minus one hour exclusive; night at other instants on that date. Dawn is day and dusk is night for this symbol choice. Explicit source event identities remain explicit. This symbol rule does not alter the scene's light-phase timing or add a new missing-Sun validation scenario.

Owner execution clarification: if either Sun event is absent for Open-Meteo Sky code 0, 1, or 2, keep the raw code and broad scene input but supply no typed event; the typed symbol is unavailable. The owner selected this bounded missing-Sun behavior on 2026-11-04. Code 3 does not require a day/night choice.

One Open-Meteo hourly code produces one primary mapped event. Other typed sources may supply several events: retain all supplied events and any wording actually supplied; a source-designated primary event selects the display symbol. Without a designated primary, select the first supplied ID in this fixed, highest-first order (the order within each row is also binding):

| Priority | Stable IDs, highest first |
|---|---|
| Combined events | `THUNDERSTORM_WITH_HAIL`, `THUNDERSTORM_WITH_SNOW`, `THUNDERSTORM_WITH_RAIN`, `WINTRY_MIX`, `RAIN_AND_SNOW` |
| Hazardous or intense | `TORNADO_FUNNEL`, `TROPICAL_CYCLONE`, `TROPICAL_STORM`, `WATERSPOUT`, `BLIZZARD`, `HEAVY_SNOW`, `HEAVY_RAIN`, `FREEZING_RAIN`, `FREEZING_DRIZZLE`, `FREEZING_FOG`, `SQUALL`, `DUST_SANDSTORM`, `VOLCANIC_ASH`, `EXTREME_HEAT`, `EXTREME_COLD`, `FROST` |
| Other precipitation and storms | `DRY_THUNDERSTORM`, `THUNDERSTORM`, `HAIL`, `ICE_PELLETS`, `GRAUPEL`, `SNOW_SHOWERS`, `SNOW`, `FLURRIES_SNOW_GRAINS`, `ICE_CRYSTALS`, `RAIN_SHOWERS`, `RAIN`, `DRIZZLE` |
| Visibility and wind | `BLOWING_SNOW`, `WINDY_GUSTY`, `FOG`, `MIST`, `HAZE_SMOKE`, `DUST_SAND_ASH` |
| Sky | `CLOUDY_OVERCAST`, `MOSTLY_CLOUDY`, `PARTLY_SUNNY_CLOUDY`, `PARTLY_CLOUDY_NIGHT`, `MOSTLY_SUNNY`, `MOSTLY_CLEAR_NIGHT`, `SUNNY`, `CLEAR_NIGHT` |

The order chooses only one display symbol and its fixed forecast/accessibility label; all supplied events, available source wording, documented code descriptions, and provenance remain intact. `CONDITION_UNAVAILABLE` is excluded because it cannot be supplied. Neither source iteration order nor a measurement chooses the fallback. For example, supplied `FOG` and `RAIN` displays `RAIN` while retaining both events.

## Review and acceptance

The board SVG shows the flow and ownership boundary. The UI artwork board pairs the proposed mapping with the approved icon sheet and representative source-code states. Product-owner board approval and the subsequent fallback-order approval are recorded above. Installed evidence later checks the 47 supplyable identities plus the unavailable fallback, canonical/recorded round-trip, legacy recording compatibility, code coverage, and each symbol state. No tests are authorized by this board review.
