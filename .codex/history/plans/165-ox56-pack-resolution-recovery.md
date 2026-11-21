# Cycle plan — 165-ox56-pack-resolution-recovery

State: CLOSED
Plan maturity: FINAL
Cycle ID: `165-ox56-pack-resolution-recovery`
Roadmap items: OX-56
Objective: Safely recover at resolution from a missing or invalid already-referenced scene package by choosing the bundled free world and preserving forecast state.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "165-ox56-pack-resolution-recovery",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-56",
      "depends_on": [
        "OX-55",
        "OX-91"
      ],
      "status": "BLOCKED",
      "exit": "A scoped device-local resolution failure leaves the bundled First City baseline, exact selected weather and timeline operational with no partial scene commit or process failure.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.dot",
              "sha256": "c53c3e3dd38b37bfc77aa0badbff0838cdb22252f0fd335382a47f1d98c54d57"
            },
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.svg",
              "sha256": "3930f3eb213b642ec7873bea73ece2804988ba0ef1393ec5b9a175ed9ce3c8cc"
            },
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.png",
              "sha256": "66bddde4fd2c05acde192f0e54f8b53e4f6a1378b557c6b93ad07ad9ac54d751"
            },
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.md",
              "sha256": "6a6294c4a08a22f9a8dba1f5e5b9d50daefbb66a6baa8dd759ee99b6ba8c28b1"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.svg",
              "sha256": "490f5de47c45b46ce45cc575117c77aa3c05a31c0a62244c0c1dc943d2f59bfb"
            },
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.png",
              "sha256": "6607190dfa828eed681e9523efcee9e611d99a9ad486a17fbf77a4a994329606"
            },
            {
              "path": ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.md",
              "sha256": "c0b72fe88087fe26c33dde65505799f59944e67ebda3c7d70fade4f4bd980cc0"
            }
          ]
        }
      },
      "outcome": "OX-56 is BLOCKED because OX-32 has not yet supplied the in-session installed-package resolution path required to reach the approved already-referenced-package failure; no OX-56 implementation or acceptance was performed.",
      "evidence": [
        ".codex/test-artifacts/165-ox56-pack-resolution-recovery/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-20",
    "summary": "Cycle 165 closed with OX-56 BLOCKED because OX-32 is unfinished and the current device-local flow has no in-session resolution path for an already-referenced installed package. The approved OX-56 scope and board packages remain preserved for re-entry after OX-32 is DONE. OX-31 remains NEXT.",
    "verification": [
      "python scripts/dev.py workflow — PASS at ACTIVE/CLOSEOUT; closeout command validated the resulting IDLE pointer.",
      "git diff --check — PASS before closeout.",
      "No OX-56 implementation, tests, build, installation, device interaction, or installed acceptance was performed; the slice remains BLOCKED, not DONE."
    ],
    "evidence_directory": ".codex/test-artifacts/165-ox56-pack-resolution-recovery/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "165-ox56-pack-resolution-recovery",
  "slice_ids": [
    "OX-56"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.dot",
    ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.svg",
    ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.png",
    ".codex/concepts/OX-56-pack-resolution-recovery-behavior-board.md",
    ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.svg",
    ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.png",
    ".codex/concepts/OX-56-pack-resolution-recovery-ui-board.md"
  ],
  "completed_gates": [
    "OX-56_scope_and_external_dependencies_confirmed",
    "OX-56_behavior_board_approved",
    "OX-56_ui_artwork_board_approved",
    "all_slice_boards_approved",
    "owner-directed OX-56 BLOCKED outcome recorded"
  ],
  "decisions": [
    "Owner selected the current bundled First City baseline as OX-56’s device-local fallback; this does not revise OX-41’s future Living Urban Park free-default decision.",
    "Product owner approved both OX-56 board packages on 2026-11-20; cycle manifest fingerprints every package file.",
    "Owner approved World: First City as sufficient recovery feedback; add no cause text, banner, dialog, or blocking state.",
    "Preserve the First City Baseline proof option as debug-only; the approved fallback is an internal recovery outcome, not a new chooser option.",
    "Owner scope/sequence decision (2026-11-20): preserve the approved already-referenced-package resolution trigger and sequence OX-56 after OX-32 creates an in-session installed-package resolution path. Do not substitute a failed new chooser candidate or import-time rejection.",
    "Repository-grounded board-record resolution: all seven OX-56 board-package fingerprints match the files on disk. The board and companion-file headers retain proposal-stage review-pending wording, while the cycle manifest and roadmap record owner approval on 2026-11-20 and the resolved World: First City feedback decision. Treat the headers as stale proposal-stage annotations; preserve the exact approved package files and their recorded approvals.",
    "Owner directed on 2026-11-20 to close cycle 165 with OX-56 BLOCKED until OX-32 is DONE; preserve the approved trigger, fallback, and board packages."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Recorded OX-56 as the sole NEXT slice, verified OX-55 and OX-91 as DONE, assigned cycle 165 evidence paths, and drafted bounded scope and dependency constraints.",
    "Completed SLICE intake for OX-56: the sole NEXT slice has resolved dependencies, exact evidence paths, bounded scope, and the owner-selected First City baseline fallback target.",
    "Created and rendered the behavior and UI artwork board proposals; owner approval and package fingerprints remained pending at that stage.",
    "Recorded behavior board approval for OX-56.",
    "Recorded ui_artwork board approval for OX-56.",
    "Product owner approved both OX-56 board packages as shown on 2026-11-20; cycle manifest records SHA-256 fingerprints for every package file.",
    "Advanced the planning cycle from BOARDS to DRAFT after saving the approved boards and resolving the recovery-feedback question.",
    "Completed the first substantive OX-56 bounded draft with approved scope, dependencies, state invariants, acceptance, evidence targets, and compatibility limits.",
    "Reviewed the current OX-55 implementation and dependency record: the only olive resolution is initiated by a new chooser selection from bundled app assets, and failures do not commit a new world. No already-referenced package failure path exists in the current device-local flow.",
    "Recorded the owner's preserve-trigger/resequence decision and the OX-32 sequence gate without changing OX-56's approved trigger, fallback, invariants, acceptance, or exclusions.",
    "Resolved the board-record discrepancy by verifying all approved package hashes and documenting that plan/roadmap approval records supersede stale proposal-stage status annotations in the unchanged board files.",
    "Focused oxygen-review-plan pass confirmed the owner decision and board-record resolution are scoped correctly; no product scope or acceptance changes are needed. OX-32 remains a sequence gate, so the cycle stays at DRAFT.",
    "Recorded OX-32 as the concrete OX-56 blocker and documented the terminal BLOCKED outcome and re-entry condition.",
    "Advanced directly to CLOSEOUT without activating or implementing OX-56.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/165-ox56-pack-resolution-recovery/README.md",
    ".codex/history/165-ox56-pack-resolution-recovery.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/165-ox56-pack-resolution-recovery.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/165-ox56-pack-resolution-recovery/README.md"
  ],
  "next_action": "Archive cycle 165 with OX-56 BLOCKED; keep OX-31 as the sole NEXT roadmap slice."
}
```
<!-- cycle-checkpoint:end -->

## SLICE intake — OX-56

The owner selected OX-56 as the sole global NEXT item on 2026-11-20 after
OX-55 closed. The external dependencies OX-55 and OX-91 are DONE. The cycle
contains only OX-56; later installation, catalog, settings, and Park outcomes
remain separate roadmap work.

The roadmap scope is to recover during resolution when an already-referenced
package is missing or invalid, by choosing the current bundled First City
baseline without changing forecast state. Import-time rejection remains owned
by OX-91/OX-57. The observable exit requires the First City baseline, exact
selected weather, and timeline to remain operational, with no partial scene
commit or process failure.

The OX-55 closeout establishes a complete-candidate/atomic-commit boundary:
failed validation, compatibility, dimension, budget, or decode checks do not
replace the committed world or visible scene. OX-91 returns a complete
validated artifact or bounded rejection without a partial asset set. OX-19
establishes that world choice is independent of place, catalog revision, and
exact selected instant, with no switch-triggered fetch or restart. The OX-41
role decision identifies Living Urban Park as the future bundled free
default; Park is not implemented in the current OX-55 device flow, which
exposes First City and Open Landscape. The owner selected the current bundled
First City baseline as this slice's fallback target on 2026-11-20; this
slice-local target does not revise OX-41's future product role decision.

## Final plan and terminal outcome — OX-56

### Approved boards and owner decisions

The product owner approved both board packages on 2026-11-20. The cycle
manifest records SHA-256 fingerprints for every approved artifact:

- Behavior package: `.codex/concepts/OX-56-pack-resolution-recovery-behavior-board.dot`,
  `.svg`, `.png`, and `.md`.
- UI artwork package: `.codex/concepts/OX-56-pack-resolution-recovery-ui-board.svg`,
  `.png`, and `.md`.

The approved package hashes were checked against all seven files and match.
The board render headers and companion Markdown files still carry their
proposal-stage "owner review pending" text. The manifest and roadmap are the
current approval records: they record both approvals and the resolved feedback
decision dated 2026-11-20. The pending labels are stale proposal metadata; the
approved board content and fingerprints remain unchanged, so no board gate is
reopened.

The approved behavior board governs recovery only when a previously referenced
package is missing or rejected as invalid during scene resolution. It keeps
the existing successful resolution path intact, routes the failure to the
complete bundled First City baseline, and preserves the forecast session
without partial scene/asset state or process failure. Import-time rejection
and a failed new chooser candidate keep their OX-91/OX-57 and OX-55 ownership.

The approved UI artwork uses the existing World control to identify the
selected fallback as `World: First City`. The owner approved that label as
sufficient feedback: add no cause text, banner, dialog, or blocking recovery
state. The Chicago readings and Friday 2:37 PM selection shown in the board
are reference values from the OX-55 proof capture, not fixed acceptance data.
The owner-selected First City target is local to OX-56 and does not revise
OX-41's future Living Urban Park free-default decision.

### Bounded scope and dependencies

- **In scope:** resolve an already-referenced package that is missing or
  invalid; discard any incomplete candidate; select and commit the complete
  bundled First City baseline; preserve the exact selected instant, place,
  active catalog revision, supplied forecast facts and availability, and
  timeline state and operation; keep the forecast readout operational.
- **Out of scope:** import-time schema/resource validation, package extraction,
  installation, persistent catalog changes, relaunch restoration, Settings,
  entitlement, new chooser behavior, broader pack switching, a new recovery
  message/state, and Living Urban Park artwork or implementation.
- **Dependencies:** OX-55 and OX-91 are DONE external dependencies. OX-55's
  complete-candidate/atomic-commit boundary applies to scene activation;
  OX-91 supplies a complete validated result or bounded rejection and does
  not expose partial assets. OX-19's established scene-choice invariants
  constrain this fallback: world selection does not change place, catalog,
  selected time, forecast facts, trigger a forecast fetch, or restart the
   process. These inherited rules constrain OX-56 without expanding its direct
   roadmap dependencies.
- **Owner blocker decision (2026-11-20):** Preserve the approved
  already-referenced-package trigger. OX-32 is the first roadmap slice that
  resolves an installed catalog package during an in-session world switch.
  The owner directed cycle 165 to close with OX-56 BLOCKED until OX-32 is
  DONE. Record OX-32 as a direct dependency before resuming OX-56 review; do
  not redirect OX-56 to failed new-chooser or import-time paths.

### Proposed behavior and presentation contract

When resolution of a previously referenced package returns missing or invalid,
OX-56 selects the bundled First City baseline as one complete scene choice.
Any incomplete candidate is discarded; no failed candidate asset or scene
state becomes visible. The recovery leaves the selected forecast instant,
place, active catalog revision, all supplied forecast values and their
availability, timeline scale and selected position, and timeline operation
unchanged. The app remains in the same process and does not fetch forecast
data as a side effect. A successfully resolved package continues through the
existing OX-55 path.

The only recovery-specific visible indication is the selected world name in
the existing control, `World: First City`. The forecast facts remain visible
as text and the timeline remains usable. No new explanation is displayed;
no reference-capture values become hard-coded product inputs.

### Implementation boundary and compatibility

The inspected OX-55 device-local path in
`app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`
keeps the selected world in transient screen state and loads the olive archive
from bundled app assets through `OlivePackCandidate.kt` when that world is
chosen. The First City baseline drawable is present in the renderer, while its
`Baseline proof` chooser option is debug-only. Keep that proof option out of
normal chooser visibility; OX-56's approved fallback is an internal recovery
outcome, not a new selectable pack or Settings/catalog feature. Do not alter
the OX-55 failed-new-chooser behavior, which retains the previous committed
world.

Repository review confirms the current OX-55 path has no persistent
scene-package catalog, relaunch reference, or later resolution call for an
already-referenced package. `CatalogTimeLabScreen.kt` keeps `CatalogWorldChoice`
in transient Compose `remember` state and invokes `OlivePackCandidateLoader.load`
only when the user newly selects the bundled olive option; a failed load does
not commit a new world. This is a chooser failure and remains outside the
approved OX-56 trigger. OX-31 and OX-57 are still planned catalog/install
roadmap work and are not available references in this cycle.

The owner resolved the scope/sequence decision on 2026-11-20: preserve the
approved already-referenced-package trigger and sequence OX-56 after OX-32
creates the first in-session resolution path for an installed catalog
package. Do not revise the approved trigger, add persistence, or substitute a
new chooser failure. Keep the cycle at DRAFT until OX-32 completes; then record
OX-32 as a direct dependency and resume review.

No manifest, archive, asset-validation, weather, or forecast-time contract
change is in scope. OX-56 changes only the already-referenced resolution
outcome. The experience specification's stable selected-time rule applies,
and installed review follows the UI workflow's affected-state capture and
direct-interaction requirement. Update only directly affected runtime
documentation if implementation reveals that the existing pack-resolution
contract does not describe this fallback.

### Proposed execution order

1. Wait until OX-32 supplies the in-session resolution path for an installed
   catalog package; then record OX-32 as a completed direct dependency and
   resume OX-56 review with the approved trigger unchanged.
2. Route only those bounded resolution failures to the complete bundled First
   City baseline. Keep candidate/scene publication whole and preserve the
   approved existing `World: First City` feedback.
3. Verify the pre-failure and recovered forecast state against the approved
   invariants, and directly check that the timeline remains operable.
4. After focused verification is authorized, capture the installed fallback
   state, record each exercised failure branch and exact result, and update
   only directly affected runtime documentation if needed.

### Risks and controls

- **Failure-path conflation:** treating a failed new chooser candidate or an
  import-time rejection as OX-56 recovery would cross the approved boundary.
  Keep the entry conditions distinct and record which resolution branch was
  exercised.
- **Forecast-state drift:** fallback must not retarget the instant, place,
  catalog, facts, or timeline. Compare the actual state before and after the
  recovery and retain the installed evidence.
- **No current re-resolution seam:** confirmed in `CatalogTimeLabScreen.kt`,
  `OlivePackCandidate.kt`, and the OX-55 cycle record. OX-32 is the recorded
  sequence gate; do not add persistence or change scope to manufacture a path.

### Exit and planned acceptance

The OX-56 exit is met only when a scoped installed resolution-failure state
shows the complete bundled First City baseline under the existing World
control, while the exact pre-failure forecast state remains visible and the
timeline remains operational, with no partial scene commit or process
failure. The approved behavior board includes both missing and invalid
resolution branches; execution evidence should identify the branch exercised
and preserve evidence for each branch that can be triggered through the
bounded device-local flow. Record any unexercised branch explicitly rather
than implying it passed.

For installed acceptance, capture the affected fallback state and directly
check that the selected-time forecast values and place remain unchanged and
that the timeline can still be operated after recovery. Compare actual values
before and after the failure; do not require the sample Chicago/Friday values
from the artwork. Keep this slice-local: app-wide accessibility, RTL, and
system/environment matrices belong to designated TEST slices.

Cycle 165 closes with a terminal BLOCKED outcome. No implementation, build,
test, installation, device interaction, or acceptance capture was performed.
The blocker evidence and re-entry condition are recorded at
`.codex/test-artifacts/165-ox56-pack-resolution-recovery/README.md` and in
`.codex/history/165-ox56-pack-resolution-recovery.md`. Focused verification
authorization remains required before any future execution.

### Plan status

This final plan preserves the product-owner-approved OX-56 boards, trigger,
fallback presentation, state-preservation obligations, branch-specific
evidence limits, and slice-local installed acceptance boundary. Repository
review found no current already-referenced installed-package resolution path,
and OX-32 remains PLANNED. The owner directed a terminal BLOCKED outcome for
cycle 165. No OX-56 implementation or acceptance is claimed.
