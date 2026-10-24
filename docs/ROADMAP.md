# Oxygen Time Lab — scoped roadmap

## P.1 — Standalone elastic time tape (implementation complete; installed gate pending)

- [x] Isolated Android Gradle/Kotlin/Compose scaffold using oxygenWX versions.
- [x] Deterministic multi-day weather fixture and separate presentation model.
- [x] Pure time<->pixel mapping and focused unit tests.
- [x] Atmospheric Canvas with live selection and compact timeline.
- [x] Tap, daily drag, hold-to-expand, hourly drag, fixed playhead, collapse.
- [x] Workflow/plan/history documents; owner app remains unmodified.
- [ ] `:app:testDebugUnitTest`, `:app:assembleDebug`, `:app:lintDebug` on Android host.
- [ ] Pixel 3 XL install, visual capture, and touch-behavior acceptance.

## P.2 — Owner review, visual polish (PLANNED)

- Replace localized focus-day expansion with uniform hourly spacing and a magnified selected-time label (cycle 003).
- Add main-display 24-hour swipes and double-tap/NOW return behavior (cycle 005).
- Inspect real device touch slop and mode switching; refine drag/inertia if needed.
- Contrast and landscape/small-screen text at representative DPI and large font.
- Verify TalkBack semantics and explicit access to hourly mode.
- Reduced-motion policy and RTL chronological controls.
- Preserve screenshots and regression evidence in `.codex/test-artifacts/`.

## P.3 — Integration feasibility spike (DEFERRED)

- Explicit owner approval before modifying oxygenWX's `Now -> Hourly -> Daily -> Details` navigation/outer pager.
- A gesture-ownership design that does not conflict with the existing full-screen pager.
- Bind real normalized forecast presentation and provenance without affecting cache/network/provider flows.
- Keep sample-only graphics separate from meteorological truth and real alert semantics.
