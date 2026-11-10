# Cycle plan — 122-mr2-main-readout-raise-compact

State: CLOSED
Plan maturity: FINAL
Cycle ID: `122-mr2-main-readout-raise-compact`
Roadmap items: MR.2
Objective: Refactor only the center weather readout to match the authoritative approved target: remove its gold halo and arrange the weather visual, location label and adjacent pin, condition, temperatures, and supporting metrics in the approved hierarchy, preserving the city-name/roofline relationship. All non-center-readout elements and behavior are out of scope for this cycle.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "122-mr2-main-readout-raise-compact",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "MR.2",
      "depends_on": [
        "MR.1"
      ],
      "status": "DONE",
      "exit": "Installed regular and compact center readouts match the approved hierarchy: no gold halo; moon/stars above Florence with the location pin beside the label; condition below the location; that stack directly above current temperature with approximately 7 dp between adjacent elements; the approved city-name/roofline relationship; side-by-side current and feels-like temperatures with aligned baselines; and Precip, Wind, and Humidity below the pair with approximately 7 dp icon-to-label gaps and aligned values. Supplied bearings show an arrow toward wind travel (+180 degrees from wind-from); missing bearings retain the neutral glyph. Existing responsive center-readout type/icon sizes and selected-time values remain. Capture installed regular and compact readouts plus supplied/unavailable-bearing states when reachable from existing inputs; record profile, selected time, bearing, viewport, and center-readout readability. If a required bearing state is unreachable, record it as not run and leave the slice incomplete. No non-center-readout elements or behavior are assessed.",
      "boards": {
        "behavior": {
          "path": "design_draft/MR.2-main-readout-behavior-board-v5.md",
          "approved": true,
          "artifacts": [
            {
              "path": "design_draft/MR.2-main-readout-behavior-board-v5.md",
              "sha256": "45c4f247b8f2f4e18116b973df4b984bcbfba1a64e8203ebb89ba15cbe32bd35"
            }
          ],
          "approved_by": "Product owner",
          "approved_on": "2026-11-09"
        },
        "ui_artwork": {
          "required": true,
          "path": "design_draft/MR.2-main-readout-target-ui-render-v4.png",
          "approved": true,
          "artifacts": [
            {
              "path": "design_draft/MR.2-main-readout-target-ui-render-v4.png",
              "sha256": "d106be1734a90722d68838780c7426c0902e02ff89b4ace94a9ff8413d6af251"
            },
            {
              "path": "design_draft/MR.2-main-readout-target-ui-render-v4.md",
              "sha256": "a41c7bf82ab6f03befabee3509faeb5857652a71993787b92719d3d88084fb2e"
            }
          ],
          "approved_by": "Product owner",
          "approved_on": "2026-11-09"
        }
      },
      "outcome": "Installed regular and compact center readouts meet the approved center-only hierarchy and preserve the selected data inputs. Supplied bearings display the wind-travel arrow and the existing stored SIMULATED state at 2026-10-28T14:00:00Z displays the neutral glyph at both viewport sizes. The location pin action/48 dp target was unchanged in source; no interactions or non-center elements were assessed. See the evidence README for input, captures, observations, and limits.",
      "evidence": [
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-supplied-bearing.png",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-supplied-bearing.png",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-night-supplied-bearing.png",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-night-supplied-bearing.png",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/unavailable-bearing.png",
        ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-unavailable-bearing.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-09",
    "summary": "MR.2 completed the approved center-readout-only refactor: the halo is removed, the approved regular and compact hierarchy is installed, supplied bearings show the wind-travel arrow, and an existing stored SIMULATED catalog/time demonstrates the neutral glyph in regular and compact portrait. Evidence and limitations are recorded in the cycle README. No non-center elements or behavior were assessed, and no fixtures were created or changed.",
    "verification": [
      "python scripts/dev.py workflow — passed at CLOSEOUT.",
      "git diff --check — passed.",
      "python scripts/dev.py build — passed after the final center-readout implementation.",
      "python scripts/dev.py run --serial 8AKY0M32X — passed; debug build installed after the final compact-position adjustment.",
      "python3 scripts/dev.py screenshot --serial 8AKY0M32X — passed for all six regular/compact supplied-bearing and unavailable-bearing captures listed in the evidence README.",
      "Automated tests, lint, and contract checks were not run under this plan."
    ],
    "evidence_directory": ".codex/test-artifacts/122-mr2-main-readout-raise-compact/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "122-mr2-main-readout-raise-compact",
  "slice_ids": [
    "MR.2"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    "design_draft/MR.2-main-readout-behavior-board-v5.md",
    "design_draft/MR.2-main-readout-target-ui-render-v4.png",
    "design_draft/MR.2-main-readout-target-ui-render-v4.md"
  ],
  "completed_gates": [
    "MR.2_ui_artwork_board_approved",
    "MR.2_behavior_board_approved",
    "MR.2_plan_finalized",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "MR.2_EXIT_MET"
  ],
  "decisions": [
    "Owner-directed existing catalog/time: final unavailable-bearing evidence uses the already-stored Florence SIMULATED catalog wx22-debug-simulated-v3 at 2026-10-28T14:00:00Z (Wednesday 9:00 AM America/Chicago), with windDirectionDegrees=null. A stored REPLAY candidate was not used because its debug symbol-review card overlapped the center weather visual in compact portrait.",
    "Read-only device catalog snapshot comparison before and after execution found 14 rows, with no added, removed, or changed catalog payloads; no fixture was created or modified.",
    "No non-center-readout elements or behavior were assessed; automated tests, lint, contract, RTL, TalkBack, and broad system checks were not authorized and were not run."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Captured and inspected regular unavailable-bearing center-readout screenshot at .codex/test-artifacts/122-mr2-main-readout-raise-compact/unavailable-bearing.png.",
    "Captured and inspected compact unavailable-bearing center-readout screenshot at .codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-unavailable-bearing.png; restored device display to 1440x2960 at 560 dpi, font scale 1.0.",
    "Updated .codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md with input identity, selected instant, viewport, screenshots, observations, and limits.",
    "Closed slice MR.2 as DONE.",
    "MR.2 installed regular and compact center-readout acceptance completed with supplied-bearing and unavailable-bearing evidence.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/unavailable-bearing.png",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-unavailable-bearing.png",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-supplied-bearing.png",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-supplied-bearing.png",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/regular-night-supplied-bearing.png",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/compact-night-supplied-bearing.png",
    ".codex/history/122-mr2-main-readout-raise-compact.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/122-mr2-main-readout-raise-compact.md",
    ".codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "docs/ROADMAP.md",
    "design_draft/MR.2-main-readout-behavior-board-v5.md",
    "design_draft/MR.2-main-readout-target-ui-render-v4.png",
    "design_draft/MR.2-main-readout-target-ui-render-v4.md",
    "git diff and git diff --check"
  ],
  "next_action": "Complete closeout review of the final scoped diff and evidence, record exact authorized verification and limits, then archive the cycle."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### MR.2 — Remove halo and align the main readout to the roofline

**Objective.** Refactor only the center weather readout to match the product-owner-approved authoritative UI target. Remove its gold halo; arrange the weather visual, Florence location label and adjacent pin, condition, current/feels-like temperatures, and Precip, Wind, and Humidity indicators in the approved hierarchy. Preserve selected-time values and source/missing-value semantics, current center-readout typography and icon sizes, transparency, and the pin's existing action and touch-target dimensions and behavior. When a bearing is supplied, point the wind arrow toward travel using wind-from bearing + 180 degrees; when unavailable, retain the neutral glyph. Every element and behavior outside the center readout is out of scope.

**Board relationship and approval.** Product owner approval of UI render v4 and behavior board v5 was recorded on 2026-11-09. The approved render is authoritative for center-readout content and arrangement: moon/stars above Florence and its adjacent pin, `Clear night` beneath the location, the temperature pair, and the Precip, Wind, and Humidity indicators. The owner confirmed the pin remains beside Florence while its existing action and touch-target dimensions and behavior are preserved. All other screen elements are fixed artwork context and out of scope. The approved artwork is a design reference, not installed acceptance evidence.

**In scope.** Adjust only the center readout in the catalog main screen: remove the decorative gold arc around its weather symbol; order its header as weather visual, location label with pin beside it, then weather description; place that stack directly above current temperature with approximately 7 dp between adjacent elements; preserve the city-name/roofline relationship; place feels-like immediately to the right of current temperature with aligned text baselines; and place Precip, Wind, and Humidity directly below the temperature pair, with approximately 7 dp between each icon and label and aligned values. Preserve the pin's existing action and touch-target dimensions and behavior, and preserve existing responsive text/icon sizes in the center readout for regular and compact portrait. For the visual wind glyph, use the supplied wind-from bearing plus 180 degrees as the wind-to direction; retain the current neutral glyph when bearing is absent. Keep spoken direction semantics as wind-from. Other screen elements may appear in the full-screen artwork as fixed context only; they are not implementation targets.

**Out of scope.** Every element and behavior outside the center readout, including the atmospheric scene and its sun/moon layers or phases, decorative scene halo, city/building artwork and skyline itself, timeline content/layout/gestures, system bars, app navigation, location-picker behavior and touch-target dimensions, and unrelated screens. The location pin's visual placement beside Florence follows the approved target. Also out of scope are canonical/provider data, projection/value selection, source disclosures, weather-condition mapping, compass spoken names, debug profiles, and changes to typography or icon sizes. Do not add fallback or inferred weather values, change missing-value semantics, or alter the neutral glyph's meaning. Do not add or run automated tests under this authorization.

**Dependencies.** MR.1 is the sole dependency and is DONE. Its installed main readout and evidence provide the before-state; MR.2 changes only the bounded visual layout and wind glyph presentation.

**Invariants.** The selected instant remains authoritative for center-readout values. Supplied values, source disclosures, and unavailable states retain their existing meaning. A supplied bearing controls only the readout's visual arrow direction; the spoken wind-from meaning is preserved. Missing bearing keeps the neutral glyph. Preserve current center-readout text/icon sizes and transparent treatment. Remove only the gold halo around the center weather symbol; the separate scene rendering is outside scope.

**Implementation and acceptance sequence.**

1. Change the main readout layout to remove the halo; order the weather visual, location, and condition as shown in the target board; place this stack directly above the current temperature with approximately 7 dp gaps; preserve the city-name/roofline relationship; and position the Precip, Wind, and Humidity groups directly below the temperature pair. Keep existing responsive type/icon sizes, align the temperature baselines, and align the three metric values.
2. Render a directional arrow only when `windDirectionDegrees` supplies a bearing. Orient the arrow toward wind travel from the supplied wind-from bearing plus 180 degrees, and retain the neutral glyph when no bearing is supplied. Preserve the existing wind-speed and spoken wind-from semantics.
3. Build and install the debug app, then inspect regular and compact portrait on the connected device. Capture installed screenshots for the regular layout, compact layout, supplied-bearing arrow, and unavailable-bearing neutral glyph. Use existing catalog/debug profile states and record the selected time/profile and bearing used for each; do not create or change fixtures to manufacture a state. If a required bearing state cannot be reached from existing inputs, record it as not run and do not claim the corresponding exit condition.
4. On the installed readout states, inspect only the center-readout composition, spacing, alignment, responsive sizing, and legibility; confirm its displayed labels/values and supplied or unavailable wind glyph match the existing selected-state inputs. Do not assess interactions or elements outside the center readout under this cycle.
5. Compare the center readout with the approved board and inspect the scoped diff. Record all commands, device/build/viewport details, screenshot-to-acceptance mapping, center-readout observations, limitations, and any not-run condition in the evidence README. Close MR.2 only if every in-scope exit condition has installed evidence; otherwise record the exact incomplete outcome.

**Authorized verification.** Run `python scripts/dev.py workflow` at planning/execution boundaries, `python scripts/dev.py build`, `python scripts/dev.py run`, and `python3 scripts/dev.py screenshot --serial <connected-device-serial> --output <artifact-path>` for the installed readout states. The current attached reference device is Pixel 3 XL (`8AKY0M32X`); confirm the connected serial and record its actual display size, density, and font scale at execution. The prior MR.1 reference was 1440 × 2960 px at 560 dpi and font scale 1.0. Reproduce the prior compact portrait viewport at 1080 × 2280 px with the same density/font scale by applying `adb shell wm size 1080x2280`, and restore the original display size with `adb shell wm size reset` after compact captures; record the actual dimensions and restoration. Perform only the center-readout visual and readability observations above; interactions and non-center-readout elements are out of scope. At closeout run `git diff --check` and inspect the final diff. No unit or instrumentation tests, lint, contract suite, RTL, TalkBack, or broad device/system matrix is authorized by this plan.

**Evidence.** Save actual captures and observation records under `.codex/test-artifacts/122-mr2-main-readout-raise-compact/`. The README target is `.codex/test-artifacts/122-mr2-main-readout-raise-compact/README.md`; include regular and compact center-readout screenshots and supplied- and unavailable-bearing screenshots. The closeout target is `.codex/history/122-mr2-main-readout-raise-compact.md`. These are planned paths, not claims that evidence exists.

**Compatibility and documentation.** No data, storage, provider, projection, or public interface compatibility work is expected because the approved scope is confined to the catalog UI. At closeout, update MR.2's roadmap status and link its exact evidence/history paths only after the observable exit is met; preserve the approved board record and its fingerprints.

**Risks and limits.** Regular and compact center readouts use different available space, so approved offsets must be checked in both installed states while keeping current responsive sizes. Directional arrows must be visually interpreted as compass bearings toward travel while spoken meaning continues to describe wind-from. The installed reference capture and a successful build are not acceptance evidence. If an installed bearing state cannot be observed, report the missing evidence and leave the slice incomplete rather than infer acceptance. No interaction, non-center-readout, RTL, TalkBack, or app-wide acceptance is claimed.
