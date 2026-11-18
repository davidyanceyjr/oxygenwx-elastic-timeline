# Cycle plan — 152-ox24-scene-asset-swap

State: CLOSED
Plan maturity: FINAL
Cycle ID: `152-ox24-scene-asset-swap`
Roadmap items: OX-24
Objective: Swap one First City scene asset through definition data while preserving element identity, depth, position, and weather interpretation.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "152-ox24-scene-asset-swap",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-24",
      "depends_on": [
        "OX-38"
      ],
      "status": "DONE",
      "exit": "Installed before/after captures show a changed First City CANOPY asset using the same scene-local element identity, depth and position; restoring the original is an authored definition-data choice, not a renderer edit.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-24-scene-asset-swap-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-24-scene-asset-swap-behavior-board.dot",
              "sha256": "f4e7d92d5ef6df6eb6817f4dfa9330d73116942fd3e0abc3e4ad07f92d7f5030"
            },
            {
              "path": ".codex/concepts/OX-24-scene-asset-swap-behavior-board.svg",
              "sha256": "4c1139c921324155d4bc73a7de1f0500adb8be8b7f013d9c56d7cceb4e4796ff"
            },
            {
              "path": ".codex/concepts/OX-24-scene-asset-swap-behavior-board.png",
              "sha256": "4e1fb9e245b4f761426542575a0b91417e199e15f0d061ea0898da4cfeb00336"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-24-scene-asset-swap-ui-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-24-scene-asset-swap-ui-board.svg",
              "sha256": "473f56185a71676273c2b8d258bfb005cb8476ffeb753b4970bc1bf3a8077bc7"
            },
            {
              "path": ".codex/concepts/OX-24-scene-asset-swap-ui-board.png",
              "sha256": "6c3c2e1f4eef59f09b71f615cad7840a2a876b4bf5359dacecbf9aba2e3c0b1f"
            }
          ]
        }
      },
      "outcome": "First City CANOPY selects the bundled olive asset through authored definition data; fixed-instant installed captures show changed foliage with the same scene identity, pass depth, bounds, sky, city, forecast facts and timeline. Switching the declaration back to BASELINE restored the original adapter choice without a renderer edit; OLIVE is delivered.",
      "evidence": [
        ".codex/test-artifacts/152-ox24-scene-asset-swap/README.md",
        ".codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png",
        ".codex/test-artifacts/152-ox24-scene-asset-swap/fixed-olive.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-24 completed: one First City CANOPY drawable now swaps through an authored typed reference; the delivered olive asset preserves identity, pass depth, geometry, selected instant, weather facts and timeline in matched installed synthetic replay captures.",
    "verification": [
      "python scripts/dev.py run --serial 8AKY0M32X: baseline and olive builds installed successfully on Pixel 3 XL.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png and fixed-olive.png: captured same 2026-10-28T17:00:00Z synthetic replay; visual comparison met scoped exit.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest.canopyAdapterUsesDeclarationAtSelectedInstant: passed for baseline restoration and delivered olive choice; an initial temporary comparison assertion failed and was corrected before both passing runs.",
      "git diff --check: passed; python scripts/dev.py workflow: passed at CLOSEOUT.",
      "Limits: no broad device, accessibility, font, RTL or motion matrix; earlier unmatched live-catalog captures excluded from acceptance."
    ],
    "evidence_directory": ".codex/test-artifacts/152-ox24-scene-asset-swap/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "152-ox24-scene-asset-swap",
  "slice_ids": [
    "OX-24"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-24-scene-asset-swap-behavior-board.dot",
    ".codex/concepts/OX-24-scene-asset-swap-behavior-board.svg",
    ".codex/concepts/OX-24-scene-asset-swap-behavior-board.png",
    ".codex/concepts/OX-24-scene-asset-swap-ui-board.svg",
    ".codex/concepts/OX-24-scene-asset-swap-ui-board.png"
  ],
  "completed_gates": [
    "OX-38 dependency DONE and OX-24 sole NEXT.",
    "OX-24_behavior_board_approved",
    "OX-24_ui_artwork_board_approved",
    "Product-owner approval of both OX-24 board packages.",
    "OX-24_full_plan_review_passed",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner chose option 1: use a fixed synthetic debug replay and recapture both authored assets at the same selected instant; existing approved boards and acceptance remain unchanged."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Matched OX-05 deterministic installed baseline and olive captures at 2026-10-28T17:00:00Z; restoration through authored reference passed; delivered choice is OLIVE",
    "Closed slice OX-24 as DONE.",
    "OX-24 closed DONE with matched installed evidence and authored restoration check",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png",
    ".codex/test-artifacts/152-ox24-scene-asset-swap/fixed-olive.png",
    ".codex/test-artifacts/152-ox24-scene-asset-swap/README.md",
    ".codex/history/152-ox24-scene-asset-swap.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/152-ox24-scene-asset-swap.md",
    ".codex/test-artifacts/152-ox24-scene-asset-swap/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect git diff --check and complete final diff, validate workflow, then close cycle 152 with exact verification and limitations"
}
```
<!-- cycle-checkpoint:end -->

## OX-24 — bounded execution draft

**Objective and source:** Deliver the OX-24 roadmap exit from the approved behavior and UI artwork packages above. OX-38 is DONE and supplies the existing First City CANOPY declaration and selected-instant thin adapter. The approved artwork proposes a lighter olive canopy with restrained warm accents against the same First City scene. Board approval governs this proposal; it does not authorize execution.

**Scope and sequence:** (1) Preserve the existing `CANOPY` semantic element, vegetation role, foreground pass, and world geometry. (2) Introduce the smallest typed, scene-local asset reference needed for that declaration to select the existing canopy or one renderer-compatible alternate PNG. (3) Load the chosen asset at the existing catalog First City CANOPY draw point using the existing aligned draw path. (4) Prove that reverting the authored reference restores the original asset without a renderer edit. Any alternate artwork must stay within the same 1024 × 1536 transparent canvas and avoid forecast or timeline content.

**Concrete boundary and consumers:** Add the asset choice to the existing `FirstCitySemanticElement` CANOPY declaration in `FirstCityComposition.kt`, carry it through `FirstCityCanopyRenderInput`, and resolve its bundled drawable in the catalog `CatalogAtmosphericScene` CANOPY branch in `AtmosphericScene.kt`. Keep the existing `sg2_city_canopy` drawable as the baseline choice and add one compatible alternate drawable; use the authored reference to choose between them. The renderer keeps its current aligned draw call and all other layers retain their current paths. `FirstCityTheme` layer metadata and ordering stay compatible with both choices; no saved-data migration or external pack format is involved. The fixture scene renderer is outside this catalog substitution.

**Board relationship:** The approved behavior board governs the declaration-to-adapter-to-draw flow and restoration through definition data. The approved artwork board governs the visible baseline/alternate treatment: the alternate canopy uses lighter olive foliage with restrained warm accents, while the sky, city, readout and timeline remain visually consistent at the fixed selected instant. The board is illustrative; the implementation asset must satisfy its treatment and the installed comparison must show the actual result.

**Functional invariants:** The selected instant, shared `SceneWeatherDecision`, supplied/carried/unavailable meaning, forecast text, timeline selection and gesture ownership remain unchanged. Keep `FirstCityElementId.CANOPY`, its role, pass depth/local order, and transformed bounds fixed. Unrelated First City passes and the Minimal Open Landscape stay on their existing paths. Keep the immersive scene and legible forecast facts from `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`.

**Exclusions:** No weather-interpreter change, copied world renderer, pack manifest or loader, general asset registry, second-world migration, optional hit/data metadata, Park composition, or broad accessibility/system/device matrix. No general palette or scene redesign.

**Observable exit:** A focused declaration/adapter check demonstrates that either authored asset choice resolves to the same `CANOPY` identity, role, pass, depth, order, bounds and selected instant; restoring the original reference selects the baseline asset. Two scoped installed captures at one fixed First City selected instant show the canopy change while sky, city, forecast readout, and timeline remain consistent. Compare the captures and record actual observations, not assumed parity.

**Capture and restoration sequence:** Keep the existing `sg2_city_canopy` choice in the CANOPY declaration for the first installed build and capture. Change only that declaration's authored asset reference to the alternate drawable, then rebuild/reinstall and capture the same First City selected instant and display state. Record the selected instant, build/reinstall and capture commands, chosen references, and image paths so the two captures can be compared. To prove restoration, change only the declaration reference back to the existing drawable and verify that the adapter resolves the baseline choice; do not edit the renderer. Leave the alternate authored choice as the delivered implementation after this check. The two installed captures remain the required visual evidence; restoration is covered by the focused authorized contract check.

**Verification authorization and evidence:** The owner authorized focused slice-specific tests. After the implementation works, extend the existing `FirstCityThemeTest` canopy adapter check to cover each authored asset choice and restoration, including identity, role, pass depth/order, bounds, selected instant, and unchanged weather-decision reference. Run only that focused test path and record its exact result. Build the affected app path as needed. The owner also authorized two scoped installed captures at the same First City selected instant, one with each authored asset choice, solely as visual evidence required by the roadmap exit; no device test cases or device/system matrix. Compare canopy treatment, other visible layers, forecast facts, timeline legibility, and obvious rendering or interaction regressions in the affected state; record actual observations and limitations. Store exact commands, capture paths, comparison findings, and limitations in `.codex/test-artifacts/152-ox24-scene-asset-swap/README.md`. `python scripts/dev.py workflow` and `git diff --check` are workflow checks, not slice acceptance by themselves. Closeout target: `.codex/history/152-ox24-scene-asset-swap.md`.

**Risks and decisions:** The alternate PNG must be compatible with the existing aligned draw, and the artwork board is illustrative rather than a final asset file. The implementation may choose a matching transparent PNG only within the approved canopy treatment. If the implemented appearance materially departs from the approved board, return for owner review before claiming visual exit. No unresolved owner decision remains for this draft.
