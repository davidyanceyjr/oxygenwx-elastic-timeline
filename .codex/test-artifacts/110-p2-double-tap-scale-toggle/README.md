# Cycle 110 installed evidence

Date: 2026-11-05
Device: Pixel 3 XL (`8AKY0M32X`), portrait, 1440×2960 physical pixels, 411×846 dp
Build: debug build installed by `python scripts/dev.py run --serial 8AKY0M32X`

## Commands

- `python scripts/dev.py workflow` — passed; cycle 110 was ACTIVE.
- `python scripts/dev.py run --serial 8AKY0M32X` — final source built, installed, and launched successfully.
- `python scripts/dev.py screenshot --serial 8AKY0M32X --output .codex/test-artifacts/110-p2-double-tap-scale-toggle/<name>.png` — captured the states listed below.
- Device interactions were injected with `adb -s 8AKY0M32X shell input tap`, `input swipe`, and `input motionevent`. A hold-then-drag used `DOWN`, a 700 ms stationary wait, `MOVE`, and `UP`.
- No unit or instrumentation tests were added or run.

## Active catalog

- `catalog-single-tap.png` and `catalog-hold.png`: daily single tap and stationary 900 ms hold leave the daily scale and selected 7:27 PM unchanged.
- `catalog-hourly.png` and `catalog-daily-return.png`: timeline double-tap enters hourly and a second double-tap returns to daily; selected time remains 7:27 PM and centered.
- `catalog-hourly-single-tap.png`: hourly single tap leaves scale and selected time unchanged.
- `catalog-daily-drag.png`: immediate daily drag navigates to the target date and remains daily.
- `catalog-hourly-drag.png`: hourly drag changes selected time and leaves hourly scale active.
- `catalog-hold-then-drag.png`: after a stationary hold, dragging navigates daily and does not activate hourly scale.
- `catalog-main-tap-collapse-confirmed.png`: main-area single tap collapses hourly mode to daily while retaining the selected instant.

## Fixture/debug profile

- `fixture-final-daily.png`, `fixture-daily-single-tap.png`, and `fixture-daily-hold.png`: daily single tap and stationary hold do not change selection or scale.
- `fixture-daily-drag.png` and `fixture-hold-then-drag.png`: immediate and hold-then-drag navigation remain daily and change the target date.
- `fixture-hourly.png`, `fixture-hourly-single-tap.png`, and `fixture-hourly-drag.png`: double-tap enters hourly at 12 PM, a single tap does not change scale or time, and hourly drag scrubs to 11 AM.
- `fixture-main-tap-collapse.png`: main-area tap returns to daily and retains 11 AM.
- `fixture-main-doubletap-reset.png`: main-area double-tap retains the existing fixture reset behavior and returns to the fixture's mapped time.

## Results and limits

The scoped gesture checks passed on both rendered timeline paths. Selected time stayed centered through scale-only transitions; visible labels and forecast facts remained readable in the captured states. Qualitative interaction review found no stuck gestures or unexpected scale changes. Frame-time/performance measurement was not part of this slice. The screen-reader scale action was preserved but not separately exercised. RTL, TalkBack, font-scale, reduced-motion, and broad system/environment checks were not run, as assigned to dedicated TEST work.

## Owner-accepted screen baseline

On 2026-11-05, the owner asked to preserve the current application screen as a baseline acceptable work. `owner-accepted-current-screen-baseline.png` records the installed Pixel 3 XL active-catalog screen at 1440×2960 physical pixels (411×846 dp), showing Florence, 60°, “Clear night,” with Wednesday at 7:59 PM selected on the daily timeline scale.

The owner's acceptance is for the mechanics and visual concept as executed in this captured state. The owner also stated that the whole system is incomplete and will need substantial further body work. This is a point-in-time design and interaction baseline, not acceptance that the overall system is complete or broadly validated.
