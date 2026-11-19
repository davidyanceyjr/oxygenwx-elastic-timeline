# OX-51 behavior board companion

**Status:** Proposal for product-owner review. This board does not record approval or authorize implementation.

## Scope and source facts

OX-51 defines relative, pack-root-confined asset references and integrity dispositions for one real variant. The example uses the existing First City CANOPY OLIVE variant from OX-24: `app/src/main/res/drawable-nodpi/sg2_city_canopy_olive.png`, a 1024 × 1536 RGBA PNG. Its current SHA-256 is `60885b635567d5e4ec4d4b8d1cb6c86ed4629ec9f003d1ef134e72823869550d`.

OX-20 established `manifestVersion` 1 as a closed identity envelope. Adding asset members to that envelope would change its field rules, so this proposal keeps v1 closed and defines an asset-reference record for OX-53 to integrate under the necessary new manifest version. The content digest is per-file integrity data, separate from logical pack identity, archive identity, trusted origin, and entitlement.

## Proposed contract for review

The proposed record has a stable `refId`, a relative `path`, a declared `mediaType`, and a required per-file `sha256`. For the OX-24 example, the proposed fixture path is `assets/first-city/canopy/olive.png`, the only required media type is `image/png`, and no font resource is needed.

The proposed path syntax is canonical slash-separated relative paths. Reject absolute paths, drive or URI forms, backslashes, empty segments, `.` and `..` segments, missing resources, and any path that resolves outside the pack root. The symlink policy needs owner confirmation; the proposal rejects symlink entries. Numeric path-length limits are not set here.

The proposed `sha256` is the lowercase 64-character hexadecimal SHA-256 digest of the exact asset bytes. Missing and mismatched digests reject the example. Unknown media types reject. A matching digest establishes content integrity only; it does not establish trusted origin or entitlement.

The proposed enforcement boundary assigns path confinement and per-resource size-limit enforcement to OX-91 resource validation before activation. OX-52 defines budget categories and cross-layer enforcement. OX-51 defines no numeric thresholds; runtime evidence should guide them.

## Review decisions

Please approve or revise these proposals:

1. Keep OX-20's manifest v1 closed; OX-53 integrates the asset-reference record under a new manifest version.
2. Use the proposed record fields and canonical relative-path rules, including rejection of symlinks.
3. Use SHA-256 over exact file bytes; reject missing or mismatched digests.
4. Support PNG only for the OX-24 example; no font type is required by this variant.
5. Assign path and per-resource size enforcement to OX-91, with OX-52 defining budget categories and no numeric thresholds in OX-51.

UI artwork is not applicable because OX-51 defines a data contract and changes no visible app output. Focused slice-specific tests are authorized by the owner; this board does not run tests or claim acceptance evidence.
