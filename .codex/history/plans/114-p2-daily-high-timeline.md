# Cycle plan — 114-p2-daily-high-timeline

State: ACTIVE — REVIEWED, READY FOR ACTIVATION
Roadmap: P.2.21
Objective: Show each date's existing daily high below its weather-event icon and the timeline divider on the active-catalog daily timeline.

## Readiness and board gate

- `.codex/current.md` was IDLE before this draft. `python scripts/dev.py workflow` reported `Workflow scaffold: OK`. P.2.20 is DONE in cycle 113; P.2.21 follows it and depends only on P.2.20.
- **Combined behavior and UI artwork board:** `.codex/concepts/P.2.21-daily-high.svg`; rendered review preview `.codex/concepts/P.2.21-daily-high.png`; behavior/art contract `.codex/concepts/P.2.21-daily-high.md`.
- **Pre-implementation installed input:** `.codex/concepts/P.2.21-daily-high-before.png`, captured from Pixel 3 XL serial `8AKY0M32X` with `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/concepts/P.2.21-daily-high-before.png`. The source screenshot is 1440×2960 px (411×846 dp at device density); its SHA-256 is `d9e7232bce16812a5210be624f988f12f23078b6b93194c03c94a0211cbb3c5e`.
- **Board approval:** The product owner approved the exact combined behavior/UI board package on 2026-11-06. The reviewed package revisions were SVG SHA-256 `c1a57c2495f90a0739a31d6ec365b7c11cb372934a139c3f64d2d472840b8096`, PNG preview SHA-256 `5acd32f54091eff4e043232d47932c03eba7e5c3ca26e0474c9e934e94611972`, and contract SHA-256 `1c189ab07e24167190e35b0ab558f8fad712c272210fdb969d3a03d62299d374`. After approval, only the board's status annotations were updated to record approval; its behavior and visual constraints did not change. Current status-annotated revisions are SVG SHA-256 `c84d5d723853d283811b9a4e3ca7619620ea29a28e5e32f23323f0f85e598507`, PNG preview SHA-256 `4302cba76ddc55ca29f2c502562388569bedb9e7b6904cf5fb643408b32fa9b4`, and contract SHA-256 `64c2a67e821e0ce39ff58278e97445d772f31693d7f7ba5d97405bd96aa3d499`. The plan remains PLANNED and is ready for activation.
- **Approved board constraints:** Use each date's existing daily-high projection and current display formatting; place an enlarged bold numeric high with a smaller `HI` marker under the daily event symbol and below the divider, aligned to that date. The numeric readout should feel approximately as prominent as the icon above it while fitting its date slot. Keep unavailable values unavailable. Keep the hourly timeline free of daily highs. Preserve event mapping, time labels, ticks, divider, selected-time playhead/badge, gestures, selected-time authority, and forecast facts. Avoid collisions among high labels, playhead/badge, and system navigation. The board guides scope and acceptance; installed verification is still required.

## Production boundary

Expected implementation paths:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — expose the already projected per-day high on the typed `CatalogDayView`, retaining the existing presentation conversion and missing-value state.
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — draw the per-day `HI` value beneath the divider in daily scale, and preserve separation from the selected-time badge.

Do not change high/low source semantics, derive daily highs from hourly values, alter event mapping/artwork, add daily-high labels to hourly scale, change selected-time behavior or gestures, remove central facts, or touch other workstreams or the deprecated oxygenWX app.

## Functional invariants

- Daily values come from the existing daily overview projection for their matching local dates.
- UI consumes typed presentation values; it does not read provider DTOs or perform independent unit conversion.
- A missing high is not zero and does not borrow another date's value. Render the board's explicit `HI —` unavailable marker.
- Each high remains attached to its date and moves with that date as the tape is browsed.
- Hourly mode remains without weather symbols and daily-high labels.
- Preserve day/hour labels, event identity and daily placement, ticks, divider, selected-time authority, playhead, time badge, daily drag, hourly scrub, and double-tap scale toggle.
- Preserve the existing central selected forecast facts and source honesty.

## Steps

