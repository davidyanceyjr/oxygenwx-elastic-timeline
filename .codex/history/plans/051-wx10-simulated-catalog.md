# Cycle plan — 051-wx10-simulated-catalog

State: ACTIVE
Cycle ID: 051-wx10-simulated-catalog
Roadmap: WX.10
Objective: Generate one deterministic 44-day DEMO timeline

## Production boundary

Add one deterministic `SimulatedCatalogSource` in `app/src/main/java/com/oxygen/weather/timeline/data/catalog/`, using the WX.9 `WeatherCatalogSource` contract and existing canonical catalog, plus the owner-approved `HAZE` member in `WeatherPhenomenon`. Its request supplies location, dataset ID, and `asOf`; there is no random seed, scenario selector, or clock read inside the source. Narrow source-local sequence/support helpers are allowed. The owner authorized focused local-slice tests in `app/src/test/.../data/catalog/` on 2026-10-27. Evidence belongs in `.codex/test-artifacts/051-wx10-simulated-catalog/`.

The owner approved `.codex/concepts/WX.10-simulator.png` and editable `.svg` on 2026-10-27, as recorded in `docs/ROADMAP.md`. On 2026-10-27 the owner also approved the exact sample/support proposal and a narrowly bounded `WeatherPhenomenon.HAZE` addition in this cycle. Record both approvals and their constraints in cycle evidence before activation. The board is design input, not acceptance evidence.

Verified dependencies: WX.6 and WX.9 are DONE. WX.9 supplies the request, result, registry, and source interface; the registry checks identity, provenance, declared support, and complete versus partial coverage. `CatalogWindow.create` already derives 44 local dates from location timezone and `asOf` and handles DST-aware boundaries. Canonical facts use metric units, nullable values, composable weather conditions/phenomena, and accumulation intervals.

## Functional invariants

- Identical requests produce equal catalogs and support metadata with one fixed event order. D is `asOf` in the supplied location's IANA timezone. D−30…D−1 are historical and D…D+13 are forecast: 44 local dates, not a guaranteed 1,056 hours.
- D−30, D−29, D+12, and D+13 contain explicit clear synthetic facts. D−28…D+11 is the 40-date event core: 28 historical and 12 forecast dates. Cases may overlap.
- Cover rain, snow, sleet, thunderstorms, heavy rain with **flash-flood conditions**, clear/partly cloudy, haze, fog, and high wind. “Flash-flood conditions” means synthetic heavy-rain facts; it does not assert flooding or an official alert. Sleet uses supported precipitation conditions and ice-pellet/freezing-rain phenomena as applicable. Haze requires an explicit representation decision; sky/visibility values alone do not establish its identity.
- Origin/product are `SIMULATED`/`SYNTHETIC_METEOROLOGICAL_PRODUCT`; historical dates are not labeled verified observations. Preserve separate precipitation and thunderstorm probabilities, rain amount/rate, snowfall/depth, wind/gust, and visibility meanings. Missing remains null; explicit zero remains zero. Accumulated values have valid intervals.
- The 7/11/14-day debug profile generators and outputs, `SampleForecast`, debug picker, app startup, DEMO DATA label, selected-time/gesture behavior, accessibility, RTL, reduced motion, and atmosphere remain unchanged.
- The approved date-to-event order, exact samples, values, intervals, and field support are recorded in `.codex/plans/051-wx10-sequence-fixture.md`. It includes clear day/night, cloudy without precipitation, rain with unknown amount, and separate low/high thunderstorm-probability cases within the existing 40-date core. No inferred values or alternate timeline are authorized.

## Steps

1. Capture baseline anchors for fixture adapters, WX.9 source contract, canonical facts, WX.5 window/support rules, and legacy debug profiles. Record the approved board and constraints in cycle evidence.
2. Freeze the approved single 40-date sequence in `.codex/plans/051-wx10-sequence-fixture.md`, with four clear buffers and exact samples, values, intervals, and declared support. Add only `HAZE` to the canonical `WeatherPhenomenon` vocabulary; use it explicitly on D−16 and D−15. Never infer haze from visibility. Record the bounded schema change in roadmap and cycle evidence.
3. Implement the source with request-derived local dates and absolute instants, synthetic provenance, canonical units, and WX.9 support/result semantics. Do not connect it to the app startup or legacy generators.
4. Add/run the owner-authorized focused checks for determinism, 44-date and 30/14 coverage, clear edges, event order/families, DST, missing versus zero, plausible combinations, provenance, no alert semantics, and unchanged debug profiles.
5. Run applicable contract/build checks, preserve actual results and the sequence fixture, inspect `git diff --check` and the final diff, and close only when the full roadmap exit is evidenced.

## Acceptance/evidence

Exit requires one reproducible canonical 44-date catalog with one event order and no scenario input, all listed event families and explicit clear buffers, honest synthetic/missing-data semantics, DST-safe dates, no alert semantics, and unchanged debug profiles.

Implementation verification: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, focused WX.10 checks including DST and legacy-profile regression, `python scripts/dev.py build`, `git diff --check`, and final diff review. The owner explicitly requested local-slice tests on 2026-10-27. Data-only work has no installed screenshot or visual-acceptance claim.

Evidence targets: `.codex/plans/051-wx10-simulated-catalog.md`, `.codex/test-artifacts/051-wx10-simulated-catalog/`, `.codex/history/051-wx10-simulated-catalog.md`, and `.codex/history/plans/051-wx10-simulated-catalog.md` after closure.

## Risks / assumptions

- The owner approved the single sequence and sample/support rules on 2026-10-27. The fixture fixes their implementation contract; retain its exact null/zero distinctions.
- Haze requires the approved, narrowly bounded `WeatherPhenomenon.HAZE` addition. Visibility alone cannot establish its cause. Flash-flood conditions need no new alert or flood type; they are the synthetic heavy-rain case.
- The diagnostic DEMO sequence crosses seasonal events; avoid implausible combinations at a single valid time and never describe synthetic history as observation.
- WX.9 checks completeness against declared support. Unsupported fields must not be declared complete or converted to zero.
- Roadmap exit needs focused checks; the owner authorized local-slice tests on 2026-10-27.

## Out of scope

Multiple scenarios/alternate timelines, changes to legacy generators, live or replay providers, official alerts, source switching, startup integration, UI/presentation/atmosphere/timeline changes, location selection, network, cache, persistence, release claims, and edits to the deprecated oxygenWX app.

## Planning verification and model fit

`python scripts/dev.py workflow` returned `Workflow scaffold: OK`; `.codex/current.md` was IDLE before planning; WX.10 was sole NEXT with WX.6/WX.9 DONE. `python scripts/codex_cycle.py start --id 051-wx10-simulated-catalog --roadmap WX.10 --objective 'Generate one deterministic 44-day DEMO timeline'` created this PLANNED cycle. No application tests, build, or implementation checks were run.

Difficulty **6/10**: one data source, with substantial calendar/DST, event plausibility, missingness, and verification work. Recommend **gpt-6.1-sol** for relative model fit; fall back to **gpt-6-astra** if sequence design exposes a schema conflict or DST/coverage behavior resists a bounded solution. This is not a price or billing estimate.
