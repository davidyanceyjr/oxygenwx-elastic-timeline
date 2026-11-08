# WX.29 behavior board — Location search and selection

**Visual board:** `.codex/concepts/WX.29-location-search-behavior-board.svg` (PNG preview alongside).

**Status:** Approved by the product owner on 2026-11-07 together with the replacement UI state board v2. This behavior board governs the interactions below; the approved board package is recorded in cycle 118.

**UI artwork:** The owner-selected primary appearance is `design_draft/location_search_ui_board.png`. The earlier state extension in `.codex/concepts/WX.29-location-search-ui-states.svg` was rejected on 2026-11-07 because boxes and solid backgrounds break the supplied scene and timeline-rail navigation. The replacement state board v2 and this behavior board were approved together on 2026-11-07.

**Current-state reference:** `.codex/test-artifacts/118-wx29-location-search-refactor/location-search-installed-reference.png` is a connected-device capture of the existing selector before search. It is a planning reference, not acceptance evidence.

## Governing interaction

The location control beside the main-screen city opens the full-screen location-search experience shown in the approved artwork. Opening refreshes the saved-location snapshot and selected ID. It does not request device location or submit a search. The artwork's Florence and `flor` examples are illustrative; result names, order, and count come from the existing place-search provider.

| State / action | Required behavior |
|---|---|
| Open from main screen | Show the location-search view and refresh saved locations/current selection. Keep the current main-screen location and forecast selection until the user commits a location. |
| Saved-location snapshot | Show loading while the snapshot is fetched, distinguish an empty list from a populated list, and provide recovery when it cannot be loaded. |
| Edit query | Update the query without calling the provider. An empty trimmed query remains idle. |
| Submit a non-empty query | Tapping the artwork's magnifier or submitting the keyboard Search action starts the request; typing alone does not. Show loading, then provider matches, no matches, or a recoverable failure. Retry resubmits the query; editing or a newer request replaces the visible search state. Dismissing the view cancels or invalidates pending search work. |
| Choose a search result | Preserve the returned place identity and disambiguating context. Save the place if it is not already saved, persist it as selected, return to the main screen, and let the existing location-scoped catalog path load that location. Preserve the supplied search attribution/license disclosure. |
| Choose a saved location | Persist that saved ID as selected, return to the main screen, and load its matching location-scoped catalog. Clearly distinguish the current location from other saved locations. |
| Remove a saved location | Ask for explicit confirmation before removal. Keep the city name in the confirmation title and show that saved place's full `displayLabel` directly beneath it so duplicate city names are distinguishable. Cancel performs no removal. Confirm removes that saved location and its stored catalog data, then refreshes the saved-location view. |
| System Back during removal confirmation | Treat Back as Cancel: keep the saved record unchanged, clear the confirmation, and keep the location selector open. |
| Exit without a committed selection | System Back returns to the same main-screen location and selected forecast time. No search result is selected and no catalog switch is initiated. The approved full-screen artwork has no visible close control and does not imply outside-scrim dismissal. |

## Invariants and boundaries

- Location search stays manual. No device-location permission, current-position lookup, or background location behavior is added.
- Search results remain distinguishable by their returned place identity and context; display names, timezone, coordinates, and provider attribution are not fabricated or discarded.
- A search-result choice saves if needed and selects; a saved-location choice selects. Selection remains explicit and continues through the existing WX.26 catalog-loading handoff.
- A new location is never shown with another location's weather. Failed/no-data catalog behavior remains governed by WX.26.
- Dismissal without selection preserves the current location and selected forecast time.
- The target artwork's abbreviations and colors do not replace readable city/context text or selected/saved meaning.
- No provider request/contract, saved-location schema, startup policy, forecast presentation, unrelated navigation, or deprecated oxygenWX files are in scope.
- Keep focused verification within this view. This slice does not claim broad TalkBack, RTL, font-scale, or system acceptance.

## Coupled approval decisions (2026-11-07)

The product owner approved this behavior board together with the replacement UI state board v2, including these decisions:

1. Loading, no-match, failure, retry, and removal confirmation stay in the supplied city scene. Search status occupies the upper area while the saved rail remains available when its snapshot is known.
2. Search-result attribution and license details remain available in the scene; exact provider text is dynamic.
3. System Back is the uncommitted exit. The view has no visible close control and does not dismiss on an outside or scrim tap.
4. The magnifier and keyboard Search submit a non-empty query. Query editing alone does not submit.
5. While full-screen removal confirmation is open, System Back acts as Cancel: make no removal and keep the selector open.
6. The removal confirmation shows the saved place's full `displayLabel` beneath the city-name title. This identifies the exact saved record when city names repeat.
