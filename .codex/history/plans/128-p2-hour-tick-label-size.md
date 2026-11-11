# Cycle plan — 128-p2-hour-tick-label-size

State: CLOSED
Plan maturity: FINAL
Cycle ID: `128-p2-hour-tick-label-size`
Roadmap items: P.2.27
Objective: Increase existing hourly tick-label sizes for legibility to the owner-selected final targets of 20 dp on active-catalog and 16 dp on fixture/debug, and raise the fixture/debug weekday, symbol, and axis group about 5 dp within the unchanged timeline band.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "128-p2-hour-tick-label-size",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.27",
      "depends_on": [
        "P.2.26"
      ],
      "status": "DONE",
      "exit": "On the installed active-catalog and fixture/debug hourly timelines, labels 3, 6, and 9 render at 20 dp and 16 dp respectively and are plainly readable in the Pixel 3 XL viewport; 12 AM and 12 PM remain fully visible at their corresponding long ticks. Labels remain attached to their ticks while scrubbing and do not clip against the timeline band or collide with the weekday, weather-symbol row, or focused-time readout. The fixture/debug weekday, symbols, axis, and labels sit about 5 dp higher within the unchanged 84 dp band; its focused-time cue remains bottom-anchored and overall screen height is unchanged. Daily mode has no hourly labels. Existing time selection, 48 dp/hour spacing, 24 dp symbols, tick cadence, forecast values, gesture behavior, and fixture/debug nearest-hour cue remain unchanged. Record actual installed hourly captures from both surfaces and a daily reference.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/P.2.27-hour-label-size-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-behavior-board.md",
              "sha256": "60d06f4d3dd9782e34b80674e5d2e26b4ea2f293aa54560412aea7d16e83c3d3"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-behavior-board.svg",
              "sha256": "526bf80c317e0737f9b667996cdcce516d42c92b44fb1fc04892e9e6f3969bed"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-behavior-board.png",
              "sha256": "5f7d3c2369e8982b631041bc5344c6738843207df5762711cbf2809f442bb4db"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.md",
              "sha256": "9a262197eece29db512c812cf6bc7ef9c0e2f50a73ccc2403a5cf5fa1c7c4422"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.svg",
              "sha256": "35ad54a8bc97ea7850c0e2a63ea104260187b631515114136e4ec8509418b74a"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.png",
              "sha256": "61339c76c16d537717e56b268c4fd126ccf6953730b47c35a9f12cac122b8eeb"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-active-before.png",
              "sha256": "38cf3ae111f942ebaf2657cfa25191d7d3d553e4f2a3e728eb2d2b61cd439244"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-debug-before.png",
              "sha256": "b2e4495408844508571b1abfeda38508928126eb01c8d58aee9b33c4d4852a1a"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-debug-near-6am-before.png",
              "sha256": "c19b17966aadd487b61a7a585fdf577ce9e615fe503be17d89f02a8cfaee2476"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-noon-before.png",
              "sha256": "2126f69649059c293c38597eb82954e3ed433d023620b49cb16553d499258715"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-midnight-before.png",
              "sha256": "d0e29854527d1b89c3ac4aec04eccbc2a88953d6911c10234321bde4f7f0d169"
            },
            {
              "path": ".codex/concepts/P.2.27-hour-label-size-daily-before.png",
              "sha256": "32d8c147bcab82045efce10c05d47ac2abab7265fb4e5ef452b53e646588a89c"
            }
          ]
        }
      },
      "outcome": "Installed the approved 20 dp active-catalog and 16 dp fixture/debug labels on Pixel 3 XL 8AKY0M32X. Captured and visually reviewed all planned hourly, noon, focused 6 AM, midnight-before/after, and daily states. Labels fit beneath the long ticks, remain distinct from focused cues, and no hourly labels appear in daily mode. Fixture/debug weekday changes from Saturday to Sunday across local midnight; its nearest-hour cue remains as specified. No out-of-scope accessibility or environment matrix was attempted.",
      "evidence": [
        ".codex/test-artifacts/128-p2-hour-tick-label-size/README.md",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-hourly-labels-20dp.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-noon-12pm.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-before-12am.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-after.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-daily-reference.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-hourly-labels-16dp.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-focused-6am.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-noon-12pm.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-before-12am.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-after-12am.png",
        ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-daily-reference.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-11",
    "summary": "P.2.27 is complete. The owner-selected 20 dp active-catalog and 16 dp fixture/debug hourly labels were installed on Pixel 3 XL 8AKY0M32X and visually reviewed in the required hourly, focused, noon, midnight-before/after, and daily states. Labels remain readable at their ticks, the fixture/debug cue remains distinct and bottom anchored, the weekday advances across local midnight, and daily mode shows no hourly labels. Fresh installed screenshots and observations are preserved under .codex/test-artifacts/128-p2-hour-tick-label-size/. The previously uncommitted P.2.26 changes and artifacts were preserved.",
    "verification": [
      "python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; streamed install Success; MainActivity launched.",
      "python3 scripts/dev.py workflow — passed after resume and during CLOSEOUT.",
      "git diff --check — passed during CLOSEOUT after reviewing the tracked final diff.",
      "python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/128-p2-hour-tick-label-size/<capture>.png — succeeded for all 11 final active-catalog and fixture/debug targets listed in README.md; captures were visually inspected.",
      "No automated tests or lint were run, per plan. RTL, TalkBack, accessibility-wide, reduced-motion, other-font-scale, other-device, and broad system/environment acceptance were not performed; these remain outside P.2.27."
    ],
    "evidence_directory": ".codex/test-artifacts/128-p2-hour-tick-label-size/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "128-p2-hour-tick-label-size",
  "slice_ids": [
    "P.2.27"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/P.2.27-hour-label-size-behavior-board.md",
    ".codex/concepts/P.2.27-hour-label-size-behavior-board.svg",
    ".codex/concepts/P.2.27-hour-label-size-behavior-board.png",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.md",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.svg",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.png",
    ".codex/concepts/P.2.27-hour-label-size-active-before.png",
    ".codex/concepts/P.2.27-hour-label-size-debug-before.png",
    ".codex/concepts/P.2.27-hour-label-size-debug-near-6am-before.png",
    ".codex/concepts/P.2.27-hour-label-size-noon-before.png",
    ".codex/concepts/P.2.27-hour-label-size-midnight-before.png",
    ".codex/concepts/P.2.27-hour-label-size-daily-before.png"
  ],
  "completed_gates": [
    "P.2.27_controlled_scrub_calibration_repeated",
    "P.2.27_ui_artwork_board_approved"
  ],
  "decisions": [
    "The product owner selected calibration option 1. The repeat produced a small same-direction selected-time change and did not reproduce the earlier multi-hour result; continue the approved visual acceptance while preserving the unexplained first observation in evidence.",
    "The product owner clarified that the active-catalog marks between 12 AM and 3 denote 30 minutes each. The latest 12:13 AM frame places the playhead just before the first half-hour mark; the earlier claim of an alignment mismatch from hourly spacing was incorrect and is withdrawn.",
    "The product owner selected option 1 for label sizing: final targets are 20 dp active-catalog and 16 dp fixture/debug. The updated UI artwork board package was reviewed against the unchanged acceptance constraints and its approval fingerprints refreshed on 2026-11-11.",
    "On 2026-11-11, the product owner selected option 1 for the device blocker: reconnect/use the specified Pixel 3 XL 8AKY0M32X and continue the same cycle's installed review."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Completed owner-directed scrub calibration. The controlled 840-unit drag advanced 4 hours 38 minutes and the repeated 50-unit drag advanced 8 minutes; the earlier multi-hour short-swipe result did not reproduce. Recorded the controlled screenshots and continued without changing gesture code.",
    "Captured installed fixture/debug 18 dp states at 7 AM, focused 6 AM, noon, evening, before and after midnight, and daily mode; tick labels, separate focused cue, and weekday transition remained visible.",
    "Displayed the fresh active-catalog 12:13 AM screenshot on the :0 display. The product owner clarified that minor marks between 12 AM and 3 are 30-minute intervals; the earlier hourly-spacing interpretation is withdrawn.",
    "Product owner selected option 1: revised active-catalog and fixture/debug targets are 20 dp and 16 dp. Updated and refreshed approval fingerprints for the P.2.27 UI artwork board package.",
    "Built the final 20 dp / 16 dp debug app successfully. Installation did not proceed because ADB no longer recognized Pixel 3 XL 8AKY0M32X; no fresh final-size installed captures exist.",
    "Closed slice P.2.27 as DONE.",
    "P.2.27 installed acceptance completed on the connected Pixel 3 XL using the approved 20 dp / 16 dp label sizes. The fresh active-catalog and fixture/debug hourly, noon, focused, midnight, and daily captures are recorded in the evidence README and cycle evidence list.",
    "Verification: python3 scripts/dev.py run --serial 8AKY0M32X built, installed, and launched successfully; python3 scripts/dev.py workflow passed; git diff --check passed. No automated tests or lint were run, per plan.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/128-p2-hour-tick-label-size/README.md",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-scrub-calibration-baseline.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-scrub-calibration-long.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-scrub-calibration-short.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-before-12am.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-after.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-midnight-after-settled.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/manual-review-current.png",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.md",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.svg",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-hourly-labels-20dp.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-noon-12pm.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/active-catalog-daily-reference.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-hourly-labels-16dp.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-focused-6am.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-noon-12pm.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-before-12am.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-midnight-after-12am.png",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/fixture-debug-daily-reference.png",
    ".codex/history/128-p2-hour-tick-label-size.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/128-p2-hour-tick-label-size.md",
    ".codex/test-artifacts/128-p2-hour-tick-label-size/README.md",
    "docs/ROADMAP.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/UI_DEVELOPMENT_WORKFLOW.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt",
    ".codex/concepts/P.2.27-hour-label-size-behavior-board.md",
    ".codex/concepts/P.2.27-hour-label-size-ui-artwork-board.md"
  ],
  "next_action": "Inspect the complete final diff and installed evidence, confirm workflow and diff checks, then close cycle 128 with P.2.27 recorded DONE."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Approved board relationship

The product owner approved both P.2.27 board packages on 2026-11-11. After reviewing the initial installed size, the owner selected 20 dp active-catalog labels and 16 dp fixture/debug labels; the revised UI artwork package records this selection and its refreshed approval fingerprints below. The behavior board governs the existing three-hour label cadence and text, hourly selection/readout semantics, noon and midnight behavior, unchanged daily mode, and the same-band fixture/debug layout. The UI artwork board governs the final 20 dp / 16 dp sizes, fixture/debug group's approximately 5 dp upward shift inside the existing 84 dp band, and the bottom-anchored focused-time cue. The board approval dates and SHA-256 fingerprints for every package file are recorded in the cycle manifest above. Prior installed captures linked by the artwork board are context only; fresh installed captures are required to meet the exit.

### Objective, scope, invariants, and exclusions

P.2.27 changes only the rendered size of the existing hourly long-tick labels and the fixture/debug vertical layout needed to fit them. In `CatalogTimeLabScreen.kt`, set the hourly label paint size to the owner-selected 20 dp. In `ElasticForecastTimeline.kt`, set it to 16 dp and shift the fixture/debug weekday, axis, ticks, labels, and symbols upward by approximately 5 dp at full hourly zoom. Apply the vertical offset through the shared axis baseline, scaled by the existing zoom animation, so daily mode remains at its current geometry and the group moves with the scale transition. Keep the canvas at 84 dp; the focused-time cue keeps its current 13 dp size and bottom anchor.

Preserve the labels' existing text, local-hour interpretation, three-hour cadence, placement below the longest-tick lower endpoint, full-bounds clipping behavior, and attachment to ticks during scrubbing. Preserve `12 AM` and `12 PM` at local midnight and noon; leave intervening ticks blank. The active-catalog readout remains minute precise. Fixture/debug continues to display its nearest whole-hour source-sample cue while the selected fractional time remains authoritative. Keep the fixture weekday row and supported symbols in their approved hierarchy, with symbols at their existing 24 dp `:30` midpoints. Maintain 48 dp/hour spacing, selected-time projection at the fixed playhead, forecast facts, symbol mapping, gesture ownership, daily appearance, host dimensions, and overall screen height.

In scope are the two existing timeline drawing paths, focused installed review of those paths and the directly affected interactions, and the P.2.27 roadmap board-approval record already synchronized at the BOARDS checkpoint. No product specification update is needed: this slice changes a board-governed visual size/layout detail and does not change the P.2.26 label, time-selection, or gesture contract. Excluded are data/presentation changes, controls, axis or tick changes, horizontal positions, screen or canvas resizing, forecast value changes, daily redesign, the deprecated oxygenWX app, automated test additions/runs, and broad RTL, TalkBack, accessibility, reduced-motion, font-scale, device, or system/environment matrices.

### Dependency and verified implementation boundary

P.2.26 is complete and supplies the existing label rendering, selected-time semantics, and focused cue behavior. P.2.24 supplies the unchanged 48 dp/hour scale and 24 dp symbols at `:30`; P.2.25 supplies the weekday treatment. The active-catalog labels use `hourLabelPaint` below `railY`; fixture/debug labels, weekday, axis, and symbols are positioned from the shared `baseY`, while its focused-time cue is already calculated from the canvas bottom. Therefore the approved group shift can be applied to the fixture/debug shared baseline without changing its 84 dp host or cue anchor. The installed Pixel 3 XL (`8AKY0M32X`) is connected and authorized; the prior P.2.26 evidence records Android 12/API 31, physical 1440×2960 at 560 dpi, and font scale 1.0.

### Ordered implementation and review steps

1. Make only the approved rendering changes in `CatalogTimeLabScreen.kt` and `ElasticForecastTimeline.kt`: set label text sizes to 20 dp and 16 dp, respectively; keep the approved fixture/debug shared axis baseline shift of 5 dp at full zoom using the existing zoom value. Leave the focused cue's bottom-based baseline unchanged. Review the actual changed lines against the approved boards before installation.
2. Build and install the debug app on the connected Pixel 3 XL with `python3 scripts/dev.py run --serial 8AKY0M32X`. This command is authorized by the user's instruction to update the app on device. Do not add or run automated tests, lint, or unrelated checks.
3. Review the installed active-catalog timeline and the synthetic debug surface. Launch the existing debug profile picker with `adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.data.debug.DebugForecastActivity --es debug_profile RAIN_3_DAY`. Use the existing timeline gestures to enter hourly mode, scrub around 6 AM, noon, and local midnight on both surfaces, and return each surface to daily mode. Do not add controls or alter gesture ownership.
4. Capture actual installed states with `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/128-p2-hour-tick-label-size/<name>.png`. Save `README.md` and captures under `.codex/test-artifacts/128-p2-hour-tick-label-size/`. Record the device/build, Android/API, resolution/density, font scale, exact commands, each selected time, the observed cue, label fit/readability, weekday transition, and limitations. Inspect the installed captures on the device and as screenshots; board diagrams or a build alone do not satisfy acceptance.
5. Run `python scripts/dev.py workflow` and `git diff --check`; inspect the complete final diff while preserving unrelated pre-existing P.2.26 workspace changes. Record exact commands/results and any state not reached. Do not mark P.2.27 done if a required installed state or interaction is not observed.

### Focused installed acceptance and evidence

On the installed active-catalog timeline, capture hourly mode with labels `3`, `6`, and `9` at 20 dp, centered under their long ticks and plainly readable; capture local noon and midnight so `12 PM` and `12 AM` are fully visible at the corresponding ticks. Scrub through those ticks and verify that labels remain attached to their positions while the minute-precise selected-time cue follows the selection. Return to daily mode and capture that no hourly labels appear.

On the installed fixture/debug timeline, capture hourly mode with labels `3`, `6`, and `9` at 16 dp and the weekday, symbols, axis, and labels about 5 dp higher within the unchanged 84 dp band. Capture a focused 6 AM state with the tick label and bottom-anchored nearest-hour cue both visible and distinct. Capture scrubs through noon and local midnight showing `12 PM`/`12 AM` attached to their ticks, the approved nearest-whole-hour cue behavior, and the selected weekday's local-date transition. Return to daily mode and capture that no hourly labels appear and the existing daily geometry remains. On both surfaces, inspect the labels at visible viewport edges and confirm that the applicable forecast facts remain readable in the changed states.

Target installed evidence files are:

- Active-catalog hourly, noon, midnight-before/after, and daily reference: `active-catalog-hourly-labels-20dp.png`, `active-catalog-noon-12pm.png`, `active-catalog-midnight-before-12am.png`, `active-catalog-midnight-after.png`, and `active-catalog-daily-reference.png`.
- Fixture/debug hourly, focused 6 AM, noon, midnight-before/after, and daily reference: `fixture-debug-hourly-labels-16dp.png`, `fixture-debug-focused-6am.png`, `fixture-debug-noon-12pm.png`, `fixture-debug-midnight-before-12am.png`, `fixture-debug-midnight-after.png`, and `fixture-debug-daily-reference.png`.
- `README.md` records the installed conditions, exact verification commands, observations, and limits.

If installed evidence shows that an approved label cannot remain readable and fit with the focused cue inside the approved band while preserving the selected sizes and unchanged screen geometry, stop the affected implementation/acceptance step, record the concrete state and evidence in the checkpoint, and ask the product owner for direction. Do not silently shrink labels, hide required ticks, move the cue, or enlarge the band. The slice closes only when every stated installed acceptance condition is met and the evidence and final diff are recorded; otherwise record the precise incomplete outcome.
