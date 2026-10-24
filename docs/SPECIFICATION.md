# Oxygen Time Lab — prototype specification

Status: one bounded standalone UI experiment. Authority intentionally limited to this sibling project.
Reference: oxygenWX commit `ec3f93976f037fbb206089d8b9d9fa1f6f93918b`.

## Goal

A full-screen atmospheric scene controlled by a minimalist bottom time tape. Three-letter day abbreviations and fine tick marks sit in a gradient overlay. A fixed central playhead is the time-reading position.

## Required behaviors

1. **Tap** a day on the tape: select that day's representative summary.
2. **Immediate horizontal drag:** browse daily time, with the tape moving beneath the playhead.
3. **Stationary hold (~480ms) then drag:** switch the whole tape to a uniform hourly scale (`16dp/hour`) and scrub hour by hour. Do not locally expand one day into a focus window.
4. **Collapse/release:** preserve absolute selected timestamp. A presentation-only switch from hourly to daily overview is permitted, but must be labeled distinctly.
5. **No coordinate jump on scale change:** the selected timestamp always projects to the center playhead, regardless of expansion factor.
6. **Time bounds:** clamp at the actual fixture horizon; never invent forecast intervals beyond it.
7. **Accessibility route:** a small HOURS/DAYS control duplicates precision-mode access without reliance on a long-press.
8. **Selected-time cue:** enlarge the selected day/hour label while neighboring labels remain at regular size; keep the selected label centered under the playhead.
9. **Illustration semantics:** show synthetic DEMO DATA; procedural rain streaks are not precipitation-rate measurements.
10. **Main-display navigation:** horizontal swipes shift the selected time by 24 hours while preserving hour-of-day; double-tap or the visible NOW control returns to the current hour within the fixture bounds.

## Architecture

The UI layer consumes typed `ForecastView`, which is derived from sample canonical `ForecastHour` / `ForecastDay` models. `ElasticTimeAxis` is a platform-independent pure monotonic projection, testable without Compose. Atmospheric rendering is decorative, with critical forecast values separately presented as text.

## Exclusions

No production oxygenWX files or navigation changes, no third-party data feed, no official alerts, no persisted preferences, no cached real forecast, no maps, no external services.

## UI acceptance boundary

Subject to installed verification on at least the project's Pixel 3 XL reference viewport: ensure drag vs long-press arbitration is reliable, time remains stable during zoom, no clipped essential text at large font scale, readable timeline under bright/dark sky, honors reduced motion and system RTL where applicable, and TalkBack can reach the fallback control. Compile/test checks alone do not prove these.
