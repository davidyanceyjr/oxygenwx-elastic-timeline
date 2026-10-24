# Cycle plan — 004-subtle-playhead

State: CLOSED
Roadmap: P.2 visual polish
Objective: Replace the long centered timeline playhead line with a small, low-intrusion selection marker.

## Production boundary

Only `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt` and `.codex` cycle/evidence records may change. Standalone prototype only.

## Functional invariants

- The selected timestamp remains mapped to the center of the tape.
- Daily and hourly selected labels stay centered and magnified.
- Tap, drag, hold-to-scrub, HOURS/DAYS access, and time bounds do not change.
- Replace the full-height playhead and top circle with a subtle axis marker.

## Steps

- [x] Scope and baseline inspection.
- [x] Replace the long line with a small marker.
- [x] Build/install/capture on Pixel 3 XL and inspect.
- [x] Close cycle with evidence and limits.

## Acceptance/evidence

- Debug build succeeds, app launches on Pixel 3 XL, and screenshot is saved in `.codex/test-artifacts/004-subtle-playhead/`.
- Screenshot confirms only a small marker remains at the centered selected position.

## Risks / assumptions

A small dot centered on the axis is enough to read as the selected position because the selected day/hour label is already centered and enlarged.

## Out of scope

No gesture, data, accessibility, outer navigation, or production oxygenWX changes.
