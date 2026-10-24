# Cycle plan — 015-responsive-readability-review

State: DONE
Roadmap: P.2.6
Objective: Close the installed readability review for the current prototype across five Pixel 3 XL conditions and record cross-cutting interaction dispositions; evidence only, no app changes.

## Scope and production boundary

Review the current standalone app on the required Pixel 3 XL after a fresh debug build/install. Capture and inspect default portrait, portrait at 1.3 font scale, landscape at 1.3, and the exact bright (`2026-10-25 12:00`) and dark/night (`2026-10-25 23:00`) fixture states. The prior captures under cycles 013/014 provide context; capture all five states again from the fresh build so one consistent current build supports the disposition. Also exercise immediate daily drag and the proven continuous hold-then-drag sequence to assess the three visual-quality requirements.

Evidence-only cycle. Do not change source, layout, styling, behavior, Gradle configuration, dependencies, or production oxygenWX. If a condition fails, record the actual finding and evidence, leave P.2.6 unmet/BLOCKED, and propose a separate bounded implementation slice; do not fix it here.

The only repository writes allowed are this plan, `.codex/current.md` through cycle lifecycle, `.codex/test-artifacts/015-responsive-readability-review/`, `.codex/history/015-responsive-readability-review.md`, and the minimum P.2.6 status/evidence update in `docs/ROADMAP.md` at closeout. Do not alter other records or roadmap slices.

## Dependencies and evidence context

- P.1.2 is DONE; the required Pixel 3 XL baseline is documented.
- P.2.6 is the sole NEXT roadmap slice and `.codex/current.md` was IDLE at planning time.
- Cycles 013 and 014 recorded the original four passing conditions and the landscape high/low defect. P.2.11 is now DONE with a fresh landscape 1.3-font capture showing the fix. This cycle rechecks all five on one fresh build; it does not claim the older captures are from this build.
- Cycle 014 also established a continuous touchscreen hold-then-drag procedure that produced the exact 12 PM and 11 PM selections. Reuse that proven procedure; do not substitute the unsuccessful cycle 013 shell gesture attempt.

## Functional and visual invariants

- Preserve selected-time authority through font/orientation changes, scale transitions, and release/collapse; zoom alone must not select another time.
- The exact bright and dark states must be corroborated by the hourly selected-time label: Sunday, Oct 25, 2026 at 12 PM and 11 PM respectively.
- Preserve daily drag, stationary hold-then-hourly drag, predictable ownership, and forecast summary/tape agreement.
- Review forecast heading/date, current temperature, conditions, high/low, rain chance, wind, DEMO DATA, and timeline labels as readable text without clipping or overlap. Atmospheric rendering remains decorative.
- Record responsiveness, gesture predictability, and readability dispositions from the fresh installed app. This is a qualitative sample, not a latency benchmark or statistical reliability claim.
- Keep TalkBack, RTL, and reduced-motion acceptance separate; record these conditions as not in scope for this cycle.

For every visual capture, record the active orientation, logical viewport, system font scale, selected fixture time where applicable, and whether the full app viewport is visible. Review the forecast heading/date, temperature, conditions, high/low, rain chance, wind, DEMO DATA label, and timeline labels individually; record pass/fail/not visible for each relevant item and note clipping, overlap, or contrast concerns. A screenshot without its state and review disposition is not acceptance evidence.

## Ordered procedure

