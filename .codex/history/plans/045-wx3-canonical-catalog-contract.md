# Cycle plan — 045-wx3-canonical-catalog-contract

State: COMPLETE
Cycle ID: 045-wx3-canonical-catalog-contract
Roadmap: WX.3
Objective: Define the provider-neutral canonical catalog envelope and nullable weather-fact contract, with pure construction and validation checks and no migration of existing fixture or presentation paths.

## Production boundary and exact planned files

This cycle defines and implements the canonical data contract only. Keep existing fixture, debug catalog/profile, presentation, projection, UI, atmospheric rendering, build configuration, and deprecated oxygenWX paths unchanged. Add the smallest cohesive canonical model and validation API under `app/src/main/java/com/oxygen/weather/timeline/data/catalog/`:

- `CatalogIdentity.kt` — local location identity, coordinates, timezone, dataset identity, and source/product provenance value types.
- `CanonicalWeatherFacts.kt` — provider-neutral valid-time fact values and composable condition/phenomena vocabulary.
- `CanonicalCatalog.kt` — catalog envelope and fact records, including `asOf: Instant` and absolute valid times.
- `CanonicalCatalogValidator.kt` — pure construction/validation rules if those rules cannot remain clear in the model factories.
- `app/src/test/java/com/oxygen/weather/timeline/data/catalog/CanonicalCatalogContractTest.kt` — focused contract coverage, only if explicitly authorized under the repository verification rule.
- `.codex/concepts/WX.3-canonical-catalog-contract.png` and editable `.svg` — annotated identity, facts, and provenance contract board. Product-owner approval was recorded on 2026-10-27 before canonical model implementation.
- `.codex/test-artifacts/045-wx3-canonical-catalog-contract/` — board approval record and actual verification outputs.

The active plan, cycle pointer, and WX.3 evidence pointer may be updated as planning records. Do not add adapters, providers, storage, transport, UI, or renderer integrations in this cycle. If source inspection shows that the proposed package/file boundaries conflict with established repository architecture, record the evidence and revise this plan before implementation.

## Functional and semantic invariants

- One provider-neutral schema accepts live, deterministic simulated, and recorded replay datasets. Origin and product provenance are metadata; they do not create parallel forecast model types.
- A catalog envelope has a stable locally assigned location identity, finite coordinates with geographic bounds, an IANA timezone, explicit `asOf: Instant`, and dataset identity. Each fact record has an absolute `validTime: Instant`; local dates and labels are derived using the envelope timezone.
- Provenance distinguishes forecast, archived model forecast, reanalysis, actual observation, and synthetic meteorological products. Archived model forecasts are not represented as verified observations. Provider DTOs and provider-specific field names do not enter canonical types.
- Weather values use documented canonical metric units and explicit, finite domain ranges. Missing values remain nullable/absent and are never defaulted to zero. A supplied zero remains a valid, distinct value where the field permits it.
- Condition labels and weather phenomena are composable typed values rather than one giant Cartesian-product enum. Preserve distinct generic precipitation probability and thunderstorm probability; precipitation probability, amount, and rate; rain, total precipitation, snowfall, and snow depth; and wind speed, gust, direction, temperature, pressure, humidity, cloud, and visibility.
- Optional severe-weather inputs exist only when supported by a source. Do not invent hail, lightning, tornado probability, intensity, or severity. Official alerts and the legacy synthetic `STORM_WARNING_PREVIEW` remain outside canonical weather facts.
- Identity, provenance, and weather facts are the same envelope shape for live, simulated, and replay origins. No synthetic-only field leaks into the canonical envelope.
- Existing `SampleForecast`, debug catalog scenario IDs and values, transition sequences, generated profiles, debug picker, presentation, selected-time behavior, scene mapping, and app startup remain unchanged.

## Resolved contract decisions for the board and implementation

These choices settle the plan's representation, unit, range, and provenance questions. The board and implementation use the same contract. The product owner approved the board as shown on 2026-10-27 before model implementation; approval constraints are recorded in the cycle evidence.

### Representation and units

