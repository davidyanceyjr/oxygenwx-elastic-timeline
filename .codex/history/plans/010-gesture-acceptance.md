# Cycle plan — 010-gesture-acceptance

State: ACTIVE
Roadmap: P.2.5
Objective: Record installed Pixel 3 XL evidence for timeline immediate-drag versus hold-then-drag arbitration, hourly scrubbing, release/collapse, selected-time continuity, responsiveness, gesture predictability, and readability. This is an evidence-gathering cycle; confirmed defects are recorded for separate planning.

## Production boundary

Review the existing standalone prototype only. Do not modify application source, Gradle configuration, dependencies, or the sibling oxygenWX checkout. Do not create a fix within this cycle. If an exit check fails, retain the evidence, identify the confirmed defect, and propose a separately bounded implementation slice before any fix.

## Functional invariants

- Immediate horizontal drag browses daily time without entering hourly mode.
- A stationary hold of about 480 ms followed by horizontal movement changes the whole tape to the uniform hourly scale and scrubs hour by hour.
- Releasing/collapsing the hourly scale preserves the selected absolute fixture timestamp; scale motion does not move the selected time independently.
- Interactions remain within displayed fixture data and do not produce an accidental mode change or gesture ownership transfer.
- Forecast values and timeline labels remain readable in the captured states.
- Synthetic-data semantics and prototype accessibility behavior remain unchanged; TalkBack acceptance is covered by P.2.7.

## Ordered steps

1. Confirm workflow status, device serial/model, Android version, display size/density/override, app foreground state, locale, font scale, and animation setting. Use the Pixel 3 XL reference device and the existing baseline viewport when available. Do not change system settings for this slice. If the required device is unavailable, capture the exact host/device result and close as blocked rather than substituting an unapproved viewport.
2. Launch the existing debug app on the Pixel 3 XL, capture the daily overview, and record its displayed selected day/time and visible forecast values. Keep the capture date, starting state, and coordinate/display configuration with the evidence.
3. Exercise immediate horizontal tape drags in both directions for three trials each. Record whether each begins in daily navigation, the selected time outcome, any unintended hourly-scale activation, and perceived response delay. Capture representative outcomes and record each trial in an observation table.
4. Exercise hold-then-drag in both directions for three trials each, holding stationary for at least the specified approximately 480 ms threshold before moving. Capture the hourly scale while active, then scrub hour by hour and record selection changes, label alignment at the center playhead, and any gesture ambiguity or delay.
5. Release after hourly scrubbing, capture the collapsed daily overview, and compare the selected fixture date/hour before the scale change, during hourly mode, and after release. Use visible selected labels and forecast summary values; if the UI does not expose enough information to establish hour continuity after collapse, record the limitation rather than infer a pass.
6. Review all captures for responsiveness, predictable gesture ownership, and readable forecast values/timeline labels. Assign an evidence-backed pass/fail disposition to each. Do not fix issues; list confirmed defects and the minimal follow-up slice needed.
7. Save original device screenshots, interaction/reproduction details, observations, and findings under `.codex/test-artifacts/010-gesture-acceptance/`. Inspect the records and final diff, run `git diff --check`, and close the cycle with exact outcomes and limits.

## Visual objective

Use installed captures to judge whether the selected day/hour remains centered and readable and whether the forecast summary and timeline labels remain legible throughout daily dragging, hourly scrubbing, and release/collapse. This cycle records the existing UI; it does not change visual styling.

## Applicable UI conditions and installed states

- Required viewport: installed Pixel 3 XL (`crosshatch`); use the recorded 1260×2240 active display override at 560 dpi when it is still available, otherwise record the actual viewport and any deviation.
- Review at the device's current/default font scale, LTR locale, and motion setting. Record these values; do not alter them in this slice.
- Large-font/constrained-layout review, RTL chronology, reduced-motion behavior, and manual TalkBack acceptance are not acceptance claims for this cycle; they remain in P.2.6, P.2.9, P.2.8, and P.2.7 respectively.
- Required installed states: daily overview baseline; immediate-drag outcomes; hourly scale while held; hourly scrub outcomes; daily overview after release/collapse.

## Acceptance and evidence

- Each arbitration path is exercised in both directions for three trials, and all trial results are recorded.
- Actual screenshots show the baseline, daily drag result, active hourly scale/scrub, and post-release daily state. Captures include device identity and viewport details.
- The observed selected date/hour remains continuous through daily-to-hourly and hourly-to-daily scale changes, or the exact failed/unobservable transition is recorded.
- Responsiveness, gesture predictability, and readability each receive a pass/fail disposition backed by observations and captures. Any confirmed defect has a concrete, separate follow-up recommendation.
- Target evidence directory: `.codex/test-artifacts/010-gesture-acceptance/`.
- Target history: `.codex/history/010-gesture-acceptance.md`.
- The roadmap slice is DONE only if the gesture evidence and all three cross-cutting dispositions are recorded. If required installed evidence cannot be obtained, record the exact blocker and close without marking P.2.5 DONE.
- A failed or unobservable selected-time continuity check is a recorded limitation, not a pass. Close the cycle with the evidence and a bounded follow-up recommendation, but do not mark P.2.5 DONE unless continuity is established.

## Authorized verification

- `python scripts/dev.py workflow` before execution and after closeout-record updates.
- Confirm the Pixel 3 XL with `adb devices -l` and device properties needed to identify display, locale, font scale, and motion setting.
- With command-scoped `JAVA_HOME=/usr/lib/jvm/java-27-openjdk`, its `bin` prepended to `PATH`, and `ANDROID_HOME=/home/opsman/project_git/oxygenWX/.android-sdk`, use `python scripts/dev.py run --serial 8AKY0M32X` to install/launch the existing debug app and `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/010-gesture-acceptance/<state>.png` to capture actual installed states. If the connected serial differs, record and use the confirmed Pixel 3 XL serial.
- Use installed-device interaction/reproduction steps for the gesture trials; record the exact gesture durations, directions, coordinates, and commands used. No unit tests, lint, contract, aggregate check, or unrelated build task is authorized by this evidence-only cycle.
- `git diff --check` before closeout.

## Risks / assumptions

- The Pixel 3 XL and its previously recorded serial `8AKY0M32X` may not be connected when the cycle is executed; confirm before relying on it.
- A screenshot captures a state but cannot alone establish response latency or timestamp continuity; interaction observations must accompany the images.
- The existing baseline used an active display override. Coordinates must be rechecked against the actual viewport before use.
- The three-trial count is a bounded repeatability sample, not a statistical reliability claim.

## Out of scope

No implementation fixes, visual redesign, accessibility/TalkBack acceptance, font-scale or constrained-layout review, RTL review, reduced-motion review, production integration, device-setting changes, or production oxygenWX changes.
