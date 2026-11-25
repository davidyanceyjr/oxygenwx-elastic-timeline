# OX-99 behavior board companion — proposed

**Status:** Pending owner review; not approved or implemented. This contract accompanies editable DOT and rendered SVG/PNG.

## Slice-level contract
- Input is the already selected Park world and the same app-owned forecast/catalog/instant. Only its authored static visual treatment may change.
- Asset inventory is precisely three supplied RGBA canvases, 1024 × 1536 each, registered with common origin: `background.png` → `middleground.png` → `foreground.png`. Do not independently stretch or shift planes; use one shared viewport transform. Preserve transparent pixels. The board demonstrates the intended z-order; actual asset placement and fit must be checked against current Park entry path at intake.
- Use existing Park rendering/selection integration. No new v2 manifest capability or packed multi-asset loader. Plan may choose a bounded bundled/local resource proof while documenting compatibility with future engine work; it must not claim the external v2 pack installer handles this bundle.
- Image decode/availability failure must not commit a partially drawn layer set or crash; retain the existing supported Park/free rendering path. The prior scene must remain navigable.
- No new forecast extraction, alternate selected time, location state, overlays with fabricated data, gesture ownership, dynamic image motion, new Settings control or paid pack behavior.

## Behavior transitions
1. Enter existing Park world → resolve all three bundled proof assets and verify dimensions/decodes → stage composition under a common viewport transform.
2. If all valid, paint in strict background / middleground / foreground order with alpha and existing scene UI retained above where already specified.
3. On failure, keep the existing supported scene; never expose a partial stack.
4. Scrub or change forecast reading → preserve canonical instant/facts and timeline response; static world plane assets are unchanged. Weather-specific art conversion is excluded.
5. Leave/return to the chosen world → preserve current world-choice contract without extra catalog fetch.

## Questions for BOARD / DRAFT
- Does the runtime's world chooser currently reach the Park composition or only its semantic inventory? If not, what is the smallest validation-only hook permitted by the owning code?
- Which renderer layer paths (Canvas, ImageBitmap, drawIntoCanvas) avoid duplicate full-resolution bitmap retention? What are the focused memory bounds on the targeted Pixel 3 XL?
- How is the 2:3 artboard mapped to the live viewport and protected from timeline chrome? Fit/crop decision requires owner validation, not implicit independent ContentScale.Crop.
- Are background and foreground alpha edges visually acceptable? Reconcile original black-transparent regions by testing alpha, not visual guessing.
- Which existing Park mural/shelter/text surfaces must remain app-composited on top, rather than duplicated from or obscured by the static PNG?

## Acceptance observations
- Repeatable 1024×1536 source inventory with digest and channel validation.
- Installed evidence of ordering, alpha, common transform, viewport treatment, visible data readout, and an unchanged source reading at a recorded exact selected instant.
- Directly affected scrub and World choice observations; fallback check using a controlled missing/invalid resource in focused tests.
- No broad RTL/TalkBack/system matrix outside dedicated TEST slices; no claim that preview is a device screenshot.
- Explicit check of frame/scrub responsiveness, gestures and readability.

## Dependencies and provenance
`docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md` OX-25/OX-42/OX-43/OX-44/OX-85 and OX-21/OX-22 etc.; `LivingUrbanParkComposition.kt`; `docs/SCENE_PACK_MANIFEST_V2.md`; `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md`; `docs/UI_DEVELOPMENT_WORKFLOW.md`; owner-provided three PNGs.
