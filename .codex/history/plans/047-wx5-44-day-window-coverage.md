# Cycle plan — 047-wx5-44-day-window-coverage

State: COMPLETE
Roadmap: WX.5
Objective: Define canonical 44-local-date windows, explicit day/field completeness, normalization rules, and provenance-safe daily aggregates using pure deterministic helpers.

## Production boundary

May change only:
- New pure catalog-domain files under `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` for the 44-local-date window, `CatalogDay`, field/day coverage, and deterministic normalization/aggregate rules.
- Focused unit tests under `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` for the temporal and coverage contract.
- `.codex/concepts/WX.5-window-completeness.png` and its editable source if needed to record the owner-approved design input; plan/evidence/history records required by the cycle.

Existing `CanonicalCatalog` and `CanonicalWeatherFacts` types, construction, and semantics are immutable scope boundaries for this cycle. New helpers consume their existing public values; do not edit those types or widen their API.

No provider/network code, persistence, fixture adapters, presentation, UI/renderer, manifest/build changes, application startup, or deprecated oxygenWX files. Treat all new domain APIs as provider-neutral and independent of Android/Compose.

## Functional invariants

- `D` is `asOf` converted to `LocalDate` in the catalog location's IANA `ZoneId`; the ordered target dates are exactly `D-30..D-1` followed by `D..D+13` (30 historical and 14 forecast dates).
- Window identity uses local calendar dates. Instants and expected hourly coverage are derived with the location zone; no date is modeled as a fixed 24-hour duration, and spring/fall days may contain 23/25 hourly instants.
- Window endpoints use an explicit half-open instant boundary: local start of `D-30` through local start of `D+14`. Facts outside it are rejected or reported by a single documented validation outcome; they are not silently clamped into the window.
- Boundary exception (owner decision 2026-10-27): permit a fact with `validTime == D+14.atStartOfDay(zone)` only when it contains one or more accumulated rain/total-precipitation/snowfall values and every such interval lies wholly within D+13 and ends at that timestamp. Reject point values or empty facts at this timestamp. This lets the final D+13 hourly accumulation close the local day without extending the date window.
- Out-of-order facts normalize into ascending `validTime` order. Identical duplicate-time facts may collapse deterministically; conflicting facts at one instant are invalid rather than resolved by input order.
- Each of the 44 dates remains represented even when no hourly facts exist. Day and per-field coverage distinguish `COMPLETE`, `PARTIAL`, and `MISSING`; missing is never converted to zero or fabricated samples. Day coverage is `COMPLETE` when all configured fields are complete, `MISSING` when there are no configured fields or every configured field is missing, and `PARTIAL` otherwise. Fields absent from caller support are unsupported/unspecified, not missing.
- Coverage is evaluated against explicit expected support for the relevant date/field. Completeness must account for the local day's actual temporal extent and field support; it must not require 24 samples on every date.
- Expected support is caller-supplied per date and field, as decided by the owner on 2026-10-27. WX.5 does not infer product support from nullable fact fields or impose a universal all-fields-hourly contract.
- Provider-supplied daily values and values derived from hourly facts retain distinct provenance. A derived total or other aggregate is not reported as complete when hourly support is incomplete; unavailable input remains unavailable.
- Derived daily calculations are limited to accumulated rain, total precipitation, and snowfall. Sum only non-overlapping canonical accumulation values whose intervals exactly partition the caller-supplied expected local-day support; emit a derived daily amount only when every expected interval is supplied exactly once. Partial support remains unavailable/incomplete, and overlapping, mismatched, or missing intervals never produce a complete total. Provider-supplied daily amounts for these same three fields may be represented independently with `PROVIDER_SUPPLIED` origin and explicit day support. Other fields have no daily aggregation rule in WX.5. Owner decision: 2026-10-27.
- Existing `CanonicalCatalog`, weather facts, WX.3 identity/provenance semantics, and all current prototype fixture/presentation/UI behavior remain unchanged.

## Steps

