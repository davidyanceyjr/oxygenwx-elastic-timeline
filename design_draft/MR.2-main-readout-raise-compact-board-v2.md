# MR.2 — Main readout target board · v2

**Approval:** Product owner approved this combined artwork and behavior board on 2026-11-08. The first constructed-panel draft was rejected. This approved revision uses the selected source, `screenshots/updated-main.png`, as the full-screen base.

**Board files:** `design_draft/MR.2-main-readout-raise-compact-board-v2.svg` and its PNG preview, with this contract. The screen image is an installed reference only; the numbered marks and relationship examples describe the target and are not post-change screenshots.

## Owner-directed target

- Remove the thin gold halo/arc around the weather symbol.
- Move the weather symbol, current temperature, feels-like value, and forecast description up 37 dp. Retain the current typography and icon sizes.
- Place feels-like immediately to the right of current temperature, with both values sharing a text baseline.
- Move the Precip, Wind, and Humidity groups up 20 dp. Keep their existing order and align their values on a common baseline.
- When a bearing is supplied, show an arrow that points where the wind travels, derived from the reported wind-from bearing plus 180 degrees. If no bearing is supplied, retain the neutral wind glyph.
- Preserve the transparent treatment, location action and touch target, selected-time values, source and unavailable-value behavior, scene and its phase behavior, and timeline content, position, and interactions.
- Apply the same relationships in compact portrait while retaining the current responsive sizes.

## Behavior contract

The selected instant remains authoritative for weather values. A supplied wind-from bearing is visually converted to a wind-to arrow; this does not change the supplied bearing or its spoken wind-from meaning. Missing direction retains the neutral glyph. Values, source disclosures, unavailable-value handling, scene behavior, and timeline behavior remain unchanged.

## States covered

1. **Regular portrait:** The full installed capture anchors the design. The target removes the gold halo, moves the specified hero facts up 37 dp, places feels-like to the right of current temperature on a shared baseline, and moves the three support groups up 20 dp with aligned values.
2. **Compact portrait:** The same offsets and alignments apply with existing responsive typography and icon sizes.
3. **Wind bearing supplied:** A directional arrow points toward wind travel using the supplied wind-from bearing plus 180 degrees. The arrow example on the board is illustrative and asserts no specific bearing.
4. **Wind bearing unavailable:** Keep the neutral glyph shown by the installed reference.
5. **Preservation:** Scene, location action, selected-time behavior, disclosures, timeline, and gestures remain as currently shown and behave.

The board establishes design intent only. It is not implementation or installed acceptance evidence.
