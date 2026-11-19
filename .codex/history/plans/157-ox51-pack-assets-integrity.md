# Cycle plan — 157-ox51-pack-assets-integrity

State: CLOSED
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
      "status": "DONE",
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
      "outcome": "Defined the standalone asset-reference contract and labeled accept/reject examples; the exact OX-24 olive PNG is contained at its canonical sample-pack path with the declared digest. Focused checks cover every approved path rejection class, symlink entries, content type, missing resources, and digest dispositions.",
      "evidence": [
        ".codex/test-artifacts/157-ox51-pack-assets-integrity/README.md",
        ".codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack/assets/first-city/canopy/olive.png"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "OX-51 completed: documented a standalone scene-pack asset-reference contract and accepted/rejected examples, copied and verified the OX-24 olive PNG inside a sample pack, and completed the authorized focused contract checks. Manifest v1 remains closed; OX-53 owns versioned manifest integration, OX-52 trust/capability/budget policy, and OX-91 runtime resource validation and limits.",
    "verification": [
      "python -m unittest discover -s scripts/tests -p 'test_scene_pack_asset_reference_contract.py' — PASS; 5 focused tests.",
      "python -m json.tool docs/scene-pack-asset-reference-examples.json — PASS.",
      "python scripts/dev.py workflow — PASS at ACTIVE / CLOSEOUT; close validation passed at IDLE.",
      "git diff --check — PASS.",
      "Source and sample PNG are 1024 × 1536 RGBA; both SHA-256 digests equal 60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d.",
      "Limit: focused checks are test-only and do not exercise a production resolver or OX-91 runtime validation. No app build, installed screenshot, device matrix, or numeric resource budgets were in scope; OX-52 and OX-91 retain their stated handoffs."
    ],
    "evidence_directory": ".codex/test-artifacts/157-ox51-pack-assets-integrity/"
  }
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
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.dot",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.svg",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.png",
    ".codex/concepts/OX-51-pack-assets-integrity-behavior-board.md"
  ],
  "completed_gates": [
    "Current pointer, ordered roster, referenced plan, and REVIEW checkpoint agree; workflow scaffold passes.",
    "OX-20 and OX-24 dependency records and the source PNG format and SHA-256 were verified.",
    "Product owner approved the OX-51 behavior package as shown on 2026-11-19, including symlink-entry rejection; all four recorded fingerprints match.",
    "UI artwork is not required because this slice changes no visible app output.",
    "Scope, observable exit, future focused verification path, evidence target, and test authorization were reviewed; the focused test module is not yet present, so execution coverage remains unverified. No tests were run.",
    "all_slice_boards_approved",
    "final_plan_reviewed",
    "Observable exit met: absolute and escaping paths, unsupported types, missing files and digests, digest mismatches, every canonical path rule, root escape, and symlink entries have explicit rejection dispositions and focused checks."
  ],
  "decisions": [
    "Owner selected option 1: the recorded approval applies to the exact fingerprinted board package and includes symlink-entry rejection despite stale proposal/open-review wording in the companion artifacts."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Owner confirmed the existing fingerprint-bound board approval is authoritative as shown; board files remain unchanged.",
    "Activated OX-51 for execution.",
    "Closed slice OX-51 as DONE.",
    "OX-51 exit met: normative contract and labeled examples are complete; the real OX-24 PNG resolves inside the sample pack root and its exact-byte SHA-256 matches the reference and source; focused authorized checks passed.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/157-ox51-pack-assets-integrity/README.md",
    ".codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack/assets/first-city/canopy/olive.png",
    ".codex/history/157-ox51-pack-assets-integrity.md"
  ],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/plans/157-ox51-pack-assets-integrity.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "docs/SCENE_PACK_ASSET_REFERENCES.md",
    "docs/scene-pack-asset-reference-examples.json",
    "scripts/tests/test_scene_pack_asset_reference_contract.py",
    ".codex/test-artifacts/157-ox51-pack-assets-integrity/README.md",
    ".codex/test-artifacts/157-ox51-pack-assets-integrity/example-pack/assets/first-city/canopy/olive.png"
  ],
  "next_action": "Inspect git diff --check and the complete final diff; run the final workflow and authorized verification commands; close cycle 157 with the exact result, evidence paths, limits, and OX-53/OX-52/OX-91 handoffs."
}
```
<!-- cycle-checkpoint:end -->

## Approved board and dependencies

The product owner approved the current package as shown on 2026-11-19, including the `AssetReference` fields, the boundary that keeps OX-20 manifest v1 closed, canonical pack-root-confined paths with symlink rejection, per-file SHA-256 of exact bytes, the PNG-only real example, and the division of limit ownership between OX-52 and OX-91. The cycle manifest records the SHA-256 fingerprint of the DOT, SVG, PNG, and Markdown files. The package retains its proposal wording; the owner's approval is recorded here against those exact fingerprints. UI artwork is not applicable because this slice changes no visible app output.

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

The normative contract and examples describe the approved reference fields and rejection rules. The evidence sample root contains the actual OX-24 olive PNG at the referenced relative path, wholly inside that root, and its calculated SHA-256 matches the declared value. The examples explicitly reject absolute and escaping paths, unsupported types, missing files, absent digests, and mismatched digests; focused checks also cover symlink entries. The contract names OX-91 as the path/resource-size enforcement layer and OX-52 as the budget-policy owner, with numeric thresholds deferred.

## Verification authorization and evidence

The owner authorized focused slice-specific tests. Add `scripts/tests/test_scene_pack_asset_reference_contract.py` as a test-only contract check, without introducing a production resolver or loader. It must verify the valid JSON example against the evidence pack root, root containment, and supported PNG type. Compute SHA-256 from the evidence sample file's bytes as read from disk; require it to match both the example reference's digest and the verified OX-24 source digest `60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d`. It must also check the approved absolute/escape/symlink, missing-file, unsupported-type, missing-digest, and mismatched-digest reject cases. Run only this focused test module with `python -m unittest discover -s scripts/tests -p 'test_scene_pack_asset_reference_contract.py'`.

Also run `python -m json.tool docs/scene-pack-asset-reference-examples.json`, `python scripts/dev.py workflow`, and `git diff --check`. Record exact commands and results, the sample file path and digest, and any limitations in `.codex/test-artifacts/157-ox51-pack-assets-integrity/README.md`. The roadmap closeout target is `.codex/history/157-ox51-pack-assets-integrity.md`. No app build, installed screenshot, device acceptance, or broad system matrix applies to this documentation and contract slice.

For path rejection coverage, include at least one focused input for each rule: absolute, drive-form, URI-form, backslash, empty segment, dot segment, and parent segment. Test a parent segment that would normalize within the pack root separately from a path that escapes the root, so canonical syntax rejection and root containment are both exercised. Retain the symlink-entry rejection check. The plan's invalid JSON examples need not duplicate every malformed path form; they remain illustrative examples, while the focused test covers the full path contract.

## Risks and remaining handoffs

The test-only contract check must not be presented as OX-91 runtime validation. OX-53 must apply the required manifest-version change when it integrates asset references; OX-20 v1 remains closed. Numeric limits remain deferred until the owning runtime layers have evidence to set them. No owner decision remains open after approval of the board package.
