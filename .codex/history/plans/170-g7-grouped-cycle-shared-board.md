# Cycle plan — 170-g7-grouped-cycle-shared-board

State: CLOSED
Plan maturity: FINAL
Cycle ID: `170-g7-grouped-cycle-shared-board`
Roadmap items: G.7
Objective: Clarify how related roadmap slices can execute in one dependency-ordered cycle with a shared board package while preserving independent slice acceptance.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "170-g7-grouped-cycle-shared-board",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "G.7",
      "depends_on": [
        "G.1",
        "G.2"
      ],
      "status": "DONE",
      "exit": "AGENTS.md, docs/ROADMAP_SPECIFICATION.md, the planning skill, and cycle CLI help consistently explain grouped rosters and shared-board use; every grouped roster entry retains its own slice-level acceptance and a verifiable approval/fingerprint record for the package that covers it; the tracker continues to accept repeated slice arguments, validate dependency order, and display the complete roster; existing single-slice records remain readable; workflow and diff checks pass.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.dot",
              "sha256": "9641ca5a11ffc77be35b9654f0e36d0db7ba243637c5e051a9d5f02be9f72e0a"
            },
            {
              "path": ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.svg",
              "sha256": "217c40c973a0041f29d52a4176a4c8359bb7e83ecc0a7fede76525ca54996854"
            },
            {
              "path": ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.png",
              "sha256": "603463bcddf77e0510936a14a798f51015c31840406348f3c6b88c84720de81a"
            },
            {
              "path": ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.md",
              "sha256": "c7ff6e603b43596b9dfd24451eab9b01cefdfdd24abcaee85aaaa0cbefa09e74"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": [],
          "reason": "G.7 changes project workflow documentation and CLI guidance; no app UI or visual output changes."
        }
      },
      "outcome": "Clarified grouped-cycle and shared-board requirements in project guidance and planning instructions, improved cycle start help, and preserved existing tracker behavior and single-slice compatibility. Workflow, CLI help, source review, and diff checks met the planned exit; tests were not authorized or run.",
      "evidence": [
        ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/README.md",
        ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/workflow.txt",
        ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/cycle-help.txt",
        ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/start-help.txt",
        ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/diff-check.txt"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Completed G.7 by clarifying related, dependency-ordered grouped cycles, preserving independent slice acceptance and per-entry approval fingerprints for shared board packages, and improving cycle start help. The approved board package remained unchanged under the owner's confirmed resolution. G.7 closed DONE; no tracker algorithms or app behavior changed.",
    "verification": [
      "python scripts/dev.py workflow — exit 0 at CLOSEOUT: Workflow scaffold: OK for cycle 170, ACTIVE/CLOSEOUT, with G.7 recorded DONE and no active slice.",
      "python scripts/codex_cycle.py --help — exit 0; start command summary describes related, dependency-ordered slices.",
      "python scripts/codex_cycle.py start --help — exit 0; output documents repeated --slice, ordering, shared-package conditions, per-slice records, and the retained --roadmap alias.",
      "git diff --check — exit 0 with no whitespace errors.",
      "Source review confirmed repeated-argument parsing, dependency validation, full-roster pointer output, per-entry board fingerprint validation, and legacy single-slice compatibility; tests/build/lint/device checks were not run because they were not authorized or in scope."
    ],
    "evidence_directory": ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "170-g7-grouped-cycle-shared-board",
  "slice_ids": [
    "G.7"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.dot",
    ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.svg",
    ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.png",
    ".codex/concepts/G.7-grouped-cycle-shared-board-behavior-board.md"
  ],
  "completed_gates": [
    "slice_intake_complete",
    "G.7_behavior_board_approved",
    "G.7 behavior board approved; UI artwork is not applicable because G.7 changes workflow documentation and CLI guidance only.",
    "Full plan review complete; plan maturity is FINAL and all board gates are satisfied.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "G.7_exit_recorded"
  ],
  "decisions": [
    "Owner explicitly confirmed the recommended resolution: honor the recorded board approval and keep its fingerprinted package unchanged."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Final closeout diff inspection complete; all tracked changes and preserved untracked cycle artifacts reviewed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/README.md",
    ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/workflow-closeout.txt",
    ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/diff-check-closeout.txt",
    ".codex/history/170-g7-grouped-cycle-shared-board.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/170-g7-grouped-cycle-shared-board.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/170-g7-grouped-cycle-shared-board/README.md"
  ],
  "next_action": "Archive the reviewed final plan and record, close cycle 170, then verify the resulting IDLE workflow pointer."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Objective

Clarify how related roadmap slices can execute in one dependency-ordered cycle with a shared board package while preserving each slice's independent acceptance, authorization, status, outcome, and evidence.

### Approved board and dependencies

The product owner approved the complete G.7 behavior package on 2026-11-22 with no additional constraints. The package consists of the DOT source, SVG and PNG renders, and Markdown contract listed with fingerprints in the cycle manifest. It governs the related-outcome grouping rule, ordered roster and dependency checks, when one package can cover multiple slices, and how a shared package's approval and fingerprints are recorded on every covered slice entry. The Markdown companion retains the review-stage banner and question as submitted; this plan and the roadmap record the approval and its resolution without changing fingerprinted inputs.

UI artwork is not applicable: G.7 changes project workflow documentation and CLI guidance, with no app UI or visual output changes.

The cycle contains only G.7. Its external dependencies G.1 and G.2 are `DONE`; there are no in-cycle dependencies.

### In scope

1. Clarify `AGENTS.md` and `docs/ROADMAP_SPECIFICATION.md`: group only related outcomes with a common bounded objective; order the roster by dependencies; preserve each slice's own acceptance and status; and allow a shared board package only when it makes every covered slice independently reviewable.
2. Clarify `.agents/skills/oxygen-plan-next-slice/SKILL.md`: repeat `--slice` for roster members in dependency order; check that the first item is the eligible `NEXT`, later items are `PLANNED`, in-cycle dependencies come first, and external dependencies are resolved; use separate cycles for unrelated outcomes.
3. Clarify board approval instructions: the owner reviews the complete shared package once; run the per-slice `approve-board` command for every covered roster entry with the same package file list, recording the same approval and fingerprints separately on each entry. Every roster entry retains its own dependency record, exit, status, authorization, and evidence.
4. Improve `scripts/codex_cycle.py` start-command help so repeated `--slice` usage, ordering constraints, and grouped-cycle scope are clear. Preserve the existing parser behavior, dependency validator, per-slice manifest, current-pointer roster display, command aliases, and single-slice compatibility.
5. Keep `docs/ROADMAP.md`'s G.7 sequencing, approved-board record, and cycle 170 evidence targets aligned with this plan; the tracker records activation and closeout status when those stages occur.

### Execution sequence

1. Update `AGENTS.md` and `docs/ROADMAP_SPECIFICATION.md` with the approved grouping and shared-package contract.
2. Update `.agents/skills/oxygen-plan-next-slice/SKILL.md` with multi-slice intake order and per-slice package approval recording.
3. Update the cycle `start` CLI help while leaving tracker algorithms and data formats unchanged.
4. Inspect the resulting help and source surfaces, run the authorized workflow and diff checks, and record exact results and limitations in the cycle evidence README.

### Exclusions

- No app implementation, app behavior, weather semantics, UI artwork, or installed visual acceptance.
- No change to the cycle manifest schema, tracker dependency or lifecycle algorithms, per-slice board approval behavior, or closed cycle records.
- No grouping of unrelated outcomes and no merged slice exits, authorization, status, or evidence.
- No new tests and no test execution are authorized by the current request. Do not run tests unless the owner authorizes them separately.
- No build, lint, device, accessibility, or broad system checks; those do not establish this documentation and CLI-help exit.

### Invariants and compatibility

- A grouped roster represents related outcomes under one bounded objective and begins at the sole eligible `NEXT` slice.
- Later roster members are `PLANNED`; in-cycle dependencies appear earlier; external dependencies are resolved before activation.
- A shared board package is permitted only when each covered slice remains independently reviewable. The same owner review may cover all represented slices, but the approval and complete file fingerprints must be recorded on each covered roster entry.
- Each slice retains its own dependencies, exit, board record, authorization, status, outcome, and evidence. Only one roster slice is `ACTIVE` at a time, and execution/closure follows dependency order.
- The existing single-slice CLI alias and cycle records remain readable; no schema migration or history rewrite is permitted.
- Tracker behavior is already represented in source: repeated `--slice` arguments are collected, dependency order is validated, the current pointer lists the full roster, and board approvals fingerprint files per slice. This slice changes help text and guidance while preserving those mechanisms.

### Acceptance mapping

| G.7 exit obligation | Planned change and scoped evidence |
| --- | --- |
| `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, and the planning skill explain grouped rosters and shared-board use consistently. | Update the three named guidance files from the approved board. Review each statement against the same rules and preserve slice-level acceptance wording. |
| Every grouped roster entry keeps its own acceptance and a verifiable approval/fingerprint record for the package that covers it. | State the per-entry rule in the authorities and planning skill. Preserve the tracker's existing per-slice board records; source-review `approve_board` and manifest validation. |
| The tracker accepts repeated slice arguments, validates dependency order, and displays the complete roster. | Clarify `start --help`; inspect the existing repeated-argument parser, dependency validator, and current-pointer roster writer. Do not change their behavior. |
| Existing single-slice records remain readable. | Keep the manifest schema and compatibility paths unchanged; inspect the existing legacy single-slice compatibility test without running it. |
| Workflow and diff checks pass. | Run `python scripts/dev.py workflow` and `git diff --check`; capture exact results in cycle evidence. |

### Verification and authorization

Authorized, non-test checks for this slice are:

- `python scripts/dev.py workflow` after the changes and at closeout.
- `python scripts/codex_cycle.py --help` and `python scripts/codex_cycle.py start --help` to inspect the published command guidance.
- Source review of the existing repeated-argument parser, dependency validation, complete roster projection, per-slice approval/fingerprint validation, and single-slice compatibility path.
- `git diff --check` and final diff inspection.

No tests have been authorized. The relevant `scripts/tests/test_codex_cycle.py` cases were read during planning but will not be added to or run without separate owner authorization; no test pass will be claimed.

### Evidence and closeout targets

Record exact commands, outputs, changed-file review, source surfaces inspected, and limitations in `.codex/test-artifacts/170-g7-grouped-cycle-shared-board/README.md`. The closeout target is `.codex/history/170-g7-grouped-cycle-shared-board.md`; archive this plan as `.codex/history/plans/170-g7-grouped-cycle-shared-board.md`. These are targets until execution and closeout create them.

### Risks and response

The main scope risk is guidance that makes a shared approval appear to merge slice outcomes. Keep the per-slice record and acceptance requirements explicit in every changed authority and check them against the approved board. Do not alter tracker behavior to make the guidance appear supported; the current tracker surfaces already provide the required per-slice records and order checks.
