# OX-39 CANOPY treatment behavior supplement

**Owner-selected resolution:** 2026-11-23 — use the existing First City baseline and olive CANOPY assets as the two treatments. The shared cycle 175 behavior board remains unchanged for the other roster slices.

## Contract

- Treatment A is `FirstCityCanopyAsset.BASELINE`, rendered from the existing `sg2_city_canopy` drawable.
- Treatment B is `FirstCityCanopyAsset.OLIVE`, rendered from the existing `sg2_city_canopy_olive` drawable.
- A scene-definition value selects one of those two treatments for the existing First City CANOPY. Do not switch worlds or change which weather inputs are consumed.
- Both treatments keep `FirstCityElementId.CANOPY`, role `VEGETATION`, the existing foreground CANOPY pass, the same authored geometry/bounds, and the same selected-instant `SceneWeatherDecision` and forecast facts.
- Keep the current CANOPY rendering and crop path. Do not add a renderer plug-in, unrestricted shader/treatment registry, new weather rule, or new artwork asset.

## Observable exit

Install both definition-declared treatments on the same OX-05 synthetic Chicago state and selected instant. The captures distinguish the baseline and olive foliage while place, exact instant, supplied weather facts, CANOPY identity, depth, geometry, and forecast access remain unchanged.

## Board relationship

This supplement resolves OX-39's exact-treatment question in the already approved shared behavior board. The shared UI artwork board remains a schematic First City and Open Landscape comparison; it does not select the CANOPY asset. This supplement specifies the First City treatment pair for OX-39 only. Existing OX-24 installed evidence is the source for the pair and their visible difference, not a substitute for OX-39's installed acceptance captures.
