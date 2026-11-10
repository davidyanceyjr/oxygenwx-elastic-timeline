# WX.29 UI artwork state extensions

**Primary target:** Owner-approved `design_draft/location_search_ui_board.png` (2026-11-07). Preserve it unchanged as the target appearance for the full-screen location search view.

**Rejected companion board:** `.codex/concepts/WX.29-location-search-ui-states.svg` and its PNG preview were rejected by the product owner on 2026-11-07. Their boxes, solid backgrounds, and separated state panels do not preserve the supplied scene or the timeline rail as the app's navigation method. These files remain as review history, not an implementation target. Replacement artwork must show affected states in the supplied full-screen composition and keep timeline-rail navigation continuous.

## Proposed affected states

- Search idle with saved locations visible.
- Search loading while the submitted query remains visible.
- No matches and failed search with a retry/edit path.
- Saved-location snapshot loading, empty, and unavailable/retry states.
- Per-result source/license details remain available without placing attribution text across the primary view.
- Saved-location removal confirmation clearly names the affected place and explains that its stored catalog data is removed; Cancel and Remove remain distinct.
- Selected and saved status remains legible in the primary view.

The variants use the same deep-blue scene, white location labels, and green selected-state accent shown in the primary target. They do not fix provider result counts, ordering, location values, or any new weather presentation. The proposed compact per-result details control is the only added visual affordance and is included to preserve the existing WX.19 attribution disclosure.

**Status:** Rejected; replacement required. Re-review the coupled behavior board with that replacement.
