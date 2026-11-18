# Cycle plan — 150-ox36-weather-input-slots

State: CLOSED
Plan maturity: FINAL
Cycle ID: `150-ox36-weather-input-slots`
Roadmap items: OX-36
Objective: Define typed scene-element weather input slots and unavailable fallback from the shared SceneWeatherDecision without new provider interpretation or visible change

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "150-ox36-weather-input-slots",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-36",
      "depends_on": [
        "OX-34",
        "OX-04",
        "OX-18"
      ],
      "status": "DONE",
      "exit": "One supported and one unavailable existing scene input are resolved without inventing evidence, with the exact interpretation owner named.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-36-weather-input-slots-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-18",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-36-weather-input-slots-behavior-board.dot",
              "sha256": "7d9af777c6dfac6498cc8ddd57df6eb83d6fd96a8137a99c7e3a4b5a4ab3d876"
            },
            {
              "path": ".codex/concepts/OX-36-weather-input-slots-behavior-board.svg",
              "sha256": "668b24bccf139d1123961e9cabf346a2584bfd95a235333dcc65a6a7ecb13c2d"
            },
            {
              "path": ".codex/concepts/OX-36-weather-input-slots-behavior-board.png",
              "sha256": "6e58804a8e143942b210e299855889f0363ca3c1d436f79b0aa4a8896e597ec7"
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
      "outcome": "Typed First City cloud-cover input resolves supplied 0.4 cover and unavailable missing cover from SceneWeatherDecision while retaining typed source evidence, selected instant, and source valid time; no visual change",
      "evidence": [
        ".codex/test-artifacts/150-ox36-weather-input-slots/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-18",
    "summary": "OX-36 completed: First City cloud-cover slot pairs shared decided cover with typed source evidence and selected instant; supplied and missing cases verified without visual change",
    "verification": [
      "Workflow validation passed; focused SharedSceneWeatherDecisionTest passed; git diff --check passed; source-level rendering and selection paths inspected; no installed visual or broad suite claims"
    ],
    "evidence_directory": ".codex/test-artifacts/150-ox36-weather-input-slots/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "150-ox36-weather-input-slots",
  "slice_ids": [
    "OX-36"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-36-weather-input-slots-behavior-board.dot",
    ".codex/concepts/OX-36-weather-input-slots-behavior-board.svg",
    ".codex/concepts/OX-36-weather-input-slots-behavior-board.png"
  ],
  "completed_gates": [
    "Full OX-36 plan review passed: scope, dependencies, board, exit, compatibility, authorized checks, and evidence are bounded",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Product owner approved the displayed OX-36 behavior package on 2026-11-18 without additional constraints",
    "No UI artwork: approved behavior board preserves visible output",
    "Product owner authorized focused slice-specific tests for OX-36 during REVIEW; broader test matrices remain outside this slice",
    "Repository resolution: cloud-cover slot values follow SceneWeatherDecision's SUPPLIED-only cloud rule; retained carried condition provenance never becomes a carried cloud-cover value (SceneWeatherDecision.kt, SemanticScenePlan.kt, SharedSceneWeatherDecisionTest.kt, approved OX-36 board)"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reviewed final OX-36 plan against approved behavior board, scoped authority, and existing scene interfaces",
    "Activated OX-36 for execution.",
    "Closed slice OX-36 as DONE.",
    "OX-36 typed cloud-cover slot implemented and focused test class passed",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/OX-36-weather-input-slots-behavior-board.dot",
    ".codex/test-artifacts/150-ox36-weather-input-slots/README.md",
    ".codex/history/150-ox36-weather-input-slots.md"
  ],
  "next_stage_read_set": [
    ".codex/plans/150-ox36-weather-input-slots.md",
    ".codex/test-artifacts/150-ox36-weather-input-slots/README.md",
    "docs/ROADMAP.md"
  ],
  "next_action": "Inspect final diff, verify workflow, and close cycle 150"
}
```
<!-- cycle-checkpoint:end -->

## Objective and approved board

Define the smallest typed weather-input slot boundary needed by a scene element. The product owner approved the displayed OX-36 behavior board DOT, SVG, and PNG package on 2026-11-18 without additional constraints; the manifest records all fingerprints. The board governs a supported and unavailable cloud-cover example, preservation of supplied/carried/unavailable meaning, and `SceneWeatherDecision` as the interpretation owner. UI artwork is inapplicable because the approved scope preserves visible output.

## Scope and dependencies

- OX-34, OX-04, and OX-18 are DONE. Reuse OX-34's First City-local semantic declaration boundary, OX-04's truthful evidence semantics, and OX-18's shared decision seam for two worlds.
- Introduce a small typed scene-element input representation, associated only with the existing cloud-cover object/path needed to prove the boundary. It must identify the selected instant and carry a resolved value or unavailable state with the source evidence and valid time needed to distinguish supplied from carried data. Use existing `SceneReading`/`ScenePoint` evidence and `SceneWeatherDecision` outputs; do not translate provider fields inside an element.
- Resolve the supported example from a supplied cloud-cover point whose shared decision has visible cover. Resolve an unavailable example from an existing missing, unsupported, or probability-only cloud-cover point without promoting it to visible cover or numeric zero. The cloud-cover slot's resolved value follows the shared decision's `SUPPLIED`-only cloud-cover rule: a carried or missing cloud-cover point has no resolved cloud-cover value. Retain its evidence and source valid time when present. Carried *condition* provenance and its original source time remain on the shared reading/decision; an element must not relabel that condition as supplied or turn it into cloud cover.
- Name `SceneWeatherDecision.resolve(reading, zone)` as the owner of weather interpretation. The slot projection may carry or pair already-decided values with their retained evidence, but it cannot independently infer weather from raw conditions, chances, or amounts. Keep the current `SceneWeatherDecision` and scene composition behavior intact.

## Invariants, exclusions, and compatibility

The selected instant, canonical forecast projection, visual fallback, existing render order, and both current world compositions remain unchanged. An unavailable input remains unavailable even if a probability or unrelated condition exists. Do not introduce pack manifests, provider DTOs, a global element registry, renderer adapters, new scene assets, visual changes, or a second-world migration. OX-38 owns the later definition-to-renderer adapter. Preserve the spec's rule that carried older evidence keeps its original source time and missing numeric facts are not zero.

## Execution sequence

1. Inspect the current `SceneWeatherDecision`, `SceneReading` evidence types, and First City `CLOUD_COVER` declaration/path. Select the minimum typed slot shape that retains supplied, carried, and unavailable evidence meaning while keeping cloud-cover value interpretation in the shared decision seam; carried condition evidence does not require a carried cloud-cover value.
2. Add the cloud-cover slot projection and association with the existing local declaration or a directly related scene-element boundary. Keep current composers and renderers on their existing behavior path.
3. Work through one supplied visible cloud-cover case and one unavailable cover case using actual existing field semantics. Inspect the resulting values, evidence, selected instant, and source valid time, and confirm no weather value is inferred from chance or unrelated labels. Inspect the existing carried-condition case to confirm its original source time remains available and it does not create a cloud-cover value.
4. Add or update focused OX-36 tests for the supported and unavailable cloud-cover slots, selected instant, retained evidence/source time where applicable, and the carried-condition case's absence of inferred cloud-cover value. Run only those slice-specific tests and record the exact command and results. Inspect affected source and final diff for unchanged rendering, ordering, and selected-time behavior. Record exact verification and limits under `.codex/test-artifacts/150-ox36-weather-input-slots/README.md`; run `python scripts/dev.py workflow` and `git diff --check` before closeout.

## Exit, verification authorization, and evidence

Exit is the roadmap's supported and unavailable existing input resolution with no invented evidence and an explicitly named interpretation owner. Authorized verification in this plan is workflow validation, focused source-level contract inspection of the two cases, focused OX-36 slice-specific tests, and final diff inspection. The product owner authorized adding and running focused slice-specific tests during REVIEW; record their exact command and result, without claiming broader suite coverage. The no-visual-change scope does not claim device evidence. Preserve the actual focused verification record in `.codex/test-artifacts/150-ox36-weather-input-slots/README.md` and completed outcome in `.codex/history/150-ox36-weather-input-slots.md` when execution is later authorized.

## Risks and open questions

The shared cloud decision currently exposes a nullable cover fraction, while source evidence remains on its `reading`; the implementation must avoid losing provenance when projecting the slot. Its `SUPPLIED`-only cloud rule makes carried cloud-cover values unavailable even when source evidence is retained; the approved board's carried example concerns conditions. No material owner decision remains after board approval. Any proposed visible fallback or broader weather-field migration would change approved scope and reopen the board gate.
