# OX-53 behavior / contract board

**Scope:** Finalize two evidence-based, asset-bearing scene-pack manifest examples.
**Cycle:** 159-ox53-example-pack-manifests.
**Board source:** `OX-53-example-pack-manifests-behavior-board.dot` (rendered to SVG and PNG).

## Outcome

Provide two complete, minimal manifest examples for the existing First City
definition. The examples use the evidence-backed baseline and olive CANOPY PNG
asset choices. This is the roadmap's permitted asset-variant route. This pair
excludes Open Landscape: OX-92 proves its left-tree renderer path but does not
establish a corresponding image asset or asset substitution.

Each example preserves OX-20's four identity fields, sets
`manifestVersion` to `2`, and keeps `engineCompatibility` at `1`. The closed
v2 envelope adds:

- `rendererPolicyVersion: 1`, separate from `manifestVersion` and
  `engineCompatibility`;
- `requiredCapabilities`, with one public capability identifier tied to the
  existing First City CANOPY PNG path; and
- `assetReferences`, using OX-51's `refId`, canonical relative `path`,
  `mediaType: image/png`, and lowercase SHA-256 of the exact asset bytes.

Capability ID: `first-city.canopy.image-png`. It describes only the required
supported image-asset path. Internal Kotlin enum, class, and method names are
not public IDs. The board does not add optional capabilities or fallback
behavior.

The two records differ by `packId`, asset `refId`, relative asset path, and
byte digest. The paths inside each pack are
`assets/first-city/canopy/baseline.png` and
`assets/first-city/canopy/olive.png`. The digest is computed from each copied
file's exact bytes.

## Flow and boundaries

1. Treat OX-20 manifest v1 as immutable and closed. The v2 examples retain its
   identity fields and do not reinterpret `engineCompatibility` as a renderer
   version.
2. Carry OX-52's renderer-policy version separately. Include only a required
   capability whose support can be traced to the existing renderer path.
   Unknown or unsupported required capabilities reject; no optional behavior
   is included.
3. Carry asset data using OX-51's canonical relative path, allowed media type,
   and exact-byte digest rules. Keep byte integrity distinct from origin,
   entitlement, renderer compatibility, and pack identity.
4. Keep manifests declarative. Do not add weather interpretation, executable
   payloads, Park content, font resources, arbitrary scene authoring, or
   optional features.
5. Use the examples as source material for a later pack-builder proof. This
   slice does not implement a builder, decoder, resource validator, installer,
   runtime loading, or app behavior. Do not set numeric resource limits here.

## Source-to-scope trace

| Source | Established fact | OX-53 scope |
| --- | --- | --- |
| `docs/SCENE_PACK_MANIFEST_V1.md` | The v1 envelope has exactly four identity fields; current world refs are `first-city` and `minimal-open-landscape`. | Preserve those four meanings and use a new manifest version for added fields. |
| `docs/SCENE_PACK_ASSET_REFERENCES.md` | Asset refs require stable `refId`, canonical pack-root-relative path, registered media type, and exact-byte SHA-256. | Add one `image/png` reference to each example. |
| `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` | Renderer policy version is distinct; OX-53 chooses public capability IDs only when existing renderer support is evidenced. | Use one required First City CANOPY PNG capability; do not expose Kotlin names. |
| `.codex/test-artifacts/152-ox24-scene-asset-swap/README.md` | First City uses baseline and olive 1024×1536 PNGs on the same CANOPY draw path; both asset choices were tested. | Use baseline and olive as the pair of real asset variants. |
| `.codex/test-artifacts/153-ox92-second-world-adapter/README.md` | Open Landscape has a shared vegetation declaration for a tree group; the scoped proof adds no bitmap asset. | Do not claim an Open Landscape asset example without additional evidence. |
| `.codex/test-artifacts/158-ox52-renderer-capability-trust/README.md` | Test fixtures use synthetic capability IDs and are not public API or OX-53 examples. | Do not reuse those fixture IDs. |

## UI and verification scope

UI artwork does not apply: this is contract/example data and changes no visible
app output. Focused slice-specific tests are authorized; system and device
tests are out of scope.
