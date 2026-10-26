# Cycle plan — 039-dfm1-debug-forecast-matrix

State: PLANNED
Roadmap: DFM.1
Objective: Define and owner-review the typed debug forecast scenario matrix and transition contract before fixture implementation.

## Production boundary

Documentation and cycle records only, in the standalone Oxygen Time Lab repository. Expected changes are limited to:

- `.codex/plans/039-dfm1-debug-forecast-matrix.md` (this plan)
- `.codex/current.md`
- `.codex/concepts/DFM.1-debug-forecast-matrix.md` (the annotated scenario coverage table and transition map)
- `.codex/history/039-dfm1-debug-forecast-matrix.md` and `.codex/history/plans/039-dfm1-debug-forecast-matrix.md` at closeout
- `docs/ROADMAP.md` only if the owner-approved matrix requires a bounded clarification to DFM.1/DFM.2; record any such change and rationale

No application source, tests, fixtures, dependencies, renderer, or production oxygenWX files may change in this cycle. Do not activate DFM.2 or begin fixture implementation.

## Verified baseline and functional invariants

- `python scripts/dev.py workflow` reports `Workflow scaffold: OK`; `.codex/current.md` was IDLE before this plan was started.
- DFM.1 is the sole NEXT roadmap slice and depends on completed ATM.6. DFM.2 and DFM.3 are PLANNED; ATM.8–ATM.10 depend on the debug catalog/browser as stated in the roadmap.
- The current canonical fixture model includes `SkyCondition` values CLEAR, PARTLY_CLOUDY, CLOUDY, RAIN, and STORM; `PrecipitationType` and nullable `PrecipitationAmount` are distinct typed fields. It has no production snow condition, thunderstorm-probability field, or warning field.
- Existing projection and atmosphere code keep condition, precipitation identity, generic precipitation chance, and amount distinct. A synthetic snow or warning case must be explicit debug data and clearly labeled; it must not imply provider observations or official alerts.
- The matrix is a design artifact for a later debug-only catalog. It must preserve intentionally missing values and must not change normal `SampleForecast`, selected-time authority, or forecast provenance.
- Required scenarios and coverage are bounded by DFM.1 in `docs/ROADMAP.md`: clear, partly cloudy, cloudy, rain, snow, and storm; meaningful day/night variants; dry/wet probability; explicit/missing amount; transitions; separate illustrative thunderstorm probability cases at 27% and 90%; and a separate debug-only warning state.

## Steps

- [ ] Reconcile DFM.1 requirements with current model, projection, atmosphere, and controlling semantic boundaries; record verified field types and gaps in the design artifact.
- [ ] Draft an annotated coverage table with stable scenario IDs, scenario purpose, time/condition, typed fields, provenance labels, and explicit missingness. Avoid a Cartesian-product expansion.
- [ ] Add a transition map for representative clear/cloudy/rain/snow/storm entry and clearing sequences, with expected time and condition behavior.
- [ ] Explicitly distinguish generic precipitation probability, thunderstorm probability, typed precipitation identity/amount, and the illustrative debug warning signal. State that debug warning is not an official alert.
- [ ] Review the draft against the roadmap exit and semantic invariants; capture the owner's approval and any constraints in the design artifact before closing DFM.1.
- [ ] Run `python scripts/dev.py workflow`, inspect `git diff --check`, and verify the design artifact names every required scenario, field distinction, missingness case, provenance label, transition, and owner approval. No tests or application verification are authorized or applicable.
- [ ] Record exact review evidence and any unresolved limitations in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at closeout.

## Acceptance/evidence

DFM.1 is complete only when `.codex/concepts/DFM.1-debug-forecast-matrix.md` contains the owner-reviewed scenario inventory, typed field contract, intentional missingness, provenance labels, warning/probability distinctions, and expected time/condition transitions, and records owner approval for DFM.2 to use it as the implementation contract. Verify the content against each DFM.1 roadmap requirement, run `python scripts/dev.py workflow`, and require `git diff --check` to pass. Record exact results in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at `.codex/history/plans/039-dfm1-debug-forecast-matrix.md`.

This is documentation-only work. It makes no installed, accessibility, visual, device, build, test, or runtime acceptance claim.

## Risks / assumptions

- The concept artifact is an annotated data/transition design, not scene artwork. DFM.1's roadmap names this artifact as the design gate for DFM.2.
- Candidate scenario rows can be selected and bounded using the DFM.1 coverage requirements; no owner decision to add new product signals is assumed. If a material field or warning-semantics decision is necessary, record the alternatives and consequence in the design artifact and leave that point unresolved for the owner.
- Model fit for implementation: difficulty 3/10. Scope is documentation-only and architecture coupling is limited, but the typed distinctions, missingness, and transition coverage need careful consistency review. Recommend the least-cost available Codex model with a credible path, `gpt-6-luna`; escalate to `gpt-6-sol` if it cannot reconcile the scenario inventory with the model/projection contract without inventing fields or expanding scope. This compares relative model fit only; no API availability or pricing claim is made.

## Out of scope

No debug catalog implementation, matrix browser, production fixture change, application code, scene mapping/redesign, alert behavior, provider integration, new model fields, data migration, test additions/runs, installed review, or DFM.2 activation. No full Cartesian product of all fields. Do not treat synthetic scenarios as source observations or the debug warning as an official alert.
