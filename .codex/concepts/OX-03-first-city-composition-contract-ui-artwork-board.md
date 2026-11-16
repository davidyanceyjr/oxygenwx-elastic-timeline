# OX-03 UI artwork board — First City route parity and conditional cloud-origin rain

**Approval record:** The current approval status and package fingerprints are recorded in the cycle plan and roadmap.

**Artwork:** `OX-03-first-city-composition-contract-ui-artwork-board.svg` (editable vector source) and `.png` (review render).

## Visual objective

Have the active catalog reading fallback draw the same `FirstCityComposition` as the primary route for the same `SceneReading` and selected instant. OX-02A recorded a supported-rain mismatch between those routes. Where the composition already shows a visible rain-cloud decision, rain streaks originate at that cloud base and fall through the scene. When eligible carried rain has no visible cloud decision, keep the existing no-cloud scene and rain-field origin; do not infer clouds. The rest of the First City appearance remains intact.

## States shown

- **Supplied rain:** both routes show the existing composition's cloud and rain treatment at the same selected instant.
- **Condition unavailable:** neither route invents a condition claim; independent facts retain their existing foreground presentation.
- **Eligible carried rain without visible clouds:** both routes show the existing no-cloud carried-condition composition while the selected instant remains 3:00 PM and the prior source time remains visible as 2:00 PM in the schematic example. Rain keeps its established field origin; no cloud layer is added.

The paired scene panels are schematic route targets. Example city and time labels are placeholders, not fixture data or a screenshot. The board does not prescribe a new missing-data rule.

## Rain-origin requirement

For a rain state with visible cloud decisions, show cloud forms above the rain field, with streaks beginning at the visible cloud base and continuing downward. For eligible carried rain with no visible cloud decision, preserve the existing no-cloud scene and rain-field origin as an explicit exception; do not infer a cloud layer or suppress the carried rain depiction. The placement correction does not change source weather values, probabilities, cloud support, or precipitation coverage rules.

The saved Cycle 055 Chicago capture provides a source-backed rain/cloud example for context: at 7:00 AM CDT on 2026-09-30 it records `RAIN`, 0.7 mm for the 6:00–7:00 AM interval, 100% cloud cover, and 25% precipitation probability. This is archived forecast/model data for Chicago, not a station observation or Florence reading. The paired panels remain schematic and do not claim to render that dataset.

## Sun/Moon cue boundary

This board does not review Sun or Moon artwork. Their current size, glow, tint, position, and visibility remain the First City baseline for OX-03. The current Moon uses the same pale disk and offset dark disk at every phase; preserving that existing output here is not approval of it as phase artwork. SG.8's approved phase treatment uses explicit stage text and a neutral phase-cycle marker, without hemisphere-specific visible illumination. Any redesign of the scene cue needs its own scoped visual decision and board aligned with that direction. Cue size and glow may support the selected-time composition, but must not imply measured physical size, altitude, or intensity.

## Scene-engine design context

The proposed scene-engine spec is built around the Living Urban Park concept: a world where weather and forecast surfaces sit within the park scene while the selected-time timeline remains available. That is the downstream design direction this shared decision boundary should support. The supplied image is context for that direction; its wording, values, layout, and appearance are not OX-03 artwork targets.

Approving this board approves the three OX-03 First City route-parity states shown above. It does not adopt the draft scene-engine spec, approve the park artwork or illustrative readouts, or decide whether the park replaces First City as the default or remains selectable. Those require their own product decision and approved artwork board, currently scoped under OX-22 after its dependencies.

## Constraints

- Keep First City styling, forecast foreground, and timeline appearance as currently approved. The explicit visual correction is cloud-origin rain placement when the composition already has visible cloud decisions.
- Leave Sun/Moon cue styling unchanged and outside the visual review; the current fixed crescent is not an approved phase-art target.
- In rain states with visible clouds, start rain strokes at the visible cloud base and carry them down through the scene. If no visible cloud decision exists, preserve the no-cloud carried-rain scene and its established rain origin; do not create clouds or remove the carried rain depiction.
- Preserve exact selected time and any carried source-time disclosure.
- Make no route-specific scene treatment, controls, cards, or new labels.
- Keep the state-only view renderer and fixture / Time Lab previews outside OX-03.
- This artwork is a planning target, not installed visual acceptance evidence.

The board follows the paired-state review style of `.codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg` and uses the route boundary recorded in OX-02.
