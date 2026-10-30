# Oxygen — Living Atmosphere & Environment Architecture

**Document ID:** OXY-ATM-001<br>
**Version:** 0.1 — proposed implementation specification<br>
**Prepared:** 2026-10-24<br>
**Target repository:** `davidyanceyjr/oxygenwx-elastic-timeline`<br>
**Inspected source baseline:** `main` at `b8477987ca8272da64a1f579177174efed387c96`<br>
**Status:** **Adopted for bounded roadmap planning on 2026-10-24.** This subordinate architecture proposal does not authorize application implementation by itself, override `docs/SPECIFICATION.md` or the adopted experience contract, or authorize production oxygenWX changes. Intake and current-tree reconciliation: `.codex/history/023-atmosphere-spec-intake.md`.<br>
**Roadmap status update:** The unstarted ATM.7–ATM.10 and ENV.1–ENV.5 follow-up sequence was superseded on 2026-10-29 20:47:50 America/Chicago by the owner-directed Scene Generation proposal in `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`. Completed ATM history and shared data, selected-time, accessibility, gesture, and readability invariants remain historical context; future implementation planning follows SG after WX.28.
**Companion:** `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` (OXY-UX-001, adopted).<br>
**Controlling scope:** `docs/SPECIFICATION.md`, then `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `AGENTS.md`, and the current `.codex/` cycle instructions as defined in the repository. On any conflict with controlling prototype constraints, stop, record the conflict and request a bounded owner decision; never silently override the specification.

> **Weather is the experience. Time is the interface. Data is the truth.**

## 1. Executive directive

Build a **Living Atmosphere Engine**, followed by **location-inspired, swappable environment packs**, entirely within the standalone Oxygen Time Lab application. Deliver richer weather behavior without adding interface chrome or changing the user's proven ways of navigating time.

The work happens in two strictly ordered tracks:

1. **ATM — Living Weather Engine:** establish a layered, deterministic atmospheric renderer with time-aware light, clouds, precipitation, fog, and controlled storm effects. Start with the current demo fixture and existing typed presentation boundary. Each layer must have a static/reduced-motion fallback and measurable installed-device acceptance.
2. **ENV — Environment Packs:** make the backdrop geography interchangeable (e.g., neutral/alpine/coastal) while using the *same* atmospheric state, weather renderer, typography and gestures. Additional packs (forest, plains, desert, urban) are candidate follow-ups—not commitments of the first track.

**The app must remain immediately useful with every layer disabled.** Nothing in the weather renderer may become the only source of forecast meaning.

### Deliverables, in order

- An approved technical and visual architecture, with measurable layer contracts.
- An installed pre-engine baseline, now covered by the existing Pixel 3 XL visual, gesture, accessibility, reduced-motion and RTL records linked from ATM.0 in `docs/ROADMAP.md`.
- A pure input-to-scene projection with tests for weather meaning, time and missingness.
- Independently implemented atmospheric render layers and a no-motion/static path.
- At least two interchangeable environment packs using one unchanged UI/gesture surface, **after** the engine is stable.
- Installed visual, gesture, accessibility, lifecycle and performance evidence per bounded slice; actual results must be recorded, not presumed.

## 2. Non-negotiable behaviors — DO NOT REGRESS

The following are the current prototype's interaction contract, not redesign targets:

| Surface | Behavior to preserve |
|---|---|
| Bottom timeline shuttle | Thin, unobtrusive tape with abbreviated day labels and fine ticks; fixed central reading position and tiny center marker. |
| Daily tape gesture | Immediate horizontal drag moves the **tape under the fixed playhead**; tap selects a representative day; daily release resolves to the documented overview. |
| Hourly tape gesture | Stationary long press (presently ~480 ms) selects the touched time, expands the **entire** timeline to uniformly spaced hours (presently ~16 dp/hour), then drags through hours. Do **not** revert to the superseded localized day-only fisheye. |
| Timestamp invariant | Merely animating scale or changing environment **never changes `selectedHour`**. Precision release retains the selected hour, even if the UI resumes a daily-overview visualization. |
| Central forecast display | Horizontal swipe selects next/previous day, preserving hour-of-day within the current fixture's bounds; double-tap returns to demo Now. |
| Accessibility | Keep the accessible forecast browser and timeline scale custom action. Graphics are decorative; visible and spoken forecast facts are independent of the renderer. |
| Visual hierarchy | Atmospheric canvas dominates; large centered temperature and condition, compact secondary metrics, thin timeline at bottom. No persistent Hourly/Daily row, help footer, day cards or effect control panel by default. |
| Data integrity | Synthetic values remain labeled **DEMO DATA**. No source claims, fabricated observations or official weather alerts. |

Retain the current app ID `com.oxygen.weather.timeline` and its isolation from the original `oxygenWX` repository. No location, network, background location, alert or account permission is authorized by this document.

**Explicit exclusions:** changing `ElasticForecastTimeline` gestures, creating a pinch lens, moving main-screen touch targets, connecting weather providers, adding a location picker, adding themes to the original oxygenWX application, or removing demo labeling. Those require their own owner-approved slices.

## 3. Exact Compose ownership and dataflow

### 3.1 Canonical one-way flow

```text
[Existing SampleForecast / eventual provider-neutral forecast]
                         |
                         v
