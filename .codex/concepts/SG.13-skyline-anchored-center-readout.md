# SG.13 combined behavior and UI artwork board — draft

Status: Draft for product-owner review; not yet approved.

## Objective

Move the complete central forecast readout upward until the baseline of the `Chicago` location label aligns with the horizontal top edge of the tallest background tower in the Pixel 3 XL reference composition.

## Visual contract

- The board's current installed reference is `SG.13-current-main-screen-reference.png`, a clean release capture at 1440×2960 px / 560 dpi.
- At the 411×846 dp reference viewport, translate the existing central readout group upward by approximately 24 dp, then tune against the visible tower cap so `Chicago`'s text baseline meets that horizontal line.
- Move the location label and picker, temperature/symbol pair, any source-time disclosure, Sun/Moon rows, and high/low row together as one group. Preserve their internal order, spacing, alignment, typography, content, and semantics.
- Keep the lower precipitation/wind row, its center gap, the timeline, and the background scene at their current positions.
- At the compact 360×800 dp viewport, preserve the same hierarchy and skyline relationship where the tower landmark is visible; avoid overlap with lower indicators and timeline.

## Behavior invariants

This is a vertical composition change only. It does not change the selected instant, forecast values, symbol identity, picker action, spoken descriptions, source/provenance meaning, timeline position, swipe/tap behavior, or lower-indicator behavior. All readout content continues to move together when the app lays out the screen.

## Approval

Product-owner approval is pending. The board defines the exact visual acceptance target; no implementation is authorized until approval is recorded in the bounded plan.
