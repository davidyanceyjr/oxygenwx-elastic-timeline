# Cycle plan — 063-wx23-time-indexed-presentation

State: ACTIVE
Roadmap: WX.23
Objective: Implement time-indexed canonical presentation without 24-hour assumptions

## Owner-approved design input

**Owner-approved projection policy (2026-10-29):** Preserve the selected instant independently from the forecast sample timestamp. For example, with selection at 09:47 and samples at 09:00 and 10:00, keep 09:47 selected and use the exact 10:00 sample for meteorological facts; retain both timestamps in the projected result. Select the nearest available fact sample only from that selected `CatalogDay`, with an equal-distance tie going to the earlier instant. If the day has no hourly samples, hourly facts are unavailable; do not borrow a neighboring date's facts. For each daily temperature extreme, prefer the provider-supplied value; derive a missing extreme from hourly facts only when that date's temperature coverage is `COMPLETE`; otherwise keep it unavailable. No selected fact is interpolated. Scene continuity is a separate decorative projection between in-day sample scene states and must not produce or alter meteorological facts.

**Owner-approved concept artifact (2026-10-29):** The product owner approved `.codex/concepts/WX.23-time-projection.png` (editable source: `.codex/concepts/WX.23-time-projection.svg`). The approved constraints are: keep selected instant distinct from exact forecast sample time; choose nearest sample only within the selected local date, with earlier-instant tie resolution and no cross-date fallback; leave hourly facts unavailable for an empty day; use provider daily extrema when present and derive missing extrema only with complete temperature coverage; keep scene interpolation decorative, same-day, and separate from all meteorological facts; preserve sparse 23/24/25-hour date support; do not migrate the legacy fixture path or change UI/timeline behavior. The board is design input, not implementation or acceptance evidence.

## Verified baseline

- `CanonicalCatalog` carries location timezone, dataset identity, provenance, absolute `Instant` facts, provider daily summaries, and product segments. `CatalogWindow` exposes the 44 local dates as `CatalogDay`s with true local start/end instants, sparse facts, coverage, daily summaries, and complete-support daily amount derivation.
- `ForecastSource` is the current fixture presentation input. It uses `LocalDateTime`, requires continuous hourly samples and complete 24-hour local days, and derives daily extrema from those samples.
- `ForecastProjection` renders facts from an exact fixture sample, converts canonical metric fixture values for display, and interpolates `SkyFrame` only for scene continuity. The current UI and timeline still use the legacy `ForecastSource` path.
- WX.5, WX.6, WX.13, and WX.20 are DONE; WX.23 is the sole NEXT slice. `.codex/current.md` was IDLE before this draft. `python scripts/dev.py workflow` passed before creating the plan.

## Production boundary

