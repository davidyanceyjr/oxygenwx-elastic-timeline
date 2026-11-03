# Cycle 093-sg8-lunar-events-and-phases

Date: 2026-11-03
Roadmap: SG.8

## Outcome

Implemented provider-backed lunar rise/set and phase through catalog mapping, v4 recordings, selected-time scene projection, cloud-layered cues, and supported text. Preserved legacy v1-v3 replay. The owner-approved midnight row-ownership rule and responsive compact 1.3 layout are recorded. Pixel 3 XL installed review passed in default portrait, compact 1080x2400 at font scale 1.3, reduced motion, event boundaries/approaches, and a live cloud-overlap state. Evidence is in .codex/test-artifacts/093-sg8-lunar-events-and-phases/.

## Verification actually performed

PASS: ./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.openmeteo.OpenMeteoCatalogMapperTest' --tests 'com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest' --tests 'com.oxygen.weather.timeline.data.catalog.RecordedCatalogTest' (23 tests total, 1 skipped, 0 failures/errors). PASS: python3 scripts/dev.py run --serial 8AKY0M32X (installed debug build). PASS: python scripts/dev.py workflow. PASS: git diff --check. PASS: Pixel 3 XL installed screenshots and hierarchy at default portrait, compact 1080x2400/font scale 1.3, reduced motion, rise/set approach and event-near-boundary, plus live cloud overlap. PASS: device settings restored to 1440x2960, font scale 1.0, all animation scales 1.0. NOT RUN: RTL, TalkBack, and broad system/environment matrices; reserved for dedicated TEST slices.

## Follow-up

No remaining SG.8 acceptance gaps. RTL, TalkBack, and broad system/environment acceptance remain assigned to dedicated TEST slices.
