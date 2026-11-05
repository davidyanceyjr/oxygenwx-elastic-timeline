# Cycle plan — 103-sg11b-rain-ice-events

State: CLOSED
Roadmap: SG.11b
Objective: Extend the typed weather-event path and complete Rain and ice row mapping and installed review

## Authority, approval, and scope

`AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md` govern this cycle. `python scripts/dev.py workflow` reports IDLE before planning; SG.11a closed as cycle 102 and SG.11b is the sole NEXT slice. WA.1–WA.6, SG.3, and SG.9 are DONE.

The product owner approved the shared SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` on 2026-11-04, and approved the six-row execution split that day. The retained behavior board and contract govern source-to-catalog flow, exact-code ledger, precedence, selected-time/source-time ownership, unavailable fallback, and recording compatibility. The retained UI board governs identity-to-symbol pairing and installed row states. This plan records only the Rain and ice row and shared behavior directly needed to support it; SG.11a–f as a whole and SG.12 remain outside this cycle. The board approval gate is met.

## Production boundary

Inspect cycle 102's implementation and evidence first. Likely affected paths are the Open-Meteo exact-code ledger/adapter, canonical event types and typed projection, version-5 codec/storage compatibility, the bounded SG.11 debug review route, and their focused existing tests. Confirm exact paths before changing them; keep changes to the Rain and ice identities and necessary shared-path compatibility. Do not alter `CONDITION_UNAVAILABLE` as a supplied event, the selected-time contract, broader scene-condition mapping, or unrelated WA rows.

Rain and ice identities and documented Open-Meteo assignments in this slice:

| Open-Meteo codes | Documented descriptions | Typed WA identity |
|---|---|---|
| 51, 53, 55 | Light, moderate, dense drizzle | `DRIZZLE` |
| 56, 57 | Light, dense freezing drizzle | `FREEZING_DRIZZLE` |
| 61, 63 | Slight, moderate rain | `RAIN` |
| 65 | Heavy rain | `HEAVY_RAIN` |
| 66, 67 | Light, heavy freezing rain | `FREEZING_RAIN` |
| 80, 81, 82 | Slight, moderate, violent rain showers | `RAIN_SHOWERS` |

`ICE_PELLETS` and `HAIL` remain representable supplied typed identities and require canonical/version-5/projection/symbol review; the approved ledger has no Open-Meteo code assignment for them. Preserve each numeric code and deterministic documented code description separately from the selected forecast/accessibility wording. Unsupported intermediate values previously accepted by broad ranges stay unsupported and yield no typed event. Preserve every supplied event and the complete deterministic fallback hierarchy; a source primary continues to take precedence.

## Functional invariants

- The selected `Instant` remains authoritative for catalog facts, projection, and scene. Gaps, replay, and zoom do not retarget it.
- The saved bounded Open-Meteo response remains the mapping input. Raw response bytes and request identity stay attached to the same live capture and version-5 recording; the generated 44-local-date catalog remains the input to scene and view.
- New supported events retain stable identity, numeric source code, documented code description, and provenance as distinct fields. Accessible/forecast wording and symbol identity derive from the selected WA identity, never measurements, source iteration order, or raw response text.
- Preserve all supplied events and member-referenced primary validity. Without a source primary, use the approved fixed 47-ID priority order. `CONDITION_UNAVAILABLE` is presentation-only and cannot be recorded as supplied.
- Versions 1–4 remain readable without typed-field backfill; version 5 retains typed events and raw capture through storage reload and replay. Do not add a database migration unless an observed table change requires one.
- Older broad scene conditions and previously completed Sky mappings remain valid. Do not change the approved Sky day/night selection or scene light timing.
- Keep readable forecast facts, symbol names, timeline responsiveness, gesture ownership, and DEMO DATA/provenance semantics in the affected installed states.

## Steps

- [x] Inspect cycle 102's code, exact-code ledger, artifact README, and the working tree; identify the Rain and ice routes and record the existing supported/unsupported code behavior.
- [x] Confirm the approved exact Rain and ice ledger rows before adapter changes. Implement only codes 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, and 82; unsupported intermediate values return no typed event. Preserve canonical event, source-description, provenance, and primary-selection contracts. Owner separately authorized focused tests and runtime verification on 2026-11-05.
- [x] Complete the bounded debug/catalog route for the eight Rain and ice IDs: `DRIZZLE`, `RAIN`, `HEAVY_RAIN`, `RAIN_SHOWERS`, `FREEZING_DRIZZLE`, `FREEZING_RAIN`, `ICE_PELLETS`, and `HAIL`. Verify all eight through canonical input, version-5 recording/replay, selected-time projection, matching WA symbol and accessible wording. Retain cycle 102 Sky checks and legacy replay behavior.
- [x] Build/install on the Pixel 3 XL portrait reference at default font scale. Capture each of the eight row states with selected fact, exact code where applicable, symbol ID, accessible name, device, command, and capture index. Check selected-time changes and directly affected timeline selection/gesture ownership. The row exit requires each state in an installed capture; do not infer installed acceptance from a build or preview.
- [x] Record pass/fail/not-run against every scoped exit item and exact commands/results in the evidence README. `git diff --check` passed and the final diff was inspected.
- [x] Verify the Open-Meteo mapper at runtime for all 13 assigned codes and unsupported intermediate values. Verify version-5 save/replay and legacy versions 1–4 decoding/replay without typed-field backfill. Focused results and the precise commands are in the evidence README.

## Acceptance and evidence

Exit only when all eight row identities survive canonical input, version-5 recording/replay, selected-time projection, and matching symbol/accessibility wording. The 13 documented Open-Meteo codes map only to the ledger assignments above with exact raw code and fixed code description preserved. Unsupported intermediate values produce no typed event. Installed Pixel 3 XL captures inspect all eight row states with the selected fact, symbol ID, accessible name, exact code or “no Open-Meteo assignment,” command, and limitation recorded. Directly affected selection is checked. Existing Sky states, older replay versions, and shared primary/precedence behavior remain valid.

After activation, use `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py run --serial <device>`, and `python scripts/dev.py screenshot --serial <device> --output .codex/test-artifacts/103-sg11b-rain-ice-events/<state>.png` as applicable. The owner separately authorized focused mapper and recording/replay tests on 2026-11-05. No RTL, TalkBack, reduced-motion matrix, or broad system acceptance is claimed; those belong to TEST slices.

Preserve commands, device/viewport, eight captures and hierarchy dumps, row observations, code-ledger updates, exact pass/fail/not-run results, limitations, and diff review in `.codex/test-artifacts/103-sg11b-rain-ice-events/README.md`. Closeout target: `.codex/history/103-sg11b-rain-ice-events.md`; archived plan: `.codex/history/plans/103-sg11b-rain-ice-events.md`.

## Risks and assumptions

The bounded debug route must make `ICE_PELLETS` and `HAIL` reviewable without inventing Open-Meteo assignments. The row may share implementation files with other rows; keep edits row-scoped and retain cross-row behavior from cycle 102. Preserve exact source descriptions where intensity differs while the WA wording stays at event-family level.

## Out of scope

No non-Rain-and-ice Open-Meteo assignments or installed row claims; no new WA artwork; no changes to Sky mappings, unavailable-state semantics, center-readout composition, scene composition/light timing, provider endpoint/variables, unit policy, location/timeline navigation, official alerts, live-network redesign, deprecated oxygenWX files, or cross-cutting accessibility/system matrices.
