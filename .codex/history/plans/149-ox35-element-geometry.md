# Cycle plan — 149-ox35-element-geometry

State: CLOSED
Plan maturity: FINAL
Cycle ID: `149-ox35-element-geometry`
Roadmap items: OX-35
Objective: Define bounded First City-local logical geometry and anchor semantics for one replaceable CANOPY object, yielding consistent transformed bounds without changing baseline visuals

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "149-ox35-element-geometry",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-35",
      "depends_on": [
        "OX-34"
      ],
      "status": "DONE",
      "exit": "One scene element can be positioned and its transformed bounds determined consistently from its authored declaration; baseline scene remains visually unchanged.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-35-element-geometry-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-35-element-geometry-behavior-board.dot",
              "sha256": "2a769fafb59afa0ef914a8e1099d6517b94e747a4da204d7dc775200b8687003"
            },
            {
              "path": ".codex/concepts/OX-35-element-geometry-behavior-board.svg",
              "sha256": "a9e1729b1e1b664186514cae1e643459dad159db4206771db1582dda39d27805"
            },
            {
              "path": ".codex/concepts/OX-35-element-geometry-behavior-board.png",
              "sha256": "74eeafa002a94ae28a2820f4fecae452cee16294717ef5e9dced56e15077c5cd"
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
      "outcome": "CANOPY has immutable First City world geometry and derivable transformed bounds; existing renderer path and pass order unchanged",
      "evidence": [
        ".codex/test-artifacts/149-ox35-element-geometry/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-35 completed: First City CANOPY authored world geometry and pure transformed bounds; baseline renderer and pass order unchanged by source inspection",
    "verification": [
      "python scripts/dev.py workflow passed; git diff --check passed; baseline and nondefault bounds calculated in evidence; no tests, build, or installed visual checks run under scoped authorization"
    ],
    "evidence_directory": ".codex/test-artifacts/149-ox35-element-geometry/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "149-ox35-element-geometry",
  "slice_ids": [
    "OX-35"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-35-element-geometry-behavior-board.dot",
    ".codex/concepts/OX-35-element-geometry-behavior-board.svg",
    ".codex/concepts/OX-35-element-geometry-behavior-board.png"
  ],
  "completed_gates": [
    "OX-35 draft complete from approved behavior board",
    "All required boards approved and bounded first draft complete",
    "OX-35 full plan review passed: scope, dependency, board fingerprints, exit, verification authorization, and evidence target",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Product owner approved displayed OX-35 behavior board without additional constraints"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented CANOPY world-space geometry and pure transformed bounds without editing renderer or layer order",
    "Closed slice OX-35 as DONE.",
    "OX-35 DONE; final diff and diff --check inspected",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/149-ox35-element-geometry/README.md",
    ".codex/history/149-ox35-element-geometry.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/149-ox35-element-geometry.md",
    ".codex/test-artifacts/149-ox35-element-geometry/README.md"
  ],
  "next_action": "Close cycle 149 with source-level verification limits recorded"
}
```
<!-- cycle-checkpoint:end -->

## Objective and approved board

Define the minimum First City-local logical geometry needed to position the existing `CANOPY` element and derive its transformed bounds. The product owner approved the displayed OX-35 behavior board DOT, SVG, and PNG package on 2026-11-18 without additional constraints; the manifest records its fingerprints. The board governs the world-space rectangle, normalized anchor, positive uniform scale, baseline example, and no-visible-change exit. UI artwork is inapplicable because visible output must remain unchanged.

## Scope and dependencies

- OX-34 is DONE. Reuse its existing `FirstCityElementId.CANOPY` / `VEGETATION` declaration and ordered physical pass reference. `FirstCityTheme.worldWidth` and `worldHeight` are 1024 and 1536. The current canopy bitmap is 1024 by 1536 pixels and the renderer draws it through `drawAlignedLayer`, which may crop for the viewport.
- Add a small immutable geometry value to the First City presentation scene boundary, associated with the `CANOPY` declaration only. Express an anchor position `(x, y)` in First City world units, unscaled positive width and height in those units, normalized anchor fractions `(ax, ay)` in `0..1`, and a positive uniform scale `s`. The baseline `CANOPY` declaration is position `(0, 0)`, size `1024 x 1536`, anchor `(0, 0)`, and scale `1`.
- Provide a pure bounds derivation: scaled width = width × scale; scaled height = height × scale; left = x − ax × scaled width; top = y − ay × scaled height; right = left + scaled width; bottom = top + scaled height. Bounds describe the authored rectangle, not the bitmap's nontransparent pixels. Keep the coordinate and unit contract clear so a later adapter can use it.
- Keep the existing scene composition, selected-instant weather decision, renderer draw calls, viewport crop, and pass ordering unchanged. The geometry is declaration metadata in this slice, not a rendering driver.

## Exclusions and compatibility

No geometry for every inventory member, arbitrary 3D transforms, rotation, skew, viewport camera mapping, pixel-alpha bounds, second-world migration, weather-input slots (OX-36), definition-to-renderer adapter (OX-38), asset substitution (OX-24), or installed UI change. Do not move the current `CANOPY` drawing or alter `TIME_WASH` and `READOUT_QUIET_ZONE` insertion. Preserve OX-34 identity, role, and pass relationships.

## Execution sequence

1. Inspect the OX-34 `FirstCitySemanticElement` boundary, the world dimensions, and the current canopy draw path. Choose the smallest immutable geometry representation that can be attached to `CANOPY` without changing existing object lookup or layer order.
2. Define the approved coordinate, anchor, size, and scale contract and pure transformed-bounds calculation. Declare the baseline canopy values using the existing First City world dimensions.
3. Inspect the declared canopy bounds against the board example `[0, 0]` to `[1024, 1536]` and at least one nondefault anchor/scale calculation using the stated formula. Inspect the renderer path and diff to confirm no visual or ordering changes.
4. Record actual scoped verification and limitations under `.codex/test-artifacts/149-ox35-element-geometry/README.md`; inspect `git diff --check` and the complete final diff before closeout.

## Exit, verification authorization, and evidence

Exit when `CANOPY` has an immutable authored world-space geometry declaration, its transformed bounds are derivable consistently for baseline and changed anchor/scale values, and the existing scene remains visually unchanged by source path. Verification is limited to source inspection, an explicit bounds calculation record, `python scripts/dev.py workflow`, `python scripts/dev.py contract` if applicable, and diff review. Tests are not authorized by this request and must not be added or run unless the user separately asks. No installed visual acceptance is claimed for this metadata-only slice. Evidence target: `.codex/test-artifacts/149-ox35-element-geometry/README.md`; eventual closeout: `.codex/history/149-ox35-element-geometry.md` and archived plan `.codex/history/plans/149-ox35-element-geometry.md`.

## Risk and review focus

The current bitmap covers the full logical world canvas, while `drawAlignedLayer` can crop it to fit a viewport. The approved bounds are authored world-space bounds, so they must not be presented as visible pixel bounds or as a claim that the renderer already consumes geometry. Keep validation limited to the contract needed for a positive rectangle, normalized anchor, and positive scale. No owner question remains open.
