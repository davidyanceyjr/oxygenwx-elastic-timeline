# Cycle plan — 120-wx30-one-location-rail

State: CLOSED
Plan maturity: FINAL
Cycle ID: `120-wx30-one-location-rail`
Roadmap items: WX.30.2
Objective: Implement the owner-approved single location rail, focused actions, and read-only selected-instant saved-place temperatures for WX.30.2.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "120-wx30-one-location-rail",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "WX.30.2",
      "depends_on": [
        "WX.30.1"
      ],
      "status": "DONE",
      "exit": "Installed Pixel 3 XL portrait main, saved-focused, and result-focused captures and scoped interactions show one rail matching the main controller, correct mode transitions and focus without activation, available/unavailable exact-instant saved temperatures, Save/temporary View and saved Remove/View behavior, no temporary View writes, query states, identity, attribution, Back dismissal, responsiveness, gesture ownership, and readable labels.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/WX.30-location-timeline-board-v5.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-08",
          "scope": "Revision 5 combined behavior contract governing one visible place set, query-set transitions, identity, focus without activation, selected-instant saved temperatures, and durable Save versus transient result View semantics for WX.30.2.",
          "constraints": [
            "Use canonical place identity and full context; the three-letter designation is display-only.",
            "Saved temperatures come from the matching saved LIVE catalog at the authoritative timeline instant; missing support remains explicitly unavailable.",
            "Focusing a rail entry does not activate a catalog or change the selected forecast instant.",
            "Result View uses the cycle 119 transient path and performs no saved-location, selected-location, or dataset writes."
          ],
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
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/WX.30-location-timeline-board-v5.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-08",
          "scope": "Revision 5 combined artwork governing the WX.30.2 one-set rail and its saved/result states, with the main controller as the installed visual reference.",
          "constraints": [
            "Preserve the main-view controller position, footprint, rail, center marker/stem, edge treatment, inset pill, and city scene.",
            "Keep the search input above the rail and show only one place set at a time.",
            "The board is design authority; installed Pixel 3 XL captures remain required for acceptance."
          ],
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
          ]
        }
      },
      "outcome": "Completed the catalog-route one-rail implementation and the approved installed Pixel 3 XL acceptance. Verified saved/result transitions, exact-instant available and unavailable rail temperatures, noncurrent focus without activation, durable Save and saved View, transient result View with unchanged persistence, removal Cancel/Confirm, search edit/submit/loading/no-match/failure/retry/clear, Back dismissal, attribution, and focused unit/Compose targets. Wi-Fi was restored; the final installed app has New York saved and selected. Cross-cutting accessibility, RTL, alternate font scale, reduced motion, and other-device checks remain outside this design slice.",
      "evidence": [
        ".codex/test-artifacts/120-wx30-one-location-rail/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-08",
    "summary": "WX.30.2 is DONE. Implemented the owner-approved catalog-route single rail, typed selected-instant saved temperature projection, unavailable treatment, focus separate from committed selection, durable Save and saved View, transient result View, removal confirmation, and explicit query states. Preserved the fixture selector. Installed Pixel 3 XL portrait acceptance met the slice exit. Evidence is recorded under .codex/test-artifacts/120-wx30-one-location-rail/README.md; final installed state is New York saved and selected with Wi-Fi restored.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT.",
      "python scripts/dev.py build — BUILD SUCCESSFUL in 5s; 37 tasks, 5 executed and 32 up to date.",
      "./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.ui.LocationRailProjectionPositionTest' — passed.",
      "./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.oxygen.weather.timeline.ui.LocationSelectorPanelTest — passed on Pixel 3 XL / Android 12; emitted deprecation and unnecessary non-null assertion warnings.",
      "python scripts/dev.py run --serial 8AKY0M32X — passed; APK installed and MainActivity launched after the final instrumentation run.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/120-wx30-one-location-rail/main-view-entry.png — passed.",
      "git diff --check — passed with no output.",
      "Read-only database comparison before/after result View confirmed the saved New York ID, selected New York ID, and LIVE dataset rows were unchanged; final database contains one saved/selected New York and one LIVE forecast-44d row.",
      "Limits: installed review covered Pixel 3 XL portrait only. RTL, TalkBack/accessibility services, alternate font scales, reduced-motion settings, and other devices were not checked; these remain outside this design slice.",
      "python scripts/dev.py test was started earlier and terminated before completion; it produced no result and is not counted as a cycle check."
    ],
    "evidence_directory": ".codex/test-artifacts/120-wx30-one-location-rail/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "120-wx30-one-location-rail",
  "slice_ids": [
    "WX.30.2"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/WX.30-location-timeline-board-v5.svg",
    ".codex/concepts/WX.30-location-timeline-board-v5.png",
    ".codex/concepts/WX.30-location-timeline-board-v5.md",
    ".codex/concepts/WX.30-location-timeline-board-v5.svg",
    ".codex/concepts/WX.30-location-timeline-board-v5.png",
    ".codex/concepts/WX.30-location-timeline-board-v5.md"
  ],
  "completed_gates": [
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "The final fixture selector comparison confirms fixture implementation and query behavior are unchanged."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the catalog-route single rail with identity-based focus, typed selected-instant saved temperature projection, unavailable state, and saved/result actions.",
    "Preserved the fixture selector and its previous query behavior; final file comparison differs only at the renamed entry point.",
    "Added and passed the two owner-authorized focused targets: rail projection/position and Compose selector interaction.",
    "Completed Pixel 3 XL portrait acceptance for saved/result states, transient View writes, removal, query states, retry, focus, and Back.",
    "Closed slice WX.30.2 as DONE.",
    ".codex/test-artifacts/120-wx30-one-location-rail/README.md",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/120-wx30-one-location-rail/README.md",
    ".codex/test-artifacts/120-wx30-one-location-rail/main-view-entry.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/saved-focused.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/unavailable-focused.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/search-loading.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/search-no-match.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/search-failure.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/search-retry-results.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/result-view-transient.png",
    ".codex/test-artifacts/120-wx30-one-location-rail/remove-confirmation.png",
    ".codex/history/120-wx30-one-location-rail.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/120-wx30-one-location-rail.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/concepts/WX.30-location-timeline-board-v5.md",
    "app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/SavedLocationRailTemperatureProjection.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorController.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/LocationSelectorPanel.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/FixtureLocationSelectorPanel.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt",
    "app/src/test/java/com/oxygen/weather/timeline/ui/LocationRailProjectionPositionTest.kt",
    "app/src/androidTest/java/com/oxygen/weather/timeline/ui/LocationSelectorPanelTest.kt",
    ".codex/test-artifacts/120-wx30-one-location-rail/README.md"
  ],
  "next_action": "Inspect the complete WX.30.2 scoped diff and git diff --check, run workflow validation, then archive cycle 120 with exact checks, evidence, and limits."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Cycle state and slice boundary

