# Cycle plan — 023-atmosphere-spec-intake

State: CLOSED
Roadmap: G.4
Objective: Ingest OXY-ATM-001 as a subordinate architecture proposal, reconcile current source/specs, and add bounded ATM/ENV roadmap slices without changing app behavior.

## Production boundary

May create/update only:

- `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md` adoption status and current-tree/baseline reconciliation.
- `docs/SPECIFICATION.md` authority cross-reference.
- `docs/ROADMAP.md` G.4 closeout and leaf slices ATM.0–ATM.7 / ENV.1–ENV.5.
- `.codex/current.md`, this plan, `.codex/test-artifacts/023-atmosphere-spec-intake/README.md`, `.codex/history/023-atmosphere-spec-intake.md`, and `.codex/history/plans/023-atmosphere-spec-intake.md` for cycle lifecycle/evidence/closeout.
- No application source, test source, README, or original oxygenWX files.

## Functional invariants

- Preserve the existing authority order: `docs/SPECIFICATION.md` controls prototype scope and hard invariants; the adopted experience spec supplies experience criteria; OXY-ATM-001 remains subordinate and cannot authorize production integration or app implementation by itself.
- No application behavior, source, fixtures, or tests change in this documentation cycle.
- Preserve the fixed-playhead shuttle, uniform-hourly long press, central ±day swipe, double-tap Now, accessible forecast browser, and DEMO DATA semantics in all proposed slices.
- Atmosphere/environment rendering remains decorative and noninteractive; no fabricated weather inputs, provider/network/location work, or gesture changes.
- Preserve historical cycle records and state installed acceptance limitations honestly.

## Steps

- [x] Read the project authorities, current cycle state, and applicable plan/history records; run `python scripts/dev.py workflow`.
- [x] Inspect the working tree and all six cited source paths; compare those paths with the exact OXY-ATM-001 baseline commit.
- [x] Record the adoption boundary, current-tree findings, and non-conflicting owner-decision disposition in the proposal and controlling-spec cross-reference.
- [x] Add ordered ATM/ENV leaf slices in the required format and record the baseline evidence gap.
- [x] Run documentation-appropriate checks, inspect the final diff, and capture evidence.
- [x] Close the cycle and reconcile the roadmap status to DONE.

## Acceptance/evidence

The architecture proposal is explicitly subordinate to existing authorities and is adopted for bounded roadmap planning only. The discrepancy report names every inspected source path and reports whether it differs from baseline `b8477987ca8272da64a1f579177174efed387c96`. Proposed ATM/ENV work uses stable slice IDs and required leaf fields, with ATM.0 accounting for the incomplete pre-engine installed baseline. Run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `git diff --check`; inspect the final diff. Target evidence is `.codex/test-artifacts/023-atmosphere-spec-intake/README.md` and `.codex/history/023-atmosphere-spec-intake.md`. No Android tests, build, install, screenshot, or new visual acceptance is in scope.

## Risks / assumptions

The user's instruction to execute the supplied intake prompt authorizes recording OXY-ATM-001 as a subordinate planning proposal. It does not override the controlling prototype scope or authorize atmospheric implementation. The local app source matches the proposal's cited Git baseline in the requested paths. Existing installed captures are useful pre-engine references but do not cover every proposed sky/scene state or performance metric; ATM.0 remains NEXT until its specified installed baseline is recorded.

## Out of scope

Application code/behavior, tests, Android build/install/device checks, visual acceptance claims, changes to the original oxygenWX, production integration, pinch gestures, provider/location/alert work, and activation/planning of any second cycle.
