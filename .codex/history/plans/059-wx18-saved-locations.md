# Cycle plan — 059-wx18-saved-locations

State: ACTIVE
Cycle ID: `059-wx18-saved-locations`
Roadmap: WX.18
Objective: Persist stable saved-location records and one restorable selected-location ID, with explicit catalog association and removal retention.

## Authority and approved design input

- WX.18 is the sole `NEXT` roadmap slice in the current working roadmap. Its dependencies WX.15 and WX.17 are recorded DONE there.
- The owner approved `.codex/concepts/WX.18-saved-locations.png` on 2026-10-28; editable source: `.codex/concepts/WX.18-saved-locations.svg`. The board is a design input, not implementation or acceptance evidence.
- Owner decision on 2026-10-28: a canonical place has the same identity across users and search adapters, while its exact weather-query coordinates and IANA timezone are stored separately as that place's weather point. GeoNames is the canonical place registry for this slice. The current Open-Meteo geocoding adapter maps its GeoNames-backed result ID into a namespaced canonical `LocationId`; other search adapters must map to that same registry identity rather than use their own provider IDs.
- This refines the board's identity boundary: the accepting layer persists the canonical `LocationId` supplied by the provider-neutral place-search contract; it does not invent a per-install ID. Catalog storage remains keyed by that stable canonical location ID across LIVE, SIMULATED, and REPLAY namespaces. Coordinates and timezone are the persisted weather point, not identity.
- WX.19 owns search/selector UI and display of persisted location attribution. WX.20 owns catalog loading and activation. Neither is included here.

The Open-Meteo Geocoding API documents its result `id` as unique for a location, says its location data is based on GeoNames, and exposes an ID lookup endpoint: https://open-meteo.com/en/docs/geocoding-api. Treat that ID as canonical only through the explicit GeoNames authority mapping; keep source attribution/reference separate. If a future search adapter cannot map a result to the same GeoNames feature, it cannot silently claim an existing canonical identity. This does not claim that external registry assignments are immutable; once persisted, this app's canonical ID is immutable, and any future registry correction requires an explicit crosswalk/migration rather than silent key replacement.

This plan resolves the board's open state and retention questions for WX.18. These are bounded implementation policies, not claims that the concept board itself specified them. WX.15 establishes retention until explicit deletion/pruning and prohibits silent eviction; WX.16 applies a two-snapshot cap only to LIVE refreshes. Accordingly, explicit saved-location removal is the deletion event for all catalogs associated with that location, while ordinary add/select operations do not prune catalog history.

## Production boundary

