# Cycle plan — 011-hour-label-alignment

State: ACTIVE
Roadmap: P.2.5a
Objective: Make the forecast summary's displayed hour use the same nearest-hour convention as the hourly tape's selected tick, retaining fractional selection and interpolated values while dragging.

## Production boundary

Change only forecast presentation's selected-hour label in `app/src/main/java/com/oxygen/weather/timeline/presentation/ForecastProjection.kt`. No provider/model/data changes, no gesture changes, no new visible controls, and no sibling oxygenWX changes.

## Functional invariants

- `selectedHour` remains the absolute timestamp authority and stays fractional during a drag.
- Forecast values continue to use the existing interpolation at the fractional selected offset.
- The displayed forecast hour and selected hourly tape label use the same nearest-hour rule.
- Release/collapse preserves the rounded selected hour already chosen by existing behavior; daily presentation remains unchanged.
- Timeline responsiveness, gesture ownership, readability, bounds, and DEMO DATA semantics remain unchanged.

## Ordered steps

1. Confirm PLANNED pointer and workflow status; activate this reviewed plan.
2. Make the smallest presentation-only label-rounding change, using the same rounding convention as `ElasticForecastTimeline`.
3. Run the authorized debug build, install/launch on Pixel 3 XL `8AKY0M32X`, and capture active fractional-scrub, post-release daily, and stationary-reentry hourly states.
4. Confirm summary hour equals the selected center tick during fractional movement; after release and re-entry confirm the same rounded hour. Review forecast values and central timeline readability; check no selection quantization or data change.
5. Save commands, device conditions, screenshots, findings, and limitations under `.codex/test-artifacts/011-hour-label-alignment/`. Run `git diff --check`, inspect the final diff, close the cycle, and keep P.2.5 blocked until its full gesture acceptance is re-run.

## Visual objective and conditions

Resolve the observed one-hour disagreement between the selected hourly tick and forecast summary during a fractional scrub while preserving interpolation. Required installed viewport is Pixel 3 XL `crosshatch`, using the existing 1260×2240 override at 560 dpi when available. Review current/default font scale, LTR locale, and current animation setting; record all values and do not change system settings. Capture active fractional-hour selection, collapsed daily state, and re-entered hourly state.

## Authorized verification

- `python scripts/dev.py workflow` before execution and after closeout-record updates.
- `python scripts/dev.py build` with command-scoped `JAVA_HOME=/usr/lib/jvm/java-27-openjdk`, its `bin` prepended to `PATH`, and `ANDROID_HOME=/home/opsman/project_git/oxygenWX/.android-sdk`.
- `python scripts/dev.py run --serial 8AKY0M32X` and `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/011-hour-label-alignment/<state>.png` with the same environment.
- Manual installed gestures using adb `motionevent` to keep contact across the stationary hold, scrub, and release.
- `git diff --check` and final diff review.

No unit tests, lint, contract, aggregate check, or unrelated build task is authorized by this cycle.

## Risks and limits

- Rounding the label identifies the nearest hourly fixture point while forecast values remain interpolated; the prototype's synthetic-data labeling remains necessary.
- A build and screenshots do not establish TalkBack, RTL, reduced-motion, large-font, or constrained-layout acceptance; those remain separate slices.
- If Pixel 3 XL is unavailable, record the exact blocker and do not substitute a viewport.

## Out of scope

No changes to scrubbing math, selection quantization, data interpolation, gesture arbitration, accessibility semantics, visual layout, or production oxygenWX.
