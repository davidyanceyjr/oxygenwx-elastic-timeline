# Cycle 088-dfm44-local-catalog-storage

Date: 2026-11-02
Roadmap: DFM.4.4

## Outcome

Completed DFM.4.4: debug-only period-wide typed SIMULATED materialization over the deterministic full 44-date baseline; sidecar-bound editable metadata with fail-closed reopen and coordinator-owned preview validation; immutable, separately identified REPLAY snapshots; and safe saved-only coordinator preview. Owner-resolved semantics and evidence are recorded in the archived plan and .codex/test-artifacts/088-dfm44-local-catalog-storage/README.md. No shared schema or LIVE/provider/cache/location state changed; editor UI remains in DFM.4.5.

## Verification actually performed

python3 scripts/dev.py workflow: PASS before work and at closeout (Workflow scaffold: OK). ./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest' --tests 'com.oxygen.weather.timeline.data.catalog.CatalogActivationCoordinatorTest' --tests 'com.oxygen.weather.timeline.data.catalog.CatalogDatasetStorageTest': PASS, 26 tests total (4 authored-catalog, 19 coordinator, 3 storage). python3 scripts/dev.py build: PASS (BUILD SUCCESSFUL; debug APK assembled). git diff --check: PASS. At cycle closeout, device/UI acceptance, contract checks, broad suite, Android SharedPreferences runtime exercise, and accessibility/system matrices were NOT RUN; outside this nonvisual slice.

## Post-closeout exploratory device check (2026-11-02)

On the connected Pixel 3 XL, `python3 scripts/dev.py run --serial 8AKY0M32X` installed and launched the debug app successfully. MainActivity restored saved Chicago (`America/Chicago`) and rendered the timeline facts. The location selector listed Chicago as saved and selected. In the existing debug picker, the Chicago `wx22-chicago-replay-v3` fixture loaded and was reported active as `REPLAY · geonames:4887398 · wx22-chicago-replay-v3 · CACHE`; returning to the immersive timeline succeeded. The sampled launch/navigation log contained no `FATAL EXCEPTION`, `SQLiteException`, or Room verification error. `git diff --check` passed, and no app data was cleared.

This was exploratory coverage of reachable app routes, not DFM.4.4 installed acceptance. The existing REPLAY fixture route does not exercise DFM.4.4's authored REPLAY sidecar/immutability behavior. Authored SIMULATED save/reopen and coordinator saved-preview remain NOT RUN on-device because the authoring controls are not exposed in the current picker; DFM.4.5 owns that UI. No screenshot or additional automated test was produced. Detailed supplemental notes are in the ignored local evidence file `.codex/test-artifacts/088-dfm44-local-catalog-storage/README.md`.

## Follow-up

DFM.4.5 is now NEXT and owns the catalog library/editor UI plus installed state and interaction evidence. Android SharedPreferences persistence compiled but the focused round-trip tests used an in-memory backend; installed persistence/UI acceptance remains for DFM.4.5. Broad accessibility/system acceptance and integrated release acceptance remain outside this slice.
