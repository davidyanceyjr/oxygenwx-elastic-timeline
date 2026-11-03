# Cycle 091-sg5-center-city-weather-readout

Date: 2026-11-02
Roadmap: SG.5

## Outcome

Implemented the approved SG.5 readout on the Pixel-targeted catalog screen: the loaded city is a central noninteractive label above temperature and condition; weekday/date/time and high/low remain supporting facts; the lower row has evenly spaced precipitation, location magnifier, settings affordance, and wind indicators; and the top City, Place, and Refresh controls are removed. Installed scoped interactions and readability checks passed. Evidence, captures, and detailed limitations are in .codex/test-artifacts/091-sg5-center-city-weather-readout/README.md. Device settings were restored. Missing-value and alternate weather-condition states remain NOT RUN because they were not observed in the sampled active Chicago window.

## Verification actually performed

- `python scripts/dev.py workflow` — PASS before implementation and at closeout; cycle 091 was active before closure and workflow returned IDLE after closure.
- `python3 scripts/dev.py run --serial 8AKY0M32X` — PASS; Gradle build, Pixel install, and launch succeeded.
- Installed Pixel 3 XL checks — PASS: location magnifier opens/dismisses the existing menu without changing selected time; settings affordance is non-clickable; main-area swipe preserves selected time; timeline day selection updates to local noon; portrait, compact 360×800 dp same-device simulation, and 1.3 font-scale screenshots were inspected.
- Device restored: `wm size` has no override, physical 1440×2960 at density 560, font scale 1.0.
- `git diff --check` — PASS after implementation and closeout documentation edits.
- No tests were added or run, per repository instructions. RTL/pseudo-locale, TalkBack, broad system acceptance, missing-value fixture, and alternate weather-condition scenes are NOT RUN; exact scope and evidence paths are recorded in the cycle README.

## Follow-up

SG.5's scoped exit is complete. The sampled active Chicago window showed cloudy conditions only; alternate weather-condition scenes and a missing-value fixture remain NOT RUN. RTL/pseudo-locale, TalkBack, reduced-motion mode, and broad system acceptance were not exercised and remain outside this design slice.
