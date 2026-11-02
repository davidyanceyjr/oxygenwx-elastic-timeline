# DFM.4 Debug session and catalog lifecycle — behavior board (review draft)

**Status:** owner-approved 2026-11-01, with the normal-view metric caveat recorded below.
**Purpose:** Make the command boundary, session lifetime, overlay behavior, and catalog preview path reviewable before the DFM.4 cycle plan is finalized.

**Visual logic and state diagram:** [DFM.4 session and location flow](DFM.4-session-location-flow.svg). The owner approved the diagram and its location-selection behavior amendment on 2026-11-01; the textual contracts below accompany it.

## Approved normal-screen content

The normal screen shows the date, selected time, temperature, current weather event, high/low, **precipitation chance**, and the **wind meter**. Precipitation chance and wind remain visible as compact main-view metrics; they are not debug overlays. The sun is illustrative scene artwork, not an additional weather fact or text readout. The location search remains the app input and debug entry point.

## State flow

```text
NORMAL FORECAST (default; overlay off)
  ├─ location field submits #DEBUG
  │    ├─ debuggable build → DEBUG SESSION ACTIVE → Debug Menu
  │    └─ release build → ordinary place-search text; no developer effect
  └─ location field submits #PICKER
       └─ either build → ordinary place-search text; no picker dispatch

DEBUG SESSION ACTIVE
  ├─ menu toggles diagnostic overlay on/off
  │    ├─ off → exact clean production presentation remains visible
  │    └─ on  → selected preset/groups appear in reserved sky corners;
  │             date/time/temp/event/high-low and tape remain unobstructed
  ├─ choose saved overlay preset → apply its groups for this session
  ├─ configure groups → save/update a named local overlay preset
  ├─ submit #PICKER → CATALOG LIBRARY
  │    ├─ choose a SIMULATED catalog → edit / preview / capture as REPLAY
  │    ├─ build → name + local-time period + typed weather events
  │    │          → save as SIMULATED → optional REPLAY snapshot
  │    └─ preview → explicit synthetic preview on existing main selected-time path
  ├─ ordinary location selection or refresh/retry
  │    └─ restore checkpoint without I/O → end debug; overlay off
  │         → perform the existing ordinary action
  └─ Exit debug → NORMAL FORECAST; overlay off; command dispatch disabled
```

## Catalog lifecycle contract

| State | Meaning | Mutability | Activation |
|---|---|---|---|
| **SIMULATED** | Named, local synthetic timeline built from supported typed fields | Editable in debug workbench | Only after explicit Preview |
| **REPLAY** | Named, fixed snapshot captured from a saved simulation | Read-only snapshot | Only after explicit Preview |
| **LIVE** | Existing provider catalog | Not editable here | Never written or replaced by DFM.4 |

Creating, saving, editing, or previewing a local catalog does not alter the selected place, provider state, LIVE cache, or saved locations. A local preview keeps synthetic provenance and uses the existing selected-time presentation path. Missing values remain unavailable, not zero; probability is separate from amount; unsupported weather stays absent.

## Ordinary location selection and refresh

**Owner-approved location-selection clarification, 2026-11-01:**

```text
LOCATION MENU (search input; persistent selected-location list below)
  ├─ select a search result → save if needed → select and load it
  │                            → close menu → main forecast
  ├─ select a persistent-list item → select and load it
  │                                  → close menu → main forecast
  └─ remove a list item → existing removal confirmation and removal flow

Reopen location menu → a new interaction; selected locations remain in the
persistent list until removed. A failed selection remains visible as a failure.
Pending debug entry is canceled before selection; a late capture cannot open
debug or replace the selected location. Active debug restores and ends first.
```

This clarification follows the owner's direction and approval in the current planning conversation. It changes the prior assumption that the ordinary selection flow remains unchanged.

Proposed UI artwork for this flow: [DFM.4-location-selection-artwork.svg](DFM.4-location-selection-artwork.svg), pending owner approval.

Owner-approved amendment, 2026-11-01: an ordinary location selection or refresh/retry ends debug first. Restore the pre-session checkpoint without I/O, turn overlays off, and discard the checkpoint before persisting a new selection or starting the existing refresh/retry path. The requested ordinary action then proceeds with its existing persistence, activation, and failure behavior. If it fails, debug remains off. A late preview completion or exit callback cannot restore the old checkpoint over that action. Cancel a pending debug-entry action when either ordinary action begins; leave startup itself under its existing coordinator ownership. Query editing/search and opening the selector do not themselves end debug.

This amendment preserves the local-only debug boundary: any normal location persistence or provider/cache activity belongs to the explicitly requested ordinary action after debug has ended. It adds no disabled controls or new screen layout.

## Overlay contract

- Debug entry and overlay visibility are separate. A new debug session opens with overlay off.
- Overlay groups are chosen from supported diagnostics (catalog/source identity and freshness state; selected sample/source bounds; missing-field state). Each label identifies diagnostic data and is not rendered as a forecast fact.
- Overlay placement uses the upper scene corners only; the date, selected time, temperature, event label, high/low, and time tape stay unobscured.
- Presets save only overlay visibility/group choices locally. They do not alter weather values or catalog activation.
- Source, freshness, stale, offline, and error information remains truthful and reachable through the separate forecast details surface even when the overlay is off.

## Security and lifecycle boundaries

- `#DEBUG` and `#PICKER` are dispatched only in debuggable builds; commands remain absent from user-facing manuals.
- `#PICKER` dispatch requires an active debug session. Exiting ends the session and restores normal presentation.
- No live network calls, location selection, provider/cache mutation, official-alert state, new forecast semantics, or background simulation are introduced.

## Approval record

Product owner approved this board set on 2026-11-01. Approval constraints: normal view keeps precipitation chance and wind meter in addition to the core date/time/temperature/event/high-low readout; the sun is illustrative artwork; developer diagnostics stay off by default; debug entry and overlay visibility remain separate; presets are local display configuration; picker commands require an active debug session; SIMULATED and REPLAY remain distinct; local catalog preview is explicit and synthetic; LIVE/provider/cache/location state is not mutated.

The owner subsequently accepted the recommended exit-before-ordinary-action rule on 2026-11-01 by requesting that the plan align with that recommendation. The transition and contract above record that amendment; the approved UI artwork remains applicable.

## Board relationships

- UI artwork: `DFM.4-normal-debug-overlay-board.svg` covers clean normal mode, the debug menu, and overlay-on main-screen state.
- UI artwork: `DFM.4-catalog-picker-board.svg` covers saved catalogs and the period-first builder/preview flow.
- Existing approved board: `DFM.4-debug-workbench.png` remains the approved command/session baseline. The approved boards add detail to the command/session baseline and govern the clean readout, retained main-view metrics, overlay presets, and local catalog builder.
