# Cycle plan — 017-talkback-timeline-recheck

State: ACTIVE
Roadmap: P.2.7
Objective: Recheck the timeline's daily/hourly custom accessibility action with TalkBack enabled on the installed Pixel 3 XL; record owner-observed speech, action outcomes, selected-time continuity, and installed captures.

## Scope and production boundary

Evidence-only installed acceptance, resuming the P.2.7 review after cycle 016 was blocked. Use Pixel 3 XL `crosshatch`, serial `8AKY0M32X`. The owner will attend to the device speech and, if needed, perform the TalkBack gestures to locate and invoke the custom action while the agent captures the resulting states. Record only observations actually supplied/heard. Do not change application source, layout, semantics, behavior, dependencies, or production oxygenWX.

Repository writes are limited to this plan, `.codex/current.md` through lifecycle commands, `.codex/test-artifacts/017-talkback-timeline-recheck/`, `.codex/history/017-talkback-timeline-recheck.md`, and the P.2.7 status/evidence update in `docs/ROADMAP.md` at closeout. Keep cycle 016 history immutable. If a confirmed product defect fails acceptance, record it and a separate bounded follow-up; do not implement a fix here.

## Dependencies and baseline

- P.2.4 is DONE and its earlier installed evidence establishes the custom action's intended presence; P.2.4 did not establish manual TalkBack acceptance.
- Cycle 016 built and installed the app on the target device and recorded its accessibility node description, but TalkBack was disabled and no screen-reader interaction was performed. See `.codex/history/016-talkback-timeline.md` and `.codex/test-artifacts/016-talkback-timeline/README.md`.
- P.1.2 and P.2.6 are DONE. The recent baseline is Android 12/API 31, TalkBack 17.1.0.983118198 installed, locale `en-US`, portrait, display override `1260x2240` at 560 dpi, font scale 1.0, animation scales 1.0.
- Initial TalkBack state in cycle 016 was disabled. Re-read it before enabling and restore that exact initial state at the end.

## Functional invariants

- The timeline is reachable through TalkBack traversal and has understandable control semantics, including its current daily/hourly scale or an action/state cue that clearly distinguishes it.
- The custom action clearly names its destination scale, is discoverable in each state, and succeeds daily-to-hourly and hourly-to-daily.
- The known representative date and noon fixture timestamp remain selected through both scale changes. Re-enter hourly mode at the end so the selected hour is visible.
- Preserve the visible prototype UI, no visible scale-control row, and honest DEMO DATA semantics.
- Record exact owner-reported spoken wording and visible state; do not infer speech from semantics, source, or screenshots.

## Ordered procedure

1. Confirm the plan is PLANNED, P.2.7 is NEXT, and run `python scripts/dev.py workflow`. Confirm Pixel 3 XL serial `8AKY0M32X`, Android/API, TalkBack package/version, locale, orientation, display/font overrides, animation settings, enabled accessibility services, and whether the owner can listen to the device and perform a gesture if needed. If the device or attentive owner observation is unavailable, preserve exact evidence and close BLOCKED.
2. Build, install, and launch the app with available JDK 17 and Android SDK. Record command, APK identity/hash, package version, and foreground activity. If build/install fails, preserve output and stop.
3. With TalkBack disabled, select Sunday, Oct 25 on the daily tape to set its representative noon summary; verify visible date and daily-overview state and capture `talkback-daily-before.png`.
4. Enable TalkBack using the verified service component, confirm it is active, then focus the timeline using a deliberate single tap in its accessible bounds. Capture `talkback-daily-focus.png`. Ask the owner to listen and record the exact label/role/state speech, focus order, scale cue, and any confusion. Confirm the sighted UI still has no mode-control row.
5. Have the owner invoke the available TalkBack custom action from daily to hourly. Capture `talkback-hourly-after-action.png`; record exact action label and spoken result. Verify the visible tape and forecast summary still show Sunday, Oct 25 at noon.
6. Have the owner invoke the reverse action from hourly to daily. Capture `talkback-daily-after-action.png`; record the speech and confirm the date/time has not changed. Invoke the action once more to return to hourly and capture `talkback-hourly-reentry.png`; verify Sunday, Oct 25 and `12 PM` remain selected.
7. Assign evidence-backed pass/fail for reachability, useful semantics, action discoverability and operation in both directions, selected-time continuity, and visual readability. Any confirmed defect gets a separate bounded follow-up; do not fix it here. If owner interaction or exact speech evidence is unavailable, close BLOCKED without claiming acceptance.
8. Restore the initial TalkBack and device settings, verify read-back, record exact speech/action observations, commands, captures, limitations, and outcome in the artifact README/history. Inspect the final diff, run `git diff --check`, and close the cycle. Mark P.2.7 DONE only if every exit condition is met.

## Acceptance and evidence

P.2.7 is DONE only when installed TalkBack reaches the timeline; the owner reports understandable control/state speech; the named custom action is found and succeeds in both directions; and the same Sunday, Oct 25 noon fixture timestamp remains selected throughout. The owner must report what was actually spoken and observed. This sample does not claim sub-hour continuity or acceptance across other devices/readers.

- Evidence directory: `.codex/test-artifacts/017-talkback-timeline-recheck/`.
- Capture targets: `talkback-daily-before.png`, `talkback-daily-focus.png`, `talkback-hourly-after-action.png`, `talkback-daily-after-action.png`, and `talkback-hourly-reentry.png` (document any unavailable state).
- Artifact index, device/accessibility settings, exact commands, owner-reported spoken output, findings, and disposition: `.codex/test-artifacts/017-talkback-timeline-recheck/README.md`.
- Cycle history: `.codex/history/017-talkback-timeline-recheck.md`.

## Authorized verification

- `python scripts/dev.py workflow`
- Read-only host/device, installed package, accessibility service, and settings inspection.
- `python scripts/dev.py run --serial 8AKY0M32X` with command-scoped JDK 17 / Android SDK environment.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/017-talkback-timeline-recheck/<state>.png` for the five named states.
- Enabling/restoring the already installed TalkBack service on this device; owner-assisted manual TalkBack traversal and custom-action use.
- `git diff --check` and final diff inspection.

Do not run unit tests, lint, or contract checks in this evidence-only cycle.

## Risks and assumptions

- The owner can hear device speech during the enabled-TalkBack states and can provide contemporaneous observations; screenshots do not capture speech.
- Gesture behavior and exact spoken wording can vary with TalkBack version/configuration. Record actual behavior rather than assuming phrasing.
- The custom action may require a TalkBack local context menu gesture; the owner should use the active TalkBack gesture guidance if direct focus traversal does not expose it.
- Findings are bounded to the named Pixel 3 XL and TalkBack configuration.

## Out of scope

- Any source, UI, accessibility semantics, behavior, dependency, or build-system changes, including fixes for discovered defects.
- Unit tests, lint, contract checks, RTL/reduced-motion review, other devices/readers, and production oxygenWX changes.
