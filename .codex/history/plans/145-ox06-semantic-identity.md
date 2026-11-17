# Cycle plan — 145-ox06-semantic-identity

State: CLOSED
Plan maturity: FINAL
Cycle ID: `145-ox06-semantic-identity`
Roadmap items: OX-06
Objective: Give meaningful First City objects stable, testable identities and semantic roles independent of their drawing functions, without introducing a universal registry.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "145-ox06-semantic-identity",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-06",
      "depends_on": [
        "OX-05"
      ],
      "status": "DONE",
      "exit": "First City elements have testable identities/roles without imposing a universal element registry. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-06-scene-element-identity-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "scope": "Finite First City object identities and reusable semantic roles, with an explicit mapping to current physical render passes; identity remains separate from renderer, depth/order, and visibility.",
          "constraints": [
            "Keep the model First City-local; do not add a universal registry or public/persisted ID schema.",
            "Map both cloud passes to one cloud-cover identity and leave TIME_WASH and READOUT_QUIET_ZONE as compositing-only passes.",
            "Preserve selected-time composition, weather meaning, visibility decisions, and OX-05 physical ordering; add no renderer extraction or interaction."
          ],
          "artifacts": [
            {
              "path": ".codex/concepts/OX-06-scene-element-identity-behavior-board.dot",
              "sha256": "f7ecbbc5b5f4564440ea41822a193786acb99a490be07d0dd6689b5a289be335"
            },
            {
              "path": ".codex/concepts/OX-06-scene-element-identity-behavior-board.svg",
              "sha256": "b9acfc65b358d1703e6a992ff3db8cb2b9f28893ca9777be2337694c0086ba3b"
            },
            {
              "path": ".codex/concepts/OX-06-scene-element-identity-behavior-board.png",
              "sha256": "988efbe5d6aa6e25700988bd37115cb89e561edfef84a97218164c2fe82c2046"
            },
            {
              "path": ".codex/concepts/OX-06-scene-element-identity-behavior-board.md",
              "sha256": "83c6a599b4acb320b0dc02ff3fe8575971be612bd17b5d12bbecaea02e147440"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "scope": "Unchanged First City appearance in day/Sun, night/Moon, and moderate-rain states, with board-only callouts that anchor the semantic map.",
          "constraints": [
            "Callouts remain review annotations; add no in-app labels, controls, hit targets, layout, animation, or accessibility changes.",
            "Keep the existing fixed Moon crescent and all visible artwork unchanged.",
            "If implementation changes visible output, stop and replan against a revised approved artwork board with installed evidence."
          ],
          "artifacts": [
            {
              "path": ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.svg",
              "sha256": "b3dd490ca9cc9973a63d21aebc4ce862c9d37e8aac10350f2147dc31022cf1cf"
            },
            {
              "path": ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.png",
              "sha256": "0aadbf2d614e234c315acf202b448d625c6de39466d3e51260ceda66b48c19ce"
            },
            {
              "path": ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.md",
              "sha256": "243bfde161f34185129880c9df3fd65f38e2b4305521eb468331d57063d032c9"
            }
          ]
        }
      },
      "outcome": "Implemented the approved metadata-only OX-06 contract: 11 First City identities with separate reusable roles, explicit mappings for all 12 physical passes including one shared cloud identity, and no mapping for either compositing pass. Focused JVM proof and existing composition/order assertions passed. The renderer and composition builder were unchanged; the scoped exit is met.",
      "evidence": [
        ".codex/test-artifacts/145-ox06-semantic-identity/README.md",
        ".codex/test-artifacts/145-ox06-semantic-identity/ox06-firstcity-jvm-tests.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-16",
    "summary": "Completed cycle 145 OX-06 within the approved metadata-only scope. Added a finite First City inventory of 11 stable identities with separate reusable roles and mappings for all 12 physical render passes; the two cloud passes share one identity, while both compositing passes remain unmapped. Existing selected-time composition and physical ordering behavior are unchanged. The focused JVM proof passed. No installed visual acceptance is claimed.",
    "verification": [
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest — passed (7 tests, 0 failures, 0 errors).",
      "python scripts/dev.py workflow — passed at CLOSEOUT before archival; the close operation also validated the archived cycle.",
      "git diff --check — passed at CLOSEOUT."
    ],
    "evidence_directory": ".codex/test-artifacts/145-ox06-semantic-identity/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "145-ox06-semantic-identity",
  "slice_ids": [
    "OX-06"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.dot",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.svg",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.png",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.md",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.svg",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.png",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-05 dependency resolved",
    "Objective-specific behavior and UI artwork board needs identified",
    "Product owner approved the OX-06 behavior board package",
    "Product owner approved the OX-06 UI artwork board package",
    "Complete package paths and SHA-256 fingerprints recorded in the cycle plan",
    "BOARDS complete: both product-owner-approved packages and their complete fingerprints are recorded.",
    "Reviewed OX-06 objective, dependency, interfaces, product/architecture invariants, compatibility, documentation, scoped verification, evidence, risks, and exclusions.",
    "Behavior and UI board approval records, constraints, exact package fingerprints, and their scope/acceptance authority are complete.",
    "Resolved embedded pre-approval board labels from repository precedent: cycle 144 records approval of an unchanged package that retains draft/pending labels; cycle 145's plan and roadmap record approval of these exact unchanged fingerprints.",
    "No unresolved plan questions; test additions/runs and implementation remain unauthorized.",
    "python scripts/dev.py workflow — passed after final plan/checkpoint update.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-06 exit met: the approved identity/role inventory and complete pass mapping are proven by focused JVM assertions; existing composition and ordering checks pass."
  ],
  "decisions": [
    "The First City candidate map keeps semantic identity/role separate from draw pass, physical depth, and visibility; no universal element registry.",
    "Product owner approved both complete packages on 2026-11-16, including one cloud-cover identity across Background and Middle-Ground cloud passes, metadata-only artwork callouts, and unchanged day/night/moderate-rain states. Board-specific scope and constraints are recorded in the manifest.",
    "After both PNG boards were opened in feh on DISPLAY=:0, the product owner confirmed approval. The plan manifest and roadmap record approval for the exact package fingerprints; the board files retain their pre-approval draft labels, consistent with the approved cycle 144 package. All approved package contents and hashes are unchanged.",
    "OX-05 is the sole direct dependency and is DONE in cycle 144. Its accepted separate state-based renderer limitation remains outside OX-06; this slice does not claim to resolve it.",
    "OX-06 adds metadata only. This planning request does not authorize implementation or test additions/runs; those require explicit owner authorization before execution.",
    "Current composition consumers continue using FirstCityComposition decisions and layerOrder; the new semantic mapping does not route drawing or replace the OX-05 depth contract.",
    "The owner explicitly instructed execution of cycle 145 on 2026-11-16, authorizing activation, implementation, and plan-scoped JVM checks; this supersedes the planning checkpoint’s pending authorization."
  ],
  "open_questions": [],
  "completed_outputs": [
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.dot",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.svg",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.png",
    ".codex/concepts/OX-06-scene-element-identity-behavior-board.md",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.svg",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.png",
    ".codex/concepts/OX-06-first-city-semantic-identity-ui-artwork-board.md",
    ".codex/plans/145-ox06-semantic-identity.md",
    ".codex/current.md",
    "Reviewed approved OX-06 board package fingerprints against the files and checked the First City presentation, renderer, and focused test interfaces.",
    "Resolved board approval-label ambiguity from cycle 144's finalized package and the OX-06 plan/roadmap approval records.",
    "python scripts/dev.py workflow — passed after final plan/checkpoint update.",
    "Activated OX-06 for execution.",
    "Closed slice OX-06 as DONE.",
    "Completed OX-06 metadata implementation and saved focused JVM evidence.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/145-ox06-semantic-identity/README.md",
    ".codex/test-artifacts/145-ox06-semantic-identity/ox06-firstcity-jvm-tests.xml",
    ".codex/history/145-ox06-semantic-identity.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/145-ox06-semantic-identity.md",
    "python scripts/dev.py workflow",
    "git diff --check",
    "complete git diff and git status --short",
    "docs/ROADMAP.md — OX-06 entry",
    ".codex/test-artifacts/145-ox06-semantic-identity/README.md",
    ".codex/test-artifacts/145-ox06-semantic-identity/ox06-firstcity-jvm-tests.xml",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/scene/FirstCityThemeTest.kt",
    "scripts/codex_cycle.py"
  ],
  "next_action": "Inspect the complete final diff, rerun workflow and diff checks, then archive cycle 145 with exact verification results and limitations."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Dependency record and inherited boundary

OX-06 has one direct dependency: OX-05, which is DONE in cycle 144. Cycle 144 established the First City Background → Middle-Ground → Foreground contract and the approved cloud and precipitation pass arrangement on the primary composition route. Its closeout and actual evidence are recorded in `.codex/history/144-ox05-depth-ordering.md` and `.codex/test-artifacts/144-ox05-depth-ordering/README.md`.

OX-05 also records an owner-accepted limitation in the separate state-based `CatalogAtmosphericScene(reading, zone, state)` route. OX-06 is scoped to the First City composition metadata contract; it neither changes that overload nor claims to resolve the limitation. OX-03 and OX-04 semantics remain inherited through the current composition boundary: preserve cloud/rain decisions, supplied or carried source-time meaning, unavailable facts, and independent fact support. OX-06 adds no weather interpretation.

The cycle contains only OX-06. There is no in-cycle dependency to order, and no OX-07 or later renderer extraction, second-world, or inspection work is included.

### Approved board authority and constraints

- The product owner approved the behavior package on 2026-11-16. It governs the 11-object First City inventory, separate identity and role concepts, the mapping from all 12 physical passes, the shared cloud identity, and the exclusion of the two compositing passes. It constrains the work to local metadata and preserves selected-time decisions, weather meaning, visibility, and OX-05 depth/order.
- The product owner approved the UI artwork package on 2026-11-16. It governs unchanged day/Sun, night/Moon, and moderate-rain appearance; its callouts are board-only. It excludes visible artwork, layout, animation, interaction, and accessibility changes. Any output change requires a revised approved artwork board and installed evidence.
- The plan manifest and `docs/ROADMAP.md` record approval for the exact package fingerprints listed above. The board source/render files retain their pre-approval draft labels. This is resolved by the repository's cycle 144 precedent: its finalized plan records owner approval and exact fingerprints for an unchanged revised board package that retains draft/pending labels (`.codex/history/plans/144-ox05-depth-ordering.md`, `.codex/history/144-ox05-depth-ordering.md`). The approved OX-06 package hashes are unchanged, so no approval input changed.

### Approved scope and proposed approach

Implement the approved OX-06 contract within First City's presentation model. Add a finite First City-specific semantic metadata model with stable element identities and separate reusable role values. Associate each meaningful current scene object with its existing render pass or passes through that metadata. Keep those identities and roles independent of renderer function names, physical depth groups, local pass order, selected-time visibility, and weather decisions. The metadata describes the complete First City inventory, including optional objects when they are hidden at a selected instant; it is not derived by filtering the currently visible composition.

Use the approved candidate map as the inventory:

| Semantic object | Role | Existing pass mapping |
| --- | --- | --- |
| Sky backdrop | Sky | `SKY` |
| Star field | Celestial field | `STARS` |
| Sun cue | Time cue | `TIME_CUE` |
| Moon cue | Lunar cue | `LUNAR_CUE` |
| Cloud cover | Cloud field | `BACKGROUND_CLOUDS`, `MIDDLE_GROUND_CLOUDS` |
| Distant skyline | City structure | `BACKGROUND_ARCHITECTURE` |
| Diffuse atmospheric light | Atmosphere | `ATMOSPHERE` |
| Near city/buildings | City structure | `FOREGROUND_ARCHITECTURE` |
| Canopy | Vegetation | `CANOPY` |
| Window details | Light detail | `WINDOW_LIGHTS` |
| Existing single rain/snow field | Weather field | `PRECIPITATION` |

The exact Kotlin type names and ID spelling remain implementation details, as the approved board specifies. Keep the model local to First City; do not create a world-wide registry or persistent/public identifier schema. `TIME_WASH` and `READOUT_QUIET_ZONE` remain compositing passes and are not scene objects.

### Ordered implementation steps

1. Add the finite identity and role metadata within the First City presentation scene boundary, adjacent to the existing `FirstCityLayer` / `FirstCityComposition` contract. Keep it complete and independent of any selected composition's visible-layer subset.
2. Define an explicit mapping for each of the 12 physical `FirstCityLayer` values to the approved semantic object. Map both cloud passes to the same cloud-cover identity; keep `TIME_WASH` and `READOUT_QUIET_ZONE` unmapped as compositing passes.
3. Leave `FirstCityTheme.compose` decisions and `layerOrder` generation unchanged. Keep `CatalogAtmosphericScene(composition, ...)` dispatching from the existing composition order; semantic metadata must not drive rendering, depth, or visibility.
4. Add code-level documentation and focused JVM assertions for the full inventory, roles, all physical-pass mappings, the shared cloud identity, and unmapped compositing passes. Preserve existing composition checks. Add or run tests only after explicit owner authorization.
5. After authorization, run only the focused checks listed below, inspect the scoped diff, and record actual results and limitations. If implementation requires a visible-output change, stop and return to planning for a revised approved artwork target and installed acceptance.

The current consumers relevant to this boundary are the composition builder (`FirstCityTheme`) and the catalog renderer (`CatalogAtmosphericScene(FirstCityComposition, ...)`). The renderer continues to consume the existing typed decisions and ordered layers; no caller migration or runtime compatibility change is needed for metadata that is not used for dispatch.

### Behavior acceptance

- The First City metadata contains the approved finite inventory of 11 meaningful objects, each with one stable identity and one semantic role. Roles are separate from identities and may be shared by multiple objects, as with the two city-structure objects.
- All 12 physical passes in the approved map resolve to their semantic object without using renderer function names or inspecting visibility. Both cloud passes resolve to the same cloud-cover identity and cloud-field role.
- The compositing-only passes remain unmapped. Metadata does not replace or reorder `FirstCityLayer`, `FirstCityDepthGroup`, or the OX-05 absolute and local ordering contract.
- The scoped proof uses focused JVM assertions for the full identity/role inventory, every physical pass mapping, the shared cloud identity, and unmapped compositing passes. Add/run those tests only after explicit owner authorization. Existing `FirstCityTheme` composition decisions and their selected-time visibility/order semantics remain unchanged.

The observable OX-06 exit is the exact board-approved inventory and mapping above, demonstrated by the focused proof or an explicit recorded disposition. This is a metadata contract exit; it does not require installed screenshots when rendered output remains unchanged.

### Product and architecture invariants

- `FirstCityComposition` remains the selected-time source for scene decisions. Preserve its exact selected instant and existing sky, cue, cloud, precipitation, weather-state, visibility, and ordered-layer semantics.
- Preserve OX-03/OX-04 support, unavailable, carried-source-time, independent-fact, and weather-meaning boundaries. OX-06 adds no weather interpretation or plausibility filtering.
- Preserve OX-05's `Background < Middle-Ground < Foreground` ordering, local pass order, single precipitation field, and compositing semantics.
- Keep rendering decorative and forecast facts readable as text. Preserve the timeline, selected-time readout, layout, gestures, and accessibility behavior.

### UI objective and exclusions

The UI objective is unchanged First City output. The approved artwork board shows day/Sun, night/Moon, and moderate-rain states only to anchor the metadata map; its callouts are not app UI. No in-app labels, debug inspector, hit targets, interactions, scene artwork, layout, animation, or accessibility changes are in scope. The fixed Moon crescent remains baseline artwork and does not claim lunar phase. Since the approved implementation is metadata-only, this plan makes no visual or device acceptance claim and requires no installed state captures. If implementation needs to alter rendered output, stop and replan against a revised approved UI artwork board before proceeding.

Also excluded: renderer extraction, a universal element registry, public or serialized IDs, scene-pack schema, second world, provider/data changes, changes to the deprecated oxygenWX app, OX-10 Moon inspection, and OX-16 debug inspection.

### Design risks, compatibility, and documentation

- The approved identity map deliberately differs from the render-pass list: one cloud-cover object maps to both cloud passes, while `TIME_WASH` and `READOUT_QUIET_ZONE` are not semantic objects. Keep those relationships explicit in the proof so later pass or depth edits cannot silently redefine identity.
- Optional cue, cloud, window-light, and precipitation passes can be absent from a selected composition. The approved contract requires metadata to remain complete independently of visibility; the proof must inspect the metadata inventory rather than infer identity from one visible frame.
- OX-06 adds only internal First City presentation metadata. There is no persisted data, public API, serialized identifier, catalog/schema migration, or cross-world compatibility change. Document the local contract in code-level documentation and focused tests; no product specification change is required for this bounded implementation.

### Verification and evidence

This plan-review request authorizes finalizing the plan only. It does not authorize implementation, test additions, or test runs. Before execution, obtain explicit owner authorization for those actions and keep verification scoped to the approved metadata contract. Once authorized, run the focused JVM assertions for the metadata inventory/mapping and existing First City composition checks, then `python scripts/dev.py workflow` and `git diff --check`. Review the final diff for preservation of the selected-time contract, existing pass order, and absence of rendering or interaction changes. Do not claim installed visual acceptance for this metadata-only scope; any implementation that changes output must stop for replanning and installed-state acceptance under a revised board.

Preserve actual implementation verification and any limitations under `.codex/test-artifacts/145-ox06-semantic-identity/README.md`. The planned closeout is `.codex/history/145-ox06-semantic-identity.md`, with the archived plan at `.codex/history/plans/145-ox06-semantic-identity.md`. These are targets only; this plan records no implementation evidence, test authorization, or test results. The plan contains the approved board packages and their fingerprints.
