# Cycle plan — 056-wx15-room-catalog-storage

State: COMPLETE
Cycle ID: 056-wx15-room-catalog-storage
Roadmap: WX.15
Objective: Persist versioned canonical datasets per location and storage origin in Room, with one validated versioned JSON recording payload per dataset; define retention and explicit schema migration behavior.

## Owner decision and concept-art gate

- Owner-approved storage direction: AndroidX Room over SQLite, one row per complete dataset, with the existing `RecordedCatalogCodec` JSON recording as the opaque payload. Index identity and retention metadata; do not normalize weather facts into SQL columns in this slice.
- The annotated storage schema board at `.codex/concepts/WX.15-storage.png` (editable source: `.codex/concepts/WX.15-storage.svg`) was approved by the product owner on 2026-10-28 as shown. No additional constraints were supplied. It shows the row key, indexed metadata, payload boundary, origin namespaces, and write/read/delete flow. The board is design input, not acceptance evidence.
- The concept-art approval gate was met before activation; the cycle was activated on 2026-10-28.
- Owner resolution during execution: add `capturedOrigin` to the row envelope and compare it with the decoded payload origin. Detailed product provenance stays in the versioned payload. This resolves the read-verification ambiguity without duplicating weather or product fields in SQL.
- Owner resolution during execution: use Android-compatible `ZoneId.getAvailableZoneIds()` for the existing IANA-zone validator in `CatalogIdentity.kt`. Android 12 lacks `ZoneRulesProvider.getAvailableZoneIds()`; the Pixel 3 XL instrumentation run exposed this before Room assertions. This compatibility correction is necessary for the planned installed storage verification.
- Owner resolution during execution: align `kotlinx-serialization-core` with Room migration's `kotlinx-serialization-json` 1.8.1 after the Pixel test found a 1.7.3/1.8.1 runtime split and `AbstractMethodError` inside `MigrationTestHelper`. Keep the dependency change minimal and verify on device.
- Owner resolution during execution: add indexed `asOfEpochSecond` and `asOfNano` columns for correct history ordering; verify both against the decoded timestamp while retaining the readable `asOf` identity column. `Instant.toString()` has variable fractional precision and cannot be ordered reliably as SQL text.
- Owner resolution during execution: allowlist only `androidx.room.MultiInstanceInvalidationService` in the merged-manifest component guard. Room adds this service during manifest merge; the installed contract check rejected it even though no app-defined service was introduced.

## Build and database-version decisions

- Repository baseline inspected for this plan: AGP 9.4.0, Gradle wrapper 9.7.0, and Kotlin Compose plugin 2.4.20. The app is Kotlin-based and has no existing Room, KSP/KAPT, Room schema snapshots, or Room migration tests.
- Use KSP for Room's Kotlin compiler processing and the Room Gradle plugin to configure schema export. Keep the Room runtime, compiler, Gradle plugin, and testing artifacts on one stable Room release; check in exported schema snapshots under `app/schemas/`. Select a KSP plugin release verified against this repository's Kotlin/AGP versions. Do not upgrade or downgrade the base toolchain as part of WX.15.
- Before writing the storage adapter, compile a minimal Room database with one entity and DAO, confirm generated code and exported schema, and run the debug build. If no stable KSP release can build with the current toolchain, stop at this compatibility check, preserve the logs in cycle evidence, and request an owner decision before changing processor strategy or base toolchain.
- Start the new Room database at schema version 1 with schema export enabled. There is no prior Room database schema in this repository, so do not invent a production migration for the initial schema. Do not configure destructive migration fallback. Test initial database creation and explicit failure for an unsupported/missing Room migration path without deleting existing database contents. If a migration is added during this slice, test every declared path and preservation of stored rows.
- Keep Room database version checks separate from `RecordedCatalogCodec` payload-version checks. Use Android instrumentation tests with Room's migration test support for actual database/schema behavior; keep codec and storage-policy tests host-side where they do not require SQLite. The app has no Room KMP/JVM test setup, and `python scripts/dev.py test` runs unit tests only.

## Production boundary

May change:

- Root `build.gradle.kts`, `app/build.gradle.kts`, and the minimum Gradle configuration needed to add Room, KSP, the Room Gradle plugin, and schema export without changing AGP/Kotlin/Gradle versions.
- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/` for the Room database/entity/DAO, storage adapter, and narrowly scoped codec integration.
- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/CatalogIdentity.kt` for the owner-approved Android timezone API compatibility fix.
- Focused codec/policy tests under `app/src/test/` and Room database/migration tests under `app/src/androidTest/`.
- Exported Room schema snapshots under `app/schemas/`.
- `.codex/test-artifacts/056-wx15-room-catalog-storage/` and this plan.
- `docs/ROADMAP.md` only to record the selected Room-plus-payload design and correct the stale NEXT summary.
- `scripts/dev.py` solely to allowlist Room's merged `MultiInstanceInvalidationService` while preserving the existing permission, activity, and component guards.

Do not change UI, active catalog selection, provider refresh, geocoding, source implementations, or the deprecated oxygenWX checkout.

## Storage contract

