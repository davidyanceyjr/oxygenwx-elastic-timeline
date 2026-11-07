# Cycle plan — 118-wx29-location-search-refactor

State: ACTIVE
Plan maturity: FINAL
Cycle ID: `118-wx29-location-search-refactor`
Roadmap items: WX.29.1, WX.29.2, WX.29.3
Objective: Execute the approved WX.29 refactor as three dependency-ordered outcomes, preserving the original visual, behavior, and installed acceptance obligations.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "118-wx29-location-search-refactor",
  "plan_maturity": "FINAL",
  "active_slice": "WX.29.3",
  "slices": [
    {
      "id": "WX.29.1",
      "depends_on": [
        "WX.19",
        "WX.26"
      ],
      "status": "DONE",
      "exit": "Installed Pixel 3 XL portrait scene and saved rail match approved artwork; saved loading, empty, populated, unavailable/retry states and current distinction are readable; touching a saved place activates it while the picker remains open and turns its label green, without a visible Select action. Scoped responsiveness, gesture ownership, readability, screenshots, and interaction results are recorded.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
              "sha256": "1d66e041b1f2922f457e2ced8e739beb6d6aa845efaca519fedefb9f2ee35cac"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.png",
              "sha256": "1017c27b9587f3adf2bf8d174d01f21c2239dc804c2339e18bf345d312470cff"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.md",
              "sha256": "9ff319a5ba69cee040d3d09734b588e7e1099dda48fe14afee268251544c92a3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": "design_draft/location_search_ui_board.png",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": "design_draft/location_search_ui_board.png",
              "sha256": "5e7c32841b063b2dec6f63f3b2671d6ab35b0182155bc16887b467ffe133c7c1"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
              "sha256": "494c4815cde877e55e3214f4962fd7090abc761ad293e8f19014e41c0b6779d0"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.png",
              "sha256": "f8fddbe72f5c3d46d83990a8943d81724863ddad711d9031d82183be1265b548"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.md",
              "sha256": "f019fcad1488b56d497358f7e9fe9a245e861914cb87e2504e414d567240c7d8"
            }
          ]
        }
      },
      "outcome": "Full-screen city scene and saved rail implemented; installed Pixel 3 XL captures cover loading, empty, populated, unavailable/retry, touch-to-green current selection, and matching Chicago catalog handoff. Temporary debug fixture was removed; lint remains blocked by four errors outside the changed selector files; no tests were run.",
      "evidence": [
        ".codex/test-artifacts/118-wx29-location-search-refactor/README.md",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-populated-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-empty-isolated-user.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-loading-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-unavailable-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-retry-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-after-touch.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-chicago-active.png"
      ]
    },
    {
      "id": "WX.29.2",
      "depends_on": [
        "WX.29.1"
      ],
      "status": "DONE",
      "exit": "Installed Pixel 3 XL portrait query editing, explicit submit, idle/loading/matches/no-match/failure/retry, upper result row, identity/context, attribution, and persistent lower saved row match approved boards. Result selection uses existing handoff. Scoped responsiveness, gesture ownership, readability, screenshots, and interaction results are recorded.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
              "sha256": "1d66e041b1f2922f457e2ced8e739beb6d6aa845efaca519fedefb9f2ee35cac"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.png",
              "sha256": "1017c27b9587f3adf2bf8d174d01f21c2239dc804c2339e18bf345d312470cff"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.md",
              "sha256": "9ff319a5ba69cee040d3d09734b588e7e1099dda48fe14afee268251544c92a3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": "design_draft/location_search_ui_board.png",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": "design_draft/location_search_ui_board.png",
              "sha256": "5e7c32841b063b2dec6f63f3b2671d6ab35b0182155bc16887b467ffe133c7c1"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
              "sha256": "494c4815cde877e55e3214f4962fd7090abc761ad293e8f19014e41c0b6779d0"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.png",
              "sha256": "f8fddbe72f5c3d46d83990a8943d81724863ddad711d9031d82183be1265b548"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.md",
              "sha256": "f019fcad1488b56d497358f7e9fe9a245e861914cb87e2504e414d567240c7d8"
            }
          ]
        }
      },
      "outcome": "Installed Pixel 3 XL portrait query editing/explicit submit, loading, matches, no-match, connectivity failure/retry, result identity/context and attribution, persistent lower saved rail, result selection handoff, and timeline responsiveness recorded. The required loading capture used a temporary five-second debug-only delay; it was removed, and the final source was rebuilt and reinstalled. Lint remains blocked by four unrelated existing errors; no automated tests were run.",
      "evidence": [
        ".codex/test-artifacts/118-wx29-location-search-refactor/README.md",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-loading.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-matches-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-details-final.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-no-matches.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-failure.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-retry.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-result-selected.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-after-select-picker.png",
        ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-timeline-responsive.png"
      ]
    },
    {
      "id": "WX.29.3",
      "depends_on": [
        "WX.29.2"
      ],
      "status": "ACTIVE",
      "exit": "Installed Pixel 3 XL portrait full-screen removal confirmation and Cancel/Remove work; result and saved selection persist and activate matching catalog without cross-location weather; Back after opening or searching preserves current location and selected forecast instant; search remains manual with no device-location permission. Original WX.29 visual and interaction exit, scoped responsiveness, gesture ownership, readability, screenshots, and exact results are recorded.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.svg",
              "sha256": "1d66e041b1f2922f457e2ced8e739beb6d6aa845efaca519fedefb9f2ee35cac"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.png",
              "sha256": "1017c27b9587f3adf2bf8d174d01f21c2239dc804c2339e18bf345d312470cff"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-behavior-board.md",
              "sha256": "9ff319a5ba69cee040d3d09734b588e7e1099dda48fe14afee268251544c92a3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": "design_draft/location_search_ui_board.png",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-07",
          "artifacts": [
            {
              "path": "design_draft/location_search_ui_board.png",
              "sha256": "5e7c32841b063b2dec6f63f3b2671d6ab35b0182155bc16887b467ffe133c7c1"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
              "sha256": "494c4815cde877e55e3214f4962fd7090abc761ad293e8f19014e41c0b6779d0"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.png",
              "sha256": "f8fddbe72f5c3d46d83990a8943d81724863ddad711d9031d82183be1265b548"
            },
            {
              "path": ".codex/concepts/WX.29-location-search-ui-states-v2.md",
              "sha256": "f019fcad1488b56d497358f7e9fe9a245e861914cb87e2504e414d567240c7d8"
            }
          ]
        }
      },
      "outcome": null,
      "evidence": []
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
  "cycle_id": "118-wx29-location-search-refactor",
  "slice_ids": [
    "WX.29.1",
    "WX.29.2",
    "WX.29.3"
  ],
  "stage": "EXECUTE",
  "prior_cycle_state": "PLANNED",
  "accepted_boards": [
    ".codex/concepts/WX.29-location-search-behavior-board.svg",
    ".codex/concepts/WX.29-location-search-behavior-board.png",
    ".codex/concepts/WX.29-location-search-behavior-board.md",
    "design_draft/location_search_ui_board.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
    ".codex/concepts/WX.29-location-search-ui-states-v2.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.md",
    ".codex/concepts/WX.29-location-search-behavior-board.svg",
    ".codex/concepts/WX.29-location-search-behavior-board.png",
    ".codex/concepts/WX.29-location-search-behavior-board.md",
    "design_draft/location_search_ui_board.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
    ".codex/concepts/WX.29-location-search-ui-states-v2.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.md",
    ".codex/concepts/WX.29-location-search-behavior-board.svg",
    ".codex/concepts/WX.29-location-search-behavior-board.png",
    ".codex/concepts/WX.29-location-search-behavior-board.md",
    "design_draft/location_search_ui_board.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.svg",
    ".codex/concepts/WX.29-location-search-ui-states-v2.png",
    ".codex/concepts/WX.29-location-search-ui-states-v2.md"
  ],
  "completed_gates": [
    "WX.29_primary_ui_artwork_board_approved",
    "WX.29_behavior_board_approved",
    "WX.29_ui_artwork_board_approved",
    "WX.29 coupled behavior and UI boards approved",
    "WX.29 bounded plan reviewed and finalized",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner chose option 1: use a temporary debug-only search delay to capture the required installed Loading state; the fixture was removed and final source rebuilt/reinstalled.",
    "Owner request scoped this execution to WX.29.2; the approved plan requires a separate owner prompt before implementing WX.29.3."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Closed WX.29.1 as DONE with installed saved-location scene evidence.",
    "Implemented WX.29.2 upper results rail and attribution; recorded installed search states, interaction outcomes, and timeline responsiveness.",
    "Captured the required loading state with a temporary debug-only delay, removed the fixture, and rebuilt/reinstalled final source.",
    "Closed WX.29.2 as DONE."
  ],
  "evidence": [
    ".codex/test-artifacts/118-wx29-location-search-refactor/README.md",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-populated-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-empty-isolated-user.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-loading-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-unavailable-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-retry-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-after-touch.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-1-chicago-active.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-matches-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-details-final.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-no-matches.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-failure.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-retry.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-result-selected.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-loading.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-after-select-picker.png",
    ".codex/test-artifacts/118-wx29-location-search-refactor/wx29-2-timeline-responsive.png"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/118-wx29-location-search-refactor.md",
    ".codex/test-artifacts/118-wx29-location-search-refactor/README.md",
    ".codex/concepts/WX.29-location-search-behavior-board.md",
    ".codex/concepts/WX.29-location-search-ui-states-v2.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorPanel.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorController.kt",
    "app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt"
  ],
  "next_action": "Await the separate owner prompt for WX.29.3; preserve completed WX.29.2 and resume this cycle at EXECUTE without repeating its accepted gates."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Approved inputs and dependency boundary

