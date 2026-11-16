# Cycle plan — 142-ox03-scene-decision-contract

State: CLOSED
Plan maturity: FINAL
Cycle ID: `142-ox03-scene-decision-contract`
Roadmap items: OX-03
Objective: Establish the source-of-truth decision boundary from SceneReading through FirstCityComposition so catalog rendering consumes decisions, align rain with existing visible clouds, and preserve the no-cloud carried-rain baseline.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "142-ox03-scene-decision-contract",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-03",
      "depends_on": [
        "OX-02"
      ],
      "status": "DONE",
      "exit": "Tested, source-of-truth decision boundary; both active catalog routes use the same composition decisions for covered conditions; rain begins at the visible cloud base when the composition already has visible clouds; and eligible carried rain without visible clouds preserves the existing no-cloud scene and rain-field origin without inferred cloud cover. Preserve exact selected time, independent facts, and source-time disclosure. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.dot",
              "sha256": "a0289a7843026ab87ea86fde56dacaef344cdc21b0f03d136457fb66bb6f4f16"
            },
            {
              "path": ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.svg",
              "sha256": "f9b78ba45d5233e4c414801e7c06dfc962edc9cd0cac1c24b8cc8969b3d76712"
            },
            {
              "path": ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.png",
              "sha256": "52e4a6adf64928218679149eb08b16ea883e4a18fab47f098a9e37518764175b"
            },
            {
              "path": ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md",
              "sha256": "b3c89cb7e9cb21746b5e18e506499074e3613a1e97c069cbb30e41ba06ff75f9"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg",
              "sha256": "50aad90305815436c155dcdbcfdf634ec9aec405151521295ab6b4e98bdd51e0"
            },
            {
              "path": ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.png",
              "sha256": "7cbcb7abe88b0ac0ba469f48639e501c7b1bedd749f448c8f3532c25dc4ebc8a"
            },
            {
              "path": ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.md",
              "sha256": "7c889ed2956909fa9b7d153d0686013419b30e13857aec52eeef8eb8f7e52dbd"
            }
          ]
        }
      },
      "outcome": "Both active catalog routes consume FirstCityComposition decisions for the approved states. Installed paired captures show supplied rain beginning at the visible cloud base, condition-unavailable output without condition-derived weather marks, and eligible carried rain retaining its no-cloud scene and established rain-field origin without inferred clouds. The reading-route drag changed the exact selected instant from 2026-10-04T15:30:00Z to 2026-10-04T14:23:34Z and updated the condition while preserving independent displayed facts. The scoped exit is met; scene-present state-only runtime behavior and broad accessibility/system acceptance remain outside this slice.",
      "evidence": [
        ".codex/test-artifacts/142-ox03-scene-decision-contract/README.md",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-composition.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-reading.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-composition.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-reading.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-composition.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-reading.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction-before.png",
        ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-15",
    "summary": "Cycle 142 completed OX-03 after the product owner selected option 1: apply cloud-origin rain placement only when FirstCityComposition already has visible clouds, while preserving the no-cloud carried-rain scene and established rain-field origin when no cloud decision exists. The active reading fallback now resolves FirstCityComposition and uses the same renderer as the primary composition route. Paired installed captures cover supplied rain, unavailable condition, and eligible carried rain on both routes; a direct reading-route drag changed selection from 2026-10-04T15:30:00Z to 2026-10-04T14:23:34Z and updated the condition with independent facts preserved. The scene-present state-only runtime path remains unverified, and no app tests, lint, broad accessibility, or system matrix were run.",
    "verification": [
      "python scripts/dev.py workflow — PASS at CLOSEOUT; final workflow validation also runs as part of cycle close.",
      "python scripts/dev.py contract — PASS (transport contract reported OK).",
      "python3 scripts/dev.py run --serial 8AKY0M32X — PASS; BUILD SUCCESSFUL, APK installed and MainActivity launched.",
      "python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/142-ox03-scene-decision-contract/<name>.png — PASS for the eight installed captures listed in the cycle evidence README.",
      "adb -s 8AKY0M32X shell uiautomator dump /sdcard/<name>.xml — PASS for matching hierarchies captured and copied for the evidence screens.",
      "adb -s 8AKY0M32X shell input swipe 720 2550 760 2550 500 — PASS; reading-route selection changed from 10:30:00 AM CDT to 9:23:34 AM CDT and the condition readout updated.",
      "git diff --check — PASS.",
      "No app tests were added or run; tests were not authorized. Lint was not run."
    ],
    "evidence_directory": ".codex/test-artifacts/142-ox03-scene-decision-contract/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "142-ox03-scene-decision-contract",
  "slice_ids": [
    "OX-03"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.dot",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.svg",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.png",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.png",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-03_conditional_rain_acceptance_approved"
  ],
  "decisions": [
    "The product owner selected option 1: apply cloud-origin rain placement only when FirstCityComposition already has visible clouds; preserve the no-cloud carried-rain scene and established rain-field origin otherwise, without inferring clouds or suppressing carried rain.",
    "The revised behavior and UI artwork packages explicitly show the visible-cloud correction and no-cloud carried-rain exception. They were displayed, reviewed against the plan, and reapproved on 2026-11-15; refreshed hashes are recorded in the cycle manifest and roadmap."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Owner option 1 was incorporated into the plan and revised boards: cloud-origin placement applies only where existing visible cloud decisions exist; no-cloud carried rain preserves its established scene and rain origin without inferred clouds.",
    "Displayed and reviewed the revised behavior and UI artwork board packages against the updated acceptance, then refreshed product-owner approvals and cycle-manifest fingerprints for both packages on 2026-11-15.",
    "Closed slice OX-03 as DONE.",
    "Recorded OX-03 as DONE with its exact outcome, installed evidence paths, and verification limitations.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.png",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/README.md",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction-before.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction.png",
    ".codex/history/142-ox03-scene-decision-contract.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/142-ox03-scene-decision-contract.md",
    "docs/ROADMAP.md",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.dot",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.svg",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.png",
    ".codex/concepts/OX-03-single-scene-decision-contract-behavior-board.md",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.svg",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.png",
    ".codex/concepts/OX-03-first-city-composition-contract-ui-artwork-board.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/README.md",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/supplied-rain-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/unavailable-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-composition.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/carried-rain-reading.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction-before.png",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/reading-interaction.png"
  ],
  "next_action": "Inspect the complete final diff and untracked plan/board artifacts; run final workflow and diff checks; archive the final plan and cycle record, then close only if validation passes."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### OX-03 scope

**Objective:** Establish `FirstCityComposition` as the shared source of First City scene decisions on both active catalog routes, then align rain with existing visible clouds while preserving the current no-cloud carried-rain composition when no cloud decision exists.

**Dependency:** OX-02 is complete. OX-02A retained both catalog fallbacks, and OX-02A-TESTS recorded matched supplied-rain, unavailable-condition, and eligible-carried-condition states plus the reading-route interaction. The supported and carried rain captures showed rain on the composition route but none on the reading fallback; exact captures and route setup are in `.codex/test-artifacts/141-ox02a-tests/README.md`. The state-only renderer remains unverified and is not an OX-03 target.

**Approved board authority:** The Markdown guide labels and all behavior/UI source-render headers point readers to the cycle plan and roadmap for current approval status and package fingerprints. After installed evidence showed eligible carried rain with no visible cloud decision, the product owner selected option 1: apply cloud-origin placement only when the existing composition has visible clouds; preserve the no-cloud carried-rain scene and its established rain-field origin otherwise. The cycle plan and both board packages were updated to record this decision; the behavior package (DOT, SVG, PNG, and Markdown) and UI artwork package (SVG, PNG, and Markdown) were displayed, reviewed against the revised acceptance, and reapproved on 2026-11-15. Their current fingerprints are recorded in the cycle manifest above. The behavior board governs the reading → composition contract, covered evidence states, route parity, selected-time authority, and conditional rain-origin rule. The UI artwork board shows the visible-cloud supplied-rain correction and the no-cloud carried-rain exception. Both boards preserve Sun/Moon cue styling as baseline; retaining the fixed lunar crescent is not approval of it as phase artwork. Approval does not adopt the proposed Scene Engine spec, approve Living Urban Park artwork or readouts, or decide the park's default/selectable role.

**In scope:**

- Keep the primary composition route and its existing `FirstCityComposition` decisions as the comparison source.
- When the active catalog reading fallback is used, resolve `FirstCityComposition` from the same `SceneReading` and zone, then send it through the same composition renderer. Do not reinterpret weather directly from the reading or `SceneVisualState` in that route.
- When the composition already has a visible cloud decision, move rain-streak origins to the base of that cloud bank and continue them downward into the scene.
- For eligible carried rain with no visible cloud decision, preserve the existing no-cloud scene and established rain-field origin. Do not infer clouds, alter cloud support, or suppress the carried rain depiction.
- Preserve exact selected time, forecast foreground, independently supported facts, carried-condition source time, and timeline behavior.
- Cover the approved supplied-rain, unavailable-condition, and eligible-carried-rain states on both active routes.

**Out of scope:**

- Removing or consolidating the state-only renderer, whose scene-present runtime path remains unverified; changing fixture or Time Lab preview routes.
- New weather inputs, inferred cloud cover, weather-value correction, plausibility checks, precipitation coverage or support changes, interpolation changes, or readout/timeline/gesture redesign.
- Sun/Moon cue appearance, lunar phase art, scene depth refactors, reusable renderers, Living Urban Park artwork, Scene Engine spec adoption, or product-role choice.
- RTL, TalkBack, or broad device/system acceptance assigned to TEST slices.

### Ordered implementation steps

1. Trace the active catalog presentation and route selection, keeping the primary composition route, reading fallback, state-only overload, and preview entry points distinct.
2. Route the active catalog reading fallback through `FirstCityTheme.compose(reading, zone)` and the existing `CatalogAtmosphericScene(composition)` renderer so both active routes consume the same decisions.
3. Change rain-streak placement so each visible streak starts at the visible rain-cloud base when the composition has visible clouds. When it has no visible cloud decision, preserve the existing no-cloud composition and rain-field origin. Retain cloud support, cloud bounds, precipitation selection, rain drawing style, and foreground quiet zones.
4. Verify the three approved matched states and the directly affected reading-route selection interaction; capture the actual installed evidence and record results and limits.

### Compatibility and documentation

- Reuse the existing `FirstCityComposition`, canonical catalog data, `#DEBUG` replays, and renderer. No catalog schema, saved replay format, fixture, migration, or provider changes are planned.
- Preserve existing preview and state-only renderer entry points and their call boundaries. No new caller or product surface is added.
- On completion, update OX-03's roadmap status, evidence path, and observed outcome; archive the final plan and closeout record under cycle `142-ox03-scene-decision-contract`.

### Risks and constraints

- The installed Pixel's current default state is clear night with 0% precipitation. A rain-origin claim requires loading the existing `#DEBUG` replay state; a build or the current screen alone cannot establish it.
- OX-02A-TESTS already exercised the approved rain, carried-rain, and unavailable-condition records on the Pixel. Reuse those exact records. If an approved state cannot be reached, capture no substitute state and record it as not run.
- The scene-present state-only renderer remains unverified. Retain it and make no parity claim for that path.
- Do not add or run tests without explicit user authorization. Do not extend verification to app-wide accessibility or system matrices.

### Functional invariants

- `selectedInstant` remains the single scene and forecast clock. Zoom, rendering, and route selection do not retarget it.
- Supplied condition and precipitation evidence remains faithful. Missing/empty conditions make no condition-derived claim; independently supported values remain available. Eligible carried conditions retain their original source-valid time and disclosure.
- Rain placement is a drawing correction only. It does not create rain support, cloud-cover data, precipitation probabilities, or a measured rain rate. The cloud-origin correction applies only when the composition already has visible clouds; otherwise the existing rain-field origin is preserved.
- The visible cloud layer retains its existing upper-third boundary. When visible clouds exist, rain begins at their base and may extend below them. When no visible cloud decision exists, preserve the established no-cloud rain origin.
- First City output remains at its approved baseline except for the cloud-origin rain correction in visible-cloud states. No cloud cover is inferred, and no inference is made about the unverified state-only route.

### Verification plan

At `EXECUTE`, continue the existing `#DEBUG` replay checks on the Pixel 3 XL (`8AKY0M32X`) using identical catalog IDs and selected instants. Follow `.codex/test-artifacts/141-ox02a-tests/README.md`: launch a fresh `MainActivity` for each route, omit `--ez sg2_null_weather true` for the composition route, and set it to `true` for the reading fallback. Wait until the debug picker is resumed before dismissing it; the extra is sampled at activity creation, so reopening the picker in the same activity does not switch routes.

| Approved state | Existing replay and instant | Required observation |
|---|---|---|
| Supplied rain | `sg4-canonical-gaps-v8`, `2026-10-04T15:30:00Z` | Same scene decisions and forecast facts on both routes; visible streaks begin at the visible cloud base. |
| Eligible carried rain with no visible cloud decision | `sg4-canonical-gaps-v8`, `2026-10-04T16:30:00Z` | Same no-cloud scene and carried rain on both routes; preserve the existing rain-field origin without adding clouds; selected time remains 11:30 AM CDT and the 10:00 AM CDT condition source remains disclosed. |
| Condition unavailable | `ox01f-condition-unavailable-typed-v1`, `2026-10-28T20:00:00Z` | No condition-derived weather claim; supplied independent facts stay matched across routes. |

Use `python scripts/dev.py contract`, `python3 scripts/dev.py run --serial 8AKY0M32X`, and `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/142-ox03-scene-decision-contract/<state-route>.png` as applicable after activation. For every captured screen, save a matching UI hierarchy dump with `adb -s 8AKY0M32X shell uiautomator dump /sdcard/<state-route>.xml` and copy it into the cycle evidence directory. Inspect the affected screenshots at the Pixel 3 XL reference viewport and compare timeline/readout selected-time values. Repeat the direct reading-route timeline drag recorded in the OX-02A-TESTS README, recording the before/after exact selected instant and displayed forecast facts. Record profile, route trigger, catalog/replay ID, selected instant, source time, visible clouds/rain, facts, viewport, interaction result, commands, and limitations.

Do not add or run app tests without a separate user request. The scoped installed route comparisons and source review are the planned slice checks; the `FirstCityThemeTest` source may be inspected, but this plan does not authorize running the test task. Do not claim installed or rain-origin acceptance from a build or preview alone.

### Evidence and closeout targets

- Cycle evidence directory: `.codex/test-artifacts/142-ox03-scene-decision-contract/`.
- Capture paired route screenshots and UI hierarchy dumps for all three approved states; include the reading-route selection capture and a README with exact observations and limits.
- Final checks: `python scripts/dev.py workflow`, `git diff --check`, inspect the final diff, and record exact verification results in `.codex/history/142-ox03-scene-decision-contract.md` and the archived plan `.codex/history/plans/142-ox03-scene-decision-contract.md`.
- Close OX-03 only if the decision boundary and route parity are demonstrated, visible-cloud rain shows cloud-origin streaks, and the carried-rain/no-visible-cloud state preserves its existing scene and rain origin, with the listed invariants intact. Otherwise record the observed incomplete outcome and evidence without marking the slice DONE.
