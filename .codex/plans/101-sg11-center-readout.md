# Cycle plan — 101-sg11-center-readout

State: PLANNED
Roadmap: SG.12
Objective: Refactor the central selected-forecast readout using the typed weather-event projection established by SG.11a–f

## Production boundary

Authority: `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md`. Dependencies SG.5, SG.10, and WA.1–WA.6 are DONE; SG.11a–f weather-event mapping must close before this SG.12 cycle can activate. Review workflow reports scaffold OK and this cycle PLANNED.

Product owner approved behavior board `.codex/concepts/SG.11-center-readout-behavior-board.svg` on 2026-11-04 with the adjacent location-trigger amendment; its PNG preview and `.md` contract accompany it. The owner approved UI artwork board `.codex/concepts/SG.11-center-readout-ui-artwork-board.svg` on 2026-11-04 after revision to place one empty indicator-sized slot between precipitation chance and wind speed. The boards govern condition/source states, symbol pairing, picker placement, omitted/removed elements, lower indicators, and regular/compact hierarchy. Roadmap exit and controlling specifications govern acceptance.

The approved behavior board governs selected-hour, eligible carried-condition, and unavailable source claims; the approved artwork board governs the regular and compact layout, including the empty lower-indicator slot. The installed state checks below compare each result with those boards and the SG.12 roadmap exit. The owner's omission of the visible center condition word is reconciled in `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` §5.1; condition text remains in accessibility semantics and the forecast browser.

Owner sequencing decision on 2026-11-04: full 48-event typed mapping, provider normalization, replay compatibility, and all-ID symbol evidence moved into SG.11a–f. This plan and its approved center-readout boards remain with SG.12; the board filenames retain their earlier SG.11 identifier as historical artifact paths. Their center selected-time, carried-source, unavailable-state, artwork, and interaction directions remain approved. SG.12 consumes SG.11a–f's completed typed projection and does not reimplement its mapping contract.

Change the central readout and directly affected semantics in `app/src/main/java/com/oxygen/weather/timeline/ui/ElasticForecastTimeline.kt`. Adjust `ui/AccessibleForecastBrowser.kt` only as needed to preserve condition text. Adjust existing picker integration only to move its trigger. Use existing WA artwork and the SG.11a–f event projection.

## Functional invariants

- The selected `Instant` governs facts and scene. Layout, symbol, picker opening/dismissal, and timeline scale transitions cannot retarget it. Timeline selected day/time and gesture ownership remain.
- Supported condition maps from typed selected-time condition/phenomenon to stable WA identity and matching accessible condition name, never from display strings. Eligible interior missing-hour carry shows prior supplied symbol only with original source time and selected-hour-unavailable disclosure. Unsupported/empty condition, edge gap, and missing day show unavailable identity without a new condition claim.
- Temperature, daily high/low, Sun/Moon rise/set, precipitation chance, wind speed, DEMO DATA, and provenance stay tied to supplied facts, with independent unavailable behavior. Forecast browser and accessible semantics retain condition text.
- Central swipe/tap/double-tap, picker behavior, scene legibility, timeline responsiveness, and readable labels remain intact.

## Typed-event dependency

SG.11a–f own canonical, simulated, recorded, provider-normalization, and presentation mapping for all 47 supplyable WA events plus the presentation-only `CONDITION_UNAVAILABLE` fallback, including exact WMO assignments, multiple-event precedence, legacy broad-condition day/night choice, and all-symbol evidence. SG.12 consumes that stable selected-time event identity and matching accessible name. It must not infer a new identity from display strings or measurements. Its installed review checks representative clear/cloudy, precipitation, carried, and unavailable states in the approved readout, not a second all-symbol mapping ledger. The existing scene's dawn/dusk light-phase timing is outside this slice.

## Steps

- [ ] Inspect installed baseline, readout/picker gestures, typed projection, WA symbol API, and available debug states; record Pixel 3 XL and compact viewport settings.
- [ ] Consume SG.11a–f typed selected-event projection and existing WA symbol API; preserve source-time disclosure and unavailable states in the center readout.
- [ ] Recompose center with noninteractive location label and adjacent picker trigger, tight temperature/symbol pair, supported Sun/Moon rise/set and high/low. Omit center condition word and duplicate weekday/time. Remove lower magnifier/gear; show prominent precipitation/wind numbers with compact cues and one empty indicator-sized slot between.
- [ ] Preserve accessible condition meaning and browser text without duplicate spoken claims; inspect picker touch target and central gesture arbitration.
- [ ] Build/install, capture scoped states, inspect selected-time updates and directly affected interactions, record exact evidence, inspect `git diff --check` and full diff, then close.

## Acceptance/evidence

Installed Pixel 3 XL portrait and compact viewport show approved hierarchy in representative clear/cloudy, precipitation, eligible carried-condition, and unavailable states when existing catalog/debug routes supply them. Record supplied facts and selected instant per capture. Inspect selected-time changes, picker open/dismiss, Sun/Moon times, high/low, provenance, lower numeric readability and middle gap, scene overlap, timeline selection/scrub, central swipe/tap/double-tap, responsiveness, and gesture ownership. Match numeric/categorical presentation, WA identity, and accessible condition name to the same supplied projection. Record pass/fail/not-run for each scoped state; no visual claim from build or preview alone.

Use the Pixel 3 XL portrait reference at default font scale and one documented compact portrait viewport at default font scale. Check directly affected text fit and symbol/fact association in both; if the installed UI shows a scoped clipping issue, inspect a 1.3 font-scale reproduction for that issue and record its result. This design slice does not establish broad large-font, RTL, or TalkBack acceptance. RTL and reduced-motion settings do not change the approved readout composition or forecast meaning; record their applicability as unchanged and leave cross-cutting installed matrices to TEST slices. No new animation is planned, so reduced-motion meaning is the same selected-time, symbol, and textual facts.

After activation, run `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py run --serial <device>`, and `python scripts/dev.py screenshot --serial <device> --output .codex/test-artifacts/101-sg11-center-readout/<state>.png` as applicable. Manually inspect installed states/interactions. Do not add or run tests unless the user separately authorizes them. No broad suite, lint, RTL, TalkBack, or system matrix in this design slice. Preserve exact commands, device/viewport, captures, observations, and limits in `.codex/test-artifacts/101-sg11-center-readout/README.md`; closeout `.codex/history/101-sg11-center-readout.md`; archived plan `.codex/history/plans/101-sg11-center-readout.md`.

## Risks / assumptions

Existing catalog/debug routes may not expose every approved center state; use a bounded review route if needed and record any truly unreachable installed state. Compact layout can crowd event times and picker target; inspect installed captures. SG.11a–f must close with the typed-event contract before this center cycle activates.

## Out of scope

No new WA artwork, provider endpoint or requested variable, unit policy, settings/menu behavior, location-selection redesign, scene composition, timeline redesign, live network, official alerts, or deprecated oxygenWX edits. Dedicated TEST slices own broad accessibility and system acceptance. This PLANNED cycle does not implement SG.12 until activation after SG.11a–f close.
