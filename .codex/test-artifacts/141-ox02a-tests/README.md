# OX-02A-TESTS execution evidence

Cycle: `141-ox02a-tests`

Slice: `OX-02A-TESTS` (duplicate acceptance cycle for OX-02A)

## Device and route setup

- Device: Pixel 3 XL, serial 8AKY0M32X.
- Build/install: `python3 scripts/dev.py run --serial 8AKY0M32X` — BUILD SUCCESSFUL; installed and launched the debug variant.
- Entered the existing debug session with the location search command #DEBUG and used the REPLAY picker for Florence, Alabama (geonames:4062577; America/Chicago). Existing SG.4 and OX01F debug replays were used; no fixture or app source was changed.
- Each composition/read pair used the same replay ID and selected instant. The supported-rain composition capture was made from the already active #DEBUG session with the sg2_null_weather extra absent; the OX01F and carried composition captures used fresh MainActivity launches without the extra. All reading captures used fresh launches with `--ez sg2_null_weather true`. The debug activation intent selected the exact instant. After activation, the debug picker was dismissed with `adb -s 8AKY0M32X shell input keyevent 4` before capture.
- Route-state correction: sg2_null_weather is sampled when MainActivity is created and remains set for that activity instance. Reopening #DEBUG does not reset it. The OX01F and carried-condition composition captures and all reading captures followed fresh MainActivity launches; their composition launches omitted the extra and reading launches set it to true. The supported-rain composition capture was taken in the already active composition session, before entering the reading route through a fresh launch.

The activation intent used the existing debug action and extras below. These are the concrete invocations for each paired route; the supported-rain composition request was sent from the active `#DEBUG` session with `-f 0x24000000`, while fresh launches were preceded by `force-stop`. Composition omitted the scene-null extra; reading included it. After activation opened the picker, it was dismissed with `adb -s 8AKY0M32X shell input keyevent 4`.

```sh
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG -f 0x24000000 --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id sg4-canonical-gaps-v8 --es debug_catalog_replay_id sg4-canonical-gaps-v8 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-04T15:30:00Z
adb -s 8AKY0M32X shell am force-stop com.oxygen.weather.timeline
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id sg4-canonical-gaps-v8 --es debug_catalog_replay_id sg4-canonical-gaps-v8 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-04T15:30:00Z --ez sg2_null_weather true
adb -s 8AKY0M32X shell am force-stop com.oxygen.weather.timeline
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id ox01f-condition-unavailable-typed-v1 --es debug_catalog_replay_id ox01f-condition-unavailable-typed-v1 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-28T20:00:00Z
adb -s 8AKY0M32X shell am force-stop com.oxygen.weather.timeline
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id ox01f-condition-unavailable-typed-v1 --es debug_catalog_replay_id ox01f-condition-unavailable-typed-v1 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-28T20:00:00Z --ez sg2_null_weather true
adb -s 8AKY0M32X shell am force-stop com.oxygen.weather.timeline
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id sg4-canonical-gaps-v8 --es debug_catalog_replay_id sg4-canonical-gaps-v8 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-04T16:30:00Z
adb -s 8AKY0M32X shell am force-stop com.oxygen.weather.timeline
adb -s 8AKY0M32X shell am start -n com.oxygen.weather.timeline/.MainActivity -a com.oxygen.weather.timeline.DEBUG_ACTIVATE_CATALOG --ei debug_catalog_version 1 --es debug_catalog_location_id geonames:4062577 --es debug_catalog_origin REPLAY --es debug_catalog_dataset_id sg4-canonical-gaps-v8 --es debug_catalog_replay_id sg4-canonical-gaps-v8 --ez debug_catalog_force_refresh false --es debug_catalog_selected_instant 2026-10-04T16:30:00Z --ez sg2_null_weather true
```

Screenshots were taken from the installed UI with the following command, once per output path: `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/141-ox02a-tests/<name>.png`. The seven actual outputs are `supported-composition.png`, `supported-reading.png`, `unavailable-composition.png`, `unavailable-reading.png`, `carried-composition.png`, `carried-reading.png`, and `reading-interaction.png`.

