# SG.13 combined behavior and UI artwork board

Status: Product-owner approved on 2026-11-05 as the SG.13 behavior and UI artwork board.

## Objective

Position the complete central forecast readout so the baseline of the `Chicago` location label aligns with the top edge of the third tallest background building in the `PROPOSED PLACEMENT` panel. Add the selected weather event as a text row between temperature and sunrise, using the same font weight as the location label. Move the full lower precipitation/wind indicator row up by exactly 23 dp.

## Visual contract

- The board's current installed reference is `SG.13-current-main-screen-reference.png`, a clean release capture at 1440×2960 px / 560 dpi.
- At the 411×846 dp reference viewport, position the central readout group so `Chicago`'s text baseline meets the top edge of the third tallest building in the `PROPOSED PLACEMENT` panel; tune the offset against installed evidence.
- Move the location label and picker, temperature/symbol pair, weather-event text row, any source-time disclosure, Sun/Moon rows, and high/low row together as one group. Insert the selected weather event as text between temperature and sunrise and give it the same font weight as the location label. Preserve existing alignment, other typography, content, and semantics.
- Move the lower precipitation cue/value, blank center slot, and wind direction/speed cue/value together upward by exactly 23 dp as a separate group from the center readout. Keep the timeline independently anchored at the bottom of the screen; neither the center readout nor precipitation/wind row moves it. Keep the background scene fixed.
- At the compact 360×800 dp viewport, preserve the skyline relationship and exact 23 dp lower-row lift; keep the indicators clear of the readout and fixed timeline.

## Behavior invariants

This is a bounded readout composition change: add the selected weather event as text between temperature and sunrise, matching the location label's font weight, and position the complete central group at the specified skyline anchor. The lower precipitation/wind row is a separate group and moves up by exactly 23 dp. The timeline stays anchored at the bottom of the screen and does not move with either group. The selected instant, forecast values, symbol identity, picker action, source/provenance meaning, swipe/tap behavior, and lower-indicator content and semantics remain unchanged.

## Approval

The product owner approved the current board package on 2026-11-05. Approval covers the third-tallest-building anchor in the Proposed Placement panel, the selected weather-event text row and location-weight match, the complete center-group positioning, the separate exact 23 dp lower-row lift, and the fixed scene and bottom timeline. The board defines visual and behavior acceptance; installed evidence remains required.
