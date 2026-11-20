# Cycle plan — 163-ox91-pack-resource-validation

State: CLOSED
Plan maturity: FINAL
Cycle ID: `163-ox91-pack-resource-validation`
Roadmap items: OX-91
Objective: Validate referenced scene-pack resources against the approved resource and trust policy before activation.

## Cycle roster and board approvals

The structured roster records each slice's dependency order, concrete exit,
approved behavior board, and UI artwork applicability/approval. The activation
command rejects incomplete or unapproved records.

<!-- cycle-manifest:start -->
```json
{
  "schema_version": 1,
  "cycle_id": "163-ox91-pack-resource-validation",
  "plan_maturity": "FINAL",
  "active_slice": null,
  "slices": [
    {
      "id": "OX-91",
      "depends_on": [
        "OX-26",
        "OX-51",
        "OX-52",
        "OX-54"
      ],
      "status": "DONE",
      "exit": "A valid generated pack yields one validated artifact/asset set; deliberate path escape, unsupported resource, oversized input or mismatched content are rejected before any scene activation, with no partial asset state.",
      "boards": {
        "behavior": {
          "path": ".codex/concepts/OX-91-pack-resource-validation-behavior-board.dot",
          "approved": true,
          "approved_by": "product owner",
          "approved_on": "2026-11-20",
          "artifacts": [
            {
              "path": ".codex/concepts/OX-91-pack-resource-validation-behavior-board.dot",
              "sha256": "b18165d0d62e7a03e94a5a4b553f3c0f2dbe4d9a68dd84d30af7b66a0c4f2799"
            },
            {
              "path": ".codex/concepts/OX-91-pack-resource-validation-behavior-board.svg",
              "sha256": "7d6e6bcd6825888cd8f70b21de481c5d8b4680789c566d4217ea52387a821d86"
            },
            {
              "path": ".codex/concepts/OX-91-pack-resource-validation-behavior-board.png",
              "sha256": "ee48d2b18744c8727cad79ba5d047a616903d1d04c7caf8dd36db6d9638af5ad"
            },
            {
              "path": ".codex/concepts/OX-91-pack-resource-validation-behavior-board.md",
              "sha256": "c6477e4dcdbd9a973a3ab12f8a5b17627d3260bcb1a308046f7356953f537846"
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
      "outcome": "Implemented bounded read-only resource validation and completed the accepted manifest, archive, PNG structure/CRC, digest, and content-identity checks. Baseline and olive packs are accepted with exact assets and builder-matching identities; focused rejection and inclusive-limit coverage passes. OX-91 exit is met. PNG decompression/pixel validity, extraction, renderer memory, installation, activation, and system/device acceptance remain outside this slice.",
      "evidence": [
        ".codex/test-artifacts/163-ox91-pack-resource-validation/README.md",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-baseline.txt",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-olive.txt",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/focused-tests.txt",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/validation-inspection.txt",
        ".codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py",
        "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidator.kt",
        "app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidatorTest.kt",
        "docs/SCENE_PACK_RESOURCE_VALIDATION.md",
        "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
        "docs/SCENE_PACK_MANIFEST_V2.md",
        "docs/SCENE_PACK_CONTENT_IDENTITY.md"
      ]
    }
  ],
  "closeout": {
    "closed_on": "2026-11-20",
    "summary": "Completed OX-91 with a bounded, read-only manifest-v2 resource validator, focused tests, and runtime validation documentation. The baseline and olive builder packs are accepted with exact source assets, matching declared SHA-256 digests, and builder-matching OX-54 identities. All focused rejection and exact-limit cases pass. The slice exit is met; no UI, schema, builder, decoder, extraction, or activation behavior changed. PNG decompression/pixel validity, extraction/install, renderer memory/activation, and broader system/device acceptance remain outside OX-91.",
    "verification": [
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip: exit 0; baseline asset 1,259,884 bytes.",
      "python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip: exit 0; olive asset 1,386,266 bytes.",
      "OX91_PACK_BASELINE_ARTIFACT and OX91_PACK_OLIVE_ARTIFACT set to the two cycle ZIPs; ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidatorTest: exit 0; 13 tests, 0 failures, 0 skipped.",
      "python .codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py: exit 0; both source assets, declared digests, and builder OX-54 identities matched; both archive inventory bounds were within limits.",
      "python scripts/dev.py workflow: exit 0; Workflow scaffold OK at ACTIVE/CLOSEOUT with OX-91 DONE and no active slice.",
      "git diff --check: exit 0, no output.",
      "Focused new-file whitespace check: exit 0, no trailing whitespace in six new files.",
      "The cycle close operation's post-archive workflow validation passed; full unit suite, Android build, lint, instrumentation, and system/device/visual checks were not run because they are outside the authorized OX-91 verification."
    ],
    "evidence_directory": ".codex/test-artifacts/163-ox91-pack-resource-validation/"
  }
}
```
<!-- cycle-manifest:end -->

