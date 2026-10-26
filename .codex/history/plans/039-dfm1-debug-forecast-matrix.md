# Cycle plan — 039-dfm1-debug-forecast-matrix

State: ACTIVE
Roadmap: DFM.1
Objective: Define and owner-review a small typed set of synthetic debug fixtures and their transition contract so later slices can prototype weather visuals and connect forecast interface elements. These fixtures are development placeholders, not a production forecast schema or provider-data contract.

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
- DFM.1 defines a debug-matrix vocabulary and field contract only. It must not imply that these concepts have been added to the canonical forecast model or production fixture. If DFM.2 needs debug-only types to represent the approved matrix, those types remain isolated from normal `SampleForecast` unless a separately reviewed plan authorizes a broader model change.
- The matrix is a design artifact for a later debug-only fixture catalog. Its scenarios provide predictable inputs for visual prototyping and interface wiring, including intentionally missing values. They must not change normal `SampleForecast`, selected-time authority, or forecast provenance, and do not define a production schema.
- Required scenarios and coverage are bounded by DFM.1 in `docs/ROADMAP.md`: clear, partly cloudy, cloudy, rain, snow, and storm; meaningful day/night variants; dry/wet probability; explicit/missing amount; transitions; separate illustrative thunderstorm probability cases at 27% and 90%; and a separate debug-only warning state.
- Historical-realism reference: NOAA's [Global Historical Climatology Network hourly (GHCNh)](https://www.ncei.noaa.gov/products/global-historical-climatology-network-hourly) is an archive of hourly/synoptic land-station observations, with station-dependent fields such as present weather and precipitation amounts. It can ground plausible observed condition/amount combinations and transitions, but it is not a forecast-probability dataset and does not establish the probability values in the illustrative matrix.
- No live provider or API is selected by this slice. Align the provider-neutral field contract to forecast concepts already required by DFM.1, and keep live forecast fields distinct from historical observed fields. Do not add a runtime data dependency or imply the historical samples are current forecasts.

## Steps

- [x] Reconcile DFM.1 requirements with current model, projection, atmosphere, and controlling semantic boundaries; record verified field types and gaps in the design artifact.
- [x] Use a small, documented set of real historical hourly observation examples as a plausibility check for condition/amount combinations and representative transitions; record dataset, station/date identifiers, fields actually present, and source/observation provenance. Keep this as reference evidence, not fixture content or live forecast truth.
- [x] Draft an annotated coverage table with stable scenario IDs, the visual/interface case each placeholder exercises, illustrative valid time/condition, typed fields, debug provenance labels, and explicit missingness. Keep historical observations in a separate evidence section/table; do not copy observed values into debug forecast rows or use them to derive forecast probabilities or warnings. Mark each synthetic fixture value as illustrative or absent. Avoid a Cartesian-product expansion.
- [x] Define the debug-fixture field contract without changing production types: use the existing forecast concepts for sky condition, generic precipitation probability, precipitation type, and nullable precipitation amount where they help exercise the prototype; name absent canonical concepts explicitly. Keep generic precipitation probability and thunderstorm probability as independent optional percentages, with 27% and 90% used only as illustrative fixture inputs. Do not infer either probability from historical observations or from the other probability field.
- [x] Represent snow with an explicitly debug-scoped scenario condition/vocabulary because canonical `SkyCondition` has no snow value. Keep this distinct from `PrecipitationType.SNOW`, which identifies precipitation and does not assert snow cover. Record owner approval of this vocabulary before DFM.2 adopts it; do not extend the production enum in DFM.1.
- [x] Represent the warning-like visual prototype input independently as a debug-only state (`NONE` and `PRESENT`) and label it as synthetic debug data, not an official alert. It exists to exercise the warning-related interface/visual state; it carries no provider provenance, official severity, or alert timing. Record owner approval of this placeholder meaning before DFM.2 adopts it; any request for official-alert behavior or a production field remains a later bounded decision.
- [x] Add a transition map for representative clear/cloudy/rain/snow/storm entry and clearing sequences, with expected time and condition behavior. The owner approved one synthetic hour per transition; row timestamps remain illustrative examples.
- [x] Explicitly distinguish historical observed weather/amount, forecast valid time/condition, generic forecast precipitation probability, thunderstorm probability, typed precipitation identity/amount, and the illustrative debug warning signal. Preserve each intentionally missing value as absent rather than filling it from another field, a nearby observation, or a transition. State that debug warning is not an official alert.
- [x] Review the draft against the roadmap exit and semantic invariants; capture the owner's approval and any constraints in the design artifact before closing DFM.1.
- [x] Run `python scripts/dev.py workflow`, inspect `git diff --check`, and verify the design artifact names every required scenario, field distinction, missingness case, provenance label, transition, and owner approval. No tests or application verification are authorized or applicable.
- [x] Record exact review evidence and any unresolved limitations in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at closeout.

