# OX-99 UI artwork board companion — draft

**Status:** Prepared for product-owner review. This package is not approved and does not authorize implementation.

## Review states

- The first canvas shows the new Urban Park theme with the Sun cue visible.
- The second canvas shows the same fixed scene with the Moon cue visible, demonstrating that the supplied artwork remains sunlit in this state.
- The Settings panel shows Urban Park as a selectable theme alongside Living Urban Park, which remains the bundled free default.

## Composition shown

The canvas uses the supplied background, middleground, and foreground source images in that back-to-front order. The layer key places independently rendered Sun / supported Moon cues above the background image and below the later image planes. The timeline and forecast readout remain separate app-owned interface layers.

The cue symbols and timeline are schematic. The board illustrates the proposed states and stacking relationship; it is not an installed screenshot and does not claim final pixel placement. Implementation is scoped to the existing baseline-proof cue and selected-time behavior.

## Visible scope limit

The supplied images are fixed sunlit artwork. The Moon-visible mock reuses those same planes; no night palette, scene adaptation, alternate weather artwork, or new weather behavior is included.

## Source inputs and artifacts

- `references/background.png`
- `references/middleground.png`
- `references/foreground.png`
- Review render: `OX-99-urban-park-theme-ui-artwork-board.svg` and `.png`

The SVG is the source artwork board; the PNG is its raster review render. Both are retained for owner review. Neither is installed visual-acceptance evidence.
