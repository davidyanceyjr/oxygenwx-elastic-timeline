# Cycle plan — 130-se0-scene-spec-reconciliation

State: CLOSED
Plan maturity: FINAL
Cycle ID: `130-se0-scene-spec-reconciliation`
Roadmap items: SE.0
Objective: Reconcile the Scene Engine draft with controlling authority and owner decisions using a documentation-only behavior board.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "130-se0-scene-spec-reconciliation",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "SE.0",
      "depends_on": [
        "SG.1",
        "SG.2",
        "SG.3",
        "SG.4",
        "SG.4a",
        "SG.8",
        "SG.9",
        "SG.13",
        "MR.2"
      ],
      "status": "DONE",
      "exit": "The draft distinguishes binding authority, completed behavior, architectural proposals, and future data-gated features; preserves readable text, missing/carry/source-time rules, discrete facts versus aesthetic interpolation, supported solar/lunar claims, and the unnamed default-scene baseline; records an authority-linked wording diff and git diff --check without test, build, or visual-acceptance claims.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-12",
          "artifacts": [
            {
              "path": ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.md",
              "sha256": "c60c1697dbca6f1200558b590ebebcae60e3037ddabe67832ea9913e631446a9"
            },
            {
              "path": ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.svg",
              "sha256": "31d76e80cf8eee56f9b6003755359c3550fd555c4d665b6a46b2e5e929c38e75"
            },
            {
              "path": ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.png",
              "sha256": "ba6d445bf08ee079ba15a017d7114d3ed0c9fa02eb1ad1411892ae57f986277b"
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
      "outcome": "Scene Engine first draft reconciled with controlling and adopted authority, completed SG/ATM/MR decisions, and owner direction; documentation-only exit met.",
      "evidence": [
        ".codex/test-artifacts/130-se0-scene-spec-reconciliation/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-12",
    "summary": "Completed SE.0 documentation-only reconciliation of the Scene Engine draft with controlling authority and owner decisions; no app implementation.",
    "verification": [
      "python scripts/dev.py workflow: PASS at closeout",
      "git diff --check: PASS",
      "Final scene-spec and roadmap diff inspected against approved SE.0 board; tests/builds/device checks NOT RUN (outside scope)"
    ],
    "evidence_directory": ".codex/test-artifacts/130-se0-scene-spec-reconciliation/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "130-se0-scene-spec-reconciliation",
  "slice_ids": [
    "SE.0"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.md",
    ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.svg",
    ".codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.png"
  ],
  "completed_gates": [
    "SE.0_behavior_board_approved",
    "SE.0_plan_final",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Current unnamed default-scene mechanics are the first refactor behavior baseline; MR.2 readout redesign is outside the first slice and its exact layout is not permanent.",
    "SE.0 behavior board approval refreshed on 2026-11-12."
  ],
  "open_questions": [],
  "completed_outputs": [
    "docs/ROADMAP.md: SE.0 documentation-only NEXT slice and bounded exit",
    "Recorded behavior board approval for SE.0.",
    "SE.0 behavior/content board approved by product owner on 2026-11-12",
    "Final bounded SE.0 documentation plan with approved board fingerprints, scope, acceptance, and verification authorization.",
    "Activated SE.0 for execution.",
    "Closed slice SE.0 as DONE.",
    "SE.0 scene draft reconciled and documentation evidence recorded.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/130-se0-scene-spec-reconciliation/README.md",
    ".codex/history/130-se0-scene-spec-reconciliation.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/130-se0-scene-spec-reconciliation.md",
    ".codex/test-artifacts/130-se0-scene-spec-reconciliation/README.md",
    "docs/Oxygen_Scene_Engine_Refactor_Spec.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect the complete final documentation diff, run workflow and diff checks, then archive cycle 130."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

**Objective:** Reconcile `docs/Oxygen_Scene_Engine_Refactor_Spec.md` with controlling authority and the product owner's default-scene and MR.2 decisions. The owner-approved SE.0 behavior/content board governs the six passage groups and acceptance reading.

**Scope:** Revise the draft's status, information hierarchy, weather interpretation, 44-date coverage and interpolation language, missing/carry/source-time rules, astronomy claims, and default-scene/first-refactor language. Add concise authority references near the changed rules. Update only the roadmap and cycle records required to document this slice.

**Exclusions:** No app code, tests, builds, installed visual review, new provider fields, new scene artwork or theme, readout redesign, paid-pack decision, measured astronomy, or first engine implementation plan. `FirstCityTheme` may remain an internal code identifier. The MR.2 arrangement is current implementation but not a permanent product requirement.

**Binding invariants:** `docs/SPECIFICATION.md` controls prototype and semantic conflicts. Preserve absolute selected-time authority; typed support and source valid times; missing/unavailable and eligible disclosed carry rules; independent field support; separately readable critical forecast text and accessible meaning; the upper-third cloud region; current default-scene behavior and directly affected timeline/gesture semantics. The adopted experience specification and completed SG.1/2/3/4/4a/8/9/13 and MR.2 decisions supply narrower context.

**Observable exit:** The draft names its subordinate proposal status, does not treat 44 dates as 1,056 guaranteed hourly values, separates aesthetic interpolation from discrete facts, makes unavailable and carried scene meaning explicit, limits current Sun/Moon claims to supported inputs, calls the product scene the unnamed default scene, and presents MR.2 as current rather than permanent. Review the final diff against the approved board and relevant authority passages.

**Authorized verification and evidence:** Run `python scripts/dev.py workflow` and `git diff --check`; inspect the exact documentation diff. Do not add or run tests or builds. Record commands, results, authority links, diff notes, and limitations in `.codex/test-artifacts/130-se0-scene-spec-reconciliation/README.md`. Close only after the documentation exit is met and archive the plan and outcome under `.codex/history/`.

**Risk:** Broad aspirational prose could still imply measured forecast or astronomy claims. Resolve this with explicit support and illustration qualifiers near each affected passage. No installed or system acceptance is claimed for documentation alone.

**Board approval:** The product owner approved `.codex/concepts/SE.0-scene-spec-reconciliation-behavior-board.md`, its SVG review sheet, and its PNG preview on 2026-11-12. The structured manifest records each package file's SHA-256 fingerprint. The board's six-row wording map and acceptance reading govern this slice; no UI artwork applies.

**Ordered execution:** (1) Reconcile the draft status and immediate-text hierarchy. (2) Reconcile catalog, support, carry, source-time, and interpolation language. (3) Reconcile astronomy and the unnamed default-scene/first-slice boundary. (4) Compare changed passages with the approved board and named authority; record exact results, run workflow and diff checks, then close the documentation slice.
