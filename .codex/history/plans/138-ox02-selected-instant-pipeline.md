# Cycle plan — 138-ox02-selected-instant-pipeline

State: CLOSED
Plan maturity: FINAL
Cycle ID: `138-ox02-selected-instant-pipeline`
Roadmap items: OX-02
Objective: Trace how the selected catalog instant and supported facts reach the visible frame; classify the primary composition, catalog fallbacks, the view-only no-scene branch, and separate preview routes; record each fallback caller, trigger, source location, and reachability without removing or consolidating a fallback; and save a result-derived route board as cycle evidence.

## Board approval record

Resolved on 2026-11-15: the product owner confirmed that the 2026-11-14
approval covers the exact four fingerprinted files in the manifest below. The
“Draft” and “owner review pending” labels in the Markdown, Graphviz, and SVG
are stale. The approved package was left unchanged, and all four fingerprints
were rechecked. The board acceptance gate is satisfied.

## Cycle roster and board approvals

The finalized roster below records the slice dependency order, observable exit,
approved behavior-board package, and UI artwork applicability. The activation
command checks these records before execution.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "138-ox02-selected-instant-pipeline",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-02",
      "depends_on": [
        "OX-01"
      ],
      "status": "DONE",
      "exit": "Source-backed selected-instant flow and a per-fallback record of caller, triggering state, source location, and verified or unverified reachability; record the separate view-only no-scene branch without claiming a rendered appearance; classify preview routes separately; and save an editable, rendered route board derived from the verified cycle findings. No fallback removal or consolidation.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-14",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.svg",
              "sha256": "83f14973ac7157b07f8d1e0b492391948929178c8547a70d7766e2f3e8e03236"
            },
            {
              "path": ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.png",
              "sha256": "8dcf1c43728206975651e71b8ab134bfabe325615fc52da2858cbe9d2f4a4482"
            },
            {
              "path": ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.dot",
              "sha256": "5c66c89354900e7080dbe57cd146210acf324e3463e3e3ce21cb1cf43ab736eb"
            },
            {
              "path": ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.md",
              "sha256": "27547a888c7e56a3cbec61ef801d14b1a6ebf91115d4b45a3aac8900d6b12b03"
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
      "outcome": "Recorded the source-backed selected-instant flow, composition and reading precedence, state-only fallback and view-only no-scene branch, preview paths, each route trigger and current source anchors, and static versus runtime reachability. Updated stale OX-02 route-table anchors and created the editable/rendered source-trace result board. No fallback was removed or consolidated.",
      "evidence": [
        ".codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md",
        ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot",
        ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.svg",
        ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-15",
    "summary": "OX-02 completed as a read-only source trace. The cycle recorded the selected-instant flow, composition and reading precedence, separate state-only fallback, view-only no-scene branch, preview routes, caller/trigger/source anchors, and static versus runtime confidence. Stale roadmap source anchors were corrected, and the editable/rendered result board was saved as source-trace evidence. No fallback was removed or consolidated. Runtime exercise, tests, build, lint, installation, and app screenshots were not performed.",
    "verification": [
      "PASS python scripts/dev.py workflow — Workflow scaffold: OK at ACTIVE / CLOSEOUT before closure.",
      "PASS git diff --check — exit 0 with no output.",
      "PASS dot -Tsvg .codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot -o .codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.svg — exit 0 with Graphviz 16.1.1.",
      "PASS dot -Tpng .codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot -o .codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.png — exit 0 with Graphviz 16.1.1.",
      "PASS rg -n 'CatalogTimeLabScreen\\s*\\(' app/src/main/java — two declarations; only production call at MainActivity.kt:246 uses the presentation overload.",
      "NOT RUN: app tests, build, lint, installation, runtime interaction checks, and installed screenshots; these were outside the approved OX-02 plan."
    ],
    "evidence_directory": ".codex/test-artifacts/138-ox02-selected-instant-pipeline/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "138-ox02-selected-instant-pipeline",
  "slice_ids": [
    "OX-02"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.svg",
    ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.png",
    ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.dot",
    ".codex/concepts/OX-02-selected-instant-pipeline-behavior-board.md"
  ],
  "completed_gates": [
    "OX-02_frontier_selected",
    "OX-02_ui_artwork_not_applicable",
    "OX-02_behavior_board_draft_created",
    "OX-02_state_route_separation_reviewed",
    "OX-02_plan_draft_completed",
    "OX-02_plan_review_source_boundary_checked",
    "OX-02_view_only_caller_search_reviewed",
    "OX-02_catalog_to_scene_route_reviewed",
    "OX-02_behavior_board_approval_resolved",
    "OX-02_all_required_boards_approved",
    "OX-02_plan_final_reviewed",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-02_selected_instant_flow_source_traced",
    "OX-02_fallback_routes_and_no_scene_branch_recorded",
    "OX-02_preview_routes_classified",
    "OX-02_caller_search_recorded",
    "OX-02_result_board_created_and_rendered",
    "OX-02_slice_exit_recorded"
  ],
  "decisions": [
    "The owner request to draft this board selects OX-02 as NEXT; no approval or implementation is implied.",
    "Keep OX-02A as a separate consolidation slice and retain every fallback during OX-02.",
    "The state-only route is a separate CatalogTimeLabScreen(view) overload; the active presentation overload always passes SceneReading.",
    "OX-02 behavior board approval refreshed on 2026-11-14.",
    "Product owner approval is recorded for the OX-02 behavior-board package on 2026-11-14; UI artwork remains not applicable because the slice is read-only and changes no visible output.",
    "Review initially reopened board acceptance because the Markdown, Graphviz, and SVG retained pending labels; the product owner's resolution is recorded below.",
    "On 2026-11-15, the product owner confirmed that the 2026-11-14 approval covers the exact four fingerprinted behavior-board files; the pending labels are stale, and the approved files remain unchanged.",
    "Review reconciled the duplicate stale pending-approval paragraph with the owner's recorded resolution; the approved board package and fingerprints remain unchanged.",
    "Owner requested a result-derived route board in cycle evidence, alongside the existing route matrix and README; the board must reflect verified cycle findings and retain their confidence limits.",
    "Source search found the view-only overload declaration but no production caller; production reachability remains unverified.",
    "The sg2_null_weather debug source path is statically verified; runtime exercise remains unverified.",
    "Rechecked and corrected stale route-table source anchors; no app behavior or approved scope changed."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Selected OX-02 as the sole next slice; OX-01 is DONE and OX-02A remains PLANNED.",
    "Scoped OX-02 as source-backed route tracing only; no visible output change or fallback removal, so no UI artwork board is required.",
    "Drafted and rendered the OX-02 behavior board package (.md, .dot, .svg, .png); product-owner approval and fingerprints are recorded.",
    "Review separated the view-only state route from the active presentation route because the latter always supplies SceneReading.",
    "Recorded behavior board approval for OX-02.",
    "Recorded product-owner approval of the OX-02 behavior-board package and all four file fingerprints.",
    "Completed the first bounded OX-02 plan draft with scope, invariants, exit, verification limits, evidence targets, and reachability limitations.",
    "Reviewed source boundaries and the OX-02 direct dependency; confirmed the selected-instant presenter, route precedence, no-scene view-only branch, and separate preview calls.",
    "Reviewed catalog/window scene-plan generation, selected-instant SceneReading evaluation, same-reading FirstCityComposition, and composition renderer consumption; expanded DRAFT verification/evidence to record this route.",
    "Searched app/src/main/java for CatalogTimeLabScreen calls; found only the MainActivity presentation-overload call in addition to the two declarations.",
    "Review found the roadmap TimeLabScreen preview anchor at line 137 had moved to line 134; execution must correct the route record and evidence anchors.",
    "Updated the OX-02 plan and roadmap evidence target to include an execution-derived Graphviz, SVG, and PNG route board.",
    "Finalized the OX-02 plan at REVIEW after recording owner confirmation for all four fingerprinted behavior-board files and reconciling duplicate stale pending-approval text; left the approved package unchanged and the cycle PLANNED and unactivated.",
    "Activated OX-02 for execution.",
    "Completed the selected-instant flow and route matrix in the cycle evidence README.",
    "Updated the OX-02 roadmap route table with rechecked source anchors.",
    "Created and visually inspected the editable and rendered result board.",
    "Closed slice OX-02 as DONE.",
    "OX-02 exit met and the slice was recorded DONE with all evidence paths.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.svg",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.png",
    ".codex/history/138-ox02-selected-instant-pipeline.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/current.md",
    ".codex/plans/138-ox02-selected-instant-pipeline.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "scripts/codex_cycle.py",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.svg",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.png"
  ],
  "next_action": "Inspect the complete final diff, run final authorized workflow and diff checks, record exact results, then close cycle 138 and archive its plan."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Objective and scope

Trace how the active catalog's selected instant and supported facts reach the visible scene route. Confirm the selected-instant handoff, then record each catalog renderer route or no-scene branch with its caller, triggering state, source location, and reachability disposition. Classify fixture and Time Lab preview callers separately. Reuse the roadmap's 2026-11-14 read-only route table as the starting trace, verify its source anchors against the affected code, and update that table to correct any stale anchor or route omission found during review.

This is a source-trace and documentation slice. It does not change app code, fixture data, catalog semantics, public APIs, stored data, or visible output; no compatibility or migration work applies. The source trace records supplied facts and their route without reinterpreting or correcting them.

### Dependencies and invariants

- External dependency: OX-01 is DONE; its closeout is `.codex/history/134-ox01-current-world-baseline.md`.
- The presenter resolves the selected instant once and uses that same instant for timeline projection and `SceneReading` evaluation; the `FirstCityComposition` is composed from that reading.
- The composition route remains the primary active-catalog renderer. The reading route is recorded as a fallback; the state-only route remains separately identified with its actual caller status.
- The view-only overload's `view.scene == null` branch makes no catalog scene call; record the branch without claiming a rendered appearance.
- Fixture and Time Lab previews remain separate from catalog fallbacks.
- Static caller reachability and runtime exercise are different claims. An unverified route stays explicitly unverified and is not removed by this slice.

### Approved board package

- Behavior board: product-owner approval is recorded on 2026-11-14. The four files and matching recorded SHA-256 fingerprints are in the cycle manifest above. The board governs the selected-instant handoff, composition-first route precedence, reading and separate state-only fallbacks, the view-only no-scene branch, preview separation, reachability confidence, and no fallback removal.
- UI artwork board: not required. The approved scope changes no visual output. If review or source tracing proposes a visible change, stop plan finalization and prepare an objective-specific UI artwork board for that changed scope.
- Result board: create it from the completed source trace under `.codex/test-artifacts/138-ox02-selected-instant-pipeline/`. Match the approved behavior board's visual style, but label only findings verified in this cycle. This is a derived evidence artifact, not a replacement approval board or a claim of installed UI acceptance.

### Observable exit

Meet the OX-02 roadmap exit: record a source-backed selected-instant flow; for every fallback, record caller, triggering state, source location, and verified or unverified reachability; record the separate view-only no-scene branch; classify preview routes separately; and save an editable and rendered route board derived from the completed trace. The evidence record and board must distinguish static source tracing from runtime exercise and state any unverified reachability without implying a runtime result. Remove and consolidate no fallback.

### Verification plan and authorization

1. Inspect `CatalogTimelinePresentation.kt`, `SemanticScenePlan.kt`, `FirstCityTheme.kt`, `MainActivity.kt`, `CatalogTimeLabScreen.kt`, `TimeLabScreen.kt`, and `AtmosphericScene.kt` at the approved board's and OX-02 roadmap record's source anchors. Confirm the catalog/window inputs that build the semantic plan, evaluation of the selected instant into `SceneReading`, composition from that same reading, the active presentation handoff, composition filter and renderer precedence, the no-scene branch, and direct preview calls. Record this as a source route; do not infer runtime behavior from it.
2. Run `rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java` to search the production source tree for callers of the view-only `CatalogTimeLabScreen(view, …)` overload. Record the exact command and result; do not treat no found caller as proof that the public route is impossible to exercise.
3. Recheck the debug `sg2_null_weather` trigger, the state-only present/absent branches, and the direct fixture / Time Lab preview callers. Label each disposition as statically verified, runtime exercised, or unverified.
4. Record the route matrix and exact source anchors in the evidence README. Update the OX-02 roadmap route table with the corrected `TimeLabScreen.kt` preview anchor and any confirmed route omissions.
5. Build `selected-instant-pipeline-result-board.dot` from the verified route matrix, preserving the approved behavior board's visual style and the trace's source anchors and confidence labels. Render the matching `.svg` and `.png` in the cycle evidence directory, then link all three from the README. Keep the board consistent with the route matrix; do not promote unverified routes to verified or imply runtime exercise.
6. Run `python scripts/dev.py workflow` and `git diff --check` for workflow and documentation integrity.

No app tests, build, lint, installation, installed app screenshots, or runtime interaction checks are included in this plan. Do not add or run tests unless the owner separately requests them. OX-02 makes no visual acceptance claim for the app; its result board documents source-trace findings only.

### Risks, exclusions, evidence, and limitations

- Documentation risk: source line anchors can drift. Recheck each anchor against the current source during execution and correct the route record before rendering the result board.
- Exclusions: no app code, fixture data, catalog semantics, public APIs, stored data, or visible output changes; no fallback removal or consolidation; no claim about the rendered appearance of the view-only no-scene branch. Compatibility or migration work does not apply to this read-only documentation slice.
- Verification limits: static source reachability is distinct from runtime exercise. Keep the reading fallback's debug route labeled runtime-unverified and the view-only overload's production reachability unverified unless execution obtains new evidence. No tests, build, lint, installation, installed screenshots, or runtime interaction checks are authorized by this plan.

Write the catalog-to-scene-plan-to-reading-to-composition flow with exact source anchors, the caller-search command and result, the route matrix for composition, reading, state-only, view-only no-scene, and preview paths, and static/runtime confidence in `.codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md`. Additionally save the result board and its editable/rendered package as `.codex/test-artifacts/138-ox02-selected-instant-pipeline/selected-instant-pipeline-result-board.dot`, `.svg`, and `.png`; the README must link them and identify them as source-trace evidence, not installed app screenshots. Closeout belongs in `.codex/history/138-ox02-selected-instant-pipeline.md`. These are targets until created during execution; the approved planning board and its PNG remain planning artifacts, not slice evidence.

OX-02 does not decide whether either route should be consolidated. OX-02A owns any later keep/consolidate disposition and parity work.
