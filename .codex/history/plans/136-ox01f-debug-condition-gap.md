# Cycle plan — 136-ox01f-debug-condition-gap

State: CLOSED
Plan maturity: FINAL
Cycle ID: `136-ox01f-debug-condition-gap`
Roadmap items: OX-01f
Objective: Resolve the OX-01R debug replay condition gap and selected-condition disagreement under an owner-approved 3 PM support rule.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "136-ox01f-debug-condition-gap",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-01f",
      "depends_on": [
        "OX-01R",
        "SG.11e"
      ],
      "status": "DONE",
      "exit": "Owner-approved behavior and UI artwork boards define the supported condition, source time, and appearance at 3:00 PM, during the interval, and at 4:00 PM; installed debug replay shows consistent condition claims, symbol, scene, and semantics throughout, with exact selected time and independent fact support preserved; scoped verification and any remaining unavailable diagnostic route are recorded.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-01f-condition-gap-behavior-board.md",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-14",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-01f-condition-gap-behavior-board.md",
              "sha256": "f90433ded28fe828fbb0beffb2ec1dd5551d6c7e33c9058ea6f4f2226740f814"
            }
          ]
        },
        "ui_artwork": {
          "required": true,
          "path": ".codex/concepts/OX-01f-condition-gap-ui-artwork-board.svg",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-14",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-01f-condition-gap-ui-artwork-board.svg",
              "sha256": "23b6f536336cb3222c958d556a314602433fc06b27de274bcea3466a5c2e2bef"
            }
          ]
        }
      },
      "outcome": "Installed Florence storm replay has supported matching partly sunny and cloudy event at 3 PM and 4 PM, common source-time handoff at the midpoint, and a separate supplied-empty diagnostic replay; scoped captures and interaction evidence recorded.",
      "evidence": [
        ".codex/test-artifacts/136-ox01f-debug-condition-gap/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-14",
    "summary": "OX-01f supplied matching 3 PM and 4 PM typed partly sunny/cloudy events in the SG.11 storm replay, aligned sub-hour selected-condition diagnostics and center, and moved supplied-empty inspection to a separate debug replay.",
    "verification": [
      "python scripts/dev.py workflow: OK at CLOSEOUT",
      "python scripts/dev.py build: BUILD SUCCESSFUL in 1m 3s",
      "python scripts/dev.py run --serial 8AKY0M32X: BUILD SUCCESSFUL and install Success; repeated after debug allowlist change",
      "Installed Pixel 3 XL Florence captures: 3:00, 3:30, 3:30:15, 4:00, separate unavailable diagnostic, and hourly scrub 3:32:05; see cycle 136 artifact README",
      "git diff --check: clean; no automated tests, lint, contract, broad device, RTL, or TalkBack checks run"
    ],
    "evidence_directory": ".codex/test-artifacts/136-ox01f-debug-condition-gap/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "136-ox01f-debug-condition-gap",
  "slice_ids": [
    "OX-01f"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-01f-condition-gap-behavior-board.md",
    ".codex/concepts/OX-01f-condition-gap-ui-artwork-board.svg"
  ],
  "completed_gates": [
    "OX-01f_behavior_board_approved",
    "OX-01f_ui_artwork_board_approved",
    "board_and_plan_reviewed",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Owner chose existing Partly sunny and cloudy label and typed PARTLY_SUNNY_CLOUDY event at 4 PM; refreshed board approvals recorded."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Fixture, separate diagnostic replay, selection alignment, installed four-state captures and focused hourly scrub",
    "Closed slice OX-01f as DONE.",
    "OX-01f closed DONE with installed scoped evidence and separate unavailable diagnostic route",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/136-ox01f-debug-condition-gap/README.md",
    ".codex/history/136-ox01f-debug-condition-gap.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/136-ox01f-debug-condition-gap.md",
    ".codex/test-artifacts/136-ox01f-debug-condition-gap/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Review final diff and exact verification limits, then archive cycle 136."
}
```
<!-- cycle-checkpoint:end -->

## Intake boundary

OX-01f is a correction to the OX-01R installed finding, not OX-02's renderer-reachability trace. The SG.11e debug replay explicitly supplies an empty event and condition at 3:00 PM; `CatalogWindow.typedEventFactAt` accepts only a nonempty event in its exact hour. The center condition uses a representative condition cell and can show the 4:00 PM condition after the 3:30 PM boundary. This creates an installed disagreement, not evidence of a missing catalog row.

The controlling specification disallows a condition claim and carry through a supplied empty condition. The owner rejected a 3:00–4:00 PM condition gap, selected a supported 3 PM condition with separate unavailable inspection, selected the existing `PARTLY_SUNNY_CLOUDY` label, and selected the same typed event at 4 PM. The base synthetic catalog supplies `PARTLY_CLOUDY` at both hours. The approved boards specify the event and SG.1 representative midpoint ownership.

## Approved boundary and ordered work

The product owner approved the behavior and UI artwork boards on 2026-11-14, then explicitly selected the existing event label and a typed 4 PM event. The refreshed board fingerprints in the manifest govern this plan. The correction applies to the SG.11 storm replay, its debug selection route, and the directly affected condition selection and diagnostic overlay. It does not alter provider facts, general support validation, OX-02 renderer reachability, or independent temperature, wind, probability, and amount support.

1. Author `PARTLY_CLOUDY` plus `PARTLY_SUNNY_CLOUDY` at the 3 PM and 4 PM facts of the storm replay. Keep the seven prior storm samples intact. Make replay preparation replace stale stored content by validating the new two facts.
2. Preserve the prior supplied-empty diagnostic in a separate, explicitly labeled debug replay and direct selection route. It must not silently replace the storm replay.
3. Select one supported representative condition source for center text, symbol, scene, semantics, and debug overlay. The 3 PM source owns the exact 3:30 midpoint; the 4 PM source owns instants after it. Preserve exact selected time, original source-valid time disclosure, and independent point/interval fact sourcing. Make the overlay's event assignment use the selected condition source rather than requiring an exact selected-hour sample.
4. Build and install the debug app. Capture the actual Florence replay at 3:00, 3:30, just after 3:30, and 4:00, plus the separate unavailable route. Inspect visible text/symbol/scene, UI semantics, source-time disclosure, and focused scrub behavior. Record exact commands, device, observations, and limits under `.codex/test-artifacts/136-ox01f-debug-condition-gap/`.
5. Inspect the scoped diff and `git diff --check`; close only when the installed evidence meets the slice exit. No tests are added or run without a separate owner request.

The main compatibility risk is changing event precedence for other catalog states. Restrict selection to the supported condition source when it has a typed event, and retain existing behavior otherwise. Check the directly affected debug routes during installed review. The separate unavailable route remains an explicit diagnostic, not a normal forecast claim.
