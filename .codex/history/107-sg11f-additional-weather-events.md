# Cycle 107-sg11f-additional-weather-events

Date: 2026-11-05
Roadmap: SG.11f

## Outcome

Completed SG.11f: added the canonical-only Additional events replay fixture and bounded review route for all eight identities, passed the runtime version-5 storage reload, captured all eight Pixel 3 XL states with hierarchies, checked timeline selection, and reconciled the complete 47-ID mapping, exact-code ledger, and priority order. No SG.11f Open-Meteo code assignments were approved, so the mapper was not changed.

## Verification actually performed

python scripts/dev.py workflow: passed before execution and after activation; python scripts/codex_cycle.py activate: activated cycle 107; python scripts/dev.py contract: passed; python scripts/dev.py build: BUILD SUCCESSFUL; python scripts/dev.py run --serial 8AKY0M32X: installed and launched; Pixel settings: font scale 1.0, size 1440x2960, density 560; runtime Additional replay preparation: version-5 save/reload check passed for eight typed events, primary indices, and empty broad conditions; installed PNG/XML evidence captured for all eight identities under .codex/test-artifacts/107-sg11f-additional-weather-events/; direct timeline hold/scrub selected 9:15:19 AM CDT from 10:00 AM and timeline accessibility text reflected the new selection; DISPLAY=:0 xdpyinfo: passed; git diff --check: passed; tests not added or run as explicitly excluded by the approved cycle plan. Prior SG.11 rows and versions 1-4 replay were not rerun; prior cycle evidence is retained. RTL, TalkBack, reduced-motion, alternate font scales, and broad system/device matrices were not checked because they are outside this slice.

## Follow-up

The scrub-time disagreement between the retained selected event and central readout is assigned to SG.12, consistent with the accepted cycle 106 limitation. SG.12 is now the sole NEXT slice. Prior SG.11 rows and legacy replay were not rerun in cycle 107; their focused checks remain in cycles 103–106 evidence. No other SG.11f scope remains open.
