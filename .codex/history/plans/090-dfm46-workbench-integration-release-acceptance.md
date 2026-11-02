# Cycle plan — 090-dfm46-workbench-integration-release-acceptance

State: DONE
Plan readiness: Finalized and closed; product-owner-approved board package and complete acceptance evidence recorded.
Cycle ID: 090-dfm46-workbench-integration-release-acceptance
Roadmap: DFM.4.6
Depends on: DFM.4.2, DFM.4.3, and DFM.4.5 (all DONE)

## Authority and handoff

Follow `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, and the original DFM.4 plan in `.codex/history/plans/084-dfm4-debug-workbench.md`. DFM.4.6 verifies the combined original DFM.4 outcome after its implementation was split across cycles 085–089. Those cycles' closeouts and evidence are inputs to the integration review; they do not waive any original DFM.4 obligation. Cycle 084 remains an incomplete attempt, and its partial code is unaccepted until verified against the approved contracts. Preserve unrelated working-tree changes.

## Approved board package

- **Behavior board:** `.codex/concepts/DFM.4.6-integrated-workbench-behavior-board.md` — product owner approved 2026-11-02 as shown. It governs the integrated debug journey, saved-record and failure paths, exact no-I/O checkpoint restoration, ordinary-action ordering, release boundary, and the evidence map.
- **UI artwork board:** `.codex/concepts/DFM.4.6-integrated-workbench-ui-artwork-board.svg` and preview `.codex/concepts/DFM.4.6-integrated-workbench-ui-artwork-board.png` — product owner approved 2026-11-02 as shown. It identifies the final installed debug and release states to capture and compare against the existing approved screen compositions. Prior-cycle screenshots are appearance references only, not DFM.4.6 acceptance evidence.
- **Approval constraint:** DFM.4.6 remains integration acceptance only. The owner explicitly deferred a draggable/resizable diagnostic panel to a separate follow-up. Do not add that behavior or redesign the overlay in this cycle.
- The parent DFM.4 session-flow, overlay, catalog-picker, DFM.4.3 clean-view, DFM.4.4 storage, and DFM.4.5 library/editor boards remain controlling references. The objective-specific boards guide scope and evidence; installed verification remains required.

## Objective and boundary

Verify the combined original DFM.4 debug-workbench outcome on the installed reference device and release variant. Confirm that the debug journey composes correctly across DFM.4.1–4.5, that exact restoration leaves the pre-session LIVE state intact, and that release has no developer command or workbench effect. This is a focused integration and release-boundary acceptance cycle, not a broad app/system TEST matrix. Fix only reproduced defects inside the approved DFM.4 boundary; replan defects or requests outside it.

## Functional invariants

- `#DEBUG` and `#PICKER` have developer effects only in their approved debug-build/session conditions. A fresh debug session opens with the overlay off; `#PICKER` dispatch requires an active debug session. Release commands have no developer effect.
- Overlay visibility and the selected local display preset remain independent. Diagnostics are labeled as such, follow the selected forecast state, and leave forecast facts and the time tape readable.
- SIMULATED remains editable local synthetic data. REPLAY remains an immutable snapshot with captured SIMULATED origin. Save and snapshot capture do not activate; only explicit Preview of a saved, revalidated record activates through the established coordinator path.
- Missing values remain unavailable, explicit zero remains a value, probability stays distinct from amount, unsupported conditions remain absent, and synthetic provenance remains visible.
- Debug entry/exit and preview do not change the selected location or write LIVE/provider/cache data. Exit restores the exact pre-session location, active catalog identity, selected `Instant`, and corresponding facts, turns the overlay off, and performs no provider request or catalog/cache write.
- An explicitly requested ordinary place selection or refresh/retry ends debug after no-I/O restoration, then performs the ordinary action under its existing persistence/network behavior. A failed ordinary action leaves debug off. Late preview/exit completion cannot replace the newer ordinary state.
- Preserve the approved clean forecast composition, atmosphere, selected-time authority, source truth, timeline responsiveness, and gesture ownership. No drag/resize behavior is added to the diagnostic panel.

## Installed review conditions

