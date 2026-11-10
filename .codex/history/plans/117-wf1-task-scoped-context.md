# Cycle plan — 117-wf1-task-scoped-context

State: CLOSED
Plan maturity: FINAL
Cycle ID: `117-wf1-task-scoped-context`
Roadmap items: WF.1
Objective: Make workflow stages use task-scoped context and one clear skill entry point per stage, and let a cycle preserve ordered slices and resume from any saved stage without losing approved work or repeating completed gates.

## Board approval and plan readiness

| Board | Path | Status and relationship to scope |
|---|---|---|
| Behavior | `.codex/concepts/WF.1-task-scoped-context-behavior-board.svg` (editable `.dot`, PNG preview, detailed `.md`) | Product owner approved the board as shown on 2026-11-07; no additional constraints. It governs the IDLE-to-close workflow, skills mapped to stages, support for multiple dependency-ordered slices in future cycles, and pause/resume checkpoints without regression. |
| UI artwork | Not applicable | This is a workflow documentation and cycle-tooling change; no application UI or visual output changes. |

The behavior-board gate is met; UI artwork is not applicable because this slice changes workflow documentation and cycle tooling only. WF.1 remains the sole slice in cycle 117. The multi-slice capability applies to future cycle rosters; it does not attach unrelated roadmap work to cycle 117. The owner invoked `oxygen-execute-plan`, which authorized cycle 117 activation; on 2026-11-07 the plan was activated with WF.1 as its only active slice.

## Cycle roster

