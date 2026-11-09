# Cycle plan — 121-mr1-main-readout-refactor

State: CLOSED
Plan maturity: FINAL
Cycle ID: `121-mr1-main-readout-refactor`
Roadmap items: MR.1
Objective: Refactor the main weather readout into the Florence golden-night hero and focused-time precipitation, wind, and humidity row.

## Cycle roster and board approvals

The behavior and UI artwork boards below were approved by the product owner on
2026-11-08; their package fingerprints are recorded in the cycle manifest.
The product owner approved mapping Open-Meteo's
hourly `apparent_temperature` through the app's data and presentation path on
2026-11-08 to fulfill the already approved feels-like hero fact. Open-Meteo
documents the field as an instantaneous, source-backed feels-like value.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "121-mr1-main-readout-refactor",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "MR.1",
      "depends_on": [],
      "status": "DONE",
      "exit": "The installed main screen matches the approved hero and three-indicator composition with no opaque cards; the three supporting values and hero facts update for the focused timeline timestamp, including unavailable-value presentation; side indicators and secondary readout values are absent; the interactive skyline and timeline remain responsive and readable. Record installed screenshots and the directly affected interaction observations.",
      "boards": {
        "behavior": {
          "path": "design_draft/main_readout_behavior_board.svg",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-08",
          "artifacts": [
            {
              "path": "design_draft/main_readout_behavior_board.svg",
              "sha256": "4d4d4caf13d4443c34a6f033ba8e69643201a812ead93a6ea3c37fba7caac5ca"
            },
            {
              "path": "design_draft/main_readout_behavior_board.png",
              "sha256": "fcaf5a01855a6a9feca7beedc8b0cd413666777cb5239f9cb1d1914e27f2fdb6"
            },
            {
              "path": "design_draft/main_readout_behavior_board.md",
              "sha256": "230a90a1aacd864b77b085d086d1bc46bb9c498a14adf93b8ecb08d632196928"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": "design_draft/Florence Golden Night Weather Dashboard.png",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-08",
          "artifacts": [
            {
              "path": "design_draft/Florence Golden Night Weather Dashboard.png",
              "sha256": "c570311ccc3e5cb2be27b892f2e47134e2d5c348e9b7ecaa7665506fde17c75e"
            }
          ]
        }
      },
      "outcome": "Installed main readout matches the approved hero and transparent three-value row at supported, second-time, missing-value, and compact states; focused values and replay contract passed scoped tests. Scene orbit and timeline implementation unchanged. Lint remains blocked by three unrelated debug picker errors.",
      "evidence": [
        ".codex/test-artifacts/121-mr1-main-readout-refactor/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-08",
    "summary": "MR.1 main readout refactored to approved gold-arc hero with source-backed apparent temperature and transparent Precip/Wind/Humidity row. Removed solar/lunar event lines and high/low from main readout only. Scene orbit and timeline implementation unchanged. Installed supported, second-time, unavailable, and compact states captured under .codex/test-artifacts/121-mr1-main-readout-refactor/.",
    "verification": [
      "Workflow, contract, build, run, git diff --check, six individually run scoped methods, and installed readout observations passed. Lint failed on three unrelated StateFlowValueCalledInComposition errors in debug CatalogPickerDelegate.kt lines 146, 152, and 204. No RTL, TalkBack, motion, large-font, multi-device, or full-suite acceptance claimed. Evidence: .codex/test-artifacts/121-mr1-main-readout-refactor/README.md."
    ],
    "evidence_directory": ".codex/test-artifacts/121-mr1-main-readout-refactor/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "121-mr1-main-readout-refactor",
  "slice_ids": [
    "MR.1"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    "design_draft/main_readout_behavior_board.svg",
    "design_draft/main_readout_behavior_board.png",
    "design_draft/main_readout_behavior_board.md",
    "design_draft/Florence Golden Night Weather Dashboard.png"
  ],
  "completed_gates": [
    "Both required board packages are approved and their package fingerprints match.",
    "MR.1 has an observable exit, no roadmap dependencies, a bounded UI and data path, and an authorized verification path.",
    "Confirmed that the current catalog and timeline presentation do not expose apparent temperature; humidity exists in canonical facts but is not exposed by CatalogTimelineView.",
    "Confirmed from https://open-meteo.com/en/docs that hourly apparent_temperature is an instantaneous feels-like value combining wind chill, humidity, and solar radiation; the app does not currently request or map that field.",
    "final_plan_reviewed",
    "all_slice_boards_approved"
  ],
  "decisions": [
    "2026-11-08 user clarification: sunrise/moonrise removal is main-readout only; sun/moon scene orbit remains. Timeline changes are out of scope; preservation observations do not authorize timeline edits."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented MR.1 canonical/provider/replay/presentation path and main readout only; no scene or timeline implementation changes.",
    "Closed slice MR.1 as DONE.",
    "MR.1 DONE with installed screenshots, scoped test logs, readout-only diff review, build/contract/workflow pass, and unrelated lint failure recorded.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/121-mr1-main-readout-refactor/README.md",
    ".codex/history/121-mr1-main-readout-refactor.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md, .codex/plans/121-mr1-main-readout-refactor.md, .codex/test-artifacts/121-mr1-main-readout-refactor/README.md, docs/ROADMAP_SPECIFICATION.md"
  ],
  "next_action": "Close the completed MR.1 cycle, preserving exact pass results and unrelated lint limitation."
}
```
<!-- cycle-checkpoint:end -->

## Approved apparent-temperature contract

The product owner's 2026-11-08 approval resolves the scope choice in favor of
including Open-Meteo's hourly `apparent_temperature` in MR.1. The approved
behavior board already shows it as a focused-time hero fact, so its visible
outcome and the roster exit stay the same.

- Request the hourly field in Celsius and map the provider's supplied value to
  a nullable canonical metric field with independent point support. Do not
  calculate wind chill or apparent temperature in the app, and do not infer a
  value from air temperature, wind, humidity, or a neighboring hour.
- Carry the field through recording/replay and the focused-time presentation
  boundary. Older recordings and simulated facts without the field remain
  valid and display apparent temperature as unavailable. Preserve their
  existing facts and the project's missing-value semantics.
- Convert to display units only in presentation. The hero displays the value
  for the authoritative focused instant; a missing value does not suppress
  independently supplied temperature, condition, precipitation, wind, or
  humidity.
- Keep this contract within MR.1's approved readout outcome. No new provider,
  local meteorological formula, detail view, mini-graph, or unrelated catalog
  field is included.

## MR.1 boundary and approved boards

**Objective.** Replace the regular catalog main-screen readout with the approved Florence hierarchy: location and picker icon, gold semicircular arc containing the selected condition symbol, large white temperature, cyan `≈` apparent temperature, condition text, and a transparent lower row with exactly Precip, Wind, and Humidity. The existing skyline and timeline stay interactive. The plan covers the catalog readout and its necessary data path; it does not redesign the separate fixture screen, debug workbench, browser, or location flow.

**Board relationship.** The owner-approved behavior SVG, its PNG preview and Markdown contract govern selected-time selection, independent missing values, source disclosure, gesture boundaries, and the precise main-readout content. The owner-approved Florence PNG governs hierarchy, arc, typography, color, spacing, transparent row, and skyline overlap. Their approval date, owner, paths, and matching SHA-256 package fingerprints are in the manifest. The artwork's timeline daily highs are timeline content; the board removes daily high/low from the **main readout**. Preserve the existing daily-high timeline cells.

**In scope.** Canonical apparent-temperature support; Open-Meteo hourly request, unit validation, mapping and point support; strict recording/replay and storage compatibility; typed focused-time view values; main-screen Compose layout and affected semantics; installed visual and direct interaction review. Humidity is already canonical but needs its own focused-time presentation field. Existing simulated and legacy replay catalogs may have no apparent value.

**Out of scope.** Local feels-like formulas or fallbacks, new provider, changes to scene meaning, mini-graphs/detail screens, timeline redesign, location selection, live cache policy, official alerts, and broad RTL/TalkBack/device matrices. Keep old secondary values in canonical data and other existing consumers; remove them only from this main readout.

## Dependency-ordered implementation steps

1. **Canonical contract and replay.** Add nullable Celsius apparent temperature and an independent `CatalogField` point field. Include it in fact equality/hash/copy, field support/coverage and empty-fact logic, preserving null versus zero. Extend the strict `RecordedCatalogCodec` with a new schema version and its own fact-field shape: write/read the new field and expected support, decode versions 1–5 unchanged with a null apparent value, and widen `CatalogDatasetStorage`'s accepted payload version. Keep old stored rows loadable and newly encoded LIVE, SIMULATED and REPLAY rows consistent. Account for positional `CanonicalWeatherFacts` construction and all exhaustive `CatalogField` consumers. Do not change stored row identity or database schema solely for this field.
2. **Provider mapping.** Add hourly `apparent_temperature` to `OpenMeteoTransport.HOURLY`; keep Celsius units. Add its expected point support, strict unit check, nullable hour mapping, and usable-hour accounting in `OpenMeteoCatalogMapper`. Apply the existing window-boundary handling. A null or absent provider value stays unavailable and does not affect independent fields. Keep raw capture and recording of the mapped value consistent.
3. **Focused-time projection.** Extend the typed catalog presentation boundary with apparent-temperature and humidity display values. Select each with its own supported point at the authoritative `selectedInstant`; convert Celsius to display Fahrenheit in presentation and format humidity as percent. Add any differing point-source times to the existing source-time disclosure. Keep temperature, condition/event text and symbol, precipitation chance, wind, status, and source semantics intact. Missing apparent temperature must not erase temperature or other values; zero humidity remains `0%`. Keep any accessibility text derived from typed values.
4. **Main readout.** Replace the current center temperature/symbol/solar/lunar/high-low lines and current two-column lower strip in `CatalogTimeLabScreen.kt` with the board's hero and three equal support items. Draw a thin gold arc as decoration and place the current selected-time condition symbol inside it. Render the apparent line with cyan `≈`, and explicit independent unavailable states for every missing metric. Preserve the existing condition-source and source-time disclosures where they are needed for honesty, plus status/source/freshness access outside the removed visual lines. Preserve the location picker, main-area swipe/tap/double-tap handlers, skyline, timeline, browser entry, and diagnostic overlay; do not let the new row intercept timeline gestures. Maintain readable labels and meaningful semantics on the affected readout.
5. **Scoped acceptance and closeout.** Run only the MR.1-specific checks listed below, then inspect the final diff against both boards and this boundary. Record the exact verification performed, pass/fail/not-run by acceptance state, evidence, limitations, and MR.1 outcome before cycle closeout.

## Verification authorization and evidence

The user's 2026-11-08 review instruction authorizes **MR.1-specific tests**, including a compact-view check. Add or adjust only test methods necessary to verify the changed readout and its required data path. Run those methods individually with Gradle `:app:testDebugUnitTest --tests <fully-qualified-class.method>` and record the exact method names and results; `python scripts/dev.py test` runs the full unit suite and is outside this authorization. Existing version-5 assertions directly invalidated by the new recording version may be updated, but unrelated existing tests are not expanded or run. No instrumentation suite, full-suite test, broad system matrix, or unrelated regression test is authorized. The execution plan may also use `python scripts/dev.py workflow`, `contract`, `build`, `lint`, `run`, and `screenshot` as applicable; record exact commands/results. A successful build or preview alone does not satisfy the installed exit.

The focused automated checks are:

1. **Canonical field:** one method verifies apparent-temperature null versus zero, independent point support/coverage, and fact copy/equality/empty-fact behavior. It exercises only the new field contract.
2. **Provider field:** one transport method verifies that `apparent_temperature` is requested in Celsius. One mapper method verifies a supplied value maps to the matching hourly point, while absent/null values stay unavailable without erasing independent supplied fields. Exercise the changed unit validation and window boundary with the same fixture only where those paths are affected.
3. **Recording compatibility:** one method verifies the new encoded field and expected support round-trip; one method decodes a representative older recording with apparent temperature unavailable; one focused storage method verifies the new payload version is accepted. Update directly affected version assertions as needed for the new schema. These checks do not retest unrelated archival or database behavior.
4. **Focused-time presentation:** one method projects two selected instants and verifies apparent temperature and humidity display values, independent unavailable and zero behavior, and source-time disclosure when the selected point differs. It confirms the new values follow the selected instant without changing the existing one.

Tie each check to its implementation step: canonical and recording checks after step 1, provider check after step 2, presentation check after step 3, then installed visual and direct interaction checks after step 4. Record test command output or JUnit XML under `.codex/test-artifacts/121-mr1-main-readout-refactor/`; a failed or unrun check is reported explicitly.

On the Pixel 3 XL reference viewport, capture installed regular-main-screen screenshots for (a) supported selected-time hero and three values, (b) a second selected time after scrubbing, and (c) independent unavailable apparent or support value. Also capture **one compact portrait viewport** of the same MR.1 readout state and check only the changed hero, three-item row, and timeline labels for clipping, overlap, and readability; record its exact viewport size, density, and font scale. This is a slice-local layout check, not a device or font-scale matrix. If a source-backed apparent value cannot be obtained on the installed device, record that limitation and leave its corresponding installed acceptance not-run rather than claiming it; the other installed states still need review. Compare displayed values with the source-backed/canonical focused facts, including source-time disclosure and null versus zero, without evaluating weather plausibility. Observe main swipe, timeline scrub and scale/collapse, location picker reachability, skyline response, labels/readability, and absence of side columns and secondary readout lines. Keep the timeline daily highs distinct from the removed main-readout values. Do not claim RTL, TalkBack, broad device acceptance, or tests that were not run.

Store screenshots and an observation log at `.codex/test-artifacts/121-mr1-main-readout-refactor/`, with a README mapping each acceptance state to its evidence and pass/fail/not-run result. Check `git diff --check`, inspect the final diff, and on completion archive the plan and closeout under `.codex/history/` with exact results and limits. The MR.1 exit is the manifest exit above; a missing required installed state or direct interaction observation prevents an unqualified completed outcome.

## Risks and execution notes

- The strict recording codec currently requires exact fact fields and writes schema version 5; changing the fact shape requires a versioned decode branch, storage gate update, and old-row check. Existing tests assert version 5; update only assertions directly invalidated by this schema change under the scoped test authorization above.
- Current provider fixtures and synthetic profiles may lack `apparent_temperature`. Their hero must show unavailable, while a live provider value needs its own installed evidence to prove the supported state. Do not fill the gap with a formula or neighbor value.
- The artwork is a composition target over a dynamic scene. Match hierarchy and transparency while preserving selected condition and actual location data; do not hard-code Florence, a night scene, or the artwork's sample numbers.
