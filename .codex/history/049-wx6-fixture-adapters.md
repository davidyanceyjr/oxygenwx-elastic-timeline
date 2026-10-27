# Cycle 049-wx6-fixture-adapters

Date: 2026-10-27
Roadmap: WX.6

## Outcome

Implemented pure canonical fixture adapters for the normal sample forecast, debug scenario catalog and transition sequences, and generated debug profiles. Preserved legacy fixture identities and values in typed adapter metadata, used strict explicit-zone conversion and synthetic provenance, and kept debug adapters in the debug source set. Sequence IDs are derived from existing names per the owner decision. WX.6 exit met; no app wiring or fixture/runtime changes were made.

## Verification actually performed

- `python scripts/dev.py workflow` — passed before work, during final review, and after closeout.
- `python scripts/dev.py contract` — passed.
- `JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:$PATH ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.catalog.SampleForecastCatalogAdapterTest --tests com.oxygen.weather.timeline.data.debug.DebugFixtureCatalogAdapterTest` — passed, five focused tests, zero failures.
- `JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:$PATH python scripts/dev.py build` — passed (`:app:assembleDebug`).
- `JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:$PATH ./gradlew :app:assembleRelease` — passed.
- Release dex/source-set inspection — passed; debug adapters are in `app/src/debug/`, and no matching debug fixture/adapter names were found in release dex.
- `git diff --check` — passed.

Evidence and exact Gradle output are in `.codex/test-artifacts/049-wx6-fixture-adapters/`, including focused test XML reports and release isolation inspection.

## Follow-up

No WX.6 exit gaps remain. The broad unit suite was not run, as excluded by the plan. Device and visual checks do not apply to this data-only slice.
