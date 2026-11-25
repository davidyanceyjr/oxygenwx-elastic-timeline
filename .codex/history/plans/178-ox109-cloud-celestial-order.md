# Cycle plan — 178-ox109-cloud-celestial-order

State: CLOSED
Plan maturity: FINAL
Cycle ID: `178-ox109-cloud-celestial-order`
Roadmap items: OX-109
Objective: Place three independent transparent Urban Park cloud layers in front of celestial paths and below Middleground

## Cycle roster and board approvals

The structured roster records each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval. The activation
command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "178-ox109-cloud-celestial-order",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-109",
      "depends_on": [
        "OX-99",
        "OX-108"
      ],
      "status": "DONE",
      "exit": "Installed Urban Park captures across multiple selected instants show the blue-sky/star-filled backdrop, base Background, selected-time Sun and Moon paths, and three separately painted cloud layers tracking at rates distinct from the celestial paths. Clouds composite above Background/Sun/Moon and below the base and added Middleground layers; Foreground artwork remains closest. Every active cloud layer is transparent outside cloud forms. OX-108's event-to-layer mapping, selected instant, forecast facts, timeline response, and existing precipitation behavior remain intact.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.dot",
              "sha256": "a5d3e29221320ff8c01ed666ca3c3b63bf035d2a9e82f8a2650039b04c8cbe30"
            },
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.svg",
              "sha256": "088080bcee1cb7bbb32d6fea4af6cd15af9779208107ff24cc9e8da5d4f999e1"
            },
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.png",
              "sha256": "dad806ff34901127c492716ebf8bac8180b278cfcb2c68cf7a765793a6a1f3a0"
            },
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.md",
              "sha256": "44e2ffd3ef679c2ce2be61cbdbc543187a2bf910e574e1ba488a2835055ef465"
            },
            {
              "path": ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png",
              "sha256": "fe4f64ea8a6ab8c808dcdcef027c909444ceaa257168c2cad129980c66f5b212"
            },
            {
              "path": ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/01-partly-sunny-cloudy.png",
              "sha256": "e37497642d94d8b1ea039c4f768761d44f9eccfa35f871a3c2a081d4881fd19c"
            },
            {
              "path": ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/02-partly-cloudy-night.png",
              "sha256": "6bdde68f9ba6d18ace8609a5e1ea39af38dfb7fba2130305ee28c304d56d7be9"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.svg",
              "sha256": "99f6464d2463240ae069bd69de022454d4776ab51cc33b635933f8c869bc2a09"
            },
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.png",
              "sha256": "cfdb7a4d23e71a9c61597567f9f88fbb14875b9c48f51b8fa00bf9e394d28807"
            },
            {
              "path": ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.md",
              "sha256": "ed94bb71fee419760d92e1ca8150f71a1a3926e01d75b6f0057b049167fe52cd"
            },
            {
              "path": ".codex/concepts/OX-109-current-sun-cloudy-reference.png",
              "sha256": "e37497642d94d8b1ea039c4f768761d44f9eccfa35f871a3c2a081d4881fd19c"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
              "sha256": "47236d2e588ae623e115373fe0934d036528c4a88a4de2a6cd3434b8a3aa8125"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
              "sha256": "1f7e4b4ee17437b9f00f17876a6d347f7bf120e3d630ffba08ede3ca645a54f2"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png",
              "sha256": "524908a4d289c8995ac0a56951c44bfd09808fee3c2a325dd2c72191d814da75"
            }
          ]
        }
      },
      "outcome": "Reordered Urban Park to draw clouds above Background and selected-time Sun/Moon cues and below the base Park Middleground/Foreground; added independent deterministic selected-time motion for all active cloud types; preserved the OX-108 event mapping, selected-time source facts, timeline behavior, and precipitation. Installed scoped acceptance passed on Pixel 3 XL with paired Sun, Moon, mostly cloudy, and overcast instants, a sunny control, timeline scrubs, and rain preservation.",
      "evidence": [
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/README.md",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/01-sun-partly-12-00.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/02-sun-partly-12-29-scrub.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/03-moon-partly-12-50.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/04-moon-partly-13-23-scrub.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/05-mostly-cloudy-14-01.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/06-mostly-cloudy-14-28-scrub.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/07-overcast-14-59.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/08-overcast-15-27-scrub.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/09-sunny-08-01-scrub.png",
        ".codex/test-artifacts/178-ox109-cloud-celestial-order/10-rain-preservation-09-00.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-25",
    "summary": "Completed cycle 178 OX-109. Urban Park now paints its mapped cloud layers above the Background and selected-time Sun/Moon cues and below the base Park Middleground and Foreground, with separate deterministic selected-time tracks for each active cloud type. Installed acceptance and timeline scrubs passed for the approved Sun-visible and Moon-visible cloud states, mostly cloudy and overcast layer stacks, a sunny control, and preserved rain behavior. The bounded debug-only SG11 symbol-review-card switch was added for sky acceptance captures; it preserves replay data, defaults visible, and resets at debug-session end. Existing uncommitted Urban Park route/catalog and other workspace changes were preserved.",
    "verification": [
      "python scripts/dev.py build — passed: BUILD SUCCESSFUL (39 tasks, 5 executed).",
      "python scripts/dev.py run --serial 8AKY0M32X — debug app installed and launched on Pixel 3 XL.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture-path> — succeeded for all ten captures listed in .codex/test-artifacts/178-ox109-cloud-celestial-order/README.md.",
      "Installed visual and interaction review on Pixel 3 XL — passed for scoped OX-109 cloud/celestial ordering, independent motion, mapped layer sets, transparency, timeline scrubs, readable labels, and existing rain preservation; see evidence README for exact states and limitation.",
      "python scripts/dev.py workflow — passed at CLOSEOUT; workflow scaffold OK.",
      "git diff --check — passed with no whitespace errors.",
      "Automated tests — not added or run; not authorized by the active plan."
    ],
    "evidence_directory": ".codex/test-artifacts/178-ox109-cloud-celestial-order/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "178-ox109-cloud-celestial-order",
  "slice_ids": [
    "OX-109"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.dot",
    ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.svg",
    ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.png",
    ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.md",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/01-partly-sunny-cloudy.png",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/02-partly-cloudy-night.png",
    ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.svg",
    ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.png",
    ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.md",
    ".codex/concepts/OX-109-current-sun-cloudy-reference.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png"
  ],
  "completed_gates": [
    "OX-99 and OX-108 dependencies are DONE; the prior OX-108 board approval does not cover this new celestial/cloud order.",
    "OX-109 behavior and UI artwork boards show three independently tracked transparent cloud layers and the full Park plane hierarchy; the product owner approved both complete packages on 2026-11-24, with per-file fingerprints recorded in the roster.",
    "OX-109_behavior_board_approved",
    "OX-109_ui_artwork_board_approved",
    "all_slice_boards_approved",
    "Full review confirmed OX-109 is the sole NEXT slice and OX-99/OX-108 dependencies are DONE.",
    "All fingerprinted OX-109 behavior and UI artwork package files exist and match their recorded SHA-256 values; owner approval and board constraints are recorded.",
    "The bounded plan, observable exit, slice-local installed acceptance, authorized verification, evidence targets, and exclusions passed full review; cycle remains PLANNED and unactivated.",
    "final_plan_reviewed",
    "python scripts/dev.py build — passed (BUILD SUCCESSFUL; 39 tasks, 5 executed).",
    "python scripts/dev.py run --serial 8AKY0M32X — installed and launched the debug app on Pixel 3 XL.",
    "python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture-path> — succeeded for all ten recorded captures.",
    "Installed scene and interaction review — passed for OX-109 states and existing rain preservation; limitations are recorded in the evidence README.",
    "python scripts/dev.py workflow — passed after cycle activation/resume and again at closeout validation.",
    "git diff --check — passed with no whitespace errors.",
    "Automated tests — not added or run; the plan does not authorize them."
  ],
  "decisions": [
    "The owner directed a transparent cloud overlay and the back-to-front order Background → Sun → Moon → Clouds for Urban Park.",
    "The owner clarified that Clouds are three separately painted layers with sky tracks at rates distinct from Sun/Moon; base and added Middleground layers stack above them, and Foreground is closest to the viewer. Numeric rates are unspecified.",
    "OX-109 is a follow-up to closed OX-108; retain the existing event mapping and all unrelated theme behavior.",
    "Cue positions shown in the artwork concept are illustrative; app cue positions remain selected-time driven and unchanged.",
    "OX-109 behavior board approval refreshed on 2026-11-24.",
    "OX-109 ui_artwork board approval refreshed on 2026-11-24.",
    "The owner approved the complete OX-109 behavior and UI artwork board packages on 2026-11-24.",
    "The OX-109 UI artwork badge PROPOSED · NOT APPROVED is stale package metadata; the board notes, roadmap, and OX-109 roster record owner approval on 2026-11-24, all fingerprints match, and cycle 177 records the same stale-label resolution pattern.",
    "The owner clarified on 2026-11-25 that the supplied Background, Middleground, and Foreground images are the first/base art for their respective layers. The current renderer draws one base pass per image; any added Middleground content stacks above its base. OX-109 preserves the supplied base images and orders any added Middleground content after clouds without creating new art.",
    "Use FirstCityComposition.selectedInstant as the deterministic input for independent cloud-type tracks. The approved board leaves numerical rates and speed ranking unspecified; choose implementation-local rates and verify the relative travel in installed captures.",
    "The existing CatalogTimeLabScreen caller already supplies the selected WeatherSymbolId and FirstCityComposition.selectedInstant; no caller/API, forecast, theme-selection, persistence, or asset migration is needed.",
    "Owner resolution (2026-11-25): preserve the existing uncommitted Urban Park route/catalog changes as workspace context and do not edit them within cycle 178. Keep the FINAL plan and approved board scope; execute only the OX-109 renderer ordering and independent selected-time cloud motion.",
    "Owner resolution (2026-11-25): authorize a bounded debug-only control to hide the forced symbol-review card during OX-109 acceptance captures while preserving the required sg11-sky-typed-v2 replay and data; complete the planned checks.",
    "Execution implementation detail: the debug session menu will expose an ephemeral switch for the sg11-sky-typed-v2 symbol-review card only; it defaults visible and resets visible when the debug session ends."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Revised behavior board source, SVG/PNG renders, and contract for OX-109 to show the full scene hierarchy, independent cloud tracks, transparency, and preserved OX-108 event mapping.",
    "Revised UI artwork SVG/PNG and notes to show three painted cloud layers, relative tracks, the base/added Middleground hierarchy, and proposed Sun-visible/Moon-visible composites.",
    "Retained the current OX-108 Sun-visible installed capture byte-for-byte as a reference, not acceptance evidence.",
    "Recorded behavior board approval for OX-109.",
    "Recorded ui_artwork board approval for OX-109.",
    "Complete behavior and UI artwork board packages approved by the product owner and separately fingerprinted for OX-109.",
    "Completed full OX-109 plan review and finalized the plan maturity at FINAL with the cycle checkpoint at REVIEW.",
    "Recorded the stale artwork badge resolution and owner clarification of base image planes and added Middleground ordering without changing approved board files or their fingerprints.",
    "Recorded dependency, implementation boundary, ordered implementation and installed acceptance steps, test authorization, evidence target, risks, and exclusions.",
    "Activated OX-109 for execution.",
    "Implemented the owner-authorized, debug-only SG11 sky replay symbol-card visibility switch; it defaults visible and resets at debug-session end.",
    "Completed installed Urban Park review for all OX-109 cloud mappings, paired selected-time states, sunny control, direct timeline scrubs, and supported rain preservation.",
    "Added the OX-109 installed-review README and ten device captures under the cycle evidence directory.",
    "Closed slice OX-109 as DONE.",
    "Completed OX-109 installed acceptance with ten Pixel 3 XL captures, four paired cloudy selected-time reviews, sunny control, direct timeline scrubs, and supported rain preservation; evidence and limitations are in the cycle README.",
    "Inspected OX-109 renderer and debug-only control diffs and reviewed the remaining tracked text diffs as existing workspace context; preserved the Urban Park route/catalog changes.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/01-partly-sunny-cloudy.png",
    ".codex/concepts/OX-109-cloud-celestial-order-behavior-board.dot",
    ".codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.svg",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/README.md",
    ".codex/history/plans/177-ox108-urban-park-cloud-depth.md",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/README.md",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/01-sun-partly-12-00.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/02-sun-partly-12-29-scrub.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/03-moon-partly-12-50.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/04-moon-partly-13-23-scrub.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/05-mostly-cloudy-14-01.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/06-mostly-cloudy-14-28-scrub.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/07-overcast-14-59.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/08-overcast-15-27-scrub.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/09-sunny-08-01-scrub.png",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/10-rain-preservation-09-00.png",
    ".codex/history/178-ox109-cloud-celestial-order.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/178-ox109-cloud-celestial-order.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/178-ox109-cloud-celestial-order/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/CatalogPickerDelegate.kt"
  ],
  "next_action": "Archive cycle 178 with exact verification results, evidence, limits, and a record of preserved pre-existing workspace changes."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### SLICE intake

- **Selected slice:** OX-109 is the sole `NEXT` slice in cycle 178. The roadmap previously had no `NEXT` entry; this owner-directed follow-up follows completed OX-108.
- **Resolved dependencies:** OX-99 supplies the Urban Park theme and aligned image planes; OX-108 supplies the existing event-mapped cloud shapes. Both are recorded `DONE` in the roadmap and have cycle history/evidence.
- **Known scope:** Recompose the Urban Park scene as sky backdrop → base Background → selected-time Sun path → supported Moon path → three independently drawn, transparent OX-108 cloud layers → base Middleground → added Middleground layers → Foreground closest to the viewer. Each cloud type tracks across the sky at a rate distinct from Sun/Moon paths; exact numerical rates are unspecified. Preserve OX-108 event mapping and selected-time/source-time behavior.
- **Exclusions:** No changes to First City, Living Urban Park, image assets/transforms, cue selection or positioning, cloud event mapping, app-owned forecast UI, timeline behavior, or precipitation behavior.
- **Board gates:** The required behavior and UI artwork packages were approved by the product owner on 2026-11-24. Cycle 178 records the approval and complete per-file SHA-256 fingerprints separately for OX-109. The prior OX-108 approval covers its original order only; it does not approve this follow-up.
- **Verification policy:** Plan for installed captures of Sun-visible/cloudy and Moon-visible/cloudy Urban Park states at multiple selected instants, reviewing separate cloud layers, relative motion, transparent gaps, and the plane hierarchy, plus scoped timeline scrubs. Do not add or run automated tests unless separately requested; no broad RTL, TalkBack, or system matrix.
- **Current-state reference:** `.codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png` is an installed OX-108 reference, not OX-109 acceptance evidence.

### Scope from the approved boards

- **Objective:** Place three independently tracked, transparent Urban Park cloud layers in front of celestial paths and below Middleground.
- **Scope:** Reorder the existing Park composite to sky backdrop → base Background image → Sun path → supported Moon path → active OX-108 cloud type 1/2/3 layers → base Middleground image → added Middleground layers → Foreground artwork. Each cloud type is separately drawn and tracks across the sky at a rate distinct from the Sun and Moon paths; numeric rates are not prescribed in the board. Cloud layers draw only their active forms and are fully transparent elsewhere. Existing precipitation remains its separate unchanged effect pass.
- **Preserved invariants:** OX-108 cumulative event mapping, selected-event/source-time/carry meaning, celestial cue selection and paths, image bytes/transforms, sky treatment, precipitation behavior, app-owned forecast UI, timeline interaction, and all other themes remain unchanged. The existing top-third cloud bound remains in force.
- **Installed acceptance target:** Sun-visible/cloudy and Moon-visible/cloudy Urban Park states at multiple selected instants, plus direct timeline scrubs through both. Check depth where cues and clouds overlap, distinct relative cloud/celestial travel, transparency outside every cloud layer, added Middleground above its base, Foreground closest, and readable forecast/timeline information.
- **Verification authorization:** The roadmap authorizes a debug build, installation, scoped captures at multiple selected instants, and direct interaction review after the plan is finalized and activated. Automated tests remain unauthorized unless separately requested. No broad RTL, TalkBack, or system/environment matrix.
- **Evidence target:** `.codex/test-artifacts/178-ox109-cloud-celestial-order/`; history `.codex/history/178-ox109-cloud-celestial-order.md`; archived plan `.codex/history/plans/178-ox109-cloud-celestial-order.md`.

### BOARDS output (approved 2026-11-24)

- Behavior board source/render/contract: `.codex/concepts/OX-109-cloud-celestial-order-behavior-board.dot`, `.svg`, `.png`, `.md`.
- UI artwork source/render/notes: `.codex/concepts/OX-109-cloud-celestial-order-ui-artwork-board.svg`, `.png`, `.md`.
- UI artwork reference: `.codex/concepts/OX-109-current-sun-cloudy-reference.png`, copied byte-for-byte from the completed OX-108 installed capture. The concept also references the existing OX-108 Background, Middleground, and Foreground preview PNGs.
- The product owner approved the complete behavior and UI artwork packages on 2026-11-24. The OX-109 roster records the approval and SHA-256 fingerprints for each source, render, contract/notes file, and referenced review asset. The approved boards govern plan scope and acceptance but do not authorize implementation; the plan is now FINAL, while activation remains a separate required step.

## REVIEW — final bounded execution plan

### Review result and approved-board contract

- OX-109 is the sole `NEXT` slice in the cycle; its OX-99 and OX-108 dependencies are `DONE`. The pointer and ordered roster agree, and the workflow validator passed before review.
- The behavior package governs the complete back-to-front order, active OX-108 event mapping, independent selected-time cloud tracks, cloud-only alpha, upper-third placement, and preserved precipitation, cues, assets, forecast UI, and timeline behavior. The UI artwork package governs the Sun-visible and Moon-visible states, visible gaps, and the relative cloud/celestial movement. Both packages were approved by the product owner on 2026-11-24; all fingerprinted files exist and match their recorded SHA-256 values.
- The UI artwork SVG's `PROPOSED · NOT APPROVED` badge is stale package metadata. The package's approval is recorded in its companion notes, the cycle roster, and the OX-109 roadmap entry. Cycle 177 documented the same stale-label condition for its approved board package; the current package is retained unchanged so its recorded fingerprints and approval remain valid.
- The supplied Background, Middleground, and Foreground images are the first/base art for their respective layers. The current renderer has one base image pass for each. Clouds go after the Background and celestial cues, then the base Middleground; any added Middleground art stacks above its base, with Foreground closest. The approved UI artwork notes represent added Middleground layers as hierarchy and do not call for new art in this slice. Preserve the supplied images and transforms.

### Product and implementation boundary

- **In scope:** Urban Park composition and its existing OX-108 cloud drawing helpers in `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`. Place the selected-time Sun and supported Moon cues before the active cloud stack, then draw the current base Middleground and Foreground planes in their existing shared viewport transform. Keep the separate precipitation effect pass and its current behavior.
- **Cloud tracking:** Paint each active OX-108 cloud type as its own transparent pass. Derive each type's stable track offset from `FirstCityComposition.selectedInstant`, using independent per-type rates that differ from the existing Sun and Moon paths. The approved board intentionally leaves numeric rates and speed ranking unspecified; choose the bounded values in the renderer and record them in the implementation evidence. A fixed selected instant must produce a fixed cloud position; zoom and animation-frame time do not choose forecast time or cloud position.
- **Weather mapping:** Preserve the existing selected `WeatherSymbolId` mapping: `PARTLY_SUNNY_CLOUDY` and `PARTLY_CLOUDY_NIGHT` draw type 1; `MOSTLY_CLOUDY` draws types 1 and 2; `CLOUDY_OVERCAST` draws types 1, 2, and 3; other or absent IDs draw no OX-109 cloud pass. Do not derive layer count from cloud-cover percentage or alter event selection, source-time, or carry meaning.
- **Drawing invariants:** Keep each cloud type's authored form and alpha, paint no backing rectangle or wash, and retain the canvas clip at `CLOUD_REGION_FRACTION` (the top 33%). The sky and Background remain visible through non-cloud pixels. Preserve Park image bytes, source/destination sizing, anchoring, and transforms. Keep all Middleground content above the clouds and Foreground closest.
- **Compatibility:** `CatalogTimeLabScreen.kt` is the existing caller; it already supplies the selected symbol and a `FirstCityComposition` whose `selectedInstant` is authoritative. No caller/API, canonical forecast, theme-selection, persistence, or asset migration is required. Leave `FirstCityTheme.kt`, First City, Living Urban Park, all other worlds, app-owned readouts, controls, and timeline code unchanged.

### Ordered execution and verification

1. At execution start, run `python scripts/dev.py workflow`; confirm the cycle is activated for OX-109 and both dependencies remain complete. Inspect only the approved OX-109 contract, the listed renderer/caller interfaces, and the debug replay route in the next-stage read set.
2. In the Urban Park branch of `AtmosphericScene.kt`, reorder the existing passes to backdrop → base Background → selected-time Sun → supported Moon → active type 1/2/3 clouds → base and any existing added Middleground passes → Foreground. Keep precipitation in its current separate effect pass. Add independent selected-time offsets to the three cloud-type draws without changing their event mapping or artwork.
3. Run `python scripts/dev.py build`, then `python scripts/dev.py run --serial 8AKY0M32X` when that Pixel 3 XL is connected and authorized. Use the existing debug catalog picker and `sg11-sky-typed-v2` replay; do not change canonical fixtures to create review states.
4. Capture and inspect Urban Park installed states in `.codex/test-artifacts/178-ox109-cloud-celestial-order/`: a Sun-visible `PARTLY_SUNNY_CLOUDY` state at multiple selected instants; a Moon-visible `PARTLY_CLOUDY_NIGHT` state at multiple selected instants; `MOSTLY_CLOUDY` and `CLOUDY_OVERCAST` states to inspect the cumulative types 1+2 and 1+2+3; and a `SUNNY` no-cloud control. Use selected instants within the appropriate replay intervals and record their exact times and displayed events. Compare cloud positions with the cue paths at the same instants.
5. In the installed captures, review the order at cue/cloud overlaps and cloud gaps, the independent movement of each active type, visible underlying pixels through cloud gaps, the top-third bound, Middleground-over-cloud and Foreground-closest depth, and readable forecast/timeline labels. Review an existing supported precipitation state and directly scrub the timeline through both cue/cloud states; record selected-time continuity, gesture response, and readability. Capture the states with `python scripts/dev.py screenshot --serial 8AKY0M32X --output <cycle-evidence-path>` and save the interaction observations with them.
6. Add an evidence README naming the device, replay, selected instants/events, screenshots, and scoped observations. Run `git diff --check`, inspect the scoped final diff, and run `python scripts/dev.py workflow` at the applicable checkpoint. Close OX-109 only when the roster exit is met and the actual evidence is stored under the cycle evidence directory.

### Acceptance, authorization, and limits

- **Observable exit:** Installed Urban Park captures across multiple selected instants show the backdrop and base Background, Sun and Moon on their existing selected-time paths, and three separately drawn cloud layers tracking at rates distinct from the celestial paths. Clouds composite above Background/Sun/Moon and below the base and any added Middleground layers; Foreground remains closest. Non-cloud pixels in every active cloud layer are transparent. OX-108's event-to-layer mapping, selected instant, forecast facts, timeline response, and existing precipitation behavior remain intact.
- **Evidence target:** Actual installed screenshots and scoped interaction observations under `.codex/test-artifacts/178-ox109-cloud-celestial-order/`; closeout `.codex/history/178-ox109-cloud-celestial-order.md`; archived plan `.codex/history/plans/178-ox109-cloud-celestial-order.md`. OX-108 and OX-99 captures are references only, not OX-109 acceptance evidence.
- **Authorized checks:** The roadmap authorizes the debug build, installation, scoped installed captures, and direct interaction review. Automated tests remain unauthorized unless separately requested. Do not add or run tests, RTL, TalkBack, or broad system/environment checks in this design slice.
- **Exclusions:** No new or retouched artwork, no image-byte/transform changes, no new Middleground assets, no event or cue reinterpretation, no changes to precipitation, forecast facts/readouts, gesture ownership, timeline behavior, or other themes.
- **Execution risk/limit:** The board specifies relative movement but deliberately leaves numeric rates open. Installed review must demonstrate the required independent relative travel; no numeric rate or ranking is a product acceptance claim. Installed visual review covers the supplied base Middleground and Foreground images; any added Middleground art must remain above its base and the cloud group. OX-109 preserves the supplied base art and does not require authoring additional art.
