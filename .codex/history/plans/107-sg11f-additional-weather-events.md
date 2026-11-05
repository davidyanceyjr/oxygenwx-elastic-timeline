# Cycle plan — 107-sg11f-additional-weather-events

State: CLOSED
Roadmap: SG.11f
Objective: Complete the Additional events row in the typed weather-event path and bounded debug review route.

## Authority and board approval

`AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md` govern this cycle. `python scripts/dev.py workflow` reported IDLE before planning. SG.11f is the sole NEXT slice; its dependency SG.11e is DONE in cycle 106.

The SG.11a behavior and UI artwork boards are shared authority for the complete SG.11a–f six-slice split, as the owner directed on 2026-11-04. Product-owner approval of both boards and approval of the split are recorded in the boards' supporting contract and `docs/ROADMAP.md`. This plan uses `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg`; the artwork board's PNG preview is alongside it. The shared behavior board governs the exact-code ledger, event retention and primary-selection precedence, raw-response to canonical catalog boundary, version-5 recording and legacy replay, selected-time/source-time semantics, and unavailable fallback. The shared artwork board governs the existing WA identity/symbol and accessible wording, and installed review of the row states. Approval includes the documented amendments in the contract: fixed event fallback priority, deterministic wording from the ledger and selected WA identity, and raw captured response as mapping input. For SG.11f, the boards constrain work to the Additional events row and shared behavior directly needed by it; they do not authorize other rows, artwork creation, or tests. The board gate is met.

## Objective and bounded scope

Complete the Additional events identities `DRY_THUNDERSTORM`, `TROPICAL_STORM`, `WATERSPOUT`, `FROST`, `EXTREME_HEAT`, `EXTREME_COLD`, `DUST_SANDSTORM`, and `VOLCANIC_ASH` in the established typed mapping and bounded debug review route. Add only documented Open-Meteo exact-code assignments that the approved SG.11 ledger assigns to this row. Keep identities without an approved Open-Meteo assignment representable as canonical events and clearly labeled as canonical-only in the review route. Do not infer events from measurements, response descriptions, iteration order, or unsupported codes.

At series exit, reconcile the complete set of 47 supplyable WA IDs, the presentation-only `CONDITION_UNAVAILABLE` fallback, the full fixed precedence order, and every exact-code assignment against the approved contract and implementation. The reconciliation is a closeout of the SG.11a–f mapping contract, not permission to implement or change another row in this cycle.

## Production boundary

Expected affected paths, subject to baseline inspection:

- `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoCatalogMapper.kt` — only exact documented assignments for SG.11f identities, plus directly required exact-code ledger corrections.
- Existing typed fact, recording, projection, symbol, and bounded SG.11 debug-review paths only where needed to carry, replay, inspect, and present these eight identities.
- Focused test sources are not in this cycle's authorized verification; do not add or run tests unless separately authorized.
- `.codex/test-artifacts/107-sg11f-additional-weather-events/README.md` and installed captures/hierarchy dumps; `.codex/history/107-sg11f-additional-weather-events.md`; archived plan `.codex/history/plans/107-sg11f-additional-weather-events.md` at close.

Confirm exact files and current-tree status during execution. Cycle 106 and prior SG.11 rows are completed baseline input; do not repeat or re-scope their work. Preserve the accepted unavailable-event scrub mismatch as a documented limitation assigned to SG.12; no center-readout redesign is included here.

## Functional invariants

- The selected `Instant` remains authoritative through canonical input, version-5 recording/replay, selected-time projection, and debug inspection.
- Preserve every supplied event and any valid source-designated primary. Without one, use the approved deterministic priority; never use collection order.
- Keep exact source code, documented code description, provider/provenance, typed identity, and forecast/accessibility wording distinct and faithful to the approved contract.
- `CONDITION_UNAVAILABLE` is presentation-only, has no `WeatherEventFact`, and cannot be stored as a supplied event. Distinguish it from a canonical-only event with no approved Open-Meteo assignment.
- Version 5 preserves typed facts through storage reload/replay; versions 1–4 remain readable without typed-field backfill.
- Preserve completed SG.11a–e assignments, symbol mappings, selected-time/source-time behavior, other scene conditions, and shared debug route behavior.
- Keep DEMO DATA and provenance honest, event names readable, and timeline responsiveness/gesture ownership intact in changed states.

