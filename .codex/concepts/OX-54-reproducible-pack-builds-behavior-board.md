# OX-54 behavior / contract board

**Proposal for owner review.** The adjacent Graphviz `.dot` file is the editable source; `.svg` and `.png` are review renders. Approval is pending. This board governs the OX-54 proof and contract only; it does not authorize implementation.

## Proposed contract

Define the canonical content identity as a SHA-256 digest over the declared uncompressed payload set, independent of ZIP representation:

1. Start with the domain/version prefix `oxygen-scene-pack-content-v1\0` and a 32-bit big-endian entry count.
2. Include each payload member in ascending UTF-8 byte order of its canonical slash-separated relative path.
3. For each member, append a 32-bit big-endian path-byte length, the UTF-8 path bytes, a 64-bit big-endian payload-byte length, and the exact payload bytes.
4. Encode the result as lowercase `sha256:<64 hex characters>`.

For the OX-30 v2 example, the member set is `manifest.json` plus its one declared PNG. Include the exact manifest bytes and resource bytes. This means a source-byte change, member path change, or manifest-byte change produces a different content identity. ZIP headers, member ordering, timestamps, compression settings, entry modes, and other archive metadata do not enter this digest.

## Acceptance flow proposed for OX-54

- Build the same committed First City baseline source twice with identical source bytes, manifest version, and builder contract. Both outputs must report the same canonical content identity.
- Build the OX-53 First City olive source as the distinct variant. It shares the existing First City world and supported `first-city.canopy.image-png` capability but has its own pack ID, reference, path, and PNG bytes. Its canonical content identity must differ from the baseline identity.
- Record any ZIP-byte SHA-256 only as a separate digest of the exact archive/transport bytes. Archive-byte equality is not the content-identity acceptance rule; the treatment of archive metadata is explicit above.
- Document the same canonical rule for future validation and installation consumers. OX-54 may expose the derived digest through builder output/documentation, but does not add a field to the closed OX-53 manifest or implement those future consumers.

## Signal boundaries

| Signal | Meaning carried forward |
| --- | --- |
| OX-20 `packId` | Stable logical pack identity across revisions. |
| OX-51 asset `sha256` | Integrity of one referenced file's exact bytes. |
| OX-54 canonical payload digest | Identity of the complete declared content payload under the proposed canonical framing. |
| ZIP-byte SHA-256 | Integrity of one exact archive/transport representation, if recorded; not the canonical payload identity. |

None of these signals establishes trusted origin, entitlement, or renderer support.

## Source trace and limits

| Source | Established fact | OX-54 constraint |
| --- | --- | --- |
| `docs/ROADMAP.md` OX-54 | Repeat identical inputs, define one canonical content-identity rule, explicitly distinguish payload and archive/transport digests, and hand the rule to builder/validator/installer. | The board tests repeatability and one distinct variant with one shared rule. |
| OX-30 plan/history and `docs/SCENE_PACK_BUILDER.md` | Local OX-30 emits a data-only ZIP preserving the exact v2 manifest and PNG bytes; OX-30 left whole-pack identity and reproducibility open. | Reuse the existing builder boundary; add no runtime loading or app behavior. |
| `docs/SCENE_PACK_MANIFEST_V1.md` and OX-53 examples | `packId` is logical identity; baseline and olive are separate manifest-v2 First City examples with the same required image capability. | Preserve manifest bytes and meanings; do not rename `packId` as a digest. |
| `docs/SCENE_PACK_ASSET_REFERENCES.md` | Asset SHA-256 covers exact referenced-file bytes and remains separate from pack/archive identity. | Keep per-asset integrity distinct from whole-payload identity and archive-byte integrity. |
| `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` (OX-52) | Identity, integrity, trusted origin, entitlement, and renderer compatibility are separate signals. | Do not infer trust or runtime acceptance from any digest. |

UI artwork does not apply because this slice changes only local build and contract behavior. Focused slice-specific tests are authorized. System and device tests are out of scope. No decoder, resource validator, installer, entitlement check, trusted-origin policy, runtime activation, or visual acceptance is claimed.
