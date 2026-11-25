# OX-111 UI artwork board — shared scene core with Urban Park tower paint

**Status:** Revised proposal for product-owner review. The SVG is the editable artwork source; the PNG is the review render. This board does not authorize implementation or establish installed acceptance evidence.

## Shared rendering model

The upper diagram shows one theme-neutral `:scene-core` rendering contract supplied by typed forecast presentation and each theme's scene definition. Background, Middleground, and Foreground planes use the declared shared transform. Ordered elements use the same depth contract. Theme-specific surface geometry and motifs remain with the theme. Any code-backed city, roadside, desert, alien, or other theme can depend on this same core and supply its own scene definition.

## Visible Urban Park result

The lower panels retain the approved paint treatment: Chicago fits on one line, while San Francisco wraps on the same curved tower face. The dynamic text follows tower curvature and perspective, sits within a face clip, and blends with pigment texture and lighting. These generated images are material references, not runtime assets. The actual renderer receives the selected display name and draws each value dynamically.

The current Pixel image shows the before-state, where the location is still in the central readout. The target keeps the picker, remaining meteo readout, selected-time tape, and accessible location meaning intact. Other current built-in themes use the shared scene contract without changing their visible treatment. This establishes a reusable code boundary; it does not define a data-only theme-pack SDK or schema.
