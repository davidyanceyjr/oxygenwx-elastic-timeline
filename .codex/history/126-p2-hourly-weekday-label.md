# Cycle 126-p2-hourly-weekday-label

Date: 2026-11-10
Roadmap items: P.2.25

## Outcome

Closed cycle 126 / P.2.25 after implementing selected-weekday overlap suppression and completing installed acceptance on the active-catalog and fixture/debug timelines. Updated board packages, exact acceptance behavior, commands, captures, and remaining verification limits are archived.

## Slice outcomes

- **P.2.25 — DONE:** Implemented the selected weekday on both timeline surfaces with adjacent-text suppression only during measured label overlap. Installed captures confirm label continuity, retained midnight ticks, restoration when bounds clear, the local-midnight update, and daily return with selected-time preservation. Debug build and workflow validation passed; automated tests and lint were not authorized. Evidence: .codex/test-artifacts/126-p2-hourly-weekday-label/README.md, .codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-near-midnight-precision.png, .codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-restored.png, .codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-after-midnight-recheck.png, .codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-daily-return-11-59pm.png, .codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-away-from-midnight.png, .codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-overlap-before-midnight.png, .codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-midnight.png, .codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-restoration.png, .codex/test-artifacts/126-p2-hourly-weekday-label/fixture-daily-return.png.

## Verification actually performed

- python scripts/dev.py workflow — passed at EXECUTE and CLOSEOUT.
- python scripts/dev.py build — BUILD SUCCESSFUL in 7s.
- python scripts/dev.py run — debug app installed and launched successfully on Pixel 3 XL serial 8AKY0M32X.
- git diff --check — passed with no whitespace errors.
- Automated tests and lint — not run; plan did not authorize them.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/126-p2-hourly-weekday-label/`. Archived plan: `.codex/history/plans/126-p2-hourly-weekday-label.md`. See the plan for stage checkpoints and exact boundaries.
