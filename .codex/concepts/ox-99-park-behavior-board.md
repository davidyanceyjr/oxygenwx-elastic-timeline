# OX-99 Park image integration behavior board — owner review draft

**Status:** Draft for composition decisions; not approved. OX-99 remains a proposal and is absent from the canonical roadmap. These files do not authorize implementation.

**Owner-selected path (2026-11-24):** Keep OX-99/OX-102 gated until the composition and state boards are completed and approved. This direction does not approve the boards or supply the remaining row-level plane/disposition choices.

## Owner-directed visual-data placement

The owner identified `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png` as the full-scene Park placement mockup. Its visible values are sample/mock content under OX-41, while the owner clarified its intended data roles:

- Water tower: location.
- Mural: selected current temperature, wind chill, and condition icon.
- Wind banner: wind.
- Separate freestanding board: daily date, high/low, and precipitation chance.
- Bottom strip: daily forecast cards.

The owner then selected the Streetcar Stop layout allocation: hourly `NEXT HOURS` values on the shelter, with the separate board retaining the daily date/high/low/precipitation summary. This supersedes the earlier three-day high/low assignment to the shelter and the earlier undecided/nonduplicate-board direction. The Streetcar mockup depicts three hourly rows, but the owner has not separately confirmed the exact row count or hourly range; displayed times and values remain illustrative.

The owner owns the three supplied transparent PNGs and authorized review-board reproduction, asset derivation, in-app display, and distribution. Preserve their bytes and alpha unchanged. Creator/source and separate terms or attribution were not provided. The mockup is available and sets intended composition; exact registration of each transparent source against it has not been measured, and no layer-separated original/source lineage is identified.

## Repository facts and source observations

- `LivingUrbanParkComposition.kt` declares nine Park element IDs across five ordered passes: `BACKGROUND`, `MURAL_WALL`, `PARK_LANDMARKS`, `PATH`, and `FOREGROUND`.
- The three supplied PNGs are 1024×1536 RGBA and contain no forecast text or streetcar shelter. The background source shows canopy/skyline, banner/lamp and path fragments; the middle source shows the water tower, gazebo and trees; the foreground source shows the mural wall, one bench, a freestanding blank board and paving/water details.
- The current renderer already draws a procedural `STREETCAR_SHELTER`, but it has no forecast sign or hourly rows. The Streetcar mockup shows the intended hourly data placement; it does not provide a separate shelter source layer. The shelter's physical source/disposition and sign treatment remain open.
- The current `MURAL` data surface displays a selected temperature and source-valid-time disclosure or an unavailable state. The owner has directed temperature, wind chill and condition icon content there; its revised rendering, placement/order against the center reading and image planes, and unavailable layout remain to be approved.
- `CatalogTimeLabScreen.kt` independently overlays the full app-owned reading over the scene. Its exact front-to-back relation to the image planes and park-specific surfaces remains open. Keep app ownership of selected time, facts, source support, uncertainty and unavailable meaning.
- Shared sky/weather effects currently use app-selected inputs. The supplied artwork is sunlit and includes wet-looking surfaces and lit lamps; its treatment by time and supported weather remains an owner decision.

## Existing Park element mapping — complete each row

For every element, choose one target plane and `RETAIN`, `REPLACE`, or `SUPPRESS`; name the retained procedural draw or replacement source. The source observations and content roles below do not silently determine dispositions.

