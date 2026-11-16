# Cycle plan — 141-ox02a-tests

State: CLOSED
Plan maturity: FINAL
Cycle ID: `141-ox02a-tests`
Roadmap items: OX-02A-TESTS
Objective: Run focused #DEBUG device tests for the OX-02A catalog fallback acceptance states.

## Cycle roster and board approvals

Final plan recorded against the unchanged, product-owner-approved OX-02A behavior
and UI artwork packages. The manifest retains their original 2026-11-15 approval
metadata and fingerprints, plus their relationship to this duplicate test slice.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "141-ox02a-tests",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-02A-TESTS",
      "depends_on": [
        "OX-02A"
      ],
      "status": "DONE",
      "exit": "On the connected Pixel 3 XL debug build, capture composition and reading routes at matched instants for supported rain, an unavailable condition with any supplied independent facts, and an eligible carried condition with its original source-valid time and disclosure. Record the exact catalog, selected instant, supplied facts, route trigger, visible scene/condition, selected-time readout, and capture paths. Perform a direct reading-route timeline selection check and record whether the selected instant remains correct. Recheck state-only route callers; retain that route and mark scene-present runtime appearance unverified if no actual debug caller exists. Record every mismatch and limitation; do not consolidate renderers or change production code in this test slice.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
              "sha256": "c761e6d6eb56ec4757473f3148ae59822d95111162428b9f8d813ebfaab1e6bf"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot",
              "sha256": "020addc5bda328f5b05d6a6bf4cf1a28867b657a544f2d278d4e4879cef324c9"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.png",
              "sha256": "88a5e20f0177c8f2d30325f081050a9a86e738e2d1130f7b85bd10fc07ae6194"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.md",
              "sha256": "ca1d54b451b5b1039e27cebf5a83932e8c4d178f9be631f264b68300bf2f1f20"
            }
          ],
          "reuse_basis": "Unchanged 2026-11-15 product-owner-approved OX-02A board package reused for OX-02A-TESTS because this is a test duplicate of the same acceptance states and appearance."
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-15",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
              "sha256": "aebf042c6446381588ad1307c1c74bd93689ef2ed657907a7ceac80b33d81789"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.png",
              "sha256": "2f521972568fe9f110265d9c95863cbfc3e784c5ee18c374818ca492e82acb60"
            },
            {
              "path": ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.md",
              "sha256": "7db421cda3b5757a6002f959fe9d49ea4b9ae324ea525d9efb8058701c44966d"
            }
          ],
          "reuse_basis": "Unchanged 2026-11-15 product-owner-approved OX-02A board package reused for OX-02A-TESTS because this is a test duplicate of the same acceptance states and appearance."
        }
      },
      "outcome": "Completed all three matched #DEBUG device comparisons, the reading-route timeline interaction, and state-only caller recheck. Recorded the supported-rain and eligible carried-rain scene mismatch between composition and reading routes; condition-unavailable facts and disclosures matched. No production behavior changed.",
      "evidence": [
        ".codex/test-artifacts/141-ox02a-tests/README.md",
        ".codex/test-artifacts/141-ox02a-tests/supported-composition.png",
        ".codex/test-artifacts/141-ox02a-tests/supported-reading.png",
        ".codex/test-artifacts/141-ox02a-tests/unavailable-composition.png",
        ".codex/test-artifacts/141-ox02a-tests/unavailable-reading.png",
        ".codex/test-artifacts/141-ox02a-tests/carried-composition.png",
        ".codex/test-artifacts/141-ox02a-tests/carried-reading.png",
        ".codex/test-artifacts/141-ox02a-tests/reading-interaction.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-15",
    "summary": "Completed the focused OX-02A-TESTS duplicate on the connected Pixel 3 XL using existing #DEBUG replays. Supported rain and eligible carried rain appear in composition but their rain streaks are absent in the reading fallback; the condition-unavailable state retains its supplied temperature, precipitation chance, wind, and unavailable condition in both routes. The reading timeline interaction updated the selected time. No production code or test fixture changed; the state-only scene-present runtime appearance remains unverified.",
    "verification": [
      "python3 scripts/dev.py run --serial 8AKY0M32X — PASS; debug build assembled, installed, and launched on Pixel 3 XL.",
      "python3 scripts/dev.py screenshot --serial 8AKY0M32X — PASS; seven installed screenshots captured under .codex/test-artifacts/141-ox02a-tests/. Seven corresponding UIAutomator dumps are present and non-empty.",
      "Matched #DEBUG captures: supported rain at 2026-10-04T15:30:00Z showed rain streaks in composition and none in reading; carried rain at 2026-10-04T16:30:00Z showed the same scene mismatch while preserving the 10:00 AM CDT source disclosure; unavailable condition at 2026-10-28T20:00:00Z retained matching supplied values.",
      "adb -s 8AKY0M32X shell input swipe 720 2550 760 2550 500 — PASS; reading-route selection changed from 10:30 AM to 8:54:14 AM CDT with the fact sample at 9:00 AM CDT.",
      "rg -n 'CatalogTimeLabScreen\\s*\\(' app/src/main/java app/src/androidTest — PASS; production presentation caller found, only view-only caller is scene=null in CatalogForecastBrowserErrorAccessibilityTest; scene-present route appearance remains unverified.",
      "python scripts/dev.py workflow — PASS at CLOSEOUT; codex_cycle.py close validated the archived state and returned the cycle pointer to IDLE.",
      "git diff --check — PASS, exit code 0.",
      "No app test suite, lint, TalkBack, RTL, localization, or broad device/environment matrix was run; only the authorized Pixel 3 XL debug slice was exercised."
    ],
    "evidence_directory": ".codex/test-artifacts/141-ox02a-tests/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "141-ox02a-tests",
  "slice_ids": [
    "OX-02A-TESTS"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.dot",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.md",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.png",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.md"
  ],
  "completed_gates": [
    "OX-02A_TESTS_duplicate_slice_scope_recorded",
    "OX-02A_TESTS_OX02A_dependency_verified",
    "OX-02A_TESTS_behavior_board_reused_from_cycle_140",
    "OX-02A_TESTS_ui_artwork_board_reused_from_cycle_140",
    "OX-02A_TESTS_board_package_fingerprints_reverified",
    "OX-02A_TESTS_reused_boards_accepted",
    "OX-02A_TESTS_approved_package_fingerprints_match",
    "OX-02A_TESTS_final_plan_drafted",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "OX-02A-TESTS_exit_met",
    "matched_debug_route_captures_recorded",
    "reading_route_interaction_recorded",
    "state_only_caller_recheck_recorded"
  ],
  "decisions": [
    "Owner directed the focused test follow-up to use the stable ID OX-02A-TESTS as a duplicate of OX-02A.",
    "Unchanged 2026-11-15 product-owner-approved OX-02A board package reused for OX-02A-TESTS because this is a test duplicate of the same acceptance states and appearance."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Added OX-02A-TESTS as the sole NEXT roadmap slice, dependent on completed OX-02A, and created cycle 141 at SLICE.",
    "Mapped the unchanged cycle-140 approved behavior/UI artwork packages to OX-02A-TESTS; all package fingerprints match.",
    "Verified and fingerprinted the unchanged cycle-140 behavior and UI artwork boards for reuse under OX-02A-TESTS.",
    "Completed final OX-02A-TESTS plan with matched device cases, exact replay IDs/instants, route interaction evidence, boundaries, and debug-only fixture allowance.",
    "Activated OX-02A-TESTS for execution.",
    "Closed slice OX-02A-TESTS as DONE.",
    "Completed OX-02A-TESTS on Pixel 3 XL: matched supported-rain, condition-unavailable, and carried-condition captures; reading-route selection interaction; state-only caller recheck.",
    "Recorded all seven installed screenshots, seven corresponding UIAutomator dumps, route setup, exact replay activations, results, mismatch, and limits in .codex/test-artifacts/141-ox02a-tests/README.md.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    "docs/ROADMAP.md",
    ".codex/history/140-ox02a-catalog-renderer-fallbacks.md",
    ".codex/history/plans/140-ox02a-catalog-renderer-fallbacks.md",
    ".codex/concepts/OX-02A-catalog-fallbacks-behavior-board.svg",
    ".codex/concepts/OX-02A-catalog-fallbacks-ui-artwork-board.svg",
    ".codex/plans/141-ox02a-tests.md",
    ".codex/test-artifacts/141-ox02a-tests/README.md",
    ".codex/test-artifacts/141-ox02a-tests/supported-composition.png",
    ".codex/test-artifacts/141-ox02a-tests/supported-reading.png",
    ".codex/test-artifacts/141-ox02a-tests/unavailable-composition.png",
    ".codex/test-artifacts/141-ox02a-tests/unavailable-reading.png",
    ".codex/test-artifacts/141-ox02a-tests/carried-composition.png",
    ".codex/test-artifacts/141-ox02a-tests/carried-reading.png",
    ".codex/test-artifacts/141-ox02a-tests/reading-interaction.png",
    ".codex/history/141-ox02a-tests.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/141-ox02a-tests.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/141-ox02a-tests/README.md"
  ],
  "next_action": "Run final workflow validation and git diff checks, inspect the final scoped diff and evidence index, then archive cycle 141 with exact verification results."
}
```
<!-- cycle-checkpoint:end -->

## Objective and relationship to OX-02A

`OX-02A-TESTS` is the test duplicate of completed slice `OX-02A`, created at the owner's direction after cycle 140 closed with supported-rain mismatch evidence. It completes the focused device checks that were left unrun. It does not reopen or rewrite cycle 140, alter OX-02A's recorded outcome, or authorize renderer consolidation. The existing OX-02A board packages are reused unchanged because they define the same route, state, and appearance acceptance criteria.

## Scope

- Use the connected Pixel 3 XL (`8AKY0M32X`) with the Android `debug` build and existing `#DEBUG` catalog picker/launch controls.
- Compare the catalog composition route with the reading fallback at the exact same selected instant for supported rain, a condition-unavailable state with any actually supplied independent facts, and an eligible carried-condition state.
- Record actual catalog/replay ID, location/time zone, selected instant, supplied fact values, route trigger, selected-time readout, condition/symbol, scene output, and source-time disclosure. Do not infer independent values or visual parity from fixture source alone.
- Capture the composition reference and reading candidate separately for every state. On the reading route, perform a direct timeline selection interaction and record the selected time before and after it.
- Recheck source callers for the state-only branch. If no real debug caller exists, retain its existing unverified disposition; do not claim runtime appearance or invent a new production caller.
- Make no production renderer or forecast-behavior changes. A dedicated catalog may be added only in `app/src/debug/` if the existing fixtures fail to supply a required test state. Do not add broad accessibility/system checks.

