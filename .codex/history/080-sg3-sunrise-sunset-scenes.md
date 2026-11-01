# Cycle 080-sg3-sunrise-sunset-scenes

Date: 2026-10-31
Roadmap: SG.3

## Outcome

SG.3 implemented provider-backed daily sunrise/sunset events through Open-Meteo mapping, canonical daily summaries, v3 recordings, replay, and shared selected-date scene readings. Sun cues use provider events only for horizon crossings; the intervening arc makes no azimuth/elevation claim. Cloud poses now follow selected local time at bounded 06:00/12:00/18:00 checkpoints, with one incoming rain bank, opposing storm banks, and clouds between distant skyline and foreground architecture. Both required behavior/UI boards were product-owner approved. Owner testing constraints limited acceptance to focused mapper/replay, scene, and Pixel app checks; omitted RTL, TalkBack, and system-wide acceptance is not claimed.

## Verification actually performed

PASS: python scripts/dev.py workflow; python scripts/dev.py contract; python scripts/dev.py build; python scripts/dev.py lint --serial 8AKY0M32X; git diff --check. PASS: ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.openmeteo.OpenMeteoCatalogMapperTest --tests com.oxygen.weather.timeline.data.catalog.RecordedCatalogTest (17 tests, zero failures, one conditional test skipped); focused scene projection tests; FirstCityThemeTest exact event-boundary checks. PASS: ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.FirstCitySceneRenderingTest on Pixel 3 XL API 31 (5 tests, zero failures). Evidence: .codex/test-artifacts/080-sg3-sunrise-sunset-scenes/README.md, command logs, provider DST responses, and Pixel captures. Limitations: no RTL, TalkBack, or system-wide tests per owner direction; compact/large-font and reduced-motion sweeps are not claimed. Pixel sunrise/sunset screenshots are near the provider event times; exact boundary geometry is verified by focused unit tests. Full test suite not run.

## Follow-up

Broader RTL, TalkBack, compact/large-font, and reduced-motion/system-configuration acceptance was not performed under the owner's explicit limit and remains unclaimed. Cycle 080's evidence is slice-local and does not substitute for future release-wide accessibility or configuration review. Exact solar boundary geometry is covered by focused tests; installed Pixel screenshots show times near, not exactly at, the provider events.
