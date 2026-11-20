# OX-26 behavior / contract board proposal

## Established dependency facts

- OX-30 produced the data-only ZIP at
  `.codex/test-artifacts/160-ox30-scene-pack-builder/first-city-canopy-baseline-v2.zip`.
  It contains the exact `manifest.json` bytes and declared PNG from the First
  City baseline example; the source manifest is version 2.
- OX-20 defines pack identity and the separate `engineCompatibility` marker.
  OX-53 defines the closed v2 envelope with exactly seven top-level fields and
  one asset-reference object containing four string fields. Duplicate JSON
  object member names are invalid; missing, null, extra, or coerced values are
  not accepted.
- Renderer policy v1 supports required capability
  `first-city.canopy.image-png`. Unsupported policy versions and unknown or
  unsupported required capability IDs reject the candidate.
- OX-52 assigns OX-26 finite bounds on manifest input size and structural work,
  including nesting and member counts, before returning a typed result. The
  authority assigns ownership but does not prescribe numeric thresholds.
- OX-54 defines canonical content identity. OX-91 owns runtime asset/resource
  inspection, path enforcement, digest/content-identity validation, and
  resource-budget enforcement before scene activation.

## Proposed OX-26 behavior

1. Read the root `manifest.json` bytes from the real OX-30 artifact, within
   finite input and structural limits. Do not extract the archive or read
   declared asset bytes.
2. Parse JSON without executing data and reject malformed JSON or duplicate
   member names at any object depth.
3. Validate the closed OX-53 manifest v2 envelope, exact JSON types, pack
   identity syntax, the three independent version values, and the supported
   required capability. The currently evidenced accepted values are
   `manifestVersion: 2`, `engineCompatibility: 1`,
   `rendererPolicyVersion: 1`, `worldDefinitionRef: first-city`, and required
   capability `first-city.canopy.image-png`.
4. Return either one typed decoded result containing the validated manifest
   declaration or one bounded rejection. Never return a partially accepted
   manifest.
5. Leave declared resource resolution and validation to OX-91. OX-26 does not
   resolve `assetReferences.path`, read an asset, compare its SHA-256, compute
   the OX-54 payload identity, extract files, or activate a scene.

The draft plan must select and justify finite numeric decode limits using the
real manifest and structural contract as evidence. No numeric threshold is
proposed by this board because OX-52 does not set one.

## Board applicability

UI artwork is not applicable: this slice produces a typed decode result and
does not change visible output. Focused slice-specific tests are authorized;
system and device tests are out of scope.
