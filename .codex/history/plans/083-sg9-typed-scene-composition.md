# Cycle plan — 083-sg9-typed-scene-composition

State: ACTIVE
Cycle ID: `083-sg9-typed-scene-composition`
Roadmap: SG.9
Objective: Refactor the catalog scene path to produce one typed, support-aware selected-time reading and one coherent first-theme composition while preserving approved visual meaning and current scrubbing behavior.

## Readiness and approved boards

- `.codex/current.md` was IDLE; cycle 082 is the latest closed cycle, so 083 is the next chronological cycle. SG.9 is the sole NEXT slice. Its SG.4 and SG.4a dependencies are DONE.
- `python scripts/dev.py workflow` reported `Workflow scaffold: OK` before planning.
- **Behavior board — product-owner approved 2026-11-01:** `.codex/concepts/SG.9-typed-scene-composition-flow.png` (editable `.svg`) and supporting contract `.codex/concepts/SG.9-typed-scene-composition-contract.md`. It governs canonical catalog + selected `Instant` → one typed support-aware reading → one first-theme composition → renderer draws the result. It requires distinct chance/amount/rate support, preserves point/interval source bounds, keeps probability-only and unknown distinct from `CLEAR` and event readings, and excludes string parsing or a renderer-selected nearest-sample weather value as semantic sources.
- **UI artwork board — product-owner approved 2026-11-01:** `.codex/concepts/SG.9-scene-composition-states.png` (editable `.svg`). It governs focused CLEAR, rain, snow, storm, probability-only, and unknown before/after states, with day/night where applicable; preserve the approved visual meaning and readable forecast facts.
- **Owner-approved conflict mapping, 2026-11-01:** retain the established independent category priorities: sky selection is `STORM` > `RAIN` > `CLOUDY` > `PARTLY_CLOUDY` > `CLEAR` > `SNOW`, while precipitation depiction is `STORM` > `SNOW` > `RAIN`. A supplied non-empty categorical condition governs its visual event. For a carried condition, a uniquely positive rain or snowfall interval takes precedence, then the carried rain or snow condition may cue the event. With no usable categorical condition, cue rain or snow only when exactly one corresponding amount interval is positive. Chance or rate alone never creates event art; zero amount does not establish `CLEAR`. This resolves combined-condition and cross-field precedence without changing forecast text or fact selection.
- **Approval constraints:** preserve current selected-time scrubbing behavior, selected `Instant`, and source bounds; no new interpolation, easing, or transition requirement. Add no provider fields, catalog/replay schema changes, weather families, or theme artwork. Preserve the SG.4a upper-third cloud-placement rule (completed and accepted in cycle 082); it is not new SG.9 work. The approved behavior contract records the mapping above; installed comparisons remain required evidence.
- **Owner execution direction, 2026-11-01:** use the existing replay fixture without adding states. Record the available unknown selection as RUN when captured. Use `CLEAR` for this slice's supported category and composition terminology; `DRY` is deprecated here.

## Production boundary

