# Oxygen Time Lab — prototype specification

Experience-level principles and acceptance criteria are maintained in the adopted companion [`OXYGEN_EXPERIENCE_SPECIFICATION.md`](OXYGEN_EXPERIENCE_SPECIFICATION.md). This document remains controlling for the current prototype's behavior and hard semantic/architecture constraints. The owner-approved product direction makes this repository the successor OxygenWX app; staged successor work is authorized only through the bounded WX roadmap and its active cycle plan. This authority update authorizes future work to be planned and implemented in this repository, but does not itself authorize implementation outside an active bounded plan.

The adopted [`OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md`](OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md) remains historical architecture context for completed ATM work and shared invariants. Its unstarted ATM/ENV roadmap sequence was superseded by the owner-directed Scene Generation draft below. The prior proposal cannot override this document or the experience contract, or authorize application changes by itself. Its adoption boundary and current-tree review are recorded in `.codex/history/023-atmosphere-spec-intake.md`.

The owner-directed draft [`OXYGEN_SCENE_GENERATION_SPECIFICATION.md`](OXYGEN_SCENE_GENERATION_SPECIFICATION.md) is the planning basis for the proposed SG workstream. It reframes the unstarted ATM/ENV roadmap around deterministic forecast-event tracks and theme-specific scene composition. It remains subordinate to this document and the adopted experience contract, and does not authorize implementation by itself. The dated source-to-destination handoff and current sequencing are recorded in `docs/ROADMAP.md`.