Expected change area, to confirm against actual code before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/` — add a provider-neutral typed projection path from canonical catalog/window values and any narrowly required presentation value types.
- `app/src/test/java/com/oxygen/weather/timeline/presentation/` — focused pure projection tests; retain and extend existing fixture compatibility checks only where required.
- `.codex/test-artifacts/063-wx23-time-indexed-presentation/` — actual verification results and compatibility evidence during execution.

Do not change catalog/provider contracts, `ElasticTimeAxis`, `TimeLabScreen`, atmospheric rendering/UI layout, gestures, app startup/source selection, or release integration. If the projection cannot be implemented within the presentation boundary, stop and record the concrete missing contract before widening scope.

### Projection boundary and reuse

- Add a pure canonical projection and only the typed presentation values needed for this slice in `presentation/`. Accept the canonical catalog, its already-created window, the selected `Instant`, and display units. Keep the legacy `ForecastView`, `ForecastSource`, and `ForecastProjection` path compatible; do not force nullable canonical facts into their non-null fields or introduce a general mapping framework.
- The caller supplies the catalog and window from the same validated snapshot. Reject inputs with differing timezones as a violated precondition; this check alone cannot prove snapshot identity because `CatalogWindow` does not expose a dataset ID. Do not rebuild the window on each projection or infer that matching timezones prove the inputs match.
- Use `CatalogDay` facts, field coverage and daily summary directly. Read overall provenance from `CanonicalCatalog.provenance` and look up the selected date's product segment in `CanonicalCatalog.productSegments` using its inclusive start and exclusive end. Preserve absent segment metadata as absent. Use the existing `CatalogDay` daily amount support/origin rules rather than reproducing them.
- `CatalogWindow.create` may include a fact at the following local midnight in the preceding day's `facts` for accumulation support. For nearest fact selection, scene bracketing, and hourly temperature extrema, consider only facts whose `validTime` is in `[localStart, localEnd)` of the selected `CatalogDay`. Keep the catalog's accumulation support handling intact.
- Reuse the window across projections and avoid sorting or rebuilding day data for each selected instant. A bounded scan of the window's dates and selected day's eligible facts is sufficient; choose the simplest implementation that preserves the contract. No performance measurement is claimed by this design; installed timeline responsiveness is assessed when the projection is integrated in WX.25.

## Functional invariants

- Canonical weather samples remain keyed by absolute `Instant`; local calendar identity and display labels use the catalog location's `ZoneId` and `CatalogDay` boundaries. A local date may have 23, 24, or 25 elapsed hours, and hourly inputs may be sparse.
- Keep `selectedInstant` unchanged in the projected result even when no forecast sample exists at that exact instant. Resolve the selected local date using the catalog timezone, then select the nearest available fact sample within that `CatalogDay`; an equal-distance tie selects the earlier `validTime`. Return that sample's `validTime` separately from `selectedInstant`. If the selected instant has no corresponding `CatalogDay`, or that day has no hourly samples, return unavailable hourly facts without clamping the selection or borrowing a sample from another date.
- Meteorological fields shown in the projected view come from the chosen exact sample. No fact is interpolated. Scene continuity may use the two in-day samples bracketing the selected instant, with its visual blend fraction based on elapsed `Instant` duration; at a day edge use the available endpoint scene without extrapolating, and with no in-day samples provide no scene transition. Scene output is decorative only and must not synthesize a temperature, probability, amount, condition, wind fact, or other forecast measurement.
- Null canonical fields remain unavailable. In particular, missing precipitation probability or amount is not zero, and snow remains distinct from rain and generic precipitation. Unsupported facts stay absent.
- For each daily temperature extreme independently, use its provider-supplied summary value when present. If one is missing, derive only that extreme from the date's hourly temperature facts when `CatalogDay.fieldCoverage[TEMPERATURE_C]` is `COMPLETE`; otherwise leave it unavailable. Preserve `PROVIDER_SUPPLIED` versus `DERIVED` origin. Other daily values preserve their existing support/origin rules; no aggregate is derived from incomplete coverage.
- Origin/product provenance remains visible in typed presentation output, using catalog provenance and the date's existing product segment where available; do not infer missing provenance. Historical catalog dates retain historical/archive semantics and are not described as current observations. Unit conversion happens at the presentation boundary, preserving canonical metric values.
- The existing `ForecastSource` fixtures retain their current presentation values, demo labeling, selected-time behavior, and scene path until a later slice migrates them. No provider DTO enters presentation.

## Dependency order

1. Recheck approved concept-board status and the current presentation/catalog contracts; confirm exact source files and existing compatibility snapshots.
2. Define typed presentation values and a pure projection entry point for a canonical catalog/window plus selected `Instant` and display units. Reuse `CatalogDay` summaries/coverage and existing canonical fact types rather than duplicating their rules.
3. Implement the approved sample-selection contract: nearest available fact sample within the selected `CatalogDay`, earlier `validTime` for ties, no cross-date fallback, and both selected and fact-sample instants retained. Project daily extrema per field using provider values first and complete-coverage derivation only as specified above.
4. Project supported exact-sample facts, source/product labels, historical/forecast meaning, nullable values, and snow identity. Represent scene continuity separately using in-day bracketing scene inputs and an elapsed-time blend fraction; do not create meteorological values or interpolate across local-day boundaries.
5. Preserve the legacy fixture adapter and current `ForecastProjection` behavior. Add focused checks for canonical projection and fixture parity, then capture actual evidence and review the complete diff.

## Acceptance and verification

WX.23 is complete only when all roadmap exit conditions are evidenced:

- Pure tests cover spring/fall 23/25-hour local dates and sparse/missing-hour dates without imposing 24-hour completeness.
- Tests cover nullable/unknown measurements, historical versus forecast semantics, exact nearest-hour fact selection and approved tie/day-boundary behavior, daily extrema and their origin, snow's distinct visual identity, provenance/product labels, and stable unit conversion.
- A 09:47 selection between 09:00 and 10:00 samples remains 09:47 in the output, carries the exact 10:00 fact sample separately, and yields no interpolated meteorological fact. Same-day scoping, earlier-instant tie resolution, empty-day behavior, local-day boundary behavior, complete versus partial temperature coverage, and scene-only blend values are covered directly.
- Cover a `CatalogDay` containing a next-midnight fact for accumulation support: it must not be chosen as that day's hourly fact or scene endpoint or included in that day's derived temperature extrema. Preserve the existing daily accumulation support behavior.
- Fixture compatibility comparisons show existing normal/debug fixture presentations are unchanged through the retained `ForecastSource` adapter.
- A missing precipitation probability/amount never becomes a displayed measured zero. Scene interpolation changes no exact-sample meteorological facts.
- `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the focused presentation/compatibility unit tests, `python scripts/dev.py build`, and `git diff --check` pass; record exact command results and any limitations in cycle evidence/history.

