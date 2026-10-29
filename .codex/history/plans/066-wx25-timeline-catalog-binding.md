# Cycle plan — 066-wx25-timeline-catalog-binding

Date: 2026-10-29
State: ACTIVE — reviewed; not activated
Roadmap: WX.25
Objective: Bind the immersive timeline and forecast browser to the active 44-day catalog

## Owner-approved concept and constraints

**Approved concept (2026-10-29):** `.codex/concepts/WX.25-timeline-44-days.png`, editable source `.codex/concepts/WX.25-timeline-44-days.svg`, based on the unaltered installed Pixel 3 XL capture `.codex/concepts/WX.25-pixel-3-xl-current-screen.png`.

Approval retains the current full-screen scene and information hierarchy while connecting its tape, central forecast facts, and accessible browser to the same active catalog and exact selected `Instant`. The visible date window is D-30 through D+13: 30 historical local dates and 14 forecast local dates including today. History is labeled as archived model forecast when that is the product meaning; today/future are identified as forecast, with origin, freshness, coverage, and missing values exposed honestly. Sparse fact samples must not move the selected instant. Browser day/hour selection and tape selection share one state. The board does not authorize startup/location flow, official alerts, or ATM.8–ATM.10 scene polish. It is design input, not installed acceptance evidence.

**Approved integration boundary (2026-10-29):** The active catalog is the location's weather dataset. `CatalogActivationCoordinator` owns both the published whole dataset and the exact selected `Instant`; tape and browser actions report `Instant` selections to that owner. `MainActivity` observes the coordinator and supplies a derived, immutable presentation view to the catalog-backed screen. The view contains display-ready dates, actual fact times, forecast facts and labels, selection, and activation status; Compose does not consume recording, storage, or provider objects. Preserve the fixture route and its existing callers. On dataset replacement, preserve an in-range instant or clamp to the new window; initial selection uses the applicable explicit target clamped to the window. Neither path snaps to a fact sample. This approval resolves the integration boundary; it does not approve unrelated activation, startup, scene, or UI changes.

**Owner execution steering (2026-10-29):** Prioritize verified numeric values, time, provenance, coverage, and missing-versus-zero semantics. The visual concept remains a prototype; record scene rough edges for later UI slices. A scene state may be neutral when a fact is unavailable, but it must not depict unsupported rain, falling snow, or storm conditions. The installed scene-truth and readability checks in this plan still receive actual dispositions rather than assumed passes.

## Verified baseline

- `MainActivity` owns `CatalogActivationCoordinator`, but currently calls `TimeLabScreen` without the active selection. `TimeLabScreen` still renders `ForecastSource.sample`, stores a fixture hour offset, and presents the fixture forecast browser.
- `CatalogActivationState.active` provides an `ActiveCatalogSelection` containing the validated `CatalogRecording`, origin/dataset identity, delivery/freshness metadata, exact selected instant, and view generation. Activation publishes the selected catalog as one state update.
- `CatalogActivationCoordinator.chooseSelectedInstant` currently chooses the nearest catalog fact, including on replacement. That conflicts with WX.24's preserve-or-clamp policy and this slice's exact-selection requirement; correct this selection policy within the coordinator while retaining whole-dataset publication.
- `CatalogWindow.create` builds 44 local dates from D-30 through D+13 using the catalog location timezone and `asOf`; `CanonicalForecastProjection.project` keeps the selected instant separate from the nearest same-date fact sample and returns provenance, product meaning, measurements, and decorative scene endpoints.
- `InstantTimeAxis` already provides absolute-time projection, local-day shifting, noon overview anchors, support clamping, and source-swap selection preservation. `ElasticTimeAxis` remains used by the current UI and fixture compatibility route.
- `ElasticForecastTimeline` owns tape gestures and screen-reader scale/browse actions. `AccessibleForecastBrowser` currently creates fixture-based rows. `AtmosphericScene` is currently fed fixture projection output by `TimeLabScreen`.
- WX.25 requires installed Pixel 3 XL review across history/today/forecast, dataset changes, accessible day/hour browsing, visual truth, responsiveness, gesture arbitration, and readability. WX.29 owns cross-app font-scale, orientation, RTL, TalkBack, and reduced-motion checks.
- Workspace already contains an uncommitted WX.25 roadmap concept-approval update and the three new storyboard/reference assets. Preserve those edits; this plan records the same approval and constraints without rewriting the roadmap.

## Production boundary