The owner approved the behavior board and replacement v2 UI state board together on 2026-11-07. The original `design_draft/location_search_ui_board.png` remains the primary appearance. The approved package is fingerprinted in the roster above. WX.19 supplies the manual search, stable place identity, saved-location operations, and attribution; WX.26 supplies the main-screen entry and location-scoped catalog handoff. Reuse those contracts.

### In scope

- Replace the current boxed `LocationSelectorPanel` presentation with a full-screen city-scene location view using the supplied composition, search field, horizontal location rail, readable place context, and green selected-place accent.
- Use the primary artwork's two horizontal rows: provider search matches above the divider and saved places below it. The rows share the rail's visual grammar but retain separate identities and selection actions. A result may also be saved; show its saved/current meaning without conflating the two lists. When no matches are displayed, search status occupies the upper row area while the lower saved row stays available if its snapshot is known.
- Keep search explicit: query editing alone does not request results; magnifier and keyboard Search submit. Present idle, loading, matches, no matches, failure, and retry in the scene. During search status states, keep saved places on the rail and place status above it.
- Present saved-location loading, populated, empty, and unavailable/retry states without inventing saved places. Keep place identity and region/country context legible for ambiguous results, and expose provider attribution/license details.
- Selecting a result saves if needed, persists selection, closes the view, and uses the existing WX.26 catalog handoff. Touching a saved location persists its ID, activates its matching catalog, keeps the picker open, and turns the newly current place label green, per the owner's 2026-11-07 direction. No visible Select button is used for saved places. Removal asks for explicit confirmation in a full-screen scene state; Cancel leaves data intact, and Remove uses the existing repository removal policy.
- System Back exits without a committed selection, preserving the main-screen location and selected forecast instant. The target has no visible close control and does not imply outside-scrim dismissal.

