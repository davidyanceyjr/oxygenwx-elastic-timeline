# Cycle plan — 176-ox99-urban-park-theme-proof

State: CLOSED
Plan maturity: FINAL
Cycle ID: `176-ox99-urban-park-theme-proof`
Roadmap items: OX-99
Objective: Add a selectable Urban Park theme from the First City baseline proof with three ordered image layers and separately rendered Sun/Moon cues, preserving the live timeline and current theme catalog behavior.

## Cycle roster and board approvals

The ordered roster records OX-99's dependencies, observable exit, approved board
packages, and complete package fingerprints. The cycle remains PLANNED and has
no active slice.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "176-ox99-urban-park-theme-proof",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-99",
      "depends_on": [
        "OX-31",
        "OX-33",
        "OX-41",
        "OX-55",
        "OX-69",
        "OX-85"
      ],
      "status": "DONE",
      "exit": "The Settings theme list contains a distinct Urban Park entry with accurate active/available state, and selecting it renders the three supplied images as separate background → middleground → foreground planes. The Sun and supported Moon remain independent selected-time cue layers over the background; the timeline and forecast readout remain visible and app-owned. Installed day and Moon-visible captures show the same aligned image viewport without lost transparency or unplanned crop; direct theme-switch and timeline-scrub observations retain the exact selected instant, forecast facts, and gesture response. The original First City baseline proof and Living Urban Park default remain available and unchanged.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-99-urban-park-theme-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-behavior-board.dot",
              "sha256": "98ff8024fd0c6c37954b646caf0466148e813525c0e8d529f4ab6a6eb7ac0a96"
            },
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-behavior-board.svg",
              "sha256": "4126699a721c627151387c1cc22ca5cc034d0b343f32fed59792017cd493a7dd"
            },
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-behavior-board.png",
              "sha256": "5a97eb55db618e071e94770eac47c6ac28347635f31c914f4062a592b2e3aef0"
            },
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-behavior-board.md",
              "sha256": "7c2ac0df7dcfbed89dd39bfd2c20c518cf57ac2a325849c55e0aa3e06543a23b"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-24",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.svg",
              "sha256": "49f7f3e71879e5be1772b94b01a28357c4d8346eef3d584eb82f653756898fa8"
            },
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.png",
              "sha256": "d8c873e85e2d2fd82b627246e5288e2698c5af9bb5728a528ad5615569038b7d"
            },
            {
              "path": ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.md",
              "sha256": "1c646d8dc94a8c94a0dc1761cb423c6cee3b08043c3a5f48a5982dd6cd172042"
            }
          ]
        }
      },
      "outcome": "Implemented Urban Park as a distinct always-available bundled theme with a stable app-managed revision and no pack-loader path. Installed Pixel 3 XL review met the planned theme-list, aligned three-plane scene, supported Sun/Moon, timeline-scrub, direct theme-switch, and First City baseline-proof exits. No tests were added or run; scoped device/system limits are recorded with the evidence.",
      "evidence": [
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/README.md",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/01-settings-living-urban-park-active.png",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/02-settings-urban-park-active.png",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/03-urban-park-sun-visible.png",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/04-urban-park-after-timeline-scrub.png",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/05-first-city-baseline-proof.png",
        ".codex/test-artifacts/176-ox99-urban-park-theme-proof/06-urban-park-moon-visible.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-24",
    "summary": "Completed OX-99 by adding Urban Park as an additional selectable bundled theme with app-managed identity and revision, rendering the three supplied transparent planes in one bottom-anchored viewport with First City weather and selected-time celestial decisions. Settings reports distinct active and available states; the Living Urban Park bundled-free default and First City baseline proof remain available. Pixel 3 XL installed review met the slice exit, including direct theme-switch and timeline-scrub continuity. Six screenshots and scoped interaction notes are preserved under .codex/test-artifacts/176-ox99-urban-park-theme-proof/. No automated tests were authorized or run; system-wide accessibility and environment acceptance are not claimed.",
    "verification": [
      "python scripts/dev.py workflow — exit 0 before activation and at CLOSEOUT; Workflow scaffold: OK.",
      "python scripts/dev.py build — BUILD SUCCESSFUL.",
      "python scripts/dev.py --serial 8AKY0M32X run — exit 0; installed and launched the debug app on Pixel 3 XL.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/01-settings-living-urban-park-active.png screenshot — captured Settings with Living Urban Park active.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/02-settings-urban-park-active.png screenshot — captured Settings with Urban Park active.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/03-urban-park-sun-visible.png screenshot — captured the noon Urban Park scene.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/04-urban-park-after-timeline-scrub.png screenshot — captured the selected 2:44:34 PM CDT scene after the direct timeline drag.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/05-first-city-baseline-proof.png screenshot — captured the existing debug-only baseline proof.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/06-urban-park-moon-visible.png screenshot — captured the supported afternoon Sun/Moon scene.",
      "identify -format %f-wxh-channels-alpha-min-max on the three urban_park PNGs — each is 1024x1536 sRGBA with alpha minimum 0.",
      "git diff --check — exit 0 with no whitespace errors at CLOSEOUT.",
      "No tests were added or run. RTL, TalkBack/accessibility services, font scaling, and motion settings were not checked; these app-wide checks are outside this slice."
    ],
    "evidence_directory": ".codex/test-artifacts/176-ox99-urban-park-theme-proof/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "176-ox99-urban-park-theme-proof",
  "slice_ids": [
    "OX-99"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-99-urban-park-theme-behavior-board.dot",
    ".codex/concepts/OX-99-urban-park-theme-behavior-board.svg",
    ".codex/concepts/OX-99-urban-park-theme-behavior-board.png",
    ".codex/concepts/OX-99-urban-park-theme-behavior-board.md",
    ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.svg",
    ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.png",
    ".codex/concepts/OX-99-urban-park-theme-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-31, OX-33, OX-41, OX-55, OX-69, and OX-85 are DONE; no external dependencies remain unresolved.",
    "OX-99 behavior and UI artwork board approvals and package fingerprints are recorded.",
    "The approved OX-99 files retain stale draft-status banners; the roadmap and roster approval records govern, consistent with the G.7 review-stage-banner precedent.",
    "The catalog representation question is resolved from OX-31/OX-33/OX-41/OX-69 and current catalog, Settings, selection, and MainActivity interfaces.",
    "The OX-99 plan preserves the approved board scope, exact dependencies, test authorization, and installed evidence obligations, including the baseline-proof and default-theme regressions.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-99 installed exit met on Pixel 3 XL; exact states and scoped interaction observations are recorded under the cycle evidence directory."
  ],
  "decisions": [
    "Owner confirmed Urban Park is an additional selectable theme and Living Urban Park remains the bundled free default.",
    "Product owner approved both OX-99 board packages on 2026-11-24; their exact files and SHA-256 fingerprints are recorded in the OX-99 roster manifest.",
    "Repository-resolvable ambiguity: the fingerprinted OX-99 behavior and UI artwork artifacts retain their original draft/review-stage banners, while the OX-99 roadmap entry and roster explicitly record owner approval for those exact fingerprinted packages. The recorded hashes match the files, and G.7 documents the same stale-banner convention. Treat the banners as stale review-stage metadata and keep approved artifacts unchanged.",
    "Resolved Urban Park as a BUNDLED_SELECTABLE entry with stable ID urban-park, an opaque app-managed content revision, and ALWAYS_AVAILABLE validity; keep it out of app-owned registration and pack loading. Supporting evidence: docs/ROADMAP.md OX-31/OX-33/OX-41/OX-69, app/src/main/java/com/oxygen/weather/timeline/data/scenepack/InstalledThemeCatalog.kt, app/src/main/java/com/oxygen/weather/timeline/ui/CatalogSettingsScreen.kt, app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt, and app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt.",
    "The planned OX-05 authored synthetic astronomy fixture supplied the exact supported Sun/Moon review inputs; no scope ambiguity or unresolved owner question remained."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Added OX-99 to the roadmap as the sole NEXT slice and created cycle 176 at SLICE.",
    "Prepared and displayed the OX-99 behavior and UI artwork boards on DISPLAY=:0; the product owner approved both packages and their per-file fingerprints are recorded in the OX-99 roster entry.",
    "Resolved the repository-grounded catalog question: add one always-available bundled-selectable Urban Park identity distinct from Living Urban Park's bundled-free role and from app-owned pack registrations.",
    "Completed the bounded OX-99 draft from the approved boards, dependencies, and current catalog/renderer interfaces.",
    "Completed the full OX-99 plan review against the approved behavior/UI boards, dependency outcomes, scoped authorities, and catalog/renderer interfaces.",
    "Finalized the bounded OX-99 plan with its observable exit, implementation boundary, Pixel 3 XL installed states, baseline-proof/default regression checks, and evidence target.",
    "Activated OX-99 for execution.",
    "Closed slice OX-99 as DONE.",
    "OX-99 implementation, three bundled assets, and six installed Pixel 3 XL screenshots with scoped interaction notes are complete.",
    "Asset inspection confirmed matching 1024 × 1536 RGBA dimensions and transparency; device captures show aligned planes and readable forecast/timeline UI.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/README.md",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/01-settings-living-urban-park-active.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/02-settings-urban-park-active.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/03-urban-park-sun-visible.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/04-urban-park-after-timeline-scrub.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/05-first-city-baseline-proof.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/06-urban-park-moon-visible.png",
    ".codex/history/176-ox99-urban-park-theme-proof.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/176-ox99-urban-park-theme-proof.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/InstalledThemeCatalog.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogSettingsScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/res/drawable-nodpi/urban_park_background.png",
    "app/src/main/res/drawable-nodpi/urban_park_middleground.png",
    "app/src/main/res/drawable-nodpi/urban_park_foreground.png",
    ".codex/test-artifacts/176-ox99-urban-park-theme-proof/README.md"
  ],
  "next_action": "Inspect the complete final diff and preserved workspace state, run diff and workflow checks at CLOSEOUT, then archive cycle 176."
}
```
<!-- cycle-checkpoint:end -->

## Approved scope and planning notes

The owner's direction is to add `Urban Park` as its own selectable built-in theme by copying the `First City · Baseline proof` experience. `Living Urban Park` remains the bundled free default, and the debug-only baseline proof remains unchanged. The supplied 1024 × 1536 PNGs form background → middleground → foreground planes with one shared viewport transform and preserved alpha. Existing selected-time Sun and supported Moon cues stay separately rendered above the background plane; an absent or unsupported Moon cue remains omitted under the existing rules. The existing live timeline and legible forecast readout stay app-owned.

The scene art is fixed sunlit imagery. This slice does not add night or weather variants, new forecast interpretation, a generic plane renderer, a reusable scene-pack format, asset import, payment behavior, or broad accessibility/system acceptance.

The product owner approved the behavior and UI artwork boards on 2026-11-24. Their complete file lists and SHA-256 fingerprints are stored separately in the OX-99 cycle-manifest board records. The behavior board controls the additive theme identity, selected-time continuity, scene layer order, fixed-art boundary, and exclusions. The UI board controls the Settings row, Sun-visible and Moon-visible review states, and the explicit static-art limitation. The fingerprinted behavior and UI artwork artifacts retain their original draft/review-stage banners, including their companion Markdown status lines. The OX-99 roadmap approval record and roster manifest identify these exact files as approved; this stale-banner handling follows the repository's G.7 precedent, whose approved package retained its review-stage banner and question. The approved artifacts remain unchanged.

### Repository-grounded catalog resolution

The current catalog in `InstalledThemeCatalog.kt` models one `BUNDLED_FREE` role plus `APP_OWNED` registrations. Settings labels and selection dispatch branch on those two origins; app-owned entries go through the installed-pack loader. Treating Urban Park as app-owned would misstate its source and involve pack loading. Keeping it only as a `CatalogWorld` would leave it out of the catalog-backed Settings list.

Resolve this by adding one explicit bundled-selectable catalog origin/metadata path for the built-in Urban Park theme, with stable ID `urban-park`, an opaque app-managed content revision, and always-available validity. Keep Living Urban Park's existing `BUNDLED_FREE` identity and default role unchanged. Route the new entry to the dedicated native Urban Park world renderer; it does not use app-owned pack registration, compatibility checks, or pack loading. Keep the revision distinct from renderer compatibility or byte identity; app-owned registrations retain their established version requirements. Settings active state is keyed to the selected built-in theme identity.

This resolution follows OX-31's catalog distinction and version contract, OX-41's default-role decision, OX-33's catalog-backed Settings list, and OX-69's Settings-to-world selection path. Direct implementation evidence is in `app/src/main/java/com/oxygen/weather/timeline/data/scenepack/InstalledThemeCatalog.kt`, `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogSettingsScreen.kt`, `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`, and `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt`. The open catalog-representation question is resolved; no owner decision remains open.

### Bounded implementation sequence

1. Add the three supplied transparent PNGs as app-bundled, density-independent scene resources and define a finite Urban Park scene using the existing 1024 × 1536 portrait mapping (fit width, preserve aspect, anchor at bottom) with one shared transform for all planes.
2. Reuse the baseline proof's existing selected-time/weather decision and cue behavior while leaving `FirstCityTheme` and the debug-only `First City · Baseline proof` choice unchanged.
3. Extend the catalog and Settings active-state path for the distinct always-available Urban Park identity; route selection to the native Urban Park scene, not to the installed-pack loader.
4. Render the approved back-to-front order: retain the existing sky/backdrop under transparent pixels; draw the background image, separate selected-time Sun / supported Moon cue layers, existing background cloud pass, middleground image, existing middleground cloud pass, and foreground image. Keep the existing selected-time precipitation pass at its current foreground depth when supported inputs call for it, and retain the existing readout/time treatment and interactive timeline as app-owned layers. Do not add Park artwork variants or new weather interpretation.
5. Verify the approved installed states and interactions; record only observed results in the cycle evidence directory.

### Verification and evidence target

The roadmap exit requires Pixel 3 XL portrait captures of the Settings list with Living Urban Park active and Urban Park available, then Urban Park active; Sun-visible and supported Moon-visible Urban Park scenes; and the existing debug-only First City baseline proof. Record focused observations of selecting Urban Park, returning to Living Urban Park, and directly scrubbing the timeline. Confirm the shared image viewport and transparency across celestial states, readable timeline/forecast labels, exact selected-instant and forecast-fact continuity, the unchanged baseline-proof choice, and the unchanged bundled-free default. Preserve captures and interaction notes under `.codex/test-artifacts/176-ox99-urban-park-theme-proof/`. No new or focused automated tests are authorized unless separately requested. The current planning stage claims no implementation or installed acceptance.

### Scope exclusions and compatibility

No weather/night artwork variants, new meteorological interpretation, generic plane framework, reusable scene-pack format, asset-import flow, payment behavior, new gestures, or app-wide RTL/TalkBack/system matrix. Preserve existing app-owned installed-theme registration, compatibility, availability, and loading behavior, as well as the current Living Urban Park default and First City baseline proof.
