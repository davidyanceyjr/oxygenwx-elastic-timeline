# Cycle plan — 160-ox30-scene-pack-builder

State: PLANNED
Plan maturity: FINAL
Cycle ID: `160-ox30-scene-pack-builder`
Roadmap items: OX-30
Objective: Build one versioned declarative scene-pack artifact from the approved OX-53 example manifest and assets

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "160-ox30-scene-pack-builder",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-30",
      "depends_on": [
        "OX-53"
      ],
      "status": "PLANNED",
      "exit": "One documented invocation builds a self-contained non-executable artifact from the baseline source, preserving exact manifest and PNG bytes at their required paths; successful output reports artifact path and manifest version, while rejected input reports its path and reason without reporting success.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-30-scene-pack-builder-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-30-scene-pack-builder-behavior-board.dot",
              "sha256": "e0b5bd8319e7742cd530a437a6103b187681b924d4441aa375d9505f45e81894"
            },
            {
              "path": ".codex/concepts/OX-30-scene-pack-builder-behavior-board.svg",
              "sha256": "288b1c372f4bf2d55aa4b804985caf9a886ef38a6c078e9ef51e517913da7aef"
            },
            {
              "path": ".codex/concepts/OX-30-scene-pack-builder-behavior-board.png",
              "sha256": "a1777625e3dc7e9c33c6f1daa2e9d0a7951aa852f538f6e2b8d17a023a9b6f95"
            },
            {
              "path": ".codex/concepts/OX-30-scene-pack-builder-behavior-board.md",
              "sha256": "d10c00c4a7bb04c7a404a466f6e690730171c13234c20f312bb295f2c93d161a"
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
  "cycle_id": "160-ox30-scene-pack-builder",
  "slice_ids": [
    "OX-30"
  ],
  "stage": "REVIEW",
  "prior_cycle_state": "PLANNED",
  "accepted_boards": [
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.dot",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.svg",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.png",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.md"
  ],
  "completed_gates": [
    "OX-53 is DONE and the OX-30 cycle roster matches the current pointer",
    "Product-owner approval for the exact four-file OX-30 behavior package is recorded; all fingerprints match current files",
    "Pre-approval status wording in the companion is treated as stale under the OX-02 and OX-06 review precedents; the approved package remains unchanged",
    "UI artwork is inapplicable because the local build operation changes no visible app output",
    "Full OX-30 plan review completed with the approved board constraints, scoped verification, evidence target, risks, and exclusions recorded"
  ],
  "decisions": [
    "The recorded owner approval covers the unchanged fingerprinted package; do not edit the approved board files or reopen the gate for stale status wording",
    "The baseline source inventory and rejection behavior follow the approved board and OX-51 contracts",
    "Container format, CLI shape, and output path remain implementation choices; OX-54 owns canonical pack identity and reproducibility"
  ],
  "open_questions": [],
  "completed_outputs": [
    "Rendered and displayed the OX-30 behavior board package on DISPLAY=:0 before owner review",
    "Recorded product-owner approval for the unchanged four-file OX-30 behavior package and verified its fingerprints",
    "Completed full plan review and made source inventory, rejection coverage, and focused verification obligations explicit",
    "python scripts/dev.py workflow — PASS after the final plan/checkpoint update",
    "git diff --check — PASS; the untracked plan file was also checked for trailing whitespace"
  ],
  "evidence": [],
  "next_stage_read_set": [
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/160-ox30-scene-pack-builder.md",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.dot",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.svg",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.png",
    ".codex/concepts/OX-30-scene-pack-builder-behavior-board.md",
    ".codex/test-artifacts/159-ox53-example-pack-manifests/README.md",
    "docs/ROADMAP.md",
    "docs/SCENE_PACK_MANIFEST_V2.md",
    "docs/SCENE_PACK_ASSET_REFERENCES.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    "docs/scene-pack-examples/first-city-canopy-baseline/manifest.json",
    "docs/scene-pack-examples/first-city-canopy-baseline/assets/first-city/canopy/baseline.png",
    "scripts/dev.py",
    "scripts/tests/test_scene_pack_manifest_v2_examples_contract.py",
    "scripts/tests/test_scene_pack_asset_reference_contract.py"
  ],
  "next_action": "Leave cycle 160 PLANNED and unactivated; owner-directed activation may begin execution of OX-30 under the finalized scope"
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

### Scope and dependency

OX-30 is the sole cycle slice. Its direct dependency OX-53 is DONE. The
product owner approved the unchanged four-file behavior package on
2026-11-19, and all recorded fingerprints match. The companion retains
pre-approval proposal wording; treat that status text as stale under the
recorded OX-02 and OX-06 review precedents. The package is
`.codex/concepts/OX-30-scene-pack-builder-behavior-board.{dot,svg,png,md}`;
it governs the build flow, source validation, artifact contents, readable
result, and downstream boundaries. UI artwork is inapplicable because the
local developer command has no visible app output.

Implement a documented local builder invocation for the committed
`docs/scene-pack-examples/first-city-canopy-baseline/` root. Produce one
self-contained, non-executable artifact with the exact `manifest.json` bytes
at its root and the exact referenced PNG bytes at the declared relative path.
Select and document the container format, command and output path as local
implementation details. A successful result reports the artifact path and
manifest version. A rejected source reports the offending input and reason
without reporting a successful artifact. The source inventory must contain
only `manifest.json` and its one declared PNG; reject undeclared files,
symlink entries, and executable content instead of silently omitting them.
The olive source may work under the same contract, but its build is not an exit
requirement.

### Invariants and exclusions

- Preserve the exact closed seven-field manifest v2 shape and JSON types;
  reject duplicate members, `engineCompatibility` values other than `1`,
  `rendererPolicyVersion` values other than `1`, and unsupported required
  capability IDs.
- Validate the single declared asset with OX-51 canonical relative path and
  symlink rules, `image/png`, and SHA-256 over exact file bytes. Reject missing,
  mismatched, unsupported, undeclared, or executable payloads before success.
- Keep pack identity, asset integrity, renderer capability, trusted origin,
  and entitlement distinct. The built artifact claims none of the latter
  three; it is not a runtime validation result.
- Do not change weather interpretation, app world selection, timeline state,
  existing scenes, or the bundled free scene. No app installation, downloader,
  storefront, Park content, UI, or runtime loader is in scope.
- OX-54 owns canonical whole-pack content identity and reproducibility. OX-26,
  OX-91, OX-57, and OX-55 own typed decode, runtime resource validation,
  installation, and activation. This slice does not set their limits.

### Execution order

1. Add a small local builder at `scripts/` with a documented invocation. Read
   the committed baseline example as the first supported source; validate the
   closed v2 manifest, exact source inventory, and referenced file against the
   adopted manifest and OX-51 asset contracts.
2. Package only the validated manifest and declared PNG into one documented
   data-only container. Keep the output outside the source root and avoid
   claiming canonical content identity or reproducibility.
3. Give success and rejection paths readable command results. Document the
   invocation, artifact layout, and validation boundary for the developer.
4. Add focused builder tests, capture actual results, inspect the artifact and
   final diff, and close only after the roadmap exit is evidenced.

### Authorized verification and evidence

Focused slice-specific tests are authorized by the OX-30 owner sequencing
decision. Add `scripts/tests/test_scene_pack_builder.py` and run
`python -m unittest discover -s scripts/tests -p 'test_scene_pack_builder.py'`.
Use a temporary copy of the committed source for rejection cases. Verify the
real baseline artifact's member layout and exact manifest/PNG bytes, plus
readable rejection results for duplicate/extra manifest members, invalid
JSON types, versions or capabilities,
noncanonical or symlinked asset paths, missing or unsupported assets, digest
mismatches, and undeclared/executable source files. Inspect the artifact
directly and record command outputs. Also run `python scripts/dev.py workflow`
and `git diff --check`. Do not run system or device tests; no installed
screenshot or broad Android suite is in scope.
Record exact commands, outputs, artifact layout, failures and limits under
`.codex/test-artifacts/160-ox30-scene-pack-builder/`; closeout target is
`.codex/history/160-ox30-scene-pack-builder.md`.

### Review points

The builder's container and CLI shape are implementation choices within the
approved board. Keep validation source-side and do not claim later runtime
gates or OX-54's identity rule.

### Risks and exclusions

Archive metadata may vary between builds. Do not report a whole-pack identity
or reproducibility result; OX-54 owns that contract. The builder supports the
committed baseline example as the required source and does not create a general
import, install, trust, entitlement, or runtime-loading path.