| Existing element | Current pass | Owner direction / observed source overlap | Target plane | Disposition and source |
|---|---|---|---|---|
| `DISTANT_BACKDROP` | `BACKGROUND` | Owner assigns the water tower as the location surface. The tower is visible in `middleground.png`; current draw is procedural skyline/backdrop. | ___ | ___ |
| `MURAL_WALL` | `MURAL_WALL` | Owner assigns current temperature, wind chill and condition icon to the mural. `foreground.png` has the brick wall with a blank mural area; its separate blank board is the daily summary surface in Concept 01. | ___ | ___ |
| `STREETCAR_SHELTER` | `PARK_LANDMARKS` | Owner selects hourly `NEXT HOURS` values here. No supplied PNG contains a shelter; current procedural shelter has no sign/rows. Streetcar mockup shows a shelter and three illustrative rows. | ___ | ___ |
| `TREE_LEFT` | `PARK_LANDMARKS` | Procedural tree overlaps tree/canopy forms in the source images. | ___ | ___ |
| `TREE_RIGHT` | `PARK_LANDMARKS` | Procedural tree overlaps foliage in background and middle source images. | ___ | ___ |
| `CURVED_PATH` | `PATH` | Procedural curve overlaps paving, hopscotch markings, wet-looking surfaces and water-edge details. | ___ | ___ |
| `ENTRANCE` | `FOREGROUND` | Procedural entrance threshold; no matching entrance element has been identified in the sources. | ___ | ___ |
| `BENCH_LEFT` | `FOREGROUND` | Procedural left bench overlaps the single visible bench in the foreground source. | ___ | ___ |
| `BENCH_RIGHT` | `FOREGROUND` | No second bench is visible in the source; nearby features include the daily board and water details. | ___ | ___ |

The map must also name how the three supplied bases occupy `BACKGROUND`, `MIDDLEGROUND`, and `FOREGROUND`, where shared sky/weather rendering sits, and how the daily-board and banner data surfaces relate to those images. Do not classify the procedural shelter as retained or replaced until its source/sign treatment is approved.

## Live reading surfaces and order

| Surface | Owner-directed role / current behavior | Still to record |
|---|---|---|
| Tower location | Location belongs on the tower. The owner-designated mockup shows the intended placement. | Exact live location text/anchor and plane order. |
| Mural data surface | Selected current temperature, wind chill, and condition icon. Existing `MURAL` region is normalized x=.05–.15, y=.38–.51 and currently renders temperature/source time or unavailable text. | Approved content layout, source-time disclosure location, order relative to image planes and center reading, unavailable layout. |
| Wind banner | The placement mockup shows wind on the banner; `background.png` contains a blank banner. | Exact live wind content/anchor, units, and image-plane order. |
| Streetcar shelter sign | Owner selected hourly `NEXT HOURS`; Streetcar mockup shows three example rows. | Physical shelter source/disposition; sign treatment; exact hourly count/range and selected-time behavior. |
| Separate freestanding board | Owner selected daily date, HI/LO, and precipitation chance. Concept 01 shows the data on the foreground blank board. | Exact text bounds/order and unavailable behavior; preserve app-owned daily data and support semantics. |
| Bottom forecast strip | Concept 01 shows daily forecast cards and highs. Existing timeline semantics remain governed by current product authority and are outside this image-source decision. | Record whether the reference implies any visual change in this slice; do not change timeline behavior by implication. |
| `CatalogTimeLabScreen` center reading | Full app-owned reading overlays the scene. | Explicit front-to-back order and how essential facts remain readable without accidental duplication. |

For each retained surface, state which selected-time facts appear there and how unsupported/unavailable facts are represented. The owner has decided the mockup's content roles; the board still needs precise placement and ordering.

## Fixed-art state policy

For each state, choose whether the supplied fixed art remains visible or is suppressed. Specify how app-owned forecast facts and shared weather/unavailable indicators remain visible. A new alternate-art or dynamic-art treatment requires a separate bounded slice.

| State | Owner choice and overlay/unavailable behavior |
|---|---|
| Daylight with supported value | ___ |
| Night with supported value | ___ |
| Supported rain | ___ |
| Supported snow | ___ |
| Selected value unavailable | ___ |

## Scope boundary

OX-99 is Park-specific wiring on the existing Compose Canvas. OX-101 specifies and verifies the reusable plane/layer ordering contract; it does not implement a shared image compositor. If general renderer capability is required, it needs its own bounded slice. This proposal does not change scene-pack manifest v2.

## Artifacts

- Behavior source: `ox-99-park-behavior-board.dot`
- Behavior renders: `ox-99-park-behavior-board.svg`, `ox-99-park-behavior-board.png`
- UI artwork source/review render: `ox-99-park-ui-board.svg`, `ox-99-park-ui-board.png`
- UI artwork notes: `ox-99-park-ui-board.md`

All are drafts pending resolution of the remaining choices and subsequent owner review. They must be fingerprinted and approved in a bounded cycle plan before that plan can be finalized or activated.
