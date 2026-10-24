---
name: slice-select
description: Analyze the next eligible roadmap slice and create a verified first-draft plan without implementing it.
---

# Select and draft a roadmap slice

Identify the sole NEXT eligible slice in `docs/ROADMAP.md` and prepare a broad
first-draft plan. Do not implement the slice or activate its plan.

## Read and inspect

Read `AGENTS.md`, `docs/SPECIFICATION.md`,
`docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/current.md`,
`.codex/README.md`, and run `python scripts/dev.py workflow`. Inspect only the
implementation, tests, and history needed to verify objective, dependencies,
architecture, invariants, validation, and evidence requirements.

If a current PLANNED or ACTIVE cycle already exists, do not replace it. If the
roadmap's next item is blocked by a material owner decision, state the decision
and its consequence instead of silently choosing a scope.

## First-draft plan

Capture the slice ID, objective, scope and exclusions, verified architecture,
affected areas, dependency order, compatibility needs, authorized validation,
evidence, risks, and completion criteria. Separate verified facts from
assumptions. Keep implementation detail at draft depth; do not redesign the
slice while planning.

Create the cycle through `python scripts/codex_cycle.py start` when the roadmap
item and identifier are settled. Review the generated plan, run workflow, and
leave it PLANNED. Do not activate or edit app code.

## Difficulty and model fit

Rate implementation difficulty from 1–10 and briefly justify it by breadth,
coupling, uncertainty, and verification burden. Recommend the least costly
available Codex model with a credible path to acceptance, and name a fallback
and escalation trigger. Do not infer API model availability from Codex model
availability. When pricing or runtime billing materially matters, verify
current official information; otherwise state that only relative model fit is
being compared.