May change:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` for saved-location domain types, repository/state contracts, Room entities/DAO, persistence mapping, and narrowly scoped catalog-location cleanup integration.
- `PlaceSearchCandidate` and `OpenMeteoPlaceSearcher` for the narrow contract addition that exposes the canonical GeoNames-backed place ID separately from `sourceReference`; preserve the existing request behavior, candidate coordinates, timezone, and attribution.
- `CatalogStorageDatabase.kt`, `CatalogDatasetStorage.kt`, and the relevant schema snapshot under `app/schemas/` to add explicit saved-location/selection persistence and location-scoped removal.
- Focused host tests under `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` and Room database/migration tests under `app/src/androidTest/java/com/oxygen/weather/timeline/data/catalog/`.
- `.codex/test-artifacts/059-wx18-saved-locations/`, this plan, and on closeout the matching history, archived plan, and WX.18 roadmap status/evidence fields.

Do not change Compose/UI, presentation, weather rendering, WX.17 search request/provider behavior beyond mapping the existing GeoNames-backed result ID into the canonical place contract, catalog fetching/activation, source switching, GPS/location permission, timeline behavior, or the deprecated oxygenWX application. Do not add a geocoder, change provider query parameters, or make a weather provider's ID the canonical key.

### Baseline guard before activation

WX.16 database/refresh changes and WX.17 search-provider changes, including their roadmap/history updates, are committed in `c87bfe7`. They are not part of cycle 059. Before activation, re-read `git status`, `docs/ROADMAP.md`, and the Room schema. Build the migration from the latest actual schema; the current committed schema is version 2, so the expected next migration is 2→3 if that baseline remains current. Record the exact baseline in the evidence README before implementation.

Repository inspection on 2026-10-28 found committed `CatalogStorageDatabase` at version 2, exported `app/schemas/com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase/1.json` and `2.json`, and `MIGRATION_1_2` in `CatalogStorageDatabase.kt`. The v2 `catalog_datasets` primary key includes location ID, namespace, dataset ID, and `asOf` seconds/nanos; `CatalogDatasetDao.deleteLocation(locationId)` already deletes all namespaces for one ID. At activation, confirm the schema and migration chain still match these facts. If version 2 remains the baseline, add a sequential 2→3 migration and exported v3 schema while retaining and verifying 1→2→3; if it differs, update this plan's migration source and required migration cases before coding. Do not silently replace the v2 schema or edit an existing shipped migration.

### Storage and transaction boundary resolved from the current code

- `CatalogStorageDatabase` is the single Room database for catalog rows. Add saved-location and singleton selection persistence to this database; the saved-location repository owns state changes and uses this same database's transaction boundary. Separate DAO calls or separate database instances do not establish atomic location removal.
- `CatalogDatasetRow.locationId` is already the explicit association key across `LIVE`, `SIMULATED`, and `REPLAY`; use the canonical `LocationId` value there, with no captured-origin or extra catalog-to-location mapping. The canonical ID is the namespaced GeoNames feature identity, not a weather-provider ID, source reference, display field, or coordinate hash. Existing catalog rows may predate saved-location rows, so adding saved locations must not rewrite, prune, or require migration of those catalog rows. Removal by canonical ID deletes every matching catalog row regardless of namespace, dataset ID, or `asOf`, including rows that predated the saved record.
- The existing `CatalogDatasetStorage.deleteLocation` / DAO delete is a catalog-only primitive. The new saved-location removal operation must coordinate saved-row deletion, conditional selected-pointer clearing, and that primitive inside one `CatalogStorageDatabase` transaction. It must not report success after only part of that transaction commits. Keep catalog-only deletion available only for its existing catalog-storage contract; it does not implement saved-location removal.
- The saved-location repository owns the `CatalogStorageDatabase` instance directly and uses `withTransaction` for state-changing operations. Its removal transaction checks that the saved ID exists before any deletion; a missing ID returns an explicit no-mutation outcome. For an existing ID, delete all `catalog_datasets` rows matching `locationId` without a namespace filter, clear the selected pointer only when it equals that ID, then delete the saved row. Return success only after the transaction commits; an exception rolls all steps back. Use the database's own DAOs within this transaction, not a separately opened database or a sequence of independent repository calls.
- Add, select, restore repair, and remove must serialize their relevant read/check/write steps against concurrent repository operations. Canonical-ID uniqueness/AlreadySaved handling, selection of an existing ID, and selected-row removal cannot rely on a read followed by an unguarded write outside a transaction. A failed or conflicting operation leaves saved rows, selection, and catalogs unchanged. Restoration may clear only a stale selected pointer; malformed saved rows return an explicit failure and do not trigger broad cleanup.
- The v2 catalog table, its payload bytes, identity columns, indexes, namespace semantics, and LIVE retention behavior remain intact in the new migration. Additive location/selection tables and indexes are sufficient unless installed migration evidence exposes a concrete incompatibility. An existing v1 database must migrate through the preserved v1→v2 step and the new step. Validate both starting schemas with installed Room migration tests, including existing catalog bytes/metadata and indexes.

## Data and state contract

- Persist one saved-location record keyed by the canonical `LocationId`, serialized as `geonames:<positive decimal feature id>`. Introduce an explicit typed canonical-place value in the provider-neutral search contract; it validates the authority namespace and positive numeric identifier. `OpenMeteoPlaceSearcher` maps its existing GeoNames-backed result ID into this value. Other search adapters must map their results to the same GeoNames feature identity. Keep the exact `CatalogLocation` coordinates and validated IANA timezone as the weather-query point, separate from the canonical identity.
- GeoNames feature identity defines the entity for this slice: for example, the Chicago city feature and a separately named airport or neighboring municipality are different features. Same-name features in different administrative areas/countries remain separate by canonical ID, even when names or coordinates happen to coincide. Do not derive identity from display labels, rounded coordinates, timezone, or coordinate hashes. Keep the raw `sourceReference` and attribution as descriptive/provenance metadata, not as the key.
- One canonical place has one saved weather-query point in WX.18. The adapter must return the coordinates/timezone associated with the canonical GeoNames feature. The saved record preserves that point exactly; adding the same canonical ID again never silently replaces it. If an adapter presents the same ID with a conflicting point, return an explicit conflict/no-mutation outcome. Supporting multiple custom points within one canonical place requires a separate future contract and is not part of WX.18.
- Adding a location does not select it. Add and select are separate operations, matching the approved board and WX.19's separate Add/Select actions. A search candidate's canonical ID is its saved/catalog identity from first acceptance onward; the app does not generate a per-install replacement ID.
- The canonical ID is the same across users and adapters that map to the same GeoNames feature. Re-adding an already saved canonical ID returns an explicit already-saved outcome and does not overwrite its weather point, display metadata, selection, or catalogs. A conflicting weather point for the same canonical ID returns an explicit conflict and makes no mutation. No identity is generated from a user's device or accepting event.
- Persist one nullable `selectedLocationId` separately from the location rows. Selecting requires an existing saved ID. Selecting does not fetch, replace, or activate weather.
- A missing ID on select and a missing ID on remove are explicit no-mutation outcomes; neither operation chooses another location or deletes unrelated catalogs. The selected pointer is application state, not an active weather/catalog pointer.
- Removing an unselected location preserves the current selection. Removing the selected location clears the pointer to `null`; never auto-select a different place. An empty saved-location set and no selection are valid states.
- Explicit removal deletes the saved record and all catalog snapshots associated with that `LocationId` in LIVE, SIMULATED, and REPLAY namespaces in the same database transaction as selected-pointer cleanup. This makes removal an explicit, location-scoped delete under WX.15's retention rule. Other locations and their snapshots remain untouched. The WX.16 two-snapshot LIVE cap continues to govern LIVE writes; WX.18 adds no broader age/count pruning for SIMULATED or REPLAY. Ordinary add/select/write operations do not prune history.
- Restoration returns the persisted selected location only when its ID resolves to a valid saved record. A missing/stale selected pointer is cleared and yields no selection; do not substitute another location. An empty store restores as no selection. A malformed saved record fails explicitly and is not reconstructed from its label/provider metadata; surface the failure to the caller and leave unrelated records and catalogs intact.
- Persist the `PlaceSearchCandidate` fields needed for later display as descriptive metadata: name, country name/code, ordered administrative areas, optional source reference, and `PlaceSearchAttribution` fields. Persist canonical `LocationId` separately from the validated weather point. Reconstruct and validate all fields on read; an invalid canonical ID, zone, coordinate, required display field, or attribution is a malformed record, not a cue to infer replacement values. The accepting seam maps the candidate's canonical place ID directly to `CatalogLocation.id`; it does not generate a fresh local ID.
- Candidates without a valid canonical GeoNames identity are not saveable and must produce an explicit outcome; do not fall back to names, coordinates, source-reference text, or generated IDs. Existing canonical IDs never change if a display name, provider, or adapter changes; future registry corrections require an explicit migration/crosswalk.
- Keep Room schema versioning and catalog payload-versioning distinct. Do not alter canonical weather payload fields, namespace meanings, provenance, or catalog identity validation.

## Functional invariants

- Saved identity is the namespaced canonical GeoNames feature ID, stable across process/database reopen and shared by users/adapters that map to the same feature; it is independent of display text, source attribution, coordinate rounding, and weather origin.
- The one weather-query point attached to a canonical place preserves IANA timezone and full coordinate precision exactly. Two distinct GeoNames features cannot alias even if their names, coordinates, or zones coincide; one GeoNames feature resolves to the same canonical ID across mapped adapters.
- Selection references zero or one existing saved location; it never dangles after a successful repository operation or database restore.
- Location deletion is explicit and atomic across the saved row, selection pointer, and all catalogs associated with that location. No other location's saved state or catalog rows are removed.
- LIVE, SIMULATED, and REPLAY remain isolated storage namespaces; catalog association uses `LocationId`, not the namespace or captured source origin.
- No network, UI, GPS, source activation, or timeline interaction is introduced.

## Ordered steps

1. Before activation, confirm the committed baseline identified above. Reinspect WX.15 storage, WX.16's latest database/retention changes, WX.17's candidate and attribution types, the approved WX.18 board, the Open-Meteo geocoding ID mapping, and all current Room schemas. Record the selected migration source version and repository state in cycle evidence.
2. Finalize the minimal saved-location record and repository outcomes against the contract above. Keep saved-state persistence provider-neutral. Add a typed canonical place ID to the place-search contract and map the current Open-Meteo result ID to its GeoNames registry namespace; retain `sourceReference` separately. Do not generate IDs at the accepting boundary. Make same-ID replay, conflicting-point, and missing-canonical-ID outcomes explicit and mutation-safe.
3. Add saved-location and nullable selection persistence to `CatalogStorageDatabase`. Add an explicit sequential migration from the reconciled actual baseline with exported schema snapshot and no destructive fallback. If version 2 remains current, implement `MIGRATION_2_3` by adding location and selection tables without rebuilding `catalog_datasets`; preserve v2 catalog columns, bytes, indexes, and `MIGRATION_1_2`. Verify both 1→2→3 and 2→3 from their exported starting schemas. Keep location rows, selection, and catalog rows in one database transaction boundary so selected-location removal and all-namespace catalog cleanup are atomic.
4. Implement repository operations for add, remove, select, list/lookup, restore, and explicit association by canonical `LocationId`. Recognize an existing canonical ID without overwriting its saved point; reject conflicting points and missing canonical IDs without mutation. Keep add independent of selection; clear selection on selected removal; remove only matching location/catalog state. Make corrupt/missing restore outcomes explicit.
5. Add focused host-side contract/repository tests and Android Room tests. Cover canonical-ID and exact weather-point round trips; two adapters mapping the same GeoNames feature to the same ID; distinct same-name GeoNames features remaining distinct; repeated-ID no-overwrite and conflicting-point/missing-ID no-mutation outcomes; selection persistence, empty and stale selection recovery, malformed-record failure, selected and unselected removal, all-namespace cleanup including preexisting rows, other-location isolation, transaction rollback after a forced removal failure, and concurrent operation ordering. From both exported starting schemas, verify migration data preservation, including catalog payload bytes, metadata, and indexes. Use installed Room tests as the authority for transactions and migrations; host fakes cover repository outcomes but cannot prove database atomicity.
6. Run the authorized scoped checks below. Retain raw command output, device/schema/build details, and limitations under the evidence directory. Inspect `git diff --check` and the complete cycle diff, then close only if every WX.18 exit condition is evidenced; otherwise record the precise gap and leave WX.18 open/blocked as applicable.

## Acceptance and evidence

Planned checks for execution after this plan is activated (not run during planning):

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- Focused host checks: `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.catalog.*'`
- Room persistence/migration on an available emulator/device: `./gradlew :app:connectedDebugAndroidTest`; retain exact instrumentation logs and the serial/device/API details. A host fake DAO is not equivalent migration evidence.
- `python scripts/dev.py build`
- `git diff --check`

