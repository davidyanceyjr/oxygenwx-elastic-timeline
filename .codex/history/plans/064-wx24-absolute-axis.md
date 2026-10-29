# Cycle plan — 064-wx24-absolute-axis

Date: 2026-10-29
State: CLOSED INCOMPLETE
Roadmap: WX.24
Objective: Migrate the pure ElasticTimeAxis to absolute instants

## Owner-approved design input

**Owner-approved concept artifact (2026-10-29):** `.codex/concepts/WX.24-absolute-axis.png` (editable source: `.codex/concepts/WX.24-absolute-axis.svg`). Approval covers the absolute-instant axis, elapsed-duration spacing, zoom-independent selection, local-calendar navigation, DST gap/overlap handling, daily overview anchor, source-swap preservation/clamping, reset targets, and retained gesture ownership. The board is design input, not implementation or installed acceptance evidence.

**Approved constraints:**

- `Instant` is the sole selected-time authority. Coordinate mapping uses elapsed time; a one-hour interval is 3,600 elapsed seconds. Zoom or scale animation changes projection only and never rewrites selection.
- Use the location `ZoneId` to derive local dates, labels, and calendar-day boundaries. Moving between dates preserves the local wall-clock time when valid; it does not add or subtract a fixed 24 elapsed hours.
- Repeated local times represent distinct chronological instants and ambiguous labels include their UTC offset. On a day shift, retain the prior offset when it is valid for the target local time; if not, choose the earlier occurrence.
- A nonexistent local time in a spring-forward gap resolves to the first valid local time after the gap. Do not manufacture an instant for the missing wall-clock time.
- A daily overview anchor is local noon for the date, resolved with the same local-time rules. This is a view anchor, not an assumed forecast sample or a change to catalog day boundaries.
- Preserve selection across a source/dataset swap when the same instant is within the target supported interval. Otherwise clamp to the nearest interval endpoint; an equal-distance tie chooses the earlier instant. Do not snap to a weather sample.
- Reset targets are supplied explicitly: real clock `now` for LIVE and the scenario reference instant for DEBUG. Clamp the target to the active supported interval. The pure axis does not read a clock, inspect source mode, or own gesture state.
- Preserve existing gesture owners and meanings. No visual redesign or timeline/catalog UI integration is included.

**Owner-approved implementation boundary (2026-10-29):** Keep `ElasticTimeAxis` and its fixture hour-offset APIs unchanged. Add a separate, small absolute-time API in `presentation/InstantTimeAxis.kt`; do not route absolute instants through fractional 24-hour offsets. This keeps WX.24 additive and leaves the current UI/rendering path intact until WX.25.

**Owner-approved performance boundary (2026-10-29):** Keep coordinate projection to constant-time elapsed arithmetic relative to an anchor instant. Do not perform `ZoneId`/`ZonedDateTime` conversion or allocate `Duration` values in the repeated coordinate-mapping path. Use timezone rules only for calendar-day selection and overview-anchor operations. WX.24 makes no visual change or performance claim; responsiveness is measured when WX.25 integrates the new API.

## Verified baseline and boundary

- `ElasticTimeAxis` currently maps a `Double` hour offset through a compact-day/expanded-hour transform. Its `axis`, `inverse`, `xForHour`, `hourAtX`, `scrub`, and `dayIndex` APIs are used by `TimeLabScreen`, `ElasticForecastTimeline`, `AccessibleForecastBrowser`, and `ForecastProjection` through hour-offset compatibility paths.
- `ForecastProjection` and the installed prototype still consume fixture `ForecastSource` data; canonical catalog projection was added in WX.23 but is not connected to the timeline. WX.25 owns that integration.
- The focused current axis tests are in `app/src/test/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxisTest.kt`. Existing gesture and fixture interaction behavior is recorded in the prior cycle evidence; WX.24 must preserve it through the compatibility path.
- `python scripts/dev.py workflow` passed before this draft. The concept board is owner-approved as recorded above.

## Production boundary

