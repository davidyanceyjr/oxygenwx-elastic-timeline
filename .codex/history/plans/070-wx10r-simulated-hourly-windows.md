# Cycle plan — 070-wx10r-simulated-hourly-windows

State: ACTIVE
Roadmap: WX.10R
Objective: Expand the deterministic SIMULATED catalog to every real local hour across all 44 dates, preserving event families, synthetic provenance, and DST-aware support

## Owner concept gate

**Approved 2026-10-29:** `.codex/concepts/WX.10R-simulated-hourly-windows.md`. The owner approved morning anchor values from 9 AM through 8 PM, night anchor values from 9 PM through 8 AM, one fact per real local hour, step-held synthetic values, preserved missingness, one-hour accumulation semantics, and point-versus-interval date support. Do not imply provider completeness or interpolate unsupported fields. No UI/alert behavior is included.

**Owner direction:** On 2026-10-29 the owner selected one fact per real local hour on each date (including both repeated fall-back hours and no nonexistent spring-forward hour), deterministic within-day event windows preserving existing event families, and the exact 9 AM/9 PM anchor schedule described above.

## Production boundary

This owner-directed follow-up reopens only the deterministic SIMULATED development product defined by WX.10. The shared canonical 44-date contract remains sparse-capable: LIVE provider and unrelated REPLAY datasets must continue to report missing/partial hourly coverage honestly. The WX.10R SIMULATED product specifically supplies one fact at each real local top-of-hour instant in all 44 dates. This is a data-slice; WX.25 consumes these rows and owns installed timeline/browser acceptance afterward.

Potential application files, subject to concept approval and evidence:

- `app/src/main/java/com/oxygen/weather/timeline/data/catalog/SimulatedCatalogSource.kt`
- `app/src/debug/java/com/oxygen/weather/timeline/data/debug/DebugCatalogActivity.kt` for SIMULATED and derived deterministic REPLAY dataset identity/fixture version only
- `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, this plan, concept, and cycle evidence/history files

Do not edit canonical model/schema, Open-Meteo source, cache/activation logic, forecast presentation, timeline/browser UI, or atmospheric rendering unless a concrete data-contract failure proves a necessary compatible correction; such a boundary requires replanning before edits.

## Functional invariants

- Keep the 44 local dates D−30 through D+13 from `asOf` and the supplied IANA timezone, one fixed sequence, one deterministic output per request, all prior condition/phenomenon cases, all existing 9 AM/9 PM anchors, and synthetic product/source provenance.
- Generate the hourly grid by iterating elapsed Instants from each local date's start to the next date's start. Fall-back creates both repeated local-hour Instants; spring-forward omits the nonexistent hour. Do not generate hours by independently resolving ambiguous `LocalDateTime`s.
- A point fact's date is the local date of `validTime`. A one-hour accumulation interval is supported by the date containing its entire `[start,end)` interval; the interval ending at midnight belongs to the prior date while its point fact belongs to the new date.
- Conditions and phenomena follow the owner-approved deterministic event windows. Missing fields stay null/unsupported, explicit zero stays present, and no unsupported weather vocabulary or official-alert semantics are introduced.
- Preserve typed metric units, per-hour accumulation meaning, coverage metadata, and distinct precipitation/wind fields. Any catalog/product ID change must prevent a stale 88-fact cache or replay fixture from appearing to satisfy hourly coverage.

## Steps

- [ ] Obtain owner approval of the data-shape concept before activation; record exact approval/constraints and update the concept/plan if needed.
- [ ] Inspect the existing `SimulatedCatalogSource`, daily anchor table, accumulation interval support, `CatalogWindow` date/support rules, source identity/cache keys, and the WX.22 deterministic replay preparation path. Record compatibility effects before changing IDs or fixture generation.
- [ ] Implement only the approved real-hour grid and event-window projection. Build support by each point fact's local date and each accumulation interval's complete local date; preserve old anchors exactly. Version only the dataset/product/fixture identities required to avoid stale sparse catalog reuse.
- [ ] Run `python scripts/dev.py workflow` and `python scripts/dev.py contract`. Do not add or run automated tests under the repository instruction unless the owner separately asks. Build/install the debuggable app only as authorized in this active plan; use the connected Pixel 3 XL to activate the versioned SIMULATED route and inspect its recorded catalog data read-only.
- [ ] Capture installed debug-source identity and a compact data audit showing 44 dates, expected 23/24/25 hourly counts for test timezones/dates, every event family, unchanged 9 AM/9 PM anchors, accumulation support at date boundaries, product provenance, and cache/replay version identity. This is data/debug-source acceptance, not WX.25 timeline/browser acceptance.
- [ ] Inspect `git diff --check` and final diff. Record exact installed/build/contract evidence and any unverified DST/timezone paths; close DONE only if every roadmap exit is evidenced, otherwise close INCOMPLETE and name follow-up.

## Acceptance/evidence

Evidence directory: `.codex/test-artifacts/070-wx10r-simulated-hourly-windows/`; closeout `.codex/history/070-wx10r-simulated-hourly-windows.md`; archived plan `.codex/history/plans/070-wx10r-simulated-hourly-windows.md`.

Required evidence:

- Deterministic inventory for a fixed `asOf` and at least one 24-hour zone day, one 23-hour spring-forward date, and one 25-hour fall-back date; verify each fact is exactly on a real local hour, unique by `Instant`, within the correct date, and the total equals the sum of real hours across the 44 local dates.
- A day/fact coverage matrix showing every existing condition and phenomenon, including clear, partly cloudy, cloudy, rain, snow, storm, haze, fog, freezing rain, and ice pellets; preserve any other currently encoded case.
- Direct comparison of every original 9 AM/9 PM anchor value, plus missing-versus-zero semantics, selected metric values, one-hour amounts and their intervals, interval support at midnight, and synthetic source/product provenance.
- Installed debuggable route records the new identity and loads the denser SIMULATED catalog. A source-derived fixed REPLAY debug fixture is updated/versioned only if required to prevent stale fixture ambiguity; captured origin remains REPLAY and source provenance retains SIMULATED.
- Verification log for the exact commands. No timeline visuals, font/RTL/reduced-motion matrix, or TalkBack acceptance is claimed; those remain WX.25/WX.29 work.

## Risks / assumptions

- The proposed concept assigns existing 9 AM values to local hours 09:00–20:00 and 9 PM values to 21:00–08:00. This is an intentionally synthetic step profile; owner approval is required before implementation.
- A midnight-ending accumulation interval belongs to the preceding local date. `CatalogWindow` accepts that interval only when expected support is attributed to the complete interval's date; point support remains tied to `validTime`.
- Debug replay fixture persistence may retain the prior 88-fact recording. Inspect its identity and invalidation path; never relabel old data as dense.
- Android build/install/device access can fail independently of source correctness. If it does, preserve the data inventory and exact failure, and do not claim installed acceptance.

## Out of scope

- Any LIVE/provider completeness change, hourly interpolation from real providers, or change to the shared catalog's honest partial-coverage semantics.
- Official alerts, new weather families, provider/network work, canonical schema migration, production startup/location/cache redesign, and deprecated oxygenWX files.
- Timeline/browser interaction fixes, UI redesign, final visual polish, broad accessibility/RTL/reduced-motion review, and atmospheric scene changes; WX.25/WX.29 own these outcomes.
- Adding or running automated tests unless the owner explicitly authorizes them.
