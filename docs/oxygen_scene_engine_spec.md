# Oxygen Scene Engine — First-Draft Product and Architecture Specification

## Status

**DRAFT / PROPOSED / NOT ADOPTED.** Architectural reconciliation for owner review; this document does not authorize implementation or change controlling product requirements.

First-draft product and architecture specification.

This proposal reconciles the intended scene direction with current source. `docs/SPECIFICATION.md` controls prototype semantics; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` controls adopted selected-time, gesture, readout and access behavior. Aspirational examples require supported data and bounded owner decisions before implementation.

# 1. Product Vision

Oxygen is not intended to become a conventional weather dashboard placed over an animated background.

The scene itself is the primary interface.

The central design principle is:

> **The default state is the world.**

Weather should first be experienced visually through an interactive environmental scene. Numeric measurements, charts, explanations, and advanced meteorological information remain available, but they should appear only when they add precision or when the user asks for deeper information.

A second governing principle is:

> **If information can exist meaningfully in the scene, it should not permanently sit on top of the scene.**

Oxygen therefore treats weather information at three levels:

1. **Environmental** — the world visually expresses the condition.
2. **Diegetic** — objects inside the world display actual measurements.
3. **Interactive** — users inspect scene elements to reveal deeper information.

The intended result is an application that is:

- immersive
- interactive
- useful
- educational
- playful
- expandable
- understandable to casual users
- deep enough for weather enthusiasts

# 2. Core User Experience

The normal Oxygen experience should contain as little conventional dashboard UI as practical.

A user should be able to scrub the timeline and observe weather changes directly.

Examples:

- clouds increase in density as cloud cover rises
- clouds darken as conditions become more threatening
- rain appears and becomes heavier as precipitation increases
- fog reduces visible depth
- wind changes trees, water, flags, clouds, birds, or other scene elements
- sun and moon cues follow supported events; exact paths require an adopted position model
- daylight and twilight evolve naturally
- snow accumulates visually where appropriate
- visibility changes alter distant scene elements

The user should develop visual familiarity with a scene over time.

Eventually the scene itself communicates:

- rain approaching
- wind increasing
- skies clearing
- visibility degrading
- cloud cover increasing
- nighttime approaching
- precipitation becoming heavier

Exact values remain accessible where precision matters.

# 3. Interaction Model

The primary interaction pattern is:

**Observe → Touch → Learn → Dismiss**

## 3.1 Level 0 — Experience

The scene communicates current and future conditions visually.

Only essential numeric information should remain immediately visible.

## 3.2 Level 1 — Inspect

Tapping a meaningful scene element reveals concise information.

Examples:

- moon → supported phase and rise/set information; other values require an adopted source/model
- cloud layer → coverage and relevant meteorological information
- rain → precipitation probability, intensity, accumulation
- weather station → current conditions
- windsock → wind direction and speed

## 3.3 Level 2 — Understand

The user expands the interaction to learn why the condition exists or how it is changing.

Examples:

- pressure trend
- cloud formation explanation
- precipitation timing
- wind trend
- moon trajectory
- visibility changes

## 3.4 Level 3 — Nerd Out

Advanced meteorological and astronomical information becomes available without being forced into the default interface.

Examples may eventually include:

- detailed forecast graphs
- atmospheric profiles
- pressure trends
- model information
- advanced wind information
- cloud structure
- astronomy tracks
- severe-weather parameters

Casual users should never be required to interact with this depth.

# 4. Scene Graph

Every scene is divided into three absolute depth groups:

```text
Background
Midground
Foreground
```

The absolute ordering rule is:

```text
Background < Midground < Foreground
```

No physical object in a lower depth group may render above a higher physical group. Precipitation declares the depth of each field. Time wash and post-processing are named compositing passes, not physical depth exceptions.

Each depth group contains multiple ordered layers.

```text
Scene
├── Background
│   ├── Layer 0
│   ├── Layer 1
│   └── Layer n
│
├── Midground
│   ├── Layer 0
│   ├── Layer 1
│   └── Layer n
│
└── Foreground
    ├── Layer 0
    ├── Layer 1
    └── Layer n
