# Cycle plan — 132-se2-scene-spec-gap-audit

State: PLANNED (cancelled before activation)
Plan maturity: DRAFT
Cycle ID: `132-se2-scene-spec-gap-audit`
Roadmap items: SE.2
Objective: Reconcile remaining Scene Engine draft gaps and ambiguity against adopted authority and workflow; documentation only

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "132-se2-scene-spec-gap-audit",
  "plan_maturity": "DRAFT",
  "active_slice": null,
  "slices": [
    {
      "id": "SE.2",
      "depends_on": [
        "SE.0",
        "SE.1",
        "SG.1",
        "SG.4a",
        "SG.9"
      ],
      "status": "CANCELLED",
      "exit": "The spec's affected passages distinguish binding authority and completed behavior from proposals, use supported inputs and source time accurately, preserve theme composition and cloud constraints, and no longer require all-day compilation or imply an adopted pack business model. Record the authority-linked wording diff, workflow result, and git diff --check; claim no test, build, or installed visual acceptance.",
      "boards": {
        "behavior": {
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
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
      "outcome": "Product owner cancelled SE.2 before board approval or specification edits and requested removal of the board.",
      "evidence": [".codex/test-artifacts/132-se2-scene-spec-gap-audit/README.md"]
    }
  ]
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "132-se2-scene-spec-gap-audit",
  "slice_ids": [
    "SE.2"
  ],
  "stage": "BOARDS",
  "prior_cycle_state": "PLANNED",
  "accepted_boards": [],
  "completed_gates": [],
  "decisions": [
    "Documentation-only scope; no UI artwork board applies."
  ],
  "open_questions": [
    "Does the product owner approve the six SE.2 documentation dispositions, including F2 candidate-depth-group wording?"
  ],
  "completed_outputs": [
    "Expanded SE.2 board into explicit full-refactor-spec audit findings, source comparisons, section coverage, owner decision, and four feh review pages."
  ],
  "evidence": [],
  "next_stage_read_set": [
    ".codex/concepts/SE.2-scene-spec-gap-audit-behavior-board.md; docs/Oxygen_Scene_Engine_Refactor_Spec.md; docs/SPECIFICATION.md; docs/OXYGEN_EXPERIENCE_SPECIFICATION.md; docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md; docs/ROADMAP.md"
  ],
  "next_action": "Await product-owner review of the six audit findings and documentation dispositions; after approval fingerprint the board and draft exact spec wording."
}
```
<!-- cycle-checkpoint:end -->

## Cancellation outcome

The product owner cancelled this draft cycle at `BOARDS` on 2026-11-12. No board was approved and the requested SE.2 board/review images were removed. The Scene Engine refactor specification was not changed in this cycle. This archived plan preserves the attempted cycle identity and cancellation decision; no further SE.2 work is authorized by it.

## Objective, scope, invariants, verification, and evidence

Draft planning is limited to a documentation-only audit of the updated Scene Engine proposal. The six findings and proposed dispositions are in `.codex/concepts/SE.2-scene-spec-gap-audit-behavior-board.md`. Product-owner approval of that board is pending; no Scene Engine spec wording is authorized by this draft plan yet.

In scope after approval: reconcile only §§2–4, 6, 9–10, 12, 15, 17, 19, 26, 28, 31–33, 35–37 and necessary cross-references. Preserve SE.0/SE.1 decisions and completed selected-time, source support, theme composition, cloud placement, readable-fact, and gesture contracts. Exclude app code, UI art, new weather inputs, engine activation, pack sales decisions, and selection of the first implementation slice.

Authorized verification is document and final-diff inspection, `python scripts/dev.py workflow`, and `git diff --check`. Tests, builds, and installed device acceptance are outside this slice. Planned evidence is `.codex/test-artifacts/132-se2-scene-spec-gap-audit/README.md` with closeout under `.codex/history/` only if the approved exit is met.
