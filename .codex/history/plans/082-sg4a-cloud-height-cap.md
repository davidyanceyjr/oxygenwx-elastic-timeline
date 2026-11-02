# Cycle plan — 082-sg4a-cloud-height-cap

State: ACTIVE
Roadmap: SG.4a
Objective: Constrain clouds to the top 33% of the atmospheric scene viewport

## Approved slice boards

- **Behavior board:** `.codex/concepts/SG.9-cloud-height-contract.md`; product owner approved 2026-11-01.
- **UI artwork board:** `.codex/concepts/SG.9-cloud-height-artwork.png`, editable source `.svg`; product owner approved 2026-11-01.
- The owner approved the contract that the atmospheric scene canvas is the measurement area; all visible cloud output, including silhouette edges, blur, shadow, motion, and transitions, remains within the top 33%; supported cloud facts stay unchanged; precipitation may extend below the cloud region. These boards authorize only SG.4a and do not approve the separate SG.9 refactor.

## Production boundary

May change:

- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt` — bound cloud rendering in both the first-city catalog route and the compatibility atmospheric route.
- `docs/SPECIFICATION.md` and `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` — record the adopted cloud placement invariant.
- `docs/ROADMAP.md` — order SG.4a before the separately planned SG.9 refactor and record the approved board package.
- `.codex/current.md`, this plan, and cycle evidence/history paths.

Do not change forecast semantics, presentation values, timeline behavior, data schemas, asset artwork, or the separate typed-scene refactor.

## Functional invariants

- Every visible cloud pixel stays within `0 ≤ y ≤ 0.33H`, where `H` is the atmospheric scene canvas height.
- Include anti-aliased silhouettes, shadow/blur, and every deterministic movement/transition pose in the bound.
- Preserve cloud presence, opacity mapping, supplied cloud-cover facts, precipitation mapping, forecast text, city depth, and existing layer semantics.
- Rain and snow may render below the cloud region. Selected time remains authoritative; the canvas does not own gestures.
- Apply the placement bound across both existing renderer routes, not only the current catalog route.

## Steps

1. [x] Inspect both cloud paths and establish a source baseline for their cloud geometry and draw order.
2. [x] Reposition the first-city cloud bank into the approved top band and enforce a hard clip at 33% of the atmospheric canvas. Preserve selected-time positioning within the band. Retain or strengthen the same bound on the compatibility route.
3. [x] Capture affected installed states on the Pixel 3 XL: cloudy/day, rain/day, snow/night, and storm/day or night where fixture support allows. Inspect the boundary, text/readout layering, and directly affected timeline interactions.
4. [x] Record screenshots and observations under `.codex/test-artifacts/082-sg4a-cloud-height-cap/`; inspect `git diff --check` and the final diff; close the cycle with exact verification results and limitations.

## Acceptance and authorized verification

- `python scripts/dev.py workflow` passes before close.
- `python scripts/dev.py build` passes for the debug app, followed by `python scripts/dev.py run --serial <connected Pixel serial>` and installed screenshots using `python scripts/dev.py screenshot`.
- Installed cloudy, rain, snow, and storm states show all visible cloud content at or above the `0.33H` boundary. The lower cloud contour remains visible and no antialiased spill, shadow, or animated pose crosses the boundary.
- Supplied cloud presence and facts remain unchanged. Rain/snow still render below clouds with readable forecast values above scene art.
- Scrubbing still changes the selected-time scene predictably; directly affected taps/drags remain owned by the timeline. Review reduced-motion still pose if the connected fixture exposes that state.
- `git diff --check` passes. No automated test suite, RTL, TalkBack, or broad system/environment matrix is authorized or claimed in this design slice.

## Evidence

- Installed captures and observation log: `.codex/test-artifacts/082-sg4a-cloud-height-cap/`.
- Closeout: `.codex/history/082-sg4a-cloud-height-cap.md`.
- Archived plan: `.codex/history/plans/082-sg4a-cloud-height-cap.md`.

## Risks and assumptions

- “Screen” means the atmospheric scene canvas, as approved in the behavior board. Forecast readouts and timeline overlays are not part of `H`.
- A clip enforces the hard bound while repositioning preserves the intended cloud silhouette rather than merely cutting off its lower portion.
- Existing debug fixtures may not provide every requested cloud condition; record any unavailable state and do not substitute unsupported weather data.

## Out of scope

- Typed scene-reading/composition refactor in SG.9.
- New clouds, themes, weather families, cloud-cover interpretation, precipitation changes, forecast text changes, or provider/data changes.
- App-wide RTL, TalkBack, accessibility, localization, or device/environment acceptance.
