# Main Readout Refactor — Behavior Board (Proposed)

This board defines the selected-time behavior and visible content for MR.1.
The companion SVG/PNG board gives the flow and states a visual review format.
The Florence Golden Night Weather Dashboard image is the companion UI artwork
board; it defines the intended hero composition and transparent support row.

## Focused-time contract

```text
Timeline focus changes to instant T
             |
             v
Project main-readout facts for T
             |
             +--> Hero: location, condition symbol, temperature,
             |           apparent temperature, condition description
             |
             +--> Support row: precipitation chance, wind speed, humidity
```

- The selected timeline instant is authoritative. Every displayed weather
  value is projected for that instant and updates as focus changes while
  scrubbing.
- Preserve the existing source and missing-value rules for each fact. A
  missing or unsupported fact stays visibly unavailable; it is never replaced
  with zero or inferred from a neighboring timestamp.
- Keep condition meaning and text tied to the supported condition for the
  focused instant. Existing source-time disclosure remains in force wherever
  the presentation currently requires it.
- Decorative arc and weather artwork may respond to selected time, but cannot
  delay or alter the displayed facts or selected instant.

## Main-readout content map

```text
Location name + location icon
                 |
          thin gold arc
     condition icon inside arc
                 |
       primary temperature
     ≈ apparent temperature
     condition description
                 |
 Precipitation | Wind | Humidity
```

- The apparent-temperature line uses `≈` (U+2248), visually larger and cyan;
  the temperature is white. The design does not require a “Feels Like” label.
- The bottom row contains exactly three evenly spaced values: precipitation
  chance, wind speed, and humidity. Each item retains a readable text label.
- The readout stays transparent over the interactive skyline. Do not add
  opaque cards, boxes, or filled containers.
- Remove the side indicator columns, sunrise/sunset, moonrise/moonset, daily
  high/low, visibility, UV, clouds, air quality, dew point, and gusts from the
  main readout. This slice does not create detail views or mini-graphs.
- The skyline, timeline, selected-time control, and their gesture ownership
  remain in place.

## Acceptance states

1. Focus a timestamp with supported hero facts and all three support values:
   all seven readout facts correspond to that focused timestamp.
2. Scrub to another timestamp: the hero and three support values update
   without waiting for decorative animation and without moving the focused
   instant.
3. Focus a timestamp with an independently missing support or hero value:
   that value is shown unavailable under existing presentation rules; other
   supported values continue to display normally.
4. Inspect the regular main-screen composition: the Florence hero hierarchy,
   gold arc, and three-item transparent lower row are visible; side indicators
   and removed secondary values do not appear in the main readout.

The companion artwork board defines visual placement and appearance. This
behavior board defines fact selection, missing-value behavior, and preserved
interaction boundaries. Installed screenshots and focused interaction
observations are still required to close the slice.
