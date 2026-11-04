# Cycle 098-wa5-storm-fallback-symbols

Date: 2026-11-03
Roadmap: WA.5

## Outcome

Completed WA.5 with eight explicitly mapped static storm and fallback symbols, a debug-only eight-range SIMULATED fixture, and scoped installed acceptance.

## Verification actually performed

- `python scripts/dev.py workflow` — PASS before activation and at closeout; scaffold OK.
- `adb devices -l` — PASS; Pixel 3 XL serial `8AKY0M32X` connected and authorized. Device review used portrait 1440×2960 px, 560 dpi, font scale 1.0.
- `python scripts/dev.py run --serial 8AKY0M32X` — PASS; `:app:assembleDebug` reported `BUILD SUCCESSFUL in 17s`, streamed install returned `Success`, and `MainActivity` launched.
- Saved `WA5StormFallback` after loading the eight adjacent ranges from 08:00 through 16:00 on 2026-11-03 in Chicago. Reopened through `EDIT` and inspected the event labels in order; all eight persisted. The fixture supplied empty conditions and phenomena and null measurements; the editor showed unselected condition choices and unavailable chance/amount after save/reopen.
- Captured and inspected each installed symbol with `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/098-wa5-storm-fallback-symbols/<name>.png`: `thunderstorm.png`, `thunderstorm-rain.png`, `thunderstorm-hail.png`, `thunderstorm-snow.png`, `squall.png`, `tropical-cyclone.png`, `tornado-funnel.png`, and `condition-unavailable.png`. Each state showed its label and both 48 dp and 24 dp previews. `reopened-condition-unavailable.png` additionally records the final range after reopening.
- `git diff --check` — PASS; no whitespace errors.
- Detailed captures, visual observations, device/build facts, and limits: `.codex/test-artifacts/098-wa5-storm-fallback-symbols/README.md`.

## Follow-up

Automated tests were NOT RUN per the cycle plan, and no test sources were changed. The existing exact-16-ID catalog assertion is known to be stale and needs maintenance before a separately authorized focused test run. RTL, TalkBack, alternate font/device, performance, and broad system/environment matrices remain outside WA.5 for dedicated TEST work. These limits do not block the scoped WA.5 exit; WA.6 is now the sole NEXT slice.
