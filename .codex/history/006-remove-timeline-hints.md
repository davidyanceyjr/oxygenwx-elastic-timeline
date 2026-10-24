# Cycle 006-remove-timeline-hints

Date: 2026-10-23
Roadmap: P.2

## Outcome

Removed the visible DAILY NAVIGATION / HOURS and HOURLY PRECISION / DAYS mode row, plus the SWIPE DAYS / HOLD TAPE FOR HOURS / NOW footer. Kept direct timeline gestures and double-tap realignment, and added a TalkBack custom action for switching timeline scale. Updated the specification and roadmap.

## Verification actually performed

python3 scripts/dev.py workflow passed; python3 scripts/dev.py contract passed; Android debug build succeeded; updated app installed on Pixel 3 XL serial 8AKY0M32X; screenshot captured and inspected at .codex/test-artifacts/006-remove-timeline-hints/pixel-3xl.png; git diff --check passed. Unit tests, lint, and manual TalkBack acceptance were not performed.

## Follow-up

State remaining gaps before declaring full acceptance.
