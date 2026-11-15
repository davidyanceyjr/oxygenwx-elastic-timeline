# Cycle plan — 137-tl01f-daily-scrub-release

State: CLOSED
Plan maturity: FINAL
Cycle ID: `137-tl01f-daily-scrub-release`
Roadmap items: TL-01f
Objective: Preserve the exact selected forecast instant when daily timeline scrubbing is released.

## Cycle roster and board approvals

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "137-tl01f-daily-scrub-release",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "TL-01f",
      "depends_on": [
        "P.2.17",
        "P.2.22",
        "WX.25"
      ],
      "status": "DONE",
      "exit": "On installed active-catalog and fixture/debug surfaces, releasing a daily scrub at an in-window instant such as 2:37 PM leaves that exact instant selected and does not move it to noon. The visible focused-time cue and selected-time forecast context remain tied to the retained instant; the daily summary and its noon-positioned representative artwork remain semantically clear. A true out-of-catalog target still clamps to the nearest catalog boundary, communicated through the existing selected-time cue and context. Daily double-tap entry, hourly scrubbing, scale-only transitions, gesture ownership, responsiveness, and readability retain their existing behavior. Record actual affected-state screenshots and focused interaction observations.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.md",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-14",
          "artifacts": [
            {
              "path": ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.md",
              "sha256": "e279bc456157a9fd4e0668ed44c069c1c90c58b2b42246fcb00266255e38d15a"
            },
            {
              "path": ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.svg",
              "sha256": "4ce0ebdd06c1befd76c8b22b0839f5fe31b0cf8478967a3fb7f7cc7223cc93e6"
            },
            {
              "path": ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.png",
              "sha256": "404142db2e8118d9e250041739c785f5e92c11e9a49032b601152dbe1b4ae692"
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
      "outcome": "Installed daily release retains the selected instant on both active-catalog and fixture/debug surfaces, preserves the SG.4 in-window missing-hour state and source-time disclosure, keeps noon representative summaries independent, preserves daily/hourly transitions, and clamps only actual out-of-catalog targets. Focused installed evidence and scope limitations are recorded in the cycle README.",
      "evidence": [
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/README.md",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-daily-release-1449.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-2pm.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-boundary-release.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-boundary-release.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-return-to-daily.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-before-release.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-drag-held.png",
        ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-after-release-confirmed.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-14",
    "summary": "Completed TL-01f. Daily scrub release preserves the exact selected local instant on active-catalog and fixture/debug surfaces, while noon representative summaries, existing boundary clamps, daily/hourly transitions, and unavailable-value semantics remain intact. The dated owner-approved specification update is recorded in both specifications. Installed acceptance evidence and limitations are preserved under the cycle artifact directory; the scoped slice is DONE.",
    "verification": [
      "python scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; debug build installed and MainActivity launched on Pixel 3 XL.",
      "python scripts/dev.py workflow — passed at EXECUTE and CLOSEOUT; closeout validation returns the cycle to IDLE.",
      "git diff --check — exit 0 after scoped final diff review.",
      "Installed active-catalog and fixture/debug daily release, true boundary clamps on both surfaces, P.2.22 daily-to-hourly entry, hourly release and return, and SG.4 missing-hour release were observed; exact captures and UI hierarchy observations are in .codex/test-artifacts/137-tl01f-daily-scrub-release/README.md.",
      "No automated tests were added or run per repository authorization rules. Broad TalkBack, RTL, font-scale, reduced-motion, and system/environment checks remain outside this slice."
    ],
    "evidence_directory": ".codex/test-artifacts/137-tl01f-daily-scrub-release/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "137-tl01f-daily-scrub-release",
  "slice_ids": [
    "TL-01f"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.md",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.svg",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.png"
  ],
  "completed_gates": [
    "TL-01f_scope_and_roster_confirmed",
    "TL-01f_ui_artwork_inapplicable",
    "TL-01f_behavior_board_approved",
    "TL-01f_implementation_plan_first_draft_written",
    "TL-01f_inrange_noon_snap_reproduced_on_installed_default_build",
    "TL-01f_final_plan_review_complete",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "TL-01f_active_catalog_daily_release_installed",
    "TL-01f_fixture_debug_daily_release_installed",
    "TL-01f_boundary_clamp_installed_both_surfaces",
    "TL-01f_daily_entry_hourly_release_and_scale_return_observed",
    "TL-01f_scoped_specifications_updated",
    "TL-01f_installed_evidence_saved",
    "TL-01f_specification_provenance_attributed_to_cycle_137",
    "TL-01f_missing_facts_release_installed",
    "TL-01f_unavailable_values_preserved_on_gap_release",
    "TL-01f_final_workflow_validation_passed",
    "TL-01f_final_diff_check_passed",
    "all_included_slices_terminal",
    "TL-01f_roadmap_and_plan_status_reviewed",
    "TL-01f_final_scoped_diff_reviewed"
  ],
  "decisions": [
    "Owner-approved target: retain the exact selected Instant on in-window daily release; preserve noon representative artwork and existing boundary clamps.",
    "No UI artwork board is required per owner direction; scope remains behavior and specification wording, with no visual treatment.",
    "The existing selected-time cue/context communicate an out-of-catalog clamped selection; no separate notification is added.",
    "Owner chose option 1 on 2026-11-14: use the reference Pixel 3 XL and retain the approved plan and acceptance unchanged.",
    "Owner selected provenance option 1 on 2026-11-14: add a distinct bounded TL-01f update note dated 2026-11-14 to both controlling specifications and point the P.2.17 summaries to it.",
    "Owner selected option 1 on 2026-11-14: reconnect the reference Pixel 3 XL 8AKY0M32X and verify the SG4 missing-facts release before closeout."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reviewed approved board, dependencies, selected-time consumers, clamp boundaries, documentation passages, verification authorization, and evidence requirements.",
    "Reproduced the in-range default-build defect on the connected Pixel 3 XL: 2:37 PM while held, 12:00 PM after pointer-up; saved diagnostic captures.",
    "Completed and finalized the TL-01f implementation plan without changing the approved behavior or adding UI scope.",
    "Clarified the roadmap's interim noon-rule authority and existing boundary-cue wording during final review.",
    "Finalized the TL-01f implementation plan and saved the REVIEW checkpoint; cycle remains PLANNED and unactivated.",
    "Owner selected option 1; confirmed Pixel 3 XL 8AKY0M32X is connected. Resolved the missing-device prerequisite without changing scope or acceptance.",
    "Activated TL-01f for execution.",
    "Removed the active-catalog daily pointer-up reselection so the exact last scrubbed Instant remains selected.",
    "Changed fixture/debug daily gesture exit to preserve the exact fractional selectedHour while returning to daily overview.",
    "Updated controlling daily-selection, stable-selected-time, daily-navigation, and T01 behavior text to align with TL-01f while preserving noon summary semantics and the P.2.22 exception.",
    "python scripts/dev.py run --serial 8AKY0M32X: BUILD SUCCESSFUL; debug app installed and MainActivity launched.",
    "Installed active-catalog daily release retained 2:49:09 PM CST with selected forecast context; saved the actual screenshot.",
    "Installed fixture/debug daily release retained the nearest-hour 2 PM cue at the scrubbed within-day marker position; saved the actual screenshot.",
    "Verified fixture/debug daily double-tap entry at 7 AM, hourly scrub release remaining hourly, and hourly-to-daily return retaining the 7 AM cue.",
    "Verified actual out-of-catalog daily drags clamp at the active-catalog Friday 11:59:59 PM boundary and fixture/debug Wednesday October 7 11 PM boundary; repeated overrun gestures remained at each boundary.",
    "Saved installed interaction screenshots and scoped observations in .codex/test-artifacts/137-tl01f-daily-scrub-release/README.md.",
    "ADB transport disconnected after the required captures/checks; one redundant fixture after-release screenshot attempt failed. Missing-facts state and broad accessibility/environment matrices were not checked.",
    "Resolved the specification-provenance question per owner direction; the TL-01f update will be explicitly dated and attributed to cycle 137.",
    "Applied the owner-approved provenance resolution: added a dated TL-01f update in both specifications and made each P.2.17 summary point to it.",
    "Read-only source review confirmed an available SG4 replay gap; installed missing-facts release behavior remains unchecked after the device disconnected.",
    "Owner selected the reconnect-and-verify path; Pixel 3 XL 8AKY0M32X reconnected and the active cycle resumed at EXECUTE.",
    "python scripts/dev.py run --serial 8AKY0M32X: BUILD SUCCESSFUL; debug app installed and MainActivity launched for the follow-up acceptance check.",
    "Opened the installed Florence REPLAY sg4-canonical-gaps-v8 at the 2026-10-04 11:30 AM CDT missing-midpoint target; the selected-hour-unavailable state disclosed rain from 10:00 AM CDT and kept other unavailable fields unavailable.",
    "A daily drag visibly changed the held selection to 6:30:46 AM CDT; release at the center returned to exactly 11:30:00 AM CDT with the same missing-hour and source-time semantics.",
    "Saved SG.4 picker, before-release, held-drag, and after-release screenshots and updated the cycle evidence README.",
    "python scripts/dev.py workflow: passed with the cycle ACTIVE at EXECUTE.",
    "git diff --check: exit 0; the scoped final implementation and documentation diff has no whitespace errors.",
    "No automated tests were added or run; cross-cutting accessibility, RTL, font, and reduced-motion matrices remain outside TL-01f scope.",
    "Closed slice TL-01f as DONE.",
    "Updated the completed roadmap sequencing note and plan status/evidence list to reflect TL-01f DONE and actual captured files.",
    "Final scoped diff reviewed after the slice status transition; only approved TL-01f implementation, specification, roadmap, plan, and evidence updates are present.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    "docs/ROADMAP.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/SPECIFICATION.md",
    "docs/OXYGEN_EXPERIENCE_SPECIFICATION.md",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.md",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.svg",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.png",
    ".codex/history/plans/110-p2-double-tap-scale-toggle.md",
    ".codex/history/plans/116-p2-location-aware-double-tap.md",
    ".codex/history/plans/071-wx25-hourly-timeline-acceptance.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/InstantTimeAxis.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt",
    "app/src/main/java/com/oxygen/weather/timeline/data/catalog/CatalogActivationCoordinator.kt",
    "app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/plan-review-device-probe/README.md",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/plan-review-device-probe/before-release.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/plan-review-device-probe/after-release.png",
    ".codex/plans/137-tl01f-daily-scrub-release.md",
    "adb devices -l: Pixel 3 XL 8AKY0M32X connected (device state), 1440 x 2960, USB transport.",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/README.md",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-daily-release-1449.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-boundary-release.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-before-release.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-2pm.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-held.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-scrub-release.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-return-to-daily.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-boundary-release.png",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/Sg4GapCatalogFixture.kt",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-inspection-picker.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-before-release.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-drag-held.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-after-release-confirmed.png",
    ".codex/history/137-tl01f-daily-scrub-release.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/137-tl01f-daily-scrub-release.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "docs/SPECIFICATION.md",
    "docs/OXYGEN_EXPERIENCE_SPECIFICATION.md",
    "docs/UI_DEVELOPMENT_WORKFLOW.md",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.md",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.svg",
    ".codex/concepts/TL-01f-daily-scrub-release-behavior-board.png",
    ".codex/test-artifacts/137-tl01f-daily-scrub-release/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt",
    "app/src/debug/java/com/oxygen/weather/timeline/data/debug/Sg4GapCatalogFixture.kt"
  ],
  "next_action": "Record exact closeout verification and limitations, archive the completed cycle, then verify the IDLE workflow state and final repository diff."
}
```
<!-- cycle-checkpoint:end -->

## Scope and baseline

The daily scrub updates the selected time while the tape moves beneath the fixed playhead. The reset occurs only after release in two paths:

- `CatalogTimeLabScreen.kt` immediately selects the local-noon overview anchor for the scrubbed date on daily pointer-up.
- `TimeLabScreen.kt` calls `leavePrecision(false)` after a daily drag; that helper replaces the current hour with local noon before leaving daily mode.

The daily summary uses a separate noon representative coordinate in `CatalogTimelinePresentation.kt`; `InstantTimeAxis.dailyOverviewAnchor` also supplies that coordinate. Keep this summary projection independent from the selected instant.

The installed default build was probed during plan review on the active catalog timeline. On the same supported Saturday, the selected-time semantics reported 2:37 PM while the pointer was held, then 12:00 PM after pointer-up. The before/after captures and exact device details are in `.codex/test-artifacts/137-tl01f-daily-scrub-release/plan-review-device-probe/README.md`. This confirms the reported in-range defect; it is diagnostic baseline evidence, not post-implementation acceptance.

## Proposed implementation

1. In the active-catalog timeline, make daily pointer-up end the drag without issuing a second date-based selection. Keep the last exact `Instant` selected during scrubbing. Preserve the hourly release path and daily double-tap's separate 7:00 AM selection.
2. In the fixture/debug timeline, leave daily mode after a drag without replacing `selectedHour` with the date's noon. Preserve its exact fractional hour as the selection authority; the established fixture cue may continue showing its nearest whole-hour source sample.
3. Retain the existing supported-interval clamps for true out-of-catalog targets. The resulting selection is communicated through the existing focused-time cue and selected-time forecast context; do not add a separate notification or visual treatment. Do not derive the in-range release selection from a date or change missing-fact behavior.
4. Update all affected behavior wording in `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`: the P.2.17 summary, the daily-selection/release rule, stable selected-time text, daily-navigation release text, and the T01 acceptance row. State that daily release retains the exact scrubbed local instant, with the existing boundary rule. Keep P.2.22's daily-entry exception explicit.
5. Leave daily representative symbols/highs at their noon coordinate. Do not change the `dailyOverviewAnchor` helper or its use for daily summary projection unless execution evidence demonstrates it is necessary.

## Functional invariants and exclusions

- The selected `Instant` / `selectedHour` remains authoritative across pointer-up and scale-only transitions.
- In-window targets, including gaps, remain selected. Only a target outside the supported interval invokes the existing nearest-boundary clamp; the selected-time cue/context communicate the resulting selection.
- Daily-to-hourly double-tap still selects 7:00 AM on its touched date under P.2.22. Hourly scrubbing/release and hourly-to-daily return retain their existing semantics.
- Daily representative content keeps its established noon coordinate; selected-time facts and cues continue to follow their existing projection rules.
- No layout, typography, controls, artwork, animation, time-axis math, data fixture, weather projection, accessibility action, or unrelated gesture change is in scope.
- UI artwork board is inapplicable as directed by the owner. No visual redesign or new UI is planned.

## Dependencies and compatibility

- **P.2.17 / cycle 110:** preserves daily drag, hourly scrubbing, and established gesture ownership. Its daily-release target is the behavior being revised by TL-01f; other gesture rules remain unchanged.
- **P.2.22 / cycle 116:** daily double-tap entry remains its bounded 7:00 AM exception, and hourly-to-daily scale return preserves the exact instant.
- **WX.25 / cycle 071:** selection remains an exact instant while daily representative summary content uses a noon overview anchor. TL-01f changes release selection only.

## Implementation boundaries

Expected implementation files:

- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — remove the daily pointer-up reselection to the noon anchor.
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` — stop daily drag completion from rewriting `selectedHour` to noon while exiting precision mode; retain in-range state.
- `docs/SPECIFICATION.md` — revise the controlling daily selection/release wording and record the bounded TL-01f update.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` — align selected-time and daily-navigation contracts with the approved behavior.

`ElasticForecastTimeline.kt`, `InstantTimeAxis.kt`, and `CatalogTimelinePresentation.kt` are reviewed boundaries expected to retain their current gesture callback, clamp/scrub behavior, and daily-noon summary projection. Change them only if implementation evidence shows the approved behavior cannot be met within the listed release paths.

`MainActivity.kt` passes active-catalog selection to `CatalogActivationCoordinator.selectInstant`; that coordinator applies the supported-interval clamp and rejects stale view-generation callbacks. `CatalogTimelinePresenter` derives the selected-time cue and scene from the selected instant. Keep these consumers and guards intact; the existing focused-time cue is the communication of any retained boundary-clamped selection.

## Ordered work and verification mapping

1. Remove the active-catalog daily pointer-up date-to-noon reselection in `CatalogTimeLabScreen.kt`. Verify with the installed 2:37 PM in-range release on the active-catalog surface; confirm the readout and forecast context remain on that instant.
2. Change fixture/debug daily drag completion in `TimeLabScreen.kt` to exit daily gesture handling without rewriting `selectedHour` to noon. Verify the same in-range release on the installed fixture/debug surface; use a follow-up scale-only transition to confirm the fractional selection remains authoritative even though its source cue rounds to the nearest hour.
3. Update the specification passages listed above. Review their wording against the approved board and the P.2.22/P.2.26 exceptions; do not alter adjacent single-tap, double-tap, hourly, or missing-data contracts.
4. Preserve interval handling and selected-time communication. Verify an actual out-of-catalog boundary target on the installed active-catalog and fixture/debug surfaces, confirming the nearest supported selection is visible through the current cue and context. Do not add a new notification, control, or artwork.
5. Capture the affected installed states, write interaction observations to the cycle evidence README, inspect the scoped diff, and record exact verification outcomes. Automated tests are not authorized by the current instructions and will not be added or run.

## Acceptance and authorized verification

The slice exits only after these focused checks on the installed app:

1. On the active-catalog timeline, scrub in daily mode to an in-window time such as Wednesday 2:37 PM local and release. Confirm the exact selected-time readout stays at 2:37 PM, with no noon reselection; confirm the selected-time forecast context follows the same instant.
2. Repeat on the fixture/debug timeline. Confirm release retains the fractional selected hour; record that its existing nearest-whole-hour source cue is permitted and does not replace the underlying selection.
3. Where available, select an in-window time with missing facts and confirm the selected instant remains there while existing unavailable-value behavior is unchanged.
4. Exercise actual out-of-catalog boundary targets on both surfaces and confirm the nearest-boundary selection is communicated by the existing focused-time cue and selected-time context.
5. Confirm daily representative symbols/highs retain their noon position, daily double-tap still enters at 7:00 AM, hourly scrub release retains hourly mode, and scale-only returns preserve the exact selection. Observe only these directly affected interactions; do not expand into broad accessibility or system matrices.
6. Record actual device/build/catalog/profile/timezone details, focused interaction observations, and screenshots of the active-catalog and fixture/debug daily-release states. A build or preview alone does not establish the exit.

Installed evidence captured:

- `.codex/test-artifacts/137-tl01f-daily-scrub-release/README.md`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-daily-release-1449.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-before-release.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-2pm.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-daily-release-held.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/active-catalog-boundary-release.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-boundary-release.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-scrub-release.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/fixture-debug-hourly-return-to-daily.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-inspection-picker.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-before-release.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-drag-held.png`
- `.codex/test-artifacts/137-tl01f-daily-scrub-release/sg4-gap-missing-after-release-confirmed.png`
- `.codex/history/137-tl01f-daily-scrub-release.md` at closeout

At execution, use `python scripts/dev.py run --serial <device-serial>` to install and launch the debug app and `python scripts/dev.py screenshot --serial <device-serial> --output <evidence-path>` for actual installed captures. Run the workflow validator and inspect `git diff --check` at the appropriate workflow boundaries. Automated tests are not included in this draft because repository instructions require explicit owner authorization before adding or running them.

## Risks and handling

- The selected instant and daily noon representative artwork have different roles. Keep the noon anchor in the daily presentation projection and verify it remains semantically distinct from the retained selection.
- The fixture/debug selected-time cue can display a nearest whole hour. Record that presentation limitation while confirming it does not overwrite the fractional selection.
- The active-catalog path clamps in `InstantTimeAxis.scrub` and again in `CatalogActivationCoordinator.selectInstant`; the fixture/debug path clamps in `ElasticTimeAxis.scrub`. Preserve these clamps and let the existing focused-time cue/context communicate the resulting selection; no separate announcement or visual treatment is part of this behavior-only slice.
- At final-plan review, the controlling specifications still stated noon selection. The completed TL-01f implementation updated both specifications and added the cycle-137 provenance note.

## Plan status

The plan was finalized and activated on 2026-11-14. TL-01f is recorded `DONE` after the scoped installed acceptance checks. Cycle 137 is at `CLOSEOUT`; the cycle tracker will archive this plan with the closeout history after exact verification results and limitations are recorded.
