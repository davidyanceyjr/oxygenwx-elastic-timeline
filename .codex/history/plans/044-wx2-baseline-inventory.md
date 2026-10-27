# Cycle plan — 044-wx2-baseline-inventory

State: COMPLETE
Roadmap: WX.2
Objective: Produce a source-grounded, read-only inventory and regression baseline for the existing catalog, projections, timeline, atmosphere, tests, and device evidence.

## Scope and production boundary

This cycle is a read-only audit of the current successor app. It may create only planning/evidence records for this cycle and update its WX.2 roadmap evidence allocation when the plan is finalized. Application sources, tests, fixtures, build configuration, and deprecated oxygenWX files are inspection-only and must not be changed.

Inspect the actual debug catalog and its 12 scenario IDs, three transition sequences, all three debug profiles and their 7/11/14-day loaded bounds, the eight-day normal sample, `ForecastSource`, `ForecastProjection`, `ElasticTimeAxis`, `TimeLabScreen`, atmosphere condition mappings, current tests/test scaffolding, and available historical device evidence. Document how independently generated `DebugForecastProfiles.profileFacts()` relates to `DebugForecastCatalog.scenarios`; identify the synthetic warning and snow/unknown visual mappings; and record assumptions around `LocalDateTime`, 24-hour day movement, reference reset, and test scaffolding.

No behavior changes, bug fixes, test additions, fixture changes, or device acceptance claims are in scope.

## Functional and semantic invariants

- The audit describes checked-in/local behavior and cites source paths with line numbers; historical documentation is labeled as historical evidence, not current verification.
- Preserve the 12 scenario IDs, three transition sequences, three complete 7/11/14-day loaded profiles, `SampleForecast`, current debug picker, current screen/gesture/atmosphere behavior, and the WX delivery-contract boundaries.
- Keep the legacy `STORM_WARNING_PREVIEW` debug-only and do not describe it as an official alert.
- Distinguish source forecast facts from decorative/interpolated rendering and distinguish unknown/missing values from zero or inferred values.
- Keep selected time authoritative; zoom does not select time. Preserve existing gesture ownership and readable forecast facts in the baseline description.
- Treat test presence as coverage intent only until the relevant command was actually run and its result recorded.

## Comparison, ownership, and concept-board rules

- Compare each catalog scenario with every generated profile sample over the shared semantic tuple: condition, precipitation probability, precipitation type, typed precipitation amount, thunderstorm probability, warning state, and provenance. Compare profile `DebugProfileSample` forecast facts; never substitute `SkyFrame.precipitationChancePercent` for the nullable forecast probability. `null` is a value meaning unavailable and must match only `null`, not zero or any other value. Compare precipitation amounts by type and value, preserving rain-liquid depth versus snow depth.
- Ignore `validTime`, generated temperature, cloud, and wind when determining shared-case coverage unless source evidence shows that one changes the meaning of the weather case. List profile-only event coverage separately. Do not require scenario IDs or timestamps to align across the independently generated structures.
- Use these classifications consistently: `profile-covered` means at least one sample exactly matches the shared tuple; `catalog-only by design` requires source or adopted-document evidence that the catalog case intentionally covers a distinct targeted case; `potential discrepancy` means there is no exact match and no evidence-backed reason to treat it as intentionally distinct. When intent cannot be established, classify it as `potential discrepancy` and disposition it as `owner decision required`; do not infer intent from a convenient near-match. For every potential discrepancy, show the catalog tuple, closest relevant profile tuple(s), exact differing fields, and source anchors for both definitions.
- Give each compatibility-table row a primary owning code area selected by responsibility, a consulted owner/authority, and a disposition. The bounded role set is: data/debug catalog, presentation/time projection, UI/gesture, atmosphere mapping, and verification/evidence. The product owner is the decision owner for cross-boundary product semantics. Use a human name only when repository evidence identifies that person; otherwise write `owner assignment needed` beside the accountable role. Do not resolve absent assignments by guessing or treating code proximity as product authority.
- Keep the concept board at an inventory/design-input level: show the normal `SampleForecast` and generated debug-profile paths entering the shared presentation source, projection, timeline, and scene; show `DebugForecastCatalog` separately as an inventory/sequence definition with no runtime connection unless source inspection proves one. Label the current facts-versus-rendering boundary, synthetic warning boundary, and known unknown-value mapping; then show the roadmap target's source/provenance and location/time boundaries at a conceptual level. Mark target contract details as future/WX.3+; do not design the canonical schema, add implementation detail, or imply that future capabilities already exist. Add ownership labels from the compatibility table and visibly flag unresolved owner decisions. Product-owner approval and constraints must be recorded explicitly; a pending or rejected board does not meet this gate.

## Owner-approved concept board

- **Approval:** Product owner approved the WX.2 current-versus-target dataflow board on 2026-10-26.
- **Approved artifact:** `.codex/concepts/WX.2-baseline-inventory.png` (editable source: `.codex/concepts/WX.2-baseline-inventory.svg`).
- **Constraints:** The catalog remains shown as inventory-only unless source inspection demonstrates a runtime connection; dashed target flows are roadmap direction, not current capability; WX.3+ contract details remain conceptual and do not authorize schema design or implementation; the board is not installed acceptance evidence.
- **Record:** `.codex/test-artifacts/044-wx2-baseline-inventory/README.md`.

## Ordered steps

