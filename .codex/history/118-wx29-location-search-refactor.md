# Cycle 118-wx29-location-search-refactor

Date: 2026-11-07
Roadmap items: WX.29.1, WX.29.2, WX.29.3

## Outcome

Cycle 118 completed WX.29.1, WX.29.2, and WX.29.3 in dependency order. WX.29.3 includes the product-owner-approved duplicate-name treatment: the removal confirmation retains the city-name title and shows the saved location full displayLabel beneath it. Installed Pixel 3 XL evidence confirms distinct Florence Tuscany and Alabama labels, Cancel for each record, and System Back cancellation while both saved rows and Chicago current selection remain intact. Existing WX.29 selection, matching-catalog, dismissal, timeline-continuity, manual-search, and removal outcomes remain recorded in the cycle evidence index.

## Slice outcomes

- **WX.29.1 — DONE:** Full-screen city scene and saved rail implemented; installed Pixel 3 XL captures cover loading, empty, populated, unavailable/retry, touch-to-green current selection, and matching Chicago catalog handoff. Temporary debug fixture was removed; lint remains blocked by four errors outside the changed selector files; no tests were run. Evidence: .codex/test-artifacts/118-wx29-location-search-refactor/README.md, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-populated-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-empty-isolated-user.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-loading-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-unavailable-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-retry-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-after-touch.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-chicago-active.png.
- **WX.29.2 — DONE:** Installed Pixel 3 XL portrait query editing/explicit submit, loading, matches, no-match, connectivity failure/retry, result identity/context and attribution, persistent lower saved rail, result selection handoff, and timeline responsiveness recorded. The required loading capture used a temporary five-second debug-only delay; it was removed, and the final source was rebuilt and reinstalled. Lint remains blocked by four unrelated existing errors; no automated tests were run. Evidence: .codex/test-artifacts/118-wx29-location-search-refactor/README.md, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-loading.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-matches-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-details-final.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-no-matches.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-failure.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-retry.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-result-selected.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-after-select-picker.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-timeline-responsive.png.
- **WX.29.3 — DONE:** Installed the owner-approved full displayLabel beneath the removal city title; distinct Florence Tuscany and Alabama labels were visible on Pixel 3 XL. Cancel on each and System Back on Alabama kept the selector open, Chicago current, and both records present. Earlier documented WX.29.3 handoff, matching-catalog, Back/time continuity, manual-search, and removal outcomes remain evidence-backed. Contract, build, run, and screenshots passed; lint reported four errors outside the changed selector file. No automated tests were run under the repository instructions. Evidence: .codex/test-artifacts/118-wx29-location-search-refactor/README.md, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-florence-duplicates-visible.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-confirmation-florence-tuscany-label.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-tuscany-label-cancelled.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-confirmation-florence-alabama-label.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-alabama-label-cancelled.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-alabama-back-cancelled.png, .codex/test-artifacts/118-wx29-location-search-refactor/wx29-3-final-main-loaded-displaylabel-build.png.

## Verification actually performed

- python scripts/dev.py workflow: passed at EXECUTE resume, after board reconciliation, after WX.29.3 completion, and at CLOSEOUT.
- python scripts/dev.py contract: passed; no new device-location/background permission or unapproved transport.
- python scripts/dev.py build: passed after full displayLabel confirmation change.
- python scripts/dev.py run --serial 8AKY0M32X: passed; streamed install succeeded and MainActivity launched.
- python scripts/dev.py screenshot --serial 8AKY0M32X --output <listed paths>: passed for installed acceptance captures, including both duplicate Florence labels and cancellation outcomes.
- python scripts/dev.py lint: failed with 4 errors, 16 warnings, and 1 hint; errors are NonObservableLocale in CatalogTimeLabScreen.kt:139 and StateFlowValueCalledInComposition in CatalogPickerDelegate.kt:146, :152, and :204; none in LocationSelectorPanel.kt.
- git diff --check: passed; final scoped diff reviewed.
- No automated tests were added or run, per repository instructions.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/118-wx29-location-search-refactor/`. Archived plan: `.codex/history/plans/118-wx29-location-search-refactor.md`. See the plan for stage checkpoints and exact boundaries.
