# Cycle 152-ox24-scene-asset-swap

Date: 2026-11-18
Roadmap items: OX-24

## Outcome

OX-24 completed: one First City CANOPY drawable now swaps through an authored typed reference; the delivered olive asset preserves identity, pass depth, geometry, selected instant, weather facts and timeline in matched installed synthetic replay captures.

## Slice outcomes

- **OX-24 — DONE:** First City CANOPY selects the bundled olive asset through authored definition data; fixed-instant installed captures show changed foliage with the same scene identity, pass depth, bounds, sky, city, forecast facts and timeline. Switching the declaration back to BASELINE restored the original adapter choice without a renderer edit; OLIVE is delivered. Evidence: .codex/test-artifacts/152-ox24-scene-asset-swap/README.md, .codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png, .codex/test-artifacts/152-ox24-scene-asset-swap/fixed-olive.png.

## Verification actually performed

- python scripts/dev.py run --serial 8AKY0M32X: baseline and olive builds installed successfully on Pixel 3 XL.
- python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/152-ox24-scene-asset-swap/fixed-baseline.png and fixed-olive.png: captured same 2026-10-28T17:00:00Z synthetic replay; visual comparison met scoped exit.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest.canopyAdapterUsesDeclarationAtSelectedInstant: passed for baseline restoration and delivered olive choice; an initial temporary comparison assertion failed and was corrected before both passing runs.
- git diff --check: passed; python scripts/dev.py workflow: passed at CLOSEOUT.
- Limits: no broad device, accessibility, font, RTL or motion matrix; earlier unmatched live-catalog captures excluded from acceptance.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/152-ox24-scene-asset-swap/`. Archived plan: `.codex/history/plans/152-ox24-scene-asset-swap.md`. See the plan for stage checkpoints and exact boundaries.
