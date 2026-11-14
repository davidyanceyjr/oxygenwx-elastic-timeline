# OX-01 current-world baseline evidence index

This is a documentation index assembled on 2026-11-14. No app was installed, run, or captured in cycle 134. Linked PNGs remain in their original cycle directories and retain those cycles' device, route, revision, and acceptance limits.

**Dated correction, cycle 135:** The [OX-01R installed follow-up](../135-ox01r-debug-baseline-correction/README.md) identifies existing SG.11 debug replay routes omitted from this index and confirms a current installed event-overlay/center-readout disagreement during a focused scrub. Cycle 134's archived history remains its original closeout record; use the follow-up for corrected conclusions.

| Record | Purpose |
| --- | --- |
| [Reference set](reference-set.md) | Representative installed stills and selection transitions, with source-time and applicability labels. |
| [Keep/change/unknown inventory](keep-change-inventory.md) | Source-backed preservation decisions and unresolved observations. |
| [SG.9 installed evidence](../083-sg9-typed-scene-composition/README.md) | Typed scene states and focused scrub, existing Florence replay. |
| [SG.13 installed evidence](../109-sg13-skyline-anchored-readout/README.md) | Chicago center readout, compact state, and focused selection gestures. |
| [MR.2 installed evidence](../122-mr2-main-readout-raise-compact/README.md) | Later center-readout layout in Florence LIVE and stored SIMULATED states. |
| [SG.8 installed evidence](../093-sg8-lunar-events-and-phases/README.md) | Lunar cue, phase, event, compact and reduced-motion states. |
| [WX.25 installed evidence](../071-wx25-hourly-timeline-acceptance/README.md) | Earlier Chicago SIMULATED catalog, browser, selection and boundary states. |
| [SG.11e installed evidence](../106-sg11e-storm-fallback-events/README.md) | Earlier explicit unavailable event and scrub-time overlay/readout disagreement. |
| [OX-01R installed follow-up](../135-ox01r-debug-baseline-correction/README.md) | Current-build Florence replay unavailable state and scrub disagreement. |

## Evidence rule

An installed observation proves only its recorded route, state, revision, and device. The current source path is statically inspected in the reference set; it does not upgrade historical screenshots into current installed acceptance. `UNKNOWN` means missing or inapplicable proof, not a demonstrated regression. No new visual acceptance, TalkBack acceptance, or system matrix is claimed.

## Cycle 134 checks

- `python scripts/dev.py workflow` — PASS before activation and after activation.
- Read-only source, history, and artifact inspection — completed for the paths cited in the reference set and inventory.
- New build, install, screenshot, interaction checks, and automated tests — NOT RUN; not authorized for this documentation baseline.
