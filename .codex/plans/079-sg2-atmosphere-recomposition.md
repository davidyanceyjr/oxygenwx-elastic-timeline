# Cycle plan — 079-sg2-atmosphere-recomposition

State: ACTIVE
Cycle ID: `079-sg2-atmosphere-recomposition`
Roadmap: SG.2
Objective: Refactor the first-city atmosphere composition and its existing sky, time-cue, cloud, and rain visuals to meet the owner's atmosphere-first target while preserving selected-time and forecast semantics.

## Readiness and board gate

- `.codex/current.md` was IDLE and `python scripts/dev.py workflow` reported `Workflow scaffold: OK`. The owner explicitly rejected SG.2 visual completion and directed slice refactoring. SG.2 is the sole ACTIVE slice; dependent SG.3 remains PLANNED. Cycle 078 is the historical implementation record, not evidence that this refactor passed.
- Cycle 078's Pixel screenshot and current defects are recorded in `.codex/test-artifacts/078-sg2-first-theme-composition/README.md`. The owner's critique supersedes the earlier note's positive art-match language for acceptance.
- **Owner-approved combined behavior/UI artwork board:** `.codex/concepts/SG.2-atmosphere-recomposition-v1.png`, editable source `.codex/concepts/SG.2-atmosphere-recomposition-v1.svg`; generated visual-direction study `.codex/concepts/SG.2-atmosphere-recomposition-study-v1.png`. The owner approved the complete board for the refactor on 2026-10-31. Approved constraints: a prominent near/middle-ground city, about 35–40% sky, softened dusk, larger sun/moon cues near the ends of their illustrative sky cycles and smaller near the high arc, no visible guide path, broad moving sunlight that supported clouds can diffuse, layered irregular soft-edged clouds, and coherent curtains of fine rain strokes. Weather is composed as fields, never scattered icons; forms may be stylized and need not be photorealistic. The owner separately approved the broad moving/diffused sunlight direction on 2026-10-31. The board makes no astronomical claim and does not authorize new weather semantics. It governs composition, visual acceptance, and reviewed states for this bounded first-city refactor; unsupported weather remains absent and the selected instant remains authoritative.
- The board's flow preserves the single selected instant: selected forecast instant and catalog timezone → SG.1 light phase and city treatment → the same time in forecast text and timeline. Missing weather omits condition imagery. Rain chance does not become rain rate or intensity. Sunlight is a broad moving atmospheric field, not only a marker; supported cloudiness may diffuse it. Sun/moon position and scale remain illustrative and make no astronomical claim.
- Board approval satisfies the artwork gate for the bounded work recorded here. It does not change semantic support, replace installed acceptance, or approve study assets as production content.
- **Owner-approved timeline artwork/behavior board:** `.codex/concepts/SG.2-timeline-footprint-proposal-v2.png`, editable source `.codex/concepts/SG.2-timeline-footprint-proposal-v2.svg`. Approved by the owner on 2026-10-31 after review on the persistent X window; the owner approved the revised board again on 2026-10-31 after the focused-weekday treatment was updated. The board uses the actual installed Pixel capture as its base and proposes an approximately 120 dp, two-band timeline: date context in band one; scrub rail, playhead, and selected-time label in band two. Move the forecast readout and lower facts upward about 20 dp to accommodate it. Preserve selected-instant authority, daily/hourly scale semantics, existing gestures, and forecast fields. Approval authorizes this bounded layout refactor and installed evaluation; it does not change data or interaction contracts.
- The owner directed that the focused weekday stand out in the date row, then specified its final size as 20 dp. Keep it bold and uppercase; keep all other weekday labels 12 dp, regular, and uppercase. The revised board showing this treatment is owner-approved as of 2026-10-31. This is a date-row legibility treatment only; selection rules and time semantics stay unchanged.

## Production boundary

- First-city composition, sky treatment, cue, cloud marks, and rain appearance on the active catalog route only.
- Expected code boundary: `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`, `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt`, first-city resources under `app/src/main/res/drawable-nodpi/`, and `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` for the specifically approved two-band timeline footprint and forecast/facts placement. No unrelated screen or timeline redesign.
- The refactor may replace the current generated city plates or Compose drawing approach as needed to meet the approved art board. Keep one coherent city camera and stable landmarks across time-of-day treatments. Keep the selected-time/theme contract local; do not introduce a generic scene framework.
- Rendering keeps selected time, catalog zone, `SceneReading.lightPhase`, and typed `SceneVisualState` as its inputs. Unsupported/missing weather draws no asserted weather condition. Rain artwork remains explicitly illustrative; probability is not intensity. No new source fields, weather semantics, provider, location logic, or alert behavior.

## Functional invariants

