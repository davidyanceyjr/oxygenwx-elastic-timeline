# Cycle 100 — G.6 closed workflow history compaction

**Roadmap slice:** G.6 — Compact closed workflow history
**State:** DONE — 2026-11-04
**Plan:** `.codex/history/plans/100-g6-history-compaction.md`
**Board:** `.codex/concepts/G.6-history-compaction-behavior-board.svg`, owner approved 2026-11-04 after `feh` review on `:0`.
**Evidence:** `.codex/test-artifacts/100-g6-history-compaction/README.md`, `ROADMAP-before.md`, `compacted-ids.txt`, and `authority-recovery-audit.md` in the same directory.

## Outcome

The owner approved the final 47-entry scope that leaves BT.1 and WA.3 intact because each contains a continuing rule not separately promoted to authority. The 47 eligible completed G, P.1, P.2, ATM, DFM, and WA leaves were shortened. Every leaf retained its ID, title, DONE status, position, dependency, concise outcome and exit, original board/approval line where present, and exact history/evidence references, except a stale WA.1 open-plan pointer was repaired to its archived plan. The original roadmap is preserved in cycle evidence, and all cycle closeouts and plans remain available on demand.

The four verified stale records moved from `.codex/plans/` to `.codex/history/plans/drafts/`: `002-p1-pixel-install.md`, `draft-dfm44.md`, `draft-dfm45.md`, and `draft-dfm46.md`. Their content was preserved; relative successor links in the three DFM drafts were corrected for the new directory. Final cycle plans and closeouts remain in `.codex/history/`.

No authority was promoted. Applicable continuing product and workflow rules remain in `AGENTS.md`, the controlling specifications, `docs/ROADMAP_SPECIFICATION.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md`; BT.1 and WA.3 remain in the routine roadmap because their continuing rules are not separately represented in durable authority.

## Verification and limits

- `python scripts/dev.py workflow`: scaffold OK before work and at closeout; current state IDLE after closure.
- Structural comparison against the saved roadmap: 105 slice headings and all workstream headings in the same order; all 47 compacted dependencies and original board approval lines retained; no protected leaf changed.
- All `.codex` board/evidence paths in the 47 compacted leaves exist. All Markdown links in the three relocated DFM drafts resolve.
- `git diff --check`: passed. Complete final documentation diff inspected.
- Roadmap size before: 949 lines, 184,059 bytes. After: 949 lines, 167,332 bytes; 16,727 bytes (9.1%) less. These are context-size proxies, not measured token savings.
- Tests, build, lint, and installed checks: NOT RUN, outside this documentation-only slice.
- Unresolved historical WA.5 NEXT note and SG.7 deferred dependency remain unchanged for separate owner resolution. No app-wide acceptance is claimed.
