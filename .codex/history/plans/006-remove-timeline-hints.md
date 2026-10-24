# Cycle plan — 006-remove-timeline-hints

State: COMPLETE
Roadmap: P.2
Objective: Remove the instructional footer and visible timeline mode-control row while preserving direct timeline gestures and double-tap return-to-current-time behavior.

## Production boundary

Change only `TimeLabScreen.kt`, `ElasticForecastTimeline.kt`, `SPECIFICATION.md`, and `ROADMAP.md`, plus this cycle's plan/history/evidence. Do not modify the oxygenWX production repository or its navigation.

## Functional invariants

- Keep the top `OXYGEN / TIME LAB` and `DEMO DATA` prototype identity.
- Keep timeline tap/day selection, swipe/day navigation, hold-to-hourly scrubbing, and double-tap return-to-current-time behavior.
- Keep the single absolute selected-hour state authoritative; changing timeline scale must not move the selected time.
- Remove the `HOURLY PRECISION` / `DAILY NAVIGATION` and `HOURS` / `DAYS` control row, and remove the `SWIPE DAYS · HOLD TAPE FOR HOURS` / `NOW` footer.
- Preserve a screen-reader action to switch between daily and hourly timeline scales without rendering another visual control.

## Steps

- [x] Scope and baseline inspection
- [x] One bounded implementation
- [x] Workflow/contract/build checks, install and capture the resulting screen

## Acceptance/evidence

Capture the updated Pixel 3 XL screen in `.codex/test-artifacts/006-remove-timeline-hints/`. Confirm visually that both rows are absent and the remaining time tape is still visible. Record build/install outcome. Automated unit tests and broader font, RTL, and TalkBack acceptance remain outside this bounded cycle.

## Risks / assumptions

Removing the visible mode row removes its explicit control affordance. Retain an equivalent screen-reader custom action; direct hold-to-scrub remains the primary visual interaction. Removing the NOW button leaves the established central double-tap reset gesture.

## Out of scope

No changes to forecast content, demo labeling, top branding, atmospheric rendering, or production navigation. No removal of the double-tap reset gesture.
