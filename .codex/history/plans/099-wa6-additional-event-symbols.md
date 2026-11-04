# Cycle plan — 099-wa6-additional-event-symbols

State: COMPLETE — archived after WA.6 closeout.
Plan readiness: completed; the approved shared behavior and UI artwork boards governed execution, and the scoped installed exit was met.
Roadmap: WA.6 — Additional Events; WA.5 was DONE in cycle 098 and WA.6 was the sole NEXT slice at activation.
Objective: Implement the eight Additional Events symbols through the shared static catalog and debug preview, with scoped installed evidence.

## Slice-board gate

- **Behavior board:** `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md`, product-owner approved on 2026-11-03 as the shared 48-ID mapping contract and six-row sequence. Its sixth row defines Dry thunderstorm, Tropical storm, Waterspout, Frost, Extreme heat, Extreme cold, Dust/sandstorm, and Volcanic ash. Its distinct-ID/label rule, explicit mapping, provider-independent graphics boundary, and separation from official alerts govern WA.6 scope and acceptance.
- **UI artwork board:** `Oxygen_forecast_weather_symbol_board.png`, product-owner approved on 2026-11-03 for all six rows. Its sixth row governs the cloud/lightning without precipitation and dry-ground cue; tropical-storm swirl; funnel over water; frost branches; sun and heat waves; snowflakes; dust/sand wind bands; and volcanic ash plume. Compare the row at the existing 24 dp and 48 dp preview sizes.
- **Approval constraints:** Implement only row six. Preserve all eight semantic IDs and precise labels even if drawing primitives are shared. The two tropical entries remain distinct events through their IDs, labels, and approved artwork direction; no unsupported wind measurement or severity is implied. Catalog coverage does not imply provider coverage or authorize invented forecast events. Do not add thresholds, provider ingestion/normalization, official-alert interpretation, or animation. Both boards, approvals, and their relationship to WA.6 are recorded in `docs/ROADMAP.md`. The board gate is met.

## Verified baseline and production boundary

- WA.5 completed in cycle 098. The existing static catalog is `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt`; it contains the WA.1–WA.5 rows (40 IDs) and exposes the shared caller-sized renderer. The established debug preview pattern is in `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugStormFallbackSymbolRangeFixture.kt` and `AuthoredCatalogLibrary.kt`.
- Extend only the symbol catalog with the eight WA.6 IDs, labels, explicit definitions, and static scalable drawings through the existing API. Add a debug-only eight-range fixture, expected at `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugAdditionalEventsSymbolRangeFixture.kt`, and its directly affected load action in `AuthoredCatalogLibrary.kt`. Reuse existing selector, persistence, save/reopen, and preview paths. Change serialization only if implementation evidence shows it is required.
- Do not change provider adapters, canonical forecast facts/schema, weather normalization, thresholds, selected time, navigation, scene composition, timeline behavior, or production data. Debug symbol selection remains preview metadata and must not materialize new canonical condition facts.
- Automated test sources are outside this plan's authorization. Do not add or run tests. Focused test changes or runs require separate owner authorization and a plan update. Evidence target: `.codex/test-artifacts/099-wa6-additional-event-symbols/`; closeout target: `.codex/history/099-wa6-additional-event-symbols.md`; archived plan target: `.codex/history/plans/099-wa6-additional-event-symbols.md`.

## Functional and visual invariants

- Add exactly eight distinct stable IDs and precise labels: Dry thunderstorm, Tropical storm, Waterspout, Frost, Extreme heat, Extreme cold, Dust/sandstorm, and Volcanic ash. Each resolves to an explicit catalog definition; no ID falls back to clear weather or another event.
- Follow the approved sixth-row artwork. Dry thunderstorm has lightning with no rain marks and retains its dry cue; waterspout remains a funnel over water; frost uses branching ice; extreme heat uses a sun with heat waves; extreme cold uses snowflakes; dust/sandstorm uses blowing particulate bands; volcanic ash shows an eruption plume. Tropical storm remains distinguishable from the existing Tropical cyclone through its semantic ID, selected label, and the intended row artwork. At 24 dp, adjacent text carries precise meaning where fine detail is not legible.
- Keep all graphics static, scalable, and inside caller-provided bounds. Preserve prior catalog IDs and meanings. Symbols do not assert measurements, thresholds, provider support, hazard severity, warning status, issuer, or instructions. Supplied forecast meaning remains available as text.
- Debug preview uses eight adjacent, non-overlapping, one-hour SIMULATED ranges in board order. Saving and reopening retains each symbol ID without adding canonical weather condition facts.
- Preserve selected time, timeline responsiveness, gesture ownership, navigation, source bounds, and readable forecast facts; WA.6 does not modify those paths.