Cycle 119 (`119-wx30-location-timeline`) is closed with WX.30.1 DONE. Its closeout records that the transient source-only result View path is available for this slice. WX.30.2 depends on WX.30.1; the earlier WX.29.3 and WX.26 dependencies are also DONE. Cycle 120 contains only WX.30.2. The roadmap marks WX.30.2 `NEXT`; the cycle manifest keeps its slice `PLANNED`, the cycle remains `PLANNED`, and no slice is active. The roadmap's `NEXT` status records eligibility and does not activate work.

The product owner approved the combined revision 5 behavior and UI artwork package on 2026-11-08. Cycle 119 records that approval and the package fingerprints. Cycle 120 records the same unchanged package under both required board kinds. Its SVG, PNG, and Markdown hashes match the archived approval. The package governs the scope and acceptance below; its proposed states are not installed acceptance evidence.

## In scope

- Replace the separate saved and result selector rows with one main-view-style location rail. Saved places are the default set; submitting a nonempty query shows results; clearing the query restores saved places. Editing alone does not change the active set. Loading, no-match, failure, and retry states remain visible without invented entries or a second place set.
- Preserve the revision 5 visual contract: city scene and current-location header, search field above the rail, main-controller position and footprint, uninterrupted rail, center marker and stem, edge treatment, and inset pill. Keep three-letter designations as display labels only and retain canonical identity plus full place context.
- Separate rail focus from committed location selection. Tap or drag release focuses the entry under the center marker and updates the selector's readout and actions without activating a catalog or changing the main timeline's selected instant. Preserve the board's current-place and result-focus treatments.
- In saved mode, show each saved place's temperature from that canonical location's matching persisted LIVE catalog at the exact authoritative selected forecast instant. Use the existing typed forecast projection and unit conversion. Resolve the saved snapshot by location identity and the existing stored LIVE snapshot ordering; do not fetch, activate, or write a catalog for rail display. If the matching catalog or supported value does not cover the selected instant, show the approved unavailable placeholder. Never borrow the active place's value or clamp/retarget the selected instant. Results mode shows no temperature.
- Connect focused result Save to the existing durable save-and-select path so it persists the place, selected choice, and matching LIVE catalog. Connect result View to the source-only transient path completed in cycle 119; View must not write saved-location, selected-location, or dataset state. Preserve saved-place Remove confirmation and saved View behavior.
- Preserve explicit search submission, retry, provider attribution, result identity, Back dismissal, selected location/time on dismissal, and predictable gesture ownership between the rail, search input, action buttons, and main timeline.

