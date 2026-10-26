# DFM.3 execution evidence

## Device and scope

- Device: Pixel 3 XL (`8AKY0M32X`, `crosshatch`), Android physical size 1440×2960, active override 1260×2240, density 560.
- The debug APK was installed on this device. Profile tooling was launched with `adb shell am start -n com.oxygen.weather.timeline/.data.debug.DebugForecastActivity --es debug_profile <RAIN_3_DAY|STORM_7_DAY|COLD_SNOW_10_DAY>`.
- Per owner direction, no application-wide test suite was run. One focused DFM.3 unit-test class was added and run. TalkBack, RTL, and large-font acceptance are deferred to their dedicated cycles.
- TalkBack was briefly enabled only while identifying the device accessibility tooling; no app navigation or acceptance check was performed with it. The original setting keys were deleted afterward. The final device reports no enabled accessibility services, `enabled_accessibility_services=null`, and `accessibility_enabled=0`.

## Verification results

| Command | Result |
|---|---|
| `python scripts/dev.py workflow` | PASS — `Workflow scaffold: OK`; cycle remains `041-dfm3-debug-forecast-matrix-browser`, ACTIVE. |
| `python scripts/dev.py contract` | PASS — isolated package, no network/location permission, pure time projection. |
| `ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.debug.DebugForecastProfilesTest` | PASS — focused profile/source tests only; 4 tests cover profile lengths/buffers/reference, exact hourly facts and unknown values, daily noon/extrema semantics, and snow/warning type mapping. |
| `ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH ./gradlew :app:assembleDebug :app:assembleRelease :app:lintDebug` | PASS — `BUILD SUCCESSFUL`. |
| `ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH python scripts/dev.py lint` | PASS — `BUILD SUCCESSFUL`. |
| `git diff --check` | PASS — no whitespace errors. |
| Release artifact inspection | PASS — [`release-isolation.txt`](release-isolation.txt) records manifest activity declarations and scans all DEX files; no debug profile/catalog/activity identifiers found. |

The first focused-test compilation attempt exposed a test-only receiver typo; it was corrected, then the exact filtered test command above passed. A lint helper invocation without the Java environment failed because the shell defaulted to Java 8; rerunning with Java 27 passed.

## Installed interaction observations

- Each profile launches at its declared synthetic reference sample. Reference screenshots confirm the storm profile at clear conditions, the rain profile, and the cold/snow profile; all retain `DEMO DATA` and the synthetic-profile provenance label.
- The 14-day cold/snow profile daily timeline was swiped to both bounds. Its accessible timeline description reported `THU, OCT 1` at the first day and `WED, OCT 14` at the last day. This verifies day-level source clamping on device; exact hourly end indices are covered by the focused source bounds and sample tests.
- The 7-day rain profile reported `THU, OCT 1` and `WED, OCT 7` at the first and last daily bounds. The 11-day storm profile reported `THU, OCT 1` and `SUN, OCT 11`. Both were swiped in both directions to the loaded edges.
- Installed captures from earlier in this cycle show the cold/snow daily tap, hold-to-enter-hourly and drag, release preserving the selected 1 PM label, and a long immediate drag. Storm captures show the storm entry, 90% thunderstorm sample/warning period, and condition transition states. Filenames are indexed below.
- The storm profile was moved from its reference day, then double-tapped; its timeline returned to `SAT, OCT 3, 12 PM`, the declared profile reference sample.
- The normal `MainActivity` was double-tapped to invoke its return-to-now path. The device clock reported `Mon Oct 26 09:51:24 CDT 2026`; the timeline description then reported `MON, OCT 26, 9 AM`, consistent with its existing wall-clock reset behavior.
- The immersive scene retains readable forecast facts and the debug profile label. No profile-browser control appears in the immersive screen; selection stays in the debug activity tooling path.

## Capture index

- `rain_3_day-reference-final.png`, `storm_7_day-reference-final.png`, `cold_snow_10_day-reference-final.png` — installed profile reference states.
- `rain_3_day-start-scrub.png`, `rain_3_day-end-scrub.png`, `storm_7_day-start-scrub.png`, `storm_7_day-end-scrub.png` — installed first/last daily bounds for the shorter profiles.
- `cold_snow_10_day-start-scrub.png`, `cold_snow_10_day-end-scrub.png` — installed first and last daily bounds.
- `cold_snow_10_day-tap-monday.png` — daily selection at the noon point.
- `cold_snow_10_day-hold-hourly.png`, `cold_snow_10_day-hold-release.png` — hold-to-expand, drag, and release preserving selected time.
- `cold_snow_10_day-immediate-drag-long.png` — immediate drag across multiple days.
- `storm_7_day-storm-entry.png`, `storm_7_day-high-thunderstorm-warning.png`, `storm_7_day-warning-preview.png`, `storm_7_day-partly.png`, `storm_7_day-cloudy.png`, `storm_7_day-storm.png` — storm progression, typed thunderstorm/warning samples, and transition states.
- `storm_7_day-return-to-reference.png` — debug profile double-tap reset.
- `main-normal-return-to-now.png` — normal fixture after double-tap return-to-now.
- `release-isolation.txt` — release manifest and DEX inspection.

## Deferred and unverified

- TalkBack interaction/accessibility acceptance, RTL, and large-font behavior are intentionally deferred to their own cycles. The separate TalkBack/RTL/font work is not represented as verified here.
- Reduced-motion acceptance was not exercised in this cycle.
- Installed landscape/compact-layout acceptance was not exercised.
- Profile bounds were manually checked at daily precision on the 14-day profile. Hourly values at every valid timestamp and exact projection fidelity are checked by the focused unit tests; each hourly endpoint was not separately selected by hand.
- No application-wide unit suite or instrumentation suite was run.
