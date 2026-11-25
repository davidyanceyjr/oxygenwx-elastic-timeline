# OX-99 Park image integration UI board — owner review draft

**Status:** Draft for composition decisions; not approved and not installed-device evidence. The editable SVG embeds the unchanged supplied source PNGs plus the owner-designated Concept 01 and Streetcar Stop mockups for portable review.

**Owner-selected path (2026-11-24):** Keep OX-99/OX-102 gated until the element map and fixed-art state matrix are completed and approved. This does not approve this board.

## Owner-directed placements shown by the mockups

- Concept 01 is the full-scene Park placement mockup: location on the water tower; current temperature and condition icon on the mural, with wind chill added by owner direction; wind on the banner; daily date/HI/LO/precipitation on the separate freestanding board; daily forecast cards on the bottom strip.
- The owner selected the Streetcar Stop allocation: hourly `NEXT HOURS` values on the shelter and daily date/HI/LO/precipitation on the separate board. This supersedes the earlier three-day HI/LO shelter assignment and earlier undecided/no-duplicate board direction.
- The Streetcar variant visibly depicts three hourly rows. Exact count/range and sample times/values have not been separately approved.
- The three supplied transparent PNGs are 1024×1536 and contain no forecast text or streetcar shelter. The current app draws a procedural shelter, but it has no forecast sign or rows.
- The owner owns the supplied PNGs and authorized review-board reproduction, asset derivation, in-app display, and distribution. Preserve source bytes and alpha unchanged. Creator/source and separate terms/attribution remain unspecified.

## What this board shows

- Each supplied transparent source on a checkerboard, so its visible content and alpha can be reviewed without flattening it into another source.
- The owner-designated Concept 01 full-scene placement mockup beside the selected Streetcar Stop variant. Both show mock values; OX-41 keeps them conceptual rather than forecast inputs.
- The existing Park passes and nine element IDs with source overlaps, but no guessed plane/disposition choices.
- The available center reading and Park `MURAL` surface as separate app-owned reading surfaces. The owner selected MURAL content roles; exact layout/order against image planes and the center reading remains open.
- Daylight, night, supported rain, supported snow, and unavailable states. The board does not select fixed-art policy.

## Owner review still required

1. Complete all nine element rows in `ox-99-park-behavior-board.md` with a target plane and `RETAIN`, `REPLACE`, or `SUPPRESS` disposition; name the source/draw and resolve each tree and bench separately.
2. Resolve the shelter source/disposition and sign treatment. The three supplied transparent PNGs do not contain a shelter. The current procedural shelter is available in the app but has no forecast rows; the mockup establishes the desired hourly placement, not an approved physical-art source.
3. Confirm the hourly count/range and selected-time behavior. The Streetcar image shows three sample rows, but their times and values are illustrative.
4. Specify exact live-reading anchors/order, including tower location, mural temperature/wind chill/condition icon and disclosure, banner wind, daily board date/HI/LO/precipitation, and the full center reading. Record how the bottom daily strip relates to existing timeline UI without changing its semantics by implication.
5. Complete the fixed-art state matrix for daylight, night, supported rain, supported snow, and unavailable value.

The owner-designated full-scene mockup is available for visual composition comparison; do not describe it as missing. Exact source-layer registration against it has not been measured, and the three source PNGs are not a layer-separated original. The app remains responsible for selected time, forecast truth, support, exact values, uncertainty, and unavailable meaning.

OX-99 covers the approved fixed images and existing state rendering. If the owner chooses a new alternate image or dynamic day/night/weather art treatment, record it as separate bounded scope before implementation.

## References inspected

- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkComposition.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`
- `references/asset-ledger.json`
- `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png`
- `design_draft/OxygenWX Streetcar Stop at Golden Hour.png`
- The three supplied transparent image sources

No code or runtime asset integration is part of this board draft.