## Exclusions

- No provider or search-contract changes, device-location permission, background location, catalog schema changes, or new weather source.
- No changes to the main timeline's selected-time policy, forecast window, catalog activation semantics, or cycle 119 transient loader/coordinator behavior beyond wiring the approved visible View action.
- No unrelated screen or deprecated oxygenWX changes. Do not add RTL, TalkBack, or broad system/environment acceptance to this design slice; those remain dedicated TEST work.

## Ordered execution

1. Inspect the existing selector panel/controller and MainActivity handoff at the saved/result focus boundary. Keep the UI's focused location distinct from persisted selection and the coordinator's active location.
2. Add or adapt a typed rail presentation for saved-location temperature. Read each location's matching stored LIVE catalog through the existing persistence boundary, validate canonical identity using existing storage semantics, and project the supported fact at the exact selected `Instant`. Keep unavailable distinct from zero and preserve the current display-unit policy.
3. Replace the two selector rails with the approved single rail and its saved/results state transitions. Keep search-status presentation, identity/context, focus behavior, and main-controller visual geometry within the board.
4. Wire focused actions: result Save through the durable path, result View through cycle 119's transient callback, and saved Remove/View through their existing semantics. Preserve the removal confirmation, Back behavior, attribution, and selected-time continuity.
5. Perform the installed acceptance below on Pixel 3 XL portrait. Compare affected states with revision 5 and the installed main-view reference, and record actual screenshots, interactions, and any deviations under the cycle 120 evidence directory.

## Installed acceptance and verification

Required screenshots and scoped interactions:

- Main-view entry and selector default show one saved rail matching the approved main controller.
- Saved mode covers a matching available temperature and an unavailable catalog/value. At least one noncurrent saved place can be focused without activating it or moving the selected instant; saved Remove/View actions are shown and exercised.
- A nonempty submitted query switches to a single result rail with full identity/context and attribution; focused results have no temperature and show Save/View. Editing without submission does not switch rails; clearing returns to saved mode. Capture the in-scope query loading, no-match, and failure/retry status states without fabricated entries.
- Save follows the durable path. View uses the transient path and creates no saved-location, selected-location, or dataset writes. Removal cancel/confirm and Back dismissal retain their approved behavior. Record failures, missing data, and unavailable temperatures explicitly.
- Rail taps and drag release focus the centered place without activation. Check responsiveness, gesture ownership, and readable values/labels in the changed states; verify the main timeline remains at the same selected instant.

Use `python scripts/dev.py workflow` at workflow boundaries, `python scripts/dev.py build` for the scoped build, and `python scripts/dev.py run` plus `python scripts/dev.py screenshot --serial <device-serial> --output .codex/test-artifacts/120-wx30-one-location-rail/<state>.png` for installed captures. Record the device, build, viewport, input, command, and result in the evidence README. A build or preview does not satisfy installed acceptance. Inspect `git diff --check` and the final scoped diff before closeout.

The 2026-11-08 WX.30 authorization covers four focused automated targets. Cycle 119 used the catalog-coordinator and selector-controller targets for WX.30.1 and explicitly transferred the rail-position and Compose-selector targets to WX.30.2. Use only those two transferred targets for this slice; do not run the full suite or broaden the target set. Installed evidence remains required regardless of automated results.

## Evidence and risks

- Planned cycle evidence: `.codex/test-artifacts/120-wx30-one-location-rail/README.md`, including installed captures and exact interaction/verification results.
- Closeout targets: `.codex/history/120-wx30-one-location-rail.md` and `.codex/history/plans/120-wx30-one-location-rail.md`.
- Multiple retained LIVE snapshots or an incomplete catalog can leave a saved place without a temperature at the chosen instant. Use the existing location/snapshot identity and ordering; present unavailable when no valid matching fact supports that exact instant. Do not fetch or activate data to fill the rail.
- A focus/selection conflation could persist or activate a place on a rail gesture. Keep focus state and the existing committed-selection/transient-View callbacks separate, and verify no unintended writes during focus or transient View.