May change only the catalog scene reading/composition path and directly required focused evidence/docs:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt` — replace string-keyed/string-valued semantic weather decisions on the catalog route with typed selected-time values while retaining support and source-bound distinctions.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` and, if required, a narrowly scoped new composition type under `presentation/scene/` — resolve first-theme light/cue/cloud/precipitation choices and documented order from the typed reading.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — pass the one selected-time reading/composition through the catalog presentation path; preserve forecast text and selected-time authority.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt` and `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualStateMapper.kt` — adapt typed scene output to existing visual state only as needed; do not re-decide forecast meaning here.
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — pass and draw the resolved catalog composition; retain existing compatibility-route inputs and behavior.
- `.codex/test-artifacts/083-sg9-typed-scene-composition/` — baseline/after installed screenshots and observation/command log.
- `.codex/history/083-sg9-typed-scene-composition.md`, `.codex/history/plans/083-sg9-typed-scene-composition.md`, and `docs/ROADMAP.md` only for exact closeout evidence/status.

`CanonicalForecastProjection` may be inspected and its catalog scene-transition result may be disconnected from decorative catalog rendering if required. Do not alter its forecast-fact selection, text presentation, or unrelated callers. If the implementation requires changes outside this boundary, record the concrete dependency and stop for replanning rather than widening SG.9 silently.

## Functional invariants

- The selected catalog and exact selected `Instant` yield one typed scene reading. It carries supported condition and cloud cover, precipitation type, distinct chance/amount/rate support, solar/light inputs, and original point/interval bounds.
- Missing/unknown support remains unknown. Probability-only evidence does not become an event; unsupported chance, amount, or rate does not become zero, `CLEAR`, or an inferred event. Supplied categorical values are represented faithfully.
- Keep the current independent combined-category priorities: sky `STORM` > `RAIN` > `CLOUDY` > `PARTLY_CLOUDY` > `CLEAR` > `SNOW`; precipitation depiction `STORM` > `SNOW` > `RAIN`. Non-empty supplied categorical conditions govern their visual event. A carried condition defers to exactly one positive rain/snow amount interval, then may cue its carried rain/snow category. Without a usable condition, an interval cues only when exactly one of rain or snowfall is positive. Chance/rate alone never cues event art; zero amount never establishes `CLEAR`.
- The first-theme composition resolves sky light, direct cue visibility, cloud appearance/placement, precipitation appearance, and layer order once. The renderer draws those decisions and does not parse display strings or make a second nearest-sample weather choice.
- Preserve SG.4-approved rain/snow appearance and existing `CLEAR`, storm, day/night, readable forecast facts, selected-time behavior, and predictable timeline gesture ownership. Keep the SG.4a cloud output entirely within the top 33% scene-canvas region.
- Scrubbing retains existing selected-time behavior: no timestamp jump, no source-bound drift, and no newly introduced easing/interpolation/transition policy.
- Keep non-catalog compatibility routes functional with their existing input contracts. Identify any remaining legacy route in closeout; do not claim universal migration unless verified.

## Ordered steps

1. [x] Reconfirm approved board constraints and inspect current catalog vs compatibility routes; trace `SceneReading`, catalog projection, visual-state mapping, and each nearest-sample/parsed-string decision used by the renderer. Confirm Pixel 3 XL display/device availability before capture.
2. [x] Build/install the current revision and capture baseline Pixel 3 XL portrait states for CLEAR, rain, snow, storm, probability-only, and unknown; include day/night where existing fixtures support them. Record exact selected instants, source point/interval bounds, displayed facts, viewport, and font scale in the evidence README.
3. [x] Implement the smallest catalog-path refactor: typed support-aware selected-time reading → first-theme composition resolving shared light/cue/cloud/precipitation/layer order → renderer draw. Remove catalog-route reliance on string parsing and a separate renderer-owned nearest-sample weather choice. Preserve compatibility routes and approved invariants.
4. [x] Review focused supplied-value mappings for each approved state, including unknown vs `CLEAR`, probability-only vs supported event, point vs interval bounds, day/night light/cue behavior, and source-time stability. Do not add or run automated tests unless separately requested by the owner.
5. [x] Build/install the changed app and capture the same supported Pixel 3 XL states after the refactor. Compare before/after meaning and forecast readability; inspect the directly affected scrub path for selected-time stability, timeline responsiveness, gesture ownership, and source-bound stability. Record unavailable fixture states as not run with the reason; do not synthesize data to fill gaps.
6. [x] Inspect `git diff --check` and the complete diff. Run `python scripts/dev.py workflow`; close SG.9 only if every roadmap exit and scoped installed evidence are met. Otherwise record exact limitations and leave SG.9 unfinished.

## Acceptance and authorized verification

- **Behavior mapping:** the same selected `Instant` yields a typed reading whose supplied condition, cloud cover, solar/light input, separate chance/amount/rate support, and source bounds match the canonical catalog. Probability-only does not show event art; unknown remains unknown; supported `CLEAR` stays `CLEAR`; supported rain, snow, and storm keep their approved meanings. Point facts retain source valid time; interval facts retain half-open source bounds, with no invented event minute.
- **Conflict mapping:** combined categories retain the independent sky and precipitation priorities recorded above. Supplied categorical condition, carried condition, and interval-only event precedence follow the approved rule. Probability/rate alone and zero amount do not create event art or a `CLEAR` condition.
- **Composition:** one first-theme boundary resolves shared sky light/direct cue, clouds, precipitation, and documented layer order. The renderer consumes its output without string parsing or a second weather sample selection. SG.4 visual meaning and the SG.4a cloud cap remain intact.
- **Installed visual comparison:** Pixel 3 XL portrait at 1440×2960 and font scale 1.0; capture with the device's default animation setting and record its value. Before/after captures cover `CLEAR`, rain, snow, storm, probability-only, and unknown, with day/night where applicable and only where existing supported fixtures provide them. Compare the same instants and values. Forecast facts and labels remain readable; precipitation, cloud, and cue relationships retain approved meaning. RTL, TalkBack, large-font, and reduced-motion acceptance remain outside this design slice and belong to dedicated `TEST` work.
- **Scrubbing and interaction:** on the installed scoped states, selected time and source bounds remain stable during scrub; timeline responsiveness, predictable gesture ownership, and readability remain intact. No new transition/interpolation behavior is required or claimed. No RTL, TalkBack, or broad system/environment matrix is included; these remain dedicated `TEST` work.
- **Commands authorized after activation:** `python scripts/dev.py workflow`; `python scripts/dev.py build`; `python scripts/dev.py run --serial <connected Pixel serial>`; `python scripts/dev.py screenshot --serial <connected Pixel serial> --output .codex/test-artifacts/083-sg9-typed-scene-composition/<state>.png`; and `git diff --check`. Confirm device serial before use. No test suite or new tests are authorized by this plan unless the owner asks separately.
- **Evidence targets:** `.codex/test-artifacts/083-sg9-typed-scene-composition/README.md`; paired `before-<state>.png` / `after-<state>.png` for each supported state; exact commands/build/device/1440×2960 portrait viewport/font scale 1.0/default animation setting; selected instants and source bounds; mapping observations; scrub/readability/gesture observations; and pass/fail/not-run status. Final closeout target: `.codex/history/083-sg9-typed-scene-composition.md`; archived plan: `.codex/history/plans/083-sg9-typed-scene-composition.md`.

## Risks and assumptions

- Existing `SceneReading` exposes string-keyed tracks and catalog presentation currently combines that reading with a separate `CanonicalForecastProjection.scene` transition. Identify which nearest-sample decisions are decorative catalog-only before disconnecting them; preserve nearest fact selection where it supplies forecast text.
- Existing debug fixtures may not expose every combination of CLEAR/rain/snow/storm/probability-only/unknown and day/night. Record unavailable states instead of changing fixtures or claiming coverage.
- The renderer also supports non-catalog compatibility routes. Keep those routes operational and state the limit of any remaining old route in closeout.
- The approved artwork is a state comparison board, not a request for new theme assets or a second theme; use existing artwork/rendering and assess visual meaning against SG.4-approved scenes.
- Refactoring can blend unsupported fields, change categorical priority, move the cloud cap, or alter timeline timing. The typed reading must carry evidence/support and bounds; installed before/after comparison guards existing behavior.

## Out of scope

- New forecast/provider fields, canonical catalog or replay schema changes, provider/cache/recovery work, new event families, official alerts, new theme artwork, second theme, moon, or generalized scene catalog.
- New transition, interpolation, easing, animation timing, or gesture behavior; changes to selected-time authority, timeline scale/zoom, or source-time interpretation.
- Changes to forecast facts/text semantics beyond keeping current supplied values and labels readable; meteorological plausibility filtering or inferred weather claims.
- Reworking the SG.4a cloud-height rule, broad accessibility/system/environment verification, RTL/TalkBack, full test suites, or unrelated cleanup.
