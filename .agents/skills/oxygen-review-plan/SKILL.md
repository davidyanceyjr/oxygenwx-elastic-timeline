---
name: oxygen-review-plan
description: Review and complete the current Oxygen Time Lab PLANNED cycle draft; leave it unactivated and unimplemented.
---

# Review the current Oxygen Time Lab plan

1. Read `AGENTS.md`, `docs/SPECIFICATION.md`,
   `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/README.md`,
   `.codex/current.md`, and its referenced plan. Run
   `python scripts/dev.py workflow`.
2. Confirm the current cycle is PLANNED and the plan matches the roadmap slice.
   If the state or pointer is inconsistent, report that without replacing the
   plan or activating it.
3. Check the plan against dependencies, exit criteria, product/architecture/UI
   invariants, scope, verification permissions, and evidence needs. Resolve
   routine gaps from repository authority; leave genuine owner decisions
   explicit.
4. Make the plan execution-ready with a bounded objective, boundary,
   invariants, ordered steps, acceptance criteria, evidence paths, risks, and
   exclusions. For visual work, specify installed states and applicable font,
   viewport, RTL, and reduced-motion conditions.
5. Run `python scripts/dev.py workflow` again. Leave the plan PLANNED and report
   its path and any unresolved decision. Do not implement unless asked.
