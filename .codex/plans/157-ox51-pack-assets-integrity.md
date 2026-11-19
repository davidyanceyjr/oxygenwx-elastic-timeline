# Cycle plan — 157-ox51-pack-assets-integrity

State: PLANNED
Plan maturity: FINAL
Cycle ID: `157-ox51-pack-assets-integrity`
Roadmap items: OX-51
Objective: Define bounded asset references, supported resource types, and content-integrity dispositions for one existing scene variant

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "157-ox51-pack-assets-integrity",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-51",
      "depends_on": [
        "OX-20",
        "OX-24"
      ],
      "status": "PLANNED",
      "exit": "A real example resolves every asset inside its pack root; escaped or absolute paths, unsupported asset types, and missing or digest-mismatched resources have explicit reject dispositions.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot",
          "approved": true,
          "approved_by": "Product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot",
              "sha256": "cc3e54894493d142d0eb3afad82a6b21a7b3e7075b3b74d8a6e15da36b4aced6"
            },
            {
              "path": ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.svg",
              "sha256": "0d62dc5c5dd58bb2584ec1e8cb9fee6a369c05574a57c20fbbfc4af6c39f5756"
            },
            {
              "path": ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.png",
              "sha256": "e0abbff1d47a0124e41354a260dca8bd3b1d754a569023593e15aa4281e146f5"
            },
            {
              "path": ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.md",
              "sha256": "84f06514cd091a854e6010c9a2ee237500f0fd738d8822b569011abded1bd2ec"
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
      "outcome": null,
      "evidence": []
    }
  ]
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "157-ox51-pack-assets-integrity",
  "slice_ids": [
    "OX-51"
  ],
  "stage": "REVIEW",
  "prior_cycle_state": "PLANNED",
  "accepted_boards": [
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.svg",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.png",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.md"
  ],
  "completed_gates": [
    "Approved behavior board package recorded; UI artwork is not required.",
    "Approved OX-51 behavior package fingerprints validate and UI artwork is not applicable.",
    "Final plan is complete and has no unresolved owner decisions."
  ],
  "decisions": [
    "No owner decisions remain open; OX-51 uses the approved standalone asset-reference contract boundary and defers complete manifest integration to OX-53.",
    "Focused slice-specific tests remain authorized for execution; this planning cycle does not run them."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Substantive OX-51 plan is complete with scope, exclusions, functional invariants, the observed exit, focused authorized test path, evidence target, and later-slice handoffs.",
    "Roadmap now records the product-owner-approved OX-51 behavior package and its constraints; the cycle manifest fingerprints all four board files.",
    "Full plan review passed: OX-51 scope, dependencies, board constraints, invariants, compatibility, focused authorized checks, evidence target, and observable exit are consistent.",
    "Cycle remains PLANNED; no slice is ACTIVE and no implementation or tests have run."
  ],
  "evidence": [],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/157-ox51-pack-assets-integrity.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.svg",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.png",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.md",
    "docs/SCENE_PACK_MANIFEST_V1.md",
    "docs/scene-pack-manifest-v1-examples.json",
    ".codex/history/plans/156-ox20-pack-identity-manifest.md",
    ".codex/history/plans/152-ox24-scene-asset-swap.md",
    ".codex/test-artifacts/152-ox24-scene-asset-swap/README.md"
  ],
  "next_action": "Cycle 157 is ready for owner-directed activation at a later time; retain OX-51 as NEXT until activation is explicitly requested."
}
```
<!-- cycle-checkpoint:end -->

## Scope and acceptance

## Approved board and dependencies

The product owner approved `.codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot` and its SVG, PNG, and Markdown companion on 2026-11-19. The cycle manifest records the SHA-256 fingerprint of each file. The board governs the `AssetReference` fields, the boundary that keeps OX-20 manifest v1 closed, canonical pack-root-confined paths with symlink rejection, per-file SHA-256 of exact bytes, the PNG-only real example, and the proposed division of limit ownership between OX-52 and OX-91. UI artwork is not applicable because this slice changes no visible app output.

OX-20 is DONE and defines a closed, data-only identity envelope with exactly four fields. Its contract requires a new manifest version when fields or required-field rules change. OX-24 is DONE and supplies a concrete alternate First City CANOPY asset at `app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png`; it is a 1024 × 1536 RGBA PNG with current SHA-256 `60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d`. The sample will use a copy of those exact bytes. The contract keeps the asset-reference record separate for OX-53 to integrate under the required manifest-version change.

## Bounded work

1. Add `docs/SCENE_PACK_ASSET_REFERENCES.md` as the normative contract for the approved `AssetReference` fields: a stable, non-empty `refId`; a canonical relative `path`; the exact `mediaType`; and a required lowercase 64-character hexadecimal `sha256` of the referenced file's exact bytes. The OX-51 example supports `image/png`; no font type is needed by the selected real variant.
2. Define canonical slash-separated path rules. Reject absolute, drive, URI, or backslash forms; empty, dot, and parent segments; symlink entries; missing files; and any resolved path outside the pack root. Do not choose numeric path-length or resource-size thresholds.
3. State explicit reject dispositions for unsupported types, a missing digest, and a digest mismatch. A correct content digest establishes byte integrity only; it does not establish trusted origin or entitlement.
4. Add `docs/scene-pack-asset-reference-examples.json` with one valid record for the OX-24 olive PNG and labeled invalid examples for an absolute path, an escaping path, an unsupported type, a missing file, a missing digest, and a mismatched digest.
5. Keep the asset reference as a standalone contract record. Do not expand or revise OX-20's closed manifest v1; OX-53 integrates this record into a new asset-bearing manifest version.
6. Preserve a sample pack root under `.codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack/` containing the exact PNG bytes at `assets/first-city/canopy/olive.png`. Record the source, byte digest, resolved path, and contract disposition in the cycle evidence README.

## Functional invariants and boundaries

- Pack identity and manifest v1 semantics remain as defined by OX-20. Per-file content integrity stays distinct from pack identity, archive identity, trusted origin, and entitlement.
- Asset metadata does not reinterpret weather, alter place or catalog selection, or change the exact selected instant, forecast facts, canonical projection, or timeline ownership.
- Asset references are declarative data. This slice does not implement pack loading, building, decoding, runtime resource validation, rendering, activation, or installation.
- OX-52 owns the capability/trust policy and names budget categories and enforcement boundaries. OX-91 resource validation enforces path and per-resource size limits before activation. OX-51 sets no arbitrary thresholds.
- OX-53 owns the complete asset-bearing manifests and the manifest-version integration. OX-30 owns pack building; OX-26 owns manifest decoding; OX-91 owns runtime resource validation; OX-55 owns renderer instantiation; OX-57 owns installation.

## Exclusions

No changes to app or renderer code, OX-20 manifest v1, OX-24 artwork, pack builder, decoder, runtime validator, capability/trust policy, installer, hot swap, Settings, downloader, entitlement, Park, fonts not required by the selected variant, UI, or weather interpretation. Do not introduce resource-size, path-length, or aggregate-budget numbers without runtime evidence.

## Observable exit

The normative contract and examples describe the approved reference fields and rejection rules. The evidence sample root contains the actual OX-24 olive PNG at the referenced relative path, wholly inside that root, and its calculated SHA-256 matches the declared value. The examples explicitly reject absolute and escaping paths, unsupported types, missing files, absent digests, and mismatched digests. The contract names OX-91 as the path/resource-size enforcement layer and OX-52 as the budget-policy owner, with numeric thresholds deferred.

## Verification authorization and evidence

The owner authorized focused slice-specific tests. Add `scripts/tests/test_scene_pack_asset_reference_contract.py` as a test-only contract check, without introducing a production resolver or loader. It must verify the valid JSON example against the evidence pack root, root containment, supported PNG type, and exact-byte SHA-256; it must also check the approved absolute/escape/symlink, missing-file, unsupported-type, missing-digest, and mismatched-digest reject cases. Run only this focused test module with `python -m unittest discover -s scripts/tests -p 'test_scene_pack_asset_reference_contract.py'`.

Also run `python -m json.tool docs/scene-pack-asset-reference-examples.json`, `python scripts/dev.py workflow`, and `git diff --check`. Record exact commands and results, the sample file path and digest, and any limitations in `.codex/test-artifacts/157-ox51-pack-assets-integrity/README.md`. The roadmap closeout target is `.codex/history/157-ox51-pack-assets-integrity.md`. No app build, installed screenshot, device acceptance, or broad system matrix applies to this documentation and contract slice.

## Risks and remaining handoffs

The test-only contract check must not be presented as OX-91 runtime validation. OX-53 must apply the required manifest-version change when it integrates asset references; OX-20 v1 remains closed. Numeric limits remain deferred until the owning runtime layers have evidence to set them. No owner decision remains open after approval of the board package.