Expected change area, to confirm during execution:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/InstantTimeAxis.kt` — new pure absolute-time mapping and calendar-selection API. Keep elapsed mapping and calendar resolution separate within this small boundary.
- `app/src/test/java/com/oxygen/weather/timeline/presentation/InstantTimeAxisTest.kt` — focused pure tests for absolute mapping, calendar boundaries, DST policies, clamping, sparse intervals, and reset/source-swap resolution.
- `app/src/test/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxisTest.kt` — retain the existing fixture compatibility checks unchanged; extend only if needed to confirm the legacy API remains stable.
- `.codex/test-artifacts/064-wx24-absolute-axis/` — actual command results and compatibility evidence if created during execution.

Do not change `ElasticTimeAxis.kt`, `TimeLabScreen`, `ElasticForecastTimeline`, `AccessibleForecastBrowser`, `ForecastProjection`, `ForecastSource`, catalog/provider contracts, atmospheric rendering, or gesture arbitration. If absolute-time semantics cannot be implemented in the separate pure API while preserving the existing fixture route unchanged, stop and record the smallest missing contract for a bounded owner decision.

## Functional invariants

- Mapping is monotonic over the supported absolute-time interval and invertible within numeric tolerance. Positions are computed from elapsed seconds relative to an anchor `Instant`, not local-hour arithmetic or fixed 24-hour day widths. The mapping path uses constant-time arithmetic and does not construct `Duration` or perform timezone conversion for each coordinate.
- `xForInstant(selectedInstant, selectedInstant, playheadX, ...)` remains the fixed playhead for every zoom/scale value. Zoom/scale is projection input only; changing only its value never mutates `selectedInstant`.
- Local dates and day boundaries are computed in the supplied location zone. Calendar-day navigation uses `LocalDate` arithmetic and the approved wall-time/DST resolution above; spring/fall dates naturally span 23/25 elapsed hours.
- Coordinate lookup and scrubbing clamp to the explicit continuous supported interval. The interval can contain sparse or missing forecast samples: selection remains an exact instant and is not rounded or snapped to an available sample.
- Source-swap selection retains an in-range instant, otherwise clamps to the only nearer endpoint. Reset resolution receives an explicit target instant and active interval; it performs no system-clock or source-mode lookup itself. For one continuous interval, an endpoint-distance tie cannot occur; do not add multi-range or nearest-sample machinery to exercise the board's deterministic earlier-tie policy.
- Daily overview anchor resolves to local noon. DST ambiguity is handled consistently with the approved resolver, even though noon is ordinarily valid.
- Preserve the existing hour-offset API as a compatibility route for fixture-backed callers. Current fixture presentation, selected-time behavior, axis bounds, gesture ownership, and established tap/hold/drag/release behavior do not change in this slice.
- This is a pure presentation change. No provider DTO, catalog mutation, UI state, Compose dependency, weather-sample interpolation, or visual redesign enters the axis contract.

## Dependency order

1. Recheck the approved board, WX.23 canonical temporal conventions, current `ElasticTimeAxis` call sites, and legacy interaction evidence. Confirm the supported absolute interval and API boundary before implementation.
2. Add `InstantTimeAxis` as an additive API without changing `ElasticTimeAxis`. Define only the pure elapsed-time mapping/inverse mapping, continuous-bound clamp, local-day shift, local-noon overview anchor, and explicit-target reset/source-swap helpers required by this slice. Keep clock access and LIVE/DEBUG choice outside the API.
3. Add the approved DST resolution policy: preserve valid local wall time when changing date; on overlaps retain the previous offset when possible, otherwise choose the earlier occurrence; on gaps advance to the first valid local time.
4. Keep narrowly scoped compatibility APIs for the current fixture presentation and callers. Avoid a UI call-site migration or changes to existing gesture ownership in this slice.
5. Add focused `InstantTimeAxisTest` coverage for the acceptance matrix below and retain the current `ElasticTimeAxisTest` expectations; run the authorized verification, preserve actual results under the cycle evidence directory, and inspect the full diff before closeout.

## Acceptance and verification

WX.24 is complete only when all roadmap exit conditions are evidenced:

- Pure tests prove monotonic and clamped coordinate mapping, inverse round trips, and no selected-instant jump across zoom/scale changes.
- Spring-forward and fall-back tests use the location zone and prove true 23/25-hour elapsed day spans, repeated-hour occurrence ordering/offset labeling policy, and missing-hour forward resolution.
- Day-shift tests preserve local wall-clock time where possible, use the approved overlap/gap policies, and do not use fixed 24-hour addition. Daily overview anchors resolve to local noon.
- Sparse-interval tests show navigation and coordinate lookup remain continuous through intervals without samples and do not snap the selected instant to a sample.
- Source-swap tests preserve an in-range instant and clamp out-of-range selections to the nearer endpoint. Reset tests distinguish explicit real-now and debug-reference targets and clamp both to bounds. The supported domain is one continuous interval, so an endpoint tie is unreachable; do not model disjoint ranges or snap to samples.
- Compatibility checks show the existing fixture-backed hour-offset route preserves prior mapping, clamping, selected-time behavior, and tap/hold/drag/release semantics. No UI gesture owner or visual layout changes are introduced.
- The coordinate hot path uses elapsed arithmetic relative to an anchor and avoids per-coordinate `ZoneId` conversion and `Duration` allocation. Calendar conversions occur only for explicit day-navigation/anchor operations.
- Run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, focused unit tests for both `InstantTimeAxisTest` and `ElasticTimeAxisTest` with `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.presentation.InstantTimeAxisTest' --tests 'com.oxygen.weather.timeline.presentation.ElasticTimeAxisTest'`, `python scripts/dev.py build`, and `git diff --check`; record exact results and limitations in `.codex/test-artifacts/064-wx24-absolute-axis/`.
- No installed visual/device acceptance is claimed or required for this pure axis slice. WX.25 owns installed timeline/catalog integration and visual acceptance.

