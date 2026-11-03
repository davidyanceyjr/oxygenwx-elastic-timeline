# SG.5 behavior board — Location and selected forecast readout

**Status:** Product-owner approved as written on 2026-11-02. The prior behavior board was approved for a city-name button; that interaction is superseded.

**Reference cap:** `.codex/concepts/SG.4-device-current.png` (current installed screen). It depicts a selected forecast at Florence. The approved target removes the top city, Place, and Refresh controls to leave the upper scene clear. Other source/freshness or dataset controls are not depicted by this refactor and must not be removed unless separately authorized.

## Purpose

Make the loaded location and selected forecast immediately legible over the existing atmospheric scene. The central reading should quickly communicate city, temperature, and atmosphere (for example, “Chicago · 33° · Windy”). Keep the city as a label; a magnifying-glass control beside precipitation opens the existing location/search menu. A gear beside wind represents settings.

## Information map

```text
Central selected-forecast readout
    ├── loaded city/location (prominent label; first in the scan; not a button)
    ├── selected temperature (most prominent numeric fact)
    ├── selected atmosphere label (prominent; e.g. Cloudy, Clear, Windy)
    ├── selected weekday (slightly emphasized to aid timeline scrubbing)
    ├── selected local date
    ├── selected local time
    └── daily high / low

Lower indicator/action row, evenly spaced and visually matched:
    ├── precipitation indicator and value
    ├── magnifier button → existing location/search menu
    ├── gear → settings affordance only; no action or menu in SG.5
    └── wind indicator and value
All four indicators/icons use 33% transparency (67% opacity).
The top city, Place, and Refresh controls are removed, leaving the top scene clear.
Existing scene remains behind the readout.
Existing lower forecast facts and timeline remain in their current roles.
```

## State and interaction contract

1. The city/location name is part of the central selected-weather reading, visually prominent and encountered before or alongside temperature and atmosphere on a quick scan. It names the currently loaded/selected location, not a location inferred from the selected forecast time.
2. The city name is a noninteractive label. The magnifying glass in the lower row is the location/search menu control and must have a clear touch target.
3. Opening and dismissing the location menu alone does not change the selected forecast instant. Location changes follow existing location-selection behavior and selected-time policy; this slice does not redefine that policy.
4. The gear denotes settings but is an affordance only in SG.5. It has no implemented action or menu destination; settings behavior and contents are deferred to a later bounded slice.
5. Date and time continue to describe the authoritative selected instant in the loaded location's timezone. The weekday is slightly larger than adjacent date/time text to support timeline scrubbing. Layout changes do not retarget the selected instant.
6. Temperature, atmosphere label, and high/low continue to show existing typed presentation values. Missing values retain existing unavailable representation; add no category mapping or fallback. Do not define US, imperial, or metric unit systems or add a temperature-unit suffix.
7. The city, temperature, and atmosphere label receive the strongest visual emphasis as one compact central group. Date/time and high/low remain clear supporting facts.
8. Precipitation indicator, magnifier, gear, and wind indicator are evenly spaced across the lower row, use a matched outline style, and each use 33% transparency (67% opacity).
9. Remove the top city, Place, and Refresh controls. Keep the upper scene clear; do not remove any other controls unless separately authorized.
10. A very subtle shadow or equivalent text-separation treatment may be applied to the readout. It must remain understated and must not become a card, opaque panel, halo, or decorative scene effect.
11. Existing timeline gestures and main-scene navigation remain intact, including predictable gesture ownership and selected-time authority. The city label has no button gesture; the location action is separated into the lower row.

## Scope boundaries

- In scope: first-theme screen hierarchy, lower-row location-menu entry, grouping/emphasis of selected forecast facts, subtly emphasized weekday, clear upper scene, matched lower indicators/icons at 33% transparency, subtle text separation, scoped installed visual and interaction review.
- Out of scope: weather-category inventory or paired illustration/icon assets; new weather semantics; temperature unit-system policy; foreground or midground lighting/shadow scene effects; location persistence/search changes; timeline redesign; broad accessibility/system acceptance; settings-menu behavior and contents, which are deferred to a later bounded slice.

## Acceptance questions for the bounded plan

- Does the central group quickly read as city, temperature, and atmosphere, with the city remaining a noninteractive label?
- Does the magnifier open the existing location/search flow without changing selected time merely by opening or dismissing it?
- Is the gear clearly presented as a settings affordance without implying an implemented menu or destination?
- Are the four lower indicators/icons evenly spaced, stylistically matched, and 33% transparent?
- Is the weekday slightly emphasized and readable while scrubbing?
- Can a person identify date/time, temperature, atmosphere, and high/low as one selected forecast reading?
- Are city, temperature, and atmosphere prominent without overwhelming the scene or timeline?
- Is the added separation visibly subtle across the affected day/night/weather scenes?
- Do selected time, supplied values, timeline responsiveness, and gesture ownership remain unchanged?

## Approval record

- Repurposing and slice rename: owner approved in conversation on 2026-11-02.
- Target hierarchy and subtle text separation: owner-directed in conversation on 2026-11-02; revised to center the city as part of the weather reading.
- SG.5 settings scope: owner agreed in conversation on 2026-11-02 that the gear is an affordance only and settings-menu contents belong to a later bounded slice.
- Prior behavior board and artwork: owner approved 2026-11-02 for city-as-button; that interaction is superseded by the latest direction.
- Revised UI artwork board: owner approved in conversation on 2026-11-02. It shows city as a label; magnifier as location-menu entry; gear as settings entry; no top city/Place/Refresh controls; modest weekday emphasis; four evenly spaced, matched lower indicators/icons at 33% transparency; subtle text separation.
- Revised behavior board: product owner approved as written in conversation on 2026-11-02. Approval constraints: keep the city as a noninteractive loaded-location label; route location-menu entry through the lower magnifier without changing selected time merely by opening or dismissing the menu; render the gear as an affordance only; preserve typed forecast facts, selected-time authority, timeline/main-scene gesture ownership, and existing location behavior; remove only the top City, Place, and Refresh controls; preserve all stated layout and opacity constraints; do not add settings behavior, weather semantics/assets, unit policy, or broad system acceptance.