```

Rendering order is determined by:

```text
Depth Group → Layer Order → Element Order
```

This structure must support scenarios such as:

- birds behind buildings
- buildings behind trees
- moon behind forest silhouettes
- clouds behind mountains
- distant precipitation behind nearby terrain
- foreground rain passing in front of environmental objects

# 5. Scene Elements

A world defines persistent elements with stable local identity, semantic role, depth group, local order, logical transform/anchor, optional asset, environmental input slots, optional data surface and explicit unknown fallback. Reuse is established from concrete second-world evidence, without a universal registry. The world contains no provider DTOs or meteorological interpretation.

Examples include:

- Sky
- Sun
- Moon
- Stars
- CloudField
- Fog
- Haze
- Rain
- Snow
- Lightning
- BirdFlock
- Boat
- Water
- MountainRange
- Forest
- Tree
- Grass
- Building
- CitySkyline
- WeatherStation
- Billboard
- WindSock
- TrailSign
- Buoy

Elements should be reusable across many different environments.

A scene element may be:

- purely decorative
- meteorologically responsive
- semantic
- interactive
- data-bearing

Not every element should be interactive.

# 6. Element Roles

Elements should carry stable semantic roles independent of their implementation.

Possible roles include:

- Sky
- Celestial
- PrimaryClouds
- SecondaryClouds
- DistantPrecipitation
- NearPrecipitation
- Terrain
- Water
- Buildings
- Vegetation
- Wildlife
- Atmosphere
- Instrumentation
- DataSurface
- Effects

Semantic roles allow the engine to:

- replace equivalent scene elements
- apply weather behavior consistently
- identify interactive elements
- control visibility
- test scene completeness
- support different scene packs using the same weather state

# 7. Scene Component Library

Oxygen should maintain a reusable library of Kotlin/Compose drawing components.

Conceptually:

```kotlin
drawSky(...)
drawSun(...)
drawMoon(...)
drawCloudField(...)
drawRain(...)
drawSnow(...)
drawFog(...)
drawLightning(...)
drawBirdFlock(...)
drawBoat(...)
drawForest(...)
drawMountainRange(...)
drawCitySkyline(...)
drawBillboard(...)
drawWindSock(...)
drawWeatherStation(...)
```

Large argument lists should be avoided.

Components should receive state objects.

Example:

```kotlin
drawBirdFlock(
    BirdFlockState(
        count = 7,
        direction = WEST,
        speed = 0.6f,
        altitude = 0.32f
    )
)
```

The component library knows how to render.

It must not decide why a weather condition exists or how meteorological values map to visual state.

# 8. Hybrid Rendering Strategy

Oxygen should not require every visual object to be procedurally drawn.

Three asset categories are expected.

## Procedural

Best suited for:

- precipitation
- stars
- fog
- haze
- atmospheric gradients
- lighting
- simple cloud systems
- particles
- motion paths
- dynamically generated text

## Authored Vector

Best suited for:

- buildings
- trees
- mountains
- boats
- birds
- signs
- instruments
- terrain silhouettes
- foreground framing

## Raster

Acceptable where visual complexity makes vector rendering unnecessarily expensive.

Raster assets should not become the default solution where dynamic coloring, animation, scaling, or interaction is required.

# 9. Scene Composition API

Scene construction may become declarative after two concrete worlds prove the required boundary. A `WorldDefinition` supplies logical bounds, authored elements and camera priorities; a resolved scene supplies selected instant/revision, placements, draw operations, transformed hit regions and accessible actions.

A conceptual Kotlin DSL may eventually resemble:

```kotlin
scene {
    background {
        sky()
        moon()
        distantClouds()
    }

    midground {
        mountains()
        birds()
        lake()
    }

    foreground {
        forest()
        rain()
        weatherStation()
    }
}
```

The exact DSL syntax is not required for the first implementation.

The architectural requirement is that scenes should be composed from reusable elements rather than having dedicated rendering logic duplicated for every scene.

# 10. Scene Packs

The commercial product is not traditional color themes.

Oxygen will sell **scene packs**.

Scene packs provide different worlds through which weather is experienced.

Potential packs include:

- scientific observatory
- space observatory
- forest lake
- mountain valley
- coastal harbor
- rural farm
- downtown skyline
- suburban neighborhood
- desert
- snowy field
- campground
- retro weather station
- pixel village
- floating islands
- cyberpunk harbor
- cozy cabin
- storm lab

Regional packs can also exist.

Scene identity must remain independent from weather location.

The location supplies meteorological reality.

The selected scene supplies its visual interpretation.

# 11. Default Scene

The Living Urban Park reference is one logical eye-level world: curved path, left-wall mural, trees/benches, skyline and optional distant sign/water tower. The approved OX-21 viewport states are portrait phone, landscape phone and unfolded fold posture. For each state, record viewport dimensions, font scale, camera scale, the mural main reading's resulting sp equivalent and an installed capture. A camera fits the mural and timeline safe area, reveals optional world area on wider viewports, and avoids insets/hinge. In each approved viewport, the mural's main reading must render at least 16 sp equivalent after camera scaling. Preserve the central readout, timeline and recognizable park entrance; simplify optional scenery first. If these cannot fit, OX-21 fails its exit and the layout must be revised. Portrait crops optional distant objects; landscape and unfolded posture reveal more world. The central readout remains the primary immediate access to essential forecast facts. This is a proposed layout contract, not approved artwork.


The default scene should be geographically and culturally neutral enough for worldwide use.

A **Living Urban Park** is the proposed default reference world because it supports:

- meteorology
- astronomy
- authored information surfaces
- weather education
- interactive objects
- embedded data displays

The default scene should demonstrate the capabilities of the scene engine without requiring a paid pack.

The free experience must remain complete and useful.

Paid scenes provide alternate environments, moods, interactions, and visual interpretations rather than unlocking basic forecast accuracy.

# 12. Weather Interpretation

Rendering consumes typed selected-instant decisions and exact information bindings rather than provider DTOs. The current source-backed path is:

```text
CatalogActivationCoordinator -> canonical catalog + expected support + selected Instant
  -> CatalogTimelinePresenter: SemanticScenePlan once per active catalog
  -> SemanticScenePlan.evaluate(selected Instant): SceneReading
      |-> shared environmental decisions -> FirstCityTheme/world composition -> Canvas
      |-> CanonicalForecastProjection -> exact information bindings -> text/world surfaces
  -> CatalogTimeLabScreen interaction host; AccessibleForecastBrowser alternate exact access
