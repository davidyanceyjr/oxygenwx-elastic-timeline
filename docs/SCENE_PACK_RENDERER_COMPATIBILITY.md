# Scene-pack renderer compatibility and data-only trust policy

This document defines the design-time policy for renderer capability
compatibility, accepted scene-pack resource classes, trust distinctions, and
finite import/load budgets. It records the approved OX-57 installation limits
and the OX-55 renderer-load limit. It does not itself implement runtime
decoding, resource validation, package extraction, renderer loading, origin
verification, or entitlement.

## Version boundaries

The three version concepts have separate meanings and acceptance rules:

| Version concept | Meaning | OX-52 policy |
| --- | --- | --- |
| `manifestVersion` | Version of the manifest envelope and its field rules. | OX-20 manifest v1 remains the closed four-field identity envelope. A v1 reader rejects extra members. Capability declarations and asset references belong only in a later manifest version. |
| `engineCompatibility` | OX-20 scene-pack identity-contract compatibility marker. | Value `1` continues to mean the OX-20 identity contract. It is not a renderer version and does not list supported capabilities. |
| Renderer capability-policy version | Version of the registry that names pack-facing capability IDs, renderer support, and any exact optional-capability behavior. | It is versioned separately from both manifest schema and `engineCompatibility`. An unsupported policy version rejects the candidate. A change to a registry's IDs, support meaning, or optional behavior requires a separately identified policy version. |

OX-52 defines the registry rules and the initial policy version. OX-53 defines
`rendererPolicyVersion` as the manifest field and selects public capability
IDs only when their support is evidenced by an existing renderer path. The
internal Kotlin `VEGETATION` role is a candidate source fact, not a
pack-facing capability ID. Internal enum, class, and method names are not pack
API. Until an ID is explicitly present in the selected registry and supported
by a renderer path, a pack cannot require it successfully.

## Renderer policy version 1 registry

Registry version 1 contains exactly one required public capability and no
optional entries:

| Capability ID | Disposition | Evidence and support boundary |
| --- | --- | --- |
| `first-city.canopy.image-png` | Required and supported for the OX-53 First City examples. | OX-24 records baseline and olive `image/png` assets as alternate choices on the same existing First City CANOPY draw path. This registry entry describes that authored path at design time; it does not claim arbitrary pack files can be loaded at runtime. |

The OX-53 examples declare `rendererPolicyVersion: 1` and require this
capability for their single First City canopy image reference. The capability
ID is public contract data; it is not derived from an internal Kotlin enum,
class, or method name.

Future registries may add an optional ID only with an explicit behavior for
that ID: either omit it or use its exactly specified fallback/default. Each
registry version is a snapshot; changing an ID's support or optional behavior
requires a new policy version. The test-only examples use synthetic registry
entries to exercise generic policy branches. They do not add IDs or optional
entries to the public registry.

## Capability disposition

| Declaration | Required disposition |
| --- | --- |
| Supported policy version and a required ID explicitly listed and supported by the selected renderer | Continue to the remaining validation gates. |
| Unsupported renderer capability-policy version | Reject the whole candidate. Do not reinterpret it using another registry. |
| Unknown or renderer-unsupported required ID | Reject the whole candidate before activation. |
| Known optional ID with an explicit `omit` policy | Omit that capability's effect and continue. |
| Known optional ID with an explicit default | Apply only the exact default recorded for that ID in the selected registry, then continue. |
| Optional ID without an explicit registry behavior, including an unknown optional ID with no policy | Reject the whole candidate. There is no generic silent fallback. |

An ID has an explicit optional policy only when the selected registry names
that ID and its precise omit/default behavior. A required capability cannot be
silently omitted or defaulted. OX-53 selects public IDs only when its examples
can trace them to existing supported renderer behavior. Capability policy
cannot reinterpret weather or override OX-36's app-owned `SceneWeatherDecision`.

## Accepted data and resource classes

Accept only:

- Declarative metadata defined by the applicable manifest or scene-pack
  contract version.
- Asset media types explicitly registered by the applicable contract. The
  evidence-backed initial asset type is `image/png`, as used by the OX-51
  example.

The current evidence establishes no font resource type. Unknown or
unregistered media types reject. These rules classify contract data; OX-91
owns runtime path confinement, media validation, and resource-size enforcement.

Reject pack-supplied behavior, including scripts, bytecode, native libraries,
callbacks, class or method references, or any other payload that asks the app
to execute pack-supplied instructions. A capability ID describes declarative
renderer support; it cannot supply executable behavior or change forecast
facts. Container encoding and transport are outside this policy.

## Identity, integrity, origin, and entitlement

