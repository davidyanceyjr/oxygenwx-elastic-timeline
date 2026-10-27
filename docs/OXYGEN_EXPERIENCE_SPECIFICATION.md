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

**Experience target:** immersive but legible; interactive but predictable; ambitious but restrained. Every gesture and visual effect must improve understanding or make navigation more effortless.

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
9. **Keep complexity behind the surface.** Sophisticated state mapping and rendering must not create additional work for a user.
10. **Measure on a device.** Attractive screenshots and compiling source do not establish good touch interaction.

## 4. Interaction contract: current preferred model

**Important design decision:** The adopted prototype in the inspected repository uses a **uniform hourly time axis after long-press**, *not* the older local fisheye/day-only expansion. Preserve the shipped interaction until an intentional, evidence-backed revision is approved.

### 4.1 Stable selected time

The single authoritative value is the **selected forecast moment**. UI position, zoom/scale, animation fraction, and rendered weather are functions of that value, not independent competing clocks.

- The selected moment stays under the fixed central playhead while the axis changes scale.
- The screen may shift from a daily representative illustration to an hourly illustration at the same selected time; **presentation mode is not the underlying timestamp**.
- Selecting a new day/hour or explicitly returning to now is permitted to change the selected moment.
- Time navigation is bounded by the real available forecast horizon. No wrapping, padding, or extrapolating beyond available data.

### 4.2 Timeline shuttle — daily navigation

**Established prototype behavior**

- A narrow time tape floats near the bottom over a subtle contrast gradient; its touch region can be larger than the visible artwork.
- Three-letter day labels (`FRI`, `SAT`, `SUN`) and day boundary ticks map to actual dates.
- A small, subdued center marker plus larger selected label indicates the selected time; no oversized thumb, panel, or full-height ruler is required.
- **Tap:** select the touched day at a documented representative time (currently approximately local noon).
- **Immediate horizontal drag:** move the **tape** beneath the central reading position. Drag left advances toward later dates; drag right moves toward earlier dates.
- **Release in daily browsing:** settle to the selected day's representative moment. A displayed day summary must clearly be described as a daily representation, not an hourly fact.
- Day labels remain compact; favor spatial breathing room over more controls.

**Acceptance:** the shuttle remains reachable, the atmospheric canvas remains visually dominant, and a user can reach a future Saturday directly instead of scrubbing hour by hour across intermediate days.

### 4.3 Timeline shuttle — hourly precision

**Established prototype behavior**

- A stationary touch lasting approximately **480 ms** activates hourly navigation, once the pointer has not exceeded touch slop. This threshold is a tuning default, not an immutable product constant.
- The initial long-press selects the time under the finger; its selected timestamp then stays fixed while the time ruler transitions to the hourly scale.
- The entire tape transitions to **uniform hourly spacing**, currently around **16 dp per hour**, rather than expanding one isolated day.
- An animated scale transition (currently around **260 ms**) reveals hourly ticks and selective major-hour labels. Avoid drawing all 24 hour labels simultaneously.
- Dragging now explores hour-level forecast samples under the fixed playhead; the selected hour label is emphasized.
- Releasing ends the current gesture but leaves hourly precision active. A tap on the main forecast area exits precision while preserving the selected absolute hour; a tap on the timeline exits precision and selects the time under the tap. The hourly sampling and a later daily overview must remain semantically distinct.
- A haptic confirmation of entering precision is appropriate; it must not be the only indication of the change.

**Acceptance:** there is no clock jump caused solely by entering, animating, or leaving precision. Releasing a hold-then-drag leaves hourly precision active. A main-area tap exits precision without changing selected time; a timeline tap exits precision and selects its target time. No gesture is recognized as both an ordinary day drag and a hold-to-hour drag.

### 4.4 Main atmospheric view — shortcut navigation

**Established prototype behavior**

- **Horizontal swipe on the central forecast summary:** go to the following or preceding calendar day, preserving the selected *hour-of-day* as closely as the forecast domain permits.
- **Double-tap on the central summary/main temperature region:** return directly to the present forecast moment (the demo currently maps the device clock into its fixed fixture).
- Main-view gestures are owned by the main-view touch region; shuttle gestures are owned by the timeline. They must not both consume the same touch sequence.
- Keep these shortcuts fast and repeatable, with no page transitions masquerading as weather changes.

