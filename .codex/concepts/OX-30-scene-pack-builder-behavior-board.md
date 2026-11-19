# OX-30 behavior board companion

**Proposal for owner review.** The diagram's editable source is the adjacent `.dot` file; SVG and PNG are its review renders. No board approval or implementation is claimed.

## Proposed build contract

- Provide one documented developer-facing local invocation using the committed OX-53 First City baseline example root. The output is one self-contained, non-executable artifact containing `manifest.json` at its root and the declared PNG at its canonical relative path. The builder may accept the olive example under the same contract, but the slice exit requires only one artifact.
- Preserve the exact OX-53 manifest bytes and referenced asset bytes in the artifact. Validate the source before reporting success: the closed v2 envelope, required capability and renderer policy fields, canonical pack-root-relative asset path, `image/png`, and exact-byte SHA-256. Reject missing, mismatched, unsupported, undeclared, or executable content with a readable result. The builder does not claim runtime acceptance.
- Use a documented non-executable container format and output path chosen during implementation. Treat the artifact as a build output, not as an installed or trusted pack. OX-54 owns canonical content identity and reproducibility; this board does not define digest semantics for the whole pack or archive metadata.
- Keep weather truth, world selection, app runtime, and the bundled free scene unchanged. OX-26, OX-91, OX-57, and OX-55 own typed decode, runtime resource validation, import, and activation respectively. No downloader, storefront, entitlement, Park content, or app UI belongs to this slice.

## Source trace and acceptance

| Source | Board constraint |
| --- | --- |
| `docs/ROADMAP.md` OX-30 | One local build operation, one versioned artifact with expected manifest and asset, readable validation failures. |
| OX-53 and `docs/SCENE_PACK_MANIFEST_V2.md` | Use the committed First City example and preserve its seven-field v2 contract. |
| `docs/SCENE_PACK_ASSET_REFERENCES.md` | Canonical relative path, supported PNG type, and exact-byte SHA-256. |
| `docs/SCENE_PACK_RENDERER_COMPATIBILITY.md` | Declarative payload only; version and capability declarations do not establish trust or runtime support. |
| User direction, 2026-11-19 | Focused slice-specific tests authorized. System and device tests excluded. |

The board is accepted when the owner approves the source and both renders. UI artwork does not apply because this local build command changes no visible app output.
