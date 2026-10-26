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
- Historical-realism reference: NOAA's [Global Historical Climatology Network hourly (GHCNh)](https://www.ncei.noaa.gov/products/global-historical-climatology-network-hourly) is an archive of hourly/synoptic land-station observations, with station-dependent fields such as present weather and precipitation amounts. It can ground plausible observed condition/amount combinations and transitions, but it is not a forecast-probability dataset and does not establish the probability values in the illustrative matrix.
- No live provider or API is selected by this slice. Align the provider-neutral field contract to forecast concepts already required by DFM.1, and keep live forecast fields distinct from historical observed fields. Do not add a runtime data dependency or imply the historical samples are current forecasts.

## Steps

- [ ] Reconcile DFM.1 requirements with current model, projection, atmosphere, and controlling semantic boundaries; record verified field types and gaps in the design artifact.
- [ ] Use a small, documented set of real historical hourly observation examples as a plausibility check for condition/amount combinations and representative transitions; record dataset, station/date identifiers, fields actually present, and source/observation provenance. Keep this as reference evidence, not fixture content or live forecast truth.
- [ ] Draft an annotated coverage table with stable scenario IDs, scenario purpose, time/condition, typed fields, provenance labels, and explicit missingness. Mark which values are historical observations, illustrative forecast values, or intentionally absent. Avoid a Cartesian-product expansion.
- [ ] Add a transition map for representative clear/cloudy/rain/snow/storm entry and clearing sequences, with expected time and condition behavior.
- [ ] Explicitly distinguish historical observed weather/amount, generic forecast precipitation probability, thunderstorm probability, typed precipitation identity/amount, and the illustrative debug warning signal. Historical observations may ground scenario plausibility but must not be used to claim or derive the 27%/90% forecast probabilities. State that debug warning is not an official alert.
- [ ] Review the draft against the roadmap exit and semantic invariants; capture the owner's approval and any constraints in the design artifact before closing DFM.1.
- [ ] Run `python scripts/dev.py workflow`, inspect `git diff --check`, and verify the design artifact names every required scenario, field distinction, missingness case, provenance label, transition, and owner approval. No tests or application verification are authorized or applicable.
- [ ] Record exact review evidence and any unresolved limitations in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at closeout.

## Acceptance/evidence

DFM.1 is complete only when `.codex/concepts/DFM.1-debug-forecast-matrix.md` contains the owner-reviewed scenario inventory, typed field contract, intentional missingness, provenance labels, warning/probability distinctions, expected time/condition transitions, and a bounded historical observation plausibility check with traceable citations/identifiers. The artifact must explicitly state that historical observations do not establish forecast probabilities and that no live provider/runtime feed is selected. It records owner approval for DFM.2 to use the matrix as its implementation contract. Verify the content against each DFM.1 roadmap requirement, run `python scripts/dev.py workflow`, and require `git diff --check` to pass. Record exact results in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at `.codex/history/plans/039-dfm1-debug-forecast-matrix.md`.

This is documentation-only work. It makes no installed, accessibility, visual, device, build, test, or runtime acceptance claim.

## Risks / assumptions

- The concept artifact is an annotated data/transition design, not scene artwork. DFM.1's roadmap names this artifact as the design gate for DFM.2.
- NOAA GHCNh observations can provide examples of actual observed weather and precipitation fields, but station coverage and available elements vary; observed history does not supply forecast probability. Select only a small traceable reference set, and do not let it expand the matrix into an exhaustive climatology study.
- "Align with live weather data" is interpreted as keeping the typed forecast contract provider-neutral and clearly separating forecast concepts from historical observations. Choosing and mapping a concrete live provider remains outside DFM.1 and requires a later bounded decision.
- Candidate scenario rows can be selected and bounded using the DFM.1 coverage requirements; no owner decision to add new product signals is assumed. If a material field or warning-semantics decision is necessary, record the alternatives and consequence in the design artifact and leave that point unresolved for the owner.
- Model fit for implementation: difficulty 3/10. Scope is documentation-only and architecture coupling is limited, but the typed distinctions, missingness, and transition coverage need careful consistency review. Recommend the least-cost available Codex model with a credible path, `gpt-6-luna`; escalate to `gpt-6-sol` if it cannot reconcile the scenario inventory with the model/projection contract without inventing fields or expanding scope. This compares relative model fit only; no API availability or pricing claim is made.

## Out of scope

No debug catalog implementation, matrix browser, production fixture change, application code, scene mapping/redesign, alert behavior, provider selection/integration, new model fields, data migration, test additions/runs, installed review, or DFM.2 activation. No full Cartesian product or broad climatological analysis. Do not treat historical observations as current forecasts, use them to infer forecast probabilities, treat synthetic scenarios as source observations, or present the debug warning as an official alert.
