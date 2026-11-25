# OX-111 behavior board — shared scene core and Urban Park location inscription

**Status:** Revised proposal for product-owner review. This scope replaces the previously approved Park-only package; no implementation is authorized.

## Forecast-to-scene contract

The app's typed forecast presentation remains the source of the selected-time scene state. App-owned mapping resolves the forecast and chosen theme into a theme-neutral scene description. `:scene-core` receives that resolved description and draws it; it does not inspect provider DTOs, reinterpret weather, or select a different timestamp.

## Shared `:scene-core` boundary

- Create a Compose-capable Gradle library module named `:scene-core`. The app depends on it; the core module has no dependency on app-owned presentation types or theme packages.
- The core owns the reusable back-to-front scene contract, optional three-plane Background/Middleground/Foreground composition, and generic scene-element scheduling.
- The core owns a reusable painted-data-surface renderer. It measures and wraps the complete dynamic value and applies the surface transform, face clip, and material/lighting treatment supplied by the owning theme.
- Theme definitions own their image planes, surface geometry/material descriptors, and theme-specific element painters. The common renderer does not contain Urban Park coordinates or motif code.
- Existing built-in theme compositions consume the shared depth/plane contract while preserving their current visible appearance and forecast mappings. Any future code-backed theme—city, roadside, desert, alien, or another setting—can depend on `:scene-core` and provide its own assets, geometry, materials, and element painters. Future birds, signs, monuments, and other motifs remain theme-owned extensions; OX-111 adds no new motif.

## Urban Park data path

Use the selected location display name from typed presentation state. Urban Park supplies the water-tower face geometry and material. The shared surface renderer paints the value after the tower-bearing Middleground plane and before Foreground/banner. `Chicago` fits on one line; a representative multiword name such as `San Francisco` wraps at a word boundary and remains inside the same face. A selection change updates the same renderer. A null or blank value draws no inscription.

The image board's paint studies are material references only. Runtime lettering remains dynamic; it is not a raster word asset. A crisp flat overlay fails the visual exit.

## Preserved behavior and exclusions

- Keep location meaning in presentation-sourced accessible text and retain the location-picker action.
- Keep every other meteo readout value, the time tape and selected instant, source-time meaning, and the current data viewer.
- Do not change weather decisions, migrate other meteo values, remove the card/readout, add location selection or networking, or add theme-specific motifs.
- Installed data-only theme-pack compatibility remains on its existing contract. OX-111 does not define a theme SDK or change pack schemas; the shared library is available to code-backed theme definitions.

## Review focus

Review the `:scene-core` module boundary, the forecast-to-neutral-scene contract, shared depth/plane composition, theme-owned extensions, and the Urban Park painted-lettering result together. The earlier Park-only board approval does not cover this expanded package. Board approval governs planning only; installed visual and interaction evidence remains required during authorized execution.
