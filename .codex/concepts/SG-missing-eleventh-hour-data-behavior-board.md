# SG-missing-eleventh-hour-data — draft behavior board

Status: PROPOSED FOR OWNER REVIEW. This board is not approved and does not authorize implementation.

## Observed input and question

On the Florence LIVE catalog, the Sunday 2026-11-08 23:00 source row supplied temperature 7.8 °C, precipitation probability 0%, wind 6.3 km/h, and direction 336°. The installed view at 23:36–23:49 displayed unavailable temperature, precipitation, and wind. The current SG.1 rule ends the final 23:00 point's representative scene cell at 23:30. The presenter also gates the text fact on condition-cell availability. The intended late-day display rule requires owner approval because extending the point cell changes the controlling SG.1 contract.

## Proposed boundary contract

The final **supported** hourly point on a local date represents its own field for display through that date's exclusive midnight boundary. This is a display association, not a new observation or a claim that weather held continuously. Keep the selected time exact and disclose the point's original valid time. At midnight, select only the new date's support. Do not carry a point across local dates.

| Selected local time | With supported 23:00 field point | Display source | Condition/scene rule |
| --- | --- | --- | --- |
| 23:00–23:29 | Existing representative cell | 23:00 | Current supported condition meaning |
| 23:30 exact | Proposed final-cell continuation begins | 23:00, disclosed | Same date's supported condition; missing stays unknown |
| 23:36 and 23:59 | Proposed continuation through end of date | 23:00, disclosed | Same rule; no new minute-level weather fact |
| Next local 00:00 | New date owns selection | New date's point if supplied | No previous-date carry; missing is unavailable |

## Field independence

```text
selected Instant + location zone
  → local date and exact selected-time label
  → each field's expected support and supplied final-hour value
       → supported: display value with original source time
       → absent / empty / unsupported: unavailable for that field
  → condition point controls condition scene only
  → precipitation chance, temperature, and wind do not disappear merely
    because the condition point is missing
```

- A supplied numeric zero remains zero. Missing probability, temperature, wind, or condition remains unavailable independently; missing condition does not become clear or dry.
- A missing final expected point is not filled from 22:00 or the next date. Isolated or sparse points retain the existing no-span rule unless the approved board explicitly changes it.
- Accumulation intervals keep their own half-open source bounds. The proposed point-cell rule does not extend an amount or rate interval.
- Local midnight comes from the IANA zone, including 23-hour, 25-hour, and repeated-hour dates. The last supported slot is identified in absolute-time order within that date.
- SG.4's disclosed carry for eligible **interior** absent condition slots is unchanged. No trailing or cross-date carry is added.
- Daily high/low, selected instant, playhead, scale toggle, daily drag, and hourly scrub keep their existing meanings.

## Review decision

Approve or revise the proposed same-date continuation and source-time disclosure. The approved decision must be recorded in the cycle plan and reconciled with `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` before implementation. The companion UI artwork board shows the supplied late-hour, independently missing, and midnight states. Installed captures and focused interactions remain required after implementation.
