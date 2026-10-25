# Cycle plan — 032-atm3-corrective-acceptance

State: CLOSED
Roadmap: ATM.3
Date: 2026-10-25

## Objective

Correct the recorded selected-time, large-font, and RTL acceptance failures;
then verify that deterministic cloud phase changes are perceptible but
restrained on the installed Pixel 3 XL. Complete ATM.3's existing cloud-renderer
acceptance without expanding into precipitation, terrain, or other atmosphere
features.

This is one corrective cycle for ATM.3. The selected-time fix comes first
because it is upstream of the summary, timeline semantics, and cloud phase.
Font and RTL corrections address required acceptance states for the same
screen. Cloud tuning and the installed review close the same slice's remaining
visual criteria.

## Production boundary

This is the standalone Oxygen Time Lab app only. Changes may be made in the
forecast presentation and Compose UI needed to preserve and disclose the
selected moment, lay out the forecast and timeline at large font sizes and in
RTL, and tune the existing ATM.3 cloud renderer. Expected owners are
`ui/TimeLabScreen.kt`, `ui/ElasticForecastTimeline.kt`,
`presentation/ForecastProjection.kt`, `ui/AtmosphericScene.kt`, and narrowly
scoped helpers in those packages if inspection requires them. Do not modify
fixtures or provider/data contracts.

No production oxygenWX checkout, provider, network, location, alert, cache, or
navigation integration is in scope.

## Findings this cycle must resolve

Use these recorded findings as the baseline:

- Cycle 031 found that `leavePrecision(true)` rounds `selectedHour` and that
  release hides the exact selected time. The visible label and semantics also
  format a rounded hour. See `.codex/history/031-atm3-blocker-confirmation.md`
  and `.codex/test-artifacts/031-atm3-blocker-confirmation/README.md`.
- Cycle 030 records forecast/tape clipping in the 1.3-font precision state.
  See `.codex/history/030-atm3-residual-evidence.md` and
  `.codex/test-artifacts/030-atm3-residual-evidence/README.md`.
- Cycles 029–031 verified that app chronology remains LTR under an RTL
  configuration. See `.codex/history/029-atm3-evidence-retry.md` and cycle
  031's evidence matrix.
- Cycles 030–031 could not visually confirm distinct cloud-phase movement
  across adjacent positive-cloud times. Cycle 031 records the current far/mid
  rates and the limits of its comparison.

## Functional invariants

- `selectedHour` remains the single authoritative forecast moment. Precision
  release, scale animation, layout direction, font size, and rendering do not
  round, replace, or advance it. Only an explicit selection gesture, day
  navigation, or return-to-now action may change it.
- A fractional selected moment survives release and subsequent re-entry within
  fixture bounds. The existing summary label, selected timeline cue, and
  timeline accessibility semantics disclose the same selected moment. Keep
  whole-hour labels concise; when the selected value is fractional, disclose
  its nearest minute so the selected time is not misrepresented as a different
  hour. This is a formatting change to existing UI, not a new persistent row.
- Forecast values remain projections of the current fixture. Interpolated
  values remain illustrative and are not described as source-measured
  minute-level forecasts. Missing cloud cover remains missing and does not
  produce invented clouds.
- Cloud phase is a deterministic function of exact `selectedHour` and the
  existing bounded far/mid rates. Geometry and phase do not reseed or pop at
  sample boundaries. No autonomous clock is added; reduced motion and
  `EffectsLevel.NONE` keep a stationary scene while selection is stationary,
  and still respond to explicit time selection.
- RTL changes visual reading/layout direction and the corresponding time-axis
  mapping coherently. Timeline labels, chronology, touch selection, and
  selected-time semantics agree; gesture ownership and the established tap,
  drag, hold, release, day-swipe, and double-tap behaviors remain predictable.
- Essential forecast values, DEMO DATA provenance, accessibility actions,
  contrast, and the scene/timeline hierarchy remain readable. Decorative
  artwork does not cover text or take gesture/focus ownership.

## Ordered steps

1. Inspect the current summary projection, precision-release callbacks,
   timeline Canvas/semantics, screen layout, and ATM.3 cloud phase code. Compare
   with the cycle 029–031 captures and verify the Pixel 3 XL is available to
   ADB before changing source.
2. Preserve fractional `selectedHour` on every precision-release path. Remove
   any redundant rounding path that changes the authoritative selection.
   Format the current selected moment consistently in the existing summary,
   selected timeline label, and semantics, including a fractional-minute
   sample. Keep interpolation and fixture bounds unchanged.
3. Correct the demonstrated 1.3-font clipping in hourly precision and review
   the daily overview at the same font scale. Keep both landscape essential
   high/low values and timeline labels visible; use compact layout behavior
   where the installed viewport requires it.
4. Correct RTL layout and Canvas chronology using the active Compose
   `LayoutDirection`. Keep drawn chronology, label alignment, touch-to-time
   mapping, and gesture direction consistent; do not silently disable RTL or
   force the device back to LTR to obtain passing captures.
