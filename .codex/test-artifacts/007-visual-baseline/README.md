# Installed visual baseline — cycle 007

Capture date: 2026-10-24 (host local time, America/Chicago; approximately 08:13–08:14 CDT)
Source revision: `718f271`
Application: `com.oxygen.weather.timeline`, debug variant, version `0.1.0-prototype` (versionCode 1)
Device: Google Pixel 3 XL (`crosshatch`), Android 12, build `SP1A.210812.016.C1/8029091`
Display: physical 1440×2960; active `wm size` override 1260×2240; density 560 dpi

## Captures

All images are PNG screenshots captured from the installed app with `adb exec-out screencap -p` through `python scripts/dev.py screenshot`. Status and navigation bars are included.

| File | State and reproduction |
| --- | --- |
| `01-default.png` | Fresh launch, fixture's current day overview. |
| `02-selected-day.png` | Tap the bottom time tape at device coordinate `(800, 1890)`; Sunday overview selected. |
| `03-hourly-hold.png` | Start a stationary tape hold at `(630, 1890)` for 5 seconds; capture while the pointer is still down, after hourly scale appears. The image shows the hourly tick rail and selected hour. |
| `04-daily-after-release.png` | Captured after the hold gesture released and the tape returned to daily overview. |
| `05-main-swipe-next-day.png` | Swipe left across the central forecast display from `(800, 1050)` to `(420, 1050)`; next day selected. |

Coordinates are device pixels at the recorded active display override. Reapply that override before replaying the coordinates if the device configuration changes.

## Follow-up capture

`06-hourly-long-press.png` was captured later on the same Pixel 3 XL by simulating a stationary 5-second hold at `(630, 1890)` with `adb shell input swipe ...`, then running the screenshot command while the simulated touch was still down. The image was inspected and shows the hourly rail with the `12 PM` selection.

## Run record

- `python scripts/dev.py workflow` — passed.
- `python scripts/dev.py contract` — passed.
- `python scripts/dev.py run --serial 8AKY0M32X` — debug build succeeded, APK installed, and `MainActivity` launched.
- The host default Java was 8 and initially prevented Gradle from starting. The successful invocation used `/usr/lib/jvm/java-27-openjdk` and Android SDK `/home/opsman/project_git/oxygenWX/.android-sdk` via command-scoped `JAVA_HOME`, `PATH`, and `ANDROID_HOME`; no project SDK configuration was written.
- `adb` confirmed the app's `MainActivity` was in the foreground after launch.

## Limits

This is a dated visual record at the device's current display override. The captures do not establish large-font, landscape, RTL, reduced-motion, TalkBack, or full gesture reliability acceptance. No application source was changed. The repository had pre-existing edits in `docs/SPECIFICATION.md` and `docs/ROADMAP.md`; they were left untouched.
