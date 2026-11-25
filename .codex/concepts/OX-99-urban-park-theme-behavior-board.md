# OX-99 behavior board companion — draft

**Status:** Prepared for product-owner review. This package is not approved and does not authorize implementation.

## Outcome

Add a new selectable built-in theme called **Urban Park**, based on a copy of the current `First City · Baseline proof` presentation. Keep the existing baseline proof unchanged and debug-only. Add Urban Park as a separate theme-list entry; retain **Living Urban Park** as the bundled free default.

## Composition contract

- Use `references/background.png`, `references/middleground.png`, and `references/foreground.png` as distinct 1024 × 1536 source images.
- Paint the back-to-front scene as background image → separate existing selected-time Sun / supported Moon cue layer(s) → existing background weather layers → middleground image → existing middleground weather layers → foreground image → existing app-rendered scene/readout layers.
- All three image planes use one shared viewport transform and preserve their supplied transparent pixels. Do not flatten them or bake in the timeline, celestial cues, weather values, or forecast labels.
- The existing interactive timeline remains a separate app UI layer. Scrubbing continues to determine the exact selected instant and feeds the existing presentation path.
- The image planes remain fixed sunlit artwork in this slice. Existing scene time/weather treatment may continue through its current path; this slice creates no alternate night/rain/snow artwork or new weather interpretation.

## Theme-list behavior

The Settings theme list shows both the current **Living Urban Park · Bundled free** entry and the new **Urban Park · Bundled** entry. Urban Park has its own identity and active state; it is not an app-owned installed pack. Selecting it changes the scene while preserving the current forecast snapshot and timeline. Returning to another existing theme leaves that theme's composition unchanged.

The current catalog source represents one bundled-free entry plus app-owned installed entries. The execution plan must choose the narrowest app-owned representation for one additional bundled selectable theme without reclassifying Urban Park as an installed pack or changing Living Urban Park's default role.

## Acceptance represented here

- The Settings theme list distinguishes Urban Park from Living Urban Park and shows the selected entry accurately.
- The Urban Park scene renders the three separate source planes in the specified order with one viewport mapping and preserved alpha.
- Sun and supported Moon cues remain independently rendered above the background image and below later planes; their existing selected-time/support rules remain authoritative.
- The timeline and forecast readout remain live, legible, and app-owned. Direct theme changes and timeline scrubbing preserve the exact selected instant and forecast facts.
- Review includes the fixed sunlit image under a Moon-visible state so the static-art limitation is explicit.

## Provenance and limits

This board uses the supplied files in `references/` as review inputs; it does not claim pixel-exact registration beyond their shared 1024 × 1536 canvas. The cue symbols in the companion UI artwork are schematic markers for the existing baseline-proof cue renderer, not new weather artwork. The package does not define a reusable plane compositor, a scene-pack capability, asset import, new weather semantics, or broad accessibility/system acceptance.
