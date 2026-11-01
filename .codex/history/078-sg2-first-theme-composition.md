# Cycle 078-sg2-first-theme-composition

Date: 2026-10-31
Roadmap: SG.2

## Outcome

SG.2 complete. Added the versioned first-city composition, aligned architecture/canopy layers, selected-time palette and cue, and additive catalog presentation route. Updated narrow-screen layout to stack the Oxygen Time Lab title and DEMO DATA label after the Pixel review found a collision. Captured the four portrait treatments and compact stacked-header state; added focused on-device scene-rendering checks for null weather, optional night-detail omission, and static selected-time output. System-wide checks were excluded per owner direction.

## Verification actually performed

python scripts/dev.py workflow: Workflow scaffold OK; cycle 078 ACTIVE before closeout. python scripts/dev.py contract: passed. python scripts/dev.py build: passed. python scripts/dev.py run --serial 8AKY0M32X: installed/launched. python scripts/dev.py lint --serial 8AKY0M32X: passed. ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.FirstCitySceneRenderingTest: 3 tests passed, 0 failures on Pixel 3 XL Android 12; XML at .codex/test-artifacts/078-sg2-first-theme-composition/FirstCitySceneRenderingTest-results.xml. Installed captures at .codex/test-artifacts/078-sg2-first-theme-composition/ (morning, noon, dusk, night, compact). git diff --check: clean. Pixel display override restored to 1440x2960 at 560 dpi. TalkBack, RTL, font-scale, system animation, and other system-wide checks not run, per owner direction.

## Follow-up

The owner re-reviewed the post-slice Pixel screen against the artwork target on 2026-10-31 and rejected the visual result: the city felt muted/far away, sky dominated, the sun cue was too small, clouds read as connected circles, the cue path looked instructional, and rain looked like comets. The cycle's build, focused scene checks, and captures remain valid records of what was done, but they did not establish acceptable visual completion. The earlier “no SG.2 follow-up” disposition is superseded. SG.2 has been restored to NEXT for a bounded refactor in cycle 079; SG.3 is PLANNED behind it. Re-review evidence: `.codex/test-artifacts/078-sg2-first-theme-composition/README.md`; refactor draft: `.codex/plans/079-sg2-atmosphere-recomposition.md`.
