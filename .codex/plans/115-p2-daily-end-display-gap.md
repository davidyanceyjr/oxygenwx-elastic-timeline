# Cycle plan — 115-p2-daily-end-display-gap

State: PLANNED — FIRST DRAFT; BOARD AND OWNER DECISION PENDING
Roadmap: P.2.22
Objective: Reconcile the reported displayed-data gap in the final 30 minutes of each local date on the active-catalog timeline, without inventing forecast facts or changing the selected instant.

## Readiness and board gate

- `python scripts/dev.py workflow` reported `Workflow scaffold: OK` while `.codex/current.md` was IDLE. P.2.21 is DONE; the owner has kept SG.6 deferred. P.2.22 is the sole NEXT slice.
- **Pending:** An objective-specific behavior board must show the 23:00 hourly point, 23:29/23:30/23:59 boundary, next local 00:00, supported and missing values, source-time disclosure, and the selected-time/scene relationship. A UI artwork board must show the affected daily and hourly display states. One combined board may cover both. Product-owner approval and constraints must be recorded here before this plan is finalized or activated.
- The SG.1 representative-display rule in `docs/OXYGEN_SCENE_GENERATION_SPECIFICATION.md` currently limits a last hourly cell to a half-hour past its point. Any proposed continuation through midnight or other change to that rule needs a bounded owner decision and a matching specification update before implementation.

## Verified baseline and open decision

- `SemanticScenePlan.kt` forms the last point's representative cell from the 23:00 slot through 23:30 on a regular day, clipped to the local-day end. Its `evaluate` path can therefore return no point for 23:30–23:59 while its light interval still resolves.
- `CanonicalForecastProjection.kt` chooses the nearest supplied fact on the selected local date for its text-facing `factSample`; this may continue showing a 23:00 fact during the final half-hour. The user reports that this difference produces erroneous displayed data. The exact installed symptom and the desired source disclosure have not yet been captured in this planning cycle.
- Open owner decision: choose the source-honest display rule at the daily boundary. The approved board must decide whether the last supplied point may remain a clearly labeled representative display after 23:30 or whether fact/scene presentation becomes explicitly unavailable there. It must define what 00:00 does, including a missing next-day point. Do not infer an hourly observation or exact weather transition from a continuation.

## Production boundary and order

1. Capture the current installed boundary state and confirm which text and scene elements disagree; record the active source, selected time, and source valid time.
2. Draft and obtain approval for the behavior and UI artwork boards. Reconcile the controlling SG.1 rule and any affected specification language before finalizing this plan.
3. Implement only the approved rule in the active-catalog projection/scene/display path. Likely affected files are `app/src/main/java/com/oxygen/weather/timeline/presentation/scene/SemanticScenePlan.kt`, `app/src/main/java/com/oxygen/weather/timeline/presentation/CanonicalForecastProjection.kt`, and the relevant active-catalog presentation/UI path. Exact edits depend on the approved rule and observed symptom.
4. Check the affected boundary states and directly affected timeline interactions on the installed Pixel 3 XL; record the captures, results, limitations, final diff, and closeout.

No implementation, board approval, specification change, or app test is claimed by this draft.

## Functional invariants and exclusions

- The selected `Instant` remains authoritative. Zoom and scale changes do not retarget it; day boundaries use the location's IANA timezone, including DST dates.
- Supplied values retain their actual valid time. Missing, unsupported, and out-of-window facts are not converted to zero, dry/clear, a new hourly record, or a claimed exact event time. An illustrative scene cannot create a meteorological fact.
- Preserve existing daily summaries/highs, hourly labels, date labels, event icons, divider, playhead, badge, central forecast facts, daily drag, hourly scrub, double-tap scale toggle, and readable forecast values.
- Do not resume SG.6, change the theme system, revise daily-drag/noon behavior, add a provider or field, or perform broad app-wide system acceptance in this slice.

## Acceptance and evidence target

- The installed active-catalog display is checked at 23:29, 23:30, 23:59, and next-day 00:00 for a date with a 23:00 supplied point, with the selected time and actual source time recorded. Check an absent/unsupported last point and a missing next-day point if the available catalog can expose them; otherwise mark those states unobserved rather than claiming device evidence.
- Check an applicable DST-short, DST-long, or repeated local-date boundary against the approved rule if available within the bounded source; record the precise applicability and any unobserved case.
- Captures and focused interaction notes go under `.codex/test-artifacts/115-p2-daily-end-display-gap/`. A build or preview alone cannot establish visual acceptance. No unit or instrumentation tests are authorized by this planning request; seek separate authorization before adding or running them.
- Before closeout, inspect `git diff --check` and the final diff. Record exact commands, observed results, and limitations in `.codex/history/115-p2-daily-end-display-gap.md` and archive this plan.

## Risks, assumptions, and model fit

- The code-level mismatch is verified; the user's precise visual error still needs installed capture. No cause beyond the inspected projection boundaries is asserted.
- A change that visually represents the 23:00 condition through midnight would alter the current SG.1 rule. The board and controlling specification must settle that semantic choice first.
- Implementation difficulty: **5/10**. The likely code surface is narrow, but source honesty, day/DST boundaries, and installed text/scene agreement add semantic and verification work.
- Relative model fit only: **GPT-6.1 Sol** is the least costly credible choice for this bounded correction after the board decision. Escalate to **GPT-6 Astra** if the approved rule requires cross-product or provider support semantics beyond this slice. No pricing or runtime billing comparison is claimed.
