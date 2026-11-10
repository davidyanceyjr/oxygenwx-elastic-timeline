# WX.30 — Shared location timeline behavior and UI board

**Status:** Draft for product-owner review; no approval recorded.

**Board:** `WX.30-location-timeline-board.svg` with PNG review preview. This combined board is intended to cover the behavior and UI artwork gates for WX.30. Approval must be recorded after the owner reviews the SVG/PNG.

**References:** The installed views in `screenshots/location-search-pixel-3-xl-2026-11-08.png` and `screenshots/main-screen-pixel-3-xl-2026-11-08.png`, plus `design_draft/location_search_ui_board.png`, inform the redesign. The 16-direction location timeline concept sheet is exploratory input. None of those references approves this changed behavior or artwork.

## Proposed behavior contract

1. Replace the independently scrolling search-results and saved-location rows with one timeline-style location component. The top track shows submitted search results; the bottom track shows saved locations. One horizontal drag controller moves both tracks together. A shorter populated track repeats circularly to keep its rail populated; an empty/loading/error track keeps its existing state instead of inventing places.
2. Tapping a place or releasing a drag centers the place from the touched track under the shared selection marker. The touched track owns focus; the other track continues as context. The focused place supplies the center readout and the action pair. Search input and the existing idle/loading/matches/no-match/failure/retry and attribution behavior remain in the same scene.
3. Rail entries show a three-letter place designation and state for US locations or country for other locations. The center readout shows the full place name and disambiguating location details. Green marks the current saved location; gold marks a focused search-result preview; white marks other rail entries. These colors are independent of the action buttons.
4. A centered search result offers Save and View. Save adds the place to saved locations, persists its location catalog, activates it, and returns to the main forecast view. View activates the place for the current session without saving its location or catalog; when another location is activated, discard its temporary active catalog. A temporary view does not replace the saved selection restored at startup.
5. A centered saved location offers Remove and View. View activates its saved location and matching persisted catalog, then returns to the main forecast view. Remove retains the existing explicit confirmation and saved-record/catalog removal behavior.
6. Centering is a preview; Save or View is the explicit commit. This interpretation makes the action row meaningful and is an approval point for the owner.

## Boundaries and acceptance intent

- Preserve manual search and existing provider identity/attribution; do not add device location, provider changes, or new weather facts to the center readout.
- Keep the existing main forecast screen and forecast-time selection behavior unchanged. WX.30 replaces the selector's two scrolling controls and owns the new location-view persistence distinction required by View.
- Installed acceptance is scoped to the location selector on Pixel 3 XL portrait: populated rails with unequal list lengths, saved and result focus, centered detail/action states, Save, temporary View and switch-away discard, saved View, and Remove confirmation. Record actual screenshots and interaction outcomes. Check responsiveness, gesture ownership, and readable labels in these states. Automated tests require separate owner authorization.
- RTL, TalkBack, broad accessibility, and environment matrices remain outside this design slice.

## Open decisions for approval

- Confirm that one drag moves both tracks together, while taps/drags on a track make that track the focus source.
- Confirm the Save/View lifecycle stated above, especially that Save persists both saved identity and catalog while View keeps neither across switching or restart.
- Confirm the proposed color meanings: green for the current saved location, gold for a focused unsaved result, white for other locations.
- Confirm that centering previews a place and the action button commits it.

These decisions are explicit in the artwork preview so they can be approved or revised before the cycle plan is finalized.
