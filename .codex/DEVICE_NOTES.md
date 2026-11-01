# Device-specific constraints

## Pixel 3 XL (`8AKY0M32X`)

- Keep Android Developer Options enabled. Never turn off the Developer Options
  master switch on this device in future work.
- Do not use that switch as an ADB recovery step. If ADB is unavailable, follow
  the active cycle's recovery gate and stop for device-side recovery when
  required.

Recorded 2026-10-25 at the owner's request.

## X window

- The owner's X window is available at display `:0`. When the owner says
  “x window,” use `DISPLAY=:0` for graphical review, including opening boards
  and screenshots there. This is a persistent project environment detail; do
  not ask the owner to repeat the display address.

Recorded 2026-10-31 at the owner's request.
