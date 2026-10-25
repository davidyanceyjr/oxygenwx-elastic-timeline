# Cycle plan — 024-atm1-scene-projection

State: ACTIVE
Roadmap: ATM.1
Objective: Add a pure typed mapping from the selected forecast presentation to immutable atmosphere scene state, with a compatibility adapter that preserves the current renderer.

## Scope

Implement the first atmosphere-engine seam only:

- Add immutable, Compose-independent atmosphere input/model types and a pure
  `SkyFrame`-to-scene mapper under
  `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/`.
- Add a `SceneVisualState` adapter path in
  `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` and
  route the current `TimeLabScreen` scene call through it.
- Add focused mapper tests under
  `app/src/test/java/com/oxygen/weather/timeline/presentation/atmosphere/`.

Keep the existing Canvas drawing, forecast fixture, visible layout, and all
gesture handlers unchanged. This cycle adds typed state and a compatibility
seam; it does not add or redesign weather artwork.

## Invariants

- `selectedHour` remains owned by `TimeLabScreen`; mapping and rendering cannot
  write it, clamp it, or derive a second forecast clock. Carry the exact value
  in `AtmosphereInput` only as authoritative-time context; do not use it to
  select another frame or to drive draw-time animation.
- `AtmosphereInput.sky` is the already selected `ForecastView.sky`; its
  `precisionMode` is `ForecastView.isPrecision`, and its `localHour` is the
  exact value currently passed to `AtmosphericScene`. The scene projection
  cannot change those inputs or recompute displayed forecast facts.
- `SceneVisualState` preserves `SkyCondition` identity separately from
  precipitation depiction. Map `CLEAR`, `PARTLY_CLOUDY`, and `CLOUDY` to no
  typed precipitation; map `RAIN` to illustrative rain; map `STORM` to an
  explicit storm depiction with precipitation unknown. Use `DepictionKind.NONE`
  for clear/cloudy conditions, `ILLUSTRATIVE_RAIN` for `RAIN`, `STORM` for
  `STORM`, and `UNKNOWN` for any future unsupported condition. Do not map storm
  to rain. Snow and mixed precipitation remain unsupported/unknown because the
  current fixture has no such condition. Preserve the original condition value
  in state so future additions do not collapse to a supported condition.
- `rainChancePercent` must not affect typed precipitation, depiction kind, or
  any particle count/rate. The legacy Canvas remains responsible for its
  current probability-based opacity behavior during this compatibility slice:
  the adapter passes the original `SkyFrame` and exact `localHour` to the
  existing renderer. Do not change `drawRainHints` or claim its existing
  opacity is a typed rain-rate mapping. Later precipitation work owns any
  renderer behavior change.
- Normalize finite cloud-cover values to `[0, 1]` after clamping the source
  percentage to `[0, 100]`; non-finite or unsupported values remain absent
  (`null`), not replaced with guessed weather. Compute the existing illustrative
  daylight cue from finite `localHour` with its current bounded sine formula;
  a non-finite hour yields absent daylight in the mapped state. The adapter
  consumes the existing finite fixture inputs only; it adds no external or
  provider-backed numeric source.
- Use explicit `EnvironmentId.NEUTRAL` and `EffectsLevel.NONE` for the initial
  adapter call. `NONE` means a coherent static scene without decorative frame
  animation; it does not suppress time-derived sky appearance. Honor
  `reducedMotion` by forcing effective effects to `NONE`. This slice does not
  read or add a system setting because the adapter remains static.
- The atmosphere stays passive: no pointer input, focus semantics, navigation,
  network, location, or provider behavior.
- The typed adapter delegates to the existing Canvas implementation with the
  same original `SkyFrame` and selected local-hour values, preserving current
  output (including legacy rain-hint opacity). It does not duplicate or rewrite
  Canvas drawing logic.
- No changes to `ElasticForecastTimeline`, `AccessibleForecastBrowser`,
  `ElasticTimeAxis`, or forecast facts/accessibility labels.

## Steps

