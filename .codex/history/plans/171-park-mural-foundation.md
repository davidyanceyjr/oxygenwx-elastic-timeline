# Cycle plan — 171-park-mural-foundation

State: CLOSED
Plan maturity: FINAL
Cycle ID: `171-park-mural-foundation`
Roadmap items: OX-37, OX-42, OX-85
Objective: Establish the Park mural's declared data surface, reserved wall region, and initial portrait safe areas without binding or painting a forecast reading.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
shared approved behavior board, and UI artwork applicability before finalizing.
The owner directed that this group needs no separate UI-board approval.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "171-park-mural-foundation",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-37",
      "depends_on": [
        "OX-35",
        "OX-04"
      ],
      "status": "DONE",
      "exit": "One declared wall surface can opt into one exact-value binding without imposing data surfaces on First City/Open Landscape or implementing mural rendering.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
              "sha256": "a185d01ec67e60a313133c6546a592d5895f649d4d561dfe6dffa46f1619615e"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.svg",
              "sha256": "bf502a0a1d533952611bb75fc68f5974638323e59aa6d42989304586d9e6d23f"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.png",
              "sha256": "f80e283b9cdfb00c19a407ec9f752af0dd4d1b6851a385351706a1f455292184"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.md",
              "sha256": "f63cc1b8cf18f2af21e078a30c240588b056e5fade14e1835cd56773a99af5a4"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Declared a Park-owned optional mural data surface with normalized plane/anchor/reserved-region geometry and one opaque canonical reference; existing decorative elements remain surface-free and no forecast reading or renderer behavior was added.",
      "evidence": [
        ".codex/test-artifacts/171-park-mural-foundation/README.md"
      ]
    },
    {
      "id": "OX-42",
      "depends_on": [
        "OX-25",
        "OX-37"
      ],
      "status": "DONE",
      "exit": "Installed portrait evidence identifies the mural plane and a non-occluded reserved data region; no exact-reading or typography claim is made.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
              "sha256": "a185d01ec67e60a313133c6546a592d5895f649d4d561dfe6dffa46f1619615e"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.svg",
              "sha256": "bf502a0a1d533952611bb75fc68f5974638323e59aa6d42989304586d9e6d23f"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.png",
              "sha256": "f80e283b9cdfb00c19a407ec9f752af0dd4d1b6851a385351706a1f455292184"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.md",
              "sha256": "f63cc1b8cf18f2af21e078a30c240588b056e5fade14e1835cd56773a99af5a4"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.svg",
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": [
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.svg",
              "sha256": "34b02f5799236cb7c082fb72fd51bf0c56af9edebb04785e2f44f9d2f71146ae"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.png",
              "sha256": "a9d3d6c3ba15a0ab93d51109ec87e7d68d5df82079c14b36335433ad37335d6f"
            }
          ]
        }
      },
      "outcome": "Added the Park mural wall in the approved depth order, attached the OX-37 optional surface declaration, rendered its blank reserved panel, updated the existing focused composition inventory test, passed only that authorized test, and captured installed Pixel 3 XL portrait evidence showing the wall/panel with central facts and timeline visible.",
      "evidence": [
        ".codex/test-artifacts/171-park-mural-foundation/README.md",
        ".codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png"
      ]
    },
    {
      "id": "OX-85",
      "depends_on": [
        "OX-25",
        "OX-42",
        "OX-41"
      ],
      "status": "DONE",
      "exit": "Installed Pixel 3 XL portrait evidence records logical crop, viewport/insets and non-obstructed central weather/timeline access, with a recognizable park and reserved mural plane.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
              "sha256": "a185d01ec67e60a313133c6546a592d5895f649d4d561dfe6dffa46f1619615e"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.svg",
              "sha256": "bf502a0a1d533952611bb75fc68f5974638323e59aa6d42989304586d9e6d23f"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.png",
              "sha256": "f80e283b9cdfb00c19a407ec9f752af0dd4d1b6851a385351706a1f455292184"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-behavior-board.md",
              "sha256": "f63cc1b8cf18f2af21e078a30c240588b056e5fade14e1835cd56773a99af5a4"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.svg",
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": [
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.svg",
              "sha256": "34b02f5799236cb7c082fb72fd51bf0c56af9edebb04785e2f44f9d2f71146ae"
            },
            {
              "path": ".codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.png",
              "sha256": "a9d3d6c3ba15a0ab93d51109ec87e7d68d5df82079c14b36335433ad37335d6f"
            }
          ]
        }
      },
      "outcome": "Measured the installed Pixel 3 XL portrait viewport and system insets, recorded the full-canvas normalized Park crop and relative mural/readout/timeline bounds, moved and narrowed only the Park mural plane/reserved region to clear the precipitation pictogram and text, and captured installed evidence of a recognizable Park with the blank panel and central content unobstructed.",
      "evidence": [
        ".codex/test-artifacts/171-park-mural-foundation/README.md",
        ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png",
        ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-hierarchy.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Cycle 171 completed OX-37, OX-42, and OX-85 as separate DONE outcomes. The Park now declares one optional mural data surface with a Park-local opaque selected-value reference, renders the wall and blank reserved panel, and uses measured Pixel 3 XL portrait bounds to keep the wall clear of central facts and timeline access. The final installed capture shows a recognizable Park, blank panel, central facts, and timeline. OX-12 retains later selected-reading semantics.",
    "verification": [
      "PASS: PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py workflow at CLOSEOUT; Workflow scaffold: OK.",
      "PASS: git diff --check; exit 0 with no output.",
      "PASS during OX-42: ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest; BUILD SUCCESSFUL, 28 actionable tasks, 0 failures. Not rerun during OX-85.",
      "PASS after final OX-85 geometry change: PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py run --serial 8AKY0M32X; debug build assembled, installed, and launched.",
      "PASS: installed Pixel 3 XL captures saved and visually reviewed at .codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png and .codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png; final capture retains UI hierarchy evidence.",
      "PASS: Pixel 3 XL portrait measurements recorded from adb wm size, wm density, font_scale, dumpsys window displays, dumpsys activity top, and uiautomator hierarchy; screenshot 1440x2960 at 560 dpi, app viewport 1440x2733 px.",
      "PASS: SHA-256 values for the approved behavior package and contextual UI artwork match cycle 171 plan fingerprints; approved board files remain unchanged.",
      "NOT RUN: full unit suite, lint, OX-85 tests, RTL, TalkBack, gesture sweep, and broad system/device acceptance."
    ],
    "evidence_directory": ".codex/test-artifacts/171-park-mural-foundation/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "171-park-mural-foundation",
  "slice_ids": [
    "OX-37",
    "OX-42",
    "OX-85"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.svg",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.png",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.md"
  ],
  "completed_gates": [
    "final_plan_reviewed",
    "all_external_dependencies_verified_done",
    "required_board_approvals_and_owner_ui_waiver_recorded",
    "all_slice_boards_approved"
  ],
  "decisions": [
    "UI artwork is supplied for context; owner explicitly waived separate UI-board approval.",
    "Product owner approved the shared behavior package for OX-37, OX-42, and OX-85 on 2026-11-22; its files and fingerprints remain unchanged.",
    "Owner explicitly selected the recommended OX-42 resolution: update the existing Park composition test and renderer checks, then run only LivingUrbanParkCompositionTest.",
    "Owner explicitly selected the recommended OX-37 resolution: use a Park-local opaque typed reference; OX-12 retains measurement and selected-reading semantics.",
    "No broader test suite, lint, or app-wide acceptance is authorized by this cycle.",
    "For OX-85, installed Pixel 3 XL bounds showed the mural plane extended behind the precipitation pictogram; moved and narrowed only the Park mural plane/reserved region to protect the measured readout area."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Completed and displayed the shared behavior board and temporary-object UI artwork package.",
    "Recorded identical approved behavior package fingerprints separately for OX-37, OX-42, and OX-85.",
    "Completed the cycle 171 FINAL plan and passed final workflow/diff review before activation.",
    "Resolved the OX-42 renderer/test mismatch with owner-approved focused test update and execution.",
    "Resolved the OX-37 exact-binding reference shape as Park-local opaque metadata; no source edits occurred before resolution.",
    "Board review and owner decisions recorded under .codex/test-artifacts/171-park-mural-foundation/board-review.md.",
    "Closed slice OX-37 as DONE.",
    "Closed slice OX-42 with the single authorized focused test passing and installed Pixel 3 XL evidence of the wall and blank reserved panel.",
    "OX-42 screenshot shows central facts and the timeline unobstructed around the left mural wall.",
    "OX-85 initial portrait crop and system insets are measured from the installed Pixel 3 XL; scene uses the full canvas with no extra crop transform.",
    "OX-85 mural plane was narrowed and raised based on installed evidence; final Pixel 3 XL capture shows a blank reserved panel and unobstructed central facts/timeline.",
    "Final OX-85 debug build installed successfully on Pixel 3 XL; no OX-85 tests were run.",
    "Closed slice OX-85 as DONE.",
    "Closed OX-37 with the Park-local opaque exact-binding reference contract; no forecast-reading or renderer behavior was added.",
    "Closed OX-42 with the approved wall pass and blank reserved panel; the one authorized focused composition test passed and the installed portrait evidence was reviewed.",
    "Closed OX-85 with measured Pixel 3 XL viewport/insets, the full-canvas normalized crop record, evidence-based mural bounds adjustment, final installed screenshot, and UI hierarchy evidence.",
    "The final installed capture shows a recognizable Park, clear blank mural panel, central facts, and timeline; First City/Open Landscape and global overlays were unchanged.",
    "Cycle-scoped verification: focused LivingUrbanParkCompositionTest PASS; final debug build/install PASS; Pixel 3 XL screenshots PASS; workflow and git diff --check PASS; no other tests, lint, RTL, TalkBack, gesture sweep, or broad app acceptance run.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/171-park-mural-foundation/board-review.md",
    ".codex/test-artifacts/171-park-mural-foundation/README.md",
    ".codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png",
    ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png",
    ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-hierarchy.xml",
    ".codex/history/171-park-mural-foundation.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/171-park-mural-foundation.md",
    "docs/ROADMAP.md",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.dot",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.svg",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.png",
    ".codex/concepts/OX-171-park-mural-foundation-behavior-board.md",
    ".codex/test-artifacts/171-park-mural-foundation/board-review.md",
    ".codex/test-artifacts/171-park-mural-foundation/README.md",
    ".codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png",
    ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png",
    ".codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-hierarchy.xml",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkCompositionTest.kt",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect the complete final diff and diff-check, verify approved board fingerprints remain unchanged, ensure the archive captures each slice outcome and exact verification/limits/evidence, run final workflow validation, and close cycle 171 into history."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Intake and dependency order

Cycle 171 groups three related steps in one Park mural foundation outcome:

1. **OX-37 — Declare optional exact-data surfaces.** External dependencies OX-35 and OX-04 are DONE. Preserve its own exit: the wall can opt into one exact-value binding without imposing data surfaces on other worlds or implementing mural rendering.
2. **OX-42 — Place the park mural wall and data-surface anchor.** OX-25 is DONE and OX-37 is an earlier in-cycle dependency. Preserve its own exit: installed portrait evidence identifies a non-occluded mural plane and reserved region; no reading or typography claim.
3. **OX-85 — Establish the initial portrait camera and safe areas.** OX-25 and OX-41 are DONE and OX-42 is an earlier in-cycle dependency. Preserve its own installed Pixel 3 XL portrait exit, including logical crop, viewport/insets, central weather/timeline access, recognizable park, and reserved mural plane.

### Approved shared board package

- Behavior board: `.codex/concepts/OX-171-park-mural-foundation-behavior-board.dot` with SVG/PNG renders and a concise Markdown contract. It presents separate lanes for the optional surface contract, placed mural plane, and portrait camera/safe-area outcome. The product owner approved the complete package on 2026-11-22; the same file list, approval details, and fingerprints are recorded separately on OX-37, OX-42, and OX-85.
- UI artwork: `.codex/concepts/OX-171-park-mural-foundation-ui-artwork-board.svg` with PNG render. It will use temporary, neutral objects for the mural wall and scene scaffold and mark the reserved region and portrait safe areas. Per the owner's direct direction, this artwork is provided for context and does not require separate UI-board approval. It is not installed visual acceptance evidence.
- The approved behavior package remains unchanged. Its Markdown companion retains the submitted proposal-status wording; the cycle manifest and roadmap record the approval.

## Implementation order, compatibility, and observable exits

Keep the Park-owned presentation declaration, composition, and renderer as the implementation boundary. `LivingUrbanParkComposition` already owns Park element identities, normalized `ParkPoint` geometry, and pass ordering; `CatalogLivingUrbanParkScene` renders that composition. Do not generalize the Park surface contract into First City/Open Landscape declarations, provider DTOs, a saved world format, or pack manifests.

1. **OX-37 — Optional Park data-surface contract.** Extend the Park-owned declaration with the board's optional surface identity, plane/anchor, reserved region, and one canonical exact-binding reference. By owner choice, represent the reference as a Park-local opaque typed identity for the one selected-reading slot; do not encode a weather parameter, value, unit, or selected instant. OX-12 owns mapping that identity to its typed exact selected-time reading. Leave the existing backdrop, path, and entrance without a surface. OX-37 does not resolve a selected instant, read forecast data, convert units, render a value, or implement OX-12 semantics. Preserve every non-Park declaration and route. Verify the source contract and compatibility boundaries, then assert the optional Park metadata and absence on decorative elements in the single focused Park composition test authorized below.
2. **OX-42 — Wall plane and blank reserved region.** Add the left mural wall and attach the OX-37 surface declaration to it. Extend Park geometry/pass handling and `CatalogLivingUrbanParkScene` so the wall is rendered in the approved scene order. Update `LivingUrbanParkCompositionTest` from the old three-element expectation to the approved wall-plus-scaffold composition and assert its declared surface and order. The wall region contains no weather value, unit, source time, unsupported state, or finished typography. Capture an installed portrait view showing the plane and clear reserved region while the central readout and timeline remain visible.
3. **OX-85 — Measured portrait crop and safe areas.** Refine only the Park scene's logical composition/crop against the installed Pixel 3 XL viewport and system insets. Derive positions from that installed viewport; do not copy numeric proportions from the temporary mockup as fixed device values. Preserve the existing central facts and timeline overlays and keep First City/Open Landscape rendering unchanged. Record the device's screenshot dimensions, logical viewport, insets, Park crop, and the relative bounds of the mural/readout/timeline in the installed evidence. Capture a recognizable Park with the blank reserved mural region and unobstructed central content.

### Shared invariants and exclusions

- The selected instant remains authoritative and must not change from viewport or camera adjustments. Keep supplied, carried, and unavailable evidence distinctions; do not infer weather or draw forecast values in these slices.
- The wall remains blank until OX-12/OX-12A. No vegetation/bench (OX-43), shelter (OX-44), final mural materials/typography, skyline redesign, weather renderer extraction, broad system/accessibility acceptance, or pack storage/schema change enters this cycle.
- If a storage/schema change, non-Park declaration change, or global forecast/timeline overlay move becomes necessary, stop at that boundary and ask the owner before broadening the plan.
- Each slice closes only on its own manifest exit and evidence. OX-42 and OX-85 installed captures do not substitute for one another: OX-42 proves the wall plane and blank region; OX-85 separately records measured crop/insets and protected content.

### Verification authorization and evidence targets

- The owner-authorized test scope is limited to `LivingUrbanParkCompositionTest`. Update its existing assertions for the optional surface and wall inventory, then run exactly `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest`. Do not run the full unit-test suite, lint, or unrelated checks.
- OX-37 also receives source-level contract review confirming non-Park declarations stay optional/inapplicable and no reading/conversion path is introduced. OX-42 and OX-85 require installed Pixel 3 XL portrait captures and documented scoped visual inspection; a board or preview is not acceptance evidence. Keep interaction checks limited to the Park route, central readout, and timeline affected by these changes.
- Cycle checks include `python scripts/dev.py workflow` and `git diff --check`. Record exact commands/results and limits under `.codex/test-artifacts/171-park-mural-foundation/`; keep each slice's acceptance and screenshots separately identifiable in `README.md`. Closeout target: `.codex/history/171-park-mural-foundation.md` and archived plan.

### Risks and review handoff

The current renderer enforces the three existing depth passes, and `LivingUrbanParkCompositionTest` asserts the three-element inventory. OX-42 must update both consistently; verification is intentionally limited to that test class. Portrait crop and safe-area values must come from the installed Pixel 3 XL capture, not the artwork mockup. No selected-time reading is included, and the approved behavior package must remain byte-for-byte unchanged.

### Scope boundaries and invariants

- No exact selected-time reading contract or mural painting (OX-12/OX-12A), final mural typography/materials, vegetation/bench (OX-43), shelter (OX-44), skyline, weather renderer, pack switching, or distribution work.
- No forecast conversion or meteorological reinterpretation inside drawing. Preserve exact selected-time authority, supplied/carried/unavailable evidence semantics, honest source time, and readable central forecast/timeline access.
- The board does not itself activate a plan. Execution remains gated on cycle 171 reaching REVIEW with a FINAL plan and explicit activation; until then the current pointer stays PLANNED.

### Verification and evidence

- Plan-review checks: `python scripts/dev.py workflow` and `git diff --check`. The sole authorized focused test and the installed Pixel 3 XL acceptance are defined above for execution; no full suite, lint, or broad system acceptance is included.
- Execution evidence targets are `.codex/test-artifacts/171-park-mural-foundation/` and `.codex/history/171-park-mural-foundation.md`; these are future targets, not existing acceptance evidence.
