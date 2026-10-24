# Cycle plan — 020-reduced-motion

State: CLOSED
Roadmap: P.2.8
Objective: Review installed reduced-motion behavior and record evidence-backed acceptance disposition.

## Production boundary

Evidence-only cycle. May create or update only:

- `.codex/test-artifacts/020-reduced-motion/` for actual device captures and a findings README.
- `.codex/history/020-reduced-motion.md` at closeout.
- `.codex/history/plans/020-reduced-motion.md` at closeout.
- `.codex/current.md` and this plan for cycle lifecycle.
- `docs/ROADMAP.md` only to maintain the slice status and record any separately scoped confirmed follow-up.

No application source changes are authorized in this cycle.

## Functional invariants

- The selected timestamp remains authoritative and does not jump because of an animation or scale change.
- The actual fixture horizon remains the time bound; the review must not imply forecast intervals beyond it.
- Daily/hourly scale, forecast values, and DEMO DATA semantics remain understandable.
- The timeline remains responsive, gesture ownership remains predictable, and forecast values and labels remain readable.
- Reduced-motion review does not change the production oxygenWX navigation contract or introduce production integration.

## Steps

- [x] Confirm the installed Pixel 3 XL baseline, Android version, active animation/reduced-motion setting, selected timestamp, and visible scale before changing the system setting. Use portrait at the default font scale; record RTL and large-font review as out of scope for this cycle.
- [x] Enable the platform reduced-motion/animation setting supported by this Android version; record its exact name and readback. If no supported setting is available, record the available controls and blocker instead of substituting an unrelated setting.
- [x] With the setting enabled, capture the daily and hourly scales and exercise a scale change, scrub, release/collapse, and re-entry. Keep the same fixture time selected through scale changes and record before/after time labels.
- [x] Record whether motion is reduced or absent where expected, whether selected time and scale remain understandable, and evidence-backed pass/fail dispositions for responsiveness, gesture predictability, and readability.
- [x] Restore the original system setting and verify its readback; preserve screenshots, setting/readback details, and observed outcomes in the artifact README.
- [x] No defect was confirmed; no follow-up slice is required.

## Acceptance/evidence

On the installed Pixel 3 XL, the exact system setting and app response are recorded. Actual captures under `.codex/test-artifacts/020-reduced-motion/` show representative daily and hourly states with reduced motion enabled. Observations cover scale change, scrubbing, release/collapse, and re-entry; selected-time continuity and understandable scale receive explicit dispositions. Timeline responsiveness, predictable gesture ownership, and readability each receive evidence-backed dispositions. If the platform has no supported setting, the blocker is recorded and P.2.8 remains incomplete. Any confirmed defect has a separate bounded follow-up. The original setting is restored and read back.

This is installed acceptance evidence, not a source change. No unit tests, lint, build, or contract check is authorized by this evidence-only slice. Run `python scripts/dev.py workflow` before and after the cycle and `git diff --check` at closeout. Do not claim acceptance for other devices, RTL, accessibility, large fonts, or unreviewed settings.

## Risks / assumptions

The installed Pixel 3 XL and Android settings flow used for prior evidence remain available. Android versions expose different animation/reduced-motion controls, so record the exact control and actual readback instead of assuming a universal setting label or effect. A screenshot alone cannot establish motion behavior; interaction observations are required. If there is no suitable platform setting, this evidence-only slice cannot meet its exit and must record the blocker. Restore the original device setting even if acceptance fails.

## Out of scope

Application behavior or source changes; defect fixes; RTL review; TalkBack review; large-font review; other devices; production integration; broad performance or frame-timing measurement; tests, lint, build, and contract checks; changes outside the listed record/evidence paths.
