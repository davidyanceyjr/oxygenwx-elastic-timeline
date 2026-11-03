# SG.8 behavior board — lunar events and phases

**Status:** Product owner approved as written on 2026-11-02.
**Scope reference:** SG.8 in `docs/ROADMAP.md`.
**Companion UI artwork board:** `SG.8-lunar-events-and-phases-ui-artwork-board.svg`.

## Product intent

Give the Moon the Sun's event-timed, selected-time treatment while keeping lunar phase as its own daily fact. Provider moonrise/moonset events control the Moon's only asserted horizon crossings. Any path drawn between supported events is illustrative. The selected forecast instant remains the single scene clock. As the Moon approaches a known horizon crossing, enlarge its cue using the current Sun cue's visual scale emphasis; reduce it toward the high middle of the illustrative path.

**Owner-directed visual behavior (2026-11-02):** The Moon should appear much larger near the horizon, like the Sun. Apply the same visual scale emphasis already used by the Sun cue. This is art direction only; it does not claim actual angular diameter, distance, or measured altitude.

## Data and scene flow

```text
Open-Meteo daily response
  moonrise timestamp ─┐
  moonset timestamp ──┼─> map by provider local date + location zone
  moon_phase fraction┘          │
                                v
Canonical daily summary (typed optional fields)
       │                        │
       ├── LIVE catalog         ├── recorded catalog / REPLAY
       │                        │
       └──────────────┬─────────┘
                      v
Selected Instant + selected location zone
                      │
          resolve that instant's local date
             ┌────────┴────────┐
             v                 v
      rise/set event track   daily phase track
      exact event bounds     daily value, no invented
      illustrative path      intraday transition time
             └────────┬────────┘
                      v
Shared scene reading; weather and Sun tracks remain independent
```

## Selected-time behavior

| Input/state | Scene behavior | Claim boundary |
|---|---|---|
| Selected instant moves within a local date with a valid lunar phase | Keep that date's provider phase value attached to the reading; show its named stage and phase cue. | Do not interpolate phase across the day or claim an exact phase-change instant. |
| Selected instant reaches a reported moonrise or moonset | Place the Moon at its horizon crossing at that exact provider event instant. | The event is the only supported horizon crossing at that boundary. Do not shift it for weather, animation, or phase art. |
| Between known rise/set crossings | Use a smooth, theme-owned illustrative path to communicate travel. | The path does not claim azimuth, elevation, orbital geometry, or visible orientation. |
| Supported rise-to-set span with a resolved path position | Make the Moon cue largest near rise/set horizons and smaller near the high middle, following the Sun cue's existing scale profile. | This is a deliberate visual effect, not actual angular diameter, distance, or measured altitude. Do not scale from an unsupported position interval. |
| Event or phase field is absent | Omit only the unsupported cue/label; retain any independent supported lunar field. | Absence creates no guessed event, phase, or certainty about visibility. |
| Selected instant crosses local midnight / DST boundary | Resolve the selected location's actual local date, then use that date's summary and absolute event Instants. | No fixed 24-hour day or UTC-date substitution. |
| Clouds/weather overlap the Moon | Preserve approved scene layer order and weather meaning; a cloud may visually occlude the Moon. | Occlusion does not change lunar event/phase data or imply the Moon is absent. |

### Phase-stage mapping proposal for review

The provider's daily phase fraction is treated as a cycle position in `[0, 1]`, with the end wrapping to the beginning. The proposed eight named stages use equal-width bins centered on the eight conventional stage landmarks: new moon at 0, waxing crescent at 0.125, first quarter at 0.25, waxing gibbous at 0.375, full moon at 0.5, waning gibbous at 0.625, last quarter at 0.75, and waning crescent at 0.875. Each boundary is halfway between adjacent landmarks, including the wrap between waning crescent and new moon. The value is daily, so the selected-time reading keeps that local date's stage throughout that date.

This is a proposed product mapping, not a claim about an exact phase-transition instant. The board asks the owner to approve or revise the bin boundaries before the plan is finalized. Out-of-range or non-finite phase values are invalid input and produce no phase claim; implementation must use the repository's established catalog validation/failure semantics rather than silently clamping them.

## Lunar visibility and event pairing

- Treat moonrise and moonset as separate dated event instants, just as SG.3 treats sunrise and sunset as dated events.
- Draw the cue at each reported crossing. Between crossings, show the cue only for an interval whose preceding and following crossing events establish a rise-to-set span; carry event ordering across local-date boundaries when required.
- If a required neighboring crossing is absent, do not invent a complete above-horizon interval. Keep any independently supported phase cue and show no asserted position/crossing for the unresolved interval.
- A provider's null for one event does not erase the other event. Do not infer a missing counterpart from local clock rules or from the Sun's times.
- At a rise/set exact boundary, the provider event owns the crossing; scene interpolation cannot move it. The selected instant itself never changes.

## Replay and compatibility

- Preserve optional moonrise, moonset, and phase values through canonical catalog snapshots and replay.
- Old recordings without lunar fields remain readable with lunar data unavailable; do not synthesize values during decode or replay.
- LIVE, SIMULATED, and REPLAY retain truthful origin/provenance. This slice adds provider-backed values to the existing shared path and does not make simulated/replay lunar support mandatory.
- Values are location/date scoped. Switching catalog/location resolves the selected instant against the newly active catalog and its zone under existing activation rules.

## Invariants

1. The selected `Instant` is the only scene clock; timeline scale, rendering animation, phase glyph, Moon path, and theme changes cannot retarget it.
2. The Sun continues to use SG.3's provider-event-only horizon crossings and illustrative intervening path. Moon behavior does not redefine solar light phases.
3. Moon rise/set, Moon phase, weather, and Sun are independent semantic tracks evaluated at the same selected instant and catalog revision.
4. Critical phase/event facts remain available as text/accessibility semantics; the decorative Moon is not the data source.
5. Preserve readable forecast facts, timeline responsiveness, and predictable gesture ownership.
6. Do not imply exact phase-transition time, lunar azimuth/elevation, phase orientation, or precise orbital position from these daily values.
7. Within a supported rise-to-set span, use the approved Sun-like horizon emphasis for cue size. Scaling changes the drawing alone and asserts no physical size or altitude measurement.

## Owner review points

**Approval record (2026-11-02):** The product owner approved both SG.8 boards as written. Approval covers the eight equal-width phase-stage bins centered on the listed landmarks; chronological cross-date rise/set pairing, with unknown spans left without a Moon-position claim; the neutral cycle marker and explicit phase labels without hemisphere-specific visible orientation; independent missing phase/event handling; and Sun-like cue scaling that is largest near supported horizon crossings and smallest high on the illustrative path. The scale treatment is art direction and makes no physical angular-size or altitude claim.

## Scope boundary

In scope: request/map/provider daily lunar fields; canonical typed daily data and support metadata; recorded-catalog compatibility; shared selected-time lunar projection; theme rendering; phase/event text semantics; focused installed acceptance for affected states.

Out of scope: lunar azimuth/elevation/orientation, exact phase-transition instants, independent astronomical calculation, other providers, new theme selection, timeline interaction changes, weather meaning changes, and broad app/system acceptance.
