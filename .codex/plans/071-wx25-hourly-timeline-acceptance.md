# Cycle plan — 071-wx25-hourly-timeline-acceptance

State: ACTIVE
Roadmap: WX.25
Objective: Complete installed WX.25 acceptance with the dense 44-date hourly SIMULATED catalog, including timeline scrub, full day/hour browsers, DST, and selected-row TalkBack time

## Production boundary

This is a fresh installed acceptance attempt after cycles 066–069 and the completed WX.10R data expansion in cycle 070. The SIMULATED dataset identity is `wx22-debug-simulated-v3`, and the approved hourly event windows are in `.codex/concepts/WX.10R-simulated-hourly-windows.md`. The owner approved the required chance/wind value amendment in `.codex/concepts/WX.25-simulated-chance-wind-completeness.md`: every SIMULATED hourly fact must have precipitation chance and sustained wind speed, filling only missing values with explicit zero while preserving non-null anchor values. The WX.25 concept `.codex/concepts/WX.25-timeline-44-days.png` and editable SVG remain the approved screen hierarchy and interaction contract. The owner explicitly described the current UI as planned but not final and directed functional data/timeline behavior before polish; acceptance is functional and evidence-focused, not a polish review.

**Owner-directed test-scope update (2026-10-29):** WX.29's standalone cross-app accessibility suite is cancelled. Keep this cycle's checks within WX.25's timeline, browser, and selected-time behavior. System-wide TalkBack, font-scale, orientation, RTL, reduced-motion, UI-performance, and cross-app persistence coverage will be considered in a separate test roadmap after the basic first-tier features work.

Inspect and exercise the installed Pixel 3 XL debug catalog picker, v3 SIMULATED source, canonical projection/presenter, forecast browser, and timeline. Rebuild/reinstall is authorized for the owner-approved SIMULATED required-value amendment as well as evidence-reproduced in-scope UI corrections. Correct only data behavior in `app/src/main/java/com/oxygen/weather/timeline/data/catalog/SimulatedCatalogSource.kt` under the concept amendment and presentation/UI behavior within the WX.25 boundary. Permitted UI owners: `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`. Other ownership or a new interaction model requires a separate bounded plan.

No visual redesign or final polish is in scope. Preserve one real-hour fact per 44-date SIMULATED date, sparse provider semantics, event windows, absolute selected time, and truthful fact-sample time. The owner-approved SIMULATED chance/wind amendment is the sole synthetic-value completeness change in this cycle; it does not change LIVE/provider data.

## Functional invariants

- Activate Chicago SIMULATED v3 through the debug picker. The active catalog, 44-date window, local bounds, condition/phenomenon distribution, and expected support must agree with cycle 070's installed inventory, with precipitation chance and sustained wind speed present on every hourly fact.
- The exact selected `Instant` remains authoritative through zoom, tap, hold, drag, browser selection, and dismiss. The sampled fact is the correct hourly fact at or nearest the selected instant under the approved projection; selection cannot jump during scale changes.
- Verify daily tap, immediate daily drag, stationary hold into uniform hourly scale, hourly drag, release persistence, main-area exit, and return to daily. The full window clamps at D−30 and D+13; no invented interval beyond the catalog.
- The browser exposes 44 local day rows and all actual hourly fact rows for a selected day, including 24 ordinary rows and both distinct 1 AM instants on Chicago November 1, 2026. Daily selection uses the local-noon overview anchor; hourly selection uses the exact chosen fact instant.
- Source/product/freshness/historical-vs-forecast labeling, condition/phenomenon facts, separate precipitation and wind facts, explicit zero, and unrelated missing values remain truthful. SIMULATED precipitation chance and sustained wind speed are never missing; missing source-anchor values become explicit zero. Decorative interpolation cannot create facts.
- TalkBack can enter the browser, scroll daily and hourly lists, focus/select rows, announce the selected hour, and return via Done. Record the exact selected row and actual spoken time only when directly observed; a hierarchy alone is not spoken-output evidence.
- Record responsiveness qualitatively and gesture arbitration with evidence. Do not claim frame-time performance without measurement.

## Steps