**Future production caveat:** a *calendar day* is not always 24 elapsed hours across daylight-saving transitions. Use location-aware calendar operations and define unambiguous behavior for nonexistent or repeated local hours. For the current fixed 24-hour demo days, ±24 hours is acceptable.

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
- Actual severe-weather alerts from authoritative sources, separate from decorative or inferred storm imagery.

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

**Proposed acceptance targets, not measured claims:**

- Aim for fluid interaction at the device's standard refresh rate; initially prioritize a dependable **60 fps** experience on the reference handset, then profile frame-time distribution and dropped frames on actual hardware.
- Never block the UI thread on forecast I/O, geocoding, cache parsing, or heavyweight scene construction.
- Keep per-frame allocations low; cache stable paths/brushes where helpful; avoid unnecessary whole-screen recomposition from tiny pointer changes.
- Stop decorative animation when the view is not visible or the app is backgrounded.
- Honor system reduced-motion settings: an immediate or substantially reduced-motion scene must communicate the same selected time and forecast meaning.
- Test TalkBack traversal and accessible actions on an installed device. Provide spoken date, local time, current scale, condition, and selected forecast facts.
- Verify larger text, font scaling, contrast on bright/dark weather, narrow widths, landscape, and chronology with RTL locales.
- Make tap and double-tap targets discernible for motor accessibility; support non-gesture navigation before production release without restoring an intrusive persistent control strip.

## 9. Acceptance scenarios and evidence

A bounded development cycle should select scenarios relevant to its modification, then preserve **actual** results (pass/fail/not run) and installed evidence under `.codex/test-artifacts/<cycle>/`.

| ID | Scenario | Pass condition |
|---|---|---|
| T01 | Tap `SAT` | Selected date becomes Saturday's representative overview; day label and weather summary agree. |
| T02 | Drag tape left then right | Time advances then reverses under a fixed playhead; no gesture gets stuck. |
| T03 | Long-press then drag | Hour ticks appear with uniform spacing and finer time changes; haptic/visual confirmation occurs. |
| T04 | Scale animation invariant | A known selected timestamp remains projected to the same playhead coordinate throughout expansion/collapse. |
| T05 | Release precision | Selected hour survives collapse even if daily-overview **rendering** replaces the hourly illustration. |
| T06 | Main-view horizontal swipe | Exactly one day changes in intended direction; local hour is preserved where representable. |
| T07 | Double-tap center | Returns to present moment; never requires scrolling back through days. |
| T08 | Scrub bounds | Cannot select beyond first/last valid forecast data point. |
| T09 | Tap / drag / hold arbitration | Early movement prevents false long-press; cancellation recovers; main-view and tape do not double-handle input. |
| T10 | Bright and dark scenes | Forecast facts and timeline markers stay legible with and without effects. |
| T11 | Larger text / small screens | Key temperature/date/facts remain readable, not clipped or covered by the tape. |
| T12 | TalkBack / non-gesture operation | A user can select time, switch granularity, and return to now with accessible actions. |
| T13 | Reduced motion | Time navigation remains correct with transitions suppressed. |
| T14 | Real-data integration (future) | Source/freshness/missing-horizon states are accurate; no fabricated values or alerts. |
| T15 | DST/timezone boundary (future) | Calendar-day and hourly navigation preserve intended local meaning through spring-forward/fall-back cases. |

**Evidence rule:** Do not mark a scenario complete based on design intent, README claims, a successful compile, or a unit test for only part of the interaction. Record which device, build, viewport, input method, test command, and screenshot/video established the result.

## 10. Development sequence and scope protection

Use the repository's `.codex` planning workflow, one bounded slice at a time. This sequence is proposed, not an automatic change to `docs/ROADMAP.md`.

### UX.0 — Specification adoption and baseline reconciliation

- Review this proposal against the **latest local changes** and obtain owner sign-off on any discrepancies.
- Keep existing `docs/SPECIFICATION.md` authoritative until an explicit adoption cycle updates the authority order.
- Reconcile README references to removed `HOURS/DAYS`, `NOW`, and footer controls; check whether prototype tests still assert historical local-focused-day behavior not used by the current UI.
- Capture and label an installed “known good” baseline of the current shuttle, center swipes, and double-tap return.