[ForecastProjection] -------> [visible forecast facts + TalkBack]
                         |
                         v
              [SkyFrame / typed values]
                         |
                         v
            [AtmosphereFrameMapper]  <-- [EffectsPolicy]
                         |                 [EnvironmentSpec]
                         v
               [SceneVisualState]
                         |
                         v
           [LivingAtmosphereRenderer]
             |       |       |       |
         sky/light  cloud   effects   terrain/foreground
                         |
                         v
            [noninteractive visual backdrop]
                         |
                 UI composited above:
     [central summary gesture owner] + [timeline gesture owner]

Only existing TimeLabScreen / accessible UI callbacks can modify selectedHour.
```

The weather engine is a **consumer** of forecast selection, never an alternate navigation controller or a source of forecast facts.

### 3.2 Existing-to-target migration map

| Existing file/type | Target treatment |
|---|---|
| `ui/TimeLabScreen.kt` | Keep selection state, main-view gestures, bottom timeline, summary and accessible browser. Replace only the `AtmosphericScene(...)` visual call with an adapter/delegating renderer. Avoid changing pointer-input modifiers. |
| `ui/ElasticForecastTimeline.kt` | **Unchanged** during weather and environment implementation; keep current scale and gesture ownership. |
| `ui/AccessibleForecastBrowser.kt` | **Unchanged**, except for separately approved accessibility bugs. |
| `presentation/ForecastProjection.kt`, `SkyFrame` | Preserve current public contract initially. Add an *additive* typed mapper for scene state; do not make the renderer parse formatted display text. |
| `data/SampleForecast.kt` | Preserve fixture chronology and factual meanings. Add targeted synthetic visual-condition fixtures only through separate, explicitly labeled test/demo slices; do not rewrite samples to beautify screenshots. |
| `ui/AtmosphericScene.kt` | Keep as a compatibility adapter during migration. Retire its implementation only after a new renderer achieves parity and is accepted on-device. |
| `presentation/ElasticTimeAxis.kt` | No changes needed; remains the pure timestamp/pixel authority. |

### 3.3 Proposed package layout

The following is the **target module structure**, not a demand to create all files in one cycle:

```text
app/src/main/java/com/oxygen/weather/timeline/
  data/
    SampleForecast.kt                          existing
  presentation/
    ForecastProjection.kt                     existing
    ElasticTimeAxis.kt                        existing
    atmosphere/
      AtmosphereFrameMapper.kt                pure scene-state derivation
      AtmosphereModels.kt                     typed visual semantics
      SceneTransitionPolicy.kt                presentation-only interpolation
  ui/
    TimeLabScreen.kt                          existing; interaction owner
    ElasticForecastTimeline.kt                existing; leave intact
    AccessibleForecastBrowser.kt              existing; leave intact
    AtmosphericScene.kt                       existing compatibility adapter
    atmosphere/
      LivingAtmosphereRenderer.kt             root composable / draw coordinator
      AtmosphereLayers.kt                     explicit layer ordering
      SkyGradientLayer.kt                     palette and horizon glow
      CelestialLayer.kt                       sun/moon motif (illustrative)
      CloudFieldLayer.kt                      distant / middle cloud groups
      PrecipitationLayer.kt                   rain/snow visual treatment
      FogWindLayer.kt                         visibility and atmospheric depth
      StormLayer.kt                           gentle, opt-in lightning treatment
      ForegroundContrastLayer.kt              stable text-readable scrims
      AtmosphereMotionPolicy.kt               lifecycle, reduced-motion, quality
    environments/
      EnvironmentModels.kt                    environment pack interface
      EnvironmentRegistry.kt                  approved pack selection/default
      NeutralEnvironment.kt                   initial baseline/parity pack
      AlpineEnvironment.kt                    later candidate pack
      CoastalEnvironment.kt                   later candidate pack

app/src/test/java/com/oxygen/weather/timeline/
  presentation/atmosphere/                    pure mapping + transition tests
  ui/atmosphere/                              bounded UI tests if authorized

docs/
  OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md   this proposed document
