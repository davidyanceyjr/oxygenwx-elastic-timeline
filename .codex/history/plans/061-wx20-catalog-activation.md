# Cycle plan — 061-wx20-catalog-activation

State: ACTIVE
Roadmap: WX.20
Objective: Load and atomically activate location-scoped catalogs

## Owner-approved design input

The owner approved `.codex/concepts/WX.20-catalog-activation.png` (editable source: `.codex/concepts/WX.20-catalog-activation.svg`) on 2026-10-28. The approved board constrains the work to the data/state activation contract: a request identifies one saved location, origin, and dataset; each origin keeps its own cache namespace; a staged whole-catalog candidate is validated before it can publish; only the newest request token can publish; publication replaces the active selection atomically and increments a view-generation marker without restarting the app. Keep the current selection active on failure, leave other locations and caches untouched, and preserve the exact selected `Instant` when available. If it is unavailable, choose the nearest valid sample; an equal-distance tie selects the earlier sample. Replay requests identify the selected recording explicitly. Never merge fields or provenance across datasets.

## Production boundary

Changes are limited to the catalog loading/activation and selected-location wiring needed for WX.20:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` — add or update the request/state coordinator and its typed active-selection model; touch existing source registry, refresh, storage, or recording contracts only where required to meet the activation invariants.
- `app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorController.kt` and `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` — wire a saved-location selection/restoration event to the activation owner only if the baseline inspection confirms this is necessary. Do not add controls or change selector appearance.
- `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` and focused existing location-selector/controller test files — add deterministic controlled-async coverage for the new activation contract.
- `.codex/test-artifacts/061-wx20-catalog-activation/` — record actual command results and evidence when the cycle is executed.

No other production paths are in scope. If implementation requires renderer, presentation-schema, database-schema, or navigation changes, stop and record the specific need for a separately bounded decision/slice before widening this plan.

## Resolved ownership and composition contract

Repository baseline inspection resolves activation ownership to a catalog-layer, application-owned coordinator, constructed and lifecycle-owned from the current `MainActivity` composition root. The coordinator owns the active-selection `StateFlow`, request sequencing/cancellation, staged candidate acceptance, and the single atomic publish operation. It is not owned by the selector controller, Compose screen state, source registry, or database.

- `LocationSelectorController` and `LocationSelectorStore` remain responsible for saved-location interaction and persistence. A successfully persisted selection is reported to the application coordinator as a committed selection event; failed selection writes do not dispatch activation. Selector open/refresh restoration remains presentation state and is not the startup activation trigger.
- Startup restoration is initiated by the application coordinator through the saved-location repository, even when the selector is never opened. The selector remains unaware of catalog sources and active catalog state. `MainActivity` remains the existing wiring/composition root; do not create another UI route or change selector appearance.
- `CatalogSourceRegistry` remains the origin-to-source dispatch boundary and validates source-result identity, provenance, facts, support, and catalog window. It does not cache, choose the selected location, or publish active state.
- `CatalogDatasetStorage` remains the whole-recording persistence/retrieval boundary. Use its existing `(location, namespace, datasetId)` identity and full envelope validation; do not add active-selection state or duplicate codec/row validation there.
- LIVE requests go through `LiveCatalogRepository`, retaining its cache lookup, refresh policy, source call, stale-cache fallback, and bounded live-snapshot persistence. Do not duplicate those policies in the activation coordinator.
- SIMULATED and REPLAY requests use `CatalogDatasetStorage` with their matching isolated namespace and dataset identity, and use `CatalogSourceRegistry` for a source load when no valid matching stored recording is available. Persist only the complete validated recording in that same namespace. Do not introduce a new freshness/refresh policy for these origins in WX.20.
- **Owner resolution (2026-10-28):** REPLAY namespace storage contains the original captured LIVE/SIMULATED `CatalogRecording` and its original provenance. Loading or sourcing that recording creates an in-memory catalog copy with REPLAY provenance for activation. Never encode or persist the converted REPLAY-provenance catalog; the existing recording codec intentionally rejects it. The request's explicit `replayRecordingId` must equal its dataset ID and selects the `(location, REPLAY, datasetId)` stored recording/source identity.
- For every origin, the coordinator stages the resulting complete recording, then performs the final request-token/current-location check immediately before publication. A valid cached recording returned by the LIVE repository is eligible for activation, including stale-cache fallback after refresh failure; a failed or invalid result with no valid recording leaves the active selection unchanged. Persistence success is not a prerequisite for publishing an otherwise valid source result.
- The stored selection is re-read at startup and compared by full `CatalogLocation` identity. The approved board fixes startup and newly selected-location activation to `LIVE` with dataset `forecast-44d`. Only a committed selection event or startup restoration may change the selected location; opening/refreshing the selector must not create a new activation request. Other origin/dataset requests remain internal coordinator inputs for deterministic WX.20 switching coverage, not new user-facing controls.

The production baseline supporting this contract is `MainActivity.onCreate`, `LocationSelectorController.select/refreshSnapshot`, `SavedLocationRepository.select/restore`, `CatalogSourceRegistry.load`, `LiveCatalogRepository.load`, and `CatalogDatasetStorage.load/save`. If implementation reveals one of these contracts has changed, record the concrete mismatch and stop before widening the production boundary.

## Functional invariants

- A load request is bound to the saved `CatalogLocation`, `CatalogOrigin`, `DatasetId` (and explicit recording identity for REPLAY), and its request sequence/token. Dataset identity/provenance must match the request before activation.
- LIVE, SIMULATED, and REPLAY load and cache in isolated namespaces. A staged candidate contains one complete recording for one location and one origin; no field, metadata, or location merging is allowed.
- Only the latest matching request for the currently selected location may publish. Cancellation is propagated, and late completion is rejected even if cancellation arrives too late. A→B→A is three distinct requests.
- Validate the complete catalog/recording envelope before publication. A failed, invalid, empty, or stale replacement leaves the previous valid active selection intact.
- Publish catalog, origin/provenance, location, dataset/recording identity, cache/freshness metadata, selected instant, and view-generation marker as one state transition. Non-selected locations and their stored data remain unchanged.
- Preserve the exact selected absolute `Instant` if the replacement contains it; otherwise choose its nearest valid sample, breaking ties toward the earlier instant. No source switch or refresh may require an app restart.
- Keep the current SampleForecast rendering path and existing timeline/gesture behavior unchanged. WX.20 does not migrate the renderer or expose source/debug controls.

## Steps

- [x] Verify the resolved ownership/composition contract against the baseline files and focused tests; record any mismatch and the concrete source evidence in cycle evidence. Do not reopen the selector-versus-coordinator ownership choice absent evidence that the recorded boundaries are false.
- [x] Add the smallest typed application-owned activation coordinator/state model and request path. Route LIVE through `LiveCatalogRepository`; route SIMULATED/REPLAY through their isolated storage namespace and `CatalogSourceRegistry` on cache miss/invalid cache. Reuse existing source/storage validation, then apply request-token/current-location validation, nearest-instant retention, and atomic publication. Keep persistence separate from active state and avoid unrelated source/storage refactors.
- [x] Add deterministic controlled-async checks covering A→B→A races, repeated same-location refresh, cancellation/late completion, failure and invalid result retention, cache restore and app recreation, all three origin switches without mixed fields/provenance/location, live-cache survival during simulation/replay, non-selected dataset stability, selected-instant exact/nearest/tie behavior, generation changes, and switching without restart.
- [x] Run only the focused verification authorized below, capture the actual results, inspect `git diff --check` and the complete diff, then close only if every WX.20 exit condition is evidenced.

## Acceptance and verification

The WX.20 exit is met only when all roadmap conditions are evidenced: controlled asynchronous checks cover A→B→A, repeated same-location refresh, failures, cache restore, and app recreation; only the newest matching location/request publishes active weather; LIVE↔SIMULATED↔REPLAY never mixes fields, metadata, or locations; failed replacement is safe; live cache survives simulation; selected-instant retention/clamping and view-generation markers are deterministic; non-selected datasets are unchanged; and switching needs no app restart.

Once this cycle is activated and implementation is explicitly underway, the planned focused verification is:

- `python scripts/dev.py workflow` — cycle/workflow integrity.
- `python scripts/dev.py contract` — architecture and release-boundary contract.
- Focused Gradle unit tests for the new activation coordinator/state and affected existing source/storage/controller tests; name the exact test classes in the closeout after implementation establishes them. Do not run the full suite unless a separate plan authorizes it.
- `python scripts/dev.py build` — debug compilation of the wired application path.
- `git diff --check` and final diff review.

This is a data/state slice with no intended visual or interaction change, so installed screenshots, font-scale/RTL/reduced-motion review, and UI acceptance are not exit evidence. If wiring changes a user-visible interaction or renderer behavior, stop and revise the bounded plan before proceeding.

Evidence targets (planned, not yet created): `.codex/test-artifacts/061-wx20-catalog-activation/`; closeout `.codex/history/061-wx20-catalog-activation.md`; archived plan `.codex/history/plans/061-wx20-catalog-activation.md`.

## Risks / assumptions

- The current app still renders `SampleForecast`; selector persistence, source loading, and active rendering are separate concerns. This slice introduces activation state without silently migrating the renderer.
- `CatalogSourceRegistry`, `LiveCatalogRepository`, and `CatalogDatasetStorage` already validate or isolate portions of the contract. Baseline inspection must reuse these boundaries and avoid duplicating validation or weakening their contracts.
- The app currently constructs catalog persistence in `MainActivity`, while selector restoration occurs only when `LocationSelectorController.open/refreshSnapshot` runs. WX.20 startup activation must therefore be initiated by the application coordinator independently of selector visibility; selector state is not the active-catalog authority.
- `CatalogLocation` equality and dataset identity must be checked as complete request identity, not by location ID alone. Tokens must distinguish A→B→A and overlapping refreshes for the same location.
- The nearest-sample rule needs a deterministic ordering for sparse sample times and an explicit earlier-sample tie break. Empty candidates cannot activate.
- Cache restore and app recreation must not let an older asynchronous response supersede a newer location/request after restart.
- Owner approval applies to the board as supplied, including the earlier-sample tie rule and explicit replay-recording input. This plan treats those details as frozen design constraints.

## Out of scope

- Debug or source-selector UI, additional selector entry points, or selector visual changes (WX.22 and separately owner-directed route work remain distinct).
- Connecting the active catalog to `ForecastProjection`, the immersive renderer, the 44-day timeline, or source-specific presentation; those are later WX slices.
- Provider transport/mapper changes, broad source-contract changes, forecast schema changes, database migrations, new caching policy, cache eviction redesign, or cross-location synchronization. A narrowly scoped replay-request identity field/key may be added only if baseline inspection proves the existing identity cannot uniquely select the approved recording; document that finding and keep the change limited to the replay request boundary.
- Application-ID/signing/release identity changes, official alerts, GPS/background location, deprecated oxygenWX changes, or general UI/accessibility redesign.
- Installed visual acceptance claims. No screenshot or device result is implied by the concept board or this plan.
