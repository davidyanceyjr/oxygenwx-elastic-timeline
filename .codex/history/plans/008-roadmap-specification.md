# Cycle plan — 008-roadmap-specification

State: COMPLETE
Roadmap: G.1
Objective: Specify a lightweight bounded roadmap format and migrate every Oxygen Time Lab roadmap slice to it, preserving existing scope and evidence.

## Production boundary

Documentation only: add `docs/ROADMAP_SPECIFICATION.md`; update `docs/ROADMAP.md` and `AGENTS.md`. Do not change application source or existing product semantics.

## Invariants

- Preserve all existing roadmap scope, completed work, unresolved checks, and explicit deferrals.
- Keep `.codex/current.md`, bounded plans, history, and evidence as the cycle record; do not duplicate detailed implementation plans into the roadmap.
- Preserve pre-existing edits in `docs/SPECIFICATION.md` and `docs/ROADMAP.md`.

## Steps

- [x] Define the status vocabulary, required slice fields, exit/evidence rules, and blocker/replanning behavior.
- [x] Rewrite every leaf slice in the experiment roadmap to use the specified format and assign exactly one NEXT item.
- [x] Reference the roadmap specification in `AGENTS.md` authority.
- [x] Review all statuses/evidence references, run `git diff --check`, and record cycle evidence.

## Acceptance/evidence

- Every roadmap leaf slice has an ID, status, objective/scope, dependency or explicit none, verifiable exit, and evidence location/state.
- Deferred work has a re-entry condition and a bounded exit to use when activated.
- A single NEXT slice is identified; ACTIVE status agrees with `.codex/current.md`.
- The specification describes the conventions used by every leaf slice.

## Risks / assumptions

- This applies only to the Oxygen Time Lab roadmap; the production oxygenWX roadmap remains unchanged.
- Existing cycles 001–007 are historical; cycle 008 is the migration evidence.

## Out of scope

No production application changes, no work in the sibling oxygenWX repository, and no tests/build/device runs.
