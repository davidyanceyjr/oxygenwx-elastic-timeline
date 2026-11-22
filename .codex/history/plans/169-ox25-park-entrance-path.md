# Cycle plan — 169-ox25-park-entrance-path

State: CLOSED
Plan maturity: FINAL
Cycle ID: `169-ox25-park-entrance-path`
Roadmap items: OX-25
Objective: Establish the Living Urban Park world composition with retained entrance, curved path, and distant backdrop components while preserving the First City route and exact forecast/timeline behavior.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "169-ox25-park-entrance-path",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-25",
      "depends_on": [
        "OX-24",
        "OX-41"
      ],
      "status": "DONE",
      "exit": "The installed portrait Park world uses its authored backdrop, curved path and entrance components in the app's Park composition, with recognizable placement and correct depth ordering; forecast/timeline operation is unchanged and the First City route remains usable.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-25-park-entrance-path-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-25-park-entrance-path-behavior-board.dot",
              "sha256": "e8554e8017a10560a4d912823e968403bdec0aac32ac96d5f1cc29345bfb1cc8"
            },
            {
              "path": ".codex/concepts/OX-25-park-entrance-path-behavior-board.svg",
              "sha256": "17cdbc9a4f3e9fb24124da984df97624ea9690f50340d2f5cfd15f8f573bb92d"
            },
            {
              "path": ".codex/concepts/OX-25-park-entrance-path-behavior-board.png",
              "sha256": "7828d60af845497c375ef60f7fd34275ebaf6d78092d16685c72beb6449662a8"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-25-park-entrance-path-ui-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-25-park-entrance-path-ui-board.svg",
              "sha256": "1906c6f838590cbb3a24ae256739f230f2d104e3134e4719ea752356e854c5c8"
            },
            {
              "path": ".codex/concepts/OX-25-park-entrance-path-ui-board.png",
              "sha256": "0389968203e6e71e60112039c5da23a4ae75063c442b0658b243b7e8cf0bb7dd"
            }
          ]
        }
      },
      "outcome": "Installed portrait Park uses the authored backdrop, curved path, and foreground entrance in back-to-front order; focused checks pass, forecast facts and timeline remained readable and operational, Park, First City, and Open Landscape remained selectable, and world switching preserved the active Chicago catalog and selected instant without additional source loads.",
      "evidence": [
        ".codex/test-artifacts/169-ox25-park-entrance-path/README.md",
        ".codex/test-artifacts/169-ox25-park-entrance-path/focused-tests.log",
        ".codex/test-artifacts/169-ox25-park-entrance-path/park-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/world-chooser-default-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/first-city-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/first-city-returned-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/open-landscape-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/park-timeline-after-scrub-recaptured.png",
        ".codex/test-artifacts/169-ox25-park-entrance-path/world-probe-recaptured.log",
        ".codex/test-artifacts/169-ox25-park-entrance-path/activity-state-after-recapture.txt",
        ".codex/test-artifacts/169-ox25-park-entrance-path/process-id-after-recapture.txt"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Completed OX-25: the installed app defaults to the approved Living Urban Park composition with authored distant backdrop, curved path, and foreground entrance. Park, First City, and Open Landscape remain selectable; world switching preserved the live Chicago catalog identity, generation, selected instant, and source request counts, and the timeline remained operable. The product-owner board-label mismatch was resolved as stale metadata using the owner-selected option; approved packages and fingerprints were left unchanged. Device acceptance is limited to the scoped Pixel 3 XL portrait states recorded in the evidence README.",
    "verification": [
      "PASS: python scripts/dev.py run --serial 8AKY0M32X — debug assemble, install, and launch succeeded.",
      "PASS: ./gradlew :app:testDebugUnitTest --rerun-tasks --tests com.oxygen.weather.timeline.ui.CatalogWorldChoiceTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest --tests com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest — 15 tests, 0 failures, 0 errors, 0 skipped.",
      "PASS: installed Pixel 3 XL portrait captures cover Park default, chooser, First City, Open Landscape, return to First City, timeline scrub, and chooser dismissal; world probe retained Chicago catalog identity/generation and one coordinator/source load across world switches.",
      "PASS: python scripts/dev.py workflow at CLOSEOUT — Workflow scaffold: OK.",
      "PASS: git diff --check — exit 0, no output.",
      "Not run by slice scope: full test suite, lint, RTL, TalkBack, font-scale, landscape/foldable, reduced-motion, performance, or broad system/device matrix."
    ],
    "evidence_directory": ".codex/test-artifacts/169-ox25-park-entrance-path/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "169-ox25-park-entrance-path",
  "slice_ids": [
    "OX-25"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-25-park-entrance-path-behavior-board.dot",
    ".codex/concepts/OX-25-park-entrance-path-behavior-board.svg",
    ".codex/concepts/OX-25-park-entrance-path-behavior-board.png",
    ".codex/concepts/OX-25-park-entrance-path-ui-board.svg",
    ".codex/concepts/OX-25-park-entrance-path-ui-board.png"
  ],
  "completed_gates": [
    "OX-25 behavior board approved by product owner on 2026-11-22",
    "OX-25 UI artwork board approved by product owner on 2026-11-22",
    "Both OX-25 board packages approved by the product owner on 2026-11-22.",
    "OX-25 behavior and UI artwork board packages approved by product owner on 2026-11-22; package fingerprints validated.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-25 exit met with installed portrait evidence and focused tests."
  ],
  "decisions": [
    "Per the user-authorized 60-second timeout default, treat “approval pending” wording in the fingerprinted OX-25 board files as stale metadata. The exact package fingerprints match the recorded 2026-11-22 product-owner approvals; preserve the packages and proceed without changing their design or approval record."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Resolved the board status-label mismatch using the user-authorized timeout default; no board artifacts or fingerprints changed.",
    "Activated OX-25 for execution.",
    "Closed slice OX-25 as DONE.",
    "OX-25 exit met and its DONE outcome and installed/focused evidence are recorded.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/169-ox25-park-entrance-path/README.md",
    ".codex/test-artifacts/169-ox25-park-entrance-path/focused-tests.log",
    ".codex/test-artifacts/169-ox25-park-entrance-path/park-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/world-chooser-default-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/first-city-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/first-city-returned-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/open-landscape-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/park-timeline-after-scrub-recaptured.png",
    ".codex/test-artifacts/169-ox25-park-entrance-path/world-probe-recaptured.log",
    ".codex/test-artifacts/169-ox25-park-entrance-path/activity-state-after-recapture.txt",
    ".codex/test-artifacts/169-ox25-park-entrance-path/process-id-after-recapture.txt",
    ".codex/history/169-ox25-park-entrance-path.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/current.md",
    ".codex/plans/169-ox25-park-entrance-path.md",
    "docs/ROADMAP.md#OX-25",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkComposition.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/LivingUrbanParkCompositionTest.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/SharedSceneWeatherDecisionTest.kt",
    "app/src/test/java/com/oxygen/weather/timeline/ui/CatalogWorldChoiceTest.kt",
    ".codex/test-artifacts/169-ox25-park-entrance-path/README.md",
    ".codex/test-artifacts/169-ox25-park-entrance-path/focused-tests.log",
    ".codex/test-artifacts/169-ox25-park-entrance-path/world-probe-recaptured.log"
  ],
  "next_action": "Inspect git diff --check and the complete final diff, rerun workflow validation, then archive the cycle with exact verification results and limits."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Approved boards and governing decisions

The product owner approved the exact OX-25 behavior package on 2026-11-22: `.codex/concepts/OX-25-park-entrance-path-behavior-board.dot` and its SVG/PNG renders. The product owner also approved `.codex/concepts/OX-25-park-entrance-path-ui-board.svg` and its PNG review render. The cycle manifest records SHA-256 fingerprints for every package file.

The behavior board governs the new Park default, reuse of the existing World control, the Park backdrop/path/entrance composition, selected-time weather input, and preservation of First City and Open Landscape. The UI artwork board governs the daylight portrait appearance and the coarse, replaceable treatment of those three Park components. The reference image guides landmark placement; its sample readings, weather, logo, and detailed artwork are not requirements. Board approval is design input, not installed acceptance evidence.

OX-24 and OX-41 are DONE. OX-24 establishes an authored scene-asset choice without changing element identity or depth. OX-41 establishes Living Urban Park as the bundled free default and supplies the coarse landmark map. OX-19 is also DONE and constrains world choice to preserve place, catalog revision, exact selected instant, and fetch/restart behavior.

## Bounded implementation

1. Add Living Urban Park to the existing in-session `CatalogWorld` choice and make it the initial bundled world on the active catalog route, as shown on the approved board. Keep the existing World control and chooser; First City and Minimal Open Landscape remain available.
2. Add one Park composition using the established authored declaration/geometry boundary. Declare only the Park-owned distant backdrop, central curved path, and foreground entrance with stable scene-local identities, semantic roles, geometry, and ordered depth passes. These are non-vegetation Park elements: keep `SceneElementRole.VEGETATION` and existing First City/Open Landscape declarations unchanged, use Park-local roles and declaration types, and do not mislabel the elements or generalize the shared helper for this slice. Use the approved coarse drawable treatment; assets and geometry remain replaceable by later Park slices.
3. Render the Park composition from the existing catalog scene reading and shared `SceneWeatherDecision`. Draw the Park backdrop behind the path and entrance, with the entrance in front. Preserve the existing weather renderer and its selected-instant behavior; do not create a Park-specific weather interpreter.
4. Keep the catalog's text readout, selected time, DEMO DATA/provenance labels, and timeline in their current ownership and order. Switching worlds changes only the selected composition.

Affected implementation boundaries are `CatalogTimeLabScreen.kt` for the existing world-choice route, the scene presentation/composition files for Park-local declarations, and `AtmosphericScene.kt` for rendering. Preserve `SceneElementDeclaration.kt` and the minimal second-world adapter patterns where their vegetation contract fits; Park-local roles and declarations follow the same authored boundary without widening the shared helper. Keep existing First City and Open Landscape render paths intact. Do not widen the presenter into a multi-world framework unless the approved behavior cannot be implemented through its current typed scene-reading boundary.

## Scope exclusions

OX-25 does not add the mural/data surface or selected-reading binding, trees/benches, streetcar shelter, production skyline/materials, other lighting/weather treatments, reusable weather renderer families, theme-pack behavior, theme persistence, or additional viewport modes. Those remain separate roadmap outcomes. Do not change missing-installed-pack recovery, catalog activation, weather projection, forecast facts, timeline gestures, selected-time semantics, or accessibility/system behavior outside the changed Park state.

The approved artwork is a coarse first Park treatment, not a production-art completion. Keep this slice to the approved three components and leave later geometry/art refinement possible without changing their scene identity or ordering.

## Invariants

- The selected `Instant` and the existing `SceneReading` remain authoritative. Park composition uses the same reading and `SceneWeatherDecision` as other worlds; world selection must not request or replace catalog data.
- A switch preserves place, active catalog identity/revision/generation, selected instant, forecast facts, source-time disclosure, DEMO DATA label, and timeline interaction state. No fetch or process/Activity restart is triggered.
- The Park backdrop is behind its path; the entrance is in front. The three components stay in the Park composition for later refinement.
- Forecast facts and timeline controls remain readable and operable. The decorative scene does not become an accessibility substitute for text or take gesture ownership.
- First City and Minimal Open Landscape remain selectable through the existing route. Preserve the completed OX-56 fallback behavior for an unavailable installed theme; OX-25 changes the initial bundled-world choice and does not revise that separate recovery outcome.

## Ordered implementation and verification

1. Add the Park world-choice entry and initial default while retaining existing chooser choices and installed-theme resolution behavior.
2. Add the Park declarations/composition and renderer path for the backdrop, path, and entrance; preserve the existing weather decision and presentation input.
3. Add or update focused tests for the Park initial choice, reversible Park/First City selection, continued Open Landscape chooser selection, Park element identity/pass order, and unchanged catalog request count and selected-time values. The product owner authorized focused OX-25 tests. Run only the affected test classes, including `CatalogWorldChoiceTest` and the relevant Park composition/shared scene weather tests; do not run the full suite.
4. Build and install the debug app on the connected Pixel 3 XL (`8AKY0M32X`). Use one fixed catalog and selected instant suitable for the approved daylight portrait state. Capture the initial Park scene, the existing chooser, and the First City route. Record the selected instant and catalog identity/revision before and after switching. Inspect that Park, First City, and Open Landscape remain selectable; inspect the approved three Park components, their depth order, forecast readability, timeline operation, and return to First City.
5. Run `python scripts/dev.py workflow` and `git diff --check`; inspect the final scoped diff. Record exact commands and actual results, not expected outcomes.

Broad device/system, RTL, TalkBack, reduced-motion, font-scale, landscape/foldable, full-suite, and performance matrices are excluded. Do not claim installed visual acceptance from a build or preview; retain the actual installed screenshots and scoped interaction observations.

## Evidence, compatibility, and closeout targets

Store test logs, installed screenshots, selected-instant/catalog observations, and an evidence README under `.codex/test-artifacts/169-ox25-park-entrance-path/`. The README must state device/build details, exact capture and test commands, observations for each approved state, and any not-run checks or limitations. The cycle history target is `.codex/history/169-ox25-park-entrance-path.md`; close only after the OX-25 exit is met or an exact incomplete terminal outcome and evidence are recorded.

The separate grouped-cycle/shared-board clarification is recorded as G.7 in `docs/ROADMAP.md`. It is not in this cycle and does not block OX-25 implementation.
