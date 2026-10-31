# BT.1 behavior board — Gradle configuration cache

Status: **Product-owner approved on 2026-10-31, with no additional constraints stated.** The visual board is `BT.1-configuration-cache-behavior-board.png`, with editable `BT.1-configuration-cache-behavior-board.svg`. This document is its detailed contract. BT.1 changes no application UI, so no UI artwork board applies.

## Proposed build flow

```text
Repository setting: org.gradle.configuration-cache=true
                  |
                  v
First invocation of a task graph
  configure projects/tasks -> execute -> store configuration entry
                  |
                  v
Same task graph, compatible inputs
  reuse configuration entry -> execute or skip tasks as inputs require
                  |
                  v
Build logic or configuration input changes
  invalidate entry -> configure again -> store new entry

Configuration-cache incompatibility -> fail the build and inspect the report
One-off troubleshooting -> ./gradlew --no-configuration-cache <task>
```

## Scope and acceptance contract

- Change only the root `gradle.properties` by adding `org.gradle.configuration-cache=true`. Preserve existing JVM, AndroidX, Kotlin, and resource settings.
- Keep Gradle's default failure behavior for configuration-cache problems. Do not add `org.gradle.configuration-cache.problems=warn`, suppress reports, change plugin versions, or change app code.
- Verify the repository's `:app:assembleDebug` and `:app:lintDebug` task graphs with a fresh stored entry and a reused entry under the project setting. Capture exact output and any failure report. Do not run automated tests unless separately requested.
- `scripts/dev.py` continues to call the Gradle wrapper; the project property applies to those commands without script changes.
- Cache state remains local under `.gradle/configuration-cache/`; no cache files are committed. No app runtime, forecast, UI, or device behavior changes.

## Baseline evidence available for review

On 2026-10-31, one-off `--configuration-cache` runs on this Gradle 9.7.0 repository stored and reused entries for `:app:assembleDebug` and `:app:lintDebug`. The repeated builds reported `Configuration cache entry reused` and completed in about one second. Those exploratory runs did not change repository settings and do not replace verification after the proposed change.
