---
name: resolve-plan-ambiguity
description: Resolve repository-grounded ambiguity in the current Oxygen Time Lab PLANNED cycle without weakening its scope, acceptance, or verification obligations.
---

# Resolve ambiguity in the current plan

This is a planning specialist. Do not implement app or workflow behavior, activate
the cycle, or close it.

## Routine context

1. Read `.codex/current.md`, the referenced plan's open question and checkpoint,
   its approved board constraint, and run `python scripts/dev.py workflow`.
2. Read only the smallest governing authority passage or dependency record
   needed to answer the named question. Inspect code, tests, specifications, or
   history only when the question depends on that evidence. Do not routinely
   load the full roadmap, unrelated specifications, closed plans, history, or
   other skills.
3. Require the cycle to be `PLANNED` at `DRAFT` or `REVIEW` and point to the
   plan being resolved. If it is `IDLE`, `ACTIVE`, `PAUSED`, malformed, or points
   elsewhere, report the state without editing another plan.

## Resolution

1. State the ambiguity and its consequence. Classify the hotspot as
   repository-resolvable ambiguity, repository-resolvable unknown, intrinsic
   implementation complexity, or an owner decision.
2. Resolve routine choices from direct repository evidence. Separate verified
   facts from assumptions. For a repository-grounded resolution, write the
   smallest contract needed for ownership/layer boundaries, state transition,
   input/output semantics, validity/missingness, compatibility, verification,
   or escalation when applicable.
3. Do not choose among materially different product, architecture, safety, or
   scope outcomes without owner authority. Leave the exact question and
   consequence visible in the plan checkpoint and stop dependent planning.
4. Change only affected plan/board passages. Preserve scope, intended outcome,
   invariants, acceptance, authorized checks, evidence requirements, and
   exclusions. Do not turn difficult required work into a follow-up to make the
   plan easier.
5. Record the decision, supporting paths, completed output, and exact next
   stage read set/action in the checkpoint. If the plan is now clear, clear only
   the resolved open question; preserve all unrelated accepted gates and
   decisions.

Run `python scripts/dev.py workflow` after edits. Leave the cycle `PLANNED`;
do not activate or close it.

## Expectation-preservation check

Before finishing, compare the changed plan with its prior version and confirm
that production boundary, observable outcome, invariants, acceptance,
verification authorization, evidence, and exclusions are intact. Support every
resolved choice with repository evidence and keep every unresolved owner
decision visible. Report the open question and its consequence when resolution
requires the owner.
