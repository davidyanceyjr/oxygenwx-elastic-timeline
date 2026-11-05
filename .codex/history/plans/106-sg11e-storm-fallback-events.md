# Cycle plan — 106-sg11e-storm-fallback-events

State: CLOSED
Roadmap: SG.11e
Objective: Complete the Storm and fallback row in the typed weather-event path and bounded debug review route.

## Authority and board approval

`AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md` govern this cycle. Workflow was IDLE before planning; SG.11e is the sole NEXT slice and depends on completed SG.11d (cycle 105).

The product owner approved the SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` on 2026-11-04, and approved the six-row split that day. Approval covers the exact-code mapping, event retention and primary selection, version-5 recording, legacy replay, selected-time behavior, symbol/accessibility mapping, canonical-only states, and installed review. These boards apply to this cycle's Storm and fallback identities; no new artwork or visual direction is introduced. The board approval gate is met.

**Owner decision — 2026-11-05:** For codes 95, 96, 97, and 99, remove the broad `WeatherCondition.RAIN` assignment and retain `WeatherCondition.STORM`. The approved typed identities and exact descriptions remain as listed below. This resolves the behavior-board instruction that a thunderstorm code must not imply rain, while preserving the existing code-specific storm identities. This bounded scene-condition correction is in SG.11e scope.

## Objective and bounded scope

Complete the seven supplyable Storm and fallback identities — `THUNDERSTORM`, `THUNDERSTORM_WITH_RAIN`, `THUNDERSTORM_WITH_HAIL`, `THUNDERSTORM_WITH_SNOW`, `SQUALL`, `TROPICAL_CYCLONE`, and `TORNADO_FUNNEL` — plus the presentation-only `CONDITION_UNAVAILABLE` state. Extend only the existing typed mapping, version-5 debug replay review route, and directly affected projection/symbol verification.

Use only the approved Open-Meteo assignments:

| Code | Documented description | Typed identity |
|---|---|---|
| 95 | Thunderstorm | `THUNDERSTORM` |
| 96 | Thunderstorm with slight hail | `THUNDERSTORM_WITH_HAIL` |
| 97 | Heavy thunderstorm | `THUNDERSTORM` |
| 99 | Thunderstorm with heavy hail | `THUNDERSTORM_WITH_HAIL` |

Preserve each exact code and description as source evidence. Do not add an Open-Meteo mapping for the other five supplyable identities. `CONDITION_UNAVAILABLE` remains a presentation fallback with no `WeatherEventFact`; the review route must distinguish an absent supplied event from a canonical event that simply has no approved Open-Meteo assignment.

The existing `mapWmo` path currently maps 95, 96, and 99 to broad storm/rain scene conditions and has no typed Storm event mapper; code 97 is not included in that condition mapping. Under the owner decision above, map all four supported storm codes to the broad `STORM` condition without adding `RAIN`. Do not change unrelated scene or weather-condition policy.

## Production boundary

Expected affected paths, subject to the baseline inspection:

- `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoCatalogMapper.kt` — exact codes 95, 96, 97, and 99 only; preserve `STORM` broad conditions without `RAIN`, plus existing source provenance.
- A new `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugSg11StormFallbackCatalogFixture.kt` and the bounded SG.11 replay route in `DebugCatalogActivity.kt` / `CatalogPickerDelegate.kt` — seven typed facts and one no-event state, version-5 reload, exact selected-time inspection, and accurate assignment/unavailable wording.
- Focused existing mapper, debug-fixture/route, recording, projection, and symbol tests under `app/src/test/` as needed for this row.
- `.codex/test-artifacts/106-sg11e-storm-fallback-events/README.md` and installed captures/XML under the same directory; `.codex/history/106-sg11e-storm-fallback-events.md` and archived plan under `.codex/history/plans/` at close.

The working-tree changes and evidence for SG.11c/SG.11d have been reconciled to cycles 104/105 and are existing baseline input to this cycle. Do not repeat or re-scope those rows. Do not change other SG.11 rows except to preserve shared picker/replay compatibility required by the Storm route.

## Functional invariants

- The selected `Instant` remains authoritative through canonical input, version-5 recording/replay, selected-time projection, and debug inspection.
- Preserve every supplied event and a valid source primary. Without one, use the owner-approved fixed priority; never use collection order.
- Keep the exact source code, documented description, provider, and provenance distinct from the typed forecast/accessibility label.
- Codes 95 and 97 map to `THUNDERSTORM`; codes 96 and 99 map to `THUNDERSTORM_WITH_HAIL`. All four retain their exact documented descriptions and map to the broad `STORM` condition without `RAIN`.
- `THUNDERSTORM_WITH_RAIN`, `THUNDERSTORM_WITH_SNOW`, `SQUALL`, `TROPICAL_CYCLONE`, and `TORNADO_FUNNEL` remain canonical-only in Open-Meteo mapping; do not infer them from other codes or measurements.
- `CONDITION_UNAVAILABLE` is presentation-only, has no `WeatherEventFact`, and cannot be encoded as a supplied event. In the review UI it is labeled as no supplied event, distinct from a canonical-only event with no approved provider assignment.
- Version 5 preserves the typed facts through storage reload and replay; versions 1–4 remain readable without typed-field backfill.
- Preserve all completed SG.11 rows, selected-time semantics, readable DEMO DATA/provenance, other existing scene conditions, and timeline gesture ownership.

## Steps

1. [x] Inspect the reconciled cycle 105 implementation/evidence, both approved boards and behavior contract, mapper, existing SG.11 replay route, and debug fixture patterns. Confirm the exact storm ledger and Pixel 3 XL availability.
2. [x] Implement typed assignments for codes 95, 96, 97, and 99 with exact descriptions. Map these codes to broad `STORM` without `RAIN`; keep unsupported/unlisted codes unmapped to typed Storm events.
3. [x] Add a bounded version-5 replay fixture for the seven Storm identities and a separate catalog instant with no supplied typed event. Ensure `CONDITION_UNAVAILABLE` is not represented in the serialized event list. Route all eight states to inspection and show source code/description for mapped events, “No approved Open-Meteo assignment” for canonical-only events, and “No supplied weather event” for unavailable.
4. [x] Add focused checks for exact assignments, unsupported values, code 95/97 no-rain-event behavior, all seven canonical event identities, unavailable non-serialization, version-5 round-trip/replay, matching selected symbol/accessibility wording, and legacy versions 1–4 compatibility. Run only these scoped checks; do not expand to the full suite.
5. [x] Build and install on the Pixel 3 XL portrait reference device at font scale 1.0. Capture all seven supplyable identities and the unavailable state with paired hierarchy XML. Directly check selected-time/timeline behavior across representative Storm and unavailable states. The direct scrub finding and its disposition status are recorded in the cycle evidence README.
6. [x] Record exact commands, observations, pass/fail/not-run scope, device parameters, captures, and limitations. Run `git diff --check`, inspect the complete final diff, record the product owner's 2026-11-05 acceptance of the installed scrub mismatch as a documented limitation assigned to SG.12, then close cycle 106 with exact verification results.

## Acceptance and evidence

Exit requires all seven supplyable identities to survive canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Only Open-Meteo codes 95, 96, 97, and 99 map as listed; descriptions, numeric source codes, and provider provenance are retained, and all four broad scene mappings contain `STORM` without `RAIN`. Unsupported codes yield no typed supplied event. The unavailable state projects to `CONDITION_UNAVAILABLE` but is absent from canonical supplied events and serialized typed events. Each of the eight states has an installed Pixel 3 XL portrait capture at font scale 1.0 and paired hierarchy inspection. Directly affected selected-time/timeline behavior is checked, and existing rows plus version 1–4 replay remain valid. The artifact README records exact commands/results and limits. RTL, TalkBack, reduced motion, alternate font sizes, and broad system/device matrices are outside this design slice and are not claimed.

Target evidence: `.codex/test-artifacts/106-sg11e-storm-fallback-events/README.md`. Target closeout: `.codex/history/106-sg11e-storm-fallback-events.md`; archived plan: `.codex/history/plans/106-sg11e-storm-fallback-events.md`.

**Execution finding and owner disposition, 2026-11-05:** The installed exact unavailable state at 3:00 PM correctly shows `CONDITION_UNAVAILABLE` and no supplied event. During an hourly scrub to 3:45 PM, the overlay retained the unavailable event while the central condition text displayed “partly cloudy” from the nearest 4:00 PM synthetic sample. Evidence is in `timeline-scrub.png/.xml`. The product owner accepted this mismatch as a documented limitation assigned to SG.12 and authorized SG.11e to close within its existing scope. No center-readout behavior change is in scope.

## Risks and assumptions

- The approved ledger and board contract are authoritative for 95/96/97/99. Do not broaden code ranges or infer identities.
- A no-event fixture instant may still have independent forecast facts; acceptance is that its typed event list remains empty and the selected event presentation is unavailable.
- Installed acceptance requires the connected Pixel 3 XL and X display. Reconfirm at execution; a build or preview does not replace captures.
- Existing central condition/date-time crowding documented in cycles 104/105 remains assigned to SG.12; this cycle makes no readout redesign claim.

## Out of scope

No event mapping outside the Storm/fallback row; no new symbol artwork; no alert subsystem; no central readout redesign; no unrelated scene, provider, unit, location, catalog-navigation, or live-network changes; no deprecated oxygenWX files; no RTL, TalkBack, reduced-motion, broad accessibility, or system/device matrices; no full-suite verification.
