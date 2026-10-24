# Cycle 002 — P.1 Pixel 3 XL install gate

Date: 2026-10-23
Status: CLOSED — app built, installed, launched, and captured
Roadmap: P.1

## Result

- Built the debug APK successfully with OpenJDK 27 and the Android SDK at `/home/opsman/project_git/oxygenWX/.android-sdk`.
- Installed and launched `com.oxygen.weather.timeline/.MainActivity` on Pixel 3 XL (`8AKY0M32X`).
- Captured and inspected `.codex/test-artifacts/002-p1-pixel-install/pixel-3xl-launch.png`; the standalone app is displayed.
- Device reported physical size 1440x2960 and active override 1260x2240 at density 560.

The first build attempt used the shell's default Java 8 and failed because Gradle requires Java 17+. Rerunning with the installed OpenJDK 27 completed successfully.

## Limits

This is a successful build/install/launch check with a first-run screenshot. Timeline gestures and accessibility behavior were not exercised, so full P.1 visual/device acceptance remains open.
