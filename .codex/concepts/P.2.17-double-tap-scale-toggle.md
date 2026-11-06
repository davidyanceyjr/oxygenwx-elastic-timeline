# P.2.17 — Timeline double-tap scale toggle

**Status:** Product owner approved as shown on 2026-11-05.

## Behavior contract

The timeline surface is the scale-toggle target. A double-tap on the tape toggles between its daily and hourly scales in either direction. This is a scale-only transition: the selected instant does not change, the hit coordinate does not select a date/hour, and the tape remains centered on the selected instant.

| Current state | Input | Result |
|---|---|---|
| Daily | One timeline tap | No selection or scale change. |
| Daily | Immediate horizontal timeline drag | Keep daily browsing behavior. |
| Daily | Stationary hold, including hold then drag | Do not enter hourly scale; daily navigation remains the active gesture. |
| Daily | Timeline double-tap | Enter hourly scale, preserving the exact selected instant. |
| Hourly | Timeline drag | Keep hourly scrubbing behavior. |
| Hourly | One timeline tap | No selection or scale change; remain hourly. |
| Hourly | Timeline double-tap | Return to daily scale, preserving the exact selected instant and ignoring the tap coordinate. |
| Either | Main forecast-area double-tap | Keep the existing fixture realignment behavior; the timeline toggle does not take ownership of this gesture. |

Timeline single taps do not select dates or hours. Gesture recognition must arbitrate a timeline double-tap as one scale toggle, without dispatching a single-tap selection. Immediate daily dragging and hourly scrubbing remain unchanged; stationary hold or hold-then-drag does not change scale. Main-area single-tap collapse, main-display double-tap fixture realignment, and the screen-reader scale action remain available and unchanged. No visible mode row or instructional hint is introduced.

## Artwork board

The paired SVG is a schematic of the existing tape and selected-time cue in daily and hourly states, with the double-tap input and preserved playhead instant annotated. It is a design board, not an installed screenshot or a claim that the current rendering already meets the proposed transition. The installed current-state capture `.codex/concepts/SG.13-current-main-screen-reference.png` is provided only as context for the existing screen composition. This revision follows the owner's 2026-11-05 direction to remove timeline single-tap selection while preserving drag behavior. The product owner approved this revised board as shown on 2026-11-05.

## Acceptance relationship

The behavior board governs gesture target, no-op timeline single taps, preserved daily/hourly drag behavior, double-tap arbitration, scale-only selection stability, and separation from main-display realignment. The artwork board governs the daily/hourly affected states, unchanged tape styling, selected-time alignment, and absence of new persistent controls. The product owner approved the revised combined board as shown on 2026-11-05. Installed screenshots and scoped interaction checks remain necessary during execution.