Exit is met only when evidence demonstrates all of the following:

- Saved records survive database close/reopen with the same namespaced canonical GeoNames `LocationId`, full-precision weather-point coordinates, IANA timezone, display/disambiguation data, and attribution needed by WX.19.
- Search adapters that map the same GeoNames feature produce the same canonical `LocationId`; distinct same-name features remain separately retrievable. Raw provider/source references, labels, and coordinates are not identity fallbacks. Re-adding a canonical ID preserves state; conflicting point or missing canonical ID fails without mutation.
- Add does not change selection; select persists and restores only a saved ID; empty selection is valid; stale selected IDs are cleared without choosing a fallback.
- Removing a selected location clears selection and deletes every catalog namespace for only that ID atomically; removing an unselected location preserves selection; all other locations and their datasets remain intact.
- The declared Room migration preserves existing catalog rows, payload bytes/metadata, and indexes as applicable; schema export and installed Room migration checks agree. No destructive fallback is configured.
- Contract, focused host and Room checks, build, workflow, and diff review results are recorded exactly. No UI/device visual acceptance is claimed for this data/state-only slice.

Target records:

- Plan: `.codex/plans/059-wx18-saved-locations.md`
- Evidence: `.codex/test-artifacts/059-wx18-saved-locations/README.md` and attached logs/schema verification
- Closeout: `.codex/history/059-wx18-saved-locations.md`
- Archived plan after closure: `.codex/history/plans/059-wx18-saved-locations.md`

