# Cycle plan — 026-atm3-cloud-renderer

State: ACTIVE
Roadmap: ATM.3
Objective: Add deterministic far and mid cloud groups driven by typed cloud-cover inputs and a controlled visual phase, then verify smooth transitions and installed acceptance without changing precipitation, terrain, timeline, or forecast semantics.

## Scope and boundary

Add the ATM.3 multi-depth cloud renderer to the standalone atmosphere scene.
Use the existing `SceneVisualState.cloudFraction` and current sky palette as
the semantic/style inputs. Give cloud geometry stable deterministic identities
and use a controlled visual phase for coherent relative drift. Any time-based
phase is presentation-only, derived from the already selected forecast time;
it must not select a forecast frame or write/change `selectedHour`. No wall-clock
randomness or per-frame reseeding.

Cloud motion uses linear deterministic drift from the exact fractional
`selectedHour` hour offset. Each depth group's fixed geometry translates at a
constant bounded rate and wraps seamlessly across its repeating field; rates
may differ between far and mid groups to create coherent relative drift. The
rate is a visual tuning value selected during implementation and recorded in
the evidence notes. The phase is otherwise determined only by `selectedHour`:
it does not depend on `precisionMode`, `localHour`, cloud cover, palette,
effects level, wall-clock time, or an animation clock. Wrapping changes only
the repeated visual offset; it must not create a visible position jump.

Expected change boundary, to reconfirm before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` and,
  if useful, a narrowly scoped `CloudFieldLayer.kt` in the same package.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt`
  and `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` for
  the missing selected-time input: add the authoritative `selectedHour` value
  to `AtmosphereInput` and pass it from the screen call site. The typed input
  may expose this time to the renderer as phase context; it must not select or
  mutate forecast state. Keep `localHour` as its existing separate daylight
  input. Inspect and preserve all existing `AtmosphereInput` construction
  sites, including the legacy `AtmosphericScene(frame, localHour)` overload and
  mapper test fixtures; any no-time compatibility path must remain deterministic
  and must not infer or mutate forecast selection. Do not add forecast/provider
  semantics or a new data source.
- Cycle records: `.codex/plans/026-atm3-cloud-renderer.md`,
  `.codex/test-artifacts/026-atm3-cloud-renderer/`,
  `.codex/history/026-atm3-cloud-renderer.md`, and the ATM.3 roadmap evidence
  field.

Cloud groups are decorative and passive. Use broad, low-detail far forms and
more defined mid forms, with coverage/opacity responding continuously to
nullable normalized `cloudFraction`. Missing cloud cover must remain
visually conservative and must not be replaced with invented weather. Reuse
the current sky palette for coherent colors. `EffectsLevel.NONE` and reduced
motion suppress autonomous animation only. They do not freeze or reset the
deterministic visual phase derived from the selected forecast time: cloud
placement may change when the user selects another time, while remaining
stationary when selection is unchanged. No independent unbounded animation
loop or lifecycle policy is introduced in this slice.

## Functional invariants

- `selectedHour` remains authoritative. Cloud phase and rendering cannot
  change selected time, forecast selection, forecast facts, or timeline state.
- `AtmosphereInput.selectedHour` carries the exact `TimeLabScreen.selectedHour`
  value as authoritative selected-forecast-time context. It is distinct from
  `localHour`, which remains the existing daylight cue, and from
  `precisionMode`, which remains a scale/presentation value. Only the
  selected-time value drives cloud phase; the mapper and renderer cannot use
  it to select another `ForecastView`, round or clamp it, or write it back to
  screen state. `selectedHour` is the existing fractional hour offset from
  fixture start, so fractional timeline positions produce fractional drift.
- For each cloud group, phase/offset is a deterministic linear function of
  `selectedHour` using that group's fixed bounded drift rate and seamless
  wrapping. Geometry and each group's rate are stable across recomposition.
  Zoom/scale presentation alone does not change phase; while selection is
  unchanged, placement is stationary. No other input or clock advances phase.
- Geometry and seeds remain stable across recomposition and adjacent sample
  boundaries. Coverage, opacity, and placement vary smoothly as typed inputs
  change; no random reseeding, shape popping, or abrupt hour-boundary reset.
