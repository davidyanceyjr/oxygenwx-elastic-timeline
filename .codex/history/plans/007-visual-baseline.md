# Cycle plan — 007-visual-baseline

State: COMPLETE
Roadmap: P.2
Objective: Establish a dated installed visual baseline of the current application on the attached Pixel 3 XL across representative execution and timeline states.

## Production boundary

No application source or Compose changes. Add baseline screenshots and a capture record under `.codex/test-artifacts/007-visual-baseline/`, and close this plan into history.

## Functional invariants

- Keep the selected timestamp and timeline interaction semantics unchanged.
- Record what states were actually reached; do not claim unperformed accessibility or visual acceptance.

## Steps

- [x] Confirm workflow, device identity, and baseline app state.
- [x] Build, install, and launch the debug app on the attached Pixel 3 XL.
- [x] Capture default, selected-time, hourly, post-release, and next-day states; landscape was out of scope for this baseline.
- [ ] Record device/build details, capture method, state reproduction notes, and limitations.
- [x] Inspect representative screenshots, run `git diff --check`, and archive cycle.

## Acceptance/evidence

- Screenshots are PNGs from the connected device, stored in `.codex/test-artifacts/007-visual-baseline/`.
- A README records device, app/build identity, capture timestamps, state, and any actions used.
- Build/install/launch success is reported only if observed. This baseline is not full UI acceptance.

## Risks / assumptions

- Device is the attached Pixel 3 XL referenced by the project; verify via ADB.
- Gesture-driven states are reached with ADB input and verified from screenshots.

## Out of scope

No app behavior, code, documentation authority files, fixture, or production integration changes.
