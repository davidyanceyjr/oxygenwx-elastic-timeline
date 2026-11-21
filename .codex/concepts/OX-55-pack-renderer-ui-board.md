# OX-55 UI artwork board companion

**Status:** Product owner approved on 2026-11-20. The SVG is a self-contained
artwork source with reduced JPEG image data embedded for review.

**Focused revision:** The owner selected a debug-only bundled BASELINE proof
choice on 2026-11-20 after installed captures showed that normal First City
already draws OLIVE. The updated chooser shows that proof choice alongside
the loaded olive pack. Normal First City remains unchanged.

## Intended appearance

The board shows the same First City selected-weather view before and after the
olive canopy asset is loaded. The lower foliage changes from the brighter green
bundled baseline canopy to the muted olive pack canopy. It also proposes temporary
`First City · Baseline proof` and `First City · Olive pack` entries in the
existing World chooser for the validation build. The
selection is session-local and does not imply a pack catalog, installer, or
persisted choice. The weather, forecast readout, selected timeline instant,
location, catalog, canopy geometry, and layer depth remain the same.

The debug-only choice is a proposal for owner review; app-wide exposure is not
an established product decision. OX-52 requires a finite renderer-load budget;
the numeric cap must be evidence-based and recorded before the plan is
finalized.

## Source images and limits

- `.codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png`
- `.codex/test-artifacts/152-ox24-scene-asset-swap/fixed-olive.png`

These are OX-24's matched installed captures at the same fixed selected time.
They illustrate the existing renderer's baseline and olive appearance only;
they do not show a pack loaded at runtime and are not OX-55 acceptance evidence.
The mock chooser is proposed artwork, not a claim about implemented behavior.
The embedded thumbnails are downscaled to fit the board; the original PNGs
remain the source references above.

No other screen states or interactions are proposed by this board.
