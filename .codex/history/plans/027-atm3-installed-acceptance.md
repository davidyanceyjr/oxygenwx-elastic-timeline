# Cycle plan — 027-atm3-installed-acceptance

State: ACTIVE
Roadmap: ATM.3
Objective: Complete ATM.3 installed acceptance on the unlocked Pixel 3 XL for the already implemented deterministic cloud renderer; tune only cloud-only visual rates or placement if installed evidence requires it.

## Scope and boundary

This is a second bounded attempt at ATM.3 after cycle 026 closed BLOCKED. Keep
the existing deterministic far/mid renderer and exact `selectedHour` flow.
Review and, only if installed evidence requires it, adjust cloud group geometry,
coverage/opacity, palette tint, or the two bounded drift rates in
`AtmosphericScene.kt`. No timeline, gesture, forecast, model, fixture, rain,
terrain, layout, accessibility semantics, provider, network, or production
oxygenWX changes.

Cycle records and actual captures belong in:

- `.codex/plans/027-atm3-installed-acceptance.md`
- `.codex/test-artifacts/027-atm3-installed-acceptance/`
- `.codex/history/027-atm3-installed-acceptance.md`
- `.codex/history/plans/027-atm3-installed-acceptance.md`

Update the ATM.3 roadmap evidence and status only when its exit is met. If a
required state remains unavailable, close this attempt incomplete and record
the exact blocker without marking ATM.3 DONE.

## Functional invariants

- Exact `selectedHour` is the only cloud phase input. It cannot select or mutate
  a forecast, and remains independent from `localHour`, scale, wall-clock time,
  and animation clocks.
- Stable geometry and deterministic linear rates remain bounded and wrap
  without a visible position jump. Coverage changes continuously from typed,
  nullable cloud fraction; missingness stays conservative.
- `EffectsLevel.NONE` and reduced motion have no autonomous cloud animation but
  still update placement after explicit time selection.
- Clouds remain decorative, passive, and outside the forecast text and
  timeline quiet zones. Preserve all existing facts, provenance, gestures,
  accessibility behavior, and fixture bounds.

## Installed visual objective and conditions

Use the Pixel 3 XL (`crosshatch`) at the project's existing 1260×2240 / 560 dpi
override unless the device reports a changed configuration. Review clear and
overcast daily/hourly states, compact/default and 1.3 font scale, portrait and
constrained landscape, RTL using Android's supported pseudo-RTL locale or
forced layout setting, and reduced motion with `EffectsLevel.NONE`.

Compare adjacent hourly samples across representative changes, including the
Mon Oct 26 11 PM → Tue Oct 27 12 AM cloudy transition. Record whether cloud
opacity and placement change continuously, whether any sample-boundary pop or
wrap jump occurs, and whether the chosen far/mid rates remain restrained. If
necessary, tune rates in this cloud-only scope and recapture the final result.
Each evidence row records selected fixture time, device/API, viewport/density,
font scale, locale/direction, orientation, motion/effects setting, visible
scale, and final rates.

Directly exercise tape tap, stationary hold, hourly scrub, and release at the
same selected time; exercise main-view day swipe and double-tap return. Record
timeline responsiveness, gesture predictability, selected-time continuity,
and forecast readability from direct observation. Captures alone do not
establish interaction acceptance.

## Ordered steps

1. Verify the Pixel is unlocked and query Android version, display, density,
   font, locale, orientation, and animation settings. Review cycle 026 evidence
   and the installed final renderer; reuse valid prior captures as context.
2. Run workflow and contract checks. Build/install/lint the debug app. Do not
   add or run tests under the standing repository rule.
3. Capture and review the clear/overcast daily/hourly and layout/settings
   matrix. Use Android Settings UI or supported ADB interfaces for temporary
   locale and motion changes; do not reboot the device to change RTL.
4. Exercise adjacent-sample scrubbing and direct interactions. Tune only cloud
   rates/placement if the installed review shows a problem. Capture actual
   before/after and final screenshots and record every state and observation.
5. Restore device settings, verify restoration, inspect the final diff and
   `git diff --check`, and update evidence/history/roadmap. Mark ATM.3 DONE only
   if every required acceptance item is evidenced; otherwise record blocker
   and keep it incomplete.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build`
- `python scripts/dev.py lint`
- `python scripts/dev.py run --serial 8AKY0M32X`
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture>`
- Manual/ADB installed state and interaction review, `git diff --check`, and
  final source/diff review.
- Tests are not authorized by this plan or `AGENTS.md`.

## Acceptance and evidence

ATM.3 may move to DONE only with actual installed evidence for:

- Distinct stable far/mid groups driven by supported typed inputs and exact
  fractional selected time, with final rates recorded.
- Smooth adjacent-sample and phase-wrap behavior without shape popping or
  visible jumps; clear samples leave open sky and overcast remains readable.
- Clear/overcast in daily/hourly scales; default/large font; portrait/landscape;
  RTL; reduced motion and `EffectsLevel.NONE`.
- Selected-time continuity through hold, scrub, and release; main-view swipe
  and double-tap; qualitative dispositions for responsiveness, gesture
  predictability, and readability.
- Captures and observation notes under
  `.codex/test-artifacts/027-atm3-installed-acceptance/README.md`.

## Out of scope

No rain/snow/terrain/fog/wind redesign, performance instrumentation, new test
authoring or execution, accessibility-semantic redesign, new system behavior,
or any production oxygenWX integration.