### Exclusions and invariants

No provider, search result schema, saved-location schema, startup/source policy, weather renderer, forecast time projection, location permission, background location, unrelated navigation, or deprecated oxygenWX changes. UI consumes provider-neutral typed values; it does not receive provider DTOs or Room rows. Do not display another location's weather during a catalog switch. Keep the main timeline responsive and its selected instant unchanged by merely opening, closing, or searching in the location view.

### Likely affected paths

`app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorPanel.kt` owns the visual replacement and scoped semantics. `LocationSelectorController.kt` may need only narrow state/interaction changes supported by the board. `MainActivity.kt` owns the entry, dismissal, and committed-selection handoff. Inspect `CatalogTimeLabScreen.kt` only for the directly affected main-screen location control and selected-time continuity. Do not widen into the catalog implementation without observed need.

### Per-slice board relationship and execution boundary

The owner-approved behavior and UI board package was reviewed as one coupled view. WX.29.1 applies its scene, lower saved rail, and saved-state treatment; WX.29.2 applies its upper search-result rail, query states, and attribution; WX.29.3 applies its same-scene removal confirmation, selection, handoff, and Back path. Each slice uses the fingerprinted package because appearance and interactions are coupled. The original unsplit exit remains recoverable in the before-split plan artifact. No new scope is authorized. Execute and close only WX.29.1 now; WX.29.2 and WX.29.3 require separate owner prompts.

