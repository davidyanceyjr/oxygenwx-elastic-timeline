# DFM.4.5 — Authored event ranges behavior board

**Status:** Product-owner approved 2026-11-02 as shown, with no additional constraints. This board is design input, not implementation authorization or acceptance evidence.
**Purpose:** Define how a debug-authored local-time envelope contains independently timed typed weather events, and how those events materialize into the existing canonical synthetic catalog without changing its shared schema.
**Visual board:** `.codex/concepts/DFM.4.5-authored-event-ranges-contract.svg`; PNG preview: `.codex/concepts/DFM.4.5-authored-event-ranges-contract.png`.
**UI artwork reference:** Owner-approved `.codex/concepts/DFM.4.5-catalog-library-editor-artwork-board.svg` and `.png`; its rain bar is an event range inside a wider authored envelope. The library/editor workflow remains governed by `.codex/concepts/DFM.4.5-catalog-library-behavior-board.md`.

## Behavior flow

```text
AUTHOR SIMULATED CATALOG
  fixed location + IANA timezone + authored envelope [start, end)
        │ envelope must fit D−30…D+13 and include real local-hour instants
        ▼
ADD TYPED EVENTS
  each event has [eventStart, eventEnd) in the location's local time
  plus one complete supported typed value set
        │ validate envelope containment, hour boundaries, and non-overlap
        ├─ invalid / overlapping / no real hourly instant
        │      → show validation; keep the draft editable; do not save or preview
        ▼
MATERIALIZE FULL CANONICAL SIMULATED CATALOG
  covered real hourly instants use that event's complete typed values
  uncovered instants retain the deterministic SIMULATED baseline
  DST overlap: include both real instants; DST gap: no nonexistent fact
        │ preserve missingness, support, provenance, and 44-date horizon
        ▼
SAVE IMMUTABLE DATASET ID + DEBUG SIDECAR
  metadata stores envelope, event ranges/values, version, and recording hash
  old period-wide sidecars reopen as one event covering their old period
  save failure → no false success and no activation
        │ explicit Capture Replay from a saved SIMULATED item only
        ▼
IMMUTABLE REPLAY SNAPSHOT
  copied canonical facts and captured SIMULATED origin; Preview only
        │ explicit Preview of saved SIMULATED or REPLAY
        ▼
EXISTING COORDINATOR VALIDATION / ACTIVATION PATH
  revalidate saved recording and sidecar; failure preserves active catalog/time
```

## Proposed behavior contract

- The authored envelope is a nonempty half-open local-time interval `[startLocal, endLocalExclusive)`, fixed to the catalog location and IANA timezone. It remains only an editing boundary; saved catalogs retain the full canonical D−30…D+13 horizon.
- Each event is a half-open local-time interval `[eventStartLocal, eventEndLocalExclusive)` contained in the envelope. Boundaries align to local hours, and every event covers at least one real canonical hourly instant. The editor communicates that each end is exclusive.
- Events are ordered and may be adjacent, but may not overlap. On overlap, keep the draft editable, explain the conflict, and do not save or preview. This carries forward the DFM.4 owner decision in `.codex/history/plans/084-dfm4-debug-workbench.md`.
- At each real catalog hourly instant covered by an event, materialize that event's complete supported typed value set. Unsupported or unset fields remain unavailable at those instants; probability remains distinct from amount. At instants not covered by an event, including uncovered parts of the envelope, retain the deterministic SIMULATED baseline.
- Use local wall-time bounds against real canonical instants. Both real instants in a fall-back repeated hour are covered when their local time is inside the half-open event range. A spring-forward nonexistent hour produces no fact. Reject an event or envelope that covers no real hourly instant.
- Save event definitions only in debug-local sidecar metadata bound to the canonical recording hash. Version the sidecar and continue to reopen existing DFM.4.4 period-wide SIMULATED/REPLAY sidecars as one event spanning their prior authored period. Reopen must establish that the stored event definitions materialize to the saved canonical recording.
- Every saved SIMULATED dataset identity remains immutable. An edit writes a new identity; only after its recording and sidecar succeed may the library update the logical item's current identity. Cancel or failed save leaves the previous logical item intact. REPLAY remains an immutable copy of saved canonical facts and captured SIMULATED origin.
- Save and Capture Replay never activate. Explicit Preview reuses the DFM.4.4 coordinator path and revalidates the saved recording plus event metadata at the activation boundary. Missing, invalid, or mismatched data fails closed without changing the active catalog or selected instant.
- Do not change the canonical catalog schema, codec, Room schema, LIVE namespace, provider/cache/location behavior, or coordinator activation/selection ownership.

## Acceptance relationship

This behavior board defines the storage/materialization semantics required by the DFM.4.5 period-first editor. The existing approved UI artwork remains the visual state guide for the library, event timeline, previews, and failure state. Focused storage evidence and installed UI evidence remain separate acceptance obligations; this board is not implementation proof.

## Approval record

**Product-owner approval:** Approved 2026-11-02 as shown, with no additional constraints. Approval covers the half-open hourly event ranges, overlap rejection, deterministic baseline in uncovered intervals, DST behavior, versioned debug-sidecar compatibility, immutable revisions, and unchanged shared schema and preview ownership.
