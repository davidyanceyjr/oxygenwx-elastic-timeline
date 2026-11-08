# Cycle plan — 119-wx30-location-timeline

State: CLOSED
Plan maturity: FINAL
Cycle ID: `119-wx30-location-timeline`
Roadmap items: WX.30.1
Objective: Establish the transient, no-write LIVE catalog handoff for result View before the one-rail selector work in WX.30.2.

## Owner-directed split and approvals

The product owner approved the rendered revision 5 behavior and UI artwork package on 2026-11-08. On the same date, the owner directed splitting the unstarted WX.30 plan at the transient View boundary. This cycle retains the original approved behavior package and fingerprints for its narrower result View and persistence contract. The UI artwork approval remains applicable to WX.30.2, whose separate cycle plan must record and review it before activation. No approved product behavior or appearance was changed by the split. The original whole-slice plan remains recoverable from repository history.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "119-wx30-location-timeline",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "WX.30.1",
      "depends_on": [
        "WX.29.3",
        "WX.26"
      ],
      "status": "DONE",
      "exit": "Focused controller and coordinator checks show a canonical result View request, validated source-only transient LIVE activation and refresh with no saved-location, selected-location, or dataset writes, including an already-saved result; failed, mismatched, and superseded loads publish no new selection or catalog; a later committed switch discards transient state and restart restores the last persisted choice; existing durable selection and saved View retain their behavior. The action is exposed for WX.30.2 without a visible selector-panel change.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WX.30-location-timeline-board-v5.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-08",
          "artifacts": [
            {
              "path": ".codex/concepts/WX.30-location-timeline-board-v5.svg",
              "sha256": "d30c612a22998930ffb6d3bf742d2b666bc718b79120af69d9b8fa7253acc16f"
            },
            {
              "path": ".codex/concepts/WX.30-location-timeline-board-v5.png",
              "sha256": "207e46033dec8adb3f45feaed7659584ff42191ce13d00ea7fecc6585fa389ad"
            },
            {
              "path": ".codex/concepts/WX.30-location-timeline-board-v5.md",
              "sha256": "dff3b52bd6a00ad41de355de7e05a76ecee10267e5a0b8f78ad42b0257ae6439"
            }
          ],
          "scope": "Approved revision 5 result View and persistence contract. Its visible Save/View artwork and rail behavior remain assigned to WX.30.2; original board approval and fingerprints carry through the owner-directed split."
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
      "outcome": "Source-only transient LIVE result View and refresh validate whole recording before publication; no saved-location or dataset writes; durable selection and restart behavior retained.",
      "evidence": [
        ".codex/test-artifacts/119-wx30-location-timeline/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-08",
    "summary": "WX.30.1 transient source-only LIVE handoff completed; result View action exposed without selector UI change; durable selection preserved.",
    "verification": [
      "python scripts/dev.py build passed; four named focused Gradle tests passed; python scripts/dev.py workflow passed; git diff --check passed. Installed UI acceptance remains WX.30.2."
    ],
    "evidence_directory": ".codex/test-artifacts/119-wx30-location-timeline/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "119-wx30-location-timeline",
  "slice_ids": [
    "WX.30.1"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/WX.30-location-timeline-board-v5.svg",
    ".codex/concepts/WX.30-location-timeline-board-v5.png",
    ".codex/concepts/WX.30-location-timeline-board-v5.md"
  ],
  "completed_gates": [
    "WX.30 behavior and UI artwork board package approved.",
    "WX.30_behavior_board_approved",
    "WX.30_ui_artwork_board_approved",
    "Revision 5 behavior and UI artwork approved with main-rail visual-match constraint; supersedes revision 4 for WX.30.",
    "WX.30 revision 5 behavior and UI artwork approved.",
    "WX.30 final plan review passed; cycle remains PLANNED and unactivated.",
    "WX.30.1 split review: original approved behavior board still covers the narrowed handoff; UI artwork is assigned to WX.30.2.",
    "WX.30.1 final plan review passed after checking controller/activity, source registry, coordinator, storage, dependency, board, and focused verification boundaries; cycle remains PLANNED.",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Use approved revision 5 as the WX.30 behavior and appearance authority; preserve the installed main-rail geometry and visual treatment from revision 4.",
    "Derive the three-letter place designation from the display name only; retain canonical place ID for identity and full place context for disambiguation.",
    "Give result View an explicit transient coordinator request and source-only LIVE loader, separate from saved-location add/select and LiveCatalogRepository.load; stage the recording in memory, reuse whole-recording identity/envelope validation and request sequencing, then publish only the current valid request. Make no saved-location, selected-location, or dataset writes, even for an already-saved result. Keep Save and saved View on durable paths, and keep transient refresh from routing through the writing LIVE repository.",
    "Keep WX.30 verification slice-local: installed controller and location-handoff checks; no project-wide contract, lint, test suite, or system matrix.",
    "The user authorized adding and running the four proposed focused automated test targets for WX.30; run only their named classes or methods.",
    "Show one place set on the rail: saved by default, submitted results after nonempty query, saved again when query is cleared.",
    "Saved place temperature is a read-only projection from each canonical location's matching saved LIVE catalog at the active timeline's exact selected instant. Resolve supported temperature using existing typed presentation semantics and display conversion; show unavailable for a missing/invalid catalog, unsupported instant, or missing temperature. Never clamp the rail query instant, activate/fetch a catalog to fill the row, or borrow another place value.",
    "WX.30 behavior board approval refreshed on 2026-11-08.",
    "WX.30 ui_artwork board approval refreshed on 2026-11-08.",
    "Product owner approved the rendered revision 5 package on 2026-11-08.",
    "Owner-directed split on 2026-11-08: cycle 119 executes transient catalog handoff as WX.30.1; the future WX.30.2 cycle handles the UI rail and selected-instant temperatures. The approved revision 5 board package remains authority for both narrower outcomes.",
    "WX.30.1 exposes a transient result View controller/activity action but does not change the visible selector panel; WX.30.2 connects approved Save/View controls. Transient failure keeps the previously published selection and catalog; transient refresh remains source-only."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Product owner approved revision 4 on 2026-11-08 without added constraints.",
    "Approved revision 4 behavior and UI artwork package recorded with content fingerprints.",
    "Reviewed and finalized the WX.30 plan against the approved revision 4 package, dependency states, affected interfaces, scope, and exit.",
    "Clarified the temporary catalog persistence boundary and display-only designation after inspecting the current repository and activation handoff.",
    "Specified a source-only, nonpersisting result View path and tightened the four authorized focused test targets around zero writes, restart before switching, and superseded requests; no board behavior or artwork changed.",
    "Audited verification scope and removed the project-wide contract check from WX.30 execution.",
    "Recorded user authorization to add and run the proposed focused rail, selector-controller, catalog-handoff, and Compose selector tests during WX.30 execution.",
    "Drafted revision 5 board package and replanned WX.30 roadmap/plan around one visible set and selected-instant saved temperatures.",
    "Reworked revision 5 artwork from the revision 4 main-view reference so controller geometry, city scene, scale, line, marker, edge treatment, and pill remain visually matched; only rail content changes.",
    "Recorded behavior board approval for WX.30.",
    "Recorded ui_artwork board approval for WX.30.",
    "Recorded owner approval and content fingerprints for revision 5 behavior and UI artwork SVG, PNG, and Markdown.",
    "Approved revision 5 package recorded with fingerprints and roadmap relationship.",
    "Reviewed and finalized WX.30 plan against approved revision 5, dependencies, read-only saved temperature path, temporary View boundary, and authorized focused verification.",
    "At owner direction, made the transient source-only LIVE handoff and exact-instant read-only temperature projection explicit using the existing selector, coordinator, storage, and presentation boundaries; retained approved scope and verification.",
    "Replanned the unstarted WX.30 outcome into ordered WX.30.1 and WX.30.2 roadmap slices without changing approved product behavior or artwork.",
    "Reviewed the split plan against the actual controller, activity, source registry, coordinator, storage, approved revision 5 board, and direct roadmap dependencies; clarified the independently checkable internal handoff and failure publication behavior.",
    "Activated WX.30.1 for execution.",
    "Closed slice WX.30.1 as DONE.",
    "WX.30.1 implementation and focused evidence completed",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/119-wx30-location-timeline/README.md",
    ".codex/history/119-wx30-location-timeline.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/119-wx30-location-timeline.md,.codex/test-artifacts/119-wx30-location-timeline/README.md,git final diff"
  ],
  "next_action": "Close cycle 119 with verification and limits"
}
```
<!-- cycle-checkpoint:end -->

## WX.30.1 bounded execution plan

### Authority and boundary

- Approved revision 5 behavior board governs result Save, transient View, durable saved View, and persistence. WX.29.3 and WX.26 are DONE dependencies.
- Implement a distinct result View action in the controller/activity handoff, coordinator, and LIVE source boundary. WX.30.1 exposes this action for WX.30.2's visible View control; it does not alter the current selector panel or claim that the action is user-accessible yet. Construct `CatalogLocation` from the result's canonical place ID, coordinates, and timezone; reject a result without canonical identity. Retain existing whole-recording identity, provenance, envelope validation, sequencing, and current-request publication gate.
- Obtain the LIVE recording in memory without `LiveCatalogRepository.load` or `CatalogDatasetStorage.save`. Keep transient refresh on this source-only route. Make no saved-location, selected-location, or dataset writes, including for an already-saved result. A failed, mismatched, or superseded request publishes nothing.
- Keep the existing durable `selectCandidate` and saved View paths intact. The future visible Save action must use the durable selection path, while the existing controller `add` remains save-only until WX.30.2 defines its UI use. A later catalog switch replaces transient state; restart restores the last persisted saved choice. Preserve selected forecast time and matching-location semantics.
- A transient request must not publish a new selected location or active catalog before successful whole-recording validation. Failure leaves the prior published selection intact, reports the error, and makes no writes. Track transient request ownership so `refreshCurrent` stays on the source-only path; ordinary committed selection and startup restoration continue to use their existing durable paths.
- Do not change the selector rail, saved temperature presentation, city scene, or artwork. WX.30.2 owns those outcomes and installed rail acceptance.

### Ordered work after activation

1. Add a source-only LIVE loader that returns an in-memory recording and cannot persist through the writing repository.
2. Add an explicit transient coordinator request with validation, request sequencing, and publish-only-current behavior. Keep transient refresh source-only and durable paths intact.
3. Expose a controller result View method and activity callback using canonical result identity, without saved-place add/select/remove or a selector-panel change. Keep the current durable result selection, saved View, and Remove paths unchanged for WX.30.2 to present as approved controls.
4. Add and run only the previously authorized named WX.30 coordinator and controller test methods relevant to this handoff. Check the controller's no-store-call result View handoff and durable result selection separately. Use Gradle `:app:testDebugUnitTest --tests` filters; fix demonstrated failures and rerun affected targets only.

### Observable exit and evidence

- Record exact focused test commands, results, and relevant logs under `.codex/test-artifacts/119-wx30-location-timeline/`. Verify valid View/refresh zero writes to saved-location, selected-location, and dataset stores; already-saved result zero writes; durable path persistence; switch-away discard and startup restore before and after switching; failed, mismatched, or superseded loads publish no new selection and persist nothing. Verify selected-instant preservation or bounded resolution through the existing coordinator time policy.
- Use `python scripts/dev.py workflow` and the scoped build as applicable. Inspect `git diff --check` and final diff. Do not claim installed rail or app-wide acceptance in this cycle.
- Only the coordinator and controller focused targets were previously authorized for this slice. The authorized rail-position and Compose selector targets move with the UI outcome to WX.30.2; they remain subject to its plan review.

### Risks and exclusions

- The existing `LiveCatalogRepository.load` writes snapshots, so result View and transient refresh must not use it.
- The existing result selection action writes saved-location state; result View needs a distinct action.
- No live provider expansion, device-location permission, official alerts, main-timeline time change, broad test suite, or system matrix.
