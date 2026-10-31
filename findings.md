# Specification audit: prioritized resolution list

Reviewed the current working-tree versions of `docs/SPECIFICATION.md` and `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md`. Both had uncommitted edits during the audit. This file records findings only; it does not change either specification.

## Minimum-cycle resolution path

Resolve items 1–10 in **one bounded specification cycle**. Decide the related time-selection rules together, then align the controlling spec and its experience companion in the same cycle. Record the required behavior-board approval before finalizing that cycle, as `AGENTS.md` requires for text and documentation changes. The cycle can define acceptance criteria and evidence requirements; installed verification belongs to the later implementation cycles that exercise them. A second specification cycle is needed only if an owner decision changes the product boundary or new evidence reveals a conflict.

## Prioritized findings

1. **Define selection across daily and hourly modes.** A main-area tap in hourly mode must preserve the selected timestamp, while a daily drag release settles to a representative moment. The specifications do not say how these rules interact, so a scale change could silently move the selection to noon. Specify which actions preserve absolute time and which choose a day's representative time. Evidence: `docs/SPECIFICATION.md:21`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:85-87`.

2. **Define selection within data gaps.** Clamping to the first and last available timestamp does not resolve unavailable hours between them. Specify whether the playhead can rest on a gap, how navigation crosses one, and what the facts and scene show. Evidence: `docs/SPECIFICATION.md:23,39`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:76`.

3. **Define the successor reset reference.** Double-tap follows the active source's reference time, but the companion calls that the present forecast moment. Specify the reset target for live, replay, simulated, stale, and historical datasets, including a target outside available coverage. Evidence: `docs/SPECIFICATION.md:27`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:111`.

4. **Resolve a timeline tap before changing scale.** In hourly mode, “the time under the tap” can differ before and after collapse. Specify which scale maps the touch to time and whether the result is an exact hour or a representative day time. Resolve this alongside item 1. Evidence: `docs/SPECIFICATION.md:21`.

5. **Complete calendar-day swipe behavior.** DST overlap and skipped-time rules are explicit, but missing destination hours, missing days, and catalog edges are not. Specify the destination selection and clamping rules using the gap policy from item 2. Evidence: `docs/SPECIFICATION.md:27`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:240`.

6. **Set measurable acceptance criteria.** “Reliable” arbitration, readability, reduced motion, “dependable 60 fps,” battery drain, and discernible targets lack pass thresholds or measurement methods. Define applicable device states, measures, and acceptable results; leave actual installed results to the relevant implementation cycles. Evidence: `docs/SPECIFICATION.md:61`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:217-226`.

7. **Reconcile the alert release gate with scope.** Official alerts are a separate future subsystem, yet the companion includes actual severe-weather alerts in the requirements triggered by real-data introduction. State whether the alert requirement applies only when alerts are offered and what the app communicates before then. Evidence: `docs/SPECIFICATION.md:41-44`; `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:164-171`.

8. **Remove the reopened predecessor decision.** UX.5 asks whether the app should remain separate or redesign the original product, although the controlling spec already establishes this repository as the successor and excludes predecessor changes. Mark that decision resolved or remove the superseded text. Evidence: `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:289-294`; `docs/SPECIFICATION.md:3-9`.

9. **Separate durable requirements from dated context.** The adopted companion mixes binding requirements, proposals, historical observations, and a superseded development sequence. Sections 10 and 13 can be mistaken for current instructions. Label or move dated material so it cannot override the current roadmap and controlling spec. Evidence: `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:254-263,320-332`.

10. **Cut repetitive, non-decisional prose.** The purpose, principles, anti-goals, feature filter, and success definition repeat broad advice. Phrases such as “immersive but legible; interactive but predictable; ambitious but restrained” and “Keep complexity behind the surface” do not settle a concrete tradeoff. Keep language that gives reviewers an actionable rule; consolidate the rest. Evidence: `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md:25,52-63,296-318,334-336`.

The existing selected-time invariant, DST resolution rule, synthetic-data boundary, and distinction between illustration and measured weather provide a sound basis for these decisions.
