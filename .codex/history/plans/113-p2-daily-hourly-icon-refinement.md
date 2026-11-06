# Cycle plan — 113-p2-daily-hourly-icon-refinement

State: ACTIVE — owner-approved board and bounded plan reviewed; ready for activation.

Roadmap: P.2.20
Objective: Enlarge daily timeline weather icons to fill the space between weekday labels and divider, and remove weather icons from hourly scale to reduce clutter.

## Board gate

- **Combined behavior and UI artwork board:** `.codex/concepts/P.2.20-daily-hourly-icon-refinement.svg`, PNG preview `.codex/concepts/P.2.20-daily-hourly-icon-refinement.png`, and behavior/art contract `.codex/concepts/P.2.20-daily-hourly-icon-refinement.md`.
- **Before/input:** `.codex/concepts/P.2.20-daily-hourly-icon-refinement-before.png`, a fresh installed active-catalog Pixel 3 XL daily screen captured on 2026-11-06.
- **Draft preview revision:** SHA-256 `234558ad6bc9a8ddc6da871d8c5dea8a4f14e60ed1b1b1719e9537bdd04a2d86`; this identifies the current draft only and is not an approval record.
- **Owner approval:** Approved on 2026-11-06 after the revised combined board was shown fullscreen in `feh` on `DISPLAY=:0`. The approved preview SHA-256 is `234558ad6bc9a8ddc6da871d8c5dea8a4f14e60ed1b1b1719e9537bdd04a2d86`.
- **Approval constraints and acceptance relationship:** Daily representative icons remain attached to their dates and are enlarged to fill the band beneath the weekday label and above the divider without overlap. Hourly weather symbols are removed; labels, ticks, divider, playhead, and forecast facts remain. Existing projection, missing-event semantics, gestures, selected-time continuity, and interaction behavior are preserved. The approved artwork governs implementation and visual acceptance; installed screenshots and focused interactions remain required for closeout.

## Production boundary

Expected implementation surface, subject to the approved board:

- `app/src/main/java/com/oxygen/weather/timeline/ui/CatalogTimeLabScreen.kt` — daily symbol draw size/vertical placement and suppression of event symbols in hourly scale.

Do not change event-to-symbol projection, daily representative event selection, weather symbol artwork/mapping, forecast facts, timeline gestures, selected-time behavior, the fixture/debug timeline, or the deprecated oxygenWX app.

## Functional invariants

- Daily scale continues using the existing daily representative event for each date.
- Center each daily icon beneath the matching three-letter weekday label at that date's timeline coordinate.
- Increase daily symbols to the largest size that fits between the weekday label and divider without overlap; retain legible labels, ticks, divider, and playhead.
- Hourly scale draws no weather event icons. Keep the existing hourly labels, ticks, divider, playhead, forecast facts, and interaction behavior.
- Missing daily weather events remain icon-free. Do not add fallback symbols or change data/mapping semantics.
- Preserve daily drag, hourly scrub, double-tap scale toggle, selected-time continuity, and gesture ownership.

## Steps

- [x] Capture the current installed daily screen as the before/input state.
- [x] Prepare a combined behavior and UI artwork board with daily enlargement and icon-free hourly output.
- [ ] Show the board and record explicit owner approval of its exact revision and constraints.
- [ ] Finalize/review this bounded plan after approval, then activate only with authorization.
- [x] Adjust the daily symbol size/placement and remove hourly event symbols within this plan's boundary.
- [x] Build/install on the Pixel 3 XL, capture daily and hourly output, and directly check daily drag, hourly scrub, and scale toggle.
- [x] Preserve installed evidence, inspect `git diff --check` and the final diff, and close with the daily-drag/noon limitation recorded.

## Acceptance and evidence

- **PASS — daily appearance:** `daily.png` and `daily-after-toggle.png` show date-matched daily representative symbols enlarged to 31 dp beneath weekday labels, centered on each date, and clear of labels/divider.
- **PASS — hourly appearance:** `hourly.png` and `hourly-after-scrub.png` show no weather symbols; hour labels, ticks, divider, playhead, forecast facts, and selected-time badge remain readable.
- **PASS — preservation:** focused Pixel interactions show hourly scrubbing changes selection while remaining hourly, double-tap scale toggle retains the exact selected instant, and daily dragging moves the tape with icons attached to their dates.
- **PASS by unchanged source path — missing events:** the daily renderer still uses `day.weatherSymbolId?.let`, so a null projected event produces no icon. The daily/hourly projection and missing-event handling were not changed. No new installed missing-event state was captured in this cycle.
- **Evidence target:** `.codex/test-artifacts/113-p2-daily-hourly-icon-refinement/README.md` and installed screenshots; eventual closeout `.codex/history/113-p2-daily-hourly-icon-refinement.md` and archived plan `.codex/history/plans/113-p2-daily-hourly-icon-refinement.md`.
- **Verification actually run:** `python scripts/dev.py workflow` PASS; `python scripts/dev.py run --serial 8AKY0M32X` PASS (debug build, streamed install, launch); installed screenshots and focused Pixel interactions documented in the evidence README; `git diff --check` PASS. No unit/instrumentation tests, RTL, TalkBack, font scaling, reduced-motion, or app-wide system matrices were run.

## Risks and assumptions

- The current canvas places the daily icon in a narrow band. The installed 31 dp symbol size uses the available space and remains clear of weekday text and divider in the captured viewport.
- Hourly event symbols are currently drawn in a separate scale branch; remove their drawing while leaving hourly tape geometry and ticks unchanged.
- Board sample glyphs communicate scale and placement only; runtime daily event identity continues to come from the existing projection.

## Out of scope

Daily drag noon correction (cancelled P.2.19), data/projection changes, icon redesign, mapping changes, fixture/debug timeline, selected-time and gesture changes, central forecast redesign, broad system acceptance, and unrelated visual/body work.