```

Do not introduce a separate Gradle module or dependency just to implement two packs. Separate modules become justified only when compilation, packaging, or asset ownership demonstrably benefits.

## 4. Typed contracts and ownership (illustrative Kotlin API)

The following shape is **normative about boundaries**, illustrative about symbol names. Codex must reconcile signatures with the latest checked-out Compose and Kotlin versions before implementing:

```kotlin
// Pure presentation/atmosphere types; not Compose mutable state.
enum class EffectsLevel { NONE, SUBTLE, FULL }
enum class EnvironmentId { NEUTRAL, ALPINE, COASTAL }
enum class VisualPrecipitation { NONE, RAIN, SNOW, MIXED, UNKNOWN }

data class AtmosphereInput(
    val selectedHour: Double,          // fixture's existing absolute hour offset
    val precisionMode: Boolean,        // daily overview vs hourly inspection
    val sky: SkyFrame,                 // existing typed presentation object
    val localHour: Double,            // demo lighting cue, NOT solar ephemeris
    val environmentId: EnvironmentId,
    val effectsLevel: EffectsLevel,
    val reducedMotion: Boolean,
)

data class SceneVisualState(
    val palette: SkyPalette,
    val cloudCover01: Float?,
    val daylight01: Float?,
    val precipitation: VisualPrecipitation,
    val precipitationDepiction: DepictionKind,
    val windMotion01: Float?,
    val stormIllustrationAllowed: Boolean,
    val environmentId: EnvironmentId,
    val effectsLevel: EffectsLevel,
)

