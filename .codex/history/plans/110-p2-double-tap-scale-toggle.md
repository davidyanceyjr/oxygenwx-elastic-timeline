# Cycle plan — 110-p2-double-tap-scale-toggle

State: ACTIVE
Roadmap: P.2.17
Objective: Replace timeline hold-to-enter-hourly with a timeline double-tap scale toggle; remove timeline single-tap selection while preserving drag behavior, selected-time continuity, and existing gesture ownership.

Plan readiness: FINAL REVIEW COMPLETE — the owner directed on 2026-11-05 that timeline single-tap selection be removed and drag behavior retained, then approved the revised combined behavior/UI artwork board as shown on 2026-11-05. The owner authorized execution on 2026-11-05 and the bounded exception was recorded in both governing specifications before activation.

## Scope and baseline

The timeline is rendered through `CatalogTimeLabScreen` for the active catalog and `TimeLabScreen` / `ElasticForecastTimeline` for fixture-preview and debug-profile flows. The same daily/hourly gesture contract must hold wherever the timeline is presented. The implementation boundary was `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`, `TimeLabScreen.kt`, and `ElasticForecastTimeline.kt`, with the approved specifications, roadmap, cycle plan, and evidence records updated for this slice lifecycle.

Current behavior baseline: a stationary hold on the daily timeline enters hourly mode; daily drag browses dates; hourly drag scrubs; hourly mode remains after release; daily and hourly timeline single taps currently select a date/hour (hourly tap also returns to daily); a main forecast-area tap collapses without changing the selected instant; main-display double-tap retains its fixture realignment meaning; and the timeline has a screen-reader scale action. This slice changes timeline single taps to no-ops, per the owner's bounded direction; it does not change main-area taps.

## Functional invariants

- A double-tap on the timeline toggles daily/hourly scale in either direction.
- Scale-only toggles preserve the exact selected instant and keep it at the center playhead; the double-tap coordinate does not select another date/hour.
- A single tap on the daily or hourly timeline does not select a date/hour or change scale. Immediate horizontal dragging remains daily navigation. A stationary hold and hold-then-drag do not activate hourly scale.
- Hourly dragging, hourly-mode retention on release, main-area single-tap collapse, the separate main-display double-tap realignment action, and the screen-reader scale action retain their current behavior.
- A recognized timeline double-tap causes one toggle and no single-tap selection. Gesture ownership remains predictable and the timeline stays responsive.
- No visible mode-control row or instructional hint is added. Forecast facts, labels, accessibility semantics, and DEMO DATA honesty remain intact.

## Steps

- [x] Record owner approval of the revised combined behavior/UI artwork board, constraints, and finalized scope.
- [x] Inspect both rendered timeline paths and implement the smallest consistent gesture change.
- [x] Complete focused daily/hourly interaction and selected-instant checks on installed active-catalog and fixture/debug flows.
- [x] Capture and inspect the scoped daily/hourly states, verify readability/responsiveness/gesture ownership, record actual evidence, inspect final diff, and close the cycle.

## Acceptance/evidence

The prior board package `.codex/concepts/P.2.17-double-tap-scale-toggle.svg` and behavior contract `.codex/concepts/P.2.17-double-tap-scale-toggle.md` were approved on 2026-11-05 as written, including timeline single-tap selection. After the owner's 2026-11-05 direction to remove timeline single-tap selection, the revised combined board package at those paths was approved as shown on 2026-11-05. Approval covers the revised no-op timeline single taps, preserved daily drag navigation and hourly scrubbing, main-area single-tap collapse, main-display double-tap realignment, selected-time continuity, and no added persistent controls. The board governs scope and acceptance; installed screenshots and scoped interaction checks remain required for execution.

Execution exit: **PASS** on the installed Pixel 3 XL reference viewport for active-catalog and fixture/debug surfaces. Double-tap toggles both scales without changing the selected instant; single taps do not select or change scale; daily dragging navigates and stays daily; holds do not activate hourly; hourly dragging scrubs and release retains hourly mode; main-area single-tap collapse preserves selection; fixture main-area double-tap still realigns. Captures show the selected-time cue centered and forecast facts readable. Actual screenshots and interaction outcomes are recorded in `.codex/test-artifacts/110-p2-double-tap-scale-toggle/README.md`. `python scripts/dev.py run --serial 8AKY0M32X` passed on the final source state. No tests were added or run. RTL, TalkBack, font-scale, reduced-motion, and broader system/environment matrices were not run, per slice boundary. Existing screen-reader scale action was preserved but not separately exercised.

## Risks / assumptions

- The intended target is a double-tap on the timeline tape, distinct from the existing main forecast-area double-tap. The owner can revise this boundary when reviewing the board.
- Daily and hourly single-tap selection currently defined by the controlling prototype specification is intentionally removed for this bounded slice by the owner's direction. The implementation must recognize double-tap without applying a single-tap selection; choose the recognizer only after inspecting current code.
- Both timeline render paths duplicate gesture handling, so consistent behavior may require edits in both. No timeline rendering redesign is intended.
- Selected-time projection remains authoritative across scale animation, including timestamps that are not at local noon.

## Out of scope

- Changing main forecast-area tap or double-tap semantics, fixture reset target, accessible scale action, data/presentation rules, screen layout/artwork beyond the revised board states, zoom/time-axis math, or drag navigation meaning.
- Adding visible controls/instructions, changing deprecated oxygenWX files, broad accessibility/system acceptance, or unrelated gesture/performance refactors.
