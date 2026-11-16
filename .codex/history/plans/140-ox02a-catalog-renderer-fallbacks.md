# Cycle plan — 140-ox02a-catalog-renderer-fallbacks

State: CLOSED
Plan maturity: FINAL
Cycle ID: `140-ox02a-catalog-renderer-fallbacks`
Roadmap items: OX-02A
Objective: Resolve catalog renderer fallback routes with evidence-backed disposition and scoped visual parity.

**Plan state:** Final review completed against both product-owner-approved board packages, the OX-02 dependency trace, relevant product/UI authority, and current route interfaces. The owner later authorized execution and selected evidence-only closeout after the supported-rain mismatch; OX-02A is recorded `DONE` with both fallback routes retained, no renderer changes, and no parity claim. See the execution evidence and archived closeout for exact results and limits.

## Cycle roster and board approvals

The structured roster records each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval. The activation
command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "140-ox02a-catalog-renderer-fallbacks",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-02A",
      "depends_on": [
        "OX-02"
      ],
      "status": "DONE",
      "exit": "For each reading and state-only route, record its actual caller, triggering state, source location, and keep/consolidate disposition. Before any removal, show parity at the same selected instant for supported, missing, and carried facts, source-valid time and disclosure, and affected scene states; include scoped installed captures and direct interaction checks for visible changes. Record any unexercised route as unverified and retain it or give an evidence-backed reason for its disposition. Preserve the primary catalog renderer and separate preview entry points. Scoped verification results and limitations are explicit; no app-wide system acceptance is claimed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
              "sha256": "c761e6d6eb56ec4757473f3148ae59822d95111162428b9f8d813ebfaab1e6bf"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot",
              "sha256": "020addc5bda328f5b05d6a6bf4cf1a28867b657a544f2d278d4e4879cef324c9"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.png",
              "sha256": "88a5e20f0177c8f2d30325f081050a9a86e738e2d1130f7b85bd10fc07ae6194"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.md",
              "sha256": "ca1d54b451b5b1039e27cebf5a83932e8c4d178f9be631f264b68300bf2f1f20"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
              "sha256": "aebf042c6446381588ad1307c1c74bd93689ef2ed657907a7ceac80b33d81789"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.png",
              "sha256": "2f521972568fe9f110265d9c95863cbfc3e784c5ee18c374818ca492e82acb60"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.md",
              "sha256": "7db421cda3b5757a6002f959fe9d49ea4b9ae324ea525d9efb8058701c44966d"
            }
          ]
        }
      },
      "outcome": "Completed the slice through evidence-backed route dispositions. Composition remains primary. Retain the reading fallback after the installed supported-rain comparison showed rain streaks in composition but not reading at 2026-10-04T15:30:00Z; no parity claim or consolidation. Retain the state-only fallback because its scene-present caller and appearance remain unverified. No renderer code changed. Missing/carried comparisons, reading-route interaction, app tests, lint, and broad system acceptance remain unverified.",
      "evidence": [
        ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/README.md",
        ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/composition-supported.png",
        ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/reading-supported.png",
        ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/composition-selection-check.png",
        ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/reading-supported.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-15",
    "summary": "Closed cycle 140 with OX-02A DONE by evidence-backed route disposition, as directed in the owner-selected option 1. The composition route remains primary; the reading fallback remains because matched installed supported-rain captures at 2026-10-04T15:30:00Z showed rain streaks in composition but not in reading; the state-only fallback remains because its scene-present caller and appearance are unverified. No renderer changes were made and no parity claim is made. Missing-condition and carried-condition comparisons, reading-route interaction, scene-present state-only reachability, app tests, lint, and broad system acceptance remain unverified.",
    "verification": [
      "python scripts/dev.py workflow — PASS at CLOSEOUT; cycle close validated the archived state.",
      "git diff --check — PASS, exit code 0 with no output.",
      "python3 scripts/dev.py run --serial 8AKY0M32X — PASS; debug build succeeded, app installed and launched on Pixel 3 XL.",
      "Matched installed captures at 2026-10-04T15:30:00Z — selected time and facts match; composition displays rain streaks and reading does not, so supported-rain parity fails.",
      "sha256sum of the seven approved behavior and UI board artifacts — all fingerprints match the final plan manifest.",
      "python scripts/dev.py workflow — PASS after cycle archive; pointer is IDLE."
    ],
    "evidence_directory": ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "140-ox02a-catalog-renderer-fallbacks",
  "slice_ids": [
    "OX-02A"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.md",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-02A_source_callers_rechecked",
    "OX-02A_behavior_board_draft_created",
    "OX-02A_ui_artwork_board_draft_created",
    "OX-02A_initial_plan_draft_created",
    "OX-02A_behavior_board_approved",
    "OX-02A_ui_artwork_board_approved",
    "OX-02A_required_boards_approved_and_fingerprinted",
    "OX-02A_first_substantive_plan_draft_completed",
    "OX-02A_dependency_route_matrix_rechecked",
    "OX-02A_approved_board_labels_reconciled",
    "OX-02A_review_route_interfaces_rechecked",
    "OX-02A_roadmap_and_authority_reconciled",
    "OX-02A_execution_sequence_and_authorization_recorded",
    "OX-02A_final_plan_review_complete",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-02A_supported_rain_state_comparison_recorded_parity_failed",
    "OX-02A_owner_option_1_evidence_only_closeout_selected",
    "OX-02A_route_dispositions_recorded",
    "OX-02A_scope_exit_met_without_consolidation"
  ],
  "decisions": [
    "The Android test caller passes scene=null and therefore identifies the no-scene branch; the test was not run and does not demonstrate the scene-present state-only route.",
    "Proposed reading-route disposition is conditional: consolidate only if same-instant parity passes; otherwise retain.",
    "Proposed state-only disposition is retain while scene-present caller, trigger, and appearance remain unverified.",
    "OX-02A behavior board approval refreshed on 2026-11-15.",
    "OX-02A ui_artwork board approval refreshed on 2026-11-15.",
    "At plan review, board approval alone did not authorize app execution; the later owner request activated execution and authorized the plan-scoped debug build/install/capture steps. App tests remain unrun.",
    "The user confirmed both OX-02A board packages are approved inputs on 2026-11-15; their current files match the manifest fingerprints.",
    "The behavior-board source/render and companion Markdown, and the UI-board companion Markdown, retain stale draft/review-pending labels. The approved packages remain unchanged; the recorded approvals govern.",
    "The OX-02 dependency provides a static source trace. Cycle 140 later exercised the reading trigger for supported rain only; the state-only scene-present route has no production caller found and remains unverified.",
    "Review rechecked the route interfaces and callers: MainActivity uses the presentation overload; the only view-only caller found is the Android test with scene=null. This does not establish runtime reachability or appearance.",
    "The prior REVIEW stage authorized plan finalization only, and no app checks or changes occurred then. The later execution request authorized the scoped runtime comparison; no source code changes have been made.",
    "At execution closeout, retain the reading route because its exercised supported-rain state failed the approved same-instant parity gate. Missing and carried states were not compared. Retain the state-only route while its scene-present caller and appearance are unverified.",
    "The supported-rain installed comparison failed parity at 2026-10-04T15:30:00Z: composition visibly renders rain streaks while the reading fallback does not; selected facts and time label match.",
    "At the initial pause, stopped dependent route work after the first material parity failure. No app source changes; retained the reading route pending owner direction. The owner later selected evidence-only closeout and confirmed retention.",
    "State-only scene-present reachability remains unverified; the only view-only call found is the Android test with scene=null.",
    "No app tests or lint were run. Missing-condition and carried-condition route comparisons were not reached.",
    "Product owner selected option 1 on 2026-11-15: retain both fallback routes and complete evidence-only closeout; record the supported-rain mismatch and unverified states; make no renderer changes and claim no parity."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Created the OX-02A behavior board package (.dot, .svg, .png, .md) with composition-first routing, per-route dispositions, and a scoped parity gate.",
    "Created the OX-02A UI artwork package (.svg, .png, .md) showing supported, missing, carried, view-only no-scene, and unverified state-only cases.",
    "Wrote the initial bounded cycle plan from the OX-02A slice and board proposals; subsequent review completed the board and plan gates.",
    "Recorded behavior board approval for OX-02A.",
    "Recorded ui_artwork board approval for OX-02A.",
    "Updated the first substantive OX-02A plan draft from the approved behavior and UI artwork board packages.",
    "Rechecked the OX-02 route matrix and current source anchors, including the existing no-scene Android test caller.",
    "Completed the substantive OX-02A draft with route dispositions, board constraints, same-instant parity evidence, scoped verification limits, and evidence targets.",
    "Reviewed the current route interfaces and relevant product, roadmap, and UI authorities; confirmed the existing conditional disposition and no-change path satisfy the OX-02A exit.",
    "Verified every approved board-package SHA-256 fingerprint against the current files; all hashes match.",
    "Finalized the bounded execution sequence, conditional verification gates, authorization limits, and REVIEW checkpoint.",
    "Activated OX-02A for execution.",
    "Captured matched Pixel 3 XL supported-rain composition and reading-route screenshots at 2026-10-04T15:30:00Z; documented the scene mismatch and paused before further route-state work or source changes.",
    "Recorded the product owner’s option 1 decision and cleared the execution question; no renderer change authorized.",
    "Closed slice OX-02A as DONE.",
    "Recorded the owner-selected evidence-only route dispositions and explicit unverified states in the cycle evidence index.",
    "Updated the OX-02A roadmap outcome and status to DONE; both fallback routes remain and no renderer changes or parity claim are recorded.",
    "Cycle closeout summary and verification archived.",
    "Recorded the post-archive IDLE workflow validation in the cycle closeout record."
  ],
  "evidence": [
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.md",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.md",
    ".codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md",
    ".codex/plans/140-ox02a-catalog-renderer-fallbacks.md",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/README.md",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/composition-supported.png",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/reading-supported.png",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/composition-selection-check.png",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/reading-supported.xml",
    "docs/ROADMAP.md",
    ".codex/history/140-ox02a-catalog-renderer-fallbacks.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/current.md",
    ".codex/plans/140-ox02a-catalog-renderer-fallbacks.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/README.md"
  ],
  "next_action": "Cycle closed; post-archive workflow validation passed. No further action remains in this checkpoint."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, dependencies, and evidence

### Objective and scope

Resolve the reading and state-only catalog renderer fallback routes identified by OX-02. Use the completed OX-02 source trace to record each route's caller, triggering state, source location, reachability confidence, and evidence-backed keep or consolidate disposition. Preserve the composition renderer as the primary active-catalog route, preserve the view-only no-scene behavior, and keep fixture and Time Lab preview routes outside catalog fallback selection.

A reading-route consolidation is conditional on runtime exercise and same-instant parity evidence. If the reading route is not exercised or parity is not established, retain it and record the limit. The state-only scene route remains retained while its actual caller, trigger, and appearance are unverified. State-only consolidation is outside this slice; a proposed visible change there requires evidence of a real scene-present route, separate owner-approved scope, and a board update covering that appearance. A source search that finds no production caller is not enough to remove a public route. This slice may close with one or both fallbacks retained and a clear disposition; it does not require a code change when the evidence does not support one.

Any implementation requires separate owner authorization during execution and is limited to catalog route selection and directly coupled catalog call paths. Do not change scene generation, weather facts, forecast projection, catalog data, the selected-time model, or preview renderer entry points.

### Dependency record and route baseline

OX-02 is DONE and is the sole direct slice dependency. Its evidence is the static trace in `.codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md`, with closeout in `.codex/history/138-ox02-selected-instant-pipeline.md`. That trace records the selected instant flowing through the scene plan, `SceneReading`, `FirstCityComposition`, and the active catalog screen. It did not exercise any route at runtime.

| Route | Caller and trigger recorded by OX-02 | Source anchors | Current confidence and disposition |
|---|---|---|---|
| Composition, primary catalog route | MainActivity supplies the scene presentation. The composition is retained when it has no weather state or the timeline scene is present; the content selects composition first. | `MainActivity.kt`:240–248; `CatalogTimeLabScreen.kt`:104–120, 181–185; `CatalogTimelinePresentation.kt`:236–246 | Static caller and precedence are verified; runtime exercise is not. Keep primary. |
| Reading, active-catalog fallback | The presentation overload supplies `SceneReading`. A weather-bearing composition is filtered when `timeline.scene` is null, exposing the reading branch. The debug-only `sg2_null_weather` extra sets that scene to null. | `MainActivity.kt`:156, 240–247, 382; `CatalogTimeLabScreen.kt`:104–120, 186–192; `CatalogTimelinePresentation.kt`:151–156, 236–245 | Static debug path is verified; runtime exercise is unverified. Consolidate only after the approved parity gate; otherwise retain. |
| State-only, view-only overload | `CatalogTimeLabScreen(view, …)` passes null reading and composition; `view.scene` selects the state renderer only when present. OX-02 found no production caller for the view-only overload. | `CatalogTimeLabScreen.kt`:93–103, 193–200; `CatalogTimelinePresentation.kt`:115–148, 454 | Branch exists; scene-present caller, trigger, and runtime appearance are unverified. Retain while unverified. |
| View-only branch with no scene call | The existing Android test constructs a view with `scene = null` and calls the view-only overload. This selects no catalog scene renderer; the rest of the screen still renders. | `CatalogForecastBrowserErrorAccessibilityTest.kt`:30–60; `CatalogTimeLabScreen.kt`:93–103, 181–200 | Test source establishes only the no-scene input. The test was not run and is not evidence for the state-only scene route. Preserve this behavior. |
| Fixture and Time Lab previews | Debug fixture branches and TimeLabScreen call `AtmosphericScene` directly, outside catalog fallback selection. | `MainActivity.kt`:219–230, 385–433; `TimeLabScreen.kt`:134–142; `AtmosphericScene.kt`:42–52 | Static preview callers are recorded separately by OX-02. Keep outside this slice. |

Recheck source anchors and production/test callers during execution. The OX-02 trace distinguishes static route evidence from runtime exercise; preserve that distinction in all cycle records.

### Approved board packages and constraints

The product owner approved both OX-02A packages on 2026-11-15. The current files match the manifest's SHA-256 fingerprints.

- Behavior board package: `.codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot`, .svg, .png, and .md. It governs composition-first route selection, the conditional reading-route parity gate, retention of the state-only route while unverified, separation of the no-scene test caller from the scene-present state-only route, and preview separation.
- UI artwork package: `.codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg`, .png, and .md. It governs the intended appearance across supported, missing, eligible carried, view-only no-scene, and unverified scene-present states. It proposes no new controls, cards, or route-specific visual treatment.
- Some approved package text retains stale draft or review-pending labels: the behavior-board source/render and companion Markdown, and the UI-board companion Markdown. The current user confirmed these packages are approved, and the recorded fingerprints match. Treat the labels as stale; do not edit the approved files or reopen their gate in this plan.
- The approved OX-02 board is source-route reference material, not OX-02A approval. The OX-01f board is a style reference only. Board approval constrains this plan but is not runtime evidence, installed acceptance, or authorization to implement.

### Functional invariants

- The selected timestamp remains authoritative and must not move because a renderer route is selected or consolidated.
- Keep the composition renderer primary. Preserve the same selected-time facts and scene state across any proposed route consolidation.
- Present supplied facts faithfully. A missing condition remains unavailable and must not become an invented weather claim; independently supported facts remain available. An eligible carried condition retains its original source-valid time and visible disclosure while the selected instant stays at the requested time.
- Preserve the view-only no-scene behavior and keep fixture / Time Lab preview entry points separate.
- Keep important forecast facts and labels readable in the affected states. Do not add route-specific controls or decorative styling.

These invariants follow docs/SPECIFICATION.md, Required behaviors and Architecture; docs/OXYGEN_EXPERIENCE_SPECIFICATION.md §§4.1, 5.1–5.2, and 6; and the accepted OX-02A behavior and artwork boards. The OX-02A roadmap exit controls evidence and disposition. The UI development workflow limits installed acceptance to the affected states and interactions.

### Exclusions and compatibility

- No changes to catalog or fixture data, forecast projection, semantic scene generation, weather interpretation, source-time rules, selected-time behavior, provider behavior, stored data, or public API are in scope.
- No fixture / Time Lab preview changes, unrelated scene work, broad accessibility acceptance, or app-wide device/environment matrix.
- No compatibility or migration work is expected because the planned boundary changes no persisted format or public data contract. If review identifies a necessary API or data-contract change, stop and re-scope instead of including it implicitly.
- Cycle documentation is limited to the cycle evidence README, the OX-02A roadmap outcome/evidence/status when its exit is met, and cycle closeout. Recheck or update source anchors only when evidence shows they changed.

### Execution sequence, verification, and authorization

At final plan review, authorization covered plan finalization only: source/document inspection, board fingerprint verification, and `python scripts/dev.py workflow`. The owner later requested execution and authorized the plan-scoped debug build/install, supported-state route exercise, installed captures, and composition timeline-selection check. No app tests, lint, or renderer changes were authorized or performed. After the supported-rain mismatch, the owner selected evidence-only closeout; no additional route-state comparisons or implementation were undertaken.

1. Recheck production and test callers for both overloads and renderer branches. Use rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java app/src/androidTest and inspect the matching source. Record the command, caller, triggering state, source anchor, and whether the evidence is static or runtime. Treat the existing test caller with scene = null as the no-scene branch only.
2. For the reading route, use the existing debug-only sg2_null_weather trigger and compare it with the composition route at the exact same selected instant only if runtime exercise is authorized and available. Cover existing catalog inputs for a supported condition, a missing condition with any independent facts, and an eligible carried condition with its original source time and visible disclosure. Record the selected instant, condition support, independent facts, and source-valid time for each covered case; do not invent fixture facts to fill a missing case.
3. Any claimed parity or proposed reading-route consolidation requires matched installed captures of the composition reference and reading candidate for each covered state, plus direct interaction checks for selected-time and timeline-selection continuity. Check condition wording and symbol, independent facts, source-time disclosure, affected scene state, and visible selected-time readout. If parity fails, the route cannot be exercised, or required evidence is not authorized, retain the reading route and make no parity claim. Do not remove the route based on source inspection alone.
4. Search for a scene-present caller of the view-only overload. If no real caller and trigger are found, record the route as unverified and retain it. The existing no-scene test is not a substitute. If a real caller is found and a visible change outside the approved artwork states is proposed, stop and obtain an approved artwork update before that change.
5. If reading-route parity passes and the owner authorizes consolidation, limit implementation to the CatalogTimeLabScreen presentation path: retain/use the composition on the formerly filtered route and remove its SceneReading selection branch from the private screen content. Preserve composition-first precedence, the view-only overload and its state-only branch, the view-only no-scene behavior, the public renderer overloads, and all preview entry points. Do not consolidate the state-only route in this slice. If a required API/data-contract change or out-of-board visual change is identified, stop and re-scope or obtain the required board update.
6. For a code change, use only separately authorized checks. The scoped visual path is the project debug run/install entry point python3 scripts/dev.py run, followed by matched device captures through python3 scripts/dev.py screenshot --serial <device-serial> --output .codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/<name>.png and direct checks of only the affected states/interactions. Do not run or add app tests, lint, or broad accessibility/system checks without authorization. If no code or visible behavior changes, do not claim installed acceptance.
7. Save exact findings, commands, results, limitations, and any authorized installed evidence under .codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/README.md, linking each actual capture. At closeout, inspect git diff --check, record the exact result, update the OX-02A roadmap outcome/evidence/status only when its exit is met, and close the cycle with the exact evidence and limitations.

### Observable exit

For each reading and state-only route, record its caller, triggering state, source location, reachability confidence, and keep or consolidate disposition. Consolidate the reading route only if it was exercised and same-instant parity is supported by the approved states and evidence above; otherwise retain it and state why parity or reachability remains unverified. Retain the state-only route when its scene-present caller, trigger, or appearance remains unverified; never infer it is dead from a production-source search alone. State-only consolidation is outside this slice. Preserve composition-first behavior, the view-only no-scene branch, and separate preview entry points. Record actual scoped verification results and limits; make no app-wide system acceptance claim.

### Evidence, risks, and limitations

- Evidence target: `.codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/README.md`, with any authorized installed captures stored alongside it; closeout target: `.codex/history/140-ox02a-catalog-renderer-fallbacks.md`.
- The source trace is static; the reading debug path was exercised only for the supported-rain state, where installed evidence showed a scene mismatch. Missing-condition and carried-condition comparisons were not run. Shared source inputs do not establish parity for those states.
- The view-only state-only route remains distinct from the known no-scene Android test call. Do not report that test as run or as evidence of the scene-present branch.
- Source anchors can drift; recheck them during execution. Do not infer a defect or a runtime mismatch from route existence alone.
- Final plan review was limited to source/document inspection, board fingerprint verification, and workflow validation. Subsequent owner direction authorized the bounded debug build/install and supported-rain captures, but did not authorize app tests or lint. The later option 1 decision records evidence-only closeout with both fallbacks retained and no renderer changes. App tests remain opt-in under the repository instructions.
