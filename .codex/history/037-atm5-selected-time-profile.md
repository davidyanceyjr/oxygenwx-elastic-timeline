# Cycle 037-atm5-selected-time-profile

Date: 2026-10-25
Roadmap: ATM.5

## Outcome

COMPLETED ATM.5. Added renderer-only haze and turbulence channels driven by the authoritative selectedHour with the six specified synthetic samples, linear fractional interpolation, endpoint clamping, and explicit preview-mode gating. Haze progressively blends only the distant ridge toward the scene horizon color within the 0.30 × haze-strength cap; no veil was needed. Turbulence remains symmetric, non-directional, and static. Corrected cloud placement to keep cloud silhouettes inside the viewport while preserving deterministic selected-time movement. Installed Pixel 3 XL review confirmed labeled preview and no-preview states, readable facts/timeline, bounded cloud artwork at horizon samples and during scrubbing, and unchanged selected time on precision entry. Cycle 036 remains recorded as an incomplete predecessor; cycle 037 supplies the missing ATM.5 acceptance evidence.

## Verification actually performed

PASS: `python scripts/dev.py workflow` (Workflow scaffold: OK). PASS: `python scripts/dev.py contract`. PASS: `export JAVA_HOME=/usr/lib/jvm/java-27-openjdk ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk; export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$PATH"; python scripts/dev.py build` (`:app:assembleDebug`). PASS: `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualStateMapperTest` (8 focused tests; initial exact-double midpoint assertion was changed to a `1e-12` tolerance, then rerun successfully). PASS: Pixel 3 XL serial `8AKY0M32X`, Android 12, native portrait 1440x2960 at density 560, font scale 1.0, en-US/LTR; no-preview, haze low/mid/high/fractional/endpoints, turbulence, combined, reduced-motion, and hold/scrub captures are in `.codex/test-artifacts/037-atm5-selected-time-profile/`. The original 1260x2240 display override was restored; animator duration scale was restored to 1. PASS: `git diff --check`. RTL, TalkBack, large-font, landscape, other-device, frame-time, and quantitative responsiveness checks were not run; none are claimed.

## Follow-up

ATM.5 is DONE. ATM.6 is NEXT. The unrun cross-app accessibility/layout checks are outside ATM.5's bounded exit and are not claims of acceptance.
