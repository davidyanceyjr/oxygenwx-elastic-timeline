# WF.1 behavior board — workflow lifecycle and skill handoff

**Visual board:** `.codex/concepts/WF.1-task-scoped-context-behavior-board.svg` (editable `.dot`; PNG preview alongside). This board follows the owner-provided workflow clarification from 2026-11-07 and maps stages to the repository's installed skills.

**Status:** Product-owner approved on 2026-11-07 as shown; no additional constraints. This is workflow documentation, so no UI artwork board applies.

## Target lifecycle

| Stage | Target behavior and output | Existing skill binding |
|---|---|---|
| IDLE | Handle general questions, concepts, and future ideas without pretending an execution cycle is active. | No dedicated skill is needed for ordinary conversation. |
| Idea → slice(s) | Write leaf slices in the roadmap's required format. Several task ideas may produce several slices. Split large outcomes into focused slices, give them stable `WF.*` IDs for workflow work, and order only real dependencies. | `oxygen-plan-next-slice` and `slice-select` overlap; both currently focus on selecting the sole NEXT slice rather than batch intake. |
| Slice → boards | Give every slice an objective-specific behavior/state board; add UI artwork when the slice changes UI or visual output. Obtain owner approval before creating its first plan draft. | Planning skills currently combine slice selection, board preparation, and plan drafting. Their board timing should be aligned to this owner-specified order. |
| Approved boards → first draft | Draft the bounded plan from the approved boards, with scope, dependencies, invariants, exit, authorized checks, and evidence paths. | `oxygen-plan-next-slice` / `slice-select` overlap. |
| Draft → finished plan | Review, resolve repository-grounded ambiguity, and revise in a loop until the plan has no avoidable gaps. Keep unresolved owner decisions explicit. Some implementation discovery may remain for execution. | `oxygen-review-plan` / `review-planned-draft` overlap; `resolve-plan-ambiguity`, `audit-planned-context`, and `rate-plan` are specialist skills. |
| Finished plan → execute | Activate and follow the bounded plan. If a material gap appears, stop the dependent work, ask the owner an interactive question, record the answer, update the plan if needed, then resume. Re-review boards/plan if the answer changes approved behavior or scope. | `oxygen-execute-plan` / `execute-plan` overlap. |
| Review and close | Review scoped checks and evidence. Fix failed checks that are within the active scope and rerun only authorized verification. Close as DONE only when the slice exit is met; otherwise record the exact failure/blocker and follow-up. Return the workflow to IDLE after closeout. | Execution skills include closeout. `compact-history` applies only to a separately authorized history-compaction task. |

## Pause and resume without regression

The owner may pause the same cycle at any stage boundary and resume later from its saved checkpoint. A checkpoint records the cycle ID and slice ID, current stage, completed outputs and board approvals, decisions and unresolved questions, actual evidence, exact read set, and the next action.

- Pausing does not fail or close the cycle, downgrade the roadmap slice, discard approved work, allocate a replacement cycle ID, or require accepted gates to be repeated.
- Resume the same cycle and slice at the saved stage. Treat already approved boards and completed stages as accepted unless an approved input materially changes or new evidence contradicts them.
- Reopen only the affected earlier stage when that change or contradiction requires it; preserve unaffected approvals, outputs, and evidence.
- A pause at a stage boundary is not a failure or blocker. A genuine unresolved dependency discovered during work still follows the normal blocker rules.

This checkpoint behavior requires workflow instructions and state validation to represent a resumable `PAUSED` cycle while retaining the roadmap slice's `ACTIVE` status. It is target behavior for the WF.1 draft; it is not implemented yet.

## Slice and cycle shape

The owner-requested target permits one cycle to contain multiple focused slices, with dependencies implemented first. The example cycle and `EX.*` IDs in the visual board illustrate that grouping; they do not change cycle `117`'s current WF.1 roster. A multi-slice cycle proceeds in dependency order, with only eligible slices active.

There is an authority mismatch to resolve before adopting that data model: `docs/ROADMAP_SPECIFICATION.md` currently defines a cycle as one bounded execution that names one slice, while `audit-planned-context` describes sequential partial plans under a shared numeric prefix. The reviewed WF.1 plan includes the bounded authority, pointer, plan/evidence/history, and lifecycle-tool changes needed for future multi-slice cycle grouping. Cycle `117` itself remains a single WF.1 slice. This board does not imply those changes are already implemented.

## Context behavior

- Load only the current stage's plan, approved boards, exact dependencies, and linked authority needed for the decision.
- Keep completed roadmap prose, closed plans, and old evidence recoverable; open them when a named dependency, conflict, regression question, or audit requires them.
- Preserve authority, approval gates, current state, scope, verification permissions, acceptance, and evidence obligations when reducing reads.
- The skill mapping exposes current overlapping entry points: planning (`oxygen-plan-next-slice` / `slice-select`), plan review (`oxygen-review-plan` / `review-planned-draft`), and execution (`oxygen-execute-plan` / `execute-plan`). WF.1 must reconcile their unique requirements before declaring one canonical entry per stage.
- `resolve-plan-ambiguity`, `audit-planned-context`, `rate-plan`, and `compact-history` have distinct specialist purposes; they are not substitutes for the main stage skills.

## Relationship to the WF.1 draft plan

The visual board represents the owner's approved target lifecycle and skills as well as WF.1's stage-specific context policy. The reviewed plan keeps cycle `117-wf1-task-scoped-context` limited to WF.1 while including the changes required to support multi-slice rosters in future cycles. The cycle-grouping mismatch is part of WF.1's approved scope and must be resolved before the capability is adopted; do not add unrelated slices to the current cycle.
