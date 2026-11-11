# P.2.25 — Hourly weekday label UI artwork board

**Review state:** Product owner approved as shown on 2026-11-10.

## Visual objective

Keep the selected local date's weekday visible in hourly mode, matching the uppercase selected-label treatment in the daily timeline. The selected weekday sits above the fixed playhead so it stays in view through ordinary hourly scrubbing.

## Device reference

Captured on 2026-11-10 from the connected Pixel 3 XL (`8AKY0M32X`), Android 12, 1440 × 2960 px, 560 dpi, 411 dp logical width. The daily reference shows the existing multi-date weekday row. The hourly reference at selected Tuesday 7:00 AM has no weekday label because the selected date's midnight boundary is outside the viewport. These are current-state references, not slice acceptance evidence.

- Daily reference: `P.2.25-daily-dow-reference.png`
- Hourly before: `P.2.25-hourly-dow-before.png`
- Behavior flow: `P.2.25-hourly-weekday-label-behavior-board.svg`

## Proposed appearance

The SVG board shows four reviewable states: current daily label treatment; current hourly scale at 7:00 AM with no weekday in view; proposed hourly scale with `TUE` centered above the playhead; and a selected Tuesday 11:59 PM state where `TUE` remains focused, `WED` text is hidden while its text bounds overlap `TUE`, and the local-midnight tick remains visible. At earlier times, such as Tuesday 10:39 PM, `WED` remains at its midnight tick; its text returns as soon as its bounds clear the focused label. The selected date is not drawn twice.

Use the existing three-letter uppercase weekday text and selected-label emphasis. Keep the label inside the existing hourly timeline band, above the event-symbol row. Keep the center playhead, divider, hour ticks, selective hour labels, 48 dp/hour scale, and 24 dp half-hour symbols unchanged. Other visible dates retain their midnight-label positions; suppress only adjacent weekday text whose bounds overlap the focused text, retain its tick, and restore the text as soon as it clears. Daily labels and artwork remain unchanged.

## Review boundary

The board proposes the selected-date label's visibility and position, plus the approved text-overlap treatment at the adjacent midnight boundary. It does not represent a production screen or installed acceptance. After approval, installed active-catalog and fixture/debug captures and focused scrub observations establish the slice exit.
