# Cycle plan — 069-wx25-functional-timeline-acceptance

State: ACTIVE
Roadmap: WX.25
Objective: Complete WX.25 installed acceptance across the full 44-day SIMULATED catalog, including all existing weather-event cases, browser rows, and timeline scrub behavior

## Production boundary

This is a continuation attempt for the sole NEXT roadmap slice, WX.25. Cycle 068 closed incomplete after accepting browser interaction on LIVE data but missed the existing SIMULATED/debug catalog route. Its post-close correction is in `.codex/test-artifacts/068-wx25-browser-acceptance/README.md`.

The owner clarified on 2026-10-29 that the intended dataset is 44 days with event cases present across that timeline, and that functional data/timeline behavior precedes final visual polish. The approved WX.25 concept remains `.codex/concepts/WX.25-timeline-44-days.png` with editable `.svg`; approval and constraints recorded in archived plan 068 remain applicable because this plan preserves its screen hierarchy and interaction model. No new concept is needed unless a reproduced defect requires a new layout or interaction model.

Inspect and exercise the existing installed debug catalog picker, `SimulatedCatalogSource`, canonical projection/presenter, forecast browser, and timeline. Scope includes proving all 44 local dates (`D-30..D+13`), event distribution, browser rows, selected-time/fact-time mapping, DST-aware date boundaries, source labeling, and daily/hourly scrolling and scrubbing. Correct only a reproduced mismatch within the existing WX.25 presentation/UI boundary. No visual redesign or polish.

If a defect is reproduced, permitted application files are `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` and `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`. A different owner boundary may be edited only if evidence localizes the defect there and the correction remains within WX.25. Do not edit provider transport, fixture data, public catalog contracts, source selection architecture, atmosphere polish, roadmap status, or unrelated UI.

## Functional invariants

- Use the deterministic SIMULATED source through the existing debug picker and verify one canonical 44-local-date catalog. Event cases are distributed over the existing sequence; do not create one-off preview facts or rewrite the fixture to improve screenshots.
- Compare installed values to the active `CanonicalCatalog`, `CatalogWindow`, and `CatalogTimelinePresenter`. Record the dataset, location/timezone, `asOf`, date, selected `Instant`, fact `validTime`, provenance, coverage, and missing/zero distinction for each event family.
- Preserve exact selected-time authority through zoom, tap, hold, drag, browser selection, and dismiss. Daily rows use their local-noon overview anchor; hourly rows use actual fact instants. Selected and sampled times may differ only as established by the projection.
- Keep source, product, freshness, historical/forecast meaning, nullable facts, separate precipitation and wind facts, and accessible text truthful. Scene interpolation cannot create weather facts.
- Preserve existing browse accessibility action, browser ownership, timeline touch region, fixed playhead, daily/hourly scale semantics, and predictable gesture arbitration. Do not infer acceptance from TalkBack hierarchy alone; owner-operated TalkBack findings must be recorded separately.
- Handle DST by local-date boundaries and IANA timezone. The October/November 2026 Chicago window spans the Nov 1 fall transition and must remain a 44-date window even though that local date is 25 elapsed hours.

## Steps

- [ ] Inspect workflow, cycle 068 correction, WX.25 authorities, approved concept, installed app/build, and debug picker. Confirm the current device, package, location/timezone, source, dataset, and display configuration before input.
- [ ] Activate the existing Chicago SIMULATED dataset through the debug picker. Capture its active identity and compare the complete 44-date window and every event case in the deterministic sequence to the canonical source/projection. Include conditions (clear, partly cloudy, cloudy, rain, snow, storm) and represented phenomena (haze, fog, freezing rain, ice pellets); record cases absent from the typed catalog as unavailable rather than inventing them.
- [ ] Scrub the installed timeline across D-30, representative event dates for every condition/phenomenon, the Nov 1 DST date, D, and D+13. Exercise daily tap, immediate drag, stationary hold into hourly mode, hourly drag, release, and a return to daily mode. Verify selected `Instant`, fact sample, day labels, source/product labels, and facts at each checkpoint.
- [ ] Open the day and hour browser. Inspect the 44 date rows and all actual fact rows by scrolling to beginning, event cases, DST date, and end. Select representative event rows and verify checked state and selected/fact-time agreement before and after Done. Repeat browser-open/scroll/dismiss with TalkBack using the user's physical input; record the actual spoken selected row/time only if directly observed.
- [ ] Capture installed evidence at portrait font scales 1.0 and 1.3. Record responsiveness qualitatively, gesture ownership, and forecast readability in bright/day and dark/night samples. Do not claim frame-time performance without measurement. Compact/landscape, RTL, reduced motion, and the broader WX.29 matrix remain delegated unless a correction affects them.
- [ ] Correct only an evidence-reproduced in-scope defect; repeat its scenario and affected checks. Otherwise make no app-source change. Run only verification authorized below, save logs/screenshots/hierarchies under the cycle evidence directory, inspect `git diff --check` and the final diff, then close complete or incomplete with exact gaps.

