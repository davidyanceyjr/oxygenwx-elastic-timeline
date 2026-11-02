# Oxygen — Scene Generation Specification

**Document ID:** OXY-SG-001  
**Version:** 0.1 — owner-directed draft  
**Prepared:** 2026-10-29 20:47:50 America/Chicago / 2026-10-30 01:47:50 UTC  
**Status:** Drafted as the planning basis for the proposed SG roadmap. It is subordinate to `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, and bounded active cycle plans. This draft does not authorize application implementation.  
**Roadmap jump point:** See the dated handoff and current sequencing in `docs/ROADMAP.md`. Source: the WX → ATM → ENV roadmap. Destination: SG.0 proposal review followed by SG.1–SG.7, independent of deferred WX.27/WX.28 release-readiness slices.
**Prior architecture context:** `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md` remains a record of the earlier layered-atmosphere proposal and completed ATM history; its unstarted ATM/ENV sequence is superseded by the SG roadmap.

> **The scene is a visual forecast across time. A theme gives that forecast a world to inhabit.**

## 1. Purpose

Scene Generation turns typed, location-scoped forecast events into a deterministic visual timeline. As a user scrubs the forecast, the scene should quickly communicate what is changing and when. A user should be able to scan the scene to understand a near-term question such as whether rain is expected in the next several hours, while exact facts and uncertainty remain available as text.

The scene is a primary forecast presentation surface. It is decorative in the data-ownership sense: it never changes, replaces, or becomes the source of forecast facts. Its composition can be expressive and animated while remaining truthful to the selected forecast and usable when motion is disabled.

## 2. Authority and scope

- `docs/SPECIFICATION.md` controls prototype scope, data semantics, and architecture boundaries.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` controls the adopted experience contract.
- `docs/ROADMAP_SPECIFICATION.md` controls bounded slices, statuses, evidence, and replanning.
- This document is a subordinate draft for planning the SG workstream. It must be reconciled with the current source tree and approved through bounded roadmap plans before implementation.
- WX.26 follows WX.25. Under the owner-directed roadmap reprioritization, SG.0 is a documentation-only proposal review and SG.1–SG.7 may proceed according to their SG and DFM dependencies without waiting for deferred WX.27/WX.28 release-readiness slices.

The specification addresses forecast-event timing, asset references, semantic layers, theme-specific composition, temporal transitions, deterministic planning, asset loading, and rendering boundaries. It does not authorize new forecast providers, climate datasets, alerts, location permissions, or changes to timeline gestures.

### 2.1 Current visual baseline and target

The current app already opens on a scene: a basic landscape background with a sky palette, diffuse light, a daytime sun cue that moves with local time, moving circular cloud groups, and rain/snow rendering. The current atmospheric renderer has no moon/night marker or wind layer. This is a description of the existing renderer, not a claim that it is already a scene engine or theme-independent.

The scene engine is intended to grow from this working primary visual surface. The background/world is a first-class part of the scene, alongside the time-varying weather and light elements. It is not incidental page decoration. The background and scene together occupy the app's main visual area from the first screen. Early engine work may use simple placeholders, but the typed model and composition must account for the background as deliberately as they account for rain, snow, clouds, and light.

There is no implemented scene-asset library, approved artwork set, or image-asset pack. The current renderer draws directly with Compose primitives. `docs/WEATHER_ASSETS_SPEC.md` is a proposed worldwide semantic and drawing inventory, not implemented coverage or an approved art board. The proposed engine includes a drawable asset library: reusable code functions create renderable objects for scene elements, and the engine composes and places those objects.

## 3. Product behavior

The selected forecast instant is the scene's authoritative reading point. Moving it along the time tape updates the scene to show the forecast pattern at and around that time. The renderer may animate between supported scene states so the user can perceive event onset, continuation, and clearing.

Hourly and daily navigation evaluate the same scene plan at that exact instant. A daily overview anchor, reference image, or other sparse preview cannot substitute its own time for the main scene or hide a supported event between reference times. Releasing a gesture or changing timeline scale does not snap the scene to an anchor. Scene metadata and readable forecast values must refer to the same selected instant and catalog revision; sample times and granularity remain explicit when they differ from the selection.

