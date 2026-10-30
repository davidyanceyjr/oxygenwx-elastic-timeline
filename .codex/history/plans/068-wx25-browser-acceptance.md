# Cycle plan — 068-wx25-browser-acceptance

State: CLOSED — INCOMPLETE; WX.25 remains NEXT
Roadmap: WX.25
Objective: Complete the remaining WX.25 browser, scene-truth, and installed interaction acceptance on the catalog-backed timeline
Date: 2026-10-29

## Owner-approved concept and purpose

The owner approved `.codex/concepts/WX.25-timeline-44-days.png` in the current conversation on 2026-10-29; its editable source is `.codex/concepts/WX.25-timeline-44-days.svg`. The board preserves the installed UI hierarchy and requires one exact selected `Instant` shared by the tape, facts, and browser across D-30 through D+13, with separate fact sample time and truthful source, product, freshness, coverage, and missing-value context. Archived plan 066 records the earlier integration-boundary approval. The board is design direction, not installed acceptance evidence. If a defect requires a new browser layout or interaction model, seek approval of a focused revision before implementing it.

This cycle completes outstanding WX.25 acceptance on the existing catalog route and corrects only reproduced defects within that boundary. Preserve the existing browse accessibility action and timeline gestures: ADB pointer taps exercised the tape handler and did not demonstrate a browser defect. Cycles 066 and 067 remain valid partial evidence; WX.25 remains NEXT until all of its exit conditions are evidenced.

## Verified baseline and dependencies

- WX.22, WX.23, and WX.24 are DONE. `.codex/current.md` was IDLE before this plan. Older files remain under `.codex/plans/`, but none was the current cycle pointer.
- `MainActivity` observes `CatalogActivationCoordinator`, creates `CatalogTimelinePresenter`, and passes `CatalogTimelineView` plus a generation-checked `Instant` selection callback to `CatalogTimeLabScreen`.
- `CatalogTimelinePresenter` supplies 44 local-date rows, actual fact-time rows, source/product/status labels, selected versus sample time, and nullable facts. `CatalogForecastTimeline` owns tape gestures and browse/scale accessibility actions. `CatalogForecastBrowser` has selectable daily overview and actual fact-time rows. The owner-observed activation and narrow ADB row-selection path are recorded below; the complete browser acceptance matrix remains open.
- Cycle 066 recorded D-30/D+13 and source-switch value observations. Cycle 067 recorded restored separate indicators, normal/missing-value states, 1.3× portrait, tape drag/hold, and a passing 22-test focused rerun. The complete scene-truth matrix and full WX.25 responsiveness/gesture/readability dispositions remain open.
- Pixel 3 XL `8AKY0M32X` was connected at planning time. Shell `DISPLAY` was unset; `DISPLAY=:0` may be available. Reconfirm device/X access when executing. The initial draft ran no test, build, or installed acceptance check; the later owner report and narrow live observations are recorded below and must be preserved or recaptured as cycle evidence before claiming a pass.

## Owner-reported TalkBack observation — 2026-10-29

The owner reports navigating the entire installed application with TalkBack, using single-finger double-taps and swipes, two-finger taps, and other gestures, and reports that "Everything played." This is an owner observation on Pixel 3 XL `8AKY0M32X` (Android 12 build `google/crosshatch/crosshatch:12/SP1A.210812.016.C1/8029091:user/release-keys`); the installed package reported version `0.1.0-prototype`, version code 1, last updated 2026-10-29 15:51:22 device time. The exact traversal sequence, spoken announcements, and per-state screenshots were not supplied, so this report does not by itself complete the formal TalkBack system acceptance matrix assigned to a TEST cycle.

During the same live session, the owner's physical TalkBack double-tap on the focused timeline opened `Daily forecasts`. Read-only installed hierarchy inspection showed the daily rows, a checked selected row, source/status text, and the `Browse fact times` control. The owner observed `Hourly facts` opening. ADB pointer selection of a 1:00 PM CST hour row then showed that row checked, and after `Done` the main screen showed selected 1:00:00 PM CST, fact sample 1:00 PM CST, 60°, cloudy, 32% precipitation chance, and 6 mph wind. These are narrow browser observations; the ADB row taps are not evidence of spoken TalkBack row navigation. No app code was changed for this observation.

## Production boundary

