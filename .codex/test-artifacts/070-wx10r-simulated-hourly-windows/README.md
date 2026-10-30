# Cycle 070 — WX.10R hourly SIMULATED catalog

**Disposition:** Data-slice acceptance passed. The deterministic SIMULATED and source-derived debug REPLAY products now contain a fact at every real local top-of-hour instant on all 44 local dates. Timeline/browser interaction acceptance remains WX.25 and is not claimed here.

## Build and installed environment

- Device: Pixel 3 XL, serial `8AKY0M32X`, Android 12, 1440×2960 at density 560, timezone `America/Chicago`.
- Package: `com.oxygen.weather.timeline`, v0.1.0-prototype, debuggable. Font scale 1.0; TalkBack disabled and unchanged during this data cycle.
- `python scripts/dev.py run --serial 8AKY0M32X`: BUILD SUCCESSFUL; installed and started the debug app.
- `python scripts/dev.py contract`: PASS.
- Installed debug picker activated `SIMULATED · geonames:4887398 · wx22-debug-simulated-v2`; main screen selected 6:50 PM CDT and showed `Fact sample 7:00 PM CDT`, confirming the installed source resolves to the new hourly point. Captures: `installed-picker-v2.png`, `installed-sim-v2-active.png`, `installed-main-v2.png`; corresponding source/main hierarchies are `.xml`.
- The debug picker also generated and stored `wx22-chicago-replay-v2` at fixed `asOf=2026-10-28T12:00:00Z`, captured origin SIMULATED. Both new v2 records passed the source's `CatalogWindow` validation before storage and were inspected read-only from the installed Room database.

## Catalog inventory

- Active SIMULATED `asOf=2026-10-29T23:51:01.354872Z` is October 29 in Chicago; window September 29 through November 11, exactly 44 local dates.
- The database has 1,057 unique fact instants: 24 for each normal date and 25 on November 1, 2026. The read-only audit reconstructed the expected instant grid from each local midnight to the next, stepping by one elapsed hour; every stored fact matched that grid and landed on a top-of-hour instant.
- The condition inventory includes CLEAR, PARTLY_CLOUDY, CLOUDY, RAIN, SNOW, and STORM. Phenomena include HAZE, FOG, FREEZING_RAIN, and ICE_PELLETS. Date-by-date presence is in `simulated-catalog-inventory.txt` and `replay-catalog-inventory.txt`.
- Comparing old sparse SIMULATED v1 with new v2 by relative date and local anchor hour, all 88 original 9 AM/9 PM canonical values were preserved (`anchor_values_preserved=88/88`). Missing fields remain null and explicit zeroes remain encoded as values.
- The active SIMULATED recording has 479 one-hour accumulated intervals; REPLAY v2 has 480. Every recorded accumulation interval is one hour and its support date equals the Chicago local date containing the complete interval, including intervals ending at midnight. The first window-edge midnight amount is intentionally unsupported because its interval begins outside the 44-date horizon.
- Product identity is `fixed-44-day-hourly-sequence-v2`; source ID remains `oxygen-wx10-simulated`. Dataset IDs changed to `wx22-debug-simulated-v2` and `wx22-chicago-replay-v2`, isolating previous 88-fact records.
- `dst-grid-check.txt` is a standalone audit of the approved instant-stepping rule: Chicago March 8, 2026 yields 23 instants and no 2 AM; November 1 yields 25 and two distinct 1 AM instants; an ordinary date yields 24. The currently installed 44-day window includes the fall transition, not the spring transition.

## Verification and limits

- PASS: `python scripts/dev.py workflow`.
- PASS: `python scripts/dev.py contract`.
- PASS: `python scripts/dev.py run --serial 8AKY0M32X` (debug build, install, launch).
- PASS: `git diff --check` and `git diff --cached --check`.
- PASS: read-only installed database inventory for both v2 catalogs, real-hour membership, date counts, anchor parity, condition/phenomenon inventory, and accumulation support.
- NOT RUN: automated tests, per the repository instruction and the owner's lack of a specific test request. Spring-forward behavior was audited from the instant-grid rule and Chicago timezone rules, not from an installed spring-dated catalog.
- Not covered: full browser scroll through all 44 days/24-hour rows, TalkBack spoken row/time, full timeline scrub checkpoints, font scale 1.3, night readability, responsiveness measurements, RTL, or reduced motion. WX.25/WX.29 retain these checks.

## Commands

Exact verification commands and results: `verification.txt`. The raw temporary Room database copy was used read-only to produce compact inventory artifacts and removed afterward; no raw database was kept in the repository.
