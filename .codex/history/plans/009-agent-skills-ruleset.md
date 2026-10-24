# Cycle plan — 009-agent-skills-ruleset

State: COMPLETE
Roadmap: G.2
Objective: Bring the reusable oxygenWX project skills and applicable agent rules into Oxygen Time Lab, adapted to this prototype's authorities, tooling, and safety boundaries.

## Production boundary

Documentation and project-local agent tooling only: `.agents/skills/**`, `AGENTS.md`, and this cycle's `.codex` records/evidence. Do not change app source or any file in the sibling oxygenWX checkout.

## Invariants

- Preserve the standalone prototype's product authority, roadmap format, Android host workflow, and gesture/architecture constraints.
- Keep user/developer authorization boundaries: do not delegate unless the user or applicable instructions authorize it; do not perform external app actions without explicit authorization.
- Keep the existing P.1.3 Android unit-test/lint plan separate and queued as cycle 010.
- Do not import production-only navigation, provider, multi-device, connected-suite, or branch-management rules as if they applied here.

## Steps

- [x] Port the ten oxygenWX repo-local skills into `.agents/skills/`, adapting paths, descriptions, lifecycle and verification commands for this repository.
- [x] Adapt `AGENTS.md` with the reusable oxygenWX lifecycle, verification, architecture, and UI-slice rules while preserving the experiment's explicit exclusions.
- [x] Validate every skill's frontmatter, directory/name agreement, metadata, and references; review for production-specific leftovers.
- [x] Run `python scripts/dev.py workflow` and `git diff --check`; record results and close the cycle.

## Acceptance/evidence

- Evidence target: `.codex/test-artifacts/009-agent-skills-ruleset/`.
- All ten source skills are represented or any omission is explicitly justified in the evidence record.
- Skills reference current project authorities and commands; no absent `docs/CODEX.md`, unsupported device fleet, or automatic delegation requirement remains.
- `AGENTS.md` agrees with `docs/ROADMAP_SPECIFICATION.md`, `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md`.
- Target history: `.codex/history/009-agent-skills-ruleset.md`.

## Risks / assumptions

- oxygenWX has local uncommitted changes; its working tree is read-only for this task.
- `.agents/skills/` is the intended repository-local discovery location, matching oxygenWX.
- Exact skill workflows may need narrowing where this prototype lacks production modules or connected-device suites.

## Out of scope

No Android app changes, tests/builds/device acceptance, changes to oxygenWX, skill installation in the user's global Codex home, or full-suite policy changes.