- The selected `Instant` remains the only scene time and stays paired with the readable forecast projection. Theme rendering cannot select, clamp, interpolate, or retarget time.
- The same city landmarks/camera persist across selected-time phases; only the approved light, sky, cue, and supported weather visuals change.
- Sunlight is a broad atmospheric gradient/halo that moves across the sky with the selected-time sun cue; it must remain visible as light across the atmosphere rather than as an isolated yellow dot. Supported cloudiness may diffuse that field. If weather/cloud support is unavailable, do not invent cloud diffusion or weather meaning.
- Decorative sun/moon imagery does not claim actual location/date-specific astronomy. Remove the dashed path/tracking line. If the approved board retains the proposed cue-size curve, both sun and moon cues vary from smaller near the high part of the theme-designed sky path to larger near its ends, using selected time only. This does not claim actual sun angle, sunrise/sunset, or moon phase.
- Clouds and rain must form composed, coherent weather fields rather than randomly scattered icons. Clouds use layered, irregular, soft-edged silhouettes, not touching circles; stylized forms are sufficient and photorealism is not required. Rain falls in coherent curtains of fine, short strokes rather than arbitrary marks or long diagonal comets; it remains absent when rain is not supported.
- Forecast facts, source/provenance labels, and the bottom timeline stay readable. Scene updates do not steal gestures or make scrubbing perceptibly lag.
- The timeline occupies about 120 dp in two bands: weekday/date context first, scrub rail with playhead and selected-time label second. Shift the central forecast readout and lower facts upward by about 20 dp as shown in the approved screenshot-based board. Keep the selected instant, daily/hourly scale semantics, gestures, and forecast facts unchanged.
- The selected weekday is visually prominent in the date-context row (20 dp, bold uppercase label); unselected weekday labels are 12 dp, regular, and uppercase.
- Static/reduced-motion output preserves the same selected-time and weather meaning. Existing DEMO DATA truth remains visible.

## Steps after board approval and activation

1. Rerun `python scripts/dev.py workflow`; inspect the exact installed active catalog route, current asset files, scene bounds, selected-time handoff, and existing history/evidence. Keep the existing worktree changes; do not clean or revert unrelated edits.
2. Translate the approved board into a focused replacement treatment. First review the city occupancy, city contrast/detail, sky share, cue size/path absence, cloud silhouette, and rain streak quality using static/time-selected renderings before wiring any larger changes.
3. Implement only the composition and existing sky/cue/cloud/rain appearance bounded above, plus the board-approved two-band timeline footprint and associated vertical placement. Keep typed data and timeline behavior unchanged. If the approved target cannot be met in this scope, record the concrete gap and stop for a new bounded plan.
4. Build and install the app on Pixel 3 XL; capture the approved portrait states plus 360×640 dp compact states. Check city prominence and stable landmarks, sky share, cue, cloud/rain forms, expanded timeline readability, text/tape clearance, and observe daily/hourly scrub ownership and responsiveness.
5. Review default and 1.3 app font scale where supported. Record RTL and reduced-motion applicability and only affected-state checks. Prior cycle 078 owner direction excluded system-wide TalkBack, RTL, font-scale, motion, and accessibility test suites; this plan does not authorize those broad suites. Keep focused app-local checks within the approved board and record any unverified condition explicitly.
6. Run `python scripts/dev.py workflow`, `python scripts/dev.py build`, and `python scripts/dev.py lint --serial 8AKY0M32X` as applicable. Do not add or run automated tests; none are authorized. Capture actual installed screenshots and manual review observations under `.codex/test-artifacts/079-sg2-atmosphere-recomposition/`. Inspect `git diff --check` and the complete diff before closeout.

## Acceptance and evidence

- Owner-approved combined atmosphere board and screenshot-based two-band timeline board, plus owner constraints, are recorded in the roadmap and plan before implementation.
- Pixel 3 XL portrait and compact installed captures show a city with clear near/middle detail and a balanced sky consistent with the approved board. The time cue has the approved visible scale and has no instructional-looking path. Clouds and rain read as coherent composed weather fields, with organic irregular cloud silhouettes and no randomly scattered icon marks. Rain no longer resembles comets. Each issue is judged at normal phone viewing size, not only enlarged inspection.
- Morning, noon, dusk, and night preserve stable city landmarks while showing the approved light treatments. Daytime captures at distinct selected times visibly move the broad light gradient across the sky with the sun cue; supported clouds visibly soften/diffuse it. Cloudy, supported-rain, and unavailable-weather captures demonstrate correctly bounded weather marks; unavailable weather never looks like clear weather.
- Forecast text remains legible and the bottom two-band timeline stays unobscured in portrait/compact and default/large app-font review. Manual scrub observations confirm selected-time authority, timeline responsiveness, and gesture ownership remain intact. Record exact device/build/viewport/font/locale/input/motion settings and every unchecked condition.
- Evidence index: `.codex/test-artifacts/079-sg2-atmosphere-recomposition/README.md`; captures in that directory. Closeout target: `.codex/history/079-sg2-atmosphere-recomposition.md` and `.codex/history/plans/079-sg2-atmosphere-recomposition.md`.
- No visual/device acceptance is claimed from the board, generated study, build, or preview alone.

## Risks and unresolved choices

- The generated scene study communicates city prominence, cloud character, light, and texture; it is not a final shipped asset. Match the approved design intent with renderable implementation assets while preserving the stable city composition and frame responsiveness.
- The current SG.2 implementation reuses Compose primitives from the prior atmosphere renderer. Reworking these visuals may require authored layered images or significantly different rendering; preserve stable city geometry and fit an asset size/render path compatible with timeline responsiveness.
- Existing cycle 078 tests checked semantic/stability cases but not visual quality. This cycle relies on installed evidence and manual observations, not extra automated tests.

## Out of scope

- New forecast facts, semantic scene tracks, event timing/support, provider/location/source behavior, timeline gestures or scales, controls/navigation redesign beyond the approved footprint and vertical placement, alerts, second theme, theme picker/downloads, animation/performance framework, or deprecated oxygenWX app changes.
- Broad system-wide accessibility/TalkBack, RTL, font-scale, or motion test suites excluded by prior owner direction.

## Plan status

**ACTIVE — owner board approval recorded 2026-10-31.**
