# Cycle plan — 015-p2-acceptance-closure

State: CLOSED
Roadmap: P.2.9
Objective: Verify RTL behavior on the installed prototype and produce an evidence-backed final disposition for P.2 installed acceptance.

## Scope

Evidence-only review of the current standalone prototype on the Pixel 3 XL reference device. Set an available system-wide RTL locale, capture daily and hourly timeline states, and exercise relevant direct-touch timeline interactions. Use portrait at the device's default font scale for the RTL review; record the exact locale and all device/settings conditions. Reconcile the P.2.5, P.2.6, P.2.7, P.2.8, P.2.11, and P.2.13 evidence into a final acceptance record without rewriting closed history. Per the product owner's direction, do not repeat TalkBack tests in this cycle: rely on the completed P.2.7 and P.2.13 evidence, and revisit TalkBack before final production release. Large-font/compact-layout and reduced-motion acceptance are assessed from their existing cycle evidence and limitations, not inferred from the default-scale RTL captures. Preserve screenshots, device/locale/settings details, interaction observations, and the final disposition.

No application source or behavior changes are in scope. If the RTL review confirms a defect, document it and propose a separate bounded roadmap slice; do not fix it in this cycle.

## Production boundary

May create or update only:

- `.codex/test-artifacts/015-p2-acceptance-closure/` for actual installed captures and findings.
- `.codex/history/015-p2-acceptance-closure.md` at closeout.
- `.codex/history/plans/015-p2-acceptance-closure.md` at closeout.
- `.codex/current.md` and this plan for cycle lifecycle.
- `docs/ROADMAP.md` to close P.2.9 only if its full exit is met, or to record an evidence-backed blocker or separately scoped confirmed follow-up.

No application source, production oxygenWX file, or unrelated roadmap item may change.

## Functional invariants

- Selected timestamp remains authoritative through scale changes and interactions; zoom does not change forecast time.
- Daily/hourly chronology, selected-row meaning, fixture bounds, and forecast values remain understandable in RTL.
- Gesture ownership remains predictable; timeline scrubbing remains responsive and forecast values and labels readable.
- The screen-reader forecast browser and scale action remain available with understandable semantics; decorative marks do not replace forecast facts.
- The DEMO DATA label and distinction between fixture values and observations remain honest.
- No network, location, alerts, caching, production integration, or change to the production navigation contract is introduced.

## Ordered steps

1. Read the current P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11 history and evidence records. Build a closure checklist from their recorded outcomes and limitations; do not promote unverified conditions to accepted.
2. Confirm the Pixel 3 XL baseline and current system locale, orientation, font scale, animation/reduced-motion state, and accessibility-service state. Record original settings so they can be restored.
3. Enable an available RTL system locale, record its exact name and readback, and install/run the current app if needed. In portrait at default font scale, capture actual device screenshots of the daily timeline, hourly timeline, daily forecast browser, and hourly forecast browser.
4. In RTL, exercise a day tap, immediate horizontal drag, stationary hold-then-drag, scale change, release/collapse, and re-entry. Scrub toward both fixture bounds where reachable and record the displayed endpoint and selected time. Observe chronology/direction, selected-time continuity, responsiveness, gesture ownership, and readable values/labels.
5. Do not enable or traverse TalkBack for this cycle. For P.2.7 and P.2.13, cite the existing evidence and record the product owner's direction that TalkBack works, no further testing is wanted now, and testing will be revisited before final production release. Do not claim RTL-specific spoken behavior was tested.
6. Restore the original locale and any other changed settings, verify readback, and preserve screenshots plus device/settings/interaction details in `.codex/test-artifacts/015-p2-acceptance-closure/README.md`.
7. Reconcile every required P.2 outcome as DONE or explicitly BLOCKED with its evidence reference. Record the three cross-cutting visual-quality dispositions, residual gaps, any confirmed defect and proposed separate slice, and the product owner's disposition of residual scope. Do not alter earlier cycle histories.
8. If P.2.9 exit is met, mark it DONE and write the closeout history and archived plan. If it is not met, record exact blocker/evidence, leave P.2.9 unresolved (BLOCKED when external/device conditions prevent progress), and do not claim final acceptance.

## Acceptance and evidence

P.2.9 is complete only when all of the following are recorded:

- Actual Pixel 3 XL RTL screenshots cover daily and hourly timeline states in portrait at default font scale. Direct-touch observations establish whether chronology, gesture directions, selected-time behavior, and visible forecast labels remain understandable. The exact device, Android version, locale, orientation, font scale, and relevant motion settings are stated.
- P.2.7 and P.2.13 remain supported by their existing TalkBack/browser evidence; no TalkBack testing is repeated here by product-owner direction. Record that RTL-specific spoken behavior remains unverified in this cycle and that TalkBack will be revisited before final production release.
- Selected-time continuity, fixture-bound behavior, timeline responsiveness, gesture predictability, and readable forecast values/labels each have an evidence-backed pass/fail disposition.
- The P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11 outcomes are each linked to their existing evidence and marked DONE or explicitly BLOCKED; limitations remain visible.
- Residual scope receives a product-owner disposition. Confirmed defects are identified as separate bounded follow-ups rather than silently included here.
- Any changed system setting is restored and read back.
- Actual installed captures and the review README are saved under `.codex/test-artifacts/015-p2-acceptance-closure/`; closeout is `.codex/history/015-p2-acceptance-closure.md`.

Planned verification: `python scripts/dev.py workflow` before and after the cycle; `python scripts/dev.py build` and `python scripts/dev.py run` as needed to install/launch the current app; installed screenshot capture and manual interaction/accessibility review. Run `git diff --check` and inspect the final diff at closeout. Do not run tests, lint, or contract checks unless the bounded review reveals a specific reason and the plan is revised before doing so.

## Risks and assumptions

- The Pixel 3 XL and a usable RTL locale remain available. If either is unavailable, record the exact host/device blocker and do not substitute a source-only or guessed screenshot for installed acceptance.
- RTL mirroring can change chronology and gesture interpretation; record actual outcomes rather than assuming layout direction implies correct behavior.
- Existing evidence covers different configurations and interaction methods. Treat each history's stated limitations as open unless this cycle has direct evidence that closes them.
- Changing system locale or accessibility settings can affect the host. Record initial state and restore/read back every setting changed by this review.
- A confirmed defect may require owner prioritization as a separate implementation slice; this evidence-only cycle does not authorize source changes.

## Out of scope

Application implementation or fixes; production integration; changes to oxygenWX; unrelated visual features; TalkBack re-testing in this cycle; broad testing on other devices; measured frame-time or contrast studies; rewriting prior history; inferring accessibility acceptance from screenshots; unit tests, lint, or contract checks without a revised plan.
