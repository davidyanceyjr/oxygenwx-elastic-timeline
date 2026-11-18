# Cycle plan — 153-ox92-second-world-adapter

State: CLOSED
Plan maturity: FINAL
Cycle ID: `153-ox92-second-world-adapter`
Roadmap items: OX-92
Objective: Reuse the minimum authored scene declaration shape in one existing Minimal Open Landscape object while preserving shared weather decisions and critical forecast access

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "153-ox92-second-world-adapter",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-92",
      "depends_on": [
        "OX-38",
        "OX-18"
      ],
      "status": "DONE",
      "exit": "First City and Minimal Open Landscape each consume the same declaration shape for at least one comparable capability, with shared selected-instant weather decisions and unmodified critical forecast access.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-92-second-world-adapter-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-92-second-world-adapter-behavior-board.dot",
              "sha256": "742117a76771a7bde65d4cec80e8a9026ada7007f8981e65e4bd80359c2c1b2e"
            },
            {
              "path": ".codex/concepts/OX-92-second-world-adapter-behavior-board.svg",
              "sha256": "da8116e153db69e079566a95500d26e57e816d51122016193785bd122fe8217c"
            },
            {
              "path": ".codex/concepts/OX-92-second-world-adapter-behavior-board.png",
              "sha256": "8b0ea67365c85cd74ae6e515cd655fc0786333613671b50fd0098af8a7574803"
            }
          ]
        },
        "ui_artwork": {
          "required": false,
          "path": null,
          "approved": false,
          "approved_by": null,
          "approved_on": null,
          "artifacts": []
        }
      },
      "outcome": "Both First City CANOPY and the left Open Landscape tree group consume the shared typed vegetation declaration shape; both resolve the existing selected-instant SceneWeatherDecision, preserve their renderer paths and baseline geometry, and leave forecast access unchanged.",
      "evidence": [
        ".codex/test-artifacts/153-ox92-second-world-adapter/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-92 completed: First City CANOPY and the left Minimal Open Landscape tree group use one typed vegetation declaration shape with world-local identity, pass, and geometry; selected-instant decision and critical forecast access remain unchanged.",
    "verification": [
      "Focused SharedSceneWeatherDecisionTest and FirstCityThemeTest plus :app:assembleDebug passed; source path inspection and git diff --check passed. No installed visual, device, RTL, TalkBack, accessibility, font-scale, reduced-motion, or system acceptance claimed."
    ],
    "evidence_directory": ".codex/test-artifacts/153-ox92-second-world-adapter/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "153-ox92-second-world-adapter",
  "slice_ids": [
    "OX-92"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-92-second-world-adapter-behavior-board.dot",
    ".codex/concepts/OX-92-second-world-adapter-behavior-board.svg",
    ".codex/concepts/OX-92-second-world-adapter-behavior-board.png"
  ],
  "completed_gates": [
    "all_slice_boards_approved",
    "draft_complete",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner approved OX-92 behavior board on 2026-11-18; UI artwork not required for baseline-preserving adapter",
    "Focused review: use one shared typed declaration contract with world-local identity, pass, and geometry types; resolve selected-instant renderer input from the existing SceneWeatherDecision in each world adapter"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Rendered and displayed proposed OX-92 behavior board DOT/SVG/PNG",
    "Recorded behavior board approval for OX-92.",
    "Product owner approved displayed OX-92 behavior board; package fingerprints recorded",
    "Completed OX-92 bounded plan with scope, exclusions, invariants, exit, focused verification authorization, and evidence target",
    "Resolved the shared declaration versus First City type boundary and retained focused slice-specific test authorization",
    "Activated OX-92 for execution.",
    "Closed slice OX-92 as DONE.",
    "Shared typed vegetation declaration and both world adapters implemented; focused tests and scoped build passed",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/153-ox92-second-world-adapter/README.md",
    ".codex/history/153-ox92-second-world-adapter.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/153-ox92-second-world-adapter.md",
    ".codex/test-artifacts/153-ox92-second-world-adapter/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect final diff and verification evidence, then archive cycle 153"
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Approved board and bounded outcome

The product owner approved the displayed OX-92 behavior board DOT, SVG, and PNG on 2026-11-18; the manifest records each file's fingerprint. It proposes one existing Minimal Open Landscape tree group as the second declaration-backed object. The First City CANOPY and this tree group must consume the same minimum declaration **shape**, while retaining scene-local identity and each world's existing drawing path. UI artwork is inapplicable because the adapter is intended to preserve current visible output. The board governs scope and acceptance; it does not establish visual parity or authorize implementation.

## Scope and dependency facts

- OX-38 is DONE: the First City CANOPY reads an authored `FirstCitySemanticElement` through `canopyRenderInput()`, then uses the existing aligned-layer renderer. OX-18 is DONE: First City and Minimal Open Landscape already consume one `SceneWeatherDecision` for a selected instant. Preserve those outcomes.
- Extract only the reusable minimum declaration contract needed by the two comparable vegetation objects: scene-local identity, semantic role, ordered physical pass, logical geometry, and typed weather-input boundary. Keep First City identities and pass ordering local. Do not create a universal registry or graph executor.
- Use one shared typed declaration representation, `SceneElementDeclaration<Id, Pass, Geometry>` (or an equivalently typed contract), for both vegetation objects. Its common fields are `id`, `role`, `pass`, and `geometry`; `Id`, `Pass`, and `Geometry` remain world-local types. The comparable role is vegetation. First City's CANOPY must be held by or adapted into this same contract while retaining `FirstCityElementId`, `FirstCityPassReference`, `FirstCityElementGeometry`, and the current asset selection. Open Landscape supplies its own tree-group identity, pass reference, and viewport-relative logical geometry. Do not move First City's complete semantic inventory or unrelated elements into the shared type.
- The typed weather-input boundary is the world-local resolver accepting the existing `SceneWeatherDecision` and a typed declaration. The declaration stores no selected-time weather result and defines no new weather interpretation. Each resolver takes its `selectedInstant` directly from that decision and returns a renderer input with its world-local identity, pass, and effective geometry. Preserve `canopyRenderInput()` as the First City caller-facing adapter; the Open Landscape adapter feeds only the chosen tree-group draw call.
- Declare one existing Open Landscape tree group (the group currently drawn at `centerX = .20f`, `scale = 1f`) using the common shape. Resolve it through a thin composition-to-renderer input that carries the selected instant, identity, pass, and geometry to the existing `drawOpenLandscapeTreeGroup` call. Preserve the second group at `centerX = .78f`, `scale = .92f` on its current direct path.
- Preserve First City's CANOPY declaration, selected-instant adapter, asset choice, pass depth, bounds, and drawing. Keep all other scene objects and weather rendering on their existing paths. The shared declaration shape must express both examples without forcing Open Landscape to adopt First City IDs or City pass names.

## Invariants and exclusions

- `SceneWeatherDecision` remains the sole weather interpretation owner for both worlds. A tree declaration adds no meteorological rule, source lookup, or fabricated state. The selected instant remains the catalog-selected instant.
- Forecast text and timeline selection continue through their existing projection and interaction paths; atmospheric artwork remains decorative. No new scene choice control, fetch, restart, or selected-time mutation.
- Existing Open Landscape tree placement, scale, draw order, and appearance remain the target baseline. No Park, scene pack, general executor, broader object migration, artwork parity, or optional hit/data-surface metadata.

## Execution order and observable exit

1. Introduce the shared typed declaration contract above and adapt the existing First City CANOPY declaration to it. Verify its identity, pass order, geometry, selected instant, and asset-choice behavior with focused slice-specific checks.
2. Add the one Open Landscape tree-group declaration and make only that render call consume a narrow resolved input. Verify its effective `centerX = .20f`, `scale = 1f`, selected instant, and draw order with focused slice-specific checks. Leave the other tree group and unrelated drawing branches direct.
3. Demonstrate in code and focused checks that both worlds consume the same declaration shape and existing decision instance. Inspect the critical forecast access path to confirm it is untouched; record the inspection without claiming an unrun interaction test.

The roadmap exit is met only when both worlds consume that shape for one comparable capability, with shared selected-instant weather decisions and unmodified critical forecast access. A source comparison alone is insufficient if the Open Landscape renderer does not consume its resolved input.

## Authorized verification and evidence

Focused slice-specific tests are authorized. Add or adjust only tests that establish the shared declaration/adapter contract and preserved selected-instant decision, then run those focused tests. Inspect the affected renderer path and critical forecast access call path. Run a scoped build if required to establish Android/Compose integration. System and device tests are out of scope; do not run device captures, RTL, TalkBack, broad accessibility, environment matrices, or the full test suite. Do not claim installed visual acceptance. Record exact commands, results, and limitations in `.codex/test-artifacts/153-ox92-second-world-adapter/README.md`; closeout target is `.codex/history/153-ox92-second-world-adapter.md`.

## Risks and review handoff

The current declaration type is First City-specific. The implementation must make its minimum shape truly reusable without importing City IDs or layers into Open Landscape or widening the abstraction into a general renderer. The existing tree-group function uses viewport-relative coordinates; the adapter must preserve those exact effective arguments and order. No unresolved owner decision remains. Plan review should confirm the approved board, scoped extraction, exit proof, and verification boundary before any activation.
