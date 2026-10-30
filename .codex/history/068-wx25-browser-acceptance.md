# Cycle 068-wx25-browser-acceptance

Date: 2026-10-29
Roadmap: WX.25

## Outcome

Partial outcome; no app code changed. The Pixel 3 XL catalog route passed the TalkBack daily/hour browser path, scrolling, checked-row selection, Done return, and selected/fact-time agreement. Default and 1.3x portrait browser readability and sampled daily/hourly tap, hold, and drag behavior are documented in .codex/test-artifacts/068-wx25-browser-acceptance/README.md. WX.25 remains NEXT because rain/snow/storm and DST/sparse-date cases were not exercised, although the existing SIMULATED/debug catalog path provides suitable data. See the post-close correction in the evidence README.

## Verification actually performed

PASS: python scripts/dev.py workflow; python scripts/dev.py contract; git diff --check; installed Pixel 3 XL captures and hierarchies; owner-observed TalkBack selected-time announcement; sampled daily/hourly gestures; cited cycle 066/067 horizon, source, and missing/zero evidence. NOT RUN: automated tests per active plan; build/install because no fresh APK was needed; catalog rain/snow/storm; DST and 23/25-hour local dates; compact/landscape, RTL, reduced motion; frame-time metrics. Device font scale 1.0 and TalkBack disabled were restored. Full evidence: .codex/test-artifacts/068-wx25-browser-acceptance/README.md.

## Follow-up

WX.25 remains `NEXT`. Complete the catalog-backed rain/snow/storm truth
matrix and DST/sparse-coverage review by activating the existing SIMULATED/debug
catalog path. Record the exact dataset, selected `Instant`, fact `validTime`,
provenance, and missing-versus-zero values for each added state. Do not mark
WX.25 DONE until its full acceptance matrix is evidenced. Compact/landscape,
RTL, reduced-motion, and broad TalkBack system acceptance remain with WX.29.
The cycle 068 evidence README records the post-close correction: these cases
were available but not exercised.
