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
   explicit. Verify that an objective-specific behavior board and, for
   UI/visual work, a UI artwork board exist and have product-owner approval
   recorded with their constraints and scope relationship. A combined board
   may cover both when clear. If required board content or approval is missing,
   leave the plan as an unfinalized draft and identify the blocker; do not mark
   it ready for activation.
4. Make the plan execution-ready with a bounded objective, boundary,
   invariants, ordered steps, acceptance criteria, evidence paths, risks, and
   exclusions. For visual work, specify installed states and applicable font,
   viewport, RTL, and reduced-motion conditions.
5. Run `python scripts/dev.py workflow` again. If the approved board gate is
   met, leave the reviewed plan PLANNED and ready for activation; otherwise
   leave it an unfinalized draft and report the missing board/approval. Do not
   implement unless asked.
