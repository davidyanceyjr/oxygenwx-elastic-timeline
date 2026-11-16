# Cycle plan — 143-ox04-scene-truth

State: CLOSED
Plan maturity: FINAL
Cycle ID: `143-ox04-scene-truth`
Roadmap items: OX-04
Objective: Protect First City scene truth and unknown states by preserving the support, source-time, and probability limits of selected weather evidence.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "143-ox04-scene-truth",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-04",
      "depends_on": [
        "OX-03"
      ],
      "status": "DONE",
      "exit": "Evidence matrix with fixture-backed evidence (with tests only if authorized) and at least one visible unknown-state check. Provide the scoped proof or explicit recorded disposition; visual claims require installed evidence when applicable.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-04-scene-truth-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-04-scene-truth-behavior-board.dot",
              "sha256": "4a448813d34a91488082322f5aa951887dbb984d9b4e581859c9198a2879d064"
            },
            {
              "path": ".codex/concepts/OX-04-scene-truth-behavior-board.svg",
              "sha256": "0241198d8753bae927c601eb2ef298e5c123eba273f84b3a696ad791cd41f126"
            },
            {
              "path": ".codex/concepts/OX-04-scene-truth-behavior-board.png",
              "sha256": "b5f9a8705ef2139ec8a2d2a86fe650ddc50efbbf541901731c9d87eb6750a900"
            },
            {
              "path": ".codex/concepts/OX-04-scene-truth-behavior-board.md",
              "sha256": "2bde9e16576ed0ca081b8ba387cb43d6f61deb7592179e770841141c69330575"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-16",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg",
              "sha256": "56fae875afebc22309da69de37dea54d196aeba39358ff753f02f5ce0fa1622a"
            },
            {
              "path": ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.png",
              "sha256": "26f6578b1142d4b7fcc4535c64998925e1b8cd9d67ba6acd57cac3636a167b34"
            },
            {
              "path": ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.md",
              "sha256": "6045987d9086d7c65b8ec3f74ab757fe496f969d057eb7b48862a366679951fe"
            }
          ]
        }
      },
      "outcome": "Completed the four-state fixture-backed evidence matrix and visible unknown-condition check on both active catalog routes. Owner selected unavailable selected-hour temperature; captures confirm the primary readout is already unavailable, and the visible 57° is the separately supported daily high. No production correction was needed. The cloud visual subcheck remains NOT RUN because the replay diagnostic overlay obscures the relevant region; app tests and lint were not authorized and were not run.",
      "evidence": [
        ".codex/test-artifacts/143-ox04-scene-truth/README.md",
        ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.png",
        ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.png",
        ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.png",
        ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.png",
        ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.png",
        ".codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.png",
        ".codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.png",
        ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.xml",
        ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.png",
        ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.xml"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-16",
    "summary": "Cycle 143 completed OX-04 with fixture-backed evidence for supplied rain, eligible carried rain, an unavailable condition with independent values, and the ineligible local-date leading gap. Both active catalog routes were captured and compared. The owner selected unavailable selected-hour temperature; installed captures already show the selected readout as unavailable, while 57° is the separately sourced daily high, so no production change was needed. The cloud visual subcheck remains not run because the diagnostic overlay obscures it; no app tests or lint were run.",
    "verification": [
      "python scripts/dev.py contract — PASS.",
      "python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; debug APK installed and MainActivity launched.",
      "Installed evidence — four fixture-backed states on primary composition and reading fallback routes, captured on Pixel 3 XL 8AKY0M32X at 1440x2960 and 560 dpi; eight screenshots and matching hierarchies inspected. Cloud visual subcheck: NOT RUN because the replay diagnostic overlay obscured the cloud band.",
      "python scripts/dev.py workflow — PASS in CLOSEOUT before archive.",
      "git diff --check — PASS before archive.",
      "App tests and lint — NOT RUN; not authorized by the plan."
    ],
    "evidence_directory": ".codex/test-artifacts/143-ox04-scene-truth/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "143-ox04-scene-truth",
  "slice_ids": [
    "OX-04"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-04-scene-truth-behavior-board.dot",
    ".codex/concepts/OX-04-scene-truth-behavior-board.svg",
    ".codex/concepts/OX-04-scene-truth-behavior-board.png",
    ".codex/concepts/OX-04-scene-truth-behavior-board.md",
    ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg",
    ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.png",
    ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-03_dependency_satisfied",
    "OX-04_behavior_board_approved",
    "OX-04_ui_artwork_board_approved",
    "OX-04_final_plan_review_passed",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "The 2026-11-16 owner approval covers the unchanged behavior and visual contracts; status-label updates only clarify where approval and current fingerprints are recorded.",
    "The OX-01f unavailable replay preserves the synthetic row's independent 40% cloud-cover input, 48°F temperature, and 25% precipitation chance while making the condition empty. Apply the approved board rule that independently supported fields retain their own semantics; do not suppress cloud output to imitate the isolated schematic panel.",
    "No material owner decision remains in the bounded OX-04 scope; no catalog-age threshold or plausibility correction is authorized.",
    "Product owner selected option 1: the selected-hour temperature is unavailable when its field slot has no support. Direct review of both installed carried-rain screenshots and matching hierarchies confirms the main selected-temperature readout already shows “—”; the visible 57° is the selected Sunday daily high labeled HI, sourced through the independent daily-summary projection. The reported mismatch was a screenshot interpretation error; no production correction is needed."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Aligned board status labels with the product-owner approval, regenerated and displayed both rendered boards, and refreshed the approved package fingerprints in the cycle manifest; board scope and artwork did not change.",
    "Reviewed and finalized the OX-04 plan against its approved boards, roadmap exit, relevant product/experience authority, source boundaries, compatibility constraints, and authorized verification.",
    "Reconciled the unknown-state example with the OX-01f replay constructor: its empty condition does not remove independent cloud-cover, temperature, or probability fields. Execution will classify those fields separately and will not require the schematic's isolated no-cloud appearance when the fixture supplies cloud evidence.",
    "Added the existing SG4 leading missing slot as an explicit ineligible cross-local-date carry boundary. It uses the approved unavailable-condition behavior and existing appearance without adding fixture data or a new artwork requirement.",
    "Cycle 143 is ready for owner activation; no implementation or execution work has started.",
    "Activated OX-04 for execution.",
    "Ran python scripts/dev.py contract — PASS.",
    "Ran python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; debug APK installed and MainActivity launched.",
    "Captured all four fixture-backed states on both active catalog routes; route-pair hierarchy text and content-description sequences match.",
    "Found an unresolved carried-rain temperature discrepancy: both settled screenshots show 57°F while the corresponding hierarchies and selected-slot support report temperature unavailable; no source or fixture edit and no slice outcome were recorded.",
    "Resolved the owner question with option 1. Reinspection of both installed carried-rain routes confirmed the selected-hour temperature is already rendered unavailable; the visible 57° is the separately labeled Sunday daily high. No production change was indicated.",
    "Updated .codex/test-artifacts/143-ox04-scene-truth/README.md with the corrected carried-rain attribution, owner disposition, verification, and explicit cloud-overlay limitation.",
    "Reran python scripts/dev.py contract — PASS; python scripts/dev.py workflow — PASS; git diff --check — PASS. No app tests or lint were run.",
    "Closed slice OX-04 as DONE.",
    "OX-04 marked DONE after its approved evidence matrix, both-route comparison, and visible unknown-state check were complete; Cycle 143 is ready for CLOSEOUT.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/OX-04-scene-truth-behavior-board.svg",
    ".codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg",
    ".codex/plans/143-ox04-scene-truth.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/136-ox01f-debug-condition-gap/README.md",
    ".codex/test-artifacts/142-ox03-scene-decision-contract/README.md",
    ".codex/test-artifacts/143-ox04-scene-truth/README.md",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.xml",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.xml",
    ".codex/history/143-ox04-scene-truth.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    "AGENTS.md",
    ".agents/skills/oxygen-execute-plan/SKILL.md",
    ".codex/plans/143-ox04-scene-truth.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/143-ox04-scene-truth/README.md",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.png",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.png",
    ".codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.png"
  ],
  "next_action": "Review the complete final diff and evidence record, run git diff --check and workflow validation, then close Cycle 143 into history with exact verification results and limitations."
}
```
<!-- cycle-checkpoint:end -->

## Board package status

- Behavior package: `.codex/concepts/OX-04-scene-truth-behavior-board.dot`, `.svg`, `.png`, and `.md` — approved by the product owner on 2026-11-16. The cycle manifest above records each package artifact's SHA-256 fingerprint.
- UI artwork package: `.codex/concepts/OX-04-first-city-unknown-state-ui-artwork-board.svg`, `.png`, and `.md` — required for visible unknown-state acceptance and approved by the product owner on 2026-11-16. The cycle manifest above records each package artifact's SHA-256 fingerprint.
- The behavior board constrains evidence-to-scene decisions; the artwork board constrains the existing First City appearance in supplied, carried, and unknown states. Approval clears the board gate only; it does not authorize implementation or cycle activation.

## Objective, scope, invariants, verification, and evidence

### OX-04 scope

**Objective:** Prevent absent, older, unsupported, or probability-only evidence from being rendered or described as confidently observed weather. Preserve the strength, support, and source time of each selected field from `SceneReading` through `FirstCityComposition` to the active catalog scene and forecast foreground.

**Dependency:** OX-03 is complete. Its shared `SceneReading` → `FirstCityComposition` contract and primary/reading-fallback route parity are the boundary for this cycle. Cycle 142's captures are reference material only; this cycle must collect and classify its own evidence.

**Approved board authority:** The product owner approved both OX-04 board packages on 2026-11-16. The behavior board governs supplied, eligible carried, unavailable/unsupported, probability-only, independent-field, and exact-selected-time decisions in active catalog First City. The UI artwork board governs the supplied-rain, carried-rain, and unavailable-condition appearance, including a visible unknown state that retains independent temperature and chance facts without condition-derived weather marks. Their full package paths and fingerprints are in the cycle manifest. The artwork is schematic and does not count as installed evidence. Approval does not authorize an age threshold, new input semantics, implementation, or scope beyond OX-04.

**In scope:**

- Trace supported weather evidence and its per-field source/support metadata from the selected `SceneReading` through `FirstCityComposition`, the active composition route and active reading fallback, and the visible forecast foreground.
- Verify supplied supported conditions retain the existing category-to-scene mapping without independent plausibility correction.
- Verify that only the established field-specific carry rule can represent an older condition at an expected slot; retain the exact selected instant and show the older `sourceValidTime` distinctly wherever the condition is disclosed.
- Verify empty, unsupported, or ineligible missing condition evidence makes no condition-derived claim. Preserve other independently supported values and independently supported scene fields under their existing support semantics.
- Verify precipitation probability remains a chance, not a condition, observed rainfall, amount, or rate. Probability by itself must not create a confident weather-scene claim.
- If source review or an installed comparison demonstrates a mismatch, make only the smallest production correction needed to satisfy the approved evidence contract on the active First City path. If the current behavior already satisfies it, make no production change and record that evidence-based disposition.
- Compare the active primary composition route and active reading fallback for every matrix state; keep state-only and fixture/Time Lab preview paths distinct.

**Out of scope:**

- Any independent meteorological plausibility audit, catalog-age freshness cutoff, new carry rule, provider/schema change, data migration, inferred value, default category, zero substitution, interpolation, or new condition mapping.
- Changes to weather inputs or support rules, forecast layout/readout redesign, timeline gestures, city artwork beyond the approved evidence states, scene-depth or renderer extraction, and sun/moon styling.
- State-only runtime behavior, fixture/Time Lab preview routes, other locations/catalogs, live data, alerts, and product-role or Scene Engine decisions.
- RTL, TalkBack, broad accessibility, localization, motion, or device/system matrices assigned to dedicated TEST slices.
- App tests unless separately authorized by the product owner.

### Fixture-backed evidence matrix

At execution, record the typed evidence/support, source time, exact selected instant, composition decisions, visible weather claim, independent foreground values, both route results, and pass/fail/not-run status for each row. Reuse the existing replay inputs without changing fixtures.

| Evidence state | Existing replay and selected instant | Required disposition |
|---|---|---|
| Supplied supported rain | `sg4-canonical-gaps-v8`, `2026-10-04T15:30:00Z` (10:30 AM CDT) | Preserve supplied rain and existing OX-03 composition, 57°F and 75% chance. Report the percentage only as probability; do not describe it as rain amount/rate. Keep the selected instant exact on both active catalog routes. |
| Eligible carried rain; no visible cloud decision | `sg4-canonical-gaps-v8`, `2026-10-04T16:30:00Z` (11:30 AM CDT); condition source `10:00 AM CDT` | Preserve the existing carry eligibility, carried-rain composition, no-cloud scene, and distinct source-time disclosure. Do not retarget the scene clock to the older source time or infer clouds. |
| Condition unavailable with independent cloud cover, temperature, and probability-only precipitation | `ox01f-condition-unavailable-typed-v1`, `2026-10-28T20:00:00Z` (3:00 PM CDT) | Show condition unavailable and no condition-derived weather marks; retain 48°F and 25% chance. The current fixture also supplies 40% cloud cover, which keeps its existing independent scene semantics. The chance alone creates neither a rain claim nor a measured rate. This is the required visible unknown-state check. |
| Ineligible leading missing condition; no same-date source | `sg4-canonical-gaps-v8`, `2026-10-05T05:30:00Z` (12:30 AM CDT); expected condition slot `2026-10-05T05:00:00Z` (12:00 AM CDT) | Keep the condition unavailable at the first expected slot of the local date. Do not carry the prior day's 8:00 PM rain across the date boundary. Each other field follows its own support at the selected slot. |

The third row is the approved visible example of an unavailable condition with probability-only precipitation, independent temperature, and independent cloud-cover evidence. The OX-01f replay constructor changes the condition/event at 3:00 PM but preserves the synthetic row's 40% cloud cover, 9°C temperature (displayed as 48°F), and 25% chance; verify the current replay input at execution. The artwork's isolated unknown panel assumes no separately supplied cloud-cover, precipitation-amount, or rate evidence, while its constraint says independently supplied fields keep their existing semantics. Apply that constraint to the actual fixture: do not suppress independently supported cloud output to force a literal match with the schematic. The acceptance target remains an unavailable condition with no condition-derived weather claim, while independent evidence retains its own attribution.

The fourth row makes the existing SG4 leading-gap boundary explicit. Its absent `2026-10-05T05:00:00Z` slot is the first expected condition slot on the local date; the preceding supplied rain at 8:00 PM CDT on October 4 is not eligible for same-date carry. The selected replay instant is the existing representative midpoint, `2026-10-05T05:30:00Z`. Capture and classify it under the behavior board's unavailable-condition rule, without adding a new visual design state. For any additional explicit-empty or unsupported typed case, use an existing fixture if one is available; otherwise record the case as not represented by the current fixture set and do not invent data or expand this slice to add a fixture. The canonical condition type is closed; do not manufacture an unknown enum value.

### Ordered work

1. Confirm replay IDs, slot construction, and source data for the matrix rows in `DebugCatalogActivity.kt`, `Sg4GapCatalogFixture.kt`, `DebugSg11StormFallbackCatalogFixture.kt`, `SimulatedCatalogSource.kt`, and `CanonicalWeatherFacts.kt`. Record the empty/carry state, each field's support, and each source time; do not modify fixtures.
2. Review the exact selected-time and per-field support/source-time path in `SemanticScenePlan.kt` and `CatalogTimelinePresentation.kt`; identify each row's typed evidence without interpreting unsupported meteorological relationships.
3. Trace `FirstCityTheme.compose`, the active `AtmosphericScene` callers, and `CatalogTimeLabScreen` route/overlay placement to verify which condition and independently supported fields create scene claims on the primary composition route and reading fallback. Keep state-only and preview callers out of acceptance.
4. Compare the source contract with the four fixture-backed rows above. Correct a demonstrated mismatch only within the approved behavior/artwork constraints; otherwise record the source and installed evidence as a no-change disposition.
5. Run the authorized scoped contract/build/install flow after activation. Capture the three artwork-approved states on both active routes and capture the SG4 leading-gap row on both routes as a behavior-only evidence state using the existing unavailable-condition appearance; do not create a fourth artwork requirement. Inspect the unavailable-condition captures against the artwork board. Verify the relevant scene area is visible; the OX-01f debug diagnostic overlay can cover the cloud band, so an occluded image cannot establish absence or presence of cloud output. Record any unreachable state or obscured visual subcheck as not run; do not substitute another fixture silently.
6. Write the cycle evidence README with the matrix results, exact commands, screenshots and matching UI hierarchies, device/viewport details, route setup, and limitations. Update the OX-04 roadmap outcome and archive the completed plan and closeout only after its exit is met or a terminal incomplete outcome is recorded.

### Functional invariants

- `SceneReading.selectedInstant` remains authoritative for composition and the forecast foreground. An older `sourceValidTime` is evidence provenance, not a replacement selected time.
- Each weather field is evaluated independently. Missing condition evidence cannot fabricate, zero, or erase a separately supported value; another supplied field cannot fill the missing condition.
- Only existing eligible same-date interior carry semantics may carry a condition, and the original source time remains available for disclosure. No catalog-age threshold is introduced.
- Supplied categories are faithfully mapped by existing semantics; no cross-field plausibility correction is added.
- Probability remains probability. A chance alone cannot become a categorical condition, measured amount/rate, or confident rain scene.
- The unknown example retains the selected-time cue and supported facts while making no condition-derived weather claim. Any visible independently supported field remains attributable to that field.
- Forecast text remains available and is not replaced by decorative weather rendering.

### Verification and installed acceptance plan

The cycle is still unactivated. At execution, run only after activation and stay within this slice:

- Read-only source review of the scoped presentation and route files plus the replay constructors named in ordered work; verify input evidence and per-field source times before assessing route reachability, composition decisions, and visible output.
- `python scripts/dev.py contract` for the repository's scoped transport/workflow contract.
- Check device availability with `adb devices -l`. Cycle 142 used Pixel 3 XL serial `8AKY0M32X` at `1440 x 2960`, `560 dpi`; confirm availability and current viewport at execution rather than assuming it is still connected.
- If available, use `python3 scripts/dev.py run --serial <serial>` to build/install/launch, activate each exact replay and selected instant from the matrix on the primary composition route and the active reading fallback, then capture each state with `python3 scripts/dev.py screenshot --serial <serial> --output .codex/test-artifacts/143-ox04-scene-truth/<state>-<route>.png`. Save a matching UI hierarchy beside each screenshot. Follow cycle 142's recorded route setup only as a starting reference and verify the current activity state before capture.
- Inspect actual screenshots for exact selected time, displayed source time for carry, condition availability wording, retained temperature/chance, independent cloud treatment, and attribution of any scene marks. Record supplied rain and carried rain separately from the unavailable-condition states, and record that the leading gap does not carry across the local date. Do not infer acceptance from a build, source review, or schematic board. If the debug overlay hides the relevant cloud region, record that visual subcheck as not run.
- Do not add or run app tests without separate authorization. Do not run lint or broaden device/accessibility checks absent a scoped need and authorization.

If the target device is unavailable or a fixture state cannot be reached, record the precise check as not run and give its blocker; do not claim installed acceptance. No tests, builds, or device checks are authorized during this planning stage.

### Compatibility, evidence, and closeout

- No fixture, replay schema, catalog/provider contract, or persistence format change is planned. Reuse the existing typed presentation and active catalog routes.
- Evidence target: `.codex/test-artifacts/143-ox04-scene-truth/README.md` and the state/route screenshots plus matching UI hierarchies recorded there. OX-03 artifacts remain reference-only.
- Final cycle record: `.codex/history/143-ox04-scene-truth.md`; archived plan: `.codex/history/plans/143-ox04-scene-truth.md`. Preserve actual verification commands and limitations in both.
- `docs/ROADMAP.md` remains `NEXT` while this plan is PLANNED. Change it only on an evidence-supported slice outcome at closeout.

### Risks and constraints

- The prior cycle's Pixel and replay setup may no longer be available; check at execution and record exact limitations.
- The approved unavailable-condition replay retains independent cloud-cover evidence, so an unknown condition does not imply a cloud-free scene. Attribute each visible mark to its own typed support and require the absence of condition-derived claims. The debug overlay may obscure the cloud band; an occluded capture cannot establish the cloud result.
- The reference captures establish OX-03 behavior only. Re-capture and review OX-04 states before claiming its exit.
- Approval fingerprints cover the complete board packages listed in the manifest. Any board edit changes the approved input and requires renewed owner review before plan activation.
