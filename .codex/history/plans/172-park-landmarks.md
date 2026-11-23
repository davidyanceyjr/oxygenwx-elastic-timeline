# Cycle plan — 172-park-landmarks

State: CLOSED
Plan maturity: FINAL
Cycle ID: `172-park-landmarks`
Roadmap items: OX-43, OX-44
Objective: Place the Park vegetation, benches, and streetcar shelter as declared landmarks while preserving the approved Park composition and essential forecast access.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "172-park-landmarks",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-43",
      "depends_on": [
        "OX-25",
        "OX-35"
      ],
      "status": "DONE",
      "exit": "Installed Park evidence shows tree/bench placement and natural depth ordering while keeping the mural reservation, central forecast facts, and timeline unobstructed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
              "sha256": "43324b23dcf06619eff53a12cf1e7f27d2641558de46ca94bac487611b643e8b"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.svg",
              "sha256": "5c8eb954aec51f0c49aa7a6d168c687698b459cb96ecb2b9ea59d18f43cfdeda"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.png",
              "sha256": "3517693567426c893a464a23895f183002941045b9e36d3f65725055670e0f3f"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.md",
              "sha256": "61efdebcee22771c9ad2b3d9de7fe8f67c40f58ba26ea3296c93beb909d0a7f9"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
              "sha256": "cbe4bcf19e668857e890e413765d9d9da1c26e7dda510689a7325163195e8f10"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.png",
              "sha256": "dafd8e08617322a41fbb4370531615e175b97f98985f9a6a372bde3f273e1a23"
            }
          ]
        }
      },
      "outcome": "DONE: Added two stable Park-local tree landmarks behind the path and two bench landmarks with the entrance in the foreground. The focused composition test passed, the debug build was installed, and Pixel 3 XL portrait evidence shows the four landmarks while preserving the blank mural region, central forecast facts, and timeline. A short scrub changed the selection from 12:00 PM to 11:20:12 AM CDT and the condition from clear to partly cloudy without obscuring the readout.",
      "evidence": [
        ".codex/test-artifacts/172-park-landmarks/README.md",
        ".codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png",
        ".codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png"
      ]
    },
    {
      "id": "OX-44",
      "depends_on": [
        "OX-25",
        "OX-41"
      ],
      "status": "DONE",
      "exit": "Installed scene demonstrates stable shelter placement and correct basic occlusion against the path, entrance, and background while keeping the mural reservation, central forecast facts, and timeline unobstructed.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
              "sha256": "43324b23dcf06619eff53a12cf1e7f27d2641558de46ca94bac487611b643e8b"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.svg",
              "sha256": "5c8eb954aec51f0c49aa7a6d168c687698b459cb96ecb2b9ea59d18f43cfdeda"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.png",
              "sha256": "3517693567426c893a464a23895f183002941045b9e36d3f65725055670e0f3f"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-behavior-board.md",
              "sha256": "61efdebcee22771c9ad2b3d9de7fe8f67c40f58ba26ea3296c93beb909d0a7f9"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
              "sha256": "cbe4bcf19e668857e890e413765d9d9da1c26e7dda510689a7325163195e8f10"
            },
            {
              "path": ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.png",
              "sha256": "dafd8e08617322a41fbb4370531615e175b97f98985f9a6a372bde3f273e1a23"
            }
          ]
        }
      },
      "outcome": "Shelter identity, role, approved normalized frame, and behind-path/foreground layer order passed the focused composition test. Pixel 3 XL portrait capture shows stable placement with the mural reservation, central facts, and timeline visible; the scoped scrub moved selection from 12:00 PM to 11:21:15 AM CDT while preserving 48° and updating clear to partly cloudy. The approved frame leaves a small visible gap above the installed path, so the capture does not show direct overlap; the verified render order satisfies the board rule for where elements overlap.",
      "evidence": [
        ".codex/test-artifacts/172-park-landmarks/README.md",
        ".codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png",
        ".codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Completed cycle 172 with OX-43 and OX-44 recorded DONE. The focused composition test passed and the debug app was installed on the Pixel 3 XL; portrait captures and scoped timeline scrub evidence were saved for both slices. OX-44 retains the exact approved shelter frame and board fingerprints; the owner accepted the verified shelter-before-path/entrance render order under the board's conditional where-they-overlap rule. The installed screenshot has a small gap above the path and does not show direct overlap. Existing cycle 171 mural-foundation work was preserved.",
    "verification": [
      "PASS — ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest (OX-43 run; BUILD SUCCESSFUL).",
      "PASS — ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest (OX-44 run; BUILD SUCCESSFUL in 55s, 28 actionable tasks, 7 executed, 21 up-to-date).",
      "PASS after reconnection — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py run --serial 8AKY0M32X (debug APK assembled, installed successfully, and MainActivity launched). The earlier attempt failed while the device was disconnected.",
      "PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png; OX-43 portrait evidence saved and inspected.",
      "PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png; scrub capture saved.",
      "PASS — adb shell input swipe 700 2580 735 2580 500; selection changed from 12:00 PM to 11:20:12 AM CDT, condition changed from clear to partly cloudy, and 48° remained.",
      "PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png; installed shelter capture saved and inspected.",
      "PASS — adb -s 8AKY0M32X shell input swipe 700 2580 735 2580 500; selection changed from 12:00 PM to 11:21:15 AM CDT, condition changed from clear to partly cloudy, and 48° remained.",
      "PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png; scrub capture saved.",
      "PASS — git diff --check.",
      "PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py workflow (Workflow scaffold: OK).",
      "Limit — no full test suite, RTL/TalkBack, or broad device/environment matrix was run; direct shelter/path pixel overlap is not visible in the approved installed frame and is documented in the OX-44 evidence."
    ],
    "evidence_directory": ".codex/test-artifacts/172-park-landmarks/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "172-park-landmarks",
  "slice_ids": [
    "OX-43",
    "OX-44"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.svg",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.png",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.md",
    ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
    ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.png",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.dot",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.svg",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.png",
    ".codex/concepts/OX-172-park-landmarks-behavior-board.md",
    ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg",
    ".codex/concepts/OX-172-park-landmarks-ui-artwork-board.png"
  ],
  "completed_gates": [
    "OX-43_selected_as_sole_NEXT_and_OX-44_as_second_PLANNED",
    "shared_behavior_and_UI_artwork_package_rendered_and_displayed",
    "product_owner_approved_complete_shared_board_package_2026-11-22",
    "OX-43_behavior_board_approved",
    "OX-44_behavior_board_approved",
    "OX-43_ui_artwork_board_approved",
    "OX-44_ui_artwork_board_approved",
    "OX-43_draft_scope_exit_verification_and_evidence_recorded",
    "OX-44_draft_scope_exit_verification_and_evidence_recorded",
    "initial_substantive_cycle_draft_ready_for_owner_review",
    "OX-43_and_OX-44_external_dependencies_resolved_and_roadmap_statuses_confirmed",
    "reviewed_Park_composition_caller_renderer_dispatch_and_focused_test_interface",
    "reviewed_Park_local_normalized_geometry_and_shared_weather_decision_boundary",
    "all_approved_board_package_fingerprints_match_for_both_roster_slices",
    "cycle_172_full_plan_review_passed",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-44 focused composition test passed; Pixel 3 XL installed shelter capture and timeline scrub completed."
  ],
  "decisions": [
    "Map the shelter frame to the approved OX-172 UI board use-frame x=122,y=244,w=82,h=83 in its 240x570 Park viewBox, using the existing Park-local normalized geometry and landmark pass before the path.",
    "The owner selected option 1: keep the approved shelter frame and board fingerprints unchanged, and accept the verified render order (shelter before path/entrance) for the behavior board's conditional behind-path requirement. The installed frame has no direct path overlap, so the screenshot itself cannot show the path crossing the shelter."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Created and rendered the shared behavior board package: .codex/concepts/OX-172-park-landmarks-behavior-board.dot, .svg, .png, and .md.",
    "Created and rendered the portrait UI artwork board: .codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg and .png.",
    "Displayed the behavior SVG and UI artwork PNG on DISPLAY=:0 for owner review.",
    "Recorded the complete behavior board approval and fingerprints separately for OX-43 and OX-44.",
    "Recorded the complete UI artwork board approval and fingerprints separately for OX-43 and OX-44.",
    "Updated roadmap board-approval records and cycle 172 evidence targets for OX-43 and OX-44.",
    "Completed the initial substantive DRAFT with independent slice scopes, exits, proposed verification, authorization limits, exclusions, and evidence targets.",
    "Reviewed direct dependency status, Park composition callers, renderer ordering, geometry units, weather/time invariants, compatibility, and the focused test interface.",
    "Finalized cycle 172 at the REVIEW checkpoint; kept cycle state PLANNED, the roster unchanged, and implementation unactivated.",
    "Activated OX-43 for execution.",
    "Closed slice OX-43 as DONE.",
    "Closed slice OX-44 as DONE.",
    "Completed OX-44 focused composition test, successful Pixel 3 XL build/install, installed portrait capture, and scoped timeline scrub; evidence and the non-overlap limitation are recorded.",
    "Recorded OX-44 as DONE and advanced the active cycle to CLOSEOUT.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/172-park-landmarks/README.md",
    ".codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png",
    ".codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png",
    ".codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png",
    ".codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png",
    ".codex/history/172-park-landmarks.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    "docs/ROADMAP.md",
    ".codex/plans/172-park-landmarks.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkCompositionTest.kt",
    ".codex/test-artifacts/172-park-landmarks/README.md",
    ".codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png",
    ".codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png",
    ".codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png",
    ".codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png"
  ],
  "next_action": "Inspect the final cycle diff and preserved cycle 171 baseline, run git diff --check and workflow validation, then archive the plan/history with exact checks and the recorded visual-overlap limitation."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Shared objective and ordered roster

Place the Park vegetation, benches, and streetcar shelter as declared landmarks
while preserving the approved Park composition and essential forecast access.
OX-43 is the sole `NEXT` slice and is first in the roster. OX-44 is the second
`PLANNED` slice; it remains independent of OX-43. Each slice retains its own
acceptance, exit, status, evidence, and terminal outcome.

The dependencies are resolved: OX-43 depends on DONE OX-25 and OX-35; OX-44
depends on DONE OX-25 and OX-41. OX-25 supplies the backdrop, curved path, and
entrance. Cycle 171 added the mural wall, blank reserved region, and initial
portrait safe areas. OX-41 maps vegetation and benches as a path-framing group
and the coarse shelter behind the curved path. The shared composition is an
implementation grouping only: each slice keeps its own approved scope, exit,
verification, and evidence, and OX-44 does not require the OX-43 objects.

### Implementation boundary and compatibility

The current Park composition declares four ordered elements in
`LivingUrbanParkComposition.kt`; `CatalogLivingUrbanParkScene` in
`AtmosphericScene.kt` checks that pass order and dispatches the sealed geometry
variants. `CatalogTimeLabScreen.kt` is the production caller and can keep its
current composition API. Keep the existing `weatherDecision` and
`selectedInstant` contracts intact, and leave the selected-time timeline and
forecast readout implementation unchanged.

Use the Park's existing normalized local coordinate model: `ParkPoint` values
are bounded to `0..1` and the renderer maps them to the current canvas. Add
only the landmark declarations, shape geometry/bounds, and depth ordering
needed by the approved boards. Put trees and the shelter in a depth group
between the mural wall and curved path; keep benches in the foreground with
the entrance. Retain the mural as the only element with a forecast data
surface. Keep First City's 1024-by-1536 world-unit contract separate from the
Park-local normalized model, and add no viewport-camera or arbitrary 3D
transforms.

The relevant caller remains source-compatible because the new variants are
internal to the Park composition. Update the renderer's exhaustive geometry
dispatch and its ordered-pass check with the inventory. If test-source changes
are authorized, update the focused composition expectation in the same
implementation slice; otherwise leave the test source untouched and report
that assertion as unverified. No migration, serialized format, weather
projection, or public API change is required.

### OX-43 — vegetation and benches

**Scope:** Add the approved small placeholder set of two trees and two benches
to the Park composition. Give each element a stable Park-local identity and
role, logical geometry and bounds, and a placement within the declared depth
model. Follow OX-35's bounded position/anchor/bounds semantics without adding
arbitrary 3D transforms or viewport camera logic. The approved artwork board
places the trees behind the curved path and the benches in the foreground,
framing the path. Placeholder forms are within scope; finished authored
artwork is not required.

**Exit:** Installed Park evidence shows tree/bench placement and natural depth
ordering while keeping the mural wall's reserved blank region, central forecast
facts, and timeline unobstructed.

**Slice-local verification proposal:** If test-source changes are authorized,
extend the existing `LivingUrbanParkCompositionTest` to check the four added
landmark declarations alongside the retained base elements, their stable
IDs/roles, logical bounds and depth ordering, absence of a forecast data
surface on decorative landmarks, and preservation of the existing weather
decision and selected instant. On the installed Pixel 3 XL portrait state,
inspect the two trees and two benches against the path and entrance, confirm
the mural reservation, forecast facts, and timeline remain visible, and save
the slice's screenshot below. Exercise one short timeline scrub and confirm
the selected time/readout still respond and remain legible.

### OX-44 — streetcar shelter

**Scope:** Add one coarse historic streetcar shelter at the OX-41 composition
anchor, with stable identity, role, logical geometry, bounds, and layer order.
Use bounded Park-local position/anchor/bounds semantics without arbitrary 3D
transforms or viewport camera logic. The approved artwork board places it
behind the curved path, with the entrance and background establishing its
depth. The shelter remains independent of OX-43 and does not require the
finished mural artwork.

**Exit:** Installed scene demonstrates stable shelter placement and correct
basic occlusion against the path, entrance, and background while keeping the
mural wall's reserved region, central forecast facts, and timeline unobstructed.

**Slice-local verification proposal:** If test-source changes are authorized,
extend the same focused composition test to check the shelter declaration,
anchor/bounds, and its ordering behind the path, while preserving the existing
weather decision and selected instant. On the installed Pixel 3 XL portrait
state after execution checks are authorized, inspect the shelter's placement
and path/entrance/background occlusion; confirm the blank mural
reservation, central forecast facts, and timeline remain visible; save the
slice's screenshot below. Exercise one short timeline scrub and confirm the
selected time/readout still respond and remain legible. The visual review may
include OX-43 elements after that slice has landed, but OX-44's acceptance is
limited to the shelter's own declared placement and exit.

### Shared invariants and exclusions

- Preserve the selected instant, forecast/weather decision, forecast values,
  central readout, and timeline behavior. Decorative landmarks do not create
  or replace weather facts.
- Retain the OX-25 backdrop, path, and entrance, OX-42 mural plane and blank
  reserved region, and OX-85 portrait safe areas.
- Keep all landmarks clear of the mural's reserved area, essential central
  forecast facts, and timeline labels/access. Keep the shelter behind the path
  where they overlap; retain the approved path-framing composition for the
  trees and benches.
- Exclude mural readings (OX-12/OX-12A), forecast interpretation, interaction
  controls, camera adaptation, weather-state changes, finished Park artwork,
  and optional reference motifs. Broad accessibility and device/environment
  matrices remain with the dedicated TEST slices.

### Ordered execution sequence

1. **OX-43:** Add distinct stable Park-local identities and roles for two tree
   placeholders and two benches. Declare their normalized positions, anchors,
   and bounds with the approved path-framing placement. Extend the Park depth
   order so trees draw behind the path and benches draw in the foreground;
   preserve the mural wall, reserved region, path, and entrance.
2. **OX-43:** Extend the `AtmosphericScene.kt` dispatch for the new geometry,
   keeping all decorative declarations free of a data surface. If test-source
   changes are authorized, update the `LivingUrbanParkCompositionTest`
   inventory, roles, bounds, and ordering expectations while preserving the
   exact weather decision and selected instant. Without that authorization,
   leave the test file unchanged and record the focused assertion as unverified;
   do not claim a test result. Do not alter `CatalogTimeLabScreen.kt`'s call
   contract.
3. **OX-43 acceptance:** After the user authorizes execution checks, install
   the app and capture the Pixel 3 XL portrait Park state. Inspect the trees
   and benches against the path, entrance, mural reservation, central facts,
   and timeline; perform one short timeline scrub and record that selection
   and the readout still respond. Save the capture and observations at the
   OX-43 evidence paths below.
4. **OX-44:** Add one stable shelter identity and role at the OX-41 composition
   anchor, using the same Park-local normalized geometry and behind-path depth
   group. Dispatch its coarse geometry without requiring finished mural art
   or any OX-43 object behavior. Extend the same focused composition check for
   its identity, bounds, role, and order if test changes are authorized.
5. **OX-44 acceptance and closeout:** After the user authorizes execution
   checks, capture the installed portrait state and inspect shelter placement
   against the path, entrance, and background while confirming the mural
   reservation, central facts, and timeline remain visible. Perform one short
   timeline scrub and save the slice-local observation. Record actual results
   for each slice separately, update roadmap status only when its own exit is
   met or a terminal outcome is recorded, then inspect the final diff and
   close the cycle with exact verification and limitations.

If installed evidence shows that a landmark obscures an approved safe region,
has the wrong depth relationship, or interferes with selected-time/readout
access, leave that slice incomplete and record the finding and evidence. Do not
change weather or timeline semantics to compensate. This scope adds no new
network or provider failure path because it adds only deterministic Park
declarations and drawing.

### Affected state, evidence, and execution authorization

The affected state is the approved daylight Park in the Pixel 3 XL portrait
reference viewport, with the existing central forecast facts and timeline
visible. The approved UI board supplies a base view, an OX-43-only view, an
OX-44-only view, and the grouped composition. It is design guidance, not
installed acceptance evidence.

Execution evidence targets:

- `.codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png`
- `.codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png`
- `.codex/test-artifacts/172-park-landmarks/README.md` for exact checks,
  commands, results, observations, and limitations.

The plan review initially authorized planning only. On 2026-11-22, the owner
explicitly authorized the focused composition test, debug build/install, both
Pixel 3 XL portrait captures, and both scoped timeline scrubs. The OX-43 and
OX-44 focused test invocations, installed results, exact device commands, and
slice-local observations are recorded in
`.codex/test-artifacts/172-park-landmarks/README.md`. The first OX-44 install
attempt occurred while the device was disconnected and failed; after the owner
reconnected the Pixel 3 XL, the same run command installed successfully. The
owner later selected option 1 for the OX-44 path-occlusion interpretation:
retain the approved frame and fingerprints, using the verified declared layer
order as evidence for the conditional behind-path rule, while recording that
the installed artwork does not visibly overlap the path. No board artifact or
fingerprint changed. The full test suite and broad accessibility/device
matrices were not run; these remain outside the authorized slice checks.
No broad accessibility, RTL, TalkBack, or system/environment matrix is
planned for these design slices. The review-stage command
`python scripts/dev.py workflow` passed after the final plan/checkpoint update;
no implementation verification has been performed.

### Approved board package and constraints

The product owner approved the rendered shared behavior and UI artwork package
on 2026-11-22. Cycle 172 records the complete same file list and SHA-256
fingerprints separately on each roster entry. The behavior package is:

- `.codex/concepts/OX-172-park-landmarks-behavior-board.dot`
- `.codex/concepts/OX-172-park-landmarks-behavior-board.svg`
- `.codex/concepts/OX-172-park-landmarks-behavior-board.png`
- `.codex/concepts/OX-172-park-landmarks-behavior-board.md`

The UI artwork package is:

- `.codex/concepts/OX-172-park-landmarks-ui-artwork-board.svg`
- `.codex/concepts/OX-172-park-landmarks-ui-artwork-board.png`

The behavior board defines the shared invariants and separate OX-43/OX-44
flows and exits. The product owner approved the exact fingerprinted package on
2026-11-22, as recorded separately for both slices in the manifest and in
`docs/ROADMAP.md`. The artwork board makes both slices independently reviewable
and also shows their grouped composition. Its approved coarse composition is
two trees, two benches, and one low-detail shelter; the shelter and trees sit
behind the curved path where shown, while the benches are in the foreground.
The readout, timeline, and blank mural reservation stay clear. Board approval
does not claim installed acceptance, approve finished asset artwork, authorize
execution, or combine the two slice exits.

### Risks and documentation

The current Park inventory and renderer have four ordered elements: backdrop,
mural wall, curved path, and entrance. The existing focused composition test
asserts that exact inventory. If test-source changes are authorized, update
that expectation with the new declarations and renderer dispatch while
preserving the existing selected-time/weather contract. Otherwise record that
the focused assertion remains unverified and do not run it. The Park renderer
uses normalized canvas-relative points, while OX-35's numerical world-unit
example belongs to First City; the plan keeps those contracts separate. The
approved board is schematic; the Pixel 3 XL installed captures establish
actual placement and readability.

No timeline, forecast-data, public API, serialization, or device-compatibility
change is planned. No specification update is needed because weather meaning,
selected-time semantics, and interactions stay unchanged. During execution,
record actual scoped results and limitations in the cycle evidence README,
update each roadmap slice only when its exit is met or its terminal outcome is
recorded, and archive the completed cycle plan with the final evidence and
exact verification results.
