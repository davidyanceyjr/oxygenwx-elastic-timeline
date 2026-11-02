# DFM.4.4 — Local catalog storage and replay snapshots

**Status:** Owner-approved 2026-11-02, as written.
**Slice:** DFM.4.4 — Local synthetic catalog storage and replay snapshots.
**Purpose:** Make the debug-local SIMULATED and REPLAY persistence lifecycle, canonical catalog boundary, and explicit-preview safety gate reviewable before finalizing the cycle plan.
**UI artwork:** Not applicable. This slice builds a nonvisual storage and activation foundation independently of editor UI. DFM.4.5 owns the library/editor presentation and installed UI acceptance.

**Visual board:** [DFM.4.4-local-catalog-storage-board.svg](DFM.4.4-local-catalog-storage-board.svg) · [PNG preview](DFM.4.4-local-catalog-storage-board.png)

## Behavior flow

```text
EDITABLE SIMULATED INPUT
  name + location identity + IANA zone + authored local-time period
  + supported typed event values
        │ validate identity, period, timezone and supported inputs
        ├─ invalid ──> report failure; no write and no activation
        ▼
MATERIALIZE CANONICAL SIMULATED RECORDING
  existing full D-30..D+13 local-date horizon
  authored events applied only within the selected period
  established deterministic synthetic baseline retained outside it
  gaps/overlaps resolved as real local-hour instants under existing rules
  unsupported fields remain absent; probability remains distinct from amount
        │ validate complete canonical recording and metadata consistency
        ├─ invalid ──> report failure; no write and no activation
        ▼
SAVE SIMULATED (editable)
  debug-local SIMULATED namespace; identity distinct from fixtures
  save succeeds → reopen returns equivalent canonical recording + metadata
  save/reopen failure → explicit failure; no false success or fallback data
        │ explicit “capture snapshot” operation only
        ▼
CAPTURE REPLAY (immutable)
  snapshot canonical recording + captured SIMULATED origin/provenance
  assign distinct REPLAY identity and save in REPLAY namespace
  later reopen returns the captured values, not the current SIMULATED values
  edits target SIMULATED only; REPLAY cannot be overwritten through this seam

SAVED SIMULATED OR REPLAY
        │ explicit Preview request
        ▼
REVALIDATE SAVED RECORDING AT ACTIVATION BOUNDARY
  identity, namespace/origin, location, canonical horizon, codec result,
  metadata and validated content must still agree with the selected record
        ├─ missing / invalid / changed-after-validation / mismatched
        │      → reject; keep current active catalog and selected time unchanged
        │        no fallback generation, replacement write, or success claim
        ▼
ACTIVATE THROUGH EXISTING COORDINATOR SELECTION PATH
  only saved valid synthetic data can be previewed
  selected-time projection remains authoritative
  LIVE storage, provider/cache, selected location and saved locations untouched

EXIT DEBUG SESSION
  existing coordinator checkpoint restores exact prior catalog + selected time
  without I/O or storage writes (covered by DFM.4.1/4.2 contracts)
```

## Contract and review boundaries

- The authored period is an event-editing range, not a shorter catalog window. Every saved SIMULATED and REPLAY recording retains the existing canonical 44-local-date `D-30..D+13` horizon and location timezone identity.
- SIMULATED remains editable and retains the established synthetic baseline/support semantics outside the authored period. REPLAY is a fixed snapshot with a distinct identity and captured SIMULATED origin.
- Reuse the current canonical catalog, codec, `CatalogWindow`, storage namespaces, and coordinator-owned activation/selection path. Do not alter shared schema, LIVE records, provider/cache state, location persistence, or selection ownership.
- Preserve typed support and meaning: missing is not zero, precipitation probability is distinct from amount, unsupported conditions stay unavailable, and valid local-hour instants account for timezone DST gaps/overlaps using established canonical rules.
- Persistence and validation are not preview. Only an explicit preview request may activate a saved SIMULATED or REPLAY recording.
- Any validation, serialization, or storage failure must be observable as failure and leave prior valid storage and active selection intact. No generated fallback may masquerade as a saved record.

## Slice acceptance mapping

| Board path | Plan obligation | Observable evidence |
|---|---|---|
| Input → materialization | Supported typed event periods produce a canonical full-horizon SIMULATED record | Focused mappings for authored bounds, baseline outside bounds, missingness, support, timezone and DST cases |
| SIMULATED save/reopen | Editable records round-trip with consistent identity and metadata | Focused codec/storage round-trip and metadata consistency observations |
| REPLAY capture/reopen | Captured values and SIMULATED origin persist under a distinct immutable REPLAY identity | Snapshot equality after source SIMULATED edits; attempted replay mutation is rejected or impossible through the approved seam |
| Explicit preview gate | Only the revalidated saved record crosses into the existing coordinator activation path | Valid preview activates synthetic provenance; invalid, missing, mismatched, or changed-after-validation input does not change active catalog or selected time |
| Failure and isolation | Errors do not claim success, write substitutes, or touch LIVE/provider/location state | Focused failure-path observations and namespace/state checks |

## Explicit exclusions

No catalog library/editor UI, location search changes, new forecast semantics, shared-schema or codec compatibility changes, LIVE refresh, provider/cache/location mutation, automatic activation on save, background simulation, external sync/sharing, or broad device/accessibility matrix. Those remain governed by the parent DFM.4 boundary and later slices as applicable.

## Approval record

Product owner approved this board as written on 2026-11-02. Approval covers the behavior flow and constraints above. UI artwork is not applicable to this nonvisual slice. This board is design input, not implementation evidence or authorization to activate the plan.