## Ordered work

- [x] Reinspect the approved sixth artwork row, current catalog/drawing helpers, WA.5 debug fixture/editor load-save-reopen path, and WA.1–WA.5 cycle evidence. Mapped all eight labels and intended visual distinctions before editing.
- [x] Add only the eight WA.6 IDs, catalog definitions, and scalable static drawings through the existing renderer. Preserved prior rows and kept event identities distinct through artwork plus labels.
- [x] Add the debug-only Additional Events fixture and editor-load action. It uses eight adjacent one-hour SIMULATED ranges in board order with no canonical weather values and reuses existing persistence and preview behavior.
- [x] Build/install the debug app on Pixel 3 XL; loaded the fixture, saved and reopened the authored simulation, and inspected each symbol at 24 dp and 48 dp.
- [x] Capture one installed screenshot per symbol and an additional reopened empty-values state. Recorded device/build/viewport/font scale, commands, save/reopen observations, artwork distinctions, canonical-fact isolation, scoped PASS/FAIL/NOT RUN results, and limitations in the evidence README.
- [x] Inspect `git diff --check` and the final diff. WA.6 exit evidence is met; the roadmap is updated to DONE, the plan is archived, and cycle 099 is closed with actual results.

## Acceptance and authorized verification

- **PASS:** All eight IDs resolve to explicit catalog definitions and scalable artwork through the existing API. Each installed state shows the selected event label and intended sixth-row graphic at 24 dp and 48 dp; event identities remain distinguishable, and prior mappings remain available. The debug fixture persisted all eight selections without creating canonical weather facts.
- Installed scope: **PASS** on Pixel 3 XL portrait, serial `8AKY0M32X`, 1440×2960 px at 560 dpi, font scale 1.0. Inspected the directly affected fixture load, selection, save/reopen, labels, previews, and canonical-fact isolation. Reduced motion is NOT APPLICABLE to these static drawings. RTL, TalkBack, alternate font/device, and broad system/environment matrices were NOT RUN; they remain for dedicated TEST work.
- Preserve eight actual captures under `.codex/test-artifacts/099-wa6-additional-event-symbols/`: `dry-thunderstorm.png`, `tropical-storm.png`, `waterspout.png`, `frost.png`, `extreme-heat.png`, `extreme-cold.png`, `dust-sandstorm.png`, and `volcanic-ash.png`; plus `reopened-empty-values.png`. Device observations and command results are in `README.md`.
- Planned commands: `python scripts/dev.py workflow`; `adb devices -l`; `python scripts/dev.py run --serial <verified-device>`; one `python scripts/dev.py screenshot --serial <verified-device> --output .codex/test-artifacts/099-wa6-additional-event-symbols/<symbol>.png` per ID; and `git diff --check`. Run `python scripts/dev.py build` separately only if installation does not establish build success. Record actual commands and results.
- Automated tests are **NOT AUTHORIZED by this planning request** and are not an acceptance step in this plan as drafted. Do not add or run tests. WA.3/WA.5 records identify an existing exact-16-ID catalog assertion as stale; if the owner separately authorizes focused testing, update this plan first, inspect and correct the assertion to the current catalog contract, then scope execution to the directly affected catalog and debug authored-event checks only. Do not run the broad suite.
- A build or preview alone is not visual evidence. Do not claim installed/device acceptance without the screenshots and scoped interaction observations above.

## Risks and assumptions

- The raster concept board is visual direction, not production-ready vector source. The static Canvas renderer is the established catalog path; fine dry-ground, heat-wave, frost, and ash details may need simplification at 24 dp while labels retain precise meanings.
- Tropical storm and tropical cyclone are related concepts and have similar spiral artwork on the board. Preserve separate IDs and labels; do not invent unsupported intensity thresholds to differentiate them.
- WA.5 source and closeout files are present as uncommitted working-tree changes at planning time. Preserve those changes; inspect the aggregate diff before editing, and keep WA.6 changes limited to its scoped catalog and debug-preview paths.
- Existing test maintenance is not authorized by this plan. The stale catalog-size assertion must be corrected before any future separately authorized focused run that depends on it.

## Out of scope

Provider integration/normalization; event thresholds or intensity inference; canonical weather facts/schema; official alerts, warning levels, or instructions; animation; changes to WA.1–WA.5 meanings; scene composition or main-screen/timeline redesign; replacing readable labels with artwork; app-wide accessibility, device, localization, performance, or system acceptance; automated test source changes or execution without separate owner authorization.
