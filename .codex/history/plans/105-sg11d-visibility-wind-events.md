# Cycle plan — 105-sg11d-visibility-wind-events

State: CLOSED
Roadmap: SG.11d
Objective: Complete the Visibility and wind row in the typed weather-event path and bounded debug review route.

## Authority and board approval

`AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md` govern this cycle. Cycle 104 closed SG.11c on 2026-11-05; SG.11d depends on that completed slice. `python scripts/dev.py workflow` reported the new cycle PLANNED.

The product owner approved the SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` on 2026-11-04, and approved the six-row split that day. These boards cover provider-to-canonical mapping, exact-code ledger, priority/primary selection, recording compatibility, selected-time/source-time behavior, unavailable fallback, symbol identity, and installed row states. They govern this slice's Visibility and wind row only. The current exact-code ledger assigns Open-Meteo code 45 (Fog) to `FOG` and code 48 (Depositing rime fog) to `FREEZING_FOG`. It has no approved Open-Meteo assignment for `MIST`, `HAZE_SMOKE`, `DUST_SAND_ASH`, `WINDY_GUSTY`, `BLOWING_SNOW`, or `BLIZZARD`; preserve these as canonical identities without fabricating provider facts. Board gate is met.

## Objective and bounded scope

Extend the established SG.11a typed mapping and bounded debug route for `MIST`, `FOG`, `FREEZING_FOG`, `HAZE_SMOKE`, `DUST_SAND_ASH`, `WINDY_GUSTY`, `BLOWING_SNOW`, and `BLIZZARD`. Add only the two exact documented provider assignments above. Preserve the other six identities as canonical-only review states, previous weather rows, deterministic event selection, version-5 recording/replay, versions 1–4 compatibility, and selected-time authority.

## Production boundary

Expected paths, to confirm against the current tree before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoCatalogMapper.kt` — exact code mapping.
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugSg11VisibilityWindCatalogFixture.kt` and the existing debug catalog/picker route — bounded eight-state review fixture and inspection labels.
- Focused mapper, fixture, symbol/projection, and recorded-catalog tests under `app/src/test/`.
- `.codex/test-artifacts/105-sg11d-visibility-wind-events/README.md` and installed captures.

Do not alter unrelated provider families or redesign the central readout. The five-state readout label/date-time collision recorded in cycle 104 is accepted for SG.11c and assigned to SG.12; it is outside this row slice.

## Functional invariants

- The selected `Instant` remains authoritative through mapping, recording/replay, projection, and debug row inspection.
- Preserve every supplied event and source-primary validity. Without a source primary, use the approved fixed priority; never use collection order.
- Each mapped event retains typed identity, exact numeric source code, documented code description, and provider provenance as distinct evidence. Symbol/accessibility wording derives from typed identity.
- `CONDITION_UNAVAILABLE` remains presentation-only and cannot be recorded as a supplied event.
- Version 5 retains typed events through recording/replay. Versions 1–4 remain readable without typed-field backfill.
- Preserve completed Sky, Rain/ice, and Snow/mixed rows; do not infer wind, dust, smoke, blowing snow, mist, or blizzard from unrelated measurements or codes.
- Keep DEMO DATA/provenance honest and maintain readable symbol names, assignment labels, and responsive timeline behavior in the changed installed states.

## Steps

1. Inspect cycle 104's final diff/evidence and the approved SG.11 behavior/artwork boards. Confirm the exact 45/48 ledger entries and current mapper/debug fixture routes.
2. Implement only code 45 → `FOG` and code 48 → `FREEZING_FOG` as typed events, retaining the exact documented descriptions. Preserve unsupported codes as unmapped for this row.
3. Add all eight canonical identities to a bounded version-5 debug replay fixture. Show the exact code/description for the two mapped identities and “no approved Open-Meteo assignment” for the other six.
4. Verify all eight identities through canonical input, version-5 recording/replay, selected-time projection, matching symbol/accessibility wording, and existing-row compatibility. Add the focused tests specified below only within this scope.
5. Confirm Pixel 3 XL availability at activation, install the debug app, capture all eight portrait states at default font scale, and check directly affected timeline selection.
6. Record commands, capture observations, results, and limitations; inspect `git diff --check` and the final diff before closeout.

## Acceptance and evidence

Exit requires all eight row identities to survive canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Only codes 45 and 48 receive provider mappings, with exact documented descriptions; the other six remain canonical-only and display no approved assignment. Unsupported values yield no typed supplied event. Every row state is inspected in an installed Pixel 3 XL portrait capture at font scale 1.0, with selected fact, code/description or no-assignment wording, symbol ID, accessible name, device/command, and limitation recorded. Directly affected selection/timeline behavior is checked. Existing rows and versions 1–4 replay compatibility remain valid. Evidence target: `.codex/test-artifacts/105-sg11d-visibility-wind-events/README.md`; history: `.codex/history/105-sg11d-visibility-wind-events.md`; archived plan: `.codex/history/plans/105-sg11d-visibility-wind-events.md`.

Focused verification should cover exact provider mapping and unsupported values, all eight fixture identities, version-5 round-trip, legacy versions 1–4, and matching symbol/accessibility projection. Add or run tests only with explicit user authorization at execution time, per repository policy. Installed captures and directly affected interaction evidence are required for visual acceptance. Do not claim RTL, TalkBack, reduced-motion, or broad system acceptance; those belong to dedicated TEST slices.

## Risks and assumptions

- The approved exact-code ledger currently supports only codes 45 and 48 for this row. Do not add provider assignments based on broad meteorological plausibility.
- Other providers may explicitly supply canonical identities; this slice preserves canonical typed support without adding unsupported Open-Meteo mappings.
- Installed acceptance requires the Pixel 3 XL and X display; recheck availability at activation and record any changed condition.

## Out of scope

No assignments for other artwork rows; no new symbol artwork; no readout composition changes; no scene composition, endpoint/variable, unit, location, or timeline-navigation changes; no official alerts, live-network redesign, deprecated oxygenWX files, or broad accessibility/system matrices.

## Difficulty and model fit

Difficulty: 4/10. This is a bounded extension of the established typed mapping and review route with two documented provider assignments and six canonical-only identities. Main verification work is exact mapping, replay preservation, and eight installed symbol states.
