# Cycle 066 evidence — WX.25 catalog binding

Date: 2026-10-29. Device: Pixel 3 XL `8AKY0M32X`, Android 12 build
`google/crosshatch/crosshatch:12/SP1A.210812.016.C1/8029091:user/release-keys`.
Installed review used portrait and system font scale 1.0.

## Command results

- `python scripts/dev.py workflow` — PASS; output in `workflow.log`.
- `python scripts/dev.py contract` — PASS; output in `contract.log`.
- `python scripts/dev.py build` — PASS; output in `build.log`. An earlier build failed on missing `Column` import and `BoxWithConstraints` receiver errors; both were corrected before this successful build.
- `python scripts/dev.py run --serial 8AKY0M32X` — PASS; installed and launched the final debug APK; output in `run.log`.
- `git diff --check` — PASS after final edits.
- Focused unit tests — NOT RUN. The user instruction for this repository says not to add or run tests unless asked, and the current request did not ask for them. The plan's focused test exit is therefore unmet.

## Installed observations

- `final-installed-live-loaded.png`: Chicago LIVE catalog loaded from cache. Selected 12:09:38 PM CDT is distinct from the 12:00 PM CDT fact sample. The displayed 71 °F, 3% precipitation chance, and 9 mph wind agree with the installed recording's 12:00 local fact: 21.6 °C, 3%, and 14.9 km/h. Daily high/low 72/50 °F agree with the recording's 22/10 °C daily summary.
- `installed-latest-source-swap.png` and `installed-latest-replay.png`: LIVE → SIMULATED → REPLAY through the debug picker kept the selected local 12:03 PM instant in range while replacing source context and facts. The SIMULATED view showed `DEMO DATA`, 50 °F from its 9:00 AM 10 °C sample, and unavailable precipitation/wind for null fields. Its derived high/low 50/45 °F match its 10/7 °C facts. The replay view retained the synthetic product meaning and identified replay origin.
- `final-installed-history.png`: the tape reached D-30 (September 29), labeled `Archived model forecast`; selected 3:16:22 PM CDT and fact sample 3:00 PM CDT remained distinct. An explicit 0% precipitation chance was displayed as `0%`.
- `final-installed-forecast-horizon.png`: the tape reached D+13 (November 11) and clamped at the final local date. It showed selected 11:59:59 PM CST and the separate 11:00 PM CST fact sample. The tape did not admit November 12.
- `installed-location-selector.png` shows the existing saved-location selector. The text visible in that screenshot was stale in the intermediate APK; the final code corrects it. `installed-initial.png` and `installed-after-unlock.png` are lock-screen captures, not app evidence.
- The current catalog tape remained responsive during repeated ADB drags. No measured latency or manual tap/hold/drag arbitration result is claimed.

The read-only recording comparison copied the device's SQLite files to a temporary host directory, inspected only the cited Chicago fields, and removed the temporary copy. The recording itself is not retained here.

## Unmet installed acceptance

- Accessible browser day/hour activation and spoken output: NOT RUN to completion. TalkBack was enabled temporarily, and the installed timeline exposed its browser action in the accessibility hierarchy. ADB pointer input did not activate that accessibility action, so browser content, selection, spoken fact time, and custom scale/browse actions are unverified. The device's original settings (`enabled_accessibility_services=null`, `accessibility_enabled=0`) were restored.
- 1.3× font, compact/landscape, RTL, and reduced motion: NOT RUN. The wider WX.29 matrix remains separate, but the local WX.25 dispositions are still required by this plan.
- Sparse/missing periods, an installed 25-hour local date, scene states for snow/storm, and the full responsiveness/gesture/readability matrix: NOT RUN or only sampled as described above. Build and screenshots do not establish those passes.

WX.25 does not meet its complete exit. The data boundary implementation is present, but the roadmap slice must not be marked DONE from this evidence.
