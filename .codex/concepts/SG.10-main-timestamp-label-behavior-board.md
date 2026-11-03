# SG.10 — Main timestamp label behavior board

**Status:** Product owner approved as written on 2026-11-02.
**Cycle:** `092-sg10-remove-selected-time-prefix`
**Roadmap item:** `SG.10`

## Owner-selected scope

The owner selected option 1 on 2026-11-02: remove the literal `Selected` prefix only from the main forecast readout's date/time line. Keep the `Selected location` fallback and the forecast-browser `Selected …` copy unchanged.

## Display contract

```text
Selected forecast instant + location time zone
                 │
                 ├── weekday and date, formatted from that instant
                 └── existing selectedTimeLabel (clock time and zone)
                             │
                             ▼
                   WEEKDAY DATE · TIME ZONE
```

Example, before → proposed:

```text
MON Nov 2 · Selected 6:25:31 PM CST
MON Nov 2 · 6:25:31 PM CST
```

## Invariants and boundaries

- The main line continues to be composed from `view.selectedInstant`, `view.zone`, and the existing `view.selectedTimeLabel`.
- Timeline selection still determines the selected instant; this copy edit does not change selection, projection, or forecast values.
- Only the visible main readout line loses `Selected`. The location fallback and forecast-browser labels retain their current text.
- No button, gesture target, accessibility action, unit formatting, or other label changes.

## Acceptance

An installed main-screen capture shows the proposed form at the current reading and after a different timeline selection. The weekday, date, time, timezone, and forecast reading continue to correspond to the same selected instant. The main readout contains no `Selected` prefix; the out-of-scope labels remain unchanged.
