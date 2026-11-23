# Cycle 171-park-mural-foundation

Date: 2026-11-22
Roadmap items: OX-37, OX-42, OX-85

## Outcome

Cycle 171 completed OX-37, OX-42, and OX-85 as separate DONE outcomes. The Park now declares one optional mural data surface with a Park-local opaque selected-value reference, renders the wall and blank reserved panel, and uses measured Pixel 3 XL portrait bounds to keep the wall clear of central facts and timeline access. The final installed capture shows a recognizable Park, blank panel, central facts, and timeline. OX-12 retains later selected-reading semantics.

## Slice outcomes

- **OX-37 — DONE:** Declared a Park-owned optional mural data surface with normalized plane/anchor/reserved-region geometry and one opaque canonical reference; existing decorative elements remain surface-free and no forecast reading or renderer behavior was added. Evidence: .codex/test-artifacts/171-park-mural-foundation/README.md.
- **OX-42 — DONE:** Added the Park mural wall in the approved depth order, attached the OX-37 optional surface declaration, rendered its blank reserved panel, updated the existing focused composition inventory test, passed only that authorized test, and captured installed Pixel 3 XL portrait evidence showing the wall/panel with central facts and timeline visible. Evidence: .codex/test-artifacts/171-park-mural-foundation/README.md, .codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png.
- **OX-85 — DONE:** Measured the installed Pixel 3 XL portrait viewport and system insets, recorded the full-canvas normalized Park crop and relative mural/readout/timeline bounds, moved and narrowed only the Park mural plane/reserved region to clear the precipitation pictogram and text, and captured installed evidence of a recognizable Park with the blank panel and central content unobstructed. Evidence: .codex/test-artifacts/171-park-mural-foundation/README.md, .codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png, .codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-hierarchy.xml.

## Verification actually performed

- PASS: PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py workflow at CLOSEOUT; Workflow scaffold: OK.
- PASS: git diff --check; exit 0 with no output.
- PASS during OX-42: ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest; BUILD SUCCESSFUL, 28 actionable tasks, 0 failures. Not rerun during OX-85.
- PASS after final OX-85 geometry change: PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py run --serial 8AKY0M32X; debug build assembled, installed, and launched.
- PASS: installed Pixel 3 XL captures saved and visually reviewed at .codex/test-artifacts/171-park-mural-foundation/ox-42-park-wall-installed.png and .codex/test-artifacts/171-park-mural-foundation/ox-85-pixel-3-xl-portrait.png; final capture retains UI hierarchy evidence.
- PASS: Pixel 3 XL portrait measurements recorded from adb wm size, wm density, font_scale, dumpsys window displays, dumpsys activity top, and uiautomator hierarchy; screenshot 1440x2960 at 560 dpi, app viewport 1440x2733 px.
- PASS: SHA-256 values for the approved behavior package and contextual UI artwork match cycle 171 plan fingerprints; approved board files remain unchanged.
- NOT RUN: full unit suite, lint, OX-85 tests, RTL, TalkBack, gesture sweep, and broad system/device acceptance.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/171-park-mural-foundation/`. Archived plan: `.codex/history/plans/171-park-mural-foundation.md`. See the plan for stage checkpoints and exact boundaries.
