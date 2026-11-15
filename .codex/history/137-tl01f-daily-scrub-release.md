# Cycle 137-tl01f-daily-scrub-release

Date: 2026-11-14
Roadmap items: TL-01f

## Outcome

Completed TL-01f. Daily scrub release preserves the exact selected local instant on active-catalog and fixture/debug surfaces, while noon representative summaries, existing boundary clamps, daily/hourly transitions, and unavailable-value semantics remain intact. The dated owner-approved specification update is recorded in both specifications. Installed acceptance evidence and limitations are preserved under the cycle artifact directory; the scoped slice is DONE.

## Slice outcomes

- **TL-01f — DONE:** Installed daily release retains the selected instant on both active-catalog and fixture/debug surfaces, preserves the SG.4 in-window missing-hour state and source-time disclosure, keeps noon representative summaries independent, preserves daily/hourly transitions, and clamps only actual out-of-catalog targets. Focused installed evidence and scope limitations are recorded in the cycle README. Evidence: .codex/test-artifacts/137-tl01f-daily-scrub-release/README.md, .codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-daily-release-1449.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-2pm.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-boundary-release.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-boundary-release.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-return-to-daily.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-before-release.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-drag-held.png, .codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-after-release-confirmed.png.

## Verification actually performed

- python scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; debug build installed and MainActivity launched on Pixel 3 XL.
- python scripts/dev.py workflow — passed at EXECUTE and CLOSEOUT; closeout validation returns the cycle to IDLE.
- git diff --check — exit 0 after scoped final diff review.
- Installed active-catalog and fixture/debug daily release, true boundary clamps on both surfaces, P.2.22 daily-to-hourly entry, hourly release and return, and SG.4 missing-hour release were observed; exact captures and UI hierarchy observations are in .codex/test-artifacts/137-tl01f-daily-scrub-release/README.md.
- No automated tests were added or run per repository authorization rules. Broad TalkBack, RTL, font-scale, reduced-motion, and system/environment checks remain outside this slice.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/137-tl01f-daily-scrub-release/`. Archived plan: `.codex/history/plans/137-tl01f-daily-scrub-release.md`. See the plan for stage checkpoints and exact boundaries.