- Cloud cover controls illustrative cloud coverage only. It does not imply
  cloud height, exact geometry, satellite measurement, rain intensity, or
  precipitation. Rain probability and condition identity do not drive cloud
  particle rates or fabricated weather values.
- Cloud artwork stays behind the existing information/contrast layers and
  outside the quiet zones needed for temperature, condition, date, metrics,
  and timeline labels. It takes no pointer input, focus, semantics, or gesture
  ownership.
- Preserve `selectedHour`, displayed forecast facts, DEMO DATA provenance,
  accessible forecast semantics, layout, and all timeline and main-display
  gestures. No fixture, model, navigation, provider, network, location, or
  production oxygenWX changes.
- `EffectsLevel.NONE` and reduced motion disable autonomous animation, but
  preserve the same forecast-time-driven cloud phase and visual response as
  other modes. No rain/snow,
  terrain, fog/wind, storm, environment, or effects lifecycle redesign.

## Visual objective and applicable conditions

Create clear separation between stable far and mid cloud groups. Cloud amount
should track supported cloud-cover input smoothly while the scene remains
readable, restrained, and continuous when scrubbing. Clear samples should
leave substantial open sky; overcast samples should read as cloudier without
covering forecast text. Avoid continuous high-radius blur unless installed
performance evidence supports it.

Installed acceptance on the Pixel 3 XL reference device covers:

- Clear and overcast fixture samples in both daily and hourly scales, including
  scrubbing across adjacent samples and checking for shape/phase discontinuity.
- Compact/default layout and large font scale, including constrained
  landscape; portrait and landscape where available.
- LTR and RTL layout direction, confirming labels and forecast chronology are
  unchanged.
- Reduced motion enabled and `EffectsLevel.NONE`, confirming there is no
  autonomous animation and that selecting another forecast time still updates
  cloud placement coherently.
- Direct-touch tape tap/drag/hold/release and main-display day-swipe/double-tap
  reset checks to confirm responsiveness, gesture predictability, and selected
  time stability.
- Use the installed adjacent-sample scrubbing and readability review to tune
  candidate far/mid drift rates. Confirm the chosen rates remain restrained,
  cloud placement changes smoothly, and forecast facts stay readable; record
  the final rates and observations. If device review cannot establish these
  outcomes, record the exact limitation and do not claim ATM.3 acceptance.

For each capture, record exact fixture/selected time, device/API, viewport and
density, font scale, locale/layout direction, orientation, motion/effects
setting, visible scale, and the final far/mid drift rates used. Screenshots
must be actual installed captures.
Record the three cross-cutting dispositions—timeline responsiveness, gesture
predictability, and readability—from direct observation; screenshots alone do
not establish interaction acceptance.

## Ordered steps

1. Reconfirm ATM.2's renderer, `SceneVisualState.cloudFraction`, selected-time
   flow, current cloud bands, device availability, and ATM.0–ATM.2 evidence.
   Check the Pixel 3 XL at execution; current availability is not assumed.
2. Implement only deterministic far/mid cloud groups and their bounded draw
   inputs. Keep geometry seeds stable. Derive each group's phase as linear
   deterministic drift from the exact fractional `selectedHour`, with a
   constant bounded rate and seamless wrapping. Select candidate rates during
   implementation, then tune and confirm the final rates during installed
   review; record the values and review observations. `NONE` and reduced motion
   suppress autonomous animation only; they preserve the same selected-time-
   driven mapping and respond to user selection. Preserve the existing sky,
   celestial, landscape, rain, contrast, text, and timeline behavior except for
   replacing the legacy cloud bands with the new groups.
3. Review source and diff. Run authorized workflow, contract, build, and lint
   checks as applicable. Do not add or run tests under the standing repository
   rule; if implementation requires test authoring or execution to resolve a
   correctness question, request owner authorization in the execution turn.
4. Install and review the stated visual/gesture matrix. Save actual screenshots
   and observation notes under the evidence target. Record any unavailable
   device/state or unmet acceptance as a precise limitation; do not claim the
   roadmap exit unless every required criterion has evidence.
