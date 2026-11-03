# Cycle plan — 094-weather-symbol-library

State: ACTIVE
Plan readiness: finalized after product-owner approval of the behavior and artwork boards on 2026-11-03; scoped simulated-range tests and installed captures authorized by the product owner.
Roadmap: WA.1
Objective: Implement the eight sky symbols in WA.1, establishing the reusable static Compose symbol API and scalable-artwork conventions for the six-row, 48-symbol catalog.

## Production boundary

Expected affected areas:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/` for stable WA.1 IDs, catalog resolution, and reusable size-aware Compose rendering.
- `app/src/main/res/drawable/` for editable/scalable vector artwork for the eight Sky symbols, or equivalent app-local vector source if required by the renderer choice.
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/` for the debug-only simulated-event selector and selected-range preview seam.
- Focused tests under `app/src/test/` for the eight-ID resolution contract and debug event-range persistence/materialization only.
- `.codex/test-artifacts/094-weather-symbol-library/` for actual installed Pixel screenshots and observations.

No provider integration, canonical weather fact/schema, alert, selected-time, navigation, or timeline behavior changes. The simulated symbol selector remains debug-only and is metadata for visual review, not a newly supplied canonical forecast fact.

## Functional invariants

- The graphics API accepts stable catalog symbol IDs; provider DTOs and weather thresholds stay outside graphics.
- A semantic ID may share artwork with another ID, but event meaning and forecast text remain specific.
- The API does not silently resolve an unimplemented/unknown ID to a different symbol; the unavailable symbol itself is delivered in its WA.5 row.
- The symbol renderer consumes caller-provided size/bounds; it does not own page coordinates or navigation state.
- Static artwork preserves the same meaning across size and palette changes. Any graphic whose detail is not legible at compact sizes relies on its adjacent text label.
- Provider support stays separate from catalog support: all 48 IDs have visual representations, but the app does not invent an event a source did not supply.
- Each debug simulated event range selects one explicit WA.1 symbol ID for preview; that selector is persisted with the debug authored event and is not materialized as a canonical weather fact.

## Steps

- [x] Record product-owner approval of the shared behavior/mapping board and six-row artwork board, including constraints, here and in the roadmap.
- [x] Inspect current Compose and vector-resource conventions; keep the library in the existing app module unless source evidence requires another boundary.
- [x] Implement only WA.1's eight Sky symbol IDs, scalable artwork, and reusable size-aware Compose API; do not implement the other five rows in this cycle.
- [x] Add a debug-only selector to simulated authored event ranges so each range previews one explicit WA.1 symbol ID without changing canonical facts or forecast behavior.
- [x] Add focused tests for all eight ID-to-art resolutions and debug event-range selector persistence/materialization isolation.
- [x] Build/install the debug app on the available Pixel 3 XL; create eight sequential non-overlapping one-hour simulated ranges, select each range, and capture each displayed graphic.
- [x] Inspect every captured symbol at compact and larger sizes, record results, then inspect the final diff and `git diff --check`.

## Execution record

- Focused tests passed: `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolCatalogTest --tests com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest`.
- Installed review passed on Pixel 3 XL serial `8AKY0M32X`. Eight adjacent simulated ranges selected the eight WA.1 IDs in order; each installed capture shows the saved selection plus 48 dp and 24 dp previews. Observations and image paths are in `.codex/test-artifacts/094-weather-symbol-library/README.md`.
- The user authorized focused tests and installed screen-capture evidence only. No broad test suite or cross-cutting device/accessibility matrix was run.

## Acceptance/evidence

The user authorized focused WA.1 tests and installed screen-capture evidence. Run only the tests named above, `python scripts/dev.py workflow`, and `python scripts/dev.py build`/`run` as needed for installed review. Do not run the broad test suite or unrelated checks. Use Pixel 3 XL portrait on serial `8AKY0M32X`; capture each of the eight symbol states using `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/094-weather-symbol-library/<symbol-id>.png`. Keep the simulated event range sequence and a concise observation log in that evidence directory. Check only artwork fidelity to the approved board, selected symbol correctness, compact/larger scaling, and the direct preview interaction. Do not add RTL, TalkBack, large-font, reduced-motion, or broad system/environment matrices to WA.1. A build or preview alone is not installed acceptance.

## Risks / assumptions

The 48-cell raster concept is a style/inventory reference, not production-ready scalable source artwork. All 48 visual IDs are in scope across WA.1–WA.6; this cycle implements only the first row. Current canonical weather categories do not distinguish all eight Sky board entries. The debug-only event-range selector will exercise symbol IDs directly and must not imply that the canonical forecast schema already represents them.

## Out of scope

Provider integrations; official alerts or hazard thresholds; animation; theme selection; replacing readable labels with art; broad screen redesign; claiming every catalog item is currently supplied by a provider; changing canonical forecast fields or existing scene/weather semantics; implementing any row after Sky.

## Slice-board gate and status

Behavior board: `.codex/concepts/WA.1-weather-symbol-library-behavior-board.md` (approved by the product owner on 2026-11-03 as the shared 48-ID mapping contract and six-row sequence).

UI artwork board: `Oxygen_forecast_weather_symbol_board.png` (approved by the product owner on 2026-11-03 for WA.1–WA.6; WA.1 implements only the Sky row).

Approval constraints: implement the six rows as sequential slices; keep every symbol's semantic ID and label distinct even when artwork is shared; expose all 48 visual IDs independently of current provider coverage; never invent a forecast event or an official alert. The user additionally directed focused tests through simulated event ranges and one screenshot per graphic. The board gate is met. Verification is limited to WA.1's eight IDs, debug event-range preview, compact/larger icon scaling, and installed Pixel evidence.

## Difficulty and model fit

Draft estimate: 7/10. Forty-eight entries, scalable artwork quality, compact-size legibility, and reusable packaging create moderate breadth and visual-review cost; integration uncertainty depends on existing Gradle/Compose conventions. Relative model fit: GPT-6.1 Sol is the least-cost credible implementation choice; escalate to GPT-6 Astra if multi-module graphics packaging or artwork/rendering behavior proves materially uncertain.
