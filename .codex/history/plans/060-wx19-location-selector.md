# Cycle plan — 060-wx19-location-selector

State: ACTIVE
Cycle ID: `060-wx19-location-selector`
Roadmap: WX.19
Objective: Build the accessible manual location search and saved-location selector using the approved selector-open design.

## Status, authority, and design input

- WX.19 is the sole `NEXT` slice. WX.17 and WX.18 are recorded DONE in the roadmap; `.codex/current.md` was IDLE before this plan was created.
- The product owner approved the selector-open concept on 2026-10-28: `.codex/concepts/WX.19-location-selector.png` and editable `.svg`. It depicts a compact locations panel over the immersive timeline, search results with region/country/timezone context, saved locations, explicit selection, separate Select and Remove actions, removal confirmation, distinct loading/no-match/failure states, and accessible labels/focus order. It is design input, not installed acceptance evidence.
- The concept and roadmap explicitly leave the **main-screen access route** owner-planned. This cycle does not choose or implement that product route. A debug-only invocation may be used to review the implemented panel on the installed app, provided it does not introduce a release-visible entry point. If this cannot provide honest installed interaction evidence without deciding the product route, stop before implementation and record the missing owner-approved route concept as a blocker.
- WX.18 deliberately makes add independent of selection. Removing a saved location also deletes that location's catalog records from every source namespace; require an explicit confirmation before invoking removal.
- The selector may persist a selected location ID, but weather loading/activation is WX.20. Selecting a location in this slice must not imply that the displayed `SampleForecast` belongs to it or has been loaded for it.

## Verified baseline and architecture

- `PlaceSearcher` and provider-neutral `PlaceSearchCandidate` / `PlaceSearchResult` are in `app/src/main/java/com/oxygen/weather/timeline/data/catalog/PlaceSearch.kt`. Search candidates contain canonical GeoNames identity, descriptive location context, IANA timezone, coordinates, and Open-Meteo attribution; search itself does not load weather.
- `OpenMeteoPlaceSearcher` is an isolated adapter in `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoPlaceSearcher.kt`. It handles bounded requests and explicit no-match/failure outcomes and propagates cancellation. There is no current caller from UI.
- `SavedLocationRepository` in `data/catalog/SavedLocations.kt` supports add, list, find, select, remove, and restore with typed outcomes. It uses `CatalogStorageDatabase`; selected state is nullable. Existing behavior preserves the first saved weather point for a canonical ID and reports a conflicting point instead of overwriting it.
- `MainActivity` currently constructs the prototype's `TimeLabScreen` directly. It has no application-level database/repository wiring. The manifest declares only `MainActivity`; there is no navigation framework or location-selector screen. `TimeLabScreen` owns selected-hour/precision state, so opening and closing an overlay must preserve the same instance and selection.
- Existing debug review patterns are available: `MainActivity` gates intent-driven previews with `ApplicationInfo.FLAG_DEBUGGABLE`, and `app/src/debug/AndroidManifest.xml` exposes `DebugForecastActivity` only in the debug source set. `docs/UI_DEVELOPMENT_WORKFLOW.md` documents launching that activity directly for installed review. These are validation precedents, not authorization to add a release-visible location route.
- The working tree contains uncommitted WX.18 source, schema, evidence, history, and roadmap changes, with the WX.18 plan moved to history. They are the current dependency baseline and are outside this cycle's changes; inspect and preserve them at activation.
- Approved contract and user direction leave the main-screen route for later design. This plan assumes the selector component can be reviewed through a debug-only route without establishing the product route; feasibility must be checked before activation.

## Production boundary

Expected change areas, subject to baseline inspection at activation:

- `app/src/main/java/com/oxygen/weather/timeline/ui/` — a focused Compose selector panel and its accessible search/results/saved-location presentation; narrow `TimeLabScreen` composition only if required to preserve the underlying timeline and show/dismiss the panel.
- `app/src/main/java/com/oxygen/weather/timeline/` — minimal repository/searcher construction and debug-only selector invocation needed for real installed review. Keep release UI free of an unapproved entry route. Prefer an existing app-owned lifecycle boundary if one is introduced by then; do not add a general navigation framework or dependency-injection system for this slice.
- Focused UI/state tests may be added only with explicit test authorization. The roadmap still requires evidence for the named flows; do not substitute a compile or mock-only preview for installed interaction evidence.
- `.codex/test-artifacts/060-wx19-location-selector/`, this plan, and on closeout the matching history, archived plan, and WX.19 roadmap evidence/status.

The UI consumes provider-neutral search and saved-location types. Provider DTOs, database rows, catalog source selection, and weather-renderer branches must not enter the composable UI. Do not add device-location permissions.