5. Inspect `git diff --check` and the final diff. Update the roadmap evidence
   path and close into history only if ATM.3's full exit is met; otherwise
   record the observed blocker/failure and keep ATM.3 not DONE.

## Acceptance and evidence

ATM.3 is complete only when all are evidenced:

- Far and mid cloud groups are visibly distinct, with stable geometry and
  deterministic linear drift from fractional `selectedHour`; adjacent sample
  scrubbing changes cloudiness and placement smoothly without reseeding,
  visible wrap jumps, or shape popping.
- Final far/mid drift rates are recorded after installed review and produce
  restrained motion, smooth adjacent-sample transitions, and readable forecast
  facts on the reference device. If no bounded rates meet those conditions,
  record the observed failure or blocker and leave ATM.3 incomplete.
- Cloud appearance derives from supported typed cloud-cover/palette inputs;
  missingness remains honest and no unsupported weather or precipitation
  intensity is inferred.
- `EffectsLevel.NONE` and reduced-motion paths are legible and coherent,
  without autonomous animation; both retain selected-time-driven cloud phase
  and placement changes when the user selects another time.
- Installed Pixel 3 XL review covers clear/overcast and daily/hourly states,
  compact/default and large font, RTL, reduced motion, and direct interaction
  checks. Timeline responsiveness, gesture predictability, selected-time
  continuity, and readable forecast facts are explicitly dispositioned.
- No changes exceed this cycle's cloud-only boundary or violate architecture
  and semantic invariants.
- Actual installed screenshots and observation notes are retained; build or
  preview alone is not visual acceptance.

Evidence target: `.codex/test-artifacts/026-atm3-cloud-renderer/README.md` and
actual captures in that directory. Closeout target:
`.codex/history/026-atm3-cloud-renderer.md`; retained plan:
`.codex/history/plans/026-atm3-cloud-renderer.md`. These are targets only; no
implementation or acceptance is claimed while the cycle is PLANNED.

## Authorized verification

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `python scripts/dev.py build` and `python scripts/dev.py lint` as applicable
  to source changes; record exact commands and host configuration.
- `python scripts/dev.py run` and `python scripts/dev.py screenshot` as
  applicable for installed evidence, plus manual interaction/settings review.
- `git diff --check` and final source/diff review.
- Test authoring/execution is not authorized by this plan under `AGENTS.md`;
  do not run tests unless explicitly authorized in the execution turn.

## Risks and assumptions

- `SceneVisualState.cloudFraction` is nullable and comes from normalized fixture
  cloud cover. The scene can render typed state; avoid falling back to raw
  `SkyFrame` mapping in the new layer.
- ATM.7 owns broad effects policy and lifecycle coordination. This slice uses
  only a bounded deterministic phase and does not introduce a continuously
  running clock. Reduced motion suppresses autonomous animation but does not
  disable cloud changes caused by explicit forecast-time selection. If
  coherent selected-time transitions cannot be achieved within the cloud-only
  boundary, record the unmet portion for replanning rather than expanding
  scope.
- The device and all configuration states may not be available at execution.
  If required installed evidence cannot be captured, document the exact
  blocker and do not mark ATM.3 DONE.
- Dense silhouettes or blur could reduce text contrast or render performance.
  Prefer simple shapes and moderate contrast, then assess actual installed
  states; do not move the forecast information layer to compensate.

## Out of scope

- Rain/snow particles, precipitation intensity, storm artwork, fog, wind,
  terrain or environment packs, and ATM.7 quality/lifecycle policy.
- Changes to forecast fixture/model/values, selected-time authority, timeline
  physics, gesture handlers, accessibility semantics, text/layout, visible
  controls, provider/network/location behavior, or production integration.
- The scoped `AtmosphereInput.selectedHour` field and its `TimeLabScreen`
  call-site wiring are permitted solely to carry the existing authoritative
  selection into the decorative renderer; they do not change selected-time
  authority or forecast selection.
- Runtime randomization, wall-clock driven weather, astronomy, satellite
  claims, or claims about cloud height/geometry.
