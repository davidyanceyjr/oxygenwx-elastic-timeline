# Cycle plan — 159-ox53-example-pack-manifests

State: CLOSED
Plan maturity: FINAL
Cycle ID: `159-ox53-example-pack-manifests`
Roadmap items: OX-53
Objective: Finalize two evidence-based, asset-bearing scene-pack manifest examples for existing authored worlds, preserving the OX-20 identity envelope and OX-51/OX-52 boundaries without implementing runtime pack behavior.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "159-ox53-example-pack-manifests",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-53",
      "depends_on": [
        "OX-52",
        "OX-92",
        "OX-24"
      ],
      "status": "DONE",
      "exit": "Both examples identify manifest, version, definitions, compatible render capabilities and relative assets sufficient for a first real pack-builder proof; unresolved optional features remain deferred.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-53-example-pack-manifests-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-53-example-pack-manifests-behavior-board.dot",
              "sha256": "00a7c2daa3fdb4eeb42b46b6517c71296e3c71757343168e25672633dc1d2b77"
            },
            {
              "path": ".codex/concepts/OX-53-example-pack-manifests-behavior-board.svg",
              "sha256": "bbc840002019b02e8b5cc0baef38c243feefe60ebd66c4efa5adcf50d27e8871"
            },
            {
              "path": ".codex/concepts/OX-53-example-pack-manifests-behavior-board.png",
              "sha256": "7538aa20028efc01fb1f7aec384f0ae633bb9dfae297bd4931c1c073865dd3e6"
            },
            {
              "path": ".codex/concepts/OX-53-example-pack-manifests-behavior-board.md",
              "sha256": "5759e283da623e0f30bb57a5e9bcb7570fd538da1b4ea03fd80b287e430b9bf6"
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
      "outcome": "Delivered the closed manifest v2 contract and two self-contained First City sample packs; preserved the v1 envelope, registered the evidence-backed required capability, and passed focused checks for exact shapes, duplicate member names, relative asset paths, and exact source bytes.",
      "evidence": [
        ".codex/test-artifacts/159-ox53-example-pack-manifests/README.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "Completed OX-53 example pack manifests: added the v2 contract, registered the evidence-backed First City canopy capability, added baseline and olive example roots with exact-byte assets, and added focused manifest contract checks. Runtime pack loading is not claimed.",
    "verification": [
      "python -m unittest discover -s scripts/tests -p 'test_scene_pack_manifest_v2_examples_contract.py' — PASS (6 focused checks)",
      "python -m json.tool docs/scene-pack-examples/first-city-canopy-baseline/manifest.json > /dev/null — PASS",
      "python -m json.tool docs/scene-pack-examples/first-city-canopy-olive/manifest.json > /dev/null — PASS",
      "python scripts/dev.py workflow — PASS at ACTIVE/CLOSEOUT before close",
      "git diff --check — PASS"
    ],
    "evidence_directory": ".codex/test-artifacts/159-ox53-example-pack-manifests/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "159-ox53-example-pack-manifests",
  "slice_ids": [
    "OX-53"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-53-example-pack-manifests-behavior-board.dot",
    ".codex/concepts/OX-53-example-pack-manifests-behavior-board.svg",
    ".codex/concepts/OX-53-example-pack-manifests-behavior-board.png",
    ".codex/concepts/OX-53-example-pack-manifests-behavior-board.md"
  ],
  "completed_gates": [
    "All four revised behavior-board package fingerprints match the current files after owner reapproval.",
    "The updated board-to-plan relationship was reviewed and remains scope-aligned.",
    "OX-53 exit and evidence recorded before CLOSEOUT."
  ],
  "decisions": [
    "Product owner re-approved the revised four-file behavior-board package on 2026-11-19; approve-board refreshed all four fingerprints.",
    "Re-review confirmed the status-language correction did not change the approved scope or constraints; the plan relationship now identifies the revised package and current approval.",
    "No app build or installed device acceptance applies because this is a documentation, example-data, and test-only slice."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reviewed the revised board against the current plan, updated the approval relationship, and resolved the pending owner question.",
    "Closed slice OX-53 as DONE.",
    "Recorded OX-53 as DONE with its exit conditions and evidence path.",
    "Prepared cycle closeout from the reviewed implementation, focused verification, and recorded limitations.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/159-ox53-example-pack-manifests/README.md",
    ".codex/history/159-ox53-example-pack-manifests.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/159-ox53-example-pack-manifests.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    "docs/SCENE_PACK_MANIFEST_V1.md",
    "docs/SCENE_PACK_MANIFEST_V2.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    "docs/scene-pack-examples/first-city-canopy-baseline/manifest.json",
    "docs/scene-pack-examples/first-city-canopy-baseline/assets/first-city/canopy/baseline.png",
    "docs/scene-pack-examples/first-city-canopy-olive/manifest.json",
    "docs/scene-pack-examples/first-city-canopy-olive/assets/first-city/canopy/olive.png",
    "scripts/tests/test_scene_pack_manifest_v2_examples_contract.py",
    ".codex/test-artifacts/159-ox53-example-pack-manifests/README.md"
  ],
  "next_action": "Review the complete final diff and evidence, run the authorized closeout checks, then archive cycle 159."
}
```
<!-- cycle-checkpoint:end -->

## Slice intake — SLICE

OX-53 is the sole selected NEXT slice for this cycle. Its declared dependencies
OX-52, OX-92, and OX-24 are DONE. The roster contains only OX-53; this planning
request authorizes focused slice-specific tests and excludes system/device
tests. The required board is a behavior/contract board. UI artwork is not
applicable because the slice defines documentation examples and contract data
without changing visible app output.

Substantive scope and acceptance details were drafted after owner approval of
the objective-specific board. They preserve the closed OX-20 manifest v1,
reuse OX-51 path/media/digest rules, distinguish OX-52 renderer-policy version
from identity compatibility, and trace the public capability declaration to
the OX-24 evidenced renderer path. No runtime pack builder, decoder, resource
validator, installer, or app behavior is in this slice.

## BOARDS — approved package

The revised objective-specific behavior/contract package was approved by the
product owner on 2026-11-19 after the status-language correction. The
correction removed obsolete proposal and approval-request wording without
changing the approved scope or constraints. Its files are
`.codex/concepts/OX-53-example-pack-manifests-behavior-board.dot`, with SVG and
PNG renders and the companion contract at the same stem. The tracker records
the refreshed package fingerprints and owner approval. The approved
constraints are two First City examples (baseline and olive), a closed
manifest v2 envelope retaining the OX-20 identity fields and adding
`rendererPolicyVersion`, `requiredCapabilities`, and `assetReferences`, plus
required public capability ID `first-city.canopy.image-png` tied to the
evidenced path. No additional constraints were stated. UI artwork does not
apply.

## Objective and approved board

Finalize two minimal, evidence-based, asset-bearing manifest v2 examples for
the existing First City world. The product owner approved the revised four-file
behavior/contract package on 2026-11-19. The approved board selects the existing
First City baseline and olive canopy variants, preserves OX-20's four identity
fields, adds the separately versioned renderer-policy and asset-reference
fields, and defines required public capability ID
`first-city.canopy.image-png`. The board is recorded with current content
fingerprints in the cycle roster. Its constraints govern the examples and
acceptance below; approval does not authorize runtime implementation.

No UI artwork applies because the slice changes design-time contract documents
and static example packages only. It does not change visible app output.

## Scope and delivery

OX-53 is the only cycle slice. Deliver:

1. `docs/SCENE_PACK_MANIFEST_V2.md`, a closed JSON object with exactly seven
   members: the four OX-20 identity fields (`packId` string, `manifestVersion`
   integer `2`, `worldDefinitionRef` string `first-city`, and
   `engineCompatibility` integer `1`); `rendererPolicyVersion` integer `1`;
   `requiredCapabilities`, an array containing exactly one public capability
   string; and `assetReferences`, an array containing exactly one OX-51
   reference object. That object has exactly the string fields `refId`, `path`,
   `mediaType`, and `sha256`. Object member names must be unique; repeated
   member names are invalid, carrying OX-20's v1 rejection rule into v2.
   `engineCompatibility: 1` remains the OX-20 identity-contract marker;
   `rendererPolicyVersion: 1` is a separate initial renderer capability
   registry.
2. `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` updated with the initial required
   capability `first-city.canopy.image-png` and its exact design-time support
   boundary. The policy v1 registry has this required capability and no
   optional entries. Its evidence is the OX-24 baseline/olive selection through
   the existing First City canopy declaration and draw path. This contract
   entry does not claim runtime loading of arbitrary pack files.
3. `docs/SCENE_PACK_MANIFEST_V1.md` linked to the v2 contract while keeping the
   v1 envelope and existing examples unchanged.
4. Two self-contained, builder-ready example roots, each with `manifest.json`
   and one real PNG asset at its canonical relative path:
   - `docs/scene-pack-examples/first-city-canopy-baseline/`, using
     `assets/first-city/canopy/baseline.png` copied byte-for-byte from
     `app/src/main/res/drawable-nodpi/sg2_city_canopy.png`.
   - `docs/scene-pack-examples/first-city-canopy-olive/`, using
     `assets/first-city/canopy/olive.png` copied byte-for-byte from
     `app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png`.
5. `scripts/tests/test_scene_pack_manifest_v2_examples_contract.py`, a focused
   test-only check of the exact seven-member envelope and reference-object
   shapes, JSON types, two approved manifest values, relative-path/media/digest
   rules, capability registration, exact asset bytes, and rejection of
   duplicate JSON object member names. Exercise duplicate-name rejection from
   raw JSON input so parsing cannot erase the repeated member before the check.

Each manifest uses a distinct valid `packId`,
`worldDefinitionRef: first-city`, `manifestVersion: 2`,
`engineCompatibility: 1`, `rendererPolicyVersion: 1`, a one-item
`requiredCapabilities` string array, and a one-item `assetReferences` array
whose OX-51 object contains `refId`, canonical relative `path`,
`mediaType: image/png`, and exact-byte lowercase SHA-256 as strings.
The two copies differ only in pack identity and selected asset reference/path
and digest. Their full relative paths resolve inside their own committed sample
pack roots so a later OX-30 builder can use them as source examples.

## Dependencies and invariants

- **OX-52 — DONE:** Use separate manifest and renderer-policy versions; keep
  required capabilities fail-closed, the payload declarative, and capability
  IDs tied to evidenced renderer paths. OX-52 leaves the exact field spelling
  and initial public capability to OX-53.
- **OX-92 — DONE:** The Open Landscape tree-group declaration is evidence for
  a renderer path but has no scoped image asset. The approved board therefore
  uses the OX-53 asset-variant option rather than implying an unsupported
  Open Landscape asset.
- **OX-24 — DONE:** The two actual First City PNG assets are alternate choices
  on the same CANOPY draw path. Preserve identity, geometry, depth, weather
  decisions, selected instant, and forecast behavior.
- **OX-20:** Keep manifest v1 closed and unchanged. Preserve `packId` grammar,
  unique object member names, world-definition reference semantics, and the
  identity-only meaning of `engineCompatibility`; carry the unique-member
  rejection into the v2 contract.
- **OX-51:** Use canonical relative slash-separated paths, only `image/png`,
  and exact-byte SHA-256. Do not infer trusted origin or entitlement from
  identity or digest.
- **OX-36:** Add no weather interpretation or provider semantics to the
  manifest, capability, or asset reference.
- Keep the public capability ID distinct from internal Kotlin enum, class, or
  method names. No optional capability or fallback is included.

## Exclusions and handoffs

No runtime builder, decoder, importer, path/resource validator, archive or
transport format, installer, renderer loader, network delivery, trusted-origin
provider, entitlement, storefront, app behavior, or UI. No font resource,
optional capability, Park asset, weather rule, numeric budget, or broader
manifest authoring system. No device, installed visual, accessibility, system,
or environment matrix. OX-30 owns the builder; OX-26 owns runtime decode; OX-91
owns resource validation; OX-55 owns renderer instantiation; OX-57 owns package
installation. This slice supplies bounded source examples and contract only.

## Execution sequence

1. Write the v2 design-time contract from the approved board and the OX-20,
   OX-51, and OX-52 contracts. Define exactly the seven-member envelope,
   unique object member names, integer/string types, one-item capability and
   asset-reference arrays, and four-string reference object; keep v1 unchanged
   and make the independent version meanings explicit.
2. Register the approved required capability under renderer policy version 1
   with its OX-24 evidence and design-time limitation. Keep registry v1 free of
   optional capabilities and do not reuse OX-52 test-only fixture IDs.
3. Copy the baseline and olive PNGs byte-for-byte into their separate sample
   pack roots. Add one complete manifest per root with exact digest values and
   links from the v2 contract. Confirm both relative paths resolve inside the
   intended sample root.
4. Add the focused test-only contract check, including duplicate object member
   name rejection from raw JSON. It must not claim a production decoder,
   resource validator, importer, or renderer was exercised.
5. Run only the authorized focused test, JSON parse, workflow, and diff checks;
   inspect all source, copies, manifests, and contract changes against the
   fingerprinted board and record actual outcomes under the cycle evidence
   directory.

## Exit and authorized verification

Close OX-53 only when both examples identify the manifest version, First City
definition, compatible renderer capability, and relative asset with its exact
digest; the closed v1 contract remains intact; the example assets resolve from
their sample roots; repeated JSON object member names are rejected under v2;
and the focused checks agree with the approved board.

The owner authorized focused slice-specific tests. Authorized verification is:

- `python -m unittest discover -s scripts/tests -p 'test_scene_pack_manifest_v2_examples_contract.py'` — focused design-time examples and exact asset checks.
- `python -m json.tool docs/scene-pack-examples/first-city-canopy-baseline/manifest.json > /dev/null` — baseline JSON syntax.
- `python -m json.tool docs/scene-pack-examples/first-city-canopy-olive/manifest.json > /dev/null` — olive JSON syntax.
- `python scripts/dev.py workflow` and `git diff --check`.
- Inspect the v1/v2 contracts, policy capability entry, each manifest and asset reference, source/copy digests, focused test, and final diff.

No app build, installed screenshot, device test, system test, accessibility
matrix, or broad suite is authorized or required for this documentation and
test-only slice. Do not claim runtime pack loading or installed acceptance.

## Evidence, risks, and limitations

Evidence target: `.codex/test-artifacts/159-ox53-example-pack-manifests/README.md`.
Closeout target: `.codex/history/159-ox53-example-pack-manifests.md`; archived
plan target: `.codex/history/plans/159-ox53-example-pack-manifests.md`.
These are targets until execution and closeout. The two committed PNG copies
add about 2.6 MB; focused exact-byte checks keep them aligned with their source
assets. The focused test proves only the design-time example contract and
pack-root references; it does not demonstrate a runtime decoder, validator,
installer, or renderer that loads package assets.