Status: successor OxygenWX app in staged development. The current installed application remains an isolated synthetic-data prototype; future catalog, live-data, location, cache, and source-switching capabilities require their own approved roadmap cycles. This repository is the successor product boundary. The deprecated oxygenWX application and its production files are not modified by this roadmap.
Reference: oxygenWX commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`.

## Goal

A full-screen atmospheric scene controlled by a minimalist bottom time tape. Three-letter day abbreviations and fine tick marks sit in a gradient overlay. A fixed central playhead is the time-reading position.

## Required behaviors

1. **Tap** a day on the tape: select 12:00 local time in the selected location's IANA timezone on that date and show its representative summary. A daily drag release selects the same representative time on its target date. On a partial first or last date, clamp an out-of-window noon target to the nearest catalog boundary and communicate the adjustment.
2. **Immediate horizontal drag:** browse daily time, with the tape moving beneath the playhead.
3. **Stationary hold (~480ms) then drag:** switch the whole tape to a uniform hourly scale (`16dp/hour`) and scrub hour by hour. Do not locally expand one day into a focus window.
4. **Hourly mode lifetime:** releasing after hourly activation ends the current drag but leaves the timeline in hourly mode. A tap on the main forecast area collapses to daily mode while preserving the exact selected instant. A tap on the hourly timeline first resolves the touched instant on the hourly axis, selects it, then collapses while preserving it. Scale-only transitions never select noon. Hourly ticks/labels and accessibility semantics distinguish the scales without a visible mode-control row.
5. **No coordinate jump on scale change:** the selected timestamp always projects to the center playhead, regardless of expansion factor.
6. **Time bounds and gaps:** clamp only targets outside the catalog's first/last selectable instant to the nearest boundary and communicate the adjustment; never invent forecast intervals beyond it. An in-window hour or day with missing facts remains selectable and navigable. A missing hourly condition remains unavailable as a source fact. For an interior absent expected hourly record bracketed by supplied condition records on the same local date, the display may carry the preceding supplied condition and scene with its original source time disclosed; this is a labeled display of older evidence, never a newly supplied fact or a claim of continuous weather. A leading or trailing gap, an unsupported or empty supplied condition, and a missing day make no hourly condition claim; retain independent daily facts where available. Other missing facts remain unavailable, never numeric zero. A refresh must not silently retarget the selected instant.
7. **Accessibility route:** the time tape exposes a screen-reader action to switch daily/hourly scales; do not add a visible mode-control row.
8. **Selected-time cue:** enlarge the selected day/hour label while neighboring labels remain at regular size; keep the selected label centered under the playhead.
9. **Illustration semantics:** show synthetic DEMO DATA; procedural rain streaks are not precipitation-rate measurements.
10. **Main-display navigation:** in the current fixed-fixture prototype, horizontal swipes shift the selected time by 24 hours while preserving hour-of-day. In the successor, a horizontal swipe targets the adjacent local calendar date at the same wall time; repeated local times retain the prior offset when possible (otherwise choose the earlier occurrence), and nonexistent local times resolve to the first valid time. An in-window gap remains selected; its facts follow rule 6, including disclosed preceding-condition display only for an eligible interior missing hour. An out-of-window destination clamps to the nearest boundary with an adjustment notice. Double-tap returns to the current fixture hour in the prototype. Successor live reset, even with a stale catalog, targets the current instant at activation; simulated and recorded replay reset to catalog `asOf`. Out-of-window reset targets clamp with an adjustment notice.

## Architecture

The UI layer consumes typed `ForecastView`, which is derived from sample canonical `ForecastHour` / `ForecastDay` models. `ElasticTimeAxis` is a platform-independent pure monotonic projection, testable without Compose. Atmospheric rendering is decorative, with critical forecast values separately presented as text.

## Hard visual-quality rule

Every visual enhancement must preserve the timeline's responsiveness, gesture predictability, and readability. A weather-scene change is incomplete if it makes scrubbing feel delayed, changes gesture ownership or arbitration unexpectedly, or obscures forecast values or timeline labels.

## Successor direction and durable boundaries

- **Date window:** a location-scoped catalog targets 44 local calendar dates: D-30 through D-1 as historical dates, followed by D through D+13 as forecast dates. D is the local date at the catalog's `asOf: Instant` in the location's IANA timezone. The shared 44-date horizon does not promise 1,056 hourly values; DST, provider gaps, and partial coverage remain explicit. The owner-directed WX.10R SIMULATED development product is a bounded exception: it provides one fact at every real local-hour instant on each of its 44 dates, with DST dates containing 23 or 25 instants as applicable. Its precipitation probability and sustained wind speed are present on every synthetic hourly fact; absent anchor values are explicit numeric zero. Other nullable synthetic fields, providers, and unrelated replay catalogs retain their own support semantics. This synthetic coverage does not imply complete provider coverage.
- **Canonical data and parity:** live, deterministic simulated, and recorded replay datasets use one canonical schema; switching replaces a whole validated dataset atomically. Missing values remain unavailable, never defaulted to zero.
- **Forecast value presentation:** the app faithfully presents supported supplied values and categorical conditions instead of re-evaluating meteorological plausibility. Design-slice acceptance checks the supplied value against its intended presentation.
- **Official alerts:** real-data integration does not require official alerts. Before an alert subsystem exists, the UI must not imply that an empty alert display means no alerts exist. If offered later, alerts require an authoritative source and remain separate from catalog conditions or inferred warning flags.
- **Retained invariants:** absolute selected-time authority; no time jump from zoom; predictable ownership between the timeline and other gestures; readable forecast facts and accessible alternatives; RTL/reduced-motion meaning; missing-data semantics; decorative rendering does not invent meteorological facts.
- **Predecessor boundary:** the deprecated oxygenWX application's files and four-page navigation are not changed by this successor roadmap. This app keeps its immersive timeline as the primary experience; no timeline is inserted into the predecessor's outer pager.
- **Explicit exclusions:** no official-alert implementation, background GPS tracking, production changes to the deprecated oxygenWX repository, or unbounded integration/release redesign. Each future capability remains gated by its own WX plan and verification.

The current prototype still has no live provider, location selection, real-data cache, or official alerts. Those are future roadmap outcomes, not current capabilities.

## Production-release presentation requirements

The following are beta/demo scaffolding and must not appear in a final production release:

- The `OXYGEN / TIME LAB` experimental title/branding.
- The `DEMO DATA` label, once the screen is backed by production forecast data.
- The instructional bottom row (`SWIPE DAYS · HOLD TAPE FOR HOURS`) and the prototype `NOW` button (removed from the prototype in cycle 006).
- The visible `HOURLY PRECISION` / `DAILY NAVIGATION` and `HOURS` / `DAYS` mode-control row (removed from the prototype in cycle 006).

The instruction and mode-control rows above are already absent from the prototype. Keep the demo label while this prototype uses synthetic fixtures. The center double-tap remains the prototype's way to realign to the current fixture hour; define how timeline reset behavior fits the production navigation and interaction model before integration.

## UI acceptance boundary

Each implementation slice records pass, fail, or not-run results for its scoped acceptance and preserves actual evidence. Visual design slices use the Pixel 3 XL reference viewport for affected states where installed evidence is part of the exit, and verify directly affected interactions and readability. Do not add RTL, TalkBack, or broad system/environment matrices to design slices. Dedicated roadmap slices explicitly designated `TEST` own cross-cutting system acceptance, including RTL, TalkBack, and broader accessibility or device matrices. A design-slice value check compares the supplied forecast value with its intended presentation and does not independently validate meteorological plausibility. Performance work records its device, workload, frame-time and dropped-frame measurements, and justified thresholds in its bounded plan. Numeric acceptance limits require a measurement method and baseline in that plan. A build or preview alone is not installed acceptance.
