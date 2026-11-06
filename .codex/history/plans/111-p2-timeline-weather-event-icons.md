# Cycle plan — 111-p2-timeline-weather-event-icons

State: ACTIVE — approved board package and bounded implementation.

Roadmap: P.2.18
Objective: Show daily representative weather-event icons below date labels and hourly weather-event icons on the hourly timeline.

## Board gate

- **Combined behavior and UI artwork board:** `.codex/concepts/P.2.18-timeline-weather-event-icons.svg` with PNG preview `.codex/concepts/P.2.18-timeline-weather-event-icons.png` and contract `.codex/concepts/P.2.18-timeline-weather-event-icons.md`.
- **Daily alignment detail:** `.codex/concepts/P.2.18-timeline-weather-event-icons-daily-alignment.svg` with PNG preview alongside it. The enlarged FRI panel shows the icon and three-letter label on the same date center.
- **Before/input capture:** `.codex/concepts/P.2.18-timeline-weather-event-icons-before.png`, captured from the connected Pixel 3 XL active-catalog daily screen.
- **Hourly layout reference:** `.codex/concepts/P.2.18-timeline-weather-event-icons-hourly-reference.png`, captured from the same installed app using the existing scale toggle.
- **Owner approval:** Approved on 2026-11-05 after the revised combined board and enlarged daily-alignment board were shown in `feh` on `DISPLAY=:0`. The owner said, “I approve.” The approved PNG revisions have SHA-256 `21d5c140a3046283f2284778cae24834f1cdd8af7e4785f2695ddc5bd220468e` (combined) and `19678e12b5f69311bdeb1648389ed4dc230dfa564fd41bd90a7c75a7312186de` (daily alignment).
- **Approval constraints and acceptance relationship:** In daily scale, use the date's daily representative event and center its icon directly below the same three-letter weekday label (the FRI detail demonstrates the shared x center). In hourly scale, use the hourly event for each visible hour. Omit absent or unsupported events. The approved board governs these placements and source rules; installed evidence and direct interaction checks remain required for acceptance.

## Production boundary

Expected implementation files, subject to final review of the approved board:

- `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` — expose the existing daily representative symbol and per-hour symbols to the timeline UI.
- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — place date and hourly icons in the timeline's existing band above the divider.
- `app/src/main/java/com/oxygen/weather/timeline/presentation/symbol/WeatherSymbol.kt` — only if a shared draw-scope path is required to render the existing symbol artwork efficiently in the timeline canvas.

Scope is the active-catalog timeline shown in the board. Do not change provider mapping, scene composition, central readout, fixture/debug timeline, the deprecated oxygenWX app, or any other navigation surface.

## Functional invariants

- In daily scale, each visible date uses its own daily representative forecast event; place its existing mapped symbol beneath the matching weekday label and above the date divider/tick.
- In hourly scale, each visible hour uses that hour's existing mapped event; align its symbol with the hourly tick in the same band above the divider. Symbols travel with their ticks during hourly scrubbing.
- Reuse existing `WeatherSymbolId`, artwork, accessible names, and event-to-symbol mapping. Add no event identities, provider mappings, weather claims, or new symbol artwork.
- If a daily or hourly source event is missing, unsupported, or unmapped, omit that icon. Do not imply clear conditions or borrow another date/hour's event.
- Icons do not change time selection, tick placement, selected-time authority, scale animation, daily drag, hourly scrubbing, double-tap scale toggle, or main-area navigation.
- Keep forecast facts visible as text and preserve timeline label and icon semantics. The icons remain a supplement to forecast facts.

## Steps

- [x] Capture and inspect the current installed daily screen as the before/input and the existing hourly screen as the affected-layout reference.
- [x] Prepare the combined behavior and UI artwork board and bounded plan from the owner's daily/hourly source direction.
- [x] Obtain owner approval of the board; record exact approval date, board revision, constraints, and acceptance relationship.
- [x] After board approval, finalize/review the bounded plan and receive authorization to activate it. The owner approval followed the explicit request to see the boards before beginning implementation.
- [x] Inspect the active-catalog daily and hourly event projection, then implement only the approved icon placements using existing symbol artwork.
- [x] Build/install on the Pixel 3 XL, capture supported and missing-event states, and inspect icon identity, readability, selected-time alignment, daily drag, hourly scrub, and scale toggling.
- [x] Record evidence and limitations, inspect the final diff, and close the cycle only if its exit is met. The existing daily-drag/noon discrepancy is owner-directed follow-up outside this icon change.

## Acceptance / evidence

- **Daily state:** installed active-catalog Pixel capture shows the daily representative event symbol beneath each visible weekday label and above the existing divider. Each shown symbol matches that date's daily representative event.
- **Hourly state:** installed Pixel capture shows the hourly symbol at each visible supported event sample, aligned to the hourly tick and above the divider. Direct hourly scrubbing keeps icons paired with their source hours.
- **Missing state:** an absent, unsupported, or unmapped daily/hourly event has no symbol and creates no clear-weather implication or adjacent-time substitution.
- **Preservation:** daily drag, hourly scrub, double-tap scale toggling, selected-time continuity, existing date/hour labels, and forecast values remain correct and readable. The timeline remains responsive and the icon row does not obscure the playhead or divider.
- **Evidence target:** `.codex/test-artifacts/111-p2-timeline-weather-event-icons/README.md`, screenshots under the same cycle directory, and `.codex/history/111-p2-timeline-weather-event-icons.md`.
- **Authorized verification if activated:** `python scripts/dev.py workflow`; `python scripts/dev.py run --serial 8AKY0M32X`; actual installed captures and focused direct interaction checks for the states above. No unit or instrumentation tests are planned because the user has not requested tests. Do not add RTL, TalkBack, large-font, reduced-motion, or broad system matrices to this design slice.

## Risks / assumptions

- `CatalogDayView` currently carries a daily `overviewInstant` and text details, while the displayed symbol is currently projected for the selected instant. Confirm the daily representative symbol can be exposed from the same existing daily representative projection without inferring an event from unrelated hourly samples.
- The hourly axis is currently 16 dp/hour. The approved artwork must confirm that the event symbols remain distinguishable at this spacing; do not silently reduce symbol identity or hide labels to make the row fit.
- The board's example glyph sequence only illustrates placement. Runtime symbol identities must always come from the corresponding daily/hourly data.

## Out of scope

- New weather-symbol design or event/provider mapping; daily or hourly aggregation changes; interpolation or fallback rules; changes to the central readout, atmospheric scene, forecast facts, date/time selection, timeline gestures, selection projection, debug workbench, or fixture/debug timeline; app-wide accessibility/system acceptance; unrelated UI/body work.
