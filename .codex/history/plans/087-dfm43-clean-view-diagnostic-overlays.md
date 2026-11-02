# Cycle plan — 087-dfm43-clean-view-diagnostic-overlays

State: ACTIVE
Plan readiness: Activated; board gate satisfied.
Cycle ID: 087-dfm43-clean-view-diagnostic-overlays
Roadmap: DFM.4.3
Objective: Clean forecast view and diagnostic overlays
Depends on: DFM.4.2, DONE in cycle 086.
Plan date: 2026-11-02

## Authority and handoff

Use `docs/SPECIFICATION.md` as controlling prototype and semantic authority, alongside `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, and `.codex/current.md`.

DFM.4.3 follows the completed coordinator lifecycle in cycle 085 and debug command/session boundary in cycle 086. Cycle 084 is archived as an incomplete attempt; its DFM.4 requirements and owner decisions remain applicable only where they intersect this slice. Do not treat cycle 084's incomplete outcome as accepted DFM.4 work, repeat DFM.4.1/4.2, or absorb catalog storage/editor/replay work assigned to DFM.4.4–4.6. Inspect the current working tree and preserve unrelated changes. The pre-existing untracked `feh_145770_000001_SG.3-clear-cloud-light-transitions-v2.png` is outside this slice.

The DFM.4.3 baseline is the installed Pixel 3 XL capture at `.codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/pre-slice-main.png`, with capture metadata in the adjacent `README.md`. It is before-state evidence only.

## Approved slice boards

- **Behavior:** `.codex/concepts/DFM.4.3-clean-view-behavior-board.md` and its visual flow `.svg`.
- **UI artwork:** `.codex/concepts/DFM.4.3-clean-view-overlay-artwork-board.png`, editable `.svg`, and companion notes `.md`.
- **Approval:** the product owner approved both boards on 2026-11-01 as shown, with no additional constraints stated.
- **Relationship to scope and acceptance:** the behavior board defines clean-main content, independent overlay/session state, diagnostic groups, local preset boundaries, and the forecast-details route. The artwork board depicts clean LIVE, debug menu with overlay off, overlay on, and preset configuration, including upper-sky placement and retained scene/tape. Its schematic scene art does not authorize a scene redesign. These boards govern implementation and visual acceptance; they do not replace tests or installed evidence.

The board gate is satisfied. This plan is ACTIVE.

## Objective and boundary

Apply the approved clean default forecast composition and add optional debug-only diagnostics and named local display presets. Keep the weather scene, selected forecast instant, time tape, navigation, gesture ownership, and truthful source/product semantics intact.

Apply the same clean active-catalog composition to LIVE, SIMULATED, and REPLAY. Remove the experimental title/status treatment, Datasets action, inline source/freshness/error text, and secondary sample/precipitation detail rows shown for removal by the approved board. Preserve the selected date and time, temperature, supplied current condition, high/low, precipitation chance, wind meter, location and refresh actions, atmospheric scene, and time tape. Keep source/product identity truthful and reachable; synthetic preview remains explicitly DEMO DATA/synthetic and REPLAY is never represented as LIVE through the existing forecast-details and approved debug-diagnostic affordances.

Source/product, source identity, delivery/freshness/stale/offline state, and applicable refresh/load errors remain reachable through the existing accessible time-tape “Browse forecast values” route. The active-catalog browser already presents product/source/status and selected sample time; it currently does not display `CatalogTimelineView.error`. Add any directly applicable hidden error detail there without changing its hourly/daily selection semantics. Preserve the separate no-active-catalog loading/error and retry surface.

In a debug session, add independently controlled diagnostic visibility and saved local presets containing display-group choices only. Overlay visibility starts off on every session entry and is turned off at session exit. Preset selection/configuration changes groups only and never enables the overlay, changes the forecast/source/location/selected instant, or triggers activation. Render groups only in the debug build. Do not add developer command names, parsing, or raw diagnostic rendering to shared main-source paths.

The first overlay iteration is a live, read-only, on-screen diagnostic log. Keep it text-first and compact: show the values currently available to the app for (a) catalog origin/product/source identity and delivery/freshness/loading/error state; (b) authoritative selected instant, the fact sample's valid time, and any declared point or interval support bounds available for that selected sample; and (c) a concise list of canonical fields unavailable at that sample. Label the panel and its entries as diagnostics. Render absent information as `unavailable` (or an equivalent explicit label), never as zero, an empty-but-ambiguous value, or a forecast fact. This first iteration logs the current state in the visible debug overlay; it does not add file, Logcat, network, or historical event logging. The same information remains reachable through the forecast browser where required below. Later expansion may add richer formatting or diagnostic fields, but is outside this cycle.

## Approved invariants and constraints

- Keep forecast facts and categorical conditions as supplied. Missing/unavailable values remain unavailable; diagnostic rendering does not fill, reinterpret, or infer them. Source/provenance fields are labeled as diagnostics, not as forecast facts.
- Diagnostic groups cover: (1) catalog origin plus product/source identity and delivery/freshness/loading/error state; (2) authoritative selected instant, fact-sample valid time, and available declared point/interval support bounds for the selected sample; (3) a concise missing-field summary for that sample. Show these as a live, read-only, text-first on-screen diagnostic log. Derive them from existing typed catalog/presentation inputs, not provider DTOs. An unavailable identity, status, sample, bound, or field value is explicitly labeled unavailable; do not substitute zero, infer a value, or imply historical logging.
- The missing-field summary is based on the selected canonical sample and its declared expected support. Name only fields the canonical representation establishes as unavailable at that sample; do not infer missingness from unrelated dates, provider payload shape, or presentation formatting. Preserve the distinction between a supported value of zero and an unavailable value.
- The selected-time row reports the authoritative selected instant separately from the fact sample's own valid time. Report support only when the canonical expected-support representation supplies the selected sample's point or interval bounds; otherwise say unavailable. Do not manufacture interval bounds for point samples or extrapolate bounds across samples.
- **Canonical diagnostic mapping (resolved before implementation):** Use `CanonicalForecastPresentation.factSample` / `CanonicalHourlyPresentation` from `CanonicalForecastProjection` for the sample chosen by the existing projection, and `CatalogDay.expectedFieldSupport()` plus `CatalogField` for declared hourly expectations. Do not select a different sample for diagnostics: its `validTime` is the fact-sample time, while `selectedInstant` remains independently authoritative. For a point field, its declaration matches the sample only when `ExpectedFieldSupport.Points.instants` contains that exact `validTime`; its declared support is that instant. For an accumulated field, its declaration matches only when `ExpectedFieldSupport.Intervals.intervals` contains the exact interval carried by that field in the sample and the interval's `end` equals the sample `validTime`; its declared support is that interval. List a field as unavailable only when its declaration matches the sample and that field is null in `CanonicalWeatherFacts`; use the canonical field's actual nullable/value representation, so numeric zero is present. Do not classify fields with no matching declaration as missing. Report only the matched declaration(s) as sample support, labeled by field and point/interval; if there is no sample or no matching declaration, report support as unavailable. `CatalogDay.fieldCoverage` is day-level and must not be used as a substitute for sample-level missingness. This mapping uses existing canonical types and requires no schema, provider, or cache change.
- Keep diagnostic panels in the approved upper-sky area. Do not cover the location/refresh actions, selected date/time, temperature/condition, high/low, precipitation chance, wind, or time tape. Overlay on/off must leave the underlying forecast and selected instant unchanged.
- Persist named preset names and group choices in app-private, debug-only local preferences. Do not use the forecast database, change its schema, sync externally, or persist the per-session overlay-visible flag. Presets are display configuration only.
- Preserve existing debug-session entry/exit and restore-before-ordinary-action lifecycle owned by DFM.4.2. Selecting, editing, and applying presets perform no catalog activation, provider/cache/location mutation, or selected-time change.
- Keep the default debug session and all debug-only UI out of release. DFM.4.6 owns integrated release acceptance.
- Do not change the canonical schema, forecast field semantics, source/cache policy, scene generation or artwork, time-axis projection, selected-time authority, timeline gestures, or broad accessibility/system behavior.

### Resolved shared seam and state ownership

- **Lifecycle owner (confirmed from current DFM.4.2 implementation):** `CatalogPickerDelegate.enterDebugSession` is the session-entry hook; `restoreAndEndSession` is the common exit path used by explicit exit and `beforeOrdinaryAction`. Keep overlay visibility as debug-delegate-owned, non-persisted session state. Set it false on entry and before clearing/restoring the checkpoint on every exit path. Do not change checkpoint, command-gating, request-cancellation, or restore ordering semantics. The delegate is recreated with the activity/variant lifecycle as it is today; visibility must start false for each newly entered session regardless of prior session state.
- **Rendering seam:** `MainActivity` already owns the active-catalog screen call and obtains the optional `ActivityVariantDelegate` through variant service loading. Add only the narrow typed inputs and optional generic composable content slot needed to place variant-provided content over `CatalogTimeLabScreen`. The debug delegate supplies the slot content; the release build has no registered provider and therefore supplies no content. Keep the screen's responsibility limited to placing supplied content in the approved overlay area and preserving the underlying composition.
- Keep the shared app seam small, typed, and variant-neutral. Shared code may carry the typed diagnostic presentation values and invoke/place an optional composable slot. It must not contain debug command names or parsing, diagnostic UI/rendering implementation, preset storage, overlay visibility state, or debug menu controls. Implement diagnostic rendering, visibility decisions, menu controls, and preset storage only in the debug source set. Do not introduce a release no-op renderer or debug-specific policy into shared source; absence of a variant provider is the release behavior.
- The optional slot is visible only when the debug delegate's session-local visibility is enabled. Hiding it must remove the overlay without changing forecast state, selected instant, or screen composition. Preset selection/configuration updates group choices only and does not alter slot visibility.
- Persist named presets separately as app-private debug-only display preferences. A preset contains diagnostic-group choices only. Selecting, saving, or updating one changes those choices without changing overlay visibility or any catalog, forecast, location, or selected-time state.

## Implementation files and boundaries

Expected primary paths, subject to preflight confirmation:

- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`: clean default composition, browser error detail, and a narrow content slot for overlay rendering.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt`: typed diagnostic projection for source/product/freshness, selected sample/support bounds, and missing fields, derived from canonical catalog structures.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` and `ActivityVariantDelegate.kt`: wire the narrow optional composable slot and typed diagnostic values from the loaded variant delegate to the catalog screen. Keep debug command dispatch, visibility/preset state, and diagnostic rendering implementation out of these shared files.
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/CatalogPickerDelegate.kt` and narrowly scoped debug-only UI/store files: session-local visibility state, menu controls, group configuration, and local named preset persistence.
- Directly affected focused JVM/instrumented tests, `.codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/`, this plan, and `docs/ROADMAP.md`.

The installed canonical-catalog screen owns its current forecast browser inside `CatalogTimeLabScreen.kt`; `AccessibleForecastBrowser.kt` belongs to the fixture screen path and is not the default catalog UI. Do not modify provider transport, catalog storage, scene composition/rendering, release source, or timeline gesture code unless preflight demonstrates a direct scoped dependency; if a schema, provider, cache, or scene change becomes necessary, stop and replan.

## Ordered work

1. **Preflight:** rerun `python3 scripts/dev.py workflow`; inspect status/diff, the approved boards, current screen/browser, typed projection and DFM.4.2 delegate. The lifecycle contract is settled: use `enterDebugSession` to initialize visibility off and `restoreAndEndSession` to force it off on exit, including `beforeOrdinaryAction`; retain existing restoration semantics. The rendering contract is settled: use the variant delegate to supply an optional generic composable slot plus typed diagnostic values to `CatalogTimeLabScreen`; absence of the provider yields no release overlay. Confirm the concrete slot can be wired through the current screen call without moving rendering/state into shared source; if repository evidence contradicts this boundary or requires changing DFM.4.2 restoration semantics, stop and replan. Planning-time availability check found Pixel 3 XL serial `8AKY0M32X` attached and an X window manager on explicit display `:0`; recheck both before use. The latest recorded Pixel viewport is 1440×2960, density 560, content viewport 1440×2733, font scale 1.0; verify these before device work. Host X preview is not installed acceptance. Preserve app data and all unrelated changes.
2. **Typed diagnostics:** define only the values needed by the approved groups. Inspect canonical support/missingness representations and project them through typed presentation values. Do not expose provider DTOs or introduce weather-data fields. Add focused checks for supplied, unavailable, and bounded samples.
3. **Clean main and detail route:** remove only the approved normal-view development/status elements and secondary rows. Keep all approved summary facts/actions and scene/timeline composition. Move applicable source/freshness/error detail to the existing accessible forecast browser and preserve browser selection behavior.
4. **Debug overlay and presets:** implement overlay visibility in the debug delegate as session-local state reset on entry and forced off on the common exit path. Supply the debug-only composable through the optional variant slot; use shared code only to pass typed diagnostics and place provided content. Add local named preset save/update/select for diagnostic groups only. Preset application changes the selected groups but does not turn on visibility. Release has no variant provider/content and renders no overlay.
5. **Scoped verification and installed review:** build the debug app; run only directly affected focused tests authorized for DFM.4; install and review the approved states/interactions on the reference Pixel. Compare screenshots with both approved boards, inspect readability and timeline/gesture behavior, and save actual captures and observations under the cycle evidence directory.
6. **Closeout preparation:** rerun the workflow validator, inspect `git diff --check` and the complete scoped diff, record pass/fail/NOT RUN results and limitations. Do not close DFM.4.3 unless its roadmap exit is fully met.

## Acceptance

All of the following must be true:

- The clean active-catalog screen for LIVE, SIMULATED, and REPLAY matches the approved main composition: date/time, temperature/current supplied condition, high/low, precipitation chance, wind, location/refresh actions, atmosphere and tape remain; approved development title/status, Datasets control, inline source diagnostics and secondary sample/amount rows are absent from this composition. Product/source identity remains available through the approved details/debug affordances; synthetic and replay provenance is never mislabeled as LIVE.
- Applicable product/source/delivery/freshness/stale/offline and refresh/load error details remain truthful and reachable through the accessible existing forecast browser. Browsing details preserves current selected-time and row-selection behavior.
- A fresh debug session shows the menu with overlay visibility off. Explicit visibility on shows only approved, clearly labeled diagnostics; off restores the exact clean view.
- Configuring/saving/updating/selecting a named local preset changes only diagnostic groups. Visibility remains independent; presets survive as local display choices without changing catalog or forecast state.
- The diagnostic groups correctly reflect current catalog/source, selected sample and any available support bounds, and missing fields. Unavailable values are named as unavailable and never represented as zero or as a supplied fact.
- With the debug overlay enabled, a compact on-screen diagnostic log visibly reports the current origin/product/source and delivery/freshness/loading/error state, selected instant, fact-sample valid time, available declared support bounds, and selected-sample unavailable fields. It updates with the current catalog and selected instant, is labeled as diagnostic, and does not write a separate log or alter forecast state.
- Clean main, overlay, preset and browser interactions preserve the selected instant, catalog activation, scene, forecast values, timeline responsiveness and gesture ownership. No DFM.4.2 restoration behavior regresses.
- Installed screenshots cover clean LIVE, debug menu/overlay off, overlay on, preset configuration, and the forecast-details route; observations record the device, build, viewport, density, font scale, selected source/time and directly affected interactions.

## Authorized verification and evidence

Use the existing DFM.4 authorization for focused tests only. Do not run an unfiltered test suite or broad `scripts/dev.py test/check` task.

- `python3 scripts/dev.py workflow` before work and at closeout.
- `python3 scripts/dev.py build` after app-code changes.
- Run directly affected focused tests with `./gradlew :app:testDebugUnitTest --tests '<fully-qualified-class-or-method>'`. Include tests for the typed diagnostic projection, debug preset persistence/session visibility, and affected lifecycle only where modified; record exact names and results. Add/run only the focused coverage needed for the approved contract.
- Verify/apply on the rechecked device with `python3 scripts/dev.py run --serial 8AKY0M32X`; capture using `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/087-dfm43-clean-view-diagnostic-overlays/<state>.png`. Reconfirm the serial before use. If unavailable, preserve the failure and leave installed acceptance NOT RUN.
- Required capture targets: `clean-live.png`, `debug-menu-overlay-off.png`, `diagnostic-overlay-on.png`, `preset-configuration.png`, and `forecast-details.png`. Keep `pre-slice-main.png` byte-identical as baseline.
- Record Pixel model/serial, display and content viewport, density, default font scale, installed variant/build, selected location/catalog/source/time, and the menu/overlay/preset/browser interactions inspected. No app data clear is expected; preserve user data. Any necessary app-only clearing remains limited to the prior explicit owner authorization and must be recorded.
- Review changed motion for reduced-motion impact only if this slice changes motion. RTL, TalkBack, broad accessibility/device/system matrices, and release runtime acceptance are out of scope; do not claim them. Release isolation remains assigned to DFM.4.6.
- Final scope checks: `git diff --check`, complete relevant diff review, and workflow validation. Record exact outputs and any unrun requirement in cycle history before closure.

## Closeout record

DFM.4.3 is complete on its scoped evidence. The clean LIVE view, debug overlay off/on, preset configuration, and active forecast-browser route were captured on the reference Pixel; focused projection/preset JVM tests and the MainActivity browser accessibility cases passed. `python3 scripts/dev.py workflow`, `contract`, `build`, and `git diff --check` passed. The full connected run had one unrelated `FirstCitySceneRenderingTest.selectedLocalTimeProducesStableRainAndStormCloudPoses` failure; the product owner directed that it be recorded as out of scope, with no investigation or fix in this cycle. Browser error detail was covered by the focused fixture; no installed LIVE error state was available. RTL, TalkBack, broad system acceptance, and release runtime acceptance remain outside DFM.4.3 (release acceptance belongs to DFM.4.6). Exact evidence is in the adjacent artifact README and cycle history.

## Risks and stop conditions

- The current typed view carries source, status, fact-sample label and error, but does not expose all support bounds or per-field missingness needed by the approved overlay. Extend only the presentation projection from existing canonical/catalog window data. If the requirements cannot be met without new provider/schema/cache semantics, stop and replan.
- Removing the main status row can hide a detail unless the browser exposes it. Check stale, offline, refresh/load error and source/product meaning against actual activation state; do not replace or summarize away information.
- Upper-sky overlays can compete with location controls and core facts on the Pixel viewport. Adjust placement within the approved artwork constraints, not by moving facts, scene art, or timeline.
- Preset persistence is assumed to be app-private debug-only display preference storage. It must not enter catalog Room storage or affect session restoration. If requirements imply forecast/session state or a shared schema, stop and replan.
- The existing debug menu lives in `CatalogPickerDelegate`; preserve DFM.4.2 command gating and exit-before-ordinary-action behavior while adding controls.

## Exclusions

- Catalog library/editor, SIMULATED catalog authoring, REPLAY snapshots, catalog storage changes and their preview lifecycle (DFM.4.4/4.5).
- New themes, city/sky/weather artwork, scene layering or renderer changes.
- Provider integration or mutation, cache/source policy, location behavior, forecast values/semantics, schema changes, or official alerts.
- Timeline scale/gesture/selection redesign, new navigation, broad system/accessibility acceptance, release runtime acceptance, or unrelated cleanup.