## Acceptance/evidence

Evidence directory: `.codex/test-artifacts/069-wx25-functional-timeline-acceptance/`; closeout `.codex/history/069-wx25-functional-timeline-acceptance.md`; archived plan `.codex/history/plans/069-wx25-functional-timeline-acceptance.md`.

The completion matrix must cover:

- Dataset identity and 44 dates `D-30..D+13`, including the 25-hour Nov 1 Chicago local date and horizon clamp.
- Presence and correct projection of every condition and phenomenon encoded in `SimulatedCatalogSource`; source facts, support/coverage, and missing versus zero values remain truthful.
- Daily/hourly timeline scrolling and scrubbing across the complete horizon, including event-date checkpoints and DST; each checkpoint records selection, fact `validTime`, displayed values, and provenance.
- Accessible browser open, daily/hourly selection, full-list scrolling, checked row, selected-time announcement as owner-observed, Done return, and main-screen agreement.
- SIMULATED activation and source identity on installed debug build. Existing cycle 066 LIVE/SIMULATED/REPLAY swap evidence may support source-switch invariants but does not replace this cycle's SIMULATED 44-day event evidence.
- Installed portrait captures at 1.0 and 1.3 font scale, daylight and night states, and evidence-backed dispositions for responsiveness, gesture ownership, and readability.

Before any input, record build/package, device ID/model/OS, viewport/resolution/density, font scale, timezone, catalog identity/asOf, and input method. Save screenshots plus `uiautomator` hierarchy XML for the initial/main state, full-horizon checkpoints, each event family, DST date, browser beginning/middle/end, TalkBack-selected row, and restored device state. Restore font scale 1.0 and TalkBack to its initial state at the end.

Authorized commands: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, installed screenshot/capture commands, and `git diff --check`. Automated tests, builds, and installs are not authorized by this plan or AGENTS.md unless the owner separately requests them. Do not claim visual/device acceptance without actual installed captures and applicable interaction/accessibility checks. If a required condition cannot be observed, record NOT RUN with the exact reason; do not describe existing data as unavailable without checking the SIMULATED/debug route.

## Risks / assumptions

- Assumes the installed debuggable app includes the existing WX.22 catalog picker and a saved Chicago location. Confirm before acceptance input; do not rebuild or install under this plan.
- The simulator's sequence is a deterministic synthetic meteorological product, not a provider forecast or official alert. Keep its provenance visible and never imply real-world event timing.
- Manual TalkBack announcements are owner-observed and cannot be represented by a silent screenshot. Record the exact row/time spoken when available; no user transcript is assumed.
- A defect outside the existing WX.25 presentation/UI owners, or a request for a new browser/layout model, requires a separate bounded plan and owner decision.

## Out of scope

- Final visual polish, atmosphere-engine work, new weather illustrations, and redesign of the current screen hierarchy.
- Creating/modifying synthetic catalog events, adding new weather vocabulary, provider/API changes, production startup/location flow, official alerts, and release packaging.
- Broad WX.29 compact/landscape, RTL, reduced-motion, and cross-app accessibility matrix except targeted regression checks required by an in-scope correction.
- Automated tests, build, or installation unless separately authorized by the owner.