- Store each complete validated catalog as one versioned JSON payload encoded by `RecordedCatalogCodec`; validate the catalog and identity before the database write.
- Store queryable envelope metadata in the row: stable local location ID, storage namespace (`LIVE`, `SIMULATED`, or `REPLAY`), dataset ID, `asOf`, and payload format version. Use a unique key on location ID + storage namespace + dataset ID, plus an index suitable for location/namespace history ordered by `asOf`.
- Keep storage namespace separate from payload provenance. A replay row uses the `REPLAY` namespace while its recording payload preserves the original LIVE or SIMULATED provenance; loading it through the replay contract yields REPLAY delivery provenance.
- Read by the complete key and verify that decoded location ID, dataset ID, `asOf`, and captured provenance agree with the row metadata. Reject corrupt, unsupported, or mismatched payloads explicitly; never return a partially decoded dataset.
- Replace one dataset row transactionally only after encoding and validation succeeds. A failed insert/update leaves the prior row readable. Deleting one key or location must not affect other locations, datasets, or origin namespaces.
- Retention policy for this cycle: retain saved datasets until an explicit delete/prune operation; do not silently evict on write. Document that automatic bounded retention requires a later owner decision before production refresh can accumulate indefinite history.
- Version Room's database schema with explicit migrations and no destructive fallback. Keep database schema versioning distinct from the recording payload's v1/v2 schema; use existing supported codec migration behavior and fail explicitly on unsupported payload versions.

## Functional invariants

- LIVE, SIMULATED, and REPLAY storage namespaces cannot overwrite one another, including when location or dataset IDs coincide.
- Preserve canonical units, explicit null/missing facts, daily summaries/support, product segments, provenance, source metadata, nanosecond instants, IANA timezone, and DST behavior supported by the recording codec.
- All storage is local app-private data. No network, UI, source refresh, or active-source publication is added.
- The existing source and presentation boundaries remain intact; no provider DTO or Room row type reaches Compose.

## Steps

- [x] Confirm the approved storage board is present, its owner approval/constraints are recorded above, and this plan remains within WX.15.
- [x] Inspect existing codec versions and catalog identity rules; record the row schema, query/index contract, Room/KSP versions, exported initial schema, and migration policy in implementation notes/evidence.
- [x] Complete the minimal KSP/Room code-generation and schema-export build check described above before implementing the storage adapter. Record the exact dependency/plugin versions and build result.
- [x] Add the Room dependency/compiler setup and a storage adapter that stores one validated recording payload per dataset row; commit the exported Room schema snapshot.
- [x] Add host-side tests for codec/policy behavior and live/simulated/replay namespace isolation, same-key replacement policy, full 44-date round trips, null versus zero, provenance, daily/product-segment data, nanosecond/DST timestamps, and corrupt/unsupported/mismatched payload rejection.
- [x] Add Android instrumentation tests for Room-backed transactional replacement and prior-row preservation after write failure, namespace/key isolation, explicit deletion isolation, database creation, and unsupported/missing migration-path failure without destructive data loss. Use Room migration test support and exported schema snapshots; if an actual migration is introduced, test every declared path and retained row data.
- [x] Run the planned focused verification; retain exact command output/results and any Android/host limitations in the evidence directory.
- [x] Inspect `git diff --check` and the final diff; update WX.15 to DONE only if every exit condition is met, then close the cycle with exact results.

## Acceptance/evidence

Planned verification (not run during planning):

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.catalog.*'` for focused host-side codec/policy tests; do not require a full app-wide test run unless a focused check exposes a relevant integration failure.
- `./gradlew :app:connectedDebugAndroidTest` for focused Room database and migration instrumentation tests on an available Android device/emulator; record if unavailable rather than treating host unit tests as equivalent evidence.
- `python scripts/dev.py build` for Room compiler/build integration and schema generation.
- `git diff --check`

Acceptance requires all WX.15 exit cases to pass: round-trip of 44-day LIVE/SIMULATED/REPLAY datasets including missing facts, per-record provenance and DST timestamps; atomic per-row replacement with old value retained after failure; explicit database/payload version failure or migration; isolated key/location deletion; and proof that simulation cannot overwrite live storage.

Target records:

- Plan: `.codex/plans/056-wx15-room-catalog-storage.md`
- Evidence: `.codex/test-artifacts/056-wx15-room-catalog-storage/`
- Closeout: `.codex/history/056-wx15-room-catalog-storage.md`
- Archived plan after closure: `.codex/history/plans/056-wx15-room-catalog-storage.md`

Execution evidence and exact results are recorded in `.codex/test-artifacts/056-wx15-room-catalog-storage/README.md`.

## Risks / assumptions

- The existing codec is the canonical payload format; persistence must not fork or duplicate its weather schema.
- Room migration and payload migration are separate concerns and need separate version checks.
- A replay payload records its captured source origin; the row namespace records that it is stored for replay. Keep these fields distinct.
- Retaining all catalogs until explicit deletion can grow without bound once recurring refresh is added. This is an explicit conservative policy for WX.15, not approval for indefinite production retention; a bounded retention decision is required before production refresh accumulates history.
- Room dependency/compiler setup must match the repository's Android Gradle Plugin/Kotlin toolchain and should be kept to the minimum needed for this table.

## Out of scope

- Automatic eviction policy, cache freshness/refresh and partial recovery (WX.16).
- Persisted saved-location application state (WX.18).
- Active catalog activation, source switching, async request coordination, and refresh wiring (WX.20).
- Provider requests, geocoding, location UI, weather-fact SQL queries, normalization into per-hour/per-field tables, debug controls, and installed visual acceptance.
