# Timeline midnight labels and half-hour event icons — behavior board

**Status:** Product owner approved this revised behavior board on 2026-11-09, including the fixture/debug condition-to-symbol mapping, daily-drag release correction, and full-window boundary-clamp verification.
**Prepared:** 2026-11-09.
**Related current behavior:** P.2.20 centers weekday labels at daily overview noon and removes hourly event icons. This proposal changes those two display rules only after owner approval.

## Requested outcome

Place each existing weekday label at its local calendar-day boundary (`00:00`) on both daily and hourly timeline scales. In hourly scale, center each supported hourly weather-event symbol at the half-hour position inside its hour. Apply the same visible behavior to the active-catalog and fixture/debug timelines. On daily scale, releasing a timeline drag selects local noon on the target date, subject to the existing active-catalog interval clamp.

For this prototype, the active selection interval is the complete 44-local-date `CatalogWindow` envelope: local midnight at D-30 through the final nanosecond before D+14. The interval is not derived from partial field support, so this slice verifies the existing full-window endpoint clamp and makes no partial-day endpoint claim. A debug-only active-catalog probe requests an instant beyond the last bound and confirms the coordinator clamps it to the actual window end.

The phrase “hourly weather action icon” is interpreted here as the existing noninteractive weather-event symbol, not a new tappable action.

## Behavior contract

### Date labels

- The weekday label and its boundary tick share the instant returned by the selected date's `atStartOfDay(locationZone)`.
- Use the location's IANA timezone for active catalog data. Use the fixture's existing local-day axis for fixture/debug data.
- Apply the midnight placement in both daily and hourly scale. The label identifies the date that begins at that boundary.
- Keep daily overview selection and representative forecast data at the existing local-noon anchor. The weekday label's new position does not retarget selection or daily facts.
- Keep the daily weather symbol and daily-high value at their current per-date overview coordinate. The label becomes a date-boundary cue; the noon-centered content remains the date's representative summary.
- After an actual daily-scale drag, release selects local `12:00` on the date under the playhead. Active-catalog selection uses the location's IANA timezone and existing full-window interval clamp; fixture/debug selection uses its existing daily-noon sample. A single tap remains a no-op.
- The active catalog's selectable interval remains the complete 44-local-date envelope. A debug-only SIMULATED request beyond its last bound verifies clamping to `endExclusive - 1 nanosecond`; partial field support does not shorten this interval in this slice.
- Preserve P.2.22 interaction semantics: daily double-tap enters hourly scale at `07:00`, and an hourly-to-daily scale-only toggle preserves the exact selected instant. Hourly scrubbing remains unchanged.

### Hourly event symbols

- Preserve each hourly fact's exact valid instant `t` (normally `HH:00`) as its source time.
- Position that fact's existing event symbol at `t + 30 elapsed minutes`, the visual midpoint of its hour. Add a subtle half-hour tick at that same position so the alignment is visible.
- A midpoint is only an artwork position. It does not create a `:30` weather fact, event onset, or change to source-time disclosure.
- Draw a symbol only when the corresponding hourly sample has a supported mapped symbol. Missing, empty, or unsupported samples remain icon-free; do not carry an adjacent symbol into the gap.
- Keep hourly fact values, event mapping, source disclosure, gesture ownership, and scale behavior unchanged except for the explicitly stated daily-drag release target.
- In DST zones, calculate midpoint positions from each sample's absolute `Instant`. This places symbols correctly in 23-hour and 25-hour days and keeps repeated wall-clock hours distinct. Do not invent nonexistent hours.
- Do not clamp an icon onto a different time if its half-hour anchor falls outside the displayed interval; omit that edge icon instead.

### Fixture/debug condition-to-symbol mapping

The legacy fixture/debug timeline receives `ForecastSourceHour.condition` as one of six existing `PresentedCondition` categories and has no `WeatherSymbolId` today. For that surface only, map the typed category to the closest existing symbol ID as follows:

