# OX-111 behavior board — Urban Park water-tower location inscription

**Status:** Proposed for product-owner review. This package is a planning artifact; it is not approval or implementation.

## Behavior

CatalogTimelinePresentationView.selectedLocationName remains the source for the visible place name. The Park scene receives that typed value and uses one reusable tower-inscription renderer for every selected location. A selection change supplies the new value to the same pass.

The renderer measures the complete name against the visible tank face. It wraps multiword names at word boundaries and adjusts type size to fit the full value within the face. It applies the Park plane transform and a face-aligned clip, bends the lettering to the cylinder, and integrates pigment texture, edge variation, highlights, and shadows with the tank surface. A crisp, uniformly filled text overlay does not meet the visual exit. The UI board uses “Chicago” on one line and “San Francisco” on two lines as representative shapes.

A null or blank value produces no painted lettering. Do not draw a placeholder or infer a location. The location meaning remains available through presentation-sourced accessible text; graphics do not become the semantic source.

## Proposed scene order

Back to front, retain the approved OX-110 passes and insert one new pass:

SKY_UNDERLAY → BACKGROUND → STARS → SUN → MOON → CLOUD_LAYER_1/2/3 → MIDDLEGROUND → TOWER_INSCRIPTION → FOREGROUND → PRECIPITATION

The inscription follows the tower-bearing Middleground pixels and uses the same plane geometry. It is clipped to the tower face. The existing Foreground/banner stays over it. All other OX-110 ordering and the separate precipitation pass remain unchanged.

## Preserved behavior

- Keep the location-picker action and its accessible affordance.
- Keep all other meteo readout values, the timeline and selected instant, existing weather and source-time meaning, and other themes unchanged.
- Move only the visible selected-location line from the center readout to the tower. The rest of the main-view data viewer stays in place.
- Future retirement of the main-view data viewer requires scene mappings and accessible text equivalents for the supported values; that later outcome is outside OX-111.
- The current Pixel capture and tower crop are visual references only, not installed acceptance evidence.

## Architecture question for review

The owner previously asked whether Urban Park-specific functions can live in an Urban Park Kotlin library. This board keeps that direction visible but does not silently choose what “library” means:

1. A dedicated :urban-park Gradle module owns Park-only layer types, composition/rendering functions, and Park-specific assets. The app passes typed scene inputs through a narrow boundary. The current UrbanParkComposition refers to app-owned presentation types, so the extraction seam must be resolved without a circular module dependency.
2. A Park-specific Kotlin package inside the existing :app module provides the same ownership boundary, with a separate Gradle module considered later.

The repository currently declares only :app. Confirm which boundary should govern this first mapping before finalizing the execution plan. The layer contract and visible result are the same either way.

## Board basis

- OX-110 approved the Park pass order, shared plane transform, Middleground tree depth, Foreground banner depth, and separate precipitation behavior.
- OX-99 established the Urban Park theme boundary; OX-19 established that choosing the scene does not alter forecast meaning.
- The current implementation reads selectedLocationName in the central UI readout and does not yet pass it into CatalogUrbanParkScene.

## Review questions

- Confirm that the lettering reads as painted into the cylindrical tank surface rather than a clean text overlay. Approve or revise the one-line Chicago and wrapped two-line San Francisco treatment, including surface texture, lighting integration, and face clipping.
- Should the Urban Park Kotlin library be a dedicated :urban-park Gradle module, or a Park-specific package within :app for this slice?

Board approval governs planning scope only. Installed capture and interaction acceptance remain required during an authorized execution cycle.