```

`SceneReading` already supplies typed weather, evidence, light and astronomy tracks; it does not carry every exact display field, including temperature and wind. An information-binding adapter reuses `CanonicalForecastProjection` for supported values, units and source meaning. Both branches use one selected instant and canonical revision. `ScenePoint`/`SceneInterval` own sample-valid time and evidence. `FirstCityTheme` owns city treatment and geometry; drawing owns clipping and artwork, not forecast meaning. The composition overload is the primary catalog renderer; reading/state overloads are catalog fallbacks, and fixture/Time Lab renderers are previews. For each catalog fallback, OX-02 records its caller, triggering state and source location; mark reachability **unverified** wherever evidence is incomplete. Retain fallbacks through OX-02; removal requires a separately scoped consolidation with parity evidence.

# 13. Meteorological Faithfulness

The scene is an interpretation of meteorological information, not a literal physical simulation.

Visual exaggeration is acceptable where necessary for clarity.

The scene should preserve:

- direction
- relative intensity
- timing
- transitions
- meteorological meaning

It should avoid implying false precision.

# 14. Time and Catalog Model

The current environmental catalog contains approximately:

- 30 historical days
- current day
- 14 forecast days

The historical window is expected to increase.

With 24 hourly states across 44 days:

```text
44 × 24 = 1,056 hourly scene inputs
```

The engine should not pre-render 1,056 complete images.

Instead, semantic tracks should be prepared once per active catalog/revision and evaluated at the selected instant. The existing full-window `SemanticScenePlan` is the baseline; changing compilation granularity requires OX-17 measurement.

Core rule:

> **Every catalog entry is scene-ready. Only the visible scene is frame-ready.**

# 15. Compiled Scene Days

A compiled-day model is a future optimization candidate, contingent on OX-17 measurements. It is not a first-refactor requirement.

Conceptually:

```text
CompiledSceneDay
├── scene definition
├── deterministic seed
├── static element references
├── weather keyframes
├── astronomical tracks
├── interaction metadata
├── transition information
└── scene events
```

Scrubbing should not require expensive interpretation work that can be completed earlier.

# 16. Timeline Interpolation

Supported selected-time facts retain source sample-valid time. Visual properties may interpolate between supported keyframes; exact displayed values do not become invented minute-by-minute measurements.

This permits continuous visual evolution without storing every possible minute.

The same system should support:

- automatic playback
- manual timeline scrubbing
- historical navigation
- forecast navigation

# 17. World, Atmosphere, and Time

The scene engine should conceptually separate:

## World
Persistent environmental structure.

## Atmosphere
Weather-driven state.

## Time
Time-dependent and astronomical state.

These combine through the scene resolver:

```text
World
   +
