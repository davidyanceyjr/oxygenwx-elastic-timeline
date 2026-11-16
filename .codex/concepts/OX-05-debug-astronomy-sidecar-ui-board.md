# OX-05 revised UI artwork board — deterministic fixture states

**Approval status:** Draft for product-owner review. Approval is pending for this revised scope. This schematic does not replace installed acceptance evidence.

**Cycle:** `144-ox05-depth-ordering` · **Slice:** `OX-05`

## Route and visual objective

From the existing debug session, load the fixed OX-05 review fixture directly into the primary `CatalogTimeLabScreen`. The fixture remains labeled **DEMO DATA · OX-05 authored synthetic astronomy fixture**. No saved-location search or network access is needed.

Show two existing First City states without changing the city, readout, forecast facts, timeline, or gestures:

1. October 28, 2026 at 12:00 CDT: Sun and Moon overlap in Background. The Sun is drawn first, the Moon after it; the nearer cloud layers are absent in this clear sample.
2. October 29, 2026 at 12:00 CDT: moderate-rain sample with 88% cloud cover. Solar and lunar event inputs are present in the same fixture; the established rain rule hides the Sun cue, nearer clouds may occlude the Moon, and the single rain field draws in front of near buildings.

## Authored event inputs shown on this board

Both dates use synthetic review values: sunrise 06:00, sunset 18:00, moonrise 04:30, moonset 17:30, phase 0.50. They are selected only to demonstrate the renderer's overlap behavior and are not real astronomy.

At 12:00, the current mapping places the Sun at `(0.500, 0.180)` and the Moon at approximately `(0.558, 0.187)`, producing a partial overlap. Event data remains in a debug-only sidecar and does not become provider daily facts.

## Preserved appearance

- Keep the approved Background → Middle-Ground → Foreground ordering and local layer order.
- Keep the existing artwork, essential forecast text, timeline, and selected-time semantics.
- Rain clouds remain in the upper scene; the single precipitation field spans the city and crosses near buildings.
- `TIME_WASH` and `READOUT_QUIET_ZONE` retain their current effect and placement.
- Show the fixture's synthetic provenance in the debug DEMO DATA label; do not present its event times as forecast facts.

## Review request

Approve the two illustrated primary-route target states, the debug-only fixture entry, the exact synthetic event values, and the provenance label. The board approves no changes to the normal live catalog route or provider data. Installed device captures are still required for OX-05 visual acceptance.