1. Confirm the cycle is PLANNED, P.2.6 is NEXT, and run `python scripts/dev.py workflow`. Confirm Pixel 3 XL serial `8AKY0M32X`, model/API, locale, physical and active viewport/density, installed override, font scale, rotation mode, and animation scales. The owner most recently requested portrait orientation; treat locked portrait at font scale 1.0 as the original state to restore, and do not restore automatic rotation.
2. Build and install the current app using the available JDK 17 and Android SDK with command-scoped environment variables. Record the package/build identity. If the Pixel is unavailable or build/install fails, record the exact evidence and stop without substituting a device or APK.
3. With the fresh app installed, capture default portrait at font scale 1.0. Set system font scale to 1.3 and capture portrait. Keep 1.3 and lock landscape; confirm the logical viewport is landscape and capture. Review each at full resolution for all forecast values and timeline labels.
4. Return to locked portrait and font scale 1.0. Select Sunday, Oct 25, 2026 on the tape, enter hourly mode with the stationary hold procedure, and capture the exact 12 PM bright state. Keep a continuous pointer contact while scrubbing to the 11 PM fixture hour, verify the hourly selected label, and capture the dark/night state. Record sky and displayed selection for both captures.
5. Exercise one immediate horizontal daily drag and capture the resulting date/summary. Exercise one stationary hold followed by a continuous hourly drag, capture before release, then release and capture the collapsed daily state. Record ownership, selected-time centering and continuity, responsiveness, and readability. Use touchscreen DOWN/MOVE/UP events on the same contact; the previously proven portrait playhead coordinates are (630,1900) at the 1260×2240 override. Hold stationary for 700 ms, allow 600 ms for scale settling, then send the documented four leftward MOVE events while preserving the same contact, capture before UP, and record the displayed selected hour. Reconfirm coordinates if the active viewport differs. Do not treat an `input swipe` as a continuous hold-and-drag substitute.
6. Review all five condition captures and interaction captures at full resolution. Record per-condition readability outcomes and the three cross-cutting dispositions. Compare with previous findings and P.2.11's fresh landscape capture. Any newly confirmed defect gets a separate bounded roadmap slice; do not implement it.
7. Restore the portrait lock, font scale 1.0, and original animation settings recorded in step 1; leave the display-size override unchanged. Confirm and record restored values. Record exact commands, outcomes, evidence filenames, per-item review dispositions, limitations, and follow-up in the artifact README and cycle history. Inspect final diff and `git diff --check`. Close the cycle; mark P.2.6 DONE only if every condition passes, otherwise retain BLOCKED with the exact finding and follow-up. If device, build, or capture prerequisites prevent completion, record the failed command and output, stop without substituting a device or APK, and leave P.2.6 BLOCKED.

## Acceptance and evidence

P.2.6 is DONE only when fresh installed screenshots exist for all five conditions and actual review records for each show the essential forecast text and timeline labels readable and unclipped, with no unresolved defect. The installed interaction observations must also record responsiveness, gesture ownership/predictability, readability, and selected-time behavior with evidence. A confirmed failure leaves P.2.6 BLOCKED and requires a separate bounded fix before another closure attempt; a host/device blocker leaves it BLOCKED with the exact blocker and evidence. Do not mark the slice DONE from historical captures, a build result, or a preview alone.

- Capture targets: `.codex/test-artifacts/015-responsive-readability-review/portrait-default.png`, `portrait-font-1_3.png`, `landscape-font-1_3.png`, `bright-2026-10-25-12.png`, `night-2026-10-25-23.png`, `immediate-daily-drag.png`, `hourly-hold-drag.png`, and `release-collapse.png`.
- Artifact index/settings/commands/findings: `.codex/test-artifacts/015-responsive-readability-review/README.md`.
- History: `.codex/history/015-responsive-readability-review.md`.
- Existing screenshot paths are context only; the five acceptance captures must come from the fresh installed build in this cycle.

## Authorized verification

- `python scripts/dev.py workflow`
- Read-only host/device and package inspection.
- `python scripts/dev.py build` with command-scoped `JAVA_HOME=/home/opsman/Android/jdk-17`, `ANDROID_HOME=/home/opsman/Android/Sdk`, and matching `PATH`.
- `python scripts/dev.py run --serial 8AKY0M32X` with the same environment.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/015-responsive-readability-review/<state>.png` and the validated continuous touchscreen interaction procedure.
- Manual installed review of the five conditions and gesture observations above.
- `git diff --check` and final diff inspection.

Do not run unit tests, lint, or contract checks; this evidence-only slice does not authorize them. A successful build or preview alone is not visual evidence.

## Risks and assumptions

- The Pixel 3 XL (`crosshatch`, `8AKY0M32X`) and SDK/JDK paths were available in the last cycle; reconfirm at execution. Do not substitute another device.
- The existing display override is 1260×2240 at 560 dpi in portrait; confirm before relying on the recorded touchscreen coordinates.
- P.2.11 changed only a compact-height presentation branch. A fresh build and all five captures avoid assuming that historical evidence automatically represents the current build.
- Bright/night times are deterministic synthetic fixtures, not claims about actual weather or general contrast compliance.
- Visual acceptance remains bounded to one device and the named states. TalkBack, RTL, reduced motion, and other device sizes are not part of this slice.

## Out of scope

- Any app/source/layout/style/behavior or build-system change, including fixing readability findings.
- TalkBack acceptance (P.2.7), reduced-motion acceptance (P.2.8), RTL/final installed-acceptance closure (P.2.9), and broader device-matrix review.
- Production oxygenWX, integration, external data, tests, lint, or dependency changes.
