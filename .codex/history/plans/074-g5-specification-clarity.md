# Cycle 074 — G.5 specification clarity

**State:** ACTIVE — board approved, plan activated, documentation work completed.
**Cycle ID:** `074-g5-specification-clarity`
**Roadmap slice:** G.5 — Resolve specification audit findings
**Current cycle pointer:** `.codex/current.md` points to this active cycle until closeout.

## Objective and authority

Resolve all ten prioritized findings in `findings.md` in one documentation-only cycle. Align the controlling prototype and successor rules in `docs/SPECIFICATION.md` with the adopted experience acceptance contract in `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`. Preserve the settled successor boundary and do not change app source, data, tests, UI, or the deprecated oxygenWX project.

Authority: project `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, and `.codex/README.md`. `python scripts/dev.py workflow` reported `Workflow scaffold: OK` and `.codex/current.md` IDLE during planning on 2026-10-30. The owner selected a new governance slice before SG.0. Cycle 073 is a parked WX.27 draft; 074 is the next chronological cycle ID.

## Board gate

Behavior board: `.codex/concepts/G.5-specification-clarity-behavior-board.md`. It diagrams selection transitions, missing-data states, reset references, and alert scope. The product owner reviewed and approved the board in conversation on 2026-10-30 after it was displayed in `feh` on X display `:0`; no additional constraints were stated. Its selected-time, availability, reset, and alert rules govern this plan's documentation scope and the corresponding exit checks. This slice edits documentation only and makes no UI or visual implementation change; UI artwork for any subsequent implementation belongs to its own bounded visual slice.

## Owner decisions to encode

1. Daily tap and daily drag release select the target day's representative time; a scale-only collapse preserves the exact selected instant.
2. Representative time is exactly 12:00 in the selected location's IANA timezone. A partial boundary date uses the existing catalog-window clamp rule; an in-window missing fact remains unavailable.
3. In-window data gaps remain selectable, with explicit unavailable facts and no condition imagery that implies a forecast fact exists. Navigation can cross gaps.
4. Live reset, including a stale live catalog, targets the current instant. Simulated and recorded replay reset to catalog `asOf`. An out-of-window target clamps to the nearest boundary and the adjustment is communicated.
5. A tap on an hourly tape resolves the touched instant on the hourly scale before collapse, then preserves that instant.
6. A main-area calendar-day swipe targets the adjacent local date at the same wall time, applying the existing DST rule. Missing facts use the unavailable state; out-of-window targets clamp at the nearest boundary.
7. Acceptance requires a recorded device/layout/input/motion matrix. Numeric thresholds belong in bounded implementation plans where measurement methods and baselines make them meaningful.
8. Real-data integration does not require official alerts. If alerts are later offered, they are authoritative and separate; before then, the UI must not imply that no displayed alerts means no alerts exist.

## Bounded work

1. At activation, confirm the approved board and recorded constraints still match the current specification and roadmap baseline.
2. Edit `docs/SPECIFICATION.md` to make the selected-time, daily/hourly gesture, gap, reset, calendar-swipe, and alert rules explicit. Keep current prototype behavior and successor rules distinguishable. Do not treat a missing fact as numeric zero.
3. Align `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` with those rules. Update interaction acceptance scenarios for scale-only collapse, pre-collapse hourly tap resolution, missing hours/days, partial window boundaries, reset by product, and DST swipes. Keep testable expected outcomes without claiming installed acceptance.
4. Define the acceptance matrix required of later implementation slices: reference viewport/device and applicable compact, large-font, bright/dark, RTL, reduced-motion, TalkBack/non-gesture, gesture-arbitration, and performance states. Require recorded pass/fail/not-run and actual evidence. Avoid unsupported universal numeric targets.
5. Resolve the alert language as a conditional future capability. Remove the reopened decision about modifying or redesigning the deprecated app.
6. Separate durable requirements from dated baseline and proposed sequence text; consolidate repetitive prose that gives no decision rule. Preserve useful history references and the authority order.
7. Trace each `findings.md` item to an exact disposition in cycle evidence. Inspect the final documentation diff and run `git diff --check`.

## Exit and evidence

The slice can close only when the approved board and constraints are recorded, all ten findings have a specific disposition, both specifications agree on every chosen rule, the acceptance matrix has observable results and evidence requirements, roadmap authority and the successor boundary remain coherent, and the final diff passes `git diff --check`. Record exact checks and any limitations. Do not claim device or visual acceptance from documentation review.

Evidence targets: `.codex/test-artifacts/074-g5-specification-clarity/README.md`, `.codex/history/074-g5-specification-clarity.md`, and `.codex/history/plans/074-g5-specification-clarity.md` at closeout. These paths are targets, not existing results.

## Verification planned, not run

- `python scripts/dev.py workflow` at activation and closeout.
- Read-only cross-check of the revised specifications, roadmap, board, and each finding.
- `git diff --check` and complete final diff inspection.
- No automated tests, build, lint, or installed device acceptance in this documentation-only cycle.

## Exclusions and risk

Do not implement app behavior, scene generation, alerts, visual design, release readiness, or predecessor integration. Do not change SG.0's objective. The principal risk is writing a contract that appears to assert unverified current behavior; label successor rules and future acceptance distinctly from observed prototype behavior. Any material new product decision discovered during execution must return to the owner before the corresponding wording is finalized.
