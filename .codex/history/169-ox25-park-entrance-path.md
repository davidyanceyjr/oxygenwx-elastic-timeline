# Cycle 169-ox25-park-entrance-path

Date: 2026-11-22
Roadmap items: OX-25

## Outcome

Completed OX-25: the installed app defaults to the approved Living Urban Park composition with authored distant backdrop, curved path, and foreground entrance. Park, First City, and Open Landscape remain selectable; world switching preserved the live Chicago catalog identity, generation, selected instant, and source request counts, and the timeline remained operable. The product-owner board-label mismatch was resolved as stale metadata using the owner-selected option; approved packages and fingerprints were left unchanged. Device acceptance is limited to the scoped Pixel 3 XL portrait states recorded in the evidence README.

## Slice outcomes

- **OX-25 — DONE:** Installed portrait Park uses the authored backdrop, curved path, and foreground entrance in back-to-front order; focused checks pass, forecast facts and timeline remained readable and operational, Park, First City, and Open Landscape remained selectable, and world switching preserved the active Chicago catalog and selected instant without additional source loads. Evidence: .codex/test-artifacts/169-ox25-park-entrance-path/README.md, .codex/test-artifacts/169-ox25-park-entrance-path/focused-tests.log, .codex/test-artifacts/169-ox25-park-entrance-path/park-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/world-chooser-default-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/first-city-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/first-city-returned-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/open-landscape-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/park-timeline-after-scrub-recaptured.png, .codex/test-artifacts/169-ox25-park-entrance-path/world-probe-recaptured.log, .codex/test-artifacts/169-ox25-park-entrance-path/activity-state-after-recapture.txt, .codex/test-artifacts/169-ox25-park-entrance-path/process-id-after-recapture.txt.

## Verification actually performed

- PASS: python scripts/dev.py run --serial 8AKY0M32X — debug assemble, install, and launch succeeded.
- PASS: ./gradlew :app:testDebugUnitTest --rerun-tasks --tests com.oxygen.weather.timeline.ui.CatalogWorldChoiceTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest --tests com.oxygen.weather.timeline.presentation.scene.SharedSceneWeatherDecisionTest — 15 tests, 0 failures, 0 errors, 0 skipped.
- PASS: installed Pixel 3 XL portrait captures cover Park default, chooser, First City, Open Landscape, return to First City, timeline scrub, and chooser dismissal; world probe retained Chicago catalog identity/generation and one coordinator/source load across world switches.
- PASS: python scripts/dev.py workflow at CLOSEOUT — Workflow scaffold: OK.
- PASS: git diff --check — exit 0, no output.
- Not run by slice scope: full test suite, lint, RTL, TalkBack, font-scale, landscape/foldable, reduced-motion, performance, or broad system/device matrix.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/169-ox25-park-entrance-path/`. Archived plan: `.codex/history/plans/169-ox25-park-entrance-path.md`. See the plan for stage checkpoints and exact boundaries.
