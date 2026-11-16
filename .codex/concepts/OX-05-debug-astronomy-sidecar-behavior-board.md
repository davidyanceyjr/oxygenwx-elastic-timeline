# OX-05 revised behavior board — debug astronomy sidecar

**Approval status:** Draft for product-owner review. This revised package is not approved and does not authorize implementation. It supersedes the earlier OX-05 board target only if the owner approves this package and its scope.

**Cycle:** `144-ox05-depth-ordering` · **Slice:** `OX-05`

## Objective

Complete the approved First City depth-ordering proof through the installed primary catalog composition. Add one deterministic debug review fixture that supplies authored solar and lunar scene inputs alongside the rain state. The fixture makes the existing overlap states reachable without changing what provider daily summaries mean.

## Debug-only review route

`MainActivity` debug session (`#DEBUG`) → `DebugCatalogActivity` → **Load OX-05 review fixture** → primary `CatalogTimeLabScreen` → `CatalogTimelineScenePresentation` → `FirstCityComposition`.

The route uses a fixed debug `CatalogLocation` (`debug:ox05-depth-review-chicago`, 41.8781° N, 87.6298° W, `America/Chicago`) and the `SIMULATED` storage namespace. It is available even with no saved user locations and makes no network request. It activates through the existing catalog activation owner. It does not add the fixture location to saved user locations.

## Fixture data contract

- The canonical weather recording is deterministic and `SIMULATED`. It carries the two target weather states at fixed hourly instants.
- Solar and lunar values live in a separate debug-source-set sidecar with explicit origin `AUTHORED_SYNTHETIC_REVIEW_INPUT`. They are not written into `CanonicalCatalog.dailySummaries`, `ProviderDailySummary`, or the recorded-catalog schema.
- A default no-op `ActivityVariantDelegate` presentation hook lets the debug build apply the sidecar only for the OX-05 fixture dataset. It adds that selected day's `SolarDayEvents`, `LunarDayFacts`, lunar rise/set events, and the matching local daylight phase to a copy of the evaluated `SceneReading`, then recomposes `FirstCityComposition`. It preserves the timeline, selected instant, weather fields, and diagnostics. The release delegate remains a no-op.
- The debug presentation identifies the data as **DEMO DATA · OX-05 authored synthetic astronomy fixture**. The event values are deliberately authored to demonstrate rendering overlap; they are not observations, forecasts, or calculated astronomy.
- `ProviderDailySummary` remains provider-only. The live mapper, catalog codec, provider behavior, and all non-fixture datasets remain unchanged.

## Fixed fixture and reviewed states

Location/time zone: `debug:ox05-depth-review-chicago`, 41.8781° N, 87.6298° W, `America/Chicago`. Dataset ID: `ox05-depth-review-synthetic-astronomy-v1`, stored in the `SIMULATED` namespace. Catalog `asOf`: `2026-10-28T12:00:00Z`. The review dates are outside the Chicago daylight-saving transition.

Both sidecar day records contain the same deliberately synthetic event values:

| Local date | Sunrise | Sunset | Moonrise | Moonset | Phase |
|---|---:|---:|---:|---:|---:|
| 2026-10-28 | 06:00 CDT (`11:00Z`) | 18:00 CDT (`23:00Z`) | 04:30 CDT (`09:30Z`) | 17:30 CDT (`22:30Z`) | 0.50 |
| 2026-10-29 | 06:00 CDT (`11:00Z`) | 18:00 CDT (`23:00Z`) | 04:30 CDT (`09:30Z`) | 17:30 CDT (`22:30Z`) | 0.50 |

1. **Celestial overlap:** select `2026-10-28T17:00:00Z` (12:00 CDT); supplied synthetic weather inputs are `CLEAR` and 0% cloud cover. The current cue mapping yields Sun center `(0.500, 0.180)` and Moon center approximately `(0.558, 0.187)`. Their artwork overlaps, and Moon is painted after Sun within Background.
2. **Moderate-rain depth:** select `2026-10-29T17:00:00Z` (12:00 CDT); supplied synthetic weather inputs are `RAIN` and 88% cloud cover, matching the existing focused rendering case. That date still contains all four solar/lunar event values and phase 0.50. Existing rain semantics suppress the Sun cue; nearer cloud layers may cover the Moon; the single Foreground precipitation field crosses in front of near buildings.

No rain amount/rate is asserted by the procedural field. These fixture values are selected rendering inputs, not meteorological claims.

## Ordering and invariants

- Physical draw order remains Background → Middle-Ground → Foreground, with the accepted local sublayer order: outer space → stars → Sun → Moon → Background clouds → background architecture; atmosphere → Middle-Ground clouds; near architecture → canopy → window lights → the single precipitation field.
- `TIME_WASH` and `READOUT_QUIET_ZONE` retain their separate compositing semantics.
- Preserve OX-03 single cloud/precipitation decisions and field origin, OX-04 support/source-time/unavailable semantics, exact selected-instant authority, accessible forecast text, and timeline gesture ownership.
- The sidecar supplies only authored astronomical scene inputs. It does not infer weather, validate astronomical plausibility, move selection, or correct supplied weather values.
- Only the tagged debug fixture receives the sidecar. No production theme, weather interpretation, provider data, live route, or saved-location behavior changes.

## Acceptance and evidence

- Focused order assertions retain Background < Middle-Ground < Foreground and Sun < Moon < nearer cloud layers; the Foreground rain field remains after near architecture.
- A focused debug-projection check proves the sidecar is applied only to the fixture dataset, preserves the selected instant and weather reading, and yields the specified overlap positions at the clear sample.
- Installed primary-route review loads the fixture without network/location setup, selects both exact instants, confirms the DEMO DATA provenance label, and captures the celestial overlap and moderate-rain depth states under `.codex/test-artifacts/144-ox05-depth-ordering/`.
- Screenshots are required for visual claims. Focus only on these in-scope states and interactions; no RTL, TalkBack, or broad system matrix is added to this design slice.

## Boundaries

Debug review route, fixed simulated weather recording, authored astronomy sidecar, and its debug presentation adapter are included only to make OX-05 installed acceptance deterministic. No changes to the canonical/provider daily-summary contract or recording schema; no live/network/location-search changes; no forecast layout, readout, timeline, or gesture redesign; no OX-06 registry or renderer extraction; no work in deprecated oxygenWX.

## Source anchors

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/ProviderDailySummary.kt` — provider-only daily-summary invariant.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt` — selected reading, solar events, lunar day, and lunar events.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` — cue positions, treatment, visibility, and local layer order.
- `app/src/main/java/com/oxygen/weather/timeline/ActivityVariantDelegate.kt` — optional build-variant behavior boundary.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` and `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — primary composition route.
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugCatalogActivity.kt` and `CatalogPickerDelegate.kt` — existing debug session and catalog activation flow.
- `.codex/concepts/OX-05-make-depth-ordering-enforceable-behavior-board.*` and `OX-05-make-depth-ordering-enforceable-ui-board.*` — earlier approved depth target, to be replaced only after this revision is approved.
