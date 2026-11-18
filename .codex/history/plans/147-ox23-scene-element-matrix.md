# Cycle plan — 147-ox23-scene-element-matrix

State: CLOSED
Plan maturity: FINAL
Cycle ID: `147-ox23-scene-element-matrix`
Roadmap items: OX-23
Objective: Inventory the existing First City and Minimal Open Landscape scene elements, mapping source-linked identity, role, depth, draw pass, and weather-input ownership for the first asset substitution.

## Cycle roster and board approvals

The structured roster records the single slice's dependency order, concrete exit,
approved behavior package, and UI artwork inapplicability.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "147-ox23-scene-element-matrix",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-23",
      "depends_on": [
        "OX-04",
        "OX-05",
        "OX-06",
        "OX-18"
      ],
      "status": "DONE",
      "exit": "A source-linked matrix identifies the actual objects needed for first asset substitution, the completed OX-05/OX-06/OX-18 contracts being reused, and explicit deferred fields. No code or visual change.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-23-scene-element-matrix-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-23-scene-element-matrix-behavior-board.dot",
              "sha256": "04ce9aaffc2df0f5c33e97d3eecaed1a2e994ec22405cf8ecea6d0cc93294198"
            },
            {
              "path": ".codex/concepts/OX-23-scene-element-matrix-behavior-board.svg",
              "sha256": "f92a07ebb872f01527022254c76423d5abcb9b241f04332ce9cfd22591fc43bc"
            },
            {
              "path": ".codex/concepts/OX-23-scene-element-matrix-behavior-board.png",
              "sha256": "0eb0cc10347a6c7f3c648665ff4a02b3fe1a474387aae075de3f9e0c93199d1c"
            },
            {
              "path": ".codex/concepts/OX-23-scene-element-matrix-behavior-board.md",
              "sha256": "82e4ae5dbceed9443e93536f56c3dfb28ff9818f76411b3c1755d06746dcdd5b"
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
      "outcome": "Recorded source-linked 11-object First City and six-draw Open Landscape matrix, contract crosswalk, canopy candidate, and deferred fields; no code or visual change.",
      "evidence": [
        ".codex/test-artifacts/147-ox23-scene-element-matrix/scene-element-matrix.md",
        ".codex/test-artifacts/147-ox23-scene-element-matrix/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-23 completed: source-linked scene-element matrix and deferred-field crosswalk recorded; no code or visual change.",
    "verification": [
      "python scripts/dev.py workflow passed; source/row/deferred-outcome inspection passed (11 First City, 6 Open Landscape); git diff --check passed; no app tests, build, or device checks authorized or run."
    ],
    "evidence_directory": ".codex/test-artifacts/147-ox23-scene-element-matrix/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

The tracker updates this record at each stage boundary and preserves the exact next-stage read set.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "147-ox23-scene-element-matrix",
  "slice_ids": [
    "OX-23"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-23-scene-element-matrix-behavior-board.dot",
    ".codex/concepts/OX-23-scene-element-matrix-behavior-board.svg",
    ".codex/concepts/OX-23-scene-element-matrix-behavior-board.png",
    ".codex/concepts/OX-23-scene-element-matrix-behavior-board.md"
  ],
  "completed_gates": [
    "Full OX-23 plan review passed; plan FINAL and cycle remains PLANNED / REVIEW.",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "No unresolved owner decision or split is required for this one documentation/evidence slice.",
    "Focused review: use one Open Landscape precipitation entry with separate conditional RAIN and SNOW draw calls; record NONE and STORM as no precipitation draw. Record the RAIN sky tint under the sky entry."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Source-linked matrix with 11 First City objects and six descriptive Open Landscape draws recorded.",
    "Closed slice OX-23 as DONE.",
    "OX-23 exit met and slice marked DONE.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/147-ox23-scene-element-matrix/scene-element-matrix.md",
    ".codex/test-artifacts/147-ox23-scene-element-matrix/README.md",
    ".codex/history/147-ox23-scene-element-matrix.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/147-ox23-scene-element-matrix.md",
    ".codex/test-artifacts/147-ox23-scene-element-matrix/README.md"
  ],
  "next_action": "Inspect final diff and close cycle 147."
}
```
<!-- cycle-checkpoint:end -->

## Objective and approved input

Produce a source-linked matrix of the meaningful **existing** First City and Minimal Open Landscape scene objects. It must identify the current object, identity and role where typed, depth and ordered draw pass, weather-input owner, source path and symbol, asset or primitive, and suitability for the first asset substitution. Mark fields needed now and fields deferred to named consumers. This is evidence intake; it changes no code or visible UI.

The product owner approved the four-file behavior board package at `.codex/concepts/OX-23-scene-element-matrix-behavior-board.{dot,svg,png,md}` on 2026-11-18 without additional constraints. The cycle manifest holds exact fingerprints. The board's row contract and source anchors govern this plan's coverage and exit. No UI artwork board applies because OX-23 has no visual output change. The board is planning authority, not the finished inventory or acceptance evidence.

## Dependencies and invariants

- OX-04, OX-05, OX-06, and OX-18 are `DONE`. Reuse OX-05 ordered First City depth/pass semantics, OX-06 First City-local identities and roles, and OX-18's one shared weather decision for both worlds; use OX-04's scene-truth evidence contract. Record source links and the relevant completed contract for each matrix field instead of inventing a new semantic model.
- Keep `CLOUD_COVER` one First City semantic object across its background and middle-ground cloud passes. Treat `TIME_WASH` and `READOUT_QUIET_ZONE` as compositing passes, not semantic objects.
- The minimal open landscape has no equivalent typed identity, role, or depth/order declaration. Use descriptive labels and `not typed` for those fields; do not infer a future cross-world identity from draw-call order.
- Preserve selected-instant weather ownership in `SceneWeatherDecision` and the source reading's evidence. The inventory does not recalculate, normalize, or reinterpret weather data.
- Keep the first asset-substitution candidate as existing First City `CANOPY` / `VEGETATION`: Foreground `CANOPY` pass, `R.drawable.sg2_city_canopy`, and `drawAlignedLayer(canopy)`. This is a documented candidate for a later slice, not an asset change here.

## Execution sequence and outputs

1. Inspect the current source and record a source location and symbol for each of the 11 entries in `FirstCitySemanticInventory`. Trace each entry's role, mapped pass(es), depth group/local order, concrete draw call, asset or primitive, and weather/cue input owner through `FirstCityTheme.compose` and `CatalogAtmosphericScene`. Where a pass spans one identity, keep it in one row with both passes. Record unsupported or conditional fields explicitly.
2. Trace `MinimalOpenLandscapeComposition` and `CatalogOpenLandscapeScene`. Record the actual sky, cloud field, terrain, left and right procedural tree groups, and precipitation draws. Use one descriptive precipitation entry that names the conditional `RAIN` / `drawCityRainField` and `SNOW` / `drawCitySnowField` calls; state that `NONE` and `STORM` draw no precipitation field in this renderer. Record the rain-only sky tint in the sky entry. These are draw branches, not separate typed identities. Mark identity, semantic role, and formal depth/order `not typed` where no declaration exists. Distinguish draw-call order from a typed depth contract.
3. Add a concise contract crosswalk for OX-04/05/06/18, with source-linked ownership of selected-instant sky, clouds, precipitation, and source evidence. Classify current fields as required now for identifying the first substitution boundary, and list authored identity/depth (OX-34), geometry/anchors (OX-35), typed weather slots (OX-36), adapter (OX-38), actual swap (OX-24), and second-world reuse (OX-92) as deferred outcomes.
4. Record the matrix, crosswalk, and deferred-field list in `.codex/test-artifacts/147-ox23-scene-element-matrix/scene-element-matrix.md`. Identify the CANOPY asset, source path, and pass as the first substitution candidate. Record any uncertainty in the artifact rather than filling it with assumptions.

## Exclusions and compatibility

No source, resource, renderer, fixture, UI, or visual change. No new universal registry, authoring schema, pack manifest, hit metadata, geometry model, typed weather slot, adapter, or asset substitution. Existing scene behavior and catalog/weather meaning remain as implemented. Documentation changes are limited to the evidence artifact, roadmap/cycle bookkeeping, and closeout record; if a source fact conflicts with the approved board, stop and return the affected input for review rather than silently widening OX-23.

## Exit, verification, and evidence

The slice exits when the matrix names all 11 First City semantic objects and the specified open-landscape draws, links each to actual source, and identifies the first substitution's object, asset/primitive, and pass. It must show reuse of completed OX-05/06/18 contracts, keep OX-04 evidence ownership visible, and explicitly defer future authored fields. No code or visual change is part of exit.

For this documentation-only slice, verify row coverage and source links by inspection, compare the recorded pass/order and input ownership with the cited source, and check that deferred fields map to their roadmap consumers. Run `python scripts/dev.py workflow` to validate cycle state; inspect `git diff --check` and the final diff before closeout. Do not run or add app tests, builds, device checks, or screenshots without separate authorization. Record the exact checks and limits in `.codex/test-artifacts/147-ox23-scene-element-matrix/README.md` and close the cycle in `.codex/history/147-ox23-scene-element-matrix.md` only after its exit is met.

## Risk and review focus

The main risk is treating a physical pass or procedural draw as an authored object that does not exist yet. Review the one-to-many cloud pass mapping and open-landscape `not typed` fields closely. The source-linked matrix, explicit uncertainty, and deferred consumer list make that boundary reviewable without expanding this slice.

## Full review disposition — 2026-11-18

The `PLANNED` pointer, one-slice roster, `NEXT` roadmap status, and four completed dependencies agree. The approved behavior package is fingerprinted, and no UI artwork is required for this documentation-only outcome. The source boundary supports the 11 First City identities, the untyped open-landscape draws, and the shared weather-decision owner. The steps, exclusions, evidence paths, inspection checks, and exit are bounded to OX-23; no tests or app changes are authorized by this review. No material owner question remains. The plan is `FINAL` for review purposes and remains unactivated.
