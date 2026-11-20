# Scene-pack canonical content identity

OX-54 defines one canonical content identity for the declared, uncompressed
payload of a scene pack. The builder derives it from validated source bytes
before ZIP creation. The builder reports it in the `contentIdentity` field as
`sha256:` followed by 64 lowercase hexadecimal characters.

## Canonical payload digest

The input is a member map keyed by canonical, slash-separated relative path.
It contains the exact `manifest.json` bytes and the exact bytes of every
resource declared by that manifest. Do not parse and reserialize JSON or
normalize resource bytes before hashing.

Calculate SHA-256 over this framing, in order:

1. The domain/version prefix `oxygen-scene-pack-content-v1` followed by one
   NUL byte.
2. The member count as an unsigned 32-bit big-endian integer.
3. Every member, sorted by the UTF-8 byte sequence of its path:
   - path byte length as an unsigned 32-bit big-endian integer;
   - exact UTF-8 path bytes;
   - payload byte length as an unsigned 64-bit big-endian integer;
   - exact payload bytes.

Prefix the lowercase hexadecimal SHA-256 result with `sha256:`. The resulting
value identifies the complete declared payload byte set under this framing.
For current First City examples, the set is the root manifest and its one
declared PNG.

## Archive and other signals

ZIP headers, member order, timestamps, compression settings, entry modes, and
other container metadata are excluded. An optional SHA-256 over exact ZIP
bytes is a separate archive/transport-byte integrity value; it is not the
canonical content identity.

| Signal | Meaning |
| --- | --- |
| OX-20 `packId` | Stable logical pack identity across revisions. |
| OX-51 asset `sha256` | Integrity of one referenced file's exact bytes. |
| OX-54 content identity | Identity of the complete declared payload under the framing above. |
| ZIP-byte SHA-256 | Integrity of one exact archive or transport representation, if recorded. |

These values do not establish trusted origin, entitlement, or renderer
support. The digest is derived output; it does not add a field to the closed
manifest-v2 contract.

## Reader and installer handoff

Future readers and installers that consume OX-54 content identity must apply
the same domain/version prefix, path ordering, length framing, and exact-byte
rules to the declared payload member map. They must keep this value distinct
from logical `packId`, per-asset integrity, and any archive-byte digest.
Runtime decoding, resource validation, and installation remain owned by their
separate roadmap slices. OX-91's resource-validation contract describes the
runtime identity handoff in [Scene-pack resource validation](SCENE_PACK_RESOURCE_VALIDATION.md).
