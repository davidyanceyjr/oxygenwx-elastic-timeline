# UI development workflow (derived from oxygenWX)

For one bounded design or implementation slice:

```
specify the scoped visual/touch objective
  -> review owning UI and presentation code
  -> implement one bounded change
  -> run focused checks for the slice's values and behavior
  -> compile the debug app when required by the plan
  -> install and capture the affected states when visual acceptance requires it
  -> inspect directly affected interactions and readability
  -> inspect diff and close the plan with scoped evidence
```

Design-slice checks compare supplied forecast values and categories with their intended presentation. They do not independently evaluate meteorological plausibility. Do not add RTL, TalkBack, or broad system/environment matrices to design slices. Assign cross-cutting system acceptance to a dedicated roadmap slice explicitly designated `TEST`. Product-level accessibility and compatibility requirements remain part of the product; dedicated `TEST` slices establish their broad acceptance.

`python scripts/dev.py workflow` validates the project scaffold.
`python scripts/dev.py contract` validates isolation and architecture guards.
`python scripts/dev.py test/build/lint/run/screenshot` are Gradle/adb entry points. Run only commands authorized by the bounded slice and task.

## Available implementation review tools

The current development setup has an X window display and a Pixel test device. Check that the display is available with `test -n "$DISPLAY"`; check that Android devices are connected and authorized with `adb devices -l`. Device availability can change between sessions, so confirm it before planning installed review.

Debug builds include three synthetic forecast profiles for implementation review: a short rain event, a clear-to-storm progression, and an extended cold/cloud/wind/snow progression. They are isolated from the normal sample forecast and are not included in release builds. Build and install the debug app with `python3 scripts/dev.py run`, then open the debug profile picker on the connected device:

```bash
adb shell am start -n com.oxygen.weather.timeline/.data.debug.DebugForecastActivity
```

Choose a profile and scrub the loaded forecast using the regular timeline. For an installed screenshot, use `python3 scripts/dev.py screenshot --serial <device-serial> --output .codex/test-artifacts/<cycle-id>/<name>.png`. Keep actual captures and observations in that cycle's evidence directory. An X display can support window-level inspection; it does not replace installed device evidence for visual or interaction acceptance.

The debug profile definitions live under `app/src/debug/`. The available profiles and their coverage are tracked in the DFM workstream in `docs/ROADMAP.md`. Build/preview availability alone is not visual acceptance: follow only the slice-local installed states and interaction checks in the bounded plan. A design slice does not establish app-wide system acceptance.
