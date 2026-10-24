# Oxygen Time Lab — prototype specification

Status: one bounded standalone UI experiment. Authority intentionally limited to this sibling project.
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

## Exclusions

No production oxygenWX files or navigation changes, no third-party data feed, no official alerts, no persisted preferences, no cached real forecast, no maps, no external services.

## Production-release presentation requirements

The following are beta/demo scaffolding and must not appear in a final production release:

- The `OXYGEN / TIME LAB` experimental title/branding.
- The `DEMO DATA` label, once the screen is backed by production forecast data and can present accurate source/provenance information.
- The instructional bottom row (`SWIPE DAYS · HOLD TAPE FOR HOURS`) and the prototype `NOW` button (removed from the prototype in cycle 006).
- The visible `HOURLY PRECISION` / `DAILY NAVIGATION` and `HOURS` / `DAYS` mode-control row (removed from the prototype in cycle 006).

The instruction and mode-control rows above are already absent from the prototype. Keep the demo label while this prototype uses synthetic fixtures. The center double-tap remains the prototype's way to realign to the current fixture hour; define how timeline reset behavior fits the production navigation and interaction model before integration.

## UI acceptance boundary

Subject to installed verification on at least the project's Pixel 3 XL reference viewport: ensure drag vs long-press arbitration is reliable, time remains stable during zoom, no clipped essential text at large font scale, readable timeline under bright/dark sky, honors reduced motion and system RTL where applicable, and TalkBack can reach the timeline's custom scale action. Compile/test checks alone do not prove these.
