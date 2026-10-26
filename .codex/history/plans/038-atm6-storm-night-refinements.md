# Cycle plan — 038-atm6-storm-night-refinements

State: ACTIVE
Roadmap: ATM.6
Objective: Add restrained, time/condition-cued storm and night scene refinements with legible facts and static/reduced-motion presentation.

## Production boundary

Standalone Oxygen Time Lab only. Expected application changes are limited to:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualState.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt`
- `app/src/main/java/com/oxygen/weather/timeline/MainActivity.kt`
- `app/src/test/java/com/oxygen/weather/timeline/presentation/atmosphere/SceneVisualStateMapperTest.kt`
- `.codex/test-artifacts/038-atm6-storm-night-refinements/`, this plan, the ATM.6 roadmap evidence/status, and `.codex/history/038-atm6-storm-night-refinements.md` at closeout.
- Owner-approved concept: `.codex/concepts/ATM.6-storm-night.png` (approved 2026-10-25). The draft candidate is retained at `.codex/concepts/ATM.6-storm-night-candidate.png`; concept artwork is not installed acceptance evidence.

No production oxygenWX integration, data/provider/network/location changes, forecast-value or timeline/gesture changes, alert behavior, or unrelated atmospheric redesign.

Debug-only acceptance support: add intent-gated illustrative storm and clear-night scene previews with a selected-hour input in debug builds. The storm preview changes only scenic depiction; the clear-night preview suppresses scenic clouds/effects. Both retain the selected fixture timestamp and forecast facts, are visibly labeled illustrative, and are unavailable in non-debug builds. They do not add forecast records or claim fixture/provider data contains these states.

## Owner scope decision — 2026-10-25

The owner chose to keep the installed baseline landscape geometry and adapt storm/night styling to it. The approved concept's mountain/forest terrain is reference artwork only for this cycle; it does not authorize replacing the current abstract ridges. Storm/night palette and cloud styling must work with the existing shapes. This resolves the concept/baseline mismatch without expanding ATM.6.

The owner also approved debug-only storm and clear-night previews. Subsequent fixture inventory found no typed storm sample, but did find a typed clear-night sample at offset 47 (day 1, 11 PM); the clear-night preview remains a controlled supplemental comparison. Both previews are scene-only visual comparisons; the selected fixture time, displayed forecast facts, and source fixture remain unchanged.

## Verified baseline and functional invariants

- ATM.5 is DONE and ATM.6 is the sole NEXT roadmap slice; cycle 037 is the latest chronological cycle. This plan is cycle 038.
- `SceneVisualStateMapper` already preserves typed `SkyCondition.STORM` as `DepictionKind.STORM`, while precipitation remains `UNKNOWN` when no typed precipitation identity is supplied. Night/day palette is presently a renderer cue based on the existing illustrative `daylight` projection from local hour.
- The fixture forecast timestamp and values remain authoritative. Storm/night styling is derived only from supported condition and existing time cues; no alerts, lightning, solar position, moon phase, or other source facts are introduced.
- Forecast facts, DEMO DATA labeling, precipitation identity/chance/amount semantics, selected time, timeline behavior, gesture ownership, and accessibility text remain unchanged.
- The existing fixture has no typed storm sample; its clear-night sample is at offset 47 (day 1, 11 PM). The owner authorized debug-only illustrative storm and clear-night scene previews for installed review; they must leave fixture/provider forecast facts untouched.
- No flashing/strobing. Static and reduced-motion output retains storm/night meaning without requiring animation. Scenic treatments stay behind and visually subordinate to forecast text and the timeline.

## Renderer contracts resolved from the approved concept and owner decision

- Preserve the current scene hierarchy and landscape geometry: apply night/storm palette and cloud treatments to the existing atmospheric Canvas shapes, behind forecast text and the timeline. Do not replace the abstract ridges with the concept's mountain/forest terrain or add content panels/overlays over facts. The existing preview banner and explicit illustrative condition text identify only the debug previews.
- Treat the existing `daylight` projection only as the renderer's generic day/night cue. Apply the approved night treatment when that cue is at its night endpoint (`daylight` is zero); do not calculate or imply real sunset, sunrise, solar position, or moon state.
- Do not draw a celestial marker in night scenes. The approved clear-night, cloudy-night, and storm-night board contains no celestial objects; this also avoids presenting the existing illustrative crescent as a phase or visibility fact. Day rendering is outside this slice.
- Select storm treatment from `DepictionKind.STORM` / typed `SkyCondition.STORM` alone, independent of precipitation amount, chance, or identity. The debug preview may select `DepictionKind.STORM` explicitly without changing the forecast condition or facts. Use only restrained blue-gray palette/cloud styling represented by the approved storm-night panel. Do not add lightning, rain/snow effects, or infer severity.
- Keep cloud extent and presence governed by the existing validated `cloudFraction`; storm styling may tint/emphasize rendered clouds but must not manufacture cloud-cover values or change their source semantics. Clear and non-storm cloudy night states continue to use their existing condition/cloud inputs.
- A scene is a deterministic snapshot of the selected forecast inputs. Reduced motion keeps the same night/storm palette and supported cloud styling while disabling effects through the existing `EffectsLevel.NONE` mapping. No wall-clock animation, flashing, or new motion is introduced; existing selected-time-driven cloud placement remains a deterministic response to selected forecast time, not an independent animation.
- Missing/invalid daylight or cloud cover follows existing mapper fallback behavior and must not be replaced by inferred astronomy or storm measurements. Storm identity remains visible through its condition-specific scene treatment even when cloud cover is unavailable.

## Steps

- [x] Inspect the approved ATM.6 concept, current scene/projection implementation, focused tests, and installed baseline before application edits. Confirmed the concept depicts mountain/forest terrain absent from the installed baseline; owner resolved this by retaining current landscape geometry and applying storm/night treatments to it.
- [ ] Add debug-build-only illustrative storm and clear-night scene previews plus a selected-hour launch extra for installed review; retain fixture forecast values and show an explicit illustrative label.
- [x] Add the narrowly scoped storm/night projection and renderer refinements to the existing atmospheric layer, honoring the resolved cue ownership, celestial-marker, cloud-input, and static/reduced-motion contracts above.
- [x] Add focused scene-projection tests for typed storm versus non-storm, night versus non-night cue mappings, static/reduced-motion state, invalid/missing scenic inputs, and preservation of forecast values and default behavior.
- [x] Run only authorized `python scripts/dev.py workflow`, `python scripts/dev.py contract`, the focused scene mapper test, `python scripts/dev.py build`, installed `python scripts/dev.py run` / `python scripts/dev.py screenshot`, and `git diff --check`.
- [x] Install/review on the Pixel 3 XL reference device in portrait at native resolution, default font scale and LTR. Captured real clear-night, non-storm cloudy-night, storm day/night, and reduced-motion/static states; inspected text/timeline clearance. Source review and captures show no new flashing or selected-time mutation; no quantitative gesture or frame-time measurement was made.
- [x] Preserve capture metadata, observations, and screenshots under `.codex/test-artifacts/038-atm6-storm-night-refinements/`; inspect final diff and close the cycle after the ATM.6 exit conditions are met.

## Acceptance/evidence

ATM.6 closes only when focused projection tests demonstrate supported storm/night mappings and static state; installed Pixel 3 XL captures show the intended restrained scenes with forecast facts and timeline readable; storm/night presentation introduces no flashing or strobing, fabricated alert/astronomical claims, or selected-time/forecast changes; and reduced-motion/static states preserve condition meaning. Exact evidence targets are `.codex/test-artifacts/038-atm6-storm-night-refinements/README.md`, `.codex/history/038-atm6-storm-night-refinements.md`, and `.codex/history/plans/038-atm6-storm-night-refinements.md`.

Authorized verification is limited to the commands and device captures above. The visual slice covers the Pixel 3 XL portrait reference viewport, normal/default font scale, LTR, and reduced-motion/static states for affected scenes. Cross-app RTL, TalkBack, large-font, landscape, other-device, frame-time, and unrelated timeline/interaction matrices are out of scope unless evidence shows a specific ATM.6 regression requiring a bounded follow-up decision.

## Risks / assumptions

- The current storm depiction is already typed but has no dedicated scene treatment; its treatment is now bounded to the atmospheric Canvas and approved artwork cues.
- Existing `daylight` is an illustrative local-hour cue, not validated sunrise/sunset or physical solar state. The night treatment uses only its zero-valued night endpoint and makes no astronomical claim.
- The owner-approved concept guides the restrained palette/cloud treatment and protected information areas. Its terrain is excluded by the owner's bounded decision. Concept approval alone did not activate the plan or authorize implementation.

## Out of scope

No weather alerts, lightning flashes, solar/moon accuracy or position claims, real wind/visibility mapping, forecast/data model changes, precipitation-intensity changes, effects lifecycle/performance policy, environment packs, production integration, timeline/gesture redesign, or broad accessibility/layout matrix.