Examples of intended behavior:

- Scrubbing between supported dry and rain samples changes the representative imagery within their time bracket. An interval-backed event may instead use its explicit supported bounds. The scene must not label a chosen visual transition minute as a reported rain onset or clearing time.
- Moving through day, dawn/dusk transition, and night changes the scene's light-phase assets smoothly while weather-event timing remains intact.
- A scene can make a short forecast window visually scannable without the user reading every value. Exact precipitation probability, amount, temperature, and missingness remain visible through the app's text presentation.

The scene maps supported forecast categories directly to their visual meaning. A selected supported condition set containing only `CLEAR`, `PARTLY_CLOUDY`, or `CLOUDY` maps to a dry scene; it does not require a separate empty-phenomena proof. Empty or missing condition data remains unavailable as a source fact. The SG.4 interior-gap exception may display a preceding supplied condition and scene with its source time disclosed; it does not fill that source gap. The app does not independently validate meteorological plausibility; design-slice acceptance checks the supplied value against its intended visual mapping. Probability is not certainty; probability is not amount or intensity. Decorative interpolation may smooth imagery between adjacent supported point samples; it cannot create an additional meteorological fact, assert continuous coverage, or report an exact event time. SG.1 defines the bounded representative-display rule; the SG.4 amendment below defines the eligible missing-hour display exception and precipitation-specific visual mapping.

## 4. Architecture and ownership

The target flow is:

```text
Canonical forecast catalog + location/timezone + selected Instant
                              |
                              v
                    Typed forecast events
                              |
                              v
                     Scene plan generator
                 semantic asset ID / layer / time
                              |
                              v
                   Theme composition rules
           asset choice / parameters / placement / order
                              |
                              v
                Compose scene asset library
               asset ID -> function -> drawable object
                              |
                              v
              Scene renderer places and draws objects
                              |
                              v
            Compose/Canvas scene at selected Instant
```

The composed scene includes its theme-owned background/world and the scene elements placed within it. The background is resolved and rendered as part of the scene output, not supplied as unrelated screen chrome. A theme may vary its background by light phase or other approved scene state while keeping weather tracks semantically unchanged.

The scene engine owns a Compose asset library that supplies drawable objects. Weather and scene-element assets can be created by drawing functions; the theme background may instead be an image exposed through the same drawable-object boundary. Illustrative names include `icSunny`, `icPartlyCloudy`, `imgBackground`, `imgCumulusCloud`, and `icSleet`; the naming convention and exact returned type are open implementation details. A background image is a scene asset, not unrelated screen chrome. The theme determines its fit/composition and places it behind the ordered weather and scene elements.

The asset library is intended to grow across globally relevant forecast events and scene elements. A location such as Chicago or Dubai uses the same semantic asset and scene-engine contracts; location timezone and supported forecast facts determine timing and conditions. Coverage grows by bounded event-family slices, and the renderer depicts only facts supplied by the typed forecast input.

### 4.1 Scene plan generator

The generator maps supported forecast facts and explicit time/light-phase inputs to an immutable list of visual tracks. It emits semantic content, not screen geometry. A visual track contains:

- a semantic asset ID or asset-family ID resolved by the Compose asset library;
- a semantic `layer_name`;
- a supported interval or time-indexed point sequence, with its support kind retained;
- an optional transition policy for entering, continuing, changing variant, and exiting.

Illustrative shape, not a finalized Kotlin API:

```text
SceneTrack(
  asset = "precipitation.rain",
  layer_name = "precipitation",
  timeline = supportedInterval(startInstant, endInstant),
  transition = "enter/hold/exit"
)
```

The interval example applies only where an explicit interval is supplied. Point-supported conditions retain their sample instants and cannot be promoted to the example's interval. The scene plan contains no x/y/z screen coordinates, theme-specific placement, provider DTOs, mutable UI state, or alternate selected-time state. It does not control gestures.

### 4.2 Theme composition

A theme defines a coherent visual world, not only a palette. It may supply:

- its background and world/viewport definition;
- semantic layer definitions and back-to-front order;
- placement regions, anchors, paths, and transforms for each layer;
- asset families and day/night/light-transition variants;
- transition style, palette, texture, and motion character;
- scale-independent and static/reduced-motion fallbacks.

The theme resolves semantic layer names and asset IDs to its own composition. It supplies parameters to the asset function and determines where the returned object is placed. Different themes may place the same asset family in different regions or use a different spatial structure entirely. A theme may be a landscape, city, ocean, abstract field, orbital scene, or another owner-approved setting. Neither the scene-plan format nor the renderer assumes a planet, sky, horizon, ground, or a particular screen coordinate for a weather event.

**Product-wide cloud placement constraint:** cloud artwork must remain entirely within the top 33% of the atmospheric scene viewport, measured from the top of that theme's scene canvas. The bound includes visible edges, blur, shadow, movement, and transitions. Theme-specific composition may shape and layer clouds within this region but cannot place them below it. This placement rule does not alter cloud-cover support or precipitation meaning; rain and snow may extend below the cloud region.

Weather meaning remains independent of the theme. A theme may change how a supported event is depicted, but may not remove, contradict, or exaggerate its forecast meaning. Theme artwork cannot imply geographic or climatological truth without supported inputs and an explicit product decision.

### 4.3 Renderer

The renderer evaluates the theme composition and generated tracks at the selected instant, asks the Compose asset library for the corresponding drawable objects, draws the background first, then places and draws the ordered elements using theme parameters, and performs only the transitions defined by the scene plan and theme. It remains passive: it owns no timeline gestures, forecast facts, or navigation state.

The owner-directed structure is a reusable Compose asset library consumed by the scene engine. Its packaging, module dependencies, function signatures, and drawable-object type remain bounded implementation choices. Compose can draw procedural or image-backed assets; the specification does not require bitmap or vector image files.

## 5. Time and transitions

- Forecast event intervals use absolute `Instant` values and half-open bounds `[start, end)` only where interval support establishes them. Point samples retain absolute valid times; adjacent supported samples with different conditions bracket a change but do not report its exact instant.
- Local date and local time labels use the selected location's IANA timezone. DST days may contain 23 or 25 actual hourly instants; no 24-hour assumption may be introduced.
- The selected instant is the sole authoritative scene time. Scale animation, theme changes, asset loading, and ambient motion cannot alter it.
- Scrub progress derives from the current selected instant, including reverse traversal and large jumps. A queued appearance animation cannot continue through older selections after the user moves elsewhere; a jump resolves its destination without replaying skipped weather intervals.
- Day/night variants may transition through dawn/dusk or other named light phases. In the first implementation, a phase can use a documented local-time rule. It must not claim astronomical sunrise, sunset, sun angle, or moon phase unless a separately approved and supported calculation supplies that information.
- Weather-event transitions and light-phase transitions are independent tracks. For example, rain can continue unchanged while a scene moves from day through dusk into night.
- Transition easing affects rendered appearance only. It cannot change supplied values, validity intervals, displayed facts, or event timestamps.
- Any ambient looping motion has a separate animation phase. It may be frozen or simplified for reduced motion, lifecycle backgrounding, or a lower quality setting; it cannot advance forecast time.
- At a fixed selected instant, ambient motion may continue without changing the resolved forecast state. Reversing a scrub reverses the forecast transformation, not the direction of ambient particles. Any structural variation must be stable for the same scene inputs.

## 6. Data truth and visual mapping

- Only typed presentation values may feed the generator; provider DTOs do not enter Compose or scene assets.
- Missing or unsupported facts remain unavailable as source facts. A disclosed display of an earlier condition in an eligible interior absent-hour cell is visually and textually distinguishable from a newly supplied condition; leading/trailing gaps and unsupported or empty supplied conditions cannot look like a newly supported dry/clear state.
- A gap between supported samples does not become a seamless weather event by interpolation. Two adjacent expected and supplied condition samples may support representative imagery and aesthetic blending between their valid times. For example, rain at 10:00 and clear at 11:00 may communicate that rain stopped sometime within that hour; a chosen visual transition minute is illustrative, not a reported stop time. An eligible interior missing expected hour may retain the preceding supplied display under the SG.4 amendment below, with its original source time disclosed. An unsupported, empty-supplied, leading, trailing, or wider sparse gap does not gain that carry. Coarse outlook or historical products cannot acquire hourly precision merely because the scene can render at an arbitrary selected instant.
- `ExpectedFieldSupport.Points` names expected instants, and `CoverageStatus.COMPLETE` confirms their supplied values; neither makes a continuous condition interval. An explicit accumulation interval supports an amount over its bounds, not continuous rain or an exact onset/clearing time. SG.4 must define which precipitation inputs establish dry or event evidence and how point-supported rain/snow differs from interval-backed amounts or rates.