1. Inspect source, test, debug-only, documentation, and history paths for each roadmap inventory area; record exact paths and line anchors.
2. Build a compatibility table with columns for invariant, primary owning code area, evidence path, consulted owner/authority, and disposition. Use the bounded responsibility roles and owner-name rule above; identify the product owner as decision owner for cross-boundary product semantics. Explicitly mark any human assignment that the repository cannot support as `owner assignment needed`.
3. Compare every catalog scenario against the generated `DebugProfileSample` values using the exact shared-tuple and classification rules above. Include a row per catalog scenario with its classification, profile matches or closest candidate, and any differing fields. Explain profile-only coverage separately. Record the nullable forecast probability versus `0.0` scene fallback as a migration constraint, not a WX.2 fix. Cite both definitions for each potential discrepancy and disposition it as `accepted`, `follow-up needed`, or `owner decision required`, with the evidence and accountable decision role stated.
4. Record current synthetic warning, snow, and unknown visual behavior, plus the `LocalDateTime`, 24-hour-day, reference-reset, and test-scaffold assumptions.
5. Inventory existing automated checks and historical installed-device artifacts; run no app tests/build/install during this planning turn. In execution, capture a focused baseline or a reproducibility plan and label each baseline claim PASS, FAIL, NOT RUN, or historical-only.
6. Record preexisting discrepancies/failures with evidence and disposition (preserve, investigate within this read-only slice, or create a later bounded follow-up); do not fix them here.
7. Preserve the owner-approved board and its constraints at the required artifact paths. If execution discovers a material source fact that makes the approved board inaccurate, record the evidence and obtain a bounded owner revision before dependent work; do not silently redraw the target or broaden into WX.3 design.

## Acceptance and verification

WX.2 is complete only when all are true:

- `.codex/test-artifacts/044-wx2-baseline-inventory/inventory.md` contains a source-path/line-grounded inventory of every named component and behavior above.
- The inventory includes a migration compatibility table for every legacy invariant with its primary owning code area, evidence path, and consulted owner. Cross-boundary product semantics identify the product owner as decision owner. Individual names are included only when supported by repository evidence; otherwise `owner assignment needed` is recorded and dispositioned.
- The inventory contains one comparison row for each of the 12 catalog scenarios, applies the plan's exact shared tuple and null/amount rules, and uses the coverage classifications (`profile-covered`, `catalog-only by design`, `potential discrepancy`). Every `catalog-only by design` classification has explicit source/document evidence for its distinct purpose; unproven intent remains a potential discrepancy with an owner-decision disposition. The comparison distinguishes profile-only coverage, cites both definitions for each potential discrepancy, and documents that nullable forecast probability remains distinct from the adapter's `0.0` decorative scene cue. It also includes the warning/snow/unknown mappings and time/reset/test-scaffold assumptions.
- `.codex/test-artifacts/044-wx2-baseline-inventory/baseline.md` contains actual recorded snapshots or a reproducible baseline procedure, exact command/device/evidence references where available, and a clear currently verified/unverified status. It must not claim checks that were not run.
- All observed discrepancies and preexisting failures have evidence-backed dispositions; no issue is silently repaired or rolled into later scope.
- The owner-approved annotated current-versus-target dataflow board exists at `.codex/concepts/WX.2-baseline-inventory.png` and meets the board rules. Approval and constraints are recorded in this plan and the evidence README before dependent implementation work. Any later material correction requires an explicit bounded owner decision.
- The evidence README summarizes artifact contents and limitations. `python scripts/dev.py workflow` and `git diff --check` pass at closeout; any other executed commands and outcomes are recorded exactly.
- No application source, fixture, test, or deprecated oxygenWX file changed.

Planned execution verification (not run by planning): focused existing data/projection checks relevant to the inventoried contracts may be run if explicitly authorized for execution; inspect any existing device artifacts and, where a fresh baseline is needed, use the applicable installed-device workflow and record device/build/viewport/input method. Do not claim WX.2 visual or interaction acceptance from source inspection, build output, or old screenshots alone.

Planning-turn verification: `python scripts/dev.py workflow` only. Do not run or add tests as part of this planning task.

## Evidence and closeout paths

- Plan: `.codex/plans/044-wx2-baseline-inventory.md`
- Evidence directory: `.codex/test-artifacts/044-wx2-baseline-inventory/`
- Required evidence: `README.md`, `inventory.md`, `baseline.md`; concept board target `.codex/concepts/WX.2-baseline-inventory.png`
- Closeout: `.codex/history/044-wx2-baseline-inventory.md`
- Archived plan after close: `.codex/history/plans/044-wx2-baseline-inventory.md`

These are allocated targets; artifacts not yet created are not evidence claims.

## Risks and assumptions

- The current local tree may differ from published documentation or old cycle artifacts; source inspection is authoritative for current implementation, and differences must be recorded.
- Historical installed evidence may not cover current behavior or every scenario; mark missing current coverage unverified and provide a repeatable procedure rather than infer acceptance.
- WX.2's annotated dataflow board is an approved design input; it is not installed acceptance evidence. The approved target path and constraints are recorded above and in the evidence README.
- Planning does not authorize test execution, application changes, or device interaction; execution verification remains bounded by this read-only slice and the repository's verification rules.

## Out of scope

- Any app, fixture, test, provider, persistence, navigation, visual, or gesture behavior change.
- Canonical schema design or migration implementation (WX.3+), live/network/location/cache work, official alerts, application-ID/signing migration, or changes to deprecated oxygenWX.
- Treating debug warnings as official alerts, decorative interpolation as measured weather, or compilation/test presence as installed acceptance.
- Activating this cycle or closing WX.2 during planning.