Atmosphere
   +
Time
   ↓
SceneState
```

# 18. Astronomy

Astronomy should be treated as part of the world rather than as a decorative weather icon.

Only where an adopted source or model supports a field may the engine track:

- sun position
- moon position
- sunrise
- sunset
- moonrise
- moonset
- lunar phase
- illumination
- twilight

Clouds and atmospheric conditions determine visibility.

# 19. Occlusion

The rendering model must support natural painter-style occlusion.

Examples:

- moon behind forest
- birds behind buildings
- mountains behind clouds
- precipitation behind foreground vegetation

More sophisticated masking may be added when required.

# 20. Diegetic Weather Data

Important weather measurements may appear as objects inside the scene rather than floating UI.

Examples:

- billboards
- weather stations
- trailhead signs
- airport displays
- windsocks
- marina boards
- buoys
- boat instrumentation
- observatory displays
- clocks
- road signage
- aircraft markings
- tail numbers
- registration numbers

These are **diegetic data surfaces**.

One typed exact-reading binding derives from the existing forecast projection. It carries the selected instant, source-valid time, supported value and display unit, provenance, carried status, and an explicit unavailable state; existing presentation supplies the display text and accessible equivalent. The central readout and mural consume this same binding, so neither surface independently converts units, selects a sample or infers a value. Other surfaces, such as a billboard or water-tower sign, may use the same contract with different visual treatments. For OX-12A, the mural supplements the central readout: both show the same selected-time reading with consistent units and source-time disclosure. DEMO DATA and carried source-time disclosures remain truthful. The central readout remains primary immediate access to essential forecast facts.

This is expected to become part of Oxygen's visual charm.

# 21. Essential Numeric Information

Some information may remain visible because immediate precision is useful.

Possible candidates:

- current temperature
- wind speed/direction
- precipitation
- humidity
- current condition

The adopted central temperature, symbol and condition readout remains primary immediate access to essential forecast facts. The mural supplements it and shows the same selected-time reading, units and source-time disclosure. Any later removal or reduction requires a separate owner decision, a bounded change to the experience specification and verified alternate exact access.

# 22. Interactive Scene Elements

Timeline touches remain timeline-owned. A completed tap on a visible, enabled scene object opens its inspection; an unclaimed main-view tap retains the adopted hourly collapse. Main-view horizontal swipes and double taps retain their adopted behavior. Tap outside the inspected object or use Back to dismiss. When selected time changes, keep inspection open and update its facts for that instant; dismiss only if the object is no longer available. An available object with unavailable facts remains inspectable and shows explicit unavailability. Occluded/offscreen objects have no visual hit target, while essential facts remain reachable through text. Provide an accessible inspect action for each available inspectable object. The interaction board must specify hit regions and verify these rules without changing timeline gesture ownership.


Interactive elements should expose semantic information.

Preferred first proof of concept: the Moon.

Potential lunar details, shown only when supported by adopted data or model:

- phase
- illumination
- altitude
- azimuth
- moonrise
- moonset
- future path
- past path
- timeline trajectory graph

Interaction must be intentional.

Not every scene object is a button.

# 23. Scene Element Categories

Scene objects should broadly fall into four behavioral categories:

- Environmental
- Informational
- Inspectable
- Deep

# 24. Educational Design

Education should emerge through exploration rather than mandatory tutorials.

The same scene should serve casual users and weather enthusiasts.

# 25. Performance Strategy

Compose should host the application and scene runtime.

The primary animated environment should preferably render through Canvas rather than creating hundreds of independent composables.

Conceptually:

```text
SceneState
    ↓
