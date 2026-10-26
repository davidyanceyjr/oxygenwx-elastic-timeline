# Cycle plan — 036-atm5-fog-wind-primitives

State: CLOSED INCOMPLETE
Roadmap: ATM.5
Objective: Add reusable illustrative haze and turbulence preview effects with honest missing-input behavior and installed acceptance.

Concept artwork: APPROVED BY OWNER on 2026-10-25. Approved artifact: `.codex/concepts/ATM.5-fog-wind-preview.png`; retained review source: `.codex/concepts/ATM.5-fog-wind-preview-candidate-v4.png`. The approved direction has a richer background and midground, a haze effect that becomes increasingly perceptible, and forecast text directly over the scene without a data box. As haze increases, the distant ridge itself softens and loses contrast. Keep cloud forms inside the scene bounds. The board's rain/snow intensity panels are visual references only; this slice implements haze/turbulence primitives and does not expand ATM.5 into ATM.4 precipitation behavior. Existing installed screenshots are acceptance evidence, not concept artwork.

## Production boundary

Standalone Oxygen Time Lab only. Expected application changes are limited to:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt` for explicit preview state and renderer-independent haze/turbulence inputs. Do not infer fog, visibility, or wind direction from existing forecast fields.
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` for reusable haze and generic turbulence drawing, with effects gated to explicit preview inputs and a readable static path.
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt` for debug-only preview entry points and persistent on-screen `ILLUSTRATIVE PREVIEW` labeling, if required to capture the states.

Cycle records may update this plan, `docs/ROADMAP.md`, `.codex/test-artifacts/036-atm5-fog-wind-primitives/`, and `.codex/history/036-atm5-fog-wind-primitives.md`. Do not edit production oxygenWX or introduce network, provider, location, or persistent preview controls.

## Functional invariants

- Existing `SkyFrame`, fixture forecasts, displayed weather facts, selected timestamp, timeline projection, gestures, accessibility semantics, and navigation remain unchanged.
- Missing visibility and wind-bearing inputs produce no fog or directional claim. Existing wind speed alone must not imply direction or measured turbulence.
- Haze/turbulence are enabled only by explicit debug preview values. Every preview screen labels its effects as illustrative and does not make them appear to be fixture forecast truth.
- Time-varying preview strength is a renderer-only profile keyed by the absolute `selectedHour` fixture-hour offset. Use independent normalized haze and turbulence channels with these synthetic samples: `(hour, haze, turbulence) = (0, .10, .20), (24, .25, .70), (48, .55, .35), (72, .85, .80), (96, .45, .45), (120, .15, .20)`. Interpolate each channel linearly between adjacent offsets for fractional selected hours; clamp values before/after the sampled range to the nearest endpoint. Preview mode gates channels: haze enables haze only, turbulence enables turbulence only, combined enables both, and NONE enables neither (zero strength).
- Haze rendering uses the selected normalized haze strength as a bounded depth treatment: blend only the distant ridge toward the scene's horizon color by at most `0.30 * hazeStrength`. Keep foreground terrain, sky details, forecast text, and timeline drawing unchanged. Do not apply a full-scene wash. If installed review shows the ridge treatment is too subtle, a faint veil may be added behind scene content at the horizon, capped at `0.10 * hazeStrength`; retain it only if facts and timeline remain readable. This is illustrative depth rendering, not a visibility measurement or wind claim.
- Preview strengths depend only on the explicit debug preview mode and `selectedHour`; they must not be inferred from `SkyFrame`, wind speed, precipitation, or any other forecast field. They do not alter forecast projection, displayed facts, or selected time. `selectedHour` remains authoritative in both daily and hourly views; zoom and wall-clock time do not drive effect strength. Reduced-motion/static presentation renders the selected-time sample without animation. Turbulence remains symmetric and non-directional.
- Haze is a bounded depth/contrast treatment; turbulence is generic and restrained. Both keep forecast text, landscape, and timeline recognizable and readable.
- The scene remains full bleed: do not add a foreground card/box around current or selected-time facts, and keep cloud artwork clear of viewport edges so it is not clipped.
- When scrubbing, effect state follows the authoritative selected timestamp deterministically. Blend adjacent preview samples continuously, including fractional selected hours, so haze/turbulence do not pop at sample boundaries; denser haze reduces distant midground contrast progressively while preserving foreground facts and their legibility. Do not use wall-clock animation as the source of effect intensity or change the selected timestamp during transitions. Static/reduced-motion presentation holds the value for the selected time.
- Static and reduced-motion presentation retains the same preview meaning without animation. No effect intercepts gestures or changes selected time.
- Preserve DEMO DATA labeling and all existing rain/snow semantics.

## Steps

- [ ] Inspect the existing scene projection/renderer, debug preview path, ATM.0 baseline records, and ATM-D authority; identify exact preview inputs and installed states without changing the forecast contract.
- [ ] Add reusable typed haze and generic turbulence primitives gated exclusively by explicit debug preview inputs; provide static/reduced-motion behavior and a visible illustrative label. Keep the drawing clear of forecast facts and bottom timeline.
- [ ] Add focused presentation tests for exact preview samples, linear midpoint interpolation, endpoint clamping, channel gating, no effect preview by default even when wind speed exists, and preservation of forecast precipitation/amount values through preview mapping. Run the focused test plus authorized contract/build checks.
- [x] Obtain owner approval for the ATM.5 concept artwork at `.codex/concepts/ATM.5-fog-wind-preview.png`; approval and visual constraints are recorded above.
- [ ] Install and review only the ATM.5 states on Pixel 3 XL in portrait, using the device's default display/font scale and LTR locale; record actual device settings. Review preview absent and each preview present state, plus representative selected-time scrub transitions across sample offsets and between them. Confirm the illustrative label, static/reduced-motion effect appearance, smooth deterministic transitions, no unsupported weather claim, full-bleed facts without a data box, clouds within scene bounds, and effects clear of forecast text and timeline. For haze, compare low, middle, and high selected strengths: the haze effect must become more perceptible as the distant ridge softens, while facts and timeline remain readable. Add the optional faint horizon veil only if the ridge treatment is too subtle, and retain it only if the same readability check passes. Do not run or add app-wide RTL, TalkBack, font-scale, or unrelated layout/interaction checks for this slice.
- [ ] Selected-time effect profile is not implemented in the current working-tree changes. Close this cycle incomplete and carry the profile into a separate bounded follow-up cycle.

## Acceptance/evidence

The roadmap exit is met only when absent inputs produce no claimed fog/wind effect, debug preview states are clearly identified as illustrative, haze/turbulence leave forecast facts and timeline readable with a static fallback, and any time-varying illustrative effect follows selected time continuously without wall-clock-driven intensity or timestamp changes. Haze strength must soften the distant ridge progressively without a full-scene wash; any optional horizon veil must remain faint and pass the same readability review. The scene remains full bleed with no data box and no clipped clouds. Focused mapper tests cover the ATM.5 state contract, installed Pixel 3 XL review covers preview-present and absent-input states plus representative scrub transitions, and approved concept artwork is present at the named path with approval recorded in this plan. This is a slice-focused review, not an application-wide RTL, TalkBack, font-scale, or layout matrix. Include actual screenshots and device/settings details in `.codex/test-artifacts/036-atm5-fog-wind-primitives/README.md`; screenshots are evidence targets until captured.

Authorized checks during execution: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the focused ATM.5 presentation test requested by the owner, `python scripts/dev.py build`, and installed `python scripts/dev.py run` / `python scripts/dev.py screenshot` when available. Do not add or run unrelated tests. A build or preview alone is not visual acceptance. Record any unavailable device check as an explicit limitation rather than claiming the exit.

## Owner-directed roadmap maintenance

Per the owner's follow-up request, unfinished roadmap slices were reviewed and their planned verification narrowed to evidence and tests for each slice's own contract. Broad RTL, TalkBack, font-scale, and unrelated application-wide matrices are not prerequisites for those slices. Each unfinished slice has a specific concept-art brief and approval target. ATM.5 artwork is now approved; other unfinished slices remain pending owner review. This documentation update records the approved scrub-motion direction and does not implement it.

## Risks / assumptions

No typed fog/visibility or wind-bearing source exists. This slice is therefore limited to explicit developer previews; it does not change production-facing projection or assert live weather. The current debug preview path and installed Pixel 3 XL are assumed available. If a legible static treatment cannot coexist with the foreground facts and bottom timeline, preserve the exact failure and do not claim ATM.5 DONE. The renderer may tune within the specified ridge-tint and optional veil caps to achieve a clear, readable result.

## Out of scope

No forecast fixture or provider schema changes; no fog, visibility, wind-direction, or physical turbulence inference; no production preview control; no timeline/gesture/accessibility redesign; no storm/night refinements, environment packs, or animation lifecycle/performance policy; no production integration; no RTL, TalkBack, font-scale, or other application-wide test matrix in this slice.