- [x] Review and approve the exact combined board revision and its constraints; record explicit owner approval here before finalizing this plan.
- [x] Reconfirm cycle 114 is PLANNED, P.2.21 is NEXT, and workflow remains valid after board approval (`python scripts/dev.py workflow`: `Workflow scaffold: OK`).
- [x] Implement the existing daily-high projection in the typed day view and add the daily-only timeline row within the approved artwork boundary.
- [x] Build/install on the Pixel 3 XL; inspect the approved regular daily state and hourly state, then capture actual installed screenshots.
- [x] Directly browse across dates and verify each high travels with its date; verify the selected-time playhead and badge remain distinct and that daily drag, hourly scrub, and scale toggle retain behavior.
- [x] Verify an unavailable high only if the installed active-catalog source provides that state; otherwise record it as not observed and inspect the typed missing-value path without inventing a screenshot claim.
- [x] Preserve captures and exact verification outcomes under `.codex/test-artifacts/114-p2-daily-high-timeline/`, inspect `git diff --check` and the final diff, and close the cycle with actual results and limitations.

## Execution results

- `python scripts/dev.py run --serial 8AKY0M32X`: PASS; debug APK assembled, streamed-installed, and launched on the Pixel 3 XL.
- Installed capture review: PASS at 1440×2960 px (411×846 dp), default font scale `1.0`, animator duration scale `1.0`. The daily capture shows each visible date's projected high below its event icon and timeline divider; the selected high is highlighted and remains visibly separate from the selected-time badge. The browsed daily capture shows the selected date and its own high moving together. The hourly capture has no daily-high row and retains hour labels, ticks, divider, playhead, and time badge.
- Direct device interactions: PASS. Double-tap toggled into hourly mode with the displayed selection unchanged at 9:39 AM. A horizontal hourly scrub advanced selection to 10:34 AM. Double-tap returned to daily mode, and a daily drag moved the centered selection from Friday to Thursday with the matching high changing from 63° to 72°. No daily drag, hourly scrub, or scale-toggle regression was observed.
- Missing high: NOT OBSERVED in the active catalog captures; inspected nullable `CatalogDayView.high` projection and UI fallback render the approved `HI —` marker without borrowing or deriving a value.
- Verification: `git diff --check` PASS; final diff reviewed. No unit or instrumentation tests were run, per the plan. RTL, TalkBack, non-default font scaling, reduced-motion, and broad system/environment acceptance were not checked and remain outside this slice.
- Evidence: `.codex/test-artifacts/114-p2-daily-high-timeline/README.md` and its four actual installed PNG captures.

## Acceptance and evidence

Installed visual states are the active-catalog daily and hourly timelines on the Pixel 3 XL portrait reference viewport (411×846 dp), using the device's default font and motion settings. The focused date-browsing check uses the daily state; hourly mode is a preservation comparison. Non-default font scaling, RTL, TalkBack, reduced-motion, and broad system/environment matrices are outside this design slice and are not claimed.

- Installed Pixel 3 XL daily capture shows each visible date's own `HI` value beneath its event icon and below the divider, aligned to its date and readable against the scene.
- Focused browsing confirms values move with dates and remain distinct from the selected-time playhead/badge; no collision occurs with the navigation area.
- Hourly capture has no daily-high row and preserves hourly labels, ticks, divider, playhead, time badge, forecast facts, and readability.
- Focused installed interactions confirm daily drag, hourly scrub, and scale toggle retain their existing behavior and selected-time meaning.
- Missing values remain unavailable; no zero, adjacent-date, or hourly-derived high is displayed.
- Capture actual approved daily/hourly states and scoped interactions under `.codex/test-artifacts/114-p2-daily-high-timeline/README.md`; eventual history is `.codex/history/114-p2-daily-high-timeline.md`, with archived plan `.codex/history/plans/114-p2-daily-high-timeline.md`.
- No unit/instrumentation tests are planned or authorized in this UI slice. RTL, TalkBack, non-default font scaling, reduced-motion, and broad system matrices remain outside its scope. A build/preview alone does not meet visual acceptance.

## Risks / assumptions

- The daily overview projection already computes a daily-high `PresentedDailyTemperature`; this plan surfaces that typed value on each `CatalogDayView` rather than recalculating it.
- The current timeline canvas places weekday labels, daily event symbols, divider/playhead, and selected-time badge within a compact vertical area. The approved layout must reserve readable space below the divider for highs without obscuring the playhead/badge or navigation area; adjust timeline layout only as needed to achieve that approved arrangement.
- The board's sample temperatures and symbols illustrate placement only. Runtime values and event identities remain projection-driven.
- Any later change to the approved readout scale, label, missing-value treatment, or hourly scope requires revising the board and plan before implementation.

## Out of scope

Daily drag/noon correction (P.2.19 is cancelled), high/low calculation changes, new forecast facts, data/provider changes, event mappings or artwork, central readout redesign, hourly high labels, fixture/debug timeline changes, broad accessibility/system acceptance, and unrelated UI work. No app code has been changed in this planning cycle.
