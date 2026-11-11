# Cycle plan — 126-p2-hourly-weekday-label

State: CLOSED
Plan maturity: FINAL
Cycle ID: `126-p2-hourly-weekday-label`
Roadmap items: P.2.25
Objective: Keep the selected weekday visible throughout hourly scrubbing while preventing overlapping adjacent weekday text.

## Cycle roster and board approvals

This cycle contains one roadmap slice. Its dependency, observable exit, and
approved behavior and UI artwork board packages are recorded below.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "126-p2-hourly-weekday-label",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "P.2.25",
      "depends_on": [
        "P.2.24"
      ],
      "status": "DONE",
      "exit": "Installed active-catalog and fixture/debug hourly views show the selected date's uppercase weekday label above the timeline at representative selections whose midnight tick is outside the viewport. The focused label uses the selected emphasis shown in daily mode, remains centered over the playhead while scrubbing within a date, and changes when the selected local date crosses midnight without changing the selected instant. At visible midnight boundaries, the selected date has one focused label and its duplicate midnight text is suppressed while its tick remains. An adjacent weekday label stays at its midnight tick except while its text bounds overlap the focused label; then only the text is hidden, the tick remains, and the text returns as soon as the bounds separate. Capture a state with overlapping bounds and a readable state after the bounds clear on both timeline surfaces. Daily weekday labels, timeline symbols and ticks, hourly forecast facts, 48 dp/hour spacing, selected-time continuity, gesture ownership, responsiveness, and readability remain intact. Record actual screenshots and focused interaction outcomes on both timeline surfaces.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-10",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.md",
              "sha256": "763fe3aeb961a0a561fbaea047dcc775a88a9485da12771c1d0c79fd02bd1962"
            },
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.svg",
              "sha256": "52bb3feb4ed72899c5a9b4220ceec546366ad3ea88f65f589b8fb87792d1d078"
            },
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.png",
              "sha256": "f0c26f9443019a8696f3e619d4d97e7cd169e6a09068ee46d2521044a70516da"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.md",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-10",
          "artifacts": [
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.md",
              "sha256": "bb19e5d7f3945636889d3493c8220d694cd104c0e45520258687072a1abf6ea6"
            },
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.svg",
              "sha256": "57d94ae0eb47f75beef13632496962adbfad796f494a4b11ba0de21928f7ea29"
            },
            {
              "path": ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.png",
              "sha256": "c97a23c0d83f1092729cb7ea2e6092fae32e295625f56cc9776504ea8be68636"
            },
            {
              "path": ".codex/concepts/P.2.25-daily-dow-reference.png",
              "sha256": "f5b840807427c390eaf150a58b5f946b47cefc3ad51546dd42a9ff7279b06c0e"
            },
            {
              "path": ".codex/concepts/P.2.25-hourly-dow-before.png",
              "sha256": "f3ad00ac086e6e98588a69a4ff93c8220b43ea0f7d6c26d1691e94272d55210c"
            }
          ]
        }
      },
      "outcome": "Implemented the selected weekday on both timeline surfaces with adjacent-text suppression only during measured label overlap. Installed captures confirm label continuity, retained midnight ticks, restoration when bounds clear, the local-midnight update, and daily return with selected-time preservation. Debug build and workflow validation passed; automated tests and lint were not authorized.",
      "evidence": [
        ".codex/test-artifacts/126-p2-hourly-weekday-label/README.md",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-near-midnight-precision.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-restored.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-after-midnight-recheck.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-daily-return-11-59pm.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-away-from-midnight.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-overlap-before-midnight.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-midnight.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-restoration.png",
        ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-daily-return.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-10",
    "summary": "Closed cycle 126 / P.2.25 after implementing selected-weekday overlap suppression and completing installed acceptance on the active-catalog and fixture/debug timelines. Updated board packages, exact acceptance behavior, commands, captures, and remaining verification limits are archived.",
    "verification": [
      "python scripts/dev.py workflow — passed at EXECUTE and CLOSEOUT.",
      "python scripts/dev.py build — BUILD SUCCESSFUL in 7s.",
      "python scripts/dev.py run — debug app installed and launched successfully on Pixel 3 XL serial 8AKY0M32X.",
      "git diff --check — passed with no whitespace errors.",
      "Automated tests and lint — not run; plan did not authorize them."
    ],
    "evidence_directory": ".codex/test-artifacts/126-p2-hourly-weekday-label/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "126-p2-hourly-weekday-label",
  "slice_ids": [
    "P.2.25"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.md",
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.svg",
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.png",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.md",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.svg",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.png",
    ".codex/concepts/P.2.25-daily-dow-reference.png",
    ".codex/concepts/P.2.25-hourly-dow-before.png"
  ],
  "completed_gates": [
    "P.2.25_installed_acceptance_complete",
    "P.2.25_exit_verified"
  ],
  "decisions": [
    "Installed active-catalog and fixture/debug evidence confirms selected-weekday continuity, overlap-only adjacent-text suppression, retained midnight ticks, label restoration after clearing overlap, local-midnight updates, and daily return.",
    "No automation ran; RTL, TalkBack, font scale, reduced motion, and responsiveness profiling remain unchecked under slice scope."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Built and installed the updated debug app; captured and reviewed the planned hourly, boundary, restoration, and daily-return states on both surfaces; README records device conditions, commands, results, and limits.",
    "Closed slice P.2.25 as DONE.",
    "Recorded P.2.25 as DONE with installed acceptance evidence; all included slices have terminal outcomes and no owner questions remain.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-post-rebuild-hourly.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-recheck-scrub-1.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-10-41pm.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-near-midnight-precision.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-restored.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-after-midnight-recheck.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-daily-return-11-59pm.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-away-from-midnight.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-same-date-scrub.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-overlap-before-midnight.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-midnight.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-after-restoration.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-daily-return.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/README.md",
    ".codex/history/126-p2-hourly-weekday-label.md"
  ],
  "next_stage_read_set": [
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/126-p2-hourly-weekday-label.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "docs/SPECIFICATION.md",
    "docs/OXYGEN_EXPERIENCE_SPECIFICATION.md",
    "docs/UI_DEVELOPMENT_WORKFLOW.md",
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.md",
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.svg",
    ".codex/concepts/P.2.25-hourly-weekday-label-behavior-board.png",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.md",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.svg",
    ".codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.png",
    "app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/README.md",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/active-catalog-hourly-near-midnight-precision.png",
    ".codex/test-artifacts/126-p2-hourly-weekday-label/fixture-hourly-overlap-before-midnight.png"
  ],
  "next_action": "Run final closeout workflow and diff checks, then archive cycle 126 with exact verification results and evidence paths."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Objective and approved basis

Keep the selected local weekday visible in hourly scale on both existing
timeline surfaces, centered over the fixed playhead with the established
selected-label emphasis, while suppressing only adjacent weekday text that
overlaps it. P.2.25 depends on completed P.2.24, which established the shared
48 dp/hour hourly scale and 24 dp half-hour event symbols. P.2.25 adds only the
selected-date weekday treatment, duplicate-label rule, and approved overlap
handling.

The product owner approved both initial board packages on 2026-11-10 and
selected option 1 for near-midnight overlap handling on 2026-11-10. The updated
behavior board
`.codex/concepts/P.2.25-hourly-weekday-label-behavior-board.md` and its SVG/PNG
governs selected-date resolution, label visibility through scrubbing, the
local-midnight transition, suppression of a duplicate selected-date midnight
label, adjacent-text suppression while bounds overlap, midnight-tick retention,
restoration when bounds clear, and preservation of selection and gesture
behavior. The UI artwork board
`.codex/concepts/P.2.25-hourly-weekday-label-ui-artwork-board.md` and its SVG/PNG
governs the uppercase three-letter label, its fixed-playhead position and
selected emphasis, the unchanged daily reference, the 11:59 PM hidden-adjacent-
text state, and the adjacent-date label's return when bounds clear. Its two
embedded Pixel 3 XL captures are current-state reference context only. Updated
approval dates and SHA-256 fingerprints for every package file are recorded in
the manifest above.

### Bounded scope

- In `ElasticForecastTimeline.kt`, keep the selected fixture date's uppercase
  weekday visible over the fixed playhead throughout steady hourly mode,
  including when that date's midnight tick is outside the viewport. Resolve it
  from the existing selected-hour/day and fixture-date context.
- In `CatalogTimeLabScreen.kt`, do the same using the selected instant and the
  existing location zone. Keep the selected date label tied to the active
  local date.
- On both surfaces, use each renderer's existing selected-label emphasis and
  existing weekday row within the current timeline band. Keep other visible
  dates' weekday labels on their local-midnight ticks. When the selected date's
  midnight is visible, show its weekday only at the playhead; suppress the
  duplicate weekday text at that date's midnight while retaining its boundary
  tick. Suppress another date's weekday text only while its centered text
  bounds overlap the focused label's bounds; retain its local-midnight tick and
  restore the text as soon as the bounds separate. Do not add a fixed time or
  spacing threshold.
- Update the narrowly relevant selected-time/date-label passages in
  `docs/SPECIFICATION.md` and §4.3 of
  `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` to record this approved hourly
  visual rule. Preserve the existing selected-time, scale, data, and gesture
  contracts.

No changes are planned to call sites, presentation models, forecast data, or
the time-axis projection. Derive the label from current selection and existing
date context; do not introduce independent selected-date state.

### Exclusions

No change to daily label positions or styling, timeline host dimensions,
48 dp/hour spacing, `:30` symbol positions or sizes, hourly facts, event
mapping, missing-value behavior, selected instants, scale transitions, gesture
ownership, or accessibility actions. No new control row, forecast data, or
fixture. No work in the deprecated oxygenWX application. No RTL, TalkBack,
reduced-motion, broad device/system matrix, or unrelated timeline cleanup.
Do not add or run automated tests unless the user separately authorizes them.

### Product and architecture invariants

- The selected instant remains authoritative and projects to the fixed
  playhead. Label recomposition or scale animation never selects or retargets
  time.
- Resolve the active-catalog weekday from the selected instant in the active
  location's IANA zone. Resolve the fixture/debug weekday from its existing
  selected-hour/date context. A crossing of local midnight changes the label
  exactly when the selected local date changes.
- Draw one weekday label for the selected date in hourly mode. Its label stays
  centered over the playhead; other visible dates remain anchored to their own
  local midnight. Suppression removes only the duplicate text, not the date
  boundary tick. An adjacent date's text is hidden only while its bounds
  intersect the focused label's bounds, and is restored as soon as they clear.
- Preserve each renderer's approved label typography and row, daily artwork,
  48 dp/hour scale, 24 dp symbols at `:30`, forecast facts, missingness,
  responsiveness, readable labels, and existing gesture ownership.

### Implementation sequence

1. Change weekday-label drawing in `ElasticForecastTimeline.kt` and
   `CatalogTimeLabScreen.kt` only. Keep the selected local weekday at the fixed
   playhead in hourly mode and skip its duplicate midnight text while retaining
   the tick. Measure the focused and neighboring weekday text bounds using
   their rendered styles; suppress only a neighboring label whose bounds
   overlap the focused label, retain its tick, and draw it again as soon as the
   bounds separate. Keep all other midnight labels and the daily drawing path
   intact.
2. Update only the selected-time/date-label rule in `docs/SPECIFICATION.md` and
   the corresponding hourly timeline description in
   `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` §4.3. Keep roadmap scope and board
   acceptance aligned; do not alter unrelated requirements.
3. Build and install the debug app for the approved installed review. Capture
   active-catalog and fixture/debug hourly states away from midnight and near a
   local-midnight transition. On both surfaces, capture a readable adjacent
   weekday at its midnight tick and an overlap state with the adjacent text
   hidden and its tick retained. Include a state where the selected date's own
   midnight tick is visible so the single-label rule and retained tick can be
   inspected, plus daily-return states to confirm the existing daily labels.
4. On both surfaces, perform focused scrubs within one local date and across
   local midnight. Confirm that the label remains centered within the date,
   changes with the selected local date, and leaves the selected instant under
   the playhead. Confirm the adjacent label hides only while text bounds
   overlap, returns as soon as the bounds clear, and keeps its midnight tick.
   Inspect label readability, gesture ownership, responsiveness, and unchanged
   daily view.
   Record the actual outcomes and any limits; do not claim broader system
   acceptance.

### Verification authorization

At planning/review, run `python scripts/dev.py workflow` and inspect
`git diff --check` plus the scoped final diff. After an explicit activation,
the slice requires the workflow check, debug build/install/run needed for
installed acceptance, actual device screenshots, and the focused interactions
above. Use the repository's `python scripts/dev.py build`, `run`, and
`screenshot` commands as applicable and record exact invocations and results.
Automated tests and lint are not authorized by the current request and are not
part of this exit. Do not add or run them without a separate user instruction.
Broad accessibility and environment matrices belong to dedicated TEST slices.

### Evidence and limitations

Retain actual installed screenshots and a README under
`.codex/test-artifacts/126-p2-hourly-weekday-label/`; target the closeout at
`.codex/history/126-p2-hourly-weekday-label.md` and the archived plan at
`.codex/history/plans/126-p2-hourly-weekday-label.md`. The README records the
device/build, scale, selected local time and zone, capture filenames, focused
interaction results, exact commands, and unchecked conditions. The two current
device captures under `.codex/concepts/` establish the observed before state
and board context; they are not slice acceptance evidence.

The focused visual risk is duplicate or crowded weekday text where the
selected date's local midnight is in view. The owner-approved behavior board
resolves this by keeping one selected label at the playhead, suppressing its
duplicate midnight text, and hiding another date's text only during actual
bounds overlap while retaining its tick; installed boundary captures must
verify suppression at 11:59 PM and restoration as soon as bounds clear on both
surfaces.
