# Cycle plan — 168-ox56-pack-resolution-recovery

State: CLOSED
Plan maturity: FINAL
Cycle ID: `168-ox56-pack-resolution-recovery`
Roadmap items: OX-56
Objective: Recover safely at resolution from a missing or invalid already-referenced installed scene package by selecting the complete bundled First City baseline while preserving forecast state.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "168-ox56-pack-resolution-recovery",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-56",
      "depends_on": [
        "OX-55",
        "OX-91",
        "OX-32"
      ],
      "status": "DONE",
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
      "outcome": "Both approved missing and invalid resource resolution branches fell back to complete bundled First City through same-process re-selection of the live installed entry; forecast snapshot, request and source counts remained unchanged, the Activity and PID stayed the same, the timeline remained operable, focused verification passed, and the original installed resource bytes and pointer were restored.",
      "evidence": [
        ".codex/test-artifacts/168-ox56-pack-resolution-recovery/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Completed OX-56 installed pack resolution recovery. Same committed installed entries now fall back to bundled First City when their resources are missing or inadmissible, while failed new choices and superseded requests remain unchanged. Exact baseline bytes were restored and SHA-256 verified after device fault injection.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT before cycle close.",
      "git diff --check — passed.",
      "./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.ui.CatalogWorldChoiceTest' — passed, 7 tests, 0 failures, 0 errors.",
      "python scripts/dev.py run --serial 8AKY0M32X — debug build, install, and launch passed on Pixel 3 XL.",
      "Installed device acceptance — missing and invalid baseline assets both recovered to bundled First City; OX-05 forecast snapshot remained unchanged, timeline scrub remained operative, same Activity/PID remained alive, and crash log was empty.",
      "Exact asset restoration — extracted baseline archive member independently and confirmed byte equality and SHA-256 480dd857a7aa4ed7d3da62c991cf04056a364fbf04554027d9f6af6628791c89; current-generation pointer remained byte-identical."
    ],
    "evidence_directory": ".codex/test-artifacts/168-ox56-pack-resolution-recovery/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "168-ox56-pack-resolution-recovery",
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
    "Current-generation pointer and live compatible catalog registration remained present during the missing-resource test; the missing file was the sole mutation.",
    "Focused CatalogWorldChoiceTest class passed: 7 tests, 0 failures, 0 errors.",
    "Missing-resource resolution fell back to complete bundled First City while preserving the active OX-05 snapshot, request/source counts, Activity, and process.",
    "Invalid-content resolution fell back through the same registered installed entry; timeline scrub remained operable and crash/lifecycle evidence stayed clean.",
    "Validated archive bytes were restored to the same generation and verified against the original SHA-256; current-generation pointer remained byte-identical.",
    "CatalogWorldChoiceTest passed 7 tests with 0 failures and 0 errors."
  ],
  "decisions": [
    "Owner selected restoring the exact baseline asset bytes from the validated archive into the same current generation before testing invalid content.",
    "Owner selected cold-launching the same OX-05 fixture intent through its existing activity-create path after warm delivery did not activate the fixture."
  ],
  "open_questions": [],
  "completed_outputs": [
    "OX05 forecast fixture cold-launched through the existing activity-create path; baseline pack installed and committed in the same process.",
    "Missing-resource acceptance passed: deleting only the declared asset caused request-current fallback to World: First City with unchanged forecast snapshot, request/source counts, Activity record, and process PID.",
    "Attempted OX57 restoration of the baseline pack; the live installer returned WRITE_FAILURE because its existing current generation is incomplete after the missing asset deletion.",
    "Closed slice OX-56 as DONE.",
    "OX-56 implementation, focused checks, and same-process installed acceptance evidence are complete.",
    "Original installed asset bytes and current-generation pointer were verified after missing and invalid branch testing.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/installed-before-missing.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/fallback-after-missing.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/chooser-after-missing-mutation.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/probe-after-missing.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/files-after-missing-mutation.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/current-generation-before-missing.bin",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/current-generation-after-missing.bin",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/restore-baseline-before-invalid.logcat.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/process-pid-after-restore-failure.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/activity-after-restore-failure.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/README.md",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/CatalogWorldChoiceTest.xml",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/build-install-launch.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/cold-launch-activation-logcat.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/install-baseline.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/installed-before-invalid.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/fallback-after-invalid.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/timeline-after-fallback.png",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/probe-before-invalid.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/probe-after-invalid.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/probe-after-fallback-scrub.log",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/files-after-invalid-mutation.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/current-generation-after-invalid-mutation.bin",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/current-generation-after-final-restore.bin",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/asset-before-missing.sha256",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/asset-after-invalid-mutation.sha256",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/asset-after-final-restore.sha256",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/process-pid-before-missing.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/process-pid-after-missing.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/process-pid-before-invalid.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/process-pid-after-invalid.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/activity-before-invalid.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/activity-after-invalid.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/crashlog-after-missing.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/crashlog-after-invalid.txt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/crashlog-after-fallback-scrub.txt",
    ".codex/history/168-ox56-pack-resolution-recovery.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/168-ox56-pack-resolution-recovery.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/test/java/com/oxygen/weather/timeline/ui/CatalogWorldChoiceTest.kt",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/README.md",
    ".codex/test-artifacts/168-ox56-pack-resolution-recovery/CatalogWorldChoiceTest.xml"
  ],
  "next_action": "Review the final code, plan, roadmap, and device evidence; run workflow and diff checks, archive the completed cycle, then validate IDLE state."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## SLICE intake — OX-56