The user requested execution of this plan, authorizing the prescribed focused tests. Use the direct focused Gradle command above rather than `python scripts/dev.py test`, which runs the full unit-test task.

## Risks and assumptions

- The board chooses deterministic local-time resolution: retain the prior offset when possible, otherwise earlier overlap occurrence; advance gaps to the first valid local time. Verify the implementation can express these policies without losing the original exact `Instant` selection.
- `ElasticTimeAxis` currently includes a local focused-day transform, while the adopted interaction specification describes a uniform hourly scale. This slice must preserve fixture-visible behavior through compatibility APIs; any need to revise the visible scale or its gesture semantics is a separate owner decision, not a reason to expand WX.24.
- The approved additive boundary avoids changing the existing focused-day fixture transform. Sparse forecast facts do not make the new elapsed-time interval discontinuous; samples remain presentation inputs owned by later integration.
- The board's earlier-endpoint tie rule is not exercised for the single continuous supported interval specified here: in-range selection is retained and out-of-range selection clamps to one endpoint. Keep this scope explicit rather than adding a more general availability-set abstraction.
- Existing `Double` hour offsets may represent fractional fixture times. The compatibility route must preserve its existing tolerances and bounds while the new absolute-time API uses `Instant` values.
- No installed acceptance is claimed because UI call sites and rendering remain outside this slice. Existing installed/interaction evidence is a regression reference, not new evidence for the absolute-time API.

## Out of scope

- Connecting `CanonicalCatalog`, `CatalogWindow`, or WX.23 projection to the UI or migrating fixture sources (WX.25 and later).
- Changing Compose state, screen layouts, displayed labels, accessibility browser, or gesture owners; adding a new visible control; broad RTL/font-scale/reduced-motion review.
- Changing atmospheric scene behavior, weather facts, catalog/provider schemas, cache/source activation, location selection, app startup, official alerts, or the deprecated oxygenWX application.
- Installed device/visual acceptance for a UI that this slice does not change.

## Evidence paths

- Actual verification: `.codex/test-artifacts/064-wx24-absolute-axis/`
- Closeout: `.codex/history/064-wx24-absolute-axis.md`
- Archived plan after cycle close: `.codex/history/plans/064-wx24-absolute-axis.md`

## Execution result

Implemented the additive `InstantTimeAxis` API and its seven focused tests. Workflow, contract, and build checks passed; the new absolute-axis suite passed. The required combined focused command failed only at the unchanged legacy day-label assertion in `ElasticTimeAxisTest.fixtureIsCompleteChronologicalAndPresentationIsSeparated`: expected `DAY OVERVIEW`, actual `DAY OVERVIEW · SELECTED 12 PM`. The plan requires preserving that assertion, so the roadmap records WX.24 as BLOCKED and not DONE until an owner-approved resolution is applied. Exact command results and limitations are recorded in `.codex/test-artifacts/064-wx24-absolute-axis/README.md` and associated logs.
