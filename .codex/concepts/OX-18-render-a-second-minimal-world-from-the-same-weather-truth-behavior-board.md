# OX-18 behavior board — one weather truth, two minimal world compositions

**Approval status:** Product owner approved this board package for cycle `146-ox18-minimal-second-world` on 2026-11-16. Approval covers the behavior contract and scope shown here; it does not authorize implementation. Package fingerprints are recorded in the cycle plan.

**Board image:** `.codex/concepts/OX-18-render-a-second-minimal-world-from-the-same-weather-truth-behavior-board.svg` (editable Graphviz source and PNG review render alongside).

## Purpose

Prove that a minimal non-city composition can consume the same selected catalog weather decisions as First City without copying First City's meteorological interpretation. The board proposes the smallest shared semantic decision seam needed for that proof, then keeps the two world compositions separate.

## Contract shown

1. One catalog selection and exact selected instant `T` produce one typed selected-time reading. Per-field values, evidence, support, source-valid times, and independent facts remain intact.
2. One minimal shared semantic decision contract resolves weather meaning from that reading once. The board approves the contract and flow, not a Kotlin type name, public schema, registry, or complete scene graph.
3. First City keeps its approved composition baseline. The minimal non-city world maps the same shared decision set to a distinct composition; it does not add another condition, carry, support, or precipitation interpreter.
4. The scoped proof compares both compositions from the same catalog selection and checks the same selected instant, decision set, evidence strength, and source-time meaning. World-specific silhouettes and visual placement may differ.

## Preserved meaning

- Supplied weather categories and values remain authoritative presentation inputs; no independent plausibility correction is added.
- An eligible carried condition remains older evidence with its original `sourceValidTime`, while `selectedInstant` stays authoritative.
- An unavailable or unsupported condition makes no condition-derived weather claim. Separately supported facts remain available under their own evidence.
- Precipitation probability remains a chance, not a measured amount or rate. Decorative weather marks remain illustrative.
- Existing selected-time light and event inputs remain authoritative. No unsupported Sun or Moon claim is introduced.

## Proposed world boundary

The artwork companion proposes a minimal open-landscape composition to make the non-city distinction reviewable. It is a portability proof only. It does not adopt the Living Urban Park proposal, define the world's product role, change the default scene, add scene selection, or define a future scene-pack API. First City depth ordering and First City-local semantic identities remain in force for First City; this slice does not require a universal identity system.

## Observable acceptance target

- A paired review uses one catalog selection and exact selected instant for both compositions.
- Both outputs consume the same weather decision set and preserve support, carried-source-time, unavailable, independent-fact, and probability meaning.
- The second composition is recognizably non-city while remaining minimal.
- No duplicated meteorological interpretation is needed. Cloud and precipitation renderer extraction is not required for this early proof.
- Side-by-side is a review comparison, not an in-app split-screen or a new interaction.

## Boundaries

No polished second-world pack, weather-field expansion, plausibility validation, scene switching, location changes, public/persisted identity schema, interaction, or renderer-library extraction is part of this board. A materially different target or newly required behavior must be reviewed before the plan is finalized.

## Source anchors

- `docs/ROADMAP.md`, OX-03 through OX-06 and OX-18 — accepted dependencies, current decision/evidence boundaries, OX-18 objective, and exit.
- `docs/ROADMAP_SPECIFICATION.md` — project-wide behavior and artwork board gate.
- `.codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md` — current selected-reading to First City decision contract and weather-meaning limits.
- `.codex/concepts/OX-04-scene-truth-behavior-board.md` — supplied, carried, unavailable, probability-only, and independent-field semantics.
- `.codex/concepts/OX-05-make-depth-ordering-enforceable-behavior-board.md` and `.codex/concepts/OX-06-scene-element-identity-behavior-board.md` — First City depth and local identity constraints.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt` — selected-time typed evidence and source-valid-time values.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` and `FirstCityComposition.kt` — current First City semantic-to-composition boundary.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — current First City catalog renderer.

The supporting Scene Engine proposal documents are context only; this board does not adopt their broader architecture or product direction.
