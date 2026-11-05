# Cycle plan — 109-sg13-skyline-anchored-readout

State: ACTIVE
Cycle ID: 109-sg13-skyline-anchored-readout
Roadmap: SG.13
Objective: Add the selected weather event as a text row between temperature and sunrise, using the location label's font weight; align the Chicago text baseline with the top edge of the third tallest building in the board's Proposed Placement image; and move the lower precipitation/wind row upward exactly 23 dp.

## Readiness and approval gate

The owner approved the current SG.13 combined behavior/UI artwork board package on 2026-11-05. It defines the third-tallest-building landmark in Proposed Placement, the new selected weather-event text row with location-label font weight, a separate exact 23 dp lift of the lower indicators, and the fixed scene/timeline. This bounded decision supersedes SG.12's omission of the visible center condition word. SG.12 is DONE, and SG.13 was the sole NEXT eligible slice. The owner's `oxygen-execute-plan` request activated cycle 109 on 2026-11-05.

## Production boundary

Change only `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` for central readout content/vertical placement and the lower indicator row offset. Use the existing selected-condition projection and its provenance semantics for the new row. Add installed evidence under `.codex/test-artifacts/109-sg13-skyline-anchored-readout/`, closeout at `.codex/history/109-sg13-skyline-anchored-readout.md`, and archived plan at `.codex/history/plans/109-sg13-skyline-anchored-readout.md`. Update roadmap/cycle records at closeout. Do not change symbol identity, forecast values, timeline position, picker behavior, other typography, existing semantics, or gesture behavior. Keep lower indicator contents and spacing unchanged while translating its whole row up 23 dp.

## Functional invariants

- Translate the selected-location row and the complete associated central readout as one block: picker trigger, temperature/symbol pair, weather-event text row, any source-time disclosure, Sun/Moon facts, and high/low.
- Insert the selected weather event as text between temperature and sunrise, with the same font weight as the location label. Use the existing selected-condition presentation value (`CatalogTimelineView.condition`) rather than deriving text from the scene or icon; when it is absent, show an explicit unavailable status instead of a weather claim. A carried condition retains its original source-time disclosure beside the new row and must not read as newly supplied.
- At the 411×846 dp Pixel 3 XL reference viewport, align the Chicago text baseline with the top edge of the third tallest building shown in the board's `PROPOSED PLACEMENT` panel. Tune the offset against installed evidence to meet that landmark.
- Preserve existing alignment, scaling, values, and merged accessibility semantics; preserve existing typography except for the new text row's specified font weight.
- Translate the lower precipitation/wind cue and value fields plus the blank center slot upward by exactly 23 dp as one row, separately from the center readout. Keep the atmospheric scene fixed and the timeline anchored at the bottom of the screen; neither moving group may reposition the timeline.
- Preserve selected-time authority, picker action, and existing tap/swipe gesture meaning.

## Installed conditions

- Review the approved 411×846 dp Pixel 3 XL portrait reference at default font scale and the affected 360×800 dp compact portrait layout at default font scale. The skyline baseline is judged against the marked Proposed Placement landmark in the regular capture; inspect the same relationship and spacing in the compact capture.
- Because SG.13 adds visible text, inspect the affected readout at 1.3 font scale on the regular viewport for clipping or collisions; record the device setting and result. Keep this a focused text-fit check, not a broad font-scale matrix.
- RTL changes neither the approved landmark nor the selected-condition meaning; no RTL matrix belongs to this design slice. SG.13 adds no animation, so reduced-motion still presents the same selected instant, event text, and facts. Record these applicability decisions; dedicated TEST slices own cross-cutting installed acceptance.

## Ordered steps

1. On activation, inspect the existing main-screen layout and SG.12 selected-condition/provenance presentation; record baseline regular and compact device settings and captures.
2. Add the selected weather-event text between temperature and sunrise with location-label font weight. Position the complete central readout group at the approved third-tallest-building anchor. Separately offset the entire lower indicator row upward by exactly 23 dp; keep scene and timeline fixed.
3. Build/install the app. Capture regular 411×846 dp and compact 360×800 dp installed states at default font scale, plus the focused 1.3 font-scale readout state. Compare the Chicago baseline with the marked board landmark at regular size, and inspect compact spacing above the lifted lower row and fixed timeline. Use a clean product capture for the board comparison; use an existing debug selection route only where needed for alternate event states.
4. Check representative supported, carried, and unavailable event text and source disclosure where available through the existing selection path; inspect selected-time, picker, center swipe/tap, and timeline gesture ownership in the shifted layout. Compare supplied values with their shown values. Record PASS/FAIL/NOT RUN per check.
5. Inspect `git diff --check` and final scoped diff. Record exact commands, screenshots, observations and limits; close only when the approved anchor and affected interactions are evidenced. Do not add or run automated tests without user authorization.

## Acceptance and evidence

Installed Pixel 3 XL captures show the location row baseline aligned to the third tallest building cap in the Proposed Placement board at the regular reference viewport. The selected weather event appears as text between temperature and sunrise, reflects the selected condition or truthful unavailable state, and uses the same font weight as the location label. Carried conditions retain source-time disclosure. The central readout hierarchy remains intact; the separate precipitation/wind row and blank slot have moved upward exactly 23 dp. The timeline stays anchored at the bottom and does not move with either group. In the compact viewport the groups stay legible and do not collide; the focused 1.3 font-scale check records the new row's text fit. Focused selection, picker, and directly affected gesture checks preserve existing behavior. Save actual captures under `.codex/test-artifacts/109-sg13-skyline-anchored-readout/` (regular, compact, 1.3 font-scale, and available event/provenance states) and record PASS/FAIL/NOT RUN results in its `README.md`, including device size/density, font scale, build/install commands, selected state, and comparisons to the approved board.

## Board package

Combined objective-specific behavior and UI artwork board: `.codex/concepts/SG.13-skyline-anchored-center-readout.svg` (PNG preview `.codex/concepts/SG.13-skyline-anchored-center-readout.png`), clean installed current-state reference `.codex/concepts/SG.13-current-main-screen-reference.png`, and explanatory contract `.codex/concepts/SG.13-skyline-anchored-center-readout.md`. Product owner approved this board package as shown on 2026-11-05, with no further constraints stated. The board governs the baseline-to-third-tallest-building relationship, weather-event text row and font-weight match, center-group positioning, separate exact 23 dp lower-row movement, and bottom-anchored timeline. Its current-screen image is a reference, not after-state evidence.

## Verification limits and exclusions

Authorized verification after activation: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py run` or equivalent release install for a clean product capture, `python scripts/dev.py screenshot --serial 8AKY0M32X --output <cycle evidence path>`, focused manual checks above, and `git diff --check`. The connected Pixel 3 XL (`8AKY0M32X`) was visible and authorized for installed review. Automated tests were not added or run. Execution results, installed captures, and NOT RUN states are recorded in `.codex/test-artifacts/109-sg13-skyline-anchored-readout/README.md` and `.codex/history/109-sg13-skyline-anchored-readout.md`.

Risks: the new text row consumes vertical space and may collide with Sun/Moon or lower indicators in the compact viewport; the skyline cap is a visual reference that needs installed comparison rather than a guessed numeric offset; carried/unavailable condition labels must remain truthful. If a collision or semantic mismatch appears, resolve it within this readout composition boundary and document the exact adjustment.

Out of scope: new weather mapping or forecast behavior, scene artwork or timeline movement, picker/navigation changes, lower-indicator content, responsive redesign beyond the affected compact layout, animation, RTL, TalkBack, and broad system/environment acceptance.
