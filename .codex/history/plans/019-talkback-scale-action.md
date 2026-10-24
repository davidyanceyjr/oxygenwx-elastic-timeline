# Cycle plan — 019-talkback-scale-action

State: ACTIVE
Roadmap: P.2.7
Objective: Verify the installed timeline’s TalkBack daily/hourly scale action in both directions and record spoken semantics and selected-time continuity.

## Scope and production boundary

Evidence-only installed acceptance of P.2.7 on Pixel 3 XL `crosshatch`, serial `8AKY0M32X`. The app implementation is committed as `c988bb3` and already installed. Do not change application source, layout, accessibility semantics, behavior, dependencies, build configuration, or production oxygenWX in this cycle.

Repository writes are limited to this plan, `.codex/current.md` through cycle commands, `.codex/test-artifacts/019-talkback-scale-action/`, `.codex/history/019-talkback-scale-action.md`, and the P.2.7 status/evidence update in `docs/ROADMAP.md` at closeout. Preserve histories 016–018 unchanged. If a product defect is confirmed, record it and create a separate bounded implementation plan before changing code.

## Baseline and dependencies

- P.2.4 is DONE and exposes the scale action; P.2.13 is DONE and provides the forecast browser as an accessible data path. Neither established manual acceptance of the scale action.
- The owner has now navigated the installed TalkBack forecast browser and confirmed double-tap selection. This satisfies the re-entry condition for a guided P.2.7 attempt.
- Last recorded device baseline: Android 12/API 31, Pixel 3 XL, locale `en-US`, portrait, display override `1260x2240` at 560 dpi, font scale 1.0, animation scales 1.0. TalkBack was restored disabled after cycle 018. Re-read all settings before enabling and restore them at close.
- Read actual TalkBack version and active gesture assignment. Do not assume the TalkBack global menu is the focused node’s custom-actions menu. Official Android help distinguishes navigation, activation, and the configurable “Show custom actions” gesture; the device’s actual mapping governs this check.

## Functional invariants

- Focus reaches the forecast timeline and its spoken label/state identifies the currently selected date/time, forecast summary, and daily/hourly scale.
- The focused timeline exposes the custom scale actions in the active TalkBack actions menu. Their labels identify the destination scale.
- Invoking hourly from daily and daily from hourly each changes only the scale. Keep the selected fixture timestamp at Sunday, Oct 25, 12 PM across both transitions; re-enter hourly at the end.
- Record only exact speech the owner actually hears and reports. Do not infer speech from source, hierarchy, or screenshots.
- Preserve the visible UI, no visible scale-control row, selected-time cues, and DEMO DATA semantics.

## Ordered procedure

1. Confirm P.2.7 is NEXT, this plan is PLANNED, and `python scripts/dev.py workflow` passes. Read the device serial/model, Android/API, TalkBack version, locale, orientation, display/font overrides, animation scales, and current accessibility-service settings. Ask the owner to have audio available for attentive listening; if they cannot listen during the action attempt, keep acceptance incomplete.
2. Capture the installed app’s daily portrait baseline with Sunday, Oct 25, 12 PM selected (`talkback-daily-before.png`) and inspect its hierarchy. Do not rebuild unless the installed package is missing or not the committed build; if a rebuild is needed, use the scoped JDK 17/Android SDK command already recorded in cycle 018.
3. Enable the existing TalkBack service and verify it is active. Guide the owner to focus the timeline by one-finger right/left navigation. Record the spoken control label, role, scale, selected date/time, forecast facts, and whether an activation hint is present. Capture `talkback-daily-focus.png`.
4. With the timeline focused, guide the owner to invoke TalkBack’s assigned “Show custom actions” action. If the owner cannot identify its gesture, inspect TalkBack settings/help to find the current assignment; do not remap gestures during this cycle. Confirm the menu exposes the app actions. Invoke “Show hourly timeline,” listen to its spoken result, and capture `talkback-hourly-after-action.png` plus a read-only UI hierarchy. Verify the timeline/summary still represent Sunday, Oct 25, 12 PM.
5. Re-focus the timeline, invoke “Show daily timeline,” record the exact action/speech result, and capture `talkback-daily-after-action.png`. Verify Sunday, Oct 25 at noon remains selected. Invoke “Show hourly timeline” once more, capture `talkback-hourly-reentry.png`, and confirm Sunday, Oct 25 / 12 PM remains selected and the hourly browser still derives from that state.
6. Assign evidence-backed pass/fail for reachability, useful spoken semantics, custom-action discovery, both action directions, selected-time continuity, and readable installed states. If the custom actions are absent, mislabeled, or do not preserve selection, record the confirmed defect and prepare a separate implementation cycle; do not fix it here. If only the user interaction or speech observation cannot be completed, close P.2.7 BLOCKED without claiming acceptance.
7. Restore the exact initial TalkBack and device settings and verify their readback. Write the artifact README and cycle history with commands, settings, exact owner-reported speech, action results, screenshots, hierarchy evidence, and limitations. Run `git diff --check`, inspect the final diff, and close.

## Acceptance and evidence

P.2.7 is DONE only when installed TalkBack reaches the timeline; the owner reports understandable control/state speech; the named custom actions are discovered and succeed daily-to-hourly and hourly-to-daily; and the same Sunday, Oct 25, noon fixture timestamp remains selected throughout. The owner reports what was actually spoken and observed. This verifies only the named Pixel 3 XL/TalkBack configuration.

- Evidence directory: `.codex/test-artifacts/019-talkback-scale-action/`.
- Captures: `talkback-daily-before.png`, `talkback-daily-focus.png`, `talkback-hourly-after-action.png`, `talkback-daily-after-action.png`, `talkback-hourly-reentry.png`, and read-only UI hierarchy dumps for action states. Document unavailable states instead of fabricating them.
- Artifact index, device/accessibility settings, exact commands, custom-action gesture guidance, owner-reported speech, findings, and disposition: `.codex/test-artifacts/019-talkback-scale-action/README.md`.
- Cycle history: `.codex/history/019-talkback-scale-action.md`.

## Authorized verification

- `python scripts/dev.py workflow`.
- Read-only host/device, installed package, TalkBack version/service, settings, and UI hierarchy inspection.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/019-talkback-scale-action/<state>.png` for named states.
- Enable/restore the already installed TalkBack service and owner-assisted traversal/action use; do not change TalkBack gesture mappings.
- `git diff --check` and final diff inspection.

Do not run tests, lint, or contract checks in this evidence-only cycle.

## Risks and assumptions

- The owner can listen attentively during action attempts and report the spoken output. Screenshots and hierarchy dumps do not prove speech.
- TalkBack version/configuration can change action-menu gestures. Inspect the installed assignment and guide the owner rather than assuming a default.
- Focus/highlight is not expected to animate the visual timeline. Acceptance is based on control speech, action invocation, visible scale/date/time, and timestamp continuity.
- If the actions are unavailable or behavior is defective, P.2.7 cannot close as DONE in this cycle; create a separate bounded implementation follow-up from the confirmed evidence.

## Out of scope

- Any app source, UI, accessibility semantics, behavior, dependency, or build-system changes.
- Changes to TalkBack gesture mappings, RTL/reduced-motion, other devices/readers, unit tests, lint, contract checks, and production oxygenWX.
