# DFM.4.5 — Catalog library and period-first editor
## Behavior board — owner approved

**Status:** Product-owner approved 2026-11-02 as shown; no additional constraints stated. This board is a scope/design input, not implementation authorization or acceptance evidence.
**Purpose:** Make the saved synthetic-catalog workflow independently reviewable: locate saved items, author SIMULATED events inside a chosen local-time period, preserve immutable REPLAY snapshots, and cross into the forecast only through explicit preview of a saved record.
**Visual behavior flow draft:** [DFM.4.5-catalog-library-behavior-board.svg](DFM.4.5-catalog-library-behavior-board.svg) · [PNG preview](DFM.4.5-catalog-library-behavior-board.png). This is a visual companion to the contract below.
**UI artwork:** [DFM.4.5-catalog-library-editor-artwork-board.svg](DFM.4.5-catalog-library-editor-artwork-board.svg) · [PNG preview](DFM.4.5-catalog-library-editor-artwork-board.png)

## Approved references and handoff

- The owner-approved parent artwork `.codex/concepts/DFM.4-catalog-picker-board.svg` supplies the catalog-library and period-first editor visual baseline. This objective-specific draft refines its editor-to-preview path to match the completed DFM.4.4 saved-record validation boundary.
- The owner-approved behavior contract `.codex/concepts/DFM.4-debug-session-flow-board.md` supplies debug-session gating, ordinary-action exit, synthetic provenance, and isolation constraints.
- The owner-approved DFM.4.4 contract `.codex/concepts/DFM.4.4-local-catalog-storage-board.md` supplies SIMULATED materialization, REPLAY immutability, full-horizon behavior, validation, and fail-closed activation.
- The DFM.4.5 cycle plan remains `PLANNED — UNFINALIZED DRAFT` pending final plan review; this behavior board's approval satisfies its objective-specific behavior-board gate.

## Behavior flow

```text
NORMAL FORECAST
  ├─ submit #PICKER outside an active debug session
  │    └─ ordinary place search; no catalog UI dispatch
  └─ active debug session submits #PICKER
       └─ CATALOG LIBRARY
            ├─ Build new → EDITOR · new SIMULATED draft
            │    1. Name the catalog and review the current location/zone identity
            │    2. Choose the authored start/end period in that location's local time
            │    3. Add or edit supported typed weather events within that period
            │    4. Save SIMULATED → persist as editable; do not activate
            │    5. Return to the library; the saved row now has explicit Preview
            ├─ SIMULATED row
            │    ├─ Edit → editor draft → Save updates that SIMULATED record only
            │    ├─ Preview → revalidate saved record → explicit synthetic activation
            │    └─ Capture REPLAY → immutable snapshot with captured SIMULATED origin
            └─ REPLAY row
                 └─ Preview → revalidate snapshot → explicit synthetic activation
```

### State and transition contract

| State/action | Proposed behavior | Must remain true |
|---|---|---|
| `#PICKER` outside active debug | Continue ordinary place search | No library or developer UI dispatch |
| `#PICKER` inside active debug | Open the local Catalog Library | Debug-only session boundary from DFM.4.2 remains in force |
| Build or edit SIMULATED | Choose the authored local-time period before placing supported events | Location identity/timezone are displayed and fixed for the record; period is not a replacement for the full catalog window |
| Save SIMULATED | Persist the full canonical SIMULATED catalog; create/update a local editable item | Save never activates, changes LIVE/provider/cache/location state, or changes selected time |
| Cancel/close editor | Discard only the unsaved editor draft and return to the library | Existing saved SIMULATED content remains unchanged; no activation |
| Capture REPLAY | Snapshot a saved SIMULATED catalog into a distinct REPLAY identity | REPLAY preserves captured facts and SIMULATED origin; it cannot be edited or overwritten |
| Preview saved SIMULATED/REPLAY | Validate the saved record again, then use the existing coordinator selection path | Preview is a separate explicit action; provenance is synthetic and discoverable |
| Invalid, missing, changed, or mismatched saved record | Show an actionable failure and remain in the current workflow | Current active catalog and selected time remain unchanged; do not synthesize fallback content |
| Exit debug session / ordinary location or refresh action | Keep the DFM.4.1/4.2 checkpoint and restore ordering | Restore the exact prior catalog and selected instant without I/O before ending debug or performing the ordinary action |

## Catalog and event semantics

- SIMULATED is editable local generated data. REPLAY is an immutable snapshot of saved SIMULATED values and origin. Both are synthetic; neither is LIVE.
- The authored period bounds where edited events apply. Every saved catalog retains the canonical D−30…D+13 local-date window, selected location identity, and IANA timezone; the established deterministic baseline remains outside the authored period.
- Use only supported typed fields. Missing remains unavailable, not zero; probability remains separate from amount; unsupported conditions remain absent. Preserve the established valid local-hour and DST rules.
- A saved item is the only input to Preview. A partially edited buffer is not previewable. Saving and capturing a snapshot do not activate either record.
- Preview uses the established selected-time path. Debug-session exit restores the exact pre-session catalog and selected instant.
- No provider request, LIVE write, cache mutation, location edit, saved-place mutation, background generation, new weather semantics, or scene redesign is added.

## UI artwork state map

1. **Catalog Library:** Separate editable SIMULATED records from read-only REPLAY snapshots. SIMULATED rows expose Edit, Preview, and Capture Replay; REPLAY rows expose Preview only. The local-only synthetic boundary is visible.
2. **Period-first editor:** Name and fixed location identity are followed by local start/end period, then event placement and supported typed values. Save is distinct from Preview. The artwork proposes returning to the library after a successful save, where explicit Preview is available for the persisted row.
3. **Synthetic main-screen preview:** Reuse the approved clean forecast composition and time tape. Keep the existing DEMO DATA treatment. SIMULATED and REPLAY use the same forecast renderer; the accessible source/details path identifies the selected synthetic record.
4. **Fail-closed alternate state:** A missing/invalid/changed saved record produces a clear message, leaves the active forecast and selected time unchanged, and returns to the catalog workflow.

## Owner-approved decisions

- **Save → return to library → explicit Preview** is the workflow. The parent artwork's editor Preview affordance is refined for DFM.4.5: activation is available only for an already saved and revalidated record.
- **Cancel** discards the current unsaved editor draft and returns to the library. If editing, the previously saved SIMULATED record remains unchanged.
- The authored period is one contiguous start/end range in the catalog location's local time; the location identity/timezone stay fixed for that catalog.
- Use the illustrated fail-closed message and return-to-catalog action when a saved record cannot be previewed. The current active catalog and selected time remain unchanged.

## Acceptance relationship

The board covers the DFM.4.5 library, editor, SIMULATED preview, REPLAY preview, save/reopen/edit/capture paths, and failure boundary. It does not replace focused tests, installed Pixel 3 XL screenshots/interactions, or DFM.4.6 release/integration acceptance. Only directly affected controls and interactions belong to DFM.4.5; broad accessibility/system matrices remain out of scope.

## Approval record

**Owner approval:** Product owner approved this behavior board as shown on 2026-11-02, with no additional constraints.
