# Cycle 035-atm4-bounded-precipitation

Date: 2026-10-25
Roadmap: ATM.4

## Outcome

Completed the ATM.4 retry. Typed hourly rain and snow now render clearly visible, deterministic precipitation. Chance controls stepped particle coverage independently; explicit RainLiquidMm changes illustrative streak weight/length, while missing amount remains null with a baseline illustration. Explicit typed snow draws distinct flakes. Paired installed Pixel 3 XL screenshots show absent/present precipitation, representative chance and amount cases, missing amount, snow, and reduced-motion static behavior. Main-screen rain leaves forecast facts and the timeline readable. Cycle 033 remains recorded as rejected prior evidence.

## Verification actually performed

PASS: python scripts/dev.py workflow. PASS: python scripts/dev.py contract. PASS: JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:/home/opsman/Android/Sdk/platform-tools:$PATH ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk python scripts/dev.py build (:app:assembleDebug). PASS: installed and launched on Pixel 3 XL serial 8AKY0M32X; screenshots and state observations are in .codex/test-artifacts/035-atm4-bounded-precipitation/README.md. PASS: git diff --check. Unit tests and lint not run per repository instructions. RTL, TalkBack, large font, landscape, other devices, frame-time measurements, and formal quantitative responsiveness checks were not run; these are not claimed.

## Follow-up

ATM.4 is DONE. ATM.5 is now the roadmap NEXT slice. Separate follow-up cycles retain RTL, TalkBack, large-font, landscape, additional-device, and deeper responsiveness/performance checks; no failure in those areas was observed in the scoped ATM.4 review, but they were not verified here. Cycle 033 remains a rejected attempt and is superseded by the cycle-035 retry evidence for ATM.4.