5. On the installed reference device, compare adjacent positive-cloud fixture
   times at matched settings after selected-time preservation is fixed. If
   phase movement remains indistinct, tune only the existing bounded far/mid
   drift rates or drawing placement enough to make selected-time-driven change
   discernible while remaining restrained. If no rate meets both conditions,
   record that result and leave ATM.3 incomplete rather than widening the
   renderer scope.
6. Complete the installed review below. Record exact selected fixture time,
   device/build, viewport/density, font scale, orientation, locale/layout
   direction, effects/reduced-motion settings, and observed result for every
   capture and interaction.
7. Run the authorized checks, inspect the complete diff and `git diff --check`,
   preserve actual evidence under the cycle directory, and close the cycle.
   Mark ATM.3 DONE only if every acceptance item passes; otherwise record the
   specific unmet condition and leave ATM.3 BLOCKED.

## Installed acceptance and evidence

Use an installed Pixel 3 XL build. Preserve actual screenshots and observation
notes under `.codex/test-artifacts/032-atm3-corrective-acceptance/`.

The required review covers:

- Clear and overcast fixture samples in daily and hourly presentation. Include
  adjacent positive-cloud times at matched settings to judge visible phase
  movement, continuity, and wrap behavior.
- Default/compact presentation and system font scale 1.3. Recheck the failing
  hourly precision state and daily overview; inspect portrait and constrained
  landscape where available. Essential forecast facts and tape labels must be
  readable and unclipped.
- LTR and an actually verified RTL configuration. Confirm the screen's text,
  day/hour chronology, selected marker, and touch mapping follow one coherent
  direction contract.
- Reduced motion and `EffectsLevel.NONE`. Confirm no autonomous time/phase
  movement while selection is stationary, and confirm explicit selected-time
  changes still update the deterministic cloud placement.
- Fractional selection (including the 7:30 PM neighborhood), precision
  release/collapse, and stationary re-entry. Confirm the selected moment does
  not jump and its visible and accessible disclosure agrees with the
  authoritative selection.
- Direct timeline tap, immediate drag, stationary hold/scrub, release, main
  view day swipe, and double-tap return. Record timeline responsiveness,
  predictable gesture ownership, and forecast readability as separate
  evidence-backed dispositions.
- TalkBack traversal of the changed time label and timeline action/semantics.
  Verify that the selected moment is understandable after precision release.

ATM.3 exit is met only when clouds respond smoothly and deterministically to
supported cloud cover and selected time; the static path is legible; the above
installed conditions pass; the selected time remains authoritative; and actual
captures and interaction observations support the result.

Target records:

- Plan: `.codex/plans/032-atm3-corrective-acceptance.md`
- Evidence: `.codex/test-artifacts/032-atm3-corrective-acceptance/README.md`
- Closeout: `.codex/history/032-atm3-corrective-acceptance.md`
- Archived plan: `.codex/history/plans/032-atm3-corrective-acceptance.md`

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build` and `python scripts/dev.py lint` as applicable
- `python scripts/dev.py run --serial 8AKY0M32X` and
  `python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture>`
  for installed evidence, plus the manual interaction, RTL, reduced-motion,
  and TalkBack checks above
- Source/diff review and `git diff --check`

Do not add or run tests under the standing repository rule. If a host, device,
or RTL setup prevents installed acceptance, record the exact command/state and
failure; do not claim visual acceptance from a build or preview.

## Risks and assumptions

- The Pixel was usable for cycles 030–031, but availability and supported RTL
  setup must be confirmed at execution. Do not assume either remains available.
- The cycle 031 fractional capture derived 7:30 PM from touch geometry rather
  than an independent UI readout. This cycle's selected-time disclosure must
  make fractional selection directly inspectable.
- Cloud-phase perceptibility is a visual acceptance question, not established
  by source inspection. Rates are tuning inputs; any rate change must remain
  bounded, deterministic, and tied only to selected forecast time.
- RTL direction can affect Canvas drawing and gesture mapping separately.
  Validate both together on an actual RTL configuration.
- One cycle is appropriate because the acceptance findings share the same
  selected-time state and ATM.3 installed acceptance gate. Keep the work within
  the listed owners and stop for replanning if implementation requires a
  broad timeline redesign, a new interaction model, or infrastructure beyond
  the existing reference device.

## Out of scope

- Rain/snow, precipitation intensity, fog/wind, storm/night, terrain, new
  environment packs, or ATM.7 lifecycle/effects policy.
- New data, fixture changes, provider integration, production oxygenWX changes,
  network/location/alerts/cache behavior, or navigation changes.
- A timeline redesign, new persistent controls, new time-navigation gestures,
  or changes to fixture interpolation/forecast semantics.
- Reducing the existing ATM.3 installed matrix or treating screenshots/builds
  alone as evidence of interaction, accessibility, or visual acceptance.
