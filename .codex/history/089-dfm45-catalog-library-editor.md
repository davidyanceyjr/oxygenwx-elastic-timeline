# Cycle 089-dfm45-catalog-library-editor

Date: 2026-11-02
Roadmap: DFM.4.5
Status: DONE

## Outcome

Implemented the debug-only authored catalog library and period-first editor, including ordered local-time event ranges, full-value replacement on covered canonical facts, retained baseline facts, legacy sidecar reopening, immutable SIMULATED revisions, and immutable REPLAY capture. Saved-record Preview revalidates through the existing coordinator path.

The product owner directed three execution clarifications on 2026-11-02: successful Preview opens the forecast; SIMULATED and captured-SIMULATED REPLAY forecasts display a debug-only `DEMO DATA` source indicator; and a failed saved Preview returns to the authored Catalog library with one inline actionable error while keeping the active forecast and selected instant. All three were implemented within the approved board states. The generic picker no longer repeats stale preview failure messages after the inline notice is dismissed.

## Verification actually performed

- `python3 scripts/dev.py workflow` — passed before implementation and at closeout. After cycle close, the workflow reported `State: IDLE`.
- `python3 scripts/dev.py build` — passed for the final app code.
- `./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.debug.DebugAuthoredCatalogTest --tests com.oxygen.weather.timeline.data.catalog.CatalogDebugLifecycleTest` — final run passed 20 tests (7 authored-catalog, 13 lifecycle), with zero failures, errors, or skipped tests. It covers 44-date canonical coverage, spring gap and repeated-hour behavior, adjacent half-open ranges, exclusive-end baseline retention, overlap and boundary rejection, support derivation, unversioned SIMULATED and REPLAY sidecar reopening, immutable replay, metadata tampering/rollback, and Preview lifecycle behavior. An earlier run after adding the range-boundary test failed at test compilation because the fallback branch inferred `Any`; the test was corrected to throw `IllegalStateException`, and the final run passed.
- `python3 scripts/dev.py run --serial 8AKY0M32X` — the final debug APK installed and launched on the reference device. The final app-source build was installed before the subsequent test-only assertion change; no app code changed after installation.
- `git diff --check` — passed after implementation and evidence updates.

### Installed Pixel review

Pixel 3 XL, serial `8AKY0M32X`, portrait, 1440×2960, 560 dpi, font scale 1.0. `DISPLAY=:0` was available. Screenshots were captured with `python3 scripts/dev.py screenshot --serial 8AKY0M32X --output <artifact-path>` and reviewed at the device viewport.

- Creation, multi-event editing, save, reopen/edit, cancel-discard, immutable snapshot, and library display are evidenced by `library-after-final-install.png`, `editor-default-final.png`, `editor-controls-final.png`, `editor-multiple-events-final.png`, `after-multi-event-save.png`, `reopened-multi-event-final.png`, `cancel-discard-final.png`, and `replay-captured.png`.
- Successful SIMULATED and REPLAY previews opened the forecast and displayed `DEMO DATA` with the source. Evidence: `simulated-preview-demo-indicator.png` and `replay-preview-final-relay-fix.png`.
- A missing saved SIMULATED identity was injected with `debug_catalog_saved_preview=true`. `fail-closed-library-final.png` shows one inline error and available saved items. After dismissing it, `picker-after-inline-error-dismissed.png` shows no duplicated stale errors. `forecast-preserved-after-failed-preview-final.png` confirms active REPLAY facts and selected `12:40:48 PM CST` remained unchanged.
- `debug-exit-restored-after-relay-fix.png` confirms the debug session restored the pre-session LIVE forecast, removed the demo badge, and retained the same selected instant.
- `ordinary-place-selector-final.png` and `ordinary-place-close-preserved.png` confirm the ordinary Place selector opens outside a debug session and closes without changing the forecast or selected location.

## Limitations and follow-up

Verification stayed within the approved design slice. The broad unit suite, release build, compact/large-font review, RTL, TalkBack, and broad system/environment matrix were not run; cross-cutting/release acceptance remains with DFM.4.6. Reduced-motion review is not applicable because this slice adds no motion-dependent navigation. Ordinary location selection was not committed; only its open/close path was checked.

No DFM.4.5 acceptance gap remains. DFM.4.6 — Workbench integration and release acceptance — is NEXT and owns integrated debug/release acceptance and the remaining parent DFM.4 obligations.
