# Cycle plan — 014-responsive-readability-recovery

State: ACTIVE
Roadmap: P.2.12

## Bounded objective

Provision a supported Android SDK/build environment on this host, build and install the current prototype on the required Pixel 3 XL, and complete the missing installed evidence from P.2.6. This is an evidence and host-setup cycle only; do not change app source, layout, styling, or behavior. Carry the known landscape high/low visibility finding forward to P.2.11.

## Boundary

- Build host: this project environment. Use an SDK installation outside the repository (planned path `/home/opsman/Android/Sdk`) and command-scoped environment variables; do not commit machine-specific paths or SDK files.
- Device: Pixel 3 XL `crosshatch`, serial `8AKY0M32X`, Android 12/API 31. If unavailable, record the exact device result and close BLOCKED without substituting another device.
- App: standalone `com.oxygen.weather.timeline`; no production oxygenWX work.
- Install the SDK command-line tools and packages required by this project's `compileSdk = 37` and Android Gradle Plugin 9.4.0. Current repository wrapper is Gradle 9.7.0. Use a JDK supported by AGP (JDK 17 or later); prefer JDK 17 for the build. Do not change Gradle, AGP, Kotlin, app compile settings, or project dependencies to work around host provisioning.
- Install no emulator; the physical Pixel is the required target.
- No source or behavior changes. If installed review confirms a defect, record it and keep its implementation in a separate roadmap slice.

## Functional and product invariants

- Selected forecast time remains authoritative and centered under the playhead during scale changes; zoom itself must not alter time.
- The bright and dark fixture captures use the exact fixture timestamps `2026-10-25 12:00` and `2026-10-25 23:00`, respectively, and record the selected hour visible in the hourly label.
- Immediate drag remains daily navigation. A stationary hold of about 480 ms followed by drag enters the uniform hourly scale; release/collapse preserves the selected timestamp.
- Essential forecast values remain text; the DEMO DATA label remains visible; source semantics remain unchanged.
- Assess timeline responsiveness, predictable gesture ownership, and readability from actual installed states and interactions. Do not treat a build as visual evidence.
- No tests, lint, contract checks, or app changes are part of this evidence-only cycle.

## Ordered procedure

1. Run `python scripts/dev.py workflow`; confirm this cycle is PLANNED and P.2.12 is the sole NEXT recovery slice. Reconfirm the Pixel serial, model, Android version/API, locale, physical and active viewport/density, and original font, rotation, and animation settings. Record the results.
2. Install Android command-line tools under `/home/opsman/Android/Sdk` from the official Android developer distribution. Configure command-scoped `ANDROID_HOME`, `ANDROID_SDK_ROOT`, `JAVA_HOME`, and `PATH`. Accept required SDK licenses only as needed to install the packages. Install `platforms;android-37`, `build-tools;36.0.0`, and platform-tools if the host adb is inadequate. Record package versions and installation path. Do not create or modify repository `local.properties`.
3. Run `python scripts/dev.py build` with the configured SDK and supported JDK. If it fails, preserve complete command output and stop device review; do not alter project build versions within this cycle.
4. Run `python scripts/dev.py run --serial 8AKY0M32X` to install the newly built debug APK and launch it. Record package version and build identity. Capture fresh portrait baseline, 1.3-font portrait, and 1.3-font landscape states. Recheck essential text and record the already known high/low landscape gap without attempting a fix.
5. Return to portrait and the original font scale. Use direct physical touchscreen interaction on the connected Pixel (or another event sequence proven to preserve a continuous pointer contact) to select `2026-10-25 12:00`. Capture the installed hourly state with its selected-hour label visible and assess the bright sky. Repeat for `2026-10-25 23:00` and the dark sky. Record exact displayed timestamp and sky state for each.
6. Perform and record one immediate daily drag and one stationary hold followed by hourly drag. Capture the hourly label before release and the resulting collapsed state after release. Confirm whether selected time stays at the playhead and whether it remains the same across scale change. Android shell input attempts from cycle 013 failed to establish valid held-contact evidence; do not count another unverified shell gesture as a pass.
7. Review every capture at full resolution. Give all five P.2.6 conditions (default portrait, 1.3 portrait, 1.3 landscape, bright daytime, dark/nighttime) explicit readability dispositions. Give responsiveness, gesture predictability, and time stability evidence-backed dispositions. Keep P.2.11 separate for the known landscape high/low issue.
8. Restore original device font, rotation, and animation settings. Record the SDK/JDK environment left on the host, exact commands/results, artifact paths, any blocker, and follow-up. Inspect `git diff --check` and final diff, then close this recovery cycle. On complete evidence, update P.2.6 to DONE only if its exit is met; otherwise leave P.2.6 BLOCKED with exact reason and evidence.

## Acceptance criteria

- The official SDK command-line tools and required API 37/build-tools packages are present outside the repository, licenses are recorded as accepted where required, and a fresh `python scripts/dev.py build` succeeds with a supported JDK.
- The fresh debug app is installed and launched on the confirmed Pixel 3 XL; app/build identity and actual viewport/density are recorded.
- Actual fresh-app screenshots exist for default portrait, 1.3-font portrait, 1.3-font landscape, exact `2026-10-25 12:00` bright, and exact `2026-10-25 23:00` dark/night states. Hourly labels corroborate both exact time selections.
- The immediate daily drag and stationary hold-then-drag are exercised with real continuous pointer contact. Outcomes for scale ownership, selected-time centering, time continuity through collapse, responsiveness, and readability are recorded with screenshots.
- Original device font/rotation/animation settings are restored and recorded.
- Evidence is stored under `.codex/test-artifacts/014-responsive-readability-recovery/`; README and closed history record exact commands, package/build details, results, limits, and any unresolved blocker.
- No app source/configuration change is made. The known high/low landscape gap remains assigned to P.2.11.

## Authorized verification

- `python scripts/dev.py workflow`
- Read-only host/device inspection and Android SDK package/license inspection.
- `python scripts/dev.py build`
- `python scripts/dev.py run --serial 8AKY0M32X`
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/014-responsive-readability-recovery/<state>.png`
- Manual installed visual review and interactions listed above.
- `git diff --check` and final diff inspection.

No tests, lint, or contract checks are authorized or needed for this evidence-only cycle.

## Risks and assumptions

- AGP 9.4 supports up to API 37 and requires JDK 17 and Gradle 9.6 or later; the repository uses compile SDK 37 and Gradle 9.7.0. SDK package availability and license state must be checked during execution.
- The old installed APK is not sufficient evidence for this recovery; all screenshots must come from the fresh build.
- Android shell input did not reliably simulate a long stationary hold followed by drag in cycle 013. Direct touchscreen interaction or a validated continuous-contact tool is required. If neither is available, record the blocker and do not claim gesture acceptance.
- Large-font landscape currently omits high/low values. This recovery cycle must not fix that defect; P.2.11 owns it.
- Visual acceptance is limited to one Pixel 3 XL and the listed states; it does not establish broad device coverage, TalkBack, RTL, or reduced-motion acceptance.

## Exclusions

- Any app source, layout, styling, or behavior fix, including the P.2.11 high/low landscape issue.
- TalkBack, reduced-motion, RTL, and broader device-matrix acceptance (tracked separately).
- Production oxygenWX changes, integration, external data, and unrelated visual enhancements.

## References

- [Android Gradle Plugin 9.4 release notes](https://developer.android.com/build/releases/agp-9-4-0-release-notes)
- [Android SDK Manager command-line tool](https://developer.android.com/tools/sdkmanager)
