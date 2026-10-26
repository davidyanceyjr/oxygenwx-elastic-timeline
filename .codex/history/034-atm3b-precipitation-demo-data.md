# Cycle 034-atm3b-precipitation-demo-data

Date: 2026-10-25
Roadmap: ATM.3b

## Outcome

Added deterministic explicit hourly RAIN, SNOW, and NONE identity and phase-tagged nullable amounts; kept cloud-derived base sky category separate from the legacy scene-renderer condition; projected categorical fields from the nearest actual hour and preserved representative-hour daily overview. Changed the visible chance label to PRECIP CHANCE. Installed Pixel 3 XL hourly rain and snow captures show selected time, condition, chance, and DEMO DATA. ATM.3b exit is met; precipitation artwork quality remains ATM.4 scope.

## Verification actually performed

Passed: python scripts/dev.py workflow; python scripts/dev.py contract; JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk python scripts/dev.py build; same configured environment python scripts/dev.py run --serial 8AKY0M32X; python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/034-atm3b-precipitation-demo-data/rain-oct23-12-hourly.png; same screenshot command with output .codex/test-artifacts/034-atm3b-precipitation-demo-data/snow-oct26-00-hourly.png; git diff --check. The initial build with default Java 8 failed because Gradle 9.7 requires Java 17+; retry with Java 27 but without SDK environment failed to locate the SDK; configured build and run then passed. Contract confirmed isolated package, no network/location permission, and pure time projection. Source review and fixture-to-projection table are in .codex/test-artifacts/034-atm3b-precipitation-demo-data/README.md. Unit tests and lint were not run per repository instructions. Device evidence is limited to one Pixel 3 XL in portrait at default font, en-US; RTL, reduced motion, large font, TalkBack, landscape, other devices, and precipitation rendering quality were not checked.

## Follow-up

No ATM.3b acceptance gaps remain. ATM.4 stays BLOCKED until a new bounded attempt meets its visible precipitation-artwork exit. This cycle supplies the typed mock inputs for that attempt; no precipitation-renderer quality was assessed here.
