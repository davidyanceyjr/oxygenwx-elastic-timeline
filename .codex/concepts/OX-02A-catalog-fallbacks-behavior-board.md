# OX-02A behavior board — catalog renderer fallback dispositions

**Status:** Draft for product-owner review; approval is pending. Prepared in cycle `140-ox02a-catalog-renderer-fallbacks`.

**Board image:** `OX-02A-catalog-fallbacks-behavior-board.svg` (editable Graphviz `.dot` source and PNG review image alongside).

## Purpose

Resolve the reading and state-only catalog renderer fallback routes from OX-02. Keep the composition renderer as the primary catalog route. Consolidate a fallback only when its actual caller and triggering state are understood and scoped parity evidence shows that its replacement preserves the same selected-time facts, disclosures, and scene state.

## Route map and present evidence

| Route | Caller and trigger | Current confidence | Proposed disposition |
|---|---|---|---|
| **Composition — primary** | `MainActivity` calls the presentation overload. The composition is retained when it has no weather state or `timeline.scene` is present. | Production caller and branch are statically traced. Runtime exercise is not recorded. | Keep as the primary catalog route. |
| **Reading — active-catalog fallback** | The presentation overload supplies a `SceneReading`. If a weather-bearing composition is filtered because `timeline.scene == null`, the reading branch is selected. `sg2_null_weather` statically exposes that state. | Static debug path verified by OX-02; runtime exercise remains unverified. | Candidate for consolidation into the composition route only after the parity gate below passes. Otherwise retain it. |
| **State-only — view-only overload** | `CatalogTimeLabScreen(view, …)` passes null reading and composition; `view.scene != null` selects the state renderer. No production scene caller was found. | Branch is statically visible; a scene-present caller and runtime exercise are unverified. | Keep while unverified. Do not remove based only on the absence of a production caller. |
| **View-only, no scene call** | `CatalogForecastBrowserErrorAccessibilityTest.forecastBrowserExposesApplicableRefreshError` calls the view-only overload with `scene = null`; this selects no catalog scene renderer. The screen foreground still renders. | Test caller and input are visible in source; the instrumentation test was not run. This is not a scene-present state-only caller. | Preserve its no-scene behavior; do not treat it as evidence for the state-only scene route. |
| **Fixture / Time Lab previews** | Direct `AtmosphericScene(input = …)` preview entry points. | Static callers are recorded separately by OX-02. | Remain outside catalog route selection and unchanged. |

## Parity gate before a reading-route consolidation

At the same exact selected instant, compare the composition reference with the reading candidate for:

1. A supported condition and its independently supported forecast facts.
2. A missing condition: preserve an unavailable condition and any independent facts; do not invent a weather claim.
3. An eligible carried condition: preserve the original condition source time and visible disclosure.
4. The affected scene states, condition symbol and wording, selected-time readout, and timeline selection.

Use scoped installed captures and direct interactions to demonstrate any visible change or claimed parity. The OX-02 static trace and this board do not constitute runtime exercise, installed appearance evidence, or acceptance.

## State-only route rule

Record the caller, trigger, source, and reachability for the scene-present state-only branch if a real route is found. The existing Android test caller passes `scene = null`, so it only identifies the no-scene branch in source. Keep the state-only route while its scene-present reachability and behavior remain unverified. Do not infer that a public overload is dead from a production-source search alone.

## Boundaries

- Preserve the exact selected instant and composition-first precedence.
- Keep fixture and Time Lab preview renderers separate from catalog fallbacks.
- Make no app, fixture, or visible-output change during board preparation.
- Keep source-trace, runtime, and installed visual evidence claims distinct.
- Do not add broad accessibility or system acceptance to this slice.

## Source anchors

- OX-02 route record and source trace: `.codex/test-artifacts/138-ox02-selected-instant-pipeline/README.md`.
- Active route: `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt:240–248` and `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt:105–120, 181–192`.
- View-only route and branch precedence: `CatalogTimeLabScreen.kt:93–103, 124–135, 181–200`.
- Known view-only test call with `scene = null`: `app/src/androidTest/java/com/oxygen/weather/timeline/ui/CatalogForecastBrowserErrorAccessibilityTest.kt:24–59`.
- Forecast scene source-time and missing-data rules: `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:141–160`.