## Steps

1. [x] Inspect cycle 106's closeout/evidence, the shared approved boards and contract, exact-code ledger, mapper, recording/replay boundary, selected-event projection, symbol API, and SG.11 debug route. Confirm exact Additional-row assignments and Pixel 3 XL availability before editing.
2. [x] Implement only approved exact-code assignments for the eight Additional event IDs. Retain canonical-only identities without fabricated provider codes; reject unsupported codes from typed mapping while preserving raw supplied code evidence where applicable.
3. [x] Extend the bounded review fixture/route for all eight identities, including source assignment or canonical-only wording, symbol ID, deterministic forecast/accessibility label, and version-5 storage reload/replay. Preserve prior rows, legacy replay, unavailable behavior, and the approved fixed primary-selection priority.
4. [x] Reconcile the full 47-ID mapping, precedence order, and exact-code ledger against all SG.11 row implementations and the approved contract. Record discrepancies and their bounded disposition; do not silently expand SG.11f to repair unrelated rows.
5. [x] Build/install on the Pixel 3 XL portrait reference device at font scale 1.0. Capture all eight Additional row states with paired hierarchy inspection. Directly check selected-time/timeline behavior in affected states. Record any unavailable capture or interaction as not run with its exact blocker.
6. [x] Record exact commands, device/viewport, captures, observations, pass/fail/not-run results, and limitations. Run `git diff --check`, inspect the complete final diff, update roadmap evidence/status only if SG.11f exit is met, and close cycle 107 with exact results.

## Acceptance and evidence

Each of the eight Additional event IDs must survive canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. Provider-assigned identities use only the exact assignments and documented descriptions in the approved ledger; other identities remain canonical-only and are labeled accordingly. Unsupported codes yield no typed supplied event. The unavailable presentation state remains unserialized. Installed Pixel 3 XL portrait evidence at font scale 1.0 shows each row identity with its supplied fact, exact code/description or canonical-only status, symbol ID/name, command, and limitation. Directly affected selected-time/timeline behavior is checked. Prior SG.11 rows and versions 1–4 replay remain valid. The complete 47-ID ledger and fixed precedence are reconciled and recorded. Evidence target: `.codex/test-artifacts/107-sg11f-additional-weather-events/README.md`; closeout target: `.codex/history/107-sg11f-additional-weather-events.md`; archived plan target: `.codex/history/plans/107-sg11f-additional-weather-events.md`.

Authorized planning/execution verification: `python scripts/dev.py workflow`, `contract`, `build`, `run --serial <device>`, and `screenshot --serial <device> --output .codex/test-artifacts/107-sg11f-additional-weather-events/<state>.png` as applicable; manual installed-state and directly affected interaction inspection; `git diff --check` and full diff review. Tests are not authorized by the shared boards or current request, so neither add nor run tests under this plan. No RTL, TalkBack, reduced-motion, alternate-font, or broad system/device matrix is in scope. A build or preview alone does not establish installed acceptance.

## Risks / assumptions

- The shared contract names the eight row identities but the exact code assignment must be taken from the approved complete ledger; do not infer one from neighboring codes or meteorological measurements.
- Some Additional IDs may have no approved Open-Meteo mapping. Keep them available to other canonical sources and make the lack of Open-Meteo assignment explicit in the review route.
- Installed acceptance depends on Pixel 3 XL availability. If unavailable, record the exact blocker and not-run states rather than treating a build as visual evidence.
- Series reconciliation may expose a mismatch outside SG.11f. Record it as a finding and do not silently alter another row's implementation or scope.

## Out of scope

No mapping outside the Additional events row except the final read-only series reconciliation; no new WA artwork; no central readout redesign; no event inference from precipitation, wind, visibility, wording, or unsupported codes; no provider endpoint, units, location, catalog navigation, official alerts, live-network redesign, or deprecated oxygenWX changes; no tests without separate authorization; no RTL, TalkBack, reduced-motion, alternate-font, broad accessibility, or system/device matrices; no full-suite verification.
