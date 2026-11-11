# Cycle plan — 125-mr3-location-search-handoff

State: CLOSED
Plan maturity: FINAL
Cycle ID: `125-mr3-location-search-handoff`
Roadmap items: MR.3
Objective: Complete the saved-location LIVE activation handoff and keyboard-aware location search interactions defined by the approved MR.3 boards.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "125-mr3-location-search-handoff",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "MR.3",
      "depends_on": [
        "MR.2"
      ],
      "status": "DONE",
      "exit": "Installed and focused interaction evidence shows saved-location View leaves the selector available during loading, then dismisses it only after successful catalog activation and displays the newly active location's weather on the main view. A failed load does not trigger success dismissal; existing error feedback remains available. With the on-screen keyboard open during location search, the search field and current query remain visible and unobscured while typing. Submitting with the keyboard's Enter/search action automatically dismisses the keyboard and leaves the result/status state visible in WX.30.2's single-rail composition without a separate dismiss gesture. Search loading, matches, no-match, and failure states do not add a second saved-place rail; existing status and retry remain. Temporary result View, selection persistence, selected-time behavior, and timeline interaction remain unchanged.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/MR.3-saved-location-search-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-10",
          "artifacts": [
            {
              "path": ".codex/concepts/MR.3-saved-location-search-behavior-board.md",
              "sha256": "051f3e3dd23ea49bee2e4f4447161619a12754a959c5432be8f6870ad80fbc6a"
            },
            {
              "path": ".codex/concepts/MR.3-saved-location-search-behavior-board.svg",
              "sha256": "0f5c7a8edeb550f12f457c0fd6293befa281feb5a862a990db2ca72c64c045a4"
            },
            {
              "path": ".codex/concepts/MR.3-saved-location-search-behavior-board.png",
              "sha256": "0893c29ca4a25712cf7834fd3a937ee3df9863caaf328c172a7fd13835ad9900"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-10",
          "artifacts": [
            {
              "path": ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.md",
              "sha256": "8494082d71513755ac1c91c838a4410568b06c8fbe9fb630ab05cbcb183f26b9"
            },
            {
              "path": ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.svg",
              "sha256": "27a8555f281bd73ce8cffa0446fc5eaeff3997cec9655a8070f01f1a1e1b7ea4"
            },
            {
              "path": ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.png",
              "sha256": "32108454488dcdda4fc5e5b94e7d40c09d8b85e1d0f797ea57037d42660dbd16"
            }
          ]
        }
      },
      "outcome": "Installed MR.3 exit met on Pixel 3 XL. Saved New York View remained in the selector through loading and dismissed after catalog activation; an uncached saved Vancouver View with network unavailable left the selector open with the existing transport error and Retry forecast. Search typing, Enter/Search Matches, NoMatches, Loading, and provider Failure with Retry were captured in the single-rail composition. Temporary Boston View preserved the selected instant, selection persistence remained New York, and a timeline drag changed the selected instant. Connectivity was restored, the temporary Vancouver saved place was removed, and the original three-place saved list and New York selection were verified.",
      "evidence": [
        ".codex/test-artifacts/125-mr3-location-search-handoff/README.md",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-keyboard-open-typing.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-enter-results.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-onscreen-results.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-no-match.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-loading-fast-capture.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/search-provider-failure.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-loading.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-success.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-failure.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-selection-after-transient-view.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/temporary-result-view-success.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/timeline-before-interaction.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/timeline-after-interaction.png",
        ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-state-restored.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-10",
    "summary": "Completed MR.3 saved-location LIVE handoff, keyboard-aware search submission, one-rail status behavior, and unchanged temporary View/selection/timeline interactions. Installed evidence covers saved-location loading, success, and offline failure; search typing, matches, no-match, immediate loading, and provider failure. Android connectivity and the original New York/Florence/Puerto Rico saved-place state were restored after the focused network-unavailable run. MR.3 is recorded DONE.",
    "verification": [
      "python scripts/dev.py workflow — passed at final closeout.",
      "git diff --check — passed at final closeout.",
      "python scripts/dev.py run --serial 8AKY0M32X — debug build and install succeeded.",
      "Installed focused UI acceptance on Pixel 3 XL serial 8AKY0M32X, Android 12/API 31, portrait, 1440x2960, font scale 1.0: keyboard-open field/query, IME Enter/Search Matches and NoMatches, on-screen Search Matches, Loading, search provider failure with Retry, saved-location loading/success/failure with Retry, temporary Boston View time continuity, timeline drag, saved-selection restoration.",
      "Network-unavailable run used Android airplane-mode and Wi-Fi controls only; no app fault-injection code was used. Airplane mode was disabled, Wi-Fi enabled, and a validated active route confirmed after the run.",
      "The temporary Vancouver saved place was removed through the UI. App storage was read afterward to confirm only Florence, New York, and Puerto Rico remained saved and New York remained selected.",
      "Automated tests were not added or run, as authorized."
    ],
    "evidence_directory": ".codex/test-artifacts/125-mr3-location-search-handoff/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "125-mr3-location-search-handoff",
  "slice_ids": [
    "MR.3"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/MR.3-saved-location-search-behavior-board.md",
    ".codex/concepts/MR.3-saved-location-search-behavior-board.svg",
    ".codex/concepts/MR.3-saved-location-search-behavior-board.png",
    ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.md",
    ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.svg",
    ".codex/concepts/MR.3-saved-location-search-ui-artwork-board.png"
  ],
  "completed_gates": [
    "MR.3 approved behavior and UI artwork board gates passed.",
    "All six approved board package fingerprints match the cycle manifest.",
    "MR.2 is complete and MR.3 was the eligible NEXT roadmap slice.",
    "Product-owner single-rail clarification is recorded and WX.29 v2 references apply only where compatible.",
    "Final MR.3 plan review passed; cycle activated for execution.",
    "python scripts/dev.py workflow passed after activation, implementation edits, offline acceptance run, and closeout preparation.",
    "git diff --check passed after implementation edits and final closeout preparation.",
    "Debug build and install succeeded on Pixel 3 XL serial 8AKY0M32X.",
    "Installed keyboard-open search field/query visibility passed on Pixel 3 XL.",
    "Installed final Enter/Search handoff passed for Boston matches and no-match; on-screen Search passed for Chicago matches with the IME hidden and one result/status rail.",
    "Installed immediate Vancouver search capture displayed Searching for Vancouver; the later request returned 10 matches.",
    "Installed Vancouver search-provider failure while Android airplane mode was enabled displayed the connection failure and Retry in the one-rail selector.",
    "Saved New York View left the selector open during observed loading and dismissed only after successful activation.",
    "Saved-place View of an uncached Vancouver location while offline left the selector open with transport_failure feedback and Retry forecast visible.",
    "Temporary Boston View preserved the selected instant at handoff; a direct timeline drag changed the selected instant, confirming the timeline interaction remains active.",
    "After the offline run, Wi-Fi connectivity was restored; New York was reselected; Vancouver was removed; the original saved locations Florence, New York, Puerto Rico and New York selected-location ID were verified.",
    "All MR.3 observable exit conditions have installed evidence; no owner questions remain."
  ],
  "decisions": [
    "Product owner selected option 1 on 2026-11-10: retain the approved MR.3 acceptance and continue implementing a reliable focus/IME handoff; reverify both Matches and NoMatches. Previous failure evidence remains at search-no-match.png.",
    "Product owner authorized a focused device-level provider-unavailable run using Android airplane-mode and Wi-Fi controls only, without app fault-injection code; restore connectivity and record any unreachable state.",
    "The offline saved-location failure scenario used the existing UI to save a previously uncached Vancouver result, then invoked saved-place View while offline. The first Save action produced a main-view error; the subsequent saved-place View kept the selector open with transport-failure feedback and Retry forecast."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Selected MR.3 as the sole cycle slice: MR.2 is DONE, the prior P.2.23 then P.2.24 sequencing is complete, and MR.3 is marked NEXT.",
    "Recorded behavior board approval for MR.3.",
    "Recorded ui_artwork board approval for MR.3.",
    "Recorded product-owner approvals for the MR.3 behavior board and UI artwork board, each with its Markdown contract, editable SVG, and PNG review image fingerprinted in the cycle manifest.",
    "Completed the MR.3 plan from the approved boards, relevant authorities, named WX.29 behavior references, completed dependency record, and directly affected source seams.",
    "Resolved the saved-rail conflict using the product-owner clarification: preserve WX.30.2 single-rail behavior without restoring a parallel saved-place rail.",
    "Synchronized the MR.3 roadmap scope, exit, and board record with the one-rail clarification.",
    "Completed final MR.3 plan review against the approved boards, exit, dependency, source seams, verification authorization, and evidence requirements.",
    "Activated MR.3 for execution.",
    "Built and installed the debug app on Pixel 3 XL serial 8AKY0M32X with python scripts/dev.py run --serial 8AKY0M32X; build and install succeeded.",
    "Installed keyboard-open typing state passed: Boston and the full search field remained visible above the IME.",
    "Installed Enter/Search result state exposed an IME restoration gap: the keyboard hid during loading, then reappeared when Boston matches rendered because the search field retained focus.",
    "Implemented IME inset handling and saved-location activation-gated selector dismissal with selector-visible forecast error/retry.",
    "Captured the main-view and idle-selector context states for cycle 125.",
    "Product owner chose option 1 on 2026-11-10: clear search input focus and hide the IME on submit, retaining the query and the approved result/status behavior.",
    "Rebuilt and reinstalled after the focus handoff change; installed Enter/Search Matches and NoMatches plus on-screen Search all retained the one-rail result/status with the IME hidden.",
    "Verified saved New York activation loading and success states; confirmed saved selection persisted through temporary Boston View.",
    "Verified the temporary View selected instant at handoff and directly exercised the timeline drag, which changed the selected instant.",
    "Product owner authorized one focused Android airplane-mode/Wi-Fi provider-unavailable run and another immediate search-loading capture attempt; no app fault-injection code was used.",
    "Captured search-provider failure with the existing connection status and Retry action visible in the single-rail selector.",
    "Captured the search Loading status for Vancouver with an immediate installed-device screencap; the request later returned 10 results.",
    "Captured a saved-location LIVE activation failure for uncached Vancouver through the saved-place View action; the selector stayed open with the transport error and Retry forecast.",
    "Restored Wi-Fi, retried Vancouver successfully, restored New York as selected, removed the temporary Vancouver saved location, and verified the original three saved locations and selected ID.",
    "Updated the evidence README and execution authorization/evidence records in the bounded cycle plan.",
    "Closed slice MR.3 as DONE.",
    "Recorded MR.3 as DONE after all installed acceptance states were observed and the device/saved-location state was restored.",
    "Inspected the final scoped source and roadmap diff; final git diff --check and python scripts/dev.py workflow passed.",
    "Automated tests were not added or run, as required by the plan authorization.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/125-mr3-location-search-handoff/README.md",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-keyboard-open-typing.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-enter-results.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-onscreen-results.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-no-match.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-loading-fast-capture.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/search-provider-failure.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-loading.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-success.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-live-failure.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-selection-persisted.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-selection-after-transient-view.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/temporary-result-view-success.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/timeline-before-interaction.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/timeline-after-interaction.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-vancouver-selector-error-before-view.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-vancouver-save-offline-outcome.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/saved-location-state-restored.png",
    ".codex/test-artifacts/125-mr3-location-search-handoff/ime-focus-trace.log",
    ".codex/history/125-mr3-location-search-handoff.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md; .codex/current.md; .codex/README.md; .agents/skills/oxygen-execute-plan/SKILL.md; .codex/plans/125-mr3-location-search-handoff.md; docs/ROADMAP.md; approved MR.3 behavior and UI artwork board Markdown and PNGs; app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt; app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorPanel.kt; .codex/test-artifacts/125-mr3-location-search-handoff/README.md and referenced installed captures; final scoped git diff"
  ],
  "next_action": "Final scoped diff, git diff --check, and python scripts/dev.py workflow were reviewed and passed. Archive the completed cycle summary and exact verification results with python scripts/codex_cycle.py close."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### MR.3 — Return to the main view after saved-location View loads

**Objective:** Complete the saved-location LIVE activation handoff and keyboard-aware search interactions in the existing full-screen location selector. The approved behavior and artwork boards define the flow, visible states, and acceptance boundary.

**Verified context:** `MR.2` is DONE in cycle 122. `P.2.23` and `P.2.24` are DONE in cycles 123 and 124, completing the earlier owner-directed ordering ahead of MR.3. MR.3 is the sole NEXT slice and depends on `MR.2`. The current source routes saved-location View through `LocationSelectorController.select`, commits the location through `MainActivity`, and tracks catalog activation separately from the selector's open state. The search field already requests the IME Search action and invokes query submission; the connected-device capture cited by the board records the current field obstruction while the keyboard is open. These are planning facts, not claims that the approved exit is already met.

**Approved board package:** The product owner approved the behavior board on 2026-11-10: `.codex/concepts/MR.3-saved-location-search-behavior-board.md`, its SVG, and its PNG. The product owner approved the UI artwork board on 2026-11-10: `.codex/concepts/MR.3-saved-location-search-ui-artwork-board.md`, its SVG, and its PNG. All six files and SHA-256 fingerprints are recorded in the cycle manifest. The product owner's later clarification on 2026-11-10 is recorded below. The current-device captures named by the boards are references, not acceptance evidence.

**Scope:** Change only the existing main-app location-selector path needed to:

- keep the selector visible while a chosen saved location's LIVE catalog is activating, then dismiss it only after successful activation and show that location on the main weather view;
- keep the search field, entered query, clear affordance, and Search action visible above the on-screen keyboard while the user edits the query;
- submit a non-empty query and hide the keyboard when the IME Enter/Search action is pressed, retaining the query and showing the existing result state;
- preserve WX.30.2's single visible rail in search loading, matches, no-match, and failure states, without adding a parallel saved-place rail;
- preserve the selector and existing failure feedback when saved-location LIVE activation fails.

Expected implementation seams are `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt`, `app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorPanel.kt`, and `app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorController.kt`. Preserve the existing `ActivityVariantDelegate` search-submission dispatch unchanged; no delegate or debug-only selector changes are in scope.

**Owner clarification — search rail during status states:** On 2026-11-10, the product owner directed MR.3 to keep WX.30.2's single visible set during loading, no-match, and failure states. Do not restore a second saved-place rail alongside search status/results. WX.29 v2 supplies status, retry, and result-state references only where they fit the current one-rail composition. This clarification keeps MR.3 within the keyboard and saved-location handoff scope.

**Invariants:** Query editing alone does not submit a provider request; an empty or whitespace-only query stays idle. The visible Search control retains its existing behavior. Search results still require an explicit Save or View action; submission does not choose a result. Existing loading, matches, no-matches, recoverable failure, retry, attribution, and query-retention semantics remain on the single visible rail. Saved-location selection and persistence use the existing path. A successful handoff is tied to the chosen location's active LIVE catalog, not merely to starting selection or persisting the ID. The temporary search-result View path, selected forecast time, forecast values, and timeline interactions remain unchanged. Location search remains manual; no device-location access is added.

**Exclusions:** No provider/request contract, catalog source, saved-location schema or persistence redesign, startup policy, new location permission, forecast presentation, timeline or gesture redesign, temporary result View change, `ActivityVariantDelegate` or debug-only selector behavior, or unrelated screen redesign. Do not include broad RTL, TalkBack, font-scale, localization, or system/environment matrices. Do not alter the deprecated oxygenWX app. No product-specification change is required for this bounded UI follow-up; the slice consumes the already implemented successor location and catalog path.

**Dependencies:** `MR.2` is the only roadmap dependency and is complete. WX.30.2's approved one-set contract governs the existing selector rail. The WX.29 behavior and UI-state boards supply manual-submission, status, retry, and result-state references only where compatible with that one-rail contract; they do not require a second saved-place rail. MR.3's own approved boards narrow new work to the keyboard-visible composition and saved-location LIVE handoff. No other open roadmap dependency is introduced.

**Observable exit:** Use the exact MR.3 exit in the roadmap and manifest. Installed focused evidence must show the selector during saved-location loading, its dismissal only after successful activation with the selected location on the main view, and preservation of the selector plus existing error feedback after a failed load. With the IME open, the search field and query stay visible and unobscured while typing. Pressing Enter/Search hides the IME, submits the current non-empty query, preserves the query, and leaves the result/status state visible in the existing one-rail composition without a separate dismiss gesture. Search loading, matches, no-match, and failure states do not display a parallel saved-place rail; existing status and retry remain available where applicable. Confirm the temporary-result View path, selection persistence, selected-time behavior, and timeline interaction remain unchanged in the directly affected flow.

**Execution authorization and verification:** The product owner authorized execution by invoking `$oxygen-execute-plan` for the active MR.3 cycle. This authorizes the scoped app changes, installed build, focused Pixel 3 XL interactions, screenshots, workflow validation, and diff inspection. Automated tests were not authorized and were not added or run. `python scripts/dev.py run --serial 8AKY0M32X` builds and installs the app; `python scripts/dev.py screenshot --serial 8AKY0M32X --output <artifact-path>` captures installed states. The brief search-loading state was captured immediately with `adb exec-out screencap -p`; this timing-specific capture supplements the `dev.py screenshot` captures. Directly observe the listed transitions; a build or preview alone does not pass visual acceptance.

**Product-owner network-condition authorization:** On 2026-11-10, the product owner authorized one focused device-level provider-unavailable run using Android airplane-mode and Wi-Fi controls only, without app fault-injection code. Restore connectivity after the captures and record any state still unreachable. This authorization retains the approved acceptance criteria and does not change the board or slice scope.

**Evidence:** Actual execution evidence is stored under `.codex/test-artifacts/125-mr3-location-search-handoff/`. The README records device/profile, selected location and time, query, catalog state, steps, observations, network controls, cleanup, and limitations. Captures include keyboard-open typing, Enter/Search matches, on-screen Search matches, no-match, immediate search loading, search-provider failure, and saved-location LIVE loading, success, and failure. The cycle closeout target is `.codex/history/125-mr3-location-search-handoff.md`; archive the plan at `.codex/history/plans/125-mr3-location-search-handoff.md`.

**Risk and limitation:** Search/provider responses and LIVE activation outcomes depend on available provider data and connectivity. Record the actual input and outcome. If the failure branch cannot be reached through an existing supported path, leave that acceptance item incomplete rather than claiming it passed.

**Compatibility and documentation:** The roadmap has an exact machine-readable dependency (`MR.2`), records why MR.3 follows completion of P.2.23/P.2.24, sets MR.3 as NEXT, includes the owner's one-rail clarification, and names cycle 125's evidence and archive targets. Keep those records synchronized if the approved scope changes. No changes to the predecessor app or unrelated compatibility documentation are in scope.
