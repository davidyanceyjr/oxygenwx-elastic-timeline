# Cycle plan — 154-ox41-park-role-composition

State: CLOSED
Plan maturity: FINAL
Cycle ID: `154-ox41-park-role-composition`
Roadmap items: OX-41
Objective: Decide the Living Urban Park bundled role and approve a coarse landmark composition map

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "154-ox41-park-role-composition",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-41",
      "depends_on": [
        "OX-23",
        "OX-35"
      ],
      "status": "DONE",
      "exit": "The owner-approved role and labeled composition board identify required/optional landmarks, default/free access and the reference-state scope. No visual acceptance or implementation is claimed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-41-park-role-composition-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-41-park-role-composition-behavior-board.dot",
              "sha256": "c5ea4f785a52f5d5aa78036784dffadaf28901c45ae1b84bf24b3bb126fe2ec8"
            },
            {
              "path": ".codex/concepts/OX-41-park-role-composition-behavior-board.svg",
              "sha256": "5cf93f62d1a606868eb79dc664255afe60e011ac6e87d14bbc2bfc2fae5c23bd"
            },
            {
              "path": ".codex/concepts/OX-41-park-role-composition-behavior-board.png",
              "sha256": "742d208ed2504f263639eb56588231e6ec0547ab78358eb29c6f4530b60e063d"
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
      "outcome": "Owner-approved option A establishes Living Urban Park as bundled free default, with First City and Open Landscape available; approved labeled composition board identifies required and optional landmarks and daylight portrait reference scope. Decision-only exit met; no app or device acceptance claimed.",
      "evidence": [
        ".codex/test-artifacts/154-ox41-park-role-composition/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-41 completed: owner-selected bundled free default Park role and approved labeled coarse composition map recorded; no app implementation or installed visual acceptance.",
    "verification": [
      "Board DOT/SVG/PNG SHA-256 values matched approved manifest; python scripts/dev.py workflow passed at EXECUTE and CLOSEOUT; git diff --check passed; roadmap, board, plan and final diff reviewed; tests, build and device checks not run because this was a decision-only slice."
    ],
    "evidence_directory": ".codex/test-artifacts/154-ox41-park-role-composition/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "154-ox41-park-role-composition",
  "slice_ids": [
    "OX-41"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-41-park-role-composition-behavior-board.dot",
    ".codex/concepts/OX-41-park-role-composition-behavior-board.svg",
    ".codex/concepts/OX-41-park-role-composition-behavior-board.png"
  ],
  "completed_gates": [
    "OX-41 behavior board approved and fingerprinted",
    "OX-41 board and role decision complete",
    "OX-41 first draft complete from approved board; owner role decision resolved",
    "OX-41 full plan review passed: scope, dependencies, approved board, exit and authorized verification agree with roadmap",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-41 owner decision and approved composition board verified."
  ],
  "decisions": [
    "Product owner chose A: Living Urban Park is bundled free default; First City and Open Landscape remain available",
    "The approved board retains A and B as the alternatives presented for decision; docs/ROADMAP.md OX-41 Owner role decision records A as selected, so B is closed"
  ],
  "open_questions": [],
  "completed_outputs": [
    ".codex/concepts/OX-41-park-role-composition-behavior-board.dot/.svg/.png rendered and displayed",
    "Recorded behavior board approval for OX-41.",
    "Approved OX-41 behavior board package and explicit owner choice A recorded in roadmap",
    "Final bounded OX-41 plan records owner option A, approved composition, exclusions, verification and evidence target",
    "Reviewed board fingerprints, roadmap exit, dependencies and plan boundary; left cycle PLANNED",
    "Resolved board-option ambiguity in execution handoff without changing the approved board package",
    "Activated OX-41 for execution.",
    "Closed slice OX-41 as DONE.",
    "OX-41 decision evidence recorded and slice exit met.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/154-ox41-park-role-composition/README.md",
    ".codex/history/154-ox41-park-role-composition.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/154-ox41-park-role-composition.md",
    ".codex/test-artifacts/154-ox41-park-role-composition/README.md",
    "docs/ROADMAP.md OX-41"
  ],
  "next_action": "Inspect final diff and workflow, then archive completed cycle."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Approved decision and board

The product owner approved the OX-41 behavior/contract board DOT, SVG and PNG on 2026-11-18. The manifest fingerprints the package. The board displays A and B as the alternatives presented for decision. The subsequent `docs/ROADMAP.md` OX-41 **Owner role decision (2026-11-18)** selects A and closes B: the Living Urban Park is the bundled free default. First City and Minimal Open Landscape remain available as other bundled worlds. This is a future product role decision, not an instruction to change the current app default in OX-41. UI artwork is inapplicable because this slice changes no app output.

## Scope, dependencies and exclusions

- OX-23 and OX-35 are DONE. Use their scene-element inventory and logical geometry boundary as established context; do not reopen or implement them.
- Record the Park role and a labeled coarse map from the approved board. Required landmarks are the foreground entrance, central curved path, left mural wall/data-surface anchor, vegetation and benches as framing, distant skyline, and a coarse streetcar shelter anchor. Optional reference motifs are the water tower, banner, fountain, flower detail and other fine decoration.
- Scope the reference state to the supplied daylight portrait concept image at `design_draft/OxygenWX_Living_Urban_Park_Concept_01_Visual_Reference.png`. Its sample readings, weather, logo treatment and fine artwork are illustrative, not approved facts or final UI.
- Do not build the Park, change world selection, change the app default, add scene elements, implement mural data, or claim any installed visual acceptance. Those are later bounded slices.

## Invariants and observable exit

The bundled free Park must remain available without entitlement. Future theme choice must remain independent of weather place, catalog revision and exact selected instant. Critical forecast facts remain accessible as text. The coarse map guides later Park slices without fixing final camera, materials, typography, weather states or viewport behavior.

The OX-41 exit is an inspectable owner decision and approved labeled composition map identifying required and optional landmarks, the default/free access role and the daylight portrait reference scope. The board and decision satisfy the documentation outcome; no app implementation or device evidence is claimed.

## Authorized verification and evidence

Focused slice-specific tests are authorized if a scoped behavior change requires them. This decision-only slice has no app code or behavior change, so verification is a source/board/roadmap consistency review, `python scripts/dev.py workflow`, and `git diff --check`. System and device tests are out of scope. Record exact results and limits under `.codex/test-artifacts/154-ox41-park-role-composition/README.md`; the eventual closeout target is `.codex/history/154-ox41-park-role-composition.md`.

## Ordered execution handoff

1. Confirm the approved board package still matches the recorded fingerprints and the OX-41 roadmap decision and exit remain aligned.
2. In `.codex/test-artifacts/154-ox41-park-role-composition/README.md`, cite the OX-41 roadmap Owner role decision as the selection of board option A; state that B was an offered alternative and is closed. Record the completed decision-only outcome and exact verification results. No app files or installed states are part of this slice.
3. Close OX-41 against its observable exit and archive the cycle with any verification limits.

## Risks and review handoff

Later Park work could mistake illustrative readings or optional motifs in the concept image for requirements. Preserve the board's required/optional labels and the reference-scope caveat in handoffs. Plan review should verify the role decision, approved board fingerprints, exact roadmap exit, and no app-change scope before activation. No owner decision remains open.