The owner selected OX-56 as the sole NEXT slice for cycle 168 after OX-32
closed DONE in cycle 167. OX-56 is the only slice in this cycle. Its direct
dependencies are OX-55, OX-91, and OX-32; all are DONE. OX-32 is added as a
direct dependency under the recorded cycle 165 re-entry condition.

The accepted scope remains the approved cycle 165 objective: when an
already-referenced app-owned installed package is missing or invalid during
the OX-32 in-session scene-resolution path, select the complete bundled First
City baseline. Preserve the active place, catalog revision, exact selected
instant, supplied forecast facts and availability, and timeline state and
operation. Keep successful installed resolution unchanged. Import-time
rejection remains owned by OX-91/OX-57, and a failed OX-55 bundled Olive
candidate remains outside this recovery path.

Repository facts verified for re-entry: OX-32's installed selection calls
`ScenePackInstaller.resolveCurrentGeneration` through
`FirstCityCanopyCandidateLoader.loadInstalledBaseline`; the resolver returns
no generation when the current pointer, record, confined asset, or digest
cannot be resolved. `CatalogWorldChoice.chooseInstalled` currently leaves the
committed scene unchanged when loading returns no candidate. OX-32 cycle 167
records successful same-process free → installed → free behavior and the
selected-time, forecast, request-count, lifecycle, and timeline boundaries.
This cycle must preserve those completed invariants.

The UI contract remains the approved existing World control label
`World: First City`, with no added cause text, banner, dialog, or blocking state. The
First City target is slice-local and does not revise OX-41's future Living
Urban Park free-default role.

The exact cycle 165 behavior and UI board files remain unchanged and their
seven recorded fingerprints match on disk. Carry forward the product-owner
approval dated 2026-11-20; no board content or acceptance scope is reopened.
Cycle 168 evidence targets `.codex/test-artifacts/168-ox56-pack-resolution-recovery/`;
cycle 165's BLOCKED evidence remains historical and unchanged.

## BOARDS — approved packages carried forward

The product owner approved the behavior and UI artwork packages on
2026-11-20 in cycle 165. Cycle 168 records the same approval date and
fingerprints for all seven unchanged package files in the manifest above.
The behavior package is the DOT source, SVG/PNG renders, and companion
Markdown at `.codex/concepts/OX-56-pack-resolution-recovery-behavior-board.*`.
The UI package is the SVG artwork, PNG review render, and companion Markdown
at `.codex/concepts/OX-56-pack-resolution-recovery-ui-board.*`.

The approved behavior board covers the missing and invalid branches for an
already-referenced package during scene resolution, then commits the complete
First City baseline while preserving the current forecast session. It excludes
new package import and failed OX-55 bundled chooser candidates. The approved
artwork uses the existing World control to show `World: First City`; no cause
copy, banner, dialog, or blocking state is in scope. The artwork's Chicago and
Friday 2:37 PM values are reference examples, not fixed acceptance inputs.
The board Markdown and render headers retain stale proposal-stage wording;
cycle 165's explicit approval record and matching hashes remain authoritative.
No accepted board input changed, so the approval gate is carried forward.

## Approved bounded plan

### Objective and bounded scope