1. Define immutable, Compose-independent `AtmosphereInput` and
   `SceneVisualState` types plus the minimum supporting enums/value types. Keep
   source condition identity in scene state; represent absent cloud/daylight
   data as nullable values. Use explicit defaults of Neutral environment and
   `EffectsLevel.NONE`; no new palette system or animation state is needed.
2. Implement a pure mapper. It receives `selectedHour` only as input context
   and does not return or mutate a time value; uses `sky.condition` (never
   `rainChancePercent`) for condition/precipitation mapping; normalizes only
   finite cloud/daylight values; preserves unsupported signals as absent or
   unknown; and reduces effects to `NONE` when reduced motion is requested.
3. Add a typed adapter entry point and route `TimeLabScreen` through
   `AtmosphereInput` and its mapped state. The compatibility call delegates
   with the exact input `SkyFrame` and `localHour` to the existing Canvas
   function. Do not edit gesture handlers, layout, or Canvas layer functions.
4. Add focused pure mapper tests for every current `SkyCondition`, storm/rain
   distinction, rain-probability independence across low/high/non-finite values,
   unsupported precipitation signals, non-finite and out-of-range numeric
   normalization, explicit defaults/reduced-motion behavior, and exact
   `selectedHour` input preservation. The time assertion verifies the input
   remains authoritative; it does not imply that the mapper owns or updates
   screen selection.
5. Run the focused test task, debug build, contract check, workflow validator,
   and `git diff --check`; inspect the full diff. Record exact results and any
   gaps in cycle evidence. Do not claim installed visual acceptance; use source
   and build evidence to confirm the adapter delegates unchanged arguments.

## Acceptance and evidence

ATM.1 exits when:

- Scene state is immutable and derives only from explicit typed inputs.
- Scene state retains the exact `SkyCondition`; `RAIN` alone selects
  illustrative rain, `STORM` remains storm with precipitation unknown, and
  unsupported snow/mixed/fog/wind-direction inputs remain absent or unknown.
- Changing only `rainChancePercent` cannot change typed precipitation,
  depiction kind, or particle count/rate. The legacy Canvas still receives the
  original `SkyFrame`, so its pre-existing opacity behavior is unchanged and is
  not represented as a typed intensity.
- Finite cloud/daylight values are bounded; invalid values produce absent
  optional draw inputs rather than NaN/infinite draw state.
- Defaults are explicit (Neutral, `NONE`); reduced motion resolves to `NONE`.
- The adapter delegates the original `SkyFrame` and `localHour` to the current
  renderer, with no changed Canvas drawing functions or visible composition.
- `selectedHour`, forecast text, accessibility labels, fixture data, timeline
  physics, and gesture ownership are unchanged.
- Focused mapper tests, Android debug build, prototype contract check, workflow
  validation, and `git diff --check` pass.

Evidence target: `.codex/test-artifacts/024-atm1-scene-projection/README.md`,
including command output/results, changed files, mapper cases, and an explicit
statement that no visual redesign or installed visual acceptance was part of
this cycle. Closeout target: `.codex/history/024-atm1-scene-projection.md`;
retained plan: `.codex/history/plans/024-atm1-scene-projection.md`.

## Risks and assumptions

- OXY-ATM-001 signatures are illustrative, but its boundary and semantics are
  adopted here: pure presentation mapping; authoritative selected time;
  condition-based weather class; probability is not rain intensity; static
  `NONE` fallback; nullable unsupported signals. Adapt symbol names only as
  needed for checked-out Kotlin/Compose APIs.
- The current Canvas intentionally derives rain-hint opacity from probability.
  This accepted legacy behavior remains isolated behind the adapter and is not
  evidence that the new typed precipitation state depends on probability.
- This slice does not require installed visual review because it preserves the
  renderer and visible composition; a build or source review does not claim new
  visual acceptance.

## Out of scope

- New sky palettes, sun/moon artwork, cloud-depth rendering, precipitation,
  fog, wind, storm effects, environment packs, or animation lifecycle policy.
- Changes to forecast models, units, fixture chronology, selected-time
  behavior, timeline interactions, accessible browser, or visible UI.
- Production oxygenWX integration, provider/network/location behavior, or
  changes to DEMO DATA semantics.