- [x] Owner approved the objective-specific annotated window/DST concept at `.codex/concepts/WX.5-window-completeness.png` on 2026-10-27 as rendered. Approval constraints: keep the WX.5 domain additive; retain all 44 local dates; use the IANA zone and local-day/DST support shown; keep `COMPLETE`/`PARTIAL`/`MISSING` distinct from measured zero; retain provider-supplied versus derived daily provenance and its support; and reject out-of-window facts with the documented typed outcome. This approval is design input for WX.5 only; it does not activate the cycle, authorize scope expansion, or count as implementation/acceptance evidence.
- [x] Inspect the WX.3 canonical model and relevant time-zone APIs; preserve the existing canonical types and specify the following resolved contracts before coding:
  - **Additive domain boundary:** leave `CanonicalCatalog` and `CanonicalWeatherFacts` entirely unchanged. New provider-neutral window, `CatalogDay`, field/day coverage, and daily-value types under `data.catalog` consume existing public values without changing or widening existing APIs.
  - **Window/time:** `D = asOf.atZone(location.zoneId).toLocalDate()`. Materialize the 44 ordered local dates `D-30..D-1`, then `D..D+13`. Use `[D-30.atStartOfDay(zone), D+14.atStartOfDay(zone))` as the half-open instant boundary. Generate each date's hourly expected instants from that date's local start through (but excluding) the next date's local start, advancing by one hour on the instant timeline; this yields 23/25 slots on DST transition dates. Never model a local date as 24 hours.
  - **Coverage support:** retain an explicit expected-support set for each date and field. Point/hourly fields compare supplied values at expected instants. Accumulated fields compare their explicit `[start,end)` intervals with the date's `[localStart,nextLocalStart)` support, partitioned into expected hourly intervals; only exact expected interval support counts toward coverage, so gaps or overlaps cannot be mistaken for complete daily accumulation. `COMPLETE` means all expected support is present, `PARTIAL` means some but not all is present, and `MISSING` means none is present. A missing field/date is never converted to zero. Keep coverage independently inspectable at day and field level.
  - **Expected-support ownership (owner decision 2026-10-27):** coverage helpers receive caller-supplied expected support per date and canonical field; they do not assume every product supports every field. The input explicitly lists expected instants for point/hourly fields and expected `[start,end)` intervals for accumulated fields. Empty expected support is invalid/unspecified rather than `MISSING`, so unsupported fields cannot be misreported as missing. Test profiles provide controlled expected-support inputs; defining production/provider profiles is outside WX.5.
  - **Daily values/provenance:** represent each daily value as an additive provider-neutral value with an explicit `PROVIDER_SUPPLIED` or `DERIVED` origin and its support interval/basis. A provider-supplied daily value retains its own origin even when hourly support is absent. A derived aggregate is available as complete only when its expected support is complete and non-overlapping; otherwise preserve it as unavailable/incomplete, never as a fabricated or complete value. An explicit supplied zero remains distinct from absence.
  - **Daily aggregation scope (owner decision 2026-10-27):** only accumulated rain, total precipitation, and snowfall can be derived. Sum source interval amounts only if their intervals exactly cover the caller-supplied expected local-day intervals once each without gaps or overlaps. Store provider-supplied daily values separately from derived values; WX.5 does not define daily temperature, wind, probability, condition, or other reductions.
  - **Normalization/validation:** sort valid facts by ascending `validTime`; collapse only identical duplicate-time facts. Conflicting duplicate-time facts invalidate normalization. Any fact outside the half-open window (apart from the documented D+13 accumulated-value endpoint exception) invalidates the whole normalization result with one documented typed outcome (`OUT_OF_WINDOW_FACT`); do not silently drop or clamp it. Error results identify the offending instant where practical.
  - **Boundary endpoint handling (owner decision 2026-10-27):** the `endExclusive` exception applies only to a fact with at least one rain, total-precipitation, or snowfall amount, no point/condition/rate values, and all accumulated intervals contained in D+13 and ending at `endExclusive`.
