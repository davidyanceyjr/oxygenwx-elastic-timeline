# Scene-pack resource validation

OX-91 validates the referenced resources of a current manifest-v2 scene-pack
ZIP before a later renderer handoff. It is read-only: it does not extract
members, write files, activate a scene, or change forecast or selected-time
state.

## Accepted result and rejection behavior

The validator passes the raw root `manifest.json` bytes to the OX-26 decoder.
On acceptance, it returns the typed manifest, the exact referenced resource
bytes keyed by their canonical relative paths, and the recomputed OX-54 content
identity. The identity uses the exact manifest and resource bytes; JSON is not
parsed and reserialized for hashing.

Any failed check rejects the whole candidate. A rejected result contains only
a bounded reason: archive rejected, manifest rejected, unsupported resource,
limit exceeded, digest mismatch, or read failure. It does not expose exception
text or return a partial asset set.

## Archive and member rules

The current OX-30 profile is classic, single-disk, non-ZIP64, unencrypted ZIP
with Unix creator metadata and stored entries. Unsupported compression,
encryption, ZIP64, multi-disk, malformed records, or unsupported member types
are rejected. The validator inspects central-directory Unix modes because
`ZipEntry` does not expose the file type, then cross-checks record names and
metadata against the JDK ZIP enumeration before reading members.

Before scanning central-directory records or opening `ZipFile`, OX-91 enforces
an inclusive cap of 64 entries and 65,536 encoded central-directory record
bytes. Both caps count directory entries. The byte count includes every
record's fixed header, filename, extra fields, and entry comment. The EOCD
record and archive comment are not included in that record-byte total.

The archive must contain one root `manifest.json` and exactly one file for the
manifest's declared asset path. Duplicate names, missing resources, symlink
members, undeclared file payloads, and entries outside the declared member
set are rejected. Empty directory entries are permitted only when their
canonical names are ancestors of the declared resource. OX-26 remains the
authority for manifest shape, capability policy, and canonical relative-path
syntax; OX-91 resolves the accepted path within the archive and rejects
manifest paths that do not resolve to the required member set.

The manifest is read with a 16 KiB bound. A referenced resource is read with
an inclusive cap of 1,572,864 bytes (1.5 MiB) per resource and 1,572,864 bytes
aggregate, measured against bytes actually read. Manifest and ZIP metadata do
not count toward these resource-byte limits. Manifest v2 currently declares
one asset, while the aggregate check remains an independent limit. These
limits are based on the current OX-53 examples: the baseline PNG is 1,259,884
bytes and the olive PNG is 1,386,266 bytes. The resource limit leaves 186,598
bytes above the larger sample. OX-57's later staged extraction limits and
OX-55's renderer-load memory limits remain separate.

## PNG container validation

For the registered `image/png` type, the validator checks the actual resource
bytes. It requires the PNG signature, checked chunk framing and four-letter
chunk types, and a valid 13-byte IHDR as the first chunk. IHDR dimensions must
be nonzero and within the PNG integer range; bit depth must be legal for the
color type; compression and filter methods must be zero; and interlace must be
zero or one.

At most one PLTE chunk may appear before IDAT. PLTE is required for indexed
color and disallowed for grayscale and grayscale-alpha color. The stream must
contain one or more consecutive IDAT chunks and end with one zero-length IEND
chunk. Every chunk CRC is checked, unknown critical chunks reject, and no bytes
may follow IEND. Ancillary chunks are accepted when their type, framing, and
CRC are valid; their payloads and ancillary-specific ordering rules are not
interpreted.

The validator does not inflate IDAT data or decode pixels. Therefore, this
container check does not establish that an otherwise self-consistent IDAT
payload is a valid compressed image stream. OX-55 owns renderer decoding and
load-memory limits. The PNG structure checks follow the [W3C Portable Network
Graphics Specification](https://www.w3.org/TR/png-3/).

## Integrity and identity

After bounded reading and PNG validation, OX-91 compares the exact resource
bytes with the manifest's lowercase SHA-256 asset digest defined by OX-51. It
then recomputes OX-54 content identity over the exact root manifest and
declared resource bytes using UTF-8 path-byte ordering and the specified
length framing. Per-file digest, content identity, `packId`, ZIP-byte digest,
trusted origin, and entitlement remain distinct signals.

## Ownership boundary

OX-91 validates resource paths, archive membership, media bytes, size limits,
asset digest, and content identity before handing off a complete validated
value. OX-57 owns package extraction and atomic installation. OX-55 owns PNG
decompression, pixel decoding, renderer instantiation, and scene activation.
This contract defines no general ZIP transport-size limit and no trusted
origin or entitlement policy.
