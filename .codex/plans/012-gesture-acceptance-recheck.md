# Cycle plan — 012-gesture-acceptance-recheck

State: PLANNED
Roadmap: P.2.5
Objective: Re-run installed Pixel 3 XL gesture, release/collapse, selected-time continuity, responsiveness, predictability, and readability acceptance after the P.2.5a label-alignment fix. Gather evidence only; record separate bounded follow-up slices for confirmed failures.

## Scope and production boundary

This is an installed evidence-gathering cycle for the standalone Oxygen Time Lab prototype. Do not modify application source, Gradle configuration, dependencies, production oxygenWX, or the sibling checkout. Do not implement fixes. Allowed new/updated records are limited to `.codex/test-artifacts/012-gesture-acceptance-recheck/` and this cycle's closeout at `.codex/history/012-gesture-acceptance-recheck.md`; retain screenshots, command output, observations, and a README in the artifact directory. A confirmed failure is documented with evidence and a proposed separate roadmap slice; it does not authorize a fix in this cycle.

## Dependencies and prior evidence

- P.1.2 device baseline is DONE (`.codex/history/002-p1-pixel-install.md` and `.codex/history/007-visual-baseline.md`).
- P.2.1 uniform hourly scale and selected-time label is DONE (`.codex/history/003-time-label-magnification.md`).
- P.2.4 visible hints removal and scale action is DONE (`.codex/history/006-remove-timeline-hints.md`).
- P.2.5a summary/tick hour alignment is DONE (`.codex/history/011-hour-label-alignment.md`); it addressed the discrepancy found during the original P.2.5 review.
- Original P.2.5 evidence and the blocker investigation remain available in `.codex/history/010-gesture-acceptance.md` and `.codex/test-artifacts/011-blocker-investigation/README.md`. The follow-up confirmed a stationary hold after collapse can re-enter hourly mode without moving selection, making the rounded hour observable.

## Functional invariants

- Immediate horizontal dragging browses daily time and does not enter hourly mode.
- A stationary hold of about 480 ms followed by horizontal movement changes the whole tape to a uniform hourly scale and scrubs time.
- The selected absolute fixture timestamp remains authoritative through scale animation and release/collapse; re-entering hourly mode with a stationary center hold must not move the selection.
- The forecast summary hour and selected hourly tick follow the same nearest-hour convention while interpolated forecast values remain fractional during movement.
- Gestures remain within the fixture horizon, do not cause unintended scale changes or gesture ownership transfers, and preserve readable forecast values and timeline labels.
- Synthetic DEMO DATA semantics and the existing accessibility behavior remain unchanged. Manual TalkBack acceptance remains P.2.7.

## Ordered steps

1. Confirm the cycle is ACTIVE before execution and run `python scripts/dev.py workflow`. Confirm Pixel 3 XL identity and record serial, Android version, display size/density/override, locale, font scale, and animation setting. Do not change device settings. If the required Pixel 3 XL is unavailable, record the exact result and close as blocked without substituting another viewport.
2. Install/launch the existing debug app on the Pixel 3 XL. Capture the daily overview baseline, selected date/time, visible forecast values, and device conditions in the artifact README.
3. Exercise immediate horizontal tape drags in each direction for three trials. Record hold duration, direction, coordinates, observed scale, selected-time outcome, response delay, and any gesture ambiguity. Capture representative daily outcomes.
4. Exercise stationary hold-then-drag in each direction for three trials, holding at the center playhead for at least approximately 480 ms before moving. Capture the active hourly scale and scrub outcomes; record selected center labels, summary-hour agreement, response delay, and any gesture ambiguity.
5. Release/collapse after hourly scrubbing and capture the daily overview. Then hold stationary at the center playhead to re-enter hourly mode without moving it. Compare the selected date/hour before the scale change, during scrubbing, and after re-entry. Include a fractional-hour scrub so the nearest-hour labels and interpolated visual values can be reviewed together. Record any transition that remains unobservable; do not infer continuity from animation alone.
6. Review installed captures and observations for timeline responsiveness, predictable gesture ownership, and readability of forecast values and timeline labels. Give each of the three cross-cutting checks an evidence-backed pass/fail disposition. For every confirmed failure, document the reproduction and evidence and recommend a separately bounded follow-up slice; make no fix here.
7. Save original screenshots, reproduction details, trial observations, commands, device conditions, findings, and limitations under `.codex/test-artifacts/012-gesture-acceptance-recheck/`. Inspect the evidence and final diff, run `git diff --check`, update the roadmap only if a confirmed failure or changed eligibility requires a bounded replanning record, and close the cycle with exact results and limitations.

