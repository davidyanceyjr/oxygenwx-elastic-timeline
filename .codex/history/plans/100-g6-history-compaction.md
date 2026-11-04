# Cycle 100 — G.6 closed workflow history compaction

**State:** CLOSED. Owner approved the final 47-entry scope on 2026-11-04, preserving BT.1 and WA.3 intact. Board approved; revised scope approved by the product owner on 2026-11-04: leave BT.1 and WA.3 intact and compact the other 47 eligible DONE entries.
**Cycle ID:** `100-g6-history-compaction`
**Roadmap slice:** G.6 — Compact closed workflow history
**Current pointer:** `.codex/current.md` returned to IDLE at closeout.

## Objective and authority

Carry out only the owner-approved 2026-11-04 compaction proposal after this objective-specific behavior board receives separate product-owner approval. Governing material: `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, and the `compact-history` skill. Planning check: `python scripts/dev.py workflow` reported scaffold OK and IDLE on 2026-11-04. Cycle 099 is the latest closed cycle.

## Board gate

Behavior board: `.codex/concepts/G.6-history-compaction-behavior-board.svg` (owner approved on 2026-11-04 as displayed in `feh` on `:0`; no additional constraints stated; editable `.dot`, review `.png`, and detailed `.md` at the same stem). Its flowchart shows approval, authority, recovery, preservation, and validation decisions. No UI or visual change is planned, so no UI artwork board applies. **Scope decision:** The pre-activation authority audit found a lasting BT.1 rule absent from durable authority. The owner approved leaving BT.1 and WA.3 intact and compacting the other 47 eligible DONE entries. WA.3 retains the still-current exact-16-ID test-maintenance note. No authority promotion is authorized.

## Bounded work after activation

1. Recheck status, authority, references, closed-cycle records, and the protected pre-existing skill edit. Record a before snapshot of roadmap line/byte counts and the four source plan paths.
2. Audit each of the 47 specified DONE leaves (BT.1 and WA.3 excluded) for lasting rules already present in applicable authority. If a missing rule requires promotion, preserve its entry and return a revised proposal before changing scope.
3. Condense only eligible DONE leaves in G, P.1, P.2, ATM, DFM, and WA. Keep each ID, title, DONE status, order, dependency, concise outcome and exit, board/approval reference, and exact history/evidence links. Leave BT.1, WA.3, unfinished leaves, and WX/SG completed detail intact.
4. Move only `.codex/plans/002-p1-pixel-install.md`, `draft-dfm44.md`, `draft-dfm45.md`, and `draft-dfm46.md` to `.codex/history/plans/drafts/`, preserving content. Correct the DFM.4.6 draft's relative successor link. Confirm its final archived plan and all cited closeouts remain accessible.
5. Validate roadmap statuses, dependency order, exact recovery paths, plan references, and unchanged active/deferred records. Preserve `.codex/plans/073-wx27-release-boundary.md`, `.codex/plans/051-wx10-sequence-fixture.md`, authority files, and `.codex/test-artifacts/`.
6. Record actual evidence under `.codex/test-artifacts/100-g6-history-compaction/`, inspect `git diff --check` and complete final diff, then close to `.codex/history/` with exact results and limitations.

## Exit and evidence

The G.6 roadmap exit applies. Record before/after line and byte counts as context-size proxies, a per-entry authority/recovery audit, relocation/reference checks, exact command results, and any preserved entries. Evidence targets: `.codex/test-artifacts/100-g6-history-compaction/`, `.codex/history/100-g6-history-compaction.md`, and `.codex/history/plans/100-g6-history-compaction.md` at closeout. These are targets, not completed results.

## Verification planned, not run

- `python scripts/dev.py workflow` before application and at closeout.
- Structural checks of all affected IDs, fields, dependencies, closeout/evidence links, and relocated plan references.
- `git diff --check` and complete final diff inspection.
- No tests, builds, lint, device, or visual acceptance in this documentation-only cycle.

## Exclusions and unresolved statuses

Do not edit app or build files, test artifacts, controlling specifications, existing skill modifications, unfinished roadmap entries, WX/SG completed details, or unrelated historical prompts. Do not resolve the old WA.5 NEXT note, SG.7's dependency on deferred SG.6, or WX.27's parked state by inference. A new authority-promotion need or changed compaction scope requires a revised proposal before application.
