# Persistent experiment record

Same development discipline as oxygenWX: scoped specification, ordered roadmap, one open cycle pointer, bounded plans, stage checkpoints, closed history, actual evidence not guessed screenshots.

- `current.md`: `IDLE`, `PLANNED`, `ACTIVE`, or `PAUSED` pointer. New-format pointers include the cycle ID, ordered slice roster, active slice (or `None`), stage, and plan. A paused pointer also records its resume state.
- `plans/`: unresolved plans and checklists.
- `history/`: completed authoring cycles with evidence and limits.
- `test-artifacts/`: device captures/logs kept locally, normally gitignored.

Keep roadmap slice IDs and cycle IDs separate. A slice ID is the stable roadmap
outcome (for example, `P.2.9`); a cycle ID is the unique, chronological
execution record (for example, `021-p2-acceptance-closure`). A cycle has an
ordered roster of one or more slice IDs; every slice keeps its own approved
boards, dependencies, exit, verification authorization, status, outcome, and
evidence. Assign the next cycle number when planning work, regardless of slice
numbers or roadmap position. Adding or splitting a slice does not reserve,
skip, or renumber cycle IDs. Store the plan, evidence, and closed history under
the cycle ID.

Cycle stage and lifecycle state are separate. Stages are `SLICE`, `BOARDS`,
`DRAFT`, `REVIEW`, `EXECUTE`, and `CLOSEOUT`. Save a checkpoint at every stage
boundary with the cycle/slice IDs, prior state, accepted boards and gates,
decisions/open questions, completed outputs, evidence, exact next-stage read
set, and next action. `PAUSED` preserves the same cycle and roster plus its
`Resume state`; resume at the saved stage without repeating accepted gates.
Only one slice is `ACTIVE` at a time. Activation requires a final plan and
approved boards for every included slice. Use `python scripts/dev.py workflow`
to validate pointer, plan, checkpoint, dependency order, and roadmap status.

Routine work reads the current stage's read set from its skill and checkpoint.
Read full authorities, unrelated roadmap sections, closed plans, and history
only when a named scope, dependency, conflict, regression question, or audit
requires them. Keep source material and evidence recoverable.

Use `python scripts/dev.py workflow` to inspect and validate the cycle context.
