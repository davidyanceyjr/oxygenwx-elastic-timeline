# P.2.24 — Hourly forecast glance behavior board

**Review state:** Revised draft for product-owner review. Approval is pending.

## Owner goal

Hourly event symbols are crowded, and the current 16 dp/hour scale lets a viewport-width drag cover roughly 22.5 hours on a 360 dp-wide timeline. Keep several upcoming hourly events readable at once.

## Proposed behavior

- Use one uniform **48 dp/hour** axis across the full hourly timeline on both active-catalog and fixture/debug surfaces; enlarge supported hourly weather symbols from **12 dp to 24 dp**.
- Keep the selected instant under the fixed center playhead. Scale entry and return keep the existing P.2.17/P.2.22 behavior; zoom animation alone never changes selection.
- Keep hourly drag mapping tied to 48 dp/hour. A 360 dp viewport-width drag covers about 7.5 hours, so crossing a full day takes about 3.2 such drags.
- Keep weekday labels anchored to their existing local-midnight positions and hourly event symbols at their existing `:30` positions. Move the hourly weekday-label row upward within the existing 84 dp timeline region so 24 dp symbols fit between labels and the divider. Keep the current selective hour labels and ticks.
- For an exact-hour selection, the next three hourly sample symbols are at +1.5, +2.5, and +3.5 hours because each symbol represents its source sample at the visual half-hour midpoint. At 48 dp/hour those centers are +72, +120, and +168 dp from the playhead; 24 dp symbols have a 24 dp nominal gap and the third symbol remains fully inside a 360 dp viewport.
- Continue to show symbols only for supported mapped events. Missing or unsupported event samples remain icon-free; do not carry, infer, or fabricate an icon.
- Keep daily artwork and spacing, forecast facts, source-time semantics, gesture ownership, scale controls, and the existing 84 dp touch region unchanged.

## Flow and observable result

Daily scale --existing scale toggle--> Hourly scale at the existing entry selection

Hourly scale --drag--> same selected-time model, moved at 48 dp/hour

Hourly scale --existing collapse/return behavior--> Daily scale with existing selection behavior

In hourly scale, a 360 dp viewport displays about 7.5 hours, including the next three hourly sample symbols when those samples have supported mapped events. Daily browsing, hourly tick chronology, exact source values, and selection remain authoritative.

## Scope and acceptance boundary

This board proposes an hourly spacing, icon-size, and label-position change only. It does not change event mapping, icon artwork, missingness, time projection, the P.2.17/P.2.22 gesture contract, or system accessibility behavior. Installed active-catalog and fixture/debug captures plus focused hourly scrubbing observations are still required for acceptance. The separate screenshot `screenshots/hourly_timeline.png` is a current-state reference, not post-change evidence.

## Owner decision

On 2026-11-09, the owner approved this behavior board and the 48 dp/hour scale, 24 dp symbols, and an upward shift of the hourly weekday-label row within the existing timeline band. The owner also asked for the artwork board to isolate the timeline after the earlier comparison sheet felt busy. The plan must preserve the scope and invariants above.
