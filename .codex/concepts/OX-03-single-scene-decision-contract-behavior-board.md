# OX-03 behavior board — one First City scene decision contract

**Approval record:** The current approval status and package fingerprints are recorded in the cycle plan and roadmap.

**Board image:** `OX-03-single-scene-decision-contract-behavior-board.svg` (editable Graphviz source and PNG review image alongside).

## Purpose

Make the existing `SceneReading` → `FirstCityComposition` boundary the source of scene decisions on both active catalog render paths. The primary composition path remains the reference. If the composition route is filtered while a reading is still available, that reading must use the same First City decision boundary and reach the same composition renderer.

This is the first shared decision seam behind the proposed Scene Engine direction. The Living Urban Park concept is a future world that should be able to present the same selected-time weather truth through scene elements and information surfaces. OX-03 establishes the current First City boundary and route parity; it does not implement that future world or generalize beyond the existing contract.

## Contract shown

1. `CatalogTimelinePresenter.scenePresentation(state)` resolves one selected instant and builds the `SceneReading`, timeline view, and `FirstCityComposition` from it.
2. The primary active-catalog route passes its existing composition to `CatalogAtmosphericScene(composition)`.
3. The active-catalog reading fallback resolves a `FirstCityComposition` from the same reading and zone, then passes it to the same composition renderer. It does not draw weather directly from `SceneReading` or `SceneVisualState`.
4. The rendered scene and the forecast foreground remain tied to the exact selected instant. Independent forecast facts and source-time disclosure remain intact.

## Weather meaning and covered states

- **Supplied condition:** keep the established First City mapping for the supplied category. The OX-02A supported-rain comparison is the known route difference to resolve.
- **Unavailable condition:** make no condition claim. A separately supported value remains available to the foreground; this board does not prescribe a new unknown-state policy.
- **Eligible carried condition:** the reading retains the prior source-valid time; the scene does not turn it into a new selected-time observation, and the foreground disclosure remains unchanged.

These examples preserve evidence already represented by `SceneReading`. They do not authorize meteorological plausibility checks, value correction, interpolation changes, or additional supported states.

## Rain-origin visual invariant

When the composition depicts rain and has an existing visible cloud decision, visible rain streaks begin at the base of that cloud bank and fall down through the scene; they must not appear detached in open mid-sky. When rain is eligible but the composition has no visible cloud decision, preserve the existing no-cloud scene and established rain-field origin. This is a renderer-placement correction only: the scene consumes existing cloud and precipitation decisions and does not invent cloud cover, change rain support, suppress supported carried rain, or reinterpret forecast values.

The current composition renderer clips cloud drawing to the upper third of the scene while its rain field seeds streaks from 52% to 87% of scene height. Installed OX-03 evidence shows supplied rain with a visible cloud decision and eligible carried rain without one. The supplied-rain state uses the cloud-origin correction; the no-cloud carried-rain state retains the 52%-to-87% field placement without adding cloud cover. The UI artwork board makes both outcomes explicit.

## Sun/Moon cue boundary

The current Sun and Moon cues remain composition baseline. Cue size and glow are meaningful visual art direction when tied to selected-time decisions, but OX-03 does not redesign their size, tint, intensity, or phase appearance.

The current Moon renderer always paints the same pale disk with an offset dark disk; its composition decision carries kind, position, scale, and visibility, but no phase. Its apparent size changes along the selected rise/set arc, while the painted crescent does not follow the supplied lunar phase. SG.8's approved phase treatment uses explicit stage text and a neutral phase-cycle marker, without hemisphere-specific visible illumination; this fixed scene crescent is separate artwork and is not approved as a phase depiction. Retaining it for OX-03 is not approval of it as phase artwork. Any redesign of the scene cue needs its own scoped visual decision aligned with SG.8; visual scale must not imply measured physical size or altitude.

## Relationship to the Living Urban Park concept

The proposed scene-engine spec names Living Urban Park as its reference world. The supplied concept visual shows weather and forecast information as part of a park scene alongside a persistent timeline. That direction motivates keeping weather meaning, the exact selected instant, and source-time evidence in typed decisions before rendering. It is context for the decision boundary, not a new OX-03 visual target.

Approving this board approves the OX-03 behavior boundary and covered First City route parity only. It does not adopt the draft scene-engine spec, approve the concept artwork or its illustrative readouts, or decide whether Living Urban Park becomes the default scene or remains selectable. Those product and artwork decisions need their own approved scope and boards.

## Boundaries

- Preserve the exact selected instant and the existing composition-route output for covered conditions. Apply cloud-origin rain placement only where the composition already has visible clouds; preserve the no-cloud carried-rain output otherwise.
- Keep current Sun/Moon cue appearance as baseline only; phase-aware Moon artwork and cue size/glow redesign are outside OX-03 review.
- Keep the state-only view overload while its scene-present route remains unverified; do not infer its disposition here.
- Keep fixture and Time Lab preview entry points separate.
- Leave scene truth for absent/stale/unsupported inputs, global depth ordering, semantic element identity, and reusable world support to their later OX slices.
- The Living Urban Park concept motivates the future Scene Engine direction; OX-03 remains the current First City contract and does not implement or approve park artwork.
- No timeline, forecast readout, gesture, or layout redesign is proposed. The only additional visual change is aligning rain with its cloud source. Because the active reading fallback currently has a different scene appearance, the companion UI artwork board is required to show the intended route parity and rain placement.
- The boards are planning targets, not implementation or installed visual acceptance evidence.

## Source anchors

- `docs/ROADMAP.md`, OX-02A and OX-03 — route finding, objective, dependency, and exit.
- `.codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot` and `.codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg` — prior route/state board package and review style.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — selected-instant reading and composition construction.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt` — typed First City decisions.
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` and `AtmosphericScene.kt` — active catalog route selection and current renderer; cloud clipping and rain-field placement are the observed visual gap.
- `.codex/test-artifacts/055-wx13r-buffered-capture/provider-response-public-chicago-15day.json` and `openmeteo-chicago-canonical-v2-candidate.json` — captured, source-backed rain/cloud example for data context only.
- `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` — selected-time authority, supplied-value fidelity, missing-data meaning, and atmospheric scene principles.
