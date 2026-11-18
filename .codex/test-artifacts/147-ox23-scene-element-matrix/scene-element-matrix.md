# OX-23 current scene-element matrix

This inventories current rendering only. Source paths below are relative to the repository root. `C` = `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt` (`FirstCitySemanticInventory`, `FirstCityLayer`); `T` = `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` (`FirstCityTheme.compose`, `layers`); `R` = `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` (`CatalogAtmosphericScene`, `CatalogOpenLandscapeScene`). Group/order are the typed `FirstCityLayer` values, not an authored pack schema. The visible layer sequence is selected by `T:compose` and drawn by `R:CatalogAtmosphericScene`; optional layers may be absent at an instant.

| World / current object (identity / role) | Source identity and depth/pass | Actual draw and bitmap/primitive | Current input owner | Disposition |
| --- | --- | --- | --- | --- |
| First City sky backdrop (`SKY_BACKDROP` / `SKY`) | `C:FirstCitySemanticInventory`; Background 0 `SKY` | `R:CatalogAtmosphericScene`: `drawSkyPalette` plus rain-only gradient when sky exists; otherwise `drawCityWorld`; Compose primitives | `SceneWeatherDecision.sky`, clouds, precipitation; `T:compose` treatment for fallback | Context |
| First City star field (`STAR_FIELD` / `CELESTIAL_FIELD`) | `C`; Background 1 `STARS` | `R`: `drawCityStars`; procedural points | `T:compose` treatment | Context |
| First City sun cue (`SUN_CUE` / `TIME_CUE`) | `C`; Background 2 `TIME_CUE` | `R`: conditional `drawCityTimeCue(composition.cue, ...)`; procedural cue | `T:compose` selected-time cue position/visibility from `SceneReading`, zone, treatment, precipitation | Context |
| First City moon cue (`MOON_CUE` / `LUNAR_CUE`) | `C`; Background 3 `LUNAR_CUE` | `R`: conditional `drawCityTimeCue(composition.moonCue, ...)`; procedural cue | `T:compose` selected-time lunar cue from `SceneReading` | Context |
| First City cloud cover (`CLOUD_COVER` / `CLOUD_FIELD`) | `C`; one identity, Background 4 `BACKGROUND_CLOUDS` and Middle-Ground 1 `MIDDLE_GROUND_CLOUDS` | `R`: `drawCityDepthCloudLayer` twice at different depths; procedural clouds | `SceneWeatherDecision.clouds`; renderer reads reduced-motion setting | Context; one object, two passes |
| First City distant skyline (`DISTANT_SKYLINE` / `CITY_STRUCTURE`) | `C`; Background 5 `BACKGROUND_ARCHITECTURE` | `R`: optional `drawLandscape` horizon plus `drawAlignedLayer(architecture)` from `R.drawable.sg2_city_architecture` | Shared sky/cloud/precipitation for horizon; fixed architecture bitmap | Context |
| First City diffuse atmospheric light (`DIFFUSE_ATMOSPHERIC_LIGHT` / `ATMOSPHERE`) | `C`; Middle-Ground 0 `ATMOSPHERE` | `R`: conditional `drawCityDiffuseLight`; Compose gradient | `SceneWeatherDecision.sky` gates it; `T:compose` cue/treatment and shared cloud fraction shape it | Context |
| First City near city (`NEAR_CITY` / `CITY_STRUCTURE`) | `C`; Foreground 0 `FOREGROUND_ARCHITECTURE` | `R`: `drawCityForegroundLayer(architecture)` from the same `sg2_city_architecture` bitmap | Fixed bitmap, world alignment | Context |
| First City canopy (`CANOPY` / `VEGETATION`) | `C`; Foreground 1 `CANOPY`; `T:layers` | `R`: `drawAlignedLayer(canopy)`; `R.drawable.sg2_city_canopy` in `app/src/main/res/drawable-nodpi/sg2_city_canopy.png` | Fixed bitmap, world alignment | **First asset-substitution candidate** for OX-24 |
| First City window details (`WINDOW_DETAILS` / `LIGHT_DETAIL`) | `C`; Foreground 2 `WINDOW_LIGHTS` | `R`: conditional `drawCityWindowLights`; Compose primitives | `T:compose` DUSK/NIGHT treatment; renderer `omitOptionalCityLayers` | Context |
| First City precipitation field (`PRECIPITATION_FIELD` / `WEATHER_FIELD`) | `C`; Foreground 3 `PRECIPITATION` | `R`: gradient overlay; `RAIN` → `drawCityRainField`, `SNOW` → `drawCitySnowField`, `NONE`/`STORM` → no precipitation marks | `SceneWeatherDecision.precipitation`, clouds for rain start; `T:compose` adds pass when `hasWeatherState` | Context |

