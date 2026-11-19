# Scene-pack asset references and content integrity

This contract describes asset-reference data for a scene pack. It does not
change the closed scene-pack manifest v1 identity envelope. OX-51 defines the
reference separately; OX-53 owns integrating it into a later asset-bearing
manifest version.

## Asset-reference fields

An asset reference defines these fields:

| Field | Contract |
| --- | --- |
| `refId` | A stable, non-empty identifier for this reference. |
| `path` | A canonical slash-separated relative path from the pack root to the asset file. |
| `mediaType` | The exact declared type. This example supports `image/png`; unsupported types reject. |
| `sha256` | The lowercase 64-character hexadecimal SHA-256 digest of the referenced file's exact bytes. |

The digest is required. A missing digest, a malformed digest, or a digest that
does not match the file bytes rejects the reference. A matching digest
establishes byte integrity only; it does not establish trusted origin or
entitlement.

## Path rules

Paths use canonical slash-separated relative syntax. Reject:

- Absolute paths, drive-form paths, and URI-form paths.
- Backslashes, empty path segments, `.` segments, and `..` segments.
- Missing asset files and any resolved path outside the pack root.
- A path containing a symlink entry, even when its target stays inside the
  pack root.

Resolve an accepted path from the pack root and keep the resolved file inside
that root. Do not normalize a non-canonical path into an accepted spelling.

## Example and enforcement boundaries

`scene-pack-asset-reference-examples.json` contains one accepted reference to
the First City CANOPY OLIVE PNG from OX-24 and labeled rejection examples for
an absolute path, an escaping path, an unsupported type, a missing file, a
missing digest, and a mismatched digest. The sample's SHA-256 is calculated
from the exact copied PNG bytes.

OX-91 owns path and per-resource size enforcement before activation. OX-52
owns capability and trust policy, including budget categories and their
cross-layer enforcement. OX-51 sets no path-length, resource-size, or
aggregate-budget thresholds.

For the broader resource-class, renderer-capability, trust, and finite-budget
policy, see [Scene-pack renderer compatibility and data-only trust policy](SCENE_PACK_RENDERER_COMPATIBILITY.md).

This is a declarative contract. It does not implement pack loading, building,
decoding, runtime validation, resource activation, rendering, or installation.
Asset integrity remains separate from pack identity, archive identity, trusted
origin, and entitlement.