For each PNG above, UIAutomator was dumped from that same screen using `adb -s 8AKY0M32X shell uiautomator dump /sdcard/<name>.xml`, then copied using `adb -s 8AKY0M32X shell cat /sdcard/<name>.xml > .codex/test-artifacts/141-ox02a-tests/<name>.xml`. The `<name>` values are the seven basenames listed above.

## Matched route results

| State and replay | Selected instant | Composition route | Reading fallback route |
|---|---|---|---|
| Supported rain; sg4-canonical-gaps-v8 | 2026-10-04T15:30:00Z (10:30 AM CDT) | 57°F, rain from 10:00 AM CDT, 75% precipitation chance, 0 mph wind. Rain streaks are visible. Capture: supported-composition.png; dump: supported-composition.xml. | Same selected instant and displayed facts. Rain streaks are absent. Capture: supported-reading.png; dump: supported-reading.xml. |
| Condition unavailable; ox01f-condition-unavailable-typed-v1 | 2026-10-28T20:00:00Z (3:00 PM CDT) | CONDITION_UNAVAILABLE diagnostic says “No supplied weather event.” Readout: condition unavailable, 48°F, 25% precipitation chance, 0 mph wind; apparent temperature and humidity unavailable. Capture: unavailable-composition.png; dump: unavailable-composition.xml. | Same selected instant and supplied facts; condition remains unavailable. Capture: unavailable-reading.png; dump: unavailable-reading.xml. |
| Eligible carried condition; sg4-canonical-gaps-v8 | 2026-10-04T16:30:00Z (11:30 AM CDT) | Readout says rain and “Condition from 10:00 AM CDT; selected hour unavailable.” Temperature, precipitation chance, wind, and humidity are unavailable. Rain streaks are visible. Capture: carried-composition.png; dump: carried-composition.xml. | Same condition, source time, and unavailable independent fields. Rain streaks are absent. Capture: carried-reading.png; dump: carried-reading.xml. |

The cold-start paired captures show “Selected location” in the location title. The in-session supported composition reference shows “Florence.” The debug activation location ID and selected America/Chicago zone remain the same; the title difference is recorded as a debug-startup presentation difference, not treated as forecast evidence.

## Reading-route interaction

The reading-route timeline was directly dragged on the supported-rain state. Before: 10:30:00 AM CDT, 2026-10-04. Gesture: `adb -s 8AKY0M32X shell input swipe 720 2550 760 2550 500`. After: 8:54:14 AM CDT; the selected-time readout changed and the displayed fact sample was 9:00 AM CDT. Capture: reading-interaction.png; dump: reading-interaction.xml. This confirms the reading route accepted the timeline gesture and updated its selected-time presentation. The selected instant changed as directed by the drag.

## State-only route caller recheck

Command: `rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java app/src/androidTest`.

The production MainActivity call uses the presentation overload. The only view-only overload caller found is CatalogForecastBrowserErrorAccessibilityTest, which supplies scene = null. No production scene-present state-only caller or debug trigger was found. Retain that fallback and leave its scene-present runtime appearance unverified, as planned. The Android test was source-inspected only and was not run.

## Verification and limits

- `python3 scripts/dev.py run --serial 8AKY0M32X` — PASS; debug build succeeded, installed, and launched on the connected device.
- `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output …` — PASS for all six matched captures and the reading interaction capture; paths are listed above.
- `python scripts/dev.py workflow` — PASS after activation; final closeout validation is recorded in cycle history.
- `git diff --check` — final result recorded in cycle history.
- No app test suite, lint, TalkBack, RTL, localization, or broad device/environment matrix was run. Only the connected Pixel 3 XL debug path was exercised.
- No production behavior changed and no new debug fixture was needed. The observed supported-rain and carried-condition scene mismatch is evidence only; this test slice does not authorize renderer changes or consolidation.
