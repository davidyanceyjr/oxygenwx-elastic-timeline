# Cycle 128-p2-hour-tick-label-size

Date: 2026-11-11
Roadmap items: P.2.27

## Outcome

P.2.27 is complete. The owner-selected 20 dp active-catalog and 16 dp fixture/debug hourly labels were installed on Pixel 3 XL 8AKY0M32X and visually reviewed in the required hourly, focused, noon, midnight-before/after, and daily states. Labels remain readable at their ticks, the fixture/debug cue remains distinct and bottom anchored, the weekday advances across local midnight, and daily mode shows no hourly labels. Fresh installed screenshots and observations are preserved under .codex/test-artifacts/128-p2-hour-tick-label-size/. The previously uncommitted P.2.26 changes and artifacts were preserved.

## Slice outcomes

- **P.2.27 — DONE:** Installed the approved 20 dp active-catalog and 16 dp fixture/debug labels on Pixel 3 XL 8AKY0M32X. Captured and visually reviewed all planned hourly, noon, focused 6 AM, midnight-before/after, and daily states. Labels fit beneath the long ticks, remain distinct from focused cues, and no hourly labels appear in daily mode. Fixture/debug weekday changes from Saturday to Sunday across local midnight; its nearest-hour cue remains as specified. No out-of-scope accessibility or environment matrix was attempted. Evidence: .codex/test-artifacts/128-p2-hour-tick-label-size/README.md, .codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-hourly-labels-20dp.png, .codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-noon-12pm.png, .codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-before-12am.png, .codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-after.png, .codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-daily-reference.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-hourly-labels-16dp.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-focused-6am.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-noon-12pm.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-before-12am.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-after-12am.png, .codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-daily-reference.png.

## Verification actually performed

- python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; streamed install Success; MainActivity launched.
- python3 scripts/dev.py workflow — passed after resume and during CLOSEOUT.
- git diff --check — passed during CLOSEOUT after reviewing the tracked final diff.
- python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/128-p2-hour-tick-label-size/<capture>.png — succeeded for all 11 final active-catalog and fixture/debug targets listed in README.md; captures were visually inspected.
- No automated tests or lint were run, per plan. RTL, TalkBack, accessibility-wide, reduced-motion, other-font-scale, other-device, and broad system/environment acceptance were not performed; these remain outside P.2.27.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/128-p2-hour-tick-label-size/`. Archived plan: `.codex/history/plans/128-p2-hour-tick-label-size.md`. See the plan for stage checkpoints and exact boundaries.
