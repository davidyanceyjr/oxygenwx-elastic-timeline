# Cycle plan — 097-wa4-visibility-wind-symbols

State: COMPLETE — archived after WA.4 closeout.
Plan readiness: approved boards recorded; focused test execution was not authorized.
Roadmap: WA.4 — sole NEXT slice; WA.3 dependency is DONE in cycle 096.
Objective: Add eight Visibility + Wind symbols through the shared static catalog with scoped installed evidence.

## Board gate

- Behavior board: `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md`, product-owner approved 2026-11-03 for the 48-ID mapping contract. Its fourth row defines Mist, Fog, Freezing fog, Haze/smoke, Dust/sand/ash, Windy/gusty, Blowing snow, and Blizzard. Its ID-to-artwork flow, distinct labels, provider-independent boundary, and no-invented-event rule govern WA.4 behavior and acceptance.
- UI artwork board: `Oxygen_forecast_weather_symbol_board.png`, product-owner approved 2026-11-03 for all six rows. Its fourth row shows the intended mist layers, dense fog, icy fog, amber haze, particulate dust, wind lines, blowing snow, and blizzard states. It governs appearance and 24 dp/48 dp comparisons. `docs/ROADMAP.md` records both approvals and their WA.4 relationship. The board gate is met.
- Constraints: deliver only row four; preserve distinct semantic IDs and precise adjacent labels even with shared primitives; catalog support does not claim provider support or create forecast facts, thresholds, or official alerts. Installed evidence is still required.

## Production boundary

- Extend `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt` for exactly eight WA.4 IDs, labels, mappings, and scalable static drawings through the existing caller-sized API. Preserve all WA.1–WA.3 meanings.
- Add one debug-only eight-range fixture under `app/src/debug/java/com/oxygen/weather/timeline/data/debug/` and one editor-load action in `AuthoredCatalogLibrary.kt`, following the WA.2–WA.3 patterns. Reuse selector, save/reopen, and preview behavior. Change debug serialization only if evidence shows it necessary.
- If WA.4 tests are separately authorized, update directly affected focused checks under `app/src/test/`. The existing `WeatherSymbolCatalogTest.kt` exact-16-ID assertion is stale after WA.3 and must be corrected to the catalog contract before an authorized run.
- Evidence: `.codex/test-artifacts/097-wa4-visibility-wind-symbols/`. Closeout: `.codex/history/097-wa4-visibility-wind-symbols.md`; archived plan: `.codex/history/plans/097-wa4-visibility-wind-symbols.md`.

## Functional and visual invariants

- Mist, fog, and freezing fog remain distinguishable through artwork plus labels. Haze/smoke and dust/sand/ash are grouped board concepts: preserve a supplied specific cause in adjacent text when available; an icon does not assert one. Windy/gusty, blowing snow, and blizzard remain distinct without inferring speed, visibility thresholds, or alert severity.
- Keep provider DTOs, thresholds, canonical facts, and event timing outside the graphics catalog. Debug symbol selection remains preview metadata, never a canonical weather condition.
- Preserve selected timestamp, timeline responsiveness, gesture ownership, navigation, source bounds, and readable forecast text. Static artwork fits existing 24 dp and 48 dp tiles without clipping. No animation or inferred weather values.
- Eight debug ranges are adjacent, non-overlapping, one-hour SIMULATED events in board order. Saving and reopening retains each ID without creating canonical condition facts.

## Ordered work

- [x] Reinspect the approved fourth artwork row, existing renderer and WA.1–WA.3 conventions, debug fixture/editor flow, and focused checks. Map all labels and visual distinctions.
- [x] Add only eight WA.4 IDs, explicit catalog definitions, and scalable drawings. Share primitives where the row's distinctions remain clear through art plus label.
- [x] Add the WA.4 fixture and editor-load action using existing selection, persistence, and preview paths.
- [x] Make the full row work on the installed reference device. Update/run focused tests only if WA.4 test execution is separately authorized; otherwise record NOT RUN and the stale assertion limitation.
- [x] Save/reopen the eight ranges. Capture one installed state per symbol with label and 24 dp/48 dp previews. Record commands, device/build/viewport, input actions, PASS/FAIL/NOT RUN results, and limits in the evidence README.
- [x] Inspect `git diff --check` and final diff. Update roadmap status only after exit evidence; archive plan and close cycle when complete.

## Acceptance and verification

- All eight IDs resolve deterministically to documented artwork and precise labels through the shared API. Fourth-row cause and wind/snow distinctions remain identifiable at intended sizes through drawing plus label; earlier rows retain their meanings. Grouped icons make no unsupported cause claim.
- Installed scope: debug catalog editor on Pixel 3 XL portrait reference viewport, default font scale, existing 24 dp and 48 dp tiles. Planning check `adb devices -l` found serial `8AKY0M32X`; reconfirm at execution and record actual viewport. Review only directly affected selector/load/save/reopen, labels, tiles, and canonical-fact isolation. The tiles are static, so reduced motion has no distinct WA.4 state; record it as not applicable. RTL and alternate font/viewport conditions are outside this design slice and remain for a dedicated TEST slice. Build or preview alone is insufficient.
- Planned commands: `python scripts/dev.py workflow`; `adb devices -l`; `python scripts/dev.py run --serial <verified-device>`; eight `python scripts/dev.py screenshot --serial <verified-device> --output .codex/test-artifacts/097-wa4-visibility-wind-symbols/<symbol>.png` calls; `git diff --check`. Build separately only if installation does not establish build success. Record actual results.
- Planning does not authorize adding or running automated tests. If separately authorized, update and run only focused catalog/debug authored-event checks, recording exact commands/results. Broad RTL, TalkBack, alternate-device/font, reduced-motion, performance, and system matrices belong to dedicated TEST slices. These static drawings add no motion behavior.

## Risks and exclusions

- The raster artwork board is visual direction, not a production-ready vector asset. Fine haze, blowing snow, or freezing detail may need simplification at 24 dp; adjacent labels retain precise meaning.
- The exact-16-ID assertion predates WA.3; it must be maintained during an authorized focused test run and its current limitation reported until then.
- Exclude WA.5–WA.6 symbols, provider normalization/ingestion, canonical facts/schema, meteorological thresholds, official alerts, animation, timeline or main-screen redesign, and app-wide acceptance.
