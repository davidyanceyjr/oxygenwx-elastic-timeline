# Cycle 068 evidence — WX.25 browser acceptance

Date: 2026-10-29. Device: Pixel 3 XL `8AKY0M32X`, Android 12
`google/crosshatch/crosshatch:12/SP1A.210812.016.C1/8029091:user/release-keys`.
The installed display was portrait, 1440 × 2960 at 560 dpi. Font scale was
1.0 for the default captures and 1.3 for the large-font captures. Device local
time zone was America/Chicago (CDT/CST). The package was
`com.oxygen.weather.timeline`, version `0.1.0-prototype` (version code 1).
No APK was rebuilt or reinstalled. The app's saved LIVE catalog reported
`open-meteo-forecast-api`, fresh cache, saved 6:14 PM CDT during the large-font
pass. `DISPLAY=:0` was reachable with `xdpyinfo`.

## Verification commands

- `python scripts/dev.py workflow` — PASS; see `workflow.log`.
- `python scripts/dev.py contract` — PASS; see `contract.log`.
- Automated tests — NOT RUN; the active plan expressly disallows them.
- Build/install — NOT RUN; the installed package was already present and no
  fresh APK was needed.
- `git diff --check` — recorded in the cycle history after artifact review.

Installed screenshots were captured with
`python scripts/dev.py screenshot --serial 8AKY0M32X --output <artifact-path>`.
Accessibility hierarchies were captured with `uiautomator dump` and pulled
from the device. TalkBack input was owner-performed on the Pixel: one-finger
swipes moved focus, double-taps activated the focused control, and two-finger
swipes scrolled. ADB pointer input is recorded separately below.

## Acceptance matrix

