# DFM.4.6 — Workbench integration and release acceptance
## Objective-specific behavior board — review draft

**Status:** Product-owner approved 2026-11-02, as shown. This board is design input, not implementation authorization or acceptance evidence.
**Purpose:** Make the combined DFM.4 debug-workbench lifecycle and release boundary reviewable after DFM.4.1–4.5, including exact restoration of the user's prior forecast state.
**UI artwork:** [Integrated workbench UI artwork board](DFM.4.6-integrated-workbench-ui-artwork-board.svg) · [PNG preview](DFM.4.6-integrated-workbench-ui-artwork-board.png)
**Parent references:** The owner-approved DFM.4 session-flow, normal/debug-overlay, catalog-picker, DFM.4.3 clean-view, DFM.4.4 storage, and DFM.4.5 library/editor boards remain the product contract. This board narrows them to one integrated debug-build journey and one release-build boundary review.

## Integrated debug-build flow

```text
NORMAL LIVE FORECAST
  Record location, source/catalog identity, selected Instant, and visible facts.
  Overlay is off; no debug controls appear in the clean forecast.
       │ submit #DEBUG in a debuggable build
       ▼
DEBUG SESSION ACTIVE · MENU · OVERLAY OFF
  The menu is available only for this debug session.
  Configure/select a local display preset without changing forecast state.
  Toggle diagnostics independently; diagnostic labels never become facts.
       │ submit #PICKER while this session is active
       ▼
CATALOG LIBRARY
  SIMULATED records: edit, save, reopen, explicit Preview, capture REPLAY.
  REPLAY records: reopen and explicit Preview only; remain immutable.
  Save/capture alone never activates a catalog.
       │ explicit Preview on a saved, revalidated record
       ▼
SYNTHETIC FORECAST PREVIEW
  Existing forecast presentation and selected-time path are reused.
  DEMO DATA and SIMULATED/REPLAY origin remain clear and truthful.
  Unsupported inputs remain unavailable; preview does not write LIVE data.
       │ exit debug, from menu or preview
       ▼
RESTORED NORMAL LIVE FORECAST
  Restore the exact pre-session location, catalog identity, selected Instant,
  and corresponding facts; turn diagnostics off and end the session.
  Restoration itself performs no provider request or catalog/cache write.
```

## Alternate and ordinary-action paths

| Starting state and action | Required integrated outcome |
|---|---|
| Active session, choose/configure a display preset | Only local diagnostic group preferences change. Forecast catalog, location, and selected Instant remain unchanged; overlay visibility stays independently controlled. |
| Active session, open or use `#PICKER` | Dispatch to the catalog library only while the debug session is active. Outside a session, keep ordinary search behavior. |
| Edit, save, reopen, or capture a local catalog | SIMULATED remains editable; REPLAY remains an immutable snapshot. Save/capture never activates. Missing, zero, probability, amount, and unsupported values retain their established meanings. |
| Preview a missing, invalid, changed, or mismatched saved record | Show the established actionable failure in the catalog flow. Preserve the active catalog and selected Instant; do not invent fallback data or report success. |
| While debug/preview is active, request ordinary place selection or refresh/retry | Restore and end debug first, then let the requested existing ordinary action proceed. If that action fails, debug remains off. Distinguish its expected persistence/network effects from restoration. |
| Late preview/exit completion follows an ordinary action | It must not restore the old checkpoint over the newer ordinary action. |
| Exit debug after preview | Restore the exact original LIVE location/catalog/selected Instant without provider I/O or LIVE/cache writes; hide overlay and disable command dispatch. |

## Release-build boundary

```text
RELEASE NORMAL FORECAST
  #DEBUG submission  → ordinary search behavior; no debug session or menu
  #PICKER submission → ordinary search behavior; no catalog dispatch
  Main screen        → approved clean forecast; no developer diagnostics,
                       catalog editor, scenario data, or debug controls
```

Verify the installed release behavior directly and inspect the assembled release manifest/DEX/source registration for the absence of developer command dispatch and debug-only workbench/catalog/diagnostic surfaces. Manually inspect user-facing manuals: developer commands remain undocumented there. This is DFM.4-focused release acceptance, not a broad device, accessibility, or environment matrix.

## Acceptance evidence map

1. **One integrated debug journey:** installed Pixel 3 XL debug build covers baseline → `#DEBUG` → default-off menu → overlay/preset → `#PICKER` → SIMULATED edit/save/reopen/preview → REPLAY capture/reopen/preview → exit.
2. **State and isolation comparison:** record prior and restored location, active catalog/source identity, selected Instant, visible facts, overlay/session status, and catalog/cache state. Prove that exit restoration makes no provider request or catalog/cache write; distinguish requests caused by an explicitly chosen ordinary action.
3. **Failure and ordinary routes:** cover failed saved preview and ordinary place selection, refresh, and retry from an active debug/preview state. Confirm restoration ordering and that ordinary-action failure leaves debug off.
4. **Release route:** assemble and install release; attempt both commands; inspect package/source boundaries and manually named user-facing manuals.
5. **Installed visuals:** capture the final integrated debug LIVE, menu, overlay off/on, library, editor, SIMULATED preview, REPLAY preview, restored LIVE, and release normal states. Compare the baseline and restored LIVE captures using the exact selected Instant, not capture-time clock text.
6. **Scoped verification:** record only directly affected focused tests, release checks, device/build/input context, exact commands, pass/fail/not-run outcomes, `git diff --check`, and any limitation. Do not claim broad app/system acceptance.

## Scope boundary

The existing DFM.4 artwork defines the screens; this integration board proposes no new forecast layout, weather meaning, command, catalog editor, source, or navigation. Preserve the clean atmosphere/readout/tape, source truth, selected-time authority, and gesture ownership. Only fix a demonstrated defect inside DFM.4; otherwise record the gap and replan. Broad RTL, TalkBack, accessibility, device, and environment matrices remain outside this slice. **Owner scope decision, 2026-11-02:** DFM.4.6 remains integration acceptance only. Making the diagnostic panel draggable or resizable is explicitly deferred from this slice to a separately scoped follow-up.

## Approval record

**Product-owner approval:** Product owner approved this behavior board and its linked UI artwork board on 2026-11-02, as shown. Scope constraint: DFM.4.6 remains integrated acceptance only; the draggable/resizable diagnostic panel is deferred. Parent-board approvals remain reference inputs and do not replace this objective-specific approval.