## Search, saved-state, and action contracts

- Search runs only on explicit user submission (Search action or keyboard submit); editing the query alone does not issue provider requests. An empty/unsubmitted query is idle. Submission moves through loading to matches, no matches, or request failure. Keep each displayed result state associated with its submitted query so results from different queries are never presented as current.
- The latest submitted query owns visible search state. A newer submission, query edit, or panel dismissal invalidates prior work. Editing clears results/errors for the previous submitted query and returns the edited query to idle until explicit submission. Propagate coroutine cancellation as cancellation; do not render it as provider failure. A completed stale request must not publish results or errors. Retry resubmits the current failed query; editing permits recovery from invalid-query and provider failures.
- Render each `PlaceSearchCandidate` using its descriptive place/administrative/country context (`displayLabel` where suitable), timezone ID, and source-precision coordinates. Same-name candidates remain separate keyed choices; identity and Add use `canonicalPlaceId`, never a label or `sourceReference`. Show the candidate's supplied attribution source, license, URLs, and changes notice in a readable, discoverable form.
- On opening the selector, load the saved list and call `restore()` to obtain the persisted selection; publish them together as one refreshed UI snapshot only after both operations succeed. This restores selector state only: do not add app-startup weather restoration or catalog loading, which belong to later slices. `Selected(null)` means no selection. WX.18 clears a dangling selected ID during restore; malformed saved data or a storage exception is an error, not an empty-list success.
- Keep the last successfully loaded list and selection visible if a refresh fails, with an explicit recoverable error and retry. On first load, distinguish loading, valid empty state, malformed persistence, and repository/storage failure. Do not replace known state with an empty state after a failed refresh.
- Add accepts only the chosen candidate. `Added` and `AlreadySaved` leave selection unchanged and show the actual saved state. `MissingCanonicalId`, `PointConflict`, `MalformedRecord`, or a repository/storage exception must not be shown as a successful save; offer recovery and never overwrite the existing point.
- Select is available only for a saved `LocationId`. `Selected(location)` updates current-selection state; `MissingLocation`, `MalformedRecord`, or a repository/storage exception is an error and must not be represented as successful selection. Selecting does not call a weather source or change the displayed `SampleForecast`.
- Remove is separate from Select. Canceling confirmation makes no repository call and no state change. Confirming calls `remove` with the exact saved `LocationId`: `Removed` refreshes the list and selection; WX.18 clears selection when that ID was selected and preserves another selection otherwise. `NoMutation`, `MalformedRecord`, or a repository/storage exception is not reported as successful removal; refresh or retry without selecting a replacement.
- UI state uses provider-neutral `PlaceSearchCandidate`, `SavedLocation`, and typed result contracts. Never expose Room rows or provider DTOs to composables. Keep the existing DEMO DATA provenance and forecast values unchanged after any location selection.

## Functional and experience invariants

- The selector is an overlay/panel that coexists with the immersive timeline and does not replace or redesign it. Dismissing it returns to the same selected time, scale, and timeline position.
- Search is manual. It does not request GPS or location permission. Show the query and distinct idle, in-progress, results, no-matches, and request-failure states, with retry/edit recovery. Cancellation and superseded-query results must not replace the latest query's state.
- Same-name results remain separate choices. Show enough returned place/administrative/country context to distinguish them, plus timezone and precise coordinates as depicted. Display the supplied Open-Meteo/GeoNames attribution and license/change notice in a readable, discoverable way; do not treat provider references or labels as identity.
- Adding a result saves it by canonical identity and leaves current selection unchanged. Handle already-saved, missing-canonical-ID, conflicting-weather-point, malformed-record, and repository failure outcomes explicitly; never silently overwrite an existing saved point.
- Saved locations have a clear current-selection state. Select persists only the saved `LocationId`; it does not initiate weather loading. Remove is separate from Select and requires confirmation because WX.18 removes all catalog namespaces for that location. Removing the selected location leaves selection empty; do not silently select another location.
- Restored selection must match a saved canonical location. Empty saved state and nullable selection are valid. Errors must not be presented as a successful save, selection, or weather load.
- Search, result, Add, Select, Remove, confirmation, and Close controls have meaningful spoken labels/state, predictable focus order, and usable touch targets. Dynamic state changes are announced where appropriate. Larger text must not hide primary actions or make ambiguous locations indistinguishable.
- Preserve RTL meaning, system back/dismiss behavior, and reduced-motion behavior. The full cross-app TalkBack, font-scale, orientation, RTL, and reduced-motion matrix remains assigned to WX.29; this slice still verifies selector-specific semantics and direct interactions.
- Existing DEMO DATA provenance and timeline values remain truthful and unchanged while a location is selected, until WX.20/WX.26 establish weather activation/startup behavior.