Expected implementation area, to confirm against call sites before editing:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/CatalogActivationCoordinator.kt` — add the narrow exact-selection update and replace nearest-fact selection with WX.24 preserve-or-clamp behavior. Keep request sequencing, validation, loading, and atomic dataset publication intact.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` — observe activation state and pass a derived presentation view plus an exact-`Instant` selection callback to the screen; do not keep a second catalog selection state.
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` — add a catalog-backed presentation route, keep the fixture route for previews/debug scenarios as required, use exact `Instant` selection and `InstantTimeAxis`, and project visible facts plus scene inputs from the active catalog.
- `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt` — bind date labels, bounds, selected position, history/today/forecast meaning, and gestures to the absolute catalog interval while retaining this component as gesture owner.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AccessibleForecastBrowser.kt` — expose day/hour entries from the same window and presentation projection, with selection state and source context matching the visual tape.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CanonicalForecastProjection.kt` and adjacent presentation code only if needed to provide display-ready provenance/freshness/coverage/missingness labels without UI dependence on provider or storage DTOs.
- Focused tests alongside the catalog, presentation, and UI layers may be added and run for exact selection, catalog-to-presentation projection, selection consistency, bounds, and fixture compatibility. The owner explicitly authorized focused scoped tests on 2026-10-29; this does not authorize a full suite.
- `.codex/test-artifacts/066-wx25-timeline-catalog-binding/` — actual focused-check logs, installed screenshots, and interaction/accessibility observations.
- `.codex/plans/066-wx25-timeline-catalog-binding.md`, `.codex/history/066-wx25-timeline-catalog-binding.md`, `.codex/history/plans/066-wx25-timeline-catalog-binding.md`, `.codex/current.md`, and the WX.25 status/evidence fields in `docs/ROADMAP.md` only for cycle records and closeout.

Do not change catalog/provider/storage contracts, activation request and publication policy beyond the specified selected-time correction, location selection or startup restoration, data acquisition, weather-scene design, or the deprecated oxygenWX application. Do not make source-specific UI branches: presentation differences must follow typed product meaning/provenance, not whether the origin is live, simulated, or replay.

## Resolved integration contract

- `CatalogActivationState.active` remains the single source of the active dataset and exact selected `Instant`. A tape, browser, or main-display selection sends an `Instant` to a narrow coordinator update. The update clamps it to the current catalog window, changes only the active selection, and does not start an activation request or advance the dataset `viewGeneration`. A callback from an older generation must not alter a newly published dataset.
- `CatalogWindow.create` uses the active recording's catalog, `expectedSupport`, and `dailyExpectedSupport`. Its 44 local dates define the selectable date window; adapt `startInclusive` through the last representable instant before `endExclusive` to `InstantTimeAxis`'s inclusive interval without admitting the next local date. Expected support and actual facts remain separate from this continuous navigation interval: an interval of selectable time does not imply an hourly observation or forecast exists at every instant.
- For a new dataset, use `InstantTimeAxis.preserveOrClampSelection` with the prior exact selection and the new interval. For first activation, use the applicable WX.24 explicit target (real now for LIVE, the debug scenario reference instant for DEBUG) and clamp it; the existing debug scenario's catalog `asOf` is its reference. `chooseSelectedInstant` must no longer search for the nearest fact. Source samples are selected only by `CanonicalForecastProjection`, which preserves `selectedInstant` and exposes the sample's separate `validTime`.
- The application/presentation boundary derives one immutable catalog view from the current activation snapshot. It carries the selected instant, local dates and actual fact instants, projected facts/scene inputs, source/product/freshness/coverage/missingness labels, and loading/error status as typed presentation values. It does not copy mutable selection into Compose or expose `CatalogRecording`, provider DTOs, or storage records to UI components. Keep the existing active dataset visible during a refresh when the coordinator retains it, with its loading/error status shown truthfully. With no active catalog, show the actual loading/empty/error state rather than fixture forecast facts; fixtures remain available only through their established preview/debug route.
- Both tape and accessible browser consume that same view and call the same exact-`Instant` selection path. Daily rows use `InstantTimeAxis.dailyOverviewAnchor` in the catalog timezone; hourly rows use actual fact `validTime`s. A tape selection between facts remains exact. The displayed nearest same-date sample keeps its own timestamp, and unsupported or missing facts remain unavailable. Retain existing fixture-facing screen/timeline/browser entry points and gesture ownership while adding the catalog route; any shared internal code must preserve their current behavior.

## Functional invariants

- One coordinator-owned exact `Instant` is authoritative across tape, central facts, and browser on the catalog route. Scale animation, viewport changes, sparse samples, and scene interpolation do not rewrite it. A displayed fact retains its actual sample `validTime`; never label a nearby sample as the selected instant.
- The active `CatalogWindow` controls the real supported interval and all 44 local dates. Navigation clamps to actual catalog bounds and never invents hourly support. Dates use the catalog timezone and local-calendar boundaries, including 23/25-hour dates.
- Dataset/source replacement preserves the selected instant when it remains in bounds and otherwise clamps using `InstantTimeAxis` policy. It does not snap to a sample. Catalog publication remains atomic through the existing activation coordinator.
- Daily overview selection uses the approved local-noon view anchor; hourly rows select an actual supported fact timestamp. Selecting a date/hour through the browser updates the same authoritative screen selection and is reflected by the tape and summary.
- Historical/future meaning comes from the typed product segment and catalog date window. Archived model forecast is never called a verified observation. Show provider/source identity, update/freshness and relevant coverage/missingness where the current screen/browser can expose them; missing values remain unavailable and are not rendered as zero.
- Preserve metric canonical values and perform display conversion only in presentation. UI consumes presentation types, not provider DTOs or storage records.
- Scene inputs remain decorative projections of supported facts. They must not imply unsupported rain/snow/storm/day-night facts or implement ATM.8–ATM.10 polish. Important forecast values stay visible as text.
- Retain full-screen atmosphere, existing timeline gesture ownership and meanings, accessibility scale/browse actions, and the established fixture/debug path. No new permanent mode row or source-specific presentation branch.
- Responsiveness, predictable gesture ownership, and readability are explicit acceptance checks for this visual integration.

## Dependency order

1. Reconfirm the approved storyboard and WX.22–WX.24 decisions; inspect active-catalog collection, window construction, presentation APIs, UI callers, and existing focused tests. Capture the connected Pixel 3 XL/build baseline needed to compare changes.
2. Correct the coordinator's nearest-fact selection policy to exact preserve-or-clamp behavior and add its narrow selection update. Derive the smallest immutable presentation view from the active recording/window and activation status; keep the coordinator as selection and activation owner.
3. Connect the catalog view, exact selection callback, and actual 44-date bounds to `TimeLabScreen` and the tape. Preserve the sample fixture route and gesture owner; establish correct labels for D-30, D, D+13, sparse periods, and source swap before adding browser rows.
4. Bind the central facts and atmospheric inputs to `CanonicalForecastProjection`; preserve selected/sample-time distinction, product meaning, provenance, missingness, canonical-unit conversion boundaries, and decorative-only interpolation.
5. Bind accessible day/hour rows to the same catalog/window/projection and selection callbacks. Provide actionable date/time, forecast meaning, source context, available facts, unavailable values, and selected state without assuming complete hourly coverage.
6. Add and run focused tests for the changed selection, projection, and compatibility behavior. Run workflow/contract and build, then install on the reference Pixel 3 XL and record the required boundary, switch, browser, scene-truth, responsiveness, gesture, and readability evidence. Inspect the final diff and `git diff --check` before closeout.

## Acceptance and verification

All required exit conditions must have actual evidence. Planned commands/checks:

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- Run focused unit tests for coordinator exact selection and source-swap behavior, canonical projection and selected-versus-fact time, absolute-axis integration, and any new pure view-state or fixture compatibility logic. Select exact Gradle test targets after confirming the implementation; record commands and results. Do not substitute a broad suite.
- `python scripts/dev.py build`
- `git diff --check`
- Install and review on the connected Pixel 3 XL. Record build/device identity and actual results in `.codex/test-artifacts/066-wx25-timeline-catalog-binding/README.md`; preserve screenshots of the installed app for history/today, forecast horizon, sparse/missing facts, and dataset switch states, plus interaction notes.

Acceptance scenarios:

1. Scrub and tap across D-30/history, the D boundary, and D+13. Date labels, historical/forecast meaning, supported bounds, and selected exact instant agree with the active catalog/window.
2. Select the same moments with the tape and browser. Both paths show the same selected `Instant`, same fact sample timestamp, values, source/provenance context, and missing fields. Day selection anchors at local noon; hour selection uses a catalog fact time.
3. Switch between representative live, simulated, and replay datasets through existing activation/debug paths. Selection is preserved in-range or clamped out-of-range, publication is whole-dataset, and source origin alone does not change presentation hierarchy.
4. Exercise sparse and missing periods and at least one local date with non-24-hour length where an available catalog supports it. Selection remains exact; absent facts and unsupported intervals remain unavailable; no zero/default or synthetic forecast interval is introduced. If no suitable catalog is available, mark the DST case NOT RUN with the dataset limitation; do not fabricate installed evidence.
5. Review representative clear/day-night/snow/storm inputs and transitions. Scene follows supported facts and selected time without claiming unsupported precision or implementing the separate ATM polish outcomes; important facts remain readable.
6. Verify timeline responsiveness, tap/hold/drag arbitration, browser-to-tape selection, and readability in bright/dark scene states. Record pass/fail/not-run separately; build success is not visual acceptance.
7. Verify the browser's day/hour entries and custom scale/browse route with an installed accessibility service, recording the actions and spoken date/time, product meaning, fact time, source context, missing-value state, and selected state. Record the Pixel 3 XL default portrait viewport/font scale and review the changed tape, summary, and browser at 1.3× font scale, compact or landscape viewport, RTL, and reduced motion. Give each condition a PASS/FAIL/NOT RUN disposition and reason, with installed captures for visual states. The broader cross-app matrix remains assigned to WX.29.

Do not claim acceptance for a state that lacks installed evidence. Preserve actual failures and limitations; if a required installed check cannot run, close as incomplete/blocked according to the recorded cause rather than inferring a pass.

## Risks and assumptions

- `MainActivity` can observe the existing coordinator `StateFlow`; the integration must derive the UI view from that snapshot without a second selection owner. If the current call sites prevent this within the bounded slice, record the concrete conflict for a narrowly scoped owner decision.
- `CatalogRecording` contains the catalog, `expectedSupport`, and `dailyExpectedSupport` needed by `CatalogWindow.create`; derive the window from those fields and avoid recalculating contradictory coverage in UI.
- The current window is 44 local dates but does not guarantee 1,056 hourly values. Timeline density/labels and browser rows must represent supported facts and missing support honestly.
- Exact selection can fall between source samples. The nearest same-date fact is a source fact only; distinguish its valid time from selection in text/accessibility semantics.
- Current atmospheric renderer accepts fixture-oriented presentation values. Mapping canonical typed states may reveal a missing narrow presentation contract; do not broaden this into atmosphere-engine redesign or unsupported weather semantics.
- The UI currently uses hour-offset APIs and several saveable fixture states. A compatibility seam is needed so debug/preview callers remain valid while the production screen consumes absolute time.
- Device access or representative dataset states may be unavailable during execution. Record the observed limitation and do not equate preview/build output with installed acceptance.

## Difficulty and model fit

**Reviewed difficulty estimate: 9/10.** The change spans application state wiring, canonical projection, absolute-time timeline behavior, forecast browser semantics, atmospheric inputs, compatibility routes, and installed acceptance. The state ownership and selection policy are specified; cross-layer implementation and installed verification remain substantial.

**Prior first-draft model note; rerate before execution:** GPT-6.1 Sol was recommended as the least costly available model with a credible path through the cross-layer integration and device-evidence plan. GPT-6.1 Astra was the fallback for unresolved UI-state or presentation mapping conflicts or repeated cross-layer regressions. The UI-state boundary is now resolved, so this note is not a fresh model assessment or API pricing guidance.

## Out of scope

- Startup, saved-location restoration, location search/selection flow, permissions, cache or provider behavior, and catalog activation request/publication policy beyond the exact-selection correction above.
- Official alerts; inferred warning flags; fabricated observations or unsupported coverage; source-specific presentation branches.
- ATM.8–ATM.10 atmospheric scene polish, ENV packs, renderer redesign, or a new weather-effects subsystem.
- WX.29 application-wide accessibility/resilience matrix: broad TalkBack traversal, 1.3× font scale, orientation, RTL, and reduced-motion sweep. WX.25 still checks the browser route and the local installed states explicitly listed above.
- Changes to the deprecated oxygenWX app, its navigation, or production integration/release setup.
- Full-suite verification unless a separate approved verification plan requires it.

## Evidence paths

- Actual verification and installed evidence: `.codex/test-artifacts/066-wx25-timeline-catalog-binding/`
- Closeout: `.codex/history/066-wx25-timeline-catalog-binding.md`
- Archived plan after close: `.codex/history/plans/066-wx25-timeline-catalog-binding.md`
- Current plan: `.codex/plans/066-wx25-timeline-catalog-binding.md`

## Execution result

Implemented the coordinator-owned exact-selection update, immutable catalog presentation view, catalog-backed screen/tape/browser path, and fact-gated decorative scene mapping. The fixture route remains available. Workflow, contract, build, installed Pixel 3 XL baseline/boundary/source-switch captures, and a read-only source-recording value comparison were performed; details are in `.codex/test-artifacts/066-wx25-timeline-catalog-binding/README.md`.

The complete WX.25 exit was not met. Focused unit tests were not run under the user's explicit repository instruction, and the installed accessibility/browser, full visual-state, DST, and responsive matrix remained unverified. The cycle is closed as an incomplete attempt; WX.25 stays NEXT rather than DONE.
