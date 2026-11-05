# Cycle 105-sg11d-visibility-wind-events

Date: 2026-11-05
Roadmap: SG.11d

## Outcome

Completed SG.11d. Added the approved Open-Meteo assignments 45 → FOG and 48 → FREEZING_FOG, retained the other six IDs as canonical-only, added the bounded version-5 replay review route, and captured all eight installed states plus hourly timeline selection. Existing rows and version 1–4 replay remained covered by focused tests. The close-spacing readout limitation remains assigned to SG.12.

## Verification actually performed

python scripts/dev.py workflow — passed before activation. python scripts/codex_cycle.py activate — passed. ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.openmeteo.OpenMeteoCatalogMapperTest --tests com.oxygen.weather.timeline.data.debug.DebugSg11VisibilityWindCatalogFixtureTest --tests com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest --tests com.oxygen.weather.timeline.presentation.symbol.WeatherSymbolCatalogTest --tests com.oxygen.weather.timeline.data.catalog.RecordedCatalogTest — BUILD SUCCESSFUL; 36 tests, 0 failures, 1 skipped. python3 scripts/dev.py contract — passed. python3 scripts/dev.py run --serial 8AKY0M32X — debug build installed and launched. Pixel 3 XL installed captures and paired UI hierarchy XMLs for all eight identities are in .codex/test-artifacts/105-sg11d-visibility-wind-events/. Direct timeline hold preserved the 3:00 PM selected instant while entering hourly scale; hourly scrub selected 2:29:48 PM and displayed the 2:00 PM BLOWING_SNOW sample. Device font_scale=1.0, 1440x2960, density 560. DISPLAY=:0 was available. git diff --check — passed. RTL, TalkBack, reduced motion, alternate font scales, and broad system/device matrices were not checked; these are outside this design slice.

## Follow-up

SG.11d met its scoped exit. The known close spacing between the central condition and selected date/time remains outside this row slice and is assigned to SG.12. RTL, TalkBack, reduced motion, alternate font scales, and broad system/device matrices remain dedicated TEST-slice work.
