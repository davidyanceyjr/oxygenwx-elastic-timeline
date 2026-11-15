# TL-01f — Daily scrub release behavior board

**Board status:** Product-owner approved on 2026-11-14; approval and package fingerprints are recorded in cycle 137.

## Slice summary

| Field | Summary |
| --- | --- |
| Slice | TL-01f — Preserve the precise selected time on daily scrub release |
| Objective | Releasing a daily timeline drag retains the exact selected forecast instant reached during the scrub. A selection at 2:37 PM remains at 2:37 PM after release. |
| Scope | Active-catalog and fixture/debug timeline selection behavior; update the controlling behavior statements to remove daily-release selection of noon. |
| Dependencies | P.2.17, P.2.22, WX.25 |
| Preserved behavior | Daily representative symbols and highs remain positioned at their existing noon coordinate; scale changes preserve the exact selection; only targets outside the catalog interval clamp and announce an adjustment. |
| Exclusions | No new controls, labels, artwork, layout, gesture, or forecast-data behavior. No change to daily-to-hourly double-tap entry or hourly scrubbing. |
| UI artwork board | Inapplicable per owner direction: no UI or artwork change is proposed. |
| Exit target | Installed release checks show an in-window selected instant remains exact after release on both timeline surfaces, with existing boundaries and unrelated gestures preserved. |

## Approved behavior

The product owner approved the daily-release contract on 2026-11-14: pointer-up ends the gesture while retaining the exact selected instant; 2:37 PM remains 2:37 PM; daily representative artwork stays at its existing noon coordinate; and existing out-of-catalog boundary clamping remains in force.

## Behavior contract

| State or event | Required behavior |
| --- | --- |
| Daily scrub in progress | The selected forecast instant follows the current scrub position under the fixed playhead. The active-catalog example may resolve to Wednesday at 2:37 PM local. |
| Release inside the catalog interval | End the drag and retain the exact selected instant reached at release. Do not resolve the target date to noon or reconstruct the selection from its date alone. |
| After release | The selected-time authority remains the same instant until another input changes it. Existing selected-time cues and forecast facts continue to follow that instant. |
| Daily representative content | Keep the daily summary's event symbols and high values at their existing noon representative coordinate. That coordinate does not retarget the selected instant. |
| In-window missing facts | Retain the exact selection. Existing unavailable-value and gap behavior continues to apply. |
| Target outside the catalog interval | Preserve the existing nearest-boundary clamp and communicated adjustment. The noon snap is removed; boundary handling is not. |
| Hourly scale or scale transition | Keep current hourly release behavior and exact-instant preservation for scale-only transitions. |
| Daily timeline double-tap | Keep P.2.22 behavior: select 7:00 AM local on the completing tap's date, with its existing out-of-catalog rule. |
| Fixture/debug surface | Retain the same exact underlying selected instant. Its existing nearest-whole-hour source-sample cue may remain; that cue must not overwrite the fractional selection. |

## Flow

1. **Scrub:** The daily tape moves beneath the playhead and the selected instant follows it. Example: Wednesday, 2:37 PM local.
2. **Release:** Pointer movement ends. The selected instant is unchanged.
3. **Settled:** The same 2:37 PM instant remains selected. The daily summary and noon-positioned representative symbols/highs keep their existing meanings.

The release is a gesture end, not a second date-selection operation. In-window data gaps do not change selection. Only an out-of-interval target invokes the existing boundary clamp and adjustment notice.

## Preservation constraints

- Keep the selected `Instant` authoritative; do not derive a new selection from the target date at pointer-up.
- Apply the same release rule to active-catalog and fixture/debug timeline surfaces.
- Preserve daily representative artwork coordinates, hourly selection, P.2.22 daily-to-hourly entry, scale-animation stability, missing-data semantics, and gesture ownership.
- Keep the current UI as-is. The only visible content changes are values that already follow the selected instant; no new visual treatment is proposed.
- Keep verification limited to this behavior and directly affected interaction states. Broad accessibility/system matrices remain with designated TEST work.

## Acceptance focus

1. On the installed active-catalog timeline, scrub in daily mode to an in-window time such as Wednesday 2:37 PM and release. The selected-time state remains 2:37 PM and does not move to noon.
2. Repeat on the fixture/debug timeline. Verify the exact fractional instant is retained even if the existing source-sample cue displays the nearest whole hour.
3. Scrub to an in-window time with missing facts, when available. Selection remains at the requested instant and existing unavailable-value behavior remains intact.
4. Exercise a true catalog-boundary target and confirm the existing nearest-boundary clamp and adjustment communication.
5. Confirm daily-to-hourly entry, hourly scrubbing/release, scale-only preservation, and daily noon-positioned representative artwork retain their existing behavior.

Record actual installed interaction observations and relevant screenshots. This board defines intended behavior; it is not implementation verification or acceptance evidence.

## Source references

- `docs/ROADMAP.md`, TL-01f objective and exit.
- `docs/SPECIFICATION.md`, selected-time authority, P.2.17/P.2.22 gesture rules, and current daily release rule.
- `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`, sections 4.1–4.3 and the current daily release rule.
- `docs/ROADMAP.md`, P.2.23 daily representative symbol/high coordinates and P.2.26 fixture/debug focused-time cue.
- Approved timeline behavior-board style and contracts: `.codex/concepts/P.2.22-location-aware-timeline-double-tap-behavior.md` and `.codex/concepts/P.2.26-hour-tick-labels-behavior-board.md`.
