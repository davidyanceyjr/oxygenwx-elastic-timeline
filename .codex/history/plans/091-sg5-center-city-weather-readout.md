# Cycle plan — 091-sg5-center-city-weather-readout

State: DONE
Plan readiness: Final; product owner approved the revised behavior board and UI artwork board on 2026-11-02; scoped installed acceptance and closeout evidence recorded on 2026-11-02.
Cycle ID: 091-sg5-center-city-weather-readout
Roadmap: SG.5
Depends on: SG.1, SG.2, SG.3, SG.9 (all DONE)

## Authority and approved board package

Follow `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, and `.codex/README.md`. The current installed screen remains the visual baseline; the reference cap is not installed acceptance evidence.

- **Behavior board:** `.codex/concepts/SG.5-location-readout-behavior-board.md` — product owner approved as written on 2026-11-02; the prior version's city-as-button interaction is superseded. The approved board governs the separate lower-row location action, noninteractive loaded-city label, selected-time and data invariants, weekday emphasis, and gesture ownership. Approval constraints: no settings behavior, weather semantics/assets, unit policy, or broad system acceptance; remove only City, Place, and Refresh from the top; preserve the stated location-menu, selected-time, timeline, and forecast-value behavior.
- **UI artwork board:** `.codex/concepts/SG.5-location-readout-ui-artwork-board.svg` — product owner approved 2026-11-02 after revision. It compares the current-screen cap with the target hierarchy. The illustrated scene is schematic, not new scene artwork.
- **Reference cap:** `.codex/concepts/SG.4-device-current.png` — current installed Florence/cloudy screen, used only as a before/reference image.
- **Approved artwork constraints:** Keep Chicago as a central noninteractive label with temperature and atmosphere; the magnifier beside precipitation opens the existing location menu. Remove top City, Place, and Refresh controls and leave the upper scene clear. Place the gear beside the wind indicator as the settings entry. Slightly emphasize the weekday in the selected date/time line. Space the precipitation indicator, magnifier, gear, and wind indicator evenly in a matched style; set each to 33% transparency (67% opacity). Preserve current temperature presentation without an F suffix or a new US/imperial/metric policy. Keep date/time and high/low as supporting facts, preserve atmosphere and timeline, and keep text separation subtle.
- **Settings scope resolved by owner:** SG.5 adds the gear affordance only; it does not implement a settings menu or claim the gear opens a functional destination. Settings content and behavior belong to a later bounded slice. Do not invent settings content.

The approved artwork board governs the revised visual hierarchy, and the approved behavior board governs the location action, selected-city semantics, selected-time invariants, and gesture ownership. Together they define the scope and acceptance for this plan. The owner resolved settings scope for SG.5 as a gear affordance only, with settings-menu behavior deferred to a later bounded slice. These boards do not authorize new weather categories or assets, unit-system work, search behavior changes, or scene-lighting effects. Installed evidence is still required to establish the exit.

## Objective and boundary

Reorganize the selected-weather readout so the loaded city label, selected temperature, and atmosphere are immediately legible together, exemplified by “Chicago · 33° · Windy.” Place the location-menu action as a magnifier beside the precipitation indicator, separate from the central gesture surface. Add a settings gear beside the wind indicator as an affordance only; settings behavior and menu contents are deferred to a later bounded slice. Remove top City, Place, and Refresh controls to leave the upper scene clear. Slightly emphasize the weekday in the selected date/time line; evenly space and visually match the precipitation, location, settings, and wind icons at 33% transparency. Selected date/time and high/low remain supporting facts. Add only very subtle text separation.

Expected production-code scope is `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`; inspect `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` to confirm the existing `onChooseLocation` callback. Changes to MainActivity are limited to wiring the magnifier to that callback. Do not change data, forecast projection, weather mapping, location search behavior, catalog activation policy, or the deprecated oxygenWX app.

## Functional and visual invariants

- The loaded city comes from the existing selected-location presentation, is shown as a noninteractive label, and is not inferred from the selected forecast time.
- The lower-row magnifier opens the existing location/search menu. Opening and dismissing that flow does not itself change the selected forecast instant. The magnifier is outside the central main-view gesture surface.
- The gear is a settings affordance only in SG.5; it has no implemented action or menu destination. Settings behavior and contents are deferred to a later bounded slice.
- Selecting or displaying temperature, atmosphere, high/low, date, and time continues to use the existing typed presentation values and unavailable behavior. Do not add a unit suffix or define a unit system.
- Remove the top city, Place, and Refresh controls and keep the upper scene clear. Preserve source/freshness or dataset information that is not part of those three controls unless the owner separately directs otherwise.
- The intended central scan order is city, temperature, atmosphere. Date/time and high/low are secondary but readable.
- The weekday is slightly larger than the remaining date/time text to help identify timeline position. The precipitation indicator, magnifier, gear, and wind indicator are evenly spaced, use a matched outline style, and each use 33% transparency.
- Text separation remains slight: no opaque panel/card, heavy glow, or scene-owned lighting effect.
- Preserve selected-time authority, main-view and timeline gesture ownership, timeline responsiveness, forecast values, scene composition, and readable timeline labels.
- Do not add category artwork, a weather icon catalog, location persistence/search changes, temperature unit policy, or broad accessibility/system acceptance.

## Ordered work

1. **Execution preflight.** Confirm this remains the SG.5 plan and dependencies remain DONE; run `python3 scripts/dev.py workflow`. Inspect the current working tree and relevant screen code. Verify connected device and viewport details at execution; current planning preflight found Pixel 3 XL serial `8AKY0M32X` connected.
2. **Capture a current installed baseline.** Install/run the current debug build on the reference Pixel 3 XL, capture the existing screen, and record device/content viewport, density, font scale, locale, selected location, selected instant, scene state, and upper-context controls. The approved reference cap is not a substitute for this current capture.
3. **Implement the approved hierarchy.** Remove the top City, Place, and Refresh controls. Keep the city as a central label; arrange city, temperature, and atmosphere as the quick-read group, then weekday/date/time and high/low as supporting facts. Apply the approved subtle text separation. Place matched, evenly spaced precipitation, magnifier, gear, and wind indicators in the lower row at 33% transparency. Render the gear as an affordance without a menu or destination.
4. **Check direct interaction and values.** Confirm the magnifier opens the existing location/search flow and opening/dismissing it leaves selected time unchanged. Confirm the gear is presented as an affordance without claiming or invoking a settings destination. Confirm displayed location, selected forecast facts, weekday, missing-value treatment, and current temperature formatting match supplied presentation. Check timeline and main-area navigation directly affected by layout.
5. **Installed visual review.** On Pixel 3 XL portrait and a compact viewport, inspect the approved hierarchy over representative available day/night and weather-scene backgrounds. Include the default and 1.3 font-scale states if available/applicable. Record locale/direction and reduced-motion settings, assess whether either changes this static readout's layout or meaning, and run only a directly relevant scoped check if indicated; do not claim broad RTL or system acceptance. Review only changed text placement, contrast, and directly affected interaction/readability. Capture installed before/after states and record any unavailable state as NOT RUN.
6. **Closeout.** Run the authorized workflow/build checks, inspect the complete diff and `git diff --check`, preserve actual screenshots and command results under the cycle evidence directory, and record limitations. Mark SG.5 DONE only if every roadmap exit condition has evidence.

## Acceptance criteria

1. In the installed screen, a quick scan identifies the loaded city label, selected temperature, and atmosphere as one prominent central reading.
2. The central city label is not a button. The lower-row magnifier opens the existing location/search menu; opening and dismissing it alone leaves selected time unchanged.
3. Selected date/time and high/low remain legible and tied to the same forecast reading; all displayed values and unavailable states match existing typed presentation inputs.
4. No F suffix or new temperature-unit policy is introduced. Top City, Place, and Refresh controls are removed; other source/freshness or dataset information remains visible unless separately directed.
5. The weekday has slight emphasis; the four lower indicators/icons are evenly spaced, stylistically matched, and 33% transparent. The text-separation treatment remains subtle and does not overpower atmosphere or timeline.
6. The gear is a settings affordance only; SG.5 implements no settings menu or destination.
7. Pixel 3 XL and compact installed captures show the intended hierarchy and preserved facts. Direct checks confirm location-menu behavior, selected-time accuracy, timeline responsiveness, gesture ownership, and readability in affected states.
8. Evidence records exact commands and PASS/FAIL/NOT RUN for scoped checks; no app-wide RTL, TalkBack, or environment acceptance is claimed.

## Verification and evidence

- Planned compile/install command when the plan is activated: `python3 scripts/dev.py run --serial 8AKY0M32X` (reconfirm serial before use). This builds and installs the debug app; it is not visual acceptance by itself.
- Planned installed captures: `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/091-sg5-center-city-weather-readout/<state>.png` (reconfirm serial and exact output state names at execution).
- Planned closeout commands: `python3 scripts/dev.py workflow`, `python3 scripts/dev.py build` if needed beyond `run`, and `git diff --check`.
- Automated tests are not included; project instructions prohibit adding or running tests unless the owner asks. No tests, builds, or app installations were run before plan activation.
- Evidence directory: `.codex/test-artifacts/091-sg5-center-city-weather-readout/`.
- Closeout history target: `.codex/history/091-sg5-center-city-weather-readout.md`.
- Archived plan target: `.codex/history/plans/091-sg5-center-city-weather-readout.md`.

## Execution outcome — 2026-11-02

The installed Pixel 3 XL screen passed the approved central hierarchy, location-menu, selected-time preservation, main-area swipe, timeline day selection, and readable-facts checks. The compact viewport was exercised as the approved reversible 360×800 dp same-device simulation, and the 1.3 font-scale capture was readable; the device was restored to 1440×2960 at font scale 1.0. The sampled Chicago dates Nov 2–7 reported cloudy; day/night cloudy states were inspected, while alternate weather conditions and a missing-value fixture remain NOT RUN because they were not observed in the sampled active window. Locale, direction, animation scales, evidence paths, and remaining acceptance limits are recorded in `.codex/test-artifacts/091-sg5-center-city-weather-readout/README.md`. `python scripts/dev.py workflow`, the authorized `python3 scripts/dev.py run --serial 8AKY0M32X`, and `git diff --check` passed. No tests were added or run.

## Risks and assumptions

- The existing screen has a location callback and live source/context controls; execution must preserve those existing flows and data roles while repositioning the city.
- There is no settings menu or callback in the current app. The owner resolved SG.5 to a gear affordance only; settings behavior is deferred to a later bounded slice and must not be claimed in SG.5.
- Current temperature display does not yet establish a selected US/imperial/metric policy. This slice must not imply or settle one.
- Compact viewport resolved by the owner on 2026-11-02: use a reversible 360×800 dp display-size override on the Pixel 3 XL at its 560 dpi density (1260×2800 px). Record this as same-device viewport simulation, not separate compact-device acceptance, and restore the physical display size after capture.
- Execution observations and explicit NOT RUN states are recorded in `.codex/test-artifacts/091-sg5-center-city-weather-readout/README.md`. The compact viewport was resolved as a reversible same-device simulation; sampled active Chicago conditions were cloudy. Missing-value and alternate weather-condition states remain unobserved and are recorded NOT RUN. Locale/direction and animation-scale observations are recorded without claiming broad RTL or system acceptance.
- The approved artwork is a layout board with a schematic background; it does not authorize replacement city artwork or a different atmospheric composition.

## Out of scope

Weather-category artwork or weather-family mappings; foreground/midground light or shadow scene effects; changing city search, persistence, or activation policy; choosing a temperature unit system; data/provider/catalog changes; timeline redesign; deprecated oxygenWX changes; broad RTL/TalkBack/accessibility/device matrices; and app-wide system acceptance. Settings-menu behavior and contents are deferred to a later bounded slice; SG.5 adds only the affordance.
