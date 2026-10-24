# Cycle plan — 010-android-host-checks

State: DONE
Roadmap: P.1.3
Objective: Run Android debug unit tests and lint on an Android host; record exact outcomes and diagnose failures without expanding into code fixes.

## Production boundary

Verification and failure diagnosis only. Do not modify application source, Gradle configuration, or dependencies in this cycle. If a project change is needed, record a bounded follow-up slice before implementation.

## Invariants

- Preserve the isolated prototype boundary and existing user edits.
- Report the exact command and result for each task; distinguish host/toolchain failures from project test or lint findings.
- Do not claim P.1.3 complete unless both tasks pass.

## Steps

- [x] Confirm Android host/toolchain and record Java/SDK versions and repository status.
- [x] Run `python scripts/dev.py test` with the configured Android host environment; this invokes `:app:testDebugUnitTest`.
- [x] Run `python scripts/dev.py lint` with the same environment; this invokes `:app:lintDebug`.
- [x] For each failure, retain complete relevant output and diagnose only enough to identify host/toolchain versus project scope; do not fix source here. Both tasks passed; lint emitted five non-fatal warnings recorded in the evidence README.
- [x] Review evidence and `git diff --check`; close with exact outcomes and limitations.

## Acceptance/evidence

- Target evidence directory: `.codex/test-artifacts/010-android-host-checks/`.
- Record both command outputs, Java version, Android SDK path/version, and repository status in the evidence README.
- Exit is met only if both tasks pass, or a reproducible blocker/failure with exact output and affected scope is recorded for replanning. Unexplained failures do not close the slice as DONE.
- Target closeout: `.codex/history/010-android-host-checks.md`.

## Authorized verification

- `python scripts/dev.py workflow` before execution and after closeout-record updates.
- `python scripts/dev.py test`, then `python scripts/dev.py lint`, using the command-scoped Java/Android SDK environment below.
- `git diff --check` before closeout.
- Do not run the aggregate `check` or build tasks; this slice authorizes only the two roadmap tasks plus workflow validation and diff hygiene.

## Host setup

The previous successful Android build used OpenJDK 27 at `/usr/lib/jvm/java-27-openjdk` and the SDK at `/home/opsman/project_git/oxygenWX/.android-sdk`. For execution, set command-scoped `JAVA_HOME`, prepend its `bin` to `PATH`, and set `ANDROID_HOME` to that SDK. Confirm both paths still exist before running tasks; do not write `local.properties` or alter project configuration just to encode this host setup.

For later visual confirmations, the connected Pixel 3 XL is the target device and the X display is `:0`. This cycle does not authorize device install/capture or visual acceptance; its only Android tasks are unit tests and lint.

## Risks / assumptions

- This host has the same Android SDK available as during cycle 007; versions may have changed and must be recorded.
- Unit tests and lint are independent Gradle tasks but can share the configured Gradle daemon; run them sequentially so each result is attributable.

## Out of scope

No app build beyond task prerequisites, device install/capture, UI acceptance, source fixes, dependency updates, or production oxygenWX changes.
