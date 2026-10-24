# Cycle plan — 016-talkback-timeline

State: ACTIVE
Roadmap: P.2.7
Objective: Accept the timeline daily/hourly custom action with TalkBack on the installed Pixel 3 XL and record semantics and selected-time continuity.

## Scope and production boundary

Evidence-only installed acceptance for the existing timeline custom accessibility action. Build and install the current standalone app on the Pixel 3 XL, traverse the timeline with TalkBack, invoke its action in both directions, inspect the spoken semantics and visible selected-time state, and record the outcome. Do not change application source, layout, semantics, behavior, dependencies, or production oxygenWX.

Repository writes for this cycle are limited to this plan, `.codex/current.md` through lifecycle commands, `.codex/test-artifacts/016-talkback-timeline/`, `.codex/history/016-talkback-timeline.md`, and the minimum P.2.7 status/evidence update in `docs/ROADMAP.md` at closeout. If a confirmed defect prevents acceptance, record the finding and a separate bounded follow-up; do not implement a fix here.

## Dependencies and baseline

- `.codex/current.md` was IDLE and P.2.7 was the sole NEXT slice when this plan was drafted.
- P.2.4 is DONE; its installed capture and source review establish that the timeline exposes a daily/hourly custom accessibility action without a visible mode-control row.
- P.1.2 is DONE. Recent installed acceptance used Pixel 3 XL `crosshatch`, serial `8AKY0M32X`; reconfirm the connected device and current settings at execution.
- P.2.6 is DONE. Its fresh installed evidence is context for timeline readability only; it does not establish TalkBack acceptance.

## Functional invariants

- The timeline is reachable through TalkBack traversal and exposes an understandable control label, current scale/state, and custom action.
- The spoken control label identifies the forecast time tape and gives useful interaction context; scale is understandable from its announced state and/or the clearly named available action. Record whether TalkBack actually communicates the current scale, rather than assuming the action's destination label is sufficient.
- The available custom action clearly names the destination scale, can be found and invoked in each state, and changes scale from daily to hourly and back without changing the selected absolute timestamp.
- The forecast summary and selected tape label continue to describe the same selected time; changing scale does not act as forecast-time navigation.
- Preserve the visible prototype UI and honest DEMO DATA semantics. No visible scale-control row is introduced.
- Record actual spoken output and observed state; do not infer TalkBack acceptance from source semantics, a screenshot, or successful compilation.

## Ordered procedure