## Visual objective and applicable conditions

Use the installed review to determine whether daily dragging, hourly scrubbing, and release/collapse preserve selected-time continuity and readable forecast information while keeping gestures responsive and predictable. This cycle records existing behavior and does not change the UI.

- Required installed viewport: Pixel 3 XL (`crosshatch`), using the previously recorded 1260×2240 active display at 560 dpi when still available; otherwise record the actual viewport and deviation.
- Review current/default font scale, LTR locale, and current animation setting. Record them and do not change system settings.
- Required states: daily overview baseline; representative immediate-drag results; hourly scale after hold; hourly scrubbing including fractional selection; daily overview after release; hourly re-entry after release without movement.
- Large-font/constrained-layout, RTL, reduced-motion, and manual TalkBack reviews are excluded from this cycle and remain in P.2.6, P.2.9, P.2.8, and P.2.7 respectively.

## Acceptance and evidence

- Both arbitration paths are exercised in both directions for three trials per direction, and every trial is recorded.
- Actual installed screenshots show the required states and are accompanied by device identity and viewport/settings details.
- The selected date/hour is compared before, during, and after scale changes, including release/collapse followed by stationary hourly re-entry. Any failed or unobservable transition is recorded as such, never as a pass.
- Summary-hour and selected-tick agreement is reviewed during fractional scrubbing; fractional selection/interpolation behavior is not inferred solely from labels.
- Responsiveness, gesture predictability, and readability each receive a pass/fail disposition supported by interaction observations and captures.
- If all required installed evidence and checks pass, the P.2.5 exit is met. If the device is unavailable or a required check fails/remains unobservable, record the precise blocker/failure and close without marking P.2.5 DONE; identify any separate follow-up slice.
- Evidence target: `.codex/test-artifacts/012-gesture-acceptance-recheck/`.
- Closeout target: `.codex/history/012-gesture-acceptance-recheck.md`.

## Authorized verification

- `python scripts/dev.py workflow` before execution and after closeout-record updates.
- `adb devices -l` and read-only device-property inspection to identify the Pixel 3 XL, display, locale, font scale, and animation setting.
- With command-scoped `JAVA_HOME=/usr/lib/jvm/java-27-openjdk`, its `bin` prepended to `PATH`, and `ANDROID_HOME=/home/opsman/project_git/oxygenWX/.android-sdk`, use `python scripts/dev.py run --serial <confirmed-pixel-3xl-serial>` to install/launch the existing debug app and `python scripts/dev.py screenshot --serial <confirmed-pixel-3xl-serial> --output .codex/test-artifacts/012-gesture-acceptance-recheck/<state>.png` to capture installed states. If the connected serial differs from the historical `8AKY0M32X`, record and use the confirmed Pixel 3 XL serial.
- Use installed interactions for the trials; record exact gestures and commands. The prior cycle used adb `motionevent` to maintain contact across the stationary hold, scrub, and release; recheck coordinates against the actual viewport before using them.
- `git diff --check` and final diff/evidence review before closeout.
- No unit tests, lint, contract, aggregate check, or unrelated build task is authorized by this evidence-only slice.

## Risks and assumptions

- The Pixel 3 XL and its historical serial may not be connected at execution time; confirm identity and viewport before interaction.
- The old display override may no longer be active; record the actual viewport and do not assume historical touch coordinates remain valid.
- Screenshots alone cannot prove latency or selection continuity; trial observations and the stationary re-entry check are required.
- Three trials per direction are a bounded repeatability sample, not a statistical reliability claim.
- The previous P.2.5 run and blocker investigation are historical evidence; this cycle must repeat acceptance after the P.2.5a fix rather than treating old observations as current passes.

## Out of scope

No application or production-source changes, fixes, visual redesign, system-setting changes, production integration, tests, lint, contract checks, unrelated builds, TalkBack acceptance, large-font/constrained-layout review, RTL review, or reduced-motion review.
