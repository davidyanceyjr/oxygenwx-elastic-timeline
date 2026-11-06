# Cycle plan — 116-p2-location-aware-double-tap

State: DONE — exit met.
Roadmap: P.2.22
Objective: Use the completing tap's daily position to select 7:00 AM local time on that date, carousel out-of-catalog targets to the first catalog date at 7:00 AM, then enter hourly scale; preserve the exact hourly selection when returning to daily.

## Plan readiness and board approval

The product owner approved both original board revisions as shown on 2026-11-06. In response to the demonstrated missing adjustment-notice path, the owner directed on 2026-11-06 that an out-of-catalog 7:00 AM entry target carousel to the first 7:00 AM entry in the catalog. The behavior board contract now records this explicit P.2.22 boundary exception; the already-approved artwork board remains applicable because wrapping changes only the selected date/time and adds no visible control or notice. The owner direction approves the bounded wrap rule. Installed screenshots and focused interaction checks remain required for cycle closeout.

| Board | Path | Preview SHA-256 | Approval |
|---|---|---|---|
| Behavior flow and contract | `.codex/concepts/P.2.22-location-aware-double-tap-behavior.svg` and `.md`; PNG preview `.png` | SVG `1cd3d0fef509599a8271ef9da72c5a527981409ea16f3f4ac57f0e782275d2fe`; PNG `9de2345dc1c2b26948905e31494a2cf8ba02b3af4eeea19f880263ebb77df316` | Original board approved as shown 2026-11-06; owner directly directed and approved the first-in-catalog 7:00 AM carousel rule on 2026-11-06 |
| UI artwork | `.codex/concepts/P.2.22-location-aware-double-tap-ui-artwork.svg`; PNG preview `.png` | SVG `71e6b178bf87d33ef5411f5cb5395b199f5ef10a2a4b9b7c150892212fcc8fb5`; PNG `ff9e32c995330b6645b2d0dd512423c3958ec10f33bd8264c86944cadf4c82dd` | Product owner approved as shown, 2026-11-06 |

The owner specified and approved a 7:00 AM entry target on 2026-11-06: daily entry selects 7:00 AM local time on the completing tap's date in the active location's timezone, or that date's existing 7:00 AM fixture/debug sample. If that date or its 7:00 AM target is outside the available catalog dates, select the first catalog date's 7:00 AM entry instead. Hourly-to-daily double-tap remains scale-only and ignores its coordinate, preserving the exact selected instant. The behavior board governs date resolution, target selection, carousel wrap, and unaffected gestures; the artwork board governs the Monday-to-Wednesday transition states, selected-time cues, existing daily/hourly hierarchy, and no added controls. Neither board replaces installed acceptance evidence.

The behavior board and contract now include the owner's explicit carousel direction; the UI artwork revision remains unchanged because the boundary outcome uses the same selected-time and timeline artwork with no notice or added chrome.

## Authority and baseline

- `docs/SPECIFICATION.md` records the controlling P.2.17 gesture rule and P.2.22's bounded daily-entry and carousel exception before implementation.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` records the same P.2.22 interaction exception; `docs/SPECIFICATION.md` remains controlling.
- Prior implementation baseline: cycle 110 / P.2.17 established a single recognized double-tap, no-op single taps, preserved drag behavior, and active-catalog plus fixture/debug parity. Cycle 114 / P.2.21 established the current daily high row beneath icons and divider.
- Read-only source inspection found the catalog's selected instant, location zone, supported interval, and local dates in `CatalogTimeLabScreen.kt` and `CatalogTimelinePresentation.kt`; the existing coordinate mapper clamps, so a new unclamped date-resolution helper is needed to detect carousel overflow. The fixture projection exposes complete hourly records grouped by local date in `ForecastSource.kt`; `ElasticTimeAxis.hourAtX` maps its timeline coordinate. `CatalogActivationCoordinator.selectInstant` silently clamps selections; P.2.22 instead resolves overflow to the first date's in-catalog 7:00 AM, so no adjustment notice is needed for this gesture.

## Scope

On both timeline paths covered by P.2.17, change only what a recognized timeline double-tap does according to the scale at gesture start:

1. In daily scale, resolve the completing tap's position to the local date, select 7:00 AM local time on that date, then enter hourly scale with that instant centered. If the touched date/target is outside the catalog, carousel to the first catalog date's 7:00 AM entry.
2. In hourly scale, return to daily scale while preserving the exact selected instant and ignoring the touch coordinate.
3. Keep active-catalog date resolution tied to `CatalogTimelineView.zone` and the catalog's supported interval. Keep fixture/debug mapping tied to the existing local date and its 7:00 AM sample; overflow wraps to the fixture's first date at 7:00 AM.

The source boundary is `InstantTimeAxis.kt`, `CatalogTimeLabScreen.kt`, `ElasticForecastTimeline.kt`, and `TimeLabScreen.kt`, plus the two governing specifications, behavior board, and roadmap records. Do not change weather projection, catalog contents, axis spacing, forecast facts, central readout, daily drag selection semantics, or unrelated scene rendering.

## Functional invariants