- [x] Use the WX.3 canonical model and its contract tests as the compatibility baseline; implement the pure helpers and focused cases against the contracts above. Keep all WX.5 implementation and verification within the existing boundary and acceptance criteria.
- [x] Implement pure date-window generation, all-44-day materialization, coverage types/rules, deterministic sorted normalization, and provenance-safe daily aggregate representation within the production boundary.
- [x] Add focused controlled-clock/location unit cases for UTC date rollover, the exact 44 date identities, representative IANA spring/fall DST 23/25-hour dates, sparse and missing days/fields, supported daily values, endpoint validation (including the D+13 accumulation exception), ordered and duplicate inputs, conflicting duplicates, and prohibition on complete aggregates from incomplete hourly support.
- [x] Run the repository workflow and contract checks, focused catalog unit tests, and `git diff --check`; record exact results and limitations under `.codex/test-artifacts/047-wx5-44-day-window-coverage/`.
- [x] Review the final diff against this boundary; all WX.5 exit conditions are met. Close the cycle through `python scripts/codex_cycle.py close` and record exact results/limits; no blocker remains.

## Acceptance/evidence

Exit evidence must demonstrate:
- A controlled `asOf` and IANA location produce exactly `D-30..D+13`, with local midnight rollover and no 24-hour-duration assumption.
- Tests cover both DST transitions for an IANA zone, 23/25-hour local dates, sparse hourly inputs, all 44 local date identities, and out-of-window endpoints.
- Every represented date and field has the expected `COMPLETE`/`PARTIAL`/`MISSING` result, including missing dates and missing versus explicit-zero values.
- Normalization has a stable chronological order; duplicate behavior is deterministic and conflicting duplicates are rejected.
- Provider-supplied daily fields remain distinguishable from derived values; incomplete hourly support cannot produce a complete derived total.
- Existing WX.3 contract tests and `python scripts/dev.py contract` continue to pass; no provider DTO, renderer, or UI change is introduced.

Target evidence and closeout paths: `.codex/test-artifacts/047-wx5-44-day-window-coverage/README.md`, `.codex/history/047-wx5-44-day-window-coverage.md`, and `.codex/history/plans/047-wx5-44-day-window-coverage.md`. The owner authorized adding and running the focused WX.5 contract tests on 2026-10-27; this does not authorize the full test suite. Do not claim the WX.5 exit is met without required test results. No installed/device, font, RTL, or reduced-motion review applies to this data-only slice.

Commands anticipated once authorized: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, focused Gradle task for the new catalog test class (record exact task), and `git diff --check`. Do not run the full test suite unless specifically authorized; the existing WX.3 history records one unrelated full-suite time-label assertion failure.

## Risks / assumptions

- Calendar-day support and hourly sample expectations must be explicit per field; a generic count of 24 would mislabel DST dates and sparse provider products.
- Daily aggregates must preserve the source's support interval and provenance. This cycle does not invent provider semantics where the canonical contract lacks them; any material unresolved definition discovered in planning the implementation must be written into the active plan before code changes.
- The conflict policy for duplicate facts is deliberately strict: exact duplicates can collapse, but differing facts at one `validTime` invalidate normalization so array order cannot silently choose a winner.
- The WX.5 concept-art gate was met by owner approval on 2026-10-27, as recorded in the first step above. The owner selected the additive type boundary, explicit local-date support, provider-versus-derived daily provenance, and typed out-of-window rejection policy on 2026-10-27. The owner also authorized adding and running the focused cases already enumerated in this plan.
- Owner decisions on 2026-10-27 resolved coverage support ownership (caller-supplied expected support per date/field) and daily aggregation scope (accumulated rain, total precipitation, and snowfall only, with exact non-overlapping local-day interval support required for derivation).
- The owner approved the D+13 boundary-ending accumulation exception on 2026-10-27; point facts remain outside the half-open window.

## Out of scope

- Provider requests/response DTOs, HTTP, source contracts, cache/storage, live coverage claims, or live smoke tests.
- Adapting `SampleForecast`, debug catalogs/profiles, or recorded fixtures; this is WX.6.
- Rendering or presenting `CatalogDay`, wiring it into the app, changing `ElasticTimeAxis`, or adding location selection.
- Official alerts, synthetic warning semantics, new weather conditions, unit conversions, or changes to the WX.3 canonical weather fact vocabulary.
- Changes to the legacy application, full app integration, visual design beyond the required concept board, and broad full-suite/device/accessibility acceptance.
