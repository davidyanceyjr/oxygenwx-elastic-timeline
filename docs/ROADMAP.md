# Oxygen Time Lab — scoped roadmap

Roadmap format and status rules: [`ROADMAP_SPECIFICATION.md`](ROADMAP_SPECIFICATION.md).
Product and semantic boundaries: [`SPECIFICATION.md`](SPECIFICATION.md).

## Cross-cutting hard rule

Every visual enhancement must preserve timeline responsiveness, gesture predictability, and readability, as defined in `docs/SPECIFICATION.md`. Each visual cycle that changes or reviews visual behavior must include an acceptance check for all three.

## G — Roadmap governance

### G.1 — Adopt and apply the roadmap specification — DONE

**Objective/scope:** Define the lightweight roadmap format and migrate every existing Oxygen Time Lab leaf slice to it. Documentation only; leave application behavior unchanged.
**Depends on:** None.
**Exit:** `docs/ROADMAP_SPECIFICATION.md` defines status, required slice fields, evidence, and failure/replanning rules; every roadmap leaf slice conforms; `AGENTS.md` identifies the roadmap specification as authority; `git diff --check` passes.
**Evidence:** `.codex/history/008-roadmap-specification.md`; `.codex/history/plans/008-roadmap-specification.md`; `.codex/test-artifacts/008-roadmap-specification/README.md`.

### G.2 — Adopt applicable oxygenWX skills and agent rules — DONE

**Objective/scope:** Bring the reusable oxygenWX planning, review, execution, context-audit, history-compaction, and model-rating skills into this repository's `.agents/skills/`, adapted to Oxygen Time Lab authorities and constraints. Adapt the project `AGENTS.md` with relevant cycle, architecture, verification, and UI rules. Do not change application source or the sibling oxygenWX checkout.
**Depends on:** G.1.
**Exit:** The selected skill set is installed with valid skill metadata and no production-only assumptions; `AGENTS.md` describes this project's actual workflow and nonnegotiable boundaries; skill validation and `git diff --check` pass; adoption evidence and limitations are recorded.
**Evidence:** `.codex/history/009-agent-skills-ruleset.md`; `.codex/history/plans/009-agent-skills-ruleset.md`; `.codex/test-artifacts/009-agent-skills-ruleset/README.md`.

### G.3 — Adopt the Oxygen experience specification — DONE

**Objective/scope:** Verify and adopt `docs/OXYGEN_EXPERIENCE_SPECIFICATION.md` as the experience-level companion to `docs/SPECIFICATION.md`; reconcile current-tree discrepancies and stale README claims. Documentation only; no application behavior changes.
**Depends on:** G.1 and G.2.
**Exit:** The authority relationship is explicit, the proposal's status reflects adoption without claiming unverified acceptance, current behavior discrepancies and owner decisions are recorded, stale README behavior claims are corrected, and documentation/workflow checks plus `git diff --check` pass.
**Evidence:** `.codex/history/022-experience-spec-adoption.md`; `.codex/history/plans/022-experience-spec-adoption.md`; `.codex/test-artifacts/022-experience-spec-adoption/README.md`.

### G.4 — Ingest the Living Atmosphere Engine proposal — DONE

**Objective/scope:** Reconcile OXY-ATM-001 against controlling prototype and experience authorities and the current source tree; adopt it as a subordinate architecture proposal for roadmap planning; add bounded ATM/ENV follow-ups. Documentation and cycle records only.
**Depends on:** G.3.
**Exit:** Authority precedence and adoption limits are explicit; every cited source path is compared with the proposal's baseline; conflicts and missing evidence are recorded; proposed ATM/ENV slices use this roadmap's leaf format; documentation checks pass without application changes.
**Evidence:** `.codex/history/023-atmosphere-spec-intake.md`; `.codex/history/plans/023-atmosphere-spec-intake.md`; `.codex/test-artifacts/023-atmosphere-spec-intake/README.md`.

## P.1 — Standalone elastic time tape