- Capture the nine states on the approved artwork board: normal LIVE baseline, debug menu with overlay off, diagnostic overlay with preset, saved catalog library, event editor, SIMULATED preview, REPLAY preview, exact restored LIVE state, and installed release normal screen. The cycle 087–089 captures are appearance references only; DFM.4.6 captures must come from the final integrated build.
- Use the Pixel 3 XL in portrait for the integrated debug journey. Cycle 086 recorded a 1440×2960 display, 1440×2733 content viewport, density 560, and font scale 1.0; recheck and record the actual values at execution. Record locale and animation settings as well. The release check must use a non-destructive isolated install and record its device and viewport conditions.
- RTL and reduced-motion sweeps are not applicable to this integration-only objective because no text-direction or motion change is planned. Do not add broad matrices. If a reproduced in-scope defect directly changes one of those behaviors, check only the affected condition and record the reason.

## Ordered work

1. **Preflight and tree review.** Reconfirm DFM.4.6 is the sole eligible `NEXT` slice, its DFM.4.2/4.3/4.5 dependencies are DONE, `.codex/current.md` points to this PLANNED cycle, and `python3 scripts/dev.py workflow` passes. Review the final relevant changes and cycle records 084–089. Inspect the current DFM.4 seams, including cycle 084's unaccepted partial code, without treating it as accepted implementation. Identify exact affected source/test paths before changing code.
2. **Installed baseline.** Recheck the authorized Pixel 3 XL, expected serial `8AKY0M32X`, build variant, portrait orientation, viewport/density, font scale, animation settings, locale, and network state. Capture the current normal LIVE forecast, location/catalog identity, selected `Instant`, and visible facts before entering debug. Preserve app data until the baseline and debug evidence are captured. The owner authorized clearing this app's data only if necessary, not clearing the device; record any app-data clear and restoration. That authorization does not permit uninstalling, overwriting, or clearing the installed debug app/data to force a release install.
3. **Integrated debug session and overlay.** On the installed debug build, exercise `#DEBUG`, fresh-session default-off menu, preset selection/configuration, independent overlay off/on, and source/details route as applicable. Confirm the overlay remains diagnostic and readable, selected time and weather facts stay stable, and timeline gesture ownership/responsiveness is preserved. Exercise `#PICKER` in an active session and the ordinary-search route outside one.
4. **Catalog integration.** From the active session, verify the library, SIMULATED create/edit/save/reopen/cancel and explicit Preview, REPLAY capture/reopen/Preview, and the fail-closed saved-record path. Confirm edits and captures remain local and synthetic; REPLAY is unchanged; save/capture alone do not activate; failure preserves the active catalog and selected `Instant`.
5. **Exit and ordinary actions.** Exit from both the menu and a synthetic preview. Compare restored location, active catalog/source identity, exact selected `Instant`, and corresponding facts with the pre-session baseline. Verify exit restoration causes no provider request and no catalog/cache write. Separately exercise ordinary place selection and refresh/retry requested while debug or preview is active; distinguish their expected effects from restoration and confirm debug remains off after success or failure.
6. **Release boundary.** After debug evidence is safe, assemble `release` and inspect the release manifest/DEX and source registration for developer command dispatch, workbench UI, diagnostics, and debug-only catalog content. Exercise the locally signed release on a non-destructive isolated install that preserves the reference debug app and its data; submit `#DEBUG` and `#PICKER` through the ordinary location input and verify no developer effect or surface appears, then capture the release normal state. Do not uninstall, overwrite, or clear the reference debug app/data to force this install. If an isolated installed release cannot be provided, record the runtime checks as NOT RUN and leave release acceptance unmet. Record signing, package inspection, install target, and any restoration needed.
7. **Manual and focused verification.** Identify and inspect the user-facing manuals; record every file checked and confirm developer commands are absent. Run the owner-authorized focused DFM.4 tests that directly cover changed/affected integration paths; select exact test classes/methods after inspecting the implementation. Do not run the broad unit or system suite. Run build/contract checks required below.
8. **Evidence and closeout review.** Preserve actual commands, results, screenshots, package-inspection output, and limitations under the cycle evidence directory. Include a parent DFM.4 obligation-to-evidence map linking each original obligation to cycles 085–089 evidence or a direct cycle 090 check; leave any uncovered obligation explicitly NOT RUN/open. Review `git diff --check` and the complete relevant diff. Close DFM.4.6 as DONE only if the approved exit is fully evidenced; otherwise record the exact gap and keep the roadmap outcome open.

## Acceptance criteria

DFM.4.6 passes only when all of the following are evidenced:

1. The installed debug flow completes from baseline through command/session entry, default-off overlay/presets, catalog library, SIMULATED editing and explicit preview, immutable REPLAY capture and preview, and session exit using the approved state paths.
2. A failed or invalid saved Preview leaves the active catalog and selected `Instant` unchanged and reports the established actionable failure without fallback content.
3. Debug exit restores the exact pre-session location/catalog identity/selected `Instant` and corresponding facts, hides the overlay, and ends command dispatch. Focused state/recording evidence shows that restoration itself makes no provider request and no catalog/cache write.
4. Ordinary place selection and refresh/retry requested during debug/preview happen only after restoration. A successful place selection leaves displayed and persisted location aligned; refresh targets the restored catalog. Their expected I/O/persistence belongs to those ordinary actions. Success and failure both leave debug off, and late completion cannot overwrite the ordinary action.
5. Installed release has no developer dispatch or developer UI/catalog/diagnostics in package/source boundaries. `#DEBUG` and `#PICKER` have no developer effect on device. User-facing manuals do not describe these commands.
6. Final-build installed captures and direct interaction checks cover normal LIVE, debug menu/overlay off, overlay on, library, editor, SIMULATED preview, REPLAY preview, restored LIVE, and release normal. The baseline/restored comparison uses the exact selected `Instant`, not the status-bar capture time. Directly affected facts, labels, gesture ownership, and timeline readability are reviewed on the Pixel 3 XL reference viewport.
7. Every required check has a PASS/FAIL/NOT RUN result with exact command and limitation. The closeout maps every original DFM.4 obligation to actual cycle 085–089 evidence or a direct cycle 090 check; no unverified parent obligation is silently treated as passed. Broad system/accessibility acceptance is not claimed.

## Verification and evidence

- Run `python3 scripts/dev.py workflow` before work and at closeout; run `python3 scripts/dev.py contract` for the debug/release boundary.
- Run `python3 scripts/dev.py build` for changed app code. Run only directly affected focused tests with `./gradlew :app:testDebugUnitTest --tests '<fully-qualified focused class or method>'`; identify and list the exact tests after source inspection. The owner authorized focused DFM.4 tests, not a broad suite.
- Assemble release with `./gradlew :app:assembleRelease`. Record the installed release signature/source, package inspection method and result, and direct on-device `#DEBUG`/`#PICKER` checks.
- For required debug installed checks, use `python3 scripts/dev.py run --serial 8AKY0M32X` after confirming the serial and `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/090-dfm46-workbench-integration-release-acceptance/<state>.png`. Capture release directly from the non-destructive isolated installed target; `scripts/dev.py run` alone is not release evidence.
- Record device model/serial, Android/build variant, display and content viewport, density, orientation, locale, font/animation settings, selected location/catalog identity, selected `Instant`, and interaction method with the screenshots. A build or preview alone is not installed evidence.
- Evidence directory: `.codex/test-artifacts/090-dfm46-workbench-integration-release-acceptance/`.
- Closeout history: `.codex/history/090-dfm46-workbench-integration-release-acceptance.md`.
- Archived plan: `.codex/history/plans/090-dfm46-workbench-integration-release-acceptance.md`.

## Risks and assumptions

- Cycle 084 contains partial, unaccepted implementation. Verify code and behavior against completed cycles 085–089 and approved contracts before deciding what to reuse or fix.
- A non-destructive isolated release install may be unavailable. Preserve the installed reference debug app and its data; if no isolated target is available, record the release runtime acceptance as NOT RUN rather than replacing or clearing the debug install.
- Network/provider state may change independently of the debug session. Record actual source/load outcomes and distinguish an explicitly requested ordinary action from debug restoration.
- Device, installed release, package-inspection tools, or a required state may be unavailable. Record the concrete condition and mark the affected check NOT RUN; do not claim release or visual acceptance from build output alone.
- Existing screenshots from cycles 087–089 are design/history references. Reuse them as DFM.4.6 evidence only if they demonstrably represent the final integrated build and satisfy the exact state/interaction under review.

## Out of scope

- Draggable or resizable diagnostic overlay panel; owner direction defers this to a separate follow-up.
- New forecast layout, debug feature, weather family/meaning, data source, provider/cache/location behavior, official alerts, or background work.
- Product identity/release readiness work owned by WX.27, full release acceptance outside DFM.4, broad app/system TEST work, RTL/TalkBack/accessibility/device/environment matrices, and changes to the deprecated oxygenWX application.
- Speculative changes without a demonstrated in-scope failure. Replan any request that exceeds the approved DFM.4.6 boundaries.
