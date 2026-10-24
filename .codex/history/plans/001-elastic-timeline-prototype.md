# Bounded plan — P.1 Elastic Timeline Prototype

State: CLOSED (code authoring only; build/visual gate pending)
Scope: independent Compose demo project only. No production oxygenWX changes.

## Visual objective

Small floating 3-letter day rail over an atmospheric Canvas, center playhead. Long-hold expands the targeted day into hourly ticks, with surrounding days compressed; time tape scrolls beneath playhead.

## Functional invariants

- Zoom animation never modifies selected timestamp.
- No live weather claims, official alert claims, or time fabrication beyond fixture.
- Metric source, presentation conversion, one gesture owner.
- 48dp or better touch region, visible alternate precision action.

## Implementation steps

- Replicate upstream Gradle version baseline and Python dev commands in isolated project.
- Add fixture, presentation projection, piecewise elastic time axis, and tests.
- Add Compose atmospheric Canvas and single-pointer-input timeline.
- Document boundaries, demo disclaimer, acceptance limitations.

## Acceptance checks

- Pure Kotlin compile and geometry smoke: PASSED.
- `scripts/dev.py workflow` and `contract`: PASSED.
- Android app build and JUnit tests: UNVERIFIED (toolchain unavailable).
- Installed Pixel 3 XL screenshot/gesture review: UNVERIFIED (device unavailable).
- Large font/RTL/reduced motion/TalkBack: UNVERIFIED.

## Risks

- Interaction gestures and Compose API can require host compilation/refinement.
- Precision mode expansion shifts timeline geometry; real-device hit testing must be checked.
- Existing oxygenWX production pager reserves horizontal gestures; integration deferred.
