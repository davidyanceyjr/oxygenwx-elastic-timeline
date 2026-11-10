# MR.2 main readout behavior board · v5

**Status:** Revised to follow product-owner direction and approved by the product owner on 2026-11-09.

## Authority and scope

The product-owner-approved rendered target `MR.2-main-readout-target-ui-render-v4.png` and its description are authoritative for the center readout's appearance, indicator content, and arrangement. Elements outside the center readout in the full-screen render are fixed context only.

This cycle is a single refactor of the center readout: its weather visual and halo, location label and adjacent pin icon, condition label, current and feels-like temperatures, and Precip, Wind, and Humidity indicators, including the wind glyph. All other elements and behavior are out of scope. The location pin stays beside Florence as drawn; preserve its existing action and touch-target dimensions and behavior.

## Approved readout arrangement

1. Remove the gold halo around the center weather visual. Place the moon and stars above the Florence label; keep the location pin beside Florence as shown in the approved target.
2. Place `Clear night` directly under Florence. Keep the moon/stars, location label, and condition in a compact stack directly above the current temperature, with approximately 7 dp between adjacent elements. Preserve the approved city-name relationship to the tallest roofline; do not change the skyline.
3. Keep current and feels-like temperatures side by side with aligned text baselines, using the approved target's hierarchy and spacing.
4. Place Precip, Wind, and Humidity directly below the temperature pair, in that order. Keep approximately 7 dp between each icon and label and align their values as shown in the approved target.
5. Preserve the approved indicator labels, icons, and arrangement; existing responsive center-readout text and icon sizes; visual transparency; and the same hierarchy in regular and compact portrait.

The example values in the approved artwork describe that rendered state. At runtime, every displayed value continues to come from the existing selected forecast instant and input; this refactor does not change value selection, source disclosures, or unavailable-value behavior.

## Wind direction behavior

When a wind-from bearing is supplied, show a directional arrow pointing toward wind travel, using the supplied bearing plus 180 degrees. Preserve spoken wind-from meaning. When no bearing is supplied, retain the neutral wind glyph shown in the approved target.

The approved target depicts the regular-portrait unavailable-bearing appearance. Apply the same approved hierarchy and indicator arrangement in compact portrait; use the wind-direction rule above for supplied-bearing states. Installed captures and observations remain the acceptance evidence for those states.

## Acceptance boundary

Review only the center-readout composition, values, spacing, alignment, responsive sizing, and supplied/unavailable wind glyph state. Do not assess or change the surrounding scene, skyline, timeline, system bars, navigation, location-picker action, or other interactions under this cycle.
