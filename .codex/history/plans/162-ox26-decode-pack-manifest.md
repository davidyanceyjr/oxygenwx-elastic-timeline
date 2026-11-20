# Cycle plan — 162-ox26-decode-pack-manifest

State: CLOSED
Plan maturity: FINAL
Cycle ID: `162-ox26-decode-pack-manifest`
Roadmap items: OX-26
Objective: Decode and validate one real generated data-only scene-pack manifest into a typed result without importing or activating the pack

## Cycle roster and board approvals

Edit the structured roster below. Record each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval before finalizing.
The activation command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "162-ox26-decode-pack-manifest",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-26",
      "depends_on": [
        "OX-30",
        "OX-52",
        "OX-54"
      ],
      "status": "DONE",
      "exit": "The real generated manifest yields a typed decoded/accepted result; malformed or version-incompatible manifests yield a bounded rejection without executing pack code.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-19",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.dot",
              "sha256": "3706ac4344c5c29b8f7a2b910d7ac3f2b08f9a262d788264ea1d834d07a308af"
            },
            {
              "path": ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.svg",
              "sha256": "bcec3b08dfe91f2bb2aa15dbea0e7d525d4ea0b53589e870bd90599ead6ce5ae"
            },
            {
              "path": ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.png",
              "sha256": "8064dc24dd15ffdf3b33de6b39595b66dc4584873d063f65bc8a212c27fa4925"
            },
            {
              "path": ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.md",
              "sha256": "8cf212a840ab5f9f2bfd1b4b8b676e73bb4fa85cb478011798b92f286571e9c3"
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
      "outcome": "The generated OX-30 manifest decodes to the expected typed declaration. Malformed, duplicate, trailing, structurally invalid, version-incompatible, unsupported-capability, over-limit, stream-read-failing, and noncanonical-path inputs return the specified bounded categories. The decoder does not resolve or inspect pack resources. Focused OX-26 verification passed 12 tests and evidence is preserved under the cycle artifact directory.",
      "evidence": [
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/README.md",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/builder-output.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-initial-failure.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-relative-path-failure.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/manifest-payload-inspection.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/workflow-execute.txt",
        ".codex/test-artifacts/162-ox26-decode-pack-manifest/git-diff-check-execute.txt",
        "docs/SCENE_PACK_MANIFEST_DECODER.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-19",
    "summary": "Cycle 162 completed OX-26. The bounded manifest-v2 decoder returns the accepted typed declaration for the generated OX-30 First City artifact and categorized rejections for malformed, invalid, unsupported-version, unsupported-capability, over-limit, and input-read-failure cases. It validates canonical relative asset-path syntax as INVALID_MANIFEST while leaving filesystem resolution, confinement, symlinks, and resource checks to OX-91. Focused verification passed all 12 tests, and exact artifact/evidence records are preserved under .codex/test-artifacts/162-ox26-decode-pack-manifest/.",
    "verification": [
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip — PASS; manifestVersion=2 and contentIdentity=sha256:38a4b402d8a997e2c248c52b342bace98bb5553ae15142c072d1524fd9e65e1f.",
      "OX26_PACK_ARTIFACT=/home/opsman/project_git/oxygenwx-elastic-timeline/.codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackManifestDecoderTest — PASS; 12 tests, 0 skipped, 0 failures, 0 errors. The initial test-helper compile error and relative artifact-path NoSuchFileException were corrected; both logs are preserved.",
      "Direct source/artifact manifest comparison — PASS; 487 bytes each, exact byte match, SHA-256 746552f60a457d6a99a1adde53ffca597ca9647b78f57dd46e2448f7f79b68fd. Inspection read only the manifest entry.",
      "python scripts/dev.py workflow — PASS at ACTIVE / EXECUTE and ACTIVE / CLOSEOUT; exact output preserved in workflow-execute.txt and workflow-closeout.txt.",
      "git diff --check — PASS with no output at EXECUTE and CLOSEOUT; new text additions also passed git diff --no-index --check.",
      "Final scoped diff review — PASS; approved OX-26 board package fingerprints remained unchanged, and path syntax plus the OX-91 filesystem/resource boundary are recorded in the roadmap and plan.",
      "No full unit suite, Android build/lint, system matrix, installed device, or visual acceptance checks were run; they are outside this slice."
    ],
    "evidence_directory": ".codex/test-artifacts/162-ox26-decode-pack-manifest/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "162-ox26-decode-pack-manifest",
  "slice_ids": [
    "OX-26"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.dot",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.svg",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.png",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.md"
  ],
  "completed_gates": [
    "final_plan_reviewed",
    "all_slice_boards_approved",
    "OX-26_exit_met",
    "OX-26_evidence_recorded"
  ],
  "decisions": [
    "Review found no unresolved owner decisions; the approved scope, test authorization, numeric bounds, and OX-91 boundary are internally consistent. The approved unreadable-input branch now maps to INPUT_READ_FAILURE, and Jackson 2.19.2 provides the planned nesting and token-count constraints.",
    "Repository-grounded rejection mapping resolved: malformed, duplicate-member, or trailing JSON maps to MALFORMED_JSON; parsed JSON that violates the closed v2 shape, exact types, or field-value rules maps to INVALID_MANIFEST; unsupported manifestVersion, engineCompatibility, or rendererPolicyVersion maps to UNSUPPORTED_VERSION; unknown or unsupported required capability IDs map to UNSUPPORTED_CAPABILITY; byte, nesting, or token limit overruns map to LIMIT_EXCEEDED; and supplied-stream read failures map to INPUT_READ_FAILURE with partial bytes discarded. The mapping follows the approved OX-26 board and docs/SCENE_PACK_MANIFEST_V2.md plus docs/SCENE_PACK_RENDERER_COMPATIBILITY.md. Focused tests and evidence now require the exact category for each branch.",
    "Focused OX-26 tests remain authorized for execution; system and device tests are excluded; none were run during planning or review.",
    "Product owner selected option 1: OX-26 validates canonical relative path syntax from docs/SCENE_PACK_ASSET_REFERENCES.md and maps syntax violations to INVALID_MANIFEST. OX-91 retains filesystem-dependent path resolution, pack-root confinement, symlink and entry checks, resource inspection, and digest/content-identity validation. Treat the lexical check as v2 field-value validation; the approved board package remains unchanged.",
    "Owner-selected path behavior is implemented: OX-26 validates canonical relative path syntax as manifest field data and returns INVALID_MANIFEST for violations; OX-91 owns filesystem-dependent path and resource checks."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Completed full review and finalized the OX-26 execution plan, including the explicit input-read failure outcome; the cycle remains PLANNED at REVIEW and no implementation or tests were run.",
    "Resolved the rejection-category mapping from the approved board and manifest-v2/policy contracts; updated the execution test and evidence expectations to assert each exact category without changing scope or authorized verification.",
    "Pre-activation review compared assetReferences.path handling in the finalized plan with the v2 and OX-51 contracts and identified an unresolved decoder/OX-91 validation boundary; no implementation or tests began.",
    "Recorded the owner resolution and clarified the plan to validate canonical path syntax in OX-26, classify invalid path syntax as INVALID_MANIFEST, and preserve filesystem-dependent resource checks for OX-91.",
    "Activated OX-26 for execution.",
    "Closed slice OX-26 as DONE.",
    "Recorded OX-26 DONE after the generated artifact decoded to the accepted typed result and all focused acceptance categories passed; evidence paths are saved in the cycle roster.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/README.md",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/builder-output.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-initial-failure.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-relative-path-failure.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/manifest-payload-inspection.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/workflow-execute.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/git-diff-check-execute.txt",
    "docs/SCENE_PACK_MANIFEST_DECODER.md",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/workflow-closeout.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/git-diff-check-closeout.txt",
    ".codex/history/162-ox26-decode-pack-manifest.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/README.md",
    ".codex/plans/162-ox26-decode-pack-manifest.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.dot",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.svg",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.png",
    ".codex/concepts/OX-26-pack-manifest-decoder-behavior-board.md",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackManifestDecoder.kt",
    "app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackManifestDecoderTest.kt",
    "docs/SCENE_PACK_MANIFEST_DECODER.md",
    "docs/SCENE_PACK_MANIFEST_V2.md",
    "docs/SCENE_PACK_ASSET_REFERENCES.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/README.md",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/builder-output.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-initial-failure.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test-relative-path-failure.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/manifest-payload-inspection.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/workflow-execute.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/git-diff-check-execute.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/workflow-closeout.txt",
    ".codex/test-artifacts/162-ox26-decode-pack-manifest/git-diff-check-closeout.txt"
  ],
  "next_action": "Archive cycle 162 with the recorded OX-26 outcome, exact verification results, evidence paths, and scope limits."
}
```
<!-- cycle-checkpoint:end -->

## SLICE intake

OX-26 is the sole selected `NEXT` slice for cycle 162. Its dependencies
OX-30, OX-52, and OX-54 are recorded DONE. The cycle contains only OX-26;
focused slice-specific tests are authorized, while system and device tests are
out of scope.

The objective-specific behavior/contract board was approved by the product
owner on 2026-11-19 and fingerprinted in the cycle roster. UI artwork is not
applicable because the slice changes no visible output. No implementation or
tests were run during this planning stage.

## Objective, scope, invariants, verification, and evidence

### Approved board and dependencies

The product owner approved the four-file OX-26 behavior/contract package on
2026-11-19. The cycle roster records the current fingerprints for
`.codex/concepts/OX-26-pack-manifest-decoder-behavior-board.dot`, its SVG and
PNG renders, and the companion Markdown contract. The board governs a bounded
manifest-v2 decode from the real OX-30 First City artifact, a typed accepted
result or bounded rejection, and a handoff to OX-91 for resource validation.
OX-26 validates the path's canonical relative syntax under
`docs/SCENE_PACK_ASSET_REFERENCES.md`; OX-91 retains filesystem-dependent path
resolution, pack-root confinement, symlink checks, and resource validation.
The slice excludes asset-byte access, digest comparison, OX-54 content-identity
validation, import, activation, and visible output changes.
UI artwork is not applicable.

OX-30, OX-52, and OX-54 are DONE. OX-30 supplies the builder and the real
baseline artifact format. OX-52 assigns OX-26 the finite manifest-input and
structural-work boundary. OX-53 defines the closed manifest-v2 envelope and
the required `first-city.canopy.image-png` capability under renderer policy
v1. OX-54 defines canonical payload identity; OX-91 owns validation of that
identity together with asset/resource inspection. The existing `Jackson`
runtime dependency and strict duplicate detection pattern are available in
the app module, so no new parsing dependency is proposed.

### Bounded implementation

Add a side-effect-free decoder in
`app/src/main/java/com/oxygen/weather/timeline/data/scenepack/`. Its input is
the root `manifest.json` stream supplied from the package; the focused
acceptance test opens that entry from the actual OX-30 ZIP and passes only the
manifest stream. The decoder owns the bounded read and JSON parse. It does not
open or enumerate package resources, extract files, or resolve paths; a later
OX-91 validator can supply the same manifest stream after its package-level
checks.

Return a typed manifest containing the OX-20 identity fields, the separate
renderer-policy version, required capability IDs, and the single declared
asset-reference record. Preserve accepted path and digest strings as data for
OX-91. Validate field shape and value syntax, including the closed seven-field
v2 envelope, `packId` syntax, exact JSON types without coercion, the
`image/png` declaration, nonempty `refId`, the lowercase 64-character
`sha256` form, and the canonical relative path syntax in
`docs/SCENE_PACK_ASSET_REFERENCES.md`. Reject an empty path, absolute,
drive-form, or URI-form paths, backslashes, empty path segments, `.` segments,
and `..` segments. Preserve an accepted path string verbatim; do not normalize
it. Do not resolve the path, inspect package entries or asset bytes, or compare
the digest with file bytes. OX-91 owns path resolution, pack-root confinement,
symlink and entry checks, resource inspection, and digest/content-identity
validation.

Represent the outcome as a typed accepted value or a typed rejection with one
stable category: `MALFORMED_JSON`, `INVALID_MANIFEST`, `UNSUPPORTED_VERSION`,
`UNSUPPORTED_CAPABILITY`, `LIMIT_EXCEEDED`, or `INPUT_READ_FAILURE`. A failure
while reading the supplied stream returns `INPUT_READ_FAILURE` without parsing
partial bytes. Reject trailing JSON content. The decoder reads the supplied
manifest stream up to its own cap and leaves closing the caller-owned stream
to the caller.

Use this category mapping as the decoder and focused-test oracle:

| Rejection condition | Category |
| --- | --- |
| Invalid JSON syntax, duplicate object member names, or trailing JSON content | `MALFORMED_JSON` |
| Parsed JSON that violates the closed v2 envelope, exact JSON types, or field-value rules, including missing/null/extra fields, invalid identity or asset-reference values, and noncanonical asset-path syntax | `INVALID_MANIFEST` |
| Unsupported `manifestVersion`, `engineCompatibility`, or `rendererPolicyVersion` value | `UNSUPPORTED_VERSION` |
| Unknown or unsupported ID in `requiredCapabilities` | `UNSUPPORTED_CAPABILITY` |
| Input exceeds 16 KiB, or Jackson reports the configured nesting-depth or token-count limit | `LIMIT_EXCEEDED` |
| Reading the caller-supplied stream fails before the bounded input has been completely read | `INPUT_READ_FAILURE`; discard partial bytes and do not parse them |

These mappings keep parser syntax failures distinct from parser resource-limit
failures, and keep unsupported version/capability values distinct from other
manifest validation failures. Tests assert the specified category, not only
that a rejection occurred.

Accept only the currently evidenced values: `manifestVersion: 2`,
`worldDefinitionRef: first-city`, `engineCompatibility: 1`,
`rendererPolicyVersion: 1`, and required capability
`first-city.canopy.image-png`. Reject unsupported versions, unknown required
capabilities, malformed or duplicate JSON members, missing/null/extra fields,
wrong field types, and noncanonical asset paths. Never return a partially
accepted manifest. The JSON is data only and must not execute pack-supplied
behavior.

Use finite decode bounds chosen from the real OX-30 manifest and exact v2
shape. The checked-in baseline manifest is 487 bytes, with a maximum valid
nested depth of 3, 29 JSON tokens, seven root object members, four asset
reference members, and one value in each declared array. Set a 16 KiB maximum
manifest byte length, maximum JSON nesting depth 4, and maximum parser token
count 128. These give modest room above the existing contract while bounding
input and parser work; the exact envelope counts and one-item arrays remain
schema rules. Enforce the byte cap while reading, then configure Jackson
`StreamReadConstraints` with `maxNestingDepth(4)` and `maxTokenCount(128)`, plus
strict duplicate detection, before constructing the tree. The app's Jackson
2.19.2 API provides both structural limits; no new parser dependency or
token-counting wrapper is needed. OX-91 resource limits and OX-57 extraction
limits remain separate.

### Invariants and exclusions

- Keep `packId`, per-asset SHA-256, OX-54 content identity, trusted origin,
  renderer capability, and entitlement distinct.
- Preserve the manifest-v2 contract exactly. Do not add fields, default
  missing values, coerce JSON types, accept unknown required capabilities, or
  reinterpret versions.
- Do not inspect archive member inventory or PNG bytes; resolve asset paths or
  enforce pack-root confinement, symlink checks, actual file/media validity,
  file digests, content identity, or resource budgets; extract or stage files;
  import/install a pack; activate a scene; change weather interpretation; or
  add UI. OX-26 only checks canonical relative path syntax from the v2 field
  contract and preserves the accepted string for OX-91.
- Add no new JSON library, Android integration, network path, storage path,
  renderer path, system/device matrix, or broader app acceptance work.

### Execution sequence

1. Add the typed manifest/reference values and decode result in the new
   `data/scenepack` package. Use Jackson's existing strict duplicate-detection
   support, capped stream read, and 16 KiB / depth-4 / 128-token constraints.
   Validate the exact v2 envelope, including canonical relative asset-path
   syntax, and return accepted or bounded-rejection results without side
   effects. Do not resolve paths or inspect package resources.
2. Add
   `app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackManifestDecoderTest.kt`.
   Cover the accepted baseline declaration, malformed and duplicate-member
   JSON, missing/extra/wrong-type fields, invalid identity/version/capability
   values, and noncanonical path syntax: empty, absolute, drive-form,
   URI-form, backslash, empty-segment, `.`-segment, and `..`-segment paths.
   Assert `INVALID_MANIFEST` for each path case, plus the category specified in
   the rejection mapping for every other rejected case. Also cover
   stream-read failure and each decode limit. When
   `OX26_PACK_ARTIFACT` is set, the
   acceptance case reads the root manifest entry from that generated ZIP;
   use an assumption to skip only that artifact-backed case when the variable
   is unset so ordinary unit runs keep the remaining cases self-contained.
3. Add `docs/SCENE_PACK_MANIFEST_DECODER.md` documenting the typed result,
   selected finite limits, rejection categories, canonical path syntax check,
   and OX-91 filesystem/resource handoff. Link it from
   `docs/SCENE_PACK_MANIFEST_V2.md` without changing the v2 schema or claiming
   resource validation. Update the OX-52 budget row in
   `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` with these owner-specific
   OX-26 limits and their evidence, leaving other owners' budgets unchanged.
4. Build the committed First City baseline with the existing OX-30 command
   into the cycle evidence directory, run only the focused decoder test class
   with `OX26_PACK_ARTIFACT` set to that artifact, and preserve exact output.
   Inspect the source/artifact manifest bytes and scoped diff before closeout.

### Authorized verification and evidence

The owner authorized focused OX-26 slice-specific tests. System and device
tests are out of scope. Run only:

```text
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip
OX26_PACK_ARTIFACT="$(pwd)/.codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip" ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackManifestDecoderTest
python scripts/dev.py workflow
git diff --check
```

The evidence README records the exact builder and focused-test results, the
accepted typed values, the three chosen finite decode limits and their source
measurements, each rejected-input case and its asserted category (including
all noncanonical path-syntax cases and stream-read failure), and the boundary
limitations.
No full unit suite, Android build/lint, system matrix, device interaction, or
installed visual acceptance is required or claimed.

Evidence targets:

- `.codex/test-artifacts/162-ox26-decode-pack-manifest/README.md`
- `.codex/test-artifacts/162-ox26-decode-pack-manifest/first-city-canopy-baseline-v2.zip`
- `.codex/test-artifacts/162-ox26-decode-pack-manifest/focused-test.txt`
- `.codex/history/162-ox26-decode-pack-manifest.md`
- `.codex/history/plans/162-ox26-decode-pack-manifest.md`

### Risks and compatibility

The main compatibility risk is conflating manifest decoding with resource
validity, OX-54 content identity, or trust. The approved board and decoder
documentation keep those as independent OX-91 and later decisions. The 16 KiB,
depth-4, and 128-token limits are intentionally tied to the current closed
v2 schema; any future schema that needs more must revise its versioned contract
and budget evidence rather than silently expanding this decoder.