**SG.1 representative-display rule (version `sg1-hourly-cells-light-v1`, amended for SG.4 interior gaps).** Sort each field's expected point slots by absolute `Instant`, including missing slots. A supplied value remains a point at its valid time. On the current hourly products, two neighboring expected slots exactly one elapsed hour apart on the same local date may define nearest-slot representative image cells. Their midpoint is a display boundary, never a reported onset or clearing time; the earlier slot owns an exact midpoint tie, including when that slot is missing. A first or last cell may extend one half-hour outward only when it has an adjacent hourly expected slot, and is clipped to the requested window and local-day bounds. The SG.1 baseline gives a missing expected slot an unknown cell and breaks visual carry; the SG.4 exception below changes only the eligible condition display in that cell. An isolated point or wider/sparse gap has no inferred span: only the exact supplied instant has that point value. Do not join cells across local-day boundaries. Supplied accumulation intervals retain their original half-open bounds and do not fill missing condition cells. A precipitation rate keeps its expected point slot and, when supplied as a mean over an interval, separately exposes those mean bounds; the mean interval is not the point's representative image cell. Probability alone stays a chance signal, not a condition or dry claim. A selected supplied condition set containing only `CLEAR`, `PARTLY_CLOUDY`, or `CLOUDY` maps to dry; empty/missing conditions remain unavailable as source facts. Zero probability and zero amount do not change the supplied categorical scene value. The light variant uses local clock hours 07:00–17:59 DAY, 18:00–19:59 DUSK, and 20:00–06:59 NIGHT, with no astronomical meaning. The plan carries clipped, half-open light-phase intervals on the absolute timeline. Weather and light tracks evaluate independently at the exact selected instant. Forecast values remain readable as text; representative image boundaries are illustrative.

**SG.4 missing-hour display amendment (`sg4-interior-carry-v1`).** Apply this only to absent expected condition records in an hourly sequence whose slots are one elapsed hour apart. A carry is eligible only when a supported supplied condition precedes the missing slot and another supported supplied condition follows it on the same local date; consecutive missing expected slots between those anchors may use the same preceding condition. In each eligible missing slot's existing SG.1 representative cell, display the preceding supplied condition and associated scene, labeled with that condition's original valid time and an indication that the selected hour has no supplied condition. The next supplied slot takes over when its representative cell begins; the earlier missing slot owns an exact midpoint tie. Never carry beyond that next cell, across a local-date or catalog boundary, through a supplied empty/unsupported condition, or across a wider or undeclared sparse gap. A leading or trailing missing run uses available daily facts without an hourly condition claim. The selected `Instant` and independent daily, solar, probability, amount, rate, and other hourly facts remain tied to their own support; the carried display creates no canonical row, amount, probability, continuous condition interval, or exact event time. A separately supported positive typed rain/snow accumulation interval retains its own half-open bounds and may govern precipitation art in an eligible missing cell while the older condition remains explicitly labeled as older evidence; it does not fill the missing condition fact. A supplied current condition retains priority over interval art under SG.4's precipitation mapping. An empty or unsupported condition on a supplied hourly record is not an absent expected record and does not trigger carry.
- Rain chance, thunderstorm chance, precipitation amount/rate, snow depth, and new snowfall remain separate signals. No signal may be substituted for another.
- A precipitation asset's presence, probability treatment, and intensity treatment must each have an explicit mapping to supported typed inputs. Probability alone must not create a depiction of measured intensity or certain precipitation.
- Official alerts remain outside this scene system unless a future bounded owner decision creates a separate alert subsystem.
- The scene remains subordinate to exact readable forecast values and accessible text semantics. Decorative art is not a replacement for those facts.