- Use immutable Kotlin data/value types in the canonical `data.catalog` package. Use opaque, nonblank app-assigned string IDs for local location and dataset identity; do not encode a geocoder/provider ID or require UUID generation. Store coordinates as latitude/longitude `Double` degrees, the timezone as an IANA `ZoneId`, and `asOf`/`validTime` as `Instant`.
- Represent scalar measurements as `Double` values whose property names state the unit (`temperatureC`, `windSpeedKph`, etc.); keep source-optional facts nullable. Validate finiteness and the inclusive bounds below at construction. Reject invalid values; never clamp, substitute zero, or silently convert units in the data layer. Enumerated conditions/phenomena remain composable typed sets, independent of nullable scalar measurements.
- Canonical units and accepted domain ranges:

  | Fact | Representation and range |
  |---|---|
  | Air temperature | degrees Celsius, `-100..70` |
  | Precipitation and thunderstorm probability | percent, `0..100` |
  | Relative humidity and cloud cover | percent, `0..100` |
  | Wind speed and gust | km/h, `0..500` |
  | Wind direction | degrees clockwise from true north, `0..<360` |
  | Rain amount and total precipitation | mm; rain is liquid rain depth, total precipitation is liquid-water equivalent across phases; each `0..2000` per explicit accumulation interval |
  | Precipitation rate | mm/hour, `0..500` |
  | Snowfall | cm of newly accumulated snow per explicit accumulation interval, `0..500` |
  | Snow depth | cm on the ground at the valid time, `0..2000` |
  | Surface pressure | hPa, `100..1200` |
  | Mean sea-level pressure | hPa, `800..1100`; keep separate from surface pressure |
  | Visibility | km, `0..500` |

- Accumulated rain, total precipitation, and snowfall carry their own absolute half-open accumulation interval `[start, end)`; the interval end equals that fact's `validTime`. A rate is an interval mean only when its supplied fact interval says so; otherwise it is the source's point rate at `validTime`. Snow depth is a point measurement, never snowfall. These interval rules preserve source time support without introducing daily aggregation or coverage policy from WX.5.
- A missing/unsupported fact is `null` (or absent from the phenomena set); a measured zero is retained for fields whose range includes zero. Do not represent missingness with sentinel values.

### Provenance classification

- Keep **dataset origin** (`LIVE`, `SIMULATED`, or `REPLAY`) independent from **product kind** (`FORECAST`, `ARCHIVED_MODEL_FORECAST`, `REANALYSIS`, `ACTUAL_OBSERVATION`, or `SYNTHETIC_METEOROLOGICAL_PRODUCT`). The same envelope shape carries both; neither origin nor product kind creates a parallel fact schema.
- `asOf` means when this catalog snapshot was assembled or recorded. It is not a forecast initialization time and does not change product classification. A forecast's optional `issuedAt`/model-run reference describes when that product was issued/initialized. `validTime` describes when each fact applies.
- An archived model forecast stays `ARCHIVED_MODEL_FORECAST` even when its valid time is in the past. It must never be labeled an actual observation. Reanalysis remains `REANALYSIS`, not an in-situ observation. `ACTUAL_OBSERVATION` is reserved for source-identified measurements represented as observations; do not infer it from a past timestamp or from `LIVE` origin.
- Simulated weather uses origin `SIMULATED` and product kind `SYNTHETIC_METEOROLOGICAL_PRODUCT`. Replaying a recorded dataset uses origin `REPLAY` while preserving the recorded product kind and source/product provenance; replay does not turn a forecast into an observation or a synthetic product. Product/provider names, when present, are opaque descriptive identifiers, not provider DTOs or schema discriminators. Metadata needed to classify a product must be explicit; do not guess missing class from timestamps or field values.
- A condition such as storm or snow remains a weather condition/phenomenon only. It does not imply an official alert, warning, or unsupported severe-weather measure. Optional severe-weather facts are present only when the classified source product explicitly supplies them.

## Ordered steps