## Acceptance/evidence

DFM.1 is complete only when `.codex/concepts/DFM.1-debug-forecast-matrix.md` contains the owner-reviewed inventory of synthetic debug fixtures for visual prototyping and interface wiring, typed field contract, intentional missingness, debug provenance labels, warning/probability distinctions, expected time/condition transitions, and a bounded historical observation plausibility check with traceable citations/identifiers. The artifact must explicitly state that the fixtures are placeholders rather than production forecast data/schema, historical observations do not establish forecast probabilities, and no live provider/runtime feed is selected. It records owner approval for DFM.2 to use the matrix as its fixture implementation contract. Verify the content against each DFM.1 roadmap requirement, run `python scripts/dev.py workflow`, and require `git diff --check` to pass. Record exact results in `.codex/history/039-dfm1-debug-forecast-matrix.md`; archive this plan at `.codex/history/plans/039-dfm1-debug-forecast-matrix.md`.

This is documentation-only work. It makes no installed, accessibility, visual, device, build, test, or runtime acceptance claim.

## Risks / assumptions

- The concept artifact is an annotated synthetic-fixture/transition design for later visual and interface prototyping, not production data design or scene artwork. DFM.1's roadmap names this artifact as the design gate for DFM.2.
- NOAA GHCNh observations can provide examples of actual observed weather and precipitation fields, but station coverage and available elements vary; observed history does not supply forecast probability. Select only a small traceable reference set, and do not let it expand the matrix into an exhaustive climatology study.
- "Align with live weather data" is interpreted as keeping the typed forecast contract provider-neutral and clearly separating forecast concepts from historical observations. Choosing and mapping a concrete live provider remains outside DFM.1 and requires a later bounded decision.
- Candidate scenario rows can be selected and bounded using the DFM.1 coverage requirements. Keep the matrix vocabulary and later catalog representations scoped to synthetic debug fixtures for visual/interface prototyping; add no production fields in this cycle. Owner approval must explicitly accept the debug-scoped snow condition and the `NONE`/`PRESENT` synthetic warning-like prototype input before DFM.1 closes. If the owner requests production model changes or provider-backed alerts, record that as a later bounded decision; do not silently broaden this cycle.
- Model fit for implementation: difficulty 3/10. Scope is documentation-only and architecture coupling is limited, but the typed distinctions, missingness, and transition coverage need careful consistency review. Recommend the least-cost available Codex model with a credible path, `gpt-6-luna`; escalate to `gpt-6-sol` if it cannot reconcile the scenario inventory with the model/projection contract without inventing fields or expanding scope. This compares relative model fit only; no API availability or pricing claim is made.

## Out of scope

No debug catalog implementation, matrix browser, production fixture change, application code, scene mapping/redesign, alert behavior, provider selection/integration, new model fields, data migration, test additions/runs, installed review, or DFM.2 activation. No full Cartesian product or broad climatological analysis. Do not treat historical observations as current forecasts, use them to infer forecast probabilities, treat synthetic scenarios as source observations, or present the debug warning as an official alert.
