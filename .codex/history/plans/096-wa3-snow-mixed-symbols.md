# Cycle plan — 096-wa3-snow-mixed-symbols

State: COMPLETE — archived after WA.3 closeout.
Plan readiness: board gate met through the owner-approved shared WA behavior and artwork boards. Test execution remains a separate owner action.
Roadmap: WA.3
Objective: Implement the eight Snow + Mixed Precipitation symbols through the shared static catalog with scoped installed evidence.

## Verified baseline and production boundary

- WA.3 is the sole NEXT slice; WA.2 is DONE. The product owner approved the shared behavior board `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` and UI artwork board `Oxygen_forecast_weather_symbol_board.png` on 2026-11-03. Their third row governs this cycle.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt` owns the size-aware static catalog and existing Sky/Rain + Ice entries. `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugRainIceSymbolRangeFixture.kt` and `AuthoredCatalogLibrary.kt` show the established eight-range fixture and editor-load pattern. `DebugAuthoredCatalog.kt` already persists symbol IDs. The focused checks are `app/src/test/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbolCatalogTest.kt` and `app/src/test/java/com/oxygen/weather/timeline/data/debug/DebugAuthoredCatalogTest.kt`; changing or running them requires explicit WA.3 authorization.
- Limit production edits to `WeatherSymbol.kt`, a WA.3 debug range fixture, and the directly affected editor-load path in `AuthoredCatalogLibrary.kt`. Only evidence or tests directly needed by those changes may be added. Evidence target: `.codex/test-artifacts/096-wa3-snow-mixed-symbols/`; closeout target: `.codex/history/096-wa3-snow-mixed-symbols.md` and archived plan.

## Functional and visual invariants

- Add distinct stable IDs, precise labels, explicit mappings, and scalable drawings for Flurries/snow grains, Snow, Heavy snow, Snow showers, Rain and snow/sleet, Wintry mix, Graupel, and Ice crystals. Shared primitives may be used where artwork plus labels preserve distinctions.
- Graupel, ice pellets, and ice crystals remain distinct. Rain/snow mixture has its own ID; unqualified provider “sleet” needs provider-defined normalization outside this slice. Preserve supplied component names where available, including distinct mixed-precipitation labels. Preserve WA.1/WA.2 meanings.
- Graphics use caller-provided size/style and do not infer provider facts, thresholds, or official alerts. Debug selection remains preview metadata. Canonical facts, selected time, source bounds, navigation, and timeline behavior remain unchanged. Forecast meaning remains visible as text.
- Static drawings fit existing 24 dp and 48 dp preview tiles without clipping.

## Ordered work

- [x] Inspected the approved third artwork row, catalog renderer, selector persistence, and row fixtures; mapped visual distinctions.
- [x] Added only the eight WA.3 IDs, labels, mappings, and scalable artwork through the existing API.
- [x] Added a WA.3 fixture with eight sequential, non-overlapping one-hour SIMULATED ranges in board order and an editor-load action in `AuthoredCatalogLibrary.kt`. Reused symbol persistence and preview tiles; confirmed save/reopen and canonical-fact isolation through the installed editor.
- [x] WA.3 tests were not authorized, so no test files were changed and no tests were run. The existing exact-16-ID assertion needs revision before the next authorized focused test run. Built/installed on Pixel 3 XL and inspected one saved/reopened state per symbol at 24 dp and 48 dp.
- [x] Recorded commands, results, screenshots, observations, and limits in the cycle evidence. Inspected final diff and ran `git diff --check`.

## Acceptance and evidence

- All eight IDs resolve deterministically through the shared API with precise labels and artwork at both sizes. Snow intensity/showers and frozen/mixed types remain distinguishable through artwork plus adjacent label. Existing IDs retain their meanings.
- Preserve eight installed screenshots in `.codex/test-artifacts/096-wa3-snow-mixed-symbols/`, with one saved SIMULATED range per symbol. Record PASS/FAIL/NOT RUN for scoped interactions and visual distinctions. A build alone does not establish visual acceptance.
- Planned commands: `python scripts/dev.py workflow`, `adb devices -l`, `python scripts/dev.py run --serial <verified-device>`, eight `python scripts/dev.py screenshot --serial <verified-device> --output .codex/test-artifacts/096-wa3-snow-mixed-symbols/<symbol-id>.png` calls, and `git diff --check`. `python scripts/dev.py build` may be used if installation does not already establish build success. If tests are authorized, run only `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolCatalogTest --tests com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest`. Confirm the device serial at execution; WA.1/WA.2 test authorization does not carry forward.
- Installed scope is the debug editor on Pixel 3 XL portrait, at the existing default font scale and existing 24 dp/48 dp tiles. The work adds static drawings and labels through an existing flow, so reduced-motion has no new behavior. The owner-approved row does not change layout direction or add a new interaction model; RTL and alternate-font matrices remain for dedicated TEST work. Inspect the directly affected labels and tiles at the scoped default state. Broad TalkBack, alternate-device, environment, and performance acceptance stay outside this design slice.

## Risks / assumptions

- Fine grain/crystal detail may need simplification at 24 dp while retaining precise adjacent labels; this does not merge IDs.
- The WA.2 fixture and editor-load action provide a concrete template. The third row still needs its own eight IDs and artwork; reuse of the workflow does not establish visual acceptance.

## Out of scope and board gate

WA.4–WA.6 symbols, provider ingestion/sleet interpretation, canonical schema/facts, inferred weather events, official alerts, animation, main-screen redesign, and app-wide acceptance.

Behavior board: `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md`, approved 2026-11-03 as the shared 48-ID mapping contract. Its third row governs the eight WA.3 IDs, labels, explicit mapping, provider-independent graphics boundary, and sleet normalization rule.

UI artwork board: `Oxygen_forecast_weather_symbol_board.png`, approved 2026-11-03 as visual direction for the six rows. Its third row governs WA.3 appearance and installed 24 dp/48 dp comparisons. Both approvals and their relationship to WA.3 scope are recorded in `docs/ROADMAP.md`; the board gate is met. Approval constraints: deliver only row three; retain distinct IDs and labels even when primitives are shared; catalog support does not imply provider support; do not invent forecast events or official alerts. The plan was activated and completed for cycle 096.

## Difficulty and model fit

Difficulty: **4/10**. The catalog and preview pattern exist; uncertainty centers on 24 dp visual distinctions and eight installed states. Recommend **GPT-6.1 Sol** as the least costly credible implementation fit. Use **GPT-6 Astra** if visual acceptance requires renderer redesign or conflicting semantics arise. This is relative model fit only.
