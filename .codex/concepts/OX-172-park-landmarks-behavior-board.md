# OX-172 Park Landmarks behavior board

**Status:** Proposal for product-owner review; approval pending.

## Shared objective

Place the vegetation/bench framing group and the streetcar shelter in the
approved Park composition while preserving essential forecast access. OX-43
and OX-44 keep separate behavior, acceptance, and exits. OX-44 does not depend
on OX-43.

## Source constraints

- **OX-25:** retain the distant backdrop, curved path, and foreground entrance.
- **OX-35:** give each authored element a stable scene-local identity/role and
  logical geometry/anchor/bounds using the established bounded geometry model.
- **OX-41:** vegetation and benches form a required path-framing group; the
  coarse streetcar shelter is a required landmark mapped behind the curved
  path. Use its approved daylight portrait composition and bundled-free-Park
  role.
- **Cycle 171:** keep the left mural wall and its reserved blank region, and
  preserve the initial portrait safe-area composition. OX-12/OX-12A mural
  readings remain out of scope.
- **Product invariants:** selected time, forecast/weather meaning, central
  facts, and timeline remain unchanged and readable. Decorative objects do not
  create or replace weather facts.

## Proposed board decisions

- OX-43 uses a small placeholder set of two trees and two benches as a
  reviewable starting proposal. Their logical bounds and depth groups frame
  the path without covering the mural reservation, central readout, or
  timeline. The approved count and visual detail remain subject to owner
  review.
- OX-44 adds one low-detail historic streetcar shelter at the OX-41 anchor.
  Its declaration carries stable identity, role, geometry, and layer order;
  its visible structure remains behind the curved path where they overlap.
- The UI artwork package shows the OX-43 and OX-44 additions separately on the
  same established base, so neither slice appears to depend on the other.

## Acceptance kept per slice

- **OX-43:** installed Park evidence shows tree/bench placement and natural
  depth ordering without covering the essential readout or timeline.
- **OX-44:** installed scene demonstrates stable shelter placement and correct
  basic occlusion against the path, entrance, and background without blocking
  central forecast access.

The proposed verification is limited to the daylight portrait Park state and
the slice-local composition/depth evidence. Broad accessibility and device
matrices remain in their dedicated TEST slices. This behavior board and its
artwork are proposals, not installed acceptance evidence.
