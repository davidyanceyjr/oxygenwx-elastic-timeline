# Cycle plan — 123-p2-midnight-labels-half-hour-icons

State: CLOSED
Plan maturity: FINAL
Cycle ID: `123-p2-midnight-labels-half-hour-icons`
Roadmap items: P.2.23
Objective: Align daily and hourly weekday labels to local midnight and show supported hourly event symbols at their half-hour visual positions on active-catalog and fixture/debug timelines, preserving source facts and the governing interaction contract. Correct active-catalog daily drag release to local noon on its target date; retain the fixture/debug path's existing noon-release behavior. Verify the existing active-catalog clamp at the complete 44-local-date window end and capture an adjacent supported/missing catalog event pair using debug-only catalog routes. Do not claim partial-day support clamping.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "123-p2-midnight-labels-half-hour-icons",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.23",
      "depends_on": [
        "P.2.20",
        "P.2.21"
      ],
      "status": "DONE",
      "exit": "Installed daily and hourly captures show weekday labels on their local-midnight ticks on both timeline surfaces; daily symbols/highs remain at noon; supported catalog event symbols sit on the `:30` tick for their own hour and all six mapped fixture categories use their corresponding symbol at that midpoint. Missing/unsupported catalog events and unmapped future fixture categories remain icon-free. Releasing a daily drag selects local noon on its target date on both timeline surfaces; the active-catalog coordinator continues to clamp selection to the complete 44-local-date window, with its last-instant behavior verified through a debug-only out-of-range SIMULATED selection request. This is not a partial-day support clamp. Single taps remain no-ops. Focused interaction observations confirm the P.2.22 07:00 daily double-tap entry, exact-instant scale-only toggle, hourly scrub, readability, and gesture ownership. The daily and hourly reference states, including a local-day boundary, the full-window clamp, and a supported/missing catalog event pair, are recorded with actual screenshots and limitations.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-09",
          "artifacts": [
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.md",
              "sha256": "96bc6c7e686aaf80a969135e0c588e1b033088d39603fdb9a2b17d1299d93424"
            },
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.svg",
              "sha256": "d183e2b3133056b6688907575a9f25ecbe40380d78ffac411d28411563c62b8b"
            },
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.png",
              "sha256": "efade24eaec79c2e81e03bbeeb7ea3e4661404dcad8dd0353bc13f0b0842b2c3"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-09",
          "artifacts": [
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.md",
              "sha256": "45402cdfe856260076ba98a6cb75495a1036a7924b47f03bc23055a6b0dfee3c"
            },
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.svg",
              "sha256": "384f76c72306dc9e01c5d4e1e285533158fedd82150f47008978435280acbeca"
            },
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.png",
              "sha256": "84dfc08411a17363283c4ab01fc4b596fda692cf64baff6916b543d4c6741a5b"
            },
            {
              "path": ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-before.png",
              "sha256": "f0d0b157c34a7f5d7fb41e7b2616bfcba671b78623d0466f5aec264f7c4bb75a"
            }
          ]
        }
      },
      "outcome": "Implemented the approved midnight-label and half-hour event-symbol presentation on active-catalog and fixture/debug timelines, corrected active-catalog daily drag release to local noon, and verified the complete CatalogWindow end clamp plus the SG.4 adjacent supported/missing event pair. The approved scope does not establish partial-day field-support clamping.",
      "evidence": [
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/README.md",
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-window-end-clamp.png",
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly-event-gap.png",
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily-drag-noon.png",
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily.png",
        ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-09",
    "summary": "Completed P.2.23: weekday labels align with local-midnight boundaries; supported hourly event symbols and midpoint ticks align at :30 on active-catalog and fixture/debug timelines; daily drag release selects local noon on the target date; and the approved interaction behavior was observed on-device. Installed evidence verifies the existing full 44-local-date CatalogWindow end clamp and the SG.4 supported 10:00/missing 11:00 event pair. No partial-day field-support clamp is claimed. No automated tests were added or run because the plan did not authorize them.",
    "verification": [
      "git diff --check — passed after recording the slice and advancing to CLOSEOUT.",
      "python scripts/dev.py workflow — passed in CLOSEOUT; close operation also validates the archived IDLE state.",
      "python scripts/dev.py build — BUILD SUCCESSFUL.",
      "python scripts/dev.py run — BUILD SUCCESSFUL; debug app installed and MainActivity launched on Pixel 3 XL serial 8AKY0M32X.",
      "Installed evidence reviewed: active-catalog daily, hourly, corrected daily drag, full-window endpoint clamp, SG.4 event gap, fixture/debug daily and six hourly category mappings; device was Android 12, 1440×2960, 560 dpi, font scale 1.0.",
      "Automated tests were not added or run; they were not authorized by the plan."
    ],
    "evidence_directory": ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "123-p2-midnight-labels-half-hour-icons",
  "slice_ids": [
    "P.2.23"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.md",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.svg",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-behavior-board.png",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.md",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.svg",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-ui-artwork-board.png",
    ".codex/concepts/timeline-midnight-labels-half-hour-event-icons-before.png"
  ],
  "completed_gates": [
    "P.2.23_behavior_board_approved",
    "P.2.23_ui_artwork_board_approved",
    "P.2.23_plan_finalized",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "The owner selected option 1 on 2026-11-09. P.2.23 now includes the bounded active-catalog daily-drag local-noon release correction; the behavior board package was refreshed and reapproved.",
    "Installed active-catalog daily drag now releases at 12:00 PM on the target date. Active P.2.22 double-tap entry at 07:00, exact-instant collapse, and hourly scrub were observed; fixture/debug noon release, scale toggle, and six category mappings were captured.",
    "Paused before closeout because the active simulated catalog contains complete local dates and cannot expose a partial first/last-day clamp case, and the current active hourly capture does not conclusively show an adjacent supported/missing event pair.",
    "P.2.23 behavior board approval refreshed on 2026-11-09.",
    "The owner selected option 1 on 2026-11-09: verify the existing full CatalogWindow endpoint clamp and do not claim partial-day support clamping. The behavior board package was revised, rendered, and reapproved. Use the existing SG.4 gap replay's supported 10:00/missing 11:00 adjacency."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented P.2.23 midnight labels, half-hour symbols and ticks on both timeline surfaces; retained actual initial active-catalog screenshots and the prior daily-drag observation.",
    "Following the owner-selected option 1, added the active-catalog daily-drag release snap to local noon, revised and reapproved the behavior board, updated the plan, and resumed the same cycle.",
    "Built and installed the debug app. The corrected active daily drag released at 12:00 PM; P.2.22 07:00 entry, exact-instant scale return, and hourly scrub were observed on-device.",
    "Captured fixture/debug daily release and scale return, and installed hourly screenshots covering all six approved condition-to-symbol mappings.",
    "Recorded device details, commands, observations, evidence paths, and remaining acceptance limitations in the cycle README.",
    "Recorded behavior board approval for P.2.23.",
    "Revised the behavior board and plan acceptance to match the existing full 44-local-date selection interval; owner reapproval was recorded for the refreshed MD/SVG/PNG board package.",
    "Added and exercised the debug-only SIMULATED out-of-range selection probe; the installed timeline reports 11:59:59 PM CST after clamping at the active full-window end.",
    "Used the installed SG.4 debug replay at 11:30, then entered hourly scale and scrubbed to 10:49:52 AM; observed the supported 10:00 rain source at its 10:30 icon position and the missing selected hour with no 11:30 icon.",
    "Closed slice P.2.23 as DONE.",
    "Recorded P.2.23 DONE with the approved full-window clamp and SG.4 event-gap evidence, explicit outcome, and evidence paths.",
    "Kept the acceptance limit explicit: no partial-day field-support clamp is claimed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/README.md",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily-drag.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily-drag-noon.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly-postfix.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-daily-return-postfix.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly-scrub-postfix.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-daily.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-rain-daily-drag-test.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-daily-scale-toggle-check.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-hourly-clear.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-hourly-partly-cloudy.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-hourly-cloudy.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-hourly-rain.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-debug-hourly-snow.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/fixture-storm-hourly.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-window-end-clamp.png",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/active-catalog-hourly-event-gap.png",
    ".codex/history/123-p2-midnight-labels-half-hour-icons.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/123-p2-midnight-labels-half-hour-icons.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/README.md"
  ],
  "next_action": "Review the closeout state, final scoped diff and evidence; repeat workflow and diff checks; archive the plan and cycle with exact results and limitations."
}
```
<!-- cycle-checkpoint:end -->

## P.2.23 — Place date labels at midnight and hourly icons at half-hour

**Owner decision (2026-11-09):** The product owner selected the bounded active-catalog release correction to local noon on the target date after the installed daily-drag observation. The fixture/debug daily release already follows its noon path; verify it remains correct. The later owner decision below replaces the planned partial-day endpoint verification with an explicit check of the current full-window clamp; it does not change the governing noon-selection specification.

### Objective and scope

Move weekday labels on both timeline surfaces to each local day's midnight boundary in daily and hourly scales. In hourly scale, place each supported catalog hourly event symbol at the half-hour visual midpoint of its source hour and add the subtle midpoint tick shown by the approved artwork. Apply the same visible placement to fixture/debug samples using the approved six-row `PresentedCondition` to `WeatherSymbolId` map. Keep source timestamps and forecast facts unchanged, and preserve interaction behavior except for the explicitly approved active-catalog daily-drag release correction.

The roadmap dependencies P.2.20 and P.2.21 are DONE. The cycle contains only P.2.23; unrelated MR.3 remains PLANNED.

**Owner decision (2026-11-09):** The owner chose to revise acceptance to verify the actual full-window endpoint clamp rather than introduce partial-support selection semantics. The active selection interval remains the `CatalogWindow` envelope from D-30 local midnight through D+14-exclusive. The behavior board package was revised and must be reapproved before implementation resumes. A debug-only SIMULATED request beyond the final bound will exercise the coordinator clamp. The adjacent supported/missing pair will use the existing debug-only SG.4 gap replay (10:00 supported rain sample; 11:00 absent sample), selected at 11:30 so both positions are visible together. No partial-day clamp is claimed.

### Implementation boundary

- Active catalog: change drawing and the bounded daily-release selection in `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`. Place the weekday label at the same `day.date.atStartOfDay(view.zone).toInstant()` coordinate as its boundary tick. Keep `day.overviewInstant` as the coordinate for daily event symbol and high. In hourly scale, use each existing `CatalogHourView.instant` as the source and draw its existing non-null `weatherSymbolId` at `instant + 30 elapsed minutes`; retain the existing typed catalog projection and event mapping. Draw subtle half-hour guide ticks alongside the hourly scale, including where an hour has no supported symbol, and omit any symbol whose midpoint lies outside the displayed interval. After an actual daily-scale drag, resolve the gesture-selected instant to its local date in `view.zone`, target `InstantTimeAxis.dailyOverviewAnchor(date, view.zone)`, and pass it through the existing selection callback so the coordinator applies its supported-interval clamp. Do not retarget taps, hourly drags, daily double-tap entry, or scale-only toggles.
- Fixture/debug: change drawing in `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`. Place weekday labels at `day * 24.0`, where the existing fixture date boundary tick is drawn. In hourly scale, draw each sample's mapped symbol at its `LocalDateTime + 30 minutes` axis position and add the corresponding subtle midpoint tick. Keep the sample's `time` as its source instant. Implement only the approved explicit map: `CLEAR → SUNNY`, `PARTLY_CLOUDY → PARTLY_SUNNY_CLOUDY`, `CLOUDY → CLOUDY_OVERCAST`, `RAIN → RAIN`, `SNOW → SNOW`, `STORM → THUNDERSTORM`; an unmapped future category returns no symbol.
- Debug-only catalog verification: in `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugCatalogActivity.kt`, expose a bounded SIMULATED request whose selected instant is beyond the end of the generated 44-local-date window. Permit that exact debug-only request through `CatalogPickerDelegate` only for `SIMULATED_DATASET_ID`; leave the coordinator and production source untouched. Capture that the active selection clamps to the window's last inclusive instant. Use the existing `SG4_GAP_REPLAY_ID` inspection at 11:30 to show the supported 10:00 event at its 10:30 symbol position and the absent 11:00 sample at its empty 11:30 midpoint. Do not modify SG.4 facts to manufacture this pair.
- Use the existing `drawWeatherSymbol` renderer and the compact, subordinate size in the approved UI artwork. Keep icons noninteractive and do not add accessible forecast facts for midpoint artwork.
- Expected production edits remain limited to those two UI files and the approved active-catalog daily-drag release correction. The only additional edits permitted by this decision are the bounded debug-only exact-selection route and button in `DebugCatalogActivity.kt` / `CatalogPickerDelegate.kt`; the current SG.4 recording already supplies the adjacent gap and its canonical facts remain unchanged. `CatalogTimelinePresentation.kt`, `ForecastSource.kt`, `WeatherSymbol.kt`, source catalog definitions, selection reducers, and other gesture paths remain unchanged. Do not derive active selection bounds from field support or change coordinator interval semantics.

### Invariants and exclusions

- Active-catalog date boundaries use the location's IANA zone and `LocalDate.atStartOfDay(zone)`. Catalog midpoint placement uses absolute `Instant` arithmetic (`+30 elapsed minutes`) so DST days and repeated hours remain correctly ordered. Do not create nonexistent hours.
- The fixture axis remains its existing complete 24-hour-per-day local axis; its `LocalDateTime` sample is unchanged. The six mappings select artwork only and do not alter readable condition, weather facts, warnings, precipitation fields, scene input, or the catalog event map.
- Daily summary symbol/high stay on their existing noon overview coordinate. Releasing a daily drag selects local noon on the target date on both surfaces; the active catalog uses the existing full 44-local-date window clamp, while fixture/debug keeps its existing noon sample release path. A debug-only out-of-range SIMULATED selection verifies the last-inclusive-instant clamp. This does not establish partial-day field-support clamping, which remains outside the accepted behavior. A single tap remains a no-op. P.2.22 daily double-tap entry remains at 07:00, hourly-to-daily scale-only toggle preserves the exact instant, and hourly scrubbing remains unchanged. Hourly symbols do not mark event onset or assert a fact at `:30`.
- Missing, empty, or unsupported catalog event samples and unmapped future fixture categories draw no icon. The existing debug-only SG.4 replay supplies the adjacent supported/missing pair at 10:00/11:00; the 10:30 midpoint symbol appears and the 11:30 midpoint stays empty. Do not carry a neighboring symbol into a gap or clamp an edge icon onto a different time.
- Preserve selected-time authority and its center-playhead projection, source-time disclosure, all forecast facts and text, scale behavior, gesture ownership, responsiveness, readable labels, RTL meaning, and the existing immersive hierarchy. No visible scale controls or instructions are added.
- No change to schemas, providers, data windows, timezone policy, scene generation, accessibility text, or the deprecated oxygenWX application. Broad accessibility/system acceptance belongs to designated TEST slices.

### Exit

Installed daily and hourly captures show weekday labels on their local-midnight ticks on both timeline surfaces; daily symbols/highs remain at noon; supported catalog event symbols sit on the `:30` tick for their own hour and all six mapped fixture categories use their corresponding symbol at that midpoint. Missing/unsupported catalog events and unmapped future fixture categories remain icon-free. A daily drag release selects local noon on its target date on both surfaces; active-catalog selection retains the existing complete 44-local-date interval. A debug-only out-of-range SIMULATED selection request is clamped to the last inclusive instant of that window. This verifies full-window endpoint behavior only; P.2.23 does not claim a partial-day support clamp. Taps remain no-ops. Focused interaction observations confirm the P.2.22 07:00 daily double-tap entry, exact-instant scale-only toggle, hourly scrub, readability, and gesture ownership. The daily and hourly reference states, including a local-day boundary, the full-window endpoint, and the supported 10:00/missing 11:00 event pair, are recorded with actual screenshots and limitations.

### Verification authorized for execution

This is a visual design slice. After activation, use the installed debug app on the Pixel 3 XL used for the approved before reference when available; record device/build/viewport details and actual observations. The bounded evidence set is:

1. Active-catalog daily and hourly captures. The daily image shows midnight labels with daily symbols/highs still at noon. The hourly image includes a local date boundary and adjacent supported/missing catalog event samples, with supported symbols and midpoint ticks aligned at `:30`. Use the debug-only SG.4 replay at 11:30 to show the 10:00 supported event next to the missing 11:00 fact.
2. Fixture/debug daily and hourly captures from the existing debug profiles. Show midnight labels and the noon daily summary. Verify all six mapped categories across the existing rain, storm, and cold/snow profiles at their own half-hour positions; do not alter fixture profiles to create evidence.
3. Observe selected-time continuity during scale toggle, daily drag, and hourly scrub. Verify a daily drag release selects local noon on its target date on both surfaces; verify the debug-only request beyond the last active-window bound is clamped to `endExclusive - 1 nanosecond`; verify single taps remain no-ops, daily double-tap still enters at 07:00, and scale-only collapse retains the exact instant. Check affected labels, symbols, and forecast values for readability and predictable gesture ownership while browsing the captured states.
4. Save actual screenshots and observations under `.codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/README.md`, naming the images `active-catalog-daily.png`, `active-catalog-hourly.png`, `active-catalog-daily-drag-noon.png`, `active-catalog-window-end-clamp.png`, `active-catalog-hourly-event-gap.png`, `fixture-debug-daily.png`, and the hourly profile captures. Retain the original `active-catalog-daily-drag.png` as the before-correction observation; record the corrected result separately and state that endpoint verification uses the full 44-day window, not a partial-day support bound.

The debug installed flow follows `docs/UI_DEVELOPMENT_WORKFLOW.md`: `python scripts/dev.py run`, launch `com.oxygen.weather.timeline/.data.debug.DebugForecastActivity`, select an existing profile, and capture with `python scripts/dev.py screenshot --serial <device-serial> --output <artifact-path>`. A build or preview alone does not satisfy acceptance. This plan authorizes the stated installed screenshots and directly affected interactions only. It does not authorize adding or running automated tests; no RTL, TalkBack, broad device matrix, contract, or general system suite is included.

### Risks, evidence, and closeout

- Midnight labels move to the viewport's day boundary; verify first/last visible labels remain readable without moving them away from midnight.
- Catalog event symbols come only from each existing `CatalogHourView.weatherSymbolId`; unsupported or missing event projections must remain blank. The hourly catalog view contains actual facts rather than a fabricated complete-hour sequence, so record the observed supported/missing pair and any coverage limits.
- The existing debug profiles already include the six fixture categories. Verify the mappings across those profiles without changing profile facts.
- The existing SG.4 replay already includes a supported 10:00 event immediately before its absent 11:00 fact; use its 11:30 inspection button for the installed pair capture. Active-catalog selectable bounds are the `CatalogWindow` envelope, not the first/last field-supported instants. Verify the actual final inclusive bound through the approved debug SIMULATED request; do not describe it as a partial-day support clamp.
- Preserve evidence and final diff under `.codex/test-artifacts/123-p2-midnight-labels-half-hour-icons/`, `.codex/history/123-p2-midnight-labels-half-hour-icons.md`, and `.codex/history/plans/123-p2-midnight-labels-half-hour-icons.md`. Close only when the exact exit is met or a specific terminal outcome and its evidence are recorded.
- Execution verification: run `python scripts/dev.py workflow`, `python scripts/dev.py build`, and `python scripts/dev.py run` as applicable, then capture and inspect the authorized installed states and interactions above. Do not run tests or broaden verification beyond this visual slice. Finish with `git diff --check` and inspect the final diff; preserve exact commands, results, evidence, and limitations in closeout.

### Documentation and compatibility

The P.2.23 roadmap item remains the governing roadmap scope; this plan makes its daily-drag acceptance concrete using the existing local-noon requirement. No product specification, architecture, or user-facing documentation change is in scope. The cycle is active and remains so through execution and closeout.