## Test matrix and known fixture inputs

| State | Planned debug input | Expected source-level setup; installed result remains to be observed |
|---|---|---|
| Supported rain | `sg4-canonical-gaps-v8`, Florence, Alabama, `2026-10-04T15:30:00Z` (`10:30 AM CDT`) | Cycle 140's installed evidence used this state and recorded rain at `10:00 AM CDT`; repeat the matched route captures in this cycle. |
| Condition unavailable | `ox01f-condition-unavailable-typed-v1`, Florence, Alabama, `2026-10-28 3:00 PM CDT` | The debug fixture clears condition events and conditions at its handoff slot. Read the installed UI dump to establish which independent facts are actually supplied. |
| Eligible carried condition | `sg4-canonical-gaps-v8`, Florence, Alabama, `2026-10-04 11:30 AM CDT` | The replay has an expected-missing `11:00` condition slot between supported `10:00 RAIN` and `12:00 CLEAR`. The presentation source defines this as eligible for carrying the previous condition; verify the installed source-time disclosure and scene. |

For each row, first capture the composition route, then activate the same replay and instant through the existing `sg2_null_weather` debug trigger and capture the reading route. The trigger sets the timeline scene to null only in the debuggable app; the screen then uses the reading fallback. If the fixture or route does not present the planned state, record the observed inputs and stop to determine whether a debug-only catalog is needed rather than substituting a guessed case.

