# Oxygen Time Lab — scoped roadmap

Roadmap format and status rules: [`ROADMAP_SPECIFICATION.md`](ROADMAP_SPECIFICATION.md).
Product and semantic boundaries: [`SPECIFICATION.md`](SPECIFICATION.md).

## Cross-cutting hard rule

Every visual enhancement must preserve timeline responsiveness, gesture predictability, and readability, as defined in `docs/SPECIFICATION.md`. Each visual cycle that changes or reviews visual behavior must include an acceptance check for all three.

## Workstream priority

WX is the current priority: `WX.1`, `WX.2`, `WX.3`, and `WX.5` are `DONE`; `WX.6` is the sole `NEXT` slice; and `ATM.8` is `PLANNED`. The owner-directed P.2.16 correction is DONE in cycle 048. After `WX.28` closes, return to `ATM.8` as the next workstream. This is an ordering decision, not a dependency; ATM slice dependencies remain unchanged.

## Concept-art gate for unfinished slices

Every unfinished slice must have concept artwork specific to its own objective and approved by the product owner before implementation of that slice begins. For design-only slices, an interaction storyboard or annotated visual design is the concept artwork. Keep the artifact at the path named by the slice, and record owner approval and any constraints in that slice's plan. Concept art is a design input, not installed acceptance evidence. Existing screenshots and renderer output do not satisfy this gate. ATM.5 artwork and the WX.3 and WX.5 contract boards are approved; approval remains pending for the other unfinished slices. The user may run artwork creation and review in a separate session.

## G — Roadmap governance

### G.1 — Adopt and apply the roadmap specification — DONE

**Objective/scope:** Define the lightweight roadmap format and migrate every existing Oxygen Time Lab leaf slice to it. Documentation only; leave application behavior unchanged.
**Depends on:** None.
**Exit:** `docs/ROADMAP_SPECIFICATION.md` defines status, required slice fields, evidence, and failure/replanning rules; every roadmap leaf slice conforms; `AGENTS.md` identifies the roadmap specification as authority; `git diff --check` passes.
**Evidence:** `.codex/history/008-roadmap-specification.md`; `.codex/history/plans/008-roadmap-specification.md`; `.codex/test-artifacts/008-roadmap-specification/README.md`.

### G.2 — Adopt applicable oxygenWX skills and agent rules — DONE

**Objective/scope:** Bring the reusable oxygenWX planning, review, execution, context-audit, history-compaction, and model-rating skills into this repository's `.agents/skills/`, adapted to Oxygen Time Lab authorities and constraints. Adapt the project `AGENTS.md` with relevant cycle, architecture, verification, and UI rules. Do not change application source or the sibling oxygenWX checkout.
**Depends on:** G.1.
**Exit:** The selected skill set is installed with valid skill metadata and no production-only assumptions; `AGENTS.md` describes this project's actual workflow and nonnegotiable boundaries; skill validation and `git diff --check` pass; adoption evidence and limitations are recorded.
**Evidence:** `.codex/history/009-agent-skills-ruleset.md`; `.codex/history/plans/009-agent-skills-ruleset.md`; `.codex/test-artifacts/009-agent-skills-ruleset/README.md`.

### G.3 — Adopt the Oxygen experience specification — DONE

**Objective/scope:** Verify and adopt `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` as the experience-level companion to `docs/SPECIFICATION.md`; reconcile current-tree discrepancies and stale README claims. Documentation only; no application behavior changes.
**Depends on:** G.1 and G.2.
**Exit:** The authority relationship is explicit, the proposal's status reflects adoption without claiming unverified acceptance, current behavior discrepancies and owner decisions are recorded, stale README behavior claims are corrected, and documentation/workflow checks plus `git diff --check` pass.
**Evidence:** `.codex/history/022-experience-spec-adoption.md`; `.codex/history/plans/022-experience-spec-adoption.md`; `.codex/test-artifacts/022-experience-spec-adoption/README.md`.

### G.4 — Ingest the Living Atmosphere Engine proposal — DONE

**Objective/scope:** Reconcile OXY-ATM-001 against controlling prototype and experience authorities and the current source tree; adopt it as a subordinate architecture proposal for roadmap planning; add bounded ATM/ENV follow-ups. Documentation and cycle records only.
**Depends on:** G.3.
**Exit:** Authority precedence and adoption limits are explicit; every cited source path is compared with the proposal's baseline; conflicts and missing evidence are recorded; proposed ATM/ENV slices use this roadmap's leaf format; documentation checks pass without application changes.
**Evidence:** `.codex/history/023-atmosphere-spec-intake.md`; `.codex/history/plans/023-atmosphere-spec-intake.md`; `.codex/test-artifacts/023-atmosphere-spec-intake/README.md`.

## WX — OxygenWX unified 44-day weather catalog and production data path

**Status:** Integrated into `docs/ROADMAP.md` after a format, dependency, authority, and provider-capability audit. WX.1 is DONE in cycle 043; no WX application integration has started.

**Authority transition:** WX.1 records the owner-approved successor direction. The current prototype remains the only integrated app behavior; bounded WX data-domain work may proceed without changing app behavior. The deprecated oxygenWX application is a reference only; do not copy its app structure or introduce its four-page Home pager. The immersive elastic timeline remains the successor app's primary experience.

**Roadmap priority:** WX is the prioritized workstream. `WX.1` is `DONE` in cycle 043 and `WX.2` is `DONE` in cycle 044; `WX.3` is `DONE` in cycle 045 and `WX.5` is `DONE` in cycle 047; `WX.6` is `DONE` in cycle 049 and `WX.9` is the sole `NEXT` slice; later WX slices are `PLANNED`; `ATM.8` remains `PLANNED`. P.2.16 is DONE in cycle 048. After `WX.28` closes, promote `ATM.8` to `NEXT` while preserving its existing dependencies and evidence. This records sequencing, not a dependency edge. Cycle 045 closed for WX.3; its contract board was owner-approved on 2026-10-27.

**Concept-art gate:** The existing roadmap requires objective-specific owner-approved concept artwork for every unfinished slice, including data-only and design-only work. Concept paths below are not evidence of owner approval. The WX.3 annotated catalog-contract board was approved on 2026-10-27 as shown, with its constraints recorded in the active plan/evidence before implementation. The WX.5 annotated window-completeness board was approved on 2026-10-27 as shown, with its constraints recorded in plan 047 before implementation. The WX.6 fixture-adapter board and WX.10 simulator board were approved on 2026-10-27 as shown, with their constraints recorded in their plans/evidence. Other proposed `.codex/concepts/WX.*.png` paths remain targets. For model and service slices, use an annotated schema, flow, state, sequence, or boundary board rather than decorative UI art. The owner must approve each artifact and its constraints in that slice's execution plan before implementation. Device/installed acceptance evidence remains separate.

**Cycle/evidence convention:** On starting a slice, assign the next **chronological** cycle ID by the repository's workflow. Each planned evidence line below means the following target paths are allocated **when that slice is planned**, not that files already exist: `.codex/plans/<cycle-id>.md`, `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` after closure. Record the roadmap slice ID in the plan and history. Run `python scripts/dev.py workflow` and the actual checks authorized by the bounded cycle. Do not invent passes, screenshots, tests, or evidence. The project `AGENTS.md` currently says not to add/run tests unless asked; these exits specify the **planned verification** for owner authorization in each cycle, not authorization to run tests now.

**Delivery contract across all WX slices:**

- **One canonical catalog schema** for live API, deterministic simulation, and recorded replay. Origin is metadata, not a parallel weather type system. Provider DTOs never enter Compose or the atmospheric engine.
- Each catalog belongs to a stable locally assigned location ID, coordinates, and IANA `ZoneId`; it is anchored to an explicit `asOf: Instant`. The target **44 local dates** are `D-30` through `D-1` (history), then `D` through `D+13` (forecast), where `D` is the location's date at `asOf`. The current day is included in the forecast window. The window contains 44 **calendar dates**, not a guaranteed 1,056 hourly values; DST, incomplete coverage, and provider gaps are real.
- Hour validity uses `Instant`; local date/labels use the location's timezone. Distinguish forecast, archived model forecast, reanalysis, actual observation, and synthetic meteorological products. Open-Meteo Forecast API `past_days` is **archived model/forecast data**, not verified station observation. Any omitted or unsupported field remains unavailable, never defaulted to zero.
- Model clear/cloud/rain/snow/storm **and severe-weather facts** using independent typed conditions, quantitative fields, phenomena, and probabilities where supplied. Thunderstorm probability is not generic precipitation chance; snow depth is not new snowfall; precipitation probability is not precipitation amount/rate. No invented hail, lightning, tornado probability, intensity, or severity. **Official alerts are a separate future subsystem**, not catalog conditions or warning flags.
- Preserve `DebugForecastCatalog`'s **12 existing scenario IDs**, **three transition sequences**, `DebugForecastProfiles`' **three complete 7/11/14-day loaded profiles**, `SampleForecast`, the current debug picker, and current screen/gesture/atmosphere behavior until individually regression-gated. The 12 rows are a starting test matrix, **not** the full weather-condition vocabulary. Keep the legacy `STORM_WARNING_PREVIEW` debug-only; never claim it represents an official warning.
- Live and simulated data cannot be field-merged implicitly. Switching must atomically replace a validated whole dataset, preserve selection by absolute instant where possible, clamp otherwise, reject stale async responses, and keep real, simulated, and replay caches isolated. A cached real dataset retains real origin plus explicit cache/freshness state.
- Preserve the atmospheric scene, selected-time authority, interactive timeline, screen-reader forecast browser, accessibility, RTL/reduced-motion semantics, and the hard triad of **responsiveness, predictable gesture ownership, and readability**. Decorative interpolation never fabricates hourly meteorological facts.
- Do not start production integration with an Android application-ID/signing migration, unrelated UI redesign, an official-alert subsystem, or a port of the deprecated application's architecture. Manual location search comes before any optional device-location permission.

**Owner-approved slice consolidation (2026-10-26):** These unstarted outcomes are combined into the retained earlier slice ID, with their requirements and acceptance coverage preserved: WX.4 into WX.3; WX.7 and WX.8 into WX.6; WX.14 into WX.13; WX.21 into WX.20. The absorbed IDs are retired and will not be reused. Completed IDs and outcomes are unchanged.

**Checkpoint gates:** WX.1–WX.3 and WX.5–WX.6 = authority, baseline, canonical contracts, and unchanged legacy behavior; WX.9–WX.11 = common source contract plus simulated and recorded 44-day datasets; WX.12–WX.13 = real 44-day Open-Meteo ingestion with honest provenance; WX.15–WX.20 = persistence, location selection, and safe catalog activation; WX.22 = debug source selection; WX.23–WX.25 = integrated 44-day timeline; WX.29 = midstream cross-app experience suite; WX.26–WX.27 = production startup and release hygiene; WX.28 = final application-wide integration and acceptance suite. Passing an intermediate gate does **not** imply overall production acceptance.

