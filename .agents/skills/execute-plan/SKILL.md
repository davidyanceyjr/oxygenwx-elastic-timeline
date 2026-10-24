---
name: execute-plan
description: Execute the current bounded Oxygen Time Lab plan within its approved scope, record evidence, and close the cycle when its exit is met.
---

# Execute a plan

Use the current plan as the execution boundary. Follow the user's request,
`AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, the product specification, and
applicable workflow guidance.

1. Read `.codex/current.md`, the referenced plan, applicable authority, and
   inspect the working tree. Run `python scripts/dev.py workflow`.
2. Confirm the cycle is PLANNED. Review the scope and acceptance criteria, then
   activate it with `python scripts/codex_cycle.py activate` before making
   planned changes. If there is no matching PLANNED cycle, stop and report the
   state instead of creating a replacement plan during execution.
3. Work only within the plan. Do not delegate unless the user or applicable
   instructions explicitly authorize delegation. If authorized, split work only
   at independent, reviewable boundaries and retain integration ownership.
4. Run only verification authorized by the task and required by the plan. For
   visual work, use the installed app and actual device screenshots; do not
   infer visual acceptance from a build or preview.
5. Preserve evidence under `.codex/test-artifacts/<cycle-id>/`. Inspect the
   final diff and run `git diff --check`.
6. Close completed work with `python scripts/codex_cycle.py close`, recording
   exact results, evidence paths, unverified boundaries, and follow-up. If the
   exit is unmet, record the failure or blocker without marking the slice DONE.