## Workflow checkpoint

Update this record at every stage boundary. Keep its next-stage read set exact.

<!-- cycle-checkpoint:start -->
```json
{
  "cycle_id": "163-ox91-pack-resource-validation",
  "slice_ids": [
    "OX-91"
  ],
  "stage": "CLOSEOUT",
  "prior_cycle_state": "ACTIVE",
  "accepted_boards": [
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.dot",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.svg",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.png",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.md"
  ],
  "completed_gates": [
    "OX-91_validator_implemented",
    "OX-91_builder_artifacts_verified",
    "OX-91_focused_test_class_passed_13",
    "OX-91_resource_validation_contract_documented",
    "OX-91_slice_exit_met",
    "OX-91_exit_recorded_DONE"
  ],
  "decisions": [
    "Owner selected bounded PNG structure and CRC checks: validate signature, chunk framing/order, required IHDR/IDAT/IEND structure and CRCs, reject trailing bytes, and leave decompression/pixel decoding to OX-55. A self-consistent but invalid compressed IDAT stream is outside OX-91 validation.",
    "The owner selected the bounded PNG structure and CRC predicate; existing behavior-board package remains unchanged and approved."
  ],
  "open_questions": [],
  "completed_outputs": [
    "Implemented the bounded read-only OX-91 resource validator and focused test class.",
    "Built baseline and olive artifacts; both source bytes, declared SHA-256 values, and builder identities matched.",
    "Passed the focused OX-91 validator test class: 13 tests, 0 failures, 0 skipped.",
    "Added the OX-91 resource-validation contract and updated budget and identity references.",
    "Closed slice OX-91 as DONE.",
    "Advanced OX-91 to CLOSEOUT after recording its DONE outcome and execution evidence.",
    "Cycle closeout summary and verification archived."
  ],
  "evidence": [
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.dot",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.svg",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.png",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.md",
    ".codex/history/162-ox26-decode-pack-manifest.md",
    ".codex/history/157-ox51-pack-assets-integrity.md",
    ".codex/history/158-ox52-renderer-capability-trust.md",
    ".codex/history/161-ox54-reproducible-pack-builds.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    "docs/ROADMAP.md#OX-91",
    "docs/ROADMAP.md#OX-57",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidator.kt",
    "app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidatorTest.kt",
    "docs/SCENE_PACK_RESOURCE_VALIDATION.md",
    "docs/SCENE_PACK_MANIFEST_V2.md",
    "docs/SCENE_PACK_CONTENT_IDENTITY.md",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/README.md",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-baseline.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-olive.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/focused-tests.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/validation-inspection.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py",
    ".codex/history/163-ox91-pack-resource-validation.md"
  ],
  "next_stage_read_set": [
    "AGENTS.md",
    ".codex/current.md",
    ".codex/plans/163-ox91-pack-resource-validation.md",
    "docs/ROADMAP_SPECIFICATION.md",
    "docs/ROADMAP.md#OX-91",
    "docs/ROADMAP.md#OX-57",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.dot",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.svg",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.png",
    ".codex/concepts/OX-91-pack-resource-validation-behavior-board.md",
    "app/src/main/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidator.kt",
    "app/src/test/java/com/oxygen/weather/timeline/data/scenepack/ScenePackResourceValidatorTest.kt",
    "docs/SCENE_PACK_RESOURCE_VALIDATION.md",
    "docs/SCENE_PACK_RENDERER_COMPATIBILITY.md",
    "docs/SCENE_PACK_MANIFEST_V2.md",
    "docs/SCENE_PACK_CONTENT_IDENTITY.md",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/README.md",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-baseline.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/builder-olive.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/focused-tests.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/validation-inspection.txt",
    ".codex/test-artifacts/163-ox91-pack-resource-validation/inspect_artifacts.py"
  ],
  "next_action": "Review the full final diff, record final workflow and whitespace checks, then close cycle 163 with exact verification results and limitations."
}
```
<!-- cycle-checkpoint:end -->

