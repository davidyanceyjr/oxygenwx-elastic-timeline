# OX-06 UI artwork board — unchanged First City with semantic review anchors

**Approval record:** Pending product-owner review for cycle `145-ox06-semantic-identity`. Approval status and package fingerprints will be recorded in the cycle plan and roadmap. Approval governs this annotated artwork target; it does not authorize implementation.

**Artwork:** `OX-06-first-city-semantic-identity-ui-artwork-board.svg` (editable vector source and PNG review render alongside).

## Visual objective

Show the existing First City appearance in representative day, night, and moderate-rain states, then annotate the meaningful scene objects with their proposed semantic roles. The identity metadata is nonvisual: remove all review callouts and the app's appearance remains at the existing baseline.

The SVG callouts are board annotations only. They do not represent in-app labels, cards, controls, debug overlays, accessibility labels, hit targets, or an inspector. The existing selected-time readout and timeline are shown as unchanged anchors.

## States shown

1. **Day and Sun cue:** existing Sun cue, city layers, canopy, readout, and time navigation. Candidate object/role anchors include Sun / `TIME_CUE`, skyline / `CITY_STRUCTURE`, and canopy / `VEGETATION`.
2. **Night and Moon cue:** existing fixed Moon cue artwork, stars, city, window lights, readout, and time navigation. Candidate anchors include Moon / `LUNAR_CUE`, stars / `CELESTIAL_FIELD`, and window details / `LIGHT_DETAIL`. Moon visibility remains controlled by existing selected-time inputs; the fixed crescent is not a lunar-phase claim.
3. **Moderate rain:** follows the OX-05 approved artwork direction: overlapping cloud clusters occupy Background and Middle-Ground, with one scene-wide precipitation field in Foreground crossing in front of near buildings. Candidate semantic identity is one cloud-cover object spanning two render passes, plus one precipitation-field object.

All three scenes are schematic, not installed screenshots or acceptance evidence. They introduce no forecast values, data-support claims, new controls, or proposed changes to visible artwork.

## Preserved behavior and appearance

- The exact selected instant and existing `FirstCityComposition` decisions remain authoritative.
- OX-03/OX-04 weather meaning, unavailable/carried states, independent facts, and source-time disclosure do not change.
- OX-05's absolute `Background < Middle-Ground < Foreground` order, approved local sublayer order, single precipitation field, and compositing passes remain unchanged.
- The fixed Moon cue artwork remains the existing baseline and does not depict supported lunar phase.
- Forecast facts remain available as text. The readout and timeline remain at their existing layout and retain their gestures.
- No new in-app labels, interaction, hit targets, layout, scene art, or accessibility contract is proposed by OX-06.

## Review request

Approve or revise the proposed identity/role anchors and the metadata-only visual direction. In particular, confirm whether one cloud-cover identity should map to both existing depth-specific cloud passes. Approval sets the OX-06 artwork target only; any later visible-output change requires a revised board and installed evidence in its plan.

## Source anchors

- `.codex/concepts/OX-05-make-depth-ordering-enforceable-ui-board.svg` — approved First City layering and OX-05 rain target.
- `.codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg` — recent First City scene and evidence-state review style.
- `.codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg` — shared composition, selected-time and rain-origin constraints.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` and `FirstCityComposition.kt` — current First City objects/layers and depth groups.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, §§5.1–5.4 and 6 — atmospheric scene hierarchy, text forecast facts, supplied values, and unavailable states.

This board is a schematic review target, not implementation or installed visual acceptance. Its callouts must not be mistaken for UI output.
