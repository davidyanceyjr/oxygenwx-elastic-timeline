# Cycle 122-mr2-main-readout-raise-compact

Date: 2026-11-09
Roadmap items: MR.2

## Outcome

MR.2 completed the approved center-readout-only refactor: the halo is removed, the approved regular and compact hierarchy is installed, supplied bearings show the wind-travel arrow, and an existing stored SIMULATED catalog/time demonstrates the neutral glyph in regular and compact portrait. Evidence and limitations are recorded in the cycle README. No non-center elements or behavior were assessed, and no fixtures were created or changed.

## Slice outcomes

- **MR.2 — DONE:** Installed regular and compact center readouts meet the approved center-only hierarchy and preserve the selected data inputs. Supplied bearings display the wind-travel arrow and the existing stored SIMULATED state at 2026-10-28T14:00:00Z displays the neutral glyph at both viewport sizes. The location pin action/48 dp target was unchanged in source; no interactions or non-center elements were assessed. See the evidence README for input, captures, observations, and limits. Evidence: .codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md, .codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-supplied-bearing.png, .codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-supplied-bearing.png, .codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-night-supplied-bearing.png, .codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-night-supplied-bearing.png, .codex/test-artifacts/122-mr2-main-readout-raise-compact/unavailable-bearing.png, .codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-unavailable-bearing.png.

## Verification actually performed

- python scripts/dev.py workflow — passed at CLOSEOUT.
- git diff --check — passed.
- python scripts/dev.py build — passed after the final center-readout implementation.
- python scripts/dev.py run --serial 8AKY0M32X — passed; debug build installed after the final compact-position adjustment.
- python3 scripts/dev.py screenshot --serial 8AKY0M32X — passed for all six regular/compact supplied-bearing and unavailable-bearing captures listed in the evidence README.
- Automated tests, lint, and contract checks were not run under this plan.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/122-mr2-main-readout-raise-compact/`. Archived plan: `.codex/history/plans/122-mr2-main-readout-raise-compact.md`. See the plan for stage checkpoints and exact boundaries.
