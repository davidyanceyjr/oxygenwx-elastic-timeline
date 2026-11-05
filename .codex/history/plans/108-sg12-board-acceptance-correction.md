# Cycle plan — 108-sg12-board-acceptance-correction

State: ACTIVE
Cycle ID: 108-sg12-board-acceptance-correction
Roadmap: SG.12
Objective: Complete the approved SG.12 lower-indicator composition and capture a clean board comparison

## Production boundary

This is a bounded correction to completed SG.12 after owner review found two acceptance gaps. The owner-approved UI artwork board `.codex/concepts/SG.12-center-readout-ui-artwork-board.svg` remains the mandatory visual acceptance target. The owner-approved behavior board `.codex/concepts/SG.12-center-readout-behavior-board.svg` and supporting `.md` govern supplied facts, unavailable states, and provenance. Both boards were approved on 2026-11-05 and the owner reiterated that the Central Selected Forecast Readout board is the visual acceptance target. No new board is needed because the approved lower-indicator row and clean main composition already define this work.

The top `DEMO DATA / REPLAY` bar in the latest capture is emitted by the debug-only `MainScreenOverlay` in `app/src/debug/java/com/oxygen/weather/timeline/data/debug/CatalogPickerDelegate.kt`; it appears for SIMULATED and REPLAY origins in debuggable builds. It is not part of the product readout and is excluded from the approved artwork. Do not change debug overlay behavior in this correction. Use a non-debug installed build for the clean product screenshot if the release configuration supports it; otherwise record the debug overlay as capture limitation and compare the product composition below it.

Change only `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` for the lower indicator row. Update `.codex/test-artifacts/101-sg11-center-readout/README.md` with the corrected comparison and add installed captures under `.codex/test-artifacts/108-sg12-board-acceptance-correction/`. Record cycle 101's acceptance gap and this correction in closeout/history and roadmap. Preserve supplied values, existing wind-direction meaning, accessibility labels, gesture behavior, provenance data, and all exclusions in SG.12.

## Functional invariants

- The approved artwork's precipitation and wind values have compact inline cues (`☂ 72%` and direction arrow plus `12 mph`) with one blank indicator-sized slot between them.
- Precipitation chance and wind speed remain independent, fact-derived values; unavailable values remain clearly unavailable.
- Wind direction remains represented by the existing supplied bearing, with its accessible spoken direction preserved.
- The top debug-only DEMO/REPLAY overlay is not product UI; no production bar is added or removed by this change.
- Center readout, selected instant, scene, timeline, picker, symbol identity, condition semantics, Sun/Moon values, high/low, provenance and DEMO DATA semantics remain unchanged.

## Steps

- [x] Verified the debug-only overlay source and Gradle variants; recorded the current screen and Pixel display settings.
- [x] Replaced stacked lower pictograms and values with compact inline cues; retained the lower blank slot, bearing-derived arrow, test tags, and merged accessibility descriptions.
- [x] Built debug and release; locally signed the release APK for device installation; captured regular and compact installed release states without the debug overlay.
- [x] Inspected both captures against the board and reviewed the changed row and semantics. Existing `Unknown` fallback code is unchanged; unavailable values were not captured in this release state. Prior cycle 101 evidence covers the existing picker and selection interactions; no new interaction changes are claimed. `git diff --check` passed. No tests were added or run.
- [x] Recorded cycle 101’s missed lower-row requirement, exact build/install steps, captures, and limitations in the evidence README, prior evidence README, roadmap, and cycle closeout.

## Acceptance/evidence

At Pixel 3 XL portrait and a documented compact portrait viewport, lower precipitation chance and wind speed appear as compact inline cues beside their prominent numbers, matching the approved board's regular and compact examples. The blank middle slot remains. Numeric values, wind direction indication, and accessible descriptions match the selected supplied facts. An unavailable value remains honest and does not collide with the other cue. Capture and inspect the installed main screen; the debug-only top overlay must not be mistaken for product UI. Record exact commands, device settings, screenshots, results, and limits in `.codex/test-artifacts/108-sg12-board-acceptance-correction/README.md`.

Use `python scripts/dev.py workflow`, `contract`, `build`, and `run` as applicable. No tests, lint, RTL, TalkBack, or broad system matrix; those are not required for this visual correction and no tests were authorized.

## Risks / assumptions

The release build may use a different application ID or signing configuration, so inspect the existing Gradle configuration before installing it. If a clean release capture is not practical, do not alter debug source; record the exact debug-only overlay limitation and assess the affected readout composition separately.

## Implementation difficulty

**2/10 — small.** One Compose row and its existing visual cues change; verification focuses on alignment and semantics at two installed viewport widths.

## Out of scope

No debug overlay changes, header/provenance behavior changes, center readout changes, new symbol artwork, provider or data mapping, forecast browser changes, broad accessibility/system checks, or unrelated cycle-101 implementation changes.


## Cycle 108 result (2026-11-05)

The board-approved precipitation and wind cues are now inline and direction-bearing, and installed Pixel screenshots show the clean release view at 411×846 dp and 360×800 dp. The 101 lower-row acceptance gap is corrected. The release capture used supplied values 9% and 7 mph. No unavailable-value screen was captured, and no new picker/selection interaction check was needed for the presentation-only row change; see the exact limits in the evidence README.
