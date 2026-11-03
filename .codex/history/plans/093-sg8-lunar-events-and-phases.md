# Cycle plan — 093-sg8-lunar-events-and-phases

State: DONE
Plan readiness: **Final; owner approved both required boards as written on 2026-11-02.**
Cycle ID: 093-sg8-lunar-events-and-phases
Roadmap: SG.8
Depends on: SG.1, SG.2, SG.3, and SG.9 (all DONE)

## Authority and board package

Follow `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, and the subordinate planning basis `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`.

- **Behavior board:** `.codex/concepts/SG.8-lunar-events-and-phases-behavior-board.svg` with detailed contract `.codex/concepts/SG.8-lunar-events-and-phases-behavior-board.md` and PNG preview `.codex/concepts/SG.8-lunar-events-and-phases-behavior-board.png` — product owner approved as written on 2026-11-02.
- **UI artwork board:** `.codex/concepts/SG.8-lunar-events-and-phases-ui-artwork-board.svg` with PNG preview `.codex/concepts/SG.8-lunar-events-and-phases-ui-artwork-board.png` — product owner approved as written on 2026-11-02.
- The behavior board defines provider event mapping, local-date/selected-time behavior, phase-stage bins, cross-date event pairing, missing-value independence, replay, invariants, and scope.
- The artwork board shows selected-time states before rise, at rise, between events with cloud overlap, and with phase unavailable while rise remains supplied. It proposes a neutral phase glyph with explicit stage text and keeps timestamp/timeline facts visible.
- **Approval constraints:** The owner approved the proposed equal-width phase-stage bins centered on the eight listed landmarks; chronological cross-date rise/set pairing with unresolved spans left without a Moon-position claim; neutral phase-cycle marker plus explicit stage text without hemisphere-specific visible orientation; independent missing phase/event handling; and Sun-like scale emphasis that enlarges the Moon near supported horizon crossings and reduces it high on the illustrative path. Scale is art direction only and does not claim physical angular diameter, distance, or measured altitude. Board approval does not replace implementation or installed acceptance evidence.

## Objective and production boundary

Extend the existing Open-Meteo daily catalog and shared semantic scene path with provider-backed moonrise, moonset, and moon phase. Preserve those typed optional values through recorded catalog/replay. Evaluate lunar events and daily phase from the selected instant's location-local date, render exact provider horizon crossings with an illustrative between-event path, and expose supported phase/event facts as text. The Sun remains governed by SG.3's provider-event boundaries; weather tracks remain independent.

Expected production boundary, to be confirmed against the approved boards and preflight before implementation:

- `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoTransport.kt`
- `app/src/main/java/com/oxygen/weather/timeline/data/openmeteo/OpenMeteoCatalogMapper.kt`
- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/ProviderDailySummary.kt`
- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/CatalogWindow.kt`
- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/RecordedCatalog.kt`
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt`
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityComposition.kt`
- `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/FirstCityTheme.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/AtmosphericScene.kt`
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` (only if needed to present supported lunar facts as text/accessibility semantics)
- Directly affected focused test sources and fixtures only, if test execution is authorized for the active cycle.

No other provider, theme-selection flow, timeline interaction, or unrelated UI is in scope. This candidate list is a planning boundary; any additional file requires a direct dependency demonstrated during execution and a recorded scope review.

## Functional invariants

1. The selected `Instant` is the only scene clock. Resolve the active catalog's local date with its IANA zone; timeline scale, animation, theme, and asset loading never retarget the instant.
2. Moonrise/moonset remain optional absolute event Instants attached to the provider local date. Every drawn horizon crossing occurs at the supplied event instant.
3. The between-event path is theme-owned and illustrative. It makes no azimuth/elevation, orbital geometry, or visible-orientation claim.
4. Daily phase remains a separate local-date value. It does not acquire an invented intraday transition time or depend on rise/set availability.
5. Missing rise, set, and phase values remain independently unavailable. No local-time, solar-time, prior-day, or default phase/event is invented.
6. Cross-date event pairing follows the owner-approved board rule. If the event history cannot establish an above-horizon interval, do not claim the Moon is above the horizon.
7. Within a supported rise-to-set span, use the same visual scale emphasis as the current Sun cue: largest near the horizon crossings, smallest near the high middle of the illustrative path. Scale affects drawing only and does not claim physical angular diameter, distance, or measured altitude.
8. Recorded catalogs without lunar fields remain compatible and replay without synthesized lunar values. LIVE, SIMULATED, and REPLAY provenance remains truthful.
9. Preserve solar event/light behavior, supplied weather meaning, cloud placement, readable forecast facts, predictable gesture ownership, and responsive scrubbing. Moon artwork never blocks timeline input.
10. Provide text/accessibility semantics for supported phase/event facts; visual art alone does not carry critical forecast information.

## Resolved implementation decisions

- **Event pairing:** Build a deterministic ordered list of the active catalog's supplied moonrise/moonset events, retaining each event's kind, local-date owner, and absolute `Instant`. For the selected instant, first honor an exact reported crossing as a horizon position. Between crossings, assert an above-horizon position only when the two neighboring events in chronological order are a rise followed by a set and the selected instant falls strictly between them. Do not pair set-to-rise or skip over an absent crossing to complete a span. If no such neighboring pair establishes the interval, leave Moon position unknown while keeping independently supported phase/event facts available. The path and its scale remain illustrative within an established rise-to-set span.
- **Provider time mapping:** Keep the existing request's Unix-time response convention for sunrise/sunset. During preflight, verify that the added `moonrise`/`moonset` values use the same whole Unix-second format and that `moon_phase` is numeric and within its documented range. If confirmed, parse event values through the existing `toEventInstant` path into absolute `Instant`s; associate each with the matching provider daily row date. Continue deriving true local-day support from that date and the requested location's IANA `ZoneId`. If the response contract differs, stop before mapping and revise the plan rather than guessing or applying a fixed UTC offset.
- **Cross-midnight event ownership (owner resolution, 2026-11-03):** The live sample showed valid `moonrise`/`moonset` instants just after midnight beyond their provider row's local-day support. The owner selected solution 1: preserve provider-row ownership and absolute event time while allowing event instants outside row-local daily coverage. When an event's local date differs from its provider row date, expose that actual date with its time. A mapper/row regression check covers this boundary.
- **Compact readout (owner resolution, 2026-11-03):** The owner selected solution 1 after the installed compact 1.3 font-scale capture showed a cramped layout. Scale readout typography, phase marker, weather pictograms, and spacing against available viewport width; split lunar phase and event times into intentional lines on narrow viewports; group the center actions to preserve a single-line wind fact. Re-review the compact and default installed states.
- **Recording compatibility:** Add lunar values to canonical summaries as nullable fields. Introduce recording schema version 4 when a recording contains lunar values or declares lunar daily support; encode the new lunar fields and support entries there. Keep the version 1–3 layouts unchanged, and retain their decoder paths with lunar values/support unavailable by default. Version 4 decoding must preserve nulls as nulls and round-trip populated values through REPLAY without synthesis. Keep LIVE/SIMULATED/REPLAY provenance and existing validation rules intact.

## Ordered work

1. **Board gate — complete.** Owner approval of both boards and constraints is recorded above and in the roadmap; no further planning decision is pending.
2. **Preflight and baseline.** Run `python scripts/dev.py workflow`; inspect only the listed data, projection, scene, and screen paths. Capture affected installed baseline states and record connected reference/compact devices and font scale applicability in `.codex/test-artifacts/093-sg8-lunar-events-and-phases/`.
3. **Map canonical lunar data.** Verify the Open-Meteo lunar variable units/format and local-date row semantics against the active request configuration. Reuse Unix-second-to-`Instant` mapping for rise/set if confirmed; attach events and phase to the matching provider local date and preserve true IANA-zone day support. Add optional provider daily lunar values/support metadata, keeping nulls null.
4. **Preserve recordings and replay.** Add nullable lunar summary fields and lunar daily support to recording schema version 4. Leave versions 1–3 wire layouts unchanged and decode them with lunar values/support unavailable; verify v4 round-trip and provenance during the authorized focused verification.
5. **Project independent lunar tracks.** Extend the typed shared scene reading with the selected local date's phase and the catalog's ordered rise/set event history. Honor exact supplied crossings; only resolve an interior above-horizon span from neighboring chronological rise-then-set events. Set-to-rise pairs, missing neighbors, and otherwise unresolved intervals produce no Moon-position claim. Keep Sun, phase, weather, and event tracks independent.
6. **Render the approved first-city treatment.** Replace unsupported clock-derived Moon placement with provider event-driven horizon crossings and the approved illustrative path/phase cue. Match the current Sun cue's horizon scale emphasis across the supported rise-to-set span; size is stylistic and position-independent outside that span. Preserve cloud ordering, scene timing, reduced-motion meaning, and gesture-free rendering.
7. **Present supported facts.** Add concise phase/event text and accessible semantics in the approved location without obscuring selected timestamp or existing forecast values.
8. **Focused verification and installed review.** After explicit test authorization, run only directly affected mapper/catalog/replay/scene checks. Install and capture the approved Pixel 3 XL portrait and compact states, default and 1.3 font scale where applicable, and the reduced-motion state when motion is present in the affected composition. Inspect exact event boundaries, chronological rise/set pairing including cross-date and incomplete-history cases, local-date/DST, legacy v1–v3 decode and v4 round-trip, missing-value combinations, waxing/waning stages, horizon-relative cue scale, cloud overlap, timestamp/readout legibility, scrub responsiveness, and gesture ownership. RTL, TalkBack, and broad system matrices remain outside this design cycle per project policy.
9. **Closeout.** Record exact commands/results and PASS/FAIL/NOT RUN, preserve screenshots/hierarchy/logs under the evidence path, inspect final diff and `git diff --check`, and close only if the approved exit is met.

## Acceptance criteria

- Provider-backed moonrise/moonset Instants and phase fraction reach canonical summaries, catalog validation/window activation, recordings, and replay; schema v4 round-trips lunar values/support, while recordings v1–v3 decode with lunar fields/support unavailable and retain existing provenance.
- The selected-time scene uses the selected location's local date and exact selected `Instant`; DST/date changes select the correct daily lunar values.
- Supported rise and set boundaries are rendered at their exact provider Instants. An interior interval is asserted only between neighboring chronological rise-then-set events; set-to-rise pairs, missing neighbors, and unresolved history create no Moon-position claim.
- Across a supported rise-to-set span, the Moon cue visibly grows toward the provider horizon crossings and shrinks toward the high middle, matching the current Sun cue's visual scale profile. This does not communicate actual angular size, distance, or measured altitude.
- The provider phase value maps to the owner-approved eight-stage mapping and changes by local-date selection, not by an invented time of day. Waxing and waning stage names remain distinct; no exact phase-transition time or unsupported orientation is implied.
- Missing phase and missing event cases are independently honest; no field is fabricated and valid independent fields remain usable.
- Lunar scene changes remain deterministic from catalog/location/selected-time inputs and do not alter Sun events, weather facts, selected time, timeline responsiveness, gesture ownership, or readable forecast facts.
- Installed evidence shows the approved states on Pixel 3 XL portrait and compact viewport, with default/1.3 font-scale and reduced-motion applicability recorded; RTL, TalkBack, and broad system matrices are explicitly not run in this design slice. Exact commands, results, and limitations are retained.

## Verification and evidence

- **Evidence directory:** `.codex/test-artifacts/093-sg8-lunar-events-and-phases/` (installed screenshots/hierarchies, focused XML test reports, provider payloads, and README).
- **Closeout:** `.codex/history/093-sg8-lunar-events-and-phases.md`.
- **Archived plan:** `.codex/history/plans/093-sg8-lunar-events-and-phases.md`.
- Plan-authoring checks: `python scripts/dev.py workflow` and `git diff --check`.
- Execution check: `./gradlew :app:testDebugUnitTest --tests 'com.oxygen.weather.timeline.data.openmeteo.OpenMeteoCatalogMapperTest' --tests 'com.oxygen.weather.timeline.presentation.scene.FirstCityThemeTest' --tests 'com.oxygen.weather.timeline.data.catalog.RecordedCatalogTest'` — PASS, 23 tests total, 1 skipped, 0 failures/errors. Tests were run after owner authorization for this cycle; mapper/catalog/replay/scene reports are retained in the evidence directory.
- Installed acceptance is required; a build or preview alone is not visual evidence. Do not claim visual/device acceptance without actual installed screenshots and scoped interactions.

## Risks and assumptions

- Current source has a local-clock-derived nighttime Moon cue; SG.8 is expected to replace that cue with provider-backed lunar event state without altering SG.3 solar event semantics.
- The official API surface exposes daily `moonrise`, `moonset`, and `moon_phase`; preflight must verify the configured response format and local-date row mapping. Reuse the current Unix-second-to-`Instant` mapping only if the lunar fields confirm that contract; otherwise stop and revise before canonical mapping.
- Owner-approved phase bins, cross-date rise/set pairing, neutral phase art, and Sun-like horizon scaling govern implementation; changes to them require a recorded replan.
- Provider nulls and sparse event days may leave visibility intervals unresolved; honor missingness rather than filling them with a solar or clock-based rule.
- Recorded-catalog schema changes must preserve legacy recordings and existing activation invariants.

## Out of scope

Lunar azimuth/elevation, visible orientation, exact phase-transition instants, independent ephemeris calculations, daylight rules for Moon visibility, additional weather providers, a theme picker, theme-specific second composition, timeline gesture changes, unrelated summary redesign, broad RTL/TalkBack/system acceptance, performance benchmarking beyond observed scrub responsiveness, and unrelated catalog/source recovery.
