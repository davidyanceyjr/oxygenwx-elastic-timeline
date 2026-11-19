# Cycle plan — 156-ox20-pack-identity-manifest

State: CLOSED
Plan maturity: FINAL
Cycle ID: `156-ox20-pack-identity-manifest`
Roadmap items: OX-20
Objective: Define a bounded non-executable scene-pack identity and versioned manifest contract for existing First City and Open Landscape worlds

## Cycle roster and board approvals

The structured roster records the approved board package and the slice exit.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "156-ox20-pack-identity-manifest",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-20",
      "depends_on": [
        "OX-19",
        "OX-92"
      ],
      "status": "DONE",
      "exit": "One bounded versioned manifest schema has explicit valid/invalid examples and can name an existing authored world without meteorological interpretation or a Park dependency.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.dot",
              "sha256": "a7fdf67539dafb5df04b411e7cbb0653762dbf03aa1683f8ef1431968fb39dca"
            },
            {
              "path": ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.svg",
              "sha256": "b0306a94c5e3794054a0abe8b02a445a8ef2e45c1193c0f53954172773ae3bf3"
            },
            {
              "path": ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.png",
              "sha256": "2f289932b5ddd3678f53c5d7659fe299dd8b7789c1ac8bcd44f442d9e8e73327"
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
      "outcome": "Defined a closed data-only v1 identity envelope, exact mappings to both existing authored worlds, version and engine-contract semantics, and reviewed valid/invalid examples without runtime or meteorological interpretation",
      "evidence": [
        ".codex/test-artifacts/156-ox20-pack-identity-manifest/contract-review.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "OX-20 completed: a closed, data-only manifest v1 identity contract and seven reviewed valid/invalid examples name the two existing authored worlds without weather interpretation or Park dependency. Runtime loading, renderer capability policy, assets, trust, and installation remain assigned to later slices.",
    "verification": [
      "python -m json.tool docs/scene-pack-manifest-v1-examples.json — PASS",
      "python scripts/dev.py workflow — PASS at intake, execution, and CLOSEOUT",
      "git diff --check — PASS at execution and CLOSEOUT",
      "Final document, example, evidence, roadmap, and pointer contents inspected; no tests, build, device, or installed visual checks run",
      "python scripts/dev.py contract — NOT RUN; command does not inspect changed OX-20 document paths"
    ],
    "evidence_directory": ".codex/test-artifacts/156-ox20-pack-identity-manifest/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

This record carries the next-stage handoff and accepted gates.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "156-ox20-pack-identity-manifest",
  "slice_ids": [
    "OX-20"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.dot",
    ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.svg",
    ".codex/concepts/OX-20-pack-identity-manifest-behavior-board.png"
  ],
  "completed_gates": [
    "FINAL plan reviewed against approved board, OX-20 exit, direct dependencies, and verification authorization",
    "all_slice_boards_approved",
    "final_plan_reviewed"
  ],
  "decisions": [
    "Manifest v1 uses first-city and minimal-open-landscape as contract references, separately mapped to current authored definitions; engineCompatibility 1 denotes this identity contract version"
  ],
  "open_questions": [],
  "completed_outputs": [
    "docs/SCENE_PACK_MANIFEST_V1.md",
    "docs/scene-pack-manifest-v1-examples.json",
    "Closed slice OX-20 as DONE.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/156-ox20-pack-identity-manifest/contract-review.md",
    ".codex/test-artifacts/156-ox20-pack-identity-manifest/README.md",
    ".codex/history/156-ox20-pack-identity-manifest.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/156-ox20-pack-identity-manifest.md",
    "docs/SCENE_PACK_MANIFEST_V1.md",
    "docs/scene-pack-manifest-v1-examples.json",
    ".codex/test-artifacts/156-ox20-pack-identity-manifest/contract-review.md"
  ],
  "next_action": "Inspect final scope and close cycle 156 with exact verification and limitations"
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## Approved board and dependencies

The product owner approved `.codex/concepts/OX-20-pack-identity-manifest-behavior-board.dot` and its SVG/PNG renders on 2026-11-19. The roster records each file's fingerprint. The board governs a data-only contract with four fields: stable opaque `packId`, `manifestVersion`, `worldDefinitionRef`, and `engineCompatibility`. It proposes supported/unsupported outcomes and examples for existing First City and Minimal Open Landscape definitions. No UI artwork applies because this slice does not alter visible app output.

OX-19 and OX-92 are DONE. They establish independent in-session world choice and a small shared declaration shape. They do not establish pack loading or public authoring. The implementation reference points are `CatalogWorld` in `CatalogTimeLabScreen.kt`, `SceneElementDeclaration.kt`, `FirstCityComposition.kt`, and `MinimalOpenLandscapeComposition.kt`.

## Bounded work

Create `docs/SCENE_PACK_MANIFEST_V1.md` as the normative contract and `docs/scene-pack-manifest-v1-examples.json` as a small set of labeled, illustrative records. The contract defines a closed, data-only v1 envelope with the four board-approved required fields. Specify each field's type and validity, stable pack identity semantics, manifest version `1`, an exact reference mapping to the two existing authored worlds, and engine-contract compatibility semantics for the current schema. Distinguish the pack's stable logical identity from an archive hash, transport filename, trusted origin, and entitlement. Define unsupported manifest/engine versions, missing fields, unknown world references, and extra executable or weather-interpretation fields as invalid *contract examples*. These are design-time dispositions; OX-26 and OX-52 own runtime decoding and trust enforcement.

Use the existing `CatalogWorld` identifiers only as evidence for current world names; do not turn that UI enum into a public pack API. Supply valid minimal records for First City and Minimal Open Landscape and invalid records for missing identity, unknown world reference, unsupported manifest version, and unsupported engine contract. Keep the records minimal; OX-53 owns complete asset-bearing manifests. The contract must state how future schema/capability extensions can be versioned without treating an unknown required field as silently supported. Do not define asset paths, digests, capability IDs, or import budgets here.

This slice is documentation/schema work; no Kotlin or runtime migration is needed to meet its exit. Preserve place, catalog revision, exact selected instant, forecast facts, canonical projection, and timeline ownership outside pack identity. The bundled free experience remains available.

Exclude Park, public editor, downloadable or executable code, assets/paths/digests, renderer capability policy, pack builder, decoder, resource validation, installation, hot swap, Settings, entitlement, and UI changes. OX-51, OX-52, OX-53, OX-30, OX-26, OX-91, OX-55, and later slices own those outcomes.

## Acceptance and verification

The exit is one bounded manifest schema that names an existing authored world, has explicit version and engine compatibility semantics, and presents valid and invalid examples without meteorological interpretation or a Park dependency. Review each example against the normative field rules and the existing world identifiers; record a field-by-field valid/invalid disposition in the evidence. Inspect the final diff for scope and documentation consistency. Run `python scripts/dev.py workflow` and `git diff --check`; run `python scripts/dev.py contract` only if that command covers the changed contract paths. Record exact commands and results. No tests are authorized by this request. Do not add or run tests without separate owner authorization.

Record actual contract-review evidence under `.codex/test-artifacts/156-ox20-pack-identity-manifest/` and closeout under `.codex/history/156-ox20-pack-identity-manifest.md`. No installed screenshot or device acceptance is claimed for this no-UI slice. The main risk is defining a manifest field that implies later resource or trust policy; keep those details explicitly deferred and preserve a clear handoff to their owning slices.
