# Cycle 007-visual-baseline

Date: 2026-10-24
Roadmap: P.2

## Outcome

Captured five installed Pixel 3 XL baseline states: default, selected Sunday, hourly scale during hold, daily scale after release, and next-day selection after a main-display swipe. Evidence and replay instructions are stored under .codex/test-artifacts/007-visual-baseline/. No app source changed.

## Verification actually performed

python scripts/dev.py workflow passed; python scripts/dev.py contract passed; debug build/install/launch succeeded using Java 27 and the host Android SDK; foreground activity confirmed; five device PNGs captured and representative images inspected; git diff --check passed. Large-font, landscape, RTL, reduced-motion, TalkBack, and full gesture acceptance were not performed.

## Follow-up

State remaining gaps before declaring full acceptance.
