# P.2.18 — Timeline weather event icons

## Board purpose

This combined behavior and UI artwork board uses fresh installed Pixel 3 XL screenshots as the input state and shows the proposed daily and hourly timeline placements. The SVG board and PNG preview are adjacent. The enlarged daily-alignment board is `P.2.18-timeline-weather-event-icons-daily-alignment.svg`, with a PNG preview alongside it. The screenshot assets are `P.2.18-timeline-weather-event-icons-before.png` and `P.2.18-timeline-weather-event-icons-hourly-reference.png`.

## Owner direction

The product owner directed that the daily timeline use daily forecast events, placing each event icon below its weekday label, above the divider. In hourly mode, the timeline uses hourly forecast events. The accepted data rule is one icon per date from its daily representative forecast time in daily scale, and one icon per visible hourly forecast sample in hourly scale. Omit an icon when the corresponding time bucket has no supported, mapped event.

## Behavior contract

- Daily scale: each visible weekday label may have the corresponding date's daily representative weather event icon centered beneath it and above that date's divider/tick. Icon and three-letter weekday label share the same projected date center (`noonX` in the current renderer); the enlarged FRI panel makes that alignment reviewable. Use that date's daily representative projection; do not use the currently selected hour or borrow a neighboring date's event.
- Hourly scale: each visible hourly sample may have its mapped event icon aligned with the corresponding hourly tick in the icon band above the timeline divider. The icon follows its hour as the tape moves during hourly scrubbing.
- Reuse the existing `WeatherSymbolId` catalog, artwork, accessible names, and event-to-symbol projection. This slice adds no event IDs, provider mappings, weather descriptions, or new symbol artwork.
- A missing, unsupported, or unmapped event has no timeline icon; it is not rendered as clear, dry, or a generic substitute. Existing forecast text and source disclosures retain their meaning.
- Icons are visual context. They do not change selected time, playhead position, scale, tick labels, daily drag, hourly scrub, double-tap scale toggle, or main-area navigation.
- Maintain a compact legible icon size at the hourly 16 dp/hour scale, with no icon collision or loss of the existing timeline labels, divider, selected-time cue, and forecast facts.

## Board reading guide

1. **Before / input:** the actual installed active-catalog Pixel screen in daily scale, Florence, with Wednesday 7:59 PM selected.
2. **Daily / proposed:** retain the weekday/date labels and existing divider. Add one date-level event icon beneath each visible weekday label. Icon artwork in this concept panel illustrates placement and varied event families; runtime symbols come from the corresponding daily records.
3. **Hourly / proposed:** use the actual installed hourly timeline layout as the reference. Add hour-level icons in the band above the divider, aligned to hourly samples. The center icon illustrates the selected sample; runtime symbols come from the corresponding hourly records.

The proposed-output panels are artwork concepts, not installed post-change screenshots or claims about the forecast events in the source capture. The proposed board and contract are pending owner review; they do not authorize implementation or activate a cycle.

## Acceptance guided by this board

Installed Pixel evidence should show the daily and hourly placements against the exact existing layout; each displayed icon should match the event supplied for its date/hour; missing events should leave the slot empty; and direct daily drag, hourly scrub, and scale toggling should retain their existing behavior. The icon band must remain readable and must not obscure the day/hour labels, divider, playhead, or forecast facts. Verify the icons' accessible meaning within the changed timeline states without expanding into the app-wide accessibility or system matrix.
