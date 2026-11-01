# Cycle 081-sg4-precipitation-event-tracks

Date: 2026-11-01
Roadmap: SG.4

## Outcome

Cycle 081 closes SG.4 DONE. The approved first-city rain/snow mapping and composition, point and interval support, explicit zero, missing-hour carry and leading-gap behavior, midpoint ownership, readable typed facts, and provider-backed solar event timing were installed and reviewed on Pixel 3 XL. Debug-only SG.4 replay v8 and LIVE solar controls supplied bounded inspection states; no release fixture or production catalog schema changes were made.

## Verification actually performed

PASS: python scripts/dev.py workflow before close (scaffold OK; cycle ACTIVE). PASS: python scripts/dev.py build (final debug APK assembled). PASS: python scripts/dev.py run --serial 8AKY0M32X (installed and launched). PASS: python scripts/dev.py screenshot --serial 8AKY0M32X for the captures indexed in .codex/test-artifacts/081-sg4-precipitation-event-tracks/README.md. PASS: manually inspected installed conflict, exact-time, precipitation, reduced-motion, and provider-backed solar states on Pixel 3 XL (1440x2960, portrait, font scale 1.0); live source sunrise 06:46:16 CDT and sunset 18:33:07 CDT on 2026-10-02. PASS: git diff --check. Automated tests, lint, contract suite, RTL, TalkBack, and broad system matrix were not run per owner direction and slice scope.

## Follow-up

No additional SG.4 gap was found in the scoped manual checks. Automated tests and cross-cutting accessibility/system acceptance remain unclaimed per owner direction and belong to dedicated TEST slices. SG.9 is next; WX.30 and WX.31 remain separate roadmap work.
