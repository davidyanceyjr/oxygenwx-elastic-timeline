# Cycle plan — 014-landscape-high-low

State: DONE
Roadmap: P.2.11
Objective: Keep forecast high/low values readable in compact landscape at 1.3 font scale and verify installed acceptance.

## Scope

Make the smallest layout change needed for the forecast high/low values to remain visible as readable text in compact landscape at 1.3 system font scale. Keep the existing forecast content and interactions; do not redesign the atmospheric scene or timeline.

### Layout decision contract

- Keep the forecast summary owned by `TimeLabScreen()` and make this a presentation-only layout change. Use the available layout bounds to arrange the existing summary content so the high/low row and every other required forecast value remain on screen together. The exact arrangement is an implementation choice; do not move the decision into new UI state, forecast presentation, or timeline behavior.
- Respect the system font scale, including 1.3. Do not counter-scale text, clip or ellipsize required values, overlap them, place them beneath another surface, or trade away another essential value to make high/low visible. Keep forecast facts as text; the atmospheric scene remains decorative.
- If the target viewport cannot show all required text readably through a bounded layout-only change, stop and record the measured constraint and proposed scope decision for the owner. Do not silently relax the text, accessibility, or content requirements.

### Production boundary

- Expected implementation area: `app/src/main/java/com/oxygen/weather/timeline/ui/TimeLabScreen.kt`.
- Inspect the owning composable and layout constraints before editing. If a different file must change, explain why in the cycle evidence and keep it limited to this same layout defect.
- Do not change fixture data, forecast projection semantics, timeline projection, gesture handlers, dependencies, or production oxygenWX files.

## Functional and semantic invariants

- The selected timestamp remains authoritative; layout or orientation changes must not change the selected time.
- Preserve timeline responsiveness and current gesture ownership for daily drag, hold-to-hourly, and hourly scrubbing.
- Keep forecast values, high/low, date, conditions, DEMO DATA provenance, and timeline labels as readable text. Decorative rendering does not replace these values.
- Preserve synthetic DEMO DATA labeling and do not imply that rain rendering is a measured precipitation rate.
- Do not add visible mode controls or alter navigation behavior.

## Ordered steps

1. Inspect `TimeLabScreen()` and the existing baseline `.codex/test-artifacts/014-responsive-readability-recovery/landscape-font-1_3.png`; reproduce the missing high/low values on the installed Pixel 3 XL in landscape at 1.3 font scale. Record the active logical viewport and font settings with the pre-change capture.
2. Make one bounded, presentation-only layout adjustment based on the available landscape bounds so high/low remain visible and legible with the date, heading, other essential forecast values, DEMO DATA label, and timeline labels. Preserve system font scaling and do not change the forecast content or gesture/timeline ownership.
3. Run the planned scaffold/architecture checks and debug build, install the resulting app on the Pixel 3 XL, and capture the landscape 1.3-font state. Exercise an immediate daily drag and hold-then-drag/hourly selection to check responsiveness, gesture ownership, and selected-time stability.
4. Review the capture at full resolution, record the three cross-cutting dispositions (responsiveness, gesture predictability, readability), note RTL and reduced-motion applicability, inspect the final diff, and close with exact command results and evidence limits.

## Acceptance and evidence

- On the installed Pixel 3 XL, a fresh-build landscape capture at system font scale 1.3 visibly includes readable high and low values alongside the date, heading, other essential forecast values, DEMO DATA label, and timeline labels, without clipping or overlap.
- The installed interaction review records an evidence-backed disposition for responsiveness, gesture ownership/predictability, and readability. Selected time remains unchanged by orientation/layout and scale transitions except when the user performs the tested gesture.
- Record whether RTL and reduced-motion checks apply to the implementation. If direction or animation behavior changes, verify the affected condition; otherwise record why those behaviors are unaffected.
- Save actual screenshots, settings, interaction observations, and command logs under `.codex/test-artifacts/014-landscape-high-low/`; close the outcome in `.codex/history/014-landscape-high-low.md`.
- Planned commands: `python scripts/dev.py workflow`, `python scripts/dev.py contract`, `python scripts/dev.py build`, `python scripts/dev.py run --serial 8AKY0M32X`, and `python scripts/dev.py screenshot --serial 8AKY0M32X --output ...`; `git diff --check` before closeout. Do not run tests or lint unless the user separately asks.

## Risks and assumptions

- The prior fresh-build capture confirms the defect on the required Pixel 3 XL at 1.3 font scale and landscape logical size 2240×1260; the host SDK and JDK were installed outside the repository during cycle 014 recovery.
- Layout space is constrained by the large temperature and bottom timeline. A layout adjustment must retain the existing essential text and not trade away another required value.
- Device evidence establishes this target device/state only; it does not imply acceptance on other devices.

## Out of scope

- Fixing unrelated readability or interaction issues, changing forecast data or presentation values, adding new controls, changing animation/gesture semantics, and production integration.
- Completing P.2.6 or closing the full installed-acceptance review in P.2.9; those remain separate roadmap outcomes.
