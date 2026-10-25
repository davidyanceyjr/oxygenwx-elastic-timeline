# Cycle plan — 030-atm3-residual-evidence

State: ACTIVE
Roadmap: ATM.3
Objective: Capture the remaining device-dependent ATM.3 evidence after ADB reconnection; verify the largest font preset and reduced-motion cloud phase under positive cloud cover, restore the device, and preserve known interaction/RTL gaps as explicit blockers. Make no application source changes.

## Production boundary

This is an installed-evidence cycle only. No application source changes,
timeline redesign, accessibility change, fixture change, or animation behavior
change is in scope.

Files that may change:

- `.codex/test-artifacts/030-atm3-residual-evidence/**` — actual screenshots,
  UI hierarchies, state records, and evidence README.
- `.codex/history/030-atm3-residual-evidence.md` and
  `.codex/history/plans/030-atm3-residual-evidence.md` — cycle closeout.
- `docs/ROADMAP.md` — ATM.3 evidence/status only, based on results.

## Functional invariants

- `selectedHour` remains the selected forecast time; no wall clock, zoom, or
  animation scale may change it.
- Cloud rendering remains deterministic and the clear sky remains open.
- Forecast facts, DEMO DATA labeling, and timeline labels remain readable.
- Android system settings are changed only through Settings UI; do not toggle
  the Developer Options master switch or USB debugging.
- Do not claim ATM.3 accepted: known RTL and release-continuity failures remain
  outside this evidence-only scope.

## Device gate and steps

Reference device: Pixel 3 XL (`crosshatch`), Android 12/API 31, expected
1260×2240 display override at 560 dpi. At activation, verify `adb devices -l`
lists `8AKY0M32X` as `device` and record `lsusb`. If ADB disappears during
execution, stop the device matrix and close incomplete; do not toggle USB
debugging or Developer Options to recover it.

1. Record baseline fingerprint/build, display size/density, font scale, locale,
   actual layout direction/configuration, rotation, animation scales, resumed
   app, and ADB authorization.
2. Through Android Settings UI, select the largest available font preset.
   Read `settings get system font_scale`, capture the app and hierarchy, inspect
   all forecast facts and tape labels, then restore font scale 1.0 through UI.
3. Reinspect the Developer Options master switch in Settings UI. Continue only
   if visibly enabled; do not toggle it. Set all three animation scales to 0
   through the UI. Capture Monday Oct 26 7 PM (partly cloudy) twice across a
   five-second idle interval, then advance one hourly tick to 8 PM (cloudy) and
   capture again. Compare the static idle pair and selected-time cloud change.
   The app's default `EffectsLevel.NONE` is the effects-disabled path.
4. Restore animation scales to 1.0 through Settings UI. Restore `en-US`,
   portrait/free rotation, and font scale 1.0. Verify every value and ADB
   authorization, then capture the app baseline.
5. Write the evidence README with results, exact commands, per-state metadata,
   limitations, and the disposition of known RTL/release gaps. Run
   `git diff --check` and review the final diff. Do not run tests.

## Acceptance/evidence

Evidence target: `.codex/test-artifacts/030-atm3-residual-evidence/README.md`.
Each app screenshot must have its UI hierarchy or equivalent selected-time
record and metadata (device/API, viewport/density, font scale, locale/direction,
orientation, motion/effects settings, timestamp, condition, and cloud rates
`.42` far / `.88` mid dp per selected hour).

This cycle's exit is to record the largest font preset and installed readability,
capture a positive-cloud reduced-motion idle pair plus an explicit adjacent-hour
update, restore and verify device settings, and preserve exact unmet acceptance
gaps. This does not close ATM.3: verified app LTR behavior under RTL and
selected-hour continuity after release require a separately scoped decision.

Authorized checks: `python scripts/dev.py workflow`,
`python scripts/dev.py contract`, read-only ADB queries, Settings UI changes
listed above, installed interactions/screenshots, and `git diff --check`.
Tests/build/lint are not authorized or needed for this evidence-only cycle.

## Risks / assumptions

- Record the largest preset's actual system value; do not synthesize a value by
  writing Android settings. The installed largest preset was 1.3 in this run.
- Main-view swipes and tape holds may alter selected scale/time; capture and
  inspect the visible timestamp before each comparison.
- ADB has disconnected repeatedly when Android Settings was open; the stop
  gate applies immediately if it happens again.

## Out of scope

No app source edits, tests, gesture changes, RTL redesign, selected-hour
accessibility redesign, forecast changes, or production integration. The
known RTL and release-continuity gaps remain blockers for ATM.3 and require
their own bounded implementation plan after this evidence cycle.

## Execution result

Captured the largest preset at font scale 1.3; forecast values and the timeline
clip at that size. Captured a five-second 7 PM partly-cloudy idle state at zero
animation scales and a held update to 8 PM cloudy. Facts and selected time
update; cloud silhouettes are too similar to confirm distinct phase movement.
Restored all three animation scales and font scale to 1.0, en-US, portrait/free
rotation; the Pixel remained authorized. RTL remains LTR in the app and release
still hides the selected hour. Cycle evidence is recorded in
`.codex/test-artifacts/030-atm3-residual-evidence/README.md`; ATM.3 remains
unaccepted and blocked.
