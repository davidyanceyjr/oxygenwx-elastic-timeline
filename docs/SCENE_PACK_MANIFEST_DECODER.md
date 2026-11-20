# Scene-pack manifest decoder

OX-26 decodes one supplied `manifest.json` stream into a typed manifest-v2
value or a bounded rejection. The decoder treats the JSON as declarative data;
it does not execute pack-supplied behavior, read package resources, or change
app state.

## Accepted manifest

The decoder accepts the closed seven-field manifest-v2 envelope for the
currently evidenced First City declaration: `manifestVersion: 2`,
`worldDefinitionRef: first-city`, `engineCompatibility: 1`,
`rendererPolicyVersion: 1`, and required capability
`first-city.canopy.image-png`. It validates exact JSON types and object
members, the OX-20 `packId` grammar, the one-item capability and asset arrays,
and the asset-reference string fields. The declared asset path and SHA-256
remain strings in the typed value for the later resource-validation handoff.

The decoder checks the canonical slash-separated relative path syntax defined
by [Scene-pack asset references](SCENE_PACK_ASSET_REFERENCES.md). It rejects
empty paths, absolute, drive-form or URI-form paths, backslashes, empty path
segments, and `.` or `..` segments. It preserves an accepted path verbatim and
does not normalize it. This check validates the manifest field value; it does
not resolve the path against a package.

## Finite decode limits

The decoder reads at most 16 KiB from the caller-supplied stream, then parses
with a maximum JSON nesting depth of 4 and a maximum of 128 parser tokens.
The baseline manifest is 487 bytes, with nesting depth 3 and 29 tokens; the
limits leave bounded room above that existing declaration. The decoder does
not close the supplied stream. A stream-read failure discards all partial
bytes and returns `INPUT_READ_FAILURE`.

## Rejection categories

| Category | Conditions |
| --- | --- |
| `MALFORMED_JSON` | Invalid JSON syntax, duplicate member names, or trailing JSON content. |
| `INVALID_MANIFEST` | Wrong root or field shape/type, missing/null/extra members, invalid identity or asset-reference values, or noncanonical path syntax. |
| `UNSUPPORTED_VERSION` | Unsupported `manifestVersion`, `engineCompatibility`, or `rendererPolicyVersion`. |
| `UNSUPPORTED_CAPABILITY` | Unknown or unsupported required capability ID in the valid one-item field shape. |
| `LIMIT_EXCEEDED` | Manifest exceeds 16 KiB or Jackson reports a nesting-depth or token-count limit. |
| `INPUT_READ_FAILURE` | Reading the caller-supplied stream fails before the bounded input is complete. |

The decoder returns no partial manifest on rejection.

## OX-91 handoff

OX-26 does not inspect ZIP member inventory or asset bytes. It does not resolve
the accepted path, enforce pack-root confinement, check symlink entries or
resource presence/media validity, compare per-file digests, or compute the
OX-54 content identity. OX-91 owns those filesystem- and resource-dependent
checks before any scene activation. The logical `packId`, per-file SHA-256,
payload content identity, renderer capability, trusted origin, and entitlement
remain separate values.
