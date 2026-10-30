# Cycle 070-wx10r-simulated-hourly-windows

Date: 2026-10-29
Roadmap: WX.10R

## Outcome

Completed WX.10R: the deterministic SIMULATED catalog now stores one fact at every real local top-of-hour instant across 44 local dates, including DST-aware 23/24/25-hour date lengths. All existing conditions, phenomena, missingness, explicit zeroes, 9 AM/9 PM anchor values, and synthetic provenance remain. Debug SIMULATED and fixed REPLAY fixture identities were versioned to v2; installed inventories show 1,057 unique hourly facts in both v2 catalogs, all 88 old anchors preserved, and valid accumulation interval support. WX.25 installed timeline/browser acceptance remains the next slice.

## Verification actually performed

PASS: python scripts/dev.py workflow. PASS: python scripts/dev.py contract. PASS: python scripts/dev.py run --serial 8AKY0M32X (debug build, install, launch). PASS: read-only installed database inventory for SIMULATED and REPLAY v2, including 44 dates, all event families, exact top-of-hour grid, 24/25 daily counts, 88/88 anchor parity, and interval support validation. PASS: standalone Chicago 23/24/25-hour instant-grid audit. PASS: git diff --check and git diff --cached --check. NOT RUN: automated tests per repository instruction; spring-forward not present in the installed 44-day window, so its gap was audited from the approved Instant-stepping rule and timezone grid, not observed in installed catalog. WX.25 timeline/browser/TalkBack acceptance remains outstanding.

## Follow-up

State remaining gaps before declaring full acceptance.
