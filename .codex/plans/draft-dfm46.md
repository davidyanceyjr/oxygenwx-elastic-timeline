# Draft cycle plan — DFM.4.6: Workbench integration and release acceptance

State: PLANNED — UNFINALIZED DRAFT
Cycle ID: not assigned; allocate the next chronological ID when this draft is selected.
Roadmap: DFM.4.6
Depends on: DFM.4.2, DFM.4.3 and DFM.4.5

## Shared authority and handoff

Read `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `.codex/README.md`, and the archived [cycle 084 plan](../history/plans/084-dfm4-debug-workbench.md). The original plan's functional invariants, owner decisions, exclusions, restoration contract, canonical 44-local-date horizon, field meaning, and original evidence obligations remain controlling within the assigned scope. The split does not waive an obligation. Cycle 084's partial code is unaccepted working-tree input; inspect and correct it rather than assuming it is complete. Do not overwrite unrelated working-tree changes.

The existing DFM.4 behavior board `.codex/concepts/DFM.4-debug-session-flow-board.md`, overlay artwork `.codex/concepts/DFM.4-normal-debug-overlay-board.png`, catalog artwork `.codex/concepts/DFM.4-catalog-picker-board.png`, and baseline `.codex/concepts/DFM.4-debug-workbench.png` were owner-approved for the original objective. Their scope and constraints remain applicable. **Board gate for this draft:** during review, establish and record whether those approvals make this slice's objective-specific behavior and, where applicable, artwork states independently reviewable; supplement and obtain owner approval if not. This draft is not finalized or activation-ready merely because the parent boards were approved.

Review only directly affected behavior; no broad system/RTL/TalkBack matrix. The owner authorized the focused DFM.4 automated tests; no broad test suite. Record exact commands and actual results under the cycle-specific evidence directory. The connected Pixel may be used, and the owner authorized clearing this app's data if necessary, not the device. Preserve the app data until its loss is necessary, and record any clearing and its effect. No unverified exit is DONE.

## Objective and boundary

Verify the combined original DFM.4 outcome on the installed reference device and release variant. This is focused integration acceptance for DFM.4, not a broad app/system TEST matrix; fix only defects within the approved DFM.4 boundary or replan.

## Work and acceptance

- Verify the complete approved debug journey: clean LIVE view, exact selected time, session entry/exit, overlay presets, normal search, ordinary selection and refresh/retry while debug/preview is active, SIMULATED editing/save/reopen/preview, immutable REPLAY capture/reopen/preview, and no-I/O return to the original view. Distinguish expected ordinary action persistence/network calls from debug work. Record source/time and affected facts, labels, semantics, timeline responsiveness and gesture ownership.
- Capture installed normal LIVE, menu, overlay off/on, library, editor, SIMULATED preview, REPLAY preview and release normal screens; ensure prior captures are reused only if they establish the final integrated build. Record Pixel, viewport, density, font and build/interaction methods. Reduced-motion check applies only if changed motion requires it; no broad accessibility, RTL or environment claim.
- Assemble release, inspect manifest/DEX for debug dispatch, workbench, debug-only catalogs and diagnostics, and directly verify `#DEBUG`/`#PICKER` have no developer effect on the installed release. The owner authorized use of the Pixel and clearing only this app's data if necessary; document any clear, signing method, app replacement and restoration. Preserve app data until earlier acceptance has been captured. Do not clear the device.
- Manually inspect user-facing manuals and record files checked to establish developer commands remain absent. Complete the remaining focused DFM.4 integration tests, release isolation checks, diff inspection and exact evidence record. All original DFM.4 exits and limitations must be accounted for before DONE.

## Verification and closeout

Run `python3 scripts/dev.py workflow` before work and closeout, `python3 scripts/dev.py build` for changed app code, and only the directly affected focused tests with `./gradlew :app:testDebugUnitTest --tests '<fully-qualified focused test class or method>'`. Identify the exact new or affected test names during implementation; record each command and result. Where installed review is required above, use `python3 scripts/dev.py run --serial <verified-serial>` and `python3 scripts/dev.py screenshot --serial <verified-serial> --output .codex/test-artifacts/<cycle-id>/<state>.png`; DFM.4.6 additionally uses `./gradlew :app:assembleRelease` and records the locally signed release install and checks. Preserve real evidence under `.codex/test-artifacts/<cycle-id>/` (for DFM.4.1, `.codex/test-artifacts/085-dfm41-coordinator-lifecycle/`); inspect `git diff --check` and the full relevant diff, and close only if this slice's exit is met. Record failed or NOT RUN checks and follow-up. Do not activate before prerequisite and board gates are resolved.
