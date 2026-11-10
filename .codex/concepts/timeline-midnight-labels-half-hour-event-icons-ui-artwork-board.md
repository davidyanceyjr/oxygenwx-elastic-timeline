# Timeline midnight labels and half-hour event icons — UI artwork board

**Status:** Product owner approved on 2026-11-09; no slice ID assigned yet.
**Prepared:** 2026-11-09.
**Companion behavior board:** `timeline-midnight-labels-half-hour-event-icons-behavior-board.md` and its SVG.
**Installed reference:** `timeline-midnight-labels-half-hour-event-icons-before.png`, a fresh unedited Pixel 3 XL capture of the current daily timeline, captured on 2026-11-09 with `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/concepts/timeline-midnight-labels-half-hour-event-icons-before.png`. SHA-256: `f0d0b157c34a7f5d7fb41e7b2616bfcba671b78623d0466f5aec264f7c4bb75a`.

## Proposed states

The paired SVG places the actual installed screen capture beside focused proposed timeline states. The capture is the “before” reference; the proposed strips are artwork concepts, not installed after-state evidence.

### Daily scale

- Move each three-letter weekday label from the day-center/noon position to the matching local-midnight boundary tick.
- Keep the existing daily representative weather symbol and daily high on the date's noon overview coordinate. This preserves the data/overview anchor while making the start of each date explicit.
- Keep the selected-time marker and badge at the exact selected instant, including when it coincides with a noon overview.
- Retain the current tape, divider, playhead, labels, symbols, values, palette, and daily drag behavior.

### Hourly scale

- Keep weekday labels centered over their local-midnight boundary ticks.
- Keep exact hourly ticks at their current sample instants. Add a short, low-contrast minor tick at each `:30` position.
- Place the existing weather-event symbol for each supported hourly sample directly above its `:30` minor tick. Use a compact symbol size that fits the hourly spacing and does not obscure the selected-time cue, hourly tick, or existing labels. The proposal shows a supported symbol in each illustrated hour; missing/unsupported samples remain empty under the behavior board.
- Keep the selected-time badge and hourly navigation unchanged. Symbols are visual summaries, not controls or source-time markers.
- Missing, empty, or unsupported event samples leave the corresponding midpoint empty.

## Artwork constraints

- Keep the current immersive screen hierarchy and bottom-tape appearance.
- Preserve readable weekday labels and selected-time text. Midnight labels align with true local date boundaries in LTR and RTL axis layouts.
- Do not move daily representative weather content or high values to midnight; their icon/value coordinate remains the existing local-noon overview point.
- Do not present a symbol at `:30` as a reported event onset or as a weather fact valid at `:30`.
- Keep the hourly symbols visually subordinate to the selected-time marker and forecast readout; do not restore the former oversized hourly icon treatment.

The proposed timeline strips are schematics based on the installed capture's colors and hierarchy, with callouts isolating the affected band. Their example timeline has regular hours for clarity. Installed acceptance must also cover an affected local date boundary and a supported/missing hourly event pair. Any additional DST screenshot should be scoped to the chosen installed state if a DST fixture is available; the behavior contract still requires instant-based placement.

## Approval record

The product owner approved this exact UI artwork board on 2026-11-09. Approval covers the installed Pixel 3 XL daily capture as the before reference; local-midnight labels in daily and hourly scale; daily symbols and highs remaining at the noon overview; compact supported hourly event symbols centered at `:30`; and unchanged source times, selected-time behavior, and gestures. No additional constraints were stated.

No stable slice ID or cycle has been assigned yet, so the cycle tracker fingerprint record is deferred until future slice planning. This approval does not activate or implement a slice.