When the OX-32 in-session scene path re-resolves the installed package identity
that is already committed as the current world, and the referenced package
cannot produce a complete renderable candidate because it is missing or
invalid, discard that incomplete result and select the complete bundled First
City baseline. Keep the already-approved trigger, fallback, selected-weather
invariants, and `World: First City` feedback.

The scope is the single OX-56 failure route at the OX-32 installed-package
resolution boundary. The current debug flow receives app-owned compatible
catalog entries from the same live `ScenePackInstaller` that installed them;
`resolveCurrentGeneration` reads the current pointer, record, confined
resource, and digest. `FirstCityCanopyCandidateLoader` then applies the
existing First City CANOPY profile and budget. The current
`CatalogWorldChoice.chooseInstalled` returns without changing the committed
world when loading produces no candidate. OX-56 changes that bounded failure
outcome for an already-referenced installed world to the complete bundled
First City choice.

For the scoped device reproduction, first commit the installed baseline in
the running session, then retain that same current world identity while
making only its manifest-declared resource unavailable or invalid under the
app-private `files/installed-scene-packs/` generation. Keep the current
pointer, record, live registration, and catalog entry available so the
existing OX-32 resolver reaches the resource failure rather than testing
import, registration, or catalog-removal behavior. Re-resolve the same
already-referenced world through the existing World control. This is the
approved existing-package scene-resolution case; it does not add an import
or a choice for a new package.

### Dependencies and inherited contracts

- **OX-55 — DONE:** complete-candidate preparation and atomic scene commit.
  OX-55's failed new bundled Olive chooser candidate continues to retain the
  prior committed world; that route is not OX-56.
- **OX-91 — DONE:** resource validation provides a complete validated artifact
  or bounded rejection. Import-time rejection remains owned by OX-91/OX-57;
  OX-56 does not change validation, installation, or catalog state.
- **OX-32 — DONE:** the first same-process installed-package resolution path.
  Its successful free → installed → free behavior, request supersession,
  process lifecycle, forecast state, and timeline interaction remain binding.
- **OX-19 — DONE, inherited constraint:** world selection remains independent
  of active location, catalog identity/revision, exact selected instant, and
  forecast facts; it does not fetch or restart the app. This is not a new
  direct roadmap dependency.
- **OX-41 — DONE, inherited decision:** the First City fallback is local to
  OX-56 and does not replace the future Living Urban Park bundled-free role.

Cycle 167 explicitly left missing/invalid package recovery, persisted theme
choice, and simultaneous scrubbing/switching untested or out of scope. OX-56
closes only the approved missing/invalid resolution outcome. OX-59 remains the
separate simultaneous-scrub/switch proof.

### Functional invariants and exclusions

- Commit either the complete requested installed scene or the complete bundled
  First City fallback. Do not expose a partial candidate or mix scene assets.
- Apply fallback only to a current, non-superseded resolution request for the
  already-referenced installed world. A later World choice wins over an older
  pending success or recovery, preserving OX-32's request-order behavior.
- Preserve the exact active location, catalog identity and revision, selected
  instant, supplied forecast facts and availability, timeline scale and
  position, and ordinary timeline operation. Do not call catalog activation,
  selection, or refresh methods as part of fallback.
- Keep the existing World control as the only recovery feedback:
  `World: First City`. Add no cause text, banner, dialog, blocking state,
  chooser option, or persistent status.
- Do not add import validation, package installation/catalog changes,
  persistence or relaunch restoration, Settings, entitlement, a new chooser
  flow, general pack recovery, or Living Urban Park artwork/default changes.
- Keep the debug-only First City baseline proof option debug-only. The OX-56
  fallback is an internal selection outcome, not a new user-selectable pack.

### Observable exit and installed acceptance

The exit is met when a scoped same-process OX-32 installed-package resolution
failure for the already-referenced installed world commits the complete
bundled First City baseline with no partial scene or process failure. The
existing World control reads `World: First City`. Immediately before and
after recovery, the exact selected instant, location, catalog identity and
revision, supplied forecast values and availability remain equal. No forecast
request or source load is added, and the timeline remains operable after the
fallback. The successful installed resolution path remains intact. The board
covers both missing and invalid branches; exercise and record each branch that
the bounded device-local path can trigger, and state explicitly if one cannot
be exercised.

