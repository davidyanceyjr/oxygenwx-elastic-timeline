# Oxygen Time Lab — scoped roadmap

## P.1 — Standalone elastic time tape (core implementation and device gate complete)

- [x] Isolated Android Gradle/Kotlin/Compose scaffold using oxygenWX versions.
- [x] Deterministic multi-day weather fixture and separate presentation model.
- [x] Pure time<->pixel mapping and focused unit tests.
- [x] Atmospheric Canvas with live selection and compact timeline.
- [x] Tap, daily drag, hold-to-expand, hourly drag, fixed playhead, collapse.
- [x] Workflow/plan/history documents; owner app remains unmodified.
- [ ] `:app:testDebugUnitTest` and `:app:lintDebug` on Android host.
- [x] Pixel 3 XL install and visual capture; core day/hour browsing and selection interactions checked on device (cycles 002–005).
- [x] Workflow and contract checks; debug build and install completed on the Android host.
- [ ] Accessibility, large-font, landscape, RTL, and reduced-motion acceptance (tracked in P.2).

## P.2 — Owner review, visual polish (IN PROGRESS)

- [x] Replace localized focus-day expansion with uniform hourly spacing and a magnified selected-time label (cycle 003).
- [x] Add main-display 24-hour swipes and double-tap/NOW return behavior (cycle 005).
- [x] Remove instructional footer and visible DAYS/HOURS mode row; preserve reset by double-tap and mode access through the tape's screen-reader action (cycle 006).
- Inspect real device touch slop and mode switching; refine drag/inertia if needed.
- Contrast and landscape/small-screen text at representative DPI and large font.
- Verify TalkBack semantics and explicit access to hourly mode.
- Reduced-motion policy and RTL chronological controls.
- Preserve screenshots and regression evidence in `.codex/test-artifacts/` (cycles 002–006).

## P.3 — Integration feasibility spike (DEFERRED)

- Explicit owner approval before modifying oxygenWX's `Now -> Hourly -> Daily -> Details` navigation/outer pager.
- A gesture-ownership design that does not conflict with the existing full-screen pager.
- Bind real normalized forecast presentation and provenance without affecting cache/network/provider flows.
- Keep sample-only graphics separate from meteorological truth and real alert semantics.

## P.4 — Production-release presentation cleanup (RELEASE-GATED)

- Remove the `OXYGEN / TIME LAB` experimental branding.
- Replace `DEMO DATA` only after production forecast data and accurate provenance are available.
- [x] Remove the instructional bottom row (`SWIPE DAYS · HOLD TAPE FOR HOURS`) and prototype `NOW` button from the experiment (cycle 006).
- Decide how timeline reset behavior fits the production navigation and interaction model before integration.