## 7. Determinism, loading, and caching

Given the same catalog identity/revision, location timezone, selected-time window, and generator rules/version, semantic scene planning must produce the same ordered tracks and transitions. Theme identity/version does not change the semantic plan. The selected instant evaluates that plan; it does not create a second forecast clock.

The generator should prepare a compact scene plan for the relevant forecast window so scrubbing evaluates already-resolved tracks instead of rebuilding complex event relationships on every frame. Reusable drawable objects, path geometry, and any renderer resources may be cached where profiling supports it. Do not pre-render every animation frame as a substitute for deterministic scene evaluation.

Any plan cache must distinguish forecast/catalog revision and relevant `asOf` window, location timezone, requested window, and generator rules/version. Theme identity/version and relevant viewport or density inputs belong to composition and drawable-resource invalidation, not semantic-plan identity. This is an invalidation boundary, not a prescribed cache implementation. Stale plans must not survive a catalog replacement or location switch.

Scene preparation and publication use latest-request-wins semantics. Obsolete work from an earlier selection, catalog revision, location, or theme cannot replace the current scene. Catalog replacement remains atomic with the readable forecast presentation. While a destination scene loads, any retained prior visual must be identified as loading or unavailable and must not be presented as weather facts for the new selected time.

No performance claim is made in this draft. A bounded installed slice must measure scene-build latency, cache behavior, rendering cost, and timeline responsiveness on the reference device before the architecture claims fast loading or smooth scrubbing.

## 8. Accessibility and motion

The scene renderer is noninteractive and decorative to accessibility services. Forecast facts and timeline actions remain available through the existing text and accessible forecast browser. RTL and TalkBack acceptance for the current product is owner-accepted; no standalone RTL/TalkBack retest is required in SG design slices. SG design slices verify only their scoped values, visual states, and directly affected interactions. Future cross-cutting RTL, TalkBack, layout, performance, or persistence acceptance belongs in dedicated `TEST` slices and does not gate a design slice.

Reduced-motion and static scene states preserve the same weather meaning, selected-time correspondence, and transition outcome. No effect may strobe, cover essential text, capture timeline gestures, or delay scene/timeline interaction while an asset loads.

## 9. Focused acceptance scenarios

Each implementation slice owns focused acceptance for its changed behavior. Candidate scenarios include:

1. **Near-term rain scan:** From a selected location's current instant, scrub across a six-hour window containing supported dry, rain, and clearing evidence. The scene follows supported sample brackets or explicit interval bounds without reporting an invented event minute; exact probability, amount, and supplied values remain text; unavailable data does not look dry.
2. **Day/night continuity:** Scrub through a light-phase transition while weather evidence remains supported. Light assets transition without changing the weather support, sample valid times, or forecast facts.
3. **Theme spatial independence:** Render equivalent semantic tracks in two themes with different layer placement and world structure. Track timing and forecast meaning agree while composition differs.
4. **Catalog replacement:** A replacement catalog or location produces a new plan; no tracks from the old dataset remain active.
5. **Static/reduced-motion behavior:** The same selected instant and weather meaning are visible without continuous animation.

The exact test set and evidence belong to each bounded SG plan. These scenarios are not authorization for a broad accessibility or application-wide test suite.

## 10. Open decisions for bounded plans

The following choices are intentionally deferred until the relevant slice has an approved concept and current-source review:

- exact Compose drawable-object contract (`Painter` or a project-specific type), asset function signatures, and registry/naming convention;
- detailed Gradle module dependencies and asset-library packaging;
- exact light-phase boundaries and whether any future source supplies explicit sunrise/sunset times;
- asset fallback policy when a theme omits a semantic layer or variant;
- cache size, eviction policy, and measured preloading window;
- selection and persistence UX for themes;
- which additional atmospheric event families receive UI boards and in what order.
- later changes to the SG.1 representative-display rule or SG.4 interior-gap amendment, and precipitation-specific dry/event evidence and timing not already settled by those bounded contracts;
- the unknown/unavailable visual treatment and the supported meaning of a missing theme asset, each with its later slice board.

