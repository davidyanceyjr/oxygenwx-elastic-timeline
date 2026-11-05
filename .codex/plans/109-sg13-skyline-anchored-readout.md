# Cycle plan draft — SG.13 skyline-anchored readout

State: DRAFT — combined board approval pending
Roadmap: SG.13
Proposed cycle ID: 109-sg13-skyline-anchored-readout (assign at finalization)
Objective: Move the complete central forecast readout upward until the Chicago text baseline aligns with the top edge of the tallest skyline tower.

## Readiness and approval gate

The owner requested the skyline alignment on 2026-11-05. The current approved SG.12 boards do not define this new vertical anchor. A new combined behavior/UI artwork board is drafted at `.codex/concepts/SG.13-skyline-anchored-center-readout.svg`, with PNG preview and current clean Pixel reference image alongside. Owner approval is pending. This draft is not final or eligible for activation until the owner approves the board and its constraints. `.codex/current.md` remains IDLE. No application changes are authorized under this unapproved draft.

## Production boundary

After board approval, change only `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` for central readout vertical placement. Add installed evidence and closeout under `.codex/test-artifacts/109-sg13-skyline-anchored-readout/` and `.codex/history/109-sg13-skyline-anchored-readout.md`. Update the SG.13 plan, roadmap and archive. Do not change symbol, forecast, timeline, lower indicators, picker behavior, typography, internal spacing, semantics, or gesture behavior.

## Functional invariants

- Translate the selected-location row and the complete associated central readout as one block: picker trigger, temperature/symbol pair, any source-time disclosure, Sun/Moon facts, and high/low.
- At the 411×846 dp Pixel 3 XL reference viewport, align the Chicago text baseline with the horizontal top edge of the tallest tower. The current screenshot suggests an additional upward shift of about 24 dp; tune the offset against installed evidence to meet the landmark alignment.
- Preserve internal order, spacing, alignment, scaling, values, and merged accessibility semantics.
- Keep lower precipitation/wind indicators, blank center slot, atmospheric scene, timeline and their positions unchanged.
- Preserve selected-time authority, picker action, and existing tap/swipe gesture meaning.

## Ordered steps

1. After owner board approval, record the approval and constraints here; finalize this scope and activate the cycle.
2. Inspect current main-screen layout and capture baseline regular and compact states on the Pixel.
3. Apply one vertical translation to the complete central readout group; avoid moving independent lower/timeline content.
4. Build/install the app. Capture regular 411×846 dp and compact 360×800 dp states. Confirm Chicago baseline meets the tower cap in the regular state and the group remains clear in compact.
5. Check selected-time and picker behavior plus center swipe/tap gesture ownership in the shifted position. Inspect values/readability, `git diff --check`, and the final scoped diff. No automated tests unless separately authorized.
6. Record screenshots, exact commands, observations and limits; close the plan only when the approved anchor and affected interactions are evidenced.

## Acceptance and evidence

Installed Pixel 3 XL captures show the location row baseline aligned to the tallest tower cap at the regular reference viewport. The central readout's internal hierarchy remains intact and has moved as one block. In the compact viewport it stays legible, does not collide with lower indicators or the timeline, and retains the same group relationships. Focused picker and readout gesture checks preserve existing behavior. Record actual captures and PASS/FAIL/NOT RUN results in `.codex/test-artifacts/109-sg13-skyline-anchored-readout/README.md`.

## Board package

Combined behavior and UI artwork board: `.codex/concepts/SG.13-skyline-anchored-center-readout.svg` (PNG preview `.codex/concepts/SG.13-skyline-anchored-center-readout.png`), with the clean installed current-state image `.codex/concepts/SG.13-current-main-screen-reference.png` and explanatory contract `.codex/concepts/SG.13-skyline-anchored-center-readout.md`. Owner approval is pending. The board defines the baseline-to-tower-cap relationship, one-block movement, unchanged internal layout and facts, and fixed lower row/timeline.

## Verification limits and exclusions

No application changes, builds, tests, or installed after-state checks have been performed for this draft. No new weather behavior, responsive redesign beyond checking the compact affected layout, background-scene changes, lower-row changes, content changes, animation, or broad accessibility/system matrix are in scope.