// Canvas-only / draw-time data, never a second forecast source.
// Suggested names are adaptable; no new data permissions are implied.
@Composable
fun LivingAtmosphereRenderer(
    scene: SceneVisualState,
    environment: EnvironmentSpec,
    modifier: Modifier = Modifier,
)
```

`SkyPalette`, `DepictionKind`, and `EnvironmentSpec` should be small immutable types with explicit defaults and unit-independent semantics. Prefer nullable fields where the source does not supply a trustworthy signal. `SceneVisualState` has **no setter** capable of writing `selectedHour`, changing forecasts, invoking weather providers, or intercepting gestures.

### 4.1 Forecast values vs illustrative controls

| Source signal currently present | Permitted visual mapping | Prohibited inference |
|---|---|---|
| `SkyFrame.condition` | Weather-class artwork: clear, partly cloudy, cloudy, rain, storm. | Official warnings, observed lightning, real rainfall intensity. |
| `SkyFrame.cloudPercent` | Opacity/coverage of decorative clouds; bounded linear smoothing across samples is acceptable as illustration. | Exact geometry, cloud heights, satellite-accurate coverage. |
| `SkyFrame.rainChancePercent` | Displayed probability text; may influence a subtle uncertainty/mood cue **if clearly illustrative**. | Particle rate, mm/h, rainfall duration, confirmed rain at a location. |
| `SkyFrame.windKph` | Qualitative particle drift direction/speed magnitude only if appropriate; direction is **not currently present**. | Wind direction or exact physical motion without wind bearing and validated calibration. |
| `selectedHour`, `localHour` | Time-driven sky hues, illustrative dawn/dusk, coherent animation interpolation. | Astronomically accurate sunrise, sunset, sun angle, moon phase. |
| `ForecastView` high/low/temperature/condition | Render as text in UI above the atmosphere. | Recalculation or substitution by the shader/scene layer. |

**Rain/snow rule:** The existing fixture's `SkyCondition` includes clear, partly cloudy, cloudy, rain, storm but **not snow**, nor rain-rate/wind-direction data. Therefore, implement the snow effect algorithm with an explicit synthetic preview fixture and labeling, or defer its on-screen activation until a reliable typed snow condition exists. Do not infer snow from low temperature alone. For rain, the `RAIN` condition can trigger restrained **illustrative** streaks independent of precipitation probability; no density/rate claims.

**Thunderstorm rule:** A storm illustration may include distant muted lightning in `FULL`, but never flash critical text, imply an alert, or strobe. In `NONE`, `SUBTLE`, or reduced motion, use a static cloud/light cue with the exact same forecast text.

### 4.2 Time-scale and interpolation discipline

- `selectedHour` is authoritative. `precisionMode` changes which *view projection* is shown; scale animation is not time.
- A daily overview may depict a representative *day*, not its unknown minute-specific sky; present it as a daily overview.
- Smooth only artwork parameters as the user scrubs. Keep categorical forecast condition changes and displayed source values tied to actual sampled times or explicitly defined presentation rounding.
- Never manufacture interstitial forecast records or animate the **displayed facts** as if providers measured each intermediate minute.
- Draw-time visual motion has its own `effectPhase` or animation clock; **it cannot advance selected forecast time**.
- Later live data must use the location's timezone and actual timestamps. Calendar days across DST are not always 24 elapsed hours; do not copy the fixture's arithmetic into provider-backed models.

## 5. Exact scene composition and layer order

### 5.1 Full-screen composition

```text
+-------------------------------------------------------------+
| Status bar / safe area                                      |
| [OXYGEN / TIME LAB]                        [DEMO DATA]       |
|                                                             |
|    SKY GRADIENT / DIFFUSE LIGHT / CELESTIAL BODY            |
|    FAR CLOUDS            MID CLOUDS             haze         |
|                                                             |
|                     [DATE / TIME]                           |
|                       [72°]                                 |
|                    [Condition]                              |
|                   [High / Low]                              |
|                                                             |
|  LANDSCAPE: distant ridge / shore / plain / city silhouette |
|              localized fog / precipitation                 |
|                                                             |
|  [RAIN CHANCE]                                  [WIND]       |
|                                                             |
|     FRI      SAT       SUN      MON      TUE                |
|        fine ticks / selected center dot                     |
| Navigation bar / safe area                                  |
+-------------------------------------------------------------+
```

No extra full-screen overlay may take pointer input. Compose's **existing central summary container** owns its swipe/double-tap, and the existing timeline owns its gestures. Decorative Canvas layers remain passive: no `clickable`, no `pointerInput`, no focus, no scrolling. The accessible forecast browser remains the separate semantic route.

### 5.2 Back-to-front weather/environment compositing sequence

| Z | Renderable layer | Ownership | Notes |
|---|---|---|---|
| 0 | Base sky | Weather renderer | Full-screen smooth palette; opaque static fallback. |
| 1 | Horizon light + sun/moon | Weather renderer | Illustrative in demo; never obscure selected date/temp. |
| 2 | Far cloud field | Weather renderer | Broad, low-detail, slower moving. |
| 3 | Environment far field | Selected pack | Distant horizon, mountains, buildings or water silhouettes. |
| 4 | Mid/near clouds | Weather renderer | Depth via opacity/scaling rather than costly continuous blur by default. |
| 5 | Environmental mid/foreground | Selected pack | Shoreline, forest, desert ridge, plains; always subordinate to facts. |
| 6 | Weather effects | Weather renderer | Rain/snow/fog/wind; may be split across depth layers after profiling. |
| 7 | Contrast protection | Neutral UI/shared renderer | Stable scrim/gradient under text and timeline; readable in clear/no effects. |
| 8 | Current forecast text and metrics | **Existing TimeLabScreen** | Preserved interaction semantics and typography hierarchy. |
| 9 | Timeline shuttle | **Existing ElasticForecastTimeline** | Small markers; retained invisible touch zone; no visual obstruction. |
| 10 | Accessible browser/dialog | **Existing AccessibleForecastBrowser** | Always above effects; modal semantics unchanged. |

When a weather effect should appear behind an environment object, split its back/foreground pass; do not move the entire Compose input overlay. Drawing order is a rendering concern, not navigation ownership.

### 5.3 Text exclusion and contrast zones

- Keep a **central quiet zone** behind temperature, condition, date and high/low: no bright sun disk, sharp lightning bolt, dense rain cluster, or high-detail foreground crossing the main text.
- Keep a **bottom quiet zone** for rain/wind metrics and the tape; use an environment-neutral legibility gradient which remains stable while scrubbing.
- Do not allow theme variants to move or scale the facts. Background colors can change, typography contrast cannot fall below the accepted legibility standard.
- Preserve current landscape/1.3 font treatment; test large fonts and rotate after each substantial change.
- Avoid tying all visuals to a single giant image: resolution, parallax, and continuity depend on independent layers.

## 6. Weather engine: precise layer responsibilities

### ATM-A. Sky and daylight (first visual slice)

Inputs: selected local hour, valid sky condition, cloud percentage, and explicit `daylight01` (demo approximated). Outputs: color stops for upper sky/horizon and an illustrative directional glow. Define named palettes for daylight clear, overcast, dusk, night, storm and dawn with blend boundaries. **Do not** abruptly jump palettes between adjacent hourly samples. Keep position/calibration clearly illustrative without a solar model.

Acceptance: scrub representative clear day → cloudy → evening → night; no flicker, no misleading exact sun timing, date/temp never occluded.

### ATM-B. Clouds (second visual slice)

Inputs: cloud cover, sky palette, seeded motion phase and quality level. Outputs: layered silhouettes/soft-edged fields with near/far differentiation. Use stable seeded shapes keyed by fixture, not random new shapes each frame; let visual parameters drift or crossfade without remounting the full scene. Avoid permanent high-radius runtime blurs on every frame unless profiling supports them.

Acceptance: continuous scrubbing varies cloudiness smoothly; no popping/reseeding at hour boundaries; `NONE` remains legible and static.

### ATM-C. Rain and snow (third visual slice, data gated)

Inputs: typed `VisualPrecipitation`, **separate** visual depiction metadata, motion phase, and quality. Render bounded particles with clipping and distinct foreground/background sizes; cap particle counts. Never compute real intensity from probability. Snow needs explicit synthetic/demo condition fixture or a future provider-neutral type—never temperature heuristics.

Acceptance: rain/snow visible in requested demo states; static/reduced-motion states communicate precipitation through typed condition text/static art; no implied rates.

### ATM-D. Fog and wind (fourth visual slice)

Inputs: only supported fog/visibility and wind signals. Presently no actual fog source or wind bearing is available; implement reusable effect primitives and enable them **only** in clearly labeled preview fixtures until typed inputs exist. Use a haze/low-contrast depth pass rather than covering forecast text. Wind without bearing can influence generic turbulence/motion only, not a falsely directional arrow.

Acceptance: no false meteorological claims; foreground objects remain recognizable; tape and large text remain crisp.

### ATM-E. Storms and nighttime (fifth visual slice)

Inputs: typed storm state, selected time, effects policy. Muted environmental darkening and cloud backlighting can suggest storms. Lightning is a **rare, subdued illustration**, not a live lightning tracker; no high-frequency flashes. Night uses sky contrast and restrained stars/moon only when illustrative. Do not assert moon phase or real sky visibility without astronomy and cloud model inputs.

Acceptance: storm/night remain readable and comfortable with reduced motion and photosensitivity considerations. Nothing flashing behind main text.

### ATM-F. Quality, lifecycle and motion control (gate before ENV)

Implement one policy for animation and quality. Suggested modes:

| Mode | Rendering contract |
|---|---|
| `NONE` | Static coherent sky/environment, complete critical UI, no particle animation or decorative loop. |
| `SUBTLE` | Restrained static layered atmosphere or minimal low-frequency transitions; no distracting continuous effects. |
| `FULL` | Controlled cloud/particle/environmental motion within an installed frame-time budget. |
| System reduced motion | Stop or sharply suppress decorative motion while preserving chosen visual content and navigation state. |
| Background/offscreen | Suspend decorative clocks and drawing work; never spend frames on hidden scenes. |

A quality tier may *reduce visual sample count/detail* but must not alter facts, time, labels, gesture handling, or availability. Preferring static over hitching is acceptable. No effect should start an independent unconditional infinite loop per layer.

## 7. ENV: interchangeable environment packs

### 7.1 Environment is not the forecast

An environment pack supplies geometry, silhouettes, material palette adjustments and parallax constants. It cannot change cloud percentage, condition, reported temperature, data provenance, selected time, accessible labels, or timeline physics.

Proposed contract:

```kotlin
// Pure pack metadata + draw instructions; one registry owns supported packs.
data class EnvironmentSpec(
    val id: EnvironmentId,
    val displayName: String,
    val horizonStyle: HorizonStyle,
    val terrainPalette: TerrainPalette,
    val depthPlan: DepthPlan,
    val foregroundStyle: ForegroundStyle,
    val supportedEffectDetail: EffectDetail,
)

