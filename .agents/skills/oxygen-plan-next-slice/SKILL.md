---
name: oxygen-plan-next-slice
description: Analyze the next eligible Oxygen Time Lab roadmap slice and draft its bounded PLANNED cycle. Does not activate or implement the plan.
---

# Plan the next Oxygen Time Lab slice

1. Read `AGENTS.md`, `docs/SPECIFICATION.md`,
   `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/current.md`,
   `.codex/README.md`, and run `python scripts/dev.py workflow`.
2. If `.codex/current.md` points to a PLANNED or ACTIVE cycle, work with that
   cycle or report why a new plan cannot start. Otherwise select the sole NEXT
   eligible slice and verify its dependencies and exit criteria.
3. Create one bounded plan under `.codex/plans/` with scope, invariants,
   ordered steps, acceptance, authorized verification, evidence paths, risks,
   and exclusions. Use `python scripts/codex_cycle.py start` when the roadmap
   slice and identifier are ready; edit and review the generated plan before
   activation.
4. Run the workflow validator and leave the cycle PLANNED. Do not activate or
   implement unless the user asks to continue.

Resolve routine planning choices from repository authority. Ask the user only
when a material product, safety, architecture, or scope decision cannot be
resolved from existing instructions. Planning alone does not close the
roadmap slice.
