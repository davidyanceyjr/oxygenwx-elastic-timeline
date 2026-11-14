# Oxygen Scene Engine — First-Draft Product and Architecture Specification

## Status

First-draft product and architecture proposal. It does not authorize implementation or replace the controlling prototype and adopted experience specifications.

This document describes a possible reusable scene engine for the unnamed default scene and future scenes. [The prototype specification](SPECIFICATION.md) controls semantic and architecture conflicts; the [adopted experience specification](OXYGEN_EXPERIENCE_SPECIFICATION.md) supplies interaction and experience requirements. Completed SG/ATM/MR decisions in the [roadmap](ROADMAP.md) establish the current behavior baseline. Architectural examples below are proposals unless expressly identified as an existing contract. The current `FirstCityTheme` identifier is internal code terminology, not the product name of the default scene.

# 1. Product Vision

Oxygen is not intended to become a conventional weather dashboard placed over an animated background.

The scene itself is the primary interface.

The central design principle is:

> **The default state is the world.**

Weather should first be experienced visually through an interactive environmental scene. Critical selected-time forecast facts remain immediately readable as text with accessible meaning; a decorative scene alone cannot convey them. Additional measurements, charts, explanations, and advanced meteorological information may appear in inspect or forecast-browser flows. The current MR.2 readout is an implementation state, not a permanent scene-engine layout requirement; changing it is outside the first engine slice.

A second governing principle is:

> **Express information in the scene where it remains truthful, immediately readable when required, and accessible; use other text presentation where those conditions cannot be met.**

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
- clouds darken in response to supported cloud and condition inputs
- rain appears for supported rain evidence; visual heaviness follows supported intensity or amount inputs only within their meaning
- fog reduces visible depth
- supported wind values may change trees, water, flags, clouds, birds, or other scene elements without inventing a direction or speed measurement
- the sun and moon cross the scene horizon at supported rise/set instants, with illustrative paths between them
- daylight and illustrative twilight lighting evolve around supported solar events
- snow coverage responds to supported snow evidence without inventing an accumulation measurement
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

