# OX-06 behavior board — First City semantic element identity

**Approval record:** Pending product-owner review for cycle `145-ox06-semantic-identity`. Approval status and package fingerprints will be recorded in the cycle plan and roadmap. Approval governs this proposed First City identity contract; it does not authorize implementation.

**Board image:** `OX-06-scene-element-identity-behavior-board.svg` (editable Graphviz source and PNG review render alongside).

## Purpose

Give meaningful First City objects identities and semantic roles that can be inspected and tested independently of the functions that draw them. Keep this a finite First City outcome. OX-06 does not establish a universal scene-element registry or implement object interaction.

## Proposed contract

- A semantic identity answers **which First City object**; a semantic role answers **what kind of object**.
- Identity and role are separate from the current draw function, physical depth group, local draw order, and current visibility.
- Existing selected-time decisions continue to control scene content. Hiding or showing an optional cue or weather object does not rename the object or alter its role.
- Existing OX-03/OX-04 selected-time and evidence semantics remain unchanged. OX-06 adds no weather interpretation.
- The OX-05 physical depth groups and their approved order stay intact. A semantic object may map to more than one existing render pass: the current cloud cover can have one semantic identity while appearing in Background and Middle-Ground cloud passes.

## Candidate First City identity map

The names below are review labels, not a required Kotlin type, ID spelling, or public API.

| Meaningful object | Candidate semantic role | Existing draw pass(es) |
| --- | --- | --- |
| Sky backdrop | Sky | `SKY` |
| Star field | Celestial field | `STARS` |
| Sun cue | Time cue | `TIME_CUE` |
| Moon cue | Lunar cue | `LUNAR_CUE` |
| Cloud cover, including its layered appearance | Cloud field | `BACKGROUND_CLOUDS`, `MIDDLE_GROUND_CLOUDS` |
| Distant skyline | City structure | `BACKGROUND_ARCHITECTURE` |
| Diffuse atmospheric light | Atmosphere | `ATMOSPHERE` |
| Near city/buildings | City structure | `FOREGROUND_ARCHITECTURE` |
| Canopy | Vegetation | `CANOPY` |
| Window details | Light detail | `WINDOW_LIGHTS` |
| Existing single rain/snow field | Weather field | `PRECIPITATION` |

`TIME_WASH` and `READOUT_QUIET_ZONE` remain composition passes, not semantic scene objects. Exact identity encoding and whether the owner wants a smaller representative set are review decisions; no global registry or cross-world ID contract is proposed.

## Review and scoped proof target

Review whether the proposed finite object/role map identifies the meaningful First City objects at a useful granularity, especially the single cloud-cover identity spanning two current passes. The OX-06 exit is met when First City elements have testable identities and roles without a universal registry. The eventual plan must state the authorized scoped proof and any installed evidence required by the approved scope. This board is a planning contract, not implementation or installed acceptance evidence.

## Boundaries

- Keep `FirstCityComposition` as the existing source for selected-time scene decisions and preserve OX-03/OX-04 weather support, unavailable, carried-source-time, and exact-time meaning.
- Preserve OX-05's `Background < Middle-Ground < Foreground` ordering and its approved local ordering; semantic identity does not replace depth or renderer routing.
- No renderer extraction, scene artwork revision, weather inference, universal registry, scene-pack schema, second world, debug inspector, hit target, or interaction. Moon inspection belongs to OX-10; a debug element-inspection surface belongs to OX-16.
- The companion UI board is an annotated, schematic view of the existing appearance. Its labels are review callouts only, not app labels, controls, hit targets, or an installed screenshot.

## Source anchors

- `docs/ROADMAP.md`, OX-03 through OX-06 — dependencies, current OX-06 exit, and exclusions.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt` — current decision values, physical depth groups, layer tokens, and separate composition passes.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` — current First City layer descriptions and selected-time composition.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — existing renderer dispatch by `FirstCityLayer`.
- `.codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md` — shared composition decision boundary and weather-meaning constraints.
- `.codex/concepts/OX-04-scene-truth-behavior-board.md` — supplied, carried, unavailable, and independent-fact boundaries.
- `.codex/concepts/OX-05-make-depth-ordering-enforceable-behavior-board.md` — approved physical order and explicit distinction between ordering layers and element identity.
- `docs/oxygen_scene_engine_spec.md` and `docs/Oxygen_Scene_Engine_Refactor_Spec.md` — supporting architecture proposals only; this board does not adopt their broader scene graph, component library, registry, or interaction direction.

This board governs only the proposed OX-06 First City identity/role contract. Its candidate labels require owner review before any plan is finalized.
