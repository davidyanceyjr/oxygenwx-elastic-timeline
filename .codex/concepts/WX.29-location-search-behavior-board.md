# WX.29 behavior board — Location search and selection

**Visual board:** `.codex/concepts/WX.29-location-search-behavior-board.svg` (PNG preview alongside).

**Status:** Draft; behavior intent was described as acceptable on 2026-11-07, but approval is withheld pending review against revised timeline-based UI artwork. Interaction placement and presentation are not settled.

**UI artwork:** The owner-selected primary appearance is `design_draft/location_search_ui_board.png`, approved on 2026-11-07. The repository path is singular `design_draft`. The proposed state extensions in `.codex/concepts/WX.29-location-search-ui-states.svg` were rejected on 2026-11-07: boxes and solid backgrounds break the supplied scene and timeline-rail navigation. This behavior board must be reviewed again with replacement artwork before approval is recorded.

**Current-state reference:** `.codex/test-artifacts/118-wx29-location-search-refactor/location-search-installed-reference.png` is a connected-device capture of the existing selector before search. It is a planning reference, not acceptance evidence.

## Governing interaction

The location control beside the main-screen city opens the full-screen location-search experience shown in the approved artwork. Opening refreshes the saved-location snapshot and selected ID. It does not request device location or submit a search. The artwork's Florence and `flor` examples are illustrative; result names, order, and count come from the existing place-search provider.

| State / action | Required behavior |
|---|---|
| Open from main screen | Show the location-search view and refresh saved locations/current selection. Keep the current main-screen location and forecast selection until the user commits a location. |
| Saved-location snapshot | Show loading while the snapshot is fetched, distinguish an empty list from a populated list, and provide recovery when it cannot be loaded. |
| Edit query | Update the query without calling the provider. An empty trimmed query remains idle. |
| Submit a non-empty query | Proposed: tapping the artwork's magnifier or submitting the keyboard Search action starts the request; typing alone does not. Show loading, then provider matches, no matches, or a recoverable failure. Retry resubmits the query; editing or a newer request replaces the visible search state. Dismissing the view cancels/invalidate pending search work. |
| Choose a search result | Preserve the returned place identity and disambiguating context. Save the place if it is not already saved, persist it as selected, return to the main screen, and let the existing location-scoped catalog path load that location. Preserve the supplied search attribution/license disclosure. |
| Choose a saved location | Persist that saved ID as selected, return to the main screen, and load its matching location-scoped catalog. Clearly distinguish the current location from other saved locations. |
| Remove a saved location | Ask for explicit confirmation before removal. Cancel performs no removal. Confirm removes that saved location and its stored catalog data, then refreshes the saved-location view. |
| Exit without a committed selection | System Back or an exit affordance supported by the approved view returns to the same main-screen location and selected forecast time. No search result is selected and no catalog switch is initiated. The full-screen artwork does not imply outside-scrim dismissal. |

## Invariants and boundaries

- Location search stays manual. No device-location permission, current-position lookup, or background location behavior is added.
- Search results remain distinguishable by their returned place identity and context; display names, timezone, coordinates, and provider attribution are not fabricated or discarded.
- A search-result choice saves if needed and selects; a saved-location choice selects. Selection remains explicit and continues through the existing WX.26 catalog-loading handoff.
- A new location is never shown with another location's weather. Failed/no-data catalog behavior remains governed by WX.26.
- Dismissal without selection preserves the current location and selected forecast time.
- The target artwork's abbreviations and colors do not replace readable city/context text or selected/saved meaning.
- No provider request/contract, saved-location schema, startup policy, forecast presentation, unrelated navigation, or deprecated oxygenWX files are in scope.
- Keep focused verification within this view. This slice does not claim broad TalkBack, RTL, font-scale, or system acceptance.

## Review points

1. The supplied UI target shows the search-results/saved-location state but not loading, no-match, failure, or removal-confirmation appearances. The proposed state board keeps those outcomes in the same view and uses the target result area for their content; owner approval is required.
2. WX.19 exposes search-source attribution and license details on each result. The proposed state board retains a per-result details affordance, while the supplied target does not depict it. Confirm that this is an acceptable addition to the primary target view.
3. The full-screen target has no explicit close control. This board requires system Back and permits a visible exit affordance only if the owner includes one in the artwork; no outside-tap dismissal is assumed.
4. Confirm the target's magnifier submits the typed query (with keyboard Search as an equivalent), keeping the existing explicit-submit behavior rather than searching on each edit.
