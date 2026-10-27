# Cycle plan — 043-wx1-successor-authority

State: COMPLETE
Cycle: 043-wx1-successor-authority
Roadmap: WX.1
Objective: Record the owner decision establishing this repository as the successor OxygenWX app and reconcile its future integration boundaries in controlling documentation, without implementing application behavior.

## Scope

Documentation and cycle evidence only. The owner approved the successor direction; amend the following files within the decisions stated by WX.1:

- `docs/SPECIFICATION.md`
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`
- `AGENTS.md`
- `README.md`
- Relevant authority, priority, and integration-boundary language in `docs/ROADMAP.md`
- `.codex/test-artifacts/043-wx1-successor-authority/` for the signed-off decision record and verification evidence

Record the future `scripts/dev.py` contract change with WX.12; do not change that script in WX.1. Preserve all completed history claims. Do not change app sources, build configuration, provider code, cycle tooling, or the production oxygenWX repository.

## Owner decision recorded

The owner authorized WX.1 and approved this repository as the successor OxygenWX app on 2026-10-26. The signed decision record captures:

- The successor product and its boundary with the deprecated oxygenWX application.
- The 44-calendar-date convention: D-30 through D-1 history and D through D+13 forecast, with D derived from the location timezone at `asOf`.
- One canonical schema and parity expectations for live, simulated, and replay datasets.
- Official alerts remain a separate future subsystem.
- Existing selected-time, timeline, accessibility, provenance, and honest-missing-data invariants that remain binding.
- WX roadmap precedence over ATM.8 as ordering only, preserving ATM.8's dependencies and exactly one NEXT slice.
- Non-goals and explicit exclusions from WX.1, including no implementation authorization and no production oxygenWX modifications.

The owner also waived WX.1 concept artwork because this is a documentation-only change. No unresolved owner decision remains for this cycle.

## Owner authorization and concept-art disposition

On 2026-10-26, the owner authorized the WX.1 documentation work and explicitly waived concept artwork because this is a documentation-only change. No concept artifact is required for this cycle. This waiver applies only to WX.1 and does not revise the general roadmap concept-art gate for other unfinished slices. Record this authorization in the cycle evidence. Concept artwork is design input, not acceptance evidence.

The owner authorization approves recording the successor direction and the constraints enumerated in this plan. The signed decision record must state those constraints explicitly. This cycle still authorizes no app implementation.

## Functional and authority invariants

- Until WX.1 is approved and closed, the existing prototype authority remains controlling.
- No application behavior, production integration, provider, location, cache, alert, or service implementation is authorized by this cycle.
- Forecast truth, missingness, source provenance, absolute selected-time authority, timeline gesture ownership, accessible alternatives, and reduced-motion meaning remain protected.
- Past Open-Meteo Forecast API samples must not be described as verified observations; official alerts remain separate from catalog conditions.
- Keep exactly one roadmap NEXT and do not turn WX's ordering priority over ATM.8 into a dependency.
- Preserve completed roadmap/history outcomes; reconcile only stale or conflicting forward-looking statements, including deprecated P.3/P.4 integration assumptions.

## Ordered steps

1. [x] Record the owner's authorization and concept-art waiver in cycle evidence, together with the successor direction and constraints enumerated in this plan.
2. [x] Capture current authority statements and conflicts in the cycle evidence, including prototype exclusions, experience-spec scope, README claims, WX delivery contract, and P.3/P.4 assumptions.
3. [x] Record the owner's successor-product decision and each decision listed above.
4. [x] Reconcile the named authority and roadmap files to the signed decision. State clearly that the WX.12 isolation-contract change is future work and WX.1 authorizes no application implementation.
5. [x] Validate roadmap ordering/status/dependency consistency and the changed documentation; record exact results and limitations in the evidence README and cycle closeout.

## Acceptance and authorized verification

All conditions are required:

- The owner-approved decision record names the successor app, date convention, live/simulation/replay parity, separate official alerts, retained interaction/data invariants, roadmap precedence, and non-goals.
- Conflicts are enumerated and reconciled in the named authorities; no predecessor navigation contract or completed history claim is silently changed.
- The future WX.12 `scripts/dev.py` contract change is recorded but not implemented.
- The roadmap has exactly one NEXT, WX.1 is closed consistently, ATM.8's dependency graph is preserved, and P.3/P.4 forward-looking assumptions are reconciled.
- No app source, build configuration, or production oxygenWX file changes.
- Record results for `python scripts/dev.py workflow`, `python scripts/dev.py contract`, and `git diff --check`; perform and record a manual changed-document/roadmap-format review. Do not run tests or claim runtime, installed, or visual acceptance for this documentation-only cycle.

## Evidence paths

- Signed decision and conflict reconciliation: `.codex/test-artifacts/043-wx1-successor-authority/successor-authority-decision.md`
- Pre-edit authority baseline: `.codex/test-artifacts/043-wx1-successor-authority/baseline-conflicts.md`
- Exact commands, roadmap-format review, concept-board approval, and limitations: `.codex/test-artifacts/043-wx1-successor-authority/README.md`
- Remaining verification logs and review artifacts: `.codex/test-artifacts/043-wx1-successor-authority/`
- Closeout after execution: `.codex/history/043-wx1-successor-authority.md`
- Archived plan after close: `.codex/history/plans/043-wx1-successor-authority.md`
No concept board is required for WX.1 per the owner's documented waiver.

## Risks and assumptions

- The successor-product decision is a material owner decision and is explicitly recorded in the cycle evidence.
- Authority edits can accidentally make future capabilities sound already implemented; use future tense and preserve explicit current-state limitations.
- WX.1 concept artwork was waived by the owner because this is a documentation-only change.
- The workflow and contract commands validate project scaffolding/isolation, not the semantic correctness of the owner decision; manual review and exact evidence remain necessary.

## Out of scope

- Any app or provider implementation, data migration, location/geocoding, persistence/cache, alert subsystem, network access, production launch, or changes to the deprecated oxygenWX repository.
- Changes to `scripts/dev.py` (reserved for WX.12), cycle tooling, completed history, or ATM.8 implementation.
- Visual/device acceptance claims or test additions/runs.
