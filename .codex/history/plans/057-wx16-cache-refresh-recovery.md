# Cycle plan — 057-wx16-cache-refresh-recovery

State: ACTIVE
Cycle ID: 057-wx16-cache-refresh-recovery
Roadmap: WX.16
Objective: Implement location-scoped LIVE catalog freshness, foreground refresh, partial recovery, and local-date window rollover.

## Approved decisions and boundary

- Owner-approved concept board: `.codex/concepts/WX.16-cache-refresh.png` (editable `.svg`), 2026-10-28. It distinguishes freshness, coverage, and cache-write status; preserves an old window as stale across midnight without changing `asOf`; keeps valid live data usable after a cache-write failure; and requires bounded retention. The board guides design; it is not acceptance evidence.
- WX.16 fetches and persists one indivisible whole catalog. `WeatherCatalogSource.load(CatalogLoadRequest)` and `CatalogRecording` do not support segment-selective requests/storage. One usable `Success` or `Partial` gets one retrieval time; partial coverage remains truthful. Separate forecast/history clocks and schedules are deferred until the source can request products independently. This supersedes earlier proposed 60-minute forecast and 24-hour history schedules.
- Data/repository only: no UI state, labels, or installed UI review; no background worker or production scheduling integration.

May change `data/catalog/`, narrowly required `data/openmeteo/` adapter code, focused catalog/Open-Meteo host tests, focused Room instrumentation, this plan/evidence/history, and `docs/ROADMAP.md` only for a fully accepted WX.16 closeout. Keep provider DTOs behind the adapter. Do not change UI, active catalog/source selection, location search/saved-location behavior, provider request semantics beyond refresh calls, or deprecated oxygenWX files.

## Policy and repository contract

- Freshness uses successful retrieval time (`storedAt`), never `asOf` or provider issue time. LIVE data is FRESH through 60 minutes after retrieval, then STALE. The named 60-minute cutoff, 15-minute eager-refresh gate, and two-snapshot cap are configurable initial policy values, not provider SLAs.
- On load, restore a valid compatible cache first. Fetch in the foreground if missing, legacy-time-unknown, older than 15 minutes, or anchored to a prior local date; otherwise serve it without an eager fetch. Forced refresh bypasses the age gate. A prior-date cache stays a stale fallback even if recently retrieved. There is no periodic/background scheduling.
- Inject `Clock`; capture one instant per operation for request `asOf` and local-date window selection. Capture retrieval time separately when a usable source result arrives. Across midnight, request the new location-timezone window while preserving the old snapshot's exact `asOf` as stale fallback; never relabel or merge its facts into the new window.
- Provide a caller-driven operation equivalent to `load(location, datasetId, forceRefresh = false)`. Return an optional usable recording and separate typed dimensions for delivery (`LIVE`/`CACHE`/none), freshness (`FRESH`/`STALE`), refresh (`SKIPPED`/`SUCCESS`/`PARTIAL`/failure), and persistence (`NOT_ATTEMPTED`/`PERSISTED`/`SUPERSEDED`/write failure). Preserve source failure/no-data reason and cache invalidity where applicable. A no-cache failure has no recording. Do not collapse coverage, age, fetch, and write outcomes.
- Return a usable live recording even if saving fails or is superseded. On refresh failure, return a valid cache as stale and expose the failure separately. Never publish corrupt or identity-incompatible cache data. Cancellation from source/storage propagates. Repository does not own active selection, lifecycle scheduling, or cross-caller cancellation; `CatalogSourceRegistry` retains source identity/coverage validation and storage retains codec/row validation.

## Storage, migration, and invariants

WX.15 stores one row per `(locationId, namespace, datasetId)`. WX.16 must preserve canonical dataset IDs while storing LIVE windows separately:

- LIVE identity is `(locationId, LIVE, datasetId, asOfEpochSecond, asOfNano)`; encoded catalog `asOf` must match. Do not derive IDs from retrieval time or sort ISO timestamp strings. SIMULATED/REPLAY keep one replacement row per `(locationId, namespace, datasetId)`, with no LIVE accumulation/pruning.
- Add nullable `storedAtEpochSecond`/`storedAtNano`, null only for legacy rows with unknown retrieval time. Unknown is immediately stale and older than all known retrieval times. Retrieval metadata never changes catalog `asOf`, product `issuedAt`, or provenance; keep the payload codec/version semantics unchanged.
- Encode and validate before the write transaction. In one DAO transaction compare incoming `asOf` with the newest for the same location/LIVE/dataset: older means `SUPERSEDED` with no write/prune; equal replaces that window; later inserts a window. A superseded source result remains usable by its caller but cannot become fallback or delete newer data.
- Only after successful insert/replacement, prune LIVE rows location-wide to at most two, ordered by retrieval instant descending, null times last, then `asOf` descending for ties. Failed validation/write or superseded writes never prune; SIMULATED/REPLAY rows are untouched. Latest-`asOf` guard, write, and prune are atomic.
- Cache lookup validates candidates and returns the latest valid compatible window by `asOf`; invalid rows are reported, never used as stale data. Validate location ID, coordinates, timezone, namespace, dataset, provenance, and `asOf` together so another location/origin cannot satisfy a request.
- Migrate Room schema v1 to v2 without losing existing rows or payloads. Legacy LIVE retrieval time stays unknown. Verify migration using Room migration support.
- Preserve source `Success` versus `Partial`, declared support, provider/product metadata, null values, and exact 44-date coverage. Never fill gaps with zero, copy old facts, or claim partial is complete. Network failure is not a new live result; cache-write failure does not discard valid live data.

