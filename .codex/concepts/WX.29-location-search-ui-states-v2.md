# WX.29 location-search UI state board — replacement candidate

**Status:** Draft for product-owner review; no approval is recorded.

**Artwork:** `WX.29-location-search-ui-states-v2.png` (editable `.svg`) uses the owner-approved `design_draft/location_search_ui_board.png` as its primary reference. The SVG embeds the supplied image. The first phone is the unmodified target. The other phones illustrate state treatment in the same city scene and horizontal rail without cards, boxed panels, or solid screen backgrounds.

## Placement and interaction decisions

- The search field stays in its supplied position. Search status appears above the rail. Saved locations remain on the rail during search loading, no matches, and failure, per the owner's 2026-11-07 direction.
- The rail remains the location navigation and selection method. Its tick marks, place abbreviations, readable names, and green current-selection accent remain visible where a saved snapshot is available.
- A saved-location snapshot can be loading, empty, or unavailable. These states keep search usable; the rail does not invent saved places when none are known.
- Removal confirmation is a full-screen scene state on the same background, per the owner's 2026-11-07 direction. Cancel and Remove are text actions; confirmation names the place and catalog-data consequence. The rail is visually present during confirmation; interaction is suspended until a choice is made.
- Attribution details appear in scene text near the rail for the selected result. Exact provider text is dynamic.
- All place names and the query in these samples are illustrative. The implementation must use provider results and the actual saved/current selection.

## Review with behavior board

Review this candidate together with `WX.29-location-search-behavior-board.md`. In particular, decide whether the status placement, attribution disclosure, and full-screen removal confirmation support the specified explicit selection and dismissal behavior. Neither board may be marked approved until this coupled review is complete.

The board is a visual direction, not installed acceptance evidence. Its small multiples show layout and placement; final device capture must establish legibility and interaction in the scoped states.
