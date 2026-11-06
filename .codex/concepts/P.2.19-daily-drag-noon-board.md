# P.2.19 — Daily drag release selects noon

## Board status and purpose

This combined behavior and UI artwork board is an **unapproved draft** for the separate follow-up chosen by the owner after cycle 111. The editable board is `P.2.19-daily-drag-noon-board.svg` and its preview is the adjacent PNG. Its input image, `P.2.19-daily-drag-before.png`, is copied from the actual cycle 111 Pixel 3 XL capture `daily-after-drag.png`. The output panel is proposed artwork, not an installed post-change result.

## Observed finding

An active-catalog daily drag from FRI 5:37 AM ended on SAT at 11:39 PM, while `docs/SPECIFICATION.md` requires release on the target date to select 12:00 local time. The icons added in P.2.18 did not change gesture code. The owner chose to keep P.2.18 closed within its icon scope and plan this correction separately.

## Proposed behavior and artwork

1. During a daily drag, the tape keeps moving immediately beneath the fixed playhead, with the existing date labels and icons attached to their dates.
2. At release, resolve the date under the playhead in the selected location's IANA timezone. Select 12:00 local on that date and project it to the center playhead. Update forecast facts for that selected instant.
3. On a partial first or last date, clamp a noon outside the selectable interval to the nearest catalog boundary and communicate the adjustment. An in-window date with missing facts remains selectable; absent facts remain unavailable.
4. Preserve no-op timeline single taps, scale-only double taps with exact selected-time continuity, hourly scrubbing and precision, main-area gestures, and all timeline labels/icons. No new control or weather event mapping is proposed.

The output artwork depicts SAT centered after the drag release with a `12:00 PM` badge. It does not claim a specific forecast value or weather condition at noon; those must come from the selected source data. The displayed weather symbols illustrate preservation of the existing date row.

## Acceptance guided by the board

Installed active-catalog evidence should show a daily drag across a date boundary ending at that date's noon under the center playhead, with its forecast facts tied to that instant. Include a partial-boundary case if the available catalog permits it, and record whether an adjustment notice was shown. Confirm directly that daily drag remains responsive, single taps remain no-ops, double-tap scale toggles preserve the selected instant, hourly scrubbing remains hourly, and date icons/labels stay legible. App-wide RTL, TalkBack, font, motion, and performance matrices remain in dedicated TEST slices.

Owner approval of this exact board package, its constraints, and relationship to the bounded plan must be recorded before the plan is finalized or activated. No P.2.19 implementation is authorized by this draft.
