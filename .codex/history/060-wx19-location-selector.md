# Cycle 060-wx19-location-selector

Date: 2026-10-28
Roadmap: WX.19

## Outcome

WX.19 selector implemented, installed and release-gated; focused flows and persistence verified. WX.20 is the sole NEXT slice.

## Verification actually performed

- `python scripts/dev.py workflow` — passed.
- `python scripts/dev.py contract` — passed; no release debug exposure or location/background/cleartext permission.
- `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.ui.LocationSelectorControllerTest` — passed; 9 tests, 0 failures.
- `python scripts/dev.py build` — passed.
- `python scripts/dev.py run --serial 8AKY0M32X` — passed; installed and launched on Pixel 3 XL, Android 12/API 31.
- `./gradlew :app:assembleRelease` — passed, including lint-vital.
- Release APK inspection — no debug selector activity in release manifest and no Springfield debug fixture strings in release DEX.
- `git diff --check` — passed.

Evidence and command logs: `.codex/test-artifacts/060-wx19-location-selector/README.md`.

## Follow-up

No WX.19 exit gaps remain. WX.20 is now the sole `NEXT` slice. The release-visible main-screen selector route remains owner-planned and outside WX.19. TalkBack audio/traversal, RTL, landscape, and frame-time profiling were not run; the broader accessibility/environment matrix remains assigned to WX.29.