SceneRenderer
    ↓
Canvas
```

Individual particles should not be separate composables.

# 26. Scene Caching

Three conceptual readiness levels are recommended.

## Level 1 — Compiled
Immutable semantic preparation for the active catalog/window; day compilation only after measured need.

## Level 2 — Graphics Prepared
Recently used or nearby scenes.

## Level 3 — Frame
Only currently visible output.

The application should not maintain thousands of complete rendered bitmaps solely to support timeline scrubbing.

# 27. Determinism

Procedural scene generation should be deterministic where practical.

Procedural randomness should use stable seeds from world, element, catalog revision and stable sample identity. Ambient motion follows frame time independently of selected-time progression and honors reduced-motion settings.

This avoids clouds, stars, birds, and other procedural elements rearranging arbitrarily whenever the user revisits the same timeline position.

# 28. Oxygen Time Lab

Oxygen Time Lab is the internal scene-development and simulation environment.

It should be treated as core development tooling rather than disposable debug UI.

Its purpose is to generate artificial environmental states and validate scenes.

It should eventually support:

- time manipulation
- weather overrides
- astronomy
- scene selection
- layer inspection
- simulation presets
- arbitrary transitions
- stress tests
- extreme conditions

# 29. Transition Testing

Time Lab must make it easy to test transitions, not only static weather states.

Critical transitions include:

- clear → cloudy
- cloudy → rain
- rain → snow
- day → sunset
- sunset → night
- calm → high wind
- fog appearing
- fog clearing
- moon visible → moon obscured
- storm → clearing

# 30. Layer Inspector

Time Lab should eventually expose scene composition with capabilities such as:

- hide/show element
- highlight bounds
- show hit target
- inspect layer order
- display values driving an element
- identify semantic role

# 31. Scene Pack Validation

Because scene packs are commercial products, Time Lab should eventually support repeatable scene QA against a weather matrix.

# 32. Scene Pack Boundary

Scene packs should eventually have a defined package format.

Conceptually:

```text
ScenePack
├── Manifest
├── SceneDefinitions
├── ElementReferences
├── Assets
├── Typography
├── AnimationProfiles
├── Localization
└── CompatibilityVersion
```

Downloaded scene packs should preferably use a constrained declarative format rather than arbitrary executable Kotlin.

# 33. Business Model

The core weather application remains free.

Paid content consists primarily of scene packs.

> Oxygen provides the weather. Scene packs determine the world through which the user experiences it.

Core forecast correctness and basic weather access should not depend on purchasing a scene.

# 34. International Design

The scene system should be capable of supporting global environments while the engine itself remains independent of region.

# 35. Non-Goals

The first scene-engine refactor does not need to provide:

- photorealistic graphics
- physically accurate fluid simulation
- complete atmospheric simulation
- 3D rendering
- game-engine physics
- every possible scene pack
- every possible weather interaction
- a public scene-authoring marketplace

The objective is a robust 2D illustration and interaction framework driven by environmental data.

# 36. First Refactor Scope

Recommended priorities:

1. Define the scene graph.
2. Enforce Background → Midground → Foreground ordering.
3. Define reusable scene elements.
4. Introduce semantic element roles.
5. Separate weather interpretation from rendering.
6. Establish scene state/keyframe structures.
7. Convert existing clouds and precipitation into reusable elements.
8. Introduce deterministic procedural generation.
9. Measure the full-window semantic plan before proposing day compilation or cache redesign.
10. Implement one complete interactive semantic element.
11. Use the Moon as the preferred first deep-interaction proof of concept.
12. Add Time Lab layer inspection.
13. Only then expand weather-event coverage.

# 37. Architectural Principle

```text
           CANONICAL CATALOG + SELECTED INSTANT
                       │
                       ▼
          SceneReading + ForecastProjection
                       │
                       ▼
           Environmental decisions + exact bindings
                       │
             ┌─────────┼─────────┐
             │         │         │
           World   Atmosphere   Information
             │         │         │
             └─────────┼─────────┘
                       ▼
                   SceneState
                       │
           ┌───────────┼───────────┐
           ▼           ▼           ▼
      Background   Midground   Foreground
           │           │           │
           └───────────┼───────────┘
                       ▼
                 Scene Renderer
                       │
                       ▼
                  Compose Canvas
                       │
                       ▼
               Interactive World
