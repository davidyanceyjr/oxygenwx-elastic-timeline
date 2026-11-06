# Cycle 115 — SG missing eleventh-hour data

Date: 2026-11-06
Roadmap: `SG-missing-eleventh-hour-data`

## Outcome

Implemented the approved same-date final hourly display boundary through exclusive local midnight, field-independent temperature and wind projection, source-time attribution, and the same-date bracket guard for SG.4 carries. During final installed review, the product owner requested the center readout return to its prior hierarchy. The grouped attribution row is now absent from the visible center; source and selected times remain in timeline semantics and the forecast browser. The late-hour source point still supplies its supported temperature, condition, chance, and wind through the local-date boundary. The next local date owns midnight.

Final visual evidence is in `.codex/test-artifacts/115-sg-missing-eleventh-hour-data/readout-restored-sunday-1136.png` and `readout-restored-midnight.png`, with matching hierarchy captures. At 11:36:19 PM CST, the visible hierarchy is temperature/condition, solar facts, lunar facts, and Sunday high/low; the timeline semantics retain the 11:00 PM source and 11:36 PM selected times. At 12:01:26 AM CST, Monday's own point and daily high/low are shown with the same hierarchy.

The latest LIVE catalog contains no genuinely absent final point or individually missing condition, probability, temperature, or wind at a qualifying final-hour slot. Those states remain NOT RUN; no values were synthesized. The available-state and source-value comparisons are preserved in `.codex/test-artifacts/115-sg-missing-eleventh-hour-data/README.md` and `current-live-source-points.json`.

## Verification actually performed

- PASS: `python scripts/dev.py workflow` — scaffold OK; cycle 115 recognized while active. After closeout, workflow reports IDLE.
- PASS: `python scripts/dev.py contract` — transport contract and architecture guard OK.
- PASS: `python scripts/dev.py build` — `BUILD SUCCESSFUL` (37 tasks; 5 executed, 32 up-to-date).
- PASS: `python scripts/dev.py run --serial 8AKY0M32X` — debug APK installed and launched.
- PASS: installed Pixel 3 XL portrait captures at 1440×2960 physical pixels, density 560, font scale 1.0; UI viewport 1440×2562 from y=171 to y=2733. Captured corrected 11:36 PM and midnight states after the owner-requested readout restoration.
- PASS: focused date swipes, timeline double-tap into hourly scale, and hourly scrubs reached the late-hour state and next local date. The exact selected time remained in timeline semantics; the final point's source time was retained there.
- PASS: `git diff --check` — clean.
- NOT RUN: automated tests; the plan requires separate owner authorization.
- NOT RUN: genuinely absent final point and independently missing field states; no qualifying rows exist in the current LIVE catalog.
- OUT OF SCOPE: RTL, TalkBack, large-font, reduced-motion, and broad system acceptance.

## Follow-up

Revisit the missing final-point and independently missing-field cases if a later source snapshot exposes them. Automated tests and cross-cutting system acceptance remain outside this cycle.

## Owner-requested restoration of existing daily high row (same task)

After closeout, the owner clarified that the Daily-mode `HI` row was existing work and should remain below each date's icon. History inspection found its approved implementation in commit `6330b03` on the `timeline` branch; the active `scene_generator` branch did not include that commit. The existing `CatalogDayView.high` projection and Daily-only row were restored using the previously approved P.2.21 scope. No new cycle was opened.

- PASS: `python scripts/dev.py workflow` — workflow scaffold OK; cycle pointer remained IDLE.
- PASS: `python scripts/dev.py build` — `BUILD SUCCESSFUL` (37 tasks; 5 executed, 32 up-to-date).
- PASS: `python scripts/dev.py run --serial 8AKY0M32X` — updated debug APK installed and launched.
- PASS: installed Pixel 3 XL Daily capture `.codex/test-artifacts/115-sg-missing-eleventh-hour-data/hi-temp-restored-daily.png` shows the visible dates' projected highs below their icons and the divider; selected Friday's 65° high is highlighted.
- PASS: `git diff --check` after restoration.
- NOT RUN: automated tests. No new hourly-state screenshot or additional gesture checks were performed for this correction.
