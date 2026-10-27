# WX.2 evidence — 044-wx2-baseline-inventory

## Scope and disposition

This is a source-grounded, read-only inventory and regression baseline of the local successor app. No application source, fixture, test, build configuration, or deprecated oxygenWX file was changed. No app tests were added or run. At execution, exact-tuple misses without explicit profile-relative intent were conservatively classified as potential discrepancies. In a post-closeout decision on 2026-10-26, the product owner accepted the four rain/snow cases as intentional illustrative catalog variants and confirmed the isolated warning preview as an intentional independent case. Their final classification is `catalog-only by design`; details are in `owner-disposition.md`.

## Artifacts

- `inventory.md` records source paths and line anchors, all twelve catalog/profile comparisons, compatibility owners/dispositions, current mapping/time behavior, existing checks, and historical limitations.
- `baseline.md` records the installed normal-sample snapshot, exact commands/results, device state, and an optional reproducible procedure. The screenshot is a baseline snapshot, not visual or interaction acceptance.
- `owner-disposition.md` records the product owner's post-closeout decision on all five initially unmatched catalog rows.
- `installed-normal-baseline.png` is a fresh debug-build capture from the connected Pixel 3 XL. It shows the normal `SampleForecast` path and its visible `DEMO DATA` label.
- `.codex/concepts/WX.2-baseline-inventory.png` and `.svg` remain the product-owner-approved WX.2 current/target board (approved 2026-10-26). The catalog is inventory-only, dashed target paths are future direction, WX.3+ remains conceptual, and the board is not installed acceptance evidence.

## Verification and limitations

- `python scripts/dev.py workflow` — PASS before activation, during execution, and after closeout (`Workflow scaffold: OK`).
- `python scripts/dev.py run --serial 8AKY0M32X` using the shell's default JVM — FAIL before Gradle tasks because Gradle 9.7 requires JVM 17+ and the default is JVM 8.
- The same `run` command with `JAVA_HOME=/usr/lib/jvm/java-27-openjdk`, matching `PATH`, `ANDROID_HOME=/home/opsman/Android/Sdk`, and `ANDROID_SDK_ROOT=/home/opsman/Android/Sdk` — PASS; `:app:assembleDebug` completed and streamed install succeeded.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/044-wx2-baseline-inventory/installed-normal-baseline.png` — PASS.
- Device facts at capture: Pixel 3 XL (`8AKY0M32X`), Android 12, display override 1260x2240, physical panel 1440x2960, density 560 dpi; package `com.oxygen.weather.timeline`, versionCode 1. The capture was taken after installing the current debug APK and launching `MainActivity`.
- `git diff --check` — PASS after evidence and roadmap updates; final tracked diff was reviewed in the cycle closeout.
- NOT RUN: app tests, contract, lint, release build, accessibility/TalkBack, RTL, large-font, reduced-motion, and gesture acceptance. These are not required for this read-only baseline; the single installed screenshot does not prove them.
- Historical-only: cycle 040 records one failing test assertion (expected `DAY OVERVIEW`; actual `DAY OVERVIEW · SELECTED 12 PM`) in `ElasticTimeAxisTest.fixtureIsCompleteChronologicalAndPresentationIsSeparated`. This cycle did not rerun it. Historical Pixel evidence in cycle 041 remains historical and is not current acceptance.

Owner decisions for all five exact-tuple misses are recorded in `owner-disposition.md`; the audit now has no open catalog/profile disposition. The separate historical cycle-040 test failure remains a verification follow-up. No source behavior was changed.
