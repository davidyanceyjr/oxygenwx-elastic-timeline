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

This archive was produced without Android SDK or emulator. Only source/static and pure Kotlin checks can be performed here. The evidence gap is **deliberately visible**; any later installed review belongs in a new `.codex` cycle and should not rewrite this historical report.
