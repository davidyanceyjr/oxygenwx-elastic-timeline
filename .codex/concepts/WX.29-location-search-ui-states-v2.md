# WX.29 location-search UI state board — approved state extensions

**Status:** Approved by the product owner on 2026-11-07 together with `.codex/concepts/WX.29-location-search-behavior-board.md`.

**Artwork:** `WX.29-location-search-ui-states-v2.png` (editable `.svg`) uses the owner-approved `design_draft/location_search_ui_board.png` as its primary reference. The SVG embeds the supplied image. The first phone is the unmodified target. The other phones illustrate state treatment in the same city scene and horizontal rail without cards, boxed panels, or solid screen backgrounds.

## Placement and interaction decisions

- The search field stays in its supplied position. Search status appears above the rail. Saved locations remain on the rail during search loading, no matches, and failure, per the owner's 2026-11-07 direction.
- The rail remains the location navigation and selection method. Its tick marks, place abbreviations, readable names, and green current-selection accent remain visible where a saved snapshot is available.
- A saved-location snapshot can be loading, empty, or unavailable. These states keep search usable; the rail does not invent saved places when none are known.
- Removal confirmation is a full-screen scene state on the same background, per the owner's 2026-11-07 direction. Cancel and Remove are text actions; confirmation keeps the city name in its title, shows the saved place's full `displayLabel` directly beneath it to distinguish duplicate city names, and states the catalog-data consequence. The rail is visually present during confirmation; interaction is suspended until a choice is made.
- Attribution details appear in scene text near the rail for the selected result. Exact provider text is dynamic.
- All place names and the query in these samples are illustrative. The implementation must use provider results and the actual saved/current selection.

## Coupled review and approval

The product owner reviewed and approved this state board together with `WX.29-location-search-behavior-board.md` on 2026-11-07. On 2026-11-07, the owner also approved the duplicate-name treatment: keep the city-name confirmation title and show the saved place's full `displayLabel` on the line below. The approved treatment keeps search status above the shared rail, leaves known saved locations available during search states, preserves provider attribution near the rail, and presents removal confirmation in the same city scene. The behavior board governs explicit submission, selection, removal, and Back semantics.

The board is a visual direction, not installed acceptance evidence. Its small multiples show layout and placement; final device capture must establish legibility and interaction in the scoped states.
