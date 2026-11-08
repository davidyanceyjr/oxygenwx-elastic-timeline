# Cycle 120-wx30-one-location-rail

Date: 2026-11-08
Roadmap items: WX.30.2

## Outcome

WX.30.2 is DONE. Implemented the owner-approved catalog-route single rail, typed selected-instant saved temperature projection, unavailable treatment, focus separate from committed selection, durable Save and saved View, transient result View, removal confirmation, and explicit query states. Preserved the fixture selector. Installed Pixel 3 XL portrait acceptance met the slice exit. Evidence is recorded under .codex/test-artifacts/120-wx30-one-location-rail/README.md; final installed state is New York saved and selected with Wi-Fi restored.

## Slice outcomes

- **WX.30.2 — DONE:** Completed the catalog-route one-rail implementation and the approved installed Pixel 3 XL acceptance. Verified saved/result transitions, exact-instant available and unavailable rail temperatures, noncurrent focus without activation, durable Save and saved View, transient result View with unchanged persistence, removal Cancel/Confirm, search edit/submit/loading/no-match/failure/retry/clear, Back dismissal, attribution, and focused unit/Compose targets. Wi-Fi was restored; the final installed app has New York saved and selected. Cross-cutting accessibility, RTL, alternate font scale, reduced motion, and other-device checks remain outside this design slice. Evidence: .codex/test-artifacts/120-wx30-one-location-rail/README.md.

## Verification actually performed

- python scripts/dev.py workflow — passed at CLOSEOUT.
- python scripts/dev.py build — BUILD SUCCESSFUL in 5s; 37 tasks, 5 executed and 32 up to date.
- ./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.ui.LocationRailProjectionPositionTest' — passed.
- ./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.LocationSelectorPanelTest — passed on Pixel 3 XL / Android 12; emitted deprecation and unnecessary non-null assertion warnings.
- python scripts/dev.py run --serial 8AKY0M32X — passed; APK installed and MainActivity launched after the final instrumentation run.
- python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/120-wx30-one-location-rail/main-view-entry.png — passed.
- git diff --check — passed with no output.
- Read-only database comparison before/after result View confirmed the saved New York ID, selected New York ID, and LIVE dataset rows were unchanged; final database contains one saved/selected New York and one LIVE forecast-44d row.
- Limits: installed review covered Pixel 3 XL portrait only. RTL, TalkBack/accessibility services, alternate font scales, reduced-motion settings, and other devices were not checked; these remain outside this design slice.
- python scripts/dev.py test was started earlier and terminated before completion; it produced no result and is not counted as a cycle check.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/120-wx30-one-location-rail/`. Archived plan: `.codex/history/plans/120-wx30-one-location-rail.md`. See the plan for stage checkpoints and exact boundaries.
