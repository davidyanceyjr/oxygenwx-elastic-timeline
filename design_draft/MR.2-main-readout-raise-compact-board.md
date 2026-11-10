# MR.2 — Raise and align the main readout

**Approval status:** Rejected by the product owner on 2026-11-08 because the constructed target panels do not reflect the expected main-screen UI. This package is not approved and cannot govern implementation.

**Superseding direction:** The owner clarified that the gold halo must be removed while current typography and icon sizes stay. The rejected artwork below has not yet been revised to that direction.

**Installed reference:** screenshots/updated-main.png is the connected Pixel 3 XL main-screen capture. It shows the current Florence readout and anchors the artwork. The proposed target panels are diagrams, not post-change screenshots.

## Owner-directed visual target

- Move the weather symbol and its existing gold arc, current temperature, feels-like value, and forecast description upward by 37 dp as a group. Keep current typography and icon sizes.
- Place feels-like immediately to the right of current temperature. Align their text baselines.
- Move the Precip, Wind, and Humidity groups upward by 20 dp. Align the three metric values on one baseline.
- Keep the gold arc and existing main-readout appearance. Do not remove the arc or reduce typography or icon sizing.
- When a wind bearing is supplied, show a directional arrow pointing where the wind travels. The source bearing is wind-from, so orient the arrow to bearing + 180 degrees. When bearing is unavailable, keep the neutral wind glyph.
- Keep the location action and its 48 dp touch target, forecast values, focused-time behavior, source and unavailable-value disclosures, scene, and timeline unchanged.

The regular and compact portrait target panels show the same owner-directed relationships. Existing responsive sizing remains in effect; the 37 dp and 20 dp offsets and alignments apply in both states.

## Behavior contract

The selected instant remains authoritative for the displayed weather values. The wind arrow is a visual wind-to indicator derived from the supplied wind-from bearing; it does not change the reported bearing or its spoken “from” meaning. Missing direction remains represented by the neutral wind glyph. No weather values, source disclosures, unavailable-value rules, scene behavior, timeline content, or gesture behavior change.

## Acceptance states

1. Regular portrait main screen: the weather symbol with its gold arc, current temperature, feels-like value, and forecast description are 37 dp higher; feels-like sits to the right of current temperature with aligned text baselines. The three supporting groups are 20 dp higher, and their values share a baseline.
2. Compact portrait main screen: the same offsets and baseline relationships fit without clipping or overlap, while the existing responsive sizing remains intact.
3. Wind-direction states: a supplied bearing produces an arrow oriented toward the wind’s travel direction; a missing bearing keeps the neutral glyph. The focused-time values and existing spoken wind-from direction remain correct.
4. Preservation: the location action remains usable; the gold arc, typography and icon sizes, scene, timeline position and contents, and timeline interactions remain unchanged.

This is a combined behavior and UI artwork board. Owner approval is pending for this revised package.