### Future product-specific refresh handoff

A later bounded plan is required before independent forecast/history scheduling. It must define product-specific source requests/results (product, location, canonical dataset, anchor, provenance, exact coverage, typed usable/partial/failure); per-product retrieval/validity metadata that advances only on that product's usable success; migration that leaves WX.16 product clocks unknown unless truthful times exist; per-product eligibility/freshness/fallback; and how different anchors combine into one valid 44-date catalog or remain separate. It must also define atomic replacement/retention under out-of-order completion, partial success, failed writes, and pruning without losing the last fallback, plus explicit scheduling ownership/cadence. A whole-catalog result/shared `storedAt` cannot establish independent product freshness. WX.16 interfaces remain whole-catalog and introduce no product clocks.

## Steps

- [x] Record baseline storage, source/coverage, Open-Meteo mapping, and test seams in cycle evidence; confirm approved board and policy defaults.
- [x] Add retrieval metadata and pure, configurable freshness/refresh policy under injected `Clock`.
- [x] Implement the location-scoped repository and typed result contract; restore cache first, apply eligible/forced foreground refresh, support truthful partial results, preserve cache on failure, and separate refresh from persistence outcomes.
- [x] Implement v1-to-v2 Room migration and LIVE snapshot identity, ordering guard, atomic write/prune, and local-date fallback behavior. Preserve all legacy rows/payloads and non-LIVE replacement semantics.
- [x] Add controlled-clock/fake-source/storage tests and Room migration/transaction instrumentation for the acceptance cases below. Host fakes do not substitute for Room checks.
- [x] Run planned verification, record exact results/limitations, inspect `git diff --check` and final diff, and close/update roadmap only if every exit passes.

## Acceptance and evidence

After activation, run:

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.catalog.*' --tests 'com.oxygen.weather.timeline.data.openmeteo.*'`
- `./gradlew :app:connectedDebugAndroidTest` for v1→v2 migration preservation and atomic write/supersede/prune on an available device. If unavailable, record the exact blocker; host/fake tests are not equivalent.
- `python scripts/dev.py build`
- `git diff --check`

Acceptance requires passing controlled-clock/repository coverage for: fresh fetch; cache-only startup below threshold; stale cache; forced refresh; refresh failure with and without cache; corrupt cache; write failure with usable live result; midnight rollover with unchanged prior `asOf` and a new request even for a cache under 15 minutes old; one retrieval time for usable whole-catalog Success/Partial; truthful partial support/missingness; location identity; older completion unable to overwrite newer `asOf`; same-`asOf` replacement; distinct `asOf` windows sharing a dataset ID; and pruning only after successful persistence. Room instrumentation must prove migration preserves rows and DAO transaction cannot leave partial save/prune. Evidence records, for relevant cases, exact `asOf`, retrieval time or legacy-unknown, timezone, source/provenance, coverage outcome, and persisted/superseded/write-failure status. No visual/device acceptance claim applies.

Target records:

- Plan: `.codex/plans/057-wx16-cache-refresh-recovery.md`
- Evidence: `.codex/test-artifacts/057-wx16-cache-refresh-recovery/`
- Closeout: `.codex/history/057-wx16-cache-refresh-recovery.md`
- Archived plan: `.codex/history/plans/057-wx16-cache-refresh-recovery.md`

## Risks and exclusions

- Current provider mapping anchors its date window to request `asOf`; next-day refresh may change dates. Never merge incompatible windows to manufacture completeness. Retention ordering uses numeric instant fields, not variable-precision `Instant.toString()`.
- Excluded: UI/visual work, manual refresh control, source/location selection or persistence, app lifecycle scheduling/background work, segment-selective fetches/product clocks/schedules, new providers or SLA interpretation, live archive verification, alerts, retry framework/general job system, location permission, and app-wide/full-suite verification unless separately planned. No changes to SIMULATED/REPLAY generation or retention.
