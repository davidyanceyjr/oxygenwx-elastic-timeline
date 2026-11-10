# WX.30 — One shared location timeline: behavior and UI board

**Status:** Rejected by the product owner on 2026-11-08; retained as review history.

**Board:** Rejected combined behavior/UI package: `.codex/concepts/WX.30-location-timeline-board-v2-rejected.svg` with PNG review preview and this contract.

**Revision:** Rejected on 2026-11-08 because the shared controller sat in the middle of the screen and did not read as the main-view controller fixed at the bottom. Revision 1 remains retained separately as `WX.30-location-timeline-board-v1-rejected.*`.

**References:** `screenshots/location-search-pixel-3-xl-2026-11-08.png`, `screenshots/main-screen-pixel-3-xl-2026-11-08.png`, and `design_draft/location_search_ui_board.png` inform this design. The existing 16-direction concept sheet remains exploratory input, not an approved board.

## Proposed behavior contract

1. Replace the two independently scrolling controls with one timeline-style location controller modeled on the main-screen timeline. There is one horizontal rail, one center marker, one scroll position, and one gesture owner. Submitted search results are laid out above the rail; saved locations are laid out below it. The tracks move together in lockstep.
2. Each data set is a circular sequence while it has entries. Repeat a shorter list as needed so its side of the shared rail remains populated while the controller moves. A genuinely empty/loading/failed list keeps its existing empty/loading/error treatment; it does not fabricate entries.
3. The place on the touched side of the rail is the focus source. A tap centers that place. A horizontal drag on either side moves the shared controller like the main-screen timeline; the focused side supplies the centered location readout and action pair. The opposite side remains context on the same rail.
4. The readout shows the full place name and disambiguating details. Each rail entry uses a three-letter place designation plus state for US locations or country for other locations. Green identifies the current saved location. Gold identifies a centered search-result preview. Other entries use the existing light text color.
5. A centered search result offers Save and View. Save adds the place to saved locations, persists its catalog, activates it, and returns to the main forecast. View activates it temporarily without saving it or its catalog; discard the temporary catalog when switching locations, and restore the prior saved selection on restart.
6. A centered saved location offers Remove and View. View activates the saved location and its matching catalog, then returns to the main forecast. Remove retains the existing explicit confirmation and saved-record/catalog removal behavior.
7. Centering previews a place; Save or View explicitly commits the action. The main forecast timeline and its selected-time behavior remain unchanged.

## Boundaries and acceptance intent

- Preserve manual query submission, search status, result identity, and attribution. Do not add device location, provider changes, or weather facts to the location readout.
- Installed acceptance is scoped to the selector on Pixel 3 XL portrait: both data sets shown around one rail; unequal list lengths with circular repetition; focus from either side; location details and actions; Save, temporary View and switch-away discard; saved View; and Remove confirmation. Record actual screenshots and interaction results. Check responsiveness, gesture ownership, and label readability in affected states. Automated tests require separate owner authorization.
- RTL, TalkBack, broad accessibility, and environment matrices remain outside this design slice.

## Decisions for owner review

- Confirm the one-rail arrangement: results above and saved locations below, sharing the same position and gesture like the main-screen timeline.
- Confirm the Save/View lifecycle, especially whether View leaves the location itself unsaved and only keeps its active catalog temporarily.
- Confirm green for the current saved location and gold for a centered search-result preview.
- Confirm that centering previews a location and the action button commits it.
