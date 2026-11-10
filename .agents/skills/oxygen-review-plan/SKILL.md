---
name: oxygen-review-plan
description: Review and finalize the current Oxygen Time Lab PLANNED cycle from its approved boards and scoped authorities; leave it unactivated and unimplemented.
---

# Review the current Oxygen Time Lab plan

Use this entry point to turn a first draft into a complete, bounded, executable
plan. Do not activate it or implement it.

## Routine context

1. Read `.codex/current.md`, `.codex/README.md`, the referenced cycle plan,
   its approved board records, and run `python scripts/dev.py workflow`.
2. Read the relevant roadmap entries, direct dependencies, and only the
   authority passages needed to assess scope, semantics, architecture, UI,
   verification, or compatibility. Do not routinely load the full roadmap,
   all specifications, all skills, closed plans, or history.
3. Inspect only implementation interfaces and tests needed to substantiate the
   proposed boundary, compatibility, migration, and verification path.

## Review requirements

- Confirm the pointer is `PLANNED`, identifies the plan being reviewed, and
  agrees with its ordered cycle roster. If inconsistent, stop and report the
  mismatch; do not create or replace a plan.
- Check every slice's objective, in/out boundary, dependencies and order,
  architecture and product invariants, callers/consumers, compatibility or
  migration work, documentation, relevant failure paths, authorized checks,
  evidence, and observable exit. Remove speculative cases, but do not move
  required work out of scope to simplify execution.
- Verify each slice has an approved objective-specific behavior board and
  each UI/visual slice has an approved artwork board. Record owner approval,
  constraints, package-file fingerprints, and how each board governs that
  slice's scope and acceptance.
  If a required board or approval is missing, keep the plan unfinished and
  identify the gate.
- The plan must preserve test authorization: do not add or run tests unless
  the user has authorized them. Tie each implementation step to a verification
  path where practical; don't claim coverage that was not run.
- If a material decision cannot be resolved from the repository, plan, board,
  or established convention, stop dependent review and state the question and
  consequence. Use `resolve-plan-ambiguity` for focused repository-grounded
  resolution. Keep unresolved owner decisions explicit.
- Use `audit-planned-context` when a split may be needed. It is a targeted
  specialist; do not invent a context-window size or token estimate.
- Keep model difficulty and fit in `rate-plan`; they are not a routine review
  gate.

## Finalization

Revise until the plan has a clear boundary, ordered steps, per-slice approvals,
invariants, acceptance, authorized verification, evidence locations, risks,
and exclusions. Set the cycle manifest to `FINAL` only when every included
slice has its complete board/exit record. Save the finished plan at checkpoint
`REVIEW`, including accepted gates, completed outputs, decisions, exact next
stage read set, and next action.

Run `python scripts/dev.py workflow` after changes. Advance from `DRAFT` to
`REVIEW` only when the board and plan gates pass. Leave the cycle `PLANNED` and
ready for the user's authorized activation step. Do not implement.