- Daily timeline single taps remain no-ops; a recognized double-tap does not dispatch a delayed single-tap selection.
- Daily double-tap uses the completing tap position to select 7:00 AM local time on that date before hourly scale is shown. If the resolved date or target falls outside the catalog, it selects the first in-catalog date at 7:00 AM instead.
- Hourly double-tap is a scale-only return. Keep the exact selected instant (including an in-window fractional selection if supported by the current UI state) under the playhead; do not reset it to noon or use the tap position.
- Use location-local dates for the active catalog; do not assume a local date is always 24 elapsed hours.
- P.2.22 daily-entry overflow wraps to the first in-catalog 7:00 AM entry; all other targets retain the existing clamp and adjustment-notice contract. In-window days with missing facts remain navigable without inventing facts.
- Preserve daily drag, hourly scrubbing, hourly-mode retention after release, main forecast-area single-tap behavior, main-area fixture reset double-tap, screen-reader scale action, readable forecast facts, and gesture ownership.
- Add no permanent mode row, instructional hint, or other visible control.

## Steps

- [x] Obtain owner approval of both exact board revisions; record date, hashes, constraints, and any requested revisions.
- [x] Review and activate the bounded plan after the board gate was met.
- [x] Record the owner-approved bounded 7:00 AM and carousel exception in both controlling interaction specifications and the behavior board.
- [x] Implement the bounded position-aware daily entry and exact-time hourly return on the active-catalog and fixture/debug timeline paths.
- [x] Perform focused installed interaction review on the Pixel reference device for Monday-to-Wednesday daily entry, hourly scrubbing to a distinct time, and exact-time return; check the directly affected labels, selected cue, gesture ownership, responsiveness, and readability.
- [x] Capture the approved active-catalog daily, hourly-entry, and hourly-return states, plus fixture/debug gesture-parity evidence; preserve actual evidence, inspect `git diff --check` and final diff, then close the cycle with exact results and limits.

## Acceptance and evidence

- On the active-catalog daily timeline, with Monday selected, a double-tap at Wednesday resolves using the selected location's local date and selects Wednesday at 7:00 AM local time before hourly mode appears. That instant is centered and the hourly labels/scene reflect the selected time.
- On fixture/debug daily and hourly paths, the same transition contract selects the touched fixture date's existing 7:00 AM sample on entry and preserves the exact selected fixture hour on return.
- From a daily position beyond the catalog's final date, both paths carousel to the first in-catalog date at 7:00 AM; record the resulting date/time and no boundary notice is shown for this P.2.22 wrap.
- After hourly scrubbing to Wednesday 4 PM, a timeline double-tap returns to daily with Wednesday and the exact 4 PM selected instant under the playhead. It does not reset to the daily noon sample.
- Single taps, daily dragging, hourly scrubbing, main-area gestures, and the screen-reader scale action retain their prior meanings. The tape stays responsive; labels, daily icons/highs, selected-time badge, forecast facts, and scene remain readable in the approved states.
- UI artwork states: active-catalog daily Monday with a Wednesday touch target; hourly Wednesday after entry; daily Wednesday at the distinct retained 4 PM selection; first-date 7:00 AM after a daily touch beyond the last catalog date. Use installed Pixel 3 XL captures for affected visual acceptance; the same artwork hierarchy applies because wrap adds no controls or notice.
- Visual conditions: capture in portrait on the Pixel 3 XL reference viewport at system font scale 1.0 with the device's standard motion setting; record the device/build, screenshot dimensions, locale/direction, and settings in the evidence README. Compact viewports and larger font scales are outside this gesture-focused slice. RTL and reduced-motion checks are NOT RUN in this cycle; repository policy assigns those cross-cutting conditions to dedicated TEST slices.
- Evidence target: `.codex/test-artifacts/116-p2-location-aware-double-tap/README.md`, installed screenshots, and `.codex/history/116-p2-location-aware-double-tap.md`.
- `python scripts/dev.py workflow` PASS before activation. Automated tests remain unauthorized under the plan's separate-authorization rule; installed checks, build/run, screenshots, evidence, and closeout remain required.

## Risks and assumptions

- The owner specified 7:00 AM as the daily-to-hourly entry target and directed out-of-catalog entry targets to carousel to the first in-catalog date's 7:00 AM. Missing weather facts at an in-range 7:00 AM remain unavailable under existing semantics.
- P.2.17 requires parity across active-catalog and fixture/debug timelines; the new date-target behavior is planned for both, while only the active catalog uses a selected location's IANA timezone.
- The P.2.22 exception changes only daily timeline double-tap entry and its overflow target. All other P.2.17, selection, and missing-data rules remain authoritative.

## Execution note

The debug profile route is the isolated legacy fixture `TimeLabScreen`, reached only through debug-only `DebugForecastActivity`. Its screenshots are labeled as fixture gesture evidence in `.codex/test-artifacts/116-p2-location-aware-double-tap/README.md`; they are not successor product UI acceptance. Active-catalog captures from `MainActivity` provide the product visual evidence.

## Out of scope

Changing hourly-to-daily selection, single-tap behavior, daily drag release behavior, main-area gestures, reset targets, accessibility action availability, location selection or GPS, catalog/data semantics, the deprecated oxygenWX app, visible mode controls, broad RTL/TalkBack/system matrices, or unrelated timeline/scene refinement.