Resolve these as bounded implementation decisions. The proposed `docs/WEATHER_ASSETS_SPEC.md` inventory can guide semantic mapping and shared art, but its rows do not add canonical facts, approve drawings, or require complete worldwide coverage in SG.2–SG.7. Current canonical conditions are `CLEAR`, `PARTLY_CLOUDY`, `CLOUDY`, `RAIN`, `SNOW`, and `STORM`; additional phenomena are `FOG`, `FREEZING_RAIN`, `ICE_PELLETS`, and `HAZE`. Several inventory meanings can share artwork while retaining distinct readable labels; meanings such as drizzle, showers, graupel, smoke, dust, and squall require later supported typed inputs or an explicit unknown fallback. `ICE_PELLETS` must not silently absorb the regionally ambiguous word “sleet.” The inventory's hazards and official products do not become SG weather conditions or alert claims. Do not silently add a new weather-data source, climate model, theme selector, download service, or general-purpose graphics framework.

## 11. Illustrative scene types

The following Kotlin-like shapes are a shared vocabulary for roadmap planning, not a finalized API or module graph. They make the ownership boundary reviewable: forecast facts become semantic tracks; a theme resolves those tracks into a composition; the renderer evaluates that composition at the selected instant. The same inputs and versioned rules must produce the same logical scene. In the current source, `CatalogTimelineView.selectedInstant` reaches `CatalogTimeLabScreen`; `CanonicalForecastProjection.sceneTransition()` supplies a decorative sample blend, not a source-aware event interval. SG.1 must reconcile the shapes below with those existing contracts.

```kotlin
data class ScenePlanRequest(
    val catalogRevision: CatalogRevision,
    val locationTimeZone: ZoneId,
    val window: InstantInterval,
)

data class SceneCompositionRequest(
    val plan: ScenePlan,
    val selectedInstant: Instant,
    val theme: ThemeVersion,
    val viewport: SceneViewport,
    val motion: MotionPolicy,
)

data class ScenePlan(
    val catalogRevision: CatalogRevision,
    val locationTimeZone: ZoneId,
    val window: InstantInterval,
    val generatorVersion: GeneratorVersion,
    val tracks: List<SceneTrack>,
)

data class SceneTrack(
    val asset: SceneAssetId,
    val semanticLayer: SemanticLayerId,
    val support: SupportedIntervalOrPointSequence,
    val variant: VisualVariant,
    val transition: TransitionPolicy,
)

data class ThemeDefinition(
    val version: ThemeVersion,
    val background: ThemeBackground,
    val layerBindings: Map<SemanticLayerId, ThemeLayerBinding>,
    val fallback: MissingAssetPolicy, // decision belongs to SG.2
)

data class ThemeBackground(
    val default: SceneAssetId,
    val variantsByLightPhase: Map<LightPhase, SceneAssetId>,
    val composition: BackgroundComposition,
)

fun interface SceneAssetFactory {
    fun create(parameters: AssetParameters): SceneAssetObject
}

data class SceneAssetDefinition(
    val id: SceneAssetId,
    val factory: SceneAssetFactory,
)

data class ResolvedScene(
    val selectedInstant: Instant,
    val background: ResolvedBackground,
    val elements: List<ResolvedSceneElement>,
    val coverage: SceneCoverage,
)

data class ResolvedBackground(
    val drawable: SceneAssetObject,
    val lightPhase: LightPhase?,
)

data class ResolvedSceneElement(
    val drawable: SceneAssetObject,
    val placement: ThemePlacement,
    val layerOrder: Int,
)
```

`SceneTrack` contains semantic meaning, source support, and time, never screen coordinates. `SupportedIntervalOrPointSequence` is a reminder to preserve source granularity, not a required new type. `SceneAssetDefinition` associates an asset ID with a function that creates a drawable object. Examples could be functions like `icSunny(parameters)` or `imgCumulusCloud(parameters)`; their exact names and return types are open. `ThemeBackground` and `ThemeLayerBinding` make background and foreground composition explicit; the background is drawn first using its theme-defined fit/composition, followed by ordered elements. Theme placement and style are supplied separately from forecast meaning. `ResolvedScene` is the deterministic logical result for a composition request, including its resolved background and drawable elements. Viewport and density are inputs to theme composition, not forecast meaning. Pixel-identical output across devices is not promised.

