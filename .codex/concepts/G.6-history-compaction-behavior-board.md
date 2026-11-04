# G.6 behavior board — closed workflow history compaction (draft)

**Visual flowchart:** `G.6-history-compaction-behavior-board.svg` (editable `.dot`; `feh` review `.png`). The flowchart is the primary board; this document records its detailed content boundaries.

**Status:** Product-owner approved on 2026-11-04 after reviewing the visual flowchart in `feh` on display `:0`; no additional constraints stated. Documentation only; no UI artwork.

## Content boundary

| Input | Decision | Output |
|---|---|---|
| 49 DONE leaves in Build Tooling, G, P.1, P.2, ATM, DFM, WA | Confirm DONE closeout and each lasting rule in applicable authority. If either is missing, preserve the leaf intact and return a revised proposal. | Compact leaf with stable ID, title, status, order, dependency, concise outcome and exit, board/approval reference, exact history and evidence links. |
| Four specified stale open-plan files | Confirm each closed or explicitly superseded cycle, successor/closeout, and references. | Content-preserved relocation to `.codex/history/plans/drafts/`; fix only the superseded DFM.4.6 draft's relative successor link. |
| Active, unfinished, deferred, and unresolved material | Protect without status inference. | Keep current pointer, unfinished leaves, WX/SG details, WX.27 parked plan, WX.10 fixture, authority, and evidence in place. |

## Decision flow

```text
Approved board and activated bounded plan?
  no  -> leave all proposed compaction targets unchanged
  yes -> audit each DONE leaf for closeout, recovery links, and lasting rules
            missing authority or changed scope -> preserve leaf; return revised proposal
            complete -> reduce only approved fields
         verify each stale plan's closed/superseded successor and references
            failed -> preserve file; record limitation
            passed -> relocate content, repair approved relative link
         validate references, preserved statuses/order, workflow, and final diff
         close cycle with actual results and limits
```

## Acceptance boundaries

- The proposal and this board were approved separately on 2026-11-04. Board approval does not itself activate the plan.
- The contradictory old WA.5 NEXT note and SG.7 dependency on deferred SG.6 remain for separate owner resolution.
- The pre-existing modification to `.agents/skills/compact-history/SKILL.md` is protected and excluded from G.6.
- No app source, build, tests, test artifacts, specifications, or other roadmap leaves change in this slice.

**Approved scope refinements:** The owner approved preserving BT.1 intact, then WA.3 intact, after their unpromoted continuing rules were found. The visual flow audits the original 49 candidates; 47 eligible entries were reduced.