1. Confirm this plan is PLANNED, P.2.7 remains NEXT, and run `python scripts/dev.py workflow`. Read the current installed-acceptance baseline and confirm Pixel 3 XL serial `8AKY0M32X`, model/API, Android version, TalkBack version and verbosity/configuration, locale, orientation, display override, font scale, and animation settings. Do not substitute another device. If the device or TalkBack is unavailable, record exact evidence and stop without claiming acceptance.
2. Build and install the current app using the available JDK 17 and Android SDK, then launch it. Record the build/package identity and exact commands. If build/install fails, preserve the output and stop.
3. With TalkBack off, tap a known day on the daily tape to establish its representative summary time (the fixture's daily selection is noon). Record its exact date and fixture timestamp. Enable TalkBack and traverse to the timeline. Record its focus order, exact spoken label and role, any explicit current-scale announcement, and the available custom action and its exact label. Judge whether the label and scale cue make the control and its current state understandable. Confirm the sighted UI has no visible mode-control row. Capture baseline focus and selected-summary state.
4. Invoke the custom action from daily to hourly. Record the exact action name and spoken result, determine whether the hourly state is clear from the announcement and newly offered reverse action, and compare the selected absolute fixture timestamp before/after. Confirm the hourly tick and summary still show the same date and noon selection. Capture the hourly state.
5. Invoke the custom action from hourly to daily. Record the action name and announcement, verify the daily state is clear, and compare the exact selected date/time against the baseline. Then invoke the action once more to return to hourly and confirm that the same date/noon remains selected. This final re-entry makes the selected hour observable after the daily overview. Capture the daily-after-action and final hourly states.
6. Review captures and interaction notes. Assign evidence-backed pass/fail for reachability, useful semantics, action discoverability and operation in both directions, selected-time continuity, and visual readability. If any confirmed defect fails the exit, do not fix it; record the separate bounded follow-up and leave P.2.7 not DONE/BLOCKED as applicable.
7. Restore the initial TalkBack and device settings. Record exact settings, commands, capture filenames, spoken output, observations, limitations, and outcome in the artifact README and cycle history. Inspect the final diff and run `git diff --check`. Close the cycle only according to the observed result; mark P.2.7 DONE only if every exit condition is met.

## Acceptance and evidence

P.2.7 is DONE only when installed TalkBack can reach the timeline; its spoken label and scale cue communicate useful control/state information; its clearly named custom action is discoverable and succeeds in both directions; and the same known representative absolute fixture timestamp remains selected through both transitions. Record actual speech and evidence-backed judgments. A clear destination action may communicate scale even without a separate “current scale” phrase, but the observed announcement/action must let a TalkBack user distinguish the current mode. Any ambiguity, failed action, or timestamp change fails acceptance and gets a separate bounded implementation slice; do not repair it in this evidence-only cycle. The result is specific to this representative timestamp and does not claim sub-hour continuity.

- Capture target directory: `.codex/test-artifacts/016-talkback-timeline/`.
- Capture targets: `talkback-daily-focus.png`, `talkback-hourly-after-action.png`, `talkback-daily-after-action.png`, and `talkback-hourly-reentry.png` (or document why a state cannot be captured).
- Artifact index, device/accessibility settings, exact commands, spoken feedback, per-condition findings, and outcome: `.codex/test-artifacts/016-talkback-timeline/README.md`.
- Cycle history: `.codex/history/016-talkback-timeline.md`.
- Update P.2.7 evidence path in `docs/ROADMAP.md` at closeout to link these records if the cycle closes.

## Authorized verification

- `python scripts/dev.py workflow`
- Read-only host/device, installed package, and accessibility-settings inspection.
- `python scripts/dev.py build` and `python scripts/dev.py run --serial 8AKY0M32X` with command-scoped JDK 17 / Android SDK environment, if confirmed available.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/016-talkback-timeline/<state>.png` for the four named states.
- Manual TalkBack traversal and custom-action invocations described above.
- `git diff --check` and final diff inspection.

Do not run unit tests, lint, or contract checks in this evidence-only cycle. A build, source inspection, or screenshot without the TalkBack interaction does not satisfy the exit.

## Risks and assumptions

- Device and Android/TalkBack availability must be reconfirmed at execution; recent availability is not a guarantee.
- Spoken wording may vary with Android/TalkBack version and settings. Record the actual version/configuration and output rather than prescribing exact phrasing.
- Screenshots may not capture speech; pair them with contemporaneous written interaction notes. Do not claim accessibility acceptance based on screenshots alone.
- TalkBack gestures can compete with app gestures. Establish the representative daily selection before enabling TalkBack, then use the custom action for each scale transition. This deterministic noon selection makes the timestamp directly observable on hourly re-entry; it does not test fractional-hour continuity.
- The roadmap currently points to earlier-numbered target paths for this slice. This cycle uses the next available cycle identifier (016); reconcile P.2.7 evidence links at closeout without changing unrelated slices.
- Findings are bounded to the named Pixel 3 XL/TalkBack configuration and do not establish acceptance across other screen readers or devices.

## Out of scope

- Any source, UI, accessibility semantics, behavior, dependency, or build-system changes, including fixes for discovered defects.
- Unit tests, lint, contract checks, broad font/orientation/RTL/reduced-motion review, and other devices or screen readers.
- Changes to production oxygenWX, navigation, forecast data, or the roadmap beyond P.2.7 closeout evidence/status.
