# Cycle 038-atm6-storm-night-refinements

Date: 2026-10-25
Roadmap: ATM.6

## Outcome

Completed ATM.6. Added restrained storm and night palette/cloud treatments to the existing abstract landscape; night scenes have no celestial marker. Added debug-only, visibly labeled storm and clear-night scene previews that preserve the selected fixture time and forecast facts. The clear-night preview is supplemental because real clear-night samples exist at offsets 43 through 47. The fixture contains no typed storm sample. Updated the ATM.6 roadmap record to cite this cycle evidence.

## Verification actually performed

PASS: python scripts/dev.py workflow (Workflow scaffold: OK; cycle 038 ACTIVE before close). PASS: python scripts/dev.py contract (isolated package, no network/location permission, pure time projection). PASS: ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.atmosphere.SceneVisualStateMapperTest. PASS: python scripts/dev.py build (:app:assembleDebug). PASS: python scripts/dev.py run --serial 8AKY0M32X. PASS: Pixel 3 XL screenshots at native portrait 1440x2960, density 560, font scale 1.0, en-US/LTR: clear-night-fixture.png, clear-night-preview.png, nonstorm-night.png, storm-night.png, storm-night-reduced-motion.png, storm-day.png under .codex/test-artifacts/038-atm6-storm-night-refinements/. Animator duration scale was set to 0 for the reduced-motion capture and restored to 1; prior display override 1260x2240 was restored after native-resolution captures. PASS: git diff --check. A first Gradle invocation without JAVA_HOME selected Java 8 and failed the Gradle JVM requirement; rerunning with JAVA_HOME=/usr/lib/jvm/java-27-openjdk and Android SDK environment succeeded. Debug fixture inventory: 192 hours; RAIN 50, PARTLY_CLOUDY 108, CLEAR 30, CLOUDY 4, STORM 0; clear-night offsets 43-47. Existing debug routes for preview_hour, preview_snow, and ATM.5 haze/turbulence/combined are documented in the artifact README.

## Follow-up

ATM.7 is the next roadmap slice. No ATM.6 exit criteria remain open. This cycle does not claim RTL, large-font, TalkBack, landscape, additional-device, gesture-session, or measured frame-time acceptance; those checks were outside this bounded slice. Live provider integration remains a separate future task, while typed canonical storm conditions now select the storm scene treatment.