This is a pure data/presentation slice with no intended touch, layout, or renderer integration change. No installed visual or device acceptance is claimed or required by this slice. Do not add/run tests or perform implementation in this planning turn; execution verification is for a separately authorized active cycle.

Evidence targets, not yet created: `.codex/test-artifacts/063-wx23-time-indexed-presentation/`; closeout `.codex/history/063-wx23-time-indexed-presentation.md`; archived plan `.codex/history/plans/063-wx23-time-indexed-presentation.md`.

## Risks and assumptions

- Repository inspection confirms `CatalogDay` exposes facts, coverage, daily summaries and existing daily amount derivation; the catalog separately exposes provenance and product segments. The projection still must demonstrate that these inputs suffice for every required output without changing established contracts or exposing storage/provider details. If not, document the smallest missing boundary and seek a bounded roadmap decision.
- The approved policy and concept board settle sample time, tie handling, day scoping, no-sample behavior, daily temperature extrema, and scene/fact separation. `CatalogWindow` does not retain dataset identity, so the caller must pair it with the catalog used to create it; timezone agreement does not verify that pairing.
- The old view has several non-null display fields, while canonical facts are nullable. The typed canonical view must preserve absence so a later UI integration can render “unknown” honestly; this slice must not force fallback zeros or fabricate labels to fit the old view.
- Fixture compatibility is important because WX.23 intentionally retains the old adapter until migration. Existing fixture snapshots or explicit value comparisons may need to be added to the focused verification, but no fixture facts should be rewritten to make the new model pass.
- **Difficulty: 5/10.** Scope is concentrated in pure presentation logic, but nullable semantics, provenance, DST-local dates, daily support, exact sample selection, and legacy parity are coupled and require a careful concept boundary and focused test matrix.
- **Relative model fit:** GPT-6.1 Luna is the least-cost available model with a credible path for this bounded implementation. Escalate to GPT-6.1 Sol if projection semantics or fixture parity remain ambiguous after the approved board and focused checks; use a stronger model only if the implementation uncovers a cross-layer architectural conflict requiring a redesign decision.

## Out of scope

- Migrating `ElasticTimeAxis` or selection/navigation logic to absolute instants (WX.24).
- Connecting the active catalog to `TimeLabScreen`, the 44-day timeline, accessible forecast browser, or atmospheric renderer (WX.25 and later).
- Any touch, layout, visual redesign, gesture, accessibility, RTL, reduced-motion, or installed UI changes.
- Provider ingestion/mapping, catalog/window rules, fixture source migration, cache/source switching, app startup, location UI, official alerts, or deprecated oxygenWX changes.