Cycle 117 contains only WF.1. Ordered multi-slice plans are supported for future cycles; this cycle does not absorb other roadmap work.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "117-wf1-task-scoped-context",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "WF.1",
      "depends_on": [],
      "status": "DONE",
      "exit": "Operational, planning, plan-review, ambiguity-resolution, and implementation stages each have an explicit minimal read set and targeted escalation rule. A cycle can pause at any stage boundary and resume from a durable checkpoint with the same cycle/slice IDs, accepted gates, outputs, approvals, and evidence; pause does not fail or downgrade the roadmap slice, reset progress, or require repeated work. Slice-specific work no longer requires routine full-roadmap, unrelated-specification, closed-plan, or all-skill loading; completed material remains recoverable and is read on demand for a named dependency, conflict, or audit. One canonical skill entry point is unambiguous per stage, and obsolete conflicting instructions are retired or clearly superseded. Plans carry compact stage handoffs with objective, decisions, invariants, acceptance, authorized checks, exact read set, evidence paths, and next action. Existing authority and scope, board approval gates, test authorization, verification, and evidence obligations remain intact. Record before/after input-token counts with a compatible tokenizer where available; otherwise record byte/line proxies without claiming exact token savings. Workflow, resume/status, reference, and `git diff --check` validation pass.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WF.1-task-scoped-context-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": ".codex/concepts/WF.1-task-scoped-context-behavior-board.svg",
              "sha256": "6f1fdbb5be74501009506fd69ccd2f2273c57e710f2ff3ea7a839764e119baf3"
            },
            {
              "path": ".codex/concepts/WF.1-task-scoped-context-behavior-board.dot",
              "sha256": "90720b9a9954757d04196dbfb540e72a1824907211fa2f68122052d88890c97a"
            },
            {
              "path": ".codex/concepts/WF.1-task-scoped-context-behavior-board.md",
              "sha256": "f6c719782a70effb298f5e4563df8ab0b708675ba5cab224c45b9d4420411ef6"
            },
            {
              "path": ".codex/concepts/WF.1-task-scoped-context-behavior-board.png",
              "sha256": "916e23256aa5c113ed9a1e5a5067b2eeee4ac8146515c3011daf50acf82e3d8d"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "reason": "No application UI or visual output changes are in scope."
        }
      },
      "outcome": "WF.1 exit met: cycle roster/checkpoint/validator lifecycle, task-scoped stage guidance, consolidated skill entry points, focused cycle-transition/workflow-validator checks, and byte/line context evidence are complete. No Android behavior changed.",
      "evidence": [
        ".codex/test-artifacts/117-wf1-task-scoped-context/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-07",
    "summary": "WF.1 is complete. The repository now has task-scoped workflow guidance, consolidated stage entry points, an ordered multi-slice cycle model with durable pause/resume checkpoints and approved-board fingerprints, and workflow validation in the developer command. The focused lifecycle suite and workflow checks pass; read-set evidence records byte/line proxies without claiming token savings. No Android behavior changed.",
    "verification": [
      "python scripts/dev.py workflow — PASS in ACTIVE / CLOSEOUT with WF.1 DONE and no active slice.",
      "python -m unittest discover -s scripts/tests -v — PASS, 14 focused cycle-transition and workflow-validator tests.",
      "python scripts/codex_cycle.py --help — PASS; lifecycle command set is available.",
      "git diff --check — PASS after the final tracked source, documentation, and checkpoint edits.",
      "close_cycle post-write validate_workflow — PASS; resulting IDLE pointer validated."
    ],
    "evidence_directory": ".codex/test-artifacts/117-wf1-task-scoped-context/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

The saved stage is `CLOSEOUT`. The activation gate retained WF.1's behavior-board approval and the reviewed plan. The WF.1 exit and evidence are recorded; the closeout read set covers the current pointer and plan, the roadmap contract and WF.1 entry, the execution skill, and the evidence summary.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "117-wf1-task-scoped-context",
  "slice_ids": [
    "WF.1"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/WF.1-task-scoped-context-behavior-board.svg",
    ".codex/concepts/WF.1-task-scoped-context-behavior-board.dot",
    ".codex/concepts/WF.1-task-scoped-context-behavior-board.md",
    ".codex/concepts/WF.1-task-scoped-context-behavior-board.png"
  ],
  "completed_gates": [
    "behavior_board_approved",
    "final_plan_reviewed",
    "cycle_activated",
    "focused_cycle_transition_and_workflow_validator_tests_passed",
    "workflow_validation_passed_at_closeout",
    "cycle_cli_help_passed",
    "final_diff_reviewed",
    "git_diff_check_passed"
  ],
  "decisions": [
    "Cycle 117 remains a single WF.1 slice; multi-slice membership is for future cycle plans."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Owner-approved WF.1 behavior board recorded.",
    "WF.1 plan finalized and activated.",
    "Implemented ordered cycle rosters, stage checkpoints, pause/resume, board fingerprints, validator integration, and canonical stage skills.",
    "Closed slice WF.1 as DONE; its approved exit is met.",
    "14 focused cycle-transition/workflow-validator tests passed.",
    "Workflow validation, CLI help, final diff review, and git diff --check passed at closeout.",
    "Before/after routine read-set byte/line evidence recorded without token or percentage claims.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/WF.1-task-scoped-context-behavior-board.md",
    ".codex/test-artifacts/117-wf1-task-scoped-context/README.md",
    ".codex/test-artifacts/117-wf1-task-scoped-context/context-measures.md",
    ".codex/history/117-wf1-task-scoped-context.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/117-wf1-task-scoped-context.md",
    ".codex/test-artifacts/117-wf1-task-scoped-context/README.md",
    "docs/ROADMAP.md",
    "docs/ROADMAP_SPECIFICATION.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md"
  ],
  "next_action": "Archive cycle 117 through the cycle tracker; the close action validates the resulting IDLE pointer."
}
```
<!-- cycle-checkpoint:end -->

## Authority and verified baseline

- `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, `.codex/README.md`, and `.codex/current.md` control the repository lifecycle. `docs/ROADMAP.md` defines the current slice and its ordering. The board approval does not override these authorities; this plan includes the authority updates needed for the approved workflow.
- Before activation, the pointer was `PLANNED`, cycle `117-wf1-task-scoped-context`, roadmap item `WF.1`, plan `.codex/plans/117-wf1-task-scoped-context.md`. Execution began at `ACTIVE` / `EXECUTE`; it is now at `ACTIVE` / `CLOSEOUT` with WF.1 recorded `DONE`. WF.1 remains cycle 117's only slice while it builds multi-slice capability for future cycles. Do not attach unrelated work to cycle 117.
- Before WF.1, `docs/ROADMAP_SPECIFICATION.md` said a cycle names one roadmap slice. The owner-approved board permits a cycle to contain one or more stable roadmap slice IDs, ordered by actual dependencies. WF.1 updated this contract, its records, and lifecycle tooling together while retaining WF.1 as cycle 117's only slice.
- Before WF.1, `scripts/codex_cycle.py` supported `start`, `activate`, and `close`; it recorded one `Roadmap item` and had no stage or pause/resume checkpoint. `python scripts/dev.py workflow` checked only required scaffold paths and printed the current pointer.
- There are ten installed skills totaling 747 lines / 44,242 bytes. Three stage pairs overlap: `slice-select` / `oxygen-plan-next-slice`, `review-planned-draft` / `oxygen-review-plan`, and `execute-plan` / `oxygen-execute-plan`. The three unprefixed entries total 180 lines / 9,553 bytes. These are byte/line baselines, not token counts or promised savings.
- Before consolidation, duplicate skill names also appeared in this plan and the owner-approved WF.1 board package. Those board references remain as the approved planning snapshot; they are not live invocation guidance. Active skill directories and instructions now have one canonical entry per stage. Closed history, including cycle 109's historical invocation, remains unchanged.
- G.6 already compacted its approved historical scope. WF.1 changes stage read policy and skills; it does not compact history, relocate evidence, or repeat G.6.
- The active runtime's context-window size and compatible tokenizer are not established in this plan. Do not claim a 65% estimate or exact token reduction without measuring them.
- Owner authorization received on 2026-11-07: add and run focused cycle-transition tests covering the cycle lifecycle and workflow validator. This does not authorize app tests or unrelated test coverage.