| Condition | Result | Evidence and observed state |
|---|---|---|
| Browser route, daily selection and checked state | PASS | Owner used TalkBack to open **Daily forecasts**. `talkback-after-daily-navigation.png` and `hierarchy-talkback-after-daily.xml` show the selected Mon, Nov 2 row checked. The live projection showed the noon daily sample separately from the selected time. |
| Browser hourly route, scrolling and dismissal | PASS | Owner activated **Browse fact times**, scrolled the actual fact rows, selected 1:00 PM CST, and activated Done. `talkback-hourly-facts.png`, `talkback-daily-scroll-confirmed.png`, `talkback-1pm-selected-state.png`, and `talkback-return-main.png` retain the states; matching hierarchy XML is beside each capture. The 1 PM row was checked and the main screen returned with selected time and fact sample both 1:00 PM CST. |
| TalkBack selection announcement | PASS, owner-observed | Owner reported: “TalkBack read the correct time from selected hourly row.” The exact spoken phrase was not transcribed. The silent screen recording was discarded; screenshots and hierarchy remain. |
| Browser readability at default font scale | PASS | `talkback-after-daily-navigation.png` and `talkback-hourly-facts.png` show the default-scale browser and readable rows. The large page remains scrollable. |
| Browser readability at 1.3× font scale | PASS | `browser-daily-large-font.png`, `browser-hourly-large-font-direct.png`, and `hierarchy-browser-daily-large-font.xml` / `hierarchy-browser-hourly-large-font.xml`. The LIVE daily view showed selected 6:14:14 PM CDT versus its 6:00 PM fact sample; the Oct 29 row showed a complete 12:00 PM sample at 67°F, high 69°F, low 49°F. The hourly view exposed 12:00 AM onward with wrapped fact text; complete rows fit within the scroll viewport and later rows remain scrollable. `main-large-font-loaded.png` records the 1.3× main screen. |
| Before/after selected-time agreement | PASS | At 1.0×, the hourly dialog showed selected 1:00:00 PM CST and fact sample 1:00 PM CST; after Done, `talkback-return-main.png` shows the same times, 60°F cloudy, 32% precipitation chance, and 6 mph wind. Catalog source stayed LIVE. |
| Main-area tap exits hourly scale without changing selected time | PASS, sampled | `talkback-return-main.png` is the TalkBack return state. `after-main-area-tap.png` shows the main-area tap retaining 1:00 PM CST while restoring daily labels. |
| Daily tape tap | PASS, sampled | ADB tap at `(915,2670)` selected Tue, Nov 3 at its local-noon overview; `daily-tape-tap.png` shows selected and fact sample at 12:00 PM CST, 64°F cloudy. |
| Daily immediate drag and hold/drag arbitration | PASS, sampled | ADB 250 ms tape drag `(900,2670) → (500,2670)` advanced Tue, Nov 3 to Thu, Nov 5 while remaining in daily mode; `daily-tape-drag.png` shows selected 2:04:10 PM CST and 2:00 PM fact sample. A stationary 700 ms hold at `(720,2670)` entered uniform hourly mode without changing selected time; `hourly-hold.png` records 2:04:10 PM. Release left hourly mode active. No frame-time metric was collected. |
| Hourly drag and scene readability | PASS, sampled | ADB 250 ms tape drag `(900,2670) → (500,2670)` in hourly mode changed selection to 8:59:02 PM CST with the 9:00 PM fact sample; `hourly-drag.png` shows a dark clear scene with readable time, facts, and hourly labels. Daylight readability is visible in `daily-tape-tap.png` and browser captures. |
| D (catalog local date) | PASS, prior-cycle evidence | Cycle 066 `final-installed-live-loaded.png` shows Thu, Oct 29, selected 12:09:38 PM CDT, 12:00 PM fact sample, and LIVE values; this is the catalog's D date in the recorded Chicago catalog. |
| D-30 and D+13 horizon | PASS, prior-cycle evidence | Cycle 066 `final-installed-history.png` records D-30, Sep 29; `final-installed-forecast-horizon.png` records D+13, Nov 11, with the final horizon clamp. |
| LIVE / SIMULATED / REPLAY replacement | PASS, prior-cycle evidence | Cycle 066 `README.md` and `installed-latest-source-swap.png`, `installed-latest-replay.png`, and related captures document the three sources, selection preservation, provenance, and values. |
| Sparse, missing and zero values | PASS, prior-cycle evidence | Cycle 066 documents explicit 0% precipitation and unavailable SIMULATED fields. Cycle 067 `simulated-earlier.png` and `simulated-missing-loaded.png` document `Unknown` values and absent wind direction without substituting zero. |
| Day/night scene states | PASS, sampled | `daily-tape-tap.png` shows a daylight cloudy state; `hourly-drag.png` shows a dark clear state. These establish sampled scene readability only. |
| Rain/snow/storm catalog truth | NOT RUN — execution gap | The debug catalog picker and `SimulatedCatalogSource` provide a deterministic canonical 44-day SIMULATED catalog with rain, snow, and storm facts. This source was available but was not activated during this cycle; the prior claim that suitable data was unavailable was incorrect. |
| DST transition and a sparse local date | NOT RUN — execution gap | The SIMULATED Chicago window was anchored in the November 1, 2026 fall DST transition, and the catalog window handles local-date boundaries. The debug catalog picker also prepares a fixed Chicago replay spanning that date. These existing routes/data were not exercised during this cycle; the prior claim that a suitable window was unavailable was incorrect. |
| Compact/landscape, RTL, reduced motion, broad TalkBack suite | NOT RUN / delegated | Outside this cycle's focused condition set; WX.29 owns these checks. |

## Limits and disposition

This cycle produced no application source changes. Contract and workflow
checks passed, and the installed browser path, selected-row agreement, default
and 1.3× portrait readability, and sampled tape gestures are evidenced.
TalkBack's selected-time announcement is an owner observation; no exact speech
transcript was captured. Gesture responsiveness was observed qualitatively;
frame timing and broad arbitration coverage were not measured. Rain/snow/storm
and DST cases were present in the existing SIMULATED/debug catalog path but were
not exercised in this cycle. This is an execution gap, not a data availability
blocker. WX.25 therefore remains NEXT and must not be marked DONE from this cycle.

At the end of review, device settings were restored to font scale 1.0 with
TalkBack disabled, matching the initial settings. The app was returned to its
main timeline.

## Post-close correction

After closeout review, repository inspection found that the debug catalog picker
exposes the deterministic SIMULATED dataset, including rain, snow, storm, and a
Chicago window spanning the November 1 DST transition. The original NOT RUN
rationale incorrectly described these data/routes as unavailable. The states
remain unverified on the installed UI; the reason was that this cycle stayed on
LIVE and did not exercise the existing SIMULATED/debug path.
