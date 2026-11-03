# WA.1 — Weather symbol catalog behavior board

Status: product-owner approved 2026-11-03.

## Purpose

Define the shared contract that maps each of the 48 normalized weather-event IDs on `Oxygen_forecast_weather_symbol_board.png` to its catalog symbol. Artwork is delivered in six ordered implementation slices, one concept-board row per slice. The first slice establishes the reusable static Compose symbol API; later slices add the remaining row entries.

## Flow

```text
Normalized weather-event ID ───────────> resolve catalog entry ──> draw its symbol at requested bounds
                 │                              │
                 │                              └── shared artwork is allowed when labels retain distinct meanings
                 ├── unknown event ID ─────────> unknown symbol; retain supplied text label when present
                 └── unavailable condition ────> unavailable symbol; never sunny/clear
```

The public Compose entry point receives a stable symbol ID, caller-provided modifier/size, palette/style inputs, and optional content description. It owns no provider DTOs, weather thresholds, forecast state, or event timing. Provider adapters normalize their own descriptions/codes into event IDs outside the graphics catalog. Meaningful neighboring text may suppress duplicate spoken semantics.

## Scope boundary

- Provide static, scalable artwork and a stable semantic catalog for all 48 symbols shown on the concept board, delivered in six row-based slices.
- Keep semantic IDs distinct even when artwork is shared; retain the original forecast label for distinctions that cannot be read reliably at small sizes.
- Include explicit `unknown` and `unavailable` behavior; neither resolves to a clear-weather glyph.
- Keep hazard/alert identity separate from official warning level, issuer, or instructions. This catalog does not create official alerts or infer hazard thresholds.
- Each catalog ID is a supported visual event regardless of whether a current provider supplies it. This does not authorize fabricating a forecast event, adding a provider, or inferring unsupported weather facts.
- This is a static-symbol slice. Animation, forecast/provider expansion, alert ingestion, and broad UI redesign are out of scope.

## Row sequence

1. **WA.1 Sky:** Sunny; Clear night; Mostly sunny; Mostly clear night; Partly sunny/cloudy; Partly cloudy night; Mostly cloudy; Cloudy/overcast.
2. **WA.2 Rain + ice:** Drizzle; Rain; Heavy rain; Rain showers; Freezing drizzle; Freezing rain; Ice pellets/sleet; Hail.
3. **WA.3 Snow + mixed precipitation:** Flurries/snow grains; Snow; Heavy snow; Snow showers; Rain and snow/sleet; Wintry mix; Graupel; Ice crystals.
4. **WA.4 Visibility + wind:** Mist; Fog; Freezing fog; Haze/smoke; Dust/sand/ash; Windy/gusty; Blowing snow; Blizzard.
5. **WA.5 Storms + fallback:** Thunderstorm; Thunderstorm with rain; Thunderstorm with hail; Thunderstorm with snow; Squall; Tropical cyclone; Tornado/funnel; Condition unavailable.
6. **WA.6 Additional events:** Dry thunderstorm; Tropical storm; Waterspout; Frost; Extreme heat; Extreme cold; Dust/sandstorm; Volcanic ash.

“Sleet” follows the provider's defined event meaning (ice pellets or rain/snow mixture); the normalized event ID must be unambiguous. Similar artwork does not collapse distinct IDs or labels.

## Artwork reference

The product owner approved `Oxygen_forecast_weather_symbol_board.png` on 2026-11-03 as the UI artwork board for the six-row WA sequence. The product owner approved this behavior board on 2026-11-03 as the shared asset-mapping contract. The source image's proposed label and slice-ID-pending footer are superseded by these recorded decisions.

## Acceptance focus

- Every catalog ID resolves deterministically to documented scalable artwork or a documented shared asset.
- Symbols render within caller-provided bounds at compact and larger sizes without changing semantic identity.
- Unknown and unavailable remain visually distinct from clear; descriptions preserve meaning and do not redundantly announce adjacent text.
- Each of the 48 normalized event IDs resolves through an explicit catalog mapping; symbol availability does not imply that a provider supplied that event.
- The graphics API remains reusable without screen coordinates baked into semantic IDs.

## Product-owner approval and constraints

Approved on 2026-11-03. This board defines normalized event-ID to symbol mapping for all 48 catalog entries and the six row-based WA implementation sequence. Artwork approval applies to the concept board's visual direction; individual slice plans still define their row's installed acceptance. Shared drawings are allowed if semantic IDs, labels, and any required distinctions remain intact. All symbols can be represented by the catalog even when a current provider does not supply that event; the app must not invent an event or claim provider coverage. This approval does not include official-alert interpretation, thresholds, provider ingestion, or animation.
