# Cycle 069 — WX.25 functional timeline acceptance

**Disposition:** INCOMPLETE. The installed debug path proved that the SIMULATED catalog and its weather events were available, correcting cycle 068's unsupported statement that these cases were unavailable. The owner then selected full hourly coverage for each of the 44 local dates, which is not supplied by the current two-facts-per-date fixture and is explicitly outside this cycle's approved scope. No application source was changed.

## Environment and catalog

- Device: Pixel 3 XL, serial `8AKY0M32X`, Android 12, app `com.oxygen.weather.timeline` v0.1.0-prototype, debuggable.
- Timezone: America/Chicago. Font scale observed at 1.0. TalkBack was enabled for the owner-operated browser observations and later observed disabled; final state should be reconfirmed before further manual acceptance.
- Catalog: SIMULATED, location Chicago (`geonames:4887398`), dataset `wx22-debug-simulated-v1`, source `oxygen-wx10-simulated`.
- The catalog is anchored at `2026-10-29T04:11:14.308714Z`, which is October 28 local time. It covers September 28 through November 10, 2026, exactly 44 local dates, with 88 fact instants (9:00 AM and 9:00 PM per date). November 1 is a 25-hour Chicago local date.
- Read-only device database inspection confirmed all 44 local dates and all encoded event families are present. Rain, snow, haze, and fog were also observed on the installed main screen or browser. The event inventory and source are recorded in the active plan and inspected source `SimulatedCatalogSource.kt`.

## Installed observations

- Activated SIMULATED from the installed debug catalog picker and opened the immersive timeline. Captures: `simulated-active-picker.png`, `simulated-main-initial.png`.
- Observed event checkpoints on the installed timeline: rain on October 3 (`simulated-rain-checkpoint.png`); haze around October 12 (`simulated-haze-checkpoint.png`); snow on October 21 (`simulated-snow-oct21.png`).
- Owner used TalkBack to open the daily and hourly browser on October 3. The hourly browser exposed two rows, 9:00 AM and 9:00 PM, with rain facts. The owner selected 9:00 AM; the hierarchy confirms the selected row and the main-screen selected/sample times. Captures and hierarchies are `talkback-sim-daily-browser.png`, `talkback-hourly-rain-oct3.png`, and `talkback-hourly-rain-oct3-selected.png` and corresponding `hierarchy-*.xml` files.
- The prior cycle 068 supplies installed evidence for daily/hourly scale gestures, browser scrolling, and 1.3x font samples on its then-active data path. It does not establish complete SIMULATED event coverage or hourly density.

## Not verified in this cycle

- Browser scrolling through the full 44-date SIMULATED day list and all hourly facts, because the source currently has only two fact rows per date.
- Installed timeline scrubbing across all event families, exact selected/sample-time agreement at every event checkpoint, or DST repeated-hour navigation.
- Exact spoken TalkBack announcement of the selected hourly time in this run; the screenshot and hierarchy establish semantic selection, not spoken output.
- Installed font-scale 1.3 SIMULATED states, day/night sample coverage, responsiveness measurement, and full gesture-ownership matrix.
- Automated tests, build, or install were not run under the active plan's authorization boundary.

## Follow-up

The owner chose one fact for every real local hour on all 44 local dates, including both repeated hours at fall-back and no nonexistent hour at spring-forward, with deterministic within-day event windows. This requires a separate bounded fixture/data-contract slice and owner-approved data-shape concept before implementation. Keep WX.25 open for subsequent installed timeline/browser acceptance after the denser catalog is available.
