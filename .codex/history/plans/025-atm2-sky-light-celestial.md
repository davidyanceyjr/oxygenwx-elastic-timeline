# Cycle plan — 025-atm2-sky-light-celestial

State: ACTIVE
Roadmap: ATM.2
Objective: Add time-aware sky palettes, diffuse light, and illustrative sun/moon artwork with a static and reduced-motion path, then verify the bounded visual and interaction acceptance matrix.

## Scope and boundary

Implement only the sky palette, diffuse-light, and illustrative celestial
background layers in the standalone atmosphere renderer. These new layers must
consume the typed `SceneVisualState` established by ATM.1 for mapped sky
properties. `AtmosphereInput.localHour` may also be used only to place the
illustrative sun/moon artwork; it remains presentation context and never owns
or changes selected time. Keep the rendering path coherent and fully
understandable without animation. The layer remains decorative and must not
intercept touch or take focus.

Potential change boundary, subject to source inspection before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` and
  narrowly related atmosphere drawing files under the same UI package.
- Existing scene presentation types only if a specific missing input is
  necessary and already supported by the fixture; do not add forecast
  semantics or data sources. Prefer the existing `SceneVisualState` fields;
  any type change must be justified by an ATM.2 rendering need.
- No test source changes or test execution unless the owner explicitly asks,
  per `AGENTS.md` and the adopted atmosphere specification.

No fixture, timeline, summary layout, screen navigation, or production
oxygenWX files may change. Do not alter ATM.1's typed meaning to accommodate
artwork.

## Functional invariants

- `selectedHour` remains the only selected-time authority. Sky/light/celestial
  appearance is derived from the already selected scene inputs; animation or
  scale changes cannot update time, fixture selection, or forecast facts.
- Changing scale alone does not alter the sky when the supplied
  `SceneVisualState` is unchanged. The existing daily overview may supply its
  representative frame while hourly precision supplies the selected-hour
  frame, so their scenes may differ without changing the authoritative
  `selectedHour`. Scale is not forecast time.
- Keep all important forecast facts, date/time, DEMO DATA, timeline labels and
  accessible forecast semantics visible and unchanged.
- Celestial artwork is illustrative. Do not claim solar ephemeris, sunrise or
  sunset precision, moon phase, or actual sky visibility. Do not infer
  unsupported weather inputs.
- Effects remain passive: no pointer input, focusable semantics, navigation,
  network, location, provider behavior, or new gesture ownership.
- Static and system reduced-motion paths preserve the same selected-time
  meaning and readable sky state. No independent unbounded animation loop may
  be introduced; any transition must use an existing bounded effect phase or
  remain static.
- The new sky palette, diffuse light, and celestial artwork use mapped
  `SceneVisualState.daylight`, `cloudFraction`, and `condition` as applicable;
  they must not recalculate these mapped properties from raw `SkyFrame` values.
- Preserve the current legacy `drawCloudBands`, `drawLandscape`, and
  `drawRainHints` behavior and inputs in this cycle. They remain outside ATM.2;
  raw `SkyFrame` may continue to reach those existing layers only.
- No cloud groups, precipitation, terrain/environment redesign, fog, wind,
  storm effects, quality/lifecycle policy, or timeline changes in this slice.

### Renderer input contract

| Layer | Permitted inputs in ATM.2 | Required preservation |
|---|---|---|
| Sky palette and diffuse light | `SceneVisualState.daylight`, `cloudFraction`, and `condition` where applicable | Do not read or remap raw `SkyFrame` values in these draws. |
| Celestial illustration | `SceneVisualState` for mapped sky appearance; `AtmosphereInput.localHour` only for illustrative placement | No astronomical position, sunrise/sunset, or moon-phase model. |
| Existing cloud bands | Existing raw cloud percentage and `localHour` inputs | Keep `drawCloudBands` implementation and behavior unchanged; do not add ATM.3 cloud groups. |
| Existing landscape | Existing no-forecast-input draw | Keep `drawLandscape` implementation and behavior unchanged. |
| Existing rain hints | Existing raw rain-chance input | Keep `drawRainHints` implementation and behavior unchanged; probability remains no intensity claim. |

Route the typed state to the new ATM.2 draws through the scene adapter, which
currently accepts but does not use `SceneVisualState`. Keep the legacy draws'
inputs, implementations, and relative ordering intact; insert the new sky and
celestial work behind them as needed. Do not change the compatibility
renderer contract or selected-time ownership to pass mapped state.

## Approved concept direction

The owner approved the middle-contrast 12-thumbnail concept sheet at
[`025-atm2-sky-light-celestial-concept.png`](025-atm2-sky-light-celestial-concept.png)
as ATM.2's visual direction. Use its moderate contrast and saturation, blue
daylight palettes, muted peach/lavender twilight, diffuse light, and simple
sun/moon shapes as reference. Preserve open quiet areas behind forecast facts
and the timeline; avoid sharp highlights, oversized celestial artwork, or
heavy glow that weakens readability.

The sheet is a concept reference, not installed acceptance evidence or a
requirement to use the bitmap in the app. Cloud silhouettes visible in some
thumbnails are incidental; they do not authorize ATM.3 cloud rendering in
this cycle. ATM.2 remains limited to sky palette, diffuse light, and
illustrative celestial layers using the existing typed scene state.

Palette color stops, blend thresholds, glow strength, and celestial size are
implementation choices; no pixel matching or further owner selection is
required. Choose values against the reference's moderate contrast and
saturation, then judge them in the installed readability matrix. Use
`SceneVisualState.daylight` and `cloudFraction` for continuous sky/light
blending. `condition` may inform a restrained sky tone where applicable, but
must not introduce precipitation or storm effects. Treat the low-daylight
periods around dawn and evening with the same muted twilight family; the
available daylight cue does not establish accurate sunrise or sunset timing.
`AtmosphereInput.localHour` remains limited to illustrative celestial
placement.

## Fixture review anchors

These are exact hourly samples from `SampleForecast.hours`, whose local-time
fixture begins 2026-10-23. They are deterministic demo inputs, not observations
or claims about actual weather. Use them as initial visual review anchors;
installed acceptance still covers the full required matrix. Daily overview
uses its representative 14:00 forecast frame, while hourly review uses the
listed time.

| Visual state | Fixture time | Mapped daylight | Cloud fraction | Condition |
|---|---|---:|---:|---|
| Clear daylight | 2026-10-25 12:00 | 1.000 | 0.180 | `CLEAR` |
| High-cloud daylight | 2026-10-27 12:00 | 1.000 | 0.770 | `RAIN` |
| Dawn | 2026-10-25 07:00 | 0.259 | 0.269 | `CLEAR` |
| Evening twilight | 2026-10-26 17:00 | 0.259 | 0.727 | `PARTLY_CLOUDY` |
| Dark night | 2026-10-25 00:00 | 0.000 | 0.420 | `PARTLY_CLOUDY` |
| High-cloud night | 2026-10-26 23:00 | 0.000 | 0.810 | `CLOUDY` |

The high-cloud daylight sample has `RAIN` condition and 82% precipitation
probability; use it to inspect palette/light under high cloud cover only.
Existing rain hints and their inputs remain unchanged and outside this visual
comparison. The high-cloud night sample provides the fixture's explicit
`CLOUDY` condition without adding a new weather state.

## Visual objective and applicable conditions

Create a legible, time-aware atmosphere with continuous palette/light changes
across the supplied clear, cloudy, day, dusk, and night fixture samples. A
diffuse light cue and simple sun/moon illustration may enrich the scene while
staying behind the information layer. The artwork must not cross or reduce
contrast around temperature, date, high/low values, metrics, or timeline
labels.

Installed review is required on the Pixel 3 XL reference device, covering:

- Daily and hourly scale; clear, cloudy, day, dusk, and night fixture states.
- Bright and dark sky; portrait and landscape.
- Default and large font scale, including compact landscape readability.
- LTR and RTL locale, with chronology and labels remaining understandable.
- System reduced motion enabled, confirming a stable static scene.
- Direct touch checks for existing tape tap/drag/hold/release and main-view
  day-swipe/double-tap reset behavior, to confirm responsiveness and gesture
  ownership remain accepted.

For every capture record exact fixture and selected time, device/API,
viewport/density, font scale, locale, orientation, motion setting, and visible
scale. Record responsiveness, predictability, and readability dispositions;
do not infer them from screenshots alone.

## Ordered steps

1. Reconfirm the current renderer, ATM.1 state/adapter, Pixel 3 XL availability,
   and ATM.0/ATM.1 baseline records before editing. The confirmed source files
   are `AtmosphericScene.kt`, `SceneVisualState.kt`, `ForecastProjection.kt`,
   and `SampleForecast.kt`; use the fixture review anchors above. Recheck the
   device at execution because its current USB availability can change.
2. Implement the bounded sky palette, diffuse light, and illustrative
   sun/moon drawing according to the renderer input contract above. Route
   `SceneVisualState` to these new draws; keep `localHour` limited to
   illustrative celestial placement. Preserve the existing cloud, landscape,
   and rain draws' implementations, inputs, and relative ordering. Keep the sky
   path deterministic and static, including with system reduced motion
   enabled; do not add animation, effects policy, forecast text, or input
   ownership changes.
3. Review the source diff and run authorized static checks/build. Do not add or
   run tests unless the owner explicitly authorizes them. Install the app and
   perform the visual/gesture matrix above; capture actual installed evidence
   and record any unavailable state or device blocker precisely.
4. Record evidence and limitations, inspect `git diff --check` and the final
   diff, then close the cycle only if the full ATM.2 exit is met. Otherwise
   record the failed condition and disposition without marking ATM.2 DONE.

## Acceptance and evidence

ATM.2 is complete only when all of the following are evidenced:

- Clear/cloudy/day/dusk/night palette and light transitions read continuously
  across fixture samples and the sun/moon artwork remains explicitly
  illustrative.
- Source review confirms the ATM.2 sky palette, diffuse light, and celestial
  artwork consume `SceneVisualState` rather than re-deriving mapped daylight,
  cloud fraction, or condition from raw `SkyFrame`. `localHour` is used only
  for illustrative placement when needed; existing cloud, landscape, and rain
  layers retain their prior behavior and inputs.
- Sky/light does not cross or impair essential text or timeline readability
  in bright/dark, portrait/landscape, default/large-font, LTR/RTL, and
  daily/hourly states.
- Reduced-motion review shows a coherent static scene with no loss of selected
  time or forecast meaning.
- Installed touch review confirms tape gestures and main-view gestures remain
  responsive and predictable; selected time stays stable through presentation
  changes.
- No unapproved behavior, data, architecture, or production integration
  changes occur.
- Evidence includes actual installed screenshots and observation notes; a
  build/preview alone does not meet visual acceptance.

Evidence target: `.codex/test-artifacts/025-atm2-sky-light-celestial/README.md`
and captures in that directory. Closeout target:
`.codex/history/025-atm2-sky-light-celestial.md`; retained plan:
`.codex/history/plans/025-atm2-sky-light-celestial.md`. These are targets only;
no evidence is claimed while the cycle is PLANNED.

## Authorized verification

- `python scripts/dev.py workflow` and `python scripts/dev.py contract`.
- `python scripts/dev.py build` and `python scripts/dev.py lint` as applicable
  to source changes; report exact commands and host configuration.
- `python scripts/dev.py run` / `python scripts/dev.py screenshot` as applicable
  for installed evidence, plus direct manual gesture and setting observations.
- `git diff --check` and final source/diff review.
- Test authoring and execution are not authorized by this plan under the
  repository's standing rule; request explicit owner authorization in the
  execution turn if tests are needed to support an implementation decision.

## Risks and assumptions

- ATM.1 exposes only the typed inputs actually available from the current
  fixture. The plan assumes no new provider-like values or astronomy model is
  needed; if a required visual distinction cannot be honestly derived, retain
  a simpler illustrative treatment and document the limit.
- `adb devices -l` currently reports a USB `Pixel_3_XL` device. Installed
  review still depends on the device and Android host being available at
  execution; if unavailable, do not claim the roadmap exit and record the exact
  blocker and evidence gathered.
- Sky gradients or glow can reduce text contrast on some states. Keep the
  information contrast layer effective and assess actual captures at all
  required orientations and font sizes. If readability cannot be preserved
  within the approved background treatment and existing contrast layer,
  simplify the ATM.2 palette/light treatment; do not move or restyle the
  information layer to compensate. If an existing input or boundary must
  change to meet acceptance, stop and record the owner decision needed.
- Avoid per-frame allocations and independent animation clocks. ATM.7 owns
  broader quality/lifecycle policy and performance profiling.

## Out of scope

- Cloud rendering, rain/snow, fog/wind, storm refinement, terrain/environment
  packs, pack transitions, or animation quality/lifecycle coordinator.
- Changes to fixture data, forecast models/values, selected time, timeline
  physics, gestures, accessible browser, visible controls, or screen layout.
- Moon-phase/sun-position calculations, sunrise/sunset claims, real weather
  simulation, provider/network/location integration, and production oxygenWX
  integration.
