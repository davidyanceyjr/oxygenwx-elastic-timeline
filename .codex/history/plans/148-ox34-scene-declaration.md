# Cycle plan — 148-ox34-scene-declaration

State: CLOSED
Plan maturity: FINAL
Cycle ID: `148-ox34-scene-declaration`
Roadmap items: OX-34
Objective: Type the smallest immutable First City-local declaration for existing identity, semantic role, depth and order of a replaceable object and multi-pass object, preserving baseline pass order

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "148-ox34-scene-declaration",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-34",
      "depends_on": [
        "OX-23",
        "OX-05",
        "OX-06"
      ],
      "status": "DONE",
      "exit": "A concrete existing-world object and a multi-pass object are represented by the declaration without changing baseline pass ordering; no universal graph executor is introduced.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-34-scene-declaration-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-34-scene-declaration-behavior-board.dot",
              "sha256": "6630b9653f58714ab549158b533f38c949b8c104dd13648f4264a19e182cc0de"
            },
            {
              "path": ".codex/concepts/OX-34-scene-declaration-behavior-board.svg",
              "sha256": "cb8124dd83b89ef82ecccb8fab32cdb4c5b052a094c3ac634cde51e02ea17bf0"
            },
            {
              "path": ".codex/concepts/OX-34-scene-declaration-behavior-board.png",
              "sha256": "32644ed1995cf90adc18596b82c552fcf6bbb7f4fa5bfa77fb9bbd453d845196"
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
      "outcome": "Immutable First City semantic elements now expose typed physical pass references ordered by existing depth and local order; CANOPY and CLOUD_COVER represented; renderer sequence unchanged",
      "evidence": [
        ".codex/test-artifacts/148-ox34-scene-declaration/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-34 completed: First City semantic inventory exposes immutable ordered physical pass references for CANOPY and CLOUD_COVER using existing layer depth and local order; rendering order unchanged.",
    "verification": [
      "Source inspection PASS; python scripts/dev.py workflow PASS; python scripts/dev.py contract PASS; git diff --check PASS. Tests, build, and installed visual checks NOT RUN; tests not authorized and no visible output change. Evidence: .codex/test-artifacts/148-ox34-scene-declaration/README.md."
    ],
    "evidence_directory": ".codex/test-artifacts/148-ox34-scene-declaration/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "148-ox34-scene-declaration",
  "slice_ids": [
    "OX-34"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-34-scene-declaration-behavior-board.dot",
    ".codex/concepts/OX-34-scene-declaration-behavior-board.svg",
    ".codex/concepts/OX-34-scene-declaration-behavior-board.png"
  ],
  "completed_gates": [
    "OX-34_behavior_board_approved",
    "OX-34 behavior board approved; UI artwork inapplicable",
    "All required OX-34 boards approved and plan content complete",
    "Full REVIEW confirms scope, dependencies, compatibility, exit, and verification authorization",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner approved the displayed OX-34 board without additional constraints"
  ],
  "open_questions": [],
  "completed_outputs": [
    ".codex/concepts/OX-34-scene-declaration-behavior-board.dot,.svg,.png rendered and displayed on :0",
    "Recorded behavior board approval for OX-34.",
    "Approved OX-34 behavior board DOT, SVG, PNG package recorded with fingerprints",
    "Completed bounded OX-34 plan with scope, dependencies, invariants, exclusions, execution sequence, exit, verification authorization and evidence targets",
    "Reviewed FINAL plan against OX-34 board, OX-23 matrix, roadmap dependencies, and current First City interfaces",
    "Activated OX-34 for execution.",
    "Closed slice OX-34 as DONE.",
    "OX-34 declaration implemented and slice DONE",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/148-ox34-scene-declaration/README.md",
    ".codex/history/148-ox34-scene-declaration.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/148-ox34-scene-declaration.md",
    ".codex/test-artifacts/148-ox34-scene-declaration/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt"
  ],
  "next_action": "Review final diff and close cycle 148"
}
```
<!-- cycle-checkpoint:end -->

## Objective and approved board

Implement the smallest immutable First City-local declaration of element identity, semantic role, and ordered physical passes. The product owner approved the OX-34 behavior board DOT, SVG, and PNG package on 2026-11-18 without further constraints; the manifest records its fingerprints. The board governs the one-pass `CANOPY` and two-pass `CLOUD_COVER` examples and the unchanged-order exit. No UI artwork applies because visible output must remain unchanged.

## Scope and dependencies

- OX-23, OX-05, and OX-06 are DONE. The OX-23 matrix names `CANOPY` / `VEGETATION` (Foreground 1) as the asset-substitution candidate and `CLOUD_COVER` / `CLOUD_FIELD` as one identity across Background 4 and Middle-Ground 1. OX-06 supplies the existing First City IDs and roles; OX-05 supplies the absolute depth groups and ordered local passes.
- Add a small immutable declaration in the First City presentation scene boundary, adapting the existing `FirstCitySemanticElement` / `FirstCitySemanticInventory` where practical. Represent each example as one element ID, one role, and a stable ordered list of existing physical pass references with their depth group and local order. Preserve the current public renderer-facing pass order; declaration data may be metadata rather than a new execution driver.
- Preserve the existing `FirstCityTheme.orderedLayerSequence` behavior, including insertion of `TIME_WASH` and `READOUT_QUIET_ZONE` after `CANOPY`. Those passes remain compositing-only and do not gain semantic element IDs. The weather decision and selected instant remain external to the declaration.

## Exclusions and compatibility

No global registry, universal graph executor, second-world migration, geometry or anchors (OX-35), weather-input slots (OX-36), definition-to-renderer adapter (OX-38), asset substitution (OX-24), pack schema, inspect metadata, or change to installed UI. Do not replace OX-06 identity vocabulary or use declaration ordering to change current drawing. The First City render path and current weather truth must remain compatible.

## Execution sequence

1. Inspect the existing `FirstCityComposition.kt` semantic inventory and `FirstCityTheme.kt` layer-order path. Choose the smallest immutable type and representation that directly expresses the approved board without duplicating a second source of pass ordering unnecessarily.
2. Represent `CANOPY` and `CLOUD_COVER` with typed, ordered pass references. Keep the one-to-many cloud identity and the existing depth/local order values explicit. Retain the existing inventory lookup contract for current users.
3. Check the declaration against the OX-23 matrix and baseline pass order, including the two special compositing passes. Refactor only directly affected presentation code needed for a coherent declaration.
4. Record actual scoped verification and any limitation in `.codex/test-artifacts/148-ox34-scene-declaration/README.md`; inspect `git diff --check` and final diff before closeout.

## Exit, verification authorization, and evidence

Exit when a concrete existing-world object (`CANOPY`) and multi-pass object (`CLOUD_COVER`) are represented by the declaration, baseline pass ordering remains unchanged, and no universal graph executor is introduced. Verification is limited to source inspection, existing workflow and contract commands if applicable, and diff review. The repository instruction forbids adding or running tests unless the user separately asks; this plan does not authorize tests. No installed visual acceptance is claimed for this no-visible-change slice. Evidence target: `.codex/test-artifacts/148-ox34-scene-declaration/README.md`; eventual closeout: `.codex/history/148-ox34-scene-declaration.md` and archived plan `.codex/history/plans/148-ox34-scene-declaration.md`.

## Risk and review focus

The existing inventory stores passes as an unordered set, while physical order is held by `FirstCityLayer` and `FirstCityTheme`. The implementation should make declaration order explicit without introducing a conflicting runtime sort source. Derive depth and local order from the existing `FirstCityLayer` values so the declaration cannot drift from the current physical pass contract; confirm the visible sequence is unchanged. No owner decision remains open.
