# Cycle plan — 095-wa2-rain-ice-symbols

State: ACTIVE
Plan readiness: reviewed with the shared WA behavior and artwork boards approved by the product owner on 2026-11-03. Activation and tests remain separate owner actions.
Roadmap: WA.2
Objective: Add the eight Rain + Ice symbols through the WA.1 size-aware catalog and debug preview, with scoped installed evidence.

## Production boundary

- Extend `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt` with distinct IDs, labels, explicit catalog definitions, and static Rain + Ice artwork. Retain the existing Compose API and Sky row.
- Extend only directly affected debug preview/fixture code under `app/src/debug/java/com/oxygen/weather/timeline/data/debug/` to exercise the eight new symbols in sequential SIMULATED authored-event ranges. Reuse existing selection, serialization, and preview behavior.
- If tests are authorized for WA.2, adjust focused catalog and debug authored-event checks under `app/src/test/`. Reserve `.codex/test-artifacts/095-wa2-rain-ice-symbols/` for actual evidence.
- Closeout target: `.codex/history/095-wa2-rain-ice-symbols.md`; archived plan target: `.codex/history/plans/095-wa2-rain-ice-symbols.md`.

No provider adapter, canonical fact/schema, selected-time, navigation, scene, or timeline behavior changes. Debug selection remains preview metadata, not a supplied forecast fact.

## Functional and visual invariants

- Implement Drizzle, Rain, Heavy rain, Rain showers, Freezing drizzle, Freezing rain, Ice pellets/sleet, and Hail as eight distinct stable IDs with precise labels. Shared primitives may be used where the event distinction remains clear through artwork plus adjacent label.
- The WA.2 ice-pellets ID represents pellets. The board's “sleet” footnote requires provider-defined normalization: do not classify an unqualified provider sleet term here. Rain/snow mixture belongs to WA.3. This slice adds no provider mapping.
- Keep provider codes, weather thresholds, forecast state, and page coordinates outside the graphics API. The renderer uses caller-provided bounds. Do not silently resolve an unimplemented ID to a different symbol.
- Forecast facts stay readable as text. Preserve WA.1 Sky symbol semantics and debug authored-event serialization. Static graphics must fit the existing 24 dp and 48 dp preview tiles.
- Each debug event range selects one explicit WA.2 ID; saving/reopening retains it without creating a canonical weather fact.

## Ordered steps

- [x] Inspect the approved Rain + Ice artwork row and WA.1 catalog, renderer, debug selector/range fixture, and focused checks; map all eight IDs and intended visual distinctions.
- [x] Add only WA.2 IDs, labels, mappings, and scalable artwork using the existing renderer and drawing conventions.
- [x] Extend the debug-only range path to create eight adjacent, non-overlapping, one-hour SIMULATED events in board order; preserve persisted selection and canonical-fact isolation.
- [x] Owner authorized focused WA.2 catalog and debug checks; updated and ran them. Built and installed the debug app on Pixel 3 XL.
- [x] Saved/reopened the events and captured one installed screenshot per symbol with label and 24 dp/48 dp previews; inspected precipitation intensity/type, freezing distinction, pellet/hail shapes, clipping, and selector behavior.
- [x] Recorded commands, results, screenshot paths, device/viewport, and limitations. Inspected final diff and ran `git diff --check`.

## Acceptance and evidence

- All eight new IDs resolve deterministically to documented scalable artwork through the shared API; existing Sky IDs retain their meanings. No unknown or later-row ID is given an invented fallback.
- Installed Pixel 3 XL portrait evidence shows one saved SIMULATED event per WA.2 symbol, in eight adjacent one-hour ranges, with the correct precise label and graphic at 24 dp and 48 dp. The ranges supply no new canonical weather facts.
- Review only the row's artwork distinctions and the directly affected debug selection/save/reopen/preview interaction. Adjacent text may carry details that a 24 dp icon cannot. Record any unmet distinction as FAIL; a build alone is not visual acceptance.
- Planning device check: `adb devices -l` showed Pixel 3 XL serial `8AKY0M32X`. Confirm it again at execution. Planned commands: `python scripts/dev.py workflow`, `python scripts/dev.py run --serial 8AKY0M32X`, and eight `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/095-wa2-rain-ice-symbols/<symbol-id>.png` calls. Focused `WeatherSymbolCatalogTest` and `DebugAuthoredCatalogTest` changes/runs require WA.2 test authorization; WA.1 authorization does not carry forward.
- The affected state is the debug editor in Pixel 3 XL portrait at existing 24 dp and 48 dp preview sizes. Review label readability and preview layout there. Alternate font/viewport, RTL, TalkBack, and reduced-motion matrices are not needed for this static preview slice and remain with dedicated TEST work. `DISPLAY` was unavailable during planning; Android device capture is available.

## Risks / assumptions

- The approved raster board is visual direction, not production-ready vector source. Fine precipitation detail may need simplification at 24 dp while precise labels remain visible.
- Rain intensity, showers, freezing treatment, pellets, and hail must remain visually and semantically distinct without interpreting provider facts.
- WA.1's current catalog test assumes exactly eight IDs; when WA.2 tests are authorized, update that expectation while retaining Sky coverage.

## Out of scope

WA.3–WA.6 symbols; provider-code ingestion or provider-specific sleet interpretation; canonical facts/schema; weather thresholds; official alerts; animation; replacing labels with art; broad screen redesign; app-wide accessibility, device, RTL, or performance acceptance.

## Slice-board gate

Behavior board: `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md`, product-owner approved 2026-11-03 as the shared 48-ID contract. Its Rain + Ice row, distinct IDs/labels, explicit mapping, provider-independent graphics boundary, and sleet footnote govern WA.2 scope and acceptance.

UI artwork board: `Oxygen_forecast_weather_symbol_board.png`, product-owner approved 2026-11-03 for all six rows. Its second row governs WA.2 appearance and installed size comparisons. Both approvals and the WA.2 scope relationship are recorded in `docs/ROADMAP.md`; the owner explicitly confirmed these boards qualify for WA.2. The board gate is met.

Approval constraints: deliver only row two now; retain distinct semantic IDs and labels even with shared primitives; catalog representation does not imply provider support; do not invent forecast events or official alerts. Boards guide scope and acceptance but do not replace installed evidence.
