# Cycle plan — 134-ox01-current-world-baseline

State: CLOSED
Plan maturity: FINAL
Cycle ID: `134-ox01-current-world-baseline`
Roadmap items: OX-01
Objective: Baseline the current First City world with representative snapshot and transition references and a source-backed keep/change inventory

## Cycle roster and board approvals

The single-slice roster is OX-01. The approved behavior package is fingerprinted below.
UI artwork is inapplicable while this cycle only records the current baseline.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "134-ox01-current-world-baseline",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-01",
      "depends_on": [
        "SG.9",
        "SG.13",
        "SG.8",
        "MR.2",
        "WX.25"
      ],
      "status": "DONE",
      "exit": "Representative snapshot/transition reference set and a written keep/change inventory; existing scoped evidence or explicitly authorized checks and documented gaps. Provide scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.",
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
      "outcome": "Representative historical installed snapshot/transition references and source-backed keep/change/unknown inventory completed; current-route carried/unavailable and combined scrub proof explicitly UNKNOWN; no new visual or accessibility acceptance claimed",
      "evidence": [
        ".codex/test-artifacts/134-ox01-current-world-baseline/README.md",
        ".codex/test-artifacts/134-ox01-current-world-baseline/reference-set.md",
        ".codex/test-artifacts/134-ox01-current-world-baseline/keep-change-inventory.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-14",
    "summary": "OX-01 baseline complete: historical installed reference set, current static path, and keep/change/unknown inventory; current-route visual and spoken-accessibility gaps documented without claiming acceptance",
    "verification": [
      "PASS python scripts/dev.py workflow; PASS git diff --check; PASS local evidence-link resolution; read-only source and artifact inspection completed; NOT RUN tests, build, lint, install, screenshot, new interactions (not authorized)"
    ],
    "evidence_directory": ".codex/test-artifacts/134-ox01-current-world-baseline/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "134-ox01-current-world-baseline",
  "slice_ids": [
    "OX-01"
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
    "OX-01_behavior_board_approved",
    "OX-01 behavior board approved; UI artwork inapplicable to baseline-only scope",
    "OX-01 bounded plan reviewed against roadmap, product authority, source interfaces, and evidence limits",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Historical captures retain original route/revision; missing current-route states are UNKNOWN, not regressions"
  ],
  "open_questions": [],
  "completed_outputs": [
    "OX-01 evidence ledger, representative reference set, and keep/change/unknown inventory",
    "Closed slice OX-01 as DONE.",
    "OX-01 DONE with explicit current-route and accessibility evidence gaps",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/134-ox01-current-world-baseline/README.md",
    ".codex/test-artifacts/134-ox01-current-world-baseline/reference-set.md",
    ".codex/test-artifacts/134-ox01-current-world-baseline/keep-change-inventory.md",
    ".codex/history/134-ox01-current-world-baseline.md"
  ],
  "next_stage_read_set": [
    ".codex/test-artifacts/134-ox01-current-world-baseline/README.md",
    ".codex/test-artifacts/134-ox01-current-world-baseline/reference-set.md",
    ".codex/test-artifacts/134-ox01-current-world-baseline/keep-change-inventory.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Archive cycle with exact limits and verification"
}
```
<!-- cycle-checkpoint:end -->

## Objective and board relationship

Establish a traceable current-state reference for the default First City world before the Scene Engine restructuring. The approved [behavior board](../concepts/OX-01-baseline-the-current-world-behavior-board.md) defines the selected-instant → typed scene → rendered frame path and the five baseline areas: scene/frame, scrubbing, astronomy, accessibility meaning, and evidence classification. It governs the inventory and reference-set structure, not a new product behavior or appearance. Owner approval was given on 2026-11-14 as shown, with no additional constraints. Its SVG, PNG, editable DOT, and Markdown companion are the fingerprinted package in the manifest. No UI artwork board is required because this slice changes no visible output; adding a visible change would require a revised scope and approved artwork before activation.

## Scope and ordered work

1. **Evidence ledger.** Read the completed dependency histories and their directly relevant artifact indexes. For each candidate reference, record the source path, selected state/instant, device or static context, actual observation, and limitation. Reuse the strongest applicable installed records without relabeling old evidence as newly captured OX-01 acceptance.
2. **Current-state path.** Inspect only the named catalog presentation, semantic scene, First City composition, catalog renderer, and selected-time UI paths needed to tie the existing records to the current route. Record source locations and distinguish observed runtime behavior from static reachability. The detailed per-fallback reachability investigation belongs to OX-02.
3. **Representative set.** Assemble a compact index of current scene stills and transitions covering supported light/weather states, a scene change during time selection, a boundary or unavailable condition where evidence exists, and Sun/Moon cues. Include the selected timestamp and source-time status for each reference. For each board area, either cite an applicable installed record or name the exact missing observation as `UNKNOWN`. A historical capture whose route or revision does not match the current route is a dated comparison, not current installed proof. Do not synthesize a replacement state or claim visual acceptance from a source trace.
4. **Keep/change/unknown inventory.** For scene state and frame, scrubbing, astronomy, and text/semantics, classify each material item as `KEEP` (current invariant with evidence), `CHANGE LATER` (named later OX outcome or proposal only), or `UNKNOWN` (unverified or contradicted). Give every item a source/evidence link and a disposition. Distinguish a demonstrated regression from an evidence gap.
5. **Closeout.** Check that the reference set and inventory meet the roadmap exit; record exact evidence, gaps, and verification performed in the cycle history. Update the roadmap only to reflect the recorded outcome. Inspect the final diff and `git diff --check`.

## Dependencies and source set

The roadmap dependencies SG.9, SG.13, SG.8, MR.2, and WX.25 are DONE. Their completed records are starting evidence, not gates to reopen:

| Area | Existing record to assess | Known limit to preserve |
|---|---|---|
| Typed scene/composition | `.codex/history/083-sg9-typed-scene-composition.md` and its artifact README | Some categorical combinations were code-reviewed, not installed. |
| Center readout/scene relation | `.codex/history/109-sg13-skyline-anchored-readout.md` and `.codex/history/122-mr2-main-readout-raise-compact.md` | SG.13 did not install-review carried or unavailable condition; MR.2 did not assess non-center interactions. |
| Lunar cues | `.codex/history/093-sg8-lunar-events-and-phases.md` and its artifact README | Reuse exact captured states and settings; do not generalize to a system matrix. |
| Selection and browser | `.codex/history/071-wx25-hourly-timeline-acceptance.md` and its artifact README | Spoken TalkBack acceptance was cancelled and is not established. |
| Earlier baseline/scenario routes | `.codex/history/044-wx2-baseline-inventory.md`, `.codex/history/041-dfm3-debug-forecast-matrix-browser.md`, and `.codex/history/007-visual-baseline.md` when they answer an OX-01 gap | Their age and route may make them reference material rather than current installed proof. |

Read only the applicable product passages in `docs/SPECIFICATION.md` (selected-time, gaps, scene/readability invariants), `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` (weather scene, source support, text/semantics), and the scoped evidence rules in `docs/ROADMAP_SPECIFICATION.md` and `docs/UI_DEVELOPMENT_WORKFLOW.md`. The Scene Engine proposals are supporting rationale and do not supersede these authorities.

## Functional invariants and exclusions

- Exact selected time controls the timeline, readout, and scene. Scale or zoom motion does not retarget it; source bounds and missing-data rules remain visible in the baseline record.
- Supported source weather and astronomical facts retain their source identity/time. Illustrative scene transitions do not become minute-precision observations; unavailable facts and eligible carried conditions are described truthfully.
- Important forecast facts remain legible as text and represented in semantics. The baseline records what is evidenced; it does not claim broad accessibility acceptance.
- Timeline response and gesture ownership are preservation questions for later implementation, not changes in this slice.
- Exclude app code/fixture changes, renderer refactoring, new artwork, new weather rules, a fallback consolidation, and broad RTL/TalkBack/device/performance matrices. OX-02 and later slices own pipeline decisions and implementation changes.

## Acceptance and evidence

The cycle artifact directory is `.codex/test-artifacts/134-ox01-current-world-baseline/`. Produce `README.md` as an evidence index, `reference-set.md` for each snapshot/transition and its provenance, and `keep-change-inventory.md` for the classification and dispositions. The closeout record belongs at `.codex/history/134-ox01-current-world-baseline.md`, with the archived plan under `.codex/history/plans/`. A reference may point to an older artifact in place; copy only when needed for a self-contained comparison, preserving its original provenance.

The exit is met when the reference set and inventory cover each board area with source-backed observations or explicit documented gaps, and each visual claim points to an applicable installed capture in its original cycle or an authorized OX-01 capture. A missing state can receive an explicit `UNKNOWN` disposition; it cannot be counted as verified. Any conflict with existing evidence must be described and resolved within the baseline record or left as a named gap, not silently corrected in app code. If applicable current installed proof is missing, close the documentation baseline with that named limitation; propose any further capture as separately authorized follow-up rather than implying it was performed.

## Verification authorization and limits

Authorized for this planning/drafting work: read-only source and artifact inspection, `python scripts/dev.py workflow`, and `git diff --check`. During execution, documentation/source consistency checks and review of existing captures are within this documentation slice. No automated tests are authorized by the present user request. New install, screenshot, or interaction checks must be explicitly authorized before they run; if needed, the execution plan must name the exact state, device, route, command, and evidence target. Do not run `test`, `build`, `lint`, `run`, or `screenshot` merely to fill a baseline gap. Visual claims from historical installed captures must retain the original device/state and limitations. Record `PASS`, `FAIL`, or `NOT RUN` only for actual scoped checks.

## Risks and execution decisions

- Some older captures may use a route or code revision that no longer proves the current frame. The ledger must label such references historical and leave a current-proof gap if no applicable capture exists.
- Existing installed records cover different states, devices, and interactions. The reference set must not imply one end-to-end scenario was observed when it was assembled from separate cycles.
- The board's five areas bound the reference set. Any additional installed capture proposal remains a separate, explicit authorization decision and does not expand OX-01 into app changes.

This plan is final at stage `REVIEW`. The cycle remains `PLANNED`; activation and execution are separate steps.
