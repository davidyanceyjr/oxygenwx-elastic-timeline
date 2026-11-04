# Cycle plan — 098-wa5-storm-fallback-symbols

State: COMPLETE — archived after WA.5 closeout.
Plan readiness: completed; approved behavior and UI artwork boards governed execution and the scoped installed exit was met.
Roadmap: WA.5 — Storms + Fallback; WA.4 is DONE in cycle 097, and WA.6 remains PLANNED.
Objective: Implement the eight static Storms + Fallback symbols through the shared catalog and debug preview, with scoped installed evidence.

## Slice-board gate

- **Behavior board:** `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md`, product-owner approved on 2026-11-03 as the shared 48-ID mapping contract and six-row sequence. Its fifth row defines Thunderstorm, Thunderstorm with rain, Thunderstorm with hail, Thunderstorm with snow, Squall, Tropical cyclone, Tornado/funnel, and Condition unavailable. Its explicit ID-to-art mapping, distinct-label rule, unavailable-vs-clear boundary, provider-independent graphics boundary, and separation from official alerts govern WA.5 scope and acceptance.
- **UI artwork board:** `Oxygen_forecast_weather_symbol_board.png`, product-owner approved on 2026-11-03 for all six rows. Its fifth row governs the cloud/lightning/precipitation variants, squall strokes, cyclone spiral, tornado/funnel, and unavailable question-mark appearance, including installed review at the existing 24 dp and 48 dp sizes.
- **Approval constraints:** Implement only row five; keep all eight IDs and labels distinct even where drawing primitives are shared; catalog availability does not imply provider coverage or authorize invented events; hazard artwork does not communicate official warning level or instructions; no thresholds, provider ingestion, or animation. Both approved boards and WA.5 scope are recorded in `docs/ROADMAP.md`. The board gate is met.

## Production boundary

- Extend `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt` with only the eight WA.5 IDs, labels, explicit definitions, and static caller-sized drawings. Preserve the existing rendering API and WA.1–WA.4 meanings.
- Add `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugStormFallbackSymbolRangeFixture.kt` with eight adjacent one-hour SIMULATED events in board order, and add the directly affected WA.5 load action to `app/src/debug/java/com/oxygen/weather/timeline/data/debug/AuthoredCatalogLibrary.kt`. Reuse existing symbol selection, persistence, save/reopen, and preview paths. Change debug serialization only if implementation evidence shows it is required.
- Do not modify automated test sources under the current authorization. If the product owner separately authorizes focused test changes/runs, first update this plan's scope and directly affected checks in `app/src/test/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbolCatalogTest.kt` and `app/src/test/java/com/oxygen/weather/timeline/data/debug/DebugAuthoredCatalogTest.kt` only.
- Evidence target: `.codex/test-artifacts/098-wa5-storm-fallback-symbols/`. Closeout target: `.codex/history/098-wa5-storm-fallback-symbols.md`; archived plan target: `.codex/history/plans/098-wa5-storm-fallback-symbols.md`.

## Functional and visual invariants

- Add distinct stable IDs and precise labels for Thunderstorm, Thunderstorm with rain, Thunderstorm with hail, Thunderstorm with snow, Squall, Tropical cyclone, Tornado/funnel, and Condition unavailable. Each ID resolves explicitly; no WA.5 ID silently resolves to clear or to another event.
- Follow the approved fifth-row artwork. Thunderstorm variants retain lightning plus their distinct rain, hail, or snow marks; squall, tropical cyclone, and tornado/funnel remain distinguishable; unavailable remains visibly separate from sunny/clear. At compact size, adjacent text retains precise meaning where fine detail is not reliably legible.
- Keep provider DTOs/codes, event normalization, weather thresholds, forecast timing, canonical facts, and official alert interpretation outside the graphics catalog. These symbols do not assert provider support, hazard severity, warning status, or instructions.
- Debug symbol selection remains persisted preview metadata. Eight loaded ranges have empty weather values and must not create canonical condition facts. Preserve existing symbol persistence and earlier rows.
- Preserve selected time, timeline responsiveness, gesture ownership, navigation, source bounds, and readable forecast facts. Drawings are static, caller-sized, and contain no forecast or screen coordinates.

## Ordered work

