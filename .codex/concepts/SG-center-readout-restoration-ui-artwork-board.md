# SG center-readout restoration — UI artwork board

Status: APPROVED BY PRODUCT OWNER — 2026-11-06. The owner requested return to the previous center-readout state after reviewing the installed capture.

## Target

At a selected time such as Sunday 11:36 PM, show the weather center stack in its pre-cycle order: temperature and symbol, condition, solar facts, lunar facts, then daily high/low. Do not insert the newly added grouped source attribution between condition and solar facts. Keep the selected time in the timeline pill.

The selected 11 PM point's source time and the exact selected instant remain available in the timeline accessibility description and forecast browser. This removes only the new visible center-readout line. Existing SG.4 carried-condition disclosure is unchanged.

At midnight, the next local date owns the facts and the same center-readout hierarchy applies. No source attribution row is added.

## Acceptance cues

- At 11:36 PM, the condition is followed directly by the solar line; the center contains no `Scene, Condition event, Temperature...` attribution text.
- Solar, lunar, and daily high/low facts retain their prior placement and remain readable.
- The bottom timeline pill continues to show the selected time, and timeline semantics retain selected and source times.
- At midnight, only the next local date's data is shown, using the same hierarchy.

The companion SVG is an annotated layout board. Installed screenshots and scoped interaction checks remain the evidence for acceptance.
