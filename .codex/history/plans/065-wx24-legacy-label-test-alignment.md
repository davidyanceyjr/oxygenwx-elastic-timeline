# Cycle plan — 065-wx24-legacy-label-test-alignment

Date: 2026-10-29
State: CLOSED DONE
Roadmap: WX.24
Objective: Correct the stale legacy day-label assertion to match current projection output, then complete the WX.24 verification in a new bounded cycle.

## Owner-approved direction

On 2026-10-29, after cycle 064 closed incomplete, the owner selected the resolution to update the stale test expectation to the current projection label and retry WX.24 in a new cycle.

The WX.24 concept board remains owner-approved as of 2026-10-29 at `.codex/concepts/WX.24-absolute-axis.png` (editable source `.codex/concepts/WX.24-absolute-axis.svg`). It continues to govern the absolute-instant API and DST/calendar behavior. This cycle does not change product or interface behavior.

## Verified baseline

- `ForecastProjection.fromHourOffset(36.0, false)` formats the selected daily label as `DAY OVERVIEW · SELECTED 12 PM` at `ForecastProjection.kt:60`.
- `ElasticTimeAxisTest.fixtureIsCompleteChronologicalAndPresentationIsSeparated` asserts the older value `DAY OVERVIEW` at line 60.
- Cycle 064 added `InstantTimeAxis` and seven tests; those tests pass. Its combined focused command failed only at this mismatched assertion. The evidence is `.codex/test-artifacts/064-wx24-absolute-axis/focused-tests.txt`.

## Production boundary

Allowed change:

- `app/src/test/java/com/oxygen/weather/timeline/presentation/ElasticTimeAxisTest.kt` — change only the expected daily label to the current full projection label.
- `.codex/test-artifacts/065-wx24-legacy-label-test-alignment/` — actual verification logs and README.
- `.codex/plans/065-wx24-legacy-label-test-alignment.md`, `.codex/history/065-wx24-legacy-label-test-alignment.md`, and `docs/ROADMAP.md` — cycle and status records.

Do not change production source, existing runtime labels, UI, gestures, `ElasticTimeAxis`, or the absolute-time API from cycle 064. If actual output differs from the recorded current projection, stop and record the discrepancy instead of changing runtime code.

## Functional invariants

- The test reflects the already implemented projection output; this cycle makes no user-visible behavior change.
- Existing fixture axis mapping, bounds, selected-time projection, and interaction ownership stay unchanged.
- All cycle 064 absolute-time semantics and tests remain intact.
- WX.24 is DONE only if the combined `InstantTimeAxisTest` and `ElasticTimeAxisTest` command passes along with workflow, contract, build, and diff checks.

## Steps

1. Reconfirm the test expectation and current `ForecastProjection` label at the recorded source location.
2. Update only the expected string in `ElasticTimeAxisTest` to `DAY OVERVIEW · SELECTED 12 PM`.
3. Run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the prescribed combined focused Gradle command, `python scripts/dev.py build`, and `git diff --check`; preserve exact results.
4. Close WX.24 as DONE only if every required command passes. Otherwise record the exact blocker and keep WX.24 blocked.

## Acceptance and verification

Required commands:

- `python scripts/dev.py workflow`
- `python scripts/dev.py contract`
- `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.presentation.InstantTimeAxisTest' --tests 'com.oxygen.weather.timeline.presentation.ElasticTimeAxisTest'`
- `python scripts/dev.py build`
- `git diff --check`

The combined test command must pass all tests in both classes, including the updated legacy label assertion. Preserve actual output in `.codex/test-artifacts/065-wx24-legacy-label-test-alignment/`. Installed/device acceptance is not required or claimed because this cycle changes only a test expectation and the cycle 064 API is pure presentation logic.

## Risks and limitations

- The updated expected string is tied to current `ForecastProjection` presentation behavior; if that behavior intentionally changes later, update the test in that bounded change.
- A passing unit test/build does not constitute installed or accessibility acceptance. WX.24 itself does not require those checks; WX.25/WX.29 own integrated installed evidence.
- Cycle 064 remains closed and incomplete. Cycle 065 is the bounded retry and does not rewrite cycle 064 history.

## Out of scope

- Any runtime/UI label changes or gesture changes.
- Changes to cycle 064 production API or its tests.
- Timeline/catalog integration, device installation, screenshots, TalkBack, RTL, large-font, reduced-motion, or visual redesign.

## Evidence paths

- Actual verification: `.codex/test-artifacts/065-wx24-legacy-label-test-alignment/`
- Closeout: `.codex/history/065-wx24-legacy-label-test-alignment.md`
- Archived plan: `.codex/history/plans/065-wx24-legacy-label-test-alignment.md`

## Execution result

Updated only the stale expected day-label string in `ElasticTimeAxisTest`. The combined focused tests passed (13 tests, 0 failures), as did workflow, contract, build, and `git diff --check`. WX.24's implementation from cycle 064 plus this verification retry now meets the roadmap exit and is recorded DONE in cycle 065. No installed acceptance is claimed or required for these pure presentation/test changes.