| Signal | Establishes | Does not establish |
| --- | --- | --- |
| OX-20 `packId` | Stable logical pack identity. | Byte integrity, sender/origin, renderer compatibility, or entitlement. |
| OX-51 SHA-256 | Integrity of the referenced exact bytes when the digest matches. | Trusted origin, executable safety, renderer support, or entitlement. |
| Trusted origin | A separate provenance result under a future approved source policy. | Renderer compatibility or entitlement by itself. |
| Entitlement | A separate product/provider decision owned by later work. | Byte integrity, trusted origin, or renderer support. |

No identity, digest, capability declaration, or pack-supplied field may assert
another trust dimension. OX-52 does not choose a trusted-origin provider or an
entitlement policy. The bundled free scene remains available without an
entitlement gate, regardless of pack identity, integrity, origin, or
capability outcomes.

## Finite budget ownership

Every enforcing boundary uses finite limits. OX-52 defines the categories,
owners, and rejection outcomes, but sets no numeric thresholds; each owner
selects values from evidence in its bounded implementation slice.

| Budget category | Enforcement owner and boundary | Outcome when exceeded |
| --- | --- | --- |
| Manifest input size and structural work, including nesting and member counts | OX-26 bounds input and structure while decoding, before returning a typed result. Its current decoder uses a 16 KiB byte cap, nesting depth 4, and 128 parser tokens, based on the 487-byte, depth-3, 29-token First City baseline manifest. | Reject the candidate at decode; do not return a partially accepted manifest. |
| Referenced resources, including pack-root confinement, allowed media, per-resource size, and aggregate resource limits | OX-91 accepts at most 1,572,864 bytes (1.5 MiB) of actual uncompressed bytes per resource and 1,572,864 bytes total, inclusive. The bound covers the largest current OX-53 example, the 1,386,266-byte olive PNG, with 186,598 bytes of headroom; the baseline PNG is 1,259,884 bytes. OX-91 also preflights at most 64 ZIP entries and 65,536 encoded central-directory record bytes before scanning records. See [scene-pack resource validation](SCENE_PACK_RESOURCE_VALIDATION.md). | Reject the whole candidate before activation; do not activate a partial asset set. |
| Package extraction, including entry count, expanded data, staged storage, and extraction working memory | OX-57 allows at most 64 materialized filesystem nodes per generation, 1,605,632 expanded-generation bytes, 1,638,400 candidate-plus-commit bytes, 3,244,032 same-pack replacement-peak bytes, and 1,622,016 package-specific working bytes. Its internal install record is capped at 32,768 bytes and each current or pending pointer at 16,384 bytes. | Abort the staged import before commit; keep any previously committed generation current and do not expose a partial candidate. |
| Renderer load memory, including decoded assets and candidate-scene allocations | OX-55 bounds candidate loading before activation; OX-91 validates actual resource bytes against its assigned limits before handoff. | Reject the candidate before scene activation; keep the bundled free scene available. |

### OX-55 validation-build renderer load

The First City olive proof uses an 8,388,608-byte (8 MiB) candidate-load cap.
After OX-91 accepts the complete archive, the renderer checks the validated
PNG header for the fixed 1024 × 1536 CANOPY dimensions. Before allocating
pixels, it uses checked wide arithmetic to admit only
`retained PNG bytes + (width × height × 4 ARGB_8888 bytes) + 524,288 reserve
bytes <= 8,388,608`. The retained PNG byte array, one decoded bitmap, and a
fixed allowance for decode and candidate preparation are counted. The
`ImageBitmap` view shares the bitmap's pixels; the disk-backed staging file is
outside this retained-load sum. A rejected validator result, unsupported
renderer requirement, failed dimension/budget check, or failed decode leaves
the committed World choice and visible scene intact.

The accepted olive PNG estimates 8,202,010 bytes under this formula. The
focused Pixel 3 XL measurement found 7,885,962 bytes of retained bytes,
bitmap, native working peak, and Java peak; its scope and raw outputs are in
[cycle 164 measurement evidence](../.codex/test-artifacts/164-ox55-pack-renderer/README.md).
This cap covers the OX-55 candidate load, not total process or GPU memory.

Passing one budget does not waive another owner's boundary. Exceeding any
limit rejects the whole candidate at the owning boundary; there is no partial
scene activation or package commit. OX-52 defines no container, transport,
download, or numeric budget policy.

OX-57's expanded-generation, commit-bookkeeping, replacement-peak, and working
data totals count different resources and do not combine with the OX-55 decode
budget. Their per-component accounting is documented in [scene-pack
installation](SCENE_PACK_INSTALLATION.md).

## Example and verification boundary

[`scene-pack-renderer-capability-policy-examples.json`](scene-pack-renderer-capability-policy-examples.json)
contains labeled policy cases. Its fixture envelope, keys, and synthetic
capability IDs are test-only inputs, not proposed manifest fields, public
capability IDs, or OX-53 manifest examples. The focused test checks that those
examples agree with this design-time policy. It does not exercise a production
decoder, resource validator, importer, renderer, trusted-origin provider, or
entitlement service.