Begin with read-only installed inspection. Correct only a reproduced defect: browser/tape semantics belong in `CatalogTimeLabScreen.kt`, truthful row content in `CatalogTimelinePresentation.kt`; touch `MainActivity.kt` or the coordinator only if evidence localizes the defect there. Preserve fixture/debug routes, selection callbacks, browse action, and gesture ownership. Do not add or run automated tests unless the owner asks; broad TalkBack system acceptance belongs to WX.29. Store evidence in `.codex/test-artifacts/068-wx25-browser-acceptance/`; closeout targets `.codex/history/068-wx25-browser-acceptance.md` and `.codex/history/plans/068-wx25-browser-acceptance.md`.

## Functional invariants

- One coordinator-owned exact selected `Instant` drives tape, summary, scene, and browser. Sparse nearest samples retain their own valid time. Zoom, browser opening, and scene interpolation cannot change selection.
- The 44 local dates are D-30 through D+13 in the catalog timezone. Day rows use the local-noon overview anchor; hour rows select actual fact instants. DST and gaps do not imply uniform 24-hour days or fabricated facts.
- Canonical metric data and presentation-only conversion remain separate; Compose consumes typed projection values. Source/product/freshness/coverage and missing-versus-zero meanings stay truthful across LIVE, SIMULATED, and REPLAY without source-specific layout branches.
- Preserve the full-screen scene, separate rain/wind indicators, textual facts, fixture/debug paths, tape touch region and fixed playhead, daily/long-hold hourly scales, and gesture ownership. Decorative day/night/snow/storm output cannot claim unsupported facts.
- Maintain responsiveness, predictable gestures, and readability. This cycle does not perform ATM.8–ATM.10 scene polish.

## Evidence-first decision procedure

- Derive expected behavior only from the active `CanonicalCatalog`/`CatalogWindow` and existing `CanonicalForecastProjection`/`CatalogTimelinePresenter`. For each exercised state, compare installed selection, values, provenance, coverage, and unavailable fields with that reference. Record selected `Instant` separately from fact `validTime`; sparse nearest facts may differ without indicating a defect. Daily rows select their local-noon anchor; hourly rows select actual fact instants. Missing remains unavailable, zero remains a value, conversions follow typed projection, and scene appearance/interpolation never establishes a weather fact.
- Diagnose in order: catalog/window, projection/presenter, installed browser/tape. Record the first divergence and its dataset, device/build, input method, selected instant, fact `validTime`, expected behavior/value, and observed behavior/value. Change nothing when the contract matches. For a repeatable in-scope mismatch, fix only its owning existing boundary and repeat that scenario and affected checks. A new layout/interaction model, public contract change, or mismatch without catalog evidence becomes a bounded follow-up, not expanded scope.
- Keep input evidence distinct: physical TalkBack activation, hierarchy inspection, and ADB pointer selection are separate observations. Claim spoken behavior only when directly recorded. If a required weather/DST state lacks a suitable installed dataset, mark it NOT RUN and identify the limitation; do not fabricate fixtures or infer acceptance.

## Steps

1. Review cycles 066/067, the approved board, and owner observations. Confirm the installed APK/device; create a PASS/FAIL/NOT RUN matrix, citing prior evidence only for observed conditions. Preserve or recapture durable browser screenshots, hierarchy, input method, and before/after values under this cycle's artifact directory.
2. On Pixel 3 XL, exercise the existing accessibility browser route and daily/hourly row selection, checked state, scrolling, Done/dismiss, return to tape, labels/roles, and scale/browse actions. Keep physical TalkBack activation distinct from ADB pointer row selection.
3. Cover remaining window, source, field, scene, and DST conditions listed in Acceptance/evidence, using existing catalog/debug routes. Record unavailable states as NOT RUN with the specific dataset limitation.
4. At the reference portrait viewport and default/1.3× font scales, record responsiveness, tap/hold/drag arbitration, ownership, and readability in bright/dark scene and browser states. Include display size, resolution, density, font scale, and app/build identity. WX.29 owns compact/landscape, RTL, reduced motion, and broad TalkBack acceptance; do not claim those from this focused pass. Recheck any delegated condition affected by a correction or mark it NOT RUN.
5. Apply only the smallest evidence-backed correction within scope and repeat its scenario and affected checks. Record exact commands, results, captures, device/build/input details, `git diff --check`, and final diff. Mark WX.25 DONE only with full exit evidence; otherwise retain NEXT with gaps, or use BLOCKED only for a demonstrated external blocker.

## Acceptance/evidence

