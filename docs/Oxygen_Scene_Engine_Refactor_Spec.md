# Oxygen Scene Engine — First-Draft Product and Architecture Specification

## Status

First-draft product and architecture specification.

This document defines the intended direction of the Oxygen scene system before analysis of the current implementation. Existing code should be evaluated against this specification rather than assumed to already follow it.

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
- the sun and moon follow their actual paths
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

- moon → phase, illumination, rise/set information
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

No layer inside a lower depth group may render above a higher depth group.

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

A scene is composed from reusable scene elements rather than bespoke scene implementations.

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

Scene construction should ultimately become declarative.

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

The default scene should be geographically and culturally neutral enough for worldwide use.

A **Scientific Observatory** or similar environment is currently the leading conceptual default because it naturally supports:

- meteorology
- astronomy
- instruments
- weather education
- interactive objects
- embedded data displays

The default scene should demonstrate the capabilities of the scene engine without requiring a paid pack.

The free experience must remain complete and useful.

Paid scenes provide alternate environments, moods, interactions, and visual interpretations rather than unlocking basic forecast accuracy.

# 12. Weather Interpretation

Rendering code should not directly interpret raw weather data.

The intended pipeline is:

```text
Weather Data
     ↓
Weather State
     ↓
Scene Interpreter
     ↓
Scene State
     ↓
Scene Renderer
```

The weather state remains universal.

The scene determines its visual language.

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

Instead, every catalog entry should be compiled into a scene-ready representation.

Core rule:

> **Every catalog entry is scene-ready. Only the visible scene is frame-ready.**

# 15. Compiled Scene Days

Each day should be compiled ahead of interaction.

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

Hourly weather values should act as keyframes.

The scene runtime should interpolate between them.

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

Where data permits, the engine should track:

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

They should receive real application data.

This is expected to become part of Oxygen's visual charm.

# 21. Essential Numeric Information

Some information may remain visible because immediate precision is useful.

Possible candidates:

- current temperature
- wind speed/direction
- precipitation
- humidity
- current condition

The preferred solution is to integrate those values into the scene.

Permanent floating widgets should be avoided unless there is a strong usability reason.

# 22. Interactive Scene Elements

Interactive elements should expose semantic information.

Preferred first proof of concept: the Moon.

Potential lunar details:

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
All catalog days.

## Level 2 — Graphics Prepared
Recently used or nearby scenes.

## Level 3 — Frame
Only currently visible output.

The application should not maintain thousands of complete rendered bitmaps solely to support timeline scrubbing.

# 27. Determinism

Procedural scene generation should be deterministic where practical.

Procedural randomness should use deterministic seeds.

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
9. Support compiled scene days across the full catalog.
10. Implement one complete interactive semantic element.
11. Use the Moon as the preferred first deep-interaction proof of concept.
12. Add Time Lab layer inspection.
13. Only then expand weather-event coverage.

# 37. Architectural Principle

```text
                  WEATHER DATA
                       │
                       ▼
                 WeatherState
                       │
                       ▼
                 SceneInterpreter
                       │
             ┌─────────┼─────────┐
             │         │         │
           World   Atmosphere   Time
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
