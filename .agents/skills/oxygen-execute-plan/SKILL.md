---
name: oxygen-execute-plan
description: Carry out the current ACTIVE Oxygen Time Lab cycle through dependency-ordered slices, authorized verification, durable checkpoints, evidence, and closeout.
---

# Execute the current bounded cycle

Follow the user request, `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, the
current plan and checkpoint, approved boards, and only the authorities needed
for the active slice.

## Routine context and state

1. Read `.codex/current.md`, `.codex/README.md`, the referenced plan and its
   accepted boards, the exact governing authority named by the plan, affected
   interfaces/files, and directly relevant verification/evidence. Run
   `python scripts/dev.py workflow` and inspect `git status --short` before
   editing so existing workspace changes remain intact. Do not routinely read
   all specifications, the full roadmap, unrelated plans, or closed history.
2. If the matching plan is `PLANNED`, activate only after the final plan and
   every in-scope slice's approved-board gates pass. If it is `PAUSED`, resume
   the same cycle and roster at the saved stage. If it is already `ACTIVE`,
   continue its current slice. Do not create a replacement cycle.
3. Execute only the active slice's approved scope, invariants, acceptance, and
   exclusions. Do not delegate unless the user or applicable repository
   instructions explicitly authorize it.

## Checkpoints and owner decisions

At every stage boundary, save the cycle/slice IDs, stage and prior cycle state,
accepted boards/gates, decisions and open questions, completed outputs,
evidence, exact next-stage read set, and next action. Keep the current pointer,
cycle manifest, and checkpoint consistent. Pause/resume preserves identifiers,
roadmap status, approved gates, and completed outputs.

If implementation exposes a material scope, behavior, architecture,
compatibility, or acceptance gap, stop the dependent work. Record the exact
question and last safe stage in the checkpoint, pause the same cycle, and ask
the owner with three concrete resolution options and recommend the one that
best matches the approved criteria. After the answer, update only affected
plan/board records; reopen a prior gate only when its approved input changed
or evidence contradicts it. If a board package changes, record owner approval
again with `approve-board`, review the affected plan relationship, resolve the
checkpoint question, then resume the same cycle. A pending answer is not a
failed cycle or a fabricated external blocker.

## Slice execution and verification

- Execute roster slices in dependency order. Only one slice is `ACTIVE` at a
  time. Close each slice only when its own exit is met or its exact incomplete
  outcome is recorded. Activate the next eligible slice under the same cycle
  ID; do not repeat completed gates.
- Run only checks required by the plan and authorized by the user. Do not add
  or run tests unless the user asks. Record exact commands and results.
- For UI work, install and capture the actual device states required by the
  plan. A build or preview is not installed visual evidence. Report device,
  accessibility, font, RTL, and motion conditions that were not checked.
- Preserve actual evidence under `.codex/test-artifacts/<cycle-id>/`.

## Closeout

When every included slice is `DONE` or has a recorded terminal incomplete
outcome, advance to `CLOSEOUT`. Inspect `git diff --check` and the complete
final diff. Run `python scripts/dev.py workflow` and other exact authorized
checks. Close through `python scripts/codex_cycle.py close`, recording every
slice outcome, verification result, evidence path, limit, and follow-up. Never
mark a slice `DONE` or a cycle complete when its exit is unmet.
