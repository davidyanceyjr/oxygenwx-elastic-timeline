# OX-57 behavior/contract board proposal

**Status:** Pending product-owner review and approval. This board is a proposal;
it does not authorize implementation.

## Scope and outcome

Install one complete OX-91-validated data-only pack from a device-local source
into app-owned storage. Keep the candidate invisible while writes are staged.
Expose or replace the app-owned OX-31 catalog registration only after every
staged member and its registration metadata are ready. A failed candidate must
leave the prior valid generation and catalog entry intact. The bundled-free
entry remains available. Weather facts and selected time do not change.

## Established dependency facts

- OX-91 accepts the local ZIP before installation and returns a typed manifest,
  validated asset bytes keyed by canonical relative path, and the recomputed
  OX-54 content identity. Rejection returns no partial asset set. OX-91 is
  read-only and does not extract or write files.
- OX-91 already enforces its own archive inventory and resource limits: at most
  64 ZIP entries, at most 65,536 encoded central-directory record bytes, and
  at most 1,572,864 uncompressed bytes per resource and in aggregate. These do
  not satisfy OX-57's separate staging/extraction budget.
- The renderer policy assigns OX-57 finite limits for extraction entry count,
  expanded data, staged storage, and extraction working memory. It assigns
  OX-55 renderer-load memory and scene activation separately.
- OX-31 composes the catalog from explicit app-managed registrations. App-owned
  identity is the manifest `packId`; display name is catalog metadata; version
  is a non-empty opaque app-managed content/release revision. It is separate
  from manifest version, renderer policy, compatibility, and OX-54 content
  identity.
- OX-31's catalog composer is read-only. OX-57 supplies the installed
  app-owned registration and storage; OX-58 later verifies cold-relaunch
  restoration. OX-32 owns in-session theme switching. OX-57 does not activate
  a rendered scene.

## Proposed flow and boundaries

1. A device-local adapter supplies the generated OX-30 artifact to OX-91. The
   proposed scope has no app-visible file picker, Settings surface, or
   storefront. Owner confirmation is requested below.
2. Continue only with OX-91's complete accepted value. On validator rejection,
   stop without writing app-owned storage or changing the installed catalog.
3. Require app-managed catalog metadata alongside the accepted package:
   `packId` comes from the manifest, while display name and opaque
   content/release version are supplied separately. Do not infer the name from
   `packId` or use OX-54 `contentIdentity` as the catalog version.
4. Before staging, enforce OX-57's finite extraction entry-count, expanded-byte,
   staged-storage, and working-memory limits. Exact numeric thresholds remain
   a plan decision supported by the existing pack sizes and bounded evidence;
   they are not invented in this board.
5. Write only the validated, canonical member set and required app-owned
   registration metadata beneath a private unpublished staging generation.
   Keep all resolved output paths inside that generation's app-owned root.
6. On missing/invalid metadata, a limit failure, write failure, or interrupted
   import, discard the incomplete stage. Before commit, the previous valid
   generation and catalog entry for the same `packId` and the active scene
   remain unchanged; no partial registration is visible.
7. After every staged output is complete, atomically make that generation and
   its app-owned catalog registration current. A successful new pack appears
   as a selectable OX-31 catalog entry; the bundled-free entry remains
   available. Proposed validity meaning: OX-91 acceptance establishes
   capability/resource compatibility for the catalog, while OX-55 still gates
   actual decode/load before scene activation. Confirm this boundary below.
   Installation does not change weather, selected time, or the active scene.

The board specifies the observable atomicity contract, not a particular
directory naming or metadata-file layout. OX-55 remains responsible for
renderer decoding/loading and OX-32 for later in-session switching. OX-58 owns
the separate cold-relaunch restoration proof.

## Owner review requests

1. Confirm the proposed developer-only handoff: a local adapter supplies a
   `File` to the installer, with no user-visible in-app picker in OX-57. If a
   visible import surface is intended, revise the slice scope and add a UI
   artwork board before plan finalization.
2. Confirm that the install boundary receives explicit app-managed display
   name and opaque content/release version metadata separately from the closed
   manifest. This follows OX-31 and keeps content identity separate.
3. Approve the budget categories and staged-commit behavior. The plan must
   choose exact finite OX-57 limits with evidence; OX-91's existing validation
   caps remain independent.
4. Confirm the proposed catalog-validity boundary: OX-91 acceptance supplies
   `COMPATIBLE` for resource/capability policy, while OX-55 separately gates
   actual decode/load and scene activation.

## UI artwork applicability

No UI artwork is proposed because the reviewed scope has no app-visible
presentation change. This depends on approval of the developer-only local-file
handoff above. A file picker, import dialog, or catalog selection surface would
change that disposition and require a UI artwork board.
