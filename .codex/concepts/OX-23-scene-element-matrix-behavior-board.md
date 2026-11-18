# OX-23 behavior board — authorable scene-element inventory

**Status:** Product-owner approved on 2026-11-18 for cycle `147-ox23-scene-element-matrix`, with no additional constraints stated. The DOT source and its SVG/PNG renders form the visual board package. This note supplies the source and row contract; approval does not constitute installed acceptance evidence.

## Approved decision

Create a source-linked inventory of **current** meaningful objects in First City and Minimal Open Landscape. Use the existing First City IDs and roles where they exist. Use descriptive object labels for Minimal Open Landscape, whose current composition has shared weather decisions but no typed semantic element inventory. Do not assign a future cross-world ID or authored schema in this slice.

One matrix row records: world; current object or descriptive label; identity and role if already typed; source path and symbol; current depth group/local order or `not typed`; draw pass/call; weather or cue input owner; bitmap/primitive source; and whether that row is a candidate for the first asset substitution, context only, or deferred. Record uncertainty explicitly rather than filling unsupported fields. Group multiple passes under one semantic object where the current identity says so.

**First substitution candidate:** First City `CANOPY` / `VEGETATION`, in the Foreground `CANOPY` pass. `AtmosphericScene.kt` loads `R.drawable.sg2_city_canopy` and renders it through `drawAlignedLayer(canopy)`. This one-object, one-pass bitmap boundary is a candidate for later OX-24; OX-23 does not edit or replace the asset. A later change to this candidate requires review of the affected approved board input.

## Required coverage and source anchors

| Current source | Inventory obligation |
| --- | --- |
| `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt` (`FirstCityDepthGroup`, `FirstCityLayer`, `FirstCitySemanticInventory`) | Include all 11 current First City semantic objects, their roles and mapped passes. `CLOUD_COVER` has one identity across `BACKGROUND_CLOUDS` and `MIDDLE_GROUND_CLOUDS`. Distinguish physical order from identity. |
| `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` (`compose`) and `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` (`CatalogAtmosphericScene`) | Trace actual ordered pass and draw-call ownership, including the canopy bitmap in `app/src/main/res/drawable-nodpi/sg2_city_canopy.png`. `TIME_WASH` and `READOUT_QUIET_ZONE` remain compositing passes rather than semantic objects. |
| `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/MinimalOpenLandscapeComposition.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` (`CatalogOpenLandscapeScene`) | Record current sky, clouds, terrain, two procedural tree groups, and precipitation draw calls. Mark identity, semantic role, and formal depth/order as `not typed` where the implementation has no equivalent declaration. |
| `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SceneWeatherDecision.kt` | Name the existing shared owner of selected-instant sky, cloud, and precipitation decisions. Preserve the reading's source evidence; do not re-interpret provider data in the inventory. |
| `.codex/concepts/OX-04-scene-truth-behavior-board.md`, `OX-05-debug-astronomy-sidecar-behavior-board.md`, `OX-06-scene-element-identity-behavior-board.md`, and `OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.md` | Reuse completed evidence, ordered depth, First City-local identity, and two-world shared-decision contracts as constraints. Approval of those boards does not approve this one. |

## Field disposition and exit

The inventory needs **current** identity/role, depth/pass, input owner, source location, and candidate status to support the first substitution decision. Future authored identity/depth representation (OX-34), geometry/anchors (OX-35), typed weather-input slots (OX-36), adapter (OX-38), actual swap (OX-24), and second-world reuse (OX-92) remain their own outcomes. Pack manifests, hit metadata, and a universal registry are outside OX-23.

OX-23 closes only when the actual source-linked matrix and deferred-field list are recorded under `.codex/test-artifacts/147-ox23-scene-element-matrix/`, with a closeout at `.codex/history/147-ox23-scene-element-matrix.md`. The matrix must identify the object, asset or primitive, and pass needed for the first substitution while keeping the completed OX-05/OX-06/OX-18 boundaries traceable. This slice changes no code or visible UI, so no UI artwork board applies. The board is planning material, not the finished inventory.
