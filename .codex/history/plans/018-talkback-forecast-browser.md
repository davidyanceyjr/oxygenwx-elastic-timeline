# Cycle plan — 018-talkback-forecast-browser

State: ACTIVE
Roadmap: P.2.13
Objective: Build an accessible daily/hourly forecast browser that reads from and updates the timeline’s single selected-time state.

## Production boundary

This slice changes only the experimental app's timeline and screen-reader presentation:

- `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt`
- New `app/src/main/java/com/oxygen/weather/timeline/ui/AccessibleForecastBrowser.kt`
- `.codex/test-artifacts/018-talkback-forecast-browser/`
- `.codex/history/018-talkback-forecast-browser.md`
- `docs/ROADMAP.md` for this slice's status/evidence at closeout
- `.codex/current.md` and the cycle plan only through lifecycle commands

No changes to fixture data, forecast conversion/projection, dependencies, build configuration, tests, production oxygenWX, or unrelated roadmap slices.

## Functional invariants

- `selectedHour` remains the sole timestamp authority; the browser derives rows from existing `ForecastProjection` / `SampleForecast` and selecting a row updates that state.
- Daily entries select the existing representative daily time; hourly entries select their exact fixture hour. Respect fixture bounds and never invent intervals.
- Switching/opening the browser does not change the timestamp. Row selection changes it only to that row's represented time.
- Preserve visual day/hour tape gestures, responsive gesture ownership, the absent visible scale-control row, selected-time cues, readable existing forecast facts, and the DEMO DATA label.
- Do not expose decorative ticks or animation as the source of spoken forecast data. Each browser row must be one understandable TalkBack stop with date/time, scale-appropriate forecast facts, and selected state.
- Provide a standard accessibility click action to open the current-scale browser, plus a clearly named custom action; retain the existing custom action for changing scale.

## Steps

1. Inspect current accessibility semantics, forecast projection, app layout, and Pixel 3 XL baseline; record baseline device/app settings.
2. Implement a scrollable daily/hourly forecast browser dialog with selectable semantic rows, current selection indication, DEMO DATA provenance, and close action. Connect both standard accessibility activation and a named custom action to opening it.
3. Build, install, and launch on Pixel 3 XL. With TalkBack off, use controlled ADB interaction to open the browser and select daily/hourly rows; verify visible selection/date/time changes. Repeat with TalkBack enabled using injected taps where possible. Capture daily and hourly list states and inspect the accessibility node dump.
4. Review default portrait, compact landscape, and 1.3 font scale. Record RTL/reduced-motion applicability; do not alter timeline motion or directional gestures. Compact landscape and 1.3 font scale were captured on the main screen; changing orientation dismissed the dialog, so no claim is made for browser layout in those variants.
5. Restore original device settings; inspect final diff and `git diff --check`; close the cycle with actual results and limitations. Leave P.2.7 BLOCKED because its manual speech/action acceptance remains separate.

## Acceptance/evidence

P.2.13 may be DONE when the installed build opens the appropriate day or hour list from the focused timeline; rows expose meaningful date/time, conditions, temperature, precipitation chance, wind, and selected state; selecting rows synchronizes the existing summary and tape; and evidence records the actual installed states without a visible main-screen mode row.

Target device: Pixel 3 XL (`crosshatch`), serial `8AKY0M32X`, Android 12/API 31, portrait baseline, `1260x2240` at `560 dpi`. Capture:

- `daily-browser.png` and `hourly-browser.png`
- `daily-row-selected.png` and `hourly-row-selected.png`
- `compact-landscape-main.png` and `large-font-main.png` (font scale 1.3; browser variants not captured)
- `accessibility-tree.xml` and artifact README with exact ADB/build/install commands, device settings, selection outcomes, and limitations

Run only `python scripts/dev.py workflow`, the plan-authorized `python scripts/dev.py run --serial 8AKY0M32X`, screenshots, read-only accessibility/UI hierarchy inspection, and `git diff --check`. Do not add or run tests, lint, or contract checks in this slice. RTL and reduced-motion are recorded as unchanged/not reviewed. Do not claim exact spoken wording or full manual TalkBack acceptance without attentive human audio observation.

## Risks / assumptions

- Compose custom-action availability is not established by the earlier `uiautomator` dump; provide a standard semantic click route as well as a custom action.
- TalkBack is restored to its original disabled state after each device session. ADB interaction can establish visible state changes and semantics presence, but does not prove what was spoken.
- Daily selection uses the existing day overview projection, whose representative forecast values are fixture-derived; hourly rows use exact hourly fixture positions. Keep that distinction honest in the row labels.
- A dialog with 192 hourly rows must remain scrollable and avoid placing all rows in one unbounded non-virtualized layout.

## Execution results

- `python scripts/dev.py workflow` passed before build and after implementation; `python scripts/dev.py run --serial 8AKY0M32X` built successfully, installed, and launched the updated app.
- The owner confirmed TalkBack double-tap activation and confirmed the final selected Sunday forecast. The final installed accessibility hierarchy shows `Hourly forecast`, a checked `SUN, OCT 25, 12 PM` radio row, and subsequent Sunday hourly rows. The corresponding installed screenshot shows Sunday noon at the top of the list.
- The installed portrait summary/timeline after selection and main-screen compact-landscape / 1.3 font scale screenshots are saved. The browser dialog closed on orientation change, so browser rendering at compact landscape and large font is not claimed. RTL and reduced-motion were not reviewed.
- `git diff --check` passed. No tests, lint, or contract checks were run, as required by this plan. TalkBack was restored to disabled, its enabled-service setting unset, font scale 1.0, and portrait rotation 0.
- P.2.7 remains BLOCKED as a separate manual acceptance item; this cycle does not claim the custom daily/hourly scale action was tested.

## Out of scope

- P.2.7's owner-reported exact speech, focus-order, custom-action discovery, and two-direction manual TalkBack acceptance.
- Reworking forecast fixture or projection semantics, adding a visible scale-control row, or changing the visual timeline.
- RTL/reduced-motion implementation, other screen readers/devices, and production integration.