Critical forecast values remain immediately readable as text. Additional exact values remain accessible where precision matters. Scene transitions illustrate change and do not establish measured onset times between samples. See the [readability and source-truth rules](SPECIFICATION.md#architecture) and [scene interpretation contract](OXYGEN_EXPERIENCE_SPECIFICATION.md#52-weather-scene).

# 3. Interaction Model

The primary interaction pattern is:

**Observe → Touch → Learn → Dismiss**

## 3.1 Level 0 — Experience

The scene communicates current and future conditions visually.

The selected local time and location, supported selected condition or its unavailable status, prominent temperature, and the small set of currently approved secondary facts remain immediately readable as text with accessible meaning. Text embedded in a scene may serve this purpose when it meets the same readability, source-time, and accessibility requirements. The precise arrangement and secondary-fact set can change through a bounded owner decision; this proposal does not make the current MR.2 layout permanent. [The adopted screen hierarchy](OXYGEN_EXPERIENCE_SPECIFICATION.md#51-screen-composition) and [controlling text rule](SPECIFICATION.md#architecture) govern until then.

## 3.2 Level 1 — Inspect

Tapping a meaningful scene element reveals concise information.

Examples:

- moon → supported phase stage and rise/set information; illumination requires a separate supported input
- cloud layer → coverage and relevant meteorological information
- rain → independently supported precipitation probability, rate, or accumulation, with unavailable values identified
- weather station → current conditions
- windsock → wind direction and speed

## 3.3 Level 2 — Understand

The user expands the interaction to learn why the condition exists or how it is changing.

Examples:

- pressure trend
- cloud formation explanation
- precipitation timing
- wind trend
- illustrative moon path, identified as illustration unless a position model supplies a measured trajectory
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

# 10. Scene Packs — Future Proposal

The commercial product is not traditional color themes.

Scene packs are a possible future product direction, not an approved part of the first engine refactor.

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

The current product scene is the unnamed **default scene**. Its established selected-time, typed-input, support, source-time, weather-meaning, gesture, cloud-region, and readable-fact behavior is the first refactor's baseline. The renderer structure may change; a replacement scene or default switch requires a separate bounded owner decision, behavior and artwork boards, and acceptance. Completed [SG.2, SG.4a, and SG.9](ROADMAP.md#sg--scene-generation) decisions are part of that baseline. `FirstCityTheme` remains an internal identifier and need not be renamed in this documentation slice.

A geographically and culturally neutral Scientific Observatory is a candidate for a future default because it could support:

- meteorology
- astronomy
- instruments
- weather education
- interactive objects
- embedded data displays

Any default scene should demonstrate the capabilities of the scene engine without requiring a paid pack.

The free experience must remain complete and useful.

Paid scenes provide alternate environments, moods, interactions, and visual interpretations rather than unlocking basic forecast accuracy.

# 12. Weather Interpretation

Rendering code should not directly interpret raw weather data. The catalog route already has a typed, support-aware selected-time scene reading and a first-theme composition boundary under [SG.1 and SG.9](ROADMAP.md#sg--scene-generation); a refactor extends that contract rather than starting from an absent interpretation layer.

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

For supported input, the scene should preserve:

- direction
- relative intensity
- timing
- transitions
- meteorological meaning

It should avoid implying false precision. Supplied categorical conditions and independently supported values control weather meaning; the scene must not reject them on its own plausibility judgment. Precipitation probability is not rainfall rate, and a visual transition does not supply an exact rain or clearing minute. These rules follow the [controlling value contract](SPECIFICATION.md#successor-direction-and-durable-boundaries) and completed [SG.4](ROADMAP.md#sg4--add-precipitation-event-tracks-and-overlays).

# 14. Time and Catalog Model

The target catalog spans 44 local calendar dates:

- D−30 through D−1: 30 historical dates
- D through D+13: the current local date and 13 later forecast dates

The local date D comes from catalog `asOf` in the selected location's IANA timezone. A larger historical window is a future proposal, not a current catalog guarantee.

If every date had 24 supplied hourly states, the arithmetic would be:

```text
44 × 24 = 1,056 hypothetical hourly scene inputs
```

Actual coverage varies with DST, partial dates, provider gaps, and independent field support. The 44-date target does not promise 1,056 hourly facts. The engine should not pre-render a complete image for every possible time. See the [date-window and missing-data contract](SPECIFICATION.md#successor-direction-and-durable-boundaries).

Instead, each available catalog entry may be prepared as a support-aware scene input. A prepared entry can still yield an unavailable hourly weather claim while retaining independently supported facts and time cues.

Core rule:

> **Prepare only supported scene inputs. Render the visible selected-time scene without inventing missing weather facts.**

# 15. Compiled Scene Days

Each day may be prepared ahead of interaction when that improves responsiveness. Preparation must preserve field support, valid times, point display cells, interval bounds, and gaps; it does not fill them.

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

Supported sampled inputs may act as keyframes for visual parameters such as palette, diffuse light, cloud pose, and opacity.

The scene runtime may interpolate those aesthetics within their support. Supplied numeric facts, categorical condition identity, point and interval boundaries, missing slots, original source times, and the authoritative selected instant remain discrete. An aesthetic blend is not a measured minute-by-minute forecast or a source event time.

This permits continuous visual evolution without storing every possible frame. An eligible interior absent expected hour may show the preceding supplied condition and scene only with the original source time and selected-hour unavailability visibly disclosed. Leading/trailing gaps, missing days, and empty or unsupported supplied conditions make no hourly condition claim. A directly supplied last expected hourly point may occupy its eligible same-date display cell until exclusive local midnight, with its source time disclosed; it never extends an absent final field or crosses into the next date. Other fields retain independent support and missing values never become zero. See [rule 6](SPECIFICATION.md#required-behaviors), [SG.4](ROADMAP.md#sg4--add-precipitation-event-tracks-and-overlays), and the [late-day decision](ROADMAP.md#sg-missing-eleventh-hour-data--resolve-the-late-day-hourly-readout-gap).

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

Astronomy can shape the world, but its scene and inspect claims must match supported inputs. The current shared catalog supplies local-date sunrise/sunset and moonrise/moonset event instants plus a daily lunar phase fraction. Supported rise/set events may govern horizon crossings; the daily fraction supports a named phase stage, not an exact transition time or a measured illumination percentage. Between-event Sun/Moon paths and apparent cue scaling are illustrative. Missing events or phase values make no corresponding claim. These boundaries follow [SG.3 and SG.8](ROADMAP.md#sg--scene-generation) and the [adopted solar-position rule](OXYGEN_EXPERIENCE_SPECIFICATION.md#52-weather-scene).

With future supported data and bounded decisions, the engine could additionally track measured Sun/Moon positions or trajectories, illumination, and twilight boundaries.

Current inputs do not establish solar or lunar azimuth/elevation, visible lunar orientation, angular size, measured illumination, or precise twilight boundaries. Clouds and atmospheric conditions may affect cue visibility without converting an illustrative path into a measured one.

# 19. Occlusion

The rendering model must support natural painter-style occlusion.

Examples:

- moon behind forest
- birds behind buildings
- mountains behind clouds
- precipitation behind foreground vegetation

More sophisticated masking may be added when required.

# 20. Diegetic Weather Data

Important weather measurements may appear as readable text on objects inside the scene when the text remains immediately legible where required, accessible, accurate for the selected time, and honest about support and source time. Other measurements may appear through inspect flows. Decorative shapes alone do not replace critical forecast text.

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

They should receive supported typed application data with its valid time and unavailable state, not inferred measurements. A carried prior-hour condition needs visible original-source-time disclosure and selected-hour unavailability even when the scene depicts that older condition. Source and freshness detail must remain reachable. See the [text and gap rules](SPECIFICATION.md#required-behaviors) and [production value gate](OXYGEN_EXPERIENCE_SPECIFICATION.md#6-forecast-values-and-unavailable-states).

This is expected to become part of Oxygen's visual charm.

# 21. Essential Numeric Information

Critical selected-time forecast facts must remain immediately readable as text. The current readout shows selected condition, temperature, and a small set of secondary values; the timeline shows selected local time. The exact MR.2 arrangement is not a permanent engine requirement, and a readout change is outside the first engine slice.

Possible immediately readable facts, subject to a bounded readout decision, include:

- current temperature
- wind speed/direction
- precipitation
- humidity
- current condition

Scene-embedded text may present these facts if it meets the same contrast, legibility, source-time, and accessibility requirements as other text. The [controlling architecture rule](SPECIFICATION.md#architecture) requires critical forecast values separately from decorative rendering.

Permanent floating widgets should be avoided unless there is a strong usability reason.

# 22. Interactive Scene Elements

Interactive elements should expose semantic information.

The Moon is a candidate first inspect interaction.

Current supported lunar inspect details:

- named daily phase stage
- moonrise
- moonset

Future details require separately supported data and a bounded design decision: measured illumination, altitude, azimuth, trajectory, past/future path, or trajectory graph. An illustrative path may be inspected only when clearly identified as illustration; daily phase and rise/set values alone cannot establish a measured lunar position, visible orientation, or exact phase-transition time. [SG.8](ROADMAP.md#sg8--add-lunar-riseset-events-and-accurate-phase-scenes) governs the current lunar cue.

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

The following are candidate outcomes for separate bounded Scene Engine slices, starting from the existing [ATM.1, SG.1, and SG.9](ROADMAP.md#sg--scene-generation) typed-scene contracts:

1. Define the scene graph.
2. Enforce Background → Midground → Foreground ordering.
3. Define reusable scene elements.
4. Introduce semantic element roles.
5. Separate weather interpretation from rendering.
6. Establish scene state/keyframe structures.
7. Refactor existing clouds and precipitation into reusable elements while preserving their approved meaning and the top-33%-of-scene cloud boundary.
8. Introduce deterministic procedural generation.
9. Support compiled scene days across the full catalog.
10. Implement one complete interactive semantic element.
11. Consider the Moon as a first inspect interaction only for its supported phase and rise/set facts; measured trajectory and coordinates require new inputs.
12. Add Time Lab layer inspection.
13. Expand weather-event coverage only through a separately bounded slice; the completed SG.11 event mapping is an existing input, not work to redo.

The first implementation slice's scope will be chosen later.

These priorities are architectural proposals, not a mandate to replace the unnamed default scene or alter its readout in the first slice. Preserve its approved selected-time, support/source-time, weather-meaning, layer, cloud-region, timeline, gesture, and readable-fact behavior while reorganizing implementation. A different default, new pack, or material scene appearance change requires its own owner-approved scope and boards. See the [hard visual-quality rule](SPECIFICATION.md#hard-visual-quality-rule) and completed [SG.2/SG.4a/SG.9 decisions](ROADMAP.md#sg--scene-generation).

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

If yes, that approach should be considered before introducing another permanent card, graph, gauge, or overlay, while preserving required immediate text, accessibility, source-time disclosure, and unavailable-state meaning.

The scene is not a background for the weather application.

**The scene is the weather application.**