1. Inspect current source and the WX.2 inventory for existing metric-unit conventions, source/provenance terminology, nullable facts, and package ownership; record exact anchors in cycle evidence. Confirm this plan's proposed model boundaries before editing.
2. Produce the annotated WX.3 contract board, covering location and dataset identity, timezone and `asOf`, absolute valid time, provenance, typed nullable facts, supported ranges, and the live/simulated/replay parity boundary. Product-owner approval was received on 2026-10-27 as shown, with its constraints recorded before canonical model implementation. The board is design input, not proof of contract correctness.
3. Implement immutable provider-neutral identity, provenance, envelope, and weather-fact types with pure validation/construction rules. Document field units, finite/range checks, and explicit missingness. Limit edits to the listed canonical data files.
4. Add focused contract checks for all WX.3 exit cases if test work is authorized. Keep checks pure and independent of Compose, provider DTOs, Android services, network, clock, or persistence.
5. Review the resulting public contract against the approved board and roadmap invariants. Record actual checks, limitations, and any unresolved contract issue in cycle evidence; run workflow validation and `git diff --check` before closeout.

## Acceptance and authorized verification

WX.3 may close only when all are true:

- The approved board exists at the named PNG/SVG paths, and its approval date and constraints are recorded in the plan/evidence before model implementation.
- Pure construction and contract checks cover stable location and dataset identity; bounded coordinates; IANA timezone; explicit reference instant; absolute valid times; internally consistent source/product metadata; finite in-range values; absent versus explicit zero; distinct snowfall versus snow depth; separate thunderstorm versus precipitation probability; severe conditions without alerts; unsupported phenomena left absent; provider-neutral identity; and identical schema shape across live/simulated/replay origins.
- Weather facts remain composable and nullable. No provider DTO or synthetic-only field appears in the envelope. Units and ranges are specified for every canonical numeric field.
- Existing fixtures, presentation paths, debug affordances, scene behavior, and app behavior have no changes. No adapters, providers, storage, transport, renderer changes, or official-alert implementation are introduced.
- Actual verification and limitations are recorded under `.codex/test-artifacts/045-wx3-canonical-catalog-contract/`; `python scripts/dev.py workflow` and `git diff --check` pass. Do not claim unrun checks.

Planned contract verification includes the focused canonical model tests and applicable `python scripts/dev.py contract` / `python scripts/dev.py test` checks. The product owner explicitly authorized implementation and contract verification on 2026-10-27 by approving option 1 (approve the board as shown and continue with implementation and focused contract verification). No device or visual acceptance is required for this data-only slice; the approved contract board is not device evidence.

## Evidence and closeout paths

- Plan: `.codex/plans/045-wx3-canonical-catalog-contract.md`
- Evidence directory: `.codex/test-artifacts/045-wx3-canonical-catalog-contract/`
- Design artifact: `.codex/concepts/WX.3-canonical-catalog-contract.png` and `.svg`
- Closeout: `.codex/history/045-wx3-canonical-catalog-contract.md`
- Archived plan after close: `.codex/history/plans/045-wx3-canonical-catalog-contract.md`

These are allocated targets; artifacts not yet created are not evidence claims.

## Risks and assumptions

- The numeric ranges above are deliberately broad canonical validation envelopes, not claims about a provider's supported range or forecast accuracy. The board may clarify labels and examples but must not silently change these ranges; any proposed change requires a recorded owner decision before implementation.
- The local location ID is an app-assigned stable identity, not a geocoder/provider identifier. Dataset identity is stable within the dataset/envelope contract and does not imply persistence in this cycle.
- Provenance describes how the dataset was delivered and what product its facts represent; it does not prove quality, freshness policy, or verification. Those belong to later bounded slices unless required for unambiguous source/product classification here.
- Product-owner board approval is recorded on 2026-10-27; model implementation may proceed under the approved board constraints.
- The product owner explicitly authorized adding and running the focused contract tests for this cycle on 2026-10-27.

## Out of scope

- Migrating `SampleForecast`, `DebugForecastCatalog`, `DebugForecastProfiles`, or existing presentation/projection/UI types; fixture adapters are later WX.6 work.
- 44-day calendar windows, daily aggregates, coverage/completeness, DST window generation, or duplicate/out-of-order normalization (WX.5).
- Any live provider, network access, API/DTO mapping, simulated/replay data production, provider configuration, persistence/cache, location search/permission, source switching, or app startup integration.
- Official alerts, warning state migration, invented severe-weather fields, scene/rendering changes, UI behavior, navigation, app identifier/signing changes, or edits to deprecated oxygenWX.
- Device installation, visual acceptance, or broad/full-suite verification beyond the explicitly authorized contract checks.
