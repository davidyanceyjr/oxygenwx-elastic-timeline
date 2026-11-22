---
name: oxygen-plan-next-slice
description: Intake one or more related roadmap slices, prepare approved boards, and draft a bounded Oxygen Time Lab cycle without activating or implementing it.
---

# Plan Oxygen Time Lab work

Use this entry point for slice intake, board preparation, and first-draft
planning. General questions and concepts stay in `IDLE`; they do not allocate a
cycle.

## Routine context

1. Read `.codex/current.md`, `.codex/README.md`, and run
   `python scripts/dev.py workflow`.
2. Read `AGENTS.md`, the relevant `docs/ROADMAP_SPECIFICATION.md` rules, and
   only the eligible roadmap frontier and its dependencies in `docs/ROADMAP.md`.
3. Read product, architecture, prior-decision, or historical sources only when
   the proposed slice touches their semantics or a named dependency/conflict.
   Inspect implementation and tests only to verify a concrete boundary.

## Slice intake and boards

1. If the pointer is `PLANNED`, `ACTIVE`, or `PAUSED`, continue that cycle or
   explain why another cycle cannot start. Never replace an open cycle. Resume
   a paused cycle at its saved stage and preserve accepted work.
2. From `IDLE`, identify the eligible `NEXT` work. One cycle may group one or
   more related stable slice IDs only when they share one bounded objective.
   Keep unrelated outcomes in separate cycles. Order the roster by
   dependencies: the first slice is the sole eligible `NEXT`, later slices are
   `PLANNED`, in-cycle dependencies appear earlier, and external dependencies
   are resolved before activation. Each slice keeps its own acceptance and
   exit. Record verified facts separately from assumptions.
3. Start a cycle shell at stage `SLICE` with
   `python scripts/codex_cycle.py start --id <cycle-id> --slice <first-id>
   --slice <later-id> ... --objective "<shared bounded objective>"` only after
   the eligible slice roster is clear. Repeat `--slice` once per roster entry
   in dependency order; keep a single-slice cycle when work has one outcome and
   use separate cycles for unrelated outcomes. The generated plan is a draft
   shell, not an implementation-ready plan.
4. Use `oxygen-build-slice-boards` to prepare an objective-specific behavior
   board for every slice and a UI artwork board for every slice that changes
   UI or visual output. A combined
   board may cover both only when each slice's flow and appearance are
   reviewable. Obtain product-owner approval before drafting the substantive
   plan. One shared package may cover several slices only when it makes each
   slice's behavior and acceptance independently reviewable. The owner reviews
   the complete package once; then run `approve-board` separately for every
   covered slice, supplying the same complete `--file` list and approval
   details so each roster entry stores its own approval and fingerprints.
   Every slice retains its own dependencies, exit, acceptance, authorization,
   status, outcome, and evidence. Record each path, approval, constraints, and
   relation to scope and acceptance in the cycle manifest. A prompt, target
   path, existing screen, or screenshot alone is not an approved board.
   Author behavior boards in Graphviz DOT; retain the `.dot` source and render
   both SVG and PNG files with `dot -Tsvg` and `dot -Tpng`. UI artwork boards
   may use SVG or PNG to suit the artwork; keep both when they have distinct
   source and review roles. Before asking for approval, show the rendered board
   with `feh` on the shared X display by setting `DISPLAY=:0` explicitly, for
   example `DISPLAY=:0 feh --auto-zoom path/to/board.svg`. Use the same display
   path for other non-terminal visual artifacts created for user review. After
   any board edit, regenerate affected renders and show the updated visual.
   After the owner approves, use `python scripts/codex_cycle.py approve-board`
   with every file in the approved package, the slice ID, board kind, approver,
   and date. This records content fingerprints; it does not create or imply
   owner approval.
5. Save the `SLICE` output, then advance to `BOARDS`; save the approved board
   package before advancing to `DRAFT`. Use `python scripts/codex_cycle.py
   advance` with the exact next-stage read set and next action at each boundary.

## First-draft plan

Draft only from the approved boards. Record per-slice scope, exclusions,
dependencies, invariants, observable exit, authorized verification, evidence,
risks, and compatibility/documentation work. Keep the cycle roster ordered.
Use `rate-plan` when the owner requests a difficulty rating; keep that
assessment out of routine intake.

The plan and checkpoint carry a versioned JSON cycle roster and workflow
handoff. Keep them synchronized with `.codex/current.md`. The checkpoint names
the stage, prior cycle state, accepted boards/gates, decisions/open questions,
completed outputs, evidence, exact next-stage read set, and next action.

Run `python scripts/dev.py workflow` after the draft changes. Advance to `REVIEW`
only when every in-scope board is approved and the plan is complete. Leave the
cycle `PLANNED`; do not activate or implement it. If a material owner decision
remains, record it as an open question and stop dependent planning. Planning
alone does not close roadmap work.
