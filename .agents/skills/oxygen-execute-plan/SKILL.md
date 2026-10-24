---
name: oxygen-execute-plan
description: Carry out the current ACTIVE Oxygen Time Lab plan through its bounded work, required verification, evidence capture, and cycle closeout.
---

# Execute the active Oxygen Time Lab plan

Follow the user's request, `AGENTS.md`, `docs/ROADMAP_SPECIFICATION.md`, the
active plan, and applicable product/UI authorities.

1. Read `docs/SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`,
   `docs/ROADMAP.md`, `.codex/current.md`, the referenced plan, and relevant
   authority documents. Run `python scripts/dev.py workflow`.
2. If the matching cycle is PLANNED, activate it with
   `python scripts/codex_cycle.py activate`. Continue only when that plan is
   ACTIVE. Do not create a replacement plan during execution.
3. Execute only the plan's scope and exclusions. Do not delegate unless the
   user or applicable instructions explicitly authorize it. Keep any authorized
   work bounded, inspect all changes, and retain responsibility for integration.
4. Run only the checks authorized by the user and required by the plan. For UI
   work, install and capture actual device states required by the UI workflow;
   report any device, accessibility, font, RTL, or motion condition not checked.
5. Preserve evidence under `.codex/test-artifacts/<cycle-id>/`, inspect
   `git diff --check` and the final diff, then close through
   `python scripts/codex_cycle.py close`. Record what passed, what did not run,
   evidence paths, limits, and follow-up. Never claim an unmet exit as DONE.