## Risks and assumptions

- WX.15's explicit-delete retention rule means saved-location removal deletes all associated LIVE, SIMULATED, and REPLAY snapshots; the WX.16 two-snapshot cap remains specific to LIVE refresh. No background pruning, cross-location cleanup, or automatic selection fallback is added. This makes removal destructive to that location's local catalog history, so implementation and repository outcomes must expose it as explicit removal and tests must prove other locations remain untouched.
- Selected-location state is nullable and independent of weather activation. Removing the selected location or finding a stale selection on restore yields no selection; it never chooses another saved place. Empty storage is valid. Malformed saved records fail explicitly rather than being reconstructed from display/provider data.
- The committed Room database schema version is 2. If the baseline changes before activation, re-plan the migration step to the latest sequential version and verify every intervening migration; never edit an already shipped migration.
- GeoNames is the canonical registry authority for WX.18. Open-Meteo documents geocoding results as GeoNames-based and exposes a unique result ID, but external registry IDs are not assumed immutable forever. Persisted IDs remain immutable in this app; any future registry correction or new search adapter requires an explicit crosswalk and must not silently rewrite saved IDs or catalog associations. A result that cannot be mapped is not saveable.
- WX.18 supports exactly one adapter-normalized weather point per canonical GeoNames feature. If the current API or future adapter cannot provide a consistent point for a canonical feature, return an explicit conflict and keep the saved point; do not silently update it or widen this slice to custom/multiple points.
- Persist place attribution as descriptive metadata because WX.17 supplies it and WX.19 owns displaying it. It is not an identity or key.
- Room's transaction guarantee requires location, selection, and catalog tables to share the same database instance. Verify the existing database wiring before implementation; do not claim atomicity across separate databases.
- A schema/storage integration failure should be recorded with exact evidence. Do not solve it by changing the canonical catalog schema, provider contracts, or base toolchain within WX.18.

## Out of scope

Search and selector UI, main-screen access route, weather loading/refresh, active catalog selection or source switching, adding/changing geocoding providers or query behavior, GPS or background location, multiple/custom weather points per canonical place, weather payload redesign, UI/accessibility/device acceptance, official alerts, timeline/atmosphere changes, production release integration, and changes to deprecated oxygenWX files.
