# WX.30 — Main-view controller with location data: revision 4

**Status:** Combined behavior and UI artwork board approved by the product owner on 2026-11-08, after revisions 1–3 were rejected. No added constraints.

**Artwork:** [SVG](WX.30-location-timeline-board-v4.svg) and [PNG preview](WX.30-location-timeline-board-v4.png). The left panel embeds the installed main-view capture as the visual reference. The two right panels show saved-place and search-result focus. They are proposed artwork, not installed captures.

**Correction from revision 3:** The location controller uses the main-view controller's bottom position and footprint; one uninterrupted horizontal rail; the same centered gold dot and short stem; the same soft edge treatment; and the same inset bottom pill. There are no new tick marks, track headings, or long selection line. The main-view upper and lower data bands become search results and saved locations, respectively. The pill displays the focused location code. Location data changes the content of those elements, not their structure or gesture treatment.

## Behavior contract

1. The search input sits above the bottom controller. The city scene and current-location header remain in place. The center weather readout becomes the focused place's full name, disambiguating details, and available actions.
2. Search results occupy the controller's upper data band and saved locations its lower band. Both bands share one horizontal position and drag gesture. The shorter populated list repeats circularly. Empty, loading, no-match, and failure states keep their existing status treatment without fabricated places. Each populated entry shows a three-letter designation with state or country.
3. Dragging either band moves both together with the main controller's responsive feel. Tapping a place or releasing a drag focuses the place under the centered marker. Focus changes the detail readout and emphasis; it does not activate the location. The current saved location stays green. A focused search result uses the main view's gold selection accent; other entries use light text. The gold marker remains the controller's focus marker in both views.
4. A focused search result offers **Save** and **View**. Save persists the location and its catalog, activates it, and returns to the forecast view. View activates it temporarily; its catalog is discarded after switching locations and it is not restored as a saved place at startup.
5. A focused saved location offers **Remove** and **View**. Remove retains the approved confirmation flow and saved-record/catalog removal semantics. View activates the saved place with its persisted catalog and returns to the forecast view.
6. Preserve explicit manual query submission, search status, result identity, and provider attribution.

## Scope and review checks

- Compare the installed main-view controller with the location view at the same portrait scale: bottom alignment, band positions, line, marker, edge treatment, pill, and gesture feel. The search input and location readout are the added or substituted content.
- Installed acceptance remains scoped to Pixel 3 XL portrait and the saved-focused/result-focused states, query status, unequal list lengths and circular repetition, Save, temporary View, saved View, and Remove confirmation. Record actual captures and interaction outcomes, including responsiveness, gesture ownership, and readable labels. Automated tests require owner authorization.
- The source screenshots and earlier rejected boards informed revision 4; none grants approval. The owner approved this revision on 2026-11-08. Installed captures and interaction outcomes remain required for WX.30 acceptance.
