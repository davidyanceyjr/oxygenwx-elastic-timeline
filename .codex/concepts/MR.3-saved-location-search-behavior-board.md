# MR.3 behavior board — saved-location return and keyboard-aware search

**Status:** Approved by the product owner on 2026-11-10. No additional constraints were stated.

**Visual board:** `MR.3-saved-location-search-behavior-board.svg` (PNG preview alongside).

**Roadmap slice:** [MR.3 — Return to the main view after saved-location View loads](../../docs/ROADMAP.md#mr3--return-to-the-main-view-after-saved-location-view-loads).

## Purpose

Define the location-selector behavior required by MR.3: keep the selector available until a saved location's LIVE catalog is active, make the search field and typed query visible while the keyboard is open, and automatically hide the keyboard when the keyboard's Enter/search action submits the query so the search state can be seen.

## Behavior contract

### Saved-location View and LIVE catalog handoff

1. The user chooses **View** for a saved location.
2. Start the existing saved-location selection and LIVE catalog activation path. Keep the location selector visible while activation is pending.
3. On successful activation of the chosen location's LIVE catalog, dismiss the selector and show that location on the main weather view.
4. If activation fails, do not dismiss the selector as if successful. Keep the selector available and preserve the existing failure feedback.

The selector's dismissal is tied to successful catalog activation. Starting a request or persisting the selected location alone is not the success handoff.

### Search field, IME, and results

1. Focusing the search field opens the on-screen keyboard. Keep the field and its current text visible and unobscured while the keyboard is open, so each typed character can be seen.
2. Editing the query alone does not submit it. An empty or whitespace-only query stays idle.
3. Pressing the keyboard's **Enter/Search** action automatically dismisses the keyboard and submits the current non-empty query. The user does not need a separate keyboard-dismiss gesture.
4. Show the existing search loading state, then matches, no matches, or recoverable failure in the location selector. Keep the query available in the field. Existing retry behavior remains available after a failure.
5. The selector's on-screen **Search** action keeps its existing submit behavior. Search submission itself does not select a result or activate a location.

## Invariants and boundaries

- A search-result choice still requires the existing explicit **Save** or **View** action.
- The temporary search-result **View** path remains unchanged.
- Saved-location selection persistence, selected-forecast-time behavior, and timeline interaction remain unchanged.
- Keep existing provider, attribution, saved-location, loading, and failure semantics. No device-location permission or provider change is introduced.
- This behavior board defines flow and state transitions. A separate approved UI artwork board is still required before MR.3's implementation plan can be finalized.

## Repository and installed references

- The approved WX.29 behavior board defines manual query submission, result states, retry, and explicit result choice: `.codex/concepts/WX.29-location-search-behavior-board.md`.
- The approved WX.29 UI state board defines the earlier location-search state presentation: `.codex/concepts/WX.29-location-search-ui-states-v2.md`.
- The current installed reference is `.codex/concepts/MR.3-current-keyboard-obstruction-reference.png`. It was captured on 2026-11-10 from the connected Pixel 3 XL running the installed `com.oxygen.weather.timeline` app (`0.1.0-prototype`). With `Boston` typed and the keyboard open, the IME covers the app's search field. This capture documents the current obstruction; it is not a target or acceptance evidence.
- The board's success and failure branches follow the MR.3 roadmap exit. The board does not claim the installed app already meets those requirements.
