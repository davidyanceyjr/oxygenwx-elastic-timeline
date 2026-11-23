# Cycle 172-park-landmarks

Date: 2026-11-22
Roadmap items: OX-43, OX-44

## Outcome

Completed cycle 172 with OX-43 and OX-44 recorded DONE. The focused composition test passed and the debug app was installed on the Pixel 3 XL; portrait captures and scoped timeline scrub evidence were saved for both slices. OX-44 retains the exact approved shelter frame and board fingerprints; the owner accepted the verified shelter-before-path/entrance render order under the board's conditional where-they-overlap rule. The installed screenshot has a small gap above the path and does not show direct overlap. Existing cycle 171 mural-foundation work was preserved.

## Slice outcomes

- **OX-43 — DONE:** DONE: Added two stable Park-local tree landmarks behind the path and two bench landmarks with the entrance in the foreground. The focused composition test passed, the debug build was installed, and Pixel 3 XL portrait evidence shows the four landmarks while preserving the blank mural region, central forecast facts, and timeline. A short scrub changed the selection from 12:00 PM to 11:20:12 AM CDT and the condition from clear to partly cloudy without obscuring the readout. Evidence: .codex/test-artifacts/172-park-landmarks/README.md, .codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png, .codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png.
- **OX-44 — DONE:** Shelter identity, role, approved normalized frame, and behind-path/foreground layer order passed the focused composition test. Pixel 3 XL portrait capture shows stable placement with the mural reservation, central facts, and timeline visible; the scoped scrub moved selection from 12:00 PM to 11:21:15 AM CDT while preserving 48° and updating clear to partly cloudy. The approved frame leaves a small visible gap above the installed path, so the capture does not show direct overlap; the verified render order satisfies the board rule for where elements overlap. Evidence: .codex/test-artifacts/172-park-landmarks/README.md, .codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png, .codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png.

## Verification actually performed

- PASS — ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest (OX-43 run; BUILD SUCCESSFUL).
- PASS — ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.presentation.scene.LivingUrbanParkCompositionTest (OX-44 run; BUILD SUCCESSFUL in 55s, 28 actionable tasks, 7 executed, 21 up-to-date).
- PASS after reconnection — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py run --serial 8AKY0M32X (debug APK assembled, installed successfully, and MainActivity launched). The earlier attempt failed while the device was disconnected.
- PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-43-vegetation-benches-portrait.png; OX-43 portrait evidence saved and inspected.
- PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-43-timeline-scrub-result.png; scrub capture saved.
- PASS — adb shell input swipe 700 2580 735 2580 500; selection changed from 12:00 PM to 11:20:12 AM CDT, condition changed from clear to partly cloudy, and 48° remained.
- PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-44-streetcar-shelter-portrait.png; installed shelter capture saved and inspected.
- PASS — adb -s 8AKY0M32X shell input swipe 700 2580 735 2580 500; selection changed from 12:00 PM to 11:21:15 AM CDT, condition changed from clear to partly cloudy, and 48° remained.
- PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/172-park-landmarks/ox-44-timeline-scrub-result.png; scrub capture saved.
- PASS — git diff --check.
- PASS — PYTHONDONTWRITEBYTECODE=1 python scripts/dev.py workflow (Workflow scaffold: OK).
- Limit — no full test suite, RTL/TalkBack, or broad device/environment matrix was run; direct shelter/path pixel overlap is not visible in the approved installed frame and is documented in the OX-44 evidence.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/172-park-landmarks/`. Archived plan: `.codex/history/plans/172-park-landmarks.md`. See the plan for stage checkpoints and exact boundaries.
