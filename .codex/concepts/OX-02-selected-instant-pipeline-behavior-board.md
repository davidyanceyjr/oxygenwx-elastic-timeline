# OX-02 behavior board — trace the selected-instant pipeline

**Board image:** `OX-02-selected-instant-pipeline-behavior-board.svg` (editable `.dot` source and PNG review image alongside).

**Status:** Draft for product-owner review; approval is pending. Prepared for cycle 138. This is a read-only route and reachability trace; it does not authorize renderer changes.

## Purpose and selected-time contract

Start with the selected instant in the active catalog state. `CatalogTimelinePresenter.scenePresentation(state)` resolves that instant once, evaluates the `SceneReading`, composes the `FirstCityComposition`, and builds the timeline view from the same instant. `MainActivity` passes the resulting typed `CatalogTimelineScenePresentation` to the presentation overload of `CatalogTimeLabScreen` for the active catalog route. That overload always supplies a reading, so this route chooses composition or reading.

The board traces how that presentation reaches the catalog scene renderer. It distinguishes the composition route, the reading and state-only fallback routes, and fixture / Time Lab previews that enter through a separate renderer path. Reachability claims are labeled as static source trace, runtime exercised, or unverified.

## Catalog route contract

| Route | Caller and triggering condition | Reachability disposition |
|---|---|---|
| **Composition — primary catalog renderer** | `MainActivity` supplies the typed catalog presentation. The screen retains its composition when it has no weather state or `timeline.scene != null`; `CatalogTimeLabScreenContent` renders a retained composition first. | Verified by static caller/state trace. |
| **Reading — active catalog fallback** | The presentation overload always passes `SceneReading`. When a weather-bearing composition is filtered out because `timeline.scene == null`, the screen selects the reading branch. The debug `sg2_null_weather` intent extra can trigger this state. | Static debug path verified; runtime exercise unverified. |
| **State-only — view-only overload route** | The separate `CatalogTimeLabScreen(view, …)` overload passes null reading and composition. Its content selects `view.scene` when present. This route does not follow the active catalog presentation branch above. | Public overload exists; no production caller was found in the OX-02 source trace, so production reachability is unverified. |
| **No catalog scene call** | On the view-only overload, if `view.scene` is absent, none of the `CatalogAtmosphericScene` branches is selected. The active catalog presentation route always supplies a reading and does not enter this case. | Source branch is visible; OX-02 does not prescribe or claim a rendered appearance for this state. |

The composition branch takes precedence over the fallbacks. A fallback remains part of the record while its reachability is unknown; absence of a found production caller is not a removal decision.

## Separate preview routes

Debug fixture previews in `MainActivity` and `TimeLabScreen` call `AtmosphericScene(input = …)` directly. They do not enter the catalog composition / reading / state selection above. Record them as preview entry points, not catalog fallback callers. Their static source callers are verified.

## OX-02 record and boundary

Produce a source-backed selected-instant flow and one record per fallback with its caller, triggering state, source location, and verified or unverified reachability. Classify preview routes separately. Distinguish static source evidence from runtime exercise. OX-02 makes no UI or rendering changes, removes or consolidates no fallback, and does not claim parity or visual acceptance. OX-02A owns any later consolidation decision and its parity evidence. A UI artwork board is not applicable unless the approved scope changes visible output.

## Source anchors

- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — `CatalogTimelinePresenter.scenePresentation` and `CatalogTimelineScenePresentation`.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` — active catalog handoff, `sg2_null_weather` route, and fixture preview callers.
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — screen overloads, composition retention condition, and renderer branch precedence.
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt` — separate preview call.
- `docs/ROADMAP.md`, OX-02 — controlling slice objective, exit, existing static reachability record, and OX-02A boundary.

This board follows the annotated behavior-flow style of the approved OX-01 baseline board. The board and its render are planning artifacts, not installed acceptance evidence.