For installed evidence, first use the existing OX-57 debug handoff to install
the approved First City baseline pack and use OX-32 to commit it in the running
session. Keep the same installed catalog entry and active installed scene,
then exercise each approved branch by making only its referenced asset absent
or invalid while retaining the live registration and the cycle's precise
mutation record. Re-resolve that same already-referenced world through the
existing in-session path. Do not use a newly imported package, an unrelated
new candidate, or OX-55's bundled Olive path as a substitute. Record the
exact failure branch and any branch that the bounded device-local path cannot
exercise; do not imply unexercised coverage.

Capture the committed installed state and each recovered First City state.
Record before/after active-selection snapshots containing location ID,
dataset ID, catalog `asOf` and provenance, view generation, and exact selected
instant. Compare visible forecast text and timeline state. Use the existing
debug world probe for request/source counts, capture the same Activity/process
identity around recovery, inspect the switch-window lifecycle/crash log, and
directly scrub the timeline after fallback. Reference values in the approved
artwork are examples only; use the live pre-failure values for comparison.

### Implementation sequence and affected boundary

1. Preserve the `ScenePackInstaller` current-generation resolver and OX-91
   validation behavior. Use their existing missing/invalid result at the
   installed-candidate call site; do not add storage or a second resolver.
2. Route only the eligible installed-resolution failure through
   `CatalogWorldChoice` to `CatalogWorld.FIRST_CITY`, guarded by the current
   request ID so a later selection cannot be overwritten. Keep successful
   candidate publication atomic and leave the bundled Olive path unchanged.
3. Keep the existing World label aligned with the committed fallback and keep
   the weather presentation/timeline state sourced from the same active
   catalog session.
4. Perform only the authorized focused verification, capture installed states
   and actual evidence, inspect the final diff, and close OX-56 only if the
   full roadmap exit is met.

Likely implementation touchpoint: `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`
(`CatalogWorldChoice` and installed selection callback). The already verified
resolver call chain is in `OlivePackCandidate.kt`, `ScenePackInstaller.kt`,
`ActivityVariantDelegate.kt`, `MainActivity.kt`, and debug-only
`CatalogPickerDelegate.kt`; change these only if implementation evidence shows
the bounded recovery cannot be routed at the current selection boundary.
No `CatalogActivationCoordinator` or release-provider change is planned.
No runtime documentation change is currently indicated; update directly
affected pack-resolution documentation only if implementation evidence shows
that it conflicts with the approved fallback.

### Verification authorization and evidence

At plan review, the cycle request authorized planning only. On 2026-11-21 the
owner explicitly invoked `$oxygen-execute-plan`, authorizing this bounded
implementation, the focused `CatalogWorldChoice` checks, debug build/install,
device-local missing/invalid resource setup, and installed acceptance capture.
The Pixel 3 XL `8AKY0M32X` was confirmed available before device execution.
The focused test target is
`app/src/test/java/com/oxygen/weather/timeline/ui/CatalogWorldChoiceTest.kt`.
Do not run a broad suite or RTL, TalkBack, or system matrix under this
design/implementation slice. Preserve `installed-before-missing.png`,
`fallback-after-missing.png`, `installed-before-invalid.png`,
`fallback-after-invalid.png`, `timeline-after-fallback.png`, state snapshots,
resolver mutation/results, request/source counts, lifecycle observations, and
exact commands under
`.codex/test-artifacts/168-ox56-pack-resolution-recovery/README.md`.

The plan-review checks were limited to `python scripts/dev.py workflow` and
`git diff --check`. Execution results, device conditions, limits, and actual
acceptance evidence are recorded in the cycle evidence README. The prior
cycle 165 BLOCKED outcome remains unchanged.

### Risks and limits

- **Failure-route conflation:** apply OX-56 only to the already-referenced
  installed-package resolution path. Import-time failures and failures for
  unrelated/new candidates retain their existing owners and outcomes.
- **Session drift:** compare exact before/after catalog state, selected instant,
  facts, and timeline operation; a rounded display value alone is not enough.
- **Supersession:** commit fallback only for the current request, so a later
  World choice remains authoritative.
- **Device trigger:** retain the live registration and record exactly which
  resource was made missing/invalid. If a branch cannot be triggered through
  this bounded device-local path, record it precisely rather than claiming it
  passed.
- Close OX-56 only after both approved resolution branches, the successful
  installed path, unchanged weather snapshot, timeline operation, focused
  verification, and exact evidence are recorded.
