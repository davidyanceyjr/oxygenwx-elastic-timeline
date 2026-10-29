# Cycle 062-wx22-debug-source-picker

Date: 2026-10-28
Roadmap: WX.22

## Outcome

Completed WX.22 in cycle 062. The debug picker now selects location-scoped LIVE, SIMULATED, and stored REPLAY catalogs through the single MainActivity-owned coordinator. Request decoding, activation routing, relay, fixture search provider, and picker references are supplied only by the debug ActivityVariantDelegate service provider; the release variant contains no provider. The installed LIVE failure was fixed by moving response consumption, closure, and JSON parsing onto Dispatchers.IO without changing request construction. Chicago and Miami source activation, cold launch, rejected-request recovery, in-process source changes, all three legacy profiles, and timeline scrubbing were observed on the Pixel. Evidence and limitations are recorded in .codex/test-artifacts/062-wx22-debug-source-picker/README.md.

## Verification actually performed

Passed: python scripts/dev.py workflow; python scripts/dev.py contract; python scripts/dev.py build; python scripts/dev.py run --serial 8AKY0M32X; ./gradlew :app:assembleRelease; git diff --check. Release artifact inspection passed: merged manifest contains no debug activity; release DEX has no debug picker/profile/relay/delegate class definitions, request action/extra strings, or replay fixture; no debug ActivityVariantDelegate service resource exists in release. Debug APK contains the provider registration for CatalogPickerDelegate. Installed Pixel evidence: LIVE, SIMULATED, REPLAY, and temporary Miami SIMULATED activation; source switches kept process PID 8886 and persisted selected_location_id stayed null; cold explicit request succeeded; malformed version 9 was rejected without a load, retained the previous active catalog, and a later valid request recovered. All existing 7/11/14-day profile entries launched, and a horizontal timeline swipe changed the selected date. Picker captures cover default font, 1.3x font, RTL, and 720x1280 compact width. No tests were added or run, per repository instruction. Limitations: the startup-restore race was not forced in an installed run; TalkBack and reduced-motion checks were not run.

## Follow-up

The in-flight startup-restore race was not forced in an installed run; stale-result rejection continues to rely on the existing coordinator sequence/cancellation path. TalkBack was not checked. Reduced motion is not applicable to the static picker; the existing timeline motion behavior was not re-accepted in this cycle. These are recorded limitations; the WX.22 roadmap exit was met.