```

# 38. Product Principle

The guiding test for future features should be:

> **Can this information be expressed by, embedded within, or accessed through the scene?**

If yes, that approach should be considered before introducing another permanent card, graph, gauge, or overlay.

The scene is not a background for the weather application.

**The scene is the weather application.**


# Short decision log — proposed, not adopted

- **Exact reading (owner decision, 2026-11-14):** One typed binding derives from `CanonicalForecastProjection` and carries selected instant, source-valid time, value/unit, provenance, carried status and explicit unavailable state. The central readout and mural consume it. OX-12/OX-12A still require implementation and verification of agreement.
- **Depth:** Physical Background < Midground < Foreground; post-processing is named separately. Owner review remains required.
- **Readout (owner decision, 2026-11-14):** The mural supplements the central readout. Both show the same selected-time reading, units and source-time disclosure; the central readout remains primary immediate access. Any later removal or reduction requires a separate owner decision and verified alternate access.
- **Inspection (owner decision, 2026-11-14):** Tap a visible object to inspect; tap outside or use Back to dismiss. On selected-time change, update facts while available and dismiss when the object is unavailable. Provide an accessible inspect action.
- **Viewport (owner decision, 2026-11-14):** OX-21 approves portrait phone, landscape phone and unfolded fold posture. For each, record dimensions, font scale, camera scale, resulting mural sp equivalent and installed capture; the minimum is 16 sp equivalent. Preserve central readout, timeline and park entrance, simplifying optional scenery first. Failure to fit fails the exit.
- **Renderer routes (owner decision, 2026-11-14):** Composition is primary catalog rendering; reading/state renderers are catalog fallbacks; fixture/Time Lab routes are previews. OX-02 records each fallback's caller, triggering state and source location, marking incomplete reachability unverified. No removal in OX-02; consolidation needs separate scope and parity evidence.
- **Roadmap IDs/order (owner decision, 2026-11-14):** Retain OX-12A, OX-21 and OX-22; keep OX-18 as an early minimal second-world proof. A separate bounded governance cycle reviews the current canonical roadmap and places each OX leaf after its actual prerequisites, preserving the existing global NEXT unless the owner explicitly changes it.
- **Performance:** Retain the full-window plan until OX-17 measurements justify changing it.
- **Astronomy:** Exact celestial paths and unsupported lunar fields remain deferred pending an adopted source/model.
- **Adoption:** These proposals require the separate bounded governance cycle described above; no roadmap status, board approval, implementation or device acceptance is asserted here.
