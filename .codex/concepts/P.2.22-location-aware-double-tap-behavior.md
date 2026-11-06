# P.2.22 — Location-aware timeline double-tap behavior

**Board status:** Owner-approved behavior board; original board approved 2026-11-06, with carousel rule directly approved 2026-11-06.

## Purpose

Let a daily timeline double-tap choose the date at the touched position before opening hourly scale. Returning from hourly scale keeps the exact hour selected there.

## Proposed behavior contract

| Current state | Input | Result |
|---|---|---|
| Daily | One timeline tap | No selection or scale change, preserving P.2.17. |
| Daily | Immediate horizontal drag | Continue daily browsing with the existing release behavior. |
| Daily | Double-tap at a date position | Use the completing tap's position to resolve the local date in the active catalog's timezone; select 7:00 AM local time on that date, then enter hourly scale with it under the playhead. If the resolved date or 7:00 AM target is outside the catalog, carousel to the first in-catalog date's 7:00 AM entry. |
| Hourly | Timeline drag | Continue hourly scrubbing. |
| Hourly | One timeline tap | No selection or scale change. |
| Hourly | Double-tap anywhere on the timeline | Return to daily scale and preserve the exact selected instant; ignore the double-tap coordinate. |
| Either | Main forecast-area gesture or accessibility scale action | Retain its separate existing behavior and preserve selection for scale-only changes. |

For the active catalog, the daily target is 7:00 AM on the touched date in `CatalogTimelineView.zone`. For the deterministic fixture/debug path, use that date's existing 7:00 AM hourly sample. If the touched date or 7:00 AM target is outside the catalog, carousel to the first catalog date's 7:00 AM entry. This bounded entry rule supersedes the general clamp/notice behavior only for P.2.22 daily double-taps. An in-window 7:00 AM remains selectable when its forecast facts are missing, under current missing-data rules.

The board defines an asymmetric toggle: the daily-to-hourly transition uses the completing tap's date and wraps an out-of-catalog target to the first catalog date at 7:00 AM; the hourly-to-daily transition is scale-only. This replaces only P.2.17's daily double-tap rule that ignores the touch coordinate. It does not add single-tap selection or an adjustment notice for the wrapped target.

## Preservation constraints

- One recognized double-tap causes one transition and no delayed single-tap selection.
- Keep the selected instant authoritative and centered through the scale animation.
- Preserve immediate daily dragging, hourly scrubbing, hourly-mode retention after release, main-area behavior, screen-reader scale action, and gesture ownership.
- Use the selected location's IANA timezone for catalog date resolution; do not assume a fixed 24-hour local day.
- Keep forecast facts, missing-value semantics, and daily/hourly presentation meaning tied to the selected instant.
- Add no visible mode row or instructional hint.

## Relationship to acceptance

This behavior board governs completing-tap coordinate-to-date resolution, 7:00 AM local selection on entry, first-entry carousel handling, exact-instant preservation on return, and unaffected gestures. The paired artwork board shows the Monday-to-Wednesday 7:00 AM entry and Wednesday 4 PM return states; the carousel uses the same selected-time cue and timeline artwork without added chrome. Both boards are design inputs, not installed acceptance evidence.

## Source references reviewed for this draft

- `docs/SPECIFICATION.md`, P.2.17 bounded exception and selected-time rules.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, sections 3 and 4.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/InstantTimeAxis.kt`: absolute coordinate mapping and interval clamping; execution adds the unclamped date-mapping helper needed to detect carousel overflow.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt`: location zone, per-day overview instant, and supported interval.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/ForecastSource.kt`: fixture local dates and hourly samples.
- Prior approved P.2.17 and P.2.21 board packages.
