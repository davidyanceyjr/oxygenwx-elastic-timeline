# Cycle plan — 127-p2-hour-tick-labels

State: CLOSED
Plan maturity: FINAL
Cycle ID: `127-p2-hour-tick-labels`
Roadmap items: P.2.26
Objective: Label every existing longest hourly tick below the axis, preserving half-hour weather-symbol positions and disambiguating noon/midnight as `12 PM`/`12 AM`.

## Cycle roster and board approvals

P.2.26 depends on completed P.2.25. The product owner approved both revised
board packages on 2026-11-11. Their approval dates and fingerprints are recorded
in the roster below. The boards governed the finalized plan and its execution;
the cycle was activated, and its single slice is now recorded DONE pending
closeout.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "127-p2-hour-tick-labels",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.26",
      "depends_on": [
        "P.2.25"
      ],
      "status": "DONE",
      "exit": "Installed active-catalog and fixture/debug hourly views show an unclipped label centered immediately below every visible longest tick (the existing every-three-local-hours marks), including `3`, `6`, and `9` in the current device viewport; intervening whole-hour/half-hour ticks remain unlabelled. Midnight and noon long ticks read `12 AM` and `12 PM` respectively. Labels stay attached to their ticks during focused scrubbing. The selected state remains authoritative. The active-catalog readout continues to show the selected local time to the minute; the fixture/debug readout retains its existing nearest whole-hour source-sample cue during sub-hour scrubbing without changing the underlying fractional selection. The selected weekday remains above the symbol row and updates at local midnight; weather symbols remain at their existing :30 midpoints with unchanged event mapping and missing-value behavior. Daily mode, 48 dp/hour spacing, 24 dp symbols, timeline gestures, responsiveness, and forecast facts remain intact. Record actual hourly captures and focused interaction outcomes on both timeline surfaces, plus a daily reference confirming no visible change.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.md",
              "sha256": "c1dfad5d732d1e5160b08afb56aec7d8f96a4e6c0331f2f726c2fee9ddc47d09"
            },
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.svg",
              "sha256": "71ae809a018f271c0c1b6cf3c04762ef5ac39908168911e6dc7fa6bbf37db358"
            },
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.png",
              "sha256": "204a41bb38ca78e8c824a36a727a9adbd711a114b4235f224499e2eb81359609"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-11",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.md",
              "sha256": "13c69be9ef07386f0acf3aa6b80373990f552076a30f4921c0a9bc70ad03a262"
            },
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.svg",
              "sha256": "fe18f158ef553698205946805f2f3251a7121866bba2fcb7720c2e70accb7ce6"
            },
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.png",
              "sha256": "6bbf7154b6150bb1fb8434881a7e874e4b04948f1709ef69e1259bf76ec95f50"
            },
            {
              "path": ".codex/concepts/P.2.26-hour-tick-labels-device.png",
              "sha256": "6e023b33dbffce14ee9bd3411114e9fe759b2782873b957cd019f0da6838f117"
            }
          ]
        }
      },
      "outcome": "Implemented and installed approved three-hour local-time labels below every visible longest tick on active-catalog and fixture/debug timelines. Labels remain anchored while scrubbing, noon and midnight read 12 PM and 12 AM, fixture focus/readout bounds remain distinct at a focused long tick, and daily mode retains its existing artwork. Focused installed acceptance is recorded on the Pixel 3 XL.",
      "evidence": [
        ".codex/test-artifacts/127-p2-hour-tick-labels/README.md",
        ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-hourly-3-6-9.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-noon-scrub.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-midnight-after.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-daily-reference.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-focus-7am.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-focus-near-6am.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-noon-12pm.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-midnight-approach.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-midnight-after.png",
        ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-daily-reference.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-11",
    "summary": "P.2.26 is complete: active-catalog and fixture/debug hourly timelines label every visible existing three-hour longest tick below the axis, leave intervening ticks blank, and distinguish noon and midnight as 12 PM and 12 AM. Labels remain anchored during scrubbing; the selected weekday updates at local midnight; the active-catalog cue remains minute precise; fixture/debug keeps its approved nearest-hour source-sample cue while fractional selection stays authoritative. The fixed-rail focused label and cue are readable together, and daily mode remains unchanged. Focused installed review passed on one Pixel 3 XL. RTL, TalkBack, reduced motion, other font scales/devices, and broad system acceptance were not checked.",
    "verification": [
      "python scripts/dev.py run --serial 8AKY0M32X: first attempt reported unresolved roundToInt in CatalogTimeLabScreen.kt:583; after adding the missing import, rerun completed BUILD SUCCESSFUL, installed the debug APK, and launched the app.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-hourly-3-6-9.png: captured actual installed review states; all exact output paths and observations are listed in .codex/test-artifacts/127-p2-hour-tick-labels/README.md.",
      "python scripts/dev.py workflow: passed during EXECUTE and CLOSEOUT; cycle close validation passed.",
      "git diff --check: passed at CLOSEOUT.",
      "Automated tests and lint were not run; the approved P.2.26 plan did not authorize them."
    ],
    "evidence_directory": ".codex/test-artifacts/127-p2-hour-tick-labels/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "127-p2-hour-tick-labels",
  "slice_ids": [
    "P.2.26"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.md",
    ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.svg",
    ".codex/concepts/P.2.26-hour-tick-labels-behavior-board.png",
    ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.md",
    ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.svg",
    ".codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.png",
    ".codex/concepts/P.2.26-hour-tick-labels-device.png"
  ],
  "completed_gates": [
    "P.2.26_fixture_readout_acceptance_resolved",
    "P.2.26_revised_behavior_board_plan_relationship_reviewed",
    "P.2.26_installed_acceptance_complete"
  ],
  "decisions": [
    "Owner selected option 1 on 2026-11-11: preserve the fixture/debug nearest whole-hour source-sample cue during sub-hour scrubbing while keeping the underlying fractional selection authoritative; the active-catalog readout remains minute precise."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Revised the P.2.26 behavior board, plan acceptance, and roadmap exit to reflect option 1; refreshed the behavior-board package approval and reviewed its relationship to the plan.",
    "Closed slice P.2.26 as DONE.",
    "Completed the approved implementation and installed acceptance on active-catalog and fixture/debug surfaces; evidence and the scoped outcome are recorded.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/127-p2-hour-tick-labels/README.md",
    ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-hourly-3-6-9.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-noon-scrub.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-midnight-after.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-daily-reference.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-focus-7am.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-focus-near-6am.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-noon-12pm.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-midnight-approach.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-hourly-midnight-after.png",
    ".codex/test-artifacts/127-p2-hour-tick-labels/fixture-debug-daily-reference.png",
    ".codex/history/127-p2-hour-tick-labels.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/127-p2-hour-tick-labels.md",
    "docs/ROADMAP.md",
    "docs/ROADMAP_SPECIFICATION.md",
    ".codex/test-artifacts/127-p2-hour-tick-labels/README.md",
    "scripts/codex_cycle.py"
  ],
  "next_action": "Cycle 127 is closed with P.2.26 DONE and the current pointer at IDLE; future work must begin from a newly planned cycle."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Objective and approved basis

Give users a readable local-time anchor at every existing longest tick while
hourly symbols and their source times remain visible together in the current
timeline window. The approved cadence is the existing longest-tick cadence of
every three local hours: the current eight-hour view shows `3`, `6`, and `9`.
The local noon and midnight marks read `12 PM` and `12 AM` so the repeated
`12` is distinguishable. Center each label immediately below the lower end of
its tick.

The revised approved behavior board at
`.codex/concepts/P.2.26-hour-tick-labels-behavior-board.md` and its SVG/PNG
governs both timeline surfaces, hourly-only visibility, label anchoring while
scrubbing, visible-bounds handling, the noon/midnight values, and unchanged
selection, weekday, symbol, and daily behavior. It also preserves each
surface's existing focused-time display: minute precision on the active-catalog
timeline and the nearest whole-hour source-sample cue on fixture/debug, while
the underlying fractional fixture selection remains authoritative. The
approved UI artwork board
at `.codex/concepts/P.2.26-hour-tick-labels-ui-artwork-board.md` and its
SVG/PNG governs placement and appearance. It overlays the labels on a fresh
installed Pixel 3 XL capture; the raw capture
`.codex/concepts/P.2.26-hour-tick-labels-device.png` is board context, not
slice acceptance evidence. Every package file and its SHA-256 fingerprint is
recorded in the cycle roster.

P.2.25 (cycle 126) is complete and supplies the selected weekday treatment on
both timeline surfaces. P.2.24 (cycle 124) supplies the unchanged 48 dp/hour
scale and 24 dp symbols at their existing `:30` midpoints.

### Bounded scope

- In `ElasticForecastTimeline.kt`, label each visible existing longest tick in
  fixture/debug hourly mode. Use the approved compact labels for the
  three-hour cadence and spell the 12-hour marks `12 AM` and `12 PM`. Keep
  intervening whole-hour and half-hour ticks unlabelled.
- In `CatalogTimeLabScreen.kt`, apply the same rule using each tick's local
  hour in `view.zone`. Keep labels attached to their corresponding ticks as
  the tape moves beneath the fixed playhead.
- In both renderers, center text immediately below the longest tick's lower
  endpoint. Draw a label only while its tick and full text bounds fit within
  the visible timeline. Label all visible longest ticks, including a tick at
  or near the focus; do not suppress a major label because it is close to the
  selected time. Preserve each surface's focused-time cue below the axis: the
  active-catalog readout remains minute precise, while fixture/debug retains
  its existing nearest whole-hour source-sample cue even when selection has
  sub-hour precision. Keep the cue distinct and readable. In the fixture
  renderer, the existing focused-time and major-label baselines nearly
  coincide inside its fixed 84 dp canvas; allocate
  separate, non-overlapping text bounds within that canvas while preserving the
  selected-time anchor, tick geometry, and host dimensions. Do not suppress the
  label at the focused tick. If this cannot be made readable within the
  approved geometry, record the evidence-backed blocker instead of changing an
  excluded dimension or hiding required content.
- Update only the relevant hourly tick/label wording in
  `docs/SPECIFICATION.md` and §4.3 of
  `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` to record the approved cadence and
  placement. In §4.3, replace the current “selective major-hour labels” wording
  with the every-visible-three-hour-tick rule, below-tick placement, blank
  intervening ticks, and `12 AM`/`12 PM`; in `SPECIFICATION.md`, add the same
  narrow P.2.26 clarification to the hourly-label contract. Keep the remaining
  selected-time, gesture, scale, and data contracts intact.

### Exclusions

Do not change longest-tick cadence, hourly spacing, half-hour tick positions,
symbol positions/sizes or event mapping, forecast values, missing-value
behavior, selected-time projection, weekday placement, scale transitions,
gesture ownership, timeline host dimensions, or daily artwork. Add no new
controls, state, data, or time-axis projection behavior. Do not touch the
deprecated oxygenWX app. RTL, TalkBack, reduced-motion, broad
device/system/environment matrices, and unrelated cleanup remain outside this
slice and belong to their designated TEST work. Automated tests and lint are
not authorized by this plan.

### Product and rendering invariants

- The selected `Instant` remains authoritative and centered at the existing
  playhead. Labels derive from their tick's local hour and never select or
  retarget time.
- The selected weekday remains above the symbol row and changes only when the
  selected local date crosses midnight. Keep the active-catalog selected time
  readable to the minute below the axis. Fixture/debug continues to display
  the nearest whole-hour source-sample cue; the underlying fractional
  `selectedHour` remains authoritative.
- Hourly weather symbols stay at their existing source-hour `:30` midpoints.
  Their mapping and icon-free behavior for missing/unsupported samples do not
  change. Daily mode contains no new hourly numerals.
- Preserve the current uniform 48 dp/hour scale, 24 dp symbols, gesture
  ownership, responsiveness, and readability of forecast facts and labels.
- Keep the existing `ElasticForecastTimeline` composable inputs and its
  `TimeLabScreen` caller unchanged. Keep `CatalogForecastTimeline` internal to
  `CatalogTimeLabScreen`; derive text from the existing fixture tick and
  `view.zone` local tick hour without changing projection, selection state,
  provider data, or view-model contracts.

### Implementation sequence

1. Update the hourly drawing paths in `ElasticForecastTimeline.kt` and
   `CatalogTimeLabScreen.kt`. Use their existing longest-tick cadence rather
   than adding ticks. Derive each label from the tick's local hour, use
   `12 AM`/`12 PM` at the local 12-hour marks, and leave intervening ticks
   blank. Render only when the tick and the full label rectangle fit within the
   canvas on both axes; center the label below the tick's lower endpoint and
   keep it attached during scrubs. Give the fixture tick labels and focused-time
   readout separate, measured vertical bounds within the existing 84 dp canvas,
   including when their centers coincide. Do not change the playhead, selected
   instant, host size, or hide a required tick label.
2. Update only the narrow hourly-label statements in the two product
   specifications. Replace the §4.3 “selective major-hour labels” phrase with
   the approved every-three-hour-long-tick cadence and placement; add the
   matching bounded P.2.26 wording to `SPECIFICATION.md`. Leave unrelated
   requirements unchanged.
3. Build and install with `python scripts/dev.py run --serial 8AKY0M32X`.
   Review the active-catalog timeline and launch the fixture profile using the
   documented debug activity. Use existing gestures to enter hourly mode and
   scrub; do not add a control or change gesture ownership. Capture the
   approved installed states on the connected Pixel 3 XL.
4. Use `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/127-p2-hour-tick-labels/<descriptive-name>.png` for each state. Record screenshot paths, device/build conditions, focused scrub
   outcomes, and limits in the evidence README. Inspect only this slice's
   responsiveness, gesture predictability, text bounds, and label/forecast
   readability; do not claim broad system acceptance.

### Focused acceptance and verification

On both the active-catalog and fixture/debug timelines, the installed hourly
view must show a label under every visible longest tick. In the current
eight-hour composition, `3`, `6`, and `9` are all present; intervening hour and
half-hour ticks have no labels. Labels at visible local noon and midnight say
`12 PM` and `12 AM`. Text remains centered on its tick and unclipped, even when
the selected hour is a long tick or a neighboring tick. When a tick label
shares the playhead center with the focused-time cue, both have distinct,
non-overlapping text bounds and remain readable below the axis; do not omit
either one. The catalog cue remains minute precise; fixture/debug retains its
nearest whole-hour source-sample format while the underlying fractional
selection remains authoritative. `WED` stays above the weather symbols,
symbols stay at `:30`, and forecast values remain readable and unchanged.

Focused installed review states:

1. Active-catalog hourly view matching the approved `7:00 AM` artwork: `3`,
   `6`, and `9` below the long ticks, with the weekday, symbols, focus readout,
   and forecast facts intact.
2. Fixture/debug hourly view positioned to show all three labels, including
   long ticks adjacent to and coincident with the focused hour.
3. Hourly scrubs across local noon and midnight on both surfaces: `12 PM` and
   `12 AM` remain anchored to their long ticks, the selected weekday changes
   at midnight, and the selected state continues to follow the gesture. The
   catalog cue shows the selected local time to the minute; fixture/debug
   retains the nearest whole-hour source-sample cue.
4. Return to daily mode on both surfaces and confirm that the new hourly
   numerals are absent and existing daily artwork is unchanged.

For these states, inspect label bounds at the viewport edges, label alignment
while scrubbing, focus/readout clarity, timeline responsiveness, predictable
gesture ownership, and forecast readability. Preserve actual installed
screenshots and interaction observations. `python scripts/dev.py workflow`
validates the cycle. During execution, use the focused debug build/install and
`python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/127-p2-hour-tick-labels/active-catalog-hourly-3-6-9.png`
for a listed device state, using a distinct descriptive filename for each
capture. Do not add or run automated tests, lint, or cross-cutting
system/environment checks without separate authorization.

### Evidence and known risk

Target evidence directory:
`.codex/test-artifacts/127-p2-hour-tick-labels/`. Store actual active-catalog
and fixture/debug hourly captures, noon/midnight scrub captures, daily
references, and a `README.md` recording exact commands, device conditions,
focused interactions, results, and limitations. Closeout targets are
`.codex/history/127-p2-hour-tick-labels.md` and the archived plan
`.codex/history/plans/127-p2-hour-tick-labels.md`; these are targets, not
existing evidence.

Visual risks reviewed on the installed Pixel 3 XL: viewport-edge labels and the
wider `12 AM`/`12 PM` strings were visible without clipping in the captured
states. The fixture renderer now labels the focused `6` tick and keeps the
nearest-hour cue readable in the existing canvas; see
`fixture-debug-hourly-focus-near-6am.png`. No approved state required an
excluded dimension or behavior change.

## Current planning limit

Cycle 127 was CLOSED on 2026-11-11 with P.2.26 DONE and both required board
packages and their fingerprints recorded. The owner resolved the fixture
readout question: active-catalog remains minute precise, and fixture/debug
retains its nearest whole-hour source-sample cue without changing the
authoritative fractional selection. P.2.26 implementation, narrow
specification updates, the authorized debug build/install, focused installed
acceptance, complete diff review, `git diff --check`, and workflow validation
are recorded above. The current cycle pointer is IDLE.
