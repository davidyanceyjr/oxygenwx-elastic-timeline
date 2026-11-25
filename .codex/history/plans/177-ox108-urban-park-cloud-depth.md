# Cycle plan — 177-ox108-urban-park-cloud-depth

State: CLOSED
Plan maturity: FINAL
Cycle ID: `177-ox108-urban-park-cloud-depth`
Roadmap items: OX-108
Objective: Replace inherited Urban Park cloud passes with three distinct clouds mapped to canonical cloudy events and drawn directly above the Background image plane.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "177-ox108-urban-park-cloud-depth",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-108",
      "depends_on": [
        "OX-05",
        "OX-99"
      ],
      "status": "DONE",
      "exit": "Installed Urban Park evidence demonstrates the correct active layer set for all four canonical cloudy event IDs, with three distinct cloud forms immediately above the Background plane, below Middleground and Foreground, and fully inside the top 33% of the scene canvas. Clear and non-cloudy selected events emit no OX-108 themed cloud layers. Existing precipitation behavior and depth, sky/Sun/Moon treatment, supplied image assets and transforms, app-owned forecast UI, and timeline behavior remain intact in the reviewed states.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.dot",
              "sha256": "9c2159a05a05c63a2fde9031140ac86378904044ad2e3257566c6e32f7b35b84"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.svg",
              "sha256": "498a1cb3778a9c287e9c9cdba2005a39b354abbd776a51314c1de44b1fe089cd"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.png",
              "sha256": "5c350302550d8e200fc00157d7905575c3c05420bc104bfa6552cb0319df760c"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.md",
              "sha256": "4da977b6e1fedba7b9bb3427b5fe9b727dc6d3a4eafcb88d0c4987045c77e8ad"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
              "sha256": "cbdbb4f89e409a4ba1086a644509c715800b089958f03eb2c72af9ef0e727e2f"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png",
              "sha256": "74cd87db4b5329f96a77f572f250c1f7ab67a00cefbfdde7015cfcdb5364d653"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.svg",
              "sha256": "32507503f44262eb0106cc5ea3c4f74eb0c8d2c2aeccf591ec5a4cff1c950042"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.png",
              "sha256": "3760afc59bcd7d8b4d0317c79b7f642d2327024a450d86d49f0b8241e154dce3"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.md",
              "sha256": "aa350357770e765529a3676b70982d30e4209a9f0f92d6e9afd6426713cc5e63"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
              "sha256": "cbdbb4f89e409a4ba1086a644509c715800b089958f03eb2c72af9ef0e727e2f"
            },
            {
              "path": ".codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png",
              "sha256": "74cd87db4b5329f96a77f572f250c1f7ab67a00cefbfdde7015cfcdb5364d653"
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
      "outcome": "Implemented the approved canonical-event mapping and three-layer Urban Park cloud stack. Pixel 3 XL review captured all four cloudy IDs, a sunny no-cloud control, preserved rain, upper-third/depth review, and a direct timeline scrub through the replay. Build and installation succeeded; automated tests were not authorized.",
      "evidence": [
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/README.md",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/01-partly-sunny-cloudy.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/02-partly-cloudy-night.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/03-mostly-cloudy.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/04-overcast.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/05-sunny-clear-control.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/06-rain-preservation.png",
        ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/07-timeline-scrub.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-24",
    "summary": "Implemented OX-108's selected-event mapping and three distinct Urban Park cloud layers. The Pixel 3 XL review captured all four cloudy events, a sunny no-layer control, preserved rain, the top-third/depth check, and a direct timeline scrub to 3:29 PM with the overcast event selected. Evidence and interaction observations are stored under .codex/test-artifacts/177-ox108-urban-park-cloud-depth/. The OX-108 roadmap outcome and evidence are recorded. Automated tests were not run because this cycle did not authorize them; unrelated pre-existing workspace changes were preserved.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT: Workflow scaffold: OK; cycle 177 was ACTIVE/CLOSEOUT.",
      "git diff --check — passed.",
      "python scripts/dev.py build — passed: BUILD SUCCESSFUL; 39 actionable tasks were up to date.",
      "python scripts/dev.py run --serial 8AKY0M32X — debug app built, installed, and launched successfully on connected Pixel 3 XL serial 8AKY0M32X.",
      "adb devices -l — Pixel 3 XL 8AKY0M32X reported state device.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/177-ox108-urban-park-cloud-depth/<capture>.png screenshot — succeeded for all seven recorded captures.",
      "Installed review — exact sky replay event labels and selected times confirmed for PARTLY_SUNNY_CLOUDY 12:00, PARTLY_CLOUDY_NIGHT 13:00, MOSTLY_CLOUDY 14:00, CLOUDY_OVERCAST 15:00, and SUNNY 08:00; rain fixture RAIN 09:00 retained rain cue, streaks, and 25% precipitation. Direct timeline drags updated selected time and event through the reviewed range; final state 3:29 PM, Cloudy or overcast.",
      "Automated tests were not run; plan authorization excludes them unless separately requested."
    ],
    "evidence_directory": ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "177-ox108-urban-park-cloud-depth",
  "slice_ids": [
    "OX-108"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.dot",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.svg",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.md",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.svg",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.md",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png"
  ],
  "completed_gates": [
    "Rebuilt behavior and UI artwork boards around the owner-revised three-cloud event mapping; complete packages approved and fingerprinted.",
    "OX-108_behavior_board_approved",
    "OX-108_ui_artwork_board_approved",
    "OX-108 behavior and UI artwork board packages approved on 2026-11-24.",
    "OX-108 behavior and UI artwork approvals verified with complete fingerprints",
    "OX-108 plan reviewed against dependencies, product and UI invariants, callers, compatibility, acceptance, authorization, and evidence",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "All installed OX-108 exit states reviewed on Pixel 3 XL; canonical layer mapping, clear output, plane order, top-third bound, rain preservation, and direct scrub were checked."
  ],
  "decisions": [
    "The earlier two-pass depth-only proposal is superseded by the owner direction to replace inherited Park scene clouds with three distinct event-mapped layers.",
    "Map PARTLY_SUNNY_CLOUDY and PARTLY_CLOUDY_NIGHT to Layer 1, MOSTLY_CLOUDY to Layers 1 and 2, and CLOUDY_OVERCAST to Layers 1, 2, and 3; select only from the canonical event identity, not cloud-cover percentage.",
    "Use the Pixel 3 XL screen capture and upper crop as current-state reference; the artwork composite uses current plane assets and is conceptual, not installed acceptance evidence.",
    "OX-108 behavior board approval refreshed on 2026-11-24.",
    "OX-108 ui_artwork board approval refreshed on 2026-11-24.",
    "Product owner confirmed that preapproval status labels remaining in the exact fingerprinted OX-108 board files are stale metadata; the behavior and UI artwork packages are approved as recorded in the roster manifest. The plan remains PLANNED, and activation is still required before implementation.",
    "The approved mapping uses selected canonical event identity: PARTLY_SUNNY_CLOUDY/PARTLY_CLOUDY_NIGHT → Layer 1; MOSTLY_CLOUDY → Layers 1+2; CLOUDY_OVERCAST → Layers 1+2+3.",
    "The approved cloud stack draws immediately after the Background image plane; existing Sun/Moon cues, Middleground, Foreground, and precipitation follow in the approved order.",
    "Existing debug SG.11 Sky replay exposes all four cloudy event IDs and clear events through selectable inspection times; use it for installed event-mapping review without changing production fixtures.",
    "Plan is FINAL at REVIEW and remains PLANNED; no implementation was started."
  ],
  "open_questions": [],
  "completed_outputs": [
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.dot",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.svg",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.md",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.svg",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.md",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-background-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-middleground-preview.png",
    ".codex/concepts/OX-108-urban-park-cloud-depth-park-foreground-preview.png",
    "Recorded behavior board approval for OX-108.",
    "Recorded ui_artwork board approval for OX-108.",
    "Recorded owner approval and complete SHA-256 fingerprints for both OX-108 board packages.",
    "Recorded the product owner's clarification that preapproval status labels in the approved fingerprinted board files are stale metadata.",
    "Completed bounded OX-108 plan with architecture seam, event mapping, exclusions, installed acceptance states, authorized verification, and evidence target.",
    "Activated OX-108 for execution.",
    "Closed slice OX-108 as DONE.",
    "OX-108 implementation, debug build/install, required Pixel captures, precipitation review, and direct timeline scrub completed; evidence is in .codex/test-artifacts/177-ox108-urban-park-cloud-depth/.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/README.md",
    ".codex/history/177-ox108-urban-park-cloud-depth.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/177-ox108-urban-park-cloud-depth.md",
    ".codex/test-artifacts/177-ox108-urban-park-cloud-depth/README.md",
    "complete git diff and git status"
  ],
  "next_action": "Inspect the complete final diff and existing dirty work; run git diff --check and python scripts/dev.py workflow; close and archive the cycle with exact results and limits."
}
```
<!-- cycle-checkpoint:end -->

## Slice intake (SLICE)

The owner revised the initial depth-only request. OX-108 now replaces the inherited Urban Park scene cloud artwork with three distinct forms and maps the canonical cloudy weather events onto those layers. OX-108 remains the sole NEXT slice. The owner approved both complete board packages on 2026-11-24; approval and per-file SHA-256 fingerprints are recorded in this cycle manifest. The plan remains PLANNED and implementation has not begun.

### Bounded scope

- Replace the two inherited generic Urban Park scene cloud passes with three distinct theme cloud layers: separated broken cumulus (Layer 1), a broad broken cloud deck (Layer 2), and a continuous soft overcast veil (Layer 3).
- Map the selected canonical event identity to cumulative layers: PARTLY_SUNNY_CLOUDY and PARTLY_CLOUDY_NIGHT → Layer 1; MOSTLY_CLOUDY → Layers 1 and 2; CLOUDY_OVERCAST → Layers 1, 2, and 3. Other or absent selected event IDs emit no OX-108 themed cloud layers.
- Use the event already selected by existing primary-event / WeatherEventPriority behavior, or the existing permitted legacy fallback. Preserve source-valid-time and carried-state meaning. Do not derive cloud type or layer count from cloud-cover percentage or unsupported source codes.
- Draw active cloud layers in Layer 1, Layer 2, Layer 3 order immediately after the Background image plane, then preserve the existing Sun/Moon cues, Middleground image, Foreground image, and precipitation order. Opaque Middleground and Foreground pixels occlude clouds; Background remains below them.
- Keep all visible cloud output, including blur, movement, and transitions, within the top 33% of the scene canvas. Preserve the existing sky treatment and precipitation behavior.
- Change Urban Park only. Leave First City and Living Urban Park, supplied image bytes/transforms, the app-owned forecast symbol and text, forecast values, controls, other worlds, and timeline behavior unchanged.

### Verified repository state and dependency constraints

- OX-05 is DONE and establishes Background → Middleground → Foreground ordering, with clouds at Background or Middleground depth and precipitation at Foreground depth. The controlling specification also bounds cloud output to the top 33% of the atmospheric scene canvas.
- OX-99 is DONE and adds the selectable Urban Park scene using three image planes. Its current renderer draws two inherited cloud passes at different depths around those planes. The owner has now explicitly asked to replace those scene cloud forms and use a new Park-specific event mapping.
- The canonical event model contains four cloudy IDs: PARTLY_SUNNY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, and CLOUDY_OVERCAST. Canonical presentation already selects an event using the explicit primary index or WeatherEventPriority and may use its constrained legacy fallback. Existing scene cloud decisions currently carry cloud-cover facts rather than this event identity, so the implementation will need to pass the canonical selected event identity into the Urban Park mapping without changing event selection.
- `CatalogTimelineView.weatherSymbolId` is populated from `CanonicalForecastProjection.project(...).selectedEvent?.symbolId` for the selected instant. `CatalogTimeLabScreenContent` already has both that view and the Urban Park renderer call, so it can pass the selected symbol ID directly to the Park renderer. Keep this mapping at the Park rendering boundary; do not change `SceneReading`, global scene decisions, or the First City/Living Urban Park paths.
- The debug-only `DebugSg11SkyCatalogFixture` and existing `DebugCatalogActivity` inspection route expose clear and all four cloudy IDs at selectable hourly instants. This supplies installed acceptance states without changing canonical production fixture data. The existing debug rain profile/catalog can supply the preserved precipitation observation.
- The large cloudy symbol drawn over the Park photo in the capture is app-owned forecast UI. It is distinct from the two atmospheric cloud passes and remains unchanged.

### UI slice contract

- **Functional invariants:** all four cloudy event IDs map exactly to the approved cumulative layer sets; absent/non-cloudy IDs do not draw OX-108 cloud layers; the cloud stack is immediately above Background and behind Middleground and Foreground; output remains inside the top 33%; existing precipitation behavior/depth and selected-time event semantics remain intact.
- **Visual objective:** replace the repeated generic bands with three distinct Urban Park cloud forms whose cumulative combinations communicate partly cloudy, mostly cloudy, and overcast, while preserving the photographic scene and unchanged app-owned forecast/timeline UI.
- **In-scope installed states:** Urban Park with PARTLY_SUNNY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY, and CLOUDY_OVERCAST selected; clear/non-cloudy selected event; a supported precipitation state; direct timeline scrub through the reviewed states.
- **Acceptance/evidence:** installed captures demonstrate every event mapping, clear/non-cloudy output, plane depth/occlusion, and the 33% bound. Review a direct timeline scrub for responsiveness, gesture ownership, and readable forecast/timeline labels. Preserve actual evidence under `.codex/test-artifacts/177-ox108-urban-park-cloud-depth/`.
- **Verification authorization:** build and the scoped installed visual/interaction review are in scope. No automated tests are added or run unless separately authorized. RTL, TalkBack, and broad system/environment matrices are outside this slice.

### Approved board gates and constraints

- Product owner approved the behavior package and UI artwork package on 2026-11-24. Their complete artifact lists and SHA-256 fingerprints are recorded separately under OX-108 in the manifest above.
- Preapproval status labels remaining in those exact fingerprinted board files are stale metadata. The roster manifest records the approved packages; the plan remains PLANNED, and activation is still required before implementation.
- Behavior package: `.codex/concepts/OX-108-urban-park-cloud-depth-behavior-board.dot`, `.svg`, `.png`, and `.md`, plus the reference capture and detail crop. It governs the canonical event mapping, empty output for other/absent events, cloud depth/order, upper-third boundary, and preserved scene/forecast behavior.
- UI artwork package: `.codex/concepts/OX-108-urban-park-cloud-depth-ui-artwork-board.svg`, `.png`, and `.md`, plus the reference capture, detail crop, and three plane previews. It governs the three distinct cloud forms and their asset-based conceptual placement. The concept is not installed acceptance evidence.
- The reviewed reference is the Pixel 3 XL capture at `.codex/concepts/OX-108-urban-park-cloud-depth-device-capture.png` and upper-scene crop `.codex/concepts/OX-108-urban-park-cloud-depth-device-detail-crop.png`. These record the current output to replace; all after-state acceptance remains device evidence.

### Implementation sequence

1. Pass `CatalogTimelineView.weatherSymbolId` from `CatalogTimeLabScreenContent` to `CatalogUrbanParkScene`. Map only `PARTLY_SUNNY_CLOUDY` and `PARTLY_CLOUDY_NIGHT` to Layer 1, `MOSTLY_CLOUDY` to Layers 1 and 2, and `CLOUDY_OVERCAST` to Layers 1, 2, and 3. All other or absent IDs select no OX-108 layers. Do not infer from `SceneReading.cloudCover`, composition cloud cover, display strings, or raw source codes.
2. In the Park renderer, remove its two inherited `BACKGROUND_CLOUDS` and `MIDDLE_GROUND_CLOUDS` renderer invocations. Draw the active, distinct Park forms in Layer 1 → Layer 2 → Layer 3 order immediately after the Background plane and before existing Sun/Moon cues and later planes. Keep their full visible bounds within `0..sceneHeight/3`; the approved artwork is static and needs no movement/blur outside that bound.
3. Preserve the shared Park plane dimensions/offset, sky palette and rain wash, Sun/Moon cue decisions, Middleground/Foreground image passes, and existing precipitation renderer. Do not change First City or Living Urban Park rendering, selected-time/event projection, symbol/text UI, other worlds, or timeline code.
4. Build and install the debug app on the connected Pixel 3 XL. Use the existing SG.11 Sky replay to inspect clear and all four cloudy event IDs in Urban Park. Capture the exact resulting states, confirm their layer count and visual form, verify later image planes occlude clouds and no cloud edge crosses below the upper third. Inspect one existing precipitation state and directly scrub the timeline across the review states, recording selected labels, response, gesture ownership, and readability.

### Verification, evidence, and exclusions

- Authorized checks: `python scripts/dev.py workflow`, `python scripts/dev.py build`, scoped debug-app installation/launch, installed screenshots and direct visual/timeline interaction review, and `git diff --check` plus final diff inspection. No tests are authorized; do not add or run them. Do not claim RTL, TalkBack, font-scale, broad accessibility, or broad environment acceptance.
- Keep actual installed captures, device/route/state details, interaction observations, exact command results, and limits in `.codex/test-artifacts/177-ox108-urban-park-cloud-depth/`. The preview composite and initial capture under `.codex/concepts/` are board references only.
- No failure path or compatibility migration is needed for forecast data: the rendering input is the already-projected nullable `WeatherSymbolId`, and unrecognized IDs map to zero Park cloud layers. Existing event priority, fallback, source time, carried-state, cloud-cover facts, and precipitation selection remain upstream and unchanged.
- Scope excludes event-model edits, new or revised fixtures, weather inference, changes to supplied Park assets/transforms, city-wide cloud changes, scene-selection/settings changes, new animation/effects, forecast UI or timeline changes, automated tests, and app-wide system acceptance.

### Current stage disposition

- The complete approved board gates and bounded plan review are recorded. The plan is FINAL at REVIEW; the cycle remains PLANNED with no active slice.
- Board approval and plan finalization do not activate the cycle. This plan does not claim device acceptance or slice completion.

No OX-108 implementation changes have been made as part of cycle 177 at this review checkpoint.