### UX.1 — Interaction stabilization

- Formalize gesture arbitration and selection/scale invariants in pure tests and installed touch review.
- Tune long-press threshold and drag gain with actual physical-device evidence.
- Preserve day and hour selection through release and layout changes.
- Include timeline bounds and rapid repeated navigation.

### UX.2 — Accessibility and resilience

- Validate TalkBack custom actions, large fonts, compact widths, RTL, high contrast, reduced motion, and input alternatives.
- Fix only the smallest evidenced problems; avoid new permanent control rows.

### UX.3 — Atmospheric polish

- Improve sky/cloud/light/rain treatment in separable, composable layers.
- Keep imagery subordinate to data and benchmark installed rendering/battery use.
- Any asset/shader or motion enhancement must preserve UX.1/UX.2 acceptance.

### UX.4 — Navigation intelligence (candidate, separately approved)

- Consider event markers such as rain onset, likely clearing, peak warmth, and sunrise/sunset **only where source data supports those labels**.
- Treat jump-to-event controls as optional and contextual, not new permanent chrome.
- A candidate weather-colored time tape needs non-color-only semantics and honest uncertainty.

### UX.5 — Production-data feasibility (deferred)

- Explicitly decide whether this remains a separate experience or becomes a redesign of the original Oxygen product.
- Model forecast time with location-aware timestamps, provider provenance, missing data, and honest daily summaries.
- Plan provider, cache, location, alerts, privacy, and navigation integration as independent bounded cycles.
- No automatic transfer of experimental gestures into the original `Now -> Hourly -> Daily -> Details` pager.

## 11. Anti-goals: what not to do

- Do not replace the shuttle with seven large cards or a full-width control panel.
- Do not permanently restore a toggle row because a gesture was difficult to test once; first improve affordance and accessible alternatives.
- Do not put feature badges, event chips, or animated status tiles across the atmosphere by default.
- Do not claim physically accurate rain, clouds, sunset, or precise precipitation timing without appropriate data and methods.
- Do not let scale changes or animation frames change forecast selection.
- Do not let new background effects reduce responsiveness, contrast, legibility, or battery life.
- Do not write over the original Oxygen project as a shortcut to integration.

## 12. Feature decision filter

Before accepting a UI enhancement, answer:

1. Which real forecast question does this make easier to answer?
2. Can it be learned without a tutorial and used with one hand?
3. Does it preserve the three core navigation routes and selection invariants?
4. Does it increase permanent visual clutter, ambiguity, or hidden state?
5. Can every visible weather claim be traced to actual data and a valid time?
6. Is an accessible/reduced-motion path available?
7. Has the actual app been tested at the necessary device and layout states?

If the first three cannot be demonstrated, **defer the feature** regardless of how striking the mockup looks.

## 13. Baseline observed at the cited commit

The following is an inspection of the public `main` branch, not a claim about newer local work:

| Area | Observed at `718f271` | Remaining gap |
|---|---|---|
| Minimal timeline | Three-letter labels, subdued central dot, large selection cue | Contrast/legibility matrix |
| Daily/hour gestures | Custom tap/drag/480 ms hold, uniform hourly scale | Full gesture arbitration and accessibility review |
| Main-view navigation | ±24-hour swipe, double-tap back to fixture now | Calendar-aware production time semantics |
| Atmospheric visual | Canvas sky, clouds, lighting, illustrative rain and landscape | Reduced motion, performance and data fidelity |
| Source data | Eight synthetic days, 192 hourly samples, explicit demo labeling | Real forecast services are out of scope |
| Installed evidence | Pixel 3 XL checks in history cycles 002–006 | Lint/unit suite/TalkBack and other matrices not completely accepted |
| Documentation | Prototype spec and active P.2 roadmap | README describes removed visible controls; historical test still covers localized expansion |

## 14. Definition of product success

Oxygen succeeds when the user can glance at the atmosphere, swipe to tomorrow, dive into an hour of interest using the shuttle, and double-tap home—**without stopping to think about the interface**. The visuals should invite exploration, but forecast meaning, speed, and confidence are the enduring result.
