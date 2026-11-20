# Scene-pack manifest v2: asset-bearing example contract

This is the design-time contract for the two OX-53 First City manifest
examples. It describes identity, renderer capability requirements, and
pack-root-relative asset references. It does not load, install, render,
authorize, or interpret a pack.

## Closed v2 envelope

A v2 manifest is a JSON object with exactly the seven required members below.
Member names are case-sensitive. Repeated member names in any JSON object,
including nested objects, are invalid. Missing members, `null`, and additional
members are invalid. No implicit defaults or type coercions apply; JSON
booleans are not integers.

| Member | Type and OX-53 example value | Meaning |
| --- | --- | --- |
| `packId` | JSON string; a nonempty sequence of ASCII lowercase letters, digits, and internal `-` or `.`, beginning and ending with a letter or digit. | Stable opaque logical pack identity. It carries no display-name, weather, trust, or entitlement meaning. |
| `manifestVersion` | JSON integer `2` | Version of this closed envelope and its required-field rules. |
| `worldDefinitionRef` | JSON string `first-city` | Exact reference to the existing First City authored definition. Its identity semantics are inherited from [manifest v1](SCENE_PACK_MANIFEST_V1.md). |
| `engineCompatibility` | JSON integer `1` | OX-20 identity-contract compatibility marker. It is not a renderer version or capability list. |
| `rendererPolicyVersion` | JSON integer `1` | Version of the separately defined renderer capability registry. It is independent of `manifestVersion` and `engineCompatibility`. |
| `requiredCapabilities` | JSON array containing exactly one string: `first-city.canopy.image-png`. | Required public renderer support for the evidenced First City canopy PNG path. Unknown or unsupported required IDs reject; they are not silently omitted or defaulted. |
| `assetReferences` | JSON array containing exactly one object with the four string members listed below. | Reference to the example's real canopy PNG asset inside its own sample pack root. |

Each `assetReferences` object has exactly these members:

| Member | Type and OX-53 example value | Meaning |
| --- | --- | --- |
| `refId` | JSON string | Stable nonempty identifier for the asset reference. |
| `path` | JSON string; canonical relative path shown in the examples below. | Slash-separated path from the sample pack root to the asset file. |
| `mediaType` | JSON string `image/png` | The declared asset type supported by these examples. |
| `sha256` | JSON string; lowercase 64-character hexadecimal digest. | SHA-256 of the exact referenced file bytes. |

The one-item array shapes are the bounded OX-53 example contract. They do not
define a general authoring system or runtime parser. The pack IDs and asset
reference IDs are distinct between the two examples.

## Version and trust boundaries

`manifestVersion: 2` versions this envelope. `engineCompatibility: 1`
continues to identify the OX-20 identity contract. `rendererPolicyVersion: 1`
selects the independent registry described in
[renderer compatibility](SCENE_PACK_RENDERER_COMPATIBILITY.md). A change to
one version concept does not imply a change to either of the others.

`packId` establishes logical identity only. The `sha256` value establishes
exact-byte integrity only when it matches the referenced file. Neither value
establishes renderer compatibility, trusted origin, or entitlement. The
manifest contains declarative data only and cannot supply weather rules or
executable behavior.

## Asset paths and examples

The references use OX-51's canonical relative slash-separated paths, exact
`image/png` media type, and lowercase SHA-256 of the copied asset bytes. The
paths resolve from each example's own root and identify committed PNG copies
of the existing First City assets:

| Example root | Pack ID | Asset reference ID | Relative path | Source asset |
| --- | --- | --- | --- | --- |
| [`first-city-canopy-baseline`](scene-pack-examples/first-city-canopy-baseline/manifest.json) | `first-city-canopy-baseline` | `first-city-canopy-baseline` | `assets/first-city/canopy/baseline.png` | `app/src/main/res/drawable-nodpi/sg2_city_canopy.png` |
| [`first-city-canopy-olive`](scene-pack-examples/first-city-canopy-olive/manifest.json) | `first-city-canopy-olive` | `first-city-canopy-olive` | `assets/first-city/canopy/olive.png` | `app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png` |

Both manifests declare `worldDefinitionRef: first-city`,
`manifestVersion: 2`, `engineCompatibility: 1`, `rendererPolicyVersion: 1`,
and the one required capability listed above. Their asset paths and digests
are specific to their copied files.

The runtime field checks and OX-91 resource-validation handoff are documented
in [Scene-pack manifest decoder](SCENE_PACK_MANIFEST_DECODER.md). OX-26 checks
canonical relative path syntax while OX-91 resolves the path and enforces
pack-root and resource rules, as specified in
[Scene-pack resource validation](SCENE_PACK_RESOURCE_VALIDATION.md); this
does not change the v2 schema.

## Scope limit

These examples and this schema contract do not implement a pack builder,
decoder, resource validator, importer, installer, runtime renderer loader,
network delivery, trusted-origin provider, entitlement policy, or app
behavior. The separate runtime contracts describe the decoder and resource
validator. Numeric resource limits are owned by OX-91 and recorded in
[Scene-pack resource validation](SCENE_PACK_RESOURCE_VALIDATION.md). The
focused contract check verifies only the example documents and their local
asset bytes.
