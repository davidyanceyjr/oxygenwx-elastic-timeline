# P.2.21 — Daily high on the timeline

## Board status

Approved by the product owner on 2026-11-06. Approval is recorded in `.codex/plans/114-p2-daily-high-timeline.md`. The cycle remains PLANNED and is not activated.

## Purpose

Show each date's projected daily high on the daily timeline, below that date's weather-event symbol and below the timeline divider. The installed Pixel screenshot is the pre-implementation reference.

## Behavior contract

- On the active-catalog daily timeline, show the existing projected daily-high value for every visible date.
- Place the value on the date's timeline coordinate, centered below the divider and directly beneath that date's weather-event symbol. It travels with its date while the timeline moves.
- Show a small `HI` marker with a bold, enlarged numeric value. Size the numeric readout to feel approximately as prominent as the event icon above it, while keeping the full label inside its date slot. Use the existing daily-high presentation value and formatting; do not derive a new high from hourly samples.
- If the daily high is unavailable, show `HI —`; do not substitute zero or another date's value.
- Keep hourly mode free of daily-high labels, consistent with its current icon-free timeline.
- Preserve date/hour labels, event-symbol identity and position, ticks, divider, selected-time playhead and badge, scale gestures, selected-time authority, central forecast facts, and daily/hourly navigation.
- Keep timeline values legible against light and dark scenes. Prevent the new row from colliding with the selected-time badge or system navigation area.

## Artwork guide

The before panel is a fresh installed Pixel 3 XL screenshot captured on 2026-11-06. The proposed daily panel illustrates date-aligned `HI` values beneath the divider, with bold numeric readouts visually comparable in scale to the event icons. Example values are layout samples only; actual values come from the existing daily projection. The hourly panel documents that no daily-high row is added there.

## Acceptance guided by this board

Installed active-catalog daily captures show each visible date's own `HI` value below its event symbol and below the divider, aligned to that date. A focused scrub confirms labels move with their dates and the selected-time badge remains distinct. An unavailable daily high is displayed as `HI —`, with no imputation. Hourly mode remains free of daily-high labels. Existing labels, symbols, ticks, divider, playhead, selected-time badge, forecast facts, and timeline gestures remain readable and functional. Record actual screenshots and focused interaction outcomes; this board is not installed acceptance evidence.
