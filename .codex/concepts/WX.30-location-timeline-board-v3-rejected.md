# WX.30 — Same bottom controller with location data: behavior and UI board

**Status:** Rejected by the product owner on 2026-11-08; retained as review history.

**Board:** Rejected combined behavior/UI package: `.codex/concepts/WX.30-location-timeline-board-v3-rejected.svg` with PNG preview and this contract.

**Revision:** The owner rejected revision 1 on 2026-11-08 because it showed separate rails, revision 2 because the controller sat mid-screen, and revision 3 because the location controller still looked different from the main-view controller. Revision 3 did place the rail at the bottom, but that alone did not meet the requested visual match. The installed main-view capture is included for comparison.

**Owner's correction:** Keep the main view's controller at the bottom. When opening location search, change the controller's data, change the center readout to the focused location and its actions, and add the search input above the controller. Keep the same city scene and overall screen composition.

**References:** `.codex/concepts/WX.30-location-timeline-board-v3-rejected.png`, `screenshots/main-screen-pixel-3-xl-2026-11-08.png`, `screenshots/location-search-pixel-3-xl-2026-11-08.png`, and `design_draft/location_search_ui_board.png`. The existing screenshots and design draft inform the board but are not approval evidence.

## Proposed behavior and appearance contract

1. Keep the location controller anchored in the main view's bottom timeline area. Preserve the controller's visual scale, horizontal rail, centered marker, edge fades, horizontal drag ownership, and responsive movement. Do not move the controller into the screen's middle.
2. Replace the main controller's forecast values with location data: search results above the rail and saved locations below the same rail. The rows share one position and gesture and move together. Repeat the shorter populated set circularly. Empty, loading, and failed search or saved states retain their existing status treatment without fabricated places.
3. Put the manual search input immediately above the bottom controller without displacing it. Preserve explicit query submission, search status, result identity, and provider attribution.
4. Replace the weather center readout with the focused location's full name and disambiguating location details. The available center-readout actions depend on the focused row: search results offer Save and View; saved locations offer Remove and View, preserving the approved removal confirmation.
5. Focusing a place changes the center readout and the data highlight. It does not activate the location. Save and View are the explicit result actions; saved View uses its saved catalog. Save persists the location and its catalog. View is temporary and its catalog is discarded when switching locations, as required by the WX.30 roadmap contract.
6. Keep the current saved location green. Use the main controller's gold selection accent for a focused search result. Other entries keep the existing light text. Each entry shows a three-letter designation plus state or country.
7. Keep the same city background, main-screen atmosphere, and current-location header. Location search changes the controller's data, center readout, and adds the search input; it does not move the controller or restructure the rest of the view.

## Boundaries and acceptance intent

- Preserve existing query, attribution, location identity, saved-location removal confirmation, and catalog handoff semantics. Do not add device location or provider changes.
- Installed acceptance is scoped to the selector on Pixel 3 XL portrait. Capture the main view and location search view to check that the controller remains bottom-aligned and visually consistent; cover the saved-focused and search-result-focused center readouts, both actions, search submission/status, unequal list lengths and circular repetition, and readable data labels. Check responsiveness and gesture ownership in affected states. Record actual screenshots and exact interaction outcomes. Automated tests require separate owner authorization.
- RTL, TalkBack, broad accessibility, and environment matrices remain outside this design slice.

## Product-owner gate

Revision 3 was rejected because its controller appearance did not match the main view. Await further owner instruction before preparing another revision. The cycle remains PLANNED at BOARDS.
