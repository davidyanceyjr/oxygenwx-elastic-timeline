# Cycle 029-atm3-evidence-retry

Date: 2026-10-25
Roadmap: ATM.3

## Outcome

Closed incomplete under the stop-on-disconnect gate. Installed review captured the Pixel 3 XL daily/hourly states, selected-day behavior, one-hour held scrub, swipe, double-tap, clear adjacent samples, a continuous-looking cloudy midnight boundary, large-font preset, landscape, RTL pseudo-locale, and reduced-motion samples. ATM.3 remains unaccepted: app content stays LTR under verified RTL; exact selected-hour continuity after release is not exposed; 1.3 font scale was unavailable (largest preset 1.15); and cloud-phase change under reduced motion with positive cloud cover was not captured. ADB disappeared during the follow-up and the Pixel remained MTP-only. No application source edits or cloud tuning were made in cycle 029.

## Verification actually performed

Passed: python scripts/dev.py workflow; python scripts/dev.py contract; python scripts/dev.py build; python scripts/dev.py lint; python scripts/dev.py run --serial 8AKY0M32X (install/launch). Tests not run; not authorized. Device restoration was verified before the final optional follow-up: en-US, font scale 1.0, portrait, animation scales 1.0, and serial authorized. At the disconnect, adb devices -l listed no device and lsusb showed Google Pixel MTP (18d1:4ee1). git diff --check passed before closeout; repeated after writing records.

## Follow-up

Re-enter ATM.3 only after the Pixel is again present as authorized ADB serial
`8AKY0M32X`. The next bounded plan must explicitly resolve or disposition the
verified RTL rendering mismatch, selected-hour observability after release,
the device's 1.15 maximum font preset versus the requested 1.3 state, and the
missing reduced-motion cloud-phase comparison at positive cloud cover. Keep
ATM.3 BLOCKED until its exit is fully evidenced. No cloud-only rendering defect
was demonstrated in cycle 029.
