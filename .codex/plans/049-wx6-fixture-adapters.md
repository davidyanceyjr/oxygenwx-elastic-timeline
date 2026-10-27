# Cycle plan — 049-wx6-fixture-adapters

State: PLANNED
Cycle ID: 049-wx6-fixture-adapters
Roadmap: WX.6
Objective: Add pure canonical adapters for the existing normal fixture, debug scenarios and sequences, and generated debug profiles while preserving their established facts, identities, and runtime behavior.

## Scope and production boundary

Implement adapters only. Existing fixtures remain the source of truth; the app does not load a canonical catalog, select a new source, or change startup behavior.

Expected affected areas, subject to source inspection before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/data/` — normal `SampleForecast` adapter.
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/` — debug scenario/sequence and generated-profile adapters. Keep debug data and adapter entry points in the debug source set.
- `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` and/or `app/src/testDebug/java/com/oxygen/weather/timeline/data/debug/` — focused golden compatibility checks using fixed explicit inputs; the owner authorized these focused adapter tests.
- `.codex/concepts/WX.6-fixture-adapters.png` and `.svg` — objective-specific annotated fixture-to-canonical flow/compatibility board.
- `.codex/test-artifacts/049-wx6-fixture-adapters/` — actual verification output and concept-board approval record.
- This plan, `.codex/current.md`, and the WX.6 evidence pointer in `docs/ROADMAP.md` as needed for cycle records.

Do not edit fixture definitions/formulas, `ForecastSource`, `ForecastProjection`, UI, atmosphere, gesture behavior, app startup, picker behavior, release source configuration, build configuration, or deprecated oxygenWX files. Do not connect an adapter to an active app data path.

## Verified architecture and compatibility facts

- The canonical envelope already exists at `app/src/main/java/com/oxygen/weather/timeline/data/catalog/CanonicalCatalog.kt`, with identity/provenance in `CatalogIdentity.kt` and nullable absolute-time facts in `CanonicalWeatherFacts.kt`.
- `SampleForecast` lives in main sources and contains 192 hours over eight fixed 24-hour fixture dates; each day uses sample hour 14 as its representative. It has no location zone, so adapting its `LocalDateTime` values requires an explicit zone input to create canonical `Instant` values.
- `DebugForecastCatalog` lives in debug sources and defines 12 stable scenario IDs and three ordered sequences. Scenario `validTime` values are separate from sequence-local `sequenceTime` values; sequence start is 2026-10-20T00:00 and advances hourly. Do not conflate the two timelines.
- `DebugForecastProfiles` independently generates the `RAIN_3_DAY`, `STORM_7_DAY`, and `COLD_SNOW_10_DAY` profiles with 7/11/14 loaded-day horizons, two-day clear buffers, direct formulas, and per-profile reference times. Profile facts are not generated from catalog rows.
- The WX.2 inventory and owner disposition classify catalog/profile differences as intentional illustrative cases; adapters must retain both sets independently.
- Legacy `PrecipitationAmount.RainLiquidMm` has no accumulation interval in the fixtures and therefore stays out of canonical interval-valued rain facts. Preserve it in adapter metadata with an explicit unmapped reason. `SnowDepthCm` maps to canonical snow depth, never snowfall.
- Missing probabilities/amounts remain absent. The debug warning preview remains a debug-only legacy state and must not be represented as an official alert or inserted into canonical weather facts.
- Existing normal/debug presentation adapters, fixture values, 7/11/14 profile bounds, reference reset behavior, debug picker, selected-time rules, and app startup remain unchanged.

## Owner-approved adapter contracts and remaining gate

The owner approved the following contracts on 2026-10-27. They settle the adapter semantics without changing WX.3 canonical types:

- **Explicit inputs:** Every adapter entry point receives a `CatalogLocation` (including its IANA timezone), `DatasetId`, and explicit `asOf: Instant`. No location, timezone, dataset identity, or current time is guessed or read from the host environment. Synthetic provenance is `SIMULATED` plus `SYNTHETIC_METEOROLOGICAL_PRODUCT`.
- **Strict local-time conversion:** Resolve every fixture `LocalDateTime` with the supplied location timezone. Convert only when `ZoneRules.getValidOffsets(localDateTime)` returns exactly one offset. Zero offsets is a DST gap; multiple offsets is a DST overlap. Return a clear typed adapter failure identifying the local time, zone, and failure kind. If any required fixture time fails, return failure for the whole adapter operation; do not return partial catalogs, shift a local time, select an overlap offset, or use system defaults. Apply the same rule to scenario `validTime`, sequence `sequenceTime`, profile sample/reference times, normal hourly times, and daily representative times.
- **Loss-aware source-only values:** Leave a legacy `RainLiquidMm` out of canonical `rainAmountMillimeters` because it has no explicit accumulation interval. Retain its exact typed value in adapter metadata with a stable reason such as `ACCUMULATION_INTERVAL_UNAVAILABLE`. Keep canonical rain amount null for that record. Map `SnowDepthCm` to `snowDepthCentimeters`, never to snowfall. Preserve other legacy values not represented by canonical facts (including the synthetic warning preview and independent precipitation identity) in adapter metadata, with their synthetic/debug meaning explicit; do not turn warning state into an alert or imply a canonical fact that the schema cannot represent.
- **Adapter-level compatibility metadata:** Keep debug scenario/profile/sequence IDs, names, order, sequence times, profile reference times, and normal daily summaries (date, extrema, max precipitation probability, representative sample identity/time) in adapter result types outside `CanonicalCatalog` and `CanonicalWeatherFacts`. A fact's canonical `validTime` is derived only from its fixture valid/sample time; a sequence's `sequenceTime` remains separate metadata. Do not widen the canonical schema to carry these fixture-specific concepts.
- **Fixed test context:** Tests use fixed, explicit locations/timezones, dataset IDs, and `asOf` instants. They do not depend on wall clock, host timezone, geolocation, network, or Android services. Include UTC success cases and explicit timezone cases that exercise valid, gap, and overlap conversion behavior.

If the concept board review reveals a need to widen or revise the canonical contract, stop and record the issue; do not edit WX.3 types inside this slice without a bounded roadmap/plan revision. The semantic decisions are approved, but the required objective-specific concept-art gate remains unmet until the board at `.codex/concepts/WX.6-fixture-adapters.png` and `.svg` is produced and approved, with these constraints recorded before implementation.

## Functional invariants

- Preserve all 12 debug scenario IDs and exact values/nulls, including generic precipitation and thunderstorm probabilities independently, tagged rain/snow values, and the isolated warning-preview state outside canonical facts.
- Preserve the three sequence IDs/names, each step ID/order, sequence times, and the distinction between a scenario's valid time and the step's sequence time.
- Preserve all three profile IDs, formulas/output facts, loaded horizons (7/11/14 days), 48-sample calm/clear prefix and suffix, event ranges, and reference times. Do not derive profiles from catalog scenarios.
- Preserve all 192 normal sample hours, day/date structure, hourly values, daily extrema, and hour-14 representatives.
- Preserve absent values as absent; preserve zero as an explicit supplied value. Do not convert scene-only fallbacks, warning state, or interpolated values into catalog facts.
- A local fixture time resolves only with exactly one valid offset in the supplied IANA zone. DST gaps and overlaps produce typed failure; any such failure rejects the whole adapter result without shifting, choosing, or dropping times.
- Durationless legacy rain amounts remain present in adapter metadata with `ACCUMULATION_INTERVAL_UNAVAILABLE`, and the corresponding canonical interval-valued rain field remains absent. Snow depth remains a canonical point value. Other unsupported legacy values remain explicitly retained in adapter metadata rather than being dropped or reinterpreted.
- Adapter results retain all fixture IDs, sequence/profile metadata, and normal daily summaries outside the canonical schema; canonical facts use their own valid/sample time, never sequence time.
- Use canonical `SIMULATED` origin and `SYNTHETIC_METEOROLOGICAL_PRODUCT` product kind for synthetic fixtures. Do not represent these as actual observations or archived forecasts.
- Keep canonical models provider-neutral and ensure provider DTOs, UI/presentation types, or renderer values do not enter the canonical schema.
- Leave all current app paths and debug affordances usable; no new source is loaded by the application.

## Ordered steps

