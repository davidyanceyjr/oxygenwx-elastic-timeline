# Cycle plan — 173-selected-time-mural-reading

State: CLOSED
Plan maturity: FINAL
Cycle ID: `173-selected-time-mural-reading`
Roadmap items: OX-12, OX-12A
Objective: Expose and paint one exact selected-time forecast reading on the Park mural while preserving canonical evidence and keeping the central readout primary.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "173-selected-time-mural-reading",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-12",
      "depends_on": [
        "OX-04",
        "OX-03"
      ],
      "status": "DONE",
      "exit": "For selected temperature, a supported source sample and an unavailable projection produce one consistent value/evidence contract usable by the central readout and future surface, preserving the source-valid time and °F display unit where supported. Missing temperature stays unavailable; no value is carried or interpolated. Existing eligible carried-condition evidence remains separate and retains its original source time.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
              "sha256": "a7bc2561aa9e2bf2f1691e47a2bf4473caff7e37ebf993ed2edf2d31fe553726"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.svg",
              "sha256": "ca96f0afcfcf4fe41826701f842d6d1be317686eeec6bdb52bf3fe483c1694e4"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.png",
              "sha256": "e949ceb552ee58553d13176da39e30decd426b7e5c9c646d64438256621ee048"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.md",
              "sha256": "9669129d98443d11ee023dd1386bff3ba72bfd3ab7f156df7cf842525cfc0915"
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
      "outcome": "Rechecked the selected-temperature binding on the correct Oct 4 SG.4 route after tracing the apparent source mismatch to an Oct 3 selection. At Oct 4 10:30 AM CDT, the binding exposes the supported 10:00 AM sample as 57°F; at 11:30 AM it remains explicitly unavailable with the 10:00 AM condition evidence separate. No additional binding code change was needed.",
      "evidence": [
        ".codex/test-artifacts/173-selected-time-mural-reading/README.md",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12-canonical-forecast-projection-test.xml",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-wrong-date-time-diagnostic.png",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-selection-diagnostic.png",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-selection-diagnostic.png"
      ]
    },
    {
      "id": "OX-12A",
      "depends_on": [
        "OX-12",
        "OX-42",
        "OX-37"
      ],
      "status": "DONE",
      "exit": "Installed portrait supported and unavailable temperature cases show a wall-integrated reading whose values, °F unit, source-valid time and unavailable meaning match the canonical central binding; no invented or carried temperature appears. Existing eligible condition carry remains field-specific and its original source time stays disclosed in the central experience.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
              "sha256": "a7bc2561aa9e2bf2f1691e47a2bf4473caff7e37ebf993ed2edf2d31fe553726"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.svg",
              "sha256": "ca96f0afcfcf4fe41826701f842d6d1be317686eeec6bdb52bf3fe483c1694e4"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.png",
              "sha256": "e949ceb552ee58553d13176da39e30decd426b7e5c9c646d64438256621ee048"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.md",
              "sha256": "9669129d98443d11ee023dd1386bff3ba72bfd3ab7f156df7cf842525cfc0915"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-173-selected-time-mural-reading-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-22",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-ui-artwork-board.svg",
              "sha256": "2e4260dd9dc46c4995b057f5f2bda5b633ca2721f1f2719b00b507f4db21645b"
            },
            {
              "path": ".codex/concepts/OX-173-selected-time-mural-reading-ui-artwork-board.png",
              "sha256": "aba688daaf7cc5327f5fd1e0efc38a323b3e4f3024bb6d3d06a8d12193a28067"
            }
          ]
        }
      },
      "outcome": "Installed portrait acceptance passed for the Oct 4 SG.4 target. At 10:30 AM CDT the central readout and mural agree on the supported temperature, and the mural preserves °F and the 10:00 AM source time. At 11:30 AM the central temperature and mural are explicitly unavailable, with no carried temperature; the separate rain condition retains and discloses its 10:00 AM source time. Both captures were inspected on the Pixel 3 XL; broader RTL, TalkBack, and system acceptance were outside this slice.",
      "evidence": [
        ".codex/test-artifacts/173-selected-time-mural-reading/README.md",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-selection-diagnostic.png",
        ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-selection-diagnostic.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-22",
    "summary": "Cycle 173 completed OX-12 and OX-12A in dependency order. The typed selected-time binding passed focused projection verification, and installed Park mural acceptance passed for the supported Oct 4 10:30 AM and unavailable Oct 4 11:30 AM SG.4 states. The apparent mismatch that prompted reopening OX-12 came from an Oct 3 route, not the Oct 4 target; corrected diagnostics matched the binding, so no additional binding code change was needed.",
    "verification": [
      "python scripts/dev.py workflow -> Workflow scaffold: OK at the final CLOSEOUT checkpoint.",
      "git diff --check -> passed with no output.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.CanonicalForecastProjectionTest -> BUILD SUCCESSFUL; 12 tests, 0 failures, 0 errors.",
      "./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest -> BUILD SUCCESSFUL.",
      "./gradlew :app:compileDebugAndroidTestKotlin -> BUILD SUCCESSFUL; compile-only compatibility check.",
      "python scripts/dev.py run --serial 8AKY0M32X -> debug build, install, and launch succeeded.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png -> captured and inspected; Oct 4 10:30 AM CDT shows 57°F valid at 10:00 AM.",
      "python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png -> captured and inspected; Oct 4 11:30 AM CDT shows unavailable/no value and separate condition source disclosure from 10:00 AM.",
      "adb uiautomator clean-state hierarchy inspection -> 10:30 description includes temperature source at 10:00 AM; 11:30 description reports unavailable temperature and separate 10:00 AM condition carry."
    ],
    "evidence_directory": ".codex/test-artifacts/173-selected-time-mural-reading/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "173-selected-time-mural-reading",
  "slice_ids": [
    "OX-12",
    "OX-12A"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.svg",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.png",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.md",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.dot",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.svg",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.png",
    ".codex/concepts/OX-173-selected-time-mural-reading-behavior-board.md",
    ".codex/concepts/OX-173-selected-time-mural-reading-ui-artwork-board.svg",
    ".codex/concepts/OX-173-selected-time-mural-reading-ui-artwork-board.png"
  ],
  "completed_gates": [
    "Both behavior board approvals and the OX-12A artwork approval are recorded with matching package fingerprints; OX-12 artwork is correctly not applicable.",
    "Independent exits, dependencies, boundaries, verification paths, authorization status, installed evidence targets, and closeout paths are complete; the existing named-argument CatalogTimelineView constructor consumer is recorded for source compatibility only.",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "The owner selected OX-12 then OX-12A for Cycle 173.",
    "The owner selected temperature in the current Imperial unit (°F) and confirmed supported/unavailable temperature with preserved source-valid time and separate condition carry.",
    "The product owner approved the exact board package artifacts with preparation-stage approval-pending labels still present; keep those artifacts and fingerprints unchanged.",
    "The user explicitly instructed execution of Cycle 173 on 2026-11-22, authorizing the scoped implementation and the checks listed in the plan; Cycle 172 authorization is not reused.",
    "The user resolved the OX-12A device precondition by choosing to save Chicago locally through the existing location selector, then continue with the SG.4 replay route.",
    "No response arrived within 60 seconds after the discrepancy prompt, so the recommended OX-12 reopen was applied; the user later confirmed that same recommendation on 2026-11-22.",
    "The Oct 3 10:30:14 diagnostic was a wrong-date route. The corrected Oct 4 10:30 selection shows the 10:00 AM supported source and Oct 4 11:30 shows unavailable temperature with condition carry separate."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reopened OX-12 after the first OX-12A route appeared to contradict its binding; OX-12A returned to PLANNED temporarily.",
    "The overlay showed the apparent mismatch was Oct 3, not the Oct 4 fixture target; corrected Oct 4 diagnostics confirmed the 10:30 supported source and 11:30 unavailable state without an additional code change.",
    "Closed OX-12 DONE after the correct-date binding recheck and focused projection test evidence.",
    "Completed OX-12A installed portrait acceptance at Oct 4 10:30 and 11:30, inspected clean screenshots and targeted UI hierarchy descriptions, and preserved diagnostic captures.",
    "Updated the plan narrative to reflect Cycle 173 execution authorization and actual completed verification.",
    "Both OX-12 and OX-12A have independent DONE outcomes and recorded evidence.",
    "The correct Oct 4 diagnostics resolved the apparent supported-source mismatch as a wrong-date Oct 3 selection; no additional binding code change was needed.",
    "Installed portrait screenshots and clean-state hierarchy captures establish the planned supported and unavailable states.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/173-selected-time-mural-reading/README.md",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12-canonical-forecast-projection-test.xml",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-setup-no-saved-locations.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-wrong-date-1030-diagnostic.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-wrong-date-time-diagnostic.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-selection-diagnostic.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-time-hierarchy.xml",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-1130-diagnostic.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-selection-diagnostic.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-time-hierarchy.xml",
    ".codex/history/173-selected-time-mural-reading.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/plans/173-selected-time-mural-reading.md",
    ".codex/test-artifacts/173-selected-time-mural-reading/README.md",
    "app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt",
    "app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt",
    "app/src/test/java/com/oxygen/weather/timeline/presentation/CanonicalForecastProjectionTest.kt",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-time-hierarchy.xml",
    ".codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-time-hierarchy.xml"
  ],
  "next_action": "Run final workflow and diff checks against the closed slice records, confirm evidence paths and exact verification results, then archive Cycle 173."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Shared objective and board authority

Expose one exact selected-time temperature reading from the existing canonical catalog projection and paint that same typed reading on the reserved Park mural. Preserve the authoritative selected instant, supported source sample, canonical Celsius value, current Imperial display value and unit, original source-valid time, and explicit unavailable state. Keep the central readout primary, and keep condition carry as separate field evidence.

The product owner approved the complete behavior package on 2026-11-22. The identical four-file package and approval are recorded separately on OX-12 and OX-12A. The product owner also approved the complete UI artwork package on 2026-11-22 for OX-12A. The manifest stores every package file fingerprint on the slice that consumes it. The displayed 48°F and 12:00 PM artwork is specimen content only.

The approved behavior and artwork files retain preparation-stage wording such as “approval pending.” The owner approved those exact rendered files with that wording present. Treat the approval record and unchanged fingerprints in this manifest as authoritative; do not edit or regenerate the approved package within this cycle.

The behavior board governs field-scoped temperature support, Fahrenheit projection, source-time preservation, unavailable behavior, and the independent exits for both slices. The artwork board governs supported and unavailable appearances in the existing mural reservation, with the central reading primary. The package does not merge slice exits or authorize implementation checks.

### Ordered slices

#### OX-12 — Expose one exact selected-time forecast binding

- **Dependency/order:** First in this cycle and the sole eligible NEXT slice. Depends on completed OX-04 and OX-03. OX-12A depends on this outcome.
- **Scope:** Create one typed temperature binding at the canonical presentation boundary. It retains the authoritative selected instant separately from the original source-valid time. A supported result carries the existing canonical Celsius value, projected display value and display unit (currently °F); an unsupported or absent field produces an explicit unavailable result. The central temperature text derives from this binding without changing its current visual hierarchy or rounded appearance.
- **Implementation boundary:** Reuse the field-specific supported-point selection in CatalogTimelinePresentation.kt for CatalogField.TEMPERATURE_C, then use CanonicalForecastProjection.kt to obtain the canonical/display measurement. Do not select from the decorative scene transition, borrow support from another field, or move forecast selection into Compose. Preserve the sample valid time in the binding even when a selected instant lies within its representative display cell.
- **Constructor compatibility:** Add the typed binding to CatalogTimelineView in a source-compatible way so the existing named-argument construction in app/src/androidTest/java/com/oxygen/weather/timeline/ui/CatalogForecastBrowserErrorAccessibilityTest.kt remains valid. This is a compile-time consumer only; it does not add Android or accessibility acceptance to Cycle 173.
- **Independent exit:** For selected temperature, a supported source sample and an unavailable projection produce one consistent value/evidence contract usable by the central readout and future surface, preserving the source-valid time and °F display unit where supported. Missing temperature stays unavailable; no value is carried or interpolated. Existing eligible carried-condition evidence remains separate and retains its original source time.
- **Acceptance evidence:** A focused presentation test exercises a supported Fahrenheit sample with selected instant distinct from source-valid time and unavailable temperature at an expected missing point. It also verifies that condition carry remains separate and retains its source time. The relevant existing test file is CanonicalForecastProjectionTest.kt; this focused test change was authorized by the user's 2026-11-22 request to execute Cycle 173.
- **Verification authorization:** Authorized by the user's 2026-11-22 request to execute Cycle 173. The focused test and scoped test edit were completed; exact results are in the cycle evidence README.
- **Evidence target:** `.codex/test-artifacts/173-selected-time-mural-reading/README.md`; record the exact focused test command/result and any not-run limitation. No visual artifact is required for this slice.
- **Visible output:** None. Do not add mural layout or change central readout styling in this slice.

#### OX-12A — Paint the selected reading on the Park mural

- **Dependency/order:** Second, after OX-12. External dependencies OX-42 and OX-37 are complete.
- **Scope:** Pass the same typed binding through the catalog scene presentation to the Park mural renderer. Within the existing declared MURAL surface and reserved text region, paint the supported rounded value with the binding’s display unit and original sample valid time, formatted using the selected location’s time zone; paint explicit unavailable text when no supported temperature exists. Keep the current Park world, mural geometry, depth order, selected time, central readout, and timeline intact.
- **Implementation boundary:** Use CatalogTimeLabScreen.kt to carry the presentation binding into the Living Urban Park path and AtmosphericScene.kt to render the wall content within the existing MURAL data-surface declaration. Read the Park surface identity and reserved region from LivingUrbanParkComposition.kt. Rendering does not query a catalog, convert units, select another time, or interpolate/carry temperature. Keep the mural a secondary visual echo; the central forecast text remains the primary accessible path. Preserve the existing central source-time disclosure for carried conditions without reinterpreting that condition evidence as temperature.
- **Independent exit:** Installed portrait supported and unavailable temperature cases show a wall-integrated reading whose values, °F unit, source-valid time and unavailable meaning match the canonical central binding; no invented or carried temperature appears. Existing eligible condition carry remains field-specific and its original source time stays disclosed in the central experience.
- **Installed acceptance:** On the connected Pixel 3 XL, install the debug app, open the debug catalog picker from the app, select the existing SG.4 canonical-gap replay, and select its “10:30 supported rain / 11:30 missing midpoint” inspection target (which initializes selection at 11:30). Choose Living Urban Park in the world picker, then enter hourly mode using the established timeline double-tap behavior; that daily-to-hourly action selects 7:00 AM on the touched local date under P.2.22. Perform two timeline scrubs: 7:00 → 10:30 supported, then 10:30 → 11:30 unavailable. Capture the supported 10:30 and unavailable 11:30 portrait states. The fixture’s expected temperature support includes the missing 11:00 point while its canonical record is absent, so the 11:30 case exercises unavailable temperature alongside the existing carried-condition case. At each captured state compare the wall value/unavailable text with the central readout, verify the wall’s °F label and original sample-valid time against the typed binding and the central fact/source-time disclosure, confirm the selected time follows the established selection, and inspect central facts and timeline readability. At 11:30, verify the eligible condition carry remains separate and its original time remains visibly disclosed. Record actual viewport/insets and inspect both captures. Reconfirm device connectivity at execution; serial 8AKY0M32X was connected during planning.
- **Verification authorization:** The user's 2026-11-22 request to execute Cycle 173 authorized the focused composition check, debug build/install, planned SG.4 route, two timeline scrubs, screenshots, and scoped inspection. Results and limits are recorded in the cycle evidence README.
- **Evidence targets:** `.codex/test-artifacts/173-selected-time-mural-reading/README.md`, `ox-12a-supported-temperature-portrait.png`, and `ox-12a-unavailable-temperature-portrait.png`.
- **Pixel acceptance boundary:** This cycle does not claim the later OX-21 measured 16 sp-equivalent typography or camera-fit exit. If the approved reading cannot be shown within the existing reserved region while preserving the primary readout and timeline, record the result and stop for owner disposition; do not expand the camera, mural geometry, or typography scope here.

### Product and architecture invariants

- The selected Instant remains authoritative. Source-valid time is separate metadata and is never substituted for the selected time.
- Temperature comes only from the supported TEMPERATURE_C point for its own field. Fahrenheit is provided by canonical presentation projection; the renderer consumes the projected value and unit without converting.
- Supported temperature keeps its original sample valid time. Missing/unsupported temperature is unavailable, never zero, carried, or interpolated.
- Existing eligible interior condition carry is unchanged, field-specific, and disclosed with its original condition source time. It does not populate the mural temperature.
- Keep the synthetic DEMO DATA label and the existing primary central forecast text. The mural is secondary, and neither decoration nor a visual transition becomes a forecast fact.
- Preserve existing timeline selection, gesture ownership, responsiveness, Park data-surface identity/reservation, and declared depth order. No new surface, selection mechanism, or other-world behavior.

### Exclusions and compatibility

No temperature carry/interpolation; no condition binding on the mural; no field or unit inference in drawing; no alternate catalog search; no forecast/provider DTO in UI; no change to eligible condition carry; no other-world data surface; no mural wall material/theme redesign; no change to Park geometry, camera or path; no OX-21 portrait typography/camera fitting; no OX-48 disclosure redesign; no broad RTL, TalkBack, accessibility, localization, performance or system/device matrix; no production provider, cache, alert, location or deprecated oxygenWX work.

Compatibility stays within the existing typed presentation-to-Compose flow. Keep other CatalogTimeLabScreen routes and decorative worlds unchanged; only the Living Urban Park MURAL surface consumes the new binding. Keep the existing named-argument CatalogTimelineView construction in app/src/androidTest/java/com/oxygen/weather/timeline/ui/CatalogForecastBrowserErrorAccessibilityTest.kt source-compatible; this call is a compile-time boundary only and adds no Android/accessibility test authorization.

### Verification authorization and commands

The user explicitly requested execution of Cycle 173 on 2026-11-22. That authorization covers the scoped implementation checks below; Cycle 172 authorization was not reused. The recorded results, device context, and limitations are in `.codex/test-artifacts/173-selected-time-mural-reading/README.md`.

Slice-scoped verification:

1. For OX-12, add or update only the focused canonical presentation test described above and run it with `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.CanonicalForecastProjectionTest`. Confirm the typed supported/unavailable contract, °F projection, selected/source time separation and independent condition carry.
2. For OX-12A, run the directly affected composition test with `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest` if its public composition/render seam changes. Build, install and launch with `python scripts/dev.py run --serial <connected-pixel-serial>`. Use the app’s debug catalog picker with the SG.4 gap replay, then capture screenshots with `python scripts/dev.py screenshot --serial <connected-pixel-serial> --output .codex/test-artifacts/173-selected-time-mural-reading/<name>.png`. Perform the two hourly timeline scrubs specified above and inspect both states.
3. Run `python scripts/dev.py workflow` after plan/implementation record changes and `git diff --check` before closeout. These planning/record checks are permitted and do not replace authorized implementation or installed acceptance checks.

No tests beyond the scoped commands above were run. Do not claim acceptance from a build, preview, or screenshot that was not actually captured and inspected. The evidence README records exact commands, device/build, selected instants, and limitations.

### Evidence targets and closeout

- Cycle evidence directory: `.codex/test-artifacts/173-selected-time-mural-reading/`.
- Evidence README: `.codex/test-artifacts/173-selected-time-mural-reading/README.md`, recording exact verification commands/results, approved test changes, device model/build/viewport/insets, replay/profile, selected/source instants, scrub outcomes, and limitations.
- OX-12A supported capture: `.codex/test-artifacts/173-selected-time-mural-reading/ox-12a-supported-temperature-portrait.png`.
- OX-12A unavailable capture: `.codex/test-artifacts/173-selected-time-mural-reading/ox-12a-unavailable-temperature-portrait.png`.
- Cycle closeout target: `.codex/history/173-selected-time-mural-reading.md`, with independent OX-12 and OX-12A outcomes, exact verification, actual evidence, and any not-run or failed gates.
- At plan review these paths were targets only. Execution evidence is now preserved under `.codex/test-artifacts/173-selected-time-mural-reading/` and indexed by the slice manifest and evidence README.

### Plan-stage record

At plan review the cycle was PLANNED and unactivated, with OX-12 as NEXT and OX-12A as PLANNED. Following the user's execution request, both slices completed in dependency order. The checkpoint and closeout record carry the final state, outcomes, evidence, and verification limits.
