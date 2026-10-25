# Cycle plan — 028-atm3-evidence-closure

State: CLOSED INCOMPLETE
Roadmap: ATM.3
Objective: Complete ATM.3 installed acceptance with verified device-state setup and timestamp-backed interaction evidence.

## Why this is a new bounded attempt

Cycles 026 and 027 both stopped short of ATM.3 acceptance. The Pixel is now
unlocked, so the external condition recorded by cycle 026 has changed. Cycle
027 also showed that requested system settings did not prove the actual layout
state, and its injected gestures were not corroborated with selected-time
semantics. This plan adds explicit setup gates and ties every interaction
capture to the visible/accessibility selected timestamp. It does not count
another attempt as acceptance by itself.

## Scope and boundary

This is an installed verification and evidence-closure cycle for the existing
ATM.3 renderer. Do not redesign the scene or timeline.

Files that may change:

- `.codex/test-artifacts/028-atm3-evidence-closure/**` — actual screenshots,
  state dumps, and evidence README.
- `.codex/history/028-atm3-evidence-closure.md` and
  `.codex/history/plans/028-atm3-evidence-closure.md` — cycle closeout.
- `docs/ROADMAP.md` — ATM.3 status/evidence only, based on the actual exit.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` —
  only if installed evidence demonstrates a cloud-only placement, coverage, or
  drift-rate defect; no other application source file is in scope.

No tests are authorized. Do not edit the timeline, fixtures, projection,
gesture ownership, accessibility semantics, layouts, or production app.

## Functional invariants

- `selectedHour` remains the sole cloud phase input; wall clock, local hour,
  zoom scale, and animation clock cannot select or change forecast time.
- Cloud coverage continues to follow typed nullable cloud fraction, with
  missingness handled conservatively. Geometry stays deterministic and wraps
  without a visible jump.
- Clear conditions retain open sky; denser cloud conditions remain visible
  without obscuring forecast facts or timeline labels.
- `EffectsLevel.NONE` and reduced motion suppress autonomous cloud movement,
  while an explicit selected-time change still changes cloud placement.
- Timeline responsiveness, predictable gesture ownership, forecast text,
  demo labeling, and all existing architecture boundaries remain intact.

## Installed setup and evidence gates

Reference device: unlocked Pixel 3 XL (`crosshatch`), Android 12/API 31,
project viewport override 1260×2240 at 560 dpi unless re-queried values differ.
Do not reboot during the cycle. Before any matrix capture, record serial, build,
API, display size/density, font scale, locale, orientation, and three animation
scales.

1. **Prove device controls before doing the matrix.** Keep the device unlocked.
   For RTL, enable/select Android's supported `ar-XB` pseudo locale in system
   Settings (including the developer pseudo-locale option if present), restart
   only the app, and verify that direction actually changes by inspecting the
   rendered app and a layout-direction/configuration dump. A stored property
   value alone does not pass this gate. For landscape, request rotation through
   Settings/Quick Settings or the supported rotation control, then verify both
   the display dimensions and app layout are landscape before capture. The app
   manifest uses `screenOrientation="unspecified"`. Restore portrait and
   en-US after each check. If either state cannot be established, stop the
   matrix, preserve the attempted state/configuration evidence, and close
   incomplete with the exact platform limitation; do not substitute an LTR or
   portrait capture.
2. Capture screenshots only after state verification. For each capture, save
   the selected date/time/scale from the UI accessibility hierarchy beside the
   screenshot and record device/API, viewport/density, font, locale/direction,
   orientation, motion/effects settings, selected timestamp, cloud condition,
   and rates `.42` far / `.88` mid dp per selected hour. Capture the actual
   artifact with `python scripts/dev.py screenshot --serial 8AKY0M32X --output
   <path>`.
3. Review daily and hourly clear and cloudy/overcast fixtures in default
   portrait. Review default and 1.3 font scales, verified landscape, verified
   RTL, and reduced-motion settings with effects disabled. Confirm the scene
   leaves forecast values, date/time, and timeline labels readable.

## Interaction procedure

Perform and record each action on the installed build. Save a UI hierarchy dump
or equivalent selected-time text immediately before and after each screenshot.
Keep the device unlocked and do not use reboot-based setup.

- Tap a named day and verify the selected date and representative time.
- Stationary hold the timeline until hourly labels appear; capture the initial
  selected timestamp while held.
- Scrub at least one hour and verify the selected timestamp changes by the
  expected amount. Capture the same selected timestamp while held and directly
  after release; selected time must be identical even if the display returns to
  daily overview.
- Select Mon Oct 26 11 PM, capture its hierarchy and screenshot, then advance
  exactly one hourly tick and verify/capture Tue Oct 27 12 AM. Compare cloud
  silhouettes/positions and opacity across this sample boundary. No cloud
  re-seed, visible shape pop, or wrap jump is acceptable. Include an additional
  adjacent clear sample pair to confirm sparse coverage remains open.
- Swipe the main forecast region one day and verify the date advances once
  while preserving hour-of-day. Double-tap the central forecast and verify the
  fixture's current hour is selected. Record responsiveness, predictability,
  and forecast readability from direct observation; injected events without
  state verification do not pass.
- With system animation scales at 0 and app effects disabled, verify no
  autonomous cloud drift in a timed observation and verify that explicit
  timeline selection still updates placement.

Use screenshots at the same viewport and stable app state for adjacent-sample
comparison. If drift rates or cloud-only geometry are adjusted, record why,
rebuild, reinstall, and recapture all affected clear/cloudy and boundary states.

## Ordered steps

1. Re-read cycles 026/027, verify current source rates and selectedHour flow,
   query device/build/settings, and run `python scripts/dev.py workflow` and
   `python scripts/dev.py contract`.
2. Run the RTL and landscape setup gates. Resolve the device setup before
   continuing to matrix capture; do not run a broad matrix in a state that
   failed its gate.
3. Run `python scripts/dev.py build`, `lint`, and `run --serial 8AKY0M32X`.
   Do not run tests. Capture and review the state matrix and interactions above.
4. Tune only cloud geometry/rates in the allowed file if actual installed
   evidence requires it; rebuild and recapture affected states.
5. Restore en-US, portrait, font scale 1.0, and animation scales 1. Verify
   restoration. Write the capture/interaction table and limitations in the
   evidence README. Inspect the final diff and run `git diff --check`.
6. Close cycle 028 with exact verification results. Set ATM.3 DONE only if all
   required states and interactions are evidenced. Otherwise close incomplete
   and keep ATM.3 BLOCKED with the specific reproducible blocker and evidence.

## Acceptance and evidence

Target artifacts: `.codex/test-artifacts/028-atm3-evidence-closure/` and its
`README.md`. The ATM.3 exit is met only when actual installed evidence shows:

- deterministic far/mid layers at the recorded final rates; clear and
  overcast daily/hourly examples, no fact/timeline obstruction;
- smooth adjacent-hour transition at Mon Oct 26 11 PM → Tue Oct 27 12 AM and
  no phase-wrap jump, with one adjacent clear pair also reviewed;
- verified default/1.3 font, portrait/landscape, LTR/RTL, and reduced-motion /
  effects-disabled states;
- selected timestamp continuity through hold, scrub, and release, plus
  verified day tap, main-view day swipe, and double-tap return-to-now;
- direct observations of timeline responsiveness, gesture predictability, and
  readable forecast values/labels;
- metadata for each evidence row: fixture time, device/API, viewport/density,
  font scale, locale/direction, orientation, motion/effects setting, visible
  scale, and final rates.

The required cycle record is `.codex/history/028-atm3-evidence-closure.md`;
the archived plan is `.codex/history/plans/028-atm3-evidence-closure.md`.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build`
- `python scripts/dev.py lint`
- `python scripts/dev.py run --serial 8AKY0M32X`
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture>`
- Read-only ADB configuration queries, Settings UI, UI hierarchy dumps, direct
  installed interactions, and screenshot review.
- `git diff --check` and final diff review.

Tests are not authorized by this plan or repository instructions.

## Risks and assumptions

- The Pixel is reported unlocked, but its current connection/settings must be
  re-queried when this cycle is activated.
- Android 12 may require enabling pseudo locales in Developer Options before
  `ar-XB` can be selected. Verify actual layout direction rather than trusting
  a setting value.
- Device rotation controls may be constrained by system UI or display override;
  verify the app and viewport, not only the requested rotation value.
- Injected touch events can be sent to the wrong coordinate region. Record the
  UI hierarchy and selected timestamp after each event so this cannot be
  mistaken for a successful interaction.
- The exact fixture sample labels and visible hourly tick positions must be
  confirmed against the installed UI before adjacent-sample comparison.
- No source tuning is assumed; current rates remain `.42` and `.88` dp/hour
  unless installed evidence demonstrates a cloud-only problem.

## Out of scope

No new feature work, timeline or gesture redesign, forecast-data changes,
tests, production Oxygen integration, APIs/network/location, rain/snow/terrain
changes, or accessibility-semantic redesign. Do not mark ATM.3 DONE from build
success, a setting value, or screenshots without matching verified state and
interaction evidence.

## Execution result

The cycle stopped at the first installed setup gate. The Pixel was visible in
ADB with the planned Android 12/API 31 viewport and default settings. After a
tap on the Developer Options master switch, the device disappeared from ADB
and remained visible only as an MTP USB device. The setup screenshot appears
to show the switch enabled, so the tap likely disabled it; the cause could not
be confirmed after the disconnect. The device's current USB debugging and
developer settings could not be queried or restored. RTL and landscape were
therefore not established; no app matrix or interaction captures were taken.
The setup artifacts and exact verification results are recorded in
`.codex/test-artifacts/028-atm3-evidence-closure/README.md`.

Cycle 028 closes incomplete. Build and lint passed using Java 27 and the
installed Android SDK. Contract passed. Device install, app captures, all
interaction checks, and restoration verification remain unrun. No application
source changes were made for this cycle. ATM.3 remains BLOCKED until the Pixel
is physically restored to authorized ADB access and a new bounded cycle is
planned.
