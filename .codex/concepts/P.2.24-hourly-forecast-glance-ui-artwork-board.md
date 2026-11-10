# P.2.24 — Hourly forecast glance UI artwork board

**Review state:** Product owner approved the revised, simplified artwork on 2026-11-09.

## Visual objective

Make hourly weather symbols large enough to read, with breathing room between consecutive events. The previous comparison sheet felt busy, so this board isolates the timeline itself; it omits the full-screen reference and hero mockup.

## Proposed timeline

The preview represents a 360 dp-wide, 84 dp-high hourly timeline with 9:00 PM selected. At the proposed 48 dp/hour scale, the next three source-hour symbols appear at 10:30 PM, 11:30 PM, and 12:30 AM. Each 24 dp symbol is separated from the next by 24 dp. All three fit within the viewport; the Wednesday label remains anchored at the midnight tick. A viewport-width scrub advances 7.5 hours.

The UI preview uses existing weather-symbol forms and illustrates the intended spacing. It is not an installed app capture or acceptance evidence. The saved current screenshot `screenshots/hourly_timeline.png` remains separate context. After board approval, installed active-catalog and fixture/debug states are still required to judge the interaction and final appearance.

## Constraints and review questions

- Preserve existing weather-symbol artwork, supported-event mapping, and icon-free missing/unsupported samples; use 24 dp symbols in hourly mode.
- Preserve `:30` event positions, hour ticks and selective labels, midnight date anchors, divider, center playhead, and selected-time cue. Fit weekday labels inside the existing 84 dp timeline region.
- Keep daily-scale artwork and surrounding forecast composition unchanged.
- The approved target is 48 dp/hour and 24 dp symbols. On-device review must check symbol legibility, spacing, label clearance, and clipping.
- The board approval is recorded in the P.2.24 cycle plan. The installed UI still requires slice-scoped acceptance evidence.
