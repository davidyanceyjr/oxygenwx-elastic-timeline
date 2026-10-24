# Cycle plan — 005-main-display-navigation

State: CLOSED
Roadmap: P.2 visual polish
Objective: Add main-display horizontal day swipes and double-tap return-to-now, with a visible accessible NOW action.

## Production boundary

Only `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt`, `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`, `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, and `.codex` cycle/evidence records may change. Standalone prototype only.

## Functional invariants

- Horizontal swipe left advances 24 hours; swipe right goes back 24 hours, preserving time-of-day within the fixture horizon.
- Double-tapping the central forecast summary selects the current fixture hour and shows hourly conditions.
- A visible NOW button provides an accessible route to the same action.
- Existing timeline gestures, bounds, and fixed centered selection remain intact.

## Steps

- [x] Review current timeline, reset behavior, and workflow requirements.
- [x] Implement main-display swipe and double-tap, plus NOW action and hint copy.
- [x] Build/install, exercise gestures, and capture device evidence.
- [x] Close cycle with evidence and limits.

## Acceptance/evidence

- Debug build succeeds and app launches on Pixel 3 XL.
- A left swipe advances the selected date by one day and keeps the hour; a right swipe goes back one day.
- Double-tap and visible NOW action select the current time within fixture bounds.
- Screenshots/log evidence saved under `.codex/test-artifacts/005-main-display-navigation/`.

## Risks / assumptions

“Current time” is device-local time projected into the fixed fixture's horizon and clamped to its available hours. The existing DEMO DATA label remains authoritative.

## Out of scope

No live weather source, provider integration, production navigation, or removal of the current instructional row.
