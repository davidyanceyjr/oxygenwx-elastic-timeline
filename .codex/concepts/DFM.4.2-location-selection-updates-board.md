# DFM.4.2 — Location selection and subsequent updates

**Status:** Owner approved 2026-11-01. This supplements the approved session flow and location-selection artwork.

```text
Choose a search result or saved location
  → end debug session first, if needed
  → save the choice if needed and select it
  → close the location menu; show the main view and load that location
  → after the selected location's foreground work settles,
    update the other saved locations' catalogs

Choose another location while work is underway
  → save the new choice; give the latest successful choice foreground priority
  → older completions cannot replace it in the main view
  → continue eligible updates for the other saved locations afterwards
```

The location menu can be reopened while loading. Successfully saved locations remain in the list until removed. A failed save is visible and does not select that choice; a failed load remains truthful. Background updates belong to their own saved locations and never activate the main view; removing a location prevents late work from recreating its data. Existing source, cache and provenance rules apply. Updates are best effort during the app's lifetime; this board makes no promise of execution after process death or automatic retry.

The approved location-selection artwork governs the search and saved-list appearance. Verify rapid choices, the final active location, subsequent updates, failures, removal and the existing debug restore-before-action boundary. Installed captures remain separate acceptance evidence.
