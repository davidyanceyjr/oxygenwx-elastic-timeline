# Cycle plan — 135-ox01r-debug-baseline-correction

State: CLOSED
Plan maturity: FINAL
Cycle ID: `135-ox01r-debug-baseline-correction`
Roadmap items: OX-01R
Objective: Correct OX-01 debug-fixture evidence using focused current-build installed unavailable-state and scrub observations

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "135-ox01r-debug-baseline-correction",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-01R",
      "depends_on": [
        "OX-01",
        "SG.11e",
        "MR.2"
      ],
      "status": "DONE",
      "exit": "Exact current debug catalog/selected instants, installed screenshots and interaction observations, fixture reachability, and a corrected evidence classification are recorded with limitations. The older SG.11 mismatch is cited and its current status is verified or explicitly unresolved.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-14",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.svg",
              "sha256": "4911c73a378077cfdb0b698fe68b034ddb5527eb3ee775bef0bce435dcb78ef0"
            },
            {
              "path": ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.png",
              "sha256": "f5fe9c49f48794e1af8f7eb667bc4833dab403d60d7c6229fbc4f784867d13c8"
            },
            {
              "path": ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.dot",
              "sha256": "dc966fe10bc9e068456950795b246b3213506abccbe620c2d2e732065460f450"
            },
            {
              "path": ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.md",
              "sha256": "b1b5e3084891a364973b839456deb9fc44bd9064d0d2ccdffc3832a3a7ca0be7"
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
      "outcome": "Corrected OX-01 debug-fixture coverage; installed matched Florence replay confirms unavailable event overlay versus 4 PM-sourced partly-cloudy center text at selected 3:30:15 PM CDT; device restored; no app change",
      "evidence": [
        ".codex/test-artifacts/135-ox01r-debug-baseline-correction/README.md",
        ".codex/test-artifacts/135-ox01r-debug-baseline-correction/unavailable-1500-matched.png",
        ".codex/test-artifacts/135-ox01r-debug-baseline-correction/scrub-matched.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-14",
    "summary": "OX-01R documentation correction complete: existing SG.11 debug routes recognized; current matched Florence replay shows unavailable overlay versus 4 PM-sourced partly-cloudy center text during 3:30:15 PM scrub; no app behavior change",
    "verification": [
      "PASS python scripts/dev.py workflow; PASS python scripts/dev.py run --serial 8AKY0M32X (build and install); PASS exact Florence replay activation, Pixel 3 XL installed PNG/XML at 3:00 PM and 3:30:15 PM, focused hourly gesture, local evidence-link check, git diff --check; device restored to Florence LIVE and temporary Chicago save removed; NOT RUN automated tests, lint, contract, TalkBack, RTL, broad system matrix; no new replay built"
    ],
    "evidence_directory": ".codex/test-artifacts/135-ox01r-debug-baseline-correction/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "135-ox01r-debug-baseline-correction",
  "slice_ids": [
    "OX-01R"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.svg",
    ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.png",
    ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.dot",
    ".codex/concepts/OX-01-baseline-the-current-world-behavior-board.md"
  ],
  "completed_gates": [
    "OX-01R_behavior_board_approved",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Existing replay sufficient; later authorization to build debug replays did not require a new fixture in this slice"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Current-build matched-location 3:00 PM unavailable capture and 3:30:15 PM scrub capture; OX-01 dated correction",
    "Closed slice OX-01R as DONE.",
    "OX-01R DONE, OX-01 evidence corrected, original cycle 134 history unchanged",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/135-ox01r-debug-baseline-correction/README.md",
    ".codex/test-artifacts/135-ox01r-debug-baseline-correction/unavailable-1500-matched.png",
    ".codex/test-artifacts/135-ox01r-debug-baseline-correction/scrub-matched.png",
    ".codex/history/135-ox01r-debug-baseline-correction.md"
  ],
  "next_stage_read_set": [
    ".codex/test-artifacts/135-ox01r-debug-baseline-correction/README.md",
    ".codex/test-artifacts/134-ox01-current-world-baseline/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Archive OX-01R with exact verification, current discrepancy, and device restoration"
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

The owner selected focused current-build debug replay capture on 2026-11-14. Reuse the approved OX-01 behavior board for its unavailable scene, selected-time scrub, and evidence classification questions. There is no visible change, so UI artwork is inapplicable. Preserve the original cycle 134 history; make only a dated correction to its artifact index, reference set, and inventory, plus this cycle's evidence and roadmap outcome.

Use the existing SG.11e storm/fallback replay at its 3:00 PM CDT unavailable instant and a focused hourly scrub toward the following supported sample. Record the catalog identity, instant, installed state, screenshot/hierarchy, and any event/readout agreement or disagreement. Check whether the already-installed app matches current source; install the current debug build if needed. Do not edit fixtures or app code. No automated tests, broad accessibility, RTL, or device matrix. `python scripts/dev.py workflow`, focused `run`/`screenshot` and device interaction, artifact inspection, and `git diff --check` are authorized by the owner's selection of option 2.

Exact fixture route: Florence location `geonames:4062577`, REPLAY dataset `sg11-storm-fallback-typed-v2`, local 2026-10-28 3:00 PM CDT (`2026-10-28T20:00:00Z`) from `DebugSg11StormFallbackCatalogFixture`. The owner selected Florence on 2026-11-14 after the planned Chicago route proved unavailable: the debug activity prepares SG.11 replays for the first saved location, Florence. The same `America/Chicago` timezone preserves the fixture's local event slots. Use the versioned `DEBUG_ACTIVATE_CATALOG` action and exact selected instant. Capture the installed main screen and hierarchy at that instant. Use current gesture rules to enter hourly scale and make a focused drag toward 4:00 PM; record the resulting exact selected instant from hierarchy and compare selected event, center condition, and source-time label. Evidence target: `.codex/test-artifacts/135-ox01r-debug-baseline-correction/README.md` plus PNG/XML files. If the Florence route cannot be reached, document the exact error and leave current status unknown; do not invent a replacement fixture.

The original OX-01 board was owner-approved on 2026-11-14; the owner chose this specific correction/capture on the same date. Its behavior/evidence questions and constraints apply unchanged. The SG.11e installed mismatch is historical evidence, not a presumed current defect. The closeout will retain cycle 134's archived plan/history as written and add a dated follow-up link in its artifact documents.
