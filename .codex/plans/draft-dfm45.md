# Draft cycle plan — DFM.4.5: Catalog library and period-first editor

State: PLANNED — UNFINALIZED DRAFT
Cycle ID: not assigned; allocate the next chronological ID when this draft is selected.
Roadmap: DFM.4.5
Depends on: DFM.4.2 and DFM.4.4

## Shared authority and handoff

Read `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/README.md`, and the archived [cycle 084 plan](../history/plans/084-dfm4-debug-workbench.md). The original plan's functional invariants, owner decisions, exclusions, restoration contract, canonical 44-local-date horizon, field meaning, and original evidence obligations remain controlling within the assigned scope. The split does not waive an obligation. Cycle 084's partial code is unaccepted working-tree input; inspect and correct it rather than assuming it is complete. Do not overwrite unrelated working-tree changes.

The existing DFM.4 behavior board `.codex/concepts/DFM.4-debug-session-flow-board.md`, overlay artwork `.codex/concepts/DFM.4-normal-debug-overlay-board.png`, catalog artwork `.codex/concepts/DFM.4-catalog-picker-board.png`, and baseline `.codex/concepts/DFM.4-debug-workbench.png` were owner-approved for the original objective. Their scope and constraints remain applicable. **Board gate for this draft:** during review, establish and record whether those approvals make this slice's objective-specific behavior and, where applicable, artwork states independently reviewable; supplement and obtain owner approval if not. This draft is not finalized or activation-ready merely because the parent boards were approved.

Review only directly affected behavior; no broad system/RTL/TalkBack matrix. The owner authorized the focused DFM.4 automated tests; no broad test suite. Record exact commands and actual results under the cycle-specific evidence directory. The connected Pixel may be used, and the owner authorized clearing this app's data if necessary, not the device. Preserve the app data until its loss is necessary, and record any clearing and its effect. No unverified exit is DONE.

## Objective and boundary

Implement the approved debug-only catalog library, period-first event editor, and explicit synthetic preview using the verified storage and coordinator seams. Keep the normal screen and ordinary location paths intact.

## Work and acceptance

- Active-session `#PICKER` opens the library; outside a session it remains ordinary search. Implement named SIMULATED create/edit/save/reopen, supported period/event fields, and immutable REPLAY capture/reopen. Editing and saving never activate. Only explicit Preview activates a validated saved catalog using the existing selected-time path; origin is discoverable and never represented as LIVE.
- Preserve period and 44-date baseline semantics, supported field/missingness rules, namespace isolation and no provider/cache/location mutation. Invalid or changed saved records surface a failure and leave the current view unchanged. Exit restores the exact checkpoint without I/O; ordinary actions exit first.
- Run focused editor/lifecycle/preview integration checks. Install and capture catalog library, editor, SIMULATED and REPLAY previews on the Pixel; exercise creation, saving, editing, reopen, snapshot, explicit preview, return and affected timeline interactions. Review visuals against the approved catalog artwork and record actual evidence. Full cross-slice and release acceptance remains DFM.4.6.

## Verification and closeout

Run `python3 scripts/dev.py workflow` before work and closeout, `python3 scripts/dev.py build` for changed app code, and only the directly affected focused tests with `./gradlew :app:testDebugUnitTest --tests '<fully-qualified focused test class or method>'`. Identify the exact new or affected test names during implementation; record each command and result. Where installed review is required above, use `python3 scripts/dev.py run --serial <verified-serial>` and `python3 scripts/dev.py screenshot --serial <verified-serial> --output .codex/test-artifacts/<cycle-id>/<state>.png`; DFM.4.6 additionally uses `./gradlew :app:assembleRelease` and records the locally signed release install and checks. Preserve real evidence under `.codex/test-artifacts/<cycle-id>/` (for DFM.4.1, `.codex/test-artifacts/085-dfm41-coordinator-lifecycle/`); inspect `git diff --check` and the full relevant diff, and close only if this slice's exit is met. Record failed or NOT RUN checks and follow-up. Do not activate before prerequisite and board gates are resolved.
