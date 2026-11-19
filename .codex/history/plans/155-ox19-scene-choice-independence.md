# Cycle plan — 155-ox19-scene-choice-independence

State: CLOSED
Plan maturity: FINAL
Cycle ID: `155-ox19-scene-choice-independence`
Roadmap items: OX-19
Objective: Separate in-session choice of bundled First City and Minimal Open Landscape from weather location, active catalog revision, and exact selected instant.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "155-ox19-scene-choice-independence",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-19",
      "depends_on": [
        "OX-92",
        "OX-18"
      ],
      "status": "DONE",
      "exit": "In-session switch between bundled First City and Open Landscape keeps place, catalog revision, precise instant and forecast behavior unchanged, with no switch-triggered fetch or process/Activity restart.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-19-scene-choice-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-19-scene-choice-behavior-board.dot",
              "sha256": "8ed19f04c6416ed0d3d70f883acc265147c1bb0f0823dd6bbb237ec18f52d522"
            },
            {
              "path": ".codex/concepts/OX-19-scene-choice-behavior-board.svg",
              "sha256": "e86b9323d84eb2408fa2687457083143dcb9d53baaf6d3793bb19f17c2091086"
            },
            {
              "path": ".codex/concepts/OX-19-scene-choice-behavior-board.png",
              "sha256": "d51d85585c283cda4f51f25c285193ec8f0277c8ed9869723d47c86215ced11f"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-19-scene-choice-ui-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-19-scene-choice-ui-board.svg",
              "sha256": "c302c7ddd6ba4f9ca2933391731fa9a948de4899a66edfb0ac2e6d401ce4bfe5"
            }
          ]
        }
      },
      "outcome": "In-session First City/Open Landscape choice preserves active catalog identity, exact instant, forecast facts, timeline state, and gestures; no switch fetch or restart observed. Focused tests and installed capture passed. Lint remains blocked by two pre-existing debug delegate errors.",
      "evidence": [
        ".codex/test-artifacts/155-ox19-scene-choice-independence/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-19 DONE: approved in-session First City/Minimal Open Landscape choice implemented; installed Pixel 3 XL switch and return preserved place, catalog identity, exact instant, forecast facts, timeline state, DEMO DATA label, and gestures, without observed fetch or process/Activity restart. Evidence: .codex/test-artifacts/155-ox19-scene-choice-independence/README.md.",
    "verification": [
      "workflow OK; contract OK; focused CatalogWorldChoiceTest and SharedSceneWeatherDecisionTest PASS; debug build/install and scoped screenshots PASS; git diff --check PASS. lintDebug FAIL on two pre-existing CatalogPickerDelegate StateFlowValueCalledInComposition errors at lines 159 and 234; no changed OX-19 path named. RTL, TalkBack, alternate font, reduced motion, broad system checks, release build, and full suite not run."
    ],
    "evidence_directory": ".codex/test-artifacts/155-ox19-scene-choice-independence/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "155-ox19-scene-choice-independence",
  "slice_ids": [
    "OX-19"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-19-scene-choice-behavior-board.dot",
    ".codex/concepts/OX-19-scene-choice-behavior-board.svg",
    ".codex/concepts/OX-19-scene-choice-behavior-board.png",
    ".codex/concepts/OX-19-scene-choice-ui-board.svg"
  ],
  "completed_gates": [
    "Focused world-choice and shared-composition tests passed; installed four-state capture and exact active-selection probe passed."
  ],
  "decisions": [
    "Corrected chooser text to approved Minimal Open Landscape while retaining short World control label; no board relationship changed."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Final installed chooser label aligned with approved Minimal Open Landscape wording; final live and simulated captures refreshed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/155-ox19-scene-choice-independence/README.md",
    ".codex/history/155-ox19-scene-choice-independence.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/155-ox19-scene-choice-independence.md",
    ".codex/test-artifacts/155-ox19-scene-choice-independence/README.md",
    "git final diff and status"
  ],
  "next_action": "Close cycle 155 after final workflow and diff checks."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Approved boards and dependencies

The product owner approved the behavior board `.codex/concepts/OX-19-scene-choice-behavior-board.dot` and its SVG/PNG renders, plus UI artwork `.codex/concepts/OX-19-scene-choice-ui-board.svg`, on 2026-11-18. The roster fingerprints each approved file. The boards govern the two-world switch flow, the visible World control and choice sheet, and preservation of catalog and weather state. The artwork uses illustrative weather values; it does not approve a new forecast sample. OX-92 and OX-18 are DONE and establish the shared selected-instant weather decision seam and the existing Minimal Open Landscape renderer.

## Bounded implementation

Add an in-session world choice for the active catalog screen. Present a small World control above the selected-place readout; opening it shows First City and Minimal Open Landscape, with the current choice indicated. Dismissal retains the current choice. On selection, change only the composition passed to the existing scene renderer; retain the same active catalog, location, generation/revision, precise selected instant, timeline interaction state, typed forecast presentation, source-time disclosure, and DEMO DATA status. Use the existing shared `SceneReading`/weather decision for both worlds. Keep the current First City opening choice for this slice; the OX-41 Park-default decision is future product intent and requires later Park implementation.

The existing `CatalogTimelinePresenter.scenePresentation` supplies the authoritative reading and First City composition. `CatalogTimeLabScreen` currently renders First City on the active catalog path; `CatalogOpenLandscapeScene` already accepts a `MinimalOpenLandscapeComposition`. Wire a world choice at the catalog screen boundary and compose the landscape through `SceneWeatherDecision.resolve(presentation.reading, presentation.timeline.zone)` and `MinimalOpenLandscapeTheme.compose`, using the same reading supplied to the First City path. Keep the presenter API and its callers, including the MainActivity active-catalog route and diagnostic variant, compatible; leave the fixture-only screen overload's presentation behavior intact. Keep world choice independent of `CatalogActivationCoordinator` selection and refresh methods. Do not introduce a second forecast interpreter, fetch, process/Activity restart, persistent theme selection, Settings, Park, pack storage, or entitlement.

## Acceptance and verification

The observable exit is an in-session First City → Open Landscape → First City switch with the same selected place, active catalog revision/generation, exact selected instant, forecast facts, source-time disclosure, and timeline state. Switching must not invoke a catalog fetch or restart the process/Activity. The chooser opens, indicates the current choice, selects either world, and dismisses without a change. Critical facts remain visible as text. The World control must not take ownership of timeline or central-summary gestures.

Focused slice-specific tests are authorized. Add and run focused tests for world-choice state and the shared selected-instant reading/composition boundary, including reversible selection and preserved catalog/instant values. Verify the chooser interaction and unchanged forecast text in a scoped installed check. Capture the approved First City, open chooser, selected Open Landscape, and returned First City states; compare place, catalog generation/revision, precise instant, forecast text, source-time disclosure, DEMO DATA label, and timeline state before and after. Record whether the switch caused a fetch or Activity/process restart using observable scoped evidence. Check dismissal, control semantics, and that the World target leaves timeline and central-summary gestures working. Use `python scripts/dev.py workflow`, `contract`, `test`, `build`, `lint`, `run`, and `screenshot` as applicable to these changed paths and installed states; record exact commands and results. System-wide, RTL, TalkBack, and broad environment matrices are out of scope. Do not claim installed visual acceptance without installed screenshots and scoped interaction evidence; a build or preview alone is not that evidence.

For the no-fetch claim, use a focused check at the source/coordinator boundary that counts catalog request or source fetch calls while selecting First City → Open Landscape → First City; the count must remain unchanged. Pair this with code-path inspection showing the World callbacks do not call `CatalogActivationCoordinator` request, selection, or refresh methods. For the installed no-restart claim, record the app PID with `adb shell pidof com.oxygen.weather.timeline` before and after the switch and capture a switch-window lifecycle observation (for example, scoped `adb logcat` ActivityTaskManager activity start/relaunch events or an equivalent debug-only Activity lifecycle probe). The same PID alone does not establish that the Activity was not recreated. Record the exact command, time window, observed events, and any evidence limit.

For before/after identity, use typed active-selection values (`recording` identity/revision, `viewGeneration`, and `selectedInstant.toString()`) in the focused check, and compare the installed readout, timeline, and available debug diagnostics against the four screenshots. The existing diagnostic overlay formats selected time for display and does not expose the active catalog identity or generation. For the installed exact-value comparison, use a scoped debug-only, read-only observation at `ActivityVariantDelegate.Host.activationCoordinator.state.value.active` during the switch sequence. Record one before and one after snapshot containing `location.id`, `datasetId`, `replayRecordingId`, `recording.catalog.asOf`, `recording.catalog.provenance`, `viewGeneration`, and `selectedInstant.toString()`; record the same values at the landscape state when practical. Retain the probe output with the screenshots, and compare exact strings/values rather than rounded visible labels. This probe must not call coordinator selection, request, or refresh methods, alter the presenter contract, or become release UI. The existing debug delegate can access this state through its `Host`; implementation may choose a temporary debug log or debug overlay output so long as the snapshots are unambiguous and reproducible. Use the installed screenshots and direct interactions to judge placement, chooser states, readability, and gesture ownership against the approved artwork and behavior board. Adjust layout within those approved constraints if the installed view reveals a concrete issue.

Preserve actual evidence under `.codex/test-artifacts/155-ox19-scene-choice-independence/README.md`: board fingerprints, focused check results, an explicit before/after state comparison, exact commands, any unrun checks, and limits. Closeout target is `.codex/history/155-ox19-scene-choice-independence.md`.

## Execution order and risk

1. Introduce a bounded world-choice value and in-session state on the active catalog screen without changing the catalog coordinator.
2. Render the selected existing composition from the same `SceneReading`; preserve the current First City path and existing landscape renderer.
3. Add the approved World control and chooser, then verify its choice, dismissal, and gesture boundaries.
4. Run authorized focused checks, inspect the final diff and `git diff --check`, record evidence, and close only when the OX-19 exit is met.

The main risk is accidentally coupling world choice to catalog activation or deriving a second weather interpretation. Keep the catalog presentation as the single source of forecast facts and selected time. If a concrete conflict with the approved board or roadmap exit appears, stop dependent work and record the owner question in the cycle checkpoint.
