# Cycle plan — 048-p2-hourly-scrub-responsiveness

State: ACTIVE
Roadmap: P.2.16
Objective: Make uniform hourly scrubbing continuous, responsive, and directionally consistent without changing selected-time semantics or gesture ownership.

## Production boundary

May change only:

- `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`
- `app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt`, if the focused correction requires a pure projection change
- `.codex/test-artifacts/048-p2-hourly-scrub-responsiveness/` and cycle records
- `docs/ROADMAP.md` only to record the completed result and restore WX.6 as the sole NEXT slice at closeout

No forecast fixture/data changes, screen redesign, new controls, broad Compose refactor, deprecated oxygenWX changes, or unrelated cleanup. If baseline evidence points outside these paths, stop and revise the bounded plan before editing.

## Functional invariants

- `selectedHour` remains the authoritative absolute fixture offset; changing or settling scale alone does not move it.
- Hourly-mode drag remains continuous during contact; preserve fractional selection and interpolated forecast presentation while the visible hour label/tick uses the established nearest-hour convention.
- Equal finger travel produces comparable time movement in either direction; scale animation progress cannot make sensitivity vary during one scrub.
- Preserve the 480 ms stationary hold, touch-slop arbitration, uniform hourly spacing, release persistence, explicit main/timeline tap exits, fixture-bound clamping, accessibility scale action, and forecast browser.
- Preserve timeline readability and existing gesture ownership. Do not add visible scale controls or instructional text.

## Verified baseline and assumptions

- `ElasticForecastTimeline.kt` uses a 260 ms scale tween and reads its animated value when converting each pointer delta to time. This means the pixels-per-hour mapping changes during the scale transition.
- The hourly drag path rounds each movement update to a whole hour, despite the existing fractional-selection contract. This can make motion visibly step rather than track the finger.
- The Canvas considers all source hours on each draw and filters off-screen marks. Treat draw cost as a hypothesis only; change it only if installed comparison identifies it as a material contributor.
- The owner reports lag and inconsistent movement in both directions after long-press. Capture exact gesture steps and observed baseline before editing; do not claim a measured frame-time cause without instrumentation.

## Steps

- [x] Confirm workflow, connected Pixel 3 XL, current source, and clean/known working-tree changes; capture the reported hourly-scrub behavior in both directions and note gesture timing, selected labels, bounds, and perception.
- [x] Identify whether the measurable/visible problem comes from changing scale mapping, quantized selection, rendering cost, or a combination; keep diagnosis within the listed files.
- [x] Implement the smallest correction that gives a stable hourly scrub mapping and continuous selection while preserving all invariants above. Optimize Canvas work only if baseline evidence supports it.
- [x] Run workflow and contract; build/install on Pixel 3 XL; repeat matched forward/backward strokes during and after precision entry; record results and screenshots in the evidence directory. Do not add or run tests.
- [x] Review RTL direction handling, reduced-motion behavior, selected-time stability, clamping, readability, and predictable gesture ownership; state which installed conditions were exercised and which remain unverified.
- [x] Inspect `git diff --check` and the final diff, record exact commands/results/limitations, close cycle 048, archive the plan, mark P.2.16 DONE, and restore WX.6 as the sole NEXT slice.

## Acceptance and evidence

Install state: Pixel 3 XL (`8AKY0M32X`), portrait, default font scale and LTR locale for matched baseline/final scrub trials. Include the current supported build environment when needed: `JAVA_HOME=/home/opsman/Android/jdk-17`, `ANDROID_HOME=/home/opsman/Android/Sdk`.

Evidence directory: `.codex/test-artifacts/048-p2-hourly-scrub-responsiveness/`.

Required installed review:

- Enter hourly precision with a stationary hold and scrub in both directions using matched finger travel; compare selected-time change and responsiveness.
- Repeat with a drag immediately after precision activation and after the scale animation has settled.
- Verify a held scrub tracks fractional selected time without visible whole-hour stepping, while the summary and selected label follow the established nearest-hour convention.
- Verify the selected timestamp does not change solely because the scale animates; confirm fixture bounds and explicit exit/re-entry behavior remain intact.
- Capture actual installed screenshots for hourly mode and selected times at the center and at representative bounds; record gesture observations in the README.
- Record the responsiveness, predictable gesture ownership, and readability dispositions. Check RTL direction mapping and reduced-motion applicability; do not claim a full accessibility matrix.

Allowed verification: `python3 scripts/dev.py workflow`; `python3 scripts/dev.py contract`; `JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:$PATH python3 scripts/dev.py run --serial 8AKY0M32X`; `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/048-p2-hourly-scrub-responsiveness/<state>.png`; `git diff --check`. Do not add or run tests unless the user asks.

## Concept artwork waiver

The owner waived the concept artwork requirement for P.2.16 only on 2026-10-27. `.codex/concepts/P.2.16-hourly-scrub-responsiveness.svg` remains an unapproved planning aid; it is neither an implementation gate nor installed evidence. This waiver does not apply to any other slice.

## Risks and assumptions

- Removing whole-hour quantization should restore the existing fractional drag contract, but the visible label remains hour-rounded. Confirm that this reads as continuous without making the selected hour confusing.
- A fixed hourly sensitivity during precision entry must not create a coordinate jump at activation; the absolute selected time stays authoritative even if the axis geometry animates.
- No frame-time profiler is currently specified. If perceptual review cannot isolate the source, use low-risk, bounded installed comparisons and do not claim a quantified frame-rate improvement.
- Reduced-motion configuration may alter/disable the transition; preserve identical selected-time semantics. RTL uses existing logical direction conversion and needs a direct regression check if gesture math changes.

## Out of scope

No changes to the forecast horizon, daily browsing sensitivity, long-press duration, hourly spacing, forecast interpolation model, atmosphere renderer, location/provider architecture, accessibility interaction model, production branding, or full-device acceptance matrix. No tests, lint, or unrelated checks.

## Difficulty and model fit

Difficulty: 5/10. The likely correction is narrow, but pointer sampling, animated coordinate mapping, Canvas redraw, absolute-time continuity, and real-device feel interact. A credible path needs matched installed trials in both directions.

Relative model fit: GPT-6.1 Sol is the least costly suitable option for the bounded Compose gesture/performance correction; escalate to GPT-6 Astra only if measurement shows a cross-layer rendering or frame-scheduling issue that cannot be resolved in the timeline component.