## Objective, scope, invariants, verification, and evidence

## SLICE intake — OX-91

The owner selected OX-91 as the sole NEXT slice after OX-26 closed. Its four
roadmap dependencies, OX-26, OX-51, OX-52, and OX-54, are DONE. The cycle
roster contains only OX-91; no later slice is grouped into this plan.

The roadmap objective and exit are copied into the structured roster. The
dependency contracts establish that OX-26 validates the closed manifest-v2
shape and canonical relative-path syntax; OX-91 owns resource path resolution,
pack-root confinement, symlink checks, allowed media, resource budgets, and
resource inspection; OX-54 defines the canonical content identity over exact
manifest and declared resource bytes. OX-52 leaves numeric resource limits to
evidence in the enforcing slice. OX-55 owns renderer-load memory and OX-57
owns package extraction budgets, including entry-count enforcement.

The finite-budget ownership table assigns package extraction entry-count
enforcement to OX-57. OX-91 inventories central-directory members before that
extraction boundary and has its own pre-extraction inventory cap, selected by
the product owner: at most **64 entries** and **65,536 bytes (64 KiB) of
encoded central-directory records**. Both limits count directory entries;
the byte total includes each record's fixed header and variable filename,
extra-field, and comment bytes. Exact-limit archives pass; excess on either
limit rejects before the per-entry scan or per-entry object creation. The
entry cap bounds per-record work and object handling; the byte cap also
limits long names and extra metadata. Sixty-four entries provide more than
twelve times the current five-record example shape. OX-57's later staged
extraction entry-count and other extraction budgets remain independent and
in force.

The product owner approved the current four-file OX-91 behavior-board package
on 2026-11-20; its fingerprints are recorded in the cycle manifest. The
companion Markdown records that approval and the status wording retained in
the rendered board. UI artwork is not applicable because this validation
slice changes no visible output. The owner authorized focused OX-91
slice-specific tests; system and device tests are out of scope. The separate
inventory cap and bounded ZIP contract are recorded in this plan. This
planning record does not activate the slice or authorize implementation.

## BOARDS — Behavior package approved

The revised Graphviz behavior package is
`.codex/concepts/OX-91-pack-resource-validation-behavior-board.dot`, with SVG
and PNG renders and the companion contract at
`.codex/concepts/OX-91-pack-resource-validation-behavior-board.md`. It maps
manifest decoding, capability availability, package-member/path checks,
resource media and byte limits, per-file digest validation, OX-54 identity
recomputation, whole-candidate rejection, and the complete-result handoff to
later OX-55 work.

The product owner approved this four-file package on 2026-11-20, including
the contextual status wording retained in the rendered board. The companion
Markdown records approval; no graphic update is required. The package governs
the exact-member rule, 1.5 MiB per-resource and aggregate caps, registered-type
byte validation, and recomputation/return of OX-54 identity. The separate
central-directory cap and bounded ZIP structure contract are specified in
this plan. The identity remains distinct from asset digests, origin, and
entitlement. UI artwork is not applicable. The cycle remains PLANNED at
BOARDS and the plan remained DRAFT; approval did not activate implementation.
Plan review is a separate gate.

## Governing contracts and implementation references

- `docs/ROADMAP.md#OX-91` defines the OX-91 objective, dependencies, exit,
  approved board package, and evidence target. `docs/ROADMAP.md#OX-57`
  preserves the later staged-extraction budget boundary.
- `docs/SCENE_PACK_MANIFEST_V2.md` and
  `docs/SCENE_PACK_MANIFEST_DECODER.md` define the accepted declaration and
  OX-26 handoff. `ScenePackManifestDecoder.kt` is the current runtime decoder.
- `docs/SCENE_PACK_ASSET_REFERENCES.md` defines canonical paths and exact-byte
  digest semantics; `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md#Finite-budget-ownership`
  assigns resource, extraction, and renderer-load budgets.
- `docs/SCENE_PACK_CONTENT_IDENTITY.md` defines OX-54 framing.
  `docs/SCENE_PACK_BUILDER.md` and `scripts/build_scene_pack.py` define the
  current generated artifact profile and builder identity output.
