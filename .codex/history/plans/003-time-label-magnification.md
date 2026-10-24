# Cycle plan — 003-time-label-magnification

State: CLOSED
Roadmap: P.2 visual polish
Objective: Replace the localized focus-day expansion with a uniform hourly scale in precision mode and make the selected day/hour label noticeably larger than neighboring labels.

## Production boundary

Only `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`, `app/src/main/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxis.kt`, the governing specification/roadmap, and `.codex` cycle/evidence records may change. This standalone application only.

## Functional invariants

- The selected timestamp remains authoritative and centered during scale changes.
- Daily navigation uses equal day spacing. Precision mode uses equal hourly spacing across the visible forecast, without a localized focus window.
- The selected day/hour label is larger than neighboring labels.
- Hold-to-enter, drag, tap, day/hour bounds, and accessible HOURS/DAYS control remain available.

## Steps

- [x] Review timeline code and installed baseline capture.
- [x] Implement uniform mode scale and selected-label typography.
- [x] Build/install/capture on Pixel 3 XL and inspect result.
- [x] Close cycle with evidence and limitations.

## Acceptance/evidence

- Debug build succeeds, app launches on Pixel 3 XL, and a screenshot is saved under `.codex/test-artifacts/003-time-label-magnification/`.
- Screenshot visibly shows one larger selected label with regular neighboring labels; no localized day-width focus window in precision mode.
- No claim of touch/accessibility suite acceptance without exercising those paths.

## Risks / assumptions

Assume “regular | regular | SELECTED | regular | regular” refers to timeline labels. Precision mode will use a uniform hourly spacing across the tape; typography distinguishes the chosen hour.

## Out of scope

No changes to weather data, selection semantics, outer navigation, accessibility architecture, or production oxygenWX.
