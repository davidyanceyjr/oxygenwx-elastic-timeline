# Cycle 144-ox05-depth-ordering

Date: 2026-11-16
Roadmap items: OX-05

## Outcome

Completed OX-05 depth ordering with the owner-approved debug-only synthetic astronomy fixture and authored sidecar. The First City physical order is explicit across Background, Middle-Ground, and Foreground; installed primary-screen evidence covers the exact celestial-overlap and Oct 29 moderate-rain states with the provenance label visible. The owner-selected existing-browser route selected the local-noon row exactly. The separate accepted state-based renderer test limitation is documented in cycle evidence.

## Slice outcomes

- **OX-05 — DONE:** Implemented the approved depth ordering and fixed debug fixture; focused JVM and OX-05 composition checks passed, and both exact installed MainActivity states were captured with the authored-synthetic provenance label. The installed browser driver selected the Oct 29 daily row and asserted 12:00:00 PM CDT with rain. The one failure in the broader scoped renderer class is the previously owner-accepted separate state-based overload limitation documented in the evidence README. Evidence: .codex/test-artifacts/144-ox05-depth-ordering/README.md, .codex/test-artifacts/144-ox05-depth-ordering/ox05-celestial-overlap-installed.png, .codex/test-artifacts/144-ox05-depth-ordering/ox05-moderate-rain-installed.png, .codex/test-artifacts/144-ox05-depth-ordering/ox05-installed-browser-driver.xml, .codex/test-artifacts/144-ox05-depth-ordering/ox05-depth-ordering-jvm-tests.xml, .codex/test-artifacts/144-ox05-depth-ordering/ox05-debug-sidecar-jvm-tests.xml.

## Verification actually performed

- python scripts/dev.py workflow — passed at CLOSEOUT.
- git diff --check — passed.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest --tests com.oxygen.weather.timeline.data.debug.DebugOx05AstronomySidecarTest — passed.
- ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.FirstCitySceneRenderingTest — 6 passed, 1 failed: selectedLocalTimeProducesStableRainAndStormCloudPoses at FirstCitySceneRenderingTest.kt:168 (rain banks should follow selected local time); the separate state-based overload limitation was owner-accepted and recorded.
- ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.Ox05InstalledForecastBrowserDriverTest — passed on Pixel 3 XL, 1/1; selected the Oct 29 row and asserted 12:00:00 PM CDT with rain.
- python scripts/dev.py run --serial 8AKY0M32X — debug build, install, and launch passed.
- Installed review — captured and inspected the exact Oct 28 celestial-overlap and Oct 29 moderate-rain states on Pixel 3 XL 8AKY0M32X; screenshots and limits are recorded under .codex/test-artifacts/144-ox05-depth-ordering/.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/144-ox05-depth-ordering/`. Archived plan: `.codex/history/plans/144-ox05-depth-ordering.md`. See the plan for stage checkpoints and exact boundaries.
