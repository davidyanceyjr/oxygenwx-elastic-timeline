# OX-05 behavior board — enforce First City depth ordering

**Approval record:** Current approval status and package fingerprints are recorded in `.codex/plans/144-ox05-depth-ordering.md` and `docs/ROADMAP.md`. The product owner approved this behavior package for cycle `144-ox05-depth-ordering`; approval governs the target and acceptance, not implementation.

**Board image:** `OX-05-make-depth-ordering-enforceable-behavior-board.svg` (editable Graphviz source and PNG review render alongside).

## Objective

Make the existing First City renderer prove the absolute physical ordering `Background < Middle-Ground < Foreground`, while preserving the established meaning of weather and compositing effects. The scene is a layered view into a world: outer space and stars form the backdrop, the Sun precedes the Moon within Background, and nearer atmosphere/cloud layers may occlude the celestial field.

## Proposed layer map

The view uses absolute physical depth groups, each with ordered sublayers. Outer space is the backdrop. Stars, the Sun, and the Moon are celestial content within Background; the Sun is farther than the Moon, so the Moon may overlap it. Nearer clouds and scene content may occlude the celestial field. Moderate-rain cloud cover uses overlapping small-to-medium clusters across Background and Middle-Ground:

- **Background:** outer space → stars → `TIME_CUE` (Sun) → `LUNAR_CUE` (Moon) → `BACKGROUND_CLOUDS` → `BACKGROUND_ARCHITECTURE`. Only the outer-space backdrop and stars sit behind the celestial field; nearer background content draws after and may occlude it.
- **Middle-Ground:** atmosphere → `MIDGROUND_CLOUDS`. Cloud cover may occupy both Background and Middle-Ground depths. The moderate-rain artwork uses both depths to show overlapping cloud clusters across the sky.
- **Foreground:** `FOREGROUND_ARCHITECTURE`, `CANOPY`, and `WINDOW_LIGHTS`.
- **Precipitation:** The owner still chooses whether the existing single `PRECIPITATION` field belongs in Middle-Ground or Foreground. Either placement draws after the celestial field, so precipitation can occlude it.
- **Moderate-rain example:** Overlapping small-to-medium cloud clusters span the sky in Background and Middle-Ground; the single rain field begins below the cloud cover and spans the scene. The A/B comparison changes only whether near buildings occlude the field.
- **Compositing passes:** `TIME_WASH` and `READOUT_QUIET_ZONE` remain named passes outside the physical depth groups. Preserve their existing visual coverage and placement relative to city layers; they do not grant a physical object a depth-order exception.

The remaining open overlap choice is precipitation and near buildings: put the single rain/snow field in Foreground over the city, or in Middle-Ground so near buildings occlude it. OX-05 does not split one field into near and distant particles.

## Contract and acceptance target

- Physical groups always paint Background, then Middle-Ground, then Foreground. No physical object in a lower group may paint above a higher group.
- Within Background, outer space and stars precede the Sun; the Moon follows the Sun and may appear in front of it. Nearer background clouds/architecture, Middle-Ground atmosphere/clouds, foreground architecture, canopy, window lights, and precipitation draw after the celestial field and may occlude it.
- Cloud cover may occupy Background and Middle-Ground sublayers. The moderate-rain artwork depicts overlapping small-to-medium cloud clusters spanning both depths.
- Each group has an explicit local order, including the owner-directed cue order. This slice does not introduce universal element identity or an element registry.
- The OX-03 shared composition decisions remain the source for the selected instant, cloud visibility, and precipitation depiction. Visible-cloud rain keeps its cloud-base origin; eligible carried rain without a visible cloud decision keeps its existing field origin.
- The moderate-rain artwork shows overlapping small-to-medium cloud clusters throughout the sky across Background and Middle-Ground, with one rain field spanning the scene below the cloud cover; no rain is shown under clear sky in these visible-cloud examples.
- OX-04 support, unavailable-state, probability, and source-time meaning remain unchanged.
- OX-05 exit remains ordering assertions plus visually checked overlap cases, with no cross-depth inversion. The moderate-rain comparison shows overlapping cloud clusters across Background and Middle-Ground and the single field spanning the scene at the selected near-building depth. Review Moon-in-front-of-Sun, celestial cues against the space backdrop, and their occlusion by nearer clouds, foreground architecture, canopy, and precipitation, plus cloud/building and precipitation/near-building overlaps. Installed screenshots remain required for visual acceptance.

## Boundaries

- First City only; no second world, renderer extraction, semantic element registry, weather reinterpretation, forecast/layout redesign, or art changes beyond the owner-directed layered cloud forms.
- `TIME_WASH` and `READOUT_QUIET_ZONE` stay explicit compositing operations with their current visual semantics.
- The UI board is a schematic review target, not a device capture or installed acceptance evidence.
- Board approval will establish the remaining precipitation overlap target. It does not activate the cycle or authorize implementation.

## Source anchors

- `docs/ROADMAP.md`, OX-03 through OX-06 — dependency, scope, exit, and exclusions.
- `docs/oxygen_scene_engine_spec.md`, section 4 — proposed absolute three-group depth rule; this draft is supporting context, not adopted authority by itself.
- `docs/Oxygen_Scene_Engine_Refactor_Spec.md`, section 4 — proposed per-group local ordering and examples; likewise supporting context.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt` — current `FirstCityLayer` values and composition contract.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` — existing local `layerOrder` and First City layer metadata.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — catalog and reading-fallback draw sequences.
- `.codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md` and `.codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg` — upstream cloud/rain and shared-composition constraints.
- `.codex/concepts/OX-04-scene-truth-behavior-board.md` and `.codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg` — upstream support, unknown-state, and source-time constraints.
