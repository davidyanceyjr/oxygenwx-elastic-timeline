# OX-05 cycle 144 scope revision — owner review draft

**Status:** Owner-approved on 2026-11-16 and merged into `.codex/plans/144-ox05-depth-ordering.md`. Retained as the exact scope revision record. Cycle `144-ox05-depth-ordering` remains paused only until the refreshed board fingerprints and checkpoint re-review are recorded.

## Owner direction recorded

The owner selected a deterministic astronomy-capable fixture and then selected option 1: keep authored solar/lunar review values in an explicit debug-only sidecar, preserve `ProviderDailySummary` as provider-sourced, and adapt the scene projection for the fixture.

## Proposed objective

Prove the accepted First City physical depth order on the installed primary catalog composition, with a deterministic debug-only fixture that presents the approved celestial overlap and moderate-rain near-building overlap.

## Proposed scope change

Add only the debug/test route and data adapter needed for review:

1. Build a fixed SIMULATED Chicago review recording at `CatalogLocation(LocationId("debug:ox05-depth-review-chicago"), GeoCoordinates(41.8781, -87.6298), ZoneId.of("America/Chicago"))`, under dataset ID `ox05-depth-review-synthetic-astronomy-v1` in the `SIMULATED` storage namespace, using fixed `asOf` `2026-10-28T12:00:00Z`. The recording contains weather points for two reviewed instants: CLEAR / 0% cloud on October 28 and RAIN / 88% cloud on October 29, each at 12:00 CDT.
2. Add a debug-source-set sidecar with explicit origin `AUTHORED_SYNTHETIC_REVIEW_INPUT`, keyed to those dates. For both dates it carries sunrise 06:00, sunset 18:00, moonrise 04:30, moonset 17:30, and phase 0.50 in `America/Chicago`.
3. Add a fixed debug-only entry in `DebugCatalogActivity` that activates the fixture through the existing catalog activation owner even when there are no saved user locations. It makes no network request and does not add a saved user location.
4. Add a default no-op build-variant presentation hook. The debug provider recognizes only the fixture dataset ID, applies that date's sidecar values to a copy of the evaluated `SceneReading`, including the local daylight phase needed for the daytime cue, and recomposes `FirstCityComposition`. It preserves the original timeline, selected instant, weather values, and diagnostics. Release behavior stays on the no-op path.
5. Identify the active fixture with `DEMO DATA · OX-05 authored synthetic astronomy fixture` in the debug overlay.

Authored astronomy does not enter `CanonicalCatalog.dailySummaries`, `ProviderDailySummary`, canonical recording persistence, live provider mapping, or non-fixture datasets. No astronomy is calculated or inferred. The fixed event values are rendering inputs only.

## Exact reviewed states

| State | Selected instant | Weather input | Solar sidecar | Lunar sidecar |
|---|---|---|---|---|
| Celestial overlap | `2026-10-28T17:00:00Z` / Oct 28, 12:00 CDT | `CLEAR`, 0% clouds | 06:00–18:00 CDT | Rise 04:30, set 17:30 CDT, phase 0.50 |
| Moderate rain | `2026-10-29T17:00:00Z` / Oct 29, 12:00 CDT | `RAIN`, 88% clouds | 06:00–18:00 CDT | Rise 04:30, set 17:30 CDT, phase 0.50 |

The cue positions follow the current First City mapping: Sun `(0.500, 0.180)`, Moon approximately `(0.558, 0.187)`. The rain case retains all solar/lunar inputs while preserving established rain behavior, which suppresses the Sun cue.

## Acceptance and verification proposal

- Retain focused depth-order assertions and the two existing composition-rendering cases.
- Add a focused debug-sidecar projection check for dataset scoping, exact event values, cue positions, selected-instant preservation, and unchanged weather fields.
- On the installed device, load the fixture from the debug session and capture both states on the primary `CatalogTimeLabScreen`. Confirm the synthetic DEMO DATA label and use the timeline to select the two exact instants. Do not substitute preview screenshots for these captures.
- Preserve screenshots and exact commands under `.codex/test-artifacts/144-ox05-depth-ordering/`. No RTL, TalkBack, or broad system matrix is added to this design slice.
- Continue using the previously owner-authorized focused verification only after the revised plan and boards are approved.

## Invariants and exclusions

Preserve the original OX-05 depth order, OX-03 cloud/rain decision semantics, OX-04 support/source-time/unavailable semantics, exact selected-instant authority, forecast facts as text, responsive timeline, and gesture ownership. `TIME_WASH` and `READOUT_QUIET_ZONE` stay separate compositing passes.

The only former exclusion to narrow is the ban on fixture/Time Lab route changes: a debug-only fixture activation entry and debug presentation adapter are now proposed. Live catalog behavior, user location search/storage, provider mapping and schemas, normal `CatalogTimelineScreen` behavior, weather interpretation, OX-06 registry work, renderer extraction, and deprecated oxygenWX remain out of scope.

## Owner approval record

- Behavior: `.codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.dot` plus SVG, PNG, and Markdown render/source package — approved by the product owner on 2026-11-16.
- UI artwork: `.codex/concepts/OX-05-debug-astronomy-sidecar-ui-board.svg` and PNG/Markdown package — approved by the product owner on 2026-11-16.

The approved scope is now merged into the active cycle plan and OX-05 roadmap entry. The cycle tracker records the approved package fingerprints and the checkpoint records completed re-review before resuming. The same cycle remains at `EXECUTE`; implementation proceeds only within this approved scope.
