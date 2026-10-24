# Cycle 018-talkback-forecast-browser

Date: 2026-10-24
Roadmap: P.2.13

## Outcome

Added the TalkBack daily/hourly forecast browser backed by the existing selected-hour state. Installed Pixel 3 XL evidence and owner confirmation verify selecting Sunday and opening hourly starts at the checked Sunday, Oct 25, 12 PM row; the timeline summary and tape also reflect Sunday. P.2.13 is DONE. P.2.7 remains BLOCKED separately because the original custom scale action was not manually accepted. Compact landscape and 1.3 font screenshots cover the main screen only; browser layouts in those variants, RTL, and reduced motion were not reviewed. TalkBack and device font/orientation settings were restored.

## Verification actually performed

- `python scripts/dev.py workflow` — passed before build and again at close.
- Build, install, and launch:

  ```sh
  JAVA_HOME=/home/opsman/Android/jdk-17 ANDROID_HOME=/home/opsman/Android/Sdk PATH=/home/opsman/Android/jdk-17/bin:/home/opsman/Android/Sdk/platform-tools:/home/opsman/Android/Sdk/cmdline-tools/latest/bin:$PATH python scripts/dev.py run --serial 8AKY0M32X
  ```

  Result: `BUILD SUCCESSFUL`, install `Success`, and `com.oxygen.weather.timeline/.MainActivity` launched.
- The installed TalkBack hierarchy and screenshot show the selected checked row `SUN, OCT 25, 12 PM` first in the visible hourly list. The owner confirmed row activation and Sunday selection.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/018-talkback-forecast-browser/portrait-selected-main.png` — captured. Compact-landscape and `font_scale=1.3` main-screen captures reviewed.
- `git diff --check` — passed.
- No tests, lint, or contract checks were run, as required by the plan.
- Device settings restored: `user_rotation=0`, `font_scale=1.0`, `accessibility_enabled=0`, `enabled_accessibility_services=null`.

## Follow-up

P.2.7 remains blocked pending observed acceptance of the custom daily/hourly scale action. Browser layout at compact landscape and 1.3 font scale, RTL, and reduced-motion remain unreviewed.
