# Cycle plan — 059-wx18-saved-locations

State: PLANNED
Cycle ID: `059-wx18-saved-locations`
Roadmap: WX.18
Objective: Persist stable saved-location records and one restorable selected-location ID, with explicit catalog association and removal retention.

## Authority and approved design input

- WX.18 is the sole `NEXT` roadmap slice in the current working roadmap. Its dependencies WX.15 and WX.17 are recorded DONE there.
- The owner approved `.codex/concepts/WX.18-saved-locations.png` on 2026-10-28; editable source: `.codex/concepts/WX.18-saved-locations.svg`. The board is a design input, not implementation or acceptance evidence.
- The board's identity boundary follows WX.17: the accepting layer creates a local `LocationId` once. Provider IDs, names, and coordinates are never storage identity. Catalog storage remains keyed by the same stable location ID across LIVE, SIMULATED, and REPLAY namespaces.
- WX.19 owns search/selector UI and display of persisted location attribution. WX.20 owns catalog loading and activation. Neither is included here.

This plan resolves the board's open state and retention questions for WX.18. These are bounded implementation policies, not claims that the concept board itself specified them. WX.15 establishes retention until explicit deletion/pruning and prohibits silent eviction; WX.16 applies a two-snapshot cap only to LIVE refreshes. Accordingly, explicit saved-location removal is the deletion event for all catalogs associated with that location, while ordinary add/select operations do not prune catalog history.

## Production boundary

