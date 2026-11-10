# MR.2 main readout behavior board · v4

**Status:** Draft for product-owner review. Approval is pending.

## Readout order and vertical anchor

1. Keep the location label first. Put the selected weather-condition description directly below it, so the heading reads as a city and condition pair (for example, `Florence` / `Clear night`).
2. Keep the weather symbol below that heading and above the temperature pair. Remove the thin gold arc around the symbol.
3. Move the complete main-readout group down together: location label and pin, condition, weather symbol, current and feels-like temperatures, and the three supporting metric groups. Align the city-name line with the roofline of the tallest background building as shown in `MR.2-main-readout-target-ui-render-v3.png`. Preserve all internal spacing and relative positions within the group. Keep the skyline and timeline fixed.
4. Show current temperature and feels-like temperature side by side, with their text baselines aligned and the existing text sizes preserved.
5. Place the Precip, Wind, and Humidity groups directly beneath the two temperatures in that order. Each group is icon, label, then value. Use approximately 7 dp between the icon and its label; align the three values on a shared baseline.

## Wind direction and preserved meaning

When a bearing is supplied, show an arrow toward wind travel, calculated as the supplied wind-from bearing plus 180 degrees. Preserve the spoken wind-from meaning. When bearing is unavailable, retain the neutral wind glyph.

The selected instant remains authoritative for every weather value. Preserve the location action and touch target, supplied values and source disclosures, unavailable-value behavior, current type and icon sizes, transparent treatment, city scene, and timeline content, position, and gestures. Apply the same information hierarchy in regular and compact portrait while retaining existing responsive sizing.

## Artwork coverage

`MR.2-main-readout-target-ui-render-v3.png` is the regular-portrait target mockup in the unavailable-bearing state. It illustrates the shared city-name/roofline anchor and the existing readout hierarchy. It is not an installed capture. Compact portrait and supplied-bearing rendered states remain to be prepared before finalizing the UI artwork board.
