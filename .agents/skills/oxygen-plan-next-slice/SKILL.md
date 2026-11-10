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
   more related stable slice IDs when a shared bounded objective benefits from
   dependency-ordered execution. Keep unrelated outcomes in separate cycles.
   Put in-cycle dependencies earlier in the roster; resolve external
   dependencies before activation. Record verified facts separately from
   assumptions.
3. Start a cycle shell at stage `SLICE` with
   `python scripts/codex_cycle.py start --id <cycle-id> --slice <slice-id> ...`
   only after the eligible slice roster is clear. The generated plan is a
   draft shell, not an implementation-ready plan.
4. Prepare an objective-specific behavior board for every slice and a UI
   artwork board for every slice that changes UI or visual output. A combined
   board may cover both only when each slice's flow and appearance are
   reviewable. Obtain product-owner approval before drafting the substantive
   plan. Record each path, approval, constraints, and relation to scope and
   acceptance in the cycle manifest. A prompt, target path, existing screen, or
   screenshot alone is not an approved board.
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
Use `rate-plan` when the owner requests difficulty or model fit; keep that
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
