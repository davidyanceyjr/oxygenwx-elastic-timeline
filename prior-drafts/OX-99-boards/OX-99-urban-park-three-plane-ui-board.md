# OX-99 UI artwork board companion — proposed

**Status:** Pending owner review and approval. Illustration is a composite of owner PNGs, not an installed device screenshot.

## Intended appearance
- A portrait proof occupying the scene's art viewport uses one aligned 1024×1536 source rectangle, with background, middleground and foreground composited in that order.
- The reference contains a distant park, water tower/gazebo and near wall, bench, flowers, water feature and signage. Empty regions are transparent and expose underlying planes. All source layers remain separately addressable at render time, though this slice introduces no motion.
- The UI board shows the intended composite, layering legend, and the explicit requirement that existing forecast readout and timeline remain live, legible and above/within their previously owned regions. The readout placeholders in the schematic are **not** proposed values or exact placement.
- On failure, show the existing supported visual scene rather than a black/partial background; no extra user-facing error UI mandated by this slice.

## Tradeoffs / pending decisions
- Choose fit and camera safe-area behavior against the actual portrait Pixel 3 XL viewport; a reference artboard does not prove screen-crop correctness.
- Determine how Park semantic mural/shelter elements and data planes coexist with baked-in artistic equivalents without double-rendering or duplicate taps.
- The fixed sunny daylight, wet pavement and glowing lamps cannot truthfully reflect rain onset, snow, season or nighttime. Do not pass this as final weather-responsive art.
- Keep normal forecast accessibility and UI out of the bitmaps. Data labels must remain app-owned and selectable/recomposable.

## Acceptance review
Record installed captures and compare against the reference, including overlap/cropping of the source planes, visible data, gesture hit regions, fallback state and selected-forecast continuity. Preserve timeline responsiveness, gesture predictability and readability; automated comparison alone is insufficient.
