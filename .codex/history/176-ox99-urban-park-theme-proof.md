# Cycle 176-ox99-urban-park-theme-proof

Date: 2026-11-24
Roadmap items: OX-99

## Outcome

Completed OX-99 by adding Urban Park as an additional selectable bundled theme with app-managed identity and revision, rendering the three supplied transparent planes in one bottom-anchored viewport with First City weather and selected-time celestial decisions. Settings reports distinct active and available states; the Living Urban Park bundled-free default and First City baseline proof remain available. Pixel 3 XL installed review met the slice exit, including direct theme-switch and timeline-scrub continuity. Six screenshots and scoped interaction notes are preserved under .codex/test-artifacts/176-ox99-urban-park-theme-proof/. No automated tests were authorized or run; system-wide accessibility and environment acceptance are not claimed.

## Slice outcomes

- **OX-99 — DONE:** Implemented Urban Park as a distinct always-available bundled theme with a stable app-managed revision and no pack-loader path. Installed Pixel 3 XL review met the planned theme-list, aligned three-plane scene, supported Sun/Moon, timeline-scrub, direct theme-switch, and First City baseline-proof exits. No tests were added or run; scoped device/system limits are recorded with the evidence. Evidence: .codex/test-artifacts/176-ox99-urban-park-theme-proof/README.md, .codex/test-artifacts/176-ox99-urban-park-theme-proof/01-settings-living-urban-park-active.png, .codex/test-artifacts/176-ox99-urban-park-theme-proof/02-settings-urban-park-active.png, .codex/test-artifacts/176-ox99-urban-park-theme-proof/03-urban-park-sun-visible.png, .codex/test-artifacts/176-ox99-urban-park-theme-proof/04-urban-park-after-timeline-scrub.png, .codex/test-artifacts/176-ox99-urban-park-theme-proof/05-first-city-baseline-proof.png, .codex/test-artifacts/176-ox99-urban-park-theme-proof/06-urban-park-moon-visible.png.

## Verification actually performed

- python scripts/dev.py workflow — exit 0 before activation and at CLOSEOUT; Workflow scaffold: OK. Post-close validation also exited 0 with State IDLE.
- python scripts/dev.py build — BUILD SUCCESSFUL.
- python scripts/dev.py --serial 8AKY0M32X run — exit 0; installed and launched the debug app on Pixel 3 XL.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/01-settings-living-urban-park-active.png screenshot — captured Settings with Living Urban Park active.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/02-settings-urban-park-active.png screenshot — captured Settings with Urban Park active.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/03-urban-park-sun-visible.png screenshot — captured the noon Urban Park scene.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/04-urban-park-after-timeline-scrub.png screenshot — captured the selected 2:44:34 PM CDT scene after the direct timeline drag.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/05-first-city-baseline-proof.png screenshot — captured the existing debug-only baseline proof.
- python scripts/dev.py --serial 8AKY0M32X --output .codex/test-artifacts/176-ox99-urban-park-theme-proof/06-urban-park-moon-visible.png screenshot — captured the supported afternoon Sun/Moon scene.
- `identify -format '%f %wx%h %[channels] alpha-min=%[fx:minima.a] alpha-max=%[fx:maxima.a]\n' app/src/main/res/drawable-nodpi/urban_park_background.png app/src/main/res/drawable-nodpi/urban_park_middleground.png app/src/main/res/drawable-nodpi/urban_park_foreground.png` — each asset is 1024 × 1536 sRGBA with alpha minimum 0.
- git diff --check — exit 0 with no whitespace errors at CLOSEOUT.
- No tests were added or run. RTL, TalkBack/accessibility services, font scaling, and motion settings were not checked; these app-wide checks are outside this slice.

## Evidence and limits

Cycle evidence: `.codex/test-artifacts/176-ox99-urban-park-theme-proof/`. Archived plan: `.codex/history/plans/176-ox99-urban-park-theme-proof.md`. See the plan for stage checkpoints and exact boundaries.
