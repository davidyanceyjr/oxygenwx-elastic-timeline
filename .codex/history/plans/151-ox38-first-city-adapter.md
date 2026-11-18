# Cycle plan — 151-ox38-first-city-adapter

State: CLOSED
Plan maturity: FINAL
Cycle ID: `151-ox38-first-city-adapter`
Roadmap items: OX-38
Objective: Adapt one First City canopy rendering path through its authored declaration while preserving baseline output and weather decisions

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "151-ox38-first-city-adapter",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-38",
      "depends_on": [
        "OX-34",
        "OX-35",
        "OX-36"
      ],
      "status": "DONE",
      "exit": "A selected-instant First City rendering path reads one declared object while installed baseline visuals, draw order and weather truth stay unchanged; untouched objects remain on their existing path.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.dot",
              "sha256": "9d73e073804d409d8e58d1c61442203a0805cd7ec67f7d8d16ec6751e036a116"
            },
            {
              "path": ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.svg",
              "sha256": "1e6424a1e24aed1c4f881fd652232d43b4be61ada39e8ea3cc9bb48bbfe1cede"
            },
            {
              "path": ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.png",
              "sha256": "7478401e41daacecd145950f2c35fd9147d9350be2e90c50529cfa3dbe490569"
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
      "outcome": "Declared First City CANOPY consumed in selected-instant catalog renderer; fixed installed scene pixel identical to pre-change build; draw order and shared weather decision unchanged",
      "evidence": [
        ".codex/test-artifacts/151-ox38-first-city-adapter/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "Completed OX-38 First City canopy declaration adapter; installed fixed-scene visual parity is pixel identical to pre-change build",
    "verification": [
      "Focused canopy adapter unit test and :app:assembleDebug passed; installed Pixel 3 XL First City pre/post captures RMSE 0; git diff --check and workflow passed. MainActivity loading captures excluded; app-wide accessibility, RTL, TalkBack, font, motion and device matrix not run."
    ],
    "evidence_directory": ".codex/test-artifacts/151-ox38-first-city-adapter/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "151-ox38-first-city-adapter",
  "slice_ids": [
    "OX-38"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.dot",
    ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.svg",
    ".codex/concepts/OX-38-first-city-canopy-adapter-behavior-board.png"
  ],
  "completed_gates": [
    "final_plan_reviewed",
    "all_slice_boards_approved"
  ],
  "decisions": [
    "Owner chose option 1: fixed debug First City scene host with isolated pre-change build and matching post-change capture"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Canopy adapter and focused test implemented; focused test and build passed; baseline and after captures show loading screen and do not prove parity",
    "Closed slice OX-38 as DONE.",
    "OX-38 completed with focused test/build and pixel-identical installed First City before/after captures",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/151-ox38-first-city-adapter/baseline.png",
    ".codex/test-artifacts/151-ox38-first-city-adapter/after.png",
    ".codex/test-artifacts/151-ox38-first-city-adapter/README.md",
    ".codex/history/151-ox38-first-city-adapter.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/151-ox38-first-city-adapter.md",
    ".codex/test-artifacts/151-ox38-first-city-adapter/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/FirstCityThemeTest.kt"
  ],
  "next_action": "Review final diff, workflow and evidence, then close completed OX-38 cycle"
}
```
<!-- cycle-checkpoint:end -->

## Approved board and objective

The product owner approved the displayed OX-38 behavior board DOT, SVG, and PNG on 2026-11-18 without added board constraints. The manifest fingerprints the package. It proposes the existing First City `CANOPY` as the single adapter target: one authored declaration with identity, role, foreground pass, and geometry flows through a thin adapter to the current canopy drawing path. UI artwork is not required because this slice preserves visible output. The board governs adapter scope and acceptance, not implementation or installed evidence.

## Scope and dependencies

- OX-34, OX-35, and OX-36 are DONE. Reuse OX-34's First City-local declaration and OX-35's authored canopy bounds. Preserve OX-36's shared weather interpretation boundary; canopy has no new weather rule or input.
- In the selected-instant First City composition path, make only the `CANOPY` render branch read its `FirstCitySemanticElement` declaration, including pass and geometry, through a small local adapter. Resolve the declaration into a narrow canopy render input that carries the selected instant, ID, pass, and transformed full-world bounds to the existing `sg2_city_canopy` draw branch. Consume the bounds as the declaration-backed placement contract; preserve `drawAlignedLayer`'s existing viewport crop and canvas mapping. Keep the existing `layerOrder` traversal as the ordering owner.
- Keep all other First City branches, both cloud passes, compositing passes, and the Minimal Open Landscape path on their current render paths. Do not introduce an asset swap, pack manifest, optional data or hit metadata, global registry, or generalized graph executor. OX-24 owns asset substitution.

## Invariants and exit

The exact selected instant, shared `SceneWeatherDecision`, weather truth and source evidence, original canopy asset, authored full-world canopy bounds, Foreground local order 1, other pass order, and untouched object behavior remain unchanged. The adapter must consume the declared canopy object in the selected-instant First City path; merely looking it up in a test does not meet the exit. The roadmap exit requires installed baseline visual parity, unchanged draw order and weather truth, and unchanged direct paths for other objects.

## Execution and verification

1. Inspect the existing `CANOPY` declaration, aligned-layer coordinates, selected-instant composition wiring, and directly affected focused tests. Capture a deterministic installed First City baseline before the renderer change at one selected state with the canopy visible. Record the exact selection, command, screenshot path, and provenance.
2. Add a narrow declaration-to-renderer adapter for `CANOPY` only. Resolve its ID, pass, selected instant, and transformed geometry to the narrow canopy render input. Check that the declared full-world bounds match the current aligned-layer placement contract, then let the current `drawAlignedLayer` preserve viewport cropping and image mapping. Keep actual pass iteration and image resource selection stable. Do not add a new transform or alter crop math to force geometry into pixel coordinates.
3. Once the complete path works, add or update focused OX-38 slice tests proving declaration consumption, selected-instant association, canonical geometry/order, and unchanged weather decision boundary where directly relevant. The owner authorized focused slice-specific tests. Run only those focused tests, with exact commands and results.
4. Capture the same installed First City state after the change and compare against the baseline for visual parity. The owner explicitly permits this focused installed capture to satisfy OX-38's exit. This is scoped visual acceptance, not a broad device or system test. Inspect the affected render path and final diff to verify untouched objects remain direct and weather decisions have not changed. Run `python scripts/dev.py workflow` and `git diff --check`.

## Evidence, limits, and risks

Preserve exact commands, focused test results, baseline/after screenshots and comparison, source inspection, and any limitations under `.codex/test-artifacts/151-ox38-first-city-adapter/README.md`. Closeout belongs in `.codex/history/151-ox38-first-city-adapter.md`; archive this plan there on completion. Do not claim app-wide accessibility, RTL, TalkBack, device matrix, or system acceptance. The main implementation risk is confusing declaration world bounds with the viewport-dependent bitmap crop. The adapter must use the bounds as a logical placement contract while leaving the existing `drawAlignedLayer` crop and mapping intact; compare focused source evidence and installed images before claiming parity. No material owner question remains.
