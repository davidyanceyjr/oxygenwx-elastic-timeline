# OX-56 behavior board proposal

**Status:** Proposed for product-owner review. Approval is pending; this board
does not authorize implementation or claim installed acceptance.

## Scope

When a previously referenced scene package is missing or invalid during scene
resolution, use the current bundled First City baseline as a complete fallback
scene. Preserve the exact selected instant, place, catalog revision, forecast
facts, and timeline operation. Do not leave a partial scene or asset commit,
and do not fail the process.

This is a resolution-time recovery rule. Import-time rejection remains with
OX-91/OX-57. OX-55's existing failed *new chooser candidate* behavior continues
to preserve the prior committed world. Neither behavior adds a package import,
installation, catalog, settings, entitlement, or relaunch policy.

## Inherited constraints

- OX-91 returns one complete validated artifact or a bounded rejection; it
  does not expose a partial asset set.
- OX-55 prepares and admits a complete renderer candidate before committing
  the selected world. Validation, compatibility, dimension, budget, and decode
  failures do not partially replace the committed scene.
- OX-19 makes scene choice independent of place, catalog revision, and exact
  selected instant, with no switch-triggered forecast fetch or process
  restart.
- OX-41 names the Living Urban Park as the future free default. The product
  owner selected the currently implemented First City baseline for this
  device-local OX-56 fallback; this bounded selection does not revise OX-41.

## Proposal to review

The recovery screen uses the existing World control to identify the selected
fallback as **World: First City**. No extra banner or dialog is proposed. The
owner may request a brief cause explanation before approving the UI artwork.

The Graphviz source is `OX-56-pack-resolution-recovery-behavior-board.dot`;
its SVG and PNG are review renders of that source.
