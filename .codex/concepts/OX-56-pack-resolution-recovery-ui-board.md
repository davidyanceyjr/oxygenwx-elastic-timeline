# OX-56 UI artwork proposal

**Status:** Proposed for product-owner review. Approval is pending. The board
does not authorize implementation or claim installed acceptance.

## Affected state

A previously referenced package cannot be resolved, so the scene falls back to
the currently bundled First City baseline. The board proposes to use the
existing World control to show **World: First City**. The same forecast
readout, selected time, place, and timeline remain visible. No new banner,
dialog, or blocking state is proposed.

The owner review point is whether the selected-world label is sufficient or
whether the UI should briefly explain that recovery occurred and why. No
specific failure reason or additional user-facing text has been assumed.

## Reference and constraints

The artwork uses cycle 164's installed First City baseline-proof capture as a
visual reference. The SVG overlays the proof-only control text with the
proposed normal First City label. Chicago, Sunny, 67°, feels like 61°, the
secondary readings, and the Friday 2:37 PM timeline are reference values that
demonstrate the state to preserve; they are not new forecast inputs.

The target is one resolution-failure state. It does not redesign First City,
add persistent status chrome, change the timeline, or cover OX-91/OX-57 import
rejection. A failed new chooser candidate retains OX-55's existing behavior.

**Reference image:**
`.codex/test-artifacts/164-ox55-pack-renderer/installed-baseline-proof.png`.

The `.svg` is the editable artwork source; the PNG render is for visual review.
