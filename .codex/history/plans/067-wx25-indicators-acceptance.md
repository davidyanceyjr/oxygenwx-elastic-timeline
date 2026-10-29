# Cycle plan — 067-wx25-indicators-acceptance

State: ACTIVE
Roadmap: WX.25
Objective: Restore catalog precipitation and wind indicators and complete authorized WX.25 verification.

## Approved concept and owner direction

The owner-approved WX.25 board is `.codex/concepts/WX.25-timeline-44-days.png` (editable SVG). It depicts the installed screen's separate rain and compass indicators above the tape and requires its information hierarchy to survive catalog binding. On 2026-10-29 the owner explicitly rejected cycle 066's replacement of these with a small summary sentence, authorized focused tests and installed gesture/visual review, and excluded TalkBack from this follow-up. The approved concept and this correction govern the result. TalkBack remains unverified here and is assigned to WX.29; no TalkBack acceptance claim is allowed.

## Production boundary

- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`: restore separate precipitation and wind icon/value indicators in the original lower-screen position without changing timeline gesture ownership.
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt`: reuse existing indicator drawing components if needed; keep the fixture screen's current layout and behavior.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt`: expose typed wind direction only if needed for the compass; retain source fact and null semantics.
- Focused existing unit tests, including alignment of a stale coordinator assertion with WX.24's exact-instant selection contract; a narrow new test only for changed pure presentation behavior if warranted. No full suite.
- Cycle 067 plan, evidence, history, current pointer, and WX.25 roadmap status/evidence entries.

## Functional invariants

- Restore the approved full-screen hierarchy: precipitation chance indicator at lower left and wind indicator at lower right, with icons and readable values above the tape in normal and compact presentation.
- Use the active catalog's selected same-date fact. Show unavailable precipitation, wind speed, or direction honestly; do not turn missing data into zero or invent a compass bearing.
- Preserve exact selected `Instant`, separate fact sample time, 44 local dates, canonical display conversion, source meaning, and fixture route.
- Keep timeline tap/hold/drag and main-display navigation ownership; no atmospheric redesign or source-specific UI branches.

## Steps

1. Inspect cycle 066 evidence, approved concept, current catalog and fixture screen, and connected device; record baseline.
2. Activate this reviewed plan. Restore the separate indicators and any narrow typed direction field. Keep the main forecast summary uncluttered.
3. Run focused tests for coordinator, canonical projection, absolute time axis, and any changed pure presentation behavior. Run workflow, contract, and build.
4. Install on Pixel 3 XL. Capture normal and representative missing-value/source states; inspect indicator visibility, text, hierarchy, history/future bounds, source switch, browser tap path, gesture arbitration, responsiveness and readability. Capture additional compact/large-font/landscape visual states if feasible; mark unrun conditions honestly. Do not enable or run TalkBack.
5. Record exact commands and results under `.codex/test-artifacts/067-wx25-indicators-acceptance/`. Review final diff and `git diff --check`; close only with evidence-backed status.

## Acceptance/evidence

- Separate rain and compass indicators with values are visibly restored on the installed catalog screen, positioned as in the approved board, including truthful unavailable values.
- Focused tests, contract, and build pass. Installed checks establish selected/fact time and source agreement, 44-day bounds, browser tap selection, and the responsiveness/gesture/readability triad where exercised.
- TalkBack and spoken browser announcements are NOT RUN by owner direction and must not be claimed. Broad cross-app TalkBack, RTL, reduced motion, and font/orientation matrix remain WX.29 work; any local checks actually run are recorded.
- If another required WX.25 condition cannot be evidenced, close this attempt as incomplete and keep WX.25 NEXT rather than silently declaring acceptance.
- Evidence: `.codex/test-artifacts/067-wx25-indicators-acceptance/`; history: `.codex/history/067-wx25-indicators-acceptance.md`.

## Risks / assumptions

- The device is connected (`8AKY0M32X`); `DISPLAY` was absent at planning time, so Android command-line capture and direct device input are the primary installed tools.
- The current catalog view has formatted precipitation and wind strings but lacks typed wind direction; adding one nullable bearing is sufficient to draw the compass honestly.
- The approved concept is reused for this corrective continuation; the owner's explicit indicator direction resolves the regression without a new design artifact.

## Out of scope

TalkBack execution, startup/location flow, official alerts, ATM scene polish, provider/storage changes, and full-suite or release acceptance.

## Execution result

Restored separate rain and compass indicators above the catalog tape, with nullable wind direction and explicit unknown values. The installed reference screenshots establish normal LIVE, SIMULATED missing-value, tape drag/hold, and 1.3× portrait readability states. One preexisting coordinator test assertion was updated to the already adopted WX.24 exact-instant contract. Focused tests, workflow, contract, build, install, and `git diff --check` passed; the first focused run and its stale assertion failure are preserved. See the cycle evidence README for exact commands, captures, and limitations. Browser activation and several required WX.25 acceptance states remain unverified, so WX.25 remains NEXT.
