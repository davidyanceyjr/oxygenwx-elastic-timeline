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

### P.2.9 — Verify RTL and close installed acceptance — NEXT

**Objective/scope:** Review timeline chronology, gesture directions, labels, and accessible semantics under a system RTL locale. Then make the final installed-acceptance disposition from the existing P.2 evidence; do not add unrelated visual features.
**Depends on:** P.2.5, P.2.6, P.2.7, P.2.8, and P.2.11.
**Exit:** RTL screenshots and interaction observations establish chronological meaning and control semantics remain understandable. Each required gesture, readability, TalkBack, reduced-motion, and RTL outcome is DONE or explicitly BLOCKED with evidence and a product-owner decision on residual scope; the three cross-cutting visual-quality checks are recorded; any remaining follow-up is identified in the roadmap. Confirmed defects get separate implementation slices.
**Evidence:** Final RTL and acceptance-closure captures target `.codex/test-artifacts/015-p2-acceptance-closure/`; history target `.codex/history/015-p2-acceptance-closure.md`. Existing P.2 evidence remains linked from the history record.

## P.3 — Integration feasibility spike — DEFERRED

### P.3.1 — Assess standalone timeline integration feasibility — DEFERRED

**Objective/scope:** Evaluate a future integration with oxygenWX's `Now -> Hourly -> Daily -> Details` navigation, normalized forecast presentation, provenance, and gesture ownership. No production integration or navigation edits are included.
**Depends on:** Explicit owner approval to begin integration design.
**Exit after re-entry:** An owner-reviewed design documents pager/timeline gesture ownership, typed normalized presentation binding, source/provenance semantics, and a bounded integration plan that preserves existing network/cache/provider flows.
**Evidence:** Re-entry and exit evidence target `.codex/test-artifacts/016-integration-feasibility/` and `.codex/history/016-integration-feasibility.md`; no evidence is claimed while deferred.
**Re-entry condition:** The oxygenWX owner explicitly authorizes an integration feasibility design and gesture-ownership review.

## P.4 — Production-release presentation cleanup — DEFERRED

### P.4.1 — Remove experimental branding for production release — DEFERRED

**Objective/scope:** Remove `OXYGEN / TIME LAB` experimental branding from a production-release presentation.
**Depends on:** An approved production integration and release candidate.
**Exit after re-entry:** Release-candidate screens use approved product branding, and installed evidence records all affected surfaces.
**Evidence:** Target `.codex/test-artifacts/017-release-branding/` and `.codex/history/017-release-branding.md`; no evidence is claimed while deferred.
**Re-entry condition:** A production-release cycle is explicitly authorized for the integrated application.

### P.4.2 — Replace DEMO DATA only with accurate provenance — DEFERRED

**Objective/scope:** Replace the synthetic-data label only after the app presents production forecast data and accurate source/provenance information.
**Depends on:** P.3.1 and a production data path with verified provenance.
**Exit after re-entry:** Installed release UI identifies the actual data/source status accurately, with semantics and source evidence reviewed.
**Evidence:** Target `.codex/test-artifacts/018-release-provenance/` and `.codex/history/018-release-provenance.md`; no evidence is claimed while deferred.
**Re-entry condition:** Production forecast data and verified provenance are available in an explicitly approved integration cycle.

### P.4.3 — Define production timeline reset behavior — DEFERRED

**Objective/scope:** Decide how timeline reset/realignment fits production navigation and interaction ownership before integration.
**Depends on:** P.3.1.
**Exit after re-entry:** An owner-approved interaction contract states reset gesture/action, relationship to the outer pager, and accessibility semantics before implementation begins.
**Evidence:** Target `.codex/test-artifacts/019-production-reset-contract/` and `.codex/history/019-production-reset-contract.md`; no evidence is claimed while deferred.
**Re-entry condition:** The owner authorizes production navigation design following the integration feasibility review.
