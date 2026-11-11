# MR.3 UI artwork board — keyboard-aware search and saved-location return

**Status:** Approved by the product owner on 2026-11-10. No additional constraints were stated.

**Artwork:** `.codex/concepts/MR.3-saved-location-search-ui-artwork-board.svg` with PNG review image alongside.

**Roadmap slice:** [MR.3 — Return to the main view after saved-location View loads](../../docs/ROADMAP.md#mr3--return-to-the-main-view-after-saved-location-view-loads).

## Purpose and visual objective

Show the MR.3 search and saved-location handoff states in the existing location selector's visual language. Keep the full-screen city scene, dark overlay, readable place identity/context, search controls, location rail, and explicit Save/View actions. Make the search field and current query visible above the system keyboard while typing; after Enter/Search, show the query and search result state with the keyboard dismissed. For a saved-location View, keep the selector visible while its LIVE catalog activates, dismiss it on success, and preserve existing failure feedback if activation fails.

This board is the appearance companion to the owner-approved MR.3 behavior board. It guides the MR.3 visible-state and installed acceptance review; it is not installed acceptance evidence.

## Intended states

### Search query while the keyboard is open

- Keep the existing selector, current-place context, scenic background, search field, clear control, and Search action.
- Position/reflow the existing search field so its full outline, current query, clear affordance, and Search action remain above and unobscured by the system IME.
- Typing remains editing only. It does not change the selected place or focused result and does not start a provider request.
- The system keyboard appearance is illustrative only; the board does not redesign the IME.

### Enter/Search submitted; keyboard dismissed

- Hide the keyboard automatically when its Enter/Search action submits the current non-empty query. Do not require a second dismissal gesture.
- Retain the query in the field and show the existing search loading, matches, no-match, or failure state in the selector.
- Keep the result identity and context readable, and preserve explicit Save/View actions. Submission does not itself select a result.
- Continue to use the approved WX.29 v2 state treatment for search status, saved-location rail continuity, retry, and result presentation. MR.3 only adds the keyboard-open and keyboard-dismissed compositions shown here.

### Saved-location View handoff

- While the selected saved location's LIVE catalog is loading, keep the selector open and the selected place visible. The inline “selected / loading its forecast catalog” message reflects the existing controller copy.
- On successful LIVE activation, close the selector and show the main weather view with that location's active catalog.
- On failure, keep the selector open and make the existing forecast failure/retry feedback available. The sample “Forecast unavailable” wording is the existing main-view fallback; provider-specific error text can vary.

## Visual constraints and scope

- Preserve the app's city-scene resource, dark blue scene treatment, white place text, green current saved-place accent, gold focused-result accent, turquoise primary action, and existing search/rail components.
- Keep the existing search submission semantics, provider attribution, result identity, persistence, selected-time behavior, and timeline behavior. No device-location permission, provider change, or new selection behavior is introduced.
- Place names, result count, coordinates, and time-zone examples in the board are illustrative. Runtime values come from the existing provider and saved-location data.
- The displayed main-screen capture is the connected device's current Florence example. It illustrates the destination screen composition; it does not claim that Florence is the MR.3 target selection or acceptance result.
- No unrelated screen redesign, solid screen panels, or boxed result states are introduced. Broader accessibility and environment matrices remain outside this slice.

## References

- Owner-approved MR.3 behavior contract: `.codex/concepts/MR.3-saved-location-search-behavior-board.md`.
- Approved location-search visual direction and existing result-state treatment: `.codex/concepts/WX.29-location-search-ui-states-v2.md` and `.codex/concepts/WX.29-location-search-ui-states-v2.png`.
- Current installed keyboard-obstruction reference: `.codex/concepts/MR.3-current-keyboard-obstruction-reference.png`, captured from the connected Pixel 3 XL on 2026-11-10. The selector is open and the IME covers the search field; this is a current-state reference, not a target or acceptance screenshot.
- Current installed main-view reference: `.codex/concepts/MR.3-current-main-view-reference.png`, captured from the connected Pixel 3 XL on 2026-11-10, package `com.oxygen.weather.timeline` version `0.1.0-prototype`. It shows the current Florence main view and is context only.
- Existing selector scene asset: `app/src/main/res/drawable-nodpi/sg2_city_architecture.png`.
- Relevant implementation boundary reviewed: `LocationSelectorPanel.kt` (selector composition, query field, search status, and location rail), `LocationSelectorController.kt` (selection/loading message), and `MainActivity.kt` (catalog activation and existing retry/failure presentation).

## Approval and acceptance boundary

The product owner approved this board on 2026-11-10 with no additional constraints. The behavior and artwork boards together define scope and acceptance intent. Record the approved package paths and content fingerprints in MR.3's cycle manifest when its BOARDS stage is opened. Installed screenshots and focused interactions are still required to establish the MR.3 exit; the references and this board do not establish that the installed app already meets it.