## Scope

1. Define the minimal read set and escalation rule for operational/status handling, planning (slice intake, board preparation, and first draft), plan review, ambiguity resolution, execution, and closeout. General questions remain in `IDLE` without allocating a cycle. Read full authority or historical records only when a named scope, dependency, conflict, regression question, or audit requires them.
2. Consolidate the overlapping planning, review, and execution pairs into one clear `oxygen-*` entry point per stage. Transfer each duplicate's useful project-compatible requirements before removing the unprefixed directory. Keep distinct specialist skills separate and invoke them only when their specific task applies.
3. Update roadmap and cycle records so one cycle may contain one or more stable roadmap slice IDs. Record their order, dependencies, per-slice board approvals, exits, evidence, and the one currently executing slice. Execute eligible slices in dependency order; close each slice only on its own exit. Keep the cycle ID distinct from every slice ID.
4. Add a durable checkpoint at every workflow boundary. The checkpoint records cycle and slice IDs, current stage, prior cycle state, accepted boards and gates, decisions and open questions, completed outputs, evidence, exact next-stage read set, and next action. Add a reversible `PAUSED` state and resume the same cycle and slice set at the saved stage. A pause does not fail or close work, downgrade roadmap status, allocate replacement IDs, discard approvals, or require completed gates to be repeated.
5. Update `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, `.codex/README.md`, cycle plan/current-pointer conventions, the three canonical stage skills, applicable specialist references, `scripts/codex_cycle.py`, and `scripts/dev.py workflow` only as needed to make the above contract consistent.
6. Record before/after context measures by workflow stage. Use a compatible tokenizer only when available; otherwise record exact bytes/lines as proxies and make no exact-token or percentage claim.

## Proposed workflow and state contract

- Starting an actionable request from `IDLE` creates one chronological cycle record and begins at slice intake. Ordinary conceptual conversation does not allocate a cycle.
- A cycle plan has an ordered roster of one or more stable roadmap slice IDs. Each slice retains its own objective, dependencies, board package and approval, exit, verification authorization, and evidence. A combined board is acceptable only when it makes the covered behavior for each listed slice reviewable.
- At most one roadmap slice is `ACTIVE` at a time. Later cycle members remain `PLANNED` until eligible. When the current slice meets its exit, the next eligible member may proceed under the same cycle ID. The cycle closes only after all included slices are completed or their exact incomplete/blocker outcomes are recorded.
- Keep cycle lifecycle state separate from workflow stage. `IDLE` means no open cycle; `PLANNED` covers slice/board/draft/review work; `ACTIVE` covers implementation; `PAUSED` records the prior `PLANNED` or `ACTIVE` state plus the saved stage. On resume, restore that prior state, the same cycle ID, and the same active slice. A pause during planning leaves the roadmap frontier unchanged; a pause during execution leaves the active roadmap slice `ACTIVE`.
- Stage values are `SLICE`, `BOARDS`, `DRAFT`, `REVIEW`, `EXECUTE`, and `CLOSEOUT`. Starting a cycle begins at `SLICE`. Advance only after saving the output of the current stage. If approved behavior changes, reopen only the affected board/plan gate; otherwise preserve accepted work.
- If implementation reaches a material gap, stop the dependent work, save the open question and last safe stage, and ask the owner interactively. Resume after the answer and update only affected artifacts. A deliberate pause or pending owner answer is not a failure. A genuine external/dependency blocker still follows the roadmap specification's `BLOCKED` and closeout rules.
- The cycle tracker and workflow validator must reject inconsistent pointers, missing checkpoint fields, duplicate slice IDs, cycles with invalid dependency order, and activation before every in-scope slice has its required approved board and finished plan. Keep previously valid single-slice cycle records readable.

## Canonical skill composition

| Stage | Canonical entry | Requirements to retain or incorporate | Retirement / specialist policy |
|---|---|---|---|
| Slice intake and first draft | `oxygen-plan-next-slice` | Retain current-cycle protection, eligible-slice/dependency inspection, evidence-based fact vs assumption notes, bounded scope/exclusions, and the approved-board-before-final-plan gate. Extend intake to several related slices with stable IDs and real dependency order. | Remove `slice-select` after its useful requirements and live references are reconciled. Keep model fit in `rate-plan` rather than forcing model research into every routine draft. |
| Draft review and finalization | `oxygen-review-plan` | Retain board approval, architecture/scope/compatibility, documentation and migration review, explicit acceptance/evidence, owner-decision handling, and a revise-until-clear loop. Import the context-pressure rule through `audit-planned-context` when a split may be needed. `AGENTS.md` authorization controls: do not add or run tests without explicit user authorization. | Remove `review-planned-draft` after transferring its unique compatible requirements. Keep `resolve-plan-ambiguity`, `audit-planned-context`, and `rate-plan` as targeted specialists, not mandatory routine reads. |
| Execution and closeout | `oxygen-execute-plan` | Retain current-pointer inspection, activation only after final plan/board gates, strict scope, exact authorized verification, installed evidence for visual slices, final diff review, and honest closeout. Add stage checkpoint/pause/resume and ordered multi-slice progression. | Remove `execute-plan` after transferring its useful requirements. Keep `compact-history` separate; its own explicit approval gate still applies to history compaction. |

Before retiring any entry, compare its full instructions with the canonical skill, reconcile frontmatter/metadata and live references, and verify that no unique requirement was lost. Keep cycle history unchanged for provenance. Do not add redirect skills that would recreate ambiguous competing entry points.

## Stage-scoped context policy

| Stage | Routine read set | Escalate only when |
|---|---|---|
| Operational/status | `.codex/current.md`, `.codex/README.md`, and `python scripts/dev.py workflow`; do not allocate a cycle for a general question. | The pointer is inconsistent, or the request is actionable and needs the roadmap frontier or a named authority section to determine next steps. |
| Slice intake | `AGENTS.md`; `.codex/current.md` and `.codex/README.md`; the relevant roadmap specification sections and eligible roadmap frontier/dependencies; `python scripts/dev.py workflow`. | The idea touches product semantics, a named dependency, a conflict, or a prior owner decision; then read the exact governing section/record. |
| Boards and first draft | The selected slice text, its approved/draft board package, the relevant authority sections, and only directly affected current implementation/interfaces when needed to bound work. | A board gap, contract mismatch, or required compatibility question needs another named source. |
| Plan review | Current cycle plan and board approval records, relevant authority passages, direct dependencies, and only the files needed to substantiate the proposed work. | Invoke ambiguity/context/rating specialists only for their named task. Do not routinely read the whole roadmap, all skill files, closed plans, or history. |
| Ambiguity resolution | The exact open question, relevant plan section, approved board constraint, and the smallest governing authority passage needed to resolve it. | Read a named dependency or prior decision only if those sources leave the ambiguity unresolved; ask the owner when it is a genuine decision rather than a repository-grounded gap. |
| Execution | Current pointer, finalized plan, approved boards, exact governing authority named by the plan, affected files, and directly relevant verification/evidence. | Stop and ask on a material gap or decision; inspect additional sources only for that question. |
| Closeout | Plan exits, actual authorized results/evidence, and only the roadmap entries/current-state records being closed. | Read historical material only when needed to substantiate a dependency, conflict, or audit. |

Keep all authority and evidence recoverable. Narrow routine reads; do not delete or summarize unfinished work, decisions, requirements, or required evidence.

## Steps

1. Compare all ten skill instructions and metadata; capture live references and unique requirements. Confirm the approved board package and current pointer still match cycle 117 / WF.1.
2. Implement the cycle/slice roster, status, checkpoint, dependency, activation, and closeout contracts from the approved workflow and roadmap authority. Keep WF.1 as cycle 117's only slice; use multi-slice membership for future cycle plans. No owner decision remains open for this contract.
3. Update the project workflow instructions and canonical planning/review/execution skills with the stage read sets and contracts above. Remove the three superseded skill directories only after their requirements and live references are reconciled. Keep the four distinct specialist skills and historical records intact.
4. Extend the cycle tracker and workflow validator for ordered multi-slice cycle rosters and reversible stage checkpoints. Preserve single-slice compatibility and stable IDs; make pause/resume explicit state transitions.
5. Walk the lifecycle from `IDLE` through slice intake, boards, first draft, review, activation, one or more dependency-ordered slices, pause/resume at each stage, and closeout. Confirm accepted outputs/gates survive each checkpoint and unrelated roadmap/history text is not required.
6. For each workflow stage, record the baseline and final routine read-set file paths and byte/line counts using the same representative task scope. Record compatible-tokenizer counts only if the runtime exposes a compatible tokenizer; otherwise report byte/line proxies without token or percentage claims. Run the workflow validator and `git diff --check`; inspect the complete diff and record exact limitations.

## Acceptance and evidence

- Each operational, planning, plan-review, ambiguity-resolution, and implementation stage has an explicit minimal read set and targeted escalation rule. No stage routine requires all skills, full closed history, or unrelated roadmap/specification prose.
- The three duplicate pairs have been reconciled without losing useful requirements. Their stale directories and live invocation references are removed; history remains intact. Distinct specialist skills remain discoverable and conditional.
- The roadmap and cycle model represent one or more stable slice IDs in a single cycle, preserve dependency order, retain per-slice approvals/exits/evidence, and activate no more than one slice at a time. Existing single-slice records remain valid.
- A cycle can pause at each defined stage and resume with the same cycle/slice IDs, state, approved boards, completed gates, outputs, decisions, evidence, read set, and next action. No completed gate is repeated unless its approved input changed or evidence contradicts it. Pause does not fail or downgrade a roadmap slice.
- Cycle creation, activation, multi-slice progression, pause, resume, and closeout have consistent records and the workflow validator accepts valid states and rejects malformed pointers/checkpoints.
- Before/after routine read sets are recorded per workflow stage using the same representative task scope and exact file lists. Report exact tokens only when measured with a compatible tokenizer; otherwise label byte/line counts as proxies and make no token-savings or percentage claim.
- `python scripts/dev.py workflow` and `git diff --check` pass; the final diff contains no application behavior changes. No app build, lint, install, or visual/device acceptance applies.
- The owner explicitly authorized adding and running focused cycle-transition and workflow-validator tests after the execution-time authorization gate was raised. No app tests or unrelated tests are authorized.
- Evidence targets: `.codex/test-artifacts/117-wf1-task-scoped-context/README.md`, `.codex/history/117-wf1-task-scoped-context.md`, and `.codex/history/plans/117-wf1-task-scoped-context.md`.

## Risks and assumptions

- Cycle 117 is a single-slice WF.1 cycle. Multi-slice membership is a capability implemented for future planned cycles; do not convert the current record into a different task roster.
- Adding pause/resume and multi-slice data to a small tracker affects roadmap, pointer, and plan semantics together. Preserve existing single-slice records and validate the new state transitions before claiming the exit.
- A compatible tokenizer may remain unavailable. Use the same per-stage read-set measurement method before and after, label byte/line counts as proxies, and never infer exact token savings or a percentage.
- The `PAUSED` state represents an intentional checkpoint or an owner answer pending. A genuine external or dependency blocker remains governed by existing `BLOCKED` rules.
- The approved workflow resolves the cycle/slice contract: cycle 117 stays single-slice, while future cycles may list multiple dependency-ordered slices. The context-measurement fallback is also resolved by the roadmap rule: use a compatible tokenizer if available, otherwise byte/line proxies.

## Out of scope

- App source/UI changes; Android tests, builds, lint, installation, screenshots, or device review.
- App tests or tests outside the explicitly authorized cycle-transition and workflow-validator coverage.
- Compacting G.6 or any other closed history, deleting evidence, modifying old cycle records, or changing product specifications/acceptance.
- Claiming pause/resume behavior is implemented before the cycle-transition checks and workflow validator establish it.
