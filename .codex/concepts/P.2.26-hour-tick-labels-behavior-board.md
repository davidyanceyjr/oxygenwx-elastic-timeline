# P.2.26 — Long-tick labels behavior board

**Review state:** Product owner approved this revised behavior contract on 2026-11-11.

## Goal

Use the existing longest hour ticks as a steady three-hour time reference without crowding the timeline. Label every longest tick, and distinguish noon from midnight by writing `12 PM` and `12 AM`.

## Behavior contract

| State or event | Required behavior |
| --- | --- |
| Daily scale | Keep the existing daily timeline appearance; do not show the new hourly numerals. |
| Enter hourly scale | Label each visible longest whole-hour tick. The current longest ticks fall every three local hours, yielding labels such as `12 AM`, `3`, `6`, `9`, and `12 PM`. Leave intervening whole-hour and half-hour ticks unlabelled. |
| Tick and symbol | Center each numeral below the lower end of its long tick. The numeral names that tick's local hour. Keep weather symbols at their established `:30` centers to the right of their source-hour ticks; do not change event mapping, missing-value behavior, symbol position, or fact time. |
| Scrub horizontally | Labels travel with their own long ticks. Show a label only when its tick and text bounds fit in the visible timeline. The active-catalog focused-time readout continues to show the selected local time to the minute. The fixture/debug readout retains its existing nearest whole-hour source-sample cue, including during sub-hour scrubbing; the underlying fractional selection remains authoritative and unchanged. |
| Cross local noon or midnight | Distinguish the local-hour `12` tick as `12 PM` at noon and `12 AM` at midnight. The selected weekday above the symbol row updates at the local-date boundary. Keep the selected `Instant` continuous. |

## Flow

The companion SVG board shows unchanged daily mode, labels below each visible longest tick, label anchoring while scrubbing, and an explicit `12 AM` at the weekday transition.

## Invariants and boundary

- Apply the same label rule to active-catalog and fixture/debug timelines.
- Preserve the current longest-tick cadence, 48 dp/hour axis, 24 dp hourly symbols, selected weekday, focus/playhead, source-time mapping, selected-time authority, and gesture ownership.
- Do not add a separate time control, change the selected time when zooming, move hourly weather symbols onto full-hour ticks, or alter daily artwork.
- This is scoped UI work; broad TalkBack, RTL, and system/environment acceptance stays with designated TEST work.

## Proposed acceptance states

1. Active-catalog hourly view: labels appear below all visible longest ticks, including `3`, `6`, and `9` in the current device viewport. Intervening ticks remain unlabelled; symbols stay at `:30`, `WED` remains above the symbol row, and the selected local time to the minute remains below the axis.
2. Fixture/debug hourly view: the same label cadence and unchanged source-hour symbol mapping. Its focused-time cue continues to show the nearest whole-hour source sample; sub-hour selection remains intact even though the cue does not display its fractional minutes.
3. Hourly scrub across noon and local midnight: labels stay with their ticks, `12 PM` and `12 AM` distinguish the two long ticks, the weekday changes at midnight, and the selected state follows the gesture without a label-driven jump. The catalog readout remains minute precise; the fixture/debug cue retains its nearest whole-hour source-sample format.
4. Daily scale reference: no new hourly numerals appear.

The UI artwork board uses an actual installed screenshot with proposed labels overlaid for review. Its raw capture is context, not installed acceptance evidence.
