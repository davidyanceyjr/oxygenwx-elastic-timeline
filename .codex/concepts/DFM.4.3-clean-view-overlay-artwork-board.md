# DFM.4.3 — Clean forecast view and diagnostic overlays
## UI artwork board notes — product-owner approved

**Status:** Product-owner approved 2026-11-01 as shown; no additional constraints stated.
**Artwork:** [`DFM.4.3-clean-view-overlay-artwork-board.png`](DFM.4.3-clean-view-overlay-artwork-board.png), editable [`SVG`](DFM.4.3-clean-view-overlay-artwork-board.svg).
**Before-state:** [Pixel 3 XL pre-slice capture](../test-artifacts/087-dfm43-clean-view-diagnostic-overlays/pre-slice-main.png).

### Reference set

This board narrows the three approved DFM.4 references to DFM.4.3's main-screen and diagnostic states:

- [`DFM.4-debug-session-flow-board.md`](DFM.4-debug-session-flow-board.md) — command/session lifecycle, overlay default-off behavior, local diagnostic presets, and no forecast/source mutation.
- [`DFM.4-normal-debug-overlay-board.png`](DFM.4-normal-debug-overlay-board.png) — clean main view, debug menu, overlay placement, compact precipitation/wind facts, and truthful source details.
- [`DFM.4-catalog-picker-board.png`](DFM.4-catalog-picker-board.png) — catalog picker/editor remains a boundary reference; those flows are not redrawn or expanded here.

### States shown

1. **Clean LIVE main, default:** date and selected time, temperature and current condition, high/low, precipitation chance, wind meter, location action, refresh action, scene, and responsive time tape. Experimental title/status, dataset control, inline sample/source diagnostics, and secondary sample/amount rows are removed from this main composition.
2. **Debug menu, overlay off:** existing DFM.4.2 menu with an independent overlay toggle, saved local display preset selection, and configure-groups entry. The existing catalog-picker and Exit actions remain context only. A newly opened debug session starts with the overlay hidden.
3. **Overlay on:** the same selected time, forecast summary, scene, chance/wind metrics, and tape as the clean main. Source/freshness, selected sample/bounds, and missing-field diagnostics occupy the upper sky and carry explicit diagnostic labels.
4. **Preset configuration:** group selection is separate from visibility. A local preset saves only the chosen display groups. Selecting a preset does not enable the overlay or modify forecast data.

### Visual constraints

- The scene and timeline in the target phones are schematic placement studies. Implementation retains the existing first-city scene art, its weather rendering, layering, and timeline behavior.
- Keep the forecast summary and time tape readable and unobscured. Diagnostic cards stay in reserved upper-sky space and remain visually distinct from forecast values.
- The example values in the clean and overlay phones come from the pre-slice LIVE capture. They anchor comparison only; the board is not a second forecast or installed after-state.
- Source, product, freshness/stale, offline, and error information remains reachable through the existing time-tape “Browse forecast values” route. If a detail is currently shown only in the removed main status/error row, expose it in that existing browser rather than adding new navigation.
- Preserve the location and refresh actions. The sun/moon remains scene art, not an added weather fact.

### Approval record

Product-owner approval: **2026-11-01, as shown; no additional constraints stated.** Approval covers the four target states and the visual constraints above. This board is a design input and does not authorize implementation or claim installed visual acceptance.