The asset functions and drawable objects are part of the scene engine. The scene can mix code-drawn weather elements with a theme-supplied image background, all placed by the same composition rules. Initial implementations may draw simple placeholder shapes to establish the function and placement contracts; the separate UI roadmap owns final UI art direction, interface layout, and visual polish. Missing forecast coverage remains unavailable as a source fact; only the bounded SG.4 interior-gap exception may display a disclosed preceding condition. A missing condition never becomes a newly supported dry state. The exact unavailable drawable treatment remains subject to a bounded plan and its approved board.

### 11.1 Determinism boundary

For a fixed catalog revision, timezone, window, and generator version, planning produces the same semantic tracks. For that plan plus a fixed selected instant, theme version, relevant viewport inputs, motion policy, and renderer rules, composition produces the same logical scene meaning. Any transition progress is derived from explicit forecast time or an explicitly supplied animation phase; ambient motion cannot advance selected forecast time. Random variation is excluded unless a future bounded plan defines a stable seed and cache identity for it.

## 12. Illustrative scene storyboards

These text storyboards describe scene-engine behavior with placeholder visuals. They are examples for reviewing semantics and boundaries, not approved final UI artwork or interface layouts.

### 12.1 Scrub across supported dry, rain, an interior missing hour, and clearing

```text
Illustrative inputs: supplied CLEAR → supplied RAIN → absent expected hour → supplied CLEAR
Forecast valid times: 09:00          10:00            11:00                  12:00

Selected 09:00       Selected 10:00  Selected 11:00        Selected 12:00
+----------------+   +----------------+ +--------------------+ +----------------+
| clear scene    |   | rain scene     | | rain scene         | | clear scene    |
| source 09:00   |   | source 10:00   | | source 10:00       | | source 12:00   |
+----------------+   +----------------+ | 11:00 unavailable | +----------------+
                                      +--------------------+
```

The labels describe illustrative representative states, not continuous intervals or reported onset/clearing times. The 11:00 absent condition remains unavailable as a source fact; its rain scene is explicitly labeled as the 10:00 condition carried for display. With hourly expected slots, the absent 11:00 cell owns the exact 11:30 midpoint tie, and the supplied 12:00 cell takes over immediately after it. If 11:00 were a leading or trailing gap, or a supplied record with an empty condition, it would not carry rain. At each position, the selected `Instant` controls the resolved scene. Exact valid times, values, and missingness remain readable.

### 12.2 Weather interval continues through a light-phase change

```text
Selected time       late day             dusk                 night
Light track         [------ day ---------][transition]--------[night]
Rain track          [---------------- rain remains active ----------------]
Theme output        day placeholder      dusk placeholder     night placeholder
```

This example assumes interval-backed rain support. The theme changes its light-phase placeholder while the same rain track retains those bounds and meaning. For point-backed rain, the separate light track still cannot move forecast sample times or imply continuous rain between them.

### 12.3 Same plan in two different themes

```text
One ScenePlan at the same selected Instant
  semantic tracks: clouds + supported rain

Theme A composition                 Theme B composition
+----------------------+            +----------------------+
| abstract field       |            | city-like world      |
| cloud placeholder    |            | cloud placeholder    |
| rain placeholder     |            | rain placeholder     |
+----------------------+            +----------------------+
same event timing and meaning        same event timing and meaning
```

The themes supply their own backgrounds, may place and order layers differently, and cannot change forecast time coverage or exaggerate forecast meaning. Both examples use placeholders; neither commits the separate UI roadmap to a visual style.

### 12.4 Static or reduced-motion scene

```text
Animated mode: transition between the scene states around selected time
Static mode:   show the state resolved at that same selected Instant
```

Motion policy may change how transitions are drawn, but not which forecast state is selected or what that state means.
