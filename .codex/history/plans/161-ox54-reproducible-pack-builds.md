# Cycle plan — 161-ox54-reproducible-pack-builds

State: CLOSED
Plan maturity: FINAL
Cycle ID: `161-ox54-reproducible-pack-builds`
Roadmap items: OX-54
Objective: Prove repeatable scene-pack builds and define a stable canonical content identity across identical inputs and one distinct First City variant.

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "161-ox54-reproducible-pack-builds",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-54",
      "depends_on": [
        "OX-30"
      ],
      "status": "DONE",
      "exit": "Two builds from identical documented baseline inputs have the same canonical content identity, while the supported OX-53 olive variant has a different identity. The content-digest rule, reproducible inputs/versions, and archive-metadata treatment are documented; future reader/installer contracts consume that same identity definition.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.dot",
              "sha256": "4b759ce4f77691178b9c7ddecd4ba635d3cad08afeab2f05190ee01fc2118a9d"
            },
            {
              "path": ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.svg",
              "sha256": "56042909cd9518d7bba9a3b0face8cb8c9578358ab95813707b34a9727b7aa40"
            },
            {
              "path": ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.png",
              "sha256": "abdda9534389a49802e66ad7ecfbb6441324e28a64f272543b2f02ccd621a7cf"
            },
            {
              "path": ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.md",
              "sha256": "2e0a0c407233f5429e67089b4946aed801c2884e8ba30fe27cd14b075d97354e"
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
      "outcome": "Two independent builds from identical First City baseline sources with manifest version 2 reported the same canonical content identity sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f; the OX-53 olive variant reported the distinct identity sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a. The exact payload identity rule, archive metadata exclusions, and future reader/installer handoff are documented. Focused tests passed and all three artifacts were inspected for exact source-byte preservation.",
      "evidence": [
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/README.md",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/focused-builder-tests.txt",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1-output.txt",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2-output.txt",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant-output.txt",
        ".codex/test-artifacts/161-ox54-reproducible-pack-builds/zip-payload-inspection.txt",
        "docs/SCENE_PACK_CONTENT_IDENTITY.md",
        "docs/SCENE_PACK_BUILDER.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "OX-54 is complete. The builder computes and reports a domain-versioned SHA-256 content identity from the exact validated manifest and declared resource bytes before ZIP creation. Two independent First City baseline builds reported sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f; the supported olive variant reported sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a. The standalone contract documents byte framing, archive metadata exclusions, signal distinctions, and future reader/installer use. System and device checks were outside scope and are not claimed.",
    "verification": [
      "python -m unittest discover -s scripts/tests -p 'test_scene_pack_builder.py' — PASS; 11 focused tests.",
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip — PASS; manifestVersion=2, contentIdentity=sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.",
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip — PASS; manifestVersion=2, contentIdentity=sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.",
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip — PASS; manifestVersion=2, contentIdentity=sha256:f985286099435fe8aab7d2d31beb8c702006b2241ba8c2f71ddd05f507dd665a.",
      "Direct ZIP payload inspection — PASS; exact member sets, source-byte matches, and non-executable entries verified; output preserved in zip-payload-inspection.txt.",
      "python scripts/dev.py workflow — PASS at ACTIVE / CLOSEOUT; Workflow scaffold: OK.",
      "git diff --check — PASS.",
      "Final scoped diff review — PASS; system/device checks not run per slice scope."
    ],
    "evidence_directory": ".codex/test-artifacts/161-ox54-reproducible-pack-builds/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "161-ox54-reproducible-pack-builds",
  "slice_ids": [
    "OX-54"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.dot",
    ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.svg",
    ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.png",
    ".codex/concepts/OX-54-reproducible-pack-builds-behavior-board.md"
  ],
  "completed_gates": [
    "OX-54 is recorded DONE with baseline repeatability, distinct olive identity, normative documentation, focused authorized checks, and actual artifact evidence."
  ],
  "decisions": [
    "Product owner approved the proposed domain-versioned payload SHA-256 framing, archive-metadata exclusion, and repeatable-baseline/distinct-olive acceptance outcomes; no additional board constraints were stated.",
    "The approved board companion retains pre-approval status wording; the plan records it as stale and leaves the fingerprinted package unchanged.",
    "Focused OX-54 tests are authorized; system and device tests are out of scope.",
    "A fixed test vector belongs in scripts/tests/test_scene_pack_builder.py, alongside the focused framing checks, rather than in the production builder module.",
    "Archive-metadata test oracle: compare builder output with the canonical digest of the validated payload member map, calculated before ZIP creation; ZIP-byte equality is not required. Supported by scripts/build_scene_pack.py's validate-then-write boundary and the approved board's metadata exclusions and archive-byte distinction."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Reviewed the complete final tracked diff and confirmed the new content contract, approved board package, current bounded plan, and cycle evidence are the only intended untracked artifacts.",
    "Final CLOSEOUT workflow validation passed and git diff --check passed; exact outputs are preserved under the cycle evidence directory.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/README.md",
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/workflow-closeout.txt",
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/git-diff-check.txt",
    ".codex/history/161-ox54-reproducible-pack-builds.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/161-ox54-reproducible-pack-builds.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/README.md",
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/workflow-closeout.txt",
    ".codex/test-artifacts/161-ox54-reproducible-pack-builds/git-diff-check.txt"
  ],
  "next_action": "Archive cycle 161 with the recorded OX-54 outcome, exact verification results, evidence paths, and scope limits."
}
```
<!-- cycle-checkpoint:end -->

## Scope, dependency, and board approval

OX-54 is the only roster slice. Its direct dependency, OX-30, is DONE; OX-53 supplies the accepted baseline and olive manifest-v2 examples. Preserve the upstream OX-20 logical `packId`, OX-51 exact-file asset digest, and OX-52 trust/capability distinctions.

The product owner approved the objective-specific behavior/contract package on 2026-11-19. The cycle manifest records fingerprints for `.codex/concepts/OX-54-reproducible-pack-builds-behavior-board.dot`, `.svg`, `.png`, and `.md`. The approved companion retains its pre-approval “Approval is pending” wording; treat that status phrase as stale under prior board-review precedent and do not edit the fingerprinted package. UI artwork is not applicable because this slice changes local build output and contracts, not app-visible output.

## Bounded scope

Define and implement one canonical content-identity rule in the local scene-pack builder. Demonstrate repeatability by building the committed First City baseline twice from identical source files and manifest version, then build the supported OX-53 olive variant and show its identity differs. Document the rule in a standalone `docs/SCENE_PACK_CONTENT_IDENTITY.md` contract and link it from `docs/SCENE_PACK_BUILDER.md` so later readers and installers can consume the same definition.

The builder reports the derived content identity alongside its artifact path and manifest version. Preserve the closed manifest-v2 bytes, ZIP payload layout, source validation, and data-only boundary. The identity is derived; do not add a manifest field. OX-54 documents the later validator/installer contract but does not implement runtime decoding, resource validation, installation, or activation.

## Canonical content-identity rule

Use the owner-approved rule from the board:

- Input is the full declared payload member map: the exact `manifest.json` bytes plus every declared resource's exact bytes, keyed by its canonical slash-separated relative path. For the current OX-30/OX-53 examples this is the root manifest and one PNG.
- Hash a domain/version prefix (`oxygen-scene-pack-content-v1` followed by NUL), a 32-bit big-endian member count, then members sorted by UTF-8 path bytes. For each member, hash the 32-bit big-endian path-byte length, path bytes, 64-bit big-endian payload-byte length, and exact payload bytes.
- Report the lowercase digest as `sha256:<64 hex characters>`. Do not parse and reserialize JSON or normalize payload bytes.
- Exclude ZIP headers and other container metadata, member order, timestamps, compression, and entry modes. A SHA-256 over exact ZIP bytes, if an external transport process records one, is a separate archive/transport-byte integrity value, not the OX-54 content identity.

`packId` remains stable logical identity across revisions. OX-51's asset `sha256` remains exact referenced-file integrity. The OX-54 payload digest identifies the complete declared content byte set. None implies trusted origin, entitlement, or renderer support.

## Invariants and exclusions

- Keep both OX-53 First City examples on `worldDefinitionRef: first-city`, `manifestVersion: 2`, `engineCompatibility: 1`, `rendererPolicyVersion: 1`, and required capability `first-city.canopy.image-png`; their distinct `packId`, asset reference, path, and PNG bytes remain authored inputs.
- Preserve the builder's current source validation, exact manifest/resource bytes, canonical paths, and non-executable ZIP entries. Identity computation must use the validated byte inputs and not weaken rejection behavior.
- Keep content identity, logical pack ID, per-asset integrity, archive/transport-byte integrity, renderer compatibility, trusted origin, and entitlement separate.
- No manifest schema change, app UI, weather or scene behavior change, network, entitlement/origin decision, runtime decoder, OX-91 resource validator, OX-57 installer, OX-55 activation, or Park work.
- Focused OX-54 tests are authorized. System and device tests are out of scope; no installed or visual acceptance is claimed.

## Execution order

1. Implement the exact canonical framing in `scripts/build_scene_pack.py`, deriving the digest from validated source members before ZIP creation. Include the digest in successful builder output without changing the existing artifact layout or manifest.
2. Add `docs/SCENE_PACK_CONTENT_IDENTITY.md` with the byte-level rule, digest label, excluded archive metadata, separate signal meanings, and later reader/installer handoff. Link it from `docs/SCENE_PACK_BUILDER.md`.
3. Extend `scripts/tests/test_scene_pack_builder.py` with at least one fixed test vector and focused checks for framing/path-order stability, exact-byte sensitivity, identical baseline builds producing equal reported identities, the olive variant producing a different identity, and archive metadata being outside the digest input. Use the content-member boundary as the oracle for archive-metadata exclusion: compare the builder-reported identity with the digest of the validated payload member map under the specified framing, calculated before ZIP creation. Do not calculate the expected identity from ZIP bytes or require the two baseline ZIP artifacts to be byte-identical. Preserve existing OX-30 checks.
4. Build two baseline artifacts and one olive artifact under the cycle evidence directory; inspect member sets and exact manifest/resource bytes, capture all command output and content identities, then review the scoped diff.

## Authorized verification and evidence

Run only the focused builder test command:

```text
python -m unittest discover -s scripts/tests -p 'test_scene_pack_builder.py'
```

Also run these three documented builder invocations and preserve their exact output. Use separate paths so the two baseline builds are independent artifacts:

```text
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip
```

The evidence README records source roots and manifest versions, both matching baseline identities, the different olive identity, the canonical rule, inspected ZIP members and exact payload-byte matches, the focused test results, `python scripts/dev.py workflow`, and `git diff --check`. Do not run the full suite, Android build, system matrix, or device tests.

## Risks and compatibility

The main compatibility risk is future consumers independently implementing a different byte framing or confusing `packId`, asset hashes, payload identity, and ZIP hashes. A standalone normative document, exact fixed vector, and focused tests keep one rule visible and reproducible. Future consumers must apply the same path sorting, byte framing, and prefix to the declared payload member set; their runtime code remains separately scoped.

## Evidence targets

- `.codex/test-artifacts/161-ox54-reproducible-pack-builds/README.md`
- `.codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-1.zip`
- `.codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-baseline-build-2.zip`
- `.codex/test-artifacts/161-ox54-reproducible-pack-builds/first-city-olive-variant.zip`
- `.codex/history/161-ox54-reproducible-pack-builds.md`
- `.codex/history/plans/161-ox54-reproducible-pack-builds.md`
