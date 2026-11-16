# OX-04 behavior board — protect scene truth and unknown states

**Status:** Product-owner-approved 2026-11-16 for cycle `143-ox04-scene-truth`. Approval status and package fingerprints are recorded in the cycle plan; approval governs this board's scope and acceptance, not implementation.

**Board image:** `OX-04-scene-truth-behavior-board.svg` (editable Graphviz source and PNG review render alongside).

## Purpose

Check that active-catalog First City rendering preserves the strength, support, and time of the selected weather evidence. A scene claim must not become more certain than the data that supports it. OX-03 established the shared `SceneReading` → `FirstCityComposition` decision boundary; OX-04 reviews how absent, older, unsupported, and probability-only evidence is represented through that boundary.

## Evidence contract shown

- **Supplied supported condition:** use the existing category-to-scene mapping faithfully. Do not reject or correct the supplied category through an independent plausibility audit.
- **Eligible carried condition:** only the existing field-specific carry rule may show an earlier condition at the selected slot. Keep `selectedInstant` authoritative and retain the earlier `sourceValidTime` as a distinct, visible disclosure. A carried value is older evidence, not a newly supplied fact at the selected instant.
- **Unavailable condition:** an explicitly empty or unsupported condition, or a missing slot that is not eligible for carry, makes no condition-derived weather claim. Do not substitute clear, dry, zero, or another category.
- **Probability-only precipitation:** preserve the probability as a chance. It is not a categorical condition, measured amount, or rainfall rate. Decorative precipitation remains illustrative under the existing specification; it must not be presented as a measurement.
- **Independent fields:** preserve each separately supplied supported field under its own support and source-time semantics. Missing condition evidence does not fabricate, zero, or erase an independently supported value; a supported field does not create a different missing field.
- **Selected time:** the exact selected instant remains the one clock for composition and forecast foreground. It does not move to an older source-valid time.

## Review flow and acceptance

Trace each fixture-backed state from the typed `SceneReading` evidence through `FirstCityComposition` to the scene and foreground. Record the evidence classification, source time, resulting scene claim, visible condition wording, independent facts, and observed result. Include at least one installed visible state in which the condition is unavailable while independent facts remain available. Record pass, fail, or not run; a build or this board alone is not visual evidence.

The board reuses the OX-03 contract and its captured unavailable and carried states as reference material. It does not assume those OX-03 captures complete OX-04's evidence matrix or establish any OX-04 acceptance result.

## Boundaries

- Scope is the active catalog First City composition and its forecast foreground, following OX-03's shared decision contract.
- Keep the primary composition and active reading fallback on the existing composition path. State-only and fixture/Time Lab preview routes remain separate.
- Do not invent a catalog-age cutoff, freshness threshold, provider/schema rule, or cross-field plausibility ranking.
- Do not add weather fields, inferred values, defaults, zero substitutions, interpolation, or new condition mappings.
- Broad accessibility and system/environment matrices remain outside this slice. Do not add or run app tests without separate authorization.
- The UI artwork board shows the intended visible unknown state; it is a schematic target, not an installed screenshot or acceptance evidence.

## Source anchors

- `docs/ROADMAP.md`, OX-03 and OX-04 — dependency, objective, exit, and evidence target.
- `docs/SPECIFICATION.md`, time bounds/gaps and successor direction — eligible same-date interior carry, unavailable missing facts, supplied-value fidelity, and no unsupported alert/weather claims.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, §§4.1, 5.2, and 6 — exact selected-time behavior, missing/unsupported conditions, precipitation-probability meaning, and unavailable states.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt` — `SceneEvidence`, typed per-field source data, and existing expected-slot carry.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` — current evidence-to-composition mapping.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — selected-time foreground and active First City rendering.
- `.codex/concepts/OX-03-single-scene-decision-contract-behavior-board.dot` and `.codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg` — immediately preceding approved board package and review style.
- `.codex/test-artifacts/142-ox03-scene-decision-contract/README.md` — reference route captures for supplied rain, carried rain, and unavailable condition. Those are prior-cycle evidence, not OX-04 acceptance.

This approved board governs the OX-04 evidence contract and covered visible states only. It does not approve implementation, new data semantics, an age threshold, or any broader scene-engine proposal.
