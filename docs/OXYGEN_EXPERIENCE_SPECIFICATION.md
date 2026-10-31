# Oxygen — Experience & Interaction Specification

**Document ID:** OXY-UX-001
**Version:** 0.1 — adopted 2026-10-24
**Prepared:** 2026-10-24
**Target repository:** [`davidyanceyjr/oxygenwx-elastic-timeline`](https://github.com/davidyanceyjr/oxygenwx-elastic-timeline)
**Repository evidence baseline:** `main` at `718f271d58818038c866a9572278512e8a4eef2b` (2026-10-24 UTC)
**Location:** `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`
**Status:** Adopted as the experience-level companion to `docs/SPECIFICATION.md`; **not** an assertion that all requirements are implemented or accepted. `docs/SPECIFICATION.md` remains controlling for prototype scope and hard semantic/architecture constraints; this document supplies experience principles and acceptance criteria. Conflicts require a bounded owner decision recorded in `docs/SPECIFICATION.md` before implementation. Existing `.codex` history remains the authority for what was previously implemented or verified.

> **Weather is the experience. Time is the interface. Data is the truth.**

## 1. Purpose and product promise

Oxygen is a modern weather experience in which a person *explores the coming atmosphere* instead of navigating through a succession of cards, tabs, and forecast tables. The interface should be visually expressive, comfortable in one hand, easy to learn through ordinary touch, and accurate about what the forecast can and cannot tell us.

A user should be able to answer, without hunting through screens:

1. What is the weather like now?
2. What will conditions look and feel like on a particular day?
3. Approximately when should rain stop, skies clear, or temperatures change?
4. What forecast time am I examining, and how can I return to now immediately?
5. Where did the forecast come from, how fresh is it, and what information is missing?

Every gesture and visual effect must preserve readable forecast facts, predictable time navigation, and truthful data meaning.

## 2. Authority, maturity, and scope

### 2.1 Source-of-truth boundary

This is the experience specification for the **successor OxygenWX app** in this repository. The current app remains an isolated synthetic-data prototype while the approved WX roadmap stages successor capabilities. The deprecated [`oxygenWX`](https://github.com/davidyanceyjr/oxygenWX) application and its production files are outside this repository's change boundary; its four-page navigation is not the successor app's navigation contract.

- The new project's `AGENTS.md`, `docs/SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, and `.codex/` lifecycle remain binding during review.
- This document supplies **adopted experience-level acceptance criteria** and future product direction. The adoption and baseline reconciliation are recorded in `.codex/history/022-experience-spec-adoption.md`.
- The WX roadmap authorizes future successor capabilities only through their bounded plans; this specification alone does not authorize implementation.
- No WX work authorizes modifying the deprecated application's navigation, weather services, or data cache.
- Read actual code and the latest local working tree before implementation: the GitHub snapshot may lag active local Codex work.

### 2.2 Status terminology

- **Established prototype behavior:** present in the inspected source, with some on-device evidence recorded in `.codex/history/`.
- **Required design invariant:** a rule that future work must preserve after owner adoption; a test or device review may still be missing.
- **Candidate enhancement:** an interesting direction but not committed scope.
- **Production gate:** a prerequisite for removing demo limitations or claiming release readiness.

### 2.3 Current prototype limits and successor scope

The current prototype still has synthetic data and no live forecast integration, location selection, real-data cache, or official alerts. The WX roadmap stages live data, manual location search, persistence, and source switching as separate bounded successor slices; those capabilities are not implied to exist today. Official alerts remain a separate future subsystem. Background GPS tracking, social features, advertising, accounts, and unbounded screen/navigation redesign remain outside the approved successor direction.

The successor's target catalog uses 44 local calendar dates: D-30 through D-1 as historical dates and D through D+13 as forecast dates, with D derived from `asOf` in the selected location's IANA timezone. Live, simulated, and replay data share one canonical schema and provenance rules. Missing fields remain missing, archived model forecasts are not station observations, and official alerts are not catalog conditions. The immersive timeline and its selected-time, gesture, accessibility, RTL, reduced-motion, and readability invariants remain binding as data capabilities are added.

## 3. Non-negotiable experience principles

1. **Atmosphere first.** The screen is principally a weather scene, not a dashboard of opaque cards.
2. **Time is spatial.** The user can pull forecast time beneath a stable reading point, like a video-editor time ruler.
3. **One coherent interaction language.** The shuttle, main-view swipes, and return-to-now gesture should reinforce one another, not compete.
4. **Minimal permanent chrome.** Use compact labels, subtle ticks, and unobtrusive cues. Do not reintroduce a permanent daily/hourly button row or instructional footer without a new owner decision.
5. **No surprise jumps.** Changing scale must not change the chosen timestamp. A long-press may first select the moment beneath the initial touch; the subsequent scale animation cannot silently alter it.
6. **True data remains distinguishable from illustration.** Never imply that animated cloud, rain, sun, or a smoothly varying number is a source-measured minute-by-minute prediction.
7. **Reachability and clarity over novelty.** A beautiful gesture without a perceivable, accessible alternative is unfinished design.
8. **Motion is optional.** Reduced-motion users get the same forecast and navigation meaning.
9. **Measure on a device.** Screenshots and compiling source do not establish touch acceptance.

## 4. Interaction contract: current preferred model

**Important design decision:** The adopted prototype in the inspected repository uses a **uniform hourly time axis after long-press**, *not* the older local fisheye/day-only expansion. Preserve the shipped interaction until an intentional, evidence-backed revision is approved.

### 4.1 Stable selected time

The single authoritative value is the **selected forecast moment**. UI position, zoom/scale, animation fraction, and rendered weather are functions of that value, not independent competing clocks.

- The selected moment stays under the fixed central playhead while the axis changes scale.
- The screen may shift from a daily representative illustration to an hourly illustration at the same selected time; **presentation mode is not the underlying timestamp**.
- Daily tap and daily drag release select exactly 12:00 local on the target date in the selected location's IANA timezone. A scale-only collapse preserves the exact selected instant, even when the daily view shows a day summary.
- In-window hours or days with missing facts remain selectable and navigable. Show unavailable facts explicitly, never as zero; condition imagery must not imply a missing fact exists. A data refresh must not silently retarget selection.
- Targets beyond the first or last selectable catalog instant clamp to the nearest boundary with a communicated adjustment. No wrapping, padding, or extrapolating beyond the catalog window.

### 4.2 Timeline shuttle — daily navigation

**Established prototype behavior**

- A narrow time tape floats near the bottom over a subtle contrast gradient; its touch region can be larger than the visible artwork.
- Three-letter day labels (`FRI`, `SAT`, `SUN`) and day boundary ticks map to actual dates.
- A small, subdued center marker plus larger selected label indicates the selected time; no oversized thumb, panel, or full-height ruler is required.
- **Tap:** select the touched date at exactly 12:00 local time. On a partial boundary date, an out-of-window noon target clamps to the nearest catalog boundary with an adjustment notice.
- **Immediate horizontal drag:** move the **tape** beneath the central reading position. Drag left advances toward later dates; drag right moves toward earlier dates.
- **Release in daily browsing:** select 12:00 local on the target date, subject to the boundary clamp. A displayed day summary is a daily representation, not an hourly fact.
- Day labels remain compact; favor spatial breathing room over more controls.

**Acceptance:** the shuttle remains reachable, the atmospheric canvas remains visually dominant, and a user can reach a future Saturday directly instead of scrubbing hour by hour across intermediate days.

### 4.3 Timeline shuttle — hourly precision

**Established prototype behavior**

- A stationary touch lasting approximately **480 ms** activates hourly navigation, once the pointer has not exceeded touch slop. This threshold is a tuning default, not an immutable product constant.
- The initial long-press selects the time under the finger; its selected timestamp then stays fixed while the time ruler transitions to the hourly scale.
- The entire tape transitions to **uniform hourly spacing**, currently around **16 dp per hour**, rather than expanding one isolated day.
- An animated scale transition (currently around **260 ms**) reveals hourly ticks and selective major-hour labels. Avoid drawing all 24 hour labels simultaneously.
- Dragging now explores hour-level forecast samples under the fixed playhead; the selected hour label is emphasized.
- Releasing ends the current gesture but leaves hourly precision active. A main-area tap collapses to daily overview and preserves the exact instant. An hourly tape tap resolves the touched instant on the hourly axis before collapse, selects it, and preserves it through collapse. The hourly sampling and daily overview remain semantically distinct.
- A haptic confirmation of entering precision is appropriate; it must not be the only indication of the change.

**Acceptance:** there is no clock jump caused solely by entering, animating, or leaving precision. Releasing a hold-then-drag leaves hourly precision active. A main-area tap collapses to daily overview without changing selected time; a timeline tap collapses to daily overview and selects its target time. No gesture is recognized as both an ordinary day drag and a hold-to-hour drag.

### 4.4 Main atmospheric view — shortcut navigation

**Established prototype behavior and successor rule**

- **Horizontal swipe on the central forecast summary:** in the fixed-fixture prototype, shift by 24 hours while preserving hour-of-day. In the successor, target the adjacent local calendar date at the same wall time. For a repeated local time, retain the prior offset when possible or choose the earlier occurrence; for a nonexistent local time, choose the first valid time. An in-window gap remains selected with unavailable facts; an out-of-window target clamps to the nearest boundary with an adjustment notice.
- **Double-tap on the central summary/main temperature region:** the fixed-fixture prototype returns to its mapped fixture hour. Successor live reset targets the current instant at activation, including with a stale live catalog; simulated and recorded replay reset to catalog `asOf`. Out-of-window targets clamp with an adjustment notice. Stale and unavailable provenance remain visible.
- Main-view gestures are owned by the main-view touch region; shuttle gestures are owned by the timeline. They must not both consume the same touch sequence.
- Keep these shortcuts fast and repeatable, with no page transitions masquerading as weather changes.

The prototype's fixed 24-hour fixture days permit ±24-hour navigation. Successor navigation uses location-aware calendar-day operations; a calendar day may be 23 or 25 elapsed hours across daylight-saving transitions.

### 4.5 Gestural precedence and discoverability

- The earliest decisive gesture wins: tap, immediate horizontal drag, stationary hold then drag, main-view swipe, or main-view double-tap. Clear cancel paths on pointer loss or orientation change.
- The fixed playhead and labeled tick marks make the tape perceivable as interactive without a permanent tutorial bar.
- Prefer restrained, contextual, dismissible first-use education *only if device testing shows users fail to discover precision mode*.
- **Accessibility parity is mandatory before release:** TalkBack or other assistive input must offer a discoverable method of switching scale and moving time. The prototype's custom accessibility scale action is a beginning, not completed acceptance.
- Haptics must respect device/user settings; use sparingly at meaningful events, not every frame.

## 5. Visual and information hierarchy

### 5.1 Screen composition

Priority order:

1. **Atmospheric visual field** — full-bleed, dynamic, and subordinate to readable facts.
2. **Current selected forecast** — large temperature, clear condition text, and date/time label.
3. **Secondary facts** — a small, stable set such as precipitation chance, wind, and daily high/low; accessible in text.
4. **Timeline shuttle** — minimalist time-navigation instrument near the bottom, with a forgiving invisible touch region.
5. **Source/freshness and deeper facts** — reachable without permanently covering the scene when real data becomes available.

Avoid accumulating persistent mode rows, page bars, chip clusters, weather cards, and status banners. Every new permanent element must justify the portion of the atmospheric scene it obscures.

### 5.2 Weather scene

- The atmosphere responds to selected time and appropriate forecast fields: lighting, sky palette, cloudiness, precipitation identity, and time of day.
- Scene transitions can be aesthetically continuous between hourly forecast samples, but **do not represent interpolated transitions as measured minute-by-minute forecasts**.
- A precipitation probability is **not** rainfall rate; rain-streak artwork must be treated as illustrative unless actual precipitation-intensity inputs exist.
- The sun's position and sunrise/sunset cues must not be presented as physically or geographically accurate until location, timezone, date, and a validated solar model are used.
- Decorative terrain, clouds, stars, and textures are optional. They must never cover critical information or cause excessive battery drain.
- Daily overview should use an honestly labeled representative or aggregated day visualization; hourly precision should represent the selected hour. Switching these display lenses may alter the scene even when the selected moment does not change.

### 5.3 Weather-encoded timeline — candidate enhancement

Optional weather segments could hint at clear, cloudy, wet, or stormy periods using **subtle** nonexclusive visual encodings. Keep tick marks and labels readable without color, and do not imply an exact rain onset or severity if the underlying data cannot support it.

### 5.4 Appearance constraints

- Small uppercase day markers and compact hour labels; selected label visibly but modestly enlarged.
- Restrained luminance changes and clear information typography; avoid excessive glow, glass, saturation, or parallax.
- Timeline contrast must work against both bright day scenes and dark storm/night scenes.
- Respect system bars, display cutouts, compact handsets, font scale, orientation, and RTL layout. Chronology must remain interpretable in every supported direction.
- Preserve 48 dp-equivalent touch accessibility where feasible even when artwork appears much smaller; existing prototype uses an 84 dp touch region.

## 6. Forecast truth and honest failure states

**Prototype:** currently uses deterministic **DEMO DATA**. Keep that label as long as demo values are shown. The atmospheric scene is an illustration, not a live observation or nowcast.

**Production gate:** once real data is introduced, each selected forecast state must distinguish:

- Location and appropriate local time / timezone.
- Observation versus forecast/model estimate.
- Forecast provider, valid time, last update, freshness, and stale/offline condition.
- Availability and missing values. Missing is not `0` and must not be silently imputed.
- Forecast horizon limits and available granularities; do not synthesize 72 hours if only 36 are present.
- If an official-alert subsystem is offered, alerts from authoritative sources, separate from decorative or inferred storm imagery. Real-data integration does not require alerts. Before the subsystem exists, an empty display must not imply that no alerts exist.

If data is unavailable, the shell should remain navigable and honest. Do not show visually convincing but fabricated “current weather” as a fallback. Old cached forecasts, if ever added, must be clearly marked stale.

## 7. Proposed Compose architecture

The current project already separates synthetic data (`data/`), typed presentation (`presentation/`), and Compose views (`ui/`). Continue that boundary.

```text
Forecast samples + source metadata
          |
          v
Typed time-aware forecast projection  --->  accessible text + semantic state
          |
   selectedForecastMoment  (single authoritative time)
          |
     +----+-------------------+
     |                        |
 Timeline projection      Atmospheric scene
 (time <-> pixels)        (visual-only frame)
     |
 Gesture reducer
 (tap / drag / hold / release / cancel / reset)
```

Suggested explicit state categories (conceptual, not a required rename of current code):

- `selectedForecastMoment`: absolute, location-aware forecast time for future production; currently `selectedHour: Double` in the fixture.
- `timeScale`: daily versus hourly. Neither scale nor zoom may rewrite the selected moment.
- `gesturePhase`: idle, candidate tap/hold, day drag, hour drag, settling, or canceled.
- `animatedScaleFraction`: presentation-only animation state.
- `availableForecastWindow`: first/last actual data times; all navigation clamps within it.
- `viewProjection`: daily-overview or hourly-fact presentation derived explicitly from the selected moment and current scale.

**Technical invariants**

1. Mapping from time to x-coordinate is monotonic and invertible over the supported range.
2. `xForTime(selectedTime) == fixedPlayheadX` for all valid scale fractions.
3. A change in zoom factor alone never modifies `selectedTime`.
4. Drawing state, haptics, and gesture state cannot trigger provider refresh or modify cached weather.
5. Rendering may interpolate aesthetics; forecast facts retain valid source sample timestamps and explicit granularity.
6. Critical text and accessibility labels come from the presentation model, not from parsing graphics or formatted strings.
7. The time tape and the central display have distinct gesture owners; system Back and assistive gestures remain usable.

## 8. Performance, motion, battery, and accessibility

**Requirements for later implementation acceptance, not measured claims:**

- Profile frame-time distribution and dropped frames on actual hardware during a defined scrub and scene workload. Each bounded implementation plan states its device baseline, measurement method, and justified pass threshold; 60 fps is a design aim, not an unmeasured acceptance claim.
- Never block the UI thread on forecast I/O, geocoding, cache parsing, or heavyweight scene construction.
- Keep per-frame allocations low; cache stable paths/brushes where helpful; avoid unnecessary whole-screen recomposition from tiny pointer changes.
- Stop decorative animation when the view is not visible or the app is backgrounded.
- Honor system reduced-motion settings: an immediate or substantially reduced-motion scene must communicate the same selected time and forecast meaning.
- Test TalkBack traversal and accessible actions on an installed device. Provide spoken date, local time, current scale, condition, and selected forecast facts.
- Verify larger text, font scaling, contrast on bright/dark weather, narrow widths, landscape, and chronology with RTL locales.
- Make tap and double-tap targets discernible for motor accessibility; support non-gesture navigation before production release without restoring an intrusive persistent control strip.

For each applicable state, later implementation cycles record **pass/fail/not run**, the expected result, device and build, viewport and orientation, font scale, locale and layout direction, input method, motion setting, and installed screenshot/video or measurement path. The matrix covers the Pixel 3 XL reference viewport, affected compact viewport and landscape layout, large font, bright and dark scenes, RTL, reduced motion, TalkBack and non-gesture actions, gesture arbitration, and performance when rendering or interaction changes. A bounded plan identifies applicability, the exact workload and measurement method, and justified numeric thresholds where useful. A not-run state remains an acceptance gap.

## 9. Acceptance scenarios and evidence

A bounded development cycle should select scenarios relevant to its modification, then preserve **actual** results (pass/fail/not run) and installed evidence under `.codex/test-artifacts/<cycle>/`.

| ID | Scenario | Pass condition |
|---|---|---|
| T01 | Tap `SAT` or release daily drag | Select 12:00 local Saturday; on a partial boundary date clamp an out-of-window noon and communicate the adjustment. Day summary and selected time agree. |
| T02 | Drag tape left then right | Time advances then reverses under a fixed playhead; no gesture gets stuck. |
| T03 | Long-press then drag | Hour ticks appear with uniform spacing and finer time changes; haptic/visual confirmation occurs. |
| T04 | Scale animation invariant | A known selected timestamp remains projected to the same playhead coordinate throughout expansion/collapse. |
| T05 | Release precision | Releasing ends the drag but keeps hourly precision active and the selected time unchanged. |
| T05a | Tap to collapse precision | Main-view tap preserves the exact instant. Hourly tape tap resolves its touched instant on the hourly axis before collapse and preserves that instant through animation. |
| T06 | Main-view horizontal swipe | Targets one adjacent local date at the same wall time using the DST rule; a missing destination stays selected with unavailable facts, and an out-of-window target clamps with notice. |
| T07 | Double-tap center | Live, including stale live, targets current instant; simulated/replay targets catalog `asOf`; outside coverage clamps with notice and provenance stays truthful. |
| T08 | Scrub bounds and gaps | Selection clamps only outside catalog boundaries; an in-window missing hour/day stays selectable with unavailable facts and no condition imagery claiming a fact. Navigation crosses the gap. |
| T09 | Tap / drag / hold arbitration | Early movement prevents false long-press; cancellation recovers; main-view and tape do not double-handle input. |
| T10 | Bright and dark scenes | Forecast facts and timeline markers stay legible with and without effects. |
| T11 | Larger text / small screens | Key temperature/date/facts remain readable, not clipped or covered by the tape. |
| T12 | TalkBack / non-gesture operation | A user can select time, switch granularity, and return to now with accessible actions. |
| T13 | Reduced motion | Time navigation remains correct with transitions suppressed. |
| T14 | Real-data integration (future) | Source/freshness/missing-horizon states are accurate; no fabricated values. Without an alert subsystem, empty alert display makes no absence claim; offered alerts are authoritative and separate. |
| T15 | DST/timezone boundary (future) | Adjacent-date swipes preserve wall time, choose the prior offset in overlaps when possible (otherwise earlier), and first valid time in a gap. |

**Evidence rule:** Do not mark a scenario complete based on design intent, README claims, a successful compile, or a unit test for only part of the interaction. Record which device, build, viewport, input method, test command, and screenshot/video established the result.


## 10. Historical context and planning authority

The UX.0–UX.5 sequence in the 2026-10-24 proposal was a candidate development sequence, not the active roadmap. `docs/ROADMAP.md` and the current bounded cycle govern work order and scope. The successor repository boundary is settled in `docs/SPECIFICATION.md`; the deprecated application is outside this workstream. Candidate event markers and weather-colored tape still require separate approval and source-supported semantics.

The `718f271` public-branch inspection recorded a synthetic eight-day prototype, a uniform hourly tape, fixed-fixture ±24-hour main swipes, and Pixel 3 XL evidence in cycles 002–006. It is dated baseline evidence, not a claim about the current tree or current acceptance. Consult later `.codex/history/` records and installed evidence for subsequent implementation status.

## 11. Feature review rule

A visual or interaction change must identify the forecast question it answers, preserve selected-time and gesture invariants, keep visible facts source-supported, and provide an accessible reduced-motion route. Its bounded plan identifies applicable device states and installed evidence; implementation is incomplete until those acceptance checks are recorded.
