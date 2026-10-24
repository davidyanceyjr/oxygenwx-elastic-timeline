# Cycle 020-reduced-motion

Date: 2026-10-24
Roadmap: P.2.8

## Outcome

Completed the installed reduced-motion review for P.2.8 on the Pixel 3 XL. With Accessibility → Text and display → Remove animations enabled, daily and hourly states remained understandable, the hourly selection was restored after collapse/re-entry, and the exercised gesture sequence remained responsive and predictable. No confirmed defect or follow-up implementation slice was needed. Original system animation settings were restored.

## Verification actually performed

Passed: python scripts/dev.py workflow before and after the cycle; git diff --check. Device observed: Pixel 3 XL (crosshatch), Android 12/API 31, portrait, default font size. Remove animations was read checked=true while active and checked=false after restoration; global window_animation_scale, transition_animation_scale, and animator_duration_scale read 0 while active and 1 after restoration. Actual captures and interaction observations are documented under .codex/test-artifacts/020-reduced-motion/README.md. No build, tests, lint, or contract check was run because this evidence-only plan excludes them.

## Follow-up

P.2.8 exit met. No confirmed defect or implementation follow-up. Installed evidence is limited to this Pixel 3 XL in portrait at default font size, using adb-injected touch events; RTL, TalkBack, large fonts, other devices, extended fixture-horizon boundaries, and frame-time measurement remain unreviewed. Continue with P.2.9 for RTL review and final installed-acceptance disposition.
