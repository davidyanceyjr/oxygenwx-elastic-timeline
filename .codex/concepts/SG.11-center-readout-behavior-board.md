# SG.11 visual behavior board — supporting contract

**Status:** Product owner approved the visual behavior board on 2026-11-04, with the same-message direction to move the location picker trigger beside the location label and remove the lower magnifier and gear. The visual board is [`SG.11-center-readout-behavior-board.svg`](SG.11-center-readout-behavior-board.svg), with a PNG preview alongside it. This document records the rules behind the diagram; it is not the primary review surface. The separate UI artwork board was approved later on 2026-11-04 after the indicator-spacing revision.

## Owner direction incorporated in this draft

- Pair the temperature and WA weather symbol closely. The supported condition word (for example, “Cloudy”) is omitted from the central visible readout because the graphic communicates it.
- Omit the center day-of-week and time line; the timeline control already displays selection. The selected `Instant` still governs all forecast facts and the symbol.
- Retain supported Sun and Moon rise/set times, daily high/low, and the noninteractive loaded-location label.
- Put a small location-picker trigger beside that label. The trigger opens the existing picker; the label remains noninteractive. Remove the lower magnifier and gear icons. Opening or dismissing the picker alone does not change the selected forecast instant.
- Show precipitation chance and wind speed as prominent numeric facts, with compact pictographic or typographic cues. The SVG currently proposes `☂` and `↗`; their exact glyphs and legibility are still subject to owner review and installed inspection.

## Condition state contract

| State | Center display | Source claim |
|---|---|---|
| Supported selected hour | Matching WA symbol beside temperature; no visible condition word | Symbol derives from typed supported condition/phenomenon, never display strings or scene artwork. |
| Eligible interior missing hour | Prior supplied symbol, plus visible original source time and selected-hour-unavailable disclosure | The condition is older evidence, not a fact supplied for the selected hour. |
| Unsupported/empty condition, edge gap, or missing day | WA unavailable symbol; concise visible unavailable status may be used | Never imply clear weather. |

The condition name remains available as an accessible description for the symbol/readout and as text in the forecast browser. Avoid duplicate spoken announcements. Sun and Moon event times, temperature, high, and low each retain independent missing-value behavior; absent values cannot become zero or an invented event.

## Owner scope amendment: full WA event catalog

On 2026-11-04 the product owner expressly included support for all 48 icon-represented weather events in SG.11. The approved WA.1 behavior board defines those 48 stable identities. For this board's selected-time flow, an explicitly supplied typed event resolves to the matching WA symbol and accessible name; a carried event retains its original source time; and no supportable event resolves to the unavailable symbol. The canonical, simulated, replay, and provider-normalization paths must preserve distinct event identities when supplied. This amendment adds semantic coverage without changing the approved UI composition or claiming that one provider supplies every event.

## Interaction and scope

The center swipe, tap, and double-tap behavior, timeline selection, existing picker behavior, lower precipitation/wind facts, provenance, and gesture ownership remain. The new picker trigger needs a usable touch target without taking over the central swipe area. Layout or symbol changes cannot retarget the selected instant. SG.11 adds no WA artwork, provider fields, weather categories, unit policy, or navigation behavior.

The adopted experience specification's selected-forecast hierarchy has been updated for the owner's SG.11 direction: the center uses the symbol while accessible semantics and the forecast browser retain textual condition meaning.

## Approval record

The product owner approved “What the Center Readout Shows” in conversation on 2026-11-04 and directed, in the same message, the small picker trigger beside the location label and removal of the lower magnifier and gear. The behavior SVG and PNG were updated to include that direction. This approval covers the behavior flow, selected-condition states, omission of the visible supported-condition word, retained Sun/Moon and high/low facts, and relocated picker action. The product owner then approved both boards after requesting one empty indicator-sized space between precipitation chance and wind. The UI artwork SVG and PNG show that spacing. The experience specification has been reconciled for the center readout.
