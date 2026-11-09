# Cycle 121-mr1-main-readout-refactor

Date: 2026-11-08
Roadmap items: MR.1

## Outcome

MR.1 main readout refactored to approved gold-arc hero with source-backed apparent temperature and transparent Precip/Wind/Humidity row. Removed solar/lunar event lines and high/low from main readout only. Scene orbit and timeline implementation unchanged. Installed supported, second-time, unavailable, and compact states captured under .codex/test-artifacts/121-mr1-main-readout-refactor/.

## Slice outcomes

- **MR.1 — DONE:** Installed main readout matches the approved hero and transparent three-value row at supported, second-time, missing-value, and compact states; focused values and replay contract passed scoped tests. Scene orbit and timeline implementation unchanged. Lint remains blocked by three unrelated debug picker errors. Evidence: .codex/test-artifacts/121-mr1-main-readout-refactor/README.md.

## Verification actually performed

- Workflow, contract, build, run, git diff --check, six individually run scoped methods, and installed readout observations passed. Lint failed on three unrelated StateFlowValueCalledInComposition errors in debug CatalogPickerDelegate.kt lines 146, 152, and 204. No RTL, TalkBack, motion, large-font, multi-device, or full-suite acceptance claimed. Evidence: .codex/test-artifacts/121-mr1-main-readout-refactor/README.md.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/121-mr1-main-readout-refactor/`. Archived plan: `.codex/history/plans/121-mr1-main-readout-refactor.md`. See the plan for stage checkpoints and exact boundaries.