## Invariants and acceptance

- The selected instant remains authoritative across route changes and timeline interaction.
- Preserve supplied facts faithfully. An unavailable condition stays unavailable; a carried condition retains its source-valid time and visible disclosure.
- The composition route remains primary. Any observed mismatch is recorded; no parity claim or renderer consolidation is authorized in this test slice.
- The state-only scene-present route remains retained and unverified if no actual caller and trigger are found.
- Pass the slice by recording the three matched installed state comparisons, the reading-route interaction result, the state-only reachability recheck/disposition, exact commands, and all limitations. A mismatch is a valid test outcome, not a reason to change production behavior within this slice.
- No app-wide device, RTL, TalkBack, accessibility, localization, or system matrix is included.

## Authorized verification

The owner directed use of the connected device and `#DEBUG` mode, and permits an existing replay catalog or a new debug-only test catalog. The current device is Pixel 3 XL serial `8AKY0M32X`. Planned commands are:

- `python3 scripts/dev.py run --serial 8AKY0M32X` to assemble/install/launch the debug variant.
- `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/141-ox02a-tests/<capture>.png` for actual installed captures.
- Use the debug replay picker for the named catalog IDs and matched selected instants. Use the existing debug-only `sg2_null_weather=true` launch trigger for the reading route, recording the exact `adb` invocation used.
- Inspect UIAutomator dumps for selected time and semantic/source-time readouts where visible; save them under the cycle evidence directory.
- Recheck callers with `rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java app/src/androidTest`.

Do not run unrelated tests or lint. If a focused catalog fixture must be created, keep it under the debug source set and record the exact fixture source/inputs in the evidence README. No fixture creation is currently assumed because the named replays already exist.

## Evidence and closeout

Store captures, UI dumps, and an exact test log under `.codex/test-artifacts/141-ox02a-tests/README.md`. At closeout, inspect `git diff --check` and the final scoped diff, run `python scripts/dev.py workflow`, and archive the test results under `.codex/history/141-ox02a-tests.md` with this plan. Record that only the connected Pixel 3 XL debug path was exercised and list all untested system/device states.
