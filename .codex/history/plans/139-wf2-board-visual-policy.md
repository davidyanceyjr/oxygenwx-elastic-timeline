# Cycle plan — 139-wf2-board-visual-policy

State: CLOSED
Plan maturity: FINAL
Cycle ID: `139-wf2-board-visual-policy`
Roadmap items: WF.2
Objective: Standardize behavior and UI board visual artifact creation and presentation

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "139-wf2-board-visual-policy",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "WF.2",
      "depends_on": [],
      "status": "DONE",
      "exit": "The board policy consistently specifies Graphviz DOT plus SVG/PNG outputs for behavior boards, SVG or PNG artwork for UI boards, and presentation of non-terminal visual artifacts through `feh` at X display `:0`. The planning workflow names the exact source and rendered files and shows the review artifact before requesting owner approval. All targeted workflow documents agree, `python scripts/dev.py workflow` passes, and `git diff --check` passes. No app tests or visual acceptance are in scope.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WF.2-board-visual-policy-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/WF.2-board-visual-policy-behavior-board.svg",
              "sha256": "16bfddfdc90b0418057ff1b6109c966882bdee29be13e1ba89bc307910d4b41b"
            },
            {
              "path": ".codex/concepts/WF.2-board-visual-policy-behavior-board.dot",
              "sha256": "7a6c2271df5412b63eb2b08cb44f56136ebd1da59bf4a99e3c097072b4bdc99a"
            },
            {
              "path": ".codex/concepts/WF.2-board-visual-policy-behavior-board.png",
              "sha256": "3a615d34afee4ad4e79819b6d24f93df13da21cdf945c7e02b38c99e93b82fba"
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
      "outcome": "Implemented the approved Graphviz DOT with SVG/PNG behavior-board policy, SVG-or-PNG UI artwork guidance, and feh presentation on DISPLAY=:0 across roadmap authority, project instructions, planning skill, and UI workflow. Exit met; this was documentation-only with no app changes.",
      "evidence": [
        ".codex/test-artifacts/139-wf2-board-visual-policy/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-15",
    "summary": "WF.2 standardized visual board artifacts and display. Behavior boards now use Graphviz DOT source with SVG and PNG renders; UI artwork boards may use SVG or PNG, retaining both when they serve distinct source and review roles. Non-terminal visual artifacts are presented to the user with feh on X display :0. The policy is recorded in the roadmap authority, project instructions, planning skill, and UI development workflow. No app UI or product behavior changed.",
    "verification": [
      "python scripts/dev.py workflow — exit 0: Workflow scaffold: OK; cycle 139 at ACTIVE/CLOSEOUT before close.",
      "git diff --check — exit 0 with no output; final tracked diff inspected.",
      "Graphviz dot 16.1.1 generated approved behavior-board SVG and PNG; feh 3.13.1 opened the PNG on DISPLAY=:0.",
      "No app tests, builds, or installs were run; they were outside the documentation-only plan."
    ],
    "evidence_directory": ".codex/test-artifacts/139-wf2-board-visual-policy/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "139-wf2-board-visual-policy",
  "slice_ids": [
    "WF.2"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/WF.2-board-visual-policy-behavior-board.svg",
    ".codex/concepts/WF.2-board-visual-policy-behavior-board.dot",
    ".codex/concepts/WF.2-board-visual-policy-behavior-board.png"
  ],
  "completed_gates": [
    "final_plan_reviewed",
    "all_slice_boards_approved",
    "All roster slices have terminal outcomes; no owner questions remain."
  ],
  "decisions": [
    "Plan review found no unresolved owner decisions or scope changes; the original user request authorizes execution of the approved WF.2 policy."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reviewed the FINAL WF.2 plan against the approved behavior board and relevant workflow authorities; scope, acceptance, board record, and verification are complete.",
    "Activated WF.2 for execution.",
    "Closed slice WF.2 as DONE.",
    "WF.2 is DONE; final documentation diff was inspected,  passed, and workflow validation passed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/139-wf2-board-visual-policy/README.md",
    ".codex/history/139-wf2-board-visual-policy.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/139-wf2-board-visual-policy.md",
    "docs/ROADMAP.md",
    "AGENTS.md",
    ".codex/test-artifacts/139-wf2-board-visual-policy/README.md"
  ],
  "next_action": "Close cycle 139 with the recorded WF.2 outcome and exact verification results, then confirm the workflow returns to IDLE."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Objective

Make board artifact creation and visual presentation consistent across future workflow cycles. Use Graphviz source and SVG/PNG renders for behavior boards, accept SVG or PNG for UI artwork according to the asset, and show review visuals to the user through `feh` on X display `:0`.

### Scope

- Make `docs/ROADMAP_SPECIFICATION.md` the normative source for board formats, file retention, and X-display review.
- Align `AGENTS.md` with the normative board and display policy.
- Add the authoring, rendering, and display steps to `.agents/skills/oxygen-plan-next-slice/SKILL.md`.
- Update `docs/UI_DEVELOPMENT_WORKFLOW.md` to direct non-terminal visual presentation to `feh` on display `:0`, while preserving installed-device evidence requirements.
- Keep the roadmap entry, plan, and cycle records consistent with the approved behavior board.

### Exclusions

- No app code, product behavior, app UI, or visual design changes.
- No changes to cycle-tracker behavior, test infrastructure, tool installation, or closed historical board packages.
- No UI artwork board is required for this documentation-only slice.
- Do not add or run app tests; the user has not authorized them and they do not establish this documentation exit.
- Do not claim installed visual acceptance from board previews or the X display.

### Approved board and constraints

Product owner approved the objective-specific behavior board on 2026-11-15. The main board is `.codex/concepts/WF.2-board-visual-policy-behavior-board.svg`; its package includes the editable `.dot` source and `.png` render. The cycle manifest records fingerprints for all three files. The board governs:

- Behavior boards retain Graphviz `.dot` source and SVG plus PNG renders.
- UI artwork boards use SVG or PNG to suit their source; retain both when they have distinct source and review roles.
- Present non-terminal visuals for user review with `feh` on X display `:0`.
- Obtain owner approval for the rendered board package and record its exact files.

No UI artwork board applies because WF.2 does not change app UI or visual output.

### Invariants

- Every slice retains its objective-specific behavior board and product-owner approval gate; slices that change app UI or visual output still require a UI artwork board.
- Visual board content, approval, and file fingerprints remain tied to the exact approved package. A changed package requires review under the existing workflow.
- The shared visual-review target is X display `:0`; commands set `DISPLAY=:0` explicitly rather than relying on the shell's default environment.
- `feh` presentation does not replace installed screenshots or slice-scoped interaction checks when visual acceptance is required.
- The policy applies to new or revised board artifacts; it does not reopen or reformat closed historical board packages.

### Implementation steps

1. Add the normative format and presentation rules to `docs/ROADMAP_SPECIFICATION.md`, including `dot -Tsvg` and `dot -Tpng` behavior-board render examples and a `DISPLAY=:0 feh` review example.
2. Summarize the policy in `AGENTS.md` and make `.agents/skills/oxygen-plan-next-slice/SKILL.md` operational: preserve the DOT source and both renders, choose SVG or PNG for UI artwork, and display the review visual before asking for board approval.
3. Align `docs/UI_DEVELOPMENT_WORKFLOW.md` with explicit X display `:0` and `feh` use; state that device captures remain the visual-acceptance evidence for installed app states.
4. Inspect the final documentation diff for consistent terminology, paths, and commands.

### Acceptance and authorized verification

- The four targeted workflow documents state the same policy without changing the existing board approval or installed-acceptance gates.
- Behavior-board instructions retain `.dot`, `.svg`, and `.png`; UI artwork instructions permit `.svg` or `.png` and keep both when the files serve different roles.
- Board preparation instructions require displaying the review artifact with `feh` at `DISPLAY=:0` before requesting owner approval.
- Run `python scripts/dev.py workflow` and `git diff --check`; inspect the final diff. No app tests, builds, or installed visual checks are authorized or needed for this documentation slice.

### Evidence and closeout

Update `.codex/test-artifacts/139-wf2-board-visual-policy/README.md` with exact commands and results. Close the slice with `.codex/history/139-wf2-board-visual-policy.md` and archive the plan at `.codex/history/plans/139-wf2-board-visual-policy.md`, recording the verification results and the documentation-only limitation.
