# Cycle 125 — MR.3 installed evidence

## Device and run

- Device: Pixel 3 XL, serial `8AKY0M32X`, Android 12 / API 31.
- Portrait profile: 1440 × 2960 screenshot, 560 dpi, font scale 1.0.
- App build/install: `python scripts/dev.py run --serial 8AKY0M32X` — succeeded.
- Workflow validation: `python scripts/dev.py workflow` — passed.
- Diff check: `git diff --check` — passed.
- Automated tests were not added or run, as directed by the plan.

## Observed states

- `search-keyboard-open-typing.png`: Boston remained readable in the search field above the open IME.
- `search-enter-results.png`: submitting Boston with IME Enter retained the query, hid the IME, and showed 10 results in the single result rail.
- `search-onscreen-results.png`: the on-screen Search action for Chicago left the results visible with the IME hidden.
- `search-no-match.png`: submitting `zzzxqvzzqv` left the no-match status visible, the query retained, and the IME hidden. The Search action owned focus after submission; the field was no longer focused.
- `saved-location-live-loading.png`: saved New York View left the selector open and showed its loading status.
- `saved-location-live-success.png`: after activation completed, the selector closed and the main view showed New York weather.
- `saved-location-selection-persisted.png` and `saved-location-selection-after-transient-view.png`: New York remained the selected saved place after viewing Boston temporarily; the Boston view did not replace the saved selection.
- The selected time remained `8:13:09 PM EST` when the temporary Boston view opened. `timeline-before-interaction.png` and `timeline-after-interaction.png` show the same Boston view before and after a timeline drag; the accessible selected time changed from `8:13:09 PM EST` to `12:00:00 PM EST`, confirming the timeline still updates the selection.
- `search-loading-fast-capture.png`: an immediate installed capture after submitting Vancouver showed “Searching for Vancouver…” in the status rail; the request later returned 10 results. This capture used `adb exec-out screencap -p` to preserve the brief loading state. Other evidence captures used `python scripts/dev.py screenshot`.
- `search-provider-failure.png`: with Android airplane mode enabled and no IP route, Vancouver search showed “Could not connect. Check your connection and retry.” with a visible Retry action. The selector stayed open in the approved one-rail layout.
- `saved-location-live-failure.png`: with network unavailable, a newly saved Vancouver place with no stored LIVE catalog was selected through the saved-place View action. The selector remained open and showed “No valid LIVE catalog is available (transport_failure)” with Retry forecast. `saved-vancouver-selector-error-before-view.png` records the prior failure that established the no-cache location; `saved-vancouver-save-offline-outcome.png` records the initial main-view error. The final failure screenshot is after tapping View on that saved Vancouver place.
- `saved-location-view-offline-outcome.png`: an offline View of the already-cached Puerto Rico saved place completed and showed its forecast, so it did not count as failure coverage. A previously uncached Vancouver place was used for the required failure path instead.
- Connectivity was restored after the failure captures with airplane mode disabled and Wi-Fi enabled; Android reported a validated Wi-Fi network and an active route. Retrying Vancouver succeeded. New York was selected again, the temporary Vancouver place was removed through the UI, and the saved rail returned to Florence, New York, and Puerto Rico. `saved-location-state-restored.png` records that cleanup state.

## Run limits

The product owner authorized a focused device-level network-unavailable run using Android airplane-mode and Wi-Fi controls only. No application fault-injection code was added or used. The initial Seattle screenshot (`search-loading-attempt.png`) captured results rather than loading; the later immediate Vancouver capture above shows the loading status. The installed device was returned to Wi-Fi before closeout. No automated tests were added or run.

## Additional retained captures

Other captures in this directory include selector context, temporary-result activation, intermediate IME debugging states, and the saved-location selection state. Files whose names include `intermediate`, `restored-before-fix`, or `focus-transfer` are diagnostic history; the canonical final IME evidence is `search-enter-results.png`, `search-onscreen-results.png`, and `search-no-match.png`.