object EnvironmentRegistry {
    fun resolve(id: EnvironmentId): EnvironmentSpec = TODO("Implement in ENV slice")
}
```

No environment pack owns a `Modifier.pointerInput`, `selectedHour`, a network request, location permission, time mapper, or semantics for forecast values. The UI may have a developer-only pack preview mechanism during acceptance, but no new permanent production control without approval.

### 7.2 Location inspiration vs geographic truth

The initial packs are **artistic environment styles**, not claims that a specific selected city looks like a photorealistic mountain lake or desert. Start with explicit manual/demo configuration. Only once real location metadata exists may the app propose a default regional pack; user preference must override automatically suggested scenery. An unsupported/uncertain location uses `NEUTRAL`, not a guessed landscape.

Future location-aware selection could use *verified* classifications (elevation, coastal proximity, local region/biome, etc.) behind a pure mapping boundary, with the actual source documented. No geolocation or map APIs are authorized for the current standalone prototype.

### 7.3 Initial environment pack matrix

| Pack | Distant field | Mid field | Foreground | Rain/storm variation | Night variation |
|---|---|---|---|---|---|
| **Neutral (baseline)** | abstract low-contrast horizon | soft atmosphere | restrained shadow layer | darker, still generic | dark horizon |
| **Alpine (first optional)** | mountain ridge and distant peaks | valley/rolling tree line | depth haze/trees | valley clouds, snow silhouette if **snow state actually supplied** | silhouette + restrained moon glow |
| **Coastal (second optional)** | level sea horizon | calm shoreline/inlet | subdued shoreline shadow | ocean haze and storm cloud lighting | waterline glow, understated reflections |
| Forest (future candidate) | distant woodland | layered tree canopy | dark trunks/foliage | canopy mist | near-black silhouettes |
| Plains (future candidate) | open flat horizon | low grasslands | minimal grain | low cloud shelf | large night sky |
| Desert (future candidate) | dry distant mesas | dunes/rock | sparse brush | storm dust only with supported metadata | desert-night contrast |
| Urban (future candidate) | skyline | mid-distance architecture | rooftop/road silhouettes | wet-looking materials only as illustrative visual | building lights |

Every pack has a readable `NONE` snapshot, a daytime image, an hourly/dusk/night case, an overcast/rain case, and the same accessibility and gestures. Avoid complete photo backgrounds that cannot convincingly change from sunny to rainy as the user scrubs.

### 7.4 No proliferation of screen types

Environment replacement is a parameter change to `LivingAtmosphereRenderer`, not a new page or Activity. The primary information arrangement stays identical in all packs. Pack switching (once implemented) should crossfade only scenic layers and must not cause a forecast refresh or a timestamp jump.

## 8. Interaction isolation and visual performance rules

### 8.1 No new gesture ownership

The background is strictly noninteractive in ATM/ENV. Do **not** implement pinch, full-screen drag, swipe interceptors or gesture detectors in the renderer. The user's proposed pinch-to-five-hours/five-days information lens remains a **separate UX.4 candidate**, requiring an explicit gesture arbitration and TalkBack design before any code is added.

Compose screen ownership should remain conceptually:

```kotlin
@Composable
fun TimeLabScreen() {
    // Existing state/gesture callbacks remain the time authority.
    Box(Modifier.fillMaxSize()) {
        AtmosphericScene(/* existing compatible API delegates to new renderer */)
        // Existing central summary with swipe + double tap
        // Existing rain/wind and timeline with pointer input
        // Existing accessible forecast browser when opened
    }
}
```

If a new full-screen graphics surface accidentally consumes touch, that is a blocking regression. The rendering layer must not add focusable semantics that steal TalkBack traversal from weather information.

### 8.2 Frame and energy budgets (proposed; measure before claiming pass)

- Target fluid interaction at the reference handset's normal refresh rate (initial ambition: 60 Hz / ~16.7 ms frame budget) and **report actual** median, 95th-percentile frame times and janky frames under representative scrubbing where tooling permits. These are goals, not existing measurements.
- Make time-scrub-to-visible-response a first-class metric. Accept an effects downgrade if needed to keep gestures dependable.
- Cache stable `Path`/geometry/shader inputs based on viewport and pack; avoid generating large paths/brushes or random arrays per frame.
- Use deterministic object seeds per frame fixture; draw effect phase from one lifecycle-aware coordinator rather than multiple nested unbounded loops.
- Do not allocate thousands of particle objects every pointer event. Prefer bounded indexed draws and immutable precomputed particle descriptors.
- Avoid expensive full-screen blur, oversized translucent offscreen buffers, repeated saveLayer operations and frequently recreated bitmap resources until actual device profiling supports them.
- Suspend animation when activity is not resumed or scene is not visible. Respect reduced-motion and effects policy; keep state after configuration change where the existing app already does.
- Keep the app usable on minSdk 26; use runtime-checked optional APIs and a baseline Canvas fallback. Do not silently make new GPU/AGSL features mandatory.

### 8.3 Effects rollback switch

Each new layer should have a controlled on/off or static fallback in developer configuration, **not** a new visible toolbar. One poorly performing effect must be removable without rolling back the entire atmospheric system. `NONE` is a requirement for scene correctness, not merely a test switch.

## 9. Test and verification matrix

**Keep existing acceptance evidence as a baseline, do not claim new acceptance from prior screenshots.** The repository's P.1/P.2 records include Pixel 3 XL gesture, high/low landscape, TalkBack custom-action, accessible browser, reduced motion and RTL evidence. Each changed slice should rerun the relevant checks because new drawings can reintroduce contrast or performance regressions. **Current `AGENTS.md` says not to add or run tests unless the owner asks; obtain explicit authorization for test creation/execution in the relevant bounded slice or record those checks as not run.**

| ID | Required scenario and expected result | Evidence type |
|---|---|---|
| ATM-T01 | `selectedHour` unchanged on environment/quality/effects change | Pure state test; installed log as applicable |
| ATM-T02 | At each scale, selected timestamp remains at fixed center playhead; verify the current **uniform hourly** projection, not only the historical localized-focus test | Focused uniform-scale tests when authorized + installed touch observation |
| ATM-T03 | Tap/day drag/hold-to-hours/release remain distinct | Pixel 3 XL actual gestures and images/video |
| ATM-T04 | Main-view ±day swipe and double-tap Now still work | Installed gesture sample |
| ATM-T05 | Clear→cloudy→rain→night demo sequence changes scene without misleading numerical claims | Labeled fixture screenshots and mapper tests |
| ATM-T06 | Rain probability alone does **not** select particle intensity; unsupported snow/fog/direction never invented | Pure mapping tests |
| ATM-T07 | Scrim preserves temperature/date/high-low/metrics/ticks in bright, dark, storm scenes | Installed full-resolution screenshots |
| ATM-T08 | Compact landscape with 1.3× font still shows high/low and time/timeline | Installed Pixel 3 XL screenshot |
| ATM-T09 | TalkBack browse/scale actions are not obscured by new render layers | Accessibility node/test or manual installed action (scope by slice) |
| ATM-T10 | Reduced motion/`NONE` gives clear static scene without decorative frame loop | Installed device setting and lifecycle observation |
| ATM-T11 | Background/offscreen stops decorative motion | Instrumented lifecycle observation when implemented |
| ATM-T12 | Environment A ↔ B changes **only** scenery, not time/facts/gestures | Pure test + paired installed captures |
| ATM-T13 | Timeline response and frame-time metrics under repeated scrub are measured | Pixel 3 XL profile with device/build details |
| ATM-T14 | RTL chronology remains explicit; large text, cutouts, and orientation retain usable facts | Installed targeted states |
| ATM-T15 | Snow/fog preview clearly marked synthetic until typed condition inputs exist | Fixture/code review + installed label |

For every visual slice record: cycle/slice ID; code revision; device/API; viewport/density; font scale; locale; motion/effects setting; exact fixture and chosen time; actual build/run/test commands; screenshot/video path; responsiveness/predictability/readability disposition; omissions and blockers. Screenshots without reproduction context are insufficient.

## 10. Bounded development roadmap (adopted proposal; roadmap status is authoritative)

Do not copy this whole sequence into one enormous Codex implementation cycle. The bounded slices are recorded in `docs/ROADMAP.md` using the project's required leaf format. Keep stable slice IDs separate from sequential `.codex` cycle IDs; assign each cycle ID only when its plan is created. The intake and authority reconciliation are recorded in cycle **023**.

### Gate G-ATM — Adoption and baseline status

**ATM.0 — Installed pre-engine atmosphere baseline — DONE**<br>
The existing installed Pixel 3 XL records cover daily/hourly states, main swipe/Now, bright/night, large-font landscape, reduced motion, TalkBack and RTL. The roadmap links the relevant captures and closeouts. The evidence is qualitative for responsiveness and does not include RTL-specific TalkBack review or frame-time measurements. No duplicate capture cycle is required.

### Phase 1 — ATM: living weather engine

**ATM.1 — Pure typed scene projection and fallback**<br>
**Boundary:** `presentation/atmosphere/` + approved focused pure tests + adapter seam; no visible redesign.<br>
**Exit:** `SkyFrame` and time drive immutable `SceneVisualState`; missingness and rain-probability semantics explicit; selectedHour/timeline unchanged; existing screenshot baseline still renders; verified build and focused checks.

**ATM.2 — Sky/light/celestial backgrounds**<br>
**Boundary:** atmosphere sky/light renderer + static fallback.<br>
**Exit:** clear/cloudy/day/dusk/night transitions on fixture visually reviewed, temperature/date not crossed by celestial light, gesture/contrast matrix passes.

**ATM.3 — Multi-depth cloud renderer**<br>
**Boundary:** clouds only; no precipitation code or landscape overhaul.<br>
**Exit:** cloud groups move coherently with selected time and controlled effect phase, no sample-boundary popping, static fallback and installed responsiveness verified.

**ATM.4 — Rain/snow particle primitives**<br>
**Boundary:** precipitation renderer + explicitly synthetic test states.<br>
**Exit:** raindrops depict only typed rain; snow requires explicit synthetic preview type or provider-neutral condition; probability not used as rate; particle budget tested; reduced-motion static outcome documented.

**ATM.5 — Fog and wind effect primitives**<br>
**Boundary:** reusable haze/turbulence primitives and clearly labeled preview fixtures only; no unsupported live fog or wind direction claims.<br>
**Exit:** absent inputs produce no fabricated effect; preview cases are labeled; static rendering remains readable and does not obscure facts or timeline.

**ATM.6 — Storm and night refinements**<br>
**Boundary:** restrained storm/night artwork only; no severe-weather alerts, solar ephemeris, moon-phase claims, or flashing critical text.<br>
**Exit:** storm/night states are comfortable and legible in static/reduced-motion modes; installed review confirms no flashes behind forecast text.

**ATM.7 — Effects policy, lifecycle and performance acceptance**<br>
**Boundary:** animation coordinator, static/quality fallback, focused benchmarking.<br>
**Exit:** actual Pixel 3 XL frame/gesture/lifecycle evidence; `NONE`, `SUBTLE`, `FULL`, reduced motion and background pause work as specified; no regression in P.2 behaviors. **ENV work begins only after this gate.**

### Phase 2 — ENV: environment packs

**ENV.1 — Environment registry + Neutral baseline**<br>
**Boundary:** pure pack contract and migrate existing scenic shapes behind `NEUTRAL`; no permanent UI selector.<br>
**Exit:** neutral parity and alternate-pack selection mechanism work with zero forecast/timeline changes; paired installed screenshots.

**ENV.2 — Alpine environment pack**<br>
**Boundary:** scenery data/drawables only; weather layers reused untouched.<br>
**Exit:** clear, rain, night and synthetic snow preview have coherent alpine art; weather text/controls remain unchanged and legible.

**ENV.3 — Coastal environment pack**<br>
**Boundary:** scenery data/drawables only; no location lookup.<br>
**Exit:** clear, storm/rain and night depict distinct coast but preserve same timeline, time and forecast details; render budget accepted.

**ENV.4 — Cross-pack scene transitions and final matrix**<br>
**Boundary:** pack swap animation/fallback, acceptance matrix.<br>
**Exit:** Neutral↔Alpine↔Coastal transitions never modify `selectedHour` or provider state, low-end and reduced-motion cases have static path, all established gestures and accessible navigation survive.

**ENV.5 — Additional forest/plains/desert/urban packs (candidate)**<br>
Not scheduled automatically. Product-owner approval and reference/asset plan required after the first three packs are accepted.

### Future separate work (not part of ATM/ENV)

- **UX pinch information lenses** for five-day/five-hour details: independent gesture ownership and accessible non-gesture equivalent required.
- Real provider normalization, actual location metadata and geographic matching, sunrise/sunset astronomy, wind direction/rainfall amount, severe-weather alerts, cache and source provenance.
- Integration into the original `oxygenWX` four-page application.

## 11. Asset and implementation quality guidance

- Start with Canvas geometry, gradients and modest layered procedural elements. Use code-only art until performance and visual direction are validated. Generated concept images are **design references**, not dynamic weather models and not meteorological evidence.
- For later production art, document each asset's source, license, dimensions, alpha and color space; never ship unlicensed photography or heavy background videos without review.
- Keep simulation determinism for screenshots: stable seed + selected forecast moment + quality state. Time phase must be separately controllable for tests; never base fixture weather on wall-clock milliseconds.
- Avoid rendering *all* environments simultaneously and hiding them with opacity; render the chosen pack and, during an approved crossfade, only the two involved packs.
- Prefer explicit `drawSky`, `drawFarClouds`, `drawEnvironmentFar`, `drawEnvironmentNear`, `drawEffects`, `drawContrast` operations over an unmaintainable single `Canvas` function.
- Document conditional rendering and quality limits, not just appearance. If any layer hitches on the reference device, its effective state should downgrade or become static without breaking navigation.

## 12. Definition of done and stop conditions

**A track is complete only when** the corresponding bounded roadmap exits are satisfied with recorded installed evidence, all required scenario checks are passed or explicitly blocked with owner disposition, and existing P.2 interaction semantics still work. No broad “looks good” claim substitutes for evidence.

**Stop a slice and record a blocker** if it changes forecast meaning, introduces conflicting gesture ownership, adds location/network access without authorization, silently expands planned scope, makes critical UI unreadable, cannot be tested on its required device without recording the gap, or has uncontrolled foreground/background frame loops. Do not paper over these conditions with new visual effects.

**Final product promise:** The atmosphere changes to tell the story of forecast time. Environment packs change where that story appears to take place. Neither changes how the user explores time or what the weather data actually says.

---

### Snapshot provenance and caveat

This document was prepared from the connected repository's `main` revision `b8477987` on 2026-10-24. Its implementation suggestions are **not** evidence of an app build or installed testing. The local intake and reconciliation were completed in cycle 023; the current roadmap records the bounded follow-ups. The already-adopted OXY-UX-001 and the controlling prototype specification remain in force.