- [ ] Run `python scripts/dev.py workflow`; confirm device `8AKY0M32X`, app/build identity, timezone, resolution/density, font scale, TalkBack setting, display, input route, selected catalog, and current main state before new input. Capture initial screenshot/hierarchy.
- [ ] Reopen the installed debug picker, confirm Chicago SIMULATED v3 identity, activate it, and open the immersive timeline. Capture source identity and initial selected/sample times.
- [ ] Scrub daily across D−30, representative dates for every condition and phenomenon, today D, the Nov 1 fall-back date, and D+13. Exercise tap, immediate drag, hold-to-hourly, hourly drag, release, return-to-daily, and bounds. Record selected `Instant`, fact `validTime`, local labels, values, and provenance at checkpoints.
- [ ] Open the day/hour browser. Scroll daily rows at beginning, middle, event cases, DST date, and end. For representative ordinary and DST dates, scroll hourly rows through beginning/middle/end; confirm all 24/25 rows, the duplicate wall-clock hour is distinguished by absolute time/offset, and selecting a row agrees with the main timeline before and after Done.
- [x] TalkBack selected-hour verification — owner confirmed “Talkback is verified for this slice; I approved it.” Record this owner approval without inventing a transcript. ADB hierarchy/focus probe remains separate and is not used as spoken-output evidence.
- [ ] Capture the installed portrait main and browser states at font scales 1.0 and 1.3. Review an event/day state and a night state. Record qualitative responsiveness, predictable gesture ownership, and readable fact/label dispositions for WX.25's changed timeline behavior. Do not expand this slice into system-wide accessibility and environment testing.
- [ ] Restore font scale 1.0 and TalkBack to their initial state. Verify every generated SIMULATED fact and expected support includes the two required fields. If an in-scope UI mismatch is reproduced, change only the owning presentation/UI files, reinstall as needed, and repeat that scenario. Run workflow/contract and `git diff --check`; do not add or run tests under the repository instruction unless the owner separately requests them.

## Acceptance/evidence

Evidence directory: `.codex/test-artifacts/071-wx25-hourly-timeline-acceptance/`; closeout `.codex/history/071-wx25-hourly-timeline-acceptance.md`; archived plan `.codex/history/plans/071-wx25-hourly-timeline-acceptance.md`.

Required evidence:

- Device/build/source/location/timezone/asOf/window identity and initial/restored settings.
- Complete 44-date catalog range, beginning/end clamps, all condition and phenomenon checkpoints, DST repeated hour, and selected `Instant` versus fact sample for all timeline gestures.
- Browser day list beginning/middle/end; hourly list beginning/middle/end on ordinary and DST dates; selected/checked state, Done behavior, and main-screen agreement.
- Owner-operated TalkBack action sequence with actual spoken selected-time observation, or `NOT RUN` with the exact blocker and a named follow-up.
- Installed default/1.3 portrait screenshots and hierarchy for relevant states; evidence-backed responsiveness, gesture ownership, and readability dispositions.
- Exact commands and outcomes. Automated tests are not authorized by the repository instruction and remain NOT RUN.

## Risks / assumptions

- Cycle 070's installed build and v2 data may remain installed; rebuild/reinstall and activate v3 because dataset/product identity changes invalidate those stored values.
- Device date/asOf can advance during review. Recompute D from catalog `asOf` in Chicago and use that fixed window for every checkpoint.
- The current time-axis may not visually distinguish repeated local hours in browser labels even though the `Instant`s differ. Treat that as a reproducible WX.25 issue only if row semantics and display evidence confirm it.
- TalkBack spoken output requires actual owner operation or reliable direct audio observation; do not substitute a hierarchy dump.

## Out of scope

- Final visual polish, atmosphere-engine work, weather illustration redesign, production startup/location changes, live provider completeness, synthetic fixture changes beyond the approved chance/wind zero fill, official alerts, release packaging, and deprecated oxygenWX.
- System-wide TalkBack, font-scale, orientation, RTL, reduced-motion, UI-performance, and cross-app persistence suites are outside WX.25. The owner cancelled WX.29 as a standalone accessibility suite on 2026-10-29; these checks will be organized in a separate test roadmap after the basic first-tier features work.
- Automated tests unless explicitly authorized by the owner.