### WX.1 — Adopt successor-product authority and integration boundaries — DONE

**Objective/scope:** Documentation-only owner decision that this repository is the successor OxygenWX app; amend `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `AGENTS.md`, `README.md`, and relevant roadmap language to authorize future live data, location, cache, 44-day chronology, and hot-swap without authorizing implementation yet. Record the future `scripts/dev.py` contract change for WX.12; do not change that script in this documentation-only slice. Reconcile stale prototype exclusions and deprecated `P.3`/`P.4` integration assumptions; leave completed slice/history claims intact. Decide precedence relative to `ATM.8` and preserve exactly one `NEXT`.
**Depends on:** None.
**Exit:** A signed-off authority/decision record names the successor app, 44-day date convention, simulation/live parity, separate official alerts, existing interaction invariants, roadmap precedence, and non-goals; conflicting statements are enumerated and reconciled; docs workflow/format validation and diff check are recorded; no app sources or active cycle were silently changed.
**Concept artwork:** Waived by the owner on 2026-10-26 because WX.1 is a documentation-only change. No artwork is required for this cycle; the waiver does not change the general concept-art gate for other unfinished slices.
**Evidence:** Cycle 043 (`043-wx1-successor-authority`): [decision and verification artifacts](../.codex/test-artifacts/043-wx1-successor-authority/README.md); [closeout](../.codex/history/043-wx1-successor-authority.md); [archived plan](../.codex/history/plans/043-wx1-successor-authority.md).

### WX.2 — Inventory and regression-baseline the existing catalog and timeline — DONE

**Objective/scope:** Read-only audit of actual debug catalog, three transitions, profiles, eight-day normal sample, `ForecastSource`, `ForecastProjection`, `ElasticTimeAxis`, `TimeLabScreen`, atmosphere mapping, and present tests/device evidence. Document the independently generated `DebugForecastProfiles.profileFacts()` versus `DebugForecastCatalog.scenarios` relationship; current synthetic warning and snow/unknown visual mappings; `LocalDateTime`, 24-hour day, reference reset, and test-scaffold assumptions. No behavior changes.
**Depends on:** WX.1.
**Exit:** A source-path/line-grounded inventory, migration compatibility table, recorded snapshots or reproducibility plan, currently verified/unverified baseline, and specific owners for every legacy invariant exist; discrepancies and preexisting failures are dispositioned rather than silently fixed.
**Concept artwork:** Product-owner approved annotated current-versus-target dataflow board at `.codex/concepts/WX.2-baseline-inventory.png` (approval and constraints recorded in cycle 044 evidence).
**Evidence:** Cycle 044 (`044-wx2-baseline-inventory`): `.codex/test-artifacts/044-wx2-baseline-inventory/README.md`, `inventory.md`, `baseline.md`, and post-closeout owner disposition `owner-disposition.md`; closeout `.codex/history/044-wx2-baseline-inventory.md`; archived plan `.codex/history/plans/044-wx2-baseline-inventory.md`; approved board `.codex/concepts/WX.2-baseline-inventory.png`.

### WX.3 — Define canonical catalog identity and weather facts — DONE

**Objective/scope:** Define the provider-neutral catalog envelope and canonical nullable weather facts together. Include stable local location identity, coordinates/timezone, `asOf: Instant`, absolute valid times, dataset identity, source/product provenance, canonical units, ranges, and a composable condition/phenomena vocabulary. Cover existing clear, partly cloudy, cloudy, rain, snow, storm; distinguish precipitation and thunder probabilities, rain, total precipitation, snowfall, snow depth, wind/gust/direction, temperature, pressure, humidity, cloud, visibility, and optional severe-weather inputs only where supported. Keep official alerts and legacy synthetic warning outside canonical facts. Keep existing fixture and presentation paths unchanged; no storage, transport, or renderer changes.
**Depends on:** WX.2.
**Exit:** Pure construction and data-contract checks cover identity, timezone, reference instant, consistent source metadata, valid/ranged finite measurements, missing versus zero, distinct snowfall/depth, separate thunder and rain semantics, severe conditions without alerts, unsupported phenomena left absent, provider-neutral identity, identical live/simulated/replay envelopes, and no giant Cartesian-product condition enum. No provider DTO or synthetic-only field leaks into the canonical envelope.
**Concept artwork:** Product-owner approved on 2026-10-27 as shown: catalog identity, weather-fact, and provenance contract board at `.codex/concepts/WX.3-canonical-catalog-contract.png` with editable `.svg`.
**Evidence:** Cycle 045 (`045-wx3-canonical-catalog-contract`): `.codex/test-artifacts/045-wx3-canonical-catalog-contract/README.md`; closeout `.codex/history/045-wx3-canonical-catalog-contract.md`; archived plan `.codex/history/plans/045-wx3-canonical-catalog-contract.md`; approved concept `.codex/concepts/WX.3-canonical-catalog-contract.png` and editable `.svg`. The full unit suite retains the documented unrelated time-label assertion failure; focused WX.3 tests pass.

### WX.5 — Define 44-day date windows, coverage, and daily aggregate rules — DONE

**Objective/scope:** Introduce pure calendar window/coverage helpers, `CatalogDay`/daily aggregate rules, duplicate/out-of-order normalization policy, and explicit COMPLETE/PARTIAL/MISSING coverage at day/field level. Separate provider-supplied daily values from derived aggregates; do not infer complete totals from incomplete hours. No provider calls or renderer changes.
**Depends on:** WX.3.
**Exit:** Controlled-clock/location tests cover `D-30..D-1` and `D..D+13`, 44 date identities, midnight rollover, spring/fall DST, 23/25-hour local days, sparse hours, endpoint validation, duplicate-time policy, missing days and valid daily provenance; no tests require 24 hours per calendar day.
**Concept artwork:** Owner-approved on 2026-10-27 as shown in `.codex/concepts/WX.5-window-completeness.png` and editable `.svg`; constraints are recorded in plan 047. This is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 047 (`047-wx5-44-day-window-coverage`): `.codex/test-artifacts/047-wx5-44-day-window-coverage/README.md`; closeout `.codex/history/047-wx5-44-day-window-coverage.md`; archived plan `.codex/history/plans/047-wx5-44-day-window-coverage.md`; approved concept `.codex/concepts/WX.5-window-completeness.png` and editable `.svg`.

### WX.6 — Migrate existing forecast fixtures through canonical adapters — DONE

**Objective/scope:** Add pure canonical adapters for the existing immutable `DebugForecastCatalog`, its three transition sequences, all three generated debug profiles, and normal `SampleForecast`. Preserve the 12 scenario IDs and values, sequence step IDs/order/times, profile IDs/formulas and 7/11/14 loaded-day behavior, two-day buffers, reference times, and the eight-day normal fixture. Define scenario `validTime` versus sequence `sequenceTime`. Preserve existing forecast text, daily extrema, selected-time rules, atmospheric mappings, gesture ownership, app startup, and debug picker. Do not relocate debug fixtures into release sources, rewrite authored expectations, or make the app load a new source.
**Depends on:** WX.3 and WX.5.
**Exit:** Golden/snapshot comparisons show scenario facts and nulls, 27%/90% storm probabilities, transition identities/order, generated profile outputs/reference reset, synthetic warning preview, and normal sample presentation match the recorded baseline. Existing debug entry points remain usable, scenario tables remain absent from release, and the adapter does not introduce source DTOs into presentation. Document or guard intentional boundaries between catalog scenarios and independently generated `profileFacts`; build and contract checks pass.
**Concept artwork:** Owner-approved on 2026-10-27 as shown in `.codex/concepts/WX.6-fixture-adapters.png` with editable `.svg`; it is design input, not implementation or acceptance evidence.
**Evidence:** Cycle 049 (`049-wx6-fixture-adapters`): `.codex/test-artifacts/049-wx6-fixture-adapters/README.md`; closeout `.codex/history/049-wx6-fixture-adapters.md`; archived plan `.codex/history/plans/049-wx6-fixture-adapters.md`.

### WX.9 — Introduce one source and load-result contract — NEXT

**Objective/scope:** Define `WeatherCatalogSource.load(request): CatalogLoadResult` (or equivalent) for real, synthetic, and recorded providers, with request location/as-of/dataset ID, injectable clock, cancellation, typed success/partial/no-data/invalid/transport failure, and metadata. Implement source registration/injection without an active-source UI or data persistence. A cached LIVE dataset keeps LIVE origin with separate cache state.
**Depends on:** WX.5 and WX.6.
**Exit:** Stubbed independent live/simulated/replay providers return the same canonical type and identical consumer-facing result semantics; failed/no-data results cannot overwrite a valid active dataset; no transport DTO crosses the source boundary.
**Concept artwork:** Pending owner-approved source contract/outcome board at `.codex/concepts/WX.9-source-interface.png`.
**Evidence:** On planning, allocate chronological cycle paths and contract evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.10 — Generate one deterministic 44-day DEMO timeline — PLANNED

**Owner direction (2026-10-27):** Use one linear synthetic DEMO sequence for troubleshooting, as resolved in the `/side` conversation. Do not add scenario selection or multiple alternate timelines. The approved board defines the 44-day arrangement and the event families.
**Objective/scope:** Add one deterministic `SimulatedCatalogSource` dataset for a supplied `CatalogLocation` and `asOf`. Anchor the 44 local dates to `D = asOf.atZone(location.zoneId).toLocalDate()`: D−30…D−1 are historical dates and D…D+13 are forecast dates. Keep two explicit clear buffer days at each edge (D−30, D−29, D+12, D+13) and arrange the 40-day event core across D−28…D+11 as one fixed linear progression. Include the approved rain, snow, sleet, thunderstorm, heavy-rain/flash-flood-condition, clear/partly-cloudy, haze, fog, and high-wind examples; these are synthetic weather facts, never official alerts. Preserve explicit missingness and unsupported fields. Keep the legacy 7/11/14-day debug profile generators and outputs unchanged. Data-only: no UI, network, active-source wiring, or scenario picker.
**Depends on:** WX.6 and WX.9.
**Exit:** The same location/as-of request produces an identical canonical 44-date catalog with the same single event order and explicit clear buffers; no scenario or alternate-sequence input exists. Tests cover local-date anchoring, the 30/14 historical/forecast split, 40-day event core, both clear edges, DST/date policies, missing versus zero, synthetic provenance, weather-fact plausibility, and no alert semantics. Existing 7/11/14 debug profiles remain unchanged.
**Concept artwork:** Owner-approved 2026-10-27 as shown in `.codex/concepts/WX.10-simulator.png` with editable `.svg`; this is data-shape design input, not UI or implementation acceptance evidence.
**Evidence:** On planning, allocate chronological cycle paths and fixed sequence fixtures. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.11 — Implement versioned recorded-catalog replay — PLANNED

**Objective/scope:** Define a stable serialized canonical catalog recording with schema version, location, source/provider/product timestamps, nullable fields, and deterministic replay loader. Support recording approved sanitized provider fixtures without network calls. No live transport, UI, or editing raw scenario tables.
**Depends on:** WX.9 and WX.10.
**Exit:** Sample recorded real-like and synthetic catalogs round-trip without losing units, timezones, source/product identity, severe-weather fields or missingness; malformed/old schemas fail or migrate explicitly; replay produces the same canonical type as simulation.
**Concept artwork:** Pending owner-approved serialization and replay-state board at `.codex/concepts/WX.11-record-replay.png`.
**Evidence:** On planning, allocate chronological cycle paths and versioned fixture evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.12 — Add the Open-Meteo transport and revise prototype isolation guard — PLANNED

**Objective/scope:** Add minimal network transport with injectable HTTP client, timeouts/cancellation, request configuration, and mocked transport fixtures. Update manifest permissions as required and change `scripts/dev.py contract` from prohibiting any network/location capability to enforcing approved provider/release boundaries; do not remove unrelated package/gesture/isolation assertions. Verify current Open-Meteo variables, rate/attribution/license requirements, and product meaning. No provider-to-catalog mapping or app wiring.
**Depends on:** WX.1 and WX.9.
**Exit:** Recorded request/failure tests cover HTTP errors, cancellation, timeout and transport parsing boundaries; contract script no longer blocks approved Internet permission but rejects unintended location/background/debug exposure; provider policy/attribution assumptions and documented endpoint capabilities have auditable references.
**Concept artwork:** Pending owner-approved transport/security boundary board at `.codex/concepts/WX.12-transport-guards.png`.
**Evidence:** On planning, allocate chronological cycle paths and transport/policy evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.13 — Build the Open-Meteo catalog source — PLANNED

**Objective/scope:** Implement `OpenMeteoCatalogSource` using the common source contract. Add provider DTO decoding, pure mapping, and catalog assembly for the agreed hourly/daily variables, canonical units, weather codes, nullable/unknown fields, timestamp validity, source/model provenance, and optional severe inputs. Request `past_days=30`, `forecast_days=14`, metric units, coordinates, `timeformat=unixtime`, and agreed hourly/daily fields. Convert returned instants through the location's IANA timezone and assemble target date identities with explicit partial coverage. Do not use a fixed request-time UTC offset to label dates across DST. Treat past Forecast API samples as **archived model/forecast data**, not station observations. Never infer independent thunder/hail/tornado values from WMO codes or generic precipitation. Support missing hours, short horizons, and failures without filling facts. No location UI or active-app wiring.
**Depends on:** WX.3, WX.5, WX.9, and WX.12.
**Exit:** Recorded complete, sparse, malformed, duplicate/out-of-order, unknown-code, and null-variable fixtures validate transport parsing, mapping, and assembly. Metrics match canonical units; unsupported severe probabilities remain null; daily provider-supplied versus derived summaries and per-segment product metadata are accurate. Request fixtures verify `D-30..D+13` membership, timezone/DST conversion using Unix instants and IANA rules, attribution, product distinctions, partial/no-data/failure outcomes, and no unsupported hazard claims. A bounded optional live smoke, when approved, records actual fields and coverage instead of assuming complete 44-date coverage.
**Concept artwork:** Pending owner-approved provider fields, normalization, request, and 44-day assembly board at `.codex/concepts/WX.13-openmeteo-catalog-source.png`.
**Evidence:** On planning, allocate chronological cycle paths and fixture/live-smoke evidence as authorized. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.15 — Persist versioned per-location datasets and catalog history — PLANNED

**Objective/scope:** Implement durable, versioned storage for canonical datasets keyed by stable location ID + origin/dataset ID; prefer an indexed database if incremental historical updates and multi-location queries justify it, otherwise document and validate an atomic serialized format. Preserve current source metadata, separate LIVE/SIMULATED/REPLAY namespaces, and define retention/schema migration. No UI, provider refresh, or geocoding.
**Depends on:** WX.11 and WX.13.
**Exit:** Persistence round-trips 44-day live/simulated/replay datasets, missing facts, per-record provenance, and DST timestamps; atomic writes prevent partial corruption; schema version failure/migration is explicit; removing one location/dataset leaves all others untouched; no simulation overwrites live cache.
**Concept artwork:** Pending owner-approved location/dataset storage schema at `.codex/concepts/WX.15-storage.png`.
**Evidence:** On planning, allocate chronological cycle paths and storage/migration evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.16 — Implement cache freshness, refresh, and partial recovery — PLANNED

**Objective/scope:** Add catalog repository refresh/state policy for live data, preserving last useful cached data on network failure, distinguishing fresh/cached/stale/failed/partial outcomes and rolling the `asOf` local-date window explicitly. Keep history and forecast refresh schedules separable for future archive providers. Do not present a failed refresh as new live weather. No UI yet.
**Depends on:** WX.13 and WX.15.
**Exit:** Controlled-clock repository checks prove fresh fetch, cache-only startup, stale display, failed refresh with/without cache, corrupt cache, cache-write failure with still-usable live data, midnight rollover, and partial provider windows, preserving truthful provenance and location identity.
**Concept artwork:** Pending owner-approved refresh/freshness state-transition board at `.codex/concepts/WX.16-cache-refresh.png`.
**Evidence:** On planning, allocate chronological cycle paths and state-machine evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.17 — Add manual geocoding/location search provider contract — PLANNED

**Objective/scope:** Define provider-neutral place-search results and implement an initial Open-Meteo geocoding adapter with usable stable local ID assignment, returned coordinates, correct IANA timezone, ambiguity/error/no-result behavior, and documented attribution. No Android device-location permission or saved-location UI.
**Depends on:** WX.3 and WX.12.
**Exit:** Recorded search fixtures cover duplicate place names, differing timezones, coordinate precision, empty/error results, and provider metadata; outputs can create a `CatalogLocation` without provider IDs becoming internal storage keys.
**Concept artwork:** Pending owner-approved geocoding-to-location identity board at `.codex/concepts/WX.17-location-search-source.png`.
**Evidence:** On planning, allocate chronological cycle paths and location mapping evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.18 — Persist selected and saved locations as application state — PLANNED

**Objective/scope:** Add stable local saved-location records, add/remove/select persistence, selected-location restoration, and explicit location-to-catalog association independent of data origin. Implement domain/controller/storage contracts only; no search/switching UI, no foreground GPS permission.
**Depends on:** WX.15 and WX.17.
**Exit:** State/storage checks prove saved locations survive relaunch, distinct coordinate/timezone locations remain distinct, removing one location handles its catalog retention policy explicitly, and restored selection cannot accidentally point to a different provider place with the same display name.
**Concept artwork:** Pending owner-approved saved-location domain/state board at `.codex/concepts/WX.18-saved-locations.png`.
**Evidence:** On planning, allocate chronological cycle paths and location persistence evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.19 — Build accessible manual search and saved-location selector — PLANNED

**Objective/scope:** Add a compact owner-reviewed location search/add/remove/select interface that coexists with, but does not replace, the immersive timeline. Handle progress, ambiguous matches, no results, request failure, current selection, and saved locations. This slice does not change the weather renderer or implement data-source hot-swap UI.
**Depends on:** WX.17 and WX.18.
**Exit:** Focused flow checks show searching, saving, selecting, deleting, and restoring locations without location permission; control labels and state semantics identify the selector's own actions and current selection; the selected location identity is unambiguous. Cross-app TalkBack, font-scale, orientation, RTL, and reduced-motion checks are assigned to WX.29.
**Concept artwork:** Pending owner-approved interaction storyboard at `.codex/concepts/WX.19-location-selector.png`.
**Evidence:** On planning, allocate chronological cycle paths, installed captures, and focused state/UI evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.20 — Load and atomically activate location-scoped catalogs — PLANNED

**Objective/scope:** Wire selected-location identity to source loading and implement location-scoped `ActiveCatalogSelection` with atomic validated dataset activation. Add load state, cancellation/sequence tokens, per-location and per-origin cache namespaces, and location-aware refresh requests. Reject late results from previous locations or older same-location requests. Switching between live, simulated, and replay data replaces the whole catalog, preserves selected `Instant` when available or clamps to the nearest valid sample, and retains the previous valid state on failure. Keep storage/retrieval and renderer independent; no debug selector UI.
**Depends on:** WX.10, WX.11, WX.15, WX.16, and WX.18.
**Exit:** Controlled asynchronous checks cover A→B→A, repeated same-location refresh, failures, cache restore, and app recreation; only the newest matching location/request can publish active weather. LIVE↔SIMULATED↔REPLAY switches contain no mixed fields, metadata, or locations; failed replacement is safe; live cache survives simulation; selected-instant retention/clamping and view-generation markers are deterministic; non-selected datasets remain unchanged; switching requires no app restart.
**Concept artwork:** Pending owner-approved location request, race handling, and atomic source-activation board at `.codex/concepts/WX.20-catalog-activation.png`.
**Evidence:** On planning, allocate chronological cycle paths, source-race checks, and atomicity evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.22 — Expose debug-only dataset selection and replay controls — PLANNED

**Objective/scope:** Add an isolated debug activity/picker to choose active location dataset and existing/new scenarios, replay IDs, and live mode; build on the existing working debug profile picker without adding permanent controls to the immersive timeline. Mark simulated provenance, show explicit load failures, and verify debug-only gating. No official-alert controls.
**Depends on:** WX.20.
**Exit:** Installed debug build selects all three origins and existing profiles, changes application data without process restart, shows source labels, recovers on rejected data, and passes release manifest/DEX/source-set isolation checks; timeline controls and legacy profile entry remain usable.
**Concept artwork:** Pending owner-approved debug selector storyboard at `.codex/concepts/WX.22-source-picker.png`.
**Evidence:** On planning, allocate chronological cycle paths, source-switch recordings, and release inspection. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.23 — Implement time-indexed canonical presentation without 24-hour assumptions — PLANNED

**Objective/scope:** Extend the presentation boundary to project sparse canonical `Instant`-keyed hours and local `CatalogDay`s, with nullable facts, source/product labels, daily extrema, exact nearest-hour meteorological samples, and interpolated *decorative scene only*. Retain the old `ForecastSource` adapter for the working fixtures until their migration completes; do not feed 44-day/DST data into the old full-24-hour validator. No touch or layout change.
**Depends on:** WX.5, WX.6, WX.13, and WX.20.
**Exit:** Pure projection tests cover 23/25-hour and missing-hour dates, unknown measurements, historical versus forecast semantics, exact sample selection, distinct snow visual identity, provenance, stable unit conversion, and unchanged existing fixture presentations; no precipitation unknown is silently displayed as measured zero.
**Concept artwork:** Pending owner-approved legacy/new projection boundary board at `.codex/concepts/WX.23-time-projection.png`.
**Evidence:** On planning, allocate chronological cycle paths and projection compatibility evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.24 — Migrate the pure ElasticTimeAxis to absolute instants — PLANNED

**Objective/scope:** Refactor the pure time-axis/time-selection logic from local-day `selectedHour` assumptions to absolute time with local-calendar day navigation, duration-correct hour spacing, selected-instant persistence, and variable day lengths. Define repeated/nonexistent local-hour selection, daily overview anchors, source-swap preservation/clamping, and real-now versus debug-reference reset. Maintain the existing gesture owners; no visual redesign.
**Depends on:** WX.23.
**Exit:** Pure tests prove monotonic/clamped coordinate mapping, no selected-time jump on zoom, spring/fall DST, day shifts preserving local hour where possible, sparse interval navigation, tap/hold/drag/release semantics, and correct reset/clamp behavior. Previously verified fixture interactions continue to pass through compatibility routes.
**Concept artwork:** Pending owner-approved DST/day-navigation time-axis storyboard at `.codex/concepts/WX.24-absolute-axis.png`.
**Evidence:** On planning, allocate chronological cycle paths and time-axis interaction evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.25 — Bind the immersive timeline and forecast browser to 44 days — PLANNED

**Objective/scope:** Connect `TimeLabScreen`, its visual tape, accessible forecast browser, and atmospheric projection to the active canonical catalog/time axis for 30 historical plus 14 forecast dates. Retain the current full-screen scene and gestures; display historical/forecast meaning, provenance, freshness, current missing data and selected time without source-based presentation branches. This slice integrates supported visual facts; it does **not** duplicate existing `ATM.8`–`ATM.10` scene-polish outcomes or implement official alerts.
**Depends on:** WX.22, WX.23, and WX.24.
**Exit:** Focused installed Pixel 3 XL checks scrub across history/today/forecast boundaries and dataset switches; exact timestamps/facts/source labels agree with the selected catalog; the forecast browser exposes usable day/hour rows to accessibility services; day/night/snow/storm mapping does not fabricate unsupported facts; responsiveness, gesture arbitration, and readability receive evidence-backed dispositions. Cross-app font-scale, orientation, RTL, TalkBack, and reduced-motion checks are assigned to WX.29.
**Concept artwork:** Pending owner-approved 44-day immersive timeline/provenance storyboard at `.codex/concepts/WX.25-timeline-44-days.png`.
**Evidence:** On planning, allocate chronological cycle paths, installed captures, and interaction evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.29 — Run cross-app experience and accessibility suite — PLANNED

**Objective/scope:** Run a bounded installed suite across the integrated location selector, 44-day timeline, and forecast browser. Cover limited TalkBack traversal of the primary forecast summary, location selection, timeline scale action, and forecast day/hour rows; default and 1.3× font scale; portrait and landscape; RTL; reduced motion; and the established responsiveness, gesture-predictability, and readability checks. This slice records findings only; corrections belong in separately scoped implementation slices.
**Depends on:** WX.19 and WX.25.
**Exit:** A coverage matrix records each condition as **PASS/FAIL/NOT RUN**, with device/build details, actual installed captures, exact TalkBack actions and observed announcements, and evidence-backed triad dispositions. Any failure has a named follow-up slice or recorded blocker; no broad app changes are included here.
**Concept artwork:** Pending owner-approved cross-app acceptance coverage storyboard at `.codex/concepts/WX.29-cross-app-experience-suite.png`.
**Evidence:** On planning, allocate chronological cycle paths, installed captures, accessibility notes, and the coverage matrix. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.26 — Compose production startup and selected-location experience — PLANNED

**Objective/scope:** Replace the normal-app unconditional `SampleForecast` startup with a location-aware controller: restore selected saved location and valid cache, initiate optional manual location selection when none exists, allow explicit foreground refresh, and show loading/live/cached/stale/failed provenance. Keep `SampleForecast` accessible only as a clearly labeled development fallback, not attributed to searched locations. No background location tracking or real-alert system. Cross-app layout and accessibility conditions are exercised in WX.29 and repeated in WX.28 after startup changes.
**Depends on:** WX.19, WX.20, and WX.25.
**Exit:** Focused flow checks cover first launch, saved-location relaunch, offline launch, location switch, source refresh, and failed live fetch with/without cache. Each state shows truthful labels/data; no synthetic data masquerades as real local weather and the immersive timeline remains usable. Cross-app TalkBack, font-scale, orientation, RTL, and reduced-motion acceptance is scheduled in WX.29 and repeated in WX.28 after startup/branding changes.
**Concept artwork:** Pending owner-approved startup/cache/location flow storyboard at `.codex/concepts/WX.26-startup-experience.png`.
**Evidence:** On planning, allocate chronological cycle paths and installed state/evidence captures. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.27 — Adopt OxygenWX product labels and audit release-data boundaries — PLANNED

**Objective/scope:** Replace experimental user-facing Time Lab branding with owner-approved OxygenWX naming, align permissions, source attribution/modified-data notices, privacy and debug/release packaging with actual implemented capabilities, and make an explicit Android application-ID/signing/migration decision **without silently changing identity**. Do not copy the deprecated app or claim official alerts. Coordinate rather than duplicate deferred `P.4` slices.
**Depends on:** WX.26 and the owner-approved WX.1 product decision.
**Exit:** Release-installed app presents approved OxygenWX labels and accurate provider/freshness context; manifests, permissions, source/DEX scan, security/attribution and legacy-app coexistence or replacement decision are recorded; synthetic data and debug picker are absent from release; no unsupported weather warning claims or misleading DEMO DATA labels exist.
**Concept artwork:** Pending owner-approved product identity/source placement storyboard at `.codex/concepts/WX.27-release-identity.png`.
**Evidence:** On planning, allocate chronological cycle paths and release manifest/APK/installed evidence. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

### WX.28 — Run final application-wide integration and release acceptance — PLANNED

**Objective/scope:** Execute the final bounded integration and release acceptance for the implemented catalog and app experience; publish a coverage/limitations matrix without hiding fixes in the verification cycle. Exercise real/recorded/simulated sources, 30+14 date coverage, archived-past labeling, DST, nullable severe fields, source hot-swap and races, multi-location persistence, offline/lifecycle behavior, and debug/release isolation. Repeat the WX.29 cross-app suite after startup/branding changes: limited TalkBack paths, default and large font scale, portrait and landscape, RTL, reduced motion, and the responsiveness/gesture/readability checks. Confirm registered devices during planning; Pixel 3 XL remains the reference. Handle defects as separate corrective slices.
**Depends on:** WX.10, WX.11, WX.13, WX.16, WX.19, WX.22, WX.25, WX.26, WX.27, and WX.29.
**Exit:** An owner-reviewable report records each scoped scenario as **PASS/FAIL/NOT RUN**, with exact commands/builds/device details, installed captures, source/units/provenance agreement, target-versus-actual date coverage, UX-suite results, remaining blockers, and release risks. Mark DONE only when the recorded release acceptance criteria pass; fixes require separate slices.
**Concept artwork:** Pending owner-approved acceptance coverage and evidence storyboard at `.codex/concepts/WX.28-acceptance-matrix.png`.
**Evidence:** On planning, allocate chronological cycle paths and integrated verification matrix/captures. Target paths when planned: `.codex/test-artifacts/<cycle-id>/`; closeout `.codex/history/<cycle-id>.md`; plan `.codex/plans/<cycle-id>.md`; archived plan `.codex/history/plans/<cycle-id>.md`.

**Definition of this workstream's completion:** The successor OxygenWX app can search/save/select multiple locations; retrieve and persist a per-location 44-local-date **target** catalog with truthful complete/partial/missing coverage; display 30 past dates (archived model forecast initially, not falsely labeled observation) and 14 forecast dates on its immersive timeline; generate fully canonical 44-day simulated scenarios from the preserved working debug system; replay recorded datasets; atomically hot-swap all three sources in debug builds; retain accurate weather/severe-field/provenance semantics; support offline restoration and safe concurrent requests; preserve timeline/atmosphere/accessibility quality; and exclude developer simulation tools from the production build. Official weather alerts, GPS/background tracking, and a dedicated station-observation/reanalysis source are separate future decisions, **not silently included here**.

**Primary source references for planning (revalidate during source implementation):** `docs/ROADMAP_SPECIFICATION.md`, `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, `.codex/current.md`, current `app/src/debug/.../DebugForecastCatalog.kt` and `DebugForecastProfiles.kt`, current `app/src/main/.../presentation/ForecastSource.kt` / `ForecastProjection.kt` / `ElasticTimeAxis.kt`, and `scripts/dev.py`. Provider documents: https://open-meteo.com/en/docs , https://open-meteo.com/en/docs/historical-weather-api , and https://open-meteo.com/en/docs/historical-forecast-api .

