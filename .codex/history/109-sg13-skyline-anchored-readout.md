# Cycle 109-sg13-skyline-anchored-readout

Date: 2026-11-05
Roadmap: SG.13

## Outcome

Implemented SG.13 in CatalogTimeLabScreen.kt: added the selected condition text row using CatalogTimelineView.condition, an explicit unavailable fallback, and the location label font weight; aligned the complete readout to the approved Chicago skyline landmark; and offset the separate precipitation/wind row upward by exactly 23 dp. Regular, compact, and focused 1.3 font-scale installed captures and focused picker, center-swipe, timeline-selection, hourly-hold, and collapse checks are recorded in .codex/test-artifacts/109-sg13-skyline-anchored-readout/README.md. Carried-condition and unavailable-condition installed states were not available in the selected Chicago catalog and remain unverified. The Pixel display, font scale, and selected location were restored.

## Verification actually performed

PASS: python scripts/dev.py workflow (scaffold OK); PASS: python scripts/dev.py contract (transport/architecture guard OK); PASS: python scripts/dev.py build (BUILD SUCCESSFUL); PASS: python scripts/dev.py run (debug APK installed and MainActivity started); PASS: installed Pixel 3 XL regular 411x846 dp, compact 360x800 dp, and 1.3 font-scale captures inspected; PASS: focused picker and selected-time/gesture checks; PASS: git diff --check. NOT RUN: automated tests (not authorized); carried-condition and unavailable-condition installed states (not available through the focused Chicago catalog route); RTL, TalkBack, reduced-motion, and broad system matrix (out of scope for this slice). Evidence: .codex/test-artifacts/109-sg13-skyline-anchored-readout/README.md.

## Follow-up

SG.13 implementation and installed acceptance are complete. The carried-condition and unavailable-condition appearance remains unverified because neither state was available through the selected Chicago catalog route; review those states when an existing selection route exposes them. Automated tests and broad system matrices were not run, consistent with the plan's authorization and slice boundaries.
