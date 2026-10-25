# Codex task — adopt and plan Oxygen's Living Atmosphere Engine

> Historical intake instructions for cycle 023, completed and recorded in
> `.codex/history/023-atmosphere-spec-intake.md`. The current authority and
> implementation order are `docs/SPECIFICATION.md`,
> `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP.md`, and
> `.codex/current.md`; the intake-only instructions below are not a current
> work directive.

You are working in `davidyanceyjr/oxygenwx-elastic-timeline`, **not** the older `oxygenWX` project.

The owner requests a structured, two-phase UI expansion:

1. A layered and responsive **Living Atmosphere Engine** (lighting, day/night, cloud depth, rain/snow, fog, wind and restrained storm effects).
2. Interchangeable **Environment Packs** (Neutral baseline, then Alpine and Coastal), changing scenery without changing forecast values, timeline physics, gestures or accessibility. Further packs remain candidates.

## Your first bounded task: SPECIFICATION INTAKE ONLY

1. Read `AGENTS.md`, `.codex/current.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, the applicable `.codex` plan/history, and the new `docs/OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md` (OXY-ATM-001).
2. Inspect the **current local working tree**, including any uncommitted edits, and these code paths: `ui/TimeLabScreen.kt`, `ui/AtmosphericScene.kt`, `ui/ElasticForecastTimeline.kt`, `ui/AccessibleForecastBrowser.kt`, `presentation/ForecastProjection.kt`, and `data/SampleForecast.kt`. Compare against OXY-ATM-001's cited GitHub baseline `b8477987` and report discrepancies.
3. Treat `docs/SPECIFICATION.md` as controlling on prototype isolation, demo data and navigation. Treat adopted `OXYGEN_EXPERIENCE_SPECIFICATION.md` as experience authority. OXY-ATM-001 is **proposed**, not a code authorization. Flag contradictions requiring owner approval; do not silently replace existing authorities.
4. Use the repository's established `python scripts/dev.py workflow` and `.codex` lifecycle. Plan a single bounded **documentation/intake** slice to adopt or amend OXY-ATM-001 and record the proposed `ATM.*` / `ENV.*` roadmap slices in the project's required leaf-slice format. Keep stable slice IDs distinct from chronological cycle IDs. Do not activate a second cycle.
5. Preserve the current behavior and all existing UI/test source. In particular, DO NOT change the fixed-playhead time shuttle, uniform-hourly long press, central ±day swipe, double-tap Now, accessible forecast browser, or DEMO DATA semantics. Do not implement pinch lenses or new gesture handlers during this task.
6. Run the appropriate **documentation-only** checks for the selected cycle and inspect the final diff. Record exact verification performed and not performed. Do not claim Android build/install/device acceptance if none ran.
7. Present the new/updated authority order, proposed ordered slices, first implementation boundary (ATM.1), any conflicts or decisions needing owner sign-off, and your next recommended single cycle.

## Following adoption (do NOT do this as part of intake)

Implement one ATM slice at a time: pure state/adapter → daylight/skies → cloud depth → precipitation → fog/storm → reduced-motion/lifecycle/performance gate; only then proceed to Environment Pack slices. Every visual slice must preserve the three project hard rules: **responsive timeline, predictable gestures, readable facts**. Record actual installed Pixel 3 XL evidence with the required font/orientation/motion/RTL and accessibility checks relevant to that slice.

The weather background is a **noninteractive renderer**. It must never intercept user touch or supply an alternate source of meteorological facts. The demo has no trustworthy snow, rainfall intensity, wind direction, fog visibility, or solar ephemeris data; do not fabricate these to drive effects.

**Deliver the intake and roadmap proposal without starting a large refactor.**