## P.1 — Standalone elastic time tape

### P.1.1 — Implement the isolated timeline prototype — DONE

**Objective/scope:** Establish the standalone Android Compose scaffold, deterministic canonical-unit forecast fixture, typed presentation, pure time projection, atmospheric scene, and core time-tape interactions without changing oxygenWX.
**Depends on:** None.
**Exit:** The isolated app and core timeline implementation exist, architecture/isolation checks are recorded, and remaining installed acceptance is tracked separately.
**Evidence:** `.codex/history/001-elastic-timeline-prototype.md`. Cycle 001 explicitly records Android build, install, and device evidence as outstanding at that time; see P.1.2 for the later launch baseline.

### P.1.2 — Install and record the device baseline — DONE

**Objective/scope:** Build, install, launch, and capture the existing standalone app on the Pixel 3 XL; record representative timeline states without changing application source.
**Depends on:** P.1.1.
**Exit:** Installed foreground app is confirmed and actual device screenshots plus reproduction/device details are recorded. This exit does not imply full accessibility or environment acceptance.
**Evidence:** `.codex/history/002-p1-pixel-install.md`, `.codex/history/007-visual-baseline.md`, and local captures under `.codex/test-artifacts/002-p1-pixel-install/` and `.codex/test-artifacts/007-visual-baseline/` (including the later hourly long-press capture documented in that folder's README).

### P.1.3 — Run Android unit tests and lint — DONE

**Objective/scope:** Run the app's debug unit-test and lint tasks on an Android host; diagnose only failures in those tasks.
**Depends on:** P.1.1.
**Exit:** `:app:testDebugUnitTest` and `:app:lintDebug` both pass, or a reproducible blocker with command output and affected scope is recorded; do not mark DONE on an unexplained failure.
**Evidence:** `.codex/history/010-android-host-checks.md`; logs and host details under `.codex/test-artifacts/010-android-host-checks/`.

## P.2 — Owner review, visual polish, and installed acceptance

### P.2.1 — Uniform hourly scale and selected-time label — DONE

**Objective/scope:** Replace localized focus-day expansion with a uniform hourly scale and magnify the selected day/hour label while keeping it centered at the playhead.
**Depends on:** P.1.1.
**Exit:** Daily/hourly labels and ticks show the uniform scale and selected-time cue in installed captures; selected time remains authoritative through scale animation.
**Evidence:** `.codex/history/003-time-label-magnification.md`; captures under `.codex/test-artifacts/003-time-label-magnification/`.

### P.2.2 — Subtle timeline playhead — DONE

**Objective/scope:** Keep the selected position clear with a small axis marker while making the selected day/hour label the primary cue.
**Depends on:** P.2.1.
**Exit:** Installed capture shows the axis marker at the selected position without a full-height playhead obscuring the scene or labels.
**Evidence:** `.codex/history/004-subtle-playhead.md`; `.codex/test-artifacts/004-subtle-playhead/pixel-3xl.png`.

### P.2.3 — Main-display day navigation and realignment — DONE

**Objective/scope:** Shift selected time by 24 hours on a main-display horizontal swipe while preserving hour-of-day, and realign by double-tap within fixture bounds.
**Depends on:** P.2.1.
**Exit:** On-device left/right swipes and double-tap produce the specified selected-time changes, with actual outcome recorded.
**Evidence:** `.codex/history/005-main-display-navigation.md`; captures under `.codex/test-artifacts/005-main-display-navigation/`.

### P.2.4 — Remove visible hints and expose the scale action — DONE

**Objective/scope:** Remove prototype instructional/mode-control rows and the NOW button; retain direct gestures, double-tap realignment, and the timeline's screen-reader scale action.
**Depends on:** P.2.3.
**Exit:** Installed UI has no removed visible controls, and source/semantics expose the daily/hourly custom accessibility action. Manual TalkBack acceptance remains a separate slice.
**Evidence:** `.codex/history/006-remove-timeline-hints.md`; `.codex/test-artifacts/006-remove-timeline-hints/pixel-3xl.png`.

### P.2.5 — Verify timeline gesture arbitration and time stability — DONE

**Objective/scope:** On the Pixel 3 XL, review immediate drag versus stationary hold, hourly scrubbing, release/collapse, and selected-time stability; assess responsiveness, gesture predictability, and readability. Fixes are out of scope for this evidence-gathering slice.
**Depends on:** P.1.2, P.2.1, P.2.4, and P.2.5a.
**Exit:** Each gesture is exercised and its observed result recorded with screenshots; selected timestamp continuity and all three cross-cutting visual-quality checks receive an evidence-backed pass/fail disposition. Confirmed defects get separate implementation slices.
**Evidence:** Original evidence and blocker investigation: `.codex/history/010-gesture-acceptance.md`, `.codex/test-artifacts/010-gesture-acceptance/`, and `.codex/test-artifacts/011-blocker-investigation/README.md`. Recheck: `.codex/test-artifacts/012-gesture-acceptance-recheck/README.md` and `.codex/history/012-gesture-acceptance-recheck.md`.

### P.2.5a — Align forecast time label with selected hourly tick — DONE

**Objective/scope:** Make the forecast summary's displayed hour use the same nearest-hour convention as the hourly tape's selected tick, while preserving fractional selection and interpolated forecast values during a drag.
**Depends on:** P.2.1.
**Exit:** On the installed Pixel 3 XL, fractional-hour positions no longer show different hours in the forecast summary and selected tape label; after release/collapse and stationary re-entry, the same rounded selected hour remains visible. The change does not quantize selection during movement or alter interpolated values.
**Evidence:** `.codex/history/011-hour-label-alignment.md`; `.codex/test-artifacts/011-hour-label-alignment/`.

### P.2.6 — Review readability at large text and constrained layouts — DONE

**Objective/scope:** Review representative large system font, landscape/small-screen layout, and bright/dark sky contrast on the installed app. Fixes are out of scope for this evidence-gathering slice.
**Depends on:** P.1.2.
**Exit:** Actual screenshots for each condition are reviewed; essential forecast values and timeline labels are readable and unclipped, with findings and any confirmed defect ownership recorded. Confirmed defects get separate implementation slices.
**Evidence:** Prior findings and recovery: `.codex/history/013-responsive-readability.md`, `.codex/history/014-responsive-readability-recovery.md`, and `.codex/history/014-landscape-high-low.md`; final fresh installed review: `.codex/history/015-responsive-readability-review.md` and `.codex/test-artifacts/015-responsive-readability-review/`.

### P.2.12 — Restore Android build host and complete P.2.6 evidence — DONE

**Objective/scope:** Provision official Android SDK command-line tools and required SDK packages on the current host, build/install the current prototype on the Pixel 3 XL, and complete the missing exact-time and continuous-contact gesture evidence for P.2.6. Evidence and host setup only; no app changes.
**Depends on:** None.
**Exit:** A fresh build succeeds with supported tooling; required installed captures show the exact bright/night fixture times; immediate drag and continuous hold-then-drag outcomes, selected-time continuity, and all P.2.6 condition dispositions are recorded. If a host/device/tool blocker prevents the exit, record it with command output and preserve P.2.6 as BLOCKED.
**Evidence:** `.codex/history/014-responsive-readability-recovery.md`; `.codex/test-artifacts/014-responsive-readability-recovery/`.

### P.2.11 — Preserve high/low values in landscape at large font — DONE

**Objective/scope:** Keep the forecast high/low values visible as text in the compact landscape layout at 1.3 system font scale; they are absent from the installed capture. Preserve selected-time authority, timeline responsiveness, predictable gesture ownership, accessible text, and DEMO DATA semantics. Review compact landscape and 1.3 font scale; record RTL and reduced-motion applicability and verify they remain unaffected if layout/animation behavior changes.
**Depends on:** None.
**Exit:** Actual installed Pixel 3 XL captures show both high/low values as readable text alongside the date, heading, other essential forecast values, DEMO DATA label, and timeline labels at compact landscape and 1.3 font scale. Responsiveness, gesture ownership, and readability receive evidence-backed dispositions; RTL and reduced-motion applicability are recorded.
**Evidence:** Finding: `.codex/history/013-responsive-readability.md`; installed captures and review: `.codex/test-artifacts/014-landscape-high-low/README.md`; outcome: `.codex/history/014-landscape-high-low.md`.

### P.2.7 — Accept the TalkBack scale action — DONE

**Objective/scope:** Manually traverse the timeline with TalkBack and invoke its daily/hourly custom action; verify understandable semantics and selected-time continuity.
**Depends on:** P.2.4.
**Exit:** TalkBack reaches the timeline, announces useful control/state semantics, invokes the daily/hourly scale action in both directions, and records the spoken label, visible scale, and selected-time continuity after each action. Any confirmed defect gets a separate implementation slice.
**Evidence:** Prior attempts: `.codex/test-artifacts/016-talkback-timeline/README.md`, `.codex/history/016-talkback-timeline.md`, `.codex/test-artifacts/017-talkback-timeline-recheck/README.md`, and `.codex/history/017-talkback-timeline-recheck.md`. Installed acceptance and limitations: `.codex/test-artifacts/019-talkback-scale-action/README.md` and `.codex/history/019-talkback-scale-action.md`.

### P.2.13 — Add a screen-reader forecast browser — DONE

**Objective/scope:** Provide a TalkBack-actionable, linear forecast browser with daily/hourly rows sourced from the same forecast projection and selected-time state as the visual timeline. Keep decorative ticks and animation out of the spoken data path; do not add a visible scale-control row.
**Depends on:** P.2.4.
**Exit:** The timeline exposes a standard accessibility click plus a named custom action to open the browser; daily and hourly rows expose their date/time and forecast values, identify the selected row, and selecting a row updates the existing timeline and summary timestamp. Installed Pixel 3 XL evidence shows the browser in daily/hourly states and the hourly list scrolled to the selected Sunday, Oct 25, 12 PM row. This does not replace the separate manual TalkBack scale-action acceptance in P.2.7.
**Evidence:** `.codex/test-artifacts/018-talkback-forecast-browser/README.md`, installed screenshots and accessibility hierarchy; closeout: `.codex/history/018-talkback-forecast-browser.md`.

### P.2.8 — Verify reduced-motion behavior — DONE

**Objective/scope:** Review the installed app with the platform reduced-motion/animation setting enabled and record the bounded behavior needed for this prototype. This slice gathers acceptance evidence; any implementation fix gets its own slice.
**Depends on:** P.1.2.
**Exit:** The relevant system setting and app response are recorded; selected time and scale remain understandable without disorienting motion, and any required change has its own bounded slice and evidence.
**Evidence:** `.codex/test-artifacts/020-reduced-motion/README.md` and `.codex/history/020-reduced-motion.md`.

### P.2.9 — Verify RTL and close installed acceptance — DONE

**Objective/scope:** Review timeline chronology, gesture directions, and labels under a system RTL locale. Reconcile the installed acceptance evidence; use existing TalkBack acceptance without repeating TalkBack tests in this cycle, per the product owner's direction to revisit before final production release.
**Depends on:** P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11.
**Exit:** Installed RTL screenshots and direct-touch observations establish that chronology, gesture directions, fixture bounds, and visible labels remain understandable. Existing P.2.7/P.2.13 TalkBack outcomes are linked without retest; RTL-specific spoken behavior is recorded as unverified per the product owner's decision to revisit TalkBack before final production release. The required P.2 evidence and limitations are reconciled, the three cross-cutting visual-quality checks are recorded, and residual scope is explicitly dispositioned. Confirmed defects get separate implementation slices.
**Evidence:** Cycle 021 (`021-p2-acceptance-closure`): `.codex/test-artifacts/021-p2-acceptance-closure/README.md` and installed captures; closeout: `.codex/history/021-p2-acceptance-closure.md`. Existing P.2 evidence is linked from the history record.

### P.2.14 — Group precipitation and wind metrics with visual signals — DONE

**Objective/scope:** Replace the visible `PRECIP CHANCE` and `WIND` headings with the approved rain-signal and compass indicators, keeping the precipitation value on the left and wind speed on the right as visible text grouped with their indicators. Add an optional synthetic wind-direction fact to the normal demo fixture so the compass needle is data-driven; represent unknown direction with a neutral compass. Preserve the full-screen scene, DEMO DATA provenance, selected-time behavior, and bottom timeline.
**Depends on:** None.
**Exit:** The installed Pixel 3 XL reference-state capture matches the approved metric arrangement: no visible metric headings; rain signal above its probability value; compass above wind speed; both values remain readable text. The compass face and placement remain fixed and north-up, while its non-animated needle reflects the selected sample's wind-from direction and is absent when direction is unknown. Focused tests cover bearing validation/projection, NE mapping, and unknown-direction handling; existing precipitation and wind formatting remains unchanged. This slice does not run application-wide accessibility/TalkBack, font-scale, RTL, reduced-motion, responsiveness, or gesture-ownership suites; those remain in their dedicated suite cycle(s).
**Concept artwork:** Owner approved 2026-10-26 at `docs/mockups/approved-precipitation-wind-metrics.png`; installed source baseline at `docs/mockups/app_screen_original.png`.
**Evidence:** Cycle 042 (`042-p2-wind-metrics`): `.codex/test-artifacts/042-p2-wind-metrics/README.md`, including installed baseline and target captures; closeout `.codex/history/042-p2-wind-metrics.md`; archived plan `.codex/history/plans/042-p2-wind-metrics.md`.

### P.2.15 — Keep hourly precision active until an explicit tap — DONE

**Objective/scope:** Preserve hourly precision after the activating hold-and-drag is released. A tap on the main forecast area exits hourly mode without changing the selected time; a tap on the timeline exits hourly mode and selects the time under the tap. Update the adopted interaction contract and implement the bounded gesture-state change without new visible controls.
**Depends on:** P.2.1, P.2.3, and P.2.5.
**Exit:** Installed Pixel 3 XL interaction evidence confirms hold-and-drag enters hourly mode, release ends dragging while hourly mode remains visible, main-area tap returns to daily mode with the same selected time, and timeline tap returns to daily mode at the tapped time. Existing timeline labels, forecast values, gesture ownership, and accessibility scale semantics remain intact. Workflow, contract, debug build/install, and `git diff --check` pass. No tests are added or run in this cycle.
**Concept artwork:** Owner waived the UI artwork requirement for this slice only on 2026-10-27. `.codex/concepts/P.2.15-hourly-mode-retention.svg` is retained as an unapproved interaction storyboard, not a gate or acceptance artifact.
**Evidence:** Cycle 046 (`046-p2-hourly-mode-retention`): `.codex/test-artifacts/046-p2-hourly-mode-retention/README.md`; closeout `.codex/history/046-p2-hourly-mode-retention.md`; archived plan `.codex/history/plans/046-p2-hourly-mode-retention.md`.

### P.2.16 — Make hourly scrubbing continuous and directionally consistent — DONE

**Objective/scope:** Correct the reported lag and uneven forward/backward response while scrubbing the uniform hourly timeline. Keep the selected timestamp authoritative and make finger movement produce continuous, predictable hourly selection in both directions. Preserve the 480 ms hold, uniform hourly spacing, sticky precision mode, gesture ownership, timeline layout, and all accessibility actions. Limit source changes to the timeline gesture/projection and drawing path needed to meet the responsiveness objective; do not redesign the experience or change forecast data.
**Depends on:** P.2.15.
**Exit:** On the installed Pixel 3 XL, repeated forward and backward scrubs show continuous selected-time movement with comparable response to equal finger travel, no visible lag during and after precision entry, and no time jump from scale animation. Hold activation, selected-time stability, clamping, release persistence, explicit tap exits, timeline readability, and forecast values remain intact. Record responsiveness, predictable gesture ownership, and readability with installed evidence. Workflow, contract, debug build/install, and `git diff --check` pass. Do not add or run tests unless requested.
**Concept artwork:** Owner waived the concept artwork requirement for P.2.16 only on 2026-10-27. `.codex/concepts/P.2.16-hourly-scrub-responsiveness.svg` remains an unapproved planning aid, not a gate or acceptance artifact. The waiver does not apply to other slices.
**Evidence:** Cycle 048 (`048-p2-hourly-scrub-responsiveness`): `.codex/test-artifacts/048-p2-hourly-scrub-responsiveness/README.md`; closeout `.codex/history/048-p2-hourly-scrub-responsiveness.md`; archived plan `.codex/history/plans/048-p2-hourly-scrub-responsiveness.md`.

## P.3 — Deprecated oxygenWX integration feasibility — DEFERRED

### P.3.1 — Assess integration into the deprecated oxygenWX app — DEFERRED

**Objective/scope:** Keep integration into the deprecated oxygenWX application's `Now -> Hourly -> Daily -> Details` navigation outside the successor WX roadmap. WX builds the successor experience in this repository and preserves the immersive timeline; this deferred slice is only for a separately authorized future reconsideration of changing the deprecated app.
**Depends on:** None.
**Exit after re-entry:** If the owner explicitly reopens deprecated-app integration, an owner-reviewed design documents pager/timeline gesture ownership, typed normalized presentation binding, source/provenance semantics, and a bounded integration plan that preserves that app's existing network/cache/provider flows. Evidence is the design and a focused source-contract review; no application-wide test matrix is required for this design slice.
**Concept artwork:** Pending owner-approved interaction storyboard at `.codex/concepts/P.3.1-integration-feasibility.png`, showing the deprecated app's outer pager and proposed timeline boundary without implying a production navigation change.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner explicitly reverses the current successor boundary and authorizes a separate design for modifying the deprecated oxygenWX application. WX implementation alone does not satisfy this condition.

## P.4 — Production-release presentation cleanup — DEFERRED

### P.4.1 — Remove experimental branding for production release — DEFERRED

**Objective/scope:** Preserve the deprecated app's branding task as legacy-only. Successor OxygenWX branding and release identity are owned by WX.27.
**Depends on:** None.
**Exit after re-entry:** If a separate release of the deprecated app is explicitly authorized, release-candidate screens use its approved branding and installed captures record the affected surfaces. The successor's WX.27 outcome does not close this legacy task.
**Concept artwork:** Pending owner-approved release-surface concept at `.codex/concepts/P.4.1-production-branding.png`, showing the approved identity replacing `OXYGEN / TIME LAB` on each affected surface.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** A separate production-release cycle is explicitly authorized for the deprecated oxygenWX application.

### P.4.2 — Replace DEMO DATA only with accurate provenance — DEFERRED

**Objective/scope:** Preserve the deprecated app's synthetic-data label task as legacy-only. Successor data/provenance labels are owned by WX.25–WX.27.
**Depends on:** A separately authorized deprecated-app integration and a production data path with verified provenance.
**Exit after re-entry:** Installed legacy release UI identifies actual data/source status accurately, with semantics and source evidence reviewed. Focused projection checks cover source identity, freshness/status labeling, and missing-provenance behavior; captures cover the affected source-label surfaces only.
**Concept artwork:** Pending owner-approved source/freshness treatment at `.codex/concepts/P.4.2-provenance-labels.png`, showing source identity, freshness, and unavailable-provenance states without invented provider claims.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** Production forecast data and verified provenance are available in an explicitly approved integration cycle for the deprecated app. Successor data work does not imply legacy-app integration.

### P.4.3 — Define production timeline reset behavior — DEFERRED

**Objective/scope:** Preserve reset behavior design for the deprecated app as legacy-only. Successor reset semantics are handled in WX.24's absolute-time-axis contract.
**Depends on:** P.3.1.
**Exit after re-entry:** An owner-approved legacy interaction contract states reset gesture/action, relationship to the outer pager, and accessibility semantics before implementation begins. Evidence is the approved contract plus a focused review of reset and pager gesture ownership; no broad application interaction or accessibility matrix is part of this design slice.
**Concept artwork:** Pending owner-approved interaction storyboard at `.codex/concepts/P.4.3-production-reset.png`, showing reset entry, selected-time result, and outer-pager gesture boundary.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner authorizes a separate deprecated-app navigation design following P.3.1. WX.24 does not satisfy this legacy re-entry condition.

## ATM — Living atmosphere engine

### ATM.0 — Capture installed pre-engine atmosphere baseline — DONE

**Objective/scope:** Establish the installed app baseline before renderer changes by consolidating the existing Pixel 3 XL visual, gesture, reduced-motion, RTL, and accessibility reviews. No new capture cycle is needed.
**Depends on:** G.4.
**Exit:** Existing installed evidence covers daily/hourly navigation, main-view swipe and double-tap Now, bright/night and large-font landscape readability, reduced motion, RTL, TalkBack, responsiveness, gesture predictability, and forecast readability; documented limitations remain explicit.
**Evidence:** Daily/hourly and main-view navigation: `.codex/history/005-main-display-navigation.md`, `.codex/history/012-gesture-acceptance-recheck.md`, and `.codex/history/007-visual-baseline.md`, with captures under the matching `.codex/test-artifacts/` directories. Bright/night and landscape large-font readability: `.codex/history/014-landscape-high-low.md` and `.codex/history/015-responsive-readability-review.md`, with matching artifacts. TalkBack: `.codex/history/018-talkback-forecast-browser.md` and `.codex/history/019-talkback-scale-action.md`, with matching artifacts. Reduced motion: `.codex/history/020-reduced-motion.md` and `.codex/test-artifacts/020-reduced-motion/`. RTL and final P.2 disposition: `.codex/history/021-p2-acceptance-closure.md` and `.codex/test-artifacts/021-p2-acceptance-closure/`. Known limits are recorded in those histories, including qualitative responsiveness and no RTL-specific TalkBack review.

### ATM.1 — Pure typed scene projection and static fallback — DONE

**Objective/scope:** Add a pure typed mapper from existing `SkyFrame`, selected time and explicit effect/environment inputs to immutable scene state, with a coherent static fallback and an adapter seam. Preserve missingness and distinguish condition identity from rain probability. Do not change visible layout, timeline physics, or gestures.
**Depends on:** ATM.0.
**Exit:** Scene state derives only from typed inputs; absent values remain absent; rain probability cannot become rain intensity; `selectedHour`, forecast facts, accessibility text, and gesture ownership remain unchanged; the current renderer still provides the accepted baseline. Focused tests/build checks are planned subject to the repository's test authorization rule.
**Evidence:** Cycle 024 (`024-atm1-scene-projection`): `.codex/test-artifacts/024-atm1-scene-projection/README.md`; closeout: `.codex/history/024-atm1-scene-projection.md`; archived plan: `.codex/history/plans/024-atm1-scene-projection.md`.

### ATM.2 — Sky, light and illustrative celestial layer — DONE

**Objective/scope:** Add time-aware sky palettes, diffuse light, and illustrative sun/moon artwork with a static/reduced-motion path. No solar ephemeris or moon-phase claims.
**Depends on:** ATM.1.
**Exit:** Clear/cloudy/day/dusk/night transitions are continuous across fixture samples; essential text remains readable; installed Pixel 3 XL checks cover daily/hourly, bright/dark, portrait/landscape, default/large font, RTL and reduced-motion states; timeline responsiveness and gesture ownership remain accepted.
**Evidence:** Cycle 025 (`025-atm2-sky-light-celestial`): `.codex/test-artifacts/025-atm2-sky-light-celestial/README.md`; closeout: `.codex/history/025-atm2-sky-light-celestial.md`; archived plan and approved concept: `.codex/history/plans/025-atm2-sky-light-celestial.md` and `.codex/history/plans/025-atm2-sky-light-celestial-concept.png`.

### ATM.3a — Consolidate ATM.3 evidence and dependency-order corrective work — DONE

**Objective/scope:** Reuse completed ATM.3 installed evidence, capture the missing 7:30 PM fractional sample and one corrected 8 PM sample after discovering a cycle-030 artifact mismatch, then map source and acceptance dependencies. Evidence and planning only; no application changes.
**Depends on:** None. This bounded audit can proceed while ATM.3 is blocked.
**Exit:** Prior evidence is cited by exact path; the two targeted Pixel 3 XL captures disposition the missing fractional and 8 PM states with limitations; device settings are restored; and a source/acceptance dependency map recommends the first corrective slice. ATM.3 remains BLOCKED until its complete exit is met.
**Evidence:** Cycle 031 (`031-atm3-blocker-confirmation`): `.codex/test-artifacts/031-atm3-blocker-confirmation/README.md`; closeout: `.codex/history/031-atm3-blocker-confirmation.md`; archived plan: `.codex/history/plans/031-atm3-blocker-confirmation.md`.

### ATM.3 — Deterministic multi-depth cloud renderer — DONE

**Objective/scope:** Complete the deterministic far/mid cloud renderer driven by supported cloud-cover inputs and selected-time phase. Correct the recorded selected-time release/observability, large-font clipping, and RTL rendering findings needed for its installed acceptance. No precipitation or terrain redesign.
**Depends on:** ATM.2.
**Exit:** Cloud visuals respond smoothly and deterministically to supported cloud-cover and selected-time inputs without reseeding or popping at sample boundaries; phase changes are perceptible but restrained; the static path is legible. The exact selected moment remains authoritative and is consistently disclosed in the forecast summary and timeline semantics after release. Installed Pixel 3 XL review covers clear/overcast and daily/hourly states, compact/1.3 font, RTL and reduced motion; it confirms no clipping, coherent RTL chronology, timeline responsiveness, gesture predictability, accessible selected-time disclosure, and readable forecast facts.
**Evidence:** Cycles `026-atm3-cloud-renderer` through `031-atm3-blocker-confirmation` are recorded in `.codex/history/`; installed evidence is in `.codex/test-artifacts/026-atm3-cloud-renderer/` through `.codex/test-artifacts/031-atm3-blocker-confirmation/`. Cycle 030 recorded 1.3-font clipping and a zero-motion idle pair; cycle 031 corrected the 8 PM capture and added a fractional sample. At that point, RTL still rendered LTR, release hid and rounded the selected time, and cloud phase movement was visually unconfirmed. Cycle `032-atm3-corrective-acceptance` resolved these findings and completed the selected-time, large-font, RTL, cloud-phase, reduced-motion, and TalkBack acceptance. The owner-confirmed TalkBack pass is recorded without a transcript. Evidence: `.codex/test-artifacts/032-atm3-corrective-acceptance/README.md`; closeout: `.codex/history/032-atm3-corrective-acceptance.md`; archived plan: `.codex/history/plans/032-atm3-corrective-acceptance.md`.

### ATM.3b — Deterministic hourly precipitation demo data — DONE

**Objective/scope:** Expand the deterministic DEMO DATA fixture and typed hourly forecast projection with precipitation inputs for visual design: stepped hourly chance values, explicit rain and snow identities, and explicit hourly precipitation totals where supplied. Keep chance and total as separate nullable/typed signals; never derive totals or snow from chance or temperature. Use a generic precipitation-chance label for both rain and snow. No renderer, timeline, gesture, or live-provider changes.
**Depends on:** ATM.3.
**Exit:** Normal hourly fixture navigation exposes dry and multiple precipitation-chance levels, rain hours with differing explicit totals, a missing-total case, and an explicitly typed snow case. Projection preserves chance, type, amount, and missingness independently; the visible chance label is accurate for rain and snow; the fixture remains deterministic and labeled DEMO DATA. No precipitation rendering or timeline behavior changes.
**Evidence:** Cycle 034 (`034-atm3b-precipitation-demo-data`): `.codex/test-artifacts/034-atm3b-precipitation-demo-data/`; closeout: `.codex/history/034-atm3b-precipitation-demo-data.md`; archived plan: `.codex/history/plans/034-atm3b-precipitation-demo-data.md`.

### ATM.4 — Bounded rain and snow depiction — DONE

**Objective/scope:** Add clearly visible precipitation artwork driven by typed hourly condition, chance, and optional amount. Chance selects stepped ambient moisture coverage; it does not select falling-rain heaviness. Explicit hourly totals may control illustrative rain heaviness; missing totals remain unknown. Snow requires an explicit typed condition or clearly labeled synthetic fixture; never infer it from temperature.
**Depends on:** ATM.3 and ATM.3b.
**Exit:** Rain falls visibly across the atmospheric view; stepped chance states produce distinct, understandable moisture coverage; explicit hourly totals distinguish illustrative rain heaviness without implying measured intensity beyond the DEMO DATA fixture. Explicit snow has a coherent, distinct depiction. Static/reduced-motion states remain understandable, and forecast facts and timeline remain readable. Paired installed Pixel 3 XL captures show absent/present effects and representative chance/amount steps at normal phone viewing size; focused checks cover the typed mappings and relevant timeline interactions. Cross-app RTL, TalkBack, font-scale, and other accessibility/layout matrices are separate cycles.
**Evidence:** Retry cycle 035 (`035-atm4-bounded-precipitation`): `.codex/test-artifacts/035-atm4-bounded-precipitation/README.md` and installed captures; closeout: `.codex/history/035-atm4-bounded-precipitation.md`; archived plan: `.codex/history/plans/035-atm4-bounded-precipitation.md`. Prior rejected attempt retained at `.codex/history/033-atm4-bounded-precipitation.md` and `.codex/test-artifacts/033-atm4-bounded-precipitation/README.md`. RTL, TalkBack, large-font, landscape, other devices, and frame-time measurements were not part of this slice and are not claimed.

### ATM.5 — Fog and wind effect primitives — DONE

**Objective/scope:** Add reusable haze/turbulence primitives only as clearly labeled preview effects until supported typed visibility and wind-direction inputs exist. No fabricated live fog, wind direction, or visibility claims.
**Depends on:** ATM.3.
**Exit:** Missing inputs produce no claimed weather effect; preview states are clearly identified as illustrative; effects do not obscure forecast text or timeline and have static fallbacks. Time-varying illustrative effects follow selected time continuously without wall-clock-driven intensity or timestamp changes. Focused presentation tests cover explicit preview gating and preservation of forecast values; installed Pixel 3 XL review covers preview-present and absent-input states plus representative scrub transitions. Application-wide RTL, TalkBack, font-scale, and unrelated layout checks are outside this slice.
**Concept artwork:** Owner approved 2026-10-25 at `.codex/concepts/ATM.5-fog-wind-preview.png`. The approved direction has a richer background and midground, no data box around forecast facts, clouds within scene bounds, and an increasingly perceptible haze effect as the distant ridge softens and loses contrast. Scrubbing should transition deterministically and smoothly with selected time; do not use wall-clock intensity changes or imply wind direction. Rain/snow intensity panels are reference material only and do not expand ATM.5 scope.
**Evidence:** Incomplete cycle 036 (`036-atm5-fog-wind-primitives`): `.codex/test-artifacts/036-atm5-fog-wind-primitives/`; closeout: `.codex/history/036-atm5-fog-wind-primitives.md`; archived plan: `.codex/history/plans/036-atm5-fog-wind-primitives.md`. ATM.5 completed in cycle 037 (`037-atm5-selected-time-profile`): `.codex/test-artifacts/037-atm5-selected-time-profile/README.md`; closeout: `.codex/history/037-atm5-selected-time-profile.md`; archived plan: `.codex/history/plans/037-atm5-selected-time-profile.md`.

### ATM.6 — Restrained storm and night refinements — DONE

**Objective/scope:** Add muted storm and night artwork using supported condition/time cues, adapted to the existing abstract landscape geometry. Keep that geometry; the approved concept's mountain/forest terrain is reference only for this slice. Add debug-build illustrative storm and clear-night scene previews needed for installed review; they must preserve selected fixture facts and be visibly labeled. No forecast fixture/provider changes, alerts, solar/moon accuracy claims, or flashes behind critical text.
**Depends on:** ATM.5.
**Exit:** Storm/night cues produce the intended restrained scene states, remain understandable in static/reduced-motion presentation, and introduce no strobing or text occlusion. Focused scene-projection tests cover the storm/night cue mappings and static state; debug-only illustrative storm and clear-night previews preserve fixture facts; installed Pixel 3 XL review covers only the affected storm/night states and their text-clearance behavior.
**Concept artwork:** Owner approved 2026-10-25 at `.codex/concepts/ATM.6-storm-night.png`. The approved board shows clear night, cloudy night, and storm night with restrained blue-gray cloud scenes and protected forecast-text/timeline areas; no celestial objects, lightning, alerts, rain/snow effects, or astronomical-accuracy claims. On 2026-10-25 the owner resolved the depicted mountain/forest terrain mismatch by retaining the installed abstract landscape and adapting storm/night styling to it.
**Evidence:** Cycle 038 (`038-atm6-storm-night-refinements`): `.codex/test-artifacts/038-atm6-storm-night-refinements/README.md`; closeout: `.codex/history/038-atm6-storm-night-refinements.md`; archived plan: `.codex/history/plans/038-atm6-storm-night-refinements.md`.

## DFM — Debug forecast matrix

This workstream establishes an explicit, debug-only set of typed forecast scenarios before adding more weather-scene behavior. It does not change the normal `SampleForecast`, claim provider data, or represent official alerts. Generic precipitation probability remains distinct from thunderstorm probability. Matrix coverage is representative and transition-oriented, not the full Cartesian product of every field.

### DFM.1 — Define and approve the debug forecast matrix — DONE

**Objective/scope:** Document the matrix scenario inventory and typed field contract. Cover clear, partly cloudy, cloudy, rain, snow, and storm; day/night variants where they change scene meaning; dry/wet probability and explicit/missing amount cases; and condition transitions. Define thunderstorm probability as a separate signal from generic precipitation probability, including a 27% illustrative case and a 90% illustrative case, plus a separate debug-only warning state. Specify expected forecast facts, provenance labels, and which values are intentionally missing. No application code or scene redesign.
**Depends on:** ATM.6.
**Exit:** An owner-reviewed matrix specification lists every scenario and variant, defines all typed fields and missingness, distinguishes illustrative warning state from official alerts, and states the expected time/condition transitions. The owner approves this matrix before fixture implementation begins.
**Design artifact:** `.codex/concepts/DFM.1-debug-forecast-matrix.md` — an annotated coverage table and transition map; owner approval is required before DFM.2.
**Evidence:** Owner-approved matrix and transition contract: `.codex/concepts/DFM.1-debug-forecast-matrix.md`. Cycle 039 (`039-dfm1-debug-forecast-matrix`): `.codex/test-artifacts/039-dfm1-debug-forecast-matrix/README.md`; closeout: `.codex/history/039-dfm1-debug-forecast-matrix.md`; archived plan: `.codex/history/plans/039-dfm1-debug-forecast-matrix.md`.

### DFM.2 — Implement the approved debug forecast catalog — DONE

**Objective/scope:** Implement the approved matrix as deterministic, immutable typed debug forecast scenarios. Keep it separate from the normal forecast fixture and gate access to debuggable builds. Preserve generic precipitation chance, thunderstorm probability, typed precipitation identity/amount, optional debug warning state, and missingness as independent values. No matrix browser or visual mapping yet.
**Depends on:** DFM.1.
**Exit:** Focused data/projection checks demonstrate every approved matrix row is addressable and stable, every field retains its specified type/missingness, and normal fixture output is unchanged. A rendered inventory report or equivalent review artifact matches the approved matrix, and the owner approves the completed catalog before UI work starts.
**Concept artifact:** The owner-approved DFM.1 matrix is the implementation contract; no additional scene artwork is in scope.
**Evidence:** Cycle 040 (`040-dfm2-debug-forecast-catalog`): `.codex/test-artifacts/040-dfm2-debug-forecast-catalog/README.md` and `forecast-catalog-inventory.md`; closeout: `.codex/history/040-dfm2-debug-forecast-catalog.md`; archived plan: `.codex/history/plans/040-dfm2-debug-forecast-catalog.md`. The full debug test task also exposed one unrelated existing time-label assertion failure, recorded in the cycle evidence.

### DFM.3 — Build debug forecast scenario sets for timeline review — DONE

**Objective/scope:** Add a debug-only way to load complete synthetic forecast profiles from the debug catalog for visual review of timeline scrubbing and atmospheric transitions. Provide 3-, 7-, and 10-day weather intervals under test, each surrounded by two calm, clear days before and after; the corresponding loaded forecasts are 7, 11, and 14 days. Include a short rain event, a clear-to-partly-cloudy-to-storm progression, and an extended cold/cloud/wind/snow progression. Treat the timeline as a viewer of the selected forecast samples. Keep all scenario data and access debug-only, and use the existing selected-time and timeline presentation path without adding browser controls to the immersive forecast screen.
**Depends on:** DFM.2.
**Exit:** Each forecast profile is selectable in a debuggable build and can be scrubbed across its exact loaded bounds on the installed reference device; every weather interval under test has two calm, clear days of forecast context on both sides; selected timestamps and typed synthetic facts match catalog samples; the complete suite covers the approved condition families; no permanent control is added to the forecast screen; release builds expose no scenario browser or scenario data; timeline interaction remains responsive, predictable, and readable.
**Concept artwork:** Owner approved 2026-10-26 at `.codex/concepts/DFM.3-debug-forecast-matrix-browser.png`. The Pixel 3 XL-based storyboard shows the same full-screen atmosphere and bottom tape across a clear → partly-cloudy → storm scrub sequence, plus separate rain and extended cold/cloud/wind/snow fixtures. No forecast cards or on-screen debug control are proposed.
**Evidence:** Cycle 041: `.codex/test-artifacts/041-dfm3-debug-forecast-matrix-browser/README.md`; release inspection: `.codex/test-artifacts/041-dfm3-debug-forecast-matrix-browser/release-isolation.txt`; history: `.codex/history/041-dfm3-debug-forecast-matrix-browser.md`; archived plan: `.codex/history/plans/041-dfm3-debug-forecast-matrix-browser.md`. TalkBack, RTL, large-font, and reduced-motion review remain assigned to applicable follow-up cycles.

## ATM — Matrix-driven atmosphere

### ATM.8 — Map clear, cloudy and day/night matrix states — PLANNED

**Objective/scope:** Review and implement the scene mapping for approved clear, partly cloudy, and cloudy scenarios across day, dusk, and night, using the retained abstract landscape. Preserve exact forecast facts and selected time; keep visual interpolation decorative and deterministic.
**Depends on:** DFM.2 and DFM.3.
**Exit:** Every approved sky/light matrix case is selectable and produces its specified scene state; transitions remain continuous across timeline scrubbing; selected time and forecast facts remain authoritative; static/reduced-motion scenes preserve meaning; installed evidence confirms responsiveness, gesture predictability, and readability.
**Concept artwork:** Pending owner-approved annotated state board at `.codex/concepts/ATM.8-matrix-sky-mapping.png`, covering only the approved sky/light variants and the existing landscape.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.9 — Map rain and snow matrix variants — PLANNED

**Objective/scope:** Review and implement the scene mapping for the approved typed rain and snow scenarios, including probability steps, explicit amounts, and missing amounts. Probability remains distinct from precipitation intensity; do not infer snow or amount from other fields.
**Depends on:** DFM.2, DFM.3, and ATM.8.
**Exit:** Every approved rain/snow variant is selectable and visually distinguishable at its stated inputs; missing values remain unknown; forecast text and timeline remain readable; installed evidence covers representative day/night and reduced-motion states with responsiveness and gesture ownership retained.
**Concept artwork:** Pending owner-approved annotated state board at `.codex/concepts/ATM.9-matrix-precipitation-mapping.png`, covering the approved rain/snow variants without implying unsupported measurements.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.10 — Map storm likelihood and warning variants — PLANNED

**Objective/scope:** Map the approved separate thunderstorm-probability and debug-warning signals to restrained storm visuals on the existing landscape. A low illustrative thunderstorm chance such as 27% may add localized darker clouds to an otherwise partly cloudy scene; a high chance such as 90% or a debug warning produces denser clouds and stronger dark contrast. Do not treat generic precipitation probability as thunderstorm probability or present a debug warning as an official alert. No lightning flashes or astronomical claims.
**Depends on:** DFM.2, DFM.3, and ATM.8.
**Exit:** Scrubbing through the approved clear/partly cloudy/cloudy-to-storm-and-clearing scenarios visibly enters and exits the intended graded storm states; the high/warning state is stronger than the low-probability state; source forecast facts remain accurate and visibly labeled; installed reduced-motion and ordinary-motion evidence confirms no strobing, text occlusion, or loss of timeline responsiveness/predictability/readability.
**Concept artwork:** Pending owner-approved storm-state board at `.codex/concepts/ATM.10-storm-likelihood-mapping.png`, comparing baseline, 27%, 90%, and debug-warning scenes on the current abstract landscape.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.7 — Effects policy, lifecycle and performance gate — PLANNED

**Objective/scope:** Add a single effects/quality policy, lifecycle-aware animation coordination, static fallback, and bounded performance review after the matrix-driven atmosphere behavior is established. Environment work is gated on this slice.
**Depends on:** ATM.8, ATM.9, and ATM.10.
**Exit:** Focused policy and lifecycle tests establish `NONE`, `SUBTLE`, `FULL`, reduced-motion, and background/offscreen behavior. Pixel 3 XL frame-time, gesture-responsiveness, and lifecycle evidence for the effects coordinator is recorded. If effects performance evidence cannot be captured, record the blocker and keep ENV work ineligible; broad retesting of unrelated P.2 interactions is outside this slice.
**Concept artwork:** Pending owner-approved effects-policy board at `.codex/concepts/ATM.7-effects-policy.png`, comparing `NONE`, `SUBTLE`, `FULL`, static/reduced-motion, and background/offscreen scene appearance for the same approved matrix state.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

## ENV — Interchangeable environment packs

The environment-pack experiments are intentionally outside the current path until the debug forecast matrix and its atmosphere mappings are complete and owner-approved.

### ENV.1 — Environment contract and Neutral baseline — DEFERRED

**Objective/scope:** Define immutable environment metadata and migrate existing scenic geometry behind a Neutral pack. No persistent selector or new screen.
**Depends on:** ATM.7.
**Exit after re-entry:** Neutral appearance retains the accepted baseline. Focused pack-selection tests confirm Neutral changes only scenery metadata/geometry and preserves the supplied forecast and selected-time values. Installed Pixel 3 XL captures compare the Neutral scene with its current baseline.
**Concept artwork:** Pending owner-approved Neutral-pack concept at `.codex/concepts/ENV.1-neutral-pack.png`, defining the baseline geometry, palette, and pack metadata without a selector or new screen.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** DFM.1–DFM.3 and ATM.8–ATM.10 are complete and owner-approved, ATM.7 meets its exit, and the owner explicitly reactivates environment-pack work.

### ENV.2 — Alpine environment pack — DEFERRED

**Objective/scope:** Add Alpine scenic geometry and palette using the shared atmosphere renderer. Snow visuals require an actual typed snow state or labeled preview.
**Depends on:** ENV.1.
**Exit after re-entry:** Alpine clear/rain/night states and any permitted snow preview are coherent. Focused tests cover Alpine pack selection and require explicit typed snow or labeled preview input for snow artwork. Installed Pixel 3 XL captures cover the affected Alpine states with forecast facts legible.
**Concept artwork:** Pending owner-approved Alpine-pack sheet at `.codex/concepts/ENV.2-alpine-pack.png`, showing clear/rain/night and explicit snow or labeled snow preview while preserving the shared scene information hierarchy.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** ENV.1 is complete and the owner explicitly reactivates the Alpine pack.

### ENV.3 — Coastal environment pack — DEFERRED

**Objective/scope:** Add Coastal scenic geometry and palette using the shared atmosphere renderer. No location lookup or geographic truth claims.
**Depends on:** ENV.1.
**Exit after re-entry:** Coastal clear/rain/storm/night states are distinct without location lookup or geographic truth claims. Focused pack tests cover Coastal state selection and independence from location data; installed Pixel 3 XL captures cover the affected Coastal states and render budget.
**Concept artwork:** Pending owner-approved Coastal-pack sheet at `.codex/concepts/ENV.3-coastal-pack.png`, showing clear/rain/storm/night palette and scenery as an illustrative environment with no geographic claim.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** ENV.1 is complete and the owner explicitly reactivates the Coastal pack.

### ENV.4 — Cross-pack transitions and acceptance matrix — DEFERRED

**Objective/scope:** Validate and implement only the bounded transition/fallback needed to move among Neutral, Alpine and Coastal, plus focused checks for the shared pack contract.
**Depends on:** ENV.2 and ENV.3.
**Exit after re-entry:** Focused transition/fallback tests confirm switching among Neutral, Alpine, and Coastal preserves the supplied selected time, forecast facts, and provider state, and exercises static/reduced-motion and lower-quality paths. Installed Pixel 3 XL captures cover only the pack transitions and representative states needed to verify those contracts.
**Concept artwork:** Pending owner-approved transition storyboard at `.codex/concepts/ENV.4-pack-transitions.png`, showing Neutral/Alpine/Coastal selection, transition, and static/lower-quality fallback states.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** ENV.2 and ENV.3 are complete and the owner explicitly reactivates pack-transition work.

### ENV.5 — Additional environment packs — DEFERRED

**Objective/scope:** Consider Forest, Plains, Desert, or Urban only after Neutral/Alpine/Coastal acceptance. No commitment to implement all candidates.
**Depends on:** ENV.4.
**Exit after re-entry:** Owner-approved asset/reference plan and one specifically selected bounded pack slice with truthful weather mapping. The selected slice defines focused checks for its pack-selection and weather-mapping contract and installed evidence for only its affected scene states.
**Concept artwork:** Pending explicit pack choice and owner-approved concept at `.codex/concepts/ENV.5-selected-pack.png`; the concept must target only the selected additional pack and show truthful condition mapping. Do not create a generic multi-pack concept as a substitute.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** Explicit owner selection and approval of one additional pack after ENV.4 is complete.
