# WX.30 — One visible location set: revision 5 draft

**Status:** Combined behavior and UI artwork board approved by the product owner on 2026-11-08, after revision 5 was redrawn to preserve the main-view rail appearance.

**Artwork:** [SVG](WX.30-location-timeline-board-v5.svg) and [PNG preview](WX.30-location-timeline-board-v5.png). Revision 5 reuses revision 4's embedded installed main-view reference, city scene, panel scale, controller geometry, line, marker, edge treatment, and pill. Only the location rail content and explanatory callouts change. The saved and submitted-search panels are design proposals, not installed captures.

## Behavior contract

1. Keep the main-view controller's bottom position, footprint, uninterrupted rail, center marker and short stem, edge treatment, and inset pill. The search input sits above it. The city scene and current-location header remain.
2. Show **one place set at a time** on the rail. Saved places are the default. Submitting a nonempty search switches the rail to results; clearing the search returns to saved places. Search status, including loading, no matches, failure, and retry, remains visible without inventing entries. Search editing alone does not change the active set before submission.
3. Saved mode shows each place's three-letter designation and state/country in the upper band and its temperature at the authoritative selected forecast instant in the lower band. Read temperature from that place's matching saved catalog when available. Show an explicit unavailable placeholder when that catalog or value does not cover the instant; never infer a value from the active place or retarget the selected instant. Results mode shows each result's designation and state/country in the upper band; the lower band shows no invented temperature.
4. A tap or drag release focuses the place under the centered marker, changing the full center readout and available actions without activation. The current saved place stays green; a focused result uses gold; other entries remain light. Use canonical place ID and full context for identity, never the three-letter designation.
5. Focused results offer **Save / View**. Save persists the place, selected choice, and matching LIVE catalog. View loads and activates a validated LIVE recording transiently, with no saved-location, selected-location, or dataset writes. Switching discards the transient recording; restart restores the last saved choice. Focused saved places offer **Remove / View**, retaining confirmation and persisted catalog semantics.
6. Preserve explicit search submission, retry, result identity, provider attribution, Back dismissal, and responsive gesture ownership for the rail, search input, and action buttons.

## Review checks

- Compare each proposed controller state with the installed main-view reference for bottom alignment, rail, marker, edge treatment, pill, and drag feel.
- Inspect saved mode with available and unavailable temperature values, submitted-result mode, query status, action pairs, and full place identity.
- Installed acceptance remains Pixel 3 XL portrait captures plus scoped interaction outcomes. The artwork does not establish installed acceptance.
