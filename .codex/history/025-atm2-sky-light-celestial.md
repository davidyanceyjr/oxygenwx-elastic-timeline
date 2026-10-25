# Cycle 025-atm2-sky-light-celestial

Date: 2026-10-25
Roadmap: ATM.2

## Outcome

Implemented the bounded ATM.2 sky palette, diffuse light, and illustrative sun/moon layers using SceneVisualState. The existing cloud, landscape, and rain draw functions, their inputs, and their relative order were preserved. Installed Pixel 3 XL captures cover all six fixture review anchors plus daily/hourly, large-font landscape, forced RTL, reduced-motion static rendering, and the gesture/release review. The first installed sun marker overlapped the selected date; it was moved into the upper sky and reduced in size before the final evidence set. ATM.2 exit met; no production, forecast, timeline, gesture-owner, or accessibility-semantics changes.

## Verification actually performed

Passed:

- `python scripts/dev.py workflow` — scaffold/current-cycle pointer valid.
- `python scripts/dev.py contract` — standalone package, no network/location permission, pure time projection.
- `JAVA_HOME=/usr/lib/jvm/java-27-openjdk ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk PATH=/usr/lib/jvm/java-27-openjdk/bin:/home/opsman/Android/Sdk/platform-tools:$PATH python scripts/dev.py build` — `BUILD SUCCESSFUL`.
- Same host environment with `python scripts/dev.py lint` — `BUILD SUCCESSFUL`.
- `python scripts/dev.py run --serial 8AKY0M32X` — build, install, and launch succeeded.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output <capture-path>` — actual screenshots saved under `.codex/test-artifacts/025-atm2-sky-light-celestial/`.
- `git diff --check` — passed before closeout.

The initial build without host overrides failed because the shell used Java 8. The retry with Java 27 and the installed Android SDK passed. No tests were added or run, per the plan and `AGENTS.md`.

## Follow-up

The bounded ATM.2 exit is met. The evidence is qualitative: no frame-time, battery, or measured gesture-latency profile was collected. Touch review used ADB touchscreen event injection rather than physical-finger input. TalkBack traversal was not retested because ATM.2 added no semantics or controls. RTL used forced layout direction with `en-US`, not an RTL language locale. The Pixel 3 XL's existing 1260×2240 / 560 dpi display override was retained, and the landscape capture includes a black left-edge inset. Full evidence and state-specific details are in `.codex/test-artifacts/025-atm2-sky-light-celestial/README.md`.
