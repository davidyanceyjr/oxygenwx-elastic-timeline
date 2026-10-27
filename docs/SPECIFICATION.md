# Oxygen Time Lab — prototype specification

Experience-level principles and acceptance criteria are maintained in the adopted companion [`OXYGEN_EXPERIENCE_SPECIFICATION.md`](OXYGEN_EXPERIENCE_SPECIFICATION.md). This document remains controlling for the current prototype's behavior and hard semantic/architecture constraints. The owner-approved product direction makes this repository the successor OxygenWX app; staged successor work is authorized only through the bounded WX roadmap and its active cycle plan. This authority update authorizes future work to be planned and implemented in this repository, but does not itself authorize implementation outside an active bounded plan.

The adopted [`OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md`](OXYGEN_ATMOSPHERE_ENGINE_SPECIFICATION.md) is a subordinate architecture proposal for atmospheric and environment work. It cannot override this document or the experience contract, or authorize application changes by itself. Its adoption boundary and current-tree review are recorded in `.codex/history/023-atmosphere-spec-intake.md`.

Status: successor OxygenWX app in staged development. The current installed application remains an isolated synthetic-data prototype; future catalog, live-data, location, cache, and source-switching capabilities require their own approved roadmap cycles. This repository is the successor product boundary. The deprecated oxygenWX application and its production files are not modified by this roadmap.
Reference: oxygenWX commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`.

## Goal

A full-screen atmospheric scene controlled by a minimalist bottom time tape. Three-letter day abbreviations and fine tick marks sit in a gradient overlay. A fixed central playhead is the time-reading position.

## Required behaviors

1. **Tap** a day on the tape: select that day's representative summary.
2. **Immediate horizontal drag:** browse daily time, with the tape moving beneath the playhead.
3. **Stationary hold (~480ms) then drag:** switch the whole tape to a uniform hourly scale (`16dp/hour`) and scrub hour by hour. Do not locally expand one day into a focus window.
4. **Collapse/release:** preserve absolute selected timestamp. A presentation-only switch from hourly to daily overview is permitted; hourly ticks/labels and accessibility semantics distinguish the scales without a visible mode-control row.
5. **No coordinate jump on scale change:** the selected timestamp always projects to the center playhead, regardless of expansion factor.
6. **Time bounds:** clamp at the actual fixture horizon; never invent forecast intervals beyond it.
7. **Accessibility route:** the time tape exposes a screen-reader action to switch daily/hourly scales; do not add a visible mode-control row.
8. **Selected-time cue:** enlarge the selected day/hour label while neighboring labels remain at regular size; keep the selected label centered under the playhead.
9. **Illustration semantics:** show synthetic DEMO DATA; procedural rain streaks are not precipitation-rate measurements.
10. **Main-display navigation:** horizontal swipes shift the selected time by 24 hours while preserving hour-of-day; double-tap returns to the current hour within the fixture bounds.

## Architecture

The UI layer consumes typed `ForecastView`, which is derived from sample canonical `ForecastHour` / `ForecastDay` models. `ElasticTimeAxis` is a platform-independent pure monotonic projection, testable without Compose. Atmospheric rendering is decorative, with critical forecast values separately presented as text.

## Hard visual-quality rule

Every visual enhancement must preserve the timeline's responsiveness, gesture predictability, and readability. A weather-scene change is incomplete if it makes scrubbing feel delayed, changes gesture ownership or arbitration unexpectedly, or obscures forecast values or timeline labels.

## Successor direction and durable boundaries

- **Date window:** a location-scoped catalog targets 44 local calendar dates: D-30 through D-1 as historical dates, followed by D through D+13 as forecast dates. D is the local date at the catalog's `asOf: Instant` in the location's IANA timezone. The 44 dates do not promise 1,056 hourly values; DST, provider gaps, and partial coverage remain explicit.
- **Canonical data and parity:** live, deterministic simulated, and recorded replay datasets use one canonical schema. Origin and product provenance are metadata; switching replaces a whole validated dataset atomically. Missing values remain unavailable, never defaulted to zero. Archived model forecasts are not described as verified observations.
- **Official alerts:** official weather alerts remain a separate future subsystem and are not catalog conditions or inferred warning flags.
- **Retained invariants:** absolute selected-time authority; no time jump from zoom; predictable ownership between the timeline and other gestures; readable forecast facts and accessible alternatives; RTL/reduced-motion meaning; truthful source, product, freshness, and missing-data semantics; decorative rendering does not invent meteorological facts.
- **Predecessor boundary:** the deprecated oxygenWX application's files and four-page navigation are not changed by this successor roadmap. This app keeps its immersive timeline as the primary experience; no timeline is inserted into the predecessor's outer pager.
- **Explicit exclusions:** no official-alert implementation, background GPS tracking, production changes to the deprecated oxygenWX repository, or unbounded integration/release redesign. Each future capability remains gated by its own WX plan and verification.

The current prototype still has no live provider, location selection, real-data cache, or official alerts. Those are future roadmap outcomes, not current capabilities.

## Production-release presentation requirements

The following are beta/demo scaffolding and must not appear in a final production release:

- The `OXYGEN / TIME LAB` experimental title/branding.
- The `DEMO DATA` label, once the screen is backed by production forecast data and can present accurate source/provenance information.
- The instructional bottom row (`SWIPE DAYS · HOLD TAPE FOR HOURS`) and the prototype `NOW` button (removed from the prototype in cycle 006).
- The visible `HOURLY PRECISION` / `DAILY NAVIGATION` and `HOURS` / `DAYS` mode-control row (removed from the prototype in cycle 006).

The instruction and mode-control rows above are already absent from the prototype. Keep the demo label while this prototype uses synthetic fixtures. The center double-tap remains the prototype's way to realign to the current fixture hour; define how timeline reset behavior fits the production navigation and interaction model before integration.

## UI acceptance boundary

Subject to installed verification on at least the project's Pixel 3 XL reference viewport: ensure drag vs long-press arbitration is reliable, time remains stable during zoom, no clipped essential text at large font scale, readable timeline under bright/dark sky, honors reduced motion and system RTL where applicable, and TalkBack can reach the timeline's custom scale action. Compile/test checks alone do not prove these.
