# Cycle 011-hour-label-alignment

Date: 2026-10-24
Roadmap: P.2.5a

## Outcome

Aligned the forecast summary hour label with the selected tape tick using the same nearest-hour rounding convention. Fractional forecast interpolation and selection movement remain unchanged. Pixel 3 XL captures show matching 2 PM summary and selected label during fractional scrubbing and after release followed by stationary re-entry. P.2.5a exit met; P.2.5 is ready for its full installed acceptance rerun.

## Verification actually performed

Passed: python scripts/dev.py workflow before and after closeout updates; python scripts/dev.py build (BUILD SUCCESSFUL); python scripts/dev.py run --serial 8AKY0M32X (installed and launched); installed Pixel 3 XL fractional-scrub, collapse, and re-entry captures recorded under .codex/test-artifacts/011-hour-label-alignment/; git diff --check. Not run: tests, lint, contract, TalkBack, RTL, reduced motion, large font, constrained layout, or subjective latency review.

## Follow-up

P.2.5a is complete. P.2.5 is NEXT for a full installed rerun of immediate-drag and hold-then-drag arbitration, hourly scrubbing, collapse/release, timestamp continuity, responsiveness, gesture predictability, and readability against the corrected label. The selected hour can be observed after collapse by holding stationary at the center playhead to re-enter hourly mode. P.2.6 remains PLANNED; TalkBack, RTL, reduced-motion, large-font, and constrained-layout acceptance remain open.
