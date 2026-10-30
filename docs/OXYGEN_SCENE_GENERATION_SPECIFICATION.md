# Oxygen — Scene Generation Specification

**Document ID:** OXY-SG-001  
**Version:** 0.1 — owner-directed draft  
**Prepared:** 2026-10-29 20:47:50 America/Chicago / 2026-10-30 01:47:50 UTC  
**Status:** Drafted as the planning basis for the proposed SG roadmap. It is subordinate to `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, and bounded active cycle plans. This draft does not authorize application implementation.  
**Roadmap jump point:** See the dated handoff in `docs/ROADMAP.md`. Source: the WX → ATM → ENV roadmap. Destination: SG.0 proposal review followed by SG.1–SG.7 after WX.28.  
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
- The WX workstream remains first in sequence. WX.26 follows active WX.25; the SG workstream begins only after WX.28 closes, unless the owner explicitly changes that order.

The specification addresses forecast-event timing, asset references, semantic layers, theme-specific composition, temporal transitions, deterministic planning, asset loading, and rendering boundaries. It does not authorize new forecast providers, climate datasets, alerts, location permissions, or changes to timeline gestures.

## 3. Product behavior

The selected forecast instant is the scene's authoritative reading point. Moving it along the time tape updates the scene to show the forecast pattern at and around that time. The renderer may animate between supported scene states so the user can perceive event onset, continuation, and clearing.

Examples of intended behavior:

- Scrubbing from a dry interval into a forecast rain interval brings the rain layer into the scene at the corresponding forecast time; scrubbing beyond the interval removes it.
- Moving through day, dawn/dusk transition, and night changes the scene's light-phase assets smoothly while weather-event timing remains intact.
- A scene can make a short forecast window visually scannable without the user reading every value. Exact precipitation probability, amount, temperature, provenance, and missingness remain visible through the app's text presentation.

The scene must distinguish a supported dry interval from unavailable forecast data. Missing data must never be rendered as an affirmative dry forecast. Probability is not certainty; probability is not amount or intensity. A decorative interpolation may smooth a visual transition but cannot create an additional meteorological fact or move a forecast event to an unsupported time.

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
                  asset reference / layer / time
                              |
               +--------------+--------------+
               |                             |
               v                             v
      Theme asset manifest          Theme composition rules
      backgrounds and variants      layer placement, order, paths
               |                             |
               +--------------+--------------+
                              v
                  Theme scene compositor
                              |
                              v
            Compose/Canvas scene at selected Instant
```

### 4.1 Scene plan generator

The generator maps supported forecast facts and explicit time/light-phase inputs to an immutable list of visual tracks. It emits semantic content, not screen geometry. A visual track contains:

- an asset ID or asset-family ID;
- a semantic `layer_name`;
- a timeline interval or time-indexed sequence;
- an optional transition policy for entering, continuing, changing variant, and exiting.

Illustrative shape, not a finalized Kotlin API:

```text
SceneTrack(
  asset = "precipitation.rain",
  layer_name = "precipitation",
  timeline = [startInstant, endInstant),
  transition = "enter/hold/exit"
)
```

The scene plan contains no x/y/z screen coordinates, theme-specific placement, provider DTOs, mutable UI state, or alternate selected-time state. It does not control gestures.

### 4.2 Theme composition

A theme defines a coherent visual world, not only a palette. It may supply:

- its background and world/viewport definition;
- semantic layer definitions and back-to-front order;
- placement regions, anchors, paths, and transforms for each layer;
- asset families and day/night/light-transition variants;
- transition style, palette, texture, and motion character;
- scale-independent and static/reduced-motion fallbacks.

The theme resolves semantic layer names to its own composition. Different themes may place the same asset family in different regions or use a different spatial structure entirely. A theme may be a landscape, city, ocean, abstract field, orbital scene, or another owner-approved setting. Neither the scene-plan format nor the renderer assumes a planet, sky, horizon, ground, or a particular screen coordinate for a weather event.

Weather meaning remains independent of the theme. A theme may change how a supported event is depicted, but may not remove, contradict, or exaggerate its forecast meaning. Theme artwork cannot imply geographic or climatological truth without supported inputs and an explicit product decision.

### 4.3 Renderer

The renderer evaluates the theme composition and generated tracks at the selected instant, draws the applicable layers, and performs only the transitions defined by the scene plan and theme. It remains passive: it owns no timeline gestures, forecast facts, or navigation state.

Implementation may begin inside the existing app package. A separate Gradle module is a later implementation choice, justified only if independent asset ownership, reuse, compilation, or packaging demonstrates a concrete benefit. The architecture requires a stable boundary, not a specific module layout.

## 5. Time and transitions

