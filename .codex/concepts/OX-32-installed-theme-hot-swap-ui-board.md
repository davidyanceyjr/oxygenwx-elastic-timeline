# OX-32 UI artwork board proposal

**Status:** Pending product-owner review and approval. This is proposed artwork,
not an implemented screen or installed acceptance evidence.

## Intended appearance

The board shows the existing session-local World control and chooser in four
states: bundled free First City, chooser with one app-owned compatible pack,
the installed pack active, and return to the bundled free scene. Keep the
existing forecast information, readout placement, and timeline visible. The
alternate is the First City baseline canopy pack, which visibly differs from
the current olive bundled canopy while retaining the same element geometry.
The return state restores the bundled appearance.

The chooser option identifies the pack as `Installed · Compatible`; the
existing bundled choice is identified as `Bundled free`. The display name in
the illustration is example app-managed metadata. It is not a new pack ID or
version rule. The World control remains a validation-build proof surface for
OX-32. OX-69 owns a later Settings selection surface.

## Constraints and limits

- Only the selected theme appearance changes. The weather readout, place,
  selected instant, and timeline stay identical across the active states.
- Use existing World control visual language; no persistent row, new page,
  import picker, banner, storefront, or Settings route.
- Show the complete alternate scene after the atomic switch. This artwork does
  not depict a blended or partially activated scene.
- The values and timeline labels are schematic illustrative values, not a
  fixed forecast fixture or new weather claim.
- The artwork does not cover invalid-pack recovery, saved selection,
  cold-relaunch restoration, or simultaneous scrub/switch.

## Source relationship

- `.codex/concepts/OX-19-scene-choice-ui-board.svg` — existing World control
  and chooser reference.
- `.codex/concepts/OX-55-pack-renderer-ui-board.svg` and its companion —
  established visual distinction between the baseline and olive canopy and
  the validation-build proof-choice precedent.
- `.codex/concepts/OX-32-installed-theme-hot-swap-behavior-board.dot` — flow,
  state invariants, atomic activation, and exclusions for this slice.
- `.codex/test-artifacts/164-ox55-pack-renderer/README.md` — installed
  baseline/olive visual result; this is dependency evidence, not OX-32
  acceptance evidence.

No screenshot alone is being used as a board. The SVG is the editable artwork
source and the PNG is its review render.