1. Review WX.2 source-grounded inventory, WX.3 canonical contract, WX.5 coverage rules, current fixtures, and existing regression checks; record exact anchors in cycle evidence.
2. Create the annotated WX.6 adapter/compatibility board showing fixture inputs, adapters, canonical envelope/facts, retained legacy IDs/sequence metadata, the scenario-time distinction, provenance, nullable values, and out-of-scope app wiring. Obtain owner approval and record constraints here before coding.
3. Implement adapter result/error contracts that require explicit `CatalogLocation`, `DatasetId`, and `asOf`; return typed whole-operation failures for DST gaps/overlaps; and preserve unsupported source values and fixture metadata in adapter-level wrappers.
4. Implement pure main-source adapter for `SampleForecast` and debug-source adapters for the scenario catalog/sequences and independently generated profiles. Preserve debug source-set isolation and avoid modifying fixture generators or runtime consumers.
5. Add focused exact-output/golden checks for every preserved item in the WX.6 exit using fixed explicit adapter inputs. Cover canonical null/zero/probability/condition/amount comparisons, loss-aware rain and warning metadata, DST unique/gap/overlap outcomes and whole-operation failure, sequence-time distinction, profile horizon/reference/formula snapshots, normal sample values/summaries, and release isolation. The owner authorized these focused adapter tests by directing that tests use fixed inputs; no full-suite run is authorized.
6. Run only authorized verification, inspect the final diff, preserve actual evidence in the cycle artifact directory, and record any pre-existing or newly observed limitations. Do not claim the roadmap exit until all required comparisons and build/contract gates have actual evidence.

## Acceptance and verification

Required WX.6 exit evidence:

- Golden/snapshot comparisons demonstrate exact scenario facts and nulls, 27%/90% thunderstorm probabilities, warning-preview boundary, transition IDs/order/times, generated profile outputs/reference reset, and normal sample presentation match the recorded baseline.
- Each adapter requires explicit location, dataset ID, and `asOf`; fixed-input tests demonstrate unique-offset conversion and typed gap/overlap failures with no partial output.
- Durationless rain amounts are preserved with their unmapped reason while canonical rain amounts remain absent; snow depth maps to canonical snow depth; unsupported warning/precipitation metadata is retained without entering canonical facts.
- Scenario/profile/sequence identities and times plus normal daily summaries are preserved in adapter-level results, with sequence time distinct from canonical fact valid time.
- All existing debug entry points remain usable; scenario tables and adapter code remain absent from release sources; adapters do not introduce provider DTOs into presentation.
- Canonical synthetic origin/product metadata is explicit and consistent, and all adapter time/identity inputs follow the owner-approved board.
- `python scripts/dev.py build`, `python scripts/dev.py contract`, focused authorized fixture adapter checks, and `git diff --check` have exact recorded results.
- Actual evidence is stored under `.codex/test-artifacts/049-wx6-fixture-adapters/`; closeout and archived plan target `.codex/history/049-wx6-fixture-adapters.md` and `.codex/history/plans/049-wx6-fixture-adapters.md`.

No device or visual acceptance applies because this is data-only and must not change UI behavior. The owner authorized focused adapter tests with fixed inputs; do not run the broad/full test suite unless separately authorized. Cycle 040 documents an unrelated time-label assertion failure. Record exact build, contract, and focused test commands/results; do not claim gates without actual results.

## Risks

- Canonical facts use `Instant`, while fixtures use local times without a zone. The approved strict resolver makes timezone gaps/overlaps explicit failures; confirm coverage with fixed tests before claiming compatibility.
- Canonical facts do not carry scenario IDs, sequence IDs/times, profile IDs, warning previews, independent precipitation identity, or daily representative metadata. Adapter-level wrappers must preserve each without adding fields to the shared schema.
- Legacy rain amounts lack accumulation intervals and remain unmapped in canonical rain facts; tests must prove that their exact amount survives in metadata with the unmapped reason and is never assigned an invented interval.
- Debug variants intentionally do not match generated profile tuples in several cases. No reconciliation or fixture normalization is in scope.
- Existing full-suite failure remains historical and unresolved; distinguish it from this cycle's results.

## Out of scope

- Live/network providers, transport DTOs, source/load-result contract, simulation/replay generation, persistence/cache, location search/permissions, or active source switching.
- App loading of canonical data, changes to startup, picker, UI, projection, timeline, atmosphere, gestures, accessibility, or fixture runtime behavior.
- Fixture value/formula rewrites, profile/catalog merging, warning/official-alert semantics, inferred precipitation intervals, snowfall inference, or new weather facts.
- Release integration, app-ID/signing work, edits to deprecated oxygenWX, device/visual acceptance, and broad full-suite verification.
