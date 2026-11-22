# Cycle plan — 167-ox32-installed-theme-hot-swap

State: CLOSED
Plan maturity: FINAL
Cycle ID: `167-ox32-installed-theme-hot-swap`
Roadmap items: OX-32
Objective: Atomically switch an active bundled/free scene to a validated locally installed pack and back in the same Activity/process, without touching active weather location, catalog revision, exact selected instant, currently displayed facts or timeline gesture ownership; no switch-triggered forecast fetch.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "167-ox32-installed-theme-hot-swap",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-32",
      "depends_on": [
        "OX-57",
        "OX-19",
        "OX-55"
      ],
      "status": "DONE",
      "exit": "Installed free → alternate → free evidence proves visible scene change without Activity/process restart, weather-place/catalog/selected-instant mutation, new switch-triggered fetch or timeline/gesture regression; activation never leaves half of two scenes visible.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-21",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.dot",
              "sha256": "ea85a88a0a78dad17eab33ff5cf5504ad434bf4310beea9f5b876386a7c8b7a7"
            },
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.svg",
              "sha256": "03301ae9d5e68dffcc8133ed03bed043c0979a4b908b2dc8969f204025a49d14"
            },
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.png",
              "sha256": "a2a84e5cd5c1b67cf3f7decfca602abb33f3e90e6ce2d9f498240c03b6112e3b"
            },
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.md",
              "sha256": "1ccc4e4c8f3f85de21c92aebea3eaa7003794ce4b533720c5eb7ba6d6b7ff12a"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-21",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.svg",
              "sha256": "5804711a81ddae62d6d1d1fec058f56e629609105e063d35a5fbc326c8e65dd4"
            },
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.png",
              "sha256": "1eb2af1904d525cbb8004b4e2b1e686b15be2d6966ecc584998c7d5b74f47aa2"
            },
            {
              "path": ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.md",
              "sha256": "92f6001bc1df2ecb17560bc7a191e08b377ddaadeb8c8e2bb89f364269e5839a"
            }
          ]
        }
      },
      "outcome": "Installed free → alternate → free evidence on Pixel 3 XL shows the canopy changed and returned in one Activity/process; active location, catalog identity/provenance, exact selected instant, displayed facts, DEMO DATA disclosure, and daily precision remained fixed during both swaps; debug counters stayed at 1 coordinator request and 0 source load calls; timeline scrubs worked before and after; the successful stale-candidate supersession test passed.",
      "evidence": [
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/baseline.ox30pack",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/device-world-probe-log.txt",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-before-switch.txt",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-after-switch.txt",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-lifecycle-log.txt",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/bundled-ready.png",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/chooser.png",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/installed.png",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/returned.png",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-before-swap.png",
        ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-after-swap.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-21",
    "summary": "OX-32 implemented and completed. On Pixel 3 XL, the app switched from bundled First City to the installed compatible baseline pack and back in the same Activity/process. The installed canopy changed visibly; exact weather location, catalog identity/provenance, selected instant, visible facts, DEMO DATA disclosure, and daily precision remained fixed. Debug coordinator and source-load counts did not increase, timeline scrubs worked before and after, and the authorized successful-candidate supersession test passed. See the cycle evidence README for device records and scoped limits.",
    "verification": [
      "python scripts/dev.py workflow — passed at ACTIVE/CLOSEOUT with OX-32 DONE and no active slice.",
      "python scripts/dev.py build — passed; debug build installed on Pixel 3 XL serial 8AKY0M32X.",
      "python scripts/dev.py contract — passed; transport contract OK.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.ui.CatalogWorldChoiceTest.successfulOlderInstalledCandidateCannotReplaceNewerBundledChoice — passed: 1 test, 0 failures, 0 errors.",
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/167-ox32-installed-theme-hot-swap/baseline.ox30pack — passed; content identity sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.",
      "Installed-device free → installed-compatible → free screenshots and scoped timeline/lifecycle/counter checks passed; exact records are in .codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md.",
      "git diff --check — passed after implementation, plan, evidence, and slice closeout edits."
    ],
    "evidence_directory": ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "167-ox32-installed-theme-hot-swap",
  "slice_ids": [
    "OX-32"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.dot",
    ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.svg",
    ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.png",
    ".codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.md",
    ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.svg",
    ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.png",
    ".codex/concepts/OX-32-installed-theme-hot-swap-ui-board.md"
  ],
  "completed_gates": [
    "OX-32 has one eligible slice with OX-57, OX-19, and OX-55 resolved; the exact roadmap exit and evidence target are recorded.",
    "OX-32_behavior_board_approved",
    "OX-32_ui_artwork_board_approved",
    "Keep cycle PLANNED. The approved boards guide the draft but do not activate the slice or authorize implementation.",
    "Both approved board packages remain fingerprint-matched; cycle 167 stays PLANNED and OX-32 remains NEXT.",
    "Full plan review passed: scope, dependency order, architecture boundaries, failure behavior, acceptance, verification, evidence targets, and exclusions are recorded.",
    "The First City proof choice and OX-31/OX-41 bundled-free role are explicitly separate; no catalog identity or default changes are in scope.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-32 accepted exit met: visible same-process free → installed-compatible → free transition with unchanged weather state, selected instant, daily precision, request/load counts, and ordinary timeline interaction.",
    "The single owner-authorized successful stale-candidate supersession test passed."
  ],
  "decisions": [
    "Owner selected debug-only coordinator and source load counters after the requested 60-second decision window; add counters at beginRequest and the registry source.load boundary and report them in OX19WorldProbe only on debuggable builds."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the OX-57 live-installer bridge, current-generation resolver, First City canopy candidate path, and atomic world selection state; debug build and transport contract passed.",
    "The owner-authorized successful-candidate supersession test passed; generated and installed the baseline pack in the same Activity/process and captured bundled-ready, chooser, and installed states.",
    "Added debug-only coordinator request and source load counters at beginRequest and WeatherCatalogSource.load; all five World probe observations remained at 1 coordinator request and 0 source load calls.",
    "Installed free → alternate → free on the reference device with matching weather state, selected instant, daily precision, and Activity/process identity; ordinary daily timeline scrubs worked before and after.",
    "Closed slice OX-32 as DONE.",
    "OX-32 recorded DONE after the installed free → alternate → free device sequence, request/source counter evidence, timeline interactions, lifecycle observation, and owner-authorized supersession test.",
    "Cycle evidence README records exact device commands, screenshots, snapshots, counters, verification results, and untested boundaries.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/device-install-log.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/counter-enabled-install-log.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/bundled-before.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/bundled-ready.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-before-swap.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/chooser.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/installed.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/returned.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/timeline-after-swap.png",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/device-world-probe-log.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-before-switch.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-after-switch.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-lifecycle-log.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/baseline.ox30pack",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md",
    ".codex/history/167-ox32-installed-theme-hot-swap.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/167-ox32-installed-theme-hot-swap.md",
    "docs/ROADMAP.md",
    "app/src/main/java/com/oxygen/weather/timeline/ActivityVariantDelegate.kt",
    "app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt",
    "app/src/main/java/com/oxygen/weather/timeline/data/catalog/CatalogActivationCoordinator.kt",
    "app/src/main/java/com/oxygen/weather/timeline/data/catalog/WeatherCatalogSource.kt",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackInstaller.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/CatalogPickerDelegate.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/OlivePackCandidate.kt",
    "app/src/test/java/com/oxygen/weather/timeline/ui/CatalogWorldChoiceTest.kt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/device-world-probe-log.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-before-switch.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-after-switch.txt",
    ".codex/test-artifacts/167-ox32-installed-theme-hot-swap/activity-lifecycle-log.txt"
  ],
  "next_action": "Inspect the final implementation and evidence diff; run the final diff and workflow checks, then close cycle 167 with exact results and limits."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Approved boards and dependency interpretation

The product owner approved both exact packages on 2026-11-21. Their paths,
approval records, and SHA-256 fingerprints are in the cycle manifest above.
The behavior board defines the same-process bundled First City → one installed
compatible pack → bundled First City flow, candidate preparation before
activation, and preserved weather and timeline state. The UI board defines
the existing validation-build World chooser and its bundled, chooser,
installed, and returned states. The board Markdown companions retain their
proposal-era status text; the explicit owner approval and manifest fingerprints
record the approval of those unchanged files.

All three roadmap dependencies are DONE. OX-19 established that a session-local
world choice preserves the active location, catalog revision, exact selected
instant, facts, and gesture ownership without a fetch or Activity/process
restart. OX-55 established the First City `first-city.canopy.image-png` render
path, complete-candidate commit, and an 8,388,608-byte candidate-load limit.
Its current candidate loader is specific to the olive pack; this slice connects
that path to one installed baseline pack. OX-57 installs a complete generation
and exposes its app-owned registration in the installing process, but does not
activate it as a scene. OX-31 catalog metadata is read-only and expresses
app-owned origin and compatibility; it does not activate or load resources.

The proof uses the existing First City world choice from OX-19 as its bundled
visual baseline. The approved board's `Bundled free` label refers to this
included First City proof choice; it does not rename or replace the OX-31
`BUNDLED_FREE_THEME_ID` (`living-urban-park`) or change the OX-41 Living Urban
Park bundled-free default role. Keep the OX-57 synthetic bundled-free metadata
fixture out of product labels. This slice adds no new catalog identity rule,
default, or persistence behavior.

### Bounded implementation scope

1. Use the existing First City baseline example at
   `docs/scene-pack-examples/first-city-canopy-baseline/` as the sole installed
   alternate. Generate the package into the cycle evidence directory with the
   existing OX-57 build path, then install it through the existing OX-57
   validation-build handoff while the Activity/process remains running.
   Reuse OX-57's app-owned install and OX-31 catalog boundaries; do not add a
   package picker or general pack-management UI.
2. Use the current process's `ScenePackInstaller`/catalog handoff for the
   installed entry. `CatalogPickerDelegate` owns the installer instance that
   handled the OX-57 import, while the Compose screen currently has no installed
   pack handoff. Add a narrow validation-build bridge through the existing
   `ActivityVariantDelegate`/`MainActivity` path so the chooser receives the
   live OX-31 app-owned `COMPATIBLE` entry and candidate requests use that same
   installer instance; do not create a second installer in the screen, since
   its process-local known-pack set would not include the installed ID. Keep
   the existing First City world choice separate from the OX-31 bundled-free
   catalog role and do not expose the OX-57 metadata fixture as product data.
   Add only a narrow read-only current-generation resolver needed to obtain the
   committed manifest and exact installed resource bytes for a cataloged pack.
   Resolve from the authoritative current-generation pointer and record, using
   the manifest-declared confined resource path, not a filesystem scan or
   caller-selected path. Keep installation, catalog composition, and scene
   activation as separate responsibilities.
3. Extend the OX-55 candidate preparation path to accept this one installed
   baseline pack through the existing
   `first-city.canopy.image-png` capability and First City CANOPY renderer.
   Keep PNG dimension checks, checked wide arithmetic, off-main-thread
   preparation, and the existing OX-55 predecode/precommit cap. The example
   PNG is 1024 × 1536 and 1,259,884 bytes. Under the OX-55 formula
   `retainedPngBytes + width * height * 4 + 524288`, its estimate is
   8,075,628 bytes, 312,980 bytes below the 8,388,608-byte limit. This is a
   known example-specific estimate, not a new general pack limit.
4. Add the installed compatible entry to the existing validation-build World
   chooser. Keep the committed scene visible while the installed candidate is
   prepared. Once the complete candidate is ready, commit the installed theme
   identity and renderer candidate together so no frame presents a partial
   mix. Selecting bundled First City returns to the existing bundled scene.
   Keep the chooser's committed selection label aligned with the currently
   visible scene. Do not change forecast coordinator state or trigger a fetch.
5. Retain the current behavior that a later World choice supersedes an
   unfinished candidate request: an older candidate that finishes afterward
   cannot replace the newer committed scene or its chooser label. OX-32 checks
   ordinary timeline interaction before and after a completed swap;
   simultaneous scrubbing and switching remains OX-59.

Likely implementation touchpoints are `CatalogTimeLabScreen.kt`,
`MainActivity.kt`, `ActivityVariantDelegate.kt`, `OlivePackCandidate.kt` (or a
narrowly renamed candidate type), `ScenePackInstaller.kt`, and the existing
debug `CatalogPickerDelegate.kt` handoff. The owner authorized one focused
OX-32 supersession test during later EXECUTE, including the narrow
test-controlled loader injection described below. This authorization does not
extend to additional OX-32 tests. Do not add a user-facing delay control or
installation interruption behavior. No change to OX-31's catalog schema or the
renderer capability registry is expected. Record the internal validation-build
handoff in the cycle evidence README; update compatibility documentation only
if implementation changes the documented renderer contract or budget.

### Invariants and observable exit

The installed theme is selected and returned in the same Activity and process.
Across both switches, preserve the exact active location, catalog identity and
revision, selected `Instant` (including precision), displayed weather facts
and source-time disclosures, the timeline's daily/hourly precision mode, and
ordinary World/timeline gesture ownership. The switch must not call catalog
selection, request, or refresh methods or change the source/coordinator fetch
count. A failed or incomplete candidate leaves the current committed scene
visible. A later World choice supersedes an unfinished installed-candidate
request. The installed and returned screenshots must show one complete scene
each, with no mixed or partially activated frame.

The slice exit is the roadmap exit verbatim: **Installed free → alternate →
free evidence proves visible scene change without Activity/process restart,
weather-place/catalog/selected-instant mutation, new switch-triggered fetch or
timeline/gesture regression; activation never leaves half of two scenes
visible.**

### Proposed execution verification

These are execution targets for a later activated cycle, not results from this
planning turn:

- Run `python scripts/dev.py workflow` and `python scripts/dev.py contract`
  against the completed implementation. The owner authorized adding and
  running one focused OX-32 supersession test during later EXECUTE, using the
  narrow test-controlled loader injection specified below. This does not
  authorize additional OX-32 tests; prior OX-19/OX-55 test authorizations do
  not carry forward. Do not duplicate OX-91 archive-validation coverage.
- Build and install the validation build on the reference device. Generate and
  install the baseline package through the OX-57 live-process path, then capture
  bundled First City, chooser, installed baseline, and returned First City.
  Inspect the installed screenshots against the approved artwork and verify
  that the canopy changes while all visible weather text and timeline content
  remain fixed.
- Record typed before/installed/returned snapshots containing location ID,
  dataset ID, replay recording ID, catalog `asOf` and full provenance,
  `viewGeneration`, and `selectedInstant.toString()`. Compare exact values,
  displayed facts and source-time disclosures. Record the timeline's daily or
  hourly precision mode at each state and confirm it remains unchanged through
  both switches. Count source/coordinator request/fetch calls across both
  swaps and confirm no increase.
- Confirm that a later bundled First City choice made while installed-candidate
  preparation is pending supersedes that request; its delayed completion must
  leave the committed First City scene and chooser label unchanged. Keep this
  check separate from OX-59's concurrent scrub/switch scenario.
- Record app PID plus a switch-window Activity/process lifecycle observation;
  PID alone is insufficient to establish no Activity recreation. Exercise the
  ordinary timeline interaction directly affected by the chooser before and
  after switching. Do not add the OX-59 simultaneous scrub/switch scenario or
  RTL, TalkBack, or broad system matrices.
- Run `git diff --check` and inspect the final diff at execution closeout; record
  their exact results and any unrun authorized checks.
- Preserve all actual commands, results, and limitations in the cycle README.
  A build or preview alone is not installed visual acceptance evidence.

### Supersession evidence resolution and owner authorization — 2026-11-21

**Classification:** Repository-resolvable unknown answered by source inspection;
the owner has authorized the focused verification method below for later
EXECUTE. This was an evidence-method gap, not a demonstrated behavior defect.

`CatalogTimeLabScreen.kt` increments `worldRequest` for every World choice and
checks the captured request before committing a loaded candidate. Its
`onWorldObservation` calls report chooser opening and successful selection;
`MainActivity.kt` logs weather state for those events. Neither supplies an
acknowledgment that candidate loading is pending or that a stale completion
has reached the commit guard. `OlivePackCandidate.kt` loads on `Dispatchers.IO`
without a controllable completion gate. `CatalogWorldChoiceTest.kt` checks
synchronous world changes and weather preservation; `OlivePackCandidateTest.kt`
checks candidate preparation and admission, not overlapping requests.
`CatalogPickerDelegate.kt`'s OX-57 interruption option stops installation after
a staged resource write; it cannot hold a renderer candidate pending.
Consequently, this review establishes no existing deterministic device route.
Rapid taps, fixed sleeps, or an unchanged final screenshot alone do not prove
the required completion order.

**Owner-authorized test contract for later EXECUTE:** Add and run one focused
supersession test against the actual selection/commit path used by the screen,
using an injectable suspend loader with test-controlled start and release
signals. `LocationSelectorControllerTest.kt` already demonstrates
`CompletableDeferred` start/release coordination; that is a harness precedent,
not existing World coverage. Keep the gate in the test loader, with normal
candidate loading as the app default; no user-facing delay control or new
installation interrupt is proposed. Any narrow extraction/injection must
exercise the same commit guard and committed scene state that the screen uses,
not duplicate the request-counter algorithm in a test.

Required sequence and pass conditions:

1. Begin installed request A, await an explicit loader-start acknowledgment,
   and keep its completion suspended while the current complete scene remains
   committed.
2. Select bundled First City as later request B through the same selection
   path; observe its committed identity, renderer candidate and chooser label.
3. Release A with a successful, admissible candidate. Await actual processing
   of that completion, not merely release of the gate. Assert that A cannot
   commit and that all three B values remain unchanged. Cancellation alone
   or a failed/null candidate does not demonstrate rejection of a successful
   stale result.
4. Retain the ordered start/B-commit/A-completion observations, assertions,
   exact command and result in the cycle evidence directory. Bound harness
   waits to report failure rather than hang; elapsed time is not the ordering
   mechanism.

This proposal supplements the existing installed free → alternate → free
captures and weather/fetch/lifecycle/precision/gesture checks; all remain
required. It neither moves supersession to OX-59 nor substitutes a unit result
for installed visual acceptance. This authorization applies to later EXECUTE only; no seam or test is
implemented or run during this REVIEW. The earlier FINAL review is retained as
history of the accepted plan, and cycle activation remains a separate gate.
Approved board artifacts and fingerprints are unchanged.

### Evidence and limits

Execution evidence is recorded in
`.codex/test-artifacts/167-ox32-installed-theme-hot-swap/README.md`, with the
closeout summary at `.codex/history/167-ox32-installed-theme-hot-swap.md`.
The evidence directory retains the generated baseline `.ox30pack` and digests,
the exact active weather snapshots and debug request/source counters, the
Activity/process lifecycle observations, the bundled/chooser/installed/returned
screenshots, and daily timeline interaction captures.

This slice covers one baseline canopy pack, one validation-build chooser, and
same-process switching. It does not prove cold-relaunch restoration, missing or
invalid referenced-pack recovery, persisted selection, arbitrary installed
packs, switching during a scrub, repeated-swap resource lifecycle, broad
accessibility/system acceptance, or commercial entitlement. Those remain with
their separately scoped roadmap items.

**EXECUTE outcome:** The installed compatible baseline canopy was selected and
returned to bundled First City on the same Pixel 3 XL `MainActivity` and process.
The screenshots show the canopy change while displayed weather facts, the
synthetic data disclosure, daily timeline mode, location, dataset, catalog
revision/provenance, and exact selected instant remained fixed. At every World
probe the coordinator request count stayed at 1 and source load calls stayed at
0. The post-return timeline scrub succeeded. The authorized stale-candidate
supersession test passed.

**Verification actually performed:** `python scripts/dev.py workflow`,
`python scripts/dev.py build`, `python scripts/dev.py contract`, the single
owner-authorized
`CatalogWorldChoiceTest.successfulOlderInstalledCandidateCannotReplaceNewerBundledChoice`,
and `git diff --check` passed. Exact commands, device steps, outputs, and limits
are in the cycle evidence README.
