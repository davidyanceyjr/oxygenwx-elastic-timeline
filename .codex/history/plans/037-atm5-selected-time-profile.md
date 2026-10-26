# Cycle plan — 037-atm5-selected-time-profile

State: CLOSED COMPLETE
Roadmap: ATM.5
Objective: Complete ATM.5's missing selected-time effect mapping and apply it through bounded ridge haze and symmetric turbulence preview rendering.

## Production boundary

Standalone Oxygen Time Lab only. Expected application and focused-test changes are limited to:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`
- `app/src/test/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualStateMapperTest.kt`
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` only if needed for explicit debug preview selection or selected-time review.
- `.codex/test-artifacts/037-atm5-selected-time-profile/`, this plan, the ATM.5 roadmap evidence/status, and `.codex/history/037-atm5-selected-time-profile.md`.

No forecast schema/provider changes, production integration, network/location behavior, gesture/navigation changes, or persistent preview controls.

## Functional invariants

- The forecast and `selectedHour` remain authoritative and unchanged by preview effects. Effect strengths are renderer-only and keyed only to the explicit debug preview mode and absolute selected-hour offset.
- Use independent normalized haze and turbulence samples: `(0, .10, .20)`, `(24, .25, .70)`, `(48, .55, .35)`, `(72, .85, .80)`, `(96, .45, .45)`, `(120, .15, .20)`. Interpolate each channel linearly between offsets, including fractional offsets, and clamp outside the range to the nearest endpoint.
- `NONE` gates both channels to zero; haze enables haze only; turbulence enables turbulence only; combined enables both. No forecast field, including wind speed, precipitation, or `SkyFrame`, infers either effect.
- Apply haze as bounded distant-ridge treatment: blend only the distant ridge toward the horizon color by at most `0.30 * hazeStrength`. Do not wash the full scene. A horizon veil may be added only if the ridge treatment proves too subtle in installed review, must be behind scene content, and must not exceed `0.10 * hazeStrength`.
- Turbulence remains restrained, symmetric, and non-directional; animate no strength from wall-clock time. Reduced-motion/static rendering shows the selected-time sample without animation.
- Preserve forecast facts, rain/snow semantics, DEMO DATA, preview labeling, full-bleed composition, cloud bounds at every selected time, gesture ownership, timeline readability, and the selected timestamp.

## Steps

- [x] Inspect the cycle-036 archived source diff and evidence inventory; confirm the exact preview launch route and establish an installed baseline without changing application behavior.
- [x] Add a pure selected-hour profile mapper with the six specified samples, linear fractional interpolation, endpoint clamping, and explicit mode gating; pass its renderer-independent values into the scene renderer.
- [x] Make haze strength progressively soften only the distant ridge within the specified blend cap. Modulate symmetric turbulence from its independent channel. Keep every cloud fully inside the viewport across the fixture horizon with deterministic, continuous selected-time movement. Preserve a static/reduced-motion path and the persistent `ILLUSTRATIVE PREVIEW` label.
- [x] Add focused presentation tests for all sample points, midpoint interpolation, before/after clamping, each preview mode, default-off behavior despite existing wind speed, and forecast precipitation/amount preservation. Run the focused test plus `python scripts/dev.py contract` and `python scripts/dev.py build`.
- [x] Install on Pixel 3 XL in portrait at native display size, default font scale, and LTR. Capture absent preview; haze at low, midpoint, high, and fractional selected hours; turbulence; combined; and reduced-motion. Record actual settings, launch/gesture commands, observations, and screenshot paths. Confirm haze increases perceptibly through ridge softening, effects track selected-hour scrubbing without jumps, clouds remain fully inside the viewport at the horizon endpoints and during scrubbing, no optional veil is needed, and facts/timeline stay readable.
- [x] Save evidence under `.codex/test-artifacts/037-atm5-selected-time-profile/README.md`; inspect `git diff --check` and final diff; close only if all ATM.5 exit conditions are met.

## Acceptance/evidence

The exit is met only when the exact profile and gating pass focused tests, absent-input states remain effect-free, the installed Pixel 3 XL review demonstrates continuous selected-time transitions and an increasingly perceptible bounded ridge haze, symmetric turbulence remains non-directional, clouds remain fully inside the viewport throughout the fixture horizon, static/reduced-motion preserves the selected sample, and forecast facts/timeline remain readable. Evidence targets: `.codex/test-artifacts/037-atm5-selected-time-profile/README.md`, `.codex/history/037-atm5-selected-time-profile.md`, and archived plan `.codex/history/plans/037-atm5-selected-time-profile.md`. The already-approved ATM.5 concept remains the visual reference. No application-wide RTL, TalkBack, large-font, or unrelated layout matrix is in scope.

Authorized verification: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the focused ATM.5 presentation test, `python scripts/dev.py build`, installed `python scripts/dev.py run` / `python scripts/dev.py screenshot` when available, and `git diff --check`. Do not run unrelated tests.

## Risks / assumptions

Cycle 036 screenshots lacked capture metadata and were not used as acceptance evidence. The Pixel 3 XL initially had a smaller display override; final acceptance captures were made at its native size and the original override was restored afterward.

## Out of scope

No redesign of the timeline or gestures, provider/data changes, precipitation redesign, storm/night refinements, environment packs, effects lifecycle/performance policy, production integration, or cross-app accessibility/layout matrix.
