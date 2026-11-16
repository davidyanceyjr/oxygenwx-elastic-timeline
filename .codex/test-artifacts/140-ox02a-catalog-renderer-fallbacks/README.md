# OX-02A execution evidence and closeout

Cycle: `140-ox02a-catalog-renderer-fallbacks`; slice: `OX-02A`.

## Owner decision and outcome

On 2026-11-15 the product owner selected option 1 after the supported-rain
comparison exposed a scene mismatch: retain both fallback routes, record the
mismatch and verification limits, and complete evidence-only closeout. No
renderer changes were authorized or made, and no parity claim is made.

The slice exit is met by recording each route's source, caller/trigger,
reachability evidence, and keep disposition. The reading route is retained
because its exercised supported-rain state differs from the composition route.
The state-only route is retained because its scene-present reachability and
appearance remain unverified. No route was consolidated.

## Route disposition

| Route | Caller and trigger | Source location | Evidence and disposition |
|---|---|---|---|
| Composition (primary catalog) | `MainActivity` calls the `CatalogTimeLabScreen(presentation = ...)` overload for the active catalog. Its composition is retained unless it has weather state while `presentation.timeline.scene` is null. | `MainActivity.kt:240–248`; `CatalogTimeLabScreen.kt:104–120, 181–185` | Installed composition reference captured for the supported-rain state. Keep as the primary route. |
| Reading fallback | The same presentation call filters out a weather-bearing composition when `presentation.timeline.scene == null`; a non-null reading then selects the reading branch. The debug-only `sg2_null_weather` intent extra sets that scene to null. | `MainActivity.kt:156, 240–247, 382`; `CatalogTimeLabScreen.kt:104–120, 186–192` | The debug path was exercised for supported rain. It did not render the rain streaks shown by the matched composition capture. Keep; parity fails for the exercised state, and no consolidation or parity claim is made. |
| State-only fallback | The view-only overload passes null for both reading and composition. The `view.scene` branch is selected only when both remain null and `view.scene != null`. No production caller of the view-only overload was found. The only caller found is the Android test below, which supplies `scene = null`. | `CatalogTimeLabScreen.kt:93–103, 193–200`; `CatalogForecastBrowserErrorAccessibilityTest.kt:48–60` | Scene-present caller, trigger, and runtime appearance remain unverified. Keep; do not infer dead code from the production-source search. |
| View-only no-scene branch | The found Android test calls the view-only overload with `scene = null`. | `CatalogForecastBrowserErrorAccessibilityTest.kt:48–60`; `CatalogTimeLabScreen.kt:93–103, 193–200` | Source inspected only; the test was not run. This call does not exercise the scene-present state-only fallback. Preserve the existing branch. |
| Fixture / Time Lab previews | Existing debug fixture and `TimeLabScreen` preview entry points call `AtmosphericScene(input)` outside the catalog fallback branches. | `MainActivity.kt:219–230, 385–433`; `TimeLabScreen.kt:134–142`; `AtmosphericScene.kt:42–52` | Static source trace; separate entry points remain unchanged and outside this slice. |

## Supported-rain installed comparison

- Device: Pixel 3 XL, serial `8AKY0M32X`.
- Catalog: existing debug replay `sg4-canonical-gaps-v8`, captured from the
  SIMULATED source for Florence, Alabama (`America/Chicago`).
- Selected instant: `2026-10-04T15:30:00Z` (`10:30:00 AM CDT`). Both route
  starts used this same explicit instant.
- Input: the replay supplies `RAIN` at `10:00 AM CDT`; the selected readout
  shows 57°F, 75% precipitation chance, and 0 mph wind. The UIAutomator dump
  also identifies the supplied `10:00 AM CDT` and selected `10:30 AM CDT`.
- Composition reference: `composition-supported.png`.
- Reading candidate, triggered with the debug-only `sg2_null_weather` extra:
  `reading-supported.png`; UIAutomator dump: `reading-supported.xml`.

The matched captures show the same selected-time readout and forecast facts.
Rain streaks are visible over the city in the composition reference and absent
from the reading candidate. This contradicts the approved artwork objective
for this supported-rain state, so the parity gate fails. The reading route is
retained under the approved plan.

A direct timeline swipe on the composition route changed the selected time from
`10:30:00 AM CDT` to `11:26:06 AM CDT`; capture: `composition-selection-check.png`.
The reading route's interaction continuity was not checked.

## Verification and limits

- `python scripts/dev.py workflow` — PASS before activation, after activation,
  after pausing, after resuming for the owner decision, after OX-02A slice
  closure, at `CLOSEOUT`, and after archive when the pointer returned to `IDLE`.
- `git diff --check` — PASS after archive; exit code 0 with no output.
- `sha256sum` over the seven approved behavior/UI board artifacts — all
  fingerprints match the final plan manifest.
- `adb devices -l` — Pixel 3 XL `8AKY0M32X` connected and authorized.
- `python3 scripts/dev.py run --serial 8AKY0M32X` — debug build succeeded; app
  installed and launched.
- `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output
  .codex/test-artifacts/140-ox02a-catalog-renderer-fallbacks/<capture>.png` —
  used for the three PNG captures listed above, with the output path set to
  each capture name.
- `rg -n 'CatalogTimeLabScreen\s*\(' app/src/main/java app/src/androidTest` —
  `MainActivity` calls the presentation overload; the only view-only caller
  found is `CatalogForecastBrowserErrorAccessibilityTest`, which sets
  `scene = null`.
- No app tests, lint, broad accessibility checks, or system/device matrix were
  run. The missing-condition and carried-condition parity comparisons were
  not run. No scene-present state-only route was found or exercised. No
  reading-route direct interaction check was performed. Source-valid-time and
  disclosure parity for a carried condition therefore remain unverified.
- The installed captures establish only the recorded supported-rain mismatch
  on this device and build. They do not establish app-wide or broad system
  acceptance.
- No app source files were changed.
