# Cycle 167-ox32-installed-theme-hot-swap

Date: 2026-11-21
Roadmap items: OX-32

## Outcome

OX-32 implemented and completed. On Pixel 3 XL, the app switched from bundled First City to the installed compatible baseline pack and back in the same Activity/process. The installed canopy changed visibly; exact weather location, catalog identity/provenance, selected instant, visible facts, DEMO DATA disclosure, and daily precision remained fixed. Debug coordinator and source-load counts did not increase, timeline scrubs worked before and after, and the authorized successful-candidate supersession test passed. See the cycle evidence README for device records and scoped limits.

## Slice outcomes

- **OX-32 — DONE:** Installed free → alternate → free evidence on Pixel 3 XL shows the canopy changed and returned in one Activity/process; active location, catalog identity/provenance, exact selected instant, displayed facts, DEMO DATA disclosure, and daily precision remained fixed during both swaps; debug counters stayed at 1 coordinator request and 0 source load calls; timeline scrubs worked before and after; the successful stale-candidate supersession test passed. Evidence: .codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/baseline.ox30pack, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/device-world-probe-log.txt, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-before-switch.txt, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-after-switch.txt, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-lifecycle-log.txt, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/bundled-ready.png, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/chooser.png, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/installed.png, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/returned.png, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-before-swap.png, .codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-after-swap.png.

## Verification actually performed

- python scripts/dev.py workflow — passed at ACTIVE/CLOSEOUT with OX-32 DONE and no active slice.
- python scripts/dev.py build — passed; debug build installed on Pixel 3 XL serial 8AKY0M32X.
- python scripts/dev.py contract — passed; transport contract OK.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.ui.CatalogWorldChoiceTest.successfulOlderInstalledCandidateCannotReplaceNewerBundledChoice — passed: 1 test, 0 failures, 0 errors.
- python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/167-ox32-installed-theme-hot-swap/baseline.ox30pack — passed; content identity sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.
- Installed-device free → installed-compatible → free screenshots and scoped timeline/lifecycle/counter checks passed; exact records are in .codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md.
- git diff --check — passed after implementation, plan, evidence, and slice closeout edits.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/167-ox32-installed-theme-hot-swap/`. Archived plan: `.codex/history/plans/167-ox32-installed-theme-hot-swap.md`. See the plan for stage checkpoints and exact boundaries.
