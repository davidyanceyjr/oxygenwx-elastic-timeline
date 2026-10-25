# Cycle plan — 029-atm3-evidence-retry

State: CLOSED INCOMPLETE
Roadmap: ATM.3
Objective: Retry ATM.3 installed acceptance after the owner restores Pixel 3 XL ADB access. Verify every device setup state before capture and close incomplete if a required state or interaction cannot be evidenced.

## Why this is a new bounded attempt

Cycle 028 stopped when the Pixel disappeared from ADB during Developer Options
setup. The owner reports Developer Options had been disabled. This retry treats
the device as unverified until its serial is listed and authorized by ADB. Do
not change the Developer Options master switch. If USB debugging, authorization,
or another required device state is unavailable, record the exact result and
stop before the affected matrix; do not infer acceptance from prior captures.

## Scope and boundary

Installed verification and evidence closure for the existing ATM.3 cloud
renderer. No timeline or scene redesign.

Files that may change:

- `.codex/test-artifacts/029-atm3-evidence-retry/**` — actual screenshots,
  state dumps, logs, and evidence README.
- `.codex/history/029-atm3-evidence-retry.md` and
  `.codex/history/plans/029-atm3-evidence-retry.md` — cycle closeout.
- `docs/ROADMAP.md` — ATM.3 status/evidence only, based on the actual exit.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` —
  only if installed evidence proves a cloud-only placement, coverage, or drift
  defect. No other application source file is in scope.

No tests are authorized. Do not edit the timeline, fixtures, projection,
gesture ownership, accessibility semantics, layouts, or production app.

## Functional invariants

- `selectedHour` remains the sole cloud phase input; wall clock, local hour,
  zoom scale, and animation clock cannot select or change forecast time.
- Cloud coverage follows typed nullable cloud fraction, with missingness handled
  conservatively. Geometry stays deterministic and wraps without a visible jump.
- Clear conditions retain open sky; denser cloud conditions remain visible
  without obscuring forecast facts or timeline labels.
- `EffectsLevel.NONE` and reduced motion suppress autonomous cloud movement,
  while an explicit selected-time change still changes cloud placement.
- Timeline responsiveness, predictable gesture ownership, forecast text, demo
  labeling, and architecture boundaries remain intact.

## Device and setup gates

Reference device: Pixel 3 XL (`crosshatch`), Android 12/API 31, expected display
override 1260×2240 at 560 dpi. Do not reboot. Do not tap the Developer Options
master switch or toggle USB debugging during this cycle.

1. Before activation or device changes, confirm `adb devices -l` lists serial
   `8AKY0M32X` as `device` (authorized, not `unauthorized` or `offline`). Record
   `lsusb` as supporting host evidence only. If absent, leave this plan PLANNED
   until the device reconnects; if it becomes absent during ACTIVE execution,
   stop and close incomplete.
2. Record fingerprint/build, API, physical and override display size/density,
   font scale, locale, direction/configuration, orientation, animation scales,
   and resumed app. Do not infer layout direction from locale/property alone.
3. For RTL, use Android Settings to select the supported `ar-XB` pseudo locale
   only if available. If Developer Options must be used to expose pseudo
   locales, first inspect its master switch in the Settings UI and proceed only
   when it is visibly enabled; never toggle that master switch. Enable/select
   the pseudo locale option and verify ADB remains authorized after each
   Settings change. Verify the running app's actual layout direction from
   configuration/UI hierarchy and rendered layout. If the master switch is
   off or the locale remains unavailable, stop the RTL matrix and record the
   exact setup blocker; do not substitute LTR.
4. For landscape, request rotation through Settings/Quick Settings or a
   supported rotation control, then verify display dimensions and app layout
   are landscape. Restore portrait and `en-US` after each check.
5. Capture only verified states. Beside every app screenshot, retain UI
   hierarchy or equivalent selected-time text and metadata: device/API,
   viewport/density, font scale, locale/direction, orientation,
   motion/effects settings, selected timestamp, cloud condition, and final
   rates `.42` far / `.88` mid dp per selected hour.

## Installed acceptance matrix and interactions

Use `python scripts/dev.py screenshot --serial 8AKY0M32X --output <path>` for
actual screenshots. Before/after each interaction capture, save the UI
hierarchy or equivalent selected-time text.

- Review clear and overcast fixtures in daily and hourly states, portrait,
  font scales 1.0 and 1.3, verified landscape and verified RTL, plus reduced
  motion with app effects disabled. Confirm forecast facts and tape labels stay
  readable.
- Tap a named day and verify its selected date and representative time.
- Hold the tape to enter hourly scale, scrub at least one hour, and verify the
  selected timestamp changes as expected. Capture while held and after release;
  timestamps must match.
- Select Mon Oct 26 11 PM, advance exactly one hourly tick, and verify/capture
  Tue Oct 27 12 AM. Compare cloud silhouette, position, opacity, and phase-wrap
  continuity at the same viewport/state. Include an adjacent clear sample pair.
- Swipe the main forecast region one day and verify exactly one date advance
  with hour-of-day preserved. Double-tap the central forecast and verify return
  to the fixture's current hour.
- With animation scales at 0 and effects disabled, verify no autonomous cloud
  drift during a timed observation and verify explicit time selection updates
  placement.
- Directly record timeline responsiveness, gesture predictability, and
  forecast-value/label readability. Injected events without selected-time
  verification do not pass.

## Ordered steps

1. Read cycles 026–028 and inspect current cloud rates/selectedHour flow. Confirm
   device authorization and baseline state before activation. Run workflow and
   contract checks.
2. Activate only after the Pixel serial is present as an authorized ADB device.
   Verify the RTL and landscape setup gates before broad matrix capture. If a
   gate fails, stop affected acceptance and preserve exact evidence.
3. Run build, lint, and `run --serial 8AKY0M32X`; capture/review the matrix and
   interactions above. Do not run tests.
4. Tune only cloud geometry/rates in the allowed file if actual installed
   evidence demonstrates a cloud-only defect; rebuild and recapture affected
   states.
5. Restore `en-US`, portrait, font scale 1.0, and animation scales 1. Verify
   restoration and USB debugging/ADB connectivity. Write the evidence table and
   limitations. Inspect final diff and run `git diff --check`.
6. Close with exact verification results. Mark ATM.3 DONE only if every
   required state and interaction is evidenced; otherwise close incomplete and
   retain BLOCKED with the reproducible blocker.

## Acceptance and evidence

Target: `.codex/test-artifacts/029-atm3-evidence-retry/README.md`. ATM.3 closes
only when installed evidence proves deterministic far/mid layers at the final
rates; clear/overcast daily/hourly readability; continuous adjacent-hour
transition across midnight plus a clear adjacent pair; verified default/large
font, portrait/landscape, LTR/RTL, and reduced-motion/effects-disabled states;
selected-time continuity through hold/scrub/release; verified day tap, main-view
swipe, and double-tap; direct observations of responsiveness, gesture
predictability, and readability; and complete per-capture metadata.

Required records: `.codex/history/029-atm3-evidence-retry.md` and
`.codex/history/plans/029-atm3-evidence-retry.md`.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build`
- `python scripts/dev.py lint`
- `python scripts/dev.py run --serial 8AKY0M32X`
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture>`
- Read-only ADB queries, Settings UI changes listed above, UI hierarchy dumps,
  direct installed interactions, and screenshot review.
- `git diff --check` and final diff review.

Tests are not authorized by this plan or repository instructions.

## Execution result

Closed incomplete on 2026-10-25 under the stop-on-disconnect gate. The Pixel
was restored to `en-US`, portrait, font scale 1.0, and Android animation scales
1.0 before the optional cloudy-state reduced-motion follow-up. During that
follow-up, `adb devices -l` lost serial `8AKY0M32X`; `lsusb` showed the Pixel in
MTP mode only. The device was not available to finish the additional capture.

Saved captures demonstrate the daily/hourly presentation, one-hour held scrub,
day selection, main-view swipe, double-tap return to now, clear adjacent
samples, and a visually continuous cloudy-to-showers midnight boundary. They
do not satisfy all acceptance criteria: verified Android RTL still renders the
app layout LTR; release returns to day overview and hides the selected hour;
the largest available system font preset is 1.15 rather than the requested
1.3; and the reduced-motion time update used clear skies, so cloud-phase change
under positive cloud cover remains unevidenced. No cloud-only visual defect was
established and no application source was changed in this cycle.

## Out of scope

No feature work, timeline/gesture redesign, forecast data changes, tests,
production integration, network/location, rain/snow/terrain changes, or
accessibility redesign. Do not mark ATM.3 DONE from a build, a setting value,
prior-cycle screenshots, or unverified interactions.