- Run `python scripts/dev.py workflow` and `python scripts/dev.py contract`; build/install with `python scripts/dev.py build` and `python scripts/dev.py run --serial 8AKY0M32X` only when a fresh APK is needed. Save installed captures with `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/068-wx25-browser-acceptance/<state>.png`; retain command output and device/build identity. Inspect `git diff --check` and the final diff.
- Matrix rows must cover: browser open, day/hour choice, scroll, dismiss, checked state and main-screen agreement; hierarchy and focused physical TalkBack activation; D-30/D/D+13 and LIVE/SIMULATED/REPLAY replacement; sparse/missing/zero fields; day/night/snow/storm truth and DST when data permits; responsiveness, gesture arbitration, and readability at reference portrait/default and 1.3× scales. Each applicable row records PASS/FAIL/NOT RUN, evidence or prior-cycle citation, dataset, input method, device/build/display configuration, selected instant, fact `validTime`, values/provenance/coverage or unavailable fields, and catalog reference. Preserve the owner's broad TalkBack report as a report only; it does not replace the WX.29 system matrix.
- No automated test is authorized by this plan. If the owner later authorizes a scoped correction test, run only its focused target; the full system suite remains in its separate TEST cycle.

## Risks / assumptions

The browser accessibility action and row hierarchy have narrow installed observations, but spoken row traversal is unrecorded; WX.29 owns broad TalkBack acceptance. Suitable DST/weather datasets or device/capture conditions may be unavailable; mark exact gaps NOT RUN. Missing required WX.25 evidence prevents DONE.

## Out of scope

Startup/location flow, provider/storage contracts, official alerts, ATM.8–ATM.10 polish, broad accessibility redesign, release packaging, and deprecated-app changes.

## Difficulty and model fit

### Raw implementation difficulty: 7/10

The scope is bounded and the interaction/design contract is settled, but the remaining installed browser, source, weather-truth, gesture, and readability matrix is substantial. A reproduced defect may still require a focused correction and an installed rerun. Device/data availability and evidence capture add work beyond mechanical code changes.

### Reasoning concentration

The main judgment is distinguishing a real installed mismatch from expected catalog behavior. Use the evidence-first procedure above; selected and fact sample times may differ without indicating a defect.

### Mechanical implementation difficulty after those decisions: 5/10

Once a repeatable mismatch is localized, the plan limits a correction to the existing UI or presentation owner and requires repeating that installed scenario and affected checks. Most remaining effort is coverage, device interaction, and durable evidence capture; a cross-layer redesign is outside this cycle.

### Recommendation and fallback

Resolve each suspected mismatch from repository semantics and installed evidence during execution, before editing. The current runtime exposes GPT-6 Luna, GPT-6 Sol, GPT-6.1 Sol, GPT-6.1 Terra, and GPT-6 Astra. Recommend **GPT-6.1 Sol** as the lowest-cost relative usage tier with a credible path through the full bounded pass; confidence is **moderate-high**, supported by the detailed decision procedure and cycles 066/067's catalog integration, focused verification, and installed evidence. GPT-6 Luna is a lower-usage option, but this task's cross-state truth judgments and evidence burden make its expected supervision/rework less attractive. One Sol model should handle the full pass while defects remain within the existing presentation/UI boundaries.

Escalate to **GPT-6 Astra** if evidence shows a defect that requires changing selection or accessibility contracts, if a mismatch cannot be localized to the current catalog/projection/UI boundaries, or if repeated focused fixes fail the same installed scenario. Do not escalate merely because selected and fact sample times differ when both agree with the established projection contract.

### Cost and context basis

Estimated workload is **30–55k input tokens**, **5–10k output tokens**, and **2–4 retry/debug/review turns**, including repository inspection, installed findings, and evidence reporting. This Codex session exposes no per-task marginal dollar billing, so a dollar estimate is unavailable and API rates would not represent its charge. Relative usage-tier fit only is assessed here. The active model and context-window capacity are not exposed, so context utilization cannot be calculated from this workload estimate.

### Fit risks

- Required DST or scene states may lack a suitable installed dataset, forcing precise NOT RUN dispositions.
- A subtle difference between selected time, sample time, and browser row selection could be misdiagnosed without following the catalog-to-projection-to-installed comparison.
- Device, font-scale, or capture conditions may limit evidence collection even when the app behavior is correct.

## Context audit

**Result: UNQUANTIFIED — context-window capacity unavailable.** The plan names a runtime candidate set but does not identify the model selected for execution, and the active environment does not expose its context-window size. The workload estimate above is retained, but no utilization percentage or split decision is claimed. Re-run the quantitative audit once the selected model and its context capacity are known. This text-only deduplication preserves WX.25 scope, acceptance conditions, evidence fields, and cycle ordering; no roadmap or current-pointer change was made.
