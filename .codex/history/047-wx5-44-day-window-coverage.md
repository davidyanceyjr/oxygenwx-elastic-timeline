# Cycle 047-wx5-44-day-window-coverage

Date: 2026-10-27
Roadmap: WX.5

## Outcome

Completed WX.5 within its approved boundary. Added immutable, provider-neutral 44-local-date window and CatalogDay coverage helpers; caller-supplied expected support; typed normalization errors; stable sort and identical-duplicate collapse; field/day COMPLETE/PARTIAL/MISSING coverage; provenance-separated provider daily amounts; and complete-support derivation for daily rain, total precipitation, and snowfall only. The D+13 boundary exception admits accumulated-only facts ending at D+14 midnight when intervals lie wholly within D+13; point facts there remain rejected. CanonicalCatalog, CanonicalWeatherFacts, provider, persistence, fixture, presentation, UI, and renderer code were not changed. Evidence: .codex/test-artifacts/047-wx5-44-day-window-coverage/README.md.

## Verification actually performed

PASS: python scripts/dev.py workflow. PASS: python scripts/dev.py contract. PASS: JAVA_HOME=/usr/lib/jvm/java-27-openjdk PATH=/usr/lib/jvm/java-27-openjdk/bin:$PATH ANDROID_HOME=/home/opsman/Android/Sdk ANDROID_SDK_ROOT=/home/opsman/Android/Sdk ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.catalog.CatalogWindowContractTest --tests com.oxygen.weather.timeline.data.catalog.CanonicalCatalogContractTest (16 tests: 8 WX.5, 8 WX.3; zero failures/errors). PASS: git diff --check and untracked-file whitespace scan. NOT RUN: full suite, separate build, lint, install/device/accessibility/font/RTL/reduced-motion review; data-only slice and plan authorizes focused tests only. Gradle was run with installed JDK 27 and Android SDK paths because shell defaults were Java 8 and unset Android SDK.

## Follow-up

No WX.5 exit remains open. Provider-specific expected-support profiles and fixture adapters remain in later WX slices. The full unit suite was not run under this bounded plan; the unrelated existing time-label assertion failure is recorded in cycle 045. Device and accessibility checks are not applicable to this data-only slice.
