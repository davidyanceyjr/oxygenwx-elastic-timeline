# Cycle plan — 129-p2-catalog-hour-label-size

State: CLOSED
Plan maturity: FINAL
Cycle ID: `129-p2-catalog-hour-label-size`
Roadmap items: P.2.28
Objective: Reduce active-catalog hourly long-tick labels from 20 dp to 18 dp while keeping fixture/debug at 16 dp.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "129-p2-catalog-hour-label-size",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.28",
      "depends_on": [
        "P.2.27"
      ],
      "status": "DONE",
      "exit": "The installed active-catalog hourly timeline shows 3, 6, and 9 at 18 dp, centered below their existing longest ticks and readable in the Pixel 3 XL viewport. 12 AM and 12 PM remain fully visible at their ticks. Focused scrubbing confirms that the labels remain attached to their ticks while the minute-precise readout follows the selected time. Daily mode has no hourly labels. Fixture/debug remains at 16 dp with its existing same-band layout. Capture actual installed active-catalog hourly, noon, midnight, and daily states, plus a fixture/debug confirmation; record selected times, direct observations, and limitations.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.md",
              "sha256": "ff6b4eed97dd3b2f500b114c62c8e9fea75cb7aefda43d8d01b5acd2169ca76a"
            },
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.svg",
              "sha256": "e52bb3324dd4d3fdb5095050e339fa85294ed4497c9ac162ba7927a17f62870e"
            },
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.png",
              "sha256": "71e84190ef54c19511d7446adbddf13dcb47f39bf24ecfa3aaa4df45da9ceb3e"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.md",
              "sha256": "d94db7bfa7ed67ba5b167af21e35e6e3a9b32613b3c542e3997abd1e5ceaa53d"
            },
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.svg",
              "sha256": "77ba539fc5346e0e7650d6a810000c30808d012c9df0c6fcacf34ea3ba2f3964"
            },
            {
              "path": ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.png",
              "sha256": "40d45beaf29477baea6cf5d16b41e140773df5a20d6c62e74527d1daaf66f587"
            },
            {
              "path": ".codex/concepts/P.2.28-active-catalog-20dp-context.png",
              "sha256": "389c58df9119246c6c7e466030389cffea3f3fd08f0ac1ee5f6af3d3c50a95a2"
            }
          ]
        }
      },
      "outcome": "Installed active-catalog labels at 18 dp on Pixel 3 XL 8AKY0M32X. Verified readable 3/6/9 labels below their ticks, fully visible 12 PM and 12 AM labels, minute-precise scrubbing through the Wednesday-to-Thursday midnight boundary, and an unchanged daily view. Confirmed the fixture/debug screen still shows 16 dp labels and its accepted layout. Captured and reviewed all planned states; left the device on the active-catalog 7 AM hourly view.",
      "evidence": [
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/README.md",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-hourly-labels-18dp.png",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-noon-12pm-18dp.png",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-midnight-before-12am-18dp.png",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-midnight-after-12am-18dp.png",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-daily-reference-18dp.png",
        ".codex/test-artifacts/129-p2-catalog-hour-label-size/fixture-debug-hourly-labels-16dp.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-11",
    "summary": "P.2.28 completed: active-catalog hourly labels set to 18 dp, fixture/debug remains 16 dp; installed acceptance captured and reviewed on Pixel 3 XL 8AKY0M32X, with device left on the active-catalog 7 AM hourly view. Existing P.2.26/P.2.27 workspace changes and artifacts were preserved.",
    "verification": [
      "python3 scripts/dev.py workflow — passed at CLOSEOUT (Workflow scaffold: OK; cycle 129, P.2.28 DONE).",
      "git diff --check — passed with no whitespace errors.",
      "python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; streamed install Success; MainActivity launched.",
      "python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/129-p2-catalog-hour-label-size/<capture>.png — captured and reviewed the six installed states listed in the evidence README.",
      "No automated tests or lint were run; excluded by the approved P.2.28 plan."
    ],
    "evidence_directory": ".codex/test-artifacts/129-p2-catalog-hour-label-size/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "129-p2-catalog-hour-label-size",
  "slice_ids": [
    "P.2.28"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.md",
    ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.svg",
    ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.png",
    ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.md",
    ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.svg",
    ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.png",
    ".codex/concepts/P.2.28-active-catalog-20dp-context.png"
  ],
  "completed_gates": [
    "P.2.28_slice_intake",
    "P.2.28_behavior_board_approved",
    "P.2.28_ui_artwork_board_approved",
    "P.2.28_final_plan_review",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "P.2.28_installed_acceptance_complete"
  ],
  "decisions": [
    "On 2026-11-11, the product owner directed active-catalog labels to 18 dp and retained fixture/debug labels at 16 dp.",
    "P.2.28 behavior board approval refreshed on 2026-11-11.",
    "P.2.28 ui_artwork board approval refreshed on 2026-11-11.",
    "The owner direct instruction to set active-catalog labels to 18 dp is treated as approval of both boards, whose scope exactly reflects 18 dp catalog and retained 16 dp fixture/debug.",
    "No remaining ambiguity: 18 dp is the owner-selected active-catalog target; fixture/debug stays 16 dp."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Selected P.2.28 as the sole follow-up NEXT slice after completed P.2.27; verified P.2.27 as its completed dependency and scoped this work to active-catalog label size.",
    "Recorded behavior board approval for P.2.28.",
    "Recorded ui_artwork board approval for P.2.28.",
    "Prepared objective-specific behavior and UI artwork boards for P.2.28, rendered and displayed both at DISPLAY=:0, and recorded owner-directed approval with content fingerprints.",
    "Reviewed the final P.2.28 plan against the approved behavior/UI boards, P.2.27 dependency and implementation boundary, controlling timeline/visual authorities, user-authorized device build, installed state matrix, evidence targets, and explicit no-tests/no-lint limit. Plan is bounded and ready for activation.",
    "Activated P.2.28 for execution.",
    "Closed slice P.2.28 as DONE.",
    "Installed acceptance is complete for P.2.28: the catalog 18 dp states and unchanged debug 16 dp confirmation were captured and reviewed; the Pixel remains on active-catalog hourly at 7 AM.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.png",
    ".codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/README.md",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-hourly-labels-18dp.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-noon-12pm-18dp.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-midnight-before-12am-18dp.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-midnight-after-12am-18dp.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/active-catalog-daily-reference-18dp.png",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/fixture-debug-hourly-labels-16dp.png",
    ".codex/history/129-p2-catalog-hour-label-size.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/129-p2-catalog-hour-label-size.md",
    ".codex/test-artifacts/129-p2-catalog-hour-label-size/README.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt"
  ],
  "next_action": "Review the complete final diff, verify the evidence/plan/roadmap closeout records, rerun workflow and diff checks, then archive cycle 129."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Approved board relationship

The product owner directed the exact target on 2026-11-11: active-catalog hourly tick labels at 18 dp, with fixture/debug retained at 16 dp. The owner-approved behavior board `.codex/concepts/P.2.28-catalog-hour-label-size-behavior-board.md` and UI artwork board `.codex/concepts/P.2.28-catalog-hour-label-size-ui-artwork-board.md` govern this slice. Their approval package paths, dates, and SHA-256 fingerprints are recorded in the cycle manifest above. The behavior board preserves the existing daily/hourly transitions, label attachment, selected-time readout, noon/midnight text, and debug surface. The artwork board makes the 20 dp to 18 dp active-catalog change visible and fixes the debug 16 dp appearance as unchanged. The prior 20 dp installed capture is context only; fresh device evidence is required.

### Objective, scope, invariants, and exclusions

Change only the active-catalog hourly `hourLabelPaint.textSize` in `CatalogTimeLabScreen.kt` from 20 dp to 18 dp. Do not change `ElasticForecastTimeline.kt`; its fixture/debug hourly labels remain at 16 dp with the P.2.27 same-band layout. Preserve the active-catalog labels' text, existing three-local-hour cadence, placement under longest ticks, color/weight, fit behavior, and attachment to their ticks. Keep `12 AM` and `12 PM` explicit. Preserve the minute-precise selected-time readout, fixed playhead, selection authority, noon/midnight continuity, daily appearance without hourly labels, forecast facts, 48 dp/hour spacing, tick and symbol positions, gesture ownership, and screen geometry.

The directly affected surface is the installed active-catalog timeline. A fixture/debug hourly capture confirms the accepted 16 dp surface remains unchanged after the shared app install. Excluded are fixture/debug code/layout changes, data or projection changes, tick geometry, label content/cadence/position changes, focused-time changes, daily redesign, other UI elements, automated test additions/runs, lint, and broad RTL, TalkBack, font-scale, device, or system/environment matrices. No product specification update is needed because this is a bounded visual-size adjustment with no semantic or interaction change; the roadmap and cycle record capture the decision and acceptance.

### Dependency and implementation boundary

P.2.26 introduced the existing catalog and fixture/debug hourly labels; P.2.27 selected and installed the 20 dp catalog and 16 dp debug sizes and completed the fixture/debug same-band adjustment. Both dependencies are DONE. Current source shows the active-catalog label paint at 20 dp in `CatalogTimeLabScreen.kt` and an independent 16 dp paint in `ElasticForecastTimeline.kt`. The implementation is therefore one scoped active-catalog size change. The connected Pixel 3 XL `8AKY0M32X` was previously verified as Android 12/API 31, 1440 × 2960 at 560 dpi, system font scale 1.0; confirm connection and these display settings immediately before installed review.

### Ordered implementation and verification

1. Change only the active-catalog label paint size from `20.dp.toPx()` to `18.dp.toPx()` in `CatalogTimeLabScreen.kt`. Review the actual changed lines against the approved boards. Leave fixture/debug source and the rest of the timeline untouched.
2. Build, install, and launch the updated app on Pixel 3 XL `8AKY0M32X` with `python3 scripts/dev.py run --serial 8AKY0M32X`. This is authorized by the owner's instruction to change and update the app on device. If the specified device is disconnected, do not substitute another device; record the exact blocker and await the owner's decision.
3. On the active-catalog screen, enter hourly mode with the existing double-tap gesture. Capture the 7:00 AM hourly view showing `3`, `6`, and `9`; scrub through noon and local midnight; inspect that labels remain attached to their ticks and the minute-specific selected-time readout follows the selected instant. Capture noon (`12 PM`), midnight before and after (`12 AM`), then return to daily and confirm that hourly labels are absent. Use the installed timeline only; add no controls or change gesture ownership.
4. Open the existing `RAIN_3_DAY` debug screen, enter hourly mode, and capture a confirmation that debug labels remain at 16 dp with the same-band layout and focused cue unchanged.
5. Capture only actual installed states using `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/129-p2-catalog-hour-label-size/<name>.png`. Save screenshots and a README under `.codex/test-artifacts/129-p2-catalog-hour-label-size/`. Record device/build, Android/API, resolution/density, font scale, selected times, gestures, exact commands, observations, and limitations. Inspect screenshots before claiming visual acceptance.
6. Run `python3 scripts/dev.py workflow` and `git diff --check`, then inspect the complete final diff while preserving unrelated pre-existing P.2.26/P.2.27 workspace changes and artifacts. Do not run automated tests or lint; they are not authorized by this plan.

### Focused installed acceptance and evidence

Acceptance is limited to the installed Pixel 3 XL reference viewport and these states:

- Active-catalog hourly at 7:00 AM: `3`, `6`, and `9` render at 18 dp, are readable, sit centered below their longest ticks, and remain separate from the minute-precise selected-time readout and forecast facts.
- Noon: `12 PM` is fully visible under its existing tick while the selected-time readout remains minute precise.
- Midnight before and after: `12 AM` is fully visible under its existing tick on both sides of the local-date boundary; selected time continues to follow the playhead.
- Scrub: the visible long-tick labels stay centered on and move with their ticks, while the minute-precise readout follows the selection.
- Daily reference: no hourly labels appear and existing daily geometry remains.
- Fixture/debug confirmation: existing 16 dp labels, same-band layout, and focused-time cue remain unchanged.

Target evidence files are `active-catalog-hourly-labels-18dp.png`, `active-catalog-noon-12pm-18dp.png`, `active-catalog-midnight-before-12am-18dp.png`, `active-catalog-midnight-after-12am-18dp.png`, `active-catalog-daily-reference-18dp.png`, `fixture-debug-hourly-labels-16dp.png`, and `README.md` under `.codex/test-artifacts/129-p2-catalog-hour-label-size/`. The slice closes only after each listed state is captured and reviewed, workflow and diff checks are recorded, and the final diff confirms the active-catalog size is the sole app-code change in this slice. If an expected state is unreachable or an 18 dp label clips or collides in the installed target viewport, record the state and evidence and leave the slice incomplete; do not silently alter the approved size or widen scope.
