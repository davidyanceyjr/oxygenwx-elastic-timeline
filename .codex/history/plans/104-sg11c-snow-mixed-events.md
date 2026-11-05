# Cycle plan — 104-sg11c-snow-mixed-events

State: CLOSED — owner accepted the SG.11c outcome on 2026-11-05; the readout label/date-time collision is documented and deferred to SG.12.
Roadmap: SG.11c
Objective: Complete the Snow and mixed precipitation row in the typed weather-event path and bounded debug review route.

## Authority and board approval

`AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, and `docs/UI_DEVELOPMENT_WORKFLOW.md` govern this cycle. `python scripts/dev.py workflow` reported IDLE before planning. The roadmap names SG.11c as the sole NEXT slice, dependent on completed SG.11b (cycle 103).

The product owner approved the SG.11 behavior board `.codex/concepts/SG.11-weather-event-mapping-behavior-board.svg`, supporting contract `.codex/concepts/SG.11-weather-event-mapping-behavior-board.md`, and UI artwork board `.codex/concepts/SG.11-weather-event-mapping-ui-artwork-board.svg` on 2026-11-04, and approved the six-row split that day. These approved boards cover provider-to-canonical mapping, exact-code ledger, priority/primary selection, recording compatibility, selected-time/source-time behavior, unavailable fallback, symbol identity, and installed row states. This cycle is limited to the Snow and mixed precipitation row and shared behavior directly needed for it. The board gate is met.

## Objective and bounded scope

Extend the established SG.11a typed mapping and bounded debug route for `FLURRIES_SNOW_GRAINS`, `SNOW`, `HEAVY_SNOW`, `SNOW_SHOWERS`, `RAIN_AND_SNOW`, `WINTRY_MIX`, `GRAUPEL`, and `ICE_CRYSTALS`. Add only documented Open-Meteo code assignments that support this row. The approved exact-code ledger assigns 71 (“Slight snowfall”) and 73 (“Moderate snowfall”) to `SNOW`, 75 (“Heavy snowfall”) to `HEAVY_SNOW`, 77 (“Snow grains”) to `FLURRIES_SNOW_GRAINS`, and 85 (“Slight snow showers”) and 86 (“Heavy snow showers”) to `SNOW_SHOWERS`. The ledger has no Open-Meteo assignment for `RAIN_AND_SNOW`, `WINTRY_MIX`, `GRAUPEL`, or `ICE_CRYSTALS`; retain these as representable canonical identities and show “no approved Open-Meteo assignment” in their debug review states. Keep unsupported intermediate codes unsupported. Preserve previous rows, the complete deterministic event priority and source-primary selection contract, canonical/version-5 recording and replay, versions 1–4 compatibility, and selected-time authority.

### Verified architecture / likely production boundary

The existing path includes `data/openmeteo/OpenMeteoCatalogMapper.kt` (provider-code mapping), `data/catalog/WeatherEventFact.kt` and `CanonicalWeatherFacts.kt` (typed facts and priority), `data/catalog/RecordedCatalog.kt` and storage codec/database code (recording compatibility), `presentation/CanonicalForecastProjection.kt` and `presentation/symbol/WeatherSymbol.kt` (selected event and symbol/accessibility projection), and the bounded debug review route. Focused mapper, recording/replay, projection, symbol, and debug-route tests exist. Confirm exact affected files and current tree before implementation; this list is a planning boundary, not permission to change every listed file.

## Functional invariants

- Keep the selected `Instant` authoritative. Mapping, projection, recording/replay, and row selection must not retarget it.
- Preserve every supplied event and existing source-primary validity. Without a source primary, use the approved fixed priority; never make source iteration order the selection rule.
- New mapped events retain typed identity, exact numeric source code, documented code description, and provider provenance as distinct evidence. Accessible wording and symbol derive from the typed identity.
- `CONDITION_UNAVAILABLE` is presentation-only and cannot be recorded as a supplied event.
- Version 5 retains typed weather events through recording/replay. Versions 1–4 remain readable without typed-field backfill.
- Preserve completed Sky and Rain/ice rows, existing weather artwork, broader legacy conditions, deterministic fallback, and shared debug selection behavior.
- Keep DEMO DATA/provenance honest and maintain readable selected facts, symbol names, and responsive timeline behavior in changed installed states.

## Dependency order

1. Inspect cycle 103's final diff/evidence, exact-code ledger and approved boards; inspect the current working tree and identify the snow/mixed row routes.
2. Implement only the six documented provider-code assignments and preserve unsupported-value behavior. Keep the four canonical-only identities reviewable without fabricating source codes.
3. Complete the bounded debug review route and wire only the necessary selected-event, symbol/accessibility, and recording/replay path.
4. Verify all eight row identities through canonical input, version-5 recording/replay, selected-time projection, and matching symbol/accessibility wording; preserve previous rows and legacy replay behavior.
5. Install on the Pixel 3 XL portrait reference viewport at default system font scale (1.0) and capture all eight row states. Check directly affected selection and timeline interaction.
6. Record scoped pass/fail/not-run evidence, exact commands, limitations, and final diff review.

## Acceptance and evidence

Exit: all eight Snow and mixed precipitation identities survive canonical input, version-5 recording/replay, projection, and matching symbol/accessibility wording. The six provider-assigned IDs use only codes 71, 73, 75, 77, 85, and 86 with their exact documented descriptions; the other four IDs remain canonical-only and show no provider assignment. Unsupported intermediate values yield no typed supplied event. Every row state is inspected in an installed capture on the Pixel 3 XL portrait reference viewport at default system font scale (1.0), with selected fact, exact provider code/description or “no approved Open-Meteo assignment,” symbol ID, accessible name, device/command, and limitation recorded. Directly affected selected-time/timeline behavior is checked. Existing rows and versions 1–4 replay compatibility remain valid. RTL, TalkBack, reduced-motion, and broader system matrices are out of scope and are not claimed.

### Owner acceptance direction — 2026-11-05

The product owner directed cycle 104 to close and identified SG.12 as the slice that will refactor the center-readout weather data. The installed captures show the center condition label colliding with/crowding selected date/time in five snow/mixed states; the owner accepts that limitation for SG.11c, which owns the typed mapping and bounded symbol review route. Record the observed states and defer the center-readout collision to SG.12. This is an owner-approved acceptance disposition; it does not claim the collision was fixed or that SG.12 work has been implemented.

Target evidence: `.codex/test-artifacts/104-sg11c-snow-mixed-events/README.md` and installed captures in that directory. Target closeout: `.codex/history/104-sg11c-snow-mixed-events.md`; archived plan: `.codex/history/plans/104-sg11c-snow-mixed-events.md`.

At execution, applicable commands are `python scripts/dev.py workflow`, `contract`, `build`, `run --serial <device>`, and `screenshot --serial <device> --output .codex/test-artifacts/104-sg11c-snow-mixed-events/<state>.png`. Repository policy requires explicit user authorization before adding or running tests; focused test commands remain conditional on that authorization. The SG.11 boards authorize no tests. Do not claim installed acceptance from a build or preview. No RTL, TalkBack, reduced-motion matrix, or broad system acceptance is in scope; those belong to TEST slices.

## Risks and assumptions

- The approved board/contract and existing mapper should define the allowed provider assignments. Confirm the exact ledger before editing; do not infer assignments from general plausibility or neighboring codes.
- Some identities may be representable canonically without a documented Open-Meteo assignment. In that case preserve the typed identity and make the lack of provider assignment explicit in the review state; do not invent a source fact.
- UI review requires an available Pixel 3 XL reference device. If unavailable, record the exact blocker and not-run states instead of treating a build as visual evidence.

## Out of scope

No assignments for other artwork rows; no new weather-symbol artwork; no changes to Sky, Rain/ice, unavailable-state semantics, the center readout, scene composition, provider endpoint/variables, unit policy, location or timeline navigation, official alerts, live-network redesign, deprecated oxygenWX files, or cross-cutting accessibility/system matrices.

## Difficulty and model fit

Difficulty: 5/10. The row is a bounded extension of an established typed mapping and review path, but it crosses provider mapping, versioned recording, projection/accessibility, and installed visual acceptance. The behavior boards reduce ambiguity; device availability and confirming exact documented assignments are the main uncertainty/verification costs.

Relative model fit: use the least costly available Codex model with credible code-editing and Android workflow performance (suggested: gpt-6-luna); fall back to gpt-6.1-sol if cross-layer compatibility analysis or implementation corrections exceed the bounded scope. Escalate when an observed issue requires a product/board decision or broader schema redesign. This compares relative fit only, not API model availability or pricing.
