# Cycle plan — 115-sg-missing-eleventh-hour-data

State: PLANNED
Draft status: INITIAL — unfinalized; required behavior and UI artwork boards are pending owner approval.
Roadmap: SG-missing-eleventh-hour-data
Branch: scene_generator (separate worktree based on `timeline` commit `314a81e`)
Objective: Resolve the demonstrated 11:30 PM–midnight local-day hourly scene/readout gap without inventing forecast values or moving the selected timestamp.

## Authority and board gate

- `docs/SPECIFICATION.md` controls missingness and selected-time authority; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` requires readable forecast facts and separates illustrative transitions from source facts.
- The SG.1 representative-display rule in `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` currently limits a final hourly cell to one half-hour beyond its point. The proposed extension to midnight changes that rule. An approved objective-specific behavior board must resolve the boundary and the controlling specification must be updated before implementation.
- Draft behavior board: `.codex/concepts/SG-missing-eleventh-hour-data-behavior-board.md`, with full-screen review board `.codex/concepts/SG-missing-eleventh-hour-data-behavior-board.svg` and rendered PNG alongside. Draft UI artwork board: `.codex/concepts/SG-missing-eleventh-hour-data-ui-artwork-board.svg` with rendered PNG alongside. Both rendered boards were opened together in `feh` on `DISPLAY=:0` for owner review. These boards propose same-date final-hour display through midnight, field-independent missingness, source-time disclosure, and a next-date handoff. They have no product-owner approval yet. The prior Pixel screenshot and these drafts do not satisfy the approval gate. Record the approved revisions, constraints, and relationship to scope and acceptance here before finalizing or activating.
- `docs/ROADMAP_SPECIFICATION.md`, `docs/ROADMAP.md`, `docs/UI_DEVELOPMENT_WORKFLOW.md`, `.codex/README.md`, `.codex/current.md`, and `AGENTS.md` govern cycle status and evidence.

## Verified baseline

- On 2026-11-06, the connected Pixel's successful Florence LIVE response contained Sunday 2026-11-08 11 PM temperature 7.8 °C, precipitation probability 0%, wind 6.3 km/h, and direction 336°. The installed view still showed a temperature dash and Unknown precipitation/wind at 11:36–11:49 PM; its timeline semantics reported no hourly fact. This was diagnostic evidence from the original `timeline` worktree, which has uncommitted P.2.21 UI changes. Reproduce and capture a baseline from the clean `scene_generator` build before claiming slice acceptance.
- `SemanticSceneGenerator.generate()` builds per-day point tracks in `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt`. The final 11 PM point cell ends at 11:30 PM while the next day's first cell starts at midnight. `SceneReading.precipitationChancePercent` is absent in that gap.
- `CatalogTimelinePresenter` in `app/src/main/java/com/oxygen/weather/timeline/presentation/CatalogTimelinePresentation.kt` evaluates that plan at the exact selected instant. Its `displayFact` gate rejects the same-day hourly sample when no condition point is present and the sample time differs from the selected minute. Temperature and wind then become unavailable; daily high/low remain sourced from the daily summary.
- `CanonicalForecastProjection.project()` selects the nearest fact within the selected local date. `MainActivity` rebuilds its presenter when the active catalog generation changes. The observed gap requires no provider request, canonical schema, or cache-policy change.

## Production boundary

After approval and activation, candidate files are `presentation/scene/SemanticScenePlan.kt`, `presentation/CatalogTimelinePresentation.kt`, `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`, and the smallest directly affected presentation/UI semantics. No app code is changed by this planning cycle. Provider transport, catalog storage, fixture data, refresh controls, daily aggregation, new weather artwork, and the deprecated app are excluded.

## Functional invariants

- A source point retains its exact valid time. A representative display cell is not a measured minute-level observation or an event-onset time. The selected `Instant` and playhead remain unchanged through scene evaluation, date rollover, and scale animation.
- Supplied zero, missing, empty, unsupported, isolated, and sparse values keep distinct meanings. Probability, amount, rate, condition, temperature, and wind are independent signals. A missing condition does not become a dry or clear condition claim.
- The final supported point may represent only its own local date under the proposed rule. The next date owns midnight; do not carry a fact across the date boundary or change another date's high/low.
- Preserve source/provenance truth, readable text and accessible meaning, timeline responsiveness, gesture ownership, city scene layering, and SG.4's separate eligible interior missing-condition rule.

## Proposed bounded behavior

Subject to the board decision, extend a local date's final supported hourly point cell through that date's exclusive midnight boundary, with its original source time disclosed. Apply the same point-cell rule to each shared scene point track, including condition, cloud, and precipitation chance. If the final point or individual field is missing, retain its unavailable state; do not substitute the previous hour. At midnight, switch to the next date's own supported point. Make temperature and wind text select their supported hourly fact independently of condition-track presence. Keep precipitation chance tied to its own point. Leave other midpoint, sparse-gap, and carry behavior unchanged.

## Steps

1. Review the drafted behavior board for 11:00, 11:29, 11:30, 11:36, 11:59, and midnight, plus genuinely missing, independently missing, sparse, and DST examples. Review the drafted UI artwork for supplied, unavailable, and midnight-switch readouts/scenes. Revise as needed, then obtain and record owner approval and any constraints before plan finalization.
2. Reconcile the approved boundary rule with SG.1 and SG.4 in `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md`; inspect the clean branch's source and capture the actual installed pre-change state.
3. After activation, implement only the approved point-cell boundary and field-independent text projection. Preserve selected-time and source-time disclosures.
4. Inspect source values and output at the named boundary instants and missing-field states. Capture scoped installed Pixel 3 XL screenshots and direct scrub/scale observations; document responsiveness, gesture ownership, readability, exact commands, and NOT RUN states. Add a compact viewport only if the approved artwork requires it.
5. Run `python scripts/dev.py workflow`, `contract`, `build`, `run`, and `screenshot` as applicable. Run automated tests only with separate owner authorization. Inspect `git diff --check` and the complete diff; close only when the approved exit is evidenced.

## Acceptance/evidence

- At 11:36 and 11:59 PM on a date with supplied 11 PM values, scene and text show that date's applicable supplied temperature, precipitation chance, wind, and condition with the 11 PM source time; the displayed selected time remains exact. Midnight uses the next date's own point. Daily high/low and timeline meaning remain correct.
- A genuinely absent final-hour point and individually missing condition, probability, temperature, or wind remain honestly unavailable. No cross-date carry, invented zero, continuous source coverage, or exact event time is claimed.
- Evidence target: `.codex/test-artifacts/115-sg-missing-eleventh-hour-data/README.md` with before/after installed captures, source-value comparison, scoped interaction observations, exact commands, and limitations. Closeout target: `.codex/history/115-sg-missing-eleventh-hour-data.md`; archived plan: `.codex/history/plans/115-sg-missing-eleventh-hour-data.md`. RTL, TalkBack, and broad system matrices belong to dedicated TEST work.

## Risks / assumptions

- **Owner decision required:** Extending the last cell to midnight conflicts with SG.1's current half-hour limit. This draft is not implementation authority until the objective-specific boards and corresponding specification amendment are approved and recorded.
- **Verified:** Florence's provider response included the relevant hourly values and the installed view had the gap. **Assumption to recheck:** The same failure occurs on the clean branch build, which lacks the original worktree's uncommitted P.2.21 UI edits.
- The edge-cell change affects condition imagery as well as probability; the presenter adjustment must not turn an absent condition into a supplied claim.
- Difficulty **6/10**: narrow code area but an explicit semantic contract change, coupled scene/text projection, and installed boundary review. Cheapest credible implementation model: **gpt-6.1-sol**; fallback **gpt-6-astra** if board/spec reconciliation remains ambiguous or a wider cross-field support conflict appears. This compares model fit only, not pricing.

## Out of scope

Visible refresh UI, provider retries, catalog refill/merging, location selection, daily-high placement, general interpolation, new event mappings or artwork, full system acceptance, and unrelated timeline changes.
