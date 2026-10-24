# Cycle plan — 022-experience-spec-adoption

State: CLOSED
Roadmap: G.3
Objective: Adopt the verified Oxygen experience specification as the experience-level companion to the prototype specification; record the live-tree discrepancy report and fix stale documentation references without changing application behavior.

## Production boundary

May create/update only:

- `AGENTS.md` authority references.
- `docs/SPECIFICATION.md` cross-reference to the adopted companion document.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` adoption status/authority record only; preserve its requirements and candidate labels.
- `docs/ROADMAP.md` G.3 status/evidence reference.
- `README.md` stale descriptions of removed controls and localized focus-day expansion.
- `.codex/current.md`, this plan, `.codex/test-artifacts/022-experience-spec-adoption/`, `.codex/history/022-experience-spec-adoption.md`, and `.codex/history/plans/022-experience-spec-adoption.md` for cycle lifecycle/evidence/closeout.

## Functional invariants

- No application source, behavior, or tests change in this documentation adoption cycle.
- Preserve current uniform hourly scale, selected timestamp, timeline bounds, main-display ±24-hour swipes, double-tap return-to-now, DEMO DATA semantics, accessibility routes, and production integration exclusions.
- Preserve historical cycle records and claims as written.
- Distinguish established source behavior from installed/device acceptance; do not claim new visual or interaction acceptance.
- Keep candidate enhancements and production gates as proposals requiring separately bounded decisions.

## Steps

- [x] Verify the two new documents, workflow state, authorities, and current working tree.
- [x] Compare the proposed specification with live source and README references; record discrepancies before adoption.
- [x] Adopt the document with explicit authority relationship and update only stale related documentation.
- [x] Run workflow/contract/documentation checks, inspect `git diff --check` and final diff, then capture exact results.
- [x] Close cycle with evidence and limitations.

## Acceptance/evidence

The proposal is clearly marked adopted as an experience-level companion, while `docs/SPECIFICATION.md` retains precedence for prototype scope and existing hard invariants. Record the baseline discrepancy report in `.codex/test-artifacts/022-experience-spec-adoption/README.md`, including source locations inspected and README stale claims. Run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `git diff --check`; inspect final diff. No Android build, test, install, screenshot, or UI acceptance is part of this documentation cycle.

## Risks / assumptions

The user's instruction to execute the Adoption prompt is owner authorization to adopt the proposal. The cited public GitHub snapshot may lag this local tree; local source is authoritative for the discrepancy report. Contract check is the project's available code/structure contract check and may not validate prose quality.

## Out of scope

Application changes, tests, lint, Android build/install/device review, revisiting TalkBack, changing gesture behavior, production integration, candidate UX enhancements, rewriting historical records, and implementing the proposed UX.1–UX.5 sequence.

## Closeout

Closed with documentation checks only. No application behavior was changed or visually re-accepted. Future implementation cycles must select appropriate acceptance scenarios from the adopted experience specification; candidate enhancements and production integration still require their own bounded owner decisions.
