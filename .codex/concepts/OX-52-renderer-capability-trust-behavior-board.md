# OX-52 behavior board companion

**Status:** Proposal for product-owner review. This package does not record approval or authorize implementation.

## Scope

Define the compatibility and trust policy for declarative scene packs: version boundaries, capability acceptance/rejection, allowed payload classes, separation of integrity/origin/entitlement, and finite budget ownership. This is a contract slice with no UI change. It does not implement a decoder, validator, importer, renderer, trust provider, or entitlement system.

## Established dependency facts

- OX-20 defines a closed manifest v1 envelope with `packId`, `manifestVersion`, `worldDefinitionRef`, and `engineCompatibility`. Its `engineCompatibility: 1` identifies the current pack identity contract; it is not a renderer capability list. Additional fields require a new manifest version.
- OX-51 defines canonical pack-root-relative asset references and exact-byte SHA-256. A matching digest establishes content integrity only, not trusted origin or entitlement. Its real example needs `image/png`; no font is required. OX-91 owns path and per-resource-size enforcement; OX-52 owns budget categories and cross-layer boundaries.
- OX-36 keeps weather interpretation in the app's typed decision path. Pack capability handling must not add weather interpretation or change supplied, carried, or unavailable meaning.
- Current source has an internal `VEGETATION` role in the First City canopy and Open Landscape tree-group declarations. That internal enum is evidence of a narrow shared role, not an already approved public pack capability ID.

## Proposed compatibility and payload policy

| Area | Proposed rule | Review status |
| --- | --- | --- |
| Manifest schema | Keep OX-20 v1 closed. Any capability declaration belongs in a separately approved later manifest version; v1 readers reject extra fields. | Preserves OX-20. |
| Renderer policy version | Version the renderer capability registry separately from manifest field/schema version and from OX-20's `engineCompatibility`. A later manifest may name the registry version. | Proposed separation; field spelling is open. |
| Known required capability | Accept only when the declared registry version is supported and every required ID is in that registry and supported by the renderer. | Fail closed on unsupported requirements. |
| Unknown required capability | Reject the pack before activation. | Required by OX-52. |
| Known optional capability | Omit it or apply only the fallback/default explicitly assigned to that ID in the approved registry. | No generic silent fallback. |
| Unknown optional capability | Reject when the registry has no explicit policy for the ID. | Conservative proposal for owner review. |
| Payload classes | Accept only schema-defined declarative metadata and explicitly registered asset media types. The evidence-backed asset set starts with `image/png`; no font type is established by the current real variant. | Unknown media types reject. |
| Executable content | Reject scripts, bytecode, native libraries, callbacks, class/method references, or other payloads that ask the app to execute pack-supplied behavior. | Always data-only; no plugin or code-loading path. |

The initial capability registry should include only capabilities that a bounded example can trace to an existing renderer path. Proposed candidate: a stable pack-facing vegetation capability, with its ID and exact supported behavior confirmed against OX-53's examples. Do not expose Kotlin enum, class, or method names as pack API. No weather capability may override OX-36's app-owned weather decisions.

## Trust and entitlement distinctions

| Signal | Establishes | Does not establish |
| --- | --- | --- |
| `packId` | Stable logical pack identity. | Content integrity, sender, renderer compatibility, or entitlement. |
| OX-51 SHA-256 | Referenced bytes match their declared digest. | Trusted origin, safety of executable code, or entitlement. |
| Trusted origin | A separate verification/provenance result from an approved source policy. | Compatibility or entitlement by itself. |
| Entitlement | A separate product/provider decision owned by later slices. | Content integrity or renderer support. |

No manifest field or digest may self-assert origin trust or entitlement. OX-52 defines these as separate dimensions; later channel slices decide provider and entitlement behavior. The bundled free scene remains available without an entitlement gate.

## Proposed finite-budget ownership

OX-52 assigns categories and enforcement boundaries, not numeric thresholds. Every enforcement point uses a finite limit; owners set values from runtime evidence in their own bounded work.

| Budget | Proposed enforcement point | Required outcome on excess |
| --- | --- | --- |
| Manifest decode size/structure (input bytes, nesting, and member counts) | OX-26, while decoding to a typed result. | Bounded decode rejection; no executable interpretation. |
| Referenced resources (path confinement, allowed media, per-resource and aggregate resource limits) | OX-91, before activation. | Reject the whole candidate; no partial asset set. |
| Package extraction (entry count, expanded bytes, and staging storage/working memory) | OX-57, during staged import and before commit. | Abort the staged import; preserve any prior valid install. |
| Renderer load memory (decoded asset dimensions/working set and candidate scene allocations) | OX-55, while preparing a candidate and before activation. OX-91 validates declared resource limits before handoff. | Reject the candidate before scene activation; keep the bundled free scene available. |

No numeric limits are proposed here. Container encoding, transport, download, payment, public authoring, failure UI, and runtime recovery flows remain outside OX-52.

## Owner review decisions

1. Approve separate manifest-schema and renderer-capability-registry versioning; leave the exact manifest field name to OX-53 unless the owner wants it fixed here.
2. Approve fail-closed behavior for unknown optional IDs unless that ID has a registry-defined omit/default policy.
3. Approve the data-only payload boundary, PNG-only evidence-backed asset set, rejection of executable content, and no implied font support.
4. Approve the trust/entitlement separation and the proposed decode/resource/extraction/renderer-memory ownership handoffs.
5. Confirm that OX-53 will select only capability IDs demonstrated by its two evidence-based examples; this board does not treat the current internal `VEGETATION` enum as a public ID.

UI artwork is not applicable because this contract slice changes no visible app output. Focused slice-specific tests are authorized; system and device tests are out of scope. This planning package runs no tests and claims no implementation or installed acceptance.
