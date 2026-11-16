# OX-02A UI artwork board — catalog fallback parity

**Status:** Draft for product-owner review; approval is pending. Prepared in cycle `140-ox02a-catalog-renderer-fallbacks`.

**Artwork:** `OX-02A-catalog-fallbacks-ui-artwork-board.svg` (editable vector source) and `.png` (review render).

## Visual objective

Keep the active catalog screen's existing central forecast readout, atmospheric field, visible source-time disclosure, and lower timeline consistent when a renderer route is consolidated. The candidate reading route must reproduce the composition reference for the same selected instant and inputs. No new controls, cards, scene styling, or route-specific visual treatment are proposed.

## States shown

- **Supported condition:** matching condition, symbol, independent facts, scene, and selected-time reading.
- **Missing condition:** explicit unavailability, no invented condition symbol or claim, and any independently supported value retained.
- **Eligible carried condition:** the selection remains at the requested instant while the prior condition's actual source time stays visible.
- **View-only with no scene:** foreground and timeline remain; the scene renderer is not called when `view.scene` is null.
- **View-only with a scene:** reachability and appearance remain unverified. Preserve the existing route until a real caller and trigger can be reviewed; this board does not invent an output for that state.

The paired screens are an artwork target, not proof that the current routes match. The city and sky are schematic; example names, values, and times are placeholders, not fixture data.

## Constraints

- Keep the exact selected instant and current timeline selection.
- Do not obscure the main forecast facts or their source-time disclosure.
- Missing conditions remain unavailable; carried conditions retain their source-time disclosure.
- Keep the composition renderer primary and fixture / Time Lab previews separate.
- Any visible change or claimed parity requires scoped installed captures and direct interaction checks during slice execution.

The artwork follows the approved OX-01f UI board's restrained cool palette and state-by-state review style. It is a planning proposal, not installed visual acceptance evidence.

