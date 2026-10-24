# Cycle 001 — Elastic timeline standalone prototype

Date: 2026-10-23
Status: AUTHORING CLOSED; DEVICE/ANDROID BUILD EVIDENCE OUTSTANDING
Roadmap: P.1
Origin: oxygenWX commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`

## Built

- Independent Android application/Gradle project (same version baseline; distinct app ID).
- Deterministic multi-day demo forecast, metric domain, separate presentation model.
- Full-bleed Canvas atmosphere and small bottom three-letter time tape.
- Fixed playhead; compressed day browsing; long-press focus lens; hour scrubbing.
- No-op security surface: no INTERNET or LOCATION permissions, no production access.
- Dedicated pure axis and JUnit tests plus host developer command and documentation.

## Invariants

- Original oxygenWX repo unmodified; product four-page Home pager not rewritten.
- No fabricated live forecast or official alerts; demo status conspicuous.
- Time/zoom independent; selected hour remains centered throughout expansion.

## Verification in authoring environment

- `kotlinc` compilation of the pure data/presentation source: PASSED.
- Python `workflow` and `contract`: see local check results, rerun on receiving host.
- Gradle Android build, JUnit, lint: NOT RUN (Android SDK/Gradle toolchain unavailable).
- Installed emulator screenshots, physical-device visual/gesture/a11y checks: NOT RUN.
- No claim of Android build or visual acceptance.

## Owner follow-up

Run `python3 scripts/dev.py check`, then `python3 scripts/dev.py run` on an Android host. Capture installed evidence. Any actual integration with production navigation requires separate approval.
