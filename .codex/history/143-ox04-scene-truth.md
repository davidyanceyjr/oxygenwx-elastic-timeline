# Cycle 143-ox04-scene-truth

Date: 2026-11-16
Roadmap items: OX-04

## Outcome

Cycle 143 completed OX-04 with fixture-backed evidence for supplied rain, eligible carried rain, an unavailable condition with independent values, and the ineligible local-date leading gap. Both active catalog routes were captured and compared. The owner selected unavailable selected-hour temperature; installed captures already show the selected readout as unavailable, while 57° is the separately sourced daily high, so no production change was needed. The cloud visual subcheck remains not run because the diagnostic overlay obscures it; no app tests or lint were run.

## Slice outcomes

- **OX-04 — DONE:** Completed the four-state fixture-backed evidence matrix and visible unknown-condition check on both active catalog routes. Owner selected unavailable selected-hour temperature; captures confirm the primary readout is already unavailable, and the visible 57° is the separately supported daily high. No production correction was needed. The cloud visual subcheck remains NOT RUN because the replay diagnostic overlay obscures the relevant region; app tests and lint were not authorized and were not run. Evidence: .codex/test-artifacts/143-ox04-scene-truth/README.md, .codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.png, .codex/test-artifacts/143-ox04-scene-truth/supplied-rain-composition.xml, .codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.png, .codex/test-artifacts/143-ox04-scene-truth/supplied-rain-reading.xml, .codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.png, .codex/test-artifacts/143-ox04-scene-truth/carried-rain-composition.xml, .codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.png, .codex/test-artifacts/143-ox04-scene-truth/carried-rain-reading.xml, .codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.png, .codex/test-artifacts/143-ox04-scene-truth/unavailable-composition.xml, .codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.png, .codex/test-artifacts/143-ox04-scene-truth/unavailable-reading.xml, .codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.png, .codex/test-artifacts/143-ox04-scene-truth/leading-gap-composition.xml, .codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.png, .codex/test-artifacts/143-ox04-scene-truth/leading-gap-reading.xml.

## Verification actually performed

- python scripts/dev.py contract — PASS.
- python3 scripts/dev.py run --serial 8AKY0M32X — BUILD SUCCESSFUL; debug APK installed and MainActivity launched.
- Installed evidence — four fixture-backed states on primary composition and reading fallback routes, captured on Pixel 3 XL 8AKY0M32X at 1440x2960 and 560 dpi; eight screenshots and matching hierarchies inspected. Cloud visual subcheck: NOT RUN because the replay diagnostic overlay obscured the cloud band.
- python scripts/dev.py workflow — PASS in CLOSEOUT before archive.
- git diff --check — PASS before archive.
- App tests and lint — NOT RUN; not authorized by the plan.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/143-ox04-scene-truth/`. Archived plan: `.codex/history/plans/143-ox04-scene-truth.md`. See the plan for stage checkpoints and exact boundaries.
