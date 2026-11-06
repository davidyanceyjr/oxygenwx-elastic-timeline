# P.2.20 — Daily and hourly timeline icon refinement

## Board purpose

This combined behavior and UI artwork board pairs a fresh installed Pixel 3 XL daily screen with the requested output: larger date-level symbols that occupy the band below each weekday label, and an hourly layout with no event symbols. The SVG board has a PNG preview alongside it. `P.2.20-daily-hourly-icon-refinement-before.png` is the actual installed input capture.

## Owner direction

The owner requested that daily icons grow to fill the space between the day-of-week label and timeline divider, and that weather icons be removed from the hourly timeline because they clutter it.

## Behavior contract

- Daily scale continues to show each date's existing daily representative weather event.
- Center each daily icon directly beneath its matching three-letter weekday label and above its date tick/divider, sharing the date's timeline coordinate.
- Increase daily symbol size to the largest existing artwork size that fits in the band between the weekday label and divider without overlap. Keep label, icon, tick, playhead, and divider legible.
- Hourly scale renders no weather event icons. Preserve hour labels, ticks, selected-time playhead, divider, and existing time selection.
- Reuse existing event projection and WeatherSymbol artwork. Do not change mappings, add event identities, or add fallback icons. A missing daily event remains icon-free.
- Preserve daily drag, hourly scrubbing, double-tap scale toggle, selected-time continuity, main forecast facts, and gesture ownership.

## Board reading guide

1. **Before/input:** actual installed active-catalog Pixel daily screen captured on 2026-11-06.
2. **Daily/proposed:** a focused timeline strip shows larger event symbols centered beneath weekday labels, filling the available band above the divider. Example symbols communicate scale and placement only.
3. **Hourly/proposed:** the symbol band is removed; hour labels, ticks, playhead, and divider remain.

Output panels are artwork concepts, not post-change installed evidence or claims about event identity. The board and contract await owner review; implementation remains gated until approval and plan activation.

## Acceptance guided by this board

Installed daily evidence should show enlarged symbols centered beneath the matching weekday labels and filling the available band without touching labels or divider. Hourly evidence should show the same timeline with no symbols and with labels, ticks, playhead, divider, and forecast facts readable. Focused interaction checks should show daily drag, hourly scrub, and scale toggling retain their current behavior.
