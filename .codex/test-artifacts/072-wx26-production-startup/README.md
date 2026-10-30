# WX.26 installed verification — 072-wx26-production-startup

Date: 2026-10-30
App: `com.oxygen.weather.timeline`, `0.1.0-prototype` (debug)
Device: Pixel 3 XL, serial `8AKY0M32X`, Android 12 / API 31, 1440×2960 at 560 dpi
Primary device user: user 0; selected location at end of review: Miami, `America/New_York`
Review conditions restored: font scale 1.0, animator duration scale 1.0, Force RTL off; network restored.

## Verification

- **PASS — first launch without selection:** temporary Android user 10 showed “No active forecast catalog” and “Choose location” with no weather values (`first-launch.png`, `first-launch-hierarchy.xml`). Denver search/save was done in that temporary user; user 10 was removed after review.
- **PASS — fresh LIVE activation and location switch with matching cache:** user 0 switched Chicago → Miami. During loading the selector identified Miami; afterward Miami’s own LIVE catalog appeared, with Miami name and EDT facts (`location-switch.*`, `miami-active.*`). The displayed selected instant retained the same absolute time across the zone change (8:08:33 CDT → 9:08:33 EDT).
- **PASS — same-location refresh failure with active catalog:** with network disabled, Miami remained the active catalog and retained its delivery/freshness; the screen separately reported `refresh transport failure` and “Refresh failed; showing the last active catalog.” (`offline-refresh-with-cache.*`). Network was restored afterward.
- **PASS — no-cache request failure and retry recovery:** in temporary user 10, Denver was saved online, then selected offline. The resulting screen showed the selected place, transport failure and retry, without forecast values (`no-cache-failure.*`). After restoring network, Retry activated Denver LIVE data (`retry-recovery.*`).
- **PASS — active A → uncached B switch:** in a fresh temporary user 10, Chicago was activated from LIVE and Denver was saved without selecting it. With Wi-Fi and mobile data disabled, selecting Denver changed the target to Denver and showed the loading state in the selector; after closing it, the no-cache screen showed `transport_failure`, Denver, and Retry, with no forecast values or Chicago facts (`uncached-switch-chicago-active.*`, `uncached-switch-denver-loading.*`). With connectivity restored, Retry activated Denver LIVE data (`uncached-switch-denver-retry.*`).
- **PASS — saved-location and offline relaunch:** user 0 relaunched to its saved Miami selection with matching data/cache and retrieval context (`saved-location-relaunch.*`, `offline-launch-with-cache.*`).
- **PASS — successful foreground refresh:** tapping Refresh online completed and retained Miami LIVE/source/fresh status (`foreground-refresh-success.*`).
- **PASS — 1.3× font scale:** the post-fix header displays “Refresh” and “Place” on one line; forecast values and timeline labels remain visible (`large-font-1_3.*`).
- **PASS — RTL review:** Android Force RTL was enabled using Developer options. The timeline date order and the location selector’s action order mirrored in the installed app (`rtl-review.*`, `rtl-selector.*`). The switch was turned off afterward and the device configuration verified.
- **PASS — reduced-motion disposition:** screenshot captured with animator duration scale 0; state and forecast meaning remain expressed in text (`reduced-motion.png`). Animator scale restored to 1.0.
- **PASS — final install:** `python scripts/dev.py run --serial 8AKY0M32X` completed build, streamed install, and started `MainActivity`.
- **PASS — current normal system display:** `DISPLAY=:0` was available for the concept review. The connected Pixel remained unlocked for installed checks.

## Additional checks not run

- **NOT RUN — selector dismissal preserving selected instant and scale:** opening/dismissing without choosing a location was not isolated as a before/after comparison.
- **NOT RUN — former-view-generation callback after B activation:** no runtime stale-callback injection was performed. Code inspection confirms `selectInstant()` returns unless the callback generation equals the currently active generation; no automated tests were run under the plan’s authorization boundary.
- **NOT RUN — broad TalkBack, orientation, performance and compact-viewport suites:** outside this slice’s scope.

## Commands and limits

- `python scripts/dev.py workflow` — PASS; cycle remained ACTIVE at time of capture.
- `python scripts/dev.py contract` — PASS: transport contract, isolated package, approved Internet only, no location/background/cleartext/release debug exposure, pure projection.
- `python scripts/dev.py build` — PASS (`BUILD SUCCESSFUL`; all 37 tasks up-to-date).
- `python scripts/dev.py run --serial 8AKY0M32X` — PASS; streamed install succeeded and activity launched.
- `git diff --check` — PASS at the review snapshot before this evidence note was added; repeat at closeout.
- Automated tests — NOT RUN, as required by the plan and repository instructions absent separate authorization.
- Temporary Android user 10 was removed after the A → B run. User 0 remains the only Android user; network and review settings are restored. A preliminary user 0 attempt to save Denver before choosing the temporary-user route did add Denver to its saved-location list; Miami remained selected. The device returned to user 0's PIN screen, so Denver could not be removed or the final saved-location list rechecked. The saved-Miami/offline-relaunch evidence was captured before that preliminary attempt.

This evidence records installed behavior and limits; it does not claim full application or accessibility acceptance. Roadmap WX.26 flows are covered. Selector-dismissal state retention and runtime stale-callback injection remain unverified; automated tests were not run under the current authorization boundary.