## Ordered draft steps

1. At activation, inspect the latest full working tree and WX.18 files/schema, rerun `python scripts/dev.py workflow`, and confirm WX.17/WX.18 contracts and the approved concept remain applicable. Record unrelated pre-existing changes without absorbing or reverting them.
2. Resolve the implementation/test access seam without choosing a release-visible main-screen route. Use the existing `FLAG_DEBUGGABLE` intent-extra and debug-source-set patterns as precedents to establish a debug-only invocation that opens and dismisses the selector while keeping the same `TimeLabScreen` composition mounted. On the installed debug app, move the timeline to a known non-default selected time, open and dismiss the selector, reopen it, and confirm the same time, scale, and position remain. Confirm the invocation is absent or inert in release. If this cannot provide that evidence, stop before selector/controller implementation for the separately owner-approved entry-route concept; do not add a guessed product affordance.
3. Define a small UI state/controller seam around `PlaceSearcher` and `SavedLocationRepository` that implements the contracts above. Search runs only on explicit submission; cancellation, query edits, newer submissions, and dismissal invalidate old work. Keep typed outcomes and persistence independent of Compose rendering, keep network work off the main thread, and preserve distinct loading, empty, failure, retry, and success states. Restore saved list and selected ID when opening the selector, not as startup weather integration.
4. Implement the approved selector panel: query input; accessible search results that distinguish ambiguous places; save action; saved-location list with explicit current selection; separate Select and Remove actions; removal confirmation; close/dismiss; loading/no-results/failure/recovery states. Show accurate attribution. Do not connect selection to active-catalog loading.
5. Bind the UI to the existing repository and Open-Meteo search adapter at the narrowest app-owned construction boundary. Restore saved locations and current selection when opening the selector; refresh the view after add/select/remove; handle typed outcomes and repository/storage failures without corrupting displayed state. Do not add startup weather restoration, alter the canonical schema, or change the WX.17 provider request contract absent a separately approved blocker resolution.
6. Verify focused flows: explicit submission drives search; duplicate-name choice remains distinguishable by returned context, timezone, and coordinates; attribution is discoverable; add does not select; `AlreadySaved` is accurate; select persists and restores; selected-location removal requires confirmation and clears selection; canceling removal makes no repository call and mutates nothing; removing an unselected location preserves selection; and no location action changes or relabels the demo forecast. Verify idle/empty/loading/results/no-match/failure/retry states, cancellation and stale-response suppression after edit/new submission/dismissal, malformed persistence, storage/repository failure, missing canonical ID, add point conflict, missing selection target, and remove no-mutation outcome as honest recoverable UI. Do not make live network success a prerequisite; use deterministic search results for repeatable flow evidence.
7. Run only validations authorized for the execution cycle. Capture actual installed before/after states and interact with the selector on the Pixel 3 XL reference device if available. Review default text and a larger-font selector state, control semantics/focus, dismiss-and-return selection continuity, and responsiveness/gesture ownership/readability of the underlying timeline. Record unavailable device or environment checks as limitations, not passes.
8. Inspect `git diff --check` and the complete cycle diff; preserve actual evidence and exact command results under the allocated directory. Close WX.19 only when every roadmap exit condition is evidenced; otherwise record the exact gap and leave the outcome open/blocked as applicable.

## Acceptance and evidence

Roadmap exit requires focused flow evidence for searching, saving, selecting, deleting, and restoring without location permission; semantics that identify each selector action and current selection; and unambiguous selected-location identity. The installed review must additionally show the real selector state against the real app, not only a concept composite or preview.

Planned verification after an active plan is authorized:

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- Focused selector/controller tests and Compose semantics/interaction checks, **only if separately authorized under AGENTS.md**. Determine the exact focused Gradle target after inspecting the implementation and existing test setup; no broad suite by default.
- `python scripts/dev.py build`
- `python scripts/dev.py run --serial 8AKY0M32X` and `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/060-wx19-location-selector/<state>.png` if the reference Pixel is available and authorized.
- Installed checks: open and close selector while preserving timeline selection; exercise search/results/add/select/remove/confirmation/restore using deterministic results; inspect spoken control names/focus through available semantics; review the selector at default and larger text; record active viewport, font scale, locale/layout direction, and motion setting for captures.
- `git diff --check` and complete final diff review.

No tests, build, install, screenshot, or UI implementation have been run for this plan draft. The owner must authorize any test addition or test execution before those actions, consistent with `AGENTS.md`. Evidence targets:

- Plan: `.codex/plans/060-wx19-location-selector.md`
- Evidence: `.codex/test-artifacts/060-wx19-location-selector/README.md` and actual captures/logs
- Closeout: `.codex/history/060-wx19-location-selector.md`
- Archived plan after closure: `.codex/history/plans/060-wx19-location-selector.md`

## Material boundary and risks

- **Main-screen route remains undecided and out of this cycle.** The roadmap and approved board say it must be designed and approved separately before UI implementation of that route. Existing debug patterns make a debug-only same-composition invocation a repository-supported approach, but installed open/dismiss/reopen continuity and release gating still must be proven before selector implementation. If that fails, the owner-approved product-route concept is a prerequisite. Do not invent a product entry point.
- WX.18 removal also deletes all stored catalogs for a location. A missed confirmation or wrong identity could remove the wrong local weather history; test confirmation cancel/confirm paths and exact canonical ID handling.
- Search result requests can complete out of order or be canceled while a query changes or the panel closes. The latest submitted query owns visible state; edits, resubmission, and dismissal invalidate prior work, and cancellation is not shown as provider failure.
- Open-Meteo terms/attribution and provider behavior can change. Recheck current primary documentation if implementation changes or displays provider/licensing details beyond the fields already supplied by WX.17.
- UI selection remains separate from catalog activation. Do not imply the selected place's weather is active; that lifecycle and request races belong to WX.20, and normal startup integration belongs to WX.26.
- The working tree was already dirty from WX.18 at planning time. Those source/schema/roadmap/history changes are dependency baseline, not WX.19 output.

## Difficulty and model fit

Estimated implementation difficulty: **7/10**. The UI surface is one compact panel, but it coordinates asynchronous provider search, Room-backed saved state, destructive removal semantics, accessibility, and installed interaction evidence. Route separation adds a validation constraint. For relative model fit only, `gpt-6.1-sol` is the least costly available model with a credible path to the implementation and acceptance evidence. Use `gpt-6-astra` if the debug-only validation seam conflicts with lifecycle/navigation or resolving that conflict requires product-scope decisions. No runtime price comparison is claimed.

## Out of scope

Main-screen entry-route design or release-visible navigation; active catalog fetch/activation, data-source hot swap, location-aware weather presentation, startup/refresh/cache policy; GPS, foreground/background location permission/tracking; changing search provider behavior or canonical location identity; official alerts; timeline or atmosphere redesign; broad navigation/dependency-injection architecture; full cross-app accessibility/environment suite assigned to WX.29; production startup and integration assigned to WX.26/WX.28; deprecated oxygenWX files.

## Execution record — 2026-10-28

Implemented the planned provider-neutral Compose selector and controller, bound it to the saved-location repository and search adapter, and added a debug-only installed invocation with deterministic fixtures. The same `TimeLabScreen` instance remains mounted. The release build and contract check confirmed that the debug invocation and fixture source are not exposed in release. No location permission or weather activation was added. The pre-existing WX.18 worktree was preserved as recorded in `baseline-status-at-activation.txt`.

The installed review initially found a timeline swipe passing through the panel and changing selected time. The full-screen dialog and input-blocking backdrop fix was verified with the same swipe: while the selector remained open, the timeline stayed at Sunday, October 25, 1 PM. `underlay-swipe-review.png` and `underlay-swipe-blocked.png` preserve before/after evidence. The evidence index in `.codex/test-artifacts/060-wx19-location-selector/README.md` maps installed states, XML semantics, build logs, and device settings.

Exact verification:

- `python scripts/dev.py workflow` — passed (`workflow.log`).
- `python scripts/dev.py contract` — passed (`contract.log`).
- `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.ui.LocationSelectorControllerTest` — passed; 9 tests, 0 failures (`focused-tests.log`).
- `python scripts/dev.py build` — passed (`debug-build.log`).
- `python scripts/dev.py run --serial 8AKY0M32X` — passed; debug app installed and launched on Pixel 3 XL (`device.txt`).
- `./gradlew :app:assembleRelease` — passed, including lint-vital (`release-build.log`).
- `git diff --check` — passed (`diff-check.log`).
- Release APK inspection confirmed no debug selector activity in the release manifest and no deterministic Springfield fixture strings in release DEX (`release-isolation.txt`).

Installed interaction covered explicit search, ambiguous results, add without selection, explicit selection, saved-state restoration after process restart, selected/unselected removal and cancel confirmation, Back/backdrop dismissal, same-time continuity, and a blocked underlay swipe. UI hierarchy captures verify distinct action and result semantics. Text scale 1.3 and reduced-motion settings were reviewed and then restored. TalkBack audio/traversal, RTL, landscape, and frame-time profiling were not run; broader accessibility and environment coverage remains assigned to WX.29. The owner-planned release entry route remains out of scope. No WX.19 exit gaps remain.
