# SG.12 — What the Center Readout Shows

**Status:** Product owner approved this replacement behavior board on 2026-11-05. It replaces the former `SG.11-center-readout-behavior-board.svg`, whose event-mapping scope no longer matched SG.12 after the owner-directed SG.11a–f split. The replacement board uses the installed Pixel main-screen capture as a labeled current-state reference. The reference documents the current UI and is not itself the approved target composition.

## Board package

- Behavior board: [`SG.12-center-readout-behavior-board.svg`](SG.12-center-readout-behavior-board.svg) and PNG preview.
- Current-screen reference: [`SG.12-pixel-main-screen-reference.png`](SG.12-pixel-main-screen-reference.png), captured 2026-11-05 from Pixel 3 XL (`8AKY0M32X`) with `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/concepts/SG.12-pixel-main-screen-reference.png`.
- Companion UI artwork: [`SG.12-center-readout-ui-artwork-board.svg`](SG.12-center-readout-ui-artwork-board.svg) and PNG preview. Its composition is unchanged from the owner-approved 2026-11-04 SG.11 board; only its label and artifact path now identify SG.12.

## Behavior covered

- The selected `Instant` governs the central time-specific facts and selected WA symbol. Zoom and picker open/dismiss do not retarget it.
- A supported selected-time typed event maps to its matching existing WA symbol and accessible condition name. The visible center condition word is omitted; condition text remains available through accessible semantics and the forecast browser.
- An eligible carried condition shows the prior supplied symbol and its original source time, while disclosing that the selected hour is unavailable. It does not imply a newly supplied event.
- An unsupported/empty condition, edge gap, or missing day uses the unavailable identity and makes no condition claim.
- The location label remains noninteractive beside the existing picker trigger. The timeline retains selected day and time.
- Supported Sun/Moon rise and set, high/low, precipitation chance, wind speed, DEMO DATA, and provenance remain tied to supplied facts. Missing facts remain independently unavailable.
- The visible condition word and duplicate weekday/time line leave the center; the lower magnifier and gear are removed. SG.12 adds no event mapping, new artwork, provider endpoint, unit policy, or navigation behavior.

The product owner approved both SG.12 boards on 2026-11-05. The companion “Central Selected Forecast Readout” UI artwork board remains the visual acceptance target for successful SG.12 completion and defines the regular and compact visual hierarchy.
