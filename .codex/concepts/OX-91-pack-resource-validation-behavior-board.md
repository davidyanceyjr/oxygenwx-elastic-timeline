# OX-91 behavior board companion

**Status:** Product-owner approved on 2026-11-20. Approval applies to this
four-file package as reviewed. The rendered board retains its contextual
reapproval wording; the approval state is recorded here and in cycle 163, so
no graphic update is required. Approval does not authorize implementation.

## Scope and outcome

Validate one generated manifest-v2 data-only pack against the approved
resource and renderer-capability policy before any scene activation. Return a
complete validated artifact and declared asset set only after every check
passes. Any failed check rejects the whole candidate and exposes no partial
asset state.

The roadmap exit is the acceptance boundary: accept a valid generated pack;
reject path escape, unsupported resources, oversized resources, and content
mismatches before activation. No visible output changes, so UI artwork does
not apply.

## Established dependency facts

- OX-26 accepts the closed manifest-v2 shape, checks canonical relative-path
  syntax, and returns a typed declaration. It does not open or resolve asset
  files.
- OX-51 requires pack-root confinement, rejects symlink paths, allows the
  evidenced `image/png` resource, and compares SHA-256 with exact resource
  bytes. It sets no numeric path or resource limits.
- OX-52 assigns OX-91 resource path, media, per-resource, and aggregate
  validation. Its current policy-v1 registry includes the required
  `first-city.canopy.image-png` capability. OX-55 owns decoded renderer-load
  memory; OX-57 owns package extraction budgets.
- OX-54 defines canonical identity over the exact manifest bytes and exact
  declared resource bytes, sorted and framed by canonical relative path. The
  identity is separate from per-asset integrity, origin, and entitlement.
- The OX-53 baseline asset is 1,259,884 bytes; the olive asset is 1,386,266
  bytes. Both are 1024 × 1536 PNGs. The cycle-161 builder artifact reports
  the latter bytes intact.

## Behavior for review

1. Decode the root manifest with OX-26. A decoder rejection ends validation.
2. Confirm every declared required capability is available in the selected
   OX-52 registry. Unknown or unsupported requirements reject the candidate.
3. Inspect the generated archive's file members. Require one root
   manifest and exactly the declared resource files; reject duplicate names
   and undeclared file payloads so the canonical OX-54 identity covers the
   full accepted payload.
4. Resolve each declared canonical path within the pack root. Reject missing,
   ambiguous, escaping, or symlink-backed resources.
5. Enforce the currently registered resource type and validate the resource
   bytes against that declared type. Full renderer decoding and decoded-memory
   limits remain with OX-55.
6. Enforce finite per-resource and aggregate byte limits. The approved cap is
   **1.5 MiB per resource and 1.5 MiB aggregate**. That is 1,572,864 bytes,
   about 13.5% above the largest current OX-53 example. Record exact measured
   sizes and the boundary in the cycle evidence.
7. Compare each referenced resource's exact-byte SHA-256 with its OX-51
   declaration. A mismatch rejects the complete candidate.
8. Recompute the OX-54 content identity from the unmodified root-manifest
   bytes and all declared resource bytes in canonical path order. Return this
   derived identity in the validated result. Manifest v2 has no
   identity field to compare; each declared asset digest remains the mismatch
   check for its referenced bytes.
9. Publish one complete validated result only after all checks pass. OX-55
   may consume that result in a later slice. OX-91 does not extract or install
   a package, activate a scene, or change weather state.

## Approval record

The product owner approved this four-file package on 2026-11-20, including its
existing contextual status wording. The behavior contract covers the
exact-member rule, 1.5 MiB per-file and aggregate caps, media validation at the
registered-type boundary, and recomputation/return of OX-54 identity. The
separate central-directory preflight cap and ZIP-format details are resolved
in cycle 163's plan. Approval does not authorize implementation.

Focused OX-91 slice-specific tests are authorized. System and device tests are
out of scope. The test plan will be drafted only after this board package is
approved.
