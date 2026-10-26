# UI development workflow (derived from oxygenWX)

For one bounded change:

```
specify exact visual/touch objective
  -> review owning UI and presentation code
  -> implement one bounded change
  -> focused unit/contract check
  -> compile Gradle debug app
  -> install on device/emulator
  -> capture screenshot and record gesture behavior
  -> check accessibility, RTL and reduced motion
  -> inspect diff and close plan with evidence
```

`python3 scripts/dev.py workflow` validates the project scaffold.
`python3 scripts/dev.py contract` validates isolation and architecture guards.
`python3 scripts/dev.py test/build/lint/run/screenshot` are Gradle/adb entry points.

## Available implementation review tools

The current development setup has an X window display and a Pixel test device. Check that the display is available with `test -n "$DISPLAY"`; check that Android devices are connected and authorized with `adb devices -l`. Device availability can change between sessions, so confirm it before planning installed review.

Debug builds include three synthetic forecast profiles for implementation review: a short rain event, a clear-to-storm progression, and an extended cold/cloud/wind/snow progression. They are isolated from the normal sample forecast and are not included in release builds. Build and install the debug app with `python3 scripts/dev.py run`, then open the debug profile picker on the connected device:

```bash
adb shell am start -n com.oxygen.weather.timeline/.data.debug.DebugForecastActivity
```

Choose a profile and scrub the loaded forecast using the regular timeline. For an installed screenshot, use `python3 scripts/dev.py screenshot --serial <device-serial> --output .codex/test-artifacts/<cycle-id>/<name>.png`. Keep actual captures and observations in that cycle's evidence directory. An X display can support window-level inspection; it does not replace installed device evidence for visual or interaction acceptance.

The debug profile definitions live under `app/src/debug/`. The available profiles and their coverage are tracked in the DFM workstream in `docs/ROADMAP.md`. Build/preview availability alone is not visual acceptance: follow the applicable installed states and interaction/accessibility checks in the bounded plan.
