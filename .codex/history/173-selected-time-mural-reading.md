# Cycle 173-selected-time-mural-reading

Date: 2026-11-22
Roadmap items: OX-12, OX-12A

## Outcome

Cycle 173 completed OX-12 and OX-12A in dependency order. The typed selected-time binding passed focused projection verification, and installed Park mural acceptance passed for the supported Oct 4 10:30 AM and unavailable Oct 4 11:30 AM SG.4 states. The apparent mismatch that prompted reopening OX-12 came from an Oct 3 route, not the Oct 4 target; corrected diagnostics matched the binding, so no additional binding code change was needed.

## Slice outcomes

- **OX-12 — DONE:** Rechecked the selected-temperature binding on the correct Oct 4 SG.4 route after tracing the apparent source mismatch to an Oct 3 selection. At Oct 4 10:30 AM CDT, the binding exposes the supported 10:00 AM sample as 57°F; at 11:30 AM it remains explicitly unavailable with the 10:00 AM condition evidence separate. No additional binding code change was needed. Evidence: .codex/test-artifacts/173-selected-time-mural-reading/README.md, .codex/test-artifacts/173-selected-time-mural-reading/ox-12-canonical-forecast-projection-test.xml, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-wrong-date-time-diagnostic.png, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-selection-diagnostic.png, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-selection-diagnostic.png.
- **OX-12A — DONE:** Installed portrait acceptance passed for the Oct 4 SG.4 target. At 10:30 AM CDT the central readout and mural agree on the supported temperature, and the mural preserves °F and the 10:00 AM source time. At 11:30 AM the central temperature and mural are explicitly unavailable, with no carried temperature; the separate rain condition retains and discloses its 10:00 AM source time. Both captures were inspected on the Pixel 3 XL; broader RTL, TalkBack, and system acceptance were outside this slice. Evidence: .codex/test-artifacts/173-selected-time-mural-reading/README.md, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-selection-diagnostic.png, .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-selection-diagnostic.png.

## Verification actually performed

- python scripts/dev.py workflow -> Workflow scaffold: OK at the final CLOSEOUT checkpoint.
- git diff --check -> passed with no output.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.CanonicalForecastProjectionTest -> BUILD SUCCESSFUL; 12 tests, 0 failures, 0 errors.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest -> BUILD SUCCESSFUL.
- ./gradlew :app:compileDebugAndroidTestKotlin -> BUILD SUCCESSFUL; compile-only compatibility check.
- python scripts/dev.py run --serial 8AKY0M32X -> debug build, install, and launch succeeded.
- python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png -> captured and inspected; Oct 4 10:30 AM CDT shows 57°F valid at 10:00 AM.
- python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png -> captured and inspected; Oct 4 11:30 AM CDT shows unavailable/no value and separate condition source disclosure from 10:00 AM.
- adb uiautomator clean-state hierarchy inspection -> 10:30 description includes temperature source at 10:00 AM; 11:30 description reports unavailable temperature and separate 10:00 AM condition carry.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/173-selected-time-mural-reading/`. Archived plan: `.codex/history/plans/173-selected-time-mural-reading.md`. See the plan for stage checkpoints and exact boundaries.
