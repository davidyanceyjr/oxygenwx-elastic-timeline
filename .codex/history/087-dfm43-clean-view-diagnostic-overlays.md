# Cycle 087-dfm43-clean-view-diagnostic-overlays

Date: 2026-11-02
Roadmap: DFM.4.3

## Outcome

Completed DFM.4.3 on its approved scoped evidence. The clean forecast view, session-local default-off diagnostics and local presets, typed diagnostic projection, and forecast-browser details are implemented. Installed Pixel captures cover clean LIVE, overlay off/on, preset configuration, and the active browser. The focused MainActivity browser accessibility test and error fixture pass. The connected suite also exposed one unrelated FirstCitySceneRenderingTest rain-bank pose failure; per owner direction it is recorded out of scope and was not investigated or changed. No remaining DFM.4.3 acceptance gap is claimed.

## Verification actually performed

python3 scripts/dev.py workflow: PASS before closeout (Workflow scaffold: OK). python3 scripts/dev.py contract: PASS. python3 scripts/dev.py build: PASS (BUILD SUCCESSFUL; up-to-date). ./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.presentation.CanonicalForecastProjectionTest' --tests 'com.oxygen.weather.timeline.data.debug.DiagnosticPresetCodecTest': PASS. ./gradlew :app:connectedDebugAndroidTest: 17 tests, 16 passed; CatalogForecastBrowserAccessibilityTest and CatalogForecastBrowserErrorAccessibilityTest passed; unrelated FirstCitySceneRenderingTest.selectedLocalTimeProducesStableRainAndStormCloudPoses failed with “rain banks should follow selected local time” and is owner-directed out of scope. Installed Pixel 3 XL evidence and interaction notes are in .codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/README.md. git diff --check: PASS; pre-slice-main.png hash unchanged.

## Follow-up

DFM.4.3 has no remaining scoped work. DFM.4.6 retains integrated workbench and release runtime acceptance; broad accessibility/system acceptance remains assigned to a dedicated TEST slice. The unrelated renderer assertion is recorded as out of scope by owner direction.
