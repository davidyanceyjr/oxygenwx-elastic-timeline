# Cycle plan — 124-p2-hourly-forecast-glance

State: CLOSED
Plan maturity: FINAL
Cycle ID: `124-p2-hourly-forecast-glance`
Roadmap items: P.2.24
Objective: Increase the uniform hourly timeline spacing to 48 dp/hour and supported hourly event symbols to 24 dp on active-catalog and fixture/debug surfaces, making the next three hourly event positions visible and legible around the selected instant while preserving event semantics, gesture rules, and selected-time authority.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "124-p2-hourly-forecast-glance",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.24",
      "depends_on": [
        "P.2.23"
      ],
      "status": "DONE",
      "exit": "Installed active-catalog and fixture/debug hourly views show the next three hourly sample positions on-screen around representative selected times, with supported 24 dp icons legible, distinct, and aligned to their existing `:30` positions; missing or unsupported samples remain icon-free. Hourly date labels remain horizontally anchored to local midnight and clear the larger symbols. Preserve each existing host size: 148 dp for active-catalog and 84 dp for fixture/debug; the approved artwork preview illustrates the fixture/debug band's vertical layout and the shared horizontal spacing and symbol treatment. A 360 dp viewport-width scrub advances about 7.5 hours at 48 dp/hour, so traversing 24 hours takes about 3.2 such swipes, while selected time follows the centered playhead without a scale-animation jump. Daily artwork, date-boundary alignment, forecast facts, and gesture ownership remain intact. Capture both hourly views and record focused interaction outcomes and limitations.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-09",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.md",
              "sha256": "a7113802d55543176c61911a5d089d1a85d4673022d4d2a22aa76dffcd1c120c"
            },
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.svg",
              "sha256": "c8966b08f6a76d1b7093c527c6665053db54c1a97f5a52fc24e5eea3c4d46193"
            },
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.png",
              "sha256": "53f1f9ae84e2fc450c5d29e1bda451f65c70720a5bb889ea25473c8f14dfde8d"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-09",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.md",
              "sha256": "487ac622710847f194e370f345c828848b24063a3e76655d8e0bab4d287d8883"
            },
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.svg",
              "sha256": "4eb072d9f7aa8899f7ad9d6659ccdec6fde14393bbcc00df48a3ea4ebb2fe4d7"
            },
            {
              "path": ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.png",
              "sha256": "8851a88425452c2bd374f8e1e332396958b5752352854c40bb46813e2c1db2e1"
            }
          ]
        }
      },
      "outcome": "Installed active-catalog and fixture/debug acceptance completed for the approved 48 dp/hour and 24 dp symbol treatment; selected-time continuity, local-midnight label clearance, daily return, and daily artwork were retained. The optional SG.4 picker-label/event-identity mismatch is documented as out of scope per the product owner’s option 1 decision.",
      "evidence": [
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/README.md",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-hourly.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly-midnight.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-hourly-midnight.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-daily-return.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-daily-return.png",
        ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly-event-gap.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-09",
    "summary": "Completed P.2.24 on the active-catalog and fixture/debug surfaces: hourly spacing is 48 dp/hour, supported symbols are 24 dp at existing :30 positions, and hourly labels clear the symbols within the existing host sizes. Installed evidence confirms focused scrubbing, selected-time continuity, midnight alignment, and daily return. The optional SG.4 picker-label/event-identity mismatch remains unchanged and is documented out of scope per the product owner’s option 1 decision.",
    "verification": [
      "python scripts/dev.py workflow — passed before activation, after activation/resume, and at CLOSEOUT.",
      "python scripts/dev.py build — passed; BUILD SUCCESSFUL in 41s.",
      "python scripts/dev.py run — passed; installed and launched the debug app on Pixel 3 XL.",
      "python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/124-p2-hourly-forecast-glance/<capture>.png screenshot — captured seven retained installed states; exact invocations and states are listed in the evidence README.",
      "Focused installed interactions — active-catalog scrub advanced 7 h 17 min 54 s over about 360 dp; fixture/debug scrubs advanced roughly 7–8 hours per about 360 dp; selection continuity, local-midnight labels, and daily return were observed.",
      "git diff --check — passed.",
      "Automated tests and lint were not run; they were not authorized for this slice."
    ],
    "evidence_directory": ".codex/test-artifacts/124-p2-hourly-forecast-glance/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "124-p2-hourly-forecast-glance",
  "slice_ids": [
    "P.2.24"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.md",
    ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.svg",
    ".codex/concepts/P.2.24-hourly-forecast-glance-behavior-board.png",
    ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.md",
    ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.svg",
    ".codex/concepts/P.2.24-hourly-forecast-glance-ui-artwork-board.png"
  ],
  "completed_gates": [
    "P.2.24_behavior_board_approved",
    "P.2.24_ui_artwork_board_approved",
    "Both objective-specific behavior and UI artwork board packages are approved for P.2.24.",
    "P.2.24 behavior and UI artwork board packages approved by product owner on 2026-11-09; package fingerprints remain recorded in the plan manifest.",
    "P.2.24 plan reviewed and finalized; dependency P.2.23 is DONE.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "P.2.24 installed acceptance exit satisfied; active-catalog and fixture/debug evidence and focused interactions recorded."
  ],
  "decisions": [
    "P.2.24 follows completed P.2.23; unrelated MR.3 remains PLANNED.",
    "The approved target is 48 dp/hour with 24 dp hourly symbols; a 360 dp viewport covers 7.5 hours and fits the three supported future :30 event positions from an exact-hour selection.",
    "The product owner approved both revised board packages on 2026-11-09 after requesting a simplified timeline-only review image.",
    "Preserve the existing 148 dp active-catalog and 84 dp fixture/debug hosts. The artwork board controls shared horizontal spacing and symbol treatment; its 84 dp vertical example applies to fixture/debug, with host-specific hourly label placement on active-catalog.",
    "Plan clarification preserves each existing canvas height: 148 dp active-catalog and 84 dp fixture/debug; artwork vertical example is fixture/debug-specific.",
    "Product owner selected option 1 on 2026-11-09: keep P.2.24 bounded, leave SG.4 fixture/event mapping unchanged, and record the optional SG.4 missing 10:30 symbol as an out-of-scope limitation."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the approved 48 dp/hour and 24 dp symbol treatment on the active-catalog and fixture/debug timelines; updated the two controlling specification passages and P.2.24 roadmap status.",
    "python scripts/dev.py workflow passed before activation and after activation; activation completed for P.2.24.",
    "python scripts/dev.py build succeeded (BUILD SUCCESSFUL in 41s); python scripts/dev.py run installed and launched the debug application on Pixel 3 XL.",
    "Captured required active-catalog and fixture/debug hourly states, local-midnight states, and daily-return states; completed focused entry, scrub, selection-continuity, and return interactions.",
    "Installed SG.4 optional capture exposed a mismatch between the picker label and existing fixture event identity; the owner chose to record this as out of scope and leave the fixture unchanged.",
    "Closed slice P.2.24 as DONE.",
    "P.2.24 finished as DONE after installed acceptance on both timeline surfaces; seven retained screenshots and the evidence README record the results and limits.",
    "Option 1 is recorded: leave SG.4 fixture and event mapping unchanged; document the optional 10:30 mismatch as out of scope.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-hourly.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly-midnight.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-hourly-midnight.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-daily-return.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/fixture-debug-daily-return.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/active-catalog-hourly-event-gap.png",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/README.md",
    ".codex/history/124-p2-hourly-forecast-glance.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/124-p2-hourly-forecast-glance.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/124-p2-hourly-forecast-glance/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "git status --short and scoped final diff"
  ],
  "next_action": "Confirm the closeout record, exact verification results, and final scoped diff; archive cycle 124 and validate the IDLE workflow state."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Bounded scope

Implement the approved hourly glance treatment on both existing timeline surfaces:

- Change the hourly spacing target from 16 dp/hour to 48 dp/hour in `ElasticForecastTimeline` and `CatalogForecastTimeline`. Keep each surface's existing shared scale calculation for drawing and scrubbing so an hourly drag uses the same projection as the marks.
- Render supported hourly weather symbols at 24 dp in the fully hourly state, keeping their existing sample mapping and `:30` midpoint coordinates. Keep the current symbol artwork and icon-free behavior for missing or unsupported events.
- Reposition hourly weekday labels vertically to clear the larger symbols while retaining their existing local-midnight x coordinates. Preserve the 84 dp fixture/debug host and the 148 dp active-catalog host. The approved artwork preview governs the 48 dp/hour horizontal spacing, 24 dp symbol size and the 84 dp fixture/debug vertical example; active-catalog uses the same visual treatment within its existing 148 dp host. Do not move the rail, change either host height, or alter the daily layout.
- Update the hourly-scale statements in `docs/SPECIFICATION.md` and §4.3 of `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` to describe the 48 dp/hour scale and record P.2.24 as the bounded decision. Update the P.2.24 roadmap board status to reflect the recorded approval. Preserve all other product rules.

### Exclusions

No event-data or condition-to-symbol mapping changes; no changes to source timestamps, missingness, projection algorithms, local-time/DST behavior, forecast facts, daily icons or spacing, visible controls, host dimensions, accessibility model, gesture arbitration, or the P.2.17/P.2.22 scale-entry/return contract. Do not modify the deprecated oxygenWX app or debug fixture definitions. No RTL, TalkBack, reduced-motion, full accessibility, broad device/environment, or test-suite work; these belong to separate system acceptance. Do not add or run automated tests without user authorization.

### Functional and visual invariants

- The selected instant remains authoritative and centered under the fixed playhead. Scale animation changes projection only and cannot retarget selected time.
- Use the same current projection for timeline rendering and pointer scrubbing; the hourly target is 48 dp/hour and daily compact spacing is unchanged.
- Keep every supported sample's existing `:30` display midpoint and existing event identity. Missing and unsupported samples remain icon-free; no carry-forward or inference is introduced.
- Keep weekday anchors at local midnight, selective hourly ticks and labels, the divider and selected-time cue. Daily artwork stays at its current positions and sizes.
- With a 360 dp viewport and exact-hour selection, supported future symbols at +1.5, +2.5 and +3.5 hours center at +72, +120 and +168 dp; the third 24 dp symbol ends at the viewport edge. A wider device viewport provides additional margin. This is a spacing/legibility target, not a promise that three symbols exist when source events are missing or unsupported.
- Preserve gesture ownership, scale controls, selection entry/return semantics, responsiveness and readable forecast facts.

### Implementation sequence

1. Update both hourly scale constants and both hourly symbol diameters. Adjust only the precision-mode weekday label baselines/tick presentation needed for clear separation in each existing host; use installed rendering to settle exact vertical coordinates without changing host height or horizontal anchors.
2. Update the two controlling specification passages and the roadmap approval record. Keep the P.2.24 scope, exit and exclusions aligned with this plan.
3. Build/install the debug application and capture active-catalog and fixture/debug hourly states with a representative exact-hour selection and supported symbols in the next three positions. Also capture the focused neighboring/missing sample state if the installed catalog route exposes one without changing fixture data.
4. Inspect the installed screenshots and perform focused interactions: enter hourly, observe selected-time continuity through scale animation, scrub a known distance and confirm the 48 dp/hour response, verify local-midnight labels and `:30` event alignment, then return to daily and confirm the exact selection and unchanged daily artwork. Record observations and any limitations; do not claim broader system acceptance.

### Verification and evidence authorization

Authorized, slice-scoped verification:

- `python scripts/dev.py workflow` before execution and after final changes.
- `python scripts/dev.py build` and `python scripts/dev.py run` to build and install/run the debug application for the required installed acceptance.
- `python scripts/dev.py screenshot` as supported by the repository workflow, plus direct installed captures as needed for the two approved surfaces.
- Focused manual inspection/interactions limited to the P.2.24 states and invariants above; `git diff --check` and final diff review.

No test, lint, RTL, TalkBack, or broad system matrix is authorized by this plan. Preserve actual installed screenshots and an evidence README under `.codex/test-artifacts/124-p2-hourly-forecast-glance/`. The README must identify device/profile, selected time and scale, capture filenames, focused interaction outcomes, exact verification commands, and limitations. Required captures are active-catalog hourly and fixture/debug hourly; include any additional capture only when it directly substantiates an exit condition.

### Risks and limits

- Enlarged symbols may overlap weekday text or nearby labels; judge the actual installed states and adjust only vertical label placement/precision-mode drawing within the fixed hosts.
- The third future icon in the 360 dp illustration is tangent to the right viewport edge at an exact-hour selection. Verify the target with the installed device width and record the viewport; do not silently change the approved spacing target.
- The preview is not acceptance evidence. If a required state cannot be reached through existing debug routes, record the limitation instead of changing fixture data or expanding scope.

### Plan approval record

Product owner approved the objective-specific behavior and UI artwork board packages on 2026-11-09. Their paths, approvals and package fingerprints are recorded in the cycle manifest above. The behavior board governs scale, mapping, selected-time and gesture invariants. The UI artwork board governs the simplified timeline-only visual direction and shared spacing/icon treatment; this plan scopes its 84 dp vertical example to the fixture/debug host while preserving active-catalog's current 148 dp host. The installed evidence remains mandatory and is not replaced by board approval.
