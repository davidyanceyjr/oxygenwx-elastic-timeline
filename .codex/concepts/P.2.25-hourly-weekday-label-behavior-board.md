# P.2.25 — Hourly weekday label behavior board

**Review state:** Product owner approved as shown on 2026-11-10.

## Owner goal

Show the day of week while using hourly precision, including when the selected date's midnight boundary is outside the visible timeline. The daily view is the visual reference for the weekday label.

## Observed device state

On 2026-11-10, the connected Pixel 3 XL showed weekday labels in daily scale. In hourly scale at Tuesday 7:00 AM, the visible 48 dp/hour window contained no weekday label because local midnight was off-screen. The captures are `.codex/concepts/P.2.25-daily-dow-reference.png` and `.codex/concepts/P.2.25-hourly-dow-before.png`. Cycle 124's installed evidence also records the existing midnight label near a local date boundary.

## Proposed behavior

- In hourly scale, show the selected local date's uppercase three-letter weekday at the top of the existing weekday-label row, centered over the fixed playhead. Keep it visible at every selected hour, including when that date's midnight tick is outside the viewport.
- Derive the label from the selected instant using the timeline's existing active-location or fixture date context. Scrubbing within the same local date keeps the same label; crossing local midnight changes it to the new date's label.
- Keep other visible dates' weekday labels anchored to their local-midnight ticks. When the selected date's midnight is visible, show its weekday at the playhead only so the same date is not labeled twice; retain its midnight boundary tick.
- Compare each other date's centered weekday text bounds with the focused label's text bounds. If they overlap, suppress only that adjacent weekday text and retain its local-midnight tick. Show the adjacent label again as soon as the text bounds no longer overlap; do not use a fixed time or spacing threshold.
- Leave the daily label layout unchanged. Entering or leaving hourly scale, label updates, and scale animation do not select or retarget a time.

## Flow and invariants

```text
Daily scale
  └─ enter hourly at existing selected instant
       ├─ derive selected local date → show its DOW over the fixed playhead
       ├─ scrub within date → keep DOW; selected instant follows existing scrub rule
       ├─ cross local midnight → update DOW for the new selected date
       ├─ adjacent label bounds overlap focused label → hide adjacent text; retain its midnight tick
       ├─ label bounds separate → restore adjacent text at its midnight tick
       └─ return to daily → preserve the existing selected instant and daily layout
```

The label describes the selected instant's local calendar date. It does not change the 48 dp/hour axis, selective hour labels, half-hour symbol placement, event mapping, missingness, forecast facts, selection, scale, or gesture ownership. Adjacent-date labels describe their midnight boundaries; only their text is suppressed while its bounds overlap the focused weekday text, with the boundary tick retained and the label restored once clear.

## Scope and exit

The proposed behavior applies to the active-catalog and fixture/debug timeline surfaces. Installed evidence must show a selected weekday away from midnight on both surfaces; the adjacent label hidden at Tuesday 11:59 PM while its midnight tick remains; that label restored as soon as its text bounds clear the focused text; a focused local-midnight transition; and the daily state retained as the visual reference. Record screenshots and focused interaction outcomes. Automated tests, broad accessibility/device matrices, and unrelated timeline changes are outside this slice.
