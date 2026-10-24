# Cycle 019-talkback-scale-action

Date: 2026-10-24
Roadmap: P.2.7

## Outcome

P.2.7 accepted on Pixel 3 XL / Android 12 / TalkBack 17.1. The owner invoked the timeline daily/hourly custom actions in both directions. At Sunday, Oct 25, 8 PM, the hourly state showed 65 degrees and Partly cloudy; after switching to daily and re-entering hourly, the same date/time and forecast state returned. The selected point had shifted from the plan’s noon target to 8 PM during earlier TalkBack navigation, so continuity is confirmed at 8 PM rather than the exact noon fixture. Access to the actions required a temporary Swipe up → Show custom actions mapping; the original Swipe up mapping and disabled TalkBack service were restored. The focused-state utterance was reported only in paraphrase, and the exact daily-action speech was not captured.

## Verification actually performed

Passed: python scripts/dev.py workflow before closeout; git diff --check. Captured .codex/test-artifacts/019-talkback-scale-action/{talkback-daily-before.png,hourly-after-action.png,daily-after-action.png,hourly-roundtrip.png}. Read-only TalkBack settings and app hierarchies were inspected; talkback-gesture-restored.xml records the original Swipe up assignment. Restored settings readback: enabled_accessibility_services=null, accessibility_enabled=0, font_scale=1.0, user_rotation=0, window_animation_scale=1.0, transition_animation_scale=1.0, animator_duration_scale=1.0. No tests, lint, or contract checks were run.

## Follow-up

Continuity was observed at Sunday, Oct 25, 8 PM rather than the planned noon baseline. The exact full focus utterance and daily-action speech were not transcribed. TalkBack required a temporary `Show custom actions` gesture assignment for this check; the original setting was restored. This acceptance establishes the scale actions and selection round trip on the tested device/configuration, not discoverability under other gesture assignments or behavior on other devices.
