# Cycle 174 behavior board contract

**Status:** Proposed for product-owner review; not approved.  
**Scope:** OX-07 → OX-45 → OX-08 → OX-46 → OX-47. The five slice exits remain separate.  
**Behavior package:** `.codex/concepts/OX-174-reusable-weather-rendering-behavior-board.dot`, `.svg`, `.png`, and `.md` (this contract).  
**UI package:** `.codex/concepts/OX-174-reusable-weather-rendering-ui-artwork-board.svg` and `.png`.

## Established dependency facts

- **OX-36 (DONE):** First City's typed cloud-cover slot carries the selected instant and typed evidence, pairs supplied cover with the shared decision, and leaves missing cover unavailable. The completed slice recorded a supplied 0.4 cover and missing-cover case without changing visuals. `SceneWeatherDecision` owns weather interpretation.
- **OX-38 (DONE):** the declared First City `CANOPY` is consumed by the selected-instant renderer and preserves its draw order and weather decision. Its fixed-scene installed comparison was pixel-identical. Cloud drawing remains the target for this cycle's separate extraction.
- **OX-92 (DONE):** First City `CANOPY` and the left Minimal Open Landscape tree group use the same typed declaration shape with world-local identity, pass and geometry; selected-instant decisions and critical forecast access remained unchanged.
- **OX-18 (DONE):** both worlds consume one selected-time weather decision while retaining world-specific composition. Its approved states include supplied rain, eligible carried rain, and unavailable condition with independent cloud evidence. The paired world images are separate review artifacts; the landscape is not a production world-selection feature.
- **OX-03 (DONE):** when visible clouds exist, First City rain starts at the cloud base; eligible carried rain without a visible cloud decision retains the established no-cloud rain origin. This does not infer clouds or rain support.
- **OX-04 (DONE):** missing, unsupported, or probability-only condition evidence cannot create a condition-derived weather claim. Supported fields remain independent; precipitation chance is not a condition or measured rain.
- **OX-05 depth/order** remains the First City boundary adopted by OX-18. The current Minimal Open Landscape composition draws sky, cloud, terrain, its two tree groups, then precipitation. OX-47 may make precipitation depth declarations explicit while retaining the approved scene-specific order and placement.

## Proposed shared behavior contract

Resolve one `SceneReading` at exact selected instant `T` into the existing `SceneWeatherDecision`. Each world maps that same decision through its local declaration/style and composition. Rendering consumes resolved decisions; it does not inspect provider DTOs, reinterpret evidence, infer weather from probability, turn missing into clear/zero, or retarget `T`. Keep supplied, carried, and unavailable evidence distinct. Rain and snow marks remain decorative illustrations, not rainfall-rate or accumulation measurements. Essential forecast facts stay on their existing readable text path.

The cloud-only placement rule is strict: the complete visible cloud output, including clipping edges, blur, shadow, movement, and transitions, remains within the top third of the atmospheric viewport. Rain and snow may extend below that cloud region. An unavailable cloud-cover value does not imply a cloud-free sky; independently supported condition and cloud-cover fields retain their own meanings.

## Independent slice gates

| Slice | Scope boundary | Observable exit copied from the roadmap |
|---|---|---|
| **OX-07** | Extract existing First City cloud drawing behind a typed reusable renderer entry. Pass resolved cloud decisions and world-authored style through a declared cloud element. Preserve the overall Canvas and output. | Installed First City day/rain comparison shows no unapproved visual change; the capability can be called from a declared cloud element. |
| **OX-45** | Invoke the reusable cloud capability from existing Minimal Open Landscape. Check all cloud output, including blur, shadow and movement, against the top-third constraint; do not fabricate unknown cover. | Installed two-world samples share weather decisions; all visible cloud output in checked states remains in the top 33% of the atmospheric scene viewport; unknown cloud coverage is not fabricated. |
| **OX-08** | Extract existing rain drawing behind a declared precipitation-element renderer. Preserve the OX-03 visible-cloud origin rule and no-cloud fallback. Add no rainfall-rate inference. | Focused installed First City supported-rain and dry comparisons preserve baseline behavior and selected-time reading; rain is callable from the scene declaration. |
| **OX-46** | Give snow its own compatible precipitation capability; reuse only rain interfaces demonstrated by OX-08. Do not introduce a generalized particle runtime. | Installed snow and no-snow comparisons show correct OX-18-driven state and unchanged essential data access in the existing world. |
| **OX-47** | Wire reusable rain and snow into existing Minimal Open Landscape with explicit scene-specific depth/placement. Keep Park composition outside this slice. | Installed two-world supplied rain/snow, dry and unavailable cases preserve depth, source truth, responsiveness and essential exact forecast access; no Park composition dependency. |

The listed order is the cycle roster, not a claim that OX-45 blocks OX-08. Declared dependencies are OX-07 → OX-45, OX-07 → OX-08, OX-08 → OX-46, and OX-45/OX-46 → OX-47, plus the resolved external prerequisites recorded on each roadmap entry.

## Proposal boundaries and exclusions

- Keep each slice's roadmap exit, status, authorization, evidence, and outcome independent even though this board package is shared.
- Preserve the current First City and Minimal Open Landscape composition order. For OX-47, express only the existing open-landscape placement as a scene-local depth declaration; do not introduce Park layers or a universal scene graph.
- Keep clouds inside the top-third bound; rain and snow may extend below it. Apply OX-03's rain origin rule only to rain.
- Do not add weather inputs, precipitation-rate or accumulation claims, provider/catalog changes, meteorological plausibility correction, world selection, forecast/timeline changes, or deprecated-app changes.
- Do not use this board as installed visual evidence. Each UI exit still needs the scoped installed states and evidence stated in the roadmap and later bounded plan.

## Owner review

Approval is pending for the complete package listed above. If approved, the cycle tracker must record the same owner/date and complete package fingerprints separately for OX-07, OX-45, OX-08, OX-46, and OX-47. Any edit after review requires regenerated renders and renewed review of the changed package.
