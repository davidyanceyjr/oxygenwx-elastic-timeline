---
name: audit-planned-context
description: Audit a PLANNED Oxygen Time Lab cycle for execution context pressure and preserve all acceptance obligations if the work must be split.
---

# Audit planned context

Planning-only specialist. Do not activate a cycle, edit application behavior,
or claim an unmeasured context estimate.

## Routine context

1. Read `.codex/current.md`, `.codex/README.md`, the referenced plan and its
   ordered slice roster/checkpoint, the relevant roadmap entries/dependencies,
   applicable authority passages, and run `python scripts/dev.py workflow`.
2. Require the cycle to be `PLANNED` at `DRAFT` or `REVIEW`. If it is `IDLE`,
   `ACTIVE`, `PAUSED`, inconsistent, or references another plan, leave all
   records unchanged and report the state.
3. Read code, tests, boards, or history only when they are in the plan's named
   execution read set or needed to establish a concrete dependency, boundary,
   verification cost, or prior decision. Do not routinely load the full
   roadmap, every specification, all skills, closed plans, or history.

## Estimate

Establish the model and available input context window from the plan or active
environment. Estimate execution use for each active slice, including required
authorities, implementation, tests/authorized checks, likely debugging,
integration, documentation, and evidence. Count input and generated tokens
against the same window. Use a compatible tokenizer when available. If the
window or tokenizer cannot be established, state that limitation and do not
invent a percentage or exact-token estimate.

When every slice is at or below 65%, record model/window, estimates, percentage,
basis, date, and `PASS — no split required` in the plan and checkpoint. Keep the
roadmap order unchanged and run `python scripts/dev.py workflow`.

## Split only when required

If a slice exceeds 65%, split at the smallest real implementation boundary and
preserve every original invariant, exit, verification obligation, and evidence
requirement. Prefer dependency-ordered leaf slices that can be independently
completed and verified. Record each slice's context estimate, scope,
dependencies, outcome, board package, exit, verification, evidence, risks, and
exclusions.

- Keep related slices in the same cycle roster only when they share a bounded
  objective and can be executed in real dependency order. One cycle keeps one
  unique cycle ID and lists stable roadmap slice IDs; do not create several
  cycle plans that reuse one numeric ID.
- Use separate cycles for unrelated work or when separate activation/closeout
  is required. Allocate each cycle ID once, in chronological order.
- Give every new slice its own approved behavior board and required UI artwork
  board before finalizing the revised plan. A split plan remains `PLANNED` and
  unactivated until the owner-approved boards and all plan gates are recorded.
- Do not silently change roadmap order or move difficult work into a later
  slice merely to meet the threshold. If no coherent split fits, leave the plan
  unchanged and report the coupling for owner review.

Preserve the original plan as audit input or keep an explicit before/after
record. Do not replace accepted work or repeat completed gates. Update only the
affected roster, roadmap entries, plan, and checkpoint, then run workflow
validation and `git diff --check`.

## Response

Report the model/window basis, estimates and threshold result, whether a split
occurred, exact affected plan/roadmap/current records, board approval needs,
verification performed, and remaining uncertainty. Keep token counts separate
from byte/line proxies; never convert proxies into token savings or percentages.