Each slice records its own installed screenshots and interaction outcomes before closure. WX.29.3 verifies the fully integrated original exit. `workflow`, `contract`, `build`, `lint`, `run`, and `screenshot` are authorized as scoped tools; tests are not authorized. A build or preview cannot replace installed evidence. Keep affected-state timeline responsiveness, predictable gestures, and readable values/labels within each slice.

### Ordered implementation and verification path

1. Replace the panel's boxed dialog/card presentation with the approved full-screen city scene. Keep the supplied search-field placement, readable selected-place detail, two horizontal location rows, divider, green current-place accent, and source/license disclosure. Compare the installed primary, populated-match, and populated-saved screenshots with the approved images; record legibility and any deviation.
2. Bind the existing controller's `Idle`, `Loading`, `Matches`, `NoMatches`, and `Failed` search states to the upper row area. Keep edit separate from submit; wire magnifier and keyboard Search to `submitSearch`, and Retry to `retrySearch`. Keep saved places in the lower row through search loading, empty, and failed states. Check the scoped submit, no-match, failure, retry, and result-selection interactions on the installed device.
3. Bind saved snapshot loading, populated, empty, and unavailable/retry states to the lower row without fabricated places. Keep current and saved meaning, full place context, and selection/removal affordances readable. Replace the removal alert with a full-screen scene confirmation; Cancel leaves the record unchanged and Remove follows the existing repository policy. Capture each state and the cancel/confirm outcomes.
4. Preserve the existing `MainActivity` entry and `LocationSelectorController` committed-selection callback to the WX.26 activation coordinator. Use system Back to dismiss without selection, close pending search work, and retain the active location and selected forecast instant. Check result and saved selection against the matching activated catalog, and check Back after opening or searching against unchanged location/time. Document exact observed results.
5. Run only authorized scoped `workflow`, `contract`, `build`, `lint`, `run`, and `screenshot` commands as applicable; do not add or run tests absent separate owner authorization. Inspect `git diff --check` and the final scoped diff before closeout. Record pass, fail, or not-run for each exit observation and link actual Pixel 3 XL portrait captures in the cycle evidence index.

The search and saved lists are already separate controller values. The controller owns request cancellation, selected-place persistence, and removal results; the panel owns their presentation. The activity already connects committed selections to location-scoped activation. Preserve those contracts; change controller or activity behavior only when an observed gap blocks the approved interaction. The existing panel's outside-scrim dismissal must not carry into the full-screen design; system Back is the approved uncommitted exit. Search provider, saved-location storage, catalog activation, and other screens require no migration in this slice.

### Slice exit and authorized verification

The roster's WX.29 exit above is controlling. Run `python scripts/dev.py workflow` at boundaries, then scoped `contract`, `build`, `lint`, `run`, and `screenshot` as applicable. Capture actual installed Pixel 3 XL portrait states for the primary target and directly affected search/saved/removal states under `.codex/test-artifacts/118-wx29-location-search-refactor/`; record interaction observations for submit/retry, result and saved selection, removal cancel/confirm, attribution, and Back preserving time. Capture matching-catalog activation and no cross-location weather on a committed switch. Compare the installed images to the approved boards and record deviations. Do not claim broad device, RTL, TalkBack, or app-wide system acceptance. Do not add or run tests without separate owner authorization under `AGENTS.md`.

### Closeout

Record exact commands, screenshots, interaction results, limitations, and any approved deviation in the evidence index. Inspect `git diff --check` and the final scoped diff. Close the slice and cycle only after its exit is met or an explicit incomplete outcome is recorded, then archive this plan and closeout under `.codex/history/`.
