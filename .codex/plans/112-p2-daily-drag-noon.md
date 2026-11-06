# Cycle plan — 112-p2-daily-drag-noon

State: PLANNED — unfinalized draft; required owner approval of the combined board is pending. Do not activate or implement before approval and final plan review.

Roadmap: P.2.19
Objective: Select the target date's local noon when an active-catalog daily drag releases, while preserving the current timeline interaction contract.

## Board gate

- **Combined behavior and UI artwork board:** `.codex/concepts/P.2.19-daily-drag-noon-board.svg`, PNG preview `.codex/concepts/P.2.19-daily-drag-noon-board.png`, and behavior/art contract `.codex/concepts/P.2.19-daily-drag-noon-board.md`.
- **Before/input:** `.codex/concepts/P.2.19-daily-drag-before.png`, an actual Pixel 3 XL cycle 111 daily-drag capture at SAT 11:39 PM.
- **Draft revision:** SHA-256 `9dd1eb3fca5a0e599a4630cdacfa3746e640e2cd6a9e3b1d26574852bd3de345` for the preview shown by path above. This hash records the draft only; it is not an approval record.
- **Owner approval:** PENDING. On 2026-11-05, the owner chose a separate approved slice for the observed daily-drag discrepancy. That decision authorizes this draft plan; it does not approve this board's detailed behavior or artwork.
- **Approval record still required:** exact approved board revision/date, constraints, and relationship to scope and acceptance. The board is a design input; installed evidence remains required for closeout.

## Production boundary

Expected implementation files, subject to review of the approved board:

- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — active-catalog daily drag release behavior in `CatalogForecastTimeline`.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/InstantTimeAxis.kt` — only if existing pure date/noon or boundary helpers need a narrow reuse or correction.
- The existing selected-time adjustment-notice path, identified during code review, only if needed to satisfy the specified partial-boundary communication.

Do not change the fixture/debug timeline, hourly time projection, weather symbol mapping, atmospheric scene, central readout design, provider data, or the deprecated oxygenWX application.

## Functional invariants

- A daily drag moves the tape immediately beneath the fixed playhead. At release, resolve the target local date under that playhead and select 12:00 in the location's IANA timezone. Project the new selected instant to the same center playhead.
- If target noon is outside the catalog's selectable interval on a partial first/last date, clamp to the nearest boundary and communicate the adjustment. An in-window missing day remains selectable; do not invent forecast facts.
- Do not snap during the pointer move or change the date based on the touch-down coordinate. Preserve daily drag responsiveness and date/icon movement.
- Timeline single taps remain no-ops; double taps toggle daily/hourly scale without changing the exact selected instant. Hourly dragging continues to scrub by hour and remains in hourly scale on release.
- Main-area gestures, existing date labels/icons, forecast facts, source disclosures, and selected-time authority remain intact and readable.

## Steps

- [x] Record the cycle 111 installed discrepancy and owner direction; prepare a separate roadmap slice, combined board, and unfinalized plan.
- [ ] Show the combined board to the owner and record explicit approval of its exact revision and constraints.
- [ ] Finalize/review this bounded plan after the board gate, then activate only with authorization.
- [ ] Inspect the active-catalog gesture and existing adjustment-notice path; implement the release selection within this scope.
- [ ] Build/install on the Pixel 3 XL and capture daily before/after, partial-boundary, and directly affected daily/hourly interaction states.
- [ ] Record exact results and limits, inspect `git diff --check` and the final diff, and close only if the slice exit is met.

## Acceptance and evidence

- **Daily release:** installed active-catalog capture and direct drag show that release on a different date selects that date's 12:00 local instant at the center playhead; displayed forecast facts follow the selected instant.
- **Boundary and gap:** an available partial-boundary case clamps out-of-window noon and shows the adjustment; an in-window missing date stays selected with unavailable facts. Record a truthful NOT RUN if the installed catalog cannot provide a boundary case rather than inferring acceptance.
- **Preservation:** direct checks show immediate daily drag, no-op timeline single tap, exact-time-preserving timeline double tap, hourly scrub/release, main-area gesture ownership, and legible date icons/labels in the changed states.
- **Evidence target:** `.codex/test-artifacts/112-p2-daily-drag-noon/README.md` and installed screenshots in that directory; eventual closeout `.codex/history/112-p2-daily-drag-noon.md`.
- **Authorized checks when activated:** `python scripts/dev.py workflow`; `python scripts/dev.py run --serial 8AKY0M32X`; `python scripts/dev.py screenshot --serial 8AKY0M32X --output <cycle evidence path>`; focused manual Pixel interactions; `git diff --check` and final diff review. Do not add or run unit/instrumentation tests unless the user requests them. RTL, TalkBack, font-scale, reduced-motion, and broad system/performance matrices belong to dedicated TEST slices.

## Risks and assumptions

- Cycle 111 demonstrated the active-catalog drag ending at SAT 11:39 PM instead of noon, but did not inspect all partial boundary states. Check available catalog bounds and the existing adjustment notice before editing.
- The `P.2.17` double-tap exception means a release after drag must remain distinguishable from a tap sequence; preserve that gesture arbitration.
- The output board is a concept. The forecast facts at selected noon are sourced from the catalog and may differ from the before capture.

## Out of scope

Fixture/debug timeline behavior, hourly scale selection rules, new weather symbols or events, forecast-data derivation, central-readout redesign, location selection, new controls, provider integrations, app-wide accessibility/system acceptance, and unrelated body work.