- [x] Reinspect the approved fifth artwork row; the current catalog/draw helpers; the WA.2–WA.4 debug fixtures and editor load/save/reopen path; and relevant WA.1–WA.4 cycle evidence. Mapped the eight labels, explicit artwork choices, and review states before editing.
- [x] Add only the eight WA.5 IDs, catalog definitions, and scalable static drawings through the existing renderer. Preserved all prior rows and kept shared primitives semantically distinguishable through drawing and adjacent labels.
- [x] Add the debug-only Storms + Fallback fixture and editor-load action. It uses eight adjacent, non-overlapping, one-hour SIMULATED ranges in board order, with no canonical weather values; existing selection and serialization behavior are reused.
- [x] Build/install the debug app on Pixel 3 XL, save and reopen the eight ranges, and inspect the direct preview flow and each symbol at 24 dp and 48 dp.
- [x] Capture one installed screenshot per symbol; record device/build/viewport/font scale, commands, save/reopen observations, artwork distinctions, canonical-fact isolation, PASS/FAIL/NOT RUN results, and limitations in the evidence README.
- [x] Inspect `git diff --check` and the final diff. WA.5 exit evidence is met; the roadmap is updated to DONE, the plan is archived, and cycle 098 is closed.

## Acceptance and authorized verification

- All eight IDs resolve deterministically to explicit definitions and scalable artwork through the existing API. Each installed state shows the correct label and intended fifth-row graphic at both preview sizes; compound storm distinctions, three separate hazard symbols, and unavailable-vs-clear remain recognizable. Earlier IDs retain their mappings and meanings.
- Installed scope: the debug catalog editor on Pixel 3 XL portrait, existing default font scale, and existing 24 dp/48 dp tiles. At execution, confirm the connected device serial; planning check on 2026-11-03 found Pixel 3 XL serial `8AKY0M32X`. Save/reopen each range, inspect the directly affected load/selection/persistence/preview behavior, and confirm that the symbol metadata adds no canonical weather conditions. These static drawings have no distinct reduced-motion state; record it as not applicable. RTL, TalkBack, alternate font/device, and broad system/environment matrices are outside this slice and remain for dedicated TEST work.
- Preserve eight actual captures under `.codex/test-artifacts/098-wa5-storm-fallback-symbols/`: `thunderstorm.png`, `thunderstorm-rain.png`, `thunderstorm-hail.png`, `thunderstorm-snow.png`, `squall.png`, `tropical-cyclone.png`, `tornado-funnel.png`, and `condition-unavailable.png`. Keep a concise observation and command log in `README.md`.
- Planned slice commands: `python scripts/dev.py workflow`; `adb devices -l`; `python scripts/dev.py run --serial <verified-device>`; one `python scripts/dev.py screenshot --serial <verified-device> --output .codex/test-artifacts/098-wa5-storm-fallback-symbols/<symbol>.png` per ID; `git diff --check`. Run `python scripts/dev.py build` separately only if installation does not establish build success. Record actual commands and results.
- Automated tests are **NOT AUTHORIZED by this planning request** and are not an acceptance step in this plan as drafted. Do not add or run tests. If separately authorized, scope the test change to catalog resolution and debug authored-event persistence/isolation, correct the known stale catalog-size assertion, and record exact test commands/results. Do not run the broad suite.
- A build or preview alone is not visual evidence. Do not claim installed/device acceptance without the screenshots and scoped interaction observations above.

## Risks and assumptions

- The raster artwork board is visual direction, not production-ready vector source. Lightning and precipitation detail may need simplification at 24 dp while labels retain exact meanings.
- The current debug editor and selector already persist symbol IDs for WA.1–WA.4. Reuse that path; only change serialization if execution demonstrates it is necessary.
- WA.3 recorded that the existing focused catalog test has a stale exact-16-ID assertion after the catalog expanded. This is a known test-maintenance limitation; test files remain outside scope unless separately authorized.
- The connected planning device was Pixel 3 XL `8AKY0M32X`; reconfirm availability and authorization during execution.

## Out of scope

WA.6 symbols; provider normalization or ingestion; unknown-code classification; canonical weather facts/schema; meteorological thresholds; official alerts, warning levels, or instructions; animation; scene composition or main-screen/timeline redesign; changing labels to rely on artwork alone; app-wide accessibility, device, localization, performance, or system acceptance; automated test source changes or execution without separate owner authorization.
