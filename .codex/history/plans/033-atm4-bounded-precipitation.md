# Cycle plan — 033-atm4-bounded-precipitation

State: CLOSED
Roadmap: ATM.4
Date: 2026-10-25
Objective: Add bounded, deterministic illustrative rain and an explicitly labeled synthetic snow preview driven by typed precipitation and depiction inputs; preserve forecast truth and existing timeline behavior.

## Production boundary

Application files in scope for this slice:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt` — typed precipitation identity and preview-only depiction metadata.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — bounded rain/snow drawing and static fallback.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` — debug-only explicit snow-preview entry used to capture an installed labeled preview without adding a control to the product screen.

Cycle records and evidence:

- Plan: `.codex/plans/033-atm4-bounded-precipitation.md`
- Evidence: `.codex/test-artifacts/033-atm4-bounded-precipitation/`
- Closeout: `.codex/history/033-atm4-bounded-precipitation.md`

The debug-only preview is selected with the `preview_snow` boolean launch extra, checks `ApplicationInfo.FLAG_DEBUGGABLE`, and renders a scene-only synthetic state with a visible `SYNTHETIC SNOW PREVIEW` caption. The owner chose this runtime debug-build gate because this project does not generate `BuildConfig`; no Gradle change is needed. Launch it for evidence with `adb shell am start -n com.oxygen.weather.timeline/.MainActivity --ez preview_snow true`. A normal app launch continues to show `TimeLabScreen`. Do not edit `SampleForecast`, forecast facts, timeline code, gesture handling, or the production screen layout. If this isolated preview cannot be shown without changing forecast semantics or normal app navigation, stop and record the blocker rather than adding user-facing controls.

## Functional invariants

- `SkyCondition.RAIN` may select restrained illustrative rain; rain probability must not affect particle count, speed, density, or any claim of intensity.
- Snow has no source-backed `SkyCondition` in this fixture. It may appear only through an explicit synthetic preview depiction and must carry a visible `SYNTHETIC SNOW PREVIEW` label. Never infer snow from temperature, precipitation probability, or another unsupported value.
- Precipitation state remains typed and immutable at the presentation boundary. The renderer does not write selected time, forecast values, or accessibility state.
- Particle placement and motion are deterministic, clipped to the scene, and bounded by a fixed maximum. Reduced motion and `EffectsLevel.NONE` use a static depiction while preserving truthful forecast condition text.
- Preserve the DEMO DATA label, selected timestamp, summary facts, timeline semantics, RTL chronology, and all gesture ownership/arbitration.
- Forecast values and timeline labels remain readable; precipitation artwork stays out of the central and bottom quiet zones.

## Steps

1. Inspect the current precipitation mapping and renderer; confirm which existing rain marks are driven by `rainChancePercent`, and record the baseline build/device state.
2. Add explicit typed depiction metadata for illustrative rain and preview-only snow. Keep fixture/provider facts unchanged; do not add snow to `SampleForecast`.
3. Replace probability-driven rain marks with deterministic bounded rendering selected from typed depiction state. Provide a static/reduced-motion path and the debug-only `preview_snow` entry in `MainActivity`; keep normal app launch and navigation unchanged.
4. Run authorized static/build checks, install on the Pixel 3 XL, and review the evidence matrix below. Exercise timeline responsiveness, gesture ownership, selected-time stability, and forecast readability on the normal rain screen; review the snow preview as an isolated renderer state.
5. Save actual screenshots and device/configuration notes under the cycle evidence directory; inspect the final diff and close the cycle only if every exit condition passes. Otherwise record the exact blocker and disposition.

## Acceptance and authorized verification

- `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, and `python scripts/dev.py lint` pass. Do not add or run tests in this cycle; the repository instructions prohibit test changes/execution unless the owner asks.
- Focused installed Pixel 3 XL evidence covers rain on the normal screen and the explicitly labeled snow preview in portrait, plus their static/reduced-motion render paths. Record device/build details, fixture state, motion setting, and screenshot paths. Focused checks establish typed rain selection, probability-independent depiction, explicit snow preview selection, readable display zones, and relevant timeline behavior. Cross-app RTL, TalkBack, and font-scale acceptance belongs in separate bounded cycles.
- Rain appears only for typed rain state, and its appearance is unchanged when only probability changes. Snow appears only in the explicit preview state. Source inspection confirms a fixed particle cap and deterministic positions; no probability-to-rate mapping or temperature-based snow inference remains.
- In reduced-motion/`NONE` mode the scene is static and the condition remains understandable from forecast text.
- During installed rain review, daily/hourly scrubbing and main-view gestures remain responsive and predictably owned; precipitation does not obscure facts or timeline labels, and the selected timestamp remains stable through movement/release. The snow preview caption is visible, readable at large font, and exposed as one understandable text semantic.
- `git diff --check` passes. Record exact commands and any unavailable checks in cycle closeout.

Evidence target: `.codex/test-artifacts/033-atm4-bounded-precipitation/`.

**Owner review / failed disposition (2026-10-25):** the owner rejected this cycle's delivery as insufficient: the precipitation logic, effects, and display do not meet the intended slice. Do not treat the captures as acceptance. Close this cycle as failed and leave ATM.4 BLOCKED pending a new bounded implementation plan. The owner clarified that RTL, TalkBack, font-scale, and other app-wide checks are separate cycles and must not be used as ATM.4 blockers.

## Risks / assumptions

- Current rain marks are drawn from probability despite being labeled illustrative; this slice will remove that mapping and use typed `RAIN` identity.
- No snow condition exists in the fixture. The visible preview label must clearly separate the synthetic rendering from forecast facts; the debug-only launch path must not create a new route or control in normal use.
- Pixel 3 XL and Android tooling availability must be confirmed during execution. A build or preview alone cannot satisfy installed visual acceptance.
- Repository instructions prohibit adding or running tests unless the owner asks, so the particle cap and deterministic mapping are verified by source inspection and installed evidence in this plan.

## Out of scope

- Forecast-provider or fixture changes; real precipitation intensity, accumulation, or snow claims.
- Rain probability as an animation or particle control.
- Fog, wind, storm/night redesign, terrain, environments, or atmosphere-wide effects policy.
- Timeline, gesture, selected-time, accessibility, forecast summary, or production oxygenWX changes.
- New permanent UI controls or user-facing navigation for the synthetic preview.
- Adding or running tests without owner authorization.