### P.1.1 — Implement the isolated timeline prototype — DONE

**Objective/scope:** Establish the standalone Android Compose scaffold, deterministic canonical-unit forecast fixture, typed presentation, pure time projection, atmospheric scene, and core time-tape interactions without changing oxygenWX.
**Depends on:** None.
**Exit:** The isolated app and core timeline implementation exist, architecture/isolation checks are recorded, and remaining installed acceptance is tracked separately.
**Evidence:** `.codex/history/001-elastic-timeline-prototype.md`. Cycle 001 explicitly records Android build, install, and device evidence as outstanding at that time; see P.1.2 for the later launch baseline.

### P.1.2 — Install and record the device baseline — DONE

**Objective/scope:** Build, install, launch, and capture the existing standalone app on the Pixel 3 XL; record representative timeline states without changing application source.
**Depends on:** P.1.1.
**Exit:** Installed foreground app is confirmed and actual device screenshots plus reproduction/device details are recorded. This exit does not imply full accessibility or environment acceptance.
**Evidence:** `.codex/history/002-p1-pixel-install.md`, `.codex/history/007-visual-baseline.md`, and local captures under `.codex/test-artifacts/002-p1-pixel-install/` and `.codex/test-artifacts/007-visual-baseline/` (including the later hourly long-press capture documented in that folder's README).

### P.1.3 — Run Android unit tests and lint — DONE

**Objective/scope:** Run the app's debug unit-test and lint tasks on an Android host; diagnose only failures in those tasks.
**Depends on:** P.1.1.
**Exit:** `:app:testDebugUnitTest` and `:app:lintDebug` both pass, or a reproducible blocker with command output and affected scope is recorded; do not mark DONE on an unexplained failure.
**Evidence:** `.codex/history/010-android-host-checks.md`; logs and host details under `.codex/test-artifacts/010-android-host-checks/`.

## P.2 — Owner review, visual polish, and installed acceptance

### P.2.1 — Uniform hourly scale and selected-time label — DONE

**Objective/scope:** Replace localized focus-day expansion with a uniform hourly scale and magnify the selected day/hour label while keeping it centered at the playhead.
**Depends on:** P.1.1.
**Exit:** Daily/hourly labels and ticks show the uniform scale and selected-time cue in installed captures; selected time remains authoritative through scale animation.
**Evidence:** `.codex/history/003-time-label-magnification.md`; captures under `.codex/test-artifacts/003-time-label-magnification/`.

### P.2.2 — Subtle timeline playhead — DONE

**Objective/scope:** Keep the selected position clear with a small axis marker while making the selected day/hour label the primary cue.
**Depends on:** P.2.1.
**Exit:** Installed capture shows the axis marker at the selected position without a full-height playhead obscuring the scene or labels.
**Evidence:** `.codex/history/004-subtle-playhead.md`; `.codex/test-artifacts/004-subtle-playhead/pixel-3xl.png`.

### P.2.3 — Main-display day navigation and realignment — DONE

**Objective/scope:** Shift selected time by 24 hours on a main-display horizontal swipe while preserving hour-of-day, and realign by double-tap within fixture bounds.
**Depends on:** P.2.1.
**Exit:** On-device left/right swipes and double-tap produce the specified selected-time changes, with actual outcome recorded.
**Evidence:** `.codex/history/005-main-display-navigation.md`; captures under `.codex/test-artifacts/005-main-display-navigation/`.

### P.2.4 — Remove visible hints and expose the scale action — DONE

**Objective/scope:** Remove prototype instructional/mode-control rows and the NOW button; retain direct gestures, double-tap realignment, and the timeline's screen-reader scale action.
**Depends on:** P.2.3.
**Exit:** Installed UI has no removed visible controls, and source/semantics expose the daily/hourly custom accessibility action. Manual TalkBack acceptance remains a separate slice.
**Evidence:** `.codex/history/006-remove-timeline-hints.md`; `.codex/test-artifacts/006-remove-timeline-hints/pixel-3xl.png`.

### P.2.5 — Verify timeline gesture arbitration and time stability — DONE

**Objective/scope:** On the Pixel 3 XL, review immediate drag versus stationary hold, hourly scrubbing, release/collapse, and selected-time stability; assess responsiveness, gesture predictability, and readability. Fixes are out of scope for this evidence-gathering slice.
**Depends on:** P.1.2, P.2.1, P.2.4, and P.2.5a.
**Exit:** Each gesture is exercised and its observed result recorded with screenshots; selected timestamp continuity and all three cross-cutting visual-quality checks receive an evidence-backed pass/fail disposition. Confirmed defects get separate implementation slices.
**Evidence:** Original evidence and blocker investigation: `.codex/history/010-gesture-acceptance.md`, `.codex/test-artifacts/010-gesture-acceptance/`, and `.codex/test-artifacts/011-blocker-investigation/README.md`. Recheck: `.codex/test-artifacts/012-gesture-acceptance-recheck/README.md` and `.codex/history/012-gesture-acceptance-recheck.md`.

### P.2.5a — Align forecast time label with selected hourly tick — DONE

**Objective/scope:** Make the forecast summary's displayed hour use the same nearest-hour convention as the hourly tape's selected tick, while preserving fractional selection and interpolated forecast values during a drag.
**Depends on:** P.2.1.
**Exit:** On the installed Pixel 3 XL, fractional-hour positions no longer show different hours in the forecast summary and selected tape label; after release/collapse and stationary re-entry, the same rounded selected hour remains visible. The change does not quantize selection during movement or alter interpolated values.
**Evidence:** `.codex/history/011-hour-label-alignment.md`; `.codex/test-artifacts/011-hour-label-alignment/`.

### P.2.6 — Review readability at large text and constrained layouts — DONE

**Objective/scope:** Review representative large system font, landscape/small-screen layout, and bright/dark sky contrast on the installed app. Fixes are out of scope for this evidence-gathering slice.
**Depends on:** P.1.2.
**Exit:** Actual screenshots for each condition are reviewed; essential forecast values and timeline labels are readable and unclipped, with findings and any confirmed defect ownership recorded. Confirmed defects get separate implementation slices.
**Evidence:** Prior findings and recovery: `.codex/history/013-responsive-readability.md`, `.codex/history/014-responsive-readability-recovery.md`, and `.codex/history/014-landscape-high-low.md`; final fresh installed review: `.codex/history/015-responsive-readability-review.md` and `.codex/test-artifacts/015-responsive-readability-review/`.

### P.2.12 — Restore Android build host and complete P.2.6 evidence — DONE

**Objective/scope:** Provision official Android SDK command-line tools and required SDK packages on the current host, build/install the current prototype on the Pixel 3 XL, and complete the missing exact-time and continuous-contact gesture evidence for P.2.6. Evidence and host setup only; no app changes.
**Depends on:** None.
**Exit:** A fresh build succeeds with supported tooling; required installed captures show the exact bright/night fixture times; immediate drag and continuous hold-then-drag outcomes, selected-time continuity, and all P.2.6 condition dispositions are recorded. If a host/device/tool blocker prevents the exit, record it with command output and preserve P.2.6 as BLOCKED.
**Evidence:** `.codex/history/014-responsive-readability-recovery.md`; `.codex/test-artifacts/014-responsive-readability-recovery/`.

### P.2.11 — Preserve high/low values in landscape at large font — DONE

**Objective/scope:** Keep the forecast high/low values visible as text in the compact landscape layout at 1.3 system font scale; they are absent from the installed capture. Preserve selected-time authority, timeline responsiveness, predictable gesture ownership, accessible text, and DEMO DATA semantics. Review compact landscape and 1.3 font scale; record RTL and reduced-motion applicability and verify they remain unaffected if layout/animation behavior changes.
**Depends on:** None.
**Exit:** Actual installed Pixel 3 XL captures show both high/low values as readable text alongside the date, heading, other essential forecast values, DEMO DATA label, and timeline labels at compact landscape and 1.3 font scale. Responsiveness, gesture ownership, and readability receive evidence-backed dispositions; RTL and reduced-motion applicability are recorded.
**Evidence:** Finding: `.codex/history/013-responsive-readability.md`; installed captures and review: `.codex/test-artifacts/014-landscape-high-low/README.md`; outcome: `.codex/history/014-landscape-high-low.md`.

### P.2.7 — Accept the TalkBack scale action — DONE

**Objective/scope:** Manually traverse the timeline with TalkBack and invoke its daily/hourly custom action; verify understandable semantics and selected-time continuity.
**Depends on:** P.2.4.
**Exit:** TalkBack reaches the timeline, announces useful control/state semantics, invokes the daily/hourly scale action in both directions, and records the spoken label, visible scale, and selected-time continuity after each action. Any confirmed defect gets a separate implementation slice.
**Evidence:** Prior attempts: `.codex/test-artifacts/016-talkback-timeline/README.md`, `.codex/history/016-talkback-timeline.md`, `.codex/test-artifacts/017-talkback-timeline-recheck/README.md`, and `.codex/history/017-talkback-timeline-recheck.md`. Installed acceptance and limitations: `.codex/test-artifacts/019-talkback-scale-action/README.md` and `.codex/history/019-talkback-scale-action.md`.

### P.2.13 — Add a screen-reader forecast browser — DONE

**Objective/scope:** Provide a TalkBack-actionable, linear forecast browser with daily/hourly rows sourced from the same forecast projection and selected-time state as the visual timeline. Keep decorative ticks and animation out of the spoken data path; do not add a visible scale-control row.
**Depends on:** P.2.4.
**Exit:** The timeline exposes a standard accessibility click plus a named custom action to open the browser; daily and hourly rows expose their date/time and forecast values, identify the selected row, and selecting a row updates the existing timeline and summary timestamp. Installed Pixel 3 XL evidence shows the browser in daily/hourly states and the hourly list scrolled to the selected Sunday, Oct 25, 12 PM row. This does not replace the separate manual TalkBack scale-action acceptance in P.2.7.
**Evidence:** `.codex/test-artifacts/018-talkback-forecast-browser/README.md`, installed screenshots and accessibility hierarchy; closeout: `.codex/history/018-talkback-forecast-browser.md`.

### P.2.8 — Verify reduced-motion behavior — DONE

**Objective/scope:** Review the installed app with the platform reduced-motion/animation setting enabled and record the bounded behavior needed for this prototype. This slice gathers acceptance evidence; any implementation fix gets its own slice.
**Depends on:** P.1.2.
**Exit:** The relevant system setting and app response are recorded; selected time and scale remain understandable without disorienting motion, and any required change has its own bounded slice and evidence.
**Evidence:** `.codex/test-artifacts/020-reduced-motion/README.md` and `.codex/history/020-reduced-motion.md`.

### P.2.9 — Verify RTL and close installed acceptance — DONE

**Objective/scope:** Review timeline chronology, gesture directions, and labels under a system RTL locale. Reconcile the installed acceptance evidence; use existing TalkBack acceptance without repeating TalkBack tests in this cycle, per the product owner's direction to revisit before final production release.
**Depends on:** P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11.
**Exit:** Installed RTL screenshots and direct-touch observations establish that chronology, gesture directions, fixture bounds, and visible labels remain understandable. Existing P.2.7/P.2.13 TalkBack outcomes are linked without retest; RTL-specific spoken behavior is recorded as unverified per the product owner's decision to revisit TalkBack before final production release. The required P.2 evidence and limitations are reconciled, the three cross-cutting visual-quality checks are recorded, and residual scope is explicitly dispositioned. Confirmed defects get separate implementation slices.
**Evidence:** Cycle 021 (`021-p2-acceptance-closure`): `.codex/test-artifacts/021-p2-acceptance-closure/README.md` and installed captures; closeout: `.codex/history/021-p2-acceptance-closure.md`. Existing P.2 evidence is linked from the history record.

## P.3 — Integration feasibility spike — DEFERRED

### P.3.1 — Assess standalone timeline integration feasibility — DEFERRED

**Objective/scope:** Evaluate a future integration with oxygenWX's `Now -> Hourly -> Daily -> Details` navigation, normalized forecast presentation, provenance, and gesture ownership. No production integration or navigation edits are included.
**Depends on:** Explicit owner approval to begin integration design.
**Exit after re-entry:** An owner-reviewed design documents pager/timeline gesture ownership, typed normalized presentation binding, source/provenance semantics, and a bounded integration plan that preserves existing network/cache/provider flows.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The oxygenWX owner explicitly authorizes an integration feasibility design and gesture-ownership review.

## P.4 — Production-release presentation cleanup — DEFERRED

### P.4.1 — Remove experimental branding for production release — DEFERRED

**Objective/scope:** Remove `OXYGEN / TIME LAB` experimental branding from a production-release presentation.
**Depends on:** An approved production integration and release candidate.
**Exit after re-entry:** Release-candidate screens use approved product branding, and installed evidence records all affected surfaces.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** A production-release cycle is explicitly authorized for the integrated application.

### P.4.2 — Replace DEMO DATA only with accurate provenance — DEFERRED

**Objective/scope:** Replace the synthetic-data label only after the app presents production forecast data and accurate source/provenance information.
**Depends on:** P.3.1 and a production data path with verified provenance.
**Exit after re-entry:** Installed release UI identifies the actual data/source status accurately, with semantics and source evidence reviewed.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** Production forecast data and verified provenance are available in an explicitly approved integration cycle.

### P.4.3 — Define production timeline reset behavior — DEFERRED

**Objective/scope:** Decide how timeline reset/realignment fits production navigation and interaction ownership before integration.
**Depends on:** P.3.1.
**Exit after re-entry:** An owner-approved interaction contract states reset gesture/action, relationship to the outer pager, and accessibility semantics before implementation begins.
**Evidence:** On re-entry, assign the next chronological cycle ID and use `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner authorizes production navigation design following the integration feasibility review.

## ATM — Living atmosphere engine

### ATM.0 — Capture installed pre-engine atmosphere baseline — DONE

**Objective/scope:** Establish the installed app baseline before renderer changes by consolidating the existing Pixel 3 XL visual, gesture, reduced-motion, RTL, and accessibility reviews. No new capture cycle is needed.
**Depends on:** G.4.
**Exit:** Existing installed evidence covers daily/hourly navigation, main-view swipe and double-tap Now, bright/night and large-font landscape readability, reduced motion, RTL, TalkBack, responsiveness, gesture predictability, and forecast readability; documented limitations remain explicit.
**Evidence:** Daily/hourly and main-view navigation: `.codex/history/005-main-display-navigation.md`, `.codex/history/012-gesture-acceptance-recheck.md`, and `.codex/history/007-visual-baseline.md`, with captures under the matching `.codex/test-artifacts/` directories. Bright/night and landscape large-font readability: `.codex/history/014-landscape-high-low.md` and `.codex/history/015-responsive-readability-review.md`, with matching artifacts. TalkBack: `.codex/history/018-talkback-forecast-browser.md` and `.codex/history/019-talkback-scale-action.md`, with matching artifacts. Reduced motion: `.codex/history/020-reduced-motion.md` and `.codex/test-artifacts/020-reduced-motion/`. RTL and final P.2 disposition: `.codex/history/021-p2-acceptance-closure.md` and `.codex/test-artifacts/021-p2-acceptance-closure/`. Known limits are recorded in those histories, including qualitative responsiveness and no RTL-specific TalkBack review.

### ATM.1 — Pure typed scene projection and static fallback — DONE

**Objective/scope:** Add a pure typed mapper from existing `SkyFrame`, selected time and explicit effect/environment inputs to immutable scene state, with a coherent static fallback and an adapter seam. Preserve missingness and distinguish condition identity from rain probability. Do not change visible layout, timeline physics, or gestures.
**Depends on:** ATM.0.
**Exit:** Scene state derives only from typed inputs; absent values remain absent; rain probability cannot become rain intensity; `selectedHour`, forecast facts, accessibility text, and gesture ownership remain unchanged; the current renderer still provides the accepted baseline. Focused tests/build checks are planned subject to the repository's test authorization rule.
**Evidence:** Cycle 024 (`024-atm1-scene-projection`): `.codex/test-artifacts/024-atm1-scene-projection/README.md`; closeout: `.codex/history/024-atm1-scene-projection.md`; archived plan: `.codex/history/plans/024-atm1-scene-projection.md`.

### ATM.2 — Sky, light and illustrative celestial layer — DONE

**Objective/scope:** Add time-aware sky palettes, diffuse light, and illustrative sun/moon artwork with a static/reduced-motion path. No solar ephemeris or moon-phase claims.
**Depends on:** ATM.1.
**Exit:** Clear/cloudy/day/dusk/night transitions are continuous across fixture samples; essential text remains readable; installed Pixel 3 XL checks cover daily/hourly, bright/dark, portrait/landscape, default/large font, RTL and reduced-motion states; timeline responsiveness and gesture ownership remain accepted.
**Evidence:** Cycle 025 (`025-atm2-sky-light-celestial`): `.codex/test-artifacts/025-atm2-sky-light-celestial/README.md`; closeout: `.codex/history/025-atm2-sky-light-celestial.md`; archived plan and approved concept: `.codex/history/plans/025-atm2-sky-light-celestial.md` and `.codex/history/plans/025-atm2-sky-light-celestial-concept.png`.

### ATM.3a — Consolidate ATM.3 evidence and dependency-order corrective work — DONE

**Objective/scope:** Reuse completed ATM.3 installed evidence, capture the missing 7:30 PM fractional sample and one corrected 8 PM sample after discovering a cycle-030 artifact mismatch, then map source and acceptance dependencies. Evidence and planning only; no application changes.
**Depends on:** None. This bounded audit can proceed while ATM.3 is blocked.
**Exit:** Prior evidence is cited by exact path; the two targeted Pixel 3 XL captures disposition the missing fractional and 8 PM states with limitations; device settings are restored; and a source/acceptance dependency map recommends the first corrective slice. ATM.3 remains BLOCKED until its complete exit is met.
**Evidence:** Cycle 031 (`031-atm3-blocker-confirmation`): `.codex/test-artifacts/031-atm3-blocker-confirmation/README.md`; closeout: `.codex/history/031-atm3-blocker-confirmation.md`; archived plan: `.codex/history/plans/031-atm3-blocker-confirmation.md`.

### ATM.3 — Deterministic multi-depth cloud renderer — DONE

**Objective/scope:** Complete the deterministic far/mid cloud renderer driven by supported cloud-cover inputs and selected-time phase. Correct the recorded selected-time release/observability, large-font clipping, and RTL rendering findings needed for its installed acceptance. No precipitation or terrain redesign.
**Depends on:** ATM.2.
**Exit:** Cloud visuals respond smoothly and deterministically to supported cloud-cover and selected-time inputs without reseeding or popping at sample boundaries; phase changes are perceptible but restrained; the static path is legible. The exact selected moment remains authoritative and is consistently disclosed in the forecast summary and timeline semantics after release. Installed Pixel 3 XL review covers clear/overcast and daily/hourly states, compact/1.3 font, RTL and reduced motion; it confirms no clipping, coherent RTL chronology, timeline responsiveness, gesture predictability, accessible selected-time disclosure, and readable forecast facts.
**Evidence:** Cycles `026-atm3-cloud-renderer` through `031-atm3-blocker-confirmation` are recorded in `.codex/history/`; installed evidence is in `.codex/test-artifacts/026-atm3-cloud-renderer/` through `.codex/test-artifacts/031-atm3-blocker-confirmation/`. Cycle 030 recorded 1.3-font clipping and a zero-motion idle pair; cycle 031 corrected the 8 PM capture and added a fractional sample. At that point, RTL still rendered LTR, release hid and rounded the selected time, and cloud phase movement was visually unconfirmed. Cycle `032-atm3-corrective-acceptance` resolved these findings and completed the selected-time, large-font, RTL, cloud-phase, reduced-motion, and TalkBack acceptance. The owner-confirmed TalkBack pass is recorded without a transcript. Evidence: `.codex/test-artifacts/032-atm3-corrective-acceptance/README.md`; closeout: `.codex/history/032-atm3-corrective-acceptance.md`; archived plan: `.codex/history/plans/032-atm3-corrective-acceptance.md`.

### ATM.3b — Deterministic hourly precipitation demo data — NEXT

**Objective/scope:** Expand the deterministic DEMO DATA fixture and typed hourly forecast projection with precipitation inputs for visual design: stepped hourly chance values, explicit rain and snow identities, and explicit hourly precipitation totals where supplied. Keep chance and total as separate nullable/typed signals; never derive totals or snow from chance or temperature. Use a generic precipitation-chance label for both rain and snow. No renderer, timeline, gesture, or live-provider changes.
**Depends on:** ATM.3.
**Exit:** Normal hourly fixture navigation exposes dry and multiple precipitation-chance levels, rain hours with differing explicit totals, a missing-total case, and an explicitly typed snow case. Projection preserves chance, type, amount, and missingness independently; the visible chance label is accurate for rain and snow; the fixture remains deterministic and labeled DEMO DATA. No precipitation rendering or timeline behavior changes.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.4 — Bounded rain and snow depiction — BLOCKED

**Objective/scope:** Add clearly visible precipitation artwork driven by typed hourly condition, chance, and optional amount. Chance selects stepped ambient moisture coverage; it does not select falling-rain heaviness. Explicit hourly totals may control illustrative rain heaviness; missing totals remain unknown. Snow requires an explicit typed condition or clearly labeled synthetic fixture; never infer it from temperature.
**Depends on:** ATM.3 and ATM.3b.
**Exit:** Rain falls visibly across the atmospheric view; stepped chance states produce distinct, understandable moisture coverage; explicit hourly totals distinguish illustrative rain heaviness without implying measured intensity beyond the DEMO DATA fixture. Explicit snow has a coherent, distinct depiction. Static/reduced-motion states remain understandable, and forecast facts and timeline remain readable. Paired installed Pixel 3 XL captures show absent/present effects and representative chance/amount steps at normal phone viewing size; focused checks cover the typed mappings and relevant timeline interactions. Cross-app RTL, TalkBack, font-scale, and other accessibility/layout matrices are separate cycles.
**Blocker:** The owner rejected cycle 033 because its precipitation was not noticeable in installed screenshots. Its faint, sparse edge marks do not meet the visual purpose, and its implementation and captures are not accepted. ATM.3b supplies the mock inputs needed for a new bounded ATM.4 plan; the visual blocker remains until a later attempt meets ATM.4's exit.
**Evidence:** Failed attempt and captures: `.codex/history/033-atm4-bounded-precipitation.md` and `.codex/test-artifacts/033-atm4-bounded-precipitation/README.md`. ATM.3 baseline closure: `.codex/history/032-atm3-corrective-acceptance.md`. The owner will schedule app-wide accessibility/layout checks separately.

### ATM.5 — Fog and wind effect primitives — PLANNED

**Objective/scope:** Add reusable haze/turbulence primitives only as clearly labeled preview effects until supported typed visibility and wind-direction inputs exist. No fabricated live fog, wind direction, or visibility claims.
**Depends on:** ATM.3.
**Exit:** Missing inputs produce no claimed weather effect; preview states are clearly identified as illustrative; effects do not obscure forecast text or timeline and have static fallbacks. Installed Pixel 3 XL review covers preview/absent-input states, compact/large font, RTL and reduced motion.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.6 — Restrained storm and night refinements — PLANNED

**Objective/scope:** Add muted storm and night artwork using supported condition/time cues. No alerts, solar/moon accuracy claims, or flashes behind critical text.
**Depends on:** ATM.5.
**Exit:** Storm/night scenes remain comfortable and legible in static/reduced-motion modes; no strobing or text occlusion occurs; installed Pixel 3 XL review covers storm/night, compact/large font, RTL and reduced motion.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ATM.7 — Effects policy, lifecycle and performance gate — PLANNED

**Objective/scope:** Add a single effects/quality policy, lifecycle-aware animation coordination, static fallback, and bounded performance review. Environment work is gated on this slice.
**Depends on:** ATM.6.
**Exit:** `NONE`, `SUBTLE`, `FULL`, reduced motion and background/offscreen behavior meet their stated contracts; Pixel 3 XL frame/gesture/lifecycle evidence is recorded; all existing P.2 interactions and the three visual hard rules remain accepted. If performance evidence cannot be captured, record the blocker and keep ENV work ineligible.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

## ENV — Interchangeable environment packs

### ENV.1 — Environment contract and Neutral baseline — PLANNED

**Objective/scope:** Define immutable environment metadata and migrate existing scenic geometry behind a Neutral pack. No persistent selector or new screen.
**Depends on:** ATM.7.
**Exit:** Neutral appearance retains accepted baseline; pack selection changes scenery only and cannot change forecast values, selected time, accessibility labels, gestures, or timeline physics; paired installed Pixel 3 XL evidence covers compact/large font, RTL and reduced motion.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ENV.2 — Alpine environment pack — PLANNED

**Objective/scope:** Add Alpine scenic geometry and palette using the shared atmosphere renderer. Snow visuals require an actual typed snow state or labeled preview.
**Depends on:** ENV.1.
**Exit:** Alpine clear/rain/night states and any permitted snow preview are coherent; forecast facts and all interaction/accessibility behavior match Neutral; paired installed Pixel 3 XL states cover compact/large font, RTL and reduced motion.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ENV.3 — Coastal environment pack — PLANNED

**Objective/scope:** Add Coastal scenic geometry and palette using the shared atmosphere renderer. No location lookup or geographic truth claims.
**Depends on:** ENV.1.
**Exit:** Coastal clear/rain/storm/night states are distinct while forecast facts, timeline, gestures, and accessibility remain unchanged; installed Pixel 3 XL states cover compact/large font, RTL and reduced motion and the render budget is accepted on device.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ENV.4 — Cross-pack transitions and acceptance matrix — PLANNED

**Objective/scope:** Validate and implement only the bounded transition/fallback needed to move among Neutral, Alpine and Coastal, plus the shared acceptance matrix.
**Depends on:** ENV.2 and ENV.3.
**Exit:** Pack transitions never change selected time, forecast facts, provider state, gestures, or accessibility; reduced-motion/static and lower-quality paths work; installed Pixel 3 XL captures cover compact/large font, RTL, daily/hourly and representative weather states.
**Evidence:** At planning, assign the next chronological cycle ID and use its `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md`.

### ENV.5 — Additional environment packs — DEFERRED

**Objective/scope:** Consider Forest, Plains, Desert, or Urban only after Neutral/Alpine/Coastal acceptance. No commitment to implement all candidates.
**Depends on:** ENV.4.
**Exit after re-entry:** Owner-approved asset/reference plan and one specifically selected bounded pack slice with truthful weather mapping and the shared acceptance matrix.
**Evidence:** No evidence claimed while deferred; re-entry cycle receives its chronological `.codex/test-artifacts/<cycle-id>/`, `.codex/history/<cycle-id>.md`, and `.codex/history/plans/<cycle-id>.md` paths when authorized.
**Re-entry condition:** Explicit owner selection and approval of one additional pack after ENV.4 is complete.
