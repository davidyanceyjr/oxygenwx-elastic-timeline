# Cycle 104-sg11c-snow-mixed-events

Date: 2026-11-05
Roadmap: SG.11c

## Outcome

Owner accepted SG.11c completion on 2026-11-05. All eight snow/mixed identities, documented code assignments, canonical-only cases, version-5 recording/replay, focused mapper/debug tests, and installed state evidence are recorded. Five center condition label/date-time collisions remain visible; per owner direction this limitation is deferred to SG.12. No collision fix or SG.12 implementation is claimed.

## Verification actually performed

python scripts/dev.py workflow — passed; python scripts/dev.py contract — passed; python scripts/dev.py run --serial 8AKY0M32X — built, installed, launched; focused ./gradlew :app:testDebugUnitTest with OpenMeteoCatalogMapperTest, DebugSg11SnowMixedCatalogFixtureTest, DebugAuthoredCatalogTest, WeatherSymbolCatalogTest, and RecordedCatalogTest — BUILD SUCCESSFUL; git diff --check — passed. Broader python scripts/dev.py test: 192 tests, 5 failures, 1 skipped; two stale row-count assertions were corrected and passed in focused rerun; three SimulatedCatalogSourceTest expected 88 records versus current 1055 and remain outside this slice. Eight installed state XMLs and direct 8:00 AM to noon timeline selection are documented under .codex/test-artifacts/104-sg11c-snow-mixed-events/.

## Follow-up

The owner accepted SG.11c with the five-state center-readout label/date-time collision recorded in the evidence README. SG.12 owns the center-readout weather-data refactor; this cycle neither fixed nor claims to fix the collision. SG.11d is the next planned slice.
