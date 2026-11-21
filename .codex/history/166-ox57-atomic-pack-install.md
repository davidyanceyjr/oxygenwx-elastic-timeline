# Cycle 166-ox57-atomic-pack-install

Date: 2026-11-21
Roadmap items: OX-57

## Outcome

OX-57 delivered a bounded app-private scene-pack installer and debug-only local File handoff. On Pixel 3 XL/API 31, the generated baseline became an APP_OWNED/COMPATIBLE catalog entry. An injected interruption after the first resource write during same-pack v2 replacement left v1 and its committed generation current, kept v2 invisible, and preserved the non-empty OX-05 weather catalog, its 1,057 facts, fact digest, and selected instant across both imports. Added install budget/failure documentation and retained exact artifacts, logs, hashes, and package-state evidence. Cold-relaunch recovery, process-death/power-loss behavior, renderer loading, broad system acceptance, and visual acceptance remain outside this slice.

## Slice outcomes

- **OX-57 — DONE:** A generated local pack installed as an APP_OWNED/COMPATIBLE catalog entry on Pixel 3 XL; an interrupted same-pack v2 replacement left v1 and its committed generation current, kept the candidate invisible, and preserved the non-empty OX-05 weather facts and selected instant. Focused installer tests, build, and execution documentation completed. Evidence: .codex/test-artifacts/166-ox57-atomic-pack-install/README.md, .codex/test-artifacts/166-ox57-atomic-pack-install/device-baseline-install-log.txt, .codex/test-artifacts/166-ox57-atomic-pack-install/device-import-log.txt.

## Verification actually performed

- ./gradlew :app:compileDebugKotlin — passed.
- ./gradlew :app:testDebugUnitTest --tests com.oxygen.weather.timeline.data.scenepack.ScenePackInstallerTest — passed, 6 tests.
- python scripts/dev.py build — passed.
- `python scripts/build_scene_pack.py --source docs/scene-pack-examples/first-city-canopy-baseline --output .codex/test-artifacts/166-ox57-atomic-pack-install/baseline.ox30pack` — passed.
- `python scripts/build_scene_pack.py --source .codex/test-artifacts/166-ox57-atomic-pack-install/replacement-source --output .codex/test-artifacts/166-ox57-atomic-pack-install/replacement.ox30pack` — passed.
- adb -s 8AKY0M32X install -r app/build/outputs/apk/debug/app-debug.apk — passed.
- Pixel 3 XL/API 31 baseline device import — outcome INSTALLED; APP_OWNED/COMPATIBLE candidate visible; weather/time snapshots identical with 1,057 facts.
- Pixel 3 XL/API 31 interrupted same-pack replacement — outcome INTERRUPTED; v1 current, v2 invisible, prior generation only on disk, and weather/time snapshots identical.
- git diff --check — passed.
- `python scripts/dev.py workflow` — passed at EXECUTE and CLOSEOUT and after closeout in IDLE.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/166-ox57-atomic-pack-install/`. Archived plan: `.codex/history/plans/166-ox57-atomic-pack-install.md`. See the plan for stage checkpoints and exact boundaries.
