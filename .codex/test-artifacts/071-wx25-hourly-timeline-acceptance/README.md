# Cycle 071 evidence — hourly timeline acceptance

This directory records the completed installed WX.25 acceptance for the Chicago SIMULATED v3 catalog. The owner-approved SIMULATED completeness amendment is part of this cycle: every synthetic hourly fact has precipitation chance and sustained wind speed, filling only missing anchor values with explicit zero and preserving non-null anchors. UI behavior for displaying zero remains a future roadmap item.

## Device and catalog

- Device: Pixel 3 XL, serial `8AKY0M32X`, Android 12, portrait 1440×2960 at density 560, display on, Chicago device timezone.
- App: `com.oxygen.weather.timeline`, debug build `0.1.0-prototype` (`oxygen-wx10-simulated`); the installed picker showed active `wx22-debug-simulated-v3` for Chicago (`geonames:4887398`).
- Catalog reference: `asOf=2026-10-30T00:12:51.483794Z` (2026-10-29 7:12:51 PM CDT), giving the fixed local range Sep 29–Nov 11, 2026 (D−30 through D+13).
- Font scale began and ended at 1.0. Accessibility began and ended disabled (`accessibility_enabled=0`, enabled services `null`). A temporary, non-speaking helper service invoked the timeline's accessibility `ACTION_CLICK` to open the browser; it was disabled and uninstalled immediately after each invocation. TalkBack remained disabled and was not evaluated.

## SIMULATED v3 completeness

- `data-completeness-v3.txt` records a read-only installed database audit: SIMULATED v3 and derived REPLAY v3 each contain 1,057 hourly facts across 44 dates (43 ordinary 24-hour dates and the 25-instant Nov 1 fall-back date). SIMULATED chance and sustained-wind null counts are zero; missing anchor values are explicit zero and the 48 preexisting non-null anchor values remain unchanged.
- The installed picker, source identity, and initial main state are captured in `data-v3-installed-picker.png` / `.xml` and `fresh-main-default.png` / `.xml`.
- Earlier installed timeline captures cover clear, partly cloudy, haze, rain, snow, storm, and missing-value cases: `daily-haze-window`, `daily-rain-window`, `daily-snow-window`, and `daily-scrub`. The fresh Oct 30 and Nov 11 checkpoints show partly cloudy and clear facts, respectively. SIMULATED chance/wind values remain separate from condition/phenomenon labels.

## Timeline and browser acceptance

- The daily browser begins at Sep 29 (`fresh-browser-daily-begin`), includes a middle checkpoint (`fresh-browser-daily-mid`), exposes Oct 30–Nov 2 around the DST boundary (`fresh-browser-daily-dst`), and ends at Nov 9–11 (`fresh-browser-daily-end`). Selecting Nov 11 chose its local-noon overview; tapping Done returned the main screen to `12:00 PM CST` with the same fact sample (`fresh-browser-selected-end`, `fresh-main-selected-end`).
- The ordinary Oct 30 hourly browser exposes the beginning at midnight and the end at 11 PM, with intermediate rows (`fresh-browser-hourly-ordinary-begin`, `fresh-browser-hourly-ordinary`, `fresh-browser-hourly-ordinary-end`). Selecting 11 PM and tapping Done preserved `11:00 PM CDT` and its fact sample (`fresh-browser-hourly-selected-end`, `fresh-main-after-hourly`).
- The Nov 1 fall-back hourly browser shows its beginning, middle, and end (`fresh-browser-hourly-dst-begin`, `fresh-browser-hourly-dst`, `fresh-browser-hourly-dst-end`). It exposes two separate `1:00 AM` rows with offsets `CDT` and `CST`; selecting each updated the selected and fact-sample label to that offset (`fresh-browser-hourly-dst-selected-cdt.xml`, `fresh-browser-hourly-dst-selected-cst.png` / `.xml`). These correspond to 2026-11-01T06:00Z and 2026-11-01T07:00Z in `America/Chicago`. The inventory count and first/last rows confirm 25 real hourly instants for that date.
- Direct timeline interaction captured a daily tap to Nov 10 (`fresh-main-daily-tap`), an immediate daily drag to Nov 11 at 9:00:13 AM CST (`fresh-main-daily-drag`), hold activation with the selected timestamp unchanged and hourly ticks visible (`fresh-main-hourly-hold`), and hourly dragging to 10:27:25 AM CST with the fact sample at 10 AM (`fresh-main-hourly-drag`). Release left hourly scale active. A main-area tap exited hourly scale without changing the selected instant (`fresh-main-hourly-exit`); a timeline tap returned to daily noon (`fresh-main-return-daily`).
- Exact D−30 and D+13 clamps are captured in `horizon-start.png` / `.xml` and `horizon-end.png` / `.xml`; both are at the actual fixture bounds (Sep 29 12:00 AM CDT and Nov 11 11:59:59 PM CST, whose final fact sample is 11 PM). The attempted end clamp also appears in `horizon-end-clamp.xml`.
- The accessibility action opened “Daily forecasts” with the selected timestamp and fact sample visible. `fresh-browser-daily-selected.png` / `.xml` records the default-scale dialog. A 1.3 font-scale portrait main state is in `main-font-1.3.png` / `.xml`; fresh main and browser captures at 1.3 are `fresh-main-font-1.3.png` / `.xml` and `fresh-browser-font-1.3.png` / `.xml`. Browser rows wrap and remain scrollable; no row text was clipped in the inspected visible states.
- Gesture input responded to each command without a stuck or dual-owned gesture. Selected-time and sampled-fact labels agreed at every recorded checkpoint. Qualitative observation only; no frame-time or throughput claim is made.

## Accessibility and limits

- The owner cancelled TalkBack acceptance for this cycle. `talkback-check-status.txt` and the older TalkBack probe files are historical only and are not counted as passes. The temporary service invoked one accessibility node action without speech; this verifies the browser entry action and content, not TalkBack speech, traversal, or usability.
- System-wide accessibility, RTL, orientation, reduced-motion, compact viewport, and UI-performance matrices were not run; they remain outside WX.25. No automated tests were added or run, per repository instructions.

## Verification commands and outcomes

- `python scripts/dev.py workflow` — PASS (before device interaction and at closeout).
- `python scripts/dev.py contract` — PASS.
- `python scripts/dev.py build` and `python scripts/dev.py run --serial 8AKY0M32X` — PASS in the earlier cycle execution; debug APK installed on the Pixel 3 XL.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output <artifact>` — PASS for installed screenshots in this directory.
- Read-only installed catalog audit — PASS; details in `data-completeness-v3.txt`.
- `git diff --check` — PASS at closeout.
- Automated tests — NOT RUN, as directed by the active plan and repository instruction.