- PNG container checks follow the W3C
  [Portable Network Graphics Specification](https://www.w3.org/TR/png-3/).

## Bounded scope and invariants

Implement one read-only data-layer validator for an OX-30-generated
manifest-v2 ZIP. It reads the exact root `manifest.json`, passes its bounded
raw bytes to `ScenePackManifestDecoder`, and validates the single typed asset
reference against the archive and the approved OX-51/OX-52/OX-54 rules.
Preserve the original manifest bytes for identity calculation; do not parse
and reserialize them.

The current manifest-v2 runtime component is OX-26's decoder; there is no
resource-validator or scene-loader call site to migrate. OX-91 adds the
validated result, and OX-55 is its later renderer consumer. Do not add a
scene-activation path in this slice.

On success, return one complete validated value containing the typed manifest,
the exact referenced asset bytes keyed by their canonical paths, and the
recomputed OX-54 content identity. Keep provisional bytes private until every
check passes. On any rejection, return a bounded reason without a partial
asset set, writing files, or activating a scene.

Enforce the owner-approved limits of **1,572,864 bytes (1.5 MiB) per
resource and 1.5 MiB aggregate** on actual uncompressed referenced resource
bytes. The limit is inclusive. The aggregate check remains explicit even
though manifest v2 currently allows one asset reference. The limit applies to
referenced resource bytes, not ZIP metadata. The current largest supported
example is the 1,386,266-byte OX-53 olive PNG.

Additional invariants:

- Call OX-26 for the closed manifest-v2 contract and use its accepted
  capability policy result. Do not duplicate or widen its schema, path syntax,
  or supported capability list.
- Map each canonical relative resource path to exactly one archive file.
  Reject missing or ambiguous entries, path escape, symlink-backed resources,
  and undeclared file payloads. Ignore only empty directory entries that are
  canonical ancestors of declared resources.
- Validate the supported `image/png` media type against the actual resource
  bytes with a bounded container check: require the eight-byte PNG signature;
  parse each chunk with checked length arithmetic and four-letter type codes;
  require one 13-byte IHDR first with legal field values, at most one PLTE
  before IDAT (required for indexed color), one or more consecutive IDAT
  chunks, and one zero-length IEND last with no trailing bytes; verify every
  chunk CRC; and reject unknown critical chunks. Permit ancillary chunks when
  their type, framing, and CRC are valid, without interpreting their payloads
  or ancillary-specific ordering. Scan only the
  already-bounded resource bytes. Do not inflate IDAT data or decode pixels,
  and do not assign renderer-load memory budgets; those belong to OX-55. This
  check does not establish that a self-consistent IDAT payload contains a
  valid compressed image stream.
- Compare each exact resource byte sequence with its declared OX-51 SHA-256.
  Recompute OX-54 identity over the exact manifest and declared resource
  bytes, using UTF-8 path-byte sorting and the specified length framing.
  Identity remains distinct from per-file digest, ZIP-byte digest, pack ID,
  trusted origin, and entitlement.
- Keep path/resource validation separate from OX-57 extraction and install,
  OX-55 renderer instantiation/activation, and all weather or selected-time
  state.
- No UI output changes; no UI artwork or installed visual acceptance applies.

## Implementation and documentation sequence

1. Add `ScenePackResourceValidator.kt` and typed accepted/rejected values in
   `app/src/main/java/com/oxygen/weather/timeline/data/scenepack/`. Use the
   OX-26 decoder, retain raw manifest bytes, enumerate archive entries, and
   read only the declared resources. Verify the supported capability through
   the decoder's policy-v1 acceptance, not a second capability source.
2. Inspect the generated ZIP's central-directory metadata sufficiently to
   reject a symlink at the root manifest or along a declared resource path,
   as required by OX-51. The Android module has no archive-inspection
   dependency, and `java.util.zip.ZipEntry` does not expose Unix file mode.
   Support the current OX-30 builder's classic, single-disk, non-ZIP64,
   unencrypted ZIP output with Unix creator metadata and stored entries;
   reject multi-disk, ZIP64, encrypted, unsupported-compression, or malformed
   structures with a bounded archive rejection. Locate and validate the end
   record using bounded reads without buffering the archive; require
   single-disk fields to agree and validate the central-directory offset and
   extent against the archive bounds. Before opening `ZipFile`, creating
   per-entry objects, or scanning central-directory records, read the end
   record's entry count and central-directory byte size and reject values
   above the owner-selected inclusive limits of 64 entries and 65,536 encoded
   record bytes. Count directory entries in both limits.
   Then parse exactly the declared central-directory records within the
   declared byte range, counting each fixed header plus filename,
   extra-field, and entry-comment bytes. Require parsed record count and
   consumed bytes to match the end-record fields, and cross-check parsed names
   and Unix modes one-to-one against JDK enumeration before reading members.
   Keep `ZipFile` as the entry-stream reader. Do not extract the archive.
   OX-57 retains its distinct staged-extraction entry-count and other package
   extraction limits.
3. Enforce package membership and resource rules: one root manifest, exactly
   one unambiguous entry for the declared asset, no duplicate or undeclared
   file payloads, confined canonical paths, the bounded PNG container
   predicate above, bounded actual resource reads, and OX-51 digest equality.
   Reject malformed chunk framing/order, invalid IHDR values, bad chunk CRCs,
   missing required chunks, and trailing bytes as unsupported resource data.
   Do not inflate IDAT or claim validity of its compressed image stream. Do
   not trust declared ZIP sizes without enforcing the cap against bytes
   actually read.
4. Implement the OX-54 identity framing in the validator using the exact raw
   manifest/resource bytes: the `oxygen-scene-pack-content-v1` NUL-terminated
   domain prefix, unsigned 32-bit big-endian member count and path lengths,
   unsigned 64-bit big-endian payload lengths, and UTF-8 path-byte ordering.
   Return the derived `sha256:` identity only in the complete accepted result;
   manifest v2 has no content-identity field. Use stable bounded rejection
   categories for archive/manifest rejection, unsupported resources, limit
   excess, digest mismatch, and read failure; do not return exception text or
   partial data.
5. Add `ScenePackResourceValidatorTest.kt` beside the decoder tests. Use the
   real baseline and olive artifacts for accepted cases and focused synthetic
   ZIP fixtures for the approved rejection gates. Assert the returned bytes
   and identities, each rejection category, and absence of an accepted result
   after rejection. Cover a noncanonical escaping manifest path, symlink and
   duplicate/undeclared members, a bad PNG signature, malformed/truncated
   chunk framing, invalid IHDR fields, invalid critical chunk order, a chunk
   CRC mismatch, missing IEND, and trailing bytes for the declared `image/png`
   type. Do not add a decompression or pixel-decoding assertion. Also cover
   the exact resource-size boundary and one byte over, missing resources, and
   a declared digest mismatch. Use the supported ZIP_STORED/Unix-creator
   profile for synthetic fixtures, with a malformed or unsupported archive
   fixture to assert bounded archive rejection. Cover the inventory limits independently:
   exactly 64 entries and 65,536 encoded record bytes are accepted when all
   other rules pass; 65 entries and 65,537 bytes reject before record scanning
   or per-entry object creation. Make the 64-entry positive fixture valid by
   using a 63-segment declared asset path with only its 62 empty canonical
   ancestor directories, the root manifest, and the asset. Reach the exact
   byte boundary with well-formed per-entry comment/extra fields, then add
   one byte for the over-limit case. Include a deterministic member-read
   failure fixture, using a package-private stream seam if needed, and assert
   its bounded read-failure category with no accepted result. Assert that an
   unsupported declared media type is rejected by the
   OX-26 handoff. Also verify the Kotlin identity against the identity reported
   by the existing OX-30 builder.
6. Add `docs/SCENE_PACK_RESOURCE_VALIDATION.md` describing the result,
   boundaries, archive/member rules, exact PNG container predicate and its
   no-inflate limitation, 1.5 MiB limits, rejection behavior, and OX-54
   handoff. Update the OX-91 resource-budget row in
   `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` with the selected values and
   sample-size evidence; link the validator contract from the manifest-v2 and
   content-identity docs. Do not change the builder, manifest schema, or
   decoder.

## Authorized focused verification and evidence

Focused OX-91 slice-specific tests are authorized. System and device tests are
out of scope. Build one baseline and one olive artifact in the cycle evidence
directory, then run only the focused validator test class:

```text
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip
python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-olive --output .codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip
OX91_PACK_BASELINE_ARTIFACT="$(pwd)/.codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-baseline-v2.zip" OX91_PACK_OLIVE_ARTIFACT="$(pwd)/.codex/test-artifacts/163-ox91-pack-resource-validation/first-city-canopy-olive-v2.zip" ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackResourceValidatorTest
python scripts/dev.py workflow
git diff --check
```

The evidence README records exact commands and outputs, source and actual
resource sizes, builder identities, accepted asset bytes, and each focused
rejection case. Preserve builder outputs in `builder-baseline.txt` and
`builder-olive.txt`, the focused test output in `focused-tests.txt`, and
bounded archive/identity inspections in `validation-inspection.txt`. The
cycle's execution evidence directory is
`.codex/test-artifacts/163-ox91-pack-resource-validation/`; the closeout is
`.codex/history/163-ox91-pack-resource-validation.md` and the archived plan
is `.codex/history/plans/163-ox91-pack-resource-validation.md`.

Do not run the full unit suite, Android build or lint, instrumentation,
system/environment matrix, device tests, or visual acceptance. These are
outside OX-91's authorized slice verification.

## Risks and compatibility

The central-directory mode check is the main implementation risk because the
standard Android ZIP entry API hides Unix file-type attributes. Keep parsing
within the supported classic single-disk subset and the inclusive 64-entry
and 65,536-byte encoded-record limits. Preflight from the end record before
opening `ZipFile` or creating per-entry objects; then cross-check parsed
counts, bytes, names, and Unix modes against JDK enumeration. Reject
unsupported structures, test symlink metadata, both exact/over inventory
boundaries, and member-read failure. The resource validator does not extract
package contents, so no archive path is materialized on the filesystem.
OX-57 retains its later extraction budgets and atomic installation; OX-55
retains decoded renderer memory and activation. The 1.5 MiB resource caps are
based on the two committed OX-53 PNGs and do not establish a general archive
or transport limit.

## REVIEW — Final plan

Full plan review passed on 2026-11-20. The roster matches the sole OX-91
roadmap item, its four completed dependencies, objective, and observable exit.
The approved four-file behavior board package still matches its recorded
fingerprints; UI artwork remains inapplicable. The product owner's selected
PNG predicate is bounded to signature, chunk structure/order, legal IHDR,
chunk CRCs, required chunks, and no trailing bytes; IDAT decompression and
pixel decoding remain outside OX-91. The plan preserves OX-26 decoder,
OX-54 identity, OX-55 renderer, and OX-57 extraction boundaries.

Focused OX-91 tests are authorized by the recorded owner sequencing decision.
The two builder artifacts and only the focused validator test class are the
planned execution checks; system, device, visual, full-suite, Android build,
and lint checks remain excluded. No implementation or execution evidence is
claimed at plan finalization. No open owner questions remain.

## EXECUTE — OX-91

Implemented the read-only data-layer validator and focused validator tests.
The validator uses OX-26 for the manifest-v2 contract, checks the bounded ZIP
inventory before opening `ZipFile`, validates exact members, resource bytes,
PNG container structure and CRCs, OX-51 asset digest, and OX-54 identity, and
returns either a complete accepted value or a bounded rejection. Added the
resource-validation contract and linked it from the manifest-v2,
content-identity, and renderer-budget authorities. No schema, builder,
decoder, UI, extraction, or activation changes were made.

The baseline and olive builder commands both exited 0. The generated assets
were 1,259,884 and 1,386,266 bytes; both byte-matched their source assets and
manifest digests. The Kotlin validator test class passed all 13 tests with no
failures or skips. The independent bounded inspection matched both builder
content identities. Exact output and the command results are preserved in the
cycle evidence README and its referenced files. OX-91's execution exit is met;
the PNG check does not inflate IDAT or establish compressed image-stream
validity, and broader verification remains outside the authorization.

## CLOSEOUT — OX-91

Reviewed the full final diff and confirmed the implementation, focused tests,
validation contract, budget evidence, manifest-v2/content-identity links, and
cycle evidence remain within the approved slice. `python scripts/dev.py workflow`
passed at ACTIVE/CLOSEOUT with OX-91 recorded DONE and no active
slice. `git diff --check` exited 0 with no output. Cycle 163 closes with the
OX-91 exit met. The focused test class and the two builder/identity
inspections are the complete authorized verification; the full test suite,
Android build, lint, installation, and visual/system/device acceptance were
not run. IDAT decompression/pixel decoding, OX-57 extraction, and OX-55
renderer-memory/activation behavior remain outside this slice.
