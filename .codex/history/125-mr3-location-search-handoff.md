# Cycle 125-mr3-location-search-handoff

Date: 2026-11-10
Roadmap items: MR.3

## Outcome

Completed MR.3 saved-location LIVE handoff, keyboard-aware search submission, one-rail status behavior, and unchanged temporary View/selection/timeline interactions. Installed evidence covers saved-location loading, success, and offline failure; search typing, matches, no-match, immediate loading, and provider failure. Android connectivity and the original New York/Florence/Puerto Rico saved-place state were restored after the focused network-unavailable run. MR.3 is recorded DONE.

## Slice outcomes

- **MR.3 — DONE:** Installed MR.3 exit met on Pixel 3 XL. Saved New York View remained in the selector through loading and dismissed after catalog activation; an uncached saved Vancouver View with network unavailable left the selector open with the existing transport error and Retry forecast. Search typing, Enter/Search Matches, NoMatches, Loading, and provider Failure with Retry were captured in the single-rail composition. Temporary Boston View preserved the selected instant, selection persistence remained New York, and a timeline drag changed the selected instant. Connectivity was restored, the temporary Vancouver saved place was removed, and the original three-place saved list and New York selection were verified. Evidence: .codex/test-artifacts/125-mr3-location-search-handoff/README.md, .codex/test-artifacts/125-mr3-location-search-handoff/search-keyboard-open-typing.png, .codex/test-artifacts/125-mr3-location-search-handoff/search-enter-results.png, .codex/test-artifacts/125-mr3-location-search-handoff/search-onscreen-results.png, .codex/test-artifacts/125-mr3-location-search-handoff/search-no-match.png, .codex/test-artifacts/125-mr3-location-search-handoff/search-loading-fast-capture.png, .codex/test-artifacts/125-mr3-location-search-handoff/search-provider-failure.png, .codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-loading.png, .codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-success.png, .codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-failure.png, .codex/test-artifacts/125-mr3-location-search-handoff/saved-location-selection-after-transient-view.png, .codex/test-artifacts/125-mr3-location-search-handoff/temporary-result-view-success.png, .codex/test-artifacts/125-mr3-location-search-handoff/timeline-before-interaction.png, .codex/test-artifacts/125-mr3-location-search-handoff/timeline-after-interaction.png, .codex/test-artifacts/125-mr3-location-search-handoff/saved-location-state-restored.png.

## Verification actually performed

- python scripts/dev.py workflow — passed at final closeout.
- git diff --check — passed at final closeout.
- python scripts/dev.py run --serial 8AKY0M32X — debug build and install succeeded.
- Installed focused UI acceptance on Pixel 3 XL serial 8AKY0M32X, Android 12/API 31, portrait, 1440x2960, font scale 1.0: keyboard-open field/query, IME Enter/Search Matches and NoMatches, on-screen Search Matches, Loading, search provider failure with Retry, saved-location loading/success/failure with Retry, temporary Boston View time continuity, timeline drag, saved-selection restoration.
- Network-unavailable run used Android airplane-mode and Wi-Fi controls only; no app fault-injection code was used. Airplane mode was disabled, Wi-Fi enabled, and a validated active route confirmed after the run.
- The temporary Vancouver saved place was removed through the UI. App storage was read afterward to confirm only Florence, New York, and Puerto Rico remained saved and New York remained selected.
- Automated tests were not added or run, as authorized.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/125-mr3-location-search-handoff/`. Archived plan: `.codex/history/plans/125-mr3-location-search-handoff.md`. See the plan for stage checkpoints and exact boundaries.