- Forecast event intervals use absolute `Instant` values and half-open bounds `[start, end)` where an interval is appropriate. They are derived only from supported forecast coverage and facts.
- Local date and local time labels use the selected location's IANA timezone. DST days may contain 23 or 25 actual hourly instants; no 24-hour assumption may be introduced.
- The selected instant is the sole authoritative scene time. Scale animation, theme changes, asset loading, and ambient motion cannot alter it.
- Day/night variants may transition through dawn/dusk or other named light phases. In the first implementation, a phase can use a documented local-time rule. It must not claim astronomical sunrise, sunset, sun angle, or moon phase unless a separately approved and supported calculation supplies that information.
- Weather-event transitions and light-phase transitions are independent tracks. For example, rain can continue unchanged while a scene moves from day through dusk into night.
- Transition easing affects rendered appearance only. It cannot change source values, validity intervals, displayed facts, or event timestamps.
- Any ambient looping motion has a separate animation phase. It may be frozen or simplified for reduced motion, lifecycle backgrounding, or a lower quality setting; it cannot advance forecast time.

## 6. Data truth and visual mapping

- Only typed presentation values may feed the generator; provider DTOs do not enter Compose or scene assets.
- Missing or unsupported facts remain unknown. Unknown is visually distinct from a supported dry/clear interval when that distinction affects the user's interpretation.
- Rain chance, thunderstorm chance, precipitation amount/rate, snow depth, and new snowfall remain separate signals. No signal may be substituted for another.
- A precipitation asset's presence, probability treatment, and intensity treatment must each have an explicit mapping to supported typed inputs. Probability alone must not create a depiction of measured intensity or certain precipitation.
- Official alerts remain outside this scene system unless a future bounded owner decision creates a separate alert subsystem.
- The scene remains subordinate to exact readable forecast values, source/product/provenance labels, and accessible text semantics. Decorative art is not a replacement for those facts.

## 7. Determinism, loading, and caching

Given the same catalog identity/revision, location timezone, selected-time window, theme identity/version, and generator version, scene planning must produce the same tracks and transitions.

The generator should prepare a compact scene plan for the relevant forecast window so scrubbing evaluates already-resolved tracks instead of rebuilding complex event relationships on every frame. Asset decoding and reusable path preparation may be cached. Do not pre-render every animation frame as a substitute for deterministic scene evaluation.

Cache identity must include every input that can change the plan, including forecast/catalog revision or `asOf` window, location timezone, theme version, and generator version. Viewport-specific layout is resolved by theme composition and must be invalidated when its relevant size or density inputs change. Stale plans must not survive a catalog replacement or location switch.

No performance claim is made in this draft. A bounded installed slice must measure scene-build latency, cache behavior, rendering cost, and timeline responsiveness on the reference device before the architecture claims fast loading or smooth scrubbing.

## 8. Accessibility and motion

The scene renderer is noninteractive and decorative to accessibility services. Forecast facts and timeline actions remain available through the existing text and accessible forecast browser. System-wide accessibility testing belongs to the later test roadmap; each SG implementation slice verifies only semantics and interaction behavior directly affected by that slice.

Reduced-motion and static scene states preserve the same weather meaning, selected-time correspondence, and transition outcome. No effect may strobe, cover essential text, capture timeline gestures, or delay scene/timeline interaction while an asset loads.

## 9. Focused acceptance scenarios

Each implementation slice owns focused acceptance for its changed behavior. Candidate scenarios include:

1. **Near-term rain scan:** From a selected location's current instant, scrub across a six-hour window containing a supported dry interval, a rain interval, and clearing. The scene shows the event at the supported time; exact probability/amount and source facts remain text; unavailable data does not look dry.
2. **Day/night continuity:** Scrub through a light-phase transition while a weather event remains active. Light assets transition while the event interval and forecast facts remain unchanged.
3. **Theme spatial independence:** Render equivalent semantic tracks in two themes with different layer placement and world structure. Track timing and forecast meaning agree while composition differs.
4. **Catalog replacement:** A replacement catalog or location produces a new plan; no tracks from the old dataset remain active.
5. **Static/reduced-motion behavior:** The same selected instant and weather meaning are visible without continuous animation.

The exact test set and evidence belong to each bounded SG plan. These scenarios are not authorization for a broad accessibility or application-wide test suite.

## 10. Open decisions for bounded plans

The following choices are intentionally deferred until the relevant slice has an approved concept and current-source review:

- concrete asset file format and asset authoring pipeline;
- final Kotlin model names and whether the implementation uses one or more Gradle modules;
- exact light-phase boundaries and whether any future source supplies explicit sunrise/sunset times;
- asset fallback policy when a theme omits a semantic layer or variant;
- cache size, eviction policy, and measured preloading window;
- selection and persistence UX for themes;
- which additional atmospheric event families receive UI boards and in what order.

Resolve these as bounded implementation decisions. Do not silently add a new weather-data source, climate model, theme selector, download service, or general-purpose graphics framework.