| Fixture condition | Existing symbol ID |
| --- | --- |
| `CLEAR` | `SUNNY` |
| `PARTLY_CLOUDY` | `PARTLY_SUNNY_CLOUDY` |
| `CLOUDY` | `CLOUDY_OVERCAST` |
| `RAIN` | `RAIN` |
| `SNOW` | `SNOW` |
| `STORM` | `THUNDERSTORM` |

Use this mapping only to draw the fixture's compact timeline symbol. It does not add a weather fact, alter the readable forecast condition, derive a symbol from precipitation or warning fields, or change catalog event mapping. If a future fixture condition has no explicit row, leave its icon slot empty. Position the mapped symbol at the fixture sample's `LocalDateTime + 30 minutes`; the sample remains the source time.

## State flow

```text
Timeline date + location zone
        │
        ├─ Daily scale ── date label + boundary tick at local 00:00
        │                 daily representative symbol/high stay at local-noon overview
        │
        └─ Hourly scale ─ date label + boundary tick at local 00:00
                          supported hour fact at t ── symbol + minor tick at t + 30 min
                                                       source fact remains at t
                          legacy fixture condition ── existing symbol ID from the six-row map above
```

See the paired behavior SVG for the same rule in a visual timeline contract.

## In scope

- Weekday label placement on the daily and hourly timeline tapes.
- Compact hourly event symbols and half-hour guide ticks in hourly scale.
- Active-catalog and fixture/debug timeline surfaces, kept consistent.
- Debug-only active-catalog verification of the existing full-window endpoint clamp and the SG.4 adjacent supported/missing event pair.

## Invariants and exclusions

- No change to catalog dates, timezone conversion, day bounds, sample timestamps, weather facts, catalog event-to-symbol mappings, daily overview anchor, selected-time semantics, or the coordinator's full-window interval derivation. The debug probes do not alter production data. The six-row fixture/debug mapping above is the only new category-to-symbol mapping.
- No change to daily symbol/high values or their current noon-centered coordinate.
- No change to gesture ownership, hourly scrubbing, single-tap behavior, forecast browser behavior, source switching, accessible forecast text, or main readout. Daily-drag release to local noon is included in this board's approved scope.
- The event symbol remains noninteractive. It does not replace readable weather facts.
- This board does not authorize implementation or installed acceptance claims. The approved plan must define focused installed screenshots and affected-interaction checks; broad system testing remains outside this visual slice.

## Acceptance guided by this board

1. In daily and hourly scale, each visible weekday label sits on its date's local-midnight tick on both active-catalog and fixture/debug surfaces.
2. Daily representative symbols and highs remain centered on their existing overview coordinate and continue to represent the correct date.
3. In hourly scale, each supported catalog event symbol sits at the midpoint tick for its own hour; the exact source valid time remains unchanged and disclosed where the existing UI discloses it. Each of the six mapped legacy fixture categories uses its listed symbol at its own midpoint.
4. Missing or unsupported catalog events and unmapped future fixture categories remain icon-free, including sparse gaps. DST transitions preserve actual elapsed-time ordering and never add a nonexistent hour.
5. Releasing a daily drag selects local noon on its target date on both timeline surfaces; active-catalog selection continues to use the existing full-window interval clamp. From the debug catalog picker, a SIMULATED selection request beyond the last window bound is clamped to `endExclusive - 1 nanosecond`. This demonstrates the actual 44-day endpoint behavior; it is not evidence of a partial-day support clamp. A daily single tap remains a no-op.
6. The debug-only SG.4 gap replay shows a supported event at 10:00 followed by the absent 11:00 fact: the 10:30 symbol is present and the 11:30 midpoint tick has no symbol. Do not infer or carry an event into the missing hour.
7. P.2.22 daily double-tap entry remains at 07:00, hourly-to-daily scale-only toggle preserves the exact instant, hourly scrub remains unchanged, and gesture ownership and readability remain intact.

**Approval:** The product owner approved the revised behavior contract on 2026-11-09, including the six explicit fixture/debug mappings, the local-noon daily-drag release correction, and verification of the existing full 44-day interval endpoint clamp. The paired UI artwork board is unchanged and remains approved as written on 2026-11-09.