May change:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` for saved-location domain types, repository/state contracts, Room entities/DAO, persistence mapping, and narrowly scoped catalog-location cleanup integration.
- `CatalogStorageDatabase.kt`, `CatalogDatasetStorage.kt`, and the relevant schema snapshot under `app/schemas/` to add explicit saved-location/selection persistence and location-scoped removal.
- Focused host tests under `app/src/test/java/com/oxygen/weather/timeline/data/catalog/` and Room database/migration tests under `app/src/androidTest/java/com/oxygen/weather/timeline/data/catalog/`.
- `.codex/test-artifacts/059-wx18-saved-locations/`, this plan, and on closeout the matching history, archived plan, and WX.18 roadmap status/evidence fields.

Do not change Compose/UI, presentation, weather rendering, the WX.17 search adapter/provider behavior, catalog fetching/activation, source switching, GPS/location permission, timeline behavior, or the deprecated oxygenWX application.

### Baseline guard before activation

The planning worktree currently contains uncommitted WX.16 database/refresh changes and WX.17 search-provider changes, plus their roadmap/history/artwork updates. They are not part of cycle 059. Before activation, reconcile or commit that work and re-read `git status`, `docs/ROADMAP.md`, and the Room schema. Build the migration from the latest actual schema; do not overwrite or assume the tracked WX.15-only baseline. The current worktree schema is version 2, so the expected next migration is 2→3 if that baseline remains current. Record the exact baseline in the evidence README before implementation.

Repository inspection on 2026-10-28 found `CatalogStorageDatabase` at version 2 in the worktree, exported `app/schemas/com.oxygen.weather.timeline.data.catalog.CatalogStorageDatabase/1.json` and `2.json`, and `MIGRATION_1_2` in `CatalogStorageDatabase.kt`. The v2 `catalog_datasets` primary key includes location ID, namespace, dataset ID, and `asOf` seconds/nanos; `CatalogDatasetDao.deleteLocation(locationId)` already deletes all namespaces for one ID. These are worktree facts, not a committed baseline guarantee. At activation, compare the reconciled schema and migration chain with these facts. If version 2 remains the baseline, add a sequential 2→3 migration and exported v3 schema while retaining and verifying 1→2→3; if it differs, update this plan's migration source and required migration cases before coding. Do not silently replace the v2 schema or edit an existing shipped migration.

### Storage and transaction boundary resolved from the current code

- `CatalogStorageDatabase` is the single Room database for catalog rows. Add saved-location and singleton selection persistence to this database; the saved-location repository owns state changes and uses this same database's transaction boundary. Separate DAO calls or separate database instances do not establish atomic location removal.
- `CatalogDatasetRow.locationId` is already the explicit association key across `LIVE`, `SIMULATED`, and `REPLAY`; no provider ID, descriptive field, captured origin, or extra catalog-to-location mapping is needed. Existing catalog rows may precede saved-location rows, so adding saved locations must not rewrite, prune, or require migration of those catalog rows. Removal by saved ID deletes every matching catalog row regardless of namespace, dataset ID, or `asOf`, including rows that predated the saved record.
- The existing `CatalogDatasetStorage.deleteLocation` / DAO delete is a catalog-only primitive. The new saved-location removal operation must coordinate saved-row deletion, conditional selected-pointer clearing, and that primitive inside one `CatalogStorageDatabase` transaction. It must not report success after only part of that transaction commits. Keep catalog-only deletion available only for its existing catalog-storage contract; it does not implement saved-location removal.
- Add, select, restore repair, and remove must serialize their relevant read/check/write steps against concurrent repository operations. Duplicate-ID rejection, selection of an existing ID, and selected-row removal cannot rely on a read followed by an unguarded write outside a transaction. A failed operation leaves saved rows, selection, and catalogs unchanged. Restoration may clear only a stale selected pointer; malformed saved rows return an explicit failure and do not trigger broad cleanup.
- The v2 catalog table, its payload bytes, identity columns, indexes, namespace semantics, and LIVE retention behavior remain intact in the new migration. Additive location/selection tables and indexes are sufficient unless installed migration evidence exposes a concrete incompatibility. An existing v1 database must migrate through the preserved v1→v2 step and the new step. Validate both starting schemas with installed Room migration tests, including existing catalog bytes/metadata and indexes.

## Data and state contract

- Persist one saved-location record keyed by its opaque, caller-created `LocationId`. Keep `CatalogLocation`'s precise coordinates and validated IANA timezone. Persist the human-readable name and disambiguating country/administrative context needed to restore the chosen WX.17 candidate; preserve its source/attribution metadata for later WX.19 display, separate from identity.
- Do not persist or promote a provider result ID as a local key. Never derive identity from a label, rounded coordinates, timezone, or coordinate hash. Same-name locations in different regions/timezones remain separate. Distinct caller-supplied IDs remain distinct even if descriptive fields coincide.
- Adding a location does not select it. Add and select are separate operations, matching the approved board and WX.19's separate Add/Select actions. An accepted candidate receives one ID at the accepting boundary; later reads and selection retain it.
- A repeated `LocationId` is rejected without replacing the original record or changing selection/catalog data. There is no implicit merge, overwrite, or coordinate-based duplicate suppression.
- Persist one nullable `selectedLocationId` separately from the location rows. Selecting requires an existing saved ID. Selecting does not fetch, replace, or activate weather.
- A missing ID on select and a missing ID on remove are explicit no-mutation outcomes; neither operation chooses another location or deletes unrelated catalogs. The selected pointer is application state, not an active weather/catalog pointer.
- Removing an unselected location preserves the current selection. Removing the selected location clears the pointer to `null`; never auto-select a different place. An empty saved-location set and no selection are valid states.
- Explicit removal deletes the saved record and all catalog snapshots associated with that `LocationId` in LIVE, SIMULATED, and REPLAY namespaces in the same database transaction as selected-pointer cleanup. This makes removal an explicit, location-scoped delete under WX.15's retention rule. Other locations and their snapshots remain untouched. The WX.16 two-snapshot LIVE cap continues to govern LIVE writes; WX.18 adds no broader age/count pruning for SIMULATED or REPLAY. Ordinary add/select/write operations do not prune history.
- Restoration returns the persisted selected location only when its ID resolves to a valid saved record. A missing/stale selected pointer is cleared and yields no selection; do not substitute another location. An empty store restores as no selection. A malformed saved record fails explicitly and is not reconstructed from its label/provider metadata; surface the failure to the caller and leave unrelated records and catalogs intact.
- Persist the `PlaceSearchCandidate` fields needed for later display as descriptive metadata: name, country name/code, ordered administrative areas, optional source reference, and `PlaceSearchAttribution` fields. Keep the validated `CatalogLocation` coordinates/timezone and caller-created ID as the location identity. Stored metadata is reconstructed and validated on read; an invalid zone, coordinate, required display field, or attribution is a malformed record rather than a cue to infer replacement values. WX.17's `PlaceSearchCandidate.toCatalogLocation(id)` remains the accepting seam; WX.18 does not change the search provider or choose an ID-generation algorithm.
- Duplicate-ID insertion fails without mutation. Re-adding an existing location under a new ID is allowed; descriptive similarity is not a deduplication rule. This preserves caller-created identity and avoids lossy name/coordinate heuristics.
- Keep Room schema versioning and catalog payload-versioning distinct. Do not alter canonical weather payload fields, namespace meanings, provenance, or catalog identity validation.

## Functional invariants

- Saved identity is stable across process/database reopen and is independent of provider identity, display name, coordinate rounding, and weather origin.
- IANA timezone and full coordinate precision survive persistence exactly; two same-name places with different coordinates/zones cannot alias.
- Selection references zero or one existing saved location; it never dangles after a successful repository operation or database restore.
- Location deletion is explicit and atomic across the saved row, selection pointer, and all catalogs associated with that location. No other location's saved state or catalog rows are removed.
- LIVE, SIMULATED, and REPLAY remain isolated storage namespaces; catalog association uses `LocationId`, not the namespace or captured source origin.
- No network, UI, GPS, source activation, or timeline interaction is introduced.

## Ordered steps

1. Before activation, reconcile the dirty baseline identified above. Reinspect WX.15 storage, WX.16's latest database/retention changes, WX.17's candidate and attribution types, the approved WX.18 board, and all current Room schemas. Record the selected migration source version and repository state in cycle evidence.
2. Finalize the minimal saved-location record and repository outcomes against the contract above. Keep the record provider-neutral and persistence-only; make ID creation injectable at the accepting boundary so collisions and stable reuse can be tested without deriving IDs from provider data.
3. Add saved-location and nullable selection persistence to `CatalogStorageDatabase`. Add an explicit sequential migration from the reconciled actual baseline with exported schema snapshot and no destructive fallback. Preserve v2 catalog columns, bytes, indexes, and `MIGRATION_1_2`; verify the full migration chain. Keep location rows, selection, and catalog rows in one database transaction boundary so selected-location removal and all-namespace catalog cleanup are atomic.
4. Implement repository operations for add, remove, select, list/lookup, restore, and explicit association by `LocationId`. Reject duplicate IDs; keep add independent of selection; clear selection on selected removal; remove only matching location/catalog state. Make corrupt/missing restore outcomes explicit.
5. Add focused host-side contract/repository tests and Android Room tests. Cover exact identity/metadata round trips, duplicate IDs, ambiguous names/timezones, missing-ID no-mutation outcomes, selection persistence, empty and stale selection recovery, malformed-record failure, selected and unselected removal, all-namespace cleanup including preexisting rows, other-location isolation, transaction rollback and concurrent operation ordering, and migration data preservation from each supported starting schema. Use installed Room tests as the authority for transactions and migrations; host fakes cover repository outcomes but cannot prove database atomicity.
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

- Saved records survive database close/reopen with the same `LocationId`, full-precision coordinates, IANA timezone, display/disambiguation data, and attribution needed by WX.19.
- Two same-name locations in different regions/timezones remain separately retrievable; provider IDs and names cannot become keys; duplicate local IDs fail without overwriting state.
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
- The current Room database schema version is 2 in the dirty planning worktree. If the baseline changes before activation, re-plan the migration step to the latest sequential version and verify every intervening migration; never edit an already shipped migration.
- Persist place attribution as descriptive metadata because WX.17 supplies it and WX.19 owns displaying it. It is not an identity or key.
- Room's transaction guarantee requires location, selection, and catalog tables to share the same database instance. Verify the existing database wiring before implementation; do not claim atomicity across separate databases.
- A schema/storage integration failure should be recorded with exact evidence. Do not solve it by changing the canonical catalog schema, provider contracts, or base toolchain within WX.18.

## Out of scope

Search and selector UI, main-screen access route, weather loading/refresh, active catalog selection or source switching, provider/geocoding changes, GPS or background location, weather payload redesign, UI/accessibility/device acceptance, official alerts, timeline/atmosphere changes, production release integration, and changes to deprecated oxygenWX files.
