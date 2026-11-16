# OX-04 UI artwork board — First City evidence states

**Status:** Product-owner-approved 2026-11-16 for cycle `143-ox04-scene-truth`. Approval status and package fingerprints are recorded in the cycle plan; approval governs this artwork target and acceptance, not implementation.

**Artwork:** `OX-04-first-city-unknown-state-ui-artwork-board.svg` (editable vector source) and `.png` (review render).

## Visual objective

Keep the existing First City screen and forecast layout while making three evidence states visually reviewable: a supported condition, an eligible older carried condition with its source time, and an unavailable condition with independent facts still visible. In the isolated unknown example, show no condition-derived weather marks; keep the time cue and supplied facts. Keep precipitation chance explicitly a chance, not an observed rain claim or measured rate.

## States shown

1. **Supplied rain:** reference replay `sg4-canonical-gaps-v8`, selected `10:30 AM CDT`, supplied rain, `57°F`, and `75%` precipitation chance. Keep the existing supported rain composition and the OX-03 cloud-origin placement.
2. **Eligible carried rain with no visible cloud decision:** reference replay `sg4-canonical-gaps-v8`, selected `11:30 AM CDT`, condition source `10:00 AM CDT`. Preserve the no-cloud carried-rain scene and keep source time visibly separate from selected time.
3. **Unavailable condition with independent facts:** reference replay `ox01f-condition-unavailable-typed-v1`, selected `3:00 PM CDT`, `48°F`, and `25%` precipitation chance. Show the condition as unavailable, no condition-derived cloud/rain marks, and retain temperature and chance. The percentage remains a probability.

The panel scenes are schematic artwork targets, not installed screenshots. Values and times identify existing replay examples in OX-03 evidence; they are not live observations. The isolated unknown panel assumes no separately supplied cloud cover, precipitation amount, or precipitation rate that would independently support a scene mark.

## Constraints

- Keep First City composition, city layers, forecast foreground, source-time disclosure, and timeline layout at the existing baseline.
- Do not add cards, controls, a new unknown-weather illustration, a replacement clear/dry symbol, or new labels. Use the existing unavailable-condition wording.
- Preserve exact selected time. For carried evidence, show its source-valid time separately.
- Do not convert probability into rainfall amount/rate or an observed condition. Preserve independently supported facts.
- Preserve time-of-day cues under their existing input rules; they are not weather evidence.
- This is a planning target only. Installed acceptance requires actual scoped captures during execution.

The board follows OX-03's First City schematic style and reuses its route-state evidence only as a reference. It does not claim OX-04 acceptance or authorize a rendering change.
