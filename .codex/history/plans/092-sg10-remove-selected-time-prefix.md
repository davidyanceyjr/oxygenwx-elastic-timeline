# Cycle plan — 092-sg10-remove-selected-time-prefix

State: DONE
Plan readiness: Final; product owner approved both required boards as written on 2026-11-02, and scoped installed acceptance is recorded below.
Cycle ID: 092-sg10-remove-selected-time-prefix
Roadmap: SG.10
Depends on: SG.5 (DONE)

## Authority and board package

Follow `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, and `.codex/README.md`.

- **Behavior board:** `.codex/concepts/SG.10-main-timestamp-label-behavior-board.md` — product owner approved as written on 2026-11-02. The owner selected scope option 1: remove `Selected` only from the main readout date/time line. Keep the `Selected location` fallback and forecast-browser `Selected …` copy unchanged.
- **UI artwork board:** `.codex/concepts/SG.10-main-timestamp-label-ui-artwork-board.svg` — product owner approved as written on 2026-11-02. It shows current and proposed main-readout states for two selected instants.
- Approval constraints: change only the visible main readout's timestamp prefix; preserve the existing date, time, timezone, selected instant, forecast values, selected-location fallback, browser copy, and all interactions. The boards define the wording and affected visual states for this cycle; installed screenshots remain the acceptance evidence.

## Objective and production boundary

Remove the literal word `Selected` from the main forecast readout's date/time line, changing `MON Nov 2 · Selected 6:25:31 PM CST` to `MON Nov 2 · 6:25:31 PM CST`. The selected timestamp remains the same source value and retains its weekday, date, clock time, and timezone.

Expected production-code scope is only `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt`, specifically the annotated main readout line tagged `catalogSelectedTime`. Do not change the time formatter, selected-time model, browser/details labels, fallback city copy, forecast values, gestures, accessibility semantics outside that line, or any other screen.

## Functional invariants

- The weekday/date remain formatted from `view.selectedInstant` in `view.zone`; the existing `view.selectedTimeLabel` continues to supply clock time and timezone.
- Selecting another forecast time still updates the displayed line to that same selected instant without a retarget or conversion change.
- Only the visible main readout line loses the prefix. The location fallback `Selected location` and forecast-browser `Selected …` copy remain unchanged per the owner's scope choice.
- No layout, control, interaction, label semantics, date formatting, time formatting, or forecast-value behavior changes beyond the requested wording.

## Ordered work

1. **Finalize plan gate.** Record product-owner approval and constraints for both boards, finalize this plan, and mark SG.10 eligible. Keep the current draft inactive until then.
2. **Preflight and capture before state.** Run `python scripts/dev.py workflow`; confirm connected Pixel serial and physical portrait configuration. Capture the installed main readout with the current prefix in `.codex/test-artifacts/092-sg10-remove-selected-time-prefix/before-installed.png`.
3. **Implement the exact copy edit.** Remove only `Selected ` from the main readout's date/time line in the named production file.
4. **Verify installed states.** Run `python3 scripts/dev.py run --serial 8AKY0M32X`; inspect the portrait default and one alternate day/time selected on the existing timeline. Capture after screenshots and hierarchy XML. Confirm the visible main line omits `Selected`, preserves the displayed date/time/timezone for each selected instant, and remains readable without overlap. The compact and 1.3 font states were already inspected in cycle 091; this change only shortens the line and adds no new layout constraints.
5. **Close out.** Run only plan-authorized checks, inspect `git diff --check` and the final diff, preserve actual evidence under the cycle evidence directory, record unrun states and close the cycle through `python scripts/codex_cycle.py close`.

## Acceptance criteria

1. The installed main readout displays `WEEKDAY DATE · TIME ZONE` with no `Selected` prefix.
2. The line still reflects the same `view.selectedInstant`, `view.zone`, and `view.selectedTimeLabel` before and after a timeline selection.
3. The main date/time line remains readable in the affected Pixel portrait states; the forecast readout layout and unrelated labels are unchanged.
4. `Selected location` fallback and forecast-browser `Selected …` copy remain unchanged.
5. The evidence records exact commands and PASS/FAIL/NOT RUN for scoped checks. No broad RTL, TalkBack, or system acceptance is claimed.

## Verification and evidence

- Build/install: `python3 scripts/dev.py run --serial 8AKY0M32X` after confirming the device serial at execution.
- Installed screenshots: `before-installed.png`, `after-default.png`, and `after-alternate-selection.png` under `.codex/test-artifacts/092-sg10-remove-selected-time-prefix/`.
- Hierarchy captures: `after-default-hierarchy.xml` and `after-alternate-selection-hierarchy.xml` in the same directory.
- Workflow and diff: `python scripts/dev.py workflow` and `git diff --check`.
- No automated tests are included; repository instructions prohibit adding or running tests unless the owner asks.
- Closeout history: `.codex/history/092-sg10-remove-selected-time-prefix.md`.
- Archived plan: `.codex/history/plans/092-sg10-remove-selected-time-prefix.md`.

## Risks and assumptions

- The product owner selected the main timestamp line only; no broader string cleanup is authorized.
- Pixel `8AKY0M32X` was connected for cycle 091 but must be confirmed again for installed acceptance.
- This is a compact copy adjustment. It must not be used to reopen the completed SG.5 scope or modify its other labels and interactions.

## Out of scope

The `Selected location` fallback; forecast-browser/detail copy; accessibility semantics outside the main line; date/time formatter changes; selected-time or timeline behavior; other screens, themes, or locations; layout redesign; broad localization/RTL, TalkBack, or device/system matrices; and all weather/data changes.

## Execution outcome — 2026-11-02

The approved one-word copy edit was installed and inspected on Pixel 3 XL `8AKY0M32X`. The settled default line omits `Selected`; tapping the Wednesday timeline position updates the line to `WED Nov 4 · 12:00:00 PM CST`, matching the selected timeline pill. Both installed states remained readable without overlap. Exact screenshots, hierarchy captures, commands, and limits are recorded in `.codex/test-artifacts/092-sg10-remove-selected-time-prefix/README.md`. The required build/install command and `git diff --check` passed. No tests were run. The location fallback, browser flow, compact/large-font states, RTL, TalkBack, and broad system acceptance were not exercised in this slice.