`T:orderedLayerSequence` inserts `TIME_WASH` and `READOUT_QUIET_ZONE` after `CANOPY`; `R` draws them as compositing passes. They have null depth/order in `C:FirstCityLayer` and no semantic identity in `FirstCitySemanticInventory`. The precipitation pass can still draw its overlay when present and the decision is `NONE` or `STORM`; that overlay is not a separate semantic object.

## Minimal Open Landscape

`app/src/main/java/com/oxygen/weather/timeline/presentation/scene/MinimalOpenLandscapeComposition.kt` (`MinimalOpenLandscapeComposition`, `MinimalOpenLandscapeTheme.compose`) holds the same `SceneWeatherDecision`; it declares no equivalent element ID, semantic role, or formal depth/local order. All rows below have **identity, role, formal depth/order: not typed**. Their sequence is the current draw-call order in `R:CatalogOpenLandscapeScene`, not a typed depth contract.

| Descriptive current draw | Source and actual call / primitive | Input owner | Disposition |
| --- | --- | --- | --- |
| Sky | `R:CatalogOpenLandscapeScene`: fallback gradient `drawRect` if no sky, else `drawSkyPalette`; RAIN adds a sky-tint gradient `drawRect` | Shared sky, cloud fraction, precipitation | Context |
| Cloud field | `R:CatalogOpenLandscapeScene`: conditional `drawOpenLandscapeClouds`; procedural clouds | Shared cloud decision; renderer reduced-motion setting | Context |
| Terrain | `R:CatalogOpenLandscapeScene`: `drawOpenLandscapeTerrain`; procedural terrain | Fixed world geometry/colors | Context |
| Left tree group | `R:CatalogOpenLandscapeScene`: `drawOpenLandscapeTreeGroup(centerX = .20f, scale = 1f)`; procedural trees | Fixed world geometry | Context |
| Right tree group | `R:CatalogOpenLandscapeScene`: `drawOpenLandscapeTreeGroup(centerX = .78f, scale = .92f)`; procedural trees | Fixed world geometry | Context |
| Precipitation field | `R:CatalogOpenLandscapeScene`: `RAIN` → `drawCityRainField(startsAtCloudBase = weather.clouds.visible)`; `SNOW` → `drawCitySnowField`; `NONE`/`STORM` → no precipitation draw | Shared precipitation decision and cloud visibility | Context; conditional branches, one descriptive entry |

## Completed contract crosswalk and current ownership

| Contract | Reused boundary and source |
| --- | --- |
| OX-04 scene truth | `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt` (`SceneReading`, `SceneEvidence`, per-field `sourceValidTime`) retains selected-instant and evidence origin. `SceneWeatherDecision.resolve` uses these fields; the inventory does not convert carried or unknown evidence into newly supplied facts. See `.codex/concepts/OX-04-scene-truth-behavior-board.md`. |
| OX-05 depth | `C:FirstCityDepthGroup` and `FirstCityLayer`, sorted in `T:orderedLayerSequence`, govern Background → Middle-Ground → Foreground and local order. The Foreground precipitation field follows near architecture and canopy. See `.codex/concepts/OX-05-debug-astronomy-sidecar-behavior-board.md` and current source. |
| OX-06 identity | `C:FirstCitySemanticInventory` declares 11 First City-local IDs and roles, distinct from passes. `CLOUD_COVER` maps to two passes. See `.codex/concepts/OX-06-scene-element-identity-behavior-board.md`. |
| OX-18 shared decision | `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneWeatherDecision.kt` (`resolve`) owns one selected-instant sky/cloud/precipitation decision. `T:compose` and `MinimalOpenLandscapeTheme.compose` consume it without a second weather interpreter. See `.codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md`. |

Required now: current object identity/role where typed; current pass and order; source and primitive/bitmap; weather/cue input owner; and first substitution candidate. Deferred outcomes: authored identity/depth representation (OX-34), logical geometry and anchors (OX-35), typed weather slots and unknown fallback (OX-36), adapter to a minimum declaration (OX-38), actual asset swap (OX-24), and second-world declaration reuse (OX-92). No pack manifest, hit metadata, or universal registry is inferred here.

Uncertainty: no authored element boundaries or typed depth/role for the open landscape exist yet. The city architecture bitmap participates in both distant and near draws; its exact pixel segmentation is outside this inventory. The canopy candidate identifies a current one-pass asset boundary, not an approved replacement design.
