# DFM.4.3 — Clean forecast view and diagnostic overlays
## Behavior board — product-owner approved

**Status:** Product-owner approved 2026-11-01 as shown; no additional constraints stated.
**Purpose:** Define the default forecast readout, the debug-only diagnostic overlay and preset boundary, and the route that keeps source/freshness details reachable after the clean view is applied.

### Approved reference boards

This objective-specific board derives from the owner-approved DFM.4 references:

- `.codex/concepts/DFM.4-debug-session-flow-board.md` — session entry/exit, default-off overlay, local display presets, and no mutation of LIVE/provider/cache/location state.
- `.codex/concepts/DFM.4-normal-debug-overlay-board.png` and editable SVG — clean default readout, debug menu, overlay placement, and source-detail boundary.
- `.codex/concepts/DFM.4-catalog-picker-board.png` and editable SVG — catalog work is later work; DFM.4.3 must not absorb authoring or replay flows.

The installed pre-slice reference is `.codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/pre-slice-main.png`. It records the current Pixel 3 XL main view before this slice.

### Behavior contract

**Clean main view — default in every source state**

- Preserve the full atmospheric scene, location action, refresh action, time tape, selected instant, and existing navigation/gesture ownership.
- Keep the approved summary visible: local date, selected time, temperature, current supplied event, daily high/low, precipitation chance, and wind meter.
- Remove the experimental title/status block, dataset control, inline source/freshness diagnostic line, and secondary sample/precipitation detail rows from the normal main composition as shown in the companion artwork.
- The selected-time reading remains a projection of the authoritative selected instant. Scene art, including the sun or moon, does not add forecast facts.
- LIVE, SIMULATED, and REPLAY source meaning remains truthful. A synthetic preview retains its DEMO DATA/synthetic identity.

**Debug session and independent overlay visibility**

- A new debug session opens the existing debug menu with diagnostic overlay visibility off.
- Selecting or configuring a preset changes the selected diagnostic groups. It does not activate the overlay or alter forecast data, source, location, or selected time. Overlay visibility is a separate explicit menu control.
- Toggling visibility draws or hides the selected groups over the same main forecast. The weather scene, readout, precipitation chance, wind meter, selected instant, and tape remain unchanged.
- Debug overlay groups are:
  1. catalog/source identity and truthful freshness/delivery state;
  2. selected instant, fact-sample time, and available point/interval bounds;
  3. missing-field summary, with unavailable values named as unavailable.
- Every overlay group is explicitly labeled diagnostic. It does not look like a measured forecast value and does not fill missing facts.

**Local display presets**

- Configure the supported overlay groups, give the configuration a local name, and save or update it as a display preset.
- A preset contains display-group choices only. It cannot change catalog values, forecast activation, source namespaces, provider/cache data, location selection, or time selection.
- Choosing a preset applies its groups to the debug session; overlay visibility remains independently off until enabled.
- Exiting the debug session hides the overlay. DFM.4.2 continues to own exit-before-ordinary-location/refresh behavior.

**Source, freshness, stale, offline, and error details**

- Keep these details truthful and reachable from the existing accessible “Browse forecast values” action on the time tape. Preserve the existing selected-time and hourly/daily browser behavior.
- The browser/detail content must expose the current source/product meaning, source identity, delivery/freshness/stale state, and applicable offline/refresh/load error. Do not rely on the hidden main status row as the only route.
- Browsing details does not silently change the selected instant; selecting a browser row continues to use the existing time-selection behavior.

### Flow

```text
NORMAL FORECAST (clean summary; diagnostics absent)
  ├─ #DEBUG in debug build → DEBUG MENU; overlay = off
  ├─ #DEBUG in release build → existing ordinary search behavior
  └─ time-tape “Browse forecast values” → existing forecast browser/details

DEBUG MENU (overlay visibility independent from selected preset)
  ├─ choose local preset → select groups for this session; overlay remains off
  ├─ configure groups → save/update local display preset; no forecast mutation
  ├─ toggle overlay on → same main view + labeled diagnostics in upper sky
  ├─ toggle overlay off → exact clean main view
  ├─ #PICKER → existing session-gated dispatch; catalog flow is outside DFM.4.3
  └─ exit or ordinary action → DFM.4.2 restoration boundary; overlay off

OVERLAY ON
  ├─ diagnostics follow the selected catalog and selected instant
  ├─ forecast facts, scene, core readout, and tape retain their meaning
  └─ hide overlay or end session → clean main view
```

### Scope and acceptance

Review the companion UI artwork for normal LIVE, debug menu/overlay-off, overlay-on, and preset configuration states. Confirm that:

- the normal view contains only the approved summary and retained actions;
- a fresh debug session has the overlay off;
- preset choice and visibility are independent;
- labels, support times, missingness, and source state are truthful;
- overlay content stays in reserved upper scene corners and leaves all forecast facts and the time tape readable;
- overlay/preset actions preserve selected instant, catalog activation, and gesture ownership;
- the existing accessible browser remains the reachable source/freshness/error detail route.

Catalog storage, authoring, replay capture, a new theme or scene design, provider/cache mutation, new weather semantics, release-wide acceptance, and broad accessibility/system matrices are outside this slice.

### Approval record

Product-owner approval: **2026-11-01, as shown; no additional constraints stated.** Approval covers the behavior contract, flow, scope, and acceptance relationship on this board. The behavior and artwork boards guide scope only; installed acceptance evidence remains separate.
