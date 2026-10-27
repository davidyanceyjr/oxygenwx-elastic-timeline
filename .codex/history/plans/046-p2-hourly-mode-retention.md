# Cycle plan — 046-p2-hourly-mode-retention

State: ACTIVE
Roadmap: P.2.15
Objective: Keep hourly precision active after release until an explicit main-screen or timeline tap exits it, with clear selection behavior and preserved timestamp semantics.

## Production boundary

May change only:
- `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` for the owner-approved interaction decision.
- `docs/ROADMAP.md`, `.codex/current.md`, this cycle plan, `.codex/concepts/P.2.15-hourly-mode-retention.svg`, `.codex/test-artifacts/046-p2-hourly-mode-retention/`, and the cycle closeout/archive records.
- `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` for the gesture state transition and explicit tap exits.

No data, presentation model, renderer, build configuration, or deprecated oxygenWX files.

## Functional invariants

- `selectedHour` remains the sole authority; entering/exiting hourly mode alone never changes it.
- A stationary hold selects the time under the touch and activates the uniform hourly scale; release ends the gesture but keeps that scale active.
- A tap on the main forecast area exits hourly mode and preserves `selectedHour`.
- A tap on the timeline exits hourly mode and selects the time under the touch.
- Dragging while hourly mode is already active continues hour-level scrubbing; a newly activated hold-and-drag also remains hour-level until release.
- Daily-mode immediate drags and taps keep their existing meanings. Accessibility scale actions and forecast browser behavior remain available.
- No visible mode-control row or instructional text is introduced.

## Steps

- [x] Read the governing specs, current cycle state, gesture implementation, roadmap, UI workflow, and confirm the authorized Pixel 3 XL is connected.
- [x] Record the owner's 2026-10-27 waiver of the UI artwork requirement for P.2.15 only; retain the storyboard as an unapproved planning aid.
- [x] Implement sticky hourly mode and explicit main-screen/timeline tap exits; update only the listed interaction authority.
- [x] Run workflow, contract, debug build/install, installed gesture review and capture evidence; do not add or run tests.
- [x] Review accessibility semantics, RTL direction handling, reduced-motion applicability, and compact/large-font applicability; record any unverified matrix.
- [x] Inspect `git diff --check` and final diff, record exact evidence/limits, close cycle and archive plan.

## Acceptance/evidence

The owner waived the UI artwork requirement for P.2.15 only on 2026-10-27. No tests will be added or run, per the user's instruction. Run:
- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `ANDROID_HOME=/home/opsman/Android/Sdk JAVA_HOME=/home/opsman/Android/jdk-17 PATH=/home/opsman/Android/jdk-17/bin:$PATH python scripts/dev.py run --serial 8AKY0M32X`
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/046-p2-hourly-mode-retention/hourly-sticky-installed.png`
- `git diff --check`

On the installed Pixel 3 XL, directly exercise: hold+drag then release (hourly remains); tap main forecast area (daily, same selected time); reactivate hourly; tap a different timeline position (daily, selected moment moves to that target); activate hourly again and drag after release (hourly scrubbing remains available). Save screenshots for hourly-after-release and daily-after-each-exit, and record observed selection labels and gesture sequence in the evidence README.

Visual objective: preserve the existing full-screen atmosphere and timeline; no new chrome. Installed state: Pixel 3 XL portrait, current default font and locale. Compact/large-font layout is not changed; RTL mapping must remain consistent because tap coordinates use the existing logical-axis conversion; reduced-motion has no new animation path, though the existing scale animation is not modified. This cycle records source-level applicability and does not claim the full accessibility, RTL, font, or reduced-motion matrix.

## Risks / assumptions

The tap-to-exit gesture on the main summary shares an input region with double-tap-to-now. Preserve double-tap precedence and selected-time behavior; record any device-level ambiguity. Timeline taps in hourly mode should resolve against the hourly axis before collapsing.

The storyboard is retained for clarity but is not approved concept artwork; the owner waived that gate for this slice only. Preserve the existing app composition and the interaction constraints in this plan.

## Out of scope

No changes to daily-axis physics, long-press threshold, scale animation timing, accessibility custom-action meaning, forecast facts, data architecture, atmosphere effects, broad accessibility acceptance, or production integration. Do not resolve unrelated build/test failures.
